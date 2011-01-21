/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE SGPREFERE1 ADD EXIBEPARCOBSDANFE CHAR(1) DEFAULT 'S';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve adicionar o desdobramento das parcelas nas observações da DANFE.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='EXIBEPARCOBSDANFE';

/* Alter Procedure... */
/* Alter (EQRELINVPRODSP) */
SET TERM ^ ;

ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR(1),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD CHAR(13),
DESCPROD CHAR(100),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
declare variable cmultialmox char(1);
declare variable ncustompm numeric(15,5);
begin
  /* Relatório de estoque */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMP) INTO :CMULTIALMOX;

  if (CCODGRUP IS NOT NULL) then
  begin
     CCODGRUP = rtrim(CCODGRUP);
     if (strlen(CCODGRUP)<14) then
        CCODGRUP = CCODGRUP || '%';
  end
  FOR SELECT P.CODPROD, P.REFPROD, P.DESCPROD, P.CODGRUP
    FROM EQPRODUTO P
    WHERE P.CODEMP=:ICODEMP AND P.CODFILIAL=:SCODFILIAL AND
          ( ( :CCODGRUP IS NULL ) OR
            (P.CODGRUP LIKE :CCODGRUP AND P.CODEMPGP=:ICODEMPGP AND
             P.CODFILIALGP=:SCODFILIALGP) )
    INTO :CODPROD, :REFPROD, :DESCPROD, :CODGRUP DO
  BEGIN
     SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODSLDSP(null, null, null, :ICODEMP,
        :SCODFILIAL, :CODPROD, :DDTESTOQ, 0, 0,
        :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
       INTO :SALDO, :NCUSTOMPM;
     if (CTIPOCUSTO='M') then
        CUSTO = NCUSTOMPM;
     else
        SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL, :CODPROD,
          :SALDO, :DDTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
        INTO :CUSTO;
     VLRESTOQ = CUSTO * SALDO;
     SUSPEND;
  END
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.0 (27/10/2010)';
    suspend;
end
^

/* empty dependent procedure body */
/* Clear: ATBUSCAPRECOSP for: VDBUSCAPRECOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDADICITORCRECMERCSP for: VDBUSCAPRECOSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
 BEGIN EXIT; END
^

/* Alter (VDBUSCAPRECOSP) */
ALTER PROCEDURE VDBUSCAPRECOSP(ICODPROD INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPTM INTEGER,
ICODFILIALTM SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(14,5))
 AS
declare variable icodtab integer;
declare variable icodemptab integer;
declare variable icodfilialtab smallint;
declare variable icodclascli integer;
declare variable icodempclascli integer;
declare variable icodfilialclascli smallint;
declare variable percdesccli numeric(3,2);
declare variable desccli char(1);
declare variable arredpreco smallint;
declare variable codfilialpf integer;
declare variable centavos decimal(2,2);
declare variable codfilialprecoprod smallint;
begin
    -- Buscando código da filial de preferencias
    select icodfilial from sgretfilial(:icodemp,'SGFILIAL') into :codfilialpf;

   -- Buscando código da filial da tabela vdprecoprod
    select icodfilial from sgretfilial(:icodemp,'VDPRECOPROD') into :codfilialprecoprod;

    -- Buscando preferencias de arredondamento;
    select coalesce(arredpreco, 0)
    from sgprefere1 p1
    where p1.codemp=:icodemp and p1.codfilial=:codfilialpf
    into :arredpreco;

    -- Buscando tabela de preços do tipo de movimento;
    select codtab, codemptb, codfilialtb
    from eqtipomov
    where codtipomov=:icodtipomov and codemp=:icodemptm and codfilial=:icodfilialtm
    into :icodtab, :icodemptab, :icodfilialtab;

    -- Buscando informações do cliente;
    select codclascli, codempcc, codfilialcc, coalesce(percdesccli,0) percdesccli
    from vdcliente
    where codcli=:icodcli and codemp=:icodempcl and codfilial=:icodfilialcl
    into :icodclascli, :icodempclascli, icodfilialclascli, :percdesccli;

    -- Buscando preço da tabela de preços utilizando todos os filtros
    select precoprod from vdprecoprod pp
    where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg and pp.codfilialpg=:icodfilialpg
    and pp.codtab=:icodtab and pp.codemptb=:icodemptab and pp.codfilialtb=:icodfilialtab
    and pp.codclascli=:icodclascli and pp.codempcc=:icodempclascli and pp.codfilialcc=:icodfilialclascli
    and pp.codemp=:icodemp and pp.codfilial=:codfilialprecoprod
    into :preco;

    --Se não encontrou um preço de tabela usando todos os filtros, deve retirar o filtro de classificação do cliente
    if ((preco is null) or (preco = 0)) then
    begin
        select max(pp.precoprod) from vdprecoprod pp
        where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg
        and pp.codfilialpg=:icodfilialpg and pp.codtab=:icodtab and pp.codemptb=:icodemptab
        and pp.codfilialtb=:icodfilialtab and pp.codclascli is null
        and pp.codemp=:icodemp and pp.codfilial=:icodfilial
        into :preco;
    end

    --Se ainda não conseguiu pagar o preco, deve utilizar o preço base do produto aplicando o desconto especial do cliente se houver
    if ((preco is null) or (preco = 0)) then
    begin

        select coalesce(pd.precobaseprod,0), coalesce(pd.desccli,'N') from eqproduto pd
        where pd.codprod=:icodprod and pd.codemp=:icodemp and pd.codfilial=:icodfilial
        into :preco, :desccli;

        -- Verifica se o cliente possui desconto especial e o produto permite este desconto...
        if( percdesccli >0 and 'S' = :desccli ) then
        begin

            preco = :preco - (:preco * (:percdesccli / 100)) ;

        end

    end

    if( :arredpreco > 0 ) then
    begin

        -- capturando valor dos centavos
        centavos = ( cast(:preco as decimal(15,2)) - truncate(preco) ) * 10;

        -- se o valor em centavos é maior ou igual ao parametro de arredondamento (arredondar para cima)
        if(:centavos >= :arredpreco) then
        begin
            preco = truncate(preco) + 1;
        end
        else
        begin
            preco = truncate(preco);
        end

    end

    suspend;

end
^

/* Restore procedure body: ATBUSCAPRECOSP */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE iCodEmpTM INTEGER;
  DECLARE VARIABLE iCodFilialTM INTEGER;
  DECLARE VARIABLE iCodCli INTEGER;
  DECLARE VARIABLE iCodEmpCli INTEGER;
  DECLARE VARIABLE iCodFilialCli INTEGER;
BEGIN
  SELECT CODTIPOMOV2,CODEMPT2,CODFILIALT2 FROM SGPREFERE1 WHERE CODEMP=:ICODEMP
         AND CODFILIAL=:ICODFILIAL INTO iCodTipoMov,iCodEmpTM,iCodFilialTM;
  SELECT CODCLI,CODEMPCL,CODFILIALCL FROM ATCONVENIADO WHERE CODCONV=:ICODCONV
         AND CODEMP=:ICODEMPCV AND CODFILIAL=:ICODFILIALCV INTO iCodCli,iCodEmpCli,iCodFilialCli;

  SELECT PRECO FROM VDBUSCAPRECOSP(:ICODPROD,:iCodCli,:iCodEmpCli,:iCodFilialCli,:ICODPLANOPAG,:ICODEMPPG,
    :ICODFILIALPG,:iCodTipoMov,:iCodEmpTM,:iCodFilialTM,:ICODEMP,:ICODFILIAL) INTO PRECO;

  SUSPEND;
END
^

/* Restore procedure body: VDADICITORCRECMERCSP */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod char(13);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitorc numeric(15,5);
declare variable qtditorc numeric(15,5);
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemppg integer;
declare variable codfilialpg smallint;
declare variable codplanopag integer;
declare variable gerachamado char(1);
declare variable obsitorc varchar(10000);
declare variable descprod char(100);
declare variable vlrliqitorc numeric(15,5);
declare variable vlrproditorc numeric(15,5);
declare variable usaprecopecaserv char(1);
declare variable codprodpeca integer;
declare variable garantia char(1);
declare variable codprodir integer;
declare variable refprodir char(13);
begin
    
    -- Buscando preferencias do GMS
    select coalesce(p8.usaprecopecaserv,'N') from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :usaprecopecaserv;

    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos,
        ir.gerachamado, pd.descprod, irm.codprod, irm.garantia, irm.codprod codprodir, irm.refprod refprodir
        from eqitrecmercitos ir, eqitrecmerc irm, eqproduto pd
        where
        irm.codemp=ir.codemp and irm.codfilial=ir.codfilial and irm.ticket=ir.ticket and irm.coditrecmerc=ir.coditrecmerc
        and pd.codemp=irm.codemppd and pd.codfilial=irm.codfilialpd and irm.codprod=pd.codprod
        and ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc,
             :gerachamado, :descprod, :codprodpeca, :garantia, :codprodir, :refprodir
        do
        begin

--            if(:codprod <> :codprodant or :codprodant is null) then
--            begin

                -- Verifica se é serviço, sendo serviço insere a descriçao do produto
                -- consertado na descrição auxiliar do item de orçamento
                if(:gerachamado=:servicos and :gerachamado='S') then
                begin
                    if( 'N' = :garantia ) then
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod;
                    end
                    else
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod || '[G]';
                    end

                end

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda
                -- Se não está em garantia...

                if('N' = :garantia) then
                begin
                    -- Se o preço é basedo na peca, deve buscar o preço da peça
                    if(usaprecopecaserv='S') then
                    begin
                        select preco from vdbuscaprecosp(:codprodpeca,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                    else
                    begin
                        select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                end
                else
                begin

                    precoitorc = 0.00;

                end

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                vlrproditorc = :qtditorc * :precoitorc;
                vlrliqitorc = vlrproditorc;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox, obsitorc, vlrproditorc, vlrliqitorc, sitproditorc)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox, :obsitorc, :vlrproditorc, :vlrliqitorc,
                'PE') ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

