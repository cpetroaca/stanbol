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

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
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
		@Property(name = RelationExtractionEngine.REFERENCED_SITE_ID, value = "dbpedia") })
public class RelationExtractionEngine extends AbstractEnhancementEngine<RuntimeException, RuntimeException>
		implements EnhancementEngine, ServiceProperties {

	private static final Integer ENGINE_ORDERING = ServiceProperties.ORDERING_POST_PROCESSING + 92;

	/**
	 * Referenced site configuration. Defaults to dbpedia.
	 */
	protected static final String REFERENCED_SITE_ID = "enhancer.engine.relationextraction.referencedSiteId";

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

		relationExtractor = new RelationExtractor(siteManager, referencedSiteID);

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
}
