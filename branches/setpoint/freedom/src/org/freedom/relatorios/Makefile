JC=javac
FLAGS=-classpath /aplic/appjava:/aplic/appjava/jars/jfreechart-0.9.12.jar:/aplic/appjava/jars/jcommon-0.8.7.jar

all: \
BalanceteBarras.class \
DLExibePizza.class \
EvoluVendasBarras.class \
EvoluVendasLinha.class \
BalancetePizza.class

BalanceteBarras.class: BalanceteBarras.java
	$(JC) $(FLAGS) $^

DLExibePizza.class: DLExibePizza.java
	$(JC) $(FLAGS) $^

EvoluVendasBarras.class: EvoluVendasBarras.java
	$(JC) $(FLAGS) $^

EvoluVendasLinha.class: EvoluVendasLinha.java
	$(JC) $(FLAGS) $^

BalancetePizza.class: BalancetePizza.java
	$(JC) $(FLAGS) $^

.PHONY: all
