import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class Remote_connection_test {

	public static void main(String[] args) {
		String URL = args[0]; 
		String user = args[1];                              
		String password = args[2];  
		System.out.println(URL);
		System.out.println(user);
		System.out.println(password);
		//Make a remote RDFConnection to the URL, with user and password for the client access using basic auth.Use with care. Basic auth over plain HTTP reveals the password on the network. 
		RDFConnection conn = RDFConnectionFactory.connectPW(URL, user, password);
				//conn.load("C:\\Users\\Rita\\Desktop\\parenthood.owl") ;
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
