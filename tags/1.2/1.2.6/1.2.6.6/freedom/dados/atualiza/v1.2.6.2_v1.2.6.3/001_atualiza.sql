/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.6.2.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

SET AUTODDL ON;

/* Alter Field (Length): from 50 to 100... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=100, RDB$CHARACTER_LENGTH=100
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='FNFBNCODRET' AND  RDB$FIELD_NAME='DESCRET');


ALTER TABLE ATATENDENTE ADD ACESATDOLEROUT CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE ATATENDENTE ADD ACESATDOALTOUT CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE ATATENDENTE ADD ACESATDODELLAN CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE ATATENDENTE ADD ACESATDODELOUT CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE ATATENDENTE ADD ACESRELESTOUT CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE ATATENDIMENTO ADD SITATENDO CHAR(2);

ALTER TABLE LFITVENDA ADD VLRBASENCM DECIMAL(15,5) DEFAULT 0 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor base de cálculo para estimativa de impostos, referente a lei de transparência'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRBASENCM';

ALTER TABLE LFITVENDA ADD ALIQNACNCM DECIMAL(9,2) DEFAULT 0 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Alíquota estimada de tributos nacionais, referente a lei de transparência'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQNACNCM';

ALTER TABLE LFITVENDA ADD ALIQIMPNCM DECIMAL(9,2) DEFAULT 0 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Alíquota estimada de tributos de importação, referente a lei de transparência'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQIMPNCM';

ALTER TABLE LFITVENDA ADD VLRNACNCM DECIMAL(15,5) DEFAULT 0 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor estimado de tributos nacionais, referente a lei de transparência'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRNACNCM';

ALTER TABLE LFITVENDA ADD VLRIMPNCM DECIMAL(15,5) DEFAULT 0 NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Valor estimado de tributos de importação, referente a lei de transparência'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRIMPNCM';

ALTER TABLE LFNCM ADD ALIQNAC DECIMAL(9,2) DEFAULT 0 NOT NULL;

ALTER TABLE LFNCM ADD ALIQIMP DECIMAL(9,2) DEFAULT 0 NOT NULL;

ALTER TABLE LFTIPOFISCCLI ADD LEITRANSP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD FATORSEGESTOQ DECIMAL(9,5) DEFAULT 0 NOT NULL;

ALTER TABLE SGPREFERE1 ADD LEITRANSP CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD TIPOIMPDANFE CHAR(1) DEFAULT 'F' NOT NULL;

ALTER TABLE SGPREFERE3 ADD CODEMPMC INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALMC SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODMODELMC INTEGER;

/* Create Table... */
CREATE TABLE ATATENDOAGENDA(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODATENDO INTEGER NOT NULL,
CODEMPAG INTEGER NOT NULL,
CODFILIALAG SMALLINT NOT NULL,
TIPOAGE CHAR(5) NOT NULL,
CODAGE INTEGER NOT NULL,
CODAGD INTEGER NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);



/* Create Primary Key... */
ALTER TABLE ATATENDOAGENDA ADD CONSTRAINT ATATENDOAGENDAPK PRIMARY KEY (CODATENDO,CODEMP,CODFILIAL,CODAGE,TIPOAGE,CODAGD,CODFILIALAG,CODEMPAG);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.6.2.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE ATATENDOAGENDA ADD CONSTRAINT ATATENDOAGENDAFKAG FOREIGN KEY (CODATENDO,CODFILIAL,CODEMP) REFERENCES ATATENDIMENTO(CODATENDO,CODFILIAL,CODEMP);

