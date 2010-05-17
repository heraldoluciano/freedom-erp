echo "Criando disco de instalacao"
mkdir ../disco
mkdir ../disco/lin
mkdir ../disco/opt
mkdir ../disco/opt/firebird
mkdir ../disco/opt/firebird/dados
mkdir ../disco/opt/firebird/UDF
mkdir ../disco/opt/freedom
mkdir ../disco/opt/freedom/lib
mkdir ../disco/opt/freedom/icon
mkdir ../disco/opt/freedom/log
mkdir ../disco/opt/freedom/bin
mkdir ../disco/opt/freedom/ini
rm ../disco/opt/freedom/* 
rm ../disco/opt/freedom/lib/* 
rm ../disco/opt/freedom/icon/* 
rm ../disco/opt/freedom/log/*
rm ../disco/opt/freedom/bin/*
rm ../disco/opt/freedom/ini/*
rm ../disco/opt/firebird/dados/*
rm ../disco/disco.zip
unzip -j -d ../disco/opt/firebird/dados/ ../../dados/estrutura/freedom.zip
cp ../../src/org/freedom/udf/udfinstall.sh ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf_lin.sql ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf.so ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/udfinstall.bat ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf_win.sql ../disco/opt/firebird/UDF/
cp ../../src/org/freedom/udf/stp_udf.dll ../disco/opt/firebird/UDF/
cp ../../icones/novos/png/*.png ../disco/opt/freedom/icon/
cp ../../icones/novos/ico/*.ico ../disco/opt/freedom/icon/
cp ../../lib/*.jar ../disco/opt/freedom/lib/
cp ../../../freedom-infra/lib/*.jar ../disco/opt/freedom/lib/
cp ../../../freedom-fw1/lib/*.jar ../disco/opt/freedom/lib/
cp ../../lib/exec/*.sh ../disco/opt/freedom/bin/
cp ../../lib/exec/*.bat ../disco/opt/freedom/bin/
cp ../../freedomlin.ini ../disco/opt/freedom/ini/freedomlin.ini
cp ../../freedomwin.ini ../disco/opt/freedom/ini/freedomwin.ini
cd ../disco
rm lin/disco.zip
zip -r lin/disco ./opt/*
