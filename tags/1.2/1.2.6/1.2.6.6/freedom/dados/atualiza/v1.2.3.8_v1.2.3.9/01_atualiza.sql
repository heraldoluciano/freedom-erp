/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE CPCOMPRA ADD CODEMPCT INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa da conta.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPCT';

ALTER TABLE CPCOMPRA ADD CODFILIALCT SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial da compra.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALCT';

ALTER TABLE CPCOMPRA ADD NUMCONTA CHAR(10);

Update Rdb$Relation_Fields set Rdb$Description =
'Número da conta.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='NUMCONTA';

ALTER TABLE CPCOMPRA ADD CODEMPCC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do centro de custos'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPCC';

ALTER TABLE CPCOMPRA ADD CODFILIALCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da filial do centro de custos.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALCC';

ALTER TABLE CPCOMPRA ADD ANOCC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Ano do centro de custo'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='ANOCC';

ALTER TABLE CPCOMPRA ADD CODCC CHAR(19);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do centro de custos'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODCC';

ALTER TABLE CPCOMPRA ADD CODEMPPN INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPPN';

ALTER TABLE CPCOMPRA ADD CODFILIALPN SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código do filial do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALPN';

ALTER TABLE CPCOMPRA ADD CODPLAN CHAR(13);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODPLAN';

ALTER TABLE FNCONTAVINCULADA ADD CONTACHEQUE CHAR(1) DEFAULT 'N' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a conta é para controle de compensação de cheques.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CONTACHEQUE';

ALTER TABLE FNPAGCHEQ ADD BAIXA CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a baixa do titulo vinculado (gatilho cheque emitido)
S - Sim;
N - Não;
'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='BAIXA';

ALTER TABLE FNPAGCHEQ ADD TRANSFERE CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve transferir o titulo vinculado da conta de cheques para conta real (gatilho cheque compensado)
S - Sim;
N - Não;'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='TRANSFERE';

ALTER TABLE SGPREFERE1 ADD CODEMPPC INTEGER;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa padrão planejamento para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPPC';

ALTER TABLE SGPREFERE1 ADD CODFILIALPC SMALLINT;

Update Rdb$Relation_Fields set Rdb$Description =
'Código da empresa para o planejamento padrão para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALPC';

ALTER TABLE SGPREFERE1 ADD CODPLANPC CHAR(13);

Update Rdb$Relation_Fields set Rdb$Description =
'Código do planejamento padrão para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANPC';

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.3.8/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE CPCOMPRA ADD CONSTRAINT CPCOMPRAFKFNCC FOREIGN KEY (CODCC,ANOCC,CODFILIALCC,CODEMPCC) REFERENCES FNCC(CODCC,ANOCC,CODFILIAL,CODEMP);

ALTER TABLE CPCOMPRA ADD CONSTRAINT CPCOMPRAFKFNCONTA FOREIGN KEY (NUMCONTA,CODFILIALCT,CODEMPCT) REFERENCES FNCONTA(NUMCONTA,CODFILIAL,CODEMP);

ALTER TABLE CPCOMPRA ADD CONSTRAINT CPCOMPRAFKFNPLANEJAMENTO FOREIGN KEY (CODPLAN,CODFILIALPN,CODEMPPN) REFERENCES FNPLANEJAMENTO(CODPLAN,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE1 ADD CONSTRAINT SGPREFERE1FKPLANCHEQUE FOREIGN KEY (CODPLANPC,CODFILIALPC,CODEMPPC) REFERENCES FNPLANEJAMENTO(CODPLAN,CODFILIAL,CODEMP);

SET TERM ^ ;

ALTER TRIGGER CPCOMPRATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Alter Procedure... */
/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
NUMCONTA CHAR(10),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13))
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG,STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB,
                          codempca, codfilialca,  numconta, codempcc, codfilialcc, anocc, codcc, codemppn, codfilialpn, codplan)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra,'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB,
                                 :codempct, :codfilialct, :numconta, :codempcc, :codfilialcc, :anocc, :codcc, :codemppn, :codfilialpn, :codplan
                          );
   END
END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.9 (18/10/2010)';
    suspend;
end
^

