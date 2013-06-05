#!/bin/sh
/opt/firebird/bin/gbak -C -R -P 4096 /opt/firebird/dados/freedom.fbk localhost:/opt/firebird/dados/freedom.fdb -user sysdba -pass masterkey
