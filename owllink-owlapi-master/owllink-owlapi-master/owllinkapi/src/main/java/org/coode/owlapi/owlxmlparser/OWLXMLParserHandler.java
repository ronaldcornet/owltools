/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, University of Manchester
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
package org.coode.owlapi.owlxmlparser;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler which knows about OWLXML.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 13-Dec-2006
 */
public class OWLXMLParserHandler extends DefaultHandler {

    protected OWLOntologyManager owlOntologyManager;
    protected OWLOntology ontology;
    protected List<OWLElementHandler<?>> handlerStack;
    protected final Map<String, OWLElementHandlerFactory> handlerMap;
    protected final Map<String, String> prefixName2PrefixMap = new HashMap<>();
    protected Locator locator;
    protected Stack<URI> bases;
    protected OWLOntologyLoaderConfiguration configuration;

    /**
     * @param ontology
     *        ontology to parse into
     */
    public OWLXMLParserHandler(OWLOntology ontology) {
        this(ontology, null, new OWLOntologyLoaderConfiguration());
    }

    /**
     * @param ontology
     *        ontology to add to
     * @param configuration
     *        load configuration
     */
    public OWLXMLParserHandler(OWLOntology ontology,
            OWLOntologyLoaderConfiguration configuration) {
        this(ontology, null, configuration);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;
        URI base = null;
        try {
            String systemId = locator.getSystemId();
            if (systemId != null) {
                base = new URI(systemId);
            }
        } catch (URISyntaxException e) {
        	e.printStackTrace();
        }
        bases.push(base);
    }

    /**
     * @param ontology
     *        ontology to parse into
     * @param topHandler
     *        top level handler
     */
    public OWLXMLParserHandler(OWLOntology ontology,
            OWLElementHandler<?> topHandler) {
        this(ontology, topHandler, new OWLOntologyLoaderConfiguration());
    }