/* Create Trigger... */
CREATE TRIGGER FNCHEQUETGAU FOR FNCHEQUE
ACTIVE AFTER UPDATE POSITION 0 
AS
begin

    -- Se o cheque mudar de Cadastrado para Emitido,
    -- deverá sinalizar o titulo para ser baixado na conta de cheques.
    if(old.sitcheq='CA' and new.sitcheq='ED') then
    begin

        update fnpagcheq pc
        set pc.baixa='S'
        where pc.codempch=new.codemp and pc.codfilialch=new.codfilial and pc.seqcheq=new.seqcheq;

    end
    -- Deve realizar a transferência entre as contas de cheque
    -- Quando o cheque for compensado.
    if(old.sitcheq='ED' and new.sitcheq='CD') then
    begin

        update fnpagcheq pc
        set pc.transfere='S'
        where pc.codempch=new.codemp and pc.codfilialch=new.codfilial and pc.seqcheq=new.seqcheq;

    end
end
^

CREATE TRIGGER FNCONTAVINCULADATGAI FOR FNCONTAVINCULADA
ACTIVE AFTER INSERT POSITION 0 
AS
begin
    -- Se a conta foi marcada como contacheque, deve desmarcar as outras
    if(new.contacheque='S') then
    begin
        update fncontavinculada cv set cv.contacheque='N'
        where cv.codemp=new.codemp and cv.codfilial=new.codfilial and cv.numconta=new.numconta and
        cv.codempcv=new.codempcv and cv.codfilialcv=new.codfilialcv and cv.numcontacv<>new.numcontacv;
    end

end
^

CREATE TRIGGER FNCONTAVINCULADATGAU FOR FNCONTAVINCULADA
ACTIVE AFTER UPDATE POSITION 0 
AS
begin
    -- Se a conta foi marcada como contacheque, deve desmarcar as outras
    if(new.contacheque='S') then
    begin
        update fncontavinculada cv set cv.contacheque='N'
        where cv.codemp=new.codemp and cv.codfilial=new.codfilial and cv.numconta=new.numconta and
        cv.codempcv=new.codempcv and cv.codfilialcv=new.codfilialcv and cv.numcontacv<>new.numcontacv;
    end
end
^

CREATE TRIGGER FNPAGCHEQTGAI FOR FNPAGCHEQ
ACTIVE AFTER INSERT POSITION 0 
as
    declare variable dtvencitpag date;
begin

    -- Buscando informações do título
    select ip.dtvencitpag from fnitpagar ip
    where ip.codemp=new.codemp and ip.codfilial=new.codfilial and ip.codpag=new.codpag and ip.nparcpag=new.nparcpag
    into :dtvencitpag;

    -- Atualizando as datas do cheque
    update fncheque ch set ch.dtvenctocheq=:dtvencitpag
    where ch.codemp=new.codempch and ch.codfilial=new.codfilialch and ch.seqcheq=new.seqcheq;

end
^

CREATE TRIGGER FNPAGCHEQTGAU FOR FNPAGCHEQ
ACTIVE AFTER UPDATE POSITION 0 
as
    declare variable codfilialch smallint;
    declare variable contacheq char(10);

    declare variable codempcb int;
    declare variable codfilialcb smallint;
    declare variable contabaixa char(10);

    declare variable codemppn int;
    declare variable codfilialpn smallint;
    declare variable codplan char(13);

    declare variable tipocheq char(2);
    declare variable dtvenctocheq date;
    declare variable vlrcheq decimal(15,5);
    declare variable vlrapagitpag decimal(15,5);
    declare variable vlrbaixa decimal(15,5);

    declare variable statusitpagar char(2);

    declare variable codempla int;
    declare variable codfilialla smallint;
    declare variable codlanca int;
    declare variable codlancatransf int;

    declare variable codemppnorig int;
    declare variable codfilialpnorig smallint;
    declare variable codplanorig char(13);

    declare variable codemppntransf int;
    declare variable codfilialpntransf smallint;
    declare variable codplantransf char(13);

    declare variable numcheq int;
    declare variable dtcompcheq date;

    declare variable codempcatorig int;
    declare variable codfilialcatorig smallint;
    declare variable codplancatorig char(13);

    declare variable vlrlanca decimal(15,5);

    declare variable icont int;

