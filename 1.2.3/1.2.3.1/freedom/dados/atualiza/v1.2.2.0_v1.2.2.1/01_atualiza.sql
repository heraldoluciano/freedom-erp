/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

Update Rdb$Relation_Fields set Rdb$Description =
'Status do recebimento da mercadoria.
PE - Pendente;
AN - Em análise (OS)
EA - Em andamento (OS)
PT - Pronto (OS)
EO - Em orçamento (entrada para concerto);
CA - Cancelada;
OA - Orçamento aprovado (entrada para concerto);
E1 - Em andamento finalizada primeira etapa;
E2 - Em andamento finalizada segunda etapa;
FN - Finalizado;
NE - Nota de entrada emitida;'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='STATUS';

/* Alter Field (Length): from 10 to 13... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=13, RDB$CHARACTER_LENGTH=13
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='VDCLIENTE' AND  RDB$FIELD_NAME='RGCLI');


ALTER TABLE SGPREFERE1 ADD DTVENCTONFE DATE;

Update Rdb$Relation_Fields set Rdb$Description =
'Data de vencimento da licença NFE.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='DTVENCTONFE';

ALTER TABLE SGPREFERE1 ADD KEYLICNFE VARCHAR(500);

Update Rdb$Relation_Fields set Rdb$Description =
'Chave de licenciamento NFE.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='KEYLICNFE';

/* Create Table... */
CREATE TABLE EQITRECMERCITOSITORC(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
TICKET INTEGER NOT NULL,
CODITRECMERC INTEGER NOT NULL,
CODITOS INTEGER NOT NULL,
CODEMPOC INTEGER NOT NULL,
CODFILIALOC SMALLINT NOT NULL,
CODORC INTEGER NOT NULL,
TIPOORC CHAR(1) NOT NULL,
CODITORC SMALLINT NOT NULL,
STATUS CHAR(2) DEFAULT 'EO' NOT NULL,
DTINS DATE DEFAULT 'today' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'today',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


Update Rdb$Relation_Fields set Rdb$Description =
'Coluna de status para repasse via triggers à tabela de ordem de seviço na atualização do status do orçamento.
(Vide status da ordem de seviço).'
where Rdb$Relation_Name='EQITRECMERCITOSITORC' and Rdb$Field_Name='STATUS';


/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER)
 AS
 BEGIN EXIT; END
^


/* Create Primary Key... */
SET TERM ; ^

ALTER TABLE EQITRECMERCITOSITORC ADD CONSTRAINT EQITRECMERCITOSITORCPK PRIMARY KEY (TICKET,CODITRECMERC,CODFILIAL,CODEMP,CODEMPOC,CODFILIALOC,CODORC,TIPOORC,CODITORC,CODITOS);

/* Create Foreign Key... */

ALTER TABLE EQITRECMERCITOSITORC ADD CONSTRAINT EQITRECMERCITOSITORCFKITRMITOS FOREIGN KEY (TICKET,CODITRECMERC,CODITOS,CODFILIAL,CODEMP) REFERENCES EQITRECMERCITOS(TICKET,CODITRECMERC,CODITOS,CODFILIAL,CODEMP);

ALTER TABLE EQITRECMERCITOSITORC ADD CONSTRAINT EQITRECMERCITOSITORCFKVDITORC FOREIGN KEY (CODORC,CODITORC,TIPOORC,CODFILIALOC,CODEMPOC) REFERENCES VDITORCAMENTO(CODORC,CODITORC,TIPOORC,CODFILIAL,CODEMP);

/*  Empty CPADICITCOMPRAPEDSP for CPUPCOMPRAPEDSP(param list change)  */
/* AssignEmptyBody proc */
SET TERM ^ ;

ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty CPADICITCOMPRARECMERCSP for LFBUSCANATSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty CPGERAITENTRADASP for LFBUSCANATSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPGERAITENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CODITVENDA INTEGER,
QTDIT INTEGER)
 AS
 BEGIN EXIT; END
^

/*  Empty VDADICITVENDAORCSP for LFBUSCANATSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA FLOAT,
VLRDESCITVENDA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/*  Empty VDADICITEMPDVSP for LFBUSCANATSP(param list change)  */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure CPUPCOMPRAPEDSP with new param-list */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure LFBUSCANATSP with new param-list */
ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
declare variable nroitensemitidos integer;
declare variable nroitens integer;
begin

    -- Atualizando os status de emissão do item do pedido de compra
    update cpitcompra set emititcompra='S' where coditcompra=:coditcomprapc
    and codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialcp;

    -- Contando o numero de ítens do pedido de compra que já estão emitidos
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc and emititcompra='S'
    into :nroitensemitidos;

    -- Contando o numero total de itens do pedido de compra
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc
    into :nroitens;

    -- Se todos os ítens já foram emitidos
    if (:nroitensemitidos = nroitens) then
    begin
        update cpcompra set statuscompra='ET' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end
    -- Se apenas alguns itens foram emitidos
    else if (:nroitensemitidos > 0) then
    begin
        update cpcompra set statuscompra='EP' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end

    insert into cpcompraped (codemp, codfilial, codcompra, coditcompra,
    codemppc, codfilialpc, codcomprapc, coditcomprapc)
    values (
    :codempcp, :codfilialcp, :codcompracp, :coditcompracp,
    :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
declare variable noest char(1);
declare variable cv char(1);
declare variable itmp integer;
begin

    -- Se o código do fornecedor é nulo subintende-se que deve realizar a busca para uma venda usando a
    -- UF do cliente
    if (codfor is null) then
    begin
        -- Verifica se a UF do emitente (Filial) é igual o estado do destinatário (Cliente) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, vdcliente c
        where f.codemp=:codemppd and f.codfilial=:filialatual and c.codcli=:codcli and c.codemp=:codempcl
        and c.codfilial=:codfilialcl and f.siglauf=coalesce(c.siglauf,c.ufcli)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para V (Venda)
        cv = 'V';
    end
    -- Se for uma busca de natureza de operação para compra (Busca pelo estado do fornecedor)
    else
    begin
        -- Verifica se a UF do emitente (Fornecedor) é igual o estado do destinatário (Filial) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, cpforneced fo
        where f.codemp=:codemppd and f.codfilial=:filialatual and fo.codfor=:codfor and fo.codemp=:codempfr
        and fo.codfilial=:codfilialfr and F.siglauf=coalesce(fo.uffor, fo.siglauf)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para C (Compra)
        cv = 'C';
    end

    -- Verifica se transação é dentro ou fora do estado e atualiza a variável NOEST
    if (itmp > 0) then
    begin
        noest='S';
    end
    else
    begin
        noest='N';
    end

    -- Primeira busca por CFOP na regra de CFOP
    select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
    where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
    and c.codfisc = p.codfisc and c.codfilial = p.codfilialfc and c.codemp = p.codempfc
    and r.codregra = c.codregra and r.codfilial = c.codfilialra and r.codemp = c.codempra
    and r.codemptm=:codemppd and r.codfilialtm=:codfilialtm and r.codtipomov=:codtipomov
    and r.cvitrf=:cv and r.noufitrf=:noest
    into codnat;

    -- Se não localizou nenhum cfop na pesquisa anterior pega a primeira cfop
    -- para a classificação fiscal do produto na regra.
    if (codnat is null) then
    begin
        select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
        where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
        and c.codfisc=p.codfisc and c.codfilial=p.codfilialfc and c.codemp = p.codempfc and r.codregra = c.codregra
        and r.codfilial = c.codfilialra and r.codemp = c.codempra
        and r.cvitrf=:cv and r.noufitrf=:noest
        into codnat;
     end

     if(codnat is null) then
     begin
--        exception vdvendaex01 'teste cv: '|| :cv || ' noest: ' || :noest || ' codfor: ' || cast(codfor as char(5))
--        || ' tipomov: ' || cast(codtipomov as char(5)) || ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;

--        exception vdvendaex01 ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;


     end


    suspend;
end
^

/* Alter (CPADICITCOMPRAPEDSP) */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod char(13);
declare variable precoitcompra numeric(15,5);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.precoitcompra, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :precoitcompra, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Alter (CPADICITCOMPRARECMERCSP) */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable codnat char(4);
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitcompra numeric(15,5);
begin
    
    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov,
    :codempfr, :codfilialfr, :codfor;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Buscando preço de compra
                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                into :precoitcompra;



                -- Inserir itens
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* empty dependent procedure body */
/* Clear: CPGERAENTRADASP for: CPGERAITENTRADASP */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter (CPGERAITENTRADASP) */
ALTER PROCEDURE CPGERAITENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CODITVENDA INTEGER,
QTDIT INTEGER)
 AS
