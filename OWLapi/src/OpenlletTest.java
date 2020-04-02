import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;

import openllet.owlapi.OpenlletReasoner;
import openllet.owlapi.OpenlletReasonerFactory;

public class OpenlletTest {
	public static void main(String[] args) throws OWLOntologyCreationException {
		// Get hold of an ontology manager
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory df = manager.getOWLDataFactory();
		// Ontology file
		File file = new File("C:\\Users\\Rita\\Desktop\\dementia.rdf");
		
		// Load the local copy
		OWLOntology localOnt = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: " + localOnt);
		
		/***************OPENLLET*****************************************************/
		final OpenlletReasoner reasoner = OpenlletReasonerFactory.getInstance().createReasoner(localOnt);
		System.out.println("done.");

		reasoner.getKB().realize();
		reasoner.getKB().printClassTree();
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		
		
	}
}

