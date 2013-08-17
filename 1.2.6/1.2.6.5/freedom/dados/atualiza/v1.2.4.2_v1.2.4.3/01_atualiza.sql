/* Server Version: UI-V6.3.6.5026 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

Update rdb$Relation_Fields set Rdb$Field_Source = 'NUMERICDN'
WHERE RDB$FIELD_NAME='PERCICMSITVENDA' AND RDB$RELATION_NAME='VDITVENDA';

Update rdb$Relation_Fields set Rdb$Field_Source = 'NUMERICDN'
WHERE RDB$FIELD_NAME='PERCIPIITVENDA' AND RDB$RELATION_NAME='VDITVENDA';

Update rdb$Relation_Fields set Rdb$Field_Source = 'NUMERICDN'
WHERE RDB$FIELD_NAME='PERCCOMISITVENDA' AND RDB$RELATION_NAME='VDITVENDA';

Update rdb$Relation_Fields set Rdb$Field_Source = 'NUMERICDN'
WHERE RDB$FIELD_NAME='PERCISSITVENDA' AND RDB$RELATION_NAME='VDITVENDA';

ALTER TABLE EQMOVPROD ADD SEQENTOP SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial de entrada da ordem de produção '
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='SEQENTOP';

ALTER TABLE FNRECEBER ADD VLRRETENSAOISS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'VALOR DO ISS RETIDO PELO CLIENTE.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='VLRRETENSAOISS';

ALTER TABLE FNSALDOLANCA ADD FECHADO CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o caixa foi fechado para este dia, se ''S'' não deverá permitir lançamentos para esta data.'
where Rdb$Relation_Name='FNSALDOLANCA' and Rdb$Field_Name='FECHADO';

ALTER TABLE FNSALDOLANCA ADD EMMANUT CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o registro está em processo de manutenção.'
where Rdb$Relation_Name='FNSALDOLANCA' and Rdb$Field_Name='EMMANUT';

ALTER TABLE LFITCLFISCAL ADD RETENSAOISS CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve ISS deve ser retido pelo cliente na fonte (pago pelo cliente e descontado na nota);'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='RETENSAOISS';

ALTER TABLE SGPREFERE1 ADD VERSAONFE CHAR(8);

ALTER TABLE SGPREFERE1 ADD REGIMETRIBNFE CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Regime tributário para NFE.
1 - Simples Nacional
2 - Simples Nacional (excesso de sub-limite)
3 - Normal'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='REGIMETRIBNFE';

ALTER TABLE SGPREFERE1 ADD FECHACAIXA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve bloquear caixas para lançamentos retroativos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='FECHACAIXA';

ALTER TABLE SGPREFERE1 ADD FECHACAIXAAUTO CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar o fechamento de caixas automaticamente, 
ou através de procedimento manual.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='FECHACAIXAAUTO';

ALTER TABLE SGPREFERE1 ADD NUMDIGIDENTTIT SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Numero de digitos para campo de identificacao do titulo em arquivos de remessa padrao cnab (parametro corretivo para periodo intermediario entre a versao 1.2.4.1 e 1.2.4.2);'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='NUMDIGIDENTTIT';

ALTER TABLE SGPREFERE5 ADD PRODETAPAS CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve permitir a finalização de Ops em etapas.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='PRODETAPAS';

ALTER TABLE VDVENDEDOR ADD VLRABONO NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do abono (utilizado nos relatórios de comissionamento pela produção.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='VLRABONO';

ALTER TABLE VDVENDEDOR ADD VLRDESCONTO NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do desconto (utilizado nos relatórios de comissionamento pela produção.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='VLRDESCONTO';

/* Create Table... */
CREATE TABLE PPOPENTRADA(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODOP INTEGER NOT NULL,
SEQOP SMALLINT DEFAULT 0 NOT NULL,
SEQENT SMALLINT DEFAULT 0 NOT NULL,
DTENT DATE DEFAULT 'now' NOT NULL,
HENT TIME DEFAULT 'now' NOT NULL,
QTDENT NUMERICDN DEFAULT 0 NOT NULL,
OBSENT VARCHAR(1000),
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relation_Fields set Rdb$Description =
'Data da entrada.'
where Rdb$Relation_Name='PPOPENTRADA' and Rdb$Field_Name='DTENT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da entrada.'
where Rdb$Relation_Name='PPOPENTRADA' and Rdb$Field_Name='HENT';


/* Create Index... */
CREATE INDEX LFLIVROFISCALIDXEMIT ON LFLIVROFISCAL(CODEMITLF,CODFILIALET,CODEMPET);

CREATE INDEX PPOPENTRADAIDX ON PPOPENTRADA(DTENT);


/* Create Exception... */
CREATE EXCEPTION FNSALDOLANCAEX01 'O Caixa da data informada não permite novos lançamentos! Caixa fechado.';


/* Create Primary Key... */
ALTER TABLE PPOPENTRADA ADD CONSTRAINT PPOPENTRADAPK PRIMARY KEY (CODOP,SEQOP,SEQENT,CODFILIAL,CODEMP);

/* Create Foreign Key... */
--connect ''localhost:/opt/firebird/dados/desenv/1.2.4.2/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey' ROLE 'ADM';

ALTER TABLE PPOPENTRADA ADD CONSTRAINT PPOPENTRADAFKPPOP FOREIGN KEY (CODOP,SEQOP,CODFILIAL,CODEMP) REFERENCES PPOP(CODOP,SEQOP,CODFILIAL,CODEMP);

/*  Empty EQMOVPRODIUDSP for EQMOVPRODDSP(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODIUDSP(CIUD CHAR(1),
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER CPITCOMPRATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITCOMPRATGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITCOMPRATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQINVPRODTGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQINVPRODTGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQINVPRODTGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPTGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPTGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPTGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

/*  Empty EQMOVPRODDSP for EQMOVPRODRETCODSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/*  Empty EQMOVPRODUSP for EQMOVPRODRETCODSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER VDVENDATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/*  Empty LFBUSCAPREVTRIBORC for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty VDADICITVENDAORCSP for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA FLOAT,
VLRDESCITVENDA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty VDADICITEMPDVSP for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
 BEGIN EXIT; END
^

/*  Empty VDBUSCACUSTOVENDASP for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty LFBUSCATRIBCOMPRA for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
 BEGIN EXIT; END
^

/*  Empty CPADICITCOMPRAPEDSP for LFBUSCAFISCALSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODDSP with new param-list */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODISP with new param-list */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODRETCODSP with new param-list */
ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODUSP with new param-list */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure LFBUSCAFISCALSP with new param-list */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca é para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de classificação fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do ítem de classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de classficiação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

/* Alter Procedure... */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f1.percissfilial, f.aliqissfisc)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

/* Alter (CPADICITCOMPRAPEDSP) */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod char(13);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Retorna o código do movimento de estoque */
  if (ICODINVPROD IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPIV=:ICODEMPIV AND MP.CODFILIALIV=:SCODFILIALIV
         AND MP.CODINVPROD=:ICODINVPROD
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODCOMPRA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPCP=:ICODEMPCP AND MP.CODFILIALCP=:SCODFILIALCP
         AND MP.CODCOMPRA=:ICODCOMPRA AND MP.CODITCOMPRA=:SCODITCOMPRA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODVENDA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPVD=:ICODEMPVD AND MP.CODFILIALVD=:SCODFILIALVD
         AND MP.TIPOVENDA=:CTIPOVENDA AND MP.CODVENDA=:ICODVENDA
         AND MP.CODITVENDA=:SCODITVENDA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODRMA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPRM=:ICODEMPRM AND MP.CODFILIALRM=:SCODFILIALRM
         AND MP.CODRMA=:ICODRMA AND MP.CODITRMA=:SCODITRMA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODOP IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
         AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqentop=:sseqopent
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;



  suspend;
end
^

/* Alter (EQMOVPRODDSP) */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

/* Alter (EQMOVPRODISP) */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable cestoqmovprod char(1);
declare variable ctipomovprod char(1);
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable soperador smallint;
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de inserção na tabela eqmovprod */

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX FROM EQMOVPRODSLDSP(null, null, null, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NPRECOMOVPROD, :NPRECOMOVPROD,
     :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX )
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

  /* Verifica se haverá mudança de saldo*/
  SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD, CTIPOMOVPROD, SOPERADOR FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX)
        INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
  end

  SELECT SCODFILIAL, ICODMOVPROD FROM EQMOVPRODSEQSP(:ICODEMPPD)
     INTO :SCODFILIAL, :ICODMOVPROD;  /* encontra o próximo código e a filial*/

   INSERT INTO EQMOVPROD ( CODEMP, CODFILIAL, CODMOVPROD,
      CODEMPPD, CODFILIALPD , CODPROD , CODEMPLE ,
      CODFILIALLE , CODLOTE , CODEMPTM, CODFILIALTM,
      CODTIPOMOV, CODEMPIV , CODFILIALIV , CODINVPROD ,
      CODEMPCP , CODFILIALCP , CODCOMPRA , CODITCOMPRA , CODEMPVD ,
      CODFILIALVD , TIPOVENDA , CODVENDA , CODITVENDA , CODEMPRM ,
      CODFILIALRM , CODRMA , CODITRMA ,
      CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENTOP,
      CODEMPNT , CODFILIALNT ,
      CODNAT , DTMOVPROD , DOCMOVPROD , FLAG , QTDMOVPROD ,
      PRECOMOVPROD, ESTOQMOVPROD, TIPOMOVPROD, SLDMOVPROD, CUSTOMPMMOVPROD,
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX )
   VALUES ( :ICODEMPPD, :SCODFILIAL, :ICODMOVPROD,
    :ICODEMPPD , :SCODFILIALPD , :ICODPROD , :ICODEMPLE ,
    :SCODFILIALLE , :CCODLOTE , :ICODEMPTM, :SCODFILIALTM,
    :ICODTIPOMOV, :ICODEMPIV , :SCODFILIALIV ,
    :ICODINVPROD , :ICODEMPCP , :SCODFILIALCP , :ICODCOMPRA ,
    :SCODITCOMPRA , :ICODEMPVD , :SCODFILIALVD , :CTIPOVENDA ,
    :ICODVENDA , :SCODITVENDA , :ICODEMPRM , :SCODFILIALRM ,
    :ICODRMA , :SCODITRMA , :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop,
    :ICODEMPNT , :SCODFILIALNT , :CCODNAT ,
    :DDTMOVPROD , :IDOCMOVPROD , :CFLAG , :NQTDMOVPROD ,
    :NPRECOMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX );

  /* REPROCESSAR ESTOQUE */

  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
     :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
     :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

 /* ATUALIZA O CUSTO NO CADASTRO DE PRODUTOS
   EXECUTE PROCEDURE EQMOVPRODATCUSTSP(:SOPERADOR, :ICODEMPPD, :SCODFILIAL,
    :ICODMOVPROD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMMOVPROD); 
 */


  suspend;
end
^

ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldprc numeric(15,5);
declare variable ncustompmprc numeric(15,5);
declare variable nsldprcax numeric(15,5);
declare variable ncustompmprcax numeric(15,5);
declare variable nsldlc numeric(15,5);
declare variable ncustompmlc numeric(15,5);
declare variable nsldlcax numeric(15,5);
declare variable ncustompmlcax numeric(15,5);
declare variable ddtmovprodold date;
declare variable nprecomovprodold numeric(15,5);
declare variable nqtdmovprodold numeric(15,5);
declare variable icodemptmold integer;
declare variable scodfilialtmold smallint;
declare variable icodtipomovold integer;
declare variable calttm char(1);
declare variable ddtprc date;
declare variable ddtprcate date;
declare variable cestoqmovprod char(1);
begin
  /* Procedure de atualização da tabela eqmovprod */

  DDTPRCATE = NULL; /* Até onde deve ser processando o estoque */
 /* localiza movprod */

-- execute procedure sgdebugsp('antes da atualização...','no inicio');

  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
    FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
      :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
      :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM,
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

--  traz valores antigos

  SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV, MP.DTMOVPROD,
       MP.PRECOMOVPROD, MP.QTDMOVPROD  FROM EQMOVPROD MP
     WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND MP.CODMOVPROD=:ICODMOVPROD
     INTO :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD, :DDTMOVPRODOLD,
       :NPRECOMOVPRODOLD, :NQTDMOVPRODOLD;

   /* abaixo verificação se a alteração de tipo de movimento mexe no estoque */
   SELECT CALTTM FROM EQMOVPRODCKUTMSP(:ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
      :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD) INTO :CALTTM;

   /* verifica se há relevância para reprocessamento */
   if ( (DDTMOVPROD!=DDTMOVPRODOLD) OR (CALTTM='S') OR
        (NPRECOMOVPROD!=NPRECOMOVPRODOLD) OR (NQTDMOVPROD!=NQTDMOVPRODOLD) ) then
   begin

   -- execute procedure sgdebugsp('entrou no if','1');


      if ( DDTMOVPRODOLD IS NULL) then
         DDTMOVPRODOLD = DDTMOVPROD; /* garantir que a data antiga não e nula; */
      /* verifica qual data é menor para reprocessamento */
      if ( DDTMOVPROD<=DDTMOVPRODOLD ) then
      begin

     -- execute procedure sgdebugsp('entrou no if','2');

          DDTPRC = DDTMOVPROD;
          if (DDTMOVPROD=DDTMOVPRODOLD) then
             DDTPRCATE = null;
          else
             DDTPRCATE = DDTMOVPRODOLD;
/*          verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, :NPRECOMOVPROD, :NPRECOMOVPROD,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC)
              INTO :NSLDPRC, :NCUSTOMPMPRC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
              NSLDPRCAX = NSLDPRC;
              NCUSTOMPMPRCAX = NCUSTOMPMPRC;
          end
          else
          begin
          SELECT NSALDO, NCUSTOMPM
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX)
              INTO :NSLDPRCAX, :NCUSTOMPMPRCAX;
          end
          NCUSTOMPMLC = NCUSTOMPMPRC;
          NSLDLC = NSLDPRC;
          NCUSTOMPMLCAX = NCUSTOMPMPRCAX;
          NSLDLCAX = NSLDPRCAX;
      end
      else
      begin
          DDTPRC = DDTMOVPRODOLD;
          DDTPRCATE = DDTMOVPROD;
          /* verifica lançamento anterior e traz custo e saldo */

       --   execute procedure sgdebugsp('entrou no else','3');

          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, 0, 0,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMLC, :NCUSTOMPMLCAX,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDLC, :NCUSTOMPMLC, :NSLDLCAX, :NCUSTOMPMLCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
              :NCUSTOMPMLC, :NSLDLC)
              INTO :NSLDLC, :NCUSTOMPMLC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
             NSLDLCAX = NSLDLC;
             NCUSTOMPMLCAX = NCUSTOMPMLC;
          end
          else
          begin
              SELECT NSALDO, NCUSTOMPM
                  FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
                  :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
                  :NCUSTOMPMLCAX, :NSLDLCAX)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

      /* REPROCESSAR ESTOQUE */

    --  execute procedure sgdebugsp('antes do reprocessamento ESTOQUE',CESTOQMOVPROD);
    --   execute procedure sgdebugsp('antes do reprocessamento QTD',NQTDMOVPROD);
    --   execute procedure sgdebugsp('MOVPROD COD',ICODMOVPROD);

       SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
        FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
          :SCODFILIALPD, :ICODPROD, :DDTPRC, :DDTPRCATE, :NSLDPRC,
          :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX,
          :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
        INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX;

      UPDATE EQMOVPROD SET DTMOVPROD=:DDTMOVPROD,
         QTDMOVPROD=:NQTDMOVPROD, PRECOMOVPROD=:NPRECOMOVPROD,
         SLDMOVPROD=:NSLDLC, CUSTOMPMMOVPROD=:NCUSTOMPMLC,
         SLDMOVPRODAX=:NSLDLCAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMLCAX,
         FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE, ESTOQMOVPROD=:CESTOQMOVPROD ,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
   end
   else /*  caso não tenha nenhuma alteração relevânte para processamento */

  --  execute procedure sgdebugsp('antes do reprocessamento','5SG');

      UPDATE EQMOVPROD SET FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
end
^

ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable cestoqmovprod char(1);
declare variable ctipomovprod char(1);
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable soperador smallint;
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de inserção na tabela eqmovprod */

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX FROM EQMOVPRODSLDSP(null, null, null, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NPRECOMOVPROD, :NPRECOMOVPROD,
     :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX )
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

  /* Verifica se haverá mudança de saldo*/
  SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD, CTIPOMOVPROD, SOPERADOR FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX)
        INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
  end

  SELECT SCODFILIAL, ICODMOVPROD FROM EQMOVPRODSEQSP(:ICODEMPPD)
     INTO :SCODFILIAL, :ICODMOVPROD;  /* encontra o próximo código e a filial*/

   INSERT INTO EQMOVPROD ( CODEMP, CODFILIAL, CODMOVPROD,
      CODEMPPD, CODFILIALPD , CODPROD , CODEMPLE ,
      CODFILIALLE , CODLOTE , CODEMPTM, CODFILIALTM,
      CODTIPOMOV, CODEMPIV , CODFILIALIV , CODINVPROD ,
      CODEMPCP , CODFILIALCP , CODCOMPRA , CODITCOMPRA , CODEMPVD ,
      CODFILIALVD , TIPOVENDA , CODVENDA , CODITVENDA , CODEMPRM ,
      CODFILIALRM , CODRMA , CODITRMA ,
      CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENTOP,
      CODEMPNT , CODFILIALNT ,
      CODNAT , DTMOVPROD , DOCMOVPROD , FLAG , QTDMOVPROD ,
      PRECOMOVPROD, ESTOQMOVPROD, TIPOMOVPROD, SLDMOVPROD, CUSTOMPMMOVPROD,
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX )
   VALUES ( :ICODEMPPD, :SCODFILIAL, :ICODMOVPROD,
    :ICODEMPPD , :SCODFILIALPD , :ICODPROD , :ICODEMPLE ,
    :SCODFILIALLE , :CCODLOTE , :ICODEMPTM, :SCODFILIALTM,
    :ICODTIPOMOV, :ICODEMPIV , :SCODFILIALIV ,
    :ICODINVPROD , :ICODEMPCP , :SCODFILIALCP , :ICODCOMPRA ,
    :SCODITCOMPRA , :ICODEMPVD , :SCODFILIALVD , :CTIPOVENDA ,
    :ICODVENDA , :SCODITVENDA , :ICODEMPRM , :SCODFILIALRM ,
    :ICODRMA , :SCODITRMA , :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop,
    :ICODEMPNT , :SCODFILIALNT , :CCODNAT ,
    :DDTMOVPROD , :IDOCMOVPROD , :CFLAG , :NQTDMOVPROD ,
    :NPRECOMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX );

  /* REPROCESSAR ESTOQUE */

  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
     :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
     :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

 /* ATUALIZA O CUSTO NO CADASTRO DE PRODUTOS
   EXECUTE PROCEDURE EQMOVPRODATCUSTSP(:SOPERADOR, :ICODEMPPD, :SCODFILIAL,
    :ICODMOVPROD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMMOVPROD); 
 */


  suspend;
end
^

/* Alter (EQMOVPRODIUDSP) */
ALTER PROCEDURE EQMOVPRODIUDSP(CIUD CHAR(1),
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 AS
declare variable cmultialmox char(1);
begin
  /* Procedure que controle INSERT, UPDATE E DELETE da tabela eqmovprod */

  /* Linha incluida para passar como parâmetro se é multi almoxarifado
      Como o objetivo de evitar I/O
  */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMPPD) INTO :CMULTIALMOX;
  
  if (CIUD='I') then /* SE FOR INSERT */
     execute procedure EQMOVPRODISP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX);
  else if (CIUD='U') then
     execute procedure EQMOVPRODUSP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX);
  else if (CIUD='D') then
     execute procedure EQMOVPRODDSP( ICODEMPPD, SCODFILIALPD, ICODPROD, ICODEMPIV,
         SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA, SCODITCOMPRA,
         ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         DDTMOVPROD, ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX );
  suspend;
end
^

