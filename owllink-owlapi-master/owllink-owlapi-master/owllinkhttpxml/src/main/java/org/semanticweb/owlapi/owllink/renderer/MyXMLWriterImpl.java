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

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.IllegalElementNameException;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.PrintWriter;
import java.net.URI;
import java.util.*;

/**
 * Author: Olaf Noppens
 * Date: 28.04.2010
 */
public class MyXMLWriterImpl implements XMLWriter {

    private Stack<XMLElement> elementStack;

    protected PrintWriter writer;

    private String encoding;

    private String xmlBase;

    private URI xmlBaseURI;

    private XMLWriterNamespaceManager xmlWriterNamespaceManager;

    private Map<String, String> entities;

    private static final int TEXT_CONTENT_WRAP_LIMIT = Integer.MAX_VALUE;

    private boolean preambleWritten;

    private static final String PERCENT_ENTITY = "&#37;";

    protected OWLOntologyWriterConfiguration config;

    /**
     * @param writer writer 
     * @param xmlWriterNamespaceManager xmlWriterNamespaceManager 
     * @param config config 
     */
    public MyXMLWriterImpl(PrintWriter writer, XMLWriterNamespaceManager xmlWriterNamespaceManager, OWLOntologyWriterConfiguration config) {
        this(writer, xmlWriterNamespaceManager, "UTF-8", config);
    }

    /**
     * @param writer writer 
     * @param xmlWriterNamespaceManager xmlWriterNamespaceManager 
     * @param xmlBase xmlBase 
     * @param config config 
     */
    public MyXMLWriterImpl(PrintWriter writer, XMLWriterNamespaceManager xmlWriterNamespaceManager, String xmlBase, OWLOntologyWriterConfiguration config) {
        this.writer = writer;
        this.xmlWriterNamespaceManager = xmlWriterNamespaceManager;
        this.xmlBase = xmlBase;
        this.xmlBaseURI = URI.create(xmlBase);
        this.encoding = "";
        this.config=config;
        elementStack = new Stack<>();
        setupEntities();
    }

