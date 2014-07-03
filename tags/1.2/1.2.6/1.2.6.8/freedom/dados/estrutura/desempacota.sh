#!/bin/sh
#set -x
# Script de descompactação e instalação do banco de dados de desenvolvimento
# Autor: Robson Sanchez/Setpoint Informática
# Data: 09/12/2009
# Atualizado para DDL em 23/05/2011

OPCAO=$1
ISC_USER="SYSDBA"
ISC_PASSWORD="masterkey"
CMD_ISQL="/opt/firebird/bin/isql"
WORKSPACE="$HOME/workspace"
DRIVER_FB="org.firebirdsql.jdbc.FBDriver"
DIR_FREEDOM="$WORKSPACE/freedom"
FILE_FB="/opt/firebird/dados/desenv/freedom25.fdb"
URL_FREEDOM_FB="localhost:$FILE_FB"
DIR_DDL="$DIR_FREEDOM/dados/estrutura"
FILE_DDL="$DIR_DDL/freedom.sql"
FILE_DROP="$DIR_DDL/drop.sql"
FILE_CREATE="$DIR_DDL/create.sql"
#FILE_DESCRIPTION="$DIR_DDL/description.sql"
CMD_DROP_DB="$CMD_ISQL -sqldialect 3 -charset ISO8859_1 -input $FILE_DROP $URL_FREEDOM_FB"
CMD_CREATE_DB="$CMD_ISQL -sqldialect 3 -charset ISO8859_1 -input $FILE_CREATE"
CMD_DDL="$CMD_ISQL -sqldialect 3 -charset ISO8859_1 -input $FILE_DDL $URL_FREEDOM_FB"
#CMD_DESCRIPTION="$CMD_ISQL -sqldialect 3 -charset ISO8859_1 -input $FILE_DESCRIPTION $URL_FREEDOM_FB"
RESULT=0
#echo $CMD_CREATE_DB

fn_senha_firebird()
{
  # Modo automático, somente para usar em outro script
  if [ "$OPCAO" != "--auto" ]; then
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
   ISC_PASSWORD=$SENHA_FIREBIRD_TMP
  fi
  export ISC_USER
  export ISC_PASSWORD
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

#  export ISC_USER
#  export ISC_PASSWORD

   if [ -f "$FILE_FB" ]; then
      echo "Eliminando banco de dados anterior"
#      echo $CMD_DROP_DB
      $CMD_DROP_DB -user $ISC_USER -pass $ISC_PASSWORD > $FILE_DROP.log
      RESULT=$?
   fi
    
   if [ $RESULT -eq 0 ]; then 
      echo "Criando novo banco de dados"
#      echo "$CMD_CREATE_DB -user $ISC_USER -pass $ISC_PASSWORD"
      $CMD_CREATE_DB -user $ISC_USER -pass $ISC_PASSWORD > $FILE_CREATE.log
      RESULT=$?
      if [ $RESULT -eq 0 ]; then
         echo "Aplicando DDL"
         echo $CMD_DDL
         $CMD_DDL -user $ISC_USER -pass $ISC_PASSWORD > $FILE_DDL.log
         RESULT=$?
         #if [ $RESULT -eq 0 ]; then
         #   echo "Aplicando descriptions DDL"
         #   #echo $CMD_DESCRIPTION -user $ISC_USER -pass $ISC_PASSWORD
         #   $CMD_DESCRIPTION -user $ISC_USER -pass $ISC_PASSWORD > $FILE_DESCRIPTION.log
         #   RESULT=$?
         #fi
      fi
   fi
    

}

fn_executa

fn_fim_script

