@echo off
@echo #--------------------------------------------------------#
@echo #     Este procedimento copiara o banco de dados de      #
@echo #     desenvolvimento em formato de backup, da area      # 
@echo #     de desenvolvimento para workspace do Eclipse,      #
@echo #     empacotando em um arquivo tipo ZIP!                #
@echo #--------------------------------------------------------#
set /p opt=#   Confirma procedimento de empacotamento (S/N) ?
@echo #
if %opt%==S goto empac
if %opt%==s goto empac 
if %opt%==N goto noempac
if %opt%==n goto noempac 
goto fim
:empac
@echo on
\opt\firebird\bin\gbak -B localhost:/opt/firebird/dados/desenv/freedom.fdb \opt\eclipse\workspace\freedom\dados\estrutura\freedom.fbk -user sysdba -pass masterkey
del \opt\eclipse\workspace\freedom\dados\estrutura\freedom.zip
\opt\7-Zip\7z a -tzip \opt\eclipse\workspace\freedom\dados\estrutura\freedom \opt\eclipse\workspace\freedom\dados\estrutura\freedom.fbk
@echo off
goto fim
:noempac
@echo #
@echo #     Procedimento cancelado!
@echo #
:fim
@echo #
@echo #     Fim do procedimento.
@echo #
pause
