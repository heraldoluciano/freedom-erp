/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

SET AUTODDL ON;

ALTER TABLE VDITVENDA DROP CONSTRAINT VDITVENDAFKCPITCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE PPOP DROP CONSTRAINT PPOPFKITCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE LFITCOMPRA DROP CONSTRAINT LFITCOMPRAFKCPITCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE EQMOVSERIE DROP CONSTRAINT EQMOVSERIEFKCPITCOM;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE EQMOVPROD DROP CONSTRAINT EQMOVPRODFKCPITCOM;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE EQITRECMERCITCP DROP CONSTRAINT EQITRECMERCITCPFKITCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPDEVOLUCAO DROP CONSTRAINT CPDEVOLUCAOFKITCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPCOMPRAVENDA DROP CONSTRAINT CPCOMPRAVENDAFKCPI;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPCOMPRAPED DROP CONSTRAINT CPCOMPRAPEDFKCPCOMPRAPED;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPCOMPRAPED DROP CONSTRAINT CPCOMPRAPEDFKCPCOMPRA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

/* Drop Constraints... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPITCOMPRA DROP CONSTRAINT CPITCOMPRAPK;


--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE LFITCALCCUSTO DROP CONSTRAINT LFITCALCCUSTOPK;



/* Empty PPCUSTOPRODSP for drop PPITESTRUTURA(QTDITEST) */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE PPCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(CUSTOUNIT NUMERIC(15,5),
SLDPROD NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Empty PPITOPSP01 for drop PPITESTRUTURA(QTDITEST) */
/* AssignEmptyBody proc */
ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Empty PPITOPSP02 for drop PPITESTRUTURA(QTDITEST) */
/* AssignEmptyBody proc */
ALTER PROCEDURE PPITOPSP02(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER PPOPFASETGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

SET TERM ; ^

ALTER DOMAIN NUMERICDN2 TYPE NUMERIC(15,9);

/* Alter Field (Length): from 50 to 60... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=60, RDB$CHARACTER_LENGTH=60
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='CPFORNECED' AND  RDB$FIELD_NAME='RAZFOR');

Update Rdb$Relation_Fields set Rdb$Description =
'Código do fornecedor na importação.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFOR';

/* Alter Field (Length): from 50 to 60... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=60, RDB$CHARACTER_LENGTH=60
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='TKCONTATO' AND  RDB$FIELD_NAME='RAZCTO');

ALTER TABLE TKCONTATO DROP CONSTRAINT TKCONTATOFKTKTIPOCONT;

DROP VIEW TKCONTCLIVW01;

ALTER TABLE TKCONTATO ALTER COLUMN CODEMPTO TYPE INTEGER;

/* Alter Field (Length): from 50 to 60... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=60, RDB$CHARACTER_LENGTH=60
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='VDCLIENTE' AND  RDB$FIELD_NAME='RAZCLI');


ALTER TABLE CPCOMPRA ADD CNF BIGINT DEFAULT 0;

ALTER TABLE CPCOMPRA ADD CHAVENFEVALIDA VARCHAR(20) DEFAULT 'N' NOT NULL;

ALTER TABLE CPFORNECED ADD CODEXPORTADOR VARCHAR(20);

ALTER TABLE CPIMPORTACAO ADD CODEMPOI INTEGER;

ALTER TABLE CPIMPORTACAO ADD CODFILIALOI SMALLINT;

ALTER TABLE CPIMPORTACAO ADD CODIMPOI INTEGER;

ALTER TABLE CPIMPORTACAO ADD TIPOIMP CHAR(1) DEFAULT 'O' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de importação - O = Original / C = Complementar'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='TIPOIMP';

ALTER TABLE CPIMPORTACAO ADD VLRDESPAD NUMERICDN DEFAULT 0.00 NOT NULL;

ALTER TABLE CPIMPORTACAO ADD VLRCOMPL NUMERICDN DEFAULT 0.00 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor para nota fiscal complementar'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRCOMPL';

ALTER TABLE CPIMPORTACAO ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a tabela está em manutenção (S/N).'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='EMMANUT';

ALTER TABLE CPITCOMPRA ADD CALCCUSTO CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE CPITCOMPRA ADD ADICICMSTOTNOTA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE CPITCOMPRA ADD VLRTXSISCOMEXITCOMPRA NUMERICDN DEFAULT 0.00;

ALTER TABLE CPITIMPORTACAO ADD VLRITDESPAD NUMERICDN DEFAULT 0.00 NOT NULL;

ALTER TABLE CPITIMPORTACAO ADD VLRCOMPL NUMERICDN DEFAULT 0.00 NOT NULL;

ALTER TABLE CPITIMPORTACAO ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE CRFICHAAVAL ADD DTVISITAFICHAAVAL DATE;

ALTER TABLE CRFICHAAVAL ADD HVISITAFICHAAVAL TIME;

ALTER TABLE CRFICHAAVAL ADD CODEMPVD INTEGER;

ALTER TABLE CRFICHAAVAL ADD CODFILIALVD SMALLINT;

ALTER TABLE CRFICHAAVAL ADD CODVEND INTEGER;

ALTER TABLE CRITFICHAAVAL ADD TIRITFICHAAVAL NUMERIC(15,5) DEFAULT 0 NOT NULL;

ALTER TABLE EQINVPROD ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE EQITRECMERC ADD PLACAVEICULO VARCHAR(10);

ALTER TABLE EQITRECMERC ADD NROFROTA VARCHAR(20);

ALTER TABLE EQITRECMERC ADD GARAGEM VARCHAR(20);

ALTER TABLE EQITRMA ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE EQPRODUTO ADD OBSPROD VARCHAR(10000);

ALTER TABLE EQPRODUTO ADD CODEMPOG INTEGER;

ALTER TABLE EQPRODUTO ADD CODFILIALOG SMALLINT;

ALTER TABLE EQPRODUTO ADD CODPRODOG INTEGER;

ALTER TABLE EQTIPOMOV ADD CODEMPTC INTEGER;

ALTER TABLE EQTIPOMOV ADD CODFILIALTC SMALLINT;

ALTER TABLE EQTIPOMOV ADD CODTIPOMOVTC INTEGER;

ALTER TABLE EQTIPOMOV ADD DESCNATCOMPL VARCHAR(60);

ALTER TABLE EQTIPOMOV ADD CODEMPMC INTEGER;

ALTER TABLE EQTIPOMOV ADD CODFILIALMC SMALLINT;

ALTER TABLE EQTIPOMOV ADD CODMENS INTEGER;

ALTER TABLE LFITCALCCUSTO ADD SEQITCALC SMALLINT NOT NULL;

ALTER TABLE LFITCLFISCAL ADD ADICICMSTOTNOTA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE LFITCLFISCAL ADD ALIQICMSSTCM NUMERIC(9,2) DEFAULT 0;

ALTER TABLE LFITCLFISCAL ADD CALCSTCM CHAR(1) DEFAULT 'N';

ALTER TABLE PPESTRUTURA ADD EXPEDIRRMA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE PPESTRUTURA ADD GERAROP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE PPITOP ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE PPOP ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGESTACAO ADD PATHCACERTS VARCHAR(256);

ALTER TABLE SGESTACAO ADD CODEMPPX INTEGER;

ALTER TABLE SGESTACAO ADD CODFILIALPX SMALLINT;

ALTER TABLE SGESTACAO ADD CODPROXY INTEGER;

ALTER TABLE SGOBJETO ADD NIVELOBJ SMALLINT DEFAULT -1 NOT NULL;

ALTER TABLE SGOBJETO ADD NUMREGOBJ INTEGER DEFAULT 0 NOT NULL;

ALTER TABLE SGPREFERE1 ADD CODEMPME INTEGER;

ALTER TABLE SGPREFERE1 ADD CODFILIALME SMALLINT;

ALTER TABLE SGPREFERE1 ADD CODMENSVENDA INTEGER;

ALTER TABLE SGPREFERE1 ADD TIPOEMISSAONFE CHAR(1);

ALTER TABLE SGPREFERE1 ADD CCNFECP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD ADICICMSTOTNOTA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD UTILIZATBCALCCA CHAR(1) DEFAULT 'N' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Utiliza tabela para cálculo do custo de aquisição.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='UTILIZATBCALCCA';

ALTER TABLE SGPREFERE1 ADD HABCOMPRACOMPL CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD CODEMPIC INTEGER;

ALTER TABLE SGPREFERE1 ADD CODFILIALIC SMALLINT;

ALTER TABLE SGPREFERE1 ADD CODTIPOMOVIC INTEGER;

ALTER TABLE SGPREFERE1 ADD DESCNATCOMPL VARCHAR(60);

ALTER TABLE SGPREFERE1 ADD HABLOGPAGAR CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD HABLOGRECEBER CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE3 ADD CODEMPT2 INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALT2 SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODTIPOCONT2 INTEGER;

ALTER TABLE SGPREFERE3 ADD CODEMPC2 INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALC2 SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODCONFEMAIL2 INTEGER;

ALTER TABLE SGPREFERE3 ADD CODEMPE2 INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALE2 SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODEMAILEN2 INTEGER;

ALTER TABLE SGPREFERE3 ADD EMAILNOTIF2 VARCHAR(250);

ALTER TABLE SGPREFERE5 ADD OPSEQ CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE5 ADD EXPEDIRRMA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE5 ADD VALIDAQTDOP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE5 ADD VALIDAFASEOP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE5 ADD EDITQTDOP CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE VDFRETEVD ADD RNTCVD VARCHAR(20);

ALTER TABLE VDITVENDA ADD CALCSTCM CHAR(1) DEFAULT 'N';

ALTER TABLE VDORCAMENTO ADD DTPREVENTORC DATE;

ALTER TABLE VDORCAMENTO ADD HPREVENTORC TIME;

ALTER TABLE VDTRANSP ADD RNTCTRAN VARCHAR(20);

ALTER TABLE VDVEICULO ADD RNTC VARCHAR(20);

ALTER TABLE VDVENDA ADD SUBTIPOVENDA CHAR(2) DEFAULT 'NF' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Subtipo da venda - NF = Nota fiscal / NC = Nota complementar.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='SUBTIPOVENDA';

ALTER TABLE VDVENDA ADD CNF BIGINT DEFAULT 0;

ALTER TABLE VDVENDA ADD SITCOMPLVENDA CHAR(1) DEFAULT 'N' NOT NULL;

/* Create Table... */
CREATE TABLE CPCOMPRALCCHAVE(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL INTEGER NOT NULL,
CODCOMPRA INTEGER NOT NULL,
DTCONSULTA DATE NOT NULL,
HCONSULTA TIME NOT NULL,
CODRETORNO INTEGER NOT NULL,
MENSAGEM VARCHAR(2000) NOT NULL,
CHAVEVALIDA CHAR(1) DEFAULT 'N' NOT NULL,
CHAVENFE VARCHAR(100) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);


CREATE TABLE CPIMPORTACAOCOMPL(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODIMP INTEGER NOT NULL,
DESCADIC VARCHAR(50) NOT NULL,
VLRDESPADIC NUMERICDN DEFAULT 0.00 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE CPITCOMPRAITCOMPRA(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODCOMPRA INTEGER NOT NULL,
CODITCOMPRA INTEGER NOT NULL,
CODEMPCO INTEGER,
CODFILIALCO SMALLINT,
CODCOMPRACO INTEGER NOT NULL,
CODITCOMPRACO INTEGER NOT NULL,
QTDITCOMPRA DECIMAL(15,5) DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);


CREATE TABLE CRFEEDBACK(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
NOME VARCHAR(128) NOT NULL,
CODEMPPE INTEGER NOT NULL,
CODFILIALPE SMALLINT NOT NULL,
EMAILPESSOA VARCHAR(128) NOT NULL,
ID_MOTIVO BIGINT NOT NULL,
MENSAGEM VARCHAR(10000) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);


CREATE TABLE CRMOTIVOFB(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
DESCRICAO VARCHAR(128) NOT NULL,
TIPO CHAR(2) DEFAULT '00' NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(128) DEFAULT USER);


CREATE TABLE CRPESSOA(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
EMAILPESSOA VARCHAR(128) NOT NULL,
NOMEPESSOA VARCHAR(60) NOT NULL,
SENHAPESSOA VARCHAR(128) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL);


CREATE TABLE NECONTINGENCIA(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
DTENTRADA DATE NOT NULL,
HENTRADA TIME NOT NULL,
DTSAIDA DATE,
HSAIDA TIME,
TIPOEMISSAONFE CHAR(1) NOT NULL,
JUSTIFICATIVA VARCHAR(256) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(128) DEFAULT USER);


CREATE TABLE SGLOGCRUD(ID BIGINT NOT NULL,
TABLENAME VARCHAR(100) NOT NULL,
OPERATION CHAR(1) DEFAULT 'U' NOT NULL,
EVENTLOG CHAR(1) DEFAULT 'B' NOT NULL,
DTLOG DATE DEFAULT 'now' NOT NULL,
HLOG TIME DEFAULT 'now' NOT NULL,
IDUSU VARCHAR(128) DEFAULT user NOT NULL,
XML BLOB SUB_TYPE 1 SEGMENT SIZE 4096 NOT NULL);


Update Rdb$Relations set Rdb$Description =
'Log das ações de insert, update e delete.'
where Rdb$Relation_Name='SGLOGCRUD';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificação'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='ID';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome da tabela'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='TABLENAME';

Update Rdb$Relation_Fields set Rdb$Description =
'Operação:
U - Update
D - Delete
I - Insert
'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='OPERATION';

Update Rdb$Relation_Fields set Rdb$Description =
'Evento:
B - Before antes da operacão
A - After após a operação'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='EVENTLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da operação'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='DTLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da operação'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='HLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuário'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='IDUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Conteúdo do registro antes ou depois da alteração.'
where Rdb$Relation_Name='SGLOGCRUD' and Rdb$Field_Name='XML';

CREATE TABLE SGPROXYWEB(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODPROXY INTEGER NOT NULL,
DESCPROXY VARCHAR(60) NOT NULL,
HOSTPROXY VARCHAR(100) NOT NULL,
PORTAPROXY INTEGER NOT NULL,
AUTPROXY CHAR(1) DEFAULT 'N' NOT NULL,
USUPROXY VARCHAR(128),
SENHAPROXY VARCHAR(128),
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL);


CREATE TABLE SGSEQUENCE_ID(TABLE_NAME VARCHAR(128) NOT NULL,
SEQ_ID BIGINT DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE SGSQUID_ACTION(ID BIGINT NOT NULL,
SIGLA VARCHAR(40) NOT NULL,
DESCRICAO VARCHAR(250) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE SGSQUID_HIER(ID BIGINT NOT NULL,
SIGLA VARCHAR(40) NOT NULL,
DESCRICAO VARCHAR(250) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE SGSQUID_LOG(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
DTLOG DATE NOT NULL,
HLOG TIME NOT NULL,
DURATION INTEGER NOT NULL,
CLIENTADDRESS VARCHAR(30) NOT NULL,
ID_ACTION BIGINT NOT NULL,
BYTES INTEGER NOT NULL,
ID_METHOD BIGINT NOT NULL,
URL VARCHAR(500) NOT NULL,
AUTENTICATION VARCHAR(128),
ID_HIER BIGINT,
TYPES VARCHAR(250),
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE SGSQUID_METHOD(ID BIGINT NOT NULL,
SIGLA VARCHAR(40) NOT NULL,
DESCRICAO VARCHAR(250) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE TKCONTPESSOA(CODEMPCTO INTEGER NOT NULL,
CODFILIALCTO SMALLINT NOT NULL,
CODCTO INTEGER NOT NULL,
CODEMPPE INTEGER NOT NULL,
CODFILIALPE SMALLINT NOT NULL,
EMAILPESSOA VARCHAR(128) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE VDITVENDAITVENDA(ID BIGINT NOT NULL,
CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
TIPOVENDA CHAR(1) DEFAULT 'V' NOT NULL,
CODVENDA INTEGER NOT NULL,
CODITVENDA INTEGER NOT NULL,
CODEMPVO INTEGER NOT NULL,
CODFILIALVO SMALLINT NOT NULL,
TIPOVENDAVO CHAR(1) DEFAULT 'V' NOT NULL,
CODVENDAVO INTEGER NOT NULL,
CODITVENDAVO INTEGER NOT NULL,
QTDITVENDA DECIMAL(15,5) DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);



/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE ATRESUMOATENDOSP01(CODEMPP INTEGER,
CODFILIALP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(ANO SMALLINT,
MES SMALLINT,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
QTDCONTR DECIMAL(15,5),
DTINICIO DATE,
VALOR DECIMAL(15,5),
VALOREXCEDENTE DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
QTDHORAS DECIMAL(15,2),
MESCOB INTEGER,
SALDOMESCOB DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
SALDOPERIODO DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
EXCEDENTEPERIODO DECIMAL(15,5),
EXCEDENTECOB DECIMAL(15,5),
VALOREXCEDENTECOB DECIMAL(15,5),
VALORTOTALCOB DECIMAL(15,5),
VALORCONTR DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
TOTFRANQUIAMES DECIMAL(15,5))
 AS
 BEGIN EXIT; END
^

CREATE PROCEDURE ATRESUMOATENDOSP02(CODEMPCLP INTEGER,
CODFILIALCLP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
RAZCLI VARCHAR(60),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODITCONTR INTEGER,
CODCONTR INTEGER,
DESCCONTR VARCHAR(100),
VLRHORA DECIMAL(15,5),
VLRHORAEXCED DECIMAL(15,5),
QTDCONTR DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
VLRCOB DECIMAL(15,5),
VLRCOBEXCED DECIMAL(15,5),
VLRCOBTOT DECIMAL(15,5),
MES SMALLINT,
ANO SMALLINT,
QTDHORAS DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
ACUMULO SMALLINT,
CDATAINI DATE)
 AS
 BEGIN EXIT; END
^

CREATE PROCEDURE LFCALCCUSTOSP01(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCALC INTEGER,
QTDADE DECIMAL(15,5),
VLRCUSTOP DECIMAL(15,5),
VLRICMS DECIMAL(15,5),
VLRIPI DECIMAL(15,5),
VLRPIS DECIMAL(15,5),
VLRCOFINS DECIMAL(15,5),
VLRISS DECIMAL(15,5),
VLRFUNRURAL DECIMAL(15,5),
VLRII DECIMAL(15,5),
VLRIR DECIMAL(15,5),
VLRTXSISCOMEX DECIMAL(15,5),
VLRICMSDIFERIDO DECIMAL(15,5),
VLRICMSPRESUMIDO DECIMAL(15,5),
VLRCOMPL DECIMAL(15,5))
 RETURNS(VLRCUSTO DECIMAL(15,5))
 AS
 BEGIN EXIT; END
^

CREATE PROCEDURE SGGERACNFSP(TIPO VARCHAR(2),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TIPOVENDA VARCHAR(2),
CODVENDA INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER)
 AS
 BEGIN EXIT; END
^

CREATE PROCEDURE SGOBJETOATUALIZANIVELSP(CODEMP INTEGER)
 RETURNS(IDOBJ VARCHAR(30),
NIVELOBJ SMALLINT)
 AS
 BEGIN EXIT; END
^

CREATE PROCEDURE SGSEQUENCE_IDSP(STABLE_NAME VARCHAR(128))
 RETURNS(BISEQ BIGINT)
 AS
 BEGIN EXIT; END
^


/* AssignEmptyBody proc */
ALTER PROCEDURE VDCONTRATOTOTSP(CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR INTEGER,
CODEMPSC INTEGER,
CODFILIALSC SMALLINT,
CODCONTRSC INTEGER,
CODEMPTA INTEGER,
CODFILIALTA SMALLINT,
CODTAREFA INTEGER,
CODEMPST INTEGER,
CODFILIALST SMALLINT,
CODTAREFAST INTEGER,
DATAINI DATE,
DATAFIM DATE)
 RETURNS(TOTALPREVGERAL NUMERIC(15,5),
TOTALPREVPER NUMERIC(15,5),
TOTALGERAL NUMERIC(15,5),
TOTALCOBCLIGERAL NUMERIC(15,5),
TOTALANT NUMERIC(15,5),
TOTALCOBCLIANT NUMERIC(15,5),
TOTALPER NUMERIC(15,5),
TOTALCOBCLIPER NUMERIC(15,5),
SALDOANT NUMERIC(15,5),
SALDOPER NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

DROP VIEW ATATENDIMENTOVW06;

DROP VIEW ATATENDIMENTOVW03;

DROP VIEW ATATENDIMENTOVW08;

DROP VIEW ATATENDIMENTOVW02;

DROP VIEW ATATENDIMENTOVW04;

DROP VIEW ATATENDIMENTOVW01;

/* Create view: ATATENDIMENTOVW01 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW01(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
PARTPREMIATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
COEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
DESCCONTR,
CODITCONTR,
CODEMPTA,
CODFILIALTA,
CODTAREFA,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
QTDCONTR,
QTDITCONTR,
VLRITCONTR,
VLRITCONTREXCED,
DTINICIO,
STATUSATENDO,
RAZCLI,
NOMECLI,
CODCLI,
CODEMPCL,
CODFILIALCL,
CODEMPCH,
CODFILIALCH,
CODCHAMADO,
DESCCHAMADO,
CODEMPTO,
CODFILIALTO,
CODTPATENDO,
DESCTPATENDO,
OBSATENDO,
DATAATENDO,
DATAATENDOFIN,
HORAATENDO,
HORAATENDOFIN,
PGCOMIESPEC,
COBCLIESPEC,
CONTMETAESPEC,
MRELCOBESPEC,
BHESPEC,
TEMPOMINCOBESPEC,
TEMPOMAXCOBESPEC,
PERCCOMIESPEC,
TOTALMIN,
SITREVATENDO,
SITCONTR,
DESCSITCONTR,
DTPREVFIN,
TIPOATENDO,
DOCATENDO)
 AS 
select a.codemp, a.codfilial, a.codatendo
  , a.codempae, a.codfilialae, a.codatend, ate.nomeatend, ate.partpremiatend, ate.codempep, codfilialep, matempr
  , a.codempea, a.codfilialea, a.codespec, e.descespec
  , a.codempct, a.codfilialct, a.codcontr, ct.desccontr, a.coditcontr 
  , a.codempta, a.codfilialta, a.codtarefa, ct.tpcobcontr
  , extract(year from a.dataatendo) anoatendo, extract(month from a.dataatendo) mesatendo
  , ( select sum(qtditcontr) from vditcontrato ics 
     where ics.codemp=a.codempct and ics.codfilial=a.codfilialct 
     and ics.codcontr=a.codcontr and ics.franquiaitcontr='S') qtdcontr 
  , ict.qtditcontr, ict.vlritcontr, ict.vlritcontrexced, ct.dtinicio
  , a.statusatendo, c.razcli, c.nomecli, c.codcli, c.codemp, c.codfilial
  , a.codempch, a.codfilialch, a.codchamado, ch.descchamado
  , a.codempto, a.codfilialto, a.codtpatendo, ta.desctpatendo
  , a.obsatendo, a.dataatendo, a.dataatendofin, a.horaatendo, a.horaatendofin
  , e.pgcomiespec, e.cobcliespec, e.contmetaespec, e.mrelcobespec, e.bhespec
  , e.tempomincobespec, e.tempomaxcobespec, e.perccomiespec, ((a.horaatendofin-a.horaatendo) / 60) TOTALMIN
  , a.sitrevatendo
  , ct.sitcontr, ct.descsitcontr, ct.dtprevfin, ta.tipoatendo, a.docatendo
from atatendente ate, atespecatend e, vdcliente c, attipoatendo ta, atatendimento a
left outer join crchamado ch on 
ch.codemp=a.codempch and ch.codfilial=a.codfilialch and ch.codchamado=a.codchamado 
left outer join vdcontrato ct on
ct.codemp=a.codempct and ct.codfilial=a.codfilialct and ct.codcontr=a.codcontr
left outer join vditcontrato ict on
ict.codemp=a.codempct and ict.codfilial=a.codfilialct and ict.codcontr=a.codcontr and ict.coditcontr=a.coditcontr
where ate.codemp=a.codempae and ate.codfilial=a.codfilialae and ate.codatend=a.codatend and
e.codemp=a.codempea and e.codfilial=a.codfilialea and e.codespec=a.codespec and 
c.codemp=a.codempcl and c.codfilial=a.codfilialcl and c.codcli=a.codcli and
ta.codemp=a.codempto and ta.codfilial=a.codfilialto and ta.codtpatendo=a.codtpatendo
;

DROP VIEW ATATENDIMENTOVW07;

/* Create view: ATATENDIMENTOVW07 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW07(
CODEMPCL,
CODFILIALCL,
CODCLI,
RAZCLI,
CODEMPCT,
CODFILIALCT,
CODCONTR,
DESCCONTR,
DESCSITCONTR,
SITCONTR,
TPCONTR,
TPCOBCONTR,
QTDCONTR,
TOTHORAS)
 AS 
select ct.codempcl, ct.codfilialcl, ct.codcli, cl.razcli,
 ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr, ct.desccontr,
 ct.descsitcontr, ct.sitcontr, ct.tpcontr, ct.tpcobcontr,
 sum(it.qtditcontr) qtdcontr, sum((select sum( (a.horaatendofin-a.horaatendo) / 60/60) 
 from atatendimento a 
 where a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr and
 a.coditcontr=it.coditcontr
  ))  tothoras
 from vdcliente cl, vditcontrato it, vdcontrato ct
  where it.codemp=ct.codemp and it.codfilial=ct.codfilial and it.codcontr=ct.codcontr and
 cl.codemp=ct.codempcl and cl.codfilial=ct.codfilialcl and cl.codcli=ct.codcli 
  group by ct.codempcl, ct.codfilialcl, ct.codcli, cl.razcli,
 ct.codemp, ct.codfilial, ct.codcontr, ct.desccontr,
 ct.descsitcontr, ct.sitcontr, ct.tpcontr, ct.tpcobcontr
;

DROP VIEW FNFLUXOCAIXAVW01;

/* Create view: FNFLUXOCAIXAVW01 (ViwData.CreateDependDef) */
CREATE VIEW FNFLUXOCAIXAVW01(
ORDEM,
TIPOLANCA,
SUBTIPO,
CODEMP,
CODFILIAL,
CODRECPAGLANC,
NPARCRECPAGLANC,
DTEMISSAO,
DTCOMP,
DTVENCTORECPAG,
DOC,
CODIGO,
RAZAO,
HISTORICO,
VALOR)
 AS 
select cast(1 as smallint) ordem,
'L' tipolanca,
(case when sl.codfor is null and sl.codcli is null then 'A' when sl.codfor is null then 'C' else 'F' end) subtipo,
sl.codemp, sl.codfilial, sl.codlanca codrecpaglanc, sl.codsublanca nparcrecpaglanc,
sl.dtcompsublanca dtemissao,
sl.dtcompsublanca dtcomp,
sl.datasublanca dtvenctorecpag,
l.doclanca doc,
(case when sl.codfor is null then sl.codcli else sl.codfor end) codigo,
(case when sl.codfor is null then cl.razcli else fl.razfor end) razao,
sl.histsublanca historico,
(sl.vlrsublanca*-1) valor
from fnlanca l, fnsublanca sl
left outer join vdcliente cl on
cl.codemp=sl.codempcl and cl.codfilial=sl.codfilialcl and cl.codcli=sl.codcli 
left outer join cpforneced fl on
fl.codemp=sl.codempfr and fl.codfilial=sl.codfilialfr and fl.codfor=sl.codfor
where l.codemp=sl.codemp and l.codfilial=sl.codfilial and l.codlanca=sl.codlanca and
sl.codpag is null and sl.codrec is null and sl.codsublanca<>0 and l.transflanca = 'N'
union all
select cast(2 as smallint) ordem,
'R' tipolanca,
'R' subtipo,
ir.codemp, ir.codfilial, ir.codrec codrecpaglanc, ir.nparcitrec nparcrecpaglanc,
r.datarec dtemissao,
ir.dtcompitrec dtcomp,
ir.dtvencitrec dtvenctorecpag,
ir.doclancaitrec doc,
r.codcli codigo,
c.razcli razao,
ir.obsitrec historico,
ir.vlrapagitrec valor
from fnreceber r, vdcliente c, fnitreceber ir
where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec and
c.codemp=r.codempcl and c.codfilial=r.codfilialcl and c.codcli=r.codcli and
ir.vlrapagitrec<>0 and ir.statusitrec not in ('CR')
union all
select cast(2 as smallint) ordem,
'R' tipolanca,
'L' subtipo,
ir.codemp, ir.codfilial, ir.codrec codrecpaglanc, ir.nparcitrec nparcrecpaglanc,
r.datarec dtemissao,
slr.dtcompsublanca dtcomp,
slr.datasublanca dtvenctorecpag,
lr.doclanca doc,
r.codcli codigo,
c.razcli razao,
slr.histsublanca historico,
slr.vlrsublanca*-1 valor
from fnreceber r, vdcliente c, fnitreceber ir, fnsublanca slr, fnlanca lr
where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec and
c.codemp=r.codempcl and c.codfilial=r.codfilialcl and c.codcli=r.codcli and
slr.codemprc=ir.codemp and slr.codfilialrc=ir.codfilial and slr.codrec=ir.codrec and slr.nparcitrec=ir.nparcitrec and
slr.codsublanca<>0 and
lr.codemp=slr.codemp and lr.codfilial=slr.codfilial and lr.codlanca=slr.codlanca and lr.transflanca = 'N'
union all
select cast(3 as smallint) ordem,
'P' tipolanca,
'P' subtipo,
ip.codemp, ip.codfilial, ip.codpag codrecpaglanc, ip.nparcpag nparcrecpaglanc,
p.datapag dtemissao,
ip.dtcompitpag dtcomp,
ip.dtvencitpag dtvenctorecpag,
ip.doclancaitpag doc,
f.codfor codigo,
f.razfor razao,
ip.obsitpag historico,
ip.vlrapagitpag*-1 valor
from fnpagar p, cpforneced f, fnitpagar ip
where ip.codemp=p.codemp and ip.codfilial=p.codfilial and ip.codpag=p.codpag and
f.codemp=p.codempfr and f.codfilial=p.codfilialfr and f.codfor=p.codfor and
ip.vlrapagitpag<>0 and ip.statusitpag not in ('CP')
union all
select cast(3 as smallint) ordem,
'P' tipolanca,
'L' subtipo,
ip.codemp, ip.codfilial, ip.codpag codrecpaglanc, ip.nparcpag nparcrecpaglanc,
p.datapag dtemissao,
slp.dtcompsublanca dtcomp,
slp.datasublanca dtvenctorecpag,
ip.doclancaitpag doc,
f.codfor codigo,
f.razfor razao,
slp.histsublanca historico,
slp.vlrsublanca*-1 valor
from fnpagar p, cpforneced f, fnitpagar ip, fnsublanca slp, fnlanca lp
where ip.codemp=p.codemp and ip.codfilial=p.codfilial and ip.codpag=p.codpag and
f.codemp=p.codempfr and f.codfilial=p.codfilialfr and f.codfor=p.codfor and
slp.codemppg=ip.codemp and slp.codfilialpg=ip.codfilial and slp.codpag=ip.codpag and slp.nparcpag=ip.nparcpag and slp.codsublanca<>0 and
lp.codemp=slp.codemp and lp.codfilial=slp.codfilial and lp.codlanca=slp.codlanca  and lp.transflanca = 'N'
;

/* Create view: TKCONTCLIVW01 (ViwData.CreateDependDef) */
CREATE VIEW TKCONTCLIVW01(
TIPOCTO,
CODEMP,
CODFILIAL,
CODCTO,
RAZCTO,
NOMECTO,
CONTCTO,
EMAILCTO,
OBSCTO,
CODEMPTO,
CODFILIALTO,
CODTIPOCONT,
CODEMPSR,
CODFILIALSR,
CODSETOR,
CODEMPOC,
CODFILIALOC,
CODORIGCONT,
CODEMPTI,
CODFILIALTI,
CODTIPOCLI,
CODEMPCC,
CODFILIALCC,
CODCLASCLI,
ATIVO,
DTINS,
DTALT,
DTINSCC,
DTALTCC)
 AS 
select cast('O' as char(1)) tipocto,  co.codemp, co.codfilial, co.codcto,
co.razcto, co.nomecto, co.contcto, co.emailcto, co.obscto,
co.codempto, co.codfilialto, co.codtipocont,
co.codempsr, co.codfilialsr, co.codsetor,
co.codempoc, co.codfilialoc, co.codorigcont,
co.codempti, co.codfilialti, co.codtipocli,
cast(null as integer) codempcc, cast(null as smallint) codfilialcc, cast(null as integer) codclascli,
co.ativocto,
co.dtins, co.dtalt,
max(cc.dtins) dtinscc,
max(cc.dtalt) dtaltcc
from tkcontato co
left outer join tkcampanhacto cc on
cc.codempco=co.codemp and cc.codfilialco=co.codfilial and
cc.codcto=co.codcto
group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
, 15, 16, 17, 18, 19, 20
, 21, 22, 23, 24, 25, 26, 27
;

/* Create Views... */
/* Create view: ATATENDIMENTOVW02 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW02(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
PARTPREMIATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
CODEMPTA,
CODFILIALTA,
CODTAREFA,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
QTDCONTR,
QTDITCONTR,
VLRITCONTR,
VLRITCONTREXCED,
DTINICIO,
STATUSATENDO,
RAZCLI,
NOMECLI,
CODCLI,
CODEMPCL,
CODFILIALCL,
CODEMPCH,
CODFILIALCH,
CODCHAMADO,
DESCCHAMADO,
CODEMPTO,
CODFILIALTO,
CODTPATENDO,
DESCTPATENDO,
OBSATENDO,
DATAATENDO,
DATAATENDOFIN,
HORAATENDO,
HORAATENDOFIN,
PGCOMIESPEC,
COBCLIESPEC,
CONTMETAESPEC,
MRELCOBESPEC,
BHESPEC,
TEMPOMINCOBESPEC,
TEMPOMAXCOBESPEC,
PERCCOMIESPEC,
TOTALMIN,
TOTALGERAL,
TOTALMETA,
TOTALCOMIS,
TOTALCOBCLI,
TOTALBH,
SITREVATENDO,
TIPOATENDO,
DOCATENDO)
 AS 
select A.CODEMP, A.CODFILIAL, A.CODATENDO, 
A.CODEMPAE, A.CODFILIALAE, A.CODATEND, A.NOMEATEND, A.PARTPREMIATEND, A.CODEMPEP, CODFILIALEP, MATEMPR,
A.COEMPEA, A.CODFILIALEA, A.CODESPEC, A.DESCESPEC, 
 A.CODEMPCT, A.CODFILIALCT, A.CODCONTR,  A.CODITCONTR, A.CODEMPTA, A.CODFILIALTA, A.CODTAREFA, A.TPCOBCONTR,
 A.ANOATENDO, A.MESATENDO, A.QTDCONTR,
 A.QTDITCONTR, A.VLRITCONTR, A.VLRITCONTREXCED, A.DTINICIO,
 A.STATUSATENDO, A.RAZCLI, A.NOMECLI, A.CODCLI,
  A.CODEMPCL, A.CODFILIALCL, A.CODEMPCH, A.CODFILIALCH, A.CODCHAMADO, A.DESCCHAMADO,
  A.CODEMPTO, A.CODFILIALTO, A.CODTPATENDO,
   A.DESCTPATENDO, A.OBSATENDO, A.DATAATENDO, A.DATAATENDOFIN, A.HORAATENDO, A.HORAATENDOFIN,
    A.PGCOMIESPEC, A.COBCLIESPEC, A.CONTMETAESPEC, A.MRELCOBESPEC, A.BHESPEC, A.TEMPOMINCOBESPEC, A.TEMPOMAXCOBESPEC,
     A.PERCCOMIESPEC, A.TOTALMIN, 
  (a.totalmin) / 60  totalgeral, 
(( (case when a.contmetaespec='S' then (case when 
a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalmeta, 
(( (case when a.pgcomiespec='S' then (case when a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalcomis, 
(( (case when a.cobcliespec='S' and a.statusatendo<>'NC' then (case when a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalcobcli,
( (case when a.bhespec='S' then a.totalmin else 0 end)/60 ) totalbh,
a.sitrevatendo, a.tipoatendo, a.docatendo
from atatendimentovw01 a
;

/* Create view: ATATENDIMENTOVW03 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW03(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
PARTPREMIATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
NOMEEMPR,
DATAATENDO,
HORAATENDO,
HORAATENDOFIN,
CODEMPTO,
CODFILIALTO,
CODTURNO,
DESCTURNO,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
PERCCOMIESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
CODEMPTA,
CODFILIALTA,
CODTAREFA,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
HORASEXPED,
TOTALCOMIS,
TOTALMIN,
TOTALGERAL,
TOTALBH,
SITREVATENDO,
TIPOATENDO,
DOCATENDO)
 AS 
select a.codemp, a.codfilial, a.codatendo, 
    a.codempae, a.codfilialae, a.codatend, a.nomeatend, a.partpremiatend,
     a.codempep, a.codfilialep, a.matempr, e.nomeempr,
     a.dataatendo, a.horaatendo, a.horaatendofin,
    e.codempto, e.codfilialto, e.codturno, t.descturno,
    a.codempea, a.codfilialea, a.codespec, a.descespec, a.perccomiespec,
    a.codempct, a.codfilialct, a.codcontr, a.coditcontr,
    a.codempta, a.codfilialta, a.codtarefa, 
    a.tpcobcontr,
    a.anoatendo, a.mesatendo, 
    x.horasexped, a.totalcomis, a.totalmin, a.totalgeral,
    ( a.totalbh * ( 1 +  
       ((case when extract(weekday from a.dataatendo)=6 then t.percbhtbsabturno 
          when extract(weekday from a.dataatendo)=0 then t.percbhtbdomturno
          when coalesce(f.trabfer,'N')='S' then t.percbhtbferturno
          else 0 end
       )/100 ) )
    ) totalbh,
    a.sitrevatendo, a.tipoatendo, a.docatendo
    from atatendimentovw02 a
    left outer join rhempregado e on
    e.codemp=a.codempep and e.codfilial=a.codfilialep and e.matempr=a.matempr
    left outer join rhturno t on
    t.codemp=e.codempto and t.codfilial=e.codfilialto and t.codturno=e.codturno
    left outer join rhexpedmes x on
    x.codemp=e.codempto and x.codfilial=e.codfilialto and x.codturno=e.codturno and 
    x.anoexped=extract(year from a.dataatendo) and x.mesexped=extract(month from a.dataatendo)
    left outer join sgferiado f on
    f.codemp=a.codemp and f.codfilial=a.codfilial and f.datafer=a.dataatendo
;

/* Create view: ATATENDIMENTOVW04 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW04(
DATAATENDO,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
ANOATENDO,
MESATENDO,
TOTALHORASTRAB)
 AS 
select a.dataatendo, a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.anoatendo, a.mesatendo, sum(totalmin)/60 totalhorastrab
from atatendimentovw01 a
group by a.dataatendo, a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.anoatendo, a.mesatendo
;

/* Create view: ATATENDIMENTOVW06 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW06(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
NOMEEMPR,
DATAATENDO,
HORAATENDO,
HORAATENDOFIN,
CODEMPTO,
CODFILIALTO,
CODTURNO,
DESCTURNO,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
PERCCOMIESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
HORASEXPED,
TOTALCOMIS,
TOTALGERAL,
TOTALBH,
TOTALHORASTRAB,
VLRLIQITVENDA,
SITREVATENDO)
 AS 
select a.codemp, a.codfilial, a.codatendo, a.codempae, a.codfilialae, a.codatend, 
a.nomeatend, a.codempep, a.codfilialep, a.matempr, a.nomeempr, a.dataatendo, 
a.horaatendo, a.horaatendofin, a.codempto, a.codfilialto, a.codturno, a.descturno,
a.codempea, a.codfilialea, a.codespec, a.descespec, perccomiespec,
a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.tpcobcontr,
a.anoatendo, a.mesatendo, 
a.horasexped, a.totalcomis, a.totalgeral, a.totalbh,
 ( case when a.tpcobcontr='ES' then ( select s1.totalhorastrab from atatendimentovw04 s1
        where s1.codempct=a.codempct and s1.codfilialct=a.codfilialct and 
        s1.codcontr=a.codcontr and s1.coditcontr=a.coditcontr ) 
   else ( select s1.totalhorastrab from atatendimentovw04 s1
        where s1.codempct=a.codempct and s1.codfilialct=a.codfilialct and 
        s1.codcontr=a.codcontr and s1.coditcontr=a.coditcontr and 
        s1.anoatendo=a.anoatendo and s1.mesatendo=a.mesatendo ) end) totalhorastrab, 
  ( case when a.tpcobcontr='ES' then ( select s2.vlrliqitvenda from atatendimentovw05 s2
        where s2.codempct=a.codempct and s2.codfilialct=a.codfilialct and 
         s2.codcontr=a.codcontr and s2.coditcontr=a.coditcontr)
        else ( select s2.vlrliqitvenda from atatendimentovw05 s2
        where s2.codempct=a.codempct and s2.codfilialct=a.codfilialct and 
         s2.codcontr=a.codcontr and s2.coditcontr=a.coditcontr and
         a.dataatendo between s2.dtiniapura and s2.dtfinapura) end ) vlrliqitvenda,
   a.sitrevatendo
from atatendimentovw03 a
;

/* Create view: ATATENDIMENTOVW08 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW08(
DATAATENDO,
DTFINCONTR,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
DESCCONTR,
DESCITCONTR,
TPCOBCONTR,
SITCONTR,
TOTALCOMIS)
 AS 
select a.dataatendo, fn.dtfincontr, a.codempae, a.codfilialae, a.codatend, a.nomeatend,
a.codempct, a.codfilialct, a.codcontr, a.coditcontr, ct.desccontr, ic.descitcontr,
ct.tpcobcontr, ct.sitcontr,
sum(a.totalcomis) totalcomis
from atatendimentovw02 a, vditcontrato ic,  vdcontrato ct
left outer join vdfincontr fn
on fn.codemp=ct.codemp and fn.codfilial=ct.codfilial and
fn.codcontr=ct.codcontr
where ct.codemp=a.codempct and ct.codfilial=a.codfilialct and ct.codcontr=a.codcontr and
ic.codemp=a.codempct and ic.codfilial=a.codfilialct and ic.codcontr=a.codcontr and
ic.coditcontr=a.coditcontr and ct.recebcontr='S' and
( (ct.tpcobcontr='ES' and fn.dtfincontr is not null) or (ct.tpcobcontr='ME') )
group by a.dataatendo, fn.dtfincontr, a.codempae, a.codfilialae, a.codatend, a.nomeatend,
a.codempct, a.codfilialct, a.codcontr, a.coditcontr, ct.desccontr, ic.descitcontr,
ct.tpcobcontr, ct.sitcontr
;

/* Create view: TKCONTCLIVW02 (ViwData.CreateDependDef) */
CREATE VIEW TKCONTCLIVW02(
TIPOCTO,
CODEMP,
CODFILIAL,
CODCTO,
RAZCTO,
NOMECTO,
CONTCTO,
EMAILCTO,
OBSCTO,
CODEMPTO,
CODFILIALTO,
CODTIPOCONT,
CODEMPSR,
CODFILIALSR,
CODSETOR,
CODEMPOC,
CODFILIALOC,
CODORIGCONT,
CODEMPTI,
CODFILIALTI,
CODTIPOCLI,
CODEMPCC,
CODFILIALCC,
CODCLASCLI,
ATIVO,
DTINS,
DTALT,
DTINSCC,
DTALTCC)
 AS 
select cast('C' as char(1)) tipocto, cl.codemp,  cl.codfilial, cl.codcli,
cl.razcli razcto, cl.nomecli nomecto, cl.contcli contcto, cl.emailcli emailcto, cl.obscli obscto,
cast(0 as integer) codempto, cast(0 as smallint) codfilialto, cast(0 as integer) codtipocont,
cl.codempsr, cl.codfilialsr, cl.codsetor,
cast(null as integer) codempoc, cast(null as smallint) codfilialoc, cast(null as integer) codorigcont,
cl.codempti, cl.codfilialti, cl.codtipocli,
cl.codempcc, cl.codfilialcc, cl.codclascli,
cl.ativocli,
cl.dtins, cl.dtalt,
max(cc.dtins) dtinscc,
max(cc.dtalt) dtaltcc
from vdcliente cl
left outer join tkcampanhacto cc on
cc.codempcl=cl.codemp and cc.codfilialcl=cl.codfilial and
cc.codcli=cl.codcli
group by 1,2, 3, 4, 5, 6, 7, 8, 9
, 10, 11, 12
, 13, 14 , 15, 16, 17, 18, 19, 20
, 21, 22, 23, 24, 25, 26, 27
;

/* Create view: TKCONTCLIVW03 (ViwData.CreateDependDef) */
CREATE VIEW TKCONTCLIVW03(
TIPOCTO,
CODEMP,
CODFILIAL,
CODCTO,
RAZCTO,
NOMECTO,
CONTCTO,
EMAILCTO,
OBSCTO,
CODEMPTO,
CODFILIALTO,
CODTIPOCONT,
CODEMPSR,
CODFILIALSR,
CODSETOR,
CODEMPOC,
CODFILIALOC,
CODORIGCONT,
CODEMPTI,
CODFILIALTI,
CODTIPOCLI,
CODEMPCC,
CODFILIALCC,
CODCLASCLI,
ATIVO,
DTINS,
DTALT,
DTINSCC,
DTALTCC)
 AS 
select tipocto, codemp, codfilial, codcto
   , razcto, nomecto, contcto, emailcto, obscto
   , codempto, codfilialto, codtipocont
   , codempsr , codfilialsr, codsetor
   , codempoc, codfilialoc, codorigcont
   , codempti, codfilialti, codtipocli
   , codempcc, codfilialcc, codclascli
   , ativo
   , dtins,  dtalt, dtinscc, dtaltcc
   from tkcontclivw01
  union all
   select tipocto, codemp, codfilial, codcto
   , razcto, nomecto, contcto, emailcto, obscto
   , codempto, codfilialto, codtipocont
   , codempsr , codfilialsr, codsetor
   , codempoc, codfilialoc, codorigcont
   , codempti, codfilialti, codtipocli
   , codempcc, codfilialcc, codclascli
   , ativo
   , dtins,  dtalt, dtinscc, dtaltcc
      from tkcontclivw02
;


/* Create Exception... */
CREATE EXCEPTION PPESTRUTURAEX02 'NÃO FOI POSSIVEL ATIVAR A ESTRUTURA, INSIRA UM SUBPRODUTO!';

CREATE EXCEPTION VDVENDAEX07 'VENDA BLOQUEADA !';


/* Create Primary Key... */
ALTER TABLE CPCOMPRALCCHAVE ADD CONSTRAINT CPCOMPRALCCHAVEPK PRIMARY KEY (ID);

ALTER TABLE CPIMPORTACAOCOMPL ADD CONSTRAINT CPIMPORTACAOCOMPLPK PRIMARY KEY (ID);

ALTER TABLE CPITCOMPRA ADD CONSTRAINT CPITCOMPRAPK PRIMARY KEY (CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP);

ALTER TABLE CPITCOMPRAITCOMPRA ADD CONSTRAINT CPITCOMPRACPITCOMPRAPK PRIMARY KEY (ID);

ALTER TABLE CRFEEDBACK ADD CONSTRAINT CRFEEDBACKPK PRIMARY KEY (ID);

ALTER TABLE CRMOTIVOFB ADD CONSTRAINT CRMOTIVOFBPK PRIMARY KEY (ID);

ALTER TABLE CRPESSOA ADD CONSTRAINT CRPESSOAPK PRIMARY KEY (EMAILPESSOA,CODFILIAL,CODEMP);

ALTER TABLE LFITCALCCUSTO ADD CONSTRAINT LFITCALCCUSTOPK PRIMARY KEY (CODCALC,SEQITCALC,CODFILIAL,CODEMP);

ALTER TABLE NECONTINGENCIA ADD CONSTRAINT NECONTINGENCIAPK PRIMARY KEY (ID);

ALTER TABLE SGLOGCRUD ADD CONSTRAINT SGLOGCRUDPK PRIMARY KEY (ID);

ALTER TABLE SGPROXYWEB ADD CONSTRAINT SGPROXYWEBPK PRIMARY KEY (CODPROXY,CODFILIAL,CODEMP);

ALTER TABLE SGSEQUENCE_ID ADD CONSTRAINT SGSEQUENCE_IDPK PRIMARY KEY (TABLE_NAME);

ALTER TABLE SGSQUID_ACTION ADD CONSTRAINT SGSQUID_ACTIONPK PRIMARY KEY (ID);

ALTER TABLE SGSQUID_HIER ADD CONSTRAINT SGSQUID_HIERPK PRIMARY KEY (ID);

ALTER TABLE SGSQUID_LOG ADD CONSTRAINT SGSQUID_LOGPK PRIMARY KEY (ID);

ALTER TABLE SGSQUID_METHOD ADD CONSTRAINT SGSQUID_METHODPK PRIMARY KEY (ID);

ALTER TABLE TKCONTPESSOA ADD CONSTRAINT TKCONTPESSOAPK PRIMARY KEY (CODCTO,EMAILPESSOA,CODFILIALCTO,CODEMPCTO,CODEMPPE,CODFILIALPE);

ALTER TABLE VDITVENDAITVENDA ADD CONSTRAINT VDITVENDAITVENDAPK PRIMARY KEY (ID);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.4.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE CPCOMPRAPED ADD CONSTRAINT CPCOMPRAPEDFKCPCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE;

ALTER TABLE CPCOMPRAPED ADD CONSTRAINT CPCOMPRAPEDFKCPCOMPRAPED FOREIGN KEY (CODCOMPRAPC,CODITCOMPRAPC,CODFILIALPC,CODEMPPC) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE;

ALTER TABLE CPCOMPRAVENDA ADD CONSTRAINT CPCOMPRAVENDAFKCPI FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE;

ALTER TABLE CPDEVOLUCAO ADD CONSTRAINT CPDEVOLUCAOFKITCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALVD,CODEMPVD) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE CPIMPORTACAO ADD CONSTRAINT CPIMPORTACAOFKCPIMP FOREIGN KEY (CODIMPOI,CODFILIALOI,CODEMPOI) REFERENCES CPIMPORTACAO(CODIMP,CODFILIAL,CODEMP);

ALTER TABLE CPIMPORTACAOCOMPL ADD CONSTRAINT CPIMPCOMPLFKCPIMP FOREIGN KEY (CODIMP,CODFILIAL,CODEMP) REFERENCES CPIMPORTACAO(CODIMP,CODFILIAL,CODEMP);

ALTER TABLE CPITCOMPRAITCOMPRA ADD CONSTRAINT CPITCOMPITCFKITCP1 FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP);

ALTER TABLE CPITCOMPRAITCOMPRA ADD CONSTRAINT CPITCOMPITCFKITCP2 FOREIGN KEY (CODCOMPRACO,CODITCOMPRACO,CODFILIALCO,CODEMPCO) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP);

ALTER TABLE CRFEEDBACK ADD CONSTRAINT CRFEEDBACKFKCRFIAL FOREIGN KEY (CODFILIAL,CODEMP) REFERENCES SGFILIAL(CODFILIAL,CODEMP);

ALTER TABLE CRFEEDBACK ADD CONSTRAINT CRFEEDBACKFKCRMOTI FOREIGN KEY (ID_MOTIVO) REFERENCES CRMOTIVOFB(ID);

ALTER TABLE CRFEEDBACK ADD CONSTRAINT CRFEEDBACKFKPESSO FOREIGN KEY (EMAILPESSOA,CODFILIALPE,CODEMPPE) REFERENCES CRPESSOA(EMAILPESSOA,CODFILIAL,CODEMP);

ALTER TABLE CRFICHAAVAL ADD CONSTRAINT CRFICHAAVALFKVDVEN FOREIGN KEY (CODVEND,CODFILIALVD,CODEMPVD) REFERENCES VDVENDEDOR(CODVEND,CODFILIAL,CODEMP);

ALTER TABLE CRMOTIVOFB ADD CONSTRAINT CRMOTIVOFBFKSGFILIAL FOREIGN KEY (CODFILIAL,CODEMP) REFERENCES SGFILIAL(CODFILIAL,CODEMP);

ALTER TABLE EQITRECMERCITCP ADD CONSTRAINT EQITRECMERCITCPFKITCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE EQMOVPROD ADD CONSTRAINT EQMOVPRODFKCPITCOM FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE;

ALTER TABLE EQMOVSERIE ADD CONSTRAINT EQMOVSERIEFKCPITCOM FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE EQTIPOMOV ADD CONSTRAINT EQTIPOMOVFKEQTIPOC FOREIGN KEY (CODTIPOMOVTC,CODFILIALTC,CODEMPTC) REFERENCES EQTIPOMOV(CODTIPOMOV,CODFILIAL,CODEMP);

ALTER TABLE EQTIPOMOV ADD CONSTRAINT EQTIPOMOVFKLFMENSA FOREIGN KEY (CODMENS,CODFILIALMC,CODEMPMC) REFERENCES LFMENSAGEM(CODMENS,CODFILIAL,CODEMP);

ALTER TABLE LFITCOMPRA ADD CONSTRAINT LFITCOMPRAFKCPITCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE NECONTINGENCIA ADD CONSTRAINT NECONTINGENCFKSGFI FOREIGN KEY (CODFILIAL,CODEMP) REFERENCES SGFILIAL(CODFILIAL,CODEMP);

ALTER TABLE PPOP ADD CONSTRAINT PPOPFKITCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE SGESTACAO ADD CONSTRAINT SGESTACAOFKSGPROXY FOREIGN KEY (CODPROXY,CODFILIALPX,CODEMPPX) REFERENCES SGPROXYWEB(CODPROXY,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE1 ADD CONSTRAINT SGPREFERE1FKLFMENS3 FOREIGN KEY (CODMENSVENDA,CODFILIALME,CODEMPME) REFERENCES LFMENSAGEM(CODMENS,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE1 ADD CONSTRAINT SGPREFERE1FKTPMOVIC FOREIGN KEY (CODTIPOMOVIC,CODFILIALIC,CODEMPIC) REFERENCES EQTIPOMOV(CODTIPOMOV,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKTKCON2 FOREIGN KEY (CODCONFEMAIL2,CODFILIALC2,CODEMPC2) REFERENCES TKCONFEMAIL(CODCONFEMAIL,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKTKEME2 FOREIGN KEY (CODEMAILEN2,CODFILIALE2,CODEMPE2) REFERENCES TKEMAIL(CODEMAIL,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKTKTIP2 FOREIGN KEY (CODTIPOCONT2,CODFILIALT2,CODEMPT2) REFERENCES TKTIPOCONT(CODTIPOCONT,CODFILIAL,CODEMP);

ALTER TABLE SGSQUID_LOG ADD CONSTRAINT SGSQUID_LOGPKAC FOREIGN KEY (ID_ACTION) REFERENCES SGSQUID_ACTION(ID);

ALTER TABLE SGSQUID_LOG ADD CONSTRAINT SGSQUID_LOGPKHI FOREIGN KEY (ID_HIER) REFERENCES SGSQUID_HIER(ID);

ALTER TABLE SGSQUID_LOG ADD CONSTRAINT SGSQUID_LOGPKME FOREIGN KEY (ID_METHOD) REFERENCES SGSQUID_METHOD(ID);

ALTER TABLE TKCONTATO ADD CONSTRAINT TKCONTATOFKTKTIPOCONT FOREIGN KEY (CODTIPOCONT,CODFILIALTO,CODEMPTO) REFERENCES TKTIPOCONT(CODTIPOCONT,CODFILIAL,CODEMP);

ALTER TABLE TKCONTPESSOA ADD CONSTRAINT TKCONTPESSOAFKCTO FOREIGN KEY (CODCTO,CODFILIALCTO,CODEMPCTO) REFERENCES TKCONTATO(CODCTO,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE TKCONTPESSOA ADD CONSTRAINT TKCONTPESSOAFKPES FOREIGN KEY (EMAILPESSOA,CODFILIALPE,CODEMPPE) REFERENCES CRPESSOA(EMAILPESSOA,CODFILIAL,CODEMP);

ALTER TABLE VDITVENDA ADD CONSTRAINT VDITVENDAFKCPITCOMPRA FOREIGN KEY (CODCOMPRA,CODITCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODCOMPRA,CODITCOMPRA,CODFILIAL,CODEMP) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE VDITVENDAITVENDA ADD CONSTRAINT VDITVENDAFKITVEND1 FOREIGN KEY (CODVENDA,CODITVENDA,TIPOVENDA,CODFILIAL,CODEMP) REFERENCES VDITVENDA(CODVENDA,CODITVENDA,TIPOVENDA,CODFILIAL,CODEMP);

ALTER TABLE VDITVENDAITVENDA ADD CONSTRAINT VDITVENDAFKITVEND2 FOREIGN KEY (CODVENDAVO,CODITVENDAVO,TIPOVENDAVO,CODFILIALVO,CODEMPVO) REFERENCES VDITVENDA(CODVENDA,CODITVENDA,TIPOVENDA,CODFILIAL,CODEMP);

/*  Empty EQMOVPRODCSLDSP for EQMOVPRODCKTMSP(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
 AS
 BEGIN EXIT; END
^

/*  Empty EQMOVPRODISP for EQMOVPRODCSLDSP(param list change)  */
/* AssignEmptyBody proc */
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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT)
 AS
 BEGIN EXIT; END
^

/*  Empty EQMOVPRODPRCSLDSP for EQMOVPRODCSLDSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty EQMOVPRODUSP for EQMOVPRODCSLDSP(param list change)  */
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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT)
 AS
 BEGIN EXIT; END
^

/*  Empty EQMOVPRODIUDSP for EQMOVPRODISP(param list change)  */
/* AssignEmptyBody proc */
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
ICODALMOX INTEGER,
SEQSUBPROD SMALLINT)
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER CPITCOMPRATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITCOMPRATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITCOMPRATGAD
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

ALTER TRIGGER EQINVPRODTGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGAD
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

ALTER TRIGGER PPOPTGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPENTRADATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPENTRADATGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPSUBPRODTGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPSUBPRODTGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER PPOPSUBPRODTGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER VDITVENDATGAD
 AS Declare variable I integer;
BEGIN I = 0; END
^

/*  Empty EQMOVPRODDSP for EQMOVPRODPRCSLDSP(param list change)  */
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
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT)
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
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
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
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
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

/* Alter empty procedure EQMOVPRODCKTMSP with new param-list */
ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODCSLDSP with new param-list */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

/* Alter empty procedure EQMOVPRODPRCSLDSP with new param-list */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
/* Alter (ATRESUMOATENDOSP01) */
SET TERM ^ ;

ALTER PROCEDURE ATRESUMOATENDOSP01(CODEMPP INTEGER,
CODFILIALP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(ANO SMALLINT,
MES SMALLINT,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
QTDCONTR DECIMAL(15,5),
DTINICIO DATE,
VALOR DECIMAL(15,5),
VALOREXCEDENTE DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
QTDHORAS DECIMAL(15,2),
MESCOB INTEGER,
SALDOMESCOB DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
SALDOPERIODO DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
EXCEDENTEPERIODO DECIMAL(15,5),
EXCEDENTECOB DECIMAL(15,5),
VALOREXCEDENTECOB DECIMAL(15,5),
VALORTOTALCOB DECIMAL(15,5),
VALORCONTR DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
TOTFRANQUIAMES DECIMAL(15,5))
 AS
begin
  saldoperiodo = 0;
 -- saldoperiodo2 = 0;
  saldomescob = 0;
  excedentemescob = 0;
  excedenteperiodo = 0;
--  excedenteperiodo2 = 0;
  excedentecob = 0;
  valorexcedentecob = 0;
  valortotalcob = 0;
  -- MES COBRANÇA
  mescob = extract(month from :dtfimp);
  -- DATA DO MÊS ANTERIOR A COBRANÇA
  --dtant = addmonth(:dtfimp, -1);
  -- MES ANTERIOR A COBRANÇA
 -- mesant = extract(month from :dtant);


  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
    , avg((select avg(ic.vlritcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valor
   , avg((select avg(ic.vlritcontrexced) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valorexcedente
   , avg((select sum(qtditcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  qtditcontr
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes

   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr, :qtdcontr, :dtinicio, :valor
   , :valorexcedente, :qtditcontr, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       if (mes=mescob) then
          valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras > :qtditcontr) and (mes = mescob) ) then
          excedentemescob = excedentemes;

       if (mes=mescob) then
       begin
          --- SALDO DO MÊS DE COBRANÇA
          saldomescob = saldomescob + saldomes;
       end
       else
       begin
         -- SALDO DO PERÍODO
         saldoperiodo = saldoperiodo + saldomes;

         -- EXCEDENTE DO PERÍODO
          excedenteperiodo = excedenteperiodo + excedentemes;

       end 

   end

   -- EXCEDENTE COBRADO
   if( :excedenteperiodo - :saldoperiodo > 0) then
       excedentecob =  excedenteperiodo - saldoperiodo;
   else
       excedentecob = 0;

   if (excedentecob>0) then
      saldoperiodo = 0;

   saldoperiodo = saldoperiodo + saldomescob;
   excedentecob = excedentemescob;

   --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

   -- VALOR A COBRAR EXCEDENTE
   valorexcedentecob = excedentecob * valorexcedente;
        
   -- VALOR TOTAL A COBRAR
   valortotalcob = (valorcontr) + (excedentecob * valorexcedente);

 -- saldoperiodo2 = 0;
  excedentemescob = 0;
  --excedenteperiodo2 = 0;

  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes
   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr
   , :qtdcontr, :dtinicio, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras < :qtditcontr)
       --or (mes <> mescob)
       ) then
          excedentemescob = 0;
       else
          excedentemescob = qtdhoras - qtditcontr;

       -- SALDO DO PERÍODO
--       saldoperiodo2 = saldoperiodo2 + saldomes;

       -- EXCEDENTE DO PERÍODO
  --     excedenteperiodo2 = excedenteperiodo2 + excedentemescob;

       --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

       -- VALOR A COBRAR EXCEDENTE
--       valorexcedentecob = excedentecob * valorexcedente;
        
       -- VALOR TOTAL A COBRAR
--       valortotalcob = (qtditcontr * valor) + (excedentecob * valorexcedente);

      suspend;
   end


end
^

/* Alter (ATRESUMOATENDOSP02) */
ALTER PROCEDURE ATRESUMOATENDOSP02(CODEMPCLP INTEGER,
CODFILIALCLP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
RAZCLI VARCHAR(60),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODITCONTR INTEGER,
CODCONTR INTEGER,
DESCCONTR VARCHAR(100),
VLRHORA DECIMAL(15,5),
VLRHORAEXCED DECIMAL(15,5),
QTDCONTR DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
VLRCOB DECIMAL(15,5),
VLRCOBEXCED DECIMAL(15,5),
VLRCOBTOT DECIMAL(15,5),
MES SMALLINT,
ANO SMALLINT,
QTDHORAS DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
ACUMULO SMALLINT,
CDATAINI DATE)
 AS
declare variable dtiniac date;
begin
  for select cl.codemp codempcl, cl.codfilial codfilialcl, cl.codcli, cl.razcli
    , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr, ct.desccontr, ct.dtinicio
    from vdcliente cl, vdcontrato ct
    where cl.codemp=:codempclp and cl.codfilial=:codfilialclp and (:codclip=0 or cl.codcli=:codclip)
       and ct.codemp=:codempctp and ct.codfilial=:codfilialctp and (:codcontrp=0 or ct.codcontr=:codcontrp)
       and ct.codempcl=cl.codemp and ct.codfilialcl=cl.codfilial and ct.codcli=cl.codcli
       and ct.ativo='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.recebcontr='S'
     order by cl.razcli,  cl.codcli,  ct.codcontr
  into :codempcl, :codfilialcl, :codcli, :razcli
    , :codempct, :codfilialct, :codcontr, :desccontr, :cdataini
  do
  begin


--       and i.codemp=ct.codemp  and i.codfilial=ct.codfilial and i.codcontr=ct.codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontrp)

  --vditcontrato i,

      select max(i.acumuloitcontr) from vditcontrato i
      where i.codemp=:codempct  and i.codfilial=:codfilialct and i.codcontr=:codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontr)
      and i.acumuloitcontr is not null
      into :acumulo;
      if ( (:acumulo is null) or (:acumulo=0) ) then
      begin
        acumulo = 0;
        dtiniac = dtinip;
      end
      else
      begin
        dtiniac = dtinip;
        dtiniac = cast( addmonth(dtiniac, -1*acumulo) as date);
        if( dtiniac > dtinip ) then
         dtiniac = dtinip;
      end
      for select a.mes, a.ano, a.qtdcontr, a.valor, a.valorexcedente, a.qtditcontr, a.qtdhoras, a.valortotalcob
       , a.saldomes, a.excedentemes, a.excedentemescob, a.valorexcedentecob , a.valorcontr
         from atresumoatendosp01(:codempcl, :codfilialcl, :codcli
         , :codempct, :codfilialct, :codcontr, :coditcontrp, :dtiniac, :dtfimp) a
      into :mes, :ano, :qtdcontr, :vlrhora, :vlrhoraexced, :qtditcontr, :qtdhoras, :vlrcobtot
      , :saldomes, :excedentemes, :excedentemescob, :vlrcobexced, :vlrcob
      do
      begin
        
      suspend;
      end
  end
end
^

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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
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
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
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
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
declare variable refprod varchar(20);
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

/* Alter (EQBUSCASIMILARSP) */
ALTER PROCEDURE EQBUSCASIMILARSP(CODEMP INTEGER,
CODFILIAL CHAR(8),
IPROD INTEGER,
SREFPROD VARCHAR(20))
 RETURNS(SRET CHAR(13),
IRET INTEGER,
CORIG CHAR(1),
ICODFOR INTEGER,
SRAZFOR CHAR(60),
NPRECO NUMERIC(15,5),
SSIM CHAR(18),
SSIMMASTER CHAR(18))
 AS
declare variable sfk char(21);
declare variable sbusca char(21);
BEGIN
/*  IF (IPROD IS NULL) THEN
  BEGIN
    SELECT CODPROD FROM EQPRODUTO WHERE CODEMP=:CODEMP
      AND CODFILIAL=:CODFILIAL AND REFPROD=:SREFPROD
      INTO IPROD;
    SBUSCA = CAST(SREFPROD AS CHAR(21));
  END
  ELSE
  BEGIN
    SBUSCA = CAST(IPROD AS VARCHAR(21));
  END
  FOR SELECT 'S',S.REFPRODSIM,S.REFPRODSIMFK,CAST(NULL AS INTEGER),
    CAST(NULL AS CHAR(50)),CAST(NULL AS NUMERIC(15, 5)),
    S.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=S.CODPROD
    AND P.CODEMP=S.CODEMP AND P.CODFILIAL=S.CODFILIAL)
    FROM EQSIMILAR S WHERE
    (S.CODPROD = :IPROD OR S.REFPRODSIM = :SBUSCA)
    AND S.CODEMP=:CODEMP AND S.CODFILIAL=:CODFILIAL INTO
    CORIG,SSIM,SFK,ICODFOR,SRAZFOR,NPRECO,IRET,SRET DO
  BEGIN
    SSIMMASTER = SSIM;
    SUSPEND;
    FOR SELECT 'K',S.REFPRODSIM,S.REFPRODSIMFK,CAST(NULL AS INTEGER),
      CAST(NULL AS CHAR(50)),CAST(NULL AS NUMERIC(15, 5)),
      S.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=S.CODPROD
      AND P.CODEMP=S.CODEMP AND P.CODFILIAL=S.CODFILIAL)
      FROM EQSIMILAR S WHERE
      (S.REFPRODSIMFK = :SFK OR S.CODPROD=:IRET) AND NOT S.REFPRODSIM=:SSIMMASTER
      AND S.CODEMP=:CODEMP AND S.CODFILIAL=:CODFILIAL INTO
      CORIG,SSIM,SFK,ICODFOR,SRAZFOR,NPRECO,IRET,SRET DO
    BEGIN
      SUSPEND;
    END
  END

  FOR SELECT 'C',C.REFPRODFOR,C.CODFOR,F.RAZFOR,CAST(NULL AS NUMERIC(15, 5)),
    P.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=C.CODPROD
    AND P.CODEMP=C.CODEMP AND P.CODFILIAL=C.CODFILIAL)
    FROM CPPRODFOR C, EQPRODUTO P, CPFORNECED F
    WHERE (C.CODPROD = :IPROD OR C.REFPRODFOR = :SBUSCA)
    AND C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND P.CODEMP=C.CODEMP
    AND P.CODFILIAL=C.CODFILIAL AND P.CODPROD=C.CODPROD
    AND F.CODEMP=C.CODEMP AND F.CODFILIAL=C.CODFILIAL
    AND F.CODFOR=C.CODFOR
    UNION
    SELECT 'T',T.REFPRODTFOR,T.CODFOR,F.RAZFOR,T.PRECOPRODTFOR,
    (SELECT P.CODPROD FROM EQPRODUTO P WHERE P.CODEMP=T.CODEMPPD
    AND P.CODFILIAL=T.CODFILIALPD AND P.CODPROD=T.CODPROD),
    (SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=T.CODPROD
    AND P.CODEMP=T.CODEMP AND P.CODFILIAL=T.CODFILIAL)
    FROM CPTABFOR T, CPFORNECED F
    WHERE T.REFPRODTFOR = :SBUSCA AND T.CODEMP=:CODEMP
    AND T.CODFILIAL=:CODFILIAL AND F.CODEMP=T.CODEMP
    AND F.CODFILIAL=T.CODFILIAL AND F.CODFOR=T.CODFOR INTO
    CORIG,SSIM,ICODFOR,SRAZFOR,NPRECO,SRET,IRET DO
  BEGIN
    SUSPEND;
  END */
END
^

/* empty dependent procedure body */
/* Clear: CPADICITCOMPRARECMERCSP for: EQCUSTOPRODSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: EQPRODUTOSP01 for: EQCUSTOPRODSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQPRODUTOSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NSALDO NUMERIC(15,5),
NSALDOAX NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
NCUSTOPEPS NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
NCUSTOPEPSAX NUMERIC(15,5),
NCUSTOINFO NUMERIC(15,5),
NCUSTOUC NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: EQRELPEPSSP for: EQCUSTOPRODSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQRELPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP VARCHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR(1),
CODUNID CHAR(20),
TIPOPROD VARCHAR(2),
CODNCM VARCHAR(10),
EXTIPI CHAR(2),
COD_GEN CHAR(2),
CODSERV CHAR(5),
ALIQ_ICMS NUMERIC(9,2),
CODNBM VARCHAR(12))
 AS
 BEGIN EXIT; END
^

/* Alter (EQCUSTOPRODSP) */
ALTER PROCEDURE EQCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
declare variable precobase numeric(15,5);
declare variable custompm numeric(15,5);
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

     -- Se o almoxarifado não for selecionado, deve buscar o saldo geral
    if(:icodalmox is null) then
    begin
   --      execute procedure sgdebugsp 'eqcustoprosp','inicio do custo por almoxarifado';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
             ) ,
            (SELECT FIRST 1 M.CUSTOMPMMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
            )
            FROM EQPRODUTO P
            WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND P.CODPROD=:ICODPROD
            INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;
  --       execute procedure sgdebugsp 'eqcustoprosp','após custo mpm';

    end
    else
    begin

   --     execute procedure sgdebugsp 'eqcustoprosp','inicio do custo geral';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPRODAX
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIAL=P.CODFILIAL AND M.CODPROD=P.CODPROD AND M.DTMOVPROD<=:DTESTOQ
                and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         ) ,
         (SELECT FIRST 1 M.CUSTOMPMMOVPROD
         FROM EQMOVPROD M
         WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
               and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
         ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         )
       FROM EQPRODUTO P
       WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
          P.CODPROD=:ICODPROD
       INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;

  --     execute procedure sgdebugsp 'eqcustoprosp','após o custo geral';

    end


  CUSTOUNIT = 0;
  CUSTOTOT = 0;
  IF (SLDPROD IS NULL) THEN
        SLDPROD = 0;
  IF ( (SLDPROD!=0) OR (CCOMSALDO!='S') ) THEN
  BEGIN
      -- Custo PEPS
      IF (CTIPOCUSTO='P') THEN
      BEGIN

         SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP,:SCODFILIAL,:ICODPROD,
            :SLDPROD,:DTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
            INTO :CUSTOUNIT;
         IF (CUSTOUNIT!=0) THEN
            CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo PEPS';

      END
      -- Custo MPM
      ELSE IF (CTIPOCUSTO='M') THEN
      BEGIN
         CUSTOUNIT = CUSTOMPM;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo MPM';

      END
      -- Preço Base
      ELSE IF (CTIPOCUSTO='B') THEN
      BEGIN
         CUSTOUNIT = PRECOBASE;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      --    execute procedure sgdebugsp 'eqcustoprosp','após precobase';

      END
      -- Preço da Ultima Compra
      else if (CTIPOCUSTO='U') then
      begin
        select first 1 ic.custoitcompra from cpitcompra ic, cpcompra cp
            where cp.codemp=:icodemp and cp.codfilial=:scodfilial and cp.dtentcompra<=:dtestoq
            and ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra
            and ic.codemppd=:icodemp and ic.codfilialpd=:scodfilial and ic.codprod=:icodprod
            and ( (:icodalmox is null) or ( ic.codempax=:icodempax and ic.codfilial=:scodfilialax and ic.codalmox=:icodalmox) )
            order by cp.dtentcompra desc
            into :CUSTOUNIT;

            if (CUSTOUNIT IS NULL) THEN
                CUSTOUNIT = :CUSTOMPM;
         -- execute procedure sgdebugsp 'eqcustoprosp','após última compra';

      end

  END
  SUSPEND;
end
^

/* Alter (EQMOVPRODCKTMSP) */
ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
begin
  /* Verifique se é para contar estoque */
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM WHERE TM.CODEMP=:ICODEMP AND
     TM.CODFILIAL = :SCODFILIAL AND TM.CODTIPOMOV = :ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;
  SOPERADOR = 0;
  if ((ESTOQTIPOMOVPD='S') and (CESTOQTIPOMOV='S')) then
  begin
     if (CESTIPOMOV='S') then
        SOPERADOR = -1;
     else
        SOPERADOR = 1;
  end
  suspend;
end
^

ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
begin
  /* Verifique se é para contar estoque */
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM WHERE TM.CODEMP=:ICODEMP AND
     TM.CODFILIAL = :SCODFILIAL AND TM.CODTIPOMOV = :ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;
  SOPERADOR = 0;
  if ((ESTOQTIPOMOVPD='S') and (CESTOQTIPOMOV='S')) then
  begin
     if (CESTIPOMOV='S') then
        SOPERADOR = -1;
     else
        SOPERADOR = 1;
  end
  suspend;
end
^

/* Alter (EQMOVPRODCSLDSP) */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
 AS
begin
  /* Procedure que retorna o cálculo de custo e saldo */
  NCUSTOMPM = 0;
  NSALDO = 0;
  SELECT CESTIPOMOV, SOPERADOR
     FROM EQMOVPRODCKTMSP( :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :ESTOQTIPOMOVPD)
     INTO :CTIPOMOVPROD, :SOPERADOR;
  if (SOPERADOR=0) then
  begin
     CESTOQMOVPROD = 'N';
     NSALDO = NSLDMOVPROD;
  end
  else
  begin  /* verifica se é para controlar estoque */
     CESTOQMOVPROD = 'S';
     NSALDO = NSLDMOVPROD + CAST ( (NQTDMOVPROD * SOPERADOR) AS NUMERIC(15, 5) );
  end
  if ( (NSALDO >= NSLDMOVPROD) AND (NSALDO > 0) ) then
  begin
    if ( (NSLDMOVPROD * NCUSTOMPMMOVPROD)  <= 0) then
       NCUSTOMPM = NPRECOMOVPROD;
    else
        NCUSTOMPM = ( cast(NSLDMOVPROD * NCUSTOMPMMOVPROD as numeric(15,5) ) +
        cast(NQTDMOVPROD * NPRECOMOVPROD as numeric(15,5)) ) / (NSLDMOVPROD + NQTDMOVPROD) ;
  end
  else
      NCUSTOMPM = NCUSTOMPMMOVPROD;

  suspend;
end
^

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 AS
DECLARE VARIABLE ICODEMPTM INTEGER;
DECLARE VARIABLE SCODFILIALTM SMALLINT;
DECLARE VARIABLE ICODTIPOMOV INTEGER;
DECLARE VARIABLE NQTDMOVPROD NUMERIC(15,5);
DECLARE VARIABLE NPRECOMOVPROD NUMERIC(15,5);
DECLARE VARIABLE ICODMOVPRODPRC INTEGER;
DECLARE VARIABLE CESTOQMOVPROD CHAR(1);
DECLARE VARIABLE ICODEMPAXPRC INTEGER;
DECLARE VARIABLE SCODFILIALAXPRC SMALLINT;
DECLARE VARIABLE ICODALMOXPRC INTEGER;
begin
  /* Procedure de processamento de estoque */
  FOR SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV ,
    MP.QTDMOVPROD, MP.PRECOMOVPROD , MP.CODMOVPROD,
    MP.CODEMPAX, MP.CODFILIALAX, MP.CODALMOX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMPPD AND MP.CODFILIALPD=:SCODFILIALPD AND
       MP.CODPROD=:ICODPROD AND MP.CODEMP=:ICODEMPPD AND MP.CODFILIAL=:SCODFILIAL AND
       ( (MP.DTMOVPROD = :DDTMOVPROD AND MP.CODMOVPROD > :ICODMOVPROD) OR
         (MP.DTMOVPROD>:DDTMOVPROD) ) AND  /* ALTEREI AQUI */
       ( (:DDTMOVPRODPRC IS NULL /* AND MP.CODMOVPROD!=:ICODMOVPROD */) OR
         (MP.DTMOVPROD = :DDTMOVPRODPRC AND MP.CODMOVPROD < :ICODMOVPROD) OR
         (MP.DTMOVPROD < :DDTMOVPRODPRC) )
    ORDER BY MP.DTMOVPROD, MP.CODMOVPROD
    INTO :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
     :NQTDMOVPROD, :NPRECOMOVPROD, :ICODMOVPRODPRC,
     :ICODEMPAXPRC, :SCODFILIALAXPRC, :ICODALMOXPRC DO
  BEGIN
      SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
        :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD;
      if (CMULTIALMOX='N') then /* Se não for multi almoxarifado*/
      begin
         NSLDMOVPRODAX = NSLDMOVPROD;
         NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
         UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else if (ICODEMPAX=ICODEMPAXPRC AND SCODFILIALAX=SCODFILIALAXPRC AND
          ICODALMOX=ICODALMOXPRC) then
          /* Se for multi almoxarifado e o código do almoxarifado for o mesmo*/
      begin
        SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
            :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
            INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else /* Se for multi almoxarifado não atualiza almoxarifado diferente */
      begin
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      NSLDPRC = NSLDMOVPROD;
      NCUSTOMPMPRC = NCUSTOMPMMOVPROD;
      NSLDPRCAX = NSLDMOVPRODAX;
      NCUSTOMPMPRCAX = NCUSTOMPMMOVPRODAX;
  END
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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT)
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
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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

ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
 AS
begin
  /* Procedure que retorna o cálculo de custo e saldo */
  NCUSTOMPM = 0;
  NSALDO = 0;
  SELECT CESTIPOMOV, SOPERADOR
     FROM EQMOVPRODCKTMSP( :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :ESTOQTIPOMOVPD)
     INTO :CTIPOMOVPROD, :SOPERADOR;
  if (SOPERADOR=0) then
  begin
     CESTOQMOVPROD = 'N';
     NSALDO = NSLDMOVPROD;
  end
  else
  begin  /* verifica se é para controlar estoque */
     CESTOQMOVPROD = 'S';
     NSALDO = NSLDMOVPROD + CAST ( (NQTDMOVPROD * SOPERADOR) AS NUMERIC(15, 5) );
  end
  if ( (NSALDO >= NSLDMOVPROD) AND (NSALDO > 0) ) then
  begin
    if ( (NSLDMOVPROD * NCUSTOMPMMOVPROD)  <= 0) then
       NCUSTOMPM = NPRECOMOVPROD;
    else
        NCUSTOMPM = ( cast(NSLDMOVPROD * NCUSTOMPMMOVPROD as numeric(15,5) ) +
        cast(NQTDMOVPROD * NPRECOMOVPROD as numeric(15,5)) ) / (NSLDMOVPROD + NQTDMOVPROD) ;
  end
  else
      NCUSTOMPM = NCUSTOMPMMOVPROD;

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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
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
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX, seqsubprod )
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
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :seqsubprod );

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

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

SET TERM ^ ;

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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC, :ESTOQTIPOMOVPD)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX, :ESTOQTIPOMOVPD)
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
              :NCUSTOMPMLC, :NSLDLC, :ESTOQTIPOMOVPD)
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
                  :NCUSTOMPMLCAX, :NSLDLCAX, :ESTOQTIPOMOVPD)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
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
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX, seqsubprod )
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
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :seqsubprod );

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
ICODALMOX INTEGER,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod, :estoqtipomovpd);
  else if (CIUD='U') then
     execute procedure EQMOVPRODUSP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX,:seqsubprod, :estoqtipomovpd);
  else if (CIUD='D') then
     execute procedure EQMOVPRODDSP( ICODEMPPD, SCODFILIALPD, ICODPROD, ICODEMPIV,
         SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA, SCODITCOMPRA,
         ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         DDTMOVPROD, ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod );
--  suspend;
end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial de subproduto'
where Rdb$Procedure_Name='EQMOVPRODIUDSP' and Rdb$Parameter_Name='SEQSUBPROD';

/* Alter (EQMOVPRODPRCSLDSP) */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 AS
DECLARE VARIABLE ICODEMPTM INTEGER;
DECLARE VARIABLE SCODFILIALTM SMALLINT;
DECLARE VARIABLE ICODTIPOMOV INTEGER;
DECLARE VARIABLE NQTDMOVPROD NUMERIC(15,5);
DECLARE VARIABLE NPRECOMOVPROD NUMERIC(15,5);
DECLARE VARIABLE ICODMOVPRODPRC INTEGER;
DECLARE VARIABLE CESTOQMOVPROD CHAR(1);
DECLARE VARIABLE ICODEMPAXPRC INTEGER;
DECLARE VARIABLE SCODFILIALAXPRC SMALLINT;
DECLARE VARIABLE ICODALMOXPRC INTEGER;
begin
  /* Procedure de processamento de estoque */
  FOR SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV ,
    MP.QTDMOVPROD, MP.PRECOMOVPROD , MP.CODMOVPROD,
    MP.CODEMPAX, MP.CODFILIALAX, MP.CODALMOX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMPPD AND MP.CODFILIALPD=:SCODFILIALPD AND
       MP.CODPROD=:ICODPROD AND MP.CODEMP=:ICODEMPPD AND MP.CODFILIAL=:SCODFILIAL AND
       ( (MP.DTMOVPROD = :DDTMOVPROD AND MP.CODMOVPROD > :ICODMOVPROD) OR
         (MP.DTMOVPROD>:DDTMOVPROD) ) AND  /* ALTEREI AQUI */
       ( (:DDTMOVPRODPRC IS NULL /* AND MP.CODMOVPROD!=:ICODMOVPROD */) OR
         (MP.DTMOVPROD = :DDTMOVPRODPRC AND MP.CODMOVPROD < :ICODMOVPROD) OR
         (MP.DTMOVPROD < :DDTMOVPRODPRC) )
    ORDER BY MP.DTMOVPROD, MP.CODMOVPROD
    INTO :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
     :NQTDMOVPROD, :NPRECOMOVPROD, :ICODMOVPRODPRC,
     :ICODEMPAXPRC, :SCODFILIALAXPRC, :ICODALMOXPRC DO
  BEGIN
      SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
        :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD;
      if (CMULTIALMOX='N') then /* Se não for multi almoxarifado*/
      begin
         NSLDMOVPRODAX = NSLDMOVPROD;
         NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
         UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else if (ICODEMPAX=ICODEMPAXPRC AND SCODFILIALAX=SCODFILIALAXPRC AND
          ICODALMOX=ICODALMOXPRC) then
          /* Se for multi almoxarifado e o código do almoxarifado for o mesmo*/
      begin
        SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
            :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
            INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else /* Se for multi almoxarifado não atualiza almoxarifado diferente */
      begin
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      NSLDPRC = NSLDMOVPROD;
      NCUSTOMPMPRC = NCUSTOMPMMOVPROD;
      NSLDPRCAX = NSLDMOVPRODAX;
      NCUSTOMPMPRCAX = NCUSTOMPMMOVPRODAX;
  END
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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC, :ESTOQTIPOMOVPD)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX, :ESTOQTIPOMOVPD)
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
              :NCUSTOMPMLC, :NSLDLC, :ESTOQTIPOMOVPD)
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
                  :NCUSTOMPMLCAX, :NSLDLCAX, :ESTOQTIPOMOVPD)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

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

/* empty dependent procedure body */
/* Clear: EQGERARMAOSSP for: EQPRODUTOSP01 */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: EQGERARMASP for: EQPRODUTOSP01 */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter (EQPRODUTOSP01) */
ALTER PROCEDURE EQPRODUTOSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NSALDO NUMERIC(15,5),
NSALDOAX NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
NCUSTOPEPS NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
NCUSTOPEPSAX NUMERIC(15,5),
NCUSTOINFO NUMERIC(15,5),
NCUSTOUC NUMERIC(15,5))
 AS
declare variable ddtmovprod date;
declare variable ddtmovprodax date;
begin

    /* Procedure que retorna saldos e custos para a tela de cadastro de produtos */
    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPROD , MP.CUSTOMPMMOVPROD
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPROD, :NSALDO, :NCUSTOMPM;

    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPRODAX, MP.CUSTOMPMMOVPRODAX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPRODAX, :NSALDOAX, :NCUSTOMPMAX;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPROD, null, null, null ) P
    INTO :NCUSTOPEPS;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPRODAX, :ICODEMPAX, :SCODFILIALAX,
    :ICODALMOX ) P
    INTO :NCUSTOPEPSAX;

    select p.custoinfoprod from eqproduto p
    where p.codemp=:icodemp and p.codfilial=:scodfilial and p.codprod=:icodprod
    into :ncustoinfo;

    select custounit from eqcustoprodsp(:icodemp, :scodfilial, :icodprod,
    cast('today' as date),'U',:icodempax, :scodfilialax, :icodalmox, 'N' )
    into :ncustouc;

    if(:ncustompm is null) then
    begin
        ncustompm = :ncustoinfo;
    end

    if(:ncustopeps is null) then
    begin
        ncustopeps = :ncustoinfo;
    end

    suspend;

end
^

/* Alter (EQRELPEPSSP) */
ALTER PROCEDURE EQRELPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP VARCHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR(1),
CODUNID CHAR(20),
TIPOPROD VARCHAR(2),
CODNCM VARCHAR(10),
EXTIPI CHAR(2),
COD_GEN CHAR(2),
CODSERV CHAR(5),
ALIQ_ICMS NUMERIC(9,2),
CODNBM VARCHAR(12))
 AS
begin

  /* procedure text */

  if (icodempgp is not null) then
  begin
    if (strlen(rtrim(ccodgrup))<14) then
       ccodgrup = rtrim(ccodgrup)||'%';
  end

  if (ctipocusto is null) then
     ctipocusto = 'P';

  for select p.codprod,p.refprod,p.descprod, p.codbarprod
     , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
     , fc.codncm, fc.extipi
     , substring(fc.codncm from 1 for 2) cod_gen
     , fc.codserv,
      (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
      ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
     , fc.codnbm

   from eqproduto p
   left outer join lfclfiscal fc
     on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
   where p.codemp = :icodemp and p.codfilial = :scodfilial and
   ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
      p.codmarca=:ccodmarca) ) and
   ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
      p.codgrup like :ccodgrup) )
      order by p.codprod

   into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
   , :ativoprod, :codunid, :tipoprod
   , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm  do

  begin

     select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
        :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
        :scodfilialax, :icodalmox, 'S')
       into :sldprod, :custounit, :custotot;
     suspend;

  end

end
^

/* Alter (FNADICLANCASP01) */
ALTER PROCEDURE FNADICLANCASP01(ICODREC INTEGER,
INPARCITREC INTEGER,
PDVITREC CHAR(1),
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITREC DATE,
DDTPAGOITREC DATE,
SDOCLANCAITREC VARCHAR(15),
SOBSITREC CHAR(250),
DVLRPAGOITREC NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRPAGOJUROS NUMERIC(15,5),
DVLRDESC NUMERIC(15,5))
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjr integer;
declare variable codfilialjr smallint;
declare variable codplanjr char(13);
declare variable codempdc integer;
declare variable codfilialdc smallint;
declare variable codplandc char(13);
declare variable codsublanca smallint = 1;
BEGIN
    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA') INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMPPN,CODFILIALPN FROM FNCONTA WHERE NUMCONTA = :SNUMCONTA
        AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALLANCA,'LA') INTO ICODLANCA;

    SELECT FLAG FROM FNRECEBER WHERE CODREC=:ICODREC
        AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO :cFlag;

    SELECT CODEMPJR,CODFILIALJR,CODPLANJR,CODEMPDC,CODFILIALDC,CODPLANDC FROM SGPREFERE1
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
        INTO :CODEMPJR,:CODFILIALJR,:CODPLANJR,:CODEMPDC,:CODFILIALDC,:CODPLANDC;


    IF (ICODCLI IS NULL) THEN
        TIPOLANCA = 'A';
    ELSE
        TIPOLANCA = 'C';

    if ( (DVLRPAGOJUROS IS NULL) OR (:CODPLANJR IS NULL)  ) then
        DVLRPAGOJUROS = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC - DVLRPAGOJUROS;

    if (DVLRDESC IS NULL  OR (:CODPLANDC IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC + DVLRDESC;


    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPRC,CODFILIALRC,CODREC,NPARCITREC,CODEMPPN,CODFILIALPN,CODPLAN, 
        DTCOMPLANCA, DATALANCA, DOCLANCA,HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG,CODEMPCL,CODFILIALCL,CODCLI,PDVITREC)
        VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:ICODREC,:iNParcItRec,:iCodEmpPConta,:iCodFilialPConta,
                :sCodPlanConta,:dDtCompItRec, :dDtPagoItRec,:sDocLancaItRec,:sObsItRec,:dDtPagoItRec,0,:cFlag,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:PDVITREC
        );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA, DTCOMPSUBLANCA, DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:ICODEMPPN,:ICODFILIALPN,:SCODPLAN,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:dVlrPagoItRec*-1,:cFlag
        );

    -- Lançamento dos juros em conta distinta.

    IF(DVLRPAGOJUROS > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                 CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                  CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPJR,:CODFILIALJR,:CODPLANJR,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRPAGOJUROS*-1,:cFlag, 'J'
        );

    END

    -- Lançamento do desconto em conta distinta.

    IF(DVLRDESC > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
             CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
             CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPDC,:CODFILIALDC,:CODPLANDC,
               :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
             :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRDESC,:cFlag, 'D'
        );

    END


END
^

/* Alter (FNADICLANCASP02) */
ALTER PROCEDURE FNADICLANCASP02(ICODPAG INTEGER,
INPARCPAG INTEGER,
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODFOR INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITPAG DATE,
DDTPAGOITPAG DATE,
SDOCLANCAITPAG VARCHAR(15),
SOBSITPAG CHAR(250),
DVLRPAGOITPAG NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRJUROSPAG NUMERIC(15,5),
DVLRDESC NUMERIC(15,5))
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjp integer;
declare variable codfilialjp smallint;
declare variable codplanjp char(13);
declare variable codempdr integer;
declare variable codfilialdr smallint;
declare variable codplandr char(13);
declare variable codsublanca smallint = 1;
BEGIN

    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA')
    INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMP,CODFILIAL FROM FNCONTA
    WHERE NUMCONTA=:sNumConta AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA
    INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:iCODEMP,:IFILIALLANCA,'LA')
    INTO :iCodLanca;

    SELECT FLAG FROM FNPAGAR
    WHERE CODPAG=:iCodPag AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :cFlag;

    SELECT CODEMPJP,CODFILIALJP,CODPLANJP,CODEMPDR,CODFILIALDR,CODPLANDR
    FROM SGPREFERE1
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :CODEMPJP,:CODFILIALJP,:CODPLANJP,:CODEMPDR,:CODFILIALDR,:CODPLANDR;

    IF (ICODFOR IS NULL) THEN
        TIPOLANCA = 'A';
      ELSE
        TIPOLANCA = 'F';

    if ( (DVLRJUROSPAG IS NULL) OR (:CODPLANJP IS NULL)  ) then
        DVLRJUROSPAG = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG - DVLRJUROSPAG;

    if (DVLRDESC IS NULL  OR (:CODPLANDR IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG + DVLRDESC;

    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPPG,CODFILIALPG,CODPAG,
                         NPARCPAG,CODEMPPN,CODFILIALPN,CODPLAN,DTCOMPLANCA,DATALANCA,DOCLANCA,
                         HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG, CODEMPFR, CODFILIALFR, CODFOR)
         VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:iCodPag,
                 :iNParcPag,:iCodEmpPConta,:iCodFilialPConta,:sCodPlanConta, :dDtCompItPag, :dDtPagoItPag,:sDocLancaItPag,
                 :sObsItPag,:dDtPagoItPag,0,:cFlag, :ICODEMPFR, :ICODFILIALFR, :ICODFOR
         );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN, CODPLAN,
        CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG,
        CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:ICODEMPPN,:ICODFILIALPN, :sCodplan,
        :ICODEMP,:ICODFILIAL,:iCodPag, :iNParcPag,
        :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:dVlrPagoItPag,:cFlag);

    -- Lançamento dos juros em conta distinta.

    IF(DVLRJUROSPAG >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
             CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
             CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
            :CODEMPJP,:CODFILIALJP,:CODPLANJP,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRJUROSPAG,
            :ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag,:cFlag, 'J');

    END

    -- Lançamento de desconto em conta distinta.

    IF(DVLRDESC >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
        CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
              CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
             :CODEMPDR,:CODFILIALDR,:CODPLANDR,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRDESC*-1,
             :ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag, :cFlag, 'D');

    END

END
^

/* Alter (FNCHECAPGTOATRASOSP) */
ALTER PROCEDURE FNCHECAPGTOATRASOSP(ICODCLI INTEGER,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(SRETORNO CHAR(1))
 AS
declare variable ilinhas integer;
declare variable ifilialreceber integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNRECEBER') INTO IFILIALRECEBER;
  SELECT COUNT(*) FROM FNITRECEBER IT WHERE CODREC
         IN (
            SELECT CODREC FROM FNRECEBER REC WHERE REC.CODCLI = :iCodCli
                   AND REC.CODEMPCL=:ICODEMP AND REC.CODFILIALCL=:ICODFILIAL
                   AND REC.CODREC = IT.CODREC
                   AND CODEMP=IT.CODEMP AND CODFILIAL=IT.CODFILIAL
         )
         AND STATUSITREC in ('R1') AND IT.dtvencitrec < cast('today' as date)
         AND IT.CODEMP=:ICODEMP AND IT.CODFILIAL=:IFILIALRECEBER INTO iLinhas;
  IF (iLinhas > 0) THEN
    sRetorno = 'S';
  ELSE
    sRetorno = 'N';
  SUSPEND;
END
^

/* Alter (LFBUSCAFISCALSP) */
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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
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
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
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
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
declare variable tipoprod varchar(2);
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
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(cf.aliqissfisc, fi.percissfilial),
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

/* Alter (LFCALCCUSTOSP01) */
ALTER PROCEDURE LFCALCCUSTOSP01(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCALC INTEGER,
QTDADE DECIMAL(15,5),
VLRCUSTOP DECIMAL(15,5),
VLRICMS DECIMAL(15,5),
VLRIPI DECIMAL(15,5),
VLRPIS DECIMAL(15,5),
VLRCOFINS DECIMAL(15,5),
VLRISS DECIMAL(15,5),
VLRFUNRURAL DECIMAL(15,5),
VLRII DECIMAL(15,5),
VLRIR DECIMAL(15,5),
VLRTXSISCOMEX DECIMAL(15,5),
VLRICMSDIFERIDO DECIMAL(15,5),
VLRICMSPRESUMIDO DECIMAL(15,5),
VLRCOMPL DECIMAL(15,5))
 RETURNS(VLRCUSTO DECIMAL(15,5))
 AS
declare variable siglacalc varchar(10);
declare variable operacao char(1);
declare variable vlrimposto decimal(15,5);
begin
  if (:vlrcustop is null) then
    vlrcusto = 0;
  else
    vlrcusto = vlrcustop;

  if (vlripi is null) then
   vlripi = 0;
  if (vlricms is null) then
   vlricms = 0;
  if (vlrpis is null) then
   vlrpis = 0;
  if (vlrcofins is null) then
   vlrcofins = 0;
  if (vlriss is null) then
   vlriss = 0;
  if (vlrfunrural is null) then
   vlrfunrural = 0;
  if (vlrii is null) then
   vlrii = 0;
  if (vlrir is null) then
   vlrir = 0;
  if (vlrtxsiscomex is null) then
   vlrtxsiscomex = 0;
  if (vlricmsdiferido is null) then
   vlricmsdiferido = 0;
  if (vlricmspresumido is null) then
   vlricmspresumido = 0;
  if (vlrcompl is null) then
   vlrcompl = 0; 

  for select ic.siglacalc, ic.operacaocalc
     from lfcalccusto c, lfitcalccusto ic
     where c.codemp=:codemp and c.codfilial=:codfilial and c.codcalc=:codcalc
       and ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.codcalc=c.codcalc
       into :siglacalc, :operacao do
  begin

     if (:siglacalc='IPI') then
       vlrimposto = vlripi;
     else if (:siglacalc='ICMS') then
       vlrimposto = vlricms;
     else if (:siglacalc='PIS') then
       vlrimposto = vlrpis;
     else if (:siglacalc='COFINS') then
       vlrimposto = vlrcofins;
     else if (:siglacalc='ISS') then
       vlrimposto = vlriss;
     else if (:siglacalc='FUNRURAL') then
       vlrimposto = vlrfunrural;
     else if (:siglacalc='II') then
       vlrimposto = vlrii;
     else if (:siglacalc='IR') then
       vlrimposto = vlrir;
     else if (:siglacalc='TXSISCOMEX') then
       vlrimposto = vlrtxsiscomex;
     else if (:siglacalc='ICMSDIF') then
       vlrimposto = vlricmsdiferido;
     else if (:siglacalc='ICMSPRES') then
       vlrimposto = vlricmspresumido;
     else if (:siglacalc='COMPL') then
       vlrimposto = vlrcompl;

     if (:operacao='+') then
        vlrcusto = vlrcusto + vlrimposto;
     else if (:operacao='-') then
        vlrcusto = vlrcusto - vlrimposto;
  end
  if (qtdade is not null and qtdade<>0) then
  begin
     -- Dividimos o total pela quantidade, multiplicamos o resultado novamente pela quantidade e dividimos pela quantidade, objetivando evitar dizima periódica.
     
      vlrcusto = cast( ( cast(vlrcusto as decimal(15,4)) / cast( qtdade as decimal(15,5) )  * cast( qtdade as decimal(15,5) ) ) as decimal(15,4) ) / cast( qtdade as decimal(15,5) ) ;
  end
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: PPRELCUSTOSP for: PPCUSTOPRODSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE PPRELCUSTOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
TIPOPROD VARCHAR(2),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter (PPCUSTOPRODSP) */
ALTER PROCEDURE PPCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(CUSTOUNIT NUMERIC(15,5),
SLDPROD NUMERIC(15,5))
 AS
declare variable qtditest numeric(15,5);
declare variable seqest smallint;
declare variable tipoprod varchar(2);
declare variable custotot numeric(15,5);
declare variable codprodpd integer;
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  SELECT P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
      P.CODPROD=:ICODPROD
   INTO :TIPOPROD;

  IF (TIPOPROD='F') THEN
  BEGIN
     SELECT SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :SLDPROD;

     CUSTOTOT = 0;

     SELECT FIRST 1 E.SEQEST FROM PPESTRUTURA E
       WHERE E.CODEMP=:ICODEMP AND E.CODFILIAL=:SCODFILIAL AND E.CODPROD=:ICODPROD
       ORDER BY E.SEQEST INTO :SEQEST;

     FOR SELECT I.CODPRODPD,I.QTDITEST FROM  PPITESTRUTURA I
        WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:SCODFILIAL AND
          I.CODPROD=:ICODPROD AND I.SEQEST=:SEQEST
        INTO :CODPRODPD, :QTDITEST DO
     BEGIN
         SELECT CUSTOUNIT FROM PPCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :CODPRODPD, :DTESTOQ,
            :CTIPOCUSTO, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CCOMSALDO)
         INTO :CUSTOUNIT;
         CUSTOTOT = CUSTOTOT + ( CUSTOUNIT * QTDITEST)  ;
     END
  END
  ELSE
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :CUSTOTOT, :SLDPROD;
  END
  CUSTOUNIT = CUSTOTOT;
  SUSPEND;
end
^

/* Alter (PPITOPSP01) */
ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable gerarma char(1);
declare variable crefprod varchar(20);
declare variable icodprodpd integer;
declare variable nqtditop numeric(15,5);
declare variable icodemple integer;
declare variable icodfilialle smallint;
declare variable ccodlote varchar(20);
declare variable icodempfs integer;
declare variable icodfilialfs smallint;
declare variable icodfase integer;
declare variable icodemptr integer;
declare variable icodfilialtr smallint;
declare variable icodtprec integer;
declare variable icodemprp integer;
declare variable icodfilialrp smallint;
declare variable icodrecp integer;
declare variable dtempoof numeric(15,5);
declare variable iseqof smallint;
declare variable iseqppitop integer;
declare variable qtditest numeric(15,5);
declare variable qtdest numeric(15,5);
declare variable qtdprevprodop numeric(15,5);
declare variable qtdfixa char(1);
declare variable estdinamica char(1);
declare variable permiteajusteitop char(1);
declare variable iseqsubprod integer;
declare variable qtditestsp numeric(15,5);
declare variable codempts integer;
declare variable codfilialts smallint;
declare variable codtipomovsp integer;
declare variable tipoexterno char(10);
begin

    --Loop nas fases da estrutura para geração da tabela de fases da OP.
    for select ef.seqef, ef.codempfs, ef.codfilialfs, ef.codfase, ef.codemptr, ef.codfilialtr, ef.codtprec, ef.tempoef, o.estdinamica
    from ppestrufase ef, ppop o, ppestrutura e
    where
        o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
        e.codemp=o.codemppd and e.codfilial=o.codfilialpd and e.codprod=o.codprod and e.seqest=o.seqest and
        ef.codemp=e.codemp and ef.codfilial=e.codfilial and ef.codprod=e.codprod and ef.seqest=E.seqest
    into
        :iseqof, :icodempfs, :icodfilialfs, :icodfase, :icodemptr, :icodfilialtr, :icodtprec, :dtempoof, :estdinamica
    do
    begin
        -- Buscando o primeiro recurso para inserção na fase (provisório)
        select first 1 codemp, codfilial, codrecp from pprecurso r
        where r.codemp=:icodemptr and r.codfilial=:icodfilialtr and r.codtprec=:icodtprec
        into :icodemprp, :icodfilialrp, :icodrecp;

        -- Inserindo na tabela de fase por op
        insert into
            ppopfase (
                codemp, codfilial, codop, seqop, seqof, codempfs, codfilialfs, codfase, codemprp, codfilialrp, codrecp, tempoof,
                codemptr, codfilialtr, codtprec
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqof, :icodempfs, :icodfilialfs,:icodfase, :icodemprp, :icodfilialrp,
                :icodrecp,:dtempoof, :icodemptr, :icodfilialtr, :icodtprec
            );
    end

    -- Se a estrutura não for dinâmica, deve inserir os ítens

    if(coalesce(:estdinamica,'N')='N'  ) then    
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, ie.permiteajusteitest, ie.tipoexterno
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote,:permiteajusteitop,:tipoexterno
        do
        begin
            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqppitop = :iseqppitop + 1;

            if ('S'=qtdfixa) then
            begin
                nqtditop = :qtditest;
            end
            else
            begin
                nqtditop = cast(:qtditest/:qtdest as numeric(15,5) ) * cast(:qtdprevprodop as numeric(15, 5));
            end

            insert into ppitop (
                codemp, codfilial, codop, seqop, seqitop, codemppd, codfilialpd, codprod, refprod,
                codempfs, codfilialfs, codfase, qtditop, gerarma, permiteajusteitop, tipoexterno
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma,
                :permiteajusteitop, :tipoexterno
            );

        end

        -- Inserindo tabela de subprodutos

        iseqsubprod = 0;

        -- Buscando tipo de movimento para subproducao
        select codempts, codfilialts, codtipomovsp from sgprefere5 where codemp=:icodemp and codfilial=:icodfilial
        into :codempts, :codfilialts, :codtipomovsp;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitestsp, ie.codprodpd, ie.refprodpd, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditestsp, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, fs.seqof
            from
                ppitestruturasubprod ie, ppestrutura e, ppop o, ppopfase fs
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
                fs.codemp=ie.codempfs and fs.codfilial=ie.codfilialfs and fs.codfase=ie.codfase and fs.codop=o.codop and fs.seqop=o.seqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqsubprod, :icodprodpd, :crefprod, :icodempfs, :icodfilialfs, :icodfase,
        :qtditestsp,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote, :iseqof
        do
        begin

            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqsubprod = :iseqsubprod + 1;

           insert into ppopsubprod (codemp, codfilial, codop, seqop, seqsubprod, codemppd, codfilialpd, codprod,
                refprod, qtditsp, codempfs, codfilialfs, codfase, codemple, codfilialle, codlote, seqof, codemptm, codfilialtm, codtipomov
           )
           values(
                :icodemp, :icodfilial,:icodop,:iseqop, :iseqsubprod, :icodemppd, :icodfilialpd, :icodprodpd,
                :crefprod, :qtditestsp, :icodempfs, :icodfilialfs, :icodfase, :icodemple, :icodfilialle, :ccodlote, :iseqof, :codempts, :codfilialts, :codtipomovsp
           );



        end


    end

end
^

/* Alter (PPITOPSP02) */
ALTER PROCEDURE PPITOPSP02(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
DECLARE VARIABLE NQTDITOP NUMERIC(15,5);
DECLARE VARIABLE ICODPRODPD INTEGER;
DECLARE VARIABLE ISEQITOP SMALLINT;
DECLARE VARIABLE ICODEMPPD INTEGER;
DECLARE VARIABLE ISEQPPITOP SMALLINT;
BEGIN
    FOR SELECT It.seqitop,
        CAST( IE.QTDITEST / E.QTDEST AS NUMERIC(15,5) ) * CAST(O.QTDFINALPRODOP AS NUMERIC(15, 5))
        FROM PPITESTRUTURA IE, PPESTRUTURA E, PPOP O, PPITOP IT
            WHERE IE.CODEMP=E.CODEMP AND IE.CODFILIAL=E.CODFILIAL AND
                IE.CODPROD=E.CODPROD AND IE.SEQEST=E.SEQEST AND
                O.CODEMPPD=E.CODEMP AND O.CODFILIALPD=E.CODFILIAL AND
                O.CODPROD=E.CODPROD AND O.SEQEST=E.SEQEST AND
                O.CODEMP=:ICODEMP AND O.CODFILIAL=:ICODFILIAL AND
                O.CODOP=:ICODOP AND O.SEQOP=:ISEQOP AND
                IT.codemp=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL AND
                IT.codop=O.CODOP AND IT.seqop=O.seqop AND
                IE.codempfs=IT.codempfs AND IE.codfilial=IT.codfilialfs AND
                IE.codfase=IT.codfase and
                ie.codemppd=it.codemp and ie.codfilialpd=it.codfilial and
                ie.codprodpd=it.codprod and
                IE.qtdvariavel = 'S'
            INTO :ISEQITOP,:NQTDITOP
    DO
    BEGIN
        UPDATE PPITOP IOP SET QTDITOP=:NQTDITOP
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND IOP.codop=:ICODOP
                AND IOP.seqop=:ISEQOP AND IOP.seqitop=:ISEQITOP;

    END
   SUSPEND;
END
^

/* Alter (PPRELCUSTOSP) */
ALTER PROCEDURE PPRELCUSTOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
TIPOPROD VARCHAR(2),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
begin
  /* Procedure Text */
  IF (ICODEMPGP IS NOT NULL) THEN
  BEGIN
    IF (STRLEN(RTRIM(CCODGRUP))<14) THEN
       CCODGRUP = RTRIM(CCODGRUP)||'%';
  END
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  FOR SELECT P.CODPROD,P.REFPROD,P.DESCPROD, P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
   ( (:ICODEMPMC IS NULL) OR (P.CODEMPMC=:ICODEMPMC AND P.CODFILIALMC=:SCODFILIALMC AND
      P.CODMARCA=:CCODMARCA) ) AND
   ((:ICODEMPGP IS NULL) OR (P.CODEMPGP=:ICODEMPGP AND P.CODFILIALGP=:SCODFILIALGP AND
      P.CODGRUP LIKE :CCODGRUP) )
   INTO :CODPROD, :REFPROD, :DESCPROD, :TIPOPROD  DO
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM PPCUSTOPRODSP(:ICODEMP,
        :SCODFILIAL, :CODPROD, :DTESTOQ, :CTIPOCUSTO, :ICODEMPAX,
        :SCODFILIALAX, :ICODALMOX, 'N')
       INTO :CUSTOUNIT, :SLDPROD;
     CUSTOTOT = CUSTOUNIT * SLDPROD;
     SUSPEND;
  END
end
^

/* Alter (SGGERACNFSP) */
ALTER PROCEDURE SGGERACNFSP(TIPO VARCHAR(2),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TIPOVENDA VARCHAR(2),
CODVENDA INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER)
 AS
declare variable icnf bigint;
begin
   --execute procedure sgdebugsp 'sggeracnfsp', 'Entrou na procedure geracnf';
   SELECT BISEQ FROM SGSEQUENCE_IDSP('NCF') INTO :ICNF;
    
   if(TIPO='CP') THEN
   BEGIN
      --execute procedure sgdebugsp 'sggeracnfsp', 'PEGOU tipo CP e O ICNF: '||:ICNF;
      UPDATE CPCOMPRA set CNF=:ICNF WHERE CODEMP=:codempcp AND CODFILIAL=:codfilialcp AND CODCOMPRA=:CODCOMPRA;
   END
   ELSE IF(TIPO='VD') THEN
   BEGIN
      UPDATE VDVENDA set CNF=:ICNF WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
   END
   suspend;
end
^

/* Alter (SGOBJETOATUALIZANIVELSP) */
ALTER PROCEDURE SGOBJETOATUALIZANIVELSP(CODEMP INTEGER)
 RETURNS(IDOBJ VARCHAR(30),
NIVELOBJ SMALLINT)
 AS
declare variable sqltexto varchar(200);
declare variable contador smallint;
declare variable numregobj integer;
begin
  /* Nivel 0 */

   nivelobj = -1;
   update sgobjeto o set o.nivelobj=:nivelobj where codemp=:codemp;

   nivelobj = 0;

   while (:nivelobj<=100) do
   begin
       for select obj.idobj
           from sgobjeto obj
           where obj.codemp=:codemp and obj.nivelobj=-1
           into :idobj do
       begin
           contador = 0;
           select  count(*)
           from rdb$relation_constraints rc
              , rdb$relation_constraints rcpk
              , rdb$ref_constraints rf
           where
               rc.rdb$relation_name=:idobj
              and rc.rdb$constraint_type='FOREIGN KEY'
              and rc.rdb$constraint_name=rf.rdb$constraint_name
              and rcpk.rdb$constraint_name=rf.rdb$const_name_uq
              and not exists( select * from sgobjeto obj1
                where obj1.codemp=:codemp and obj1.idobj=rcpk.rdb$relation_name
                and rcpk.rdb$relation_name<>:idobj
                and rcpk.rdb$relation_name<>'SGFILIAL'
                and obj1.nivelobj>-1 and obj1.nivelobj<:nivelobj
              )
          -- group by 1
           into :contador;
           if (:contador is null or contador=0) then
           begin
              sqltexto = 'select count(*) from '||idobj;
              execute statement sqltexto into :numregobj;
              update sgobjeto
                 set nivelobj=:nivelobj
                 , numregobj=:numregobj
                 where codemp=:codemp and idobj=:idobj;

           end
       end
       nivelobj=nivelobj+1;
   end
   suspend;
   --INSERT INTO SGOBJETO (CODEMP,IDOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ,SIGLAOBJ)
     --  SELECT DISTINCT :ICODEMP, RC.RDB$RELATION_NAME, RC.RDB$RELATION_NAME,  'TB', RC.RDB$RELATION_NAME /*CAST(R.RDB$DESCRIPTION AS VARCHAR(10000)),
       --'S', SUBSTRING(RC.RDB$RELATION_NAME FROM 1 FOR 8)
--       FROM RDB$RELATION_CONSTRAINTS RC, RDB$RELATIONS R
  --     WHERE R.RDB$RELATION_NAME = RC.RDB$RELATION_NAME AND
    --   RC.RDB$RELATION_NAME NOT IN (SELECT IDOBJ FROM SGOBJETO O
--       WHERE O.TIPOOBJ='TB' AND O.IDOBJ=RC.RDB$RELATION_NAME);*/

end
^

/* empty dependent procedure body */
/* Clear: SGATUALIZABDSP for: SGOBJETOINSTBSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter (SGOBJETOINSTBSP) */
ALTER PROCEDURE SGOBJETOINSTBSP(ICODEMP INTEGER)
 AS
begin

   INSERT INTO SGOBJETO (CODEMP,IDOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ,SIGLAOBJ)
       SELECT DISTINCT :ICODEMP, RC.RDB$RELATION_NAME, RC.RDB$RELATION_NAME,
        'TB', RC.RDB$RELATION_NAME /*CAST(R.RDB$DESCRIPTION AS VARCHAR(10000))*/, 'N', SUBSTRING(RC.RDB$RELATION_NAME FROM 1 FOR 8)
       FROM RDB$RELATION_CONSTRAINTS RC, RDB$RELATIONS R
       WHERE R.RDB$RELATION_NAME = RC.RDB$RELATION_NAME AND
       RC.RDB$RELATION_NAME NOT IN (SELECT IDOBJ FROM SGOBJETO O
       WHERE O.TIPOOBJ='TB' AND O.IDOBJ=RC.RDB$RELATION_NAME);

    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.5.5 (21/01/2013)';
    suspend;
end
^

/* Alter (SGSEQUENCE_IDSP) */
ALTER PROCEDURE SGSEQUENCE_IDSP(STABLE_NAME VARCHAR(128))
 RETURNS(BISEQ BIGINT)
 AS
begin
  --execute procedure sgdebugsp 'sggeracnfsp', 'Entrou na procedure SGSEQUENCE';
  BISEQ = NULL;
  SELECT SEQ_ID FROM SGSEQUENCE_ID
    WHERE TABLE_NAME=:sTABLE_NAME
    INTO :BISEQ;
   IF (BISEQ IS NULL) THEN
   BEGIN
     BISEQ = 1;
     INSERT INTO SGSEQUENCE_ID (TABLE_NAME,SEQ_ID)
            VALUES (:sTABLE_NAME, :BISEQ+1);
   END
   ELSE
   BEGIN
      UPDATE SGSEQUENCE_ID SET SEQ_ID=:BISEQ+1 WHERE
          TABLE_NAME=:sTABLE_NAME;
   END
   suspend;
end
^

/* Alter (TKGERACAMPANHACTO) */
ALTER PROCEDURE TKGERACAMPANHACTO(TIPOCTO CHAR(1),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
CODCAMP CHAR(13),
CODEMPCO INTEGER,
CODFILIALCO SMALLINT,
CODCTO INTEGER,
CODEMPAT INTEGER,
CODFILIALAT SMALLINT,
CODATIV INTEGER,
SITHISTTK CHAR(2),
DESCHISTTK VARCHAR(1000))
 AS
declare variable seqcampcto integer; /* Código do contato pra validação. */
declare variable seqsitcamp integer;
declare variable codfilialhi smallint;
declare variable codhisttk integer;
declare variable codempae integer;
declare variable codfilialae smallint;
declare variable codatend integer; /* Código do atendente. */
declare variable codempus integer;
declare variable codfilialus smallint;
declare variable idusu char(8); /* Id do usuário */
begin

    select icodfilial from sgretfilial(:codempca,'TKHISTORICO') into codfilialhi;
    select iseq from spgeranum(:codempca,:codfilialhi,'HI') into codhisttk;
    select codempusu, codfilialusu, idusus from sgretinfousu(:codempca, user) where codempusu=:codempca into
            :codempus, :codfilialus, :idusu;

    select first 1 codemp, codfilial, codatend from atatendente
            where codempus=:codempus and codfilialus=:codfilialus and idusu=:idusu
    into codempae, codfilialae, codatend;

    if(:codatend is null) then
    begin
        exception TKGERACAMANHACTO01 ' - ID: ' || idusu || ' - User: '|| user ;
    end

    -- Verifica se o contato já foi vinculado à campanha

    if ( tipocto = 'O' ) then
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempco=:codempco and cc.codfilialco=:codfilialco and cc.codcto=:codcto
           into :seqcampcto;
    end
    else
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempcl=:codempco and cc.codfilialcl=:codfilialco and cc.codcli=:codcto
           into :seqcampcto;
    end

    if ( (:seqcampcto is null) or (:seqcampcto=0) ) then
    begin
       select max(coalesce(seqcampcto,0)+1) seqcampcto from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           into :seqcampcto;
        if (seqcampcto is null) then
        begin
           seqcampcto = 1;
        end
        if ( tipocto = 'O' ) then
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempco, codfilialco, codcto)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
        else 
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempcl, codfilialcl, codcli)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
               -- exception TKGERACAMANHACTO01 'teste';
        end

    end

    seqsitcamp = 0;
    select max(sc.seqsitcamp) from tksitcamp sc
            where sc.codemp=:codempca and sc.codfilial=:codfilialca and
                sc.codcamp=:codcamp and sc.tipocto=:tipocto
                into :seqsitcamp;

    if(:seqsitcamp is null) then
    begin
        seqsitcamp = 0;
    end

    seqsitcamp = seqsitcamp + 1;

    if ( tipocto = 'O' ) then
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempco,codfilialco,codcto,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end
    else
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempcl,codfilialcl,codcli,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end

    -- Inserindo histórico
    
    if ( tipocto = 'O' ) then 
    begin 
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempco,codfilialco,codcto,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
    else
    begin
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempcl,codfilialcl,codcli,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
   

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
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod varchar(20);
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
declare variable srefprod varchar(20);
declare variable stipoprod varchar(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable qtditorc numeric(15,5);
declare variable calcstcm char(1);
declare variable aliqicmsstcm numeric(9,2);
declare variable vlricmsstcalc numeric(15,5);
begin
-- Inicialização de variaveis
    CALCSTCM = 'N';
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
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc, it.qtditorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda, :qtditorc;

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
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss,coalesce(calcstcm,'N'),aliqicmsstcm
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA, CALCSTCM, ALIQICMSSTCM;

   
   
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
     if (:iqtditvenda<>:qtditorc) then
    begin
       VLRDESCITVENDA = (:VLRDESCITVENDA/:QTDITORC*:IQTDITVENDA);
    end
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
            VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
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
            --Calculo do ICMS ST para fora de mato grosso.

            
            if(calcstcm = 'N') then
            begin
                
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
            --Calculo do ICMS ST para o mato grosso.
            else
            begin
                if(percred>0 and redbaseicmsst='S') then
                begin
                   vlricmsstcalc=0;
                -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                   vlricmsstcalc = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100) );
                   vlrbaseicmsstitvenda =   (vlricmsitvenda + vlricmsstcalc)/(PERCICMSST/100);
                   

                end
                else
                begin
                    -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                    vlricmsstcalc = ( (coalesce(vlrbaseicmsbrutitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
                    
                    vlrbaseicmsstitvenda = ((vlricmsitvenda  + vlricmsstcalc )/(:PERCICMSST/100));
                  
                end

           
                VLROUTRASITVENDA = 0;
                VLRISENTASITVENDA = 0;
                VLRICMSSTITVENDA = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
           

            end 
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
        margemvlagritvenda,vlrbaseicmsbrutitvenda, calcstcm)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda, :calcstcm);
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

/* Alter (VDCONTRATOTOTSP) */
ALTER PROCEDURE VDCONTRATOTOTSP(CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR INTEGER,
CODEMPSC INTEGER,
CODFILIALSC SMALLINT,
CODCONTRSC INTEGER,
CODEMPTA INTEGER,
CODFILIALTA SMALLINT,
CODTAREFA INTEGER,
CODEMPST INTEGER,
CODFILIALST SMALLINT,
CODTAREFAST INTEGER,
DATAINI DATE,
DATAFIM DATE)
 RETURNS(TOTALPREVGERAL NUMERIC(15,5),
TOTALPREVPER NUMERIC(15,5),
TOTALGERAL NUMERIC(15,5),
TOTALCOBCLIGERAL NUMERIC(15,5),
TOTALANT NUMERIC(15,5),
TOTALCOBCLIANT NUMERIC(15,5),
TOTALPER NUMERIC(15,5),
TOTALCOBCLIPER NUMERIC(15,5),
SALDOANT NUMERIC(15,5),
SALDOPER NUMERIC(15,5))
 AS
begin
  if (:codcontrsc is not null) then
  begin
     codempct = codempsc;
     codfilialct = codfilialsc;
     codcontr = codcontrsc;
  end
  if (:codtarefast is not null) then
  begin
     codempta = codempst;
     codfilialta = codfilialst;
     codtarefa = codtarefast;
  end
  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
      on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalgeral,  :totalcobcligeral;

  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
      on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
      a.dataatendo between :dataini and :datafim and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalper,  :totalcobcliper;

  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
     on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
     a.dataatendo < :dataini and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalant,  :totalcobcliant;

  select sum(coalesce(st.tempodectarefa, ta.tempodectarefa) )
    from crtarefa ta
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where ta.codempct=:codempct and ta.codfilialct=:codfilialct and ta.codcontr=:codcontr and
    (:coditcontr is null or ta.coditcontr=:coditcontr) and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into totalprevgeral;

  select sum(tp.tempodectarefa)
   from crtarefa ttp, crtarefaper tp
    left outer join crtarefa ta
      on ta.codemp=ttp.codempta and ta.codfilial=ttp.codfilialta and ta.codtarefa=ttp.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where ttp.codemp=tp.codemp and ttp.codfilial=tp.codfilial and ttp.codtarefa=tp.codtarefa and
      ttp.codempct=:codempct and ttp.codfilialct=:codfilialct and ttp.codcontr=:codcontr and
    (:coditcontr is null or ttp.coditcontr=:coditcontr) and
      tp.dtiniper>=:dataini and tp.dtfimper<=:datafim and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalprevper;

  if (totalgeral is null) then
      totalgeral = 0;
  if (totalcobcligeral is null) then
      totalcobcligeral = 0;
  if (totalant is null) then
      totalant = 0;
  if (totalcobcliant is null) then
      totalcobcliant = 0;
  if (totalper is null) then
      totalper = 0;
  if (totalcobcliper is null) then
      totalcobcliper = 0;
  saldoant = totalprevgeral - totalcobcliant;
  saldoper = totalprevper - totalcobcliper;

  suspend;
end
^

/* Alter (VDRETULTVDCLIPROD) */
ALTER PROCEDURE VDRETULTVDCLIPROD(ICODEMP INTEGER,
ICODCLI INTEGER,
ICODFILIALVD SMALLINT,
ICODVEND INTEGER,
DTINI DATE,
DTFIM DATE,
CODEMPTIPOCL INTEGER,
CODFILIALTIPOCL SMALLINT,
CODTIPOCLI INTEGER)
 RETURNS(RAZCLI_RET CHAR(60),
CODCLI_RET INTEGER,
DESCPROD_RET CHAR(50),
CODPROD_RET INTEGER,
DTEMITVENDA_RET DATE,
DOCVENDA_RET INTEGER,
SERIE_RET CHAR(4),
PRECOVENDA_RET NUMERIC(15,4))
 AS
declare variable icodfilial smallint;
declare variable icodprod integer;
begin

    select icodfilial from sgretfilial(:ICODEMP,'VDVENDA') into :ICODFILIAL;

    for select v.codcli,iv.codprod
        from vdvenda v, vdcliente cl, vditvenda iv
        where
            iv.codemp=v.codemp and iv.codfilial=v.codfilial
            and iv.tipovenda=v.TIPOVENDA and iv.codvenda=v.codvenda
            and v.codemp=:ICODEMP and v.codfilial=:ICODFILIAL
            and (v.codcli=:ICODCLI or :ICODCLI is null)
            and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM
            and cl.codemp=v.codempcl and cl.codfilial=v.codfilialcl and cl.codcli=v.codcli
            and (cl.codtipocli=:codtipocli or :codtipocli is null)
        group by v.codcli,iv.codprod into :ICODCLI,:ICODPROD
    do
    begin
        select first 1 c.razcli, c.codcli, p.descprod, iv.codprod, v.dtemitvenda, v.docvenda, v.serie,
            (iv.vlrliqitvenda/(case when iv.qtditvenda=0 then 1 else iv.qtditvenda end)) precovenda
        from vdcliente c, vdvenda v, vditvenda iv, eqproduto p
        where
            c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli
            and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and iv.codemp=v.codemp
            and iv.codfilial=v.codfilial and iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda
            and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd and p.codprod=iv.codprod
            and v.codempvd=:ICODEMP and v.codfilialvd=:ICODFILIALVD and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM and c.codcli=:ICODCLI and p.codprod=:ICODPROD
            order by v.dtemitvenda desc
            into :RAZCLI_RET, :CODCLI_RET, :DESCPROD_RET, :CODPROD_RET, :DTEMITVENDA_RET, :DOCVENDA_RET, :SERIE_RET,
                 :PRECOVENDA_RET;
            suspend;
    end
end
^

/* Alter (VDUPVENDAORCSP) */
ALTER PROCEDURE VDUPVENDAORCSP(ICODEMP INTEGER,
ICODFILIAL INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIALVD INTEGER,
ICODVENDA INTEGER,
ICODITVENDA INTEGER,
STIPOVENDA CHAR(10))
 AS
declare variable iconta1 decimal(15,5);
declare variable vlrdescvenda decimal(15,5);
declare variable iconta2 decimal(15,5);
declare variable iconta3 decimal(15,5);
begin
  /* Procedure Text */
  

 -- EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'inicio :'|| cast('now' as time);

  INSERT INTO VDVENDAORC (CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
                          CODEMPOR,CODFILIALOR,TIPOORC,CODORC,CODITORC) VALUES
                         (:ICODEMP,:ICODFILIALVD,:STIPOVENDA,:ICODVENDA,:ICODITVENDA,
                          :ICODEMP,:ICODFILIAL,'O',:ICODORC,:ICODITORC);

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim insert vdvendaorc:'|| cast('now' as time);

  UPDATE VDITORCAMENTO SET EMITITORC='S'
       WHERE CODITORC=:ICODITORC AND CODORC=:ICODORC AND TIPOORC='O'
       AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
       AND EMITITORC<>'S';


--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vditorcamento '|| cast('now' as time);
    
  SELECT SUM(QTDITORC), SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO :ICONTA1, :ICONTA2;
--  SELECT SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
--    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
--      INTO ICONTA2;

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim select sum(qtditorc) '|| cast('now' as time);

  IF ( ICONTA1 = ICONTA2 ) THEN
  BEGIN
    UPDATE VDORCAMENTO SET STATUSORC='OV'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC
    AND STATUSORC<>'OV';
    SELECT SUM(IV.QTDITVENDA) FROM VDITVENDA IV, VDVENDAORC VO
       WHERE VO.CODEMP=:ICODEMP AND VO.CODFILIAL=:ICODFILIALVD AND
       VO.TIPOVENDA=:STIPOVENDA AND VO.CODVENDA=:ICODVENDA AND
       IV.CODEMP=VO.CODEMP AND IV.CODFILIAL=VO.CODFILIAL AND
       IV.TIPOVENDA=VO.TIPOVENDA AND IV.CODVENDA=VO.CODVENDA AND
       IV.CODITVENDA=VO.CODITVENDA
          INTO ICONTA3;
  --  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento OV = ICONTA1=ICONTA2 '|| cast('now' as time);

    IF ( ICONTA1<>ICONTA3 ) THEN -- Verifica se o orçamento foi dividido em várias vendas
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA
           AND VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1<>ICONTA3 '|| cast('now' as time);

    END
  END
  ELSE IF (ICONTA1 > ICONTA2) THEN
  BEGIN               
    UPDATE VDORCAMENTO SET STATUSORC='FP'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC AND
     STATUSORC<>'FP';
    SELECT SUM(I.VLRDESCITVENDA) FROM VDITVENDA I
       WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:ICODFILIALVD AND I.TIPOVENDA=:STIPOVENDA AND I.CODVENDA=:ICODVENDA
       INTO :VLRDESCVENDA;
--    EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento STATUSORC=FP ICONTA1 > ICONTA2 '|| cast('now' as time);
    IF (:VLRDESCVENDA<>0) THEN
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA AND
         VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1>ICONTA2 '|| cast('now' as time);
    END 
  END

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim VDUPVENDAORCSP'|| cast('now' as time);

  --  exception vdvendaex06 'teste de velociadade';

  suspend;
end
^

/* Restore procedure body: CPADICITCOMPRARECMERCSP */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
declare variable usaprecocot char(1);
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable codnat char(4);
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitcompra numeric(15,5);
declare variable aprovpreco char(1);
declare variable codemppp integer;
declare variable codfilialpp smallint;
declare variable codplanopag integer;
declare variable vlrproditcompra numeric(15,5);
declare variable qtditrecmerc numeric(15,5);
declare variable codempns integer;
declare variable codfilialns smallint;
declare variable numserietmp varchar(30);
declare variable percprecocoletacp numeric(15,5);
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;
    
    -- Buscando preferências GMS
    select coalesce(p8.percprecocoletacp,100) percprecocoletacp
    from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :percprecocoletacp;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc, ir.qtditrecmerc,
        ir.codempns, ir.codfilialns, ir.numserie
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc, :qtditrecmerc,
        :codempns, :codfilialns, :numserietmp
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov,null)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Se deve buscar preço de cotação.
                if( 'S' = :usaprecocot) then
                begin
                    -- Deve implementar ipi, vlrliq etc... futuramente...
                    select first 1 ct.precocot
                    from cpcotacao ct, cpsolicitacao sl, cpitsolicitacao iso
                    left outer join eqrecmerc rm on
                    rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket
                    where
                    iso.codemp = sl.codemp and iso.codfilial=sl.codfilial and iso.codsol=sl.codsol
                    and ct.codemp=iso.codemp and ct.codfilial=iso.codfilial and ct.codsol=iso.codsol and ct.coditsol=iso.coditsol
                    and iso.codemppd=:codemppd and iso.codfilialpd=:codfilialpd and iso.codprod=:codprod
                    and ct.codempfr=:codempfr and ct.codfilialfr=:codfilialfr and ct.codfor=:codfor
                    and (ct.dtvalidcot>=cast('today' as date) and (ct.dtcot<=cast('today' as date)))
                    and ct.sititsol not in ('EF','CA')

                    and ( (ct.rendacot = rm.rendaamostragem) or ( coalesce(ct.usarendacot,'N') = 'N') )

                    and ( (ct.codemppp=:codemppp and ct.codfilialpp=:codfilialpp and ct.codplanopag=:codplanopag)
                       or (ct.codplanopag is null))

                    order by ct.dtcot desc
                    into :precoitcompra;

                    if(:precoitcompra is not null) then
                    begin
                        -- Indica que o preço é aprovado (cotado anteriormente);
                        aprovpreco = 'S';
                    end

                end

                -- Se não conseguiu obter o preço das cotações
                if(precoitcompra is null) then
                begin
                    -- Buscando preço de compra da tabela de custos de produtos
                    select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                    cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                    into :precoitcompra;

                end

                -- verifica se quantidade está zerada (coleta) se estiver preechida (trata-se de uma pesagem)
                if ( (qtditcompra is null) or (qtditcompra = 0) ) then 
                begin
                    qtditcompra = qtditrecmerc;
                end

                if ( ( :percprecocoletacp is not null) and (:percprecocoletacp<>100) ) then
                begin
                   precoitcompra = cast( :precoitcompra / 100 * :percprecocoletacp as decimal(15,5) ); 
                end
                 
                vlrproditcompra = :precoitcompra * qtditcompra;

                -- Inserir itens
                
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra,
                codempns, codfilialns, numserietmp)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra,
                :codempns, :codfilialns,  :numserietmp) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Restore procedure body: EQGERARMAOSSP */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialtm1 smallint;
declare variable codfilialtm smallint;
declare variable codemppd integer;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable qtdaprov numeric(15,5) = 0;
begin


    -- buscando centro de custo do usuário atual
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codemprm, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando filial da rma
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQRMA')
    into :codfilialrma;

    -- Buscando filial do lote
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQLOTE')
    into :codfilialle;

    -- Buscando filial do tipo de movimento
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQTIPOMOV')
    into :codfilialtm;

    -- buscando tipo de movimento para RMA
    select codfilialt8,codtipomov8 from sgprefere1
    where codemp=:codemprm and codfilial=:codfilialrm
    into :codfilialtm1,:codtipomov1;

    -- Buscado a situação pardrão para RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:codemprm and
    codfilial=(select icodfilial from sgretfilial(:codemprm,'SGPREFERE5'))
    into :statusrmager;

    if(:statusrmager is null) then
    begin
       statusrmager = 'PE';
    end

    -- Carregando quantidade aprovada...
    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    -- Buscando código novo código de RMA.
    select coalesce((max(codrma)+1),1) from
    eqrma where codemp=:codemprm and codfilial=:codfilialrma
    into :codrma;

    -- Inserindo nova RMA
    insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempos,codfilialos,ticket,coditrecmerc,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,:coditrecmerc,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditrecmerc, os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
--    and ( (os.coditrecmerc=:coditrecmerc) or (:coditrecmerc is null) )
    and os.gerarma='S'
    into :coditrecmerc, :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
    do

    begin

       select coalesce((max(coditrma)+1),1) from eqitrma
       where codemp=:codemprm and codfilial=:codfilialrma and codrma=:codrma
       into :coditrma;

       if(:statusrmager='AF') then
       begin
         STATUSAPROVRMAGER = 'AT';
         QTDAPROV = :qtd;
       end

       insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            CODEMPOS,CODFILIALOS,TICKET,CODITRECMERC,CODITOS
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE',
                            :codemprm, :codfilialrm, :ticket, :coditrecmerc,:coditos
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* Restore procedure body: EQGERARMASP */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
declare variable seqof smallint;
declare variable idusu1 char(8);
declare variable seqitop integer;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codrma integer;
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialpd smallint;
declare variable codfilialtm1 smallint;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
declare variable codfase integer;
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc integer;
declare variable coditos integer;
begin

    -- Buscando informações do usuário
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codempop, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando preferencias de tipo de movimento para OP
    select codfilialt8,codtipomov8
    from sgprefere1 where codemp=:CODEMPOP and codfilial=(select icodfilial from sgretfilial(:CODEMPOP, 'SGPREFERE1'))
    into :CODFILIALTM1,:CODTIPOMOV1;

    --Buscando informações da OP.
    select op.codempos, op.codfilialos, op.ticket, op.coditrecmerc, op.coditos
    from  ppop op
    where op.codemp=:codempop and op.codfilial=:codfilialop and op.codop=:codop and op.seqop=:seqop
    into :codempos, :codfilialos, :ticket, :coditrecmerc, :coditos;

    -- Buscando preferencias para geração de RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:CODEMPOP and
    codfilial=(select icodfilial from sgretfilial(:CODEMPOP,'SGPREFERE5'))
    into :STATUSRMAGER;

    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    for select codfilialfs,codfase,seqof from ppopfase opf
        where opf.codemp=:CODEMPOP and opf.codfilial=:CODFILIALOP and
        opf.codop=:CODOP and  opf.seqop=:SEQOP and
        (select count(1) from ppitop it
        where it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP and
        it.codempfs=opf.codempfs and it.codfilialfs=opf.codfilialfs and
        it.codfase=opf.codfase and it.gerarma='S' and
        it.codop=:CODOP and it.seqop=:SEQOP) > 0
        into :codfilialfase,:codfase,:SEQOF do
    begin
        select coalesce((max(codrma)+1),1)
        from eqrma
        where codemp=:CODEMPOP and codfilial=:CODFILIALOP into :CODRMA;

        insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempof,codfilialof,codop,seqop,seqof,
                     motivorma, codempos, codfilialos, ticket, coditrecmerc)
                     values
                     (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQRMA')),:CODRMA,
                      :CODEMPOP, :CODFILIALUSU1,:IDUSU1,
                      null,null,null,
                      null,null,null,
                      :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQTIPOMOV')),
                      :CODTIPOMOV1,
                      :CODEMPOP,:CODFILIALCCUSU1,:ANOCCUSU1,:CODCCUSU1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :CODEMPOP,:CODFILIALOP,:CODOP,:SEQOP,:SEQOF,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OP:'||:CODOP||' SEQ:'||:SEQOP||' - FASE:'||:CODFASE,
                      :codempos, :codfilialos, :ticket, :coditrecmerc
        );

        for select it.seqitop,it.codfilialpd,it.codprod,it.refprod,it.qtditop-coalesce(it.qtdcopiaitop,0),it.codfilialle,it.codlote,
            (select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)),
            (SELECT CODFILIALAX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod),
            (SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod)
            from ppitop it, eqproduto pd
            where
            pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
            and it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP
            and it.codop=:CODOP and it.seqop=:SEQOP and it.codempfs=:CODEMPOP
            and it.codfilialfs=:CODFILIALFASE
            and it.codfase = :CODFASE and it.gerarma='S'
            and (('S'=(select ratauto from sgprefere5 where codemp=it.codemp and codfilial=it.codfilial))
            and (it.qtditop-coalesce(it.qtdcopiaitop, 0))<=(SELECT L.SLDLOTE FROM EQLOTE L
                                                            WHERE L.CODEMP=it.codemple AND L.CODFILIAL=it.codfilialle AND
                                                            L.CODPROD=it.codprod AND L.CODLOTE=it.codlote)
            or pd.cloteprod = 'N'
                                                            )
            into :SEQITOP, :CODFILIALPD,:CODPROD,:REFPROD,:QTD,
            :CODFILIALLE,:CODLOTE,:CUSTOMPMIT,:CODFILIALAX,:CODALMOX  DO
        begin
            select coalesce((max(coditrma)+1),1) from eqitrma
            where codemp=:CODEMPOP and codfilial=:CODFILIALOP and
            codrma=:CODRMA into :coditrma;

            if(:STATUSRMAGER='AF')then
            begin
                STATUSAPROVRMAGER = 'AT';
                QTDAPROV = :QTD;
            end

            insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            codempos, codfilialos, ticket, coditrecmerc, coditos
                            )
                            values
                            (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQITRMA')),:CODRMA,
                            :coditrma,
                            :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQPRODUTO')),
                            :CODPROD,:REFPROD,:QTD,:QTDAPROV,0,:CUSTOMPMIT,:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQLOTE')),:CODLOTE,
                            :CODEMPOP,:CODFILIALAX,:CODALMOX,
                            :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                            :codempop, :codfilialos, :ticket, :coditrecmerc, :coditos
                            );

            update ppitop it set it.gerarma='N' where it.CODEMP=:CODEMPOP AND
                it.CODFILIAL=:CODFILIALOP AND
                it.codop=:CODOP and it.seqop=:SEQOP and it.seqitop=:seqitop;

        end

    end

    suspend;

end
^

/* Restore procedure body: SGATUALIZABDSP */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
DECLARE VARIABLE ICODEMPTMP INTEGER;
begin
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Entrou no atualizabdsp '||cast(ICODEMP AS CHAR(10)));*/

  EXECUTE PROCEDURE SGDADOSINISP(:ICODEMP);
  EXECUTE PROCEDURE SGGRANTUSERSP ;
  FOR SELECT CODEMP FROM SGEMPRESA INTO :ICODEMPTMP DO
  BEGIN
     EXECUTE PROCEDURE SGOBJETOINSTBSP(:ICODEMPTMP);
     EXECUTE PROCEDURE SGGRANTADMSP(:ICODEMPTMP);
  END
  suspend;
end
^

/* Create Trigger... */
CREATE TRIGGER CRFEEDBACKTGBU FOR CRFEEDBACK
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=user;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER CRMOTIVOFBTGBU FOR CRMOTIVOFB
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=user;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER CRPESSOATGBU FOR CRPESSOA
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER PPESTRUTURATGAI FOR PPESTRUTURA
ACTIVE AFTER INSERT POSITION 0 
AS
    declare variable iFinaliza smallint;
begin
     IF(NEW.ativoest='S') THEN
      BEGIN
        select count(1) from ppestrufase ef
            where ef.codemp=new.codemp and ef.codfilial=new.codfilial and
            ef.codprod=new.codprod and ef.seqest=new.seqest and ef.finalizaop='S'
            into :iFinaliza;
        if ((:iFinaliza is null) or (:iFinaliza<1) ) then
        begin
          exception ppestruturaex01;
        end
      END
end
^

CREATE TRIGGER PPESTRUTURATGAU FOR PPESTRUTURA
ACTIVE AFTER UPDATE POSITION 0 
AS
declare variable iCount smallint;
declare variable sCertfsc char;
begin
    --verifica se estruta está em fase de ativação
  IF(NEW.ativoest='S' AND OLD.ativoest='N') THEN
  BEGIN
     --busca na tabela de produtos se produto é certificado FSC
     select pd.certfsc from eqproduto pd
        where pd.codemp=new.codemp and pd.codfilial=new.codfilial and pd.codprod=new.codprod
        into :sCertfsc;

     --Se produto é certificado, e não possui nenhum subproduto cadastrado lança uma exceção.
     IF(:sCertfsc='S') then
     BEGIN
        select count(1) from PPITESTRUTURASUBPROD es
           where es.codemp= new.codemp and es.codfilial=new.codfilial and es.codprod= new.codprod  and es.seqest = new.seqest
           into :iCount;
        IF ((:iCount is null) or (:iCount<1) ) THEN
        BEGIN
           exception ppestruturaex02;
        END
     END
  END
end
^

CREATE TRIGGER SGPROXYWEBTGBU FOR SGPROXYWEB
ACTIVE BEFORE UPDATE POSITION 0 
AS
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^


/* Create Views... */

/* Alter exist trigger... */
ALTER TRIGGER CPCOMPRATGAU
AS
  DECLARE VARIABLE iCodPag INTEGER;
  DECLARE VARIABLE dVLR NUMERIC(15, 5);
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE TIPOMOV CHAR(2);        
  DECLARE VARIABLE ICODITEM SMALLINT;
  DECLARE VARIABLE VLRITEM NUMERIC(15, 5);  
  DECLARE VARIABLE QTDITEM NUMERIC(15, 5);
  DECLARE VARIABLE PERCITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE VLRITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE PERCITADIC NUMERIC(15, 5);
  DECLARE VARIABLE VLRITADIC NUMERIC(15, 5);
  DECLARE VARIABLE SCODFILIALP1 SMALLINT;
  DECLARE VARIABLE GERAPAGEMIS CHAR(1);
  DECLARE VARIABLE DTBASE DATE;
  DECLARE VARIABLE ICODMODNOTA INTEGER;
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) )) ) THEN
  BEGIN
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :SCODFILIALP1;
      SELECT P1.GERAPAGEMIS FROM SGPREFERE1 P1 WHERE  P1.CODEMP=new.CODEMP AND
         P1.CODFILIAL=:SCODFILIALP1 INTO :GERAPAGEMIS;
      IF (GERAPAGEMIS IS NULL) THEN
      BEGIN
        GERAPAGEMIS = 'N';
      END
      IF (GERAPAGEMIS='S') THEN
      BEGIN
        DTBASE = new.DTEMITCOMPRA;
      END
      ELSE
      BEGIN
        DTBASE = new.DTENTCOMPRA;
      END
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
      SELECT CODPAG FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA
             AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALPAG INTO iCodPag;
      SELECT TIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
             AND CODEMP=new.CODEMPTM AND CODFILIAL=new.CODFILIALTM INTO TIPOMOV;
      IF ((NOT TIPOMOV IN ('DV','TR')) AND (
         (iCodPag IS NULL) OR (
         (new.CODPLANOPAG != old.CODPLANOPAG) OR
         (new.CODFOR != old.CODFOR) OR
         (new.VLRLIQCOMPRA != old.VLRLIQCOMPRA) OR
         (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
         (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
         (new.DOCCOMPRA != old.DOCCOMPRA) OR
         (new.CODBANCO != old.CODBANCO)))) THEN
      BEGIN
        dVLR = new.VLRLIQCOMPRA;
        IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P1','C1'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=:IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,
               new.CODPLANOPAG,new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,
               :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,
               new.FLAG,new.CODEMP,new.CODFILIAL, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
               
          SELECT CODMODNOTA FROM EQTIPOMOV m WHERE m.codemp=new.codemptm and m.codfilial=new.codfilialtm and m.codtipomov=new.codtipomov INTO ICODMODNOTA;
          --execute procedure sgdebugsp 'cpcompratgbu', 'PEGOU O MODELO DE NOTA:'||:ICODMODNOTA;
          IF(:ICODMODNOTA=55) THEN
          BEGIN
              --execute procedure sgdebugsp 'cpcompratgbu', 'ENTROU O MODELO DA NOTA É IGUAL A 55:';
              execute procedure sggeracnfsp('CP', null,null,null,null,new.codemp, new.codfilial,new.codcompra);
          END
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE , new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
               new.CODEMPFR, new.CODFILIALFR, new.CODFOR, :dVLR, :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO, new.CODFILIALBO, new.CODBANCO, new.FLAG, new.CODEMP, new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag);
        END
      END
    /**
     Movimento do estoque
    **/
    /* Avisa os itens que a data de saida foi alterada */
      IF ( (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
           (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
           (new.DOCCOMPRA != old.DOCCOMPRA) OR
           (new.CODTIPOMOV != old.CODTIPOMOV) )  THEN
        UPDATE CPITCOMPRA SET CODITCOMPRA=CODITCOMPRA WHERE
             CODCOMPRA = old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;

      IF ( new.codimp is null and (((new.VLRFRETECOMPRA > 0) AND ( NOT new.VLRFRETECOMPRA = old.VLRFRETECOMPRA)) or ((new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)))  ) THEN
      BEGIN
          FOR SELECT IT.CODITCOMPRA, IT.VLRLIQITCOMPRA, IT.QTDITCOMPRA FROM CPITCOMPRA IT
              WHERE IT.CODEMP=new.CODEMP AND IT.CODFILIAL=new.CODFILIAL AND IT.CODCOMPRA=new.CODCOMPRA
              INTO :ICODITEM, :VLRITEM, :QTDITEM
              DO
              BEGIN

                  IF ( (new.vlrfretecompra > 0) AND ( NOT new.vlrfretecompra = old.vlrfretecompra)  ) THEN
                  begin
                      PERCITFRETE = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITFRETE =  (:PERCITFRETE * (new.VLRFRETECOMPRA / 100)) / COALESCE(:QTDITEM, 1);

                      UPDATE CPITCOMPRA CIT
                          SET VLRFRETEITCOMPRA=:VLRITFRETE
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;
                  end
                  IF ( (new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)  ) THEN
                  begin
                      PERCITADIC = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITADIC =  (:PERCITADIC * (new.VLRADICCOMPRA / 100)) / COALESCE(:QTDITEM, 1);
                      UPDATE CPITCOMPRA CIT
                          SET VLRADICITCOMPRA=:VLRITADIC
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;

                  end


              END
        END
          IF ((substr(new.STATUSCOMPRA,1,1)='X') AND (substr(old.STATUSCOMPRA,1,1) IN ('P','C'))) THEN
          BEGIN
              UPDATE CPITCOMPRA SET QTDITCOMPRACANC=QTDITCOMPRA, QTDITCOMPRA=0 WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
              DELETE FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
          END

        -- Atualização do status do recebimento da mercadoria quando a nota for emitida.
        if( old.statuscompra!='ET' and new.statuscompra='ET') then
        begin
            
           update eqrecmerc rm set rm.status='NE'
           where rm.codemp=new.codemp and rm.codfilial=new.codfilial
           and rm.ticket in
           (
            select ticket
            from eqitrecmercitcp rm
            where rm.codempcp=new.codemp and rm.codfilialcp=new.codfilial
            and rm.codcompra=new.codcompra
           );

        end

    END
END
^

/* Alter exist trigger... */
ALTER TRIGGER CPIMPORTACAOTGBI
AS
begin

    -- Convertendo valores em moeda corrente / estrangeira
  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
  begin
    new.vlrfrete    =   new.vlrfretemi      *   new.cotacaomoeda;
    new.vlrseguro   =   new.vlrseguromi     *   new.cotacaomoeda;
    new.vlrthcmi    =   new.vlrthc          /   new.cotacaomoeda;
  end


end
^

/* Alter exist trigger... */
ALTER TRIGGER CPIMPORTACAOTGBU
as
begin
  if (new.emmanut is null) then
     new.emmanut='N';
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    new.dtalt=cast('now' as date);
    new.idusualt=user;
    new.halt = cast('now' as time);

    -- Não permitindo valores negativos
    if (new.pesobruto       < 0) then new.pesobruto     = 0;
    if (new.pesoliquido     < 0) then new.pesoliquido   = 0;
    if (new.vmlemi          < 0) then new.pesobruto     = 0;
    if (new.vmldmi          < 0) then new.pesobruto     = 0;
    if (new.vlrii           < 0) then new.pesobruto     = 0;
    if (new.vlripi          < 0) then new.pesobruto     = 0;
    if (new.vlrpis          < 0) then new.pesobruto     = 0;
    if (new.vlrcofins       < 0) then new.pesobruto     = 0;
    if (new.vlrtxsiscomex   < 0) then new.pesobruto     = 0;

    -- Convertendo valores em moeda corrente / estrangeira
    if( (old.cotacaomoeda != new.cotacaomoeda) or (old.vlrfretemi != new.vlrfretemi) or (old.vlrseguromi != new.vlrseguromi) or (old.vlrthc != new.vlrthc) ) then
    begin
        new.vlrfrete    =   new.vlrfretemi      *   new.cotacaomoeda;
        new.vlrseguro   =   new.vlrseguromi     *   new.cotacaomoeda;
        new.vlrthcmi    =   new.vlrthc          /   new.cotacaomoeda;
    end

  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAD
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
            -- Icms substituição tributária
            cp.vlrbaseicmsstcompra = coalesce(cp.vlrbaseicmsstcompra,0) - coalesce(old.vlrbaseicmsstitcompra,0),
            cp.vlricmsstcompra = coalesce(cp.vlricmsstcompra,0) - coalesce(old.vlricmsstitcompra,0),

            cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra,
            cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra,
            cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra,
            cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra,
            cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra,
            cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra,
            cp.vlrfretecompra = cp.vlrfretecompra - old.vlrfreteitcompra,
            cp.vlradiccompra = cp.vlradiccompra - old.vlradicitcompra
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
        old.codempax, old.codfilialax, old.codalmox, null, 'S');

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAI
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
        :dtcompra, :doccompra, :flag, new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox, null, 'S'
    );

    -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure cpitcompraseriesp('I', new.codemp, new.codfilial, new.codcompra, new.coditcompra, new.codemppd, new.codfilialpd, new.codprod, new.numserietmp, new.qtditcompra);

    -- Gerando tabela de informações fiscais adicionais (lfitcompra)
    if(calctrib='S') then
    begin
       -- Inserindo registros na tabela de informações fiscais complementares (LFITCOMPRA)
        execute procedure lfgeralfitcomprasp(new.codemp, new.codfilial, new.codcompra, new.coditcompra);
    end

    -- Atualizando cabeçalho da compra

    update cpcompra cp set
    cp.vlrfretecompra = cp.vlrfretecompra + new.vlrfreteitcompra,
    cp.vlradiccompra = cp.vlradiccompra + new.vlradicitcompra
    where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial;


end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAU
as

declare variable ddtcompra date;
declare variable cflag char(1);
declare variable idoccompra integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable calctrib char(1);
declare variable codimp integer;

begin
    -- Se não estiver em manutenção...
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin
        -- Buscando informações da compra
        select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov, c.calctrib, c.codimp
        from cpcompra c
        where c.codcompra = new.codcompra and c.codemp=new.codemp and c.codfilial = new.codfilial
        into :ddtcompra, :cflag, :idoccompra, :icodemptm, :scodfilialtm, :icodtipomov, :calctrib, :codimp;

        if(:codimp is null) then
        begin
            -- Atualizando cabeçalho da compra (não atualiza frete para não entrar em loop);
            update cpcompra cp set
                cp.vlrdescitcompra = cp.vlrdescitcompra -old.vlrdescitcompra + new.vlrdescitcompra,
                cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra + new.vlrproditcompra,
                cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra + new.vlrbaseicmsitcompra,
                cp.vlricmscompra = cp.vlricmscompra -old.vlricmsitcompra + new.vlricmsitcompra,
                -- Icms substituição tributária
                cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra - old.vlrbaseicmsstitcompra + new.vlrbaseicmsstitcompra,
                cp.vlricmsstcompra = cp.vlricmsstcompra -old.vlricmsstitcompra + new.vlricmsstitcompra,
                
                cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra + new.vlrisentasitcompra,
                cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra + new.vlroutrasitcompra,
                cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra + new.vlrbaseipiitcompra,
                cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra + new.vlripiitcompra,
                cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra + new.vlrliqitcompra,
                cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra + new.vlrfunruralitcompra
            where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codimp is null;
        end
        else
        begin

            -- Atualizando cabeçalho da compra (atualiza o frete);

            update cpcompra cp set
                cp.vlrdescitcompra = cp.vlrdescitcompra -old.vlrdescitcompra + new.vlrdescitcompra,
                cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra + new.vlrproditcompra,
                cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra + new.vlrbaseicmsitcompra,
                cp.vlricmscompra = cp.vlricmscompra -old.vlricmsitcompra + new.vlricmsitcompra,
                -- Icms substituição tributária
                cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra - old.vlrbaseicmsstitcompra + new.vlrbaseicmsstitcompra,
                cp.vlricmsstcompra = cp.vlricmsstcompra -old.vlricmsstitcompra + new.vlricmsstitcompra,

                cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra + new.vlrisentasitcompra,
                cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra + new.vlroutrasitcompra,
                cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra + new.vlrbaseipiitcompra,
                cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra + new.vlripiitcompra,
                cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra + new.vlrliqitcompra,
                cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra + new.vlrfunruralitcompra,
                cp.vlrfretecompra = cp.vlrfretecompra - old.vlrfreteitcompra + new.vlrfreteitcompra,
                cp.vlradiccompra = cp.vlradiccompra - old.vlradicitcompra + new.vlradicitcompra
            where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codimp is not null;

        end

        -- Atualizando movimentação de estoque
        execute procedure eqmovprodiudsp('U', new.codemppd, new.codfilialpd, new.codprod,
        new.codemple, new.codfilialle, new.codlote, :icodemptm, :scodfilialtm, :icodtipomov, null, null, null,
        new.codemp, new.codfilial, new.codcompra, new.coditcompra, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, new.codempnt, new.codfilialnt, new.codnat, :ddtcompra, :idoccompra, :cflag,
        new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox, null, 'S');

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
ALTER TRIGGER CPITCOMPRATGBI
as

declare variable srefprod varchar(20);
declare variable habCustoCompra char(1);
declare variable calctrib char(1);
declare variable utilizatbcalcca char(1) ;
declare variable codempcc integer;
declare variable codfilialcc smallint;
declare variable codcalc integer;
declare variable codempcf integer;
declare variable codfilialcf smallint;
declare variable codcf integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;



begin

    if (new.calccusto is null) then 
       new.calccusto = 'S';
    -- Buscando preferências
    select p.custocompra, p.utilizatbcalcca from sgprefere1 p
    where p.codemp=new.codemp and p.codfilial=new.codfilial
    into :habCustoCompra, :utilizatbcalcca;
    if (utilizatbcalcca is null) then
       utilizatbcalcca = 'N';

    --Buscando informações da compra
    select cp.calctrib from cpcompra cp
    where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
    into :calctrib;

    -- Buscando referência do produto
    select refprod from eqproduto
    where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
    into srefprod;

    -- Carregando valores padrão
    if (new.refprod is null) then new.refprod = srefprod;
    if (new.vlrdescitcompra is null) then new.vlrdescitcompra = 0;
    if (new.vlrbaseicmsitcompra is null) then new.vlrbaseicmsitcompra = 0;
    if (new.vlricmsitcompra is null) then new.vlricmsitcompra = 0;
    if (new.vlrbaseipiitcompra is null) then new.vlrbaseipiitcompra = 0;
    if (new.vlripiitcompra is null) then new.vlripiitcompra = 0;
    if (new.vlrliqitcompra is null) then new.vlrliqitcompra = 0;
    if (new.vlradicitcompra is null) then new.vlradicitcompra = 0;
    if (new.vlrfreteitcompra is null) then new.vlrfreteitcompra = 0;
    if (new.vlrbaseicmsstitcompra is null) then new.vlrbaseicmsstitcompra = 0;
    if (new.vlricmsstitcompra is null) then new.vlricmsstitcompra = 0;
    

    if(new.vlrliqitcompra=0) then
    begin
       new.vlrliqitcompra = (new.qtditcompra * new.precoitcompra) - new.vlrdescitcompra + new.vlradicitcompra;
    end

    -- Buscando e carregando almoxarifado do produto
    if (new.codalmox is null) then
    begin
        select codempax, codfilialax, codalmox from eqproduto
        where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
        into new.codempax, new.codfilialax, new.codalmox;
    end

    -- Descontando o valor do funrual do valor liquido do ítem
    if( new.vlrfunruralitcompra > 0 ) then
    begin
        new.vlrliqitcompra = new.vlrliqitcompra - new.vlrfunruralitcompra;
    end

   -- Buscando e carregando retenção de tributos
    if(calctrib='S') then
    begin
        select coalesce(bc.vlrbasefunrural,0), coalesce(bc.aliqfunrural,0), coalesce(bc.vlrfunrural,0), bc.codempif, bc.codfilialif, bc.codfisc, bc.coditfisc
        from lfbuscatribcompra(new.codemp, new.codfilial, new.codcompra, new.codemppd, new.codfilialpd, new.codprod, new.vlrliqitcompra) bc
        into new.vlrbasefunruralitcompra, new.aliqfunruralitcompra, new.vlrfunruralitcompra,
        new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
    end

           -- Buscando e carregando custo do produto
    if ( ( ('N' = habCustoCompra) or (new.custoitcompra is null) ) and (new.calccusto='S') ) then
    begin
        if (utilizatbcalcca='N') then
        begin
            select nvlrcusto from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra)
            into new.custoitcompra;
        end
        else
        begin

            if (new.coditfisc is null) then
            begin
               select codempfr, codfilialfr, codfor, codemptm, codfilialtm, codtipomov
                  from cpcompra
                  where codemp=new.codemp and codfilial=new.codfilial and codcompra=new.codcompra
                  into :codempcf, :codfilialcf, :codcf, :codemptm, :codfilialtm, :codtipomov;

               select itfisc.codempif, itfisc.codfilialif, itfisc.codfisc, itfisc.coditfisc
                from lfbuscafiscalsp(new.codemppd, new.codfilialpd, new.codprod, :codempcf, :codfilialcf, :codcf
                 , :codemptm, :codfilialtm, :codtipomov, 'CP', new.codnat, null, null, null, null) itfisc
               into new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
            end

            select codempcc, codfilialcc, codcalc from lfitclfiscal itcl
              where itcl.codemp=new.codempif and itcl.codfilial=new.codfilialif and itcl.codfisc=new.codfisc and itcl.coditfisc=new.coditfisc
            into :codempcc, :codfilialcc, :codcalc ;

            select vlrcusto from lfcalccustosp01( :codempcc, :codfilialcc, :codcalc, new.qtditcompra, new.vlrproditcompra*new.qtditcompra
             , new.vlricmsitcompra, new.vlripiitcompra, 0, 0, new.vlrissitcompra, new.vlrfunruralitcompra
             , new.vlriiitcompra, 0, 0, 0, 0, 0 )
            into new.custoitcompra;

        end
    end

    new.calccusto = 'S';
    
    --Atualizando totais da compra
    update cpcompra cp set cp.vlrdescitcompra=cp.vlrdescitcompra + new.vlrdescitcompra,
    cp.vlrprodcompra = cp.vlrprodcompra + new.vlrproditcompra,
    cp.vlrbaseicmscompra = cp.vlrbaseicmscompra + new.vlrbaseicmsitcompra,
    cp.vlricmscompra = cp.vlricmscompra + new.vlricmsitcompra,
    -- Icms subtituição tributária
    cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra + new.vlrbaseicmsstitcompra,
    cp.vlricmsstcompra = cp.vlricmsstcompra + new.vlricmsstitcompra,
    -- 
    cp.vlrisentascompra = cp.vlrisentascompra + new.vlrisentasitcompra,
    cp.vlroutrascompra = cp.vlroutrascompra + new.vlroutrasitcompra,
    cp.vlrbaseipicompra = cp.vlrbaseipicompra + new.vlrbaseipiitcompra,
    cp.vlripicompra = cp.vlripicompra + new.vlripiitcompra,
    cp.vlrliqcompra = cp.vlrliqcompra + new.vlrliqitcompra,
    cp.vlrfunruralcompra = coalesce(cp.vlrfunruralcompra,0) + coalesce(new.vlrfunruralitcompra,0)
    where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGBU
as

declare variable srefprod varchar(20);
declare variable sadicfrete char(1);
declare variable sadicadic char(1);
declare variable habcustocompra char(1);
declare variable vlritcusto numeric(15, 5);
declare variable statuscompra char(2);
declare variable calctrib char(1);
declare variable utilizatbcalcca char(1);
declare variable codempcc integer;
declare variable codfilialcc smallint;
declare variable codcalc integer;

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        if ( new.vlrbaseicmsstitcompra is null ) then new.vlrbaseicmsstitcompra=0;
        if (new.vlricmsstitcompra is null ) then new.vlricmsstitcompra=0;
        
        
        -- Atulizando log de alteração
        new.dtalt = cast('today' as date);
        new.idusualt = user;
        new.halt = cast('now' as time);

        -- Não permite a alteração do produto
        if (new.codprod != old.codprod) then
        begin
            exception cpitcompraex01;
        end

        -- Não permite a alteração do lote
        if (new.codlote != old.codlote) then
        begin
            exception cpitcompraex02;
        end

        -- Se o código do almoxarifado estiver nulo, preenche como almoxarifado padrão do produto
        if (new.codalmox is null) then
        begin
            select codempax, codfilialax, codalmox from eqproduto
            where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
            into new.codempax, new.codfilialax, new.codalmox;
        end

        -- Não permite a troca de almoxarifado
        if ( old.codalmox is not null and old.codalmox != new.codalmox ) then
        begin
            exception eqalmox01;
        end

        -- Busca referência do produto
        select refprod from eqproduto
        where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
        into srefprod;

        -- Busca informações no cabeçalho da compra
        select adicfretecompra, adicadiccompra, statuscompra
        from cpcompra where
        codemp=new.codemp and codfilial=new.codfilial and codcompra=new.codcompra
        into :sadicfrete, :sadicadic, :statuscompra;

        /* Caso a nota não seja cancelada */
        if ((substr(:statuscompra,1,1)<>'X')) then
        begin

            vlritcusto = new.vlrliqitcompra/new.qtditcompra;

            -- Buscando informações das preferencias gerais
            select p.custocompra, p.utilizatbcalcca from sgprefere1 p
            where p.codemp=new.codemp and p.codfilial=new.codfilial
            into :habcustocompra, :utilizatbcalcca;

            --Buscando informações da compra
            select cp.calctrib from cpcompra cp
            where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
            into :calctrib;

            --  Atualizado a referencia do produto
            if (new.refprod is null) then
            begin
                new.refprod = srefprod;
            end

            -- Adicionando o frete ao valor de custo do item
            if (:sadicfrete = 'S' ) then
            begin
                vlritcusto = vlritcusto + new.vlrfreteitcompra;
            end

            -- Adiconando valores adicionais ao custo do item
            if (:sadicadic = 'S') then
            begin
                vlritcusto = vlritcusto + new.vlradicitcompra;
            end

            new.custoitcompra=:vlritcusto;

            -- Buscando e carregando retenção de tributos
            if(calctrib='S') then
            begin
                select coalesce(bc.vlrbasefunrural,0), coalesce(bc.aliqfunrural,0), coalesce(bc.vlrfunrural,0), bc.codempif, bc.codfilialif, bc.codfisc, bc.coditfisc
                from lfbuscatribcompra(new.codemp, new.codfilial, new.codcompra, new.codemppd, new.codfilialpd, new.codprod, new.vlrliqitcompra) bc
                into new.vlrbasefunruralitcompra, new.aliqfunruralitcompra, new.vlrfunruralitcompra,
                new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
            end

            -- Descontando o valor do funrual do valor liquido do ítem
            if( new.vlrfunruralitcompra > 0 ) then
            begin
                new.vlrliqitcompra = new.vlrliqitcompra - new.vlrfunruralitcompra;
            end

            -- Buscando e carregando custo do produto
            if ( ('N' = habCustoCompra) or (new.custoitcompra is null)) then
            begin
                if (utilizatbcalcca='N') then
                begin
                    select nvlrcusto from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra)
                    into new.custoitcompra;
                end
                else
                begin
                    select codempcc, codfilialcc, codcalc from lfitclfiscal itcl
                      where itcl.codemp=new.codempif and itcl.codfilial=new.codfilialif and itcl.codfisc=new.codfisc and itcl.coditfisc=new.coditfisc
                    into :codempcc, :codfilialcc, :codcalc ;
        
                    select vlrcusto from lfcalccustosp01( :codempcc, :codfilialcc, :codcalc, new.qtditcompra, new.vlrproditcompra*new.qtditcompra
                     , new.vlricmsitcompra, new.vlripiitcompra, 0, 0, new.vlrissitcompra, new.vlrfunruralitcompra
                     , new.vlriiitcompra, 0, 0, 0, 0, 0 )
                    into new.custoitcompra;
                end
            end
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGAI
AS
begin

  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
  begin

    -- Atualizando totais da importação

    update cpimportacao set

    pesobruto           =   pesobruto           +   new.pesobruto, 
    pesoliquido         =   pesoliquido         +   new.pesoliquido,
    vmlemi              =   vmlemi              +   new.vmlemi,
    vmldmi              =   vmldmi              +   new.vmldmi,
    vmle                =   vmle                +   new.vmle,
    vmld                =   vmld                +   new.vmld,
    vlradmi             =   vlradmi             +   new.vlradmi,
    vlrad               =   vlrad               +   new.vlrad,
    vlrbaseicms         =   vlrbaseicms         +   new.vlrbaseicms,

    -- Tributos

    vlrii               =   vlrii               +   new.vlrii,
    vlripi              =   vlripi              +   new.vlripi,
    vlrpis              =   vlrpis              +   new.vlrpis,
    vlrcofins           =   vlrcofins           +   new.vlrcofins,
    vlrtxsiscomex       =   vlrtxsiscomex       +   new.vlrtxsiscomex,
    vlricms             =   vlricms             +   new.vlricms,
    vlricmsdiferido     =   vlricmsdiferido     +   new.vlricmsdiferido,
    vlricmsdevido       =   vlricmsdevido       +   new.vlricmsdevido,
    vlricmscredpresum   =   vlricmscredpresum   +   new.vlricmscredpresum,
    vlricmsrecolhimento =   vlricmsrecolhimento +   new.vlricmsrecolhimento

    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp;
    
  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGAU
AS
begin

    -- Atualizando totais da importação
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    update cpimportacao set

    pesobruto           = pesobruto         - old.pesobruto                 + new.pesobruto,
    pesoliquido         = pesoliquido       - old.pesoliquido               + new.pesoliquido,
    vmlemi              = vmlemi            - old.vmlemi                    + new.vmlemi,
    vmldmi              = vmldmi            - old.vmldmi                    + new.vmldmi,
    vmle                = vmle              - old.vmle                      + new.vmle,
    vmld                = vmld              - old.vmld                      + new.vmld,
    vlrad               = vlrad             - old.vlrad                     + new.vlrad,
    vlradmi             = vlradmi           - old.vlradmi                   + new.vlradmi,
    vlrbaseicms         = vlrbaseicms       - old.vlrbaseicms               + new.vlrbaseicms,

    -- Tributos

    vlrii               =   vlrii               -   old.vlrii               +   new.vlrii,
    vlripi              =   vlripi              -   old.vlripi              +   new.vlripi,
    vlrpis              =   vlrpis              -   old.vlrpis              +   new.vlrpis,
    vlrcofins           =   vlrcofins           -   old.vlrcofins           +   new.vlrcofins,
    vlrtxsiscomex       =   vlrtxsiscomex       -   old.vlrtxsiscomex       +   new.vlrtxsiscomex,
    vlricms             =   vlricms             -   old.vlricms             +   new.vlricms,
    vlricmsdiferido     =   vlricmsdiferido     -   old.vlricmsdiferido     +   new.vlricmsdiferido,
    vlricmsdevido       =   vlricmsdevido       -   old.vlricmsdevido       +   new.vlricmsdevido,
    vlricmscredpresum   =   vlricmscredpresum   -   old.vlricmscredpresum   +   new.vlricmscredpresum,
    vlricmsrecolhimento =   vlricmsrecolhimento -   old.vlricmsrecolhimento +   new.vlricmsrecolhimento

    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp;
    
 end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGBI
AS
    declare variable cotacao numeric(15,5);
begin
    
  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
  begin
    
    -- Buscando cotação da moeda de importação
    select imp.cotacaomoeda from cpimportacao imp where imp.codemp=new.codemp and imp.codfilial=new.codfilial and imp.codimp=new.codimp
    into :cotacao;

    -- Calculando VMLE , VMLD e VLRAD

    new.vmlemi      =   new.qtd         *       new.precomi;
    new.vmldmi      =   new.vmlemi      +       new.vlrfretemi;
    new.vlradmi     =   new.vmldmi      +       new.vlrthcmi;


    -- Calculando valores em moeda corrente
    new.preco       =   new.precomi     *       :cotacao;
    new.vmle        =   new.vmlemi      *       :cotacao;
    new.vmld        =   new.vmldmi      *       :cotacao;
    new.vlrfrete    =   new.vlrfretemi  *       :cotacao;
    new.vlrseguro   =   new.vlrseguromi *       :cotacao;
    new.vlrthc      =   new.vlrthcmi    *       :cotacao;
    new.vlrad       =   new.vlradmi     *       :cotacao;

    -- Calculando II

    new.vlrii       =   new.vlrad       *       new.aliqii  /   100.00;

    -- Gerando o número do sequencial de adição.
    select coalesce(count(*),0) + 1
    from cpitimportacao
    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp and codncm=new.codncm
    into new.seqadic;
    
  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGBU
as
    declare variable cotacao        decimal(15,5);

begin

  if (new.emmanut is null) then
     new.emmanut='N';
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    -- Atualizando log
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

    -- Buscando informações do cabeçalho
    select imp.cotacaomoeda
    from cpimportacao imp where imp.codemp=new.codemp and imp.codfilial=new.codfilial and imp.codimp=new.codimp
    into :cotacao;

    -- Calculando VMLE e VMLD

    new.vmlemi      =  ( new.qtd         *       new.precomi ) + new.vlrthcmi ;
    new.vmldmi      =   new.vmlemi       +       new.vlrfretemi;
    new.vlradmi     =   new.vmldmi       ;
    new.vlrvmcv     =  ( new.qtd         *       new.precomi ) + new.vlrfretemi ;

    -- Calculando valores em moeda corrente
    new.preco       =   new.precomi     *       :cotacao;
    new.vmle        =   new.vmlemi      *       :cotacao;
    new.vmld        =   new.vmldmi      *       :cotacao;
    new.vlrfrete    =   new.vlrfretemi  *       :cotacao;
    new.vlrseguro   =   new.vlrseguromi *       :cotacao;
    new.vlrthc      =   new.vlrthcmi    *       :cotacao;
    new.vlrad       =   new.vlradmi     *       :cotacao;

    -- Calculando II

    new.vlrii       =   new.vlrad       *       new.aliqii  /   100.00;

    -- Calculando IPI

    new.vlripi      = ( new.vlrad       +       new.vlrii ) *  ( new.aliqipi /   100.00);

    -- Calculando o ICMS

    new.vlrbaseicms         =  cast ( ( new.vlrad      +       new.vlrcofins      +   new.vlrpis   +     new.vlripi   + new.vlrii + new.vlrtxsiscomex + new.vlritdespad ) / (  cast(1.00 as decimal(15,5)) - (new.aliqicmsuf / 100.00) ) as decimal(15,5));

    new.vlricms             =   cast( new.vlrbaseicms *   cast( ( new.aliqicmsuf     / cast(100.00 as decimal(15,5)) ) as decimal(15,5)) as decimal(15,5));

    if(new.percdifericms > 0) then
        new.vlricmsdiferido     =   new.vlricms     *      ( new.percdifericms  / cast(100.00 as decimal(15,5)) );
    else
        new.vlricmsdiferido     =   0.00;

    new.vlricmsdevido       =   new.vlricms     -       new.vlricmsdiferido;

    new.vlricmscredpresum   =   cast( new.vlrbaseicms *   cast( ( new.aliqicmsuf  / cast(100.00 as numeric(15,5) ) ) as decimal(15,5) ) as decimal(15,5)) ;
    
    new.vlricmscredpresum   =   new.vlricmscredpresum - new.vlricmsdiferido;

    if (new.perccredpresimp<100) then
    begin
       new.vlricmscredpresum = new.vlricmscredpresum * ( new.perccredpresimp / cast(100.00 as numeric(15,5) ) ) ; 
    end

    new.vlricmsrecolhimento =   new.vlricmsdevido   -   new.vlricmscredpresum;

    -- Gerando o número do sequencial de adição.
    if(old.codncm <> new.codncm) then
    begin
        select coalesce(count(*),0) + 1
        from cpitimportacao
        where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp and codncm=new.codncm
        into new.seqadic;
    end
    
  end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPORDCOMPRATGBU
AS
begin
    
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);
    
    -- Na aprovação total mudar status para aguardando recebimento
    if(old.statusapoc='PE' and new.statusapoc='AT') then
    begin
        new.statusoc='AR';
    
        -- Gerando contas a pagar de empenho
        execute procedure fnadicpagarsp02(
            new.codemp, new.codfilial, new.codordcp,
            new.codemppg, new.codfilialpg, new.codplanopag,
            new.codempfr, new.codfilialfr, new.codfor, 
            substring(new.obsordcp from 1 for 250) );

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQEXPEDICAOTGAI
as
declare variable codempte integer;
declare variable codfilialte smallint;
declare variable codtipoexped integer;
declare variable codprocexped integer;
declare variable coditexped integer;

begin

    -- Se tiver um produto padrão no cabeçalho, deve gerar os ítens automaticamente.
    if(new.codprod is not null) then
    begin

        coditexped = 1;
    
        for select pr.codemp, pr.codfilial, pr.codtipoexped, pr.codprocexped
        from eqprocexped pr
        where pr.codemp=new.codempte and pr.codfilial=new.codfilialte and pr.codtipoexped=new.codtipoexped
    order by pr.codprocexped
        into :codempte,  :codfilialte, :codtipoexped, :codprocexped do
        begin

            select coalesce( max(coditexped) , 0 ) + 1
            from eqitexpedicao ie
            where ie.codemp=new.codemp and ie.codfilial=new.codfilial and ie.ticket=new.ticket
            into coditexped;
        
            insert into eqitexpedicao
            ( codemp, codfilial, ticket, coditexped, codemppd, codfilialpd, codprod, refprod, codempte, codfilialte, codtipoexped, codprocexped ) values
            ( new.codemp, new.codfilial, new.ticket, :coditexped, new.codemppd, new.codfilial, new.codprod, new.refprod, :codempte, :codfilialte, :codtipoexped, :codprocexped );


        end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGAD
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
     old.CODEMPAX, old.CODFILIALAX, old.CODALMOX, null, 'S');
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGAI
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
     new.CODEMPAX, new.CODFILIALAX, new.CODALMOX, null, 'S');
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGAU
AS
BEGIN
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
  begin
      EXECUTE PROCEDURE EQMOVPRODIUDSP('U', new.CODEMPPD, new.CODFILIALPD,
         new.CODPROD, new.CODEMPLE, new.CODFILIALLE, new.CODLOTE,
         new.CODEMPTM, new.CODFILIALTM, new.CODTIPOMOV, new.CODEMP,
         new.CODFILIAL, new.CODINVPROD,  null, null, null, null,
         null, null, null, null, null, null, null, null, null,null,null,null,
         null, null, null, null, null, new.DATAINVP, new.CODINVPROD, 'S',
         new.QTDINVP, new.PRECOINVP,
         new.CODEMPAX, new.CODFILIALAX, new.CODALMOX, null, 'S');
  end
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGBU
AS
  DECLARE VARIABLE CLOTEPROD CHAR;
BEGIN
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
  if ( new.emmanut is null) then
      new.emmanut='N';

  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
  begin

      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      SELECT CLOTEPROD FROM EQPRODUTO WHERE CODPROD=new.CODPROD
        AND CODEMP=new.CODEMPPD AND CODFILIAL=new.CODFILIALPD INTO CLOTEPROD;
      IF (new.CODPROD != old.CODPROD) THEN
        EXCEPTION EQINVPRODEX01;
      IF (new.CODLOTE != old.CODLOTE) THEN
        EXCEPTION EQINVPRODEX02;
      IF (new.DATAINVP != old.DATAINVP) THEN
        EXCEPTION EQINVPRODEX03;
      IF (new.CODALMOX != old.CODALMOX) THEN
        EXCEPTION EQINVPRODEX04;
      IF (CLOTEPROD = 'S' AND new.CODLOTE IS NULL) THEN
        EXCEPTION EQINVPRODEX05;
  end
  
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMASTRMA
AS
  declare variable numitens int;
  declare variable numitensaf int;
  declare variable numitensap int;
  declare variable numitensna int;
  declare variable numitensat int;
  declare variable numitensef int;
  declare variable numitensca int;
  declare variable numitensep int;
  declare variable numitensne int;
  declare variable numitenset int;

begin

  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
  begin

      if (old.sititrma!='AF') then
      begin
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma into :numitens;
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sititrma='AF' into :numitensaf;
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sititrma='EF' into :numitensef;
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sititrma='CA' into :numitensca;
          if(:numitens=:numitensaf) then
          begin
            update eqrma set sitrma='AF' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitrma!='AF';
          end
          else if (:numitens=:numitensef) then
          begin
            update eqrma set sitrma='EF' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitrma!='EF';
          end
          else if (:numitens=:numitensca) then
          begin
            update eqrma set sitrma='CA' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitrma!='CA';
          end
          if (new.qtdaprovitrma!=old.qtdaprovitrma) then
          begin
              if (new.qtdaprovitrma>0) then
              begin
                  update eqrma set sitrma='EA' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitrma='PE';
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitaprovitrma='AP' into :numitensap;
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitaprovitrma='NA' into :numitensna;
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitaprovitrma='AT' into :numitensat;
                  if(:numitens!=:numitensaf) then
                  begin
                    if(:numitens=:numitensat) then
                    begin
                       update eqrma set sitaprovrma='AT' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitaprovrma!='AT';
                    end
                    else if((:numitensap>0) or (:numitensna>0)) then
                    begin
                       update eqrma set sitaprovrma='AP' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitaprovrma!='AP';
                    end
                    if(:numitens=:numitensna) then
                    begin
                       update eqrma set sitaprovrma='NA' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma  and sitaprovrma!='NA';
                    end
                  end
              end
          end
      end
      else if (old.sititrma!='EF') then
      begin
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma into :numitens;
          select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sititrma='EF' into :numitensef;
          if (:numitens=:numitensef) then
          begin
            update eqrma set sitrma='EF' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitrma!='EF';
          end
          if (new.qtdexpitrma!=old.qtdexpitrma) then
          begin
              if (new.qtdexpitrma>0) then
              begin
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitexpitrma='EP' into :numitensep;
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitexpitrma='NE' into :numitensne;
                  select count(1) from eqitrma where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitexpitrma='ET' into :numitenset;
                  if(:numitens!=:numitensef) then
                  begin
                    if(:numitens=:numitenset) then
                    begin
                       update eqrma set sitexprma='ET' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitexprma!='ET';
                    end
                    else if((:numitensep>0) or (:numitensne>0)) then
                    begin
                       update eqrma set sitexprma='EP' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma and sitexprma!='EP';
                    end
                    if(:numitens=:numitensne) then
                    begin
                       update eqrma set sitaprovrma='NE' where codemp=new.codemp and codfilial=new.codfilial and codrma = new.codrma  and sitexprma!='NE';
                    end
                  end
              end
          end
      end
  end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAD
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
        OLD.CODEMPAX, OLD.CODFILIALAX, OLD.CODALMOX, null, 'S');
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAI
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
            new.CODFILIALAX, new.CODALMOX, null, 'S');
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAIAU
AS
declare variable icodemptm int;
declare variable icodfilialtm smallint;
declare variable icodtipomov integer;
begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

      if(new.sititrma='EF') then
      begin
        select tm.codemptm,tm.codfilialtm,tm.codtipomovtm from eqtipomov tm, eqrma rm
           where tm.codemp=rm.codemptm and tm.codfilial=rm.codfilialtm and tm.codtipomov=rm.codtipomov
           and rm.codemp=new.codemp and rm.codfilial=new.codfilial and codrma=new.codrma
           into :icodemptm,:icodfilialtm,:icodtipomov;
        if((:icodemptm is not null) and (:icodfilialtm is not null) and (:icodtipomov is not null)) then
        begin
           update eqrma set codemptm=:icodemptm, codfilialtm=:icodfilialtm, codtipomov=:icodtipomov where
           codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma;
        end
        
     end
  end

  -- Atualização do status do item de ordem de serviço ao expedir o item de rma
  if( new.coditos is not null and new.sitexpitrma in ('ET','EP')) then
  begin

    update eqitrecmercitos ios set ios.statusitos='CO'
    where ios.codemp=new.codempos and ios.codfilial=new.codfilialos and ios.ticket=new.ticket
    and ios.coditrecmerc = new.coditrecmerc and ios.coditos=new.coditos;

  end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAU
as
    declare variable icodemptm int;
    declare variable icodfilialtm smallint;
    declare variable icodtipomov int;
    declare variable ddtrma date;
    declare variable baixarmaaprov char(1);
    declare variable estoque char(1);
    declare variable qtdmov numeric(15,5);

begin
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
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
            new.codempax, new.codfilialax, new.codalmox, null, 'S' );
    
   end
   
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGBD
AS
declare variable sUsuarioCN char(8);
declare variable sUsuarioRM char(8);
declare variable apagarmaop char(1);
declare variable icodfilialpref smallint;
declare variable icodop integer;
begin

       if(old.sititrma<>'PE') then
       begin
    --      exception eqitrma01;
       end
    
       select icodfilial from sgretfilial(old.codemp,'SGPREFERE5') into :icodfilialpref;
    
       select idusus from sgretinfousu(old.CODEMP, USER)
          into :sUsuarioCN;
    
       select rm.idusu,rm.codop from eqrma rm
          where rm.codemp=old.codemp and rm.codfilial=old.codfilial and rm.codrma=old.codrma
          into :sUsuarioRM, :icodop;
    
       select p.apagarmaop from sgprefere5 p
          where p.codemp=old.codemp and p.codfilial=:icodfilialpref
          into :apagarmaop;
    
       if(:icodop is null) then
       begin
           if(:sUsuarioCN<>:sUsuarioRM)then
           begin
    --          exception eqitrma02;
           end
       end
       else
       begin
          if(:apagarmaop='N' and :sUsuarioCN<>:sUsuarioRM) then
           begin
     --         exception eqitrma02;
           end
       end
       
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGBU
AS
    declare variable statusitem char(2);

begin


    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

       -- Atualizando log.
       new.dtalt = cast('now' AS DATE);
       new.idusualt = USER;
       new.halt = cast('now' AS TIME);

       -- Acertando almoxarifado (quando não informado)
       if (new.codalmox is null) then
           select CODEMPAX,CODFILIALAX,CODALMOX from eqproduto where
               codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
           into new.codempax, new.codfilialax,new.codalmox;

       -- Acertando os status.
       if (old.sititrma!='AF') then
       begin
           if (new.qtdaprovitrma!=old.qtdaprovitrma or (NEW.sitaprovitrma='AT') ) then
           begin
               if ((new.sitaprovitrma='AT') and (new.qtdaprovitrma=0) and (old.sitaprovitrma!='AT')) then
               begin
                   new.qtdaprovitrma=new.qtditrma;
               end
               if (new.qtdaprovitrma>0) then
               begin
                   if (new.qtdaprovitrma<new.qtditrma) then
                   begin
                       statusitem = 'AP'; -- Aprovação parcial
                   end
                   else
                   begin
                       statusitem = 'AT'; -- Aprovação total
                   end

                   -- atualizando status e data da aprovação
                   new.sitaprovitrma=:statusitem;
                   new.dtaprovitrma=cast('today' as date);

                   if(new.sititrma='PE') then
                   begin
                       new.sititrma='EA';
                   end
               end
           end
       end
       else if (old.sititrma!='EF') then
       begin
           if (new.qtdexpitrma!=old.qtdexpitrma or (NEW.sitexpitrma='ET') ) then
           begin
               if ((new.sitexpitrma='ET') and (new.qtdexpitrma=0) and (old.sitexpitrma!='ET')) then
               begin
                   new.qtdexpitrma=new.qtdaprovitrma;
               end
               if (new.qtdexpitrma>0) then
               begin
                   if (new.qtdexpitrma<new.qtdaprovitrma) then
                   begin
                       statusitem = 'EP'; -- Expedição parcial
                   end
                   else
                   begin
                       statusitem = 'ET'; -- Expedição total
                   end

                   -- Atualizando status e data da expedição
                   new.sitexpitrma=:statusitem;
                   new.dtaexpitrma=cast('today' as date);

                   -- Atualizando custo do produto no momento da expedição.
                   select ncustompmax from eqprodutosp01(new.codemppd,new.codfilialpd,new.codprod,new.codempax,new.codfilialax,new.codalmox)
                   into new.precoitrma;

               end
           end
       end
       if(new.sititrma='CA') then
       begin
           new.qtdaprovitrma=0;
           new.qtdexpitrma=0;
           new.sitaprovitrma='NA';
           new.sitexpitrma='NA';
       end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQMOVPRODTGAD
AS
  DECLARE VARIABLE SOPERADOR SMALLINT;
BEGIN
  
  IF ( not ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
  BEGIN
      IF (old.ESTOQMOVPROD = 'S') THEN
      BEGIN
          IF (old.TIPOMOVPROD = 'S') THEN
            SOPERADOR = -1;
          ELSE
            SOPERADOR = 1;
          EXECUTE PROCEDURE EQMOVPRODATEQSP(old.CODEMPPD, old.CODFILIALPD, old.CODPROD,
            old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
            old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
            :SOPERADOR, old.QTDMOVPROD, 0,
            old.CODEMPAX, old.CODFILIALAX, old.CODALMOX,
            old.CODEMPAX, old.CODFILIALAX, old.CODALMOX);
       END
   END
 /* EXECUTE PROCEDURE EQMOVPRODATCUSTSP(old.CODEMPPD, old.CODFILIALPD, old.CODPROD, SOPERADOR); */
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGAU
AS
  DECLARE VARIABLE ICODCLI INTEGER;
  DECLARE VARIABLE ICODEMPCL INTEGER;
  DECLARE VARIABLE ICODFILIALCL INTEGER;
  DECLARE VARIABLE SCODFILIALCI SMALLINT;
  DECLARE VARIABLE CODFILIALLC SMALLINT;
  DECLARE VARIABLE CODEMPLC INTEGER;
  DECLARE VARIABLE CODLANCA INTEGER;
  DECLARE VARIABLE VLRLANCA NUMERIC(15,5);
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ESTITRECALTDTVENC FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;
     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='R1') ) OR
           ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='RR') ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNCOMISSAO') INTO :SCODFILIALCI;
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :CODFILIALLC;
       UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
              
       IF ( (old.MULTIBAIXA IS NULL) OR (old.MULTIBAIXA='N') ) THEN
       BEGIN        
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
          DELETE FROM FNLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
       END
       ELSE 
       BEGIN
          SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
                CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA;
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
          SELECT VLRLANCA FROM FNLANCA
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA
             INTO :VLRLANCA;
          IF (:VLRLANCA=0) THEN 
          BEGIN
             DELETE FROM FNLANCA 
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA;
          END 
       END
     END
     ELSE IF ((old.STATUSITREC='R1' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC='RR' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC in ('RP','RL') AND new.STATUSITREC in ('RP','RL') AND new.VLRPAGOITREC > 0) OR
              (old.STATUSITREC = 'RB' AND new.STATUSITREC = 'RP')) THEN
     BEGIN
        SELECT CODCLI,CODEMPCL,CODFILIALCL FROM FNRECEBER WHERE CODEMP=new.CODEMP AND
           CODFILIAL=new.CODFILIAL AND CODREC=new.CODREC
           INTO ICODCLI,ICODEMPCL,ICODFILIALCL;
        IF ((new.VLRPAGOITREC-old.VLRPAGOITREC) > 0) THEN
        BEGIN
       IF(new.multibaixa is null or new.multibaixa = 'N')THEN
           BEGIN
        EXECUTE PROCEDURE FNADICLANCASP01(new.CodRec,new.NParcItRec,new.PDVITREC,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODCLI,:ICODEMPCL,:ICODFILIALCL,
                        new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.ANOCC,new.CODCC,new.CODEMPCC,new.CODFILIALCC, new.dtCompItRec, new.DtPagoItRec, 
                        new.DocLancaItRec, SUBSTRING(new.ObsItRec FROM 1 FOR 50),new.VlrPagoItRec-old.VlrPagoItRec,new.CODEMP,new.CODFILIAL,new.vlrjurositrec,new.vlrdescitrec);
           END
        END
        IF (new.STATUSITREC = 'RP') THEN
        BEGIN
            UPDATE VDCOMISSAO SET STATUSCOMI='C2'
               WHERE CODREC=old.CODREC
               AND CODEMPRC=old.CODEMP
               AND CODFILIALRC=old.CODFILIAL
               AND NPARCITREC=old.NPARCITREC
               AND NOT STATUSCOMI IN ('CP','C2')
               AND CODEMP=old.CODEMP;
        END
     END
     ELSE IF (old.VLRCOMIITREC != new.VLRCOMIITREC) THEN
     BEGIN
        EXECUTE PROCEDURE vdgeracomissaosp(new.CODEMP, new.CODFILIAL, new.CODREC,
           new.NPARCITREC, new.VLRCOMIITREC, new.DTVENCITREC);
     END

     IF ( (new.ALTUSUITREC='S') AND ( (old.VLRITREC!=new.VLRITREC) OR
          (old.VLRDESCITREC!=new.VLRDESCITREC) OR (old.VLRMULTAITREC!=new.VLRMULTAITREC) OR
          (old.VLRJUROSITREC!=new.VLRJUROSITREC) OR (old.VLRPARCITREC!=new.VLRPARCITREC) OR
          (old.VLRPAGOITREC!=new.VLRPAGOITREC) OR (old.VLRAPAGITREC!=new.VLRAPAGITREC) ) ) THEN
        UPDATE FNRECEBER SET VLRREC = VLRREC - old.VLRITREC + new.VLRITREC,
            VLRDESCREC = VLRDESCREC - old.VLRDESCITREC + new.VLRDESCITREC,
            VLRMULTAREC = VLRMULTAREC - old.VLRMULTAITREC + new.VLRMULTAITREC,
            VLRJUROSREC = VLRJUROSREC - old.VLRJUROSITREC + new.VLRJUROSITREC,
            VLRDEVREC = VLRDEVREC - old.VLRDEVITREC + new.VLRDEVITREC,
            VLRPARCREC = VLRPARCREC - old.VLRPARCITREC + new.VLRPARCITREC,
            VLRPAGOREC = VLRPAGOREC - old.VLRPAGOITREC + new.VLRPAGOITREC,
            VLRAPAGREC = VLRAPAGREC - old.VLRAPAGITREC + new.VLRAPAGITREC,
            ALTUSUREC = 'S' WHERE CODREC=new.CODREC
           AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
      /* Condição para evitar baixa parcial de títulos com juros, descontos ou multas. */
      IF ( (new.STATUSITREC='RL') AND ( (new.VLRDESCITREC<>0) OR (new.VLRJUROSITREC<>0) OR (new.VLRMULTAITREC<>0) ) ) THEN
         EXCEPTION FNITRECEBEREX03; 
   END
   
END
^

/* Alter exist trigger... */
ALTER TRIGGER PPITOPTGAU
as
declare variable codemprma integer;
declare variable codfilialrma smallint;
declare variable codrma integer;
declare variable coditrma smallint;
declare variable sititrma char(2);
declare variable qtditrma numeric(15,5);
declare variable qtdaprovitrma numeric(15,5);
declare variable qtdexpitrma numeric(15,5);

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        if(new.qtdcopiaitop is not null) then
        begin
            if( (new.qtdcopiaitop>0) and (old.qtdcopiaitop is null) ) then
            begin
                insert into ppitop (codemp,codfilial,codop,seqop,seqitop,
                                    codemppd, codfilialpd,codprod,refprod,
                                    qtditop, codempfs,codfilialfs, codfase,
                                    codemple,codfilialle,codlote,gerarma,SEQITOPCP)
                                    values
                                    (new.codemp,new.codfilial,new.codop,new.seqop,
                                    (select (max(op.seqitop)+1) from ppitop op
                                    where op.codemp=new.codemp and op.codfilial=new.codfilial
                                    and op.codop=new.codop and op.seqop=new.seqop),
                                    new.codemppd,new.codfilialpd,new.codprod,new.refprod,
                                    new.qtdcopiaitop,new.codempfs,new.codfilialfs,new.codfase,
                                    new.codemple, new.codfilialle,new.codloterat,new.gerarma,new.seqitop);
            end
        end
        -- Atualizando as requisições de material, caso as quantidades na OP sejam alteradas
        if(old.qtditop <> new.qtditop) then
        begin
            -- Buscando RMA Gerada para o produto de entrada
            select first 1 ir.codemp, ir.codfilial, ir.codrma, ir.coditrma, ir.sititrma,
            ir.qtditrma, ir.qtdaprovitrma, ir.qtdexpitrma
            from eqrma rm, eqitrma ir
            where rm.codemp=ir.codemp and rm.codfilial=ir.codfilial and rm.codrma=ir.codrma
            and ir.codemppd=new.codemppd and ir.codfilialpd=new.codfilialpd and ir.codprod=new.codprod
            and rm.codop=new.codop and rm.seqop=new.seqop
            into codemprma, codfilialrma, codrma, coditrma, sititrma, qtditrma, qtdaprovitrma, qtdexpitrma ;
    
            -- Atualizando item de rma
            if(sititrma='PE') then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
            else if (sititrma = 'AF' and :qtditrma=:qtdaprovitrma) then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop,ir.qtdaprovitrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
            else if(sititrma = 'EF' and :qtditrma=:qtdexpitrma) then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop,ir.qtdaprovitrma=new.qtditop,ir.qtdexpitrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPITOPTGBU
as
begin
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      SELECT REFPROD FROM EQPRODUTO WHERE CODEMP=new.CODEMPPD AND
        CODFILIAL=new.CODFILIALPD AND CODPROD=new.CODPROD INTO new.REFPROD;
    end
    
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPENTRADATGAD
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

    execute procedure eqmovprodiudsp('U', :codemppd, :codfilialpd, :codprod,
        :codemple, :codfilialle, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, old.seqent,
        null, null, null,
        old.dtent, old.codop, 'N', 0,:preco,
        :codempax, :codfilialax, :codalmox, null, 'S' );

   if (:codopm is not null) then
   begin
      execute procedure ppatudistopsp( :codempopm, :codfilialopm, :codopm, :seqopm, :qtddistiop, 0);
   end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPENTRADATGAI
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

        -- Buscando custo do produto acabado
        select sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) * it.qtditop ) / op.qtdprevprodop
        from ppitop it, eqproduto pd, ppop op
        where it.codemp=new.codemp and it.codfilial=it.codfilial
        and it.codop=new.codop and it.seqop=new.seqop
        and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
        and pd.codprod=it.codprod
        and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop and op.seqop=new.seqop
        group by op.qtdprevprodop
        into :preco;

        execute procedure EQMOVPRODIUDSP('I', :CODEMPPD, :CODFILIALPD, :CODPROD,
        :CODEMPLE, :CODFILIALLE, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, new.seqent,
        null, null,  null, new.dtent, new.codop,
        'S',new.qtdent,cast(:preco as numeric(15,5)),
        :codempax, :codfilialax, :codalmox, null, 'S' );

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

/* Alter exist trigger... */
ALTER TRIGGER PPOPFASETGAI
AS
declare variable ctipofase char(2);
declare variable imesesdesccp smallint;
declare variable ddtdescarte date;
declare variable ddtvalidop date;
declare variable icodempest integer;
declare variable icodfilialest smallint;
declare variable icodprodest integer;
declare variable iseqest smallint;
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable icodprodpd integer;
declare variable icoditretcp smallint;
declare variable iqtd decimal(15,5);
declare variable numretcp integer;

begin
  select tipofase from ppfase where codemp=new.codempfs and codfilial=new.codfilialfs and codfase=new.codfase
      into :ctipofase;

  if(ctipofase='CQ') then
  begin

    select max(sg.mesesdesccp) from sgprefere5 sg where codemp=new.codemp and codfilial=new.codfilial
      into :imesesdesccp;

    select op.codemppd, op.codfilialpd, op.codprod, op.seqest, op.dtvalidpdop
        from ppop op
            where op.codemp = new.codemp and op.codfilial=new.codfilial and op.codop=new.codop
                and op.seqop=new.seqop
        into :icodempest,:icodfilialest,:icodprodest,:iseqest,:ddtvalidop;

    ddtdescarte = cast(addmonth(:ddtvalidop,:imesesdesccp) as date);

   -- Buscando o numero de contra provas (Deve haver apenas 1)
   select count(*) from ppretcp rc where rc.codemp=new.codemp and rc.codfilial=new.codfilial and rc.codop=new.codop and rc.seqop=new.seqop
   into :numretcp;
   
   if(:numretcp is null or :numretcp <1) then
   begin
       insert into ppretcp (codemp,codfilial,codop,seqop,dtdescarte)
       values(new.codemp,new.codfilial, new.codop,new.seqop,:ddtdescarte);


       for select it.codemppd,it.codfilialpd,it.codprodpd,it.seqitest,it.qtditest from ppitestrutura it
           where it.codemp = :icodempest and it.codfilial=:icodfilialest and it.codprod=:icodprodest
               and it.seqest = :iseqest and it.codempfs=new.codempfs and it.codfilialfs=new.codfilialfs
               and it.codfase = new.codfase
       into :icodemppd,:icodfilialpd,:icodprodpd,:icoditretcp,:iqtd do
       begin
           insert into ppitretcp (codemp,codfilial,codop,seqop,coditretcp,codemppd,codfilialpd,codprod,qtditret)
           values(new.codemp,new.codfilial,new.codop,new.seqop, :icoditretcp,:icodemppd,:icodfilialpd,:icodprodpd,:iqtd);
       end

    end
  end

  end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPSUBPRODTGAD
AS
declare variable preco decimal(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable dtestoque date;
begin

    --Buscando almoxarifado do produto
    select codempax, codfilialax, codalmox from eqproduto where codemp=old.codemppd and codfilial=old.codfilialpd and codprod=old.codprod
    into :codempax, :codfilialax, :codalmox;

    -- Buscando custo do produto;
    select ncustompm from eqprodutosp01(old.codemppd,old.codfilialpd,old.codprod,null,null,null)
    into :preco;

    -- Buscando data de finalização da fase
    select coalesce(fs.datafimprodfs,op.dtemitop) from ppopfase fs, ppop op
    where
    op.codemp=fs.codemp and op.codfilial=fs.codfilial and op.codop=fs.codop and op.seqop=fs.seqop and
    fs.codemp=old.codemp and fs.codfilial=old.codfilial and fs.codop=old.codop and fs.seqop=old.seqop and fs.codempfs=old.codempfs
    and fs.codfilialfs=old.codfilialfs and fs.codfase=old.codfase and fs.seqof=old.seqof
    into :dtestoque;

 
   EXECUTE PROCEDURE EQMOVPRODIUDSP('D',old.CODEMPPD, old.CODFILIALPD, old.CODPROD,
        old.CODEMPLE, old.CODFILIALLE, old.codlote, old.codemptm,
        old.codfilialtm, old.codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, null,
        null, null, null,
        :dtestoque, old.codop, 'N', old.qtditsp,:preco,
        :codempax, :codfilialax, :codalmox, old.seqsubprod, 'S' );
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPSUBPRODTGAI
AS
declare variable dtestoque date;
declare variable preco decimal(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
begin

    --Buscando almoxarifado do produto
    select codempax, codfilialax, codalmox from eqproduto where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
    into :codempax, :codfilialax, :codalmox;

    -- Buscando custo do produto;
    select ncustompm from eqprodutosp01(new.codemppd,new.codfilialpd,new.codprod,null,null,null)
    into :preco;

    -- Buscando data de finalização da fase
    select coalesce(fs.datafimprodfs,op.dtemitop) from ppopfase fs, ppop op
    where
    op.codemp=fs.codemp and op.codfilial=fs.codfilial and op.codop=fs.codop and op.seqop=fs.seqop and
    fs.codemp=new.codemp and fs.codfilial=new .codfilial and fs.codop=new.codop and fs.seqop=new.seqop and fs.codempfs=new.codempfs
    and fs.codfilialfs=new.codfilialfs and fs.codfase=new.codfase and fs.seqof=new.seqof
    into :dtestoque;

    EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
    null, null, null, null, null, null,
    null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, null,
    null, null,  null, :dtestoque, new.codop,
    'N',0.00,cast(:preco as numeric(15,5)),
    :codempax, :codfilialax, :codalmox, new.seqsubprod, 'S' );

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPSUBPRODTGAU
AS
declare variable preco decimal(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;

begin

    if ( new.qtditsp>0 and (old.dtsubprod is null and new.dtsubprod is not null) ) then
        begin

            --Buscando almoxarifado do produto
            select codempax, codfilialax, codalmox from eqproduto where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
            into :codempax, :codfilialax, :codalmox;

            -- Buscando custo do produto;
            select ncustompm from eqprodutosp01(new.codemppd,new.codfilialpd,new.codprod,null,null,null)
            into :preco;

            execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                null, null, null, null, null, null,null, null, null, null, null,
                new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                new.dtsubprod , new.codop, 'N', new.qtditsp,:preco,
                :codempax, :codfilialax, :codalmox, new.seqsubprod, 'S' );
        end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAD
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
        old.codempax, old.codfilialax, old.codalmox, null, 'S' );

   if (old.CODOPM is not null) then
      EXECUTE PROCEDURE PPATUDISTOPSP(old.CODEMPOPM, old.CODFILIALOPM, old.CODOPM,
        old.SEQOPM, old.QTDDISTIOP, 0);

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAI
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
   if( :prodetapas = 'N' or :prodetapas is null ) then
   begin

       EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
            new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
            new.codfilialtm, new.codtipomov, null, null, null ,null, null,
            null, null, null, null, null, null,
            null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, null,
            null, null,  null, new.dtfabrop, new.codop,
            'N',new.qtdfinalprodop,cast(:preco as numeric(15,5)),
            new.codempax, new.codfilialax, new.codalmox, null, 'S' );

       if (new.CODOPM is not null) then
          EXECUTE PROCEDURE PPATUDISTOPSP(new.CODEMPOPM, new.CODFILIALOPM, new.CODOPM,
            new.SEQOPM, 0, new.QTDDISTIOP);

   end

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAU
as
declare variable preco decimal(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
declare variable codorc integer;
declare variable coditorc smallint;
declare variable prodetapas char(1);
declare variable qtdprodorc decimal(15,5);

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
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
                    new.codempax, new.codfilialax, new.codalmox, null, 'S' );
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
    
            -- Excluindo subproducao
            delete from ppopsubprod where codemp=new.codemp and codfilial=new.codfilial and codop=new.codop and seqop = new.seqop;
    
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
               if ( (new.qtdfinalprodop is null) or (new.qtdfinalprodop=0) ) then
               begin
                  preco = 0;
               end
               else
               begin
                   select cast(cast(sum( cast((select cast(ncustompm as decimal(15,5)) 
                   from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) as decimal(15,5)) * it.qtditop ) 
                   as decimal(15,5)) / new.qtdfinalprodop as decimal(15,5))
                       from ppitop it, eqproduto pd
                       where it.codemp=new.codemp and it.codfilial=it.codfilial
                       and it.codop=new.codop and it.seqop=new.seqop
                       and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
                       and pd.codprod=it.codprod
                   into :preco;
               end
    
               execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                    null, null, null, null, null, null,null, null, null, null, null,
                    new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                    new.dtfabrop, new.codop, 'N', new.qtdfinalprodop,:preco,
                    new.codempax, new.codfilialax, new.codalmox, null, 'S' );
    
                -- Buscando quantidade de produto acabado destinado a orçamentos;
                select cast(sum(oo.qtdprod) as decimal(15,5)) from ppopitorc oo
                where oo.codemp=new.codemp and oo.codfilial=new.codfilial and oo.codop=new.codop and oo.seqop=new.seqop
                into :qtdprodorc;
    
                -- Atualizando a quantidade final produzida por item de orçamento;
    
                if(:qtdprodorc is not null and :qtdprodorc > 0 ) then
                begin
    
                    update ppopitorc oo set oo.qtdfinalproditorc = cast( ( cast(cast(oo.qtdprod as decimal(15,5)) /  cast(:qtdprodorc as decimal(15,5) ) as decimal(15,5)) * (cast(new.qtdfinalprodop as decimal(15,5)))) as decimal(15,5) )
                    where oo.codemp=new.codemp and oo.codfilial=new.codfilial and oo.codop=new.codop and oo.seqop=new.seqop;
    
                end
    
            end
            
            else
            begin    --se for em etapas executar a atualização dos itens de RMA
                   execute procedure ppitopsp02(new.codemp, new.codfilial, new.codop, new.seqop);
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
                    select cast(sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) * it.qtditop ) / new.qtdfinalprodop as decimal(15,5))
    
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
                    new.codempax, new.codfilialax, new.codalmox, null, 'S' );
    
                end
    
                execute procedure ppitopsp02(new.codemp, new.codfilial, new.codop, new.seqop);
    
            end
            if (new.CODOPM is not null) then
                execute procedure ppatudistopsp(new.codempopm, new.codfilialopm, new.codopm,
                    new.seqopm, old.qtddistiop, new.qtddistiop);
    
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGBU
as
declare variable prodetapas char(1);
begin

    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
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

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDFINCONTRTGAI
AS
begin
    update vdcontrato set sitcontr='FN' 
       where codemp=new.codemp and codfilial=new.codfilial and codcontr=new.codcontr;
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGAU
as
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
    declare variable vlricmsitorc numeric(15,5);
    declare variable vlripiitorc numeric(15,5);
    declare variable vlrpisitorc numeric(15,5);
    declare variable vlrcofinsitorc numeric(15,5);
    declare variable vlriritorc numeric(15,5);
    declare variable vlrcsocialitorc numeric(15,5);
    declare variable vlrissitorc numeric(15,5);
    declare variable qtdstatusitem integer;
    declare variable qtdstatustot integer;
    declare variable tipoprod char(1);

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin

    if (old.vlrdescitorc<>new.vlrdescitorc
        or old.vlrproditorc<>new.vlrproditorc
        or old.vlrliqitorc<>new.vlrliqitorc ) then
    begin
      update vdorcamento set
        VLRDESCITORC = VLRDESCITORC - old.VLRDESCITORC + new.VLRDESCITORC,
        VLRPRODORC = VLRPRODORC - old.VLRPRODITORC + new.VLRPRODITORC,
        VLRLIQORC = VLRLIQORC - old.VLRLIQITORC + new.VLRLIQITORC
        where CODORC=new.CODORC and TIPOORC='O' and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL;
    end

    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    if( visualizalucr = 'S' ) then
    begin
            select tipoprod from eqproduto where codemp=new.codemppd and codfilial=new.codfilialpd
               and codprod=new.codprod
            into :tipoprod;

            if (tipoprod='P') then
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

               -- Atualizando registro na tabela de custos de item de orçamento

               update vditcustoorc ico set vlrprecoultcp=:custouc, vlrcustompm=:custompm, vlrcustopeps=:custopeps
                   where ico.codemp=new.codemp and ico.codfilial=new.codfilial and ico.codorc=new.codorc
                   and ico.tipoorc=new.tipoorc and ico.coditorc=new.coditorc
                   -- Condição inserida para evitar cascade quando não for necessário
                   and (vlrprecoultcp<>:custouc or vlrcustompm<>:custompm or vlrcustopeps<>:custopeps ) ;

            end 

            -- Buscando e inserindo previsão de tributos
            select vlricms, vlripi, vlrpis, vlrcofins, vlrir, vlrcsocial, vlriss
            from lfbuscaprevtriborc(new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc)
            into :vlricmsitorc, :vlripiitorc, :vlrpisitorc, :vlrcofinsitorc, :vlriritorc, :vlrcsocialitorc, :vlrissitorc;

            update vdprevtribitorc po set
            po.vlricmsitorc=:vlricmsitorc, po.vlripiitorc=:vlripiitorc, po.vlrpisitorc=:vlrpisitorc,
            po.vlrcofinsitorc=:vlrcofinsitorc, po.vlriritorc=:vlriritorc, po.vlrcsocialitorc=:vlrcsocialitorc,
            po.vlrissitorc=:vlrissitorc
            where po.codemp=new.codemp and po.codfilial=new.codfilial and po.codorc=new.codorc
            and po.tipoorc=new.tipoorc and po.coditorc=new.coditorc
             -- Condição inserida para evitar cascade quando não for necessário
             and ( po.vlricmsitorc<>:vlricmsitorc or po.vlripiitorc<>:vlripiitorc
              or po.vlrpisitorc<>:vlrpisitorc or po.vlrcofinsitorc<>:vlrcofinsitorc
              or po.vlriritorc<>:vlriritorc or po.vlrcsocialitorc<>:vlrcsocialitorc
              or po.vlrissitorc<>:vlrissitorc )
            ;

       end

       -- Se o status foi alterado para Aprovado (OL), deve atualizar o status das ordens de serviço vinculadas
       -- para OA (Orçamento aprovado)
       if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' ) then
       begin
            update eqitrecmercitositorc ios set ios.status='OA'
            where ios.codempoc=new.codemp and ios.codfilialoc=new.codfilial and ios.codorc=new.codorc
            and ios.tipoorc=new.tipoorc and ios.coditorc=new.coditorc
            and ios.status<>'OA';
       end

       -- Contando a quantidade de itens do orçamento com o status do item atual
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL and statusitorc=new.statusitorc
       into :qtdstatusitem;

       -- Contando a quantidade de itens total do orçamento
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL 
       into :qtdstatustot;

       -- Se todos os itens do orçamento tem o mesmo status, deve ataulizar o status do cabeçalho do orçamento;
       if(:qtdstatusitem > 0 and :qtdstatusitem = :qtdstatustot) then
       begin
            update vdorcamento set statusorc=new.statusitorc
            where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL
            and statusorc<>new.statusitorc;
       end

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGBU
as
    declare variable adicfrete char(1);
    declare variable tipoprod varchar(2);
    declare variable encorcprod char(1);

begin
    if ( new.emmanut is null) then
        new.emmanut='N';

    if(old.aprovitorc!=new.aprovitorc and new.aprovitorc='S') then -- Na aprovação do item de orçamento
    begin
        new.statusitorc='OL';
        new.dtaprovitorc=cast('today' as date);

        if((new.qtdaprovitorc is null) or (new.qtdaprovitorc=0)) then
        begin
            new.qtdaprovitorc=new.qtditorc;
        end
    end
    else if(new.cancitorc='S' or new.statusitorc='CA' ) then
    begin
        new.aceiteitorc = 'N';
        new.aprovitorc = 'N';
        new.qtdaprovitorc = 0;
        new.statusitorc = 'CA';
        new.cancitorc = 'S';
    end
    
    new.qtdafatitorc = new.qtditorc - new.qtdfatitorc;
    
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        new.dtalt = cast('now' as date);
        new.halt = cast('now' as time);
        new.idusualt = user;
        
        if (new.qtdfatitorc>0) then
        begin
           if (new.qtdfatitorc<new.qtditorc) then
           begin
              new.fatitorc = 'P';
           end
           else
           begin
              new.fatitorc = 'S';
           end
        end
        else 
        begin
           new.fatitorc = 'N';
        end

        select adicfrete from vdorcamento
        where codemp=new.codemp and codfilial=new.codfilial and codorc=new.codorc and tipoorc=new.tipoorc
        into adicfrete;

        if (new.vlrproditorc is null) then new.vlrproditorc = 0;
        if (new.vlrdescitorc is null) then new.vlrdescitorc = 0;
        if (new.vlrliqitorc is null) then new.vlrliqitorc = 0;

        if( adicfrete = 'S' and new.vlrfreteitorc is not null and new.vlrfreteitorc>0) then
        begin
            new.vlrliqitorc = new.vlrliqitorc + new.vlrfreteitorc;
        end

        -- Buscando nas preferencias de deve encaminhar orçamento para a produção.
        select coalesce(p1.encorcprod,'N') from sgprefere1 p1
        where p1.codemp=new.codemp and p1.codfilial=new.codfilial
        into :encorcprod;
    
    
        -- Se for produto acabado e encaminhamento pull definido nas preferencias e item for aprovado deverá sinalizar item para produção.
    
        if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' and :encorcprod='S') then
        begin
    
            select tipoprod from eqproduto pd where pd.codemp=new.codemppd and pd.codfilial=new.codfilialpd and pd.codprod=new.codprod
            into :tipoprod;
    
            if(:tipoprod='F') then
            begin
                new.sitproditorc='PE';
            end
    
        end
    
        -- Atualiza status do item, quando produzido
        if( old.sitproditorc!=new.sitproditorc and new.sitproditorc='PD' ) then
        begin
           new.statusitorc = 'OP';
        end
        
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGAD
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
  declare variable subtipovenda char(2);
  declare variable estoqtipomovpd char(1);
BEGIN
  IF ( not ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
  BEGIN
      estoqtipomovpd = 'S';
      
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
          V.CODEMPTM, V.CODFILIALTM, V.CODTIPOMOV, V.SUBTIPOVENDA
       FROM VDVENDA V  WHERE V.CODVENDA = old.CODVENDA AND
          V.CODEMP=old.CODEMP AND V.CODFILIAL = old.CODFILIAL AND
          V.TIPOVENDA=old.TIPOVENDA
      INTO :DDTVENDA, :CFLAG, :IDOCVENDA, :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :subtipovenda;
      
      if ( (subtipovenda is not null) and (subtipovenda='NC') ) then
         estoqtipomovpd = 'N';
         
      EXECUTE PROCEDURE EQMOVPRODIUDSP('D', old.CODEMPPD, old.CODFILIALPD,
         old.CODPROD, old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
         :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, null, null, null,
         null, null, null, null,
         old.CODEMP, old.CODFILIAL, old.TIPOVENDA, old.CODVENDA, old.CODITVENDA,
          null, null, null, null,null,null, null, null, null,
         old.CODEMPNT, old.CODFILIALNT, old.CODNAT, :DDTVENDA,
         :IDOCVENDA, :CFLAG, old.QTDITVENDA, old.PRECOITVENDA,
         old.CODEMPAX, old.CODFILIALAX, old.CODALMOX, null, :estoqtipomovpd);
      
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGAI
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
    declare variable subtipovenda char(2);
    declare variable estoqtipomovpd char(1);

begin
    estoqtipomovpd = 'S';
    
    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    -- Carregamento de informações do cabeçalho da venda
    select vd.dtemitvenda, vd.flag, vd.docvenda, vd.codemptm, vd.codfilialtm, vd.codtipomov, vd.subtipovenda
        from eqtipomov tm, vdvenda vd
        where
            tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.tipovenda=new.tipovenda and vd.codvenda=new.codvenda
    into :dtvenda, :flag, :docvenda, :codemptm, :codfilialtm, :codtipomov, :subtipovenda;

    if ( (subtipovenda is not null) and (subtipovenda='NC')) then
       estoqtipomovpd='N';
       
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
        new.codempax, new.codfilialax, new.codalmox, null, :estoqtipomovpd
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
ALTER TRIGGER VDITVENDATGAU
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
  DECLARE VARIABLE CSTATUS CHAR(2);
  DECLARE VARIABLE CTIPOPROD varCHAR(2);
  DECLARE VARIABLE NPRECO NUMERIC(15, 5);
  DECLARE VARIABLE visualizalucr CHAR(1);
  DECLARE VARIABLE custopeps NUMERIC(15, 5);
  DECLARE VARIABLE custompm NUMERIC(15, 5);
  DECLARE VARIABLE custouc NUMERIC(15, 5);
  declare variable subtipovenda char(2);
  declare variable estoqtipomovpd char(1);

BEGIN
  
  estoqtipomovpd = 'S';
  select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) ) THEN
  BEGIN
      SELECT VD.DTEMITVENDA,VD.DOCVENDA,VD.STATUSVENDA,VD.FLAG,
             VD.CODEMPTM, VD.CODFILIALTM, VD.CODTIPOMOV, VD.SUBTIPOVENDA
        FROM VDVENDA VD
        WHERE VD.CODVENDA = new.CODVENDA AND VD.TIPOVENDA = new.TIPOVENDA
            AND VD.CODEMP=new.CODEMP AND VD.CODFILIAL = new.CODFILIAL
        INTO :DDTVENDA, :IDOCVENDA, :CSTATUS, :CFLAG, :ICODEMPTM,
          :SCODFILIALTM, :ICODTIPOMOV, :SUBTIPOVENDA;
          
      if ( (subtipovenda is not null) and (subtipovenda='NC') ) then
          estoqtipomovpd = 'N';
          
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
        new.CODEMPAX, new.CODFILIALAX, new.CODALMOX, null, :estoqtipomovpd);


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
ALTER TRIGGER VDITVENDATGBI
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable precocomisprod numeric(15,5);
    declare variable redfisc numeric(9,2);
    declare variable redbasest char(1);
    declare variable ufcli char(2);
    declare variable ufemp char(2);
    declare variable percicmscm numeric(9,2);


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
        if (new.vlrbaseicmsstretitvenda is null) then new.vlrbaseicmsstretitvenda = 0;
        if (new.vlricmsstitvenda is null) then new.vlricmsstitvenda = 0;
        if (new.vlricmsstretitvenda is null) then new.vlricmsstretitvenda = 0;
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
            select first 1 coalesce(c.aliqissfisc, f.percissfilial)
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

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end


                        if(redfisc>0 and redbasest='S') then
                        begin
    
                            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
    
                            new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                            new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));
    
                        end
                        else
                        begin
    
                            -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                            new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                            new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));
    
                        end
                     end



            
            else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
            begin
                percicmscm = 0.00;

                -- Buscando estado do cliente

                select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                into ufcli;

                -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                select coalesce(ic.aliqfiscintra,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S'), coalesce(ic.aliqicmsstcm,0.00) from lfitclfiscal ic
                where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                into percicmsst, redfisc, redbasest, percicmscm  ;
                -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                if (percicmsst = 0) then
                begin
                    select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                    into PERCICMSST;
                end
                if(new.calcstcm = 'N') then
                begin

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
                 else
                 begin
                    if(redfisc>0 and redbasest='S') then
                    begin

                         -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                         new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                    end
                    else
                    begin
                         new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsbrutitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                    end
                         new.vlroutrasitvenda = 0;
                         new.vlrisentasitvenda = 0;
                         new.vlricmsstitvenda =  ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100)  );
                 end
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
ALTER TRIGGER VDITVENDATGBU
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable ntmp numeric(15, 5);
    declare variable precocomisprod numeric(15, 5);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable ufcli char(2);
    declare variable ufemp char(2);
    declare variable redbasest char(1);
    declare variable redfisc numeric(9,2);
    declare variable codfilialpf smallint;
    declare variable fatorcparc char(1);
    declare variable percicmscm numeric(9,2);


    begin
        percicmscm = 0.00;


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
            if ( (old.qtditvenda<>new.qtditvenda) and (new.qtditvenda<>0) ) then
            begin
               select icodfilial from sgretfilial(old.codemp,'SGPREFERE1') into :codfilialpf; 
               select fatorcparc from sgprefere1 p 
                    where p.codemp=old.codemp and p.codfilial=:codfilialpf
                 into :fatorcparc;
               if (fatorcparc='S') then
               begin
                  exception vditvendaex05;
               end 
            end
            -- Ser for um serviço
            if (stipoprod = 'S') then
            begin
                -- Calculo do ISS
                select first 1 coalesce(c.aliqissfisc, f.percissfilial)
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
                    new.vlroutrasitvenda = new.vlrliqitvenda;
                    new.vlrbaseicmsitvenda = 0;
                    new.percicmsitvenda = 0;
                    new.vlricmsitvenda = 0;
                    new.vlrisentasitvenda = 0;

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest ;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end

                    if(redfisc>0 and redbasest='S') then
                    begin

                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro

                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
                    else
                    begin

                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
    

                end
                else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
                begin
                     -- Buscando estado do cliente
                    select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    into ufcli;

                   -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0), ic.redbasest, ic.redfisc, coalesce(ic.aliqicmsstcm,0.00) from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into PERCICMSST,redbasest, redfisc, percicmscm;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                        into PERCICMSST;
                    end

                    new.vlroutrasitvenda = 0;
                    new.VLRISENTASITVENDA = 0;

                   if(new.calcstcm = 'N') then
                    begin
                        
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
        
                        --new.vlroutrasitvenda = 0;
                        --new.vlrisentasitvenda = 0;
                        new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;
                     end
                     else
                     begin
                         
                        if(redfisc>0 and redbasest='S') then
                        begin
    
                             -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                             new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(:percicmscm,0)/100) ))/(:PERCICMSST/100);
                        end
                        else
                        begin
                             new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsbrutitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                        end
                        new.vlroutrasitvenda = 0;
                        new.vlrisentasitvenda = 0;
                        new.vlricmsstitvenda =  ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100)  );
                     end
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
ALTER TRIGGER VDVENDAORCTGAI
AS
    declare variable qtditvenda numeric(15,5);
begin
    -- Inserção de registro de movimentação de numero de série,
    -- para faturamento de seviços de conserto (recmerc/Ordens de serviço)
   -- EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'inicio do vdvendaorctgai '|| cast('now' as time);

    insert into eqmovserie (
        codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
        numserie    , codempvd      , codfilialvd   , codvenda      , coditvenda    , tipovenda  ,
        dtmovserie  , tipomovserie  , docmovserie
    )
    select
        ir.codemp, ir.codfilial, coalesce((select max(codmovserie) + 1 from eqmovserie where codemp=vd.codemp and codfilial=vd.codfilial),1), ir.codemppd, ir.codfilialpd, ir.codprod,
        ir.numserie, new.codemp, new.codfilial, new.codvenda, new.coditvenda, new.tipovenda, vd.dtsaidavenda, -1, vd.docvenda
    from eqitrecmercitositorc ro, eqitrecmerc ir, vdvenda vd, vditvenda iv
    where
        ro.codemp=new.codempor and ro.codfilial=new.codfilialor and ro.codorc=new.codorc and ro.tipoorc=new.tipoorc and ro.coditorc=new.coditorc and
        ir.codemp=ro.codemp and ir.codfilial=ro.codfilial and ir.ticket=ro.ticket and ir.coditrecmerc=ro.coditrecmerc and
        iv.codemp=new.codemp and iv.codfilial=new.codfilial and iv.codvenda=new.codvenda and iv.tipovenda=new.tipovenda and iv.coditvenda=new.coditvenda and
        vd.codemp=iv.codemp and vd.codfilial=iv.codfilial and vd.tipovenda=iv.tipovenda and vd.codvenda=iv.codvenda and
        ir.numserie is not null;

--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após insert no eqmovserie '|| cast('now' as time);

    -- Atualizando status do item de orçamento indicando que o mesmo foi faturado.
    select iv.qtditvenda from vditvenda iv where iv.codemp=new.codemp and iv.codfilial=new.codfilial and 
       iv.tipovenda=new.tipovenda and iv.codvenda=new.codvenda and iv.coditvenda=new.coditvenda
       into :qtditvenda;

--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após select vditvenda '|| cast('now' as time);
       
    update vditorcamento io set
    --io.emmanut='S',
    io.statusitorc='OV', io.qtdfatitorc=coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0)
    where io.codemp=new.codempor and io.codfilial=new.codfilialor and io.codorc=new.codorc and io.tipoorc=new.tipoorc and io.coditorc=new.coditorc
    and (io.statusitorc<>'OV' or io.qtdfatitorc is null or io.qtdfatitorc<>coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0) ) ;

--    update vditorcamento io set io.emmanut='N'
--     where io.codemp=new.codempor and io.codfilial=new.codfilialor and io.codorc=new.codorc and io.tipoorc=new.tipoorc and io.coditorc=new.coditorc
--    and io.emmanut='S'
--    and (io.statusitorc<>'OV' or io.qtdfatitorc is null or io.qtdfatitorc<>coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0) ) ;
--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após update vditorcamento '|| cast('now' as time);


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGAU
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
    declare variable qtditvenda numeric(15,5);
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
    declare variable icodmodnota integer;

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
                   new.vlrliqvenda,dtrec, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           end
           else
           begin
               delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
           
           SELECT CODMODNOTA FROM EQTIPOMOV m WHERE m.codemp=new.codemptm and m.codfilial=new.codfilialtm and m.codtipomov=new.codtipomov INTO ICODMODNOTA;
           --execute procedure sgdebugsp 'vdvendatgbu', 'PEGOU O MODELO DE NOTA:'||:ICODMODNOTA;
           IF(:ICODMODNOTA=55) THEN
           BEGIN
             --execute procedure sgdebugsp 'vdvendatgbu', 'ENTROU NO IF MODELO DA NOTA É IGUAL A 55:';
             execute procedure sggeracnfsp('VD', new.codemp, new.codfilial, new.tipovenda, new.codvenda,null,null,null);
           END
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob,
                   new.codempca, new.codfilialca, new.numconta,
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

        delete from vdvendaorc where codemp=new.codemp and codfilial=new.codfilial and
        codvenda=new.codvenda and tipovenda=new.tipovenda;

        update vditvenda set qtditvendacanc=qtditvenda, qtditvenda=0 where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilial=new.codfilial;

        delete from fnreceber where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilialva=new.codfilial;

      end
      -- Verificação se existem registros na tabela eqmovserie com o mesmo código de venda
      -- caso existam registros, os mesmos devem ser atualizados para o novo documento de venda,
      -- para que sejam listados no módulo GMS Consulta Mov. Série, com o documento correto de saída.
      -- antes desta alteração havia apenas a inserção de registros na tabela eqmovserie com número
      -- de documento de pedido, não sendo atualizado após o pedido se transforme em PV PS PR PD
      if (old.docvenda<>new.docvenda) then 
      begin
         update eqmovserie eqm set eqm.docmovserie=new.docvenda 
            where eqm.codempvd=new.codemp and eqm.codfilialvd=new.codfilial and 
              eqm.tipovenda=new.tipovenda and eqm.codvenda=new.codvenda and eqm.docmovserie<>new.docvenda;
      end

   end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGBU
AS
  DECLARE VARIABLE ICODFILIAL INTEGER;
  DECLARE VARIABLE ICODITVENDA INTEGER;
  DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE sSerie CHAR(4);
  DECLARE VARIABLE credicmssimples CHAR(1);
  DECLARE VARIABLE iCodFilialPref smallint;
  DECLARE VARIABLE dDesc NUMERIC(15, 5);
  DECLARE VARIABLE PERCPISFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCCOFINSFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCIRFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCCSOCIALFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCSIMPLESFILIAL NUMERIC(9,2);
  DECLARE VARIABLE VLRPRODITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE QTDITVENDA NUMERIC(9,2);
  DECLARE VARIABLE VLRCOMISITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE VLRDESCITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE PERCCOMISITVENDA NUMERIC(9,2);
  DECLARE VARIABLE SIMPLESFILIAL CHAR(1);
  DECLARE VARIABLE SIMPLESCLI CHAR(1);
  DECLARE VARIABLE PESSOACLI CHAR(1);          
  DECLARE VARIABLE NVLRFRETE NUMERIC(15,5);
  DECLARE VARIABLE CADICFRETEVD CHAR(1);
  DECLARE VARIABLE PERCIT NUMERIC(9,2);
  DECLARE VARIABLE RETENSAOIMP CHAR(1);

BEGIN

  retensaoimp = 'N';

  IF (new.EMMANUT IS NULL) THEN
     new.EMMANUT='N';
  if (new.BLOQVENDA IS NULL) then
     new.BLOQVENDA='N';
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      if ( ( (old.BLOQVENDA IS  NULL) OR (old.BLOQVENDA='N') ) AND (new.BLOQVENDA='S') )  then
      begin
          new.DTALT=cast('now' AS DATE);
          new.IDUSUALT=user;
          new.HALT=cast('now' AS TIME);
      end
      IF ( (new.DTCOMPVENDA is null) or (old.DTEMITVENDA<>new.DTEMITVENDA)  ) THEN
         new.DTCOMPVENDA=new.DTEMITVENDA;

      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP, 'SGPREFERE1') INTO iCodFilialPref;
      EXECUTE PROCEDURE VDCLIENTEATIVOSP(new.CODEMPCL, new.CODFILIALCL, new.CODCLI);

      if ( ( (old.BLOQVENDA IS NOT NULL AND old.BLOQVENDA='S') or (new.BLOQVENDA='S') ) and old.chavenfevenda=new.chavenfevenda) then
         EXCEPTION VDVENDAEX07 'ESTA VENDA ESTÁ BLOQUEADA!!!';


      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=user;
      new.HALT=cast('now' AS TIME);
      SELECT CODFILIALSEL FROM SGCONEXAO WHERE NRCONEXAO=CURRENT_CONNECTION AND
          CONECTADO > 0 INTO ICODFILIAL;
      IF (substr(old.STATUSVENDA,1,1) = 'C' and substr(new.STATUSVENDA,1,1) <> 'C' and old.chavenfevenda=new.chavenfevenda ) THEN
        EXCEPTION VDVENDAEX05;
      IF (substr(old.STATUSVENDA,1,1) = 'D' and substr(old.STATUSVENDA,1,1) <> 'D' and old.chavenfevenda=new.chavenfevenda) THEN
        EXCEPTION VDVENDAEX05 'ESTA VENDA FOI DEVOLVIDA!';
      IF ((SUBSTR(old.STATUSVENDA,1,1) = 'P') AND (SUBSTR(new.STATUSVENDA,1,1) = 'V' ) AND new.IMPNOTAVENDA = 'N') THEN
      BEGIN
        if ( new.subtipovenda = 'NC' ) then
        begin
             SELECT T2.CODTIPOMOV, T2.SERIE FROM EQTIPOMOV T2, EQTIPOMOV T WHERE T2.CODEMP=T.CODEMPTC
               AND T2.CODFILIAL=T.CODFILIALTC AND T2.CODTIPOMOV = T.CODTIPOMOVTC
               AND T.CODEMP=new.CODEMPTM AND T.CODFILIAL=new.CODFILIALTM AND T.CODTIPOMOV=new.CODTIPOMOV
               INTO :iCodTipoMov, :sSerie;
        end
        if ( ( new.subtipovenda <> 'NC') or (iCodTipoMov is null) ) then 
        begin
		     SELECT T2.CODTIPOMOV, T2.SERIE FROM EQTIPOMOV T2, EQTIPOMOV T WHERE T2.CODEMP=T.CODEMPTM
               AND T2.CODFILIAL=T.CODFILIALTM AND T2.CODTIPOMOV = T.CODTIPOMOVTM
               AND T.CODEMP=new.CODEMPTM AND T.CODFILIAL=new.CODFILIALTM AND T.CODTIPOMOV=new.CODTIPOMOV
               INTO :iCodTipoMov, :sSerie;
        end
        IF (iCodTipoMov IS NULL) THEN
          SELECT T.CODTIPOMOV, T.SERIE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMPTM=T.CODEMP AND
                 P.CODFILIALTM=T.CODFILIAL AND P.CODTIPOMOV = T.CODTIPOMOV
                 AND P.CODEMP=new.CODEMP AND P.CODFILIAL = :iCodFilialPref INTO :iCodTipoMov, :sSerie;
        new.CODTIPOMOV = :iCodTipoMov;
        new.SERIE = :sSerie;
        IF ( ( not (old.IMPNOTAVENDA = 'S') ) AND ( not (new.IMPNOTAVENDA = 'S') ) ) THEN
        BEGIN
            SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMPSE,new.CODFILIALSE) INTO new.DocVenda;
            new.IMPNOTAVENDA = 'S';
        END
      END
      SELECT FISCALTIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
             AND CODEMP=new.CODEMP AND CODFILIAL = new.codfilialtm INTO new.FLAG;
      IF (new.FLAG<>'S') THEN
        new.FLAG = 'N';
      SELECT VLRFRETEVD, ADICFRETEVD FROM VDFRETEVD WHERE CODVENDA=old.CODVENDA AND TIPOVENDA=old.TIPOVENDA AND ADICFRETEVD = 'S'
             AND CODEMP=old.CODEMP AND CODFILIAL = old.codfilial INTO new.VLRFRETEVENDA, :CADICFRETEVD;
      IF (new.VLRDESCVENDA IS NULL) THEN
        new.VLRDESCVENDA = 0;
      IF (new.VLRDESCITVENDA IS NULL) THEN
        new.VLRDESCITVENDA = 0;
      IF (new.VLRADICVENDA IS NULL) THEN
        new.VLRADICVENDA = 0;
      IF (new.VLRFRETEVENDA IS NULL) THEN
        new.VLRFRETEVENDA = 0;
      IF (new.VLRPRODVENDA IS NULL) THEN
        new.VLRPRODVENDA = 0;
      IF (new.VLRIPIVENDA IS NULL) THEN
        new.VLRIPIVENDA = 0;
      IF (new.VLRBASEICMSVENDA IS NULL) THEN
        new.VLRBASEICMSVENDA = 0;
      IF (new.VLRDESCITVENDA > 0) THEN
        dDesc = new.VLRDESCITVENDA;
      ELSE
        dDesc = new.VLRDESCVENDA;
      IF (new.VLRBASEICMSSTVENDA IS NULL) THEN
        new.VLRBASEICMSSTVENDA = 0;
      IF (new.VLRICMSSTVENDA IS NULL) THEN
        new.VLRICMSSTVENDA = 0;

      SELECT C.SIMPLESCLI,C.PESSOACLI FROM VDCLIENTE C WHERE C.CODCLI=new.CODCLI AND
        C.CODFILIAL=new.CODFILIALCL AND C.CODEMP=new.CODEMPCL INTO SIMPLESCLI,PESSOACLI;
      SELECT SIMPLESFILIAL,PERCPISFILIAL,PERCCOFINSFILIAL,PERCIRFILIAL,PERCCSOCIALFILIAL,coalesce(PERCSIMPLESFILIAL,0)
        FROM SGFILIAL WHERE CODEMP=new.CODEMP AND CODFILIAL=:ICODFILIAL
        INTO SIMPLESFILIAL,PERCPISFILIAL,PERCCOFINSFILIAL,PERCIRFILIAL,PERCCSOCIALFILIAL,PERCSIMPLESFILIAL;
      IF (SIMPLESFILIAL = 'N') THEN
      BEGIN
        new.VLRIRVENDA = (new.vlrliqvenda/100)*PERCIRFILIAL;
        new.VLRCSOCIALVENDA = (new.vlrliqvenda/100)*PERCCSOCIALFILIAL;
      END
      ELSE
      BEGIN
        new.VLRIRVENDA = 0;
        new.VLRCSOCIALVENDA = 0;

        /*Verifica se deve destacar crédito de icms (simples)*/
        select p1.credicmssimples,p1.retensaoimp from sgprefere1 p1 where p1.codemp=new.codemp and p1.codfilial=:icodfilialpref
        into credicmssimples,retensaoimp;

        if(credicmssimples='S') then
        begin
            new.vlricmssimples = (new.vlrprodvenda/100) * percsimplesfilial ;
            new.percicmssimples = percsimplesfilial;
        end

      END
      if (CADICFRETEVD = 'S') then
         NVLRFRETE = new.VLRFRETEVENDA;
      else
         NVLRFRETE = 0;

      new.VLRLIQVENDA = coalesce(new.VLRPRODVENDA,0)
                      - coalesce(dDesc,0)
                      + coalesce(new.VLRADICVENDA,0)
                      + coalesce(:NVLRFRETE,0)
                      + coalesce(new.VLRIPIVENDA,0)
                      + coalesce(new.vlricmsstvenda,0);

      if (SIMPLESCLI = 'N' AND PESSOACLI = 'J' AND RETENSAOIMP = 'S') then
      begin
        new.VLRLIQVENDA =
             cast(
                (coalesce(new.VLRLIQVENDA,0)) -
                (case when new.calcpisvenda='S' then coalesce(new.vlrpisvenda,0) else 0 end) -
                (case when new.calccofinsvenda='S' then coalesce(new.vlrcofinsvenda,0) else 0 end) -
                (case when new.calcirvenda='S' then coalesce(new.vlrirvenda,0) else 0 end) -
                (case when new.calccsocialvenda='S' then coalesce(new.vlrcsocialvenda,0) else 0 end)
            as numeric(15, 5));
      end

      IF ((substr(old.STATUSVENDA,1,1) IN ('P','V')) AND (substr(new.STATUSVENDA,1,1)='C')) THEN
      BEGIN
          new.VLRDESCITVENDA = 0;
          new.VLRPRODVENDA = 0;
          new.VLRBASEICMSVENDA = 0;
          new.VLRICMSVENDA = 0;
          new.VLRISENTASVENDA = 0;
          new.VLROUTRASVENDA = 0;
          new.VLRBASEIPIVENDA = 0;
          new.VLRIPIVENDA = 0;
          new.VLRLIQVENDA = 0;
          new.VLRCOMISVENDA = 0;
          new.VLRISSVENDA = 0;
          new.VLRBASEISSVENDA = 0;
          new.vlrpisvenda = 0;
          new.vlrcofinsvenda = 0;
          new.vlrirvenda = 0;
          new.vlrcsocialvenda =0;
          new.vlrbaseicmsstvenda=0;
          new.vlricmsstvenda=0;
      END
    /**
       COMISSAO
    **/
      IF ((NOT NEW.VLRCOMISVENDA IS NULL) AND
          (NEW.VLRLIQVENDA > 0) AND
          ((NOT NEW.VLRDESCVENDA = OLD.VLRDESCVENDA) OR (NOT NEW.PERCMCOMISVENDA = OLD.PERCMCOMISVENDA))) then
      BEGIN
        /* Distribuindo a comissao: */
        FOR SELECT CODITVENDA,VLRPRODITVENDA,QTDITVENDA,coalesce(VLRCOMISITVENDA,0),coalesce(vlrdescitvenda,0),
            coalesce(iv.perccomisitvenda,0)
            FROM VDITVENDA IV
            WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL AND CODVENDA=new.CODVENDA and tipovenda=new.tipovenda
            INTO ICODITVENDA,VLRPRODITVENDA,QTDITVENDA,VLRCOMISITVENDA,VLRDESCITVENDA,PERCCOMISITVENDA DO
        BEGIN
          /* Calculo do item.: */
          /* INCLUIDO PARA DISTRIBUIR A COMISSAO MENOS O DESCONTO PROPORCIONALMENTE*/
          PERCIT = 0;
          IF (new.VLRPRODVENDA > 0 AND NOT new.VLRDESCITVENDA > 0 AND new.VLRDESCVENDA > 0) THEN
          BEGIN
            PERCIT = (100*VLRPRODITVENDA) / new.VLRPRODVENDA;
            VLRDESCITVENDA = (new.VLRDESCVENDA  * PERCIT) / 100;
          END
          IF (new.VLRPRODVENDA > 0 AND new.PERCMCOMISVENDA > 0) THEN
          BEGIN
            PERCCOMISITVENDA = new.PERCMCOMISVENDA;
            /* Retira.. */
            new.VLRCOMISVENDA = new.VLRCOMISVENDA - VLRCOMISITVENDA;
            /* Atualiza.. */
            VLRCOMISITVENDA = ((VLRPRODITVENDA - VLRDESCITVENDA) * PERCCOMISITVENDA) / 100;
            /* Adiciona.. */
            new.VLRCOMISVENDA = new.VLRCOMISVENDA + VLRCOMISITVENDA;
          END
          ELSE IF (new.PERCMCOMISVENDA=0) then
          BEGIN
              VLRCOMISITVENDA = 0;
              PERCCOMISITVENDA = 0;
              new.VLRCOMISVENDA = 0;
          END
          UPDATE VDITVENDA SET VLRCOMISITVENDA=:VLRCOMISITVENDA,PERCCOMISITVENDA=:PERCCOMISITVENDA
          WHERE CODITVENDA=:ICODITVENDA AND CODVENDA=new.CODVENDA AND TIPOVENDA=new.TIPOVENDA AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
        END
      END
      /* Calcula o percentual medio da comissao */
      ELSE IF (new.PERCMCOMISVENDA = old.PERCMCOMISVENDA AND new.VLRLIQVENDA > 0) THEN
      begin
        -- new.PERCMCOMISVENDA = (new.VLRCOMISVENDA/new.VLRLIQVENDA)*100.000;
    -- Modificado, pois causava divergencia em vendas geradas a partir de orçamentos.
    if ((new.vlrprodvenda-new.vlrdescvenda)>0) then
    begin
        new.PERCMCOMISVENDA = (new.VLRCOMISVENDA/(new.vlrprodvenda-new.vlrdescvenda)) * 100;
    end
    else
    begin
        new.PERCMCOMISVENDA = 0;
    end
      end

      IF (new.STATUSVENDA = 'V4') THEN
      BEGIN
        new.IMPNOTAVENDA = 'S';
        new.STATUSVENDA = 'V3';
      END
      IF ((new.IMPNOTAVENDA = 'S') AND (old.IMPNOTAVENDA = 'S')) THEN
      BEGIN
        new.DOCVENDA = old.DOCVENDA;
      END
  END

  -- Atualizando o status do documento fiscal para 02 - Documento cancelado, quando nota for cancelado pelo sistema.
  IF (substr(new.STATUSVENDA,1,1) = 'C' and new.sitdoc!='02') THEN
  begin
    new.sitdoc = '02';
  end

  if(old.chavenfevenda is null and new.chavenfevenda is not null) then
  begin
    new.emmanut = 'N';
  end


END
^

/* Alter empty procedure EQMOVPRODCKTMSP with new param-list */
ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure EQMOVPRODCSLDSP with new param-list */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

/* Alter empty procedure EQMOVPRODPRCSLDSP with new param-list */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
/* Alter (ATRESUMOATENDOSP01) */
SET TERM ^ ;

ALTER PROCEDURE ATRESUMOATENDOSP01(CODEMPP INTEGER,
CODFILIALP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(ANO SMALLINT,
MES SMALLINT,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
QTDCONTR DECIMAL(15,5),
DTINICIO DATE,
VALOR DECIMAL(15,5),
VALOREXCEDENTE DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
QTDHORAS DECIMAL(15,2),
MESCOB INTEGER,
SALDOMESCOB DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
SALDOPERIODO DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
EXCEDENTEPERIODO DECIMAL(15,5),
EXCEDENTECOB DECIMAL(15,5),
VALOREXCEDENTECOB DECIMAL(15,5),
VALORTOTALCOB DECIMAL(15,5),
VALORCONTR DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
TOTFRANQUIAMES DECIMAL(15,5))
 AS
begin
  saldoperiodo = 0;
 -- saldoperiodo2 = 0;
  saldomescob = 0;
  excedentemescob = 0;
  excedenteperiodo = 0;
--  excedenteperiodo2 = 0;
  excedentecob = 0;
  valorexcedentecob = 0;
  valortotalcob = 0;
  -- MES COBRANÇA
  mescob = extract(month from :dtfimp);
  -- DATA DO MÊS ANTERIOR A COBRANÇA
  --dtant = addmonth(:dtfimp, -1);
  -- MES ANTERIOR A COBRANÇA
 -- mesant = extract(month from :dtant);


  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
    , avg((select avg(ic.vlritcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valor
   , avg((select avg(ic.vlritcontrexced) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valorexcedente
   , avg((select sum(qtditcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  qtditcontr
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes

   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr, :qtdcontr, :dtinicio, :valor
   , :valorexcedente, :qtditcontr, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       if (mes=mescob) then
          valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras > :qtditcontr) and (mes = mescob) ) then
          excedentemescob = excedentemes;

       if (mes=mescob) then
       begin
          --- SALDO DO MÊS DE COBRANÇA
          saldomescob = saldomescob + saldomes;
       end
       else
       begin
         -- SALDO DO PERÍODO
         saldoperiodo = saldoperiodo + saldomes;

         -- EXCEDENTE DO PERÍODO
          excedenteperiodo = excedenteperiodo + excedentemes;

       end 

   end

   -- EXCEDENTE COBRADO
   if( :excedenteperiodo - :saldoperiodo > 0) then
       excedentecob =  excedenteperiodo - saldoperiodo;
   else
       excedentecob = 0;

   if (excedentecob>0) then
      saldoperiodo = 0;

   saldoperiodo = saldoperiodo + saldomescob;
   excedentecob = excedentemescob;

   --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

   -- VALOR A COBRAR EXCEDENTE
   valorexcedentecob = excedentecob * valorexcedente;
        
   -- VALOR TOTAL A COBRAR
   valortotalcob = (valorcontr) + (excedentecob * valorexcedente);

 -- saldoperiodo2 = 0;
  excedentemescob = 0;
  --excedenteperiodo2 = 0;

  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes
   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr
   , :qtdcontr, :dtinicio, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras < :qtditcontr)
       --or (mes <> mescob)
       ) then
          excedentemescob = 0;
       else
          excedentemescob = qtdhoras - qtditcontr;

       -- SALDO DO PERÍODO
--       saldoperiodo2 = saldoperiodo2 + saldomes;

       -- EXCEDENTE DO PERÍODO
  --     excedenteperiodo2 = excedenteperiodo2 + excedentemescob;

       --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

       -- VALOR A COBRAR EXCEDENTE
--       valorexcedentecob = excedentecob * valorexcedente;
        
       -- VALOR TOTAL A COBRAR
--       valortotalcob = (qtditcontr * valor) + (excedentecob * valorexcedente);

      suspend;
   end


end
^

/* Alter (ATRESUMOATENDOSP02) */
ALTER PROCEDURE ATRESUMOATENDOSP02(CODEMPCLP INTEGER,
CODFILIALCLP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
RAZCLI VARCHAR(60),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODITCONTR INTEGER,
CODCONTR INTEGER,
DESCCONTR VARCHAR(100),
VLRHORA DECIMAL(15,5),
VLRHORAEXCED DECIMAL(15,5),
QTDCONTR DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
VLRCOB DECIMAL(15,5),
VLRCOBEXCED DECIMAL(15,5),
VLRCOBTOT DECIMAL(15,5),
MES SMALLINT,
ANO SMALLINT,
QTDHORAS DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
ACUMULO SMALLINT,
CDATAINI DATE)
 AS
declare variable dtiniac date;
begin
  for select cl.codemp codempcl, cl.codfilial codfilialcl, cl.codcli, cl.razcli
    , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr, ct.desccontr, ct.dtinicio
    from vdcliente cl, vdcontrato ct
    where cl.codemp=:codempclp and cl.codfilial=:codfilialclp and (:codclip=0 or cl.codcli=:codclip)
       and ct.codemp=:codempctp and ct.codfilial=:codfilialctp and (:codcontrp=0 or ct.codcontr=:codcontrp)
       and ct.codempcl=cl.codemp and ct.codfilialcl=cl.codfilial and ct.codcli=cl.codcli
       and ct.ativo='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.recebcontr='S'
     order by cl.razcli,  cl.codcli,  ct.codcontr
  into :codempcl, :codfilialcl, :codcli, :razcli
    , :codempct, :codfilialct, :codcontr, :desccontr, :cdataini
  do
  begin


--       and i.codemp=ct.codemp  and i.codfilial=ct.codfilial and i.codcontr=ct.codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontrp)

  --vditcontrato i,

      select max(i.acumuloitcontr) from vditcontrato i
      where i.codemp=:codempct  and i.codfilial=:codfilialct and i.codcontr=:codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontr)
      and i.acumuloitcontr is not null
      into :acumulo;
      if ( (:acumulo is null) or (:acumulo=0) ) then
      begin
        acumulo = 0;
        dtiniac = dtinip;
      end
      else
      begin
        dtiniac = dtinip;
        dtiniac = cast( addmonth(dtiniac, -1*acumulo) as date);
        if( dtiniac > dtinip ) then
         dtiniac = dtinip;
      end
      for select a.mes, a.ano, a.qtdcontr, a.valor, a.valorexcedente, a.qtditcontr, a.qtdhoras, a.valortotalcob
       , a.saldomes, a.excedentemes, a.excedentemescob, a.valorexcedentecob , a.valorcontr
         from atresumoatendosp01(:codempcl, :codfilialcl, :codcli
         , :codempct, :codfilialct, :codcontr, :coditcontrp, :dtiniac, :dtfimp) a
      into :mes, :ano, :qtdcontr, :vlrhora, :vlrhoraexced, :qtditcontr, :qtdhoras, :vlrcobtot
      , :saldomes, :excedentemes, :excedentemescob, :vlrcobexced, :vlrcob
      do
      begin
        
      suspend;
      end
  end
end
^

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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
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
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
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
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
declare variable refprod varchar(20);
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

/* Alter (CPADICITCOMPRARECMERCSP) */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
declare variable usaprecocot char(1);
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable codnat char(4);
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitcompra numeric(15,5);
declare variable aprovpreco char(1);
declare variable codemppp integer;
declare variable codfilialpp smallint;
declare variable codplanopag integer;
declare variable vlrproditcompra numeric(15,5);
declare variable qtditrecmerc numeric(15,5);
declare variable codempns integer;
declare variable codfilialns smallint;
declare variable numserietmp varchar(30);
declare variable percprecocoletacp numeric(15,5);
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;
    
    -- Buscando preferências GMS
    select coalesce(p8.percprecocoletacp,100) percprecocoletacp
    from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :percprecocoletacp;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc, ir.qtditrecmerc,
        ir.codempns, ir.codfilialns, ir.numserie
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc, :qtditrecmerc,
        :codempns, :codfilialns, :numserietmp
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov,null)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Se deve buscar preço de cotação.
                if( 'S' = :usaprecocot) then
                begin
                    -- Deve implementar ipi, vlrliq etc... futuramente...
                    select first 1 ct.precocot
                    from cpcotacao ct, cpsolicitacao sl, cpitsolicitacao iso
                    left outer join eqrecmerc rm on
                    rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket
                    where
                    iso.codemp = sl.codemp and iso.codfilial=sl.codfilial and iso.codsol=sl.codsol
                    and ct.codemp=iso.codemp and ct.codfilial=iso.codfilial and ct.codsol=iso.codsol and ct.coditsol=iso.coditsol
                    and iso.codemppd=:codemppd and iso.codfilialpd=:codfilialpd and iso.codprod=:codprod
                    and ct.codempfr=:codempfr and ct.codfilialfr=:codfilialfr and ct.codfor=:codfor
                    and (ct.dtvalidcot>=cast('today' as date) and (ct.dtcot<=cast('today' as date)))
                    and ct.sititsol not in ('EF','CA')

                    and ( (ct.rendacot = rm.rendaamostragem) or ( coalesce(ct.usarendacot,'N') = 'N') )

                    and ( (ct.codemppp=:codemppp and ct.codfilialpp=:codfilialpp and ct.codplanopag=:codplanopag)
                       or (ct.codplanopag is null))

                    order by ct.dtcot desc
                    into :precoitcompra;

                    if(:precoitcompra is not null) then
                    begin
                        -- Indica que o preço é aprovado (cotado anteriormente);
                        aprovpreco = 'S';
                    end

                end

                -- Se não conseguiu obter o preço das cotações
                if(precoitcompra is null) then
                begin
                    -- Buscando preço de compra da tabela de custos de produtos
                    select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                    cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                    into :precoitcompra;

                end

                -- verifica se quantidade está zerada (coleta) se estiver preechida (trata-se de uma pesagem)
                if ( (qtditcompra is null) or (qtditcompra = 0) ) then 
                begin
                    qtditcompra = qtditrecmerc;
                end

                if ( ( :percprecocoletacp is not null) and (:percprecocoletacp<>100) ) then
                begin
                   precoitcompra = cast( :precoitcompra / 100 * :percprecocoletacp as decimal(15,5) ); 
                end
                 
                vlrproditcompra = :precoitcompra * qtditcompra;

                -- Inserir itens
                
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra,
                codempns, codfilialns, numserietmp)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra,
                :codempns, :codfilialns,  :numserietmp) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Alter (EQBUSCASIMILARSP) */
ALTER PROCEDURE EQBUSCASIMILARSP(CODEMP INTEGER,
CODFILIAL CHAR(8),
IPROD INTEGER,
SREFPROD VARCHAR(20))
 RETURNS(SRET CHAR(13),
IRET INTEGER,
CORIG CHAR(1),
ICODFOR INTEGER,
SRAZFOR CHAR(60),
NPRECO NUMERIC(15,5),
SSIM CHAR(18),
SSIMMASTER CHAR(18))
 AS
declare variable sfk char(21);
declare variable sbusca char(21);
BEGIN
/*  IF (IPROD IS NULL) THEN
  BEGIN
    SELECT CODPROD FROM EQPRODUTO WHERE CODEMP=:CODEMP
      AND CODFILIAL=:CODFILIAL AND REFPROD=:SREFPROD
      INTO IPROD;
    SBUSCA = CAST(SREFPROD AS CHAR(21));
  END
  ELSE
  BEGIN
    SBUSCA = CAST(IPROD AS VARCHAR(21));
  END
  FOR SELECT 'S',S.REFPRODSIM,S.REFPRODSIMFK,CAST(NULL AS INTEGER),
    CAST(NULL AS CHAR(50)),CAST(NULL AS NUMERIC(15, 5)),
    S.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=S.CODPROD
    AND P.CODEMP=S.CODEMP AND P.CODFILIAL=S.CODFILIAL)
    FROM EQSIMILAR S WHERE
    (S.CODPROD = :IPROD OR S.REFPRODSIM = :SBUSCA)
    AND S.CODEMP=:CODEMP AND S.CODFILIAL=:CODFILIAL INTO
    CORIG,SSIM,SFK,ICODFOR,SRAZFOR,NPRECO,IRET,SRET DO
  BEGIN
    SSIMMASTER = SSIM;
    SUSPEND;
    FOR SELECT 'K',S.REFPRODSIM,S.REFPRODSIMFK,CAST(NULL AS INTEGER),
      CAST(NULL AS CHAR(50)),CAST(NULL AS NUMERIC(15, 5)),
      S.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=S.CODPROD
      AND P.CODEMP=S.CODEMP AND P.CODFILIAL=S.CODFILIAL)
      FROM EQSIMILAR S WHERE
      (S.REFPRODSIMFK = :SFK OR S.CODPROD=:IRET) AND NOT S.REFPRODSIM=:SSIMMASTER
      AND S.CODEMP=:CODEMP AND S.CODFILIAL=:CODFILIAL INTO
      CORIG,SSIM,SFK,ICODFOR,SRAZFOR,NPRECO,IRET,SRET DO
    BEGIN
      SUSPEND;
    END
  END

  FOR SELECT 'C',C.REFPRODFOR,C.CODFOR,F.RAZFOR,CAST(NULL AS NUMERIC(15, 5)),
    P.CODPROD,(SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=C.CODPROD
    AND P.CODEMP=C.CODEMP AND P.CODFILIAL=C.CODFILIAL)
    FROM CPPRODFOR C, EQPRODUTO P, CPFORNECED F
    WHERE (C.CODPROD = :IPROD OR C.REFPRODFOR = :SBUSCA)
    AND C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND P.CODEMP=C.CODEMP
    AND P.CODFILIAL=C.CODFILIAL AND P.CODPROD=C.CODPROD
    AND F.CODEMP=C.CODEMP AND F.CODFILIAL=C.CODFILIAL
    AND F.CODFOR=C.CODFOR
    UNION
    SELECT 'T',T.REFPRODTFOR,T.CODFOR,F.RAZFOR,T.PRECOPRODTFOR,
    (SELECT P.CODPROD FROM EQPRODUTO P WHERE P.CODEMP=T.CODEMPPD
    AND P.CODFILIAL=T.CODFILIALPD AND P.CODPROD=T.CODPROD),
    (SELECT P.REFPROD FROM EQPRODUTO P WHERE P.CODPROD=T.CODPROD
    AND P.CODEMP=T.CODEMP AND P.CODFILIAL=T.CODFILIAL)
    FROM CPTABFOR T, CPFORNECED F
    WHERE T.REFPRODTFOR = :SBUSCA AND T.CODEMP=:CODEMP
    AND T.CODFILIAL=:CODFILIAL AND F.CODEMP=T.CODEMP
    AND F.CODFILIAL=T.CODFILIAL AND F.CODFOR=T.CODFOR INTO
    CORIG,SSIM,ICODFOR,SRAZFOR,NPRECO,SRET,IRET DO
  BEGIN
    SUSPEND;
  END */
END
^

/* Alter (EQCUSTOPRODSP) */
ALTER PROCEDURE EQCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
declare variable precobase numeric(15,5);
declare variable custompm numeric(15,5);
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

     -- Se o almoxarifado não for selecionado, deve buscar o saldo geral
    if(:icodalmox is null) then
    begin
   --      execute procedure sgdebugsp 'eqcustoprosp','inicio do custo por almoxarifado';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
             ) ,
            (SELECT FIRST 1 M.CUSTOMPMMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
            )
            FROM EQPRODUTO P
            WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND P.CODPROD=:ICODPROD
            INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;
  --       execute procedure sgdebugsp 'eqcustoprosp','após custo mpm';

    end
    else
    begin

   --     execute procedure sgdebugsp 'eqcustoprosp','inicio do custo geral';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPRODAX
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIAL=P.CODFILIAL AND M.CODPROD=P.CODPROD AND M.DTMOVPROD<=:DTESTOQ
                and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         ) ,
         (SELECT FIRST 1 M.CUSTOMPMMOVPROD
         FROM EQMOVPROD M
         WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
               and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
         ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         )
       FROM EQPRODUTO P
       WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
          P.CODPROD=:ICODPROD
       INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;

  --     execute procedure sgdebugsp 'eqcustoprosp','após o custo geral';

    end


  CUSTOUNIT = 0;
  CUSTOTOT = 0;
  IF (SLDPROD IS NULL) THEN
        SLDPROD = 0;
  IF ( (SLDPROD!=0) OR (CCOMSALDO!='S') ) THEN
  BEGIN
      -- Custo PEPS
      IF (CTIPOCUSTO='P') THEN
      BEGIN

         SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP,:SCODFILIAL,:ICODPROD,
            :SLDPROD,:DTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
            INTO :CUSTOUNIT;
         IF (CUSTOUNIT!=0) THEN
            CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo PEPS';

      END
      -- Custo MPM
      ELSE IF (CTIPOCUSTO='M') THEN
      BEGIN
         CUSTOUNIT = CUSTOMPM;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo MPM';

      END
      -- Preço Base
      ELSE IF (CTIPOCUSTO='B') THEN
      BEGIN
         CUSTOUNIT = PRECOBASE;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      --    execute procedure sgdebugsp 'eqcustoprosp','após precobase';

      END
      -- Preço da Ultima Compra
      else if (CTIPOCUSTO='U') then
      begin
        select first 1 ic.custoitcompra from cpitcompra ic, cpcompra cp
            where cp.codemp=:icodemp and cp.codfilial=:scodfilial and cp.dtentcompra<=:dtestoq
            and ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra
            and ic.codemppd=:icodemp and ic.codfilialpd=:scodfilial and ic.codprod=:icodprod
            and ( (:icodalmox is null) or ( ic.codempax=:icodempax and ic.codfilial=:scodfilialax and ic.codalmox=:icodalmox) )
            order by cp.dtentcompra desc
            into :CUSTOUNIT;

            if (CUSTOUNIT IS NULL) THEN
                CUSTOUNIT = :CUSTOMPM;
         -- execute procedure sgdebugsp 'eqcustoprosp','após última compra';

      end

  END
  SUSPEND;
end
^

/* Alter (EQGERARMAOSSP) */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialtm1 smallint;
declare variable codfilialtm smallint;
declare variable codemppd integer;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable qtdaprov numeric(15,5) = 0;
begin


    -- buscando centro de custo do usuário atual
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codemprm, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando filial da rma
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQRMA')
    into :codfilialrma;

    -- Buscando filial do lote
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQLOTE')
    into :codfilialle;

    -- Buscando filial do tipo de movimento
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQTIPOMOV')
    into :codfilialtm;

    -- buscando tipo de movimento para RMA
    select codfilialt8,codtipomov8 from sgprefere1
    where codemp=:codemprm and codfilial=:codfilialrm
    into :codfilialtm1,:codtipomov1;

    -- Buscado a situação pardrão para RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:codemprm and
    codfilial=(select icodfilial from sgretfilial(:codemprm,'SGPREFERE5'))
    into :statusrmager;

    if(:statusrmager is null) then
    begin
       statusrmager = 'PE';
    end

    -- Carregando quantidade aprovada...
    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    -- Buscando código novo código de RMA.
    select coalesce((max(codrma)+1),1) from
    eqrma where codemp=:codemprm and codfilial=:codfilialrma
    into :codrma;

    -- Inserindo nova RMA
    insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempos,codfilialos,ticket,coditrecmerc,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,:coditrecmerc,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditrecmerc, os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
--    and ( (os.coditrecmerc=:coditrecmerc) or (:coditrecmerc is null) )
    and os.gerarma='S'
    into :coditrecmerc, :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
    do

    begin

       select coalesce((max(coditrma)+1),1) from eqitrma
       where codemp=:codemprm and codfilial=:codfilialrma and codrma=:codrma
       into :coditrma;

       if(:statusrmager='AF') then
       begin
         STATUSAPROVRMAGER = 'AT';
         QTDAPROV = :qtd;
       end

       insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            CODEMPOS,CODFILIALOS,TICKET,CODITRECMERC,CODITOS
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE',
                            :codemprm, :codfilialrm, :ticket, :coditrecmerc,:coditos
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* empty dependent procedure body */
/* Clear: PPGERAOP for: EQGERARMASP */
/* AssignEmptyBody proc */
ALTER PROCEDURE PPGERAOP(TIPOPROCESS CHAR(1),
CODEMPOP INTEGER,
CODFILIALOP SMALLINT,
CODOP INTEGER,
SEQOP INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT,
QTDSUGPRODOP NUMERIC(15,5),
DTFABROP DATE,
SEQEST SMALLINT,
CODEMPET INTEGER,
CODFILIALET SMALLINT,
CODEST SMALLINT,
AGRUPDATAAPROV CHAR(1),
AGRUPDTFABROP CHAR(1),
AGRUPCODCLI CHAR(1),
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
DATAAPROV DATE,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
JUSTFICQTDPROD VARCHAR(500),
CODEMPPDENTRADA INTEGER,
CODFILIALPDENTRADA SMALLINT,
CODPRODENTRADA INTEGER,
QTDENTRADA NUMERIC(15,5))
 RETURNS(CODOPRET INTEGER,
SEQOPRET SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter (EQGERARMASP) */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
declare variable seqof smallint;
declare variable idusu1 char(8);
declare variable seqitop integer;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codrma integer;
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialpd smallint;
declare variable codfilialtm1 smallint;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
declare variable codfase integer;
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc integer;
declare variable coditos integer;
begin

    -- Buscando informações do usuário
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codempop, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando preferencias de tipo de movimento para OP
    select codfilialt8,codtipomov8
    from sgprefere1 where codemp=:CODEMPOP and codfilial=(select icodfilial from sgretfilial(:CODEMPOP, 'SGPREFERE1'))
    into :CODFILIALTM1,:CODTIPOMOV1;

    --Buscando informações da OP.
    select op.codempos, op.codfilialos, op.ticket, op.coditrecmerc, op.coditos
    from  ppop op
    where op.codemp=:codempop and op.codfilial=:codfilialop and op.codop=:codop and op.seqop=:seqop
    into :codempos, :codfilialos, :ticket, :coditrecmerc, :coditos;

    -- Buscando preferencias para geração de RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:CODEMPOP and
    codfilial=(select icodfilial from sgretfilial(:CODEMPOP,'SGPREFERE5'))
    into :STATUSRMAGER;

    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    for select codfilialfs,codfase,seqof from ppopfase opf
        where opf.codemp=:CODEMPOP and opf.codfilial=:CODFILIALOP and
        opf.codop=:CODOP and  opf.seqop=:SEQOP and
        (select count(1) from ppitop it
        where it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP and
        it.codempfs=opf.codempfs and it.codfilialfs=opf.codfilialfs and
        it.codfase=opf.codfase and it.gerarma='S' and
        it.codop=:CODOP and it.seqop=:SEQOP) > 0
        into :codfilialfase,:codfase,:SEQOF do
    begin
        select coalesce((max(codrma)+1),1)
        from eqrma
        where codemp=:CODEMPOP and codfilial=:CODFILIALOP into :CODRMA;

        insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempof,codfilialof,codop,seqop,seqof,
                     motivorma, codempos, codfilialos, ticket, coditrecmerc)
                     values
                     (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQRMA')),:CODRMA,
                      :CODEMPOP, :CODFILIALUSU1,:IDUSU1,
                      null,null,null,
                      null,null,null,
                      :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQTIPOMOV')),
                      :CODTIPOMOV1,
                      :CODEMPOP,:CODFILIALCCUSU1,:ANOCCUSU1,:CODCCUSU1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :CODEMPOP,:CODFILIALOP,:CODOP,:SEQOP,:SEQOF,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OP:'||:CODOP||' SEQ:'||:SEQOP||' - FASE:'||:CODFASE,
                      :codempos, :codfilialos, :ticket, :coditrecmerc
        );

        for select it.seqitop,it.codfilialpd,it.codprod,it.refprod,it.qtditop-coalesce(it.qtdcopiaitop,0),it.codfilialle,it.codlote,
            (select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)),
            (SELECT CODFILIALAX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod),
            (SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod)
            from ppitop it, eqproduto pd
            where
            pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
            and it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP
            and it.codop=:CODOP and it.seqop=:SEQOP and it.codempfs=:CODEMPOP
            and it.codfilialfs=:CODFILIALFASE
            and it.codfase = :CODFASE and it.gerarma='S'
            and (('S'=(select ratauto from sgprefere5 where codemp=it.codemp and codfilial=it.codfilial))
            and (it.qtditop-coalesce(it.qtdcopiaitop, 0))<=(SELECT L.SLDLOTE FROM EQLOTE L
                                                            WHERE L.CODEMP=it.codemple AND L.CODFILIAL=it.codfilialle AND
                                                            L.CODPROD=it.codprod AND L.CODLOTE=it.codlote)
            or pd.cloteprod = 'N'
                                                            )
            into :SEQITOP, :CODFILIALPD,:CODPROD,:REFPROD,:QTD,
            :CODFILIALLE,:CODLOTE,:CUSTOMPMIT,:CODFILIALAX,:CODALMOX  DO
        begin
            select coalesce((max(coditrma)+1),1) from eqitrma
            where codemp=:CODEMPOP and codfilial=:CODFILIALOP and
            codrma=:CODRMA into :coditrma;

            if(:STATUSRMAGER='AF')then
            begin
                STATUSAPROVRMAGER = 'AT';
                QTDAPROV = :QTD;
            end

            insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            codempos, codfilialos, ticket, coditrecmerc, coditos
                            )
                            values
                            (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQITRMA')),:CODRMA,
                            :coditrma,
                            :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQPRODUTO')),
                            :CODPROD,:REFPROD,:QTD,:QTDAPROV,0,:CUSTOMPMIT,:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQLOTE')),:CODLOTE,
                            :CODEMPOP,:CODFILIALAX,:CODALMOX,
                            :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                            :codempop, :codfilialos, :ticket, :coditrecmerc, :coditos
                            );

            update ppitop it set it.gerarma='N' where it.CODEMP=:CODEMPOP AND
                it.CODFILIAL=:CODFILIALOP AND
                it.codop=:CODOP and it.seqop=:SEQOP and it.seqitop=:seqitop;

        end

    end

    suspend;

end
^

/* Alter (EQMOVPRODCKTMSP) */
ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
begin
  /* Verifique se é para contar estoque */
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM WHERE TM.CODEMP=:ICODEMP AND
     TM.CODFILIAL = :SCODFILIAL AND TM.CODTIPOMOV = :ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;
  SOPERADOR = 0;
  if ((ESTOQTIPOMOVPD='S') and (CESTOQTIPOMOV='S')) then
  begin
     if (CESTIPOMOV='S') then
        SOPERADOR = -1;
     else
        SOPERADOR = 1;
  end
  suspend;
end
^

ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(CESTIPOMOV CHAR(1),
SOPERADOR SMALLINT)
 AS
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
begin
  /* Verifique se é para contar estoque */
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM WHERE TM.CODEMP=:ICODEMP AND
     TM.CODFILIAL = :SCODFILIAL AND TM.CODTIPOMOV = :ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;
  SOPERADOR = 0;
  if ((ESTOQTIPOMOVPD='S') and (CESTOQTIPOMOV='S')) then
  begin
     if (CESTIPOMOV='S') then
        SOPERADOR = -1;
     else
        SOPERADOR = 1;
  end
  suspend;
end
^

/* Alter (EQMOVPRODCSLDSP) */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
 AS
begin
  /* Procedure que retorna o cálculo de custo e saldo */
  NCUSTOMPM = 0;
  NSALDO = 0;
  SELECT CESTIPOMOV, SOPERADOR
     FROM EQMOVPRODCKTMSP( :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :ESTOQTIPOMOVPD)
     INTO :CTIPOMOVPROD, :SOPERADOR;
  if (SOPERADOR=0) then
  begin
     CESTOQMOVPROD = 'N';
     NSALDO = NSLDMOVPROD;
  end
  else
  begin  /* verifica se é para controlar estoque */
     CESTOQMOVPROD = 'S';
     NSALDO = NSLDMOVPROD + CAST ( (NQTDMOVPROD * SOPERADOR) AS NUMERIC(15, 5) );
  end
  if ( (NSALDO >= NSLDMOVPROD) AND (NSALDO > 0) ) then
  begin
    if ( (NSLDMOVPROD * NCUSTOMPMMOVPROD)  <= 0) then
       NCUSTOMPM = NPRECOMOVPROD;
    else
        NCUSTOMPM = ( cast(NSLDMOVPROD * NCUSTOMPMMOVPROD as numeric(15,5) ) +
        cast(NQTDMOVPROD * NPRECOMOVPROD as numeric(15,5)) ) / (NSLDMOVPROD + NQTDMOVPROD) ;
  end
  else
      NCUSTOMPM = NCUSTOMPMMOVPROD;

  suspend;
end
^

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 AS
DECLARE VARIABLE ICODEMPTM INTEGER;
DECLARE VARIABLE SCODFILIALTM SMALLINT;
DECLARE VARIABLE ICODTIPOMOV INTEGER;
DECLARE VARIABLE NQTDMOVPROD NUMERIC(15,5);
DECLARE VARIABLE NPRECOMOVPROD NUMERIC(15,5);
DECLARE VARIABLE ICODMOVPRODPRC INTEGER;
DECLARE VARIABLE CESTOQMOVPROD CHAR(1);
DECLARE VARIABLE ICODEMPAXPRC INTEGER;
DECLARE VARIABLE SCODFILIALAXPRC SMALLINT;
DECLARE VARIABLE ICODALMOXPRC INTEGER;
begin
  /* Procedure de processamento de estoque */
  FOR SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV ,
    MP.QTDMOVPROD, MP.PRECOMOVPROD , MP.CODMOVPROD,
    MP.CODEMPAX, MP.CODFILIALAX, MP.CODALMOX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMPPD AND MP.CODFILIALPD=:SCODFILIALPD AND
       MP.CODPROD=:ICODPROD AND MP.CODEMP=:ICODEMPPD AND MP.CODFILIAL=:SCODFILIAL AND
       ( (MP.DTMOVPROD = :DDTMOVPROD AND MP.CODMOVPROD > :ICODMOVPROD) OR
         (MP.DTMOVPROD>:DDTMOVPROD) ) AND  /* ALTEREI AQUI */
       ( (:DDTMOVPRODPRC IS NULL /* AND MP.CODMOVPROD!=:ICODMOVPROD */) OR
         (MP.DTMOVPROD = :DDTMOVPRODPRC AND MP.CODMOVPROD < :ICODMOVPROD) OR
         (MP.DTMOVPROD < :DDTMOVPRODPRC) )
    ORDER BY MP.DTMOVPROD, MP.CODMOVPROD
    INTO :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
     :NQTDMOVPROD, :NPRECOMOVPROD, :ICODMOVPRODPRC,
     :ICODEMPAXPRC, :SCODFILIALAXPRC, :ICODALMOXPRC DO
  BEGIN
      SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
        :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD;
      if (CMULTIALMOX='N') then /* Se não for multi almoxarifado*/
      begin
         NSLDMOVPRODAX = NSLDMOVPROD;
         NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
         UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else if (ICODEMPAX=ICODEMPAXPRC AND SCODFILIALAX=SCODFILIALAXPRC AND
          ICODALMOX=ICODALMOXPRC) then
          /* Se for multi almoxarifado e o código do almoxarifado for o mesmo*/
      begin
        SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
            :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
            INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else /* Se for multi almoxarifado não atualiza almoxarifado diferente */
      begin
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      NSLDPRC = NSLDMOVPROD;
      NCUSTOMPMPRC = NCUSTOMPMMOVPROD;
      NSLDPRCAX = NSLDMOVPRODAX;
      NCUSTOMPMPRCAX = NCUSTOMPMMOVPRODAX;
  END
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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT)
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
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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

ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR(1),
CTIPOMOVPROD CHAR(1),
SOPERADOR SMALLINT)
 AS
begin
  /* Procedure que retorna o cálculo de custo e saldo */
  NCUSTOMPM = 0;
  NSALDO = 0;
  SELECT CESTIPOMOV, SOPERADOR
     FROM EQMOVPRODCKTMSP( :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :ESTOQTIPOMOVPD)
     INTO :CTIPOMOVPROD, :SOPERADOR;
  if (SOPERADOR=0) then
  begin
     CESTOQMOVPROD = 'N';
     NSALDO = NSLDMOVPROD;
  end
  else
  begin  /* verifica se é para controlar estoque */
     CESTOQMOVPROD = 'S';
     NSALDO = NSLDMOVPROD + CAST ( (NQTDMOVPROD * SOPERADOR) AS NUMERIC(15, 5) );
  end
  if ( (NSALDO >= NSLDMOVPROD) AND (NSALDO > 0) ) then
  begin
    if ( (NSLDMOVPROD * NCUSTOMPMMOVPROD)  <= 0) then
       NCUSTOMPM = NPRECOMOVPROD;
    else
        NCUSTOMPM = ( cast(NSLDMOVPROD * NCUSTOMPMMOVPROD as numeric(15,5) ) +
        cast(NQTDMOVPROD * NPRECOMOVPROD as numeric(15,5)) ) / (NSLDMOVPROD + NQTDMOVPROD) ;
  end
  else
      NCUSTOMPM = NCUSTOMPMMOVPROD;

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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
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
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX, seqsubprod )
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
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :seqsubprod );

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

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

SET TERM ^ ;

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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC, :ESTOQTIPOMOVPD)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX, :ESTOQTIPOMOVPD)
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
              :NCUSTOMPMLC, :NSLDLC, :ESTOQTIPOMOVPD)
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
                  :NCUSTOMPMLCAX, :NSLDLCAX, :ESTOQTIPOMOVPD)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

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
CMULTIALMOX CHAR(1),
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
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
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX, seqsubprod )
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
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :seqsubprod );

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
ICODALMOX INTEGER,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR(1))
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
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod, :estoqtipomovpd);
  else if (CIUD='U') then
     execute procedure EQMOVPRODUSP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX,:seqsubprod, :estoqtipomovpd);
  else if (CIUD='D') then
     execute procedure EQMOVPRODDSP( ICODEMPPD, SCODFILIALPD, ICODPROD, ICODEMPIV,
         SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA, SCODITCOMPRA,
         ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         DDTMOVPROD, ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod );
--  suspend;
end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial de subproduto'
where Rdb$Procedure_Name='EQMOVPRODIUDSP' and Rdb$Parameter_Name='SEQSUBPROD';

/* Alter (EQMOVPRODPRCSLDSP) */
SET TERM ^ ;

ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR(1))
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR(1))
 AS
DECLARE VARIABLE ICODEMPTM INTEGER;
DECLARE VARIABLE SCODFILIALTM SMALLINT;
DECLARE VARIABLE ICODTIPOMOV INTEGER;
DECLARE VARIABLE NQTDMOVPROD NUMERIC(15,5);
DECLARE VARIABLE NPRECOMOVPROD NUMERIC(15,5);
DECLARE VARIABLE ICODMOVPRODPRC INTEGER;
DECLARE VARIABLE CESTOQMOVPROD CHAR(1);
DECLARE VARIABLE ICODEMPAXPRC INTEGER;
DECLARE VARIABLE SCODFILIALAXPRC SMALLINT;
DECLARE VARIABLE ICODALMOXPRC INTEGER;
begin
  /* Procedure de processamento de estoque */
  FOR SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV ,
    MP.QTDMOVPROD, MP.PRECOMOVPROD , MP.CODMOVPROD,
    MP.CODEMPAX, MP.CODFILIALAX, MP.CODALMOX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMPPD AND MP.CODFILIALPD=:SCODFILIALPD AND
       MP.CODPROD=:ICODPROD AND MP.CODEMP=:ICODEMPPD AND MP.CODFILIAL=:SCODFILIAL AND
       ( (MP.DTMOVPROD = :DDTMOVPROD AND MP.CODMOVPROD > :ICODMOVPROD) OR
         (MP.DTMOVPROD>:DDTMOVPROD) ) AND  /* ALTEREI AQUI */
       ( (:DDTMOVPRODPRC IS NULL /* AND MP.CODMOVPROD!=:ICODMOVPROD */) OR
         (MP.DTMOVPROD = :DDTMOVPRODPRC AND MP.CODMOVPROD < :ICODMOVPROD) OR
         (MP.DTMOVPROD < :DDTMOVPRODPRC) )
    ORDER BY MP.DTMOVPROD, MP.CODMOVPROD
    INTO :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
     :NQTDMOVPROD, :NPRECOMOVPROD, :ICODMOVPRODPRC,
     :ICODEMPAXPRC, :SCODFILIALAXPRC, :ICODALMOXPRC DO
  BEGIN
      SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
        :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD;
      if (CMULTIALMOX='N') then /* Se não for multi almoxarifado*/
      begin
         NSLDMOVPRODAX = NSLDMOVPROD;
         NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
         UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else if (ICODEMPAX=ICODEMPAXPRC AND SCODFILIALAX=SCODFILIALAXPRC AND
          ICODALMOX=ICODALMOXPRC) then
          /* Se for multi almoxarifado e o código do almoxarifado for o mesmo*/
      begin
        SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
            :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD)
            INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else /* Se for multi almoxarifado não atualiza almoxarifado diferente */
      begin
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      NSLDPRC = NSLDMOVPROD;
      NCUSTOMPMPRC = NCUSTOMPMMOVPROD;
      NSLDPRCAX = NSLDMOVPRODAX;
      NCUSTOMPMPRCAX = NCUSTOMPMMOVPRODAX;
  END
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
CMULTIALMOX CHAR(1),
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR(1))
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
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC, :ESTOQTIPOMOVPD)
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
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX, :ESTOQTIPOMOVPD)
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
              :NCUSTOMPMLC, :NSLDLC, :ESTOQTIPOMOVPD)
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
                  :NCUSTOMPMLCAX, :NSLDLCAX, :ESTOQTIPOMOVPD)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

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

/* Alter (EQPRODUTOSP01) */
ALTER PROCEDURE EQPRODUTOSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NSALDO NUMERIC(15,5),
NSALDOAX NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
NCUSTOPEPS NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
NCUSTOPEPSAX NUMERIC(15,5),
NCUSTOINFO NUMERIC(15,5),
NCUSTOUC NUMERIC(15,5))
 AS
declare variable ddtmovprod date;
declare variable ddtmovprodax date;
begin

    /* Procedure que retorna saldos e custos para a tela de cadastro de produtos */
    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPROD , MP.CUSTOMPMMOVPROD
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPROD, :NSALDO, :NCUSTOMPM;

    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPRODAX, MP.CUSTOMPMMOVPRODAX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPRODAX, :NSALDOAX, :NCUSTOMPMAX;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPROD, null, null, null ) P
    INTO :NCUSTOPEPS;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPRODAX, :ICODEMPAX, :SCODFILIALAX,
    :ICODALMOX ) P
    INTO :NCUSTOPEPSAX;

    select p.custoinfoprod from eqproduto p
    where p.codemp=:icodemp and p.codfilial=:scodfilial and p.codprod=:icodprod
    into :ncustoinfo;

    select custounit from eqcustoprodsp(:icodemp, :scodfilial, :icodprod,
    cast('today' as date),'U',:icodempax, :scodfilialax, :icodalmox, 'N' )
    into :ncustouc;

    if(:ncustompm is null) then
    begin
        ncustompm = :ncustoinfo;
    end

    if(:ncustopeps is null) then
    begin
        ncustopeps = :ncustoinfo;
    end

    suspend;

end
^

/* Alter (EQRELPEPSSP) */
ALTER PROCEDURE EQRELPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP VARCHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR(1),
CODUNID CHAR(20),
TIPOPROD VARCHAR(2),
CODNCM VARCHAR(10),
EXTIPI CHAR(2),
COD_GEN CHAR(2),
CODSERV CHAR(5),
ALIQ_ICMS NUMERIC(9,2),
CODNBM VARCHAR(12))
 AS
begin

  /* procedure text */

  if (icodempgp is not null) then
  begin
    if (strlen(rtrim(ccodgrup))<14) then
       ccodgrup = rtrim(ccodgrup)||'%';
  end

  if (ctipocusto is null) then
     ctipocusto = 'P';

  for select p.codprod,p.refprod,p.descprod, p.codbarprod
     , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
     , fc.codncm, fc.extipi
     , substring(fc.codncm from 1 for 2) cod_gen
     , fc.codserv,
      (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
      ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
     , fc.codnbm

   from eqproduto p
   left outer join lfclfiscal fc
     on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
   where p.codemp = :icodemp and p.codfilial = :scodfilial and
   ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
      p.codmarca=:ccodmarca) ) and
   ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
      p.codgrup like :ccodgrup) )
      order by p.codprod

   into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
   , :ativoprod, :codunid, :tipoprod
   , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm  do

  begin

     select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
        :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
        :scodfilialax, :icodalmox, 'S')
       into :sldprod, :custounit, :custotot;
     suspend;

  end

end
^

/* Alter (FNADICLANCASP01) */
ALTER PROCEDURE FNADICLANCASP01(ICODREC INTEGER,
INPARCITREC INTEGER,
PDVITREC CHAR(1),
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITREC DATE,
DDTPAGOITREC DATE,
SDOCLANCAITREC VARCHAR(15),
SOBSITREC CHAR(250),
DVLRPAGOITREC NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRPAGOJUROS NUMERIC(15,5),
DVLRDESC NUMERIC(15,5))
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjr integer;
declare variable codfilialjr smallint;
declare variable codplanjr char(13);
declare variable codempdc integer;
declare variable codfilialdc smallint;
declare variable codplandc char(13);
declare variable codsublanca smallint = 1;
BEGIN
    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA') INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMPPN,CODFILIALPN FROM FNCONTA WHERE NUMCONTA = :SNUMCONTA
        AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALLANCA,'LA') INTO ICODLANCA;

    SELECT FLAG FROM FNRECEBER WHERE CODREC=:ICODREC
        AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO :cFlag;

    SELECT CODEMPJR,CODFILIALJR,CODPLANJR,CODEMPDC,CODFILIALDC,CODPLANDC FROM SGPREFERE1
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
        INTO :CODEMPJR,:CODFILIALJR,:CODPLANJR,:CODEMPDC,:CODFILIALDC,:CODPLANDC;


    IF (ICODCLI IS NULL) THEN
        TIPOLANCA = 'A';
    ELSE
        TIPOLANCA = 'C';

    if ( (DVLRPAGOJUROS IS NULL) OR (:CODPLANJR IS NULL)  ) then
        DVLRPAGOJUROS = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC - DVLRPAGOJUROS;

    if (DVLRDESC IS NULL  OR (:CODPLANDC IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC + DVLRDESC;


    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPRC,CODFILIALRC,CODREC,NPARCITREC,CODEMPPN,CODFILIALPN,CODPLAN, 
        DTCOMPLANCA, DATALANCA, DOCLANCA,HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG,CODEMPCL,CODFILIALCL,CODCLI,PDVITREC)
        VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:ICODREC,:iNParcItRec,:iCodEmpPConta,:iCodFilialPConta,
                :sCodPlanConta,:dDtCompItRec, :dDtPagoItRec,:sDocLancaItRec,:sObsItRec,:dDtPagoItRec,0,:cFlag,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:PDVITREC
        );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA, DTCOMPSUBLANCA, DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:ICODEMPPN,:ICODFILIALPN,:SCODPLAN,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:dVlrPagoItRec*-1,:cFlag
        );

    -- Lançamento dos juros em conta distinta.

    IF(DVLRPAGOJUROS > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                 CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                  CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPJR,:CODFILIALJR,:CODPLANJR,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRPAGOJUROS*-1,:cFlag, 'J'
        );

    END

    -- Lançamento do desconto em conta distinta.

    IF(DVLRDESC > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
             CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
             CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPDC,:CODFILIALDC,:CODPLANDC,
               :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
             :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRDESC,:cFlag, 'D'
        );

    END


END
^

/* Alter (FNADICLANCASP02) */
ALTER PROCEDURE FNADICLANCASP02(ICODPAG INTEGER,
INPARCPAG INTEGER,
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODFOR INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITPAG DATE,
DDTPAGOITPAG DATE,
SDOCLANCAITPAG VARCHAR(15),
SOBSITPAG CHAR(250),
DVLRPAGOITPAG NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRJUROSPAG NUMERIC(15,5),
DVLRDESC NUMERIC(15,5))
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjp integer;
declare variable codfilialjp smallint;
declare variable codplanjp char(13);
declare variable codempdr integer;
declare variable codfilialdr smallint;
declare variable codplandr char(13);
declare variable codsublanca smallint = 1;
BEGIN

    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA')
    INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMP,CODFILIAL FROM FNCONTA
    WHERE NUMCONTA=:sNumConta AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA
    INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:iCODEMP,:IFILIALLANCA,'LA')
    INTO :iCodLanca;

    SELECT FLAG FROM FNPAGAR
    WHERE CODPAG=:iCodPag AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :cFlag;

    SELECT CODEMPJP,CODFILIALJP,CODPLANJP,CODEMPDR,CODFILIALDR,CODPLANDR
    FROM SGPREFERE1
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :CODEMPJP,:CODFILIALJP,:CODPLANJP,:CODEMPDR,:CODFILIALDR,:CODPLANDR;

    IF (ICODFOR IS NULL) THEN
        TIPOLANCA = 'A';
      ELSE
        TIPOLANCA = 'F';

    if ( (DVLRJUROSPAG IS NULL) OR (:CODPLANJP IS NULL)  ) then
        DVLRJUROSPAG = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG - DVLRJUROSPAG;

    if (DVLRDESC IS NULL  OR (:CODPLANDR IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG + DVLRDESC;

    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPPG,CODFILIALPG,CODPAG,
                         NPARCPAG,CODEMPPN,CODFILIALPN,CODPLAN,DTCOMPLANCA,DATALANCA,DOCLANCA,
                         HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG, CODEMPFR, CODFILIALFR, CODFOR)
         VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:iCodPag,
                 :iNParcPag,:iCodEmpPConta,:iCodFilialPConta,:sCodPlanConta, :dDtCompItPag, :dDtPagoItPag,:sDocLancaItPag,
                 :sObsItPag,:dDtPagoItPag,0,:cFlag, :ICODEMPFR, :ICODFILIALFR, :ICODFOR
         );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN, CODPLAN,
        CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG,
        CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:ICODEMPPN,:ICODFILIALPN, :sCodplan,
        :ICODEMP,:ICODFILIAL,:iCodPag, :iNParcPag,
        :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:dVlrPagoItPag,:cFlag);

    -- Lançamento dos juros em conta distinta.

    IF(DVLRJUROSPAG >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
             CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
             CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
            :CODEMPJP,:CODFILIALJP,:CODPLANJP,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRJUROSPAG,
            :ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag,:cFlag, 'J');

    END

    -- Lançamento de desconto em conta distinta.

    IF(DVLRDESC >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
        CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
              CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
             :CODEMPDR,:CODFILIALDR,:CODPLANDR,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRDESC*-1,
             :ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag, :cFlag, 'D');

    END

END
^

/* Alter (FNCHECAPGTOATRASOSP) */
ALTER PROCEDURE FNCHECAPGTOATRASOSP(ICODCLI INTEGER,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(SRETORNO CHAR(1))
 AS
declare variable ilinhas integer;
declare variable ifilialreceber integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNRECEBER') INTO IFILIALRECEBER;
  SELECT COUNT(*) FROM FNITRECEBER IT WHERE CODREC
         IN (
            SELECT CODREC FROM FNRECEBER REC WHERE REC.CODCLI = :iCodCli
                   AND REC.CODEMPCL=:ICODEMP AND REC.CODFILIALCL=:ICODFILIAL
                   AND REC.CODREC = IT.CODREC
                   AND CODEMP=IT.CODEMP AND CODFILIAL=IT.CODFILIAL
         )
         AND STATUSITREC in ('R1') AND IT.dtvencitrec < cast('today' as date)
         AND IT.CODEMP=:ICODEMP AND IT.CODFILIAL=:IFILIALRECEBER INTO iLinhas;
  IF (iLinhas > 0) THEN
    sRetorno = 'S';
  ELSE
    sRetorno = 'N';
  SUSPEND;
END
^

/* Alter (LFBUSCAFISCALSP) */
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
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR(1),
CALCSTCM CHAR(1),
ALIQICMSSTCM NUMERIC(9,2))
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
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
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
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
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
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
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
declare variable tipoprod varchar(2);
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
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(cf.aliqissfisc, fi.percissfilial),
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

/* Alter (LFCALCCUSTOSP01) */
ALTER PROCEDURE LFCALCCUSTOSP01(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCALC INTEGER,
QTDADE DECIMAL(15,5),
VLRCUSTOP DECIMAL(15,5),
VLRICMS DECIMAL(15,5),
VLRIPI DECIMAL(15,5),
VLRPIS DECIMAL(15,5),
VLRCOFINS DECIMAL(15,5),
VLRISS DECIMAL(15,5),
VLRFUNRURAL DECIMAL(15,5),
VLRII DECIMAL(15,5),
VLRIR DECIMAL(15,5),
VLRTXSISCOMEX DECIMAL(15,5),
VLRICMSDIFERIDO DECIMAL(15,5),
VLRICMSPRESUMIDO DECIMAL(15,5),
VLRCOMPL DECIMAL(15,5))
 RETURNS(VLRCUSTO DECIMAL(15,5))
 AS
declare variable siglacalc varchar(10);
declare variable operacao char(1);
declare variable vlrimposto decimal(15,5);
begin
  if (:vlrcustop is null) then
    vlrcusto = 0;
  else
    vlrcusto = vlrcustop;

  if (vlripi is null) then
   vlripi = 0;
  if (vlricms is null) then
   vlricms = 0;
  if (vlrpis is null) then
   vlrpis = 0;
  if (vlrcofins is null) then
   vlrcofins = 0;
  if (vlriss is null) then
   vlriss = 0;
  if (vlrfunrural is null) then
   vlrfunrural = 0;
  if (vlrii is null) then
   vlrii = 0;
  if (vlrir is null) then
   vlrir = 0;
  if (vlrtxsiscomex is null) then
   vlrtxsiscomex = 0;
  if (vlricmsdiferido is null) then
   vlricmsdiferido = 0;
  if (vlricmspresumido is null) then
   vlricmspresumido = 0;
  if (vlrcompl is null) then
   vlrcompl = 0; 

  for select ic.siglacalc, ic.operacaocalc
     from lfcalccusto c, lfitcalccusto ic
     where c.codemp=:codemp and c.codfilial=:codfilial and c.codcalc=:codcalc
       and ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.codcalc=c.codcalc
       into :siglacalc, :operacao do
  begin

     if (:siglacalc='IPI') then
       vlrimposto = vlripi;
     else if (:siglacalc='ICMS') then
       vlrimposto = vlricms;
     else if (:siglacalc='PIS') then
       vlrimposto = vlrpis;
     else if (:siglacalc='COFINS') then
       vlrimposto = vlrcofins;
     else if (:siglacalc='ISS') then
       vlrimposto = vlriss;
     else if (:siglacalc='FUNRURAL') then
       vlrimposto = vlrfunrural;
     else if (:siglacalc='II') then
       vlrimposto = vlrii;
     else if (:siglacalc='IR') then
       vlrimposto = vlrir;
     else if (:siglacalc='TXSISCOMEX') then
       vlrimposto = vlrtxsiscomex;
     else if (:siglacalc='ICMSDIF') then
       vlrimposto = vlricmsdiferido;
     else if (:siglacalc='ICMSPRES') then
       vlrimposto = vlricmspresumido;
     else if (:siglacalc='COMPL') then
       vlrimposto = vlrcompl;

     if (:operacao='+') then
        vlrcusto = vlrcusto + vlrimposto;
     else if (:operacao='-') then
        vlrcusto = vlrcusto - vlrimposto;
  end
  if (qtdade is not null and qtdade<>0) then
  begin
     -- Dividimos o total pela quantidade, multiplicamos o resultado novamente pela quantidade e dividimos pela quantidade, objetivando evitar dizima periódica.
     
      vlrcusto = cast( ( cast(vlrcusto as decimal(15,4)) / cast( qtdade as decimal(15,5) )  * cast( qtdade as decimal(15,5) ) ) as decimal(15,4) ) / cast( qtdade as decimal(15,5) ) ;
  end
  suspend;
end
^

/* Alter (PPCUSTOPRODSP) */
ALTER PROCEDURE PPCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(CUSTOUNIT NUMERIC(15,5),
SLDPROD NUMERIC(15,5))
 AS
declare variable qtditest numeric(15,5);
declare variable seqest smallint;
declare variable tipoprod varchar(2);
declare variable custotot numeric(15,5);
declare variable codprodpd integer;
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  SELECT P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
      P.CODPROD=:ICODPROD
   INTO :TIPOPROD;

  IF (TIPOPROD='F') THEN
  BEGIN
     SELECT SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :SLDPROD;

     CUSTOTOT = 0;

     SELECT FIRST 1 E.SEQEST FROM PPESTRUTURA E
       WHERE E.CODEMP=:ICODEMP AND E.CODFILIAL=:SCODFILIAL AND E.CODPROD=:ICODPROD
       ORDER BY E.SEQEST INTO :SEQEST;

     FOR SELECT I.CODPRODPD,I.QTDITEST FROM  PPITESTRUTURA I
        WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:SCODFILIAL AND
          I.CODPROD=:ICODPROD AND I.SEQEST=:SEQEST
        INTO :CODPRODPD, :QTDITEST DO
     BEGIN
         SELECT CUSTOUNIT FROM PPCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :CODPRODPD, :DTESTOQ,
            :CTIPOCUSTO, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CCOMSALDO)
         INTO :CUSTOUNIT;
         CUSTOTOT = CUSTOTOT + ( CUSTOUNIT * QTDITEST)  ;
     END
  END
  ELSE
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :CUSTOTOT, :SLDPROD;
  END
  CUSTOUNIT = CUSTOTOT;
  SUSPEND;
end
^

/* Alter (PPGERAOP) */
ALTER PROCEDURE PPGERAOP(TIPOPROCESS CHAR(1),
CODEMPOP INTEGER,
CODFILIALOP SMALLINT,
CODOP INTEGER,
SEQOP INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT,
QTDSUGPRODOP NUMERIC(15,5),
DTFABROP DATE,
SEQEST SMALLINT,
CODEMPET INTEGER,
CODFILIALET SMALLINT,
CODEST SMALLINT,
AGRUPDATAAPROV CHAR(1),
AGRUPDTFABROP CHAR(1),
AGRUPCODCLI CHAR(1),
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
DATAAPROV DATE,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
JUSTFICQTDPROD VARCHAR(500),
CODEMPPDENTRADA INTEGER,
CODFILIALPDENTRADA SMALLINT,
CODPRODENTRADA INTEGER,
QTDENTRADA NUMERIC(15,5))
 RETURNS(CODOPRET INTEGER,
SEQOPRET SMALLINT)
 AS
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable refprod varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable sitpadop char(2);
declare variable seqof smallint;
declare variable codempfs integer;
declare variable codfilialfs smallint;
declare variable codfase integer;
declare variable tempoefdias numeric(15,5);
declare variable tempoef numeric(15,5);
declare variable datafimprodant date;
declare variable hfimprodant time;
declare variable qtdfinalprodop numeric(15,5);
declare variable codtipomovtm integer;
declare variable sitpadopconv char(2);
declare variable codemprma integer;
declare variable codfilialrma smallint;
declare variable codrma integer;
declare variable coditrma smallint;
declare variable estdinamica char(1);
begin

    if(codop is null) then
    begin

        -- Busca novo código para a OP caso não venha no parâmetro.
        select coalesce(max(codop),0) + 1 from ppop where codemp=:codempop and codfilial=:codfilialop
        into :codop;

        -- Buscando informações do produto e estrutura.

        select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod, es.estdinamica from eqproduto pd, ppestrutura es
        where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
        and es.codemp=pd.codemp and es.codfilial=pd.codfilial and es.codprod=pd.codprod and es.seqest=:seqest
        into :codempax, :codfilialax, :codalmox, :refprod, :estdinamica;

        -- Buscando tipo de movimento para OP.
        select p5.codemptm, p5.codfilialtm, p5.codtipomov, coalesce(tm.codtipomovtm,p5.codtipomov), p5.sitpadop, p5.sitpadopconv
        from sgprefere5 p5, eqtipomov tm
        where p5.codemp=:codempop and p5.codfilial=:codfilialop and
        tm.codemp=p5.codemptm and tm.codfilial=p5.codfilialtm and tm.codtipomov=p5.codtipomov
        into :codemptm, :codfilialtm, :codtipomov, :codtipomovtm, :sitpadop, :sitpadopconv;

        -- Inserindo OP
        seqop = 0;

        if(sitpadop='FN' and tipoprocess in ('D','A')) then
        begin
            qtdfinalprodop = :qtdsugprodop;
            codtipomov = :codtipomovtm;
        end
        else if(sitpadopconv='FN' and tipoprocess='C') then
        begin
            qtdfinalprodop = :qtdsugprodop;
            codtipomov = :codtipomovtm;
        end
        else
        begin
            qtdfinalprodop = 0;
        end

        insert into ppop  (codemp, codfilial, codop, seqop,
                           codemppd, codfilialpd, codprod, seqest, refprod,
                           codempax, codfilialax, codalmox,
                           dtemitop, dtfabrop,
                           qtdsugprodop, qtdprevprodop, qtdfinalprodop,
                           codemple, codfilialle, codlote,
                           codemptm, codfilialtm, codtipomov,
                           sitop, codempcp, codfilialcp, codcompra, coditcompra, justficqtdprod, estdinamica)
        values ( :codempop, :codfilialop, :codop, :seqop,
                 :codemppd, :codfilialpd, :codprod, :seqest, :refprod,
                 :codempax, :codfilialax, :codalmox,
                 cast('today' as date), :dtfabrop,
                 :qtdsugprodop, :qtdsugprodop, :qtdfinalprodop, null,null,null, 
                 :codemptm, :codfilialtm, :codtipomov, :sitpadop,
                 :codempcp, :codfilialcp, :codcompra, :coditcompra, :justficqtdprod, :estdinamica

        );

        -- Caso o status padrão da OP seja Finalizado
        if(:sitpadop='FN') then
        begin
            -- Inicializando variaveis
            datafimprodant = :dtfabrop;
            hfimprodant = cast('now' as time);

            -- Gerando RMAS

            execute procedure eqgerarmasp(:codempop,:codfilialop,:codop,:seqop);

            -- Finalizando Fases

            for select oe.codempfs, oe.codfilialfs, oe.codfase, oe.seqof
            from ppopfase oe, ppop op
            where oe.codemp=:codempop and oe.codfilial=:codfilialop and oe.codop=:codop and oe.seqop=:seqop and
            op.codemp=oe.codemp and op.codfilial=oe.codfilial and op.codop=oe.codop and op.seqop=oe.seqop
            order by oe.seqof
            into :codempfs, :codfilialfs, :codfase, :seqof do
            begin
                -- Buscando informações da fase
                select ef.tempoef from ppestrufase ef
                where ef.codemp=:codempfs and ef.codfilial=:codfilialfs and ef.codfase=:codfase and
                ef.codemp=:codemppd and ef.codfilial=:codfilialpd and ef.codprod=:codprod and ef.seqest=:seqest
                into :tempoef;

                tempoefdias = (tempoef/3600) / 24; -- de segundos para dias...

                update ppopfase oe set oe.sitfs=:sitpadop, oe.obsfs='Fase finalizada automaticamente',
                oe.datainiprodfs=:datafimprodant, oe.hiniprodfs=:hfimprodant,
                oe.datafimprodfs=:datafimprodant + :tempoefdias, oe.hfimprodfs=:hfimprodant + :tempoef
                where oe.codemp=:codempop and oe.codfilial=:codfilialop and oe.codop=:codop and oe.seqop=:seqop and
                oe.seqof=:seqof;

                -- Carregando variáveis para proximo registro
                datafimprodant = :datafimprodant + :tempoefdias;
                hfimprodant = :hfimprodant + :tempoef;

            end
        end

    end

    -- Caso o tipo de processamento seja Detalhado (uma OP por orçamento)
    if(tipoprocess='D') then
    begin

        -- Caso o código do orçamento e código da OP tenham sido informados (deve ocorrer no modo orçamento ou a partir da segunda passagem do modo agrupado...
        if( (codorc is not null) and (codop is not null) ) then
        begin
            insert into ppopitorc (codemp, codfilial, codop, seqop, codempoc, codfilialoc, tipoorc, codorc, coditorc, qtdprod)
            values(:codempop, :codfilialop, :codop, :seqop, :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc, :qtdsugprodop);

            -- Caso a OP seja gerada já finalizada... deve mudar o status do item de orçamento para produzido.
            if(sitpadop='FN') then
            begin
                update vditorcamento io set io.sitproditorc='PD'
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.coditorc=:coditorc and io.tipoorc=:tipoorc;
            end

        end
    end
    -- Caso o tipo de processamento seja Agrupado (uma OP para vários orçamentos)
    else if(tipoprocess='A') then
    begin

        for select pt.codemp,pt.codfilial, pt.codorc, pt.coditorc, pt.tipoorc, pt.dtfabrop, pt.qtdaprod
        from ppprocessaoptmp pt, vditorcamento io, vdorcamento oc
        where pt.codempet=:codempet and pt.codfilialet=:codfilialet and pt.codest=:codest
        and io.codemp=pt.codemp and io.codfilial=pt.codfilial and io.codorc=pt.codorc and io.coditorc=pt.coditorc and io.tipoorc=pt.tipoorc
        and oc.codemp=io.codemp and oc.codfilial=io.codfilial and oc.codorc=io.codorc and oc.tipoorc=io.tipoorc
        and (:agrupdataaprov='N' or io.dtaprovitorc=:dataaprov )
        and (:agrupdtfabrop='N' or pt.dtfabrop=:dtfabrop )
        and (:agrupcodcli='N' or (oc.codorc=:codcli and oc.codempcl=:codempcl and oc.codfilialcl=:codfilialcl) )
        and io.codemppd=:codemppd and io.codfilialpd=:codfilialpd and io.codprod=:codprod
        into :codempoc, :codfilialoc, :codorc, :coditorc, :tipoorc, :dtfabrop, :qtdsugprodop do
        begin

            insert into ppopitorc (codemp, codfilial, codop, seqop, codempoc, codfilialoc, tipoorc, codorc, coditorc, qtdprod)
            values(:codempop, :codfilialop, :codop, :seqop, :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc, :qtdsugprodop);

            -- Caso a OP seja gerada já finalizada... deve mudar o status do item de orçamento para produzido.
            if(sitpadop='FN') then
            begin
                update vditorcamento io set io.sitproditorc='PD'
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.coditorc=:coditorc and io.tipoorc=:tipoorc;
            end

        end
    end

    -- Carregando parametros de saída
    codopret = :codop;
    seqopret = :seqop;

    suspend;

end
^

/* Alter (PPITOPSP01) */
ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable gerarma char(1);
declare variable crefprod varchar(20);
declare variable icodprodpd integer;
declare variable nqtditop numeric(15,5);
declare variable icodemple integer;
declare variable icodfilialle smallint;
declare variable ccodlote varchar(20);
declare variable icodempfs integer;
declare variable icodfilialfs smallint;
declare variable icodfase integer;
declare variable icodemptr integer;
declare variable icodfilialtr smallint;
declare variable icodtprec integer;
declare variable icodemprp integer;
declare variable icodfilialrp smallint;
declare variable icodrecp integer;
declare variable dtempoof numeric(15,5);
declare variable iseqof smallint;
declare variable iseqppitop integer;
declare variable qtditest numeric(15,5);
declare variable qtdest numeric(15,5);
declare variable qtdprevprodop numeric(15,5);
declare variable qtdfixa char(1);
declare variable estdinamica char(1);
declare variable permiteajusteitop char(1);
declare variable iseqsubprod integer;
declare variable qtditestsp numeric(15,5);
declare variable codempts integer;
declare variable codfilialts smallint;
declare variable codtipomovsp integer;
declare variable tipoexterno char(10);
begin

    --Loop nas fases da estrutura para geração da tabela de fases da OP.
    for select ef.seqef, ef.codempfs, ef.codfilialfs, ef.codfase, ef.codemptr, ef.codfilialtr, ef.codtprec, ef.tempoef, o.estdinamica
    from ppestrufase ef, ppop o, ppestrutura e
    where
        o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
        e.codemp=o.codemppd and e.codfilial=o.codfilialpd and e.codprod=o.codprod and e.seqest=o.seqest and
        ef.codemp=e.codemp and ef.codfilial=e.codfilial and ef.codprod=e.codprod and ef.seqest=E.seqest
    into
        :iseqof, :icodempfs, :icodfilialfs, :icodfase, :icodemptr, :icodfilialtr, :icodtprec, :dtempoof, :estdinamica
    do
    begin
        -- Buscando o primeiro recurso para inserção na fase (provisório)
        select first 1 codemp, codfilial, codrecp from pprecurso r
        where r.codemp=:icodemptr and r.codfilial=:icodfilialtr and r.codtprec=:icodtprec
        into :icodemprp, :icodfilialrp, :icodrecp;

        -- Inserindo na tabela de fase por op
        insert into
            ppopfase (
                codemp, codfilial, codop, seqop, seqof, codempfs, codfilialfs, codfase, codemprp, codfilialrp, codrecp, tempoof,
                codemptr, codfilialtr, codtprec
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqof, :icodempfs, :icodfilialfs,:icodfase, :icodemprp, :icodfilialrp,
                :icodrecp,:dtempoof, :icodemptr, :icodfilialtr, :icodtprec
            );
    end

    -- Se a estrutura não for dinâmica, deve inserir os ítens

    if(coalesce(:estdinamica,'N')='N'  ) then    
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, ie.permiteajusteitest, ie.tipoexterno
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote,:permiteajusteitop,:tipoexterno
        do
        begin
            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqppitop = :iseqppitop + 1;

            if ('S'=qtdfixa) then
            begin
                nqtditop = :qtditest;
            end
            else
            begin
                nqtditop = cast(:qtditest/:qtdest as numeric(15,5) ) * cast(:qtdprevprodop as numeric(15, 5));
            end

            insert into ppitop (
                codemp, codfilial, codop, seqop, seqitop, codemppd, codfilialpd, codprod, refprod,
                codempfs, codfilialfs, codfase, qtditop, gerarma, permiteajusteitop, tipoexterno
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma,
                :permiteajusteitop, :tipoexterno
            );

        end

        -- Inserindo tabela de subprodutos

        iseqsubprod = 0;

        -- Buscando tipo de movimento para subproducao
        select codempts, codfilialts, codtipomovsp from sgprefere5 where codemp=:icodemp and codfilial=:icodfilial
        into :codempts, :codfilialts, :codtipomovsp;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitestsp, ie.codprodpd, ie.refprodpd, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditestsp, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, fs.seqof
            from
                ppitestruturasubprod ie, ppestrutura e, ppop o, ppopfase fs
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
                fs.codemp=ie.codempfs and fs.codfilial=ie.codfilialfs and fs.codfase=ie.codfase and fs.codop=o.codop and fs.seqop=o.seqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqsubprod, :icodprodpd, :crefprod, :icodempfs, :icodfilialfs, :icodfase,
        :qtditestsp,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote, :iseqof
        do
        begin

            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqsubprod = :iseqsubprod + 1;

           insert into ppopsubprod (codemp, codfilial, codop, seqop, seqsubprod, codemppd, codfilialpd, codprod,
                refprod, qtditsp, codempfs, codfilialfs, codfase, codemple, codfilialle, codlote, seqof, codemptm, codfilialtm, codtipomov
           )
           values(
                :icodemp, :icodfilial,:icodop,:iseqop, :iseqsubprod, :icodemppd, :icodfilialpd, :icodprodpd,
                :crefprod, :qtditestsp, :icodempfs, :icodfilialfs, :icodfase, :icodemple, :icodfilialle, :ccodlote, :iseqof, :codempts, :codfilialts, :codtipomovsp
           );



        end


    end

end
^

/* Alter (PPITOPSP02) */
ALTER PROCEDURE PPITOPSP02(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
DECLARE VARIABLE NQTDITOP NUMERIC(15,5);
DECLARE VARIABLE ICODPRODPD INTEGER;
DECLARE VARIABLE ISEQITOP SMALLINT;
DECLARE VARIABLE ICODEMPPD INTEGER;
DECLARE VARIABLE ISEQPPITOP SMALLINT;
BEGIN
    FOR SELECT It.seqitop,
        CAST( IE.QTDITEST / E.QTDEST AS NUMERIC(15,5) ) * CAST(O.QTDFINALPRODOP AS NUMERIC(15, 5))
        FROM PPITESTRUTURA IE, PPESTRUTURA E, PPOP O, PPITOP IT
            WHERE IE.CODEMP=E.CODEMP AND IE.CODFILIAL=E.CODFILIAL AND
                IE.CODPROD=E.CODPROD AND IE.SEQEST=E.SEQEST AND
                O.CODEMPPD=E.CODEMP AND O.CODFILIALPD=E.CODFILIAL AND
                O.CODPROD=E.CODPROD AND O.SEQEST=E.SEQEST AND
                O.CODEMP=:ICODEMP AND O.CODFILIAL=:ICODFILIAL AND
                O.CODOP=:ICODOP AND O.SEQOP=:ISEQOP AND
                IT.codemp=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL AND
                IT.codop=O.CODOP AND IT.seqop=O.seqop AND
                IE.codempfs=IT.codempfs AND IE.codfilial=IT.codfilialfs AND
                IE.codfase=IT.codfase and
                ie.codemppd=it.codemp and ie.codfilialpd=it.codfilial and
                ie.codprodpd=it.codprod and
                IE.qtdvariavel = 'S'
            INTO :ISEQITOP,:NQTDITOP
    DO
    BEGIN
        UPDATE PPITOP IOP SET QTDITOP=:NQTDITOP
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND IOP.codop=:ICODOP
                AND IOP.seqop=:ISEQOP AND IOP.seqitop=:ISEQITOP;

    END
   SUSPEND;
END
^

/* Alter (PPRELCUSTOSP) */
ALTER PROCEDURE PPRELCUSTOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
CTIPOCUSTO CHAR(1),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
TIPOPROD VARCHAR(2),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
begin
  /* Procedure Text */
  IF (ICODEMPGP IS NOT NULL) THEN
  BEGIN
    IF (STRLEN(RTRIM(CCODGRUP))<14) THEN
       CCODGRUP = RTRIM(CCODGRUP)||'%';
  END
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  FOR SELECT P.CODPROD,P.REFPROD,P.DESCPROD, P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
   ( (:ICODEMPMC IS NULL) OR (P.CODEMPMC=:ICODEMPMC AND P.CODFILIALMC=:SCODFILIALMC AND
      P.CODMARCA=:CCODMARCA) ) AND
   ((:ICODEMPGP IS NULL) OR (P.CODEMPGP=:ICODEMPGP AND P.CODFILIALGP=:SCODFILIALGP AND
      P.CODGRUP LIKE :CCODGRUP) )
   INTO :CODPROD, :REFPROD, :DESCPROD, :TIPOPROD  DO
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM PPCUSTOPRODSP(:ICODEMP,
        :SCODFILIAL, :CODPROD, :DTESTOQ, :CTIPOCUSTO, :ICODEMPAX,
        :SCODFILIALAX, :ICODALMOX, 'N')
       INTO :CUSTOUNIT, :SLDPROD;
     CUSTOTOT = CUSTOUNIT * SLDPROD;
     SUSPEND;
  END
end
^

/* empty dependent procedure body */
/* Clear: SGINICONSP for: SGATUALIZABDSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE SGINICONSP(ICODEMP INTEGER,
CIDUSU CHAR(8),
ICODFILIALSEL SMALLINT,
ICODTERM SMALLINT)
 RETURNS(SRET SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter (SGATUALIZABDSP) */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
DECLARE VARIABLE ICODEMPTMP INTEGER;
begin
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Entrou no atualizabdsp '||cast(ICODEMP AS CHAR(10)));*/

  EXECUTE PROCEDURE SGDADOSINISP(:ICODEMP);
  EXECUTE PROCEDURE SGGRANTUSERSP ;
  FOR SELECT CODEMP FROM SGEMPRESA INTO :ICODEMPTMP DO
  BEGIN
     EXECUTE PROCEDURE SGOBJETOINSTBSP(:ICODEMPTMP);
     EXECUTE PROCEDURE SGGRANTADMSP(:ICODEMPTMP);
  END
  suspend;
end
^

/* Alter (SGGERACNFSP) */
ALTER PROCEDURE SGGERACNFSP(TIPO VARCHAR(2),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TIPOVENDA VARCHAR(2),
CODVENDA INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER)
 AS
declare variable icnf bigint;
begin
   --execute procedure sgdebugsp 'sggeracnfsp', 'Entrou na procedure geracnf';
   SELECT BISEQ FROM SGSEQUENCE_IDSP('NCF') INTO :ICNF;
    
   if(TIPO='CP') THEN
   BEGIN
      --execute procedure sgdebugsp 'sggeracnfsp', 'PEGOU tipo CP e O ICNF: '||:ICNF;
      UPDATE CPCOMPRA set CNF=:ICNF WHERE CODEMP=:codempcp AND CODFILIAL=:codfilialcp AND CODCOMPRA=:CODCOMPRA;
   END
   ELSE IF(TIPO='VD') THEN
   BEGIN
      UPDATE VDVENDA set CNF=:ICNF WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
   END
   suspend;
end
^

/* Alter (SGINICONSP) */
ALTER PROCEDURE SGINICONSP(ICODEMP INTEGER,
CIDUSU CHAR(8),
ICODFILIALSEL SMALLINT,
ICODTERM SMALLINT)
 RETURNS(SRET SMALLINT)
 AS
DECLARE VARIABLE ICONECTADO INTEGER;
DECLARE VARIABLE ICODFILIALSELCX SMALLINT;
DECLARE VARIABLE ICODEMPTMP INTEGER;
DECLARE VARIABLE ICODFILIAL SMALLINT;
begin
  /* Procedure para inicialização da conexão com o banco de dados */
  SRET = 0;
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Vai verificar o usuário');*/
  CIDUSU = lower(CIDUSU);
  IF ( CIDUSU='sysdba' ) THEN
  BEGIN
/*     CTEMP = printLog('Usuário sysdba');*/

     SELECT CODEMP FROM SGEMPRESA WHERE CODEMP=:ICODEMP INTO :ICODEMPTMP;
     IF (ICODEMPTMP IS NULL) THEN
        EXECUTE PROCEDURE SGATUALIZABDSP(:ICODEMP);
  END
  SELECT CODFILIAL FROM SGUSUARIO WHERE IDUSU=:cIdUsu AND
      CODEMP=:ICODEMP  INTO :iCodFilial;
/*  CTEMP = printLog('O usuário é = '||cIdUsu); */

/*  CTEMP = printLog('Contador de usuários '||cIdUsu); */

  if (iCodFilial is null) then
    EXCEPTION SGCONEXAOEX02;

  if (iCodFilialSel is not null) then
  begin
      SRET = 1;
      SELECT CONECTADO, CODFILIALSEL FROM SGCONEXAO
        WHERE NRCONEXAO=CURRENT_CONNECTION INTO :iConectado, iCodFilialSelCX;
      if (iConectado is null) then
          INSERT INTO SGCONEXAO (NRCONEXAO, CODEMP,CODFILIAL,IDUSU,CODFILIALSEL,CONECTADO,CODTERM)
            VALUES (CURRENT_CONNECTION, :iCodemp, :iCodfilial, :cIdusu, :iCodfilialSel, 1, :iCodTerm);
      else
      begin
          if (iConectado<=0) then
             UPDATE SGCONEXAO SET CONECTADO=1, CODFILIALSEL=:iCodfilialSel, CODTERM = :iCodTerm,
               CODEMP=:iCodemp, CODFILIAL=:iCodfilial , IDUSU=:cIdUsu
               WHERE NRCONEXAO=CURRENT_CONNECTION;
          else
          begin
             if (not (iCodfilialSelCX=iCodfilialSel) ) then
                EXCEPTION SGCONEXAOEX01;
             else
                UPDATE SGCONEXAO SET CONECTADO=CONECTADO+1, CODTERM = :iCodTerm,
                   CODEMP=:iCodemp, CODFILIAL=:iCodfilial, IDUSU=:cIdUsu
                WHERE NRCONEXAO=CURRENT_CONNECTION;
          end
      end 
  end
  suspend;
end
^

/* Alter (SGOBJETOATUALIZANIVELSP) */
ALTER PROCEDURE SGOBJETOATUALIZANIVELSP(CODEMP INTEGER)
 RETURNS(IDOBJ VARCHAR(30),
NIVELOBJ SMALLINT)
 AS
declare variable sqltexto varchar(200);
declare variable contador smallint;
declare variable numregobj integer;
begin
  /* Nivel 0 */

   nivelobj = -1;
   update sgobjeto o set o.nivelobj=:nivelobj where codemp=:codemp;

   nivelobj = 0;

   while (:nivelobj<=100) do
   begin
       for select obj.idobj
           from sgobjeto obj
           where obj.codemp=:codemp and obj.nivelobj=-1
           into :idobj do
       begin
           contador = 0;
           select  count(*)
           from rdb$relation_constraints rc
              , rdb$relation_constraints rcpk
              , rdb$ref_constraints rf
           where
               rc.rdb$relation_name=:idobj
              and rc.rdb$constraint_type='FOREIGN KEY'
              and rc.rdb$constraint_name=rf.rdb$constraint_name
              and rcpk.rdb$constraint_name=rf.rdb$const_name_uq
              and not exists( select * from sgobjeto obj1
                where obj1.codemp=:codemp and obj1.idobj=rcpk.rdb$relation_name
                and rcpk.rdb$relation_name<>:idobj
                and rcpk.rdb$relation_name<>'SGFILIAL'
                and obj1.nivelobj>-1 and obj1.nivelobj<:nivelobj
              )
          -- group by 1
           into :contador;
           if (:contador is null or contador=0) then
           begin
              sqltexto = 'select count(*) from '||idobj;
              execute statement sqltexto into :numregobj;
              update sgobjeto
                 set nivelobj=:nivelobj
                 , numregobj=:numregobj
                 where codemp=:codemp and idobj=:idobj;

           end
       end
       nivelobj=nivelobj+1;
   end
   suspend;
   --INSERT INTO SGOBJETO (CODEMP,IDOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ,SIGLAOBJ)
     --  SELECT DISTINCT :ICODEMP, RC.RDB$RELATION_NAME, RC.RDB$RELATION_NAME,  'TB', RC.RDB$RELATION_NAME /*CAST(R.RDB$DESCRIPTION AS VARCHAR(10000)),
       --'S', SUBSTRING(RC.RDB$RELATION_NAME FROM 1 FOR 8)
--       FROM RDB$RELATION_CONSTRAINTS RC, RDB$RELATIONS R
  --     WHERE R.RDB$RELATION_NAME = RC.RDB$RELATION_NAME AND
    --   RC.RDB$RELATION_NAME NOT IN (SELECT IDOBJ FROM SGOBJETO O
--       WHERE O.TIPOOBJ='TB' AND O.IDOBJ=RC.RDB$RELATION_NAME);*/

end
^

/* Alter (SGOBJETOINSTBSP) */
ALTER PROCEDURE SGOBJETOINSTBSP(ICODEMP INTEGER)
 AS
begin

   INSERT INTO SGOBJETO (CODEMP,IDOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ,SIGLAOBJ)
       SELECT DISTINCT :ICODEMP, RC.RDB$RELATION_NAME, RC.RDB$RELATION_NAME,
        'TB', RC.RDB$RELATION_NAME /*CAST(R.RDB$DESCRIPTION AS VARCHAR(10000))*/, 'N', SUBSTRING(RC.RDB$RELATION_NAME FROM 1 FOR 8)
       FROM RDB$RELATION_CONSTRAINTS RC, RDB$RELATIONS R
       WHERE R.RDB$RELATION_NAME = RC.RDB$RELATION_NAME AND
       RC.RDB$RELATION_NAME NOT IN (SELECT IDOBJ FROM SGOBJETO O
       WHERE O.TIPOOBJ='TB' AND O.IDOBJ=RC.RDB$RELATION_NAME);

    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.5.5 (21/01/2013)';
    suspend;
end
^

/* Alter (SGSEQUENCE_IDSP) */
ALTER PROCEDURE SGSEQUENCE_IDSP(STABLE_NAME VARCHAR(128))
 RETURNS(BISEQ BIGINT)
 AS
begin
  --execute procedure sgdebugsp 'sggeracnfsp', 'Entrou na procedure SGSEQUENCE';
  BISEQ = NULL;
  SELECT SEQ_ID FROM SGSEQUENCE_ID
    WHERE TABLE_NAME=:sTABLE_NAME
    INTO :BISEQ;
   IF (BISEQ IS NULL) THEN
   BEGIN
     BISEQ = 1;
     INSERT INTO SGSEQUENCE_ID (TABLE_NAME,SEQ_ID)
            VALUES (:sTABLE_NAME, :BISEQ+1);
   END
   ELSE
   BEGIN
      UPDATE SGSEQUENCE_ID SET SEQ_ID=:BISEQ+1 WHERE
          TABLE_NAME=:sTABLE_NAME;
   END
   suspend;
end
^

/* Alter (TKGERACAMPANHACTO) */
ALTER PROCEDURE TKGERACAMPANHACTO(TIPOCTO CHAR(1),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
CODCAMP CHAR(13),
CODEMPCO INTEGER,
CODFILIALCO SMALLINT,
CODCTO INTEGER,
CODEMPAT INTEGER,
CODFILIALAT SMALLINT,
CODATIV INTEGER,
SITHISTTK CHAR(2),
DESCHISTTK VARCHAR(1000))
 AS
declare variable seqcampcto integer; /* Código do contato pra validação. */
declare variable seqsitcamp integer;
declare variable codfilialhi smallint;
declare variable codhisttk integer;
declare variable codempae integer;
declare variable codfilialae smallint;
declare variable codatend integer; /* Código do atendente. */
declare variable codempus integer;
declare variable codfilialus smallint;
declare variable idusu char(8); /* Id do usuário */
begin

    select icodfilial from sgretfilial(:codempca,'TKHISTORICO') into codfilialhi;
    select iseq from spgeranum(:codempca,:codfilialhi,'HI') into codhisttk;
    select codempusu, codfilialusu, idusus from sgretinfousu(:codempca, user) where codempusu=:codempca into
            :codempus, :codfilialus, :idusu;

    select first 1 codemp, codfilial, codatend from atatendente
            where codempus=:codempus and codfilialus=:codfilialus and idusu=:idusu
    into codempae, codfilialae, codatend;

    if(:codatend is null) then
    begin
        exception TKGERACAMANHACTO01 ' - ID: ' || idusu || ' - User: '|| user ;
    end

    -- Verifica se o contato já foi vinculado à campanha

    if ( tipocto = 'O' ) then
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempco=:codempco and cc.codfilialco=:codfilialco and cc.codcto=:codcto
           into :seqcampcto;
    end
    else
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempcl=:codempco and cc.codfilialcl=:codfilialco and cc.codcli=:codcto
           into :seqcampcto;
    end

    if ( (:seqcampcto is null) or (:seqcampcto=0) ) then
    begin
       select max(coalesce(seqcampcto,0)+1) seqcampcto from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           into :seqcampcto;
        if (seqcampcto is null) then
        begin
           seqcampcto = 1;
        end
        if ( tipocto = 'O' ) then
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempco, codfilialco, codcto)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
        else 
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempcl, codfilialcl, codcli)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
               -- exception TKGERACAMANHACTO01 'teste';
        end

    end

    seqsitcamp = 0;
    select max(sc.seqsitcamp) from tksitcamp sc
            where sc.codemp=:codempca and sc.codfilial=:codfilialca and
                sc.codcamp=:codcamp and sc.tipocto=:tipocto
                into :seqsitcamp;

    if(:seqsitcamp is null) then
    begin
        seqsitcamp = 0;
    end

    seqsitcamp = seqsitcamp + 1;

    if ( tipocto = 'O' ) then
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempco,codfilialco,codcto,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end
    else
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempcl,codfilialcl,codcli,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end

    -- Inserindo histórico
    
    if ( tipocto = 'O' ) then 
    begin 
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempco,codfilialco,codcto,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
    else
    begin
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempcl,codfilialcl,codcli,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
   

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
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod varchar(20);
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
declare variable srefprod varchar(20);
declare variable stipoprod varchar(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable qtditorc numeric(15,5);
declare variable calcstcm char(1);
declare variable aliqicmsstcm numeric(9,2);
declare variable vlricmsstcalc numeric(15,5);
begin
-- Inicialização de variaveis
    CALCSTCM = 'N';
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
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc, it.qtditorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda, :qtditorc;

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
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss,coalesce(calcstcm,'N'),aliqicmsstcm
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA, CALCSTCM, ALIQICMSSTCM;

   
   
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
     if (:iqtditvenda<>:qtditorc) then
    begin
       VLRDESCITVENDA = (:VLRDESCITVENDA/:QTDITORC*:IQTDITVENDA);
    end
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
            VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
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
            --Calculo do ICMS ST para fora de mato grosso.

            
            if(calcstcm = 'N') then
            begin
                
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
            --Calculo do ICMS ST para o mato grosso.
            else
            begin
                if(percred>0 and redbaseicmsst='S') then
                begin
                   vlricmsstcalc=0;
                -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                   vlricmsstcalc = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100) );
                   vlrbaseicmsstitvenda =   (vlricmsitvenda + vlricmsstcalc)/(PERCICMSST/100);
                   

                end
                else
                begin
                    -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                    vlricmsstcalc = ( (coalesce(vlrbaseicmsbrutitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
                    
                    vlrbaseicmsstitvenda = ((vlricmsitvenda  + vlricmsstcalc )/(:PERCICMSST/100));
                  
                end

           
                VLROUTRASITVENDA = 0;
                VLRISENTASITVENDA = 0;
                VLRICMSSTITVENDA = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
           

            end 
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
        margemvlagritvenda,vlrbaseicmsbrutitvenda, calcstcm)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda, :calcstcm);
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

/* Alter (VDCONTRATOTOTSP) */
ALTER PROCEDURE VDCONTRATOTOTSP(CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR INTEGER,
CODEMPSC INTEGER,
CODFILIALSC SMALLINT,
CODCONTRSC INTEGER,
CODEMPTA INTEGER,
CODFILIALTA SMALLINT,
CODTAREFA INTEGER,
CODEMPST INTEGER,
CODFILIALST SMALLINT,
CODTAREFAST INTEGER,
DATAINI DATE,
DATAFIM DATE)
 RETURNS(TOTALPREVGERAL NUMERIC(15,5),
TOTALPREVPER NUMERIC(15,5),
TOTALGERAL NUMERIC(15,5),
TOTALCOBCLIGERAL NUMERIC(15,5),
TOTALANT NUMERIC(15,5),
TOTALCOBCLIANT NUMERIC(15,5),
TOTALPER NUMERIC(15,5),
TOTALCOBCLIPER NUMERIC(15,5),
SALDOANT NUMERIC(15,5),
SALDOPER NUMERIC(15,5))
 AS
begin
  if (:codcontrsc is not null) then
  begin
     codempct = codempsc;
     codfilialct = codfilialsc;
     codcontr = codcontrsc;
  end
  if (:codtarefast is not null) then
  begin
     codempta = codempst;
     codfilialta = codfilialst;
     codtarefa = codtarefast;
  end
  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
      on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalgeral,  :totalcobcligeral;

  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
      on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
      a.dataatendo between :dataini and :datafim and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalper,  :totalcobcliper;

  select sum(a.totalgeral), sum(a.totalcobcli)
   from atatendimentovw02 a
    left outer join crtarefa ta
     on ta.codemp=a.codempta and ta.codfilial=a.codfilialta and ta.codtarefa=a.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where a.codempct=:codempct and a.codfilialct=:codfilialct and a.codcontr=:codcontr and
    (:coditcontr is null or a.coditcontr=:coditcontr) and
     a.dataatendo < :dataini and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalant,  :totalcobcliant;

  select sum(coalesce(st.tempodectarefa, ta.tempodectarefa) )
    from crtarefa ta
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where ta.codempct=:codempct and ta.codfilialct=:codfilialct and ta.codcontr=:codcontr and
    (:coditcontr is null or ta.coditcontr=:coditcontr) and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into totalprevgeral;

  select sum(tp.tempodectarefa)
   from crtarefa ttp, crtarefaper tp
    left outer join crtarefa ta
      on ta.codemp=ttp.codempta and ta.codfilial=ttp.codfilialta and ta.codtarefa=ttp.codtarefa
    left outer join crtarefa st
      on st.codempta=ta.codemp and st.codfilialta=ta.codemp and st.codtarefata=ta.codtarefa
    where ttp.codemp=tp.codemp and ttp.codfilial=tp.codfilial and ttp.codtarefa=tp.codtarefa and
      ttp.codempct=:codempct and ttp.codfilialct=:codfilialct and ttp.codcontr=:codcontr and
    (:coditcontr is null or ttp.coditcontr=:coditcontr) and
      tp.dtiniper>=:dataini and tp.dtfimper<=:datafim and
    (
       (:codtarefa is null) or
       (st.codtarefa is null and ta.codemp=:codempta and ta.codfilial=:codfilialta and ta.codtarefa=:codtarefa ) or
       (st.codtarefa is not null and st.codempta=:codempta and st.codfilialta=:codfilialta and st.codtarefata=:codtarefa )
    )
  into :totalprevper;

  if (totalgeral is null) then
      totalgeral = 0;
  if (totalcobcligeral is null) then
      totalcobcligeral = 0;
  if (totalant is null) then
      totalant = 0;
  if (totalcobcliant is null) then
      totalcobcliant = 0;
  if (totalper is null) then
      totalper = 0;
  if (totalcobcliper is null) then
      totalcobcliper = 0;
  saldoant = totalprevgeral - totalcobcliant;
  saldoper = totalprevper - totalcobcliper;

  suspend;
end
^

/* Alter (VDRETULTVDCLIPROD) */
ALTER PROCEDURE VDRETULTVDCLIPROD(ICODEMP INTEGER,
ICODCLI INTEGER,
ICODFILIALVD SMALLINT,
ICODVEND INTEGER,
DTINI DATE,
DTFIM DATE,
CODEMPTIPOCL INTEGER,
CODFILIALTIPOCL SMALLINT,
CODTIPOCLI INTEGER)
 RETURNS(RAZCLI_RET CHAR(60),
CODCLI_RET INTEGER,
DESCPROD_RET CHAR(50),
CODPROD_RET INTEGER,
DTEMITVENDA_RET DATE,
DOCVENDA_RET INTEGER,
SERIE_RET CHAR(4),
PRECOVENDA_RET NUMERIC(15,4))
 AS
declare variable icodfilial smallint;
declare variable icodprod integer;
begin

    select icodfilial from sgretfilial(:ICODEMP,'VDVENDA') into :ICODFILIAL;

    for select v.codcli,iv.codprod
        from vdvenda v, vdcliente cl, vditvenda iv
        where
            iv.codemp=v.codemp and iv.codfilial=v.codfilial
            and iv.tipovenda=v.TIPOVENDA and iv.codvenda=v.codvenda
            and v.codemp=:ICODEMP and v.codfilial=:ICODFILIAL
            and (v.codcli=:ICODCLI or :ICODCLI is null)
            and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM
            and cl.codemp=v.codempcl and cl.codfilial=v.codfilialcl and cl.codcli=v.codcli
            and (cl.codtipocli=:codtipocli or :codtipocli is null)
        group by v.codcli,iv.codprod into :ICODCLI,:ICODPROD
    do
    begin
        select first 1 c.razcli, c.codcli, p.descprod, iv.codprod, v.dtemitvenda, v.docvenda, v.serie,
            (iv.vlrliqitvenda/(case when iv.qtditvenda=0 then 1 else iv.qtditvenda end)) precovenda
        from vdcliente c, vdvenda v, vditvenda iv, eqproduto p
        where
            c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli
            and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and iv.codemp=v.codemp
            and iv.codfilial=v.codfilial and iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda
            and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd and p.codprod=iv.codprod
            and v.codempvd=:ICODEMP and v.codfilialvd=:ICODFILIALVD and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM and c.codcli=:ICODCLI and p.codprod=:ICODPROD
            order by v.dtemitvenda desc
            into :RAZCLI_RET, :CODCLI_RET, :DESCPROD_RET, :CODPROD_RET, :DTEMITVENDA_RET, :DOCVENDA_RET, :SERIE_RET,
                 :PRECOVENDA_RET;
            suspend;
    end
end
^

/* Alter (VDUPVENDAORCSP) */
ALTER PROCEDURE VDUPVENDAORCSP(ICODEMP INTEGER,
ICODFILIAL INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIALVD INTEGER,
ICODVENDA INTEGER,
ICODITVENDA INTEGER,
STIPOVENDA CHAR(10))
 AS
declare variable iconta1 decimal(15,5);
declare variable vlrdescvenda decimal(15,5);
declare variable iconta2 decimal(15,5);
declare variable iconta3 decimal(15,5);
begin
  /* Procedure Text */
  

 -- EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'inicio :'|| cast('now' as time);

  INSERT INTO VDVENDAORC (CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
                          CODEMPOR,CODFILIALOR,TIPOORC,CODORC,CODITORC) VALUES
                         (:ICODEMP,:ICODFILIALVD,:STIPOVENDA,:ICODVENDA,:ICODITVENDA,
                          :ICODEMP,:ICODFILIAL,'O',:ICODORC,:ICODITORC);

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim insert vdvendaorc:'|| cast('now' as time);

  UPDATE VDITORCAMENTO SET EMITITORC='S'
       WHERE CODITORC=:ICODITORC AND CODORC=:ICODORC AND TIPOORC='O'
       AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
       AND EMITITORC<>'S';


--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vditorcamento '|| cast('now' as time);
    
  SELECT SUM(QTDITORC), SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO :ICONTA1, :ICONTA2;
--  SELECT SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
--    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
--      INTO ICONTA2;

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim select sum(qtditorc) '|| cast('now' as time);

  IF ( ICONTA1 = ICONTA2 ) THEN
  BEGIN
    UPDATE VDORCAMENTO SET STATUSORC='OV'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC
    AND STATUSORC<>'OV';
    SELECT SUM(IV.QTDITVENDA) FROM VDITVENDA IV, VDVENDAORC VO
       WHERE VO.CODEMP=:ICODEMP AND VO.CODFILIAL=:ICODFILIALVD AND
       VO.TIPOVENDA=:STIPOVENDA AND VO.CODVENDA=:ICODVENDA AND
       IV.CODEMP=VO.CODEMP AND IV.CODFILIAL=VO.CODFILIAL AND
       IV.TIPOVENDA=VO.TIPOVENDA AND IV.CODVENDA=VO.CODVENDA AND
       IV.CODITVENDA=VO.CODITVENDA
          INTO ICONTA3;
  --  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento OV = ICONTA1=ICONTA2 '|| cast('now' as time);

    IF ( ICONTA1<>ICONTA3 ) THEN -- Verifica se o orçamento foi dividido em várias vendas
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA
           AND VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1<>ICONTA3 '|| cast('now' as time);

    END
  END
  ELSE IF (ICONTA1 > ICONTA2) THEN
  BEGIN               
    UPDATE VDORCAMENTO SET STATUSORC='FP'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC AND
     STATUSORC<>'FP';
    SELECT SUM(I.VLRDESCITVENDA) FROM VDITVENDA I
       WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:ICODFILIALVD AND I.TIPOVENDA=:STIPOVENDA AND I.CODVENDA=:ICODVENDA
       INTO :VLRDESCVENDA;
--    EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento STATUSORC=FP ICONTA1 > ICONTA2 '|| cast('now' as time);
    IF (:VLRDESCVENDA<>0) THEN
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA AND
         VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1>ICONTA2 '|| cast('now' as time);
    END 
  END

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim VDUPVENDAORCSP'|| cast('now' as time);

  --  exception vdvendaex06 'teste de velociadade';

  suspend;
end
^

SET TERM ; ^

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCOMPRA POSITION 3;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPG POSITION 4;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPG POSITION 5;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLANOPAG POSITION 6;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPFR POSITION 7;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALFR POSITION 8;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFOR POSITION 9;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSE POSITION 10;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSE POSITION 11;

ALTER TABLE CPCOMPRA ALTER COLUMN SERIE POSITION 12;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTM POSITION 13;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTM POSITION 14;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOMOV POSITION 15;

ALTER TABLE CPCOMPRA ALTER COLUMN DOCCOMPRA POSITION 16;

ALTER TABLE CPCOMPRA ALTER COLUMN DTENTCOMPRA POSITION 17;

ALTER TABLE CPCOMPRA ALTER COLUMN DTEMITCOMPRA POSITION 18;

ALTER TABLE CPCOMPRA ALTER COLUMN DTCOMPCOMPRA POSITION 19;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCDESCCOMPRA POSITION 20;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCIPICOMPRA POSITION 21;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPRODCOMPRA POSITION 22;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRLIQCOMPRA POSITION 23;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOMPRA POSITION 24;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCCOMPRA POSITION 25;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 26;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRADICCOMPRA POSITION 27;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSCOMPRA POSITION 28;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSSTCOMPRA POSITION 29;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIPICOMPRA POSITION 30;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEPISCOMPRA POSITION 31;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASECOFINSCOMPRA POSITION 32;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCOMPRA POSITION 33;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSSTCOMPRA POSITION 34;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIPICOMPRA POSITION 35;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPISCOMPRA POSITION 36;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOFINSCOMPRA POSITION 37;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFUNRURALCOMPRA POSITION 38;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFRETECOMPRA POSITION 39;

ALTER TABLE CPCOMPRA ALTER COLUMN VLROUTRASCOMPRA POSITION 40;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRISENTASCOMPRA POSITION 41;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTC POSITION 42;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTC POSITION 43;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOCOB POSITION 44;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPBO POSITION 45;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALBO POSITION 46;

ALTER TABLE CPCOMPRA ALTER COLUMN CODBANCO POSITION 47;

ALTER TABLE CPCOMPRA ALTER COLUMN IMPNOTACOMPRA POSITION 48;

ALTER TABLE CPCOMPRA ALTER COLUMN BLOQCOMPRA POSITION 49;

ALTER TABLE CPCOMPRA ALTER COLUMN EMMANUT POSITION 50;

ALTER TABLE CPCOMPRA ALTER COLUMN FLAG POSITION 51;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETECOMPRA POSITION 52;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPOFRETECOMPRA POSITION 53;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSOL POSITION 54;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSOL POSITION 55;

ALTER TABLE CPCOMPRA ALTER COLUMN CODSOL POSITION 56;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTN POSITION 57;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTN POSITION 58;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTRAN POSITION 59;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSERVACAO POSITION 60;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS01 POSITION 61;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS02 POSITION 62;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS03 POSITION 63;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS04 POSITION 64;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICCOMPRA POSITION 65;

ALTER TABLE CPCOMPRA ALTER COLUMN QTDFRETECOMPRA POSITION 66;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETEBASEICMS POSITION 67;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICBASEICMS POSITION 68;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICIPIBASEICMS POSITION 69;

ALTER TABLE CPCOMPRA ALTER COLUMN CHAVENFECOMPRA POSITION 70;

ALTER TABLE CPCOMPRA ALTER COLUMN STATUSCOMPRA POSITION 71;

ALTER TABLE CPCOMPRA ALTER COLUMN NRODI POSITION 72;

ALTER TABLE CPCOMPRA ALTER COLUMN DTREGDI POSITION 73;

ALTER TABLE CPCOMPRA ALTER COLUMN LOCDESEMBDI POSITION 74;

ALTER TABLE CPCOMPRA ALTER COLUMN SIGLAUFDESEMBDI POSITION 75;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPAISDESEMBDI POSITION 76;

ALTER TABLE CPCOMPRA ALTER COLUMN DTDESEMBDI POSITION 77;

ALTER TABLE CPCOMPRA ALTER COLUMN IDENTCONTAINER POSITION 78;

ALTER TABLE CPCOMPRA ALTER COLUMN CALCTRIB POSITION 79;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPRM POSITION 80;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALRM POSITION 81;

ALTER TABLE CPCOMPRA ALTER COLUMN TICKET POSITION 82;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCT POSITION 83;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCT POSITION 84;

ALTER TABLE CPCOMPRA ALTER COLUMN NUMCONTA POSITION 85;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCC POSITION 86;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCC POSITION 87;

ALTER TABLE CPCOMPRA ALTER COLUMN ANOCC POSITION 88;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCC POSITION 89;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPN POSITION 90;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPN POSITION 91;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLAN POSITION 92;

ALTER TABLE CPCOMPRA ALTER COLUMN INFCOMPL POSITION 93;

ALTER TABLE CPCOMPRA ALTER COLUMN NUMACDRAW POSITION 94;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPODOCIMP POSITION 95;

ALTER TABLE CPCOMPRA ALTER COLUMN SITDOC POSITION 96;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSNFE POSITION 97;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPOP POSITION 98;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALOP POSITION 99;

ALTER TABLE CPCOMPRA ALTER COLUMN CODOP POSITION 100;

ALTER TABLE CPCOMPRA ALTER COLUMN SEQOP POSITION 101;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPIM POSITION 102;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALIM POSITION 103;

ALTER TABLE CPCOMPRA ALTER COLUMN CODIMP POSITION 104;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIICOMPRA POSITION 105;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIICOMPRA POSITION 106;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSDIFERIDO POSITION 107;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSDEVIDO POSITION 108;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCREDPRESUM POSITION 109;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSPAG POSITION 110;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPOC POSITION 111;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALOC POSITION 112;

ALTER TABLE CPCOMPRA ALTER COLUMN CODORDCP POSITION 113;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRISSCOMPRA POSITION 114;

ALTER TABLE CPCOMPRA ALTER COLUMN NROORDEMCOMPRA POSITION 115;

ALTER TABLE CPCOMPRA ALTER COLUMN VLROUTRASDESP POSITION 116;

ALTER TABLE CPCOMPRA ALTER COLUMN CNF POSITION 117;

ALTER TABLE CPCOMPRA ALTER COLUMN CHAVENFEVALIDA POSITION 118;

ALTER TABLE CPCOMPRA ALTER COLUMN DTINS POSITION 119;

ALTER TABLE CPCOMPRA ALTER COLUMN HINS POSITION 120;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUINS POSITION 121;

ALTER TABLE CPCOMPRA ALTER COLUMN DTALT POSITION 122;

ALTER TABLE CPCOMPRA ALTER COLUMN HALT POSITION 123;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUALT POSITION 124;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPFORNECED ALTER COLUMN CODFOR POSITION 3;

ALTER TABLE CPFORNECED ALTER COLUMN RAZFOR POSITION 4;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMPTF POSITION 5;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIALTF POSITION 6;

ALTER TABLE CPFORNECED ALTER COLUMN CODTIPOFOR POSITION 7;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMPBO POSITION 8;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIALBO POSITION 9;

ALTER TABLE CPFORNECED ALTER COLUMN CODBANCO POSITION 10;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMPHP POSITION 11;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIALHP POSITION 12;

ALTER TABLE CPFORNECED ALTER COLUMN CODHIST POSITION 13;

ALTER TABLE CPFORNECED ALTER COLUMN NOMEFOR POSITION 14;

ALTER TABLE CPFORNECED ALTER COLUMN DATAFOR POSITION 15;

ALTER TABLE CPFORNECED ALTER COLUMN ATIVOFOR POSITION 16;

ALTER TABLE CPFORNECED ALTER COLUMN PESSOAFOR POSITION 17;

ALTER TABLE CPFORNECED ALTER COLUMN CNPJFOR POSITION 18;

ALTER TABLE CPFORNECED ALTER COLUMN CPFFOR POSITION 19;

ALTER TABLE CPFORNECED ALTER COLUMN INSCFOR POSITION 20;

ALTER TABLE CPFORNECED ALTER COLUMN RGFOR POSITION 21;

ALTER TABLE CPFORNECED ALTER COLUMN ENDFOR POSITION 22;

ALTER TABLE CPFORNECED ALTER COLUMN NUMFOR POSITION 23;

ALTER TABLE CPFORNECED ALTER COLUMN COMPLFOR POSITION 24;

ALTER TABLE CPFORNECED ALTER COLUMN BAIRFOR POSITION 25;

ALTER TABLE CPFORNECED ALTER COLUMN CEPFOR POSITION 26;

ALTER TABLE CPFORNECED ALTER COLUMN CIDFOR POSITION 27;

ALTER TABLE CPFORNECED ALTER COLUMN UFFOR POSITION 28;

ALTER TABLE CPFORNECED ALTER COLUMN CONTFOR POSITION 29;

ALTER TABLE CPFORNECED ALTER COLUMN FONEFOR POSITION 30;

ALTER TABLE CPFORNECED ALTER COLUMN FAXFOR POSITION 31;

ALTER TABLE CPFORNECED ALTER COLUMN AGENCIAFOR POSITION 32;

ALTER TABLE CPFORNECED ALTER COLUMN CONTAFOR POSITION 33;

ALTER TABLE CPFORNECED ALTER COLUMN EMAILFOR POSITION 34;

ALTER TABLE CPFORNECED ALTER COLUMN OBSFOR POSITION 35;

ALTER TABLE CPFORNECED ALTER COLUMN CELFOR POSITION 36;

ALTER TABLE CPFORNECED ALTER COLUMN CLIFOR POSITION 37;

ALTER TABLE CPFORNECED ALTER COLUMN SSPFOR POSITION 38;

ALTER TABLE CPFORNECED ALTER COLUMN DDDFONEFOR POSITION 39;

ALTER TABLE CPFORNECED ALTER COLUMN DDDFAXFOR POSITION 40;

ALTER TABLE CPFORNECED ALTER COLUMN DDDCELFOR POSITION 41;

ALTER TABLE CPFORNECED ALTER COLUMN SITEFOR POSITION 42;

ALTER TABLE CPFORNECED ALTER COLUMN CODCONTDEB POSITION 43;

ALTER TABLE CPFORNECED ALTER COLUMN CODCONTCRED POSITION 44;

ALTER TABLE CPFORNECED ALTER COLUMN CODFORCONTAB POSITION 45;

ALTER TABLE CPFORNECED ALTER COLUMN CODMUNIC POSITION 46;

ALTER TABLE CPFORNECED ALTER COLUMN SIGLAUF POSITION 47;

ALTER TABLE CPFORNECED ALTER COLUMN CODPAIS POSITION 48;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMPUC POSITION 49;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIALUC POSITION 50;

ALTER TABLE CPFORNECED ALTER COLUMN CODUNIFCOD POSITION 51;

ALTER TABLE CPFORNECED ALTER COLUMN CODEMPFF POSITION 52;

ALTER TABLE CPFORNECED ALTER COLUMN CODFILIALFF POSITION 53;

ALTER TABLE CPFORNECED ALTER COLUMN CODFISCFOR POSITION 54;

ALTER TABLE CPFORNECED ALTER COLUMN SUFRAMAFOR POSITION 55;

ALTER TABLE CPFORNECED ALTER COLUMN NRODEPENDFOR POSITION 56;

ALTER TABLE CPFORNECED ALTER COLUMN INSCCONREG POSITION 57;

ALTER TABLE CPFORNECED ALTER COLUMN CODEXPORTADOR POSITION 58;

ALTER TABLE CPFORNECED ALTER COLUMN DTINS POSITION 59;

ALTER TABLE CPFORNECED ALTER COLUMN HINS POSITION 60;

ALTER TABLE CPFORNECED ALTER COLUMN IDUSUINS POSITION 61;

ALTER TABLE CPFORNECED ALTER COLUMN DTALT POSITION 62;

ALTER TABLE CPFORNECED ALTER COLUMN HALT POSITION 63;

ALTER TABLE CPFORNECED ALTER COLUMN IDUSUALT POSITION 64;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODIMP POSITION 3;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODEMPMI POSITION 4;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFILIALMI POSITION 5;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODMOEDA POSITION 6;

ALTER TABLE CPIMPORTACAO ALTER COLUMN COTACAOMOEDA POSITION 7;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODEMPPG POSITION 8;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFILIALPG POSITION 9;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODPLANOPAG POSITION 10;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODEMPFR POSITION 11;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFILIALFR POSITION 12;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFOR POSITION 13;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODEMPOI POSITION 14;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODFILIALOI POSITION 15;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODIMPOI POSITION 16;

ALTER TABLE CPIMPORTACAO ALTER COLUMN TIPOIMP POSITION 17;

ALTER TABLE CPIMPORTACAO ALTER COLUMN INVOICE POSITION 18;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DI POSITION 19;

ALTER TABLE CPIMPORTACAO ALTER COLUMN MANIFESTO POSITION 20;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CERTORIGEM POSITION 21;

ALTER TABLE CPIMPORTACAO ALTER COLUMN LACRE POSITION 22;

ALTER TABLE CPIMPORTACAO ALTER COLUMN PRESCARGA POSITION 23;

ALTER TABLE CPIMPORTACAO ALTER COLUMN IDENTHOUSE POSITION 24;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTA POSITION 25;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CONHECCARGA POSITION 26;

ALTER TABLE CPIMPORTACAO ALTER COLUMN IDENTCONTAINER POSITION 27;

ALTER TABLE CPIMPORTACAO ALTER COLUMN TIPOMANIFESTO POSITION 28;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTIMP POSITION 29;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTEMB POSITION 30;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTCHEGADA POSITION 31;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTDESEMBDI POSITION 32;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTREGDI POSITION 33;

ALTER TABLE CPIMPORTACAO ALTER COLUMN LOCALEMB POSITION 34;

ALTER TABLE CPIMPORTACAO ALTER COLUMN RECINTOADUANEIRO POSITION 35;

ALTER TABLE CPIMPORTACAO ALTER COLUMN CODPAISDESEMBDI POSITION 36;

ALTER TABLE CPIMPORTACAO ALTER COLUMN SIGLAUFDESEMBDI POSITION 37;

ALTER TABLE CPIMPORTACAO ALTER COLUMN LOCDESEMBDI POSITION 38;

ALTER TABLE CPIMPORTACAO ALTER COLUMN OBS POSITION 39;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VEICULO POSITION 40;

ALTER TABLE CPIMPORTACAO ALTER COLUMN PESOBRUTO POSITION 41;

ALTER TABLE CPIMPORTACAO ALTER COLUMN PESOLIQUIDO POSITION 42;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRFRETEMI POSITION 43;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRFRETE POSITION 44;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VMLEMI POSITION 45;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VMLDMI POSITION 46;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VMLE POSITION 47;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VMLD POSITION 48;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRSEGUROMI POSITION 49;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRSEGURO POSITION 50;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRII POSITION 51;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRIPI POSITION 52;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRPIS POSITION 53;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRCOFINS POSITION 54;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRDIREITOSAD POSITION 55;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRTHC POSITION 56;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRTHCMI POSITION 57;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRTXSISCOMEX POSITION 58;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRAD POSITION 59;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRADMI POSITION 60;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRBASEICMS POSITION 61;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRICMS POSITION 62;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRICMSDIFERIDO POSITION 63;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRICMSDEVIDO POSITION 64;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRICMSCREDPRESUM POSITION 65;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRICMSRECOLHIMENTO POSITION 66;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRDESPAD POSITION 67;

ALTER TABLE CPIMPORTACAO ALTER COLUMN VLRCOMPL POSITION 68;

ALTER TABLE CPIMPORTACAO ALTER COLUMN EMMANUT POSITION 69;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTINS POSITION 70;

ALTER TABLE CPIMPORTACAO ALTER COLUMN HINS POSITION 71;

ALTER TABLE CPIMPORTACAO ALTER COLUMN IDUSUINS POSITION 72;

ALTER TABLE CPIMPORTACAO ALTER COLUMN DTALT POSITION 73;

ALTER TABLE CPIMPORTACAO ALTER COLUMN HALT POSITION 74;

ALTER TABLE CPIMPORTACAO ALTER COLUMN IDUSUALT POSITION 75;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODCOMPRA POSITION 3;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODITCOMPRA POSITION 4;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPPD POSITION 5;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALPD POSITION 6;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODPROD POSITION 7;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPLE POSITION 8;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALLE POSITION 9;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODLOTE POSITION 10;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPNT POSITION 11;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALNT POSITION 12;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODNAT POSITION 13;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPAX POSITION 14;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALAX POSITION 15;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODALMOX POSITION 16;

ALTER TABLE CPITCOMPRA ALTER COLUMN QTDITCOMPRA POSITION 17;

ALTER TABLE CPITCOMPRA ALTER COLUMN QTDITCOMPRACANC POSITION 18;

ALTER TABLE CPITCOMPRA ALTER COLUMN PRECOITCOMPRA POSITION 19;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCDESCITCOMPRA POSITION 20;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 21;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCICMSITCOMPRA POSITION 22;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEICMSITCOMPRA POSITION 23;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRICMSITCOMPRA POSITION 24;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCICMSSTITCOMPRA POSITION 25;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEICMSSTITCOMPRA POSITION 26;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRICMSSTITCOMPRA POSITION 27;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCIPIITCOMPRA POSITION 28;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEIPIITCOMPRA POSITION 29;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRIPIITCOMPRA POSITION 30;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEFUNRURALITCOMPRA POSITION 31;

ALTER TABLE CPITCOMPRA ALTER COLUMN ALIQFUNRURALITCOMPRA POSITION 32;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFUNRURALITCOMPRA POSITION 33;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRLIQITCOMPRA POSITION 34;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRADICITCOMPRA POSITION 35;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFRETEITCOMPRA POSITION 36;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRISENTASITCOMPRA POSITION 37;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLROUTRASITCOMPRA POSITION 38;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRPRODITCOMPRA POSITION 39;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOITCOMPRA POSITION 40;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOVDITCOMPRA POSITION 41;

ALTER TABLE CPITCOMPRA ALTER COLUMN REFPROD POSITION 42;

ALTER TABLE CPITCOMPRA ALTER COLUMN OBSITCOMPRA POSITION 43;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPIF POSITION 44;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALIF POSITION 45;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFISC POSITION 46;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMMANUT POSITION 47;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODITFISC POSITION 48;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPNS POSITION 49;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALNS POSITION 50;

ALTER TABLE CPITCOMPRA ALTER COLUMN NUMSERIETMP POSITION 51;

ALTER TABLE CPITCOMPRA ALTER COLUMN NADICAO POSITION 52;

ALTER TABLE CPITCOMPRA ALTER COLUMN SEQADIC POSITION 53;

ALTER TABLE CPITCOMPRA ALTER COLUMN DESCDI POSITION 54;

ALTER TABLE CPITCOMPRA ALTER COLUMN APROVPRECO POSITION 55;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMITITCOMPRA POSITION 56;

ALTER TABLE CPITCOMPRA ALTER COLUMN ALIQISSITCOMPRA POSITION 57;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRISSITCOMPRA POSITION 58;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRIIITCOMPRA POSITION 59;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRITOUTRASDESPITCOMPRA POSITION 60;

ALTER TABLE CPITCOMPRA ALTER COLUMN CALCCUSTO POSITION 61;

ALTER TABLE CPITCOMPRA ALTER COLUMN ADICICMSTOTNOTA POSITION 62;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRTXSISCOMEXITCOMPRA POSITION 63;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTINS POSITION 64;

ALTER TABLE CPITCOMPRA ALTER COLUMN HINS POSITION 65;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUINS POSITION 66;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTALT POSITION 67;

ALTER TABLE CPITCOMPRA ALTER COLUMN HALT POSITION 68;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUALT POSITION 69;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODIMP POSITION 3;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODITIMP POSITION 4;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODEMPPD POSITION 5;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODFILIALPD POSITION 6;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODPROD POSITION 7;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN REFPROD POSITION 8;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN QTD POSITION 9;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODEMPUN POSITION 10;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODFILIALUN POSITION 11;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODUNID POSITION 12;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PESOLIQUIDO POSITION 13;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PESOBRUTO POSITION 14;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PRECOMI POSITION 15;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PRECO POSITION 16;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VMLEMI POSITION 17;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VMLDMI POSITION 18;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VMLE POSITION 19;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VMLD POSITION 20;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRFRETEMI POSITION 21;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRFRETE POSITION 22;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRSEGUROMI POSITION 23;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRSEGURO POSITION 24;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRTHCMI POSITION 25;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRTHC POSITION 26;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRADMI POSITION 27;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRAD POSITION 28;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQICMSIMP POSITION 29;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQICMSUF POSITION 30;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PERCDIFERICMS POSITION 31;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN PERCCREDPRESIMP POSITION 32;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQIPI POSITION 33;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQPIS POSITION 34;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQCOFINS POSITION 35;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN ALIQII POSITION 36;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRII POSITION 37;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRIPI POSITION 38;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRPIS POSITION 39;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRCOFINS POSITION 40;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRBASEICMS POSITION 41;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRICMS POSITION 42;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRICMSDIFERIDO POSITION 43;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRICMSDEVIDO POSITION 44;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRICMSCREDPRESUM POSITION 45;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRICMSRECOLHIMENTO POSITION 46;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRITDESPAD POSITION 47;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRTXSISCOMEX POSITION 48;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRVMCV POSITION 49;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN VLRCOMPL POSITION 50;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODEMPCF POSITION 51;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODFILIALCF POSITION 52;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODFISC POSITION 53;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODITFISC POSITION 54;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN CODNCM POSITION 55;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN SEQADIC POSITION 56;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN EMMANUT POSITION 57;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN DTINS POSITION 58;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN HINS POSITION 59;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN IDUSUINS POSITION 60;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN DTALT POSITION 61;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN HALT POSITION 62;

ALTER TABLE CPITIMPORTACAO ALTER COLUMN IDUSUALT POSITION 63;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CRFICHAAVAL ALTER COLUMN SEQFICHAAVAL POSITION 3;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODEMPCO POSITION 4;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODFILIALCO POSITION 5;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODCTO POSITION 6;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODEMPMA POSITION 7;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODFILIALMA POSITION 8;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODMOTAVAL POSITION 9;

ALTER TABLE CRFICHAAVAL ALTER COLUMN DTFICHAAVAL POSITION 10;

ALTER TABLE CRFICHAAVAL ALTER COLUMN LOCALFICHAAVAL POSITION 11;

ALTER TABLE CRFICHAAVAL ALTER COLUMN FINALICRIFICHAAVAL POSITION 12;

ALTER TABLE CRFICHAAVAL ALTER COLUMN FINALIANIFICHAAVAL POSITION 13;

ALTER TABLE CRFICHAAVAL ALTER COLUMN FINALIOUTFICHAAVAL POSITION 14;

ALTER TABLE CRFICHAAVAL ALTER COLUMN PREDENTRFICHAAVAL POSITION 15;

ALTER TABLE CRFICHAAVAL ALTER COLUMN ANDARFICHAAVAL POSITION 16;

ALTER TABLE CRFICHAAVAL ALTER COLUMN COBERTFICHAAVAL POSITION 17;

ALTER TABLE CRFICHAAVAL ALTER COLUMN ESTRUTFICHAAVAL POSITION 18;

ALTER TABLE CRFICHAAVAL ALTER COLUMN OCUPADOFICHAAVAL POSITION 19;

ALTER TABLE CRFICHAAVAL ALTER COLUMN MOBILFICHAAVAL POSITION 20;

ALTER TABLE CRFICHAAVAL ALTER COLUMN JANELAFICHAAVAL POSITION 21;

ALTER TABLE CRFICHAAVAL ALTER COLUMN QTDJANELAFICHAAVAL POSITION 22;

ALTER TABLE CRFICHAAVAL ALTER COLUMN SACADAFICHAAVAL POSITION 23;

ALTER TABLE CRFICHAAVAL ALTER COLUMN QTDSACADAFICHAAVAL POSITION 24;

ALTER TABLE CRFICHAAVAL ALTER COLUMN OUTROSFICHAAVAL POSITION 25;

ALTER TABLE CRFICHAAVAL ALTER COLUMN DTVISITAFICHAAVAL POSITION 26;

ALTER TABLE CRFICHAAVAL ALTER COLUMN HVISITAFICHAAVAL POSITION 27;

ALTER TABLE CRFICHAAVAL ALTER COLUMN DESCOUTROSFICHAAVAL POSITION 28;

ALTER TABLE CRFICHAAVAL ALTER COLUMN OBSFICHAAVAL POSITION 29;

ALTER TABLE CRFICHAAVAL ALTER COLUMN PONTOREFFICHAAVAL POSITION 30;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODEMPVD POSITION 31;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODFILIALVD POSITION 32;

ALTER TABLE CRFICHAAVAL ALTER COLUMN CODVEND POSITION 33;

ALTER TABLE CRFICHAAVAL ALTER COLUMN DTINS POSITION 34;

ALTER TABLE CRFICHAAVAL ALTER COLUMN HINS POSITION 35;

ALTER TABLE CRFICHAAVAL ALTER COLUMN IDUSUINS POSITION 36;

ALTER TABLE CRFICHAAVAL ALTER COLUMN DTALT POSITION 37;

ALTER TABLE CRFICHAAVAL ALTER COLUMN HALT POSITION 38;

ALTER TABLE CRFICHAAVAL ALTER COLUMN IDUSUALT POSITION 39;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQFICHAAVAL POSITION 3;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITFICHAAVAL POSITION 4;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPAM POSITION 5;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALAM POSITION 6;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODAMBAVAL POSITION 7;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN DESCITFICHAAVAL POSITION 8;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPPD POSITION 9;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALPD POSITION 10;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODPROD POSITION 11;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN ALTITFICHAAVAL POSITION 12;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN COMPITFICHAAVAL POSITION 13;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN TIRITFICHAAVAL POSITION 14;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN M2ITFICHAAVAL POSITION 15;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN VLRUNITITFICHAAVAL POSITION 16;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN VLRTOTITFICHAAVAL POSITION 17;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV1 POSITION 18;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV1 POSITION 19;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG1 POSITION 20;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG1 POSITION 21;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV2 POSITION 22;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV2 POSITION 23;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG2 POSITION 24;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG2 POSITION 25;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV3 POSITION 26;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV3 POSITION 27;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG3 POSITION 28;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG3 POSITION 29;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV4 POSITION 30;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV4 POSITION 31;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG4 POSITION 32;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG4 POSITION 33;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV5 POSITION 34;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV5 POSITION 35;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG5 POSITION 36;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG5 POSITION 37;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV6 POSITION 38;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV6 POSITION 39;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG6 POSITION 40;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG6 POSITION 41;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV7 POSITION 42;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV7 POSITION 43;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG7 POSITION 44;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG7 POSITION 45;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODEMPV8 POSITION 46;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODFILIALV8 POSITION 47;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN CODVARG8 POSITION 48;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN SEQITVARG8 POSITION 49;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN DTINS POSITION 50;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN HINS POSITION 51;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN IDUSUINS POSITION 52;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN DTALT POSITION 53;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN HALT POSITION 54;

ALTER TABLE CRITFICHAAVAL ALTER COLUMN IDUSUALT POSITION 55;

ALTER TABLE EQINVPROD ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQINVPROD ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQINVPROD ALTER COLUMN CODINVPROD POSITION 3;

ALTER TABLE EQINVPROD ALTER COLUMN CODEMPPD POSITION 4;

ALTER TABLE EQINVPROD ALTER COLUMN CODFILIALPD POSITION 5;

ALTER TABLE EQINVPROD ALTER COLUMN CODPROD POSITION 6;

ALTER TABLE EQINVPROD ALTER COLUMN CODEMPLE POSITION 7;

ALTER TABLE EQINVPROD ALTER COLUMN CODFILIALLE POSITION 8;

ALTER TABLE EQINVPROD ALTER COLUMN CODLOTE POSITION 9;

ALTER TABLE EQINVPROD ALTER COLUMN CODEMPTM POSITION 10;

ALTER TABLE EQINVPROD ALTER COLUMN CODFILIALTM POSITION 11;

ALTER TABLE EQINVPROD ALTER COLUMN CODTIPOMOV POSITION 12;

ALTER TABLE EQINVPROD ALTER COLUMN DATAINVP POSITION 13;

ALTER TABLE EQINVPROD ALTER COLUMN QTDINVP POSITION 14;

ALTER TABLE EQINVPROD ALTER COLUMN PRECOINVP POSITION 15;

ALTER TABLE EQINVPROD ALTER COLUMN CODEMPAX POSITION 16;

ALTER TABLE EQINVPROD ALTER COLUMN CODFILIALAX POSITION 17;

ALTER TABLE EQINVPROD ALTER COLUMN CODALMOX POSITION 18;

ALTER TABLE EQINVPROD ALTER COLUMN REFPROD POSITION 19;

ALTER TABLE EQINVPROD ALTER COLUMN SLDATUALINVP POSITION 20;

ALTER TABLE EQINVPROD ALTER COLUMN SLDDIGINVP POSITION 21;

ALTER TABLE EQINVPROD ALTER COLUMN FLAG POSITION 22;

ALTER TABLE EQINVPROD ALTER COLUMN OBSINVP POSITION 23;

ALTER TABLE EQINVPROD ALTER COLUMN EMMANUT POSITION 24;

ALTER TABLE EQINVPROD ALTER COLUMN DTINS POSITION 25;

ALTER TABLE EQINVPROD ALTER COLUMN HINS POSITION 26;

ALTER TABLE EQINVPROD ALTER COLUMN IDUSUINS POSITION 27;

ALTER TABLE EQINVPROD ALTER COLUMN DTALT POSITION 28;

ALTER TABLE EQINVPROD ALTER COLUMN HALT POSITION 29;

ALTER TABLE EQINVPROD ALTER COLUMN IDUSUALT POSITION 30;

ALTER TABLE EQITRECMERC ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQITRECMERC ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQITRECMERC ALTER COLUMN TICKET POSITION 3;

ALTER TABLE EQITRECMERC ALTER COLUMN CODITRECMERC POSITION 4;

ALTER TABLE EQITRECMERC ALTER COLUMN DESCITRECMERC POSITION 5;

ALTER TABLE EQITRECMERC ALTER COLUMN CODEMPPD POSITION 6;

ALTER TABLE EQITRECMERC ALTER COLUMN CODFILIALPD POSITION 7;

ALTER TABLE EQITRECMERC ALTER COLUMN CODPROD POSITION 8;

ALTER TABLE EQITRECMERC ALTER COLUMN CODEMPNS POSITION 9;

ALTER TABLE EQITRECMERC ALTER COLUMN CODFILIALNS POSITION 10;

ALTER TABLE EQITRECMERC ALTER COLUMN NUMSERIE POSITION 11;

ALTER TABLE EQITRECMERC ALTER COLUMN GARANTIA POSITION 12;

ALTER TABLE EQITRECMERC ALTER COLUMN REFPROD POSITION 13;

ALTER TABLE EQITRECMERC ALTER COLUMN CODEMPTP POSITION 14;

ALTER TABLE EQITRECMERC ALTER COLUMN CODFILIALTP POSITION 15;

ALTER TABLE EQITRECMERC ALTER COLUMN CODTIPORECMERC POSITION 16;

ALTER TABLE EQITRECMERC ALTER COLUMN CODPROCRECMERC POSITION 17;

ALTER TABLE EQITRECMERC ALTER COLUMN MEDIAAMOSTRAGEM POSITION 18;

ALTER TABLE EQITRECMERC ALTER COLUMN RENDAAMOSTRAGEM POSITION 19;

ALTER TABLE EQITRECMERC ALTER COLUMN QTDITRECMERC POSITION 20;

ALTER TABLE EQITRECMERC ALTER COLUMN STATUSITRECMERC POSITION 21;

ALTER TABLE EQITRECMERC ALTER COLUMN DEFEITOITRECMERC POSITION 22;

ALTER TABLE EQITRECMERC ALTER COLUMN CONDICOESITRECMERC POSITION 23;

ALTER TABLE EQITRECMERC ALTER COLUMN SERVICOSOLICITRECMERC POSITION 24;

ALTER TABLE EQITRECMERC ALTER COLUMN OBSITRECMERC POSITION 25;

ALTER TABLE EQITRECMERC ALTER COLUMN PLACAVEICULO POSITION 26;

ALTER TABLE EQITRECMERC ALTER COLUMN NROFROTA POSITION 27;

ALTER TABLE EQITRECMERC ALTER COLUMN GARAGEM POSITION 28;

ALTER TABLE EQITRECMERC ALTER COLUMN DTINS POSITION 29;

ALTER TABLE EQITRECMERC ALTER COLUMN HINS POSITION 30;

ALTER TABLE EQITRECMERC ALTER COLUMN IDUSUINS POSITION 31;

ALTER TABLE EQITRECMERC ALTER COLUMN DTALT POSITION 32;

ALTER TABLE EQITRECMERC ALTER COLUMN HALT POSITION 33;

ALTER TABLE EQITRECMERC ALTER COLUMN IDUSUALT POSITION 34;

ALTER TABLE EQITRMA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQITRMA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQITRMA ALTER COLUMN CODRMA POSITION 3;

ALTER TABLE EQITRMA ALTER COLUMN CODITRMA POSITION 4;

ALTER TABLE EQITRMA ALTER COLUMN CODEMPPD POSITION 5;

ALTER TABLE EQITRMA ALTER COLUMN CODFILIALPD POSITION 6;

ALTER TABLE EQITRMA ALTER COLUMN CODPROD POSITION 7;

ALTER TABLE EQITRMA ALTER COLUMN QTDITRMA POSITION 8;

ALTER TABLE EQITRMA ALTER COLUMN CODEMPAX POSITION 9;

ALTER TABLE EQITRMA ALTER COLUMN CODFILIALAX POSITION 10;

ALTER TABLE EQITRMA ALTER COLUMN CODALMOX POSITION 11;

ALTER TABLE EQITRMA ALTER COLUMN QTDAPROVITRMA POSITION 12;

ALTER TABLE EQITRMA ALTER COLUMN QTDEXPITRMA POSITION 13;

ALTER TABLE EQITRMA ALTER COLUMN CODEMPLE POSITION 14;

ALTER TABLE EQITRMA ALTER COLUMN CODFILIALLE POSITION 15;

ALTER TABLE EQITRMA ALTER COLUMN CODLOTE POSITION 16;

ALTER TABLE EQITRMA ALTER COLUMN REFPROD POSITION 17;

ALTER TABLE EQITRMA ALTER COLUMN DTAPROVITRMA POSITION 18;

ALTER TABLE EQITRMA ALTER COLUMN DTAEXPITRMA POSITION 19;

ALTER TABLE EQITRMA ALTER COLUMN PRECOITRMA POSITION 20;

ALTER TABLE EQITRMA ALTER COLUMN SITITRMA POSITION 21;

ALTER TABLE EQITRMA ALTER COLUMN SITAPROVITRMA POSITION 22;

ALTER TABLE EQITRMA ALTER COLUMN SITEXPITRMA POSITION 23;

ALTER TABLE EQITRMA ALTER COLUMN MOTIVOCANCITRMA POSITION 24;

ALTER TABLE EQITRMA ALTER COLUMN PRIORITRMA POSITION 25;

ALTER TABLE EQITRMA ALTER COLUMN MOTIVOPRIORITRMA POSITION 26;

ALTER TABLE EQITRMA ALTER COLUMN CODEMPOS POSITION 27;

ALTER TABLE EQITRMA ALTER COLUMN CODFILIALOS POSITION 28;

ALTER TABLE EQITRMA ALTER COLUMN TICKET POSITION 29;

ALTER TABLE EQITRMA ALTER COLUMN CODITRECMERC POSITION 30;

ALTER TABLE EQITRMA ALTER COLUMN CODITOS POSITION 31;

ALTER TABLE EQITRMA ALTER COLUMN EMMANUT POSITION 32;

ALTER TABLE EQITRMA ALTER COLUMN DTINS POSITION 33;

ALTER TABLE EQITRMA ALTER COLUMN HINS POSITION 34;

ALTER TABLE EQITRMA ALTER COLUMN IDUSUINS POSITION 35;

ALTER TABLE EQITRMA ALTER COLUMN DTALT POSITION 36;

ALTER TABLE EQITRMA ALTER COLUMN HALT POSITION 37;

ALTER TABLE EQITRMA ALTER COLUMN IDUSUALT POSITION 38;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQPRODUTO ALTER COLUMN CODPROD POSITION 3;

ALTER TABLE EQPRODUTO ALTER COLUMN DESCPROD POSITION 4;

ALTER TABLE EQPRODUTO ALTER COLUMN REFPROD POSITION 5;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPAX POSITION 6;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALAX POSITION 7;

ALTER TABLE EQPRODUTO ALTER COLUMN CODALMOX POSITION 8;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPMA POSITION 9;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALMA POSITION 10;

ALTER TABLE EQPRODUTO ALTER COLUMN CODMOEDA POSITION 11;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPUD POSITION 12;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALUD POSITION 13;

ALTER TABLE EQPRODUTO ALTER COLUMN CODUNID POSITION 14;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPFC POSITION 15;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALFC POSITION 16;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFISC POSITION 17;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPMC POSITION 18;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALMC POSITION 19;

ALTER TABLE EQPRODUTO ALTER COLUMN CODMARCA POSITION 20;

ALTER TABLE EQPRODUTO ALTER COLUMN DESCAUXPROD POSITION 21;

ALTER TABLE EQPRODUTO ALTER COLUMN TIPOPROD POSITION 22;

ALTER TABLE EQPRODUTO ALTER COLUMN CVPROD POSITION 23;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPGP POSITION 24;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALGP POSITION 25;

ALTER TABLE EQPRODUTO ALTER COLUMN CODGRUP POSITION 26;

ALTER TABLE EQPRODUTO ALTER COLUMN CODBARPROD POSITION 27;

ALTER TABLE EQPRODUTO ALTER COLUMN CLOTEPROD POSITION 28;

ALTER TABLE EQPRODUTO ALTER COLUMN SERIEPROD POSITION 29;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFABPROD POSITION 30;

ALTER TABLE EQPRODUTO ALTER COLUMN COMISPROD POSITION 31;

ALTER TABLE EQPRODUTO ALTER COLUMN PESOLIQPROD POSITION 32;

ALTER TABLE EQPRODUTO ALTER COLUMN PESOBRUTPROD POSITION 33;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDMINPROD POSITION 34;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDMAXPROD POSITION 35;

ALTER TABLE EQPRODUTO ALTER COLUMN DTMPMPROD POSITION 36;

ALTER TABLE EQPRODUTO ALTER COLUMN CUSTOMPMPROD POSITION 37;

ALTER TABLE EQPRODUTO ALTER COLUMN CUSTOPEPSPROD POSITION 38;

ALTER TABLE EQPRODUTO ALTER COLUMN CUSTOINFOPROD POSITION 39;

ALTER TABLE EQPRODUTO ALTER COLUMN PRECOBASEPROD POSITION 40;

ALTER TABLE EQPRODUTO ALTER COLUMN PRECOCOMISPROD POSITION 41;

ALTER TABLE EQPRODUTO ALTER COLUMN SLDPROD POSITION 42;

ALTER TABLE EQPRODUTO ALTER COLUMN SLDRESPROD POSITION 43;

ALTER TABLE EQPRODUTO ALTER COLUMN SLDCONSIGPROD POSITION 44;

ALTER TABLE EQPRODUTO ALTER COLUMN SLDLIQPROD POSITION 45;

ALTER TABLE EQPRODUTO ALTER COLUMN ATIVOPROD POSITION 46;

ALTER TABLE EQPRODUTO ALTER COLUMN DTULTCPPROD POSITION 47;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDULTCPPROD POSITION 48;

ALTER TABLE EQPRODUTO ALTER COLUMN DESCCOMPPROD POSITION 49;

ALTER TABLE EQPRODUTO ALTER COLUMN OBSPROD POSITION 50;

ALTER TABLE EQPRODUTO ALTER COLUMN VERIFPROD POSITION 51;

ALTER TABLE EQPRODUTO ALTER COLUMN LOCALPROD POSITION 52;

ALTER TABLE EQPRODUTO ALTER COLUMN RMAPROD POSITION 53;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPPE POSITION 54;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALPE POSITION 55;

ALTER TABLE EQPRODUTO ALTER COLUMN CODPE POSITION 56;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPCC POSITION 57;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALCC POSITION 58;

ALTER TABLE EQPRODUTO ALTER COLUMN ANOCC POSITION 59;

ALTER TABLE EQPRODUTO ALTER COLUMN CODCC POSITION 60;

ALTER TABLE EQPRODUTO ALTER COLUMN USARECEITAPROD POSITION 61;

ALTER TABLE EQPRODUTO ALTER COLUMN USATELAADICPDV POSITION 62;

ALTER TABLE EQPRODUTO ALTER COLUMN VLRDENSIDADE POSITION 63;

ALTER TABLE EQPRODUTO ALTER COLUMN VLRCONCENT POSITION 64;

ALTER TABLE EQPRODUTO ALTER COLUMN COMPRIMENTO POSITION 65;

ALTER TABLE EQPRODUTO ALTER COLUMN LARGURA POSITION 66;

ALTER TABLE EQPRODUTO ALTER COLUMN ESPESSURA POSITION 67;

ALTER TABLE EQPRODUTO ALTER COLUMN GUIATRAFPROD POSITION 68;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDEMBALAGEM POSITION 69;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEANPROD POSITION 70;

ALTER TABLE EQPRODUTO ALTER COLUMN CUBAGEM POSITION 71;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPSC POSITION 72;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALSC POSITION 73;

ALTER TABLE EQPRODUTO ALTER COLUMN CODSECAO POSITION 74;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPTC POSITION 75;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALTC POSITION 76;

ALTER TABLE EQPRODUTO ALTER COLUMN CODTPCHAMADO POSITION 77;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDHORASSERV POSITION 78;

ALTER TABLE EQPRODUTO ALTER COLUMN NRODIASVALID POSITION 79;

ALTER TABLE EQPRODUTO ALTER COLUMN DESCCLI POSITION 80;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDPORPLANO POSITION 81;

ALTER TABLE EQPRODUTO ALTER COLUMN NROPLANOS POSITION 82;

ALTER TABLE EQPRODUTO ALTER COLUMN CERTFSC POSITION 83;

ALTER TABLE EQPRODUTO ALTER COLUMN FATORFSC POSITION 84;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPOG POSITION 85;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALOG POSITION 86;

ALTER TABLE EQPRODUTO ALTER COLUMN CODPRODOG POSITION 87;

ALTER TABLE EQPRODUTO ALTER COLUMN DTINS POSITION 88;

ALTER TABLE EQPRODUTO ALTER COLUMN HINS POSITION 89;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUINS POSITION 90;

ALTER TABLE EQPRODUTO ALTER COLUMN DTALT POSITION 91;

ALTER TABLE EQPRODUTO ALTER COLUMN HALT POSITION 92;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUALT POSITION 93;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODTIPOMOV POSITION 3;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPMN POSITION 4;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALMN POSITION 5;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODMODNOTA POSITION 6;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPSE POSITION 7;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALSE POSITION 8;

ALTER TABLE EQTIPOMOV ALTER COLUMN SERIE POSITION 9;

ALTER TABLE EQTIPOMOV ALTER COLUMN DESCTIPOMOV POSITION 10;

ALTER TABLE EQTIPOMOV ALTER COLUMN ESTIPOMOV POSITION 11;

ALTER TABLE EQTIPOMOV ALTER COLUMN FISCALTIPOMOV POSITION 12;

ALTER TABLE EQTIPOMOV ALTER COLUMN ESTOQTIPOMOV POSITION 13;

ALTER TABLE EQTIPOMOV ALTER COLUMN TIPOMOV POSITION 14;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPTB POSITION 15;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALTB POSITION 16;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODTAB POSITION 17;

ALTER TABLE EQTIPOMOV ALTER COLUMN ESPECIETIPOMOV POSITION 18;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPTM POSITION 19;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALTM POSITION 20;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODTIPOMOVTM POSITION 21;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPTC POSITION 22;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALTC POSITION 23;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODTIPOMOVTC POSITION 24;

ALTER TABLE EQTIPOMOV ALTER COLUMN IMPPEDTIPOMOV POSITION 25;

ALTER TABLE EQTIPOMOV ALTER COLUMN IMPNFTIPOMOV POSITION 26;

ALTER TABLE EQTIPOMOV ALTER COLUMN IMPBOLTIPOMOV POSITION 27;

ALTER TABLE EQTIPOMOV ALTER COLUMN IMPRECTIPOMOV POSITION 28;

ALTER TABLE EQTIPOMOV ALTER COLUMN REIMPNFTIPOMOV POSITION 29;

ALTER TABLE EQTIPOMOV ALTER COLUMN TUSUTIPOMOV POSITION 30;

ALTER TABLE EQTIPOMOV ALTER COLUMN SOMAVDTIPOMOV POSITION 31;

ALTER TABLE EQTIPOMOV ALTER COLUMN SEQNFTIPOMOV POSITION 32;

ALTER TABLE EQTIPOMOV ALTER COLUMN VLRMFINTIPOMOV POSITION 33;

ALTER TABLE EQTIPOMOV ALTER COLUMN MCOMISTIPOMOV POSITION 34;

ALTER TABLE EQTIPOMOV ALTER COLUMN OPERTIPOMOV POSITION 35;

ALTER TABLE EQTIPOMOV ALTER COLUMN CTIPOFRETE POSITION 36;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPRC POSITION 37;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALRC POSITION 38;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODREGRCOMIS POSITION 39;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPTN POSITION 40;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALTN POSITION 41;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODTRAN POSITION 42;

ALTER TABLE EQTIPOMOV ALTER COLUMN EMITNFCPMOV POSITION 43;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPDF POSITION 44;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALDF POSITION 45;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODMODDOCFISC POSITION 46;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPPP POSITION 47;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALPP POSITION 48;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODPLANOPAG POSITION 49;

ALTER TABLE EQTIPOMOV ALTER COLUMN DESCNATCOMPL POSITION 50;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODEMPMC POSITION 51;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODFILIALMC POSITION 52;

ALTER TABLE EQTIPOMOV ALTER COLUMN CODMENS POSITION 53;

ALTER TABLE EQTIPOMOV ALTER COLUMN DTINS POSITION 54;

ALTER TABLE EQTIPOMOV ALTER COLUMN HINS POSITION 55;

ALTER TABLE EQTIPOMOV ALTER COLUMN IDUSUINS POSITION 56;

ALTER TABLE EQTIPOMOV ALTER COLUMN DTALT POSITION 57;

ALTER TABLE EQTIPOMOV ALTER COLUMN HALT POSITION 58;

ALTER TABLE EQTIPOMOV ALTER COLUMN IDUSUALT POSITION 59;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN CODCALC POSITION 3;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN SEQITCALC POSITION 4;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN SIGLACALC POSITION 5;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN OPERACAOCALC POSITION 6;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN DTINS POSITION 7;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN HINS POSITION 8;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN IDUSUINS POSITION 9;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN DTALT POSITION 10;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN HALT POSITION 11;

ALTER TABLE LFITCALCCUSTO ALTER COLUMN IDUSUALT POSITION 12;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFISC POSITION 3;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODITFISC POSITION 4;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ORIGFISC POSITION 5;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPTT POSITION 6;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALTT POSITION 7;

ALTER TABLE LFITCLFISCAL ALTER COLUMN TIPOFISC POSITION 8;

ALTER TABLE LFITCLFISCAL ALTER COLUMN TPREDICMSFISC POSITION 9;

ALTER TABLE LFITCLFISCAL ALTER COLUMN REDFISC POSITION 10;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODTRATTRIB POSITION 11;

ALTER TABLE LFITCLFISCAL ALTER COLUMN NOUFITFISC POSITION 12;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPFC POSITION 13;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALFC POSITION 14;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFISCCLI POSITION 15;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQFISC POSITION 16;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQFISCINTRA POSITION 17;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQLFISC POSITION 18;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQIPIFISC POSITION 19;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQPISFISC POSITION 20;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQCOFINSFISC POSITION 21;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQCSOCIALFISC POSITION 22;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQIRFISC POSITION 23;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQFUNRURALFISC POSITION 24;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQIIFISC POSITION 25;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPME POSITION 26;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALME POSITION 27;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODMENS POSITION 28;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPTM POSITION 29;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALTM POSITION 30;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODTIPOMOV POSITION 31;

ALTER TABLE LFITCLFISCAL ALTER COLUMN TIPOST POSITION 32;

ALTER TABLE LFITCLFISCAL ALTER COLUMN MARGEMVLAGR POSITION 33;

ALTER TABLE LFITCLFISCAL ALTER COLUMN GERALFISC POSITION 34;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPSP POSITION 35;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALSP POSITION 36;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODSITTRIBPIS POSITION 37;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IMPSITTRIBPIS POSITION 38;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPSC POSITION 39;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALSC POSITION 40;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODSITTRIBCOF POSITION 41;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IMPSITTRIBCOF POSITION 42;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPSI POSITION 43;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALSI POSITION 44;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODSITTRIBIPI POSITION 45;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IMPSITTRIBIPI POSITION 46;

ALTER TABLE LFITCLFISCAL ALTER COLUMN TPCALCIPI POSITION 47;

ALTER TABLE LFITCLFISCAL ALTER COLUMN VLRIPIUNIDTRIB POSITION 48;

ALTER TABLE LFITCLFISCAL ALTER COLUMN MODBCICMS POSITION 49;

ALTER TABLE LFITCLFISCAL ALTER COLUMN MODBCICMSST POSITION 50;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODPAIS POSITION 51;

ALTER TABLE LFITCLFISCAL ALTER COLUMN SIGLAUF POSITION 52;

ALTER TABLE LFITCLFISCAL ALTER COLUMN VLRPISUNIDTRIB POSITION 53;

ALTER TABLE LFITCLFISCAL ALTER COLUMN VLRCOFUNIDTRIB POSITION 54;

ALTER TABLE LFITCLFISCAL ALTER COLUMN TIPOUSOITFISC POSITION 55;

ALTER TABLE LFITCLFISCAL ALTER COLUMN REDBASEST POSITION 56;

ALTER TABLE LFITCLFISCAL ALTER COLUMN REDBASEFRETE POSITION 57;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPRA POSITION 58;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALRA POSITION 59;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODREGRA POSITION 60;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPIS POSITION 61;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALIS POSITION 62;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODSITTRIBISS POSITION 63;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IMPSITTRIBISS POSITION 64;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQISSFISC POSITION 65;

ALTER TABLE LFITCLFISCAL ALTER COLUMN RETENSAOISS POSITION 66;

ALTER TABLE LFITCLFISCAL ALTER COLUMN INDAPURIPI POSITION 67;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPCN POSITION 68;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALCN POSITION 69;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CSOSN POSITION 70;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQICMSIMP POSITION 71;

ALTER TABLE LFITCLFISCAL ALTER COLUMN PERCCREDPRESIMP POSITION 72;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ADICIPIBASEICMS POSITION 73;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ADICICMSTOTNOTA POSITION 74;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODEMPCC POSITION 75;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODFILIALCC POSITION 76;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CODCALC POSITION 77;

ALTER TABLE LFITCLFISCAL ALTER COLUMN ALIQICMSSTCM POSITION 78;

ALTER TABLE LFITCLFISCAL ALTER COLUMN CALCSTCM POSITION 79;

ALTER TABLE LFITCLFISCAL ALTER COLUMN DTINS POSITION 80;

ALTER TABLE LFITCLFISCAL ALTER COLUMN HINS POSITION 81;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IDUSUINS POSITION 82;

ALTER TABLE LFITCLFISCAL ALTER COLUMN DTALT POSITION 83;

ALTER TABLE LFITCLFISCAL ALTER COLUMN HALT POSITION 84;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IDUSUALT POSITION 85;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODPROD POSITION 3;

ALTER TABLE PPESTRUTURA ALTER COLUMN SEQEST POSITION 4;

ALTER TABLE PPESTRUTURA ALTER COLUMN DESCEST POSITION 5;

ALTER TABLE PPESTRUTURA ALTER COLUMN QTDEST POSITION 6;

ALTER TABLE PPESTRUTURA ALTER COLUMN REFPROD POSITION 7;

ALTER TABLE PPESTRUTURA ALTER COLUMN ATIVOEST POSITION 8;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODEMPML POSITION 9;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODFILIALML POSITION 10;

ALTER TABLE PPESTRUTURA ALTER COLUMN CODMODLOTE POSITION 11;

ALTER TABLE PPESTRUTURA ALTER COLUMN NRODIASVALID POSITION 12;

ALTER TABLE PPESTRUTURA ALTER COLUMN GLOTEOPP POSITION 13;

ALTER TABLE PPESTRUTURA ALTER COLUMN USADENSIDADEOP POSITION 14;

ALTER TABLE PPESTRUTURA ALTER COLUMN OBSERVACAO POSITION 15;

ALTER TABLE PPESTRUTURA ALTER COLUMN ESTDINAMICA POSITION 16;

ALTER TABLE PPESTRUTURA ALTER COLUMN DESPAUTO POSITION 17;

ALTER TABLE PPESTRUTURA ALTER COLUMN BLOQQTDPROD POSITION 18;

ALTER TABLE PPESTRUTURA ALTER COLUMN EXPEDIRRMA POSITION 19;

ALTER TABLE PPESTRUTURA ALTER COLUMN GERAROP POSITION 20;

ALTER TABLE PPESTRUTURA ALTER COLUMN DTINS POSITION 21;

ALTER TABLE PPESTRUTURA ALTER COLUMN HINS POSITION 22;

ALTER TABLE PPESTRUTURA ALTER COLUMN IDUSUINS POSITION 23;

ALTER TABLE PPESTRUTURA ALTER COLUMN DTALT POSITION 24;

ALTER TABLE PPESTRUTURA ALTER COLUMN HALT POSITION 25;

ALTER TABLE PPESTRUTURA ALTER COLUMN IDUSUALT POSITION 26;

ALTER TABLE PPITOP ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE PPITOP ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE PPITOP ALTER COLUMN CODOP POSITION 3;

ALTER TABLE PPITOP ALTER COLUMN SEQOP POSITION 4;

ALTER TABLE PPITOP ALTER COLUMN SEQITOP POSITION 5;

ALTER TABLE PPITOP ALTER COLUMN CODEMPPD POSITION 6;

ALTER TABLE PPITOP ALTER COLUMN CODFILIALPD POSITION 7;

ALTER TABLE PPITOP ALTER COLUMN CODPROD POSITION 8;

ALTER TABLE PPITOP ALTER COLUMN REFPROD POSITION 9;

ALTER TABLE PPITOP ALTER COLUMN QTDITOP POSITION 10;

ALTER TABLE PPITOP ALTER COLUMN CODEMPFS POSITION 11;

ALTER TABLE PPITOP ALTER COLUMN CODFILIALFS POSITION 12;

ALTER TABLE PPITOP ALTER COLUMN CODFASE POSITION 13;

ALTER TABLE PPITOP ALTER COLUMN CODEMPLE POSITION 14;

ALTER TABLE PPITOP ALTER COLUMN CODFILIALLE POSITION 15;

ALTER TABLE PPITOP ALTER COLUMN CODLOTE POSITION 16;

ALTER TABLE PPITOP ALTER COLUMN SEQITOPCP POSITION 17;

ALTER TABLE PPITOP ALTER COLUMN CODLOTERAT POSITION 18;

ALTER TABLE PPITOP ALTER COLUMN QTDCOPIAITOP POSITION 19;

ALTER TABLE PPITOP ALTER COLUMN GERARMA POSITION 20;

ALTER TABLE PPITOP ALTER COLUMN SEQAC POSITION 21;

ALTER TABLE PPITOP ALTER COLUMN BLOQOP POSITION 22;

ALTER TABLE PPITOP ALTER COLUMN PERMITEAJUSTEITOP POSITION 23;

ALTER TABLE PPITOP ALTER COLUMN TIPOEXTERNO POSITION 24;

ALTER TABLE PPITOP ALTER COLUMN CODEMPVD POSITION 25;

ALTER TABLE PPITOP ALTER COLUMN CODFILIALVD POSITION 26;

ALTER TABLE PPITOP ALTER COLUMN TIPOVENDA POSITION 27;

ALTER TABLE PPITOP ALTER COLUMN CODVENDA POSITION 28;

ALTER TABLE PPITOP ALTER COLUMN CODEMPCP POSITION 29;

ALTER TABLE PPITOP ALTER COLUMN CODFILIALCP POSITION 30;

ALTER TABLE PPITOP ALTER COLUMN CODCOMPRA POSITION 31;

ALTER TABLE PPITOP ALTER COLUMN EMMANUT POSITION 32;

ALTER TABLE PPITOP ALTER COLUMN DTINS POSITION 33;

ALTER TABLE PPITOP ALTER COLUMN HINS POSITION 34;

ALTER TABLE PPITOP ALTER COLUMN IDUSUINS POSITION 35;

ALTER TABLE PPITOP ALTER COLUMN DTALT POSITION 36;

ALTER TABLE PPITOP ALTER COLUMN HALT POSITION 37;

ALTER TABLE PPITOP ALTER COLUMN IDUSUALT POSITION 38;

ALTER TABLE PPOP ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE PPOP ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE PPOP ALTER COLUMN CODOP POSITION 3;

ALTER TABLE PPOP ALTER COLUMN SEQOP POSITION 4;

ALTER TABLE PPOP ALTER COLUMN DTEMITOP POSITION 5;

ALTER TABLE PPOP ALTER COLUMN CODEMPPD POSITION 6;

ALTER TABLE PPOP ALTER COLUMN CODFILIALPD POSITION 7;

ALTER TABLE PPOP ALTER COLUMN CODPROD POSITION 8;

ALTER TABLE PPOP ALTER COLUMN SEQEST POSITION 9;

ALTER TABLE PPOP ALTER COLUMN REFPROD POSITION 10;

ALTER TABLE PPOP ALTER COLUMN DTFABROP POSITION 11;

ALTER TABLE PPOP ALTER COLUMN QTDSUGPRODOP POSITION 12;

ALTER TABLE PPOP ALTER COLUMN QTDPREVPRODOP POSITION 13;

ALTER TABLE PPOP ALTER COLUMN QTDFINALPRODOP POSITION 14;

ALTER TABLE PPOP ALTER COLUMN QTDDISTPOP POSITION 15;

ALTER TABLE PPOP ALTER COLUMN QTDDISTIOP POSITION 16;

ALTER TABLE PPOP ALTER COLUMN DTVALIDPDOP POSITION 17;

ALTER TABLE PPOP ALTER COLUMN CODEMPLE POSITION 18;

ALTER TABLE PPOP ALTER COLUMN CODFILIALLE POSITION 19;

ALTER TABLE PPOP ALTER COLUMN CODLOTE POSITION 20;

ALTER TABLE PPOP ALTER COLUMN CODEMPTM POSITION 21;

ALTER TABLE PPOP ALTER COLUMN CODFILIALTM POSITION 22;

ALTER TABLE PPOP ALTER COLUMN CODTIPOMOV POSITION 23;

ALTER TABLE PPOP ALTER COLUMN CODEMPAX POSITION 24;

ALTER TABLE PPOP ALTER COLUMN CODFILIALAX POSITION 25;

ALTER TABLE PPOP ALTER COLUMN CODALMOX POSITION 26;

ALTER TABLE PPOP ALTER COLUMN CODEMPOPM POSITION 27;

ALTER TABLE PPOP ALTER COLUMN CODFILIALOPM POSITION 28;

ALTER TABLE PPOP ALTER COLUMN CODOPM POSITION 29;

ALTER TABLE PPOP ALTER COLUMN SEQOPM POSITION 30;

ALTER TABLE PPOP ALTER COLUMN SITOP POSITION 31;

ALTER TABLE PPOP ALTER COLUMN OBSOP POSITION 32;

ALTER TABLE PPOP ALTER COLUMN JUSTFICQTDPROD POSITION 33;

ALTER TABLE PPOP ALTER COLUMN JUSTIFICCANC POSITION 34;

ALTER TABLE PPOP ALTER COLUMN DTCANC POSITION 35;

ALTER TABLE PPOP ALTER COLUMN IDUSUCANC POSITION 36;

ALTER TABLE PPOP ALTER COLUMN HCANC POSITION 37;

ALTER TABLE PPOP ALTER COLUMN CODEMPCP POSITION 38;

ALTER TABLE PPOP ALTER COLUMN CODFILIALCP POSITION 39;

ALTER TABLE PPOP ALTER COLUMN CODCOMPRA POSITION 40;

ALTER TABLE PPOP ALTER COLUMN CODITCOMPRA POSITION 41;

ALTER TABLE PPOP ALTER COLUMN ESTDINAMICA POSITION 42;

ALTER TABLE PPOP ALTER COLUMN GARANTIA POSITION 43;

ALTER TABLE PPOP ALTER COLUMN CODEMPOS POSITION 44;

ALTER TABLE PPOP ALTER COLUMN CODFILIALOS POSITION 45;

ALTER TABLE PPOP ALTER COLUMN TICKET POSITION 46;

ALTER TABLE PPOP ALTER COLUMN CODITRECMERC POSITION 47;

ALTER TABLE PPOP ALTER COLUMN CODITOS POSITION 48;

ALTER TABLE PPOP ALTER COLUMN EMMANUT POSITION 49;

ALTER TABLE PPOP ALTER COLUMN DTINS POSITION 50;

ALTER TABLE PPOP ALTER COLUMN HINS POSITION 51;

ALTER TABLE PPOP ALTER COLUMN IDUSUINS POSITION 52;

ALTER TABLE PPOP ALTER COLUMN DTALT POSITION 53;

ALTER TABLE PPOP ALTER COLUMN HALT POSITION 54;

ALTER TABLE PPOP ALTER COLUMN IDUSUALT POSITION 55;

ALTER TABLE SGESTACAO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGESTACAO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGESTACAO ALTER COLUMN CODEST POSITION 3;

ALTER TABLE SGESTACAO ALTER COLUMN DESCEST POSITION 4;

ALTER TABLE SGESTACAO ALTER COLUMN MODODEMOEST POSITION 5;

ALTER TABLE SGESTACAO ALTER COLUMN NFEEST POSITION 6;

ALTER TABLE SGESTACAO ALTER COLUMN TAMFONTETXT POSITION 7;

ALTER TABLE SGESTACAO ALTER COLUMN FONTETXT POSITION 8;

ALTER TABLE SGESTACAO ALTER COLUMN PATHCACERTS POSITION 9;

ALTER TABLE SGESTACAO ALTER COLUMN CODEMPPX POSITION 10;

ALTER TABLE SGESTACAO ALTER COLUMN CODFILIALPX POSITION 11;

ALTER TABLE SGESTACAO ALTER COLUMN CODPROXY POSITION 12;

ALTER TABLE SGESTACAO ALTER COLUMN DTINS POSITION 13;

ALTER TABLE SGESTACAO ALTER COLUMN HINS POSITION 14;

ALTER TABLE SGESTACAO ALTER COLUMN IDUSUINS POSITION 15;

ALTER TABLE SGESTACAO ALTER COLUMN DTALT POSITION 16;

ALTER TABLE SGESTACAO ALTER COLUMN HALT POSITION 17;

ALTER TABLE SGESTACAO ALTER COLUMN IDUSUALT POSITION 18;

ALTER TABLE SGOBJETO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGOBJETO ALTER COLUMN IDOBJ POSITION 2;

ALTER TABLE SGOBJETO ALTER COLUMN DESCOBJ POSITION 3;

ALTER TABLE SGOBJETO ALTER COLUMN TIPOOBJ POSITION 4;

ALTER TABLE SGOBJETO ALTER COLUMN COMENTOBJ POSITION 5;

ALTER TABLE SGOBJETO ALTER COLUMN USOMEOBJ POSITION 6;

ALTER TABLE SGOBJETO ALTER COLUMN SIGLAOBJ POSITION 7;

ALTER TABLE SGOBJETO ALTER COLUMN NIVELOBJ POSITION 8;

ALTER TABLE SGOBJETO ALTER COLUMN NUMREGOBJ POSITION 9;

ALTER TABLE SGOBJETO ALTER COLUMN DTINS POSITION 10;

ALTER TABLE SGOBJETO ALTER COLUMN HINS POSITION 11;

ALTER TABLE SGOBJETO ALTER COLUMN IDUSUINS POSITION 12;

ALTER TABLE SGOBJETO ALTER COLUMN DTALT POSITION 13;

ALTER TABLE SGOBJETO ALTER COLUMN HALT POSITION 14;

ALTER TABLE SGOBJETO ALTER COLUMN IDUSUALT POSITION 15;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAREFPROD POSITION 3;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV POSITION 4;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTM POSITION 5;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTM POSITION 6;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPEDSEQ POSITION 7;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAORCSEQ POSITION 8;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILTRO POSITION 9;

ALTER TABLE SGPREFERE1 ALTER COLUMN USALIQREL POSITION 10;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPRECOCUSTO POSITION 11;

ALTER TABLE SGPREFERE1 ALTER COLUMN ANOCENTROCUSTO POSITION 12;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSORCPAD POSITION 13;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV2 POSITION 14;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT2 POSITION 15;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT2 POSITION 16;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSORC POSITION 17;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSORCPD POSITION 18;

ALTER TABLE SGPREFERE1 ALTER COLUMN TITORCTXT01 POSITION 19;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV3 POSITION 20;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT3 POSITION 21;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT3 POSITION 22;

ALTER TABLE SGPREFERE1 ALTER COLUMN ORDNOTA POSITION 23;

ALTER TABLE SGPREFERE1 ALTER COLUMN SETORVENDA POSITION 24;

ALTER TABLE SGPREFERE1 ALTER COLUMN PREFCRED POSITION 25;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPREFCRED POSITION 26;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMO POSITION 27;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMO POSITION 28;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMOEDA POSITION 29;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV4 POSITION 30;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT4 POSITION 31;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT4 POSITION 32;

ALTER TABLE SGPREFERE1 ALTER COLUMN USACLASCOMIS POSITION 33;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERCPRECOCUSTO POSITION 34;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODGRUP POSITION 35;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALGP POSITION 36;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPGP POSITION 37;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMARCA POSITION 38;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMC POSITION 39;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMC POSITION 40;

ALTER TABLE SGPREFERE1 ALTER COLUMN RGCLIOBRIG POSITION 41;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABFRETEVD POSITION 42;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABADICVD POSITION 43;

ALTER TABLE SGPREFERE1 ALTER COLUMN TRAVATMNFVD POSITION 44;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOVALIDORC POSITION 45;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLIMESMOCNPJ POSITION 46;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTBJ POSITION 47;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTJ POSITION 48;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTJ POSITION 49;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJOBRIGCLI POSITION 50;

ALTER TABLE SGPREFERE1 ALTER COLUMN JUROSPOSCALC POSITION 51;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFR POSITION 52;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFR POSITION 53;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFOR POSITION 54;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTN POSITION 55;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTN POSITION 56;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTRAN POSITION 57;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTF POSITION 58;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTF POSITION 59;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFOR POSITION 60;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT5 POSITION 61;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT5 POSITION 62;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV5 POSITION 63;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTLOTNEG POSITION 64;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEG POSITION 65;

ALTER TABLE SGPREFERE1 ALTER COLUMN NATVENDA POSITION 66;

ALTER TABLE SGPREFERE1 ALTER COLUMN IPIVENDA POSITION 67;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOSICMS POSITION 68;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPG POSITION 69;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPG POSITION 70;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAG POSITION 71;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTB POSITION 72;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTB POSITION 73;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTAB POSITION 74;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPCE POSITION 75;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALCE POSITION 76;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODCLASCLI POSITION 77;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDEC POSITION 78;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECFIN POSITION 79;

ALTER TABLE SGPREFERE1 ALTER COLUMN COMISPDUPL POSITION 80;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT6 POSITION 81;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT6 POSITION 82;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV6 POSITION 83;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQVENDA POSITION 84;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQCOMPRA POSITION 85;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAMATPRIM POSITION 86;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAPATRIM POSITION 87;

ALTER TABLE SGPREFERE1 ALTER COLUMN PEPSPROD POSITION 88;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJFOROBRIG POSITION 89;

ALTER TABLE SGPREFERE1 ALTER COLUMN INSCESTFOROBRIG POSITION 90;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAPRODSIMILAR POSITION 91;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTIALMOX POSITION 92;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT8 POSITION 93;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT8 POSITION 94;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV8 POSITION 95;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEGGRUP POSITION 96;

ALTER TABLE SGPREFERE1 ALTER COLUMN USATABPE POSITION 97;

ALTER TABLE SGPREFERE1 ALTER COLUMN TAMDESCPROD POSITION 98;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCCOMPPED POSITION 99;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSCLIVEND POSITION 100;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONTESTOQ POSITION 101;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIASPEDT POSITION 102;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCVENDA POSITION 103;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCORC POSITION 104;

ALTER TABLE SGPREFERE1 ALTER COLUMN USALAYOUTPED POSITION 105;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERIFALTPARCVENDA POSITION 106;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACODPRODGEN POSITION 107;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENPROD POSITION 108;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENREF POSITION 109;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODBAR POSITION 110;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFAB POSITION 111;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFOR POSITION 112;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAVLRULTCOMPRA POSITION 113;

ALTER TABLE SGPREFERE1 ALTER COLUMN ICMSVENDA POSITION 114;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOZERO POSITION 115;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIMGASSORC POSITION 116;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMGASSORC POSITION 117;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTCPFCLI POSITION 118;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIECLI POSITION 119;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEFOR POSITION 120;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTECPFFOR POSITION 121;

ALTER TABLE SGPREFERE1 ALTER COLUMN USANOMEVENDORC POSITION 122;

ALTER TABLE SGPREFERE1 ALTER COLUMN SISCONTABIL POSITION 123;

ALTER TABLE SGPREFERE1 ALTER COLUMN ATBANCOIMPBOL POSITION 124;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCODBAR POSITION 125;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICORCOBSPED POSITION 126;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMENSORC POSITION 127;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMENSORC POSITION 128;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSORC POSITION 129;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOCOMPRA POSITION 130;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPCP POSITION 131;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPORC POSITION 132;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABSOLCP POSITION 133;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICFRETEBASEICM POSITION 134;

ALTER TABLE SGPREFERE1 ALTER COLUMN PRECOCPREL POSITION 135;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCORC POSITION 136;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTICOMIS POSITION 137;

ALTER TABLE SGPREFERE1 ALTER COLUMN USUATIVCLI POSITION 138;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTREC POSITION 139;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTREC POSITION 140;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTREC POSITION 141;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTPAG POSITION 142;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTPAG POSITION 143;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTPAG POSITION 144;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTC POSITION 145;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTC POSITION 146;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOCLI POSITION 147;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTITRECALTDTVENC POSITION 148;

ALTER TABLE SGPREFERE1 ALTER COLUMN LCREDGLOBAL POSITION 149;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDMANUTCOMOBRIG POSITION 150;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSPED POSITION 151;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCLASSPED POSITION 152;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGECLI POSITION 153;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGEFOR POSITION 154;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGETRANSP POSITION 155;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOMAVOLUMES POSITION 156;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACEP POSITION 157;

ALTER TABLE SGPREFERE1 ALTER COLUMN URLWSCEP POSITION 158;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSCP POSITION 159;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS01CP POSITION 160;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS02CP POSITION 161;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS03CP POSITION 162;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS04CP POSITION 163;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEPF POSITION 164;

ALTER TABLE SGPREFERE1 ALTER COLUMN CREDICMSSIMPLES POSITION 165;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMS POSITION 166;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMS POSITION 167;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSICMSSIMPLES POSITION 168;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACOMISVENDAORC POSITION 169;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACODUNIF POSITION 170;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV9 POSITION 171;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT9 POSITION 172;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT9 POSITION 173;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJP POSITION 174;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJP POSITION 175;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJP POSITION 176;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJR POSITION 177;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJR POSITION 178;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJR POSITION 179;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDR POSITION 180;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDR POSITION 181;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDR POSITION 182;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDC POSITION 183;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDC POSITION 184;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDC POSITION 185;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERAPAGEMIS POSITION 186;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCAFINCONTR POSITION 187;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCARMACONTR POSITION 188;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECPRE POSITION 189;

ALTER TABLE SGPREFERE1 ALTER COLUMN VISUALIZALUCR POSITION 190;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSNFE POSITION 191;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFE POSITION 192;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFELIN POSITION 193;

ALTER TABLE SGPREFERE1 ALTER COLUMN FORMATODANFE POSITION 194;

ALTER TABLE SGPREFERE1 ALTER COLUMN AMBIENTENFE POSITION 195;

ALTER TABLE SGPREFERE1 ALTER COLUMN PROCEMINFE POSITION 196;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERPROCNFE POSITION 197;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICNFE POSITION 198;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTONFE POSITION 199;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFADPRODNFE POSITION 200;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPNF POSITION 201;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALNF POSITION 202;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMAILNF POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN EXIBEPARCOBSDANFE POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERSAONFE POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN REGIMETRIBNFE POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 211;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 212;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 213;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 214;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 215;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOT POSITION 216;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOAPROV POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFT POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFT POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFORFT POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOMIS POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESPECIALCOMIS POSITION 222;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTS POSITION 223;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVS POSITION 224;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTS POSITION 225;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPSV POSITION 226;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALSV POSITION 227;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAGSV POSITION 228;

ALTER TABLE SGPREFERE1 ALTER COLUMN ARREDPRECO POSITION 229;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPC POSITION 230;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPC POSITION 231;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANPC POSITION 232;

ALTER TABLE SGPREFERE1 ALTER COLUMN TPNOSSONUMERO POSITION 233;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMPDOCBOL POSITION 234;

ALTER TABLE SGPREFERE1 ALTER COLUMN FECHACAIXA POSITION 235;

ALTER TABLE SGPREFERE1 ALTER COLUMN FECHACAIXAAUTO POSITION 236;

ALTER TABLE SGPREFERE1 ALTER COLUMN NUMDIGIDENTTIT POSITION 237;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICEFD POSITION 238;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTOEFD POSITION 239;

ALTER TABLE SGPREFERE1 ALTER COLUMN REVALIDARLOTECOMPRA POSITION 240;

ALTER TABLE SGPREFERE1 ALTER COLUMN ENCORCPROD POSITION 241;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIM POSITION 242;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIM POSITION 243;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVIM POSITION 244;

ALTER TABLE SGPREFERE1 ALTER COLUMN COMISSAODESCONTO POSITION 245;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHC POSITION 246;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHC POSITION 247;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTCNAB POSITION 248;

ALTER TABLE SGPREFERE1 ALTER COLUMN ALINHATELALANCA POSITION 249;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDACONSUM POSITION 250;

ALTER TABLE SGPREFERE1 ALTER COLUMN CVPROD POSITION 251;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERIFPROD POSITION 252;

ALTER TABLE SGPREFERE1 ALTER COLUMN RMAPROD POSITION 253;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPROD POSITION 254;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIG POSITION 255;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIG POSITION 256;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODIMG POSITION 257;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSITVENDAPED POSITION 258;

ALTER TABLE SGPREFERE1 ALTER COLUMN FATORCPARC POSITION 259;

ALTER TABLE SGPREFERE1 ALTER COLUMN APROVORCFATPARC POSITION 260;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQSEQICP POSITION 261;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQSEQIVD POSITION 262;

ALTER TABLE SGPREFERE1 ALTER COLUMN UTILORDCPINT POSITION 263;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICEPC POSITION 264;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTOEPC POSITION 265;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMPLOTENFE POSITION 266;

ALTER TABLE SGPREFERE1 ALTER COLUMN TOTCPSFRETE POSITION 267;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDENTCLIBCO POSITION 268;

ALTER TABLE SGPREFERE1 ALTER COLUMN QTDDESC POSITION 269;

ALTER TABLE SGPREFERE1 ALTER COLUMN LOCALSERV POSITION 270;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDPRODQQCLAS POSITION 271;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPVD POSITION 272;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALVD POSITION 273;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODVEND POSITION 274;

ALTER TABLE SGPREFERE1 ALTER COLUMN PADRAONFE POSITION 275;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPME POSITION 276;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALME POSITION 277;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSVENDA POSITION 278;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOEMISSAONFE POSITION 279;

ALTER TABLE SGPREFERE1 ALTER COLUMN CCNFECP POSITION 280;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICICMSTOTNOTA POSITION 281;

ALTER TABLE SGPREFERE1 ALTER COLUMN UTILIZATBCALCCA POSITION 282;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABCOMPRACOMPL POSITION 283;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIC POSITION 284;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIC POSITION 285;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVIC POSITION 286;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCNATCOMPL POSITION 287;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABLOGPAGAR POSITION 288;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABLOGRECEBER POSITION 289;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 290;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 291;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 292;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 293;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 294;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 295;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPMAIL POSITION 3;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPSSLMAIL POSITION 4;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPAUTMAIL POSITION 5;

ALTER TABLE SGPREFERE3 ALTER COLUMN PORTAMAIL POSITION 6;

ALTER TABLE SGPREFERE3 ALTER COLUMN USERMAIL POSITION 7;

ALTER TABLE SGPREFERE3 ALTER COLUMN PASSMAIL POSITION 8;

ALTER TABLE SGPREFERE3 ALTER COLUMN ENDMAIL POSITION 9;

ALTER TABLE SGPREFERE3 ALTER COLUMN AUTOHORATEND POSITION 10;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPCE POSITION 11;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALCE POSITION 12;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATIVCE POSITION 13;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPTE POSITION 14;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALTE POSITION 15;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATIVTE POSITION 16;

ALTER TABLE SGPREFERE3 ALTER COLUMN BLOQATENDCLIATRASO POSITION 17;

ALTER TABLE SGPREFERE3 ALTER COLUMN MOSTRACLIATRASO POSITION 18;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPNC POSITION 19;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALNC POSITION 20;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILNC POSITION 21;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPAO POSITION 22;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALAO POSITION 23;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATENDO POSITION 24;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPMI POSITION 25;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALMI POSITION 26;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELMI POSITION 27;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPME POSITION 28;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALME POSITION 29;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELME POSITION 30;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPFI POSITION 31;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALFI POSITION 32;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELFI POSITION 33;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPFJ POSITION 34;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALFJ POSITION 35;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELFJ POSITION 36;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPAP POSITION 37;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALAP POSITION 38;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELAP POSITION 39;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEC POSITION 40;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEC POSITION 41;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEC POSITION 42;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEA POSITION 43;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEA POSITION 44;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEA POSITION 45;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPT1 POSITION 46;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALT1 POSITION 47;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODTIPOCONT1 POSITION 48;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPT2 POSITION 49;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALT2 POSITION 50;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODTIPOCONT2 POSITION 51;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPCF POSITION 52;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALCF POSITION 53;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODCONFEMAIL POSITION 54;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPC2 POSITION 55;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALC2 POSITION 56;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODCONFEMAIL2 POSITION 57;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEN POSITION 58;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEN POSITION 59;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEN POSITION 60;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPE2 POSITION 61;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALE2 POSITION 62;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEN2 POSITION 63;

ALTER TABLE SGPREFERE3 ALTER COLUMN EMAILNOTIF1 POSITION 64;

ALTER TABLE SGPREFERE3 ALTER COLUMN EMAILNOTIF2 POSITION 65;

ALTER TABLE SGPREFERE3 ALTER COLUMN TEMPOMAXINT POSITION 66;

ALTER TABLE SGPREFERE3 ALTER COLUMN LANCAPONTOAF POSITION 67;

ALTER TABLE SGPREFERE3 ALTER COLUMN TOLREGPONTO POSITION 68;

ALTER TABLE SGPREFERE3 ALTER COLUMN USACTOSEQ POSITION 69;

ALTER TABLE SGPREFERE3 ALTER COLUMN LAYOUTFICHAAVAL POSITION 70;

ALTER TABLE SGPREFERE3 ALTER COLUMN LAYOUTPREFICHAAVAL POSITION 71;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV1 POSITION 72;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV1 POSITION 73;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG1 POSITION 74;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV2 POSITION 75;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV2 POSITION 76;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG2 POSITION 77;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV3 POSITION 78;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV3 POSITION 79;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG3 POSITION 80;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV4 POSITION 81;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV4 POSITION 82;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG4 POSITION 83;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV5 POSITION 84;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV5 POSITION 85;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG5 POSITION 86;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV6 POSITION 87;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV6 POSITION 88;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG6 POSITION 89;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV7 POSITION 90;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV7 POSITION 91;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG7 POSITION 92;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV8 POSITION 93;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV8 POSITION 94;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG8 POSITION 95;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTINS POSITION 96;

ALTER TABLE SGPREFERE3 ALTER COLUMN HINS POSITION 97;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUINS POSITION 98;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTALT POSITION 99;

ALTER TABLE SGPREFERE3 ALTER COLUMN HALT POSITION 100;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUALT POSITION 101;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGPREFERE5 ALTER COLUMN CLASSOP POSITION 3;

ALTER TABLE SGPREFERE5 ALTER COLUMN NOMERESP POSITION 4;

ALTER TABLE SGPREFERE5 ALTER COLUMN IDENTPROFRESP POSITION 5;

ALTER TABLE SGPREFERE5 ALTER COLUMN CARGORESP POSITION 6;

ALTER TABLE SGPREFERE5 ALTER COLUMN MESESDESCCP POSITION 7;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODTIPOMOV POSITION 8;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODEMPTM POSITION 9;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODFILIALTM POSITION 10;

ALTER TABLE SGPREFERE5 ALTER COLUMN SITRMAOP POSITION 11;

ALTER TABLE SGPREFERE5 ALTER COLUMN IMGASSRESP POSITION 12;

ALTER TABLE SGPREFERE5 ALTER COLUMN BAIXARMAAPROV POSITION 13;

ALTER TABLE SGPREFERE5 ALTER COLUMN RATAUTO POSITION 14;

ALTER TABLE SGPREFERE5 ALTER COLUMN APAGARMAOP POSITION 15;

ALTER TABLE SGPREFERE5 ALTER COLUMN NOMERELANAL POSITION 16;

ALTER TABLE SGPREFERE5 ALTER COLUMN SITPADOP POSITION 17;

ALTER TABLE SGPREFERE5 ALTER COLUMN SITPADOPCONV POSITION 18;

ALTER TABLE SGPREFERE5 ALTER COLUMN HABCONVCP POSITION 19;

ALTER TABLE SGPREFERE5 ALTER COLUMN OPSEQ POSITION 20;

ALTER TABLE SGPREFERE5 ALTER COLUMN PRODETAPAS POSITION 21;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODEMPTS POSITION 22;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODFILIALTS POSITION 23;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODTIPOMOVSP POSITION 24;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODEMPEN POSITION 25;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODFILIALEN POSITION 26;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODTIPOMOVEN POSITION 27;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODEMPRE POSITION 28;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODFILIALRE POSITION 29;

ALTER TABLE SGPREFERE5 ALTER COLUMN CODTIPOMOVRE POSITION 30;

ALTER TABLE SGPREFERE5 ALTER COLUMN EXPEDIRRMA POSITION 31;

ALTER TABLE SGPREFERE5 ALTER COLUMN VALIDAQTDOP POSITION 32;

ALTER TABLE SGPREFERE5 ALTER COLUMN VALIDAFASEOP POSITION 33;

ALTER TABLE SGPREFERE5 ALTER COLUMN EDITQTDOP POSITION 34;

ALTER TABLE SGPREFERE5 ALTER COLUMN DTINS POSITION 35;

ALTER TABLE SGPREFERE5 ALTER COLUMN HINS POSITION 36;

ALTER TABLE SGPREFERE5 ALTER COLUMN IDUSUINS POSITION 37;

ALTER TABLE SGPREFERE5 ALTER COLUMN DTALT POSITION 38;

ALTER TABLE SGPREFERE5 ALTER COLUMN HALT POSITION 39;

ALTER TABLE SGPREFERE5 ALTER COLUMN IDUSUALT POSITION 40;

ALTER TABLE VDFRETEVD ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDFRETEVD ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDFRETEVD ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE VDFRETEVD ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE VDFRETEVD ALTER COLUMN CODEMPTN POSITION 5;

ALTER TABLE VDFRETEVD ALTER COLUMN CODFILIALTN POSITION 6;

ALTER TABLE VDFRETEVD ALTER COLUMN CODTRAN POSITION 7;

ALTER TABLE VDFRETEVD ALTER COLUMN TIPOFRETEVD POSITION 8;

ALTER TABLE VDFRETEVD ALTER COLUMN PLACAFRETEVD POSITION 9;

ALTER TABLE VDFRETEVD ALTER COLUMN UFFRETEVD POSITION 10;

ALTER TABLE VDFRETEVD ALTER COLUMN VLRFRETEVD POSITION 11;

ALTER TABLE VDFRETEVD ALTER COLUMN QTDFRETEVD POSITION 12;

ALTER TABLE VDFRETEVD ALTER COLUMN PESOBRUTVD POSITION 13;

ALTER TABLE VDFRETEVD ALTER COLUMN PESOLIQVD POSITION 14;

ALTER TABLE VDFRETEVD ALTER COLUMN ESPFRETEVD POSITION 15;

ALTER TABLE VDFRETEVD ALTER COLUMN MARCAFRETEVD POSITION 16;

ALTER TABLE VDFRETEVD ALTER COLUMN CONHECFRETEVD POSITION 17;

ALTER TABLE VDFRETEVD ALTER COLUMN PERCVENDAFRETEVD POSITION 18;

ALTER TABLE VDFRETEVD ALTER COLUMN FLAG POSITION 19;

ALTER TABLE VDFRETEVD ALTER COLUMN ADICFRETEVD POSITION 20;

ALTER TABLE VDFRETEVD ALTER COLUMN ADICFRETEBASEICM POSITION 21;

ALTER TABLE VDFRETEVD ALTER COLUMN VLRBASEICMSFRETEVD POSITION 22;

ALTER TABLE VDFRETEVD ALTER COLUMN ALIQICMSFRETEVD POSITION 23;

ALTER TABLE VDFRETEVD ALTER COLUMN VLRICMSFRETEVD POSITION 24;

ALTER TABLE VDFRETEVD ALTER COLUMN VLRSEGFRETEVD POSITION 25;

ALTER TABLE VDFRETEVD ALTER COLUMN RNTCVD POSITION 26;

ALTER TABLE VDFRETEVD ALTER COLUMN DTINS POSITION 27;

ALTER TABLE VDFRETEVD ALTER COLUMN HINS POSITION 28;

ALTER TABLE VDFRETEVD ALTER COLUMN IDUSUINS POSITION 29;

ALTER TABLE VDFRETEVD ALTER COLUMN DTALT POSITION 30;

ALTER TABLE VDFRETEVD ALTER COLUMN HALT POSITION 31;

ALTER TABLE VDFRETEVD ALTER COLUMN IDUSUALT POSITION 32;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE VDITVENDA ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE VDITVENDA ALTER COLUMN CODITVENDA POSITION 5;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPNT POSITION 6;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALNT POSITION 7;

ALTER TABLE VDITVENDA ALTER COLUMN CODNAT POSITION 8;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPPD POSITION 9;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALPD POSITION 10;

ALTER TABLE VDITVENDA ALTER COLUMN CODPROD POSITION 11;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPLE POSITION 12;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALLE POSITION 13;

ALTER TABLE VDITVENDA ALTER COLUMN CODLOTE POSITION 14;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPAX POSITION 15;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALAX POSITION 16;

ALTER TABLE VDITVENDA ALTER COLUMN CODALMOX POSITION 17;

ALTER TABLE VDITVENDA ALTER COLUMN QTDITVENDA POSITION 18;

ALTER TABLE VDITVENDA ALTER COLUMN QTDITVENDACANC POSITION 19;

ALTER TABLE VDITVENDA ALTER COLUMN PRECOITVENDA POSITION 20;

ALTER TABLE VDITVENDA ALTER COLUMN PERCDESCITVENDA POSITION 21;

ALTER TABLE VDITVENDA ALTER COLUMN VLRDESCITVENDA POSITION 22;

ALTER TABLE VDITVENDA ALTER COLUMN PERCICMSITVENDA POSITION 23;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSITVENDA POSITION 24;

ALTER TABLE VDITVENDA ALTER COLUMN VLRICMSITVENDA POSITION 25;

ALTER TABLE VDITVENDA ALTER COLUMN PERCIPIITVENDA POSITION 26;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEIPIITVENDA POSITION 27;

ALTER TABLE VDITVENDA ALTER COLUMN VLRIPIITVENDA POSITION 28;

ALTER TABLE VDITVENDA ALTER COLUMN VLRLIQITVENDA POSITION 29;

ALTER TABLE VDITVENDA ALTER COLUMN PERCCOMISITVENDA POSITION 30;

ALTER TABLE VDITVENDA ALTER COLUMN VLRCOMISITVENDA POSITION 31;

ALTER TABLE VDITVENDA ALTER COLUMN VLRADICITVENDA POSITION 32;

ALTER TABLE VDITVENDA ALTER COLUMN PERCISSITVENDA POSITION 33;

ALTER TABLE VDITVENDA ALTER COLUMN VLRISSITVENDA POSITION 34;

ALTER TABLE VDITVENDA ALTER COLUMN VLRFRETEITVENDA POSITION 35;

ALTER TABLE VDITVENDA ALTER COLUMN VLRPRODITVENDA POSITION 36;

ALTER TABLE VDITVENDA ALTER COLUMN VLRISENTASITVENDA POSITION 37;

ALTER TABLE VDITVENDA ALTER COLUMN VLROUTRASITVENDA POSITION 38;

ALTER TABLE VDITVENDA ALTER COLUMN REFPROD POSITION 39;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEISSITVENDA POSITION 40;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSBRUTITVENDA POSITION 41;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSSTITVENDA POSITION 42;

ALTER TABLE VDITVENDA ALTER COLUMN VLRICMSSTITVENDA POSITION 43;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASECOMISITVENDA POSITION 44;

ALTER TABLE VDITVENDA ALTER COLUMN MARGEMVLAGRITVENDA POSITION 45;

ALTER TABLE VDITVENDA ALTER COLUMN OBSITVENDA POSITION 46;

ALTER TABLE VDITVENDA ALTER COLUMN ORIGFISC POSITION 47;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPTT POSITION 48;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALTT POSITION 49;

ALTER TABLE VDITVENDA ALTER COLUMN CODTRATTRIB POSITION 50;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOFISC POSITION 51;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOST POSITION 52;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPME POSITION 53;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALME POSITION 54;

ALTER TABLE VDITVENDA ALTER COLUMN CODMENS POSITION 55;

ALTER TABLE VDITVENDA ALTER COLUMN STRDESCITVENDA POSITION 56;

ALTER TABLE VDITVENDA ALTER COLUMN QTDDEVITVENDA POSITION 57;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPLG POSITION 58;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALLG POSITION 59;

ALTER TABLE VDITVENDA ALTER COLUMN CODLOG POSITION 60;

ALTER TABLE VDITVENDA ALTER COLUMN CANCITVENDA POSITION 61;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPPE POSITION 62;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALPE POSITION 63;

ALTER TABLE VDITVENDA ALTER COLUMN CODPE POSITION 64;

ALTER TABLE VDITVENDA ALTER COLUMN DIASPE POSITION 65;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCV POSITION 66;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCV POSITION 67;

ALTER TABLE VDITVENDA ALTER COLUMN CODCONV POSITION 68;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPIF POSITION 69;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALIF POSITION 70;

ALTER TABLE VDITVENDA ALTER COLUMN CODFISC POSITION 71;

ALTER TABLE VDITVENDA ALTER COLUMN CODITFISC POSITION 72;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCP POSITION 73;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCP POSITION 74;

ALTER TABLE VDITVENDA ALTER COLUMN CODCOMPRA POSITION 75;

ALTER TABLE VDITVENDA ALTER COLUMN CODITCOMPRA POSITION 76;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPVR POSITION 77;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALVR POSITION 78;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOVENDAVR POSITION 79;

ALTER TABLE VDITVENDA ALTER COLUMN CODVENDAVR POSITION 80;

ALTER TABLE VDITVENDA ALTER COLUMN CODITVENDAVR POSITION 81;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPNS POSITION 82;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALNS POSITION 83;

ALTER TABLE VDITVENDA ALTER COLUMN NUMSERIETMP POSITION 84;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSSTRETITVENDA POSITION 85;

ALTER TABLE VDITVENDA ALTER COLUMN VLRICMSSTRETITVENDA POSITION 86;

ALTER TABLE VDITVENDA ALTER COLUMN CALCSTCM POSITION 87;

ALTER TABLE VDITVENDA ALTER COLUMN EMMANUT POSITION 88;

ALTER TABLE VDITVENDA ALTER COLUMN DTINS POSITION 89;

ALTER TABLE VDITVENDA ALTER COLUMN HINS POSITION 90;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUINS POSITION 91;

ALTER TABLE VDITVENDA ALTER COLUMN DTALT POSITION 92;

ALTER TABLE VDITVENDA ALTER COLUMN HALT POSITION 93;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUALT POSITION 94;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDORCAMENTO ALTER COLUMN TIPOORC POSITION 3;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODORC POSITION 4;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPAE POSITION 5;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALAE POSITION 6;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODATEND POSITION 7;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPCV POSITION 8;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALCV POSITION 9;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODCONV POSITION 10;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTORC POSITION 11;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTVENCORC POSITION 12;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRPRODORC POSITION 13;

ALTER TABLE VDORCAMENTO ALTER COLUMN PERCDESCORC POSITION 14;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRDESCORC POSITION 15;

ALTER TABLE VDORCAMENTO ALTER COLUMN PERCADICORC POSITION 16;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRADICORC POSITION 17;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRDESCITORC POSITION 18;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRLIQORC POSITION 19;

ALTER TABLE VDORCAMENTO ALTER COLUMN STATUSORC POSITION 20;

ALTER TABLE VDORCAMENTO ALTER COLUMN OBSORC POSITION 21;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODPLANOPAG POSITION 22;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALPG POSITION 23;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPPG POSITION 24;

ALTER TABLE VDORCAMENTO ALTER COLUMN TXT01 POSITION 25;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPVD POSITION 26;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALVD POSITION 27;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODVEND POSITION 28;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPCL POSITION 29;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALCL POSITION 30;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODCLI POSITION 31;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODTPCONV POSITION 32;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALTC POSITION 33;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPTC POSITION 34;

ALTER TABLE VDORCAMENTO ALTER COLUMN PRAZOENTORC POSITION 35;

ALTER TABLE VDORCAMENTO ALTER COLUMN EMMANUT POSITION 36;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODCLCOMIS POSITION 37;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPCM POSITION 38;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALCM POSITION 39;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPTN POSITION 40;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALTN POSITION 41;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODTRAN POSITION 42;

ALTER TABLE VDORCAMENTO ALTER COLUMN TIPOFRETE POSITION 43;

ALTER TABLE VDORCAMENTO ALTER COLUMN ADICFRETE POSITION 44;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRFRETEORC POSITION 45;

ALTER TABLE VDORCAMENTO ALTER COLUMN VLRCOMISORC POSITION 46;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODEMPTM POSITION 47;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODFILIALTM POSITION 48;

ALTER TABLE VDORCAMENTO ALTER COLUMN CODTIPOMOV POSITION 49;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTAPROVORC POSITION 50;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTPREVENTORC POSITION 51;

ALTER TABLE VDORCAMENTO ALTER COLUMN HPREVENTORC POSITION 52;

ALTER TABLE VDORCAMENTO ALTER COLUMN JUSTIFICCANCORC POSITION 53;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTINS POSITION 54;

ALTER TABLE VDORCAMENTO ALTER COLUMN HINS POSITION 55;

ALTER TABLE VDORCAMENTO ALTER COLUMN IDUSUINS POSITION 56;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTALT POSITION 57;

ALTER TABLE VDORCAMENTO ALTER COLUMN HALT POSITION 58;

ALTER TABLE VDORCAMENTO ALTER COLUMN IDUSUALT POSITION 59;

ALTER TABLE VDTRANSP ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDTRANSP ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDTRANSP ALTER COLUMN CODTRAN POSITION 3;

ALTER TABLE VDTRANSP ALTER COLUMN RAZTRAN POSITION 4;

ALTER TABLE VDTRANSP ALTER COLUMN NOMETRAN POSITION 5;

ALTER TABLE VDTRANSP ALTER COLUMN CNPJTRAN POSITION 6;

ALTER TABLE VDTRANSP ALTER COLUMN CPFTRAN POSITION 7;

ALTER TABLE VDTRANSP ALTER COLUMN INSCTRAN POSITION 8;

ALTER TABLE VDTRANSP ALTER COLUMN ENDTRAN POSITION 9;

ALTER TABLE VDTRANSP ALTER COLUMN NUMTRAN POSITION 10;

ALTER TABLE VDTRANSP ALTER COLUMN COMPLTRAN POSITION 11;

ALTER TABLE VDTRANSP ALTER COLUMN BAIRTRAN POSITION 12;

ALTER TABLE VDTRANSP ALTER COLUMN CIDTRAN POSITION 13;

ALTER TABLE VDTRANSP ALTER COLUMN CEPTRAN POSITION 14;

ALTER TABLE VDTRANSP ALTER COLUMN FONETRAN POSITION 15;

ALTER TABLE VDTRANSP ALTER COLUMN FAXTRAN POSITION 16;

ALTER TABLE VDTRANSP ALTER COLUMN UFTRAN POSITION 17;

ALTER TABLE VDTRANSP ALTER COLUMN TIPOTRAN POSITION 18;

ALTER TABLE VDTRANSP ALTER COLUMN CELTRAN POSITION 19;

ALTER TABLE VDTRANSP ALTER COLUMN CONTTRAN POSITION 20;

ALTER TABLE VDTRANSP ALTER COLUMN DDDFONETRAN POSITION 21;

ALTER TABLE VDTRANSP ALTER COLUMN DDDFAXTRAN POSITION 22;

ALTER TABLE VDTRANSP ALTER COLUMN DDDCELTRAN POSITION 23;

ALTER TABLE VDTRANSP ALTER COLUMN CODMUNIC POSITION 24;

ALTER TABLE VDTRANSP ALTER COLUMN SIGLAUF POSITION 25;

ALTER TABLE VDTRANSP ALTER COLUMN CODPAIS POSITION 26;

ALTER TABLE VDTRANSP ALTER COLUMN CODEMPUC POSITION 27;

ALTER TABLE VDTRANSP ALTER COLUMN CODFILIALUC POSITION 28;

ALTER TABLE VDTRANSP ALTER COLUMN CODUNIFCOD POSITION 29;

ALTER TABLE VDTRANSP ALTER COLUMN CODEMPFR POSITION 30;

ALTER TABLE VDTRANSP ALTER COLUMN CODFILIALFR POSITION 31;

ALTER TABLE VDTRANSP ALTER COLUMN CODFOR POSITION 32;

ALTER TABLE VDTRANSP ALTER COLUMN CONJUGETRAN POSITION 33;

ALTER TABLE VDTRANSP ALTER COLUMN CODEMPBO POSITION 34;

ALTER TABLE VDTRANSP ALTER COLUMN CODFILIALBO POSITION 35;

ALTER TABLE VDTRANSP ALTER COLUMN CODBANCO POSITION 36;

ALTER TABLE VDTRANSP ALTER COLUMN AGENCIATRAN POSITION 37;

ALTER TABLE VDTRANSP ALTER COLUMN NUMCONTATRAN POSITION 38;

ALTER TABLE VDTRANSP ALTER COLUMN PLACATRAN POSITION 39;

ALTER TABLE VDTRANSP ALTER COLUMN NRODEPENDTRAN POSITION 40;

ALTER TABLE VDTRANSP ALTER COLUMN RGTRAN POSITION 41;

ALTER TABLE VDTRANSP ALTER COLUMN SSPTRAN POSITION 42;

ALTER TABLE VDTRANSP ALTER COLUMN CODGPS POSITION 43;

ALTER TABLE VDTRANSP ALTER COLUMN NROPISTRAN POSITION 44;

ALTER TABLE VDTRANSP ALTER COLUMN OBSTRAN POSITION 45;

ALTER TABLE VDTRANSP ALTER COLUMN EMAILTRAN POSITION 46;

ALTER TABLE VDTRANSP ALTER COLUMN EMAILNFETRAN POSITION 47;

ALTER TABLE VDTRANSP ALTER COLUMN RNTCTRAN POSITION 48;

ALTER TABLE VDTRANSP ALTER COLUMN DTINS POSITION 49;

ALTER TABLE VDTRANSP ALTER COLUMN HINS POSITION 50;

ALTER TABLE VDTRANSP ALTER COLUMN IDUSUINS POSITION 51;

ALTER TABLE VDTRANSP ALTER COLUMN DTALT POSITION 52;

ALTER TABLE VDTRANSP ALTER COLUMN HALT POSITION 53;

ALTER TABLE VDTRANSP ALTER COLUMN IDUSUALT POSITION 54;

ALTER TABLE VDVEICULO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDVEICULO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDVEICULO ALTER COLUMN CODVEIC POSITION 3;

ALTER TABLE VDVEICULO ALTER COLUMN PLACA POSITION 4;

ALTER TABLE VDVEICULO ALTER COLUMN RENAVAM POSITION 5;

ALTER TABLE VDVEICULO ALTER COLUMN CODPAIS POSITION 6;

ALTER TABLE VDVEICULO ALTER COLUMN CODMUNIC POSITION 7;

ALTER TABLE VDVEICULO ALTER COLUMN SIGLAUF POSITION 8;

ALTER TABLE VDVEICULO ALTER COLUMN FABRICANTE POSITION 9;

ALTER TABLE VDVEICULO ALTER COLUMN MODELO POSITION 10;

ALTER TABLE VDVEICULO ALTER COLUMN DESCCOR POSITION 11;

ALTER TABLE VDVEICULO ALTER COLUMN CODCOR POSITION 12;

ALTER TABLE VDVEICULO ALTER COLUMN OBS POSITION 13;

ALTER TABLE VDVEICULO ALTER COLUMN ANOFABRIC POSITION 14;

ALTER TABLE VDVEICULO ALTER COLUMN ANOMODELO POSITION 15;

ALTER TABLE VDVEICULO ALTER COLUMN CODEMPTN POSITION 16;

ALTER TABLE VDVEICULO ALTER COLUMN CODFILIALTN POSITION 17;

ALTER TABLE VDVEICULO ALTER COLUMN CODTRAN POSITION 18;

ALTER TABLE VDVEICULO ALTER COLUMN RNTC POSITION 19;

ALTER TABLE VDVEICULO ALTER COLUMN DTINS POSITION 20;

ALTER TABLE VDVEICULO ALTER COLUMN HINS POSITION 21;

ALTER TABLE VDVEICULO ALTER COLUMN IDUSUINS POSITION 22;

ALTER TABLE VDVEICULO ALTER COLUMN DTALT POSITION 23;

ALTER TABLE VDVEICULO ALTER COLUMN HALT POSITION 24;

ALTER TABLE VDVEICULO ALTER COLUMN IDUSUALT POSITION 25;

ALTER TABLE VDVENDA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDVENDA ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE VDVENDA ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE VDVENDA ALTER COLUMN SUBTIPOVENDA POSITION 5;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPVD POSITION 6;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALVD POSITION 7;

ALTER TABLE VDVENDA ALTER COLUMN CODVEND POSITION 8;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCL POSITION 9;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCL POSITION 10;

ALTER TABLE VDVENDA ALTER COLUMN CODCLI POSITION 11;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPPG POSITION 12;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALPG POSITION 13;

ALTER TABLE VDVENDA ALTER COLUMN CODPLANOPAG POSITION 14;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPSE POSITION 15;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALSE POSITION 16;

ALTER TABLE VDVENDA ALTER COLUMN SERIE POSITION 17;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPTM POSITION 18;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALTM POSITION 19;

ALTER TABLE VDVENDA ALTER COLUMN CODTIPOMOV POSITION 20;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCX POSITION 21;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCX POSITION 22;

ALTER TABLE VDVENDA ALTER COLUMN CODCAIXA POSITION 23;

ALTER TABLE VDVENDA ALTER COLUMN DOCVENDA POSITION 24;

ALTER TABLE VDVENDA ALTER COLUMN DTSAIDAVENDA POSITION 25;

ALTER TABLE VDVENDA ALTER COLUMN DTEMITVENDA POSITION 26;

ALTER TABLE VDVENDA ALTER COLUMN DTCOMPVENDA POSITION 27;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPRM POSITION 28;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALRM POSITION 29;

ALTER TABLE VDVENDA ALTER COLUMN TICKET POSITION 30;

ALTER TABLE VDVENDA ALTER COLUMN VLRPRODVENDA POSITION 31;

ALTER TABLE VDVENDA ALTER COLUMN PERCDESCVENDA POSITION 32;

ALTER TABLE VDVENDA ALTER COLUMN VLRDESCVENDA POSITION 33;

ALTER TABLE VDVENDA ALTER COLUMN VLRDESCITVENDA POSITION 34;

ALTER TABLE VDVENDA ALTER COLUMN VLRVENDA POSITION 35;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEICMSVENDA POSITION 36;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSVENDA POSITION 37;

ALTER TABLE VDVENDA ALTER COLUMN CALCICMSVENDA POSITION 38;

ALTER TABLE VDVENDA ALTER COLUMN IMPICMSVENDA POSITION 39;

ALTER TABLE VDVENDA ALTER COLUMN VLRISENTASVENDA POSITION 40;

ALTER TABLE VDVENDA ALTER COLUMN VLROUTRASVENDA POSITION 41;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEIPIVENDA POSITION 42;

ALTER TABLE VDVENDA ALTER COLUMN VLRLIQVENDA POSITION 43;

ALTER TABLE VDVENDA ALTER COLUMN PERCCOMISVENDA POSITION 44;

ALTER TABLE VDVENDA ALTER COLUMN VLRCOMISVENDA POSITION 45;

ALTER TABLE VDVENDA ALTER COLUMN STATUSVENDA POSITION 46;

ALTER TABLE VDVENDA ALTER COLUMN VLRFRETEVENDA POSITION 47;

ALTER TABLE VDVENDA ALTER COLUMN VLRADICVENDA POSITION 48;

ALTER TABLE VDVENDA ALTER COLUMN VLRIPIVENDA POSITION 49;

ALTER TABLE VDVENDA ALTER COLUMN CALCIPIVENDA POSITION 50;

ALTER TABLE VDVENDA ALTER COLUMN IMPIPIVENDA POSITION 51;

ALTER TABLE VDVENDA ALTER COLUMN OBSVENDA POSITION 52;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPBO POSITION 53;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALBO POSITION 54;

ALTER TABLE VDVENDA ALTER COLUMN CODBANCO POSITION 55;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPTC POSITION 56;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALTC POSITION 57;

ALTER TABLE VDVENDA ALTER COLUMN CODTIPOCOB POSITION 58;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEISSVENDA POSITION 59;

ALTER TABLE VDVENDA ALTER COLUMN VLRISSVENDA POSITION 60;

ALTER TABLE VDVENDA ALTER COLUMN CALCISSVENDA POSITION 61;

ALTER TABLE VDVENDA ALTER COLUMN IMPIISSVENDA POSITION 62;

ALTER TABLE VDVENDA ALTER COLUMN IMPNOTAVENDA POSITION 63;

ALTER TABLE VDVENDA ALTER COLUMN FLAG POSITION 64;

ALTER TABLE VDVENDA ALTER COLUMN CODCLASCOMIS POSITION 65;

ALTER TABLE VDVENDA ALTER COLUMN VLRPISVENDA POSITION 66;

ALTER TABLE VDVENDA ALTER COLUMN CALCPISVENDA POSITION 67;

ALTER TABLE VDVENDA ALTER COLUMN IMPPISVENDA POSITION 68;

ALTER TABLE VDVENDA ALTER COLUMN VLRCOFINSVENDA POSITION 69;

ALTER TABLE VDVENDA ALTER COLUMN CALCCOFINSVENDA POSITION 70;

ALTER TABLE VDVENDA ALTER COLUMN IMPCOFINSVENDA POSITION 71;

ALTER TABLE VDVENDA ALTER COLUMN VLRIRVENDA POSITION 72;

ALTER TABLE VDVENDA ALTER COLUMN CALCIRVENDA POSITION 73;

ALTER TABLE VDVENDA ALTER COLUMN IMPIRVENDA POSITION 74;

ALTER TABLE VDVENDA ALTER COLUMN VLRCSOCIALVENDA POSITION 75;

ALTER TABLE VDVENDA ALTER COLUMN CALCCSOCIALVENDA POSITION 76;

ALTER TABLE VDVENDA ALTER COLUMN IMPCSOCIALVENDA POSITION 77;

ALTER TABLE VDVENDA ALTER COLUMN PERCMCOMISVENDA POSITION 78;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCM POSITION 79;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCM POSITION 80;

ALTER TABLE VDVENDA ALTER COLUMN CODCLCOMIS POSITION 81;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCB POSITION 82;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCB POSITION 83;

ALTER TABLE VDVENDA ALTER COLUMN CODCARTCOB POSITION 84;

ALTER TABLE VDVENDA ALTER COLUMN PEDCLIVENDA POSITION 85;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSSTVENDA POSITION 86;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEICMSSTVENDA POSITION 87;

ALTER TABLE VDVENDA ALTER COLUMN EMMANUT POSITION 88;

ALTER TABLE VDVENDA ALTER COLUMN BLOQVENDA POSITION 89;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSSIMPLES POSITION 90;

ALTER TABLE VDVENDA ALTER COLUMN PERCICMSSIMPLES POSITION 91;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEPISVENDA POSITION 92;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASECOFINSVENDA POSITION 93;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASECOMIS POSITION 94;

ALTER TABLE VDVENDA ALTER COLUMN CHAVENFEVENDA POSITION 95;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCA POSITION 96;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCA POSITION 97;

ALTER TABLE VDVENDA ALTER COLUMN NUMCONTA POSITION 98;

ALTER TABLE VDVENDA ALTER COLUMN OBSREC POSITION 99;

ALTER TABLE VDVENDA ALTER COLUMN INFCOMPL POSITION 100;

ALTER TABLE VDVENDA ALTER COLUMN SITDOC POSITION 101;

ALTER TABLE VDVENDA ALTER COLUMN OBSNFE POSITION 102;

ALTER TABLE VDVENDA ALTER COLUMN DESCIPIVENDA POSITION 103;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPOP POSITION 104;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALOP POSITION 105;

ALTER TABLE VDVENDA ALTER COLUMN SEQOP POSITION 106;

ALTER TABLE VDVENDA ALTER COLUMN CODOP POSITION 107;

ALTER TABLE VDVENDA ALTER COLUMN LOCALSERV POSITION 108;

ALTER TABLE VDVENDA ALTER COLUMN NROATUALIZADO POSITION 109;

ALTER TABLE VDVENDA ALTER COLUMN CNF POSITION 110;

ALTER TABLE VDVENDA ALTER COLUMN SITCOMPLVENDA POSITION 111;

ALTER TABLE VDVENDA ALTER COLUMN DTINS POSITION 112;

ALTER TABLE VDVENDA ALTER COLUMN HINS POSITION 113;

ALTER TABLE VDVENDA ALTER COLUMN IDUSUINS POSITION 114;

ALTER TABLE VDVENDA ALTER COLUMN DTALT POSITION 115;

ALTER TABLE VDVENDA ALTER COLUMN HALT POSITION 116;

ALTER TABLE VDVENDA ALTER COLUMN IDUSUALT POSITION 117;


COMMIT WORK;
