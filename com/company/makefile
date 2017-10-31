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

cliente: cliente
servidorCentral: servidorCentral
distrito: distrito

cliente: $(Cliente:.java=.class)
servidorCentral: $(ServidorCentral:.java=.class)
distrito: $(Distrito:.java=.class)

clean:
        $(RM) *.class