ALTER TABLE ATATENDOAGENDA ADD CONSTRAINT ATATENDOAGENDAFKAT FOREIGN KEY (CODAGE,TIPOAGE,CODAGD,CODFILIALAG,CODEMPAG) REFERENCES SGAGENDA(CODAGE,TIPOAGE,CODAGD,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3ATMODMC FOREIGN KEY (CODMODELMC,CODFILIALMC,CODEMPMC) REFERENCES ATMODATENDO(CODMODEL,CODFILIAL,CODEMP);

/*  Empty LFGERALFITCOMPRASP for LFBUSCAFISCALSP02(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE LFGERALFITCOMPRASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 AS
 BEGIN EXIT; END
^

/*  Empty LFGERALFITVENDASP for LFBUSCAFISCALSP02(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE LFGERALFITVENDASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(2),
CODITVENDA SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure LFBUSCAFISCALSP02 with new param-list */
ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* Alter (ATATENDIMENTOIUSP) */
ALTER PROCEDURE ATATENDIMENTOIUSP(IU CHAR(1),
CODEMP INTEGER,
CODFILIAL SMALLINT,
CODATENDO INTEGER,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
CODTPATENDO INTEGER,
CODEMPAE INTEGER,
CODFILIALAE SMALLINT,
CODATEND INTEGER,
CODEMPSA INTEGER,
CODFILIALSA SMALLINT,
CODSETOR INTEGER,
DOCATENDO INTEGER,
DATAATENDO DATE,
DATAATENDOFIN DATE,
HORAATENDO TIME,
HORAATENDOFIN TIME,
OBSATENDO VARCHAR(10000),
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR SMALLINT,
CODEMPIR INTEGER,
CODFILIALIR SMALLINT,
CODREC INTEGER,
NPARCITREC INTEGER,
CODEMPCH INTEGER,
CODFILIALCH SMALLINT,
CODCHAMADO INTEGER,
OBSINTERNO VARCHAR(10000),
CONCLUICHAMADO CHAR(1),
CODEMPEA INTEGER,
CODFILIALEA SMALLINT,
CODESPEC INTEGER,
CODEMPUS INTEGER,
CODFILIALUS SMALLINT,
IDUSU VARCHAR(128),
STATUSATENDO CHAR(2),
CODEMPTA INTEGER,
CODFILIALTA SMALLINT,
CODTAREFA INTEGER,
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
TIPOORC CHAR(1),
CODORC INTEGER,
SITATENDO CHAR(2),
CODEMPAG INTEGER,
CODFILIALAG SMALLINT,
TIPOAGE CHAR(5),
CODAGE INTEGER,
CODAGD INTEGER)
 AS
declare variable horaatendors time;
declare variable horaatendofinrs time;
declare variable dataatendors date;
declare variable contorc integer;
BEGIN

  DATAATENDORS = NULL;

  SELECT FIRST 1 A.DATAATENDO, A.HORAATENDO, A.HORAATENDOFIN
    FROM ATATENDIMENTO A
    WHERE A.CODEMP=:CODEMP AND A.CODFILIAL=:CODFILIAL AND
        A.CODEMPAE=:CODEMPAE AND A.CODFILIALAE=:CODFILIALAE AND
        A.CODATEND=:CODATEND AND A.CODATENDO<>:CODATENDO AND
        A.DATAATENDO=:DATAATENDO AND ( (A.HORAATENDO BETWEEN :HORAATENDO+1 AND :HORAATENDOFIN-1 )
        OR (A.HORAATENDOFIN BETWEEN :HORAATENDO+1 AND :HORAATENDOFIN-1 ) )
    INTO :DATAATENDORS, :HORAATENDORS, :HORAATENDOFINRS ;

  if (DATAATENDORS IS NOT NULL) then
  begin
     exception atatendimentoex02 'Jah existe(m) lancamento(s) em '||:dataatendors||' - h.: '||
        :horaatendors||' - '||:horaatendofinrs;
  end

  if (IU = 'I') then
  begin
     SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'AT') INTO :CODATENDO;
     STATUSATENDO = 'AA';
     INSERT INTO ATATENDIMENTO (
        CODEMP,CODFILIAL,CODATENDO,CODEMPTO,
        CODFILIALTO,CODTPATENDO,CODEMPAE,
        CODFILIALAE,CODATEND,CODEMPSA,CODFILIALSA,
        CODSETAT,STATUSATENDO,
        CODEMPUS,CODFILIALUS, IDUSU,
        DOCATENDO, DATAATENDO,
        DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, OBSATENDO, CODEMPCL, CODFILIALCL, CODCLI, CODEMPCT, CODFILIALCT,
        CODCONTR, CODITCONTR, CODEMPCH, CODFILIALCH, CODCHAMADO, obsinterno, CONCLUICHAMADO,
        CODEMPEA, CODFILIALEA, CODESPEC , CODEMPTA, CODFILIALTA, CODTAREFA, SITATENDO )

     VALUES (
        :CODEMP, :CODFILIAL, :CODATENDO,
        :CODEMPTO, :CODFILIALTO, :CODTPATENDO,
        :CODEMPAE, :CODFILIALAE,:CODATEND,
        :CODEMPSA,:CODFILIALSA, :CODSETOR,
        :STATUSATENDO ,
        :CODEMPUS, :CODFILIALUS, lower(:IDUSU),
        :DOCATENDO, :DATAATENDO, :DATAATENDOFIN, :HORAATENDO,
        :HORAATENDOFIN, :OBSATENDO,
        :CODEMPCL, :CODFILIALCL, :CODCLI,
        :CODEMPCT, :CODFILIALCT, :CODCONTR, :CODITCONTR,
        :CODEMPCH, :CODFILIALCH, :CODCHAMADO,
        :OBSINTERNO, :CONCLUICHAMADO,
        :CODEMPEA, :CODFILIALEA, :CODESPEC , :CODEMPTA, :CODFILIALTA, :CODTAREFA, :SITATENDO
     );
  -- Caso o atendimento tenha vinculo com o contas a receber
     if (CODREC IS NOT NULL AND NPARCITREC IS NOT NULL) then
     begin
        INSERT INTO ATATENDIMENTOITREC (CODEMP,CODFILIAL,CODATENDO,CODEMPIR,CODFILIALIR,CODREC,NPARCITREC) VALUES
                (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPIR,:CODFILIALIR,:CODREC,:NPARCITREC);
     end

       -- Caso o atendimento tenha vinculo com o contas a receber
     if ( CODORC IS NOT NULL AND TIPOORC IS NOT NULL) then
     begin
        INSERT INTO atatendimentoorc (CODEMP,CODFILIAL,CODATENDO,CODEMPOC,CODFILIALOC,TIPOORC,CODORC) VALUES
                (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPOC,:CODFILIALOC,:TIPOORC,:CODORC);
     end

     -- Caso o atendimento tenha vinculo com um agendamento
     if ( CODAGD IS NOT NULL AND TIPOAGE IS NOT NULL) then
     begin
       
        INSERT INTO ATATENDOAGENDA (CODEMP, CODFILIAL, CODATENDO, CODEMPAG, CODFILIALAG, TIPOAGE, CODAGE, CODAGD)
                 VALUES (:CODEMP, :CODFILIAL, :CODATENDO, :CODEMPAG, :CODFILIALAG, :TIPOAGE, :CODAGE, :CODAGD);
     end

  end
  else if (IU = 'U') then
  begin
        UPDATE ATATENDIMENTO SET
            CODATEND=:CODATEND, DATAATENDO=:DATAATENDO, HORAATENDO=:HORAATENDO, DATAATENDOFIN=:DATAATENDOFIN,
            HORAATENDOFIN=:HORAATENDOFIN, OBSATENDO=:OBSATENDO,CODEMPTO=:CODEMPTO, CODFILIALTO=:CODFILIALTO,
            CODTPATENDO=:CODTPATENDO,CODEMPSA=:CODEMPSA, CODFILIALSA=:CODFILIALSA, CODSETAT=:CODSETOR, CODEMPCH=:CODEMPCH,
            CODFILIALCH=:CODFILIALCH, CODCHAMADO=:CODCHAMADO, CODEMPCT=:CODEMPCT, CODFILIALCT=:CODFILIALCT,
            CODCONTR=:CODCONTR, CODITCONTR=:CODITCONTR, STATUSATENDO=:STATUSATENDO, OBSINTERNO=:OBSINTERNO,
            CONCLUICHAMADO=:CONCLUICHAMADO, CODEMPEA=:CODEMPEA, CODFILIALEA=:CODFILIALEA, CODESPEC=:CODESPEC,
            CODEMPTA=:CODEMPTA, CODFILIALTA=:CODFILIALTA, CODTAREFA=:CODTAREFA,
            CODEMPCL=:CODEMPCL, CODFILIALCL=:CODFILIALCL, CODCLI=:CODCLI, SITATENDO=:SITATENDO
        WHERE
            CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODATENDO=:CODATENDO;

        SELECT COUNT(*) FROM ATATENDIMENTOORC WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODATENDO=:CODATENDO
        INTO :CONTORC;

        if ( :CONTORC = 0 AND CODORC IS NOT NULL AND TIPOORC IS NOT NULL) then
        begin
           INSERT INTO atatendimentoorc (CODEMP,CODFILIAL,CODATENDO,CODEMPOC,CODFILIALOC,TIPOORC,CODORC) VALUES
                    (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPOC,:CODFILIALOC,:TIPOORC,:CODORC);
        end

                 -- Caso o atendimento tenha vinculo com um agendamento
         if ( CODAGD IS NOT NULL AND TIPOAGE IS NOT NULL) then
         begin
           
            INSERT INTO ATATENDOAGENDA (CODEMP, CODFILIAL, CODATENDO, CODEMPAG, CODFILIALAG, TIPOAGE, CODAGE, CODAGD)
                     VALUES (:CODEMP, :CODFILIAL, :CODATENDO, :CODEMPAG, :CODFILIALAG, :TIPOAGE, :CODAGE, :CODAGD);
         end
  end
END
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
ICODALMOX INTEGER,
CLOTEPROD CHAR(1),
SOPRODSALDO CHAR(1))
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
CODNBM VARCHAR(12),
CODLOTE VARCHAR(20),
VENCTOLOTE DATE,
SLDLOTE NUMERIC(15,5),
CODGRUP VARCHAR(14),
SIGLAGRUP VARCHAR(10))
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

  if (:cloteprod = 'S') then
  begin
        for select p.codprod,p.refprod,p.descprod, p.codbarprod
            , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
            , fc.codncm, fc.extipi
            , substring(fc.codncm from 1 for 2) cod_gen
            , fc.codserv,
            (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
            ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
            , fc.codnbm, lt.codlote, lt.venctolote, lt.sldlote, p.codgrup, gp.siglagrup
        from eqproduto p
        left outer join lfclfiscal fc
            on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
        left outer join eqlote lt
            on lt.codemp=p.codemp and lt.codfilial=p.codfilial and lt.codprod=p.codprod
        left outer join eqgrupo gp
            on gp.codemp=p.codemp and gp.codfilial=p.codfilial and gp.codgrup=p.codgrup

        where p.codemp = :icodemp and p.codfilial = :scodfilial and
            ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
             p.codmarca=:ccodmarca) ) and
            ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
             p.codgrup like :ccodgrup) )
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm, :codlote, :venctolote, :sldlote, :codgrup, :siglagrup do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  :sldprod <> 0) then
                suspend;
        end
  end
  else
  begin
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
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  sldprod <> 0) then
                suspend;
        end
  end 


end
^

