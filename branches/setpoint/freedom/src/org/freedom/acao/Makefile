JC=javac -g
FLAGS=-classpath /aplic/appjava

all: \
ArvoreFace.class \
PostListener.class \
CarregaListener.class \
DeleteListener.class \
InsertListener.class \
CancelListener.class \
PostEvent.class \
CarregaEvent.class \
DeleteEvent.class \
InsertEvent.class \
CancelEvent.class

ArvoreFace.class: ArvoreFace.java
	$(JC) $(FLAGS) $^

PostListener.class: PostListener.java
	$(JC) $(FLAGS) $^

CarregaListener.class: CarregaListener.java
	$(JC) $(FLAGS) $^

DeleteListener.class: DeleteListener.java
	$(JC) $(FLAGS) $^

InsertListener.class: InsertListener.java
	$(JC) $(FLAGS) $^

CancelListener.class: CancelListener.java
	$(JC) $(FLAGS) $^

PostEvent.class: PostEvent.java
	$(JC) $(FLAGS) $^

CarregaEvent.class: CarregaEvent.java
	$(JC) $(FLAGS) $^

DeleteEvent.class: DeleteEvent.java
	$(JC) $(FLAGS) $^

InsertEvent.class: InsertEvent.java
	$(JC) $(FLAGS) $^

CancelEvent.class: CancelEvent.java
	$(JC) $(FLAGS) $^

.PHONY: all
