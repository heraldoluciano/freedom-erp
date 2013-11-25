SET TERM ^ ;

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
COMMIT WORK ^