/* Alter (LFBUSCAFISCALSP02) */
ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codsittribpis char(2);
declare variable codsittribcof char(2);
declare variable vlrpisunidtrib numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrprodit numeric(15,5);
declare variable aliqpis numeric(6,2);
declare variable qtdit numeric(15,5);
declare variable aliqcofins numeric(6,2);
declare variable aliqir numeric(15,5);
declare variable aliqcsocial numeric(15,5);
declare variable vlrliqit numeric(15,5);
declare variable vlrfreteit numeric(15,5);
declare variable vlrdescit numeric(15,5);
begin

	vlrbasencm = 0;
	aliqnacncm = 0;
	aliqimpncm = 0;
	vlrnacncm = 0;
	vlrimpncm = 0;
	
    -- Busca de regra de classificação fiscal da venda
    if(codvenda is not null and codcompra is null) then
    begin

        select li.codsittribpis,li.codsittribcof,iv.vlrproditvenda,li.aliqpisfisc,li.vlrpisunidtrib,
        iv.qtditvenda,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        iv.vlrliqitvenda, coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda,
        coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(nc.aliqnac,0) aliqnacncm, coalesce(nc.aliqimp,0) aliqimpncm,
        coalesce(iv.vlrliqitvenda,0) vlrbasencm, coalesce(li.origfisc,'0') origfisc
        from vditvenda iv left outer join lfitclfiscal li on
        li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
        left outer join sgfilial fi on
        fi.codemp=iv.codemp and fi.codfilial=iv.codfilial
        left outer join lfclfiscal cf on
        cf.codemp=li.codemp and cf.codfilial=li.codfilial and cf.codfisc=li.codfisc
        left outer join lfncm nc on
        nc.codncm=cf.codncm
        where
        iv.codemp=:codemp and iv.codfilial=:codfilial and iv.codvenda=:codvenda and iv.tipovenda=:tipovenda and iv.coditvenda=:coditvenda
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT
        ,:ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT
        ,:ALIQNACNCM, :ALIQIMPNCM, :VLRBASENCM, :ORIGFISC;
    end
    -- Busca de regra de classificação fiscal da compra
    else if(codvenda is null and codcompra is not null) then
    begin

       select li.codsittribpis,li.codsittribcof,ic.vlrproditcompra,li.aliqpisfisc,li.vlrpisunidtrib,
        ic.qtditcompra,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        ic.vlrliqitcompra, coalesce(ic.vlripiitcompra,0) vlripiit, coalesce(ic.vlrfreteitcompra,0) vlrfreteit,
        coalesce(ic.vlrdescitcompra,0) vlrdescit
        from cpitcompra ic left outer join lfitclfiscal li on
        li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
        left outer join sgfilial fi on
        fi.codemp=ic.codemp and fi.codfilial=ic.codfilial
        where
        ic.codemp=:codemp and ic.codfilial=:codfilial and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT,
        :ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT;

    end

    -- Definição do PIS

    if(:CODSITTRIBPIS in ('01','02','99')) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprodit - :vlrdescit; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtdit * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:CODSITTRIBCOF in ('01','02','99')) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprodit - :vlrdescit; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtdit * vlrcofunidtrib;
    end

    -- Definição do IR

    vlrir = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqir) / 100;

    -- Definição da CSocial

    vlrcsocial = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqcsocial) / 100;
    
    if ( coalesce(:vlrbasencm,0)>0 ) then
    begin
       -- Cálculo estimativo para lei de transparência
       if (:origfisc in ('1','2','6','7')) then 
       	  vlrimpncm = :vlrbasencm * :aliqimpncm / 100;
       else
          vlrnacncm = :vlrbasencm * :aliqnacncm / 100;
    end 

  suspend;
end
^

ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codsittribpis char(2);
declare variable codsittribcof char(2);
declare variable vlrpisunidtrib numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrprodit numeric(15,5);
declare variable aliqpis numeric(6,2);
declare variable qtdit numeric(15,5);
declare variable aliqcofins numeric(6,2);
declare variable aliqir numeric(15,5);
declare variable aliqcsocial numeric(15,5);
declare variable vlrliqit numeric(15,5);
declare variable vlrfreteit numeric(15,5);
declare variable vlrdescit numeric(15,5);
begin

	vlrbasencm = 0;
	aliqnacncm = 0;
	aliqimpncm = 0;
	vlrnacncm = 0;
	vlrimpncm = 0;
	
    -- Busca de regra de classificação fiscal da venda
    if(codvenda is not null and codcompra is null) then
    begin

        select li.codsittribpis,li.codsittribcof,iv.vlrproditvenda,li.aliqpisfisc,li.vlrpisunidtrib,
        iv.qtditvenda,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        iv.vlrliqitvenda, coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda,
        coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(nc.aliqnac,0) aliqnacncm, coalesce(nc.aliqimp,0) aliqimpncm,
        coalesce(iv.vlrliqitvenda,0) vlrbasencm, coalesce(li.origfisc,'0') origfisc
        from vditvenda iv left outer join lfitclfiscal li on
        li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
        left outer join sgfilial fi on
        fi.codemp=iv.codemp and fi.codfilial=iv.codfilial
        left outer join lfclfiscal cf on
        cf.codemp=li.codemp and cf.codfilial=li.codfilial and cf.codfisc=li.codfisc
        left outer join lfncm nc on
        nc.codncm=cf.codncm
        where
        iv.codemp=:codemp and iv.codfilial=:codfilial and iv.codvenda=:codvenda and iv.tipovenda=:tipovenda and iv.coditvenda=:coditvenda
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT
        ,:ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT
        ,:ALIQNACNCM, :ALIQIMPNCM, :VLRBASENCM, :ORIGFISC;
    end
    -- Busca de regra de classificação fiscal da compra
    else if(codvenda is null and codcompra is not null) then
    begin

       select li.codsittribpis,li.codsittribcof,ic.vlrproditcompra,li.aliqpisfisc,li.vlrpisunidtrib,
        ic.qtditcompra,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        ic.vlrliqitcompra, coalesce(ic.vlripiitcompra,0) vlripiit, coalesce(ic.vlrfreteitcompra,0) vlrfreteit,
        coalesce(ic.vlrdescitcompra,0) vlrdescit
        from cpitcompra ic left outer join lfitclfiscal li on
        li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
        left outer join sgfilial fi on
        fi.codemp=ic.codemp and fi.codfilial=ic.codfilial
        where
        ic.codemp=:codemp and ic.codfilial=:codfilial and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT,
        :ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT;

    end

    -- Definição do PIS

    if(:CODSITTRIBPIS in ('01','02','99')) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprodit - :vlrdescit; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtdit * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:CODSITTRIBCOF in ('01','02','99')) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprodit - :vlrdescit; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtdit * vlrcofunidtrib;
    end

    -- Definição do IR

    vlrir = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqir) / 100;

    -- Definição da CSocial

    vlrcsocial = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqcsocial) / 100;
    
    if ( coalesce(:vlrbasencm,0)>0 ) then
    begin
       -- Cálculo estimativo para lei de transparência
       if (:origfisc in ('1','2','6','7')) then 
       	  vlrimpncm = :vlrbasencm * :aliqimpncm / 100;
       else
          vlrnacncm = :vlrbasencm * :aliqnacncm / 100;
    end 

  suspend;
end
^

/* Alter (LFGERALFITCOMPRASP) */
ALTER PROCEDURE LFGERALFITCOMPRASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable uffor char(2);
declare variable percicmsst numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
begin

    -- Inserindo informações fiscais na tabela LFITCOMPRA

    select 'S'
    from lfitcompra
    where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial, li.codtrattrib, li.origfisc, ic.vlrbaseicmsstitcompra, ic.vlricmsstitcompra
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,null,null,null,:codcompra,:coditcompra) bf
    left outer join cpitcompra ic on ic.codemp=:CODEMP and ic.codfilial=:CODFILIAL and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
    left outer join lfitclfiscal li on li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL,:codtrattrib,:origfisc, :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA;

    -- Buscando estado do fornecedor
    select coalesce(fr.siglauf,fr.uffor), ic.codempif, ic.codfilialif, ic.codfisc, ic.coditfisc, ic.codnat from cpforneced fr, cpcompra cp
    left outer join cpitcompra ic on ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra and ic.coditcompra=:coditcompra
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into uffor,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitcompra set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL, vlrbaseicmsstitcompra=:vlrbaseicmsstitcompra, vlricmsstitcompra=:vlricmsstitcompra
            where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra;
    end
    else
    begin
        insert into lfitcompra (codemp,codfilial,codcompra,coditcompra,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial,codtrattrib,origfisc,
            vlrbaseicmsstitcompra, vlricmsstitcompra)
        values(:CODEMP,:CODFILIAL,:CODcompra,:CODITcompra,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL, :codtrattrib, :origfisc,
        :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA );

    end
  suspend;
end
^

