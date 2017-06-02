package org.apache.stanbol.enhancer.nlp.relation;

import java.util.Set;

import org.apache.stanbol.enhancer.nlp.model.Span;

/**
 * A relation between two or more entities
 * 
 * @author cpetroaca
 *
 */
public class EntityRelation {
	/**
	 * The type of relation. For example : Work_For, Live_In, etc
	 */
	private String type;

	/**
	 * The confidence that this is the type of relation
	 */
	private double confidence;

	/**
	 * The set of Spans containing the entities which are involved in the
	 * relation
	 */
	private Set<Span> entitySpans;

	public EntityRelation(String type, double confidence, Set<Span> entitySpans) {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("The relation type cannot be null or empty");
		}

		if (entitySpans == null || entitySpans.isEmpty()) {
			throw new IllegalArgumentException("The entity spans cannot be null or empty");
		}

		this.type = type;
		this.confidence = confidence;
		this.entitySpans = entitySpans;
	}

	/**
	 * 
	 * @return the relation type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return the confidence with which we are sure we detected this relation
	 *         type
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * 
	 * @return the set of Spans containing the entities which are involved in
	 *         the relation
	 */
	public Set<Span> getEntitySpans() {
		return entitySpans;
	}
	
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + type.hashCode();
        result = prime * result + Double.valueOf(confidence).hashCode();
        result = prime * result + entitySpans.hashCode();
        return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
            return false;
		
		EntityRelation other = (EntityRelation) obj;
        
		return (type.equals(other.type))
			&& (confidence == other.confidence)
			&& (entitySpans.equals(other.entitySpans));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type=");
		sb.append(type);
		sb.append(", ");
		sb.append("Confidence=");
		sb.append(confidence);
		sb.append(", ");
		sb.append("EntitySpans=");
		sb.append(entitySpans);
		
		return sb.toString();
	}
}
