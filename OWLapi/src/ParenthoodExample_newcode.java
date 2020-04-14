import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredIndividualAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

public class ParenthoodExample_newcode {
	public static void main(String[] args) throws Exception {
	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	    OWLOntology ontology = manager.createOntology(
	        IRI.create("C:\\Users\\Rita\\Desktop\\parenthood.owl"));
	    OWLDataFactory df = manager.getOWLDataFactory();

	    Configuration configuration = new Configuration();
	    configuration.ignoreUnsupportedDatatypes = true;
	    OWLReasonerFactory rf = new ReasonerFactory();

	    OWLReasoner reasoner = rf.createReasoner(ontology, configuration);
	    boolean consistencyCheck = reasoner.isConsistent();
	    if (consistencyCheck) {
	        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY,
	            InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_HIERARCHY,
	            InferenceType.DATA_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS);

	        List<InferredAxiomGenerator<? extends OWLAxiom>> generators = new ArrayList<>();
	        generators.add(new InferredSubClassAxiomGenerator());
	        generators.add(new InferredClassAssertionAxiomGenerator());
	        generators.add(new InferredDataPropertyCharacteristicAxiomGenerator());
	        generators.add(new InferredEquivalentClassAxiomGenerator());
	        generators.add(new InferredEquivalentDataPropertiesAxiomGenerator());
	        generators.add(new InferredEquivalentObjectPropertyAxiomGenerator());
	        generators.add(new InferredInverseObjectPropertiesAxiomGenerator());
	        generators.add(new InferredObjectPropertyCharacteristicAxiomGenerator());

	        // NOTE: InferredPropertyAssertionGenerator significantly slows down
	        // inference computation
	        generators.add(new org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator());

	        generators.add(new InferredSubClassAxiomGenerator());
	        generators.add(new InferredSubDataPropertyAxiomGenerator());
	        generators.add(new InferredSubObjectPropertyAxiomGenerator());
	        List<InferredIndividualAxiomGenerator<? extends OWLIndividualAxiom>> individualAxioms =
	            new ArrayList<>();
	        generators.addAll(individualAxioms);

	        generators.add(new InferredDisjointClassesAxiomGenerator());
	        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, generators);
	        OWLOntology inferredAxiomsOntology = manager.createOntology();
	        iog.fillOntology(df, inferredAxiomsOntology);
	        for(InferredAxiomGenerator<?> i : iog.getAxiomGenerators()) {
	        System.out.println(i);}
	        File inferredOntologyFile = new File("C:\\Users\\Rita\\Desktop\\output.txt");
	        // Now we create a stream since the ontology manager can then write to that stream.
	        try (OutputStream outputStream = new FileOutputStream(inferredOntologyFile)) {
	            // We use the same format as for the input ontology.
	            manager.saveOntology(inferredAxiomsOntology, outputStream);
	        }
	        System.out.println("done");
	    } // End if consistencyCheck
	    else {
	        System.out.println("Inconsistent input Ontology, Please check the OWL File");
	    }
	}
}
