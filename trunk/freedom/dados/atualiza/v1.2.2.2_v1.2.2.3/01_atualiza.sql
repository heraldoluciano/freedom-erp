/* Server version: LI-V6.3.3.4870 Firebird 1.5 
   SQLDialect: 3. ODS: 10.1. Forced writes: On. Sweep inteval: 20000.
   Page size: 4096. Cache pages: 75 (300 Kb). Read-only: False. */
SET SQL DIALECT 3;

--CONNECT '/opt/firebird/dados/desenv/1.2.2.2/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

SET AUTODDL ON;

/* Alter Procedure... */
/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.2.3 (14/06/2010)';
    suspend;
end
^

