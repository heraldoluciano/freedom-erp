#!/bin/sh
#set -x
# Script de descompactação e instalação do banco de dados de desenvolvimento
# Autor: Robson Sanchez/Setpoint Informática
# Data: 09/12/2009
# Adaptação: Heraldo Luciano
# 14/02/2011

ISC_USER="SYSDBA"
ISC_PASSWORD="masterkey"
CMD_GBAK="/opt/firebird/bin/gbak"
RESULT=0
OPCAO=$1

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

  export ISC_USER
  export ISC_PASSWORD

#   rm /tmp/freedom.fbk

  unzip -o ./represen.zip -d /

  mkdir -p /opt/firebird/dados/desenv/

  $CMD_GBAK -C -R -P 4096 /tmp/represen.fbk localhost:/opt/firebird/dados/desenv/represen.fdb

}

fn_executa

fn_fim_script

