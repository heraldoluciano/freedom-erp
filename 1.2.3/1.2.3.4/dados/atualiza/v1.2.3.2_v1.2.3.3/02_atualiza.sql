/* Server version: LI-V6.3.3.4870 Firebird 1.5 
   SQLDialect: 3. ODS: 10.1. Forced writes: On. Sweep inteval: 20000.
   Page size: 4096. Cache pages: 75 (300 Kb). Read-only: False. */
SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE CPITCOMPRA ADD QTDITCOMPRACANC NUMERICDN;

UPDATE RDB$RELATION_FIELDS SET RDB$DESCRIPTION = 
'Quantidade cancelada'
WHERE RDB$RELATION_NAME = 'CPITCOMPRA' AND RDB$FIELD_NAME = 'QTDITCOMPRACANC';

ALTER TABLE VDITVENDA ADD QTDITVENDACANC NUMERICDN;

UPDATE RDB$RELATION_FIELDS SET RDB$DESCRIPTION = 
'Quantidade cancelada'
WHERE RDB$RELATION_NAME = 'VDITVENDA' AND RDB$FIELD_NAME = 'QTDITVENDACANC';

/* Alter exist trigger... */
SET TERM ^ ;

ALTER TRIGGER CPCOMPRATGAU
AS
  DECLARE VARIABLE iCodPag INTEGER;
  DECLARE VARIABLE dVLR NUMERIC(15, 5);
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE TIPOMOV CHAR(2);        
  DECLARE VARIABLE ICODITEM SMALLINT;
  DECLARE VARIABLE VLRITEM NUMERIC(15, 5);  
  DECLARE VARIABLE QTDITEM NUMERIC(15, 5);
  DECLARE VARIABLE PERCITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE VLRITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE PERCITADIC NUMERIC(15, 5);
  DECLARE VARIABLE VLRITADIC NUMERIC(15, 5);
  DECLARE VARIABLE SCODFILIALP1 SMALLINT;
  DECLARE VARIABLE GERAPAGEMIS CHAR(1);
  DECLARE VARIABLE DTBASE DATE;
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) )) ) THEN
  BEGIN
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :SCODFILIALP1;
      SELECT P1.GERAPAGEMIS FROM SGPREFERE1 P1 WHERE  P1.CODEMP=new.CODEMP AND
         P1.CODFILIAL=:SCODFILIALP1 INTO :GERAPAGEMIS;
      IF (GERAPAGEMIS IS NULL) THEN
      BEGIN
        GERAPAGEMIS = 'N';
      END
      IF (GERAPAGEMIS='S') THEN
      BEGIN
        DTBASE = new.DTEMITCOMPRA;
      END
      ELSE
      BEGIN
        DTBASE = new.DTENTCOMPRA;
      END
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
      SELECT CODPAG FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA
             AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALPAG INTO iCodPag;
      SELECT TIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
             AND CODEMP=new.CODEMPTM AND CODFILIAL=new.CODFILIALTM INTO TIPOMOV;
      IF ((NOT TIPOMOV IN ('DV','TR')) AND (
         (iCodPag IS NULL) OR (
         (new.CODPLANOPAG != old.CODPLANOPAG) OR
         (new.CODFOR != old.CODFOR) OR
         (new.VLRLIQCOMPRA != old.VLRLIQCOMPRA) OR
         (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
         (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
         (new.DOCCOMPRA != old.DOCCOMPRA) OR
         (new.CODBANCO != old.CODBANCO)))) THEN
      BEGIN
        dVLR = new.VLRLIQCOMPRA;
        IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P1','C1'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=:IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,
               new.CODPLANOPAG,new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,
               :DTBASE, new.DOCCOMPRA,new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,
               new.FLAG,new.CODEMP,new.CODFILIAL);
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE ,new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL);
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR, :DTBASE,new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL);
        END
      END
    /**
     Movimento do estoque
    **/
    /* Avisa os itens que a data de saida foi alterada */
      IF ( (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
           (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
           (new.DOCCOMPRA != old.DOCCOMPRA) OR
           (new.CODTIPOMOV != old.CODTIPOMOV) )  THEN
        UPDATE CPITCOMPRA SET CODITCOMPRA=CODITCOMPRA WHERE
             CODCOMPRA = old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;

      IF ( ((new.VLRFRETECOMPRA > 0) AND ( NOT new.VLRFRETECOMPRA = old.VLRFRETECOMPRA)) or ((new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra))  ) THEN
      BEGIN
          FOR SELECT IT.CODITCOMPRA, IT.VLRLIQITCOMPRA, IT.QTDITCOMPRA FROM CPITCOMPRA IT
              WHERE IT.CODEMP=new.CODEMP AND IT.CODFILIAL=new.CODFILIAL AND IT.CODCOMPRA=new.CODCOMPRA
              INTO :ICODITEM, :VLRITEM, :QTDITEM
              DO
              BEGIN

                  IF ( (new.vlrfretecompra > 0) AND ( NOT new.vlrfretecompra = old.vlrfretecompra)  ) THEN
                  begin
                      PERCITFRETE = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITFRETE =  (:PERCITFRETE * (new.VLRFRETECOMPRA / 100)) / COALESCE(:QTDITEM, 1);

                      UPDATE CPITCOMPRA CIT
                          SET VLRFRETEITCOMPRA=:VLRITFRETE
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;
                  end
                  IF ( (new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)  ) THEN
                  begin
                      PERCITADIC = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITADIC =  (:PERCITADIC * (new.VLRADICCOMPRA / 100)) / COALESCE(:QTDITEM, 1);
                      UPDATE CPITCOMPRA CIT
                          SET VLRADICITCOMPRA=:VLRITADIC
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;

                  end


              END
        END
          IF ((substr(new.STATUSCOMPRA,1,1)='X') AND (substr(old.STATUSCOMPRA,1,1) IN ('P','C'))) THEN
          BEGIN
              UPDATE CPITCOMPRA SET QTDITCOMPRACANC=QTDITCOMPRA, QTDITCOMPRA=0 WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
              DELETE FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
          END

        -- Atualização do status do recebimento da mercadoria quando a nota for emitida.
        if( old.statuscompra!='ET' and new.statuscompra='ET') then
        begin
            
           update eqrecmerc rm set rm.status='NE'
           where rm.codemp=new.codemp and rm.codfilial=new.codfilial
           and rm.ticket in
           (
            select ticket
            from eqitrecmercitcp rm
            where rm.codempcp=new.codemp and rm.codfilialcp=new.codfilial
            and rm.codcompra=new.codcompra
           );

        end

    END
END
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGBU
as

declare variable srefprod char(13);
declare variable sadicfrete char(1);
declare variable sadicadic char(1);
declare variable habcustocompra char(1);
declare variable vlritcusto numeric(15, 5);
declare variable statuscompra char(2);
declare variable calctrib char(1);

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin
        
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
            select p.custocompra from sgprefere1 p
            where p.codemp=new.codemp and p.codfilial=new.codfilial
            into :habcustocompra;

            --Buscando informações da compra
            select cp.calctrib from cpcompra cp
            where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
            into :calctrib;

            if (('N' = habcustocompra) or (new.custoitcompra is null)) then
            begin
                select nvlrcusto
                from cpcomprasp01 (new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra)
                into new.custoitcompra;
            end

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

        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGAU
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
    declare variable qtditvenda numeric(9,2);
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
                into percred, percipiitvenda, tipofisc, tpredicms, redbasefrete ;

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
                   new.vlrliqvenda,dtrec,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, new.flag, new.obsrec);
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
                   new.vlrliqvenda,new.dtsaidavenda,new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob,
                   new.flag, new.obsrec);
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
            and codempva=new.codemp and codfilialva = new.codfilial into :nvlrparcrec;
        if (new.vlrliqvenda != :nvlrparcrec) then
            exception vdvendaex06;
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
   end
