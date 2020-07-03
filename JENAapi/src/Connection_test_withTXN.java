import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;


public class Connection_test_withTXN {

	//Transactions are the preferred way to work with RDF data. 
	//Operations on an RDFConnection outside of an application-controlled transaction will cause the system to add one for the duration of the operation. 
	//This “autocommit” feature may lead to inefficient operations due to excessive overhead.
	//The Txn class provides a Java8-style transaction API.
	//Transactions are code passed in the Txn library that handles the transaction lifecycle.
	
	public static void main(String[] args) {
		String connectionString = args[0]; 
		String queryString =  connectionString+"/query?force=true";
		String updateString = connectionString+"/update";
		String graphStoreProtocolString = connectionString+"/data";	
		
		try ( RDFConnection conn = RDFConnectionFactory.connect(queryString,updateString,graphStoreProtocolString) ) {
		    Txn.executeWrite(conn, ()-> {
		    	String ontologyPath = args[1];
		        conn.load(ontologyPath) ;
		        conn.querySelect("SELECT DISTINCT ?s { ?s ?p ?o }", (qs)->{
		           Resource subject = qs.getResource("s") ;
		           System.out.println("Subject: "+subject) ;
		        }) ;
		    }) ;
		}

	}

}
