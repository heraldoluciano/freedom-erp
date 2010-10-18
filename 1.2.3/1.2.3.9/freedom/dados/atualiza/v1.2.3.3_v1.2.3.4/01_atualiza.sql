/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

/* Alter Field (Length): from 50 to 100... */
UPDATE RDB$FIELDS SET RDB$FIELD_LENGTH=100, RDB$CHARACTER_LENGTH=100
  WHERE RDB$FIELD_NAME=(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
  WHERE RDB$RELATION_NAME='CRCHAMADO' AND  RDB$FIELD_NAME='DESCCHAMADO');

CREATE DOMAIN IBCXXX CHAR(2) DEFAULT 'PE' NOT NULL;

UPDATE RDB$RELATION_FIELDS SET RDB$DEFAULT_SOURCE=
(SELECT RDB$DEFAULT_SOURCE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX'),
RDB$DEFAULT_VALUE=
(SELECT RDB$DEFAULT_VALUE FROM RDB$FIELDS where RDB$FIELD_NAME='IBCXXX')
WHERE RDB$FIELD_NAME='STATUS' AND RDB$RELATION_NAME='CRCHAMADO';

DROP DOMAIN IBCXXX;

UPDATE RDB$FIELDS SET RDB$DEFAULT_VALUE = NULL,
RDB$DEFAULT_SOURCE = '' WHERE RDB$FIELD_NAME =
(SELECT RDB$FIELD_SOURCE FROM RDB$RELATION_FIELDS
WHERE RDB$FIELD_NAME = 'STATUS' AND RDB$RELATION_NAME = 'CRCHAMADO');

Update Rdb$Relation_Fields set Rdb$Description =
'''PE'' - Item pendente / previsto / Orçado
''EC'' - Encaminhado / Chamado aberto / RMA aberta
''EA'' - Em andamento
''CO'' - Item Concluído;'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='STATUSITOS';


ALTER TABLE EQITRECMERCITOS ADD GERACHAMADO CHAR(1) DEFAULT 'N';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar chamado para esse item.'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='GERACHAMADO';

ALTER TABLE EQITRECMERCITOS ADD GERANOVO CHAR(1);

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o item de OS é um produto novo para substituição, 
ao com defeito;'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='GERANOVO';

/* Create Index... */
CREATE INDEX CRCHAMADOIDXEQRECMERC ON CRCHAMADO(CODEMPOS,CODFILIALOS,TICKET);


/* Alter Procedure... */
/* Alter (EQGERARMAOSSP) */
SET TERM ^ ;

ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod char(13);
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialtm1 smallint;
declare variable codfilialtm smallint;
declare variable codemppd integer;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable qtdaprov numeric(15,5) = 0;
begin


    -- buscando centro de custo do usuário atual
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando filial da rma
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQRMA')
    into :codfilialrma;

    -- Buscando filial do lote
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQLOTE')
    into :codfilialle;

    -- Buscando filial do tipo de movimento
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQTIPOMOV')
    into :codfilialtm;

    -- buscando tipo de movimento para RMA
    select codfilialt8,codtipomov8 from sgprefere1
    where codemp=:codemprm and codfilial=:codfilialrm
    into :codfilialtm1,:codtipomov1;

    -- Buscado a situação pardrão para RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:codemprm and
    codfilial=(select icodfilial from sgretfilial(:codemprm,'SGPREFERE5'))
    into :statusrmager;

    if(:statusrmager is null) then
    begin
       statusrmager = 'PE';
    end

    -- Carregando quantidade aprovada...
    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    -- Buscando código novo código de RMA.
    select coalesce((max(codrma)+1),1) from
    eqrma where codemp=:codemprm and codfilial=:codfilialrma
    into :codrma;

    -- Inserindo nova RMA
    insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempos,codfilialos,ticket,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket and os.coditrecmerc=:coditrecmerc
    and os.gerarma='S'
    into :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
    do

    begin

       select coalesce((max(coditrma)+1),1) from eqitrma
       where codemp=:codemprm and codfilial=:codfilialrma and codrma=:codrma
       into :coditrma;

       if(:statusrmager='AF') then
       begin
         STATUSAPROVRMAGER = 'AT';
         QTDAPROV = :qtd;
       end

       insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE'
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.4 (23/08/2010)';
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
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

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

/* Create Trigger... */
CREATE TRIGGER EQITRECMERCITOSTGAI FOR EQITRECMERCITOS
ACTIVE AFTER INSERT POSITION 0 
AS
declare variable num_os_tot integer;
declare variable num_os_status integer;
declare variable status_atual_os char(2);
declare variable novo_status_os char(2);
begin

    if(new.gerarma = 'S') then

    begin
        
        -- Buscando status geral da OS
        select rm.status from eqrecmerc rm
        where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
        into status_atual_os;

        -- Verifica se deve atualizar o status geral da OS
        if(status_atual_os in('PE','AN','EA','EC')) then
        begin

            -- carregando quantidade de itens para a OS
            select count(*) from eqitrecmercitos os
            where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
            into num_os_tot;

            -- carregando quantidade de ítens na situação atual
            select count(*) from eqitrecmercitos os
            where os.codemp = new.codemp and os.codfilial=new.codfilial and os.ticket=new.ticket
            and os.statusitos=new.statusitos
            into num_os_status;

            -- Atualização do status da ordem de serviço
    
            if(num_os_status = num_os_tot) then
            begin
                if(new.statusitos = 'AN') then
                begin
                    novo_status_os = 'AN';

                    update eqrecmerc rm set rm.status=:novo_status_os
                    where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;

                end
/*                else
                begin
                    novo_status_os = new.statusitos;
                end*/
    
            end
        end
    end
end
^

CREATE TRIGGER EQITRECMERCITOSTGBI FOR EQITRECMERCITOS
ACTIVE BEFORE INSERT POSITION 0 
AS
declare variable rmaprod char(1);
declare variable tipoprod char(1);

begin
    -- Buscando informação do produto
    select pd.rmaprod, pd.tipoprod from eqproduto pd
    where pd.codemp=new.codemppd and pd.codfilial=new.codfilialpd and pd.codprod=new.codprodpd
    into :rmaprod, :tipoprod;

    if('S' = :rmaprod) then
    begin
        
        new.gerarma = 'S';
        new.statusitos = 'AN';

    end

    -- Se o produto é de Comercio, Equipamento ou Fabricação deve sinalizar
    -- o Ítem de OS como um produto novo, para substituição.

    if(:tipoprod in ('P','E','F')) then
    begin
        new.gerarma = 'N';
        new.geranovo = 'S';
        new.statusitos = 'AN';
    end


end
^


/* Alter Procedure... */
/* Alter (EQGERARMAOSSP) */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
declare variable coditos smallint;
declare variable idusu1 char(8);
declare variable codfilialrma smallint;
declare variable codfilialpd smallint;
declare variable coditrma integer;
declare variable refprod char(13);
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialtm1 smallint;
declare variable codfilialtm smallint;
declare variable codemppd integer;
declare variable codprod integer;
declare variable codfilialle smallint;
declare variable custompmit numeric(15,5);
declare variable codlote varchar(20);
declare variable codalmox integer;
declare variable qtd numeric(15,2);
declare variable codfilialax smallint;
declare variable codfilialusu1 smallint;
declare variable anoccusu1 smallint;
declare variable statusaprovrmager char(2) = 'PE';
declare variable statusrmager char(2);
declare variable qtdaprov numeric(15,5) = 0;
begin


    -- buscando centro de custo do usuário atual
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando filial da rma
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQRMA')
    into :codfilialrma;

    -- Buscando filial do lote
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQLOTE')
    into :codfilialle;

    -- Buscando filial do tipo de movimento
    SELECT ICODFILIAL FROM sgretfilial(:CODEMPRM,'EQTIPOMOV')
    into :codfilialtm;

    -- buscando tipo de movimento para RMA
    select codfilialt8,codtipomov8 from sgprefere1
    where codemp=:codemprm and codfilial=:codfilialrm
    into :codfilialtm1,:codtipomov1;

    -- Buscado a situação pardrão para RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:codemprm and
    codfilial=(select icodfilial from sgretfilial(:codemprm,'SGPREFERE5'))
    into :statusrmager;

    if(:statusrmager is null) then
    begin
       statusrmager = 'PE';
    end

    -- Carregando quantidade aprovada...
    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    -- Buscando código novo código de RMA.
    select coalesce((max(codrma)+1),1) from
    eqrma where codemp=:codemprm and codfilial=:codfilialrma
    into :codrma;

    -- Inserindo nova RMA
    insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempos,codfilialos,ticket,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket and os.coditrecmerc=:coditrecmerc
    and os.gerarma='S'
    into :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
    do

    begin

       select coalesce((max(coditrma)+1),1) from eqitrma
       where codemp=:codemprm and codfilial=:codfilialrma and codrma=:codrma
       into :coditrma;

       if(:statusrmager='AF') then
       begin
         STATUSAPROVRMAGER = 'AT';
         QTDAPROV = :qtd;
       end

       insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE'
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* Alter (SGRETVERSAO) */
ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.4 (23/08/2010)';
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
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket and
        -- Filtrando componentes e serviços
        (
           (ir.gerarma=:componentes and ir.gerarma='S') or
           (ir.gerachamado=:servicos and ir.gerachamado='S') or
           (ir.geranovo=:novos and ir.geranovo='S')
        )

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

SET TERM ; ^

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN TICKET POSITION 3;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODITRECMERC POSITION 4;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODITOS POSITION 5;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMPPD POSITION 6;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIALPD POSITION 7;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODPRODPD POSITION 8;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN REFPRODPD POSITION 9;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODEMPNS POSITION 10;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN CODFILIALNS POSITION 11;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN NUMSERIE POSITION 12;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN QTDITOS POSITION 13;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN OBSITOS POSITION 14;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN STATUSITOS POSITION 15;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN GERARMA POSITION 16;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN GERACHAMADO POSITION 17;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN GERANOVO POSITION 18;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN DTINS POSITION 19;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN HINS POSITION 20;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN IDUSUINS POSITION 21;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN DTALT POSITION 22;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN HALT POSITION 23;

ALTER TABLE EQITRECMERCITOS ALTER COLUMN IDUSUALT POSITION 24;