end
^

SET TERM ; ^

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODCOMPRA POSITION 3;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODITCOMPRA POSITION 4;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPPD POSITION 5;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALPD POSITION 6;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODPROD POSITION 7;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPLE POSITION 8;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALLE POSITION 9;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODLOTE POSITION 10;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPNT POSITION 11;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALNT POSITION 12;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODNAT POSITION 13;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPAX POSITION 14;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALAX POSITION 15;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODALMOX POSITION 16;

ALTER TABLE CPITCOMPRA ALTER COLUMN QTDITCOMPRA POSITION 17;

ALTER TABLE CPITCOMPRA ALTER COLUMN QTDITCOMPRACANC POSITION 18;

ALTER TABLE CPITCOMPRA ALTER COLUMN PRECOITCOMPRA POSITION 19;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCDESCITCOMPRA POSITION 20;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 21;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCICMSITCOMPRA POSITION 22;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEICMSITCOMPRA POSITION 23;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRICMSITCOMPRA POSITION 24;

ALTER TABLE CPITCOMPRA ALTER COLUMN PERCIPIITCOMPRA POSITION 25;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEIPIITCOMPRA POSITION 26;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRIPIITCOMPRA POSITION 27;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRBASEFUNRURALITCOMPRA POSITION 28;