/* Alter (EQMOVPRODRETCODSP) */
ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Retorna o código do movimento de estoque */
  if (ICODINVPROD IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPIV=:ICODEMPIV AND MP.CODFILIALIV=:SCODFILIALIV
         AND MP.CODINVPROD=:ICODINVPROD
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODCOMPRA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPCP=:ICODEMPCP AND MP.CODFILIALCP=:SCODFILIALCP
         AND MP.CODCOMPRA=:ICODCOMPRA AND MP.CODITCOMPRA=:SCODITCOMPRA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODVENDA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPVD=:ICODEMPVD AND MP.CODFILIALVD=:SCODFILIALVD
         AND MP.TIPOVENDA=:CTIPOVENDA AND MP.CODVENDA=:ICODVENDA
         AND MP.CODITVENDA=:SCODITVENDA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODRMA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPRM=:ICODEMPRM AND MP.CODFILIALRM=:SCODFILIALRM
         AND MP.CODRMA=:ICODRMA AND MP.CODITRMA=:SCODITRMA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODOP IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
         AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqentop=:sseqopent
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;



  suspend;
end
^

/* Alter (EQMOVPRODUSP) */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldprc numeric(15,5);
declare variable ncustompmprc numeric(15,5);
declare variable nsldprcax numeric(15,5);
declare variable ncustompmprcax numeric(15,5);
declare variable nsldlc numeric(15,5);
declare variable ncustompmlc numeric(15,5);
declare variable nsldlcax numeric(15,5);
declare variable ncustompmlcax numeric(15,5);
declare variable ddtmovprodold date;
declare variable nprecomovprodold numeric(15,5);
declare variable nqtdmovprodold numeric(15,5);
declare variable icodemptmold integer;
declare variable scodfilialtmold smallint;
declare variable icodtipomovold integer;
declare variable calttm char(1);
declare variable ddtprc date;
declare variable ddtprcate date;
declare variable cestoqmovprod char(1);
begin
  /* Procedure de atualização da tabela eqmovprod */

  DDTPRCATE = NULL; /* Até onde deve ser processando o estoque */
 /* localiza movprod */

-- execute procedure sgdebugsp('antes da atualização...','no inicio');

  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
    FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
      :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
      :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM,
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

--  traz valores antigos

  SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV, MP.DTMOVPROD,
       MP.PRECOMOVPROD, MP.QTDMOVPROD  FROM EQMOVPROD MP
     WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND MP.CODMOVPROD=:ICODMOVPROD
     INTO :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD, :DDTMOVPRODOLD,
       :NPRECOMOVPRODOLD, :NQTDMOVPRODOLD;

   /* abaixo verificação se a alteração de tipo de movimento mexe no estoque */
   SELECT CALTTM FROM EQMOVPRODCKUTMSP(:ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
      :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD) INTO :CALTTM;

   /* verifica se há relevância para reprocessamento */
   if ( (DDTMOVPROD!=DDTMOVPRODOLD) OR (CALTTM='S') OR
        (NPRECOMOVPROD!=NPRECOMOVPRODOLD) OR (NQTDMOVPROD!=NQTDMOVPRODOLD) ) then
   begin

   -- execute procedure sgdebugsp('entrou no if','1');


      if ( DDTMOVPRODOLD IS NULL) then
         DDTMOVPRODOLD = DDTMOVPROD; /* garantir que a data antiga não e nula; */
      /* verifica qual data é menor para reprocessamento */
      if ( DDTMOVPROD<=DDTMOVPRODOLD ) then
      begin

     -- execute procedure sgdebugsp('entrou no if','2');

          DDTPRC = DDTMOVPROD;
          if (DDTMOVPROD=DDTMOVPRODOLD) then
             DDTPRCATE = null;
          else
             DDTPRCATE = DDTMOVPRODOLD;
/*          verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, :NPRECOMOVPROD, :NPRECOMOVPROD,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC)
              INTO :NSLDPRC, :NCUSTOMPMPRC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
              NSLDPRCAX = NSLDPRC;
              NCUSTOMPMPRCAX = NCUSTOMPMPRC;
          end
          else
          begin
          SELECT NSALDO, NCUSTOMPM
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX)
              INTO :NSLDPRCAX, :NCUSTOMPMPRCAX;
          end
          NCUSTOMPMLC = NCUSTOMPMPRC;
          NSLDLC = NSLDPRC;
          NCUSTOMPMLCAX = NCUSTOMPMPRCAX;
          NSLDLCAX = NSLDPRCAX;
      end
      else
      begin
          DDTPRC = DDTMOVPRODOLD;
          DDTPRCATE = DDTMOVPROD;
          /* verifica lançamento anterior e traz custo e saldo */

       --   execute procedure sgdebugsp('entrou no else','3');

          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, 0, 0,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMLC, :NCUSTOMPMLCAX,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDLC, :NCUSTOMPMLC, :NSLDLCAX, :NCUSTOMPMLCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
              :NCUSTOMPMLC, :NSLDLC)
              INTO :NSLDLC, :NCUSTOMPMLC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
             NSLDLCAX = NSLDLC;
             NCUSTOMPMLCAX = NCUSTOMPMLC;
          end
          else
          begin
              SELECT NSALDO, NCUSTOMPM
                  FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
                  :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
                  :NCUSTOMPMLCAX, :NSLDLCAX)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

      /* REPROCESSAR ESTOQUE */

    --  execute procedure sgdebugsp('antes do reprocessamento ESTOQUE',CESTOQMOVPROD);
    --   execute procedure sgdebugsp('antes do reprocessamento QTD',NQTDMOVPROD);
    --   execute procedure sgdebugsp('MOVPROD COD',ICODMOVPROD);

       SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
        FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
          :SCODFILIALPD, :ICODPROD, :DDTPRC, :DDTPRCATE, :NSLDPRC,
          :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX,
          :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
        INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX;

      UPDATE EQMOVPROD SET DTMOVPROD=:DDTMOVPROD,
         QTDMOVPROD=:NQTDMOVPROD, PRECOMOVPROD=:NPRECOMOVPROD,
         SLDMOVPROD=:NSLDLC, CUSTOMPMMOVPROD=:NCUSTOMPMLC,
         SLDMOVPRODAX=:NSLDLCAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMLCAX,
         FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE, ESTOQMOVPROD=:CESTOQMOVPROD ,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
   end
   else /*  caso não tenha nenhuma alteração relevânte para processamento */

  --  execute procedure sgdebugsp('antes do reprocessamento','5SG');

      UPDATE EQMOVPROD SET FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
end
^

/* Alter (FNADICRECEBERSP01) */
ALTER PROCEDURE FNADICRECEBERSP01(CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
ICODEMPTC INTEGER,
ICODFILIALTC INTEGER,
CODTIPOCOB INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
CODCLI INTEGER,
ICODEMPVD INTEGER,
ICODFILIALVD SMALLINT,
CODVEND INTEGER,
VLRLIQVENDA NUMERIC(15,5),
DTVENDA DATE,
VLRCOMISVENDA NUMERIC(15,5),
DOCVENDA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPCB INTEGER,
CODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
FLAG CHAR(1),
OBSREC VARCHAR(250),
VLRBASECOMIS NUMERIC(15,5),
VLRRETENSAOISS NUMERIC(15,5))
 AS
declare variable icodrec integer;
declare variable scodfilialrc smallint;
declare variable iparcplanopag integer;
BEGIN
  SELECT R.CODREC,R.CODFILIAL FROM FNRECEBER R
     WHERE R.CODEMPVA=:ICODEMP AND R.CODFILIALVA=:ICODFILIAL AND
       R.TIPOVENDA=:CTIPOVENDA AND R.CODVENDA=:ICODVENDA
     INTO :ICODREC,:SCODFILIALRC;
  SELECT PARCPLANOPAG FROM FNPLANOPAG WHERE CODEMP=:ICODEMPPG AND
     CODFILIAL=:ICODFILIALPG AND CODPLANOPAG=:CODPLANOPAG
     INTO :IPARCPLANOPAG;

  IF ( (ICODREC IS NULL) AND (IPARCPLANOPAG>0) ) THEN
  BEGIN
     SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNRECEBER') INTO SCODFILIALRC;
     SELECT ISEQ FROM SPGERANUM(:ICODEMP,:SCODFILIALRC,'RC') INTO :ICODREC;

     -- Caso haja retensão de ISS deve
     if(coalesce(:vlrretensaoiss,0)>0) then
     begin
        vlrliqvenda = vlrliqvenda - vlrretensaoiss;
     end

     INSERT INTO FNRECEBER (CODEMP,CODFILIAL,CODREC, CODTIPOCOB, CODEMPTC, CODFILIALTC,
              CODPLANOPAG,CODEMPPG,CODFILIALPG,CODCLI,
              CODEMPCL,CODFILIALCL,CODVEND,CODEMPVD,CODFILIALVD,TIPOVENDA,CODVENDA,
              CODEMPVA,CODFILIALVA,VLRREC,
              VLRDESCREC,VLRMULTAREC,VLRJUROSREC,VLRPARCREC,VLRPAGOREC,
              VLRAPAGREC,DATAREC,STATUSREC,VLRCOMIREC,DOCREC,CODBANCO,CODEMPBO,CODFILIALBO,
              CODEMPCB, CODFILIALCB, CODCARTCOB, FLAG, OBSREC, vlrbasecomis)
              VALUES (
                     :ICODEMP,:SCODFILIALRC,:ICODREC, :CODTIPOCOB, :ICODEMPTC, :ICODFILIALTC,
                     :CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodCli,
                     :ICODEMPCL,:ICODFILIALCL,:CodVend,:ICODEMPVD,:ICODFILIALVD,:CTIPOVENDA,:ICodVenda,
                     :ICODEMP,:ICODFILIAL,:VlrLiqVenda,
                     0,0,0,:VlrLiqVenda,0,:VlrLiqVenda,:dtVenda,'R1',
                     :VlrComisVenda,:DocVenda,:CodBanco,:ICODEMPBO,:ICODFILIALBO,
                     :CODEMPCB, :CODFILIALCB, :CODCARTCOB, :FLAG, :OBSREC,:vlrbasecomis
              );
  END
  ELSE IF (ICODREC IS NOT NULL) THEN
  BEGIN
    IF (IPARCPLANOPAG>0) THEN
    BEGIN
        UPDATE FNRECEBER SET ALTUSUREC='N',
              CODTIPOCOB=:CODTIPOCOB,CODEMPTC=:ICODEMPTC, CODFILIALTC=:ICODFILIALTC,
              CODPLANOPAG=:CodPlanoPag,CODEMPPG=:ICODEMPPG,CODFILIALPG=:ICODFILIALPG,
              CODCLI=:CodCli, CODEMPCL=:ICODEMPCL,CODFILIALCL=:ICODFILIALCL,
              CODVEND=:CodVend,CODEMPVD=:ICODEMPVD,CODFILIALVD=:ICODFILIALVD,
              VLRREC=:VlrLiqVenda,VLRDESCREC=0,VLRMULTAREC=0,VLRJUROSREC=0,
              VLRPARCREC=:VlrLiqVenda,VLRPAGOREC=0,VLRAPAGREC=:VlrLiqVenda,
              DATAREC=:dtVenda,VLRCOMIREC=:VlrComisVenda,
              /* STATUSREC='R1' */
              DOCREC=:DocVenda,CODBANCO=:CodBanco,CODEMPBO=:ICODEMPBO,
              CODFILIALBO=:ICODFILIALBO,
              CODEMPCB=:CODEMPCB, CODFILIALCB=:CODFILIALCB, CODCARTCOB=:CODCARTCOB, FLAG=:FLAG,
              vlrbasecomis=:vlrbasecomis
             WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIALRC;

        UPDATE FNITRECEBER SET ALTUSUITREC='S' /* Atualiza os itens de contas a */
        /* receber para ajustar automaticamente os valores no cabeçalho */
             WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIALRC;
     END
     ELSE
     BEGIN
         DELETE FROM FNRECEBER WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND
            CODFILIAL=:SCODFILIALRC;
     END
   END

END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retensão do tributo ISS (descontando do valor final do título)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

/* Alter (LFBUSCAFISCALSP) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f1.percissfilial, f.aliqissfisc)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca é para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de classificação fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do ítem de classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de classficiação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

/* Alter (LFBUSCAPREVTRIBORC) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc smallint;
declare variable vlrbasepis numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrprod integer;
declare variable aliqpis numeric(6,2);
declare variable qtd numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(6,2);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrliq numeric(15,5);
declare variable vlrfrete numeric(15,5);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable aliqipi numeric(6,2);
declare variable tpcalcipi char(1);
declare variable vlrbaseipi numeric(15,5);
declare variable aliqcsocial numeric(6,2);
declare variable aliqir numeric(6,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable tipoprod char(1);
declare variable aliqiss numeric(6,2);
declare variable aliqicms numeric(6,2);
declare variable tpredicms char(1);
declare variable redicms numeric(15,5);
declare variable baseicms numeric(15,5);
declare variable codtrattrib char(2);
declare variable codsittribpis char(2);
declare variable ufcli char(2);
begin

    -- Inicializando variáveis

    vlripi = 0;
    vlrfrete = 0;

    -- Buscando informações no orçamento (cliente e tipo de movimento)
    select oc.codempcl,oc.codfilialcl,oc.codcli,tm.codemptm,tm.codfilialtm,tm.codtipomovtm,coalesce(cl.siglauf,cl.ufcli)
    from vdorcamento oc, vdcliente cl, eqtipomov tm
    where oc.codemp=:codemp and oc.codfilial=:codfilial and oc.codorc=:codorc and oc.tipoorc=:tipoorc
    and cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli
    and tm.codemp=oc.codemp and tm.codfilial=oc.codfilialtm and tm.codtipomov=oc.codtipomov
    into :codempcl, :codfilialcl, :codcli, :codemptm, :codfilialtm, :codtipomov, :ufcli;

    -- Buscando informações do produto no item de orçamento
    select io.codemppd, io.codfilialpd, io.codprod, io.vlrproditorc, io.qtditorc, io.vlrliqitorc, coalesce(io.vlrfreteitorc,0),
    pd.tipoprod

    from vditorcamento io, eqproduto pd
    where io.codemp=:codemp and io.codfilial=:codfilial and io.codorc=:codorc and io.tipoorc=:tipoorc and io.coditorc=:coditorc
    and pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod
    into :codemppd, :codfilialpd, :codprod, :vlrprod, :qtd, :vlrliq, :vlrfrete, :tipoprod;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempcl, :codfilialcl, :codcli,
    :codemptm, :codfilialtm, :codtipomov, 'VD',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.codsittribpis, cf.aliqpisfisc, cf.vlrpisunidtrib, cf.codsittribcof, cf.aliqcofinsfisc, cf.vlrcofunidtrib,
    cf.vlripiunidtrib, cf.aliqipifisc, cf.codsittribipi, cf.tpcalcipi,
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(fi.percissfilial, cf.aliqissfisc),
    cf.tpredicmsfisc, cf.redfisc, cf.codtrattrib
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :codsittribpis, :aliqpis, :vlrpisunidtrib, :codsittribcof, :aliqcofins, :vlrcofunidtrib,
    :vlripiunidtrib, :aliqipi, :codsittribipi,:tpcalcipi, :aliqcsocial, :aliqir, :aliqiss,
    :tpredicms, :redicms, :codtrattrib;

    -- Definição do IPI
    if(:codsittribipi not in ('52','53','54')) then -- IPI Tributado
    begin
        if(:tpcalcipi='P' and aliqipi is not null and aliqipi > 0) then -- Calculo pela aliquota
        begin
            vlrbaseipi = :vlrliq; -- (Base do IPI = Valor total dos produtos - Implementar situações distintas futuramente)
            vlripi = (vlrbaseipi * aliqipi) / 100;
        end
        else if (vlripiunidtrib is not null and vlripiunidtrib > 0) then -- Calculo pela quantidade
        begin
            vlripi = qtd * vlripiunidtrib;
        end
    end

    -- Definição do PIS

    if(:codsittribpis in ('01','02','99') and aliqpis is not null and aliqpis > 0 ) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprod; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:codsittribpis in ('03') and vlrpisunidtrib is not null and vlrpisunidtrib > 0) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtd * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:codsittribcof in ('01','02','99') and aliqcofins is not null and aliqcofins > 0 ) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprod; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:codsittribpis in ('03') and vlrcofunidtrib is not null and vlrcofunidtrib > 0) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtd * vlrcofunidtrib;
    end

    -- Definição do IR

    if(aliqir is not null and aliqir > 0) then
    begin
        vlrir = ((:vlrliq + :vlripi + :vlrfrete) * aliqir) / 100;
    end

    -- Definição da CSocial

    vlrcsocial = ((:vlrliq + :vlripi + :vlrfrete) * aliqcsocial) / 100;

    -- Definição do ISS se for um serviço
    if (tipoprod = 'S') then
    begin
        if ( aliqiss is not null and aliqiss > 0 ) then
        begin
            vlriss = vlrliq * (aliqiss/100);
        end
    end

    -- Definição do ICMS
    -- Calcular icms quando aliquota maio que zero e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

    if(codtrattrib not in ('40','30','41','50')) then
    begin

        if(aliqicms is null) then
        begin
            select ti.aliqti from lftabicms ti
            where codemp=:codemp and codfilial=:codfilial and ufti=:ufcli
            into aliqicms;
        end

        if (redicms>0) then -- Com redução
        begin
            if(tpredicms='B') then -- Redução na base de cálculo
            begin
                baseicms = vlrliq * ( 1 - (redicms / 100));
                vlricms = baseicms * (aliqicms / 100);
            end
            else -- Redução no valor
            begin

                baseicms = vlrliq;
                vlricms = baseicms * ( aliqicms / 100 );
                vlricms = vlricms * (( 100 - redicms ) / 100);


            end
        end
        else -- Sem redução
        begin

            baseicms = vlrliq;
            vlricms = baseicms * (aliqicms / 100);

        end

    end
  suspend;
end
^

/* Alter (LFBUSCATRIBCOMPRA) */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
begin

    -- Inicializando variáveis

    vlrfunrural = 0;

    -- Buscando informações na compra (fornecedor e tipo de movimento)
    select cp.codempfr,cp.codfilialfr,cp.codfor,tm.codemptm,tm.codfilialtm,tm.codtipomovtm
    from cpcompra cp, cpforneced fr, eqtipomov tm
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra
    and fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    and tm.codemp=cp.codemp and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov
    into :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.aliqfunruralfisc
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :aliqfunrural;

    -- Definição do Funrural
    if(:aliqfunrural>0) then -- Retenção do funrural
    begin
        vlrbasefunrural = :vlrliq; -- (Base do Funrural = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrfunrural = (vlrbasefunrural * aliqfunrural) / 100;
    end


    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.3 (08/12/2010)';
    suspend;
end
^

/* Alter (VDADICITEMPDVSP) */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod char(13);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;

  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,null)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT,null,null,null,null)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(15,5);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(15,5);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(15,5);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(15,5);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,5);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod char(13);
declare variable stipoprod char(1);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

    -- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

    -- Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)

    select codempif,codfilialif,codfisc,coditfisc
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',null,null,null,null,null)
    into CODEMPIF,CODFILIALIF,CODFISC,CODITFISC;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,
    :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,:coditfisc)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda (já sabe o coditfisc)

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
    -- Bloco comentado, pois já buscou o percentual do iss através da procedure.
   --     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
   --     into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Alter (VDBUSCACUSTOVENDASP) */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
