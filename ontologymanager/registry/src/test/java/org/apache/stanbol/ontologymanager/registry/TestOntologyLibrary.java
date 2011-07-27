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
package org.apache.stanbol.ontologymanager.registry;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.stanbol.ontologymanager.ontonet.api.OfflineConfiguration;
import org.apache.stanbol.ontologymanager.ontonet.api.io.OntologyInputSource;
import org.apache.stanbol.ontologymanager.ontonet.impl.OfflineConfigurationImpl;
import org.apache.stanbol.ontologymanager.registry.api.RegistryManager;
import org.apache.stanbol.ontologymanager.registry.api.model.Library;
import org.apache.stanbol.ontologymanager.registry.api.model.Registry;
import org.apache.stanbol.ontologymanager.registry.api.model.RegistryItem;
import org.apache.stanbol.ontologymanager.registry.impl.RegistryManagerImpl;
import org.apache.stanbol.ontologymanager.registry.impl.util.RegistryUtils;
import org.apache.stanbol.ontologymanager.registry.io.LibrarySource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

/**
 * This class tests the correct loading of ontology libraries from an OWL repository. It also checks that
 * ontologies referred to only by other libraries in the same repository are not loaded.
 */
public class TestOntologyLibrary {

    private static OfflineConfiguration offline;

    /**
     * Sets the ontology network manager and registry loader before running the tests.
     */
    @BeforeClass
    public static void setupTest() throws Exception {
        final Dictionary<String,Object> config = new Hashtable<String,Object>();
        config.put(OfflineConfiguration.ONTOLOGY_PATHS, new String[] {"/ontologies", "/ontologies/registry"});
        offline = new OfflineConfigurationImpl(config);
    }

    private String registryResource = "/ontologies/registry/onmtest.owl";

    private RegistryManager regMgr;

    private OWLOntologyManager virginOntologyManager;

    @After
    public void reset() {
        regMgr = null;
    }

    /**
     * Resets the {@link OWLOntologyManager} used for tests, since caching phenomena across tests could bias
     * the results.
     * 
     * @throws Exception
     *             if any error occurs;
     */
    @Before
    public void setup() throws Exception {
        virginOntologyManager = OWLManager.createOWLOntologyManager();
        URL url = getClass().getResource("/ontologies");
        virginOntologyManager.addIRIMapper(new AutoIRIMapper(new File(url.toURI()), true));
        url = getClass().getResource("/ontologies/registry");
        virginOntologyManager.addIRIMapper(new AutoIRIMapper(new File(url.toURI()), true));

        // *Not* adding mappers to empty resource directories.
        // It seems the Maven surefire plugin won't copy them.
        // url = getClass().getResource("/ontologies/odp");
        // virginOntologyManager.addIRIMapper(new AutoIRIMapper(new File(url.toURI()), true));
    }

    /**
     * Uses a plain {@link RegistryLoader} to load a single ontology library and checks for its expected hits
     * and misses.
     * 
     * @throws Exception
     */
    @Test
    public void testLibraryLoad() throws Exception {

        IRI localTestRegistry = IRI.create(getClass().getResource(registryResource));

        Dictionary<String,Object> regmanConf = new Hashtable<String,Object>();
        regmanConf.put(RegistryManager.REGISTRY_LOCATIONS, new String[] {localTestRegistry.toString()});
        regMgr = new RegistryManagerImpl(offline, regmanConf);
        assertNotNull(regMgr);

        assertFalse(regMgr.getRegistries().isEmpty());
        assertEquals(1, regMgr.getRegistries().size());
        Registry reg = regMgr.getRegistries().iterator().next();
        assertTrue(reg.hasChildren());
        Library lib = null;
        // Look for test #Library2
        for (RegistryItem item : reg.getChildren()) {
            if (Locations.LIBRARY_TEST2.equals(item.getIRI())) {
                lib = (Library) item;
                break;
            }
        }
        assertNotNull(lib);
        // Should be in the library.
        boolean hasShould = RegistryUtils.containsOntologyRecursive(lib, Locations.CHAR_DROPPED);
        // Should NOT be in the library (belongs to another library in the same registry).
        boolean hasShouldNot = RegistryUtils.containsOntologyRecursive(lib, Locations.CHAR_ACTIVE);
        assertTrue(hasShould);
        assertFalse(hasShouldNot);
    }

    /**
     * Tests the creation of an ontology input source from a single library. Because the test is run offline,
     * import statements might be file URIs, so tests should not fail on this.
     * 
     * @throws Exception
     */
    @Test
    public void testLibrarySourceCreation() throws Exception {

        IRI localTestRegistry = IRI.create(getClass().getResource(registryResource));

        Dictionary<String,Object> regmanConf = new Hashtable<String,Object>();
        regmanConf.put(RegistryManager.REGISTRY_LOCATIONS, new String[] {localTestRegistry.toString()});
        regMgr = new RegistryManagerImpl(offline, regmanConf);
        assertNotNull(regMgr);

        OntologyInputSource src = new LibrarySource(Locations.LIBRARY_TEST1, regMgr, virginOntologyManager);
        OWLOntology o = src.getRootOntology();
        boolean hasImporting = false, hasImported = false;
        for (OWLImportsDeclaration ax : o.getImportsDeclarations()) {
            // Since we added a local IRI mapping, import statements might be using file: IRIs instead of
            // HTTP, in which case IRI equality would fail. So it is enough here to just check the filename.
            String tmpstr = ax.getIRI().toString();
            if (!hasImporting && tmpstr.endsWith("characters_all.owl")) hasImporting = true;
            else if (!hasImported && tmpstr.endsWith("maincharacters.owl")) hasImported = true;
            if (hasImporting && hasImported) break;
        }
        assertTrue(hasImporting);
        assertTrue(hasImported);
    }

}