/* Alter (LFGERALFITVENDASP) */
ALTER PROCEDURE LFGERALFITVENDASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(2),
CODITVENDA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable ufcli char(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbasencm decimal(15,5);
declare variable aliqnacncm decimal(9,2);
declare variable aliqimpncm decimal(9,2);
declare variable vlrnacncm decimal(15,5);
declare variable vlrimpncm decimal(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
begin

    -- Inserindo informações fiscais na tabela LFITVENDA

    select 'S'
    from lfitvenda
    where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial
    , bf.vlrbasencm, bf.aliqnacncm, bf.aliqimpncm, bf.vlrnacncm, bf.vlrimpncm 
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA, null, null) bf
    left outer join vditvenda iv on iv.codemp=:CODEMP and iv.codfilial=:CODFILIAL and iv.codvenda=:CODVENDA and iv.tipovenda=:TIPOVENDA and iv.coditvenda=:CODITVENDA
    left outer join lfitclfiscal li on li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL
    , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm;


    -- Buscando estado do cliente
    select coalesce(cl.siglauf,cl.ufcli), iv.codempif, iv.codfilialif, iv.codfisc, iv.coditfisc, iv.codnat from vdcliente cl, vdvenda vd
    left outer join vditvenda iv on iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.coditvenda=:coditvenda
    where vd.codemp=:codemp and vd.codfilial=:codfilial and vd.codvenda=:codvenda and vd.tipovenda=:tipovenda and
    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
    into ufcli,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:ufcli,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitvenda set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL
            , vlrbasencm=:vlrbasencm, aliqnacncm=:aliqnacncm, aliqimpncm=:aliqimpncm, vlrnacncm=:vlrnacncm, vlrimpncm=:vlrimpncm
            where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda;
    end
    else
    begin
        insert into lfitvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial
            , vlrbasencm, aliqnacncm, aliqimpncm, vlrnacncm, vlrimpncm)
        values(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL
        , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm );

    end

  suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.3 (26/06/2013)';
    suspend;
end
^

/* Alter exist trigger... */
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

     if ( (old.edititpag='S') or (new.edititpag is null) ) then
     begin
         new.edititpag = 'N';
     end

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
          FOR SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND
                CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA
          DO BEGIN

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

       if ( (new.multibaixa = 'N') and ((new.edititpag is null) or (new.edititpag='N') ) ) THEN
       BEGIN
           EXECUTE PROCEDURE FNADICLANCASP02(new.CodPag,new.NParcPag,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODFOR,:ICODEMPFR,:ICODFILIALFR,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.AnoCC,new.CodCC,new.CODEMPCC,new.CODFILIALCC, new.DTCOMPITPAG,
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag
                              ,new.codempct, new.codfilialct, new.codcontr, new.coditcontr);
       END

       /* Altera o valor pago e o valor a pagar */
       if ((new.edititpag is null) or (new.edititpag='N')) then
       begin
           new.VLRPAGOITPAG = new.VLRPAGOITPAG + old.VLRPAGOITPAG;
       end

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

     if (new.edititpag='S') then
     begin
        new.edititpag='N';
     end

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
    declare variable codempcl integer;
    declare variable codfilialcl smallint;
    declare variable codcli integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;


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
        
        if (new.tipovenda='E') then
        begin
           --Busca código do cliente e tipo de movimento para utilizar na procedure lfbuscafiscal.
           select vd.codempcl, vd.codfilialcl, vd.codcli, vd.codemptm,  vd.codfilialtm, vd.codtipomov from vdvenda vd
               where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.tipovenda=new.tipovenda and vd.codvenda=new.codvenda
               into :codempcl, :codfilialcl, :codcli, :codemptm,  :codfilialtm, :codtipomov;
                
           select  codempif, codfilialif, codfisc, coditfisc from lfbuscafiscalsp(new.codemppd,new.codfilialpd,new.codprod,:codempcl,:codfilialcl,:codcli,
               :codemptm,  :codfilialtm, :codtipomov, 'VD',
               new.codnat, null, null, null, null)  v
               into new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
               
        end 
        

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

/* Alter empty procedure LFBUSCAFISCALSP02 with new param-list */
ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
/* Alter (ATATENDIMENTOIUSP) */
ALTER PROCEDURE ATATENDIMENTOIUSP(IU CHAR(1),
CODEMP INTEGER,
CODFILIAL SMALLINT,
CODATENDO INTEGER,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
CODTPATENDO INTEGER,
CODEMPAE INTEGER,
CODFILIALAE SMALLINT,
CODATEND INTEGER,
CODEMPSA INTEGER,
CODFILIALSA SMALLINT,
CODSETOR INTEGER,
DOCATENDO INTEGER,
DATAATENDO DATE,
DATAATENDOFIN DATE,
HORAATENDO TIME,
HORAATENDOFIN TIME,
OBSATENDO VARCHAR(10000),
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR SMALLINT,
CODEMPIR INTEGER,
CODFILIALIR SMALLINT,
CODREC INTEGER,
NPARCITREC INTEGER,
CODEMPCH INTEGER,
CODFILIALCH SMALLINT,
CODCHAMADO INTEGER,
OBSINTERNO VARCHAR(10000),
CONCLUICHAMADO CHAR(1),
CODEMPEA INTEGER,
CODFILIALEA SMALLINT,
CODESPEC INTEGER,
CODEMPUS INTEGER,
CODFILIALUS SMALLINT,
IDUSU VARCHAR(128),
STATUSATENDO CHAR(2),
CODEMPTA INTEGER,
CODFILIALTA SMALLINT,
CODTAREFA INTEGER,
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
TIPOORC CHAR(1),
CODORC INTEGER,
SITATENDO CHAR(2),
CODEMPAG INTEGER,
CODFILIALAG SMALLINT,
TIPOAGE CHAR(5),
CODAGE INTEGER,
CODAGD INTEGER)
 AS
