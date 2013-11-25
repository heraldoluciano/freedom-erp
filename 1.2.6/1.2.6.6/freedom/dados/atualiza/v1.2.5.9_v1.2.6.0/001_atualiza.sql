/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.9.fdb' USER 'SYSDBA' PASSWORD '123654';

SET AUTODDL ON;

/* AssignEmptyBody proc */
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

DROP VIEW VWCUSTOPROJ01;

ALTER TABLE ATATENDENTE ALTER COLUMN FAXATEND TYPE VARCHAR(9);

ALTER TABLE ATATENDENTE ALTER COLUMN CELATEND TYPE VARCHAR(9);

ALTER TABLE ATCONVENIADO ALTER COLUMN FAXCONV TYPE VARCHAR(9);

/* Alter Field (Length): from 8 to 9... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=9, RDB$CHARACTER_LENGTH=9
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='ATCONVENIADO' AND  RDB$FIELD_NAME='CELCONV');

ALTER TABLE ATENCAMINHADOR ALTER COLUMN FAXENC TYPE VARCHAR(9);

/* Empty CPADICFORSP for drop CPFORNECED(FAXFOR) */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE CPADICFORSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALCL INTEGER,
CODCLI INTEGER)
 RETURNS(CODFOR INTEGER)
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

DROP VIEW FNFLUXOCAIXAVW01;

ALTER TABLE CPFORNECED ALTER COLUMN FAXFOR TYPE VARCHAR(9);

ALTER TABLE CPFORNECED ALTER COLUMN CELFOR TYPE VARCHAR(9);

/* Alter Field (Length): from 8 to 9... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=9, RDB$CHARACTER_LENGTH=9
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='FNCHEQUE' AND  RDB$FIELD_NAME='FONEEMITCHEQ');

/* Alter Field (Length): from 8 to 9... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=9, RDB$CHARACTER_LENGTH=9
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='FNCHEQUE' AND  RDB$FIELD_NAME='FONEFAVCHEQ');

ALTER TABLE RHCANDIDATO ALTER COLUMN CELCAND TYPE VARCHAR(9);

ALTER TABLE RHEMPREGADOR ALTER COLUMN FAXEMPR TYPE VARCHAR(9);

ALTER TABLE RHEMPREGADOR ALTER COLUMN CELEMPR TYPE VARCHAR(9);

ALTER TABLE SGEMPRESA ALTER COLUMN FAXEMP TYPE VARCHAR(9);

ALTER TABLE SGFILIAL ALTER COLUMN FAXFILIAL TYPE VARCHAR(9);

/* Empty TKCONTCLISP for drop TKCONTATO(FAXCTO) */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE TKCONTCLISP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCTO INTEGER,
CODFILIALTI INTEGER,
CODTIPOCLI INTEGER,
CODFILIALCC INTEGER,
CODCLASCLI INTEGER,
CODFILIALSR INTEGER,
CODSETOR INTEGER)
 RETURNS(IRET INTEGER)
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

DROP VIEW TKCONTCLIVW03;

DROP VIEW TKCONTCLIVW01;

ALTER TABLE TKCONTATO ALTER COLUMN FAXCTO TYPE VARCHAR(9);

/* Alter Field (Length): from 8 to 9... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=9, RDB$CHARACTER_LENGTH=9
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='VDCLICOMPL' AND  RDB$FIELD_NAME='FONETRABCLI');

/* Alter Field (Length): from 8 to 9... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=9, RDB$CHARACTER_LENGTH=9
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='VDCLICOMPL' AND  RDB$FIELD_NAME='FONEAVALCLI');

/* Empty VDCOPIACLIENTE for drop VDCLIENTE(FAXCLI) */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE VDCOPIACLIENTE(ICODCLI INTEGER,
IDOCUMENTO VARCHAR(14),
ICODEMP INTEGER,
ICODFILIAL INTEGER)
 RETURNS(ICOD INTEGER)
 AS
 BEGIN EXIT; END
^

SET TERM ; ^

DROP VIEW ATATENDIMENTOVW07;

DROP VIEW TKCONTCLIVW02;

ALTER TABLE VDCLIENTE ALTER COLUMN FAXCLI TYPE VARCHAR(9);

ALTER TABLE VDCLIENTE ALTER COLUMN FAXCOB TYPE VARCHAR(9);

ALTER TABLE VDCLIENTE ALTER COLUMN FAXENT TYPE VARCHAR(9);

ALTER TABLE VDCLIENTE ALTER COLUMN CELCLI TYPE VARCHAR(9);

ALTER TABLE VDMOTORISTA ALTER COLUMN CELMOT TYPE VARCHAR(9);

ALTER TABLE VDTRANSP ALTER COLUMN FAXTRAN TYPE VARCHAR(9);

ALTER TABLE VDTRANSP ALTER COLUMN CELTRAN TYPE VARCHAR(9);

ALTER TABLE VDVENDEDOR ALTER COLUMN CELVEND TYPE VARCHAR(12);


ALTER TABLE SGPREFERE1 ADD AGENDAFPRINCIPAL CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD ATUALIZAAGENDA CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD TEMPOATUAGENDA INTEGER DEFAULT 30 NOT NULL;

ALTER TABLE SGPREFERE1 ADD SOLDTSAIDA CHAR(1) DEFAULT 'N' NOT NULL;

/* Create Views... */
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


/* Alter Procedure... */
/* empty dependent procedure body */
/* Clear: ATRESUMOATENDOSP02 for: ATRESUMOATENDOSP01 */
/* AssignEmptyBody proc */
SET TERM ^ ;

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
 BEGIN EXIT; END
^

/* Alter (ATRESUMOATENDOSP01) */
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

/* empty dependent procedure body */
/* Clear: CPGERAENTRADASP for: CPADICFORSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter (CPADICFORSP) */
ALTER PROCEDURE CPADICFORSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALCL INTEGER,
CODCLI INTEGER)
 RETURNS(CODFOR INTEGER)
 AS
