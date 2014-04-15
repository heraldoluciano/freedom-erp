/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.8.fdb' USER 'SYSDBA' PASSWORD '123654';

SET AUTODDL ON;

ALTER TABLE SGPREFERE8 ADD OBSPADOC VARCHAR(500);

/* Alter View (Drop, Create)... */
/* Drop altered view: ATATENDIMENTOVW01 */
/* AssignEmptyBody proc */
SET TERM ^ ;

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

/* Create altered view: ATATENDIMENTOVW01 */
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
select a.codemp, a.codfilial, a.codatendo, 
  a.codempae, a.codfilialae, a.codatend, ate.nomeatend, ate.partpremiatend, ate.codempep, codfilialep, matempr,
  a.codempea, a.codfilialea, a.codespec, e.descespec, 
  a.codempct, a.codfilialct, a.codcontr, ct.desccontr, a.coditcontr, 
  a.codempta, a.codfilialta, a.codtarefa, ct.tpcobcontr,
  extract(year from a.dataatendo) anoatendo, extract(month from a.dataatendo) mesatendo, 
  ict.qtditcontr, ict.vlritcontr, ict.vlritcontrexced, ct.dtinicio,
  a.statusatendo, c.razcli, c.nomecli, c.codcli, c.codemp, c.codfilial,
  a.codempch, a.codfilialch, a.codchamado, ch.descchamado,
  a.codempto, a.codfilialto, a.codtpatendo, ta.desctpatendo,
  a.obsatendo, a.dataatendo, a.dataatendofin, a.horaatendo, a.horaatendofin,
  e.pgcomiespec, e.cobcliespec, e.contmetaespec, e.mrelcobespec, e.bhespec,
  e.tempomincobespec, e.tempomaxcobespec, e.perccomiespec, ((a.horaatendofin-a.horaatendo) / 60) TOTALMIN,
  a.sitrevatendo,
  ct.sitcontr, ct.descsitcontr, ct.dtprevfin, ta.tipoatendo, a.docatendo
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

/* Drop altered view: ATATENDIMENTOVW02 */
/* Create altered view: ATATENDIMENTOVW02 */
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
 A.CODEMPCT, A.CODFILIALCT, A.CODCONTR, A.CODITCONTR, A.CODEMPTA, A.CODFILIALTA, A.CODTAREFA, A.TPCOBCONTR,
 A.ANOATENDO, A.MESATENDO,
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

/* Drop altered view: ATATENDIMENTOVW03 */
/* Create altered view: ATATENDIMENTOVW03 */
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

/* Alter Procedure... */
/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.9 (29/03/2012)';
    suspend;
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

/* Create Views... */
/* Create view: ATATENDIMENTOVW04 (ViwData.CreateDependDef) */
SET TERM ; ^

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


/* Alter exist trigger... */
SET TERM ^ ;

