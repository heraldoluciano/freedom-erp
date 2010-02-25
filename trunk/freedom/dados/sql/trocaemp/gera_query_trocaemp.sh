#!/bin/sh

# Script para geração de query de troca de empresa.
# Autor: Robson Sanchez/Setpoint Informática Ltda.
# Data: 23/02/2010

ISC_USER="SYSDBA"
ISC_PASSWORD="masterkey"
CMD_ISQL="/opt/firebird/bin/isql"
ARQ_DEST="trocaemp.sql"
#ARQ_LOG="gera_query_troca_emp.log"
ARQ_SQL="gera_query_trocaemp.sql"
ARQ_DADOS="localhost:/opt/firebird/dados/desenv/freedom.fdb"
LINHA_COMENTARIO="----------------------------------------------"
RESULT=0

fn_executa()
{
   fn_senha_firebird
   if [ "$ISC_PASSWORD" = "0" ]; then
     fn_fim_script
   fi 
#   echo $ISC_USER
#   echo $ISC_PASSWORD
   rm $ARQ_DEST
echo "$CMD_ISQL -i $ARQ_SQL -o $ARQ_DEST -user $ISC_USER -pass $ISC_PASSWORD -page 20000 $ARQ_DADOS"
   $CMD_ISQL -i $ARQ_SQL -o $ARQ_DEST -user $ISC_USER -pass $ISC_PASSWORD -page 20000 $ARQ_DADOS
   
   echo "COMMIT WORK ; " >> $ARQ_DEST
   
   RESULT=$?
   
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
}

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
   ISC_PASSWORD=$SENHA_FIREBIRD_TMP
}

fn_executa

fn_fim_script