begin

    -- Busca informações do cheque
    select ch.tipocheq, ch.contacheq, ch.dtvenctocheq, ch.vlrcheq, ch.numcheq, ch.dtcompcheq from fncheque ch
    where ch.codemp=new.codempch and ch.codfilial=new.codfilialch and ch.seqcheq=new.seqcheq
    into :tipocheq, :contacheq, :dtvenctocheq, :vlrcheq, :numcheq, :dtcompcheq;

    -- Baixa de título na emissão do cheque
    if(coalesce(old.baixa,'N')='N' and new.baixa='S') then
    begin

        -- Buscando filial da tabela de contas
        select icodfilial from sgretfilial(new.codemp,'FNCONTA') into :codfilialch;

        -- Se o cheque é de pagamento de fornecedores, deverá dar baixa no título para a conta de controle de cheques.
        if('PF' = :tipocheq) then
        begin

            -- Busca conta para a baixa
            select cv.codempcv, cv.codfilialcv, cv.numcontacv
            from fncontavinculada cv
            where cv.codemp=new.codempch and cv.codfilial=:codfilialch and cv.numconta=:contacheq
            into :codempcb, :codfilialcb, :contabaixa;

            -- Busca informações do planejamento
            select ip.vlrapagitpag, coalesce(ip.codemppn,p1.codemppc), coalesce(ip.codfilialpn,p1.codfilialpc), coalesce(ip.codplan,p1.codplanpc)
            from fnitpagar ip, sgprefere1 p1
            where ip.codemp=new.codemp and ip.codfilial=new.codfilial and ip.codpag=new.codpag and ip.nparcpag=new.nparcpag
            and p1.codemp=new.codemp and p1.codfilial=new.codfilial
            into :vlrapagitpag, :codemppn, :codfilialpn, codplan;

            -- Calculando valor e status da baixa de acordo com o valor do cheque.
            if(:vlrcheq >= :vlrapagitpag) then
            begin
                vlrbaixa = :vlrapagitpag;
                statusitpagar = 'PP';

            end
            else
            begin
                vlrbaixa = :vlrcheq;
                statusitpagar = 'PL';
            end

            -- Realizando a baixa
            update fnitpagar ip set
            ip.numconta=:contabaixa, ip.codempca=:codempcb, ip.codfilialca=:codfilialcb,
            ip.codplan=:codplan, ip.codemppn=:codemppn, ip.codfilialpn=:codfilialpn,
            ip.dtpagoitpag=:dtvenctocheq, ip.vlrpagoitpag=:vlrbaixa,
            ip.statusitpag=:statusitpagar
            where ip.codpag=new.codpag and ip.nparcpag=new.nparcpag and ip.codemp=new.codemp and ip.codfilial=new.codfilial;
        end
   end

    -- Transferencia de baixa na compensação do cheque
    if(coalesce(old.transfere,'N')='N' and new.transfere='S') then
    begin
        icont = 0;
        for
            select
            la.codemp, la.codfilial, la.codlanca, la.codemppn, la.codfilialpn, la.codplan, pg.codemppn, pg.codfilialpn, pg.codplan, la.vlrlanca
            from fnlanca la, fnitpagar pg, fnpagcheq pc
            where pc.codemp=pg.codemp and pc.codfilial=pg.codfilial and pc.codpag=pg.codpag and pc.nparcpag=pg.nparcpag and
            la.codemppg=pg.codemp and la.codfilialpg=pg.codfilial and la.codpag=pg.codpag and la.nparcpag=pg.nparcpag
            and pc.codemp=new.codemp and pc.codfilial=new.codfilial and pc.codpag=new.codpag and pc.nparcpag=new.nparcpag
            into :codempla, :codfilialla, :codlanca, :codemppnorig, :codfilialpnorig, :codplanorig, :codempcatorig, :codfilialcatorig, :codplancatorig, :vlrlanca
        do
        begin

             icont = :icont + 1;

            -- Buscando código do novo lançamento
            select iseq from spgeranum(:codempla, :codfilialla, 'LA' ) into :codlancatransf;

            -- Buscando conta planejamento da compensação.
            select ct.codemppn, ct.codfilialpn, ct.codplan
            from fncontavinculada cv, fnconta ct, fnconta ctv
            where ct.codemp=cv.codemp and ct.codfilial=cv.codfilial and ct.numconta=cv.numconta
            and ctv.codemppn=:codemppnorig and ctv.codfilialpn=:codfilialpnorig and ctv.codplan=:codplanorig
            and cv.codempcv=ctv.codemp and cv.codfilialcv=ctv.codfilial and cv.numcontacv=ctv.numconta and cv.contacheque='S'
            into :codemppntransf, :codfilialpntransf, codplantransf;

            -- Inserir lancamento na primeira passada....
            if(:icont=1) then
            begin

            -- Inserindo lançamento de transferência...
                insert into fnlanca (
                    tipolanca, codemp, codfilial, codlanca,
                    codemppn, codfilialpn, codplan,
                    datalanca, doclanca, histblanca, transflanca, vlrlanca )
                values (
                      'A', :codempla, :codfilialla, :codlancatransf,
                  :codemppntransf, :codfilialpntransf, :codplantransf,
                  :dtcompcheq, cast(:numcheq as char(15)),'COMPENSAÇÃO DO CHEQUE NRO:' || cast(:numcheq as char(15)),'S',0);
            end

            -- Inserindo sub-lançamento de transferência...

            insert into fnsublanca (
                codemp, codfilial, codlanca, codsublanca,
                codemppn, codfilialpn, codplan,
                datasublanca, vlrsublanca, histsublanca)
            values (
                :codempla, :codfilialla, :codlancatransf, :icont,
                :codemppnorig, :codfilialpnorig, :codplanorig,
                :dtcompcheq, :vlrlanca * -1 , 'COMPENSAÇÃO DO CHEQUE NRO:' || cast(:numcheq as char(15)));

        end

    end
