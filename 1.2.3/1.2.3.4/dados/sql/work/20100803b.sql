/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

/* Create Table... */
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


/* Create Primary Key... */
ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQPK PRIMARY KEY (SEQCHEQ,CODPAG,NPARCPAG,CODFILIALCH,CODEMPCH,CODFILIAL,CODEMP);

/* Create Foreign Key... */
CONNECT 'localhost:/opt/firebird/dados/desenv/freedom.fdb' USER 'SYSDBA' PASSWORD '123654';

ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQFKCHEQUE FOREIGN KEY (SEQCHEQ,CODFILIALCH,CODEMPCH) REFERENCES FNCHEQUE(SEQCHEQ,CODFILIAL,CODEMP) USING INDEX FNPAGCHEQFKCHEQ;

ALTER TABLE FNPAGCHEQ ADD CONSTRAINT FNPAGCHEQFKFNITPAG FOREIGN KEY (NPARCPAG,CODPAG,CODFILIAL,CODEMP) REFERENCES FNITPAGAR(NPARCPAG,CODPAG,CODFILIAL,CODEMP) USING INDEX FNPAGCHEQFKFNPAGAR;

/* Create Trigger... */
SET TERM ^ ;

CREATE TRIGGER FNCHEQUETGBU FOR FNCHEQUE
ACTIVE BEFORE UPDATE POSITION 0 
AS
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^



