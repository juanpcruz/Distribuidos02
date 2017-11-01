JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

Dist = \
	ServerDistr.java \
	ServerDistrThread.java \
	Titan.java 

Cent = \
	ServerTCP.java \
	ServerCentralDistritos.java \
	ServerCentralThread.java \
	Titan.java

Cli = \
	ClienteTCP.java \
	ClienteTCPthread.java \
	Titan.java

cliente: client
servidorCentral: centralServer
distrito: district

client: $(Cli:.java=.class)
centralServer: $(Cent:.java=.class)
district: $(Dist:.java=.class)

clean:
	$(RM) *.class