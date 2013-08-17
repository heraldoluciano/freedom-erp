/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE VDCOMISSAO DROP CONSTRAINT VDCOMISSAOFKFNPAGT;

--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE VDCOMISSAO DROP CONSTRAINT VDCOMISSAOFKFNITRE;

--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE EQITRECMERCITOSITORC DROP CONSTRAINT EQITRECMERCITOSITORCFKVDITORC;

--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

/* Alter Field (Length): from 13 to 15... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=15, RDB$CHARACTER_LENGTH=15
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='EQPRODUTO' AND  RDB$FIELD_NAME='CODBARPROD');

/* Alter Field (Length): from 13 to 15... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=15, RDB$CHARACTER_LENGTH=15
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='EQPRODUTO' AND  RDB$FIELD_NAME='CODFABPROD');

/* Alter Field (Length): from 0 to 2... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=2, RDB$CHARACTER_LENGTH=2
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='EQRECMERC' AND  RDB$FIELD_NAME='STATUS');

Update Rdb$Relation_Fields set Rdb$Description =
'Imposto, pode ser: CO (Cofins),PI (PIS),IC (ICMS),IR (IMPOSTO DE RENDA),CS (Contribuição Social), IP (IPI), IS (ISS)'
where Rdb$Relation_Name='LFSITTRIB' and Rdb$Field_Name='IMPSITTRIB';

/* Alter Field (Length): from 0 to 4... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=4, RDB$CHARACTER_LENGTH=4
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='RHCODGPS' AND  RDB$FIELD_NAME='CODGPS');

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de movimento para orçamento'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV2';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de movimento para serviços'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV4';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODEMPRC' AND RDB$RELATION_NAME='VDCOMISSAO';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODFILIALRC' AND RDB$RELATION_NAME='VDCOMISSAO';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODREC' AND RDB$RELATION_NAME='VDCOMISSAO';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='NPARCITREC' AND RDB$RELATION_NAME='VDCOMISSAO';

CREATE DOMAIN IBCXXX CHAR(2) DEFAULT 'NP' NOT NULL;

UPDATE RDB$RELATION_FIELDS SET RDB$DEFAULT_SOURCE=
(SELECT RDB$DEFAULT_SOURCE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX'),
RDB$DEFAULT_VALUE=
(SELECT RDB$DEFAULT_VALUE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX')
WHERE RDB$FIELD_NAME='SITPRODITORC' AND RDB$RELATION_NAME='VDITORCAMENTO';

DROP DOMAIN IBCXXX;

UPDATE RDB$FIELDS SET RDB$DEFAULT_VALUE = NULL,
RDB$DEFAULT_SOURCE = '' WHERE RDB$FIELD_NAME =
(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
WHERE RDB$FIELD_NAME = 'SITPRODITORC' AND RDB$RELATION_NAME = 'VDITORCAMENTO');

Update Rdb$Relation_Fields set Rdb$Description =
'Situação da produção do ítem de orçamento.
PE - Pendente
EP - Em produção
NP - Não produzir
PD - Produzido'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='SITPRODITORC';


ALTER TABLE EQITRMA ADD CODEMPOS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da ordem de serviço vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODEMPOS';

ALTER TABLE EQITRMA ADD CODFILIALOS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da ordem de serviço vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODFILIALOS';

ALTER TABLE EQITRMA ADD TICKET INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket da ordem de serviço vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='TICKET';

ALTER TABLE EQITRMA ADD CODITRECMERC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Item de recebimento da ordem de serviço vinculada'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODITRECMERC';

ALTER TABLE EQITRMA ADD CODITOS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Item de suplemento da ordem de serviço vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODITOS';

ALTER TABLE EQPRODUTO ADD NRODIASVALID SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Número de dias para validade do produto.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='NRODIASVALID';

ALTER TABLE EQPRODUTO ADD DESCCLI CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve aplicar o desconto padrão do cliente.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='DESCCLI';

ALTER TABLE EQRMA ADD CODITRECMERC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do ítem de recebimento de mercadoria (Ordem de serviço)'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODITRECMERC';

ALTER TABLE FNITRECEBER ADD VLRBASECOMIS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor base para comissionamento especial'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='VLRBASECOMIS';

ALTER TABLE FNRECEBER ADD VLRBASECOMIS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo de comissionamento especial'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='VLRBASECOMIS';

ALTER TABLE LFITCLFISCAL ADD CODEMPIS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da situação tributário para o ISS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPIS';

ALTER TABLE LFITCLFISCAL ADD CODFILIALIS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial para a situação tributária do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALIS';

ALTER TABLE LFITCLFISCAL ADD CODSITTRIBISS CHAR(2);

Update Rdb$Relation_Fields set Rdb$Description =
'Código da situação tributária para o ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODSITTRIBISS';

ALTER TABLE LFITCLFISCAL ADD IMPSITTRIBISS CHAR(2);

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla do imposto definido para situação tributária do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='IMPSITTRIBISS';

ALTER TABLE LFITCLFISCAL ADD ALIQISSFISC NUMERIC(6,2);

Update Rdb$Relation_Fields set Rdb$Description =
'Alíquota do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQISSFISC';

ALTER TABLE LFTIPOFISCCLI ADD CODEMPOC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa para o tipo de movimento de orçamento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODEMPOC';

ALTER TABLE LFTIPOFISCCLI ADD CODFILIALOC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial para o tipo de movimento de orçamento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODFILIALOC';

ALTER TABLE LFTIPOFISCCLI ADD CODTIPOMOVOC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do tipo de movimento de orçamento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODTIPOMOVOC';

ALTER TABLE PPESTRUTURA ADD ESTDINAMICA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a estrutura é dinâmica.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='ESTDINAMICA';

ALTER TABLE PPOP ADD ESTDINAMICA CHAR(1) DEFAULT 'N';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a ordem de produção deve utilizar o mecanismo de estruturas dinâmicas.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='ESTDINAMICA';

ALTER TABLE PPOP ADD GARANTIA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a ordem de produção e proveniente de garantia.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='GARANTIA';

ALTER TABLE PPOP ADD CODEMPOS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da Ordem de serviço vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPOS';

ALTER TABLE PPOP ADD CODFILIALOS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da ordem de serviço vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALOS';

ALTER TABLE PPOP ADD TICKET INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket da ordem de serviço vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='TICKET';

ALTER TABLE PPOP ADD CODITRECMERC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do item de recebimento da OS vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODITRECMERC';

ALTER TABLE PPOP ADD CODITOS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do item da OS vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODITOS';

ALTER TABLE SGPREFERE1 ADD ESPECIALCOMIS CHAR(1) DEFAULT 'N';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o mecanismo de comissionamento especial (por setor de produção)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ESPECIALCOMIS';

ALTER TABLE SGPREFERE1 ADD CODFILIALTS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do tipo de movimento para orçamentos de serviço.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALTS';

ALTER TABLE SGPREFERE1 ADD CODTIPOMOVS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do tipo de movimento para orçamentos de serviço.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOVS';

ALTER TABLE SGPREFERE1 ADD CODEMPTS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do tipo de movimento para orçamentos de serviço.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPTS';

ALTER TABLE SGPREFERE1 ADD CODEMPSV INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa de plano de pagamento sem valor financeiro para uso em devoluções e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPSV';

ALTER TABLE SGPREFERE1 ADD CODFILIALSV SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial de plano de pagamento sem valor financeiro para uso em devoluções e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALSV';

ALTER TABLE SGPREFERE1 ADD CODPLANOPAGSV INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código de plano de pagamento sem valor financeiro para uso em devoluções e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANOPAGSV';

ALTER TABLE SGPREFERE1 ADD ARREDPRECO SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Indice de arredondamento de precos:
null ou 0 = sem arredondamento;
> 0 fator de arredondamento decimal'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ARREDPRECO';

ALTER TABLE SGPREFERE8 ADD USAPRECOPECASERV CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o preço do peça consertada no orçamento de serviços.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='USAPRECOPECASERV';

ALTER TABLE SGPREFERE8 ADD CODEMPDS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa para o tipo de movimento padrão para devolução de peças consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPDS';

ALTER TABLE SGPREFERE8 ADD CODFILIALDS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial para o tipo de movimento padrão devolução de peças consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALDS';

ALTER TABLE SGPREFERE8 ADD CODTIPOMOVDS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do tipo de movimento padrão para devolução de peças consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODTIPOMOVDS';

ALTER TABLE SGPREFERE8 ADD CODEMPSE INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do produto padrão para serviços.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPSE';

ALTER TABLE SGPREFERE8 ADD CODFILIALSE SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial para produto padrão para serviço.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALSE';

ALTER TABLE SGPREFERE8 ADD CODPRODSE INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do produto padrão para serviço.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODPRODSE';

ALTER TABLE VDCLIENTE ADD PERCDESCCLI NUMERIC(3,2);

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual padrão de desconto para o cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='PERCDESCCLI';

ALTER TABLE VDCLIENTE ADD CONTCLICOB CHAR(40);

Update Rdb$Relation_Fields set Rdb$Description =
'Contato no cliente, para cobrança.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CONTCLICOB';

ALTER TABLE VDCLIENTE ADD CONTCLIENT CHAR(40);

Update Rdb$Relation_Fields set Rdb$Description =
'Contato no cliente, para entrega.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CONTCLIENT';

ALTER TABLE VDCOMISSAO ADD CODEMPVE INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da venda vinculada (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODEMPVE';

ALTER TABLE VDCOMISSAO ADD CODFILIALVE SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da venda vinculada (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODFILIALVE';

ALTER TABLE VDCOMISSAO ADD CODVENDA INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da venda (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODVENDA';

ALTER TABLE VDCOMISSAO ADD TIPOVENDA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo da venda'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='TIPOVENDA';

ALTER TABLE VDITVENDA ADD VLRBASECOMISITVENDA NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor base para comissionamento
Qtd x Preco comissao (cad.prod.)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRBASECOMISITVENDA';

ALTER TABLE VDREGRACOMIS ADD PERCCOMISGERAL NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de comissão para o grupo de comissionados (comissionamento especial por producao)'
where Rdb$Relation_Name='VDREGRACOMIS' and Rdb$Field_Name='PERCCOMISGERAL';

ALTER TABLE VDVENDA ADD VLRBASECOMIS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor base para calculo das comissões especiais.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='VLRBASECOMIS';

/* Create Table... */
CREATE TABLE EQITRECMERCSERIE(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
TICKET INTEGER NOT NULL,
CODITRECMERC INTEGER NOT NULL,
SEQITSERIE INTEGER NOT NULL,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIE VARCHAR(30),
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relations set Rdb$Description =
'Tabela de vinculo entre item de recebimento de mercadoria e seus respectivos numeros de serie.'''
where Rdb$Relation_Name='EQITRECMERCSERIE';

CREATE TABLE FNCONTAVINCULADA(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
NUMCONTA CHAR(10) NOT NULL,
CODEMPCV INTEGER NOT NULL,
CODFILIALCV SMALLINT NOT NULL,
NUMCONTACV CHAR(10) NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relations set Rdb$Description =
'Tabela de vinculo entre contas para composisão de saldo interno/externo'
where Rdb$Relation_Name='FNCONTAVINCULADA';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da conta vinculada'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CODEMPCV';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da conta vinculada.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CODFILIALCV';

Update Rdb$Relation_Fields set Rdb$Description =
'Número da conta vinculada.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='NUMCONTACV';

CREATE TABLE VDSETORROTA(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODSETOR INTEGER NOT NULL,
SEQUENCIA INTEGER NOT NULL,
CODEMPCL INTEGER NOT NULL,
CODFILIALCL SMALLINT NOT NULL,
CODCLI INTEGER NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relations set Rdb$Description =
'Tela de rotas baseada no setor dos clientes.'
where Rdb$Relation_Name='VDSETORROTA';


/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE EQITRECMERCSERIESP(TRANSACAO CHAR(1),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODITRECMERC SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITRECMERC INTEGER)
 AS
 BEGIN EXIT; END
^


SET TERM ; ^

DROP VIEW EQCONFESTOQVW01;

/* Create view: EQCONFESTOQVW01 (ViwData.CreateDependDef) */
CREATE VIEW EQCONFESTOQVW01(
CODEMP,
CODFILIAL,
ATIVOPROD,
DESCPROD,
CODPROD,
REFPROD,
SLDLIQPROD,
QTDINVP,
QTDITCOMPRA,
QTDFINALPRODOP,
QTDEXPITRMA,
QTDITVENDA,
SLDMOVPROD,
SLDLIQPRODAX)
 AS 
SELECT P.CODEMP, P.CODFILIAL, P.ATIVOPROD, P.DESCPROD,P.CODPROD,P.REFPROD,P.SLDLIQPROD,
    COALESCE((SELECT SUM(QTDINVP)  FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND
       IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD ),0) QTDINVP,
   COALESCE((SELECT SUM(QTDITCOMPRA)  FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM
       WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND
       IC.CODPROD=P.CODPROD AND  C.CODCOMPRA=IC.CODCOMPRA AND
       C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND  TM.CODTIPOMOV=C.CODTIPOMOV AND
       TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND TM.ESTOQTIPOMOV='S' ),0) QTDITCOMPRA,
   COALESCE((SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM
      WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND
      TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND
      TM.CODFILIAL=O.CODFILIALTM  AND TM.ESTOQTIPOMOV='S' ),0) QTDFINALPRODOP,
   COALESCE((SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM  WHERE IR.CODEMPPD=P.CODEMP AND
      IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND  R.CODRMA=IR.CODRMA AND
      R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND
      TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND
      TM.CODFILIAL=R.CODFILIALTM  AND TM.ESTOQTIPOMOV='S' ),0) QTDEXPITRMA,
   COALESCE((SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM
      WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND
      V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND
      V.CODEMP=IV.CODEMP AND V.CODFILIAL=IV.CODFILIAL AND
      (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND TM.CODTIPOMOV=V.CODTIPOMOV AND
      TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM  AND TM.ESTOQTIPOMOV='S' ),0) QTDITVENDA,
   COALESCE((SELECT FIRST 1 M.SLDMOVPROD FROM EQMOVPROD M WHERE M.CODEMPPD=P.CODEMP AND
      M.CODFILIALPD=P.CODFILIAL AND  M.CODPROD=P.CODPROD
      ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC ),0) SLDMOVPROD,
   COALESCE((SELECT SUM(SP.SLDLIQPROD) FROM EQSALDOPROD SP WHERE SP.CODEMP=P.CODEMP AND
      SP.CODFILIAL=P.CODFILIAL AND SP.CODPROD=P.CODPROD),0) SLDLIQPRODAX
   FROM EQPRODUTO P
;

DROP VIEW PPLISTAOPVW01;

/* Create view: PPLISTAOPVW01 (ViwData.CreateDependDef) */
CREATE VIEW PPLISTAOPVW01(
CODEMP,
CODFILIAL,
DTEMITOP,
DTFABROP,
CODOP,
SEQOP,
DESCEST,
SITOP,
TEMPOTOT,
TEMPOFIN,
FASEATUAL,
TOTFASES,
QTDSUG,
QTDPREV,
QTDFINAL,
CODPROD,
REFPROD,
CODSECAO)
 AS 
select op.codemp,op.codfilial,op.dtemitop, op.dtfabrop,op.codop, op.seqop, et.descest,op.sitop,
(select coalesce(sum( ef.tempoef ),0)
    from ppestrufase ef
    where
    ef.codemp=et.codemp and ef.codfilial=et.codfilial and ef.codprod=et.codprod and ef.seqest=et.seqest) as tempotot,
(select coalesce(sum( opf.tempoof ),0)
    from ppopfase opf
    where
    opf.codemp=op.codemp and opf.codfilial=op.codfilial and opf.codop=op.codop and opf.seqop=op.seqop
    and opf.sitfs='FN') as tempofin,
(select coalesce(min(opf.seqof),0) from ppopfase opf
    where
    opf.codemp=op.codemp and opf.codfilial=op.codfilial and opf.codop=op.codop and opf.seqop=op.seqop
    and opf.sitfs='PE') as faseatual,
(select count(*) from ppopfase opf
    where
    opf.codemp=op.codemp and opf.codfilial=op.codfilial and opf.codop=op.codop and opf.seqop=op.seqop) as totfases,
    op.qtdsugprodop,
    op.qtdprevprodop,
    op.qtdfinalprodop,
    pd.codprod, pd.refprod, pd.codsecao
from ppop op, ppestrutura et, eqproduto pd
where
et.codemp=op.codemppd and et.codfilial=op.codfilialpd and et.codprod=op.codprod and et.seqest=op.seqest
and pd.codemp=op.codemppd and pd.codfilial=op.codfilialpd and pd.codprod=op.codprod
;

DROP VIEW VWCUSTOPROJ01;

/* Create view: VWCUSTOPROJ01 (ViwData.CreateDependDef) */
CREATE VIEW VWCUSTOPROJ01(
CODEMP,
CODFILIAL,
CODCLI,
DATA,
DESCCUSTO,
CODCONTR,
CODITCONTR,
TPCONTR,
VLRPREVREC,
QTDCUSTO,
VLRCUSTO,
TIPOCUSTO)
 AS 
select ad.codemp, ad.codfilial, ad.codcli, ad.dataatendo ,cast('Hora trabalhada - ' || rtrim(ae.nomeatend)  as varchar(200)) as desccusto, ad.codcontr,ad.coditcontr, co.tpcontr ,ic.vlritcontr * ic.qtditcontr as vlrreceitaprev,
 ( ad.horaatendofin  - ad.horaatendo ) / 3600 as qtd , sa.custohoratrab as custo, 'M' as tipo
from
vditcontrato ic, atatendimento ad, atatendente ae, rhempregado em, rhempregadosal sa, vdcontrato co
where
ic.codemp=ad.codempct and ic.codfilial=ad.codfilialct and ic.codcontr=ad.codcontr and ic.coditcontr=ad.coditcontr and
co.codemp=ic.codemp and co.codfilial=ic.codfilial and co.codcontr=ic.codcontr and
ae.codemp=ad.codempae and ae.codfilial=ad.codfilialae and ae.codatend=ad.codatend and
em.codemp=ae.codempep and em.codfilial=ae.codfilialep and em.matempr=ae.matempr and
sa.codemp=em.codemp and sa.codfilial=em.codfilial and sa.matempr=em.matempr and sa.seqsal=
(
    select first 1 seqsal from rhempregadosal s1 where s1.codemp=em.codemp and
    s1.codfilial=em.codfilial and s1.matempr=em.matempr and
    s1.dtvigor < cast('today' as date) order by s1.dtvigor desc
)
-- Custos de outras despesas financeiras;
union
select sl.codemp, sl.codfilial, co.codcli, sl.datasublanca, cast(sl.histsublanca as varchar(200)) as desccusto, ic.codcontr, ic.coditcontr, co.tpcontr ,ic.vlritcontr * ic.qtditcontr as vlrreceitaprev,
1.00 as qtd, sl.vlrsublanca  as custo, 'F' as tipo
from
vditcontrato ic, vdcontrato co, fnsublanca sl, fnplanejamento pl
where
ic.codemp=sl.codempct and ic.codfilial=sl.codfilialct and ic.codcontr=sl.codcontr and ic.coditcontr=sl.coditcontr and
co.codemp=ic.codemp and co.codfilial=ic.codfilial and co.codcontr=ic.codcontr
and pl.codemp=sl.codemppn and pl.codfilial=sl.codfilialpn and pl.codplan=sl.codplan and pl.tipoplan='D'
union
--Custos de estoque
select ir.codemp, ir.codfilial, co.codcli, rma.dtaexprma, cast(pd.descprod as varchar(200)) as desccusto ,ic.codcontr, ic.coditcontr, co.tpcontr, ic.vlritcontr * ic.qtditcontr as vlrreceitaprev,
ir.qtdexpitrma as qtd, ir.precoitrma as custo, 'E' as tipo
from
vditcontrato ic, vdcontrato co, eqrma rma, eqitrma ir, eqproduto pd
where ic.codemp=rma.codempct and ic.codfilial=rma.codfilialct and ic.codcontr=rma.codcontr and ic.coditcontr=rma.coditcontr and
co.codemp=ic.codemp and co.codfilial=ic.codfilial and co.codcontr=ic.codcontr
and ir.codemp=rma.codemp and ir.codfilial=rma.codfilial and ir.codrma=rma.codrma and
pd.codemp=ir.codemppd and pd.codfilial=ir.codfilialpd and pd.codprod=ir.codprod and
ir.sitexpitrma='ET'
;

/* Create Primary Key... */
ALTER TABLE EQITRECMERCSERIE ADD CONSTRAINT EQITRECMERCSERIEPK PRIMARY KEY (TICKET,CODITRECMERC,SEQITSERIE,CODFILIAL,CODEMP);

ALTER TABLE FNCONTAVINCULADA ADD CONSTRAINT FNCONTAVINCULADAPK PRIMARY KEY (NUMCONTA,CODFILIAL,CODEMP,NUMCONTACV,CODFILIALCV,CODEMPCV);

ALTER TABLE VDSETORROTA ADD CONSTRAINT VDSETORROTAPK PRIMARY KEY (CODEMP,CODFILIAL,CODSETOR,SEQUENCIA);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE EQITRECMERCITOSITORC ADD CONSTRAINT EQITRECMERCITOSITORCFKVDITORC FOREIGN KEY (CODORC,CODITORC,TIPOORC,CODFILIALOC,CODEMPOC) REFERENCES VDITORCAMENTO(CODORC,CODITORC,TIPOORC,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE EQITRECMERCSERIE ADD CONSTRAINT EQITRECMERCFKSERIE FOREIGN KEY (NUMSERIE,CODPROD,CODFILIALPD,CODEMPPD) REFERENCES EQSERIE(NUMSERIE,CODPROD,CODFILIAL,CODEMP);

ALTER TABLE EQITRECMERCSERIE ADD CONSTRAINT EQITRECMERCSERIEFKITRECMERC FOREIGN KEY (TICKET,CODITRECMERC,CODFILIAL,CODEMP) REFERENCES EQITRECMERC(TICKET,CODITRECMERC,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE EQITRMA ADD CONSTRAINT EQITRMAFKEQITRECMERCITOS FOREIGN KEY (TICKET,CODITRECMERC,CODITOS,CODFILIALOS,CODEMPOS) REFERENCES EQITRECMERCITOS(TICKET,CODITRECMERC,CODITOS,CODFILIAL,CODEMP) ON DELETE SET NULL;

ALTER TABLE LFITCLFISCAL ADD CONSTRAINT LFITCLSFISCALFKLFSITTRIBISS FOREIGN KEY (CODSITTRIBISS,IMPSITTRIBISS,CODFILIALIS,CODEMPIS) REFERENCES LFSITTRIB(CODSITTRIB,IMPSITTRIB,CODFILIAL,CODEMP);

ALTER TABLE LFTIPOFISCCLI ADD CONSTRAINT LFTIPOFISCCLIFKEQTIPOMOV FOREIGN KEY (CODTIPOMOVOC,CODFILIALOC,CODEMPOC) REFERENCES EQTIPOMOV(CODTIPOMOV,CODFILIAL,CODEMP);

ALTER TABLE PPESTRUFASE ADD CONSTRAINT PPESTRUFASEFKESTRU FOREIGN KEY (CODPROD,SEQEST,CODFILIAL,CODEMP) REFERENCES PPESTRUTURA(CODPROD,SEQEST,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE PPOP ADD CONSTRAINT PPOPFKEQOS FOREIGN KEY (TICKET,CODITRECMERC,CODITOS,CODFILIALOS,CODEMPOS) REFERENCES EQITRECMERCITOS(TICKET,CODITRECMERC,CODITOS,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE1 ADD CONSTRAINT SGPREFERE1FKPLANOPAGSV FOREIGN KEY (CODPLANOPAGSV,CODFILIALSV,CODEMPSV) REFERENCES FNPLANOPAG(CODPLANOPAG,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE1 ADD CONSTRAINT SGPREFERE1FKTIPOMOVORCSERV FOREIGN KEY (CODTIPOMOVS,CODFILIALTS,CODEMPTS) REFERENCES EQTIPOMOV(CODTIPOMOV,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE8 ADD CONSTRAINT SGPREFERE8FKEQTIPOMOVDS FOREIGN KEY (CODTIPOMOVDS,CODFILIALDS,CODEMPDS) REFERENCES EQTIPOMOV(CODTIPOMOV,CODFILIAL,CODEMP);

ALTER TABLE VDCOMISSAO ADD CONSTRAINT VDCOMISSAOFKFNITRE FOREIGN KEY (NPARCITREC,CODREC,CODFILIALRC,CODEMPRC) REFERENCES FNITRECEBER(NPARCITREC,CODREC,CODFILIAL,CODEMP);

ALTER TABLE VDCOMISSAO ADD CONSTRAINT VDCOMISSAOFKFNPAGT FOREIGN KEY (CODPCOMI,CODFILIALCI,CODEMPCI) REFERENCES FNPAGTOCOMI(CODPCOMI,CODFILIAL,CODEMP);

ALTER TABLE VDCOMISSAO ADD CONSTRAINT VDCOMISSAOFKVDVENDA FOREIGN KEY (CODVENDA,TIPOVENDA,CODFILIALVE,CODEMPVE) REFERENCES VDVENDA(CODVENDA,TIPOVENDA,CODFILIAL,CODEMP);

ALTER TABLE VDSETORROTA ADD CONSTRAINT VDSETORROTAFKCLIENTE FOREIGN KEY (CODCLI,CODFILIALCL,CODEMPCL) REFERENCES VDCLIENTE(CODCLI,CODFILIAL,CODEMP);

/*  Empty FNGERAITRECEBERSP01 for FNADICITRECEBERSP01(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE FNGERAITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
ICODEMPPG INTEGER,
SCODFILIALPG SMALLINT,
ICODPLANOPAG INTEGER,
NVLRREC NUMERIC(15,5),
DDATAREC DATE,
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIREC NUMERIC(15,5),
OBSREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC INTEGER,
CODCC CHAR(19))
 RETURNS(I INTEGER)
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER VDVENDATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER FNRECEBERTGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER FNRECEBERTGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Alter empty procedure FNADICITRECEBERSP01 with new param-list */
ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* empty dependent procedure body */
/* Clear: EQCUSTOPRODSP for: EQCALCPEPSSP */
/* AssignEmptyBody proc */
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
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: EQPRODUTOSP01 for: EQCALCPEPSSP */
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
/* Clear: EQRELINVPRODSP for: EQCALCPEPSSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR(1),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD CHAR(13),
DESCPROD CHAR(50),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter (EQCALCPEPSSP) */
ALTER PROCEDURE EQCALCPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
NSLDPROD NUMERIC(15,5),
DTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NCUSTOPEPS NUMERIC(15,5))
 AS
declare variable dttemp date;
declare variable ncusto numeric(15,5);
declare variable nqtd numeric(15,5);
declare variable nresto numeric(15,5);
declare variable ncustotot numeric(15,5);
declare variable nsldtmp numeric(15,5);
begin
  /* Procedure que retorna o cálculo do custo peps */
  NCUSTO = 0;
  NCUSTOTOT = 0;
  NRESTO = 0;
  NCUSTOPEPS = 0;
  IF ( (NSLDPROD IS NOT NULL) AND (NSLDPROD != 0) ) THEN
  BEGIN
      NSLDTMP = NSLDPROD;
      FOR SELECT MP.DTMOVPROD, coalesce(MP.QTDMOVPROD,0),coalesce(MP.PRECOMOVPROD,0)
        FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD AND
           MP.CODEMPTM=TM.CODEMP AND MP.CODFILIALTM=TM.CODFILIAL AND MP.CODTIPOMOV=TM.CODTIPOMOV AND
           MP.TIPOMOVPROD IN ('E','I') AND TM.TIPOMOV IN ('IV','CP','PC') AND
           MP.DTMOVPROD<=:DTESTOQ  AND
           ( (:ICODALMOX IS NULL) OR
             (MP.CODEMPAX=:ICODEMPAX AND MP.CODFILIALAX=:SCODFILIALAX AND
             MP.CODALMOX=:ICODALMOX)
           )
         ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
         INTO :DTTEMP,:NQTD,:NCUSTO DO
      BEGIN
         NRESTO = NSLDTMP - NQTD;
         IF (NRESTO<=0) THEN
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * (NQTD+NRESTO) );
           BREAK;
         END
         ELSE
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * NQTD);
           NSLDTMP = NSLDTMP - NQTD;
         END
      END
      if( (:nsldprod is not null and :nsldprod>0) and (:ncustotot is not null and :ncustotot>0) ) then
          NCUSTOPEPS = NCUSTOTOT / NSLDPROD;
      else
          ncustopeps = 0;
  END
  suspend;
end
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(13),
ATIVOPROD CHAR(1))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: PPCUSTOPRODSP for: EQCUSTOPRODSP */
/* AssignEmptyBody proc */
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
DECLARE VARIABLE PRECOBASE NUMERIC(15,5);
DECLARE VARIABLE CUSTOMPM NUMERIC(15,5);
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

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
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
      P.CODPROD=:ICODPROD
   INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;

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
      END
      -- Custo MPM
      ELSE IF (CTIPOCUSTO='M') THEN
      BEGIN
         CUSTOUNIT = CUSTOMPM;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      END
      -- Preço Base
      ELSE IF (CTIPOCUSTO='B') THEN
      BEGIN
         CUSTOUNIT = PRECOBASE;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      END
      -- Preço da Ultima Compra
      else if (CTIPOCUSTO='U') then
      begin
        select first 1 ic.custoitcompra from cpitcompra ic, cpcompra cp
            where cp.codemp=:icodemp and cp.codfilial=:scodfilial and cp.dtentcompra<=:dtestoq
            and ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra
            and ic.codemppd=:icodemp and ic.codfilialpd=:scodfilial and ic.codprod=:icodprod
            order by cp.dtentcompra desc
            into :CUSTOUNIT;

            if (CUSTOUNIT IS NULL) THEN
                CUSTOUNIT = :CUSTOMPM;
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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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

/* Alter (EQITRECMERCSERIESP) */
ALTER PROCEDURE EQITRECMERCSERIESP(TRANSACAO CHAR(1),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODITRECMERC SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITRECMERC INTEGER)
 AS
declare variable seqitserie integer;
declare variable serieprod char(1);
begin
        
    -- Buscando informações do produto
    select pd.serieprod from eqproduto pd
    where pd.codemp = :codemppd and pd.codfilial = :codfilialpd and pd.codprod = :codprod
    into :serieprod;
    
    -- Se o produto usa série...
    if(serieprod = 'S') then
    begin
    
        -- Buscando ultimo item de série inserido...
        select coalesce(max(seqitserie),0) from eqitrecmercserie
        where codemp=:codemp and codfilial=:codfilial and ticket=:ticket and coditrecmerc=:coditrecmerc
        into :seqitserie;
    
        -- Se for uma transaçáo de Inserção...
        if( 'I' = :transacao) then
        begin
            -- Inserindo itens, enquanto a sequencia for menor ou igual à quantidade de itens
            while (seqitserie < qtditrecmerc) do
            begin
                seqitserie = seqitserie + 1;

                -- Se a quantidade não for unitária
                if(qtditrecmerc > 1) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie);
                end
                -- Se for apenas um item, deve inserir o numero se série digitado na tela...
                else if(qtditrecmerc = 1 and :numserietmp is not null) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie, codemppd, codfilialpd, codprod, numserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie, :codemppd, :codfilialpd, :codprod, :numserietmp );
                end
            end
        end
        -- Se for uma transação de Deleção, deve excluir todas os ítens gerados.
        else if('D' = :transacao) then
        begin
            delete from eqitrecmercserie itcs
            where
            itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc;
        end
        -- Se for uma transação de Update, deve excluir os excessos ou incluir os faltantes...
        else if('U' = :transacao) then
        begin

            if(seqitserie > qtditrecmerc) then
            begin

                delete from eqitrecmercserie itcs
                where
                itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc
                and itcs.seqitserie > :qtditrecmerc;
            
            end
            else
            begin
            
--                execute procedure cpitcompraseriesp( 'I', :codemp, :codfilial, :codcompra, :coditcompra, :codemppd, :codfilialpd, :codprod, :numserietmp, :qtditcompra);

            end

        end

    end


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

/* Alter (EQRELINVPRODSP) */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR(1),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD CHAR(13),
DESCPROD CHAR(50),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
DECLARE VARIABLE CMULTIALMOX CHAR(1);
DECLARE VARIABLE NCUSTOMPM NUMERIC(15,5);
begin
  /* Relatório de estoque */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMP) INTO :CMULTIALMOX;

  if (CCODGRUP IS NOT NULL) then
  begin
     CCODGRUP = rtrim(CCODGRUP);
     if (strlen(CCODGRUP)<14) then
        CCODGRUP = CCODGRUP || '%';
  end
  FOR SELECT P.CODPROD, P.REFPROD, P.DESCPROD, P.CODGRUP
    FROM EQPRODUTO P
    WHERE P.CODEMP=:ICODEMP AND P.CODFILIAL=:SCODFILIAL AND
          ( ( :CCODGRUP IS NULL ) OR
            (P.CODGRUP LIKE :CCODGRUP AND P.CODEMPGP=:ICODEMPGP AND
             P.CODFILIALGP=:SCODFILIALGP) )
    INTO :CODPROD, :REFPROD, :DESCPROD, :CODGRUP DO
  BEGIN
     SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODSLDSP(null, null, null, :ICODEMP,
        :SCODFILIAL, :CODPROD, :DDTESTOQ, 0, 0,
        :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
       INTO :SALDO, :NCUSTOMPM;
     if (CTIPOCUSTO='M') then
        CUSTO = NCUSTOMPM;
     else
        SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL, :CODPROD,
          :SALDO, :DDTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
        INTO :CUSTO;
     VLRESTOQ = CUSTO * SALDO;
     SUSPEND;
  END
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR(1))
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

  FOR SELECT P.CODPROD,P.REFPROD,P.DESCPROD, P.CODBARPROD, P.CODFABPROD, P.ATIVOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
   ( (:ICODEMPMC IS NULL) OR (P.CODEMPMC=:ICODEMPMC AND P.CODFILIALMC=:SCODFILIALMC AND
      P.CODMARCA=:CCODMARCA) ) AND
   ((:ICODEMPGP IS NULL) OR (P.CODEMPGP=:ICODEMPGP AND P.CODFILIALGP=:SCODFILIALGP AND
      P.CODGRUP LIKE :CCODGRUP) )
      ORDER BY P.CODPROD
   INTO :CODPROD, :REFPROD, :DESCPROD, :CODBARPROD, :CODFABPROD, :ATIVOPROD  DO
  BEGIN
     SELECT SLDPROD, CUSTOUNIT, CUSTOTOT FROM EQCUSTOPRODSP(:ICODEMP,
        :SCODFILIAL, :CODPROD, :DTESTOQ, :CTIPOCUSTO, :ICODEMPAX,
        :SCODFILIALAX, :ICODALMOX, 'S')
       INTO :SLDPROD, :CUSTOUNIT, :CUSTOTOT;
     SUSPEND;
  END
end
^

/* Alter (FNADICITRECEBERSP01) */
ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas areceber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
  END
 -- suspend;
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
VLRBASECOMIS NUMERIC(15,5))
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

ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas areceber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
  END
 -- suspend;
end
^

/* Alter (FNGERAITRECEBERSP01) */
ALTER PROCEDURE FNGERAITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
ICODEMPPG INTEGER,
SCODFILIALPG SMALLINT,
ICODPLANOPAG INTEGER,
NVLRREC NUMERIC(15,5),
DDATAREC DATE,
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIREC NUMERIC(15,5),
OBSREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC INTEGER,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 RETURNS(I INTEGER)
 AS
declare variable nperc numeric(15,5);
declare variable npercpag numeric(15,5);
declare variable nresto numeric(15,5);
declare variable inumparc integer;
declare variable idiaspag integer;
declare variable nrestocomi numeric(15,5);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nvlritrec numeric(15,5);
declare variable regrvcto char(1);
declare variable rvsab char(1);
declare variable rvdom char(1);
declare variable rvfer char(1);
declare variable dtvencto date;
declare variable rvdia char(1);
declare variable dtbase date;
declare variable diavcto smallint;
declare variable casasdecfin smallint;
declare variable vlrbaseitcomis numeric(15,5);
declare variable restobasecomis numeric(15,5);
begin

    -- Inicializando variáveis

    i = 0;
    nperc = 100;
    nresto = nvlrrec;
    nrestocomi = nvlrcomirec;
    dtbase = ddatarec;
    vlrbaseitcomis = vlrbasecomis;
    restobasecomis = vlrbasecomis;

    -- Buscando preferencias

    select casasdecfin from sgprefere1 where codemp=:icodemp and codfilial=:scodfilial
    into casasdecfin;

    -- Buscanco informações do plano de pagamento
    select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag, pp.rvferplanopag, pp.rvdiaplanopag,
    pp.diavctoplanopag
    from fnplanopag pp
    where pp.codplanopag =:icodplanopag and pp.codemp=:icodemppg and pp.codfilial = :scodfilialpg
    into :inumparc, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

    -- Loop nas parcelas do plano de pagamento para geração dos itens do contas a receber

    for select percpag, diaspag from fnparcpag
    where codplanopag=:icodplanopag and codemp=:icodemppg and codfilial =:scodfilialpg
    order by diaspag
    into :npercpag, :idiaspag
    do begin
        i = i+1;

        -- Calculando valor da parcela
        select v.deretorno from arreddouble(cast(:nvlrrec * :npercpag as numeric(15, 5))/:nperc, :casasdecfin ) v
        into :nvlritrec;

        nresto = nresto - nvlritrec;

        if (i = inumparc) then
        begin
            nvlritrec = nvlritrec + nresto;
        end

        -- Calculando o valor da comissão a pagar na parcela.
        select v.deretorno from arreddouble(cast(:nvlrcomirec * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :nvlrcomiitrec;

        nrestocomi = nrestocomi - nvlrcomiitrec;

        if (i = inumparc) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nrestocomi;
        end

         -- Calculando o valor da base da comissão especial a pagar na parcela.
        select v.deretorno from arreddouble(cast(:vlrbasecomis * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :vlrbaseitcomis;

        restobasecomis = restobasecomis - vlrbaseitcomis;

        if (i = inumparc) then
        begin
            vlrbaseitcomis = vlrbaseitcomis + restobasecomis;
        end

        -- Definindo o vencimento da parcela
        select c.dtvencret from sgcalcvencsp(:icodemp, :dtbase, :ddatarec + :idiaspag, :regrvcto,
        :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :idiaspag ) c
        into :dtvencto;

        dtbase = dtvencto;

        -- Execuntado procedimento que insere registros na tabela fnitreceber

        execute procedure fnadicitrecebersp01 (
        :caltvlr, :icodemp,:scodfilial,:icodrec,:i,0,0,0,:nvlritrec,0, :ddatarec,:dtvencto,'R1',:cflag,
        :icodempbo,:scodfilialbo, :ccodbanco, :icodemptc, :scodfilialtc, :icodtipocob,
        :icodempcb, :scodfilialcb, :codcartcob, :nvlrcomiitrec, :obsrec, :codempca,
        :codfilialca, :numconta, :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc, :vlrbaseitcomis);

    end
    suspend;
end
^

/* Alter (FNITRECEBERSP01) */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR(1))
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin
        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
TIPOPROD CHAR(1),
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
DECLARE VARIABLE QTDITEST NUMERIC(15,5);
DECLARE VARIABLE SEQEST SMALLINT;
DECLARE VARIABLE TIPOPROD CHAR(1);
DECLARE VARIABLE CUSTOTOT NUMERIC(15,5);
DECLARE VARIABLE CODPRODPD INTEGER;
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
declare variable refprod char(13);
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
declare variable crefprod char(13);
declare variable iseqitest smallint;
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

    if(:estdinamica='N') then
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitest, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqitest, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote
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
                codempfs, codfilialfs, codfase, qtditop, gerarma
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma
            );

        end

    end

    suspend;

end
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
TIPOPROD CHAR(1),
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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.6 (05/10/2010)';
    suspend;
end
^

/* empty dependent procedure body */
/* Clear: VDGERACOMISSAOSP for: VDADICCOMISSAOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDGERACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRCOMIITREC NUMERIC(15,5),
DDTVENCITREC DATE)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDESTORNACOMISSAOSP for: VDADICCOMISSAOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDESTORNACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
ICODVEND INTEGER,
DINI DATE,
DFIM DATE,
CORDEM CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter (VDADICCOMISSAOSP) */
ALTER PROCEDURE VDADICCOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRVENDACOMI NUMERIC(15,5),
NVLRCOMI NUMERIC(15,5),
DDATACOMI DATE,
DDTVENCCOMI DATE,
CTIPOCOMI CHAR(1),
CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVEND INTEGER)
 AS
declare variable scodfilialcs smallint;
declare variable icodcomi integer;
declare variable cstatuscomi char(2);
begin

    -- Se o valor for nulo ou 0 deve deletar a comissão já gerada
    if ( (nvlrcomi is null) or  (nvlrcomi=0) ) then
    begin

        delete from vdcomissao co
        where co.codemprc=:icodemp and co.codfilialrc=:scodfilial and co.codrec=:icodrec and co.nparcitrec=:inparcitrec and
        co.tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend;

    end
    -- Caso seja um estorno de comissão
    else if (nvlrcomi<0) then
    begin

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial(:icodemp,'VDCOMISSAO') into :scodfilialcs;

        -- Buscando novo numero para
        select max(codcomi) from vdcomissao where codemp=:icodemp and codfilialvd = :scodfilialcs into icodcomi;

        if (:icodcomi is null) then
            icodcomi = 1;
        else
            icodcomi = icodcomi + 1;

        -- Inserindo na tabela de comissões
        insert into vdcomissao (
            codemp, codfilial, codcomi, codempRc, codfilialrc, codrec, nparcitrec, vlrvendacomi, vlrcomi, datacomi,
            dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrvendacomi, :nvlrcomi, :ddatacomi,
            :ddtvenccomi, 'CE', :ctipocomi, :codempvd, :codfilialvd,:codvend
            );

        -- Transforma o valor da comissão em positivo e programa para o proximo pagto.
        nvlrcomi = nvlrcomi * -1;

        insert into vdcomissao (
            codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
            :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvenccomi, 'C1', :ctipocomi, :codempvd,:codfilialvd,:codvend
            );

    end
    else
    begin

        if (ctipocomi='F') then
            cstatuscomi = 'C2';
        else
            cstatuscomi = 'C1';

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial( :icodemp, 'VDCOMISSAO') into :scodfilialcs;

        -- Buscando o código da comissão já existente
        select codcomi from vdcomissao
        where codemp=:icodemp and codfilialrc=:scodfilial and codrec=:icodrec and nparcitrec=:inparcitrec and
        tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend
        into :icodcomi;

        -- Caso já não exista a comissão deve inserir
        if (icodcomi is null) then
        begin
            --Buscando um novo código
            select max(codcomi) from vdcomissao where codemp=:icodemp and codfilial = :scodfilialcs into icodcomi;

            if (:icodcomi is null) then
                icodcomi = 1;
            else
                icodcomi = icodcomi + 1;

            -- Inserindo na tabela de comissões
            insert into vdcomissao( codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend)
            values (
                :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvenccomi, :cstatuscomi, :ctipocomi, :codempvd, :codfilialvd, :codvend
            );

        end
        -- Se encontrou a comissão atualiza
        else
        begin

            update vdcomissao set vlrvendacomi=:nvlrvendacomi, vlrcomi=:nvlrcomi, datacomi=:ddatacomi,
            dtvenccomi=:ddtvenccomi, statuscomi=:cstatuscomi
            where codemp=:icodemp and codfilial=:scodfilialcs and codcomi=:icodcomi and codempvd=:codempvd and
            codfilialvd=:codfilialvd and codvend=:codvend and statuscomi!='CP' ;

        end

    end
    suspend;
end
^

/* Alter (VDADICITORCRECMERCSP) */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod char(13);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitorc numeric(15,5);
declare variable qtditorc numeric(15,5);
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemppg integer;
declare variable codfilialpg smallint;
declare variable codplanopag integer;
declare variable gerachamado char(1);
declare variable obsitorc varchar(10000);
declare variable descprod char(100);
declare variable vlrliqitorc numeric(15,5);
declare variable vlrproditorc numeric(15,5);
declare variable usaprecopecaserv char(1);
declare variable codprodpeca integer;
declare variable garantia char(1);
declare variable codprodir integer;
declare variable refprodir char(13);
begin
    
    -- Buscando preferencias do GMS
    select coalesce(p8.usaprecopecaserv,'N') from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :usaprecopecaserv;

    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos,
        ir.gerachamado, pd.descprod, irm.codprod, irm.garantia, irm.codprod codprodir, irm.refprod refprodir
        from eqitrecmercitos ir, eqitrecmerc irm, eqproduto pd
        where
        irm.codemp=ir.codemp and irm.codfilial=ir.codfilial and irm.ticket=ir.ticket and irm.coditrecmerc=ir.coditrecmerc
        and pd.codemp=irm.codemppd and pd.codfilial=irm.codfilialpd and irm.codprod=pd.codprod
        and ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc,
             :gerachamado, :descprod, :codprodpeca, :garantia, :codprodir, :refprodir
        do
        begin

--            if(:codprod <> :codprodant or :codprodant is null) then
--            begin

                -- Verifica se é serviço, sendo serviço insere a descriçao do produto
                -- consertado na descrição auxiliar do item de orçamento
                if(:gerachamado=:servicos and :gerachamado='S') then
                begin
                    if( 'N' = :garantia ) then
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod;
                    end
                    else
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod || '[G]';
                    end

                end

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda
                -- Se não está em garantia...

                if('N' = :garantia) then
                begin
                    -- Se o preço é basedo na peca, deve buscar o preço da peça
                    if(usaprecopecaserv='S') then
                    begin
                        select preco from vdbuscaprecosp(:codprodpeca,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                    else
                    begin
                        select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                end
                else
                begin

                    precoitorc = 0.00;

                end

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                vlrproditorc = :qtditorc * :precoitorc;
                vlrliqitorc = vlrproditorc;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox, obsitorc, vlrproditorc, vlrliqitorc, sitproditorc)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox, :obsitorc, :vlrproditorc, :vlrliqitorc,
                'PE') ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

--            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* empty dependent procedure body */
/* Clear: ATBUSCAPRECOSP for: VDBUSCAPRECOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDADICITORCRECMERCSP for: VDBUSCAPRECOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter (VDBUSCAPRECOSP) */
ALTER PROCEDURE VDBUSCAPRECOSP(ICODPROD INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPTM INTEGER,
ICODFILIALTM SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(14,5))
 AS
declare variable icodtab integer;
declare variable icodemptab integer;
declare variable icodfilialtab smallint;
declare variable icodclascli integer;
declare variable icodempclascli integer;
declare variable icodfilialclascli smallint;
declare variable percdesccli numeric(3,2);
declare variable desccli char(1);
declare variable arredpreco smallint;
declare variable codfilialpf integer;
declare variable centavos decimal(2,2);
begin
    -- Buscando código da filial de preferencias
    select icodfilial from sgretfilial(:icodemp,'SGFILIAL') into :codfilialpf;

    -- Buscando preferencias de arredondamento;
    select coalesce(arredpreco, 0)
    from sgprefere1 p1
    where p1.codemp=:icodemp and p1.codfilial=:codfilialpf
    into :arredpreco;

    -- Buscando tabela de preços do tipo de movimento;
    select codtab, codemptb, codfilialtb
    from eqtipomov
    where codtipomov=:icodtipomov and codemp=:icodemptm and codfilial=:icodfilialtm
    into :icodtab, :icodemptab, :icodfilialtab;

    -- Buscando informações do cliente;
    select codclascli, codempcc, codfilialcc, coalesce(percdesccli,0) percdesccli
    from vdcliente
    where codcli=:icodcli and codemp=:icodempcl and codfilial=:icodfilialcl
    into :icodclascli, :icodempclascli, icodfilialclascli, :percdesccli;

    -- Buscando preço da tabela de preços utilizando todos os filtros
    select precoprod from vdprecoprod pp
    where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg and pp.codfilialpg=:icodfilialpg
    and pp.codtab=:icodtab and pp.codemptb=:icodemptab and pp.codfilialtb=:icodfilialtab
    and pp.codclascli=:icodclascli and pp.codempcc=:icodempclascli and pp.codfilialcc=:icodfilialclascli
    and pp.codemp=:icodemp and pp.codfilial=:icodfilial
    into :preco;

    --Se não encontrou um preço de tabela usando todos os filtros, deve retirar o filtro de classificação do cliente
    if ((preco is null) or (preco = 0)) then
    begin
        select max(pp.precoprod) from vdprecoprod pp
        where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg
        and pp.codfilialpg=:icodfilialpg and pp.codtab=:icodtab and pp.codemptb=:icodemptab
        and pp.codfilialtb=:icodfilialtab and pp.codclascli is null
        and pp.codemp=:icodemp and pp.codfilial=:icodfilial
        into :preco;
    end

    --Se ainda não conseguiu pagar o preco, deve utilizar o preço base do produto aplicando o desconto especial do cliente se houver
    if ((preco is null) or (preco = 0)) then
    begin

        select coalesce(pd.precobaseprod,0), coalesce(pd.desccli,'N') from eqproduto pd
        where pd.codprod=:icodprod and pd.codemp=:icodemp and pd.codfilial=:icodfilial
        into :preco, :desccli;

        -- Verifica se o cliente possui desconto especial e o produto permite este desconto...
        if( percdesccli >0 and 'S' = :desccli ) then
        begin

            preco = :preco - (:preco * (:percdesccli / 100)) ;

        end

    end

    if( :arredpreco > 0 ) then
    begin

        -- capturando valor dos centavos
        centavos = ( cast(:preco as decimal(15,2)) - truncate(preco) ) * 10;

        -- se o valor em centavos é maior ou igual ao parametro de arredondamento (arredondar para cima)
        if(:centavos >= :arredpreco) then
        begin
            preco = truncate(preco) + 1;
        end
        else
        begin
            preco = truncate(preco);
        end

    end

    suspend;

end
^

/* Alter (VDESTORNACOMISSAOSP) */
ALTER PROCEDURE VDESTORNACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
ICODVEND INTEGER,
DINI DATE,
DFIM DATE,
CORDEM CHAR(1))
 AS
declare variable icodcomi integer;
declare variable icodemprc integer;
declare variable scodfilialrc smallint;
declare variable icodrec integer;
declare variable inparcitrec integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable nvlrcomi numeric(15,5);
declare variable ddatacomi date;
declare variable ddtvenccomi date;
declare variable ctipocomi char(1);
declare variable cstatuscomi char(2);
declare variable datual date;
declare variable cstatusitrec char(2);
declare variable ddtvencitrec date;
begin
  /* Procedure Text */
  DATUAL = CAST( 'now' AS DATE);
  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTVENCCOMI,
      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
    WHERE C.CODEMP=:ICODEMP AND C.CODFILIAL=:SCODFILIAL AND
       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
       R.CODREC=C.CODREC AND R.CODEMPVD=:ICODEMPVD AND
       R.CODFILIALVD=:SCODFILIALVD AND R.CODVEND=:ICODVEND AND
       ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       ( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
       C.STATUSCOMI IN ('C2','CP') AND
       IR.STATUSITREC NOT IN ('RP') AND
       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
    INTO :ICODCOMI, :ICODEMPRC, :SCODFILIALRC, :ICODREC, :INPARCITREC, :NVLRVENDACOMI,
      :NVLRCOMI, :DDATACOMI, :DDTVENCCOMI, :CTIPOCOMI, :CSTATUSCOMI, :CSTATUSITREC,
      :DDTVENCITREC
  DO
  BEGIN
     IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
     BEGIN
        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
          WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND
            CODCOMI=:ICODCOMI;
     END
     ELSE IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
     BEGIN
        NVLRCOMI = NVLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
        EXECUTE PROCEDURE vdadiccomissaosp(:ICODEMPRC,:SCODFILIALRC,:ICODREC,
          :INPARCITREC, :NVLRVENDACOMI, :NVLRCOMI, :DDATACOMI , :DDTVENCITREC,
          :CTIPOCOMI, :icodempvd, :scodfilialvd, : icodvend );
     END
  END
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: FNITRECEBERSP01 for: VDGERACOMISSAOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter (VDGERACOMISSAOSP) */
ALTER PROCEDURE VDGERACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRCOMIITREC NUMERIC(15,5),
DDTVENCITREC DATE)
 AS
declare variable icodempva integer;
declare variable scodfilialva smallint;
declare variable ctipovenda char(3);
declare variable icodvenda integer;
declare variable icodempcm integer;
declare variable scodfilialcm smallint;
declare variable icodclcomis integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable ddatacomi date;
declare variable npercfatclcomis numeric(9,2);
declare variable npercpgtoclcomis numeric(9,2);
declare variable ctipocomi char(1);
declare variable nvlrcomi numeric(15,5);
declare variable i integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable cmulticomis char(1);
declare variable icodempvd integer; /* Código da empresa do comissionado principal */
declare variable scodfilialvd smallint; /* Código da filial do comissionado principal */
declare variable icodvend integer; /* Código do comissionado principal */
declare variable nperccomisvendadic numeric(15,2); /* Percentual de comissão para cada comissionado adicional */
declare variable nvlrcomiadic numeric(15,5); /* Valor total da comissão para os comissionados adicionais. */
declare variable icodempvdadic integer; /* Código da empresa do comissionado adicional */
declare variable scodfilialvdadic smallint; /* Código da filial do comissionado adicional */
declare variable icodvendadic integer; /* Código do comissionado adicional */
declare variable nvlrcomiparc numeric(15,5); /* Valor da comissão por vendedor parcial. */
begin
    /* Gera as comissões a pagar na tabela VDCOMISSAO */


    nvlrcomiadic = 0;

    select r.codempva, r.codfilialva, r.tipovenda, r.codvenda,
        v.codempcm, v.codfilialcm, v.codclcomis, ( v.vlrprodvenda - v.vlrdescvenda ),
        v.dtemitvenda, cm.percfatclcomis, cm.percpgtoclcomis,
        v.codemptm, v.codfilialtm, v.codtipomov,
        v.codempvd, v.codfilialvd, v.codvend
        from fnreceber r, vdvenda v, vdclcomis cm
        where r.codemp=:icodemp and r.codfilial=:scodfilial and r.codrec=:icodrec
            and v.codemp=r.codempva and v.codfilial=r.codfilialva and v.tipovenda=r.tipovenda
            and v.codvenda=r.codvenda and cm.codemp=v.codempcm and cm.codfilial=v.codfilialcm
            and cm.codclcomis=v.codclcomis
    into :icodempva, :scodfilialva, :ctipovenda, :icodvenda,
         :icodempcm, :scodfilialcm, :icodclcomis, :nvlrvendacomi,
         :ddatacomi, :npercfatclcomis, :npercpgtoclcomis,
         :icodemptm, :scodfilialtm, :icodtipomov,
         :icodempvd, :scodfilialvd, :icodvend ;

    /*Verifica se deve utilizar mecanismo de multiplos comissionados*/

    select cmulticomis from sgretmulticomissp(:icodemp, :icodemptm, :scodfilialtm, :icodtipomov)
        into cmulticomis;

    if(cmulticomis = 'S') then
    begin

        /*Implementação do mecanismo de multiplos comissionados*/
        
        for select vc.codempvd, vc.codfilialvd, vc.codvend, vc.percvc
            from vdvendacomis vc
            where vc.codemp=:icodempva and vc.codfilial=:scodfilialva and vc.codvenda=:icodvenda
                and vc.tipovenda=:ctipovenda and vc.codvend is not null
        into :icodempvdadic, :scodfilialvdadic, :icodvendadic, :nperccomisvendadic do
        begin

            /* Calcula o valor da comissão proporcional para cada comissionado adicional*/

            nvlrcomi = cast( ( nvlrcomiitrec * nperccomisvendadic / 100 ) as numeric(15,5));
            I = 1;
            while (:I<=2) do
            begin
                if (I=1) then
                    begin
                        ctipocomi='F';
                        nvlrcomiparc = cast( ( nvlrcomi * npercfatclcomis / 100 ) as NUMERIC(15, 5));
                    end
                else
                    begin
                        ctipocomi='R';
                        nvlrcomiparc = cast( (nvlrcomi * npercpgtoclcomis / 100 ) as NUMERIC(15, 5));
                    end
                execute procedure vdadiccomissaosp(:iCodEmp, :sCodFilial, :iCodRec, :iNParcItRec,
                    :nVlrVendaComi, :nvlrcomiparc, :dDataComi, :dDtVencItRec, cTipoComi,:icodempvdadic, :scodfilialvdadic, :icodvendadic );
                I=I+1;
                /*Acumula as comissões adicionais para posteriormente descontar do valor principal*/
                nvlrcomiadic = nvlrcomiadic + nvlrcomiparc;

           /*     exception vdcomissaoex02 'rodou procedure para o vendedor:' || cast(:icodempvdadic as char(2)) || '-' || cast(:scodfilialvdadic as char(2)) || '-' || cast(:icodvendadic as char(2)) || '-' || ' - nvlrcomiadic:' || cast(nvlrcomiadic as char(20));*/

            end

        end

    end

    /*Comissionamento do vendedor principal*/

    I = 1;
    while (:I<=2) do
        begin
            if (I=1) then
                begin
                    ctipocomi='F';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercFatClComis / 100 ) as numeric(15, 5) );
                end
            else
                begin
                    ctipocomi='R';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercPgtoClComis / 100 ) as numeric(15, 5));
                end
