/* Server Version: LI-V6.3.6.5026 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.6.fdb' USER 'SYSDBA' PASSWORD '123654';

SET AUTODDL ON;

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';


COMMIT WORK;
