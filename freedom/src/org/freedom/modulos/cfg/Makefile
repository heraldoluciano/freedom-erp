JC=javac -g
FLAGS=-classpath /aplic/appjava

all: \
FUsuario.class \
FGrupo.class \
FMenuObj.class \
FAcesso.class \
FreedomCFG.class

FUsuario.class: FUsuario.java
	$(JC) $(FLAGS) $^

FGrupo.class: FGrupo.java
	$(JC) $(FLAGS) $^

FAcesso.class: FAcesso.java
	$(JC) $(FLAGS) $^

FMenuObj.class: FMenuObj.java
	$(JC) $(FLAGS) $^

FreedomCFG.class: FreedomCFG.java
	$(JC) $(FLAGS) $^

.PHONY: all