declare variable aliqicms numeric(9,2);
declare variable aliqipi numeric(9,2);
declare variable aliqpis numeric(9,2);
declare variable aliqir numeric(9,2);
declare variable aliqcofins numeric(9,2);
declare variable aliqcsocial numeric(9,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codfilialpf smallint;
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable vlrliq numeric(15,5);
declare variable redbase numeric(9,2);
declare variable base numeric(15,5);
declare variable ufcli char(2);
declare variable codtrattrib char(2);
declare variable comisprod numeric(6,2);
declare variable perccomvend numeric(6,2);
declare variable codnat char(4);
declare variable codregra char(4);
begin

    --Verifica se deve buscar custos para venda .
    if(:CODVENDA is not null) then
    begin

        select
            coalesce(vd.vlrprodvenda,0), coalesce(vd.vlrdescvenda,0), coalesce(vd.vlricmsvenda,0),
            coalesce(vd.vlroutrasvenda,0), coalesce(vd.vlrcomisvenda,0), coalesce(vd.vlradicvenda,0),
            coalesce(vd.vlripivenda,0), coalesce(vd.vlrpisvenda,0), coalesce(vd.vlrcofinsvenda,0),
            coalesce(vd.vlrirvenda,0), coalesce(vd.vlrcsocialvenda,0),
            coalesce(fr.vlrfretevd,0), fr.tipofretevd, fr.adicfretevd,
            
            sum(icv.vlrcustopeps * iv.qtditvenda),
            sum(icv.vlrcustompm * iv.qtditvenda),
            sum(icv.vlrprecoultcp * iv.qtditvenda)
            
            from
            vdvenda vd left outer join vdfretevd fr on
            fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda,
            
            vditvenda iv left outer join vditcustovenda icv on
            icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda
            and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda
            
            where vd.codemp=:CODEMPVD and vd.codfilial=:CODFILIALVD and vd.codvenda=:CODVENDA and vd.tipovenda=:TIPOVENDA and
            iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda
            
            group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14

            into vlrprod,vlrdesc,vlricms,vlroutras,vlrcomis,vlradic,vlripi,vlrpis,vlrcofins,vlrir,vlrcsocial,
                 vlrfrete,tipofrete,adicfrete,vlrcustopeps,vlrcustompm,vlrprecoultcp;

            suspend;

    end
    else
    begin
        --Buscando informações sobre o produto do item de orçamento
        select io.codemppd,io.codfilialpd,io.codprod,pd.comisprod,
        coalesce(io.vlrproditorc,0),coalesce(io.vlrdescitorc,0),coalesce(io.vlrliqitorc,0),
        ico.vlrcustopeps * io.qtditorc, ico.vlrcustompm * io.qtditorc, ico.vlrprecoultcp * io.qtditorc,
        cf.codregra
        from lfclfiscal cf, eqproduto pd, vditorcamento io left outer join vditcustoorc ico on
        ico.codemp=io.codemp and ico.codfilial=io.codfilial and ico.codorc = io.codorc and
        ico.tipoorc=io.tipoorc and ico.coditorc=io.coditorc
        where io.codemp=:CODEMPOC and io.codfilial=:CODFILIALOC and io.codorc=:CODORC and io.tipoorc=:TIPOORC and io.coditorc=:CODITORC and
        pd.codemp=io.codemppd and pd.codfilial=io.codfilial and pd.codprod=io.codprod and
        cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc
        into :CODEMPPD,:CODFILIALPD,:CODPROD,:VLRPROD,:VLRDESC,:VLRLIQ,:COMISPROD,:VLRCUSTOPEPS,:VLRCUSTOMPM,:VLRPRECOULTCP,:CODREGRA;

        -- Buscanco informações do orçamento,cliente,vendedor
        select oc.codempcl,oc.codfilialcl,oc.codcli,coalesce(cl.siglauf,cl.ufcli),vd.perccomvend,
        oc.tipofrete,oc.adicfrete
        from vdorcamento oc, vdcliente cl, vdvendedor vd
        where oc.codemp=:CODEMPOC and oc.codfilial=:CODFILIALOC and oc.tipoorc=:TIPOORC and oc.codorc=:CODORC and
        cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli and
        vd.codemp=oc.codempvd and vd.codfilial=oc.codfilialvd and vd.codvend=oc.codvend
        into :CODEMPCL,:CODFILIALCL,:CODCLI,:UFCLI,:PERCCOMVEND,:TIPOFRETE,:ADICFRETE;

        --Buscando o tipo de movimento a ser utilizado na venda futura
        select p1.codempt2,p1.codfilialt2,p1.codtipomov2 from sgprefere1 p1
        where p1.codemp=:CODEMPOC and p1.codfilial=:CODFILIALPF
        into :CODEMPTM,:CODFILIALTM,:CODTIPOMOV;

        -- Buscando natureza de operação da venda futura
        select first 1 nt.codnat from lfnatoper nt, lfitregrafiscal irf
        where nt.codemp=irf.codemp and nt.codfilial=irf.codfilial and nt.codnat=irf.codnat
        and (irf.codtipomov=:CODTIPOMOV or irf.codtipomov is null)
        and (irf.codemp=:CODEMPTM or irf.codemp is null)
        and (irf.codfilial=:CODFILIALTM or irf.codfilial is null)
        and irf.codregra=:CODREGRA and irf.noufitrf='N' and irf.cvitrf='V'
        into :CODNAT;

         -- Buscando informações fiscais
        select codtrattrib,coalesce(aliqfisc,0),coalesce(aliqipifisc,0),coalesce(aliqpis,0),coalesce(aliqcofins,0),coalesce(aliqcsocial,0),
        coalesce(aliqir,0),coalesce(redfisc,0)
        from lfbuscafiscalsp(:CODEMPPD,:CODFILIALPD,:CODPROD,:CODEMPCL,:CODFILIALCL,:CODCLI,:CODEMPTM,:CODFILIALTM,
        :CODTIPOMOV,'VD',:codnat,null,null,null,null)
        into :CODTRATTRIB,:ALIQICMS,:ALIQIPI,:ALIQPIS,:ALIQCOFINS,:ALIQCSOCIAL,:ALIQIR,:REDBASE;

        -- Caso o ICMS não seja definido na classifificação, buscar da tabela de aliquotas.
        if(:ALIQICMS = 0 and :CODTRATTRIB in('00','51','20','70','10') ) then
        begin
            select coalesce(PERCICMS,0) from lfbuscaicmssp (:CODNAT,:UFCLI,:CODEMPOC,:CODFILIALPF)
            into :ALIQICMS;
        end

        -- Buscando custo do ítem

        if(:REDBASE >0) then
        begin
            BASE = :VLRLIQ - ((:VLRLIQ * :REDBASE) /100 );
        end

        BASE = :VLRLIQ;

        vlricms = :BASE * :ALIQICMS / 100;

--      vlroutras =
        vlrcomis = :VLRLIQ * ((:COMISPROD * :PERCCOMVEND) / 10000 );

--      vlradic =
        vlripi = :VLRLIQ * :ALIQIPI / 100;
        vlrpis = :VLRLIQ * :ALIQCOFINS / 100;
        vlrcofins = :VLRLIQ * :ALIQCOFINS / 100;
        vlrir = :VLRLIQ * :ALIQIR /100;
        vlrcsocial = :VLRLIQ * :ALIQCSOCIAL / 100;
--      vlrfrete =

    end

end
^

/* Alter (VDEVOLUVENDAS) */
ALTER PROCEDURE VDEVOLUVENDAS(ICODEMP SMALLINT,
ICODFILIAL SMALLINT,
DATAINI DATE,
DATAFIM DATE,
ICODTIPOCLI SMALLINT,
ICODCLI SMALLINT,
FILTRAVENDAS CHAR(1),
FATURADO CHAR(1),
FINANCEIRO CHAR(1),
EMITIDO CHAR(1))
 RETURNS(VALOR NUMERIC(15,5),
MES SMALLINT,
ANO SMALLINT)
 AS
declare variable valortmp numeric(15,5);
declare variable datatmp date;
declare variable mestmp smallint;
declare variable anotmp smallint;
declare variable mesant smallint;
declare variable anoant smallint;
declare variable valorant numeric(15,5);
declare variable enviou char(1);
begin
  /* Retorna os valores de vendas por período */
  VALOR = 0;
  VALORANT = 0;
  ENVIOU = 'N';

  FOR select sum(v.vlrliqvenda),v.dtemitvenda from vdvenda v, vdcliente c, eqtipomov tm
             where c.codcli=v.codcli and c.codemp=v.codempcl and c.codfilial=v.codfilialcl
             and v.dtemitvenda between :DATAINI and :DATAFIM and v.codemp = :ICODEMP
             and v.codfilial = :ICODFILIAL
             and tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov
             and ( (c.codtipocli=:ICODTIPOCLI) or (:ICODTIPOCLI is null) )
             and ( (v.codcli=:ICODCLI) or (:ICODCLI is null) )
             and ( (tm.tipomov in(select tm2.tipomov from eqtipomov tm2 where tm2.codemp=v.codemptm and tm2.codfilial=v.codfilialtm and tm2.somavdtipomov='S')) or (:FILTRAVENDAS='N') )

             and (TM.FISCALTIPOMOV=:faturado or :faturado='A')
             and (TM.SOMAVDTIPOMOV=:financeiro or :financeiro='A')
             and (( (V.STATUSVENDA IN ('V2','V3','P3') and :emitido='S') or :emitido='A' )
             or ( (V.STATUSVENDA NOT IN ('V2','V3','P3') and :emitido='N') or :emitido='A' ))

             group by v.dtemitvenda order by v.dtemitvenda
      into VALORTMP,DATATMP do
      BEGIN
        MESTMP = extract(MONTH from DATATMP);
        ANOTMP = extract(YEAR from DATATMP);
        VALOR=VALOR+VALORANT;
        MES=MESANT;
        ANO=ANOANT;
        if ((not MESANT = MESTMP or not ANOANT = ANOTMP) AND not MESANT is null) then
        begin
          suspend;
          ENVIOU = 'S';
          VALOR = 0;
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        end
        ELSE
        BEGIN
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        END
      END

     VALOR=VALOR+VALORANT;
     MES=MESANT;
     ANO=ANOANT;
     suspend;
end
^

/* Create Trigger... */
CREATE TRIGGER FNSALDOLANCATGAI FOR FNSALDOLANCA
ACTIVE AFTER INSERT POSITION 0 
AS
    declare variable fechacaixa char(1);
    declare variable fechacaixaauto char(1);
begin

    -- buscando preferências para fechamento de caixas

    select coalesce(p1.fechacaixa,'N'),  coalesce(p1.fechacaixaauto,'N') from sgprefere1 p1
    where p1.codemp=new.codemp and p1.codfilial=new.codfilial
    into :fechacaixa, :fechacaixaauto;

    -- carregando valores para saldo...
    if (new.SALDOSL is null) then
        new.SALDOSL = 0;
    if (new.PREVSL is null) then
        new.PREVSL = 0;

    -- Fechando todos os caixas anteriors...

    if('S' = :fechacaixa and 'S' = :fechacaixaauto) then
    begin
        update fnsaldolanca sl
        set sl.fechado='S'
        where coalesce(sl.fechado,'N')='N'
        and sl.codemp=new.codemp and sl.codfilial=new.codfilial
        and sl.codemppn=new.codemppn and sl.codfilialpn=new.codfilialpn
        and sl.codplan=new.codplan
        and sl.datasl < new.datasl;
    end
end
^

CREATE TRIGGER PPOPENTRADATGAD FOR PPOPENTRADA
ACTIVE AFTER DELETE POSITION 0 
AS
declare variable preco decimal;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemple integer;
declare variable codfilialle smallint;
declare variable codlote varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codempopm integer;
declare variable codfilialopm smallint;
declare variable codopm integer;
declare variable seqopm smallint;
declare variable qtddistiop decimal(15,5);

begin

    -- Buscando custo do produto acabado
    select sum(pd.custompmprod) from ppitop it, eqproduto pd where it.codemp=old.codemp and it.codfilial=it.codfilial and
    it.codop=old.codop and it.seqop=old.seqop and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod into :preco;

    -- Buscando informações da OP
    select codemppd, codfilialpd, codprod, codemple, codfilialle, codlote, codemptm, codfilialtm, codtipomov, codempax, codfilialax, codalmox,
    codempopm, codfilialopm, codopm, seqopm, qtddistiop
    from ppop where
    codemp=old.codemp and codfilial=old.codfilial and codop=old.codop and seqop=old.seqop
    into
    :codemppd, :codfilialpd, :codprod, :codemple, :codfilialle, :codlote, :codemptm, :codfilialtm, :codtipomov, :codempax, :codfilialax, :codalmox,
    :codempopm, codfilialopm, :codopm, :seqopm, :qtddistiop;

    execute procedure eqmovprodiudsp('D', :codemppd, :codfilialpd, :codprod,
        :codemple, :codfilialle, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, old.seqent,
        null, null, null,
        old.dtent, old.codop, 'N', old.qtdent,:preco,
        :codempax, :codfilialax, :codalmox );

   if (:codopm is not null) then
   begin
      execute procedure ppatudistopsp( :codempopm, :codfilialopm, :codopm, :seqopm, :qtddistiop, 0);
   end
end
^

CREATE TRIGGER PPOPENTRADATGAI FOR PPOPENTRADA
ACTIVE AFTER INSERT POSITION 0 
as
declare variable preco numeric;
declare variable qtdest numeric;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemple integer;
declare variable codfilialle smallint;
declare variable codlote varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codempopm integer;
declare variable codfilialopm smallint;
declare variable codopm integer;
declare variable seqopm smallint;
declare variable qtddistiop decimal(15,5);
declare variable seqest integer;
declare variable prodetapas char(1);

begin

    -- Buscando preferências de produção

    select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
    into :prodetapas;

    -- Buscando informações da OP
    select codemppd, codfilialpd, codprod, codemple, codfilialle, codlote, codemptm, codfilialtm, codtipomov, codempax, codfilialax, codalmox,
    codempopm, codfilialopm, codopm, seqopm, qtddistiop, seqest
    from ppop where codemp=new.codemp and codfilial=new.codfilial and codop=new.codop and seqop=new.seqop
    into
    :codemppd, :codfilialpd, :codprod, :codemple, :codfilialle, :codlote, :codemptm, :codfilialtm, :codtipomov, :codempax, :codfilialax, :codalmox,
    :codempopm, codfilialopm, :codopm, :seqopm, :qtddistiop, :seqest;

    select pe.qtdest from ppestrutura pe
    where pe.codemp=:codemppd and pe.codfilial=:codfilialpd and
    pe.codprod=:codprod and pe.seqest=:seqest into :qtdest;

    if (:codopm is not null) then
    begin
        execute procedure ppatudistopsp( :codempopm, :codfilialopm, :codopm, :seqopm, :qtddistiop, 0);
    end

    -- Se o processo de finalização for em etapas deve gerar movimentação de estoque vinculada ao item de entrada da O.P.
    if( :prodetapas = 'S' ) then
    begin

        execute procedure EQMOVPRODIUDSP('I', :CODEMPPD, :CODFILIALPD, :CODPROD,
        :CODEMPLE, :CODFILIALLE, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, new.seqent,
        null, null,  null, new.dtent, new.codop,
        'S',new.qtdent,cast(:preco as numeric(15,5)),
        :codempax, :codfilialax, :codalmox );

        -- Atualizando quantidade final produzida na O.P.
        update ppop op set
        op.qtdfinalprodop=( select sum(et.qtdent) from ppopentrada et
                            where et.codemp=new.codemp and et.codfilial=new.codfilial and et.codop=new.codop and et.seqop=new.seqop
                           )
        where op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop and op.seqop=new.seqop;

    end

    if (:CODOPM is not null) then EXECUTE PROCEDURE PPATUDISTOPSP(:CODEMPOPM, :CODFILIALOPM, :CODOPM, :SEQOPM, 0, :QTDDISTIOP);

end
^

CREATE TRIGGER PPOPENTRADATGBU FOR PPOPENTRADA
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

end
^


/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPITCOMPRATGAD;

SET TERM ^ ;

CREATE TRIGGER CPITCOMPRATGAD FOR CPITCOMPRA
ACTIVE AFTER DELETE POSITION 0 
as

declare variable ddtcompra date;
declare variable cflag char(1);
declare variable idoccompra integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;

begin

    -- Se não estifver em manutenção...
    if ( not ( (old.emmanut='S') and (old.emmanut is not null) ) ) then
    begin

        -- Atualizando cabeçalho da compra
        update cpcompra cp set
            cp.vlrdescitcompra = cp.vlrdescitcompra - old.vlrdescitcompra,
            cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra,
            cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra,
            cp.vlricmscompra = cp.vlricmscompra - old.vlricmsitcompra,
            cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra,
            cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra,
            cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra,
            cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra,
            cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra,
            cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra
        where codcompra=old.codcompra and codemp=old.codemp and codfilial=old.codfilial;

        -- Buscando informações do cabeçaho da compra
        select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov
        from cpcompra c
        where c.codcompra=old.codcompra and c.codemp=old.codemp and c.codfilial=old.codfilial
        into :ddtcompra, :cflag, :idoccompra, :icodemptm, :scodfilialtm, :icodtipomov;

        -- Atualizando movimentação de estoque
        execute procedure eqmovprodiudsp('D', old.codemppd, old.codfilialpd, old.codprod,
        old.codemple, old.codfilialle, old.codlote, :icodemptm, :scodfilialtm, :icodtipomov,
        null, null, null, old.codemp, old.codfilial, old.codcompra, old.coditcompra,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, old.codempnt,
        old.codfilialnt, old.codnat, :ddtcompra, :idoccompra, :cflag, old.qtditcompra, old.custoitcompra,
        old.codempax, old.codfilialax, old.codalmox);

    end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPITCOMPRATGAI;

SET TERM ^ ;

CREATE TRIGGER CPITCOMPRATGAI FOR CPITCOMPRA
ACTIVE AFTER INSERT POSITION 0 
as
    declare variable dtcompra date;
    declare variable flag char(1);
    declare variable doccompra integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;
    declare variable calctrib char(1);

begin

    -- Buscando informações do cabeçalho da compra
    select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov, calctrib
    from cpcompra c
    where c.codcompra = new.codcompra and c.codemp = new.codemp and c.codfilial = new.codfilial
    into :dtcompra, :flag, :doccompra, :codemptm, :codfilialtm, :codtipomov, :calctrib;

    -- Executando procedure para movimentação de produto
    execute procedure eqmovprodiudsp (
        'I', new.codemppd, new.codfilialpd, new.codprod, new.codemple, new.codfilialle, new.codlote,
        :codemptm, :codfilialtm, :codtipomov, null, null, null, new.codemp, new.codfilial, new.codcompra, new.coditcompra,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, new.codempnt, new.codfilialnt, new.codnat,
        :dtcompra, :doccompra, :flag, new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox
    );

    -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure cpitcompraseriesp('I', new.codemp, new.codfilial, new.codcompra, new.coditcompra, new.codemppd, new.codfilialpd, new.codprod, new.numserietmp, new.qtditcompra);

    -- Gerando tabela de informações fiscais adicionais (lfitcompra)
    if(calctrib='S') then
    begin
       -- Inserindo registros na tabela de informações fiscais complementares (LFITCOMPRA)
        execute procedure lfgeralfitcomprasp(new.codemp, new.codfilial, new.codcompra, new.coditcompra);
    end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPITCOMPRATGAU;

SET TERM ^ ;

CREATE TRIGGER CPITCOMPRATGAU FOR CPITCOMPRA
ACTIVE AFTER UPDATE POSITION 0 
as

declare variable ddtcompra date;
declare variable cflag char(1);
declare variable idoccompra integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable calctrib char(1);

begin
    -- Se não estiver em manutenção...
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin
        -- Atualizando cabeçalho da compra
        update cpcompra cp set
            cp.vlrdescitcompra = cp.vlrdescitcompra -old.vlrdescitcompra + new.vlrdescitcompra,
            cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra + new.vlrproditcompra,
            cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra + new.vlrbaseicmsitcompra,
            cp.vlricmscompra = cp.vlricmscompra -old.vlricmsitcompra + new.vlricmsitcompra,
            cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra + new.vlrisentasitcompra,
            cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra + new.vlroutrasitcompra,
            cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra + new.vlrbaseipiitcompra,
            cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra + new.vlripiitcompra,
            cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra + new.vlrliqitcompra,
            cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra + new.vlrfunruralitcompra
        where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial;

        -- Buscando informações da compra
        select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov, c.calctrib
        from cpcompra c
        where c.codcompra = new.codcompra and c.codemp=new.codemp and c.codfilial = new.codfilial
        into :ddtcompra, :cflag, :idoccompra, :icodemptm, :scodfilialtm, :icodtipomov, :calctrib;

        -- Atualizando movimentação de estoque
        execute procedure eqmovprodiudsp('U', new.codemppd, new.codfilialpd, new.codprod,
        new.codemple, new.codfilialle, new.codlote, :icodemptm, :scodfilialtm, :icodtipomov, null, null, null,
        new.codemp, new.codfilial, new.codcompra, new.coditcompra, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, new.codempnt, new.codfilialnt, new.codnat, :ddtcompra, :idoccompra, :cflag,
        new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox);

        -- Executa procedure para atualização de tabela de vinculo para numeros de serie
        execute procedure cpitcompraseriesp('U', old.codemp, old.codfilial, old.codcompra, old.coditcompra, old.codemppd, old.codfilialpd, old.codprod, new.numserietmp, new.qtditcompra);

        -- Gerando tabela de informações fiscais adicionais (lfitcompra)
        if(calctrib='S') then
        begin
           -- Inserindo registros na tabela de informações fiscais complementares (LFITCOMPRA)
            execute procedure lfgeralfitcomprasp(new.codemp, new.codfilial, new.codcompra, new.coditcompra);
        end


    end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQINVPRODTGAD;

SET TERM ^ ;

CREATE TRIGGER EQINVPRODTGAD FOR EQINVPROD
ACTIVE AFTER DELETE POSITION 0 
AS
begin
  /* Apos a exclusão do inventário */
  EXECUTE PROCEDURE EQMOVPRODIUDSP('D', old.CODEMPPD, old.CODFILIALPD,
     old.CODPROD, old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
     old.CODEMPTM, old.CODFILIALTM, old.CODTIPOMOV, old.CODEMP,
     old.CODFILIAL, old.CODINVPROD, null, null, null, null,null,
     null, null, null, null, null, null, null, null, null, null, null,
     null, null, null,null,
     null, old.DATAINVP, old.CODINVPROD, 'S', old.QTDINVP, old.PRECOINVP,
     old.CODEMPAX, old.CODFILIALAX, old.CODALMOX);
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQINVPRODTGAI;

SET TERM ^ ;

CREATE TRIGGER EQINVPRODTGAI FOR EQINVPROD
ACTIVE AFTER INSERT POSITION 0 
AS
BEGIN
  EXECUTE PROCEDURE EQMOVPRODIUDSP('I', new.CODEMPPD, new.CODFILIALPD,
     new.CODPROD, new.CODEMPLE, new.CODFILIALLE, new.CODLOTE,
     new.CODEMPTM, new.CODFILIALTM, new.CODTIPOMOV, new.CODEMP,
     new.CODFILIAL, new.CODINVPROD,  null, null, null, null,
     null, null, null, null, null, null, null, null, null,null,
     null,null,null,null,
     null, null, null, new.DATAINVP, new.CODINVPROD, 'S',
     new.QTDINVP, new.PRECOINVP,
     new.CODEMPAX, new.CODFILIALAX, new.CODALMOX);
END
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQINVPRODTGAU;

SET TERM ^ ;

CREATE TRIGGER EQINVPRODTGAU FOR EQINVPROD
ACTIVE AFTER UPDATE POSITION 0 
AS
BEGIN
  EXECUTE PROCEDURE EQMOVPRODIUDSP('U', new.CODEMPPD, new.CODFILIALPD,
     new.CODPROD, new.CODEMPLE, new.CODFILIALLE, new.CODLOTE,
     new.CODEMPTM, new.CODFILIALTM, new.CODTIPOMOV, new.CODEMP,
     new.CODFILIAL, new.CODINVPROD,  null, null, null, null,
     null, null, null, null, null, null, null, null, null,null,null,null,
     null, null, null, null, null, new.DATAINVP, new.CODINVPROD, 'S',
     new.QTDINVP, new.PRECOINVP,
     new.CODEMPAX, new.CODFILIALAX, new.CODALMOX);
END
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQITRMATGAD;

SET TERM ^ ;

CREATE TRIGGER EQITRMATGAD FOR EQITRMA
ACTIVE AFTER DELETE POSITION 0 
AS
  DECLARE VARIABLE DDTRMA DATE;
  DECLARE VARIABLE ICODEMPTM INT;
  DECLARE VARIABLE ICODFILIALTM SMALLINT;
  declare VARIABLE ICODTIPOMOV INT ;

  begin
      SELECT R.DTAREQRMA,R.codemptm,R.codfilialtm,R.codtipomov
        FROM EQRMA R
        WHERE R.CODRMA = OLD.CODRMA AND R.CODEMP=OLD.CODEMP AND R.CODFILIAL = OLD.CODFILIAL
        INTO :DDTRMA,:ICODEMPTM,:ICODFILIALTM,:ICODTIPOMOV;

      EXECUTE PROCEDURE EQMOVPRODIUDSP('D',OLD.CODEMPPD, OLD.CODFILIALPD, OLD.CODPROD,
        OLD.CODEMPLE, OLD.CODFILIALLE, OLD.codlote, :ICODEMPTM,
        :ICODFILIALTM, :ICODTIPOMOV, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, OLD.CODEMP, OLD.CODFILIAL, OLD.codrma, OLD.coditrma, null, null, null,null,
        null,null,null,null, :DDTRMA, OLD.codrma, 'N', OLD.qtdexpitrma, OLD.precoitrma,
        OLD.CODEMPAX, OLD.CODFILIALAX, OLD.CODALMOX);
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQITRMATGAI;

SET TERM ^ ;

CREATE TRIGGER EQITRMATGAI FOR EQITRMA
ACTIVE AFTER INSERT POSITION 0 
AS
    declare variable DDTRMA date;
    declare variable ICODEMPTM int;
    declare variable ICODFILIALTM smallint;
    declare variable ICODTIPOMOV int;
    declare variable ICODOP int;
    declare variable QTD decimal(15,5);
    declare variable CBAIXARMAAPROV char(1);

    begin

        select r.dtareqrma,r.codemptm,r.codfilialtm,r.codtipomov,r.codop
            from eqrma r
                where r.codrma=new.codrma and r.codemp=new.codemp and r.codfilial=new.codfilial
        into :DDTRMA,:ICODEMPTM,:ICODFILIALTM,:ICODTIPOMOV,:ICODOP;

        select baixarmaaprov from sgprefere5
            where codemp=new.codemp and codfilial=new.codfilial
        into :CBAIXARMAAPROV;

        if(:CBAIXARMAAPROV='S' AND :ICODOP IS NOT NULL) THEN
        begin
            QTD = new.qtdaprovitrma;
        end
        else
            QTD = new.qtdexpitrma;

        execute procedure eqmovprodiudsp('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
            new.CODEMPLE, new.CODFILIALLE, new.codlote, :ICODEMPTM,:ICODFILIALTM,:ICODTIPOMOV,
            null, null, null ,null, null, null, null, null, null, null, null, null, new.CODEMP,
            new.CODFILIAL, new.codrma, new.coditrma, null, null, null, null,null,null,null,null,
            :DDTRMA, new.codrma, 'N', :QTD, new.precoitrma, new.CODEMPAX,
            new.CODFILIALAX, new.CODALMOX);
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQITRMATGAU;

SET TERM ^ ;

CREATE TRIGGER EQITRMATGAU FOR EQITRMA
ACTIVE AFTER UPDATE POSITION 0 
as
    declare variable icodemptm int;
    declare variable icodfilialtm smallint;
    declare variable icodtipomov int;
    declare variable ddtrma date;
    declare variable baixarmaaprov char(1);
    declare variable estoque char(1);
    declare variable qtdmov numeric(15,5);

begin
    -- Carregando preferências
    select baixarmaaprov from sgprefere5
    where codemp=new.codemp and codfilial=new.codfilial
    into :baixarmaaprov;

    qtdmov = new.qtdexpitrma;

    if(:baixarmaaprov='S' and new.sitaprovitrma in ('AT','AP')) then
    begin
        estoque = 'S';
        qtdmov = new.qtdaprovitrma;
    end
    else
    begin
        estoque = 'N';
    end


    -- Carregando informações do cabeçalho (RMA)
    select r.dtareqrma,r.codemptm,r.codfilialtm,r.codtipomov
    from eqrma r
    where r.codrma = new.codrma and r.codemp=new.codemp and r.codfilial = new.codfilial
    into :ddtrma,:icodemptm,:icodfilialtm,:icodtipomov;

    -- Movimentação de estoque
    execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
        new.codemple, new.codfilialle, new.codlote, :icodemptm,:icodfilialtm, :icodtipomov,
        null, null, null ,null, null,null, null, null, null, null, null, null,
        new.codemp, new.codfilial, new.codrma, new.coditrma, null, null, null, null,
        null, null, null, null, :ddtrma, new.codrma, :estoque, :qtdmov, new.precoitrma,
        new.codempax, new.codfilialax, new.codalmox );

    execute procedure sgdebugsp('Qtd:', :qtdmov);


