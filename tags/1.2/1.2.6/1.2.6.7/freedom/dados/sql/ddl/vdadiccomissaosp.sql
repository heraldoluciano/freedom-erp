SET TERM ^ ;

CREATE OR ALTER PROCEDURE VDADICCOMISSAOSP (
    icodemp integer,
    scodfilial smallint,
    icodrec integer,
    inparcitrec integer,
    nvlrvendacomi numeric(15,5),
    nvlrcomi numeric(15,5),
    ddatacomi date,
    ddtcompcomi date,
    ddtvenccomi date,
    ctipocomi char(1),
    codempvd integer,
    codfilialvd smallint,
    codvend integer)
as
declare variable scodfilialcs smallint;
declare variable icodcomi integer;
declare variable cstatuscomi char(2);
begin

    -- Se o valor for nulo ou 0 deve deletar a comissão já gerada
    if ( (nvlrcomi is null) or  (nvlrcomi=0) ) then
    begin

        delete from vdcomissao co
        where co.codemprc=:icodemp and co.codfilialrc=:scodfilial and co.codrec=:icodrec and co.nparcitrec=:inparcitrec and
        co.tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend;

    end
    -- Caso seja um estorno de comissão
    else if (nvlrcomi<0) then
    begin

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial(:icodemp,'VDCOMISSAO') into :scodfilialcs;

        -- Buscando novo numero para
        select max(codcomi) from vdcomissao where codemp=:icodemp and codfilialvd = :scodfilialcs into icodcomi;

        if (:icodcomi is null) then
            icodcomi = 1;
        else
            icodcomi = icodcomi + 1;

        -- Inserindo na tabela de comissões
        insert into vdcomissao (
            codemp, codfilial, codcomi, codempRc, codfilialrc, codrec, nparcitrec, vlrvendacomi, vlrcomi, datacomi,
            dtcompcomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrvendacomi, :nvlrcomi, :ddatacomi,
            :ddtcompcomi, :ddtvenccomi, 'CE', :ctipocomi, :codempvd, :codfilialvd,:codvend
            );

        -- Transforma o valor da comissão em positivo e programa para o proximo pagto.
        nvlrcomi = nvlrcomi * -1;

        icodcomi = icodcomi + 1;

        insert into vdcomissao (
            codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtcompcomi,  dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend )
        values (
            :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
            :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtcompcomi, :ddtvenccomi, 'C1', :ctipocomi, :codempvd,:codfilialvd,:codvend
            );

    end
    else
    begin

        if (ctipocomi='F') then
            cstatuscomi = 'C2';
        else
            cstatuscomi = 'C1';

        -- Buscando a filial da tabela de comissões
        select icodfilial from sgretfilial( :icodemp, 'VDCOMISSAO') into :scodfilialcs;

        -- Buscando o código da comissão já existente
        select codcomi from vdcomissao
        where codemp=:icodemp and codfilialrc=:scodfilial and codrec=:icodrec and nparcitrec=:inparcitrec and
        tipocomi=:ctipocomi and codempvd=:codempvd and codfilialvd=:codfilialvd and codvend=:codvend
        into :icodcomi;

        -- Caso já não exista a comissão deve inserir
        if (icodcomi is null) then
        begin
            --Buscando um novo código
            select max(codcomi) from vdcomissao where codemp=:icodemp and codfilial = :scodfilialcs into icodcomi;

            if (:icodcomi is null) then
                icodcomi = 1;
            else
                icodcomi = icodcomi + 1;

            -- Inserindo na tabela de comissões
            insert into vdcomissao( codemp, codfilial, codcomi, codemprc, codfilialrc, codrec, nparcitrec,
            vlrvendacomi, vlrcomi, datacomi, dtcompcomi, dtvenccomi, statuscomi, tipocomi, codempvd, codfilialvd, codvend)
            values (
                :icodemp, :scodfilialcs, :icodcomi, :icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtcompcomi, :ddtvenccomi, :cstatuscomi, :ctipocomi, :codempvd, :codfilialvd, :codvend
            );

        end
        -- Se encontrou a comissão atualiza
        else
        begin

            update vdcomissao set vlrvendacomi=:nvlrvendacomi, vlrcomi=:nvlrcomi, datacomi=:ddatacomi,
            dtvenccomi=:ddtvenccomi, statuscomi=:cstatuscomi
            where codemp=:icodemp and codfilial=:scodfilialcs and codcomi=:icodcomi and codempvd=:codempvd and
            codfilialvd=:codfilialvd and codvend=:codvend and statuscomi!='CP' ;

        end

    end
    suspend;
end^

SET TERM ; ^

GRANT SELECT,INSERT,DELETE,UPDATE ON VDCOMISSAO TO PROCEDURE VDADICCOMISSAOSP;

GRANT EXECUTE ON PROCEDURE SGRETFILIAL TO PROCEDURE VDADICCOMISSAOSP;

GRANT EXECUTE ON PROCEDURE VDADICCOMISSAOSP TO PROCEDURE VDESTORNACOMISSAOSP;
GRANT EXECUTE ON PROCEDURE VDADICCOMISSAOSP TO PROCEDURE VDGERACOMISSAOSP;
GRANT EXECUTE ON PROCEDURE VDADICCOMISSAOSP TO ADM;

commit work;