end
^


/* Alter exist trigger... */
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
               new.FLAG,new.CODEMP,new.CODFILIAL, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE ,new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
               new.CODEMPFR, new.CODFILIALFR, new.CODFOR, :dVLR, :DTBASE, new.DOCCOMPRA,
               new.CODEMPBO, new.CODFILIALBO, new.CODBANCO, new.FLAG, new.CODEMP, new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan);
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

/* Alter Procedure... */
/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
NUMCONTA CHAR(10),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13))
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG,STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB,
                          codempca, codfilialca,  numconta, codempcc, codfilialcc, anocc, codcc, codemppn, codfilialpn, codplan)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra,'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB,
                                 :codempct, :codfilialct, :numconta, :codempcc, :codfilialcc, :anocc, :codcc, :codemppn, :codfilialpn, :codplan
                          );
   END
END
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.9 (18/10/2010)';
    suspend;
end
^

SET TERM ; ^

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCOMPRA POSITION 3;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPG POSITION 4;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPG POSITION 5;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLANOPAG POSITION 6;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPFR POSITION 7;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALFR POSITION 8;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFOR POSITION 9;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSE POSITION 10;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSE POSITION 11;

ALTER TABLE CPCOMPRA ALTER COLUMN SERIE POSITION 12;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTM POSITION 13;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTM POSITION 14;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOMOV POSITION 15;

ALTER TABLE CPCOMPRA ALTER COLUMN DOCCOMPRA POSITION 16;

ALTER TABLE CPCOMPRA ALTER COLUMN DTENTCOMPRA POSITION 17;

ALTER TABLE CPCOMPRA ALTER COLUMN DTEMITCOMPRA POSITION 18;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCDESCCOMPRA POSITION 19;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCIPICOMPRA POSITION 20;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPRODCOMPRA POSITION 21;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRLIQCOMPRA POSITION 22;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOMPRA POSITION 23;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCCOMPRA POSITION 24;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 25;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRADICCOMPRA POSITION 26;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSCOMPRA POSITION 27;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSSTCOMPRA POSITION 28;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIPICOMPRA POSITION 29;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEPISCOMPRA POSITION 30;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASECOFINSCOMPRA POSITION 31;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCOMPRA POSITION 32;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSSTCOMPRA POSITION 33;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIPICOMPRA POSITION 34;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPISCOMPRA POSITION 35;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOFINSCOMPRA POSITION 36;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFUNRURALCOMPRA POSITION 37;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFRETECOMPRA POSITION 38;

ALTER TABLE CPCOMPRA ALTER COLUMN VLROUTRASCOMPRA POSITION 39;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRISENTASCOMPRA POSITION 40;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTC POSITION 41;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTC POSITION 42;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOCOB POSITION 43;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPBO POSITION 44;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALBO POSITION 45;

ALTER TABLE CPCOMPRA ALTER COLUMN CODBANCO POSITION 46;

ALTER TABLE CPCOMPRA ALTER COLUMN IMPNOTACOMPRA POSITION 47;

