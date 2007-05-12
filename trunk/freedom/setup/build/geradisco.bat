@echo "Criando disco de instalação"
mkdir ..\disco
mkdir ..\disco\win
mkdir ..\disco\opt
mkdir ..\disco\opt\firebird
mkdir ..\disco\opt\firebird\dados
mkdir ..\disco\opt\firebird\UDF
mkdir ..\disco\opt\freedom
mkdir ..\disco\opt\freedom\lib
del ..\disco\opt\freedom\*.* /Q
del ..\disco\opt\freedom\lib\*.* /Q
del ..\disco\opt\firebird\dados\*.* /Q
del ..\disco\disco.zip /Q
7z x -o..\disco\opt\firebird\dados\ -tzip ..\..\dados\estrutura\freedom.zip
copy ..\..\src\org\freedom\udf\udfinstall.bat ..\disco\opt\firebird\UDF
copy ..\..\src\org\freedom\udf\stp_udf_win.sql ..\disco\opt\firebird\UDF
copy ..\..\src\org\freedom\udf\stp_udf.dll ..\disco\opt\firebird\UDF
copy ..\..\lib\*.jar ..\disco\opt\freedom\lib
copy ..\..\lib\exec\*.bat ..\disco\opt\freedom
copy ..\..\freedomwin.ini ..\disco\opt\freedom\freedom.ini
7z a -tzip ..\disco\win\disco ..\disco\opt\
