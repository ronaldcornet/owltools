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
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.DataPropertySynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDisjointDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpD()));
        return axioms;
    }

    public void testAreDataPropertiesDisjoint() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB()));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC()));
        answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpE()));
        answer = super.reasoner.answer(query);
        falseResponse(answer);
    }

    public void testAreDataPropertiesDisjointViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpE());
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetDisjointDataProperties() {
        GetDisjointDataProperties query = new GetDisjointDataProperties(getKBIRI(), dpB());
        DataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(asUnorderedSet(response.nodes()).toString(), 3,response.nodes().count());
    }

    public void testGetDisjointDataPropertiesViaOWLReasoner() {
        NodeSet<OWLDataProperty> response = super.reasoner.getDisjointDataProperties(dpB());
        assertEquals(asUnorderedSet(response.nodes()).toString(),3,response.nodes().count());
    }
}
