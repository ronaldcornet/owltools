import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class Connection_test_withTNX {

	//Transactions are the preferred way to work with RDF data. 
	//Operations on an RDFConnection outside of an application-controlled transaction will cause the system to add one for the duration of the operation. 
	//This “autocommit” feature may lead to inefficient operations due to excessive overhead.
	//The Txn class provides a Java8-style transaction API.
	//Transactions are code passed in the Txn library that handles the transaction lifecycle.
	
	public static void main(String[] args) {
		String connectionString =  "http://localhost:8080/fuseki/Parenthood/query?force=true";
		String updateString = "http://localhost:8080/fuseki/Parenthood/update";
		String graphStoreProtocolString = "http://localhost:8080/fuseki/Parenthood/data";
		try ( RDFConnection conn = RDFConnectionFactory.connect(connectionString,updateString,graphStoreProtocolString) ) {
		    conn.begin(ReadWrite.WRITE) ;
		    try {
		        conn.load("C:\\Users\\Rita\\Desktop\\parenthood.owl") ;
		        conn.querySelect("SELECT DISTINCT ?s { ?s ?p ?o }", (qs)->{
		           Resource subject = qs.getResource("s") ;
		           System.out.println("Subject: "+subject) ;
		        }) ;
		        conn.commit() ;
		    } finally { conn.end() ; }
		}

	}

}