--            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSTGAU
as
declare variable num_os_tot integer;
declare variable num_os_tot_item integer;
declare variable num_os_status integer;
declare variable num_os_status_item integer;
declare variable status_atual_os char(2);
declare variable novo_status_os char(2);
declare variable novo_status_it char(2);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable codorc integer;
declare variable tipoorc char(1);
declare variable coditorc smallint;

begin

    -- Buscando status geral da OS
    select rm.status from eqrecmerc rm
    where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
    into status_atual_os;

    -- Verifica se deve atualizar o status geral da OS
    if(status_atual_os in('PE','AN','EA','EC','OA','EO','PT')) then
    begin

        -- carregando quantidade de itens para a OS
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
        into num_os_tot;

        -- carregando quantidade de itens para um item
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket and os.coditrecmerc=new.coditrecmerc
        into num_os_tot_item;

        -- carregando quantidade de ítens na situação atual toda a OS
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
        and os.statusitos=new.statusitos
        into num_os_status;

        -- carregando quantidade de ítens na situação atual para um item
        select count(*) from eqitrecmercitos os
        where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket and os.coditrecmerc=new.coditrecmerc
        and os.statusitos=new.statusitos
        into num_os_status_item;

        -- Atualização do status da ordem de serviço
    
        if(num_os_status = num_os_tot) then
        begin
            if(new.statusitos = 'CO') then
            begin
                novo_status_os = 'PT';
            end
            else
            begin
                novo_status_os = new.statusitos;
            end

           update eqrecmerc rm set rm.status=:novo_status_os
           where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;

        end
        -- Atualização do status do item da OS
        if(num_os_status_item = num_os_tot_item) then
        begin
        if(new.statusitos = 'CO') then
            begin
                novo_status_it = 'FN';
            end
            else
            begin
                novo_status_it = new.statusitos;
            end

            update eqitrecmerc ir set ir.statusitrecmerc =:novo_status_it
            where ir.codemp=new.codemp and ir.codfilial=new.codfilial and ir.ticket=new.ticket and ir.coditrecmerc=new.coditrecmerc;

        end

    end

    -- Quando ítem for concluído, deve alterar o status do orçamento para 'PRODUZIDO' OP;

    if(old.statusitos!='CO' and new.statusitos='CO') then
    begin

        for select itos.codempoc, itos.codfilialoc, itos.codorc, itos.tipoorc, itos.coditorc
            from eqitrecmercitositorc itos
            where itos.codemp=new.codemp and itos.codfilial=new.codfilial and itos.ticket=new.ticket
            and itos.coditrecmerc=new.coditrecmerc and itos.coditos=new.coditos
        into :codempoc, :codfilialoc, :codorc, :tipoorc, :coditorc
        do
        begin
            update vditorcamento ito set ito.statusitorc='OP', ito.sitproditorc='PD', ito.aceiteitorc='S', ito.aprovitorc='S'
            where ito.codemp=:codempoc and ito.codfilial=:codfilialoc and ito.tipoorc=:tipoorc and ito.codorc=:codorc and ito.coditorc=:coditorc;
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCTGAU INACTIVE
^