DECLARE VARIABLE CODTIPOFOR INTEGER;
DECLARE VARIABLE CODFILIALTF INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT P.CODTIPOFOR,P.CODFILIALTF FROM SGPREFERE1 P WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL INTO CODTIPOFOR,CODFILIALTF;
  SELECT MAX(CODFOR)+1 FROM CPFORNECED WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIALFR INTO CODFOR;
  INSERT INTO CPFORNECED (CODEMP,CODFILIAL,CODFOR,RAZFOR,CODEMPTF,CODFILIALTF,
                        CODTIPOFOR,CODEMPBO,CODFILIALBO,CODBANCO,NOMEFOR,
                        DATAFOR,ATIVOFOR,PESSOAFOR,CNPJFOR,CPFFOR,INSCFOR,
                        RGFOR,ENDFOR,NUMFOR,COMPLFOR,BAIRFOR,CEPFOR,CIDFOR,
                        UFFOR,CONTFOR,FONEFOR,FAXFOR,AGENCIAFOR,CONTAFOR,
                        EMAILFOR,OBSFOR,CELFOR,CLIFOR)
                 SELECT :CODEMP,:CODFILIALFR,:CODFOR,RAZCLI,:CODEMP,:CODFILIALTF,
                        :CODTIPOFOR,NULL,NULL,NULL,NOMECLI,
                        DATACLI,ATIVOCLI,PESSOACLI,CNPJCLI,CPFCLI,INSCCLI,
                        RGCLI,ENDCLI,NUMCLI,COMPLCLI,BAIRCLI,CEPCLI,CIDCLI,
                        UFCLI,CONTCLI,FONECLI,FAXCLI,NULL,NULL,
                        EMAILCLI,OBSCLI,CELCLI,'C' FROM VDCLIENTE WHERE
                        CODCLI=:CODCLI AND CODFILIAL=:CODFILIALCL AND
                        CODEMP=:CODEMP;
  SUSPEND;
END
^

/* Alter (CPGERAENTRADASP) */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
DECLARE VARIABLE STATUSVENDA CHAR(2);
DECLARE VARIABLE CODCLI INTEGER;
DECLARE VARIABLE CODFILIALCL INTEGER;
DECLARE VARIABLE CODFILIALCP INTEGER;
DECLARE VARIABLE CODTIPOMOV INTEGER;
DECLARE VARIABLE CODFILIALTM INTEGER;
DECLARE VARIABLE SERIE INTEGER;
DECLARE VARIABLE CODFILIALSE INTEGER;
DECLARE VARIABLE DOC INTEGER;
DECLARE VARIABLE CODFOR INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
DECLARE VARIABLE CODITVENDA INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPCOMPRA') INTO CODFILIALCP;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'VDCLINTE') INTO CODFILIALCL;

  SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'CP') INTO CODCOMPRA;

  SELECT CODCLI,CODFILIALCL,STATUSVENDA FROM VDVENDA WHERE CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO CODCLI,CODFILIALCL,STATUSVENDA;
  IF (SUBSTRING (STATUSVENDA FROM 1 FOR 1) = 'C') THEN
    EXCEPTION VDVENDAEX05;
  
  SELECT CODFOR FROM CPADICFORSP(:CODEMP,:CODFILIAL,:CODFILIALCL,:CODCLI) INTO CODFOR;

  SELECT P.CODTIPOMOV5,P.CODFILIALT5,
    T.SERIE,T.CODFILIALSE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL AND T.CODTIPOMOV=P.CODTIPOMOV5 AND
    T.CODEMP=P.CODEMPT5 AND T.CODFILIAL=P.CODFILIALT5
    INTO CODTIPOMOV,CODFILIALTM,SERIE,CODFILIALSE;

  SELECT * FROM LFNOVODOCSP(:SERIE,:CODEMP,:CODFILIALSE) INTO DOC;

  INSERT INTO CPCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        CODEMPFR,CODFILIALFR,CODFOR,CODEMPSE,CODFILIALSE,SERIE,
                        CODEMPTM,CODFILIALTM,CODTIPOMOV,DOCCOMPRA,DTENTCOMPRA,DTEMITCOMPRA)
                 SELECT :CODEMP,:CODFILIAL,:CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        :CODEMP,:CODFILIALFR,:CODFOR,:CODEMP,:CODFILIALSE,:SERIE,
                        CODEMP,:CODFILIALTM,:CODTIPOMOV,:DOC,DTSAIDAVENDA,DTEMITVENDA FROM
                        VDVENDA WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
  IF (CITEM = 'S') THEN
    EXECUTE PROCEDURE CPGERAITENTRADASP(:CODEMP,:CODFILIALCP,:CODCOMPRA,:CODFILIALVD,:TIPOVENDA,:CODVENDA,NULL,NULL);
    
  UPDATE VDVENDA SET STATUSVENDA='DV' WHERE TIPOVENDA=:TIPOVENDA AND CODVENDA=:CODVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD;
  
  SUSPEND;
END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.0 (25/04/2013)';
    suspend;
end
^

/* Alter (TKCONTCLISP) */
ALTER PROCEDURE TKCONTCLISP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCTO INTEGER,
CODFILIALTI INTEGER,
CODTIPOCLI INTEGER,
CODFILIALCC INTEGER,
CODCLASCLI INTEGER,
CODFILIALSR INTEGER,
CODSETOR INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable icodcli integer;
declare variable codfilialpf integer;
declare variable usuativcli char(1);
declare variable ifilialcli integer;
BEGIN
  SELECT MAX(CODCLI)+1 FROM VDCLIENTE WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO ICODCLI;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'VDCLIENTE') INTO IFILIALCLI;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'SGPREFERE1') INTO :CODFILIALPF;
  SELECT PF.USUATIVCLI FROM SGPREFERE1 PF WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALPF
    INTO :USUATIVCLI;
  
  INSERT INTO VDCLIENTE (CODEMP,CODFILIAL,CODCLI,RAZCLI,CODEMPCC,CODFILIALCC,CODCLASCLI,
  CODEMPVD,CODFILIALVD,CODVEND,CODEMPSR,CODFILIALSR,CODSETOR,NOMECLI,CODEMPTI,CODFILIALTI,
  CODTIPOCLI,DATACLI,PESSOACLI,ATIVOCLI,CNPJCLI,INSCCLI,CPFCLI,RGCLI,ENDCLI,NUMCLI,
  COMPLCLI,BAIRCLI,CIDCLI,UFCLI,CEPCLI,FONECLI,FAXCLI,EMAILCLI,CONTCLI,CTOCLI,
  CODPAIS, SIGLAUF, CODMUNIC, EDIFICIOCLI)
    SELECT :CODEMP,:IFILIALCLI,:ICODCLI,RAZCTO,:CODEMP,:CODFILIALCC,:CODCLASCLI,
    CODEMPVD,CODFILIALVD,CODVEND,:CODEMP,:CODFILIALSR,:CODSETOR,NOMECTO,:CODEMP,:CODFILIALTI,
    :CODTIPOCLI,DATACTO,PESSOACTO,(CASE WHEN COALESCE(:USUATIVCLI,'N')='S' THEN 'N' ELSE 'S' END),
    CNPJCTO,INSCCTO,CPFCTO,RGCTO,ENDCTO,NUMCTO,
    COMPLCTO,BAIRCTO,CIDCTO,UFCTO,CEPCTO,FONECTO,FAXCTO,EMAILCTO,CONTCTO,'O',
    CODPAIS, SIGLAUF, CODMUNIC, EDIFICIOCTO
  FROM TKCONTATO WHERE
    CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODCTO=:CODCTO;
    
  INSERT INTO TKCONTCLI (CODEMPCTO, CODFILIALCTO, CODCTO, CODEMPCLI, CODFILIALCLI, CODCLI)
    VALUES (:CODEMP, :CODFILIAL, :CODCTO, :CODEMP, :IFILIALCLI, :ICODCLI);
  IRET = ICODCLI;
  
  SUSPEND;
