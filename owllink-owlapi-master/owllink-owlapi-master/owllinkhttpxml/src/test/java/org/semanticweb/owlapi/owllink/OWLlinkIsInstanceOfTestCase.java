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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedTypes;
import org.semanticweb.owlapi.owllink.builtin.requests.GetInstances;
import org.semanticweb.owlapi.owllink.builtin.requests.GetTypes;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.ClassSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.Classes;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIsInstanceOfTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();

        axioms.add(getDataFactory().getOWLClassAssertionAxiom(a(), i()));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(b(), i()));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(b(), j()));

        return axioms;
    }

    public void testIsInstanceOf() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(a(), i()));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(top(), i()));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(b(), j()));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(a(), j()));
        falseResponse(super.reasoner.answer(query));
    }

    public void testIsInstanceOfViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLClassAssertionAxiom(a(), i());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(top(), i());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(b(), j());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(a(), j());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testFlattenedTypes() {
        GetFlattenedTypes query = new GetFlattenedTypes(getKBIRI(), i());
        Classes answer = super.reasoner.answer(query);
        assertEquals(set(a(),b(),top()), asUnorderedSet(answer.entities()));

        query = new GetFlattenedTypes(getKBIRI(), i(), true);
        answer = super.reasoner.answer(query);
        assertEquals(set(a(),b()), asUnorderedSet(answer.entities()));
    }

    public void testTypes() {
        GetTypes types = new GetTypes(getKBIRI(), i(), false);
        ClassSynsets answerTypes = super.reasoner.answer(types);
        assertEquals(set(a(),b(),top()), asUnorderedSet(answerTypes.entities()));
    }

    public void testTypesViaOWLReasoner() {
        NodeSet<OWLClass> answerTypes = super.reasoner.getTypes(i(), false);
        Set<OWLClass> expected = set(a(),b(),top());
        assertEquals(expected, asUnorderedSet(answerTypes.entities()));
    }

    public void testGetInstances() {
        GetInstances query = new GetInstances(getKBIRI(), a());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(i()), response.getFlattened());
    }

    public void testGetInstancesViaOWLReasoner() {
        NodeSet<OWLNamedIndividual> response = super.reasoner.getInstances(a(), false);
        assertEquals(set(i()), asUnorderedSet(response.entities()));
    }
}
