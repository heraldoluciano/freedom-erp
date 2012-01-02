EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath "lib/*" -DARQINI=ini/freedom.ini -DARQINI=ini/freedom.ini -DARQLOG=log/freedomcfg.log org.freedom.modulos.cfg.FreedomCFG