declare variable coditcompra integer;
declare variable codfilialtm integer;
declare variable codtipomov integer;
declare variable codfilialfr integer;
declare variable codfor integer;
declare variable codnat char(4);
declare variable codfilialnt integer;
declare variable percicmsitcompra numeric(9,2);
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codfilialle integer;
declare variable codlote varchar(20);
declare variable qtditvenda numeric(18,3);
declare variable qtddevitvenda numeric(18,3);
declare variable precoitvenda numeric(18,3);
declare variable percdescitvenda numeric(15,5);
declare variable vlrdescitvenda numeric(18,3);
declare variable percicmsitvenda numeric(9,2);
declare variable vlrbaseicmsitvenda numeric(18,3);
declare variable vlricmsitvenda numeric(18,3);
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(18,3);
declare variable vlripiitvenda numeric(18,3);
declare variable vlrliqitvenda numeric(18,3);
declare variable vlradicitvenda numeric(18,3);
declare variable vlrfreteitvenda numeric(18,3);
declare variable vlrisentasitvenda numeric(18,3);
declare variable vlroutrasitvenda numeric(18,3);
declare variable vlrproditvenda numeric(18,3);
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO CODFILIALNT;
  SELECT CODFOR,CODFILIALFR,CODTIPOMOV,CODFILIALTM FROM CPCOMPRA WHERE CODCOMPRA=:CODCOMPRA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO
    :CODFOR,:CODFILIALFR,:CODTIPOMOV,:CODFILIALTM;
  FOR SELECT CODITVENDA,CODFILIALPD,CODPROD,CODFILIALLE,CODLOTE,
             QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,PERCICMSITVENDA,
             VLRBASEICMSITVENDA,VLRICMSITVENDA,PERCIPIITVENDA,VLRBASEIPIITVENDA,
             VLRIPIITVENDA,VLRLIQITVENDA,VLRADICITVENDA,VLRFRETEITVENDA,VLRISENTASITVENDA,
             VLROUTRASITVENDA,VLRPRODITVENDA,VLRPRODITVENDA FROM VDITVENDA WHERE
             CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA AND
             (:CODITVENDA IS NULL OR CODITVENDA=:CODITVENDA)
             ORDER BY CODITVENDA INTO CODITCOMPRA,CODFILIALPD,CODPROD,CODFILIALLE,CODLOTE,
             QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,PERCICMSITVENDA,
             VLRBASEICMSITVENDA,VLRICMSITVENDA,PERCIPIITVENDA,VLRBASEIPIITVENDA,
             VLRIPIITVENDA,VLRLIQITVENDA,VLRADICITVENDA,VLRFRETEITVENDA,VLRISENTASITVENDA,
             VLROUTRASITVENDA,VLRPRODITVENDA,VLRPRODITVENDA DO
  BEGIN
      IF (QTDIT IS NULL) THEN
        QTDIT = QTDITVENDA;
      SELECT SUM(QTDDEV) FROM CPCOMPRAVENDA WHERE TIPOVENDA=:TIPOVENDA
        AND CODVENDA=:CODVENDA AND CODITVENDA=:CODITVENDA
        AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO QTDDEVITVENDA;
      IF (QTDIT > QTDITVENDA OR QTDIT+QTDDEVITVENDA > QTDITVENDA) THEN
        EXCEPTION VDVENDAEX01 'O ITEM: '||CAST(CODITVENDA AS CHAR(3))||' DO PEDIDO: '||
          CAST(CODVENDA AS CHAR(6))||' JA FOI DEVOLVIDO!';
        
      SELECT CODNAT FROM LFBUSCANATSP (:CODFILIAL,:CODEMP,:CODFILIALPD,:CODPROD,NULL,NULL,NULL,
                                   :CODEMP,:CODFILIALFR,:CODFOR,:CODFILIALTM,:CODTIPOMOV) INTO CODNAT;
      INSERT INTO CPITCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODITCOMPRA,CODEMPPD,CODFILIALPD,CODPROD,
                              CODEMPLE,CODFILIALLE,CODLOTE,CODEMPNT,CODFILIALNT,CODNAT,QTDITCOMPRA,
                              PRECOITCOMPRA,PERCDESCITCOMPRA,VLRDESCITCOMPRA,PERCICMSITCOMPRA,
                              VLRBASEICMSITCOMPRA,VLRICMSITCOMPRA,PERCIPIITCOMPRA,VLRBASEIPIITCOMPRA,
                              VLRIPIITCOMPRA,VLRLIQITCOMPRA,VLRADICITCOMPRA,VLRFRETEITCOMPRA,
                              VLRISENTASITCOMPRA,VLROUTRASITCOMPRA,VLRPRODITCOMPRA,CUSTOITCOMPRA)
                       VALUES (:CODEMP,:CODFILIAL,:CODCOMPRA,:CODITCOMPRA,:CODEMP,:CODFILIALPD,:CODPROD,
                              :CODEMP,:CODFILIALLE,:CODLOTE,:CODEMP,:CODFILIALNT,:CODNAT,:QTDIT,
                              :PRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,
                              :VLRBASEICMSITVENDA,:VLRICMSITVENDA,:PERCIPIITVENDA,:VLRBASEIPIITVENDA,
                              :VLRIPIITVENDA,:VLRLIQITVENDA,:VLRADICITVENDA,:VLRFRETEITVENDA,
                              :VLRISENTASITVENDA,:VLROUTRASITVENDA,:VLRPRODITVENDA,:VLRPRODITVENDA);

      INSERT INTO CPCOMPRAVENDA(CODEMP,CODFILIAL,CODCOMPRA,CODITCOMPRA,CODEMPVD,
                                CODFILIALVD,TIPOVENDA,CODVENDA,CODITVENDA,QTDDEV)
                       VALUES (:CODEMP,:CODFILIAL,:CODCOMPRA,:CODITCOMPRA,:CODEMP,
                               :CODFILIALVD,:TIPOVENDA,:CODVENDA,:CODITCOMPRA,:QTDIT);
  END
  SUSPEND;
