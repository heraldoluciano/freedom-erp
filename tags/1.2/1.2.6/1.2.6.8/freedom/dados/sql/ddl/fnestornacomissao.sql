SET TERM ^ ;

CREATE OR ALTER PROCEDURE FNESTORNACOMISSAOSP (
    codemp integer,
    codfilial smallint,
    codrec integer,
    nparcitrec smallint)
as
declare variable vlrvendacomi numeric(15,5);
declare variable vlrcomi numeric(15,5);
declare variable datacomi date;
declare variable dtcompcomi date;
declare variable dtvenccomi date;
declare variable tipocomi char(1);
declare variable statuscomi char(2);
declare variable dtatual date;
declare variable dtvencitrec date;
declare variable codempvd integer;
declare variable codfilialvd smallint;
declare variable codvend integer;
begin
  /* Procedure Text */
  dtatual = cast( 'now' as date);

  select first 1 c.statuscomi, c.tipocomi, c.codempvd, c.codfilialvd, c.codvend
  , c.vlrvendacomi, c.vlrcomi, c.datacomi, c.dtcompcomi, ir.dtvencitrec , c.dtvenccomi
  from vdcomissao c, fnitreceber ir
  where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec and tipocomi='R'
  and ir.codemp=c.codemprc and ir.codfilial=c.codfilialrc and ir.codrec=c.codrec and ir.nparcitrec=c.nparcitrec
  and c.statuscomi<>'CE'
  order by c.codcomi desc
  into :statuscomi, :tipocomi, :codempvd, :codfilialvd,  :codvend
  , :vlrvendacomi, :vlrcomi, :datacomi, :dtcompcomi, :dtvencitrec, :dtvenccomi;

  if (statuscomi not in ('CP') ) then
  begin
      update vdcomissao c set c.statuscomi='C1'
      where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec
      and tipocomi='R' and statuscomi not in ('CP','CE');
  end
  else if (statuscomi in ('CP') ) then
  begin
      vlrcomi = vlrcomi * -1; /* Transforma o valor da comissão em negativo */
      /* para gerar estorno */
      execute procedure vdadiccomissaosp(:codemp,:codfilial,:codrec,
         :nparcitrec, :vlrvendacomi, :vlrcomi, :datacomi , :dtcompcomi, :dtvenccomi,
         :tipocomi, :codempvd, :codfilialvd, : codvend );

     -- execute vdadiccomissaosp
  end

  /*UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
    */
--  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
--      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTCOMPCOMI, C.DTVENCCOMI,
--      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
--    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
--    WHERE C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND
--       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
--       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
--       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
--       R.CODREC=C.CODREC
       --AND R.CODEMPVD=:CODEMPVD
       --AND
       --R.CODFILIALVD=:CODFILIALVD
       --AND R.CODVEND=:CODVEND AND
       --and ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       --( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
--       and C.STATUSCOMI IN ('C2','CP') AND
--       IR.STATUSITREC NOT IN ('RP') AND
--       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
--         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
--         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
--         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
--    INTO :CODCOMI, :CODEMP, :CODFILIAL, :CODREC, :NPARCITREC, :VLRVENDACOMI,
--      :VLRCOMI, :DATACOMI, :DTCOMPCOMI, :DTVENCCOMI, :TIPOCOMI, :STATUSCOMI, :STATUSITREC,
--      :DTVENCITREC
--  DO
--  BEGIN
--     IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
--     BEGIN
--        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--          WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
--            CODCOMI=:CODCOMI;
--     END
--     ELSE IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
--     BEGIN
--        VLRCOMI = VLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
     --   EXECUTE PROCEDURE vdadiccomissaosp(:CODEMP,:CODFILIAL,:CODREC,
       --   :NPARCITREC, :VLRVENDACOMI, :VLRCOMI, :DATACOMI , :DTCOMPCOMI, :DTVENCITREC,
         -- :TIPOCOMI, :codempvd, :codfilialvd, : codvend );
--     END
--  END
 -- suspend;
end^

SET TERM ; ^

SET TERM ^ ;

CREATE OR ALTER TRIGGER FNITRECEBERTGAU FOR FNITRECEBER
ACTIVE AFTER UPDATE POSITION 0
AS
  DECLARE VARIABLE ICODCLI INTEGER;
  DECLARE VARIABLE ICODEMPCL INTEGER;
  DECLARE VARIABLE ICODFILIALCL INTEGER;
  DECLARE VARIABLE SCODFILIALCI SMALLINT;
  DECLARE VARIABLE CODFILIALLC SMALLINT;
  DECLARE VARIABLE CODEMPLC INTEGER;
  DECLARE VARIABLE CODLANCA INTEGER;
  DECLARE VARIABLE VLRLANCA NUMERIC(15,5);
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
  DECLARE VARIABLE PERMITBAIXAPARCJDM CHAR(1);
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ESTITRECALTDTVENC, PERMITBAIXAPARCJDM FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC, :PERMITBAIXAPARCJDM;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;
     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='R1') ) OR
           ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='RR') ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNCOMISSAO') INTO :SCODFILIALCI;
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :CODFILIALLC;
       
       execute procedure fnestornacomissaosp new.codemp, new.codfilial, new.codrec, new.nparcitrec;

       --UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
