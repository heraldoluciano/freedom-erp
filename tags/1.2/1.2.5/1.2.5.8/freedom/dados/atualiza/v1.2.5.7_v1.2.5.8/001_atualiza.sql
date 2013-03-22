/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.7.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

SET AUTODDL ON;

/* Alter Field (Length): from 0 to 2... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=2, RDB$CHARACTER_LENGTH=2
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='CPORDCOMPRA' AND  RDB$FIELD_NAME='STATUSOC');


ALTER TABLE EQITMODGRADE ADD ORDEMITMODG INTEGER DEFAULT 0 NOT NULL;

ALTER TABLE EQITMODGRADE ADD PRECOITVARG DECIMAL(15,5) DEFAULT 0 NOT NULL;

ALTER TABLE SGFILIAL ADD CODRECEITA VARCHAR(20);

ALTER TABLE SGPREFERE1 ADD BLOQCOMISSORC CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD BLOQCOMISSVD CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD CALCPRECOG CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD ENDERECOOBRIGCLI CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE1 ADD ENTREGAOBRIGCLI CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGPREFERE3 ADD CODEMPSR INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALSR SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODSETOR INTEGER;

ALTER TABLE VDORCAMENTO ADD ACORC VARCHAR(80);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o recebedor do orçamento (Aos cuidados de)'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='ACORC';

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.7.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKVDSETOR FOREIGN KEY (CODSETOR,CODFILIALSR,CODEMPSR) REFERENCES VDSETOR(CODSETOR,CODFILIAL,CODEMP);

SET TERM ^ ;

ALTER TRIGGER CPITCOMPRATGBI
 AS Declare variable I integer;
BEGIN I = 0; END
^

ALTER TRIGGER CPITCOMPRATGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Alter Procedure... */
/* Alter (CPCOMPRASP01) */
ALTER PROCEDURE CPCOMPRASP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
NQTD NUMERIC(15,5),
NVLRTOT NUMERIC(15,5),
NVLRICMS NUMERIC(15,5),
ADICFRETE CHAR(1),
VLRFRETE NUMERIC(15,5),
ADICADIC CHAR(1),
VLRADIC NUMERIC(15,5))
 RETURNS(NVLRCUSTO NUMERIC(15,5))
 AS
declare variable icasasdec integer;
declare variable ccustosicms char(1);
begin
  /* Procedure Text */
  if (NQTD!=0) then
  begin
    SELECT CUSTOSICMS,CASASDECFIN FROM SGPREFERE1 WHERE CODEMP=:iCodEmp
       AND CODFILIAL=:sCodFilial INTO :cCustoSICMS, :iCasasDec;
    if (iCasasDec is null) then
       iCasasDec = 2;
    if (cCustoSICMS IS NULL) then
       cCustoSICMS = 'N';
    if (cCustoSICMS='S') then
    begin
       select deRetorno
         from arredDouble( (:NVLRTOT - :NVLRICMS) / :NQTD , :ICASASDEC  )
         into :NVLRCUSTO;
    end
    else
    begin
       select deRetorno
         from arredDouble( :NVLRTOT / :NQTD , :ICASASDEC )
         into :NVLRCUSTO;
    end
    -- Adicionando o frete ao valor de custo do item
    if (:adicfrete = 'S' ) then
    begin
        select deRetorno from arredDouble( :nvlrcusto + :vlrfrete, :icasasdec)
          into :nvlrcusto;
    end
    -- Adiconando valores adicionais ao custo do item
    if (:adicadic = 'S') then
    begin
         select deRetorno from arredDouble( :nvlrcusto + :vlradic, :icasasdec)
         into :nvlrcusto;
    end
  end
  else
  begin
    NVLRCUSTO = 0;
  end
  suspend;
end
^

/* Alter (EQADICPRODUTOSP) */
ALTER PROCEDURE EQADICPRODUTOSP(CODEMPPD INTEGER,
CODFILIALPD INTEGER,
CODPROD INTEGER,
DESCPROD VARCHAR(100),
DESCAUXPROD VARCHAR(40),
REFPROD VARCHAR(20),
CODFABPROD CHAR(15),
CODBARPROD CHAR(13),
CODEMPMG INTEGER,
CODFILIALMG SMALLINT,
CODMODG INTEGER,
DESCCOMPPROD VARCHAR(500),
PRECOBASE DECIMAL(15,5),
ATUALIZAPROD CHAR(1))
 AS
