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
package org.apache.stanbol.ontologymanager.ontonet;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.clerezza.rdf.core.access.TcManager;
import org.apache.clerezza.rdf.core.access.TcProvider;
import org.apache.clerezza.rdf.core.serializedform.Parser;
import org.apache.clerezza.rdf.core.serializedform.Serializer;
import org.apache.clerezza.rdf.jena.parser.JenaParserProvider;
import org.apache.clerezza.rdf.jena.serializer.JenaSerializerProvider;
import org.apache.clerezza.rdf.rdfjson.parser.RdfJsonParsingProvider;
import org.apache.clerezza.rdf.rdfjson.serializer.RdfJsonSerializingProvider;
import org.apache.clerezza.rdf.simple.storage.SimpleTcProvider;
import org.apache.stanbol.ontologymanager.ontonet.api.ONManager;
import org.apache.stanbol.ontologymanager.ontonet.api.OfflineConfiguration;
import org.apache.stanbol.ontologymanager.ontonet.api.ontology.OntologyProvider;
import org.apache.stanbol.ontologymanager.ontonet.api.scope.OntologySpaceFactory;
import org.apache.stanbol.ontologymanager.ontonet.api.session.SessionManager;
import org.apache.stanbol.ontologymanager.ontonet.impl.ONManagerImpl;
import org.apache.stanbol.ontologymanager.ontonet.impl.OfflineConfigurationImpl;
import org.apache.stanbol.ontologymanager.ontonet.impl.clerezza.ClerezzaOntologyProvider;
import org.apache.stanbol.ontologymanager.ontonet.impl.clerezza.OntologySpaceFactoryImpl;
import org.apache.stanbol.ontologymanager.ontonet.impl.session.SessionManagerImpl;

/**
 * Utility class that provides some objects that would otherwise be provided by SCR reference in an OSGi
 * environment. Can be used to simulate OSGi in unit tests.
 * 
 * @author alexdma
 * 
 */
public class MockOsgiContext {

    private static Dictionary<String,Object> config;

    private static OfflineConfiguration offline;

    public static ONManager onManager;

    public static OntologyProvider<TcProvider> ontologyProvider;

    public static Parser parser;

    public static Serializer serializer;

    public static SessionManager sessionManager;

    public static TcManager tcManager;

    static {
        config = new Hashtable<String,Object>();
        config.put(ONManager.ONTOLOGY_NETWORK_NS, "http://stanbol.apache.org/scope/");
        config.put(SessionManager.SESSIONS_NS, "http://stanbol.apache.org/session/");
        config.put(SessionManager.MAX_ACTIVE_SESSIONS, "-1");
        offline = new OfflineConfigurationImpl(new Hashtable<String,Object>());
        reset();
    }

    /**
     * Sets up a new mock OSGi context and cleans all resources and components.
     */
    public static void reset() {
        // reset Clerezza objects
        tcManager = new TcManager();
        tcManager.addWeightedTcProvider(new SimpleTcProvider());
        parser = new Parser(); // add Jena-supported formats + RDF/JSON
        parser.bindParsingProvider(new JenaParserProvider());
        parser.bindParsingProvider(new RdfJsonParsingProvider());
        serializer = new Serializer(); // add Jena-supported formats + RDF/JSON
        serializer.bindSerializingProvider(new JenaSerializerProvider());
        serializer.bindSerializingProvider(new RdfJsonSerializingProvider());

        // reset Stanbol objects
        ontologyProvider = new ClerezzaOntologyProvider(tcManager, offline, parser);
        resetManagers();
    }

    public static void resetManagers() {
        OntologySpaceFactory factory = new OntologySpaceFactoryImpl(ontologyProvider, config);
        onManager = new ONManagerImpl(ontologyProvider, offline, factory, config);
        sessionManager = new SessionManagerImpl(ontologyProvider, config);
    }

}
