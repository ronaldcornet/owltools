import java.io.File;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AxiomsCount {
	public static void main(String[] args) throws Exception {
	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	    File file = new File("C:\\Users\\Rita\\Desktop\\parenthood_jena\\parenthood_inferreBy_PELLET_from_JENA.txt");
	    OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
	    System.out.println("axioms " +ontology.getAxiomCount());
	    Set<OWLClass> classes = ontology.getClassesInSignature();
	   
	}
}
