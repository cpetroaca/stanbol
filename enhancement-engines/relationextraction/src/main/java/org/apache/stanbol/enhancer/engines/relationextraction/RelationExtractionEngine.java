/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.stanbol.enhancer.engines.relationextraction;

import static org.apache.stanbol.enhancer.nlp.utils.NlpEngineHelper.getLanguage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.apache.clerezza.commons.rdf.IRI;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.commons.stanboltools.datafileprovider.DataFileProvider;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.impl.AbstractEnhancementEngine;
import org.apache.stanbol.entityhub.servicesapi.site.SiteManager;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Engine which extracts relations between entities
 * 
 * @author cpetroaca
 *
 */
@Component(immediate = true, metatype = true)
@Service(value = EnhancementEngine.class)
@Properties(value = { @Property(name = EnhancementEngine.PROPERTY_NAME, value = "relationextraction"),
		@Property(name = RelationExtractionEngine.REFERENCED_SITE_ID, value = "dbpediafacts"),
		@Property(name = RelationExtractionEngine.ONTOLOGY_MAP_FILE, value = "dbpedia_ontology_map") })
public class RelationExtractionEngine extends AbstractEnhancementEngine<RuntimeException, RuntimeException>
		implements EnhancementEngine, ServiceProperties {

	private static final Integer ENGINE_ORDERING = ServiceProperties.ORDERING_POST_PROCESSING + 92;

	/**
	 * Referenced site configuration. Defaults to dbpedia.
	 */
	protected static final String REFERENCED_SITE_ID = "enhancer.engine.relationextraction.referencedSiteId";

	/**
	 * Referenced site configuration. Defaults to dbpedia.
	 */
	protected static final String ONTOLOGY_MAP_FILE = "enhancer.engine.relationextraction.ontologyMapFile";

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(RelationExtractionEngine.class);

	/**
	 * Service of the Entityhub that manages all the active referenced Site.
	 * This Service is used to lookup the configured Referenced Site when we
	 * need to enhance a content item.
	 */
	@Reference
	protected SiteManager siteManager;

	/**
	 * Reference to the {@link DataFileProvider} that loads the event model
	 */
	@Reference
	protected DataFileProvider dataFileProvider;

	private RelationExtractor relationExtractor;

	@SuppressWarnings("unchecked")
	@Activate
	protected void activate(ComponentContext ctx) throws ConfigurationException {
		super.activate(ctx);

		Dictionary<String, Object> config = ctx.getProperties();

		Object referencedSiteIDfromConfig = config.get(REFERENCED_SITE_ID);
		String referencedSiteID = null;
		if (referencedSiteIDfromConfig != null) {
			referencedSiteID = referencedSiteIDfromConfig.toString();
			if (referencedSiteID.isEmpty()) {
				throw new ConfigurationException("Property cannot be empty", REFERENCED_SITE_ID);
			}
		} else {
			throw new ConfigurationException("Missing property", REFERENCED_SITE_ID);
		}

		String ontologyMapFilename = (String) config.get(ONTOLOGY_MAP_FILE);
		if (ontologyMapFilename == null || ontologyMapFilename.isEmpty()) {
			throw new ConfigurationException(ONTOLOGY_MAP_FILE,
					"The Ontology Map File is a required Parameter and MUST NOT be NULL or an empty String!");
		}

		relationExtractor = new RelationExtractor(siteManager, referencedSiteID,
				parseOntologyMapFile(ontologyMapFilename));

		log.info("activate {}[name:{}]", getClass().getSimpleName(), getName());
	}

	@Override
	public Map<String, Object> getServiceProperties() {
		return Collections
				.unmodifiableMap(Collections.singletonMap(ENHANCEMENT_ENGINE_ORDERING, (Object) ENGINE_ORDERING));
	}

	@Override
	public int canEnhance(ContentItem ci) throws EngineException {
		String language = getLanguage(this, ci, false);
		if (language == null) {
			log.debug("Engine {} ignores ContentItem {} becuase language {} is not detected.",
					new Object[] { getName(), ci.getUri(), language });
			return CANNOT_ENHANCE;
		}

		return ENHANCE_SYNCHRONOUS;
	}

	@Override
	public void computeEnhancements(ContentItem ci) throws EngineException {
		relationExtractor.extract(ci, this);
	}

	@Deactivate
	protected void deactivate(ComponentContext ctx) {
		log.info("deactivate {}[name:{}]", getClass().getSimpleName(), getName());

		super.deactivate(ctx);
	}

	/**
	 * Lookup an ontology data file via the {@link #dataFileProvider}
	 * 
	 * @param modelName
	 *            the name of the model
	 * @return the stream or <code>null</code> if not found
	 * @throws IOException
	 *             an any error while opening the model file
	 */
	private InputStream lookupOntologyStream(final String fileName, final Map<String, String> properties)
			throws IOException {
		try {
			return AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
				public InputStream run() throws IOException {
					return dataFileProvider.getInputStream(null, fileName, properties);
				}
			});
		} catch (PrivilegedActionException pae) {
			Exception e = pae.getException();
			if (e instanceof IOException) {
				throw (IOException) e;
			} else {
				throw RuntimeException.class.cast(e);
			}
		}
	}

	/**
	 * Read the ontology mappings from file
	 * @param ontologyMapFilename
	 * @return
	 * @throws ConfigurationException
	 */
	private Map<String, IRI> parseOntologyMapFile(String ontologyMapFilename) throws ConfigurationException {
		Map<String, IRI> ontologyMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(lookupOntologyStream(ontologyMapFilename, null)));) {
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] parts = line.split("=");

				String relationType = parts[0];
				IRI ontIri = new IRI(parts[1]);

				ontologyMap.put(relationType, ontIri);
			}
		} catch (IOException e) {
			throw new ConfigurationException("", "Error reading ontology file", e);
		}

		return ontologyMap;
	}
}
