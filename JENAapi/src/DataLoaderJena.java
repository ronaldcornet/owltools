
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;


public class DataLoaderJena {
	  
	  public static void main(String[]args) throws FileNotFoundException {
		String inputFileName = args[0];    /// "C:\\Users\\Rita\\Desktop\\dementia.owl";
		//create an empty ontology model
		 OntDocumentManager mgr = new OntDocumentManager();
		 OntModelSpec s = new OntModelSpec(OntModelSpec.OWL_MEM);
		 s.setDocumentManager(mgr);
		 
		 OntModel m = ModelFactory.createOntologyModel(s,null);
		 
		 //Use the fileManager to open the ontology from the filesystem
		 InputStream in = FileManager.get().open(inputFileName);
		 //read the ontology file
		 m.read(in,"");
		 
		 //source
		 String SOURCE  = "C:\\Users\\Rita\\Desktop\\dementia-empty.owl";
		 String NS      = "http://www.semanticweb.org/rita/ontologies/2020/1/untitled-ontology-2#";
		 
		 /*********************CLASSES***************************************/
		 //disease
		 OntClass dementia   				= m.createClass(NS + "Dementia");
		 OntClass disease    				= m.createClass(NS+"Disease");
		 OntClass vad       				= m.createClass(NS+"VAD");
		 OntClass depression 				= m.createClass(NS+"Depression");

		 //treatment
		 OntClass treatment 			   = m.createClass(NS+ "Treatment");
		 OntClass antidepressant 		   = m.createClass(NS+ "Antidepressant");
		 OntClass cholinesteraseinhibitors = m.createClass(NS+"CholinesteraseInhibitors");
		 
		 //person
		 OntClass person				   = m.createClass(NS+"Person"); 
		 
		 /***********************OBJECT PROPERTIES**************************/
		 //subclasses  
		 dementia.addSuperClass(disease);
		 vad.addSuperClass(dementia);
		 depression.addSuperClass(disease);
		 
		 antidepressant.addSuperClass(treatment);
		 cholinesteraseinhibitors.addSuperClass(treatment);
		 
		 //Q: how to add disjoint with?????????????
		 
		 //hasTreatment
		 ObjectProperty hasTreatment = m.createObjectProperty(NS+"hasTreatment");
		 hasTreatment.addDomain(disease);
		 hasTreatment.addRange(treatment);
		 
		 //hasDisease
		 ObjectProperty hasDisease = m.createObjectProperty(NS+"hasDisease");
		 hasDisease.addDomain(person);
		 hasDisease.addRange(disease);
		 
		//Q: how can i add an object property hasTreatment only for depression/antidepressant and vad/cholinesterase inhibitors?
		 
		 
		 
		 
		 
		 
		 
		 /*******************INDIVIDUAL***************************************/
		 
		 
		 
		 
		 
		 
		 m.write(System.out); //Write a serialization of this model as an XML document.

        // now write the model in XML form to a file
        FileOutputStream dementia_File = new FileOutputStream(SOURCE);

        m.write(dementia_File, "RDF/XML-ABBREV");

		 
	  }
}
