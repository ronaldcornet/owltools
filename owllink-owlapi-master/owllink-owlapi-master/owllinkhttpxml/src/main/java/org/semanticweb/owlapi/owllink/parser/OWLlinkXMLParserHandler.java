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

import org.coode.owlapi.owlxmlparser.OWLElementHandler;
import org.coode.owlapi.owlxmlparser.OWLElementHandlerFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;
import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler for OWLlink Responses.
 * <p>
 * Here we also handle the prefix name mapping for abbreviated IRIs.
 */
public class OWLlinkXMLParserHandler extends MyOWLXMLParserHandler {

    protected Map<String, OWLlinkElementHandlerFactory> owllinkHandlerMap;
    protected OWLlinkResponseMessageElementHandler responseMessageHandler = new OWLlinkResponseMessageElementHandler(this);
    protected PrefixManagerProvider prov;
    Request<?>[] requests;

    /**
     * @param prov prov 
     * @param requests requests 
     * @param ontology ontology 
     */
    public OWLlinkXMLParserHandler(PrefixManagerProvider prov, Request<?>[] requests, OWLOntology ontology) {
        this(prov, requests, ontology, null);
    }

    /**
     * @param provider provider 
     * @param requests requests 
     * @param ontology ontology 
     * @param topHandler topHandler 
     */
    public OWLlinkXMLParserHandler(PrefixManagerProvider provider, Request<?>[] requests, OWLOntology ontology, OWLElementHandler<?> topHandler) {
        super(ontology, topHandler);
        this.owllinkHandlerMap = new HashMap<>();
        this.prov = provider;
        this.requests = requests;

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DESCRIPTION, OWLlinkDescriptionElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PublicKB, OWLlinkPublicKBElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUPPORTEDEXTENSION, OWLlinkSupportedExtensionElemenetHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROPERTY, OWLlinkPropertyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SETTING, OWLlinkSettingElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LITERAL, OWLlinkLiteralElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.ONEOF, OWLlinkOneOfElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LIST, OWLlinkListElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATATYPE, OWLlinkDatatypeElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROTOCOLVERSION, OWLlinkProtocolVersionElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.REASONERVERSION, OWLlinkReasonerVersionElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIXES, OWLlinkPrefixesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.KB_RESPONSE, OWLlinkKBElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OK, OWLlinkOKElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.BOOLEAN_RESPONSE, OWLlinkBooleanResponseElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.UNKNOWN_RESPONSE, OWLlinkUnknownResponseElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_ANNOTATIONPROPERTIES, OWLlinkSetOfAnnotationPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_CLASSES, OWLlinkSetOfClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATAPROPERTIES, OWLlinkSetOfDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATATYPES, OWLlinkSetOfDatatypesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_INDIVIDUALS, OWLlinkSetOfIndividualsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_OBJECTPROPERTIES, OWLlinkSetOfObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.String_RESPONSE, OWLlinkStringResponseElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_CLASS_SYNSETS, OWLlinkSetOfClassSynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASSES, OWLlinkClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SYNSET, OWLlinkClassSynsetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SYNSETS, OWLlinkClassSynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_HIERARCHY, OWLlinkClassHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SUBCLASSESPAIR, OWLlinkClassSubClassesPairElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBCLASS_SYNSETS, OWLlinkSubClassSynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_OBJECTPROPERTY_SYNSETS, OWLlinkSetOfObjectPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SYNSET, OWLlinkObjectPropertySynsetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SYNSETS, OWLlinkObjectPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_HIERARCHY, OWLlinkObjectPropertyHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SUBOBJECTPROPERTIESPAIR, OWLlinkObjectPropertySubPropertiesPairElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SUBOBJECTPROPERTIESPAIR, OWLlinkObjectPropertySubPropertiesPairElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBOBJECTPROPERTY_SYNSETS, OWLlinkSubObjectPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATAPROPERTY_SYNSETS, OWLlinkSetOfDataPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNSET, OWLlinkDataPropertySynsetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNSETS, OWLlinkDataPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNONYMS, OWLlinkDataPropertySynonymsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_HIERARCHY, OWLlinkDataPropertyHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SUBDATAPROPERTIESPAIR, OWLlinkDataPropertySubDataPropertiesPairElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SUBDATAPROPERTIESPAIR, OWLlinkDataPropertySubDataPropertiesPairElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBDATAPROPERTY_SYNSETS, OWLlinkSubDataPropertySynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_INDIVIDUALS_SYNSETS, OWLlinkSetOfIndividualSynsetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.INDIVIDUAL_SYNSET, OWLlinkIndividualSynsetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.INDIVIDUAL_SYNONYMS, OWLlinkIndividualSynonymsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_LITERALS, OWLlinkSetOfLiteralsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.RESPONSE_MESSAGE, x-> responseMessageHandler));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.ERROR, OWLlinkErrorResponseElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.KBERROR, OWLlinkKBErrorElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.NOTSUPPORTEDDATATYPEERROR, OWLlinkNotSupportedDatatypeErrorElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROFILEVIOLATIONERROR, OWLlinkProfileViolationResponseErrorExceptionElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SEMANTIC_ERROR, OWLlinkSemanticErrorElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SYNTAX_ERROR, OWLlinkSyntaxErrorElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.UNSATISFIABLEKBERROR, OWLlinkUnsatisfiableKBErrorElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIX, OWLlinkPrefixElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIXES, OWLlinkPrefixesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SETTINGS, OWLlinkSettingsElementHandler::new));
    }

    /**
     * @param factory factory 
     * @param legacyElementNames legacyElementNames 
     */
    public void addFactory(OWLlinkElementHandlerFactory factory, String... legacyElementNames) {
        this.owllinkHandlerMap.put(factory.getElementName(), factory);
        for (String elementName : legacyElementNames) {
            this.owllinkHandlerMap.put(elementName, factory);
        }
    }

    /**
     * @param factories factories 
     */
    public void addFactories(Collection<OWLlinkElementHandlerFactory> factories) {
        for (OWLlinkElementHandlerFactory factory : factories)
            this.owllinkHandlerMap.put(factory.getElementName(), factory);
    }

    /** @param index index 
     * @return request at index */
    public Request<?> getRequest(int index) {
        return this.requests[index];
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            processXMLBase(attributes);
            if (Namespaces.OWL2.toString().equals(uri) || Namespaces.OWL.toString().equals(uri) || Namespaces.OWL11XML.toString().equals(uri)) {
                super.startElement(uri, localName, qName, attributes);
            } else {
                OWLElementHandlerFactory handlerFactory = owllinkHandlerMap.get(localName);
                if (handlerFactory != null) {
                    OWLElementHandler<?> handler = handlerFactory.createHandler(this);
                    if (!handlerStack.isEmpty()) {
                        OWLElementHandler<?> topElement = handlerStack.get(0);
                        handler.setParentHandler(topElement);
                    }
                    handlerStack.add(0, handler);
                    handler.startElement(localName);

                    for (int i = 0; i < attributes.getLength(); i++) {
                        handler.attribute(attributes.getLocalName(i), attributes.getValue(i));
                    }
                }
            }
        }
        catch (OWLRuntimeException e) {
            throw new SAXException(e.getMessage() + "(Current element " + localName + ")", e);
        }
    }

    /** @return responses */
    public List<Object> getResponses() {
        return responseMessageHandler.getOWLLinkObject();
    }
}
