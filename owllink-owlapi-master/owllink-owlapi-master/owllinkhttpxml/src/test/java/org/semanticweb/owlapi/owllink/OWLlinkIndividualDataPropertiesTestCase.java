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
import org.semanticweb.owlapi.owllink.builtin.requests.*;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfDataPropertySynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfLiterals;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIndividualDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();
        axioms.add(getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(1)));
        axioms.add(getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(2)));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpp(), dpq()));
        axioms.add(getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpp(), dpr()));

        return axioms;
    }

    public void testGetDataPropertiesOfSource() throws Exception {
        GetDataPropertiesOfSource query = new GetDataPropertiesOfSource(getKBIRI(), getOWLIndividual("i"));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        Set<OWLDataProperty> expected = set(dpp(), dpq(), dpr(), topProperty()); 
        assertEquals(expected, asUnorderedSet(response.entities()));
    }

    public void testGetDataPropertiesOfTarget() throws Exception {
        GetDataPropertiesOfLiteral query = new GetDataPropertiesOfLiteral(getKBIRI(), getLiteral(2));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        Set<OWLDataProperty> expected = set(dpp(), dpq(), dpr(), topProperty()); 
        assertEquals(expected, asUnorderedSet(response.entities()));
    }

    public void testGetDataPropertiesBetween() throws Exception {
        GetDataPropertiesBetween query = new GetDataPropertiesBetween(getKBIRI(), getOWLIndividual("i"), getLiteral(1));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(set(dpp(),dpq(),dpr(),topProperty()), asUnorderedSet(response.entities()));
    }

    protected OWLDataProperty topProperty() {
        return manager.getOWLDataFactory().getOWLTopDataProperty();
    }

    public void testIsIndividualRelatedWithLiteral() throws Exception {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(1)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpr(), getOWLIndividual("i"), getLiteral(1)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(2)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpq(), getOWLIndividual("i"), getLiteral(2)));
        trueResponse(reasoner.answer(query));
    }

    public void testGetDataPropertyTargets() throws Exception {
        GetDataPropertyTargets query = new GetDataPropertyTargets(getKBIRI(), getOWLIndividual("i"), dpp());
        SetOfLiterals response = super.reasoner.answer(query);
        assertEquals(set(getLiteral(1), getLiteral(2)), response);
    }

    public void testGetDataPropertySources() throws Exception {
        GetDataPropertySources query = new GetDataPropertySources(getKBIRI(), dpp(), getLiteral(1));
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response.getFlattened());
    }

    public void testGetFlattenedDataPropertySources() throws Exception {
        GetFlattenedDataPropertySources query = new GetFlattenedDataPropertySources(getKBIRI(), dpp(), getLiteral(1));
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response);
    }
}