declare variable codnovo integer;
declare variable codalmox integer;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codmoeda char(4);
declare variable codempma integer;
declare variable codfilialma integer;
declare variable codunid varchar(20);
declare variable codempud integer;
declare variable codfilialud integer;
declare variable codfisc char(13);
declare variable codempfc integer;
declare variable codfilialfc integer;
declare variable codmarca char(6);
declare variable codempmc integer;
declare variable codfilialmc integer;
declare variable codgrup char(10);
declare variable codempgp integer;
declare variable codfilialgp integer;
declare variable tipoprod varchar(2);
declare variable cvprod char(1);
declare variable cloteprod char(1);
declare variable comisprod numeric(15,5);
declare variable pesoliqprod numeric(15,5);
declare variable pesobrutprod numeric(15,5);
declare variable qtdminprod numeric(15,5);
declare variable qtdmaxprod numeric(15,5);
declare variable refprodsel varchar(20);
BEGIN
  BEGIN
    BEGIN
      codnovo = CAST(REFPROD AS INTEGER);
/*Se não conseguir converter para int causa uma excessão e entra neste bloco: */
      WHEN ANY DO
      BEGIN
        SELECT MAX(CODPROD) FROM EQPRODUTO
           WHERE CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD INTO :codnovo;
        if (codnovo is null) then
           codnovo = 0;
        codnovo = codnovo + 1;
      END
    END
    SELECT CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA,CODFILIALMA,CODUNID
           ,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA
           ,CODEMPMC,CODFILIALMC,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD
           ,CVPROD,CLOTEPROD,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD
           ,QTDMAXPROD
           FROM EQPRODUTO WHERE CODPROD=:Codprod
           AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD
           INTO
           :CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA,:CODUNID
           ,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA
           ,:CODEMPMC,:CODFILIALMC,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD
           ,:CVPROD,:CLOTEPROD,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,
           :QTDMAXPROD;

    select refprod from eqproduto where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod into :refprodsel;

    if( (:ATUALIZAPROD = 'S') AND (coalesce(:refprodsel, '' ) = :refprod )) then
    begin
        update eqproduto p set p.descprod=:descprod, p.desccompprod=:desccompprod, p.descauxprod=:descauxprod, p.precobaseprod=:precobase
        where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod;
    end
    else if (coalesce(:refprodsel, '' ) <> :refprod ) then
    begin
        INSERT INTO EQPRODUTO (CODEMP,CODFILIAL,CODPROD,REFPROD,CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA
              ,CODFILIALMA,CODUNID,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA,CODEMPMC,CODFILIALMC
              ,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD,CVPROD,DESCPROD,DESCAUXPROD,CLOTEPROD,CODBARPROD,CODFABPROD
              ,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD,QTDMAXPROD,PRECOBASEPROD
              ,CODEMPOG, CODFILIALOG, CODPRODOG, CODEMPMG, CODFILIALMG, CODMODG, DESCCOMPPROD)
               VALUES (
                      :CODEMPPD,:CODFILIALPD,:Codnovo,:RefProd,:CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA
                      ,:CODUNID,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA,:CODEMPMC,:CODFILIALMC
                      ,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD,:CVPROD,:Descprod,:DescAuxprod,:CLOTEPROD,:Codbarprod,:Codfabprod
                      ,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,:QTDMAXPROD,:PRECOBASE
                      ,:CODEMPPD, :CODFILIALPD, :CODPROD, :CODEMPMG, :CODFILIALMG, :CODMODG, :DESCCOMPPROD
               );
        INSERT INTO VDPRECOPROD (CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD)
               SELECT :CODEMPPD,:CODFILIALPD,:Codnovo,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,
                      CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD FROM VDPRECOPROD WHERE CODPROD=:Codprod
                      AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD;
    end
  END
--  SUSPEND;
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
            on lt.codemp=p.codemp and lt.codfilial=p.codfilial and lt.codprod=p.codprod and (:soprodsaldo='S' or  SLDLOTE <> 0)
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
             p.codgrup like :ccodgrup) ) and (:soprodsaldo='N' or  sldprod <> 0)
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            suspend;
        end
  end 


end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.5.8 (22/03/2013)';
    suspend;
end
^

/* Alter (VDADICVENDAORCSP) */
ALTER PROCEDURE VDADICVENDAORCSP(ICODORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
NEWCODVENDA INTEGER)
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
    CAST('today' AS DATE),CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC
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