    /**
     * Creates an OWLXML handler with the specified top level handler. This
     * allows OWL/XML representations of axioms to be embedded in abitrary XML
     * documents e.g. DIG 2.0 documents. (The default handler behaviour expects
     * the top level element to be an Ontology element).
     * 
     * @param ontology
     *        The ontology object that the XML representation should be parsed
     *        into.
     * @param topHandler
     *        top level handler
     * @param configuration
     *        load configuration
     */
    public OWLXMLParserHandler(OWLOntology ontology,
            OWLElementHandler<?> topHandler,
            OWLOntologyLoaderConfiguration configuration) {
        owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        bases = new Stack<>();
        this.configuration = configuration;
        handlerStack = new ArrayList<>();
        prefixName2PrefixMap.put("owl:", Namespaces.OWL.toString());
        prefixName2PrefixMap.put("xsd:", Namespaces.XSD.toString());
        if (topHandler != null) {
            handlerStack.add(0, topHandler);
        }
        handlerMap = new HashMap<>();
        addFactory(new AbstractElementHandlerFactory(ONTOLOGY, OWLOntologyHandler::new));
        addFactory(new AbstractElementHandlerFactory(ANNOTATION, OWLAnnotationElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(LITERAL, OWLLiteralElementHandler::new), "Constant");
        addFactory(new AbstractElementHandlerFactory(IMPORT, OWLImportsHandler::new), "Imports");
        addFactory(new AbstractElementHandlerFactory(CLASS, OWLClassElementHandler::new), "OWLClass");
        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY, OWLAnnotationPropertyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY_DOMAIN, OWLAnnotationPropertyDomainElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY_RANGE, OWLAnnotationPropertyRangeElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SUB_ANNOTATION_PROPERTY_OF, OWLSubAnnotationPropertyOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY, OWLObjectPropertyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_INVERSE_OF, OWLInverseObjectPropertyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY, OWLDataPropertyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(NAMED_INDIVIDUAL, OWLIndividualElementHandler::new), "Individual");
        addFactory(new AbstractElementHandlerFactory(DATA_COMPLEMENT_OF, OWLDataComplementOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_ONE_OF, OWLDataOneOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATATYPE, OWLDatatypeElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATATYPE_RESTRICTION, OWLDatatypeRestrictionElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_INTERSECTION_OF, OWLDataIntersectionOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_UNION_OF, OWLDataUnionOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(FACET_RESTRICTION, OWLDatatypeFacetRestrictionElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_INTERSECTION_OF, OWLObjectIntersectionOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_UNION_OF, OWLObjectUnionOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_COMPLEMENT_OF, OWLObjectComplementOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_ONE_OF, OWLObjectOneOfElementHandler::new));
        // Object Restrictions
        addFactory(new AbstractElementHandlerFactory(OBJECT_SOME_VALUES_FROM, OWLObjectSomeValuesFromElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_ALL_VALUES_FROM, OWLObjectAllValuesFromElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_HAS_SELF, OWLObjectExistsSelfElementHandler::new), "ObjectExistsSelf");
        addFactory(new AbstractElementHandlerFactory(OBJECT_HAS_VALUE, OWLObjectHasValueElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_MIN_CARDINALITY, OWLObjectMinCardinalityElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_EXACT_CARDINALITY, OWLObjectExactCardinalityElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_MAX_CARDINALITY, OWLObjectMaxCardinalityElementHandler::new));
        // Data Restrictions
        addFactory(new AbstractElementHandlerFactory(DATA_SOME_VALUES_FROM, OWLDataSomeValuesFromElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_ALL_VALUES_FROM, OWLDataAllValuesFromElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_HAS_VALUE, OWLDataHasValueElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_MIN_CARDINALITY, OWLDataMinCardinalityElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_EXACT_CARDINALITY, OWLDataExactCardinalityElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_MAX_CARDINALITY, OWLDataMaxCardinalityElementHandler::new));
        // Axioms
        addFactory(new AbstractElementHandlerFactory(SUB_CLASS_OF, OWLSubClassAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_CLASSES, OWLEquivalentClassesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DISJOINT_CLASSES, OWLDisjointClassesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DISJOINT_UNION, OWLDisjointUnionElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(UNION_OF, OWLUnionOfElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SUB_OBJECT_PROPERTY_OF, OWLSubObjectPropertyOfAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_CHAIN, OWLSubObjectPropertyChainElementHandler::new), "SubObjectPropertyChain");
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_CHAIN, OWLSubObjectPropertyChainElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_OBJECT_PROPERTIES, OWLEquivalentObjectPropertiesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DISJOINT_OBJECT_PROPERTIES, OWLDisjointObjectPropertiesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_DOMAIN, OWLObjectPropertyDomainElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_RANGE, OWLObjectPropertyRangeAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(INVERSE_OBJECT_PROPERTIES, OWLInverseObjectPropertiesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(FUNCTIONAL_OBJECT_PROPERTY, OWLFunctionalObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, OWLInverseFunctionalObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SYMMETRIC_OBJECT_PROPERTY, OWLSymmetricObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ASYMMETRIC_OBJECT_PROPERTY, OWLAsymmetricObjectPropertyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(REFLEXIVE_OBJECT_PROPERTY, OWLReflexiveObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(IRREFLEXIVE_OBJECT_PROPERTY, OWLIrreflexiveObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(TRANSITIVE_OBJECT_PROPERTY, OWLTransitiveObjectPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SUB_DATA_PROPERTY_OF, OWLSubDataPropertyOfAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_DATA_PROPERTIES, OWLEquivalentDataPropertiesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DISJOINT_DATA_PROPERTIES, OWLDisjointDataPropertiesAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_DOMAIN, OWLDataPropertyDomainAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_RANGE, OWLDataPropertyRangeAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(FUNCTIONAL_DATA_PROPERTY, OWLFunctionalDataPropertyAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SAME_INDIVIDUAL, OWLSameIndividualsAxiomElementHandler::new), "SameIndividuals");
        addFactory(new AbstractElementHandlerFactory(DIFFERENT_INDIVIDUALS, OWLDifferentIndividualsAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(CLASS_ASSERTION, OWLClassAssertionAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_ASSERTION, OWLObjectPropertyAssertionAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(NEGATIVE_OBJECT_PROPERTY_ASSERTION, OWLNegativeObjectPropertyAssertionAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(NEGATIVE_DATA_PROPERTY_ASSERTION, OWLNegativeDataPropertyAssertionAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_ASSERTION, OWLDataPropertyAssertionAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ANNOTATION_ASSERTION, OWLAnnotationAssertionElementHandler::new));
        addFactory(new AbstractElementHandlerFactory("EntityAnnotation", LegacyEntityAnnotationElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DECLARATION, OWLDeclarationAxiomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(IRI_ELEMENT, IRIElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ABBREVIATED_IRI_ELEMENT, AbbreviatedIRIElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(ANONYMOUS_INDIVIDUAL, OWLAnonymousIndividualElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(HAS_KEY, OWLHasKeyElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATATYPE_DEFINITION, OWLDatatypeDefinitionElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DL_SAFE_RULE, SWRLRuleElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(BODY, SWRLAtomListElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(HEAD, SWRLAtomListElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(VARIABLE, SWRLVariableElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(CLASS_ATOM, SWRLClassAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_ATOM, SWRLObjectPropertyAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_ATOM, SWRLDataPropertyAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DATA_RANGE_ATOM, SWRLDataRangeAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(BUILT_IN_ATOM, SWRLBuiltInAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(DIFFERENT_INDIVIDUALS_ATOM, SWRLDifferentIndividualsAtomElementHandler::new));
        addFactory(new AbstractElementHandlerFactory(SAME_INDIVIDUAL_ATOM, SWRLSameIndividualAtomElementHandler::new));
    }

    /** @return config */
    public OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the line number that the parser is at.
     * 
     * @return A positive integer that represents the line number or -1 if the
     *         line number is not known.
     */
    public int getLineNumber() {
        if (locator != null) {
            return locator.getLineNumber();
        } else {
            return -1;
        }
    }

    /** @return column number */
    public int getColumnNumber() {
        if (locator != null) {
            return locator.getColumnNumber();
        } else {
            return -1;
        }
    }

    private Map<String, IRI> iriMap = new HashMap<>();

    /**
     * @param iriStr
     *        iri
     * @return parsed, absolute iri
     * @throws OWLParserException
     *         if an error is raised
     */
    public IRI getIRI(String iriStr) throws OWLParserException {
        try {
            IRI iri = iriMap.get(iriStr);
            if (iri == null) {
                URI uri = new URI(iriStr);
                if (!uri.isAbsolute()) {
                    URI base = getBase();
                    if (base == null) {
                        throw new OWLXMLParserException(
                                "Unable to resolve relative URI",
                                getLineNumber(), getColumnNumber());
                    }
                    iri = IRI.create(base + iriStr);
                } else {
                    iri = IRI.create(uri);
                }
                iriMap.put(iriStr, iri);
            }
            return iri;
        } catch (URISyntaxException e) {
            throw new OWLXMLParserException(e.getMessage(), getLineNumber(), getColumnNumber());
        }
    }

    private static String getNormalisedAbbreviatedIRI(String input) {
        if (input.indexOf(':') != -1) {
            return input;
        } else {
            return ":" + input;
        }
    }

    /**
     * @param abbreviatedIRI
     *        short iri
     * @return extended iri
     * @throws OWLParserException
     *         if an error is raised
     */
    public IRI getAbbreviatedIRI(String abbreviatedIRI)
            throws OWLParserException {
        String normalisedAbbreviatedIRI = getNormalisedAbbreviatedIRI(abbreviatedIRI);
        int sepIndex = normalisedAbbreviatedIRI.indexOf(':');
        String prefixName = normalisedAbbreviatedIRI.substring(0, sepIndex + 1);
        String localName = normalisedAbbreviatedIRI.substring(sepIndex + 1);
        String base = prefixName2PrefixMap.get(prefixName);
        if (base == null) {
            throw new OWLXMLParserException("Prefix name not defined: "
                    + prefixName, getLineNumber(), getColumnNumber());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        sb.append(localName);
        return getIRI(sb.toString());
    }

    /** @return prefix name to prefix */
    public Map<String, String> getPrefixName2PrefixMap() {
        return prefixName2PrefixMap;
    }

    private void addFactory(OWLElementHandlerFactory factory,
            String... legacyElementNames) {
        handlerMap.put(factory.getElementName(), factory);
        for (String elementName : legacyElementNames) {
            handlerMap.put(elementName, factory);
        }
    }

    /** @return ontology */
    public OWLOntology getOntology() {
        return ontology;
    }

    /** @return data factory */
    public OWLDataFactory getDataFactory() {
        return getOWLOntologyManager().getOWLDataFactory();
    }

    @Override
    public void startDocument() {}

    @Override
    public void endDocument() {}

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (!handlerStack.isEmpty()) {
            try {
                OWLElementHandler<?> handler = handlerStack.get(0);
                if (handler.isTextContentPossible()) {
                    handler.handleChars(ch, start, length);
                }
            } catch (OWLRuntimeException e) {
                throw new SAXException(e);
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        try {
            processXMLBase(attributes);
            if (localName.equals(OWLXMLVocabulary.PREFIX.getShortForm())) {
                String name = attributes
                        .getValue(OWLXMLVocabulary.NAME_ATTRIBUTE
                                .getShortForm());
                String iriString = attributes
                        .getValue(OWLXMLVocabulary.IRI_ATTRIBUTE.getShortForm());
                if (name != null && iriString != null) {
                    if (name.endsWith(":")) {
                        prefixName2PrefixMap.put(name, iriString);
                    } else {
                        prefixName2PrefixMap.put(name + ":", iriString);
                    }
                }
                return;
            }
            OWLElementHandlerFactory handlerFactory = handlerMap.get(localName);
            if (handlerFactory != null) {
                OWLElementHandler<?> handler = handlerFactory
                        .createHandler(this);
                if (!handlerStack.isEmpty()) {
                    OWLElementHandler<?> topElement = handlerStack.get(0);
                    handler.setParentHandler(topElement);
                }
                handlerStack.add(0, handler);
                for (int i = 0; i < attributes.getLength(); i++) {
                    handler.attribute(attributes.getLocalName(i),
                            attributes.getValue(i));
                }
                handler.startElement(localName);
            }
        } catch (OWLParserException e) {
            throw new TranslatedOWLParserException(e);
        }
    }

    protected void processXMLBase(Attributes attributes) {
        String base = attributes.getValue(Namespaces.XML.toString(), "base");
        if (base != null) {
            bases.push(URI.create(base));
        } else {
            bases.push(bases.peek());
        }
    }

    /**
     * Return the base URI for resolution of relative URIs.
     * 
     * @return base URI or null if unavailable (xml:base not present and the
     *         document locator does not provide a URI)
     */
    public URI getBase() {
        return bases.peek();
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        try {
            if (localName.equals(OWLXMLVocabulary.PREFIX.getShortForm())) {
                return;
            }
            if (!handlerStack.isEmpty()) {
                OWLElementHandler<?> handler = handlerStack.remove(0);
                handler.endElement();
            }
            bases.pop();
        } catch (OWLParserException e) {
            // Temporarily translate to a SAX parse exception
            throw new TranslatedOWLParserException(e);
        } catch (UnloadableImportException e) {
            // Temporarily translate to a SAX parse exception
            throw new TranslatedUnloadableImportException(e);
        }
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) {
        prefixName2PrefixMap.put(prefix, uri);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws IOException, SAXException {
        return super.resolveEntity(publicId, systemId);
    }

    /** @return manager */
    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }
}
