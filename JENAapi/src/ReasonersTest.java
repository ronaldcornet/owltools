
import java.util.Iterator;

import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.reasoner.ValidityReport;

import openllet.jena.PelletReasoner;
import openllet.jena.PelletReasonerFactory;
import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;





public class ReasonersTest {
	private static final Logger logger = LogManager.getLogger(Main.class);
	public static void main(String[] args) {
//		String ont ="C:\\Users\\Rita\\Desktop\\dementia.rdf";
//		String ns = "C:\\Users\\Rita\\Desktop\\DementiaOnto#";
		
		String ont ="C:\\Users\\Rita\\Desktop\\parenthood.owl";
		String ns = "C:/Users/Rita/Desktop/parenthood.owl#";
		
		
//		String ont ="C:\\Users\\Rita\\Desktop\\wine.rdf";
//		String ns ="http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
		final PelletReasoner aReasoner = PelletReasonerFactory.theInstance().create();

		
		/*******************************************************************************/
		  // create an empty model
		  final OntModel emptyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		  // create an inferencing model using Pellet reasoner
		  final InfModel model = ModelFactory.createInfModel(aReasoner, emptyModel);
		  // read the file
		  model.read(ont);
		  
		  
		  final ValidityReport validity = model.validate(); //Test the consistency of the underlying data.		  
		  if (validity.isValid())
			  System.out.println("OK");
		  else{
			  System.out.println("Conflicts");
			  for (Iterator i = validity.getReports(); i.hasNext(); ){
				  ValidityReport.Report report =(ValidityReport.Report)i.next();
				  System.out.println(" - " + report);
			  }
		  }
		  
		  //validity.getReports() is empty because the model is valid!
		  
		  
		  final String aQuery = "PREFIX proj:<"+ns+"> "+
			  					"SELECT ?s ?p ?o WHERE {"+
			  					"?s ?p ?o ."+
			  					"} LIMIT 20";
		  final QueryExecution qe = SparqlDLExecutionFactory.create(QueryFactory.create(aQuery), model);
		  final ResultSet results = qe.execSelect();
		  ResultSetFormatter.out(System.out,results);

		  System.out.println();

	}

}
