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

package org.semanticweb.owlapi.owllink.builtin.response;

import org.semanticweb.owlapi.owllink.Response;

/**
 * Author: Olaf Noppens
 * Date: 24.11.2009
 * @param <O> object type
 */
public interface ResponseVisitor<O> {

    /** @param response response to visit
     * @return value */
    O visit(Response response);

    /** @param response response to visit
     * @return value */
    O visit(KBResponse response);

    /** @param response response to visit
     * @return value */
    O visit(BooleanResponse response);

    /** @param response response to visit
     * @return value */
    O visit(Classes response);

    /** @param response response to visit
     * @return value */
    O visit(ClassHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(ClassSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertyHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertySynonyms response);

    /** @param response response to visit
     * @return value */
    O visit(Description response);

    /** @param response response to visit
     * @return value */
    O visit(IndividualSynonyms response);

    /** @param response response to visit
     * @return value */
    O visit(KB response);

    /** @param response response to visit
     * @return value */
    O visit(ObjectPropertyHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(ObjectPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(OK response);

    /** @param response response to visit
     * @return value */
    O visit(Prefixes response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfAnnotationProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfClasses response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfClassSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDataProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDataPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDatatypes response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfIndividuals response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfIndividualSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfLiterals response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfObjectProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfObjectPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(Settings response);

    /** @param response response to visit
     * @return value */
    O visit(StringResponse response);
}
