/opt/firebird/bin/gbak -B localhost:/opt/firebird/dados/desenv/freedom.fdb /opt/eclipse/workspace/freedom/dados/estrutura/freedom.fbk -user sysdba -pass masterkey
rm /opt/eclipse/workspace/freedom/dados/estrutura/freedom.zip
zip -D /opt/eclipse/workspace/freedom/dados/estrutura/freedom.zip /opt/eclipse/workspace/freedom/dados/estrutura/freedom.fbk