END
^

/* Alter (VDADICVENDAORCSP) */
ALTER PROCEDURE VDADICVENDAORCSP(ICODORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
NEWCODVENDA INTEGER,
DTSAIDAVENDA DATE)
 RETURNS(IRET INTEGER)
 AS
declare variable icodvenda integer;
declare variable ifilialvd smallint;
declare variable icodtipomov integer;
declare variable ifilialtm smallint;
declare variable sserie char(4);
declare variable ifilialse smallint;
declare variable sstatusvenda char(2);
declare variable dperccomvend numeric(9,2);
declare variable icodvend integer;
declare variable icodfilialvd integer;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodplanopag integer;
declare variable icodfilialpg integer;
declare variable usapedseq char(1);
declare variable iconta integer;
declare variable icodclcomis integer;
declare variable icodfilialcm integer;
declare variable vlrdescorc numeric(15,5);
declare variable vlrfreteorc numeric(15,5);
declare variable codemptn integer;
declare variable codfilialtn smallint;
declare variable codtran integer;
declare variable tipofrete char(1);
declare variable adicfrete char(1);
BEGIN

/*Cod. Tipo Mov e Sequencia:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'EQTIPOMOV') INTO IFILIALTM;
  SELECT COUNT(P.TIPOPROD) FROM EQPRODUTO P, VDITORCAMENTO I WHERE
    P.CODPROD=I.CODPROD AND P.CODFILIAL=I.CODFILIALPD AND P.CODEMP=I.CODEMPPD
    AND P.TIPOPROD = 'S' AND I.CODORC=:ICODORC
    AND I.CODFILIAL=:ICODFILIAL AND I.CODEMP=:ICODEMP INTO ICONTA;
    
  IF (ICONTA > 0) THEN
    SELECT CODTIPOMOV4,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;
  IF (ICODTIPOMOV IS NULL) THEN  /* CASO AINDA O IF DE CIMA NAO TENHA PREECHIDO... */
    SELECT CODTIPOMOV3,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;

/*Informações basicas:*/

  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'VDVENDA') INTO IFILIALVD;
  IF (USAPEDSEQ='S') THEN
    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALVD,'VD') INTO ICODVENDA;
  ELSE
    IF ((:NEWCODVENDA > 0) AND (:NEWCODVENDA IS NOT NULL)) THEN
      ICODVENDA = :NEWCODVENDA;
    ELSE
      SELECT MAX(CODVENDA)+1 FROM VDVENDA
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALVD
        INTO ICODVENDA;

/*Serie:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'LFSERIE') INTO IFILIALSE;
  SELECT SERIE FROM EQTIPOMOV WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALTM AND CODTIPOMOV=:ICODTIPOMOV INTO SSERIE;

/*Status:*/
  SSTATUSVENDA = 'P1';

/*Busca no orçamento:*/
  SELECT O.CODFILIALVD,O.CODVEND,O.CODFILIALCL,O.CODCLI,O.CODFILIALPG,
    O.CODPLANOPAG,VE.PERCCOMVEND,O.CODCLCOMIS,O.CODFILIALCM, o.vlrdescorc
    , coalesce(O.VLRFRETEORC,0), O.CODEMPTN, O.CODFILIALTN, O.CODTRAN
    , o.tipofrete, o.adicfrete
    FROM VDORCAMENTO O, VDVENDEDOR VE WHERE O.CODORC=:ICODORC
    AND O.CODFILIAL=:ICODFILIAL AND O.CODEMP=:ICODEMP
    AND VE.CODEMP=O.CODEMP AND VE.CODFILIAL=O.CODFILIALVD
    AND VE.CODVEND=O.CODVEND INTO
           :ICODFILIALVD,:ICODVEND,:ICODFILIALCL,:ICODCLI
           ,:ICODFILIALPG,:ICODPLANOPAG,DPERCCOMVEND,:ICODCLCOMIS
           ,:ICODFILIALCM,:vlrdescorc, :VLRFRETEORC
           ,:CODEMPTN, :CODFILIALTN, :CODTRAN
           , :TIPOFRETE, :adicfrete;

  INSERT INTO VDVENDA (
    CODEMP,CODFILIAL,CODVENDA,TIPOVENDA,CODEMPVD,CODFILIALVD,CODVEND,CODEMPCL,CODFILIALCL,CODCLI,
    CODEMPPG,CODFILIALPG,CODPLANOPAG,CODEMPSE,CODFILIALSE,SERIE,CODEMPTM,CODFILIALTM,CODTIPOMOV,
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM,vlrdescvenda)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    :DTSAIDAVENDA,CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC
    );

  if ( (:codtran is not null) or (:vlrfreteorc>0) )  then
  begin
     insert into vdfretevd (codemp, codfilial, tipovenda, codvenda, codemptn
        , codfilialtn, codtran, tipofretevd, vlrfretevd, adicfretevd
        , placafretevd, vlrsegfretevd, pesobrutvd, pesoliqvd
        , espfretevd, marcafretevd, qtdfretevd, uffretevd
         )
        values (:icodemp, :icodfilialvd, :stipovenda, :icodvenda, :codemptn
        , :codfilialtn, :codtran, :tipofrete,  :vlrfreteorc, :adicfrete
        , '***-***', 0, 0, 0
        , 'Volume', '*', 0, '**' );

  end

  IRET = ICODVENDA;

  SUSPEND;
END
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

/* Alter (VDCOPIACLIENTE) */
ALTER PROCEDURE VDCOPIACLIENTE(ICODCLI INTEGER,
IDOCUMENTO VARCHAR(14),
ICODEMP INTEGER,
ICODFILIAL INTEGER)
 RETURNS(ICOD INTEGER)
 AS