END
^

/* Alter (CPUPCOMPRAPEDSP) */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
declare variable nroitensemitidos integer;
declare variable nroitens integer;
begin

    -- Atualizando os status de emissão do item do pedido de compra
    update cpitcompra set emititcompra='S' where coditcompra=:coditcomprapc
    and codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialcp;

    -- Contando o numero de ítens do pedido de compra que já estão emitidos
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc and emititcompra='S'
    into :nroitensemitidos;

    -- Contando o numero total de itens do pedido de compra
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc
    into :nroitens;

    -- Se todos os ítens já foram emitidos
    if (:nroitensemitidos = nroitens) then
    begin
        update cpcompra set statuscompra='ET' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end
    -- Se apenas alguns itens foram emitidos
    else if (:nroitensemitidos > 0) then
    begin
        update cpcompra set statuscompra='EP' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end

    insert into cpcompraped (codemp, codfilial, codcompra, coditcompra,
    codemppc, codfilialpc, codcomprapc, coditcomprapc)
    values (
    :codempcp, :codfilialcp, :codcompracp, :coditcompracp,
    :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Alter (LFBUSCANATSP) */
ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
declare variable noest char(1);
declare variable cv char(1);
declare variable itmp integer;
begin

    -- Se o código do fornecedor é nulo subintende-se que deve realizar a busca para uma venda usando a
    -- UF do cliente
    if (codfor is null) then
    begin
        -- Verifica se a UF do emitente (Filial) é igual o estado do destinatário (Cliente) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, vdcliente c
        where f.codemp=:codemppd and f.codfilial=:filialatual and c.codcli=:codcli and c.codemp=:codempcl
        and c.codfilial=:codfilialcl and f.siglauf=coalesce(c.siglauf,c.ufcli)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para V (Venda)
        cv = 'V';
    end
    -- Se for uma busca de natureza de operação para compra (Busca pelo estado do fornecedor)
    else
    begin
        -- Verifica se a UF do emitente (Fornecedor) é igual o estado do destinatário (Filial) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, cpforneced fo
        where f.codemp=:codemppd and f.codfilial=:filialatual and fo.codfor=:codfor and fo.codemp=:codempfr
        and fo.codfilial=:codfilialfr and F.siglauf=coalesce(fo.uffor, fo.siglauf)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para C (Compra)
        cv = 'C';
    end

    -- Verifica se transação é dentro ou fora do estado e atualiza a variável NOEST
    if (itmp > 0) then
    begin
        noest='S';
    end
    else
    begin
        noest='N';
    end

    -- Primeira busca por CFOP na regra de CFOP
    select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
    where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
    and c.codfisc = p.codfisc and c.codfilial = p.codfilialfc and c.codemp = p.codempfc
    and r.codregra = c.codregra and r.codfilial = c.codfilialra and r.codemp = c.codempra
    and r.codemptm=:codemppd and r.codfilialtm=:codfilialtm and r.codtipomov=:codtipomov
    and r.cvitrf=:cv and r.noufitrf=:noest
    into codnat;

    -- Se não localizou nenhum cfop na pesquisa anterior pega a primeira cfop
    -- para a classificação fiscal do produto na regra.
    if (codnat is null) then
    begin
        select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
        where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
        and c.codfisc=p.codfisc and c.codfilial=p.codfilialfc and c.codemp = p.codempfc and r.codregra = c.codregra
        and r.codfilial = c.codfilialra and r.codemp = c.codempra
        and r.cvitrf=:cv and r.noufitrf=:noest
        into codnat;
     end

     if(codnat is null) then
     begin
--        exception vdvendaex01 'teste cv: '|| :cv || ' noest: ' || :noest || ' codfor: ' || cast(codfor as char(5))
--        || ' tipomov: ' || cast(codtipomov as char(5)) || ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;

--        exception vdvendaex01 ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;


     end


    suspend;
end
^

/* Alter (VDADICITEMPDVSP) */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod char(13);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;
  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITORCRECMERCSP) */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER)
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
begin
    
    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos
        from eqitrecmercitos ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda

                select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                into :precoitorc;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox) ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA FLOAT,
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(9,2);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(9,2);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,2);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod char(13);
declare variable stipoprod char(1);
declare variable percicmsst numeric(9,2);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

-- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,aliqipifisc,aliqfiscintra,
        tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,:ICODTIPOMOV, 'VD',:scodnat)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
        select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
        into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Restore procedure body: CPGERAENTRADASP */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
DECLARE VARIABLE STATUSVENDA CHAR(2);
DECLARE VARIABLE CODCLI INTEGER;
DECLARE VARIABLE CODFILIALCL INTEGER;
DECLARE VARIABLE CODFILIALCP INTEGER;
DECLARE VARIABLE CODTIPOMOV INTEGER;
DECLARE VARIABLE CODFILIALTM INTEGER;
DECLARE VARIABLE SERIE INTEGER;
DECLARE VARIABLE CODFILIALSE INTEGER;
DECLARE VARIABLE DOC INTEGER;
DECLARE VARIABLE CODFOR INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
DECLARE VARIABLE CODITVENDA INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPCOMPRA') INTO CODFILIALCP;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'VDCLINTE') INTO CODFILIALCL;

  SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'CP') INTO CODCOMPRA;

  SELECT CODCLI,CODFILIALCL,STATUSVENDA FROM VDVENDA WHERE CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO CODCLI,CODFILIALCL,STATUSVENDA;
  IF (SUBSTRING (STATUSVENDA FROM 1 FOR 1) = 'C') THEN
    EXCEPTION VDVENDAEX05;
  
  SELECT CODFOR FROM CPADICFORSP(:CODEMP,:CODFILIAL,:CODFILIALCL,:CODCLI) INTO CODFOR;

  SELECT P.CODTIPOMOV5,P.CODFILIALT5,
    T.SERIE,T.CODFILIALSE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL AND T.CODTIPOMOV=P.CODTIPOMOV5 AND
    T.CODEMP=P.CODEMPT5 AND T.CODFILIAL=P.CODFILIALT5
    INTO CODTIPOMOV,CODFILIALTM,SERIE,CODFILIALSE;

  SELECT * FROM LFNOVODOCSP(:SERIE,:CODEMP,:CODFILIALSE) INTO DOC;

  INSERT INTO CPCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        CODEMPFR,CODFILIALFR,CODFOR,CODEMPSE,CODFILIALSE,SERIE,
                        CODEMPTM,CODFILIALTM,CODTIPOMOV,DOCCOMPRA,DTENTCOMPRA,DTEMITCOMPRA)
                 SELECT :CODEMP,:CODFILIAL,:CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        :CODEMP,:CODFILIALFR,:CODFOR,:CODEMP,:CODFILIALSE,:SERIE,
                        CODEMP,:CODFILIALTM,:CODTIPOMOV,:DOC,DTSAIDAVENDA,DTEMITVENDA FROM
                        VDVENDA WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
  IF (CITEM = 'S') THEN
    EXECUTE PROCEDURE CPGERAITENTRADASP(:CODEMP,:CODFILIALCP,:CODCOMPRA,:CODFILIALVD,:TIPOVENDA,:CODVENDA,NULL,NULL);
    
  UPDATE VDVENDA SET STATUSVENDA='DV' WHERE TIPOVENDA=:TIPOVENDA AND CODVENDA=:CODVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD;
  
  SUSPEND;
