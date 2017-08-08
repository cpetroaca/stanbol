package org.apache.stanbol.enhancer.engines.relationextraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.clerezza.commons.rdf.Graph;
import org.apache.clerezza.commons.rdf.IRI;
import org.apache.clerezza.commons.rdf.impl.utils.TripleImpl;
import org.apache.clerezza.rdf.core.LiteralFactory;
import org.apache.stanbol.enhancer.nlp.NlpAnnotations;
import org.apache.stanbol.enhancer.nlp.coref.CorefFeature;
import org.apache.stanbol.enhancer.nlp.model.AnalysedText;
import org.apache.stanbol.enhancer.nlp.model.Section;
import org.apache.stanbol.enhancer.nlp.model.Span;
import org.apache.stanbol.enhancer.nlp.model.SpanTypeEnum;
import org.apache.stanbol.enhancer.nlp.model.annotation.Value;
import org.apache.stanbol.enhancer.nlp.ner.NerTag;
import org.apache.stanbol.enhancer.nlp.relation.EntityRelation;
import org.apache.stanbol.enhancer.nlp.utils.NlpEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.rdf.Properties;
import org.apache.stanbol.entityhub.servicesapi.model.Entity;
import org.apache.stanbol.entityhub.servicesapi.site.SiteManager;

/**
 * Relation extractor
 * 
 * @author cpetroaca
 *
 */
class RelationExtractor {
	/**
	 * Access to entities in Sites or Entityhub
	 */
	private EntityStore entityStore;

	/**
	 * Maps a relation type to a common ontology IRI
	 */
	private Map<String, IRI> ontologyMap;

	public RelationExtractor(SiteManager siteManager, String referencedSiteId, Map<String, IRI> ontologyMap) {
		this.entityStore = new EntityStore(siteManager, referencedSiteId);
		this.ontologyMap = ontologyMap;
	}

	public void extract(ContentItem ci, RelationExtractionEngine relationExtractionEngine) throws EngineException {
		AnalysedText at = NlpEngineHelper.getAnalysedText(relationExtractionEngine, ci, true);
		String language = NlpEngineHelper.getLanguage(relationExtractionEngine, ci, false);
		Graph metadata = ci.getMetadata();
		LiteralFactory literalFactory = LiteralFactory.getInstance();
		int sentenceNo = 0;

		Iterator<? extends Section> sections = at.getSentences();
		if (!sections.hasNext()) { // process as single sentence
			sections = Collections.singleton(at).iterator();
		}

		while (sections.hasNext()) {
			sentenceNo++;
			Section section = sections.next();
			List<Value<EntityRelation>> relations = section.getAnnotations(NlpAnnotations.ENTITY_RELATION_ANNOTATION);

			if (relations == null || relations.isEmpty())
				continue;

			List<Span> ners = extractNers(section);

			for (Value<EntityRelation> relation : relations) {
				EntityRelation value = relation.value();
				Set<Span> relationSpans = value.getEntitySpans();
				List<Object> entities = new ArrayList<>();

				/*
				 * Convert from relation spans to {@link Entity}
				 */
				for (Span relationSpan : relationSpans) {
					Span nerChunk = findNerChunk(relationSpan, ners);
					if (nerChunk != null) {
						Entity entity = entityStore.lookupEntity(nerChunk, language);
						if (entity != null) {
							entities.add(entity);
						} else {
							entities.add(relationSpan.getSpan());
						}
					}
				}

				if (entities.isEmpty())
					continue;

				IRI relationTypeMapping = ontologyMap.get(value.getType());
				/*
				 * TODO - is it ok to take the lock for every relation we find?
				 * Maybe we can write them all after the sentences loop
				 */
				ci.getLock().writeLock().lock();
				try {
					IRI textAnnotation = EnhancementEngineHelper.createTextEnhancement(ci, relationExtractionEngine);
					metadata.add(
							new TripleImpl(textAnnotation, RelationEnhancerProperties.TYPE, relationTypeMapping == null
									? literalFactory.createTypedLiteral(value.getType()) : relationTypeMapping));
					metadata.add(new TripleImpl(textAnnotation, RelationEnhancerProperties.SENTENCE_NO,
							literalFactory.createTypedLiteral(sentenceNo)));
					metadata.add(new TripleImpl(textAnnotation, RelationEnhancerProperties.CONFIDENCE_LEVEL,
							literalFactory.createTypedLiteral(value.getConfidence())));

					for (Object entity : entities) {
						if (entity instanceof Entity) {
							metadata.add(new TripleImpl(textAnnotation, RelationEnhancerProperties.ENTITY,
									new IRI(((Entity) entity).getId())));
						} else if (entity instanceof String) {
							metadata.add(new TripleImpl(textAnnotation, RelationEnhancerProperties.ENTITY,
									literalFactory.createTypedLiteral((String) entity)));
						}
					}

					metadata.add(new TripleImpl(textAnnotation, Properties.DC_TYPE,
							RelationEnhancerProperties.ENHANCEMENT_TYPE));
				} finally {
					ci.getLock().writeLock().unlock();
				}
			}
		}
	}

	/**
	 * Extracts all NERs and coreference elements which point to NERs from the
	 * given sentence
	 * 
	 * @param section
	 * @return
	 */
	private List<Span> extractNers(Section section) {
		List<Span> ners = new ArrayList<>();
		Iterator<Span> chunks = section.getEnclosed(EnumSet.of(SpanTypeEnum.Chunk));

		while (chunks.hasNext()) {
			Span chunk = chunks.next();

			Value<NerTag> ner = chunk.getAnnotation(NlpAnnotations.NER_ANNOTATION);
			if (ner != null) {
				ners.add(chunk);
			}
		}

		return ners;
	}

	/**
	 * 
	 * @param token
	 * @param ners
	 * @return the {@link Span} which contains the given token and also is a NER
	 */
	private Span findNerChunk(Span token, List<Span> ners) {
		if (token == null) {
			return null;
		}

		/*
		 * Also check coreferences
		 */
		Span coreference = null;
		outerfor: for (Value<CorefFeature> coref : token.getAnnotations(NlpAnnotations.COREF_ANNOTATION)) {
			CorefFeature value = coref.value();

			for (Span mention : value.getMentions()) {
				for (Value<CorefFeature> mentionCoref : mention.getAnnotations(NlpAnnotations.COREF_ANNOTATION)) {
					CorefFeature mentionCorefValue = mentionCoref.value();
					if (mentionCorefValue.isRepresentative()) {
						coreference = mention;
						break outerfor;
					}
				}
			}
		}

		for (Span ner : ners) {
			if ((token.getStart() >= ner.getStart() && token.getEnd() <= ner.getEnd()) || (coreference != null
					&& coreference.getStart() >= ner.getStart() && coreference.getEnd() <= ner.getEnd())) {
				return ner;
			}
		}

		return null;
	}
}