declare variable horaatendors time;
declare variable horaatendofinrs time;
declare variable dataatendors date;
declare variable contorc integer;
BEGIN

  DATAATENDORS = NULL;

  SELECT FIRST 1 A.DATAATENDO, A.HORAATENDO, A.HORAATENDOFIN
    FROM ATATENDIMENTO A
    WHERE A.CODEMP=:CODEMP AND A.CODFILIAL=:CODFILIAL AND
        A.CODEMPAE=:CODEMPAE AND A.CODFILIALAE=:CODFILIALAE AND
        A.CODATEND=:CODATEND AND A.CODATENDO<>:CODATENDO AND
        A.DATAATENDO=:DATAATENDO AND ( (A.HORAATENDO BETWEEN :HORAATENDO+1 AND :HORAATENDOFIN-1 )
        OR (A.HORAATENDOFIN BETWEEN :HORAATENDO+1 AND :HORAATENDOFIN-1 ) )
    INTO :DATAATENDORS, :HORAATENDORS, :HORAATENDOFINRS ;

  if (DATAATENDORS IS NOT NULL) then
  begin
     exception atatendimentoex02 'Jah existe(m) lancamento(s) em '||:dataatendors||' - h.: '||
        :horaatendors||' - '||:horaatendofinrs;
  end

  if (IU = 'I') then
  begin
     SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'AT') INTO :CODATENDO;
     STATUSATENDO = 'AA';
     INSERT INTO ATATENDIMENTO (
        CODEMP,CODFILIAL,CODATENDO,CODEMPTO,
        CODFILIALTO,CODTPATENDO,CODEMPAE,
        CODFILIALAE,CODATEND,CODEMPSA,CODFILIALSA,
        CODSETAT,STATUSATENDO,
        CODEMPUS,CODFILIALUS, IDUSU,
        DOCATENDO, DATAATENDO,
        DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, OBSATENDO, CODEMPCL, CODFILIALCL, CODCLI, CODEMPCT, CODFILIALCT,
        CODCONTR, CODITCONTR, CODEMPCH, CODFILIALCH, CODCHAMADO, obsinterno, CONCLUICHAMADO,
        CODEMPEA, CODFILIALEA, CODESPEC , CODEMPTA, CODFILIALTA, CODTAREFA, SITATENDO )

     VALUES (
        :CODEMP, :CODFILIAL, :CODATENDO,
        :CODEMPTO, :CODFILIALTO, :CODTPATENDO,
        :CODEMPAE, :CODFILIALAE,:CODATEND,
        :CODEMPSA,:CODFILIALSA, :CODSETOR,
        :STATUSATENDO ,
        :CODEMPUS, :CODFILIALUS, lower(:IDUSU),
        :DOCATENDO, :DATAATENDO, :DATAATENDOFIN, :HORAATENDO,
        :HORAATENDOFIN, :OBSATENDO,
        :CODEMPCL, :CODFILIALCL, :CODCLI,
        :CODEMPCT, :CODFILIALCT, :CODCONTR, :CODITCONTR,
        :CODEMPCH, :CODFILIALCH, :CODCHAMADO,
        :OBSINTERNO, :CONCLUICHAMADO,
        :CODEMPEA, :CODFILIALEA, :CODESPEC , :CODEMPTA, :CODFILIALTA, :CODTAREFA, :SITATENDO
     );
  -- Caso o atendimento tenha vinculo com o contas a receber
     if (CODREC IS NOT NULL AND NPARCITREC IS NOT NULL) then
     begin
        INSERT INTO ATATENDIMENTOITREC (CODEMP,CODFILIAL,CODATENDO,CODEMPIR,CODFILIALIR,CODREC,NPARCITREC) VALUES
                (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPIR,:CODFILIALIR,:CODREC,:NPARCITREC);
     end

       -- Caso o atendimento tenha vinculo com o contas a receber
     if ( CODORC IS NOT NULL AND TIPOORC IS NOT NULL) then
     begin
        INSERT INTO atatendimentoorc (CODEMP,CODFILIAL,CODATENDO,CODEMPOC,CODFILIALOC,TIPOORC,CODORC) VALUES
                (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPOC,:CODFILIALOC,:TIPOORC,:CODORC);
     end

     -- Caso o atendimento tenha vinculo com um agendamento
     if ( CODAGD IS NOT NULL AND TIPOAGE IS NOT NULL) then
     begin
       
        INSERT INTO ATATENDOAGENDA (CODEMP, CODFILIAL, CODATENDO, CODEMPAG, CODFILIALAG, TIPOAGE, CODAGE, CODAGD)
                 VALUES (:CODEMP, :CODFILIAL, :CODATENDO, :CODEMPAG, :CODFILIALAG, :TIPOAGE, :CODAGE, :CODAGD);
     end

  end
  else if (IU = 'U') then
  begin
        UPDATE ATATENDIMENTO SET
            CODATEND=:CODATEND, DATAATENDO=:DATAATENDO, HORAATENDO=:HORAATENDO, DATAATENDOFIN=:DATAATENDOFIN,
            HORAATENDOFIN=:HORAATENDOFIN, OBSATENDO=:OBSATENDO,CODEMPTO=:CODEMPTO, CODFILIALTO=:CODFILIALTO,
            CODTPATENDO=:CODTPATENDO,CODEMPSA=:CODEMPSA, CODFILIALSA=:CODFILIALSA, CODSETAT=:CODSETOR, CODEMPCH=:CODEMPCH,
            CODFILIALCH=:CODFILIALCH, CODCHAMADO=:CODCHAMADO, CODEMPCT=:CODEMPCT, CODFILIALCT=:CODFILIALCT,
            CODCONTR=:CODCONTR, CODITCONTR=:CODITCONTR, STATUSATENDO=:STATUSATENDO, OBSINTERNO=:OBSINTERNO,
            CONCLUICHAMADO=:CONCLUICHAMADO, CODEMPEA=:CODEMPEA, CODFILIALEA=:CODFILIALEA, CODESPEC=:CODESPEC,
            CODEMPTA=:CODEMPTA, CODFILIALTA=:CODFILIALTA, CODTAREFA=:CODTAREFA,
            CODEMPCL=:CODEMPCL, CODFILIALCL=:CODFILIALCL, CODCLI=:CODCLI, SITATENDO=:SITATENDO
        WHERE
            CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODATENDO=:CODATENDO;

        SELECT COUNT(*) FROM ATATENDIMENTOORC WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND CODATENDO=:CODATENDO
        INTO :CONTORC;

        if ( :CONTORC = 0 AND CODORC IS NOT NULL AND TIPOORC IS NOT NULL) then
        begin
           INSERT INTO atatendimentoorc (CODEMP,CODFILIAL,CODATENDO,CODEMPOC,CODFILIALOC,TIPOORC,CODORC) VALUES
                    (:CODEMP,:CODFILIAL,:CODATENDO,:CODEMPOC,:CODFILIALOC,:TIPOORC,:CODORC);
        end

                 -- Caso o atendimento tenha vinculo com um agendamento
         if ( CODAGD IS NOT NULL AND TIPOAGE IS NOT NULL) then
         begin
           
            INSERT INTO ATATENDOAGENDA (CODEMP, CODFILIAL, CODATENDO, CODEMPAG, CODFILIALAG, TIPOAGE, CODAGE, CODAGD)
                     VALUES (:CODEMP, :CODFILIAL, :CODATENDO, :CODEMPAG, :CODFILIALAG, :TIPOAGE, :CODAGE, :CODAGD);
         end
  end
END
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
ICODALMOX INTEGER,
CLOTEPROD CHAR(1),
SOPRODSALDO CHAR(1))
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
CODNBM VARCHAR(12),
CODLOTE VARCHAR(20),
VENCTOLOTE DATE,
SLDLOTE NUMERIC(15,5),
CODGRUP VARCHAR(14),
SIGLAGRUP VARCHAR(10))
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

  if (:cloteprod = 'S') then
  begin
        for select p.codprod,p.refprod,p.descprod, p.codbarprod
            , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
            , fc.codncm, fc.extipi
            , substring(fc.codncm from 1 for 2) cod_gen
            , fc.codserv,
            (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
            ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
            , fc.codnbm, lt.codlote, lt.venctolote, lt.sldlote, p.codgrup, gp.siglagrup
        from eqproduto p
        left outer join lfclfiscal fc
            on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
        left outer join eqlote lt
            on lt.codemp=p.codemp and lt.codfilial=p.codfilial and lt.codprod=p.codprod
        left outer join eqgrupo gp
            on gp.codemp=p.codemp and gp.codfilial=p.codfilial and gp.codgrup=p.codgrup

        where p.codemp = :icodemp and p.codfilial = :scodfilial and
            ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
             p.codmarca=:ccodmarca) ) and
            ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
             p.codgrup like :ccodgrup) )
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm, :codlote, :venctolote, :sldlote, :codgrup, :siglagrup do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  :sldprod <> 0) then
                suspend;
        end
  end
  else
  begin
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
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  sldprod <> 0) then
                suspend;
        end
  end 


end
^

/* Alter (LFBUSCAFISCALSP02) */
ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codsittribpis char(2);
declare variable codsittribcof char(2);
declare variable vlrpisunidtrib numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrprodit numeric(15,5);
declare variable aliqpis numeric(6,2);
declare variable qtdit numeric(15,5);
declare variable aliqcofins numeric(6,2);
declare variable aliqir numeric(15,5);
declare variable aliqcsocial numeric(15,5);
declare variable vlrliqit numeric(15,5);
declare variable vlrfreteit numeric(15,5);
declare variable vlrdescit numeric(15,5);
begin

	vlrbasencm = 0;
	aliqnacncm = 0;
	aliqimpncm = 0;
	vlrnacncm = 0;
	vlrimpncm = 0;
	
    -- Busca de regra de classificação fiscal da venda
    if(codvenda is not null and codcompra is null) then
    begin

        select li.codsittribpis,li.codsittribcof,iv.vlrproditvenda,li.aliqpisfisc,li.vlrpisunidtrib,
        iv.qtditvenda,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        iv.vlrliqitvenda, coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda,
        coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(nc.aliqnac,0) aliqnacncm, coalesce(nc.aliqimp,0) aliqimpncm,
        coalesce(iv.vlrliqitvenda,0) vlrbasencm, coalesce(li.origfisc,'0') origfisc
        from vditvenda iv left outer join lfitclfiscal li on
        li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
        left outer join sgfilial fi on
        fi.codemp=iv.codemp and fi.codfilial=iv.codfilial
        left outer join lfclfiscal cf on
        cf.codemp=li.codemp and cf.codfilial=li.codfilial and cf.codfisc=li.codfisc
        left outer join lfncm nc on
        nc.codncm=cf.codncm
        where
        iv.codemp=:codemp and iv.codfilial=:codfilial and iv.codvenda=:codvenda and iv.tipovenda=:tipovenda and iv.coditvenda=:coditvenda
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT
        ,:ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT
        ,:ALIQNACNCM, :ALIQIMPNCM, :VLRBASENCM, :ORIGFISC;
    end
    -- Busca de regra de classificação fiscal da compra
    else if(codvenda is null and codcompra is not null) then
    begin

       select li.codsittribpis,li.codsittribcof,ic.vlrproditcompra,li.aliqpisfisc,li.vlrpisunidtrib,
        ic.qtditcompra,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        ic.vlrliqitcompra, coalesce(ic.vlripiitcompra,0) vlripiit, coalesce(ic.vlrfreteitcompra,0) vlrfreteit,
        coalesce(ic.vlrdescitcompra,0) vlrdescit
        from cpitcompra ic left outer join lfitclfiscal li on
        li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
        left outer join sgfilial fi on
        fi.codemp=ic.codemp and fi.codfilial=ic.codfilial
        where
        ic.codemp=:codemp and ic.codfilial=:codfilial and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT,
        :ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT;

    end

    -- Definição do PIS

    if(:CODSITTRIBPIS in ('01','02','99')) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprodit - :vlrdescit; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtdit * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:CODSITTRIBCOF in ('01','02','99')) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprodit - :vlrdescit; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtdit * vlrcofunidtrib;
    end

    -- Definição do IR

    vlrir = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqir) / 100;

    -- Definição da CSocial

    vlrcsocial = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqcsocial) / 100;
    
    if ( coalesce(:vlrbasencm,0)>0 ) then
    begin
       -- Cálculo estimativo para lei de transparência
       if (:origfisc in ('1','2','6','7')) then 
       	  vlrimpncm = :vlrbasencm * :aliqimpncm / 100;
       else
          vlrnacncm = :vlrbasencm * :aliqnacncm / 100;
    end 

  suspend;
