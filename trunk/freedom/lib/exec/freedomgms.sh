#!/bin/sh
cd /opt/freedom
java -classpath jars/freedomgms.jar:jars/jcommon-0.8.7.jar:jars/jfreechart-0.9.12.jar:jars/itext-1.02b.jar:jars/firebirdsql-full.jar -DARQINI=freedom.ini  projetos.freedomgms.FreedomGMS
