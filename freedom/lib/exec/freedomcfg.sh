#!/bin/sh
cd /opt/freedom
java -classpath lib/freedomcfg.jar:lib/jcommon-0.8.7.jar:lib/jfreechart-0.9.12.jar:lib/itext-1.02b.jar:lib/firebirdsql-full.jar -DARQINI=freedom.ini org.freedom.modulos.cfg.FreedomCFG
