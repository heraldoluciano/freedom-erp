SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor dos descontos. (Deve ser incluidos as retenções se houverem)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRDESCPAG';

SET TERM ^ ;

ALTER TRIGGER FNPAGARTGAI
 AS Declare variable I integer;
BEGIN I = 0; END
^

SET TERM ; ^

ALTER TABLE FNPAGAR ALTER COLUMN OBSPAG TYPE VARCHAR(250);

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODEMPNT' AND RDB$RELATION_NAME='LFFRETE';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODFILIALNT' AND RDB$RELATION_NAME='LFFRETE';

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='CODNAT' AND RDB$RELATION_NAME='LFFRETE';

CREATE DOMAIN IBCXXX NUMERIC(15,5) DEFAULT 0;

UPDATE RDB$RELATION_FIELDS SET RDB$DEFAULT_SOURCE=
(SELECT RDB$DEFAULT_SOURCE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX'),
RDB$DEFAULT_VALUE=
(SELECT RDB$DEFAULT_VALUE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX')
WHERE RDB$FIELD_NAME='ALIQICMSFRETE' AND RDB$RELATION_NAME='LFFRETE';

DROP DOMAIN IBCXXX;

CREATE DOMAIN IBCXXX NUMERIC(15,5) DEFAULT 0;

UPDATE RDB$RELATION_FIELDS SET RDB$DEFAULT_SOURCE=
(SELECT RDB$DEFAULT_SOURCE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX'),
RDB$DEFAULT_VALUE=
(SELECT RDB$DEFAULT_VALUE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX')
WHERE RDB$FIELD_NAME='VLRICMSFRETE' AND RDB$RELATION_NAME='LFFRETE';

DROP DOMAIN IBCXXX;

CREATE DOMAIN IBCXXX NUMERIC(15,5) DEFAULT 0;

UPDATE RDB$RELATION_FIELDS SET RDB$DEFAULT_SOURCE=
(SELECT RDB$DEFAULT_SOURCE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX'),
RDB$DEFAULT_VALUE=
(SELECT RDB$DEFAULT_VALUE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX')
WHERE RDB$FIELD_NAME='VLRBASEICMSFRETE' AND RDB$RELATION_NAME='LFFRETE';

DROP DOMAIN IBCXXX;


ALTER TABLE CPCOMPRA ADD CODEMPTC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa na tabela tipo de cobrança.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPTC';

ALTER TABLE CPCOMPRA ADD CODFILIALTC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial na tabela tipo de cobrança.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALTC';

ALTER TABLE CPCOMPRA ADD CODTIPOCOB INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do tipo de cobrança.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODTIPOCOB';

ALTER TABLE CPCOMPRA ADD CODEMPRM INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do recebimento de mercadoria vinculado.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPRM';

ALTER TABLE CPCOMPRA ADD CODFILIALRM SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do recebimento de mercadoria vinculado.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALRM';

ALTER TABLE CPCOMPRA ADD TICKET INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket do recebimento de mercadoria vinculado.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='TICKET';

ALTER TABLE CPCOTACAO ADD USARENDACOT CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve usar o critério da renda na busca do preço de cotações.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='USARENDACOT';

ALTER TABLE CPCOTACAO ADD RENDACOT SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Indica a renda (qualidade) do produto cotado.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='RENDACOT';

ALTER TABLE CPCOTACAO ADD CODEMPPP INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODEMPPP';

ALTER TABLE CPCOTACAO ADD CODFILIALPP SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODFILIALPP';

ALTER TABLE CPCOTACAO ADD CODPLANOPAG INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODPLANOPAG';

ALTER TABLE CPFORNECED ADD NRODEPENDFOR SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Número de dependes do fornecedor (pessoa física) (calculo de irrf)'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='NRODEPENDFOR';

ALTER TABLE CPITCOMPRA ADD QTDITCOMPRACANC NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade cancelada'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='QTDITCOMPRACANC';

ALTER TABLE CPTIPOFOR ADD RETENCAOINSS CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a retenção do inss nos pagamentos (autonomo).'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOINSS';

ALTER TABLE CPTIPOFOR ADD PERCBASEINSS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de base de calculo do INSS.'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='PERCBASEINSS';

ALTER TABLE CPTIPOFOR ADD RETENCAOOUTROS CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve calcular outras retenções agregadas ao INSS ex.: SEST/SENAT'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOOUTROS';

ALTER TABLE CPTIPOFOR ADD PERCRETOUTROS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de retenção de outros tributos agregados ao inss (SEST/SENAT)'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='PERCRETOUTROS';

ALTER TABLE CPTIPOFOR ADD RETENCAOIRRF CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a retenção do imposto de renda nos pagamentos (autonomo).'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOIRRF';

ALTER TABLE CPTIPOFOR ADD PERCBASEIRRF NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de base de calculo do IRRF.'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='PERCBASEIRRF';

ALTER TABLE EQITRECMERCITOS ADD GERARMA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar RMA para o ítem de O.S.'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='GERARMA';

ALTER TABLE EQRECMERC ADD MEDIAAMOSTRAGEM NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Media geral da amostragem.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='MEDIAAMOSTRAGEM';

ALTER TABLE EQRECMERC ADD RENDAAMOSTRAGEM SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Renda media geral da amostragem'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='RENDAAMOSTRAGEM';

ALTER TABLE EQRECMERC ADD CODEMPAX INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODEMPAX';

ALTER TABLE EQRECMERC ADD CODFILIALAX SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODFILIALAX';

ALTER TABLE EQRECMERC ADD CODALMOX INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODALMOX';

ALTER TABLE EQRMA ADD CODEMPOS INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da empresa da ordem de serviço.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPOS';

ALTER TABLE EQRMA ADD CODFILIALOS SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da ordem de serviço.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALOS';

ALTER TABLE EQRMA ADD TICKET INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Número do ticket da ordem de serviço vinculada.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='TICKET';

ALTER TABLE FNBANCO ADD LAYOUTCHEQBANCO VARCHAR(1000);

Update Rdb$Relation_Fields set Rdb$Description =
'Layout de impressão de cheques.'
where Rdb$Relation_Name='FNBANCO' and Rdb$Field_Name='LAYOUTCHEQBANCO';

ALTER TABLE FNCARTCOB ADD VARIACAOCARTCOB CHAR(10);

Update Rdb$Relation_Fields set Rdb$Description =
'Variação da carteira de cobrança.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='VARIACAOCARTCOB';

ALTER TABLE FNPAGAR ADD VLRBASEIRRF NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do IRRF (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRBASEIRRF';

ALTER TABLE FNPAGAR ADD VLRBASEINSS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do INSS (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRBASEINSS';

ALTER TABLE FNPAGAR ADD VLRRETIRRF NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de desconto/retenção do IRRF (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRRETIRRF';

ALTER TABLE FNPAGAR ADD VLRRETINSS NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do desconto/retenção do INSS (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRRETINSS';

ALTER TABLE FNPAGAR ADD CODEMPPN INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODEMPPN';

ALTER TABLE FNPAGAR ADD CODFILIALPN SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODFILIALPN';

ALTER TABLE FNPAGAR ADD CODPLAN CHAR(13);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODPLAN';

ALTER TABLE FNPAGAR ADD CODEMPCC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODEMPCC';

ALTER TABLE FNPAGAR ADD CODFILIALCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODFILIALCC';

ALTER TABLE FNPAGAR ADD ANOCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Ano do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='ANOCC';

ALTER TABLE FNPAGAR ADD CODCC CHAR(19);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODCC';

ALTER TABLE FNRECEBER ADD CODEMPPN INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPPN';

ALTER TABLE FNRECEBER ADD CODFILIALPN SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALPN';

ALTER TABLE FNRECEBER ADD CODPLAN CHAR(13);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODPLAN';

ALTER TABLE FNRECEBER ADD CODEMPCC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPCC';

ALTER TABLE FNRECEBER ADD CODFILIALCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALCC';

ALTER TABLE FNRECEBER ADD ANOCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Ano do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='ANOCC';

ALTER TABLE FNRECEBER ADD CODCC CHAR(19);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODCC';

ALTER TABLE FNTIPOCOB ADD CODEMPCT INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa na tabela de contas.
'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='CODEMPCT';

ALTER TABLE FNTIPOCOB ADD CODFILIALCT SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial na tabela de contas.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='CODFILIALCT';

ALTER TABLE FNTIPOCOB ADD NUMCONTA VARCHAR(10);

Update Rdb$Relation_Fields set Rdb$Description =
'Número da conta para impressão de cheques.
'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='NUMCONTA';

ALTER TABLE FNTIPOCOB ADD SEQTALAO SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do talonário.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='SEQTALAO';

ALTER TABLE LFFRETE ADD CODEMPRM INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do recebimento de mercadorias.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODEMPRM';

ALTER TABLE LFFRETE ADD CODFILIALRM SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do recebimento de mercadorias.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODFILIALRM';

ALTER TABLE LFFRETE ADD TICKET INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket do recebimento de mercadoria.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='TICKET';

ALTER TABLE SGBAIRRO ADD VLRFRETE NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de frete estimado para carga vinda deste bairro.'
where Rdb$Relation_Name='SGBAIRRO' and Rdb$Field_Name='VLRFRETE';

ALTER TABLE SGBAIRRO ADD QTDFRETE NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade referente ao valor de frete.'
where Rdb$Relation_Name='SGBAIRRO' and Rdb$Field_Name='QTDFRETE';

ALTER TABLE SGPREFERE1 ADD CODEMPFT INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPFT';

ALTER TABLE SGPREFERE1 ADD CODFILIALFT SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALFT';

ALTER TABLE SGPREFERE1 ADD CODTIPOFORFT INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOFORFT';

ALTER TABLE VDITVENDA ADD QTDITVENDACANC NUMERICDN;

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade cancelada'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='QTDITVENDACANC';

/* Create Table... */
CREATE TABLE FNCHEQUE(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODEMPBO INTEGER NOT NULL,
SEQCHEQ INTEGER NOT NULL,
CODFILIALBO SMALLINT NOT NULL,
CODBANC CHAR(3) NOT NULL,
AGENCIACHEQ VARCHAR(7) NOT NULL,
CONTACHEQ VARCHAR(10) NOT NULL,
NUMCHEQ INTEGER NOT NULL,
NOMEEMITCHEQ VARCHAR(50) NOT NULL,
NOMEFAVCHEQ VARCHAR(50) NOT NULL,
DTEMITCHEQ DATE DEFAULT 'today' NOT NULL,
DTVENCTOCHEQ DATE DEFAULT 'today' NOT NULL,
DTCOMPCHEQ DATE DEFAULT 'today' NOT NULL,
TIPOCHEQ CHAR(2) DEFAULT 'PF' NOT NULL,
PREDATCHEQ CHAR(1) DEFAULT 'N',
SITCHEQ CHAR(2) DEFAULT 'CA' NOT NULL,
VLRCHEQ NUMERIC(15,5) DEFAULT 0 NOT NULL,
HISTCHEQ VARCHAR(500) NOT NULL,
CNPJEMITCHEQ VARCHAR(14),
CPFEMITCHEQ VARCHAR(11),
CNPJFAVCHEQ VARCHAR(14),
CPFFAVCHEQ VARCHAR(11),
DDDEMITCHEQ VARCHAR(4),
FONEEMITCHEQ VARCHAR(8),
DTINS DATE DEFAULT 'today' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT user NOT NULL,
DTALT DATE DEFAULT 'today' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT user NOT NULL);


Update Rdb$Relations set Rdb$Description =
'Tabela de cheques.'
where Rdb$Relation_Name='FNCHEQUE';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Código empresa na tabela banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODEMPBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='SEQCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial na tabela banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODFILIALBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Código do banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODBANC';

Update Rdb$Relation_Fields set Rdb$Description =
'Agência.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='AGENCIACHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Número da conta
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CONTACHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Número do cheque.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='NUMCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome do emitente.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='NOMEEMITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome do favorecido.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='NOMEFAVCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Emissão'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTEMITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de vencimento.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTVENCTOCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de compensação'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTCOMPCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de cheque.
PF - Pagamento de fornecedor;
RC - Recebimento de cliente.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='TIPOCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Cheque pré-datado (S/N)
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='PREDATCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Situação do cheque.
CA - Cadastrado;
ED - Emitido;
CD - Compensado;
DV - Devolvido.
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='SITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do cheque'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='VLRCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Histórico.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HISTCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'DDD Tel. emitente.
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DDDEMITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Telefone emitente.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='FONEEMITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserção.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserção.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Ident. do usuário que inseriu.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteração.
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteração.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Ident. usuário que alterou.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='IDUSUALT';

CREATE TABLE FNPAGCHEQ(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODPAG INTEGER NOT NULL,
NPARCPAG SMALLINT NOT NULL,
CODEMPCH INTEGER NOT NULL,
CODFILIALCH SMALLINT NOT NULL,
SEQCHEQ INTEGER NOT NULL,
DTINS DATE DEFAULT 'today' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'today' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL);


Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa na tabela FNPAGAR.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial na tabela FNPAGAR.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Código do pagamento.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Número sequencial.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='NPARCPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa na tabela FNCHEQUE.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODEMPCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Código filial na tabela FNCHEQUE.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODFILIALCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia de cheque.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='SEQCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserção.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserção.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Id. do usuário que inseriu.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteração.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteração.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Id. do usuário que alterou.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='IDUSUALT';

CREATE TABLE FNTALAOCHEQ(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
NUMCONTA CHAR(10) NOT NULL,
SEQTALAO SMALLINT NOT NULL,
DTTALAO DATE DEFAULT 'today' NOT NULL,
CHEQINITALAO INTEGER DEFAULT 0 NOT NULL,
CHEQFIMTALAO INTEGER DEFAULT 1 NOT NULL,
CHEQATUALTALAO INTEGER DEFAULT 1 NOT NULL,
ATIVOTALAO CHAR(1) DEFAULT 'S' NOT NULL,
DTINS DATE DEFAULT 'today' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT 'USER' NOT NULL,
DTALT DATE DEFAULT 'today' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT 'USER' NOT NULL);


Update Rdb$Relations set Rdb$Description =
'Tabela de talonário de cheques.'
where Rdb$Relation_Name='FNTALAOCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Número da conta'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='NUMCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do talão.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='SEQTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data do talão.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='DTTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Número do cheque inicial.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CHEQINITALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Número final do cheque.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CHEQFIMTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Último cheque impresso.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CHEQATUALTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Talonário ativo (S/N).'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='ATIVOTALAO';

CREATE TABLE RHTABELAINSS(CODTABINSS INTEGER NOT NULL,
TETO NUMERICDN NOT NULL,
ALIQUOTA NUMERICDN NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE RHTABELAIRRF(CODTABIRRF INTEGER NOT NULL,
TETO NUMERICDN NOT NULL,
ALIQUOTA NUMERICDN NOT NULL,
REDUCAODEPENDENTE NUMERICDN NOT NULL,
DEDUCAO NUMERICDN NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relation_Fields set Rdb$Description =
'Dedução'
where Rdb$Relation_Name='RHTABELAIRRF' and Rdb$Field_Name='DEDUCAO';


/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 AS
 BEGIN EXIT; END
^


/* Create Index... */
SET TERM ; ^

CREATE INDEX EQRECMERCIDXDTENT ON EQRECMERC(DTENT);


/* Create Primary Key... */
ALTER TABLE FNCHEQUE ADD CONSTRAINT FNCHEQUEPK PRIMARY KEY (SEQCHEQ,CODFILIAL,CODEMP);

ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQPK PRIMARY KEY (SEQCHEQ,CODPAG,NPARCPAG,CODFILIALCH,CODEMPCH,CODFILIAL,CODEMP);

ALTER TABLE FNTALAOCHEQ ADD CONSTRAINT FNTALAOCHEQPK PRIMARY KEY (NUMCONTA,SEQTALAO,CODFILIAL,CODEMP);

ALTER TABLE RHTABELAINSS ADD CONSTRAINT RHTABELAINSSPK PRIMARY KEY (CODTABINSS);

ALTER TABLE RHTABELAIRRF ADD CONSTRAINT RHTABELAIRRFPK PRIMARY KEY (CODTABIRRF);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.2/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE CPCOMPRA ADD CONSTRAINT CPCOMPRAFKEQRECMERC FOREIGN KEY (TICKET,CODFILIALRM,CODEMPRM) REFERENCES EQRECMERC(TICKET,CODFILIAL,CODEMP);

ALTER TABLE CPCOMPRA ADD CONSTRAINT CPCOMPRAFKFNTIPOCOB FOREIGN KEY (CODTIPOCOB,CODFILIALTC,CODEMPTC) REFERENCES FNTIPOCOB(CODTIPOCOB,CODFILIAL,CODEMP);

ALTER TABLE CPCOTACAO ADD CONSTRAINT CPCOTACAOFKFNPLANOPAG FOREIGN KEY (CODPLANOPAG,CODFILIALPP,CODEMPPP) REFERENCES FNPLANOPAG(CODPLANOPAG,CODFILIAL,CODEMP);

ALTER TABLE EQITRECMERCITCP ADD CONSTRAINT EQITRECMERCITCPFKITCOMPRA FOREIGN KEY (CODITCOMPRA,CODCOMPRA,CODFILIALCP,CODEMPCP) REFERENCES CPITCOMPRA(CODITCOMPRA,CODCOMPRA,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE EQITRECMERCITCP ADD CONSTRAINT EQITRECMERCITCPFKITRECMERC FOREIGN KEY (TICKET,CODITRECMERC,CODFILIAL,CODEMP) REFERENCES EQITRECMERC(TICKET,CODITRECMERC,CODFILIAL,CODEMP) ON DELETE CASCADE;

ALTER TABLE EQRECMERC ADD CONSTRAINT EQRECMERCFKEQALMOX FOREIGN KEY (CODALMOX,CODFILIALAX,CODEMPAX) REFERENCES EQALMOX(CODALMOX,CODFILIAL,CODEMP);

ALTER TABLE EQRMA ADD CONSTRAINT EQRMAFKEQRECMERC FOREIGN KEY (TICKET,CODFILIALOS,CODEMPOS) REFERENCES EQRECMERC(TICKET,CODFILIAL,CODEMP);

ALTER TABLE FNPAGAR ADD CONSTRAINT FNPAGARFKFNCC FOREIGN KEY (CODCC,ANOCC,CODFILIALCC,CODEMPCC) REFERENCES FNCC(CODCC,ANOCC,CODFILIAL,CODEMP);

ALTER TABLE FNPAGAR ADD CONSTRAINT FNPAGARFKFNPLANEJ FOREIGN KEY (CODPLAN,CODFILIALPN,CODEMPPN) REFERENCES FNPLANEJAMENTO(CODPLAN,CODFILIAL,CODEMP) USING INDEX FNPAGARFKSGPLANEJ;

ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQFKCHEQUE FOREIGN KEY (SEQCHEQ,CODFILIALCH,CODEMPCH) REFERENCES FNCHEQUE(SEQCHEQ,CODFILIAL,CODEMP) USING INDEX FNPAGCHEQFKCHEQ;

ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQFKFNITPAG FOREIGN KEY (NPARCPAG,CODPAG,CODFILIAL,CODEMP) REFERENCES FNITPAGAR(NPARCPAG,CODPAG,CODFILIAL,CODEMP) USING INDEX FNPAGCHEQFKFNPAGAR;

ALTER TABLE FNRECEBER ADD CONSTRAINT FNRECEBERFKFNCC FOREIGN KEY (CODCC,ANOCC,CODFILIALCC,CODEMPCC) REFERENCES FNCC(CODCC,ANOCC,CODFILIAL,CODEMP);

ALTER TABLE FNRECEBER ADD CONSTRAINT FNRECEBERFKFNPLANEJ FOREIGN KEY (CODPLAN,CODFILIALPN,CODEMPPN) REFERENCES FNPLANEJAMENTO(CODPLAN,CODFILIAL,CODEMP);

ALTER TABLE FNTIPOCOB ADD CONSTRAINT FNTIPOCOBFKFNTALON FOREIGN KEY (NUMCONTA,SEQTALAO,CODFILIAL,CODEMP) REFERENCES FNTALAOCHEQ(NUMCONTA,SEQTALAO,CODFILIAL,CODEMP);

ALTER TABLE LFFRETE ADD CONSTRAINT LFFRETEFKEQRECMERC FOREIGN KEY (TICKET,CODFILIALRM,CODEMPRM) REFERENCES EQRECMERC(TICKET,CODFILIAL,CODEMP);

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
NUMCONTA CHAR(10))
 RETURNS(I INTEGER)
 AS
 BEGIN EXIT; END
^

ALTER TRIGGER CPCOMPRATGAU
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
CODCC CHAR(19))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* Alter (CPADICCOMPRAPEDSP) */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable codempse integer;
declare variable codfilialse smallint;
declare variable serie char(4);
declare variable statuscompra char(2);
begin

    --Buscando a série da compra
    select tm.codempse, tm.codfilialse, tm.serie
    from eqtipomov tm
    where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
    into :codempse, :codfilialse, :serie;

    --Definição do status da compra
    statuscompra = 'P1';

    --Buscando doccompra
    select doc from lfnovodocsp(:serie, :codempse , :codfilialse) into doccompra;

    insert into cpcompra (
    codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor,
    codempse, codfilialse, serie, codemptm, codfilialtm, codtipomov, doccompra, dtentcompra, dtemitcompra, statuscompra, calctrib )
    values (
    :codemp, :codfilial, :codcompra, :codemppg, :codfilialpg, :codplanopag, :codempfr, :codfilialfr, :codfor,
    :codempse, :codfilialse, :serie, :codemptm, :codfilialtm, :codtipomov, :doccompra,
    cast('today' as date), cast('today' as date), :statuscompra, 'S' );

    iret = :codcompra;

    suspend;

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

/* Alter (EQGERARMAOSSP) */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod char(13);
declare variable codrma integer;
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
--declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
--declare variable codfase integer;
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
                     codempos,codfilialos,ticket,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket and os.coditrecmerc=:coditrecmerc
    and os.gerarma='S'
    into :coditos, :codemppd, :codfilialpd, :codprod, :refprod, :qtd, :custompmit
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
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE'
                            );

        update eqitrecmercitos os set os.gerarma='N'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

   end
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
CODCC CHAR(19))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
       INSERT INTO FNITRECEBER (CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc );
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
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta
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
  suspend;
end
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
DDTPAGOITPAG DATE,
SDOCLANCAITPAG CHAR(10),
SOBSITPAG CHAR(100),
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
                         NPARCPAG,CODEMPPN,CODFILIALPN,CODPLAN,DATALANCA,DOCLANCA,
                         HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG, CODEMPFR, CODFILIALFR, CODFOR)
         VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:iCodPag,
                 :iNParcPag,:iCodEmpPConta,:iCodFilialPConta,:sCodPlanConta,:dDtPagoItPag,:sDocLancaItPag,
                 :sObsItPag,:dDtPagoItPag,0,:cFlag, :ICODEMPFR, :ICODFILIALFR, :ICODFOR
         );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:ICODEMPPN,:ICODFILIALPN,:sCodplan,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:dVlrPagoItPag,:cFlag);

    -- Lançamento dos juros em conta distinta.

    IF(DVLRJUROSPAG >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:CODEMPJP,:CODFILIALJP,:CODPLANJP,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:DVLRJUROSPAG,:cFlag);

    END

    -- Lançamento de desconto em conta distinta.

    IF(DVLRDESC >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:CODEMPDR,:CODFILIALDR,:CODPLANDR,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:DVLRDESC*-1,:cFlag);

    END

END
^

/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER)
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG,STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra,'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB
                          );
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
CODCC CHAR(19))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
       INSERT INTO FNITRECEBER (CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc );
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
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta
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
  suspend;
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
CODCC CHAR(19))
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
begin
  /* Procedure Text */
  i = 0;
  nPerc = 100;
  nResto = nVLRREC;
  nRestoComi = nVLRCOMIREC;
  DTBASE = DDATAREC;

  -- Buscando preferencias

  select casasdecfin from sgprefere1 where codemp=:icodemp and codfilial=:scodfilial
  into casasdecfin;

  SELECT PARCPLANOPAG, REGRVCTOPLANOPAG, RVSABPLANOPAG, RVDOMPLANOPAG,
     RVFERPLANOPAG, RVDIAPLANOPAG, DIAVCTOPLANOPAG
    FROM FNPLANOPAG WHERE CODPLANOPAG=:iCODPLANOPAG
         AND CODEMP=:iCODEMPPG AND CODFILIAL = :sCODFILIALPG
         INTO :iNumParc, :REGRVCTO, :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO;
  FOR SELECT PERCPAG,DIASPAG FROM FNPARCPAG WHERE CODPLANOPAG=:iCODPLANOPAG
             AND CODEMP=:iCODEMPPG AND CODFILIAL = :sCODFILIALPG ORDER BY DIASPAG
             INTO :nPercPag,:iDiasPag DO
  BEGIN
    i = i+1;
    select v.deretorno
        from arreddouble(cast(:nVLRREC*:nPercPag as NUMERIC(15, 5))/:nPerc,:casasdecfin ) v
       into :nVlrItRec;
    nResto = nResto-nVlrItRec;
    IF (i = iNumParc) THEN
    BEGIN
      nVlrItRec=nVlrItRec+nResto;
    END
    select v.deretorno from
       arreddouble(cast(:nVlrComiRec*:nPercPag as NUMERIC(15, 5))/:nPerc,:casasdecfin) v
       into :nVlrComiItRec;
    nRestoComi = nRestoComi-nVlrComiItRec;
    if (i = iNumParc) then
    begin
      nVlrComiItRec = nVlrComiItRec+nRestoComi;
    end

    SELECT C.DTVENCRET FROM SGCALCVENCSP(:ICODEMP, :DTBASE, :dDATAREC+:iDiasPag, :REGRVCTO,
       :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO, :IDIASPAG ) C INTO :DTVENCTO;

    DTBASE = DTVENCTO;

    EXECUTE PROCEDURE fnadicitrecebersp01 (:cALTVLR, :iCODEMP,:sCODFILIAL,:iCODREC,:i,0,0,0,:nVlrItRec,0,
                            :dDATAREC,:DTVENCTO,'R1',:cFLAG,:iCODEMPBO,:sCODFILIALBO,
                            :cCODBANCO, :iCODEMPTC, :sCODFILIALTC, :iCODTIPOCOB,
                            :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSREC, :codempca,
                            :codfilialca, :numconta,
                            :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc);

  END
  suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.3 (03/08/2010)';
    suspend;
end
^

/* Create Trigger... */
CREATE TRIGGER FNCHEQUETGBU FOR FNCHEQUE
ACTIVE BEFORE UPDATE POSITION 0 
AS
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER FNTALAOCHEQTGBU FOR FNTALAOCHEQ
ACTIVE BEFORE UPDATE POSITION 0 
AS
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER RHTABELAINSSTGBU FOR RHTABELAINSS
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.dtalt=cast('now' as date);
  new.idusualt=user;
  new.halt = cast('now' as time);
end
^

CREATE TRIGGER RHTABELAIRRFTGBU FOR RHTABELAIRRF
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.dtalt=cast('now' as date);
  new.idusualt=user;
  new.halt = cast('now' as time);
end
^


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
               :DTBASE, new.DOCCOMPRA,new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,
               new.FLAG,new.CODEMP,new.CODFILIAL, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE ,new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
               new.CODEMPFR, new.CODFILIALFR, new.CODFOR, :dVLR, :DTBASE, new.DOCCOMPRA,
               new.CODEMPBO, new.CODFILIALBO, new.CODBANCO, new.FLAG, new.CODEMP, new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB );
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

      IF ( ((new.VLRFRETECOMPRA > 0) AND ( NOT new.VLRFRETECOMPRA = old.VLRFRETECOMPRA)) or ((new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra))  ) THEN
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
ALTER TRIGGER CPITCOMPRATGBI
as

declare variable srefprod char(13);
declare variable habCustoCompra char(1);
declare variable calctrib char(1);

begin

    -- Buscando preferências
    select p.custocompra from sgprefere1 p
    where p.codemp=new.codemp and p.codfilial=new.codfilial
    into habCustoCompra;

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

    -- Buscando e carregando custo do produto
    if (('N' = habCustoCompra) or (new.custoitcompra is null)) then
    begin
        select nvlrcusto from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra)
        into new.custoitcompra;
    end

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

    --Atualizando totais da compra
    update cpcompra cp set cp.vlrdescitcompra=cp.vlrdescitcompra + new.vlrdescitcompra,
    cp.vlrprodcompra = cp.vlrprodcompra + new.vlrproditcompra,
    cp.vlrbaseicmscompra = cp.vlrbaseicmscompra + new.vlrbaseicmsitcompra,
    cp.vlricmscompra = cp.vlricmscompra + new.vlricmsitcompra,
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

