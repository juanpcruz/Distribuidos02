JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

Distrito = \
	ServerDistr.java \
	ServerDistrThread.java \
	Titan.java 

ServidorCentral = \
	ServerTCP.java \
	ServerCentralDistritos.java \
	ServerCentralThread.java \
	Titan.java

Cliente = \
	ClienteTCP.java \
	ClienteTCPthread.java \
	Titan.java

cliente: client
servidorCentral: centralServer
distrito: district

client: $(Cliente:.java=.class)
centralServer: $(ServidorCentral:.java=.class)
district: $(Distrito:.java=.class)

clean:
	$(RM) *.class