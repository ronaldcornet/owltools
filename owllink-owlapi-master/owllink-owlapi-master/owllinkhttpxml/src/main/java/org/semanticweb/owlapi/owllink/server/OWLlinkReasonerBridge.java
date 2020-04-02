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

package org.semanticweb.owlapi.owllink.server;

import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpResponse;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.owllink.DefaultPrefixManagerProvider;
import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.owllink.Response;
import org.semanticweb.owlapi.owllink.builtin.requests.AbstractKBRequest;
import org.semanticweb.owlapi.owllink.builtin.requests.Classify;
import org.semanticweb.owlapi.owllink.builtin.requests.CreateKB;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllAnnotationProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllDatatypes;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDataPropertiesBetween;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDataPropertiesOfLiteral;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDataPropertiesOfSource;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDataPropertySources;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDataPropertyTargets;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDescription;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedDataPropertySources;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedInstances;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedObjectPropertySources;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedObjectPropertyTargets;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedTypes;
import org.semanticweb.owlapi.owllink.builtin.requests.GetKBLanguage;
import org.semanticweb.owlapi.owllink.builtin.requests.GetObjectPropertiesBetween;
import org.semanticweb.owlapi.owllink.builtin.requests.GetObjectPropertiesOfSource;
import org.semanticweb.owlapi.owllink.builtin.requests.GetObjectPropertiesOfTarget;
import org.semanticweb.owlapi.owllink.builtin.requests.GetObjectPropertySources;
import org.semanticweb.owlapi.owllink.builtin.requests.GetObjectPropertyTargets;
import org.semanticweb.owlapi.owllink.builtin.requests.GetPrefixes;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSameIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSettings;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubClassHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubDataPropertyHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectPropertyHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.IRIMapping;
import org.semanticweb.owlapi.owllink.builtin.requests.IsClassSatisfiable;
import org.semanticweb.owlapi.owllink.builtin.requests.IsDataPropertySatisfiable;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailedDirect;
import org.semanticweb.owlapi.owllink.builtin.requests.IsKBConsistentlyDeclared;
import org.semanticweb.owlapi.owllink.builtin.requests.IsKBSatisfiable;
import org.semanticweb.owlapi.owllink.builtin.requests.IsObjectPropertySatisfiable;
import org.semanticweb.owlapi.owllink.builtin.requests.LoadOntologies;
import org.semanticweb.owlapi.owllink.builtin.requests.Realize;
import org.semanticweb.owlapi.owllink.builtin.requests.ReleaseKB;
import org.semanticweb.owlapi.owllink.builtin.requests.RequestVisitor;
import org.semanticweb.owlapi.owllink.builtin.requests.Tell;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.owllink.retraction.RetractRequest;
import org.semanticweb.owlapi.owllink.server.parser.OWLlinkXMLRequestParserHandler;
import org.semanticweb.owlapi.owllink.server.renderer.OWLlinkXMLResponseRenderer;
import org.semanticweb.owlapi.owllink.server.response.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.impl.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The {@code OWLlinkReasonerBridge} mediates between OWLlink and an implementation of OWLReasoner.
 * It supports the core OWLlink queries (as can be simulated via the OWL API) as well as the retraction extension.
 * <p>
 * Author: Olaf Noppens
 * Date: 25.10.2009
 */
public class OWLlinkReasonerBridge implements RequestVisitor {

    protected static final Logger LOGGER=LoggerFactory.getLogger(OWLlinkReasonerBridge.class);

    protected OWLOntologyManager manager;
    protected OWLDataFactory df;
    protected OWLReasonerFactory factory;
    private Map<IRI, OWLReasoner> reasonersByKB;
    private OWLlinkReasonerConfiguration reasonerConfiguration;
    private Response response;
    final OWLObjectProperty topObjectProperty;
    final OWLObjectProperty bottomObjectProperty;
    final OWLDataProperty topDataProperty;
    final OWLDataProperty bottomDataProperty;
    private Map<IRI, String> kbNameByIRI;
    private Map<IRI, AbstractOWLlinkReasonerConfiguration> configurationsByKB;
    private Map<IRI, Stack<String>> warningsByReasoners;

    /** Blockable prefix manager provider. */
    public static class BlockablePrefixManagerProvider extends DefaultPrefixManagerProvider {
        private Set<IRI> blockedKBs = CollectionFactory.createSet();

        /**
         * @param kb kb 
         * @param isBlocked isBlocked 
         */
        public void setBlocked(IRI kb, boolean isBlocked) {
            if (isBlocked)
                blockedKBs.add(kb);
            else
                blockedKBs.remove(kb);
        }

        @Override
        public boolean contains(IRI knowledgeBase) {
            if (blockedKBs.contains(knowledgeBase))
                return false;
            return super.contains(knowledgeBase);
        }

        @Override
        public PrefixManager getPrefixes(IRI knowledgeBase) {
            if (blockedKBs.contains(knowledgeBase)) return null;
            return super.getPrefixes(knowledgeBase);
        }

        @Override
        public void removePrefixes(IRI knowledgeBase) {
            super.removePrefixes(knowledgeBase);
            blockedKBs.remove(knowledgeBase);
        }
    }

    BlockablePrefixManagerProvider prov = new BlockablePrefixManagerProvider();

    /**
     * @param factory factory 
     * @param configuration configuration 
     */
    public OWLlinkReasonerBridge(OWLReasonerFactory factory, OWLlinkReasonerConfiguration configuration) {
        this.reasonersByKB = CollectionFactory.createMap();
        this.manager = OWLManager.createOWLOntologyManager();
        this.df=manager.getOWLDataFactory();
        this.factory = factory;
        this.reasonerConfiguration = configuration;
        this.topObjectProperty = manager.getOWLDataFactory().getOWLTopObjectProperty();
        this.bottomObjectProperty = manager.getOWLDataFactory().getOWLBottomObjectProperty();
        this.topDataProperty = manager.getOWLDataFactory().getOWLTopDataProperty();
        this.bottomDataProperty = manager.getOWLDataFactory().getOWLBottomDataProperty();
        this.kbNameByIRI = CollectionFactory.createMap();
        this.configurationsByKB = CollectionFactory.createMap();
        this.warningsByReasoners = CollectionFactory.createMap();
    }

    /**
     * @param request request 
     * @param httpResponse httpResponse 
     * @throws IOException IOException 
     */
    public final synchronized void process(HttpRequest request, HttpResponse httpResponse) throws IOException {
        String field = request.getField("Accept-Encoding");
        boolean clientAcceptGzip = false;
        boolean clientContentIsGzip = false;
        if (field != null) {
            StringTokenizer tokenizer = new StringTokenizer(field, ",");
            while (tokenizer.hasMoreTokens()) {
                if ("gzip".equals(tokenizer.nextToken())) {
                    //we can gzip our result
                    clientAcceptGzip = true;
                    break;
                }
            }
        }
        field = request.getField("content-encoding");
        if (field != null) {
            StringTokenizer tokenizer = new StringTokenizer(field, ",");
            while (tokenizer.hasMoreTokens()) {
                if ("gzip".equals(tokenizer.nextToken())) {
                    //inputStream must be a GZIPInputStream
                    clientContentIsGzip = true;
                    break;
                }
            }
        }
        httpResponse.setField("Accept-Encoding", "gzip");
        this.process(clientContentIsGzip ? new GZIPInputStream(httpResponse.getInputStream()) : httpResponse.getInputStream(), httpResponse.getOutputStream(), httpResponse, clientAcceptGzip);
    }