declare variable srefprod char(13);
declare variable sadicfrete char(1);
declare variable sadicadic char(1);
declare variable habcustocompra char(1);
declare variable vlritcusto numeric(15, 5);
declare variable statuscompra char(2);
declare variable calctrib char(1);

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin
        
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
            select p.custocompra from sgprefere1 p
            where p.codemp=new.codemp and p.codfilial=new.codfilial
            into :habcustocompra;

            --Buscando informações da compra
            select cp.calctrib from cpcompra cp
            where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
            into :calctrib;

            if (('N' = habcustocompra) or (new.custoitcompra is null)) then
            begin
                select nvlrcusto
                from cpcomprasp01 (new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra)
                into new.custoitcompra;
            end

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

        end
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

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNITPAGARTGBU
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
      new.EMMANUT = 'S';
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
ALTER TRIGGER FNPAGARTGAI
as
declare variable vlrpag numeric(15, 5);
declare variable percpag numeric(11,6);
declare variable resto numeric(15, 5);
declare variable numparc integer;
declare variable diaspag integer;
declare variable i integer;
declare variable filialpp integer;
declare variable regrvcto char(1);
declare variable rvsab char(1);
declare variable rvdom char(1);
declare variable rvfer char(1);
declare variable rvdia char(1);
declare variable diavcto smallint;
declare variable casasdecfin smallint;
declare variable dtvencto date;

