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

package org.semanticweb.owlapi.owllink;

import org.semanticweb.owlapi.model.IRI;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Olaf Noppens
 */
public enum OWLlinkXMLVocabulary {
    /** build                       */ BUILD_ATTRIBUTE("build"),
    /** considerImports             */ CONSIDER_IMPORTS_ATTRIBUTE("considerImports"),
    /** direct                      */ DIRECT_ATTRIBUTE("direct"),
    /** error                       */ ERROR_ATTRIBUTE("error"),
    /** identifier                  */ IDENTIFIER_ATTRIBUTE("identifier"),
    /** IRI                         */ IRI_ATTRIBUTE("IRI"),
    /** IRIMapping                  */ IRI_MAPPING("IRIMapping"),
    /** fullIRI                     */ FULL_IRI_ATTRIBUTE("fullIRI"),
    /** kb                          */ KB_ATTRIBUTE("kb"),
    /** key                         */ KEY_ATTRIBUTE("key"),
    /** name                        */ NAME_Attribute("name"),
    /** negative                    */ NEGATIVE_ATTRIBUTE("negative"),
    /** major                       */ MAJOR_ATTRIBUTE("major"),
    /** minor                       */ MINOR_ATTRIBUTE("minor"),
    /** message                     */ MESSAGE_ATTRIBUTE("message"),
    /** Configuration               */ CONFIGURATION("Configuration"),
    /** RequestMessage              */ REQUEST_MESSAGE("RequestMessage"),
    /** result                      */ RESULT_ATTRIBUTE("result"),
    /** ResponseMessage             */ RESPONSE_MESSAGE("ResponseMessage"),
    /** warning                     */ WARNING_ATTRIBUTE("warning"),
    /** value                       */ VALUE_ATTRIBUTE("value"),

