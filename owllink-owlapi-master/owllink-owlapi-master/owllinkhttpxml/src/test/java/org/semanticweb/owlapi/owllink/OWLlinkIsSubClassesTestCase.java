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
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubClassHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSuperClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIsSubClassesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();

        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(b(), c()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(d(), c()));
        return axioms;
    }

    public void testSubsumedBy() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        BooleanResponse response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(a(), c()));
        response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(d(), b()));
        response = super.reasoner.answer(query);
        falseResponse(response);
    }

    public void testSubsumedByViaOWLReasoner() {
        OWLSubClassOfAxiom axiom = getDataFactory().getOWLSubClassOfAxiom(a(), b());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubClassOfAxiom(a(), c());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubClassOfAxiom(d(), b());
        assertFalse(super.reasoner.isEntailed(axiom));

    }

    public void testGetSubClasses() {
        GetSubClasses query = new GetSubClasses(getKBIRI(), b());
        NodeSet<OWLClass> response = super.reasoner.answer(query);
        assertEquals(2,response.nodes().count());
        Set<OWLClass> flattenedClasses = asUnorderedSet(response.entities());
        assertEquals(set(a(),manager.getOWLDataFactory().getOWLNothing()), flattenedClasses);
    }

    public void testGetSubClassesViaOWLReasoner() {
        NodeSet<OWLClass> nodeSet = super.reasoner.getSubClasses(b(), false);
        assertEquals(2,nodeSet.nodes().count());

        Set<OWLClass> flattenedClasses = asUnorderedSet(nodeSet.entities());
        assertEquals(set(a(),manager.getOWLDataFactory().getOWLNothing()), flattenedClasses);
    }

    public void testGetSuperClasses() {
        GetSuperClasses query = new GetSuperClasses(getKBIRI(), a());
        SetOfClassSynsets response = super.reasoner.answer(query);
        assertEquals(3,response.nodes().count());

        query = new GetSuperClasses(getKBIRI(), a(), true);
        response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
    }

    public void testGetSuperClassesViaOWLReasoner() {
        NodeSet<OWLClass> response = super.reasoner.getSuperClasses(a(), false);
        assertEquals(3,response.nodes().count());

        response = super.reasoner.getSuperClasses(a(), true);
        assertEquals(1,response.nodes().count());
    }

    public void testClassHierarchy() {
        GetSubClassHierarchy query = new GetSubClassHierarchy(getKBIRI());
        Hierarchy<OWLClass> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLClass>> pairs = response.getPairs();
        assertEquals(3,pairs.size());

        Set<HierarchyPair<OWLClass>> expectedSet = CollectionFactory.createSet();
        Node<OWLClass> synset = new OWLClassNode(getDataFactory().getOWLThing());
        Set<Node<OWLClass>> set = CollectionFactory.createSet();
        set.add(new OWLClassNode(c()));
        SubEntitySynsets<OWLClass> setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(c());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(b()));
        set.add(new OWLClassNode(d()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(b());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(a()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }

    public void testSubClassHierarchy() {
        GetSubClassHierarchy query = new GetSubClassHierarchy(getKBIRI(), c());
        Hierarchy<OWLClass> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLClass>> pairs = response.getPairs();
        assertEquals(2,pairs.size());

        Set<HierarchyPair<OWLClass>> expectedSet = CollectionFactory.createSet();

        Node<OWLClass> synset = new OWLClassNode(c());
        Set<Node<OWLClass>> set = CollectionFactory.createSet();
        set.add(new OWLClassNode(d()));
        set.add(new OWLClassNode(b()));
        SubEntitySynsets<OWLClass> setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(b());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(a()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }
}
