import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.ValidityReport;

import openllet.jena.PelletReasoner;
import openllet.jena.PelletReasonerFactory;
import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;

public class Parenthoodexample_newcode_jena {
	public static void main(String[] args) throws Exception {
		
		String NS = "C:/Users/Rita/Desktop/parenthood.owl#";
		String SOURCE = "C:\\Users\\Rita\\Desktop\\parenthood.owl";
		OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontology.read(SOURCE, "OWL");
	
	    List<OntClass> classes = ontology.listClasses().toList();
	    
	    //test for correctly uploading ontology
	    for(OntClass clas : classes) {
        	System.out.println(clas);}
	    
	    final PelletReasoner pellet = PelletReasonerFactory.theInstance().create();

		
		/*******************************************************************************/
		  // create an inferencing model using Pellet reasoner
		  final InfModel model = ModelFactory.createInfModel(pellet, ontology);
		  // read the file
		  model.read(SOURCE);
		  
		  
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
		  
		  
		  final String aQuery = "PREFIX proj:<"+NS+"> "+
			  					"SELECT ?s ?p ?o WHERE {"+
			  					" proj:filippo ?p ?o ."+
			  					"} LIMIT 20";
		  final QueryExecution qe = SparqlDLExecutionFactory.create(QueryFactory.create(aQuery), model);
		  final ResultSet results = qe.execSelect();
		  ResultSetFormatter.out(System.out,results);

		  System.out.println();
	 
	}	    

}
	
