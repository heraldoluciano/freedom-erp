@echo off
set JAR_FILE=%1
set SP=
if NOT "%TMP_CP%"=="" set SP=;

set TMP_CP=%TMP_CP%%SP%%1