begin

    -- Buscando filial do plano de pagamento
    select icodfilial from sgretfilial(new.codemp, 'FNPLANOPAG') into :filialpp;

    -- Buscando preferencias

    select casasdecfin from sgprefere1 where codemp=new.codemp and codfilial=new.codfilial
    into casasdecfin;

    -- Carregando valor restante de pagamento
    i = 0;
    resto = new.vlrpag;

    -- Buscando informações do plano de pagamento
    select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag,
           pp.rvferplanopag, pp.rvdiaplanopag, pp.diavctoplanopag
    from fnplanopag pp
    where pp.codplanopag=new.codplanopag and pp.codemp=new.codemp and pp.codfilial=:filialpp
    into :numparc, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

    -- Gerando parcelas

    for select pp.percpag, pp.diaspag
        from fnparcpag pp
        where pp.codplanopag=new.codplanopag and pp.codemp=new.codemp and pp.codfilial=:filialpp
        order by pp.diaspag
        into :percpag, :diaspag
    do
    begin
        i = i + 1;
        
        -- Calculando valor da parcela
        vlrpag = cast(cast(new.vlrpag * percpag as numeric(15, 5)) / 100 as numeric(15, 5));

        -- Usando o arredondamento para evitar dízima
        select v.deretorno
        from arreddouble(:vlrpag,:casasdecfin) v
        into :vlrpag;

        -- Valor restante para proximas parcelas
        resto = resto - vlrpag;

        if (i = numparc) then
        begin
            vlrpag = vlrpag + resto;
        end
        
        -- Buscando data de vencimento
        select c.dtvencret
        from sgcalcvencsp( new.codemp , new.datapag, new.datapag + :diaspag, :regrvcto,
                           :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :diaspag ) c
        into :dtvencto;

        -- Inserindo parcela

        insert into fnitpagar (CODEMP,CODFILIAL,CODPAG,NPARCPAG,VLRITPAG,VLRDESCITPAG,VLRMULTAITPAG,
                           VLRJUROSITPAG,VLRPARCITPAG,VLRPAGOITPAG,VLRAPAGITPAG,
                           DTITPAG,DTVENCITPAG,STATUSITPAG,VLRADICITPAG,FLAG,OBSITPAG,
                           CODEMPTC,CODFILIALTC,CODTIPOCOB,CODEMPCA,CODFILIALCA,NUMCONTA,
                           CODEMPPN, CODFILIALPN, CODPLAN, CODEMPCC, CODFILIALCC, ANOCC, CODCC
                           ) VALUES
                           (new.CODEMP,new.CODFILIAL,new.CODPAG,:i,:vlrpag,0,0,0,:vlrpag,0,:vlrpag,
                            new.datapag,:dtvencto,'P1',new.vlradicpag,new.flag,new.obspag,
                            new.CODEMPTC,new.CODFILIALTC,new.CODTIPOCOB, new.codempca, new.codfilialca, new.numconta,
                            new.codemppn, new.codfilialpn, new.codplan, new.codempcc, new.codfilialcc, new.anocc, new.codcc
                            );
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNPAGARTGBI
AS
BEGIN
  IF (new.VLRDESCPAG IS NULL) THEN new.VLRDESCPAG = 0;
  IF (new.VLRJUROSPAG IS NULL) THEN new.VLRJUROSPAG = 0;
  IF (new.VLRMULTAPAG IS NULL) THEN new.VLRMULTAPAG = 0;
  IF (new.VLRPARCPAG IS NULL) THEN new.VLRPARCPAG = 0;
  IF (new.VLRPAGOPAG IS NULL) THEN new.VLRPAGOPAG = 0;
  IF (new.STATUSPAG IS NULL) THEN new.STATUSPAG = 'P1';
  new.VLRPAG = new.VLRPARCPAG - new.VLRDESCPAG - new.VLRDEVPAG + new.VLRJUROSPAG + new.VLRADICPAG +
  new.VLRMULTAPAG;
  new.VLRAPAGPAG = new.VLRPAG - new.VLRPAGOPAG;
  if ( ( new.NUMCONTA IS NULL ) AND ( new.CODTIPOCOB IS NOT NULL ) ) then
  begin
     SELECT TC.CODEMPCT, TC.CODFILIALCT, TC.NUMCONTA FROM FNTIPOCOB TC
        WHERE TC.CODEMP=new.CODEMPTC AND TC.CODFILIAL=new.CODFILIALTC AND TC.CODTIPOCOB=new.CODTIPOCOB
        INTO new.CODEMPCA, new.CODFILIALCA, new.NUMCONTA;
  end