declare variable inovocod integer;
begin
   SELECT MAX(CODCLI)+1 FROM VDCLIENTE
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO INOVOCOD;

    INSERT INTO VDCLIENTE (CODEMP, CODFILIAL, CODCLI, RAZCLI, NOMECLI, CODEMPCC, CODFILIALCC,
        CODCLASCLI, CODEMPVD, CODFILIALVD, CODVEND, CODEMPTC, CODFILIALTC, CODTIPOCOB, CODEMPPG,
        CODFILIALPG, CODPLANOPAG, CODEMPTN, CODFILIALTN, CODTRAN, CODEMPBO, CODFILIALBO, CODBANCO,
        CODEMPSR, CODFILIALSR, CODSETOR, CODEMPTI, CODFILIALTI, CODTIPOCLI, CODTPCRED, CODFILIALTR,
        CODEMPTR, CODFISCCLI, CODEMPFC, CODFILIALFC, CODEMPEC, CODFILIALEC, CODTBEC, CODITTBEC, CODEMPHP,
        CODFILIALHP, CODHIST, CODEMPCB, CODFILIALCB, CODCARTCOB, DATACLI, PESSOACLI, ATIVOCLI, CNPJCLI,
        INSCCLI, CPFCLI, RGCLI, SSPCLI, ENDCLI, NUMCLI, COMPLCLI, BAIRCLI, CIDCLI, UFCLI, CEPCLI, DDDCLI,
        FONECLI, RAMALCLI, DDDFAXCLI, FAXCLI, EMAILCLI, EMAILCOB, EMAILENT, EMAILNFECLI, CONTCLI, ENDCOB,
        NUMCOB, COMPLCOB, BAIRCOB, CIDCOB, UFCOB, CEPCOB, DDDFONECOB, FONECOB, DDDFAXCOB, FAXCOB, ENDENT,
        NUMENT, COMPLENT, BAIRENT, CIDENT, UFENT, CEPENT, DDDFONEENT, FONEENT, DDDFAXENT, FAXENT, OBSCLI,
        AGENCIACLI, CODEMPPQ, CODFILIALPQ, CODPESQ, INCRACLI, DTINITR, DTVENCTOTR, NIRFCLI, SIMPLESCLI, DDDCELCLI,
        CELCLI, NATCLI, UFNATCLI, TEMPORESCLI, APELIDOCLI, SITECLI, CODCONTDEB, CODCONTCRED, CODCLICONTAB,
        FOTOCLI, IMGASSCLI, CODMUNIC, SIGLAUF, CODPAIS, CODMUNICENT, SIGLAUFENT, CODPAISENT, CODMUNICCOB,
        SIGLAUFCOB, CODPAISCOB, CODEMPUC, CODFILIALUC, CODUNIFCOD, SUFRAMACLI, PRODRURALCLI, CTOCLI, CODCNAE,
        INSCMUNCLI, PERCDESCCLI, CONTCLICOB, CONTCLIENT, DESCIPI)
        SELECT :ICODEMP, :ICODFILIAL, :INOVOCOD, RAZCLI, NOMECLI, CODEMPCC, CODFILIALCC, CODCLASCLI, CODEMPVD,
            CODFILIALVD, CODVEND, CODEMPTC, CODFILIALTC, CODTIPOCOB, CODEMPPG, CODFILIALPG, CODPLANOPAG, CODEMPTN,
            CODFILIALTN, CODTRAN, CODEMPBO, CODFILIALBO, CODBANCO, CODEMPSR, CODFILIALSR, CODSETOR, CODEMPTI,
            CODFILIALTI, CODTIPOCLI, CODTPCRED, CODFILIALTR, CODEMPTR, CODFISCCLI, CODEMPFC, CODFILIALFC, CODEMPEC,
            CODFILIALEC, CODTBEC, CODITTBEC, CODEMPHP, CODFILIALHP, CODHIST, CODEMPCB, CODFILIALCB, CODCARTCOB, DATACLI,
            PESSOACLI, ATIVOCLI,

            CASE PESSOACLI
                WHEN 'J' THEN :IDOCUMENTO
            ELSE NULL
            END,

            INSCCLI,

            CASE PESSOACLI
                WHEN 'F' THEN :IDOCUMENTO
            ELSE NULL
            END,

            RGCLI, SSPCLI, ENDCLI, NUMCLI, COMPLCLI, BAIRCLI, CIDCLI,
            UFCLI, CEPCLI, DDDCLI, FONECLI, RAMALCLI, DDDFAXCLI, FAXCLI, EMAILCLI, EMAILCOB, EMAILENT, EMAILNFECLI,
            CONTCLI, ENDCOB, NUMCOB, COMPLCOB, BAIRCOB, CIDCOB, UFCOB, CEPCOB, DDDFONECOB, FONECOB, DDDFAXCOB, FAXCOB,
            ENDENT, NUMENT, COMPLENT, BAIRENT, CIDENT, UFENT, CEPENT, DDDFONEENT, FONEENT, DDDFAXENT, FAXENT, OBSCLI,
            AGENCIACLI, CODEMPPQ, CODFILIALPQ, CODPESQ, INCRACLI, DTINITR, DTVENCTOTR, NIRFCLI, SIMPLESCLI, DDDCELCLI,
            CELCLI, NATCLI, UFNATCLI, TEMPORESCLI, APELIDOCLI, SITECLI, CODCONTDEB, CODCONTCRED, CODCLICONTAB, FOTOCLI,
            IMGASSCLI, CODMUNIC, SIGLAUF, CODPAIS, CODMUNICENT, SIGLAUFENT, CODPAISENT, CODMUNICCOB, SIGLAUFCOB, CODPAISCOB,
            CODEMPUC, CODFILIALUC, CODUNIFCOD, SUFRAMACLI, PRODRURALCLI, CTOCLI, CODCNAE, INSCMUNCLI, PERCDESCCLI, CONTCLICOB,
            CONTCLIENT, DESCIPI
            FROM VDCLIENTE  VC WHERE VC.CODEMP=:ICODEMP AND VC.CODFILIAL=:ICODFILIAL AND VC.CODCLI = :ICODCLI ;

     ICOD = INOVOCOD;

  suspend;
end
^

/* Alter Procedure... */
/* Alter (ATRESUMOATENDOSP01) */
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

/* Alter (CPADICFORSP) */
ALTER PROCEDURE CPADICFORSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALCL INTEGER,
CODCLI INTEGER)
 RETURNS(CODFOR INTEGER)
 AS
