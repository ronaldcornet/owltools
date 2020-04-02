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
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectPropertyHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSuperObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkSubObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();

        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opc()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opc()));

        return axioms;
    }

    public void testSubsumedBy() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        BooleanResponse response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opc()));
        response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opb()));
        response = super.reasoner.answer(query);
        falseResponse(response);
    }

    public void testSubsumedByViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opc());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opb());
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetSubObjectProperties() {
        //indirect case
        GetSubObjectProperties query = new GetSubObjectProperties(getKBIRI(), opb());
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);

        assertEquals(2,response.nodes().count());
        Node<OWLObjectPropertyExpression> synset = response.iterator().next();
        assertEquals(set(opa()), asUnorderedSet(synset.entities()));
        assertEquals(set(opa(), bottomObject()), asUnorderedSet(response.entities()));
    }

    protected OWLObjectProperty bottomObject() {
        return manager.getOWLDataFactory().getOWLBottomObjectProperty();
    }

    public void testGetSubObjectPropertiesViaOWLReasoner() {
        //indirect case
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSubObjectProperties(opb(), false);

        assertEquals(2,response.nodes().count());
        Node<OWLObjectPropertyExpression> synset = response.iterator().next();
        assertEquals(set(opa()), asUnorderedSet(synset.entities()));
        assertEquals(set(opa(), bottomObject()), asUnorderedSet(response.entities()));
    }

    public void testGetDirectSubObjectProperties() {
        //direct case
        GetSubObjectProperties query = new GetSubObjectProperties(getKBIRI(), opb(), true);
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
        assertEquals(set(opa()), asUnorderedSet(response.entities()));
    }

    public void testGetDirectSubObjectPropertiesViaOWLReasoner() {
        //direct case
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSubObjectProperties(opb(), true);
        assertEquals(1,response.nodes().count());
        assertEquals(set(opa()), asUnorderedSet(response.entities()));
    }

    public void testGetSuperProperties() {
        GetSuperObjectProperties query = new GetSuperObjectProperties(getKBIRI(), opa());
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(3,response.nodes().count());
        assertEquals(set(new OWLObjectPropertyNode(opb()), new OWLObjectPropertyNode(opc()), topObject()),asUnorderedSet(response.nodes()));
    }

    protected OWLObjectPropertyNode topObject() {
        return new OWLObjectPropertyNode(manager.getOWLDataFactory().getOWLTopObjectProperty());
    }

    public void testGetSuperPropertiesViaOWLReasoner() {
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSuperObjectProperties(opa(), false);
        assertEquals(set(new OWLObjectPropertyNode(opb()), new OWLObjectPropertyNode(opc()), topObject()),asUnorderedSet(response.nodes()));
    }

    public void testGetSuperPropertiesDirect() {
        GetSuperObjectProperties query = new GetSuperObjectProperties(getKBIRI(), opa(), true);
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
    }

    public void testGetSuperPropertiesDirectViaOWLReasoner() {
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSuperObjectProperties(opa(), true);
        assertEquals(1,response.nodes().count());
    }

    public void testSubPropertyHierarchy() {
        GetSubObjectPropertyHierarchy query = new GetSubObjectPropertyHierarchy(getKBIRI());
        Hierarchy<OWLObjectPropertyExpression> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLObjectPropertyExpression>> pairs = response.getPairs();
        assertFalse(pairs.isEmpty());
        assertEquals(3, pairs.size());

        Set<HierarchyPair<OWLObjectPropertyExpression>> expectedSet = CollectionFactory.createSet();
        OWLlinkOWLObjectPropertyNode synset = new OWLlinkOWLObjectPropertyNode(getDataFactory().getOWLTopObjectProperty());
        Set<Node<OWLObjectPropertyExpression>> set = CollectionFactory.createSet();
        set.add(new OWLlinkOWLObjectPropertyNode(opc()));
        SubEntitySynsets<OWLObjectPropertyExpression> setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLlinkOWLObjectPropertyNode(opc());
        set = CollectionFactory.createSet();
        set.add(new OWLlinkOWLObjectPropertyNode(opb()));
        set.add(new OWLlinkOWLObjectPropertyNode(opd()));

        setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLlinkOWLObjectPropertyNode(opb());
        set = CollectionFactory.createSet();
        set.add(new OWLlinkOWLObjectPropertyNode(opa()));
        setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }
}
