/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.7.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

SET AUTODDL ON;

ALTER TABLE CPPRODFOR DROP CONSTRAINT CPPRODFORFKSGFILIA;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.7.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE CPPRODFOR DROP CONSTRAINT CPPRODFORFKCPFORNE;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.7.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

/* Drop Constraints... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.7.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE CPPRODFOR DROP CONSTRAINT CPPRODFORPK;



/* Empty trigger body before drop... */
SET TERM ^ ;

ALTER TRIGGER CPITSOLTGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Drop Trigger... */
SET TERM ; ^

DROP TRIGGER CPITSOLTGBU;


ALTER TABLE CPPRODFOR ADD CODEMPFR INTEGER NOT NULL;

ALTER TABLE CPPRODFOR ADD CODFILIALFR SMALLINT NOT NULL;

ALTER TABLE SGFILIAL ADD INDNATPJFILIAL CHAR(2);

ALTER TABLE SGFILIAL ADD CODINCTRIB CHAR(1);

ALTER TABLE SGFILIAL ADD INDAPROCRED CHAR(1);

ALTER TABLE SGFILIAL ADD CODTIPOCONT CHAR(1);

ALTER TABLE SGFILIAL ADD INDREGCUM CHAR(1);

ALTER TABLE SGPREFERE1 ADD KEYLICEPC VARCHAR(500);

ALTER TABLE SGPREFERE1 ADD DTVENCTOEPC DATE;

ALTER TABLE SGPREFERE8 ADD SOLCPHOMOLOGFOR CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE8 ADD UTILRENDACOT CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE SGPREFERE8 ADD PERMITDOCCOLDUPL CHAR(1) DEFAULT 'S' NOT NULL;

/* Create Table... */
CREATE TABLE CPHOMOLOGFOR(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODFOR INTEGER NOT NULL,
ISHFOR CHAR(1) DEFAULT 'S' NOT NULL,
DTHFOR DATE DEFAULT 'now' NOT NULL,
OBSHFOR VARCHAR(2000) DEFAULT '' NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);


CREATE TABLE CPITSOLITSOL(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODSOL INTEGER NOT NULL,
CODITSOL SMALLINT NOT NULL,
CODEMPSN INTEGER NOT NULL,
CODFILIALSN SMALLINT NOT NULL,
CODSOLN INTEGER NOT NULL,
CODITSOLN SMALLINT NOT NULL,
DTITSOLIS DATE DEFAULT 'now' NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(128) DEFAULT USER NOT NULL,
DTALT DATE,
HALT TIME,
IDUSUALT CHAR(128));


CREATE TABLE CRTAREFAPER(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODTAREFA INTEGER NOT NULL,
ANOTPER SMALLINT NOT NULL,
MESTPER SMALLINT NOT NULL,
DTINIPER DATE NOT NULL,
DTFIMPER DATE NOT NULL,
TEMPOESTTAREFA VARCHAR(10) DEFAULT '00:00' NOT NULL,
TEMPODECTAREFA NUMERICDN DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT VARCHAR(128) DEFAULT USER);


CREATE TABLE PEBATIDALOG(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
DTBAT DATE NOT NULL,
HBAT TIME NOT NULL,
CODEMPEP INTEGER NOT NULL,
CODFILIALEP SMALLINT NOT NULL,
MATEMPR INTEGER NOT NULL,
SEQLOG SMALLINT NOT NULL,
TIPOLOG CHAR(1) DEFAULT 'D' NOT NULL,
TIPOBAT CHAR(1) DEFAULT 'M' NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL,
IDUSULOG VARCHAR(128) DEFAULT USER NOT NULL,
DTINSLOG DATE DEFAULT 'now' NOT NULL,
HINSLOG DATE DEFAULT 'now' NOT NULL);