ALTER TABLE CPCOMPRA ALTER COLUMN BLOQCOMPRA POSITION 48;

ALTER TABLE CPCOMPRA ALTER COLUMN EMMANUT POSITION 49;

ALTER TABLE CPCOMPRA ALTER COLUMN FLAG POSITION 50;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETECOMPRA POSITION 51;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPOFRETECOMPRA POSITION 52;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSOL POSITION 53;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSOL POSITION 54;

ALTER TABLE CPCOMPRA ALTER COLUMN CODSOL POSITION 55;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTN POSITION 56;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTN POSITION 57;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTRAN POSITION 58;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSERVACAO POSITION 59;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS01 POSITION 60;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS02 POSITION 61;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS03 POSITION 62;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS04 POSITION 63;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICCOMPRA POSITION 64;

ALTER TABLE CPCOMPRA ALTER COLUMN QTDFRETECOMPRA POSITION 65;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETEBASEICMS POSITION 66;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICBASEICMS POSITION 67;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICIPIBASEICMS POSITION 68;

ALTER TABLE CPCOMPRA ALTER COLUMN CHAVENFECOMPRA POSITION 69;

ALTER TABLE CPCOMPRA ALTER COLUMN STATUSCOMPRA POSITION 70;

ALTER TABLE CPCOMPRA ALTER COLUMN NRODI POSITION 71;

ALTER TABLE CPCOMPRA ALTER COLUMN DTREGDI POSITION 72;

ALTER TABLE CPCOMPRA ALTER COLUMN LOCDESEMBDI POSITION 73;

ALTER TABLE CPCOMPRA ALTER COLUMN SIGLAUFDESEMBDI POSITION 74;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPAISDESEMBDI POSITION 75;

ALTER TABLE CPCOMPRA ALTER COLUMN DTDESEMBDI POSITION 76;

ALTER TABLE CPCOMPRA ALTER COLUMN IDENTCONTAINER POSITION 77;

ALTER TABLE CPCOMPRA ALTER COLUMN CALCTRIB POSITION 78;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPRM POSITION 79;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALRM POSITION 80;

ALTER TABLE CPCOMPRA ALTER COLUMN TICKET POSITION 81;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCT POSITION 82;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCT POSITION 83;

ALTER TABLE CPCOMPRA ALTER COLUMN NUMCONTA POSITION 84;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCC POSITION 85;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCC POSITION 86;

ALTER TABLE CPCOMPRA ALTER COLUMN ANOCC POSITION 87;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCC POSITION 88;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPN POSITION 89;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPN POSITION 90;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLAN POSITION 91;

ALTER TABLE CPCOMPRA ALTER COLUMN DTINS POSITION 92;

ALTER TABLE CPCOMPRA ALTER COLUMN HINS POSITION 93;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUINS POSITION 94;

ALTER TABLE CPCOMPRA ALTER COLUMN DTALT POSITION 95;

ALTER TABLE CPCOMPRA ALTER COLUMN HALT POSITION 96;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUALT POSITION 97;

ALTER TABLE FNCHEQUE ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNCHEQUE ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNCHEQUE ALTER COLUMN CODEMPBO POSITION 3;

ALTER TABLE FNCHEQUE ALTER COLUMN SEQCHEQ POSITION 4;

ALTER TABLE FNCHEQUE ALTER COLUMN CODFILIALBO POSITION 5;

ALTER TABLE FNCHEQUE ALTER COLUMN CODBANC POSITION 6;

ALTER TABLE FNCHEQUE ALTER COLUMN AGENCIACHEQ POSITION 7;

ALTER TABLE FNCHEQUE ALTER COLUMN CONTACHEQ POSITION 8;

ALTER TABLE FNCHEQUE ALTER COLUMN NUMCHEQ POSITION 9;

ALTER TABLE FNCHEQUE ALTER COLUMN NOMEEMITCHEQ POSITION 10;

ALTER TABLE FNCHEQUE ALTER COLUMN NOMEFAVCHEQ POSITION 11;

ALTER TABLE FNCHEQUE ALTER COLUMN DTEMITCHEQ POSITION 12;

ALTER TABLE FNCHEQUE ALTER COLUMN DTVENCTOCHEQ POSITION 13;

ALTER TABLE FNCHEQUE ALTER COLUMN DTCOMPCHEQ POSITION 14;