/* Alter (VDCOPIAORCSP) */
ALTER PROCEDURE VDCOPIAORCSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
CODFILIALCL INTEGER,
CODCLI INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable icodorc integer;
declare variable icodfilialpf integer;
declare variable cusaorcseq char(1);
declare variable vlrprodorc decimal(15,5);
declare variable percdescorc decimal(9,2);
declare variable vlrdescorc decimal(15,5);
declare variable vlradicorc decimal(15,5);
declare variable vlrdescitorc decimal(15,5);
declare variable vlrliqorc decimal(15,5);
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'SGPREFERE1')
    INTO :ICODFILIALPF;
  SELECT USAORCSEQ FROM SGPREFERE1 P
     WHERE P.CODEMP=:CODEMP AND P.CODFILIAL=:ICODFILIALPF
     INTO :CUSAORCSEQ;
  IF (CUSAORCSEQ='S') THEN
  BEGIN
     SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'OC') INTO ICODORC;
  END
  ELSE
  BEGIN
     SELECT COALESCE(MAX(O.CODORC),0)+1 FROM VDORCAMENTO O
       WHERE O.CODEMP=:CODEMP AND O.CODFILIAL=:CODFILIAL AND
       O.TIPOORC='O' INTO ICODORC;
  END

  INSERT INTO VDORCAMENTO (CODEMP, CODFILIAL, TIPOORC, CODORC,
    CODEMPAE, CODFILIALAE, CODATEND, CODEMPCV, CODFILIALCV, CODCONV,
    DTORC, DTVENCORC, OBSORC, CODPLANOPAG, CODFILIALPG,
    CODEMPPG, TXT01, CODEMPVD, CODFILIALVD, CODVEND, CODEMPCL, CODFILIALCL,
    CODCLI, CODTPCONV, CODFILIALTC, CODEMPTC, PRAZOENTORC, STATUSORC, VLRFRETEORC, ADICFRETE,
    CODEMPTM, CODFILIALTM, CODTRAN

    )
    SELECT CODEMP, CODFILIAL, 'O', :ICODORC, CODEMPAE, CODFILIALAE,
      CODATEND, CODEMPCV, CODFILIALCV, CODCONV, DTORC, DTVENCORC,
      OBSORC, CODPLANOPAG, CODFILIALPG, CODEMPPG, TXT01, CODEMPVD,
      CODFILIALVD, CODVEND, :CODEMP, :CODFILIALCL, :CODCLI, CODTPCONV,
      CODFILIALTC, CODEMPTC, PRAZOENTORC, '*', VLRFRETEORC, ADICFRETE,
      CODEMPTM, CODFILIALTM, CODTRAN

      FROM VDORCAMENTO WHERE
      CODORC=:CODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  INSERT INTO VDITORCAMENTO (CODEMP, CODFILIAL, TIPOORC, CODORC, CODITORC,
    CODEMPPD, CODFILIALPD, CODPROD, QTDITORC, PRECOITORC, PERCDESCITORC,
    VLRDESCITORC, VLRFRETEITORC, VLRPRODITORC, REFPROD, NUMAUTORIZORC, ACEITEITORC,
    APROVITORC, EMITITORC, VENCAUTORIZORC, STRDESCITORC, OBSITORC,
    CODEMPAX, CODFILIALAX, CODALMOX)
    SELECT CODEMP, CODFILIAL, TIPOORC, :ICODORC, CODITORC,
      CODEMPPD, CODFILIALPD, CODPROD, QTDITORC, PRECOITORC, PERCDESCITORC,
      VLRDESCITORC, VLRFRETEITORC, VLRPRODITORC, REFPROD, null, 'N',
      'N', 'N', VENCAUTORIZORC, STRDESCITORC, OBSITORC,
      CODEMPAX, CODFILIALAX, CODALMOX
      FROM VDITORCAMENTO
      WHERE CODORC=:CODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  SELECT VLRPRODORC, PERCDESCORC,VLRDESCORC,VLRADICORC,VLRDESCITORC,VLRLIQORC
      FROM VDORCAMENTO WHERE CODORC=:CODORC AND CODEMP=:CODEMP AND
      CODFILIAL=:CODFILIAL
      INTO :VLRPRODORC, :PERCDESCORC, :VLRDESCORC, :VLRADICORC, :VLRDESCITORC, :VLRLIQORC;

  UPDATE VDORCAMENTO SET VLRPRODORC=:VLRPRODORC, PERCDESCORC=:PERCDESCORC,
     VLRDESCORC=:VLRDESCORC, VLRADICORC=:VLRADICORC,
     VLRDESCITORC=:VLRDESCITORC, VLRLIQORC=:VLRLIQORC
     WHERE CODORC=:ICODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  IRET = ICODORC;
  SUSPEND;
END
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
    if ( ( ('N' = habCustoCompra) or (new.custoitcompra is null) ) and (new.calccusto='S') ) then
    begin
        if (utilizatbcalcca='N') then
        begin
            select nvlrcusto
            from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra
            , new.vlricmsitcompra,'N',0,'N',0)
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
                    select nvlrcusto from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra
                    , :sadicfrete, new.vlrfreteitcompra, :sadicadic, new.vlradicitcompra)
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
ALTER TRIGGER VDITORCAMENTOTGBI
as
    declare variable refprod VARchar(20);
    declare variable vlripi numeric(15,5);
    declare variable contribipi char(1);
    declare variable adicfrete char(1);