END
^

/* Create Trigger... */
CREATE TRIGGER EQITRECMERCITOSITORCTGAU FOR EQITRECMERCITOSITORC
ACTIVE AFTER UPDATE POSITION 0 
AS
begin
    -- Atualizando status da OS
    if(old.status!=new.status) then
    begin
        -- Implementar futuramento o status parcial
        update eqrecmerc rm set rm.status=new.status
        where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
    end
end
^

CREATE TRIGGER EQITRECMERCITOSITORCTGBU FOR EQITRECMERCITOSITORC
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
end
^


/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGBD
AS
declare variable habconvcp char(1);
begin
    -- Buscando preferências
    select p5.habconvcp from sgprefere5 p5
    into habconvcp;

    if(habconvcp='S') then
    begin
        update ppop op set op.sitop='CA', op.justificcanc='OP de conversão: Item de compra excluído'
        where op.codempcp=old.codemp and op.codfilialcp=old.codfilial and op.codcompra=old.codcompra
        and op.coditcompra=old.coditcompra;
    end

    -- Executa procedure para exclusão de tabela de vinculo para numeros de serie
    execute procedure cpitcompraseriesp('D', old.codemp, old.codfilial, old.codcompra, old.coditcompra, old.codemppd, old.codfilialpd, old.codprod, null, old.qtditcompra);

    -- Deleta vinculos com tabela de compra de compra
    delete from cpcompraped cp where cp.codemp=old.codemp and cp.codfilial=old.codfilial
    and cp.codcompra = old.codcompra and cp.coditcompra=old.coditcompra;


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGAU
as
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
    declare variable vlricmsitorc numeric(15,5);
    declare variable vlripiitorc numeric(15,5);
    declare variable vlrpisitorc numeric(15,5);
    declare variable vlrcofinsitorc numeric(15,5);
    declare variable vlriritorc numeric(15,5);
    declare variable vlrcsocialitorc numeric(15,5);
    declare variable vlrissitorc numeric(15,5);

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin

    update vdorcamento set
        VLRDESCITORC = VLRDESCITORC - old.VLRDESCITORC + new.VLRDESCITORC,
        VLRPRODORC = VLRPRODORC - old.VLRPRODITORC + new.VLRPRODITORC,
        VLRLIQORC = VLRLIQORC - old.VLRLIQITORC + new.VLRLIQITORC
        where CODORC=new.CODORC and TIPOORC='O' and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL;

    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    if( visualizalucr = 'S' ) then
       begin

            -- Busca do custo da ultima compra;
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'U',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custouc;

            -- Busca do custo médio (MPM)
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'M',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custompm;

            -- Busca do custo peps
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'P',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custopeps;

            -- Atualizando registro na tabela de custos de item de orçamento

            update vditcustoorc ico set vlrprecoultcp=:custouc, vlrcustompm=:custompm, vlrcustopeps=:custopeps
                where ico.codemp=new.codemp and ico.codfilial=new.codfilial and ico.codorc=new.codorc
                and ico.tipoorc=new.tipoorc and ico.coditorc=new.coditorc;

            -- Buscando e inserindo previsão de tributos
            select vlricms, vlripi, vlrpis, vlrcofins, vlrir, vlrcsocial, vlriss
            from lfbuscaprevtriborc(new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc)
            into :vlricmsitorc, :vlripiitorc, :vlrpisitorc, :vlrcofinsitorc, :vlriritorc, :vlrcsocialitorc, :vlrissitorc;

            update vdprevtribitorc po set
            po.vlricmsitorc=:vlricmsitorc, po.vlripiitorc=:vlripiitorc, po.vlrpisitorc=:vlrpisitorc,
            po.vlrcofinsitorc=:vlrcofinsitorc, po.vlriritorc=:vlriritorc, po.vlrcsocialitorc=:vlrcsocialitorc,
            po.vlrissitorc=:vlrissitorc
            where po.codemp=new.codemp and po.codfilial=new.codfilial and po.codorc=new.codorc
            and po.tipoorc=new.tipoorc and po.coditorc=new.coditorc;

       end

       -- Se o status foi alterado para Aprovado (OL), deve atualizar o status das ordens de serviço vinculadas
       -- para OA (Orçamento aprovado)
       if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' ) then
       begin
            update eqitrecmercitositorc ios set ios.status='OA'
            where ios.codempoc=new.codemp and ios.codfilialoc=new.codfilial and ios.codorc=new.codorc
            and ios.tipoorc=new.tipoorc and ios.coditorc=new.coditorc;
       end

    end
end
^

/* Alter empty procedure CPUPCOMPRAPEDSP with new param-list */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter empty procedure LFBUSCANATSP with new param-list */
ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
 BEGIN EXIT; END
^

