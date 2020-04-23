import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class JENASPARQL {
	  public static void main(String[]args)
      {
		  String SOURCE = args[0]; //"C:\\Users\\Rita\\Desktop\\dementia.rdf or C:\\Users\\Rita\\Desktop\\wine.rdf";
		  
		  
		  OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		  model.read(SOURCE, "RDF/XML");
		  
		  String queryString =  "SELECT ?s ?p ?o WHERE {"+
                  "?s ?p ?o ."+
                  "} LIMIT 20";
		
		  
		  //a query is created from a string using QueryFactory
		  Query query = QueryFactory.create(queryString);
		  
		  //The query and model or RDF Dataset to be queried are then passed to QueryExecutionFastory
		  //QueryExecution objects are java.lang.AutoCloseable. 
		  try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
			    ResultSet results = qexec.execSelect() ;
			    for ( ; results.hasNext() ; )
			    {
			      QuerySolution soln = results.nextSolution() ;
			      
			      System.out.println(soln);
			    }
			    //resultSetFormatter can be used instead of a loop to deal with each row in the result set
			    //ResultSetFormatter.out(System.out,results);
			    // this return empty result, maybe it depends on jena versions
			    // https://stackoverflow.com/questions/23899841/jenas-subquery-intersection-returns-empty-contrary-to-the-results-from-prot%C3%A9g%C3%A9
		  }
	
	}
}