begin

    select fi.contribipifilial from sgfilial fi where fi.codemp=new.codemp and fi.codfilial=new.codfilial
    into contribipi;

    if(contribipi = 'S') then
    begin
        select pt.vlripiitorc from vdprevtribitorc pt
        where pt.codemp=new.codemp and pt.codfilial=new.codfilial and pt.codorc=new.codorc and pt.tipoorc=new.tipoorc
        and pt.coditorc=new.coditorc
        into vlripi;
    end

    -- Garantindo valor válido para IPI
    if (vlripi is null) then vlripi = 0;

    -- Garantindo valor válido para desconto
    if (new.vlrdescitorc is null) then new.vlrdescitorc = 0;

    -- Garantindo valor válido para vlrliqitorc
    if (new.vlrliqitorc is null) then new.vlrliqitorc = 0;

    select adicfrete from vdorcamento
    where codemp=new.codemp and codfilial=new.codfilial and codorc=new.codorc and tipoorc=new.tipoorc
    into adicfrete;

    -- Calculando valor liquido
    if (new.vlrliqitorc = 0) then
    begin
        new.vlrliqitorc = (new.qtditorc * new.precoitorc) - new.vlrdescitorc + vlripi;
    end

    if( adicfrete = 'S' ) then
    begin
       new.vlrliqitorc = new.vlrliqitorc + coalesce(new.vlrfreteitorc,0);
    end

    new.tipoorc = 'O';

    -- Garantindo a gravação da referência do produto.

    if (new.refprod is null) then
    begin
        select p.refprod from eqproduto p
        where p.codprod=new.codprod and p.codemp=new.codemppd and p.codfilial=new.codfilialpd
        into refprod;

        new.refprod = refprod;
    end

    -- Atualizando a tabela de orçamento

    update vdorcamento o set o.vlrdescitorc=o.vlrdescitorc + new.vlrdescitorc,
                             o.vlrprodorc = o.vlrprodorc + new.vlrproditorc,
                             o.vlrliqorc = o.vlrliqorc + new.vlrliqitorc
    where o.codorc=new.codorc and o.tipoorc=new.tipoorc and o.codemp=new.codemp and o.codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGBU
as
    declare variable adicfrete char(1);
    declare variable tipoprod varchar(2);
    declare variable encorcprod char(1);
    declare variable vlripi decimal(15,5);
    declare variable contribipi char(1);

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
        
        select fi.contribipifilial from sgfilial fi where fi.codemp=new.codemp and fi.codfilial=new.codfilial
        into contribipi;

        if(contribipi = 'S') then
        begin
            select pt.vlripiitorc from vdprevtribitorc pt
            where pt.codemp=new.codemp and pt.codfilial=new.codfilial and pt.codorc=new.codorc and pt.tipoorc=new.tipoorc
            and pt.coditorc=new.coditorc
            into vlripi;
        end

        -- Garantindo valor válido para IPI
        if (vlripi is null) then vlripi = 0;

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

        new.vlrliqitorc = (new.qtditorc * new.precoitorc) - new.vlrdescitorc + :vlripi;

        if( adicfrete = 'S' ) then
        begin
            new.vlrliqitorc = new.vlrliqitorc + coalesce(new.vlrfreteitorc,0);
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
ALTER TRIGGER VDORCAMENTOTGAU
as

    declare variable coditorc integer;
    declare variable percitfrete numeric(15, 5);
    declare variable vlritfrete numeric(15, 5);
    declare variable vlrproditorc numeric(15, 5);

begin

    for select io.coditorc, io.vlrproditorc
    from vditorcamento io
    where io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and io.tipoorc=new.tipoorc
    into :coditorc, :vlrproditorc
    do
    begin

        -- distribuição do frete
        if ( (new.vlrfreteorc != old.vlrfreteorc) or (new.adicfrete!=old.adicfrete)  ) then
        begin
           percitfrete = :vlrproditorc / new.vlrprodorc ;
           vlritfrete =  :percitfrete * new.vlrfreteorc ;


           -- atualizando tabela de ítens
            update vditorcamento io set
            io.vlrfreteitorc = :vlritfrete
            where
            io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and
            io.coditorc=:coditorc and io.tipoorc=new.tipoorc;
        end

        -- Atualização do STATUS desde que o orçamento não tenha sido faturado parcialmente.
        if(old.statusorc != new.statusorc) then
        begin
            update vditorcamento io set io.statusitorc=new.statusorc
            where io.codemp=new.codemp and io.codfilial=new.codfilial
            and io.codorc=new.codorc and io.tipoorc=new.tipoorc
            and io.statusitorc!=new.statusorc;
        end

    end

end
^

/* Alter Procedure... */
/* Alter (CPCOMPRASP01) */
ALTER PROCEDURE CPCOMPRASP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
NQTD NUMERIC(15,5),
NVLRTOT NUMERIC(15,5),
NVLRICMS NUMERIC(15,5),
ADICFRETE CHAR(1),
VLRFRETE NUMERIC(15,5),
ADICADIC CHAR(1),
VLRADIC NUMERIC(15,5))
 RETURNS(NVLRCUSTO NUMERIC(15,5))
 AS