/* Alter Procedure... */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
declare variable nroitensemitidos integer;
declare variable nroitens integer;
begin

    -- Atualizando os status de emissão do item do pedido de compra
    update cpitcompra set emititcompra='S' where coditcompra=:coditcomprapc
    and codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialcp;

    -- Contando o numero de ítens do pedido de compra que já estão emitidos
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc and emititcompra='S'
    into :nroitensemitidos;

    -- Contando o numero total de itens do pedido de compra
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc
    into :nroitens;

    -- Se todos os ítens já foram emitidos
    if (:nroitensemitidos = nroitens) then
    begin
        update cpcompra set statuscompra='ET' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end
    -- Se apenas alguns itens foram emitidos
    else if (:nroitensemitidos > 0) then
    begin
        update cpcompra set statuscompra='EP' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end

    insert into cpcompraped (codemp, codfilial, codcompra, coditcompra,
    codemppc, codfilialpc, codcomprapc, coditcomprapc)
    values (
    :codempcp, :codfilialcp, :codcompracp, :coditcompracp,
    :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
declare variable noest char(1);
declare variable cv char(1);
declare variable itmp integer;
begin

    -- Se o código do fornecedor é nulo subintende-se que deve realizar a busca para uma venda usando a
    -- UF do cliente
    if (codfor is null) then
    begin
        -- Verifica se a UF do emitente (Filial) é igual o estado do destinatário (Cliente) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, vdcliente c
        where f.codemp=:codemppd and f.codfilial=:filialatual and c.codcli=:codcli and c.codemp=:codempcl
        and c.codfilial=:codfilialcl and f.siglauf=coalesce(c.siglauf,c.ufcli)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para V (Venda)
        cv = 'V';
    end
    -- Se for uma busca de natureza de operação para compra (Busca pelo estado do fornecedor)
    else
    begin
        -- Verifica se a UF do emitente (Fornecedor) é igual o estado do destinatário (Filial) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, cpforneced fo
        where f.codemp=:codemppd and f.codfilial=:filialatual and fo.codfor=:codfor and fo.codemp=:codempfr
        and fo.codfilial=:codfilialfr and F.siglauf=coalesce(fo.uffor, fo.siglauf)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para C (Compra)
        cv = 'C';
    end

    -- Verifica se transação é dentro ou fora do estado e atualiza a variável NOEST
    if (itmp > 0) then
    begin
        noest='S';
    end
    else
    begin
        noest='N';
    end

    -- Primeira busca por CFOP na regra de CFOP
    select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
    where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
    and c.codfisc = p.codfisc and c.codfilial = p.codfilialfc and c.codemp = p.codempfc
    and r.codregra = c.codregra and r.codfilial = c.codfilialra and r.codemp = c.codempra
    and r.codemptm=:codemppd and r.codfilialtm=:codfilialtm and r.codtipomov=:codtipomov
    and r.cvitrf=:cv and r.noufitrf=:noest
    into codnat;

    -- Se não localizou nenhum cfop na pesquisa anterior pega a primeira cfop
    -- para a classificação fiscal do produto na regra.
    if (codnat is null) then
    begin
        select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
        where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
        and c.codfisc=p.codfisc and c.codfilial=p.codfilialfc and c.codemp = p.codempfc and r.codregra = c.codregra
        and r.codfilial = c.codfilialra and r.codemp = c.codempra
        and r.cvitrf=:cv and r.noufitrf=:noest
        into codnat;
     end

     if(codnat is null) then
     begin
--        exception vdvendaex01 'teste cv: '|| :cv || ' noest: ' || :noest || ' codfor: ' || cast(codfor as char(5))
--        || ' tipomov: ' || cast(codtipomov as char(5)) || ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;

--        exception vdvendaex01 ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;


     end


    suspend;
end
^

/* Alter (CPADICITCOMPRAPEDSP) */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod char(13);
declare variable precoitcompra numeric(15,5);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.precoitcompra, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :precoitcompra, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Alter (CPADICITCOMPRARECMERCSP) */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable codnat char(4);
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable precoitcompra numeric(15,5);
begin
    
    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov,
    :codempfr, :codfilialfr, :codfor;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Buscando preço de compra
                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                into :precoitcompra;



                -- Inserir itens
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Alter (CPGERAENTRADASP) */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CITEM CHAR(1))
 RETURNS(CODCOMPRA INTEGER)
 AS
DECLARE VARIABLE STATUSVENDA CHAR(2);
DECLARE VARIABLE CODCLI INTEGER;
DECLARE VARIABLE CODFILIALCL INTEGER;
DECLARE VARIABLE CODFILIALCP INTEGER;
DECLARE VARIABLE CODTIPOMOV INTEGER;
DECLARE VARIABLE CODFILIALTM INTEGER;
DECLARE VARIABLE SERIE INTEGER;
DECLARE VARIABLE CODFILIALSE INTEGER;
DECLARE VARIABLE DOC INTEGER;
DECLARE VARIABLE CODFOR INTEGER;
DECLARE VARIABLE CODFILIALFR INTEGER;
DECLARE VARIABLE CODITVENDA INTEGER;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPFORNECED') INTO CODFILIALFR;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'CPCOMPRA') INTO CODFILIALCP;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'VDCLINTE') INTO CODFILIALCL;

  SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIAL,'CP') INTO CODCOMPRA;

  SELECT CODCLI,CODFILIALCL,STATUSVENDA FROM VDVENDA WHERE CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO CODCLI,CODFILIALCL,STATUSVENDA;
  IF (SUBSTRING (STATUSVENDA FROM 1 FOR 1) = 'C') THEN
    EXCEPTION VDVENDAEX05;
  
  SELECT CODFOR FROM CPADICFORSP(:CODEMP,:CODFILIAL,:CODFILIALCL,:CODCLI) INTO CODFOR;

  SELECT P.CODTIPOMOV5,P.CODFILIALT5,
    T.SERIE,T.CODFILIALSE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMP=:CODEMP
    AND P.CODFILIAL=:CODFILIAL AND T.CODTIPOMOV=P.CODTIPOMOV5 AND
    T.CODEMP=P.CODEMPT5 AND T.CODFILIAL=P.CODFILIALT5
    INTO CODTIPOMOV,CODFILIALTM,SERIE,CODFILIALSE;

  SELECT * FROM LFNOVODOCSP(:SERIE,:CODEMP,:CODFILIALSE) INTO DOC;

  INSERT INTO CPCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        CODEMPFR,CODFILIALFR,CODFOR,CODEMPSE,CODFILIALSE,SERIE,
                        CODEMPTM,CODFILIALTM,CODTIPOMOV,DOCCOMPRA,DTENTCOMPRA,DTEMITCOMPRA)
                 SELECT :CODEMP,:CODFILIAL,:CODCOMPRA,CODEMPPG,CODFILIALPG,CODPLANOPAG,
                        :CODEMP,:CODFILIALFR,:CODFOR,:CODEMP,:CODFILIALSE,:SERIE,
                        CODEMP,:CODFILIALTM,:CODTIPOMOV,:DOC,DTSAIDAVENDA,DTEMITVENDA FROM
                        VDVENDA WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND
                        CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA;
  IF (CITEM = 'S') THEN
    EXECUTE PROCEDURE CPGERAITENTRADASP(:CODEMP,:CODFILIALCP,:CODCOMPRA,:CODFILIALVD,:TIPOVENDA,:CODVENDA,NULL,NULL);
    
  UPDATE VDVENDA SET STATUSVENDA='DV' WHERE TIPOVENDA=:TIPOVENDA AND CODVENDA=:CODVENDA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD;
  
  SUSPEND;
END
^

/* Alter (CPGERAITENTRADASP) */
ALTER PROCEDURE CPGERAITENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR(1),
CODVENDA INTEGER,
CODITVENDA INTEGER,
QTDIT INTEGER)
 AS
