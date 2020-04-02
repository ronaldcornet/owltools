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

package org.semanticweb.owlapi.owllink.renderer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;

/**
 * @author Olaf Noppens
 */
public interface OWLlinkWriter {

    /**
     * @param isRequest isRequest 
     */
    void startDocument(boolean isRequest);

    /**
     * End document.
     */
    void endDocument();

    /**
     * @param name name 
     */
    void writeStartElement(IRI name);

    /**
     * End element.
     */
    void writeEndElement();

    /**
     * @param attribute attribute 
     * @param value value 
     */
    void writeAttribute(String attribute, String value);

    /**
     * @param attribute attribute 
     * @param value value 
     */
    void writeAttribute(IRI attribute, String value);

    /**
     * @param isNegative isNegative 
     */
    void writeNegativeAttribute(boolean isNegative);

    /**
     * @param isNegative isNegative 
     */
    void writeDirectAttribute(boolean isNegative);

    /**
     * @param kb kb 
     */
    void writeKBAttribute(IRI kb);

    /**
     * @param iri iri 
     */
    void writeFullIRIAttribute(IRI iri);

    /**
     * @param object object 
     * @param KB KB 
     */
    void writeOWLObject(OWLObject object, IRI KB);

    /**
     * @param text text 
     */
    void writeTextContent(String text);

    /** @return prefix manager provider */
    PrefixManagerProvider getPrefixManagerProvider();
}