/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE VDCONTRATOTOTSP(CODEMPCT INTEGER,
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


/* Create Primary Key... */
SET TERM ; ^

ALTER TABLE CPHOMOLOGFOR ADD CONSTRAINT CPHOMOLOGFORPK PRIMARY KEY (CODFOR,CODFILIAL,CODEMP);

ALTER TABLE CPITSOLITSOL ADD CONSTRAINT CPITSOLITSOLPK PRIMARY KEY (CODSOL,CODITSOL,CODSOLN,CODITSOLN,CODFILIAL,CODEMP,CODFILIALSN,CODEMPSN);

ALTER TABLE CPPRODFOR ADD CONSTRAINT CPPRODFORPK PRIMARY KEY (CODPROD,CODFOR,CODFILIAL,CODFILIALFR,CODEMP,CODEMPFR);

ALTER TABLE CRTAREFAPER ADD CONSTRAINT CRTAREFAPERPK PRIMARY KEY (CODTAREFA,MESTPER,ANOTPER,CODFILIAL,CODEMP);

ALTER TABLE PEBATIDALOG ADD CONSTRAINT PEBATIDALOGPK PRIMARY KEY (HBAT,DTBAT,SEQLOG,CODFILIAL,CODEMP,MATEMPR,CODEMPEP,CODFILIALEP);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.4.7.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE CPHOMOLOGFOR ADD CONSTRAINT CPHOMOLOGFORFKFOR FOREIGN KEY (CODFOR,CODFILIAL,CODEMP) REFERENCES CPFORNECED(CODFOR,CODFILIAL,CODEMP);

ALTER TABLE CPITSOLITSOL ADD CONSTRAINT CPITSOLFKCPITSOLI FOREIGN KEY (CODSOL,CODITSOL,CODFILIAL,CODEMP) REFERENCES CPITSOLICITACAO(CODSOL,CODITSOL,CODFILIAL,CODEMP);

ALTER TABLE CPITSOLITSOL ADD CONSTRAINT CPITSOLFKCPITSOLN FOREIGN KEY (CODSOLN,CODITSOLN,CODFILIALSN,CODEMPSN) REFERENCES CPITSOLICITACAO(CODSOL,CODITSOL,CODFILIAL,CODEMP);

ALTER TABLE CPPRODFOR ADD CONSTRAINT CPPRODFORFKCPFORNE FOREIGN KEY (CODFOR,CODFILIALFR,CODEMPFR) REFERENCES CPFORNECED(CODFOR,CODFILIAL,CODEMP);

ALTER TABLE CPPRODFOR ADD CONSTRAINT CPPRODFORFKEQPROD FOREIGN KEY (CODPROD,CODFILIAL,CODEMP) REFERENCES EQPRODUTO(CODPROD,CODFILIAL,CODEMP);

ALTER TABLE CRTAREFAPER ADD CONSTRAINT CRTAREFAPERFKCRTAR FOREIGN KEY (CODTAREFA,CODFILIAL,CODEMP) REFERENCES CRTAREFA(CODTAREFA,CODFILIAL,CODEMP);

/*  Empty EQGERARMAOSSP for SGRETINFOUSU(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
 BEGIN EXIT; END
^

/*  Empty EQGERARMASP for SGRETINFOUSU(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
 BEGIN EXIT; END
^

/*  Empty TKGERACAMPANHACTO for SGRETINFOUSU(param list change)  */
/* AssignEmptyBody proc */
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
 BEGIN EXIT; END
^

ALTER TRIGGER CPCOTACAOTGBD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITSOLICITACAOTGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITSOLTGBD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPSOLICITACAOTGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQITRMATGBD
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER EQRMATGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Alter empty procedure SGRETINFOUSU with new param-list */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
begin
    select icodfilial from sgretfilial(:codemp, 'SGUSUARIO') into :codfilialusu;
    codempusu=:codemp;
    select first 1 u.codempcc, u.codfilialcc, u.anocc, u.codcc,
       u.idusu, u.almoxarifeusu, u.aprovrmausu
    from sgusuario u where lower(u.idusu)=lower(:idusu) and u.codemp=:codemp and u.codfilial=:codfilialusu
    into :codempccusu, :codfilialccusu, :anoccusu, :codccusu,
    :idusus, :almoxarife, :aprovarma;

    suspend;
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

/* Alter (GERAEXPEDIENTESP) */
ALTER PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
DECLARE VARIABLE codturno smallint; 
DECLARE VARIABLE dataini date;
DECLARE VARIABLE datafer date;
DECLARE VARIABLE mesexped smallint;
DECLARE VARIABLE anotmp smallint;
DECLARE VARIABLE horasexped decimal(15,5);
DECLARE VARIABLE trabsabturno char(1);
DECLARE VARIABLE trabdomturno char(1);
DECLARE VARIABLE diasemana smallint;
BEGIN
   dataini = null;
   FOR SELECT T.CODTURNO, T.TRABSABTURNO, T.TRABDOMTURNO, 
     ((T.HINIINTTURNO-T.HINITURNO)+(T.HFIMTURNO-T.HFIMINTTURNO))/60/60 HORASEXPED 
     FROM RHTURNO T
     WHERE T.CODEMP=:codempto and T.CODFILIAL=:codfilialto
     INTO :codturno, :trabsabturno, :trabdomturno, :horasexped DO 
   begin
      DELETE FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      DELETE FROM RHEXPEDMES
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      dataini = cast( '01.01.'||cast(:anoexp as char(4)) as date);  
      anotmp = anoexp; 
      WHILE ( :anotmp=:anoexp )  DO 
      begin
         DATAFER=NULL;
         -- Verificação de sábados e domingos
         -- Expressão weekday retorna 0 para domingo e 6 para sábado
         diasemana = extract(weekday from :dataini);
         if ( ( (:trabsabturno='S') or (:diasemana<>6) ) and ( (:trabdomturno='S') or (:diasemana<>0) ) )  then
         begin
            --exception VDVENDAEX01 'Teste'||:dataini;

            SELECT F.DATAFER FROM SGFERIADO F
              WHERE F.CODEMP=:codempfe and F.CODFILIAL=:codfilialfe and F.DATAFER=:dataini and F.TRABFER='S' 
              INTO :DATAFER;
            if ( (:datafer is null) or (:datafer<>:dataini)) then
            begin
               mesexped = extract( month from :dataini);
               INSERT INTO RHEXPEDIENTE 
                        (CODEMP, CODFILIAL, CODTURNO, DTEXPED, ANOEXPED, MESEXPED, HORASEXPED)
                 VALUES (:codempto, :codfilialto, :codturno, :dataini, :anoexp, :mesexped, :horasexped);
            end
         end
         DATAINI=:DATAINI+1;
         anotmp = EXTRACT( YEAR FROM :DATAINI); 
      end
      INSERT INTO RHEXPEDMES( CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, HORASEXPED )
        SELECT CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, SUM(HORASEXPED) HORASEXPED
        FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and 
           ANOEXPED=:anoexp
        GROUP BY CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED;
   end
   suspend;
END
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

/* Alter (SGRETINFOUSU) */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
begin
    select icodfilial from sgretfilial(:codemp, 'SGUSUARIO') into :codfilialusu;
    codempusu=:codemp;
    select first 1 u.codempcc, u.codfilialcc, u.anocc, u.codcc,
       u.idusu, u.almoxarifeusu, u.aprovrmausu
    from sgusuario u where lower(u.idusu)=lower(:idusu) and u.codemp=:codemp and u.codfilial=:codfilialusu
    into :codempccusu, :codfilialccusu, :anoccusu, :codccusu,
    :idusus, :almoxarife, :aprovarma;

    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.8 (16/02/2012)';
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

    select codemp, codfilial, codatend from atatendente
            where codempus=:codempus and codfilialus=:codfilialus and idusu=:idusu
    into codempae, codfilialae, codatend;

    if(:codatend is null) then
    begin
        exception TKGERACAMANHACTO01 ' - ID: ' || idusu || ' - User: '|| user ;
    end

    -- Verifica se o contato já foi vinculado à campanha

    if ( tipocto = 'O' ) then 
    begin 
    	select seqcampcto from tkcampanhacto cc
	        where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
            	and cc.codempco=:codempco and cc.codfilialco=:codfilialco and cc.codcto=:codcto
    	into :seqcampcto;
    end
    else
    begin
    	select seqcampcto from tkcampanhacto cc
	        where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
            	and cc.codempcl=:codempco and cc.codfilialcl=:codfilialco and cc.codcli=:codcto
    	into :seqcampcto;
    end

    if( (:seqcampcto is null) or (:seqcampcto=0) ) then
    begin
        seqcampcto = 1;
        if ( tipocto = 'O' ) then 
        begin
	        insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempco, codfilialco, codcto)
		        values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
        else 
        begin
	        insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempcl, codfilialcl, codcli)
		        values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
    end
    else
    begin
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