declare variable coditcompra integer;
declare variable codfilialtm integer;
declare variable codtipomov integer;
declare variable codfilialfr integer;
declare variable codfor integer;
declare variable codnat char(4);
declare variable codfilialnt integer;
declare variable percicmsitcompra numeric(9,2);
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable codfilialle integer;
declare variable codlote varchar(20);
declare variable qtditvenda numeric(18,3);
declare variable qtddevitvenda numeric(18,3);
declare variable precoitvenda numeric(18,3);
declare variable percdescitvenda numeric(15,5);
declare variable vlrdescitvenda numeric(18,3);
declare variable percicmsitvenda numeric(9,2);
declare variable vlrbaseicmsitvenda numeric(18,3);
declare variable vlricmsitvenda numeric(18,3);
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(18,3);
declare variable vlripiitvenda numeric(18,3);
declare variable vlrliqitvenda numeric(18,3);
declare variable vlradicitvenda numeric(18,3);
declare variable vlrfreteitvenda numeric(18,3);
declare variable vlrisentasitvenda numeric(18,3);
declare variable vlroutrasitvenda numeric(18,3);
declare variable vlrproditvenda numeric(18,3);
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO CODFILIALNT;
  SELECT CODFOR,CODFILIALFR,CODTIPOMOV,CODFILIALTM FROM CPCOMPRA WHERE CODCOMPRA=:CODCOMPRA
    AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO
    :CODFOR,:CODFILIALFR,:CODTIPOMOV,:CODFILIALTM;
  FOR SELECT CODITVENDA,CODFILIALPD,CODPROD,CODFILIALLE,CODLOTE,
             QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,PERCICMSITVENDA,
             VLRBASEICMSITVENDA,VLRICMSITVENDA,PERCIPIITVENDA,VLRBASEIPIITVENDA,
             VLRIPIITVENDA,VLRLIQITVENDA,VLRADICITVENDA,VLRFRETEITVENDA,VLRISENTASITVENDA,
             VLROUTRASITVENDA,VLRPRODITVENDA,VLRPRODITVENDA FROM VDITVENDA WHERE
             CODEMP=:CODEMP AND CODFILIAL=:CODFILIALVD AND CODVENDA=:CODVENDA AND TIPOVENDA=:TIPOVENDA AND
             (:CODITVENDA IS NULL OR CODITVENDA=:CODITVENDA)
             ORDER BY CODITVENDA INTO CODITCOMPRA,CODFILIALPD,CODPROD,CODFILIALLE,CODLOTE,
             QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,PERCICMSITVENDA,
             VLRBASEICMSITVENDA,VLRICMSITVENDA,PERCIPIITVENDA,VLRBASEIPIITVENDA,
             VLRIPIITVENDA,VLRLIQITVENDA,VLRADICITVENDA,VLRFRETEITVENDA,VLRISENTASITVENDA,
             VLROUTRASITVENDA,VLRPRODITVENDA,VLRPRODITVENDA DO
  BEGIN
      IF (QTDIT IS NULL) THEN
        QTDIT = QTDITVENDA;
      SELECT SUM(QTDDEV) FROM CPCOMPRAVENDA WHERE TIPOVENDA=:TIPOVENDA
        AND CODVENDA=:CODVENDA AND CODITVENDA=:CODITVENDA
        AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL INTO QTDDEVITVENDA;
      IF (QTDIT > QTDITVENDA OR QTDIT+QTDDEVITVENDA > QTDITVENDA) THEN
        EXCEPTION VDVENDAEX01 'O ITEM: '||CAST(CODITVENDA AS CHAR(3))||' DO PEDIDO: '||
          CAST(CODVENDA AS CHAR(6))||' JA FOI DEVOLVIDO!';
        
      SELECT CODNAT FROM LFBUSCANATSP (:CODFILIAL,:CODEMP,:CODFILIALPD,:CODPROD,NULL,NULL,NULL,
                                   :CODEMP,:CODFILIALFR,:CODFOR,:CODFILIALTM,:CODTIPOMOV) INTO CODNAT;
      INSERT INTO CPITCOMPRA (CODEMP,CODFILIAL,CODCOMPRA,CODITCOMPRA,CODEMPPD,CODFILIALPD,CODPROD,
                              CODEMPLE,CODFILIALLE,CODLOTE,CODEMPNT,CODFILIALNT,CODNAT,QTDITCOMPRA,
                              PRECOITCOMPRA,PERCDESCITCOMPRA,VLRDESCITCOMPRA,PERCICMSITCOMPRA,
                              VLRBASEICMSITCOMPRA,VLRICMSITCOMPRA,PERCIPIITCOMPRA,VLRBASEIPIITCOMPRA,
                              VLRIPIITCOMPRA,VLRLIQITCOMPRA,VLRADICITCOMPRA,VLRFRETEITCOMPRA,
                              VLRISENTASITCOMPRA,VLROUTRASITCOMPRA,VLRPRODITCOMPRA,CUSTOITCOMPRA)
                       VALUES (:CODEMP,:CODFILIAL,:CODCOMPRA,:CODITCOMPRA,:CODEMP,:CODFILIALPD,:CODPROD,
                              :CODEMP,:CODFILIALLE,:CODLOTE,:CODEMP,:CODFILIALNT,:CODNAT,:QTDIT,
                              :PRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,
                              :VLRBASEICMSITVENDA,:VLRICMSITVENDA,:PERCIPIITVENDA,:VLRBASEIPIITVENDA,
                              :VLRIPIITVENDA,:VLRLIQITVENDA,:VLRADICITVENDA,:VLRFRETEITVENDA,
                              :VLRISENTASITVENDA,:VLROUTRASITVENDA,:VLRPRODITVENDA,:VLRPRODITVENDA);

      INSERT INTO CPCOMPRAVENDA(CODEMP,CODFILIAL,CODCOMPRA,CODITCOMPRA,CODEMPVD,
                                CODFILIALVD,TIPOVENDA,CODVENDA,CODITVENDA,QTDDEV)
                       VALUES (:CODEMP,:CODFILIAL,:CODCOMPRA,:CODITCOMPRA,:CODEMP,
                               :CODFILIALVD,:TIPOVENDA,:CODVENDA,:CODITCOMPRA,:QTDIT);
  END
  SUSPEND;
END
^

/* Alter (CPUPCOMPRAPEDSP) */
ALTER PROCEDURE CPUPCOMPRAPEDSP(CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRACP INTEGER,
CODITCOMPRACP INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER)
 AS
