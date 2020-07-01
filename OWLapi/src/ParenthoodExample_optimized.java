import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory;
import org.semanticweb.owlapi.owllink.builtin.response.OWLlinkErrorResponseException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.TimedConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEntityAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredIndividualAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

import au.csiro.snorocket.owlapi.SnorocketReasonerFactory;
import openllet.owlapi.OpenlletReasonerFactory;
import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasoner;
import uk.ac.manchester.cs.jfact.JFactFactory;

import org.semanticweb.elk.owlapi.ElkReasonerFactory;
//import org.semanticweb.reasonerfactory.factpp.FaCTPlusPlusReasonerFactory;
 
public class ParenthoodExample_optimized {
	public static void main(String[] args) throws Exception {
	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	    File file = new File(args[0]);
	    OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
	    System.out.println("axioms before reasoning" +ontology.getAxiomCount());
	    Set<OWLClass> classes = ontology.getClassesInSignature();
	    //File filename = FilenameUtils.removeExtension(args[0]);

	    OWLDataFactory df = manager.getOWLDataFactory();
	    String args1 = args[1];
	    Reasoner reasoner = Reasoner.valueOf(args1);
	    try {
	    System.out.println(RunReasoner(reasoner, df,ontology,manager,file));
	    }catch (final UnsupportedEntailmentTypeException e) {
	    	System.out.println(e.getMessage());
		}
	    catch (final OWLlinkErrorResponseException e) {
	    	System.out.println(e.getMessage());
		}
//	    Reasoner hermit = Reasoner.HERMIT;
//	    System.out.println(RunReasoner(hermit, df,ontology,manager,filename));
//	    
//	    Reasoner Structural = Reasoner.Structural;
//	    System.out.println(RunReasoner(Structural, df,ontology,manager,filename));
//	    
//	    
//	    Reasoner openllet = Reasoner.PELLET;
//	    System.out.println(RunReasoner(openllet, df,ontology,manager,filename));
//	    
//	    Reasoner konclude = Reasoner.KONCLUDE;
//	    System.out.println(RunReasoner(konclude, df,ontology,manager,filename));
//	    
//	    Reasoner elk = Reasoner.ELK;
//	    System.out.println(RunReasoner(elk, df,ontology,manager,filename));
	    
//	    Reasoner jfact = Reasoner.JFACT;
//	    System.out.println(RunReasoner(jfact, df,ontology,manager,filename));
//	    
	    

	
	}
	
