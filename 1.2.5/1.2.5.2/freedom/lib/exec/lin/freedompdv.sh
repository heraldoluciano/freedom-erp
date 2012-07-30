EXEDIR=${0%/*}
CMDENV="${EXEDIR}/freedomenv.sh"
FREEDOMMD="freedompdv"
FREEDOMCL="org.freedom.modulos.pdv.FreedomPDV"
CMDFREEDOM=`$CMDENV $FREEDOMMD $FREEDOMCL`
$CMDFREEDOM