ALTER TABLE FNCHEQUE ALTER COLUMN TIPOCHEQ POSITION 15;

ALTER TABLE FNCHEQUE ALTER COLUMN PREDATCHEQ POSITION 16;

ALTER TABLE FNCHEQUE ALTER COLUMN SITCHEQ POSITION 17;

ALTER TABLE FNCHEQUE ALTER COLUMN VLRCHEQ POSITION 18;

ALTER TABLE FNCHEQUE ALTER COLUMN HISTCHEQ POSITION 19;

ALTER TABLE FNCHEQUE ALTER COLUMN CNPJEMITCHEQ POSITION 20;

ALTER TABLE FNCHEQUE ALTER COLUMN CPFEMITCHEQ POSITION 21;

ALTER TABLE FNCHEQUE ALTER COLUMN CNPJFAVCHEQ POSITION 22;

ALTER TABLE FNCHEQUE ALTER COLUMN CPFFAVCHEQ POSITION 23;

ALTER TABLE FNCHEQUE ALTER COLUMN DDDFAVCHEQ POSITION 24;

ALTER TABLE FNCHEQUE ALTER COLUMN DDDEMITCHEQ POSITION 25;

ALTER TABLE FNCHEQUE ALTER COLUMN FONEEMITCHEQ POSITION 26;

ALTER TABLE FNCHEQUE ALTER COLUMN FONEFAVCHEQ POSITION 27;

ALTER TABLE FNCHEQUE ALTER COLUMN DTINS POSITION 28;

ALTER TABLE FNCHEQUE ALTER COLUMN HINS POSITION 29;

ALTER TABLE FNCHEQUE ALTER COLUMN IDUSUINS POSITION 30;

ALTER TABLE FNCHEQUE ALTER COLUMN DTALT POSITION 31;

ALTER TABLE FNCHEQUE ALTER COLUMN HALT POSITION 32;

ALTER TABLE FNCHEQUE ALTER COLUMN IDUSUALT POSITION 33;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN NUMCONTA POSITION 3;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN CODEMPCV POSITION 4;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN CODFILIALCV POSITION 5;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN NUMCONTACV POSITION 6;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN CONTACHEQUE POSITION 7;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN DTINS POSITION 8;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN HINS POSITION 9;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN IDUSUINS POSITION 10;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN DTALT POSITION 11;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN HALT POSITION 12;

ALTER TABLE FNCONTAVINCULADA ALTER COLUMN IDUSUALT POSITION 13;

ALTER TABLE FNPAGCHEQ ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNPAGCHEQ ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNPAGCHEQ ALTER COLUMN CODPAG POSITION 3;

ALTER TABLE FNPAGCHEQ ALTER COLUMN NPARCPAG POSITION 4;

ALTER TABLE FNPAGCHEQ ALTER COLUMN CODEMPCH POSITION 5;

ALTER TABLE FNPAGCHEQ ALTER COLUMN CODFILIALCH POSITION 6;

ALTER TABLE FNPAGCHEQ ALTER COLUMN SEQCHEQ POSITION 7;

ALTER TABLE FNPAGCHEQ ALTER COLUMN BAIXA POSITION 8;

ALTER TABLE FNPAGCHEQ ALTER COLUMN TRANSFERE POSITION 9;

ALTER TABLE FNPAGCHEQ ALTER COLUMN DTINS POSITION 10;

ALTER TABLE FNPAGCHEQ ALTER COLUMN HINS POSITION 11;

ALTER TABLE FNPAGCHEQ ALTER COLUMN IDUSUINS POSITION 12;

ALTER TABLE FNPAGCHEQ ALTER COLUMN DTALT POSITION 13;

ALTER TABLE FNPAGCHEQ ALTER COLUMN HALT POSITION 14;

ALTER TABLE FNPAGCHEQ ALTER COLUMN IDUSUALT POSITION 15;

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

ALTER TABLE SGPREFERE1 ALTER COLUMN TITORCTXT01 POSITION 18;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV3 POSITION 19;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT3 POSITION 20;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT3 POSITION 21;

ALTER TABLE SGPREFERE1 ALTER COLUMN ORDNOTA POSITION 22;

ALTER TABLE SGPREFERE1 ALTER COLUMN SETORVENDA POSITION 23;