	//CREATE AN ENUM REASONER
	public enum Reasoner{
		Structural,
		HERMIT, 
		PELLET, 
		KONCLUDE,
		JFACT,
		FACT,
		ELK,
		SNOROCKET
		
	}
	public static String RunReasoner(Reasoner reasoner, OWLDataFactory df, OWLOntology ontology, OWLOntologyManager manager, File filename) throws OWLOntologyCreationException, FileNotFoundException, IOException, OWLOntologyStorageException {
		String esito = "";
		OWLReasoner reasoner_object = null;
		
		//to measure execution time
		long startTime = System.nanoTime();
		if(reasoner == Reasoner.HERMIT) {
			/****************HERMIT****************************************************************************************/

		    OWLReasonerFactory rf = new ReasonerFactory();
		    TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
		    Configuration configuration = new Configuration();
		    configuration.reasonerProgressMonitor = progressMonitor;
		    configuration.ignoreUnsupportedDatatypes = true;
		    reasoner_object = rf.createReasoner(ontology, configuration);
		    
		   
		}
		else if(reasoner == Reasoner.Structural) {
			
			StructuralReasonerFactory rf = new StructuralReasonerFactory();
		    TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
		    Configuration configuration = new Configuration();
		    configuration.reasonerProgressMonitor = progressMonitor;
		    configuration.ignoreUnsupportedDatatypes = true;
		    reasoner_object = rf.createReasoner(ontology, configuration);
		}
//		//new entry
//		else if(reasoner == Reasoner.SNOROCKET) {
//			
//			SnorocketReasonerFactory rf = new SnorocketReasonerFactory();
//		    TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
//		    Configuration configuration = new Configuration();
//		    configuration.reasonerProgressMonitor = progressMonitor;
//		    configuration.ignoreUnsupportedDatatypes = true;
//		    reasoner_object = rf.createReasoner(ontology, configuration);
//		}
		//new entry
		else if(reasoner == Reasoner.FACT) {
			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
		    Configuration configuration = new Configuration();
		    configuration.reasonerProgressMonitor = progressMonitor;
		    configuration.ignoreUnsupportedDatatypes = true;
			reasoner_object = new FaCTPlusPlusReasoner(ontology,configuration,BufferingMode.BUFFERING);
		  //  reasoner_object = rf.
		    		
		    		//.createReasoner(ontology, configuration);
		}
		else if(reasoner == Reasoner.KONCLUDE) {
			
			// configure the server end-point
			URL url = new URL("http://localhost:8080");
			OWLlinkHTTPXMLReasonerFactory factory = new OWLlinkHTTPXMLReasonerFactory();
			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
			//OWLlinkReasonerConfiguration conf = (OWLlinkReasonerConfiguration) new SimpleConfiguration(progressMonitor);
			reasoner_object = factory.createNonBufferingReasoner(ontology);
					
		}
		else if(reasoner == Reasoner.JFACT) {
			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
			OWLReasonerConfiguration conf = new SimpleConfiguration(progressMonitor);
			JFactFactory factory = new JFactFactory();			
			reasoner_object = factory.createNonBufferingReasoner(ontology,conf);
		}
		else if(reasoner == Reasoner.ELK) {
			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
			OWLReasonerConfiguration conf = new SimpleConfiguration(progressMonitor);
			ElkReasonerFactory factory = new ElkReasonerFactory();
			reasoner_object = factory.createNonBufferingReasoner(ontology,conf);
		}
		else if(reasoner == Reasoner.PELLET) {
			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
			OWLReasonerConfiguration conf = new SimpleConfiguration(progressMonitor);
			reasoner_object = OpenlletReasonerFactory.getInstance().createReasoner(ontology,conf);			
		}
		else{
			esito = "Reasoner non valido";
		}
		System.out.println("UnsatisfiableClasses "+reasoner_object.getUnsatisfiableClasses().getSize());
		System.out.println("TopClassNode "+reasoner_object.topClassNode());
		 boolean consistencyCheck = reasoner_object.isConsistent();
			    if (consistencyCheck) {
			    	reasoner_object.precomputeInferences(InferenceType.CLASS_HIERARCHY,
			            InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_HIERARCHY,
			            InferenceType.DATA_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS);
			    	reasoner_object.flush(); //Flushes any changes stored in the buffer, which causes the reasoner to take into consideration the changes the current root ontology specified by the changes. FROM RONALD's CODE
			        List<InferredAxiomGenerator<? extends OWLAxiom>> generators = new ArrayList<>();
			        generators.add(new InferredSubClassAxiomGenerator());
			        generators.add(new InferredClassAssertionAxiomGenerator());
			        generators.add(new InferredDataPropertyCharacteristicAxiomGenerator());
			        generators.add(new InferredEquivalentClassAxiomGenerator());
			        generators.add(new InferredEquivalentDataPropertiesAxiomGenerator());
			        generators.add(new InferredEquivalentObjectPropertyAxiomGenerator());
			        generators.add(new InferredInverseObjectPropertiesAxiomGenerator());
			        generators.add(new InferredObjectPropertyCharacteristicAxiomGenerator());
			        
			        
			        // NOTE: InferredPropertyAssertionGenerator significantly slows down
			        // inference computation
			        //generators.add(new org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator());
			        generators.add(new InferredSubDataPropertyAxiomGenerator());
			        generators.add(new InferredSubObjectPropertyAxiomGenerator());
			        
			        List<InferredIndividualAxiomGenerator<? extends OWLIndividualAxiom>> individualAxioms =
			            new ArrayList<>();					
			        generators.addAll(individualAxioms);
			        
			        List<InferredDataPropertyAxiomGenerator<? extends OWLDataPropertyAxiom>> dataPropertyAxioms =
				            new ArrayList<>();					
				        generators.addAll(dataPropertyAxioms);
				        
			        List<InferredEntityAxiomGenerator<? extends OWLEntity,? extends OWLAxiom>> inferredEntityAxioms =
				            new ArrayList<>();					
				        generators.addAll(inferredEntityAxioms);    
			        
			        List<InferredObjectPropertyAxiomGenerator<? extends OWLObjectPropertyAxiom>> objectPropertyAxioms =
				            new ArrayList<>();					
				        generators.addAll(objectPropertyAxioms);
					        
			        List<InferredClassAxiomGenerator<? extends OWLClassAxiom>> classAxioms =
				            new ArrayList<>();					
				        generators.addAll(classAxioms);
				        
			        //generators.add(new InferredDisjointClassesAxiomGenerator());
			        //InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner_object, generators); //Generates an ontology based on inferred axioms which are essentially supplied by a reasoner
			        OWLOntology inferredAxiomsOntology = manager.createOntology();
			        //iog.fillOntology(df, inferredAxiomsOntology);
			        
		        	String path = filename.getParent();
		        	String namefile = FilenameUtils.removeExtension(filename.getName());
		        	//create main directory
		        	File mainDir = new File(path+"/"+namefile);
		        	if (!mainDir.exists()) mainDir.mkdirs();	
		        	
		        	//report file
		        	File report = new File(mainDir,reasoner.toString()+".txt");
		        	PrintWriter writer = new PrintWriter(report, "UTF-8");
		        	writer.println("Reasoner: "+ reasoner.toString());
		        	writer.println("Datetime: "+ DateTime.now());
		        	
		        	
			        for (InferredAxiomGenerator< ? extends OWLAxiom> inf : generators ) {
			            try{
				        	Set< ?  extends OWLAxiom> ax = inf.createAxioms(df, reasoner_object);
				        	System.out.println(inf +" : "  + ax.size());
				        	writer.println(inf +" : "  + ax.size());
				        	//save single generator axioms in a little ontology	
				        	//create child directory
				        	File childDir = new File(mainDir+"/"+inf.toString());
				        	if (!childDir.exists()) childDir.mkdirs();	
				        	File newFile = new File(childDir,inf.toString()+"_"+reasoner.toString()+".owl");
				        	
					        // Now we create a stream since the ontology manager can then write to that stream.
					        try (OutputStream outputStream = new FileOutputStream(newFile)) {
					            // We use the same format as for the input ontol4ogy.
					        	OWLOntology singleGeneratorOntology = manager.createOntology();
					        	singleGeneratorOntology.addAxioms(ax);
					            manager.saveOntology(singleGeneratorOntology, outputStream);
					            inferredAxiomsOntology.addAxioms(ax);
					            
					        }
				        } catch (Exception e) {
				            System.out.println("Error generating axioms using  "+reasoner_object.getReasonerName()+", version  " +reasoner_object.getReasonerVersion()  +" Error:" +e);
				        }
			        	
			        }
			        //to measure runtime memory
			        Runtime runtime = Runtime.getRuntime(); 						//Returns the runtime object associated with the current Java application.
			        runtime.gc(); 													//Runs the garbage collector.
			        long memory = runtime.totalMemory() - runtime.freeMemory();     //total amount of memory in the Java virtual machine - the amount of free memory in the Java Virtual Machine
			        System.out.println("Used memory is bytes: " + memory);
			        writer.println("");
			        writer.println("Used memory is megabytes: " + memory/(1024L*1024L));
			        
			        //to measure execution time 
					long endTime = System.nanoTime();
					long timeElapsed = endTime - startTime;
					System.out.println("Execution time in nanoseconds  : " + timeElapsed);
					writer.println("Execution time in seconds: " + TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS)+ "''");
					writer.println("Total Inferred Axioms: "  + inferredAxiomsOntology.getAxiomCount());
			        writer.close();
			        
			        
			        System.out.println("numero inferred axioms "  + inferredAxiomsOntology.getAxiomCount());
			        File inferredOntologyFile = new File(mainDir,namefile+"_inferredBy_"+reasoner.toString()+".owl");
			        System.out.println(inferredOntologyFile);
			        // Now we create a stream since the ontology manager can then write to that stream.
			        try (OutputStream outputStream = new FileOutputStream(inferredOntologyFile)) {
			            // We use the same format as for the input ontology.
			            manager.saveOntology(inferredAxiomsOntology, outputStream);
			        }
			        esito = "done "+ reasoner.toString();
			        reasoner_object.dispose();
			    } // End if consistencyCheck
			    else {
			        esito = reasoner.toString() +" -- Inconsistent input Ontology, Please check the OWL File";
			    }
		return esito;
	}
}