end
^

ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR(1))
 AS
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codsittribpis char(2);
declare variable codsittribcof char(2);
declare variable vlrpisunidtrib numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrprodit numeric(15,5);
declare variable aliqpis numeric(6,2);
declare variable qtdit numeric(15,5);
declare variable aliqcofins numeric(6,2);
declare variable aliqir numeric(15,5);
declare variable aliqcsocial numeric(15,5);
declare variable vlrliqit numeric(15,5);
declare variable vlrfreteit numeric(15,5);
declare variable vlrdescit numeric(15,5);
begin

	vlrbasencm = 0;
	aliqnacncm = 0;
	aliqimpncm = 0;
	vlrnacncm = 0;
	vlrimpncm = 0;
	
    -- Busca de regra de classificação fiscal da venda
    if(codvenda is not null and codcompra is null) then
    begin

        select li.codsittribpis,li.codsittribcof,iv.vlrproditvenda,li.aliqpisfisc,li.vlrpisunidtrib,
        iv.qtditvenda,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        iv.vlrliqitvenda, coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda,
        coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(nc.aliqnac,0) aliqnacncm, coalesce(nc.aliqimp,0) aliqimpncm,
        coalesce(iv.vlrliqitvenda,0) vlrbasencm, coalesce(li.origfisc,'0') origfisc
        from vditvenda iv left outer join lfitclfiscal li on
        li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
        left outer join sgfilial fi on
        fi.codemp=iv.codemp and fi.codfilial=iv.codfilial
        left outer join lfclfiscal cf on
        cf.codemp=li.codemp and cf.codfilial=li.codfilial and cf.codfisc=li.codfisc
        left outer join lfncm nc on
        nc.codncm=cf.codncm
        where
        iv.codemp=:codemp and iv.codfilial=:codfilial and iv.codvenda=:codvenda and iv.tipovenda=:tipovenda and iv.coditvenda=:coditvenda
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT
        ,:ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT
        ,:ALIQNACNCM, :ALIQIMPNCM, :VLRBASENCM, :ORIGFISC;
    end
    -- Busca de regra de classificação fiscal da compra
    else if(codvenda is null and codcompra is not null) then
    begin

       select li.codsittribpis,li.codsittribcof,ic.vlrproditcompra,li.aliqpisfisc,li.vlrpisunidtrib,
        ic.qtditcompra,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        ic.vlrliqitcompra, coalesce(ic.vlripiitcompra,0) vlripiit, coalesce(ic.vlrfreteitcompra,0) vlrfreteit,
        coalesce(ic.vlrdescitcompra,0) vlrdescit
        from cpitcompra ic left outer join lfitclfiscal li on
        li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
        left outer join sgfilial fi on
        fi.codemp=ic.codemp and fi.codfilial=ic.codfilial
        where
        ic.codemp=:codemp and ic.codfilial=:codfilial and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT,
        :ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT;

    end

    -- Definição do PIS

    if(:CODSITTRIBPIS in ('01','02','99')) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprodit - :vlrdescit; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtdit * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:CODSITTRIBCOF in ('01','02','99')) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprodit - :vlrdescit; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtdit * vlrcofunidtrib;
    end

    -- Definição do IR

    vlrir = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqir) / 100;

    -- Definição da CSocial

    vlrcsocial = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqcsocial) / 100;
    
    if ( coalesce(:vlrbasencm,0)>0 ) then
    begin
       -- Cálculo estimativo para lei de transparência
       if (:origfisc in ('1','2','6','7')) then 
       	  vlrimpncm = :vlrbasencm * :aliqimpncm / 100;
       else
          vlrnacncm = :vlrbasencm * :aliqnacncm / 100;
    end 

  suspend;
end
^

/* Alter (LFGERALFITCOMPRASP) */
ALTER PROCEDURE LFGERALFITCOMPRASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable uffor char(2);
declare variable percicmsst numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
begin

    -- Inserindo informações fiscais na tabela LFITCOMPRA

    select 'S'
    from lfitcompra
    where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial, li.codtrattrib, li.origfisc, ic.vlrbaseicmsstitcompra, ic.vlricmsstitcompra
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,null,null,null,:codcompra,:coditcompra) bf
    left outer join cpitcompra ic on ic.codemp=:CODEMP and ic.codfilial=:CODFILIAL and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
    left outer join lfitclfiscal li on li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL,:codtrattrib,:origfisc, :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA;

    -- Buscando estado do fornecedor
    select coalesce(fr.siglauf,fr.uffor), ic.codempif, ic.codfilialif, ic.codfisc, ic.coditfisc, ic.codnat from cpforneced fr, cpcompra cp
    left outer join cpitcompra ic on ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra and ic.coditcompra=:coditcompra
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into uffor,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitcompra set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL, vlrbaseicmsstitcompra=:vlrbaseicmsstitcompra, vlricmsstitcompra=:vlricmsstitcompra
            where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra;
    end
    else
    begin
        insert into lfitcompra (codemp,codfilial,codcompra,coditcompra,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial,codtrattrib,origfisc,
            vlrbaseicmsstitcompra, vlricmsstitcompra)
        values(:CODEMP,:CODFILIAL,:CODcompra,:CODITcompra,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL, :codtrattrib, :origfisc,
        :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA );

    end
  suspend;
end
^

/* Alter (LFGERALFITVENDASP) */
ALTER PROCEDURE LFGERALFITVENDASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(2),
CODITVENDA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable ufcli char(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbasencm decimal(15,5);
declare variable aliqnacncm decimal(9,2);
declare variable aliqimpncm decimal(9,2);
declare variable vlrnacncm decimal(15,5);
declare variable vlrimpncm decimal(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
begin

    -- Inserindo informações fiscais na tabela LFITVENDA

    select 'S'
    from lfitvenda
    where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial
    , bf.vlrbasencm, bf.aliqnacncm, bf.aliqimpncm, bf.vlrnacncm, bf.vlrimpncm 
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA, null, null) bf
    left outer join vditvenda iv on iv.codemp=:CODEMP and iv.codfilial=:CODFILIAL and iv.codvenda=:CODVENDA and iv.tipovenda=:TIPOVENDA and iv.coditvenda=:CODITVENDA
    left outer join lfitclfiscal li on li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL
    , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm;


    -- Buscando estado do cliente
    select coalesce(cl.siglauf,cl.ufcli), iv.codempif, iv.codfilialif, iv.codfisc, iv.coditfisc, iv.codnat from vdcliente cl, vdvenda vd
    left outer join vditvenda iv on iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.coditvenda=:coditvenda
    where vd.codemp=:codemp and vd.codfilial=:codfilial and vd.codvenda=:codvenda and vd.tipovenda=:tipovenda and
    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
    into ufcli,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:ufcli,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitvenda set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL
            , vlrbasencm=:vlrbasencm, aliqnacncm=:aliqnacncm, aliqimpncm=:aliqimpncm, vlrnacncm=:vlrnacncm, vlrimpncm=:vlrimpncm
            where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda;
    end
    else
    begin
        insert into lfitvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial
            , vlrbasencm, aliqnacncm, aliqimpncm, vlrnacncm, vlrimpncm)
        values(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL
        , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm );

    end

  suspend;
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.3 (26/06/2013)';
    suspend;
