EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath "lib/*" -DARQINI=ini/freedom.ini -DARQLOG=log/freedomcrm.log org.freedom.modulos.crm.FreedomCRM
