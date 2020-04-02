import java.util.Iterator;

import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JenaApi {
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		
		OntModel base = ModelFactory.createOntologyModel();
		base.read("C:\\Users\\Rita\\Desktop\\dementia.rdf", "RDF/XML");
		
		String NS = "C:\\Users\\Rita\\Desktop\\DementiaOnto#";
		
		
		OntClass disease = base.getOntClass(NS+"Disease");
		OntClass dementia = base.getOntClass(NS+"Dementia");
		OntClass vad = base.getOntClass(NS+"VAD");
		OntClass depression = base.getOntClass(NS+"depression");
		
		Individual depressione = base.createIndividual(NS+ "depressione", depression);
		System.out.println(depressione.getRDFType()); //error --> NULL
		
		/*for (Iterator<Resource> i = dementia_ind.listRDFTypes(true); i.hasNext(); ) {
		  System.out.println( dementia_ind.getURI() + " is asserted in class "+ i.next());
		}*/
	
		//create the reasoning model using the base 
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, base);
		depressione = inf.getIndividual(NS+"depressione"); //error --> NULL
		

		
		
		
	}

}
