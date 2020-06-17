import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;


public class Connection_test {

	public static void main(String[] args) {
		String connectionString =  "http://localhost:8080/fuseki/$/server";
		RDFConnection conn = RDFConnectionFactory.connect(connectionString);
				conn.load("C:\\Users\\Rita\\Desktop\\parenthood.owl") ;
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