DECLARE VARIABLE CODTIPOFOR INTEGER;
DECLARE VARIABLE CODFILIALTF INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT P.CODTIPOFOR,P.CODFILIALTF FROM SGPREFERE1 P WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL INTO CODTIPOFOR,CODFILIALTF;
  SELECT MAX(CODFOR)+1 FROM CPFORNECED WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIALFR INTO CODFOR;
  INSERT INTO CPFORNECED (CODEMP,CODFILIAL,CODFOR,RAZFOR,CODEMPTF,CODFILIALTF,
                        CODTIPOFOR,CODEMPBO,CODFILIALBO,CODBANCO,NOMEFOR,
                        DATAFOR,ATIVOFOR,PESSOAFOR,CNPJFOR,CPFFOR,INSCFOR,
                        RGFOR,ENDFOR,NUMFOR,COMPLFOR,BAIRFOR,CEPFOR,CIDFOR,
                        UFFOR,CONTFOR,FONEFOR,FAXFOR,AGENCIAFOR,CONTAFOR,
                        EMAILFOR,OBSFOR,CELFOR,CLIFOR)
                 SELECT :CODEMP,:CODFILIALFR,:CODFOR,RAZCLI,:CODEMP,:CODFILIALTF,
                        :CODTIPOFOR,NULL,NULL,NULL,NOMECLI,
                        DATACLI,ATIVOCLI,PESSOACLI,CNPJCLI,CPFCLI,INSCCLI,
                        RGCLI,ENDCLI,NUMCLI,COMPLCLI,BAIRCLI,CEPCLI,CIDCLI,
                        UFCLI,CONTCLI,FONECLI,FAXCLI,NULL,NULL,
                        EMAILCLI,OBSCLI,CELCLI,'C' FROM VDCLIENTE WHERE
                        CODCLI=:CODCLI AND CODFILIAL=:CODFILIALCL AND
                        CODEMP=:CODEMP;
  SUSPEND;
END
^

/* Alter (CPGERAENTRADASP) */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
DECLARE VARIABLE STATUSVENDA CHAR(2);
DECLARE VARIABLE CODCLI INTEGER;
DECLARE VARIABLE CODFILIALCL INTEGER;
DECLARE VARIABLE CODFILIALCP INTEGER;
DECLARE VARIABLE CODTIPOMOV INTEGER;
DECLARE VARIABLE CODFILIALTM INTEGER;
DECLARE VARIABLE SERIE INTEGER;
DECLARE VARIABLE CODFILIALSE INTEGER;
DECLARE VARIABLE DOC INTEGER;
DECLARE VARIABLE CODFOR INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
DECLARE VARIABLE CODITVENDA INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPCOMPRA') INTO CODFILIALCP;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'VDCLINTE') INTO CODFILIALCL;

  SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'CP') INTO CODCOMPRA;

  SELECT CODCLI,CODFILIALCL,STATUSVENDA FROM VDVENDA WHERE CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO CODCLI,CODFILIALCL,STATUSVENDA;
  IF (SUBSTRING (STATUSVENDA FROM 1 FOR 1) = 'C') THEN
    EXCEPTION VDVENDAEX05;
  
  SELECT CODFOR FROM CPADICFORSP(:CODEMP,:CODFILIAL,:CODFILIALCL,:CODCLI) INTO CODFOR;

  SELECT P.CODTIPOMOV5,P.CODFILIALT5,
    T.SERIE,T.CODFILIALSE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL AND T.CODTIPOMOV=P.CODTIPOMOV5 AND
    T.CODEMP=P.CODEMPT5 AND T.CODFILIAL=P.CODFILIALT5
    INTO CODTIPOMOV,CODFILIALTM,SERIE,CODFILIALSE;

  SELECT * FROM LFNOVODOCSP(:SERIE,:CODEMP,:CODFILIALSE) INTO DOC;

  INSERT INTO CPCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        CODEMPFR,CODFILIALFR,CODFOR,CODEMPSE,CODFILIALSE,SERIE,
                        CODEMPTM,CODFILIALTM,CODTIPOMOV,DOCCOMPRA,DTENTCOMPRA,DTEMITCOMPRA)
                 SELECT :CODEMP,:CODFILIAL,:CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        :CODEMP,:CODFILIALFR,:CODFOR,:CODEMP,:CODFILIALSE,:SERIE,
                        CODEMP,:CODFILIALTM,:CODTIPOMOV,:DOC,DTSAIDAVENDA,DTEMITVENDA FROM
                        VDVENDA WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
  IF (CITEM = 'S') THEN
    EXECUTE PROCEDURE CPGERAITENTRADASP(:CODEMP,:CODFILIALCP,:CODCOMPRA,:CODFILIALVD,:TIPOVENDA,:CODVENDA,NULL,NULL);
    
  UPDATE VDVENDA SET STATUSVENDA='DV' WHERE TIPOVENDA=:TIPOVENDA AND CODVENDA=:CODVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD;
  
  SUSPEND;
END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.0 (25/04/2013)';
    suspend;
end
^

/* Alter (TKCONTCLISP) */
ALTER PROCEDURE TKCONTCLISP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCTO INTEGER,
CODFILIALTI INTEGER,
CODTIPOCLI INTEGER,
CODFILIALCC INTEGER,
CODCLASCLI INTEGER,
CODFILIALSR INTEGER,
CODSETOR INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable icodcli integer;
declare variable codfilialpf integer;
declare variable usuativcli char(1);
declare variable ifilialcli integer;
BEGIN
  SELECT MAX(CODCLI)+1 FROM VDCLIENTE WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO ICODCLI;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'VDCLIENTE') INTO IFILIALCLI;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'SGPREFERE1') INTO :CODFILIALPF;
  SELECT PF.USUATIVCLI FROM SGPREFERE1 PF WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALPF
    INTO :USUATIVCLI;
  
  INSERT INTO VDCLIENTE (CODEMP,CODFILIAL,CODCLI,RAZCLI,CODEMPCC,CODFILIALCC,CODCLASCLI,
  CODEMPVD,CODFILIALVD,CODVEND,CODEMPSR,CODFILIALSR,CODSETOR,NOMECLI,CODEMPTI,CODFILIALTI,
  CODTIPOCLI,DATACLI,PESSOACLI,ATIVOCLI,CNPJCLI,INSCCLI,CPFCLI,RGCLI,ENDCLI,NUMCLI,
  COMPLCLI,BAIRCLI,CIDCLI,UFCLI,CEPCLI,FONECLI,FAXCLI,EMAILCLI,CONTCLI,CTOCLI,
  CODPAIS, SIGLAUF, CODMUNIC, EDIFICIOCLI)
    SELECT :CODEMP,:IFILIALCLI,:ICODCLI,RAZCTO,:CODEMP,:CODFILIALCC,:CODCLASCLI,
    CODEMPVD,CODFILIALVD,CODVEND,:CODEMP,:CODFILIALSR,:CODSETOR,NOMECTO,:CODEMP,:CODFILIALTI,
    :CODTIPOCLI,DATACTO,PESSOACTO,(CASE WHEN COALESCE(:USUATIVCLI,'N')='S' THEN 'N' ELSE 'S' END),
    CNPJCTO,INSCCTO,CPFCTO,RGCTO,ENDCTO,NUMCTO,
    COMPLCTO,BAIRCTO,CIDCTO,UFCTO,CEPCTO,FONECTO,FAXCTO,EMAILCTO,CONTCTO,'O',
    CODPAIS, SIGLAUF, CODMUNIC, EDIFICIOCTO
  FROM TKCONTATO WHERE
    CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODCTO=:CODCTO;
    
  INSERT INTO TKCONTCLI (CODEMPCTO, CODFILIALCTO, CODCTO, CODEMPCLI, CODFILIALCLI, CODCLI)
    VALUES (:CODEMP, :CODFILIAL, :CODCTO, :CODEMP, :IFILIALCLI, :ICODCLI);
  IRET = ICODCLI;
  
  SUSPEND;
