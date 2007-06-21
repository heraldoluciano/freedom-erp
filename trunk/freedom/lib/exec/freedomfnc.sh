EXEDIR=${0%/*}
cd $EXEDIR/..
java -classpath lib/freedomfnc.jar:lib/jcommon.jar:lib/jfreechart.jar:lib/itext.jar:lib/firebirdsql-full.jar:lib/nachocalendar.jar:lib/commons-logging.jar:lib/jasperreports.jar:lib/liquidlnf.jar:lib/metouia.jar:lib/commons-beanutils.jar:lib/commons-collections.jar:lib/mail.jar:lib/barbecue.jar:lib/ireport.jar -DARQINI=ini/freedom.ini -DARQLOG=log/freedomfnc.log org.freedom.modulos.fnc.FreedomFNC
