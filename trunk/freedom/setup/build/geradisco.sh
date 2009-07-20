echo "Criando disco de instalacao"
mkdir ../disco
mkdir ../disco/lin
mkdir ../disco/opt
mkdir ../disco/opt/firebird
mkdir ../disco/opt/firebird/dados
mkdir ../disco/opt/firebird/UDF
mkdir ../disco/opt/freedom
mkdir ../disco/opt/freedom/lib
mkdir ../disco/opt/freedom/ico
mkdir ../disco/opt/freedom/log
mkdir ../disco/opt/freedom/bin
mkdir ../disco/opt/freedom/ini
rm ../disco/opt/freedom/* 
rm ../disco/opt/freedom/lib/* 
rm ../disco/opt/freedom/ico/* 
rm ../disco/opt/freedom/log/*
rm ../disco/opt/freedom/bin/*
rm ../disco/opt/freedom/ini/*
rm ../disco/opt/firebird/dados/*
rm ../disco/disco.zip
unzip -d ../disco/opt/firebird/dados/ ../../dados/estrutura/freedom.zip
cp ../../src/org/freedom/udf/udfinstall.sh ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf_lin.sql ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf.so ../disco/opt/firebird/UDF/
cp ../../icones/novos/ico/*.ico ../disco/opt/freedom/ico/
cp ../../lib/*.jar ../disco/opt/freedom/lib/
cp ../../lib/exec/*.sh ../disco/opt/freedom/bin/
cp ../../freedomlin.ini ../disco/opt/freedom/ini/freedom.ini
cd ../disco
rm lin/disco.zip
zip -r lin/disco ./opt/*