--              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
--              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
--              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
       
       
              
       IF ( (old.MULTIBAIXA IS NULL) OR (old.MULTIBAIXA='N') ) THEN
       BEGIN        
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
          DELETE FROM FNLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
       END
       ELSE 
       BEGIN
          SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
                CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA;
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
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
     ELSE IF ((old.STATUSITREC='R1' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC='RR' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC in ('RP','RL') AND new.STATUSITREC in ('RP','RL') AND new.VLRPAGOITREC > 0) OR
              (old.STATUSITREC = 'RB' AND new.STATUSITREC = 'RP')) THEN
     BEGIN
        SELECT CODCLI,CODEMPCL,CODFILIALCL FROM FNRECEBER WHERE CODEMP=new.CODEMP AND
           CODFILIAL=new.CODFILIAL AND CODREC=new.CODREC
           INTO ICODCLI,ICODEMPCL,ICODFILIALCL;
        IF ((new.VLRPAGOITREC-old.VLRPAGOITREC) > 0) THEN
        BEGIN
          IF(new.multibaixa is null or new.multibaixa = 'N')THEN
          BEGIN
             EXECUTE PROCEDURE FNADICLANCASP01(new.CodRec,new.NParcItRec,new.PDVITREC,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODCLI,:ICODEMPCL,:ICODFILIALCL,
                        new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.ANOCC,new.CODCC,new.CODEMPCC,new.CODFILIALCC, new.dtCompItRec, new.DtPagoItRec, 
                        new.DocLancaItRec, SUBSTRING(new.ObsItRec FROM 1 FOR 50),new.VlrPagoItRec-old.VlrPagoItRec,new.CODEMP,new.CODFILIAL,new.vlrjurositrec,new.vlrdescitrec);
          END
        END
        IF (new.STATUSITREC = 'RP') THEN
        BEGIN
            UPDATE VDCOMISSAO SET STATUSCOMI='C2'
               WHERE CODREC=old.CODREC
               AND CODEMPRC=old.CODEMP
               AND CODFILIALRC=old.CODFILIAL
               AND NPARCITREC=old.NPARCITREC
               AND NOT STATUSCOMI IN ('CP','C2')
               AND CODEMP=old.CODEMP;
        END
     END
     ELSE IF (old.VLRCOMIITREC != new.VLRCOMIITREC) THEN
     BEGIN
        EXECUTE PROCEDURE vdgeracomissaosp(new.CODEMP, new.CODFILIAL, new.CODREC,
           new.NPARCITREC, new.VLRCOMIITREC, new.DTVENCITREC);
     END

     IF ( (new.ALTUSUITREC='S') AND ( (old.VLRITREC!=new.VLRITREC) OR
          (old.VLRDESCITREC!=new.VLRDESCITREC) OR (old.VLRMULTAITREC!=new.VLRMULTAITREC) OR
          (old.VLRJUROSITREC!=new.VLRJUROSITREC) OR (old.VLRPARCITREC!=new.VLRPARCITREC) OR
          (old.VLRPAGOITREC!=new.VLRPAGOITREC) OR (old.VLRAPAGITREC!=new.VLRAPAGITREC) ) ) THEN
        UPDATE FNRECEBER SET VLRREC = VLRREC - old.VLRITREC + new.VLRITREC,
            VLRDESCREC = VLRDESCREC - old.VLRDESCITREC + new.VLRDESCITREC,
            VLRMULTAREC = VLRMULTAREC - old.VLRMULTAITREC + new.VLRMULTAITREC,
            VLRJUROSREC = VLRJUROSREC - old.VLRJUROSITREC + new.VLRJUROSITREC,
            VLRDEVREC = VLRDEVREC - old.VLRDEVITREC + new.VLRDEVITREC,
            VLRPARCREC = VLRPARCREC - old.VLRPARCITREC + new.VLRPARCITREC,
            VLRPAGOREC = VLRPAGOREC - old.VLRPAGOITREC + new.VLRPAGOITREC,
            ALTUSUREC = 'S' WHERE CODREC=new.CODREC
           AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
      /* Condição para evitar baixa parcial de títulos com juros, descontos ou multas. */
      IF ( (:PERMITBAIXAPARCJDM='N') AND (new.STATUSITREC='RL') AND ( (new.VLRDESCITREC<>0) OR (new.VLRJUROSITREC<>0) OR (new.VLRMULTAITREC<>0) ) ) THEN
         EXCEPTION FNITRECEBEREX03; 
   END
   
END
^


SET TERM ; ^

GRANT SELECT,UPDATE ON VDCOMISSAO TO PROCEDURE FNESTORNACOMISSAOSP;

GRANT SELECT ON FNITRECEBER TO PROCEDURE FNESTORNACOMISSAOSP;

GRANT EXECUTE ON PROCEDURE VDADICCOMISSAOSP TO PROCEDURE FNESTORNACOMISSAOSP;

GRANT EXECUTE ON PROCEDURE FNESTORNACOMISSAOSP TO ADM;

commit work;