end
^

SET TERM ; ^

ALTER TABLE ATATENDENTE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE ATATENDENTE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE ATATENDENTE ALTER COLUMN CODATEND POSITION 3;

ALTER TABLE ATATENDENTE ALTER COLUMN NOMEATEND POSITION 4;

ALTER TABLE ATATENDENTE ALTER COLUMN CODEMPTA POSITION 5;

ALTER TABLE ATATENDENTE ALTER COLUMN CODFILIALTA POSITION 6;

ALTER TABLE ATATENDENTE ALTER COLUMN CODTPATEND POSITION 7;

ALTER TABLE ATATENDENTE ALTER COLUMN RGATEND POSITION 8;

ALTER TABLE ATATENDENTE ALTER COLUMN ENDATEND POSITION 9;

ALTER TABLE ATATENDENTE ALTER COLUMN BAIRATEND POSITION 10;

ALTER TABLE ATATENDENTE ALTER COLUMN CIDATEND POSITION 11;

ALTER TABLE ATATENDENTE ALTER COLUMN CEPATEND POSITION 12;

ALTER TABLE ATATENDENTE ALTER COLUMN FONEATEND POSITION 13;

ALTER TABLE ATATENDENTE ALTER COLUMN FAXATEND POSITION 14;

ALTER TABLE ATATENDENTE ALTER COLUMN EMAILATEND POSITION 15;

ALTER TABLE ATATENDENTE ALTER COLUMN NUMATEND POSITION 16;

ALTER TABLE ATATENDENTE ALTER COLUMN UFATEND POSITION 17;

ALTER TABLE ATATENDENTE ALTER COLUMN CELATEND POSITION 18;

ALTER TABLE ATATENDENTE ALTER COLUMN IDENTIFICATEND POSITION 19;

ALTER TABLE ATATENDENTE ALTER COLUMN CODEMPUS POSITION 20;

ALTER TABLE ATATENDENTE ALTER COLUMN CODFILIALUS POSITION 21;

ALTER TABLE ATATENDENTE ALTER COLUMN IDUSU POSITION 22;

ALTER TABLE ATATENDENTE ALTER COLUMN CPFATEND POSITION 23;

ALTER TABLE ATATENDENTE ALTER COLUMN CODVEND POSITION 24;

ALTER TABLE ATATENDENTE ALTER COLUMN CODEMPVE POSITION 25;

ALTER TABLE ATATENDENTE ALTER COLUMN CODFILIALVE POSITION 26;

ALTER TABLE ATATENDENTE ALTER COLUMN CODEMPEP POSITION 27;

ALTER TABLE ATATENDENTE ALTER COLUMN CODFILIALEP POSITION 28;

ALTER TABLE ATATENDENTE ALTER COLUMN MATEMPR POSITION 29;

ALTER TABLE ATATENDENTE ALTER COLUMN METAATEND POSITION 30;

ALTER TABLE ATATENDENTE ALTER COLUMN PARTPREMIATEND POSITION 31;

ALTER TABLE ATATENDENTE ALTER COLUMN ACESATDOLEROUT POSITION 32;

ALTER TABLE ATATENDENTE ALTER COLUMN ACESATDOALTOUT POSITION 33;

ALTER TABLE ATATENDENTE ALTER COLUMN ACESATDODELLAN POSITION 34;

ALTER TABLE ATATENDENTE ALTER COLUMN ACESATDODELOUT POSITION 35;

ALTER TABLE ATATENDENTE ALTER COLUMN ACESRELESTOUT POSITION 36;

ALTER TABLE ATATENDENTE ALTER COLUMN DTINS POSITION 37;

ALTER TABLE ATATENDENTE ALTER COLUMN IDUSUINS POSITION 38;

ALTER TABLE ATATENDENTE ALTER COLUMN DTALT POSITION 39;

ALTER TABLE ATATENDENTE ALTER COLUMN IDUSUALT POSITION 40;

ALTER TABLE ATATENDENTE ALTER COLUMN HINS POSITION 41;

ALTER TABLE ATATENDENTE ALTER COLUMN HALT POSITION 42;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODATENDO POSITION 3;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCONV POSITION 4;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCV POSITION 5;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCV POSITION 6;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPTO POSITION 7;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALTO POSITION 8;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODTPATENDO POSITION 9;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPAE POSITION 10;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALAE POSITION 11;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODATEND POSITION 12;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPSA POSITION 13;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALSA POSITION 14;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODSETAT POSITION 15;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DATAATENDO POSITION 16;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HORAATENDO POSITION 17;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DATAATENDOFIN POSITION 18;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HORAATENDOFIN POSITION 19;

ALTER TABLE ATATENDIMENTO ALTER COLUMN OBSATENDO POSITION 20;

ALTER TABLE ATATENDIMENTO ALTER COLUMN OBSINTERNO POSITION 21;

ALTER TABLE ATATENDIMENTO ALTER COLUMN STATUSATENDO POSITION 22;

ALTER TABLE ATATENDIMENTO ALTER COLUMN SITREVATENDO POSITION 23;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCL POSITION 24;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCL POSITION 25;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCLI POSITION 26;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSU POSITION 27;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPUS POSITION 28;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALUS POSITION 29;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DOCATENDO POSITION 30;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCT POSITION 31;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCT POSITION 32;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCONTR POSITION 33;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODITCONTR POSITION 34;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPTA POSITION 35;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALTA POSITION 36;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODTAREFA POSITION 37;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCA POSITION 38;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCA POSITION 39;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCLASATENDO POSITION 40;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCH POSITION 41;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCH POSITION 42;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCHAMADO POSITION 43;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CONCLUICHAMADO POSITION 44;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPEA POSITION 45;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALEA POSITION 46;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODESPEC POSITION 47;

ALTER TABLE ATATENDIMENTO ALTER COLUMN SITATENDO POSITION 48;

ALTER TABLE ATATENDIMENTO ALTER COLUMN EMMANUT POSITION 49;

ALTER TABLE ATATENDIMENTO ALTER COLUMN BLOQATENDO POSITION 50;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DTINS POSITION 51;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HINS POSITION 52;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSUINS POSITION 53;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DTALT POSITION 54;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HALT POSITION 55;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSUALT POSITION 56;

ALTER TABLE LFITVENDA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFITVENDA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFITVENDA ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE LFITVENDA ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE LFITVENDA ALTER COLUMN CODITVENDA POSITION 5;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQCOFINS POSITION 6;

ALTER TABLE LFITVENDA ALTER COLUMN CODEMPSP POSITION 7;

ALTER TABLE LFITVENDA ALTER COLUMN CODFILIALSP POSITION 8;

ALTER TABLE LFITVENDA ALTER COLUMN CODSITTRIBPIS POSITION 9;

ALTER TABLE LFITVENDA ALTER COLUMN IMPSITTRIBPIS POSITION 10;

ALTER TABLE LFITVENDA ALTER COLUMN CODEMPSC POSITION 11;

ALTER TABLE LFITVENDA ALTER COLUMN CODFILIALSC POSITION 12;

ALTER TABLE LFITVENDA ALTER COLUMN CODSITTRIBCOF POSITION 13;

ALTER TABLE LFITVENDA ALTER COLUMN IMPSITTRIBCOF POSITION 14;

ALTER TABLE LFITVENDA ALTER COLUMN CODEMPSI POSITION 15;

ALTER TABLE LFITVENDA ALTER COLUMN CODFILIALSI POSITION 16;

ALTER TABLE LFITVENDA ALTER COLUMN CODSITTRIBIPI POSITION 17;