/* Create Trigger... */
CREATE TRIGGER CPITSOLITSOL FOR CPITSOLITSOL
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^

CREATE TRIGGER PEBATIDATGAD FOR PEBATIDA
ACTIVE AFTER DELETE POSITION 0 
as
   declare variable seqlog smallint;
begin

   select max(seqlog) from pebatidalog bl
     where bl.codemp=old.codemp and bl.codfilial=old.codfilial and 
       bl.dtbat=old.dtbat and bl.hbat=old.hbat and 
       bl.codempep=old.codempep and bl.codfilialep=old.codfilialep and bl.matempr=old.matempr
    into :seqlog;
   if (:seqlog is null) then
   begin
     seqlog = 0;
   end
   seqlog = seqlog + 1;
   insert into pebatidalog (codemp, codfilial, dtbat, hbat,
        codempep, codfilialep, matempr, seqlog,
        tipobat, dtins, hins, idusuins, 
        dtalt, halt, idusualt)
      values (old.codemp, old.codfilial, old.dtbat, old.hbat,
        old.codempep, old.codfilialep, old.matempr, :seqlog,
        old.tipobat, old.dtins, old.hins, old.idusuins, 
        old.dtalt, old.halt, old.idusualt);
   
end
^

CREATE TRIGGER PEFALTATGAD FOR PEFALTA
ACTIVE AFTER DELETE POSITION 0 
AS
  declare variable codfilialat integer;
  declare variable codfilialp3 integer;