/* Alter exist trigger... */
ALTER TRIGGER EQRECMERCTGAU
AS
begin
    -- Mecanismo de contingência do status dos itens de OS

    if( new.status='PT' ) then
    begin
        update eqitrecmercitos set statusitos = 'CO'
        where codemp=new.codemp and codfilial=new.codfilial and ticket = new.ticket and statusitos!='CO';
    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER FNCHEQUETGAU
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

     -- Seve limpar os flags caso o status do cheque volte para cadastrado
    if(new.sitcheq='CA' and old.sitcheq!='CA') then
    begin
        update fnpagcheq pc
        set pc.baixa='N', pc.transfere='N'
        where pc.codempch=new.codemp and pc.codfilialch=new.codfilial and pc.seqcheq=new.seqcheq;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNPAGCHEQTGAU
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
    declare variable codlancatransf int;

    declare variable codemppnorig int;
    declare variable codfilialpnorig smallint;
    declare variable codplanorig char(13);

    declare variable codemppntransf int;
    declare variable codfilialpntransf smallint;
    declare variable codplantransf char(13);

    declare variable numcheq int;
    declare variable dtcompcheq date;

    declare variable vlrlanca decimal(15,5);

    declare variable icont int;

    declare variable obsitpag varchar(250);

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
            select ip.vlrapagitpag, coalesce(ip.codemppn,p1.codemppc), coalesce(ip.codfilialpn,p1.codfilialpc), coalesce(ip.codplan,p1.codplanpc), coalesce(ip.obsitpag,'')
            from fnitpagar ip, sgprefere1 p1
            where ip.codemp=new.codemp and ip.codfilial=new.codfilial and ip.codpag=new.codpag and ip.nparcpag=new.nparcpag
            and p1.codemp=new.codemp and p1.codfilial=new.codfilial
            into :vlrapagitpag, :codemppn, :codfilialpn, :codplan, :obsitpag;

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


            if(:obsitpag='') then
            begin
                obsitpag = 'PGTO C/CHEQUE NRO:' || :numcheq;
            end
            else
            begin
                obsitpag = rtrim(obsitpag) || ' / ' || 'PGTO C/CHEQUE NRO:' || :numcheq;
            end

            -- Realizando a baixa
            update fnitpagar ip set
            ip.numconta=:contabaixa, ip.codempca=:codempcb, ip.codfilialca=:codfilialcb,
            ip.codplan=:codplan, ip.codemppn=:codemppn, ip.codfilialpn=:codfilialpn,
            ip.dtpagoitpag=:dtvenctocheq, ip.vlrpagoitpag=:vlrbaixa,
            ip.statusitpag=:statusitpagar, obsitpag=:obsitpag
            where ip.codpag=new.codpag and ip.nparcpag=new.nparcpag and ip.codemp=new.codemp and ip.codfilial=new.codfilial;
        end
   end

    -- Transferencia de baixa na compensação do cheque
    if(coalesce(old.transfere,'N')='N' and new.transfere='S') then
    begin
        icont = 0;
        for
            select
            la.codemp, la.codfilial, la.codemppn, la.codfilialpn, la.codplan, la.vlrlanca
            from fnlanca la, fnitpagar pg, fnpagcheq pc
            where pc.codemp=pg.codemp and pc.codfilial=pg.codfilial and pc.codpag=pg.codpag and pc.nparcpag=pg.nparcpag and
            la.codemppg=pg.codemp and la.codfilialpg=pg.codfilial and la.codpag=pg.codpag and la.nparcpag=pg.nparcpag
            and pc.codemp=new.codemp and pc.codfilial=new.codfilial and pc.codpag=new.codpag and pc.nparcpag=new.nparcpag
            into :codempla, :codfilialla, :codemppnorig, :codfilialpnorig, :codplanorig, :vlrlanca
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