END
^

/* Alter (VDADICVENDAORCSP) */
ALTER PROCEDURE VDADICVENDAORCSP(ICODORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
NEWCODVENDA INTEGER,
DTSAIDAVENDA DATE)
 RETURNS(IRET INTEGER)
 AS
declare variable icodvenda integer;
declare variable ifilialvd smallint;
declare variable icodtipomov integer;
declare variable ifilialtm smallint;
declare variable sserie char(4);
declare variable ifilialse smallint;
declare variable sstatusvenda char(2);
declare variable dperccomvend numeric(9,2);
declare variable icodvend integer;
declare variable icodfilialvd integer;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodplanopag integer;
declare variable icodfilialpg integer;
declare variable usapedseq char(1);
declare variable iconta integer;
declare variable icodclcomis integer;
declare variable icodfilialcm integer;
declare variable vlrdescorc numeric(15,5);
declare variable vlrfreteorc numeric(15,5);
declare variable codemptn integer;
declare variable codfilialtn smallint;
declare variable codtran integer;
declare variable tipofrete char(1);
declare variable adicfrete char(1);
BEGIN

/*Cod. Tipo Mov e Sequencia:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'EQTIPOMOV') INTO IFILIALTM;
  SELECT COUNT(P.TIPOPROD) FROM EQPRODUTO P, VDITORCAMENTO I WHERE
    P.CODPROD=I.CODPROD AND P.CODFILIAL=I.CODFILIALPD AND P.CODEMP=I.CODEMPPD
    AND P.TIPOPROD = 'S' AND I.CODORC=:ICODORC
    AND I.CODFILIAL=:ICODFILIAL AND I.CODEMP=:ICODEMP INTO ICONTA;
    
  IF (ICONTA > 0) THEN
    SELECT CODTIPOMOV4,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;
  IF (ICODTIPOMOV IS NULL) THEN  /* CASO AINDA O IF DE CIMA NAO TENHA PREECHIDO... */
    SELECT CODTIPOMOV3,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;

/*Informações basicas:*/

  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'VDVENDA') INTO IFILIALVD;
  IF (USAPEDSEQ='S') THEN
    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALVD,'VD') INTO ICODVENDA;
  ELSE
    IF ((:NEWCODVENDA > 0) AND (:NEWCODVENDA IS NOT NULL)) THEN
      ICODVENDA = :NEWCODVENDA;
    ELSE
      SELECT MAX(CODVENDA)+1 FROM VDVENDA
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALVD
        INTO ICODVENDA;

/*Serie:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'LFSERIE') INTO IFILIALSE;
  SELECT SERIE FROM EQTIPOMOV WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALTM AND CODTIPOMOV=:ICODTIPOMOV INTO SSERIE;

/*Status:*/
  SSTATUSVENDA = 'P1';

/*Busca no orçamento:*/
  SELECT O.CODFILIALVD,O.CODVEND,O.CODFILIALCL,O.CODCLI,O.CODFILIALPG,
    O.CODPLANOPAG,VE.PERCCOMVEND,O.CODCLCOMIS,O.CODFILIALCM, o.vlrdescorc
    , coalesce(O.VLRFRETEORC,0), O.CODEMPTN, O.CODFILIALTN, O.CODTRAN
    , o.tipofrete, o.adicfrete
    FROM VDORCAMENTO O, VDVENDEDOR VE WHERE O.CODORC=:ICODORC
    AND O.CODFILIAL=:ICODFILIAL AND O.CODEMP=:ICODEMP
    AND VE.CODEMP=O.CODEMP AND VE.CODFILIAL=O.CODFILIALVD
    AND VE.CODVEND=O.CODVEND INTO
           :ICODFILIALVD,:ICODVEND,:ICODFILIALCL,:ICODCLI
           ,:ICODFILIALPG,:ICODPLANOPAG,DPERCCOMVEND,:ICODCLCOMIS
           ,:ICODFILIALCM,:vlrdescorc, :VLRFRETEORC
           ,:CODEMPTN, :CODFILIALTN, :CODTRAN
           , :TIPOFRETE, :adicfrete;

  INSERT INTO VDVENDA (
    CODEMP,CODFILIAL,CODVENDA,TIPOVENDA,CODEMPVD,CODFILIALVD,CODVEND,CODEMPCL,CODFILIALCL,CODCLI,
    CODEMPPG,CODFILIALPG,CODPLANOPAG,CODEMPSE,CODFILIALSE,SERIE,CODEMPTM,CODFILIALTM,CODTIPOMOV,
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM,vlrdescvenda)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    :DTSAIDAVENDA,CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC
    );

  if ( (:codtran is not null) or (:vlrfreteorc>0) )  then
  begin
     insert into vdfretevd (codemp, codfilial, tipovenda, codvenda, codemptn
        , codfilialtn, codtran, tipofretevd, vlrfretevd, adicfretevd
        , placafretevd, vlrsegfretevd, pesobrutvd, pesoliqvd
        , espfretevd, marcafretevd, qtdfretevd, uffretevd
         )
        values (:icodemp, :icodfilialvd, :stipovenda, :icodvenda, :codemptn
        , :codfilialtn, :codtran, :tipofrete,  :vlrfreteorc, :adicfrete
        , '***-***', 0, 0, 0
        , 'Volume', '*', 0, '**' );

  end

  IRET = ICODVENDA;

  SUSPEND;
END
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

/* Alter (VDCOPIACLIENTE) */
ALTER PROCEDURE VDCOPIACLIENTE(ICODCLI INTEGER,
IDOCUMENTO VARCHAR(14),
ICODEMP INTEGER,
ICODFILIAL INTEGER)
 RETURNS(ICOD INTEGER)
 AS
