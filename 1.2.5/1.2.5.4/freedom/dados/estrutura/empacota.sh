#!/bin/sh

ISC_USER="SYSDBA"
ISC_PASSWORD="masterkey"
#CMD_GBAK="/opt/firebird/bin/gbak"
CMD_ISQL="/opt/firebird/bin/isql"
WORKSPACE="$HOME/workspace"
DIR_INFRA="$WORKSPACE/freedom-infra"
CPATH="$DIR_INFRA/bin/:$DIR_INFRA/lib/jaybird-full.jar"
DRIVER_FB="org.firebirdsql.jdbc.FBDriver"
URL_EXTDESCRIPTION="org.freedom.infra.util.db.ExtractDescription"
URL_FREEDOM_JAVA="jdbc:firebirdsql:localhost/3050:/opt/firebird/dados/desenv/freedom.fdb?lc_ctype=ISO8859_1"
DIR_FREEDOM="$WORKSPACE/freedom"
URL_FREEDOM_FB="localhost:/opt/firebird/dados/desenv/freedom.fdb"
DIR_DDL="$DIR_FREEDOM/dados/estrutura"
FILE_DESCRIPTION="$DIR_DDL/description.sql"
FILE_DDL="$DIR_DDL/freedom.sql"
PARAM_JAVA="-Dfile.encoding=ISO-8859-1"
CMD_DESCRIPTION="java -cp $CPATH $PARAM_JAVA $URL_EXTDESCRIPTION $DRIVER_FB $URL_FREEDOM_JAVA $FILE_DESCRIPTION"
CMD_DDL="$CMD_ISQL -a $URL_FREEDOM_FB -o $FILE_DDL"
#echo $CMD_DESCRIPTION
#echo $CMD_ISQL

RESULT=0

fn_senha_firebird()
{
   SENHA_FIREBIRD_TMP=""
   while [ -z $SENHA_FIREBIRD_TMP ]; do 
      echo "Senha do firebird? (Deixe em branco para padrão)"
      echo "Padrão: $ISC_PASSWORD" 
      read SENHA_FIREBIRD_TMP
      if [ -z $SENHA_FIREBIRD_TMP ]; then 
        SENHA_FIREBIRD_TMP=$ISC_PASSWORD
      fi
      echo "Senha: $SENHA_FIREBIRD_TMP"
      echo "Senha está correta? (S/N)"
      read OPCAO
      if [ $OPCAO = "N" -o $OPCAO = "n" ]; then 
         SENHA_FIREBIRD_TMP=""
      fi
   done
   ISC_PASSWORD="$SENHA_FIREBIRD_TMP"
}

fn_fim_script() 
{
  echo $LINHA_COMENTARIO
  if [ $RESULT -gt 0 ]; then
     echo "Script foi finalizado com erro(s)!"
  else
     echo "Script concluído com sucesso!"
  fi
  echo $LINHA_COMENTARIO
  exit 0
}

fn_executa() 
{
  fn_senha_firebird
  if [ "$ISC_PASSWORD" = "0" ]; then
    fn_fim_script
  fi

# $CMD_GBAK -B localhost:/opt/firebird/dados/desenv/freedom.fdb /tmp/freedom.fbk -user "$ISC_USER" -pass "$ISC_PASSWORD"
#  rm ./freedom.zip
#  zip ./freedom.zip /tmp/freedom.fbk

  fn_exporta_ddl
  fn_exporta_description
   
}

fn_exporta_ddl()
{
   echo "Exportando DDL"
   if [ -f "$FILE_DDL" ]; then
      rm $FILE_DDL
   fi
   $CMD_DDL -user "$ISC_USER" -pass "$ISC_PASSWORD"
   RESULT=$?
}

fn_exporta_description()
{
    echo "Exportando Description DDL"
	$CMD_DESCRIPTION $ISC_USER $ISC_PASSWORD
	RESULT=$?
}

fn_executa

fn_fim_script