ALTER TRIGGER FNITPAGARTGBU
AS
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE ICODFOR INTEGER;
  DECLARE VARIABLE ICODEMPFR INTEGER;
  DECLARE VARIABLE ICODFILIALFR INTEGER;
  DECLARE VARIABLE CODEMPLC INTEGER;
  DECLARE VARIABLE CODFILIALLC SMALLINT;
  DECLARE VARIABLE CODLANCA INTEGER;
  DECLARE VARIABLE COUNTLANCA INTEGER;
  DECLARE VARIABLE VLRLANCA NUMERIC(15,5);
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

       IF ( (old.MULTIBAIXA IS NULL) OR (old.MULTIBAIXA='N') ) THEN
       BEGIN        
          DELETE FROM FNSUBLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND 
              CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL;
          DELETE FROM FNLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND 
              CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL;
       END
       ELSE 
       BEGIN
          SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND 
                CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA;
          DELETE FROM FNSUBLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND 
             CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL; 
          SELECT VLRLANCA FROM FNLANCA
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA
             INTO :VLRLANCA;
          IF (:VLRLANCA=0) THEN 
          BEGIN
             DELETE FROM FNLANCA 
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA;
          END 
       END

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

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNSUBLANCA') INTO :CODFILIALLC;
     SELECT COUNT (CODLANCA) FROM FNSUBLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG
           AND CODEMPPG= new.CODEMP AND CODFILIALPG=new.CODFILIAL
           AND CODEMP=new.CODEMP AND CODFILIAL = :CODFILIALLC INTO :COUNTLANCA;

     new.VLRITPAG = new.VLRPARCITPAG - new.VLRDESCITPAG - new.VLRDEVITPAG + new.VLRJUROSITPAG + new.VLRMULTAITPAG + new.VLRADICITPAG;

     IF ( new.STATUSITPAG = 'P1' ) THEN
     BEGIN
         new.VLRAPAGITPAG = new.VLRITPAG - new.VLRPAGOITPAG;
     END
  
     if( :countlanca <= 1 ) then
     begin
         if (new.VLRAPAGITPAG < 0) then /* se o valor a pagar for menor que zero */
           new.VLRAPAGITPAG = 0;  /* então valor a pagar será zero */
         if ( (new.VLRAPAGITPAG=0) AND (new.VLRITPAG>0) ) then /* se o valor a pagar for igual a zero e existir valor na parcela*/
           new.STATUSITPAG = 'PP';  /* então o status será PP(pagamento completo) */
         else if ( (new.VLRPAGOITPAG>0) AND (new.VLRITPAG>0) ) then /* caso contrário e o valor pago maior que zero e existir valor na parcela*/
           new.STATUSITPAG = 'PL'; /*  então o status será PL(pagamento parcial) */
     end

     IF ((old.STATUSITPAG='P1' AND new.STATUSITPAG in ('PP','PL')) OR
            (old.STATUSITPAG in ('PL') AND new.STATUSITPAG in ('PP','PL') AND new.VLRPAGOITPAG > 0) ) THEN
     BEGIN
       /* faz o lançamento */
       SELECT CODFOR,CODEMPFR,CODFILIALFR FROM FNPAGAR WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL AND CODPAG=new.CODPAG
         INTO ICODFOR,ICODEMPFR,ICODFILIALFR;

       IF(new.multibaixa = 'N')THEN
       BEGIN
           EXECUTE PROCEDURE FNADICLANCASP02(new.CodPag,new.NParcPag,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODFOR,:ICODEMPFR,:ICODFILIALFR,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.AnoCC,new.CodCC,new.CODEMPCC,new.CODFILIALCC, new.DTCOMPITPAG,
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag);
       END

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
     ELSE IF (new.VLRPAGOITPAG < 0 )THEN
     BEGIN
         new.VLRPAGOITPAG = new.VLRPAGOITPAG * -1;
         new.VLRAPAGITPAG = new.VLRAPAGITPAG + new.VLRPAGOITPAG;
         new.VLRPAGOITPAG = old.VLRPAGOITPAG - new.VLRPAGOITPAG;
     END
     ELSE IF ((old.STATUSITPAG='PP') AND (new.STATUSITPAG='PP')) THEN
     BEGIN
        EXCEPTION FNPAGAREX01;
     END

   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGBU
AS

  DECLARE VARIABLE SCODFILIALPF SMALLINT;
  DECLARE VARIABLE CCOMISPDUPL CHAR(1);
  DECLARE VARIABLE NVLRPARCREC NUMERIC(15, 5);
  DECLARE VARIABLE NVLRCOMIREC NUMERIC(15, 5);
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
  declare variable seqnossonumero int;
  DECLARE VARIABLE CODFILIALLC SMALLINT;
  DECLARE VARIABLE COUNTLANCA INTEGER;
