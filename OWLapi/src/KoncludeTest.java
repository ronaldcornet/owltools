import java.io.File;
import java.net.URL;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
public class KoncludeTest {

	public static void main(String[] args) {
		try {
			// create a test ontology
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			String path = args[0];
			OWLOntology ontology =logicalAxiomsOnly(path, manager); 
			OWLDataFactory dfactory = manager.getOWLDataFactory();
			OWLClass dementia = dfactory.getOWLClass(IRI.create("http://www.semanticweb.org/rita/ontologies/2020/1/untitled-ontology-2#Dementia"));
			/*OWLClass B = dfactory.getOWLClass(IRI.create("C:\\Users\\Rita\\Desktop\\dementia-empty.owl/#B"));
			OWLAxiom a = dfactory.getOWLSubClassOfAxiom(A, B);
			manager.addAxiom(ontology, a);*/
			
			// configure the server end-point
			URL url = new URL(args[1]);  //"http://localhost:8080"
			//OWLlinkReasonerConfiguration reasonerConfiguration = new OWLlinkReasonerConfiguration(url);
			OWLlinkHTTPXMLReasonerFactory factory = new OWLlinkHTTPXMLReasonerFactory();
			 	OWLReasoner reasoner = factory.createNonBufferingReasoner(ontology);
	 	
			System.out.println("Satisfiability of class " + dementia.toString() +": " + 
				reasoner.isSatisfiable(dementia));			
			
			// adding additional axioms to our test ontology to make class A unsatisfiable 
			OWLAxiom b = dfactory.getOWLSubClassOfAxiom(dementia, manager.getOWLDataFactory()
				.getOWLNothing());
			manager.addAxiom(ontology, b);

			System.out.println("After ontology changes, satisfiability of class " + 
					dementia.toString() +": " + reasoner.isSatisfiable(dementia));
			
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
	}
	
	protected static OWLOntology logicalAxiomsOnly(String path, OWLOntologyManager manager)
		    throws OWLOntologyCreationException {
			File file = new File(path);
		    OWLOntology l = manager.loadOntologyFromOntologyDocument(file);
		    return manager.createOntology(
		        l.importsClosure().flatMap(OWLOntology::logicalAxioms).collect(Collectors.toSet()));
		}
}

