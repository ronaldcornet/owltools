/*
 * This file is part of the OWLlink API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, derivo GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, derivo GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.owllink;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.server.OWLlinkServer;
import org.semanticweb.owlapi.owllink.server.serverfactory.OWLlinkServerFactory;
import org.semanticweb.owlapi.owllink.server.serverfactory.OpenlletServerFactory;
import org.semanticweb.owlapi.reasoner.BufferingMode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Olaf Noppens
 */
@SuppressWarnings("javadoc")
public abstract class AbstractOWLlinkTestCase extends TestCase {
     static class ReasonerServer {
        protected static OWLlinkServerFactory openlletFactory = new OpenlletServerFactory();
        private static OWLlinkServer server;
       static  void start() {
           if(server==null) {
                server = openlletFactory.createServer(8080);
            server.run();
           }
        }
    }

     protected void trueResponse(BooleanResponse response) {
         assertFalse(response.isUnknown());
         assertTrue(response.getResult().booleanValue());
     }

     protected void falseResponse(BooleanResponse response) {
         assertFalse(response.isUnknown());
         assertFalse(response.getResult().booleanValue());
     }

    @SafeVarargs
    protected static final <X> Set<X> set(X... xs ){
        return new HashSet<>(Arrays.asList(xs));
    }

    protected OWLClass top() {
        return getDataFactory().getOWLThing();
    }

    protected OWLNamedIndividual j() {
        return getOWLIndividual("j");
    }

    protected OWLNamedIndividual i() {
        return getOWLIndividual("i");
    }

    protected OWLClass a() {
        return getOWLClass("A");
    }

    protected OWLClass b() {
        return getOWLClass("B");
    }

    protected OWLClass c() {
        return getOWLClass("C");
    }

    protected OWLClass d() {
        return getOWLClass("D");
    }

    protected OWLNamedIndividual ib() {
        return getOWLIndividual("B");
    }

    protected OWLNamedIndividual id() {
        return getOWLIndividual("D");
    }

    protected OWLNamedIndividual ic() {
        return getOWLIndividual("C");
    }

    protected OWLNamedIndividual ia() {
        return getOWLIndividual("A");
    }
    protected OWLDataProperty dpA() {
        return getOWLDataProperty("A");
    }

    protected OWLDataProperty dpB() {
        return getOWLDataProperty("B");
    }

    protected OWLDataProperty dpC() {
        return getOWLDataProperty("C");
    }

    protected OWLDataProperty dpD() {
        return getOWLDataProperty("D");
    }

    protected OWLDataProperty dpE() {
        return getOWLDataProperty("E");
    }

    protected OWLDataProperty dpp() {
        return getOWLDataProperty("p");
    }

    protected OWLDataProperty dpq() {
        return getOWLDataProperty("q");
    }

    protected OWLDataProperty dpr() {
        return getOWLDataProperty("r");
    }

    protected OWLObjectProperty opd() {
        return getOWLObjectProperty("D");
    }

    protected OWLObjectProperty opc() {
        return getOWLObjectProperty("C");
    }

    protected OWLObjectProperty opb() {
        return getOWLObjectProperty("B");
    }

    protected OWLObjectProperty opa() {
        return getOWLObjectProperty("A");
    }

    protected OWLObjectProperty opE() {
        return getOWLObjectProperty("E");
    }

    protected OWLObjectProperty opF() {
        return getOWLObjectProperty("F");
    }

    protected OWLObjectProperty opP() {
        return getOWLObjectProperty("P");
    }

    protected OWLObjectProperty opS() {
        return getOWLObjectProperty("S");
    }

    protected OWLObjectProperty opp() {
        return getOWLObjectProperty("p");
    }

    protected OWLObjectProperty opq() {
        return getOWLObjectProperty("q");
    }

    protected OWLObjectProperty opr() {
        return getOWLObjectProperty("r");
    }

    protected OWLlinkReasoner reasoner;
    protected OWLDataFactory dataFactory;
    protected OWLOntologyManager manager;
    private IRI uriBase;
    protected IRI reasonerIRI;
    OWLOntology rootOntology;

    @Override
    protected void setUp() throws Exception {
       ReasonerServer.start();
        this.manager = OWLManager.createOWLOntologyManager();
        rootOntology = this.manager.createOntology();
        reasoner = new OWLlinkHTTPXMLReasoner(rootOntology, new OWLlinkReasonerConfigurationImpl(),
            BufferingMode.NON_BUFFERING);
        uriBase = IRI.create("http://www.semanticweb.org/owlapi/owllink/test");
        reasonerIRI = uriBase;
    }

    /** @return */
    public IRI getKBIRI() {
        return this.reasonerIRI;
    }

    /** @return */
    public OWLOntology getRootOntology() {
        return this.rootOntology;
    }

    /** @return */
    public OWLOntology getOWLOntology(String name) {
        try {
            IRI iri = IRI.create(uriBase + "/" + name);
            if (manager.contains(iri)) {
                return manager.getOntology(iri);
            } else {
                return manager.createOntology(iri);
            }
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void tearDown() throws Exception {
        this.manager.removeOntology(rootOntology);
        super.tearDown();
    }

    protected OWLOntologyManager getManager() {
        return this.manager;
    }

    protected OWLDataFactory getDataFactory() {
        return getManager().getOWLDataFactory();
    }

    /** @return */
    public OWLClass getOWLClass(String name) {
        return getDataFactory().getOWLClass(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLObjectProperty getOWLObjectProperty(String name) {
        return getDataFactory().getOWLObjectProperty(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLDataProperty getOWLDataProperty(String name) {
        return getDataFactory().getOWLDataProperty(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLNamedIndividual getOWLIndividual(String name) {
        return getDataFactory().getOWLNamedIndividual(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLDatatype getOWLDatatype(String name) {
        return getDataFactory().getOWLDatatype(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        return getDataFactory().getOWLAnnotationProperty(IRI.create(uriBase + "#" + name));
    }

    /** @return */
    public OWLLiteral getLiteral(int value) {
        return getDataFactory().getOWLLiteral(value);
    }

    protected abstract Set<? extends OWLAxiom> createAxioms();
}
