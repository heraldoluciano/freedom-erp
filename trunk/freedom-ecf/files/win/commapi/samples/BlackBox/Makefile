JC=$(JDK)/bin/javac

SOURCES= \
	ToggleLabel.java \
	ByteCounter.java \
	BufferSize.java \
	ByteStatistics.java \
	CtlSigDisplay.java \
	FlowCtlDisplay.java \
	ReceiveTimeout.java \
	ReceiveFraming.java \
	ReceiveThreshold.java \
	ReceiveOptions.java \
	Receiver.java \
	ConvertedOutputStream.java \
	Transmitter.java \
	BaudRate.java \
	DataBits.java \
	StopBits.java \
	Parity.java \
	SerialPortDisplay.java \
	BlackBox.java 

CLASSES=$(SOURCES:%.java=%.class)

all : BlackBox.jar

BlackBox : $(CLASSES)

BlackBox.jar : BlackBox
	jar cvf $@ *.class

clean:
	rm -f core ,* *% *.class *.jar

%.class : %.java
	$(JC) -classpath $(JDK)/lib/classes.zip:../../../../../build/solaris/lib/ext/comm.jar:. $<

