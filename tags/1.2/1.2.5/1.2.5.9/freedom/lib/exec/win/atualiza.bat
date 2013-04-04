copy \opt\eclipse\workspace\freedom\lib\*.jar \opt\freedom\lib
copy \opt\eclipse\workspace\freedom-fw1\lib\*.jar \opt\freedom\lib
copy \opt\eclipse\workspace\freedom\lib\exec\*.bat \opt\freedom\bin
mkdir \opt\firebird\dados\desenv
copy \opt\eclipse\workspace\freedom\dados\estrutura\freedom.zip \opt\firebird\dados\desenv\freedom.zip
cd \opt\firebird\dados\desenv
\opt\7-Zip\7z e -y freedom.zip

