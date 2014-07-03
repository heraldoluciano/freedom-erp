@echo off
title Gerar banco de dados de desenvolvimento Freedom.fdb 
color cf
@echo #--------------------------------------------------------#
@echo #     Este procedimento substituira o banco de dados     #
@echo #     de desenvolvimento de sua maquina!                 #
@echo #--------------------------------------------------------#
set /p opt=#   Confirma procedimento? (S/N)
@echo #
if %opt%==S goto desempac
if %opt%==s goto desempac 
if %opt%==N goto nodesempac
if %opt%==n goto nodesempac 
goto fim
:desempac

set ISC_USER=sysdba
set ISC_PASSWORD=masterkey

@echo off
"C:\opt\firebird\bin\isql" -i drop.sql  localhost:/opt/firebird/dados/desenv/freedom.fdb
"C:\opt\firebird\bin\isql" -i create.sql 
@echo on
"C:\opt\firebird\bin\isql" -i freedom.sql localhost:/opt/firebird/dados/desenv/freedom.fdb
"C:\opt\firebird\bin\isql" -i description.sql localhost:/opt/firebird/dados/desenv/freedom.fdb
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