    private void setupEntities() {
        List<String> namespaces = new ArrayList<>();
        xmlWriterNamespaceManager.getNamespaces().forEach(namespaces::add);
        Collections.sort(namespaces, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Shortest string first
                return o1.length() - o2.length();
            }
        });
        entities = new LinkedHashMap<>();
        for (String curNamespace : namespaces) {
            String curPrefix = "";
            if (xmlWriterNamespaceManager.getDefaultNamespace().equals(curNamespace)) {
                curPrefix = xmlWriterNamespaceManager.getDefaultPrefix();
            } else {
                curPrefix = xmlWriterNamespaceManager.getPrefixForNamespace(curNamespace);

            }
            if (curPrefix!=null && !curPrefix.isEmpty()) {
                entities.put(curNamespace, "&" + curPrefix + ";");
            }
        }
    }

    protected String swapForEntity(String value) {
        for (String curEntity : entities.keySet()) {
            String entityVal = entities.get(curEntity);
            if (value.length() > curEntity.length()) {
                String repVal = value.replace(curEntity, entityVal);
                if (repVal.length() < value.length()) {
                    return repVal;
                }
            }
        }
        return value;
    }

    /** @return default namespace */
    public String getDefaultNamespace() {
        return xmlWriterNamespaceManager.getDefaultNamespace();
    }

    @Override
    public String getXMLBase() {
        return xmlBase;
    }

    /** @return base xml uri*/
    public URI getXMLBaseAsURI() {
        return xmlBaseURI;
    }

    @Override
    public XMLWriterNamespaceManager getNamespacePrefixes() {
        return xmlWriterNamespaceManager;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    private static boolean isValidQName(String name) {
        if (name == null) {
            return false;
        }
        int colonIndex = name.indexOf(":");
        boolean valid = false;
        if (colonIndex == -1) {
            valid = OWL2Datatype.XSD_NCNAME.getPattern().matcher(name).matches();
        } else {
            valid = OWL2Datatype.XSD_NCNAME.getPattern().matcher(name.substring(0, colonIndex - 1)).matches();
            if (valid) {
                valid = OWL2Datatype.XSD_NAME.getPattern().matcher(name.substring(colonIndex + 1)).matches();
            }
        }
        return valid;
    }

    @Override
    public void setWrapAttributes(boolean b) {
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.peek();
            element.setWrapAttributes(b);
        }
    }

    @Override
    public void writeAttribute(IRI attr, String val) {
     writeAttribute(attr.toString(), val);
        
    }

    @Override
    public void writeStartElement(IRI iri) {
        String name=iri.toString();   
        String qName = xmlWriterNamespaceManager.getQName(name);
        if (qName == null || qName.equals(name)) {
            if (!isValidQName(name)) {
                //let us try write it inline

                int index = name.indexOf("#");
                if (index == -1) {
                    throw new IllegalElementNameException(name);
                }
                String localName = name.substring(index + 1, name.length());
                XMLElement element = new XMLElement(localName, elementStack.size());
                element.setAttribute("xmlns", name.substring(0, index+1));
                if (!elementStack.isEmpty()) {
                    XMLElement topElement = elementStack.peek();
                    if (topElement != null) {
                        topElement.writeElementStart(false);
                    }
                }
                elementStack.push(element);

                return;
                // Could not generate a valid QName, therefore, we cannot
                // write valid XML - just throw an exception!
                //throw new IllegalElementNameException(name);

            }
        }
        XMLElement element = new XMLElement(qName, elementStack.size());
        if (!elementStack.isEmpty()) {
            XMLElement topElement = elementStack.peek();
            if (topElement != null) {
                topElement.writeElementStart(false);
            }
        }
        elementStack.push(element);
    }

    @Override
    public void writeEndElement() {
        // Pop the element off the stack and write it out
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.pop();
            element.writeElementEnd();
        }
    }

    @Override
    public void writeAttribute(String attr, String val) {
        XMLElement element = elementStack.peek();
        element.setAttribute(xmlWriterNamespaceManager.getQName(attr), val);
    }

    @Override
    public void writeTextContent(String text) {
        XMLElement element = elementStack.peek();
        element.setText(text);
    }

    @Override
    public void writeComment(String commentText) {
        XMLElement element = new XMLElement(null, elementStack.size());
        element.setText("<!-- " + commentText + " -->");
        if (!elementStack.isEmpty()) {
            XMLElement topElement = elementStack.peek();
            if (topElement != null) {
                topElement.writeElementStart(false);
            }
        }
        if (preambleWritten) {
            element.writeElementStart(true);
        } else {
            elementStack.push(element);
        }
    }

    private void writeEntities(String rootName) {
        writer.write("\n\n<!DOCTYPE " + xmlWriterNamespaceManager.getQName(rootName) + " [\n");
        for (String entityVal : entities.keySet()) {
            String entity = entities.get(entityVal);
            entity = entity.substring(1, entity.length() - 1);
            writer.write("    <!ENTITY ");
            writer.write(entity);
            writer.write(" \"");
            entityVal = XMLUtils.escapeXML(entityVal);
            entityVal = entityVal.replace("%", PERCENT_ENTITY);
            writer.write(entityVal);
            writer.write("\" >\n");
        }
        writer.write("]>\n\n\n");
    }

    @Override
    public void startDocument(IRI rootElement) {
        String rootElementName=rootElement.toString();
        String encodingString = "";
        if (encoding.length() > 0) {
            encodingString = " encoding=\"" + encoding + "\"";
        }
        writer.write("<?xml version=\"1.0\"" + encodingString + "?>\n");
        if (config.isUseNamespaceEntities()) {
            writeEntities(rootElementName);
        }
        preambleWritten = true;
        while (!elementStack.isEmpty()) {
            elementStack.pop().writeElementStart(true);
        }
        writeStartElement(rootElement);
        setWrapAttributes(true);
        writeAttribute("xmlns", xmlWriterNamespaceManager.getDefaultNamespace());
        if (xmlBase.length() != 0) {
            writeAttribute("xml:base", xmlBase);
        }
        for (String curPrefix : xmlWriterNamespaceManager.getPrefixes()) {
            if (!curPrefix.isEmpty()) {
                writeAttribute("xmlns:" + curPrefix, verifyNotNull(xmlWriterNamespaceManager.getNamespaceForPrefix(curPrefix)));
            }
        }
    }

    @Override
    public void endDocument() {
        // Pop of each element
        while (!elementStack.isEmpty()) {
            writeEndElement();
        }
        writer.flush();
    }

    /**
     * XML element.
     */
    public class XMLElement {

        private String name;

        private Map<String, String> attributes;

        String textContent;

        private boolean startWritten;

        private int indentation;

        private boolean wrapAttributes;

        /**
         * @param name name 
         */
        public XMLElement(String name) {
            this(name, 0);
            wrapAttributes = false;
        }

        /**
         * @param name name 
         * @param indentation indentation 
         */
        public XMLElement(String name, int indentation) {
            this.name = name;
            attributes = new LinkedHashMap<>();
            this.indentation = indentation;
            textContent = null;
            startWritten = false;
        }

        /**
         * @param b b 
         */
        public void setWrapAttributes(boolean b) {
            wrapAttributes = b;
        }

        /**
         * @param attribute attribute 
         * @param value value 
         */
        public void setAttribute(String attribute, String value) {
            attributes.put(attribute, value);
        }

        /**
         * @param content content 
         */
        public void setText(String content) {
            textContent = content;
        }

        /**
         * @param close close 
         */
        public void writeElementStart(boolean close) {
            if (!startWritten) {
                startWritten = true;
                insertIndentation();
                if (name != null) {
                    writer.write('<');
                    writer.write(name);
                    writeAttributes();
                    if (textContent != null) {
                        boolean wrap = textContent.length() > TEXT_CONTENT_WRAP_LIMIT;
                        if (wrap) {
                            writeNewLine();
                            indentation++;
                            insertIndentation();
                        }
                        writer.write('>');
                        writeTextContent();
                        if (wrap) {
                            indentation--;
                        }
                    }
                    if (close) {
                        if (textContent != null) {
                            writeElementEnd();
                        } else {
                            writer.write("/>");
                            writeNewLine();
                        }
                    } else {
                        if (textContent == null) {
                            writer.write('>');
                            writeNewLine();
                        }
                    }
                } else {
                    // Name is null so by convension this is a comment
                    if (textContent != null) {
                        writer.write("\n\n\n");
                        StringTokenizer tokenizer = new StringTokenizer(textContent, "\n", true);
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if (!token.equals("\n")) {
                                insertIndentation();
                            }
                            writer.write(token);
                        }
                        writer.write("\n\n");
                    }
                }
            }
        }

        /**
         * Write end.
         */
        public void writeElementEnd() {
            if (name != null) {
                if (!startWritten) {
                    writeElementStart(true);
                } else {
                    if (textContent == null) {
                        insertIndentation();
                    }
                    writer.write("</");
                    writer.write(name);
                    writer.write(">");
                    writeNewLine();
                }
            }
        }

        private void writeAttribute(String attr, String val) {
            writer.write(attr);
            writer.write('=');
            writer.write('"');
            if (config.isUseNamespaceEntities()) {
                writer.write(swapForEntity(XMLUtils.escapeXML(val)));
            } else {
                writer.write(XMLUtils.escapeXML(val));
            }
            writer.write('"');
        }

        private void writeAttributes() {
            for (Iterator<String> it = attributes.keySet().iterator(); it.hasNext();) {
                String attr = it.next();
                String val = attributes.get(attr);
                writer.write(' ');
                writeAttribute(attr, val);
                if (it.hasNext() && wrapAttributes) {
                    writer.write("\n");
                    indentation++;
                    insertIndentation();
                    indentation--;
                }
            }
        }

        private void writeTextContent() {
            if (textContent != null) {
                writer.write(XMLUtils.escapeXML(textContent));
            }
        }

        private void insertIndentation() {
            if (config.isIndenting()) {
                for (int i = 0; i < indentation * config.getIndentSize(); i++) {
                    writer.write(' ');
                }
            }
        }

        private void writeNewLine() {
            writer.write('\n');
        }
    }
}