/* Alter Procedure... */
/* Alter (ATBUSCAPRECOSP) */
ALTER PROCEDURE ATBUSCAPRECOSP(ICODPROD INTEGER,
ICODCONV INTEGER,
ICODEMPCV INTEGER,
ICODFILIALCV SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(15,5))
 AS
DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE iCodEmpTM INTEGER;
  DECLARE VARIABLE iCodFilialTM INTEGER;
  DECLARE VARIABLE iCodCli INTEGER;
  DECLARE VARIABLE iCodEmpCli INTEGER;
  DECLARE VARIABLE iCodFilialCli INTEGER;
BEGIN
  SELECT CODTIPOMOV2,CODEMPT2,CODFILIALT2 FROM SGPREFERE1 WHERE CODEMP=:ICODEMP
         AND CODFILIAL=:ICODFILIAL INTO iCodTipoMov,iCodEmpTM,iCodFilialTM;
  SELECT CODCLI,CODEMPCL,CODFILIALCL FROM ATCONVENIADO WHERE CODCONV=:ICODCONV
         AND CODEMP=:ICODEMPCV AND CODFILIAL=:ICODFILIALCV INTO iCodCli,iCodEmpCli,iCodFilialCli;

  SELECT PRECO FROM VDBUSCAPRECOSP(:ICODPROD,:iCodCli,:iCodEmpCli,:iCodFilialCli,:ICODPLANOPAG,:ICODEMPPG,
    :ICODFILIALPG,:iCodTipoMov,:iCodEmpTM,:iCodFilialTM,:ICODEMP,:ICODFILIAL) INTO PRECO;

  SUSPEND;