/*
                exception vdcomissaoex01 ''
                || cast(:icodempvd as char(2)) || '-' || cast(:scodfilialvd as char(2)) || '-' || cast(:icodvend as char(2))
                || 'nvlrcomi:' || cast(:nvlrcomi as char(20))
                || 'nvlrvendacomi:' || cast(:nvlrvendacomi as char(20));
  */
            execute procedure vdadiccomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvencitrec, ctipocomi, :icodempvd, :scodfilialvd, :icodvend );


            I=I+1;
        end

        suspend;
end
^

/* Restore procedure body: ATBUSCAPRECOSP */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE iCodEmpTM INTEGER;
  DECLARE VARIABLE iCodFilialTM INTEGER;
  DECLARE VARIABLE iCodCli INTEGER;
  DECLARE VARIABLE iCodEmpCli INTEGER;
  DECLARE VARIABLE iCodFilialCli INTEGER;
BEGIN
  SELECT CODTIPOMOV2,CODEMPT2,CODFILIALT2 FROM SGPREFERE1 WHERE CODEMP=:ICODEMP
         AND CODFILIAL=:ICODFILIAL INTO iCodTipoMov,iCodEmpTM,iCodFilialTM;
  SELECT CODCLI,CODEMPCL,CODFILIALCL FROM ATCONVENIADO WHERE CODCONV=:ICODCONV
         AND CODEMP=:ICODEMPCV AND CODFILIAL=:ICODFILIALCV INTO iCodCli,iCodEmpCli,iCodFilialCli;

  SELECT PRECO FROM VDBUSCAPRECOSP(:ICODPROD,:iCodCli,:iCodEmpCli,:iCodFilialCli,:ICODPLANOPAG,:ICODEMPPG,
    :ICODFILIALPG,:iCodTipoMov,:iCodEmpTM,:iCodFilialTM,:ICODEMP,:ICODFILIAL) INTO PRECO;

  SUSPEND;
