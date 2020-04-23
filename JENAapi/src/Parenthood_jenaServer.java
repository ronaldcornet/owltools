import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class Parenthood_jenaServer {

	
	public static ResultSet execSelectAndPrint(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = q.execSelect();
		  
		  
		ResultSetFormatter.out(System.out, results);

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			ResultSetFormatter.out(System.out,results);
			RDFNode o = soln.get("o");
			System.out.println(o);
			RDFNode s = soln.get("s");
			System.out.println(s);
			RDFNode p = soln.get("p");
			System.out.println(p);
		}
		return results;
	}

	public static void main(String[] args) {
	String connection = args[0]; //"http://localhost:8080/fuseki/Parenthood"
	String query = args[1]; //SELECT ?s ?p ?o WHERE { ?s ?p ?o .} LIMIT 10
	ResultSet results =		execSelectAndPrint(
						connection,
						query);
	
		
	}
}

