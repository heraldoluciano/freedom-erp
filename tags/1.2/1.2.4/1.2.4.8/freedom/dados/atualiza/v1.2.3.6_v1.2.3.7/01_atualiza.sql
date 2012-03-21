/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

/* Alter Field (Null / Not Null)... */
UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = NULL WHERE RDB$FIELD_NAME='DTCOMPCHEQ' AND RDB$RELATION_NAME='FNCHEQUE';


ALTER TABLE EQUNIDADE ADD CALCVOLEMB CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve calcular o numero de volumes baseado na quantidade por embalagem.'
where Rdb$Relation_Name='EQUNIDADE' and Rdb$Field_Name='CALCVOLEMB';

/* Create Views... */
/* Create view: VWPROD_PRECO_CUSTO (ViwData.CreateDependDef) */
CREATE VIEW VWPROD_PRECO_CUSTO(
REFPROD,
CODSECAO,
DESCPROD,
PRECOBASEPROD,
CUSTO)
 AS 
select pd.refprod, pd.codsecao, pd.descprod, pd.precobaseprod,
(case
when (select custounit from eqcustoprodsp(pd.codemp, pd.codfilial, pd.codprod,cast('today' as date),'M',pd.codempax, pd.codfilialax,pd.codalmox, null)) > 0
then (select custounit from eqcustoprodsp(pd.codemp, pd.codfilial, pd.codprod,cast('today' as date),'M',pd.codempax, pd.codfilialax,pd.codalmox, null))
else pd.custoinfoprod end) custo
from eqproduto pd
;


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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.7 (11/10/2010)';
    suspend;
end
^

/* Create Views... */

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
  declare variable docvenda integer;
  declare variable serievenda char(4);

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
    select codempca,codfilialca,numconta, vd.docvenda, vd.serie from vdvenda vd
    where codemp=new.codempva and codfilial=new.codfilialva and codvenda=new.codvenda and tipovenda=new.tipovenda
    into :codempca, :codfilialca, :numconta, :docvenda, :serievenda;
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
      DOCLANCAITREC= :docvenda || '/' || :nparcrec,
--      DOCLANCAITREC='AUTO',
      DTPAGOITREC=NEW.DATAREC,
      VLRPAGOITREC=VLRPARCITREC - coalesce(vlrdescitrec,0),
--      VLRDESCITREC=0,
      VLRJUROSITREC=0,
      OBSITREC='BAIXA AUTOMÁTICA' || ' REF. DOC: ' || :serievenda || '-' || :docvenda,
      STATUSITREC='RP'
      WHERE CODREC=NEW.CODREC AND CODEMP=NEW.CODEMP AND CODFILIAL=:CODFILIALIR AND
      NPARCITREC=:NPARCREC;
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

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.7 (11/10/2010)';
    suspend;
end
^

SET TERM ; ^

ALTER TABLE EQUNIDADE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQUNIDADE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQUNIDADE ALTER COLUMN CODUNID POSITION 3;

ALTER TABLE EQUNIDADE ALTER COLUMN DESCUNID POSITION 4;

ALTER TABLE EQUNIDADE ALTER COLUMN CASASDEC POSITION 5;

ALTER TABLE EQUNIDADE ALTER COLUMN CALCVOLEMB POSITION 6;

ALTER TABLE EQUNIDADE ALTER COLUMN DTINS POSITION 7;

ALTER TABLE EQUNIDADE ALTER COLUMN HINS POSITION 8;

ALTER TABLE EQUNIDADE ALTER COLUMN IDUSUINS POSITION 9;

ALTER TABLE EQUNIDADE ALTER COLUMN DTALT POSITION 10;

ALTER TABLE EQUNIDADE ALTER COLUMN HALT POSITION 11;

ALTER TABLE EQUNIDADE ALTER COLUMN IDUSUALT POSITION 12;

commit;