end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER FNITPAGARTGBU;

SET TERM ^ ;

CREATE TRIGGER FNITPAGARTGBU FOR FNITPAGAR
ACTIVE BEFORE UPDATE POSITION 0 
AS
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE ICODFOR INTEGER;
  DECLARE VARIABLE ICODEMPFR INTEGER;
  DECLARE VARIABLE ICODFILIALFR INTEGER;
BEGIN

  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);

  IF ( new.EMMANUT IS NULL ) THEN
  BEGIN
      new.EMMANUT = 'N';
  END

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
     IF ((old.STATUSITPAG IN ('PP','PL') )  AND (new.STATUSITPAG='P1') ) THEN
     BEGIN
       DELETE FROM FNLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG
              AND CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALPAG;

       new.VLRPAGOITPAG = 0;
       new.DTPAGOITPAG = NULL;

     END
     ELSE IF ( (old.STATUSITPAG='P1') AND ( new.STATUSITPAG='CP' ) ) THEN
     BEGIN
        IF ( (new.OBSITPAG IS NULL) OR (rtrim(new.OBSITPAG)='') ) THEN
        BEGIN
           EXCEPTION FNITPAGAREX01;
        END
        new.VLRCANCITPAG = new.VLRAPAGITPAG;
        new.VLRPARCITPAG = 0;
        new.VLRDESCITPAG = 0;
        new.VLRJUROSITPAG = 0;
        new.VLRDEVITPAG = 0;
        new.VLRITPAG = 0;
     END

     new.VLRITPAG = new.VLRPARCITPAG - new.VLRDESCITPAG - new.VLRDEVITPAG + new.VLRJUROSITPAG +
     new.VLRMULTAITPAG + new.VLRADICITPAG;
     new.VLRAPAGITPAG = new.VLRITPAG - new.VLRPAGOITPAG;
  
     if (new.VLRAPAGITPAG < 0) then /* se o valor a pagar for menor que zero */
       new.VLRAPAGITPAG = 0;  /* então valor a pagar será zero */
     if ( (new.VLRAPAGITPAG=0) AND (new.VLRITPAG>0) ) then /* se o valor a pagar for igual a zero e existir valor na parcela*/
       new.STATUSITPAG = 'PP';  /* então o status será PP(pagamento completo) */
     else if ( (new.VLRPAGOITPAG>0) AND (new.VLRITPAG>0) ) then /* caso contrário e o valor pago maior que zero e existir valor na parcela*/
       new.STATUSITPAG = 'PL'; /*  então o status será PL(pagamento parcial) */

     /* faz o lançamento */
     SELECT CODFOR,CODEMPFR,CODFILIALFR FROM FNPAGAR WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL AND CODPAG=new.CODPAG
        INTO ICODFOR,ICODEMPFR,ICODFILIALFR;

     IF ((old.STATUSITPAG='P1' AND new.STATUSITPAG in ('PP','PL')) OR (old.STATUSITPAG in ('PP','PL') AND new.STATUSITPAG in ('PP','PL') AND new.VLRPAGOITPAG > 0)) THEN
     BEGIN

       EXECUTE PROCEDURE FNADICLANCASP02(new.CodPag,new.NParcPag,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODFOR,:ICODEMPFR,:ICODFILIALFR,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.AnoCC,new.CodCC,new.CODEMPCC,new.CODFILIALCC,
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag);

       /* Altera o valor pago e o valor a pagar */
       new.VLRPAGOITPAG = new.VLRPAGOITPAG + old.VLRPAGOITPAG;
       new.VLRAPAGITPAG = new.VLRITPAG - new.VLRPAGOITPAG;

       if (new.VLRAPAGITPAG < 0) then /* se o valor a pagar for menor que zero */
         new.VLRAPAGITPAG = 0;  /* então valor a pagar será zero */

       if (new.VLRAPAGITPAG=0) then /* se o valor a pagar for igual a zero */
         new.STATUSITPAG = 'PP';  /* então o status será PP(pagamento completo) */
       else if (new.VLRPAGOITPAG>0) then /* caso contrário e o valor pago maior que zero */
         new.STATUSITPAG = 'PL'; /*  então o status será PL(pagamento parcial) */

     END
     ELSE IF ((old.STATUSITPAG='PP') AND (new.STATUSITPAG='PP')) THEN
     BEGIN
        EXCEPTION FNPAGAREX01;
     END

   END

END
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER FNSALDOLANCATGBI;

SET TERM ^ ;

CREATE TRIGGER FNSALDOLANCATGBI FOR FNSALDOLANCA
ACTIVE BEFORE INSERT POSITION 0 
AS
begin

    -- carregando valores para saldo...
    if (new.SALDOSL is null) then
        new.SALDOSL = 0;
    if (new.PREVSL is null) then
        new.PREVSL = 0;

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER FNSALDOLANCATGBU;

SET TERM ^ ;

CREATE TRIGGER FNSALDOLANCATGBU FOR FNSALDOLANCA
ACTIVE BEFORE UPDATE POSITION 0 
as
    declare variable fechacaixa char(1);
 --   declare variable fechacaixaauto char(1);
    declare variable fechado char(1);
    declare variable numconta char(10);

begin

    -- buscando preferências para fechamento de caixas

    select coalesce(p1.fechacaixa,'N') from sgprefere1 p1
    where p1.codemp=new.codemp and p1.codfilial=new.codfilial
    into :fechacaixa;

    -- Verifica se a conta é de caixa, pois deve bloquear apenas contas caixa...
    select ct.numconta from fnconta ct
    where ct.codemppn=new.codemppn and ct.codfilialpn=new.codfilialpn
    and ct.codplan=new.codplan
    into :numconta;

    -- Verifica se deve verificar o fechamento de caixas...
    if('S' = :fechacaixa and :numconta is not null) then
    begin

        if(new.fechado is null) then
        begin
           new.fechado = 'N';
        end

        -- Verifica se o caixa está fechado antes de permitir a atualização

        if( new.fechado='N' ) then
        begin
            --Verifica se os caixas futuros estão fechados.

            select first 1 sl.fechado from fnsaldolanca sl
            where
            sl.codemp=new.codemp and sl.codfilial=new.codfilial
            and sl.codemppn=new.codemppn and sl.codfilialpn=new.codfilialpn and sl.codplan=new.codplan
            and coalesce(sl.fechado,'N')='S' and sl.datasl>new.datasl
            into :fechado;

            if( (new.fechado='S' or :fechado='S' ) and old.saldosl<>new.saldosl) then
            begin
                exception FNSALDOLANCAEX01;
            end
        end
        else if(old.saldosl<>new.saldosl) then
        begin
            exception FNSALDOLANCAEX01;
        end
    end

    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER PPOPTGAD;

SET TERM ^ ;

CREATE TRIGGER PPOPTGAD FOR PPOP
ACTIVE AFTER DELETE POSITION 0 
AS
declare variable preco decimal;
begin
  select sum(pd.custompmprod) from ppitop it, eqproduto pd where it.codemp=old.codemp and it.codfilial=it.codfilial and
    it.codop=old.codop and it.seqop=old.seqop and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod into :preco;

   EXECUTE PROCEDURE EQMOVPRODIUDSP('D',old.CODEMPPD, old.CODFILIALPD, old.CODPROD,
        old.CODEMPLE, old.CODFILIALLE, old.codlote, old.codemptm,
        old.codfilialtm, old.codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, null,
        null, null, null,
        old.dtfabrop, old.codop, 'N', old.qtdfinalprodop,:preco,
        old.codempax, old.codfilialax, old.codalmox );

   if (old.CODOPM is not null) then
      EXECUTE PROCEDURE PPATUDISTOPSP(old.CODEMPOPM, old.CODFILIALOPM, old.CODOPM,
        old.SEQOPM, old.QTDDISTIOP, 0);

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER PPOPTGAI;

SET TERM ^ ;

CREATE TRIGGER PPOPTGAI FOR PPOP
ACTIVE AFTER INSERT POSITION 0 
as
declare variable preco numeric;
declare variable qtdest numeric;
declare variable prodetapas char(1);
begin

   EXECUTE PROCEDURE PPITOPSP01(new.CODEMP, new.CODFILIAL, new.CODOP, new.SEQOP);

   EXECUTE PROCEDURE ppgeraopcq (new.CODEMP, new.CODFILIAL, new.CODOP, new.SEQOP);

   select sum(pd.custompmprod) from ppitop it, eqproduto pd where it.codemp=new.codemp and
   it.codfilial=it.codfilial and it.codop=new.codop and it.seqop=new.seqop and
   pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and
   pd.codprod=it.codprod into :preco;

   select pe.qtdest from ppestrutura pe
      where pe.codemp=new.codemppd and pe.codfilial=new.codfilialpd and
      pe.codprod=new.codprod and pe.seqest=new.seqest into :qtdest;

   -- Buscando preferências de produção

   select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
   into :prodetapas;

   -- Se o processo de finalização não for em etapas deve gerar movimentação de estoque vinculada diretamente a O.P.
   if( :prodetapas = 'N' ) then
   begin

       EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
            new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
            new.codfilialtm, new.codtipomov, null, null, null ,null, null,
            null, null, null, null, null, null,
            null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, null,
            null, null,  null, new.dtfabrop, new.codop,
            'N',new.qtdfinalprodop,cast(:preco as numeric(15,5)),
            new.codempax, new.codfilialax, new.codalmox );

       if (new.CODOPM is not null) then
          EXECUTE PROCEDURE PPATUDISTOPSP(new.CODEMPOPM, new.CODFILIALOPM, new.CODOPM,
            new.SEQOPM, 0, new.QTDDISTIOP);

   end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER PPOPTGAU;

SET TERM ^ ;

CREATE TRIGGER PPOPTGAU FOR PPOP
ACTIVE AFTER UPDATE POSITION 0 
as
declare variable preco decimal(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
declare variable codorc integer;
declare variable coditorc smallint;
declare variable prodetapas char(1);

begin

    -- Buscando preferências de produção

    select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
    into :prodetapas;

    /*Cancelamento de O.P */
    
    if(old.sitop!='CA' and new.sitop = 'CA') then
    begin

        /* Cancelamento de movimentação de estoque */

        -- Se o processo de finalização não for em etapas deve gerar movimentação de estoque vinculada diretamente a O.P.
        if( :prodetapas = 'N' ) then
        begin
            execute procedure eqmovprodiudsp('D',new.codemppd, new.codfilialpd, new.codprod,
                new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                null, null, null, null, null, null,null, null, null, null, null,
                new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                new.dtfabrop, new.codop, null, new.qtdfinalprodop,:preco,
                new.codempax, new.codfilialax, new.codalmox );
        end
        else
        begin

            delete from ppopentrada et where et.codemp=new.codemp and et.codfilial=new.codfilial and et.codop=new.codop and et.seqop=new.seqop;

        end

        -- Desfazendo vinculos com ítem de orçamento
        delete from ppopitorc oi where oi.codemp=new.codemp and oi.codfilial=new.codfilial
        and oi.codop=new.codop and oi.seqop=new.seqop;

        -- Cancelando as RMAs vinculadas
        update eqrma rma set rma.sitrma='CA', rma.motivocancrma='Ordem de produção original cancelada!'
        where rma.codempof=new.codemp and rma.codfilialof=new.codfilial and rma.codop=new.codop and rma.seqop=new.seqop;

    end
    else if (old.sitop!='FN' and new.sitop='FN') then
    begin
        -- Atualizando status do ítem de orçamento na finalização da OP
        for select oi.codempoc,oi.codfilialoc, oi.tipoorc, oi.codorc, oi.coditorc
        from ppopitorc oi
        where oi.codemp=new.codemp and oi.codfilial=new.codfilial and oi.codop=new.codop and oi.seqop=new.seqop
        into :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc do
        begin
            update vditorcamento io set io.sitproditorc = 'PD'
            where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.tipoorc=:tipoorc
            and io.codorc=:codorc and io.coditorc=:coditorc;
        end

        if( :prodetapas = 'N' ) then
        begin
            -- Buscando custo do produto acabado
            select sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)))
                from ppitop it, eqproduto pd
                where it.codemp=new.codemp and it.codfilial=it.codfilial
                and it.codop=new.codop and it.seqop=new.seqop
                and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
                and pd.codprod=it.codprod
            into :preco;

            execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                null, null, null, null, null, null,null, null, null, null, null,
                new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                new.dtfabrop, new.codop, 'N', new.qtdfinalprodop,:preco,
                new.codempax, new.codfilialax, new.codalmox );
        end
    end

    /* Outras ações */

    else
    begin
        if (old.qtdprevprodop <> new.qtdprevprodop ) then
        begin
            delete from ppitop
                where codemp=new.codemp AND codfilial=new.codfilial
                    and codop=new.codop and seqop=new.seqop;

            delete from PPOPFASE
                where codemp=new.codemp and codfilial=new.codfilial
                    and codop=new.codop and seqop=new.seqop;

            execute procedure ppitopsp01(new.codemp, new.codfilial, new.codop, new.seqop);
        end

        if( (old.qtdfinalprodop <> new.qtdfinalprodop) and (new.qtdfinalprodop>0) ) then
        begin

            if( :prodetapas = 'N' ) then
            begin

                -- Buscando custo do produto acabado
                select sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null))) from ppitop it, eqproduto pd
                where it.codemp=new.codemp and it.codfilial=it.codfilial
                and it.codop=new.codop and it.seqop=new.seqop
                and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
                and pd.codprod=it.codprod
                into :preco;

                execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                null, null, null, null, null, null,null, null, null, null, null,
                new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                new.dtfabrop, new.codop, 'N', new.qtdfinalprodop,:preco,
                new.codempax, new.codfilialax, new.codalmox );

            end

            execute procedure ppitopsp02(new.codemp, new.codfilial, new.codop, new.seqop);

        end
        if (new.CODOPM is not null) then
            execute procedure ppatudistopsp(new.codempopm, new.codfilialopm, new.codopm,
                new.seqopm, old.qtddistiop, new.qtddistiop);

    end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER PPOPTGBU;

SET TERM ^ ;

CREATE TRIGGER PPOPTGBU FOR PPOP
ACTIVE BEFORE UPDATE POSITION 0 
as
declare variable prodetapas char(1);
begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);
    SELECT REFPROD FROM EQPRODUTO WHERE CODEMP=new.CODEMP AND
    CODFILIAL=new.CODFILIAL AND CODPROD=new.CODPROD INTO new.REFPROD;

    -- Buscando preferências de produção
    select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
    into :prodetapas;

    if('S'=:prodetapas) then
    begin

        if( new.qtdfinalprodop>=new.qtdprevprodop ) then
        begin
            select coalesce(tm.codemptm,tm.codemp),coalesce(tm.codfilialtm,tm.codfilial),coalesce(tm.codtipomovtm,tm.codtipomov)
            from eqtipomov tm, ppop op
            where tm.codemp=op.codemptm and tm.codfilial=op.codfilialtm and tm.codtipomov=op.codtipomov
            and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop
            and op.seqop=new.seqop
            into new.codemptm,new.codfilialtm,new.codtipomov;
            new.sitop='FN';
        end

    end
    else
    begin
        if(new.qtdfinalprodop>0 and new.qtdfinalprodop<>old.qtdfinalprodop) then
        begin
            select coalesce(tm.codemptm,tm.codemp),coalesce(tm.codfilialtm,tm.codfilial),coalesce(tm.codtipomovtm,tm.codtipomov)
            from eqtipomov tm, ppop op
            where tm.codemp=op.codemptm and tm.codfilial=op.codfilialtm and tm.codtipomov=op.codtipomov
            and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop
            and op.seqop=new.seqop
            into new.codemptm,new.codfilialtm,new.codtipomov;
            new.sitop='FN';
        end
     end


     if(new.sitop='CA' and old.sitop<>'CA') then
        begin
            new.idusucanc=USER;
            new.dtcanc=cast('now' AS DATE);
            new.hcanc = cast('now' AS TIME);
        end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDITVENDATGAD;

SET TERM ^ ;

CREATE TRIGGER VDITVENDATGAD FOR VDITVENDA
ACTIVE AFTER DELETE POSITION 0 
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
BEGIN
  IF ( not ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
  BEGIN
      UPDATE VDVENDA SET
         VLRPRODVENDA = VLRPRODVENDA - old.VLRPRODITVENDA,
         VLRBASEICMSVENDA = VLRBASEICMSVENDA - old.VLRBASEICMSITVENDA,
         VLRICMSVENDA = VLRICMSVENDA -old.VLRICMSITVENDA,
         VLRISENTASVENDA = VLRISENTASVENDA - old.VLRISENTASITVENDA,
         VLROUTRASVENDA = VLROUTRASVENDA - old.VLROUTRASITVENDA,
         VLRBASEIPIVENDA = VLRBASEIPIVENDA - old.VLRBASEIPIITVENDA,
         VLRIPIVENDA = VLRIPIVENDA - old.VLRIPIITVENDA,
         VLRLIQVENDA = VLRLIQVENDA - old.VLRLIQITVENDA,
         VLRCOMISVENDA = VLRCOMISVENDA - old.VLRCOMISITVENDA,
         VLRBASEISSVENDA = VLRBASEISSVENDA - old.VLRBASEISSITVENDA,
         VLRISSVENDA = VLRISSVENDA - old.VLRISSITVENDA,
         VLRDESCITVENDA = VLRDESCITVENDA - old.VLRDESCITVENDA,
         VLRBASEICMSSTVENDA = VLRBASEICMSSTVENDA - OLD.vlrbaseicmsstitvenda,
         VLRICMSSTVENDA = VLRICMSSTVENDA - OLD.vlricmsstitvenda,
         VLRBASECOMIS = VLRBASECOMIS - OLD.vlrbasecomisitvenda
         WHERE CODVENDA=old.CODVENDA AND TIPOVENDA=old.TIPOVENDA AND
         CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;
      SELECT V.DTEMITVENDA, V.FLAG, V.DOCVENDA,
          V.CODEMPTM, V.CODFILIALTM, V.CODTIPOMOV
       FROM VDVENDA V  WHERE V.CODVENDA = old.CODVENDA AND
          V.CODEMP=old.CODEMP AND V.CODFILIAL = old.CODFILIAL AND
          V.TIPOVENDA=old.TIPOVENDA
      INTO :DDTVENDA, :CFLAG, :IDOCVENDA, :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV;
      EXECUTE PROCEDURE EQMOVPRODIUDSP('D', old.CODEMPPD, old.CODFILIALPD,
         old.CODPROD, old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
         :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, null, null, null,
         null, null, null, null,
         old.CODEMP, old.CODFILIAL, old.TIPOVENDA, old.CODVENDA, old.CODITVENDA,
          null, null, null, null,null,null, null, null, null,
         old.CODEMPNT, old.CODFILIALNT, old.CODNAT, :DDTVENDA,
         :IDOCVENDA, :CFLAG, old.QTDITVENDA, old.PRECOITVENDA,
         old.CODEMPAX, old.CODFILIALAX, old.CODALMOX);
  END
END
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDITVENDATGAI;

SET TERM ^ ;

CREATE TRIGGER VDITVENDATGAI FOR VDITVENDA
ACTIVE AFTER INSERT POSITION 0 
as
    declare variable dtvenda date ;
    declare variable flag char(1);
    declare variable docvenda integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;
    declare variable preco numeric(15, 5);
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);