ALTER TABLE SGPREFERE1 ALTER COLUMN PREFCRED POSITION 24;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOPREFCRED POSITION 25;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMO POSITION 26;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMO POSITION 27;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMOEDA POSITION 28;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV4 POSITION 29;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT4 POSITION 30;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT4 POSITION 31;

ALTER TABLE SGPREFERE1 ALTER COLUMN USACLASCOMIS POSITION 32;

ALTER TABLE SGPREFERE1 ALTER COLUMN PERCPRECOCUSTO POSITION 33;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODGRUP POSITION 34;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALGP POSITION 35;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPGP POSITION 36;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMARCA POSITION 37;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMC POSITION 38;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMC POSITION 39;

ALTER TABLE SGPREFERE1 ALTER COLUMN RGCLIOBRIG POSITION 40;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABFRETEVD POSITION 41;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABADICVD POSITION 42;

ALTER TABLE SGPREFERE1 ALTER COLUMN TRAVATMNFVD POSITION 43;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOVALIDORC POSITION 44;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLIMESMOCNPJ POSITION 45;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTBJ POSITION 46;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTJ POSITION 47;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTJ POSITION 48;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJOBRIGCLI POSITION 49;

ALTER TABLE SGPREFERE1 ALTER COLUMN JUROSPOSCALC POSITION 50;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFR POSITION 51;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFR POSITION 52;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFOR POSITION 53;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTN POSITION 54;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTN POSITION 55;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTRAN POSITION 56;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTF POSITION 57;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTF POSITION 58;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFOR POSITION 59;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT5 POSITION 60;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT5 POSITION 61;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV5 POSITION 62;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTLOTNEG POSITION 63;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEG POSITION 64;

ALTER TABLE SGPREFERE1 ALTER COLUMN NATVENDA POSITION 65;

ALTER TABLE SGPREFERE1 ALTER COLUMN IPIVENDA POSITION 66;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOSICMS POSITION 67;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPG POSITION 68;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPG POSITION 69;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAG POSITION 70;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTB POSITION 71;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTB POSITION 72;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTAB POSITION 73;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPCE POSITION 74;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALCE POSITION 75;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODCLASCLI POSITION 76;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDEC POSITION 77;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECFIN POSITION 78;

ALTER TABLE SGPREFERE1 ALTER COLUMN COMISPDUPL POSITION 79;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT6 POSITION 80;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT6 POSITION 81;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV6 POSITION 82;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQVENDA POSITION 83;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQCOMPRA POSITION 84;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAMATPRIM POSITION 85;

ALTER TABLE SGPREFERE1 ALTER COLUMN VENDAPATRIM POSITION 86;

ALTER TABLE SGPREFERE1 ALTER COLUMN PEPSPROD POSITION 87;

ALTER TABLE SGPREFERE1 ALTER COLUMN CNPJFOROBRIG POSITION 88;

ALTER TABLE SGPREFERE1 ALTER COLUMN INSCESTFOROBRIG POSITION 89;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAPRODSIMILAR POSITION 90;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTIALMOX POSITION 91;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT8 POSITION 92;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT8 POSITION 93;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV8 POSITION 94;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTNEGGRUP POSITION 95;

ALTER TABLE SGPREFERE1 ALTER COLUMN USATABPE POSITION 96;

ALTER TABLE SGPREFERE1 ALTER COLUMN TAMDESCPROD POSITION 97;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCCOMPPED POSITION 98;

ALTER TABLE SGPREFERE1 ALTER COLUMN OBSCLIVEND POSITION 99;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONTESTOQ POSITION 100;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIASPEDT POSITION 101;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCVENDA POSITION 102;

ALTER TABLE SGPREFERE1 ALTER COLUMN RECALCPCORC POSITION 103;

ALTER TABLE SGPREFERE1 ALTER COLUMN USALAYOUTPED POSITION 104;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERIFALTPARCVENDA POSITION 105;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACODPRODGEN POSITION 106;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENPROD POSITION 107;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENREF POSITION 108;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODBAR POSITION 109;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFAB POSITION 110;

ALTER TABLE SGPREFERE1 ALTER COLUMN FILBUSCGENCODFOR POSITION 111;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCAVLRULTCOMPRA POSITION 112;

ALTER TABLE SGPREFERE1 ALTER COLUMN ICMSVENDA POSITION 113;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOZERO POSITION 114;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIMGASSORC POSITION 115;

