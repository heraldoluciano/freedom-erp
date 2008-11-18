mkdir \opt\freedom\lib\back
mkdir \opt\freedom\bin\back
copy \opt\freedom\lib\*.jar \opt\freedom\lib\back
copy \opt\freedom\bin\*.bat \opt\freedom\bin\back
\opt\firebird\bin\gback -B localhost:/opt/firebird/dados/freedom.fdb \opt\firebird\dados\freedom.fbk -USER sysdba -PASS masterkey
