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

package org.semanticweb.owlapi.owllink.builtin.requests;

import org.semanticweb.owlapi.owllink.Request;

/**
 * Visitor interface for requests. For OWLlink extension queries a
 * general {@link #answer(org.semanticweb.owlapi.owllink.Request) answer} method
 * exists.
 *
 * @author Olaf Noppens
 */
public interface RequestVisitor {

    /**
     * Common answer method for all queries where no specific answer method
     * exists, i.e. for all non-core queries.
     *
     * @param query query
     */
    void answer(Request<?> query);

    /** @param query query */
    void answer(Classify query);

    /** @param query query */
    void answer(CreateKB query);

    /** @param query query */
    void answer(GetAllAnnotationProperties query);

    /** @param query query */
    void answer(GetAllClasses query);

    /** @param query query */
    void answer(GetAllDataProperties query);

    /** @param query query */
    void answer(GetAllDatatypes query);

    /** @param query query */
    void answer(GetAllIndividuals query);

    /** @param query query */
    void answer(GetAllObjectProperties query);

    /** @param query query */
    void answer(GetDataPropertiesBetween query);

    /** @param query query */
    void answer(GetDataPropertiesOfLiteral query);

    /** @param query query */
    void answer(GetDataPropertiesOfSource query);

    /** @param query query */
    void answer(GetDataPropertySources query);

    /** @param query query */
    void answer(GetDataPropertyTargets query);

    /** @param query query */
    void answer(GetDescription query);

    /** @param query query */
    void answer(GetDisjointClasses query);

    /** @param query query */
    void answer(GetDisjointDataProperties query);

    /** @param query query */
    void answer(GetDifferentIndividuals query);

    /** @param query query */
    void answer(GetDisjointObjectProperties query);

    /** @param query query */
    void answer(GetEquivalentClasses query);

    /** @param query query */
    void answer(GetEquivalentDataProperties query);

    /** @param query query */
    void answer(GetSameIndividuals query);

    /** @param query query */
    void answer(GetEquivalentObjectProperties query);

    /** @param query query */
    void answer(GetFlattenedDataPropertySources query);

    /** @param query query */
    void answer(GetFlattenedDifferentIndividuals query);

    /** @param query query */
    void answer(GetFlattenedInstances query);

    /** @param query query */
    void answer(GetFlattenedObjectPropertySources query);

    /** @param query query */
    void answer(GetFlattenedObjectPropertyTargets query);

    /** @param query query */
    void answer(GetFlattenedTypes query);

    /** @param query query */
    void answer(GetInstances query);

    /** @param query query */
    void answer(GetKBLanguage query);

    /** @param query query */
    void answer(GetObjectPropertiesBetween query);

    /** @param query query */
    void answer(GetObjectPropertiesOfSource query);

    /** @param query query */
    void answer(GetObjectPropertiesOfTarget query);

    /** @param query query */
    void answer(GetObjectPropertySources query);

    /** @param query query */
    void answer(GetObjectPropertyTargets query);

    /** @param query query */
    void answer(GetPrefixes query);

    /** @param query query */
    void answer(GetSettings query);

    /** @param query query */
    void answer(GetSubClasses query);

    /** @param query query */
    void answer(GetSubClassHierarchy query);

    /** @param query query */
    void answer(GetSubDataProperties query);

    /** @param query query */
    void answer(GetSubDataPropertyHierarchy query);

    /** @param query query */
    void answer(GetSubObjectProperties query);

    /** @param query query */
    void answer(GetSubObjectPropertyHierarchy query);

    /** @param query query */
    void answer(GetSuperClasses query);

    /** @param query query */
    void answer(GetSuperDataProperties query);

    /** @param query query */
    void answer(GetSuperObjectProperties query);

    /** @param query query */
    void answer(GetTypes query);

    /** @param query query */
    void answer(IsClassSatisfiable query);

    /** @param query query */
    void answer(IsDataPropertySatisfiable query);

    /** @param query query */
    void answer(IsKBConsistentlyDeclared query);

    /** @param query query */
    void answer(IsKBSatisfiable query);

    /** @param query query */
    void answer(IsEntailed query);

    /** @param query query */
    void answer(IsEntailedDirect query);

    /** @param query query */
    void answer(IsObjectPropertySatisfiable query);

    /** @param query query */
    void answer(LoadOntologies query);

    /** @param query query */
    void answer(Realize query);

    /** @param query query */
    void answer(ReleaseKB query);

    /** @param query query */
    void answer(Set query);

    /** @param query query */
    void answer(Tell query);
}
