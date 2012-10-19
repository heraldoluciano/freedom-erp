@echo off
set execdir=%~f0
set removed=
set CMDJAVA=start javaw -Dfile.encoding=ISO-8859-1
set FREEDOMMD=%1
set FREEDOMCL=%2

:strip_exec


set removed=%execdir:~-1%
set execdir=%execdir:~0,-1%

if NOT "%removed%"=="\" goto strip_exec

set removed=
set basedir=%execdir%

:strip_base

set removed=%basedir:~-1%
set basedir=%basedir:~0,-1%

if NOT "%removed%"=="\" goto strip_base

set FREEDOM_EXEC=%execdir%
set FREEDOM_HOME=%basedir%

set FREEDOMFI=%FREEDOM_HOME%\ini\freedom.ini
set STPSPED=%FREEDOM_HOME%\ini\stpnfe.ini
set FREEDOMFL=%FREEDOM_HOME%\log\%FREEDOMMD%.log

set TMP_CP=
dir /b "%FREEDOM_HOME%\lib\*.jar" > %TEMP%\freedom-lib.tmp
FOR /F %%I IN (%TEMP%\freedom-lib.tmp) DO CALL "%FREEDOM_EXEC%\addpath.bat" "%FREEDOM_HOME%\lib\%%I"
set FREEDOM_CP=%TMP_CP%

set CMDFREEDOM=%CMDJAVA% -cp %FREEDOM_CP% -DARQINI=%FREEDOMFI% -DARQINI2=%STPSPED% -DARQLOG=%FREEDOMFL% %FREEDOMCL%

echo %CMDFREEDOM%

