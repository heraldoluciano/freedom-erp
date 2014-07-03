@echo off

set JVM=%1

:begin

if [%JVM%]==[] goto fn_uso

goto fn_executa


:fn_uso
  echo Uso: inst_win [directory_jvm]
  echo -----------------------------
  @pause
  goto fn_fim


:fn_executa

  echo Pasta da JVM = %JVM%
  set JAVA=%JVM%\bin\java.exe
  set ZIP=c:\opt\7-zip\7z.exe

  if not exist %JAVA% goto fn_jvm_ne 

  if not exist %ZIP% goto fn_zip_ne

  echo Iniciando processo

  %ZIP% x commapi.zip
  copy commapi\*.jar %JVM%\lib\
  copy commapi\*.dll %JVM%\bin\
  copy commapi\javax.comm.properties %JVM%\lib\

  echo Fim do processo de instala‡ao
  goto fn_fim


:fn_jvm_ne
echo JVM nao foi encontrada em %JAVA%
goto fn_fim

:fn_zip_ne
echo 7-Zip nao foi encontrado em %ZIP%
goto fn_fim

:fn_fim
echo ..