declare variable icasasdec integer;
declare variable ccustosicms char(1);
begin
  /* Procedure Text */
  if (NQTD!=0) then
  begin
    SELECT CUSTOSICMS,CASASDECFIN FROM SGPREFERE1 WHERE CODEMP=:iCodEmp
       AND CODFILIAL=:sCodFilial INTO :cCustoSICMS, :iCasasDec;
    if (iCasasDec is null) then
       iCasasDec = 2;
    if (cCustoSICMS IS NULL) then
       cCustoSICMS = 'N';
    if (cCustoSICMS='S') then
    begin
       select deRetorno
         from arredDouble( (:NVLRTOT - :NVLRICMS) / :NQTD , :ICASASDEC  )
         into :NVLRCUSTO;
    end
    else
    begin
       select deRetorno
         from arredDouble( :NVLRTOT / :NQTD , :ICASASDEC )
         into :NVLRCUSTO;
    end
    -- Adicionando o frete ao valor de custo do item
    if (:adicfrete = 'S' ) then
    begin
        select deRetorno from arredDouble( :nvlrcusto + :vlrfrete, :icasasdec)
          into :nvlrcusto;
    end
    -- Adiconando valores adicionais ao custo do item
    if (:adicadic = 'S') then
    begin
         select deRetorno from arredDouble( :nvlrcusto + :vlradic, :icasasdec)
         into :nvlrcusto;
    end
  end
  else
  begin
    NVLRCUSTO = 0;
  end
  suspend;
end
^

/* Alter (EQADICPRODUTOSP) */
ALTER PROCEDURE EQADICPRODUTOSP(CODEMPPD INTEGER,
CODFILIALPD INTEGER,
CODPROD INTEGER,
DESCPROD VARCHAR(100),
DESCAUXPROD VARCHAR(40),
REFPROD VARCHAR(20),
CODFABPROD CHAR(15),
CODBARPROD CHAR(13),
CODEMPMG INTEGER,
CODFILIALMG SMALLINT,
CODMODG INTEGER,
DESCCOMPPROD VARCHAR(500),
PRECOBASE DECIMAL(15,5),
ATUALIZAPROD CHAR(1))
 AS
declare variable codnovo integer;
declare variable codalmox integer;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codmoeda char(4);
declare variable codempma integer;
declare variable codfilialma integer;
declare variable codunid varchar(20);
declare variable codempud integer;
declare variable codfilialud integer;
declare variable codfisc char(13);
declare variable codempfc integer;
declare variable codfilialfc integer;
declare variable codmarca char(6);
declare variable codempmc integer;
declare variable codfilialmc integer;
declare variable codgrup char(10);
declare variable codempgp integer;
declare variable codfilialgp integer;
declare variable tipoprod varchar(2);
declare variable cvprod char(1);
declare variable cloteprod char(1);
declare variable comisprod numeric(15,5);
declare variable pesoliqprod numeric(15,5);
declare variable pesobrutprod numeric(15,5);
declare variable qtdminprod numeric(15,5);
declare variable qtdmaxprod numeric(15,5);
declare variable refprodsel varchar(20);
BEGIN
  BEGIN
    BEGIN
      codnovo = CAST(REFPROD AS INTEGER);
/*Se não conseguir converter para int causa uma excessão e entra neste bloco: */
      WHEN ANY DO
      BEGIN
        SELECT MAX(CODPROD) FROM EQPRODUTO
           WHERE CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD INTO :codnovo;
        if (codnovo is null) then
           codnovo = 0;
        codnovo = codnovo + 1;
      END
    END
    SELECT CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA,CODFILIALMA,CODUNID
           ,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA
           ,CODEMPMC,CODFILIALMC,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD
           ,CVPROD,CLOTEPROD,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD
           ,QTDMAXPROD
           FROM EQPRODUTO WHERE CODPROD=:Codprod
           AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD
           INTO
           :CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA,:CODUNID
           ,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA
           ,:CODEMPMC,:CODFILIALMC,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD
           ,:CVPROD,:CLOTEPROD,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,
           :QTDMAXPROD;

    select refprod from eqproduto where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod into :refprodsel;

    if( (:ATUALIZAPROD = 'S') AND (coalesce(:refprodsel, '' ) = :refprod )) then
    begin
        update eqproduto p set p.descprod=:descprod, p.desccompprod=:desccompprod, p.descauxprod=:descauxprod, p.precobaseprod=:precobase
        where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod;
    end
    else if (coalesce(:refprodsel, '' ) <> :refprod ) then
    begin
        INSERT INTO EQPRODUTO (CODEMP,CODFILIAL,CODPROD,REFPROD,CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA
              ,CODFILIALMA,CODUNID,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA,CODEMPMC,CODFILIALMC
              ,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD,CVPROD,DESCPROD,DESCAUXPROD,CLOTEPROD,CODBARPROD,CODFABPROD
              ,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD,QTDMAXPROD,PRECOBASEPROD
              ,CODEMPOG, CODFILIALOG, CODPRODOG, CODEMPMG, CODFILIALMG, CODMODG, DESCCOMPPROD)
               VALUES (
                      :CODEMPPD,:CODFILIALPD,:Codnovo,:RefProd,:CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA
                      ,:CODUNID,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA,:CODEMPMC,:CODFILIALMC
                      ,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD,:CVPROD,:Descprod,:DescAuxprod,:CLOTEPROD,:Codbarprod,:Codfabprod
                      ,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,:QTDMAXPROD,:PRECOBASE
                      ,:CODEMPPD, :CODFILIALPD, :CODPROD, :CODEMPMG, :CODFILIALMG, :CODMODG, :DESCCOMPPROD
               );
        INSERT INTO VDPRECOPROD (CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD)
               SELECT :CODEMPPD,:CODFILIALPD,:Codnovo,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,
                      CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD FROM VDPRECOPROD WHERE CODPROD=:Codprod
                      AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD;
    end
  END