ALTER TABLE CPITCOMPRA ALTER COLUMN ALIQFUNRURALITCOMPRA POSITION 29;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFUNRURALITCOMPRA POSITION 30;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRLIQITCOMPRA POSITION 31;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRADICITCOMPRA POSITION 32;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRFRETEITCOMPRA POSITION 33;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRISENTASITCOMPRA POSITION 34;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLROUTRASITCOMPRA POSITION 35;

ALTER TABLE CPITCOMPRA ALTER COLUMN VLRPRODITCOMPRA POSITION 36;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOITCOMPRA POSITION 37;

ALTER TABLE CPITCOMPRA ALTER COLUMN CUSTOVDITCOMPRA POSITION 38;

ALTER TABLE CPITCOMPRA ALTER COLUMN REFPROD POSITION 39;

ALTER TABLE CPITCOMPRA ALTER COLUMN OBSITCOMPRA POSITION 40;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPIF POSITION 41;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALIF POSITION 42;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFISC POSITION 43;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMMANUT POSITION 44;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODITFISC POSITION 45;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODEMPNS POSITION 46;

ALTER TABLE CPITCOMPRA ALTER COLUMN CODFILIALNS POSITION 47;

ALTER TABLE CPITCOMPRA ALTER COLUMN NUMSERIETMP POSITION 48;

ALTER TABLE CPITCOMPRA ALTER COLUMN NADICAO POSITION 49;

ALTER TABLE CPITCOMPRA ALTER COLUMN SEQADIC POSITION 50;

ALTER TABLE CPITCOMPRA ALTER COLUMN DESCDI POSITION 51;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTINS POSITION 52;

ALTER TABLE CPITCOMPRA ALTER COLUMN HINS POSITION 53;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUINS POSITION 54;

ALTER TABLE CPITCOMPRA ALTER COLUMN DTALT POSITION 55;

ALTER TABLE CPITCOMPRA ALTER COLUMN HALT POSITION 56;

ALTER TABLE CPITCOMPRA ALTER COLUMN IDUSUALT POSITION 57;

ALTER TABLE CPITCOMPRA ALTER COLUMN EMITITCOMPRA POSITION 58;

ALTER TABLE CPITCOMPRA ALTER COLUMN APROVPRECO POSITION 59;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOVENDA POSITION 3;

ALTER TABLE VDITVENDA ALTER COLUMN CODVENDA POSITION 4;

ALTER TABLE VDITVENDA ALTER COLUMN CODITVENDA POSITION 5;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPNT POSITION 6;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALNT POSITION 7;

ALTER TABLE VDITVENDA ALTER COLUMN CODNAT POSITION 8;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPPD POSITION 9;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALPD POSITION 10;

ALTER TABLE VDITVENDA ALTER COLUMN CODPROD POSITION 11;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPLE POSITION 12;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALLE POSITION 13;

ALTER TABLE VDITVENDA ALTER COLUMN CODLOTE POSITION 14;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPAX POSITION 15;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALAX POSITION 16;

ALTER TABLE VDITVENDA ALTER COLUMN CODALMOX POSITION 17;

ALTER TABLE VDITVENDA ALTER COLUMN QTDITVENDA POSITION 18;

ALTER TABLE VDITVENDA ALTER COLUMN QTDITVENDACANC POSITION 19;

ALTER TABLE VDITVENDA ALTER COLUMN PRECOITVENDA POSITION 20;

ALTER TABLE VDITVENDA ALTER COLUMN PERCDESCITVENDA POSITION 21;

ALTER TABLE VDITVENDA ALTER COLUMN VLRDESCITVENDA POSITION 22;

ALTER TABLE VDITVENDA ALTER COLUMN PERCICMSITVENDA POSITION 23;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSITVENDA POSITION 24;

ALTER TABLE VDITVENDA ALTER COLUMN VLRICMSITVENDA POSITION 25;

ALTER TABLE VDITVENDA ALTER COLUMN PERCIPIITVENDA POSITION 26;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEIPIITVENDA POSITION 27;

ALTER TABLE VDITVENDA ALTER COLUMN VLRIPIITVENDA POSITION 28;

ALTER TABLE VDITVENDA ALTER COLUMN VLRLIQITVENDA POSITION 29;

ALTER TABLE VDITVENDA ALTER COLUMN PERCCOMISITVENDA POSITION 30;

