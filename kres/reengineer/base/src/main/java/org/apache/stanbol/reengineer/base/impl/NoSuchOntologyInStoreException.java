package org.apache.stanbol.reengineer.base.impl;

import org.semanticweb.owlapi.model.IRI;

public class NoSuchOntologyInStoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IRI ontologyIRI;
	
	public NoSuchOntologyInStoreException(IRI ontologyIRI) {
		this.ontologyIRI = ontologyIRI;
	}
	
	public IRI getOntologyIRI() {
		return ontologyIRI;
	}
	
}
