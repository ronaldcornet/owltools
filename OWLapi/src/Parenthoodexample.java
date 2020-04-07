import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;

import openllet.owlapi.OpenlletReasoner;
import openllet.owlapi.OpenlletReasonerFactory;

public class Parenthoodexample {

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, MalformedURLException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		IRI iri = IRI.create("C:\\Users\\Rita\\Desktop\\parenthood.owl");
		
		//Create new ontology
		OWLOntology onto = man.createOntology(iri);
		OWLOntologyManager outputOntologyManager = OWLManager.createOWLOntologyManager();
		//Data Factory
		OWLDataFactory df = man.getOWLDataFactory();
		
		/************HERMIT**********************************************************/
		
		// get and configure a reasoner (HermiT)
		OWLReasonerFactory rf = new ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		
		// create the reasoner instance, classify and compute inferences
		OWLReasoner hermit = rf.createReasoner(onto, config);
		
		
		//Unsatisfiable classes
		System.out.println("Unsatisfiable Class: "+hermit.getUnsatisfiableClasses().getSize());
		for(org.semanticweb.owlapi.model.OWLClass c : hermit.getUnsatisfiableClasses()) {
			System.out.println(c);			
		}
	
		//System.out.println(df.getOWLNamedIndividual(iri+"#matteo"));
		NodeSet<OWLClass> superclasses = hermit.getTypes(df.getOWLNamedIndividual(iri+"#filippo"));
		for(Node<OWLClass> superclass : superclasses) {
			System.out.println(superclass);
					
		}
				

		// To generate an inferred ontology we use implementations of
		// inferred axiom generators
		List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
		gens.add(new InferredSubClassAxiomGenerator());
		gens.add(new InferredEquivalentClassAxiomGenerator());
		// Put the inferred axioms into a fresh empty ontology.
		OWLOntology infOnt = outputOntologyManager.createOntology();
		InferredOntologyGenerator iog = new InferredOntologyGenerator(hermit,
				gens);
		iog.fillOntology(outputOntologyManager.getOWLDataFactory(), infOnt);

		// Save the inferred ontology.
		outputOntologyManager.saveOntology(infOnt,
				IRI.create((new File("C:\\Users\\Rita\\Desktop\\parenthood_hermit.owl").toURI())));

		// Terminate the worker threads used by the reasoner.
		hermit.dispose();
		
		/***************************************KONCLUDE********************************************/
		
		// configure the server end-point
		URL url = new URL("http://localhost:8080");
		//OWLlinkReasonerConfiguration reasonerConfiguration = new OWLlinkReasonerConfiguration(url);
		OWLlinkHTTPXMLReasonerFactory factory = new OWLlinkHTTPXMLReasonerFactory();
		 	OWLReasoner konclude = factory.createNonBufferingReasoner(onto);
		 	
		 	List<InferredAxiomGenerator<? extends OWLAxiom>> gens_konclude = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
			gens_konclude.add(new InferredSubClassAxiomGenerator());
			gens_konclude.add(new InferredEquivalentClassAxiomGenerator());
			// Put the inferred axioms into a fresh empty ontology.
			OWLOntology infOnt_konclude = outputOntologyManager.createOntology();
			InferredOntologyGenerator iog_konclude = new InferredOntologyGenerator(konclude,
					gens_konclude);
			iog_konclude.fillOntology(outputOntologyManager.getOWLDataFactory(), infOnt_konclude);

			// Save the inferred ontology.
			outputOntologyManager.saveOntology(infOnt_konclude,
					IRI.create((new File("C:\\Users\\Rita\\Desktop\\parenthood_konclude.owl").toURI())));

			// Terminate the worker threads used by the reasoner.
			konclude.dispose();
		/****************PELLET******************************************************************/
			
		final OpenlletReasoner openllet = OpenlletReasonerFactory.getInstance().createReasoner(onto);
		System.out.println("openllet done");

		openllet.getKB().realize();
		openllet.getKB().printClassTree();
		openllet.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		
		List<InferredAxiomGenerator<? extends OWLAxiom>> gens_openllet = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
		gens_openllet.add(new InferredSubClassAxiomGenerator());
		gens_openllet.add(new InferredEquivalentClassAxiomGenerator());
		// Put the inferred axioms into a fresh empty ontology.
		OWLOntology infOnt_openllet = outputOntologyManager.createOntology();
		InferredOntologyGenerator iog_openllet = new InferredOntologyGenerator(openllet,
				gens_openllet);
		iog_konclude.fillOntology(outputOntologyManager.getOWLDataFactory(), infOnt_openllet);

		// Save the inferred ontology.
		outputOntologyManager.saveOntology(infOnt_openllet,
				IRI.create((new File("C:\\Users\\Rita\\Desktop\\parenthood_openllet.owl").toURI())));

		// Terminate the worker threads used by the reasoner.
		openllet.dispose();
		
	}
	

}