begin
  select icodfilial from sgretfilial(old.codemp, 'ATATENDIMENTO')
    into :codfilialat;
  select icodfilial from sgretfilial(old.codemp, 'SGPREFERE3')
    into :codfilialp3;
  delete from atatendimento a
    where a.codemp=old.codemp and a.codfilial=:codfilialat and
    a.dataatendo=old.dtfalta and
    exists( select * from atatendente ae, sgprefere3 p3
      left outer join atmodatendo mfi on
        mfi.codemp=p3.codempfi and mfi.codfilial=p3.codfilialfi and mfi.codmodel=p3.codmodelfi
      left outer join atmodatendo mfj on
        mfj.codemp=p3.codempfj and mfj.codfilial=p3.codfilialfj and mfj.codmodel=p3.codmodelfj
      where ae.codempep=old.codempep and ae.codfilialep=old.codfilialep and ae.matempr=old.matempr and
      ae.codemp=a.codempae and ae.codfilial=a.codfilialae and ae.codatend=a.codatend and
      p3.codemp=old.codemp and p3.codfilial=:codfilialp3 and
      ( ( mfi.codempea=a.codempea and mfi.codfilialea=a.codfilialea and mfi.codespec=a.codespec ) or
        ( mfj.codempea=a.codempea and mfj.codfilialea=a.codfilialea and mfj.codespec=a.codespec )
      )
    ) and
    ( ( extract(hour from a.horaatendo)=extract(hour from old.hinifalta) and
        extract(minute from a.horaatendo)=extract(minute from old.hinifalta) ) or
      ( extract(hour from a.horaatendo)=extract(hour from old.hfinintfalta) and
        extract(minute from a.horaatendo)=extract(minute from old.hfinintfalta) )
    ) ;

end
^


/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPCOTACAOTGBD;

SET TERM ^ ;

CREATE TRIGGER CPCOTACAOTGBD FOR CPCOTACAO
ACTIVE BEFORE DELETE POSITION 0 
AS
declare variable sUsuarioCN char(8);
declare variable sUsuarioRM char(8);
begin

   if(old.sititsol<>'PE') then
   begin
      exception eqitrma01;
   end

   select idusus from sgretinfousu(old.CODEMP, USER) into :sUsuarioCN;
   select rm.idusucot from cpcotacao rm where rm.codemp=old.codemp and rm.codfilial=old.codfilial and rm.codsol=old.codsol into :sUsuarioRM;
   if(:sUsuarioCN<>:sUsuarioRM)then
   begin
      exception eqitrma02;
   end
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPITSOLICITACAOTGBU;

SET TERM ^ ;

CREATE TRIGGER CPITSOLICITACAOTGBU FOR CPITSOLICITACAO
ACTIVE BEFORE UPDATE POSITION 0 
AS
     declare variable sRefProd VARChar(20);
     DECLARE VARIABLE ICODEMPUSU INT;
     declare variable ICODFILIALUSU smallint;
     declare variable IDUSU char(8);
begin
  /*#IBA#new.DTALT = cast('today' AS DATE);
  new.IDUSUALT = USER;
  new.HALT = cast('now' AS TIME);#IBA#*/
  IF (new.CODALMOX IS NULL) THEN
       SELECT CODEMPAX,CODFILIALAX,CODALMOX FROM EQPRODUTO WHERE
          CODEMP=new.CODEMPPD AND CODFILIAL=new.CODFILIALPD AND
          CODPROD=new.CODPROD
       INTO new.codempam, new.codfilialam,new.CODALMOX;

  IF (old.CODALMOX IS NOT NULL) THEN
  BEGIN
     IF (old.CODALMOX!=new.CODALMOX) THEN
         EXCEPTION EQALMOX01;
  END
  IF (new.CODPROD != old.CODPROD) THEN
     EXCEPTION CPITSOLICITACAOEX01;
  IF (new.REFPROD IS NULL) THEN
  BEGIN
       SELECT REFPROD FROM EQPRODUTO WHERE CODPROD=new.CODPROD
            AND CODEMP=new.CODEMPPD AND CODFILIAL = new.CODFILIALPD INTO sRefProd;
       new.REFPROD = sRefProd;
  END
  if((new.idusuaprovitsol is null) and (new.sititsol!=old.sititsol) and ((new.sititsol= 'AP') or (new.sititsol= 'AT') )) then
  begin
    SELECT CODEMPUSU,CODFILIALUSU,IDUSUS FROM sgretinfousu(new.CODEMP, USER) into :ICODEMPUSU,:ICODFILIALUSU,:IDUSU;
    new.codempua = :ICODEMPUSU;
    new.codfilialua = :ICODFILIALUSU;
    new.idusuaprovitsol = :IDUSU;
    new.dtaprovitsol = cast('now' AS DATE);
  end
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPITSOLTGBD;

SET TERM ^ ;

CREATE TRIGGER CPITSOLTGBD FOR CPITSOLICITACAO
ACTIVE BEFORE DELETE POSITION 0 
AS
declare variable sUsuarioCN char(8);
declare variable sUsuarioRM char(8);
begin

   if(old.sititsol<>'PE') then
   begin
      exception eqitrma01;
   end

   select idusus from sgretinfousu(old.CODEMP, USER) into :sUsuarioCN;
   select rm.idusu from cpsolicitacao rm where rm.codemp=old.codemp and rm.codfilial=old.codfilial and rm.codsol=old.codsol into :sUsuarioRM;
   if(:sUsuarioCN<>:sUsuarioRM)then
   begin
      exception eqitrma02;
   end
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER CPSOLICITACAOTGBU;

