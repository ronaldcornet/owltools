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
import org.semanticweb.owlapi.model.OWLAxiom;
import static org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary.*;
import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.owllink.builtin.requests.*;
import org.semanticweb.owlapi.owllink.builtin.response.OWLlinkLiteral;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.Map;

/**
 * @author Olaf Noppens
 */
public class BuiltinRequestRenderer implements OWLlinkRequestRenderer, RequestVisitor {
    protected OWLlinkWriter writer;

    @Override
    public void answer(Classify query) {
        writer.writeStartElement(CLASSIFY.getURI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(CreateKB query) {
        writer.writeStartElement(BuiltinRequestVocabulary.CREATEKB.getURI());
        if (query.getName() != null)
            writer.writeAttribute(NAME_Attribute.getURI().toString(), query.getName());
        if (query.getKB() != null)
            writer.writeAttribute(KB_ATTRIBUTE.getURI().toString(), query.getKB().toString());
        if (query.getPrefixes() != null) {
            final DefaultPrefixManager manager = new DefaultPrefixManager();
            final Map<String, String> prefixName2PrefixMap = manager.getPrefixName2PrefixMap();
            for (Map.Entry<String, String> prefix : query.getPrefixes().entrySet()) {
                writer.writeStartElement(PREFIX.getURI());
                writer.writeAttribute(NAME_Attribute.getURI().toString(), prefix.getKey().endsWith(":") ? prefix.getKey().substring(0, prefix.getKey().length() - 1) : prefix.getKey());
                writer.writeAttribute(FULLIRI.getURI().toString(), prefix.getValue());
                writer.writeEndElement();
                prefixName2PrefixMap.put(prefix.getValue(), prefix.getKey());
            }
            if (query.getKB() != null) {
                //if kb is known we can update prefix information

            }
        }
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllAnnotationProperties query) {
        writer.writeStartElement(GET_ALL_ANNOTATION_PROPERTIES.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllClasses query) {
        writer.writeStartElement(GET_ALL_CLASSES.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllDataProperties query) {
        writer.writeStartElement(GET_ALL_DATAPROPERTIES.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllDatatypes query) {
        writer.writeStartElement(GET_ALL_DATATYPES.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllIndividuals query) {
        writer.writeStartElement(GET_ALL_INDIVIDUALS.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetAllObjectProperties query) {
        writer.writeStartElement(GET_ALL_OBJECTPROPERTIES.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDataPropertiesBetween query) {
        writer.writeStartElement(GET_DATAPROPERTIES_BETWEEN.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getSourceIndividual(), kb);
        writer.writeOWLObject(query.getTargetValue(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDataPropertiesOfLiteral query) {
        writer.writeStartElement(GET_DATAPROPERTIES_OF_LITERAL.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getTargetValue(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDataPropertiesOfSource query) {
        writer.writeStartElement(GET_DATAPROPERTIES_OF_SOURCE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getSourceIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDataPropertySources query) {
        writer.writeStartElement(GET_DATAPROPERTY_SOURCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getTargetValue(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDataPropertyTargets query) {
        writer.writeStartElement(GET_DATAPROPERTY_TARGETS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getSourceIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDescription query) {
        writer.writeStartElement(GET_DESCRIPTION.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDisjointClasses query) {
        writer.writeStartElement(GET_DISJOINT_CLASSES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDisjointDataProperties query) {
        writer.writeStartElement(GET_DISJOINT_DATAPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDifferentIndividuals query) {
        writer.writeStartElement(GET_DIFFERENT_INDIVIDUALS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetDisjointObjectProperties query) {
        writer.writeStartElement(GET_DISJOINT_OBJECTPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetEquivalentClasses query) {
        writer.writeStartElement(GET_EQUIVALENT_CLASSES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetEquivalentDataProperties query) {
        writer.writeStartElement(GET_EQUIVALENT_DATAPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSameIndividuals query) {
        writer.writeStartElement(GET_SAME_INDIVIDUALS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetEquivalentObjectProperties query) {
        writer.writeStartElement(GET_EQUIVALENT_OBJECTPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedDataPropertySources query) {
        writer.writeStartElement(GET_FLATTENED_DATAPROPERTY_SOURCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getTargetValue(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedDifferentIndividuals query) {
        writer.writeStartElement(GET_FLATTENED_DIFFERENT_INDIVIDUALS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedInstances query) {
        writer.writeStartElement(GET_FLATTENED_INSTANCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getClassExpression(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedObjectPropertySources query) {
        writer.writeStartElement(GET_FLATTENED_OBJECTPROPERTY_SOURCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getOWLIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedObjectPropertyTargets query) {
        writer.writeStartElement(GET_FLATTENED_OBJECTPROPERTY_TARGETS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getOWLIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetFlattenedTypes query) {
        writer.writeStartElement(GET_FLATTENED_TYPES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetInstances query) {
        writer.writeStartElement(GET_INSTANCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getClassExpression(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetKBLanguage query) {
        writer.writeStartElement(GET_KB_LANGUAGE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetObjectPropertiesBetween query) {
        writer.writeStartElement(GET_OBJECTPROPERTIES_BETWEEN.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getSourceIndividual(), kb);
        writer.writeOWLObject(query.getTargetIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetObjectPropertiesOfSource query) {
        writer.writeStartElement(GET_OBJECTPROPERTIES_OF_SOURCE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetObjectPropertiesOfTarget query) {
        writer.writeStartElement(GET_OBJECTPROPERTIES_OF_TARGET.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetObjectPropertySources query) {
        writer.writeStartElement(GET_OBJECTPROPERTY_SOURCES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getOWLIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetObjectPropertyTargets query) {
        writer.writeStartElement(GET_OBJECTPROPERTY_TARGETS.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeNegativeAttribute(query.isNegative());
        writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeOWLObject(query.getOWLIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetPrefixes query) {
        writer.writeStartElement(GET_PREFIXES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSettings query) {
        writer.writeStartElement(GET_SETTINGS.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubClasses query) {
        writer.writeStartElement(GET_SUBCLASSES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getClassExpression(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubClassHierarchy query) {
        writer.writeStartElement(GET_SUBCLASS_HIERARCHY.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        if (query.getOWLClass() != null)
            writer.writeOWLObject(query.getOWLClass(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubDataProperties query) {
        writer.writeStartElement(GET_SUBDATAPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getProperty(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubDataPropertyHierarchy query) {
        writer.writeStartElement(GET_SUBDATAPROPERTY_HIERARCHY.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        if (query.getOWLProperty() != null)
            writer.writeOWLObject(query.getOWLProperty(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubObjectProperties query) {
        writer.writeStartElement(GET_SUBOBJECTPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getOWLObjectPropertyExpression(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSubObjectPropertyHierarchy query) {
        writer.writeStartElement(GET_SUBOBJECTPROPERTY_HIERARCHY.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        if (query.getObjectProperty() != null)
            writer.writeOWLObject(query.getObjectProperty(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSuperClasses query) {
        writer.writeStartElement(GET_SUPERCLASSES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getOWLClassExpression(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSuperDataProperties query) {
        writer.writeStartElement(GET_SUPERDATAPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getProperty(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetSuperObjectProperties query) {
        writer.writeStartElement(GET_SUPEROBJECTPROPERTIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getProperty(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(GetTypes query) {
        writer.writeStartElement(GET_TYPES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeDirectAttribute(query.isDirect());
        writer.writeOWLObject(query.getIndividual(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(IsClassSatisfiable query) {
        writer.writeStartElement(IS_CLASS_SATISFIABLE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(IsDataPropertySatisfiable query) {
        writer.writeStartElement(IS_DATAPROPERTY_SATISFIABLE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(IsKBConsistentlyDeclared query) {
        writer.writeStartElement(IS_KB_CONSISTENTLY_DECLARED.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(IsKBSatisfiable query) {
        writer.writeStartElement(IS_KB_SATISFIABLE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(IsEntailed query) {
        writer.writeStartElement(IS_ENTAILED.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeOWLObject(query.getAxiom(), query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(IsEntailedDirect query) {
        writer.writeStartElement(IS_ENTAILED_DIRECT.getIRI());
        writer.writeKBAttribute(query.getKB());
        writer.writeOWLObject(query.getAxiom(), query.getKB());
        writer.writeEndElement();
    }

    @Override
    public void answer(IsObjectPropertySatisfiable query) {
        writer.writeStartElement(IS_OBJECTPROPERTY_SATISFIABLE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeOWLObject(query.getObject(), kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(LoadOntologies query) {
        writer.writeStartElement(LOAD_ONTOLOGIES.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        if (!query.isConsideringImports())
            writer.writeAttribute(CONSIDER_IMPORTS_ATTRIBUTE.getURI().toString(), Boolean.FALSE.toString());
        for (IRI ontologyIRI : query.getOntologyIRIs()) {
            writer.writeStartElement(ONTOLOGY_IRI.getIRI());
            writer.writeFullIRIAttribute(ontologyIRI);
            writer.writeEndElement();
        }
        for (IRIMapping irimapping : query.getIRIMapping()) {
            writer.writeStartElement(IRI_MAPPING.getIRI());
            writer.writeAttribute(KEY_ATTRIBUTE.getURI(), irimapping.key);
            writer.writeAttribute(VALUE_ATTRIBUTE.getURI(), irimapping.value.toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    @Override
    public void answer(Realize query) {
        writer.writeStartElement(REALIZE.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(ReleaseKB query) {
        writer.writeStartElement(RELEASE_KB.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeEndElement();
    }

    @Override
    public void answer(Set query) {
        writer.writeStartElement(SET.getIRI());
        final IRI kb = query.getKB();
        writer.writeKBAttribute(kb);
        writer.writeAttribute(KEY_ATTRIBUTE.getURI(), query.getKey());
        for (OWLlinkLiteral literal : query) {
            writer.writeStartElement(LITERAL.getIRI());
            writer.writeTextContent(literal.getValue());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    @Override
    public void answer(Tell request) {
        writer.writeStartElement(BuiltinRequestVocabulary.TELL.getURI());
        IRI kb = request.getKB();
        writer.writeKBAttribute(kb);
        for (OWLAxiom axiom : request) {
            writer.writeOWLObject(axiom, kb);
        }
        writer.writeEndElement();
    }

    @Override
    public void answer(Request<?> request) {
    }

    @Override
    public void render(Request request, OWLlinkWriter w) {
        this.writer = w;
        request.accept(this);
        //this.answer(request);
    }
}