END
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
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc
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

                vlrproditcompra = :precoitcompra * qtditcompra;


                -- Inserir itens
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra) ;

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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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

/* Restore procedure body: FNITRECEBERSP01 */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR(1))
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin

        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
    end
    suspend;
end
^

/* Restore procedure body: VDADICITORCRECMERCSP */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod char(13);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitorc numeric(15,5);
declare variable qtditorc numeric(15,5);
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemppg integer;
declare variable codfilialpg smallint;
declare variable codplanopag integer;
declare variable gerachamado char(1);
declare variable obsitorc varchar(10000);
declare variable descprod char(100);
declare variable vlrliqitorc numeric(15,5);
declare variable vlrproditorc numeric(15,5);
declare variable usaprecopecaserv char(1);
declare variable codprodpeca integer;
declare variable garantia char(1);
declare variable codprodir integer;
declare variable refprodir char(13);
begin
    
    -- Buscando preferencias do GMS
    select coalesce(p8.usaprecopecaserv,'N') from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :usaprecopecaserv;

    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos,
        ir.gerachamado, pd.descprod, irm.codprod, irm.garantia, irm.codprod codprodir, irm.refprod refprodir
        from eqitrecmercitos ir, eqitrecmerc irm, eqproduto pd
        where
        irm.codemp=ir.codemp and irm.codfilial=ir.codfilial and irm.ticket=ir.ticket and irm.coditrecmerc=ir.coditrecmerc
        and pd.codemp=irm.codemppd and pd.codfilial=irm.codfilialpd and irm.codprod=pd.codprod
        and ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc,
             :gerachamado, :descprod, :codprodpeca, :garantia, :codprodir, :refprodir
        do
        begin

--            if(:codprod <> :codprodant or :codprodant is null) then
--            begin

                -- Verifica se é serviço, sendo serviço insere a descriçao do produto
                -- consertado na descrição auxiliar do item de orçamento
                if(:gerachamado=:servicos and :gerachamado='S') then
                begin
                    if( 'N' = :garantia ) then
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod;
                    end
                    else
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod || '[G]';
                    end

                end

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda
                -- Se não está em garantia...

                if('N' = :garantia) then
                begin
                    -- Se o preço é basedo na peca, deve buscar o preço da peça
                    if(usaprecopecaserv='S') then
                    begin
                        select preco from vdbuscaprecosp(:codprodpeca,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                    else
                    begin
                        select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                end
                else
                begin

                    precoitorc = 0.00;

                end

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                vlrproditorc = :qtditorc * :precoitorc;
                vlrliqitorc = vlrproditorc;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox, obsitorc, vlrproditorc, vlrliqitorc, sitproditorc)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox, :obsitorc, :vlrproditorc, :vlrliqitorc,
                'PE') ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

--            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Create Trigger... */
CREATE TRIGGER EQITRECMERCSERIETGBIBU FOR EQITRECMERCSERIE
ACTIVE BEFORE INSERT POSITION 0 
as
    declare variable temserie smallint;
begin
    -- Verifica se numero de série já foi inserido nessa entrada
    select count(*) from eqitreCmercserie its
    where its.codemp = new.codemp and its.codfilial=new.codfilial and its.ticket=new.ticket
    and its.codemppd=new.codemppd and its.codfilialpd=new.codfilialpd and its.codprod=new.codprod
    and its.numserie = new.numserie and its.numserie is not null and its.seqitserie!=new.seqitserie
    into temserie;

    if(temserie > 0) then
    begin
        exception cpitcompraex03 new.numserie;
    end


end
^

CREATE TRIGGER EQITRECMERCSERIETGBU FOR EQITRECMERCSERIE
ACTIVE BEFORE UPDATE POSITION 0 
as
    declare variable temserie smallint;