SET TERM ^ ;

CREATE TRIGGER CPSOLICITACAOTGBU FOR CPSOLICITACAO
ACTIVE BEFORE UPDATE POSITION 1 
as
  DECLARE VARIABLE ICODEMPUSU INT;
  declare variable ICODFILIALUSU smallint;
  declare variable IDUSU char(8);
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
  if((new.idusuaprov is null) and (new.sitsol!=old.sitsol) and ((new.sitsol= 'AP') or (new.sitsol= 'AT') )) then
  begin
    SELECT CODEMPUSU,CODFILIALUSU,IDUSUS FROM sgretinfousu(new.CODEMP, USER) into :ICODEMPUSU,:ICODFILIALUSU,:IDUSU;
    new.codempua = :ICODEMPUSU;
    new.codfilialua = :ICODFILIALUSU;
    new.idusuaprov = :IDUSU;
    new.dtaaprovsol = cast('now' AS DATE);
  end
end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQITRECMERCTGAU;

SET TERM ^ ;

CREATE TRIGGER EQITRECMERCTGAU FOR EQITRECMERC
ACTIVE AFTER UPDATE POSITION 0 
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

    -- Atualiza registros de ticket com número de série da tabela eqmovserie
    if (old.codprod <> new.codprod) then
    begin
        update eqmovserie eq set eq.codprod=new.codprod where
        eq.ticket=old.ticket and eq.coditrecmerc=old.coditrecmerc and
        eq.codfilialrc=old.codfilial and eq.codemprc=old.codemp and
        eq.codprod<>new.codprod;
    end

    if (old.numserie <> new.numserie) then
    begin
        update eqmovserie eq set eq.numserie=new.numserie where
        eq.ticket=old.ticket and eq.coditrecmerc=old.coditrecmerc and
        eq.codfilialrc=old.codfilial and eq.codemprc=old.codemp and
        eq.numserie<>new.numserie;
    end

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER EQITRMATGBD;

SET TERM ^ ;

CREATE TRIGGER EQITRMATGBD FOR EQITRMA
ACTIVE BEFORE DELETE POSITION 0 
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
SET TERM ; ^

DROP TRIGGER EQRMATGBU;

SET TERM ^ ;

CREATE TRIGGER EQRMATGBU FOR EQRMA
ACTIVE BEFORE UPDATE POSITION 0 
as
  DECLARE VARIABLE ICODEMPUSU INT;
  declare variable ICODFILIALUSU smallint;
  declare variable IDUSU char(8);
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
  if((new.idusuaprov is null) and (new.sitaprovrma!=old.sitaprovrma)) then
  begin
    SELECT CODEMPUSU,CODFILIALUSU,IDUSUS FROM sgretinfousu(new.CODEMP, USER) into :ICODEMPUSU,:ICODFILIALUSU,:IDUSU;
    new.codempua = :ICODEMPUSU;
    new.codfilialua = :ICODFILIALUSU;
    new.idusuaprov = :IDUSU;
    new.dtaaprovrma = cast('now' AS DATE);
  end
  if((new.idusuexp is null) and (new.sitexprma!=old.sitexprma)) then
  begin
    SELECT CODEMPUSU,CODFILIALUSU,IDUSUS FROM sgretinfousu(new.CODEMP, USER) into :ICODEMPUSU,:ICODFILIALUSU,:IDUSU;
    new.codempue = :ICODEMPUSU;
    new.codfilialue = :ICODFILIALUSU;
    new.idusuexp = :IDUSU;
    new.dtaexprma = cast('now' AS DATE);
  end
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=user;
  new.HALT=cast('now' AS TIME);

end
^

/* Alter exist trigger... */
SET TERM ; ^

DROP TRIGGER VDVENDAORCTGAD;

SET TERM ^ ;

CREATE TRIGGER VDVENDAORCTGAD FOR VDVENDAORC
ACTIVE AFTER DELETE POSITION 0 
AS
begin
  UPDATE VDITORCAMENTO SET EMITITORC='N', FATITORC='N', QTDFATITORC=0 
    WHERE CODEMP=old.CODEMPOR AND CODFILIAL=old.CODFILIALOR AND
       TIPOORC=old.TIPOORC AND CODORC=old.CODORC AND CODITORC=old.CODITORC;
  UPDATE VDORCAMENTO SET STATUSORC='OL'
    WHERE CODEMP=old.CODEMPOR AND CODFILIAL=old.CODFILIALOR AND
       TIPOORC=old.TIPOORC AND CODORC=old.CODORC;
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

        update vditvenda set qtditvendacanc=qtditvenda, qtditvenda=0 where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilial=new.codfilial;

        delete from fnreceber where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilialva=new.codfilial;

        delete from vdvendaorc where codemp=new.codemp and codfilial=new.codfilial and
        codvenda=new.codvenda and tipovenda=new.tipovenda;

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

