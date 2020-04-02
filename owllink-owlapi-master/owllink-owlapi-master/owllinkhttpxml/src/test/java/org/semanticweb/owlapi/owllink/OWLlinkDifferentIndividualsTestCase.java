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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDifferentIndividualsTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDifferentIndividualsAxiom(ia(), ib(), ic()));
        axioms.add(getDataFactory().getOWLSameIndividualAxiom(ia(), id()));
        return axioms;
    }

    public void testAreIndividualsDisjoint() {
        Set<OWLIndividual> indis = CollectionFactory.createSet();
        indis.add(ia());
        indis.add(ib());
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDifferentIndividualsAxiom(indis));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);
        indis.add(ic());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDifferentIndividualsAxiom(indis));
        answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(indis));
        answer = super.reasoner.answer(query);
        falseResponse(answer);
    }

    public void testAreIndividualsDisjointViaOWLReasoner() {
        Set<OWLIndividual> indis = CollectionFactory.createSet();
        indis.add(ia());
        indis.add(ib());
        OWLAxiom axiom = getDataFactory().getOWLDifferentIndividualsAxiom(indis);
        assertTrue(super.reasoner.isEntailed(axiom));

        indis.add(ic());

        axiom = getDataFactory().getOWLDifferentIndividualsAxiom(indis);
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(indis);
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetDisjointIndividuals() {
        GetDifferentIndividuals query = new GetDifferentIndividuals(getKBIRI(), ib());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(2, response.getSynsets().size());
    }

    public void testGetDisjointIndividualsWithOWLReasoner() {
        NodeSet<OWLNamedIndividual> response = super.reasoner.getDifferentIndividuals(ib());
        assertEquals(set(ia(),ic(),id()),asUnorderedSet(response.entities()));
    }

    public void testGetFlattenedDisjointIndividuals() {
        GetFlattenedDifferentIndividuals query = new GetFlattenedDifferentIndividuals(getKBIRI(), ib());
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(ia(),ic(),id()), response);
    }
}
