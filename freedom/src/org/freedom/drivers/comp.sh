echo "Compilando JAVA..."
javac -classpath ../../ JBemaFI32.java
if [ "$?" -gt "0" ]; then exit; fi
echo "Gerando cabeçalho..."
javah -classpath ../../ -jni bibli.drivers.JBemaFI32
if [ "$?" -gt "0" ]; then exit; fi
echo "Compilando C..."
gcc -c -I/usr/java/j2sdk1.4.2_02/include -I/usr/java/j2sdk1.4.2_02/include/linux JBemaFI32.c
if [ "$?" -gt "0" ]; then exit; fi
echo "Linkando tudo..."
#gcc -shared -Wall -o JBemaFI32.so JBemaFI32.o lib300fi.o
gcc -shared -Wall -o JBemaFI32.so JBemaFI32.o lib300fi.o
if [ "$?" -gt "0" ]; then exit; fi
echo "Criando lib..."
cp JBemaFI32.so libJBemaFI32.so
