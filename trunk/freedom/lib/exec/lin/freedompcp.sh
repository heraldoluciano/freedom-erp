EXEDIR=${0%/*}
CMDENV="${EXEDIR}/freedomenv.sh"
FREEDOMMD="freedompcp"
FREEDOMCL="org.freedom.modulos.pcp.FreedomPCP"
CMDFREEDOM=`$CMDENV $FREEDOMMD $FREEDOMCL`
$CMDFREEDOM