END
^

/* Alter (EQRELINVPRODSP) */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR(1),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD CHAR(13),
DESCPROD CHAR(100),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
declare variable cmultialmox char(1);
declare variable ncustompm numeric(15,5);
begin
  /* Relatório de estoque */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMP) INTO :CMULTIALMOX;

  if (CCODGRUP IS NOT NULL) then
  begin
     CCODGRUP = rtrim(CCODGRUP);
     if (strlen(CCODGRUP)<14) then
        CCODGRUP = CCODGRUP || '%';
  end
  FOR SELECT P.CODPROD, P.REFPROD, P.DESCPROD, P.CODGRUP
    FROM EQPRODUTO P
    WHERE P.CODEMP=:ICODEMP AND P.CODFILIAL=:SCODFILIAL AND
          ( ( :CCODGRUP IS NULL ) OR
            (P.CODGRUP LIKE :CCODGRUP AND P.CODEMPGP=:ICODEMPGP AND
             P.CODFILIALGP=:SCODFILIALGP) )
    INTO :CODPROD, :REFPROD, :DESCPROD, :CODGRUP DO
  BEGIN
     SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODSLDSP(null, null, null, :ICODEMP,
        :SCODFILIAL, :CODPROD, :DDTESTOQ, 0, 0,
        :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
       INTO :SALDO, :NCUSTOMPM;
     if (CTIPOCUSTO='M') then
        CUSTO = NCUSTOMPM;
     else
        SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL, :CODPROD,
          :SALDO, :DDTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
        INTO :CUSTO;
     VLRESTOQ = CUSTO * SALDO;
     SUSPEND;
  END
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.0 (27/10/2010)';
    suspend;
end
^

/* Alter (VDADICITORCRECMERCSP) */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR(1),
SERVICOS CHAR(1),
NOVOS CHAR(1))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod char(13);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitorc numeric(15,5);
declare variable qtditorc numeric(15,5);
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemppg integer;
declare variable codfilialpg smallint;
declare variable codplanopag integer;
declare variable gerachamado char(1);
declare variable obsitorc varchar(10000);
declare variable descprod char(100);
declare variable vlrliqitorc numeric(15,5);
declare variable vlrproditorc numeric(15,5);
declare variable usaprecopecaserv char(1);
declare variable codprodpeca integer;
declare variable garantia char(1);
declare variable codprodir integer;
declare variable refprodir char(13);
begin
    
    -- Buscando preferencias do GMS
    select coalesce(p8.usaprecopecaserv,'N') from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :usaprecopecaserv;

    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos,
        ir.gerachamado, pd.descprod, irm.codprod, irm.garantia, irm.codprod codprodir, irm.refprod refprodir
        from eqitrecmercitos ir, eqitrecmerc irm, eqproduto pd
        where
        irm.codemp=ir.codemp and irm.codfilial=ir.codfilial and irm.ticket=ir.ticket and irm.coditrecmerc=ir.coditrecmerc
        and pd.codemp=irm.codemppd and pd.codfilial=irm.codfilialpd and irm.codprod=pd.codprod
        and ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc,
             :gerachamado, :descprod, :codprodpeca, :garantia, :codprodir, :refprodir
        do
        begin