/* Alter empty procedure SGRETINFOUSU with new param-list */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
begin
    select icodfilial from sgretfilial(:codemp, 'SGUSUARIO') into :codfilialusu;
    codempusu=:codemp;
    select first 1 u.codempcc, u.codfilialcc, u.anocc, u.codcc,
       u.idusu, u.almoxarifeusu, u.aprovrmausu
    from sgusuario u where lower(u.idusu)=lower(:idusu) and u.codemp=:codemp and u.codfilial=:codfilialusu
    into :codempccusu, :codfilialccusu, :anoccusu, :codccusu,
    :idusus, :almoxarife, :aprovarma;

    suspend;
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

/* Alter (GERAEXPEDIENTESP) */
ALTER PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
DECLARE VARIABLE codturno smallint; 
DECLARE VARIABLE dataini date;
DECLARE VARIABLE datafer date;
DECLARE VARIABLE mesexped smallint;
DECLARE VARIABLE anotmp smallint;
DECLARE VARIABLE horasexped decimal(15,5);
DECLARE VARIABLE trabsabturno char(1);
DECLARE VARIABLE trabdomturno char(1);
DECLARE VARIABLE diasemana smallint;
BEGIN
   dataini = null;
   FOR SELECT T.CODTURNO, T.TRABSABTURNO, T.TRABDOMTURNO, 
     ((T.HINIINTTURNO-T.HINITURNO)+(T.HFIMTURNO-T.HFIMINTTURNO))/60/60 HORASEXPED 
     FROM RHTURNO T
     WHERE T.CODEMP=:codempto and T.CODFILIAL=:codfilialto
     INTO :codturno, :trabsabturno, :trabdomturno, :horasexped DO 
   begin
      DELETE FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      DELETE FROM RHEXPEDMES
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      dataini = cast( '01.01.'||cast(:anoexp as char(4)) as date);  
      anotmp = anoexp; 
      WHILE ( :anotmp=:anoexp )  DO 
      begin
         DATAFER=NULL;
         -- Verificação de sábados e domingos
         -- Expressão weekday retorna 0 para domingo e 6 para sábado
         diasemana = extract(weekday from :dataini);
         if ( ( (:trabsabturno='S') or (:diasemana<>6) ) and ( (:trabdomturno='S') or (:diasemana<>0) ) )  then
         begin
            --exception VDVENDAEX01 'Teste'||:dataini;

            SELECT F.DATAFER FROM SGFERIADO F
              WHERE F.CODEMP=:codempfe and F.CODFILIAL=:codfilialfe and F.DATAFER=:dataini and F.TRABFER='S' 
              INTO :DATAFER;
            if ( (:datafer is null) or (:datafer<>:dataini)) then
            begin
               mesexped = extract( month from :dataini);
               INSERT INTO RHEXPEDIENTE 
                        (CODEMP, CODFILIAL, CODTURNO, DTEXPED, ANOEXPED, MESEXPED, HORASEXPED)
                 VALUES (:codempto, :codfilialto, :codturno, :dataini, :anoexp, :mesexped, :horasexped);
            end
         end
         DATAINI=:DATAINI+1;
         anotmp = EXTRACT( YEAR FROM :DATAINI); 
      end
      INSERT INTO RHEXPEDMES( CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, HORASEXPED )
        SELECT CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, SUM(HORASEXPED) HORASEXPED
        FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and 
           ANOEXPED=:anoexp
        GROUP BY CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED;
   end
   suspend;
END
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

/* Alter (SGRETINFOUSU) */
ALTER PROCEDURE SGRETINFOUSU(CODEMP INTEGER,
IDUSU VARCHAR(128))
 RETURNS(ANOCCUSU SMALLINT,
CODEMPCCUSU INTEGER,
CODFILIALCCUSU SMALLINT,
CODEMPUSU INTEGER,
CODFILIALUSU SMALLINT,
CODCCUSU CHAR(19),
IDUSUS CHAR(8),
ALMOXARIFE CHAR(1),
APROVARMA CHAR(2))
 AS
