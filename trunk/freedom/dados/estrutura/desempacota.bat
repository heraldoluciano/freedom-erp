@echo off
@echo #--------------------------------------------------------#
@echo #     Este procedimento substituira o banco de dados     #
@echo #     de desenvolvimento de sua maquina!                 #
@echo #--------------------------------------------------------#
set /p opt=#   Confirma procedimento de desempacotamento (S/N) ?
@echo #
if %opt%==S goto desempac
if %opt%==s goto desempac 
if %opt%==N goto nodesempac
if %opt%==n goto nodesempac 
goto fim
:desempac
@echo on
del \opt\firebird\dados\desenv\freedom.fbk
\opt\7-Zip\7z e -tzip -o\opt\firebird\dados\desenv \opt\eclipse\workspace\freedom\dados\estrutura\freedom.zip 
\opt\firebird\bin\gbak -C -R -P 4096 \opt\firebird\dados\desenv\freedom.fbk localhost:/opt/firebird/dados/desenv/freedom.fdb -user sysdba -pass masterkey
@echo off
goto fim
:nodesempac
@echo #
@echo #     Procedimento cancelado!
@echo #
:fim
@echo #
@echo #     Fim do procedimento.
@echo #
pause
