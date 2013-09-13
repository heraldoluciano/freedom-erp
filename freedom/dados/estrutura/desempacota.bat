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
isql -sqldialect 3 -charset ISO8859_1 -input drop.sql  localhost:c:/opt/firebird/dados/desenv/freedom25.fdb
isql -sqldialect 3 -charset ISO8859_1 -input create.sql 
@echo on
isql -sqldialect 3 -charset ISO8859_1 -input freedom.sql localhost:c:/opt/firebird/dados/desenv/freedom25.fdb
isql -sqldialect 3 -charset ISO8859_1 -input description.sql localhost:c:/opt/firebird/dados/desenv/freedom25.fdb
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
