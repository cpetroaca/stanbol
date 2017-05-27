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
package org.apache.stanbol.enhancer.nlp.json.valuetype.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.nlp.json.valuetype.ValueTypeParser;
import org.apache.stanbol.enhancer.nlp.json.valuetype.ValueTypeSerializer;
import org.apache.stanbol.enhancer.nlp.model.AnalysedText;
import org.apache.stanbol.enhancer.nlp.model.Span;
import org.apache.stanbol.enhancer.nlp.model.SpanTypeEnum;
import org.apache.stanbol.enhancer.nlp.relation.EntityRelation;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

@Component(immediate = true, policy = ConfigurationPolicy.IGNORE)
@Service(value = { ValueTypeParser.class, ValueTypeSerializer.class })
@Property(name = ValueTypeParser.PROPERTY_TYPE, value = EntityRelationSupport.TYPE_VALUE)
public class EntityRelationSupport implements ValueTypeParser<EntityRelation>, ValueTypeSerializer<EntityRelation> {

	public static final String TYPE_VALUE = "org.apache.stanbol.enhancer.nlp.relation.EntityRelation";

	private static final String RELATION_TYPE_TAG = "relation_type";
	private static final String CONFIDENCE_TAG = "confidence";
	private static final String ENTITIES_TAG = "entities";
	private static final String ENTITY_TYPE_TAG = "type";
	private static final String ENTITY_START_TAG = "start";
	private static final String ENTITY_END_TAG = "end";

	@Override
	public ObjectNode serialize(ObjectMapper mapper, EntityRelation relation) {
		ObjectNode jRelation = mapper.createObjectNode();

		jRelation.put(RELATION_TYPE_TAG, relation.getType());
		jRelation.put(CONFIDENCE_TAG, relation.getConfidence());

		Set<Span> entities = relation.getEntitySpans();
		ArrayNode jEntities = mapper.createArrayNode();

		for (Span entity : entities) {
			ObjectNode jEntity = mapper.createObjectNode();

			jEntity.put(ENTITY_TYPE_TAG, entity.getType().toString());
			jEntity.put(ENTITY_START_TAG, entity.getStart());
			jEntity.put(ENTITY_END_TAG, entity.getEnd());

			jEntities.add(jEntity);
		}

		jRelation.put(ENTITIES_TAG, jEntities);

		return jRelation;
	}

	@Override
	public Class<EntityRelation> getType() {
		return EntityRelation.class;
	}

	@Override
	public EntityRelation parse(ObjectNode jRelation, AnalysedText at) {
		JsonNode jType = jRelation.path(RELATION_TYPE_TAG);
		if (jType.isNull()) {
			throw new IllegalStateException("Field 'type' muwst not be null");
		}

		JsonNode jConfidence = jRelation.path(CONFIDENCE_TAG);
		if (jConfidence.isNull()) {
			throw new IllegalStateException("Field 'confidence' muwst not be null");
		}
		
		JsonNode node = jRelation.path(ENTITIES_TAG);
		Set<Span> entities = new HashSet<Span>();

		if (node.isArray()) {
			ArrayNode jEntities = (ArrayNode) node;

			for (int i = 0; i < jEntities.size(); i++) {
				JsonNode member = jEntities.get(i);

				if (member.isObject()) {
					ObjectNode jEntity = (ObjectNode) member;
					SpanTypeEnum spanType = SpanTypeEnum.valueOf(jEntity.path(ENTITY_TYPE_TAG).getTextValue());
					int spanStart = jEntity.path(ENTITY_START_TAG).asInt();
					int spanEnd = jEntity.path(ENTITY_END_TAG).asInt();
					Span entitySpan = null;

					switch (spanType) {
					case Chunk:
						entitySpan = at.addChunk(spanStart, spanEnd);
						break;
					case Sentence:
					case Text:
					case TextSection:
						break;
					case Token:
						entitySpan = at.addToken(spanStart, spanEnd);
						break;

					}

					entities.add(entitySpan);
				}
			}
		}

		return new EntityRelation(jType.asText(), jConfidence.asDouble(), entities);
	}
}
