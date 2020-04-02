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
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSameIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.IndividualSynonyms;
import org.semanticweb.owlapi.reasoner.Node;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkSameIndividualsTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();
        axioms.add(getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k")));
        axioms.add(getDataFactory().getOWLDifferentIndividualsAxiom(getOWLIndividual("i"), getOWLIndividual("l")));
        return axioms;
    }

    public void testAreSameIndividuals() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("k")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("l")));
        falseResponse(super.reasoner.answer(query));
    }

    public void testAreSameIndividualsViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("k"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("l"));
        assertFalse(super.reasoner.isEntailed(axiom));

    }

    public void testGetSameIndividuals() {
        GetSameIndividuals query = new GetSameIndividuals(getKBIRI(), getOWLIndividual("i"));
        IndividualSynonyms response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i"),getOWLIndividual("j"),getOWLIndividual("k")), response.getIndividuals());
    }

    public void testGetSameIndividualsViaOWLReasoner() {
        Node<OWLNamedIndividual> nodeSet = super.reasoner.getSameIndividuals(getOWLIndividual("i"));
        assertEquals(set(getOWLIndividual("i"),getOWLIndividual("j"),getOWLIndividual("k")), asUnorderedSet(nodeSet.entities()));
    }
}