begin
    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    -- Carregamento de informações do cabeçalho da venda
    select vd.dtemitvenda, vd.flag, vd.docvenda, vd.codemptm, vd.codfilialtm, vd.codtipomov
        from eqtipomov tm, vdvenda vd
        where
            tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.tipovenda=new.tipovenda and vd.codvenda=new.codvenda
    into :dtvenda, :flag, :docvenda, :codemptm, :codfilialtm, :codtipomov;

    -- Inicializando preco do item
    if (new.qtditvenda != 0 ) then
        preco = new.vlrliqitvenda/new.qtditvenda;
    else
        preco = new.precoitvenda;

    -- Executando movimentação do estoque
    execute procedure eqmovprodiudsp(
        'I', new.codemppd, new.codfilialpd, new.codprod,new.codemple, new.codfilialle, new.codlote, :codemptm, :codfilialtm,
        :codtipomov, null, null, null, null, null, null, null, new.codemp, new.codfilial, new.tipovenda, new.codvenda, new.coditvenda,
        null, null, null, null,null,null,null, null, null, new.codempnt, new.codfilialnt, new.codnat,:dtvenda, :docvenda, :flag, new.qtditvenda, :preco,
        new.codempax, new.codfilialax, new.codalmox
    );

    -- Salvamento de custos no momento da venda
    if( visualizalucr = 'S' ) then
    begin
        -- Busca do custo da ultima compra;
        select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
            new.dtins,'U',new.codempax, new.codfilialax, new.codalmox,'N')
        into :custouc;

        -- Busca do custo médio (MPM)
        select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
            new.dtins,'M',new.codempax, new.codfilialax, new.codalmox,'N')
        into :custompm;

        -- Busca do custo peps
        select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
            new.dtins,'P',new.codempax, new.codfilialax, new.codalmox,'N')
        into :custopeps;

        -- Inserindo registro na tabela de custos de item de venda
        insert into vditcustovenda (codemp,codfilial,codvenda,tipovenda,coditvenda,vlrprecoultcp, vlrcustompm, vlrcustopeps)
        values (new.codemp,new.codfilial,new.codvenda,new.tipovenda,new.coditvenda,:custouc,:custompm,:custopeps);

    end

     -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure vditvendaseriesp('I', new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda, new.codemppd, new.codfilialpd, new.codprod, new.numserietmp, new.qtditvenda);

    -- Inserindo registros na tabela de informações fiscais complementares (LFITVENDA)

    execute procedure lfgeralfitvendasp(new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda);

    -- Inserindo registro na tabela de vinculo entre compras e vendas (devolução)

    if(new.codcompra is not null and new.coditcompra is not null) then
    begin
        insert into cpdevolucao
            (codemp, codfilial, codcompra, coditcompra, qtddev,
             codempvd, codfilialvd, codvenda, tipovenda, coditvenda )
        values
            (new.codempcp, new.codfilialcp, new.codcompra, new.coditcompra,new.qtditvenda,
             new.codemp, new.codfilial,new.codvenda, new.tipovenda, new.coditvenda);
    end

    execute procedure cpgeradevolucaosp (
    new.codempcp,new.codfilialcp, new.codcompra, new.coditcompra, new.codemp, new.codfilial,
    new.codvenda, new.coditvenda, new.tipovenda, new.qtditvenda);

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDITVENDATGAU;

SET TERM ^ ;

CREATE TRIGGER VDITVENDATGAU FOR VDITVENDA
ACTIVE AFTER UPDATE POSITION 0 
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
  DECLARE VARIABLE CSTATUS CHAR(2);
  DECLARE VARIABLE CTIPOPROD CHAR(1);
  DECLARE VARIABLE NPRECO NUMERIC(15, 5);
  DECLARE VARIABLE visualizalucr CHAR(1);
  DECLARE VARIABLE custopeps NUMERIC(15, 5);
  DECLARE VARIABLE custompm NUMERIC(15, 5);
  DECLARE VARIABLE custouc NUMERIC(15, 5);

BEGIN
  
  select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) ) THEN
  BEGIN
      SELECT VD.DTEMITVENDA,VD.DOCVENDA,VD.STATUSVENDA,VD.FLAG,
             VD.CODEMPTM, VD.CODFILIALTM, VD.CODTIPOMOV
        FROM VDVENDA VD
        WHERE VD.CODVENDA = new.CODVENDA AND VD.TIPOVENDA = new.TIPOVENDA
            AND VD.CODEMP=new.CODEMP AND VD.CODFILIAL = new.CODFILIAL
        INTO :DDTVENDA, :IDOCVENDA, :CSTATUS, :CFLAG, :ICODEMPTM,
          :SCODFILIALTM, :ICODTIPOMOV;
      SELECT TIPOPROD FROM EQPRODUTO WHERE CODPROD=old.CODPROD
             AND CODEMP=old.CODEMPPD AND CODFILIAL = old.CODFILIALPD
         INTO CTIPOPROD;
      if (substr(:CSTATUS,1,1)!='C') then
      BEGIN
        UPDATE VDVENDA SET VLRDESCITVENDA = VLRDESCITVENDA -old.VLRDESCITVENDA + new.VLRDESCITVENDA,
               VLRPRODVENDA = VLRPRODVENDA - old.VLRPRODITVENDA + new.VLRPRODITVENDA,
               VLRBASEICMSVENDA = VLRBASEICMSVENDA - old.VLRBASEICMSITVENDA + new.VLRBASEICMSITVENDA,
               VLRICMSVENDA = VLRICMSVENDA -old.VLRICMSITVENDA + new.VLRICMSITVENDA,
               VLRISENTASVENDA = VLRISENTASVENDA - old.VLRISENTASITVENDA + new.VLRISENTASITVENDA,
               VLROUTRASVENDA = VLROUTRASVENDA - old.VLROUTRASITVENDA + new.VLROUTRASITVENDA,
               VLRBASEIPIVENDA = VLRBASEIPIVENDA - old.VLRBASEIPIITVENDA + new.VLRBASEIPIITVENDA,
               VLRIPIVENDA = VLRIPIVENDA - old.VLRIPIITVENDA + new.VLRIPIITVENDA,
               VLRLIQVENDA = VLRLIQVENDA - old.VLRLIQITVENDA + new.VLRLIQITVENDA,
               VLRCOMISVENDA = VLRCOMISVENDA - old.VLRCOMISITVENDA + new.VLRCOMISITVENDA,
               VLRBASEISSVENDA = VLRBASEISSVENDA - old.VLRBASEISSITVENDA + new.VLRBASEISSITVENDA,
               VLRISSVENDA = VLRISSVENDA - old.VLRISSITVENDA + new.VLRISSITVENDA,
               VLRBASEICMSSTVENDA = VLRBASEICMSSTVENDA - OLD.vlrbaseicmsstitvenda + NEW.vlrbaseicmsstitvenda,
               VLRICMSSTVENDA = VLRICMSSTVENDA - OLD.vlricmsstitvenda + NEW.vlricmsstitvenda,
               vlrbasecomis = vlrbasecomis - old.vlrbasecomisitvenda + new.vlrbasecomisitvenda
               WHERE CODVENDA=new.CODVENDA AND TIPOVENDA=new.TIPOVENDA
               AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
        
        IF (SUBSTR(CSTATUS,1,1) = 'P') THEN
          CSTATUS = 'PV';
        ELSE IF (SUBSTR(CSTATUS,1,1) = 'V') THEN
          CSTATUS = 'VD';
      END
      IF (new.QTDITVENDA = 0) THEN
         NPRECO = new.PRECOITVENDA;
      ELSE
         NPRECO = new.VLRLIQITVENDA/new.QTDITVENDA;
      EXECUTE PROCEDURE EQMOVPRODIUDSP('U',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
        new.CODEMPLE, new.CODFILIALLE, new.CODLOTE, :ICODEMPTM,
        :SCODFILIALTM, :ICODTIPOMOV, null, null, null ,null, null,
        null, null, new.CODEMP, new.CODFILIAL, new.TIPOVENDA, new.CODVENDA,
        new.CODITVENDA, null, null, null, null,null,null,null, null, null,
        new.CODEMPNT, new.CODFILIALNT,
        new.CODNAT, :DDTVENDA, :IDOCVENDA, :CFLAG, new.QTDITVENDA, :NPRECO,
        new.CODEMPAX, new.CODFILIALAX, new.CODALMOX);


       if( visualizalucr = 'S' ) then
       begin

            -- Busca do custo da ultima compra;
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'U',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custouc;

            -- Busca do custo médio (MPM)
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'M',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custompm;

            -- Busca do custo peps
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'P',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custopeps;

            -- Atualizando registro na tabela de custos de item de venda

            update vditcustovenda icv set vlrprecoultcp=:custouc, vlrcustompm=:custompm, vlrcustopeps=:custopeps
                where icv.codemp=new.codemp and icv.codfilial=new.codfilial and icv.codvenda=new.codvenda
                and icv.tipovenda=new.tipovenda and icv.coditvenda=new.coditvenda;

       end

        -- Executa procedure para atualização de tabela de vinculo para numeros de serie
        execute procedure vditvendaseriesp('U', old.codemp, old.codfilial, old.codvenda, old.tipovenda, old.coditvenda, old.codemppd, old.codfilialpd, old.codprod, new.numserietmp, new.qtditvenda);


        -- Atualizando registros na tabela de informações fiscais complementares (LFITVENDA)
        if(new.coditfisc is not null) then
        begin
            execute procedure lfgeralfitvendasp(
            new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda);
        end

        -- Atualizando informações referentes à devolução
        execute procedure cpgeradevolucaosp (
        new.codempcp,new.codfilialcp, new.codcompra, new.coditcompra, new.codemp, new.codfilial,
        new.codvenda, new.coditvenda, new.tipovenda, new.qtditvenda);


   END
END
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDITVENDATGBI;

SET TERM ^ ;

CREATE TRIGGER VDITVENDATGBI FOR VDITVENDA
ACTIVE BEFORE INSERT POSITION 0 
as
    declare variable srefprod char(13);
    declare variable stipoprod char(1);
    declare variable percicmsst numeric(9,2);
    declare variable precocomisprod numeric(15,5);
    declare variable redfisc numeric(9,2);
    declare variable redbasest char(1);
    declare variable ufcli char(2);

    begin

        -- Inicializando campos nulos.
        if (new.vlrdescitvenda is null) then new.vlrdescitvenda = 0;
        if (new.vlrbaseicmsitvenda is null) then new.vlrbaseicmsitvenda = 0;
        if (new.vlricmsitvenda is null) then new.vlricmsitvenda = 0;
        if (new.vlrbaseipiitvenda is null) then new.vlrbaseipiitvenda = 0;
        if (new.vlripiitvenda is null) then new.vlripiitvenda = 0;
        if (new.vlrliqitvenda is null) then new.vlrliqitvenda = 0;
        if (new.vlrcomisitvenda is null) then new.vlrcomisitvenda = 0;
        if (new.vlradicitvenda is null) then new.vlradicitvenda = 0;
        if (new.vlrissitvenda is null) then new.vlrissitvenda = 0;
        if (new.vlrfreteitvenda is null) then new.vlrfreteitvenda = 0;
        if (new.tipovenda is null) then new.tipovenda = 'V';
        if (new.vlrbaseicmsstitvenda is null) then new.vlrbaseicmsstitvenda = 0;
        if (new.vlricmsstitvenda is null) then new.vlricmsstitvenda = 0;
        if (new.vlrbasecomisitvenda is null) then new.vlrbasecomisitvenda = 0;

        -- Calculando valor liquido do ítem quando zerado.
        if (new.vlrliqitvenda = 0) then
            new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) +
                new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;

        -- Carregando almoxarifado padrão do produto
        if (new.codalmox is null) then
            select codempax, codfilialax, codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
            into new.codempax, new.codfilialax,new.codalmox;

        -- Acertando o codigo de empresa e filial da mensagem, caso a mensagem seja informada.
        if (not new.codmens is null) then
        begin
            select icodfilial from sgretfilial(new.codemp,'LFMENSAGEM') into new.codfilialme;
            new.codempme = new.codemp;
        end

        -- Buscando referência do produto
        select p.refprod, p.tipoprod, p.precocomisprod from eqproduto p
            where p.codemp=new.codemppd and p.codfilial = new.codfilialpd and p.codprod=new.codprod
        into srefprod, stipoprod, precocomisprod;

        -- Acertando referência quando nula
        if (new.refprod is null) then new.refprod = srefprod;

          
        -- Se o item vendido seja um SERVIÇO (Calculo do ISS);
        if (stipoprod = 'S') then
        begin
            -- Carregando aliquota do ISS
            select first 1 coalesce(f.percissfilial,c.aliqissfisc)
            from sgfilial f
            left outer join lfitclfiscal c on
            c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
            where codemp=new.codemp and codfilial=new.codfilial
            into new.percissitvenda;

            -- Calculando e computando base e valor do ISS;
            if (new.percissitvenda != 0) then
            begin
                new.vlrbaseissitvenda = new.vlrliqitvenda;
                new.vlrissitvenda = new.vlrbaseissitvenda*(new.percissitvenda/100);
            end
        end
        else -- Se o item vendido não for SERVIÇO zera ISS
            new.vlrbaseissitvenda = 0;

        -- Se produto for isento de ICMS
        if (new.tipofisc = 'II') then
        begin
            new.vlrisentasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlroutrasitvenda = 0;
        end
        -- Se produto for de Substituição Tributária
        else if (new.tipofisc = 'FF') then
        begin
            if (new.tipost = 'SI' ) then -- Contribuinte Substituído
            begin
                new.vlroutrasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
            else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
            begin

                -- Buscando estado do cliente

                select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                into ufcli;

                -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                select coalesce(ic.aliqfiscintra,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                into percicmsst, redfisc, redbasest ;
                -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                if (percicmsst = 0) then
                begin
                    select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                    into PERCICMSST;
                end

                if(redfisc>0 and redbasest='S') then
                begin
                    -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                    new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                end
                else
                begin
                    -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                    new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                end

                new.vlroutrasitvenda = 0;
                new.vlrisentasitvenda = 0;


                new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;

            end
        end
        -- Se produto não for tributado e não isento
        else if (new.tipofisc = 'NN') then
        begin
            new.vlroutrasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlrisentasitvenda = 0;
        end
        -- Se produto for tributado integralmente
        else if (new.tipofisc = 'TT') then
        begin
            new.vlroutrasitvenda = 0;
            new.vlrisentasitvenda = 0;
        end

        -- Gerando preço especial para comissionamento
        if(precocomisprod is not null) then
        begin

            new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

        end

        -- Atualização de totais no cabeçalho da venda

        update vdvenda vd set vd.vlrdescitvenda = vd.vlrdescitvenda + new.vlrdescitvenda,
            vd.vlrprodvenda = vd.vlrprodvenda + new.vlrproditvenda,
            vd.vlrbaseicmsvenda = vd.vlrbaseicmsvenda + new.vlrbaseicmsitvenda,
            vd.vlricmsvenda = vd.VLRICMSVENDA + new.vlricmsitvenda,
            vd.vlrisentasvenda = vd.VLRISENTASVENDA + new.vlrisentasitvenda,
            vd.vlroutrasvenda = vd.VLROUTRASVENDA + new.vlroutrasitvenda,
            vd.vlrbaseipivenda = vd.VLRBASEIPIVENDA + new.vlrbaseipiitvenda,
            vd.vlripivenda = vd.VLRIPIVENDA + new.vlripiitvenda,
            vd.vlrliqvenda = vd.VLRLIQVENDA + new.vlrliqitvenda,
            vd.vlrbaseissvenda = vd.VLRBASEISSVENDA + new.vlrbaseissitvenda,
            vd.vlrissvenda = vd.VLRISSVENDA + new.vlrissitvenda,
            vd.vlrcomisvenda = vd.VLRCOMISVENDA + new.vlrcomisitvenda,
            vd.vlrbaseicmsstvenda = coalesce(vd.vlrbaseicmsstvenda,0) + new.vlrbaseicmsstitvenda,
            vd.vlricmsstvenda = coalesce(vd.vlricmsstvenda,0) + new.vlricmsstitvenda,
            vd.vlrbasecomis = coalesce(vd.vlrbasecomis, 0) + new.vlrbasecomisitvenda
         where vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial;



end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDITVENDATGBU;

SET TERM ^ ;

CREATE TRIGGER VDITVENDATGBU FOR VDITVENDA
ACTIVE BEFORE UPDATE POSITION 0 
as
    declare variable srefprod char(13);
    declare variable stipoprod char(1);
    declare variable ntmp numeric(15, 5);
    declare variable precocomisprod numeric(15, 5);
    declare variable percicmsst numeric(9,2);
    declare variable ufcli char(2);
    declare variable redbasest char(1);
    declare variable redfisc numeric(9,2);

    begin
        -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
        if ( new.emmanut is null) then
            new.emmanut='N';

        if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
        begin

            -- Computando campos de log
            new.dtalt=cast('now' as date);
            new.idusualt=user;
            new.halt = cast('now' as time);

            -- Verifica se o produto foi alterado
            if (new.codprod != old.codprod) then
                exception vditvendaex01;

            -- Verifica se o lote foi alterado
            if (new.codlote != old.codlote) then
                exception vditvendaex02;

            -- Verifica se o código do almoxarifa está nuloo, se estiver carrega o almoxarifado padrão do produto
            if (new.codalmox is null) then
            begin
                select codempax,codfilialax,codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
                into new.codempax, new.codfilialax,new.codalmox;
            end

            -- Verifica se o almoxarifado anterior estava nulo, se não informa que não é pode ser trocado
            if (old.codalmox is not null) then
            begin
                if (old.codalmox != new.codalmox) then
                    exception eqalmox01;
            end

            -- Carrega a referencia e tipo do produto
            select refprod, tipoprod, precocomisprod
            from eqproduto where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
            into srefprod, stipoprod, precocomisprod;

            if (new.refprod is null) then new.refprod = srefprod;

            -- Caso a nota tenha sido cancelada
            if ( (new.cancitvenda is not null) and (new.cancitvenda = 'S') ) then
            begin
                new.qtditvenda = 0;
                new.vlrliqitvenda = 0;
                new.vlrproditvenda = 0;
                new.vlrbaseicmsitvenda = 0;
                new.vlrbaseipiitvenda = 0;
                new.vlrdescitvenda = 0;
            end

            -- Calculando o valor liquido o ítem
            if ( (new.vlrliqitvenda = 0) and ( (new.cancitvenda is null) or (new.cancitvenda!='S') ) ) then
            begin
                new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) + new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;
            end
            else if (new.vlrdescitvenda > 0 and new.qtditvenda > 0) then
            begin
                ntmp = new.vlrliqitvenda/new.qtditvenda;
                ntmp = ntmp * new.qtditvenda;
                new.vlrdescitvenda = new.vlrdescitvenda + (new.vlrliqitvenda-ntmp);

                -- Recalculando comissão sobre o ítem
                new.vlrcomisitvenda = (new.perccomisitvenda * new.vlrliqitvenda ) / 100;
            end

            -- Ser for um serviço
            if (stipoprod = 'S') then
            begin
                -- Calculo do ISS
                select first 1 coalesce(f.percissfilial,c.aliqissfisc)
                from sgfilial f
                left outer join lfitclfiscal c on
                c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
                where codemp=new.codemp and codfilial=new.codfilial
                into new.percissitvenda;

                if (new.percissitvenda != 0) then
                begin
                    new.vlrbaseissitvenda = new.vlrliqitvenda;
                    new.vlrissitvenda = new.vlrbaseissitvenda * (new.percissitvenda/100);
                end
            end
            else
                new.vlrbaseissitvenda = 0;

            -- ìtem isento
            if (new.tipofisc = 'II') then
            begin
                new.vlrisentasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlroutrasitvenda = 0;
            end
            -- Item com substituição tributária
            else if (new.tipofisc = 'FF') then
            begin
                if (new.tipost = 'SI' ) then -- Contribuinte Substituído
                begin
                    new.VLROUTRASITVENDA = new.VLRLIQITVENDA;
                    new.VLRBASEICMSITVENDA = 0;
                    new.PERCICMSITVENDA = 0;
                    new.VLRICMSITVENDA = 0;
                    new.VLRISENTASITVENDA = 0;
                end
                else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
                begin

                    -- Buscando estado do cliente
                    select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    into ufcli;

                   -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0), ic.redbasest, ic.redfisc from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into PERCICMSST, redbasest, redfisc;
                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                        into PERCICMSST;
                    end

                    new.vlroutrasitvenda = 0;
                    new.VLRISENTASITVENDA = 0;

                   if(redfisc>0 and redbasest='S') then
                    begin
                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                    end
                    else
                    begin
                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                    end

                    new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;

                end
            end
            -- Não insidencia
            else if (new.tipofisc = 'NN') then
            begin
                new.vlroutrasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
            -- Tributado integralmente
            else if (new.tipofisc = 'TT') then
            begin
                new.vlroutrasitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
        end


       -- Atualizando preço especial para comissionamento
       if(precocomisprod is not null) then
       begin

           new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

       end



    end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDVENDATGAU;

SET TERM ^ ;

