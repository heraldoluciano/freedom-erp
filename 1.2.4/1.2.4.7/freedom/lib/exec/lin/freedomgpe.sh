EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath "lib/*" -DARQINI=ini/freedom.ini -DARQLOG=log/freedomgpe.log org.freedom.modulos.gpe.FreedomGPE
