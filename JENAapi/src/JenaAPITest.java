import java.util.Iterator;

import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class JenaAPITest {

	public static void main(String[] args) { 
		
		String SOURCE = args[0];// "C:\\Users\\Rita\\Desktop\\dementia.owl";
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		base.read(SOURCE, "OWL");
		
		String NS = base.getNsPrefixURI("");
		
		System.out.println( "file letto");
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF,base);
		System.out.println( "onotlogy model creato");
		OntClass Depression = base.getOntClass(NS+"Depression");
		Individual d1 = base.createIndividual(NS+"depressione",Depression);
		System.out.println( "classe e individuo creato");
		
		System.out.println(d1.listRDFTypes(false));
		for (Iterator<Resource> i = d1.listRDFTypes(false); i.hasNext(); ) {
		    System.out.println( d1.getURI() + " is asserted in class " + i.next() );
		}
		System.out.println( "inferenza creata");
		
		// list the inferred types
		d1 = inf.getIndividual( NS + "depressione" );
		System.out.println(d1.listRDFTypes(false));
		/*for (Iterator<Resource> i = p1.listRDFTypes(false); i.hasNext(); ) {
		    System.out.println( p1.getURI() + " is inferred to be in class " + i.next() );
		
		}*/
		System.out.println( "fine");
		
	}
}
