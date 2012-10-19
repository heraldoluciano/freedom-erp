# DIRETORIO DE BIBLIOTECAS
HOME_FREEDOM=${0%/*}
if [ "$HOME_FREEDOM" == "." ]; then
   HOME_FREEDOM=`pwd`
   HOME_FREEDOM=${HOME_FREEDOM%/*}
else
   HOME_FREEDOM=${0%/*/*}
fi
MD=$1
FREEDOMCL=$2
LB="$HOME_FREEDOM/lib/"
DI="$HOME_FREEDOM/ini/"
DL="$HOME_FREEDOM/log/"
CT=0
SP=":"
CP=""
for FL in $LB*.jar; do
  if [ $CT -ne 0 ]; then
    if [ $CT -ne 1 ]; then
      CP="$CP$SP"
    fi
    CP="$CP$FL"
  fi
  CT=$(($CT+1))
done
FREEDOMCP="$CP"
FREEDOMFI="${DI}freedom.ini"
FREEDOMFL="${DL}${MD}.log"
CMDFREEDOM="java -Dfile.encoding=ISO-8859-1 -classpath $FREEDOMCP -DARQINI=$FREEDOMFI -DARQLOG=$FREEDOMFL $FREEDOMCL"
echo $CMDFREEDOM