ALTER TABLE VDITVENDA ALTER COLUMN VLRCOMISITVENDA POSITION 31;

ALTER TABLE VDITVENDA ALTER COLUMN VLRADICITVENDA POSITION 32;

ALTER TABLE VDITVENDA ALTER COLUMN PERCISSITVENDA POSITION 33;

ALTER TABLE VDITVENDA ALTER COLUMN VLRISSITVENDA POSITION 34;

ALTER TABLE VDITVENDA ALTER COLUMN VLRFRETEITVENDA POSITION 35;

ALTER TABLE VDITVENDA ALTER COLUMN VLRPRODITVENDA POSITION 36;

ALTER TABLE VDITVENDA ALTER COLUMN VLRISENTASITVENDA POSITION 37;

ALTER TABLE VDITVENDA ALTER COLUMN VLROUTRASITVENDA POSITION 38;

ALTER TABLE VDITVENDA ALTER COLUMN REFPROD POSITION 39;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEISSITVENDA POSITION 40;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSBRUTITVENDA POSITION 41;

ALTER TABLE VDITVENDA ALTER COLUMN VLRBASEICMSSTITVENDA POSITION 42;

ALTER TABLE VDITVENDA ALTER COLUMN VLRICMSSTITVENDA POSITION 43;

ALTER TABLE VDITVENDA ALTER COLUMN MARGEMVLAGRITVENDA POSITION 44;

ALTER TABLE VDITVENDA ALTER COLUMN OBSITVENDA POSITION 45;

ALTER TABLE VDITVENDA ALTER COLUMN ORIGFISC POSITION 46;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPTT POSITION 47;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALTT POSITION 48;

ALTER TABLE VDITVENDA ALTER COLUMN CODTRATTRIB POSITION 49;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOFISC POSITION 50;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOST POSITION 51;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPME POSITION 52;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALME POSITION 53;

ALTER TABLE VDITVENDA ALTER COLUMN CODMENS POSITION 54;

ALTER TABLE VDITVENDA ALTER COLUMN STRDESCITVENDA POSITION 55;

ALTER TABLE VDITVENDA ALTER COLUMN QTDDEVITVENDA POSITION 56;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPLG POSITION 57;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALLG POSITION 58;

ALTER TABLE VDITVENDA ALTER COLUMN CODLOG POSITION 59;

ALTER TABLE VDITVENDA ALTER COLUMN CANCITVENDA POSITION 60;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPPE POSITION 61;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALPE POSITION 62;

ALTER TABLE VDITVENDA ALTER COLUMN CODPE POSITION 63;

ALTER TABLE VDITVENDA ALTER COLUMN DIASPE POSITION 64;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCV POSITION 65;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCV POSITION 66;

ALTER TABLE VDITVENDA ALTER COLUMN CODCONV POSITION 67;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPIF POSITION 68;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALIF POSITION 69;

ALTER TABLE VDITVENDA ALTER COLUMN CODFISC POSITION 70;

ALTER TABLE VDITVENDA ALTER COLUMN CODITFISC POSITION 71;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPCP POSITION 72;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALCP POSITION 73;

ALTER TABLE VDITVENDA ALTER COLUMN CODCOMPRA POSITION 74;

ALTER TABLE VDITVENDA ALTER COLUMN CODITCOMPRA POSITION 75;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPVR POSITION 76;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALVR POSITION 77;

ALTER TABLE VDITVENDA ALTER COLUMN TIPOVENDAVR POSITION 78;

ALTER TABLE VDITVENDA ALTER COLUMN CODVENDAVR POSITION 79;

ALTER TABLE VDITVENDA ALTER COLUMN CODITVENDAVR POSITION 80;

ALTER TABLE VDITVENDA ALTER COLUMN CODEMPNS POSITION 81;

ALTER TABLE VDITVENDA ALTER COLUMN CODFILIALNS POSITION 82;

ALTER TABLE VDITVENDA ALTER COLUMN NUMSERIETMP POSITION 83;

ALTER TABLE VDITVENDA ALTER COLUMN EMMANUT POSITION 84;

ALTER TABLE VDITVENDA ALTER COLUMN DTINS POSITION 85;

ALTER TABLE VDITVENDA ALTER COLUMN HINS POSITION 86;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUINS POSITION 87;

ALTER TABLE VDITVENDA ALTER COLUMN DTALT POSITION 88;

ALTER TABLE VDITVENDA ALTER COLUMN HALT POSITION 89;

ALTER TABLE VDITVENDA ALTER COLUMN IDUSUALT POSITION 90;

