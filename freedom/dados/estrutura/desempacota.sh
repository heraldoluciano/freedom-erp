mkdir /opt/firebird/dados/desenv/
cd /opt/firebird/dados/desenv/
cp /opt/eclipse/workspace/freedom/dados/estrutura/freedom.zip ./
mkdir backup
mv freedom.fbk backup/
unzip freedom.zip
gbak -C -R -P 4096 /opt/firebird/dados/desenv/freedom.fbk localhost:/opt/firebird/dados/desenv/freedom.fdb -user sysdba -pass masterkey