declare variable inovocod integer;
begin
   SELECT MAX(CODCLI)+1 FROM VDCLIENTE
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO INOVOCOD;

    INSERT INTO VDCLIENTE (CODEMP, CODFILIAL, CODCLI, RAZCLI, NOMECLI, CODEMPCC, CODFILIALCC,
        CODCLASCLI, CODEMPVD, CODFILIALVD, CODVEND, CODEMPTC, CODFILIALTC, CODTIPOCOB, CODEMPPG,
        CODFILIALPG, CODPLANOPAG, CODEMPTN, CODFILIALTN, CODTRAN, CODEMPBO, CODFILIALBO, CODBANCO,
        CODEMPSR, CODFILIALSR, CODSETOR, CODEMPTI, CODFILIALTI, CODTIPOCLI, CODTPCRED, CODFILIALTR,
        CODEMPTR, CODFISCCLI, CODEMPFC, CODFILIALFC, CODEMPEC, CODFILIALEC, CODTBEC, CODITTBEC, CODEMPHP,
        CODFILIALHP, CODHIST, CODEMPCB, CODFILIALCB, CODCARTCOB, DATACLI, PESSOACLI, ATIVOCLI, CNPJCLI,
        INSCCLI, CPFCLI, RGCLI, SSPCLI, ENDCLI, NUMCLI, COMPLCLI, BAIRCLI, CIDCLI, UFCLI, CEPCLI, DDDCLI,
        FONECLI, RAMALCLI, DDDFAXCLI, FAXCLI, EMAILCLI, EMAILCOB, EMAILENT, EMAILNFECLI, CONTCLI, ENDCOB,
        NUMCOB, COMPLCOB, BAIRCOB, CIDCOB, UFCOB, CEPCOB, DDDFONECOB, FONECOB, DDDFAXCOB, FAXCOB, ENDENT,
        NUMENT, COMPLENT, BAIRENT, CIDENT, UFENT, CEPENT, DDDFONEENT, FONEENT, DDDFAXENT, FAXENT, OBSCLI,
        AGENCIACLI, CODEMPPQ, CODFILIALPQ, CODPESQ, INCRACLI, DTINITR, DTVENCTOTR, NIRFCLI, SIMPLESCLI, DDDCELCLI,
        CELCLI, NATCLI, UFNATCLI, TEMPORESCLI, APELIDOCLI, SITECLI, CODCONTDEB, CODCONTCRED, CODCLICONTAB,
        FOTOCLI, IMGASSCLI, CODMUNIC, SIGLAUF, CODPAIS, CODMUNICENT, SIGLAUFENT, CODPAISENT, CODMUNICCOB,
        SIGLAUFCOB, CODPAISCOB, CODEMPUC, CODFILIALUC, CODUNIFCOD, SUFRAMACLI, PRODRURALCLI, CTOCLI, CODCNAE,
        INSCMUNCLI, PERCDESCCLI, CONTCLICOB, CONTCLIENT, DESCIPI)
        SELECT :ICODEMP, :ICODFILIAL, :INOVOCOD, RAZCLI, NOMECLI, CODEMPCC, CODFILIALCC, CODCLASCLI, CODEMPVD,
            CODFILIALVD, CODVEND, CODEMPTC, CODFILIALTC, CODTIPOCOB, CODEMPPG, CODFILIALPG, CODPLANOPAG, CODEMPTN,
            CODFILIALTN, CODTRAN, CODEMPBO, CODFILIALBO, CODBANCO, CODEMPSR, CODFILIALSR, CODSETOR, CODEMPTI,
            CODFILIALTI, CODTIPOCLI, CODTPCRED, CODFILIALTR, CODEMPTR, CODFISCCLI, CODEMPFC, CODFILIALFC, CODEMPEC,
            CODFILIALEC, CODTBEC, CODITTBEC, CODEMPHP, CODFILIALHP, CODHIST, CODEMPCB, CODFILIALCB, CODCARTCOB, DATACLI,
            PESSOACLI, ATIVOCLI,

            CASE PESSOACLI
                WHEN 'J' THEN :IDOCUMENTO
            ELSE NULL
            END,

            INSCCLI,

            CASE PESSOACLI
                WHEN 'F' THEN :IDOCUMENTO
            ELSE NULL
            END,

            RGCLI, SSPCLI, ENDCLI, NUMCLI, COMPLCLI, BAIRCLI, CIDCLI,
            UFCLI, CEPCLI, DDDCLI, FONECLI, RAMALCLI, DDDFAXCLI, FAXCLI, EMAILCLI, EMAILCOB, EMAILENT, EMAILNFECLI,
            CONTCLI, ENDCOB, NUMCOB, COMPLCOB, BAIRCOB, CIDCOB, UFCOB, CEPCOB, DDDFONECOB, FONECOB, DDDFAXCOB, FAXCOB,
            ENDENT, NUMENT, COMPLENT, BAIRENT, CIDENT, UFENT, CEPENT, DDDFONEENT, FONEENT, DDDFAXENT, FAXENT, OBSCLI,
            AGENCIACLI, CODEMPPQ, CODFILIALPQ, CODPESQ, INCRACLI, DTINITR, DTVENCTOTR, NIRFCLI, SIMPLESCLI, DDDCELCLI,
            CELCLI, NATCLI, UFNATCLI, TEMPORESCLI, APELIDOCLI, SITECLI, CODCONTDEB, CODCONTCRED, CODCLICONTAB, FOTOCLI,
            IMGASSCLI, CODMUNIC, SIGLAUF, CODPAIS, CODMUNICENT, SIGLAUFENT, CODPAISENT, CODMUNICCOB, SIGLAUFCOB, CODPAISCOB,
            CODEMPUC, CODFILIALUC, CODUNIFCOD, SUFRAMACLI, PRODRURALCLI, CTOCLI, CODCNAE, INSCMUNCLI, PERCDESCCLI, CONTCLICOB,
            CONTCLIENT, DESCIPI
            FROM VDCLIENTE  VC WHERE VC.CODEMP=:ICODEMP AND VC.CODFILIAL=:ICODFILIAL AND VC.CODCLI = :ICODCLI ;

     ICOD = INOVOCOD;

  suspend;
end
^

SET TERM ; ^

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

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSPED02 POSITION 152;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCLASSPED POSITION 153;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGECLI POSITION 154;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGEFOR POSITION 155;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGETRANSP POSITION 156;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOMAVOLUMES POSITION 157;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACEP POSITION 158;

ALTER TABLE SGPREFERE1 ALTER COLUMN URLWSCEP POSITION 159;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSCP POSITION 160;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS01CP POSITION 161;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS02CP POSITION 162;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS03CP POSITION 163;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS04CP POSITION 164;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEPF POSITION 165;

