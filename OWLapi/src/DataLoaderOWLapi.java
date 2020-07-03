import org.semanticweb.owlapi.apibinding.*;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
//import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;


public class DataLoaderOWLapi {

	public static void main(String[] args) throws OWLOntologyCreationException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		IRI iri = IRI.create(args[0]);
		
		//Create new ontology
		OWLOntology onto = man.createOntology(iri);
		
		//Data Factory
		OWLDataFactory factory = man.getOWLDataFactory();
		
		/**********************DISEASE*************************************/
		//create Disease class
		 OWLClass disease = factory.getOWLClass(iri+"#Disease");
		 OWLDeclarationAxiom disease_axiom = factory.getOWLDeclarationAxiom(disease);
		 onto.add(disease_axiom);
		 
		//Create Dementia Class
		 OWLClass dementia = factory.getOWLClass(iri+"#Dementia");
		 OWLDeclarationAxiom dementia_axiom = factory.getOWLDeclarationAxiom(dementia);
		 onto.add(dementia_axiom);
		
		 OWLAxiom dementia_is_a_disease = factory.getOWLSubClassOfAxiom(dementia, disease);
		 onto.add(dementia_is_a_disease);
		 
		 //Create Dementia Class
		 OWLClass depression = factory.getOWLClass(iri+"#Depression");
		 OWLDeclarationAxiom depression_axiom = factory.getOWLDeclarationAxiom(depression);
		 onto.add(depression_axiom);
		
		 OWLAxiom depression_is_a_disease = factory.getOWLSubClassOfAxiom(depression, disease);
		 onto.add(depression_is_a_disease);
		 
		//Create VAD Class
		 OWLClass VAD = factory.getOWLClass(iri+"#VAD");
		 OWLDeclarationAxiom VAD_axiom = factory.getOWLDeclarationAxiom(VAD);
		 onto.add(VAD_axiom);
		
		 OWLAxiom VAD_is_a_dementia = factory.getOWLSubClassOfAxiom(VAD, dementia);
		 onto.add(VAD_is_a_dementia);
		 
		 /**********************TREATMENT*************************************/
		//create Treatment class
		 OWLClass treatment = factory.getOWLClass(iri+"#Treatment");
		 OWLDeclarationAxiom treatment_axiom = factory.getOWLDeclarationAxiom(treatment);
		 onto.add(treatment_axiom);
		 
		//Create Cholinesterase Inhibitors Class
		 OWLClass cholinesteraseInhibitors = factory.getOWLClass(iri+"#CholinesteraseInhibitors");
		 OWLDeclarationAxiom cholinesteraseInhibitors_axiom = factory.getOWLDeclarationAxiom(cholinesteraseInhibitors);
		 onto.add(cholinesteraseInhibitors_axiom);
		
		 OWLAxiom cholinesteraseInhibitors_is_a_treatment = factory.getOWLSubClassOfAxiom(cholinesteraseInhibitors, treatment);
		 onto.add(cholinesteraseInhibitors_is_a_treatment);
		 
		//Create Antidepressant Class
		 OWLClass antidepressant = factory.getOWLClass(iri+"#Antidepressant");
		 OWLDeclarationAxiom antidepressant_axiom = factory.getOWLDeclarationAxiom(antidepressant);
		 onto.add(antidepressant_axiom);
		
		 OWLAxiom antidepressant_is_a_treatment = factory.getOWLSubClassOfAxiom(antidepressant, treatment);
		 onto.add(antidepressant_is_a_treatment);
		 
		 /*************PERSON********************************************/
		 OWLClass person = factory.getOWLClass(iri+"#Person");
		 OWLDeclarationAxiom person_axiom = factory.getOWLDeclarationAxiom(person);
		 onto.add(person_axiom);
		 
		 /*************OBJECT PROPERTIES************************************/
		 OWLObjectProperty hasDisease = factory.getOWLObjectProperty(iri+"#hasDisease");
		 OWLClassExpression hasDisease_disease = factory.getOWLObjectSomeValuesFrom(hasDisease, disease);
		 OWLSubClassOfAxiom person_hasDisease_disease = factory.getOWLSubClassOfAxiom(person, hasDisease_disease);
		 onto.add(person_hasDisease_disease);
		 
