/* Server version: LI-V6.3.3.4870 Firebird 1.5 
   SQLDialect: 3. ODS: 10.1. Forced writes: On. Sweep inteval: 20000.
   Page size: 4096. Cache pages: 75 (300 Kb). Read-only: False. */
SET SQL DIALECT 3;

SET AUTODDL ON;

/* Alter Procedure... */
/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.2.8 (13/07/2010)';
    suspend;
end
^

/* Alter (VDRETULTVDCLIPROD) */
ALTER PROCEDURE VDRETULTVDCLIPROD(ICODEMP INTEGER,
ICODCLI INTEGER,
ICODFILIALVD SMALLINT,
ICODVEND INTEGER,
DTINI DATE,
DTFIM DATE,
CODEMPTIPOCL INTEGER,
CODFILIALTIPOCL SMALLINT,
CODTIPOCLI INTEGER)
 RETURNS(RAZCLI_RET CHAR(50),
CODCLI_RET INTEGER,
DESCPROD_RET CHAR(50),
CODPROD_RET INTEGER,
DTEMITVENDA_RET DATE,
DOCVENDA_RET INTEGER,
SERIE_RET CHAR(4),
PRECOVENDA_RET NUMERIC(15,4))
 AS
declare variable icodfilial smallint;
declare variable icodprod integer;
begin

    select icodfilial from sgretfilial(:ICODEMP,'VDVENDA') into :ICODFILIAL;

    for select v.codcli,iv.codprod
        from vdvenda v, vdcliente cl, vditvenda iv
        where
            iv.codemp=v.codemp and iv.codfilial=v.codfilial
            and iv.tipovenda=v.TIPOVENDA and iv.codvenda=v.codvenda
            and v.codemp=:ICODEMP and v.codfilial=:ICODFILIAL
            and (v.codcli=:ICODCLI or :ICODCLI is null)
            and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM
            and cl.codemp=v.codempcl and cl.codfilial=v.codfilialcl and cl.codcli=v.codcli
            and (cl.codtipocli=:codtipocli or :codtipocli is null)
        group by v.codcli,iv.codprod into :ICODCLI,:ICODPROD
    do
    begin
        select first 1 c.razcli, c.codcli, p.descprod, iv.codprod, v.dtemitvenda, v.docvenda, v.serie,
            (iv.vlrliqitvenda/(case when iv.qtditvenda=0 then 1 else iv.qtditvenda end)) precovenda
        from vdcliente c, vdvenda v, vditvenda iv, eqproduto p
        where
            c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli
            and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and iv.codemp=v.codemp
            and iv.codfilial=v.codfilial and iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda
            and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd and p.codprod=iv.codprod
            and v.codempvd=:ICODEMP and v.codfilialvd=:ICODFILIALVD and (v.codvend=:ICODVEND or :ICODVEND is null )
            and v.dtemitvenda between :DTINI and :DTFIM and c.codcli=:ICODCLI and p.codprod=:ICODPROD
            order by v.dtemitvenda desc
            into :RAZCLI_RET, :CODCLI_RET, :DESCPROD_RET, :CODPROD_RET, :DTEMITVENDA_RET, :DOCVENDA_RET, :SERIE_RET,
                 :PRECOVENDA_RET;
            suspend;
    end
end
^