CREATE TRIGGER VDVENDATGAU FOR VDVENDA
ACTIVE AFTER UPDATE POSITION 0 
as
    declare variable icodrec integer;
    declare variable scodfilialrc smallint;
    declare variable icoditvenda integer;
    declare variable percred numeric(15,5);
    declare variable percit numeric(15,5);
    declare variable percicmsitvenda numeric(15,5);
    declare variable percipiitvenda numeric(15,5);
    declare variable tipofisc char(2);
    declare variable vlrdescitvenda numeric(15, 5);
    declare variable vlrbaseipiitvenda numeric(15, 5);
    declare variable vlrbaseicmsitvenda numeric(15, 5);
    declare variable vlrbaseicmsfreteitvenda numeric(15, 5);
    declare variable vlripiitvenda numeric(15, 5);
    declare variable vlrproditvenda numeric(15, 5);
    declare variable vlrliqitvenda numeric(15, 5);
    declare variable vlricmsitvenda numeric(15, 5);
    declare variable vlricmsfreteitvenda numeric(15, 5);
    declare variable tipomov char(2);
    declare variable vlrmfintipomov char(1);
    declare variable vlrtmp numeric(15, 5);
    declare variable qtditvenda numeric(9,2);
    declare variable nvlrparcrec numeric(15, 5);
    declare variable nvlrcomirec numeric(15, 5);
    declare variable percitfrete numeric(15, 5);
    declare variable vlritfrete numeric(15, 5);
    declare variable snroparcrec smallint;
    declare variable codempif integer;
    declare variable codfilialif smallint;
    declare variable codfisc char(13);
    declare variable coditfisc integer;
    declare variable dtrec date;
    declare variable gerarecemis char(1);
    declare variable tpredicms char(1);
    declare variable redbasefrete char(1);
    declare variable vlrretensaoiss numeric(15, 5);

    begin

        if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
        begin

        -- buscando preferências
        select gerarecemis from sgprefere1 p1 where p1.codemp=new.codemp and p1.codfilial=new.codfilial
        into :gerarecemis;

        -- Se foi dado desconto ou alterado o valor do frete da venda

        if ((not new.vlrdescvenda = old.vlrdescvenda) or (not new.vlrfretevenda = old.vlrfretevenda) ) then
        begin

            -- distribuindo o desconto e frete csocial e ir:

            for select coditvenda,percicmsitvenda,vlrdescitvenda,
                vlrliqitvenda,vlrproditvenda,qtditvenda,codempif,codfilialif,codfisc,coditfisc
                from vditvenda
                where codemp=new.codemp and codfilial=new.codfilial and codvenda=new.codvenda and tipovenda=new.tipovenda
                into icoditvenda,percicmsitvenda,
                vlrdescitvenda,vlrliqitvenda,vlrproditvenda,qtditvenda,codempif,codfilialif,codfisc,coditfisc
            do
            begin

                -- distribuição do desconto
                percit = 0;
                if (new.vlrprodvenda > 0 and not new.vlrdescitvenda > 0 and new.vlrdescvenda > 0) then
                begin
                    percit = (100*vlrproditvenda) / new.vlrprodvenda;
                    vlrdescitvenda = (new.vlrdescvenda  * percit) / 100;
                end

                -- distribuição do frete
                if ( new.vlrfretevenda > 0 and ( not new.vlrfretevenda = old.vlrfretevenda) ) then
                begin
                    percitfrete = :vlrproditvenda / new.vlrprodvenda ;
                    vlritfrete =  :percitfrete * new.vlrfretevenda ;
                end

                -- busca informações fiscais.:
                select first 1 i.redfisc, i.aliqipifisc, i.tipofisc, i.tpredicmsfisc, i.redbasefrete
                from lfitclfiscal i
                where i.codemp=:codempif and i.codfilial=:codfilialif and i.codfisc=:codfisc and i.coditfisc=:coditfisc
                into percred, percipiitvenda, tipofisc, tpredicms, redbasefrete;

                if (percred is null) then
                    percred = 0;

                if (percipiitvenda is null) then
                    percipiitvenda = 0;

                if (percicmsitvenda is null) then
                    percicmsitvenda = 0;

                vlrliqitvenda = vlrproditvenda - vlrdescitvenda;
                vlrbaseipiitvenda = 0;
                vlrbaseicmsitvenda = 0;
                vlricmsitvenda = 0;
                vlripiitvenda = 0;

                if (qtditvenda > 0) then
                begin
                    vlrtmp = vlrliqitvenda/qtditvenda;
                    vlrdescitvenda = vlrproditvenda - (vlrtmp*qtditvenda);
                    vlrliqitvenda = vlrproditvenda - vlrdescitvenda;
                end

                if ( tipofisc = 'II' ) then -- Isento de ICMS
                begin
                    percicmsitvenda = 0;
                    vlricmsitvenda = 0;
                    vlrbaseicmsfreteitvenda = 0;
                    vlrbaseicmsitvenda = 0;
                    percipiitvenda = 0;
                    vlripiitvenda = 0;
                    vlrbaseipiitvenda = 0;
                end
                else if ( tipofisc = 'FF' ) then -- Substituição tributária do icms
                begin
                    vlrbaseicmsitvenda = vlrliqitvenda - (vlrproditvenda*(percred/100));
                    vlricmsitvenda = vlrbaseicmsitvenda*(percicmsitvenda/100);
                    vlrbaseipiitvenda = vlrliqitvenda;
                    vlripiitvenda = vlrbaseipiitvenda*(percipiitvenda/100);
                end
                else if ( tipofisc = 'NN') then -- Não insidência do icms
                begin
                    percicmsitvenda = 0;
                    vlricmsitvenda = 0;
                    vlrbaseicmsitvenda = 0;
                    vlrbaseicmsfreteitvenda = 0;
                    percipiitvenda = 0;
                    vlripiitvenda = 0;
                    vlrbaseipiitvenda = 0;
                end
                else if ( tipofisc = 'TT') then -- Tributado integralmente o icms
                begin

                    vlrbaseicmsitvenda = vlrliqitvenda;
                    vlrbaseicmsfreteitvenda = vlritfrete;

                    if(percred>0) then
                    begin
                        if(:tpredicms='B') then
                        begin
                            --Se deve reduzir a base do icms do frete...

                            if(:redbasefrete='S' and vlritfrete>0) then
                            begin
                                vlrbaseicmsfreteitvenda = vlritfrete - ( vlritfrete *(percred/100) );
                                vlricmsfreteitvenda = vlrbaseicmsfreteitvenda*(percicmsitvenda/100);
                            end
                            else
                            begin
                                vlrbaseicmsfreteitvenda = vlritfrete;
                            end

                            --vlrbaseicmsitvenda = vlrliqitvenda - (vlrproditvenda*(percred/100));
                            -- Revisao 12/07/2010 - Robson Sanchez
                            -- Foram separados os calculos de ICMS do frete e da venda.
                            -- Em virtude disso a formacao da base de calculo nao pode ser sobre o valor liquido,
                            -- pois o valor liquido pode conter valor adicional de frete, causando duplicacao de impostos.

                            vlrbaseicmsitvenda = (vlrproditvenda + vlripiitvenda - vlrdescitvenda)*(1-(percred/100));

                            vlricmsitvenda = vlrbaseicmsitvenda*(percicmsitvenda/100);

                        end
                        else if(:tpredicms='V') then
                        begin
                            vlricmsitvenda = ( vlrbaseicmsitvenda*(percicmsitvenda/100) );
                            vlricmsitvenda = vlricmsitvenda - ( vlricmsitvenda * (percred/100) );

                            vlricmsfreteitvenda = vlrbaseicmsfreteitvenda*(percicmsitvenda/100);
                            vlricmsfreteitvenda = vlricmsfreteitvenda - ( vlricmsfreteitvenda * (percred/100) );

                        end
                    end
                    else
                    begin
                        vlricmsitvenda = vlrbaseicmsitvenda*(percicmsitvenda/100);
                        vlricmsfreteitvenda = vlrbaseicmsfreteitvenda*(percicmsitvenda/100);
                    end

                    vlrbaseipiitvenda = vlrliqitvenda;
                    vlripiitvenda = vlrbaseipiitvenda*(percipiitvenda/100);

                end

                -- atualizando tabela de ítens
                update vditvenda set
                vlrbaseicmsitvenda = :vlrbaseicmsitvenda, vlrbaseipiitvenda = :vlrbaseipiitvenda,
                vlricmsitvenda = :vlricmsitvenda, vlripiitvenda = :vlripiitvenda,
                vlrdescitvenda = :vlrdescitvenda, vlrliqitvenda = :vlrliqitvenda,
                vlrfreteitvenda = :vlritfrete
                where
                codemp=new.codemp and codfilial=new.codfilial and codvenda=new.codvenda and
                coditvenda=:icoditvenda and tipovenda=new.tipovenda;

                -- Atualizando tabela de tributos referente ao frete
                if(new.vlrfretevenda != old.vlrfretevenda) then
                begin
                    update lfitvenda set
                    vlrbaseicmsfreteitvenda = :vlrbaseicmsfreteitvenda,
                    vlricmsfreteitvenda = :vlricmsfreteitvenda
                    where
                    codemp=new.codemp and codfilial=new.codfilial and codvenda=new.codvenda and
                    coditvenda=:icoditvenda and tipovenda=new.tipovenda;
                end

            end
        end


    -- Busca informações do tipo de movimento da venda
    select tipomov, vlrmfintipomov
    from eqtipomov
    where codtipomov=new.codtipomov and codemp=new.codemptm and codfilial=new.codfilialtm
    into tipomov, vlrmfintipomov;

    -- Busca informações do contas a receber da venda
    select codrec
    from fnreceber
    where codvenda=new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva=new.codfilial
    into icodrec;

    -- Verifica de
    if ((not tipomov in ('DV','TR')) and ((icodrec is null) or ((new.codplanopag != old.codplanopag) or
        (new.codcli != old.codcli) or (new.codvend != old.codvend) or (new.vlrliqvenda != old.vlrliqvenda) or
        (new.dtemitvenda != old.dtemitvenda) or (new.docvenda != old.docvenda) or (new.codbanco != old.codbanco)))) then

    begin

        if(gerarecemis = 'S') then
        begin
            dtrec = new.dtemitvenda;
        end
        else
        begin
            dtrec = new.dtsaidavenda;
        end


        -- Verica se deve haver retensão de ISS

        select sum(coalesce(iv.vlrissitvenda,0))
        from vditvenda iv left outer join lfitclfiscal cf on
        cf.codemp=iv.codempif and cf.codfilial=iv.codfilialif and cf.codfisc=iv.codfisc and cf.coditfisc=iv.coditfisc and cf.retensaoiss='S'
        where iv.codemp=new.codemp and iv.codfilial=new.codfilial and iv.codvenda=new.codvenda and iv.tipovenda=new.tipovenda
        and iv.vlrissitvenda>0
        into :vlrretensaoiss;

        if(:vlrretensaoiss is null) then
        begin
            vlrretensaoiss = 0;
        end

        -- De pedido para Venda
        if ((substr(old.statusvenda,1,1) = 'P') and (substr(new.statusvenda,1,1) = 'V' ) or
        ( (not new.vlrcomisvenda=old.vlrcomisvenda ) and (not new.statusvenda in ('P1','V1') ) ) ) then

        begin
           if (new.vlrliqvenda > 0) then
           begin

              execute procedure fnadicrecebersp01(
                   new.tipovenda, new.codvenda,
                   new.codemptc, new.codfilialtc, new.codtipocob,
                   new.codemppg,new.codfilialpg,new.codplanopag,
                   new.codempcl,new.codfilialcl,new.codcli,
                   new.codempvd,new.codfilialvd,new.codvend,
                   new.vlrliqvenda,dtrec,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           end
           else
           begin
               delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
        end
        -- De pedido ou venda aberto mudou para finalizado
        if ((new.statusvenda in ('P2','V2')) and (old.statusvenda in ('P1','V1'))) then
        begin
           if (new.vlrliqvenda > 0) then
               execute procedure fnadicrecebersp01(
                   new.tipovenda, new.codvenda,
                   new.codemptc, new.codfilialtc, new.codtipocob,
                   new.codemppg,new.codfilialpg,new.codplanopag,
                   new.codempcl,new.codfilialcl,new.codcli,
                   new.codempvd,new.codfilialvd,new.codvend,
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           else
           begin
               delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
        end
        -- De pedido fechado para venda fechada ou venda fechada para pedido fechado, ou sem alteração (em processo de fechamento)
        else if ((new.statusvenda in ('P2','V2')) and (old.statusvenda in ('P2','V2'))) then
        begin
           if (new.vlrliqvenda > 0) then
               execute procedure fnadicrecebersp01(
                   new.tipovenda, new.codvenda,
                   new.codemptc, new.codfilialtc, new.codtipocob,
                   new.codemppg,new.codfilialpg,new.codplanopag,
                   new.codempcl,new.codfilialcl,new.codcli,
                   new.codempvd,new.codfilialvd,new.codvend,
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           else
           begin
                delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
        end
        -- De pedido emitido para venda emitida ou venda emitida para pedido emitido, ou sem alteração
        else if ((new.statusvenda in ('P3','V3')) and (old.statusvenda in ('P3','V3'))) then
        begin
           if (new.vlrliqvenda > 0) then
               execute procedure fnadicrecebersp01(
                   new.tipovenda, new.codvenda,
                   new.codemptc, new.codfilialtc, new.codtipocob,
                   new.codemppg,new.codfilialpg,new.codplanopag,
                   new.codempcl,new.codfilialcl,new.codcli,
                   new.codempvd,new.codfilialvd,new.codvend,
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           else
           begin
               delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
        end
      end
      if (old.vlrcomisvenda != new.vlrcomisvenda) then
      begin
          update fnreceber set vlrcomirec=new.vlrcomisvenda
          where codvenda=new.codvenda and tipovenda=new.tipovenda and codempvd=new.codemp and
                codfilialvd=new.codfilial;
      end
      else if (old.codclcomis != new.codclcomis) then
      begin
          select r.codfilial,r.codrec,r.vlrparcrec,r.vlrcomirec,r.nroparcrec
              from fnreceber r
              where r.codvenda=new.codvenda and r.tipovenda=new.tipovenda
              and r.codempva=new.codemp and r.codfilialva=new.codfilial
              into :scodfilialrc, :icodrec, :nvlrparcrec, :nvlrcomirec, :snroparcrec;
          execute procedure fnitrecebersp01(new.codemp,:scodfilialrc,:icodrec,
                :nvlrparcrec,:nvlrcomirec,:snroparcrec,'S');
      end

      /**
        testa valor das parcelas x valor da venda
      **/
      if ((old.statusvenda in ('P2','V2')) and (new.statusvenda in ('P3','V3')) and (vlrmfintipomov<>'S') ) then
      begin
        select vlrparcrec from fnreceber
            where codvenda=new.codvenda and tipovenda=new.tipovenda
            and codempva=new.codemp and codfilialva = new.codfilial
            into :nvlrparcrec;

       if (new.vlrliqvenda-:vlrretensaoiss != :nvlrparcrec) then
        begin
            exception vdvendaex06;
        end

      end

      -- Caso a data ou o tipo de movimento tenham sido alterados, deve disparar o trigger da vditvenda
    if ( (new.dtsaidavenda != old.dtsaidavenda) or (new.codtipomov!=old.codtipomov) or ( new.docvenda != old.docvenda )  ) then
    begin
        -- Update necessário para disparar o trigger da tabela vditvenda
        update vditvenda set coditvenda=coditvenda
        where codvenda = old.codvenda and tipovenda = old.tipovenda and codemp=old.codemp and codfilial=old.codfilial;
    end
    else if ((substr(new.statusvenda,1,1)='C') and (substr(old.statusvenda,1,1) in ('P','V'))) then
    begin

        update vditvenda set qtditvendacanc=qtditvenda, qtditvenda=0 where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilial=new.codfilial;

        delete from fnreceber where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilialva=new.codfilial;

        delete from vdvendaorc where codemp=new.codemp and codfilial=new.codfilial and
        codvenda=new.codvenda and tipovenda=new.tipovenda;

      end
   end
end
^

/* Alter empty procedure EQMOVPRODDSP with new param-list */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODISP with new param-list */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODRETCODSP with new param-list */
ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODUSP with new param-list */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure LFBUSCAFISCALSP with new param-list */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca é para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de classificação fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do ítem de classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de classficiação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

/* Alter Procedure... */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f1.percissfilial, f.aliqissfisc)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

/* Alter (CPADICITCOMPRAPEDSP) */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod char(13);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Retorna o código do movimento de estoque */
  if (ICODINVPROD IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPIV=:ICODEMPIV AND MP.CODFILIALIV=:SCODFILIALIV
         AND MP.CODINVPROD=:ICODINVPROD
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODCOMPRA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPCP=:ICODEMPCP AND MP.CODFILIALCP=:SCODFILIALCP
         AND MP.CODCOMPRA=:ICODCOMPRA AND MP.CODITCOMPRA=:SCODITCOMPRA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODVENDA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPVD=:ICODEMPVD AND MP.CODFILIALVD=:SCODFILIALVD
         AND MP.TIPOVENDA=:CTIPOVENDA AND MP.CODVENDA=:ICODVENDA
         AND MP.CODITVENDA=:SCODITVENDA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODRMA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPRM=:ICODEMPRM AND MP.CODFILIALRM=:SCODFILIALRM
         AND MP.CODRMA=:ICODRMA AND MP.CODITRMA=:SCODITRMA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODOP IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
         AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqentop=:sseqopent
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;



  suspend;
end
^

/* Alter (EQMOVPRODDSP) */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

/* Alter (EQMOVPRODISP) */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable cestoqmovprod char(1);
declare variable ctipomovprod char(1);
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable soperador smallint;
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de inserção na tabela eqmovprod */

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX FROM EQMOVPRODSLDSP(null, null, null, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NPRECOMOVPROD, :NPRECOMOVPROD,
     :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX )
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

  /* Verifica se haverá mudança de saldo*/
  SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD, CTIPOMOVPROD, SOPERADOR FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX)
        INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
  end

  SELECT SCODFILIAL, ICODMOVPROD FROM EQMOVPRODSEQSP(:ICODEMPPD)
     INTO :SCODFILIAL, :ICODMOVPROD;  /* encontra o próximo código e a filial*/

   INSERT INTO EQMOVPROD ( CODEMP, CODFILIAL, CODMOVPROD,
      CODEMPPD, CODFILIALPD , CODPROD , CODEMPLE ,
      CODFILIALLE , CODLOTE , CODEMPTM, CODFILIALTM,
      CODTIPOMOV, CODEMPIV , CODFILIALIV , CODINVPROD ,
      CODEMPCP , CODFILIALCP , CODCOMPRA , CODITCOMPRA , CODEMPVD ,
      CODFILIALVD , TIPOVENDA , CODVENDA , CODITVENDA , CODEMPRM ,
      CODFILIALRM , CODRMA , CODITRMA ,
      CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENTOP,
      CODEMPNT , CODFILIALNT ,
      CODNAT , DTMOVPROD , DOCMOVPROD , FLAG , QTDMOVPROD ,
      PRECOMOVPROD, ESTOQMOVPROD, TIPOMOVPROD, SLDMOVPROD, CUSTOMPMMOVPROD,
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX )
   VALUES ( :ICODEMPPD, :SCODFILIAL, :ICODMOVPROD,
    :ICODEMPPD , :SCODFILIALPD , :ICODPROD , :ICODEMPLE ,
    :SCODFILIALLE , :CCODLOTE , :ICODEMPTM, :SCODFILIALTM,
    :ICODTIPOMOV, :ICODEMPIV , :SCODFILIALIV ,
    :ICODINVPROD , :ICODEMPCP , :SCODFILIALCP , :ICODCOMPRA ,
    :SCODITCOMPRA , :ICODEMPVD , :SCODFILIALVD , :CTIPOVENDA ,
    :ICODVENDA , :SCODITVENDA , :ICODEMPRM , :SCODFILIALRM ,
    :ICODRMA , :SCODITRMA , :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop,
    :ICODEMPNT , :SCODFILIALNT , :CCODNAT ,
    :DDTMOVPROD , :IDOCMOVPROD , :CFLAG , :NQTDMOVPROD ,
    :NPRECOMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX );

  /* REPROCESSAR ESTOQUE */

  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
     :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
     :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

 /* ATUALIZA O CUSTO NO CADASTRO DE PRODUTOS
   EXECUTE PROCEDURE EQMOVPRODATCUSTSP(:SOPERADOR, :ICODEMPPD, :SCODFILIAL,
    :ICODMOVPROD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMMOVPROD); 
 */


  suspend;
end
^

ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldprc numeric(15,5);
declare variable ncustompmprc numeric(15,5);
declare variable nsldprcax numeric(15,5);
declare variable ncustompmprcax numeric(15,5);
declare variable nsldlc numeric(15,5);
declare variable ncustompmlc numeric(15,5);
declare variable nsldlcax numeric(15,5);
declare variable ncustompmlcax numeric(15,5);
declare variable ddtmovprodold date;
declare variable nprecomovprodold numeric(15,5);
declare variable nqtdmovprodold numeric(15,5);
declare variable icodemptmold integer;
declare variable scodfilialtmold smallint;
declare variable icodtipomovold integer;
declare variable calttm char(1);
declare variable ddtprc date;
declare variable ddtprcate date;
declare variable cestoqmovprod char(1);
begin
  /* Procedure de atualização da tabela eqmovprod */

  DDTPRCATE = NULL; /* Até onde deve ser processando o estoque */
 /* localiza movprod */

-- execute procedure sgdebugsp('antes da atualização...','no inicio');

  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
    FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
      :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
      :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM,
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

--  traz valores antigos

  SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV, MP.DTMOVPROD,
       MP.PRECOMOVPROD, MP.QTDMOVPROD  FROM EQMOVPROD MP
     WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND MP.CODMOVPROD=:ICODMOVPROD
     INTO :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD, :DDTMOVPRODOLD,
       :NPRECOMOVPRODOLD, :NQTDMOVPRODOLD;

   /* abaixo verificação se a alteração de tipo de movimento mexe no estoque */
   SELECT CALTTM FROM EQMOVPRODCKUTMSP(:ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
      :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD) INTO :CALTTM;

   /* verifica se há relevância para reprocessamento */
   if ( (DDTMOVPROD!=DDTMOVPRODOLD) OR (CALTTM='S') OR
        (NPRECOMOVPROD!=NPRECOMOVPRODOLD) OR (NQTDMOVPROD!=NQTDMOVPRODOLD) ) then
   begin

   -- execute procedure sgdebugsp('entrou no if','1');


      if ( DDTMOVPRODOLD IS NULL) then
         DDTMOVPRODOLD = DDTMOVPROD; /* garantir que a data antiga não e nula; */
      /* verifica qual data é menor para reprocessamento */
      if ( DDTMOVPROD<=DDTMOVPRODOLD ) then
      begin

     -- execute procedure sgdebugsp('entrou no if','2');

          DDTPRC = DDTMOVPROD;
          if (DDTMOVPROD=DDTMOVPRODOLD) then
             DDTPRCATE = null;
          else
             DDTPRCATE = DDTMOVPRODOLD;
/*          verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, :NPRECOMOVPROD, :NPRECOMOVPROD,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC)
              INTO :NSLDPRC, :NCUSTOMPMPRC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
              NSLDPRCAX = NSLDPRC;
              NCUSTOMPMPRCAX = NCUSTOMPMPRC;
          end
          else
          begin
          SELECT NSALDO, NCUSTOMPM
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX)
              INTO :NSLDPRCAX, :NCUSTOMPMPRCAX;
          end
          NCUSTOMPMLC = NCUSTOMPMPRC;
          NSLDLC = NSLDPRC;
          NCUSTOMPMLCAX = NCUSTOMPMPRCAX;
          NSLDLCAX = NSLDPRCAX;
      end
      else
      begin
          DDTPRC = DDTMOVPRODOLD;
          DDTPRCATE = DDTMOVPROD;
          /* verifica lançamento anterior e traz custo e saldo */

       --   execute procedure sgdebugsp('entrou no else','3');

          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, 0, 0,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMLC, :NCUSTOMPMLCAX,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDLC, :NCUSTOMPMLC, :NSLDLCAX, :NCUSTOMPMLCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
              :NCUSTOMPMLC, :NSLDLC)
              INTO :NSLDLC, :NCUSTOMPMLC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
             NSLDLCAX = NSLDLC;
             NCUSTOMPMLCAX = NCUSTOMPMLC;
          end
          else
          begin
              SELECT NSALDO, NCUSTOMPM
                  FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
                  :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
                  :NCUSTOMPMLCAX, :NSLDLCAX)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

      /* REPROCESSAR ESTOQUE */

    --  execute procedure sgdebugsp('antes do reprocessamento ESTOQUE',CESTOQMOVPROD);
    --   execute procedure sgdebugsp('antes do reprocessamento QTD',NQTDMOVPROD);
    --   execute procedure sgdebugsp('MOVPROD COD',ICODMOVPROD);

       SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
        FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
          :SCODFILIALPD, :ICODPROD, :DDTPRC, :DDTPRCATE, :NSLDPRC,
          :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX,
          :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
        INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX;

      UPDATE EQMOVPROD SET DTMOVPROD=:DDTMOVPROD,
         QTDMOVPROD=:NQTDMOVPROD, PRECOMOVPROD=:NPRECOMOVPROD,
         SLDMOVPROD=:NSLDLC, CUSTOMPMMOVPROD=:NCUSTOMPMLC,
         SLDMOVPRODAX=:NSLDLCAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMLCAX,
         FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE, ESTOQMOVPROD=:CESTOQMOVPROD ,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
   end
   else /*  caso não tenha nenhuma alteração relevânte para processamento */

  --  execute procedure sgdebugsp('antes do reprocessamento','5SG');

      UPDATE EQMOVPROD SET FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
end
^

ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable cestoqmovprod char(1);
declare variable ctipomovprod char(1);
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable soperador smallint;
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de inserção na tabela eqmovprod */

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX FROM EQMOVPRODSLDSP(null, null, null, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NPRECOMOVPROD, :NPRECOMOVPROD,
     :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX )
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

  /* Verifica se haverá mudança de saldo*/
  SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD, CTIPOMOVPROD, SOPERADOR FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX)
        INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
  end

  SELECT SCODFILIAL, ICODMOVPROD FROM EQMOVPRODSEQSP(:ICODEMPPD)
     INTO :SCODFILIAL, :ICODMOVPROD;  /* encontra o próximo código e a filial*/

   INSERT INTO EQMOVPROD ( CODEMP, CODFILIAL, CODMOVPROD,
      CODEMPPD, CODFILIALPD , CODPROD , CODEMPLE ,
      CODFILIALLE , CODLOTE , CODEMPTM, CODFILIALTM,
      CODTIPOMOV, CODEMPIV , CODFILIALIV , CODINVPROD ,
      CODEMPCP , CODFILIALCP , CODCOMPRA , CODITCOMPRA , CODEMPVD ,
      CODFILIALVD , TIPOVENDA , CODVENDA , CODITVENDA , CODEMPRM ,
      CODFILIALRM , CODRMA , CODITRMA ,
      CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENTOP,
      CODEMPNT , CODFILIALNT ,
      CODNAT , DTMOVPROD , DOCMOVPROD , FLAG , QTDMOVPROD ,
      PRECOMOVPROD, ESTOQMOVPROD, TIPOMOVPROD, SLDMOVPROD, CUSTOMPMMOVPROD,
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX )
   VALUES ( :ICODEMPPD, :SCODFILIAL, :ICODMOVPROD,
    :ICODEMPPD , :SCODFILIALPD , :ICODPROD , :ICODEMPLE ,
    :SCODFILIALLE , :CCODLOTE , :ICODEMPTM, :SCODFILIALTM,
    :ICODTIPOMOV, :ICODEMPIV , :SCODFILIALIV ,
    :ICODINVPROD , :ICODEMPCP , :SCODFILIALCP , :ICODCOMPRA ,
    :SCODITCOMPRA , :ICODEMPVD , :SCODFILIALVD , :CTIPOVENDA ,
    :ICODVENDA , :SCODITVENDA , :ICODEMPRM , :SCODFILIALRM ,
    :ICODRMA , :SCODITRMA , :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop,
    :ICODEMPNT , :SCODFILIALNT , :CCODNAT ,
    :DDTMOVPROD , :IDOCMOVPROD , :CFLAG , :NQTDMOVPROD ,
    :NPRECOMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX );

  /* REPROCESSAR ESTOQUE */

  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
     :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
     :CMULTIALMOX)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

 /* ATUALIZA O CUSTO NO CADASTRO DE PRODUTOS
   EXECUTE PROCEDURE EQMOVPRODATCUSTSP(:SOPERADOR, :ICODEMPPD, :SCODFILIAL,
    :ICODMOVPROD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMMOVPROD); 
 */


  suspend;
