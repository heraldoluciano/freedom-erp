EXEDIR=${0%/*}
CMDENV="${EXEDIR}/freedomenv.sh"
FREEDOMMD="freedomlvf"
FREEDOMCL="org.freedom.modulos.lvf.FreedomLVF"
CMDFREEDOM=`$CMDENV $FREEDOMMD $FREEDOMCL`
$CMDFREEDOM