    /** AreClassesDisjoint          */ ARE_CLASSES_DISJOINT("AreClassesDisjoint"),
    /** AreClassesEquivalent        */ ARE_CLASSES_EQUIVALENT("AreClassesEquivalent"),
    /** AreDataPropertiesDisjoint   */ ARE_DATAPROPERTIES_DISJOINT("AreDataPropertiesDisjoint"),
    /** AreDataPropertiesEquivalent */ ARE_DATAPROPERTIES_EQUIVALENT("AreDataPropertiesEquivalent"),
    /** AreIndividualsDisjoint      */ ARE_INDIVIDUALS_DISJOINT("AreIndividualsDisjoint"),
    /** AreIndividualsEquivalent    */ ARE_INDIVIDUALS_EQUIVALENT("AreIndividualsEquivalent"),
    /** AreIndividualsRelated       */ ARE_INDIVIDUALS_RELATED("AreIndividualsRelated"),
    /** AreObjectPropertiesDisjoint */ ARE_OBJECTPROPERTIES_DISJOINT("AreObjectPropertiesDisjoint"),
    /** AreDataPropertiesEquivalent */ ARE_OBJECTPROPERTIES_EQUIVALENT("AreDataPropertiesEquivalent"),
    /** Classify                    */ CLASSIFY("Classify"),
    /** CreateKB                    */ CREATE_KB("CreateKB"),
    /** Description                 */ DESCRIPTION("Description"),
    /** fullIRI                     */ FULLIRI("fullIRI"),
    /** GetAllAnnotationProperties  */ GET_ALL_ANNOTATION_PROPERTIES("GetAllAnnotationProperties"),
    /** GetAllClasses               */ GET_ALL_CLASSES("GetAllClasses"),
    /** GetAllDataProperties        */ GET_ALL_DATAPROPERTIES("GetAllDataProperties"),
    /** GetAllDatatypes             */ GET_ALL_DATATYPES("GetAllDatatypes"),
    /** GetAllIndividuals           */ GET_ALL_INDIVIDUALS("GetAllIndividuals"),
    /** GetAllObjectProperties      */ GET_ALL_OBJECTPROPERTIES("GetAllObjectProperties"),
    /** GetDataPropertiesBetween    */ GET_DATAPROPERTIES_BETWEEN("GetDataPropertiesBetween"),
    /** GetDataPropertiesOfLiteral  */ GET_DATAPROPERTIES_OF_LITERAL("GetDataPropertiesOfLiteral"),
    /** GetDataPropertiesOfSource   */ GET_DATAPROPERTIES_OF_SOURCE("GetDataPropertiesOfSource"),
    /** GetDataPropertySources      */ GET_DATAPROPERTY_SOURCES("GetDataPropertySources"),
    /** GetDataPropertyTargets      */ GET_DATAPROPERTY_TARGETS("GetDataPropertyTargets"),
    /** GetDescription              */ GET_DESCRIPTION("GetDescription"),
    /** GetDisjointClasses          */ GET_DISJOINT_CLASSES("GetDisjointClasses"),
    /** GetDisjointDataProperties   */ GET_DISJOINT_DATAPROPERTIES("GetDisjointDataProperties"),
    /** GetDifferentIndividuals     */ GET_DIFFERENT_INDIVIDUALS("GetDifferentIndividuals"),
    /** GetDisjointObjectProperties */ GET_DISJOINT_OBJECTPROPERTIES("GetDisjointObjectProperties"),
    /** GetEquivalentClasses        */ GET_EQUIVALENT_CLASSES("GetEquivalentClasses"),
    /** GetEquivalentDataProperties */ GET_EQUIVALENT_DATAPROPERTIES("GetEquivalentDataProperties"),
    /** GetSameIndividuals          */ GET_SAME_INDIVIDUALS("GetSameIndividuals"),
    /** GetEquivalentObjectProperties       */ GET_EQUIVALENT_OBJECTPROPERTIES("GetEquivalentObjectProperties"),
    /** GetFlattenedDataPropertySources     */ GET_FLATTENED_DATAPROPERTY_SOURCES("GetFlattenedDataPropertySources"),
    /** GetFlattenedDifferentIndividuals    */ GET_FLATTENED_DIFFERENT_INDIVIDUALS("GetFlattenedDifferentIndividuals"),
    /** GetFlattenedInstances               */ GET_FLATTENED_INSTANCES("GetFlattenedInstances"),
    /** GetFlattenedObjectPropertySources   */ GET_FLATTENED_OBJECTPROPERTY_SOURCES("GetFlattenedObjectPropertySources"),
    /** GetFlattenedObjectPropertyTargets   */ GET_FLATTENED_OBJECTPROPERTY_TARGETS("GetFlattenedObjectPropertyTargets"),
    /** GetFlattenedTypes           */ GET_FLATTENED_TYPES("GetFlattenedTypes"),
    /** GetInstances                */ GET_INSTANCES("GetInstances"),
    /** GetKBLanguage               */ GET_KB_LANGUAGE("GetKBLanguage"),
    /** GetPrefixes                 */ GET_PREFIXES("GetPrefixes"),
    /** GetObjectPropertiesBetween  */ GET_OBJECTPROPERTIES_BETWEEN("GetObjectPropertiesBetween"),
    /** GetObjectPropertiesOfSource */ GET_OBJECTPROPERTIES_OF_SOURCE("GetObjectPropertiesOfSource"),
    /** GetObjectPropertiesOfTarget */ GET_OBJECTPROPERTIES_OF_TARGET("GetObjectPropertiesOfTarget"),
    /** GetObjectPropertySources    */ GET_OBJECTPROPERTY_SOURCES("GetObjectPropertySources"),
    /** GetObjectPropertyTargets    */ GET_OBJECTPROPERTY_TARGETS("GetObjectPropertyTargets"),
    /** GetSettings                 */ GET_SETTINGS("GetSettings"),
    /** GetSubClasses               */ GET_SUBCLASSES("GetSubClasses"),
    /** GetSubClassHierarchy        */ GET_SUBCLASS_HIERARCHY("GetSubClassHierarchy"),
    /** GetSubDataProperties        */ GET_SUBDATAPROPERTIES("GetSubDataProperties"),
    /** GetSubDataPropertyHierarchy */ GET_SUBDATAPROPERTY_HIERARCHY("GetSubDataPropertyHierarchy"),
    /** GetSubObjectProperties      */ GET_SUBOBJECTPROPERTIES("GetSubObjectProperties"),
    /** GetSubObjectPropertyHierarchy   */ GET_SUBOBJECTPROPERTY_HIERARCHY("GetSubObjectPropertyHierarchy"),
    /** GetSuperClasses                 */ GET_SUPERCLASSES("GetSuperClasses"),
    /** GetSuperDataProperties          */ GET_SUPERDATAPROPERTIES("GetSuperDataProperties"),
    /** GetSuperObjectProperties        */ GET_SUPEROBJECTPROPERTIES("GetSuperObjectProperties"),
    /** GetTypes                    */ GET_TYPES("GetTypes"),
    /** IsClassSatisfiable          */ IS_CLASS_SATISFIABLE("IsClassSatisfiable"),
    /** IsEntailed                  */ IS_ENTAILED("IsEntailed"),
    /** IsEntailedDirect            */ IS_ENTAILED_DIRECT("IsEntailedDirect"),
    /** IsKBConsistentlyDeclared    */ IS_KB_CONSISTENTLY_DECLARED("IsKBConsistentlyDeclared"),
    /** IsDataPropertySatisfiable   */ IS_DATAPROPERTY_SATISFIABLE("IsDataPropertySatisfiable"),
    /** IsObjectPropertySatisfiable */ IS_OBJECTPROPERTY_SATISFIABLE("IsObjectPropertySatisfiable"),
    /** IsKBSatisfiable             */ IS_KB_SATISFIABLE("IsKBSatisfiable"),
    /** KBError                     */ KBERROR("KBError"),
    /** LoadOntologies              */ LOAD_ONTOLOGIES("LoadOntologies"),
    /** NotSupportedDatatypeError   */ NOTSUPPORTEDDATATYPEERROR("NotSupportedDatatypeError"),
    /** OntologyIRI                 */ ONTOLOGY_IRI("OntologyIRI"),
    /** ProfileViolationError       */ PROFILEVIOLATIONERROR("ProfileViolationError"),
    /** Realize                     */ REALIZE("Realize"),
    /** ReleaseKB                   */ RELEASE_KB("ReleaseKB"),
    /** Set                         */ SET("Set"),
    /** Tell                        */ TELL("Tell"),
    /** Setting                     */ SETTING("Setting"),
    /** Settings                    */ SETTINGS("Settings"),
    /** UnsatisfiableKBError        */ UNSATISFIABLEKBERROR("UnsatisfiableKBError"),
    /** Prefix                      */ PREFIX("Prefix"),
    /** Property                    */ PROPERTY("Property"),
    /** PublicKB                    */ PublicKB("PublicKB"),
    /** SupportedExtension          */ SUPPORTEDEXTENSION("SupportedExtension"),
    /** Literal                     */ LITERAL("Literal"),
    /** OneOf                       */ ONEOF("OneOf"),
    /** List                        */ LIST("List"),
    /** Datatype                    */ DATATYPE("Datatype"),
    /** ProtocolVersion             */ PROTOCOLVERSION("ProtocolVersion"),
    /** ReasonerVersion             */ REASONERVERSION("ReasonerVersion"),