ALTER TABLE LFITVENDA ALTER COLUMN IMPSITTRIBIPI POSITION 18;

ALTER TABLE LFITVENDA ALTER COLUMN VLRIPIUNIDTRIB POSITION 19;

ALTER TABLE LFITVENDA ALTER COLUMN MODBCICMS POSITION 20;

ALTER TABLE LFITVENDA ALTER COLUMN MODBCICMSST POSITION 21;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQREDBCICMS POSITION 22;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQREDBCICMSST POSITION 23;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQICMSST POSITION 24;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQPIS POSITION 25;

ALTER TABLE LFITVENDA ALTER COLUMN VLRPISUNIDTRIB POSITION 26;

ALTER TABLE LFITVENDA ALTER COLUMN VLRBASEPIS POSITION 27;

ALTER TABLE LFITVENDA ALTER COLUMN VLRBASECOFINS POSITION 28;

ALTER TABLE LFITVENDA ALTER COLUMN VLRCOFUNIDTRIB POSITION 29;

ALTER TABLE LFITVENDA ALTER COLUMN VLRIR POSITION 30;

ALTER TABLE LFITVENDA ALTER COLUMN VLRPIS POSITION 31;

ALTER TABLE LFITVENDA ALTER COLUMN VLRCOFINS POSITION 32;

ALTER TABLE LFITVENDA ALTER COLUMN VLRCSOCIAL POSITION 33;

ALTER TABLE LFITVENDA ALTER COLUMN VLRBASEICMSITVENDA POSITION 34;

ALTER TABLE LFITVENDA ALTER COLUMN VLRBASEICMSFRETEITVENDA POSITION 35;

ALTER TABLE LFITVENDA ALTER COLUMN VLRICMSFRETEITVENDA POSITION 36;

ALTER TABLE LFITVENDA ALTER COLUMN VLRBASENCM POSITION 37;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQNACNCM POSITION 38;

ALTER TABLE LFITVENDA ALTER COLUMN ALIQIMPNCM POSITION 39;

ALTER TABLE LFITVENDA ALTER COLUMN VLRNACNCM POSITION 40;

ALTER TABLE LFITVENDA ALTER COLUMN VLRIMPNCM POSITION 41;

ALTER TABLE LFITVENDA ALTER COLUMN EMMANUT POSITION 42;

ALTER TABLE LFITVENDA ALTER COLUMN DTINS POSITION 43;

ALTER TABLE LFITVENDA ALTER COLUMN HINS POSITION 44;

ALTER TABLE LFITVENDA ALTER COLUMN IDUSUINS POSITION 45;

ALTER TABLE LFITVENDA ALTER COLUMN DTALT POSITION 46;

ALTER TABLE LFITVENDA ALTER COLUMN HALT POSITION 47;

ALTER TABLE LFITVENDA ALTER COLUMN IDUSUALT POSITION 48;

ALTER TABLE LFNCM ALTER COLUMN CODNCM POSITION 1;

ALTER TABLE LFNCM ALTER COLUMN DESCNCM POSITION 2;

ALTER TABLE LFNCM ALTER COLUMN ALIQNCM POSITION 3;

ALTER TABLE LFNCM ALTER COLUMN TEXTONCM POSITION 4;

ALTER TABLE LFNCM ALTER COLUMN EXCECAONCM POSITION 5;

ALTER TABLE LFNCM ALTER COLUMN ALIQNAC POSITION 6;

ALTER TABLE LFNCM ALTER COLUMN ALIQIMP POSITION 7;

ALTER TABLE LFNCM ALTER COLUMN DTINS POSITION 8;

ALTER TABLE LFNCM ALTER COLUMN HINS POSITION 9;

ALTER TABLE LFNCM ALTER COLUMN IDUSUINS POSITION 10;

ALTER TABLE LFNCM ALTER COLUMN DTALT POSITION 11;

ALTER TABLE LFNCM ALTER COLUMN HALT POSITION 12;

ALTER TABLE LFNCM ALTER COLUMN IDUSUALT POSITION 13;

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

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN LEITRANSP POSITION 19;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODEMPOC POSITION 20;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODFILIALOC POSITION 21;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN CODTIPOMOVOC POSITION 22;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN DTINS POSITION 23;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN HINS POSITION 24;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IDUSUINS POSITION 25;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN DTALT POSITION 26;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN HALT POSITION 27;

ALTER TABLE LFTIPOFISCCLI ALTER COLUMN IDUSUALT POSITION 28;

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

ALTER TABLE SGPREFERE1 ALTER COLUMN NPERMITDTMAIOR POSITION 311;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERMITIMPORCANTAP POSITION 312;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQEDITORCAPOSAP POSITION 313;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQVDPORATRASO POSITION 314;

ALTER TABLE SGPREFERE1 ALTER COLUMN NUMDIASBLOQVD POSITION 315;

ALTER TABLE SGPREFERE1 ALTER COLUMN FATORSEGESTOQ POSITION 316;

ALTER TABLE SGPREFERE1 ALTER COLUMN LEITRANSP POSITION 317;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOIMPDANFE POSITION 318;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 319;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 320;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 321;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 322;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 323;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 324;

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

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPOR POSITION 40;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALOR POSITION 41;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELOR POSITION 42;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPMC POSITION 43;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALMC POSITION 44;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODMODELMC POSITION 45;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEC POSITION 46;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEC POSITION 47;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEC POSITION 48;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEA POSITION 49;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEA POSITION 50;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEA POSITION 51;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPT1 POSITION 52;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALT1 POSITION 53;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODTIPOCONT1 POSITION 54;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPT2 POSITION 55;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALT2 POSITION 56;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODTIPOCONT2 POSITION 57;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPCF POSITION 58;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALCF POSITION 59;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODCONFEMAIL POSITION 60;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPC2 POSITION 61;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALC2 POSITION 62;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODCONFEMAIL2 POSITION 63;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEN POSITION 64;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEN POSITION 65;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEN POSITION 66;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPE2 POSITION 67;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALE2 POSITION 68;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEN2 POSITION 69;

ALTER TABLE SGPREFERE3 ALTER COLUMN EMAILNOTIF1 POSITION 70;

ALTER TABLE SGPREFERE3 ALTER COLUMN EMAILNOTIF2 POSITION 71;

ALTER TABLE SGPREFERE3 ALTER COLUMN TEMPOMAXINT POSITION 72;

ALTER TABLE SGPREFERE3 ALTER COLUMN LANCAPONTOAF POSITION 73;

ALTER TABLE SGPREFERE3 ALTER COLUMN TOLREGPONTO POSITION 74;

ALTER TABLE SGPREFERE3 ALTER COLUMN USACTOSEQ POSITION 75;

ALTER TABLE SGPREFERE3 ALTER COLUMN LAYOUTFICHAAVAL POSITION 76;

ALTER TABLE SGPREFERE3 ALTER COLUMN LAYOUTPREFICHAAVAL POSITION 77;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV1 POSITION 78;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV1 POSITION 79;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG1 POSITION 80;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV2 POSITION 81;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV2 POSITION 82;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG2 POSITION 83;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV3 POSITION 84;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV3 POSITION 85;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG3 POSITION 86;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV4 POSITION 87;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV4 POSITION 88;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG4 POSITION 89;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV5 POSITION 90;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV5 POSITION 91;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG5 POSITION 92;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV6 POSITION 93;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV6 POSITION 94;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG6 POSITION 95;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV7 POSITION 96;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV7 POSITION 97;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG7 POSITION 98;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPV8 POSITION 99;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALV8 POSITION 100;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODVARG8 POSITION 101;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPSR POSITION 102;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALSR POSITION 103;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODSETOR POSITION 104;

ALTER TABLE SGPREFERE3 ALTER COLUMN BLOQATENDIMENTO POSITION 105;

ALTER TABLE SGPREFERE3 ALTER COLUMN PERIODOBLOQ POSITION 106;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTINS POSITION 107;

ALTER TABLE SGPREFERE3 ALTER COLUMN HINS POSITION 108;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUINS POSITION 109;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTALT POSITION 110;

ALTER TABLE SGPREFERE3 ALTER COLUMN HALT POSITION 111;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUALT POSITION 112;


COMMIT WORK;
