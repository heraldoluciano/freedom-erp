EXEDIR=${0%/*}
CMDENV="${EXEDIR}/freedomenv.sh"
FREEDOMMD="freedomgrh"
FREEDOMCL="org.freedom.modulos.grh.FreedomGRH"
CMDFREEDOM=`$CMDENV $FREEDOMMD $FREEDOMCL`
$CMDFREEDOM