--            if(:codprod <> :codprodant or :codprodant is null) then
--            begin

                -- Verifica se é serviço, sendo serviço insere a descriçao do produto
                -- consertado na descrição auxiliar do item de orçamento
                if(:gerachamado=:servicos and :gerachamado='S') then
                begin
                    if( 'N' = :garantia ) then
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod;
                    end
                    else
                    begin
                        obsitorc = :refprodir || ' - ' || :descprod || '[G]';
                    end

                end

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda
                -- Se não está em garantia...

                if('N' = :garantia) then
                begin
                    -- Se o preço é basedo na peca, deve buscar o preço da peça
                    if(usaprecopecaserv='S') then
                    begin
                        select preco from vdbuscaprecosp(:codprodpeca,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                    else
                    begin
                        select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                        :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                        into :precoitorc;
                    end
                end
                else
                begin

                    precoitorc = 0.00;

                end

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                vlrproditorc = :qtditorc * :precoitorc;
                vlrliqitorc = vlrproditorc;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox, obsitorc, vlrproditorc, vlrliqitorc, sitproditorc)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox, :obsitorc, :vlrproditorc, :vlrliqitorc,
                'PE') ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

--            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Alter (VDBUSCAPRECOSP) */
ALTER PROCEDURE VDBUSCAPRECOSP(ICODPROD INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL SMALLINT,
ICODPLANOPAG INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPTM INTEGER,
ICODFILIALTM SMALLINT,
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PRECO NUMERIC(14,5))
 AS
