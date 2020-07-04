# structura

In JenaAPI run: mvn install

In OWLAPI run: mvn install

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


