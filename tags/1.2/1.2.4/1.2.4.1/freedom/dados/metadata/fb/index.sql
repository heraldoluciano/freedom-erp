/* Generated by IB DB Comparer v.1.15.Beta.  23/10/2007 11:09:23 */
/* Server Version: WI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */

SET NAMES NONE;

SET SQL DIALECT 3;

CONNECT '/opt/firebird/dados/vazio.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

/* Create Index... */
CREATE INDEX "ATCONVENIADOIDX01" ON "ATCONVENIADO"("ICARDCONV","NRVIACONV");
CREATE INDEX "CPFORNECEDIDX01" ON "CPFORNECED"("RAZFOR");
CREATE INDEX "CPPRODFORIDX01" ON "CPPRODFOR"("REFPRODFOR","CODFILIAL","CODEMP");
CREATE INDEX "CPTIPOFORICX01" ON "CPTIPOFOR"("DESCTIPOFOR");
CREATE INDEX "EQETIQPRODIDX01" ON "EQETIQPROD"("NRCONEXAO","CODPROD");
CREATE INDEX "EQMOVPRODIDX01" ON "EQMOVPROD"("CODLOTE","CODPROD");
CREATE UNIQUE INDEX "EQPRODUTOIDX01" ON "EQPRODUTO"("REFPROD");
CREATE INDEX "EQPRODUTOIDX02" ON "EQPRODUTO"("DESCPROD");
CREATE INDEX "EQUNIDADEIDX01" ON "EQUNIDADE"("DESCUNID");
CREATE INDEX "FNBANCOIDX01" ON "FNBANCO"("NOMEBANCO");
CREATE INDEX "FNCCIDX01" ON "FNCC"("DESCCC");
CREATE INDEX "FNCCIDX02" ON "FNCC"("CODREDCC","CODEMP","CODFILIAL");
CREATE UNIQUE INDEX "FNCONTAIDX01" ON "FNCONTA"("CODPLAN");
CREATE INDEX "FNLANCAIDX01" ON "FNLANCA"("DATALANCA","CODFILIAL","CODEMP");
CREATE INDEX "FNPLANEJAMENTOIDX01" ON "FNPLANEJAMENTO"("DESCPLAN");
CREATE INDEX "FNPLANOPAGIDX01" ON "FNPLANOPAG"("DESCPLANOPAG");
CREATE INDEX "FNSUBLANCAIDX01" ON "FNSUBLANCA"("DATASUBLANCA","CODSUBLANCA","CODLANCA","CODFILIAL","CODEMP");
CREATE INDEX "FNTIPOCOBIDX01" ON "FNTIPOCOB"("DESCTIPOCOB");
CREATE INDEX "SGLOGIDX01" ON "SGLOG"("DATALOG");
CREATE INDEX "SGLOGIDX02" ON "SGLOG"("TIPOLOG");
CREATE INDEX "VDCLIENTEIDX01" ON "VDCLIENTE"("NOMECLI");
CREATE INDEX "VDCLIENTEIDX02" ON "VDCLIENTE"("RAZCLI");
CREATE INDEX "VDCOMISSAOIDX01" ON "VDCOMISSAO"("CODREC","NPARCITREC");
CREATE INDEX "VDSETORIDX01" ON "VDSETOR"("DESCSETOR");
CREATE INDEX "VDTIPOCLIIDX01" ON "VDTIPOCLI"("DESCTIPOCLI");
CREATE INDEX "VDTRANSPIDX01" ON "VDTRANSP"("RAZTRAN");
CREATE INDEX "VDVENDAIDX01" ON "VDVENDA"("DTEMITVENDA");
CREATE INDEX "VDVENDAIDX02" ON "VDVENDA"("DTSAIDAVENDA");
CREATE INDEX "VDVENDEDORIDX01" ON "VDVENDEDOR"("NOMEVEND");
COMMIT WORK;

/* (c) 1998-2002 by Boris Loboda, barry@audit.kharkov.com */