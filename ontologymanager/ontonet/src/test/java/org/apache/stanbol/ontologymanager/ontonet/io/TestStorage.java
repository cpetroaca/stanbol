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
package org.apache.stanbol.ontologymanager.ontonet.io;

import static org.apache.stanbol.ontologymanager.ontonet.MockOsgiContext.onManager;
import static org.apache.stanbol.ontologymanager.ontonet.MockOsgiContext.ontologyProvider;
import static org.apache.stanbol.ontologymanager.ontonet.MockOsgiContext.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.clerezza.rdf.core.Graph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.stanbol.ontologymanager.ontonet.Constants;
import org.apache.stanbol.ontologymanager.ontonet.api.io.OntologyInputSource;
import org.apache.stanbol.ontologymanager.ontonet.api.io.ParentPathInputSource;
import org.apache.stanbol.ontologymanager.ontonet.api.io.RootOntologyIRISource;
import org.apache.stanbol.ontologymanager.ontonet.api.scope.OntologyScope;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStorage {

    @BeforeClass
    public static void setup() {
        reset();
    }

    private Logger log = LoggerFactory.getLogger(getClass());

    private String scopeId = "StorageTest";

    @After
    public void cleanup() {
        reset();
    }

    @Test
    public void storageOnScopeCreation() throws Exception {

        assertEquals(1, ontologyProvider.getStore().listTripleCollections().size());
        // This one has an import that we want to hijack locally, so we use the ParentPathInputSource.
        OntologyInputSource<?,?> ois = new ParentPathInputSource(new File(getClass().getResource(
            "/ontologies/minorcharacters.owl").toURI()));

        OntologyScope sc = onManager.createOntologyScope(scopeId, ois);

        Set<Triple> triples = new HashSet<Triple>();

        for (UriRef iri : ontologyProvider.getStore().listTripleCollections()) {
            log.info("{}", iri.toString());
            UriRef entity = new UriRef(Constants.PEANUTS_MINOR_BASE + "#" + Constants.truffles);
            Graph ctx = new GraphNode(entity, ontologyProvider.getStore().getTriples(iri)).getNodeContext();
            Iterator<Triple> it = ctx.iterator();
            while (it.hasNext())
                triples.add(it.next());
        }

        assertFalse(ontologyProvider.getStore().listTripleCollections().isEmpty());
        assertEquals(3, triples.size());

    }

    /**
     * If an ontology is removed from a scope, or the scope itself is torn down, this should not result in the
     * deletion of that ontology in general.
     */
    @Test
    public void storedOntologyOutlivesScope() throws Exception {
        String ephemeralScopeId = "CaducousScope";
        OntologyInputSource<OWLOntology,?> ois = new RootOntologyIRISource(IRI.create(getClass().getResource(
            "/ontologies/nonexistentcharacters.owl")));
        IRI ontologyId = ois.getRootOntology().getOntologyID().getOntologyIRI();
        OntologyScope scope = onManager.createOntologyScope(ephemeralScopeId);
        // Initially, the ontology is not there
        assertNull(ontologyProvider.getKey(ontologyId));
        // Once added, the ontology is there
        scope.getCustomSpace().addOntology(ois);
        assertNotNull(ontologyProvider.getKey(ontologyId));
        // Once removed from the scope, the ontology is still there
        scope.getCustomSpace().removeOntology(ontologyId);
        assertNotNull(ontologyProvider.getKey(ontologyId));
        // Once the scope is killed, the ontology is still there
        // TODO find a more appropriate method to kill scopes?
        scope.tearDown();
        assertNotNull(ontologyProvider.getKey(ontologyId));
    }

}
