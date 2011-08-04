#!/bin/sh
# Script para geração de discos de instalação

VERSION=$1
ISC_PASSWORD=$2

fn_get_version() {
    if [ -z $VERSION ]; then
       echo "Entre com o número da versão: "
       read VERSION
       if [ -z $VERSION ]; then
          echo "Versão não selecionada !"
          fn_end_script
       fi
    fi
}

fn_get_password() {
   if [ -z $ISC_PASSWORD ]; then
       echo "Entre com a senha do Sysdba: "
       read ISC_PASSWORD
       if [ -z $ISC_PASSWORD ]; then
          echo "Senha não selecionada !"
          fn_end_script
       fi 
   fi
}

fn_create_all_dir()
{
    echo "Criando diretórios..."
    if [ -d "../disco" ]; then
       rm -R "../disco"
    fi
    fn_create_dir "../disco/opt/firebird/dados"
    fn_create_dir "../disco/opt/firebird/UDF"
    fn_create_dir "../disco/opt/freedom/lib"
    fn_create_dir "../disco/opt/freedom/ico"
    fn_create_dir "../disco/opt/freedom/log"
    fn_create_dir "../disco/opt/freedom/bin"
    fn_create_dir "../disco/opt/freedom/ini"
    echo "Fim da criação de diretórios !"
}

fn_create_dir()
{
    NEWDIR=$1
    echo "Criando diretório $NEWDIR"
    if [ ! -d $NEWDIR ]; then
       mkdir -p $NEWDIR
    fi
}

fn_create_db() 
{
   CMD_ISQL="/opt/firebird/bin/isql"
   WORKSPACE="$HOME/workspace"
   DIR_FREEDOM="$WORKSPACE/freedom"
   DIR_DDL="$DIR_FREEDOM/dados/estrutura"
   DIR_TMP="/tmp"
   FILE_DDL="$DIR_DDL/freedom.sql"
   FILE_DROP="$DIR_DDL/drop.sql"
   FILE_CREATE="$DIR_DDL/create.sql"
   FILE_DESCRIPTION="$DIR_DDL/description.sql"
   CMD_DROP_DB="$CMD_ISQL -i $FILE_DROP $URL_FREEDOM_FB"
   CMD_CREATE_DB="$CMD_ISQL -i $FILE_CREATE"

      echo "Criando novo banco de dados"
      $CMD_CREATE_DB -user $ISC_USER -pass $ISC_PASSWORD > $FILE_CREATE.log
      RESULT=$?
      if [ $RESULT -eq 0 ]; then
         echo "Aplicando DDL"
         echo $CMD_DDL
         $CMD_DDL -user $ISC_USER -pass $ISC_PASSWORD > $FILE_DDL.log
         RESULT=$?
         if [ $RESULT -eq 0 ]; then
            echo "Aplicando descriptions DDL"
            #echo $CMD_DESCRIPTION -user $ISC_USER -pass $ISC_PASSWORD
            $CMD_DESCRIPTION -user $ISC_USER -pass $ISC_PASSWORD > $FILE_DESCRIPTION.log
            RESULT=$?
         fi
      fi

    /opt/firebird/bin/isql -i ../../dados/estrutura/create.sql
}

fn_copy_files() 
{
    echo "Copiando arquivos ..."
    cp ../../src/org/freedom/udf/udfinstall.sh ../disco/opt/firebird/UDF/
    cp ../../src/org/freedom/udf/stp_udf_lin.sql ../disco/opt/firebird/UDF/
    cp ../../src/org/freedom/udf/stp_udf.so ../disco/opt/firebird/UDF/
    cp ../../src/org/freedom/udf/udfinstall.bat ../disco/opt/firebird/UDF/
    cp ../../src/org/freedom/udf/stp_udf_win.sql ../disco/opt/firebird/UDF/
    cp ../../src/org/freedom/udf/stp_udf.dll ../disco/opt/firebird/UDF/
    cp ../../icones/novos/png/*.png ../disco/opt/freedom/ico/
    cp ../../icones/novos/ico/*.ico ../disco/opt/freedom/ico/
    cp ../../lib/*.jar ../disco/opt/freedom/lib/
    cp ../../../freedom-infra/lib/*.jar ../disco/opt/freedom/lib/
    cp ../../../freedom-fw1/lib/*.jar ../disco/opt/freedom/lib/
    cp ../../lib/exec/lin/*.sh ../disco/opt/freedom/bin/
    cp ../../lib/exec/win/*.bat ../disco/opt/freedom/bin/
    cp ../../lib/exec/osx/*.app ../disco/opt/freedom/bin/
    cp ../../freedomlin.ini ../disco/opt/freedom/ini/freedomlin.ini
    cp ../../freedomwin.ini ../disco/opt/freedom/ini/freedomwin.ini
    echo "Fim da cópia de arquivos !"
}

fn_zip_disk()
{
    cd ../disco
    zip -r "freedom_full_$VERSION.zip" ./opt/*
}

fn_end_script() 
{
    echo "Script concluído !"
    exit 0
}

fn_get_version
fn_get_password
fn_create_all_dir
fn_create_db
fn_copy_files
fn_zip_disk
fn_end_script

