@echo "Criando disco de instalação"
mkdir ..\disco
mkdir ..\disco\win
mkdir ..\disco\opt
mkdir ..\disco\opt\firebird
mkdir ..\disco\opt\firebird\dados
mkdir ..\disco\opt\firebird\UDF
mkdir ..\disco\opt\freedom
mkdir ..\disco\opt\freedom\lib
mkdir ..\disco\opt\freedom\ico
mkdir ..\disco\opt\freedom\log
mkdir ..\disco\opt\freedom\bin
mkdir ..\disco\opt\freedom\ini
del ..\disco\opt\freedom\*.* /Q
del ..\disco\opt\freedom\lib\*.* /Q
del ..\disco\opt\freedom\ico\*.* /Q
del ..\disco\opt\freedom\log\*.* /Q
del ..\disco\opt\freedom\bin\*.* /Q
del ..\disco\opt\freedom\ini\*.* /Q
del ..\disco\opt\firebird\dados\*.* /Q
del ..\disco\disco.zip /Q
\opt\7-Zip\7z x -o..\disco\opt\firebird\dados -tzip ..\..\dados\estrutura\freedom.zip
\opt\firebird\bin\gbak -C -R -P 4096 ..\disco\opt\firebird\dados\freedom.fbk localhost:/opt/firebird/dados/freedom.fdb -user sysdba -pass masterkey
copy \opt\firebird\dados\freedom.fdb ..\disco\opt\firebird\dados
copy ..\..\src\org\freedom\udf\udfinstall.bat ..\disco\opt\firebird\UDF
copy ..\..\src\org\freedom\udf\stp_udf_win.sql ..\disco\opt\firebird\UDF
copy ..\..\src\org\freedom\udf\stp_udf.dll ..\disco\opt\firebird\UDF
copy ..\..\icones\novos\ico\*.ico ..\disco\opt\freedom\ico
copy ..\..\lib\*.jar ..\disco\opt\freedom\lib
copy ..\..\lib\exec\*.bat ..\disco\opt\freedom\bin
copy ..\..\freedomwin.ini ..\disco\opt\freedom\ini\freedom.ini
\opt\7-Zip\7z a -tzip ..\disco\win\disco ..\disco\opt\