--  SUSPEND;
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
            on lt.codemp=p.codemp and lt.codfilial=p.codfilial and lt.codprod=p.codprod and (:soprodsaldo='S' or  SLDLOTE <> 0)
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
             p.codgrup like :ccodgrup) ) and (:soprodsaldo='N' or  sldprod <> 0)
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            suspend;
        end
  end 


end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.5.8 (22/03/2013)';
    suspend;
end
^

/* Alter (VDADICVENDAORCSP) */
ALTER PROCEDURE VDADICVENDAORCSP(ICODORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
NEWCODVENDA INTEGER)
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
    CAST('today' AS DATE),CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC
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

/* Alter (VDCOPIAORCSP) */
ALTER PROCEDURE VDCOPIAORCSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
CODFILIALCL INTEGER,
CODCLI INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable icodorc integer;
declare variable icodfilialpf integer;
declare variable cusaorcseq char(1);
declare variable vlrprodorc decimal(15,5);
declare variable percdescorc decimal(9,2);
declare variable vlrdescorc decimal(15,5);
declare variable vlradicorc decimal(15,5);
declare variable vlrdescitorc decimal(15,5);
declare variable vlrliqorc decimal(15,5);
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP, 'SGPREFERE1')
    INTO :ICODFILIALPF;
  SELECT USAORCSEQ FROM SGPREFERE1 P
     WHERE P.CODEMP=:CODEMP AND P.CODFILIAL=:ICODFILIALPF
     INTO :CUSAORCSEQ;
  IF (CUSAORCSEQ='S') THEN
  BEGIN
     SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'OC') INTO ICODORC;
  END
  ELSE
  BEGIN
     SELECT COALESCE(MAX(O.CODORC),0)+1 FROM VDORCAMENTO O
       WHERE O.CODEMP=:CODEMP AND O.CODFILIAL=:CODFILIAL AND
       O.TIPOORC='O' INTO ICODORC;
  END

  INSERT INTO VDORCAMENTO (CODEMP, CODFILIAL, TIPOORC, CODORC,
    CODEMPAE, CODFILIALAE, CODATEND, CODEMPCV, CODFILIALCV, CODCONV,
    DTORC, DTVENCORC, OBSORC, CODPLANOPAG, CODFILIALPG,
    CODEMPPG, TXT01, CODEMPVD, CODFILIALVD, CODVEND, CODEMPCL, CODFILIALCL,
    CODCLI, CODTPCONV, CODFILIALTC, CODEMPTC, PRAZOENTORC, STATUSORC, VLRFRETEORC, ADICFRETE,
    CODEMPTM, CODFILIALTM, CODTRAN

    )
    SELECT CODEMP, CODFILIAL, 'O', :ICODORC, CODEMPAE, CODFILIALAE,
      CODATEND, CODEMPCV, CODFILIALCV, CODCONV, DTORC, DTVENCORC,
      OBSORC, CODPLANOPAG, CODFILIALPG, CODEMPPG, TXT01, CODEMPVD,
      CODFILIALVD, CODVEND, :CODEMP, :CODFILIALCL, :CODCLI, CODTPCONV,
      CODFILIALTC, CODEMPTC, PRAZOENTORC, '*', VLRFRETEORC, ADICFRETE,
      CODEMPTM, CODFILIALTM, CODTRAN

      FROM VDORCAMENTO WHERE
      CODORC=:CODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  INSERT INTO VDITORCAMENTO (CODEMP, CODFILIAL, TIPOORC, CODORC, CODITORC,
    CODEMPPD, CODFILIALPD, CODPROD, QTDITORC, PRECOITORC, PERCDESCITORC,
    VLRDESCITORC, VLRFRETEITORC, VLRPRODITORC, REFPROD, NUMAUTORIZORC, ACEITEITORC,
    APROVITORC, EMITITORC, VENCAUTORIZORC, STRDESCITORC, OBSITORC,
    CODEMPAX, CODFILIALAX, CODALMOX)
    SELECT CODEMP, CODFILIAL, TIPOORC, :ICODORC, CODITORC,
      CODEMPPD, CODFILIALPD, CODPROD, QTDITORC, PRECOITORC, PERCDESCITORC,
      VLRDESCITORC, VLRFRETEITORC, VLRPRODITORC, REFPROD, null, 'N',
      'N', 'N', VENCAUTORIZORC, STRDESCITORC, OBSITORC,
      CODEMPAX, CODFILIALAX, CODALMOX
      FROM VDITORCAMENTO
      WHERE CODORC=:CODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  SELECT VLRPRODORC, PERCDESCORC,VLRDESCORC,VLRADICORC,VLRDESCITORC,VLRLIQORC
      FROM VDORCAMENTO WHERE CODORC=:CODORC AND CODEMP=:CODEMP AND
      CODFILIAL=:CODFILIAL
      INTO :VLRPRODORC, :PERCDESCORC, :VLRDESCORC, :VLRADICORC, :VLRDESCITORC, :VLRLIQORC;

  UPDATE VDORCAMENTO SET VLRPRODORC=:VLRPRODORC, PERCDESCORC=:PERCDESCORC,
     VLRDESCORC=:VLRDESCORC, VLRADICORC=:VLRADICORC,
     VLRDESCITORC=:VLRDESCITORC, VLRLIQORC=:VLRLIQORC
     WHERE CODORC=:ICODORC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL;

  IRET = ICODORC;
  SUSPEND;