end
^

/* Alter (EQMOVPRODIUDSP) */
ALTER PROCEDURE EQMOVPRODIUDSP(CIUD CHAR(1),
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 AS
declare variable cmultialmox char(1);
begin
  /* Procedure que controle INSERT, UPDATE E DELETE da tabela eqmovprod */

  /* Linha incluida para passar como parâmetro se é multi almoxarifado
      Como o objetivo de evitar I/O
  */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMPPD) INTO :CMULTIALMOX;
  
  if (CIUD='I') then /* SE FOR INSERT */
     execute procedure EQMOVPRODISP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX);
  else if (CIUD='U') then
     execute procedure EQMOVPRODUSP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX);
  else if (CIUD='D') then
     execute procedure EQMOVPRODDSP( ICODEMPPD, SCODFILIALPD, ICODPROD, ICODEMPIV,
         SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA, SCODITCOMPRA,
         ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         DDTMOVPROD, ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX );
  suspend;
end
^

/* Alter (EQMOVPRODRETCODSP) */
ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Retorna o código do movimento de estoque */
  if (ICODINVPROD IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPIV=:ICODEMPIV AND MP.CODFILIALIV=:SCODFILIALIV
         AND MP.CODINVPROD=:ICODINVPROD
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODCOMPRA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPCP=:ICODEMPCP AND MP.CODFILIALCP=:SCODFILIALCP
         AND MP.CODCOMPRA=:ICODCOMPRA AND MP.CODITCOMPRA=:SCODITCOMPRA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODVENDA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPVD=:ICODEMPVD AND MP.CODFILIALVD=:SCODFILIALVD
         AND MP.TIPOVENDA=:CTIPOVENDA AND MP.CODVENDA=:ICODVENDA
         AND MP.CODITVENDA=:SCODITVENDA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODRMA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPRM=:ICODEMPRM AND MP.CODFILIALRM=:SCODFILIALRM
         AND MP.CODRMA=:ICODRMA AND MP.CODITRMA=:SCODITRMA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODOP IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
         AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqentop=:sseqopent
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;



  suspend;
end
^

/* Alter (EQMOVPRODUSP) */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR(1),
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldprc numeric(15,5);
declare variable ncustompmprc numeric(15,5);
declare variable nsldprcax numeric(15,5);
declare variable ncustompmprcax numeric(15,5);
declare variable nsldlc numeric(15,5);
declare variable ncustompmlc numeric(15,5);
declare variable nsldlcax numeric(15,5);
declare variable ncustompmlcax numeric(15,5);
declare variable ddtmovprodold date;
declare variable nprecomovprodold numeric(15,5);
declare variable nqtdmovprodold numeric(15,5);
declare variable icodemptmold integer;
declare variable scodfilialtmold smallint;
declare variable icodtipomovold integer;
declare variable calttm char(1);
declare variable ddtprc date;
declare variable ddtprcate date;
declare variable cestoqmovprod char(1);
begin
  /* Procedure de atualização da tabela eqmovprod */

  DDTPRCATE = NULL; /* Até onde deve ser processando o estoque */
 /* localiza movprod */

-- execute procedure sgdebugsp('antes da atualização...','no inicio');

  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
    FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
      :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
      :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM,
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

--  traz valores antigos

  SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV, MP.DTMOVPROD,
       MP.PRECOMOVPROD, MP.QTDMOVPROD  FROM EQMOVPROD MP
     WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND MP.CODMOVPROD=:ICODMOVPROD
     INTO :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD, :DDTMOVPRODOLD,
       :NPRECOMOVPRODOLD, :NQTDMOVPRODOLD;

   /* abaixo verificação se a alteração de tipo de movimento mexe no estoque */
   SELECT CALTTM FROM EQMOVPRODCKUTMSP(:ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
      :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD) INTO :CALTTM;

   /* verifica se há relevância para reprocessamento */
   if ( (DDTMOVPROD!=DDTMOVPRODOLD) OR (CALTTM='S') OR
        (NPRECOMOVPROD!=NPRECOMOVPRODOLD) OR (NQTDMOVPROD!=NQTDMOVPRODOLD) ) then
   begin

   -- execute procedure sgdebugsp('entrou no if','1');


      if ( DDTMOVPRODOLD IS NULL) then
         DDTMOVPRODOLD = DDTMOVPROD; /* garantir que a data antiga não e nula; */
      /* verifica qual data é menor para reprocessamento */
      if ( DDTMOVPROD<=DDTMOVPRODOLD ) then
      begin

     -- execute procedure sgdebugsp('entrou no if','2');

          DDTPRC = DDTMOVPROD;
          if (DDTMOVPROD=DDTMOVPRODOLD) then
             DDTPRCATE = null;
          else
             DDTPRCATE = DDTMOVPRODOLD;
/*          verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, :NPRECOMOVPROD, :NPRECOMOVPROD,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC)
              INTO :NSLDPRC, :NCUSTOMPMPRC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
              NSLDPRCAX = NSLDPRC;
              NCUSTOMPMPRCAX = NCUSTOMPMPRC;
          end
          else
          begin
          SELECT NSALDO, NCUSTOMPM
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX)
              INTO :NSLDPRCAX, :NCUSTOMPMPRCAX;
          end
          NCUSTOMPMLC = NCUSTOMPMPRC;
          NSLDLC = NSLDPRC;
          NCUSTOMPMLCAX = NCUSTOMPMPRCAX;
          NSLDLCAX = NSLDPRCAX;
      end
      else
      begin
          DDTPRC = DDTMOVPRODOLD;
          DDTPRCATE = DDTMOVPROD;
          /* verifica lançamento anterior e traz custo e saldo */

       --   execute procedure sgdebugsp('entrou no else','3');

          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, 0, 0,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMLC, :NCUSTOMPMLCAX,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDLC, :NCUSTOMPMLC, :NSLDLCAX, :NCUSTOMPMLCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
              :NCUSTOMPMLC, :NSLDLC)
              INTO :NSLDLC, :NCUSTOMPMLC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
             NSLDLCAX = NSLDLC;
             NCUSTOMPMLCAX = NCUSTOMPMLC;
          end
          else
          begin
              SELECT NSALDO, NCUSTOMPM
                  FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
                  :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
                  :NCUSTOMPMLCAX, :NSLDLCAX)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

      /* REPROCESSAR ESTOQUE */

    --  execute procedure sgdebugsp('antes do reprocessamento ESTOQUE',CESTOQMOVPROD);
    --   execute procedure sgdebugsp('antes do reprocessamento QTD',NQTDMOVPROD);
    --   execute procedure sgdebugsp('MOVPROD COD',ICODMOVPROD);

       SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
        FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
          :SCODFILIALPD, :ICODPROD, :DDTPRC, :DDTPRCATE, :NSLDPRC,
          :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX,
          :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
        INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX;

      UPDATE EQMOVPROD SET DTMOVPROD=:DDTMOVPROD,
         QTDMOVPROD=:NQTDMOVPROD, PRECOMOVPROD=:NPRECOMOVPROD,
         SLDMOVPROD=:NSLDLC, CUSTOMPMMOVPROD=:NCUSTOMPMLC,
         SLDMOVPRODAX=:NSLDLCAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMLCAX,
         FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE, ESTOQMOVPROD=:CESTOQMOVPROD ,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
   end
   else /*  caso não tenha nenhuma alteração relevânte para processamento */

  --  execute procedure sgdebugsp('antes do reprocessamento','5SG');

      UPDATE EQMOVPROD SET FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
end
^

/* Alter (FNADICRECEBERSP01) */
ALTER PROCEDURE FNADICRECEBERSP01(CTIPOVENDA CHAR(1),
ICODVENDA INTEGER,
ICODEMPTC INTEGER,
ICODFILIALTC INTEGER,
CODTIPOCOB INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
CODCLI INTEGER,
ICODEMPVD INTEGER,
ICODFILIALVD SMALLINT,
CODVEND INTEGER,
VLRLIQVENDA NUMERIC(15,5),
DTVENDA DATE,
VLRCOMISVENDA NUMERIC(15,5),
DOCVENDA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPCB INTEGER,
CODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
FLAG CHAR(1),
OBSREC VARCHAR(250),
VLRBASECOMIS NUMERIC(15,5),
VLRRETENSAOISS NUMERIC(15,5))
 AS
declare variable icodrec integer;
declare variable scodfilialrc smallint;
declare variable iparcplanopag integer;
BEGIN
  SELECT R.CODREC,R.CODFILIAL FROM FNRECEBER R
     WHERE R.CODEMPVA=:ICODEMP AND R.CODFILIALVA=:ICODFILIAL AND
       R.TIPOVENDA=:CTIPOVENDA AND R.CODVENDA=:ICODVENDA
     INTO :ICODREC,:SCODFILIALRC;
  SELECT PARCPLANOPAG FROM FNPLANOPAG WHERE CODEMP=:ICODEMPPG AND
     CODFILIAL=:ICODFILIALPG AND CODPLANOPAG=:CODPLANOPAG
     INTO :IPARCPLANOPAG;

  IF ( (ICODREC IS NULL) AND (IPARCPLANOPAG>0) ) THEN
  BEGIN
     SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNRECEBER') INTO SCODFILIALRC;
     SELECT ISEQ FROM SPGERANUM(:ICODEMP,:SCODFILIALRC,'RC') INTO :ICODREC;

     -- Caso haja retensão de ISS deve
     if(coalesce(:vlrretensaoiss,0)>0) then
     begin
        vlrliqvenda = vlrliqvenda - vlrretensaoiss;
     end

     INSERT INTO FNRECEBER (CODEMP,CODFILIAL,CODREC, CODTIPOCOB, CODEMPTC, CODFILIALTC,
              CODPLANOPAG,CODEMPPG,CODFILIALPG,CODCLI,
              CODEMPCL,CODFILIALCL,CODVEND,CODEMPVD,CODFILIALVD,TIPOVENDA,CODVENDA,
              CODEMPVA,CODFILIALVA,VLRREC,
              VLRDESCREC,VLRMULTAREC,VLRJUROSREC,VLRPARCREC,VLRPAGOREC,
              VLRAPAGREC,DATAREC,STATUSREC,VLRCOMIREC,DOCREC,CODBANCO,CODEMPBO,CODFILIALBO,
              CODEMPCB, CODFILIALCB, CODCARTCOB, FLAG, OBSREC, vlrbasecomis)
              VALUES (
                     :ICODEMP,:SCODFILIALRC,:ICODREC, :CODTIPOCOB, :ICODEMPTC, :ICODFILIALTC,
                     :CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodCli,
                     :ICODEMPCL,:ICODFILIALCL,:CodVend,:ICODEMPVD,:ICODFILIALVD,:CTIPOVENDA,:ICodVenda,
                     :ICODEMP,:ICODFILIAL,:VlrLiqVenda,
                     0,0,0,:VlrLiqVenda,0,:VlrLiqVenda,:dtVenda,'R1',
                     :VlrComisVenda,:DocVenda,:CodBanco,:ICODEMPBO,:ICODFILIALBO,
                     :CODEMPCB, :CODFILIALCB, :CODCARTCOB, :FLAG, :OBSREC,:vlrbasecomis
              );
  END
  ELSE IF (ICODREC IS NOT NULL) THEN
  BEGIN
    IF (IPARCPLANOPAG>0) THEN
    BEGIN
        UPDATE FNRECEBER SET ALTUSUREC='N',
              CODTIPOCOB=:CODTIPOCOB,CODEMPTC=:ICODEMPTC, CODFILIALTC=:ICODFILIALTC,
              CODPLANOPAG=:CodPlanoPag,CODEMPPG=:ICODEMPPG,CODFILIALPG=:ICODFILIALPG,
              CODCLI=:CodCli, CODEMPCL=:ICODEMPCL,CODFILIALCL=:ICODFILIALCL,
              CODVEND=:CodVend,CODEMPVD=:ICODEMPVD,CODFILIALVD=:ICODFILIALVD,
              VLRREC=:VlrLiqVenda,VLRDESCREC=0,VLRMULTAREC=0,VLRJUROSREC=0,
              VLRPARCREC=:VlrLiqVenda,VLRPAGOREC=0,VLRAPAGREC=:VlrLiqVenda,
              DATAREC=:dtVenda,VLRCOMIREC=:VlrComisVenda,
              /* STATUSREC='R1' */
              DOCREC=:DocVenda,CODBANCO=:CodBanco,CODEMPBO=:ICODEMPBO,
              CODFILIALBO=:ICODFILIALBO,
              CODEMPCB=:CODEMPCB, CODFILIALCB=:CODFILIALCB, CODCARTCOB=:CODCARTCOB, FLAG=:FLAG,
              vlrbasecomis=:vlrbasecomis
             WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIALRC;

        UPDATE FNITRECEBER SET ALTUSUITREC='S' /* Atualiza os itens de contas a */
        /* receber para ajustar automaticamente os valores no cabeçalho */
             WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIALRC;
     END
     ELSE
     BEGIN
         DELETE FROM FNRECEBER WHERE CODREC=:ICODREC AND CODEMP=:ICODEMP AND
            CODFILIAL=:SCODFILIALRC;
     END
   END

END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retensão do tributo ISS (descontando do valor final do título)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

/* Alter (LFBUSCAFISCALSP) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f1.percissfilial, f.aliqissfisc)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(f.percissfilial, it.aliqissfisc)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca é para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de classificação fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do ítem de classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da classificação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de classficiação fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

/* Alter (LFBUSCAPREVTRIBORC) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc smallint;
declare variable vlrbasepis numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrprod integer;
declare variable aliqpis numeric(6,2);
declare variable qtd numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(6,2);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrliq numeric(15,5);
declare variable vlrfrete numeric(15,5);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable aliqipi numeric(6,2);
declare variable tpcalcipi char(1);
declare variable vlrbaseipi numeric(15,5);
declare variable aliqcsocial numeric(6,2);
declare variable aliqir numeric(6,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable tipoprod char(1);
declare variable aliqiss numeric(6,2);
declare variable aliqicms numeric(6,2);
declare variable tpredicms char(1);
declare variable redicms numeric(15,5);
declare variable baseicms numeric(15,5);
declare variable codtrattrib char(2);
declare variable codsittribpis char(2);
declare variable ufcli char(2);
begin

    -- Inicializando variáveis

    vlripi = 0;
    vlrfrete = 0;

    -- Buscando informações no orçamento (cliente e tipo de movimento)
    select oc.codempcl,oc.codfilialcl,oc.codcli,tm.codemptm,tm.codfilialtm,tm.codtipomovtm,coalesce(cl.siglauf,cl.ufcli)
    from vdorcamento oc, vdcliente cl, eqtipomov tm
    where oc.codemp=:codemp and oc.codfilial=:codfilial and oc.codorc=:codorc and oc.tipoorc=:tipoorc
    and cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli
    and tm.codemp=oc.codemp and tm.codfilial=oc.codfilialtm and tm.codtipomov=oc.codtipomov
    into :codempcl, :codfilialcl, :codcli, :codemptm, :codfilialtm, :codtipomov, :ufcli;

    -- Buscando informações do produto no item de orçamento
    select io.codemppd, io.codfilialpd, io.codprod, io.vlrproditorc, io.qtditorc, io.vlrliqitorc, coalesce(io.vlrfreteitorc,0),
    pd.tipoprod

    from vditorcamento io, eqproduto pd
    where io.codemp=:codemp and io.codfilial=:codfilial and io.codorc=:codorc and io.tipoorc=:tipoorc and io.coditorc=:coditorc
    and pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod
    into :codemppd, :codfilialpd, :codprod, :vlrprod, :qtd, :vlrliq, :vlrfrete, :tipoprod;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempcl, :codfilialcl, :codcli,
    :codemptm, :codfilialtm, :codtipomov, 'VD',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.codsittribpis, cf.aliqpisfisc, cf.vlrpisunidtrib, cf.codsittribcof, cf.aliqcofinsfisc, cf.vlrcofunidtrib,
    cf.vlripiunidtrib, cf.aliqipifisc, cf.codsittribipi, cf.tpcalcipi,
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(fi.percissfilial, cf.aliqissfisc),
    cf.tpredicmsfisc, cf.redfisc, cf.codtrattrib
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :codsittribpis, :aliqpis, :vlrpisunidtrib, :codsittribcof, :aliqcofins, :vlrcofunidtrib,
    :vlripiunidtrib, :aliqipi, :codsittribipi,:tpcalcipi, :aliqcsocial, :aliqir, :aliqiss,
    :tpredicms, :redicms, :codtrattrib;

    -- Definição do IPI
    if(:codsittribipi not in ('52','53','54')) then -- IPI Tributado
    begin
        if(:tpcalcipi='P' and aliqipi is not null and aliqipi > 0) then -- Calculo pela aliquota
        begin
            vlrbaseipi = :vlrliq; -- (Base do IPI = Valor total dos produtos - Implementar situações distintas futuramente)
            vlripi = (vlrbaseipi * aliqipi) / 100;
        end
        else if (vlripiunidtrib is not null and vlripiunidtrib > 0) then -- Calculo pela quantidade
        begin
            vlripi = qtd * vlripiunidtrib;
        end
    end

    -- Definição do PIS

    if(:codsittribpis in ('01','02','99') and aliqpis is not null and aliqpis > 0 ) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprod; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:codsittribpis in ('03') and vlrpisunidtrib is not null and vlrpisunidtrib > 0) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtd * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:codsittribcof in ('01','02','99') and aliqcofins is not null and aliqcofins > 0 ) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprod; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:codsittribpis in ('03') and vlrcofunidtrib is not null and vlrcofunidtrib > 0) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtd * vlrcofunidtrib;
    end

    -- Definição do IR

    if(aliqir is not null and aliqir > 0) then
    begin
        vlrir = ((:vlrliq + :vlripi + :vlrfrete) * aliqir) / 100;
    end

    -- Definição da CSocial

    vlrcsocial = ((:vlrliq + :vlripi + :vlrfrete) * aliqcsocial) / 100;

    -- Definição do ISS se for um serviço
    if (tipoprod = 'S') then
    begin
        if ( aliqiss is not null and aliqiss > 0 ) then
        begin
            vlriss = vlrliq * (aliqiss/100);
        end
    end

    -- Definição do ICMS
    -- Calcular icms quando aliquota maio que zero e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

    if(codtrattrib not in ('40','30','41','50')) then
    begin

        if(aliqicms is null) then
        begin
            select ti.aliqti from lftabicms ti
            where codemp=:codemp and codfilial=:codfilial and ufti=:ufcli
            into aliqicms;
        end

        if (redicms>0) then -- Com redução
        begin
            if(tpredicms='B') then -- Redução na base de cálculo
            begin
                baseicms = vlrliq * ( 1 - (redicms / 100));
                vlricms = baseicms * (aliqicms / 100);
            end
            else -- Redução no valor
            begin

                baseicms = vlrliq;
                vlricms = baseicms * ( aliqicms / 100 );
                vlricms = vlricms * (( 100 - redicms ) / 100);


            end
        end
        else -- Sem redução
        begin

            baseicms = vlrliq;
            vlricms = baseicms * (aliqicms / 100);

        end

    end
  suspend;
end
^

/* Alter (LFBUSCATRIBCOMPRA) */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
begin

    -- Inicializando variáveis

    vlrfunrural = 0;

    -- Buscando informações na compra (fornecedor e tipo de movimento)
    select cp.codempfr,cp.codfilialfr,cp.codfor,tm.codemptm,tm.codfilialtm,tm.codtipomovtm
    from cpcompra cp, cpforneced fr, eqtipomov tm
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra
    and fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    and tm.codemp=cp.codemp and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov
    into :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.aliqfunruralfisc
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :aliqfunrural;

    -- Definição do Funrural
    if(:aliqfunrural>0) then -- Retenção do funrural
    begin
        vlrbasefunrural = :vlrliq; -- (Base do Funrural = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrfunrural = (vlrbasefunrural * aliqfunrural) / 100;
    end


    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.3 (08/12/2010)';
    suspend;
end
^

/* Alter (VDADICITEMPDVSP) */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod char(13);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;

  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,null)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT,null,null,null,null)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(15,5);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(15,5);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(15,5);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(15,5);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,5);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod char(13);
