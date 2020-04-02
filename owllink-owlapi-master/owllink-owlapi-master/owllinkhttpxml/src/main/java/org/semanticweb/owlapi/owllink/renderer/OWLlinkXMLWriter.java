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

import org.coode.owlapi.owlxmlrenderer.OWLXMLObjectRenderer;
import org.coode.owlapi.owlxmlrenderer.OWLXMLWriter;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.owllink.OWLlinkNamespaces;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.Namespaces;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author Olaf Noppens
 */
public class OWLlinkXMLWriter implements OWLlinkWriter {
    private XMLWriter writer;
    private PrefixManagerProvider prefixProvider;
    Map<IRI, OWLXMLObjectRenderer> rendererByIRI;
    OWLXMLObjectRenderer defaultRenderer;
    Writer baseWriter;
    OWLOntologyWriterConfiguration config;

    /**
     * @param writer writer 
     * @param prefixProvider prefixProvider 
     * @param config config 
     */
    public OWLlinkXMLWriter(PrintWriter writer, PrefixManagerProvider prefixProvider, OWLOntologyWriterConfiguration config) {
        this.config=config;
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager(OWLlinkNamespaces.OWLLink.toString() + "#");
        nsm.setPrefix("xsd", Namespaces.XSD.toString());
        nsm.setPrefix("owl", Namespaces.OWL.toString());
        String base = OWLlinkNamespaces.OWLLink.toString();
        //we need an own xml writer because in OWL attribute's NS are not allowed.
        this.writer = new MyXMLWriterImpl(writer, nsm, base, config) {
            @Override
            public void writeAttribute(String attr, String val) {
                if (attr.startsWith(Namespaces.OWL.toString())) {
                    String localName = attr.substring(Namespaces.OWL.toString().length(), attr.length());
                    super.writeAttribute(localName, val);
                } else
                    super.writeAttribute(attr, val);
            }
        };
        this.writer.setEncoding("UTF-8");
        OWLXMLWriter owlxmlWriter = new OWLXMLWriter(this.writer);
        this.defaultRenderer = new OWLXMLObjectRenderer(owlxmlWriter);
        this.baseWriter = writer;
        rendererByIRI = CollectionFactory.createMap();
        this.prefixProvider = prefixProvider;
    }

    @Override
    public void startDocument(final boolean isRequest) {
        if (isRequest)
            writer.startDocument(OWLlinkXMLVocabulary.REQUEST_MESSAGE.getIRI());
        else
            writer.startDocument(OWLlinkXMLVocabulary.RESPONSE_MESSAGE.getIRI());
    }

    @Override
    public void endDocument() {
        writer.endDocument();
    }

    /**
     * @param v v
     */
    public final void writeStartElement(OWLlinkXMLVocabulary v) {
        this.writeStartElement(v.getURI());
    }

    @Override
    public void writeStartElement(IRI name) {
        writer.writeStartElement(name);
    }

    @Override
    public void writeEndElement() {
        writer.writeEndElement();
    }

    @Override
    public void writeAttribute(String attribute, String value) {
        writer.writeAttribute(attribute, value);
    }

    @Override
    public void writeAttribute(IRI attribute, String value) {
        writer.writeAttribute(attribute.toString(), value);
    }

    @Override
    public void writeNegativeAttribute(boolean isNegative) {
        writer.writeAttribute(OWLlinkXMLVocabulary.NEGATIVE_ATTRIBUTE.getURI().toString(), Boolean.toString(isNegative));
    }

    @Override
    public void writeDirectAttribute(boolean isNegative) {
        writer.writeAttribute(OWLlinkXMLVocabulary.DIRECT_ATTRIBUTE.getURI().toString(), Boolean.toString(isNegative));
    }

    @Override
    public void writeKBAttribute(IRI kb) {
        writer.writeAttribute(OWLlinkXMLVocabulary.KB_ATTRIBUTE.getURI().toString(), kb.toString());
    }

    @Override
    public void writeFullIRIAttribute(IRI iri) {
        writer.writeAttribute(OWLlinkXMLVocabulary.IRI_ATTRIBUTE.getURI().toString(), iri.toString());
    }

    @Override
    public void writeOWLObject(OWLObject object, IRI KB) {
        if (KB == null) {
            object.accept(defaultRenderer);
        } else {
            OWLXMLObjectRenderer renderer = rendererByIRI.get(KB);
            if (renderer == null) {
                OWLXMLWriter w = new OWLXMLWriter(this.writer);
                if (prefixProvider.contains(KB)) {
                    Map<String, String> map = prefixProvider.getPrefixes(KB).getPrefixName2PrefixMap();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        w.getIRIPrefixMap().put(entry.getValue(), entry.getKey());
                    }
                }
                renderer = new OWLXMLObjectRenderer(w);
                rendererByIRI.put(KB, renderer);
            }
            object.accept(renderer);
        }
    }

    @Override
    public void writeTextContent(String text) {
        writer.writeTextContent(text);
    }

    @Override
    public PrefixManagerProvider getPrefixManagerProvider() {
        return this.prefixProvider;
    }
}
