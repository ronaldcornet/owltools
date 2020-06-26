import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class Connection_test {

	public static void main(String[] args) {
		String connectionString = args[0]; 
		String queryString =  connectionString+"/query?force=true";
		String updateString = connectionString+"/update";
		String graphStoreProtocolString = connectionString+"/data";
		
		//RDFConnection Interface for SPARQL operations on a datasets, whether local or remote.
		//RDFConnectionFactory.connect --> Create a connection specifying the URLs of the service.
		RDFConnection conn = RDFConnectionFactory.connect(queryString,updateString,graphStoreProtocolString);
		
		//load method = Load RDF into the default graph of a dataset. This is SPARQL Graph Store Protocol HTTP POST or equivalent.
				conn.load(args[1]) ;      //"C:\\Users\\Rita\\Desktop\\parenthood.owl"    
				QueryExecution qExec = conn.query("SELECT DISTINCT ?s { ?s ?p ?o }") ;
				ResultSet rs = qExec.execSelect() ;
				while(rs.hasNext()) {
				    QuerySolution qs = rs.next() ;
				    Resource subject = qs.getResource("s") ;
				    System.out.println("Subject: "+subject) ;
				}
				qExec.close() ;
				conn.close() ;

	}

}
