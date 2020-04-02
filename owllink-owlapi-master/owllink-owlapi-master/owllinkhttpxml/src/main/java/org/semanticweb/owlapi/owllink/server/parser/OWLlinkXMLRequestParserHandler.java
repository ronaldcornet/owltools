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

package org.semanticweb.owlapi.owllink.server.parser;

import org.coode.owlapi.owlxmlparser.OWLElementHandler;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;
import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.owllink.parser.MyOWLXMLParserHandler;
import org.semanticweb.owlapi.owllink.retraction.RetractionVocabulary;
import org.semanticweb.owlapi.owllink.retraction.server.OWLlinkRetractElementHandler;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Olaf Noppens
 * Date: 25.10.2009
 */
public class OWLlinkXMLRequestParserHandler extends MyOWLXMLParserHandler {

    protected Map<String, OWLlinkElementHandlerFactory> owllinkHandlerMap;
    protected PrefixManagerProvider prov;

    /**
     * @param prov prov 
     * @param ontology ontology 
     */
    public OWLlinkXMLRequestParserHandler(PrefixManagerProvider prov, OWLOntology ontology) {
        this(prov, ontology, null);
    }

    /**
     * @param prov prov 
     * @param ontology ontology 
     * @param topHandler topHandler 
     */
    public OWLlinkXMLRequestParserHandler(PrefixManagerProvider prov, OWLOntology ontology, OWLElementHandler<?> topHandler) {
        super(ontology, topHandler);
        owllinkHandlerMap = new HashMap<>();
        this.prov = prov;

        addFactory(new AbstractOWLlinkElementHandlerFactory(RetractionVocabulary.Retraction.getShortName(), OWLlinkRetractElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASSIFY, OWLlinkClassifyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_ANNOTATION_PROPERTIES, OWLlinkGetAllAnnotationPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_CLASSES, OWLlinkGetAllClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_DATAPROPERTIES, OWLlinkGetAllDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_DATATYPES, OWLlinkGetAllDatatypesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_INDIVIDUALS, OWLlinkGetAllIndividualsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_ALL_OBJECTPROPERTIES, OWLlinkGetAllObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CREATE_KB, OWLlinkCreateKBElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DATAPROPERTIES_OF_SOURCE, OWLlinkGetDataPropertiesOfSourceElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DATAPROPERTIES_BETWEEN, OWLlinkGetDataPropertiesBetweenElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DATAPROPERTIES_OF_LITERAL, OWLlinkGetDataPropertiesOfLiteralElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DATAPROPERTY_TARGETS, OWLlinkGetDataPropertyTargetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DATAPROPERTY_SOURCES, OWLlinkGetDataPropertySourcesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DESCRIPTION, OWLlinkGetDescriptionElemenHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DIFFERENT_INDIVIDUALS, OWLlinkGetDifferentIndividualsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DISJOINT_CLASSES, OWLlinkGetDisjointClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DISJOINT_DATAPROPERTIES, OWLlinkGetDisjointDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_DISJOINT_OBJECTPROPERTIES, OWLlinkGetDisjointObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_EQUIVALENT_CLASSES, OWLlinkGetEquivalentClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_EQUIVALENT_DATAPROPERTIES, OWLlinkGetEquivalentDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_DATAPROPERTY_SOURCES, OWLlinkGetFlattenedDataPropertySourcesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_EQUIVALENT_OBJECTPROPERTIES, OWLlinkGetEquivalentObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_KB_LANGUAGE, OWLlinkGetKBLanguageElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_DIFFERENT_INDIVIDUALS, OWLlinkGetFlattenedDifferentIndividualsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_INSTANCES, OWLlinkGetFlattenedInstancesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_OBJECTPROPERTY_TARGETS, OWLlinkGetFlattenedObjectPropertyTargetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_OBJECTPROPERTY_SOURCES, OWLlinkGetFlattendObjectPropertySourcesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_INSTANCES, OWLlinkGetInstancesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_OBJECTPROPERTY_SOURCES, OWLlinkGetObjectPropertySourcesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_OBJECTPROPERTY_TARGETS, OWLlinkGetObjectPropertyTargetsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_OBJECTPROPERTIES_BETWEEN, OWLlinkGetObjectPropertiesBetweenElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_OBJECTPROPERTIES_OF_SOURCE, OWLlinkGetObjectPropertiesOfSourceElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_OBJECTPROPERTIES_OF_TARGET, OWLlinkGetObjectPropertiesOfTargetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_PREFIXES, OWLlinkGetPrefixesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SAME_INDIVIDUALS, OWLlinkGetSameIndividualsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SETTINGS, OWLlinkGetSettingsElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBCLASSES, OWLlinkGetSubClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBCLASS_HIERARCHY, OWLlinkGetSubClassHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBOBJECTPROPERTY_HIERARCHY, OWLlinkGetSubObjectPropertyHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBDATAPROPERTIES, OWLlinkGetSubDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBOBJECTPROPERTIES, OWLlinkGetSubObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUPERCLASSES, OWLlinkGetSuperClassesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUBDATAPROPERTY_HIERARCHY, OWLlinkGetSubDataPropertyHierarchyElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUPERDATAPROPERTIES, OWLlinkGetSuperDataPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_SUPEROBJECTPROPERTIES, OWLlinkGetSuperObjectPropertiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_TYPES, OWLlinkGetTypesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.GET_FLATTENED_TYPES, OWLlinkGetFlattenedTypesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IRI_MAPPING, OWLlinkIRIMappingElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_CLASS_SATISFIABLE, OWLlinkIsClassSatisfiableElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_DATAPROPERTY_SATISFIABLE, OWLlinkIsDataPropertySatisfiableElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_KB_SATISFIABLE, OWLlinkIsKBSatisfiableElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_KB_CONSISTENTLY_DECLARED, OWLlinkIsKBConsistentlyDeclaredElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_ENTAILED, OWLlinkIsEntailedElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_ENTAILED_DIRECT, OWLlinkIsEntailedDirectElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.ONTOLOGY_IRI, OWLlinkOntologyIRIElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.IS_OBJECTPROPERTY_SATISFIABLE, OWLlinkIsObjectPropertySatisfiableElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LITERAL, OWLlinkLiteralElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIX, OWLlinkPrefixElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LOAD_ONTOLOGIES, OWLlinkLoadOntologiesElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.RELEASE_KB, OWLlinkReleaseKBElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET, OWLlinkSetElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.TELL, OWLlinkTellElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.REALIZE, OWLlinkRealizeElementHandler::new));
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.REQUEST_MESSAGE, x-> messageHandler));
    }

    OWLlinkRequestMessageElementHandler messageHandler = new OWLlinkRequestMessageElementHandler(this);

    /** @return requests */
    public List<Request<?>> getRequest() {
        return messageHandler.getOWLObject();
    }

    /**
     * @param listener listener 
     */
    public void setRequestListener(OWLLinkRequestListener listener) {
        this.messageHandler.setRequestListener(listener);
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

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            processXMLBase(attributes);

            if (Namespaces.OWL2.toString().equals(uri) || Namespaces.OWL.toString().equals(uri) || Namespaces.OWL11XML.toString().equals(uri)) {
                super.startElement(uri, localName, qName, attributes);
            } else {
                OWLlinkElementHandlerFactory handlerFactory = owllinkHandlerMap.get(localName);
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
}