declare variable nroitensemitidos integer;
declare variable nroitens integer;
begin

    -- Atualizando os status de emissão do item do pedido de compra
    update cpitcompra set emititcompra='S' where coditcompra=:coditcomprapc
    and codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialcp;

    -- Contando o numero de ítens do pedido de compra que já estão emitidos
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc and emititcompra='S'
    into :nroitensemitidos;

    -- Contando o numero total de itens do pedido de compra
    select count(*) from cpitcompra where codcompra=:codcomprapc and codemp=:codemppc and codfilial=:codfilialpc
    into :nroitens;

    -- Se todos os ítens já foram emitidos
    if (:nroitensemitidos = nroitens) then
    begin
        update cpcompra set statuscompra='ET' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end
    -- Se apenas alguns itens foram emitidos
    else if (:nroitensemitidos > 0) then
    begin
        update cpcompra set statuscompra='EP' where codemp=:codemppc and codfilial=:codfilialpc and codcompra=:codcomprapc;
    end

    insert into cpcompraped (codemp, codfilial, codcompra, coditcompra,
    codemppc, codfilialpc, codcomprapc, coditcomprapc)
    values (
    :codempcp, :codfilialcp, :codcompracp, :coditcompracp,
    :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Alter (LFBUSCANATSP) */
ALTER PROCEDURE LFBUSCANATSP(FILIALATUAL INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CODNAT CHAR(4))
 AS
declare variable noest char(1);
declare variable cv char(1);
declare variable itmp integer;
begin

    -- Se o código do fornecedor é nulo subintende-se que deve realizar a busca para uma venda usando a
    -- UF do cliente
    if (codfor is null) then
    begin
        -- Verifica se a UF do emitente (Filial) é igual o estado do destinatário (Cliente) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, vdcliente c
        where f.codemp=:codemppd and f.codfilial=:filialatual and c.codcli=:codcli and c.codemp=:codempcl
        and c.codfilial=:codfilialcl and f.siglauf=coalesce(c.siglauf,c.ufcli)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para V (Venda)
        cv = 'V';
    end
    -- Se for uma busca de natureza de operação para compra (Busca pelo estado do fornecedor)
    else
    begin
        -- Verifica se a UF do emitente (Fornecedor) é igual o estado do destinatário (Filial) retorna >0 para dentro do estado
        select count(f.siglauf) from sgfilial f, cpforneced fo
        where f.codemp=:codemppd and f.codfilial=:filialatual and fo.codfor=:codfor and fo.codemp=:codempfr
        and fo.codfilial=:codfilialfr and F.siglauf=coalesce(fo.uffor, fo.siglauf)
        into itmp;
        -- Atualiza variável que indica se é compra ou venda para C (Compra)
        cv = 'C';
    end

    -- Verifica se transação é dentro ou fora do estado e atualiza a variável NOEST
    if (itmp > 0) then
    begin
        noest='S';
    end
    else
    begin
        noest='N';
    end

    -- Primeira busca por CFOP na regra de CFOP
    select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
    where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
    and c.codfisc = p.codfisc and c.codfilial = p.codfilialfc and c.codemp = p.codempfc
    and r.codregra = c.codregra and r.codfilial = c.codfilialra and r.codemp = c.codempra
    and r.codemptm=:codemppd and r.codfilialtm=:codfilialtm and r.codtipomov=:codtipomov
    and r.cvitrf=:cv and r.noufitrf=:noest
    into codnat;

    -- Se não localizou nenhum cfop na pesquisa anterior pega a primeira cfop
    -- para a classificação fiscal do produto na regra.
    if (codnat is null) then
    begin
        select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
        where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
        and c.codfisc=p.codfisc and c.codfilial=p.codfilialfc and c.codemp = p.codempfc and r.codregra = c.codregra
        and r.codfilial = c.codfilialra and r.codemp = c.codempra
        and r.cvitrf=:cv and r.noufitrf=:noest
        into codnat;
     end

     if(codnat is null) then
     begin
--        exception vdvendaex01 'teste cv: '|| :cv || ' noest: ' || :noest || ' codfor: ' || cast(codfor as char(5))
--        || ' tipomov: ' || cast(codtipomov as char(5)) || ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;

--        exception vdvendaex01 ' codemppd:' || coalesce(cast(codemppd as char(5)),'erro1') || ' codfilialpd:' || coalesce(cast(codfilialpd as char(5)),'erro2')
--        || ' codprod: ' || coalesce(cast(codprod as char(5)),'erro3') ;


     end


    suspend;
end
^

/* Alter (VDADICITEMPDVSP) */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod char(13);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;
  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITORCRECMERCSP) */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER)
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
begin
    
    -- Buscando informações do orçamento
    select codempcl, codfilialcl, codcli, codemppg, codfilialpg, codplanopag, codemptm, codfilialtm, codtipomov
    from vdorcamento
    where codemp=:codempoc and codfilial=:codfilialoc and codorc=:codorc and tipoorc='O'
    into :codempcl, :codfilialcl, :codcli, :codemppg, :codfilialpg, :codplanopag, :codemptm, :codfilialtm, :codtipomov;

    -- Sendo um orçamento para peças e mão-de-obra
    -- Deve gerar orçamento dos ítens de suplemento
    for select ir.codemppd, ir.codfilialpd, ir.codprodpd, ir.refprodpd, ir.coditrecmerc, ir.coditos, ir.qtditos
        from eqitrecmercitos ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :refprod, :coditrecmerc, :coditos, :qtditorc
        do
        begin

            if(:codprod <> :codprodant or :codprodant is null) then
            begin

                --Buscando código do item de orçamento
                select coalesce(max(coditorc)+1,1) from vditorcamento io
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.tipoorc='O'
                into :coditorc;

                -- Buscando preço de venda

                select preco from vdbuscaprecosp(:codprod,:codcli,:codempcl,:codfilialcl,
                :codplanopag,:codemppg,:codfilialpg,:codtipomov,:codemptm,:codfilialtm,:codemp,:codfilial)
                into :precoitorc;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox, :refprod;

                -- Inserir itens
                insert into vditorcamento (
                codemp, codfilial, codorc, tipoorc, coditorc,
                codemppd, codfilialpd, codprod, refprod,
                qtditorc, precoitorc, codempax, codfilialax, codalmox)
                values (:codempoc, :codfilialoc, :codorc, 'O', :coditorc,
                :codemppd, :codfilialpd, :codprod, :refprod,
                :qtditorc, :precoitorc, :codempax, :codfilialax, :codalmox) ;

                -- Inserindo vínculo entre item de orçamento e ordem de serviço

                insert into eqitrecmercitositorc(codemp, codfilial, ticket, coditrecmerc, coditos, codempoc, codfilialoc, codorc, coditorc, tipoorc)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:coditos, :codempoc,:codfilialoc,:codorc,:coditorc,'O');

                codprodant = codprod;

            end
        end

        -- Atualizando o status da ordem de serviço
        update eqrecmerc rm set rm.status = 'EO'
        where rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket;

end
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA FLOAT,
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(9,2);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(9,2);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,2);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod char(13);
declare variable stipoprod char(1);
declare variable percicmsst numeric(9,2);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

-- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,aliqipifisc,aliqfiscintra,
        tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,:ICODTIPOMOV, 'VD',:scodnat)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
        select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
        into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

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

ALTER TABLE SGPREFERE1 ALTER COLUMN USANOMEVENDORC POSITION 120;

ALTER TABLE SGPREFERE1 ALTER COLUMN SISCONTABIL POSITION 121;

ALTER TABLE SGPREFERE1 ALTER COLUMN ATBANCOIMPBOL POSITION 122;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCODBAR POSITION 123;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICORCOBSPED POSITION 124;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMENSORC POSITION 125;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMENSORC POSITION 126;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSORC POSITION 127;