ALTER TABLE SGPREFERE1 ALTER COLUMN CREDICMSSIMPLES POSITION 166;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMS POSITION 167;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMS POSITION 168;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSICMSSIMPLES POSITION 169;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACOMISVENDAORC POSITION 170;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACODUNIF POSITION 171;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV9 POSITION 172;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT9 POSITION 173;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT9 POSITION 174;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJP POSITION 175;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJP POSITION 176;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJP POSITION 177;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJR POSITION 178;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJR POSITION 179;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJR POSITION 180;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDR POSITION 181;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDR POSITION 182;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDR POSITION 183;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDC POSITION 184;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDC POSITION 185;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDC POSITION 186;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERAPAGEMIS POSITION 187;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCAFINCONTR POSITION 188;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCARMACONTR POSITION 189;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECPRE POSITION 190;

ALTER TABLE SGPREFERE1 ALTER COLUMN VISUALIZALUCR POSITION 191;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSNFE POSITION 192;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFE POSITION 193;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFELIN POSITION 194;

ALTER TABLE SGPREFERE1 ALTER COLUMN FORMATODANFE POSITION 195;

ALTER TABLE SGPREFERE1 ALTER COLUMN AMBIENTENFE POSITION 196;

ALTER TABLE SGPREFERE1 ALTER COLUMN PROCEMINFE POSITION 197;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERPROCNFE POSITION 198;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICNFE POSITION 199;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTONFE POSITION 200;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFADPRODNFE POSITION 201;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPNF POSITION 202;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALNF POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMAILNF POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN EXIBEPARCOBSDANFE POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERSAONFE POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN REGIMETRIBNFE POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 211;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 212;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 213;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 214;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 215;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 216;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOT POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOAPROV POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFT POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFT POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFORFT POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOMIS POSITION 222;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESPECIALCOMIS POSITION 223;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTS POSITION 224;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVS POSITION 225;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTS POSITION 226;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPSV POSITION 227;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALSV POSITION 228;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAGSV POSITION 229;

ALTER TABLE SGPREFERE1 ALTER COLUMN ARREDPRECO POSITION 230;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPC POSITION 231;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPC POSITION 232;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANPC POSITION 233;

ALTER TABLE SGPREFERE1 ALTER COLUMN TPNOSSONUMERO POSITION 234;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMPDOCBOL POSITION 235;

ALTER TABLE SGPREFERE1 ALTER COLUMN FECHACAIXA POSITION 236;

ALTER TABLE SGPREFERE1 ALTER COLUMN FECHACAIXAAUTO POSITION 237;

ALTER TABLE SGPREFERE1 ALTER COLUMN NUMDIGIDENTTIT POSITION 238;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICEFD POSITION 239;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTOEFD POSITION 240;

ALTER TABLE SGPREFERE1 ALTER COLUMN REVALIDARLOTECOMPRA POSITION 241;

ALTER TABLE SGPREFERE1 ALTER COLUMN ENCORCPROD POSITION 242;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIM POSITION 243;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIM POSITION 244;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVIM POSITION 245;

ALTER TABLE SGPREFERE1 ALTER COLUMN COMISSAODESCONTO POSITION 246;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHC POSITION 247;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHC POSITION 248;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTCNAB POSITION 249;

ALTER TABLE SGPREFERE1 ALTER COLUMN ALINHATELALANCA POSITION 250;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDACONSUM POSITION 251;

ALTER TABLE SGPREFERE1 ALTER COLUMN CVPROD POSITION 252;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERIFPROD POSITION 253;

ALTER TABLE SGPREFERE1 ALTER COLUMN RMAPROD POSITION 254;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPROD POSITION 255;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIG POSITION 256;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIG POSITION 257;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODIMG POSITION 258;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSITVENDAPED POSITION 259;

ALTER TABLE SGPREFERE1 ALTER COLUMN FATORCPARC POSITION 260;

ALTER TABLE SGPREFERE1 ALTER COLUMN APROVORCFATPARC POSITION 261;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQSEQICP POSITION 262;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQSEQIVD POSITION 263;

ALTER TABLE SGPREFERE1 ALTER COLUMN UTILORDCPINT POSITION 264;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICEPC POSITION 265;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTOEPC POSITION 266;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMPLOTENFE POSITION 267;

ALTER TABLE SGPREFERE1 ALTER COLUMN TOTCPSFRETE POSITION 268;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDENTCLIBCO POSITION 269;

ALTER TABLE SGPREFERE1 ALTER COLUMN QTDDESC POSITION 270;

ALTER TABLE SGPREFERE1 ALTER COLUMN LOCALSERV POSITION 271;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDPRODQQCLAS POSITION 272;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPVD POSITION 273;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALVD POSITION 274;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODVEND POSITION 275;

ALTER TABLE SGPREFERE1 ALTER COLUMN PADRAONFE POSITION 276;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPME POSITION 277;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALME POSITION 278;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSVENDA POSITION 279;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOEMISSAONFE POSITION 280;

ALTER TABLE SGPREFERE1 ALTER COLUMN CCNFECP POSITION 281;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICICMSTOTNOTA POSITION 282;

ALTER TABLE SGPREFERE1 ALTER COLUMN UTILIZATBCALCCA POSITION 283;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABCOMPRACOMPL POSITION 284;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPIC POSITION 285;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALIC POSITION 286;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVIC POSITION 287;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCNATCOMPL POSITION 288;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABLOGPAGAR POSITION 289;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABLOGRECEBER POSITION 290;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTENDENTVD POSITION 291;

ALTER TABLE SGPREFERE1 ALTER COLUMN USACLISEQ POSITION 292;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQDESCCOMPORC POSITION 293;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOORC POSITION 294;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQDESCCOMPVD POSITION 295;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOVD POSITION 296;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESABDESCFECHAVD POSITION 297;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESABDESCFECHAORC POSITION 298;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERMITBAIXAPARCJDM POSITION 299;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQCOMISSORC POSITION 300;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQCOMISSVD POSITION 301;

ALTER TABLE SGPREFERE1 ALTER COLUMN CALCPRECOG POSITION 302;

ALTER TABLE SGPREFERE1 ALTER COLUMN ENDERECOOBRIGCLI POSITION 303;

ALTER TABLE SGPREFERE1 ALTER COLUMN ENTREGAOBRIGCLI POSITION 304;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERIODOCONSCH POSITION 305;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPEDVD POSITION 306;

ALTER TABLE SGPREFERE1 ALTER COLUMN AGENDAFPRINCIPAL POSITION 307;

ALTER TABLE SGPREFERE1 ALTER COLUMN ATUALIZAAGENDA POSITION 308;

ALTER TABLE SGPREFERE1 ALTER COLUMN TEMPOATUAGENDA POSITION 309;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOLDTSAIDA POSITION 310;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 311;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 312;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 313;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 314;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 315;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 316;


COMMIT WORK;
