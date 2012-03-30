EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath "lib/*" -DARQINI=ini/freedom.ini -DARQINI=ini/freedom.ini -DARQLOG=log/freedomlvf.log org.freedom.modulos.lvf.FreedomLVF
