import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.TimedConsoleProgressMonitor;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredIndividualAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

import openllet.owlapi.OpenlletReasoner;
import openllet.owlapi.OpenlletReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
//import org.semanticweb.reasonerfactory.factpp.FaCTPlusPlusReasonerFactory;

public class ParenthoodExample_optimized {
	public static void main(String[] args) throws Exception {
	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	    File file = new File(args[0]);
	    OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
	    Set<OWLClass> classes = ontology.getClassesInSignature();
	    

	    String inferredFile = args[1];
	    //test for correctly uploading ontology
	    for(OWLClass clas : classes) {
        	System.out.println(clas);}
	    OWLDataFactory df = manager.getOWLDataFactory();

	    Reasoner hermit = Reasoner.HERMIT;
	    System.out.println(RunReasoner(hermit, df,ontology,manager,inferredFile));
	    
//	    
//	    Reasoner openllet = Reasoner.PELLET;
//	    System.out.println(RunReasoner(openllet, df,ontology,manager,inferredFile));
//	    
//	    Reasoner konclude = Reasoner.KONCLUDE;
//	    System.out.println(RunReasoner(konclude, df,ontology,manager,inferredFile));
//	    
//	    Reasoner elk = Reasoner.ELK;
//	    System.out.println(RunReasoner(elk, df,ontology,manager,inferredFile));
//	    
//	    Reasoner jfact = Reasoner.JFACT;
//	    System.out.println(RunReasoner(jfact, df,ontology,manager,inferredFile));
	    

	
	}
	
	//CREATE AN ENUM REASONER
	public enum Reasoner{
		HERMIT, 
		PELLET, 
		KONCLUDE,
		JFACT,
		FACT,
		ELK
		
	}
	public static String RunReasoner(Reasoner reasoner, OWLDataFactory df, OWLOntology ontology, OWLOntologyManager manager, String inferredFile) throws OWLOntologyCreationException, FileNotFoundException, IOException, OWLOntologyStorageException {
		String esito = "";
		OWLReasoner reasoner_object = null;
		if(reasoner == Reasoner.HERMIT) {
			/****************HERMIT****************************************************************************************/

		    OWLReasonerFactory rf = new ReasonerFactory();
		    TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
		    Configuration configuration = new Configuration();
		    configuration.reasonerProgressMonitor = progressMonitor;
		    configuration.ignoreUnsupportedDatatypes = true;
		    reasoner_object = rf.createReasoner(ontology, configuration);
		   
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
//		else if(reasoner == Reasoner.FACT) {
//			TimedConsoleProgressMonitor progressMonitor = new TimedConsoleProgressMonitor();
//			OWLReasonerConfiguration conf = new SimpleConfiguration(progressMonitor);
//			FaCTPlusPlusReasonerFactory factory = new FaCTPlusPlusReasonerFactory();
//			reasoner_object = factory.createNonBufferingReasoner(ontology,conf);
//		}
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
		 boolean consistencyCheck = reasoner_object.isConsistent();
			    if (consistencyCheck) {
			    	reasoner_object.precomputeInferences(InferenceType.CLASS_HIERARCHY,
			            InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_HIERARCHY,
			            InferenceType.DATA_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS);
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
			        generators.add(new org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator());

			        generators.add(new InferredSubClassAxiomGenerator());
			        generators.add(new InferredSubDataPropertyAxiomGenerator());
			        generators.add(new InferredSubObjectPropertyAxiomGenerator());
			        List<InferredIndividualAxiomGenerator<? extends OWLIndividualAxiom>> individualAxioms =
			            new ArrayList<>();
			        generators.addAll(individualAxioms);

			        generators.add(new InferredDisjointClassesAxiomGenerator());
			        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner_object, generators);
			        OWLOntology inferredAxiomsOntology = manager.createOntology();
			        iog.fillOntology(df, inferredAxiomsOntology);
//			        for(InferredAxiomGenerator<?> i : iog.getAxiomGenerators()) {
//			        	System.out.println(i);}
			        File inferredOntologyFile = new File(inferredFile);
			        // Now we create a stream since the ontology manager can then write to that stream.
			        try (OutputStream outputStream = new FileOutputStream(inferredOntologyFile)) {
			            // We use the same format as for the input ontology.
			            manager.saveOntology(inferredAxiomsOntology, outputStream);
			        }
			        esito = "done "+ reasoner.toString();
			    } // End if consistencyCheck
			    else {
			        esito = reasoner.toString() +" -- Inconsistent input Ontology, Please check the OWL File";
			    }
		return esito;
	}
}
