# OwlTools

In JenaAPI run: mvn install

In OWLAPI run: mvn install 
Or easier: ```mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies```  

Then run: ```java -Xmx4G -cp target/OWLapi-0.0.1-SNAPSHOT-jar-with-dependencies.jar ReasonerComparison <ontology file or URL> <reasoner>```  

Reasoner Options:
HERMIT
PELLET
JFACT
ELK
KONCLUDE

Command-line options:
mvn exec:java -Dexec.mainClass=ReasonerComparison -Dexec.args="ontologyPath reasoner"

mvn exec:java -Dexec.mainClass=ReasonerComparison_with_SPARQLconnection -Dexec.args="ontologyPath reasoner connectionString"

mvn exec:java -Dexec.mainClass=ReasonerComparison_with_remoteSPARQLconnection -Dexec.args="ontologyPath reasoner connectionString username password"