declare variable icodtab integer;
declare variable icodemptab integer;
declare variable icodfilialtab smallint;
declare variable icodclascli integer;
declare variable icodempclascli integer;
declare variable icodfilialclascli smallint;
declare variable percdesccli numeric(3,2);
declare variable desccli char(1);
declare variable arredpreco smallint;
declare variable codfilialpf integer;
declare variable centavos decimal(2,2);
declare variable codfilialprecoprod smallint;
begin
    -- Buscando código da filial de preferencias
    select icodfilial from sgretfilial(:icodemp,'SGFILIAL') into :codfilialpf;

   -- Buscando código da filial da tabela vdprecoprod
    select icodfilial from sgretfilial(:icodemp,'VDPRECOPROD') into :codfilialprecoprod;

    -- Buscando preferencias de arredondamento;
    select coalesce(arredpreco, 0)
    from sgprefere1 p1
    where p1.codemp=:icodemp and p1.codfilial=:codfilialpf
    into :arredpreco;

    -- Buscando tabela de preços do tipo de movimento;
    select codtab, codemptb, codfilialtb
    from eqtipomov
    where codtipomov=:icodtipomov and codemp=:icodemptm and codfilial=:icodfilialtm
    into :icodtab, :icodemptab, :icodfilialtab;

    -- Buscando informações do cliente;
    select codclascli, codempcc, codfilialcc, coalesce(percdesccli,0) percdesccli
    from vdcliente
    where codcli=:icodcli and codemp=:icodempcl and codfilial=:icodfilialcl
    into :icodclascli, :icodempclascli, icodfilialclascli, :percdesccli;

    -- Buscando preço da tabela de preços utilizando todos os filtros
    select precoprod from vdprecoprod pp
    where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg and pp.codfilialpg=:icodfilialpg
    and pp.codtab=:icodtab and pp.codemptb=:icodemptab and pp.codfilialtb=:icodfilialtab
    and pp.codclascli=:icodclascli and pp.codempcc=:icodempclascli and pp.codfilialcc=:icodfilialclascli
    and pp.codemp=:icodemp and pp.codfilial=:codfilialprecoprod
    into :preco;

    --Se não encontrou um preço de tabela usando todos os filtros, deve retirar o filtro de classificação do cliente
    if ((preco is null) or (preco = 0)) then
    begin
        select max(pp.precoprod) from vdprecoprod pp
        where pp.codprod=:icodprod and pp.codplanopag=:icodplanopag and pp.codemppg=:icodemppg
        and pp.codfilialpg=:icodfilialpg and pp.codtab=:icodtab and pp.codemptb=:icodemptab
        and pp.codfilialtb=:icodfilialtab and pp.codclascli is null
        and pp.codemp=:icodemp and pp.codfilial=:icodfilial
        into :preco;
    end

    --Se ainda não conseguiu pagar o preco, deve utilizar o preço base do produto aplicando o desconto especial do cliente se houver
    if ((preco is null) or (preco = 0)) then
    begin

        select coalesce(pd.precobaseprod,0), coalesce(pd.desccli,'N') from eqproduto pd
        where pd.codprod=:icodprod and pd.codemp=:icodemp and pd.codfilial=:icodfilial
        into :preco, :desccli;

        -- Verifica se o cliente possui desconto especial e o produto permite este desconto...
        if( percdesccli >0 and 'S' = :desccli ) then
        begin

            preco = :preco - (:preco * (:percdesccli / 100)) ;

        end

    end

    if( :arredpreco > 0 ) then
    begin

        -- capturando valor dos centavos
        centavos = ( cast(:preco as decimal(15,2)) - truncate(preco) ) * 10;

        -- se o valor em centavos é maior ou igual ao parametro de arredondamento (arredondar para cima)
        if(:centavos >= :arredpreco) then
        begin
            preco = truncate(preco) + 1;
        end
        else
        begin
            preco = truncate(preco);
        end

    end

    suspend;

end
^

SET TERM ; ^

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

ALTER TABLE SGPREFERE1 ALTER COLUMN EXIBEPARCOBSDANFE POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 211;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 212;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOT POSITION 213;

ALTER TABLE SGPREFERE1 ALTER COLUMN BLOQPRECOAPROV POSITION 214;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPFT POSITION 215;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALFT POSITION 216;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOFORFT POSITION 217;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAPRECOCOMIS POSITION 218;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESPECIALCOMIS POSITION 219;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 220;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTS POSITION 221;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOVS POSITION 222;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTS POSITION 223;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 224;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 225;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 226;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 227;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 228;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPSV POSITION 229;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALSV POSITION 230;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANOPAGSV POSITION 231;

ALTER TABLE SGPREFERE1 ALTER COLUMN ARREDPRECO POSITION 232;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPPC POSITION 233;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALPC POSITION 234;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANPC POSITION 235;

commit;