ALTER TABLE SGPREFERE1 ALTER COLUMN IMGASSORC POSITION 116;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTCPFCLI POSITION 117;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIECLI POSITION 118;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEFOR POSITION 119;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTECPFFOR POSITION 120;

ALTER TABLE SGPREFERE1 ALTER COLUMN USANOMEVENDORC POSITION 121;

ALTER TABLE SGPREFERE1 ALTER COLUMN SISCONTABIL POSITION 122;

ALTER TABLE SGPREFERE1 ALTER COLUMN ATBANCOIMPBOL POSITION 123;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCODBAR POSITION 124;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICORCOBSPED POSITION 125;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMENSORC POSITION 126;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMENSORC POSITION 127;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSORC POSITION 128;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOCOMPRA POSITION 129;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPCP POSITION 130;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPORC POSITION 131;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABSOLCP POSITION 132;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICFRETEBASEICM POSITION 133;

ALTER TABLE SGPREFERE1 ALTER COLUMN PRECOCPREL POSITION 134;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCORC POSITION 135;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTICOMIS POSITION 136;

ALTER TABLE SGPREFERE1 ALTER COLUMN USUATIVCLI POSITION 137;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTREC POSITION 138;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTREC POSITION 139;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTREC POSITION 140;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTPAG POSITION 141;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTPAG POSITION 142;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTPAG POSITION 143;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTC POSITION 144;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTC POSITION 145;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOCLI POSITION 146;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTITRECALTDTVENC POSITION 147;

ALTER TABLE SGPREFERE1 ALTER COLUMN LCREDGLOBAL POSITION 148;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDMANUTCOMOBRIG POSITION 149;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSPED POSITION 150;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCLASSPED POSITION 151;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGECLI POSITION 152;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGEFOR POSITION 153;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGETRANSP POSITION 154;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOMAVOLUMES POSITION 155;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACEP POSITION 156;

ALTER TABLE SGPREFERE1 ALTER COLUMN URLWSCEP POSITION 157;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSCP POSITION 158;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS01CP POSITION 159;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS02CP POSITION 160;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS03CP POSITION 161;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS04CP POSITION 162;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEPF POSITION 163;

ALTER TABLE SGPREFERE1 ALTER COLUMN CREDICMSSIMPLES POSITION 164;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMS POSITION 165;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMS POSITION 166;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSICMSSIMPLES POSITION 167;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACOMISVENDAORC POSITION 168;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACODUNIF POSITION 169;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV9 POSITION 170;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT9 POSITION 171;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT9 POSITION 172;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJP POSITION 173;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJP POSITION 174;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJP POSITION 175;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJR POSITION 176;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJR POSITION 177;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJR POSITION 178;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDR POSITION 179;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDR POSITION 180;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDR POSITION 181;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDC POSITION 182;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDC POSITION 183;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDC POSITION 184;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERAPAGEMIS POSITION 185;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCAFINCONTR POSITION 186;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCARMACONTR POSITION 187;

ALTER TABLE SGPREFERE1 ALTER COLUMN CASASDECPRE POSITION 188;

ALTER TABLE SGPREFERE1 ALTER COLUMN VISUALIZALUCR POSITION 189;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSNFE POSITION 190;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFE POSITION 191;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFELIN POSITION 192;

ALTER TABLE SGPREFERE1 ALTER COLUMN FORMATODANFE POSITION 193;

ALTER TABLE SGPREFERE1 ALTER COLUMN AMBIENTENFE POSITION 194;

ALTER TABLE SGPREFERE1 ALTER COLUMN PROCEMINFE POSITION 195;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERPROCNFE POSITION 196;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICNFE POSITION 197;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTONFE POSITION 198;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFADPRODNFE POSITION 199;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPNF POSITION 200;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALNF POSITION 201;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMAILNF POSITION 202;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 211;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOT POSITION 212;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOAPROV POSITION 213;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFT POSITION 214;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFT POSITION 215;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFORFT POSITION 216;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOMIS POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESPECIALCOMIS POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTS POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVS POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTS POSITION 222;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 223;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 224;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 225;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 226;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 227;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPSV POSITION 228;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALSV POSITION 229;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAGSV POSITION 230;

ALTER TABLE SGPREFERE1 ALTER COLUMN ARREDPRECO POSITION 231;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPC POSITION 232;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPC POSITION 233;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANPC POSITION 234;

commit;