    /**
     * @param in in 
     * @param out out 
     * @param httpResponse httpResponse 
     * @param zipContentIfAppropriate zipContentIfAppropriate 
     * @return zip content if appropriate
     */
    public final synchronized boolean process(InputStream in, OutputStream out, HttpResponse httpResponse, boolean zipContentIfAppropriate) {
        try {
            SAXParserFactory f = SAXParserFactory.newInstance();

            f.setNamespaceAware(true);
            SAXParser parser = f.newSAXParser();
            final OWLlinkXMLRequestParserHandler handler = new OWLlinkXMLRequestParserHandler(prov, manager.createOntology());
            final List<Response> responses = new ArrayList<>();

            handler.setRequestListener(request -> listener(responses, request));
            parser.parse(in, handler);

            boolean useCompression = false;
            if (responses.size() > 20 && zipContentIfAppropriate) {
                useCompression = true;
                httpResponse.setField("content-encoding", "gzip");
            }
            GZIPOutputStream gzipOut = useCompression ? new GZIPOutputStream(out) : null;
            try {
            OutputStreamWriter writer = new OutputStreamWriter(gzipOut == null ? out : gzipOut);
            OWLlinkXMLResponseRenderer renderer = new OWLlinkXMLResponseRenderer();
            List<Request<?>> requests = handler.getRequest();
            renderer.render(new PrintWriter(writer), prov, requests, responses);
            writer.flush();
            if (gzipOut != null)
                gzipOut.finish();
            }finally {
                if (gzipOut != null)
                    gzipOut.close();
            }
            return zipContentIfAppropriate;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void listener(final List<Response> responses, Request<?> request) {
        try {
            request.accept(OWLlinkReasonerBridge.this);
        } catch (Exception e) {
            handle(e);
        }
        responses.add(getResponse());
    }

    /**
     * @param kb kb 
     * @return reasoner
     * @throws KBException KBException 
     */
    @Nonnull
    public final OWLReasoner getReasoner(IRI kb) throws KBException {
        final OWLReasoner reasoner = this.reasonersByKB.get(kb);
        if (reasoner == null)
            throw new KBException("KB " + kb.toString() + " does not exist!");
        return reasoner;
    }

    /**
     * @param kb kb 
     * @param createIfNull createIfNull 
     * @return configuration
     * @throws KBException KBException 
     */
    public final OWLlinkReasonerConfiguration getReasonerConfiguration(IRI kb, boolean createIfNull) throws KBException {
        OWLlinkReasonerConfiguration configuration = this.configurationsByKB.get(kb);
        if (configuration == null && createIfNull) {
            configuration = new AbstractOWLlinkReasonerConfiguration(this.reasonerConfiguration);
        }
        return configuration;
    }

    /**
     * @param reasoner reasoner 
     * @param warning warning 
     */
    public final void logWarning(IRI reasoner, String warning) {
        Stack<String> warnings = this.warningsByReasoners.get(reasoner);
        if (warnings == null) {
            warnings = new Stack<>();
        }
        warnings.push(warning);
    }

    protected String getWarning(IRI reasoner) {
        Stack<String> warnings = this.warningsByReasoners.get(reasoner);
        if (warnings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : warnings)
            sb.append(s);
        this.warningsByReasoners.remove(reasoner);
        return sb.toString();
    }

    class KBException extends RuntimeException {

        public KBException(String message) {
            super(message);
        }
    }

    protected ErrorResponse handle(Exception e) {
        if (e instanceof InconsistentOntologyException) {
            this.response = new UnsatisfiableKBErrorResponseImpl(e.getMessage() == null ? e.toString() : e.getMessage());
        } else if (e instanceof AxiomNotInProfileException) {
            StringBuilder errorString = new StringBuilder();
            if (((AxiomNotInProfileException) e).getAxiom() != null)
                errorString.append("axiom: " + ((AxiomNotInProfileException) e).getAxiom());
            if (((AxiomNotInProfileException) e).getProfile() != null)
                errorString.append("profile: " + ((AxiomNotInProfileException) e).getProfile().getFragment());
            this.response = new ProfileViolationErrorResponseImpl(errorString.toString().isEmpty() ? e.toString() : errorString.toString());
        } else if (e instanceof KBException) {
            this.response = new KBErrorResponseImpl(e.getMessage() == null ? e.toString() : e.getMessage());
        } else
            this.response = new ErrorResponseImpl(e.getMessage() == null ? e.toString() : e.getMessage());
        return (ErrorResponse) this.response;
    }

    protected ErrorResponse handleReasonerException(Exception e) {
        if (e instanceof KBException) {
            this.response = new KBErrorResponseImpl(e.getMessage() == null ? e.toString() : e.getMessage());
        } else
            this.response = new ErrorResponseImpl(factory.getReasonerName() + ": " + (e.getMessage() == null ? e.toString() : e.getMessage()));
        return (ErrorResponse) this.response;
    }

    @Override
    public void answer(Classify query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        this.response = new OKImpl();
    }

    @Override
    public void answer(Realize query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS);
        this.response = new OKImpl();
    }

    @Override
    public void answer(CreateKB query) {
        IRI kb = query.getKB();
        if (kb != null) {
            OWLReasoner reasoner = this.reasonersByKB.get(query.getKB());
            if (reasoner != null)
                throw new OWLlinkErrorResponseException("KB " + query.getKB() + " already exists!");
        } else
            kb = IRI.create("http://owllink.org#" + getClass().getName() + ".kb" + System.currentTimeMillis());
        OWLOntology ontology = null;
        try {
            ontology = manager.createOntology(kb);
        } catch (OWLOntologyCreationException e) {
            LOGGER.error("Ontology creation failed", e);
            return;
        }
        AbstractOWLlinkReasonerConfiguration configuration = (AbstractOWLlinkReasonerConfiguration) getReasonerConfiguration(kb, true);
        this.configurationsByKB.put(kb, configuration);
        OWLReasoner reasoner;
        if (configuration.getOWLReasonerConfiguration() == null)
            reasoner = factory.createNonBufferingReasoner(ontology);
        else
            reasoner = factory.createNonBufferingReasoner(ontology, configuration.getOWLReasonerConfiguration());

        if (reasoner instanceof OWLOntologyChangeListener) {
            //because some reasoners (FaCt++ for example) do not do this task on their own....:-(
             manager.removeOntologyChangeListener((OWLOntologyChangeListener) reasoner);
             manager.addOntologyChangeListener((OWLOntologyChangeListener) reasoner);
        }
        DefaultPrefixManager pm = new DefaultPrefixManager();
        pm.clear();
        pm.setPrefix("owl:", Namespaces.OWL.toString());
        pm.setPrefix("xsd:", Namespaces.XSD.toString());
        pm.setPrefix("rdf:", Namespaces.RDF.toString());
        pm.setPrefix("rdfs:", Namespaces.RDFS.toString());

        Map<String, String> map = CollectionFactory.createMap();
        Map<String, String> prefixes = query.getPrefixes();
        if (prefixes != null) {
            for (Map.Entry<String, String> prefix : prefixes.entrySet()) {
                if (!prefix.getKey().endsWith(":"))
                    map.put(prefix.getKey() + ":", prefix.getValue().toString());
                else
                    map.put(prefix.getKey(), prefix.getValue().toString());
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet())
            pm.setPrefix(entry.getKey(), entry.getValue());
        prov.putPrefixes(kb, pm);

        if (query.getName() != null) {
            this.kbNameByIRI.put(kb, query.getName());
        }

        this.reasonersByKB.put(kb, reasoner);
        this.response = new KBImpl(kb);
    }

    private static <T> List<T> l(Function<Imports, Stream<T>> s) {
        return asList(s.apply(Imports.INCLUDED));
    }

    private static <T> List<T> l(Function<Imports, Stream<? extends T>> s, Class<T> t) {
        return asList(s.apply(Imports.INCLUDED), t);
    }

    private static <T> Set<T> s(Function<Imports, Stream<T>> s) {
        return asUnorderedSet(s.apply(Imports.INCLUDED));
    }

    private static <T> Set<T> s(Function<Imports, Stream<? extends T>> s, Class<T> t) {
        return asUnorderedSet(s.apply(Imports.INCLUDED), t);
    }

    protected OWLOntology root(AbstractKBRequest<?> query) {
        return getReasoner(query.getKB()).getRootOntology();
    }

    @Override
    public void answer(GetAllAnnotationProperties query) {
        this.response = new SetOfAnnotationPropertiesImpl(l(root(query)::annotationPropertiesInSignature), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetAllClasses query) {
        this.response = new SetOfClassesImpl(l(root(query)::classesInSignature));
    }

    @Override
    public void answer(GetAllDataProperties query) {
        this.response = new SetOfDataPropertiesImpl(l(root(query)::dataPropertiesInSignature));
    }

    @Override
    public void answer(GetAllDatatypes query) {
        this.response = new SetOfDatatypesImpl(l(root(query)::datatypesInSignature));
    }

    @Override
    public void answer(GetAllIndividuals query) {
        this.response = new SetOfIndividualsImpl(l(root(query)::individualsInSignature, OWLIndividual.class));
    }

    @Override
    public void answer(GetAllObjectProperties query) {
        this.response = new SetOfObjectPropertiesImpl(l(root(query)::objectPropertiesInSignature, OWLObjectPropertyExpression.class));
    }

    @Override
    public void answer(GetDataPropertiesBetween query) {
        if (query.getSourceIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous source individuals are not allowed");
            return;
        }
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) in GetDataPropertiesBetween is not supported");
            return;
        }

        OWLReasoner reasoner = getReasoner(query.getKB());
        Set<OWLDataProperty> properties = CollectionFactory.createSet();
        Set<Node<OWLDataProperty>> synsetSet = CollectionFactory.createSet();
        boolean topConsidered = false;
        for (OWLDataProperty property : getAllDataProperties(query.getKB())) {
            if (properties.contains(property)) continue;
            Set<OWLLiteral> literals =
                    reasoner.getDataPropertyValues(query.getSourceIndividual().asOWLNamedIndividual(), property);
            if (literals.contains(query.getTargetValue())) {
                properties.add(property);
                Node<OWLDataProperty> equis = reasoner.getEquivalentDataProperties(property);
                if (!topConsidered)
                    topConsidered = equis.contains(topDataProperty);
                synsetSet.add(equis);
            }
        }

        if (!topConsidered)

        {
            synsetSet.add(new OWLDataPropertyNode(this.topDataProperty));
            this.response = new SetOfDataPropertySynsetsImpl(synsetSet, "Equivalents to TopDataProperty not considered");
        } else this.response = new SetOfDataPropertySynsetsImpl(synsetSet, getWarning(query.getKB()));
    }

    protected Set<OWLClass> getAllClasses(IRI kb) {
        return s(root(kb)::classesInSignature);
    }

    protected Set<OWLDataProperty> getAllDataProperties(IRI kb) {
        return s(root(kb)::dataPropertiesInSignature);
    }

    protected Set<OWLObjectProperty> getAllObjectProperties(IRI kb) {
        return s(root(kb)::objectPropertiesInSignature);
    }

    protected Set<OWLIndividual> getAllIndividuals(IRI kb) {
        return s(root(kb)::individualsInSignature, OWLIndividual.class);
    }

    protected OWLOntology root(IRI kb) {
        return getReasoner(kb).getRootOntology();
    }

    @Override
    public void answer(GetDataPropertiesOfLiteral query) {
        if (query.isNegative())
            this.response = new ErrorResponseImpl("(negative=true) in GetDataPropertiesOfLiteral is not supported");
        else {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Set<OWLDataProperty> allProps = getAllDataProperties(query.getKB());
            Set<OWLIndividual> allIndis = getAllIndividuals(query.getKB());
            boolean topConsidered = false;
            {
                Set<Node<OWLDataProperty>> answerNodeSet = CollectionFactory.createSet();
                Set<Node<OWLDataProperty>> setOfNodes = CollectionFactory.createSet();
                while (!allProps.isEmpty()) {
                    OWLDataProperty prop = allProps.iterator().next();
                    allProps.remove(prop);
                    Node<OWLDataProperty> equivalents = reasoner.getEquivalentDataProperties(prop);
                    if (equivalents.getSize() > 0) { //should always be the case!
                        setOfNodes.add(equivalents);
                        equivalents.entities().forEach(allProps::remove);
                    }
                }
                for (OWLIndividual indi : allIndis) {
                    if (indi.isAnonymous()) continue;
                    for (Node<OWLDataProperty> node : setOfNodes) {
                        if (reasoner.getDataPropertyValues(indi.asOWLNamedIndividual(), node.getRepresentativeElement()).contains(query.getTargetValue())) {
                            answerNodeSet.add(node);
                        }
                        if (!topConsidered)
                            topConsidered = node.isBottomNode();
                    }
                }
                if (!topConsidered) {
                    answerNodeSet.add(reasoner.getTopDataPropertyNode());
                }
                this.response = new SetOfDataPropertySynsetsImpl(answerNodeSet, getWarning(query.getKB()));
            }
        }
    }

    @Override
    public void answer(GetDataPropertiesOfSource query) {
        if (query.isNegative())
            this.response = new ErrorResponseImpl("(negative=true) in GetDataPropertiesOfSource is not supported");
        else if (query.getSourceIndividual().isAnonymous())
            this.response = new ErrorResponseImpl("anonymous individuals are not supported");
        else {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Set<OWLDataProperty> allProps = getAllDataProperties(query.getKB());
            Set<Node<OWLDataProperty>> answerNodeSet = CollectionFactory.createSet();
            Set<Node<OWLDataProperty>> setOfNodes = CollectionFactory.createSet();
            boolean topConsidered = false;
            while (!allProps.isEmpty()) {
                OWLDataProperty prop = allProps.iterator().next();
                allProps.remove(prop);
                Node<OWLDataProperty> equivalents = reasoner.getEquivalentDataProperties(prop);
                if (equivalents.getSize() > 0) { //should always be the case!
                    setOfNodes.add(equivalents);
                    equivalents.forEach(allProps::remove);
                }
                for (Node<OWLDataProperty> node : setOfNodes) {
                    Set<OWLLiteral> literals = reasoner.getDataPropertyValues(
                            query.getSourceIndividual().asOWLNamedIndividual(), node.getRepresentativeElement());
                    if (literals.size() > 0) {
                        answerNodeSet.add(node);
                        if (!topConsidered)
                            topConsidered = node.contains(this.topDataProperty);
                    }
                }
                if (!topConsidered)
                    answerNodeSet.add(reasoner.getTopDataPropertyNode());
                this.response = new SetOfDataPropertySynsetsImpl(answerNodeSet, getWarning(query.getKB()));
            }
        }
    }

    @Override
    public void answer(GetDataPropertySources query) {
        if (query.isNegative())
            this.response = new ErrorResponseImpl("(negative=true) in GetDataPropertiesOfSource is not supported");
        else {
            String warning = null;
            OWLReasoner reasoner = getReasoner(query.getKB());
            Set<IndividualSynset> indis = CollectionFactory.createSet();
            Set<OWLIndividual> individuals = getAllIndividuals(query.getKB());
            while (individuals.size() > 0) {
                OWLIndividual individual = individuals.iterator().next();
                individuals.remove(individual);
                if (individual.isAnonymous()) continue;
                if (reasoner.getDataPropertyValues(individual.asOWLNamedIndividual(),
                        query.getOWLProperty()).contains(query.getTargetValue())) {
                    try {
                        Node<OWLNamedIndividual> node = reasoner.getSameIndividuals(individual.asOWLNamedIndividual());
                        IndividualSynset synset = new IndividualSynsetImpl(node);
                        indis.add(synset);
                        node.entities().forEach(individuals::remove);
                    } catch (Exception e) {
                        warning = "Synonymous individuals are not considered ["+e.getMessage()+"]";
                        Node<OWLNamedIndividual> node = new OWLNamedIndividualNode(individual.asOWLNamedIndividual());
                        IndividualSynset synset = new IndividualSynsetImpl(node);
                        indis.add(synset);
                        node.entities().forEach(individuals::remove);
                    }
                }
            }
            this.response = new SetOfIndividualSynsetsImpl(indis, warning);
        }
    }

    @Override
    public void answer(GetDataPropertyTargets query) {
        if (query.getSourceIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous source individual is not supported");
            return;
        }
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Set<OWLLiteral> literals = reasoner.getDataPropertyValues(
                    query.getSourceIndividual().asOWLNamedIndividual(), query.getOWLProperty());
            if (!literals.isEmpty())
                this.response = new SetOfLiteralsImpl(literals, getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(GetDescription query) {
        ProtocolVersion pVersion = new ProtocolVersionImpl(1, 0);
        Set<PublicKB> publicKBs = CollectionFactory.createSet();
        for (Map.Entry<IRI, String> entry : kbNameByIRI.entrySet()) {
            publicKBs.add(new PublicKBImpl(entry.getKey(), entry.getValue()));
        }
        Description description = new DescriptionImpl(this.factory.getReasonerName() + "-OWLlink",
                this.reasonerConfiguration.getConfigurations(), this.reasonerConfiguration.getReasonerVersion(),
                pVersion, Collections.<IRI>singleton(RetractRequest.EXTENSION_IRI), publicKBs);
        this.response = description;
    }

    @Override
    public void answer(GetDisjointClasses query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLClass> nodeSet = reasoner.getDisjointClasses(query.getObject());
        this.response = new ClassSynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    protected <T extends OWLObject> Set<Node<T>> s(NodeSet<T> nodeSet) {
        return asUnorderedSet(nodeSet.nodes());
    }

    @Override
    public void answer(GetDisjointDataProperties query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLDataProperty> nodeSet = reasoner.getDisjointDataProperties(query.getOWLProperty());
        this.response = new DataPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetDifferentIndividuals query) {
        if (query.getIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLNamedIndividual> nodeSet = reasoner.getDifferentIndividuals(
                query.getIndividual().asOWLNamedIndividual());

        if (reasoner.getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_NAME) {
            nodeSet = computeSynonyms(nodeSet, reasoner, query.getKB());
        }

        this.response = new SetOfIndividualSynsetsImpl(convertTo(nodeSet), getWarning(query.getKB()));
    }

    protected Set<IndividualSynset> convertTo(NodeSet<OWLNamedIndividual> nodeSet) {
        Set<IndividualSynset> synonymsets = new HashSet<>();

        for (Node<OWLNamedIndividual> node : nodeSet) {
            synonymsets.add(new IndividualSynsetImpl(asList(node.entities(), OWLIndividual.class)));
        }

        return synonymsets;
    }

    protected IndividualSynonyms convertTo(Node<OWLNamedIndividual> node) {
        return new IndividualSynonymsImpl(asList(node.entities(), OWLIndividual.class));
    }

    @Override
    public void answer(GetDisjointObjectProperties query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLObjectPropertyExpression> nodeSet = reasoner.getDisjointObjectProperties(query.getOWLProperty());
        this.response = new ObjectPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentClasses query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        Node<OWLClass> node = reasoner.getEquivalentClasses(query.getObject());
        this.response = new SetOfClassesImpl(asList(node.entities()), getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentDataProperties query) {
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Node<OWLDataProperty> node = reasoner.getEquivalentDataProperties(query.getObject());
            this.response = new DataPropertySynonymsImpl(asUnorderedSet(node.entities()), getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(GetSameIndividuals query) {
        if (query.getObject().isAnonymous())
            this.response = new ErrorResponseImpl("Anonymous Individuals are not supported");
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            this.response = convertTo(reasoner.getSameIndividuals(query.getIndividual().asOWLNamedIndividual()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentObjectProperties query) {
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Node<OWLObjectPropertyExpression> props = reasoner.getEquivalentObjectProperties(query.getObject());
            this.response = new SetOfObjectPropertiesImpl(asList(props.entities()), getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(GetFlattenedDataPropertySources query) {
        if (query.isNegative())
            this.response = new ErrorResponseImpl("(negative=true) in GetDataPropertiesOfSource is not supported");
        else {
            try {
                OWLReasoner reasoner = getReasoner(query.getKB());
                Set<OWLIndividual> indis = CollectionFactory.createSet();
                for (OWLIndividual individual : getAllIndividuals(query.getKB())) {
                    if (individual.isAnonymous()) continue;
                    if (reasoner.getDataPropertyValues(individual.asOWLNamedIndividual(),
                            query.getOWLProperty()).contains(query.getTargetValue())) {
                        indis.add(individual);
                    }
                }
                this.response = new SetOfIndividualsImpl(indis, getWarning(query.getKB()));
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    @Override
    public void answer(GetFlattenedDifferentIndividuals query) {
        if (query.getIndividual().isAnonymous())
            this.response = new ErrorResponseImpl("Anonymous individiduals are not supported");
        else {
            OWLReasoner reasoner = getReasoner(query.getKB());
            NodeSet<OWLNamedIndividual> set = reasoner.getDifferentIndividuals(query.getIndividual().asOWLNamedIndividual());
            this.response = new SetOfIndividualsImpl(set, getWarning(query.getKB()));
        }
    }

    @Override
    public void answer(GetFlattenedInstances query) {
        org.semanticweb.owlapi.owllink.builtin.requests.GetInstances subQuery = new
                org.semanticweb.owlapi.owllink.builtin.requests.GetInstances(query.getKB(),
                query.getClassExpression(), query.isDirect());
        answer(subQuery);
        this.response = new SetOfIndividualsImpl(((SetOfIndividualSynsets) this.response).getFlattened(), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetFlattenedObjectPropertySources query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getOWLIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            Set<OWLIndividual> setOfIndis = CollectionFactory.createSet();
            for (OWLIndividual indi : getAllIndividuals(query.getKB())) {
                NodeSet<OWLNamedIndividual> targets = reasoner.getObjectPropertyValues(indi.asOWLNamedIndividual(),
                        query.getOWLProperty());
                if (targets.containsEntity(query.getOWLIndividual().asOWLNamedIndividual())) {
                    setOfIndis.add(indi);
                }
            }
            this.response = new SetOfIndividualsImpl(setOfIndis, getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }

    }

    @Override
    public void answer(GetFlattenedObjectPropertyTargets query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getOWLIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        this.response = new SetOfIndividualsImpl(reasoner.getObjectPropertyValues(
                query.getOWLIndividual().asOWLNamedIndividual(), query.getOWLProperty()), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetFlattenedTypes query) {
        org.semanticweb.owlapi.owllink.builtin.requests.GetTypes subQuery =
                new org.semanticweb.owlapi.owllink.builtin.requests.GetTypes(query.getKB(), query.getIndividual(),
                        query.isDirect());
        answer(subQuery);
        ClassSynsets subResponse = (ClassSynsets) this.response;
        this.response = new ClassesImpl(asList(subResponse.entities()), subResponse.getWarning());
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetInstances query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLNamedIndividual> nodeSet = reasoner.getInstances(query.getClassExpression(), query.isDirect());
        if (reasoner.getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_NAME) {
            nodeSet = computeSynonyms(nodeSet, reasoner, query.getKB());
        }
        this.response = new SetOfIndividualSynsetsImpl(convertTo(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetKBLanguage query) {
        this.response = new ErrorResponseImpl("GetKBLanguage is not supported");

    }

    @Override
    public void answer(GetObjectPropertiesBetween query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getSourceIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous source individuals are not supported");
            return;
        }
        if (query.getTargetIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous target individuals are not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        Set<OWLObjectPropertyExpression> properties = CollectionFactory.createSet();
        Set<Node<OWLObjectPropertyExpression>> synsetSet = CollectionFactory.createSet();
        boolean topConsidered = false;
        for (OWLObjectPropertyExpression property : getAllObjectProperties(query.getKB())) {
            if (properties.contains(property)) continue;
            NodeSet<OWLNamedIndividual> literals =
                    reasoner.getObjectPropertyValues(query.getSourceIndividual().asOWLNamedIndividual(), property);
            if (literals.containsEntity(query.getTargetIndividual().asOWLNamedIndividual())) {
                properties.add(property);
                Node<OWLObjectPropertyExpression> node = reasoner.getEquivalentObjectProperties(property);
                if (!topConsidered) {
                    topConsidered = node.contains(this.topObjectProperty);
                }
                synsetSet.add(node);
            }
        }
        if (!topConsidered) {
            OWLlinkOWLObjectPropertyNode node = new OWLlinkOWLObjectPropertyNode(this.topObjectProperty);
            synsetSet.add(node);
            this.response = new SetOfObjectPropertySynsetsImpl(synsetSet, "Equivalents to TOP not considered");
        } else
            this.response = new SetOfObjectPropertySynsetsImpl(synsetSet, getWarning(query.getKB()));
    }

    private Set<Node<OWLObjectPropertyExpression>> getAllEquivalentObjectProperties(IRI kb) {
        OWLReasoner reasoner = getReasoner(kb);
        Set<Node<OWLObjectPropertyExpression>> setOfNodes = CollectionFactory.createSet();
        Set<OWLObjectProperty> properties = getAllObjectProperties(kb);
        while (!properties.isEmpty()) {
            OWLObjectProperty property = properties.iterator().next();
            properties.remove(property);
            Node<OWLObjectPropertyExpression> node = reasoner.getEquivalentObjectProperties(property);
            setOfNodes.add(node);
            node.entities().forEach(properties::remove);
        }
        return setOfNodes;
    }

    @Override
    public void answer(GetObjectPropertiesOfSource query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        if (query.getIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        Set<Node<OWLObjectPropertyExpression>> synsets = CollectionFactory.createSet();
        Set<Node<OWLObjectPropertyExpression>> setOfNodes = getAllEquivalentObjectProperties(query.getKB());
        boolean topConsidered = false;

        final OWLNamedIndividual individual = query.getIndividual().asOWLNamedIndividual();
        for (Node<OWLObjectPropertyExpression> propertyNode : setOfNodes) {
            NodeSet<OWLNamedIndividual> nodeSet = reasoner.getObjectPropertyValues(individual, propertyNode.getRepresentativeElement());
            if (!nodeSet.isEmpty()) {
                synsets.add(propertyNode);
                if (!topConsidered) {
                    topConsidered = propertyNode.contains(this.topObjectProperty);
                }
            }
        }
        if (!topConsidered) {
            synsets.add(reasoner.getEquivalentObjectProperties(this.topObjectProperty));
        }
        this.response = new SetOfObjectPropertySynsetsImpl(synsets, getWarning(query.getKB()));
    }

    @Override
    public void answer(GetObjectPropertiesOfTarget query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        final OWLReasoner reasoner = getReasoner(query.getKB());
        Set<Node<OWLObjectPropertyExpression>> found = CollectionFactory.createSet();
        Set<Node<OWLObjectPropertyExpression>> props = getAllEquivalentObjectProperties(query.getKB());
        boolean topConsidered = false;

        for (OWLIndividual indi : getAllIndividuals(query.getKB())) {
            if (indi.isAnonymous()) continue;
            for (Node<OWLObjectPropertyExpression> prop : props) {
                NodeSet<OWLNamedIndividual> nodeSet = reasoner.getObjectPropertyValues(indi.asOWLNamedIndividual(),
                        prop.getRepresentativeElement());
                if (nodeSet.containsEntity(query.getIndividual().asOWLNamedIndividual())) {
                    found.add(prop);
                    if (!topConsidered) {
                        topConsidered = prop.contains(this.topObjectProperty);
                    }
                }
            }
        }
        if (!topConsidered) {
            found.add(reasoner.getEquivalentObjectProperties(this.topObjectProperty));
        }
        this.response = new SetOfObjectPropertySynsetsImpl(found, getWarning(query.getKB()));
    }

    @Override
    public void answer(GetObjectPropertySources query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getOWLIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        try {
            final OWLReasoner reasoner = getReasoner(query.getKB());
            final Set<IndividualSynset> synsets = CollectionFactory.createSet();
            for (OWLIndividual indi : getAllIndividuals(query.getKB())) {
                NodeSet<OWLNamedIndividual> targets = reasoner.getObjectPropertyValues(indi.asOWLNamedIndividual(),
                        query.getOWLProperty());
                if (targets.containsEntity(query.getOWLIndividual().asOWLNamedIndividual())) {
                    synsets.add(computeSynonyms(indi, reasoner, query.getKB()));
                }
            }
            this.response = new SetOfIndividualSynsetsImpl(synsets, getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(GetObjectPropertyTargets query) {
        if (query.isNegative()) {
            this.response = new ErrorResponseImpl("(negative=true) is not supported");
            return;
        }
        if (query.getOWLIndividual().isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLNamedIndividual> nodeSet = reasoner.getObjectPropertyValues(
                query.getOWLIndividual().asOWLNamedIndividual(), query.getOWLProperty());
        if (reasoner.getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_NAME) {
            nodeSet = computeSynonyms(nodeSet, reasoner, query.getKB());
        }
        this.response = new SetOfIndividualSynsetsImpl(convertToIndividualSynsetSet(nodeSet), getWarning(query.getKB()));
    }

    protected Set<IndividualSynset> convertToIndividualSynsetSet(NodeSet<OWLNamedIndividual> nodeSet) {
        Set<IndividualSynset> synsets = CollectionFactory.createSet();
        for (Node<OWLNamedIndividual> node : nodeSet) {
            IndividualSynset synset = new IndividualSynsetImpl(node);
            synsets.add(synset);
        }
        return synsets;
    }

    protected IndividualSynset computeSynonyms(OWLIndividual individual, OWLReasoner reasoner, IRI kb) {
        try {
            Node<OWLNamedIndividual> node = reasoner.getSameIndividuals(individual.asOWLNamedIndividual());
            return new IndividualSynsetImpl(node);
        } catch (Exception e) {
            logWarning(kb, "Synonyms could not be considered " + e.toString());
        }
        return new IndividualSynsetImpl(CollectionFactory.createSet(individual));
    }

    protected NodeSet<OWLNamedIndividual> computeSynonyms(NodeSet<OWLNamedIndividual> nodeSet,
                                                          OWLReasoner reasoner,
                                                          IRI kb) {
        try {
            Set<Node<OWLNamedIndividual>> convertedSet = new HashSet<>();
            Set<OWLNamedIndividual> individuals = new HashSet<>();
            nodeSet.entities().forEach(individuals::add);
            while (individuals.size() > 0) {
                OWLNamedIndividual indi = individuals.iterator().next();
                individuals.remove(indi);
                Node<OWLNamedIndividual> node = reasoner.getSameIndividuals(indi);
                node.entities().forEach(individuals::remove);
                convertedSet.add(node);
            }
            return new OWLNamedIndividualNodeSet(convertedSet);
        } catch (Exception e) {
            logWarning(kb, "Synonyms could not be considered " + e.toString());
        }
        return nodeSet;
    }

    @Override
    public void answer(GetPrefixes query) {
        PrefixManager pm = prov.getPrefixes(query.getKB());
        Map<String, String> prefixes = pm.getPrefixName2PrefixMap();
        this.response = new PrefixesImpl(prefixes);
    }

    @Override
    public void answer(GetSettings query) {
        AbstractOWLlinkReasonerConfiguration config = this.configurationsByKB.get(query.getKB());
        if (config == null) {
            this.response = new SettingsImpl(reasonerConfiguration.getSettings());
        } else {
            this.response = new SettingsImpl(config.getSettings());
        }
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSubClasses query) {
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            NodeSet<OWLClass> nodeSet = reasoner.getSubClasses(query.getClassExpression(), query.isDirect());
            if (!nodeSet.isEmpty())
                this.response = new SetOfClassSynsetsImpl(s(nodeSet));
            else if (!query.getClassExpression().isOWLNothing()) {
                nodeSet = new OWLClassNodeSet(df.getOWLNothing());
                this.response = new SetOfClassSynsetsImpl(s(nodeSet), getWarning(query.getKB()));
            } else {
                this.response = new SetOfClassSynsetsImpl(Collections.<Node<OWLClass>>emptySet(), getWarning(query.getKB()));
            }
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(GetSubClassHierarchy query) {
        OWLClass superClass;
        if (query.getOWLClass() == null) {
            superClass = df.getOWLThing();
        } else {
            superClass = query.getOWLClass();
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        Set<HierarchyPair<OWLClass>> pairs = CollectionFactory.createSet();
        OWLClassNode equivalentClasses = new OWLClassNode();
        equivalentClasses.add(superClass);
        reasoner.getEquivalentClasses(superClass).entities().forEach(equivalentClasses::add);
        Node<OWLClass> superClassSynset = equivalentClasses;
        List<Node<OWLClass>> nextSynsets = new Vector<>();

        if (superClassSynset.getSize() > 0) {
            nextSynsets.add(superClassSynset);
        }
        final OWLClass nothing = df.getOWLNothing();
        while (!nextSynsets.isEmpty()) {
            Node<OWLClass> synset = nextSynsets.remove(0);
            if (synset.getSize() > 0) {
                NodeSet<OWLClass> subClasses = reasoner.getSubClasses(synset.getRepresentativeElement(), true);
                //rule out nothing set!
                Set<Node<OWLClass>> setOfSynsets = asUnorderedSet(subClasses.nodes().filter(classes->!classes.contains(nothing)));
                if (setOfSynsets.size() > 0) {
                    SubEntitySynsets<OWLClass> subClassSynset = new SubClassSynsets(setOfSynsets);
                    pairs.add(new HierarchyPairImpl<>(synset, subClassSynset));
                    nextSynsets.addAll(setOfSynsets);
                }
            }
        }

        Node<OWLClass> unsatisfiableClasses = reasoner.getUnsatisfiableClasses();
        this.response = new ClassHierarchyImpl(pairs, unsatisfiableClasses, getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSubDataProperties query) {
        final OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLDataProperty> nodeSet = reasoner.getSubDataProperties(query.getProperty(), query.isDirect());
        this.response = new SetOfDataPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(GetSubDataPropertyHierarchy query) {
        OWLDataProperty superClass;
        if (query.getOWLProperty() == null) {
            superClass = df.getOWLTopDataProperty();
        } else {
            superClass = query.getOWLProperty();
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        Set<HierarchyPair<OWLDataProperty>> pairs = CollectionFactory.createSet();
        final OWLDataProperty nothing = df.getOWLBottomDataProperty();
        List<Node<OWLDataProperty>> nextSynsets = new Vector<>();
        if (superClass.equals(df.getOWLTopDataProperty()) && !reasonerKnowsTopProperty) {
            //not every reasoner knowns about the top property:
            Set<OWLDataProperty> rootProperties = CollectionFactory.createSet();
            Map<OWLDataProperty, NodeSet<OWLDataProperty>> subPropertiesByProperty = CollectionFactory.createMap();
            rootProperties.addAll(getAllDataProperties(query.getKB()));
            Set<OWLDataProperty> allProperties = CollectionFactory.createSet();
            allProperties.addAll(rootProperties);

            for (OWLDataProperty property : allProperties) {
                if (property.isOWLTopDataProperty()) continue;
                NodeSet<OWLDataProperty> subProperties = reasoner.getSubDataProperties(property, true);
                boolean containsNothing = false;
                for (Node<OWLDataProperty> props : subProperties) {
                    props.entities().forEach(rootProperties::remove);
                    containsNothing = props.contains(nothing);
                }
                if (!containsNothing) {
                    subPropertiesByProperty.put(property, subProperties);
                }
            }
            Set<Node<OWLDataProperty>> setOfSynsets = CollectionFactory.createSet();
            for (OWLDataProperty prop : rootProperties) {
                Node<OWLDataProperty> equis = reasoner.getEquivalentDataProperties(prop);
                setOfSynsets.add(equis);
            }
            OWLDataPropertyNode rootSynset = new OWLDataPropertyNode(df.getOWLTopDataProperty());
            if (!setOfSynsets.isEmpty()) {
                SubDataPropertySynsets synsets = new SubDataPropertySynsets(setOfSynsets);
                pairs.add(new HierarchyPairImpl<>(rootSynset, synsets));
                nextSynsets.addAll(setOfSynsets);
            }
        } else {
            nextSynsets.add(reasoner.getEquivalentDataProperties(superClass));
        }
        while (!nextSynsets.isEmpty()) {
            Node<OWLDataProperty> synset = nextSynsets.remove(0);
            if (synset.getSize() > 0) {
                NodeSet<OWLDataProperty> subClasses = reasoner.getSubDataProperties(synset.getRepresentativeElement(), true);
                OWLDataPropertyNodeSet ruledOut = new OWLDataPropertyNodeSet();
                for (Node<OWLDataProperty> prop : subClasses) {
                    if (!prop.contains(nothing))
                        ruledOut.addNode(prop);
                }
                subClasses = ruledOut;
                if (!subClasses.isEmpty()) {
                    SubEntitySynsets<OWLDataProperty> subSynsets = new SubDataPropertySynsets(s(subClasses));
                    pairs.add(new HierarchyPairImpl<>(synset, subSynsets));
                    subClasses.nodes().forEach(nextSynsets::add);
                }
            }
        }
        Node<OWLDataProperty> unsatisfiables = reasoner.getEquivalentDataProperties(bottomDataProperty);
        if (!reasonerKnowsTopProperty) {
            String warning = "Equivalent top properties and unsatisfiable properties are not considered";
            this.response = new DataPropertyHierarchyImpl(pairs, unsatisfiables, warning);
        } else
            this.response = new DataPropertyHierarchyImpl(pairs, unsatisfiables, getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectProperties query) {
        final OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLObjectPropertyExpression> nodeSet = reasoner.getSubObjectProperties(query.getOWLObjectPropertyExpression(), query.isDirect());
        this.response = new SetOfObjectPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    boolean reasonerKnowsTopProperty = true;

    /**
     * @param b b 
     */
    public void setReasonerKnowsTopProperty(boolean b) {
        this.reasonerKnowsTopProperty = b;
    }

    @Override
    public void answer(GetSubObjectPropertyHierarchy query) {
        OWLObjectProperty superClass;
        if (query.getObjectProperty() == null) {
            superClass = df.getOWLTopObjectProperty();
        } else {
            superClass = query.getObjectProperty();
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        Set<HierarchyPair<OWLObjectPropertyExpression>> pairs = CollectionFactory.createSet();
        final OWLObjectProperty nothing = df.getOWLBottomObjectProperty();
        List<Node<OWLObjectPropertyExpression>> nextSynsets = new Vector<>();
        if (superClass.equals(df.getOWLTopObjectProperty()) && !reasonerKnowsTopProperty) {
            //not every reasoner knowns about the top property:
            Set<OWLObjectPropertyExpression> rootProperties = CollectionFactory.createSet();
            Map<OWLObjectPropertyExpression, NodeSet<OWLObjectPropertyExpression>> subPropertiesByProperty = CollectionFactory.createMap();
            rootProperties.addAll(getAllObjectProperties(query.getKB()));
            Set<OWLObjectPropertyExpression> allProperties = CollectionFactory.createSet();
            allProperties.addAll(rootProperties);

            for (OWLObjectPropertyExpression property : allProperties) {
                if (property.isOWLTopObjectProperty()) continue;
                NodeSet<OWLObjectPropertyExpression> subProperties = reasoner.getSubObjectProperties(property, true);
                boolean containsNothing = false;
                for (Node<OWLObjectPropertyExpression> props : subProperties) {
                    props.entities().forEach(rootProperties::remove);
                    containsNothing = props.contains(nothing);
                }
                if (!containsNothing) {
                    subPropertiesByProperty.put(property, subProperties);
                }
            }
            Set<Node<OWLObjectPropertyExpression>> setOfSynsets = CollectionFactory.createSet();
            for (OWLObjectPropertyExpression prop : rootProperties) {
                Node<OWLObjectPropertyExpression> equis = reasoner.getEquivalentObjectProperties(prop);
                setOfSynsets.add(equis);
            }
            Node<OWLObjectPropertyExpression> rootSynset = new OWLObjectPropertyNode(df.getOWLTopObjectProperty());
            if (!setOfSynsets.isEmpty()) {
                pairs.add(new HierarchyPairImpl<>(rootSynset, new
                        SubObjectPropertySynsets(setOfSynsets)));
                nextSynsets.addAll(setOfSynsets);
            }
        } else {
            Node<OWLObjectPropertyExpression> superClassSynset = reasoner.getEquivalentObjectProperties(superClass);
            nextSynsets.add(superClassSynset);
        }
        while (!nextSynsets.isEmpty()) {
            Node<OWLObjectPropertyExpression> synset = nextSynsets.remove(0);
            if (synset.getSize() > 0) {
                NodeSet<OWLObjectPropertyExpression> subClasses = reasoner.getSubObjectProperties(synset.getRepresentativeElement(), true);
                OWLObjectPropertyNodeSet ruledOutSubClasses = new OWLObjectPropertyNodeSet();
                for (Node<OWLObjectPropertyExpression> classes : subClasses) {
                    if (!classes.contains(nothing))
                        ruledOutSubClasses.addNode(classes);
                }
                subClasses = ruledOutSubClasses;
                if (!subClasses.isEmpty()) {
                    Set<Node<OWLObjectPropertyExpression>> setOfSynsets = CollectionFactory.createSet();
                    for (Node<OWLObjectPropertyExpression> classes : subClasses) {
                        setOfSynsets.add(classes);
                    }
                    SubEntitySynsets<OWLObjectPropertyExpression> subClassSynset = new SubObjectPropertySynsets(setOfSynsets);
                    pairs.add(new HierarchyPairImpl<>(synset, subClassSynset));
                    nextSynsets.addAll(setOfSynsets);
                }
            }
        }
        Set<OWLObjectPropertyExpression> unsatisfiableClasses = CollectionFactory.createSet();//reasoner.getUnsatisfiableClasses();
        Node<OWLObjectPropertyExpression> unsatisfiableSynset;
        if (unsatisfiableClasses.contains(df.getOWLBottomObjectProperty()))
            unsatisfiableSynset = new OWLObjectPropertyNode(unsatisfiableClasses);
        else {
            Set<OWLObjectPropertyExpression> newSet = new HashSet<>();
            newSet.addAll(unsatisfiableClasses);
            newSet.add(df.getOWLBottomObjectProperty());
            unsatisfiableSynset = new OWLObjectPropertyNode(newSet);
        }
        if (!reasonerKnowsTopProperty) {
            String warning = "Equivalent top properties and unsatisfiable properties are not considered";
            this.response = new ObjectPropertyHierarchyImpl(pairs, unsatisfiableSynset, warning);
        } else
            this.response = new ObjectPropertyHierarchyImpl(pairs, unsatisfiableSynset, getWarning(query.getKB()));
    }

    protected Response getResponse() {
        return this.response;
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSuperClasses query) {
        try {
            OWLReasoner reasoner = getReasoner(query.getKB());
            NodeSet<OWLClass> nodeSet = reasoner.getSuperClasses(query.getOWLClassExpression(), query.isDirect());
            this.response = new SetOfClassSynsetsImpl(s(nodeSet), getWarning(query.getKB()));
        } catch (Exception e) {
            handle(e);
        }
    }

    @Override
    public void answer(Request<?> query) {
        if (query instanceof RetractRequest) {
            RetractRequest request = (RetractRequest) query;
            OWLOntology ontology = manager.getOntology(request.getKB());
            if(ontology==null) {
                this.response = new ErrorResponseImpl(request.getKB() + " matches no ontology");
                return;
            }
            ontology.removeAxioms(request.getAxioms());
            this.response = new OKImpl();
        } else
            this.response = new ErrorResponseImpl(query.getClass().getSimpleName() + " is not supported");
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSuperDataProperties query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLDataProperty> nodeSet = reasoner.getSuperDataProperties(query.getProperty(), query.isDirect());
        this.response = new SetOfDataPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetSuperObjectProperties query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLObjectPropertyExpression> nodeSet = reasoner.getSuperObjectProperties(query.getProperty(), query.isDirect());
        this.response = new SetOfObjectPropertySynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.GetTypes query) {
        OWLIndividual individual = query.getIndividual();
        if (individual.isAnonymous()) {
            this.response = new ErrorResponseImpl("Anonymous individual is not supported");
            return;
        }
        OWLReasoner reasoner = getReasoner(query.getKB());
        NodeSet<OWLClass> nodeSet = reasoner.getTypes(query.getIndividual().asOWLNamedIndividual(), query.isDirect());
        this.response = new ClassSynsetsImpl(s(nodeSet), getWarning(query.getKB()));
    }

    @Override
    public void answer(IsClassSatisfiable query) {
        OWLReasoner reasoner = getReasoner(query.getKB());

        try {
            boolean isSatisfiable = reasoner.isSatisfiable(query.getObject());
            this.response = new BooleanResponseImpl(isSatisfiable);
        } catch (Exception e) {
            handle(e);
            e.printStackTrace();
        }
    }

    @Override
    public void answer(IsDataPropertySatisfiable query) {
        this.response = new ErrorResponseImpl("IsDataPropertySatisfiable is not supported");
    }

    @Override
    public void answer(IsKBConsistentlyDeclared query) {
        this.response = new ErrorResponseImpl("IsKBConsistentlyDeclared is not supported");
    }

    @Override
    public void answer(IsKBSatisfiable query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        this.response = new BooleanResponseImpl(reasoner.isConsistent(), getWarning(query.getKB()));
    }

    @Override
    public void answer(IsEntailed query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        this.response = new BooleanResponseImpl(reasoner.isEntailed(query.getAxiom()), getWarning(query.getKB()));
    }

    @Override
    public void answer(IsEntailedDirect query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        if (query.isOWLClassAssertionAxiom()) {
            OWLClassAssertionAxiom axiom = query.asOWLClassAssertionAxiom();
            if (axiom.getIndividual().isAnonymous())
                this.response = new ErrorResponseImpl("Anonymous individuals are not supported");
            else {
                this.response = new BooleanResponseImpl(reasoner.getInstances(axiom.getClassExpression(), true).containsEntity(axiom.getIndividual().asOWLNamedIndividual()));
            }
        } else if (query.isOWLSubClassOfAxiom()) {
            OWLSubClassOfAxiom axiom = query.asOWLSubClassOfAxiom();
            if (axiom.getSubClass().isAnonymous() && axiom.getSuperClass().isAnonymous()) {
                this.response = new ErrorResponseImpl("Both sub- and superclass are anonymous. This is not supported");
            } else if (axiom.getSubClass().isAnonymous()) {
                this.response = new BooleanResponseImpl(reasoner.getSuperClasses(axiom.getSubClass(), true).containsEntity(axiom.getSuperClass().asOWLClass()), getWarning(query.getKB()));
            } else if (axiom.getSuperClass().isAnonymous()) {
                this.response = new BooleanResponseImpl((reasoner.getSubClasses(axiom.getSuperClass(), true)).containsEntity(axiom.getSubClass().asOWLClass()), getWarning(query.getKB()));
            } else {
                this.response = new BooleanResponseImpl(reasoner.getSubClasses(axiom.getSuperClass().asOWLClass(), true).containsEntity(axiom.getSubClass().asOWLClass()), getWarning(query.getKB()));
            }
        } else if (query.isOWLSubDataPropertyOfAxiom()) {
            OWLSubDataPropertyOfAxiom axiom = query.asOWLSubDataPropertOfAxiom();
            if (axiom.getSubProperty().isAnonymous() && axiom.getSuperProperty().isAnonymous()) {
                this.response = new ErrorResponseImpl("Both sub- and superclass are anonymous. This is not supported");
            } else if (axiom.getSubProperty().isAnonymous()) {
                this.response = new BooleanResponseImpl(reasoner.getSuperDataProperties(axiom.getSubProperty().asOWLDataProperty(), true).containsEntity(axiom.getSuperProperty().asOWLDataProperty()), getWarning(query.getKB()));
            } else if (axiom.getSuperProperty().isAnonymous()) {
                this.response = new BooleanResponseImpl((reasoner.getSubDataProperties(axiom.getSuperProperty().asOWLDataProperty(), true)).containsEntity(axiom.getSubProperty().asOWLDataProperty()), getWarning(query.getKB()));
            } else {
                this.response = new BooleanResponseImpl(reasoner.getSubDataProperties(axiom.getSuperProperty().asOWLDataProperty(), true).containsEntity(axiom.getSubProperty().asOWLDataProperty()), getWarning(query.getKB()));
            }
        } else if (query.isOWLSubObjectPropertyOfAxiom()) {
            OWLSubObjectPropertyOfAxiom axiom = query.asOWLSubObjectPropertOfAxiom();
            if (axiom.getSubProperty().isAnonymous() && axiom.getSuperProperty().isAnonymous()) {
                this.response = new ErrorResponseImpl("Both sub- and superclass are anonymous. This is not supported");
            } else if (axiom.getSubProperty().isAnonymous()) {
                this.response = new BooleanResponseImpl(reasoner.getSuperObjectProperties(axiom.getSubProperty(), true).containsEntity(axiom.getSuperProperty().asOWLObjectProperty()), getWarning(query.getKB()));
            } else if (axiom.getSuperProperty().isAnonymous()) {
                this.response = new BooleanResponseImpl((reasoner.getSubObjectProperties(axiom.getSuperProperty(), true)).containsEntity(axiom.getSubProperty().asOWLObjectProperty()), getWarning(query.getKB()));
            } else {
                this.response = new BooleanResponseImpl(reasoner.getSubObjectProperties(axiom.getSuperProperty().asOWLObjectProperty(), true).containsEntity(axiom.getSubProperty().asOWLObjectProperty()), getWarning(query.getKB()));
            }
        }
    }

    @Override
    public void answer(IsObjectPropertySatisfiable query) {
        this.response = new ErrorResponseImpl("IsObjectPropertySatisfiable is not supported");
    }

    @Override
    public synchronized void answer(LoadOntologies query) {
        OWLlinkIRIMapper mapper = new OWLlinkIRIMapper(query.getIRIMapping());
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getIRIMappers().add(mapper);
        OWLReasoner reasoner = getReasoner(query.getKB());
        try {
            for (IRI iri : query.getOntologyIRIs()) {
                m.loadOntology(iri);
            }
            OWLOntology ontology2 = manager.getOntology(query.getKB());
            if (ontology2 != null) {
                m.ontologies().forEach(o -> ontology2.addAxioms(o.axioms()));
            }
            reasoner.flush();
            //reasoner.prepareReasoner();
            this.response = new OKImpl();
        } catch (Exception e) {
            handle(e);
        }
    }

    class OWLlinkIRIMapper implements OWLOntologyIRIMapper {
        private List<IRIMapping> mappings;

        public OWLlinkIRIMapper(List<IRIMapping> mappings) {
            this.mappings = mappings;
        }

        @Override
        public IRI getDocumentIRI(IRI iri) {
            for (IRIMapping mapping : mappings) {
                if (iri.toString().startsWith(mapping.key)) {
                    String result = mapping.value.toString();
                    result += iri.toString().substring(mapping.key.length());
                        return IRI.create(result);
                }
            }
            return iri;
        }
    }

    @Override
    public void answer(ReleaseKB query) {
        OWLReasoner reasoner = getReasoner(query.getKB());
        OWLOntology ontology = manager.getOntology(query.getKB());
        if(ontology!=null) {
            manager.removeOntology(ontology);
        }
        this.reasonersByKB.remove(query.getKB());
        this.kbNameByIRI.remove(query.getKB());
        this.configurationsByKB.remove(query.getKB());
        this.warningsByReasoners.remove(query.getKB());
        try {
            reasoner.dispose();
            //  if (reasoner instanceof OWLOntologyChangeListener)
            //    manager.removeOntologyChangeListener((OWLOntologyChangeListener) reasoner);
        } catch (Exception e) {
            LOGGER.warn("Reasoner failure during dispose", e);
        }
        this.response = new OKImpl();
    }

    @Override
    public void answer(org.semanticweb.owlapi.owllink.builtin.requests.Set query) {
        AbstractOWLlinkReasonerConfiguration config = this.configurationsByKB.get(query.getKB());
        if (config == null) {
            config = new AbstractOWLlinkReasonerConfiguration(this.reasonerConfiguration.getOWLReasonerConfiguration());
            config.add(this.reasonerConfiguration);
            this.configurationsByKB.put(query.getKB(), config);
        }
        if (!config.set(query.getKey(), query.getValue())) {
            this.response = new ErrorResponseImpl("The given set is not supported");
        } else {
            this.response = new OKImpl();
        }
        if (query.getKey().equals(AbstractOWLlinkReasonerConfiguration.ABBREVIATES_IRIS)) {
            //set appreviated IRIs!
            OWLlinkLiteral literal = query.getValue().iterator().next();
            boolean abbrevIRIs = Boolean.parseBoolean(literal.getValue());
            prov.setBlocked(query.getKB(), !abbrevIRIs);
        }
    }

    @Override
    public void answer(Tell request) {
        try {
            OWLOntology ontology = manager.getOntology(request.getKB());
            verifyNotNull(ontology, "Ontology for "+request.getKB()+" not found").addAxioms(request.getAxioms());
            this.response = new OKImpl();
            // getReasoner(request.getKB()).prepareReasoner();
        } catch (Exception e) {
            handle(e);
        }
    }

    /**
     * Pairs iterator.
     *
     * @param <O> Type contained
     */
    public final static class PairsIterator<O> implements Iterator<PairsIterator.Pair<O>>, Iterable<PairsIterator.Pair<O>> {
        List<O> elements;
        int innerIndex = 0;
        int outerIndex = 0;
        Pair<O> current;
        boolean hasCurrent = false;

        /**
         * @param elements elements 
         */
        public PairsIterator(Set<O> elements) {
            this.elements = new Vector<>(elements);
            this.innerIndex = 1;
            this.outerIndex = 0;
            if (elements.size() == 1) {
                current = new Pair<>();
                current.first = this.elements.get(0);
                current.second = null;
                hasCurrent = true;
            }
            if (elements.size() > 1) {
                current = new Pair<>();
                current.first = this.elements.get(0);
                current.second = this.elements.get(1);
                hasCurrent = true;
            }
        }

        @Override
        public boolean hasNext() {
            while (!hasCurrent && outerIndex < this.elements.size()) {
                if (innerIndex < this.elements.size() - 1) {
                    innerIndex++;
                    current = new Pair<>();
                    current.first = this.elements.get(outerIndex);
                    current.second = this.elements.get(innerIndex);
                    hasCurrent = true;
                } else {
                    outerIndex++;
                    innerIndex = outerIndex;
                }
            }
            return hasCurrent;
        }

        @Override
        public Pair<O> next() {
            if (hasCurrent || hasNext()) {
                hasCurrent = false;
                return current;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
        }

        @Override
        public Iterator<Pair<O>> iterator() {
            return this;
        }

        /** Pair. 
         * @param <O> type of pair */
        public static class Pair<O> {
            O first;
            O second;
        }
    }
}