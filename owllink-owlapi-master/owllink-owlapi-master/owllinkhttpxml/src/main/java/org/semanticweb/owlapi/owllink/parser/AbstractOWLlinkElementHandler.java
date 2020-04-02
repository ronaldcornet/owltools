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

import org.coode.owlapi.owlxmlparser.AbstractOWLElementHandler;
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.owllink.Request;

/**
 * Author: Olaf Noppens
 * Date: 21.10.2009
 * @param <O> object type
 */
public abstract class AbstractOWLlinkElementHandler<O> extends AbstractOWLElementHandler<O> implements OWLlinkElementHandler<O> {
    OWLlinkXMLParserHandler handler;

    /** @param handler handler */
    public AbstractOWLlinkElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        this.handler = (OWLlinkXMLParserHandler) handler;
    }

    @Override
    public void handleChild(OWLlinkClassSubClassesPairElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataPropertySubDataPropertiesPairElementHandler h) {
    }

    @Override
    public void handleChild(OWLlinkObjectPropertySubPropertiesPairElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubDataPropertySynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubObjectPropertySynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubClassSynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkResponseElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkErrorElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkBooleanResponseElementHandler h) {
    }

    @Override
    public void handleChild(OWLlinkConfigurationElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataRangeElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkLiteralElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPrefixElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkProtocolVersionElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkReasonerVersionElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPublicKBElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSupportedExtensionElemenetHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkClassSynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSettingElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPropertyElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkObjectPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkIndividualSynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkResponseMessageElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public abstract O getOWLLinkObject() throws OWLXMLParserException;

    @Override
    public O getOWLObject() throws OWLXMLParserException {
        return this.getOWLLinkObject();
    }

    /** @param value value 
     * @return iri */
    public IRI getFullIRI(String value) {
        return super.getIRI(value);
    }

    @Override
    protected OWLlinkElementHandler<?> getParentHandler() {
        return (OWLlinkElementHandler<?>) super.getParentHandler();
    }

    protected Request<?> getRequest() {
        int index = handler.responseMessageHandler.getOWLLinkObject().size();
        return handler.getRequest(index);
    }
}
