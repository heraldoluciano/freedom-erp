/* Server version: LI-V6.3.3.4870 Firebird 1.5 
   SQLDialect: 3. ODS: 10.1. Forced writes: On. Sweep inteval: 20000.
   Page size: 4096. Cache pages: 75 (300 Kb). Read-only: False. */
SET SQL DIALECT 3;

SET AUTODDL ON;

UPDATE RDB$RELATION_FIELDS SET RDB$DESCRIPTION = 
'Status do processo de recebimento.
PE - Pendente;
AN - Em analise;
EA - Em andamento;
FN - Finalizado;'
WHERE RDB$RELATION_NAME = 'EQITRECMERC' AND RDB$FIELD_NAME = 'STATUSITRECMERC';

UPDATE RDB$RELATION_FIELDS SET RDB$DESCRIPTION = 
'''PE'' - Item pendente / previsto / Orçado
''EC'' - Encaminhado / Chamado aberto
''EA'' - Em andamento
''CO'' - Item Concluído;'
WHERE RDB$RELATION_NAME = 'EQITRECMERCITOS' AND RDB$FIELD_NAME = 'STATUSITOS';

/* Alter Procedure... */
/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.3.1 (22/07/2010)';
    suspend;
end
^

/* Create trigger... */
CREATE TRIGGER CRCHAMADOTGAD FOR CRCHAMADO
ACTIVE AFTER DELETE POSITION 0 
as
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc smallint;
declare variable coditos smallint;

begin
     -- Alterando status do item de OS caso exista;

     if(old.coditos is not null) then
     begin
        update eqitrecmercitos os set os.statusitos='PE' where
        os.codemp=old.codempos and os.codfilial=old.codfilialos and os.ticket=old.ticket and
        os.coditrecmerc=old.coditrecmerc and os.coditos=old.coditos;
     end

end
^

CREATE TRIGGER CRCHAMADOTGAI FOR CRCHAMADO
ACTIVE AFTER INSERT POSITION 0 
AS
begin
    -- Atualização do status da ordem de serviço quando integrada....
    if(new.ticket is not null) then
    begin
        update eqitrecmercitos os set os.statusitos='EC' where
        os.codemp=new.codempos and os.codfilial=new.codfilialos and os.ticket=new.ticket and
        os.coditrecmerc=new.coditrecmerc and os.coditos=new.coditos;
    end
end
^

CREATE TRIGGER CRCHAMADOTGAU FOR CRCHAMADO
ACTIVE AFTER UPDATE POSITION 0 
AS
begin
    -- Atualização do status da ordem de serviço quando integrada....
    if(new.ticket is not null and new.status!=old.status) then
    begin
        -- Se o status do chamado for alterado para EA (Em andamento)
        if(new.status in ('EA','AN')) then
        begin
            update eqitrecmercitos os set os.statusitos=new.status where
            os.codemp=new.codempos and os.codfilial=new.codfilialos and os.ticket=new.ticket and
            os.coditrecmerc=new.coditrecmerc and os.coditos=new.coditos;
        end
        -- Se o chamado for concluído atualizar o status do item de OS.
        else if(new.status='CO') then
        begin
            update eqitrecmercitos os set os.statusitos='CO' where
            os.codemp=new.codempos and os.codfilial=new.codfilialos and os.ticket=new.ticket and
            os.coditrecmerc=new.coditrecmerc and os.coditos=new.coditos;
        end

    end
end
^

CREATE TRIGGER EQITRECMERCITOSTGAU FOR EQITRECMERCITOS
ACTIVE AFTER UPDATE POSITION 0 
as
declare variable num_os_tot integer;
declare variable num_os_status integer;
declare variable status_atual_os char(2);
declare variable novo_status_os char(2);


begin

    -- Buscando status geral da OS
    select rm.status from eqrecmerc rm
    where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
    into status_atual_os;

--    execute procedure sgdebugsp('eqitrecmercitostgau','statusatual:' || status_atual_os);

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

--        execute procedure sgdebugsp('eqitrecmercitostgau','num_os_status' || num_os_status);

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
    end
end
^