BEGIN
  IF (new.EMMANUT IS NULL) THEN   /* Evita flag de manutenÃ§Ã£o nulo */
     new.EMMANUT='N';

  IF ( new.ALTUSUITREC IS NULL ) THEN /* Para nÃ£o permitir flag de usuÃ¡rio nulo */
     new.ALTUSUITREC = 'S';

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
     new.DTALT=cast('now' AS DATE);
     new.IDUSUALT=USER;
     new.HALT = cast('now' AS TIME);

     IF ( (new.DTPAGOITREC is not null) AND (new.DTLIQITREC is null) ) THEN
     BEGIN
        new.DTLIQITREC = new.DTPAGOITREC;
     END

     SELECT ESTITRECALTDTVENC FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;

     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC IN ('R1', 'RR')) ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       IF(new.STATUSITREC != 'RR')THEN
       BEGIN
        new.STATUSITREC = 'R1';
       END
       new.VLRPAGOITREC = 0;
     END
     ELSE IF ( (old.STATUSITREC='R1') AND ( new.STATUSITREC='CR' ) ) THEN
     BEGIN
        IF ( (new.OBSITREC IS NULL) OR (rtrim(new.OBSITREC)='') ) THEN
        BEGIN
           EXCEPTION FNITRECEBEREX02;
        END
        new.VLRCANCITREC = new.VLRAPAGITREC;
        new.VLRPARCITREC = 0;
        new.VLRDESCITREC = 0;
        new.VLRJUROSITREC = 0;
        new.VLRDEVITREC = 0;
        new.VLRITREC = 0;
     END

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNSUBLANCA') INTO :CODFILIALLC;
     SELECT COUNT (CODLANCA) FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC= new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL = :CODFILIALLC INTO :COUNTLANCA;

     new.VLRITREC = new.VLRPARCITREC - new.VLRDESCITREC - new.VLRDEVITREC + new.VLRJUROSITREC + new.VLRMULTAITREC;
     new.VLRAPAGITREC = new.VLRITREC - new.VLRPAGOITREC;
     if (new.VLRAPAGITREC < 0 or new.VLRAPAGITREC is null ) then /* se o valor a pagar for maior que zero */
        new.VLRAPAGITREC = 0;  /* entÃ£o valor a pagar serÃ¡ zero */

     if(:countlanca <= 1)then
     begin
        if ( (new.VLRAPAGITREC=0) AND (new.STATUSITREC<>'CR') ) then /* se o valor a pagar for igual a zero */
            new.STATUSITREC = 'RP';  /* entÃ£o o status serÃ¡ RP(pagamento completo) */
        else if (new.VLRPAGOITREC>0) then /* caso contrÃ¡rio e o valor pago maior que zero */
            new.STATUSITREC = 'RL'; /*  entÃ£o o status serÃ¡ RL(pagamento parcial) */
     end
     /*
       Esta seÃ§Ã£o Ã© destinada e ajustar as comissÃµes conforme os valores de parcelas
       caso o preferÃªncias esteja ajustado para isso.
     */
     IF ( (new.VLRPARCITREC!=old.VLRPARCITREC) AND (new.VLRPARCITREC!=0) ) THEN
     BEGIN
        SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :SCODFILIALPF;
        SELECT P1.COMISPDUPL  FROM SGPREFERE1 P1
            WHERE P1.CODEMP=new.CODEMP AND P1.CODFILIAL=:SCODFILIALPF INTO :CCOMISPDUPL;
        IF (CCOMISPDUPL='S') THEN
        BEGIN
           SELECT V.VLRLIQVENDA, R.VLRCOMIREC FROM FNRECEBER R, VDVENDA V
             WHERE R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND
                 R.CODREC=new.CODREC AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND
                 V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA INTO :NVLRPARCREC, :NVLRCOMIREC;
           IF (NVLRPARCREC!=0) THEN
             new.VLRCOMIITREC = cast( new.VLRPARCITREC * :NVLRCOMIREC / :NVLRPARCREC as NUMERIC(15, 5) );
        END
     END
     IF ((old.IMPRECIBOITREC='N') AND (new.IMPRECIBOITREC='S') AND (new.RECIBOITREC IS NULL)) THEN
     BEGIN
        SELECT ISEQ FROM SPGERANUM(new.CODEMP,new.CODFILIAL,'RB') INTO new.RECIBOITREC;
     END
     /*AlteraÃ§Ã£o das datas de entrada e saida do estado de 'em cobranÃ§a'*/
     IF (new.RECEMCOB='S') THEN
     BEGIN
       new.DTINIEMCOB=CURRENT_DATE;
       new.DTFIMEMCOB=NULL;
     END
     ELSE IF (new.RECEMCOB='N') THEN
     BEGIN
       new.DTFIMEMCOB=CURRENT_DATE;
     END
     if(new.dtprevitrec is null) then
       new.dtprevitrec = new.dtvencitrec;

    --Buscando sequencial caso informaÃ§Ãµes de banco e carteira tenham sido alteradas...

       if ( (old.codbanco is null or old.codcartcob is null or old.numconta is null )
             or
            (new.codbanco != old.codbanco or new.codcartcob != old.codcartcob or new.numconta != old.numconta)
             and
            (new.codbanco is not null and new.codcartcob is not null and new.numconta is not null ) ) then

       begin

           seqnossonumero = 0;

          select seqnossonumero
          from fngeraseqnossonumero( new.codempbo, new.codfilialbo, new.codbanco, new.codempcb, new.codfilialcb, new.codcartcob, new.codempca, new.codfilialca, new.numconta)
          into :seqnossonumero;

          if (:seqnossonumero is not null and :seqnossonumero >0 ) then
          begin
             new.seqnossonumero = :seqnossonumero;
          end

       end
   end