		 OWLObjectProperty hasTreatment = factory.getOWLObjectProperty(iri+"#hasTreatment");
		 OWLClassExpression hasTreatment_treatment = factory.getOWLObjectSomeValuesFrom(hasTreatment, treatment);
		 OWLSubClassOfAxiom disease_hasTreatment_treatment = factory.getOWLSubClassOfAxiom(disease, hasTreatment_treatment);
		 onto.add(disease_hasTreatment_treatment);
		 
		 /******************INDIVIDUAL*********************************/
		 
		 //Create depression_ - depression individual
		 OWLIndividual depression_ = factory.getOWLNamedIndividual(iri+"#depression_");
		 OWLClassAssertionAxiom depression_ax = factory.getOWLClassAssertionAxiom(depression, depression_);
		 onto.add(depression_ax);
		 
		//Create vad_ - vad individual
		 OWLIndividual vad_ = factory.getOWLNamedIndividual(iri+"#vad_");
		 OWLClassAssertionAxiom vad_ax = factory.getOWLClassAssertionAxiom(VAD, vad_);
		 onto.add(vad_ax);
		 
		//Create antidepressant_ - antidepressant individual
		 OWLIndividual antidepressant_ = factory.getOWLNamedIndividual(iri+"#antidepressant_");
		 OWLClassAssertionAxiom antidepressant_ax = factory.getOWLClassAssertionAxiom(antidepressant,antidepressant_);
		 onto.add(antidepressant_ax);
		 
		//Create cholinesteraseInhibitors_ - cholinesteraseInhibitors individual
		 OWLIndividual cholinesteraseInhibitors_ = factory.getOWLNamedIndividual(iri+"#cholinesteraseInhibitors_");
		 OWLClassAssertionAxiom cholinesteraseInhibitors_ax = factory.getOWLClassAssertionAxiom(cholinesteraseInhibitors,cholinesteraseInhibitors_);
		 onto.add(cholinesteraseInhibitors_ax);
		 
		 // Create MarioRossi - person individual
		 OWLIndividual mariorossi = factory.getOWLNamedIndividual(iri+"#mariorossi");
		 OWLClassAssertionAxiom mariorossi_ax = factory.getOWLClassAssertionAxiom(person, mariorossi);
		 onto.add(mariorossi_ax);
		 
		 //mariorossi hasDisease vad
		 OWLObjectPropertyAssertionAxiom mariorossi_hasDisease_vad = factory.getOWLObjectPropertyAssertionAxiom(hasDisease, mariorossi, vad_);
		 onto.add(mariorossi_hasDisease_vad);
		 
		 // Create FrancaGialli - person individual
		 OWLIndividual francagialli = factory.getOWLNamedIndividual(iri+"#francagialli");
		 OWLClassAssertionAxiom francagialli_ax = factory.getOWLClassAssertionAxiom(person, francagialli);
		 onto.add(francagialli_ax);
		 
		 //mariorossi hasDisease vad
		 OWLObjectPropertyAssertionAxiom francagialli_hasDisease_depression = factory.getOWLObjectPropertyAssertionAxiom(hasDisease, francagialli, depression_);
		 onto.add(francagialli_hasDisease_depression);
		 
		 
		 /********************HERMIT******************************************************/
		 /*OWLReasonerFactory rf = new ReasonerFactory();
		 OWLReasoner r = rf.createReasoner(onto);
		 r.precomputeInferences(InferenceType.CLASS_HIERARCHY);*/
		 
		 /******************OPENLLET********************************************************/
		 
		/* final OpenlletReasoner reasoner = OpenlletReasonerFactory.getInstance().createReasoner(onto);
			System.out.println("done.");

			reasoner.getKB().realize();
			reasoner.getKB().printClassTree();*/
		 /*****************KONCLUDE*******************************************************/
		 
			
		/********************PELLET*************************************************************/
			/*PelletReasonerFactory reasonerFactory = PelletReasonerFactory
					.getInstance();
					PelletReasoner reasoner = reasonerFactory.createReasoner(onto);
					reasoner.prepareReasoner();
					reasoner.precomputeInferences();
					reasoner.getKB().classify();
					reasoner.getKB().realize();*/
					
					
//			 OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
//			
//		 	 reasonerFactory = new PelletReasonerFactory();
//	         OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(onto);
	         System.out.println("done.");
		}

	}


