#!/bin/sh
diretorio=`pwd`
/usr/bin/unzip $diretorio/comm3.0_u1_linux.zip -d /tmp/
cp /tmp/commapi/jar/*.jar /usr/lib/jvm/java-1.5.0-sun/jre/lib/
cp /tmp/commapi/jar/tools/*.jar /usr/lib/jvm/java-1.5.0-sun/jre/lib
cp /tmp/commapi/lib/*.so /usr/lib/jvm/java-1.5.0-sun/jre/lib/amd64
cp /tmp/commapi/docs/javax.comm.properties /usr/lib/jvm/java-1.5.0-sun/jre/lib
cd /usr/lib
ln -sf /usr/lib/jvm/java-1.5.0-sun/jre/lib/amd64/libLinuxSerialParallel_g.so
ln -sf /usr/lib/jvm/java-1.5.0-sun/jre/lib/amd64/libLinuxSerialParallel.so
cd $diretorio

