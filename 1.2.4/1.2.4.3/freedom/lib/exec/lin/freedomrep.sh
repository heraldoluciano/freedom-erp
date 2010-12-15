EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath "lib/*" -DARQINI=ini/freedom.ini -DARQINI=ini/represen.ini -DARQLOG=log/freedomrep.log org.freedom.modulos.rep.FreedomREP
