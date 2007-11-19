JC=javac

SOURCES= \
	ByteCounter.java \
	BufferSize.java \
	ByteStatistics.java \
	CtlSigDisplay.java \
	ReceiveTimeout.java \
	ReceiveFraming.java \
	ReceiveThreshold.java \
	ReceiveOptions.java \
	Receiver.java \
	Transmitter.java \
	LPTMode.java \
	ParallelPortDisplay.java \
	ParallelBlackBox.java 

CLASSES=$(SOURCES:%.java=%.class)

all : ParallelBlackBox.jar

ParallelBlackBox : $(CLASSES)

ParallelBlackBox.jar : ParallelBlackBox
	jar cvf $@ *.class

clean:
	rm -f core ,* *% *.class *.jar

%.class : %.java
	$(JC) -classpath $(JDK)/lib/classes.zip:../../../../../build/solaris/lib/ext/comm.jar:. $<