END
^

SET TERM ; ^

ALTER TABLE EQITMODGRADE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODMODG POSITION 3;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODITMODG POSITION 4;

ALTER TABLE EQITMODGRADE ALTER COLUMN ORDEMITMODG POSITION 5;

ALTER TABLE EQITMODGRADE ALTER COLUMN REFITMODG POSITION 6;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODEMPVG POSITION 7;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODFILIALVG POSITION 8;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODVARG POSITION 9;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODFABITMODG POSITION 10;

ALTER TABLE EQITMODGRADE ALTER COLUMN CODBARITMODG POSITION 11;

ALTER TABLE EQITMODGRADE ALTER COLUMN DESCITMODG POSITION 12;

ALTER TABLE EQITMODGRADE ALTER COLUMN DESCCOMPITMODG POSITION 13;

ALTER TABLE EQITMODGRADE ALTER COLUMN PRECOITVARG POSITION 14;

ALTER TABLE EQITMODGRADE ALTER COLUMN DTINS POSITION 15;

ALTER TABLE EQITMODGRADE ALTER COLUMN HINS POSITION 16;

ALTER TABLE EQITMODGRADE ALTER COLUMN IDUSUINS POSITION 17;

ALTER TABLE EQITMODGRADE ALTER COLUMN DTALT POSITION 18;

ALTER TABLE EQITMODGRADE ALTER COLUMN HALT POSITION 19;

ALTER TABLE EQITMODGRADE ALTER COLUMN IDUSUALT POSITION 20;

ALTER TABLE SGFILIAL ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGFILIAL ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGFILIAL ALTER COLUMN RAZFILIAL POSITION 3;

ALTER TABLE SGFILIAL ALTER COLUMN NOMEFILIAL POSITION 4;

ALTER TABLE SGFILIAL ALTER COLUMN MZFILIAL POSITION 5;

ALTER TABLE SGFILIAL ALTER COLUMN CNPJFILIAL POSITION 6;

ALTER TABLE SGFILIAL ALTER COLUMN INSCFILIAL POSITION 7;

ALTER TABLE SGFILIAL ALTER COLUMN ENDFILIAL POSITION 8;

ALTER TABLE SGFILIAL ALTER COLUMN NUMFILIAL POSITION 9;

ALTER TABLE SGFILIAL ALTER COLUMN COMPLFILIAL POSITION 10;

ALTER TABLE SGFILIAL ALTER COLUMN BAIRFILIAL POSITION 11;

ALTER TABLE SGFILIAL ALTER COLUMN CEPFILIAL POSITION 12;

ALTER TABLE SGFILIAL ALTER COLUMN CIDFILIAL POSITION 13;

ALTER TABLE SGFILIAL ALTER COLUMN UFFILIAL POSITION 14;

ALTER TABLE SGFILIAL ALTER COLUMN DDDFILIAL POSITION 15;

ALTER TABLE SGFILIAL ALTER COLUMN FONEFILIAL POSITION 16;

ALTER TABLE SGFILIAL ALTER COLUMN FAXFILIAL POSITION 17;

ALTER TABLE SGFILIAL ALTER COLUMN EMAILFILIAL POSITION 18;

ALTER TABLE SGFILIAL ALTER COLUMN WWWFILIAL POSITION 19;

ALTER TABLE SGFILIAL ALTER COLUMN CODDISTFILIAL POSITION 20;

ALTER TABLE SGFILIAL ALTER COLUMN PERCPISFILIAL POSITION 21;