begin
    -- Atualizando log
    new.DTALT=cast('now' as date);
    new.IDUSUALT=USER;
    new.HALT = cast('now' as time);

    -- Verificando se foi inserido um numero de série
    if(old.numserie is null and new.numserie is not null) then
    begin
        -- Verificando se o número de série informado, já existe.
        select count(*) from eqserie sr
        where sr.codemp = new.codemppd and sr.codfilial = new.codfilialpd and sr.codprod = new.codprod and sr.numserie = new.numserie
        into :temserie;

        -- se o número de série informado não existe, deve inseri-lo;
        if (temserie = 0) then
        begin
            insert into eqserie (codemp, codfilial, codprod, numserie)
            values(new.codemppd, new.codfilialpd, new.codprod, new.numserie);
        end

    end

end
^

CREATE TRIGGER EQITRECMERCTGAI FOR EQITRECMERC
ACTIVE AFTER INSERT POSITION 0 
AS
begin
    -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure eqitrecmercseriesp('I', new.codemp, new.codfilial, new.ticket, new.coditrecmerc, new.codemppd, new.codfilialpd, new.codprod, new.numserie, new.qtditrecmerc);

end
^

CREATE TRIGGER EQITRECMERCTGBD FOR EQITRECMERC
ACTIVE BEFORE DELETE POSITION 0 
AS
begin

    -- Executa procedure para exclusão de tabela de vinculo para numeros de serie
    execute procedure eqitrecmercseriesp('D', old.codemp, old.codfilial, old.ticket, old.coditrecmerc, old.codemppd, old.codfilialpd, old.codprod, null, old.qtditrecmerc);


end
^

CREATE TRIGGER EQRECMERCTGAU FOR EQRECMERC
ACTIVE AFTER UPDATE POSITION 0 
AS
begin
    -- Mecanismo de contingência do status dos itens de OS

    if( new.status='PT' ) then
    begin
        update eqitrecmercitos set statusitos = 'CO'
        where codemp=new.codemp and codfilial=new.codfilial and ticket = new.ticket;
    end


end
^

CREATE TRIGGER FNCONTAVINCULADATGBU FOR FNCONTAVINCULADA
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER VDSETORROTATGBU FOR VDSETORROTA
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
new.DTALT=cast('now' as date);
new.IDUSUALT=USER;
new.HALT = cast('now' as time);
end
^


/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSITORCTGAU
AS
begin
    -- Atualizando status da OS
    if(old.status!=new.status) then
    begin
        -- Implementar futuramento o status parcial
        update eqitrecmercitos rm set rm.statusitos =new.status
        where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
        and rm.coditrecmerc=new.coditrecmerc and rm.coditos=new.coditos;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSTGAU
as
declare variable num_os_tot integer;
declare variable num_os_status integer;
declare variable status_atual_os char(2);
declare variable novo_status_os char(2);
declare variable novo_status_it char(2);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable codorc integer;
declare variable tipoorc char(1);
declare variable coditorc smallint;

begin

    -- Buscando status geral da OS
    select rm.status from eqrecmerc rm
    where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
    into status_atual_os;

--    execute procedure sgdebugsp('eqitrecmercitostgau','statusatual:' || status_atual_os);

    -- Verifica se deve atualizar o status geral da OS
    if(status_atual_os in('PE','AN','EA','EC','OA','EO')) then
    begin

        -- carregando quantidade de itens para a OS
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
        into num_os_tot;

        -- carregando quantidade de ítens na situação atual
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
        and os.statusitos=new.statusitos
        into num_os_status;

--        execute procedure sgdebugsp('eqitrecmercitostgau','num_os_status' || num_os_status);

        -- Atualização do status da ordem de serviço
    
        if(num_os_status = num_os_tot) then
        begin
            if(new.statusitos = 'CO') then
            begin
                novo_status_os = 'PT';
                novo_status_it = 'FN';
            end
            else
            begin
                novo_status_os = new.statusitos;
                novo_status_it = new.statusitos;
            end

            update eqitrecmerc ir set ir.statusitrecmerc =:novo_status_it
            where ir.codemp=new.codemp and ir.codfilial=new.codfilial and ir.ticket=new.ticket and ir.coditrecmerc=new.coditrecmerc;

            update eqrecmerc rm set rm.status=:novo_status_os
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;

        end

        -- Quando ítem for concluído, deve alterar o status do orçamento para 'PRODUZIDO' OP;

        if(new.statusitos='CO') then
        begin

            for select itos.codempoc, itos.codfilialoc, itos.codorc, itos.tipoorc, itos.coditorc
            from eqitrecmercitositorc itos
            where itos.codemp=new.codemp and itos.codfilial=new.codfilial and itos.ticket=new.ticket
            and itos.coditrecmerc=new.coditrecmerc and itos.coditos=new.coditos
            into :codempoc, :codfilialoc, :codorc, :tipoorc, :coditorc
            do
            begin

                update vditorcamento ito set ito.statusitorc='OP', ito.sitproditorc='PD', ito.aceiteitorc='S', ito.aprovitorc='S'
                where ito.codemp=:codempoc and ito.codfilial=:codfilialoc and ito.tipoorc=:tipoorc and ito.codorc=:codorc and ito.coditorc=:coditorc;

            end

        end


    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSTGBI
AS
declare variable rmaprod char(1);
declare variable tipoprod char(1);

begin
    -- Buscando informação do produto
    select pd.rmaprod, pd.tipoprod from eqproduto pd
    where pd.codemp=new.codemppd and pd.codfilial=new.codfilialpd and pd.codprod=new.codprodpd
    into :rmaprod, :tipoprod;

    if('S' = :rmaprod) then
    begin
        
        new.gerarma = 'S';
        new.statusitos = 'AN';

    end

    -- Se o produto é de Comercio, Equipamento ou Fabricação deve sinalizar
    -- o Ítem de OS como um produto novo, para substituição.

    if(:tipoprod in ('P','E','F')) then
    begin
--        new.gerarma = 'N';
        new.geranovo = 'S';
        new.statusitos = 'AN';
    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCTGAU
as
    declare variable tipoprocrecmerc char(2);

begin

  -- buscando tipo de amostragem

    select pr.tipoprocrecmerc
    from eqprocrecmerc pr, eqitrecmerc ir
    where ir.codemp=new.codemp and ir.codfilial=new.codfilial and ir.ticket=new.ticket and ir.coditrecmerc=new.coditrecmerc
    and pr.codemp=ir.codemptp and pr.codfilial=ir.codfilialtp and pr.codtiporecmerc=ir.codtiporecmerc and pr.codprocrecmerc=ir.codprocrecmerc
    into :tipoprocrecmerc;

    if( ( coalesce(new.mediaamostragem,0) > 0 )  and ( coalesce(new.rendaamostragem,0) > 0 ) ) then
    begin

        update eqrecmerc rm set
        rm.mediaamostragem = ( coalesce(rm.mediaamostragem,new.mediaamostragem) + new.mediaamostragem ) / 2,
        rm.rendaamostragem = ( coalesce(rm.rendaamostragem,new.rendaamostragem) + new.rendaamostragem ) / 2
        where
        rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;

    end

    if(new.statusitrecmerc='FN') then
    begin

        if (tipoprocrecmerc='PI') then
        begin
            update eqrecmerc rm set rm.status='E1'
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
        else if (tipoprocrecmerc='TR') then
        begin
            update eqrecmerc rm set
            rm.status='E2',
            rm.rendaamostragem=new.rendaamostragem
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
        else if (tipoprocrecmerc='PF') then
        begin
            update eqrecmerc rm set rm.status='FN'
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
    end


    -- Executa procedure para atualização de tabela de vinculo para numeros de serie
    execute procedure eqitrecmercseriesp('U', old.codemp, old.codfilial, old.ticket, old.coditrecmerc, old.codemppd, old.codfilialpd, old.codprod, new.numserie, new.qtditrecmerc);


end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAIAU
AS
declare variable icodemptm int;
declare variable icodfilialtm smallint;
declare variable icodtipomov integer;
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

   select idusus from sgretinfousu(USER)
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
ALTER TRIGGER EQRMATGAU
AS
begin
    if(new.sitrma='AF')  then
    begin
      update eqitrma set sititrma = 'AF' where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma and sititrma!='AF';
    end
    else if (new.sitrma='CA')  then
    begin
      update eqitrma set sititrma = 'CA' where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma and sititrma!='CA';
    end
    else if(new.sitaprovrma='AT')  then
    begin
      update eqitrma set sitaprovitrma = 'AT' where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma and sitaprovitrma!='AT';
    end
    if(new.sitrma='EF')  then
    begin
      update eqitrma it set sititrma = 'EF',precoitrma=(select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma and sititrma!='EF';
    end
    if(new.sitexprma='ET')  then
    begin
      update eqitrma set sitexpitrma = 'ET' where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma and sitexpitrma!='ET';
    end
    if(new.codtipomov!=old.codtipomov) then
    begin
        update eqitrma it set precoitrma=(select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null))
            where codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNRECEBERTGAI
AS
  DECLARE VARIABLE NPARCREC INTEGER;
  DECLARE VARIABLE CODFILIALPP SMALLINT;
  DECLARE VARIABLE CODFILIALPN SMALLINT;
  DECLARE VARIABLE CODPLAN CHAR(13);
  DECLARE VARIABLE CODCC CHAR(19);
  DECLARE VARIABLE ANOCC SMALLINT;
  DECLARE VARIABLE CODFILIALCC SMALLINT;
  DECLARE VARIABLE CODFILIALIR SMALLINT;
  DECLARE VARIABLE CODFILIALCL SMALLINT;
  DECLARE VARIABLE ATIVOCLI CHAR(1);
  DECLARE VARIABLE NUMRESTR INTEGER;
  DECLARE VARIABLE CODFILIALCA SMALLINT;
  DECLARE VARIABLE CODEMPCA INTEGER;
  DECLARE VARIABLE NUMCONTA CHAR(10);

BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'VDCLIENTE') INTO CODFILIALCL;
  SELECT C.ATIVOCLI  FROM VDCLIENTE C WHERE C.CODEMP=NEW.CODEMP AND
     C.CODFILIAL=:CODFILIALCL AND C.CODCLI=NEW.CODCLI INTO :ATIVOCLI;
  IF (ATIVOCLI<>'S') THEN
  BEGIN
     EXCEPTION FNRECEBEREX01;
  END
  SELECT COUNT(*) FROM FNRESTRICAO R, FNTIPORESTR TR
    WHERE R.CODEMP=NEW.CODEMP AND R.CODFILIAL=:CODFILIALCL AND
      R.CODCLI=NEW.CODCLI AND R.SITRESTR IN ('I') AND
      TR.CODEMP=R.CODEMPTR AND TR.CODFILIAL=R.CODFILIALTR AND
      TR.CODTPRESTR=R.CODTPRESTR AND TR.BLOQTPRESTR='S'
      INTO :NUMRESTR;
  IF ( (NUMRESTR IS NOT NULL) AND (NUMRESTR>0) ) THEN
  BEGIN
     IF (NUMRESTR=1) THEN
     BEGIN
        EXCEPTION FNRECEBEREX02 'Cliente possui '||RTRIM(CAST(NUMRESTR AS CHAR(10)))||' restrição!';
     END
     ELSE
     BEGIN
        EXCEPTION FNRECEBEREX02 'Cliente possui '||RTRIM(CAST(NUMRESTR AS CHAR(10)))||' restrições!';
     END
  END
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNPLANOPAG') INTO CODFILIALPP;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNPLANEJAMENTO') INTO CODFILIALPN;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNCENTROCUSTO') INTO CODFILIALCC;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNITRECEBER') INTO CODFILIALIR;

  if(new.codvenda is not null and new.numconta is null) then
  begin
    select codempca,codfilialca,numconta from vdvenda
    where codemp=new.codempva and codfilial=new.codfilialva and codvenda=new.codvenda and tipovenda=new.tipovenda
    into :codempca, :codfilialca, :numconta;
  end
  else
  begin
    codempca = new.codempca;
    codfilialca = new.codfilialca;
    numconta = new.numconta;
  end


  SELECT I FROM fngeraitrecebersp01('S',new.CODEMP,
    new.CODFILIAL, new.CODREC, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
    new.VLRREC, new.DATAREC, new.FLAG, new.CODEMPBO, new.CODFILIALBO,
    new.CODBANCO, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
    new.CODEMPCB, new.CODFILIALCB, new.CODCARTCOB,
    new.VLRCOMIREC, new.OBSREC, :codempca, :codfilialca, :numconta,
    new.codemppn, new.codfilialpn, new.codplan, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.vlrbasecomis
    ) INTO :NPARCREC;

/* Se a parcela do plano de pagamento tiver marcado para autobaixa, então é realizada a baixa automática. */
  FOR SELECT PP.NUMCONTA,PP.CODFILIALCA,PP.CODEMPCA, PP.CODPLAN,PP.CODFILIALPN,PP.CODCC,PP.ANOCC,PP.CODFILIALCC,PC.NROPARCPAG
      FROM FNPARCPAG PC, FNPLANOPAG PP
      WHERE
        PP.CODEMP=NEW.CODEMP AND PP.CODFILIAL=:CODFILIALPP AND PP.CODPLANOPAG=NEW.CODPLANOPAG AND
        PC.CODEMP=PP.CODEMP AND PC.CODFILIAL=PP.CODFILIAL AND PC.CODPLANOPAG=PP.CODPLANOPAG AND
        PC.AUTOBAIXAPARC='S'
      INTO NUMCONTA,CODFILIALCA,CODEMPCA, CODPLAN,CODFILIALPN,CODCC,ANOCC,CODFILIALCC,NPARCREC
  DO
  BEGIN
    UPDATE FNITRECEBER SET
      NUMCONTA=:NUMCONTA,
      CODEMPCA=:CODEMPCA,
      CODFILIALCA=:CODFILIALCA,
      NUMCONTA=:NUMCONTA,
      CODPLAN=:CODPLAN,
      CODEMPPN=NEW.CODEMP,
      CODFILIALPN=:CODFILIALPN,
      ANOCC=:ANOCC,
      CODCC=:CODCC,
      CODEMPCC=NEW.CODEMP,
      CODFILIALCC=:CODFILIALCC,
      DOCLANCAITREC='AUTO',
      DTPAGOITREC=NEW.DATAREC,
      VLRPAGOITREC=VLRPARCITREC,
      VLRDESCITREC=0,
      VLRJUROSITREC=0,
      OBSITREC='BAIXA AUTOMÁTICA',
      STATUSITREC='RP'
      WHERE CODREC=NEW.CODREC AND CODEMP=NEW.CODEMP AND CODFILIAL=:CODFILIALIR AND
      NPARCITREC=:NPARCREC;
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNRECEBERTGAU
as
    declare variable inparcold integer;
    declare variable inparcnew integer;
    declare variable caltvlr char(1);
    declare variable regrvcto char(1);
    declare variable rvsab char(1);
    declare variable rvdom char(1);
    declare variable rvfer char(1);
    declare variable rvdia char(1);
    declare variable diavcto smallint;
    declare variable diaspag smallint;
    declare variable dtvencto date;
    declare variable nroparcrec smallint;

    begin

        -- Se houver alterações relevantes...
        if ( ((old.codplanopag<>new.codplanopag) or (old.flag<>new.flag) or
              (old.vlrrec<>new.vlrrec) or (old.datarec<>new.datarec)) and
              (new.altusurec != 'S') ) then

        begin

            -- Se foi alterado o plano de pagamento            
            if (old.codplanopag <> new.codplanopag) then
            begin

                -- Buscando informações do plano de pagamento antigo
                select pp.parcplanopag from fnplanopag pp
                where codemp=old.codemppg and pp.codfilial=old.codfilialpg and pp.codplanopag=old.codplanopag
                into :inparcold;

                -- Buscando informações do novo plano de pagamento
                select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag,
                pp.rvferplanopag, pp.rvdiaplanopag, pp.diavctoplanopag
                from fnplanopag pp
                where pp.codemp=new.codemppg and pp.codfilial=new.codfilialpg and pp.codplanopag = new.codplanopag
                into :inparcnew, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

                -- Se o numero de parcelas entre os planos de pagamento são diferentes.
                if (inparcnew != inparcold) then
                begin
                    -- exclui os itens de contas a receber, para recria-los.
                    delete from fnitreceber where codrec=new.codrec and codemp=new.codemp and codfilial=new.codfilial;
                end
                -- Caso possua apenas uma parcela deve apenas atualizar a data do vencimento.
                else if (inparcnew=1) then
                begin
                    -- Buscando informação do item do plano de pagamento
                    select diaspag from fnparcpag
                    where codplanopag=new.codplanopag and codemp=new.codemppg and codfilial=new.codfilialpg
                    into :diaspag;

                    -- Calculando o vencimento da parcela.
                    select c.dtvencret from sgcalcvencsp( new.codemppg, new.datarec, new.datarec + :diaspag,
                    :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :diaspag ) c
                    into :dtvencto;

                    -- Atualizando item do contas a receber
                    update fnitreceber set dtvencitrec=:dtvencto
                    where codemp=new.codemp and codfilial=new.codfilial and codrec=new.codrec and
                    statusitrec not in ('RP','RL');

                end
            end

            -- Se o valor foi alterado
            if (new.vlrrec!=old.vlrrec) then
                caltvlr = 'S';
            else
                caltvlr = 'N';

            select i from fngeraitrecebersp01(:caltvlr,  new.codemp, new.codfilial, new.codrec, new.codemppg,
            new.codfilialpg, new.codplanopag, new.vlrrec, new.datarec, new.flag, new.codempbo, new.codfilialbo,
            new.codbanco, new.codemptc, new.codfilialtc, new.codtipocob, new.codempcb, new.codfilialcb, new.codcartcob,
            new.vlrcomirec, new.obsrec, null, null, null, new.codemppn, new.codfilialpn,new.codplan, new.codempcc,
            new.codfilialcc, new.anocc, new.codcc, new.vlrbasecomis)
            into :nroparcrec;

        end

        -- Atualiza os itens para eles atualizarem a comissao de forma distribuida.
        if (old.vlrcomirec != new.vlrcomirec) then
        begin
            execute procedure fnitrecebersp01(new.codemp, new.codfilial, new.codrec, new.vlrparcrec,
            new.vlrcomirec, new.nroparcrec, 'N');
        end
    end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAI
as
declare variable preco numeric;
declare variable qtdest numeric;
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

   EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
        new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
        new.codfilialtm, new.codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop,
        null, null,  null, new.dtfabrop, new.codop,
        'N',new.qtdfinalprodop,cast(:preco as numeric(15,5)),
        NEW.codempax, NEW.codfilialax, NEW.codalmox );
   if (new.CODOPM is not null) then
      EXECUTE PROCEDURE PPATUDISTOPSP(new.CODEMPOPM, new.CODFILIALOPM, new.CODOPM,
        new.SEQOPM, 0, new.QTDDISTIOP);
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
          null, null, null, null,null,null, null, null,
         old.CODEMPNT, old.CODFILIALNT, old.CODNAT, :DDTVENDA,
         :IDOCVENDA, :CFLAG, old.QTDITVENDA, old.PRECOITVENDA,
         old.CODEMPAX, old.CODFILIALAX, old.CODALMOX);
  END
END
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
        new.CODITVENDA, null, null, null, null,null,null,null, null,
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
ALTER TRIGGER VDITVENDATGBI
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
            select percissfilial from sgfilial where codemp=new.codemp and codfilial=new.codfilial
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
ALTER TRIGGER VDITVENDATGBU
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
                select percissfilial from sgfilial where codemp=new.codemp and codfilial=new.codfilial into new.percissitvenda;

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
                into percred, percipiitvenda, tipofisc, tpredicms, redbasefrete ;

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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis);
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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis);
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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec, new.vlrbasecomis);
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
                   new.flag, new.obsrec, new.vlrbasecomis);
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
            and codempva=new.codemp and codfilialva = new.codfilial into :nvlrparcrec;
        if (new.vlrliqvenda != :nvlrparcrec) then
            exception vdvendaex06;
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

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGBI
AS
  DECLARE VARIABLE sTipoMov CHAR(2);
BEGIN
  EXECUTE PROCEDURE VDCLIENTEATIVOSP(new.CODEMPCL, new.CODFILIALCL, new.CODCLI);