END
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
    new.codemppn, new.codfilialpn, new.codplan, new.codempcc,  new.codfilialcc, new.anocc, new.codcc
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
AS
    declare variable INPARCOLD INTEGER;
    declare variable INPARCNEW INTEGER;
    declare variable CALTVLR CHAR(1);
    declare variable REGRVCTO CHAR(1);
    declare variable RVSAB CHAR(1);
    declare variable RVDOM CHAR(1);
    declare variable RVFER CHAR(1);
    declare variable RVDIA CHAR(1);
    declare variable DIAVCTO smallint;
    declare variable DIASPAG smallint;
    declare variable DTVENCTO DATE;
    declare variable NROPARCREC smallint;

begin
    if ( ((old.CODPLANOPAG<>new.CODPLANOPAG) or (old.FLAG<>new.FLAG) or (old.VLRREC<>new.VLRREC) OR
       (old.DATAREC<>new.DATAREC)) and (new.ALTUSUREC != 'S') ) then
    begin
        if (old.CODPLANOPAG<>new.CODPLANOPAG) then -- Se foi alterado o plano de pagamento
        begin
            select PP.PARCPLANOPAG from fnplanopag pp where CODEMP=old.CODEMPPG and
                PP.CODFILIAL=old.CODFILIALPG and
                PP.CODPLANOPAG=old.CODPLANOPAG into :INPARCOLD;

            select PP.PARCPLANOPAG, REGRVCTOPLANOPAG, RVSABPLANOPAG, RVDOMPLANOPAG,
                RVFERPLANOPAG, RVDIAPLANOPAG, DIAVCTOPLANOPAG
            from fnplanopag PP where CODEMP=new.CODEMPPG and PP.CODFILIAL=new.CODFILIALPG AND PP.CODPLANOPAG=new.CODPLANOPAG
            into :INPARCNEW,:REGRVCTO, :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO;

            if (INPARCNEW != INPARCOLD) then -- Se o numero de parcelas entre os planos de pagamento são diferentes.
            begin
                delete from fnitreceber where codrec=new.codrec and codemp=new.codemp and codfilial=new.codfilial; -- exclui os itens de contas a receber, para recria-los.
            end
            else if (inparcnew=1) then -- Caso possua apenas uma parcela deve apenas atualizar a data do vencimento.
            begin
                select diaspag from fnparcpag where codplanopag=new.codplanopag and codemp=new.codemppg
                    and codfilial=new.codfilialpg into :diaspag;

                select C.DTVENCRET from sgcalcvencsp(NEW.codemppg, new.DATAREC, new.DATAREC + :DIASPAG, :REGRVCTO, :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO, :DIASPAG ) C INTO :DTVENCTO;

                update fnitreceber set dtvencitrec=:DTVENCTO
                    where CODEMP=new.codemp and codfilial=new.codfilial and codrec=new.codrec and STATUSITREC not in ('RP','RL');

            end
    end
    if (new.VLRREC!=old.VLRREC) THEN
       CALTVLR = 'S';
    else
       CALTVLR = 'N';
    select I from fngeraitrecebersp01(:CALTVLR,  new.CODEMP,
       new.CODFILIAL, new.CODREC, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
       new.VLRREC, new.DATAREC, new.FLAG, new.CODEMPBO, new.CODFILIALBO,
       new.CODBANCO, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
       new.CODEMPCB, new.CODFILIALCB, new.CODCARTCOB,
       new.VLRCOMIREC, new.OBSREC, null, null, null,
       new.codemppn, new.codfilialpn,new.codplan, new.codempcc, new.codfilialcc, new.anocc, new.codcc)
       into :NROPARCREC;
    end
/* Atualiza os itens para eles atualizarem a comissao de forma distribuida. */
    if (old.VLRCOMIREC != new.VLRCOMIREC) then
        execute procedure FNITRECEBERSP01(new.CODEMP, new.CODFILIAL, new.CODREC,
            new.VLRPARCREC,new.VLRCOMIREC,new.NROPARCREC,'N');
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNSUBLANCATGAI
AS
DECLARE VARIABLE sCodplan CHAR(13);
DECLARE VARIABLE dSaldoAnt NUMERIC(15, 5);
DECLARE VARIABLE IFILIALSALDO INTEGER;
declare variable codfilialpn smallint;
BEGIN

  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNSALDOLANCA') INTO IFILIALSALDO;
  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPLANEJAMENTO') INTO :codfilialpn;

  SELECT CODPLAN FROM FNSALDOLANCA
  WHERE CODPLAN=new.CODPLAN AND DATASL=new.DATASUBLANCA AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALSALDO
  INTO :sCodplan;


   IF (:sCodplan IS NULL ) THEN
   BEGIN

      SELECT SALDOSL FROM FNSALDOLANCA SL1 WHERE SL1.CODPLAN=new.CODPLAN and sl1.codemppn=new.codemp and sl1.codfilialpn=:codfilialpn
             AND SL1.DATASL=(
                            SELECT MAX(SL2.DATASL) FROM FNSALDOLANCA SL2 WHERE
                                   SL2.CODPLAN=SL1.CODPLAN AND SL2.DATASL<new.DATASUBLANCA
                                   AND SL2.CODEMP=new.CODEMP AND SL2.CODFILIAL=:IFILIALSALDO
                            )
              AND SL1.CODEMP=new.CODEMP AND SL1.CODFILIAL = :IFILIALSALDO INTO :dSaldoAnt;



      IF (:dSaldoAnt IS NULL) THEN
         dSaldoAnt = 0;

      INSERT INTO FNSALDOLANCA (CODEMP,CODFILIAL,CODPLAN,CODEMPPN,CODFILIALPN,DATASL,SALDOSL)
             VALUES (new.CODEMPPN,:IFILIALSALDO,new.CODPLAN,new.CODEMPPN,new.CODFILIALPN,new.DATASUBLANCA,:dSaldoAnt+new.VLRSUBLANCA);

      UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
             WHERE CODPLAN=new.CODPLAN AND DATASL>new.DATASUBLANCA
             AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;



   END
   ELSE
   BEGIN

      UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
             WHERE CODPLAN=new.CODPLAN AND DATASL>=new.DATASUBLANCA
             AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;

   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNSUBLANCATGAU
AS
DECLARE VARIABLE dDataSl DATE;
DECLARE VARIABLE dSaldoSl NUMERIC(15, 5);
DECLARE VARIABLE sCodplan CHAR(13);
DECLARE VARIABLE dSaldoAnt NUMERIC(15, 5);
DECLARE VARIABLE IFILIALSALDO INTEGER;
declare variable codfilialpn smallint;
BEGIN

    SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNSALDOLANCA') INTO IFILIALSALDO;
    SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPLANEJAMENTO') INTO :codfilialpn;

   IF ( (old.DATASUBLANCA <> new.DATASUBLANCA) OR (old.VLRSUBLANCA<>new.VLRSUBLANCA) ) THEN
   BEGIN
/*Verifica se já existe saldo no dia da nova data:*/
     SELECT CODPLAN,DATASL,SALDOSL FROM FNSALDOLANCA WHERE CODPLAN=new.CODPLAN
            AND DATASL=new.DATASUBLANCA AND CODEMP=new.CODEMPPN AND CODFILIAL=:IFILIALSALDO
            INTO :sCodplan,:dDataSl,:dSaldoSl;
/*Se não existir, cria um saldo para o nova data:*/
     IF (sCodplan IS NULL ) THEN
     BEGIN
/*Pega o ultimo saldo anterior a data do lançamento:*/
        SELECT SALDOSL FROM FNSALDOLANCA SL1 WHERE SL1.CODPLAN=new.CODPLAN and sl1.codemppn=new.codemp and sl1.codfilialpn=:codfilialpn
               AND SL1.DATASL=(
                              SELECT MAX(SL2.DATASL) FROM FNSALDOLANCA SL2 WHERE
                                     SL2.CODPLAN=SL1.CODPLAN AND SL2.DATASL<new.DATASUBLANCA
                                     AND SL2.CODEMP=new.CODEMPPN AND SL2.CODFILIAL=:IFILIALSALDO
                              )
               AND SL1.CODEMP=new.CODEMPPN AND SL1.CODFILIAL=:IFILIALSALDO INTO :dSaldoAnt;
    /*Cria um saldo para este dia e atualiza saldos possíveis que tiverem na frente:*/
        IF (:dSaldoAnt IS NULL) THEN
           dSaldoAnt = 0;
        INSERT INTO FNSALDOLANCA (CODEMP,CODFILIAL,CODPLAN,CODEMPPN,CODFILIALPN,DATASL,SALDOSL)
               VALUES (new.CODEMP,:IFILIALSALDO,new.CODPLAN,new.CODEMPPN,new.CODFILIALPN,new.DATASUBLANCA,:dSaldoAnt + new.VLRSUBLANCA);
        UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
               WHERE CODPLAN=new.CODPLAN AND DATASL>new.DATASUBLANCA
               AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;
     END
    /*Caso contrário só atualiza o saldo:*/
     ELSE
     BEGIN
        UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
               WHERE CODPLAN=new.CODPLAN AND DATASL>=new.DATASUBLANCA
               AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;
     END
   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNSUBLANCATGBI
