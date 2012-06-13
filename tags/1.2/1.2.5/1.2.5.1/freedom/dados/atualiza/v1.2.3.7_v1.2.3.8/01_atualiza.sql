/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE SGDEBUG ADD SEQ INTEGER NOT NULL;

/* Create Index... */
CREATE UNIQUE INDEX SGDEBUG_IDX1 ON SGDEBUG(SEQ);


/* Create Generator... */
CREATE GENERATOR GEN_SGDEBUG_ID;


/* Alter Procedure... */
/* empty dependent procedure body */
/* Clear: FNGERAITRECEBERSP01 for: FNADICITRECEBERSP01 */
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
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 RETURNS(I INTEGER)
 AS
 BEGIN EXIT; END
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
declare variable doclancaitrec char(10);
begin
   SELECT ir.doclancaitrec,ir.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :doclancaitrec, :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis, doclancaitrec)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis,:doclancaitrec );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas a receber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis, obsitrec=:obsitrec
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, obsitrec=:obsitrec
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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.8 (14/10/2010)';
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
    FROM VDORCAMENTO O, VDVENDEDOR VE WHERE O.CODORC=:ICODORC
    AND O.CODFILIAL=:ICODFILIAL AND O.CODEMP=:ICODEMP
    AND VE.CODEMP=O.CODEMP AND VE.CODFILIAL=O.CODFILIALVD
    AND VE.CODVEND=O.CODVEND INTO
           :ICODFILIALVD,:ICODVEND,:ICODFILIALCL,:ICODCLI,
           :ICODFILIALPG,:ICODPLANOPAG,DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:vlrdescorc;

  INSERT INTO VDVENDA (
    CODEMP,CODFILIAL,CODVENDA,TIPOVENDA,CODEMPVD,CODFILIALVD,CODVEND,CODEMPCL,CODFILIALCL,CODCLI,
    CODEMPPG,CODFILIALPG,CODPLANOPAG,CODEMPSE,CODFILIALSE,SERIE,CODEMPTM,CODFILIALTM,CODTIPOMOV,
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM,vlrdescvenda)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    CAST('today' AS DATE),CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC );

  IRET = ICODVENDA;

  SUSPEND;
END
^

