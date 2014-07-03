/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.6.1.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

SET AUTODDL ON;

ALTER TABLE EQPRODUTO ADD MEDIAVENDA NUMERIC(15,5);

ALTER TABLE LFCLFISCAL ADD CST CHAR(2);

ALTER TABLE LFCLFISCAL ADD CODIGO VARCHAR(10);

/* Create Table... */
CREATE TABLE EQPREVESTOQLOG(ID BIGINT NOT NULL,
CODEMP INTEGER,
CODFILIAL SMALLINT,
DTINI DATE NOT NULL,
DTFIM DATE NOT NULL,
CODEMPGP INTEGER,
CODFILIALGP SMALLINT,
CODGRUP CHAR(14),
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
ORDEM CHAR(1) DEFAULT 'V' NOT NULL,
MERCADORIA_REVENDA CHAR(1) DEFAULT 'S' NOT NULL,
MATERIA_PRIMA CHAR(1) DEFAULT 'S' NOT NULL,
EM_PROCESSO CHAR(1) DEFAULT 'S' NOT NULL,
OUTROS CHAR(1) DEFAULT 'S' NOT NULL,
SUB_PRODUTO CHAR(1) DEFAULT 'S' NOT NULL,
EQUIPAMENTO CHAR(1) DEFAULT 'S' NOT NULL,
MATERIAL_CONSUMO CHAR(1) DEFAULT 'S' NOT NULL,
PRODUTO_INTERMED CHAR(1) DEFAULT 'S' NOT NULL,
PRODUTO_ACABADO CHAR(1) DEFAULT 'S' NOT NULL,
EMBALAGEM CHAR(1) DEFAULT 'S' NOT NULL,
OUTROS_INSUMOS CHAR(1) DEFAULT 'S' NOT NULL,
DTINS DATE DEFAULT 'today' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'today' NOT NULL,
HALT TIME DEFAULT 'now' NOT NULL,
IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL);



/* Create Primary Key... */
ALTER TABLE EQPREVESTOQLOG ADD CONSTRAINT EQPREVESTOQLOG PRIMARY KEY (ID);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.6.1.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

ALTER TABLE LFCLFISCAL ADD CONSTRAINT LFCLFISCALFKLFNATOPER FOREIGN KEY (CODIGO,CST) REFERENCES SPNATOPER(CODIGO,CST);

/* Alter Procedure... */
/* Alter (PPGERAOPCQ) */
SET TERM ^ ;

ALTER PROCEDURE PPGERAOPCQ(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable iseqef smallint;
declare variable iseqopcq smallint;
declare variable icodempea integer;
declare variable icodfilialea smallint;
declare variable icodestanalise integer;
BEGIN

    SELECT COALESCE(MAX(SEQOPCQ),0)
        FROM PPOPCQ
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
                AND CODOP=:ICODOP AND SEQOP=:ISEQOP
    INTO :ISEQOPCQ;

    FOR
        SELECT EA.SEQEF, EA.codemp,EA.codfilial,EA.codestanalise
            FROM PPESTRUANALISE EA, PPOP O, PPESTRUTURA E, ppfase f
            WHERE O.CODEMP=:iCodEmp AND O.CODFILIAL=:iCodFilial
                AND O.CODOP=:iCodOp AND O.SEQOP=:iSEQOP AND E.CODEMP=O.CODEMPPD
                AND E.CODFILIAL=O.CODFILIALPD AND E.CODPROD=O.CODPROD AND E.SEQEST=O.SEQEST
                AND EA.CODEMP=E.CODEMP AND EA.CODFILIAL=E.CODFILIAL AND EA.CODPROD=E.CODPROD
                AND EA.SEQEST=E.SEQEST
                and f.codemp=ea.codempfs and f.codfilial=ea.codfilialfs and f.codfase=ea.codfase
                and f.tipofase='CQ'
        INTO :iSeqEf, :ICODEMPEA,:ICODFILIALEA,:ICODESTANALISE
    DO
    BEGIN
        ISEQOPCQ = :ISEQOPCQ + 1;
        INSERT INTO PPOPCQ (CODEMP,CODFILIAL,CODOP,SEQOP,SEQOPCQ,
            CODEMPEA,CODFILIALEA,CODESTANALISE, SEQEF)
            VALUES (:iCodEmp,:iCodFilial,:iCodOp,:iSeqOp, :ISEQOPCQ,:ICODEMPEA,
                    :ICODFILIALEA,:ICODESTANALISE,:iseqef);
    END

END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.2 (05/06/2013)';
    suspend;
end
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
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    :DTSAIDAVENDA,CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP
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

/* Alter (VDATUDESCVENDAORCSP) */
ALTER PROCEDURE VDATUDESCVENDAORCSP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
TIPOVENDA CHAR(1),
CODVENDA INTEGER)
 AS
