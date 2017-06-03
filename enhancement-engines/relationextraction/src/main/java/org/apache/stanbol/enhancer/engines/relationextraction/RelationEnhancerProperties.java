package org.apache.stanbol.enhancer.engines.relationextraction;

import org.apache.clerezza.commons.rdf.IRI;
import org.apache.stanbol.enhancer.servicesapi.rdf.NamespaceEnum;

public final class RelationEnhancerProperties {
	private RelationEnhancerProperties() {
	}

	/**
	 * The IRI for enhancement of type Relation
	 */
	public static final IRI ENHANCEMENT_TYPE = new IRI(NamespaceEnum.rdf + "relation");

	/**
	 * The IRI for relation type
	 */
	public static final IRI TYPE = new IRI(NamespaceEnum.rdf + "relation-type");
	
	/**
	 * The IRI for relation type
	 */
	public static final IRI ENTITY = new IRI(NamespaceEnum.rdf + "entity");

	/**
	 * The IRI for relation sentence number
	 */
	public static final IRI SENTENCE_NO = new IRI(NamespaceEnum.rdf + "sentence-number");

	/**
	 * The IRI for relation confidence level
	 */
	public static final IRI CONFIDENCE_LEVEL = new IRI(NamespaceEnum.rdf + "confidence-level");
}
