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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.nlp.NlpAnnotations;
import org.apache.stanbol.enhancer.nlp.model.AnalysedText;
import org.apache.stanbol.enhancer.nlp.model.Section;
import org.apache.stanbol.enhancer.nlp.model.annotation.Value;
import org.apache.stanbol.enhancer.nlp.relation.EntityRelation;
import org.apache.stanbol.enhancer.nlp.utils.NlpEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.impl.AbstractEnhancementEngine;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cpetroaca
 *
 */
@Component(immediate = true, metatype = true)
@Service(value = EnhancementEngine.class)
@Properties(value = { @Property(name = EnhancementEngine.PROPERTY_NAME, value = "relationextraction") })
public class RelationExtractionEngine extends AbstractEnhancementEngine<RuntimeException, RuntimeException>
		implements EnhancementEngine, ServiceProperties {

	private static final Integer ENGINE_ORDERING = ServiceProperties.ORDERING_POST_PROCESSING + 92;

	/**
	 * Logger
	 */
	private final Logger log = LoggerFactory.getLogger(RelationExtractionEngine.class);

	@Activate
	protected void activate(ComponentContext ctx) throws ConfigurationException {
		super.activate(ctx);

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
		AnalysedText at = NlpEngineHelper.getAnalysedText(this, ci, true);

		Iterator<? extends Section> sections = at.getSentences();
		if (!sections.hasNext()) { // process as single sentence
			sections = Collections.singleton(at).iterator();
		}

		while (sections.hasNext()) {
			Section section = sections.next();
			List<Value<EntityRelation>> relations = section.getAnnotations(NlpAnnotations.ENTITY_RELATION_ANNOTATION);
			for (Value<EntityRelation> relation : relations) {
				EntityRelation value = relation.value();
				value.getClass();
			}
		}
	}

	@Deactivate
	protected void deactivate(ComponentContext ctx) {
		log.info("deactivate {}[name:{}]", getClass().getSimpleName(), getName());

		super.deactivate(ctx);
	}
}