declare variable stipoprod char(1);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

    -- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

    -- Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)

    select codempif,codfilialif,codfisc,coditfisc
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',null,null,null,null,null)
    into CODEMPIF,CODFILIALIF,CODFISC,CODITFISC;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,
    :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,:coditfisc)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda (já sabe o coditfisc)

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
    -- Bloco comentado, pois já buscou o percentual do iss através da procedure.
   --     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
   --     into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Alter (VDBUSCACUSTOVENDASP) */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
declare variable aliqicms numeric(9,2);
declare variable aliqipi numeric(9,2);
declare variable aliqpis numeric(9,2);
declare variable aliqir numeric(9,2);
declare variable aliqcofins numeric(9,2);
declare variable aliqcsocial numeric(9,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codfilialpf smallint;
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable vlrliq numeric(15,5);
declare variable redbase numeric(9,2);
declare variable base numeric(15,5);
declare variable ufcli char(2);
declare variable codtrattrib char(2);
declare variable comisprod numeric(6,2);
declare variable perccomvend numeric(6,2);
declare variable codnat char(4);
declare variable codregra char(4);
begin

    --Verifica se deve buscar custos para venda .
    if(:CODVENDA is not null) then
    begin

        select
            coalesce(vd.vlrprodvenda,0), coalesce(vd.vlrdescvenda,0), coalesce(vd.vlricmsvenda,0),
            coalesce(vd.vlroutrasvenda,0), coalesce(vd.vlrcomisvenda,0), coalesce(vd.vlradicvenda,0),
            coalesce(vd.vlripivenda,0), coalesce(vd.vlrpisvenda,0), coalesce(vd.vlrcofinsvenda,0),
            coalesce(vd.vlrirvenda,0), coalesce(vd.vlrcsocialvenda,0),
            coalesce(fr.vlrfretevd,0), fr.tipofretevd, fr.adicfretevd,
            
            sum(icv.vlrcustopeps * iv.qtditvenda),
            sum(icv.vlrcustompm * iv.qtditvenda),
            sum(icv.vlrprecoultcp * iv.qtditvenda)
            
            from
            vdvenda vd left outer join vdfretevd fr on
            fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda,
            
            vditvenda iv left outer join vditcustovenda icv on
            icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda
            and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda
            
            where vd.codemp=:CODEMPVD and vd.codfilial=:CODFILIALVD and vd.codvenda=:CODVENDA and vd.tipovenda=:TIPOVENDA and
            iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda
            
            group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14

            into vlrprod,vlrdesc,vlricms,vlroutras,vlrcomis,vlradic,vlripi,vlrpis,vlrcofins,vlrir,vlrcsocial,
                 vlrfrete,tipofrete,adicfrete,vlrcustopeps,vlrcustompm,vlrprecoultcp;

            suspend;

    end
    else
    begin
        --Buscando informações sobre o produto do item de orçamento
        select io.codemppd,io.codfilialpd,io.codprod,pd.comisprod,
        coalesce(io.vlrproditorc,0),coalesce(io.vlrdescitorc,0),coalesce(io.vlrliqitorc,0),
        ico.vlrcustopeps * io.qtditorc, ico.vlrcustompm * io.qtditorc, ico.vlrprecoultcp * io.qtditorc,
        cf.codregra
        from lfclfiscal cf, eqproduto pd, vditorcamento io left outer join vditcustoorc ico on
        ico.codemp=io.codemp and ico.codfilial=io.codfilial and ico.codorc = io.codorc and
        ico.tipoorc=io.tipoorc and ico.coditorc=io.coditorc
        where io.codemp=:CODEMPOC and io.codfilial=:CODFILIALOC and io.codorc=:CODORC and io.tipoorc=:TIPOORC and io.coditorc=:CODITORC and
        pd.codemp=io.codemppd and pd.codfilial=io.codfilial and pd.codprod=io.codprod and
        cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc
        into :CODEMPPD,:CODFILIALPD,:CODPROD,:VLRPROD,:VLRDESC,:VLRLIQ,:COMISPROD,:VLRCUSTOPEPS,:VLRCUSTOMPM,:VLRPRECOULTCP,:CODREGRA;

        -- Buscanco informações do orçamento,cliente,vendedor
        select oc.codempcl,oc.codfilialcl,oc.codcli,coalesce(cl.siglauf,cl.ufcli),vd.perccomvend,
        oc.tipofrete,oc.adicfrete
        from vdorcamento oc, vdcliente cl, vdvendedor vd
        where oc.codemp=:CODEMPOC and oc.codfilial=:CODFILIALOC and oc.tipoorc=:TIPOORC and oc.codorc=:CODORC and
        cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli and
        vd.codemp=oc.codempvd and vd.codfilial=oc.codfilialvd and vd.codvend=oc.codvend
        into :CODEMPCL,:CODFILIALCL,:CODCLI,:UFCLI,:PERCCOMVEND,:TIPOFRETE,:ADICFRETE;

        --Buscando o tipo de movimento a ser utilizado na venda futura
        select p1.codempt2,p1.codfilialt2,p1.codtipomov2 from sgprefere1 p1
        where p1.codemp=:CODEMPOC and p1.codfilial=:CODFILIALPF
        into :CODEMPTM,:CODFILIALTM,:CODTIPOMOV;

        -- Buscando natureza de operação da venda futura
        select first 1 nt.codnat from lfnatoper nt, lfitregrafiscal irf
        where nt.codemp=irf.codemp and nt.codfilial=irf.codfilial and nt.codnat=irf.codnat
        and (irf.codtipomov=:CODTIPOMOV or irf.codtipomov is null)
        and (irf.codemp=:CODEMPTM or irf.codemp is null)
        and (irf.codfilial=:CODFILIALTM or irf.codfilial is null)
        and irf.codregra=:CODREGRA and irf.noufitrf='N' and irf.cvitrf='V'
        into :CODNAT;

         -- Buscando informações fiscais
        select codtrattrib,coalesce(aliqfisc,0),coalesce(aliqipifisc,0),coalesce(aliqpis,0),coalesce(aliqcofins,0),coalesce(aliqcsocial,0),
        coalesce(aliqir,0),coalesce(redfisc,0)
        from lfbuscafiscalsp(:CODEMPPD,:CODFILIALPD,:CODPROD,:CODEMPCL,:CODFILIALCL,:CODCLI,:CODEMPTM,:CODFILIALTM,
        :CODTIPOMOV,'VD',:codnat,null,null,null,null)
        into :CODTRATTRIB,:ALIQICMS,:ALIQIPI,:ALIQPIS,:ALIQCOFINS,:ALIQCSOCIAL,:ALIQIR,:REDBASE;

        -- Caso o ICMS não seja definido na classifificação, buscar da tabela de aliquotas.
        if(:ALIQICMS = 0 and :CODTRATTRIB in('00','51','20','70','10') ) then
        begin
            select coalesce(PERCICMS,0) from lfbuscaicmssp (:CODNAT,:UFCLI,:CODEMPOC,:CODFILIALPF)
            into :ALIQICMS;
        end

        -- Buscando custo do ítem

        if(:REDBASE >0) then
        begin
            BASE = :VLRLIQ - ((:VLRLIQ * :REDBASE) /100 );
        end

        BASE = :VLRLIQ;

        vlricms = :BASE * :ALIQICMS / 100;

--      vlroutras =
        vlrcomis = :VLRLIQ * ((:COMISPROD * :PERCCOMVEND) / 10000 );

--      vlradic =
        vlripi = :VLRLIQ * :ALIQIPI / 100;
        vlrpis = :VLRLIQ * :ALIQCOFINS / 100;
        vlrcofins = :VLRLIQ * :ALIQCOFINS / 100;
        vlrir = :VLRLIQ * :ALIQIR /100;
        vlrcsocial = :VLRLIQ * :ALIQCSOCIAL / 100;
--      vlrfrete =

    end

end
^

/* Alter (VDEVOLUVENDAS) */
ALTER PROCEDURE VDEVOLUVENDAS(ICODEMP SMALLINT,
ICODFILIAL SMALLINT,
DATAINI DATE,
DATAFIM DATE,
ICODTIPOCLI SMALLINT,
ICODCLI SMALLINT,
FILTRAVENDAS CHAR(1),
FATURADO CHAR(1),
FINANCEIRO CHAR(1),
EMITIDO CHAR(1))
 RETURNS(VALOR NUMERIC(15,5),
MES SMALLINT,
ANO SMALLINT)
 AS
declare variable valortmp numeric(15,5);
declare variable datatmp date;
declare variable mestmp smallint;
declare variable anotmp smallint;
declare variable mesant smallint;
declare variable anoant smallint;
declare variable valorant numeric(15,5);
declare variable enviou char(1);
begin
  /* Retorna os valores de vendas por período */
  VALOR = 0;
  VALORANT = 0;
  ENVIOU = 'N';

  FOR select sum(v.vlrliqvenda),v.dtemitvenda from vdvenda v, vdcliente c, eqtipomov tm
             where c.codcli=v.codcli and c.codemp=v.codempcl and c.codfilial=v.codfilialcl
             and v.dtemitvenda between :DATAINI and :DATAFIM and v.codemp = :ICODEMP
             and v.codfilial = :ICODFILIAL
             and tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov
             and ( (c.codtipocli=:ICODTIPOCLI) or (:ICODTIPOCLI is null) )
             and ( (v.codcli=:ICODCLI) or (:ICODCLI is null) )
             and ( (tm.tipomov in(select tm2.tipomov from eqtipomov tm2 where tm2.codemp=v.codemptm and tm2.codfilial=v.codfilialtm and tm2.somavdtipomov='S')) or (:FILTRAVENDAS='N') )

             and (TM.FISCALTIPOMOV=:faturado or :faturado='A')
             and (TM.SOMAVDTIPOMOV=:financeiro or :financeiro='A')
             and (( (V.STATUSVENDA IN ('V2','V3','P3') and :emitido='S') or :emitido='A' )
             or ( (V.STATUSVENDA NOT IN ('V2','V3','P3') and :emitido='N') or :emitido='A' ))

             group by v.dtemitvenda order by v.dtemitvenda
      into VALORTMP,DATATMP do
      BEGIN
        MESTMP = extract(MONTH from DATATMP);
        ANOTMP = extract(YEAR from DATATMP);
        VALOR=VALOR+VALORANT;
        MES=MESANT;
        ANO=ANOANT;
        if ((not MESANT = MESTMP or not ANOANT = ANOTMP) AND not MESANT is null) then
        begin
          suspend;
          ENVIOU = 'S';
          VALOR = 0;
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        end
        ELSE
        BEGIN
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        END
      END

     VALOR=VALOR+VALORANT;
     MES=MESANT;
     ANO=ANOANT;
     suspend;
end
^

SET TERM ; ^

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPPD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALPD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='TIPOVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODITVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPLE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALLE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODLOTE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODTIPOMOV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODITCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPIV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALIV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODINVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='DTMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='TIPOMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='ESTOQMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPAX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALAX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODALMOX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='QTDMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='PRECOMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CUSTOMPMMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CUSTOMPMMOVPRODAX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='SLDMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='SLDMOVPRODAX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='FLAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPNT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALNT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODNAT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='DOCMOVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPRM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALRM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODRMA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODITRMA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='EMMANUT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODEMPOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODFILIALOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='CODOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='SEQOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='SEQENTOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='EQMOVPROD' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPBO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALBO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODBANCO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODPLANOPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPCL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALCL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPVA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALVA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='TIPOVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODTIPOCOB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPCB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALCB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODCARTCOB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRDESCREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRMULTAREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRJUROSREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRDEVREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRPARCREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRPAGOREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRAPAGREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRBASECOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRRETENSAOISS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='DATAREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='STATUSREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='VLRCOMIREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='DTQUITREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='DOCREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='NROPARCREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='OBSREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='FLAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='ALTUSUREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='NUMCONTA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODPLAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODEMPCC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODFILIALCC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='ANOCC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='CODCC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=58
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=59
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=60
 WHERE RDB$RELATION_NAME='FNRECEBER' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='CODEMPPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='CODFILIALPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='CODPLAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='DATASL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='PREVSL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='SALDOSL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='FECHADO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='EMMANUT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='FNSALDOLANCA' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODITFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ORIGFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPTT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALTT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='TIPOFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='TPREDICMSFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='REDFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODTRATTRIB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='NOUFITFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPFC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALFC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFISCCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQFISCINTRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQLFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQIPIFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQPISFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQCOFINSFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQCSOCIALFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQIRFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQFUNRURALFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQIIFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPME';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALME';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODMENS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODTIPOMOV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='TIPOST';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='MARGEMVLAGR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='GERALFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPSP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALSP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODSITTRIBPIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IMPSITTRIBPIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPSC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALSC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODSITTRIBCOF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IMPSITTRIBCOF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPSI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALSI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODSITTRIBIPI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IMPSITTRIBIPI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='TPCALCIPI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='VLRIPIUNIDTRIB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='MODBCICMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='MODBCICMSST';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODPAIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='SIGLAUF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='VLRPISUNIDTRIB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='VLRCOFUNIDTRIB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='TIPOUSOITFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='REDBASEST';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='REDBASEFRETE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=58
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=59
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=60
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODREGRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=61
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODEMPIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=62
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODFILIALIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=63
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='CODSITTRIBISS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=64
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IMPSITTRIBISS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=65
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='ALIQISSFISC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=66
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='RETENSAOISS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=67
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=68
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=69
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=70
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=71
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=72
 WHERE RDB$RELATION_NAME='LFITCLFISCAL' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAREFPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPEDSEQ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAORCSEQ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILTRO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USALIQREL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOPRECOCUSTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ANOCENTROCUSTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='OBSORCPAD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV2';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT2';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT2';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TITORCTXT01';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ORDNOTA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SETORVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PREFCRED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOPREFCRED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMOEDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USACLASCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PERCPRECOCUSTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODGRUP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALGP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPGP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMARCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RGCLIOBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABFRETEVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABADICVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TRAVATMNFVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOVALIDORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLIMESMOCNPJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTBJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CNPJOBRIGCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='JUROSPOSCALC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTRAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=58
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=59
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=60
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=61
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=62
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=63
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTLOTNEG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=64
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTNEG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=65
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='NATVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=66
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IPIVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=67
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CUSTOSICMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=68
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=69
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=70
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANOPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=71
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=72
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=73
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTAB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=74
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPCE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=75
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALCE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=76
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODCLASCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=77
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDEC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=78
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDECFIN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=79
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='COMISPDUPL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=80
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=81
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=82
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=83
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=84
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=85
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VENDAMATPRIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=86
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VENDAPATRIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=87
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PEPSPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=88
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CNPJFOROBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=89
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INSCESTFOROBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=90
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCAPRODSIMILAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=91
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='MULTIALMOX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=92
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=93
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=94
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=95
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTNEGGRUP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=96
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USATABPE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=97
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TAMDESCPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=98
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DESCCOMPPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=99
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='OBSCLIVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=100
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONTESTOQ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=101
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIASPEDT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=102
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RECALCPCVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=103
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RECALCPCORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=104
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USALAYOUTPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=105
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERIFALTPARCVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=106
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCACODPRODGEN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=107
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=108
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENREF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=109
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODBAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=110
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODFAB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=111
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=112
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCAVLRULTCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=113
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ICMSVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=114
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOZERO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=115
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIMGASSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=116
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IMGASSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=117
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTCPFCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=118
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIECLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=119
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIEFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=120
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTECPFFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=121
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USANOMEVENDORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=122
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SISCONTABIL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=123
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ATBANCOIMPBOL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=124
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCODBAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=125
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICORCOBSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=126
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=127
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=128
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=129
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CUSTOCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=130
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABTRANSPCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=131
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABTRANSPORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=132
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABSOLCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=133
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICFRETEBASEICM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=134
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PRECOCPREL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=135
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DESCORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=136
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='MULTICOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=137
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USUATIVCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=138
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=139
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=140
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=141
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=142
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=143
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=144
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=145
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=146
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=147
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTITRECALTDTVENC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=148
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LCREDGLOBAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=149
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VDMANUTCOMOBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=150
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=151
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCLASSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=152
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGECLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=153
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGEFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=154
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGETRANSP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=155
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SOMAVOLUMES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=156
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCACEP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=157
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='URLWSCEP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=158
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=159
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS01CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=160
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS02CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=161
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS03CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=162
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS04CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=163
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIEPF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=164
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CREDICMSSIMPLES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=165
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=166
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=167
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMENSICMSSIMPLES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=168
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERACOMISVENDAORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=169
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERACODUNIF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=170
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=171
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=172
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=173
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=174
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=175
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=176
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=177
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=178
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=179
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=180
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=181
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=182
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=183
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=184
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=185
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERAPAGEMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=186
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LANCAFINCONTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=187
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LANCARMACONTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=188
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDECPRE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=189
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VISUALIZALUCR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=190
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=191
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIRNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=192
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIRNFELIN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=193
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FORMATODANFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=194
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='AMBIENTENFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=195
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PROCEMINFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=196
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERPROCNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=197
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='KEYLICNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=198
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTVENCTONFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=199
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFADPRODNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=200
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=201
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=202
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMAILNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=203
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='EXIBEPARCOBSDANFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=204
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERSAONFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=205
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='REGIMETRIBNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=206
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFCPDEVOLUCAO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=207
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFVDREMESSA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=208
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERARECEMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=209
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RETENSAOIMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=210
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCUSTOLUC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=211
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABIMPORTCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=212
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HABVLRTOTITORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=213
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USABUSCAGENPRODCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=214
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICOBSORCPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=215
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOCOT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=216
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQPRECOAPROV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=217
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=218
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=219
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOFORFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=220
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=221
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESPECIALCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=222
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=223
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=224
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOVS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=225
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=226
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=227
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=228
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=229
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=230
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=231
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=232
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=233
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANOPAGSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=234
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ARREDPRECO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=235
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=236
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=237
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=238
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TPNOSSONUMERO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=239
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IMPDOCBOL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=240
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FECHACAIXA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=241
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FECHACAIXAAUTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=242
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='NUMDIGIDENTTIT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CLASSOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='NOMERESP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='IDENTPROFRESP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CARGORESP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='MESESDESCCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CODTIPOMOV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CODEMPTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='CODFILIALTM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='SITRMAOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='IMGASSRESP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='BAIXARMAAPROV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='RATAUTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='APAGARMAOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='NOMERELANAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='SITPADOP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='SITPADOPCONV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='HABCONVCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='PRODETAPAS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='SGPREFERE5' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='NOMEVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='ENDVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='NUMVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='COMPLVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='BAIRVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CIDVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CEPVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='UFVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='FONEVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='FAXVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='EMAILVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='PERCCOMVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='COMIPIVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALPN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODPLAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CPFVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='RGVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CNPJVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='INSCVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CELVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPSE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALSE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODSETOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPCM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALCM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODCLCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFORNVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFUNC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPFU';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALFU';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPTV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALTV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODTIPOVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='DDDFONEVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='DDDFAXVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='DDDCELVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='SSPVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='OBSVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='ATIVOCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='IMGASSVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='NUMCONTA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODEMPSC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODFILIALSC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='CODSECAO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='VLRABONO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=58
 WHERE RDB$RELATION_NAME='VDVENDEDOR' AND RDB$FIELD_NAME='VLRDESCONTO';

/* Create(Add) Crant */
GRANT DELETE, INSERT, SELECT, UPDATE ON PPOPENTRADA TO ADM;

GRANT EXECUTE ON PROCEDURE SGDEBUGSP TO ADM;