/*  EXECUTE PROCEDURE FNLIBCREDSP(new.CODVENDA,new.CODCLI,new.CODEMPCL,new.CODFILIALCL,null); */
  IF (new.VLRPRODVENDA IS NULL) THEN new.VLRPRODVENDA = 0;
  IF (new.VLRDESCVENDA IS NULL) THEN new.VLRDESCVENDA = 0;
  IF (new.VLRDESCITVENDA IS NULL) THEN new.VLRDESCITVENDA = 0;
  IF (new.VLRVENDA IS NULL) THEN new.VLRVENDA = 0;
  IF (new.VLRBASEICMSVENDA IS NULL) THEN new.VLRBASEICMSVENDA = 0;
  IF (new.VLRICMSVENDA IS NULL) THEN new.VLRICMSVENDA = 0;
  IF (new.VLRPISVENDA IS NULL) THEN new.VLRPISVENDA = 0;
  IF (new.VLRIRVENDA IS NULL) THEN new.VLRIRVENDA = 0;
  IF (new.VLRCSOCIALVENDA IS NULL) THEN new.VLRCSOCIALVENDA = 0;
  IF (new.VLRISENTASVENDA IS NULL) THEN new.VLRISENTASVENDA = 0;
  IF (new.VLROUTRASVENDA IS NULL) THEN new.VLROUTRASVENDA = 0;
  IF (new.VLRBASEIPIVENDA IS NULL) THEN new.VLRBASEIPIVENDA = 0;
  IF (new.VLRIPIVENDA IS NULL) THEN new.VLRIPIVENDA = 0;
  IF (new.VLRLIQVENDA IS NULL) THEN new.VLRLIQVENDA = 0;
  IF (new.VLRCOMISVENDA IS NULL) THEN new.VLRCOMISVENDA = 0;
  IF (new.VLRFRETEVENDA IS NULL) THEN new.VLRFRETEVENDA = 0;
  IF (new.VLRADICVENDA IS NULL) THEN new.VLRADICVENDA = 0;
  IF (new.TIPOVENDA IS NULL) THEN new.TIPOVENDA = 'V';
  IF (new.VLRBASEISSVENDA IS NULL) THEN new.VLRBASEISSVENDA = 0;
  IF (new.VLRISSVENDA IS NULL) THEN new.VLRISSVENDA = 0;
  IF (new.VLRPISVENDA IS NULL) THEN new.VLRPISVENDA = 0;
  IF (new.VLRIRVENDA IS NULL) THEN new.VLRIRVENDA = 0;
  IF (new.VLRCSOCIALVENDA IS NULL) THEN new.VLRCSOCIALVENDA = 0;
  IF (new.vlrbasepisvenda IS NULL) THEN new.vlrbasepisvenda = 0;
  IF (new.vlrbasecofinsvenda IS NULL) THEN new.vlrbasecofinsvenda = 0;
  IF (new.vlrpisvenda IS NULL) THEN new.vlrpisvenda = 0;
  IF (new.vlrcofinsvenda IS NULL) THEN new.vlrcofinsvenda = 0;
  IF (new.vlrbasecomis IS NULL) THEN new.vlrbasecomis = 0;

  IF (new.CODCAIXA IS NULL) THEN
    SELECT CODTERM FROM SGRETCAIXA INTO new.CODCAIXA;
  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'PVCAIXA') INTO new.CODFILIALCX;
  new.CODEMPCX = new.CODEMP;
  IF (NOT new.tipovenda = 'E') THEN -- Se for ECF não precisa buscar o DOC, porque o DOC é o numero do cupom.
      SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMP,new.CODFILIAL) INTO new.DocVenda;
  SELECT TIPOMOV,FISCALTIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
         AND CODEMP=new.CODEMP AND CODFILIAL = new.CODFILIALTM INTO sTipoMov,new.FLAG;
  IF ( new.FLAG <> 'S') THEN
  BEGIN
     new.FLAG = 'N';
  END
  if ((new.STATUSVENDA is null) OR (RTRIM(new.STATUSVENDA) = '*')) then
  BEGIN
     IF (sTipoMov = 'VD') THEN new.STATUSVENDA = 'V1';
     ELSE new.STATUSVENDA = 'P1';
  END
END
^

/* Alter empty procedure FNADICITRECEBERSP01 with new param-list */
ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* Alter (ATBUSCAPRECOSP) */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE iCodEmpTM INTEGER;
  DECLARE VARIABLE iCodFilialTM INTEGER;
  DECLARE VARIABLE iCodCli INTEGER;
  DECLARE VARIABLE iCodEmpCli INTEGER;
  DECLARE VARIABLE iCodFilialCli INTEGER;
BEGIN
  SELECT CODTIPOMOV2,CODEMPT2,CODFILIALT2 FROM SGPREFERE1 WHERE CODEMP=:ICODEMP
         AND CODFILIAL=:ICODFILIAL INTO iCodTipoMov,iCodEmpTM,iCodFilialTM;
  SELECT CODCLI,CODEMPCL,CODFILIALCL FROM ATCONVENIADO WHERE CODCONV=:ICODCONV
         AND CODEMP=:ICODEMPCV AND CODFILIAL=:ICODFILIALCV INTO iCodCli,iCodEmpCli,iCodFilialCli;

  SELECT PRECO FROM VDBUSCAPRECOSP(:ICODPROD,:iCodCli,:iCodEmpCli,:iCodFilialCli,:ICODPLANOPAG,:ICODEMPPG,
    :ICODFILIALPG,:iCodTipoMov,:iCodEmpTM,:iCodFilialTM,:ICODEMP,:ICODFILIAL) INTO PRECO;

  SUSPEND;
END
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
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc
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

                vlrproditcompra = :precoitcompra * qtditcompra;


                -- Inserir itens
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Alter (EQCALCPEPSSP) */
ALTER PROCEDURE EQCALCPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
NSLDPROD NUMERIC(15,5),
DTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NCUSTOPEPS NUMERIC(15,5))
 AS
declare variable dttemp date;
declare variable ncusto numeric(15,5);
declare variable nqtd numeric(15,5);
declare variable nresto numeric(15,5);
declare variable ncustotot numeric(15,5);
declare variable nsldtmp numeric(15,5);
begin
  /* Procedure que retorna o cálculo do custo peps */
  NCUSTO = 0;
  NCUSTOTOT = 0;
  NRESTO = 0;
  NCUSTOPEPS = 0;
  IF ( (NSLDPROD IS NOT NULL) AND (NSLDPROD != 0) ) THEN
  BEGIN
      NSLDTMP = NSLDPROD;
      FOR SELECT MP.DTMOVPROD, coalesce(MP.QTDMOVPROD,0),coalesce(MP.PRECOMOVPROD,0)
        FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD AND
           MP.CODEMPTM=TM.CODEMP AND MP.CODFILIALTM=TM.CODFILIAL AND MP.CODTIPOMOV=TM.CODTIPOMOV AND
           MP.TIPOMOVPROD IN ('E','I') AND TM.TIPOMOV IN ('IV','CP','PC') AND
           MP.DTMOVPROD<=:DTESTOQ  AND
           ( (:ICODALMOX IS NULL) OR
             (MP.CODEMPAX=:ICODEMPAX AND MP.CODFILIALAX=:SCODFILIALAX AND
             MP.CODALMOX=:ICODALMOX)
           )
         ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
         INTO :DTTEMP,:NQTD,:NCUSTO DO
      BEGIN
         NRESTO = NSLDTMP - NQTD;
         IF (NRESTO<=0) THEN
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * (NQTD+NRESTO) );
           BREAK;
         END
         ELSE
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * NQTD);
           NSLDTMP = NSLDTMP - NQTD;
         END
      END
      if( (:nsldprod is not null and :nsldprod>0) and (:ncustotot is not null and :ncustotot>0) ) then
          NCUSTOPEPS = NCUSTOTOT / NSLDPROD;
      else
          ncustopeps = 0;
  END
  suspend;
end
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
DECLARE VARIABLE PRECOBASE NUMERIC(15,5);
DECLARE VARIABLE CUSTOMPM NUMERIC(15,5);
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

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
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
      P.CODPROD=:ICODPROD
   INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;

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
      END
      -- Custo MPM
      ELSE IF (CTIPOCUSTO='M') THEN
      BEGIN
         CUSTOUNIT = CUSTOMPM;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      END
      -- Preço Base
      ELSE IF (CTIPOCUSTO='B') THEN
      BEGIN
         CUSTOUNIT = PRECOBASE;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      END
      -- Preço da Ultima Compra
      else if (CTIPOCUSTO='U') then
      begin
        select first 1 ic.custoitcompra from cpitcompra ic, cpcompra cp
            where cp.codemp=:icodemp and cp.codfilial=:scodfilial and cp.dtentcompra<=:dtestoq
            and ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra
            and ic.codemppd=:icodemp and ic.codfilialpd=:scodfilial and ic.codprod=:icodprod
            order by cp.dtentcompra desc
            into :CUSTOUNIT;

            if (CUSTOUNIT IS NULL) THEN
                CUSTOUNIT = :CUSTOMPM;
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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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
declare variable refprod char(13);
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
    from sgretinfousu(user)
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

/* Alter (EQITRECMERCSERIESP) */
ALTER PROCEDURE EQITRECMERCSERIESP(TRANSACAO CHAR(1),
CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODITRECMERC SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITRECMERC INTEGER)
 AS
declare variable seqitserie integer;
declare variable serieprod char(1);
begin
        
    -- Buscando informações do produto
    select pd.serieprod from eqproduto pd
    where pd.codemp = :codemppd and pd.codfilial = :codfilialpd and pd.codprod = :codprod
    into :serieprod;
    
    -- Se o produto usa série...
    if(serieprod = 'S') then
    begin
    
        -- Buscando ultimo item de série inserido...
        select coalesce(max(seqitserie),0) from eqitrecmercserie
        where codemp=:codemp and codfilial=:codfilial and ticket=:ticket and coditrecmerc=:coditrecmerc
        into :seqitserie;
    
        -- Se for uma transaçáo de Inserção...
        if( 'I' = :transacao) then
        begin
            -- Inserindo itens, enquanto a sequencia for menor ou igual à quantidade de itens
            while (seqitserie < qtditrecmerc) do
            begin
                seqitserie = seqitserie + 1;

                -- Se a quantidade não for unitária
                if(qtditrecmerc > 1) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie);
                end
                -- Se for apenas um item, deve inserir o numero se série digitado na tela...
                else if(qtditrecmerc = 1 and :numserietmp is not null) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie, codemppd, codfilialpd, codprod, numserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie, :codemppd, :codfilialpd, :codprod, :numserietmp );
                end
            end
        end
        -- Se for uma transação de Deleção, deve excluir todas os ítens gerados.
        else if('D' = :transacao) then
        begin
            delete from eqitrecmercserie itcs
            where
            itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc;
        end
        -- Se for uma transação de Update, deve excluir os excessos ou incluir os faltantes...
        else if('U' = :transacao) then
        begin

            if(seqitserie > qtditrecmerc) then
            begin

                delete from eqitrecmercserie itcs
                where
                itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc
                and itcs.seqitserie > :qtditrecmerc;
            
            end
            else
            begin
            
--                execute procedure cpitcompraseriesp( 'I', :codemp, :codfilial, :codcompra, :coditcompra, :codemppd, :codfilialpd, :codprod, :numserietmp, :qtditcompra);

            end

        end

    end


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

/* Alter (EQRELINVPRODSP) */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR(1),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD CHAR(13),
DESCPROD CHAR(50),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
DECLARE VARIABLE CMULTIALMOX CHAR(1);
DECLARE VARIABLE NCUSTOMPM NUMERIC(15,5);
begin
  /* Relatório de estoque */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMP) INTO :CMULTIALMOX;

  if (CCODGRUP IS NOT NULL) then
  begin
     CCODGRUP = rtrim(CCODGRUP);
     if (strlen(CCODGRUP)<14) then
        CCODGRUP = CCODGRUP || '%';
  end
  FOR SELECT P.CODPROD, P.REFPROD, P.DESCPROD, P.CODGRUP
    FROM EQPRODUTO P
    WHERE P.CODEMP=:ICODEMP AND P.CODFILIAL=:SCODFILIAL AND
          ( ( :CCODGRUP IS NULL ) OR
            (P.CODGRUP LIKE :CCODGRUP AND P.CODEMPGP=:ICODEMPGP AND
             P.CODFILIALGP=:SCODFILIALGP) )
    INTO :CODPROD, :REFPROD, :DESCPROD, :CODGRUP DO
  BEGIN
     SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODSLDSP(null, null, null, :ICODEMP,
        :SCODFILIAL, :CODPROD, :DDTESTOQ, 0, 0,
        :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
       INTO :SALDO, :NCUSTOMPM;
     if (CTIPOCUSTO='M') then
        CUSTO = NCUSTOMPM;
     else
        SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL, :CODPROD,
          :SALDO, :DDTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
        INTO :CUSTO;
     VLRESTOQ = CUSTO * SALDO;
     SUSPEND;
  END
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR(1))
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

  FOR SELECT P.CODPROD,P.REFPROD,P.DESCPROD, P.CODBARPROD, P.CODFABPROD, P.ATIVOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
   ( (:ICODEMPMC IS NULL) OR (P.CODEMPMC=:ICODEMPMC AND P.CODFILIALMC=:SCODFILIALMC AND
      P.CODMARCA=:CCODMARCA) ) AND
   ((:ICODEMPGP IS NULL) OR (P.CODEMPGP=:ICODEMPGP AND P.CODFILIALGP=:SCODFILIALGP AND
      P.CODGRUP LIKE :CCODGRUP) )
      ORDER BY P.CODPROD
   INTO :CODPROD, :REFPROD, :DESCPROD, :CODBARPROD, :CODFABPROD, :ATIVOPROD  DO
  BEGIN
     SELECT SLDPROD, CUSTOUNIT, CUSTOTOT FROM EQCUSTOPRODSP(:ICODEMP,
        :SCODFILIAL, :CODPROD, :DTESTOQ, :CTIPOCUSTO, :ICODEMPAX,
        :SCODFILIALAX, :ICODALMOX, 'S')
       INTO :SLDPROD, :CUSTOUNIT, :CUSTOTOT;
     SUSPEND;
  END
end
^

/* Alter (FNADICITRECEBERSP01) */
ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas areceber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
  END
 -- suspend;
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
VLRBASECOMIS NUMERIC(15,5))
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

ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas areceber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
  END
 -- suspend;
end
^

/* Alter (FNGERAITRECEBERSP01) */
ALTER PROCEDURE FNGERAITRECEBERSP01(CALTVLR CHAR(1),
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
ICODEMPPG INTEGER,
SCODFILIALPG SMALLINT,
ICODPLANOPAG INTEGER,
NVLRREC NUMERIC(15,5),
DDATAREC DATE,
CFLAG CHAR(1),
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(2),
NVLRCOMIREC NUMERIC(15,5),
OBSREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC INTEGER,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 RETURNS(I INTEGER)
 AS
declare variable nperc numeric(15,5);
declare variable npercpag numeric(15,5);
declare variable nresto numeric(15,5);
declare variable inumparc integer;
declare variable idiaspag integer;
declare variable nrestocomi numeric(15,5);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nvlritrec numeric(15,5);
declare variable regrvcto char(1);
declare variable rvsab char(1);
declare variable rvdom char(1);
declare variable rvfer char(1);
declare variable dtvencto date;
declare variable rvdia char(1);
declare variable dtbase date;
declare variable diavcto smallint;
declare variable casasdecfin smallint;
declare variable vlrbaseitcomis numeric(15,5);
declare variable restobasecomis numeric(15,5);
begin

    -- Inicializando variáveis

    i = 0;
    nperc = 100;
    nresto = nvlrrec;
    nrestocomi = nvlrcomirec;
    dtbase = ddatarec;
    vlrbaseitcomis = vlrbasecomis;
    restobasecomis = vlrbasecomis;

    -- Buscando preferencias

    select casasdecfin from sgprefere1 where codemp=:icodemp and codfilial=:scodfilial
    into casasdecfin;

    -- Buscanco informações do plano de pagamento
    select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag, pp.rvferplanopag, pp.rvdiaplanopag,
    pp.diavctoplanopag
    from fnplanopag pp
    where pp.codplanopag =:icodplanopag and pp.codemp=:icodemppg and pp.codfilial = :scodfilialpg
    into :inumparc, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

    -- Loop nas parcelas do plano de pagamento para geração dos itens do contas a receber

    for select percpag, diaspag from fnparcpag
    where codplanopag=:icodplanopag and codemp=:icodemppg and codfilial =:scodfilialpg
    order by diaspag
    into :npercpag, :idiaspag
    do begin
        i = i+1;

        -- Calculando valor da parcela
        select v.deretorno from arreddouble(cast(:nvlrrec * :npercpag as numeric(15, 5))/:nperc, :casasdecfin ) v
        into :nvlritrec;

        nresto = nresto - nvlritrec;

        if (i = inumparc) then
        begin
            nvlritrec = nvlritrec + nresto;
        end

        -- Calculando o valor da comissão a pagar na parcela.
        select v.deretorno from arreddouble(cast(:nvlrcomirec * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :nvlrcomiitrec;

        nrestocomi = nrestocomi - nvlrcomiitrec;

        if (i = inumparc) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nrestocomi;
        end

         -- Calculando o valor da base da comissão especial a pagar na parcela.
        select v.deretorno from arreddouble(cast(:vlrbasecomis * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :vlrbaseitcomis;

        restobasecomis = restobasecomis - vlrbaseitcomis;

        if (i = inumparc) then
        begin
            vlrbaseitcomis = vlrbaseitcomis + restobasecomis;
        end

        -- Definindo o vencimento da parcela
        select c.dtvencret from sgcalcvencsp(:icodemp, :dtbase, :ddatarec + :idiaspag, :regrvcto,
        :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :idiaspag ) c
        into :dtvencto;

        dtbase = dtvencto;

        -- Execuntado procedimento que insere registros na tabela fnitreceber

        execute procedure fnadicitrecebersp01 (
        :caltvlr, :icodemp,:scodfilial,:icodrec,:i,0,0,0,:nvlritrec,0, :ddatarec,:dtvencto,'R1',:cflag,
        :icodempbo,:scodfilialbo, :ccodbanco, :icodemptc, :scodfilialtc, :icodtipocob,
        :icodempcb, :scodfilialcb, :codcartcob, :nvlrcomiitrec, :obsrec, :codempca,
        :codfilialca, :numconta, :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc, :vlrbaseitcomis);

    end
    suspend;
end
^

/* Alter (FNITRECEBERSP01) */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR(1))
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin
        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
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
DECLARE VARIABLE QTDITEST NUMERIC(15,5);
DECLARE VARIABLE SEQEST SMALLINT;
DECLARE VARIABLE TIPOPROD CHAR(1);
DECLARE VARIABLE CUSTOTOT NUMERIC(15,5);
DECLARE VARIABLE CODPRODPD INTEGER;
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
declare variable refprod char(13);
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
declare variable crefprod char(13);
declare variable iseqitest smallint;
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

    if(:estdinamica='N') then
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitest, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqitest, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote
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
                codempfs, codfilialfs, codfase, qtditop, gerarma
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma
            );

        end

    end

    suspend;

end
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
REFPROD CHAR(13),
DESCPROD CHAR(50),
TIPOPROD CHAR(1),
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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.6 (05/10/2010)';
    suspend;
end
^

/* Alter (VDADICCOMISSAOSP) */
ALTER PROCEDURE VDADICCOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRVENDACOMI NUMERIC(15,5),
NVLRCOMI NUMERIC(15,5),
DDATACOMI DATE,
DDTVENCCOMI DATE,
CTIPOCOMI CHAR(1),
CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVEND INTEGER)
 AS
declare variable scodfilialcs smallint;
declare variable icodcomi integer;
declare variable cstatuscomi char(2);
begin

    -- Se o valor for nulo ou 0 deve deletar a comissão já gerada
    if ( (nvlrcomi is null) or  (nvlrcomi=0) ) then
    begin

        delete from vdcomissao co
        where co.codemprc=:icodemp and co.codfilialrc=:scodfilial and co.codrec=:icodrec and co.nparcitrec=:inparcitrec and
        co.tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend;

    end
    -- Caso seja um estorno de comissão
    else if (nvlrcomi<0) then
    begin

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial(:icodemp,'VDCOMISSAO') into :scodfilialcs;

        -- Buscando novo numero para
        select max(codcomi) from vdcomissao where codemp=:icodemp and codfilialvd = :scodfilialcs into icodcomi;

        if (:icodcomi is null) then
            icodcomi = 1;
        else
            icodcomi = icodcomi + 1;

        -- Inserindo na tabela de comissões
        insert into vdcomissao (
            codemp, codfilial, codcomi, codempRc, codfilialrc, codrec, nparcitrec, vlrvendacomi, vlrcomi, datacomi,
            dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrvendacomi, :nvlrcomi, :ddatacomi,
            :ddtvenccomi, 'CE', :ctipocomi, :codempvd, :codfilialvd,:codvend
            );

        -- Transforma o valor da comissão em positivo e programa para o proximo pagto.
        nvlrcomi = nvlrcomi * -1;

        insert into vdcomissao (
            codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
            :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvenccomi, 'C1', :ctipocomi, :codempvd,:codfilialvd,:codvend
            );

    end
    else
    begin

        if (ctipocomi='F') then
            cstatuscomi = 'C2';
        else
            cstatuscomi = 'C1';

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial( :icodemp, 'VDCOMISSAO') into :scodfilialcs;

        -- Buscando o código da comissão já existente
        select codcomi from vdcomissao
        where codemp=:icodemp and codfilialrc=:scodfilial and codrec=:icodrec and nparcitrec=:inparcitrec and
        tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend
        into :icodcomi;

        -- Caso já não exista a comissão deve inserir
        if (icodcomi is null) then
        begin
            --Buscando um novo código
            select max(codcomi) from vdcomissao where codemp=:icodemp and codfilial = :scodfilialcs into icodcomi;

            if (:icodcomi is null) then
                icodcomi = 1;
            else
                icodcomi = icodcomi + 1;

            -- Inserindo na tabela de comissões
            insert into vdcomissao( codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend)
            values (
                :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvenccomi, :cstatuscomi, :ctipocomi, :codempvd, :codfilialvd, :codvend
            );

        end
        -- Se encontrou a comissão atualiza
        else
        begin

            update vdcomissao set vlrvendacomi=:nvlrvendacomi, vlrcomi=:nvlrcomi, datacomi=:ddatacomi,
            dtvenccomi=:ddtvenccomi, statuscomi=:cstatuscomi
            where codemp=:icodemp and codfilial=:scodfilialcs and codcomi=:icodcomi and codempvd=:codempvd and
            codfilialvd=:codfilialvd and codvend=:codvend and statuscomi!='CP' ;

        end

    end
    suspend;
end
^

/* Alter (VDADICITORCRECMERCSP) */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod char(13);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitorc numeric(15,5);
declare variable qtditorc numeric(15,5);
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemppg integer;
declare variable codfilialpg smallint;
declare variable codplanopag integer;
declare variable gerachamado char(1);
declare variable obsitorc varchar(10000);
declare variable descprod char(100);
declare variable vlrliqitorc numeric(15,5);
declare variable vlrproditorc numeric(15,5);
declare variable usaprecopecaserv char(1);
declare variable codprodpeca integer;
declare variable garantia char(1);
declare variable codprodir integer;
declare variable refprodir char(13);
begin
    
    -- Buscando preferencias do GMS
    select coalesce(p8.usaprecopecaserv,'N') from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :usaprecopecaserv;

    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos,
        ir.gerachamado, pd.descprod, irm.codprod, irm.garantia, irm.codprod codprodir, irm.refprod refprodir
        from eqitrecmercitos ir, eqitrecmerc irm, eqproduto pd
        where
        irm.codemp=ir.codemp and irm.codfilial=ir.codfilial and irm.ticket=ir.ticket and irm.coditrecmerc=ir.coditrecmerc
        and pd.codemp=irm.codemppd and pd.codfilial=irm.codfilialpd and irm.codprod=pd.codprod
        and ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc,
             :gerachamado, :descprod, :codprodpeca, :garantia, :codprodir, :refprodir
        do
        begin

--            if(:codprod <> :codprodant or :codprodant is null) then
--            begin

                -- Verifica se é serviço, sendo serviço insere a descriçao do produto
                -- consertado na descrição auxiliar do item de orçamento
                if(:gerachamado=:servicos and :gerachamado='S') then
                begin
                    if( 'N' = :garantia ) then
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod;
                    end
                    else
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod || '[G]';
                    end

                end

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda
                -- Se não está em garantia...

                if('N' = :garantia) then
                begin
                    -- Se o preço é basedo na peca, deve buscar o preço da peça
                    if(usaprecopecaserv='S') then
                    begin
                        select preco from vdbuscaprecosp(:codprodpeca,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                    else
                    begin
                        select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                end
                else
                begin

                    precoitorc = 0.00;

                end

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                vlrproditorc = :qtditorc * :precoitorc;
                vlrliqitorc = vlrproditorc;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox, obsitorc, vlrproditorc, vlrliqitorc, sitproditorc)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox, :obsitorc, :vlrproditorc, :vlrliqitorc,
                'PE') ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

--            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Alter (VDBUSCAPRECOSP) */
ALTER PROCEDURE VDBUSCAPRECOSP(ICODPROD INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPTM INTEGER,
ICODFILIALTM SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(14,5))
 AS
declare variable icodtab integer;
declare variable icodemptab integer;
declare variable icodfilialtab smallint;
declare variable icodclascli integer;
declare variable icodempclascli integer;
declare variable icodfilialclascli smallint;
declare variable percdesccli numeric(3,2);
declare variable desccli char(1);
declare variable arredpreco smallint;
declare variable codfilialpf integer;
declare variable centavos decimal(2,2);
begin
    -- Buscando código da filial de preferencias
    select icodfilial from sgretfilial(:icodemp,'SGFILIAL') into :codfilialpf;

    -- Buscando preferencias de arredondamento;
    select coalesce(arredpreco, 0)
    from sgprefere1 p1
    where p1.codemp=:icodemp and p1.codfilial=:codfilialpf
    into :arredpreco;

    -- Buscando tabela de preços do tipo de movimento;
    select codtab, codemptb, codfilialtb
    from eqtipomov
    where codtipomov=:icodtipomov and codemp=:icodemptm and codfilial=:icodfilialtm
    into :icodtab, :icodemptab, :icodfilialtab;

    -- Buscando informações do cliente;
    select codclascli, codempcc, codfilialcc, coalesce(percdesccli,0) percdesccli
    from vdcliente
    where codcli=:icodcli and codemp=:icodempcl and codfilial=:icodfilialcl
    into :icodclascli, :icodempclascli, icodfilialclascli, :percdesccli;

    -- Buscando preço da tabela de preços utilizando todos os filtros
    select precoprod from vdprecoprod pp
    where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg and pp.codfilialpg=:icodfilialpg
    and pp.codtab=:icodtab and pp.codemptb=:icodemptab and pp.codfilialtb=:icodfilialtab
    and pp.codclascli=:icodclascli and pp.codempcc=:icodempclascli and pp.codfilialcc=:icodfilialclascli
    and pp.codemp=:icodemp and pp.codfilial=:icodfilial
    into :preco;

    --Se não encontrou um preço de tabela usando todos os filtros, deve retirar o filtro de classificação do cliente
    if ((preco is null) or (preco = 0)) then
    begin
        select max(pp.precoprod) from vdprecoprod pp
        where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg
        and pp.codfilialpg=:icodfilialpg and pp.codtab=:icodtab and pp.codemptb=:icodemptab
        and pp.codfilialtb=:icodfilialtab and pp.codclascli is null
        and pp.codemp=:icodemp and pp.codfilial=:icodfilial
        into :preco;
    end

    --Se ainda não conseguiu pagar o preco, deve utilizar o preço base do produto aplicando o desconto especial do cliente se houver
    if ((preco is null) or (preco = 0)) then
    begin

        select coalesce(pd.precobaseprod,0), coalesce(pd.desccli,'N') from eqproduto pd
        where pd.codprod=:icodprod and pd.codemp=:icodemp and pd.codfilial=:icodfilial
        into :preco, :desccli;

        -- Verifica se o cliente possui desconto especial e o produto permite este desconto...
        if( percdesccli >0 and 'S' = :desccli ) then
        begin

            preco = :preco - (:preco * (:percdesccli / 100)) ;

        end

    end

    if( :arredpreco > 0 ) then
    begin

        -- capturando valor dos centavos
        centavos = ( cast(:preco as decimal(15,2)) - truncate(preco) ) * 10;

        -- se o valor em centavos é maior ou igual ao parametro de arredondamento (arredondar para cima)
        if(:centavos >= :arredpreco) then
        begin
            preco = truncate(preco) + 1;
        end
        else
        begin
            preco = truncate(preco);
        end

    end

    suspend;

end
^

/* Alter (VDESTORNACOMISSAOSP) */
ALTER PROCEDURE VDESTORNACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
ICODVEND INTEGER,
DINI DATE,
DFIM DATE,
CORDEM CHAR(1))
 AS
