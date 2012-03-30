#!/bin/sh
RUNJAVA="java"


#Verifica opções:


case $1 in 
   "--path"|"atd"|"cfg"|"fnc"|"")
   ;;
   *)
	echo "Opção inválida!!"
	echo "Tente: ./Freedom.sh [--path <path>] [atd,cfg,fnc]"
	exit 1
esac
if [ "$1" == "--path" ]; then
	PATH_RUN=$2
	shift
fi


#Ajusta o PATH:


if [ "$PATH_RUN" == "" ]; then
  PATH_RUN="/opt"
else
  shift
fi


#JARS:


if [ "$1" == "atd" ]; then
  JAR_ARQS=$PATH_RUN/freedom/jars/freedomatd.jar
  CLASSE=projetos.freedomatd.FreedomATD
elif [ "$1" == "cfg" ]; then
  JAR_ARQS=$PATH_RUN/freedom/jars/freedomcfg.jar
  CLASSE=projetos.freedomcfg.FreedomCFG
elif [ "$1" == "fnc" ]; then
  JAR_ARQS=$PATH_RUN/freedom/jars/freedomfnc.jar
  CLASSE=projetos.freedomfnc.FreedomFNC
elif [ "$1" == "pcp" ]; then
  JAR_ARQS=$PATH_RUN/freedom/jars/freedompcp.jar
  CLASSE=projetos.freedompcp.FreedomPCP
else
  JAR_ARQS=$PATH_RUN/freedom/jars/freedomstd.jar
  CLASSE=projetos.freedomstd.FreedomSTD
fi

JAR_ARQS=$JAR_ARQS:\
$PATH_RUN/freedom/jars/firebirdsql-full.jar:\
$PATH_RUN/freedom/jars/jfreechart-0.9.12.jar:\
$PATH_RUN/freedom/jars/jcommon-0.8.7.jar:\
$PATH_RUN/freedom/jars/itext-1.02b.jar


#Monta o ARQINI:


ARQINI="$PATH_RUN/freedom/freedom.ini"


#Executa o Freedom:


if [ ! -e $PATH_RUN/freedom/jars ]; then
	echo "Não foi possível encontrar $PATH_RUN/freedom/jars"
else
	$RUNJAVA -DARQINI=$ARQINI -classpath $JAR_ARQS $CLASSE
fi