ALTER TABLE SGPREFERE1 ALTER COLUMN CUSTOCOMPRA POSITION 128;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPCP POSITION 129;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABTRANSPORC POSITION 130;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABSOLCP POSITION 131;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICFRETEBASEICM POSITION 132;

ALTER TABLE SGPREFERE1 ALTER COLUMN PRECOCPREL POSITION 133;

ALTER TABLE SGPREFERE1 ALTER COLUMN DESCORC POSITION 134;

ALTER TABLE SGPREFERE1 ALTER COLUMN MULTICOMIS POSITION 135;

ALTER TABLE SGPREFERE1 ALTER COLUMN USUATIVCLI POSITION 136;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTREC POSITION 137;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTREC POSITION 138;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTREC POSITION 139;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPHISTPAG POSITION 140;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALHISTPAG POSITION 141;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODHISTPAG POSITION 142;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPTC POSITION 143;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALTC POSITION 144;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOCLI POSITION 145;

ALTER TABLE SGPREFERE1 ALTER COLUMN ESTITRECALTDTVENC POSITION 146;

ALTER TABLE SGPREFERE1 ALTER COLUMN LCREDGLOBAL POSITION 147;

ALTER TABLE SGPREFERE1 ALTER COLUMN VDMANUTCOMOBRIG POSITION 148;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSPED POSITION 149;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCLASSPED POSITION 150;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGECLI POSITION 151;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGEFOR POSITION 152;

ALTER TABLE SGPREFERE1 ALTER COLUMN USAIBGETRANSP POSITION 153;

ALTER TABLE SGPREFERE1 ALTER COLUMN SOMAVOLUMES POSITION 154;

ALTER TABLE SGPREFERE1 ALTER COLUMN BUSCACEP POSITION 155;

ALTER TABLE SGPREFERE1 ALTER COLUMN URLWSCEP POSITION 156;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSCP POSITION 157;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS01CP POSITION 158;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS02CP POSITION 159;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS03CP POSITION 160;

ALTER TABLE SGPREFERE1 ALTER COLUMN LABELOBS04CP POSITION 161;

ALTER TABLE SGPREFERE1 ALTER COLUMN CONSISTEIEPF POSITION 162;

ALTER TABLE SGPREFERE1 ALTER COLUMN CREDICMSSIMPLES POSITION 163;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPMS POSITION 164;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALMS POSITION 165;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODMENSICMSSIMPLES POSITION 166;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACOMISVENDAORC POSITION 167;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERACODUNIF POSITION 168;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODTIPOMOV9 POSITION 169;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALT9 POSITION 170;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPT9 POSITION 171;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJP POSITION 172;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJP POSITION 173;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJP POSITION 174;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPJR POSITION 175;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALJR POSITION 176;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANJR POSITION 177;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDR POSITION 178;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDR POSITION 179;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDR POSITION 180;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODEMPDC POSITION 181;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODFILIALDC POSITION 182;

ALTER TABLE SGPREFERE1 ALTER COLUMN CODPLANDC POSITION 183;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERAPAGEMIS POSITION 184;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCAFINCONTR POSITION 185;

ALTER TABLE SGPREFERE1 ALTER COLUMN LANCARMACONTR POSITION 186;

ALTER TABLE SGPREFERE1 ALTER COLUMN VISUALIZALUCR POSITION 187;

ALTER TABLE SGPREFERE1 ALTER COLUMN CLASSNFE POSITION 188;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFE POSITION 189;

ALTER TABLE SGPREFERE1 ALTER COLUMN DIRNFELIN POSITION 190;

ALTER TABLE SGPREFERE1 ALTER COLUMN FORMATODANFE POSITION 191;

ALTER TABLE SGPREFERE1 ALTER COLUMN AMBIENTENFE POSITION 192;

ALTER TABLE SGPREFERE1 ALTER COLUMN PROCEMINFE POSITION 193;

ALTER TABLE SGPREFERE1 ALTER COLUMN VERPROCNFE POSITION 194;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFCPDEVOLUCAO POSITION 195;

ALTER TABLE SGPREFERE1 ALTER COLUMN INFVDREMESSA POSITION 196;

ALTER TABLE SGPREFERE1 ALTER COLUMN GERARECEMIS POSITION 197;

ALTER TABLE SGPREFERE1 ALTER COLUMN RETENSAOIMP POSITION 198;

ALTER TABLE SGPREFERE1 ALTER COLUMN TIPOCUSTOLUC POSITION 199;

ALTER TABLE SGPREFERE1 ALTER COLUMN TABIMPORTCP POSITION 200;

ALTER TABLE SGPREFERE1 ALTER COLUMN HABVLRTOTITORC POSITION 201;

ALTER TABLE SGPREFERE1 ALTER COLUMN USABUSCAGENPRODCP POSITION 202;

ALTER TABLE SGPREFERE1 ALTER COLUMN ADICOBSORCPED POSITION 203;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTINS POSITION 204;

ALTER TABLE SGPREFERE1 ALTER COLUMN HINS POSITION 205;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUINS POSITION 206;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTALT POSITION 207;

ALTER TABLE SGPREFERE1 ALTER COLUMN HALT POSITION 208;

ALTER TABLE SGPREFERE1 ALTER COLUMN IDUSUALT POSITION 209;

ALTER TABLE SGPREFERE1 ALTER COLUMN DTVENCTONFE POSITION 210;

ALTER TABLE SGPREFERE1 ALTER COLUMN KEYLICNFE POSITION 211;

/* Create(Add) Crant */
GRANT EXECUTE ON PROCEDURE VDADICITORCRECMERCSP TO ADM;

GRANT EXECUTE ON PROCEDURE VDBUSCAPRECOSP TO PROCEDURE CPADICITORCRECMERCSP;

GRANT EXECUTE ON PROCEDURE VDBUSCAPRECOSP TO PROCEDURE VDADICITORCRECMERCSP;

GRANT INSERT ON EQITRECMERCITOSITORC TO PROCEDURE CPADICITORCRECMERCSP;

GRANT INSERT ON EQITRECMERCITOSITORC TO PROCEDURE VDADICITORCRECMERCSP;

GRANT INSERT ON VDITORCAMENTO TO PROCEDURE CPADICITORCRECMERCSP;

GRANT INSERT, SELECT ON VDITORCAMENTO TO PROCEDURE VDADICITORCRECMERCSP;

GRANT SELECT ON EQITRECMERC TO PROCEDURE CPADICITORCRECMERCSP;

GRANT SELECT ON EQITRECMERCITOS TO PROCEDURE VDADICITORCRECMERCSP;

GRANT SELECT ON EQPRODUTO TO PROCEDURE CPADICITORCRECMERCSP;

GRANT SELECT ON VDORCAMENTO TO PROCEDURE CPADICITORCRECMERCSP;

GRANT SELECT ON VDORCAMENTO TO PROCEDURE VDADICITORCRECMERCSP;