    /** BooleanResponse             */ BOOLEAN_RESPONSE("BooleanResponse"),
    /** Classes                     */ CLASSES("Classes"),
    /** ClassHierarchy              */ CLASS_HIERARCHY("ClassHierarchy"),
    /** ClassSubClassesPair         */ CLASS_SUBCLASSESPAIR("ClassSubClassesPair"),
    /** ClassSynset                 */ CLASS_SYNSET("ClassSynset"),
    /** ClassSynsets                */ CLASS_SYNSETS("ClassSynsets"),
    /** DataPropertyHierarchy       */ DATAPROPERTY_HIERARCHY("DataPropertyHierarchy"),
    /** DataPropertySubDataPropertiesPair */ DATAPROPERTY_SUBDATAPROPERTIESPAIR("DataPropertySubDataPropertiesPair"),
    /** DataPropertySynset          */ DATAPROPERTY_SYNSET("DataPropertySynset"),
    /** DataPropertySynsets         */ DATAPROPERTY_SYNSETS("DataPropertySynsets"),
    /** DataPropertySynonyms        */ DATAPROPERTY_SYNONYMS("DataPropertySynonyms"),
    /** Error                       */ ERROR("Error"),
    /** IndividualSynset            */ INDIVIDUAL_SYNSET("IndividualSynset"),
    /** IndividualSynonyms          */ INDIVIDUAL_SYNONYMS("IndividualSynonyms"),
    /** KBError                     */ KB_ERROR("KBError"),
    /** KB                          */ KB_RESPONSE("KB"),
    /** ObjectPropertyHierarchy     */ OBJECTPROPERTY_HIERARCHY("ObjectPropertyHierarchy"),
    /** ObjectPropertySubObjectPropertiesPair   */ OBJECTPROPERTY_SUBOBJECTPROPERTIESPAIR("ObjectPropertySubObjectPropertiesPair"),
    /** ObjectPropertySynset        */ OBJECTPROPERTY_SYNSET("ObjectPropertySynset"),
    /** ObjectPropertySynsets       */ OBJECTPROPERTY_SYNSETS("ObjectPropertySynsets"),
    /** OK                          */ OK("OK"),
    /** Prefixes                    */ PREFIXES("Prefixes"),
    /** SetOfAnnotationProperties   */ SET_OF_ANNOTATIONPROPERTIES("SetOfAnnotationProperties"),
    /** SetOfClasses                */ SET_OF_CLASSES("SetOfClasses"),
    /** SetOfClassSynsets           */ SET_OF_CLASS_SYNSETS("SetOfClassSynsets"),
    /** SetOfDataProperties         */ SET_OF_DATAPROPERTIES("SetOfDataProperties"),
    /** SetOfDataPropertySynsets    */ SET_OF_DATAPROPERTY_SYNSETS("SetOfDataPropertySynsets"),
    /** SetOfDatatypes              */ SET_OF_DATATYPES("SetOfDatatypes"),
    /** SetOfIndividuals            */ SET_OF_INDIVIDUALS("SetOfIndividuals"),
    /** SetOfIndividualSynsets      */ SET_OF_INDIVIDUALS_SYNSETS("SetOfIndividualSynsets"),
    /** SetOfLiterals               */ SET_OF_LITERALS("SetOfLiterals"),
    /** SetOfObjectProperties       */ SET_OF_OBJECTPROPERTIES("SetOfObjectProperties"),
    /** SetOfObjectPropertySynsets  */ SET_OF_OBJECTPROPERTY_SYNSETS("SetOfObjectPropertySynsets"),
    /** SubClassSynsets             */ SUBCLASS_SYNSETS("SubClassSynsets"),
    /** SubDataPropertySynsets      */ SUBDATAPROPERTY_SYNSETS("SubDataPropertySynsets"),
    /** SubObjectPropertySynsets    */ SUBOBJECTPROPERTY_SYNSETS("SubObjectPropertySynsets"),
    /** Unknown                     */ UNKNOWN_RESPONSE("Unknown"),
    /** SyntaxError                 */ SYNTAX_ERROR("SyntaxError"),
    /** SemanticError               */ SEMANTIC_ERROR("SemanticError"),
    /** StringResponse              */ String_RESPONSE("StringResponse");

    private IRI iri;

    private String shortName;

    OWLlinkXMLVocabulary(String name) {
        this.iri = IRI.create(OWLlinkNamespaces.OWLLink + "#" + name);
        shortName = name;
    }

    /** @return iri*/
    public IRI getIRI() {
        return iri;
    }

    /** @return iri*/
    public IRI getURI() {
        return iri;
    }

    /** @return short name*/
    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return iri.toString();
    }

    static Set<IRI> BUILT_IN_URIS;

    static {
        BUILT_IN_URIS = new HashSet<>();
        for (OWLlinkXMLVocabulary v : OWLlinkXMLVocabulary.values()) {
            BUILT_IN_URIS.add(v.getIRI());
        }
    }

}
