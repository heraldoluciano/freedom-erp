del JBemaFI32.dll
del JBemaFI32.o
del bibli_drivers_JBemaFI32.h
rem javac -classpath m:\appjava\ JBemaFI32.java
javah -classpath ..\..\ -jni org.freedom.drivers.JBemaFI32
gcc -c -o JBemaFI32.o -Ic:\j2sdk1.4.2_03\include -Ic:\j2sdk1.4.2_03\include\win32 JBemaFI32.c 
gcc --enable-stdcall-fixup -shared -o JBemaFI32.dll JBemaFI32.o JBemaFI32.def -Wl,--out-implib,libJBemaFI32.a -L. -lBemaFI32 
copy JBemaFI32.dll C:\windows\system32


