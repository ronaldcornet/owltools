import java.io.File;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.InfGraph;


import openllet.core.KnowledgeBase;
import openllet.jena.PelletInfGraph;
import openllet.jena.PelletReasoner;
import openllet.jena.PelletReasonerFactory;

import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;

public class JENAOWLCombination {

//	public static void main(String[] args) throws OWLOntologyCreationException {
//		// Get hold of an ontology manager
//	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//	    String SOURCE = args[0]; 
//	    File file = new File(SOURCE);
//	            // Load the local copy
//	    OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
//	    
//	    OntModel ontmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
//	    ontmodel.read(SOURCE, "OWL");
//		String NS = ontmodel.getNsPrefixURI("");
//
//		final OpenlletReasoner reasoner = OpenlletReasonerFactory.getInstance().createReasoner(ontology);
//
//	    KnowledgeBase kb = reasoner.getKB();
////	    PelletInfGraph graph = new org.mindswap.pellet.jena.PelletReasoner().bind( kb );
//	    InfModel model = ModelFactory.createInfModel( (InfGraph) kb );
//
//	    String PREFIX = "PREFIX proj:<"+NS+"> ";
//	            
//	    String SELECT = "SELECT ?s ?p ?o ";
//	    String WHERE = "WHERE {proj:maria ?p ?o .}" ;
//
//	    QueryExecution qe = SparqlDLExecutionFactory.create(QueryFactory.create(PREFIX + SELECT + WHERE), model);
//	    ResultSet rs = qe.execSelect();
//	    ResultSetFormatter.out(System.out,rs);
//	    rs = null;  qe.close();
//
//	    reasoner.dispose();
//
//	    //OWLReasonerSPARQLEngine sparqlEngine=new OWLReasonerSPARQLEngine(new MinimalPrintingMonitor());
//	    //sparqlEngine.execQuery(str.toString(),dataset);
//
//	    System.out.println("Loaded ontology: " + ontology);
//	}

}
