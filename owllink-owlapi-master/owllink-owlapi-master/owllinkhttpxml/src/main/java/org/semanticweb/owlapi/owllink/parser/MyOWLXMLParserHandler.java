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

package org.semanticweb.owlapi.owllink.parser;

import org.coode.owlapi.owlxmlparser.*;
import org.semanticweb.owlapi.model.*;
import java.util.*;

/**
 * SWR*Handler: protected class constructor
 * <p>
 * This OWLXMLParserHandler is based on Matthew Horridge's implementation.
 */
public class MyOWLXMLParserHandler extends OWLXMLParserHandler {

    /**
     * Creates an OWLXML handler.
     *
     * @param ontology           The ontology that the XML representation will be parsed into.
     */
    public MyOWLXMLParserHandler(OWLOntology ontology) {
        this(ontology, null);
    }

    /**
     * Creates an OWLXML handler with the specified top level handler.  This allows OWL/XML
     * representations of axioms to be embedded in abitrary XML documents e.g. DIG 2.0 documents.
     * (The default handler behaviour expects the top level element to be an Ontology
     * element).
     *
     * @param ontology           The ontology object that the XML representation should be parsed into.
     * @param topHandler         The handler for top level elements - may be {@code null}, in which
     *                           case the parser will expect an Ontology element to be the root element.
     */
    public MyOWLXMLParserHandler(OWLOntology ontology, OWLElementHandler<?> topHandler) {
        super(ontology, topHandler);
    }

    /**
     * @param map map 
     */
    public void setPrefixName2PrefixMap(Map<String, String> map) {
        prefixName2PrefixMap.clear();
        prefixName2PrefixMap.putAll(map);
    }
}