end
^

/* Alter Procedure... */
/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.9 (29/03/2012)';
    suspend;
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

SET TERM ; ^

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

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPPP POSITION 15;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALPP POSITION 16;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODPLANOPAG POSITION 17;

ALTER TABLE SGPREFERE8 ALTER COLUMN GERACHAMADOOS POSITION 18;

ALTER TABLE SGPREFERE8 ALTER COLUMN USAPRECOPECASERV POSITION 19;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPDS POSITION 20;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALDS POSITION 21;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPOMOVDS POSITION 22;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPSE POSITION 23;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALSE POSITION 24;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODPRODSE POSITION 25;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPTE POSITION 26;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALTE POSITION 27;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTIPOEXPED POSITION 28;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODEMPTN POSITION 29;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODFILIALTN POSITION 30;

ALTER TABLE SGPREFERE8 ALTER COLUMN CODTRAN POSITION 31;

ALTER TABLE SGPREFERE8 ALTER COLUMN SINCTICKET POSITION 32;

ALTER TABLE SGPREFERE8 ALTER COLUMN SOLCPHOMOLOGFOR POSITION 33;

ALTER TABLE SGPREFERE8 ALTER COLUMN UTILRENDACOT POSITION 34;

ALTER TABLE SGPREFERE8 ALTER COLUMN PERMITDOCCOLDUPL POSITION 35;

ALTER TABLE SGPREFERE8 ALTER COLUMN OBSPADOC POSITION 36;

ALTER TABLE SGPREFERE8 ALTER COLUMN DTINS POSITION 37;

ALTER TABLE SGPREFERE8 ALTER COLUMN HINS POSITION 38;

ALTER TABLE SGPREFERE8 ALTER COLUMN IDUSUINS POSITION 39;

ALTER TABLE SGPREFERE8 ALTER COLUMN DTALT POSITION 40;

ALTER TABLE SGPREFERE8 ALTER COLUMN HALT POSITION 41;

ALTER TABLE SGPREFERE8 ALTER COLUMN IDUSUALT POSITION 42;

/* Create(Add) Crant */
GRANT SELECT ON ATATENDIMENTOVW01 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW02 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW02 TO PROCEDURE VDCONTRATOTOTSP;

GRANT SELECT ON ATATENDIMENTOVW03 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW04 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW06 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW08 TO ADM;



COMMIT WORK;
