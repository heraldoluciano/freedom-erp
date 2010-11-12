mkdir \opt\freedom\lib\bak
mkdir \opt\freedom\bin\bak
copy \opt\freedom\lib\*.jar \opt\freedom\lib\bak
copy \opt\freedom\bin\*.bat \opt\freedom\bin\bak
\opt\firebird\bin\gbak -B localhost:/opt/firebird/dados/freedom.fdb \opt\firebird\dados\freedom.fbk -USER sysdba -PASS masterkey
