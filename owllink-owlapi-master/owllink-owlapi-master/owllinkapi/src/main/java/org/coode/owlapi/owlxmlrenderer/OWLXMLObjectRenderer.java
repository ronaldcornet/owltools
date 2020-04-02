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
package org.coode.owlapi.owlxmlrenderer;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import org.coode.owlapi.owlxmlrenderer.OWLXMLWriter;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 12-Dec-2006
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private OWLXMLWriter writer;

    /**
     * @param writer
     *        writer
     */
    public OWLXMLObjectRenderer(OWLXMLWriter writer) {
        this.writer = writer;
    }

    private void accept(OWLObject o) {
        o.accept(this);
    }

    private void writeAnnotations(OWLAxiom axiom) {
        axiom.annotations().forEach(this::accept);
    }

    @Override
    public void visit(OWLOntology ontology) {
        ontology.importsDeclarations().forEach(this::writeImport);
        ontology.annotations().forEach(this::accept);
        ontology.axioms().sorted().forEach(this::accept);
    }

    protected void writeImport(OWLImportsDeclaration decl) {
        writer.writeStartElement(IMPORT);
        writer.writeTextContent(decl.getIRI().toString());
        writer.writeEndElement();
    }

    @Override
    public void visit(IRI iri) {
        writer.writeIRIElement(iri);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        writer.writeStartElement(ANONYMOUS_INDIVIDUAL);
        writer.writeNodeIDAttribute(individual.getID());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(ASYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writer.writeStartElement(CLASS_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getClassExpression());
        accept(axiom.getIndividual());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getSubject());
        accept(axiom.getObject());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getDomain());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_RANGE);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getRange());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_DATA_PROPERTY_OF);
        writeAnnotations(axiom);
        accept(axiom.getSubProperty());
        accept(axiom.getSuperProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writer.writeStartElement(DECLARATION);
        writeAnnotations(axiom);
        accept(axiom.getEntity());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS);
        writeAnnotations(axiom);
        axiom.individuals().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        writer.writeStartElement(DISJOINT_CLASSES);
        writeAnnotations(axiom);
        axiom.classExpressions().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        axiom.properties().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        axiom.properties().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writer.writeStartElement(DISJOINT_UNION);
        writeAnnotations(axiom);
        accept(axiom.getOWLClass());
        axiom.classExpressions().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writer.writeStartElement(ANNOTATION_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getSubject());
        accept(axiom.getValue());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_CLASSES);
        writeAnnotations(axiom);
        axiom.classExpressions().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        axiom.properties().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        axiom.properties().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_DATA_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writer.writeStartElement(INVERSE_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        accept(axiom.getFirstProperty());
        accept(axiom.getSecondProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(IRREFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getSubject());
        accept(axiom.getObject());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getSubject());
        accept(axiom.getObject());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getSubject());
        accept(axiom.getObject());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        writer.writeStartElement(OBJECT_PROPERTY_CHAIN);
        axiom.getPropertyChain().forEach(this::accept);
        writer.writeEndElement();
        accept(axiom.getSuperProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getDomain());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_RANGE);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getRange());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        accept(axiom.getSubProperty());
        accept(axiom.getSuperProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(REFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        writer.writeStartElement(SAME_INDIVIDUAL);
        writeAnnotations(axiom);
        axiom.individuals().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writer.writeStartElement(SUB_CLASS_OF);
        writeAnnotations(axiom);
        accept(axiom.getSubClass());
        accept(axiom.getSuperClass());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(SYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(TRANSITIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLClass desc) {
        writer.writeStartElement(CLASS);
        writer.writeIRIAttribute(desc.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        writer.writeStartElement(DATA_ALL_VALUES_FROM);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataExactCardinality desc) {
        writer.writeStartElement(DATA_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataMaxCardinality desc) {
        writer.writeStartElement(DATA_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
        writer.writeStartElement(DATA_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom desc) {
        writer.writeStartElement(DATA_SOME_VALUES_FROM);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataHasValue desc) {
        writer.writeStartElement(DATA_HAS_VALUE);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        writer.writeStartElement(OBJECT_ALL_VALUES_FROM);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectComplementOf desc) {
        writer.writeStartElement(OBJECT_COMPLEMENT_OF);
        accept(desc.getOperand());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        writer.writeStartElement(OBJECT_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        writer.writeStartElement(OBJECT_INTERSECTION_OF);
        desc.operands().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        writer.writeStartElement(OBJECT_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        writer.writeStartElement(OBJECT_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        accept(desc.getProperty());
        if (desc.isQualified()) {
            accept(desc.getFiller());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectOneOf desc) {
        writer.writeStartElement(OBJECT_ONE_OF);
        desc.individuals().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectHasSelf desc) {
        writer.writeStartElement(OBJECT_HAS_SELF);
        accept(desc.getProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        writer.writeStartElement(OBJECT_SOME_VALUES_FROM);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        writer.writeStartElement(OBJECT_UNION_OF);
        desc.operands().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        writer.writeStartElement(OBJECT_HAS_VALUE);
        accept(desc.getProperty());
        accept(desc.getFiller());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        writer.writeStartElement(DATA_COMPLEMENT_OF);
        accept(node.getDataRange());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        writer.writeStartElement(DATA_ONE_OF);
        node.values().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatype node) {
        writer.writeStartElement(DATATYPE);
        writer.writeIRIAttribute(node.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        writer.writeStartElement(DATATYPE_RESTRICTION);
        accept(node.getDatatype());
        node.facetRestrictions().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        writer.writeStartElement(FACET_RESTRICTION);
        writer.writeFacetAttribute(node.getFacet());
        accept(node.getFacetValue());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLLiteral node) {
        writer.writeStartElement(LITERAL);
        if (node.hasLang()) {
            writer.writeLangAttribute(node.getLang());
        }
        writer.writeDatatypeAttribute(node.getDatatype());
        writer.writeTextContent(node.getLiteral());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataProperty property) {
        writer.writeStartElement(DATA_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectProperty property) {
        writer.writeStartElement(OBJECT_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        writer.writeStartElement(OBJECT_INVERSE_OF);
        accept(property.getInverse());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        writer.writeStartElement(NAMED_INDIVIDUAL);
        writer.writeIRIAttribute(individual.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        writer.writeStartElement(HAS_KEY);
        writeAnnotations(axiom);
        accept(axiom.getClassExpression());
        axiom.objectPropertyExpressions().forEach(this::accept);
        axiom.dataPropertyExpressions().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        writer.writeStartElement(DATA_INTERSECTION_OF);
        node.operands().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        writer.writeStartElement(DATA_UNION_OF);
        node.operands().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        writer.writeStartElement(ANNOTATION_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotation annotation) {
        writer.writeStartElement(ANNOTATION);
        annotation.annotations().forEach(this::accept);
        accept(annotation.getProperty());
        accept(annotation.getValue());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getDomain());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_RANGE);
        writeAnnotations(axiom);
        accept(axiom.getProperty());
        accept(axiom.getRange());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_ANNOTATION_PROPERTY_OF);
        writeAnnotations(axiom);
        accept(axiom.getSubProperty());
        accept(axiom.getSuperProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writer.writeStartElement(DATATYPE_DEFINITION);
        writeAnnotations(axiom);
        accept(axiom.getDatatype());
        accept(axiom.getDataRange());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLRule rule) {
        writer.writeStartElement(DL_SAFE_RULE);
        writeAnnotations(rule);
        writer.writeStartElement(BODY);
        rule.body().forEach(this::accept);
        writer.writeEndElement();
        writer.writeStartElement(HEAD);
        rule.head().forEach(this::accept);
        writer.writeEndElement();
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLClassAtom node) {
        writer.writeStartElement(CLASS_ATOM);
        accept(node.getPredicate());
        accept(node.getArgument());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        writer.writeStartElement(DATA_RANGE_ATOM);
        accept(node.getPredicate());
        accept(node.getArgument());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        writer.writeStartElement(OBJECT_PROPERTY_ATOM);
        accept(node.getPredicate());
        accept(node.getFirstArgument());
        accept(node.getSecondArgument());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        writer.writeStartElement(DATA_PROPERTY_ATOM);
        accept(node.getPredicate());
        accept(node.getFirstArgument());
        accept(node.getSecondArgument());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        writer.writeStartElement(BUILT_IN_ATOM);
        writer.writeIRIAttribute(node.getPredicate());
        node.arguments().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLVariable node) {
        writer.writeStartElement(VARIABLE);
        writer.writeIRIAttribute(node.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        accept(node.getLiteral());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS_ATOM);
        accept(node.getFirstArgument());
        accept(node.getSecondArgument());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        writer.writeStartElement(SAME_INDIVIDUAL_ATOM);
        accept(node.getFirstArgument());
        accept(node.getSecondArgument());
        writer.writeEndElement();
    }
}