declare variable icodcomi integer;
declare variable icodemprc integer;
declare variable scodfilialrc smallint;
declare variable icodrec integer;
declare variable inparcitrec integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable nvlrcomi numeric(15,5);
declare variable ddatacomi date;
declare variable ddtvenccomi date;
declare variable ctipocomi char(1);
declare variable cstatuscomi char(2);
declare variable datual date;
declare variable cstatusitrec char(2);
declare variable ddtvencitrec date;
begin
  /* Procedure Text */
  DATUAL = CAST( 'now' AS DATE);
  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTVENCCOMI,
      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
    WHERE C.CODEMP=:ICODEMP AND C.CODFILIAL=:SCODFILIAL AND
       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
       R.CODREC=C.CODREC AND R.CODEMPVD=:ICODEMPVD AND
       R.CODFILIALVD=:SCODFILIALVD AND R.CODVEND=:ICODVEND AND
       ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       ( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
       C.STATUSCOMI IN ('C2','CP') AND
       IR.STATUSITREC NOT IN ('RP') AND
       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
    INTO :ICODCOMI, :ICODEMPRC, :SCODFILIALRC, :ICODREC, :INPARCITREC, :NVLRVENDACOMI,
      :NVLRCOMI, :DDATACOMI, :DDTVENCCOMI, :CTIPOCOMI, :CSTATUSCOMI, :CSTATUSITREC,
      :DDTVENCITREC
  DO
  BEGIN
     IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
     BEGIN
        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
          WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND
            CODCOMI=:ICODCOMI;
     END
     ELSE IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
     BEGIN
        NVLRCOMI = NVLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
        EXECUTE PROCEDURE vdadiccomissaosp(:ICODEMPRC,:SCODFILIALRC,:ICODREC,
          :INPARCITREC, :NVLRVENDACOMI, :NVLRCOMI, :DDATACOMI , :DDTVENCITREC,
          :CTIPOCOMI, :icodempvd, :scodfilialvd, : icodvend );
     END
  END
  suspend;
end
^

/* Alter (VDGERACOMISSAOSP) */
ALTER PROCEDURE VDGERACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRCOMIITREC NUMERIC(15,5),
DDTVENCITREC DATE)
 AS
declare variable icodempva integer;
declare variable scodfilialva smallint;
declare variable ctipovenda char(3);
declare variable icodvenda integer;
declare variable icodempcm integer;
declare variable scodfilialcm smallint;
declare variable icodclcomis integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable ddatacomi date;
declare variable npercfatclcomis numeric(9,2);
declare variable npercpgtoclcomis numeric(9,2);
declare variable ctipocomi char(1);
declare variable nvlrcomi numeric(15,5);
declare variable i integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable cmulticomis char(1);
declare variable icodempvd integer; /* Código da empresa do comissionado principal */
declare variable scodfilialvd smallint; /* Código da filial do comissionado principal */
declare variable icodvend integer; /* Código do comissionado principal */
declare variable nperccomisvendadic numeric(15,2); /* Percentual de comissão para cada comissionado adicional */
declare variable nvlrcomiadic numeric(15,5); /* Valor total da comissão para os comissionados adicionais. */
declare variable icodempvdadic integer; /* Código da empresa do comissionado adicional */
declare variable scodfilialvdadic smallint; /* Código da filial do comissionado adicional */
declare variable icodvendadic integer; /* Código do comissionado adicional */
declare variable nvlrcomiparc numeric(15,5); /* Valor da comissão por vendedor parcial. */
begin
    /* Gera as comissões a pagar na tabela VDCOMISSAO */


    nvlrcomiadic = 0;

    select r.codempva, r.codfilialva, r.tipovenda, r.codvenda,
        v.codempcm, v.codfilialcm, v.codclcomis, ( v.vlrprodvenda - v.vlrdescvenda ),
        v.dtemitvenda, cm.percfatclcomis, cm.percpgtoclcomis,
        v.codemptm, v.codfilialtm, v.codtipomov,
        v.codempvd, v.codfilialvd, v.codvend
        from fnreceber r, vdvenda v, vdclcomis cm
        where r.codemp=:icodemp and r.codfilial=:scodfilial and r.codrec=:icodrec
            and v.codemp=r.codempva and v.codfilial=r.codfilialva and v.tipovenda=r.tipovenda
            and v.codvenda=r.codvenda and cm.codemp=v.codempcm and cm.codfilial=v.codfilialcm
            and cm.codclcomis=v.codclcomis
    into :icodempva, :scodfilialva, :ctipovenda, :icodvenda,
         :icodempcm, :scodfilialcm, :icodclcomis, :nvlrvendacomi,
         :ddatacomi, :npercfatclcomis, :npercpgtoclcomis,
         :icodemptm, :scodfilialtm, :icodtipomov,
         :icodempvd, :scodfilialvd, :icodvend ;

    /*Verifica se deve utilizar mecanismo de multiplos comissionados*/

    select cmulticomis from sgretmulticomissp(:icodemp, :icodemptm, :scodfilialtm, :icodtipomov)
        into cmulticomis;

    if(cmulticomis = 'S') then
    begin

        /*Implementação do mecanismo de multiplos comissionados*/
        
        for select vc.codempvd, vc.codfilialvd, vc.codvend, vc.percvc
            from vdvendacomis vc
            where vc.codemp=:icodempva and vc.codfilial=:scodfilialva and vc.codvenda=:icodvenda
                and vc.tipovenda=:ctipovenda and vc.codvend is not null
        into :icodempvdadic, :scodfilialvdadic, :icodvendadic, :nperccomisvendadic do
        begin

            /* Calcula o valor da comissão proporcional para cada comissionado adicional*/

            nvlrcomi = cast( ( nvlrcomiitrec * nperccomisvendadic / 100 ) as numeric(15,5));
            I = 1;
            while (:I<=2) do
            begin
                if (I=1) then
                    begin
                        ctipocomi='F';
                        nvlrcomiparc = cast( ( nvlrcomi * npercfatclcomis / 100 ) as NUMERIC(15, 5));
                    end
                else
                    begin
                        ctipocomi='R';
                        nvlrcomiparc = cast( (nvlrcomi * npercpgtoclcomis / 100 ) as NUMERIC(15, 5));
                    end
                execute procedure vdadiccomissaosp(:iCodEmp, :sCodFilial, :iCodRec, :iNParcItRec,
                    :nVlrVendaComi, :nvlrcomiparc, :dDataComi, :dDtVencItRec, cTipoComi,:icodempvdadic, :scodfilialvdadic, :icodvendadic );
                I=I+1;
                /*Acumula as comissões adicionais para posteriormente descontar do valor principal*/
                nvlrcomiadic = nvlrcomiadic + nvlrcomiparc;

           /*     exception vdcomissaoex02 'rodou procedure para o vendedor:' || cast(:icodempvdadic as char(2)) || '-' || cast(:scodfilialvdadic as char(2)) || '-' || cast(:icodvendadic as char(2)) || '-' || ' - nvlrcomiadic:' || cast(nvlrcomiadic as char(20));*/

            end

        end

    end

    /*Comissionamento do vendedor principal*/

    I = 1;
    while (:I<=2) do
        begin
            if (I=1) then
                begin
                    ctipocomi='F';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercFatClComis / 100 ) as numeric(15, 5) );
                end
            else
                begin
                    ctipocomi='R';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercPgtoClComis / 100 ) as numeric(15, 5));
                end
/*
                exception vdcomissaoex01 ''
                || cast(:icodempvd as char(2)) || '-' || cast(:scodfilialvd as char(2)) || '-' || cast(:icodvend as char(2))
                || 'nvlrcomi:' || cast(:nvlrcomi as char(20))
                || 'nvlrvendacomi:' || cast(:nvlrvendacomi as char(20));
  */
            execute procedure vdadiccomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtvencitrec, ctipocomi, :icodempvd, :scodfilialvd, :icodvend );


            I=I+1;
        end

        suspend;
end
^

SET TERM ; ^

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

ALTER TABLE EQITRMA ALTER COLUMN DTINS POSITION 32;

ALTER TABLE EQITRMA ALTER COLUMN HINS POSITION 33;

ALTER TABLE EQITRMA ALTER COLUMN IDUSUINS POSITION 34;

ALTER TABLE EQITRMA ALTER COLUMN DTALT POSITION 35;

ALTER TABLE EQITRMA ALTER COLUMN HALT POSITION 36;

ALTER TABLE EQITRMA ALTER COLUMN IDUSUALT POSITION 37;

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

ALTER TABLE EQPRODUTO ALTER COLUMN VERIFPROD POSITION 50;

ALTER TABLE EQPRODUTO ALTER COLUMN LOCALPROD POSITION 51;

ALTER TABLE EQPRODUTO ALTER COLUMN RMAPROD POSITION 52;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPPE POSITION 53;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALPE POSITION 54;

ALTER TABLE EQPRODUTO ALTER COLUMN CODPE POSITION 55;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPCC POSITION 56;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALCC POSITION 57;

ALTER TABLE EQPRODUTO ALTER COLUMN ANOCC POSITION 58;

ALTER TABLE EQPRODUTO ALTER COLUMN CODCC POSITION 59;

ALTER TABLE EQPRODUTO ALTER COLUMN USARECEITAPROD POSITION 60;

ALTER TABLE EQPRODUTO ALTER COLUMN USATELAADICPDV POSITION 61;

ALTER TABLE EQPRODUTO ALTER COLUMN VLRDENSIDADE POSITION 62;

ALTER TABLE EQPRODUTO ALTER COLUMN VLRCONCENT POSITION 63;

ALTER TABLE EQPRODUTO ALTER COLUMN COMPRIMENTO POSITION 64;

ALTER TABLE EQPRODUTO ALTER COLUMN LARGURA POSITION 65;

ALTER TABLE EQPRODUTO ALTER COLUMN ESPESSURA POSITION 66;

ALTER TABLE EQPRODUTO ALTER COLUMN GUIATRAFPROD POSITION 67;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDEMBALAGEM POSITION 68;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEANPROD POSITION 69;

ALTER TABLE EQPRODUTO ALTER COLUMN CUBAGEM POSITION 70;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPSC POSITION 71;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALSC POSITION 72;

ALTER TABLE EQPRODUTO ALTER COLUMN CODSECAO POSITION 73;

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPTC POSITION 74;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALTC POSITION 75;

ALTER TABLE EQPRODUTO ALTER COLUMN CODTPCHAMADO POSITION 76;

ALTER TABLE EQPRODUTO ALTER COLUMN QTDHORASSERV POSITION 77;

ALTER TABLE EQPRODUTO ALTER COLUMN NRODIASVALID POSITION 78;

ALTER TABLE EQPRODUTO ALTER COLUMN DESCCLI POSITION 79;

ALTER TABLE EQPRODUTO ALTER COLUMN DTINS POSITION 80;

ALTER TABLE EQPRODUTO ALTER COLUMN HINS POSITION 81;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUINS POSITION 82;

ALTER TABLE EQPRODUTO ALTER COLUMN DTALT POSITION 83;

ALTER TABLE EQPRODUTO ALTER COLUMN HALT POSITION 84;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUALT POSITION 85;

ALTER TABLE EQRMA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQRMA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQRMA ALTER COLUMN CODRMA POSITION 3;

ALTER TABLE EQRMA ALTER COLUMN IDUSU POSITION 4;

ALTER TABLE EQRMA ALTER COLUMN CODEMPTM POSITION 5;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALTM POSITION 6;

ALTER TABLE EQRMA ALTER COLUMN CODTIPOMOV POSITION 7;

ALTER TABLE EQRMA ALTER COLUMN MOTIVORMA POSITION 8;

ALTER TABLE EQRMA ALTER COLUMN MOTIVOCANCRMA POSITION 9;

ALTER TABLE EQRMA ALTER COLUMN SITRMA POSITION 10;

ALTER TABLE EQRMA ALTER COLUMN CODEMPCC POSITION 11;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALCC POSITION 12;

ALTER TABLE EQRMA ALTER COLUMN ANOCC POSITION 13;

ALTER TABLE EQRMA ALTER COLUMN CODCC POSITION 14;

ALTER TABLE EQRMA ALTER COLUMN CODEMPUU POSITION 15;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALUU POSITION 16;

ALTER TABLE EQRMA ALTER COLUMN DTAREQRMA POSITION 17;

ALTER TABLE EQRMA ALTER COLUMN DTAEXPRMA POSITION 18;

ALTER TABLE EQRMA ALTER COLUMN SITAPROVRMA POSITION 19;

ALTER TABLE EQRMA ALTER COLUMN SITEXPRMA POSITION 20;

ALTER TABLE EQRMA ALTER COLUMN IDUSUAPROV POSITION 21;

ALTER TABLE EQRMA ALTER COLUMN CODEMPUA POSITION 22;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALUA POSITION 23;

ALTER TABLE EQRMA ALTER COLUMN IDUSUEXP POSITION 24;

ALTER TABLE EQRMA ALTER COLUMN CODEMPUE POSITION 25;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALUE POSITION 26;

ALTER TABLE EQRMA ALTER COLUMN DTAAPROVRMA POSITION 27;

ALTER TABLE EQRMA ALTER COLUMN CODEMPOF POSITION 28;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALOF POSITION 29;

ALTER TABLE EQRMA ALTER COLUMN CODOP POSITION 30;

ALTER TABLE EQRMA ALTER COLUMN SEQOP POSITION 31;

ALTER TABLE EQRMA ALTER COLUMN SEQOF POSITION 32;

ALTER TABLE EQRMA ALTER COLUMN CODEMPCT POSITION 33;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALCT POSITION 34;

ALTER TABLE EQRMA ALTER COLUMN CODCONTR POSITION 35;

ALTER TABLE EQRMA ALTER COLUMN CODITCONTR POSITION 36;

ALTER TABLE EQRMA ALTER COLUMN CODEMPOS POSITION 37;

ALTER TABLE EQRMA ALTER COLUMN CODFILIALOS POSITION 38;

ALTER TABLE EQRMA ALTER COLUMN TICKET POSITION 39;

ALTER TABLE EQRMA ALTER COLUMN CODITRECMERC POSITION 40;

ALTER TABLE EQRMA ALTER COLUMN DTINS POSITION 41;

ALTER TABLE EQRMA ALTER COLUMN HINS POSITION 42;

ALTER TABLE EQRMA ALTER COLUMN IDUSUINS POSITION 43;

ALTER TABLE EQRMA ALTER COLUMN DTALT POSITION 44;

ALTER TABLE EQRMA ALTER COLUMN HALT POSITION 45;

ALTER TABLE EQRMA ALTER COLUMN IDUSUALT POSITION 46;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNITRECEBER ALTER COLUMN CODREC POSITION 3;