ALTER TABLE SGFILIAL ALTER COLUMN PERCCOFINSFILIAL POSITION 22;

ALTER TABLE SGFILIAL ALTER COLUMN PERCIRFILIAL POSITION 23;

ALTER TABLE SGFILIAL ALTER COLUMN PERCCSOCIALFILIAL POSITION 24;

ALTER TABLE SGFILIAL ALTER COLUMN SIMPLESFILIAL POSITION 25;

ALTER TABLE SGFILIAL ALTER COLUMN PERCSIMPLESFILIAL POSITION 26;

ALTER TABLE SGFILIAL ALTER COLUMN CODMUNIC POSITION 27;

ALTER TABLE SGFILIAL ALTER COLUMN SIGLAUF POSITION 28;

ALTER TABLE SGFILIAL ALTER COLUMN CODPAIS POSITION 29;

ALTER TABLE SGFILIAL ALTER COLUMN CODEMPUC POSITION 30;

ALTER TABLE SGFILIAL ALTER COLUMN CODFILIALUC POSITION 31;

ALTER TABLE SGFILIAL ALTER COLUMN CODUNIFCOD POSITION 32;

ALTER TABLE SGFILIAL ALTER COLUMN INSCMUNFILIAL POSITION 33;

ALTER TABLE SGFILIAL ALTER COLUMN CNAEFILIAL POSITION 34;

ALTER TABLE SGFILIAL ALTER COLUMN PERCISSFILIAL POSITION 35;

ALTER TABLE SGFILIAL ALTER COLUMN CONTRIBIPIFILIAL POSITION 36;

ALTER TABLE SGFILIAL ALTER COLUMN TIMBREFILIAL POSITION 37;

ALTER TABLE SGFILIAL ALTER COLUMN PERFILFILIAL POSITION 38;

ALTER TABLE SGFILIAL ALTER COLUMN INDATIVFILIAL POSITION 39;

ALTER TABLE SGFILIAL ALTER COLUMN INDNATPJFILIAL POSITION 40;

ALTER TABLE SGFILIAL ALTER COLUMN CODEMPCO POSITION 41;

ALTER TABLE SGFILIAL ALTER COLUMN CODFILIALCO POSITION 42;

ALTER TABLE SGFILIAL ALTER COLUMN CODFOR POSITION 43;

ALTER TABLE SGFILIAL ALTER COLUMN SUFRAMA POSITION 44;

ALTER TABLE SGFILIAL ALTER COLUMN CODINCTRIB POSITION 45;

ALTER TABLE SGFILIAL ALTER COLUMN INDAPROCRED POSITION 46;

ALTER TABLE SGFILIAL ALTER COLUMN CODTIPOCONT POSITION 47;

ALTER TABLE SGFILIAL ALTER COLUMN INDREGCUM POSITION 48;

ALTER TABLE SGFILIAL ALTER COLUMN UNIDFRANQUEADA POSITION 49;

ALTER TABLE SGFILIAL ALTER COLUMN MARCAFRANQUEADORA POSITION 50;

ALTER TABLE SGFILIAL ALTER COLUMN WWWFRANQUEADORA POSITION 51;

ALTER TABLE SGFILIAL ALTER COLUMN CODRECEITA POSITION 52;

ALTER TABLE SGFILIAL ALTER COLUMN DTINS POSITION 53;

ALTER TABLE SGFILIAL ALTER COLUMN HINS POSITION 54;

ALTER TABLE SGFILIAL ALTER COLUMN IDUSUINS POSITION 55;

ALTER TABLE SGFILIAL ALTER COLUMN DTALT POSITION 56;

ALTER TABLE SGFILIAL ALTER COLUMN HALT POSITION 57;

ALTER TABLE SGFILIAL ALTER COLUMN IDUSUALT POSITION 58;

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

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 305;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 306;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 307;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 308;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 309;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 310;

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

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPSR POSITION 96;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALSR POSITION 97;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODSETOR POSITION 98;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTINS POSITION 99;

ALTER TABLE SGPREFERE3 ALTER COLUMN HINS POSITION 100;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUINS POSITION 101;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTALT POSITION 102;

ALTER TABLE SGPREFERE3 ALTER COLUMN HALT POSITION 103;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUALT POSITION 104;

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

ALTER TABLE VDORCAMENTO ALTER COLUMN ACORC POSITION 54;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTINS POSITION 55;

ALTER TABLE VDORCAMENTO ALTER COLUMN HINS POSITION 56;

ALTER TABLE VDORCAMENTO ALTER COLUMN IDUSUINS POSITION 57;

ALTER TABLE VDORCAMENTO ALTER COLUMN DTALT POSITION 58;

ALTER TABLE VDORCAMENTO ALTER COLUMN HALT POSITION 59;

ALTER TABLE VDORCAMENTO ALTER COLUMN IDUSUALT POSITION 60;


COMMIT WORK;
