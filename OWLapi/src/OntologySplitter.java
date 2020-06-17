import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.protege.owl.diff.Engine;
import org.protege.owl.diff.align.algorithms.MatchByCode;
import org.protege.owl.diff.align.algorithms.MatchById;
import org.protege.owl.diff.present.Changes;
import org.protege.owl.diff.present.EntityBasedDiff;
import org.protege.owl.diff.present.EntityBasedDiff.DiffType;
import org.protege.owl.diff.present.MatchedAxiom;
import org.protege.owl.diff.present.algorithms.IdentifyMergedConcepts;
import org.protege.owl.diff.present.algorithms.IdentifyRenameOperation;
import org.protege.owl.diff.present.algorithms.IdentifyRetiredConcepts;
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
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
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
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

public class OntologySplitter {
	public static void main(String[] args) throws Exception {
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		    File filename = new File(args[0]);
		    OWLOntology ontology = manager.loadOntologyFromOntologyDocument(filename);
		    System.out.println("axioms number" +ontology.getAxiomCount());
		    OWLDataFactory df = manager.getOWLDataFactory();
	        OWLReasonerFactory rf = new ReasonerFactory();
	        OWLReasoner reasoner = rf.createReasoner(ontology);
        	String path = filename.getParent();
        	String namefile = FilenameUtils.removeExtension(filename.getName());
        	//create main directory
        	File mainDir = new File(path+"/"+namefile);
        	if (!mainDir.exists()) mainDir.mkdirs();	
        	
        	//report file
        	File report = new File(mainDir,"split_report"+".txt");
        	PrintWriter writer = new PrintWriter(report, "UTF-8");
        	
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
			        
		        generators.add(new InferredDisjointClassesAxiomGenerator());
		        	        	
	        	
		        for (InferredAxiomGenerator< ? extends OWLAxiom> inf : generators ) {
		            try{
			        	Set< ?  extends OWLAxiom> ax = inf.createAxioms(df, reasoner);
			        	System.out.println(inf +" : "  + ax.size());
			        	writer.println(inf +" : "  + ax.size());
			        	//save single generator axioms in a little ontology	
			        	//create child directory
			        	File newFile = new File(mainDir,inf.toString()+"_splitted.owl");
			        	
				        // Now we create a stream since the ontology manager can then write to that stream.
				        try (OutputStream outputStream = new FileOutputStream(newFile)) {
				            // We use the same format as for the input ontol4ogy.
				        	OWLOntology singleGeneratorOntology = manager.createOntology();
				        	singleGeneratorOntology.addAxioms(ax);
				            manager.saveOntology(singleGeneratorOntology, outputStream);
				            
				        }
			        } catch (Exception e) {
			            System.out.println("Error generating axioms using  "+reasoner.getReasonerName()+", version  " +reasoner.getReasonerVersion()  +" Error:" +e);
			        }
		        	
		        }
	        	
	            writer.close();
	}
}