AS
  DECLARE VARIABLE sEhTransf CHAR(1);
  DECLARE VARIABLE sTipoPlan CHAR(1);
  DECLARE VARIABLE dDataLanca DATE;
  DECLARE VARIABLE dDtPrevLanca DATE;
  DECLARE VARIABLE sHistSubLanc VARCHAR(100);
BEGIN
  /*IF ( new.VLRSUBLANCA = 0 ) THEN
     EXCEPTION FNSUBLANCAEX04;         */
  SELECT HISTBLANCA FROM FNLANCA WHERE CODLANCA = new.CODLANCA AND CODEMP=new.CODEMP AND CODFILIAL = new.CODFILIAL
  INTO sHistSubLanc;

  IF (new.HISTSUBLANCA IS NULL) THEN
      new.HISTSUBLANCA = sHistSubLanc;

  IF (new.CODSUBLANCA IS NULL) THEN
    SELECT MAX(CODSUBLANCA) FROM FNSUBLANCA
    WHERE CODLANCA = new.CODLANCA AND CODEMP=new.CODEMP AND CODFILIAL = new.CODFILIAL
    INTO new.CODSUBLANCA;

  new.STATUSSUBLANCA = 'S1';

  SELECT TRANSFLANCA,DATALANCA,DTPREVLANCA FROM FNLANCA
  WHERE CODLANCA = new.CODLANCA AND CODEMP=new.CODEMP AND CODFILIAL = new.CODFILIAL
  INTO sEhTransf,dDataLanca,dDtPrevLanca;

  SELECT TIPOPLAN FROM FNPLANEJAMENTO WHERE CODPLAN = new.CODPLAN AND CODEMP=new.CODEMPPN AND CODFILIAL = new.CODFILIALPN
  INTO sTipoPlan;

  new.DATASUBLANCA = dDataLanca;

  IF (new.CODSUBLANCA > 0) THEN
  BEGIN
    new.ORIGSUBLANCA = 'N';
  END

  new.STATUSSUBLANCA = 'S1';
  new.DTPREVSUBLANCA = dDtPrevLanca;

  IF (sEhTransf = 'S') THEN
  BEGIN
   IF (new.CODSUBLANCA = 2) THEN
     EXCEPTION FNSUBLANCAEX01;
   ELSE IF (sTipoPlan IN ('D','R')) THEN
     EXCEPTION FNSUBLANCAEX02;
  END
  ELSE
    IF ((new.CODSUBLANCA != 0) AND (sTipoPlan IN ('C','B'))) THEN
      EXCEPTION FNSUBLANCAEX03;
  UPDATE FNLANCA SET STATUSLANCA = 'L2', VLRLANCA=(VLRLANCA-new.VLRSUBLANCA)
         WHERE CODLANCA = new.CODLANCA AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
END
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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.flag, new.obsrec);
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
CODCC CHAR(19))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* Alter (CPADICCOMPRAPEDSP) */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable codempse integer;
declare variable codfilialse smallint;
declare variable serie char(4);
declare variable statuscompra char(2);
begin

    --Buscando a série da compra
    select tm.codempse, tm.codfilialse, tm.serie
    from eqtipomov tm
    where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
    into :codempse, :codfilialse, :serie;

    --Definição do status da compra
    statuscompra = 'P1';

    --Buscando doccompra
    select doc from lfnovodocsp(:serie, :codempse , :codfilialse) into doccompra;


    insert into cpcompra (
    codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor,
    codempse, codfilialse, serie, codemptm, codfilialtm, codtipomov, doccompra, dtentcompra, dtemitcompra, statuscompra, calctrib )
    values (
    :codemp, :codfilial, :codcompra, :codemppg, :codfilialpg, :codplanopag, :codempfr, :codfilialfr, :codfor,
    :codempse, :codfilialse, :serie, :codemptm, :codfilialtm, :codtipomov, :doccompra,
    cast('today' as date), cast('today' as date), :statuscompra, 'S' );

    iret = :codcompra;

    suspend;

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

/* Alter (EQGERARMAOSSP) */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod char(13);
declare variable codrma integer;
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
--declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
--declare variable codfase integer;
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
                     codempos,codfilialos,ticket,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket and os.coditrecmerc=:coditrecmerc
    and os.gerarma='S'
    into :coditos, :codemppd, :codfilialpd, :codprod, :refprod, :qtd, :custompmit
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
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE'
                            );

        update eqitrecmercitos os set os.gerarma='N'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

   end
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
CODCC CHAR(19))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
       INSERT INTO FNITRECEBER (CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc );
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
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta
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
  suspend;
end
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
DDTPAGOITPAG DATE,
SDOCLANCAITPAG CHAR(10),
SOBSITPAG CHAR(100),
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
                         NPARCPAG,CODEMPPN,CODFILIALPN,CODPLAN,DATALANCA,DOCLANCA,
                         HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG, CODEMPFR, CODFILIALFR, CODFOR)
         VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:iCodPag,
                 :iNParcPag,:iCodEmpPConta,:iCodFilialPConta,:sCodPlanConta,:dDtPagoItPag,:sDocLancaItPag,
                 :sObsItPag,:dDtPagoItPag,0,:cFlag, :ICODEMPFR, :ICODFILIALFR, :ICODFOR
         );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:ICODEMPPN,:ICODFILIALPN,:sCodplan,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:dVlrPagoItPag,:cFlag);

    -- Lançamento dos juros em conta distinta.

    IF(DVLRJUROSPAG >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:CODEMPJP,:CODFILIALJP,:CODPLANJP,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:DVLRJUROSPAG,:cFlag);

    END

    -- Lançamento de desconto em conta distinta.

    IF(DVLRDESC >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:CODEMPDR,:CODFILIALDR,:CODPLANDR,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtPagoItPag,:dDtPagoItPag,:DVLRDESC*-1,:cFlag);

    END

END
^

/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER)
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG,STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra,'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB
                          );
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
CODCC CHAR(19))
 AS
declare variable inparcitrecold integer;
begin
   SELECT IR.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
       INSERT INTO FNITRECEBER (CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc );
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
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta
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
  suspend;
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
CODCC CHAR(19))
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
begin
  /* Procedure Text */
  i = 0;
  nPerc = 100;
  nResto = nVLRREC;
  nRestoComi = nVLRCOMIREC;
  DTBASE = DDATAREC;

  -- Buscando preferencias

  select casasdecfin from sgprefere1 where codemp=:icodemp and codfilial=:scodfilial
  into casasdecfin;

  SELECT PARCPLANOPAG, REGRVCTOPLANOPAG, RVSABPLANOPAG, RVDOMPLANOPAG,
     RVFERPLANOPAG, RVDIAPLANOPAG, DIAVCTOPLANOPAG
    FROM FNPLANOPAG WHERE CODPLANOPAG=:iCODPLANOPAG
         AND CODEMP=:iCODEMPPG AND CODFILIAL = :sCODFILIALPG
         INTO :iNumParc, :REGRVCTO, :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO;
  FOR SELECT PERCPAG,DIASPAG FROM FNPARCPAG WHERE CODPLANOPAG=:iCODPLANOPAG
             AND CODEMP=:iCODEMPPG AND CODFILIAL = :sCODFILIALPG ORDER BY DIASPAG
             INTO :nPercPag,:iDiasPag DO
  BEGIN
    i = i+1;
    select v.deretorno
        from arreddouble(cast(:nVLRREC*:nPercPag as NUMERIC(15, 5))/:nPerc,:casasdecfin ) v
       into :nVlrItRec;
    nResto = nResto-nVlrItRec;
    IF (i = iNumParc) THEN
    BEGIN
      nVlrItRec=nVlrItRec+nResto;
    END
    select v.deretorno from
       arreddouble(cast(:nVlrComiRec*:nPercPag as NUMERIC(15, 5))/:nPerc,:casasdecfin) v
       into :nVlrComiItRec;
    nRestoComi = nRestoComi-nVlrComiItRec;
    if (i = iNumParc) then
    begin
      nVlrComiItRec = nVlrComiItRec+nRestoComi;
    end

    SELECT C.DTVENCRET FROM SGCALCVENCSP(:ICODEMP, :DTBASE, :dDATAREC+:iDiasPag, :REGRVCTO,
       :RVSAB, :RVDOM, :RVFER, :RVDIA, :DIAVCTO, :IDIASPAG ) C INTO :DTVENCTO;

    DTBASE = DTVENCTO;

    EXECUTE PROCEDURE fnadicitrecebersp01 (:cALTVLR, :iCODEMP,:sCODFILIAL,:iCODREC,:i,0,0,0,:nVlrItRec,0,
                            :dDATAREC,:DTVENCTO,'R1',:cFLAG,:iCODEMPBO,:sCODFILIALBO,
                            :cCODBANCO, :iCODEMPTC, :sCODFILIALTC, :iCODTIPOCOB,
                            :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSREC, :codempca,
                            :codfilialca, :numconta,
                            :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc);

  END
  suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.3 (03/08/2010)';
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

ALTER TABLE CPCOMPRA ALTER COLUMN PERCDESCCOMPRA POSITION 19;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCIPICOMPRA POSITION 20;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPRODCOMPRA POSITION 21;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRLIQCOMPRA POSITION 22;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOMPRA POSITION 23;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCCOMPRA POSITION 24;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 25;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRADICCOMPRA POSITION 26;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSCOMPRA POSITION 27;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSSTCOMPRA POSITION 28;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIPICOMPRA POSITION 29;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEPISCOMPRA POSITION 30;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASECOFINSCOMPRA POSITION 31;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCOMPRA POSITION 32;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSSTCOMPRA POSITION 33;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIPICOMPRA POSITION 34;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPISCOMPRA POSITION 35;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOFINSCOMPRA POSITION 36;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFUNRURALCOMPRA POSITION 37;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFRETECOMPRA POSITION 38;

ALTER TABLE CPCOMPRA ALTER COLUMN VLROUTRASCOMPRA POSITION 39;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRISENTASCOMPRA POSITION 40;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTC POSITION 41;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTC POSITION 42;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOCOB POSITION 43;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPBO POSITION 44;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALBO POSITION 45;

ALTER TABLE CPCOMPRA ALTER COLUMN CODBANCO POSITION 46;

ALTER TABLE CPCOMPRA ALTER COLUMN IMPNOTACOMPRA POSITION 47;

ALTER TABLE CPCOMPRA ALTER COLUMN BLOQCOMPRA POSITION 48;

ALTER TABLE CPCOMPRA ALTER COLUMN EMMANUT POSITION 49;

ALTER TABLE CPCOMPRA ALTER COLUMN FLAG POSITION 50;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETECOMPRA POSITION 51;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPOFRETECOMPRA POSITION 52;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSOL POSITION 53;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSOL POSITION 54;

ALTER TABLE CPCOMPRA ALTER COLUMN CODSOL POSITION 55;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTN POSITION 56;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTN POSITION 57;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTRAN POSITION 58;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSERVACAO POSITION 59;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS01 POSITION 60;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS02 POSITION 61;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS03 POSITION 62;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS04 POSITION 63;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICCOMPRA POSITION 64;