ALTER TABLE FNITRECEBER ALTER COLUMN NPARCITREC POSITION 4;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRITREC POSITION 5;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRDESCITREC POSITION 6;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRMULTAITREC POSITION 7;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRJUROSITREC POSITION 8;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRDEVITREC POSITION 9;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRPARCITREC POSITION 10;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRPAGOITREC POSITION 11;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRAPAGITREC POSITION 12;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRCOMIITREC POSITION 13;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRCANCITREC POSITION 14;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRBASECOMIS POSITION 15;

ALTER TABLE FNITRECEBER ALTER COLUMN DESCPONT POSITION 16;

ALTER TABLE FNITRECEBER ALTER COLUMN DTITREC POSITION 17;

ALTER TABLE FNITRECEBER ALTER COLUMN DTVENCITREC POSITION 18;

ALTER TABLE FNITRECEBER ALTER COLUMN DTPREVITREC POSITION 19;

ALTER TABLE FNITRECEBER ALTER COLUMN DTPAGOITREC POSITION 20;

ALTER TABLE FNITRECEBER ALTER COLUMN STATUSITREC POSITION 21;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPPN POSITION 22;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALPN POSITION 23;

ALTER TABLE FNITRECEBER ALTER COLUMN CODPLAN POSITION 24;

ALTER TABLE FNITRECEBER ALTER COLUMN OBSITREC POSITION 25;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCA POSITION 26;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCA POSITION 27;

ALTER TABLE FNITRECEBER ALTER COLUMN NUMCONTA POSITION 28;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPBO POSITION 29;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALBO POSITION 30;

ALTER TABLE FNITRECEBER ALTER COLUMN CODBANCO POSITION 31;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPTC POSITION 32;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALTC POSITION 33;

ALTER TABLE FNITRECEBER ALTER COLUMN CODTIPOCOB POSITION 34;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCB POSITION 35;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCB POSITION 36;

ALTER TABLE FNITRECEBER ALTER COLUMN CODCARTCOB POSITION 37;

ALTER TABLE FNITRECEBER ALTER COLUMN DOCLANCAITREC POSITION 38;

ALTER TABLE FNITRECEBER ALTER COLUMN FLAG POSITION 39;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCC POSITION 40;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCC POSITION 41;

ALTER TABLE FNITRECEBER ALTER COLUMN ANOCC POSITION 42;

ALTER TABLE FNITRECEBER ALTER COLUMN CODCC POSITION 43;

ALTER TABLE FNITRECEBER ALTER COLUMN IMPRECIBOITREC POSITION 44;

ALTER TABLE FNITRECEBER ALTER COLUMN RECIBOITREC POSITION 45;

ALTER TABLE FNITRECEBER ALTER COLUMN ALTUSUITREC POSITION 46;

ALTER TABLE FNITRECEBER ALTER COLUMN PDVITREC POSITION 47;

ALTER TABLE FNITRECEBER ALTER COLUMN RECEMCOB POSITION 48;

ALTER TABLE FNITRECEBER ALTER COLUMN DTINIEMCOB POSITION 49;

ALTER TABLE FNITRECEBER ALTER COLUMN DTFIMEMCOB POSITION 50;

ALTER TABLE FNITRECEBER ALTER COLUMN DTINS POSITION 51;

ALTER TABLE FNITRECEBER ALTER COLUMN HINS POSITION 52;

ALTER TABLE FNITRECEBER ALTER COLUMN IDUSUINS POSITION 53;

ALTER TABLE FNITRECEBER ALTER COLUMN DTALT POSITION 54;

ALTER TABLE FNITRECEBER ALTER COLUMN HALT POSITION 55;

ALTER TABLE FNITRECEBER ALTER COLUMN IDUSUALT POSITION 56;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNRECEBER ALTER COLUMN CODREC POSITION 3;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPBO POSITION 4;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALBO POSITION 5;

ALTER TABLE FNRECEBER ALTER COLUMN CODBANCO POSITION 6;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPPG POSITION 7;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALPG POSITION 8;

ALTER TABLE FNRECEBER ALTER COLUMN CODPLANOPAG POSITION 9;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCL POSITION 10;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCL POSITION 11;

ALTER TABLE FNRECEBER ALTER COLUMN CODCLI POSITION 12;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPVD POSITION 13;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALVD POSITION 14;

ALTER TABLE FNRECEBER ALTER COLUMN CODVEND POSITION 15;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPVA POSITION 16;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALVA POSITION 17;

ALTER TABLE FNRECEBER ALTER COLUMN TIPOVENDA POSITION 18;

ALTER TABLE FNRECEBER ALTER COLUMN CODVENDA POSITION 19;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPTC POSITION 20;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALTC POSITION 21;

ALTER TABLE FNRECEBER ALTER COLUMN CODTIPOCOB POSITION 22;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCB POSITION 23;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCB POSITION 24;

ALTER TABLE FNRECEBER ALTER COLUMN CODCARTCOB POSITION 25;

ALTER TABLE FNRECEBER ALTER COLUMN VLRREC POSITION 26;

ALTER TABLE FNRECEBER ALTER COLUMN VLRDESCREC POSITION 27;

ALTER TABLE FNRECEBER ALTER COLUMN VLRMULTAREC POSITION 28;

ALTER TABLE FNRECEBER ALTER COLUMN VLRJUROSREC POSITION 29;

ALTER TABLE FNRECEBER ALTER COLUMN VLRDEVREC POSITION 30;

ALTER TABLE FNRECEBER ALTER COLUMN VLRPARCREC POSITION 31;

ALTER TABLE FNRECEBER ALTER COLUMN VLRPAGOREC POSITION 32;

ALTER TABLE FNRECEBER ALTER COLUMN VLRAPAGREC POSITION 33;

ALTER TABLE FNRECEBER ALTER COLUMN VLRBASECOMIS POSITION 34;

ALTER TABLE FNRECEBER ALTER COLUMN DATAREC POSITION 35;

ALTER TABLE FNRECEBER ALTER COLUMN STATUSREC POSITION 36;

ALTER TABLE FNRECEBER ALTER COLUMN VLRCOMIREC POSITION 37;

ALTER TABLE FNRECEBER ALTER COLUMN DTQUITREC POSITION 38;

ALTER TABLE FNRECEBER ALTER COLUMN DOCREC POSITION 39;

ALTER TABLE FNRECEBER ALTER COLUMN NROPARCREC POSITION 40;

ALTER TABLE FNRECEBER ALTER COLUMN OBSREC POSITION 41;

ALTER TABLE FNRECEBER ALTER COLUMN FLAG POSITION 42;

ALTER TABLE FNRECEBER ALTER COLUMN ALTUSUREC POSITION 43;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCA POSITION 44;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCA POSITION 45;

ALTER TABLE FNRECEBER ALTER COLUMN NUMCONTA POSITION 46;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPPN POSITION 47;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALPN POSITION 48;

ALTER TABLE FNRECEBER ALTER COLUMN CODPLAN POSITION 49;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCC POSITION 50;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCC POSITION 51;

ALTER TABLE FNRECEBER ALTER COLUMN ANOCC POSITION 52;

ALTER TABLE FNRECEBER ALTER COLUMN CODCC POSITION 53;

ALTER TABLE FNRECEBER ALTER COLUMN DTINS POSITION 54;

ALTER TABLE FNRECEBER ALTER COLUMN HINS POSITION 55;

ALTER TABLE FNRECEBER ALTER COLUMN IDUSUINS POSITION 56;

ALTER TABLE FNRECEBER ALTER COLUMN DTALT POSITION 57;

ALTER TABLE FNRECEBER ALTER COLUMN HALT POSITION 58;

ALTER TABLE FNRECEBER ALTER COLUMN IDUSUALT POSITION 59;

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

ALTER TABLE LFITCLFISCAL ALTER COLUMN DTINS POSITION 66;

ALTER TABLE LFITCLFISCAL ALTER COLUMN HINS POSITION 67;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IDUSUINS POSITION 68;

ALTER TABLE LFITCLFISCAL ALTER COLUMN DTALT POSITION 69;

ALTER TABLE LFITCLFISCAL ALTER COLUMN HALT POSITION 70;

ALTER TABLE LFITCLFISCAL ALTER COLUMN IDUSUALT POSITION 71;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODFISCCLI POSITION 3;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN DESCFISCCLI POSITION 4;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCCOFINSTF POSITION 5;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCCSOCIALTF POSITION 6;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCICMSTF POSITION 7;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCIPITF POSITION 8;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCIRTF POSITION 9;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCISSTF POSITION 10;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CALCPISTF POSITION 11;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPCOFINSTF POSITION 12;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPCSOCIALTF POSITION 13;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPICMSTF POSITION 14;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPISSTF POSITION 15;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPIPITF POSITION 16;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPIRTF POSITION 17;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IMPPISTF POSITION 18;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODEMPOC POSITION 19;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODFILIALOC POSITION 20;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODTIPOMOVOC POSITION 21;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN DTINS POSITION 22;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN HINS POSITION 23;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IDUSUINS POSITION 24;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN DTALT POSITION 25;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN HALT POSITION 26;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IDUSUALT POSITION 27;

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

ALTER TABLE PPESTRUTURA ALTER COLUMN DTINS POSITION 17;

ALTER TABLE PPESTRUTURA ALTER COLUMN HINS POSITION 18;

ALTER TABLE PPESTRUTURA ALTER COLUMN IDUSUINS POSITION 19;

ALTER TABLE PPESTRUTURA ALTER COLUMN DTALT POSITION 20;

ALTER TABLE PPESTRUTURA ALTER COLUMN HALT POSITION 21;

ALTER TABLE PPESTRUTURA ALTER COLUMN IDUSUALT POSITION 22;

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

ALTER TABLE PPOP ALTER COLUMN DTINS POSITION 49;

ALTER TABLE PPOP ALTER COLUMN HINS POSITION 50;

ALTER TABLE PPOP ALTER COLUMN IDUSUINS POSITION 51;

ALTER TABLE PPOP ALTER COLUMN DTALT POSITION 52;

ALTER TABLE PPOP ALTER COLUMN HALT POSITION 53;

ALTER TABLE PPOP ALTER COLUMN IDUSUALT POSITION 54;

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

ALTER TABLE SGPREFERE1 ALTER COLUMN TITORCTXT01 POSITION 18;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV3 POSITION 19;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT3 POSITION 20;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT3 POSITION 21;

ALTER TABLE SGPREFERE1 ALTER COLUMN ORDNOTA POSITION 22;

ALTER TABLE SGPREFERE1 ALTER COLUMN SETORVENDA POSITION 23;

ALTER TABLE SGPREFERE1 ALTER COLUMN PREFCRED POSITION 24;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPREFCRED POSITION 25;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMO POSITION 26;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMO POSITION 27;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMOEDA POSITION 28;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV4 POSITION 29;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT4 POSITION 30;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT4 POSITION 31;

ALTER TABLE SGPREFERE1 ALTER COLUMN USACLASCOMIS POSITION 32;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERCPRECOCUSTO POSITION 33;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODGRUP POSITION 34;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALGP POSITION 35;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPGP POSITION 36;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMARCA POSITION 37;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMC POSITION 38;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMC POSITION 39;

ALTER TABLE SGPREFERE1 ALTER COLUMN RGCLIOBRIG POSITION 40;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABFRETEVD POSITION 41;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABADICVD POSITION 42;

ALTER TABLE SGPREFERE1 ALTER COLUMN TRAVATMNFVD POSITION 43;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOVALIDORC POSITION 44;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLIMESMOCNPJ POSITION 45;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTBJ POSITION 46;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTJ POSITION 47;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTJ POSITION 48;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJOBRIGCLI POSITION 49;

ALTER TABLE SGPREFERE1 ALTER COLUMN JUROSPOSCALC POSITION 50;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFR POSITION 51;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFR POSITION 52;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFOR POSITION 53;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTN POSITION 54;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTN POSITION 55;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTRAN POSITION 56;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTF POSITION 57;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTF POSITION 58;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFOR POSITION 59;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT5 POSITION 60;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT5 POSITION 61;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV5 POSITION 62;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTLOTNEG POSITION 63;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEG POSITION 64;

ALTER TABLE SGPREFERE1 ALTER COLUMN NATVENDA POSITION 65;

ALTER TABLE SGPREFERE1 ALTER COLUMN IPIVENDA POSITION 66;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOSICMS POSITION 67;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPG POSITION 68;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPG POSITION 69;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAG POSITION 70;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTB POSITION 71;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTB POSITION 72;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTAB POSITION 73;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPCE POSITION 74;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALCE POSITION 75;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODCLASCLI POSITION 76;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDEC POSITION 77;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECFIN POSITION 78;

ALTER TABLE SGPREFERE1 ALTER COLUMN COMISPDUPL POSITION 79;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT6 POSITION 80;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT6 POSITION 81;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV6 POSITION 82;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQVENDA POSITION 83;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQCOMPRA POSITION 84;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAMATPRIM POSITION 85;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAPATRIM POSITION 86;

ALTER TABLE SGPREFERE1 ALTER COLUMN PEPSPROD POSITION 87;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJFOROBRIG POSITION 88;

ALTER TABLE SGPREFERE1 ALTER COLUMN INSCESTFOROBRIG POSITION 89;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAPRODSIMILAR POSITION 90;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTIALMOX POSITION 91;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT8 POSITION 92;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT8 POSITION 93;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV8 POSITION 94;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEGGRUP POSITION 95;

ALTER TABLE SGPREFERE1 ALTER COLUMN USATABPE POSITION 96;

ALTER TABLE SGPREFERE1 ALTER COLUMN TAMDESCPROD POSITION 97;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCCOMPPED POSITION 98;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSCLIVEND POSITION 99;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONTESTOQ POSITION 100;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIASPEDT POSITION 101;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCVENDA POSITION 102;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCORC POSITION 103;

ALTER TABLE SGPREFERE1 ALTER COLUMN USALAYOUTPED POSITION 104;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERIFALTPARCVENDA POSITION 105;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACODPRODGEN POSITION 106;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENPROD POSITION 107;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENREF POSITION 108;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODBAR POSITION 109;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFAB POSITION 110;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFOR POSITION 111;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAVLRULTCOMPRA POSITION 112;

ALTER TABLE SGPREFERE1 ALTER COLUMN ICMSVENDA POSITION 113;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOZERO POSITION 114;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIMGASSORC POSITION 115;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMGASSORC POSITION 116;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTCPFCLI POSITION 117;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIECLI POSITION 118;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEFOR POSITION 119;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTECPFFOR POSITION 120;

ALTER TABLE SGPREFERE1 ALTER COLUMN USANOMEVENDORC POSITION 121;

ALTER TABLE SGPREFERE1 ALTER COLUMN SISCONTABIL POSITION 122;

ALTER TABLE SGPREFERE1 ALTER COLUMN ATBANCOIMPBOL POSITION 123;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCODBAR POSITION 124;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICORCOBSPED POSITION 125;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMENSORC POSITION 126;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMENSORC POSITION 127;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSORC POSITION 128;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOCOMPRA POSITION 129;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPCP POSITION 130;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPORC POSITION 131;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABSOLCP POSITION 132;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICFRETEBASEICM POSITION 133;

ALTER TABLE SGPREFERE1 ALTER COLUMN PRECOCPREL POSITION 134;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCORC POSITION 135;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTICOMIS POSITION 136;

ALTER TABLE SGPREFERE1 ALTER COLUMN USUATIVCLI POSITION 137;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTREC POSITION 138;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTREC POSITION 139;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTREC POSITION 140;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTPAG POSITION 141;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTPAG POSITION 142;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTPAG POSITION 143;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTC POSITION 144;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTC POSITION 145;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOCLI POSITION 146;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTITRECALTDTVENC POSITION 147;

ALTER TABLE SGPREFERE1 ALTER COLUMN LCREDGLOBAL POSITION 148;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDMANUTCOMOBRIG POSITION 149;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSPED POSITION 150;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCLASSPED POSITION 151;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGECLI POSITION 152;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGEFOR POSITION 153;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGETRANSP POSITION 154;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOMAVOLUMES POSITION 155;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACEP POSITION 156;

ALTER TABLE SGPREFERE1 ALTER COLUMN URLWSCEP POSITION 157;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSCP POSITION 158;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS01CP POSITION 159;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS02CP POSITION 160;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS03CP POSITION 161;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS04CP POSITION 162;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEPF POSITION 163;

ALTER TABLE SGPREFERE1 ALTER COLUMN CREDICMSSIMPLES POSITION 164;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMS POSITION 165;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMS POSITION 166;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSICMSSIMPLES POSITION 167;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACOMISVENDAORC POSITION 168;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACODUNIF POSITION 169;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV9 POSITION 170;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT9 POSITION 171;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT9 POSITION 172;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJP POSITION 173;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJP POSITION 174;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJP POSITION 175;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJR POSITION 176;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJR POSITION 177;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJR POSITION 178;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDR POSITION 179;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDR POSITION 180;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDR POSITION 181;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDC POSITION 182;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDC POSITION 183;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDC POSITION 184;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERAPAGEMIS POSITION 185;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCAFINCONTR POSITION 186;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCARMACONTR POSITION 187;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECPRE POSITION 188;

ALTER TABLE SGPREFERE1 ALTER COLUMN VISUALIZALUCR POSITION 189;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSNFE POSITION 190;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFE POSITION 191;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFELIN POSITION 192;

ALTER TABLE SGPREFERE1 ALTER COLUMN FORMATODANFE POSITION 193;

ALTER TABLE SGPREFERE1 ALTER COLUMN AMBIENTENFE POSITION 194;

ALTER TABLE SGPREFERE1 ALTER COLUMN PROCEMINFE POSITION 195;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERPROCNFE POSITION 196;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICNFE POSITION 197;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTONFE POSITION 198;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFADPRODNFE POSITION 199;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPNF POSITION 200;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALNF POSITION 201;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMAILNF POSITION 202;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 211;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOT POSITION 212;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOAPROV POSITION 213;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFT POSITION 214;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFT POSITION 215;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFORFT POSITION 216;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOMIS POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESPECIALCOMIS POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTS POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVS POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTS POSITION 222;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 223;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 224;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 225;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 226;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 227;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPSV POSITION 228;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALSV POSITION 229;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAGSV POSITION 230;

ALTER TABLE SGPREFERE1 ALTER COLUMN ARREDPRECO POSITION 231;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPTR POSITION 3;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALTR POSITION 4;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPORECMERC POSITION 5;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPCM POSITION 6;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALCM POSITION 7;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPORECMERCCM POSITION 8;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPTC POSITION 9;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALTC POSITION 10;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPOMOVTC POSITION 11;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPTO POSITION 12;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALTO POSITION 13;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPORECMERCOS POSITION 14;

ALTER TABLE SGPREFERE8 ALTER COLUMN GERACHAMADOOS POSITION 15;

ALTER TABLE SGPREFERE8 ALTER COLUMN USAPRECOPECASERV POSITION 16;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPDS POSITION 17;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALDS POSITION 18;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPOMOVDS POSITION 19;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPSE POSITION 20;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALSE POSITION 21;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODPRODSE POSITION 22;

ALTER TABLE SGPREFERE8 ALTER COLUMN DTINS POSITION 23;

ALTER TABLE SGPREFERE8 ALTER COLUMN HINS POSITION 24;

ALTER TABLE SGPREFERE8 ALTER COLUMN IDUSUINS POSITION 25;

ALTER TABLE SGPREFERE8 ALTER COLUMN DTALT POSITION 26;

ALTER TABLE SGPREFERE8 ALTER COLUMN HALT POSITION 27;

ALTER TABLE SGPREFERE8 ALTER COLUMN IDUSUALT POSITION 28;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCLI POSITION 3;

ALTER TABLE VDCLIENTE ALTER COLUMN RAZCLI POSITION 4;

ALTER TABLE VDCLIENTE ALTER COLUMN NOMECLI POSITION 5;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPCC POSITION 6;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALCC POSITION 7;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCLASCLI POSITION 8;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPVD POSITION 9;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALVD POSITION 10;

ALTER TABLE VDCLIENTE ALTER COLUMN CODVEND POSITION 11;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPTC POSITION 12;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALTC POSITION 13;

ALTER TABLE VDCLIENTE ALTER COLUMN CODTIPOCOB POSITION 14;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPPG POSITION 15;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALPG POSITION 16;

ALTER TABLE VDCLIENTE ALTER COLUMN CODPLANOPAG POSITION 17;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPTN POSITION 18;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALTN POSITION 19;

ALTER TABLE VDCLIENTE ALTER COLUMN CODTRAN POSITION 20;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPBO POSITION 21;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALBO POSITION 22;

ALTER TABLE VDCLIENTE ALTER COLUMN CODBANCO POSITION 23;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPSR POSITION 24;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALSR POSITION 25;

ALTER TABLE VDCLIENTE ALTER COLUMN CODSETOR POSITION 26;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPTI POSITION 27;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALTI POSITION 28;

ALTER TABLE VDCLIENTE ALTER COLUMN CODTIPOCLI POSITION 29;

ALTER TABLE VDCLIENTE ALTER COLUMN CODTPCRED POSITION 30;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALTR POSITION 31;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPTR POSITION 32;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFISCCLI POSITION 33;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPFC POSITION 34;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALFC POSITION 35;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPEC POSITION 36;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALEC POSITION 37;

