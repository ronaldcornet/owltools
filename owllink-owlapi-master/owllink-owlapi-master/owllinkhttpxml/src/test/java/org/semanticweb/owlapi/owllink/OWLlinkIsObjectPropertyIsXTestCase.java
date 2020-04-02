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

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIsObjectPropertyIsXTestCase extends AbstractOWLlinkAxiomsTestCase {
    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axiom = CollectionFactory.createSet();
        axiom.add(getDataFactory().getOWLFunctionalObjectPropertyAxiom(opa()));
        axiom.add(getDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(opb()));
        axiom.add(getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc()));
        axiom.add(getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd()));
        axiom.add(getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE()));
        axiom.add(getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF()));

        return axiom;
    }

    public void testIsFunctional() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opa()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opb()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsFunctionalViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opa());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opb());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsInverseFunctional() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opb()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opa()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsInverseFunctionalViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opb());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opa());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsReflexive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLReflexiveObjectPropertyAxiom(opd()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsReflexiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLReflexiveObjectPropertyAxiom(opd());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsIrreflexive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opa()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsIrreflexiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opa());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsAsymmetric() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opF()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsAsymmetricViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opF());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsTranstive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLTransitiveObjectPropertyAxiom(opE()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsTranstiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLTransitiveObjectPropertyAxiom(opE());
        assertFalse(reasoner.isEntailed(axiom));
    }
}