declare variable vlrdescorc numeric(15,5);
declare variable vlrtotdesc numeric(15,5) = 0;
declare variable codorc integer;
declare variable conta1 numeric(15,5);
declare variable statusorc char(2);
declare variable conta2 numeric(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
begin
    -- verifica a quantidade total do orçamneto vinculado a venda
    select first 1 oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc, sum(iv.qtditvenda)
      from vdorcamento oc, vdvendaorc vo, vditorcamento itoc, vditvenda iv
        where vo.codemp=:codempvd and vo.codfilial=:codfilialvd and
          vo.tipovenda=:tipovenda and vo.codvenda=:codvenda and
          oc.codemp=vo.codempor and oc.codfilial=vo.codfilialor and
          oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc and
          itoc.codemp=oc.codemp and itoc.codfilial=oc.codfilial and
          itoc.tipoorc=oc.tipoorc and itoc.codorc=oc.codorc and
          itoc.coditorc=vo.coditorc and
          iv.codemp=vo.codemp and iv.codfilial=vo.codfilial and
          iv.tipovenda=vo.tipovenda and iv.codvenda=vo.codvenda and
          iv.coditvenda=vo.coditvenda
        group by oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc
        into :codempoc, :codfilialoc, :tipoorc, :codorc, :statusorc, :conta1;

    if (:statusorc not in ('FP') ) then
    begin
        -- verifica a quantidade total do orçamento
        select sum(qtditorc) from vditorcamento it
          where codemp=:codempoc and codfilial=:codfilialoc and
          tipoorc=:tipoorc and codorc=:codorc
          into :conta2;
        if (conta1=conta2) then
        begin
            -- Buscando desconto nos orçamentos dessa venda
            for select vo.codorc, oc.vlrdescorc from vdvendaorc vo, vdorcamento oc
            where
              vo.codemp=:CODEMPVD and vo.codfilial=:CODFILIALVD and vo.tipovenda=:TIPOVENDA and vo.codvenda=:CODVENDA and
              oc.codemp=vo.codempor and oc.codfilial=vo.codfilial and oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc
            group by 1,2
            into :codorc,:vlrdescorc
            do
            begin
                VLRTOTDESC = :VLRTOTDESC + :VLRDESCORC;
            end
            -- Atualizando desconto na venda
            if(:VLRTOTDESC is not null and :VLRTOTDESC>0) then
            begin
               --update vdvenda set vlrdescvenda = :VLRTOTDESC
               --where codemp=:CODEMPVD and codfilial=:CODFILIALVD and tipovenda=:TIPOVENDA and codvenda=:CODVENDA;
            end
        end
    end
end
^

/* Create Trigger... */
CREATE TRIGGER EQPREVESTOQLOGTGBU FOR EQPREVESTOQLOG
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^


/* Alter Procedure... */
/* Alter (PPGERAOPCQ) */
ALTER PROCEDURE PPGERAOPCQ(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable iseqef smallint;
declare variable iseqopcq smallint;
declare variable icodempea integer;
declare variable icodfilialea smallint;
declare variable icodestanalise integer;
BEGIN

    SELECT COALESCE(MAX(SEQOPCQ),0)
        FROM PPOPCQ
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
                AND CODOP=:ICODOP AND SEQOP=:ISEQOP
    INTO :ISEQOPCQ;

    FOR
        SELECT EA.SEQEF, EA.codemp,EA.codfilial,EA.codestanalise
            FROM PPESTRUANALISE EA, PPOP O, PPESTRUTURA E, ppfase f
            WHERE O.CODEMP=:iCodEmp AND O.CODFILIAL=:iCodFilial
                AND O.CODOP=:iCodOp AND O.SEQOP=:iSEQOP AND E.CODEMP=O.CODEMPPD
                AND E.CODFILIAL=O.CODFILIALPD AND E.CODPROD=O.CODPROD AND E.SEQEST=O.SEQEST
                AND EA.CODEMP=E.CODEMP AND EA.CODFILIAL=E.CODFILIAL AND EA.CODPROD=E.CODPROD
                AND EA.SEQEST=E.SEQEST
                and f.codemp=ea.codempfs and f.codfilial=ea.codfilialfs and f.codfase=ea.codfase
                and f.tipofase='CQ'
        INTO :iSeqEf, :ICODEMPEA,:ICODFILIALEA,:ICODESTANALISE
    DO
    BEGIN
        ISEQOPCQ = :ISEQOPCQ + 1;
        INSERT INTO PPOPCQ (CODEMP,CODFILIAL,CODOP,SEQOP,SEQOPCQ,
            CODEMPEA,CODFILIALEA,CODESTANALISE, SEQEF)
            VALUES (:iCodEmp,:iCodFilial,:iCodOp,:iSeqOp, :ISEQOPCQ,:ICODEMPEA,
                    :ICODFILIALEA,:ICODESTANALISE,:iseqef);
    END

END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.6.2 (05/06/2013)';
    suspend;
end
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
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    :DTSAIDAVENDA,CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP
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

/* Alter (VDATUDESCVENDAORCSP) */
ALTER PROCEDURE VDATUDESCVENDAORCSP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
TIPOVENDA CHAR(1),
CODVENDA INTEGER)
 AS
declare variable vlrdescorc numeric(15,5);
declare variable vlrtotdesc numeric(15,5) = 0;
declare variable codorc integer;
declare variable conta1 numeric(15,5);
declare variable statusorc char(2);
declare variable conta2 numeric(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
begin
    -- verifica a quantidade total do orçamneto vinculado a venda
    select first 1 oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc, sum(iv.qtditvenda)
      from vdorcamento oc, vdvendaorc vo, vditorcamento itoc, vditvenda iv
        where vo.codemp=:codempvd and vo.codfilial=:codfilialvd and
          vo.tipovenda=:tipovenda and vo.codvenda=:codvenda and
          oc.codemp=vo.codempor and oc.codfilial=vo.codfilialor and
          oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc and
          itoc.codemp=oc.codemp and itoc.codfilial=oc.codfilial and
          itoc.tipoorc=oc.tipoorc and itoc.codorc=oc.codorc and
          itoc.coditorc=vo.coditorc and
          iv.codemp=vo.codemp and iv.codfilial=vo.codfilial and
          iv.tipovenda=vo.tipovenda and iv.codvenda=vo.codvenda and
          iv.coditvenda=vo.coditvenda
        group by oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc
        into :codempoc, :codfilialoc, :tipoorc, :codorc, :statusorc, :conta1;

    if (:statusorc not in ('FP') ) then
    begin
        -- verifica a quantidade total do orçamento
        select sum(qtditorc) from vditorcamento it
          where codemp=:codempoc and codfilial=:codfilialoc and
          tipoorc=:tipoorc and codorc=:codorc
          into :conta2;
        if (conta1=conta2) then
        begin
            -- Buscando desconto nos orçamentos dessa venda
            for select vo.codorc, oc.vlrdescorc from vdvendaorc vo, vdorcamento oc
            where
              vo.codemp=:CODEMPVD and vo.codfilial=:CODFILIALVD and vo.tipovenda=:TIPOVENDA and vo.codvenda=:CODVENDA and
              oc.codemp=vo.codempor and oc.codfilial=vo.codfilial and oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc
            group by 1,2
            into :codorc,:vlrdescorc
            do
            begin
                VLRTOTDESC = :VLRTOTDESC + :VLRDESCORC;
            end
            -- Atualizando desconto na venda
            if(:VLRTOTDESC is not null and :VLRTOTDESC>0) then
            begin
               --update vdvenda set vlrdescvenda = :VLRTOTDESC
               --where codemp=:CODEMPVD and codfilial=:CODFILIALVD and tipovenda=:TIPOVENDA and codvenda=:CODVENDA;
            end
        end
    end
end
^

SET TERM ; ^

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

ALTER TABLE EQPRODUTO ALTER COLUMN CODEMPMG POSITION 88;

ALTER TABLE EQPRODUTO ALTER COLUMN CODFILIALMG POSITION 89;

ALTER TABLE EQPRODUTO ALTER COLUMN CODMODG POSITION 90;

ALTER TABLE EQPRODUTO ALTER COLUMN PRAZOREPO POSITION 91;

ALTER TABLE EQPRODUTO ALTER COLUMN MEDIAVENDA POSITION 92;

ALTER TABLE EQPRODUTO ALTER COLUMN DTINS POSITION 93;

ALTER TABLE EQPRODUTO ALTER COLUMN HINS POSITION 94;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUINS POSITION 95;

ALTER TABLE EQPRODUTO ALTER COLUMN DTALT POSITION 96;

ALTER TABLE EQPRODUTO ALTER COLUMN HALT POSITION 97;

ALTER TABLE EQPRODUTO ALTER COLUMN IDUSUALT POSITION 98;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODFISC POSITION 3;

ALTER TABLE LFCLFISCAL ALTER COLUMN DESCFISC POSITION 4;

ALTER TABLE LFCLFISCAL ALTER COLUMN TIPOFISC POSITION 5;

ALTER TABLE LFCLFISCAL ALTER COLUMN TPREDICMSFISC POSITION 6;

ALTER TABLE LFCLFISCAL ALTER COLUMN ALIQFISC POSITION 7;

ALTER TABLE LFCLFISCAL ALTER COLUMN ALIQLFISC POSITION 8;

ALTER TABLE LFCLFISCAL ALTER COLUMN REDFISC POSITION 9;

ALTER TABLE LFCLFISCAL ALTER COLUMN ALIQIPIFISC POSITION 10;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODEMPRA POSITION 11;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODFILIALRA POSITION 12;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODREGRA POSITION 13;

ALTER TABLE LFCLFISCAL ALTER COLUMN ORIGFISC POSITION 14;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODEMPTT POSITION 15;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODFILIALTT POSITION 16;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODTRATTRIB POSITION 17;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODEMPME POSITION 18;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODFILIALME POSITION 19;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODMENS POSITION 20;

ALTER TABLE LFCLFISCAL ALTER COLUMN SITPISFISC POSITION 21;

ALTER TABLE LFCLFISCAL ALTER COLUMN SITCOFINSFISC POSITION 22;

ALTER TABLE LFCLFISCAL ALTER COLUMN TIPOST POSITION 23;

ALTER TABLE LFCLFISCAL ALTER COLUMN MARGEMVLAGR POSITION 24;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODNCM POSITION 25;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODNBM POSITION 26;

ALTER TABLE LFCLFISCAL ALTER COLUMN EXTIPI POSITION 27;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODSERV POSITION 28;

ALTER TABLE LFCLFISCAL ALTER COLUMN CST POSITION 29;

ALTER TABLE LFCLFISCAL ALTER COLUMN CODIGO POSITION 30;

ALTER TABLE LFCLFISCAL ALTER COLUMN DTINS POSITION 31;

ALTER TABLE LFCLFISCAL ALTER COLUMN HINS POSITION 32;

ALTER TABLE LFCLFISCAL ALTER COLUMN IDUSUINS POSITION 33;

ALTER TABLE LFCLFISCAL ALTER COLUMN DTALT POSITION 34;

ALTER TABLE LFCLFISCAL ALTER COLUMN HALT POSITION 35;

ALTER TABLE LFCLFISCAL ALTER COLUMN IDUSUALT POSITION 36;


COMMIT WORK;