ALTER TABLE VDCLIENTE ALTER COLUMN CODTBEC POSITION 38;

ALTER TABLE VDCLIENTE ALTER COLUMN CODITTBEC POSITION 39;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPHP POSITION 40;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALHP POSITION 41;

ALTER TABLE VDCLIENTE ALTER COLUMN CODHIST POSITION 42;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPCB POSITION 43;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALCB POSITION 44;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCARTCOB POSITION 45;

ALTER TABLE VDCLIENTE ALTER COLUMN DATACLI POSITION 46;

ALTER TABLE VDCLIENTE ALTER COLUMN PESSOACLI POSITION 47;

ALTER TABLE VDCLIENTE ALTER COLUMN ATIVOCLI POSITION 48;

ALTER TABLE VDCLIENTE ALTER COLUMN CNPJCLI POSITION 49;

ALTER TABLE VDCLIENTE ALTER COLUMN INSCCLI POSITION 50;

ALTER TABLE VDCLIENTE ALTER COLUMN CPFCLI POSITION 51;

ALTER TABLE VDCLIENTE ALTER COLUMN RGCLI POSITION 52;

ALTER TABLE VDCLIENTE ALTER COLUMN SSPCLI POSITION 53;

ALTER TABLE VDCLIENTE ALTER COLUMN ENDCLI POSITION 54;

ALTER TABLE VDCLIENTE ALTER COLUMN NUMCLI POSITION 55;

ALTER TABLE VDCLIENTE ALTER COLUMN COMPLCLI POSITION 56;

ALTER TABLE VDCLIENTE ALTER COLUMN BAIRCLI POSITION 57;

ALTER TABLE VDCLIENTE ALTER COLUMN CIDCLI POSITION 58;

ALTER TABLE VDCLIENTE ALTER COLUMN UFCLI POSITION 59;

ALTER TABLE VDCLIENTE ALTER COLUMN CEPCLI POSITION 60;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDCLI POSITION 61;

ALTER TABLE VDCLIENTE ALTER COLUMN FONECLI POSITION 62;

ALTER TABLE VDCLIENTE ALTER COLUMN RAMALCLI POSITION 63;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDFAXCLI POSITION 64;

ALTER TABLE VDCLIENTE ALTER COLUMN FAXCLI POSITION 65;

ALTER TABLE VDCLIENTE ALTER COLUMN EMAILCLI POSITION 66;

ALTER TABLE VDCLIENTE ALTER COLUMN EMAILCOB POSITION 67;

ALTER TABLE VDCLIENTE ALTER COLUMN EMAILENT POSITION 68;

ALTER TABLE VDCLIENTE ALTER COLUMN EMAILNFECLI POSITION 69;

ALTER TABLE VDCLIENTE ALTER COLUMN CONTCLI POSITION 70;

ALTER TABLE VDCLIENTE ALTER COLUMN ENDCOB POSITION 71;

ALTER TABLE VDCLIENTE ALTER COLUMN NUMCOB POSITION 72;

ALTER TABLE VDCLIENTE ALTER COLUMN COMPLCOB POSITION 73;

ALTER TABLE VDCLIENTE ALTER COLUMN BAIRCOB POSITION 74;

ALTER TABLE VDCLIENTE ALTER COLUMN CIDCOB POSITION 75;

ALTER TABLE VDCLIENTE ALTER COLUMN UFCOB POSITION 76;

ALTER TABLE VDCLIENTE ALTER COLUMN CEPCOB POSITION 77;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDFONECOB POSITION 78;

ALTER TABLE VDCLIENTE ALTER COLUMN FONECOB POSITION 79;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDFAXCOB POSITION 80;

ALTER TABLE VDCLIENTE ALTER COLUMN FAXCOB POSITION 81;

ALTER TABLE VDCLIENTE ALTER COLUMN ENDENT POSITION 82;

ALTER TABLE VDCLIENTE ALTER COLUMN NUMENT POSITION 83;

ALTER TABLE VDCLIENTE ALTER COLUMN COMPLENT POSITION 84;

ALTER TABLE VDCLIENTE ALTER COLUMN BAIRENT POSITION 85;

ALTER TABLE VDCLIENTE ALTER COLUMN CIDENT POSITION 86;

ALTER TABLE VDCLIENTE ALTER COLUMN UFENT POSITION 87;

ALTER TABLE VDCLIENTE ALTER COLUMN CEPENT POSITION 88;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDFONEENT POSITION 89;

ALTER TABLE VDCLIENTE ALTER COLUMN FONEENT POSITION 90;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDFAXENT POSITION 91;

ALTER TABLE VDCLIENTE ALTER COLUMN FAXENT POSITION 92;

ALTER TABLE VDCLIENTE ALTER COLUMN OBSCLI POSITION 93;

ALTER TABLE VDCLIENTE ALTER COLUMN AGENCIACLI POSITION 94;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPPQ POSITION 95;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALPQ POSITION 96;

ALTER TABLE VDCLIENTE ALTER COLUMN CODPESQ POSITION 97;

ALTER TABLE VDCLIENTE ALTER COLUMN INCRACLI POSITION 98;

ALTER TABLE VDCLIENTE ALTER COLUMN DTINITR POSITION 99;

ALTER TABLE VDCLIENTE ALTER COLUMN DTVENCTOTR POSITION 100;

ALTER TABLE VDCLIENTE ALTER COLUMN NIRFCLI POSITION 101;

ALTER TABLE VDCLIENTE ALTER COLUMN SIMPLESCLI POSITION 102;

ALTER TABLE VDCLIENTE ALTER COLUMN DDDCELCLI POSITION 103;

ALTER TABLE VDCLIENTE ALTER COLUMN CELCLI POSITION 104;

ALTER TABLE VDCLIENTE ALTER COLUMN NATCLI POSITION 105;

ALTER TABLE VDCLIENTE ALTER COLUMN UFNATCLI POSITION 106;

ALTER TABLE VDCLIENTE ALTER COLUMN TEMPORESCLI POSITION 107;

ALTER TABLE VDCLIENTE ALTER COLUMN APELIDOCLI POSITION 108;

ALTER TABLE VDCLIENTE ALTER COLUMN SITECLI POSITION 109;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCONTDEB POSITION 110;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCONTCRED POSITION 111;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCLICONTAB POSITION 112;

ALTER TABLE VDCLIENTE ALTER COLUMN FOTOCLI POSITION 113;

ALTER TABLE VDCLIENTE ALTER COLUMN IMGASSCLI POSITION 114;

ALTER TABLE VDCLIENTE ALTER COLUMN CODMUNIC POSITION 115;

ALTER TABLE VDCLIENTE ALTER COLUMN SIGLAUF POSITION 116;

ALTER TABLE VDCLIENTE ALTER COLUMN CODPAIS POSITION 117;

ALTER TABLE VDCLIENTE ALTER COLUMN CODMUNICENT POSITION 118;

ALTER TABLE VDCLIENTE ALTER COLUMN SIGLAUFENT POSITION 119;

ALTER TABLE VDCLIENTE ALTER COLUMN CODPAISENT POSITION 120;

ALTER TABLE VDCLIENTE ALTER COLUMN CODMUNICCOB POSITION 121;

ALTER TABLE VDCLIENTE ALTER COLUMN SIGLAUFCOB POSITION 122;

ALTER TABLE VDCLIENTE ALTER COLUMN CODPAISCOB POSITION 123;

ALTER TABLE VDCLIENTE ALTER COLUMN CODEMPUC POSITION 124;

ALTER TABLE VDCLIENTE ALTER COLUMN CODFILIALUC POSITION 125;

ALTER TABLE VDCLIENTE ALTER COLUMN CODUNIFCOD POSITION 126;

ALTER TABLE VDCLIENTE ALTER COLUMN SUFRAMACLI POSITION 127;

ALTER TABLE VDCLIENTE ALTER COLUMN PRODRURALCLI POSITION 128;

ALTER TABLE VDCLIENTE ALTER COLUMN CTOCLI POSITION 129;

ALTER TABLE VDCLIENTE ALTER COLUMN CODCNAE POSITION 130;

ALTER TABLE VDCLIENTE ALTER COLUMN INSCMUNCLI POSITION 131;

ALTER TABLE VDCLIENTE ALTER COLUMN PERCDESCCLI POSITION 132;

ALTER TABLE VDCLIENTE ALTER COLUMN CONTCLICOB POSITION 133;

ALTER TABLE VDCLIENTE ALTER COLUMN CONTCLIENT POSITION 134;

ALTER TABLE VDCLIENTE ALTER COLUMN DTINS POSITION 135;

ALTER TABLE VDCLIENTE ALTER COLUMN HINS POSITION 136;

ALTER TABLE VDCLIENTE ALTER COLUMN IDUSUINS POSITION 137;

ALTER TABLE VDCLIENTE ALTER COLUMN DTALT POSITION 138;

ALTER TABLE VDCLIENTE ALTER COLUMN HALT POSITION 139;

ALTER TABLE VDCLIENTE ALTER COLUMN IDUSUALT POSITION 140;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODCOMI POSITION 3;

ALTER TABLE VDCOMISSAO ALTER COLUMN TIPOCOMI POSITION 4;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMPRC POSITION 5;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIALRC POSITION 6;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODREC POSITION 7;

ALTER TABLE VDCOMISSAO ALTER COLUMN NPARCITREC POSITION 8;

ALTER TABLE VDCOMISSAO ALTER COLUMN VLRVENDACOMI POSITION 9;

ALTER TABLE VDCOMISSAO ALTER COLUMN VLRCOMI POSITION 10;

ALTER TABLE VDCOMISSAO ALTER COLUMN VLRPAGOCOMI POSITION 11;

ALTER TABLE VDCOMISSAO ALTER COLUMN VLRAPAGCOMI POSITION 12;

ALTER TABLE VDCOMISSAO ALTER COLUMN DATACOMI POSITION 13;

ALTER TABLE VDCOMISSAO ALTER COLUMN DTVENCCOMI POSITION 14;

ALTER TABLE VDCOMISSAO ALTER COLUMN DTPAGTOCOMI POSITION 15;

ALTER TABLE VDCOMISSAO ALTER COLUMN STATUSCOMI POSITION 16;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMPCI POSITION 17;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIALCI POSITION 18;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODPCOMI POSITION 19;

ALTER TABLE VDCOMISSAO ALTER COLUMN FLAG POSITION 20;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMPCT POSITION 21;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIALCT POSITION 22;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODPCOMIANT POSITION 23;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMPVD POSITION 24;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIALVD POSITION 25;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODVEND POSITION 26;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODEMPVE POSITION 27;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODFILIALVE POSITION 28;

ALTER TABLE VDCOMISSAO ALTER COLUMN CODVENDA POSITION 29;

ALTER TABLE VDCOMISSAO ALTER COLUMN TIPOVENDA POSITION 30;

ALTER TABLE VDCOMISSAO ALTER COLUMN DTINS POSITION 31;

ALTER TABLE VDCOMISSAO ALTER COLUMN HINS POSITION 32;

ALTER TABLE VDCOMISSAO ALTER COLUMN IDUSUINS POSITION 33;

ALTER TABLE VDCOMISSAO ALTER COLUMN DTALT POSITION 34;

ALTER TABLE VDCOMISSAO ALTER COLUMN HALT POSITION 35;

ALTER TABLE VDCOMISSAO ALTER COLUMN IDUSUALT POSITION 36;

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

ALTER TABLE VDITVENDA ALTER COLUMN EMMANUT POSITION 85;

ALTER TABLE VDITVENDA ALTER COLUMN DTINS POSITION 86;

ALTER TABLE VDITVENDA ALTER COLUMN HINS POSITION 87;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUINS POSITION 88;

ALTER TABLE VDITVENDA ALTER COLUMN DTALT POSITION 89;

ALTER TABLE VDITVENDA ALTER COLUMN HALT POSITION 90;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUALT POSITION 91;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODREGRCOMIS POSITION 3;

ALTER TABLE VDREGRACOMIS ALTER COLUMN DESCREGRCOMIS POSITION 4;

ALTER TABLE VDREGRACOMIS ALTER COLUMN IDUSUALT POSITION 5;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODEMPSC POSITION 6;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODFILIALSC POSITION 7;

ALTER TABLE VDREGRACOMIS ALTER COLUMN CODSECAO POSITION 8;

ALTER TABLE VDREGRACOMIS ALTER COLUMN PERCCOMISGERAL POSITION 9;

ALTER TABLE VDREGRACOMIS ALTER COLUMN DTINS POSITION 10;

ALTER TABLE VDREGRACOMIS ALTER COLUMN HINS POSITION 11;

ALTER TABLE VDREGRACOMIS ALTER COLUMN IDUSUINS POSITION 12;

ALTER TABLE VDREGRACOMIS ALTER COLUMN DTALT POSITION 13;

ALTER TABLE VDREGRACOMIS ALTER COLUMN HALT POSITION 14;

ALTER TABLE VDVENDA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDVENDA ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE VDVENDA ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPVD POSITION 5;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALVD POSITION 6;

ALTER TABLE VDVENDA ALTER COLUMN CODVEND POSITION 7;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCL POSITION 8;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCL POSITION 9;

ALTER TABLE VDVENDA ALTER COLUMN CODCLI POSITION 10;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPPG POSITION 11;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALPG POSITION 12;

ALTER TABLE VDVENDA ALTER COLUMN CODPLANOPAG POSITION 13;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPSE POSITION 14;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALSE POSITION 15;

ALTER TABLE VDVENDA ALTER COLUMN SERIE POSITION 16;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPTM POSITION 17;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALTM POSITION 18;

ALTER TABLE VDVENDA ALTER COLUMN CODTIPOMOV POSITION 19;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCX POSITION 20;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCX POSITION 21;

ALTER TABLE VDVENDA ALTER COLUMN CODCAIXA POSITION 22;

ALTER TABLE VDVENDA ALTER COLUMN DOCVENDA POSITION 23;

ALTER TABLE VDVENDA ALTER COLUMN DTSAIDAVENDA POSITION 24;

ALTER TABLE VDVENDA ALTER COLUMN DTEMITVENDA POSITION 25;

ALTER TABLE VDVENDA ALTER COLUMN VLRPRODVENDA POSITION 26;

ALTER TABLE VDVENDA ALTER COLUMN PERCDESCVENDA POSITION 27;

ALTER TABLE VDVENDA ALTER COLUMN VLRDESCVENDA POSITION 28;

ALTER TABLE VDVENDA ALTER COLUMN VLRDESCITVENDA POSITION 29;

ALTER TABLE VDVENDA ALTER COLUMN VLRVENDA POSITION 30;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEICMSVENDA POSITION 31;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSVENDA POSITION 32;

ALTER TABLE VDVENDA ALTER COLUMN CALCICMSVENDA POSITION 33;

ALTER TABLE VDVENDA ALTER COLUMN IMPICMSVENDA POSITION 34;

ALTER TABLE VDVENDA ALTER COLUMN VLRISENTASVENDA POSITION 35;

ALTER TABLE VDVENDA ALTER COLUMN VLROUTRASVENDA POSITION 36;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEIPIVENDA POSITION 37;

ALTER TABLE VDVENDA ALTER COLUMN VLRLIQVENDA POSITION 38;

ALTER TABLE VDVENDA ALTER COLUMN PERCCOMISVENDA POSITION 39;

ALTER TABLE VDVENDA ALTER COLUMN VLRCOMISVENDA POSITION 40;

ALTER TABLE VDVENDA ALTER COLUMN STATUSVENDA POSITION 41;

ALTER TABLE VDVENDA ALTER COLUMN VLRFRETEVENDA POSITION 42;

ALTER TABLE VDVENDA ALTER COLUMN VLRADICVENDA POSITION 43;

ALTER TABLE VDVENDA ALTER COLUMN VLRIPIVENDA POSITION 44;

ALTER TABLE VDVENDA ALTER COLUMN CALCIPIVENDA POSITION 45;

ALTER TABLE VDVENDA ALTER COLUMN IMPIPIVENDA POSITION 46;

ALTER TABLE VDVENDA ALTER COLUMN OBSVENDA POSITION 47;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPBO POSITION 48;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALBO POSITION 49;

ALTER TABLE VDVENDA ALTER COLUMN CODBANCO POSITION 50;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPTC POSITION 51;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALTC POSITION 52;

ALTER TABLE VDVENDA ALTER COLUMN CODTIPOCOB POSITION 53;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEISSVENDA POSITION 54;

ALTER TABLE VDVENDA ALTER COLUMN VLRISSVENDA POSITION 55;

ALTER TABLE VDVENDA ALTER COLUMN CALCISSVENDA POSITION 56;

ALTER TABLE VDVENDA ALTER COLUMN IMPIISSVENDA POSITION 57;

ALTER TABLE VDVENDA ALTER COLUMN IMPNOTAVENDA POSITION 58;

ALTER TABLE VDVENDA ALTER COLUMN FLAG POSITION 59;

ALTER TABLE VDVENDA ALTER COLUMN CODCLASCOMIS POSITION 60;

ALTER TABLE VDVENDA ALTER COLUMN VLRPISVENDA POSITION 61;

ALTER TABLE VDVENDA ALTER COLUMN CALCPISVENDA POSITION 62;

ALTER TABLE VDVENDA ALTER COLUMN IMPPISVENDA POSITION 63;

ALTER TABLE VDVENDA ALTER COLUMN VLRCOFINSVENDA POSITION 64;

ALTER TABLE VDVENDA ALTER COLUMN CALCCOFINSVENDA POSITION 65;

ALTER TABLE VDVENDA ALTER COLUMN IMPCOFINSVENDA POSITION 66;

ALTER TABLE VDVENDA ALTER COLUMN VLRIRVENDA POSITION 67;

ALTER TABLE VDVENDA ALTER COLUMN CALCIRVENDA POSITION 68;

ALTER TABLE VDVENDA ALTER COLUMN IMPIRVENDA POSITION 69;

ALTER TABLE VDVENDA ALTER COLUMN VLRCSOCIALVENDA POSITION 70;

ALTER TABLE VDVENDA ALTER COLUMN CALCCSOCIALVENDA POSITION 71;

ALTER TABLE VDVENDA ALTER COLUMN IMPCSOCIALVENDA POSITION 72;

ALTER TABLE VDVENDA ALTER COLUMN PERCMCOMISVENDA POSITION 73;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCM POSITION 74;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCM POSITION 75;

ALTER TABLE VDVENDA ALTER COLUMN CODCLCOMIS POSITION 76;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCB POSITION 77;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCB POSITION 78;

ALTER TABLE VDVENDA ALTER COLUMN CODCARTCOB POSITION 79;

ALTER TABLE VDVENDA ALTER COLUMN PEDCLIVENDA POSITION 80;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSSTVENDA POSITION 81;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEICMSSTVENDA POSITION 82;

ALTER TABLE VDVENDA ALTER COLUMN EMMANUT POSITION 83;

ALTER TABLE VDVENDA ALTER COLUMN BLOQVENDA POSITION 84;

ALTER TABLE VDVENDA ALTER COLUMN VLRICMSSIMPLES POSITION 85;

ALTER TABLE VDVENDA ALTER COLUMN PERCICMSSIMPLES POSITION 86;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASEPISVENDA POSITION 87;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASECOFINSVENDA POSITION 88;

ALTER TABLE VDVENDA ALTER COLUMN VLRBASECOMIS POSITION 89;

ALTER TABLE VDVENDA ALTER COLUMN CHAVENFEVENDA POSITION 90;

ALTER TABLE VDVENDA ALTER COLUMN CODEMPCA POSITION 91;

ALTER TABLE VDVENDA ALTER COLUMN CODFILIALCA POSITION 92;

ALTER TABLE VDVENDA ALTER COLUMN NUMCONTA POSITION 93;

ALTER TABLE VDVENDA ALTER COLUMN OBSREC POSITION 94;

ALTER TABLE VDVENDA ALTER COLUMN DTINS POSITION 95;

ALTER TABLE VDVENDA ALTER COLUMN HINS POSITION 96;

ALTER TABLE VDVENDA ALTER COLUMN IDUSUINS POSITION 97;

ALTER TABLE VDVENDA ALTER COLUMN DTALT POSITION 98;

ALTER TABLE VDVENDA ALTER COLUMN HALT POSITION 99;

ALTER TABLE VDVENDA ALTER COLUMN IDUSUALT POSITION 100;

/* Create(Add) Crant */
GRANT DELETE, INSERT, SELECT ON EQITRECMERCSERIE TO PROCEDURE EQITRECMERCSERIESP;

GRANT DELETE, INSERT, SELECT, UPDATE ON FNCONTAVINCULADA TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON PPPROCESSAOPTMP TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON SGPREFERE8 TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON VDSETORROTA TO ADM;

GRANT EXECUTE ON PROCEDURE EQITRECMERCSERIESP TO ADM;

GRANT EXECUTE ON PROCEDURE EQITRECMERCSERIESP TO PROCEDURE EQITRECMERCSERIESP;

GRANT SELECT ON EQCONFESTOQVW01 TO ADM;

GRANT SELECT ON EQPRODUTO TO PROCEDURE EQITRECMERCSERIESP;

GRANT SELECT ON PPLISTAOPVW01 TO ADM;

GRANT SELECT ON VWCUSTOPROJ01 TO ADM;

COMMIT;