begin
    select icodfilial from sgretfilial(:codemp, 'SGUSUARIO') into :codfilialusu;
    codempusu=:codemp;
    select first 1 u.codempcc, u.codfilialcc, u.anocc, u.codcc,
       u.idusu, u.almoxarifeusu, u.aprovrmausu
    from sgusuario u where lower(u.idusu)=lower(:idusu) and u.codemp=:codemp and u.codfilial=:codfilialusu
    into :codempccusu, :codfilialccusu, :anoccusu, :codccusu,
    :idusus, :almoxarife, :aprovarma;

    suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.8 (16/02/2012)';
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

    select codemp, codfilial, codatend from atatendente
            where codempus=:codempus and codfilialus=:codfilialus and idusu=:idusu
    into codempae, codfilialae, codatend;

    if(:codatend is null) then
    begin
        exception TKGERACAMANHACTO01 ' - ID: ' || idusu || ' - User: '|| user ;
    end

    -- Verifica se o contato já foi vinculado à campanha

    if ( tipocto = 'O' ) then 
    begin 
    	select seqcampcto from tkcampanhacto cc
	        where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
            	and cc.codempco=:codempco and cc.codfilialco=:codfilialco and cc.codcto=:codcto
    	into :seqcampcto;
    end
    else
    begin
    	select seqcampcto from tkcampanhacto cc
	        where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
            	and cc.codempcl=:codempco and cc.codfilialcl=:codfilialco and cc.codcli=:codcto
    	into :seqcampcto;
    end

    if( (:seqcampcto is null) or (:seqcampcto=0) ) then
    begin
        seqcampcto = 1;
        if ( tipocto = 'O' ) then 
        begin
	        insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempco, codfilialco, codcto)
		        values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
        else 
        begin
	        insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempcl, codfilialcl, codcli)
		        values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
    end
    else
    begin
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

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODEMPFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODFILIALFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='CODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='REFPRODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='CPPRODFOR' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='RAZFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='NOMEFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='MZFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CNPJFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INSCFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='ENDFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='NUMFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='COMPLFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='BAIRFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CEPFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CIDFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='UFFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='DDDFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='FONEFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='FAXFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='EMAILFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='WWWFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODDISTFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCPISFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCCOFINSFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCIRFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCCSOCIALFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='SIMPLESFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCSIMPLESFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODMUNIC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='SIGLAUF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODPAIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODEMPUC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODFILIALUC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODUNIFCOD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INSCMUNFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CNAEFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERCISSFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CONTRIBIPIFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='TIMBREFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='PERFILFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INDATIVFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INDNATPJFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODEMPCO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODFILIALCO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='SUFRAMA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODINCTRIB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INDAPROCRED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='CODTIPOCONT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='SGFILIAL' AND RDB$FIELD_NAME='INDREGCUM';

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
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSORCPD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TITORCTXT01';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT3';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ORDNOTA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SETORVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PREFCRED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOPREFCRED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMOEDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT4';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USACLASCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PERCPRECOCUSTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODGRUP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALGP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPGP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMARCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RGCLIOBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=42
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABFRETEVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=43
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABADICVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=44
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TRAVATMNFVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=45
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOVALIDORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=46
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLIMESMOCNPJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=47
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTBJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=48
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=49
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTJ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=50
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CNPJOBRIGCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=51
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='JUROSPOSCALC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=52
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=53
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALFR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=54
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=55
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=56
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=57
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTRAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=58
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=59
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=60
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=61
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=62
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=63
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV5';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=64
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTLOTNEG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=65
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTNEG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=66
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='NATVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=67
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IPIVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=68
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CUSTOSICMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=69
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=70
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALPG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=71
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANOPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=72
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=73
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=74
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTAB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=75
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPCE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=76
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALCE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=77
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODCLASCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=78
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDEC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=79
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDECFIN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=80
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='COMISPDUPL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=81
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=82
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=83
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV6';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=84
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=85
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=86
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VENDAMATPRIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=87
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VENDAPATRIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=88
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PEPSPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=89
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CNPJFOROBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=90
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INSCESTFOROBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=91
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCAPRODSIMILAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=92
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='MULTIALMOX';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=93
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=94
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=95
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV8';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=96
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTNEGGRUP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=97
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USATABPE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=98
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TAMDESCPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=99
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DESCCOMPPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=100
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='OBSCLIVEND';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=101
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONTESTOQ';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=102
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIASPEDT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=103
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RECALCPCVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=104
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RECALCPCORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=105
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USALAYOUTPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=106
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERIFALTPARCVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=107
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCACODPRODGEN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=108
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=109
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENREF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=110
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODBAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=111
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODFAB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=112
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FILBUSCGENCODFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=113
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCAVLRULTCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=114
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ICMSVENDA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=115
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOZERO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=116
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIMGASSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=117
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IMGASSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=118
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTCPFCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=119
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIECLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=120
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIEFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=121
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTECPFFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=122
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USANOMEVENDORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=123
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SISCONTABIL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=124
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ATBANCOIMPBOL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=125
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCODBAR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=126
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICORCOBSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=127
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=128
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=129
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMENSORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=130
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CUSTOCOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=131
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABTRANSPCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=132
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABTRANSPORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=133
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABSOLCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=134
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICFRETEBASEICM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=135
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PRECOCPREL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=136
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DESCORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=137
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='MULTICOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=138
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USUATIVCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=139
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=140
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=141
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODHISTREC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=142
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=143
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=144
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODHISTPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=145
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=146
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=147
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOCLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=148
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESTITRECALTDTVENC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=149
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LCREDGLOBAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=150
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VDMANUTCOMOBRIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=151
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=152
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCLASSPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=153
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGECLI';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=154
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGEFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=155
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAIBGETRANSP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=156
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='SOMAVOLUMES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=157
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BUSCACEP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=158
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='URLWSCEP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=159
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=160
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS01CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=161
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS02CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=162
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS03CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=163
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LABELOBS04CP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=164
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CONSISTEIEPF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=165
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CREDICMSSIMPLES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=166
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=167
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALMS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=168
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODMENSICMSSIMPLES';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=169
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERACOMISVENDAORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=170
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERACODUNIF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=171
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOV9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=172
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALT9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=173
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPT9';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=174
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=175
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=176
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANJP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=177
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=178
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=179
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANJR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=180
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=181
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=182
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANDR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=183
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=184
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=185
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANDC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=186
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERAPAGEMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=187
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LANCAFINCONTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=188
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='LANCARMACONTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=189
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CASASDECPRE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=190
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VISUALIZALUCR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=191
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CLASSNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=192
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIRNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=193
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DIRNFELIN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=194
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FORMATODANFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=195
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='AMBIENTENFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=196
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='PROCEMINFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=197
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERPROCNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=198
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='KEYLICNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=199
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTVENCTONFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=200
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFADPRODNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=201
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=202
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=203
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMAILNF';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=204
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='EXIBEPARCOBSDANFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=205
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERSAONFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=206
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='REGIMETRIBNFE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=207
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFCPDEVOLUCAO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=208
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='INFVDREMESSA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=209
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='GERARECEMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=210
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RETENSAOIMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=211
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOCUSTOLUC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=212
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TABIMPORTCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=213
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HABVLRTOTITORC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=214
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USABUSCAGENPRODCP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=215
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ADICOBSORCPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=216
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOCOT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=217
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQPRECOAPROV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=218
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=219
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=220
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOFORFT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=221
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='USAPRECOCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=222
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ESPECIALCOMIS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=223
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALTS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=224
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOVS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=225
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPTS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=226
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=227
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=228
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANOPAGSV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=229
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ARREDPRECO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=230
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=231
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=232
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODPLANPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=233
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TPNOSSONUMERO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=234
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IMPDOCBOL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=235
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FECHACAIXA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=236
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FECHACAIXAAUTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=237
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='NUMDIGIDENTTIT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=238
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='KEYLICEFD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=239
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTVENCTOEFD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=240
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='REVALIDARLOTECOMPRA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=241
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ENCORCPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=242
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=243
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=244
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODTIPOMOVIM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=245
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='COMISSAODESCONTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=246
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPHC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=247
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALHC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=248
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODHISTCNAB';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=249
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='ALINHATELALANCA';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=250
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VENDACONSUM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=251
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CVPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=252
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='VERIFPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=253
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='RMAPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=254
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='TIPOPROD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=255
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODEMPIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=256
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODFILIALIG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=257
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='CODIMG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=258
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='OBSITVENDAPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=259
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='FATORCPARC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=260
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='APROVORCFATPARC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=261
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQSEQICP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=262
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='BLOQSEQIVD';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=263
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='UTILORDCPINT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=264
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='KEYLICEPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=265
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTVENCTOEPC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=266
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=267
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=268
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=269
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=270
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=271
 WHERE RDB$RELATION_NAME='SGPREFERE1' AND RDB$FIELD_NAME='IDUSUALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=1
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=2
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIAL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=3
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=4
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALTR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=5
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPORECMERC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=6
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPCM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=7
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALCM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=8
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPORECMERCCM';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=9
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=10
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=11
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPOMOVTC';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=12
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=13
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALTO';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=14
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPORECMERCOS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=15
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPPP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=16
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALPP';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=17
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODPLANOPAG';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=18
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='GERACHAMADOOS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=19
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='USAPRECOPECASERV';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=20
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPDS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=21
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALDS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=22
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPOMOVDS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=23
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPSE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=24
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALSE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=25
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODPRODSE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=26
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPTE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=27
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALTE';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=28
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTIPOEXPED';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=29
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODEMPTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=30
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODFILIALTN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=31
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='CODTRAN';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=32
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='SINCTICKET';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=33
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='SOLCPHOMOLOGFOR';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=34
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='UTILRENDACOT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=35
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='PERMITDOCCOLDUPL';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=36
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='DTINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=37
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='HINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=38
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='IDUSUINS';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=39
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='DTALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=40
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='HALT';

UPDATE RDB$RELATION_FIELDS SET RDB$FIELD_POSITION=41
 WHERE RDB$RELATION_NAME='SGPREFERE8' AND RDB$FIELD_NAME='IDUSUALT';


COMMIT WORK;
