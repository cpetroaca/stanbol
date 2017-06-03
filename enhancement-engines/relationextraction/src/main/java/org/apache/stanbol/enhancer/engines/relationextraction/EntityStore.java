package org.apache.stanbol.enhancer.engines.relationextraction;

import java.util.Iterator;

import org.apache.stanbol.enhancer.nlp.NlpAnnotations;
import org.apache.stanbol.enhancer.nlp.model.Span;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.rdf.Properties;
import org.apache.stanbol.entityhub.servicesapi.model.Entity;
import org.apache.stanbol.entityhub.servicesapi.model.Representation;
import org.apache.stanbol.entityhub.servicesapi.model.rdf.RdfResourceEnum;
import org.apache.stanbol.entityhub.servicesapi.query.FieldQuery;
import org.apache.stanbol.entityhub.servicesapi.query.FieldQueryFactory;
import org.apache.stanbol.entityhub.servicesapi.query.QueryResultList;
import org.apache.stanbol.entityhub.servicesapi.query.ReferenceConstraint;
import org.apache.stanbol.entityhub.servicesapi.query.TextConstraint;
import org.apache.stanbol.entityhub.servicesapi.site.Site;
import org.apache.stanbol.entityhub.servicesapi.site.SiteManager;

/**
 * Wrapper class to have easy access to entityhub or managed site
 * 
 * @author cpetroaca
 *
 */
class EntityStore {
	private static final int MAX_MATCHES = 3;

	/**
	 * Site manager
	 */
	private SiteManager siteManager;

	/**
	 * Referenced site id
	 */
	private String referencedSiteId;

	public EntityStore(SiteManager siteManager, String referencedSiteId) {
		this.siteManager = siteManager;
		this.referencedSiteId = referencedSiteId;
	}

	/**
	 * Tries to link a NER first to the custom site, then if no results are
	 * found it tries to link it to the default site
	 * 
	 * @param ner
	 * @param language
	 * @return
	 * @throws EngineException
	 */
	public Entity lookupEntity(Span ner, String language) throws EngineException {
		Site referencedSite = getReferencedSite();
		FieldQueryFactory queryFactory = referencedSite.getQueryFactory();
		FieldQuery query = queryFactory.createFieldQuery();
		TextConstraint labelConstraint;
		String namedEntityLabel = ner.getSpan();
		
		labelConstraint = new TextConstraint(namedEntityLabel, false, language, null);
		query.setConstraint(Properties.RDFS_LABEL.getUnicodeString(), labelConstraint);
		query.setLimit(MAX_MATCHES);
		query.setConstraint(Properties.RDF_TYPE.getUnicodeString(), new ReferenceConstraint(
				ner.getAnnotation(NlpAnnotations.NER_ANNOTATION).value().getType().getUnicodeString()));
		
		QueryResultList<Entity> results = referencedSite.findEntities(query);

		if (results.isEmpty()) {
			return null;
		}

		return getMaxScoreEntity(results);
	}

	/**
	 * Retrieves the configured {@link Site} which holds the NER properties.
	 * 
	 * @return
	 * @throws EngineException
	 */
	private Site getReferencedSite() {
		Site site = null;

		if (referencedSiteId != null) { // lookup the referenced site
			site = siteManager.getSite(referencedSiteId);
		}

		return site;
	}

	/**
	 * Returns the entity with the max score
	 * 
	 * @param results
	 * @return
	 */
	private Entity getMaxScoreEntity(QueryResultList<Entity> results) {
		Entity maxScoreEntity = null;
		Iterator<Entity> entities = results.iterator();

		while (entities.hasNext()) {
			Entity entity = entities.next();
			Representation rep = entity.getRepresentation();
			Float score = rep.getFirst(RdfResourceEnum.resultScore.getUri(), Float.class);
			Float maxScore = maxScoreEntity == null ? 0f
					: maxScoreEntity.getRepresentation().getFirst(RdfResourceEnum.resultScore.getUri(), Float.class);

			if (score > maxScore) {
				maxScoreEntity = entity;
			}
		}

		return maxScoreEntity;
	}
}