ALTER TABLE CPCOMPRA ALTER COLUMN QTDFRETECOMPRA POSITION 65;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETEBASEICMS POSITION 66;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICBASEICMS POSITION 67;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICIPIBASEICMS POSITION 68;

ALTER TABLE CPCOMPRA ALTER COLUMN CHAVENFECOMPRA POSITION 69;

ALTER TABLE CPCOMPRA ALTER COLUMN STATUSCOMPRA POSITION 70;

ALTER TABLE CPCOMPRA ALTER COLUMN NRODI POSITION 71;

ALTER TABLE CPCOMPRA ALTER COLUMN DTREGDI POSITION 72;

ALTER TABLE CPCOMPRA ALTER COLUMN LOCDESEMBDI POSITION 73;

ALTER TABLE CPCOMPRA ALTER COLUMN SIGLAUFDESEMBDI POSITION 74;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPAISDESEMBDI POSITION 75;

ALTER TABLE CPCOMPRA ALTER COLUMN DTDESEMBDI POSITION 76;

ALTER TABLE CPCOMPRA ALTER COLUMN IDENTCONTAINER POSITION 77;

ALTER TABLE CPCOMPRA ALTER COLUMN CALCTRIB POSITION 78;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPRM POSITION 79;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALRM POSITION 80;

ALTER TABLE CPCOMPRA ALTER COLUMN TICKET POSITION 81;

ALTER TABLE CPCOMPRA ALTER COLUMN DTINS POSITION 82;

ALTER TABLE CPCOMPRA ALTER COLUMN HINS POSITION 83;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUINS POSITION 84;

ALTER TABLE CPCOMPRA ALTER COLUMN DTALT POSITION 85;

ALTER TABLE CPCOMPRA ALTER COLUMN HALT POSITION 86;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUALT POSITION 87;

ALTER TABLE CPCOTACAO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPCOTACAO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPCOTACAO ALTER COLUMN CODSOL POSITION 3;

ALTER TABLE CPCOTACAO ALTER COLUMN CODITSOL POSITION 4;

ALTER TABLE CPCOTACAO ALTER COLUMN CODCOT POSITION 5;

ALTER TABLE CPCOTACAO ALTER COLUMN DTCOT POSITION 6;

ALTER TABLE CPCOTACAO ALTER COLUMN DTVALIDCOT POSITION 7;

ALTER TABLE CPCOTACAO ALTER COLUMN IDUSUCOT POSITION 8;

ALTER TABLE CPCOTACAO ALTER COLUMN CODEMPFR POSITION 9;

ALTER TABLE CPCOTACAO ALTER COLUMN CODFILIALFR POSITION 10;

ALTER TABLE CPCOTACAO ALTER COLUMN CODFOR POSITION 11;

ALTER TABLE CPCOTACAO ALTER COLUMN QTDCOT POSITION 12;

ALTER TABLE CPCOTACAO ALTER COLUMN QTDAPROVCOT POSITION 13;

ALTER TABLE CPCOTACAO ALTER COLUMN PRECOCOT POSITION 14;

ALTER TABLE CPCOTACAO ALTER COLUMN SITCOMPITSOL POSITION 15;

ALTER TABLE CPCOTACAO ALTER COLUMN SITAPROVITSOL POSITION 16;

ALTER TABLE CPCOTACAO ALTER COLUMN SITITSOL POSITION 17;

ALTER TABLE CPCOTACAO ALTER COLUMN MOTIVOCANCCOT POSITION 18;

ALTER TABLE CPCOTACAO ALTER COLUMN MOTIVOCOTABAIXO POSITION 19;

ALTER TABLE CPCOTACAO ALTER COLUMN USARENDACOT POSITION 20;

ALTER TABLE CPCOTACAO ALTER COLUMN RENDACOT POSITION 21;

ALTER TABLE CPCOTACAO ALTER COLUMN IDUSUALT POSITION 22;

ALTER TABLE CPCOTACAO ALTER COLUMN VLRFRETEITCOMPRA POSITION 23;

ALTER TABLE CPCOTACAO ALTER COLUMN PERCIPIITCOMPRA POSITION 24;

ALTER TABLE CPCOTACAO ALTER COLUMN VLRBASEIPIITCOMPRA POSITION 25;

ALTER TABLE CPCOTACAO ALTER COLUMN VLRIPIITCOMPRA POSITION 26;

ALTER TABLE CPCOTACAO ALTER COLUMN VLRLIQITCOMPRA POSITION 27;

ALTER TABLE CPCOTACAO ALTER COLUMN CODEMPPP POSITION 28;

ALTER TABLE CPCOTACAO ALTER COLUMN CODFILIALPP POSITION 29;

ALTER TABLE CPCOTACAO ALTER COLUMN CODPLANOPAG POSITION 30;

ALTER TABLE CPCOTACAO ALTER COLUMN DTINS POSITION 31;

ALTER TABLE CPCOTACAO ALTER COLUMN HINS POSITION 32;

ALTER TABLE CPCOTACAO ALTER COLUMN IDUSUINS POSITION 33;

ALTER TABLE CPCOTACAO ALTER COLUMN DTALT POSITION 34;

ALTER TABLE CPCOTACAO ALTER COLUMN HALT POSITION 35;

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

ALTER TABLE CPFORNECED ALTER COLUMN DTINS POSITION 57;

ALTER TABLE CPFORNECED ALTER COLUMN HINS POSITION 58;

ALTER TABLE CPFORNECED ALTER COLUMN IDUSUINS POSITION 59;

ALTER TABLE CPFORNECED ALTER COLUMN DTALT POSITION 60;

ALTER TABLE CPFORNECED ALTER COLUMN HALT POSITION 61;

ALTER TABLE CPFORNECED ALTER COLUMN IDUSUALT POSITION 62;

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

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCIPIITCOMPRA POSITION 25;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEIPIITCOMPRA POSITION 26;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRIPIITCOMPRA POSITION 27;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEFUNRURALITCOMPRA POSITION 28;

ALTER TABLE CPITCOMPRA ALTER COLUMN ALIQFUNRURALITCOMPRA POSITION 29;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFUNRURALITCOMPRA POSITION 30;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRLIQITCOMPRA POSITION 31;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRADICITCOMPRA POSITION 32;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFRETEITCOMPRA POSITION 33;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRISENTASITCOMPRA POSITION 34;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLROUTRASITCOMPRA POSITION 35;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRPRODITCOMPRA POSITION 36;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOITCOMPRA POSITION 37;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOVDITCOMPRA POSITION 38;

ALTER TABLE CPITCOMPRA ALTER COLUMN REFPROD POSITION 39;

ALTER TABLE CPITCOMPRA ALTER COLUMN OBSITCOMPRA POSITION 40;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPIF POSITION 41;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALIF POSITION 42;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFISC POSITION 43;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMMANUT POSITION 44;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODITFISC POSITION 45;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPNS POSITION 46;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALNS POSITION 47;

ALTER TABLE CPITCOMPRA ALTER COLUMN NUMSERIETMP POSITION 48;

ALTER TABLE CPITCOMPRA ALTER COLUMN NADICAO POSITION 49;

ALTER TABLE CPITCOMPRA ALTER COLUMN SEQADIC POSITION 50;

ALTER TABLE CPITCOMPRA ALTER COLUMN DESCDI POSITION 51;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTINS POSITION 52;

ALTER TABLE CPITCOMPRA ALTER COLUMN HINS POSITION 53;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUINS POSITION 54;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTALT POSITION 55;

ALTER TABLE CPITCOMPRA ALTER COLUMN HALT POSITION 56;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUALT POSITION 57;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMITITCOMPRA POSITION 58;

ALTER TABLE CPITCOMPRA ALTER COLUMN APROVPRECO POSITION 59;

ALTER TABLE CPTIPOFOR ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPTIPOFOR ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPTIPOFOR ALTER COLUMN CODTIPOFOR POSITION 3;

ALTER TABLE CPTIPOFOR ALTER COLUMN DESCTIPOFOR POSITION 4;

ALTER TABLE CPTIPOFOR ALTER COLUMN RETENCAOINSS POSITION 5;

ALTER TABLE CPTIPOFOR ALTER COLUMN PERCBASEINSS POSITION 6;

ALTER TABLE CPTIPOFOR ALTER COLUMN RETENCAOOUTROS POSITION 7;

ALTER TABLE CPTIPOFOR ALTER COLUMN PERCRETOUTROS POSITION 8;

ALTER TABLE CPTIPOFOR ALTER COLUMN RETENCAOIRRF POSITION 9;

ALTER TABLE CPTIPOFOR ALTER COLUMN PERCBASEIRRF POSITION 10;

ALTER TABLE CPTIPOFOR ALTER COLUMN DTINS POSITION 11;

ALTER TABLE CPTIPOFOR ALTER COLUMN HINS POSITION 12;

ALTER TABLE CPTIPOFOR ALTER COLUMN IDUSUINS POSITION 13;

ALTER TABLE CPTIPOFOR ALTER COLUMN DTALT POSITION 14;

ALTER TABLE CPTIPOFOR ALTER COLUMN HALT POSITION 15;

ALTER TABLE CPTIPOFOR ALTER COLUMN IDUSUALT POSITION 16;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN TICKET POSITION 3;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODITRECMERC POSITION 4;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODITOS POSITION 5;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMPPD POSITION 6;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIALPD POSITION 7;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODPRODPD POSITION 8;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN REFPRODPD POSITION 9;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMPNS POSITION 10;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIALNS POSITION 11;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN NUMSERIE POSITION 12;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN QTDITOS POSITION 13;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN OBSITOS POSITION 14;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN STATUSITOS POSITION 15;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN GERARMA POSITION 16;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN DTINS POSITION 17;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN HINS POSITION 18;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN IDUSUINS POSITION 19;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN DTALT POSITION 20;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN HALT POSITION 21;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN IDUSUALT POSITION 22;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQRECMERC ALTER COLUMN TICKET POSITION 3;

ALTER TABLE EQRECMERC ALTER COLUMN PLACAVEICULO POSITION 4;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPPD POSITION 5;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALPD POSITION 6;

ALTER TABLE EQRECMERC ALTER COLUMN CODPROD POSITION 7;

ALTER TABLE EQRECMERC ALTER COLUMN REFPROD POSITION 8;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPFR POSITION 9;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALFR POSITION 10;

ALTER TABLE EQRECMERC ALTER COLUMN CODFOR POSITION 11;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPTR POSITION 12;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALTR POSITION 13;

ALTER TABLE EQRECMERC ALTER COLUMN CODTIPORECMERC POSITION 14;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPTN POSITION 15;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALTN POSITION 16;

ALTER TABLE EQRECMERC ALTER COLUMN CODTRAN POSITION 17;

ALTER TABLE EQRECMERC ALTER COLUMN IDUSUALT POSITION 18;

ALTER TABLE EQRECMERC ALTER COLUMN CODBAIRRO POSITION 19;

ALTER TABLE EQRECMERC ALTER COLUMN CODMUNIC POSITION 20;

