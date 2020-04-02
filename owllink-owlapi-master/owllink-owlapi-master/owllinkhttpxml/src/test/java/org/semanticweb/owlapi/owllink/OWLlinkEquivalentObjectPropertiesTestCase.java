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
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfObjectProperties;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkEquivalentObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opc()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opa()));
        axioms.add(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE()));
        return axioms;
    }

    public void testAreObjectPropertiesEquivalent() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb()));
        BooleanResponse result = super.reasoner.answer(query);
        trueResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb(), opc()));
        result = super.reasoner.answer(query);
        falseResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE(), opa()));
        result = super.reasoner.answer(query);
        falseResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE()));
        result = super.reasoner.answer(query);
        trueResponse(result);
    }

    public void testAreObjectPropertiesEquivalentViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb(), opc());
        assertFalse(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE(), opa());
        assertFalse(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE());
        assertTrue(super.reasoner.isEntailed(axiom));
    }

    public void testGetEquivalentObjectProperties() {
        GetEquivalentObjectProperties query = new GetEquivalentObjectProperties(getKBIRI(), opa());
        SetOfObjectProperties result = super.reasoner.answer(query);
        assertEquals(set(opa(),opb()),result);
    }

    public void testGetEquivalentObjectPropertiesViaOWLReasoner() {
        Node<OWLObjectPropertyExpression> result = super.reasoner.getEquivalentObjectProperties(opa());
        assertEquals(set(opa(),opb()),asUnorderedSet(result.entities()));
    }
}
