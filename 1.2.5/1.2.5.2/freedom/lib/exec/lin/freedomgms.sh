EXEDIR=${0%/*}
CMDENV="${EXEDIR}/freedomenv.sh"
FREEDOMMD="freedomgms"
FREEDOMCL="org.freedom.modulos.gms.FreedomGMS"
CMDFREEDOM=`$CMDENV $FREEDOMMD $FREEDOMCL`
$CMDFREEDOM