ALTER TABLE EQRECMERC ALTER COLUMN SIGLAUF POSITION 21;

ALTER TABLE EQRECMERC ALTER COLUMN CODPAIS POSITION 22;

ALTER TABLE EQRECMERC ALTER COLUMN STATUS POSITION 23;

ALTER TABLE EQRECMERC ALTER COLUMN TIPOFRETE POSITION 24;

ALTER TABLE EQRECMERC ALTER COLUMN DTENT POSITION 25;

ALTER TABLE EQRECMERC ALTER COLUMN DTPREVRET POSITION 26;

ALTER TABLE EQRECMERC ALTER COLUMN DOCRECMERC POSITION 27;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPVD POSITION 28;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALVD POSITION 29;

ALTER TABLE EQRECMERC ALTER COLUMN CODVEND POSITION 30;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPCL POSITION 31;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALCL POSITION 32;

ALTER TABLE EQRECMERC ALTER COLUMN CODCLI POSITION 33;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPAR POSITION 34;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALAR POSITION 35;

ALTER TABLE EQRECMERC ALTER COLUMN CODATENDREC POSITION 36;

ALTER TABLE EQRECMERC ALTER COLUMN SOLICITANTE POSITION 37;

ALTER TABLE EQRECMERC ALTER COLUMN MEDIAAMOSTRAGEM POSITION 38;

ALTER TABLE EQRECMERC ALTER COLUMN RENDAAMOSTRAGEM POSITION 39;

ALTER TABLE EQRECMERC ALTER COLUMN CODEMPAX POSITION 40;

ALTER TABLE EQRECMERC ALTER COLUMN CODFILIALAX POSITION 41;

ALTER TABLE EQRECMERC ALTER COLUMN CODALMOX POSITION 42;

ALTER TABLE EQRECMERC ALTER COLUMN DTINS POSITION 43;

ALTER TABLE EQRECMERC ALTER COLUMN HINS POSITION 44;

ALTER TABLE EQRECMERC ALTER COLUMN IDUSUINS POSITION 45;

ALTER TABLE EQRECMERC ALTER COLUMN DTALT POSITION 46;

ALTER TABLE EQRECMERC ALTER COLUMN HALT POSITION 47;

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

ALTER TABLE EQRMA ALTER COLUMN DTINS POSITION 40;

ALTER TABLE EQRMA ALTER COLUMN HINS POSITION 41;

ALTER TABLE EQRMA ALTER COLUMN IDUSUINS POSITION 42;

ALTER TABLE EQRMA ALTER COLUMN DTALT POSITION 43;

ALTER TABLE EQRMA ALTER COLUMN HALT POSITION 44;

ALTER TABLE EQRMA ALTER COLUMN IDUSUALT POSITION 45;

ALTER TABLE FNBANCO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNBANCO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNBANCO ALTER COLUMN CODBANCO POSITION 3;

ALTER TABLE FNBANCO ALTER COLUMN NOMEBANCO POSITION 4;

ALTER TABLE FNBANCO ALTER COLUMN DVBANCO POSITION 5;

ALTER TABLE FNBANCO ALTER COLUMN SITEBANCO POSITION 6;

ALTER TABLE FNBANCO ALTER COLUMN CODEMPMB POSITION 7;

ALTER TABLE FNBANCO ALTER COLUMN CODFILIALMB POSITION 8;

ALTER TABLE FNBANCO ALTER COLUMN CODMODBOL POSITION 9;

ALTER TABLE FNBANCO ALTER COLUMN IMGBOLBANCO POSITION 10;

ALTER TABLE FNBANCO ALTER COLUMN IMGBOLBANCO2 POSITION 11;

ALTER TABLE FNBANCO ALTER COLUMN LAYOUTCHEQBANCO POSITION 12;

ALTER TABLE FNBANCO ALTER COLUMN DTINS POSITION 13;

ALTER TABLE FNBANCO ALTER COLUMN HINS POSITION 14;

ALTER TABLE FNBANCO ALTER COLUMN IDUSUINS POSITION 15;

ALTER TABLE FNBANCO ALTER COLUMN DTALT POSITION 16;

ALTER TABLE FNBANCO ALTER COLUMN HALT POSITION 17;

ALTER TABLE FNBANCO ALTER COLUMN IDUSUALT POSITION 18;

ALTER TABLE FNCARTCOB ALTER COLUMN CODEMPBO POSITION 1;

ALTER TABLE FNCARTCOB ALTER COLUMN CODFILIALBO POSITION 2;

ALTER TABLE FNCARTCOB ALTER COLUMN CODBANCO POSITION 3;

ALTER TABLE FNCARTCOB ALTER COLUMN CODEMP POSITION 4;

ALTER TABLE FNCARTCOB ALTER COLUMN CODFILIAL POSITION 5;

ALTER TABLE FNCARTCOB ALTER COLUMN CODCARTCOB POSITION 6;

ALTER TABLE FNCARTCOB ALTER COLUMN VARIACAOCARTCOB POSITION 7;

ALTER TABLE FNCARTCOB ALTER COLUMN DESCCARTCOB POSITION 8;

ALTER TABLE FNCARTCOB ALTER COLUMN CARTCOBCNAB POSITION 9;

ALTER TABLE FNCARTCOB ALTER COLUMN DTINS POSITION 10;

ALTER TABLE FNCARTCOB ALTER COLUMN HINS POSITION 11;

ALTER TABLE FNCARTCOB ALTER COLUMN IDUSUINS POSITION 12;

ALTER TABLE FNCARTCOB ALTER COLUMN DTALT POSITION 13;

ALTER TABLE FNCARTCOB ALTER COLUMN HALT POSITION 14;

ALTER TABLE FNCARTCOB ALTER COLUMN IDUSUALT POSITION 15;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNPAGAR ALTER COLUMN CODPAG POSITION 3;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPCP POSITION 4;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALCP POSITION 5;

ALTER TABLE FNPAGAR ALTER COLUMN CODCOMPRA POSITION 6;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPPG POSITION 7;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALPG POSITION 8;

ALTER TABLE FNPAGAR ALTER COLUMN CODPLANOPAG POSITION 9;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPFR POSITION 10;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALFR POSITION 11;

ALTER TABLE FNPAGAR ALTER COLUMN CODFOR POSITION 12;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPBO POSITION 13;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALBO POSITION 14;

ALTER TABLE FNPAGAR ALTER COLUMN CODBANCO POSITION 15;

ALTER TABLE FNPAGAR ALTER COLUMN VLRDESCPAG POSITION 16;

ALTER TABLE FNPAGAR ALTER COLUMN VLRMULTAPAG POSITION 17;

ALTER TABLE FNPAGAR ALTER COLUMN VLRJUROSPAG POSITION 18;

ALTER TABLE FNPAGAR ALTER COLUMN VLRDEVPAG POSITION 19;

ALTER TABLE FNPAGAR ALTER COLUMN VLRPARCPAG POSITION 20;

ALTER TABLE FNPAGAR ALTER COLUMN VLRPAGOPAG POSITION 21;

ALTER TABLE FNPAGAR ALTER COLUMN VLRAPAGPAG POSITION 22;

ALTER TABLE FNPAGAR ALTER COLUMN DATAPAG POSITION 23;

ALTER TABLE FNPAGAR ALTER COLUMN STATUSPAG POSITION 24;

ALTER TABLE FNPAGAR ALTER COLUMN DTQUITPAG POSITION 25;

ALTER TABLE FNPAGAR ALTER COLUMN DOCPAG POSITION 26;

ALTER TABLE FNPAGAR ALTER COLUMN VLRPAG POSITION 27;

ALTER TABLE FNPAGAR ALTER COLUMN NROPARCPAG POSITION 28;

ALTER TABLE FNPAGAR ALTER COLUMN OBSPAG POSITION 29;

ALTER TABLE FNPAGAR ALTER COLUMN VLRADICPAG POSITION 30;

ALTER TABLE FNPAGAR ALTER COLUMN FLAG POSITION 31;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPTC POSITION 32;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALTC POSITION 33;

ALTER TABLE FNPAGAR ALTER COLUMN CODTIPOCOB POSITION 34;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPCA POSITION 35;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALCA POSITION 36;

ALTER TABLE FNPAGAR ALTER COLUMN NUMCONTA POSITION 37;

ALTER TABLE FNPAGAR ALTER COLUMN EMMANUT POSITION 38;

ALTER TABLE FNPAGAR ALTER COLUMN VLRBASEIRRF POSITION 39;

ALTER TABLE FNPAGAR ALTER COLUMN VLRBASEINSS POSITION 40;

ALTER TABLE FNPAGAR ALTER COLUMN VLRRETIRRF POSITION 41;

ALTER TABLE FNPAGAR ALTER COLUMN VLRRETINSS POSITION 42;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPPN POSITION 43;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALPN POSITION 44;

ALTER TABLE FNPAGAR ALTER COLUMN CODPLAN POSITION 45;

ALTER TABLE FNPAGAR ALTER COLUMN CODEMPCC POSITION 46;

ALTER TABLE FNPAGAR ALTER COLUMN CODFILIALCC POSITION 47;

ALTER TABLE FNPAGAR ALTER COLUMN ANOCC POSITION 48;

ALTER TABLE FNPAGAR ALTER COLUMN CODCC POSITION 49;

ALTER TABLE FNPAGAR ALTER COLUMN DTINS POSITION 50;

ALTER TABLE FNPAGAR ALTER COLUMN HINS POSITION 51;

ALTER TABLE FNPAGAR ALTER COLUMN IDUSUINS POSITION 52;

ALTER TABLE FNPAGAR ALTER COLUMN DTALT POSITION 53;

ALTER TABLE FNPAGAR ALTER COLUMN HALT POSITION 54;

ALTER TABLE FNPAGAR ALTER COLUMN IDUSUALT POSITION 55;

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

ALTER TABLE FNRECEBER ALTER COLUMN DATAREC POSITION 34;

ALTER TABLE FNRECEBER ALTER COLUMN STATUSREC POSITION 35;

ALTER TABLE FNRECEBER ALTER COLUMN VLRCOMIREC POSITION 36;

ALTER TABLE FNRECEBER ALTER COLUMN DTQUITREC POSITION 37;

ALTER TABLE FNRECEBER ALTER COLUMN DOCREC POSITION 38;

ALTER TABLE FNRECEBER ALTER COLUMN NROPARCREC POSITION 39;

ALTER TABLE FNRECEBER ALTER COLUMN OBSREC POSITION 40;

ALTER TABLE FNRECEBER ALTER COLUMN FLAG POSITION 41;

ALTER TABLE FNRECEBER ALTER COLUMN ALTUSUREC POSITION 42;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCA POSITION 43;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCA POSITION 44;

ALTER TABLE FNRECEBER ALTER COLUMN NUMCONTA POSITION 45;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPPN POSITION 46;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALPN POSITION 47;

ALTER TABLE FNRECEBER ALTER COLUMN CODPLAN POSITION 48;

ALTER TABLE FNRECEBER ALTER COLUMN CODEMPCC POSITION 49;

