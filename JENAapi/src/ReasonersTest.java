
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

import openllet.jena.PelletReasoner;
import openllet.jena.PelletReasonerFactory;
import openllet.query.sparqldl.jena.SparqlDLExecutionFactory;





public class ReasonersTest {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String ont =args[0];//"C:\\Users\\Rita\\Desktop\\parenthood.owl";

		//Model emptyModel = FileManager.get().loadModel("file:data/owlDemoSchema.owl");
		
		Model data = FileManager.get().loadModel(ont);
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		//PelletReasoner reasoner = PelletReasonerFactory.theInstance().create();
		// create an empty model
		 final OntModel emptyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		reasoner = reasoner.bindSchema(emptyModel);
		
		/*******************************************************************************/
		 
		  // create an inferencing model using reasoner
		  final InfModel model = ModelFactory.createInfModel(reasoner, data);
		  // read the file
		  model.read(ont);
		  
		  
		  File file = new File(args[0]);
		  String path = file.getParent();
      	  String namefile = FilenameUtils.removeExtension(file.getName());
      		//create main directory
      		File mainDir = new File(path+"/"+namefile+"_jena");
      		if (!mainDir.exists()) mainDir.mkdirs();	
      		File report = new File(mainDir,namefile+"_inferreBy_JENA.rdf");
      	  PrintWriter writer = new PrintWriter(report, "UTF-8");
		  model.write(writer); 
		  
		  String ns = model.getNsPrefixURI("");

		  final ValidityReport validity = model.validate(); //Test the consistency of the underlying data.		  
		  if (validity.isValid())
			  System.out.println("OK");
		  else{
			  System.out.println("Conflicts");
			  for (Iterator i = validity.getReports(); i.hasNext(); ){
				  ValidityReport.Report valReport =(ValidityReport.Report)i.next();
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
