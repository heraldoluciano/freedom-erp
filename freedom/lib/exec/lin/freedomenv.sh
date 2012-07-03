# DIRETORIO DE BIBLIOTECAS
MD=$1
FREEDOMCL=$2
LB="../lib/"
DI="../ini/"
DL="../log/"
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
CMDFREEDOM="java -classpath $FREEDOMCP -DARQINI=$FREEDOMFI -DARQLOG=$FREEDOMFL $FREEDOMCL"
echo $CMDFREEDOM