/* Create Trigger... */
CREATE TRIGGER SGDEBUG_BI FOR SGDEBUG
ACTIVE BEFORE INSERT POSITION 0 
AS
BEGIN
  IF (NEW.SEQ IS NULL) THEN
    NEW.SEQ = GEN_ID(GEN_SGDEBUG_ID,1);
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
        if ( (
                (old.codplanopag<>new.codplanopag) or
                (old.flag<>new.flag) or
                (old.vlrrec<>new.vlrrec) or
                (old.datarec<>new.datarec) or
                (coalesce(old.obsrec,'')<>coalesce(new.obsrec,''))
            )

            and (new.altusurec != 'S')

        ) then

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
  if ( ( (old.BLOQVENDA IS  NULL) OR (old.BLOQVENDA='N') ) AND (new.BLOQVENDA='S') )  then
  begin
      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=user;
      new.HALT=cast('now' AS TIME);
  end
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP, 'SGPREFERE1') INTO iCodFilialPref;
      EXECUTE PROCEDURE VDCLIENTEATIVOSP(new.CODEMPCL, new.CODFILIALCL, new.CODCLI);
      if ( (old.BLOQVENDA IS NOT NULL AND old.BLOQVENDA='S') or (new.BLOQVENDA='S') ) then
         EXCEPTION VDVENDAEX05 'ESTA VENDA ESTÁ BLOQUEADA!!!';
      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=user;
      new.HALT=cast('now' AS TIME);
      SELECT CODFILIALSEL FROM SGCONEXAO WHERE NRCONEXAO=CURRENT_CONNECTION AND
          CONECTADO > 0 INTO ICODFILIAL;
      IF (substr(old.STATUSVENDA,1,1) = 'C') THEN
        EXCEPTION VDVENDAEX05;
      IF (substr(old.STATUSVENDA,1,1) = 'D') THEN
        EXCEPTION VDVENDAEX05 'ESTA VENDA FOI DEVOLVIDA!';
      IF ((SUBSTR(old.STATUSVENDA,1,1) = 'P') AND (SUBSTR(new.STATUSVENDA,1,1) = 'V' ) AND new.IMPNOTAVENDA = 'N') THEN
      BEGIN
        SELECT T2.CODTIPOMOV, T2.SERIE FROM EQTIPOMOV T2, EQTIPOMOV T WHERE T2.CODEMP=T.CODEMPTM
               AND T2.CODFILIAL=T.CODFILIALTM AND T2.CODTIPOMOV = T.CODTIPOMOVTM
               AND T.CODEMP=new.CODEMPTM AND T.CODFILIAL=new.CODFILIALTM AND T.CODTIPOMOV=new.CODTIPOMOV
               INTO :iCodTipoMov, :sSerie;
        IF (iCodTipoMov IS NULL) THEN
          SELECT T.CODTIPOMOV, T.SERIE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMPTM=T.CODEMP AND
                 P.CODFILIALTM=T.CODFILIAL AND P.CODTIPOMOV = T.CODTIPOMOV
                 AND P.CODEMP=new.CODEMP AND P.CODFILIAL = :iCodFilialPref INTO :iCodTipoMov, :sSerie;
        new.CODTIPOMOV = :iCodTipoMov;
        new.SERIE = :sSerie;
        IF ( ( not (old.IMPNOTAVENDA = 'S') ) AND ( not (new.IMPNOTAVENDA = 'S') ) ) THEN
        BEGIN
            SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMP,new.CODFILIAL) INTO new.DocVenda;
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
          new.PERCMCOMISVENDA = (new.VLRCOMISVENDA/(new.vlrprodvenda-new.vlrdescvenda)) * 100;
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
END
^

/* Alter Procedure... */
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
declare variable doclancaitrec char(10);
begin
   SELECT ir.doclancaitrec,ir.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :doclancaitrec, :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC,DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis, doclancaitrec)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC,:DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis,:doclancaitrec );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas a receber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis, obsitrec=:obsitrec
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, obsitrec=:obsitrec
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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.8 (14/10/2010)';
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
    FROM VDORCAMENTO O, VDVENDEDOR VE WHERE O.CODORC=:ICODORC
    AND O.CODFILIAL=:ICODFILIAL AND O.CODEMP=:ICODEMP
    AND VE.CODEMP=O.CODEMP AND VE.CODFILIAL=O.CODFILIALVD
    AND VE.CODVEND=O.CODVEND INTO
           :ICODFILIALVD,:ICODVEND,:ICODFILIALCL,:ICODCLI,
           :ICODFILIALPG,:ICODPLANOPAG,DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:vlrdescorc;

  INSERT INTO VDVENDA (
    CODEMP,CODFILIAL,CODVENDA,TIPOVENDA,CODEMPVD,CODFILIALVD,CODVEND,CODEMPCL,CODFILIALCL,CODCLI,
    CODEMPPG,CODFILIALPG,CODPLANOPAG,CODEMPSE,CODFILIALSE,SERIE,CODEMPTM,CODFILIALTM,CODTIPOMOV,
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM,vlrdescvenda)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    CAST('today' AS DATE),CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP,:VLRDESCORC );

  IRET = ICODVENDA;

  SUSPEND;
END
^

SET TERM ; ^

ALTER TABLE SGDEBUG ALTER COLUMN SEQ POSITION 1;

ALTER TABLE SGDEBUG ALTER COLUMN DATA POSITION 2;

ALTER TABLE SGDEBUG ALTER COLUMN HORA POSITION 3;

ALTER TABLE SGDEBUG ALTER COLUMN ROTINA POSITION 4;

ALTER TABLE SGDEBUG ALTER COLUMN TEXTO POSITION 5;

COMMIT;
