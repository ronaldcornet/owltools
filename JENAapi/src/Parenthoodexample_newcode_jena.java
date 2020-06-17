import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

import openllet.jena.PelletReasoner;
import openllet.jena.PelletReasonerFactory;
import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;

public class Parenthoodexample_newcode_jena {
	public static void main(String[] args) throws Exception {
		
		
		String SOURCE = args[0]; //"C:\\Users\\Rita\\Desktop\\parenthood.owl";
		OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontology.read(SOURCE, "OWL");
		String NS = ontology.getNsPrefixURI("");
	    List<OntClass> classes = ontology.listClasses().toList();
	    
	  //to measure execution time
	  		long startTime = System.nanoTime();
	  	
	  		
	    //test for correctly uploading ontology
	    for(OntClass clas : classes) {
        	System.out.println(clas);}
	    
	    final PelletReasoner pellet = PelletReasonerFactory.theInstance().create();
	    
	    //Model schema = FileManager.get().loadModel("C:\\Users\\Rita\\Desktop\\owlDemoSchema.owl");
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    //reasoner = reasoner.bindSchema(schema);
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
				  ValidityReport.Report valreport =(ValidityReport.Report)i.next();
				  System.out.println(" - " + valreport);
			  }
		  }
		  
		  //validity.getReports() is empty because the model is valid!
		  File file = new File(args[0]);
		  String path = file.getParent();
      	  String namefile = FilenameUtils.removeExtension(file.getName());
      		//create main directory
      		File mainDir = new File(path+"/"+namefile+"_jena");
      		if (!mainDir.exists()) mainDir.mkdirs();	
      		File report = new File(mainDir,namefile+"_inferreBy_PELLET_from_JENA.rdf");
      	  PrintWriter writer = new PrintWriter(report, "UTF-8");
		  model.write(writer);
      	  
		  long endTime = System.nanoTime();
		  long timeElapsed = endTime - startTime;
		  System.out.println("Execution time in seconds  : " + TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS));
//		  
//		  final String aQuery = "PREFIX proj:<"+NS+"> "+
//			  					"SELECT ?s ?p ?o WHERE {"+
//			  					" proj:maria ?p ?o ."+
//			  					"} LIMIT 20";
//		  final QueryExecution qe = SparqlDLExecutionFactory.create(QueryFactory.create(aQuery), model);
//		  final ResultSet results = qe.execSelect();
//		  ResultSetFormatter.out(System.out,results);

		  System.out.println();
	 
	}	    

}
	