ALTER TABLE FNRECEBER ALTER COLUMN CODFILIALCC POSITION 50;

ALTER TABLE FNRECEBER ALTER COLUMN ANOCC POSITION 51;

ALTER TABLE FNRECEBER ALTER COLUMN CODCC POSITION 52;

ALTER TABLE FNRECEBER ALTER COLUMN DTINS POSITION 53;

ALTER TABLE FNRECEBER ALTER COLUMN HINS POSITION 54;

ALTER TABLE FNRECEBER ALTER COLUMN IDUSUINS POSITION 55;

ALTER TABLE FNRECEBER ALTER COLUMN DTALT POSITION 56;

ALTER TABLE FNRECEBER ALTER COLUMN HALT POSITION 57;

ALTER TABLE FNRECEBER ALTER COLUMN IDUSUALT POSITION 58;

ALTER TABLE FNTIPOCOB ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNTIPOCOB ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNTIPOCOB ALTER COLUMN CODTIPOCOB POSITION 3;

ALTER TABLE FNTIPOCOB ALTER COLUMN DESCTIPOCOB POSITION 4;

ALTER TABLE FNTIPOCOB ALTER COLUMN TIPOFEBRABAN POSITION 5;

ALTER TABLE FNTIPOCOB ALTER COLUMN DUPLCOB POSITION 6;

ALTER TABLE FNTIPOCOB ALTER COLUMN OBRIGCARTCOB POSITION 7;

ALTER TABLE FNTIPOCOB ALTER COLUMN DTINS POSITION 8;

ALTER TABLE FNTIPOCOB ALTER COLUMN HINS POSITION 9;

ALTER TABLE FNTIPOCOB ALTER COLUMN IDUSUINS POSITION 10;

ALTER TABLE FNTIPOCOB ALTER COLUMN DTALT POSITION 11;

ALTER TABLE FNTIPOCOB ALTER COLUMN HALT POSITION 12;

ALTER TABLE FNTIPOCOB ALTER COLUMN IDUSUALT POSITION 13;

ALTER TABLE FNTIPOCOB ALTER COLUMN CODEMPCT POSITION 14;

ALTER TABLE FNTIPOCOB ALTER COLUMN CODFILIALCT POSITION 15;

ALTER TABLE FNTIPOCOB ALTER COLUMN NUMCONTA POSITION 16;

ALTER TABLE FNTIPOCOB ALTER COLUMN SEQTALAO POSITION 17;

ALTER TABLE LFFRETE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFFRETE ALTER COLUMN CODFRETE POSITION 3;

ALTER TABLE LFFRETE ALTER COLUMN DOCFRETE POSITION 4;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPTN POSITION 5;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALTN POSITION 6;

ALTER TABLE LFFRETE ALTER COLUMN CODTRAN POSITION 7;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPTM POSITION 8;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALTM POSITION 9;

ALTER TABLE LFFRETE ALTER COLUMN CODTIPOMOV POSITION 10;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPNT POSITION 11;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALNT POSITION 12;

ALTER TABLE LFFRETE ALTER COLUMN CODNAT POSITION 13;

ALTER TABLE LFFRETE ALTER COLUMN TIPOFRETE POSITION 14;

ALTER TABLE LFFRETE ALTER COLUMN TIPOPGTO POSITION 15;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPRE POSITION 16;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALRE POSITION 17;

ALTER TABLE LFFRETE ALTER COLUMN CODREMET POSITION 18;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPDE POSITION 19;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALDE POSITION 20;

ALTER TABLE LFFRETE ALTER COLUMN CODDESTINAT POSITION 21;

ALTER TABLE LFFRETE ALTER COLUMN DTEMITFRETE POSITION 22;

ALTER TABLE LFFRETE ALTER COLUMN QTDFRETE POSITION 23;

ALTER TABLE LFFRETE ALTER COLUMN VLRMERCADORIA POSITION 24;

ALTER TABLE LFFRETE ALTER COLUMN VLRFRETE POSITION 25;

ALTER TABLE LFFRETE ALTER COLUMN VLRFRETENOTA POSITION 26;

ALTER TABLE LFFRETE ALTER COLUMN PESOBRUTO POSITION 27;

ALTER TABLE LFFRETE ALTER COLUMN PESOLIQUIDO POSITION 28;

ALTER TABLE LFFRETE ALTER COLUMN ALIQICMSFRETE POSITION 29;

ALTER TABLE LFFRETE ALTER COLUMN VLRICMSFRETE POSITION 30;

ALTER TABLE LFFRETE ALTER COLUMN VLRBASEICMSFRETE POSITION 31;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPPA POSITION 32;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALPA POSITION 33;

ALTER TABLE LFFRETE ALTER COLUMN CODPAG POSITION 34;

ALTER TABLE LFFRETE ALTER COLUMN SERIE POSITION 35;

ALTER TABLE LFFRETE ALTER COLUMN CODEMPRM POSITION 36;

ALTER TABLE LFFRETE ALTER COLUMN CODFILIALRM POSITION 37;

ALTER TABLE LFFRETE ALTER COLUMN TICKET POSITION 38;

ALTER TABLE LFFRETE ALTER COLUMN DTINS POSITION 39;

ALTER TABLE LFFRETE ALTER COLUMN HINS POSITION 40;

ALTER TABLE LFFRETE ALTER COLUMN IDUSUINS POSITION 41;

ALTER TABLE LFFRETE ALTER COLUMN DTALT POSITION 42;

ALTER TABLE LFFRETE ALTER COLUMN HALT POSITION 43;

ALTER TABLE LFFRETE ALTER COLUMN IDUSUALT POSITION 44;

ALTER TABLE SGBAIRRO ALTER COLUMN CODBAIRRO POSITION 1;

ALTER TABLE SGBAIRRO ALTER COLUMN CODMUNIC POSITION 2;

ALTER TABLE SGBAIRRO ALTER COLUMN SIGLAUF POSITION 3;

ALTER TABLE SGBAIRRO ALTER COLUMN CODPAIS POSITION 4;

ALTER TABLE SGBAIRRO ALTER COLUMN NOMEBAIRRO POSITION 5;

ALTER TABLE SGBAIRRO ALTER COLUMN VLRFRETE POSITION 6;

ALTER TABLE SGBAIRRO ALTER COLUMN QTDFRETE POSITION 7;

ALTER TABLE SGBAIRRO ALTER COLUMN DTINS POSITION 8;

ALTER TABLE SGBAIRRO ALTER COLUMN HINS POSITION 9;

ALTER TABLE SGBAIRRO ALTER COLUMN IDUSUINS POSITION 10;

ALTER TABLE SGBAIRRO ALTER COLUMN DTALT POSITION 11;

ALTER TABLE SGBAIRRO ALTER COLUMN HALT POSITION 12;

ALTER TABLE SGBAIRRO ALTER COLUMN IDUSUALT POSITION 13;

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

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 222;

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

ALTER TABLE VDITVENDA ALTER COLUMN MARGEMVLAGRITVENDA POSITION 44;

ALTER TABLE VDITVENDA ALTER COLUMN OBSITVENDA POSITION 45;

ALTER TABLE VDITVENDA ALTER COLUMN ORIGFISC POSITION 46;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPTT POSITION 47;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALTT POSITION 48;

ALTER TABLE VDITVENDA ALTER COLUMN CODTRATTRIB POSITION 49;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOFISC POSITION 50;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOST POSITION 51;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPME POSITION 52;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALME POSITION 53;

ALTER TABLE VDITVENDA ALTER COLUMN CODMENS POSITION 54;

ALTER TABLE VDITVENDA ALTER COLUMN STRDESCITVENDA POSITION 55;

ALTER TABLE VDITVENDA ALTER COLUMN QTDDEVITVENDA POSITION 56;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPLG POSITION 57;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALLG POSITION 58;

ALTER TABLE VDITVENDA ALTER COLUMN CODLOG POSITION 59;

ALTER TABLE VDITVENDA ALTER COLUMN CANCITVENDA POSITION 60;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPPE POSITION 61;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALPE POSITION 62;

ALTER TABLE VDITVENDA ALTER COLUMN CODPE POSITION 63;

ALTER TABLE VDITVENDA ALTER COLUMN DIASPE POSITION 64;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCV POSITION 65;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCV POSITION 66;

ALTER TABLE VDITVENDA ALTER COLUMN CODCONV POSITION 67;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPIF POSITION 68;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALIF POSITION 69;

ALTER TABLE VDITVENDA ALTER COLUMN CODFISC POSITION 70;

ALTER TABLE VDITVENDA ALTER COLUMN CODITFISC POSITION 71;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCP POSITION 72;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCP POSITION 73;

ALTER TABLE VDITVENDA ALTER COLUMN CODCOMPRA POSITION 74;

ALTER TABLE VDITVENDA ALTER COLUMN CODITCOMPRA POSITION 75;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPVR POSITION 76;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALVR POSITION 77;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOVENDAVR POSITION 78;

ALTER TABLE VDITVENDA ALTER COLUMN CODVENDAVR POSITION 79;

ALTER TABLE VDITVENDA ALTER COLUMN CODITVENDAVR POSITION 80;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPNS POSITION 81;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALNS POSITION 82;

ALTER TABLE VDITVENDA ALTER COLUMN NUMSERIETMP POSITION 83;

ALTER TABLE VDITVENDA ALTER COLUMN EMMANUT POSITION 84;

ALTER TABLE VDITVENDA ALTER COLUMN DTINS POSITION 85;

ALTER TABLE VDITVENDA ALTER COLUMN HINS POSITION 86;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUINS POSITION 87;

ALTER TABLE VDITVENDA ALTER COLUMN DTALT POSITION 88;

ALTER TABLE VDITVENDA ALTER COLUMN HALT POSITION 89;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUALT POSITION 90;

/* Create(Add) Crant */
GRANT DELETE, INSERT, SELECT, UPDATE ON EQITRECMERCITCP TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON FNCHEQUE TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON FNPAGCHEQ TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON FNTALAOCHEQ TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON RHTABELAINSS TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON RHTABELAIRRF TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON SGBAIRRO TO ADM;

GRANT EXECUTE ON PROCEDURE EQGERARMAOSSP TO ADM;

GRANT EXECUTE ON PROCEDURE EQPRODUTOSP01 TO PROCEDURE EQGERARMAOSSP;

GRANT EXECUTE ON PROCEDURE SGRETFILIAL TO PROCEDURE EQGERARMAOSSP;

GRANT EXECUTE ON PROCEDURE SGRETINFOUSU TO PROCEDURE EQGERARMAOSSP;

GRANT INSERT, SELECT ON EQITRMA TO PROCEDURE EQGERARMAOSSP;

GRANT INSERT, SELECT ON EQRMA TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT ON EQLOTE TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT ON EQPRODUTO TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT ON PPOPFASE TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT ON SGPREFERE1 TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT ON SGPREFERE5 TO PROCEDURE EQGERARMAOSSP;

GRANT SELECT, UPDATE ON PPITOP TO PROCEDURE EQGERARMAOSSP;



