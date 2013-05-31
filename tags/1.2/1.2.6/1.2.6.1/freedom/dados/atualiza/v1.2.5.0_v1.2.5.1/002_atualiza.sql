SET TERM ^ ;

CREATE OR ALTER TRIGGER FNITPAGARTGBU FOR FNITPAGAR
ACTIVE BEFORE UPDATE POSITION 0
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

       if ( (new.multibaixa = 'N') and ((new.edititpag is null) or (new.edititpag='N') ) ) THEN
       BEGIN
           EXECUTE PROCEDURE FNADICLANCASP02(new.CodPag,new.NParcPag,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODFOR,:ICODEMPFR,:ICODFILIALFR,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.AnoCC,new.CodCC,new.CODEMPCC,new.CODFILIALCC, new.DTCOMPITPAG,
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag);
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

COMMIT WORK ^

SET TERM ; ^

SELECT CODEMP, CODPAG, NPARCPAG FROM FNITPAGAR WHERE EDITITPAG='S' AND VLRPAGOITPAG>0;

UPDATE FNITPAGAR SET EDITITPAG='N' WHERE EDITITPAG='S';

COMMIT WORK;




