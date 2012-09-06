#!/bin/sh
diretorio=`pwd`
jvm=$1

fn_fim()
{
  exit 0 
}

fn_uso()
{
  echo "Uso: inst_ubuntu.sh [directory_jvm]"
}

fn_executa()
{
  /usr/bin/unzip $diretorio/comm3.0_u1_linux.zip -d /tmp/
  cp /tmp/commapi/jar/*.jar $jvm/jre/lib/
  cp /tmp/commapi/jar/tools/*.jar $jvm/jre/lib
  cp /tmp/commapi/lib/*.so $jvm/jre/bin/
  cp /tmp/commapi/docs/javax.comm.properties $jvm/jre/lib
  cd /usr/lib
  ln -sf $jvm/jre/bin/libLinuxSerialParallel_g.so
  ln -sf $jvm/jre/bin/libLinuxSerialParallel.so
  cd $diretorio
}

if [ "$jvm" = "" ]; then
  fn_uso
  fn_fim
fi
fn_executa
fn_fim
