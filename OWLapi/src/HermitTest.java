import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;

public class HermitTest {

	public static void main(String[] args) throws OWLOntologyCreationException {
		// Get hold of an ontology manager
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory df = manager.getOWLDataFactory();
		// Ontology file
		File file = new File("C:\\Users\\Rita\\Desktop\\dementia.rdf");
		
		// Load the local copy
		OWLOntology localOnt = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: " + localOnt);
		
		
		/************REASONERS TEST**************************************************/
		/************HERMIT**********************************************************/
//		OWLReasonerFactory rf = new ReasonerFactory();
//		OWLReasoner r = rf.createReasoner(localOnt);
//		r.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		
		// get and configure a reasoner (HermiT)
		ReasonerFactory rf = new ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		
		// create the reasoner instance, classify and compute inferences
		OWLReasoner r = rf.createReasoner(localOnt, config);
		
		
		//Unsatisfiable classes
		System.out.println("Unsatisfiable Class: "+r.getUnsatisfiableClasses().getSize());
		for(org.semanticweb.owlapi.model.OWLClass c : r.getUnsatisfiableClasses()) {
			System.out.println(c);			
		}

		//Query the inferred ontology
		NodeSet<OWLClass> subclasses = r.getSubClasses(df.getOWLClass("http://www.semanticweb.org/rita/ontologies/2020/1/untitled-ontology-2"+"#Disease"));
		for(Node<OWLClass> subclass : subclasses) {
			System.out.println(subclass);
					
		}
		
		
	}

}
