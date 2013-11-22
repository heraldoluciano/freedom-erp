/* Server version: LI-V6.3.2.26540 Firebird 2.5 
   SQLDialect: 3. ODS: 11.2. Forced writes: On. Sweep inteval: 20000.
   Page size: 4096. Cache pages: 2048 (8192 Kb). Read-only: False. */
SET NAMES ISO8859_1;

SET SQL DIALECT 3;

CONNECT '/opt/firebird/dados/desenv/freedom251.2.6.5.fdb' USER 'SYSDBA' PASSWORD '123654';

SET AUTODDL ON;

/* Alter Procedure... */
/* empty dependent procedure body */
/* Clear: ATRESUMOATENDOSP02 for: ATRESUMOATENDOSP01 */
SET TERM ^ ;

ALTER PROCEDURE ATRESUMOATENDOSP02(CODEMPCLP INTEGER,
CODFILIALCLP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
RAZCLI VARCHAR(60),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODITCONTR INTEGER,
CODCONTR INTEGER,
DESCCONTR VARCHAR(100),
VLRHORA DECIMAL(15,5),
VLRHORAEXCED DECIMAL(15,5),
QTDCONTR DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
VLRCOB DECIMAL(15,5),
VLRCOBEXCED DECIMAL(15,5),
VLRCOBTOT DECIMAL(15,5),
MES SMALLINT,
ANO SMALLINT,
QTDHORAS DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
ACUMULO SMALLINT,
CDATAINI DATE)
 AS
 BEGIN SUSPEND; END
^

/* Alter (ATRESUMOATENDOSP01) */
ALTER PROCEDURE ATRESUMOATENDOSP01(CODEMPP INTEGER,
CODFILIALP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(ANO SMALLINT,
MES SMALLINT,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
QTDCONTR DECIMAL(15,5),
DTINICIO DATE,
VALOR DECIMAL(15,5),
VALOREXCEDENTE DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
QTDHORAS DECIMAL(15,2),
MESCOB INTEGER,
SALDOMESCOB DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
SALDOPERIODO DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
EXCEDENTEPERIODO DECIMAL(15,5),
EXCEDENTECOB DECIMAL(15,5),
VALOREXCEDENTECOB DECIMAL(15,5),
VALORTOTALCOB DECIMAL(15,5),
VALORCONTR DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
TOTFRANQUIAMES DECIMAL(15,5))
 AS
begin
  saldoperiodo = 0;
 -- saldoperiodo2 = 0;
  saldomescob = 0;
  excedentemescob = 0;
  excedenteperiodo = 0;
--  excedenteperiodo2 = 0;
  excedentecob = 0;
  valorexcedentecob = 0;
  valortotalcob = 0;
  -- MES COBRANÇA
  mescob = extract(month from :dtfimp);
  -- DATA DO MÊS ANTERIOR A COBRANÇA
  --dtant = addmonth(:dtfimp, -1);
  -- MES ANTERIOR A COBRANÇA
 -- mesant = extract(month from :dtant);


  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
    , avg((select avg(ic.vlritcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valor
   , avg((select avg(ic.vlritcontrexced) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  valorexcedente
   , avg((select sum(qtditcontr) from vditcontrato ic
     where  ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr
     and coalesce(ic.franquiaitcontr,'N')='S' ) )  qtditcontr
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes

   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr, :qtdcontr, :dtinicio, :valor
   , :valorexcedente, :qtditcontr, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       if (mes=mescob) then
          valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras > :qtditcontr) and (mes = mescob) ) then
          excedentemescob = excedentemes;

       if (mes=mescob) then
       begin
          --- SALDO DO MÊS DE COBRANÇA
          saldomescob = saldomescob + saldomes;
       end
       else
       begin
         -- SALDO DO PERÍODO
         saldoperiodo = saldoperiodo + saldomes;

         -- EXCEDENTE DO PERÍODO
          excedenteperiodo = excedenteperiodo + excedentemes;

       end 

   end

   -- EXCEDENTE COBRADO
   if( :excedenteperiodo - :saldoperiodo > 0) then
       excedentecob =  excedenteperiodo - saldoperiodo;
   else
       excedentecob = 0;

   if (excedentecob>0) then
      saldoperiodo = 0;

   saldoperiodo = saldoperiodo + saldomescob;
   excedentecob = excedentemescob;

   --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

   -- VALOR A COBRAR EXCEDENTE
   valorexcedentecob = excedentecob * valorexcedente;
        
   -- VALOR TOTAL A COBRAR
   valortotalcob = (valorcontr) + (excedentecob * valorexcedente);

 -- saldoperiodo2 = 0;
  excedentemescob = 0;
  --excedenteperiodo2 = 0;

  for select extract(year from a.dataatendo) ano
   , extract( month from a.dataatendo) mes
   , ct.codempcl, ct.codfilialcl, ct.codcli
   , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr
   , a.qtdcontr, a.dtinicio
   , cast(sum(a.totalcobcli) as decimal(15,2)) qtdhoras
    from vdcontrato ct
    left outer join atatendimentovw02 a on
    a.codempcl=ct.codempcl and a.codfilialcl=ct.codfilialcl and a.codcli=ct.codcli
    and a.codempct=ct.codemp and a.codfilialct=ct.codfilial and a.codcontr=ct.codcontr
    and ( :coditcontrp=0 or a.coditcontr=:coditcontrp ) 
    and a.dataatendo between :dtinip  and
    :dtfimp and a.mrelcobespec='S'
    where
     ct.codempcl=:codempp and ct.codfilialcl=:codfilialp and ct.codcli=:codclip
    and ct.codemp=:codempctp and ct.codfilial=:codfilialctp  and ct.codcontr=:codcontrp
    and ct.recebcontr='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.ativo='S'
    group by 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    order by 1 desc, 2 desc

   into :ano, :mes
   , :codempcl, :codfilialcl, :codcli
   , :codempct, :codfilialct, :codcontr
   , :qtdcontr, :dtinicio, :qtdhoras
   do
   begin
       /* Caso não existam lançamentos retornar ano e mês atual */
       if (:ano is null) then
          ano = extract(year from dtfimp);

       if (:mes is null) then
           mes = extract(month from dtfimp);

       -- TOTAL DE FRANQUIA NO MES
       totfranquiames = qtditcontr;

       -- SALDO MÊS
       if( :qtditcontr - :qtdhoras   > 0) then
           saldomes =  qtditcontr - qtdhoras;
       else
           saldomes = 0;

       -- VALOR CONTRATADO
       valorcontr = qtditcontr * valor;

       -- EXCEDENTE MES
       if( :qtdhoras < :qtditcontr ) then
          excedentemes = 0;
       else
          excedentemes = qtdhoras - qtditcontr;

       -- EXCEDENTE MES COBRANÇA
       if( (:qtdhoras < :qtditcontr)
       --or (mes <> mescob)
       ) then
          excedentemescob = 0;
       else
          excedentemescob = qtdhoras - qtditcontr;

       -- SALDO DO PERÍODO
--       saldoperiodo2 = saldoperiodo2 + saldomes;

       -- EXCEDENTE DO PERÍODO
  --     excedenteperiodo2 = excedenteperiodo2 + excedentemescob;

       --VLRTOTAL =$V{QTDITCONTRCALC}.multiply($F{VALOR}).add($V{EXCEDENTECOB}.multiply( $V{VALOREXCEDENTE} ))

       -- VALOR A COBRAR EXCEDENTE
--       valorexcedentecob = excedentecob * valorexcedente;
        
       -- VALOR TOTAL A COBRAR
--       valortotalcob = (qtditcontr * valor) + (excedentecob * valorexcedente);

      suspend;
   end


end
^

/* Alter (ATRESUMOATENDOSP02) */
ALTER PROCEDURE ATRESUMOATENDOSP02(CODEMPCLP INTEGER,
CODFILIALCLP SMALLINT,
CODCLIP INTEGER,
CODEMPCTP INTEGER,
CODFILIALCTP SMALLINT,
CODCONTRP INTEGER,
CODITCONTRP SMALLINT,
DTINIP DATE,
DTFIMP DATE)
 RETURNS(CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
RAZCLI VARCHAR(60),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODITCONTR INTEGER,
CODCONTR INTEGER,
DESCCONTR VARCHAR(100),
VLRHORA DECIMAL(15,5),
VLRHORAEXCED DECIMAL(15,5),
QTDCONTR DECIMAL(15,5),
QTDITCONTR DECIMAL(15,5),
VLRCOB DECIMAL(15,5),
VLRCOBEXCED DECIMAL(15,5),
VLRCOBTOT DECIMAL(15,5),
MES SMALLINT,
ANO SMALLINT,
QTDHORAS DECIMAL(15,5),
SALDOMES DECIMAL(15,5),
EXCEDENTEMES DECIMAL(15,5),
EXCEDENTEMESCOB DECIMAL(15,5),
ACUMULO SMALLINT,
CDATAINI DATE)
 AS
declare variable dtiniac date;
begin
  for select cl.codemp codempcl, cl.codfilial codfilialcl, cl.codcli, cl.razcli
    , ct.codemp codempct, ct.codfilial codfilialct, ct.codcontr, ct.desccontr, ct.dtinicio
    from vdcliente cl, vdcontrato ct
    where cl.codemp=:codempclp and cl.codfilial=:codfilialclp and (:codclip=0 or cl.codcli=:codclip)
       and ct.codemp=:codempctp and ct.codfilial=:codfilialctp and (:codcontrp=0 or ct.codcontr=:codcontrp)
       and ct.codempcl=cl.codemp and ct.codfilialcl=cl.codfilial and ct.codcli=cl.codcli
       and ct.ativo='S' and ct.tpcobcontr in ('ME','BI','AN') and ct.recebcontr='S'
     order by cl.razcli,  cl.codcli,  ct.codcontr
  into :codempcl, :codfilialcl, :codcli, :razcli
    , :codempct, :codfilialct, :codcontr, :desccontr, :cdataini
  do
  begin


--       and i.codemp=ct.codemp  and i.codfilial=ct.codfilial and i.codcontr=ct.codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontrp)

  --vditcontrato i,

      select max(i.acumuloitcontr) from vditcontrato i
      where i.codemp=:codempct  and i.codfilial=:codfilialct and i.codcontr=:codcontr and (:coditcontrp=0 or i.coditcontr=:coditcontr)
      and i.acumuloitcontr is not null
      into :acumulo;
      if ( (:acumulo is null) or (:acumulo=0) ) then
      begin
        acumulo = 0;
        dtiniac = dtinip;
      end
      else
      begin
        dtiniac = dtinip;
        dtiniac = cast( addmonth(dtiniac, -1*acumulo) as date);
        if( dtiniac > dtinip ) then
         dtiniac = dtinip;
      end
      for select a.mes, a.ano, a.qtdcontr, a.valor, a.valorexcedente, a.qtditcontr, a.qtdhoras, a.valortotalcob
       , a.saldomes, a.excedentemes, a.excedentemescob, a.valorexcedentecob , a.valorcontr
         from atresumoatendosp01(:codempcl, :codfilialcl, :codcli
         , :codempct, :codfilialct, :codcontr, :coditcontrp, :dtiniac, :dtfimp) a
      into :mes, :ano, :qtdcontr, :vlrhora, :vlrhoraexced, :qtditcontr, :qtdhoras, :vlrcobtot
      , :saldomes, :excedentemes, :excedentemescob, :vlrcobexced, :vlrcob
      do
      begin
        
      suspend;
      end
  end
end
^

/* Alter (CPADICCOMPRAPEDSP) */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable codempse integer;
declare variable codfilialse smallint;
declare variable serie char(4);
declare variable statuscompra char(2);
begin

    --Buscando a série da compra
    select tm.codempse, tm.codfilialse, tm.serie
    from eqtipomov tm
    where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
    into :codempse, :codfilialse, :serie;

    --Definição do status da compra
    statuscompra = 'P1';

    --Buscando doccompra
    select doc from lfnovodocsp(:serie, :codempse , :codfilialse) into doccompra;

    insert into cpcompra (
    codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor,
    codempse, codfilialse, serie, codemptm, codfilialtm, codtipomov, doccompra, dtentcompra, dtemitcompra, statuscompra, calctrib )
    values (
    :codemp, :codfilial, :codcompra, :codemppg, :codfilialpg, :codplanopag, :codempfr, :codfilialfr, :codfor,
    :codempse, :codfilialse, :serie, :codemptm, :codfilialtm, :codtipomov, :doccompra,
    cast('today' as date), cast('today' as date), :statuscompra, 'S' );

    iret = :codcompra;

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
TPAGRUP CHAR,
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
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
declare variable refprod varchar(20);
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

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
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
declare variable usaprecocot char(1);
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
declare variable aprovpreco char(1);
declare variable codemppp integer;
declare variable codfilialpp smallint;
declare variable codplanopag integer;
declare variable vlrproditcompra numeric(15,5);
declare variable qtditrecmerc numeric(15,5);
declare variable codempns integer;
declare variable codfilialns smallint;
declare variable numserietmp varchar(30);
declare variable percprecocoletacp numeric(15,5);
declare variable permititemrepcp char(1);
declare variable trocaqtd char(1);
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Verificando se é para buscar as quantidades
    if ( (qtditcompra is null) or (qtditcompra=0)) then
       trocaqtd = 'S';
    else 
       trocaqtd = 'N';
    
    
    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;
    
    -- Buscando preferências GMS
    select coalesce(p8.percprecocoletacp,100) percprecocoletacp
    , coalesce(permititemrepcp, 'N') 
    from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :percprecocoletacp, :permititemrepcp;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc, ir.qtditrecmerc,
        ir.codempns, ir.codfilialns, ir.numserie
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc, :qtditrecmerc,
        :codempns, :codfilialns, :numserietmp
        do
        begin
            if (:trocaqtd='S') then
                 qtditcompra = 0;  
            if(:permititemrepcp='S' or :codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov,null)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Se deve buscar preço de cotação.
                if( 'S' = :usaprecocot) then
                begin
                    -- Deve implementar ipi, vlrliq etc... futuramente...
                    select first 1 ct.precocot
                    from cpcotacao ct, cpsolicitacao sl, cpitsolicitacao iso
                    left outer join eqrecmerc rm on
                    rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket
                    where
                    iso.codemp = sl.codemp and iso.codfilial=sl.codfilial and iso.codsol=sl.codsol
                    and ct.codemp=iso.codemp and ct.codfilial=iso.codfilial and ct.codsol=iso.codsol and ct.coditsol=iso.coditsol
                    and iso.codemppd=:codemppd and iso.codfilialpd=:codfilialpd and iso.codprod=:codprod
                    and ct.codempfr=:codempfr and ct.codfilialfr=:codfilialfr and ct.codfor=:codfor
                    and (ct.dtvalidcot>=cast('today' as date) and (ct.dtcot<=cast('today' as date)))
                    and ct.sititsol not in ('EF','CA')

                    and ( (ct.rendacot = rm.rendaamostragem) or ( coalesce(ct.usarendacot,'N') = 'N') )

                    and ( (ct.codemppp=:codemppp and ct.codfilialpp=:codfilialpp and ct.codplanopag=:codplanopag)
                       or (ct.codplanopag is null))

                    order by ct.dtcot desc
                    into :precoitcompra;

                    if(:precoitcompra is not null) then
                    begin
                        -- Indica que o preço é aprovado (cotado anteriormente);
                        aprovpreco = 'S';
                    end
                end

                -- Se não conseguiu obter o preço das cotações
                if(precoitcompra is null) then
                begin
                    -- Buscando preço de compra da tabela de custos de produtos
                    select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                    cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                    into :precoitcompra;

                end

                -- verifica se quantidade está zerada (coleta) se estiver preechida (trata-se de uma pesagem)
                if ( (qtditcompra is null) or (qtditcompra = 0) ) then 
                begin
                    qtditcompra = qtditrecmerc;
                end

                if ( ( :percprecocoletacp is not null) and (:percprecocoletacp<>100) ) then
                begin
                   precoitcompra = cast( :precoitcompra / 100 * :percprecocoletacp as decimal(15,5) ); 
                end
                 
                vlrproditcompra = :precoitcompra * qtditcompra;

                -- Inserir itens
                
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra,
                codempns, codfilialns, numserietmp)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra,
                :codempns, :codfilialns,  :numserietmp) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Alter (CPGERADEVOLUCAOSP) */
ALTER PROCEDURE CPGERADEVOLUCAOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
CODITVENDA INTEGER,
TIPOVENDA CHAR(2),
QTDDEV NUMERIC(15,5))
 AS
begin

    -- Limpando vínculo anterior

    delete from cpdevolucao
    where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra
    and coditcompra=:coditcompra and codempvd=:codempvd and codfilialvd=:codfilialvd
    and codvenda=:codvenda and coditvenda=:coditvenda and tipovenda=:tipovenda;

    -- Inserindo novo vínculo de devolução
    if(:CODCOMPRA is not null and :CODITCOMPRA is not null) then
    begin
        insert into cpdevolucao
        (codemp, codfilial, codcompra, coditcompra, qtddev,
         codempvd, codfilialvd, codvenda, tipovenda, coditvenda )
        values
        (:codemp, :codfilial, :codcompra, :coditcompra, :qtddev,
         :codempvd, :codfilialvd, :codvenda, :tipovenda, :coditvenda);
    end
  suspend;
end
^

/* Alter (CPITCOMPRASERIESP) */
ALTER PROCEDURE CPITCOMPRASERIESP(TRANSACAO CHAR,
CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITCOMPRA INTEGER)
 AS
declare variable seqitserie integer;
declare variable serieprod char(1);
begin
        
    -- Buscando informações do produto
    select pd.serieprod from eqproduto pd
    where pd.codemp = :codemppd and pd.codfilial = :codfilialpd and pd.codprod = :codprod
    into :serieprod;
    
    -- Se o produto usa série...
    if(serieprod = 'S') then
    begin
    
        -- Buscando ultimo item de série inserido...
        select coalesce(max(seqitserie),0) from cpitcompraserie
        where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra
        into :seqitserie;
    
        -- Se for uma transaçáo de Inserção...
        if( 'I' = :transacao) then
        begin
            -- Inserindo itens, enquanto a sequencia for menor ou igual à quantidade de itens
            while (seqitserie < qtditcompra) do
            begin
                seqitserie = seqitserie + 1;

                -- Se a quantidade não for unitária
                if(qtditcompra > 1) then
                begin
                    insert into cpitcompraserie
                    (codemp, codfilial, codcompra,  coditcompra, seqitserie )
                    values
                    (:codemp, :codfilial, :codcompra, :coditcompra, :seqitserie);
                end
                -- Se for apenas um item, deve inserir o numero se série digitado na tela...
                else if(qtditcompra = 1 and :numserietmp is not null) then
                begin
                    insert into cpitcompraserie
                    (codemp, codfilial, codcompra,  coditcompra, seqitserie, codemppd, codfilialpd, codprod, numserie )
                    values
                    (:codemp, :codfilial, :codcompra, :coditcompra, :seqitserie, :codemppd, :codfilialpd, :codprod, :numserietmp );
                end
            end
        end
        -- Se for uma transação de Deleção, deve excluir todas os ítens gerados.
        else if('D' = :transacao) then
        begin
            delete from cpitcompraserie itcs
            where
            itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.codcompra = :codcompra and itcs.coditcompra = :coditcompra;
        end
        -- Se for uma transação de Update, deve excluir os excessos ou incluir os faltantes...
        else if('U' = :transacao) then
        begin

            if(seqitserie > qtditcompra) then
            begin

                delete from cpitcompraserie itcs
                where
                itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.codcompra = :codcompra and itcs.coditcompra = :coditcompra
                and itcs.seqitserie > :qtditcompra;
            
            end
            else
            begin
            
                execute procedure cpitcompraseriesp( 'I', :codemp, :codfilial, :codcompra, :coditcompra, :codemppd, :codfilialpd, :codprod, :numserietmp, :qtditcompra);

            end

        end

    end


end
^

/* empty dependent procedure body */
/* Clear: CPADICITCOMPRAPEDSP for: CPUPCOMPRAPEDSP */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR,
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
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

/* Alter (CRCARREGAPONTOSP) */
ALTER PROCEDURE CRCARREGAPONTOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
IDUSU VARCHAR(128),
AFTELA CHAR,
TOLREGPONTO SMALLINT)
 RETURNS(CARREGAPONTO CHAR,
DATAPONTO DATE,
HORAPONTO TIME,
CODEMPAE INTEGER,
CODFILIALAE SMALLINT,
CODATEND INTEGER,
CODEMPEP INTEGER,
CODFILIALEP SMALLINT,
MATEMPR INTEGER)
 AS
declare variable contabat smallint;
declare variable hiniturno time;
declare variable hiniintturno time;
declare variable hfimintturno time;
declare variable hfimturno time;
declare variable tolregpontoseg smallint;
declare variable umahoraseg smallint;
declare variable duashorasseg smallint;
begin
  if ( tolregponto is null) then
  begin
     tolregponto = 20;
  end
  tolregpontoseg = tolregponto * 60;
  umahoraseg = 60 * 60;
  duashorasseg = umahoraseg * 2;


  dataponto = cast('today' as date);
  horaponto = cast('now' as time);
  carregaponto = 'N';
  select 'S' carregaponto, ae.codemp, ae.codfilial, ae.codatend,
    ae.codempep, ae.codfilialep, ae.matempr,
    t.hiniturno, t.hiniintturno, t.hfimintturno, t.hfimturno
    from atatendente ae, rhempregado ep, rhturno t
    where ae.codempus=:codemp and ae.codfilialus=:codfilial and ae.idusu=:idusu and
    ep.codemp=ae.codempep and ep.codfilial=ae.codfilialep and ep.matempr=ae.matempr and
    t.codemp=ep.codempto and t.codfilial=ep.codfilialto and t.codturno=ep.codturno
    into :carregaponto, :codempae, :codfilialae, :codatend,
    :codempep, :codfilialep, :matempr,
    :hiniturno, :hiniintturno, :hfimintturno, :hfimturno;
  if (carregaponto='S') then
  begin
     -- Verificação do ponto
     select count(*)
       from pebatida b
       where b.codempep=:codempep and b.codfilialep=:codfilialep and
       b.matempr=:matempr and b.dtbat=:dataponto
       into :contabat;
     if ( (contabat is not null) and (contabat>0) ) then
     begin
         -- Tratamento no caso de tela de abertura
         if (aftela='A') then
         begin
            -- Se o número de batidas for ímpar, não deve carregar a tela de registro.
            if ( mod(contabat, 2)>0 ) then
            begin
               carregaponto = 'N';
            end
            else
            begin
              -- Verifica a tolerância de 20 minutos para batida do ponto e
              -- horário para início de turno adicional (1 hora após fim do turno).
              if  ( ( not (:horaponto between (:hiniturno-:tolregpontoseg) and (:hiniturno+:tolregpontoseg) ) ) and
                    ( not (:horaponto between (:hfimintturno-:tolregpontoseg) and (:hfimintturno+:tolregpontoseg) ) ) and
                    ( not (:horaponto > (:hfimturno+:umahoraseg) ) )   )  then
              begin
                carregaponto = 'N';
              end
            end
         end
         -- Tratamento no caso de tela de fechamento
         else if (aftela='F') then
         begin
           -- Se for tela de fechamento e já tiver uma ou mais batidas e o número de
           -- batidas for par, não precisa carregar a tela de registro
           if ( (mod(contabat,2)=0)) then
           begin
             --execute procedure sgdebugsp 'crcarregapontosp', 'Entrou no mod-conta-bat=0';
             carregaponto = 'N';
           end
           else
           begin
             --execute procedure sgdebugsp 'crcarregapontosp', 'Entrou no if - horaponto='||:horaponto||' - hiniintturo:'||:hiniintturno||' - hfimturno:'||:hfimturno||' - cp:'||:carregaponto;
             
             -- Se não estiver entre o horário de fechamento do primeiro turno (tolerância de 20 minutos)
             -- e não estiver no intervalo de fechamento de turno (tolerância de 20 mintuos).
             -- horário para fim de turno adicional (2 horas após fim do turno).
            -- execute procedure sgdebugsp 'crcarregapontosp', 'hiniintturno-totregponto'||(:hiniintturno-:tolregponto);

             if  ( ( not (:horaponto between (:hiniintturno-:tolregpontoseg) and (:hiniintturno+:tolregpontoseg) ) ) and
                   ( not (:horaponto between (:hfimturno-:tolregpontoseg) and (:hfimturno+:tolregpontoseg) ) ) and
                   ( not (:horaponto > (:hfimturno+:duashorasseg ) ) ) ) then
             begin
               carregaponto = 'N';
             end
           end
         end
     end
  end

  suspend;
end
^

/* Alter (EQADICPRODUTOSP) */
ALTER PROCEDURE EQADICPRODUTOSP(CODEMPPD INTEGER,
CODFILIALPD INTEGER,
CODPROD INTEGER,
DESCPROD VARCHAR(100),
DESCAUXPROD VARCHAR(40),
REFPROD VARCHAR(20),
CODFABPROD CHAR(15),
CODBARPROD CHAR(13),
CODEMPMG INTEGER,
CODFILIALMG SMALLINT,
CODMODG INTEGER,
DESCCOMPPROD VARCHAR(500),
PRECOBASE DECIMAL(15,5),
ATUALIZAPROD CHAR)
 AS
declare variable codnovo integer;
declare variable codalmox integer;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codmoeda char(4);
declare variable codempma integer;
declare variable codfilialma integer;
declare variable codunid varchar(20);
declare variable codempud integer;
declare variable codfilialud integer;
declare variable codfisc char(13);
declare variable codempfc integer;
declare variable codfilialfc integer;
declare variable codmarca char(6);
declare variable codempmc integer;
declare variable codfilialmc integer;
declare variable codgrup char(10);
declare variable codempgp integer;
declare variable codfilialgp integer;
declare variable tipoprod varchar(2);
declare variable cvprod char(1);
declare variable cloteprod char(1);
declare variable comisprod numeric(15,5);
declare variable pesoliqprod numeric(15,5);
declare variable pesobrutprod numeric(15,5);
declare variable qtdminprod numeric(15,5);
declare variable qtdmaxprod numeric(15,5);
declare variable refprodsel varchar(20);
BEGIN
  BEGIN
    BEGIN
      codnovo = CAST(REFPROD AS INTEGER);
/*Se não conseguir converter para int causa uma excessão e entra neste bloco: */
      WHEN ANY DO
      BEGIN
        SELECT MAX(CODPROD) FROM EQPRODUTO
           WHERE CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD INTO :codnovo;
        if (codnovo is null) then
           codnovo = 0;
        codnovo = codnovo + 1;
      END
    END
    SELECT CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA,CODFILIALMA,CODUNID
           ,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA
           ,CODEMPMC,CODFILIALMC,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD
           ,CVPROD,CLOTEPROD,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD
           ,QTDMAXPROD
           FROM EQPRODUTO WHERE CODPROD=:Codprod
           AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD
           INTO
           :CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA,:CODUNID
           ,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA
           ,:CODEMPMC,:CODFILIALMC,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD
           ,:CVPROD,:CLOTEPROD,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,
           :QTDMAXPROD;

    select refprod from eqproduto where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod into :refprodsel;

    if( (:ATUALIZAPROD = 'S') AND (coalesce(:refprodsel, '' ) = :refprod )) then
    begin
        update eqproduto p set p.descprod=:descprod, p.desccompprod=:desccompprod, p.descauxprod=:descauxprod, p.precobaseprod=:precobase
        where codemp=:codemppd and codfilial=:codfilialpd and refprod=:refprod;
    end
    else if (coalesce(:refprodsel, '' ) <> :refprod ) then
    begin
        INSERT INTO EQPRODUTO (CODEMP,CODFILIAL,CODPROD,REFPROD,CODALMOX,CODEMPAX,CODFILIALAX,CODMOEDA,CODEMPMA
              ,CODFILIALMA,CODUNID,CODEMPUD,CODFILIALUD,CODFISC,CODEMPFC,CODFILIALFC,CODMARCA,CODEMPMC,CODFILIALMC
              ,CODGRUP,CODEMPGP,CODFILIALGP,TIPOPROD,CVPROD,DESCPROD,DESCAUXPROD,CLOTEPROD,CODBARPROD,CODFABPROD
              ,COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD,QTDMAXPROD,PRECOBASEPROD
              ,CODEMPOG, CODFILIALOG, CODPRODOG, CODEMPMG, CODFILIALMG, CODMODG, DESCCOMPPROD)
               VALUES (
                      :CODEMPPD,:CODFILIALPD,:Codnovo,:RefProd,:CODALMOX,:CODEMPAX,:CODFILIALAX,:CODMOEDA,:CODEMPMA,:CODFILIALMA
                      ,:CODUNID,:CODEMPUD,:CODFILIALUD,:CODFISC,:CODEMPFC,:CODFILIALFC,:CODMARCA,:CODEMPMC,:CODFILIALMC
                      ,:CODGRUP,:CODEMPGP,:CODFILIALGP,:TIPOPROD,:CVPROD,:Descprod,:DescAuxprod,:CLOTEPROD,:Codbarprod,:Codfabprod
                      ,:COMISPROD,:PESOLIQPROD,:PESOBRUTPROD,:QTDMINPROD,:QTDMAXPROD,:PRECOBASE
                      ,:CODEMPPD, :CODFILIALPD, :CODPROD, :CODEMPMG, :CODFILIALMG, :CODMODG, :DESCCOMPPROD
               );
        INSERT INTO VDPRECOPROD (CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD)
               SELECT :CODEMPPD,:CODFILIALPD,:Codnovo,CODPRECOPROD,CODCLASCLI,CODEMPCC,CODFILIALCC,
                      CODTAB,CODEMPTB,CODFILIALTB,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD FROM VDPRECOPROD WHERE CODPROD=:Codprod
                      AND CODEMP=:CODEMPPD AND CODFILIAL=:CODFILIALPD;
    end
  END
--  SUSPEND;
END
^

/* empty dependent procedure body */
/* Clear: EQCUSTOPRODSP for: EQCALCPEPSSP */
ALTER PROCEDURE EQCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: EQPRODUTOSP01 for: EQCALCPEPSSP */
ALTER PROCEDURE EQPRODUTOSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NSALDO NUMERIC(15,5),
NSALDOAX NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
NCUSTOPEPS NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
NCUSTOPEPSAX NUMERIC(15,5),
NCUSTOINFO NUMERIC(15,5),
NCUSTOUC NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: EQRELINVPRODSP for: EQCALCPEPSSP */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR,
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
CODGRUP CHAR(14),
SALDO NUMERIC(15,5),
CUSTO NUMERIC(15,5),
VLRESTOQ NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* Alter (EQCALCPEPSSP) */
ALTER PROCEDURE EQCALCPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
NSLDPROD NUMERIC(15,5),
DTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NCUSTOPEPS NUMERIC(15,5))
 AS
declare variable dttemp date;
declare variable ncusto numeric(15,5);
declare variable nqtd numeric(15,5);
declare variable nresto numeric(15,5);
declare variable ncustotot numeric(15,5);
declare variable nsldtmp numeric(15,5);
begin
  /* Procedure que retorna o cálculo do custo peps */
  NCUSTO = 0;
  NCUSTOTOT = 0;
  NRESTO = 0;
  NCUSTOPEPS = 0;
  IF ( (NSLDPROD IS NOT NULL) AND (NSLDPROD != 0) ) THEN
  BEGIN
      NSLDTMP = NSLDPROD;
      FOR SELECT MP.DTMOVPROD, coalesce(MP.QTDMOVPROD,0),coalesce(MP.PRECOMOVPROD,0)
        FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD AND
           MP.CODEMPTM=TM.CODEMP AND MP.CODFILIALTM=TM.CODFILIAL AND MP.CODTIPOMOV=TM.CODTIPOMOV AND
           MP.TIPOMOVPROD IN ('E','I') AND TM.TIPOMOV IN ('IV','CP','PC') AND
           MP.DTMOVPROD<=:DTESTOQ  AND
           ( (:ICODALMOX IS NULL) OR
             (MP.CODEMPAX=:ICODEMPAX AND MP.CODFILIALAX=:SCODFILIALAX AND
             MP.CODALMOX=:ICODALMOX)
           )
         ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
         INTO :DTTEMP,:NQTD,:NCUSTO DO
      BEGIN
         NRESTO = NSLDTMP - NQTD;
         IF (NRESTO<=0) THEN
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * (NQTD+NRESTO) );
           BREAK;
         END
         ELSE
         BEGIN
           NCUSTOTOT = NCUSTOTOT + (NCUSTO * NQTD);
           NSLDTMP = NSLDTMP - NQTD;
         END
      END
      if( (:nsldprod is not null and :nsldprod>0) and (:ncustotot is not null and :ncustotot>0) ) then
          NCUSTOPEPS = NCUSTOTOT / NSLDPROD;
      else
          ncustopeps = 0;
  END
  suspend;
end
^

/* Alter (EQCOPIAPROD) */
ALTER PROCEDURE EQCOPIAPROD(ICODPROD INTEGER,
ICODEMP INTEGER,
ICODFILIAL INTEGER)
 RETURNS(ICOD INTEGER)
 AS
declare variable inovocod integer;
BEGIN
  SELECT MAX(CODPROD)+1 FROM EQPRODUTO
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO INOVOCOD;
  INSERT INTO EQPRODUTO
      (CODPROD,CODEMP,CODFILIAL,DESCPROD,REFPROD,CODEMPAX,
      CODFILIALAX,CODALMOX,CODEMPMA,CODFILIALMA,CODMOEDA,CODEMPUD,
      CODFILIALUD,CODUNID,CODEMPFC,CODFILIALFC,CODFISC,CODEMPMC,
      CODFILIALMC,CODMARCA,DESCAUXPROD,TIPOPROD,CVPROD,CODEMPGP,
      CODFILIALGP,CODGRUP,CODBARPROD,CLOTEPROD,CODFABPROD,
      COMISPROD,PESOLIQPROD,PESOBRUTPROD,QTDMINPROD,QTDMAXPROD,
      PRECOBASEPROD,ATIVOPROD,DESCCOMPPROD)
    SELECT :INOVOCOD,:ICODEMP,:ICODFILIAL,DESCPROD,:INOVOCOD,CODEMPAX,
      CODFILIALAX,CODALMOX,CODEMPMA,CODFILIALMA,CODMOEDA,CODEMPUD,
      CODFILIALUD,CODUNID,CODEMPFC,CODFILIALFC,CODFISC,CODEMPMC,
      CODFILIALMC,CODMARCA,DESCAUXPROD,TIPOPROD,CVPROD,CODEMPGP,
      CODFILIALGP,CODGRUP,CODBARPROD,CLOTEPROD,CODFABPROD,COMISPROD,
      PESOLIQPROD,PESOBRUTPROD,QTDMINPROD,QTDMAXPROD,PRECOBASEPROD,
      ATIVOPROD,DESCCOMPPROD FROM
      EQPRODUTO WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND
      CODPROD=:ICODPROD;

    INSERT INTO VDPRECOPROD (CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,
        CODEMPTB,CODFILIALTB,CODTAB,CODEMPCC,CODFILIALCC,CODCLASCLI,
        CODEMPPG,CODFILIALPG,CODPLANOPAG,PRECOPROD)
      SELECT CODEMP,CODFILIAL,:INOVOCOD,CODPRECOPROD,
        CODEMPTB,CODFILIALTB,CODTAB,CODEMPCC,CODFILIALCC,CODCLASCLI,
        CODEMPPG,CODFILIALPG,CODPLANOPAG,PRECOPROD FROM VDPRECOPROD
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND
          CODPROD=:ICODPROD;

    INSERT INTO EQFATCONV (CODEMP,CODFILIAL,CODPROD,CODUNID,FATCONV)
      SELECT CODEMP,CODFILIAL,:INOVOCOD,CODUNID,FATCONV FROM EQFATCONV
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODPROD=:ICODPROD;

    INSERT INTO CPPRODFOR (CODEMP,CODFILIAL,CODPROD,CODFOR,REFPRODFOR)
      SELECT CODEMP,CODFILIAL,:INOVOCOD,CODFOR,REFPRODFOR FROM CPPRODFOR
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODPROD=:ICODPROD;
        
    /* se for outro produto o lote provavelmente será outro também */
    /*INSERT INTO EQLOTE (CODEMP,CODFILIAL,CODPROD,CODLOTE,VENCTOLOTE,
        SLDLOTE,SLDRESLOTE,SLDCONSIGLOTE,SLDLIQLOTE,DINILOTE)
      SELECT CODEMP,CODFILIAL,:INOVOCOD,CODLOTE,VENCTOLOTE,
        SLDLOTE,SLDRESLOTE,SLDCONSIGLOTE,SLDLIQLOTE,DINILOTE FROM EQLOTE
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODPROD=:ICODPROD;*/
        
    INSERT INTO VDFOTOPROD (CODEMP,CODFILIAL,CODPROD,CODFOTOPROD,DESCFOTOPROD,
        TIPOFOTOPROD,LARGFOTOPROD,ALTFOTOPROD,FOTOPROD)
      SELECT CODEMP,CODFILIAL,:INOVOCOD,CODFOTOPROD,DESCFOTOPROD,
        TIPOFOTOPROD,LARGFOTOPROD,ALTFOTOPROD,FOTOPROD FROM VDFOTOPROD
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODPROD=:ICODPROD;
   ICOD = INOVOCOD;
   SUSPEND;
END
^

/* empty dependent procedure body */
/* Clear: CPADICITCOMPRARECMERCSP for: EQCUSTOPRODSP */
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

/* empty dependent procedure body */
/* Clear: EQRELPEPSSP for: EQCUSTOPRODSP */
ALTER PROCEDURE EQRELPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP VARCHAR(14),
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CLOTEPROD CHAR,
SOPRODSALDO CHAR)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR,
CODUNID CHAR(20),
TIPOPROD VARCHAR(2),
CODNCM VARCHAR(10),
EXTIPI CHAR(2),
COD_GEN CHAR(2),
CODSERV CHAR(5),
ALIQ_ICMS NUMERIC(9,2),
CODNBM VARCHAR(12),
CODLOTE VARCHAR(20),
VENCTOLOTE DATE,
SLDLOTE NUMERIC(15,5),
CODGRUP VARCHAR(14),
SIGLAGRUP VARCHAR(10))
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: PPCUSTOPRODSP for: EQCUSTOPRODSP */
ALTER PROCEDURE PPCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(CUSTOUNIT NUMERIC(15,5),
SLDPROD NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* Alter (EQCUSTOPRODSP) */
ALTER PROCEDURE EQCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
declare variable precobase numeric(15,5);
declare variable custompm numeric(15,5);
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

     -- Se o almoxarifado não for selecionado, deve buscar o saldo geral
    if(:icodalmox is null) then
    begin
   --      execute procedure sgdebugsp 'eqcustoprosp','inicio do custo por almoxarifado';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
             ) ,
            (SELECT FIRST 1 M.CUSTOMPMMOVPROD
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
            )
            FROM EQPRODUTO P
            WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND P.CODPROD=:ICODPROD
            INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;
  --       execute procedure sgdebugsp 'eqcustoprosp','após custo mpm';

    end
    else
    begin

   --     execute procedure sgdebugsp 'eqcustoprosp','inicio do custo geral';

        SELECT P.PRECOBASEPROD,
            (SELECT FIRST 1 M.SLDMOVPRODAX
             FROM EQMOVPROD M
             WHERE M.CODEMPPD=P.CODEMP AND M.CODFILIAL=P.CODFILIAL AND M.CODPROD=P.CODPROD AND M.DTMOVPROD<=:DTESTOQ
                and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
             ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         ) ,
         (SELECT FIRST 1 M.CUSTOMPMMOVPROD
         FROM EQMOVPROD M
         WHERE M.CODEMPPD=P.CODEMP AND
               M.CODFILIAL=P.CODFILIAL AND
               M.CODPROD=P.CODPROD AND
               M.DTMOVPROD<=:DTESTOQ
               and ( (:icodalmox is null) or ( m.codempax=:icodempax and m.codfilial=:scodfilialax and m.codalmox=:icodalmox) )
         ORDER BY P.CODPROD, M.DTMOVPROD DESC , M.CODMOVPROD DESC
         )
       FROM EQPRODUTO P
       WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
          P.CODPROD=:ICODPROD
       INTO :PRECOBASE, :SLDPROD, :CUSTOMPM;

  --     execute procedure sgdebugsp 'eqcustoprosp','após o custo geral';

    end


  CUSTOUNIT = 0;
  CUSTOTOT = 0;
  IF (SLDPROD IS NULL) THEN
        SLDPROD = 0;
  IF ( (SLDPROD!=0) OR (CCOMSALDO!='S') ) THEN
  BEGIN
      -- Custo PEPS
      IF (CTIPOCUSTO='P') THEN
      BEGIN

         SELECT NCUSTOPEPS FROM EQCALCPEPSSP(:ICODEMP,:SCODFILIAL,:ICODPROD,
            :SLDPROD,:DTESTOQ, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX)
            INTO :CUSTOUNIT;
         IF (CUSTOUNIT!=0) THEN
            CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo PEPS';

      END
      -- Custo MPM
      ELSE IF (CTIPOCUSTO='M') THEN
      BEGIN
         CUSTOUNIT = CUSTOMPM;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
    --     execute procedure sgdebugsp 'eqcustoprosp','após custo MPM';

      END
      -- Preço Base
      ELSE IF (CTIPOCUSTO='B') THEN
      BEGIN
         CUSTOUNIT = PRECOBASE;
         IF (CUSTOUNIT IS NULL) THEN
            CUSTOUNIT = 0;
          CUSTOTOT = CUSTOUNIT*SLDPROD;
      --    execute procedure sgdebugsp 'eqcustoprosp','após precobase';

      END
      -- Preço da Ultima Compra
      else if (CTIPOCUSTO='U') then
      begin
        select first 1 ic.custoitcompra from cpitcompra ic, cpcompra cp
            where cp.codemp=:icodemp and cp.codfilial=:scodfilial and cp.dtentcompra<=:dtestoq
            and ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra
            and ic.codemppd=:icodemp and ic.codfilialpd=:scodfilial and ic.codprod=:icodprod
            and ( (:icodalmox is null) or ( ic.codempax=:icodempax and ic.codfilial=:scodfilialax and ic.codalmox=:icodalmox) )
            order by cp.dtentcompra desc
            into :CUSTOUNIT;

            if (CUSTOUNIT IS NULL) THEN
                CUSTOUNIT = :CUSTOMPM;
         -- execute procedure sgdebugsp 'eqcustoprosp','após última compra';

      end

  END
  SUSPEND;
end
^

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
declare variable refprod varchar(20);
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
    from sgretinfousu(:codemprm, user)
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
                     codempos,codfilialos,ticket,coditrecmerc,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,:coditrecmerc,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditrecmerc, os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
--    and ( (os.coditrecmerc=:coditrecmerc) or (:coditrecmerc is null) )
    and os.gerarma='S'
    into :coditrecmerc, :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
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
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            CODEMPOS,CODFILIALOS,TICKET,CODITRECMERC,CODITOS
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE',
                            :codemprm, :codfilialrm, :ticket, :coditrecmerc,:coditos
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* empty dependent procedure body */
/* Clear: PPGERAOP for: EQGERARMASP */
ALTER PROCEDURE PPGERAOP(TIPOPROCESS CHAR,
CODEMPOP INTEGER,
CODFILIALOP SMALLINT,
CODOP INTEGER,
SEQOP INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC SMALLINT,
QTDSUGPRODOP NUMERIC(15,5),
DTFABROP DATE,
SEQEST SMALLINT,
CODEMPET INTEGER,
CODFILIALET SMALLINT,
CODEST SMALLINT,
AGRUPDATAAPROV CHAR,
AGRUPDTFABROP CHAR,
AGRUPCODCLI CHAR,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
DATAAPROV DATE,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
JUSTFICQTDPROD VARCHAR(500),
CODEMPPDENTRADA INTEGER,
CODFILIALPDENTRADA SMALLINT,
CODPRODENTRADA INTEGER,
QTDENTRADA NUMERIC(15,5))
 RETURNS(CODOPRET INTEGER,
SEQOPRET SMALLINT)
 AS
 BEGIN SUSPEND; END
^

/* Alter (EQGERARMASP) */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
declare variable seqof smallint;
declare variable idusu1 char(8);
declare variable seqitop integer;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codrma integer;
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialpd smallint;
declare variable codfilialtm1 smallint;
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
declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
declare variable codfase integer;
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc integer;
declare variable coditos integer;
begin

    -- Buscando informações do usuário
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codempop, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando preferencias de tipo de movimento para OP
    select codfilialt8,codtipomov8
    from sgprefere1 where codemp=:CODEMPOP and codfilial=(select icodfilial from sgretfilial(:CODEMPOP, 'SGPREFERE1'))
    into :CODFILIALTM1,:CODTIPOMOV1;

    --Buscando informações da OP.
    select op.codempos, op.codfilialos, op.ticket, op.coditrecmerc, op.coditos
    from  ppop op
    where op.codemp=:codempop and op.codfilial=:codfilialop and op.codop=:codop and op.seqop=:seqop
    into :codempos, :codfilialos, :ticket, :coditrecmerc, :coditos;

    -- Buscando preferencias para geração de RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:CODEMPOP and
    codfilial=(select icodfilial from sgretfilial(:CODEMPOP,'SGPREFERE5'))
    into :STATUSRMAGER;

    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    for select codfilialfs,codfase,seqof from ppopfase opf
        where opf.codemp=:CODEMPOP and opf.codfilial=:CODFILIALOP and
        opf.codop=:CODOP and  opf.seqop=:SEQOP and
        (select count(1) from ppitop it
        where it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP and
        it.codempfs=opf.codempfs and it.codfilialfs=opf.codfilialfs and
        it.codfase=opf.codfase and it.gerarma='S' and
        it.codop=:CODOP and it.seqop=:SEQOP) > 0
        into :codfilialfase,:codfase,:SEQOF do
    begin
        select coalesce((max(codrma)+1),1)
        from eqrma
        where codemp=:CODEMPOP and codfilial=:CODFILIALOP into :CODRMA;

        insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempof,codfilialof,codop,seqop,seqof,
                     motivorma, codempos, codfilialos, ticket, coditrecmerc)
                     values
                     (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQRMA')),:CODRMA,
                      :CODEMPOP, :CODFILIALUSU1,:IDUSU1,
                      null,null,null,
                      null,null,null,
                      :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQTIPOMOV')),
                      :CODTIPOMOV1,
                      :CODEMPOP,:CODFILIALCCUSU1,:ANOCCUSU1,:CODCCUSU1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :CODEMPOP,:CODFILIALOP,:CODOP,:SEQOP,:SEQOF,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OP:'||:CODOP||' SEQ:'||:SEQOP||' - FASE:'||:CODFASE,
                      :codempos, :codfilialos, :ticket, :coditrecmerc
        );

        for select it.seqitop,it.codfilialpd,it.codprod,it.refprod,it.qtditop-coalesce(it.qtdcopiaitop,0),it.codfilialle,it.codlote,
            (select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)),
            (SELECT CODFILIALAX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod),
            (SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod)
            from ppitop it, eqproduto pd
            where
            pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
            and it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP
            and it.codop=:CODOP and it.seqop=:SEQOP and it.codempfs=:CODEMPOP
            and it.codfilialfs=:CODFILIALFASE
            and it.codfase = :CODFASE and it.gerarma='S'
            and (('S'=(select ratauto from sgprefere5 where codemp=it.codemp and codfilial=it.codfilial))
            and (it.qtditop-coalesce(it.qtdcopiaitop, 0))<=(SELECT L.SLDLOTE FROM EQLOTE L
                                                            WHERE L.CODEMP=it.codemple AND L.CODFILIAL=it.codfilialle AND
                                                            L.CODPROD=it.codprod AND L.CODLOTE=it.codlote)
            or pd.cloteprod = 'N'
                                                            )
            into :SEQITOP, :CODFILIALPD,:CODPROD,:REFPROD,:QTD,
            :CODFILIALLE,:CODLOTE,:CUSTOMPMIT,:CODFILIALAX,:CODALMOX  DO
        begin
            select coalesce((max(coditrma)+1),1) from eqitrma
            where codemp=:CODEMPOP and codfilial=:CODFILIALOP and
            codrma=:CODRMA into :coditrma;

            if(:STATUSRMAGER='AF')then
            begin
                STATUSAPROVRMAGER = 'AT';
                QTDAPROV = :QTD;
            end

            insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            codempos, codfilialos, ticket, coditrecmerc, coditos
                            )
                            values
                            (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQITRMA')),:CODRMA,
                            :coditrma,
                            :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQPRODUTO')),
                            :CODPROD,:REFPROD,:QTD,:QTDAPROV,0,:CUSTOMPMIT,:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQLOTE')),:CODLOTE,
                            :CODEMPOP,:CODFILIALAX,:CODALMOX,
                            :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                            :codempop, :codfilialos, :ticket, :coditrecmerc, :coditos
                            );

            update ppitop it set it.gerarma='N' where it.CODEMP=:CODEMPOP AND
                it.CODFILIAL=:CODFILIALOP AND
                it.codop=:CODOP and it.seqop=:SEQOP and it.seqitop=:seqitop;

        end

    end

    suspend;

end
^

/* Alter (EQITRECMERCSERIESP) */
ALTER PROCEDURE EQITRECMERCSERIESP(TRANSACAO CHAR,
CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODITRECMERC SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITRECMERC INTEGER)
 AS
declare variable seqitserie integer;
declare variable serieprod char(1);
begin
        
    -- Buscando informações do produto
    select pd.serieprod from eqproduto pd
    where pd.codemp = :codemppd and pd.codfilial = :codfilialpd and pd.codprod = :codprod
    into :serieprod;
    
    -- Se o produto usa série...
    if(serieprod = 'S') then
    begin
    
        -- Buscando ultimo item de série inserido...
        select coalesce(max(seqitserie),0) from eqitrecmercserie
        where codemp=:codemp and codfilial=:codfilial and ticket=:ticket and coditrecmerc=:coditrecmerc
        into :seqitserie;
    
        -- Se for uma transaçáo de Inserção...
        if( 'I' = :transacao) then
        begin
            -- Inserindo itens, enquanto a sequencia for menor ou igual à quantidade de itens
            while (seqitserie < qtditrecmerc) do
            begin
                seqitserie = seqitserie + 1;

                -- Se a quantidade não for unitária
                if(qtditrecmerc > 1) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie);
                end
                -- Se for apenas um item, deve inserir o numero se série digitado na tela...
                else if(qtditrecmerc = 1 and :numserietmp is not null) then
                begin
                    insert into eqitrecmercserie
                    (codemp, codfilial, ticket,  coditrecmerc, seqitserie, codemppd, codfilialpd, codprod, numserie )
                    values
                    (:codemp, :codfilial, :ticket, :coditrecmerc, :seqitserie, :codemppd, :codfilialpd, :codprod, :numserietmp );
                end
            end
        end
        -- Se for uma transação de Deleção, deve excluir todas os ítens gerados.
        else if('D' = :transacao) then
        begin
            delete from eqitrecmercserie itcs
            where
            itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc;
        end
        -- Se for uma transação de Update, deve excluir os excessos ou incluir os faltantes...
        else if('U' = :transacao) then
        begin

            if(seqitserie > qtditrecmerc) then
            begin

                delete from eqitrecmercserie itcs
                where
                itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.ticket = :ticket and itcs.coditrecmerc = :coditrecmerc
                and itcs.seqitserie > :qtditrecmerc;
            
            end
            else
            begin
            
--                execute procedure cpitcompraseriesp( 'I', :codemp, :codfilial, :codcompra, :coditcompra, :codemppd, :codfilialpd, :codprod, :numserietmp, :qtditcompra);

            end

        end

    end


end
^

/* Alter (EQMOVPRODATEQSP) */
ALTER PROCEDURE EQMOVPRODATEQSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLEOLD INTEGER,
SCODFILIALLEOLD SMALLINT,
CCODLOTEOLD VARCHAR(20),
ICODEMPLENEW INTEGER,
SCODFILIALLENEW SMALLINT,
CCODLOTENEW VARCHAR(20),
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
DECLARE VARIABLE CCLOTEPROD CHAR(1);
DECLARE VARIABLE ICODALMOX INTEGER;
DECLARE VARIABLE ICODEMPAX INTEGER;
DECLARE VARIABLE SCODFILIALAX SMALLINT;
begin
  /* Procedure de atualização do estoque */
  UPDATE EQPRODUTO SET SLDPROD = SLDPROD+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
            WHERE CODPROD=:ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD;

  SELECT CLOTEPROD, CODALMOX,CODEMPAX,CODFILIALAX FROM EQPRODUTO
     WHERE CODPROD = :ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL = :SCODFILIALPD
      INTO :CCLOTEPROD, :ICODALMOX, :ICODEMPAX, :SCODFILIALAX;

  EXECUTE PROCEDURE EQSALDOPRODATEQSP(:ICODEMPPD, :SCODFILIALPD, :ICODPROD, :SOPERADOR,
   :NQTDMOVPRODOLD, :NQTDMOVPRODNEW, :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD,
   :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);

  IF (CCLOTEPROD = 'S') THEN
  BEGIN
    IF (CCODLOTENEW IS NULL) THEN
    BEGIN
      EXCEPTION EQPRODUTOEX01 'ESTE MOVIMENTO PRECISA DE UM LOTE!';
    END
    ELSE
    BEGIN
       EXECUTE PROCEDURE EQSALDOLOTEATEQSP(:ICODEMPLEOLD, :SCODFILIALLEOLD, :ICODPROD, :CCODLOTEOLD,
          :ICODEMPLENEW, :SCODFILIALLENEW, :CCODLOTENEW, :SOPERADOR, :NQTDMOVPRODOLD, :NQTDMOVPRODNEW,
          :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD, :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);
    END
  END

  suspend;
end
^

/* Alter (EQMOVPRODCKCPSP) */
ALTER PROCEDURE EQMOVPRODCKCPSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODCOMPRA INTEGER,
ICODINVPROD INTEGER,
DDTMOVPROD DATE,
NQTDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5))
 RETURNS(DDTULTCPPROD DATE,
NQTDULTCPPROD NUMERIC(15,5),
DDTMPMPROD DATE,
NCUSTOMPMPROD NUMERIC(15,5))
 AS
begin
  /* Procedure para checar data da última compra */
  NCUSTOMPMPROD = 0;
  if (NCUSTOMPMMOVPROD IS NULL) then
     NCUSTOMPMMOVPROD = 0;
  if ( (ICODINVPROD IS NOT NULL) OR (ICODCOMPRA IS NOT NULL) ) then
  begin
     SELECT DTULTCPPROD,QTDULTCPPROD,DTMPMPROD, CUSTOMPMPROD FROM EQPRODUTO WHERE CODPROD=:ICODPROD
          AND CODEMP=:ICODEMPPD AND CODFILIAL = :SCODFILIALPD
          INTO :DDTULTCPPROD, :NQTDULTCPPROD, :DDTMPMPROD, :NCUSTOMPMPROD;
      if (NQTDULTCPPROD IS NULL) then
         NQTDULTCPPROD = 0;
      if (NCUSTOMPMPROD IS NULL) then
         NCUSTOMPMPROD = 0;
      if ( ICODCOMPRA IS NOT NULL ) then
      begin
         if ( (DDTMOVPROD>DDTULTCPPROD) OR (DDTULTCPPROD IS NULL) ) then
         begin
             DDTULTCPPROD = DDTMOVPROD;
             NQTDULTCPPROD = NQTDMOVPROD;
         end
      end
      if ( ( ICODINVPROD IS NOT NULL) OR (ICODCOMPRA IS NOT NULL) ) then
      begin
         if ( (DDTMOVPROD>=DDTMPMPROD OR DDTMPMPROD IS NULL) AND
              (NCUSTOMPMMOVPROD>0)  ) then
         begin
            DDTMPMPROD = DDTMOVPROD;
            NCUSTOMPMPROD = NCUSTOMPMMOVPROD;
         end
      end
  end


  suspend;
end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODCSLDSP for: EQMOVPRODCKTMSP */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR,
CTIPOMOVPROD CHAR,
SOPERADOR SMALLINT)
 AS
 BEGIN SUSPEND; END
^

/* Alter (EQMOVPRODCKTMSP) */
ALTER PROCEDURE EQMOVPRODCKTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ESTOQTIPOMOVPD CHAR)
 RETURNS(CESTIPOMOV CHAR,
SOPERADOR SMALLINT)
 AS
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
begin
  /* Verifique se é para contar estoque */
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM WHERE TM.CODEMP=:ICODEMP AND
     TM.CODFILIAL = :SCODFILIAL AND TM.CODTIPOMOV = :ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;
  SOPERADOR = 0;
  if ((ESTOQTIPOMOVPD='S') and (CESTOQTIPOMOV='S')) then
  begin
     if (CESTIPOMOV='S') then
        SOPERADOR = -1;
     else
        SOPERADOR = 1;
  end
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODUSP for: EQMOVPRODCKUTMSP */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
 BEGIN EXIT; END
^

/* Alter (EQMOVPRODCKUTMSP) */
ALTER PROCEDURE EQMOVPRODCKUTMSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPOLD INTEGER,
SCODFILIALOLD SMALLINT,
ICODTIPOMOVOLD INTEGER)
 RETURNS(CALTTM CHAR)
 AS
DECLARE VARIABLE CESTIPOMOV CHAR(1);
DECLARE VARIABLE CESTOQTIPOMOV CHAR(1);
DECLARE VARIABLE CESTIPOMOVOLD CHAR(1);
DECLARE VARIABLE CESTOQTIPOMOVOLD CHAR(1);
begin
  /* Verifica se há alteração no tipo de movimento para reprocessamento de estoque */
  CALTTM = 'N';
  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM
     WHERE TM.CODEMP=:ICODEMP AND TM.CODFILIAL=:SCODFILIAL
      AND TM.CODTIPOMOV=:ICODTIPOMOV
     INTO :CESTIPOMOV, :CESTOQTIPOMOV;

  SELECT TM.ESTIPOMOV, TM.ESTOQTIPOMOV FROM EQTIPOMOV TM
     WHERE TM.CODEMP=:ICODEMPOLD AND TM.CODFILIAL=:SCODFILIALOLD
      AND TM.CODTIPOMOV=:ICODTIPOMOVOLD
     INTO :CESTIPOMOVOLD, :CESTOQTIPOMOVOLD;

  if ( (CESTIPOMOV!=CESTIPOMOVOLD) OR (CESTOQTIPOMOV!=CESTOQTIPOMOVOLD) OR
       (CESTIPOMOVOLD IS NULL) OR (CESTOQTIPOMOVOLD IS NULL)  ) then
     CALTTM = 'S';
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODISP for: EQMOVPRODCSLDSP */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODPRCSLDSP for: EQMOVPRODCSLDSP */
ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
TIPONF CHAR)
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR)
 AS
 BEGIN SUSPEND; END
^

/* Alter (EQMOVPRODCSLDSP) */
ALTER PROCEDURE EQMOVPRODCSLDSP(ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPROD NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 RETURNS(NCUSTOMPM NUMERIC(15,5),
NSALDO NUMERIC(15,5),
CESTOQMOVPROD CHAR,
CTIPOMOVPROD CHAR,
SOPERADOR SMALLINT)
 AS
begin
  /* Procedure que retorna o cálculo de custo e saldo */
  NCUSTOMPM = 0;
  NSALDO = 0;
  SELECT CESTIPOMOV, SOPERADOR
     FROM EQMOVPRODCKTMSP( :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :ESTOQTIPOMOVPD)
     INTO :CTIPOMOVPROD, :SOPERADOR;
  if (SOPERADOR=0) then
  begin
     CESTOQMOVPROD = 'N';
     NSALDO = NSLDMOVPROD;
  end
  else
  begin  /* verifica se é para controlar estoque */
     CESTOQMOVPROD = 'S';
     NSALDO = NSLDMOVPROD + CAST ( (NQTDMOVPROD * SOPERADOR) AS NUMERIC(15, 5) );
  end
  if ( (NSALDO >= NSLDMOVPROD) AND (NSALDO > 0) AND (SOPERADOR>0 OR CTIPOMOVPROD='E') ) then
  begin
    if ( (NSLDMOVPROD * NCUSTOMPMMOVPROD)  <= 0) then
       NCUSTOMPM = NPRECOMOVPROD;
    else
    begin
       if (tiponf='C') then
          NCUSTOMPM = cast(NPRECOMOVPROD * NQTDMOVPROD / NSLDMOVPROD as numeric(15,5) ) + NCUSTOMPMMOVPROD   ;
       else
          NCUSTOMPM = ( cast(NSLDMOVPROD * NCUSTOMPMMOVPROD as numeric(15,5) ) +
          cast(NQTDMOVPROD * NPRECOMOVPROD as numeric(15,5)) ) / (NSLDMOVPROD + NQTDMOVPROD) ;
    end
  end
  else
      NCUSTOMPM = NCUSTOMPMMOVPROD;

  suspend;
end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODIUDSP for: EQMOVPRODDSP */
ALTER PROCEDURE EQMOVPRODIUDSP(CIUD CHAR,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
 BEGIN EXIT; END
^

/* Alter (EQMOVPRODDSP) */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
TIPONF CHAR)
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX, :TIPONF)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

/* Alter (EQMOVPRODISP) */
ALTER PROCEDURE EQMOVPRODISP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable cestoqmovprod char(1);
declare variable ctipomovprod char(1);
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable soperador smallint;
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de inserção na tabela eqmovprod */

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX FROM EQMOVPRODSLDSP(null, null, null, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NPRECOMOVPROD, :NPRECOMOVPROD,
     :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX )
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

  /* Verifica se haverá mudança de saldo*/
  SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD, CTIPOMOVPROD, SOPERADOR FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
      :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD, :TIPONF)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :SOPERADOR;

  if (CMULTIALMOX='N') then
  begin
     NSLDMOVPRODAX = NSLDMOVPROD;
     NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
  end
  else
  begin
      SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
          :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD, :TIPONF)
        INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
  end

  SELECT SCODFILIAL, ICODMOVPROD FROM EQMOVPRODSEQSP(:ICODEMPPD)
     INTO :SCODFILIAL, :ICODMOVPROD;  /* encontra o próximo código e a filial*/

   INSERT INTO EQMOVPROD ( CODEMP, CODFILIAL, CODMOVPROD,
      CODEMPPD, CODFILIALPD , CODPROD , CODEMPLE ,
      CODFILIALLE , CODLOTE , CODEMPTM, CODFILIALTM,
      CODTIPOMOV, CODEMPIV , CODFILIALIV , CODINVPROD ,
      CODEMPCP , CODFILIALCP , CODCOMPRA , CODITCOMPRA , CODEMPVD ,
      CODFILIALVD , TIPOVENDA , CODVENDA , CODITVENDA , CODEMPRM ,
      CODFILIALRM , CODRMA , CODITRMA ,
      CODEMPOP, CODFILIALOP, CODOP, SEQOP, SEQENTOP,
      CODEMPNT , CODFILIALNT ,
      CODNAT , DTMOVPROD , DOCMOVPROD , FLAG , QTDMOVPROD ,
      PRECOMOVPROD, ESTOQMOVPROD, TIPOMOVPROD, SLDMOVPROD, CUSTOMPMMOVPROD,
      SLDMOVPRODAX, CUSTOMPMMOVPRODAX, CODEMPAX, CODFILIALAX, CODALMOX, seqsubprod )
   VALUES ( :ICODEMPPD, :SCODFILIAL, :ICODMOVPROD,
    :ICODEMPPD , :SCODFILIALPD , :ICODPROD , :ICODEMPLE ,
    :SCODFILIALLE , :CCODLOTE , :ICODEMPTM, :SCODFILIALTM,
    :ICODTIPOMOV, :ICODEMPIV , :SCODFILIALIV ,
    :ICODINVPROD , :ICODEMPCP , :SCODFILIALCP , :ICODCOMPRA ,
    :SCODITCOMPRA , :ICODEMPVD , :SCODFILIALVD , :CTIPOVENDA ,
    :ICODVENDA , :SCODITVENDA , :ICODEMPRM , :SCODFILIALRM ,
    :ICODRMA , :SCODITRMA , :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop,
    :ICODEMPNT , :SCODFILIALNT , :CCODNAT ,
    :DDTMOVPROD , :IDOCMOVPROD , :CFLAG , :NQTDMOVPROD ,
    :NPRECOMOVPROD, :CESTOQMOVPROD, :CTIPOMOVPROD, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
    :NSLDMOVPRODAX,  :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :seqsubprod );

  /* REPROCESSAR ESTOQUE */

  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
     :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
     :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
     :CMULTIALMOX, :TIPONF)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX ;

 /* ATUALIZA O CUSTO NO CADASTRO DE PRODUTOS
   EXECUTE PROCEDURE EQMOVPRODATCUSTSP(:SOPERADOR, :ICODEMPPD, :SCODFILIAL,
    :ICODMOVPROD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMMOVPROD); 
 */


  suspend;
end
^

/* Alter (EQMOVPRODIUDSP) */
ALTER PROCEDURE EQMOVPRODIUDSP(CIUD CHAR,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
SEQSUBPROD SMALLINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
declare variable cmultialmox char(1);
begin
  /* Procedure que controle INSERT, UPDATE E DELETE da tabela eqmovprod */

  /* Linha incluida para passar como parâmetro se é multi almoxarifado
      Como o objetivo de evitar I/O
  */
  SELECT CMULTIALMOX FROM SGRETMULTIALMOXSP(:ICODEMPPD) INTO :CMULTIALMOX;
  
  if (CIUD='I') then /* SE FOR INSERT */
     execute procedure EQMOVPRODISP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod, :estoqtipomovpd, :tiponf);
  else if (CIUD='U') then
     execute procedure EQMOVPRODUSP( ICODEMPPD, SCODFILIALPD, ICODPROD,
         ICODEMPLE, SCODFILIALLE, CCODLOTE, ICODEMPTM, SCODFILIALTM, ICODTIPOMOV,
         ICODEMPIV, SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA,
         SCODITCOMPRA, ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         ICODEMPNT, SCODFILIALNT,
         CCODNAT, DDTMOVPROD, IDOCMOVPROD, CFLAG, NQTDMOVPROD, NPRECOMOVPROD,
         ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX,:seqsubprod, :estoqtipomovpd, :tiponf);
  else if (CIUD='D') then
     execute procedure EQMOVPRODDSP( ICODEMPPD, SCODFILIALPD, ICODPROD, ICODEMPIV,
         SCODFILIALIV, ICODINVPROD, ICODEMPCP, SCODFILIALCP, ICODCOMPRA, SCODITCOMPRA,
         ICODEMPVD, SCODFILIALVD, CTIPOVENDA, ICODVENDA, SCODITVENDA,
         ICODEMPRM, SCODFILIALRM, ICODRMA, SCODITRMA,
         ICODEMPOP, SCODFILIALOP, ICODOP, SSEQOP, sseqentop,
         DDTMOVPROD, ICODEMPAX, SCODFILIALAX, ICODALMOX, CMULTIALMOX, :seqsubprod, :tiponf );
--  suspend;
end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODDSP for: EQMOVPRODPRCSLDSP */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
TIPONF CHAR)
 AS
 BEGIN EXIT; END
^

/* Alter (EQMOVPRODPRCSLDSP) */
ALTER PROCEDURE EQMOVPRODPRCSLDSP(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
DDTMOVPRODPRC DATE,
NSLDMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPROD NUMERIC(15,5),
NSLDMOVPRODAX NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
TIPONF CHAR)
 RETURNS(NSLDPRC NUMERIC(15,5),
NCUSTOMPMPRC NUMERIC(15,5),
NSLDPRCAX NUMERIC(15,5),
NCUSTOMPMPRCAX NUMERIC(15,5),
ESTOQTIPOMOVPD CHAR)
 AS
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable nqtdmovprod numeric(15,5);
declare variable nprecomovprod numeric(15,5);
declare variable icodmovprodprc integer;
declare variable cestoqmovprod char(1);
declare variable icodempaxprc integer;
declare variable scodfilialaxprc smallint;
declare variable icodalmoxprc integer;
begin
  /* Procedure de processamento de estoque */
  FOR SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV ,
    MP.QTDMOVPROD, MP.PRECOMOVPROD , MP.CODMOVPROD,
    MP.CODEMPAX, MP.CODFILIALAX, MP.CODALMOX, MP.ESTOQMOVPROD
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMPPD AND MP.CODFILIALPD=:SCODFILIALPD AND
       MP.CODPROD=:ICODPROD AND MP.CODEMP=:ICODEMPPD AND MP.CODFILIAL=:SCODFILIAL AND
       ( (MP.DTMOVPROD = :DDTMOVPROD AND MP.CODMOVPROD > :ICODMOVPROD) OR
         (MP.DTMOVPROD>:DDTMOVPROD) ) AND  /* ALTEREI AQUI */
       ( (:DDTMOVPRODPRC IS NULL /* AND MP.CODMOVPROD!=:ICODMOVPROD */) OR
         (MP.DTMOVPROD = :DDTMOVPRODPRC AND MP.CODMOVPROD < :ICODMOVPROD) OR
         (MP.DTMOVPROD < :DDTMOVPRODPRC) )
    ORDER BY MP.DTMOVPROD, MP.CODMOVPROD
    INTO :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
     :NQTDMOVPROD, :NPRECOMOVPROD, :ICODMOVPRODPRC,
     :ICODEMPAXPRC, :SCODFILIALAXPRC, :ICODALMOXPRC, :ESTOQTIPOMOVPD DO
  BEGIN
      SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
        :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPROD, :ESTOQTIPOMOVPD, :TIPONF)
      INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :CESTOQMOVPROD;
      if (CMULTIALMOX='N') then /* Se não for multi almoxarifado*/
      begin
         NSLDMOVPRODAX = NSLDMOVPROD;
         NCUSTOMPMMOVPRODAX = NCUSTOMPMMOVPROD;
         UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else if (ICODEMPAX=ICODEMPAXPRC AND SCODFILIALAX=SCODFILIALAXPRC AND
          ICODALMOX=ICODALMOXPRC) then
          /* Se for multi almoxarifado e o código do almoxarifado for o mesmo*/
      begin
        SELECT NSALDO, NCUSTOMPM FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
            :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMMOVPRODAX, :NSLDMOVPRODAX, :ESTOQTIPOMOVPD, :TIPONF)
            INTO :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            SLDMOVPRODAX=:NSLDMOVPRODAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMMOVPRODAX,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      else /* Se for multi almoxarifado não atualiza almoxarifado diferente */
      begin
        UPDATE EQMOVPROD SET SLDMOVPROD=:NSLDMOVPROD, CUSTOMPMMOVPROD=:NCUSTOMPMMOVPROD,
            ESTOQMOVPROD=:CESTOQMOVPROD
            WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPRODPRC;
      end
      NSLDPRC = NSLDMOVPROD;
      NCUSTOMPMPRC = NCUSTOMPMMOVPROD;
      NSLDPRCAX = NSLDMOVPRODAX;
      NCUSTOMPMPRCAX = NCUSTOMPMMOVPRODAX;
  END
  suspend;
end
^

/* Alter (EQMOVPRODRETCODSP) */
ALTER PROCEDURE EQMOVPRODRETCODSP(ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQOPENT SMALLINT,
SEQSUBPROD SMALLINT)
 RETURNS(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Retorna o código do movimento de estoque */
  if (ICODINVPROD IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPIV=:ICODEMPIV AND MP.CODFILIALIV=:SCODFILIALIV
         AND MP.CODINVPROD=:ICODINVPROD
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODCOMPRA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPCP=:ICODEMPCP AND MP.CODFILIALCP=:SCODFILIALCP
         AND MP.CODCOMPRA=:ICODCOMPRA AND MP.CODITCOMPRA=:SCODITCOMPRA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODVENDA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPVD=:ICODEMPVD AND MP.CODFILIALVD=:SCODFILIALVD
         AND MP.TIPOVENDA=:CTIPOVENDA AND MP.CODVENDA=:ICODVENDA
         AND MP.CODITVENDA=:SCODITVENDA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODRMA IS NOT NULL) then
     SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
       WHERE MP.CODEMPRM=:ICODEMPRM AND MP.CODFILIALRM=:SCODFILIALRM
         AND MP.CODRMA=:ICODRMA AND MP.CODITRMA=:SCODITRMA
       INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
  else if (ICODOP IS NOT NULL) then
     begin

        if( :sseqopent is not null ) then
        begin
            SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
            WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
            AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqentop=:sseqopent
            INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;
        end
        else if (:seqsubprod is not null) then
        begin
            SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
            WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
            AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP AND MP.seqsubprod=:seqsubprod
            INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

        end
        else
        begin
            SELECT MP.CODEMP, MP.CODFILIAL, MP.CODMOVPROD FROM EQMOVPROD MP
            WHERE MP.CODEMPOP=:ICODEMPOP AND MP.CODFILIALOP=:SCODFILIALOP
            AND MP.CODOP=:ICODOP AND MP.SEQOP=:SSEQOP and mp.seqentop is null and mp.seqsubprod is null
            INTO  :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

        end
    end



  suspend;
end
^

/* Alter (EQMOVPRODSEQSP) */
ALTER PROCEDURE EQMOVPRODSEQSP(ICODEMP INTEGER)
 RETURNS(SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER)
 AS
begin
  /* Busca o código sequencial da tabela eqmovprod */
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP, 'EQMOVPROD') INTO :SCODFILIAL ;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP, :SCODFILIAL, 'MP' ) INTO :ICODMOVPROD ;
  suspend;
end
^

/* Alter (EQMOVPRODSLDSP) */
ALTER PROCEDURE EQMOVPRODSLDSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODMOVPROD INTEGER,
ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
DDTMOVPROD DATE,
NCUSTOMPMMOVPROD NUMERIC(15,5),
NCUSTOMPMMOVPRODAX NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR)
 RETURNS(NSALDO NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
ICODMOVPRODSLD INTEGER,
NSALDOAX NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
ICODMOVPRODSLDAX INTEGER)
 AS
begin
  /* Procedure que busca saldo e custo médio */
  NSALDO = 0;
  NCUSTOMPM = 0;
  if (NCUSTOMPMMOVPROD IS NULL) then
     NCUSTOMPMMOVPROD = 0;
  if (ICODMOVPROD IS NULL) then
     SELECT FIRST 1 M.SLDMOVPROD, M.CUSTOMPMMOVPROD, M.CODMOVPROD
        FROM EQMOVPROD M
        WHERE M.CODEMPPD = :ICODEMPPD AND
          M.CODFILIALPD = :SCODFILIALPD AND
          M.CODPROD = :ICODPROD AND
          M.DTMOVPROD <= :DDTMOVPROD
        ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC
        INTO :NSALDO, :NCUSTOMPM, :ICODMOVPRODSLD;
   else
     SELECT FIRST 1 M.SLDMOVPROD, M.CUSTOMPMMOVPROD, M.CODMOVPROD
        FROM EQMOVPROD M
        WHERE M.CODEMP = :ICODEMP AND
          M.CODFILIAL = :SCODFILIAL AND
          M.CODEMPPD = :ICODEMPPD AND
          M.CODFILIALPD = :SCODFILIALPD AND
          M.CODPROD = :ICODPROD AND
          ( (M.DTMOVPROD = :DDTMOVPROD AND M.CODMOVPROD < :ICODMOVPROD ) OR
            (M.DTMOVPROD < :DDTMOVPROD ) )
        ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC
        INTO :NSALDO, :NCUSTOMPM, :ICODMOVPRODSLD;
  if (NSALDO IS NULL) then
     NSALDO = 0;
  if (NCUSTOMPM IS NULL) then
     NCUSTOMPM = NCUSTOMPMMOVPROD;

  if (CMULTIALMOX='N') then
  begin
      NSALDOAX = NSALDO;
      NCUSTOMPMAX = NCUSTOMPM;
      ICODMOVPRODSLDAX = ICODMOVPRODSLD;
  end
  else
  begin
      NSALDOAX = 0;
      NCUSTOMPMAX = 0;
      if (NCUSTOMPMMOVPRODAX IS NULL) then
         NCUSTOMPMMOVPRODAX = 0;
      if (ICODMOVPROD IS NULL) then
         SELECT FIRST 1 M.SLDMOVPRODAX, M.CUSTOMPMMOVPROD, M.CODMOVPROD
            FROM EQMOVPROD M
            WHERE M.CODEMPPD = :ICODEMPPD AND
              M.CODFILIALPD = :SCODFILIALPD AND
              M.CODPROD = :ICODPROD AND
              M.DTMOVPROD <= :DDTMOVPROD AND
              M.CODEMPAX = :ICODEMPAX AND
              M.CODFILIALAX = :SCODFILIALAX AND
              M.CODALMOX= :ICODALMOX
            ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC
            INTO :NSALDOAX, :NCUSTOMPMAX, :ICODMOVPRODSLDAX;
       else
         SELECT FIRST 1 M.SLDMOVPRODAX, M.CUSTOMPMMOVPROD, M.CODMOVPROD
            FROM EQMOVPROD M
            WHERE M.CODEMP = :ICODEMP AND
              M.CODFILIAL = :SCODFILIAL AND
              M.CODEMPPD = :ICODEMPPD AND
              M.CODFILIALPD = :SCODFILIALPD AND
              M.CODPROD = :ICODPROD AND
              M.CODEMPAX = :ICODEMPAX AND
              M.CODFILIALAX = :SCODFILIALAX AND
              M.CODALMOX = :ICODALMOX AND
              ( (M.DTMOVPROD = :DDTMOVPROD AND M.CODMOVPROD < :ICODMOVPROD ) OR
                (M.DTMOVPROD < :DDTMOVPROD ) )
            ORDER BY M.DTMOVPROD DESC, M.CODMOVPROD DESC
            INTO :NSALDOAX, :NCUSTOMPMAX, :ICODMOVPRODSLDAX;
      if (NSALDOAX IS NULL) then
         NSALDOAX = 0;
      if (NCUSTOMPMAX IS NULL) then
         NCUSTOMPMAX = NCUSTOMPMMOVPROD;
  end
  suspend;
end
^

/* Alter (EQMOVPRODUSP) */
ALTER PROCEDURE EQMOVPRODUSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLE INTEGER,
SCODFILIALLE SMALLINT,
CCODLOTE VARCHAR(20),
ICODEMPTM INTEGER,
SCODFILIALTM SMALLINT,
ICODTIPOMOV INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
ICODEMPNT INTEGER,
SCODFILIALNT SMALLINT,
CCODNAT CHAR(4),
DDTMOVPROD DATE,
IDOCMOVPROD INTEGER,
CFLAG CHAR,
NQTDMOVPROD NUMERIC(15,5),
NPRECOMOVPROD NUMERIC(15,5),
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD BIGINT,
ESTOQTIPOMOVPD CHAR,
TIPONF CHAR)
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldprc numeric(15,5);
declare variable ncustompmprc numeric(15,5);
declare variable nsldprcax numeric(15,5);
declare variable ncustompmprcax numeric(15,5);
declare variable nsldlc numeric(15,5);
declare variable ncustompmlc numeric(15,5);
declare variable nsldlcax numeric(15,5);
declare variable ncustompmlcax numeric(15,5);
declare variable ddtmovprodold date;
declare variable nprecomovprodold numeric(15,5);
declare variable nqtdmovprodold numeric(15,5);
declare variable icodemptmold integer;
declare variable scodfilialtmold smallint;
declare variable icodtipomovold integer;
declare variable calttm char(1);
declare variable ddtprc date;
declare variable ddtprcate date;
declare variable cestoqmovprod char(1);
begin
  /* Procedure de atualização da tabela eqmovprod */

  DDTPRCATE = NULL; /* Até onde deve ser processando o estoque */
 /* localiza movprod */

-- execute procedure sgdebugsp('antes da atualização...','no inicio');

  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
    FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
      :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
      :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM,
      :ICODRMA, :SCODITRMA, :ICODEMPOP, :SCODFILIALOP, :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

--  traz valores antigos

  SELECT MP.CODEMPTM, MP.CODFILIALTM, MP.CODTIPOMOV, MP.DTMOVPROD,
       MP.PRECOMOVPROD, MP.QTDMOVPROD  FROM EQMOVPROD MP
     WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND MP.CODMOVPROD=:ICODMOVPROD
     INTO :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD, :DDTMOVPRODOLD,
       :NPRECOMOVPRODOLD, :NQTDMOVPRODOLD;

   /* abaixo verificação se a alteração de tipo de movimento mexe no estoque */
   SELECT CALTTM FROM EQMOVPRODCKUTMSP(:ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV,
      :ICODEMPTMOLD, :SCODFILIALTMOLD, :ICODTIPOMOVOLD) INTO :CALTTM;

   /* verifica se há relevância para reprocessamento */
   if ( (DDTMOVPROD!=DDTMOVPRODOLD) OR (CALTTM='S') OR
        (NPRECOMOVPROD!=NPRECOMOVPRODOLD) OR (NQTDMOVPROD!=NQTDMOVPRODOLD) ) then
   begin

   -- execute procedure sgdebugsp('entrou no if','1');


      if ( DDTMOVPRODOLD IS NULL) then
         DDTMOVPRODOLD = DDTMOVPROD; /* garantir que a data antiga não e nula; */
      /* verifica qual data é menor para reprocessamento */
      if ( DDTMOVPROD<=DDTMOVPRODOLD ) then
      begin

     -- execute procedure sgdebugsp('entrou no if','2');

          DDTPRC = DDTMOVPROD;
          if (DDTMOVPROD=DDTMOVPRODOLD) then
             DDTPRCATE = null;
          else
             DDTPRCATE = DDTMOVPRODOLD;
/*          verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, :NPRECOMOVPROD, :NPRECOMOVPROD,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRC, :NSLDPRC, :ESTOQTIPOMOVPD, :TIPONF)
              INTO :NSLDPRC, :NCUSTOMPMPRC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
              NSLDPRCAX = NSLDPRC;
              NCUSTOMPMPRCAX = NCUSTOMPMPRC;
          end
          else
          begin
          SELECT NSALDO, NCUSTOMPM
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, :NQTDMOVPROD, :NPRECOMOVPROD, :NCUSTOMPMPRCAX, :NSLDPRCAX, :ESTOQTIPOMOVPD, :TIPONF)
              INTO :NSLDPRCAX, :NCUSTOMPMPRCAX;
          end
          NCUSTOMPMLC = NCUSTOMPMPRC;
          NSLDLC = NSLDPRC;
          NCUSTOMPMLCAX = NCUSTOMPMPRCAX;
          NSLDLCAX = NSLDPRCAX;
      end
      else
      begin
          DDTPRC = DDTMOVPRODOLD;
          DDTPRCATE = DDTMOVPROD;
          /* verifica lançamento anterior e traz custo e saldo */

       --   execute procedure sgdebugsp('entrou no else','3');

          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTPRC, 0, 0,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX ;

          /* verifica lançamento anterior e traz custo e saldo */
          SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
             FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
             :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, :NCUSTOMPMLC, :NCUSTOMPMLCAX,
             :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
             INTO :NSLDLC, :NCUSTOMPMLC, :NSLDLCAX, :NCUSTOMPMLCAX ;

          /* verifica se havera mudança de saldo */
          SELECT NSALDO, NCUSTOMPM, CESTOQMOVPROD
              FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
              :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
              :NCUSTOMPMLC, :NSLDLC, :ESTOQTIPOMOVPD, :TIPONF)
              INTO :NSLDLC, :NCUSTOMPMLC, :CESTOQMOVPROD;
          if (CMULTIALMOX='N') then
          begin
             NSLDLCAX = NSLDLC;
             NCUSTOMPMLCAX = NCUSTOMPMLC;
          end
          else
          begin
              SELECT NSALDO, NCUSTOMPM
                  FROM EQMOVPRODCSLDSP(:ICODEMPTM, :SCODFILIALTM,
                  :ICODTIPOMOV, (:NQTDMOVPROD-:NQTDMOVPRODOLD), :NPRECOMOVPROD,
                  :NCUSTOMPMLCAX, :NSLDLCAX, :ESTOQTIPOMOVPD, :TIPONF)
                  INTO :NSLDLCAX, :NCUSTOMPMLCAX;
          end

      end

       SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
        FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
          :SCODFILIALPD, :ICODPROD, :DDTPRC, :DDTPRCATE, :NSLDPRC,
          :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX,
          :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX, :TIPONF)
        INTO :NSLDPRC, :NCUSTOMPMPRC, :NSLDPRCAX, :NCUSTOMPMPRCAX;

      UPDATE EQMOVPROD SET DTMOVPROD=:DDTMOVPROD,
         QTDMOVPROD=:NQTDMOVPROD, PRECOMOVPROD=:NPRECOMOVPROD,
         SLDMOVPROD=:NSLDLC, CUSTOMPMMOVPROD=:NCUSTOMPMLC,
         SLDMOVPRODAX=:NSLDLCAX, CUSTOMPMMOVPRODAX=:NCUSTOMPMLCAX,
         FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE, ESTOQMOVPROD=:CESTOQMOVPROD ,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
   end
   else /*  caso não tenha nenhuma alteração relevânte para processamento */

  --  execute procedure sgdebugsp('antes do reprocessamento','5SG');

      UPDATE EQMOVPROD SET FLAG=:CFLAG, CODEMPNT=:ICODEMPNT, CODFILIALNT=:SCODFILIALNT,
         CODNAT=:CCODNAT, DOCMOVPROD=:IDOCMOVPROD, CODEMPTM=:ICODEMPTM,
         CODFILIALTM=:SCODFILIALTM, CODTIPOMOV=:ICODTIPOMOV, CODEMPLE=:ICODEMPLE,
         CODFILIALLE=:SCODFILIALLE, CODLOTE=:CCODLOTE,
         CODEMPAX=:ICODEMPAX, CODFILIALAX=:SCODFILIALAX, CODALMOX=:ICODALMOX
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODMOVPROD=:ICODMOVPROD;
end
^

/* empty dependent procedure body */
/* Clear: EQGERARMAOSSP for: EQPRODUTOSP01 */
ALTER PROCEDURE EQGERARMAOSSP(CODEMPRM INTEGER,
CODFILIALRM INTEGER,
TICKET INTEGER,
CODITRECMERC SMALLINT)
 RETURNS(CODRMA INTEGER)
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: EQGERARMASP for: EQPRODUTOSP01 */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter (EQPRODUTOSP01) */
ALTER PROCEDURE EQPRODUTOSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(NSALDO NUMERIC(15,5),
NSALDOAX NUMERIC(15,5),
NCUSTOMPM NUMERIC(15,5),
NCUSTOPEPS NUMERIC(15,5),
NCUSTOMPMAX NUMERIC(15,5),
NCUSTOPEPSAX NUMERIC(15,5),
NCUSTOINFO NUMERIC(15,5),
NCUSTOUC NUMERIC(15,5))
 AS
declare variable ddtmovprod date;
declare variable ddtmovprodax date;
begin

    /* Procedure que retorna saldos e custos para a tela de cadastro de produtos */
    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPROD , MP.CUSTOMPMMOVPROD
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPROD, :NSALDO, :NCUSTOMPM;

    SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPRODAX, MP.CUSTOMPMMOVPRODAX
    FROM EQMOVPROD MP
    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
    INTO :DDTMOVPRODAX, :NSALDOAX, :NCUSTOMPMAX;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPROD, null, null, null ) P
    INTO :NCUSTOPEPS;

    SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPRODAX, :ICODEMPAX, :SCODFILIALAX,
    :ICODALMOX ) P
    INTO :NCUSTOPEPSAX;

    select p.custoinfoprod from eqproduto p
    where p.codemp=:icodemp and p.codfilial=:scodfilial and p.codprod=:icodprod
    into :ncustoinfo;

    select custounit from eqcustoprodsp(:icodemp, :scodfilial, :icodprod,
    cast('today' as date),'U',:icodempax, :scodfilialax, :icodalmox, 'N' )
    into :ncustouc;

    if(:ncustompm is null) then
    begin
        ncustompm = :ncustoinfo;
    end

    if(:ncustopeps is null) then
    begin
        ncustopeps = :ncustoinfo;
    end

    suspend;

end
^

/* Alter (EQRELDEMANDASP) */
ALTER PROCEDURE EQRELDEMANDASP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
SCODFILIALPD SMALLINT,
DDTINI DATE,
DDTFIM DATE)
 RETURNS(CODMARCA CHAR(6),
CODGRUP CHAR(14),
CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
DESCGRUP CHAR(50),
SLDINI NUMERIC(15,5),
VLRCOMPRAS NUMERIC(15,5),
VLRDEVENT NUMERIC(15,5),
VLROUTENT NUMERIC(15,5),
VLRVENDAS NUMERIC(15,5),
VLRDEVSAI NUMERIC(15,5),
VLROUTSAI NUMERIC(15,5),
SLDFIM NUMERIC(15,5))
 AS
begin
  /* Stored procedure de relatório de demanda */
  FOR SELECT P.CODMARCA, P.CODGRUP,P.CODPROD,
     P.REFPROD, P.DESCPROD,G.DESCGRUP,
     (SELECT FIRST 1 SLDMOVPROD FROM EQMOVPROD MP
      WHERE MP.CODEMP=:ICODEMP AND MP.CODFILIAL=:SCODFILIAL AND
         MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
         MP.CODPROD=P.CODPROD AND MP.DTMOVPROD<:DDTINI
         ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC ) SLDINI,
     (SELECT SUM(MP.QTDMOVPROD)
         FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV IN ('CP','PC') AND TM.ESTIPOMOV = 'E') VLRCOMPRAS,
     (SELECT SUM(MP.QTDMOVPROD)
         FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV ='DV' AND TM.ESTIPOMOV = 'E') VLRDEVENT,
     (SELECT SUM(MP.QTDMOVPROD)
         FROM EQMOVPROD MP , EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV NOT IN ('CP','PC','DV') AND TM.ESTIPOMOV IN ('E','I')) VLROUTENT,
     (SELECT SUM(MP.QTDMOVPROD)
         FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV IN ('VD','PV') AND TM.ESTIPOMOV = 'S') VLRVENDAS,
     (SELECT SUM(MP.QTDMOVPROD)
         FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV ='DV' AND TM.ESTIPOMOV = 'S') VLRDEVSAI,
     (SELECT SUM(MP.QTDMOVPROD)
        FROM EQMOVPROD MP, EQTIPOMOV TM
         WHERE TM.CODEMP=MP.CODEMPTM AND TM.CODFILIAL=MP.CODFILIALTM AND
           TM.CODTIPOMOV=MP.CODTIPOMOV AND MP.CODPROD=P.CODPROD AND
           MP.CODEMPPD=P.CODEMP AND MP.CODFILIALPD=P.CODFILIAL AND
           MP.DTMOVPROD BETWEEN :DDTINI AND :DDTFIM AND MP.ESTOQMOVPROD='S' AND
           TM.TIPOMOV NOT IN ('VD','PV','DV') AND TM.ESTIPOMOV = 'S') VLROUTSAI
     FROM EQPRODUTO P, EQGRUPO G
         WHERE G.CODGRUP=P.CODGRUP AND G.CODEMP=P.CODEMPGP AND
           G.CODFILIAL=P.CODFILIALGP AND P.CODEMP=:ICODEMP AND
           P.CODFILIAL=:SCODFILIALPD AND P.ATIVOPROD='S'
     INTO :CODMARCA, :CODGRUP, :CODPROD, :REFPROD, :DESCPROD, :DESCGRUP,
        :SLDINI, :VLRCOMPRAS, :VLRDEVENT,  :VLROUTENT,
         :VLRVENDAS, :VLRDEVSAI, :VLROUTSAI DO
     BEGIN
        if (SLDINI IS NULL) then
           SLDINI = 0;
        if (VLROUTSAI IS NULL) then
           VLROUTSAI = 0;
        if (VLROUTENT IS NULL) then
           VLROUTENT = 0;
        if (VLRDEVSAI IS NULL) then
           VLRDEVSAI = 0;
        if (VLRDEVENT IS NULL) then
           VLRDEVENT = 0;
        if (VLRCOMPRAS IS NULL) then
           VLRCOMPRAS = 0;
        if (VLRVENDAS IS NULL) then
           VLRVENDAS = 0;
        SLDFIM = SLDINI + VLRCOMPRAS + VLRDEVENT + VLROUTENT -
           VLRVENDAS - VLRDEVSAI - VLROUTSAI;
        suspend;
     END
end
^

/* Alter (EQRELINVPRODSP) */
ALTER PROCEDURE EQRELINVPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
CTIPOCUSTO CHAR,
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
DDTESTOQ DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
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

/* Alter (EQRELPEPSSP) */
ALTER PROCEDURE EQRELPEPSSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP VARCHAR(14),
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CLOTEPROD CHAR,
SOPRODSALDO CHAR)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5),
CODBARPROD CHAR(13),
CODFABPROD CHAR(15),
ATIVOPROD CHAR,
CODUNID CHAR(20),
TIPOPROD VARCHAR(2),
CODNCM VARCHAR(10),
EXTIPI CHAR(2),
COD_GEN CHAR(2),
CODSERV CHAR(5),
ALIQ_ICMS NUMERIC(9,2),
CODNBM VARCHAR(12),
CODLOTE VARCHAR(20),
VENCTOLOTE DATE,
SLDLOTE NUMERIC(15,5),
CODGRUP VARCHAR(14),
SIGLAGRUP VARCHAR(10))
 AS
begin

  /* procedure text */

  if (icodempgp is not null) then
  begin
    if (strlen(rtrim(ccodgrup))<14) then
       ccodgrup = rtrim(ccodgrup)||'%';
  end

  if (ctipocusto is null) then
     ctipocusto = 'P';

  if (:cloteprod = 'S') then
  begin
        for select p.codprod,p.refprod,p.descprod, p.codbarprod
            , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
            , fc.codncm, fc.extipi
            , substring(fc.codncm from 1 for 2) cod_gen
            , fc.codserv,
            (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
            ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
            , fc.codnbm, lt.codlote, lt.venctolote, lt.sldlote, p.codgrup, gp.siglagrup
        from eqproduto p
        left outer join lfclfiscal fc
            on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
        left outer join eqlote lt
            on lt.codemp=p.codemp and lt.codfilial=p.codfilial and lt.codprod=p.codprod
        left outer join eqgrupo gp
            on gp.codemp=p.codemp and gp.codfilial=p.codfilial and gp.codgrup=p.codgrup

        where p.codemp = :icodemp and p.codfilial = :scodfilial and
            ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
             p.codmarca=:ccodmarca) ) and
            ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
             p.codgrup like :ccodgrup) )
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm, :codlote, :venctolote, :sldlote, :codgrup, :siglagrup do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  :sldprod <> 0) then
                suspend;
        end
  end
  else
  begin
        for select p.codprod,p.refprod,p.descprod, p.codbarprod
            , p.codfabprod, p.ativoprod, p.codunid, p.tipoprod
            , fc.codncm, fc.extipi
            , substring(fc.codncm from 1 for 2) cod_gen
            , fc.codserv,
            (select first 1 ifc.aliqfisc from lfitclfiscal ifc where ifc.codemp=fc.codemp and ifc.codfilial=fc.codfilial and
            ifc.geralfisc='S' and ifc.noufitfisc='S') aliq_icms
            , fc.codnbm
        from eqproduto p
        left outer join lfclfiscal fc
            on fc.codemp=p.codempfc and fc.codfilial=p.codfilialfc and fc.codfisc=p.codfisc
        where p.codemp = :icodemp and p.codfilial = :scodfilial and
            ( (:icodempmc is null) or (p.codempmc=:icodempmc and p.codfilialmc=:scodfilialmc and
             p.codmarca=:ccodmarca) ) and
            ((:icodempgp is null) or (p.codempgp=:icodempgp and p.codfilialgp=:scodfilialgp and
             p.codgrup like :ccodgrup) )
        order by p.codprod
    
        into :codprod, :refprod, :descprod, :codbarprod, :codfabprod
            , :ativoprod, :codunid, :tipoprod
            , :codncm, :extipi, :cod_gen, :codserv, :aliq_icms, :codnbm do
    
        begin
            select sldprod, custounit, custotot from eqcustoprodsp(:icodemp,
                 :scodfilial, :codprod, :dtestoq, :ctipocusto, :icodempax,
                 :scodfilialax, :icodalmox, 'S')
            into :sldprod, :custounit, :custotot;
            if (:soprodsaldo='N' or  sldprod <> 0) then
                suspend;
        end
  end 


end
^

/* empty dependent procedure body */
/* Clear: EQMOVPRODATEQSP for: EQSALDOLOTEATEQSP */
ALTER PROCEDURE EQMOVPRODATEQSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLEOLD INTEGER,
SCODFILIALLEOLD SMALLINT,
CCODLOTEOLD VARCHAR(20),
ICODEMPLENEW INTEGER,
SCODFILIALLENEW SMALLINT,
CCODLOTENEW VARCHAR(20),
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter (EQSALDOLOTEATEQSP) */
ALTER PROCEDURE EQSALDOLOTEATEQSP(ICODEMPLEOLD INTEGER,
SCODFILIALLEOLD SMALLINT,
ICODPROD INTEGER,
CCODLOTEOLD VARCHAR(20),
ICODEMPLENEW INTEGER,
SCODFILIALLENEW SMALLINT,
CCODLOTENEW VARCHAR(20),
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
DECLARE VARIABLE ICODPRODSLD INTEGER;
begin
  /* Procedure de atualização do estoque  na tabela de saldos de lotes por almoxarifado*/

  SELECT CODPROD FROM EQSALDOLOTE
     WHERE CODEMP=:ICODEMPLENEW AND CODFILIAL=:SCODFILIALLENEW AND CODPROD=:ICODPROD AND
        CODLOTE=:CCODLOTENEW AND CODEMPAX=:ICODEMPAXNEW AND 
        CODFILIALAX=:SCODFILIALAXNEW AND CODALMOX=:ICODALMOXNEW
     INTO :ICODPRODSLD;
  IF (ICODPRODSLD IS NULL) THEN
  BEGIN
     INSERT INTO EQSALDOLOTE (CODEMP, CODFILIAL, CODPROD, CODLOTE, CODEMPAX, CODFILIALAX, CODALMOX)
       VALUES (:ICODEMPLENEW, :SCODFILIALLENEW, :ICODPROD, :CCODLOTENEW, :ICODEMPAXNEW, :SCODFILIALAXNEW,
         :ICODALMOXNEW);
     NQTDMOVPRODOLD = 0;
  END

  IF ( (ICODALMOXOLD IS NULL) OR (CCODLOTEOLD IS NULL) ) THEN
    NQTDMOVPRODOLD = 0;
  ELSE IF ( (ICODEMPAXOLD!=ICODEMPAXNEW) OR (SCODFILIALAXOLD!=SCODFILIALAXNEW) OR
            (ICODALMOXOLD!=ICODALMOXNEW) OR (CCODLOTEOLD!=CCODLOTENEW) OR
            (ICODEMPLEOLD!=ICODEMPLENEW) OR (SCODFILIALLEOLD!=SCODFILIALLENEW) ) THEN
  BEGIN
      IF ((CCODLOTEOLD!=CCODLOTENEW) OR (ICODEMPLEOLD!=ICODEMPLENEW) OR
          (SCODFILIALLEOLD!=SCODFILIALLENEW) ) THEN
      BEGIN
         UPDATE EQLOTE SET SLDLOTE = SLDLOTE+((0-:NQTDMOVPRODOLD)*:SOPERADOR)
            WHERE CODLOTE=:CCODLOTEOLD AND CODPROD=:ICODPROD
            AND CODEMP=:ICODEMPLEOLD AND CODFILIAL=:SCODFILIALLEOLD;
      END
      UPDATE EQSALDOLOTE SET SLDLOTE = SLDLOTE+((0-:NQTDMOVPRODOLD)*:SOPERADOR)
         WHERE CODPROD=:ICODPROD AND CODLOTE=:CCODLOTEOLD AND CODALMOX = :ICODALMOXOLD
           AND CODEMPAX = :ICODEMPAXOLD AND CODFILIALAX = :SCODFILIALAXOLD
           AND CODEMP=:ICODEMPLEOLD AND CODFILIAL=:SCODFILIALLEOLD; 
      NQTDMOVPRODOLD=0;
  END
  UPDATE EQLOTE SET SLDLOTE = SLDLOTE+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
        WHERE CODLOTE=:CCODLOTENEW AND CODPROD=:ICODPROD
         AND CODEMP=:ICODEMPLENEW AND CODFILIAL=:SCODFILIALLENEW;
  UPDATE EQSALDOLOTE SET SLDLOTE = SLDLOTE+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
         WHERE CODPROD=:ICODPROD AND CODLOTE=:CCODLOTENEW AND CODALMOX = :ICODALMOXNEW
           AND CODEMPAX = :ICODEMPAXNEW AND CODFILIALAX = :SCODFILIALAXNEW
           AND CODEMP=:ICODEMPLENEW AND CODFILIAL=:SCODFILIALLENEW;
  suspend;
end
^

/* Alter (EQSALDOPRODATEQSP) */
ALTER PROCEDURE EQSALDOPRODATEQSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
DECLARE VARIABLE ICODPRODSLD INTEGER;
begin
  /* Procedure de atualização do estoque  na tabela de saldos por almoxarifado*/
  SELECT CODPROD FROM EQSALDOPROD
     WHERE CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD AND CODPROD=:ICODPROD AND
        CODEMPAX=:ICODEMPAXNEW AND CODFILIALAX=:SCODFILIALAXNEW AND CODALMOX=:ICODALMOXNEW
     INTO :ICODPRODSLD;
  IF (ICODPRODSLD IS NULL) THEN
  BEGIN
     INSERT INTO EQSALDOPROD (CODEMP, CODFILIAL, CODPROD, CODEMPAX, CODFILIALAX, CODALMOX)
       VALUES (:ICODEMPPD, :SCODFILIALPD, :ICODPROD, :ICODEMPAXNEW, :SCODFILIALAXNEW,
         :ICODALMOXNEW);
     NQTDMOVPRODOLD = 0;
  END

  IF (ICODALMOXOLD IS NULL) THEN
    NQTDMOVPRODOLD = 0;
  ELSE IF ( (ICODEMPAXOLD!=ICODEMPAXNEW) OR (SCODFILIALAXOLD!=SCODFILIALAXNEW) OR
            (ICODALMOXOLD!=ICODALMOXNEW) ) THEN
  BEGIN
      UPDATE EQSALDOPROD SET SLDPROD = SLDPROD+((0-:NQTDMOVPRODOLD)*:SOPERADOR)
         WHERE CODPROD=:ICODPROD AND CODALMOX = :ICODALMOXOLD
           AND CODEMPAX = :ICODEMPAXOLD AND CODFILIALAX = :SCODFILIALAXOLD
           AND CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD;
      NQTDMOVPRODOLD=0;
  END
  UPDATE EQSALDOPROD SET SLDPROD = SLDPROD+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
         WHERE CODPROD=:ICODPROD AND CODALMOX = :ICODALMOXNEW
           AND CODEMPAX = :ICODEMPAXNEW AND CODFILIALAX = :SCODFILIALAXNEW
           AND CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD;
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: FNGERAITRECEBERSP01 for: FNADICITRECEBERSP01 */
ALTER PROCEDURE FNGERAITRECEBERSP01(CALTVLR CHAR,
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
ICODEMPPG INTEGER,
SCODFILIALPG SMALLINT,
ICODPLANOPAG INTEGER,
NVLRREC NUMERIC(15,5),
DDATAREC DATE,
DDTCOMPREC DATE,
CFLAG CHAR,
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(3),
NVLRCOMIREC NUMERIC(15,5),
OBSREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC INTEGER,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 RETURNS(I INTEGER)
 AS
 BEGIN SUSPEND; END
^

/* Alter (FNADICITRECEBERSP01) */
ALTER PROCEDURE FNADICITRECEBERSP01(CALTVLR CHAR,
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRDESCITREC NUMERIC(15,5),
NVLRMULTAITREC NUMERIC(15,5),
NVLRJUROSITREC NUMERIC(15,5),
NVLRPARCITREC NUMERIC(15,5),
NVLRPAGOITREC NUMERIC(15,5),
DDTITREC DATE,
DDTCOMPITREC DATE,
DDTVENCITREC DATE,
CSTATUSITREC CHAR(2),
CFLAG CHAR,
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(3),
NVLRCOMIITREC NUMERIC(15,5),
OBSITREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 AS
declare variable inparcitrecold integer;
declare variable doclancaitrec varchar(15);
begin
   SELECT ir.doclancaitrec,ir.NPARCITREC FROM FNITRECEBER IR WHERE IR.CODEMP=:ICODEMP AND
     IR.CODFILIAL=:SCODFILIAL AND IR.CODREC=:ICODREC AND
     IR.NPARCITREC=:INPARCITREC INTO :doclancaitrec, :INPARCITRECOLD;

   IF (INPARCITRECOLD IS NULL) THEN
     /* Faz inserção na tabela de parcelas do contas a receber */
      INSERT INTO FNITRECEBER(CODEMP,CODFILIAL,CODREC,NPARCITREC,VLRDESCITREC,VLRMULTAITREC,
              VLRJUROSITREC,VLRPARCITREC,VLRPAGOITREC,DTITREC, DTCOMPITREC, DTVENCITREC,
              STATUSITREC,FLAG,CODEMPBO,CODFILIALBO,CODBANCO,
              CODEMPTC, CODFILIALTC, CODTIPOCOB,
              CODEMPCB, CODFILIALCB, CODCARTCOB, VLRCOMIITREC, OBSITREC, CODEMPCA,CODFILIALCA,NUMCONTA,dtprevitrec,
              codemppn, codfilialpn, codplan, codempcc, codfilialcc, anocc, codcc, vlrbasecomis, doclancaitrec)
              VALUES
             (:ICODEMP,:SCODFILIAL,:ICODREC,:INPARCITREC,:NVLRDESCITREC,:NVLRMULTAITREC,
             :NVLRJUROSITREC,:NVLRPARCITREC,:NVLRPAGOITREC, :DDTITREC, :DDTCOMPITREC, :DDTVENCITREC,
             :CSTATUSITREC,:CFLAG,:ICODEMPBO,:SCODFILIALBO,:CCODBANCO,
             :ICODEMPTC, :SCODFILIALTC, :ICODTIPOCOB,
             :ICODEMPCB, :SCODFILIALCB, :CODCARTCOB, :NVLRCOMIITREC, :OBSITREC, :codempca, :codfilialca, :numconta,:ddtvencitrec,
             :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc,:vlrbasecomis,:doclancaitrec );
   ELSE
   BEGIN
       SELECT VLRDESCITREC, VLRMULTAITREC, VLRJUROSITREC, VLRPAGOITREC, STATUSITREC
       FROM FNITRECEBER
       WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
            NPARCITREC=:INPARCITREC
       INTO :NVLRDESCITREC, :NVLRMULTAITREC, :NVLRJUROSITREC, :NVLRPAGOITREC, :CSTATUSITREC;

       IF (CALTVLR='S') THEN /* Flag que indica se é para mudar os valores do contas a receber */
       BEGIN

           UPDATE FNITRECEBER SET ALTUSUITREC='N',VLRDESCITREC=:NVLRDESCITREC,VLRMULTAITREC=:NVLRMULTAITREC,
                  VLRJUROSITREC=:NVLRJUROSITREC,VLRPARCITREC=:NVLRPARCITREC,
                  VLRPAGOITREC=:NVLRPAGOITREC,DTITREC=:DDTITREC,STATUSITREC=:CSTATUSITREC,
                  FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,CODFILIALBO=:SCODFILIALBO,
                  CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC, CODFILIALTC=:SCODFILIALTC, 
                  CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, CODEMPCA=:codempca, codfilialca=:codfilialca, numconta=:numconta,
                  vlrbasecomis=:vlrbasecomis, obsitrec=:obsitrec
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
       END
       ELSE
           UPDATE FNITRECEBER SET ALTUSUITREC='N',DTITREC=:DDTITREC,
                  STATUSITREC=:CSTATUSITREC, FLAG=:CFLAG,CODEMPBO=:ICODEMPBO,
                  CODFILIALBO=:SCODFILIALBO,CODBANCO=:CCODBANCO,CODEMPTC=:ICODEMPTC,
                  CODFILIALTC=:SCODFILIALTC, CODTIPOCOB=:ICODTIPOCOB,
                  CODEMPCB=:ICODEMPCB, CODFILIALCB=:SCODFILIALCB, CODCARTCOB=:CODCARTCOB,
                  VLRCOMIITREC=:NVLRCOMIITREC, obsitrec=:obsitrec
            WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND CODREC=:ICODREC AND
                NPARCITREC=:INPARCITREC;
  END
 -- suspend;
end
^

/* Alter (FNADICLANCASP01) */
ALTER PROCEDURE FNADICLANCASP01(ICODREC INTEGER,
INPARCITREC INTEGER,
PDVITREC CHAR,
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODCLI INTEGER,
ICODEMPCL INTEGER,
ICODFILIALCL INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITREC DATE,
DDTPAGOITREC DATE,
SDOCLANCAITREC VARCHAR(15),
SOBSITREC CHAR(250),
DVLRPAGOITREC NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRPAGOJUROS NUMERIC(15,5),
DVLRDESC NUMERIC(15,5),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR SMALLINT)
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjr integer;
declare variable codfilialjr smallint;
declare variable codplanjr char(13);
declare variable codempdc integer;
declare variable codfilialdc smallint;
declare variable codplandc char(13);
declare variable codsublanca smallint = 1;
BEGIN
    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA') INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMPPN,CODFILIALPN FROM FNCONTA WHERE NUMCONTA = :SNUMCONTA
        AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALLANCA,'LA') INTO ICODLANCA;

    SELECT FLAG FROM FNRECEBER WHERE CODREC=:ICODREC
        AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL INTO :cFlag;

    SELECT CODEMPJR,CODFILIALJR,CODPLANJR,CODEMPDC,CODFILIALDC,CODPLANDC FROM SGPREFERE1
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
        INTO :CODEMPJR,:CODFILIALJR,:CODPLANJR,:CODEMPDC,:CODFILIALDC,:CODPLANDC;


    IF (ICODCLI IS NULL) THEN
        TIPOLANCA = 'A';
    ELSE
        TIPOLANCA = 'C';

    if ( (DVLRPAGOJUROS IS NULL) OR (:CODPLANJR IS NULL)  ) then
        DVLRPAGOJUROS = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC - DVLRPAGOJUROS;

    if (DVLRDESC IS NULL  OR (:CODPLANDC IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITREC = DVLRPAGOITREC + DVLRDESC;


    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPRC,CODFILIALRC,CODREC,NPARCITREC,CODEMPPN,CODFILIALPN,CODPLAN, 
        DTCOMPLANCA, DATALANCA, DOCLANCA,HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG,CODEMPCL,CODFILIALCL,CODCLI,PDVITREC)
        VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:ICODREC,:iNParcItRec,:iCodEmpPConta,:iCodFilialPConta,
                :sCodPlanConta,:dDtCompItRec, :dDtPagoItRec,:sDocLancaItRec,:sObsItRec,:dDtPagoItRec,0,:cFlag,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:PDVITREC
        );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA, DTCOMPSUBLANCA, DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:ICODEMPPN,:ICODFILIALPN,:SCODPLAN,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:dVlrPagoItRec*-1,:cFlag, :codempct, :codfilialct, :codcontr, :coditcontr
        );

    -- Lançamento dos juros em conta distinta.

    IF(DVLRPAGOJUROS > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
                 CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
                  CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA, CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPJR,:CODFILIALJR,:CODPLANJR,
                :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
                :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRPAGOJUROS*-1,:cFlag, 'J', :codempct, :codfilialct, :codcontr, :coditcontr
        );

    END

    -- Lançamento do desconto em conta distinta.

    IF(DVLRDESC > 0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,
             CODEMPRC, CODFILIALRC, CODREC, NPARCITREC,
             CODEMPCC, CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG, TIPOSUBLANCA
             ,CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPCL,:ICODFILIALCL,:ICODCLI,:CODEMPDC,:CODFILIALDC,:CODPLANDC,
               :ICODEMP, :ICODFILIAL, :ICODREC, :INPARCITREC,
             :ICODEMPCC, :ICODFILIALCC,:IANOCC,:SCODCC,'S',:dDtCompItRec,:dDtPagoItRec,:dDtPagoItRec,:DVLRDESC,:cFlag, 'D'
             , :codempct, :codfilialct, :codcontr, :coditcontr
        );

    END


END
^

/* Alter (FNADICLANCASP02) */
ALTER PROCEDURE FNADICLANCASP02(ICODPAG INTEGER,
INPARCPAG INTEGER,
SNUMCONTA CHAR(10),
ICODEMPCA INTEGER,
ICODFILIALCA INTEGER,
ICODFOR INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR INTEGER,
SCODPLAN CHAR(13),
ICODEMPPN INTEGER,
ICODFILIALPN INTEGER,
IANOCC INTEGER,
SCODCC CHAR(19),
ICODEMPCC INTEGER,
ICODFILIALCC INTEGER,
DDTCOMPITPAG DATE,
DDTPAGOITPAG DATE,
SDOCLANCAITPAG VARCHAR(15),
SOBSITPAG CHAR(250),
DVLRPAGOITPAG NUMERIC(15,5),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
DVLRJUROSPAG NUMERIC(15,5),
DVLRDESC NUMERIC(15,5),
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
CODCONTR INTEGER,
CODITCONTR SMALLINT)
 AS
declare variable icodlanca integer;
declare variable scodplanconta char(13);
declare variable icodemppconta integer;
declare variable icodfilialpconta integer;
declare variable cflag char(1);
declare variable ifiliallanca integer;
declare variable tipolanca char(1);
declare variable codempjp integer;
declare variable codfilialjp smallint;
declare variable codplanjp char(13);
declare variable codempdr integer;
declare variable codfilialdr smallint;
declare variable codplandr char(13);
declare variable codsublanca smallint = 1;
BEGIN

    SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNLANCA')
    INTO IFILIALLANCA;

    SELECT CODPLAN,CODEMP,CODFILIAL FROM FNCONTA
    WHERE NUMCONTA=:sNumConta AND CODEMP=:ICODEMPCA AND CODFILIAL=:ICODFILIALCA
    INTO :sCodPlanConta,:iCodEmpPConta,:iCodFilialPConta;

    SELECT ISEQ FROM SPGERANUM(:iCODEMP,:IFILIALLANCA,'LA')
    INTO :iCodLanca;

    SELECT FLAG FROM FNPAGAR
    WHERE CODPAG=:iCodPag AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :cFlag;

    SELECT CODEMPJP,CODFILIALJP,CODPLANJP,CODEMPDR,CODFILIALDR,CODPLANDR
    FROM SGPREFERE1
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
    INTO :CODEMPJP,:CODFILIALJP,:CODPLANJP,:CODEMPDR,:CODFILIALDR,:CODPLANDR;

    IF (ICODFOR IS NULL) THEN
        TIPOLANCA = 'A';
      ELSE
        TIPOLANCA = 'F';

    if ( (DVLRJUROSPAG IS NULL) OR (:CODPLANJP IS NULL)  ) then
        DVLRJUROSPAG = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG - DVLRJUROSPAG;

    if (DVLRDESC IS NULL  OR (:CODPLANDR IS NULL) ) then
        DVLRDESC = 0;
    else
        DVLRPAGOITPAG = DVLRPAGOITPAG + DVLRDESC;

    INSERT INTO FNLANCA (TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,CODEMPPG,CODFILIALPG,CODPAG,
                         NPARCPAG,CODEMPPN,CODFILIALPN,CODPLAN,DTCOMPLANCA,DATALANCA,DOCLANCA,
                         HISTBLANCA,DTPREVLANCA,VLRLANCA,FLAG, CODEMPFR, CODFILIALFR, CODFOR)
         VALUES (:TIPOLANCA,:ICODEMP,:IFILIALLANCA,:iCodLanca,:ICODEMP,:ICODFILIAL,:iCodPag,
                 :iNParcPag,:iCodEmpPConta,:iCodFilialPConta,:sCodPlanConta, :dDtCompItPag, :dDtPagoItPag,:sDocLancaItPag,
                 :sObsItPag,:dDtPagoItPag,0,:cFlag, :ICODEMPFR, :ICODFILIALFR, :ICODFOR
         );

    INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,CODEMPPN,CODFILIALPN, CODPLAN,
        CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG,
        CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,FLAG,
        CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR)
        VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,:ICODEMPPN,:ICODFILIALPN, :sCodplan,
        :ICODEMP,:ICODFILIAL,:iCodPag, :iNParcPag,
        :ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:dVlrPagoItPag,:cFlag,
        :codempct, :codfilialct, :codcontr, :coditcontr);

    -- Lançamento dos juros em conta distinta.

    IF(DVLRJUROSPAG >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
             CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
             CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA
             , CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR
             )
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
            :CODEMPJP,:CODFILIALJP,:CODPLANJP,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRJUROSPAG,
            :ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag,:cFlag, 'J'
            , :codempct, :codfilialct, :codcontr, :coditcontr
            );

    END

    -- Lançamento de desconto em conta distinta.

    IF(DVLRDESC >0) THEN
    BEGIN

        CODSUBLANCA = CODSUBLANCA + 1;

        INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPFR,CODFILIALFR,CODFOR,
        CODEMPPN,CODFILIALPN,CODPLAN,CODEMPCC,CODFILIALCC,ANOCC,CODCC,ORIGSUBLANCA,DTCOMPSUBLANCA,DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA,
              CODEMPPG, CODFILIALPG, CODPAG, NPARCPAG, FLAG, TIPOSUBLANCA
              , CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR)
            VALUES (:ICODEMP,:IFILIALLANCA,:iCodLanca,:CODSUBLANCA,:ICODEMPFR,:ICODFILIALFR,:ICODFOR,
             :CODEMPDR,:CODFILIALDR,:CODPLANDR,:ICODEMPCC,:ICODFILIALCC,:IANOCC,:SCODCC,'E',:dDtCompItPag,:dDtPagoItPag,:dDtPagoItPag,:DVLRDESC*-1
             ,:ICODEMP, :ICODFILIAL, :iCodPag, :iNParcPag, :cFlag, 'D'
             ,:codempct, :codfilialct, :codcontr, :coditcontr
             );

    END

END
^

/* Alter (FNADICPAGARSP02) */
ALTER PROCEDURE FNADICPAGARSP02(CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORDCP INTEGER,
CODEMPPP INTEGER,
CODFILIALPP SMALLINT,
CODPLANOPAG INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
OBSPAG VARCHAR(2000))
 AS
declare variable codpag integer;
declare variable codfilialpag integer;
declare variable numparcs integer;
declare variable valor decimal(15,5);
declare variable data date;

BEGIN     
    
    -- Buscando filial para tabela FNPAGAR
    select icodfilial from sgretfilial( :codemppp, 'FNPAGAR' ) into :codfilialpag;

    -- Gerando código do pagamento
    select iseq from spgeranum( :codemppp, :codfilialpag, 'PA') into :codpag;

    -- Consultando número de parcelas do plano de pagamento
    select coalesce( parcplanopag, 0 ) from fnplanopag
        where codemp=:codemppp and codfilial=:codfilialpp and codplanopag=:codplanopag
        into :numparcs;

    -- Limpando empenhos anteriores
    delete from fnpagar pg where pg.codempoc=:codempoc and pg.codfilialoc=:codfilialoc and pg.codordcp=:codordcp;

    -- Precorre tabela de previsões de entrega para gerar os empenhos no contas a pagar.
    for select pe.dtitpe data, sum(( io.vlrliqapitordcp/io.qtdapitordcp ) * cast(pe.qtditpe-pe.qtditentpe as decimal(8,2))) valor
        from cpitordcompra io, cpitordcomprape pe
        where
            io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codordcp=:codordcp and
            pe.codemp=io.codemp and pe.codfilial=io.codfilial and pe.codordcp=io.codordcp and pe.coditordcp=io.coditordcp and
            (pe.qtditpe-pe.qtditentpe) >0
        group by pe.dtitpe
        order by pe.dtitpe
        into data, :valor

        do begin
                    
            -- Se existirem parcelas a gerar.

            if( numparcs>0 ) then
            begin

                insert into fnpagar (
                    codemp, codfilial, codpag,
                    codemppg, codfilialpg, codplanopag,
                    codempfr, codfilialfr, codfor,
                    codempoc, codfilialoc, codordcp,
                    vlrpag, vlrparcpag, vlrapagpag,
                    datapag, dtcomppag, statuspag, docpag, obspag )
                values (
                    :codempoc, :codfilialpag, :codpag,
                    :codemppp, :codfilialpp, :codplanopag,
                    :codempfr, :codfilialfr, :codfor,
                    :codempoc, :codfilialoc, :codordcp,
                    :valor, :valor, :valor,
                    :data, :data, 'P1', :codordcp, substring(:obspag from 1 for 250)
                );

                 -- Gerando código do pagamento
                 select iseq from spgeranum( :codemppp, :codfilialpag, 'PA') into :codpag;

            end
     end

END
^

/* Alter (FNADICRECEBERSP01) */
ALTER PROCEDURE FNADICRECEBERSP01(TIPOVENDA CHAR,
CODVENDA INTEGER,
CODEMPTC INTEGER,
CODFILIALTC INTEGER,
CODTIPOCOB INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVEND INTEGER,
VLRLIQVENDA NUMERIC(15,5),
DTVENDA DATE,
DTCOMP DATE,
VLRCOMISVENDA NUMERIC(15,5),
DOCVENDA INTEGER,
CODEMPBO INTEGER,
CODFILIALBO SMALLINT,
CODBANCO CHAR(3),
CODEMP INTEGER,
CODFILIAL SMALLINT,
CODEMPCB INTEGER,
CODFILIALCB SMALLINT,
CODCARTCOB CHAR(3),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
FLAG CHAR,
OBSREC VARCHAR(250),
VLRBASECOMIS NUMERIC(15,5),
VLRRETENSAOISS NUMERIC(15,5))
 AS
declare variable codrec integer;
declare variable codfilialrc smallint;
declare variable parcplanopag integer;
BEGIN
  SELECT R.CODREC,R.CODFILIAL FROM FNRECEBER R
     WHERE R.CODEMPVA=:CODEMP AND R.CODFILIALVA=:CODFILIAL AND
       R.TIPOVENDA=:TIPOVENDA AND R.CODVENDA=:CODVENDA
     INTO :CODREC,:CODFILIALRC;
  SELECT PARCPLANOPAG FROM FNPLANOPAG WHERE CODEMP=:CODEMPPG AND
     CODFILIAL=:CODFILIALPG AND CODPLANOPAG=:CODPLANOPAG
     INTO :PARCPLANOPAG;

  IF ( (CODREC IS NULL) AND (PARCPLANOPAG>0) ) THEN
  BEGIN
     SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'FNRECEBER') INTO :CODFILIALRC;
     SELECT ISEQ FROM SPGERANUM(:CODEMP,:CODFILIALRC,'RC') INTO :CODREC;

     -- Caso haja retensão de ISS deve
     if(coalesce(:vlrretensaoiss,0)>0) then
     begin
        vlrliqvenda = vlrliqvenda - vlrretensaoiss;
     end

     INSERT INTO FNRECEBER (CODEMP,CODFILIAL,CODREC, CODTIPOCOB, CODEMPTC, CODFILIALTC,
              CODPLANOPAG,CODEMPPG,CODFILIALPG,CODCLI,
              CODEMPCL,CODFILIALCL,CODVEND,CODEMPVD,CODFILIALVD,TIPOVENDA,CODVENDA,
              CODEMPVA,CODFILIALVA,VLRREC,
              VLRDESCREC,VLRMULTAREC,VLRJUROSREC,VLRPARCREC,VLRPAGOREC,
              VLRAPAGREC,DATAREC, DTCOMPREC, STATUSREC,VLRCOMIREC,DOCREC,CODBANCO,CODEMPBO,CODFILIALBO,
              CODEMPCB, CODFILIALCB, CODCARTCOB, CODEMPCA, CODFILIALCA, NUMCONTA,
              FLAG, OBSREC, vlrbasecomis)
              VALUES (
                     :CODEMP, :CODFILIALRC, :CODREC, :CODTIPOCOB, :CODEMPTC, :CODFILIALTC,
                     :CodPlanoPag, :CODEMPPG, :CODFILIALPG, :CodCli,
                     :CODEMPCL, :CODFILIALCL, :CodVend, :CODEMPVD, :CODFILIALVD, :TIPOVENDA, :CodVenda,
                     :CODEMP, :CODFILIAL, :VlrLiqVenda,
                     0,0,0,:VlrLiqVenda,0,:VlrLiqVenda,:dtVenda, :dtComp, 'R1',
                     :VlrComisVenda,:DocVenda,:CodBanco,:CODEMPBO,:CODFILIALBO,
                     :CODEMPCB, :CODFILIALCB, :CODCARTCOB, 
                     :CODEMPCA, :CODFILIALCA, :NUMCONTA,
                     :FLAG, :OBSREC,:vlrbasecomis
              );
  END
  ELSE IF (CODREC IS NOT NULL) THEN
  BEGIN
    IF (PARCPLANOPAG>0) THEN
    BEGIN
        UPDATE FNRECEBER SET ALTUSUREC='N',
              CODTIPOCOB=:CODTIPOCOB, CODEMPTC=:CODEMPTC, CODFILIALTC=:CODFILIALTC,
              CODPLANOPAG=:CodPlanoPag, CODEMPPG=:CODEMPPG, CODFILIALPG=:CODFILIALPG,
              CODCLI=:CodCli, CODEMPCL=:CODEMPCL, CODFILIALCL=:CODFILIALCL,
              CODVEND=:CodVend, CODEMPVD=:CODEMPVD, CODFILIALVD=:CODFILIALVD,
              VLRREC=:VlrLiqVenda, VLRDESCREC=0, VLRMULTAREC=0, VLRJUROSREC=0,
              VLRPARCREC=:VlrLiqVenda, VLRPAGOREC=0, VLRAPAGREC=:VlrLiqVenda,
              DATAREC=:dtVenda, VLRCOMIREC=:VlrComisVenda,
              /* STATUSREC='R1' */
              DOCREC=:DocVenda, 
              CODEMPBO=:CODEMPBO, CODFILIALBO=:CODFILIALBO, CODBANCO=:CODBANCO,
              CODEMPCB=:CODEMPCB, CODFILIALCB=:CODFILIALCB, CODCARTCOB=:CODCARTCOB,
              CODEMPCA=:CODEMPCA, CODFILIALCA=:CODFILIALCA, NUMCONTA=:NUMCONTA,
              FLAG=:FLAG, vlrbasecomis=:vlrbasecomis
             WHERE CODREC=:CODREC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALRC;

        UPDATE FNITRECEBER SET ALTUSUITREC='S', /* Atualiza os itens de contas a */
         /* receber para ajustar automaticamente os valores no cabeçalho */
              CODEMPBO=:CODEMPBO, CODFILIALBO=:CODFILIALBO, CODBANCO=:CODBANCO,
              CODEMPCB=:CODEMPCB, CODFILIALCB=:CODFILIALCB, CODCARTCOB=:CODCARTCOB,
              CODEMPCA=:CODEMPCA, CODFILIALCA=:CODFILIALCA, NUMCONTA=:NUMCONTA
             WHERE CODREC=:CODREC AND CODEMP=:CODEMP AND CODFILIAL=:CODFILIALRC;
     END
     ELSE
     BEGIN
         DELETE FROM FNRECEBER WHERE CODREC=:CODREC AND CODEMP=:CODEMP AND
            CODFILIAL=:CODFILIALRC;
     END
   END

END
^

/* Alter (FNESTORNACOMISSAOSP) */
ALTER PROCEDURE FNESTORNACOMISSAOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODREC INTEGER,
NPARCITREC SMALLINT)
 AS
declare variable vlrvendacomi numeric(15,5);
declare variable vlrcomi numeric(15,5);
declare variable datacomi date;
declare variable dtcompcomi date;
declare variable dtvenccomi date;
declare variable tipocomi char(1);
declare variable statuscomi char(2);
declare variable dtatual date;
declare variable dtvencitrec date;
declare variable codempvd integer;
declare variable codfilialvd smallint;
declare variable codvend integer;
begin
  /* Procedure Text */
  dtatual = cast( 'now' as date);

  select first 1 c.statuscomi, c.tipocomi, c.codempvd, c.codfilialvd, c.codvend
  , c.vlrvendacomi, c.vlrcomi, c.datacomi, c.dtcompcomi, ir.dtvencitrec , c.dtvenccomi
  from vdcomissao c, fnitreceber ir
  where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec and tipocomi='R'
  and ir.codemp=c.codemprc and ir.codfilial=c.codfilialrc and ir.codrec=c.codrec and ir.nparcitrec=c.nparcitrec
  and c.statuscomi<>'CE'
  order by c.codcomi desc
  into :statuscomi, :tipocomi, :codempvd, :codfilialvd,  :codvend
  , :vlrvendacomi, :vlrcomi, :datacomi, :dtcompcomi, :dtvencitrec, :dtvenccomi;

  if (statuscomi not in ('CP') ) then
  begin
      update vdcomissao c set c.statuscomi='C1'
      where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec
      and tipocomi='R' and statuscomi not in ('CP','CE');
  end
  else if (statuscomi in ('CP') ) then
  begin
      vlrcomi = vlrcomi * -1; /* Transforma o valor da comissão em negativo */
      /* para gerar estorno */
      execute procedure vdadiccomissaosp(:codemp,:codfilial,:codrec,
         :nparcitrec, :vlrvendacomi, :vlrcomi, :datacomi , :dtcompcomi, :dtvenccomi,
         :tipocomi, :codempvd, :codfilialvd, : codvend );

     -- execute vdadiccomissaosp
  end

  /*UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
    */
--  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
--      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTCOMPCOMI, C.DTVENCCOMI,
--      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
--    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
--    WHERE C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND
--       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
--       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
--       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
--       R.CODREC=C.CODREC
       --AND R.CODEMPVD=:CODEMPVD
       --AND
       --R.CODFILIALVD=:CODFILIALVD
       --AND R.CODVEND=:CODVEND AND
       --and ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       --( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
--       and C.STATUSCOMI IN ('C2','CP') AND
--       IR.STATUSITREC NOT IN ('RP') AND
--       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
--         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
--         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
--         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
--    INTO :CODCOMI, :CODEMP, :CODFILIAL, :CODREC, :NPARCITREC, :VLRVENDACOMI,
--      :VLRCOMI, :DATACOMI, :DTCOMPCOMI, :DTVENCCOMI, :TIPOCOMI, :STATUSCOMI, :STATUSITREC,
--      :DTVENCITREC
--  DO
--  BEGIN
--     IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
--     BEGIN
--        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--          WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
--            CODCOMI=:CODCOMI;
--     END
--     ELSE IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
--     BEGIN
--        VLRCOMI = VLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
     --   EXECUTE PROCEDURE vdadiccomissaosp(:CODEMP,:CODFILIAL,:CODREC,
       --   :NPARCITREC, :VLRVENDACOMI, :VLRCOMI, :DATACOMI , :DTCOMPCOMI, :DTVENCITREC,
         -- :TIPOCOMI, :codempvd, :codfilialvd, : codvend );
--     END
--  END
 -- suspend;
end
^

/* Alter (FNGERAITRECEBERSP01) */
ALTER PROCEDURE FNGERAITRECEBERSP01(CALTVLR CHAR,
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
ICODEMPPG INTEGER,
SCODFILIALPG SMALLINT,
ICODPLANOPAG INTEGER,
NVLRREC NUMERIC(15,5),
DDATAREC DATE,
DDTCOMPREC DATE,
CFLAG CHAR,
ICODEMPBO INTEGER,
SCODFILIALBO SMALLINT,
CCODBANCO CHAR(3),
ICODEMPTC INTEGER,
SCODFILIALTC SMALLINT,
ICODTIPOCOB INTEGER,
ICODEMPCB INTEGER,
SCODFILIALCB SMALLINT,
CODCARTCOB CHAR(3),
NVLRCOMIREC NUMERIC(15,5),
OBSREC VARCHAR(250),
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
NUMCONTA CHAR(10),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC INTEGER,
CODCC CHAR(19),
VLRBASECOMIS NUMERIC(15,5))
 RETURNS(I INTEGER)
 AS
declare variable nperc numeric(15,5);
declare variable npercpag numeric(15,5);
declare variable nresto numeric(15,5);
declare variable inumparc integer;
declare variable idiaspag integer;
declare variable nrestocomi numeric(15,5);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nvlritrec numeric(15,5);
declare variable regrvcto char(1);
declare variable rvsab char(1);
declare variable rvdom char(1);
declare variable rvfer char(1);
declare variable dtvencto date;
declare variable rvdia char(1);
declare variable dtbase date;
declare variable diavcto smallint;
declare variable casasdecfin smallint;
declare variable vlrbaseitcomis numeric(15,5);
declare variable restobasecomis numeric(15,5);
begin

    -- Inicializando variáveis

    i = 0;
    nperc = 100;
    nresto = nvlrrec;
    nrestocomi = nvlrcomirec;
    dtbase = ddatarec;
    vlrbaseitcomis = vlrbasecomis;
    restobasecomis = vlrbasecomis;

    -- Buscando preferencias

    select casasdecfin from sgprefere1 where codemp=:icodemp and codfilial=:scodfilial
    into casasdecfin;

    -- Buscanco informações do plano de pagamento
    select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag, pp.rvferplanopag, pp.rvdiaplanopag,
    pp.diavctoplanopag
    from fnplanopag pp
    where pp.codplanopag =:icodplanopag and pp.codemp=:icodemppg and pp.codfilial = :scodfilialpg
    into :inumparc, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

    -- Loop nas parcelas do plano de pagamento para geração dos itens do contas a receber

    for select percpag, diaspag from fnparcpag
    where codplanopag=:icodplanopag and codemp=:icodemppg and codfilial =:scodfilialpg
    order by diaspag
    into :npercpag, :idiaspag
    do begin
        i = i+1;

        -- Calculando valor da parcela
        select v.deretorno from arreddouble(cast(:nvlrrec * :npercpag as numeric(15, 5))/:nperc, :casasdecfin ) v
        into :nvlritrec;

        nresto = nresto - nvlritrec;

        if (i = inumparc) then
        begin
            nvlritrec = nvlritrec + nresto;
        end

        -- Calculando o valor da comissão a pagar na parcela.
        select v.deretorno from arreddouble(cast(:nvlrcomirec * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :nvlrcomiitrec;

        nrestocomi = nrestocomi - nvlrcomiitrec;

        if (i = inumparc) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nrestocomi;
        end

         -- Calculando o valor da base da comissão especial a pagar na parcela.
        select v.deretorno from arreddouble(cast(:vlrbasecomis * :npercpag as numeric(15, 5))/:nperc,:casasdecfin) v
        into :vlrbaseitcomis;

        restobasecomis = restobasecomis - vlrbaseitcomis;

        if (i = inumparc) then
        begin
            vlrbaseitcomis = vlrbaseitcomis + restobasecomis;
        end

        -- Definindo o vencimento da parcela
        select c.dtvencret from sgcalcvencsp(:icodemp, :dtbase, :ddatarec + :idiaspag, :regrvcto,
        :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :idiaspag ) c
        into :dtvencto;

        dtbase = dtvencto;

        -- Execuntado procedimento que insere registros na tabela fnitreceber

        execute procedure fnadicitrecebersp01 (
        :caltvlr, :icodemp,:scodfilial,:icodrec,:i,0,0,0,:nvlritrec,0, :ddatarec, :ddtcomprec, :dtvencto,'R1',:cflag,
        :icodempbo,:scodfilialbo, :ccodbanco, :icodemptc, :scodfilialtc, :icodtipocob,
        :icodempcb, :scodfilialcb, :codcartcob, :nvlrcomiitrec, :obsrec, :codempca,
        :codfilialca, :numconta, :codemppn, :codfilialpn, :codplan, :codempcc, :codfilialcc, :anocc, :codcc, :vlrbaseitcomis);


    end
    suspend;
end
^

/* Alter (FNITRECEBERSP01) */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR)
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin
        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
    end
    suspend;
end
^

/* Alter (FNLIBCREDSP) */
ALTER PROCEDURE FNLIBCREDSP(CODEMPVD INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR,
CODVENDA INTEGER,
CODCLI INTEGER,
CODEMPCL INTEGER,
CODFILIALCL INTEGER,
VLRVENDA NUMERIC(15,5),
VLRADIC NUMERIC(15,2),
CODITEM INTEGER)
 AS
declare variable prefcred char(1);
declare variable codfilial integer;
declare variable vlrcredito numeric(15,5);
declare variable ecf char(1);
declare variable vlraberto numeric(15,5);
declare variable agrup char(1);
declare variable codempclip integer;
declare variable codfilialclip smallint;
declare variable codclip integer;
declare variable vlrdif numeric(15,2);
declare variable vlrautorizado numeric(15,2);
declare variable vlrpedidoant numeric(15,2);
declare variable vlrautorizadotot numeric(15,2);
begin
    select icodfilial from sgretfilial(:codempcl,'FNLIBCRED') into codfilial;
    select prefcred,lcredglobal from sgprefere1 where codemp=:codempcl and codfilial=:codfilial into prefcred, agrup;

    select first 1 cx.ecfcaixa from pvcaixa cx, sgconexao con, sgestacao est
        where est.codemp=con.codemp and est.codfilial=con.codfilial
            and est.codest=con.codterm AND cx.codfilialet=est.codfilial
            and cx.codempet=est.codemp AND cx.codest=est.codest
            and con.nrconexao=current_connection and conectado > 0
    into ecf;

    if (prefcred = 'N' or ecf='S') then /* Não verificar */
        exit;
    else if (prefcred = 'L') then /*Liberar se não ultrapassar o crédito pré aprovado */
        begin

             if( :coditem is not null and :vlradic is not null  ) then
             begin
                select vi.vlrproditvenda from vditvenda vi where vi.codemp=:codempvd
                and vi.codfilial=:codfilialvd and vi.codvenda=:codvenda
                and vi.tipovenda=:tipovenda and vi.coditvenda=:coditem into :vlrdif;
             end

            vlrvenda = coalesce(vlrvenda,0) + ( coalesce(vlradic,0) - coalesce(:vlrdif,0) );

            /* busca informações do cliente principal */
            select cli.codemppq, cli.codfilialpq, cli.codpesq
                from vdcliente cli
                where cli.codemp=:codempcl and cli.codfilial=:codfilialcl and cli.codcli=:codcli
            into codempclip, codfilialclip, codclip;

            if(agrup = 'S') then /* Verifica se deve agrupar despesas dos sub-clientes */
            begin
                /* BUSCA DE VALORES EM ABERTO */
                select coalesce(sum( (coalesce(it.vlrparcitrec,0)) - (coalesce(it.vlrpagoitrec,0)) ),0)
                    from fnreceber r, vdvenda v,fnitreceber it, vdcliente cl
                    where cl.codemppq=:codempclip and cl.codfilialpq=:codfilialclip and cl.codpesq=:codclip
                        and r.codempcl=cl.codemp and r.codfilialcl=cl.codfilial and r.codcli=cl.codcli
                        and (r.codempvd!=:codempvd or r.codfilialvd!=:codfilialvd or R.codvenda!=:codvenda or R.tipovenda!=:tipovenda) /* não deve contar com a venda atual */
                        and v.codemp=r.codempvd and v.codfilial=v.codfilialvd and v.codvenda=r.codvenda and v.tipovenda=r.tipovenda
                        and it.codemp=r.codemp and it.codfilial=r.codfilial and it.codrec=r.codrec and it.statusitrec not in ('RP')
                into vlraberto;

                /* BUSCA VALOR AUTORIZADO/LIBERADO */
                vlrautorizadotot = 0;
                for select lc.vlrautorizlcred,lc.vlrvendacred
                    from fnlibcred lc
                    where lc.codemp=:codempvd and lc.codfilial=:codfilialvd
                        and lc.codfilialvd=:codfilialvd and lc.codempcl=:codempcl
                        and lc.codfilialcl=:codfilialcl and lc.tipovenda=:tipovenda
                        and lc.codvenda=:codvenda and lc.dtavctolcred >= cast('today' as date)
                into :vlrautorizado, :vlrpedidoant do
                begin
                    vlrautorizadotot = :vlrautorizadotot + :vlrautorizado;
                end

                /* BUSCA DE LIMITE */
                select sum(coalesce(tc.vlrtpcred,0))
                    from fntipocred tc, vdcliente cl
                    where cl.codemppq=:codempclip and cl.codfilial=:codfilialcl and cl.codpesq=:codclip
                        and tc.codtpcred=cl.codtpcred and tc.codemp=cl.codemptr and tc.codfilial=cl.codfilialtr
                        and cast('today' as date) <= cl.dtvenctotr
                into vlrcredito;

                /* VALOR DO CREDITO É A SOMA DO CREDITO PRÉ-FIXADO + O VALOR LIBERADO PARA O PEDIDO*/
                vlrcredito = vlrcredito + vlrautorizadotot;

            end
            else /* Caso não deva agrupar as despesas dos sub-clientes */
            begin

                /* BUSCA DE VALORES EM ABERTO */

                select coalesce(sum( (coalesce(it.vlrparcitrec,0)) - (coalesce(it.vlrpagoitrec,0)) ),0)
                    from fnreceber r, vdvenda v,fnitreceber it, vdcliente cl
                    where cl.codemp=:codempvd and cl.codfilial=:codfilialvd and cl.codcli=:codcli
                        and r.codempcl=cl.codemp and r.codfilialcl=cl.codfilial and r.codcli=cl.codcli
                        and (r.codempvd!=:codempvd or r.codfilialvd!=:codfilialvd or R.codvenda!=:codvenda or R.tipovenda!=:tipovenda) /* não deve contar com a venda atual */
                        and v.codemp=r.codempvd and v.codfilial=v.codfilialvd and v.codvenda=r.codvenda and v.tipovenda=r.tipovenda
                        and it.codemp=r.codemp and it.codfilial=r.codfilial and it.codrec=r.codrec and it.statusitrec not in ('RP')
                into vlraberto;

                /* BUSCA VALOR AUTORIZADO/LIBERADO */
                vlrautorizadotot = 0;
                for select lc.vlrautorizlcred,lc.vlrvendacred
                    from fnlibcred lc
                    where lc.codemp=:codempvd and lc.codfilial=:codfilialvd
                        and lc.codfilialvd=:codfilialvd and lc.codempcl=:codempcl
                        and lc.codfilialcl=:codfilialcl and lc.tipovenda=:tipovenda
                        and lc.codvenda=:codvenda and lc.dtavctolcred >= cast('today' as date)
                into :vlrautorizado, :vlrpedidoant do
                begin
                    vlrautorizadotot = :vlrautorizadotot + :vlrautorizado;
                end

                /* BUSCA DE LIMITE */
                select coalesce(tc.vlrtpcred,0)
                    from fntipocred tc, vdcliente cl
                    where cl.codcli=:codcli and cl.codemp=:codempcl
                        and cl.codfilial=:codfilialcl AND tc.codtpcred=cl.codtpcred
                        and tc.codemp=cl.codemptr and tc.codfilial=cl.codfilialtr
                        and cast('today' as date) <= cl.dtvenctotr
                into vlrcredito;
                
                /* VALOR DO CREDITO É A SOMA DO CREDITO PRÉ-FIXADO + O VALOR LIBERADO PARA O PEDIDO*/
                vlrcredito = vlrcredito + vlrautorizadotot;

            end

            /* CASO JÁ EXISTA UM VALOR AUTORIZADO E O VALOR DO PEDIDO NÃO AUMENTOU, NÃO DEVE BLOQUEAR A VENDA*/
            if(vlrautorizadotot>0 and vlrpedidoant>=vlrvenda) then
            begin
                exit;
            end
            else if (vlrcredito > 0) then
                begin
                    if ( (vlrvenda + vlraberto ) <= vlrcredito) then
                        exit;
                    else
                        exception vdvendaex04 'VENDA:' || cast(vlrvenda as char(15)) || 'AB.: ' || cast(vlraberto as char(15)) || 'CRED.:' || cast(vlrcredito as char(15));
                        /*Valor pre-aprovado insuficiente*/
                end
        end
    suspend;
end
^

/* Alter (GERAEXPEDIENTESP) */
ALTER PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
DECLARE VARIABLE codturno smallint; 
DECLARE VARIABLE dataini date;
DECLARE VARIABLE datafer date;
DECLARE VARIABLE mesexped smallint;
DECLARE VARIABLE anotmp smallint;
DECLARE VARIABLE horasexped decimal(15,5);
DECLARE VARIABLE trabsabturno char(1);
DECLARE VARIABLE trabdomturno char(1);
DECLARE VARIABLE diasemana smallint;
BEGIN
   dataini = null;
   FOR SELECT T.CODTURNO, T.TRABSABTURNO, T.TRABDOMTURNO, 
     ((T.HINIINTTURNO-T.HINITURNO)+(T.HFIMTURNO-T.HFIMINTTURNO))/60/60 HORASEXPED 
     FROM RHTURNO T
     WHERE T.CODEMP=:codempto and T.CODFILIAL=:codfilialto
     INTO :codturno, :trabsabturno, :trabdomturno, :horasexped DO 
   begin
      DELETE FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      DELETE FROM RHEXPEDMES
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      dataini = cast( '01.01.'||cast(:anoexp as char(4)) as date);  
      anotmp = anoexp; 
      WHILE ( :anotmp=:anoexp )  DO 
      begin
         DATAFER=NULL;
         -- Verificação de sábados e domingos
         -- Expressão weekday retorna 0 para domingo e 6 para sábado
         diasemana = extract(weekday from :dataini);
         if ( ( (:trabsabturno='S') or (:diasemana<>6) ) and ( (:trabdomturno='S') or (:diasemana<>0) ) )  then
         begin
            --exception VDVENDAEX01 'Teste'||:dataini;

            SELECT F.DATAFER FROM SGFERIADO F
              WHERE F.CODEMP=:codempfe and F.CODFILIAL=:codfilialfe and F.DATAFER=:dataini and F.TRABFER='S' 
              INTO :DATAFER;
            if ( (:datafer is null) or (:datafer<>:dataini)) then
            begin
               mesexped = extract( month from :dataini);
               INSERT INTO RHEXPEDIENTE 
                        (CODEMP, CODFILIAL, CODTURNO, DTEXPED, ANOEXPED, MESEXPED, HORASEXPED)
                 VALUES (:codempto, :codfilialto, :codturno, :dataini, :anoexp, :mesexped, :horasexped);
            end
         end
         DATAINI=:DATAINI+1;
         anotmp = EXTRACT( YEAR FROM :DATAINI); 
      end
      INSERT INTO RHEXPEDMES( CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, HORASEXPED )
        SELECT CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, SUM(HORASEXPED) HORASEXPED
        FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and 
           ANOEXPED=:anoexp
        GROUP BY CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED;
   end
   suspend;
END
^

/* empty dependent procedure body */
/* Clear: LFBUSCAPREVTRIBORC for: LFBUSCAFISCALSP */
ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: LFBUSCATRIBCOMPRA for: LFBUSCAFISCALSP */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: VDADICITEMPDVSP for: LFBUSCAFISCALSP */
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
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: VDADICITVENDAORCSP for: LFBUSCAFISCALSP */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR,
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDBUSCACUSTOVENDASP for: LFBUSCAFISCALSP */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR,
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR,
ADICFRETE CHAR,
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* Alter (LFBUSCAFISCALSP) */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR,
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR,
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR,
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR,
CALCSTCM CHAR,
ALIQICMSSTCM NUMERIC(9,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

/* empty dependent procedure body */
/* Clear: LFGERALFITCOMPRASP for: LFBUSCAFISCALSP02 */
ALTER PROCEDURE LFGERALFITCOMPRASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: LFGERALFITVENDASP for: LFBUSCAFISCALSP02 */
ALTER PROCEDURE LFGERALFITVENDASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(2),
CODITVENDA SMALLINT)
 AS
 BEGIN EXIT; END
^

/* Alter (LFBUSCAFISCALSP02) */
ALTER PROCEDURE LFBUSCAFISCALSP02(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR,
CODITVENDA SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 RETURNS(VLRBASEPIS NUMERIC(15,5),
VLRBASECOFINS NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRIPIIT NUMERIC(15,5),
VLRBASENCM DECIMAL(15,5),
ALIQNACNCM DECIMAL(9,2),
ALIQIMPNCM DECIMAL(9,2),
VLRNACNCM DECIMAL(15,2),
VLRIMPNCM DECIMAL(15,2),
ORIGFISC CHAR)
 AS
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codsittribpis char(2);
declare variable codsittribcof char(2);
declare variable vlrpisunidtrib numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrprodit numeric(15,5);
declare variable aliqpis numeric(6,2);
declare variable qtdit numeric(15,5);
declare variable aliqcofins numeric(6,2);
declare variable aliqir numeric(15,5);
declare variable aliqcsocial numeric(15,5);
declare variable vlrliqit numeric(15,5);
declare variable vlrfreteit numeric(15,5);
declare variable vlrdescit numeric(15,5);
begin

    vlrbasencm = 0;
    aliqnacncm = 0;
    aliqimpncm = 0;
    vlrnacncm = 0;
    vlrimpncm = 0;
    
    -- Busca de regra de classificação fiscal da venda
    if(codvenda is not null and codcompra is null) then
    begin

        select li.codsittribpis,li.codsittribcof,iv.vlrproditvenda,li.aliqpisfisc,li.vlrpisunidtrib,
        iv.qtditvenda,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        iv.vlrliqitvenda, coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda,
        coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(nc.aliqnac,0) aliqnacncm, coalesce(nc.aliqimp,0) aliqimpncm,
        coalesce(iv.vlrliqitvenda,0) vlrbasencm, coalesce(li.origfisc,'0') origfisc
        from vditvenda iv left outer join lfitclfiscal li on
        li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
        left outer join sgfilial fi on
        fi.codemp=iv.codemp and fi.codfilial=iv.codfilial
        left outer join lfclfiscal cf on
        cf.codemp=li.codemp and cf.codfilial=li.codfilial and cf.codfisc=li.codfisc
        left outer join lfncm nc on
        nc.codncm=cf.codncm
        where
        iv.codemp=:codemp and iv.codfilial=:codfilial and iv.codvenda=:codvenda and iv.tipovenda=:tipovenda and iv.coditvenda=:coditvenda
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT
        ,:ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT
        ,:ALIQNACNCM, :ALIQIMPNCM, :VLRBASENCM, :ORIGFISC;
    end
    -- Busca de regra de classificação fiscal da compra
    else if(codvenda is null and codcompra is not null) then
    begin

       select li.codsittribpis,li.codsittribcof,ic.vlrproditcompra,li.aliqpisfisc,li.vlrpisunidtrib,
        ic.qtditcompra,li.aliqcofinsfisc,coalesce(li.aliqirfisc,fi.percirfilial),coalesce(li.aliqcsocialfisc, fi.perccsocialfilial),
        ic.vlrliqitcompra, coalesce(ic.vlripiitcompra,0) vlripiit, coalesce(ic.vlrfreteitcompra,0) vlrfreteit,
        coalesce(ic.vlrdescitcompra,0) vlrdescit
        from cpitcompra ic left outer join lfitclfiscal li on
        li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
        left outer join sgfilial fi on
        fi.codemp=ic.codemp and fi.codfilial=ic.codfilial
        where
        ic.codemp=:codemp and ic.codfilial=:codfilial and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
        into
        :CODSITTRIBPIS,:CODSITTRIBCOF,:VLRPRODIT,:ALIQPIS,:VLRPISUNIDTRIB,:QTDIT,
        :ALIQCOFINS,:ALIQIR,:ALIQCSOCIAL,:VLRLIQIT,:VLRIPIIT, :VLRFRETEIT, :VLRDESCIT;

    end

    -- Definição do PIS

    if(:CODSITTRIBPIS in ('01','02','99')) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprodit - :vlrdescit; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtdit * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:CODSITTRIBCOF in ('01','02','99')) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprodit - :vlrdescit; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:CODSITTRIBPIS in ('03')) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtdit * vlrcofunidtrib;
    end

    -- Definição do IR

    vlrir = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqir) / 100;

    -- Definição da CSocial

    vlrcsocial = ((:VLRLIQIT + :VLRIPIIT + :VLRFRETEIT) * aliqcsocial) / 100;
    
    if ( coalesce(:vlrbasencm,0)>0 ) then
    begin
       -- Cálculo estimativo para lei de transparência
       if (:origfisc in ('1','2','6','7')) then 
             vlrimpncm = :vlrbasencm * :aliqimpncm / 100;
       else
          vlrnacncm = :vlrbasencm * :aliqnacncm / 100;
    end 

  suspend;
end
^

/* empty dependent procedure body */
/* Clear: LFBUSCAFISCALSP for: LFBUSCAICMSSP */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR,
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR,
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR,
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR,
CALCSTCM CHAR,
ALIQICMSSTCM NUMERIC(9,2))
 AS
 BEGIN SUSPEND; END
^

/* Alter (LFBUSCAICMSSP) */
ALTER PROCEDURE LFBUSCAICMSSP(CODNAT CHAR(4),
ESTCLI CHAR(2),
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(PERCICMS NUMERIC(9,2),
PERCICMSINTRA NUMERIC(9,2))
 AS
DECLARE VARIABLE ESTEMP CHAR(2);
begin
    -- Buscando o estado de origem
    select coalesce(siglauf, uffilial) from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
    into :EstEmp;

    -- Caso estado de origem e destino sejam o mesmo
    if (EstCli = EstEmp) then
    begin
        -- busca aliquota da tabela de CFOP
        select aliqenat, aliqenat from lfnatoper where codnat=:CodNat and codemp=:ICODEMP and codfilial=:ICODFILIAL
        into :PERCICMS, :PERCICMSINTRA;
    end
    else
    begin
        -- busca aliquota da tabela de icms para a natureza de operação informada.
        select ti.aliqti, ti.aliqintrati from lftabicms ti, lfitnatoper ino
        where ti.codemp = ino.codempti and ti.codfilial=ino.codfilialti and ti.ufti=ino.ufti and
        ino.codemp=:ICODEMP and ino.codfilial=:ICODFILIAL and ino.codnat=:CODNAT and ino.ufti=:ESTCLI
        into :PERCICMS, :PERCICMSINTRA;
    end

    -- Caso não tenha obtido a aliquota nas formas anteriores
    if (PERCICMS is null) then
    begin
        select aliqfnat,aliqenat from lfnatoper where codnat=:CODNAT and codemp=:ICODEMP and codfilial=:ICODFILIAL
        into :PERCICMS, :PERCICMSINTRA;
    end

  suspend;

end
^

/* empty dependent procedure body */
/* Clear: CPGERAITENTRADASP for: LFBUSCANATSP */
ALTER PROCEDURE CPGERAITENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR,
CODVENDA INTEGER,
CODITVENDA INTEGER,
QTDIT INTEGER)
 AS
 BEGIN EXIT; END
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
CODTIPOMOV INTEGER,
CODITFISC INTEGER)
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
    left outer join lfitclfiscal icf on
    icf.codemp=c.codemp and icf.codfilial=c.codfilial and icf.codfisc=c.codfisc and icf.coditfisc=:coditfisc
    where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
    and c.codfisc = p.codfisc and c.codfilial = p.codfilialfc and c.codemp = p.codempfc
    and r.codregra = coalesce(icf.codregra, c.codregra) and r.codfilial = c.codfilialra and r.codemp = c.codempra
    and r.codemptm=:codemppd and r.codfilialtm=:codfilialtm and r.codtipomov=:codtipomov
    and r.cvitrf=:cv and r.noufitrf=:noest
    into codnat;

    -- Se não localizou nenhum cfop na pesquisa anterior pega a primeira cfop
    -- para a classificação fiscal do produto na regra.
    if (codnat is null) then
    begin
        select first 1 r.codnat from eqproduto p, lfitregrafiscal r, lfclfiscal c
        left outer join lfitclfiscal icf on
        icf.codemp=c.codemp and icf.codfilial=c.codfilial and icf.codfisc=c.codfisc and icf.coditfisc=:coditfisc
        where p.codprod=:codprod and p.codfilial=:codfilialpd and p.codemp=:codemppd
        and c.codfisc=p.codfisc and c.codfilial=p.codfilialfc and c.codemp = p.codempfc and r.codregra = coalesce(icf.codregra, c.codregra)
        and r.codfilial = c.codfilialra and r.codemp = c.codempra
        and r.cvitrf=:cv and r.noufitrf=:noest
        order by r.codtipomov
        into codnat;
     end

    suspend;
end
^

/* Alter (LFBUSCAPREVTRIBORC) */
ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc smallint;
declare variable vlrbasepis numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrprod integer;
declare variable aliqpis numeric(6,2);
declare variable qtd numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(6,2);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrliq numeric(15,5);
declare variable vlrfrete numeric(15,5);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable aliqipi numeric(6,2);
declare variable tpcalcipi char(1);
declare variable vlrbaseipi numeric(15,5);
declare variable aliqcsocial numeric(6,2);
declare variable aliqir numeric(6,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable tipoprod varchar(2);
declare variable aliqiss numeric(6,2);
declare variable aliqicms numeric(6,2);
declare variable tpredicms char(1);
declare variable redicms numeric(15,5);
declare variable baseicms numeric(15,5);
declare variable codtrattrib char(2);
declare variable codsittribpis char(2);
declare variable ufcli char(2);
begin

    -- Inicializando variáveis

    vlripi = 0;
    vlrfrete = 0;

    -- Buscando informações no orçamento (cliente e tipo de movimento)
    select oc.codempcl,oc.codfilialcl,oc.codcli,tm.codemptm,tm.codfilialtm,tm.codtipomovtm,coalesce(cl.siglauf,cl.ufcli)
    from vdorcamento oc, vdcliente cl, eqtipomov tm
    where oc.codemp=:codemp and oc.codfilial=:codfilial and oc.codorc=:codorc and oc.tipoorc=:tipoorc
    and cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli
    and tm.codemp=oc.codemp and tm.codfilial=oc.codfilialtm and tm.codtipomov=oc.codtipomov
    into :codempcl, :codfilialcl, :codcli, :codemptm, :codfilialtm, :codtipomov, :ufcli;

    -- Buscando informações do produto no item de orçamento
    select io.codemppd, io.codfilialpd, io.codprod, io.vlrproditorc, io.qtditorc, io.vlrliqitorc, coalesce(io.vlrfreteitorc,0),
    pd.tipoprod

    from vditorcamento io, eqproduto pd
    where io.codemp=:codemp and io.codfilial=:codfilial and io.codorc=:codorc and io.tipoorc=:tipoorc and io.coditorc=:coditorc
    and pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod
    into :codemppd, :codfilialpd, :codprod, :vlrprod, :qtd, :vlrliq, :vlrfrete, :tipoprod;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempcl, :codfilialcl, :codcli,
    :codemptm, :codfilialtm, :codtipomov, 'VD',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.codsittribpis, cf.aliqpisfisc, cf.vlrpisunidtrib, cf.codsittribcof, cf.aliqcofinsfisc, cf.vlrcofunidtrib,
    cf.vlripiunidtrib, cf.aliqipifisc, cf.codsittribipi, cf.tpcalcipi,
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(cf.aliqissfisc, fi.percissfilial),
    cf.tpredicmsfisc, cf.redfisc, cf.codtrattrib
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :codsittribpis, :aliqpis, :vlrpisunidtrib, :codsittribcof, :aliqcofins, :vlrcofunidtrib,
    :vlripiunidtrib, :aliqipi, :codsittribipi,:tpcalcipi, :aliqcsocial, :aliqir, :aliqiss,
    :tpredicms, :redicms, :codtrattrib;

    -- Definição do IPI
    if(:codsittribipi not in ('52','53','54')) then -- IPI Tributado
    begin
        if(:tpcalcipi='P' and aliqipi is not null and aliqipi > 0) then -- Calculo pela aliquota
        begin
            vlrbaseipi = :vlrliq; -- (Base do IPI = Valor total dos produtos - Implementar situações distintas futuramente)
            vlripi = (vlrbaseipi * aliqipi) / 100;
        end
        else if (vlripiunidtrib is not null and vlripiunidtrib > 0) then -- Calculo pela quantidade
        begin
            vlripi = qtd * vlripiunidtrib;
        end
    end

    -- Definição do PIS

    if(:codsittribpis in ('01','02','99') and aliqpis is not null and aliqpis > 0 ) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprod; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:codsittribpis in ('03') and vlrpisunidtrib is not null and vlrpisunidtrib > 0) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtd * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:codsittribcof in ('01','02','99') and aliqcofins is not null and aliqcofins > 0 ) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprod; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:codsittribpis in ('03') and vlrcofunidtrib is not null and vlrcofunidtrib > 0) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtd * vlrcofunidtrib;
    end

    -- Definição do IR

    if(aliqir is not null and aliqir > 0) then
    begin
        vlrir = ((:vlrliq + :vlripi + :vlrfrete) * aliqir) / 100;
    end

    -- Definição da CSocial

    vlrcsocial = ((:vlrliq + :vlripi + :vlrfrete) * aliqcsocial) / 100;

    -- Definição do ISS se for um serviço
    if (tipoprod = 'S') then
    begin
        if ( aliqiss is not null and aliqiss > 0 ) then
        begin
            vlriss = vlrliq * (aliqiss/100);
        end
    end

    -- Definição do ICMS
    -- Calcular icms quando aliquota maio que zero e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

    if(codtrattrib not in ('40','30','41','50')) then
    begin

        if(aliqicms is null) then
        begin
            select ti.aliqti from lftabicms ti
            where codemp=:codemp and codfilial=:codfilial and ufti=:ufcli
            into aliqicms;
        end

        if (redicms>0) then -- Com redução
        begin
            if(tpredicms='B') then -- Redução na base de cálculo
            begin
                baseicms = vlrliq * ( 1 - (redicms / 100));
                vlricms = baseicms * (aliqicms / 100);
            end
            else -- Redução no valor
            begin

                baseicms = vlrliq;
                vlricms = baseicms * ( aliqicms / 100 );
                vlricms = vlricms * (( 100 - redicms ) / 100);


            end
        end
        else -- Sem redução
        begin

            baseicms = vlrliq;
            vlricms = baseicms * (aliqicms / 100);

        end

    end
  suspend;
end
^

/* Alter (LFBUSCATRIBCOMPRA) */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
begin

    -- Inicializando variáveis

    vlrfunrural = 0;

    -- Buscando informações na compra (fornecedor e tipo de movimento)
    select cp.codempfr,cp.codfilialfr,cp.codfor,coalesce(tm.codemptm, tm.codemp)
    ,coalesce(tm.codfilialtm,tm.codfilial),coalesce(tm.codtipomovtm, tm.codtipomov)
    from cpcompra cp, cpforneced fr, eqtipomov tm
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra
    and fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    and tm.codemp=cp.codemp and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov
    into :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.aliqfunruralfisc
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :aliqfunrural;

    -- Definição do Funrural
    if(:aliqfunrural>0) then -- Retenção do funrural
    begin
        vlrbasefunrural = :vlrliq; -- (Base do Funrural = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrfunrural = (vlrbasefunrural * aliqfunrural) / 100;
    end


    suspend;
end
^

/* Alter (LFCALCCUSTOSP01) */
ALTER PROCEDURE LFCALCCUSTOSP01(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCALC INTEGER,
QTDADE DECIMAL(15,5),
VLRCUSTOP DECIMAL(15,5),
VLRICMS DECIMAL(15,5),
VLRIPI DECIMAL(15,5),
VLRPIS DECIMAL(15,5),
VLRCOFINS DECIMAL(15,5),
VLRISS DECIMAL(15,5),
VLRFUNRURAL DECIMAL(15,5),
VLRII DECIMAL(15,5),
VLRIR DECIMAL(15,5),
VLRTXSISCOMEX DECIMAL(15,5),
VLRICMSDIFERIDO DECIMAL(15,5),
VLRICMSPRESUMIDO DECIMAL(15,5),
VLRCOMPL DECIMAL(15,5))
 RETURNS(VLRCUSTO DECIMAL(15,5))
 AS
declare variable siglacalc varchar(10);
declare variable operacao char(1);
declare variable vlrimposto decimal(15,5);
begin
  if (:vlrcustop is null) then
    vlrcusto = 0;
  else
    vlrcusto = vlrcustop;

  if (vlripi is null) then
   vlripi = 0;
  if (vlricms is null) then
   vlricms = 0;
  if (vlrpis is null) then
   vlrpis = 0;
  if (vlrcofins is null) then
   vlrcofins = 0;
  if (vlriss is null) then
   vlriss = 0;
  if (vlrfunrural is null) then
   vlrfunrural = 0;
  if (vlrii is null) then
   vlrii = 0;
  if (vlrir is null) then
   vlrir = 0;
  if (vlrtxsiscomex is null) then
   vlrtxsiscomex = 0;
  if (vlricmsdiferido is null) then
   vlricmsdiferido = 0;
  if (vlricmspresumido is null) then
   vlricmspresumido = 0;
  if (vlrcompl is null) then
   vlrcompl = 0; 

  for select ic.siglacalc, ic.operacaocalc
     from lfcalccusto c, lfitcalccusto ic
     where c.codemp=:codemp and c.codfilial=:codfilial and c.codcalc=:codcalc
       and ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.codcalc=c.codcalc
       into :siglacalc, :operacao do
  begin

     if (:siglacalc='IPI') then
       vlrimposto = vlripi;
     else if (:siglacalc='ICMS') then
       vlrimposto = vlricms;
     else if (:siglacalc='PIS') then
       vlrimposto = vlrpis;
     else if (:siglacalc='COFINS') then
       vlrimposto = vlrcofins;
     else if (:siglacalc='ISS') then
       vlrimposto = vlriss;
     else if (:siglacalc='FUNRURAL') then
       vlrimposto = vlrfunrural;
     else if (:siglacalc='II') then
       vlrimposto = vlrii;
     else if (:siglacalc='IR') then
       vlrimposto = vlrir;
     else if (:siglacalc='TXSISCOMEX') then
       vlrimposto = vlrtxsiscomex;
     else if (:siglacalc='ICMSDIF') then
       vlrimposto = vlricmsdiferido;
     else if (:siglacalc='ICMSPRES') then
       vlrimposto = vlricmspresumido;
     else if (:siglacalc='COMPL') then
       vlrimposto = vlrcompl;

     if (:operacao='+') then
        vlrcusto = vlrcusto + vlrimposto;
     else if (:operacao='-') then
        vlrcusto = vlrcusto - vlrimposto;
  end
  if (qtdade is not null and qtdade<>0) then
  begin
     -- Dividimos o total pela quantidade, multiplicamos o resultado novamente pela quantidade e dividimos pela quantidade, objetivando evitar dizima periódica.
     
      vlrcusto = cast( ( cast(vlrcusto as decimal(15,4)) / cast( qtdade as decimal(15,5) )  * cast( qtdade as decimal(15,5) ) ) as decimal(15,4) ) / cast( qtdade as decimal(15,5) ) ;
  end
  suspend;
end
^

/* Alter (LFGERALFITCOMPRASP) */
ALTER PROCEDURE LFGERALFITCOMPRASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable uffor char(2);
declare variable percicmsst numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
begin

    -- Inserindo informações fiscais na tabela LFITCOMPRA

    select 'S'
    from lfitcompra
    where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial, li.codtrattrib, li.origfisc, ic.vlrbaseicmsstitcompra, ic.vlricmsstitcompra
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,null,null,null,:codcompra,:coditcompra) bf
    left outer join cpitcompra ic on ic.codemp=:CODEMP and ic.codfilial=:CODFILIAL and ic.codcompra=:codcompra and ic.coditcompra=:coditcompra
    left outer join lfitclfiscal li on li.codemp=ic.codempif and li.codfilial=ic.codfilialif and li.codfisc=ic.codfisc and li.coditfisc=ic.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL,:codtrattrib,:origfisc, :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA;

    -- Buscando estado do fornecedor
    select coalesce(fr.siglauf,fr.uffor), ic.codempif, ic.codfilialif, ic.codfisc, ic.coditfisc, ic.codnat from cpforneced fr, cpcompra cp
    left outer join cpitcompra ic on ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra and ic.coditcompra=:coditcompra
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into uffor,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitcompra set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL, vlrbaseicmsstitcompra=:vlrbaseicmsstitcompra, vlricmsstitcompra=:vlricmsstitcompra
            where codemp=:codemp and codfilial=:codfilial and codcompra=:codcompra and coditcompra=:coditcompra;
    end
    else
    begin
        insert into lfitcompra (codemp,codfilial,codcompra,coditcompra,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial,codtrattrib,origfisc,
            vlrbaseicmsstitcompra, vlricmsstitcompra)
        values(:CODEMP,:CODFILIAL,:CODcompra,:CODITcompra,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL, :codtrattrib, :origfisc,
        :VLRBASEICMSSTITCOMPRA, :VLRICMSSTITCOMPRA );

    end
  suspend;
end
^

/* Alter (LFGERALFITVENDASP) */
ALTER PROCEDURE LFGERALFITVENDASP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(2),
CODITVENDA SMALLINT)
 AS
declare variable temitem char(1);
declare variable codempsp integer;
declare variable codfilialsp smallint;
declare variable impsittribpis char(2);
declare variable codsittribpis char(2);
declare variable aliqpisfisc numeric(15,5);
declare variable vlrbasepis numeric(15,5);
declare variable vlrpis numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codempsc integer;
declare variable codfilialsc smallint;
declare variable impsittribcof char(2);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrcofins numeric(15,5);
declare variable vlrcofunidtrib numeric(15,5);
declare variable codempsi integer;
declare variable codfilialsi smallint;
declare variable impsittribipi char(2);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable modbcicms smallint;
declare variable modbcicmsst smallint;
declare variable redfisc numeric(9,2);
declare variable aliqfisc integer;
declare variable vlrir numeric(15,5);
declare variable vlrcsocial numeric(15,5);
declare variable ufcli char(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbasencm decimal(15,5);
declare variable aliqnacncm decimal(9,2);
declare variable aliqimpncm decimal(9,2);
declare variable vlrnacncm decimal(15,5);
declare variable vlrimpncm decimal(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable codnat char(4);
begin

    -- Inserindo informações fiscais na tabela LFITVENDA

    select 'S'
    from lfitvenda
    where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda
    into :temitem;

    select
    li.codempsp,li.codfilialsp,li.impsittribpis,li.codsittribpis,li.aliqpisfisc,bf.vlrbasepis,bf.vlrpis,li.vlrpisunidtrib,
    li.codempsc,li.codfilialsc,li.impsittribcof,li.codsittribcof,li.aliqcofinsfisc,bf.vlrbasecofins,bf.vlrcofins,li.vlrcofunidtrib,
    li.codempsi,li.codfilialsi,li.impsittribipi,li.codsittribipi,li.vlripiunidtrib,
    li.modbcicms,li.modbcicmsst,li.redfisc,li.aliqfisc,bf.vlrir,bf.vlrcsocial
    , bf.vlrbasencm, bf.aliqnacncm, bf.aliqimpncm, bf.vlrnacncm, bf.vlrimpncm 
    from lfbuscafiscalsp02(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA, null, null) bf
    left outer join vditvenda iv on iv.codemp=:CODEMP and iv.codfilial=:CODFILIAL and iv.codvenda=:CODVENDA and iv.tipovenda=:TIPOVENDA and iv.coditvenda=:CODITVENDA
    left outer join lfitclfiscal li on li.codemp=iv.codempif and li.codfilial=iv.codfilialif and li.codfisc=iv.codfisc and li.coditfisc=iv.coditfisc
    into
    :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
    :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
    :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
    :MODBCICMS,:MODBCICMSST,:REDFISC,:ALIQFISC,:VLRIR,:VLRCSOCIAL
    , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm;


    -- Buscando estado do cliente
    select coalesce(cl.siglauf,cl.ufcli), iv.codempif, iv.codfilialif, iv.codfisc, iv.coditfisc, iv.codnat from vdcliente cl, vdvenda vd
    left outer join vditvenda iv on iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.coditvenda=:coditvenda
    where vd.codemp=:codemp and vd.codfilial=:codfilial and vd.codvenda=:codvenda and vd.tipovenda=:tipovenda and
    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
    into ufcli,codempif, codfilialif, codfisc, coditfisc, codnat;

    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
    select coalesce(ic.aliqfiscintra,0) from lfitclfiscal ic
    where ic.codemp=:codempif and ic.codfilial=:codfilialif and ic.codfisc=:codfisc and ic.coditfisc=:coditfisc
    into PERCICMSST;

    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
    if (percicmsst = 0) then
    begin
        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:codnat,:ufcli,:codemp,:codfilial)
        into PERCICMSST;
    end

    if(:TEMITEM='S') then
    begin
            update lfitvenda set codempsp=:CODEMPSP,codfilialsp=:CODFILIALSP,impsittribpis=:IMPSITTRIBPIS,
            codsittribpis=:CODSITTRIBPIS,aliqpis=:ALIQPISFISC,vlrbasepis=:VLRBASEPIS,vlrpis=:VLRPIS,vlrpisunidtrib=:VLRPISUNIDTRIB,
            codempsc=:CODEMPSC,codfilialsc=:CODFILIALSC,impsittribcof=:IMPSITTRIBCOF,codsittribcof=:CODSITTRIBCOF,aliqcofins=:ALIQCOFINS,
            vlrbasecofins=:VLRBASECOFINS,vlrcofins=:VLRCOFINS,vlrcofunidtrib=:VLRCOFUNIDTRIB,
            codempsi=:CODEMPSI,codfilialsi=:CODFILIALSI,impsittribipi=:IMPSITTRIBIPI,codsittribipi=:CODSITTRIBIPI,vlripiunidtrib=:VLRIPIUNIDTRIB,
            modbcicms=:MODBCICMS,modbcicmsst=:MODBCICMSST,aliqredbcicms=:REDFISC,aliqredbcicmsst=:REDFISC,aliqicmsst=:percicmsst,
            vlrir=:VLRIR,vlrcsocial=:VLRCSOCIAL
            , vlrbasencm=:vlrbasencm, aliqnacncm=:aliqnacncm, aliqimpncm=:aliqimpncm, vlrnacncm=:vlrnacncm, vlrimpncm=:vlrimpncm
            where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda;
    end
    else
    begin
        insert into lfitvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,
            codempsp,codfilialsp,impsittribpis,codsittribpis,aliqpis,vlrbasepis,vlrpis,vlrpisunidtrib,
            codempsc,codfilialsc,impsittribcof,codsittribcof,aliqcofins,vlrbasecofins,vlrcofins,vlrcofunidtrib,
            codempsi,codfilialsi,impsittribipi,codsittribipi,vlripiunidtrib,
            modbcicms,modbcicmsst,aliqredbcicms,aliqredbcicmsst,aliqicmsst,vlrir,vlrcsocial
            , vlrbasencm, aliqnacncm, aliqimpncm, vlrnacncm, vlrimpncm)
        values(:CODEMP,:CODFILIAL,:CODVENDA,:TIPOVENDA,:CODITVENDA,
        :CODEMPSP,:CODFILIALSP,:IMPSITTRIBPIS,:CODSITTRIBPIS,:ALIQPISFISC,:VLRBASEPIS,:VLRPIS,:VLRPISUNIDTRIB,
        :CODEMPSC,:CODFILIALSC,:IMPSITTRIBCOF,:CODSITTRIBCOF,:ALIQCOFINS,:VLRBASECOFINS,:VLRCOFINS,:VLRCOFUNIDTRIB,
        :CODEMPSI,:CODFILIALSI,:IMPSITTRIBIPI,:CODSITTRIBIPI,:VLRIPIUNIDTRIB,
        :MODBCICMS,:MODBCICMSST,:REDFISC,:REDFISC,:percicmsst,:VLRIR,:VLRCSOCIAL
        , :vlrbasencm, :aliqnacncm, :aliqimpncm, :vlrnacncm, :vlrimpncm );

    end

  suspend;
end
^

/* empty dependent procedure body */
/* Clear: CPADICCOMPRAPEDSP for: LFNOVODOCSP */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: CPGERAENTRADASP for: LFNOVODOCSP */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR,
CODVENDA INTEGER,
CITEM CHAR)
 RETURNS(CODCOMPRA INTEGER)
 AS
 BEGIN SUSPEND; END
^

/* Alter (LFNOVODOCSP) */
ALTER PROCEDURE LFNOVODOCSP(SSERIE CHAR(4),
ICODEMP INTEGER,
ICODFILIAL SMALLINT)
 RETURNS(DOC INTEGER)
 AS
declare variable iseqserie integer;
declare variable icodfilialss smallint;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP, 'LFSEQSERIE')
     INTO :ICODFILIALSS;
  DOC = NULL;
  /* Utiliza o número sequencial do documento da tabela LFSERIE,
    para retrocompatibilidade */
  SELECT COALESCE(SS.DOCSERIE,S.SERIE) DOCSERIE, SS.SEQSERIE
     FROM LFSERIE S
     LEFT OUTER JOIN LFSEQSERIE SS
     ON SS.CODEMP=S.CODEMP AND
        SS.CODFILIAL=S.CODFILIAL AND SS.SERIE=S.SERIE AND
        SS.CODEMPSS=:ICODEMP AND SS.CODFILIALSS=:ICODFILIALSS
     WHERE S.SERIE=:SSERIE AND S.CODEMP=:ICODEMP AND
        S.CODFILIAL=:ICODFILIAL AND SS.ATIVSERIE='S'
     INTO :DOC, :ISEQSERIE;
  IF (:ISEQSERIE IS NULL) THEN
  BEGIN
     IF (:DOC IS NULL) THEN
        DOC = 0;
     DOC = DOC + 1;
     SELECT MAX(SEQSERIE) FROM LFSEQSERIE SS
        WHERE SS.CODEMP=:ICODEMP AND SS.CODFILIAL=:ICODFILIAL AND
            SS.CODEMPSS=:ICODEMP AND SS.CODFILIALSS=:ICODFILIALSS AND
            SS.SERIE=:SSERIE INTO :ISEQSERIE;
     IF (:ISEQSERIE IS NULL) THEN
         ISEQSERIE = 1;
     INSERT INTO LFSEQSERIE (CODEMP, CODFILIAL, SERIE,
         CODEMPSS, CODFILIALSS, SEQSERIE, DOCSERIE)
     VALUES (:ICODEMP, :ICODFILIAL, :SSERIE,
         :ICODEMP, :ICODFILIALSS, :ISEQSERIE, :DOC);
  END
  ELSE
  BEGIN
    DOC = DOC + 1;
  END
  UPDATE LFSEQSERIE SET DOCSERIE=:DOC
     WHERE SERIE=:SSERIE AND CODEMP=:ICODEMP AND
     CODFILIAL=:ICODFILIAL AND CODEMPSS=:ICODEMP AND
     CODFILIALSS=:ICODFILIALSS AND SEQSERIE=:ISEQSERIE;
  SUSPEND;
END
^

/* empty dependent procedure body */
/* Clear: PPRELCUSTOSP for: PPCUSTOPRODSP */
ALTER PROCEDURE PPRELCUSTOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
TIPOPROD VARCHAR(2),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
 BEGIN SUSPEND; END
^

/* Alter (PPCUSTOPRODSP) */
ALTER PROCEDURE PPCUSTOPRODSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODPROD INTEGER,
DTESTOQ DATE,
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CCOMSALDO CHAR(10))
 RETURNS(CUSTOUNIT NUMERIC(15,5),
SLDPROD NUMERIC(15,5))
 AS
declare variable qtditest numeric(15,5);
declare variable seqest smallint;
declare variable tipoprod varchar(2);
declare variable custotot numeric(15,5);
declare variable codprodpd integer;
begin
  /* Retorna o custo unitário do produto */
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  SELECT P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
      P.CODPROD=:ICODPROD
   INTO :TIPOPROD;

  IF (TIPOPROD='F') THEN
  BEGIN
     SELECT SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :SLDPROD;

     CUSTOTOT = 0;

     SELECT FIRST 1 E.SEQEST FROM PPESTRUTURA E
       WHERE E.CODEMP=:ICODEMP AND E.CODFILIAL=:SCODFILIAL AND E.CODPROD=:ICODPROD
       ORDER BY E.SEQEST INTO :SEQEST;

     FOR SELECT I.CODPRODPD,I.QTDITEST FROM  PPITESTRUTURA I
        WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:SCODFILIAL AND
          I.CODPROD=:ICODPROD AND I.SEQEST=:SEQEST
        INTO :CODPRODPD, :QTDITEST DO
     BEGIN
         SELECT CUSTOUNIT FROM PPCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :CODPRODPD, :DTESTOQ,
            :CTIPOCUSTO, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CCOMSALDO)
         INTO :CUSTOUNIT;
         CUSTOTOT = CUSTOTOT + ( CUSTOUNIT * QTDITEST)  ;
     END
  END
  ELSE
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM EQCUSTOPRODSP(:ICODEMP, :SCODFILIAL, :ICODPROD,
        :DTESTOQ, :CTIPOCUSTO,  :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, 'N')
     INTO :CUSTOTOT, :SLDPROD;
  END
  CUSTOUNIT = CUSTOTOT;
  SUSPEND;
end
^

/* Alter (PPGERAOP) */
ALTER PROCEDURE PPGERAOP(TIPOPROCESS CHAR,
CODEMPOP INTEGER,
CODFILIALOP SMALLINT,
CODOP INTEGER,
SEQOP INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC SMALLINT,
QTDSUGPRODOP NUMERIC(15,5),
DTFABROP DATE,
SEQEST SMALLINT,
CODEMPET INTEGER,
CODFILIALET SMALLINT,
CODEST SMALLINT,
AGRUPDATAAPROV CHAR,
AGRUPDTFABROP CHAR,
AGRUPCODCLI CHAR,
CODEMPCL INTEGER,
CODFILIALCL SMALLINT,
CODCLI INTEGER,
DATAAPROV DATE,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
CODITCOMPRA SMALLINT,
JUSTFICQTDPROD VARCHAR(500),
CODEMPPDENTRADA INTEGER,
CODFILIALPDENTRADA SMALLINT,
CODPRODENTRADA INTEGER,
QTDENTRADA NUMERIC(15,5))
 RETURNS(CODOPRET INTEGER,
SEQOPRET SMALLINT)
 AS
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable refprod varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable sitpadop char(2);
declare variable seqof smallint;
declare variable codempfs integer;
declare variable codfilialfs smallint;
declare variable codfase integer;
declare variable tempoefdias numeric(15,5);
declare variable tempoef numeric(15,5);
declare variable datafimprodant date;
declare variable hfimprodant time;
declare variable qtdfinalprodop numeric(15,5);
declare variable codtipomovtm integer;
declare variable sitpadopconv char(2);
declare variable codemprma integer;
declare variable codfilialrma smallint;
declare variable codrma integer;
declare variable coditrma smallint;
declare variable estdinamica char(1);
begin

    if(codop is null) then
    begin

        -- Busca novo código para a OP caso não venha no parâmetro.
        select coalesce(max(codop),0) + 1 from ppop where codemp=:codempop and codfilial=:codfilialop
        into :codop;

        -- Buscando informações do produto e estrutura.

        select pd.codempax, pd.codfilialax, pd.codalmox, pd.refprod, es.estdinamica from eqproduto pd, ppestrutura es
        where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
        and es.codemp=pd.codemp and es.codfilial=pd.codfilial and es.codprod=pd.codprod and es.seqest=:seqest
        into :codempax, :codfilialax, :codalmox, :refprod, :estdinamica;

        -- Buscando tipo de movimento para OP.
        select p5.codemptm, p5.codfilialtm, p5.codtipomov, coalesce(tm.codtipomovtm,p5.codtipomov), p5.sitpadop, p5.sitpadopconv
        from sgprefere5 p5, eqtipomov tm
        where p5.codemp=:codempop and p5.codfilial=:codfilialop and
        tm.codemp=p5.codemptm and tm.codfilial=p5.codfilialtm and tm.codtipomov=p5.codtipomov
        into :codemptm, :codfilialtm, :codtipomov, :codtipomovtm, :sitpadop, :sitpadopconv;

        -- Inserindo OP
        seqop = 0;

        if(sitpadop='FN' and tipoprocess in ('D','A')) then
        begin
            qtdfinalprodop = :qtdsugprodop;
            codtipomov = :codtipomovtm;
        end
        else if(sitpadopconv='FN' and tipoprocess='C') then
        begin
            qtdfinalprodop = :qtdsugprodop;
            codtipomov = :codtipomovtm;
        end
        else
        begin
            qtdfinalprodop = 0;
        end

        insert into ppop  (codemp, codfilial, codop, seqop,
                           codemppd, codfilialpd, codprod, seqest, refprod,
                           codempax, codfilialax, codalmox,
                           dtemitop, dtfabrop,
                           qtdsugprodop, qtdprevprodop, qtdfinalprodop,
                           codemple, codfilialle, codlote,
                           codemptm, codfilialtm, codtipomov,
                           sitop, codempcp, codfilialcp, codcompra, coditcompra, justficqtdprod, estdinamica)
        values ( :codempop, :codfilialop, :codop, :seqop,
                 :codemppd, :codfilialpd, :codprod, :seqest, :refprod,
                 :codempax, :codfilialax, :codalmox,
                 cast('today' as date), :dtfabrop,
                 :qtdsugprodop, :qtdsugprodop, :qtdfinalprodop, null,null,null, 
                 :codemptm, :codfilialtm, :codtipomov, :sitpadop,
                 :codempcp, :codfilialcp, :codcompra, :coditcompra, :justficqtdprod, :estdinamica

        );

        -- Caso o status padrão da OP seja Finalizado
        if(:sitpadop='FN') then
        begin
            -- Inicializando variaveis
            datafimprodant = :dtfabrop;
            hfimprodant = cast('now' as time);

            -- Gerando RMAS

            execute procedure eqgerarmasp(:codempop,:codfilialop,:codop,:seqop);

            -- Finalizando Fases

            for select oe.codempfs, oe.codfilialfs, oe.codfase, oe.seqof
            from ppopfase oe, ppop op
            where oe.codemp=:codempop and oe.codfilial=:codfilialop and oe.codop=:codop and oe.seqop=:seqop and
            op.codemp=oe.codemp and op.codfilial=oe.codfilial and op.codop=oe.codop and op.seqop=oe.seqop
            order by oe.seqof
            into :codempfs, :codfilialfs, :codfase, :seqof do
            begin
                -- Buscando informações da fase
                select ef.tempoef from ppestrufase ef
                where ef.codemp=:codempfs and ef.codfilial=:codfilialfs and ef.codfase=:codfase and
                ef.codemp=:codemppd and ef.codfilial=:codfilialpd and ef.codprod=:codprod and ef.seqest=:seqest
                into :tempoef;

                tempoefdias = (tempoef/3600) / 24; -- de segundos para dias...

                update ppopfase oe set oe.sitfs=:sitpadop, oe.obsfs='Fase finalizada automaticamente',
                oe.datainiprodfs=:datafimprodant, oe.hiniprodfs=:hfimprodant,
                oe.datafimprodfs=:datafimprodant + :tempoefdias, oe.hfimprodfs=:hfimprodant + :tempoef
                where oe.codemp=:codempop and oe.codfilial=:codfilialop and oe.codop=:codop and oe.seqop=:seqop and
                oe.seqof=:seqof;

                -- Carregando variáveis para proximo registro
                datafimprodant = :datafimprodant + :tempoefdias;
                hfimprodant = :hfimprodant + :tempoef;

            end
        end

    end

    -- Caso o tipo de processamento seja Detalhado (uma OP por orçamento)
    if(tipoprocess='D') then
    begin

        -- Caso o código do orçamento e código da OP tenham sido informados (deve ocorrer no modo orçamento ou a partir da segunda passagem do modo agrupado...
        if( (codorc is not null) and (codop is not null) ) then
        begin
            insert into ppopitorc (codemp, codfilial, codop, seqop, codempoc, codfilialoc, tipoorc, codorc, coditorc, qtdprod)
            values(:codempop, :codfilialop, :codop, :seqop, :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc, :qtdsugprodop);

            -- Caso a OP seja gerada já finalizada... deve mudar o status do item de orçamento para produzido.
            if(sitpadop='FN') then
            begin
                update vditorcamento io set io.sitproditorc='PD'
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.coditorc=:coditorc and io.tipoorc=:tipoorc;
            end

        end
    end
    -- Caso o tipo de processamento seja Agrupado (uma OP para vários orçamentos)
    else if(tipoprocess='A') then
    begin

        for select pt.codemp,pt.codfilial, pt.codorc, pt.coditorc, pt.tipoorc, pt.dtfabrop, pt.qtdaprod
        from ppprocessaoptmp pt, vditorcamento io, vdorcamento oc
        where pt.codempet=:codempet and pt.codfilialet=:codfilialet and pt.codest=:codest
        and io.codemp=pt.codemp and io.codfilial=pt.codfilial and io.codorc=pt.codorc and io.coditorc=pt.coditorc and io.tipoorc=pt.tipoorc
        and oc.codemp=io.codemp and oc.codfilial=io.codfilial and oc.codorc=io.codorc and oc.tipoorc=io.tipoorc
        and (:agrupdataaprov='N' or io.dtaprovitorc=:dataaprov )
        and (:agrupdtfabrop='N' or pt.dtfabrop=:dtfabrop )
        and (:agrupcodcli='N' or (oc.codorc=:codcli and oc.codempcl=:codempcl and oc.codfilialcl=:codfilialcl) )
        and io.codemppd=:codemppd and io.codfilialpd=:codfilialpd and io.codprod=:codprod
        into :codempoc, :codfilialoc, :codorc, :coditorc, :tipoorc, :dtfabrop, :qtdsugprodop do
        begin

            insert into ppopitorc (codemp, codfilial, codop, seqop, codempoc, codfilialoc, tipoorc, codorc, coditorc, qtdprod)
            values(:codempop, :codfilialop, :codop, :seqop, :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc, :qtdsugprodop);

            -- Caso a OP seja gerada já finalizada... deve mudar o status do item de orçamento para produzido.
            if(sitpadop='FN') then
            begin
                update vditorcamento io set io.sitproditorc='PD'
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.codorc=:codorc and io.coditorc=:coditorc and io.tipoorc=:tipoorc;
            end

        end
    end

    -- Carregando parametros de saída
    codopret = :codop;
    seqopret = :seqop;

    suspend;

end
^

/* Alter (PPITOPSP01) */
ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable gerarma char(1);
declare variable crefprod varchar(20);
declare variable icodprodpd integer;
declare variable nqtditop numeric(15,5);
declare variable icodemple integer;
declare variable icodfilialle smallint;
declare variable ccodlote varchar(20);
declare variable icodempfs integer;
declare variable icodfilialfs smallint;
declare variable icodfase integer;
declare variable icodemptr integer;
declare variable icodfilialtr smallint;
declare variable icodtprec integer;
declare variable icodemprp integer;
declare variable icodfilialrp smallint;
declare variable icodrecp integer;
declare variable dtempoof numeric(15,5);
declare variable iseqof smallint;
declare variable iseqppitop integer;
declare variable qtditest numeric(15,5);
declare variable qtdest numeric(15,5);
declare variable qtdprevprodop numeric(15,5);
declare variable qtdfixa char(1);
declare variable estdinamica char(1);
declare variable permiteajusteitop char(1);
declare variable iseqsubprod integer;
declare variable qtditestsp numeric(15,5);
declare variable codempts integer;
declare variable codfilialts smallint;
declare variable codtipomovsp integer;
declare variable tipoexterno char(10);
begin

    --Loop nas fases da estrutura para geração da tabela de fases da OP.
    for select ef.seqef, ef.codempfs, ef.codfilialfs, ef.codfase, ef.codemptr, ef.codfilialtr, ef.codtprec, ef.tempoef, o.estdinamica
    from ppestrufase ef, ppop o, ppestrutura e
    where
        o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
        e.codemp=o.codemppd and e.codfilial=o.codfilialpd and e.codprod=o.codprod and e.seqest=o.seqest and
        ef.codemp=e.codemp and ef.codfilial=e.codfilial and ef.codprod=e.codprod and ef.seqest=E.seqest
    into
        :iseqof, :icodempfs, :icodfilialfs, :icodfase, :icodemptr, :icodfilialtr, :icodtprec, :dtempoof, :estdinamica
    do
    begin
        -- Buscando o primeiro recurso para inserção na fase (provisório)
        select first 1 codemp, codfilial, codrecp from pprecurso r
        where r.codemp=:icodemptr and r.codfilial=:icodfilialtr and r.codtprec=:icodtprec
        into :icodemprp, :icodfilialrp, :icodrecp;

        -- Inserindo na tabela de fase por op
        insert into
            ppopfase (
                codemp, codfilial, codop, seqop, seqof, codempfs, codfilialfs, codfase, codemprp, codfilialrp, codrecp, tempoof,
                codemptr, codfilialtr, codtprec
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqof, :icodempfs, :icodfilialfs,:icodfase, :icodemprp, :icodfilialrp,
                :icodrecp,:dtempoof, :icodemptr, :icodfilialtr, :icodtprec
            );
    end

    -- Se a estrutura não for dinâmica, deve inserir os ítens

    if(coalesce(:estdinamica,'N')='N'  ) then    
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, ie.permiteajusteitest, ie.tipoexterno
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote,:permiteajusteitop,:tipoexterno
        do
        begin
            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqppitop = :iseqppitop + 1;

            if ('S'=qtdfixa) then
            begin
                nqtditop = :qtditest;
            end
            else
            begin
                nqtditop = cast(:qtditest/:qtdest as numeric(15,5) ) * cast(:qtdprevprodop as numeric(15, 5));
            end

            insert into ppitop (
                codemp, codfilial, codop, seqop, seqitop, codemppd, codfilialpd, codprod, refprod,
                codempfs, codfilialfs, codfase, qtditop, gerarma, permiteajusteitop, tipoexterno
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma,
                :permiteajusteitop, :tipoexterno
            );

        end

        -- Inserindo tabela de subprodutos

        iseqsubprod = 0;

        -- Buscando tipo de movimento para subproducao
        select codempts, codfilialts, codtipomovsp from sgprefere5 where codemp=:icodemp and codfilial=:icodfilial
        into :codempts, :codfilialts, :codtipomovsp;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitestsp, ie.codprodpd, ie.refprodpd, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditestsp, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, fs.seqof
            from
                ppitestruturasubprod ie, ppestrutura e, ppop o, ppopfase fs
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
                fs.codemp=ie.codempfs and fs.codfilial=ie.codfilialfs and fs.codfase=ie.codfase and fs.codop=o.codop and fs.seqop=o.seqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqsubprod, :icodprodpd, :crefprod, :icodempfs, :icodfilialfs, :icodfase,
        :qtditestsp,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote, :iseqof
        do
        begin

            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqsubprod = :iseqsubprod + 1;

           insert into ppopsubprod (codemp, codfilial, codop, seqop, seqsubprod, codemppd, codfilialpd, codprod,
                refprod, qtditsp, codempfs, codfilialfs, codfase, codemple, codfilialle, codlote, seqof, codemptm, codfilialtm, codtipomov
           )
           values(
                :icodemp, :icodfilial,:icodop,:iseqop, :iseqsubprod, :icodemppd, :icodfilialpd, :icodprodpd,
                :crefprod, :qtditestsp, :icodempfs, :icodfilialfs, :icodfase, :icodemple, :icodfilialle, :ccodlote, :iseqof, :codempts, :codfilialts, :codtipomovsp
           );



        end


    end

end
^

/* Alter (PPRELCUSTOSP) */
ALTER PROCEDURE PPRELCUSTOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DTESTOQ DATE,
ICODEMPMC INTEGER,
SCODFILIALMC SMALLINT,
CCODMARCA CHAR(6),
ICODEMPGP INTEGER,
SCODFILIALGP SMALLINT,
CCODGRUP CHAR(14),
CTIPOCUSTO CHAR,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER)
 RETURNS(CODPROD INTEGER,
REFPROD VARCHAR(20),
DESCPROD CHAR(100),
TIPOPROD VARCHAR(2),
SLDPROD NUMERIC(15,5),
CUSTOUNIT NUMERIC(15,5),
CUSTOTOT NUMERIC(15,5))
 AS
begin
  /* Procedure Text */
  IF (ICODEMPGP IS NOT NULL) THEN
  BEGIN
    IF (STRLEN(RTRIM(CCODGRUP))<14) THEN
       CCODGRUP = RTRIM(CCODGRUP)||'%';
  END
  IF (CTIPOCUSTO IS NULL) then
     CTIPOCUSTO = 'P';

  FOR SELECT P.CODPROD,P.REFPROD,P.DESCPROD, P.TIPOPROD
   FROM EQPRODUTO P
   WHERE P.CODEMP = :ICODEMP AND P.CODFILIAL = :SCODFILIAL AND
   ( (:ICODEMPMC IS NULL) OR (P.CODEMPMC=:ICODEMPMC AND P.CODFILIALMC=:SCODFILIALMC AND
      P.CODMARCA=:CCODMARCA) ) AND
   ((:ICODEMPGP IS NULL) OR (P.CODEMPGP=:ICODEMPGP AND P.CODFILIALGP=:SCODFILIALGP AND
      P.CODGRUP LIKE :CCODGRUP) )
   INTO :CODPROD, :REFPROD, :DESCPROD, :TIPOPROD  DO
  BEGIN
     SELECT CUSTOUNIT,SLDPROD FROM PPCUSTOPRODSP(:ICODEMP,
        :SCODFILIAL, :CODPROD, :DTESTOQ, :CTIPOCUSTO, :ICODEMPAX,
        :SCODFILIALAX, :ICODALMOX, 'N')
       INTO :CUSTOUNIT, :SLDPROD;
     CUSTOTOT = CUSTOUNIT * SLDPROD;
     SUSPEND;
  END
end
^

/* Alter (PVVERIFCAIXASP) */
ALTER PROCEDURE PVVERIFCAIXASP(ICODCAIXA INTEGER,
ICODEMP INTEGER,
SCODFILIAL SMALLINT,
DDTAMOV DATE,
SCODFILIALUS SMALLINT,
CIDUSU CHAR(8))
 RETURNS(IRETORNO INTEGER)
 AS
DECLARE VARIABLE CTMP CHAR(1);
DECLARE VARIABLE CIDUSUANT CHAR(8);
DECLARE VARIABLE SCODFILIALANT SMALLINT;
DECLARE VARIABLE DULTMOV DATE;
DECLARE VARIABLE INROMOV INTEGER;
BEGIN
  iRetorno = -1;
  SELECT FIRST 1 MC.TIPOMOV, MC.IDUSU, MC.CODFILIALUS, MC.DTAMOV, MC.NROMOV
    FROM PVMOVCAIXA MC
    WHERE MC.CODCAIXA=:ICODCAIXA  AND
       MC.CODEMP=:ICODEMP AND MC.CODFILIAL=:SCODFILIAL
    ORDER BY MC.DTAMOV DESC, MC.NROMOV DESC
    INTO :cTMP, :cIDUSUANT, :SCodFilialAnt, :DULTMOV, :INROMOV;

  if ( (DULTMOV IS NULL) OR (DDTAMOV = DULTMOV) ) then
  BEGIN
     if ( (cTMP IS NULL) OR (cTMP = 'F') ) then
     BEGIN
       iRetorno = 0; /*Caixa OK*/
       SUSPEND;
       EXIT;
     END
     else if (cTMP = 'Z') then
     BEGIN
       iRetorno = 11; /*Jah foi realizada reduçãoZ neste dia*/
       SUSPEND;
       EXIT;
     END
     else if (cTMP in ('A','S','U','V')) then
     BEGIN
       if ( (cIDUSU=cIDUSUANT) AND (SCodFilialUs=SCodFilialAnt) )then
          iRetorno = 1; /*Caixa aberto*/
       else
          iRetorno = 12; /* Caixa aberto por outro usuatio */
       SUSPEND;
       EXIT;
     END
  END
  else if (DDTAMOV > DULTMOV) then
  BEGIN
     if ( cTMP = 'F' ) then /* Caixa anterior fechado sem redução Z */
     BEGIN                  /* O usuário deverá digitar a leitura da memória fiscal */
       iRetorno = 2;
       SUSPEND;
       EXIT;
     END
     else if (cTMP = 'Z') then
     BEGIN
       iRetorno = 0; /* Caixa anterior fechado corretamente */
       SUSPEND;
       EXIT;
     END
     else /* Caixa anterior deverá ser fechado */
     BEGIN
       iRetorno = 3; /* devera fechar o caixa*/
       SUSPEND;
       EXIT;
     END
  END
  ELSE
  BEGIN /*Tentativa de abertura de caixa retroativo*/
     iRetorno = 13;
  END
  SUSPEND;
END
^

/* Alter (SGCALCVENCSP) */
ALTER PROCEDURE SGCALCVENCSP(CODEMP INTEGER,
DTBASE DATE,
DTVENC DATE,
REGRVCTO CHAR,
RVSAB CHAR,
RVDOM CHAR,
RVFER CHAR,
RVDIA CHAR,
DIAVCTO SMALLINT,
DIASVCTO SMALLINT)
 RETURNS(DTVENCRET DATE)
 AS
declare variable maxloop smallint;
declare variable curloop smallint;
declare variable dttemp date;
declare variable valorinc smallint;
declare variable codfilial smallint;
declare variable curloop02 smallint;
begin
  DTVENCRET = DTVENC;

  -- Busca o codigo da filial que será utilizado em duas regras
  if ( (REGRVCTO<>'N') and (RVFER = 'S') ) then
  begin
     SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'SGFERIADO') INTO :CODFILIAL;
  end

  -- Logica pra dia fixo
  if ( RVDIA = 'F' ) then
  begin
     MAXLOOP = 2;
     CURLOOP = 1;
     while ( CURLOOP<MAXLOOP ) do
     begin
        DTTEMP = DTVENCRET + ( 1 - EXTRACT(DAY FROM DTVENCRET) );
        DTTEMP = DTTEMP + DIAVCTO - 1;
        -- Objetivo do loop é voltar quando o dia fixo for no final do mês.
        while ( ( ( extract( month from DTVENCRET ) ) < ( extract( month from DTTEMP ) ) ) or
                ( ( extract( year from DTVENCRET ) ) < ( extract( year from DTTEMP ) ) ) ) do
        begin
           DTTEMP = DTTEMP - 1;
        end
        DTVENCRET = DTTEMP;
        if ( DTVENCRET<DTBASE ) then
        begin
           DTVENCRET = addmonth(DTVENCRET,1);
        end
        else
        begin
           break;
        end
        CURLOOP = CURLOOP + 1;
     end
  end
  -- Logica para ajustar dias uteis
  else if ( RVDIA = 'U' ) then
  begin
     while ( ( extract(month from DTVENCRET)<=extract(month from DTBASE) ) and
             ( extract(year from DTVENCRET)<=extract(year from DTBASE ) ) and
             ( DIASVCTO>0 ) ) do
     begin
        DTVENCRET = addmonth(DTVENCRET,1);
     end
     DTTEMP = DTVENCRET + ( 1 - EXTRACT(DAY FROM DTVENCRET) );
     DTVENCRET = DTTEMP;
     if (DIAVCTO<28) then -- Se for menor que dia 28 calcula o dia útil
     begin                -- Caso contrário vai será o último dia útil.
        CURLOOP02 = 1;
        while (CURLOOP02<12) do
        begin
           CURLOOP = 1;
           MAXLOOP = DIAVCTO;
           while ( CURLOOP < MAXLOOP )  do
           begin
              DTTEMP = null;
              if (RVFER='S') then
              begin
                 SELECT DATAFER FROM SGFERIADO F
                   WHERE F.CODEMP=:CODEMP AND F.CODFILIAL=:CODFILIAL AND
                     F.DATAFER=:DTVENCRET AND F.BANCFER='S'
                       INTO :DTTEMP;
              end
              if ( ( (extract( weekday from DTVENCRET )=0) and (RVDOM='S')  ) or
                   ( (extract( weekday from DTVENCRET )=6) and (RVSAB='S')  ) or
                   ( (RVFER='S') and (DTVENCRET=DTTEMP) ) ) then
              begin
                 DTVENCRET = DTVENCRET + 1;
              end
              else
              begin

                 DTVENCRET = DTVENCRET + 1;
                 CURLOOP = CURLOOP + 1;
              end
           end
           if (DTVENCRET<=DTBASE) then
           begin
              DTTEMP = DTVENCRET + ( 1 - EXTRACT(DAY FROM DTVENCRET) );
              DTVENCRET = DTTEMP;
              while ( ( extract(month from DTVENCRET)<=extract(month from DTBASE) ) and
                  ( extract(year from DTVENCRET)<=extract(year from DTBASE ) ) ) do
              begin
                 DTVENCRET = addmonth(DTVENCRET,1);
              end
           end
           else
           begin
              break;
           end
           CURLOOP02 = CURLOOP02 + 1;
        end
     end
     else
     begin
        DTTEMP = addmonth(DTVENCRET,1);
        DTVENCRET = DTTEMP - 1;
     end
  end

--  if (extract(month from dtvencret)=8) then
--  begin
--     exception fnreceberex02 'VENCIMENTO: '||CAST(dtvencret AS CHAR(10))||'REGRAVCTO = ' || REGRVCTO || 'REGRA DIA '|| RVDIA;

--  end


  -- Logica para ajustar finais de semana e feriados
  if ( (REGRVCTO<>'N') ) then
  begin
     MAXLOOP = 30;
     CURLOOP = 1;
     if ( REGRVCTO='A' ) then
     begin
        VALORINC = -1;
     end
     else
     begin
        VALORINC = 1;
     end
     while (CURLOOP<MAXLOOP) do
     begin
        if ( ( (extract( weekday from dtvencret )=0) and (RVDOM='S')  ) or
             ( (extract( weekday from dtvencret )=6) and (RVSAB='S')  ) ) then
        begin
           DTVENCRET = DTVENCRET + VALORINC;
        end
        else if (RVFER='S') then
        begin
           DTTEMP = NULL;
           SELECT DATAFER FROM SGFERIADO F
             WHERE F.CODEMP=:CODEMP AND F.CODFILIAL=:CODFILIAL AND
               F.DATAFER=:DTVENCRET AND F.BANCFER='S'
                 INTO :DTTEMP;
           if ( (DTTEMP IS NOT NULL) AND (DTTEMP=DTVENCRET) ) then
           begin
             DTVENCRET = DTVENCRET + VALORINC;
           end
           else
           begin
             break;
           end
        end
        else
        begin
           break;
        end
        CURLOOP = CURLOOP + 1;
     end
  end
  suspend;
end
^

/* empty dependent procedure body */
/* Clear: SGATUALIZABDSP for: SGDADOSINISP */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
 BEGIN EXIT; END
^

/* Alter (SGDADOSINISP) */
ALTER PROCEDURE SGDADOSINISP(ICODEMP INTEGER)
 AS
declare variable crole_name char(31);
declare variable inumemp integer;
declare variable cidgrpusu char(8);
declare variable cidusu char(8);
declare variable csql varchar(256);
begin
  /*CTEMP = printLog('Entrou em dados ini '||cast(ICODEMP AS CHAR(10)));*/
  SELECT CODEMP FROM SGEMPRESA WHERE CODEMP=:ICODEMP INTO :INUMEMP;
  IF (INUMEMP IS NULL) THEN
  BEGIN
   /* CTEMP = printLog('Empresa é nulo '||cast(ICODEMP AS CHAR(10))); */
     INSERT INTO SGEMPRESA (CODEMP,RAZEMP)
        VALUES (:ICODEMP,'EMPRESA '||CAST(:ICODEMP AS CHAR(10) ));
     INSERT INTO SGFILIAL (CODEMP, CODFILIAL, RAZFILIAL,
        NOMEFILIAL,MZFILIAL)
       VALUES (:ICODEMP,1,'EMPRESA'||CAST(:ICODEMP AS CHAR(10) )||' - MATRIZ',
        'EMPRESA'||CAST(:ICODEMP AS CHAR(10) )||' - MATRIZ' ,'S');
    /*CTEMP = printLog('Inseriu empresa e filial '||cast(ICODEMP AS CHAR(10)));*/
  END
  SELECT RDB$ROLE_NAME FROM RDB$ROLES
     WHERE RDB$ROLE_NAME='ADM'
       INTO :CROLE_NAME;
  IF (CROLE_NAME IS NULL) THEN
  BEGIN
     CSQL = 'CREATE ROLE ADM';
     EXECUTE STATEMENT CSQL;
  END
  /* CTEMP = printLog('cadastrou roles'); */
  FOR SELECT CODEMP FROM SGEMPRESA WHERE CODEMP=:ICODEMP INTO :INUMEMP DO
  BEGIN
  /* CTEMP = printLog('LOOP DE EMPRESA PARA CADASTRAR GRUPO'||CAST(INUMEMP AS CHAR(10)));*/

     SELECT IDGRPUSU FROM SGGRPUSU
       WHERE CODEMP=:INUMEMP AND IDGRPUSU='ADM'
         INTO :CIDGRPUSU;
     IF (CIDGRPUSU IS NULL) THEN
        INSERT INTO SGGRPUSU ( CODEMP, CODFILIAL, IDGRPUSU, NOMEGRPUSU)
          VALUES ( :INUMEMP, 1, 'ADM', 'ADMINISTRADOR' );
     SELECT IDUSU FROM SGUSUARIO
       WHERE CODEMP=:INUMEMP AND IDUSU='sysdba' AND CODFILIAL=1
         INTO :CIDUSU;
     IF (CIDUSU IS NULL) THEN
        INSERT INTO SGUSUARIO (CODEMP, CODFILIAL, IDUSU, NOMEUSU, IDGRPUSU)
          VALUES (:INUMEMP, 1, 'sysdba', 'ADMINISTRADOR DBA', 'ADM');
  /* CTEMP = printLog('CADASTROU USUARIOS');*/

  END

  suspend;
end
^

/* Alter (SGESTACAOIMPSP01) */
ALTER PROCEDURE SGESTACAOIMPSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEST INTEGER,
SNROIMP SMALLINT,
CPADIMP CHAR)
 AS
begin
  /* Procedure que ajusta impressora padrão */
  IF ( (CPADIMP IS NOT NULL) AND (CPADIMP='S') ) THEN
     UPDATE SGESTACAOIMP SET IMPPAD='N' WHERE CODEMP=:ICODEMP AND
        CODFILIAL=:SCODFILIAL AND CODEST=:ICODEST AND NROIMP!=:SNROIMP;
  suspend;
end
^

/* Alter (SGGRANTADMSP) */
ALTER PROCEDURE SGGRANTADMSP(ICODEMP INTEGER)
 AS
declare variable csql varchar(200);
declare variable cidobj char(30);
begin
  /* Procedure que dá grant para o grupo ADM */

  FOR SELECT DISTINCT O.IDOBJ
  FROM SGOBJETO O, RDB$RELATION_CONSTRAINTS RC, RDB$RELATIONS R
    WHERE O.CODEMP=:ICODEMP AND O.TIPOOBJ='TB'
    AND R.RDB$RELATION_NAME = RC.RDB$RELATION_NAME
    AND RC.RDB$RELATION_NAME=O.IDOBJ
    INTO :CIDOBJ DO
  BEGIN
      CSQL = 'GRANT DELETE, INSERT, SELECT, UPDATE ON TABLE '||CIDOBJ||
        ' TO ROLE ADM';
      EXECUTE STATEMENT CSQL;
  END
  suspend;
end
^

/* Alter (SGINICONSP) */
ALTER PROCEDURE SGINICONSP(ICODEMP INTEGER,
CIDUSU CHAR(8),
ICODFILIALSEL SMALLINT,
ICODTERM SMALLINT)
 RETURNS(SRET SMALLINT)
 AS
DECLARE VARIABLE ICONECTADO INTEGER;
DECLARE VARIABLE ICODFILIALSELCX SMALLINT;
DECLARE VARIABLE ICODEMPTMP INTEGER;
DECLARE VARIABLE ICODFILIAL SMALLINT;
begin
  /* Procedure para inicialização da conexão com o banco de dados */
  SRET = 0;
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Vai verificar o usuário');*/
  CIDUSU = lower(CIDUSU);
  IF ( CIDUSU='sysdba' ) THEN
  BEGIN
/*     CTEMP = printLog('Usuário sysdba');*/

     SELECT CODEMP FROM SGEMPRESA WHERE CODEMP=:ICODEMP INTO :ICODEMPTMP;
     IF (ICODEMPTMP IS NULL) THEN
        EXECUTE PROCEDURE SGATUALIZABDSP(:ICODEMP);
  END
  SELECT CODFILIAL FROM SGUSUARIO WHERE IDUSU=:cIdUsu AND
      CODEMP=:ICODEMP  INTO :iCodFilial;
/*  CTEMP = printLog('O usuário é = '||cIdUsu); */

/*  CTEMP = printLog('Contador de usuários '||cIdUsu); */

  if (iCodFilial is null) then
    EXCEPTION SGCONEXAOEX02;

  if (iCodFilialSel is not null) then
  begin
      SRET = 1;
      SELECT CONECTADO, CODFILIALSEL FROM SGCONEXAO
        WHERE NRCONEXAO=CURRENT_CONNECTION INTO :iConectado, iCodFilialSelCX;
      if (iConectado is null) then
          INSERT INTO SGCONEXAO (NRCONEXAO, CODEMP,CODFILIAL,IDUSU,CODFILIALSEL,CONECTADO,CODTERM)
            VALUES (CURRENT_CONNECTION, :iCodemp, :iCodfilial, :cIdusu, :iCodfilialSel, 1, :iCodTerm);
      else
      begin
          if (iConectado<=0) then
             UPDATE SGCONEXAO SET CONECTADO=1, CODFILIALSEL=:iCodfilialSel, CODTERM = :iCodTerm,
               CODEMP=:iCodemp, CODFILIAL=:iCodfilial , IDUSU=:cIdUsu
               WHERE NRCONEXAO=CURRENT_CONNECTION;
          else
          begin
             if (not (iCodfilialSelCX=iCodfilialSel) ) then
                EXCEPTION SGCONEXAOEX01;
             else
                UPDATE SGCONEXAO SET CONECTADO=CONECTADO+1, CODTERM = :iCodTerm,
                   CODEMP=:iCodemp, CODFILIAL=:iCodfilial, IDUSU=:cIdUsu
                WHERE NRCONEXAO=CURRENT_CONNECTION;
          end
      end 
  end
  suspend;
end
^

/* Alter (SGRETMULTIALMOXSP) */
ALTER PROCEDURE SGRETMULTIALMOXSP(ICODEMP INTEGER)
 RETURNS(CMULTIALMOX CHAR)
 AS
DECLARE VARIABLE SCODFILIALPF SMALLINT;
begin
  /* Procedure Text */
  SELECT RF.ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'SGPREFERE1') RF
     INTO :SCODFILIALPF;
  SELECT MULTIALMOX FROM SGPREFERE1 WHERE CODEMP=:ICODEMP AND
     CODFILIAL=:SCODFILIALPF
     INTO :CMULTIALMOX;

/* Condição que evita null no valor do flag */
  CMULTIALMOX = coalesce(CMULTIALMOX,'N');

  suspend;
end
^

/* empty dependent procedure body */
/* Clear: VDGERACOMISSAOSP for: SGRETMULTICOMISSP */
ALTER PROCEDURE VDGERACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRCOMIITREC NUMERIC(15,5),
DDTVENCITREC DATE)
 AS
 BEGIN EXIT; END
^

/* Alter (SGRETMULTICOMISSP) */
ALTER PROCEDURE SGRETMULTICOMISSP(ICODEMP INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER)
 RETURNS(CMULTICOMIS CHAR)
 AS
declare variable scodfilialpf smallint;
declare variable cmulticomistm char(1);
begin
    select rf.icodfilial from sgretfilial(:icodemp, 'SGPREFERE1') rf
        into :scodfilialpf;

    /* Verifica se o mecanismo de multiplos comissionados está habilitado nas preferências gerais */

    select pf.multicomis from sgprefere1 pf
        where pf.codemp=:icodemp and pf.codfilial=:scodfilialpf
    into :cmulticomis;

    /* Caso esteja habilitado nas preferências, verifica se o tipo de movimento suporta multiplos comissionados */
    if(:cmulticomis = 'S') then
    begin

        select tm.mcomistipomov from eqtipomov tm
            where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
        into :cmulticomistm;

        if(:cmulticomis is null or :cmulticomistm='N') then
        begin
            cmulticomis = 'N';
        end

    end

  cmulticomis = coalesce(cmulticomis,'N');

  suspend;
end
^

/* Alter (SGSETAGENDASP) */
ALTER PROCEDURE SGSETAGENDASP(CODAGD INTEGER,
CODEMP INTEGER,
DTAINIAGD DATE,
HRINIAGD TIME,
DTAFIMAGD DATE,
HRFIMAGD TIME,
ASSUNTOAGD CHAR(50),
DESCAGD VARCHAR(10000),
CODFILIALTA SMALLINT,
CODTIPOAGD INTEGER,
PRIORAGD SMALLINT,
CODAGE INTEGER,
TIPOAGE CHAR(5),
CODFILIALAE SMALLINT,
CODAGEEMIT INTEGER,
TIPOAGEEMIT CHAR(5),
CAAGD CHAR(2),
SITAGD CHAR(2),
RESOLUCAOMOTIVO VARCHAR(10000),
CODAGDAR INTEGER,
DIATODO CHAR)
 RETURNS(IRET INTEGER)
 AS
DECLARE VARIABLE IFILIALAGD INTEGER;
begin
  select ICODFILIAL from SGRETFILIAL(:CODEMP,'SGAGENDA') INTO IFILIALAGD;
  IF (CODAGD = 0) THEN
  BEGIN

    SELECT ISEQ FROM SPGERANUM(:CODEMP,:IFILIALAGD,'AG') INTO CODAGD;

    if(codagdar is not null ) then  -- Agendamento vinculado (repetição)
    begin
        insert into sgagenda (
            codemp, codfilial, codage, tipoage, codagd,
            dataagd, dtainiagd, hriniagd, dtafimagd, hrfimagd,
            assuntoagd, descagd, codempta, codfilialta, codtipoagd, prioragd, sitagd,
            codempae, codfilialae, codageemit, tipoageemit, caagd, resolucaomotivo,
            codempar,codfilialar,codagear,tipoagear,codagdar,diatodo)
        values (
            :codemp, :ifilialagd, :codage, :tipoage, :codagd,
            cast('today' as date), :dtainiagd, :hriniagd, :dtafimagd, :hrfimagd,
            :assuntoagd, :descagd, :codemp, :codfilialta, :codtipoagd, :prioragd, :sitagd,
            :codemp, :codfilialae, :codageemit, :tipoageemit, :caagd, :resolucaomotivo,
            :codemp,:ifilialagd,:codage,:tipoage,:codagdar,:diatodo
        );
    end
    else -- Agendamento simples, sem vínvulo (repetição)
    begin
        insert into sgagenda (
            CODEMP,CODFILIAL,CODAGE,TIPOAGE,CODAGD,
            DATAAGD,DTAINIAGD,HRINIAGD,DTAFIMAGD,HRFIMAGD,
            ASSUNTOAGD,DESCAGD,CODEMPTA,CODFILIALTA,CODTIPOAGD,PRIORAGD,SITAGD,
            CODEMPAE,CODFILIALAE,CODAGEEMIT,TIPOAGEEMIT,CAAGD, RESOLUCAOMOTIVO, DIATODO)
        values (
            :CODEMP,:IFILIALAGD,:CODAGE,:TIPOAGE,:CODAGD,
            CAST('today' AS DATE),:DTAINIAGD,:HRINIAGD,:DTAFIMAGD,:HRFIMAGD,
            :ASSUNTOAGD,:DESCAGD,:CODEMP,:CODFILIALTA,:CODTIPOAGD,:PRIORAGD,:SITAGD,
            :CODEMP,:CODFILIALAE,:CODAGEEMIT,:TIPOAGEEMIT,:CAAGD, :RESOLUCAOMOTIVO,:DIATODO

        );
    end
  end
  else
    update sgagenda set
      DTAINIAGD=:DTAINIAGD,HRINIAGD=:HRINIAGD,
      DTAFIMAGD=:DTAFIMAGD,HRFIMAGD=:HRFIMAGD,
      ASSUNTOAGD=:ASSUNTOAGD,DESCAGD=:DESCAGD,
      CODEMPTA=:CODEMP,CODFILIALTA=:CODFILIALTA,CODTIPOAGD=:CODTIPOAGD,
      PRIORAGD=:PRIORAGD,CAAGD=:CAAGD,
      SITAGD=:SITAGD,
      RESOLUCAOMOTIVO=:RESOLUCAOMOTIVO,
      codage=:codage, diatodo=:diatodo
      WHERE CODEMP=:CODEMP AND CODFILIAL=:IFILIALAGD
      AND CODAGD=:CODAGD AND TIPOAGE=:TIPOAGE;
  IRET = CODAGD;
  SUSPEND;
END
^

/* Alter (SGUPMENUSP01) */
ALTER PROCEDURE SGUPMENUSP01(ICODSIS INTEGER,
CDESCSIS CHAR(200),
ICODMODU INTEGER,
CDESCMODU CHAR(200),
ICODMENU INTEGER,
CDESCMENU CHAR(200),
CNOMEMENU CHAR(200),
CACAOMENU CHAR(200),
ICODSISPAI INTEGER,
ICODMODUPAI INTEGER,
ICODMENUPAI INTEGER)
 AS
declare variable itmp integer;
begin
    
    -- Verifica se o já existe registro do sistema;
    select count(*) from sgsistema where codsis = :icodsis into :itmp;

    -- Se o sistema não existe deve inseri-lo
    if ( (itmp is null) or (itmp=0) ) then
    begin
        insert into sgsistema(codsis, descsis) values(:icodsis, :cdescsis);
    end
    else
    -- Atualiza descrição caso o sistema já exista
    begin
        update sgsistema si set si.descsis=:cdescsis where si.codsis=:icodsis;
    end

    -- Verifica se já existe registro do módulo
    select count(*) from sgmodulo where codsis=:icodsis and codmodu = :icodmodu into :itmp;

    -- Se o modulo não existe deve inseri-lo
    if ( (itmp is null) or (itmp=0) ) then
    begin
        insert into sgmodulo(codsis, codmodu, descmodu) values(:icodsis, :icodmodu, :cdescmodu);
    end
    else
    -- Atualiza descrição caso o módulo já exista
    begin
        update sgmodulo set descmodu=:cdescmodu where codsis=:icodsis and codmodu=:icodmodu;
    end

    -- Verifica se já existe registro do menu
    select count(*) from sgmenu where codsis =:icodsis and codmodu =:icodmodu and codmenu =:icodmenu into :itmp;

    -- Se o menu existir deve atualizar
    if ( (itmp is not null) and (itmp > 0) ) then
    begin
        update sgmenu set descmenu = :cdescmenu, acaomenu = :cacaomenu, codsispai = :icodsispai, codmodupai = :icodmodupai, codmenupai = :icodmenupai
        where codsis = :icodsis and codmodu = :icodmodu and codmenu = :icodmenu;
    end
    else
    -- se menu não existir, deve inseri-lo
    begin
        insert into sgmenu(codsis, codmodu, codmenu, descmenu, nomemenu, acaomenu, codsispai, codmodupai, codmenupai)
        values (:icodsis, :icodmodu, :icodmenu, :cdescmenu, :cnomemenu, :cacaomenu, :icodsispai, :icodmodupai, :icodmenupai );
    end

end
^

/* Alter (TKGERACAMPANHACTO) */
ALTER PROCEDURE TKGERACAMPANHACTO(TIPOCTO CHAR,
CODEMPCA INTEGER,
CODFILIALCA SMALLINT,
CODCAMP CHAR(13),
CODEMPCO INTEGER,
CODFILIALCO SMALLINT,
CODCTO INTEGER,
CODEMPAT INTEGER,
CODFILIALAT SMALLINT,
CODATIV INTEGER,
SITHISTTK CHAR(2),
DESCHISTTK VARCHAR(1000))
 AS
declare variable seqcampcto integer; /* Código do contato pra validação. */
declare variable seqsitcamp integer;
declare variable codfilialhi smallint;
declare variable codhisttk integer;
declare variable codempae integer;
declare variable codfilialae smallint;
declare variable codatend integer; /* Código do atendente. */
declare variable codempus integer;
declare variable codfilialus smallint;
declare variable idusu char(8); /* Id do usuário */
begin

    select icodfilial from sgretfilial(:codempca,'TKHISTORICO') into codfilialhi;
    select iseq from spgeranum(:codempca,:codfilialhi,'HI') into codhisttk;
    select codempusu, codfilialusu, idusus from sgretinfousu(:codempca, user) where codempusu=:codempca into
            :codempus, :codfilialus, :idusu;

    select first 1 codemp, codfilial, codatend from atatendente
            where codempus=:codempus and codfilialus=:codfilialus and idusu=:idusu
    into codempae, codfilialae, codatend;

    if(:codatend is null) then
    begin
        exception TKGERACAMANHACTO01 ' - ID: ' || idusu || ' - User: '|| user ;
    end

    -- Verifica se o contato já foi vinculado à campanha

    if ( tipocto = 'O' ) then
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempco=:codempco and cc.codfilialco=:codfilialco and cc.codcto=:codcto
           into :seqcampcto;
    end
    else
    begin
       select max(seqcampcto) from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           and cc.codempcl=:codempco and cc.codfilialcl=:codfilialco and cc.codcli=:codcto
           into :seqcampcto;
    end

    if ( (:seqcampcto is null) or (:seqcampcto=0) ) then
    begin
       select max(coalesce(seqcampcto,0)+1) seqcampcto from tkcampanhacto cc
           where cc.codemp=:codempca and cc.codfilial=:codfilialca and cc.codcamp=:codcamp
           into :seqcampcto;
        if (seqcampcto is null) then
        begin
           seqcampcto = 1;
        end
        if ( tipocto = 'O' ) then
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempco, codfilialco, codcto)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
        end
        else 
        begin
            insert into tkcampanhacto (codemp, codfilial, codcamp, seqcampcto, codempcl, codfilialcl, codcli)
                values(:codempca, :codfilialca, :codcamp, :seqcampcto, :codempco, :codfilialco, :codcto);
               -- exception TKGERACAMANHACTO01 'teste';
        end

    end

    seqsitcamp = 0;
    select max(sc.seqsitcamp) from tksitcamp sc
            where sc.codemp=:codempca and sc.codfilial=:codfilialca and
                sc.codcamp=:codcamp and sc.tipocto=:tipocto
                into :seqsitcamp;

    if(:seqsitcamp is null) then
    begin
        seqsitcamp = 0;
    end

    seqsitcamp = seqsitcamp + 1;

    if ( tipocto = 'O' ) then
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempco,codfilialco,codcto,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end
    else
    begin
        insert into tksitcamp (codemp,codfilial,codcamp,codempcl,codfilialcl,codcli,seqsitcamp,
                codempav,codfilialav,codativ, tipocto)
            values (:codempca,:codfilialca,:codcamp,:codempco,:codfilialco,:codcto,:seqsitcamp,
                :codempat,:codfilialat ,:codativ, :tipocto );
    end

    -- Inserindo histórico
    
    if ( tipocto = 'O' ) then 
    begin 
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempco,codfilialco,codcto,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
    else
    begin
        insert into tkhistorico (codemp, codfilial,codhisttk,horahisttk,datahisttk,
                         codempcl,codfilialcl,codcli,deschisttk,codempae,codfilialae,codatend,
                         sithisttk,tipohisttk, tipocto)
            values (:codempca,:codfilialhi,:codhisttk,cast('now' as time),cast('now' as date),
                      :codempco,:codfilialco,:codcto,:deschisttk,:codempae,:codfilialae,:codatend,
                          :sithisttk,'C', :tipocto);
    end
   

end
^

/* empty dependent procedure body */
/* Clear: FNESTORNACOMISSAOSP for: VDADICCOMISSAOSP */
ALTER PROCEDURE FNESTORNACOMISSAOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODREC INTEGER,
NPARCITREC SMALLINT)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDESTORNACOMISSAOSP for: VDADICCOMISSAOSP */
ALTER PROCEDURE VDESTORNACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
ICODVEND INTEGER,
DINI DATE,
DFIM DATE,
CORDEM CHAR)
 AS
 BEGIN EXIT; END
^

/* Alter (VDADICCOMISSAOSP) */
ALTER PROCEDURE VDADICCOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRVENDACOMI NUMERIC(15,5),
NVLRCOMI NUMERIC(15,5),
DDATACOMI DATE,
DDTCOMPCOMI DATE,
DDTVENCCOMI DATE,
CTIPOCOMI CHAR,
CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVEND INTEGER)
 AS
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
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod varchar(20);
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
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,null)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT,null,null,null,null)
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
CODORC INTEGER,
COMPONENTES CHAR,
SERVICOS CHAR,
NOVOS CHAR)
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod varchar(20);
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
declare variable refprodir varchar(20);
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

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR,
IQTDITVENDA NUMERIC(15,5),
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
declare variable percicmsitvenda numeric(15,5);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(15,5);
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
declare variable percred numeric(15,5);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(15,5);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,5);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod varchar(20);
declare variable stipoprod varchar(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable qtditorc numeric(15,5);
declare variable calcstcm char(1);
declare variable aliqicmsstcm numeric(9,2);
declare variable vlricmsstcalc numeric(15,5);
begin
-- Inicialização de variaveis
    CALCSTCM = 'N';
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
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc, it.qtditorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda, :qtditorc;

    -- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

    -- Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)

    select codempif,codfilialif,codfisc,coditfisc
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',null,null,null,null,null)
    into CODEMPIF,CODFILIALIF,CODFISC,CODITFISC;


-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,
    :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,:coditfisc)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda (já sabe o coditfisc)

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss,coalesce(calcstcm,'N'),aliqicmsstcm
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA, CALCSTCM, ALIQICMSSTCM;

   
   
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
     if (:iqtditvenda<>:qtditorc) then
    begin
       VLRDESCITVENDA = (:VLRDESCITVENDA/:QTDITORC*:IQTDITVENDA);
    end
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
            VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
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
    -- Bloco comentado, pois já buscou o percentual do iss através da procedure.
   --     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
   --     into PERCISSITVENDA;

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
            --Calculo do ICMS ST para fora de mato grosso.

            
            if(calcstcm = 'N') then
            begin
                
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
            --Calculo do ICMS ST para o mato grosso.
            else
            begin
                if(percred>0 and redbaseicmsst='S') then
                begin
                   vlricmsstcalc=0;
                -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                   vlricmsstcalc = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100) );
                   vlrbaseicmsstitvenda =   (vlricmsitvenda + vlricmsstcalc)/(PERCICMSST/100);
                   

                end
                else
                begin
                    -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                    vlricmsstcalc = ( (coalesce(vlrbaseicmsbrutitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
                    
                    vlrbaseicmsstitvenda = ((vlricmsitvenda  + vlricmsstcalc )/(:PERCICMSST/100));
                  
                end

           
                VLROUTRASITVENDA = 0;
                VLRISENTASITVENDA = 0;
                VLRICMSSTITVENDA = ( (coalesce(vlrbaseicmsitvenda,0) + coalesce(vlripiitvenda,0) ) * (coalesce(aliqicmsstcm,0)/100)  );
           

            end 
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
        margemvlagritvenda,vlrbaseicmsbrutitvenda, calcstcm)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda, :calcstcm);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Alter (VDADICVENDAORCSP) */
ALTER PROCEDURE VDADICVENDAORCSP(ICODORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
NEWCODVENDA INTEGER,
DTSAIDAVENDA DATE)
 RETURNS(IRET INTEGER)
 AS
declare variable icodvenda integer;
declare variable ifilialvd smallint;
declare variable icodtipomov integer;
declare variable ifilialtm smallint;
declare variable sserie char(4);
declare variable ifilialse smallint;
declare variable sstatusvenda char(2);
declare variable dperccomvend numeric(9,2);
declare variable icodvend integer;
declare variable icodfilialvd integer;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodplanopag integer;
declare variable icodfilialpg integer;
declare variable usapedseq char(1);
declare variable iconta integer;
declare variable icodclcomis integer;
declare variable icodfilialcm integer;
declare variable vlrdescorc numeric(15,5);
declare variable vlrfreteorc numeric(15,5);
declare variable codemptn integer;
declare variable codfilialtn smallint;
declare variable codtran integer;
declare variable tipofrete char(1);
declare variable adicfrete char(1);
BEGIN

/*Cod. Tipo Mov e Sequencia:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'EQTIPOMOV') INTO IFILIALTM;
  SELECT COUNT(P.TIPOPROD) FROM EQPRODUTO P, VDITORCAMENTO I WHERE
    P.CODPROD=I.CODPROD AND P.CODFILIAL=I.CODFILIALPD AND P.CODEMP=I.CODEMPPD
    AND P.TIPOPROD = 'S' AND I.CODORC=:ICODORC
    AND I.CODFILIAL=:ICODFILIAL AND I.CODEMP=:ICODEMP INTO ICONTA;
    
  IF (ICONTA > 0) THEN
    SELECT CODTIPOMOV4,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;
  IF (ICODTIPOMOV IS NULL) THEN  /* CASO AINDA O IF DE CIMA NAO TENHA PREECHIDO... */
    SELECT CODTIPOMOV3,USAPEDSEQ FROM SGPREFERE1
      WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO ICODTIPOMOV,USAPEDSEQ;

/*Informações basicas:*/

  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'VDVENDA') INTO IFILIALVD;
  IF (USAPEDSEQ='S') THEN
    SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALVD,'VD') INTO ICODVENDA;
  ELSE
    IF ((:NEWCODVENDA > 0) AND (:NEWCODVENDA IS NOT NULL)) THEN
      ICODVENDA = :NEWCODVENDA;
    ELSE
      SELECT MAX(CODVENDA)+1 FROM VDVENDA
        WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALVD
        INTO ICODVENDA;

/*Serie:*/
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'LFSERIE') INTO IFILIALSE;
  SELECT SERIE FROM EQTIPOMOV WHERE CODEMP=:ICODEMP AND CODFILIAL=:IFILIALTM AND CODTIPOMOV=:ICODTIPOMOV INTO SSERIE;

/*Status:*/
  SSTATUSVENDA = 'P1';

/*Busca no orçamento:*/
  SELECT O.CODFILIALVD,O.CODVEND,O.CODFILIALCL,O.CODCLI,O.CODFILIALPG,
    O.CODPLANOPAG,VE.PERCCOMVEND,O.CODCLCOMIS,O.CODFILIALCM, o.vlrdescorc
    , coalesce(O.VLRFRETEORC,0), O.CODEMPTN, O.CODFILIALTN, O.CODTRAN
    , o.tipofrete, o.adicfrete
    FROM VDORCAMENTO O, VDVENDEDOR VE WHERE O.CODORC=:ICODORC
    AND O.CODFILIAL=:ICODFILIAL AND O.CODEMP=:ICODEMP
    AND VE.CODEMP=O.CODEMP AND VE.CODFILIAL=O.CODFILIALVD
    AND VE.CODVEND=O.CODVEND INTO
           :ICODFILIALVD,:ICODVEND,:ICODFILIALCL,:ICODCLI
           ,:ICODFILIALPG,:ICODPLANOPAG,DPERCCOMVEND,:ICODCLCOMIS
           ,:ICODFILIALCM,:vlrdescorc, :VLRFRETEORC
           ,:CODEMPTN, :CODFILIALTN, :CODTRAN
           , :TIPOFRETE, :adicfrete;

  INSERT INTO VDVENDA (
    CODEMP,CODFILIAL,CODVENDA,TIPOVENDA,CODEMPVD,CODFILIALVD,CODVEND,CODEMPCL,CODFILIALCL,CODCLI,
    CODEMPPG,CODFILIALPG,CODPLANOPAG,CODEMPSE,CODFILIALSE,SERIE,CODEMPTM,CODFILIALTM,CODTIPOMOV,
    DTSAIDAVENDA,DTEMITVENDA,STATUSVENDA,PERCCOMISVENDA,CODCLCOMIS,CODFILIALCM,CODEMPCM)
    VALUES (
    :ICODEMP,:IFILIALVD,:ICODVENDA,:STIPOVENDA,:ICODEMP,:ICODFILIALVD,:ICODVEND,:ICODEMP,:ICODFILIALCL,:ICODCLI,
    :ICODEMP,:ICODFILIALPG,:ICODPLANOPAG,:ICODEMP,:IFILIALSE,:SSERIE,:ICODEMP,:IFILIALTM,:ICODTIPOMOV,
    :DTSAIDAVENDA,CAST('today' AS DATE),:SSTATUSVENDA,:DPERCCOMVEND,:ICODCLCOMIS,:ICODFILIALCM,:ICODEMP
    );

  if ( (:codtran is not null) or (:vlrfreteorc>0) )  then
  begin
     insert into vdfretevd (codemp, codfilial, tipovenda, codvenda, codemptn
        , codfilialtn, codtran, tipofretevd, vlrfretevd, adicfretevd
        , placafretevd, vlrsegfretevd, pesobrutvd, pesoliqvd
        , espfretevd, marcafretevd, qtdfretevd, uffretevd
         )
        values (:icodemp, :icodfilialvd, :stipovenda, :icodvenda, :codemptn
        , :codfilialtn, :codtran, :tipofrete,  :vlrfreteorc, :adicfrete
        , '***-***', 0, 0, 0
        , 'Volume', '*', 0, '**' );

  end

  IRET = ICODVENDA;

  SUSPEND;
END
^

/* Alter (VDATUDESCVENDAORCSP) */
ALTER PROCEDURE VDATUDESCVENDAORCSP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
TIPOVENDA CHAR,
CODVENDA INTEGER)
 AS
declare variable vlrdescorc numeric(15,5);
declare variable vlrtotdesc numeric(15,5) = 0;
declare variable codorc integer;
declare variable conta1 numeric(15,5);
declare variable statusorc char(2);
declare variable conta2 numeric(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
begin
    -- verifica a quantidade total do orçamneto vinculado a venda
    select first 1 oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc, sum(iv.qtditvenda)
      from vdorcamento oc, vdvendaorc vo, vditorcamento itoc, vditvenda iv
        where vo.codemp=:codempvd and vo.codfilial=:codfilialvd and
          vo.tipovenda=:tipovenda and vo.codvenda=:codvenda and
          oc.codemp=vo.codempor and oc.codfilial=vo.codfilialor and
          oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc and
          itoc.codemp=oc.codemp and itoc.codfilial=oc.codfilial and
          itoc.tipoorc=oc.tipoorc and itoc.codorc=oc.codorc and
          itoc.coditorc=vo.coditorc and
          iv.codemp=vo.codemp and iv.codfilial=vo.codfilial and
          iv.tipovenda=vo.tipovenda and iv.codvenda=vo.codvenda and
          iv.coditvenda=vo.coditvenda
        group by oc.codemp, oc.codfilial, oc.tipoorc, oc.codorc, oc.statusorc
        into :codempoc, :codfilialoc, :tipoorc, :codorc, :statusorc, :conta1;

    if (:statusorc not in ('FP') ) then
    begin
        -- verifica a quantidade total do orçamento
        select sum(qtditorc) from vditorcamento it
          where codemp=:codempoc and codfilial=:codfilialoc and
          tipoorc=:tipoorc and codorc=:codorc
          into :conta2;
        if (conta1=conta2) then
        begin
            -- Buscando desconto nos orçamentos dessa venda
            for select vo.codorc, oc.vlrdescorc from vdvendaorc vo, vdorcamento oc
            where
              vo.codemp=:CODEMPVD and vo.codfilial=:CODFILIALVD and vo.tipovenda=:TIPOVENDA and vo.codvenda=:CODVENDA and
              oc.codemp=vo.codempor and oc.codfilial=vo.codfilial and oc.tipoorc=vo.tipoorc and oc.codorc=vo.codorc
            group by 1,2
            into :codorc,:vlrdescorc
            do
            begin
                VLRTOTDESC = :VLRTOTDESC + :VLRDESCORC;
            end
            -- Atualizando desconto na venda
            if(:VLRTOTDESC is not null and :VLRTOTDESC>0) then
            begin
               --update vdvenda set vlrdescvenda = :VLRTOTDESC
               --where codemp=:CODEMPVD and codfilial=:CODFILIALVD and tipovenda=:TIPOVENDA and codvenda=:CODVENDA;
            end
        end
    end
end
^

/* Alter (VDBUSCACUSTOVENDASP) */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR,
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR,
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR,
ADICFRETE CHAR,
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
declare variable aliqicms numeric(9,2);
declare variable aliqipi numeric(9,2);
declare variable aliqpis numeric(9,2);
declare variable aliqir numeric(9,2);
declare variable aliqcofins numeric(9,2);
declare variable aliqcsocial numeric(9,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codfilialpf smallint;
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable vlrliq numeric(15,5);
declare variable redbase numeric(9,2);
declare variable base numeric(15,5);
declare variable ufcli char(2);
declare variable codtrattrib char(2);
declare variable comisprod numeric(6,2);
declare variable perccomvend numeric(6,2);
declare variable codnat char(4);
declare variable codregra char(4);
begin

    --Verifica se deve buscar custos para venda .
    if(:CODVENDA is not null) then
    begin

        select
            coalesce(vd.vlrprodvenda,0), coalesce(vd.vlrdescvenda,0), coalesce(vd.vlricmsvenda,0),
            coalesce(vd.vlroutrasvenda,0), coalesce(vd.vlrcomisvenda,0), coalesce(vd.vlradicvenda,0),
            coalesce(vd.vlripivenda,0), coalesce(vd.vlrpisvenda,0), coalesce(vd.vlrcofinsvenda,0),
            coalesce(vd.vlrirvenda,0), coalesce(vd.vlrcsocialvenda,0),
            coalesce(fr.vlrfretevd,0), fr.tipofretevd, fr.adicfretevd,
            
            sum(icv.vlrcustopeps * iv.qtditvenda),
            sum(icv.vlrcustompm * iv.qtditvenda),
            sum(icv.vlrprecoultcp * iv.qtditvenda)
            
            from
            vdvenda vd left outer join vdfretevd fr on
            fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda,
            
            vditvenda iv left outer join vditcustovenda icv on
            icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda
            and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda
            
            where vd.codemp=:CODEMPVD and vd.codfilial=:CODFILIALVD and vd.codvenda=:CODVENDA and vd.tipovenda=:TIPOVENDA and
            iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda
            
            group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14

            into vlrprod,vlrdesc,vlricms,vlroutras,vlrcomis,vlradic,vlripi,vlrpis,vlrcofins,vlrir,vlrcsocial,
                 vlrfrete,tipofrete,adicfrete,vlrcustopeps,vlrcustompm,vlrprecoultcp;

            suspend;

    end
    else
    begin
        --Buscando informações sobre o produto do item de orçamento
        select io.codemppd,io.codfilialpd,io.codprod,pd.comisprod,
        coalesce(io.vlrproditorc,0),coalesce(io.vlrdescitorc,0),coalesce(io.vlrliqitorc,0),
        ico.vlrcustopeps * io.qtditorc, ico.vlrcustompm * io.qtditorc, ico.vlrprecoultcp * io.qtditorc,
        cf.codregra
        from lfclfiscal cf, eqproduto pd, vditorcamento io left outer join vditcustoorc ico on
        ico.codemp=io.codemp and ico.codfilial=io.codfilial and ico.codorc = io.codorc and
        ico.tipoorc=io.tipoorc and ico.coditorc=io.coditorc
        where io.codemp=:CODEMPOC and io.codfilial=:CODFILIALOC and io.codorc=:CODORC and io.tipoorc=:TIPOORC and io.coditorc=:CODITORC and
        pd.codemp=io.codemppd and pd.codfilial=io.codfilial and pd.codprod=io.codprod and
        cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc
        into :CODEMPPD,:CODFILIALPD,:CODPROD,:VLRPROD,:VLRDESC,:VLRLIQ,:COMISPROD,:VLRCUSTOPEPS,:VLRCUSTOMPM,:VLRPRECOULTCP,:CODREGRA;

        -- Buscanco informações do orçamento,cliente,vendedor
        select oc.codempcl,oc.codfilialcl,oc.codcli,coalesce(cl.siglauf,cl.ufcli),vd.perccomvend,
        oc.tipofrete,oc.adicfrete
        from vdorcamento oc, vdcliente cl, vdvendedor vd
        where oc.codemp=:CODEMPOC and oc.codfilial=:CODFILIALOC and oc.tipoorc=:TIPOORC and oc.codorc=:CODORC and
        cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli and
        vd.codemp=oc.codempvd and vd.codfilial=oc.codfilialvd and vd.codvend=oc.codvend
        into :CODEMPCL,:CODFILIALCL,:CODCLI,:UFCLI,:PERCCOMVEND,:TIPOFRETE,:ADICFRETE;

        --Buscando o tipo de movimento a ser utilizado na venda futura
        select p1.codempt2,p1.codfilialt2,p1.codtipomov2 from sgprefere1 p1
        where p1.codemp=:CODEMPOC and p1.codfilial=:CODFILIALPF
        into :CODEMPTM,:CODFILIALTM,:CODTIPOMOV;

        -- Buscando natureza de operação da venda futura
        select first 1 nt.codnat from lfnatoper nt, lfitregrafiscal irf
        where nt.codemp=irf.codemp and nt.codfilial=irf.codfilial and nt.codnat=irf.codnat
        and (irf.codtipomov=:CODTIPOMOV or irf.codtipomov is null)
        and (irf.codemp=:CODEMPTM or irf.codemp is null)
        and (irf.codfilial=:CODFILIALTM or irf.codfilial is null)
        and irf.codregra=:CODREGRA and irf.noufitrf='N' and irf.cvitrf='V'
        into :CODNAT;

         -- Buscando informações fiscais
        select codtrattrib,coalesce(aliqfisc,0),coalesce(aliqipifisc,0),coalesce(aliqpis,0),coalesce(aliqcofins,0),coalesce(aliqcsocial,0),
        coalesce(aliqir,0),coalesce(redfisc,0)
        from lfbuscafiscalsp(:CODEMPPD,:CODFILIALPD,:CODPROD,:CODEMPCL,:CODFILIALCL,:CODCLI,:CODEMPTM,:CODFILIALTM,
        :CODTIPOMOV,'VD',:codnat,null,null,null,null)
        into :CODTRATTRIB,:ALIQICMS,:ALIQIPI,:ALIQPIS,:ALIQCOFINS,:ALIQCSOCIAL,:ALIQIR,:REDBASE;

        -- Caso o ICMS não seja definido na classifificação, buscar da tabela de aliquotas.
        if(:ALIQICMS = 0 and :CODTRATTRIB in('00','51','20','70','10') ) then
        begin
            select coalesce(PERCICMS,0) from lfbuscaicmssp (:CODNAT,:UFCLI,:CODEMPOC,:CODFILIALPF)
            into :ALIQICMS;
        end

        -- Buscando custo do ítem

        if(:REDBASE >0) then
        begin
            BASE = :VLRLIQ - ((:VLRLIQ * :REDBASE) /100 );
        end

        BASE = :VLRLIQ;

        vlricms = :BASE * :ALIQICMS / 100;

--      vlroutras =
        vlrcomis = :VLRLIQ * ((:COMISPROD * :PERCCOMVEND) / 10000 );

--      vlradic =
        vlripi = :VLRLIQ * :ALIQIPI / 100;
        vlrpis = :VLRLIQ * :ALIQCOFINS / 100;
        vlrcofins = :VLRLIQ * :ALIQCOFINS / 100;
        vlrir = :VLRLIQ * :ALIQIR /100;
        vlrcsocial = :VLRLIQ * :ALIQCSOCIAL / 100;
--      vlrfrete =

    end

end
^

/* empty dependent procedure body */
/* Clear: ATBUSCAPRECOSP for: VDBUSCAPRECOSP */
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
 BEGIN SUSPEND; END
^

/* empty dependent procedure body */
/* Clear: VDADICITORCRECMERCSP for: VDBUSCAPRECOSP */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR,
SERVICOS CHAR,
NOVOS CHAR)
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
 RETURNS(PRECO NUMERIC(14,5),
CODCLASCLIP INTEGER,
CODPLANOPAGP INTEGER,
CODPRECOPRODP INTEGER,
CODTABP INTEGER)
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
declare variable precobase decimal(15,5);

begin
    -- Buscando código da filial de preferencias
    select icodfilial from sgretfilial(:icodemp,'SGFILIAL') into :codfilialpf;

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


    -- Buscando informações do produto
    select coalesce(pd.desccli,'N'), coalesce(precobaseprod,0) from eqproduto pd
        where pd.codprod=:icodprod and pd.codemp=:icodemp and pd.codfilial=:icodfilial
        into :desccli, :precobase;

    -- Buscando informações do cliente
        
    select codclascli, codempcc, codfilialcc, coalesce(percdesccli,0) percdesccli
    from vdcliente
    where codcli=:icodcli and codemp=:icodempcl and codfilial=:icodfilialcl
    into :icodclascli, :icodempclascli, icodfilialclascli, :percdesccli;

     -- Buscando preço da tabela de preços utilizando todos os filtros exceto tabela de preços
    for select pp.codclascli, pp.codplanopag, pp.codtab, pp.codprecoprod, pp.precoprod
    from vdprecoprod pp
    where pp.codemp=:icodemp and pp.codfilial=:icodfilial and pp.codprod=:icodprod
    and pp.ativoprecoprod='S'
    and ( ( pp.codplanopag is null ) or (pp.codemppg=:icodemppg and pp.codfilialpg=:icodfilialpg and pp.codplanopag=:icodplanopag ) )
    and ( ( pp.codclascli is null) or (pp.codempcc=:icodempclascli and pp.codfilialcc=:icodfilialclascli and pp.codclascli=:icodclascli ) )
    order by pp.codclascli, pp.codplanopag, pp.codtab, pp.codprecoprod
    into :codclasclip, :codplanopagp, :codtabp, :codprecoprodp, :preco do
    begin
        --exception vdvendaex01 'Teste';

        if ( (:preco is not null) or (:preco <> 0) ) then
        begin
            --suspend;
            break;
        end
    end

    -- Buscando preço da tabela de preços específica
    if ( (:preco is null) or (:preco = 0) ) then
    begin

        for select pp.codclascli, pp.codplanopag, pp.codtab, pp.codprecoprod, pp.precoprod
        from vdprecoprod pp
        where pp.codemp=:icodemp and pp.codfilial=:icodfilial and pp.codprod=:icodprod
        and pp.ativoprecoprod='S'
        and pp.codemptb=:icodemptab and pp.codfilialtb=:icodfilialtab and pp.codtab=:icodtab
        order by pp.codclascli, pp.codplanopag, pp.codtab, pp.codprecoprod
        into :codclasclip, :codplanopagp, :codtabp, :codprecoprodp, :preco do
        begin
            --exception vdvendaex01 'Teste';

            if ( (:preco is not null) or (:preco <> 0) ) then
            begin
               --suspend;
               break;
            end
        end
    end

    --Se ainda não conseguiu pagar o preco, deve utilizar o preço base do produto aplicando o desconto especial do cliente se houver
    if ((preco is null) or (preco = 0)) then
    begin
         preco = precobase;
    end

    -- Verifica se o cliente possui desconto especial e o produto permite este desconto...
    if( percdesccli >0 and 'S' = :desccli ) then
    begin
         preco = :preco - (:preco * (:percdesccli / 100)) ;
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

/* Alter (VDESTORNACOMISSAOSP) */
ALTER PROCEDURE VDESTORNACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
ICODVEND INTEGER,
DINI DATE,
DFIM DATE,
CORDEM CHAR)
 AS
declare variable icodcomi integer;
declare variable icodemprc integer;
declare variable scodfilialrc smallint;
declare variable icodrec integer;
declare variable inparcitrec integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable nvlrcomi numeric(15,5);
declare variable ddatacomi date;
declare variable ddtcompcomi date;
declare variable ddtvenccomi date;
declare variable ctipocomi char(1);
declare variable cstatuscomi char(2);
declare variable datual date;
declare variable cstatusitrec char(2);
declare variable ddtvencitrec date;
begin
  /* Procedure Text */
  DATUAL = CAST( 'now' AS DATE);
  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTCOMPCOMI, C.DTVENCCOMI,
      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
    WHERE C.CODEMP=:ICODEMP AND C.CODFILIAL=:SCODFILIAL AND
       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
       R.CODREC=C.CODREC AND R.CODEMPVD=:ICODEMPVD AND
       R.CODFILIALVD=:SCODFILIALVD AND R.CODVEND=:ICODVEND AND
       ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       ( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
       C.STATUSCOMI IN ('C2','CP') AND
       IR.STATUSITREC NOT IN ('RP') AND
       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
    INTO :ICODCOMI, :ICODEMPRC, :SCODFILIALRC, :ICODREC, :INPARCITREC, :NVLRVENDACOMI,
      :NVLRCOMI, :DDATACOMI, :DDTCOMPCOMI, :DDTVENCCOMI, :CTIPOCOMI, :CSTATUSCOMI, :CSTATUSITREC,
      :DDTVENCITREC
  DO
  BEGIN
     IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
     BEGIN
        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
          WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL AND
            CODCOMI=:ICODCOMI;
     END
     ELSE IF ( (DATUAL>DDTVENCITREC) AND (CSTATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
     BEGIN
        NVLRCOMI = NVLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
        EXECUTE PROCEDURE vdadiccomissaosp(:ICODEMPRC,:SCODFILIALRC,:ICODREC,
          :INPARCITREC, :NVLRVENDACOMI, :NVLRCOMI, :DDATACOMI , :DDTCOMPCOMI, :DDTVENCITREC,
          :CTIPOCOMI, :icodempvd, :scodfilialvd, : icodvend );
     END
  END
  suspend;
end
^

/* Alter (VDEVOLUVENDAS) */
ALTER PROCEDURE VDEVOLUVENDAS(ICODEMP SMALLINT,
ICODFILIAL SMALLINT,
DATAINI DATE,
DATAFIM DATE,
ICODTIPOCLI SMALLINT,
ICODCLI SMALLINT,
FILTRAVENDAS CHAR,
FATURADO CHAR,
FINANCEIRO CHAR,
EMITIDO CHAR)
 RETURNS(VALOR NUMERIC(15,5),
MES SMALLINT,
ANO SMALLINT)
 AS
declare variable valortmp numeric(15,5);
declare variable datatmp date;
declare variable mestmp smallint;
declare variable anotmp smallint;
declare variable mesant smallint;
declare variable anoant smallint;
declare variable valorant numeric(15,5);
declare variable enviou char(1);
begin
  /* Retorna os valores de vendas por período */
  VALOR = 0;
  VALORANT = 0;
  ENVIOU = 'N';

  FOR select sum(v.vlrliqvenda),v.dtemitvenda from vdvenda v, vdcliente c, eqtipomov tm
             where c.codcli=v.codcli and c.codemp=v.codempcl and c.codfilial=v.codfilialcl
             and v.dtemitvenda between :DATAINI and :DATAFIM and v.codemp = :ICODEMP
             and v.codfilial = :ICODFILIAL
             and tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov
             and ( (c.codtipocli=:ICODTIPOCLI) or (:ICODTIPOCLI is null) )
             and ( (v.codcli=:ICODCLI) or (:ICODCLI is null) )
             and ( (tm.tipomov in(select tm2.tipomov from eqtipomov tm2 where tm2.codemp=v.codemptm and tm2.codfilial=v.codfilialtm and tm2.somavdtipomov='S')) or (:FILTRAVENDAS='N') )

             and (TM.FISCALTIPOMOV=:faturado or :faturado='A')
             and (TM.SOMAVDTIPOMOV=:financeiro or :financeiro='A')
             and (( (V.STATUSVENDA IN ('V2','V3','P3') and :emitido='S') or :emitido='A' )
             or ( (V.STATUSVENDA NOT IN ('V2','V3','P3') and :emitido='N') or :emitido='A' ))

             group by v.dtemitvenda order by v.dtemitvenda
      into VALORTMP,DATATMP do
      BEGIN
        MESTMP = extract(MONTH from DATATMP);
        ANOTMP = extract(YEAR from DATATMP);
        VALOR=VALOR+VALORANT;
        MES=MESANT;
        ANO=ANOANT;
        if ((not MESANT = MESTMP or not ANOANT = ANOTMP) AND not MESANT is null) then
        begin
          suspend;
          ENVIOU = 'S';
          VALOR = 0;
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        end
        ELSE
        BEGIN
          MESANT=MESTMP;
          ANOANT=ANOTMP;
          VALORANT=VALORTMP;
        END
      END

     VALOR=VALOR+VALORANT;
     MES=MESANT;
     ANO=ANOANT;
     suspend;
end
^

/* empty dependent procedure body */
/* Clear: FNITRECEBERSP01 for: VDGERACOMISSAOSP */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR)
 AS
 BEGIN EXIT; END
^

/* Alter (VDGERACOMISSAOSP) */
ALTER PROCEDURE VDGERACOMISSAOSP(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
INPARCITREC INTEGER,
NVLRCOMIITREC NUMERIC(15,5),
DDTVENCITREC DATE)
 AS
declare variable icodempva integer;
declare variable scodfilialva smallint;
declare variable ctipovenda char(3);
declare variable icodvenda integer;
declare variable icodempcm integer;
declare variable scodfilialcm smallint;
declare variable icodclcomis integer;
declare variable nvlrvendacomi numeric(15,5);
declare variable ddatacomi date;
declare variable ddtcompcomi date;
declare variable npercfatclcomis numeric(9,2);
declare variable npercpgtoclcomis numeric(9,2);
declare variable ctipocomi char(1);
declare variable nvlrcomi numeric(15,5);
declare variable i integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable cmulticomis char(1);
declare variable icodempvd integer; /* Código da empresa do comissionado principal */
declare variable scodfilialvd smallint; /* Código da filial do comissionado principal */
declare variable icodvend integer; /* Código do comissionado principal */
declare variable nperccomisvendadic numeric(15,2); /* Percentual de comissão para cada comissionado adicional */
declare variable nvlrcomiadic numeric(15,5); /* Valor total da comissão para os comissionados adicionais. */
declare variable icodempvdadic integer; /* Código da empresa do comissionado adicional */
declare variable scodfilialvdadic smallint; /* Código da filial do comissionado adicional */
declare variable icodvendadic integer; /* Código do comissionado adicional */
declare variable nvlrcomiparc numeric(15,5); /* Valor da comissão por vendedor parcial. */
begin
    /* Gera as comissões a pagar na tabela VDCOMISSAO */


    nvlrcomiadic = 0;

    select r.codempva, r.codfilialva, r.tipovenda, r.codvenda,
        v.codempcm, v.codfilialcm, v.codclcomis, ( v.vlrprodvenda - v.vlrdescvenda ),
        v.dtemitvenda, v.dtcompvenda, cm.percfatclcomis, cm.percpgtoclcomis,
        v.codemptm, v.codfilialtm, v.codtipomov,
        v.codempvd, v.codfilialvd, v.codvend
        from fnreceber r, vdvenda v, vdclcomis cm
        where r.codemp=:icodemp and r.codfilial=:scodfilial and r.codrec=:icodrec
            and v.codemp=r.codempva and v.codfilial=r.codfilialva and v.tipovenda=r.tipovenda
            and v.codvenda=r.codvenda and cm.codemp=v.codempcm and cm.codfilial=v.codfilialcm
            and cm.codclcomis=v.codclcomis
    into :icodempva, :scodfilialva, :ctipovenda, :icodvenda,
         :icodempcm, :scodfilialcm, :icodclcomis, :nvlrvendacomi,
         :ddatacomi, :ddtcompcomi, :npercfatclcomis, :npercpgtoclcomis,
         :icodemptm, :scodfilialtm, :icodtipomov,
         :icodempvd, :scodfilialvd, :icodvend ;

    /*Verifica se deve utilizar mecanismo de multiplos comissionados*/

    select cmulticomis from sgretmulticomissp(:icodemp, :icodemptm, :scodfilialtm, :icodtipomov)
        into cmulticomis;

    if(cmulticomis = 'S') then
    begin

        /*Implementação do mecanismo de multiplos comissionados*/
        
        for select vc.codempvd, vc.codfilialvd, vc.codvend, vc.percvc
            from vdvendacomis vc
            where vc.codemp=:icodempva and vc.codfilial=:scodfilialva and vc.codvenda=:icodvenda
                and vc.tipovenda=:ctipovenda and vc.codvend is not null
        into :icodempvdadic, :scodfilialvdadic, :icodvendadic, :nperccomisvendadic do
        begin

            /* Calcula o valor da comissão proporcional para cada comissionado adicional*/

            nvlrcomi = cast( ( nvlrcomiitrec * nperccomisvendadic / 100 ) as numeric(15,5));
            I = 1;
            while (:I<=2) do
            begin
                if (I=1) then
                    begin
                        ctipocomi='F';
                        nvlrcomiparc = cast( ( nvlrcomi * npercfatclcomis / 100 ) as NUMERIC(15, 5));
                    end
                else
                    begin
                        ctipocomi='R';
                        nvlrcomiparc = cast( (nvlrcomi * npercpgtoclcomis / 100 ) as NUMERIC(15, 5));
                    end
                execute procedure vdadiccomissaosp(:iCodEmp, :sCodFilial, :iCodRec, :iNParcItRec,
                    :nVlrVendaComi, :nvlrcomiparc, :dDataComi, :dDtCompComi, :dDtVencItRec, cTipoComi,:icodempvdadic, :scodfilialvdadic, :icodvendadic );
                I=I+1;
                /*Acumula as comissões adicionais para posteriormente descontar do valor principal*/
                nvlrcomiadic = nvlrcomiadic + nvlrcomiparc;

           /*     exception vdcomissaoex02 'rodou procedure para o vendedor:' || cast(:icodempvdadic as char(2)) || '-' || cast(:scodfilialvdadic as char(2)) || '-' || cast(:icodvendadic as char(2)) || '-' || ' - nvlrcomiadic:' || cast(nvlrcomiadic as char(20));*/

            end

        end

    end

    /*Comissionamento do vendedor principal*/

    I = 1;
    while (:I<=2) do
        begin
            if (I=1) then
                begin
                    ctipocomi='F';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercFatClComis / 100 ) as numeric(15, 5) );
                end
            else
                begin
                    ctipocomi='R';
                    nvlrcomi = cast( ( (nvlrcomiitrec - nvlrcomiadic ) * nPercPgtoClComis / 100 ) as numeric(15, 5));
                end
/*
                exception vdcomissaoex01 ''
                || cast(:icodempvd as char(2)) || '-' || cast(:scodfilialvd as char(2)) || '-' || cast(:icodvend as char(2))
                || 'nvlrcomi:' || cast(:nvlrcomi as char(20))
                || 'nvlrvendacomi:' || cast(:nvlrvendacomi as char(20));
  */
            execute procedure vdadiccomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec,
                :nvlrvendacomi, :nvlrcomi, :ddatacomi, :ddtcompcomi, :ddtvencitrec, ctipocomi, :icodempvd, :scodfilialvd, :icodvend );


            I=I+1;
        end

        suspend;
end
^

/* Alter (VDITVENDASERIESP) */
ALTER PROCEDURE VDITVENDASERIESP(TRANSACAO CHAR,
CODEMP INTEGER,
CODFILIAL SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR,
CODITVENDA SMALLINT,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
NUMSERIETMP VARCHAR(30),
QTDITVENDA INTEGER)
 AS
declare variable seqitserie integer;
declare variable serieprod char(1);
begin
        
    -- Buscando informações do produto
    select pd.serieprod from eqproduto pd
    where pd.codemp = :codemppd and pd.codfilial = :codfilialpd and pd.codprod = :codprod
    into :serieprod;
    
    -- Se o produto usa série...
    if(serieprod = 'S') then
    begin
    
        -- Buscando ultimo item de série inserido...
        select coalesce(max(seqitserie),0) from vditvendaserie
        where codemp=:codemp and codfilial=:codfilial and codvenda=:codvenda and tipovenda=:tipovenda and coditvenda=:coditvenda
        into :seqitserie;
    
        -- Se for uma transaçáo de Inserção...
        if( 'I' = :transacao) then
        begin
            -- Inserindo itens, enquanto a sequencia for menor ou igual à quantidade de itens
            while (seqitserie < qtditvenda) do
            begin
                seqitserie = seqitserie + 1;

                -- Se a quantidade não for unitária
                if(qtditvenda > 1) then
                begin
                    insert into vditvendaserie
                    (codemp, codfilial, codvenda,  tipovenda, coditvenda, seqitserie )
                    values
                    (:codemp, :codfilial, :codvenda, :tipovenda, :coditvenda, :seqitserie);
                end
                -- Se for apenas um item, deve inserir o numero se série digitado na tela...
                else if(qtditvenda = 1 and :numserietmp is not null) then
                begin
                    insert into vditvendaserie
                    (codemp, codfilial, codvenda, tipovenda,  coditvenda, seqitserie, codemppd, codfilialpd, codprod, numserie )
                    values
                    (:codemp, :codfilial, :codvenda, :tipovenda, :coditvenda, :seqitserie, :codemppd, :codfilialpd, :codprod, :numserietmp );
                end
            end
        end
        -- Se for uma transação de Deleção, deve excluir todas os ítens gerados.
        else if('D' = :transacao) then
        begin
            delete from vditvendaserie itcs
            where
            itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.codvenda = :codvenda and itcs.coditvenda = :coditvenda;
        end
        -- Se for uma transação de Update, deve excluir os excessos ou incluir os faltantes...
        else if('U' = :transacao) then
        begin

            if(seqitserie > qtditvenda) then
            begin

                delete from vditvendaserie itcs
                where
                itcs.codemp = :codemp and itcs.codfilial = :codfilial and itcs.codvenda = :codvenda and itcs.coditvenda = :coditvenda
                and itcs.seqitserie > :qtditvenda;
            
            end
            else
            begin
            
                execute procedure vditvendaseriesp( 'I', :codemp, :codfilial, :codvenda, :tipovenda, :coditvenda, :codemppd, :codfilialpd, :codprod, :numserietmp, :qtditvenda);

            end

        end

    end


end
^

/* Alter (VDUPVENDAORCSP) */
ALTER PROCEDURE VDUPVENDAORCSP(ICODEMP INTEGER,
ICODFILIAL INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIALVD INTEGER,
ICODVENDA INTEGER,
ICODITVENDA INTEGER,
STIPOVENDA CHAR(10))
 AS
declare variable iconta1 decimal(15,5);
declare variable vlrdescvenda decimal(15,5);
declare variable iconta2 decimal(15,5);
declare variable iconta3 decimal(15,5);
begin
  /* Procedure Text */
  

 -- EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'inicio :'|| cast('now' as time);

  INSERT INTO VDVENDAORC (CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
                          CODEMPOR,CODFILIALOR,TIPOORC,CODORC,CODITORC) VALUES
                         (:ICODEMP,:ICODFILIALVD,:STIPOVENDA,:ICODVENDA,:ICODITVENDA,
                          :ICODEMP,:ICODFILIAL,'O',:ICODORC,:ICODITORC);

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim insert vdvendaorc:'|| cast('now' as time);

  UPDATE VDITORCAMENTO SET EMITITORC='S'
       WHERE CODITORC=:ICODITORC AND CODORC=:ICODORC AND TIPOORC='O'
       AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
       AND EMITITORC<>'S';


--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vditorcamento '|| cast('now' as time);
    
  SELECT SUM(QTDITORC), SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
      INTO :ICONTA1, :ICONTA2;
--  SELECT SUM(QTDFATITORC) FROM VDITORCAMENTO WHERE CODORC=:ICODORC AND TIPOORC='O'
--    AND CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL
--      INTO ICONTA2;

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim select sum(qtditorc) '|| cast('now' as time);

  IF ( ICONTA1 = ICONTA2 ) THEN
  BEGIN
    UPDATE VDORCAMENTO SET STATUSORC='OV'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC
    AND STATUSORC<>'OV';
    SELECT SUM(IV.QTDITVENDA) FROM VDITVENDA IV, VDVENDAORC VO
       WHERE VO.CODEMP=:ICODEMP AND VO.CODFILIAL=:ICODFILIALVD AND
       VO.TIPOVENDA=:STIPOVENDA AND VO.CODVENDA=:ICODVENDA AND
       IV.CODEMP=VO.CODEMP AND IV.CODFILIAL=VO.CODFILIAL AND
       IV.TIPOVENDA=VO.TIPOVENDA AND IV.CODVENDA=VO.CODVENDA AND
       IV.CODITVENDA=VO.CODITVENDA
          INTO ICONTA3;
  --  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento OV = ICONTA1=ICONTA2 '|| cast('now' as time);

    IF ( ICONTA1<>ICONTA3 ) THEN -- Verifica se o orçamento foi dividido em várias vendas
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA
           AND VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1<>ICONTA3 '|| cast('now' as time);

    END
  END
  ELSE IF (ICONTA1 > ICONTA2) THEN
  BEGIN               
    UPDATE VDORCAMENTO SET STATUSORC='FP'
    WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIAL AND CODORC=:ICODORC AND
     STATUSORC<>'FP';
    SELECT SUM(I.VLRDESCITVENDA) FROM VDITVENDA I
       WHERE I.CODEMP=:ICODEMP AND I.CODFILIAL=:ICODFILIALVD AND I.TIPOVENDA=:STIPOVENDA AND I.CODVENDA=:ICODVENDA
       INTO :VLRDESCVENDA;
--    EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdorcamento STATUSORC=FP ICONTA1 > ICONTA2 '|| cast('now' as time);
    IF (:VLRDESCVENDA<>0) THEN
    BEGIN
       UPDATE VDVENDA SET VLRDESCVENDA=0
         WHERE CODEMP=:ICODEMP AND CODFILIAL=:ICODFILIALVD AND TIPOVENDA=:STIPOVENDA AND CODVENDA=:ICODVENDA AND
         VLRDESCVENDA<>0;
--        EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim update vdvenda VRLDESCVENDA=0 / ICONTA1>ICONTA2 '|| cast('now' as time);
    END 
  END

--  EXECUTE PROCEDURE sgdebugsp 'vdupvendaorcsp, orc.: '||:icodorc, 'fim VDUPVENDAORCSP'|| cast('now' as time);

  --  exception vdvendaex06 'teste de velociadade';

  suspend;
end
^

/* Restore proc. body: ATBUSCAPRECOSP */
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

/* Restore proc. body: CPADICCOMPRAPEDSP */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable codempse integer;
declare variable codfilialse smallint;
declare variable serie char(4);
declare variable statuscompra char(2);
begin

    --Buscando a série da compra
    select tm.codempse, tm.codfilialse, tm.serie
    from eqtipomov tm
    where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
    into :codempse, :codfilialse, :serie;

    --Definição do status da compra
    statuscompra = 'P1';

    --Buscando doccompra
    select doc from lfnovodocsp(:serie, :codempse , :codfilialse) into doccompra;

    insert into cpcompra (
    codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor,
    codempse, codfilialse, serie, codemptm, codfilialtm, codtipomov, doccompra, dtentcompra, dtemitcompra, statuscompra, calctrib )
    values (
    :codemp, :codfilial, :codcompra, :codemppg, :codfilialpg, :codplanopag, :codempfr, :codfilialfr, :codfor,
    :codempse, :codfilialse, :serie, :codemptm, :codfilialtm, :codtipomov, :doccompra,
    cast('today' as date), cast('today' as date), :statuscompra, 'S' );

    iret = :codcompra;

    suspend;

end
^

/* Restore proc. body: CPADICITCOMPRAPEDSP */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR,
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
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
declare variable refprod varchar(20);
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

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
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

/* Restore proc. body: CPADICITCOMPRARECMERCSP */
ALTER PROCEDURE CPADICITCOMPRARECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPCP INTEGER,
CODFILIALCP SMALLINT,
CODCOMPRA INTEGER,
QTDITCOMPRA NUMERIC(15,5))
 AS
declare variable usaprecocot char(1);
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
declare variable aprovpreco char(1);
declare variable codemppp integer;
declare variable codfilialpp smallint;
declare variable codplanopag integer;
declare variable vlrproditcompra numeric(15,5);
declare variable qtditrecmerc numeric(15,5);
declare variable codempns integer;
declare variable codfilialns smallint;
declare variable numserietmp varchar(30);
declare variable percprecocoletacp numeric(15,5);
declare variable permititemrepcp char(1);
declare variable trocaqtd char(1);
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Verificando se é para buscar as quantidades
    if ( (qtditcompra is null) or (qtditcompra=0)) then
       trocaqtd = 'S';
    else 
       trocaqtd = 'N';
    
    
    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;
    
    -- Buscando preferências GMS
    select coalesce(p8.percprecocoletacp,100) percprecocoletacp
    , coalesce(permititemrepcp, 'N') 
    from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :percprecocoletacp, :permititemrepcp;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc, ir.qtditrecmerc,
        ir.codempns, ir.codfilialns, ir.numserie
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc, :qtditrecmerc,
        :codempns, :codfilialns, :numserietmp
        do
        begin
            if (:trocaqtd='S') then
                 qtditcompra = 0;  
            if(:permititemrepcp='S' or :codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov,null)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Se deve buscar preço de cotação.
                if( 'S' = :usaprecocot) then
                begin
                    -- Deve implementar ipi, vlrliq etc... futuramente...
                    select first 1 ct.precocot
                    from cpcotacao ct, cpsolicitacao sl, cpitsolicitacao iso
                    left outer join eqrecmerc rm on
                    rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket
                    where
                    iso.codemp = sl.codemp and iso.codfilial=sl.codfilial and iso.codsol=sl.codsol
                    and ct.codemp=iso.codemp and ct.codfilial=iso.codfilial and ct.codsol=iso.codsol and ct.coditsol=iso.coditsol
                    and iso.codemppd=:codemppd and iso.codfilialpd=:codfilialpd and iso.codprod=:codprod
                    and ct.codempfr=:codempfr and ct.codfilialfr=:codfilialfr and ct.codfor=:codfor
                    and (ct.dtvalidcot>=cast('today' as date) and (ct.dtcot<=cast('today' as date)))
                    and ct.sititsol not in ('EF','CA')

                    and ( (ct.rendacot = rm.rendaamostragem) or ( coalesce(ct.usarendacot,'N') = 'N') )

                    and ( (ct.codemppp=:codemppp and ct.codfilialpp=:codfilialpp and ct.codplanopag=:codplanopag)
                       or (ct.codplanopag is null))

                    order by ct.dtcot desc
                    into :precoitcompra;

                    if(:precoitcompra is not null) then
                    begin
                        -- Indica que o preço é aprovado (cotado anteriormente);
                        aprovpreco = 'S';
                    end
                end

                -- Se não conseguiu obter o preço das cotações
                if(precoitcompra is null) then
                begin
                    -- Buscando preço de compra da tabela de custos de produtos
                    select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                    cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                    into :precoitcompra;

                end

                -- verifica se quantidade está zerada (coleta) se estiver preechida (trata-se de uma pesagem)
                if ( (qtditcompra is null) or (qtditcompra = 0) ) then 
                begin
                    qtditcompra = qtditrecmerc;
                end

                if ( ( :percprecocoletacp is not null) and (:percprecocoletacp<>100) ) then
                begin
                   precoitcompra = cast( :precoitcompra / 100 * :percprecocoletacp as decimal(15,5) ); 
                end
                 
                vlrproditcompra = :precoitcompra * qtditcompra;

                -- Inserir itens
                
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra,
                codempns, codfilialns, numserietmp)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra,
                :codempns, :codfilialns,  :numserietmp) ;

                -- Inserindo vínculo entre compra e recebimento

                insert into eqitrecmercitcp(codemp, codfilial, ticket, coditrecmerc, codempcp, codfilialcp, codcompra, coditcompra)
                values(:codemp,:codfilial,:ticket,:coditrecmerc,:codempcp,:codfilialcp,:codcompra,:coditrecmerc);

                codprodant = codprod;

            end
        end



  --  suspend;
end
^

/* Restore proc. body: CPGERAENTRADASP */
ALTER PROCEDURE CPGERAENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR,
CODVENDA INTEGER,
CITEM CHAR)
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

/* Restore proc. body: CPGERAITENTRADASP */
ALTER PROCEDURE CPGERAITENTRADASP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODFILIALVD INTEGER,
TIPOVENDA CHAR,
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
                                   :CODEMP,:CODFILIALFR,:CODFOR,:CODFILIALTM,:CODTIPOMOV,null) INTO CODNAT;

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

/* Restore proc. body: EQGERARMAOSSP */
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
declare variable refprod varchar(20);
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
    from sgretinfousu(:codemprm, user)
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
                     codempos,codfilialos,ticket,coditrecmerc,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,:coditrecmerc,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditrecmerc, os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
--    and ( (os.coditrecmerc=:coditrecmerc) or (:coditrecmerc is null) )
    and os.gerarma='S'
    into :coditrecmerc, :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
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
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            CODEMPOS,CODFILIALOS,TICKET,CODITRECMERC,CODITOS
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE',
                            :codemprm, :codfilialrm, :ticket, :coditrecmerc,:coditos
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* Restore proc. body: EQGERARMASP */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
declare variable seqof smallint;
declare variable idusu1 char(8);
declare variable seqitop integer;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codrma integer;
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialpd smallint;
declare variable codfilialtm1 smallint;
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
declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
declare variable codfase integer;
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc integer;
declare variable coditos integer;
begin

    -- Buscando informações do usuário
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codempop, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando preferencias de tipo de movimento para OP
    select codfilialt8,codtipomov8
    from sgprefere1 where codemp=:CODEMPOP and codfilial=(select icodfilial from sgretfilial(:CODEMPOP, 'SGPREFERE1'))
    into :CODFILIALTM1,:CODTIPOMOV1;

    --Buscando informações da OP.
    select op.codempos, op.codfilialos, op.ticket, op.coditrecmerc, op.coditos
    from  ppop op
    where op.codemp=:codempop and op.codfilial=:codfilialop and op.codop=:codop and op.seqop=:seqop
    into :codempos, :codfilialos, :ticket, :coditrecmerc, :coditos;

    -- Buscando preferencias para geração de RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:CODEMPOP and
    codfilial=(select icodfilial from sgretfilial(:CODEMPOP,'SGPREFERE5'))
    into :STATUSRMAGER;

    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    for select codfilialfs,codfase,seqof from ppopfase opf
        where opf.codemp=:CODEMPOP and opf.codfilial=:CODFILIALOP and
        opf.codop=:CODOP and  opf.seqop=:SEQOP and
        (select count(1) from ppitop it
        where it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP and
        it.codempfs=opf.codempfs and it.codfilialfs=opf.codfilialfs and
        it.codfase=opf.codfase and it.gerarma='S' and
        it.codop=:CODOP and it.seqop=:SEQOP) > 0
        into :codfilialfase,:codfase,:SEQOF do
    begin
        select coalesce((max(codrma)+1),1)
        from eqrma
        where codemp=:CODEMPOP and codfilial=:CODFILIALOP into :CODRMA;

        insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempof,codfilialof,codop,seqop,seqof,
                     motivorma, codempos, codfilialos, ticket, coditrecmerc)
                     values
                     (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQRMA')),:CODRMA,
                      :CODEMPOP, :CODFILIALUSU1,:IDUSU1,
                      null,null,null,
                      null,null,null,
                      :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQTIPOMOV')),
                      :CODTIPOMOV1,
                      :CODEMPOP,:CODFILIALCCUSU1,:ANOCCUSU1,:CODCCUSU1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :CODEMPOP,:CODFILIALOP,:CODOP,:SEQOP,:SEQOF,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OP:'||:CODOP||' SEQ:'||:SEQOP||' - FASE:'||:CODFASE,
                      :codempos, :codfilialos, :ticket, :coditrecmerc
        );

        for select it.seqitop,it.codfilialpd,it.codprod,it.refprod,it.qtditop-coalesce(it.qtdcopiaitop,0),it.codfilialle,it.codlote,
            (select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)),
            (SELECT CODFILIALAX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod),
            (SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod)
            from ppitop it, eqproduto pd
            where
            pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
            and it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP
            and it.codop=:CODOP and it.seqop=:SEQOP and it.codempfs=:CODEMPOP
            and it.codfilialfs=:CODFILIALFASE
            and it.codfase = :CODFASE and it.gerarma='S'
            and (('S'=(select ratauto from sgprefere5 where codemp=it.codemp and codfilial=it.codfilial))
            and (it.qtditop-coalesce(it.qtdcopiaitop, 0))<=(SELECT L.SLDLOTE FROM EQLOTE L
                                                            WHERE L.CODEMP=it.codemple AND L.CODFILIAL=it.codfilialle AND
                                                            L.CODPROD=it.codprod AND L.CODLOTE=it.codlote)
            or pd.cloteprod = 'N'
                                                            )
            into :SEQITOP, :CODFILIALPD,:CODPROD,:REFPROD,:QTD,
            :CODFILIALLE,:CODLOTE,:CUSTOMPMIT,:CODFILIALAX,:CODALMOX  DO
        begin
            select coalesce((max(coditrma)+1),1) from eqitrma
            where codemp=:CODEMPOP and codfilial=:CODFILIALOP and
            codrma=:CODRMA into :coditrma;

            if(:STATUSRMAGER='AF')then
            begin
                STATUSAPROVRMAGER = 'AT';
                QTDAPROV = :QTD;
            end

            insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            codempos, codfilialos, ticket, coditrecmerc, coditos
                            )
                            values
                            (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQITRMA')),:CODRMA,
                            :coditrma,
                            :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQPRODUTO')),
                            :CODPROD,:REFPROD,:QTD,:QTDAPROV,0,:CUSTOMPMIT,:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQLOTE')),:CODLOTE,
                            :CODEMPOP,:CODFILIALAX,:CODALMOX,
                            :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                            :codempop, :codfilialos, :ticket, :coditrecmerc, :coditos
                            );

            update ppitop it set it.gerarma='N' where it.CODEMP=:CODEMPOP AND
                it.CODFILIAL=:CODFILIALOP AND
                it.codop=:CODOP and it.seqop=:SEQOP and it.seqitop=:seqitop;

        end

    end

    suspend;

end
^

/* Restore proc. body: EQMOVPRODATEQSP */
ALTER PROCEDURE EQMOVPRODATEQSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLEOLD INTEGER,
SCODFILIALLEOLD SMALLINT,
CCODLOTEOLD VARCHAR(20),
ICODEMPLENEW INTEGER,
SCODFILIALLENEW SMALLINT,
CCODLOTENEW VARCHAR(20),
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
DECLARE VARIABLE CCLOTEPROD CHAR(1);
DECLARE VARIABLE ICODALMOX INTEGER;
DECLARE VARIABLE ICODEMPAX INTEGER;
DECLARE VARIABLE SCODFILIALAX SMALLINT;
begin
  /* Procedure de atualização do estoque */
  UPDATE EQPRODUTO SET SLDPROD = SLDPROD+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
            WHERE CODPROD=:ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD;

  SELECT CLOTEPROD, CODALMOX,CODEMPAX,CODFILIALAX FROM EQPRODUTO
     WHERE CODPROD = :ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL = :SCODFILIALPD
      INTO :CCLOTEPROD, :ICODALMOX, :ICODEMPAX, :SCODFILIALAX;

  EXECUTE PROCEDURE EQSALDOPRODATEQSP(:ICODEMPPD, :SCODFILIALPD, :ICODPROD, :SOPERADOR,
   :NQTDMOVPRODOLD, :NQTDMOVPRODNEW, :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD,
   :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);

  IF (CCLOTEPROD = 'S') THEN
  BEGIN
    IF (CCODLOTENEW IS NULL) THEN
    BEGIN
      EXCEPTION EQPRODUTOEX01 'ESTE MOVIMENTO PRECISA DE UM LOTE!';
    END
    ELSE
    BEGIN
       EXECUTE PROCEDURE EQSALDOLOTEATEQSP(:ICODEMPLEOLD, :SCODFILIALLEOLD, :ICODPROD, :CCODLOTEOLD,
          :ICODEMPLENEW, :SCODFILIALLENEW, :CCODLOTENEW, :SOPERADOR, :NQTDMOVPRODOLD, :NQTDMOVPRODNEW,
          :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD, :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);
    END
  END

  suspend;
end
^

/* Restore proc. body: EQMOVPRODDSP */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
TIPONF CHAR)
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX, :TIPONF)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

/* Restore proc. body: FNESTORNACOMISSAOSP */
ALTER PROCEDURE FNESTORNACOMISSAOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODREC INTEGER,
NPARCITREC SMALLINT)
 AS
declare variable vlrvendacomi numeric(15,5);
declare variable vlrcomi numeric(15,5);
declare variable datacomi date;
declare variable dtcompcomi date;
declare variable dtvenccomi date;
declare variable tipocomi char(1);
declare variable statuscomi char(2);
declare variable dtatual date;
declare variable dtvencitrec date;
declare variable codempvd integer;
declare variable codfilialvd smallint;
declare variable codvend integer;
begin
  /* Procedure Text */
  dtatual = cast( 'now' as date);

  select first 1 c.statuscomi, c.tipocomi, c.codempvd, c.codfilialvd, c.codvend
  , c.vlrvendacomi, c.vlrcomi, c.datacomi, c.dtcompcomi, ir.dtvencitrec , c.dtvenccomi
  from vdcomissao c, fnitreceber ir
  where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec and tipocomi='R'
  and ir.codemp=c.codemprc and ir.codfilial=c.codfilialrc and ir.codrec=c.codrec and ir.nparcitrec=c.nparcitrec
  and c.statuscomi<>'CE'
  order by c.codcomi desc
  into :statuscomi, :tipocomi, :codempvd, :codfilialvd,  :codvend
  , :vlrvendacomi, :vlrcomi, :datacomi, :dtcompcomi, :dtvencitrec, :dtvenccomi;

  if (statuscomi not in ('CP') ) then
  begin
      update vdcomissao c set c.statuscomi='C1'
      where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec
      and tipocomi='R' and statuscomi not in ('CP','CE');
  end
  else if (statuscomi in ('CP') ) then
  begin
      vlrcomi = vlrcomi * -1; /* Transforma o valor da comissão em negativo */
      /* para gerar estorno */
      execute procedure vdadiccomissaosp(:codemp,:codfilial,:codrec,
         :nparcitrec, :vlrvendacomi, :vlrcomi, :datacomi , :dtcompcomi, :dtvenccomi,
         :tipocomi, :codempvd, :codfilialvd, : codvend );

     -- execute vdadiccomissaosp
  end

  /*UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
    */
--  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
--      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTCOMPCOMI, C.DTVENCCOMI,
--      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
--    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
--    WHERE C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND
--       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
--       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
--       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
--       R.CODREC=C.CODREC
       --AND R.CODEMPVD=:CODEMPVD
       --AND
       --R.CODFILIALVD=:CODFILIALVD
       --AND R.CODVEND=:CODVEND AND
       --and ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       --( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
--       and C.STATUSCOMI IN ('C2','CP') AND
--       IR.STATUSITREC NOT IN ('RP') AND
--       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
--         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
--         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
--         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
--    INTO :CODCOMI, :CODEMP, :CODFILIAL, :CODREC, :NPARCITREC, :VLRVENDACOMI,
--      :VLRCOMI, :DATACOMI, :DTCOMPCOMI, :DTVENCCOMI, :TIPOCOMI, :STATUSCOMI, :STATUSITREC,
--      :DTVENCITREC
--  DO
--  BEGIN
--     IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
--     BEGIN
--        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--          WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
--            CODCOMI=:CODCOMI;
--     END
--     ELSE IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
--     BEGIN
--        VLRCOMI = VLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
     --   EXECUTE PROCEDURE vdadiccomissaosp(:CODEMP,:CODFILIAL,:CODREC,
       --   :NPARCITREC, :VLRVENDACOMI, :VLRCOMI, :DATACOMI , :DTCOMPCOMI, :DTVENCITREC,
         -- :TIPOCOMI, :codempvd, :codfilialvd, : codvend );
--     END
--  END
 -- suspend;
end
^

/* Restore proc. body: FNITRECEBERSP01 */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR)
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin
        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
    end
    suspend;
end
^

/* Restore proc. body: LFBUSCAFISCALSP */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR,
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR,
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR,
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR,
CALCSTCM CHAR,
ALIQICMSSTCM NUMERIC(9,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

/* Restore proc. body: SGATUALIZABDSP */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
DECLARE VARIABLE ICODEMPTMP INTEGER;
begin
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Entrou no atualizabdsp '||cast(ICODEMP AS CHAR(10)));*/

  EXECUTE PROCEDURE SGDADOSINISP(:ICODEMP);
  EXECUTE PROCEDURE SGGRANTUSERSP ;
  FOR SELECT CODEMP FROM SGEMPRESA INTO :ICODEMPTMP DO
  BEGIN
     EXECUTE PROCEDURE SGOBJETOINSTBSP(:ICODEMPTMP);
     EXECUTE PROCEDURE SGGRANTADMSP(:ICODEMPTMP);
  END
  suspend;
end
^

/* Restore proc. body: VDADICITORCRECMERCSP */
ALTER PROCEDURE VDADICITORCRECMERCSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
TICKET INTEGER,
CODEMPOC INTEGER,
CODFILIALOC SMALLINT,
CODORC INTEGER,
COMPONENTES CHAR,
SERVICOS CHAR,
NOVOS CHAR)
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod varchar(20);
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
declare variable refprodir varchar(20);
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
ALTER TRIGGER ATATENDIMENTOTGAI
AS
declare variable statuschamado char(2);
declare variable novostatuschamado char(2);
begin
    -- Verifica se o atendimento é para um chamado
    if (new.codchamado is not null) then
    begin
        -- Buscando status do chamado;
        select status from crchamado where codemp=new.codempch and codfilial=new.codfilialch and codchamado=new.codchamado
        into :statuschamado;

        -- Atualiza o status do chamado...
        -- Outros status devem ser implementados futuramente.
        if(:statuschamado is not null) then
        begin
            novostatuschamado = 'EA';

            if(new.concluichamado='S') then
            begin
                novostatuschamado = 'CO';
            end

            update crchamado set status=:novostatuschamado, ematendimento='S'
            where codemp=new.codempch and codfilial=new.codfilialch and codchamado=new.codchamado;

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
  DECLARE VARIABLE ICODMODNOTA INTEGER;
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
               :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,
               new.FLAG,new.CODEMP,new.CODFILIAL, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
               
          SELECT CODMODNOTA FROM EQTIPOMOV m WHERE m.codemp=new.codemptm and m.codfilial=new.codfilialtm and m.codtipomov=new.codtipomov INTO ICODMODNOTA;
          --execute procedure sgdebugsp 'cpcompratgbu', 'PEGOU O MODELO DE NOTA:'||:ICODMODNOTA;
          IF(:ICODMODNOTA=55) THEN
          BEGIN
              --execute procedure sgdebugsp 'cpcompratgbu', 'ENTROU O MODELO DA NOTA É IGUAL A 55:';
              execute procedure sggeracnfsp('CP', null,null,null,null,new.codemp, new.codfilial,new.codcompra);
          END
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE , new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
               new.CODEMPFR, new.CODFILIALFR, new.CODFOR, :dVLR, :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO, new.CODFILIALBO, new.CODBANCO, new.FLAG, new.CODEMP, new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag);
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

      IF ( new.codimp is null and (((new.VLRFRETECOMPRA > 0) AND ( NOT new.VLRFRETECOMPRA = old.VLRFRETECOMPRA)) or ((new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)))  ) THEN
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
ALTER TRIGGER CPCOMPRATGBD
AS
begin
  /* Trigger text */
  if ( old.BLOQCOMPRA IS NOT NULL AND old.BLOQCOMPRA='S'  ) then
      EXCEPTION CPCOMPRAEX04 'ESTA COMPRA ESTÁ BLOQUEADA!!!';
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPCOMPRATGBU
AS
  DECLARE VARIABLE dVlrPagar NUMERIC(15, 5);
  DECLARE VARIABLE sStatusPag CHAR(2);
  DECLARE VARIABLE IFILIALTIPOMOV INTEGER;
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE CSEQNFTIPOMOV CHAR(1);
BEGIN
  IF (new.EMMANUT IS NULL) THEN
     new.EMMANUT='N';
  if (new.BLOQCOMPRA IS NULL) then
     new.BLOQCOMPRA='N';
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      if ( (old.BLOQCOMPRA IS NOT NULL AND old.BLOQCOMPRA='S') or (new.BLOQCOMPRA='S') ) then
         EXCEPTION CPCOMPRAEX04 'ESTA COMPRA ESTÁ BLOQUEADA!!!';
      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      if ( (new.DTCOMPCOMPRA IS NULL) OR (new.DTEMITCOMPRA<>old.DTEMITCOMPRA) )  then
         new.DTCOMPCOMPRA = new.DTEMITCOMPRA;
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'EQTIPOMOV') INTO IFILIALTIPOMOV;
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
      SELECT T.FISCALTIPOMOV, T.SEQNFTIPOMOV FROM EQTIPOMOV T WHERE T.CODTIPOMOV=new.CODTIPOMOV
             AND T.CODEMP=new.CODEMP AND T.CODFILIAL = :IFILIALTIPOMOV INTO new.FLAG, :CSEQNFTIPOMOV;
      IF (old.IMPNOTACOMPRA IS NULL) THEN
         new.IMPNOTACOMPRA = 'N';
      IF ( (CSEQNFTIPOMOV IS NOT NULL) AND (CSEQNFTIPOMOV='S') ) THEN
      BEGIN
         IF  (new.IMPNOTACOMPRA='N') THEN
         BEGIN
            SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMPSE,new.CODFILIALSE) INTO new.DocCompra;
            new.IMPNOTACOMPRA = 'S';
         END
         ELSE IF (old.DOCCOMPRA != new.DOCCOMPRA) THEN
         BEGIN
            new.DOCCOMPRA = old.DOCCOMPRA;
         END
      END

      IF ( new.FLAG <> 'S') THEN
      BEGIN
        new.FLAG = 'N';
      END
      IF (new.VLRDESCCOMPRA IS NULL) THEN
        new.VLRDESCCOMPRA = 0;
      IF (new.VLRADICCOMPRA IS NULL) THEN
        new.VLRADICCOMPRA = 0;
      IF (new.VLRFRETECOMPRA IS NULL) THEN
        new.VLRFRETECOMPRA = 0;
      IF (old.STATUSCOMPRA = 'P1')  THEN
      BEGIN
        IF (new.STATUSCOMPRA = 'C1') THEN
        BEGIN
          new.STATUSCOMPRA = 'C2';
        END
      END
      ELSE IF ((old.STATUSCOMPRA IN ('P4','C4')) AND (new.STATUSCOMPRA IN ('P4','C4'))) THEN
      BEGIN
        EXCEPTION CPCOMPRAEX03;
      END
      ELSE IF ((old.STATUSCOMPRA IN ('P2','C2')) AND (new.STATUSCOMPRA IN ('P3','C3'))) THEN
      BEGIN
        SELECT STATUSPAG,VLRPARCPAG FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA
             AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALPAG INTO :sStatusPag,:dVlrPagar;
        IF ((sStatusPag = 'PD') AND (new.VLRLIQCOMPRA != :dVlrPagar)) THEN
          EXCEPTION CPCOMPRAEX04;
      END
       IF ((substr(old.STATUSCOMPRA,1,1) IN ('P','C')) AND (substr(new.STATUSCOMPRA,1,1)='X')) THEN
      BEGIN
           new.vlrdescitcompra = 0;
           new.vlrprodcompra = 0;
           new.vlrbaseicmscompra = 0;
           new.vlricmscompra = 0;
           new.vlrisentascompra = 0;
           new.vlroutrascompra = 0;
           new.vlrbaseipicompra = 0;
           new.vlripicompra = 0;
           new.vlrliqcompra = 0;
           new.vlripicompra = 0;
      END

  END
    -- Atualizando o status do documento fiscal para 02 - Documento cancelado, quando nota for cancelado pelo sistema.
  IF (substr(new.statuscompra,1,1) = 'X' and new.sitdoc!='02') THEN
  begin
    new.sitdoc = '02';
  end

  if(old.chavenfecompra is null and new.chavenfecompra is not null) then
  begin
    new.emmanut = 'N';
  end

END
^

/* Alter exist trigger... */
ALTER TRIGGER CPCOTACAOTGAIAU001
AS
DECLARE VARIABLE NUMITENS INTEGER;
DECLARE VARIABLE NUMITENSAF INTEGER;
DECLARE VARIABLE NUMITENSAP INTEGER;
DECLARE VARIABLE NUMITENSNA INTEGER;
DECLARE VARIABLE NUMITENSAT INTEGER;
DECLARE VARIABLE NUMITENSEF INTEGER;
DECLARE VARIABLE NUMITENSCA INTEGER;
begin
  if (old.sititsol!='AF') then
  begin
      select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol into :numitens;
      select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitcompitsol='EF' into :numitensef;
      select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and new.sititsol='CA' into :numitensca;
      if (:numitens=:numitensef) then
      begin
        update cpitsolicitacao set sititsol='EF' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sititsol!='EF';
      end
      else if (:numitens=:numitensca) then
      begin
        update cpitsolicitacao set sititsol='CA' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sititsol!='CA';
      end
      if (new.qtdaprovcot!=old.qtdaprovcot) then
      begin
          if (new.qtdaprovcot>0) then
          begin
              update cpitsolicitacao set sititsol='EA' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sititsol='PE';
              select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitaprovitsol='AP' into :numitensap;
              select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitaprovitsol='NA' into :numitensna;
              select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitaprovitsol='AT' into :numitensat;
              if(:numitens!=:numitensaf) then
              begin
                if(:numitens=:numitensat) then
                begin
                   update cpitsolicitacao set sitcompitsol='CT' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitcompitsol!='CT';
                end
                else if((:numitensap>0) or (:numitensna>0)) then
                begin
                   update cpitsolicitacao set sitcompitsol='CP' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitcompitsol!='CP';
                end
                if(:numitens=:numitensna) then
                begin
                   update cpitsolicitacao set sitcompitsol='NA' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sitcompitsol!='NA';
                end
              end
          end
      end
  end
  else if (old.sititsol!='EF') then
  begin
      select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol into :numitens;
      select count(1) from cpcotacao where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sititsol='EF' into :numitensef;
      if (:numitens=:numitensef) then
      begin
        update cpitsolicitacao set sititsol='EF' where codemp=new.codemp and codfilial=new.codfilial and codsol = new.codsol and coditsol = new.coditsol and sititsol!='EF';
      end
  end
  -- Implementação inútil pois não é possivel atribuir falor em atributo "new"
/*  SELECT sititsol FROM cpitsolicitacao
      WHERE CODEMP=new.codemp AND CODFILIAL=new.codfilial and coditsol=new.coditsol and codsol=new.codsol
      INTO new.sititsol;*/
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPFORNECEDTGAU
AS
    declare variable filialpref smallint;
    declare variable geracodunif char(1);

begin
      -- Atualização de descrição do código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S' and new.codunifcod is not null) then
    begin
        update sgunifcod set descunifcod=new.razfor
        where codemp=new.codempuc and codfilial=new.codfilialuc and codunifcod=new.codunifcod;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPFORNECEDTGBI
AS
    declare variable nomemunicfor CHAR(30);
    declare variable geracodunif char(1);
    declare variable codunifcod integer;
    declare variable filialpref smallint;

begin

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(new.codmunic is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemunicfor;
        if (nomemunicfor is not null) then new.cidfor = nomemunicfor;
    end

    if(new.siglauf is not null) then
    begin
        new.uffor=new.siglauf;
    end

    /* Fim da atualização do campo cidade e estado*/

    -- Geração de código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;

    if(:geracodunif = 'S') then
    begin

        select (coalesce(max(codunifcod),0)+1) from sgunifcod where codemp=new.codemp and codfilial=new.codfilial
        into :codunifcod;

        insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod)
        values (new.codemp, new.codfilial, :codunifcod, 'F', new.razfor);
        new.codempuc=new.codemp;
        new.codfilialuc=new.codfilial;
        new.codunifcod=:codunifcod;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPFORNECEDTGBU
as
  declare variable nomemunicfor CHAR(30);
begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(old.codmunic != new.codmunic) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemunicfor;
        if (nomemunicfor is not null) then new.cidfor = nomemunicfor;
    end

    if(new.siglauf is not null) then
    begin
        new.uffor=new.siglauf;
    end

    /* Fim da atualização do campo cidade */

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPIMPORTACAOTGBU
as
begin
  if (new.emmanut is null) then
     new.emmanut='N';
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    new.dtalt=cast('now' as date);
    new.idusualt=user;
    new.halt = cast('now' as time);

    -- Não permitindo valores negativos
    if (new.pesobruto       < 0) then new.pesobruto     = 0;
    if (new.pesoliquido     < 0) then new.pesoliquido   = 0;
    if (new.vmlemi          < 0) then new.pesobruto     = 0;
    if (new.vmldmi          < 0) then new.pesobruto     = 0;
    if (new.vlrii           < 0) then new.pesobruto     = 0;
    if (new.vlripi          < 0) then new.pesobruto     = 0;
    if (new.vlrpis          < 0) then new.pesobruto     = 0;
    if (new.vlrcofins       < 0) then new.pesobruto     = 0;
    if (new.vlrtxsiscomex   < 0) then new.pesobruto     = 0;

    -- Convertendo valores em moeda corrente / estrangeira
    if( (old.cotacaomoeda != new.cotacaomoeda) or (old.vlrfretemi != new.vlrfretemi) or (old.vlrseguromi != new.vlrseguromi) or (old.vlrthc != new.vlrthc) ) then
    begin
        new.vlrfrete    =   new.vlrfretemi      *   new.cotacaomoeda;
        new.vlrseguro   =   new.vlrseguromi     *   new.cotacaomoeda;
        new.vlrthcmi    =   new.vlrthc          /   new.cotacaomoeda;
    end

  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRASERIETGAI
AS
    declare variable codemptm integer;
    declare variable codfilialtm integer;
    declare variable codtipomov integer;
    declare variable estoqtipomov char(1);
    declare variable tipomovserie smallint;
begin
    if(new.numserie is not null)then
        begin

            -- Buscando informações da compra e to tipo de movimento
            select c.codemptm, c.codfilialtm, c.codtipomov, tm.estoqtipomov
                from cpcompra c, eqtipomov tm
                where c.codemp = new.codemp and c.codfilial = new.codfilial and c.codcompra = new.codcompra
                and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and tm.codtipomov=c.codtipomov
                into :codemptm, :codfilialtm, :codtipomov, :estoqtipomov;

            if('S' = :estoqtipomov) then
            begin
                tipomovserie = 1;
            end
            else
            begin
                tipomovserie = 0;
            end

            insert into eqmovserie (codemp, codfilial, codemppd, codfilialpd, codprod,
                codempcp, codfilialcp, codcompra, coditcompra,
                codemptm, codfilialtm, codtipomov,
                numserie, dtmovserie, tipomovserie)

            values(new.codemp, new.codfilial, new.codemppd, new.codfilialpd, new.codprod,
                new.codemp, new.codfilial, new.codcompra, new.coditcompra,
                :codemptm, :codfilialtm, :codtipomov,
                new.numserie,  current_timestamp , :tipomovserie );
        end
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRASERIETGBIBU
as
    declare variable temserie smallint;
begin
    -- Verifica se numero de série já foi inserido nessa compra
    select count(*) from cpitcompraserie its
    where its.codemp = new.codemp and its.codfilial=new.codfilial and its.codcompra=new.codcompra
    and its.codemppd=new.codemppd and its.codfilialpd=new.codfilialpd and its.codprod=new.codprod
    and its.numserie = new.numserie and its.numserie is not null and its.seqitserie!=new.seqitserie
    into temserie;

    if(temserie > 0) then
    begin
        exception cpitcompraex03 new.numserie;
    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRASERIETGBU
as
    declare variable temserie smallint;
begin
    -- Atualizando log
    new.DTALT=cast('now' as date);
    new.IDUSUALT=USER;
    new.HALT = cast('now' as time);

    -- Verificando se foi inserido um numero de série
    if(old.numserie is null and new.numserie is not null) then
    begin
        -- Verificando se o número de série informado, já existe.
        select count(*) from eqserie sr
        where sr.codemp = new.codemppd and sr.codfilial = new.codfilialpd and sr.codprod = new.codprod and sr.numserie = new.numserie
        into :temserie;

        -- se o número de série informado não existe, deve inseri-lo;
        if (temserie = 0) then
        begin
            insert into eqserie (codemp, codfilial, codprod, numserie)
            values(new.codemppd, new.codfilialpd, new.codprod, new.numserie);
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAD
as

declare variable ddtcompra date;
declare variable cflag char(1);
declare variable idoccompra integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;

begin

    -- Se não estifver em manutenção...
    if ( not ( (old.emmanut='S') and (old.emmanut is not null) ) ) then
    begin

        -- Atualizando cabeçalho da compra
        update cpcompra cp set
            cp.vlrdescitcompra = cp.vlrdescitcompra - old.vlrdescitcompra,
            cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra,
            cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra,
            cp.vlricmscompra = cp.vlricmscompra - old.vlricmsitcompra,
            -- Icms substituição tributária
            cp.vlrbaseicmsstcompra = coalesce(cp.vlrbaseicmsstcompra,0) - coalesce(old.vlrbaseicmsstitcompra,0),
            cp.vlricmsstcompra = coalesce(cp.vlricmsstcompra,0) - coalesce(old.vlricmsstitcompra,0),

            cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra,
            cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra,
            cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra,
            cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra,
            cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra,
            cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra,
            cp.vlrfretecompra = cp.vlrfretecompra - old.vlrfreteitcompra,
            cp.vlradiccompra = cp.vlradiccompra - old.vlradicitcompra
        where codcompra=old.codcompra and codemp=old.codemp and codfilial=old.codfilial;

        -- Buscando informações do cabeçaho da compra
        select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov
        from cpcompra c
        where c.codcompra=old.codcompra and c.codemp=old.codemp and c.codfilial=old.codfilial
        into :ddtcompra, :cflag, :idoccompra, :icodemptm, :scodfilialtm, :icodtipomov;

        -- Atualizando movimentação de estoque
        execute procedure eqmovprodiudsp('D', old.codemppd, old.codfilialpd, old.codprod,
        old.codemple, old.codfilialle, old.codlote, :icodemptm, :scodfilialtm, :icodtipomov,
        null, null, null, old.codemp, old.codfilial, old.codcompra, old.coditcompra,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, old.codempnt,
        old.codfilialnt, old.codnat, :ddtcompra, :idoccompra, :cflag, old.qtditcompra, old.custoitcompra,
        old.codempax, old.codfilialax, old.codalmox, null, 'S', old.tiponfitcompra);

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAI
as
    declare variable dtcompra date;
    declare variable flag char(1);
    declare variable doccompra integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;
    declare variable calctrib char(1);

begin

    -- Buscando informações do cabeçalho da compra
    select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov, calctrib
    from cpcompra c
    where c.codcompra = new.codcompra and c.codemp = new.codemp and c.codfilial = new.codfilial
    into :dtcompra, :flag, :doccompra, :codemptm, :codfilialtm, :codtipomov, :calctrib;

    -- Executando procedure para movimentação de produto
    execute procedure eqmovprodiudsp (
        'I', new.codemppd, new.codfilialpd, new.codprod, new.codemple, new.codfilialle, new.codlote,
        :codemptm, :codfilialtm, :codtipomov, null, null, null, new.codemp, new.codfilial, new.codcompra, new.coditcompra,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, new.codempnt, new.codfilialnt, new.codnat,
        :dtcompra, :doccompra, :flag, new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox, null, 'S', new.tiponfitcompra
    );

    -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure cpitcompraseriesp('I', new.codemp, new.codfilial, new.codcompra, new.coditcompra, new.codemppd, new.codfilialpd, new.codprod, new.numserietmp, new.qtditcompra);

    -- Gerando tabela de informações fiscais adicionais (lfitcompra)
    if(calctrib='S') then
    begin
       -- Inserindo registros na tabela de informações fiscais complementares (LFITCOMPRA)
        execute procedure lfgeralfitcomprasp(new.codemp, new.codfilial, new.codcompra, new.coditcompra);
    end

    -- Atualizando cabeçalho da compra

    update cpcompra cp set
    cp.vlrfretecompra = cp.vlrfretecompra + new.vlrfreteitcompra,
    cp.vlradiccompra = cp.vlradiccompra + new.vlradicitcompra
    where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial;


end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGAU
as

declare variable ddtcompra date;
declare variable cflag char(1);
declare variable idoccompra integer;
declare variable icodemptm integer;
declare variable scodfilialtm smallint;
declare variable icodtipomov integer;
declare variable calctrib char(1);
declare variable codimp integer;

begin
    -- Se não estiver em manutenção...
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin
        -- Buscando informações da compra
        select c.dtentcompra, c.flag, c.doccompra, c.codemptm, c.codfilialtm, c.codtipomov, c.calctrib, c.codimp
        from cpcompra c
        where c.codcompra = new.codcompra and c.codemp=new.codemp and c.codfilial = new.codfilial
        into :ddtcompra, :cflag, :idoccompra, :icodemptm, :scodfilialtm, :icodtipomov, :calctrib, :codimp;

        if(:codimp is null) then
        begin
            -- Atualizando cabeçalho da compra (não atualiza frete para não entrar em loop);
            update cpcompra cp set
                cp.vlrdescitcompra = cp.vlrdescitcompra -old.vlrdescitcompra + new.vlrdescitcompra,
                cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra + new.vlrproditcompra,
                cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra + new.vlrbaseicmsitcompra,
                cp.vlricmscompra = cp.vlricmscompra -old.vlricmsitcompra + new.vlricmsitcompra,
                -- Icms substituição tributária
                cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra - old.vlrbaseicmsstitcompra + new.vlrbaseicmsstitcompra,
                cp.vlricmsstcompra = cp.vlricmsstcompra -old.vlricmsstitcompra + new.vlricmsstitcompra,
                
                cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra + new.vlrisentasitcompra,
                cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra + new.vlroutrasitcompra,
                cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra + new.vlrbaseipiitcompra,
                cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra + new.vlripiitcompra,
                cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra + new.vlrliqitcompra,
                cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra + new.vlrfunruralitcompra
            where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codimp is null;
        end
        else
        begin

            -- Atualizando cabeçalho da compra (atualiza o frete);

            update cpcompra cp set
                cp.vlrdescitcompra = cp.vlrdescitcompra -old.vlrdescitcompra + new.vlrdescitcompra,
                cp.vlrprodcompra = cp.vlrprodcompra - old.vlrproditcompra + new.vlrproditcompra,
                cp.vlrbaseicmscompra = cp.vlrbaseicmscompra - old.vlrbaseicmsitcompra + new.vlrbaseicmsitcompra,
                cp.vlricmscompra = cp.vlricmscompra -old.vlricmsitcompra + new.vlricmsitcompra,
                -- Icms substituição tributária
                cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra - old.vlrbaseicmsstitcompra + new.vlrbaseicmsstitcompra,
                cp.vlricmsstcompra = cp.vlricmsstcompra -old.vlricmsstitcompra + new.vlricmsstitcompra,

                cp.vlrisentascompra = cp.vlrisentascompra - old.vlrisentasitcompra + new.vlrisentasitcompra,
                cp.vlroutrascompra = cp.vlroutrascompra - old.vlroutrasitcompra + new.vlroutrasitcompra,
                cp.vlrbaseipicompra = cp.vlrbaseipicompra - old.vlrbaseipiitcompra + new.vlrbaseipiitcompra,
                cp.vlripicompra = cp.vlripicompra - old.vlripiitcompra + new.vlripiitcompra,
                cp.vlrliqcompra = cp.vlrliqcompra - old.vlrliqitcompra + new.vlrliqitcompra,
                cp.vlrfunruralcompra = cp.vlrfunruralcompra - old.vlrfunruralitcompra + new.vlrfunruralitcompra,
                cp.vlrfretecompra = cp.vlrfretecompra - old.vlrfreteitcompra + new.vlrfreteitcompra,
                cp.vlradiccompra = cp.vlradiccompra - old.vlradicitcompra + new.vlradicitcompra
            where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codimp is not null;

        end

        -- Atualizando movimentação de estoque
        execute procedure eqmovprodiudsp('U', new.codemppd, new.codfilialpd, new.codprod,
        new.codemple, new.codfilialle, new.codlote, :icodemptm, :scodfilialtm, :icodtipomov, null, null, null,
        new.codemp, new.codfilial, new.codcompra, new.coditcompra, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, new.codempnt, new.codfilialnt, new.codnat, :ddtcompra, :idoccompra, :cflag,
        new.qtditcompra, new.custoitcompra, new.codempax, new.codfilialax, new.codalmox, null, 'S', new.tiponfitcompra);

        -- Executa procedure para atualização de tabela de vinculo para numeros de serie
        execute procedure cpitcompraseriesp('U', old.codemp, old.codfilial, old.codcompra, old.coditcompra, old.codemppd, old.codfilialpd, old.codprod, new.numserietmp, new.qtditcompra);

        -- Gerando tabela de informações fiscais adicionais (lfitcompra)
        if(calctrib='S') then
        begin
           -- Inserindo registros na tabela de informações fiscais complementares (LFITCOMPRA)
            execute procedure lfgeralfitcomprasp(new.codemp, new.codfilial, new.codcompra, new.coditcompra);
        end


    end

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
ALTER TRIGGER CPITCOMPRATGBI
as

declare variable srefprod varchar(20);
declare variable habCustoCompra char(1);
declare variable calctrib char(1);
declare variable utilizatbcalcca char(1) ;
declare variable codempcc integer;
declare variable codfilialcc smallint;
declare variable codcalc integer;
declare variable codempcf integer;
declare variable codfilialcf smallint;
declare variable codcf integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;



begin

    if (new.calccusto is null) then 
       new.calccusto = 'S';
    -- Buscando preferências
    select p.custocompra, p.utilizatbcalcca from sgprefere1 p
    where p.codemp=new.codemp and p.codfilial=new.codfilial
    into :habCustoCompra, :utilizatbcalcca;
    if (utilizatbcalcca is null) then
       utilizatbcalcca = 'N';

    --Buscando informações da compra
    select cp.calctrib from cpcompra cp
    where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
    into :calctrib;

    -- Buscando referência do produto
    select refprod from eqproduto
    where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
    into srefprod;

    -- Carregando valores padrão
    if (new.refprod is null) then new.refprod = srefprod;
    if (new.vlrdescitcompra is null) then new.vlrdescitcompra = 0;
    if (new.vlrbaseicmsitcompra is null) then new.vlrbaseicmsitcompra = 0;
    if (new.vlricmsitcompra is null) then new.vlricmsitcompra = 0;
    if (new.vlrbaseipiitcompra is null) then new.vlrbaseipiitcompra = 0;
    if (new.vlripiitcompra is null) then new.vlripiitcompra = 0;
    if (new.vlrliqitcompra is null) then new.vlrliqitcompra = 0;
    if (new.vlradicitcompra is null) then new.vlradicitcompra = 0;
    if (new.vlrfreteitcompra is null) then new.vlrfreteitcompra = 0;
    if (new.vlrbaseicmsstitcompra is null) then new.vlrbaseicmsstitcompra = 0;
    if (new.vlricmsstitcompra is null) then new.vlricmsstitcompra = 0;
    

    if(new.vlrliqitcompra=0) then
    begin
       new.vlrliqitcompra = (new.qtditcompra * new.precoitcompra) - new.vlrdescitcompra + new.vlradicitcompra;
    end

    -- Buscando e carregando almoxarifado do produto
    if (new.codalmox is null) then
    begin
        select codempax, codfilialax, codalmox from eqproduto
        where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
        into new.codempax, new.codfilialax, new.codalmox;
    end

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
    
    
    -- Buscando e carregando custo do produto
    if ( ( ('N' = habCustoCompra) or (new.custoitcompra is null) ) and (new.calccusto='S') ) then
    begin
        if (utilizatbcalcca='N') then
        begin
            select nvlrcusto
            from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra
            , new.vlricmsitcompra,'N',0,'N',0)
            into new.custoitcompra;
        end
        else
        begin

            if (new.coditfisc is null) then
            begin
               select codempfr, codfilialfr, codfor, codemptm, codfilialtm, codtipomov
                  from cpcompra
                  where codemp=new.codemp and codfilial=new.codfilial and codcompra=new.codcompra
                  into :codempcf, :codfilialcf, :codcf, :codemptm, :codfilialtm, :codtipomov;

               select itfisc.codempif, itfisc.codfilialif, itfisc.codfisc, itfisc.coditfisc
                from lfbuscafiscalsp(new.codemppd, new.codfilialpd, new.codprod, :codempcf, :codfilialcf, :codcf
                 , :codemptm, :codfilialtm, :codtipomov, 'CP', new.codnat, null, null, null, null) itfisc
               into new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
            end

            select codempcc, codfilialcc, codcalc from lfitclfiscal itcl
              where itcl.codemp=new.codempif and itcl.codfilial=new.codfilialif and itcl.codfisc=new.codfisc and itcl.coditfisc=new.coditfisc
            into :codempcc, :codfilialcc, :codcalc ;

            select vlrcusto from lfcalccustosp01( :codempcc, :codfilialcc, :codcalc, new.qtditcompra, new.vlrproditcompra*new.qtditcompra
             , new.vlricmsitcompra, new.vlripiitcompra, 0, 0, new.vlrissitcompra, new.vlrfunruralitcompra
             , new.vlriiitcompra, 0, 0, 0, 0, 0 )
            into new.custoitcompra;

        end
    end

    new.calccusto = 'S';
    
    --Atualizando totais da compra
    update cpcompra cp set cp.vlrdescitcompra=cp.vlrdescitcompra + new.vlrdescitcompra,
    cp.vlrprodcompra = cp.vlrprodcompra + new.vlrproditcompra,
    cp.vlrbaseicmscompra = cp.vlrbaseicmscompra + new.vlrbaseicmsitcompra,
    cp.vlricmscompra = cp.vlricmscompra + new.vlricmsitcompra,
    -- Icms subtituição tributária
    cp.vlrbaseicmsstcompra = cp.vlrbaseicmsstcompra + new.vlrbaseicmsstitcompra,
    cp.vlricmsstcompra = cp.vlricmsstcompra + new.vlricmsstitcompra,
    -- 
    cp.vlrisentascompra = cp.vlrisentascompra + new.vlrisentasitcompra,
    cp.vlroutrascompra = cp.vlroutrascompra + new.vlroutrasitcompra,
    cp.vlrbaseipicompra = cp.vlrbaseipicompra + new.vlrbaseipiitcompra,
    cp.vlripicompra = cp.vlripicompra + new.vlripiitcompra,
    cp.vlrliqcompra = cp.vlrliqcompra + new.vlrliqitcompra,
    cp.vlrfunruralcompra = coalesce(cp.vlrfunruralcompra,0) + coalesce(new.vlrfunruralitcompra,0)
    where cp.codcompra=new.codcompra and cp.codemp=new.codemp and cp.codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITCOMPRATGBU
as

declare variable srefprod varchar(20);
declare variable sadicfrete char(1);
declare variable sadicadic char(1);
declare variable habcustocompra char(1);
declare variable vlritcusto numeric(15, 5);
declare variable statuscompra char(2);
declare variable calctrib char(1);
declare variable utilizatbcalcca char(1);
declare variable codempcc integer;
declare variable codfilialcc smallint;
declare variable codcalc integer;

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        if ( new.vlrbaseicmsstitcompra is null ) then new.vlrbaseicmsstitcompra=0;
        if (new.vlricmsstitcompra is null ) then new.vlricmsstitcompra=0;
        
        
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
            select p.custocompra, p.utilizatbcalcca from sgprefere1 p
            where p.codemp=new.codemp and p.codfilial=new.codfilial
            into :habcustocompra, :utilizatbcalcca;

            --Buscando informações da compra
            select cp.calctrib from cpcompra cp
            where cp.codemp=new.codemp and cp.codfilial=new.codfilial and cp.codcompra=new.codcompra
            into :calctrib;

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

            -- Buscando e carregando custo do produto
            if ( ('N' = habCustoCompra) or (new.custoitcompra is null)) then
            begin
                if (utilizatbcalcca='N') then
                begin
                    select nvlrcusto from cpcomprasp01(new.codemp, new.codfilial, new.qtditcompra, new.vlrliqitcompra, new.vlricmsitcompra
                    , :sadicfrete, new.vlrfreteitcompra, :sadicadic, new.vlradicitcompra)
                    into new.custoitcompra;
                end
                else
                begin
                    select codempcc, codfilialcc, codcalc from lfitclfiscal itcl
                      where itcl.codemp=new.codempif and itcl.codfilial=new.codfilialif and itcl.codfisc=new.codfisc and itcl.coditfisc=new.coditfisc
                    into :codempcc, :codfilialcc, :codcalc ;
        
                    select vlrcusto from lfcalccustosp01( :codempcc, :codfilialcc, :codcalc, new.qtditcompra, new.vlrproditcompra*new.qtditcompra
                     , new.vlricmsitcompra, new.vlripiitcompra, 0, 0, new.vlrissitcompra, new.vlrfunruralitcompra
                     , new.vlriiitcompra, 0, 0, 0, 0, 0 )
                    into new.custoitcompra;
                end
            end
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGAD
AS
begin

    -- Atualizando totais da importação

    update cpimportacao set

    pesobruto           =   pesobruto           -   old.pesobruto,
    pesoliquido         =   pesoliquido         -   old.pesoliquido,
    vmlemi              =   vmlemi              -   old.vmlemi,
    vmldmi              =   vmldmi              -   old.vmldmi,
    vmle                =   vmle                -   old.vmle,
    vmld                =   vmld                -   old.vmld,
    vlradmi             =   vlradmi             -   old.vlradmi,
    vlrad               =   vlrad               -   old.vlrad,
    vlrbaseicms         =   vlrbaseicms         -   old.vlrbaseicms,

    -- Tributos

    vlrii               =   vlrii               -   old.vlrii,
    vlripi              =   vlripi              -   old.vlripi,
    vlrpis              =   vlrpis              -   old.vlrpis,
    vlrcofins           =   vlrcofins           -   old.vlrcofins,
    vlrtxsiscomex       =   vlrtxsiscomex       -   old.vlrtxsiscomex,
    vlricms             =   vlricms             -   old.vlricms,
    vlricmsdiferido     =   vlricmsdiferido     -   old.vlricmsdiferido,
    vlricmsdevido       =   vlricmsdevido       -   old.vlricmsdevido,
    vlricmscredpresum   =   vlricmscredpresum   -   old.vlricmscredpresum,
    vlricmsrecolhimento =   vlricmsrecolhimento -   old.vlricmsrecolhimento


    where codemp=old.codemp and codfilial=old.codfilial and codimp=old.codimp;

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGAI
AS
begin

  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
  begin

    -- Atualizando totais da importação

    update cpimportacao set

    pesobruto           =   pesobruto           +   new.pesobruto, 
    pesoliquido         =   pesoliquido         +   new.pesoliquido,
    vmlemi              =   vmlemi              +   new.vmlemi,
    vmldmi              =   vmldmi              +   new.vmldmi,
    vmle                =   vmle                +   new.vmle,
    vmld                =   vmld                +   new.vmld,
    vlradmi             =   vlradmi             +   new.vlradmi,
    vlrad               =   vlrad               +   new.vlrad,
    vlrbaseicms         =   vlrbaseicms         +   new.vlrbaseicms,

    -- Tributos

    vlrii               =   vlrii               +   new.vlrii,
    vlripi              =   vlripi              +   new.vlripi,
    vlrpis              =   vlrpis              +   new.vlrpis,
    vlrcofins           =   vlrcofins           +   new.vlrcofins,
    vlrtxsiscomex       =   vlrtxsiscomex       +   new.vlrtxsiscomex,
    vlricms             =   vlricms             +   new.vlricms,
    vlricmsdiferido     =   vlricmsdiferido     +   new.vlricmsdiferido,
    vlricmsdevido       =   vlricmsdevido       +   new.vlricmsdevido,
    vlricmscredpresum   =   vlricmscredpresum   +   new.vlricmscredpresum,
    vlricmsrecolhimento =   vlricmsrecolhimento +   new.vlricmsrecolhimento

    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp;
    
  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGAU
AS
begin

    -- Atualizando totais da importação
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    update cpimportacao set

    pesobruto           = pesobruto         - old.pesobruto                 + new.pesobruto,
    pesoliquido         = pesoliquido       - old.pesoliquido               + new.pesoliquido,
    vmlemi              = vmlemi            - old.vmlemi                    + new.vmlemi,
    vmldmi              = vmldmi            - old.vmldmi                    + new.vmldmi,
    vmle                = vmle              - old.vmle                      + new.vmle,
    vmld                = vmld              - old.vmld                      + new.vmld,
    vlrad               = vlrad             - old.vlrad                     + new.vlrad,
    vlradmi             = vlradmi           - old.vlradmi                   + new.vlradmi,
    vlrbaseicms         = vlrbaseicms       - old.vlrbaseicms               + new.vlrbaseicms,

    -- Tributos

    vlrii               =   vlrii               -   old.vlrii               +   new.vlrii,
    vlripi              =   vlripi              -   old.vlripi              +   new.vlripi,
    vlrpis              =   vlrpis              -   old.vlrpis              +   new.vlrpis,
    vlrcofins           =   vlrcofins           -   old.vlrcofins           +   new.vlrcofins,
    vlrtxsiscomex       =   vlrtxsiscomex       -   old.vlrtxsiscomex       +   new.vlrtxsiscomex,
    vlricms             =   vlricms             -   old.vlricms             +   new.vlricms,
    vlricmsdiferido     =   vlricmsdiferido     -   old.vlricmsdiferido     +   new.vlricmsdiferido,
    vlricmsdevido       =   vlricmsdevido       -   old.vlricmsdevido       +   new.vlricmsdevido,
    vlricmscredpresum   =   vlricmscredpresum   -   old.vlricmscredpresum   +   new.vlricmscredpresum,
    vlricmsrecolhimento =   vlricmsrecolhimento -   old.vlricmsrecolhimento +   new.vlricmsrecolhimento

    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp;
    
 end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGBI
AS
    declare variable cotacao numeric(15,5);
begin
    
  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
  begin
    
    -- Buscando cotação da moeda de importação
    select imp.cotacaomoeda from cpimportacao imp where imp.codemp=new.codemp and imp.codfilial=new.codfilial and imp.codimp=new.codimp
    into :cotacao;

    -- Calculando VMLE , VMLD e VLRAD

    new.vmlemi      =   new.qtd         *       new.precomi;
    new.vmldmi      =   new.vmlemi      +       new.vlrfretemi;
    new.vlradmi     =   new.vmldmi      +       new.vlrthcmi;


    -- Calculando valores em moeda corrente
    new.preco       =   new.precomi     *       :cotacao;
    new.vmle        =   new.vmlemi      *       :cotacao;
    new.vmld        =   new.vmldmi      *       :cotacao;
    new.vlrfrete    =   new.vlrfretemi  *       :cotacao;
    new.vlrseguro   =   new.vlrseguromi *       :cotacao;
    new.vlrthc      =   new.vlrthcmi    *       :cotacao;
    new.vlrad       =   new.vlradmi     *       :cotacao;

    -- Calculando II

    new.vlrii       =   new.vlrad       *       new.aliqii  /   100.00;

    -- Gerando o número do sequencial de adição.
    select coalesce(count(*),0) + 1
    from cpitimportacao
    where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp and codncm=new.codncm
    into new.seqadic;
    
  end
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER CPITIMPORTACAOTGBU
as
    declare variable cotacao        decimal(15,5);

begin

  if (new.emmanut is null) then
     new.emmanut='N';
  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
  begin

    -- Atualizando log
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

    -- Buscando informações do cabeçalho
    select imp.cotacaomoeda
    from cpimportacao imp where imp.codemp=new.codemp and imp.codfilial=new.codfilial and imp.codimp=new.codimp
    into :cotacao;

    -- Calculando VMLE e VMLD

    new.vmlemi      =  ( new.qtd         *       new.precomi ) + new.vlrthcmi ;
    new.vmldmi      =   new.vmlemi       +       new.vlrfretemi;
    new.vlradmi     =   new.vmldmi       ;
    new.vlrvmcv     =  ( new.qtd         *       new.precomi ) + new.vlrfretemi ;

    -- Calculando valores em moeda corrente
    new.preco       =   new.precomi     *       :cotacao;
    new.vmle        =   new.vmlemi      *       :cotacao;
    new.vmld        =   new.vmldmi      *       :cotacao;
    new.vlrfrete    =   new.vlrfretemi  *       :cotacao;
    new.vlrseguro   =   new.vlrseguromi *       :cotacao;
    new.vlrthc      =   new.vlrthcmi    *       :cotacao;
    new.vlrad       =   new.vlradmi     *       :cotacao;

    -- Calculando II

    new.vlrii       =   new.vlrad       *       new.aliqii  /   100.00;

    -- Calculando IPI

    new.vlripi      = ( new.vlrad       +       new.vlrii ) *  ( new.aliqipi /   100.00);

    -- Calculando o ICMS

    new.vlrbaseicms         =  cast ( ( new.vlrad      +       new.vlrcofins      +   new.vlrpis   +     new.vlripi   + new.vlrii + new.vlrtxsiscomex + new.vlritdespad ) / (  cast(1.00 as decimal(15,5)) - (new.aliqicmsuf / 100.00) ) as decimal(15,5));

    new.vlricms             =   cast( new.vlrbaseicms *   cast( ( new.aliqicmsuf     / cast(100.00 as decimal(15,5)) ) as decimal(15,5)) as decimal(15,5));

    if(new.percdifericms > 0) then
        new.vlricmsdiferido     =   new.vlricms     *    cast(  ( new.percdifericms  / cast(100.00 as decimal(15,5)) ) as decimal(15,5));
    else
        new.vlricmsdiferido     =   0.00;

    new.vlricmsdevido       =   new.vlricms     -       new.vlricmsdiferido;

    new.vlricmscredpresum   =   cast( new.vlrbaseicms *   cast( ( new.aliqicmsuf  / cast(100.00 as numeric(15,5) ) ) as decimal(15,5) ) as decimal(15,5)) ;
    
    new.vlricmscredpresum   =   new.vlricmscredpresum - new.vlricmsdiferido;

    if (new.perccredpresimp<100) then
    begin
       new.vlricmscredpresum = new.vlricmscredpresum * cast(( new.perccredpresimp / cast(100.00 as numeric(15,5) ) ) as decimal(15,5)) ; 
    end

    new.vlricmsrecolhimento =   new.vlricmsdevido   -   new.vlricmscredpresum;

    -- Gerando o número do sequencial de adição.
    if(old.codncm <> new.codncm) then
    begin
        select coalesce(count(*),0) + 1
        from cpitimportacao
        where codemp=new.codemp and codfilial=new.codfilial and codimp=new.codimp and codncm=new.codncm
        into new.seqadic;
    end
    
  end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CPORDCOMPRATGBU
AS
begin
    
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);
    
    -- Na aprovação total mudar status para aguardando recebimento
    if(old.statusapoc='PE' and new.statusapoc='AT') then
    begin
        new.statusoc='AR';
    
        -- Gerando contas a pagar de empenho
        execute procedure fnadicpagarsp02(
            new.codemp, new.codfilial, new.codordcp,
            new.codemppg, new.codfilialpg, new.codplanopag,
            new.codempfr, new.codfilialfr, new.codfor, 
            substring(new.obsordcp from 1 for 250) );

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER CRCHAMADOTGAI
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

/* Alter exist trigger... */
ALTER TRIGGER CRCHAMADOTGAU
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

/* Alter exist trigger... */
ALTER TRIGGER EQEXPEDICAOTGAI
as
declare variable codempte integer;
declare variable codfilialte smallint;
declare variable codtipoexped integer;
declare variable codprocexped integer;
declare variable coditexped integer;

begin

    -- Se tiver um produto padrão no cabeçalho, deve gerar os ítens automaticamente.
    if(new.codprod is not null) then
    begin

        coditexped = 1;
    
        for select pr.codemp, pr.codfilial, pr.codtipoexped, pr.codprocexped
        from eqprocexped pr
        where pr.codemp=new.codempte and pr.codfilial=new.codfilialte and pr.codtipoexped=new.codtipoexped
    order by pr.codprocexped
        into :codempte,  :codfilialte, :codtipoexped, :codprocexped do
        begin

            select coalesce( max(coditexped) , 0 ) + 1
            from eqitexpedicao ie
            where ie.codemp=new.codemp and ie.codfilial=new.codfilial and ie.ticket=new.ticket
            into coditexped;
        
            insert into eqitexpedicao
            ( codemp, codfilial, ticket, coditexped, codemppd, codfilialpd, codprod, refprod, codempte, codfilialte, codtipoexped, codprocexped ) values
            ( new.codemp, new.codfilial, new.ticket, :coditexped, new.codemppd, new.codfilial, new.codprod, new.refprod, :codempte, :codfilialte, :codtipoexped, :codprocexped );


        end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQEXPEDICAOTGBU
as
begin

    -- Atualizando log de atualização do registro
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT=cast('now'AS TIME);

    -- Verifica se um romaneio foi vinculado e atualizao o status.
    if(old.codroma is null and new.codroma is not null) then
    begin
    
        new.status='RE';

    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGAD
AS
begin
  /* Apos a exclusão do inventário */
  EXECUTE PROCEDURE EQMOVPRODIUDSP('D', old.CODEMPPD, old.CODFILIALPD,
     old.CODPROD, old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
     old.CODEMPTM, old.CODFILIALTM, old.CODTIPOMOV, old.CODEMP,
     old.CODFILIAL, old.CODINVPROD, null, null, null, null,null,
     null, null, null, null, null, null, null, null, null, null, null,
     null, null, null,null,
     null, old.DATAINVP, old.CODINVPROD, 'S', old.QTDINVP, old.PRECOINVP,
     old.CODEMPAX, old.CODFILIALAX, old.CODALMOX, null, 'S', 'N');
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQINVPRODTGBU
AS
  DECLARE VARIABLE CLOTEPROD CHAR;
BEGIN
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
  if ( new.emmanut is null) then
      new.emmanut='N';

  if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
  begin

      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      SELECT CLOTEPROD FROM EQPRODUTO WHERE CODPROD=new.CODPROD
        AND CODEMP=new.CODEMPPD AND CODFILIAL=new.CODFILIALPD INTO CLOTEPROD;
      IF (new.CODPROD != old.CODPROD) THEN
        EXCEPTION EQINVPRODEX01;
      IF (new.CODLOTE != old.CODLOTE) THEN
        EXCEPTION EQINVPRODEX02;
      IF (new.DATAINVP != old.DATAINVP) THEN
        EXCEPTION EQINVPRODEX03;
      IF (new.CODALMOX != old.CODALMOX) THEN
        EXCEPTION EQINVPRODEX04;
      IF (CLOTEPROD = 'S' AND new.CODLOTE IS NULL) THEN
        EXCEPTION EQINVPRODEX05;
  end
  
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSTGAI
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

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
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
  END
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCITOSTGBI
AS
declare variable rmaprod char(1);
declare variable tipoprod varchar(2);

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
--        new.gerarma = 'N';
        new.geranovo = 'S';
        new.statusitos = 'AN';
    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCSERIETGBIBU
as
    declare variable temserie smallint;
begin
    -- Verifica se numero de série já foi inserido nessa entrada
    select count(*) from eqitreCmercserie its
    where its.codemp = new.codemp and its.codfilial=new.codfilial and its.ticket=new.ticket
    and its.codemppd=new.codemppd and its.codfilialpd=new.codfilialpd and its.codprod=new.codprod
    and its.numserie = new.numserie and its.numserie is not null and its.seqitserie!=new.seqitserie
    into temserie;

    if(temserie > 0) then
    begin
        exception cpitcompraex03 new.numserie;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCSERIETGBU
as
    declare variable temserie smallint;
begin
    -- Atualizando log
    new.DTALT=cast('now' as date);
    new.IDUSUALT=USER;
    new.HALT = cast('now' as time);

    -- Verificando se foi inserido um numero de série
    if(old.numserie is null and new.numserie is not null) then
    begin
        -- Verificando se o número de série informado, já existe.
        select count(*) from eqserie sr
        where sr.codemp = new.codemppd and sr.codfilial = new.codfilialpd and sr.codprod = new.codprod and sr.numserie = new.numserie
        into :temserie;

        -- se o número de série informado não existe, deve inseri-lo;
        if (temserie = 0) then
        begin
            insert into eqserie (codemp, codfilial, codprod, numserie)
            values(new.codemppd, new.codfilialpd, new.codprod, new.numserie);
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCTGAI
AS
begin
    -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure eqitrecmercseriesp('I', new.codemp, new.codfilial, new.ticket, new.coditrecmerc, new.codemppd, new.codfilialpd, new.codprod, new.numserie, new.qtditrecmerc);

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCTGAU
as
    declare variable tipoprocrecmerc char(2);

begin

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
  -- buscando tipo de amostragem

    select pr.tipoprocrecmerc
    from eqprocrecmerc pr, eqitrecmerc ir
    where ir.codemp=new.codemp and ir.codfilial=new.codfilial and ir.ticket=new.ticket and ir.coditrecmerc=new.coditrecmerc
    and pr.codemp=ir.codemptp and pr.codfilial=ir.codfilialtp and pr.codtiporecmerc=ir.codtiporecmerc and pr.codprocrecmerc=ir.codprocrecmerc
    into :tipoprocrecmerc;

    if( ( coalesce(new.mediaamostragem,0) > 0 )  and ( coalesce(new.rendaamostragem,0) > 0 ) ) then
    begin

        update eqrecmerc rm set
        rm.mediaamostragem = ( coalesce(rm.mediaamostragem,new.mediaamostragem) + new.mediaamostragem ) / 2,
        rm.rendaamostragem = ( coalesce(rm.rendaamostragem,new.rendaamostragem) + new.rendaamostragem ) / 2
        where
        rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;

    end

    if(new.statusitrecmerc='FN') then
    begin

        if (tipoprocrecmerc='PI') then
        begin
            update eqrecmerc rm set rm.status='E1'
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
        else if (tipoprocrecmerc='TR') then
        begin
            update eqrecmerc rm set
            rm.status='E2',
            rm.rendaamostragem=new.rendaamostragem
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
        else if (tipoprocrecmerc='PF') then
        begin
            update eqrecmerc rm set rm.status='FN'
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket;
        end
    end


    -- Executa procedure para atualização de tabela de vinculo para numeros de serie
    execute procedure eqitrecmercseriesp('U', old.codemp, old.codfilial, old.ticket, old.coditrecmerc, old.codemppd, old.codfilialpd, old.codprod, new.numserie, new.qtditrecmerc);

    -- Atualiza registros de ticket com número de série da tabela eqmovserie
    if (old.codprod <> new.codprod) then
    begin
        update eqmovserie eq set eq.codprod=new.codprod where
        eq.ticket=old.ticket and eq.coditrecmerc=old.coditrecmerc and
        eq.codfilialrc=old.codfilial and eq.codemprc=old.codemp and
        eq.codprod<>new.codprod;
    end

    if (old.numserie <> new.numserie) then
    begin
        update eqmovserie eq set eq.numserie=new.numserie where
        eq.ticket=old.ticket and eq.coditrecmerc=old.coditrecmerc and
        eq.codfilialrc=old.codfilial and eq.codemprc=old.codemp and
        eq.numserie<>new.numserie;
    end
  END
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRECMERCTGBD
AS
begin

    -- Executa procedure para exclusão de tabela de vinculo para numeros de serie
    execute procedure eqitrecmercseriesp('D', old.codemp, old.codfilial, old.ticket, old.coditrecmerc, old.codemppd, old.codfilialpd, old.codprod, null, old.qtditrecmerc);


end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAIAU
AS
declare variable icodemptm int;
declare variable icodfilialtm smallint;
declare variable icodtipomov integer;
begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

      if(new.sititrma='EF') then
      begin
        select tm.codemptm,tm.codfilialtm,tm.codtipomovtm from eqtipomov tm, eqrma rm
           where tm.codemp=rm.codemptm and tm.codfilial=rm.codfilialtm and tm.codtipomov=rm.codtipomov
           and rm.codemp=new.codemp and rm.codfilial=new.codfilial and codrma=new.codrma
           into :icodemptm,:icodfilialtm,:icodtipomov;
        if((:icodemptm is not null) and (:icodfilialtm is not null) and (:icodtipomov is not null)) then
        begin
           update eqrma set codemptm=:icodemptm, codfilialtm=:icodfilialtm, codtipomov=:icodtipomov where
           codemp=new.codemp and codfilial=new.codfilial and codrma=new.codrma;
        end
        
     end
  end

  -- Atualização do status do item de ordem de serviço ao expedir o item de rma
  if( new.coditos is not null and new.sitexpitrma in ('ET','EP')) then
  begin

    update eqitrecmercitos ios set ios.statusitos='CO'
    where ios.codemp=new.codempos and ios.codfilial=new.codfilialos and ios.ticket=new.ticket
    and ios.coditrecmerc = new.coditrecmerc and ios.coditos=new.coditos;

  end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGAU
as
    declare variable icodemptm int;
    declare variable icodfilialtm smallint;
    declare variable icodtipomov int;
    declare variable ddtrma date;
    declare variable baixarmaaprov char(1);
    declare variable estoque char(1);
    declare variable qtdmov numeric(15,5);

begin
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    -- if ( new.emmanut is null) then
    --     new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin


        -- Carregando preferências
        select baixarmaaprov from sgprefere5
        where codemp=new.codemp and codfilial=new.codfilial
        into :baixarmaaprov;
    
        qtdmov = new.qtdexpitrma;
    
        if(:baixarmaaprov='S' and new.sitaprovitrma in ('AT','AP')) then
        begin
            estoque = 'S';
            qtdmov = new.qtdaprovitrma;
        end
        else
        begin
            estoque = 'N';
        end
    
    
        -- Carregando informações do cabeçalho (RMA)
        select r.dtareqrma,r.codemptm,r.codfilialtm,r.codtipomov
        from eqrma r
        where r.codrma = new.codrma and r.codemp=new.codemp and r.codfilial = new.codfilial
        into :ddtrma,:icodemptm,:icodfilialtm,:icodtipomov;
    
        -- Movimentação de estoque
        execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
            new.codemple, new.codfilialle, new.codlote, :icodemptm,:icodfilialtm, :icodtipomov,
            null, null, null ,null, null,null, null, null, null, null, null, null,
            new.codemp, new.codfilial, new.codrma, new.coditrma, null, null, null, null,
            null, null, null, null, :ddtrma, new.codrma, :estoque, :qtdmov, new.precoitrma,
            new.codempax, new.codfilialax, new.codalmox, null, 'S', 'N' );
    
   end
   
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQITRMATGBU
AS
    declare variable statusitem char(2);

begin


    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

       -- Atualizando log.
       new.dtalt = cast('now' AS DATE);
       new.idusualt = USER;
       new.halt = cast('now' AS TIME);

       -- Acertando almoxarifado (quando não informado)
       if (new.codalmox is null) then
           select CODEMPAX,CODFILIALAX,CODALMOX from eqproduto where
               codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
           into new.codempax, new.codfilialax,new.codalmox;

       -- Acertando os status.
       if (old.sititrma!='AF') then
       begin
           if (new.qtdaprovitrma!=old.qtdaprovitrma or (NEW.sitaprovitrma='AT') ) then
           begin
               if ((new.sitaprovitrma='AT') and (new.qtdaprovitrma=0) and (old.sitaprovitrma!='AT')) then
               begin
                   new.qtdaprovitrma=new.qtditrma;
               end
               if (new.qtdaprovitrma>0) then
               begin
                   if (new.qtdaprovitrma<new.qtditrma) then
                   begin
                       statusitem = 'AP'; -- Aprovação parcial
                   end
                   else
                   begin
                       statusitem = 'AT'; -- Aprovação total
                   end

                   -- atualizando status e data da aprovação
                   new.sitaprovitrma=:statusitem;
                   new.dtaprovitrma=cast('today' as date);

                   if(new.sititrma='PE') then
                   begin
                       new.sititrma='EA';
                   end
               end
           end
       end
       else if (old.sititrma!='EF') then
       begin
           if (new.qtdexpitrma!=old.qtdexpitrma or (NEW.sitexpitrma='ET') ) then
           begin
               if ((new.sitexpitrma='ET') and (new.qtdexpitrma=0) and (old.sitexpitrma!='ET')) then
               begin
                   new.qtdexpitrma=new.qtdaprovitrma;
               end
               if (new.qtdexpitrma>0) then
               begin
                   if (new.qtdexpitrma<new.qtdaprovitrma) then
                   begin
                       statusitem = 'EP'; -- Expedição parcial
                   end
                   else
                   begin
                       statusitem = 'ET'; -- Expedição total
                   end

                   -- Atualizando status e data da expedição
                   new.sitexpitrma=:statusitem;
                   new.dtaexpitrma=cast('today' as date);

                   -- Atualizando custo do produto no momento da expedição.
                   select ncustompmax from eqprodutosp01(new.codemppd,new.codfilialpd,new.codprod,new.codempax,new.codfilialax,new.codalmox)
                   into new.precoitrma;

               end
           end
       end
       if(new.sititrma='CA') then
       begin
           new.qtdaprovitrma=0;
           new.qtdexpitrma=0;
           new.sitaprovitrma='NA';
           new.sitexpitrma='NA';
       end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQLOTETGBU
as
  DECLARE VARIABLE ICODFILIAL INTEGER;
  DECLARE VARIABLE CESTLOTNEG CHAR(1);
  DECLARE VARIABLE CESTNEGGRUP CHAR(1);
  DECLARE VARIABLE CESTLOTNEGGRUP CHAR(1);
begin
  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :iCodFilial;
  SELECT ESTLOTNEG,ESTNEGGRUP FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=:iCodFilial
     INTO :cEstLotNeg, :cEstNegGrup;
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
  if (new.SLDLOTE is null) then
     new.SLDLOTE = 0;
  if (new.SLDRESLOTE is null) then
     new.SLDRESLOTE = 0;
  if (new.SLDCONSIGLOTE is null) then
     new.SLDCONSIGLOTE = 0;
  new.SLDLIQLOTE = new.SLDLOTE-new.SLDRESLOTE-new.SLDCONSIGLOTE;
  if (cEstNegGrup is null) then
      cEstNegGrup ='N';
  if (cEstLotNeg Is null) then
      cEstLotNeg = 'N';

  if ( new.SLDLOTE < 0) then
  begin
     if (cEstNegGrup = 'S') then
     begin
        SELECT G.ESTLOTNEGGRUP FROM EQGRUPO G, EQPRODUTO P
          WHERE P.CODEMP=new.CODEMP AND P.CODFILIAL=new.CODFILIAL AND
            P.CODPROD=new.CODPROD AND G.CODEMP=P.CODEMPGP AND
            G.CODFILIAL=P.CODFILIALGP AND G.CODGRUP=P.CODGRUP
          INTO :cEstLotNegGrup;
        if (cEstLotNegGrup is null) then
           cEstLotNegGrup = 'N';
        if (cEstLotNegGrup='N') then
           EXCEPTION EQLOTEEX01 'O LOTE '||rtrim(new.CODLOTE)||
              ' NÃO POSSUI SALDO P/COMPL. A OPERAÇÃO';
     end
     else if (cEstLotNeg='N') then
           EXCEPTION EQLOTEEX01 'O LOTE '||rtrim(new.CODLOTE)||
              ' NÃO POSSUI SALDO P/COMPL. A OPERAÇÃO';
  end
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQMOVPRODTGAU
AS
DECLARE VARIABLE SOPERADOR SMALLINT;
DECLARE VARIABLE NQTDMOVPRODOLD NUMERIC(15, 5);
DECLARE VARIABLE NQTDMOVPRODNEW NUMERIC(15, 5);
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) ) THEN
  BEGIN

      IF ( ( old.ESTOQMOVPROD='S' ) OR (new.ESTOQMOVPROD='S') ) THEN
      BEGIN
          NQTDMOVPRODOLD = old.QTDMOVPROD;
          NQTDMOVPRODNEW = new.QTDMOVPROD;
          IF (new.TIPOMOVPROD = 'S') THEN /* Se for saída tem que diminuir do estoque */
            SOPERADOR = -1;
          ELSE
            SOPERADOR = 1;
          /* Caso o flag de controle de estoque tenha mudado */
          IF ( (old.ESTOQMOVPROD='S') AND (new.ESTOQMOVPROD='N') ) THEN
              NQTDMOVPRODNEW = 0;
          ELSE IF ( (old.ESTOQMOVPROD='N') AND (new.ESTOQMOVPROD='S') ) THEN
              NQTDMOVPRODOLD = 0;
          EXECUTE PROCEDURE EQMOVPRODATEQSP(new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
            old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
            new.CODEMPLE, new.CODFILIALLE, new.CODLOTE,
            :SOPERADOR, NQTDMOVPRODOLD, NQTDMOVPRODNEW,
            old.CODEMPAX, old.CODFILIALAX, old.CODALMOX,
            new.CODEMPAX, new.CODFILIALAX, new.CODALMOX);
      END
   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQPRODUTOTGAI
AS
begin
    -- Inserindo registro na tabela de log do produto (para permitir processamento de preços
    insert into eqprodutolog(codemp, codfilial, codprod, seqlog, precobaseprodant, precobaseprodnovo)
    values (new.codemp, new.codfilial, new.codprod, 1, 0.00, new.precobaseprod);
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQPRODUTOTGAU
AS
    declare variable seqlog integer;
begin
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN  
    --Verifica se o preço foi realmente alterado;
    if(new.precobaseprod != old.precobaseprod) then
    begin
    
      select coalesce(max(seqlog),0) + 1 from eqprodutolog
      where codemp=new.codemp and codfilial=new.codfilial and codprod=new.codprod
      into :seqlog;

      insert into eqprodutolog(codemp, codfilial, codprod, seqlog, precobaseprodant, precobaseprodnovo)
      values (new.codemp, new.codfilial, new.codprod, coalesce(:seqlog,1), old.precobaseprod, new.precobaseprod);

    end
  END
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQPRODUTOTGBU
as
  DECLARE VARIABLE ICODFILIAL INTEGER;
  DECLARE VARIABLE CESTNEG CHAR(1);
  DECLARE VARIABLE CESTNEGGRUPPREF CHAR(1);
  DECLARE VARIABLE CESTNEGGRUP CHAR(1);
  DECLARE VARIABLE ICODPROD INTEGER;
begin
  IF (new.EMMANUT IS NULL) THEN
     new.EMMANUT='N';
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :iCodFilial;
      SELECT ESTNEG, ESTNEGGRUP FROM SGPREFERE1
         WHERE CODEMP=new.CODEMP AND CODFILIAL=:iCodFilial
         INTO :cEstNeg, :cEstNegGrupPref;
      if (new.SLDPROD is null) then
         new.SLDPROD = 0;
      if (new.SLDRESPROD is null) then
         new.SLDRESPROD = 0;
      if (new.SLDCONSIGPROD is null) then
         new.SLDCONSIGPROD = 0;
      new.SLDLIQPROD = new.SLDPROD-new.SLDRESPROD-new.SLDCONSIGPROD;
      if (cEstNeg Is null) then
          cEstNeg = 'N';
      if (cEstNegGrupPref is null) then
          cEstNegGrupPref = 'N';
      if ((new.SLDPROD < 0) AND (new.TIPOPROD='P') ) THEN
      begin
        if (cEstNegGrupPref = 'S') then
        begin
           SELECT ESTNEGGRUP FROM EQGRUPO
             WHERE CODEMP=new.CODEMPGP AND CODFILIAL=new.CODFILIALGP AND
                CODGRUP=new.CODGRUP INTO :CESTNEGGRUP;
           if (cEstNegGrup is null) then
              cEstNegGrup = 'N';
           if (cEstNegGrup='N') then
              EXCEPTION EQPRODUTOEX01 'O PROD. '||rtrim(cast(new.CODPROD as char(10)))
                ||'-'||substring(new.DESCPROD from 1 for 20)||
              ' NÃO POSSUI SALDO P/COMPL. A OPERAÇÃO';
        end
        else if (cEstNeg='N') then
              EXCEPTION EQPRODUTOEX01 'O PROD. '||rtrim(cast(new.CODPROD as char(10)))
                ||'-'||substring(new.DESCPROD from 1 for 20)||
              ' NÃO POSSUI SALDO P/COMPL. A OPERAÇÃO';
      end
      if ( new.REFPROD != old.REFPROD ) then
      begin
         SELECT FIRST 1 CODPROD FROM EQMOVPROD WHERE CODEMPPD=new.CODEMP AND
            CODFILIALPD=new.CODFILIAL AND CODPROD=new.CODPROD
              INTO :ICODPROD;
         if ( (ICODPROD IS NOT NULL) AND (ICODPROD=new.CODPROD) ) then
            EXCEPTION EQPRODUTOEX01 'REFERÊNCIA NÃO PODE SER ALTERADA. PRODUTO POSSUI MOVIMENTO!';
         else
            UPDATE EQCODALTPROD SET REFPROD=new.REFPROD WHERE CODEMP=new.CODEMP AND
              CODFILIAL=new.CODFILIAL AND CODPROD=new.CODPROD;
      end
  END
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQRECAMOSTRAGEMTGAI
as

    declare variable nroamostprocrecmercval smallint;
    declare variable nroamostprocrecmercatu smallint;

begin

    -- buscando quantidade de amostras necessárias para finalização

    select pr.nroamostprocrecmerc
    from eqprocrecmerc pr, eqitrecmerc ir
    where ir.codemp=new.codemp and ir.codfilial=new.codfilial and ir.ticket=new.ticket and ir.coditrecmerc=new.coditrecmerc
    and pr.codemp=ir.codemptp and pr.codfilial=ir.codfilialtp and pr.codtiporecmerc=ir.codtiporecmerc and pr.codprocrecmerc=ir.codprocrecmerc
    into :nroamostprocrecmercval;

    select count(*) from eqrecamostragem am
    where am.codemp=new.codemp and am.codfilial=new.codfilial and am.ticket=new.ticket and am.coditrecmerc=new.coditrecmerc
    into :nroamostprocrecmercatu;

--    exception vdvendaex01 'nrovalida:' || nroamostprocrecmercval;
--    exception vdvendaex01 'nroatu   :' || nroamostprocrecmercatu;

    -- Atualizando status do recebimento
    -- Primeira pesagem

    if( :nroamostprocrecmercval = :nroamostprocrecmercatu) then
    begin
        
--        exception vdvendaex01 'entrou no update' || nroamostprocrecmercval;

        update eqitrecmerc itr set itr.statusitrecmerc='FN'
        where itr.codemp=new.codemp and itr.codfilial=new.codfilial and itr.ticket=new.ticket
        and itr.coditrecmerc=new.coditrecmerc;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQRECMERCTGAI
as
declare variable codemptp integer;
declare variable codfilialtp smallint;
declare variable codtiporecmerc integer;
declare variable codprocrecmerc integer;
declare variable coditrecmerc integer;

begin

    -- Se tiver um produto padrão no cabeçalho, deve gerar os ítens automaticamente.
    if(new.codprod is not null) then
    begin

        coditrecmerc = 1;
    
        for select pr.codemp, pr.codfilial, pr.codtiporecmerc, pr.codprocrecmerc
        from eqprocrecmerc pr
        where pr.codemp=new.codemptr and pr.codfilial=new.codfilialtr and pr.codtiporecmerc=new.codtiporecmerc
        into :codemptp,  :codfilialtp, :codtiporecmerc, :codprocrecmerc do
        begin

            select coalesce( max(coditrecmerc) , 0 ) + 1
            from eqitrecmerc rm
            where rm.codemp=new.codemp and rm.codfilial=new.codfilial and rm.ticket=new.ticket
            into coditrecmerc;
        
            insert into eqitrecmerc
            ( codemp, codfilial, ticket, coditrecmerc, codemppd, codfilialpd, codprod, refprod, codemptp, codfilialtp, codtiporecmerc, codprocrecmerc ) values
            ( new.codemp, new.codfilial, new.ticket, :coditrecmerc, new.codemppd, new.codfilial, new.codprod, new.refprod, :codemptp, :codfilialtp, :codtiporecmerc, :codprocrecmerc );


        end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER EQRECMERCTGAU
AS
begin
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
    -- Mecanismo de contingência do status dos itens de OS

    if( new.status='PT' ) then
    begin
        update eqitrecmercitos set statusitos = 'CO'
        where codemp=new.codemp and codfilial=new.codfilial and ticket = new.ticket and statusitos!='CO';
    end
 END
end
^

/* Alter exist trigger... */
ALTER TRIGGER EQTIPOMOVTGBI
AS
BEGIN
  IF ( (new.TIPOMOV='IV') AND (new.ESTIPOMOV!='I') ) THEN
     EXCEPTION EQTIPOMOVEX01 'FLUXO INCOMPATÍVEL COM O TIPO DE MOVIMENTO';
  ELSE IF (new.ESTIPOMOV = 'E') THEN
  BEGIN
    IF (new.CODTAB IS NOT NULL) THEN
    BEGIN
      EXCEPTION EQTIPOMOVEX01;
    END
  END
  ELSE
  BEGIN
    IF (new.CODTAB IS NULL) THEN
    BEGIN
      EXCEPTION EQTIPOMOVEX02;
    END
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER EQTIPOMOVTGBU
as
begin
  new.DTALT = cast('now' AS DATE);
  new.IDUSUALT = USER;
  new.HALT = cast('now' AS TIME);
  IF ( (new.TIPOMOV='IV') AND (new.ESTIPOMOV!='I') ) THEN
     EXCEPTION EQTIPOMOVEX01 'FLUXO INCOMPATÍVEL COM O TIPO DE MOVIMENTO';
  ELSE IF (new.ESTIPOMOV = 'E') THEN
  BEGIN
    IF (new.CODTAB IS NOT NULL) THEN
    BEGIN
      EXCEPTION EQTIPOMOVEX01;
    END
  END
  ELSE
  BEGIN
    IF (new.CODTAB IS NULL) THEN
    BEGIN
      EXCEPTION EQTIPOMOVEX02;
    END
  END
  IF (old.ESTOQTIPOMOV != new.ESTOQTIPOMOV) THEN
  BEGIN
    IF ((EXISTS (SELECT CODVENDA FROM VDVENDA WHERE CODTIPOMOV=new.CODTIPOMOV
      AND CODEMPTM=new.CODEMP AND CODFILIALTM=new.CODFILIAL)) OR
        (EXISTS (SELECT CODCOMPRA FROM CPCOMPRA WHERE CODTIPOMOV=new.CODTIPOMOV
      AND CODEMPTM=new.CODEMP AND CODFILIALTM=new.CODFILIAL))) THEN
    BEGIN
      EXCEPTION EQTIPOMOVEX01 'JA EXISTE MOVIMENTOS PARA ESTE TIPO DE MOVIMENTO!';
    END
  END
end
^

/* Alter exist trigger... */
ALTER TRIGGER FNBORDEROTGAU
AS                                   
DECLARE VARIABLE IFILIALLANCA INTEGER;
DECLARE VARIABLE ICODLANCA INTEGER;   
DECLARE VARIABLE NVALOR NUMERIC(15,5); 
DECLARE VARIABLE ICODEMPPLAN INTEGER;
DECLARE VARIABLE IFILIALPLAN INTEGER;
DECLARE VARIABLE SPLANEJAMENTO CHAR(13); 
DECLARE VARIABLE ICODEMPPLANBOR INTEGER;
DECLARE VARIABLE IFILIALPLANBOR INTEGER;
DECLARE VARIABLE SPLANEJAMENTOBOR CHAR(13);
DECLARE VARIABLE ICODSUBLANCA INTEGER;  
begin

  IF (new.STATUSBOR = 'BC') THEN
  BEGIN

    SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO IFILIALLANCA;
    SELECT ISEQ FROM SPGERANUM(new.CODEMP,:IFILIALLANCA,'LA') INTO ICODLANCA;
    SELECT SUM(IR.VLRITREC) FROM FNITRECEBER IR, FNITBORDERO IB
      WHERE
      IR.CODEMP=IB.CODEMPRC AND IR.CODFILIAL=IB.CODFILIALRC AND
      IR.CODREC=IB.CODREC AND IR.NPARCITREC=IB.NPARCITREC AND
      IB.CODEMP=new.CODEMP AND IB.CODFILIAL=new.CODFILIAL AND IB.CODBOR=new.CODBOR
      INTO NVALOR;

    SELECT P.CODEMP, P.CODFILIAL, P.CODPLAN FROM FNPLANEJAMENTO P, FNCONTA C
      WHERE P.CODEMP=C.CODEMPPN AND P.CODFILIAL=C.CODFILIALPN AND P.CODPLAN=C.CODPLAN AND
      C.CODEMP=new.CODEMPCC AND C.CODFILIAL=new.CODFILIALCC AND C.NUMCONTA=new.NUMCONTA
      INTO ICODEMPPLAN, IFILIALPLAN, SPLANEJAMENTO; 

    SELECT P.CODEMP, P.CODFILIAL, P.CODPLAN FROM FNPLANEJAMENTO P, FNCONTA C
      WHERE P.CODEMP=C.CODEMPPN AND P.CODFILIAL=C.CODFILIALPN AND P.CODPLAN=C.CODPLAN AND
      C.CODEMP=new.CODEMPCB AND C.CODFILIAL=new.CODFILIALCB AND C.NUMCONTA=new.NUMCONTABOR
      INTO ICODEMPPLANBOR, IFILIALPLANBOR, SPLANEJAMENTOBOR;

    /* Transferencia */
    INSERT INTO FNLANCA (
      TIPOLANCA,CODEMP,CODFILIAL,CODLANCA,
      CODEMPPN,CODFILIALPN,CODPLAN, DTCOMPLANCA,
      DATALANCA,DOCLANCA,HISTBLANCA,TRANSFLANCA,VLRLANCA)
      VALUES (
      'A',new.CODEMP,:IFILIALLANCA,:ICODLANCA,
      :ICODEMPPLANBOR,:IFILIALPLANBOR,:SPLANEJAMENTOBOR, new.DTBOR,
      new.DTBOR,CAST(new.CODBOR AS CHAR(15)),'ADIANTAMENTO DE RECEBIVÉIS','S',0);       

    ICODSUBLANCA=1;

    INSERT INTO FNSUBLANCA (
      CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,
      CODEMPPN,CODFILIALPN,CODPLAN, DTCOMPSUBLANCA,
      DATASUBLANCA,VLRSUBLANCA,HISTSUBLANCA)
      VALUES (
      new.CODEMP,:IFILIALLANCA,:ICODLANCA,:ICODSUBLANCA,
      :ICODEMPPLAN,:IFILIALPLAN,:SPLANEJAMENTO, new.DTBOR,
      new.DTBOR,:NVALOR,'ADIANTAMENTO DE RECEBIVÉIS');
  END
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

     -- Serve para limpar os flags caso o status do cheque volte para cadastrado
    if(new.sitcheq='CA' and old.sitcheq!='CA') then
    begin
        update fnpagcheq pc
        set pc.baixa='N', pc.transfere='N'
        where pc.codempch=new.codemp and pc.codfilialch=new.codfilial and pc.seqcheq=new.seqcheq;
    end

    -- Caso o status do cheque seja cancelado, deve eliminar os vinculos iniciais
    if(new.sitcheq='CN' and old.sitcheq!='CN') then
    begin
        delete from fnpagcheq pc
        where pc.codempch=new.codemp and pc.codfilialch=new.codfilial and pc.seqcheq=new.seqcheq;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNITPAGARTGBU
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
          FOR SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG AND
                CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA
          DO BEGIN

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
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag
                              ,new.codempct, new.codfilialct, new.codcontr, new.coditcontr);
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

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGAU
AS
  DECLARE VARIABLE ICODCLI INTEGER;
  DECLARE VARIABLE ICODEMPCL INTEGER;
  DECLARE VARIABLE ICODFILIALCL INTEGER;
  DECLARE VARIABLE SCODFILIALCI SMALLINT;
  DECLARE VARIABLE CODFILIALLC SMALLINT;
  DECLARE VARIABLE CODEMPLC INTEGER;
  DECLARE VARIABLE CODLANCA INTEGER;
  DECLARE VARIABLE VLRLANCA NUMERIC(15,5);
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
  DECLARE VARIABLE PERMITBAIXAPARCJDM CHAR(1);
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ESTITRECALTDTVENC, PERMITBAIXAPARCJDM FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC, :PERMITBAIXAPARCJDM;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;
     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='R1') ) OR
           ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='RR') ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNCOMISSAO') INTO :SCODFILIALCI;
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :CODFILIALLC;
       
       execute procedure fnestornacomissaosp new.codemp, new.codfilial, new.codrec, new.nparcitrec;

       --UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
--              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
--              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
--              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
       
       
              
       IF ( (old.MULTIBAIXA IS NULL) OR (old.MULTIBAIXA='N') ) THEN
       BEGIN        
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
          DELETE FROM FNLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
       END
       ELSE 
       BEGIN
          SELECT CODEMP, CODFILIAL, CODLANCA FROM FNSUBLANCA
             WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
                CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL 
             INTO :CODEMPLC, :CODFILIALLC, :CODLANCA;
          DELETE FROM FNSUBLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC AND 
             CODEMPRC=new.CODEMP AND CODFILIALRC = new.CODFILIAL;
          SELECT VLRLANCA FROM FNLANCA
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA
             INTO :VLRLANCA;
          IF (:VLRLANCA=0) THEN 
          BEGIN
             DELETE FROM FNLANCA 
             WHERE CODEMP=:CODEMPLC AND CODFILIAL=:CODFILIALLC AND CODLANCA=:CODLANCA;
          END 
       END
     END
     ELSE IF ((old.STATUSITREC='R1' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC='RR' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC in ('RP','RL') AND new.STATUSITREC in ('RP','RL') AND new.VLRPAGOITREC > 0) OR
              (old.STATUSITREC = 'RB' AND new.STATUSITREC = 'RP')) THEN
     BEGIN
        SELECT CODCLI,CODEMPCL,CODFILIALCL FROM FNRECEBER WHERE CODEMP=new.CODEMP AND
           CODFILIAL=new.CODFILIAL AND CODREC=new.CODREC
           INTO ICODCLI,ICODEMPCL,ICODFILIALCL;
        IF ((new.VLRPAGOITREC-old.VLRPAGOITREC) > 0) THEN
        BEGIN
          IF(new.multibaixa is null or new.multibaixa = 'N')THEN
          BEGIN
             EXECUTE PROCEDURE FNADICLANCASP01(new.CodRec,new.NParcItRec,new.PDVITREC,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODCLI,:ICODEMPCL,:ICODFILIALCL,
                        new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.ANOCC,new.CODCC,new.CODEMPCC,new.CODFILIALCC, new.dtCompItRec, new.DtPagoItRec, 
                        new.DocLancaItRec, SUBSTRING(new.ObsItRec FROM 1 FOR 50),new.VlrPagoItRec-old.VlrPagoItRec,new.CODEMP,new.CODFILIAL,new.vlrjurositrec,new.vlrdescitrec
                        ,new.codempct, new.codfilialct, new.codcontr, new.coditcontr);
          END
        END
        IF (new.STATUSITREC = 'RP') THEN
        BEGIN
            UPDATE VDCOMISSAO SET STATUSCOMI='C2'
               WHERE CODREC=old.CODREC
               AND CODEMPRC=old.CODEMP
               AND CODFILIALRC=old.CODFILIAL
               AND NPARCITREC=old.NPARCITREC
               AND NOT STATUSCOMI IN ('CP','C2')
               AND CODEMP=old.CODEMP;
        END
     END
     ELSE IF (old.VLRCOMIITREC != new.VLRCOMIITREC) THEN
     BEGIN
        EXECUTE PROCEDURE vdgeracomissaosp(new.CODEMP, new.CODFILIAL, new.CODREC,
           new.NPARCITREC, new.VLRCOMIITREC, new.DTVENCITREC);
     END

     IF ( (new.ALTUSUITREC='S') AND ( (old.VLRITREC!=new.VLRITREC) OR
          (old.VLRDESCITREC!=new.VLRDESCITREC) OR (old.VLRMULTAITREC!=new.VLRMULTAITREC) OR
          (old.VLRJUROSITREC!=new.VLRJUROSITREC) OR (old.VLRPARCITREC!=new.VLRPARCITREC) OR
          (old.VLRPAGOITREC!=new.VLRPAGOITREC) OR (old.VLRAPAGITREC!=new.VLRAPAGITREC) ) ) THEN
        UPDATE FNRECEBER SET VLRREC = VLRREC - old.VLRITREC + new.VLRITREC,
            VLRDESCREC = VLRDESCREC - old.VLRDESCITREC + new.VLRDESCITREC,
            VLRMULTAREC = VLRMULTAREC - old.VLRMULTAITREC + new.VLRMULTAITREC,
            VLRJUROSREC = VLRJUROSREC - old.VLRJUROSITREC + new.VLRJUROSITREC,
            VLRDEVREC = VLRDEVREC - old.VLRDEVITREC + new.VLRDEVITREC,
            VLRPARCREC = VLRPARCREC - old.VLRPARCITREC + new.VLRPARCITREC,
            VLRPAGOREC = VLRPAGOREC - old.VLRPAGOITREC + new.VLRPAGOITREC,
            ALTUSUREC = 'S' WHERE CODREC=new.CODREC
           AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
      /* Condição para evitar baixa parcial de títulos com juros, descontos ou multas. */
      IF ( (:PERMITBAIXAPARCJDM='N') AND (new.STATUSITREC='RL') AND ( (new.VLRDESCITREC<>0) OR (new.VLRJUROSITREC<>0) OR (new.VLRMULTAITREC<>0) ) ) THEN
         EXCEPTION FNITRECEBEREX03; 
   END
   
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGBI
as

    declare variable seqnossonumero int;

begin

    --Setando valor padrão de campos para 0;
    if (new.vlrparcitrec is null) then
        new.vlrparcitrec = 0;

    if (new.vlrdescitrec is null) then
        new.vlrdescitrec = 0;

    if (new.vlrjurositrec is null) then
        new.vlrjurositrec = 0;

    if (new.vlrmultaitrec is null) then
        new.vlrmultaitrec = 0;

    -- Calculando o valor liquido da parcela...
    new.vlritrec = new.vlrparcitrec - new.vlrdescitrec - new.vlrdevitrec + new.vlrjurositrec + new.vlrmultaitrec;
    new.vlrapagitrec = new.vlritrec - new.vlrpagoitrec;

    -- Zerando valores caso fique negativo...
    if (new.vlrapagitrec<0) then
        new.vlrapagitrec = 0;

    if(new.dtprevitrec is null) then
        new.dtprevitrec = new.dtvencitrec;

   -- Buscando informações da conta (necessário quando é alterado o plano de pagamento na venda)
    if(new.numconta is null) then
    begin
    
        select vd.codempca, vd.codfilialca, vd.numconta
        from fnreceber rc, vdvenda vd
        where
        rc.codemp=new.codemp and rc.codfilial=new.codfilial and rc.codrec=new.codrec and
        vd.codemp=rc.codempva and vd.codfilial=rc.codfilialva and vd.codvenda=rc.codvenda and vd.tipovenda=rc.tipovenda
        into new.codempca, new.codfilialca, new.numconta;

    end

    --Buscando sequencial caso informações de banco e carteira já tenham sido informadas...

    if(new.codbanco is not null and new.codcartcob is not null and new.numconta is not null) then
    begin

       select seqnossonumero
       from fngeraseqnossonumero( new.codempbo, new.codfilialbo, new.codbanco, new.codempcb, new.codfilialcb, new.codcartcob, new.codempca, new.codfilialca, new.numconta)
       into :seqnossonumero;

       if (:seqnossonumero is not null and :seqnossonumero >0 ) then
       begin
          new.seqnossonumero = :seqnossonumero;
       end

    end



end
^

/* Alter exist trigger... */
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
     if (new.VLRAPAGITREC < 0 or new.VLRAPAGITREC is null ) then /* se o valor a pagar for menor que zero */
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

/* Alter exist trigger... */
ALTER TRIGGER FNPAGARTGAI
as
declare variable vlrpag numeric(15, 5);
declare variable percpag numeric(11,6);
declare variable resto numeric(15, 5);
declare variable numparc integer;
declare variable diaspag integer;
declare variable i integer;
declare variable filialpp integer;
declare variable regrvcto char(1);
declare variable rvsab char(1);
declare variable rvdom char(1);
declare variable rvfer char(1);
declare variable rvdia char(1);
declare variable diavcto smallint;
declare variable casasdecfin smallint;
declare variable dtvencto date;

begin

    -- Buscando filial do plano de pagamento
    select icodfilial from sgretfilial(new.codemp, 'FNPLANOPAG') into :filialpp;

    -- Buscando preferencias

    select casasdecfin from sgprefere1 where codemp=new.codemp and codfilial=new.codfilial
    into casasdecfin;

    -- Carregando valor restante de pagamento
    i = 0;
    resto = new.vlrpag;

    -- Buscando informações do plano de pagamento
    select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag,
           pp.rvferplanopag, pp.rvdiaplanopag, pp.diavctoplanopag
    from fnplanopag pp
    where pp.codplanopag=new.codplanopag and pp.codemp=new.codemp and pp.codfilial=:filialpp
    into :numparc, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

    -- Gerando parcelas

    for select pp.percpag, pp.diaspag
        from fnparcpag pp
        where pp.codplanopag=new.codplanopag and pp.codemp=new.codemp and pp.codfilial=:filialpp
        order by pp.diaspag
        into :percpag, :diaspag
    do
    begin
        i = i + 1;
        
        -- Calculando valor da parcela
        vlrpag = cast(cast(new.vlrpag * percpag as numeric(15, 5)) / 100 as numeric(15, 5));

        -- Usando o arredondamento para evitar dízima
        select v.deretorno
        from arreddouble(:vlrpag,:casasdecfin) v
        into :vlrpag;

        -- Valor restante para proximas parcelas
        resto = resto - vlrpag;

        if (i = numparc) then
        begin
            vlrpag = vlrpag + resto;
        end
        
        -- Buscando data de vencimento
        select c.dtvencret
        from sgcalcvencsp( new.codemp , new.datapag, new.datapag + :diaspag, :regrvcto,
                           :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :diaspag ) c
        into :dtvencto;

        -- Inserindo parcela

        insert into fnitpagar (CODEMP,CODFILIAL,CODPAG,NPARCPAG,VLRITPAG,VLRDESCITPAG,VLRMULTAITPAG,
                           VLRJUROSITPAG,VLRPARCITPAG,VLRPAGOITPAG,VLRAPAGITPAG,
                           DTITPAG, DTCOMPITPAG, DTVENCITPAG,STATUSITPAG,VLRADICITPAG,FLAG,OBSITPAG,
                           CODEMPTC,CODFILIALTC,CODTIPOCOB,CODEMPCA,CODFILIALCA,NUMCONTA,
                           CODEMPPN, CODFILIALPN, CODPLAN, CODEMPCC, CODFILIALCC, ANOCC, CODCC
                           ) VALUES
                           (new.CODEMP,new.CODFILIAL,new.CODPAG,:i,:vlrpag,0,0,0,:vlrpag,0,:vlrpag,
                            new.datapag, new.DTCOMPPAG, :dtvencto,'P1',new.vlradicpag,new.flag,new.obspag,
                            new.CODEMPTC,new.CODFILIALTC,new.CODTIPOCOB, new.codempca, new.codfilialca, new.numconta,
                            new.codemppn, new.codfilialpn, new.codplan, new.codempcc, new.codfilialcc, new.anocc, new.codcc
                            );
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNPAGCHEQTGAI
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
    declare variable dtcompitpag date;
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
    declare variable histblanca varchar(250);
    declare variable snumcheque varchar(15);
    declare variable sdoccompra varchar(50);
    declare variable nomefavcheq varchar(50);

begin

    -- Busca informações do cheque
    select ch.tipocheq, ch.contacheq, ch.dtvenctocheq, ch.vlrcheq, ch.numcheq, ch.dtcompcheq, ch.nomefavcheq
    from fncheque ch
    where ch.codemp=new.codempch and ch.codfilial=new.codfilialch and ch.seqcheq=new.seqcheq
    into :tipocheq, :contacheq, :dtvenctocheq, :vlrcheq, :numcheq, :dtcompcheq, :nomefavcheq;

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
            select ip.vlrapagitpag, coalesce(ip.codemppn,p1.codemppc), coalesce(ip.codfilialpn,p1.codfilialpc),
            coalesce(ip.codplan,p1.codplanpc), coalesce(ip.obsitpag,'')
            from fnitpagar ip, fnpagar pg, sgprefere1 p1
            where ip.codemp=new.codemp and ip.codfilial=new.codfilial and ip.codpag=new.codpag and ip.nparcpag=new.nparcpag
            and p1.codemp=new.codemp and p1.codfilial=new.codfilial
            and pg.codemp=ip.codemp and pg.codfilial=ip.codfilial and pg.codpag=ip.codpag
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
            ip.statusitpag=:statusitpagar, obsitpag=:obsitpag, multibaixa='N'
            where ip.codpag=new.codpag and ip.nparcpag=new.nparcpag and ip.codemp=new.codemp and ip.codfilial=new.codfilial;
        end
   end

    -- Transferencia de baixa na compensação do cheque
    if(coalesce(old.transfere,'N')='N' and new.transfere='S') then
    begin
        icont = 0;
        for
            select
            la.codemp, la.codfilial, la.codemppn, la.codfilialpn, la.codplan, la.vlrlanca, pg.dtcompitpag, coalesce(p.docpag,'') || '/' || coalesce(pg.nparcpag,'')
            from fnlanca la, fnitpagar pg, fnpagcheq pc, fnpagar p
            where pc.codemp=pg.codemp and pc.codfilial=pg.codfilial and pc.codpag=pg.codpag and pc.nparcpag=pg.nparcpag and
            la.codemppg=pg.codemp and la.codfilialpg=pg.codfilial and la.codpag=pg.codpag and la.nparcpag=pg.nparcpag
            and pc.codemp=new.codemp and pc.codfilial=new.codfilial and pc.codpag=new.codpag and pc.nparcpag=new.nparcpag
            and p.codemp=pg.codemp and p.codfilial=pg.codfilial and p.codpag=pg.codpag
            into :codempla, :codfilialla, :codemppnorig, :codfilialpnorig, :codplanorig, :vlrlanca, :dtcompitpag, :sdoccompra


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

            -- Montando histórico...
            snumcheque = rtrim(coalesce(cast(:numcheq as char(15)),'0'));
            histblanca = 'CH CP [' || :snumcheque || '] NF ' || :sdoccompra || ' ' || coalesce(:nomefavcheq,'');

            -- Inserir lançamento na primeira passada....

            if(:icont=1) then
            begin

                -- Inserindo lançamento de transferência...
                insert into fnlanca (
                    tipolanca, codemp, codfilial, codlanca,
                    codemppn, codfilialpn, codplan, dtcomplanca,
                    datalanca, doclanca, histblanca, transflanca, vlrlanca )
                values (
                      'A', :codempla, :codfilialla, :codlancatransf,
                  :codemppntransf, :codfilialpntransf, :codplantransf, :dtcompitpag, 
                  :dtcompcheq, :snumcheque, :histblanca, 'S', 0);

            end

            -- Inserindo sub-lançamento de transferência...

            insert into fnsublanca (
                codemp, codfilial, codlanca, codsublanca,
                codemppn, codfilialpn, codplan, dtcompsublanca,
                datasublanca, vlrsublanca, histsublanca)
            values (
                :codempla, :codfilialla, :codlancatransf, :icont,
                :codemppnorig, :codfilialpnorig, :codplanorig, :dtcompitpag,
                :dtcompcheq, :vlrlanca * -1 , :histblanca );

        end

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER FNPLANEJAMENTOTGBI
AS
declare variable codredplan Integer;
begin

    -- Se é de nível analítico deve gerar código reduzido
    if (new.nivelplan=6 and new.codredplan is null) then
    begin

        select coalesce(max(codredplan),0) from fnplanejamento where codemp=new.codemp and codfilial = new.codfilial
        into :codredplan;

        new.codredplan = codredplan + 1;

     end
end
^

/* Alter exist trigger... */
ALTER TRIGGER FNPLANEJAMENTOTGBU
as
declare variable codredplan integer;
begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);
     -- Se é de nível analítico deve gerar código reduzido
    if (new.nivelplan=6 and new.codredplan is null) then
    begin
        select coalesce(max(codredplan),0) from fnplanejamento where codemp=new.codemp and codfilial = new.codfilial
        into :codredplan;
        if (codredplan is null) then
        begin
           codredplan = 0;
        end
        new.codredplan = codredplan + 1;
    end
    if ( (old.codredplan is null and new.codredplan is not null) or (new.codredplan <> old.codredplan) ) then
    begin
        codredplan = null;
        select first 1 codredplan from fnplanejamento where codemp=new.codemp and codfilial=new.codfilial and codplan<>old.codplan and codredplan=new.codredplan
        into :codredplan;
        if(:codredplan is not null) then
        begin
            exception fnplanejamento01;
        end
     end
end
^

/* Alter exist trigger... */
ALTER TRIGGER FNRECEBERTGAI
AS
  DECLARE VARIABLE NPARCREC INTEGER;
  DECLARE VARIABLE CODFILIALPP SMALLINT;
  DECLARE VARIABLE CODFILIALPN SMALLINT;
  DECLARE VARIABLE CODPLAN CHAR(13);
  DECLARE VARIABLE CODCC CHAR(19);
  DECLARE VARIABLE ANOCC SMALLINT;
  DECLARE VARIABLE CODFILIALCC SMALLINT;
  DECLARE VARIABLE CODFILIALIR SMALLINT;
  DECLARE VARIABLE CODFILIALCL SMALLINT;
  DECLARE VARIABLE ATIVOCLI CHAR(1);
  DECLARE VARIABLE NUMRESTR INTEGER;
  DECLARE VARIABLE CODFILIALCA SMALLINT;
  DECLARE VARIABLE CODEMPCA INTEGER;
  DECLARE VARIABLE NUMCONTA CHAR(10);
  declare variable docvenda integer;
  declare variable serievenda char(4);

BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'VDCLIENTE') INTO CODFILIALCL;
  SELECT C.ATIVOCLI  FROM VDCLIENTE C WHERE C.CODEMP=NEW.CODEMP AND
     C.CODFILIAL=:CODFILIALCL AND C.CODCLI=NEW.CODCLI INTO :ATIVOCLI;
  IF (ATIVOCLI<>'S') THEN
  BEGIN
     EXCEPTION FNRECEBEREX01;
  END
  SELECT COUNT(*) FROM FNRESTRICAO R, FNTIPORESTR TR
    WHERE R.CODEMP=NEW.CODEMP AND R.CODFILIAL=:CODFILIALCL AND
      R.CODCLI=NEW.CODCLI AND R.SITRESTR IN ('I') AND
      TR.CODEMP=R.CODEMPTR AND TR.CODFILIAL=R.CODFILIALTR AND
      TR.CODTPRESTR=R.CODTPRESTR AND TR.BLOQTPRESTR='S'
      INTO :NUMRESTR;
  IF ( (NUMRESTR IS NOT NULL) AND (NUMRESTR>0) ) THEN
  BEGIN
     IF (NUMRESTR=1) THEN
     BEGIN
        EXCEPTION FNRECEBEREX02 'Cliente possui '||RTRIM(CAST(NUMRESTR AS CHAR(10)))||' restrição!';
     END
     ELSE
     BEGIN
        EXCEPTION FNRECEBEREX02 'Cliente possui '||RTRIM(CAST(NUMRESTR AS CHAR(10)))||' restrições!';
     END
  END
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNPLANOPAG') INTO CODFILIALPP;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNPLANEJAMENTO') INTO CODFILIALPN;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNCENTROCUSTO') INTO CODFILIALCC;
  SELECT ICODFILIAL FROM SGRETFILIAL(NEW.CODEMP,'FNITRECEBER') INTO CODFILIALIR;

  if(new.codvenda is not null and new.numconta is null) then
  begin
    select codempca,codfilialca,numconta, vd.docvenda, vd.serie from vdvenda vd
    where codemp=new.codempva and codfilial=new.codfilialva and codvenda=new.codvenda and tipovenda=new.tipovenda
    into :codempca, :codfilialca, :numconta, :docvenda, :serievenda;
  end
  else
  begin
    codempca = new.codempca;
    codfilialca = new.codfilialca;
    numconta = new.numconta;
  end

  if (new.codvenda is not null and :docvenda is null) then
  begin
    select vd.docvenda, vd.serie from vdvenda vd
    where codemp=new.codempva and codfilial=new.codfilialva and codvenda=new.codvenda and tipovenda=new.tipovenda
    into :docvenda, :serievenda;
  end
  
  SELECT I FROM fngeraitrecebersp01('S',new.CODEMP,
    new.CODFILIAL, new.CODREC, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
    new.VLRREC, new.DATAREC, new.DTCOMPREC, new.FLAG, new.CODEMPBO, new.CODFILIALBO,
    new.CODBANCO, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
    new.CODEMPCB, new.CODFILIALCB, new.CODCARTCOB,
    new.VLRCOMIREC, new.OBSREC, :codempca, :codfilialca, :numconta,
    new.codemppn, new.codfilialpn, new.codplan, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.vlrbasecomis
    ) INTO :NPARCREC;

/* Se a parcela do plano de pagamento tiver marcado para autobaixa, então é realizada a baixa automática. */
  FOR SELECT PP.NUMCONTA,PP.CODFILIALCA,PP.CODEMPCA, PP.CODPLAN,PP.CODFILIALPN,PP.CODCC,PP.ANOCC,PP.CODFILIALCC,PC.NROPARCPAG
      FROM FNPARCPAG PC, FNPLANOPAG PP
      WHERE
        PP.CODEMP=NEW.CODEMP AND PP.CODFILIAL=:CODFILIALPP AND PP.CODPLANOPAG=NEW.CODPLANOPAG AND
        PC.CODEMP=PP.CODEMP AND PC.CODFILIAL=PP.CODFILIAL AND PC.CODPLANOPAG=PP.CODPLANOPAG AND
        PC.AUTOBAIXAPARC='S'
      INTO NUMCONTA,CODFILIALCA,CODEMPCA, CODPLAN,CODFILIALPN,CODCC,ANOCC,CODFILIALCC,NPARCREC
  DO
  BEGIN
    UPDATE FNITRECEBER SET
      NUMCONTA=:NUMCONTA,
      CODEMPCA=:CODEMPCA,
      CODFILIALCA=:CODFILIALCA,
      -- NUMCONTA=:NUMCONTA,
      CODPLAN=:CODPLAN,
      CODEMPPN=NEW.CODEMP,
      CODFILIALPN=:CODFILIALPN,
      ANOCC=:ANOCC,
      CODCC=:CODCC,
      CODEMPCC=NEW.CODEMP,
      CODFILIALCC=:CODFILIALCC,
      DOCLANCAITREC= :docvenda || '/' || :nparcrec,
--      DOCLANCAITREC='AUTO',
      DTPAGOITREC=NEW.DATAREC,
      VLRPAGOITREC=VLRPARCITREC - coalesce(vlrdescitrec,0),
--      VLRDESCITREC=0,
      VLRJUROSITREC=0,
      OBSITREC='BAIXA AUTOMÁTICA' || ' REF. DOC: ' || :serievenda || '-' || :docvenda,
      STATUSITREC='RP'
      WHERE CODREC=NEW.CODREC AND CODEMP=NEW.CODEMP AND CODFILIAL=:CODFILIALIR AND
      NPARCITREC=:NPARCREC;
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNRECEBERTGAU
as
    declare variable inparcold integer;
    declare variable inparcnew integer;
    declare variable caltvlr char(1);
    declare variable regrvcto char(1);
    declare variable rvsab char(1);
    declare variable rvdom char(1);
    declare variable rvfer char(1);
    declare variable rvdia char(1);
    declare variable diavcto smallint;
    declare variable diaspag smallint;
    declare variable dtvencto date;
    declare variable nroparcrec smallint;

    begin
      if ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) then
      begin

        -- Se houver alterações relevantes...
        if ( (
                (old.codplanopag<>new.codplanopag) or
                (old.flag<>new.flag) or
                (old.vlrrec<>new.vlrrec) or
                (old.datarec<>new.datarec) or
                (coalesce(old.obsrec,'')<>coalesce(new.obsrec,''))
            )

            and (new.altusurec != 'S')

        ) then

        begin

            -- Se foi alterado o plano de pagamento            
            if (old.codplanopag <> new.codplanopag) then
            begin

                -- Buscando informações do plano de pagamento antigo
                select pp.parcplanopag from fnplanopag pp
                where codemp=old.codemppg and pp.codfilial=old.codfilialpg and pp.codplanopag=old.codplanopag
                into :inparcold;

                -- Buscando informações do novo plano de pagamento
                select pp.parcplanopag, pp.regrvctoplanopag, pp.rvsabplanopag, pp.rvdomplanopag,
                pp.rvferplanopag, pp.rvdiaplanopag, pp.diavctoplanopag
                from fnplanopag pp
                where pp.codemp=new.codemppg and pp.codfilial=new.codfilialpg and pp.codplanopag = new.codplanopag
                into :inparcnew, :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto;

                -- Se o numero de parcelas entre os planos de pagamento são diferentes.
                if (inparcnew != inparcold) then
                begin
                    -- exclui os itens de contas a receber, para recria-los.
                    delete from fnitreceber where codrec=new.codrec and codemp=new.codemp and codfilial=new.codfilial;
                end
                -- Caso possua apenas uma parcela deve apenas atualizar a data do vencimento.
                else if (inparcnew=1) then
                begin
                    -- Buscando informação do item do plano de pagamento
                    select diaspag from fnparcpag
                    where codplanopag=new.codplanopag and codemp=new.codemppg and codfilial=new.codfilialpg
                    into :diaspag;

                    -- Calculando o vencimento da parcela.
                    select c.dtvencret from sgcalcvencsp( new.codemppg, new.datarec, new.datarec + :diaspag,
                    :regrvcto, :rvsab, :rvdom, :rvfer, :rvdia, :diavcto, :diaspag ) c
                    into :dtvencto;

                    -- Atualizando item do contas a receber
                    update fnitreceber set dtvencitrec=:dtvencto
                    where codemp=new.codemp and codfilial=new.codfilial and codrec=new.codrec and
                    statusitrec not in ('RP','RL');

                end
            end

            -- Se o valor foi alterado
            if (new.vlrrec!=old.vlrrec) then
                caltvlr = 'S';
            else
                caltvlr = 'N';

            select i from fngeraitrecebersp01(:caltvlr,  new.codemp, new.codfilial, new.codrec, new.codemppg,
            new.codfilialpg, new.codplanopag, new.vlrrec, new.datarec, new.dtcomprec, new.flag, new.codempbo, new.codfilialbo,
            new.codbanco, new.codemptc, new.codfilialtc, new.codtipocob, new.codempcb, new.codfilialcb, new.codcartcob,
            new.vlrcomirec, new.obsrec, null, null, null, new.codemppn, new.codfilialpn,new.codplan, new.codempcc,
            new.codfilialcc, new.anocc, new.codcc, new.vlrbasecomis)
            into :nroparcrec;

        end

        -- Atualiza os itens para eles atualizarem a comissao de forma distribuida.
        if (old.vlrcomirec != new.vlrcomirec) then
        begin
            execute procedure fnitrecebersp01(new.codemp, new.codfilial, new.codrec, new.vlrparcrec,
            new.vlrcomirec, new.nroparcrec, 'N');
        end
      end
    end
^

/* Alter exist trigger... */
ALTER TRIGGER FNSALDOLANCATGAI
AS
    declare variable fechacaixa char(1);
    declare variable fechacaixaauto char(1);
begin

    -- buscando preferências para fechamento de caixas

    select coalesce(p1.fechacaixa,'N'),  coalesce(p1.fechacaixaauto,'N') from sgprefere1 p1
    where p1.codemp=new.codemp and p1.codfilial=new.codfilial
    into :fechacaixa, :fechacaixaauto;

    -- carregando valores para saldo...
    -- if (new.SALDOSL is null) then
    --     new.SALDOSL = 0;
    -- if (new.PREVSL is null) then
    --     new.PREVSL = 0;

    -- Fechando todos os caixas anteriors...

    if('S' = :fechacaixa and 'S' = :fechacaixaauto) then
    begin
        update fnsaldolanca sl
        set sl.fechado='S'
        where coalesce(sl.fechado,'N')='N'
        and sl.codemp=new.codemp and sl.codfilial=new.codfilial
        and sl.codemppn=new.codemppn and sl.codfilialpn=new.codfilialpn
        and sl.codplan=new.codplan
        and sl.datasl < new.datasl;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER FNSALDOLANCATGBU
as
    declare variable fechacaixa char(1);
 --   declare variable fechacaixaauto char(1);
    declare variable fechado char(1);
    declare variable numconta char(10);

begin

    -- buscando preferências para fechamento de caixas

    select coalesce(p1.fechacaixa,'N') from sgprefere1 p1
    where p1.codemp=new.codemp and p1.codfilial=new.codfilial
    into :fechacaixa;

    -- Verifica se a conta é de caixa, pois deve bloquear apenas contas caixa...
    select ct.numconta from fnconta ct
    where ct.codemppn=new.codemppn and ct.codfilialpn=new.codfilialpn
    and ct.codplan=new.codplan
    into :numconta;

    -- Verifica se deve verificar o fechamento de caixas...
    if('S' = :fechacaixa and :numconta is not null) then
    begin

        if(new.fechado is null) then
        begin
           new.fechado = 'N';
        end

        -- Verifica se o caixa está fechado antes de permitir a atualização

        if( new.fechado='N' ) then
        begin
            --Verifica se os caixas futuros estão fechados.

            select first 1 sl.fechado from fnsaldolanca sl
            where
            sl.codemp=new.codemp and sl.codfilial=new.codfilial
            and sl.codemppn=new.codemppn and sl.codfilialpn=new.codfilialpn and sl.codplan=new.codplan
            and coalesce(sl.fechado,'N')='S' and sl.datasl>new.datasl
            into :fechado;

            if( (new.fechado='S' or :fechado='S' ) and old.saldosl<>new.saldosl) then
            begin
                exception FNSALDOLANCAEX01;
            end
        end
        else if(old.saldosl<>new.saldosl) then
        begin
            exception FNSALDOLANCAEX01;
        end
    end

    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

end
^

/* Alter exist trigger... */
ALTER TRIGGER FNSUBLANCATGAU
AS
DECLARE VARIABLE dDataSl DATE;
DECLARE VARIABLE dSaldoSl NUMERIC(15, 5);
DECLARE VARIABLE sCodplan CHAR(13);
DECLARE VARIABLE dSaldoAnt NUMERIC(15, 5);
DECLARE VARIABLE IFILIALSALDO INTEGER;
declare variable codfilialpn smallint;
BEGIN

   IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
   BEGIN

      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNSALDOLANCA') INTO IFILIALSALDO;
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPLANEJAMENTO') INTO :codfilialpn;

      IF ( (old.DATASUBLANCA <> new.DATASUBLANCA) OR (old.VLRSUBLANCA<>new.VLRSUBLANCA) ) THEN
      BEGIN
   /*Verifica se já existe saldo no dia da nova data:*/
        SELECT CODPLAN,DATASL,SALDOSL FROM FNSALDOLANCA WHERE CODPLAN=new.CODPLAN
               AND DATASL=new.DATASUBLANCA AND CODEMP=new.CODEMPPN AND CODFILIAL=:IFILIALSALDO
               INTO :sCodplan,:dDataSl,:dSaldoSl;
   /*Se não existir, cria um saldo para o nova data:*/
        IF (sCodplan IS NULL ) THEN
        BEGIN
   /*Pega o ultimo saldo anterior a data do lançamento:*/
           SELECT SALDOSL FROM FNSALDOLANCA SL1 WHERE SL1.CODPLAN=new.CODPLAN and sl1.codemppn=new.codemp and sl1.codfilialpn=:codfilialpn
                  AND SL1.DATASL=(
                                 SELECT MAX(SL2.DATASL) FROM FNSALDOLANCA SL2 WHERE
                                        SL2.CODPLAN=SL1.CODPLAN AND SL2.DATASL<new.DATASUBLANCA
                                        AND SL2.CODEMP=new.CODEMPPN AND SL2.CODFILIAL=:IFILIALSALDO
                                 )
                  AND SL1.CODEMP=new.CODEMPPN AND SL1.CODFILIAL=:IFILIALSALDO INTO :dSaldoAnt;
       /*Cria um saldo para este dia e atualiza saldos possíveis que tiverem na frente:*/
           IF (:dSaldoAnt IS NULL) THEN
              dSaldoAnt = 0;
           INSERT INTO FNSALDOLANCA (CODEMP,CODFILIAL,CODPLAN,CODEMPPN,CODFILIALPN,DATASL,SALDOSL)
                  VALUES (new.CODEMP,:IFILIALSALDO,new.CODPLAN,new.CODEMPPN,new.CODFILIALPN,new.DATASUBLANCA,:dSaldoAnt + new.VLRSUBLANCA);
           UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
                  WHERE CODPLAN=new.CODPLAN AND DATASL>new.DATASUBLANCA
                  AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;
        END
       /*Caso contrário só atualiza o saldo:*/
        ELSE
        BEGIN
           UPDATE FNSALDOLANCA SET SALDOSL = SALDOSL + new.VLRSUBLANCA
                  WHERE CODPLAN=new.CODPLAN AND DATASL>=new.DATASUBLANCA
                  AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALSALDO;
        END
      END
   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER LFFRETETGAD
AS
begin

    -- Atualiza status da expedição ao excluir frete
    if(old.ticketex is not null) then
    begin
        update eqexpedicao ex set ex.status='RE' where ex.codemp=old.codempex and ex.codfilial=old.codfilialex and ex.ticket=old.ticketex;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFFRETETGAI
AS
begin

    -- Atualiza status da expedição ao inserir frete
    if(new.ticketex is not null) then
    begin
        update eqexpedicao ex set ex.status='CE' where ex.codemp=new.codempex and ex.codfilial=new.codfilialex and ex.ticket=new.ticketex;
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITCOMPRATGAD
AS

    declare variable ovlrbasepis numeric(15, 5);
    declare variable ovlrbasecofins numeric(15, 5);
    declare variable ovlrpis numeric(15, 5);
    declare variable ovlrcofins numeric(15, 5);
    declare variable vlricmsdiferido numeric(15, 5);
    declare variable vlricmsdevido numeric(15, 5);
    declare variable vlricmscredpresum numeric(15, 5);

begin
    -- Inicialização de variáveis (old)
    if (old.vlrbasepis is null) then ovlrbasepis = 0; else ovlrbasepis = old.vlrbasepis;
    if (old.vlrbasecofins is null) then ovlrbasecofins = 0; else ovlrbasecofins = old.vlrbasepis;
    if (old.vlrpis is null) then ovlrpis = 0; else ovlrpis = old.vlrpis;
    if (old.vlrcofins is null) then ovlrcofins = 0; else ovlrcofins = old.vlrcofins;
    if (old.vlricmsdiferido is null) then vlricmsdiferido = 0; else vlricmsdiferido = old.vlricmsdiferido;
    if (old.vlricmsdevido is null) then vlricmsdevido = 0; else vlricmsdevido = old.vlricmsdevido;
    if (old.vlricmscredpresum is null) then vlricmscredpresum = 0; else vlricmscredpresum = old.vlricmscredpresum;

    update cpcompra cp set
        cp.vlrbasepiscompra = cp.vlrbasepiscompra - :ovlrbasepis,
        cp.vlrpiscompra = cp.vlrpiscompra - :ovlrpis,
        cp.vlrbasecofinscompra = cp.vlrbasecofinscompra - :ovlrbasecofins ,
        cp.vlrcofinscompra = cp.vlrcofinscompra - :ovlrcofins,
        cp.vlrbaseiicompra = cp.vlrbaseiicompra - old.vlrbaseii,
        cp.vlriicompra = cp.vlriicompra - old.vlrii,
        cp.vlricmsdiferido = cp.vlricmsdiferido - :vlricmsdiferido,
        cp.vlricmsdevido = cp.vlricmsdevido - :vlricmsdevido,
        cp.vlricmscredpresum = cp.vlricmscredpresum - :vlricmscredpresum
    where codcompra=old.codcompra and codemp=old.codemp and codfilial=old.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITCOMPRATGAI
as
    declare variable vlrbasepis numeric(15, 5);
    declare variable vlrbasecofins numeric(15, 5);
    declare variable vlrpis numeric(15, 5);
    declare variable vlrcofins numeric(15, 5);
    declare variable vlrbaseii numeric(15, 5);
    declare variable vlrii numeric(15, 5);
    declare variable vlricmsdiferido numeric(15, 5);
    declare variable vlricmsdevido numeric(15, 5);
    declare variable vlricmscredpresum numeric(15, 5);

    begin

    -- Inicialização de variáveis
    if (new.vlrbasepis is null) then vlrbasepis = 0; else vlrbasepis = new.vlrbasepis;
    if (new.vlrbasecofins is null) then vlrbasecofins = 0; else vlrbasecofins = new.vlrbasepis;
    if (new.vlrpis is null) then vlrpis = 0; else vlrpis = new.vlrpis;
    if (new.vlrcofins is null) then vlrcofins = 0; else vlrcofins = new.vlrcofins;
    if (new.vlrbaseii is null) then vlrbaseii = 0; else vlrbaseii = new.vlrbaseii;
    if (new.vlrii is null) then vlrii = 0; else vlrii = new.vlrii;
    if (new.vlricmsdiferido is null) then vlricmsdiferido = 0; else vlricmsdiferido = new.vlricmsdiferido;
    if (new.vlricmsdevido is null) then vlricmsdevido = 0; else vlricmsdevido = new.vlricmsdevido;
    if (new.vlricmscredpresum is null) then vlricmscredpresum = 0; else vlricmscredpresum = new.vlricmscredpresum;

    -- Atualizando campos de totais na tabela de compra;
    update cpcompra cp set
        cp.vlrbasepiscompra = cp.vlrbasepiscompra + :vlrbasepis ,
        cp.vlrpiscompra =  cp.vlrpiscompra + :vlrpis ,
        cp.vlrbasecofinscompra = cp.vlrbasecofinscompra + :vlrbasecofins ,
        cp.vlrcofinscompra =  cp.vlrcofinscompra + :vlrcofins,
        cp.vlrbaseiicompra = cp.vlrbaseiicompra + :vlrbaseii,
        cp.vlriicompra = cp.vlriicompra + :vlrii,
        cp.vlricmsdiferido = cp.vlricmsdiferido + :vlricmsdiferido,
        cp.vlricmsdevido = cp.vlricmsdevido + :vlricmsdevido,
        cp.vlricmscredpresum = cp.vlricmscredpresum + :vlricmscredpresum

    where codcompra=new.codcompra and codemp=new.codemp and codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITCOMPRATGAU
as
    declare variable cstatus char (2);
    declare variable nvlrbasepis numeric(15, 5);
    declare variable nvlrbasecofins numeric(15, 5);
    declare variable nvlrpis numeric(15, 5);
    declare variable nvlrcofins numeric(15, 5);

    declare variable ovlrbasepis numeric(15, 5);
    declare variable ovlrbasecofins numeric(15, 5);
    declare variable ovlrpis numeric(15, 5);
    declare variable ovlrcofins numeric(15, 5);

    declare variable ovlrbaseii numeric(15, 5);
    declare variable ovlrii numeric(15, 5);

    declare variable vlricmsdiferido numeric(15, 5);
    declare variable vlricmsdevido numeric(15, 5);
    declare variable vlricmscredpresum numeric(15, 5);

begin
    -- Inicialização de variáveis (new)
    if (new.vlrbasepis is null) then nvlrbasepis = 0; else nvlrbasepis = new.vlrbasepis;
    if (new.vlrbasecofins is null) then nvlrbasecofins = 0; else nvlrbasecofins = new.vlrbasepis;
    if (new.vlrpis is null) then nvlrpis = 0; else nvlrpis = new.vlrpis;
    if (new.vlrcofins is null) then nvlrcofins = 0; else nvlrcofins = new.vlrcofins;

    -- Inicialização de variáveis (old)
    if (old.vlrbasepis is null) then ovlrbasepis = 0; else ovlrbasepis = old.vlrbasepis;
    if (old.vlrbasecofins is null) then ovlrbasecofins = 0; else ovlrbasecofins = old.vlrbasepis;
    if (old.vlrpis is null) then ovlrpis = 0; else ovlrpis = old.vlrpis;
    if (old.vlrcofins is null) then ovlrcofins = 0; else ovlrcofins = old.vlrcofins;
    if (old.vlrbaseii is null) then ovlrbaseii = 0; else ovlrbaseii = old.vlrbaseii;
    if (old.vlrii is null) then ovlrii = 0; else ovlrii = old.vlrii;

    if (old.vlricmsdiferido is null) then vlricmsdiferido = 0; else vlricmsdiferido = old.vlricmsdiferido;
    if (old.vlricmsdevido is null) then vlricmsdevido = 0; else vlricmsdevido = old.vlricmsdevido;
    if (old.vlricmscredpresum is null) then vlricmscredpresum = 0; else vlricmscredpresum = old.vlricmscredpresum;


    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin
        select cp.statuscompra from cpcompra cp
        where cp.codcompra = new.codcompra and cp.codemp=new.codemp and cp.codfilial = new.codfilial
        into :cstatus;

        if (substr(:cstatus,1,1)!='X') then
        begin
            update cpcompra cp set
            cp.vlrbasepiscompra = cp.vlrbasepiscompra - :ovlrbasepis + :nvlrbasepis,
            cp.vlrpiscompra = vlrpiscompra - :ovlrpis + :nvlrpis,
            cp.vlrbasecofinscompra = vlrbasecofinscompra - :ovlrbasecofins + :nvlrbasecofins,
            cp.vlrcofinscompra= vlrcofinscompra - :ovlrcofins + :nvlrcofins,
            cp.vlrbaseiicompra= vlrbaseiicompra - :ovlrbaseii + new.vlrbaseii,
            cp.vlriicompra= vlriicompra - :ovlrii + new.vlrii,
            cp.vlricmsdiferido= vlricmsdiferido - :vlricmsdiferido + new.vlricmsdiferido,
            cp.vlricmsdevido= vlricmsdevido - :vlricmsdevido + new.vlricmsdevido,
            cp.vlricmscredpresum = vlricmscredpresum - :vlricmscredpresum + new.vlricmscredpresum
            where codcompra=new.codcompra and codemp=new.codemp and codfilial=new.codfilial;
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITVENDATGAD
AS

    declare variable ovlrbasepis numeric(15, 5);
    declare variable ovlrbasecofins numeric(15, 5);
    declare variable ovlrpis numeric(15, 5);
    declare variable ovlrcofins numeric(15, 5);

begin
    -- Inicialização de variáveis (old)
    if (old.vlrbasepis is null) then ovlrbasepis = 0; else ovlrbasepis = old.vlrbasepis;
    if (old.vlrbasecofins is null) then ovlrbasecofins = 0; else ovlrbasecofins = old.vlrbasepis;
    if (old.vlrpis is null) then ovlrpis = 0; else ovlrpis = old.vlrpis;
    if (old.vlrcofins is null) then ovlrcofins = 0; else ovlrcofins = old.vlrcofins;

    update vdvenda vd set
        vd.vlrbasepisvenda = vd.vlrbasepisvenda - :ovlrbasepis,
        vd.vlrpisvenda = vd.vlrpisvenda - :ovlrpis,
        vd.vlrbasecofinsvenda = vd.vlrbasecofinsvenda - :ovlrbasecofins ,
        vd.vlrcofinsvenda = vd.vlrcofinsvenda - :ovlrcofins
    where codvenda=old.codvenda and tipovenda=old.tipovenda and codemp=old.codemp and codfilial=old.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITVENDATGAI
as
    declare variable vlrbasepis numeric(15, 5);
    declare variable vlrbasecofins numeric(15, 5);
    declare variable vlrpis numeric(15, 5);
    declare variable vlrcofins numeric(15, 5);

    begin

    -- Inicialização de variáveis
    if (new.vlrbasepis is null) then vlrbasepis = 0; else vlrbasepis = new.vlrbasepis;
    if (new.vlrbasecofins is null) then vlrbasecofins = 0; else vlrbasecofins = new.vlrbasepis;
    if (new.vlrpis is null) then vlrpis = 0; else vlrpis = new.vlrpis;
    if (new.vlrcofins is null) then vlrcofins = 0; else vlrcofins = new.vlrcofins;

    -- Atualizando campos de totais na tabela de venda;
    update vdvenda vd set
        vd.vlrbasepisvenda = vd.vlrbasepisvenda + :vlrbasepis ,
        vd.vlrpisvenda =  vd.vlrpisvenda + :vlrpis ,
        vd.vlrbasecofinsvenda = vd.vlrbasecofinsvenda + :vlrbasecofins ,
        vd.vlrcofinsvenda =  vd.vlrcofinsvenda + :vlrcofins
    where codvenda=new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER LFITVENDATGAU
as
    declare variable cstatus char (2);
    declare variable nvlrbasepis numeric(15, 5);
    declare variable nvlrbasecofins numeric(15, 5);
    declare variable nvlrpis numeric(15, 5);
    declare variable nvlrcofins numeric(15, 5);

    declare variable ovlrbasepis numeric(15, 5);
    declare variable ovlrbasecofins numeric(15, 5);
    declare variable ovlrpis numeric(15, 5);
    declare variable ovlrcofins numeric(15, 5);

begin
    -- Inicialização de variáveis (new)
    if (new.vlrbasepis is null) then nvlrbasepis = 0; else nvlrbasepis = new.vlrbasepis;
    if (new.vlrbasecofins is null) then nvlrbasecofins = 0; else nvlrbasecofins = new.vlrbasepis;
    if (new.vlrpis is null) then nvlrpis = 0; else nvlrpis = new.vlrpis;
    if (new.vlrcofins is null) then nvlrcofins = 0; else nvlrcofins = new.vlrcofins;

    -- Inicialização de variáveis (old)
    if (old.vlrbasepis is null) then ovlrbasepis = 0; else ovlrbasepis = old.vlrbasepis;
    if (old.vlrbasecofins is null) then ovlrbasecofins = 0; else ovlrbasecofins = old.vlrbasepis;
    if (old.vlrpis is null) then ovlrpis = 0; else ovlrpis = old.vlrpis;
    if (old.vlrcofins is null) then ovlrcofins = 0; else ovlrcofins = old.vlrcofins;

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin
        select vd.statusvenda from vdvenda vd
        where vd.codvenda = new.codvenda and VD.tipovenda = new.tipovenda
        and vd.codemp=new.codemp and vd.codfilial = new.codfilial
        into :cstatus;

        if (substr(:cstatus,1,1)!='C') then
        begin
            update vdvenda vd set
            vd.vlrbasepisvenda = vd.vlrbasepisvenda - :ovlrbasepis + :nvlrbasepis,
            vd.vlrpisvenda = vlrpisvenda - :ovlrpis + :nvlrpis,
            vd.vlrbasecofinsvenda = vlrbasecofinsvenda - :ovlrbasecofins + :nvlrbasecofins,
            vd.vlrcofinsvenda= vlrcofinsvenda - :ovlrcofins + :nvlrcofins
            where codvenda=new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilial=new.codfilial;
        end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPESTRUTURATGAU
AS
declare variable iCount smallint;
declare variable sCertfsc char;
begin
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
        --verifica se estruta está em fase de ativação
      IF(NEW.ativoest='S' AND OLD.ativoest='N') THEN
      BEGIN
         --busca na tabela de produtos se produto é certificado FSC
         select pd.certfsc from eqproduto pd
            where pd.codemp=new.codemp and pd.codfilial=new.codfilial and pd.codprod=new.codprod
            into :sCertfsc;
    
         --Se produto é certificado, e não possui nenhum subproduto cadastrado lança uma exceção.
         IF(:sCertfsc='S') then
         BEGIN
            select count(1) from PPITESTRUTURASUBPROD es
               where es.codemp= new.codemp and es.codfilial=new.codfilial and es.codprod= new.codprod  and es.seqest = new.seqest
               into :iCount;
            IF ((:iCount is null) or (:iCount<1) ) THEN
            BEGIN
               exception ppestruturaex02;
            END
         END
      END
 END
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPESTRUTURATGBI
as
begin

    -- Acertando referência quando nula
    if (new.refprod is null) then
    begin
        -- Buscando referência do produto
        select p.refprod from eqproduto p
        where p.codemp=new.codemp and p.codfilial = new.codfilial and p.codprod=new.codprod
        into new.refprod;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPITOPTGAU
as
declare variable codemprma integer;
declare variable codfilialrma smallint;
declare variable codrma integer;
declare variable coditrma smallint;
declare variable sititrma char(2);
declare variable qtditrma numeric(15,5);
declare variable qtdaprovitrma numeric(15,5);
declare variable qtdexpitrma numeric(15,5);

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        if(new.qtdcopiaitop is not null) then
        begin
            if( (new.qtdcopiaitop>0) and (old.qtdcopiaitop is null) ) then
            begin
                insert into ppitop (codemp,codfilial,codop,seqop,seqitop,
                                    codemppd, codfilialpd,codprod,refprod,
                                    qtditop, codempfs,codfilialfs, codfase,
                                    codemple,codfilialle,codlote,gerarma,SEQITOPCP)
                                    values
                                    (new.codemp,new.codfilial,new.codop,new.seqop,
                                    (select (max(op.seqitop)+1) from ppitop op
                                    where op.codemp=new.codemp and op.codfilial=new.codfilial
                                    and op.codop=new.codop and op.seqop=new.seqop),
                                    new.codemppd,new.codfilialpd,new.codprod,new.refprod,
                                    new.qtdcopiaitop,new.codempfs,new.codfilialfs,new.codfase,
                                    new.codemple, new.codfilialle,new.codloterat,new.gerarma,new.seqitop);
            end
        end
        -- Atualizando as requisições de material, caso as quantidades na OP sejam alteradas
        if(old.qtditop <> new.qtditop) then
        begin
            -- Buscando RMA Gerada para o produto de entrada
            select first 1 ir.codemp, ir.codfilial, ir.codrma, ir.coditrma, ir.sititrma,
            ir.qtditrma, ir.qtdaprovitrma, ir.qtdexpitrma
            from eqrma rm, eqitrma ir
            where rm.codemp=ir.codemp and rm.codfilial=ir.codfilial and rm.codrma=ir.codrma
            and ir.codemppd=new.codemppd and ir.codfilialpd=new.codfilialpd and ir.codprod=new.codprod
            and rm.codop=new.codop and rm.seqop=new.seqop
            into codemprma, codfilialrma, codrma, coditrma, sititrma, qtditrma, qtdaprovitrma, qtdexpitrma ;
    
            -- Atualizando item de rma
            if(sititrma='PE') then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
            else if (sititrma = 'AF' and :qtditrma=:qtdaprovitrma) then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop,ir.qtdaprovitrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
            else if(sititrma = 'EF' and :qtditrma=:qtdexpitrma) then
            begin
                update eqitrma ir set ir.qtditrma=new.qtditop,ir.qtdaprovitrma=new.qtditop,ir.qtdexpitrma=new.qtditop
                where ir.codemp=:codemprma and ir.codfilial=:codfilialrma and ir.codrma=:codrma
                and ir.coditrma=:coditrma;
            end
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPITOPTGBU
as
begin
    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      SELECT REFPROD FROM EQPRODUTO WHERE CODEMP=new.CODEMPPD AND
        CODFILIAL=new.CODFILIALPD AND CODPROD=new.CODPROD INTO new.REFPROD;
    end
    
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPENTRADATGAD
AS
declare variable preco decimal;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemple integer;
declare variable codfilialle smallint;
declare variable codlote varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codempopm integer;
declare variable codfilialopm smallint;
declare variable codopm integer;
declare variable seqopm smallint;
declare variable qtddistiop decimal(15,5);

begin

    -- Buscando custo do produto acabado
    select sum(pd.custompmprod) from ppitop it, eqproduto pd where it.codemp=old.codemp and it.codfilial=it.codfilial and
    it.codop=old.codop and it.seqop=old.seqop and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod into :preco;

    -- Buscando informações da OP
    select codemppd, codfilialpd, codprod, codemple, codfilialle, codlote, codemptm, codfilialtm, codtipomov, codempax, codfilialax, codalmox,
    codempopm, codfilialopm, codopm, seqopm, qtddistiop
    from ppop where
    codemp=old.codemp and codfilial=old.codfilial and codop=old.codop and seqop=old.seqop
    into
    :codemppd, :codfilialpd, :codprod, :codemple, :codfilialle, :codlote, :codemptm, :codfilialtm, :codtipomov, :codempax, :codfilialax, :codalmox,
    :codempopm, codfilialopm, :codopm, :seqopm, :qtddistiop;

    execute procedure eqmovprodiudsp('U', :codemppd, :codfilialpd, :codprod,
        :codemple, :codfilialle, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, old.seqent,
        null, null, null,
        old.dtent, old.codop, 'N', 0,:preco,
        :codempax, :codfilialax, :codalmox, null, 'S', 'N' );

   if (:codopm is not null) then
   begin
      execute procedure ppatudistopsp( :codempopm, :codfilialopm, :codopm, :seqopm, :qtddistiop, 0);
   end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPENTRADATGAI
as
declare variable preco numeric;
declare variable qtdest numeric;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemple integer;
declare variable codfilialle smallint;
declare variable codlote varchar(20);
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codempopm integer;
declare variable codfilialopm smallint;
declare variable codopm integer;
declare variable seqopm smallint;
declare variable qtddistiop decimal(15,5);
declare variable seqest integer;
declare variable prodetapas char(1);

begin

    -- Buscando preferências de produção

    select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
    into :prodetapas;

    -- Buscando informações da OP
    select codemppd, codfilialpd, codprod, codemple, codfilialle, codlote, codemptm, codfilialtm, codtipomov, codempax, codfilialax, codalmox,
    codempopm, codfilialopm, codopm, seqopm, qtddistiop, seqest
    from ppop where codemp=new.codemp and codfilial=new.codfilial and codop=new.codop and seqop=new.seqop
    into
    :codemppd, :codfilialpd, :codprod, :codemple, :codfilialle, :codlote, :codemptm, :codfilialtm, :codtipomov, :codempax, :codfilialax, :codalmox,
    :codempopm, codfilialopm, :codopm, :seqopm, :qtddistiop, :seqest;

    select pe.qtdest from ppestrutura pe
    where pe.codemp=:codemppd and pe.codfilial=:codfilialpd and
    pe.codprod=:codprod and pe.seqest=:seqest into :qtdest;

    if (:codopm is not null) then
    begin
        execute procedure ppatudistopsp( :codempopm, :codfilialopm, :codopm, :seqopm, :qtddistiop, 0);
    end

    -- Se o processo de finalização for em etapas deve gerar movimentação de estoque vinculada ao item de entrada da O.P.
    if( :prodetapas = 'S' ) then
    begin

        -- Buscando custo do produto acabado
        select sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) * it.qtditop ) / op.qtdprevprodop
        from ppitop it, eqproduto pd, ppop op
        where it.codemp=new.codemp and it.codfilial=it.codfilial
        and it.codop=new.codop and it.seqop=new.seqop
        and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
        and pd.codprod=it.codprod
        and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop and op.seqop=new.seqop
        group by op.qtdprevprodop
        into :preco;

        execute procedure EQMOVPRODIUDSP('I', :CODEMPPD, :CODFILIALPD, :CODPROD,
        :CODEMPLE, :CODFILIALLE, :codlote, :codemptm,
        :codfilialtm, :codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, new.seqent,
        null, null,  null, new.dtent, new.codop,
        'S',new.qtdent,cast(:preco as numeric(15,5)),
        :codempax, :codfilialax, :codalmox, null, 'S', 'N' );

        -- Atualizando quantidade final produzida na O.P.
        update ppop op set
        op.qtdfinalprodop=( select sum(et.qtdent) from ppopentrada et
                            where et.codemp=new.codemp and et.codfilial=new.codfilial and et.codop=new.codop and et.seqop=new.seqop
                           )
        where op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop and op.seqop=new.seqop;

    end

    if (:CODOPM is not null) then EXECUTE PROCEDURE PPATUDISTOPSP(:CODEMPOPM, :CODFILIALOPM, :CODOPM, :SEQOPM, 0, :QTDDISTIOP);

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPFASETGAU
AS
declare variable seqsubprod smallint;
begin

    -- Buscando subprodução para finalização

    if(old.datafimprodfs is null and new.datafimprodfs is not null) then
    begin
        for select sp.seqsubprod from ppopsubprod sp where sp.codemp=new.codemp and sp.codfilial=new.codfilial and sp.codop=new.codop and sp.seqop=new.seqop
        and sp.codempfs=new.codempfs and sp.codfilialfs=new.codfilialfs and sp.codfase=new.codfase and sp.seqof=new.seqof
        into :seqsubprod
        do
        begin

            update ppopsubprod sp set sp.dtsubprod=new.datafimprodfs
            where sp.codemp=new.codemp and sp.codfilial=new.codfilial and sp.codop=new.codop and sp.seqop=new.seqop
            and sp.codempfs=new.codempfs and sp.codfilialfs=new.codfilialfs and sp.codfase=new.codfase and sp.seqof=new.seqof
            and sp.seqsubprod=:seqsubprod;

        end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPFASETGBU0
as
declare variable finaliza char(2);
declare variable qtdpe smallint;
declare variable icodemptm int;
declare variable icodfilialtm smallint;
declare variable icodtipomov integer;

begin

    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

    if ((new.sitfs!='FN') and (new.datafimprodfs is not null)) then
    begin

        new.sitfs = 'FN';

        select ef.finalizaop
            from ppestrufase ef, ppopfase pf, ppop op
            where
                ef.codemp = op.codemppd and ef.codfilial = op.codfilialpd
                and ef.codprod = op.codprod and ef.seqest = op.seqest
                and ef.codempfs = pf.codempfs and ef.codfilialfs = pf.codfilialfs
                and ef.codfase = pf.codfase and pf.codemp = op.codemp
                and pf.codfilial = op.codfilial and pf.codop = op.codop
                and pf.seqop = op.seqop and pf.seqof = new.seqof
                and op.codemp=new.codemp and op.codfilial=new.codfilial
                and op.codop=new.codop and op.seqop=new.seqop and ef.seqef = pf.seqof
             into :finaliza;

        if (:finaliza='S') then
            begin
                select count(1)
                    from ppopfase pf
                    where pf.codemp = new.codemp and pf.codfilial=new.codfilial
                        and pf.codop=new.codop and pf.seqop=new.seqop and pf.sitfs<>'FN'
                into :qtdpe;

                if(:qtdpe>1) then
                begin
                    exception ppopfase 'O processo não poderá ser concluído, pois existem fases pendentes!';
                end
                else
                begin
                    update ppop set sitop = 'FN'
                        where codemp = new.codemp and codfilial = new.codfilial
                            and codop = new.codop and seqop = new.seqop;
                end
            end
        end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPITORCTGAI
AS
begin
  -- Atualizando status do ítem de orçamento
  update vditorcamento io set io.sitproditorc='EP'
  where io.codemp=new.codempoc and io.codfilial=new.codfilialoc and
  io.codorc=new.codorc and io.coditorc=new.coditorc and io.tipoorc=new.tipoorc;
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPSUBPRODTGAD
AS
declare variable preco decimal(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable dtestoque date;
begin

    --Buscando almoxarifado do produto
    select codempax, codfilialax, codalmox from eqproduto where codemp=old.codemppd and codfilial=old.codfilialpd and codprod=old.codprod
    into :codempax, :codfilialax, :codalmox;

    -- Buscando custo do produto;
    select ncustompm from eqprodutosp01(old.codemppd,old.codfilialpd,old.codprod,null,null,null)
    into :preco;

    -- Buscando data de finalização da fase
    select coalesce(fs.datafimprodfs,op.dtemitop) from ppopfase fs, ppop op
    where
    op.codemp=fs.codemp and op.codfilial=fs.codfilial and op.codop=fs.codop and op.seqop=fs.seqop and
    fs.codemp=old.codemp and fs.codfilial=old.codfilial and fs.codop=old.codop and fs.seqop=old.seqop and fs.codempfs=old.codempfs
    and fs.codfilialfs=old.codfilialfs and fs.codfase=old.codfase and fs.seqof=old.seqof
    into :dtestoque;

 
   EXECUTE PROCEDURE EQMOVPRODIUDSP('D',old.CODEMPPD, old.CODFILIALPD, old.CODPROD,
        old.CODEMPLE, old.CODFILIALLE, old.codlote, old.codemptm,
        old.codfilialtm, old.codtipomov, null, null, null ,null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, old.codemp, old.codfilial, old.codop, old.seqop, null,
        null, null, null,
        :dtestoque, old.codop, 'N', old.qtditsp,:preco,
        :codempax, :codfilialax, :codalmox, old.seqsubprod, 'S', 'N' );
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPSUBPRODTGAI
AS
declare variable dtestoque date;
declare variable preco decimal(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
begin

    --Buscando almoxarifado do produto
    select codempax, codfilialax, codalmox from eqproduto where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
    into :codempax, :codfilialax, :codalmox;

    -- Buscando custo do produto;
    select ncustompm from eqprodutosp01(new.codemppd,new.codfilialpd,new.codprod,null,null,null)
    into :preco;

    -- Buscando data de finalização da fase
    select coalesce(fs.datafimprodfs,op.dtemitop) from ppopfase fs, ppop op
    where
    op.codemp=fs.codemp and op.codfilial=fs.codfilial and op.codop=fs.codop and op.seqop=fs.seqop and
    fs.codemp=new.codemp and fs.codfilial=new .codfilial and fs.codop=new.codop and fs.seqop=new.seqop and fs.codempfs=new.codempfs
    and fs.codfilialfs=new.codfilialfs and fs.codfase=new.codfase and fs.seqof=new.seqof
    into :dtestoque;

    EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
    null, null, null, null, null, null,
    null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, null,
    null, null,  null, :dtestoque, new.codop,
    'N',0.00,cast(:preco as numeric(15,5)),
    :codempax, :codfilialax, :codalmox, new.seqsubprod, 'S', 'N' );

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAI
as
declare variable preco numeric;
declare variable qtdest numeric;
declare variable prodetapas char(1);
begin

   EXECUTE PROCEDURE PPITOPSP01(new.CODEMP, new.CODFILIAL, new.CODOP, new.SEQOP);

   EXECUTE PROCEDURE ppgeraopcq (new.CODEMP, new.CODFILIAL, new.CODOP, new.SEQOP);

   select sum(pd.custompmprod) from ppitop it, eqproduto pd where it.codemp=new.codemp and
   it.codfilial=it.codfilial and it.codop=new.codop and it.seqop=new.seqop and
   pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and
   pd.codprod=it.codprod into :preco;

   select pe.qtdest from ppestrutura pe
      where pe.codemp=new.codemppd and pe.codfilial=new.codfilialpd and
      pe.codprod=new.codprod and pe.seqest=new.seqest into :qtdest;

   -- Buscando preferências de produção

   select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
   into :prodetapas;

   -- Se o processo de finalização não for em etapas deve gerar movimentação de estoque vinculada diretamente a O.P.
   if( :prodetapas = 'N' or :prodetapas is null ) then
   begin

       EXECUTE PROCEDURE EQMOVPRODIUDSP('I',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
            new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
            new.codfilialtm, new.codtipomov, null, null, null ,null, null,
            null, null, null, null, null, null,
            null, null, null, null, null,new.codemp, new.codfilial,new.codop, new.seqop, null,
            null, null,  null, new.dtfabrop, new.codop,
            'N',new.qtdfinalprodop,cast(:preco as numeric(15,5)),
            new.codempax, new.codfilialax, new.codalmox, null, 'S', 'N' );

       if (new.CODOPM is not null) then
          EXECUTE PROCEDURE PPATUDISTOPSP(new.CODEMPOPM, new.CODFILIALOPM, new.CODOPM,
            new.SEQOPM, 0, new.QTDDISTIOP);

   end

end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGAU
as
declare variable preco decimal(15,5);
declare variable codempoc integer;
declare variable codfilialoc smallint;
declare variable tipoorc char(1);
declare variable codorc integer;
declare variable coditorc smallint;
declare variable prodetapas char(1);
declare variable qtdprodorc decimal(15,5);

begin

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        -- Buscando preferências de produção
    
        select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
        into :prodetapas;
    
        /*Cancelamento de O.P */
        
        if(old.sitop!='CA' and new.sitop = 'CA') then
        begin
    
            /* Cancelamento de movimentação de estoque */
    
            -- Se o processo de finalização não for em etapas deve gerar movimentação de estoque vinculada diretamente a O.P.
            if( :prodetapas = 'N' ) then
            begin
                execute procedure eqmovprodiudsp('D',new.codemppd, new.codfilialpd, new.codprod,
                    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                    null, null, null, null, null, null,null, null, null, null, null,
                    new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                    new.dtfabrop, new.codop, null, new.qtdfinalprodop,:preco,
                    new.codempax, new.codfilialax, new.codalmox, null, 'S', 'N' );
            end
            else
            begin
    
                delete from ppopentrada et where et.codemp=new.codemp and et.codfilial=new.codfilial and et.codop=new.codop and et.seqop=new.seqop;
    
            end
    
            -- Desfazendo vinculos com ítem de orçamento
            delete from ppopitorc oi where oi.codemp=new.codemp and oi.codfilial=new.codfilial
            and oi.codop=new.codop and oi.seqop=new.seqop;
    
            -- Cancelando as RMAs vinculadas
            update eqrma rma set rma.sitrma='CA', rma.motivocancrma='Ordem de produção original cancelada!'
            where rma.codempof=new.codemp and rma.codfilialof=new.codfilial and rma.codop=new.codop and rma.seqop=new.seqop;
    
            -- Excluindo subproducao
            delete from ppopsubprod where codemp=new.codemp and codfilial=new.codfilial and codop=new.codop and seqop = new.seqop;
    
        end
        else if (old.sitop!='FN' and new.sitop='FN') then
        begin
            -- Atualizando status do ítem de orçamento na finalização da OP
            for select oi.codempoc,oi.codfilialoc, oi.tipoorc, oi.codorc, oi.coditorc
            from ppopitorc oi
            where oi.codemp=new.codemp and oi.codfilial=new.codfilial and oi.codop=new.codop and oi.seqop=new.seqop
            into :codempoc, :codfilialoc, :tipoorc, :codorc, :coditorc do
            begin
                update vditorcamento io set io.sitproditorc = 'PD'
                where io.codemp=:codempoc and io.codfilial=:codfilialoc and io.tipoorc=:tipoorc
                and io.codorc=:codorc and io.coditorc=:coditorc;
            end
    
            if( :prodetapas = 'N' ) then
            begin
                -- Buscando custo do produto acabado
               if ( (new.qtdfinalprodop is null) or (new.qtdfinalprodop=0) ) then
               begin
                  preco = 0;
               end
               else
               begin
                   select cast(cast(sum( cast((select cast(ncustompm as decimal(15,5)) 
                   from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) as decimal(15,5)) * it.qtditop ) 
                   as decimal(15,5)) / new.qtdfinalprodop as decimal(15,5))
                       from ppitop it, eqproduto pd
                       where it.codemp=new.codemp and it.codfilial=it.codfilial
                       and it.codop=new.codop and it.seqop=new.seqop
                       and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
                       and pd.codprod=it.codprod
                   into :preco;
               end
    
               execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                    null, null, null, null, null, null,null, null, null, null, null,
                    new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                    new.dtfabrop, new.codop, 'N', new.qtdfinalprodop,:preco,
                    new.codempax, new.codfilialax, new.codalmox, null, 'S', 'N' );
    
                -- Buscando quantidade de produto acabado destinado a orçamentos;
                select cast(sum(oo.qtdprod) as decimal(15,5)) from ppopitorc oo
                where oo.codemp=new.codemp and oo.codfilial=new.codfilial and oo.codop=new.codop and oo.seqop=new.seqop
                into :qtdprodorc;
    
                -- Atualizando a quantidade final produzida por item de orçamento;
    
                if(:qtdprodorc is not null and :qtdprodorc > 0 ) then
                begin
    
                    update ppopitorc oo set oo.qtdfinalproditorc = cast( ( cast(cast(oo.qtdprod as decimal(15,5)) /  cast(:qtdprodorc as decimal(15,5) ) as decimal(15,5)) * (cast(new.qtdfinalprodop as decimal(15,5)))) as decimal(15,5) )
                    where oo.codemp=new.codemp and oo.codfilial=new.codfilial and oo.codop=new.codop and oo.seqop=new.seqop;
    
                end
    
            end
            
            else
            begin    --se for em etapas executar a atualização dos itens de RMA
                   execute procedure ppitopsp02(new.codemp, new.codfilial, new.codop, new.seqop);
            end
            
        end
    
        /* Outras ações */
    
        else
        begin
            if (old.qtdprevprodop <> new.qtdprevprodop ) then
            begin
                delete from ppitop
                    where codemp=new.codemp AND codfilial=new.codfilial
                        and codop=new.codop and seqop=new.seqop;
    
                delete from PPOPFASE
                    where codemp=new.codemp and codfilial=new.codfilial
                        and codop=new.codop and seqop=new.seqop;
    
                execute procedure ppitopsp01(new.codemp, new.codfilial, new.codop, new.seqop);
            end
    
            if( (old.qtdfinalprodop <> new.qtdfinalprodop) and (new.qtdfinalprodop>0) ) then
            begin
    
                if( :prodetapas = 'N' ) then
                begin
    
                    -- Buscando custo do produto acabado
                    select cast(sum((select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)) * it.qtditop ) / new.qtdfinalprodop as decimal(15,5))
    
                    from ppitop it, eqproduto pd
                    where it.codemp=new.codemp and it.codfilial=it.codfilial
                    and it.codop=new.codop and it.seqop=new.seqop
                    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd
                    and pd.codprod=it.codprod
                    into :preco;
    
                    execute procedure eqmovprodiudsp('U',new.codemppd, new.codfilialpd, new.codprod,
                    new.CODEMPLE, new.CODFILIALLE, new.codlote, new.codemptm,
                    new.codfilialtm, new.codtipomov, null, null, null ,null, null,
                    null, null, null, null, null, null,null, null, null, null, null,
                    new.codemp, new.codfilial,new.codop,new.seqop, null, null, null, null,
                    new.dtfabrop, new.codop, 'N', new.qtdfinalprodop,:preco,
                    new.codempax, new.codfilialax, new.codalmox, null, 'S', 'N' );
    
                end
    
                execute procedure ppitopsp02(new.codemp, new.codfilial, new.codop, new.seqop);
    
            end
            if (new.CODOPM is not null) then
                execute procedure ppatudistopsp(new.codempopm, new.codfilialopm, new.codopm,
                    new.seqopm, old.qtddistiop, new.qtddistiop);
    
        end
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER PPOPTGBU
as
declare variable prodetapas char(1);
begin

    -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
    if ( new.emmanut is null) then
        new.emmanut='N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        new.DTALT=cast('now' AS DATE);
        new.IDUSUALT=USER;
        new.HALT = cast('now' AS TIME);
        SELECT REFPROD FROM EQPRODUTO WHERE CODEMP=new.CODEMP AND
        CODFILIAL=new.CODFILIAL AND CODPROD=new.CODPROD INTO new.REFPROD;
    
        -- Buscando preferências de produção
        select coalesce(p5.prodetapas,'N') from sgprefere5 p5 where p5.codemp=new.codemp and p5.codfilial=new.codfilial
        into :prodetapas;
    
        if('S'=:prodetapas) then
        begin
    
            if( new.qtdfinalprodop>=new.qtdprevprodop ) then
            begin
                select coalesce(tm.codemptm,tm.codemp),coalesce(tm.codfilialtm,tm.codfilial),coalesce(tm.codtipomovtm,tm.codtipomov)
                from eqtipomov tm, ppop op
                where tm.codemp=op.codemptm and tm.codfilial=op.codfilialtm and tm.codtipomov=op.codtipomov
                and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop
                and op.seqop=new.seqop
                into new.codemptm,new.codfilialtm,new.codtipomov;
                new.sitop='FN';
            end
    
        end
        else
        begin
            if(new.qtdfinalprodop>0 and new.qtdfinalprodop<>old.qtdfinalprodop) then
            begin
                select coalesce(tm.codemptm,tm.codemp),coalesce(tm.codfilialtm,tm.codfilial),coalesce(tm.codtipomovtm,tm.codtipomov)
                from eqtipomov tm, ppop op
                where tm.codemp=op.codemptm and tm.codfilial=op.codfilialtm and tm.codtipomov=op.codtipomov
                and op.codemp=new.codemp and op.codfilial=new.codfilial and op.codop=new.codop
                and op.seqop=new.seqop
                into new.codemptm,new.codfilialtm,new.codtipomov;
                new.sitop='FN';
            end
         end
    
         if(new.sitop='CA' and old.sitop<>'CA') then
         begin
             new.idusucanc=USER;
             new.dtcanc=cast('now' AS DATE);
             new.hcanc = cast('now' AS TIME);
         end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER PVCAIXATGBU
as
  DECLARE VARIABLE ICOUNTSEQ INTEGER;
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT=cast('now' AS TIME);
  
  IF ((old.SEQINI<>new.SEQINI) or (old.SEQMAX<>new.SEQMAX) ) THEN
  BEGIN
     SELECT COUNT(SEQCAIXA) FROM PVSEQUENCIA
        WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL
        AND CODCAIXA=new.CODCAIXA
        INTO :ICOUNTSEQ;
     IF (:ICOUNTSEQ > 0) THEN
        EXCEPTION PVSEQCAIXA01 'JÁ EXISTEM VENDAS DENTRO DESTA FAIXA DE VENDA';
  END

  EXECUTE PROCEDURE PVVALIDFAIXACAIXASP(new.CODEMP, new.CODFILIAL, new.CODCAIXA, new.SEQINI, new.SEQMAX);

end
^

/* Alter exist trigger... */
ALTER TRIGGER SGAGENDATGBU
AS -- Mecanismo de troca de usuario da agenda
declare variable codagd integer;
begin
    new.DTALT=cast('now' as date);
    new.IDUSUALT=USER;
    new.HALT = cast('now' as time);

  if(new.codage != old.codage) then
  begin
      select iseq from spgeranum(new.codemp,new.codfilial,'AG') into codagd;
      /*Insere o agendamento para o novo usuário*/
      insert into sgagenda (
            codemp, codfilial, codage, tipoage, codagd,
            dataagd, dtainiagd, hriniagd, dtafimagd, hrfimagd,
            assuntoagd, descagd, codempta, codfilialta, codtipoagd, prioragd, sitagd,
            codempae, codfilialae, codageemit, tipoageemit, caagd, resolucaomotivo,
            codempar,codfilialar,codagear,tipoagear,codagdar)
        values (
            new.codemp, new.codfilial, new.codage, new.tipoage, :codagd,
            new.dataagd, new.dtainiagd, new.hriniagd, new.dtafimagd, new.hrfimagd,
            new.assuntoagd, new.descagd, new.codempta, new.codfilialta, new.codtipoagd,
            new.prioragd, new.sitagd,new.codempae, new.codfilialae, new.codageemit, new.tipoageemit,
            new.caagd, new.resolucaomotivo, new.codempar, new.codfilialar, new.codagear, new.tipoagear,
            new.codagdar);

        new.codage = old.codage; -- Cancela a troca de usuario;
        new.sitagd = 'CA'; -- Cancela agendamento atual;
  end
end
^

/* Alter exist trigger... */
ALTER TRIGGER SGFILIALTGAU
AS
    declare variable filialpref smallint;
    declare variable geracodunif char(1);

begin
      -- Atualização de descrição do código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S' and new.codunifcod is not null) then
    begin
        update sgunifcod set descunifcod=new.razfilial
        where codemp=new.codempuc and codfilial=new.codfilialuc and codunifcod=new.codunifcod;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER SGTRATARETAI
as
begin
-- Implementação inútil pois não é possível atribuir valor à variavel new.xxx
/*  IF (new.CODPROCGT IS NULL AND NOT new.SEQITPROCGT IS NULL) THEN
    new.CODPROCGT = new.CODPROC;*/

end
^

/* Alter exist trigger... */
ALTER TRIGGER TKCONTATOTGBI
AS
    declare variable nomemuniccto CHAR(30);
begin

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(new.codmunic is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemuniccto;
        if (nomemuniccto is not null) then new.cidcto = nomemuniccto;
    end

    if(new.siglauf is not null) then
    begin
        new.ufcto=new.siglauf;
    end

    /* Fim da atualização do campo cidade e estado*/

end
^

/* Alter exist trigger... */
ALTER TRIGGER TKCONTATOTGBU
as
  declare variable nomemuniccto CHAR(30);
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);

   /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(old.codmunic != new.codmunic) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemuniccto;
        if (nomemuniccto is not null) then new.cidcto = nomemuniccto;
    end

    if(new.siglauf is not null) then
    begin
        new.ufcto=new.siglauf;
    end

    /* Fim da atualização do campo cidade */

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDCLIENTETGAU
AS
    declare variable filialpref smallint;
    declare variable geracodunif char(1);

begin
      -- Atualização de descrição do código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S' and new.codunifcod is not null) then
    begin
        update sgunifcod set descunifcod=new.razcli
        where codemp=new.codempuc and codfilial=new.codfilialuc and codunifcod=new.codunifcod;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDCLIENTETGBI
as
    declare variable nomemuniccli CHAR(30);
    declare variable nomemunicent CHAR(30);
    declare variable nomemuniccob CHAR(30);
    declare variable filialpref smallint;
    declare variable geracodunif CHAR(1);
    declare variable codunifcod integer;
begin

    if (new.codpesq is null) then
    begin
        new.codemppq = new.codemp;
        new.codfilialpq = new.codfilial;
        new.codpesq = new.codcli;
    end

    -- Atualização do campo cidade e estado (temporário para retro-compatibilidade)

    if(new.codmunic is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemuniccli;
        if (nomemuniccli is not null) then new.cidcli = nomemuniccli;
    end

    if(new.siglauf is not null) then
    begin
        new.ufcli=new.siglauf;
    end

    if(new.codmunicent is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpaisent and mun.siglauf=new.siglaufent and mun.codmunic=new.codmunicent
                into :nomemunicent;

        if (nomemunicent is not null) then new.cident = nomemunicent;
    end

    if(new.siglaufent is not null) then
    begin
        new.ufent=new.siglaufent;
    end

    if(new.codmuniccob is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpaiscob and mun.siglauf=new.siglaufcob and mun.codmunic=new.codmuniccob
                into :nomemuniccob;

        if (nomemuniccob is not null) then new.cidcob = nomemuniccob;
    end

    if(new.siglaufcob is not null) then
    begin
        new.ufcob=new.siglaufcob;
    end

    -- Fim da atualização do campo cidade e estado

    -- Geração de código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S') then
    begin

        select (coalesce(max(codunifcod),0)+1) from sgunifcod where codemp=new.codemp and codfilial=new.codfilial
        into :codunifcod;

        insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod)
        values (new.codemp, new.codfilial, :codunifcod, 'C', new.razcli);
        new.codempuc=new.codemp;
        new.codfilialuc=new.codfilial;
        new.codunifcod=:codunifcod;

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDCLIENTETGBU
as
    declare variable nomemuniccli CHAR(30);
    declare variable nomemunicent CHAR(30);
    declare variable nomemuniccob CHAR(30);
    declare variable filialpref smallint;
    declare variable geracodunif char(1);
    declare variable codunifcod integer;

begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=user;
    new.HALT = cast('now' AS TIME);

    if (new.codpesq is null) then
    begin
        new.codemppq = new.codemp;
        new.codfilialpq = new.codfilial;
        new.codpesq = new.codcli;
    end

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if ( ((old.codmunic is null) and (new.codmunic is not null)) or
         (old.codmunic<>new.codmunic) ) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemuniccli;
        if (nomemuniccli is not null) then new.cidcli = nomemuniccli;
    end

    if(new.siglauf is not null) then
    begin
        new.ufcli=new.siglauf;
    end

    if(old.codmunicent != new.codmunicent) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpaisent and mun.siglauf=new.siglaufent and mun.codmunic=new.codmunicent
                into :nomemunicent;

        if (nomemunicent is not null) then new.cident = nomemunicent;
    end

    if(new.siglaufent is not null) then
    begin
        new.ufent=new.siglaufent;
    end

    if(old.codmuniccob != new.codmuniccob) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpaiscob and mun.siglauf=new.siglaufcob and mun.codmunic=new.codmuniccob
                into :nomemuniccob;

        if (nomemuniccob is not null) then new.cidcob = nomemuniccob;
    end

    if(new.siglaufcob is not null) then
    begin
        new.ufcob=new.siglaufcob;
    end

    /* Fim da atualização do campo cidade */

    -- Geração de código unificador (SGUNIFCOD)

    if (new.codunifcod is null) then
    begin
      select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
      select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
      if( :geracodunif = 'S' ) then
      begin

         select (coalesce(max(codunifcod),0)+1) from sgunifcod where codemp=new.codemp and codfilial=new.codfilial
         into :codunifcod;

         insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod)
         values (new.codemp, new.codfilial, :codunifcod, 'C', new.razcli);

         new.codempuc=new.codemp;
         new.codfilialuc=new.codfilial;
         new.codunifcod=:codunifcod;

       end
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDCOMISSAOTGBU
AS
BEGIN
  if (new.EMMANUT IS NULL) then
     new.EMMANUT = 'N';
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
     new.DTALT=cast('now' AS DATE);
     new.IDUSUALT=USER;
     new.HALT = cast('now' AS TIME);
     IF ( ( new.DTCOMPCOMI IS NULL ) OR ( new.DATACOMI<>old.DATACOMI ) ) THEN
        new.DTCOMPCOMI = new.DATACOMI;
     IF ( OLD.STATUSCOMI = 'CP' AND NEW.STATUSCOMI = 'C1' ) then
       EXCEPTION VDCOMISSAOEX01;
     ELSE if ( OLD.STATUSCOMI='CP' AND NEW.STATUSCOMI='CP' AND OLD.VLRCOMI!=NEW.VLRCOMI ) then
       EXCEPTION VDCOMISSAOEX01;
     ELSE IF ( OLD.STATUSCOMI = 'C1' AND NEW.STATUSCOMI = 'CP' ) then
       EXCEPTION VDCOMISSAOEX02;
     ELSE IF ( OLD.STATUSCOMI = 'CP' AND NEW.STATUSCOMI = 'CD' ) THEN /* Estorno de comissões */
     BEGIN
        new.STATUSCOMI ='C2';
        new.CODEMPCI = null;
        new.CODFILIALCI = null;
        new.CODPCOMI = null;
        new.VLRPAGOCOMI = 0;
     END
     IF ( new.VLRCOMI IS NULL) THEN
       new.VLRCOMI = 0;
     IF ( new.VLRPAGOCOMI IS NULL ) THEN
       new.VLRPAGOCOMI = 0;
     new.VLRAPAGCOMI = new.VLRCOMI - new.VLRPAGOCOMI;
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDFRETEVDTGAI
as
declare variable vlrliqvenda numeric(15,5);
declare variable vlrbaseicmsfretevd numeric(15,5);
declare variable vlricmsfretevd numeric(15,5);
begin
    -- Buscando valor liquido da venda
    select vlrliqvenda from vdvenda
    where tipovenda=new.tipovenda and codvenda=new.codvenda and codemp=new.codemp and codfilial=new.codfilial
    into vlrliqvenda;
    
    -- Calcula o percentual do frete sobre o valor da venda
    if (not vlrliqvenda is null and vlrliqvenda > 0 and not new.percvendafretevd > 0) then
    begin
        new.percvendafretevd = (new.vlrfretevd/:vlrliqvenda)*100;
    end

    -- Atualizando valor do frete na tabela de venda.

    update vdvenda vd set vd.vlrfretevenda = new.vlrfretevd
    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda;

    -- Busca totais da base de calculo do icms do frete e valor do icms do frete
    select sum(lfi.vlrbaseicmsfreteitvenda), sum(lfi.vlricmsfreteitvenda) from lfitvenda lfi
    where lfi.codemp=new.codemp and lfi.codfilial=new.codfilial and lfi.codvenda=new.codvenda and lfi.tipovenda=new.tipovenda
    into :vlrbaseicmsfretevd, :vlricmsfretevd;

    -- Atualizando os valores (supondo que já houve o reprocessamento dos valores nos ítens);
    new.vlrbaseicmsfretevd = :vlrbaseicmsfretevd;
    new.vlricmsfretevd =: vlricmsfretevd;

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDFRETEVDTGBU
as
    declare variable vlrliqvenda numeric(15,5);
    declare variable vlrbaseicmsfretevd numeric(15,5);
    declare variable vlricmsfretevd numeric(15,5);

begin

    -- Atualizando o log de alterações
    new.dtalt=cast('now' as date);
    new.idusualt = user;
    new.halt = cast('now' as time);

    -- Buscando o valor liquido da venda
    select coalesce(vlrliqvenda,0) from vdvenda
    where tipovenda=new.tipovenda and codvenda=new.codvenda and codemp=new.codemp and codfilial=new.codfilial
    into vlrliqvenda;

    -- Calculando o percentual do frete sobre a venda
    if (vlrliqvenda > 0) then
    begin
        new.percvendafretevd = (new.vlrfretevd/:vlrliqvenda) * 100;
    end

    -- Verifica se o valor do frete foi alterado.
    if(new.vlrfretevd != old.vlrfretevd) then
    begin

        -- Atualização do valor do frete na tabela de venda
        update vdvenda vd set vd.vlrfretevenda = new.vlrfretevd
        where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda;

        -- Busca totais da base de calculo do icms do frete e valor do icms do frete
        select sum(lfi.vlrbaseicmsfreteitvenda), sum(lfi.vlricmsfreteitvenda) from lfitvenda lfi
        where lfi.codemp=new.codemp and lfi.codfilial=new.codfilial and lfi.codvenda=new.codvenda and lfi.tipovenda=new.tipovenda
        into :vlrbaseicmsfretevd, :vlricmsfretevd;

        -- Atualizando os valores (supondo que já houve o reprocessamento dos valores nos ítens);
        new.vlrbaseicmsfretevd = :vlrbaseicmsfretevd;
        new.vlricmsfretevd =: vlricmsfretevd;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGAI
AS
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
begin

    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    -- Salvamento de custos no momento do orçamento
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

        -- Inserindo registro na tabela de custos de item de venda
        insert into vditcustoorc (codemp,codfilial,codorc,tipoorc,coditorc,vlrprecoultcp, vlrcustompm, vlrcustopeps)
        values (new.codemp,new.codfilial,new.codorc,new.tipoorc,new.coditorc,:custouc,:custompm,:custopeps);

        -- Buscando e inserindo previsão de tributos
        insert into vdprevtribitorc(codemp, codfilial, codorc, tipoorc, coditorc,
        vlricmsitorc, vlripiitorc, vlrpisitorc, vlrcofinsitorc, vlriritorc, vlrcsocialitorc, vlrissitorc)
        select new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc,
        vlricms, vlripi, vlrpis, vlrcofins, vlrir, vlrcsocial, vlriss
        from lfbuscaprevtriborc(new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc);

    end




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
    declare variable qtdstatusitem integer;
    declare variable qtdstatustot integer;
    declare variable tipoprod char(1);

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin

    if (old.vlrdescitorc<>new.vlrdescitorc
        or old.vlrproditorc<>new.vlrproditorc
        or old.vlrliqitorc<>new.vlrliqitorc ) then
    begin
      update vdorcamento set
        VLRDESCITORC = VLRDESCITORC - old.VLRDESCITORC + new.VLRDESCITORC,
        VLRPRODORC = VLRPRODORC - old.VLRPRODITORC + new.VLRPRODITORC,
        VLRLIQORC = VLRLIQORC - old.VLRLIQITORC + new.VLRLIQITORC
        where CODORC=new.CODORC and TIPOORC='O' and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL;
    end

    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    if( visualizalucr = 'S' ) then
    begin
            select tipoprod from eqproduto where codemp=new.codemppd and codfilial=new.codfilialpd
               and codprod=new.codprod
            into :tipoprod;

            if (tipoprod='P') then
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
                   and ico.tipoorc=new.tipoorc and ico.coditorc=new.coditorc
                   -- Condição inserida para evitar cascade quando não for necessário
                   and (vlrprecoultcp<>:custouc or vlrcustompm<>:custompm or vlrcustopeps<>:custopeps ) ;

            end 

            -- Buscando e inserindo previsão de tributos
            select vlricms, vlripi, vlrpis, vlrcofins, vlrir, vlrcsocial, vlriss
            from lfbuscaprevtriborc(new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc)
            into :vlricmsitorc, :vlripiitorc, :vlrpisitorc, :vlrcofinsitorc, :vlriritorc, :vlrcsocialitorc, :vlrissitorc;

            update vdprevtribitorc po set
            po.vlricmsitorc=:vlricmsitorc, po.vlripiitorc=:vlripiitorc, po.vlrpisitorc=:vlrpisitorc,
            po.vlrcofinsitorc=:vlrcofinsitorc, po.vlriritorc=:vlriritorc, po.vlrcsocialitorc=:vlrcsocialitorc,
            po.vlrissitorc=:vlrissitorc
            where po.codemp=new.codemp and po.codfilial=new.codfilial and po.codorc=new.codorc
            and po.tipoorc=new.tipoorc and po.coditorc=new.coditorc
             -- Condição inserida para evitar cascade quando não for necessário
             and ( po.vlricmsitorc<>:vlricmsitorc or po.vlripiitorc<>:vlripiitorc
              or po.vlrpisitorc<>:vlrpisitorc or po.vlrcofinsitorc<>:vlrcofinsitorc
              or po.vlriritorc<>:vlriritorc or po.vlrcsocialitorc<>:vlrcsocialitorc
              or po.vlrissitorc<>:vlrissitorc )
            ;

       end

       -- Se o status foi alterado para Aprovado (OL), deve atualizar o status das ordens de serviço vinculadas
       -- para OA (Orçamento aprovado)
       if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' ) then
       begin
            update eqitrecmercitositorc ios set ios.status='OA'
            where ios.codempoc=new.codemp and ios.codfilialoc=new.codfilial and ios.codorc=new.codorc
            and ios.tipoorc=new.tipoorc and ios.coditorc=new.coditorc
            and ios.status<>'OA';
       end

       -- Contando a quantidade de itens do orçamento com o status do item atual
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL and statusitorc=new.statusitorc
       into :qtdstatusitem;

       -- Contando a quantidade de itens total do orçamento
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL 
       into :qtdstatustot;

       -- Se todos os itens do orçamento tem o mesmo status, deve ataulizar o status do cabeçalho do orçamento;
       if(:qtdstatusitem > 0 and :qtdstatusitem = :qtdstatustot) then
       begin
            update vdorcamento set statusorc=new.statusitorc
            where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL
            and statusorc<>new.statusitorc;
       end

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGBI
as
    declare variable refprod VARchar(20);
    declare variable vlripi numeric(15,5);
    declare variable contribipi char(1);
    declare variable adicfrete char(1);

begin

    select fi.contribipifilial from sgfilial fi where fi.codemp=new.codemp and fi.codfilial=new.codfilial
    into contribipi;

    if(contribipi = 'S') then
    begin
        select pt.vlripiitorc from vdprevtribitorc pt
        where pt.codemp=new.codemp and pt.codfilial=new.codfilial and pt.codorc=new.codorc and pt.tipoorc=new.tipoorc
        and pt.coditorc=new.coditorc
        into vlripi;
    end

    -- Garantindo valor válido para IPI
    if (vlripi is null) then vlripi = 0;

    -- Garantindo valor válido para desconto
    if (new.vlrdescitorc is null) then new.vlrdescitorc = 0;

    -- Garantindo valor válido para vlrliqitorc
    if (new.vlrliqitorc is null) then new.vlrliqitorc = 0;

    select adicfrete from vdorcamento
    where codemp=new.codemp and codfilial=new.codfilial and codorc=new.codorc and tipoorc=new.tipoorc
    into adicfrete;

    -- Calculando valor liquido
    if (new.vlrliqitorc = 0) then
    begin
        new.vlrliqitorc = (new.qtditorc * new.precoitorc) - new.vlrdescitorc + vlripi;
    end

    if( adicfrete = 'S' ) then
    begin
       new.vlrliqitorc = new.vlrliqitorc + coalesce(new.vlrfreteitorc,0);
    end

    new.tipoorc = 'O';

    -- Garantindo a gravação da referência do produto.

    if (new.refprod is null) then
    begin
        select p.refprod from eqproduto p
        where p.codprod=new.codprod and p.codemp=new.codemppd and p.codfilial=new.codfilialpd
        into refprod;

        new.refprod = refprod;
    end

    -- Atualizando a tabela de orçamento

    update vdorcamento o set o.vlrdescitorc=o.vlrdescitorc + new.vlrdescitorc,
                             o.vlrprodorc = o.vlrprodorc + new.vlrproditorc,
                             o.vlrliqorc = o.vlrliqorc + new.vlrliqitorc
    where o.codorc=new.codorc and o.tipoorc=new.tipoorc and o.codemp=new.codemp and o.codfilial=new.codfilial;

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGBU
as
    declare variable adicfrete char(1);
    declare variable tipoprod varchar(2);
    declare variable encorcprod char(1);
    declare variable vlripi decimal(15,5);
    declare variable contribipi char(1);

begin
    if ( new.emmanut is null) then
        new.emmanut='N';

    if(old.aprovitorc!=new.aprovitorc and new.aprovitorc='S') then -- Na aprovação do item de orçamento
    begin
        new.statusitorc='OL';
        new.dtaprovitorc=cast('today' as date);

        if((new.qtdaprovitorc is null) or (new.qtdaprovitorc=0)) then
        begin
            new.qtdaprovitorc=new.qtditorc;
        end
    end
    else if(new.cancitorc='S' or new.statusitorc='CA' ) then
    begin
        new.aceiteitorc = 'N';
        new.aprovitorc = 'N';
        new.qtdaprovitorc = 0;
        new.statusitorc = 'CA';
        new.cancitorc = 'S';
    end
    
    new.qtdafatitorc = new.qtditorc - new.qtdfatitorc;
    
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
    begin

        new.dtalt = cast('now' as date);
        new.halt = cast('now' as time);
        new.idusualt = user;
        
        select fi.contribipifilial from sgfilial fi where fi.codemp=new.codemp and fi.codfilial=new.codfilial
        into contribipi;

        if(contribipi = 'S') then
        begin
            select pt.vlripiitorc from vdprevtribitorc pt
            where pt.codemp=new.codemp and pt.codfilial=new.codfilial and pt.codorc=new.codorc and pt.tipoorc=new.tipoorc
            and pt.coditorc=new.coditorc
            into vlripi;
        end

        -- Garantindo valor válido para IPI
        if (vlripi is null) then vlripi = 0;

        if (new.qtdfatitorc>0) then
        begin
           if (new.qtdfatitorc<new.qtditorc) then
           begin
              new.fatitorc = 'P';
           end
           else
           begin
              new.fatitorc = 'S';
           end
        end
        else 
        begin
           new.fatitorc = 'N';
        end

        select adicfrete from vdorcamento
        where codemp=new.codemp and codfilial=new.codfilial and codorc=new.codorc and tipoorc=new.tipoorc
        into adicfrete;

        if (new.vlrproditorc is null) then new.vlrproditorc = 0;
        if (new.vlrdescitorc is null) then new.vlrdescitorc = 0;
        if (new.vlrliqitorc is null) then new.vlrliqitorc = 0;

        new.vlrliqitorc = (new.qtditorc * new.precoitorc) - new.vlrdescitorc + :vlripi;

        if( adicfrete = 'S' ) then
        begin
            new.vlrliqitorc = new.vlrliqitorc + coalesce(new.vlrfreteitorc,0);
        end

        -- Buscando nas preferencias de deve encaminhar orçamento para a produção.
        select coalesce(p1.encorcprod,'N') from sgprefere1 p1
        where p1.codemp=new.codemp and p1.codfilial=new.codfilial
        into :encorcprod;
    
    
        -- Se for produto acabado e encaminhamento pull definido nas preferencias e item for aprovado deverá sinalizar item para produção.
    
        if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' and :encorcprod='S') then
        begin
    
            select tipoprod from eqproduto pd where pd.codemp=new.codemppd and pd.codfilial=new.codfilialpd and pd.codprod=new.codprod
            into :tipoprod;
    
            if(:tipoprod='F') then
            begin
                new.sitproditorc='PE';
            end
    
        end
    
        -- Atualiza status do item, quando produzido
        if( old.sitproditorc!=new.sitproditorc and new.sitproditorc='PD' ) then
        begin
           new.statusitorc = 'OP';
        end
        
    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDASERIETGBIBU
as
    declare variable temserie smallint;
begin
    -- Verifica se numero de série já foi inserido nessa VENDA
    select count(*) from vditvendaserie its
    where its.codemp = new.codemp and its.codfilial=new.codfilial and its.codvenda=new.codvenda
    and its.tipovenda=new.tipovenda and its.codemppd=new.codemppd and its.codfilialpd=new.codfilialpd and its.codprod=new.codprod
    and its.numserie = new.numserie and its.numserie is not null and its.seqitserie!=new.seqitserie
    into temserie;

    if(temserie > 0) then
    begin
        exception vditvendaex04 new.numserie;
    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDASERIETGBU
as
    declare variable temserie smallint;
begin
    -- Atualizando log
    new.DTALT=cast('now' as date);
    new.IDUSUALT=USER;
    new.HALT = cast('now' as time);

    -- Verificando se foi inserido um numero de série
    if(old.numserie is null and new.numserie is not null) then
    begin
        -- Verificando se o número de série informado, já existe.
        select count(*) from eqserie sr
        where sr.codemp = new.codemppd and sr.codfilial = new.codfilialpd and sr.codprod = new.codprod and sr.numserie = new.numserie
        into :temserie;

        -- se o número de série informado não existe, deve inseri-lo;
        if (temserie = 0) then
        begin
            insert into eqserie (codemp, codfilial, codprod, numserie)
            values(new.codemppd, new.codfilialpd, new.codprod, new.numserie);
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGAD
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
  declare variable subtipovenda char(2);
  declare variable estoqtipomovpd char(1);

--  DECLARE VARIABLE V_CHAVE VARCHAR(500);
--  DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

--  V_CHAVE = 'VDITVENDATGAD'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
--  V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
 -- V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

--  EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
--  IF (V_RECURSIVO = 0) THEN
--  BEGIN

    IF ( not ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
    BEGIN
    
      estoqtipomovpd = 'S';
      
      UPDATE VDVENDA SET
         VLRPRODVENDA = VLRPRODVENDA - old.VLRPRODITVENDA,
         VLRBASEICMSVENDA = VLRBASEICMSVENDA - old.VLRBASEICMSITVENDA,
         VLRICMSVENDA = VLRICMSVENDA -old.VLRICMSITVENDA,
         VLRISENTASVENDA = VLRISENTASVENDA - old.VLRISENTASITVENDA,
         VLROUTRASVENDA = VLROUTRASVENDA - old.VLROUTRASITVENDA,
         VLRBASEIPIVENDA = VLRBASEIPIVENDA - old.VLRBASEIPIITVENDA,
         VLRIPIVENDA = VLRIPIVENDA - old.VLRIPIITVENDA,
         VLRLIQVENDA = VLRLIQVENDA - old.VLRLIQITVENDA,
         VLRCOMISVENDA = VLRCOMISVENDA - old.VLRCOMISITVENDA,
         VLRBASEISSVENDA = VLRBASEISSVENDA - old.VLRBASEISSITVENDA,
         VLRISSVENDA = VLRISSVENDA - old.VLRISSITVENDA,
         VLRDESCITVENDA = VLRDESCITVENDA - old.VLRDESCITVENDA,
         VLRBASEICMSSTVENDA = VLRBASEICMSSTVENDA - OLD.vlrbaseicmsstitvenda,
         VLRICMSSTVENDA = VLRICMSSTVENDA - OLD.vlricmsstitvenda,
         VLRBASECOMIS = VLRBASECOMIS - OLD.vlrbasecomisitvenda
         WHERE CODVENDA=old.CODVENDA AND TIPOVENDA=old.TIPOVENDA AND
         CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;
         
      SELECT V.DTEMITVENDA, V.FLAG, V.DOCVENDA,
          V.CODEMPTM, V.CODFILIALTM, V.CODTIPOMOV, V.SUBTIPOVENDA
       FROM VDVENDA V  WHERE V.CODVENDA = old.CODVENDA AND
          V.CODEMP=old.CODEMP AND V.CODFILIAL = old.CODFILIAL AND
          V.TIPOVENDA=old.TIPOVENDA
      INTO :DDTVENDA, :CFLAG, :IDOCVENDA, :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, :subtipovenda;
      
      if ( (subtipovenda is not null) and (subtipovenda='NC') ) then
         estoqtipomovpd = 'N';
         
      EXECUTE PROCEDURE EQMOVPRODIUDSP('D', old.CODEMPPD, old.CODFILIALPD,
         old.CODPROD, old.CODEMPLE, old.CODFILIALLE, old.CODLOTE,
         :ICODEMPTM, :SCODFILIALTM, :ICODTIPOMOV, null, null, null,
         null, null, null, null,
         old.CODEMP, old.CODFILIAL, old.TIPOVENDA, old.CODVENDA, old.CODITVENDA,
          null, null, null, null,null,null, null, null, null,
         old.CODEMPNT, old.CODFILIALNT, old.CODNAT, :DDTVENDA,
         :IDOCVENDA, :CFLAG, old.QTDITVENDA, old.PRECOITVENDA,
         old.CODEMPAX, old.CODFILIALAX, old.CODALMOX, null, :estoqtipomovpd, 'N');
      
    END

--  END
  
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGAI
as
    declare variable dtvenda date ;
    declare variable flag char(1);
    declare variable docvenda integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;
    declare variable preco numeric(15, 5);
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
    declare variable subtipovenda char(2);
    declare variable estoqtipomovpd char(1);

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDITVENDATGAI'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

    estoqtipomovpd = 'S';
    
    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    -- Carregamento de informações do cabeçalho da venda
    select vd.dtemitvenda, vd.flag, vd.docvenda, vd.codemptm, vd.codfilialtm, vd.codtipomov, vd.subtipovenda
        from eqtipomov tm, vdvenda vd
        where
            tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.tipovenda=new.tipovenda and vd.codvenda=new.codvenda
    into :dtvenda, :flag, :docvenda, :codemptm, :codfilialtm, :codtipomov, :subtipovenda;

    if ( (subtipovenda is not null) and (subtipovenda='NC')) then
       estoqtipomovpd='N';
       
    -- Inicializando preco do item
    if (new.qtditvenda != 0 ) then
        preco = new.vlrliqitvenda/new.qtditvenda;
    else
        preco = new.precoitvenda;

    -- Executando movimentação do estoque
    execute procedure eqmovprodiudsp(
        'I', new.codemppd, new.codfilialpd, new.codprod,new.codemple, new.codfilialle, new.codlote, :codemptm, :codfilialtm,
        :codtipomov, null, null, null, null, null, null, null, new.codemp, new.codfilial, new.tipovenda, new.codvenda, new.coditvenda,
        null, null, null, null,null,null,null, null, null, new.codempnt, new.codfilialnt, new.codnat,:dtvenda, :docvenda, :flag, new.qtditvenda, :preco,
        new.codempax, new.codfilialax, new.codalmox, null, :estoqtipomovpd, 'N'
    );

    -- Salvamento de custos no momento da venda
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

        -- Inserindo registro na tabela de custos de item de venda
        insert into vditcustovenda (codemp,codfilial,codvenda,tipovenda,coditvenda,vlrprecoultcp, vlrcustompm, vlrcustopeps)
        values (new.codemp,new.codfilial,new.codvenda,new.tipovenda,new.coditvenda,:custouc,:custompm,:custopeps);

    end

     -- Executa procedure de geração de tabela de vinculo para numeros de serie

    execute procedure vditvendaseriesp('I', new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda, new.codemppd, new.codfilialpd, new.codprod, new.numserietmp, new.qtditvenda);

    -- Inserindo registros na tabela de informações fiscais complementares (LFITVENDA)

    execute procedure lfgeralfitvendasp(new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda);

    -- Inserindo registro na tabela de vinculo entre compras e vendas (devolução)

    if(new.codcompra is not null and new.coditcompra is not null) then
    begin
        insert into cpdevolucao
            (codemp, codfilial, codcompra, coditcompra, qtddev,
             codempvd, codfilialvd, codvenda, tipovenda, coditvenda )
        values
            (new.codempcp, new.codfilialcp, new.codcompra, new.coditcompra,new.qtditvenda,
             new.codemp, new.codfilial,new.codvenda, new.tipovenda, new.coditvenda);
    end

    execute procedure cpgeradevolucaosp (
    new.codempcp,new.codfilialcp, new.codcompra, new.coditcompra, new.codemp, new.codfilial,
    new.codvenda, new.coditvenda, new.tipovenda, new.qtditvenda);

  --END
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGAU
AS
  DECLARE VARIABLE DDTVENDA DATE;
  DECLARE VARIABLE CFLAG CHAR(1);
  DECLARE VARIABLE IDOCVENDA INTEGER;
  DECLARE VARIABLE ICODEMPTM INTEGER;
  DECLARE VARIABLE SCODFILIALTM SMALLINT;
  DECLARE VARIABLE ICODTIPOMOV INTEGER;
  DECLARE VARIABLE CSTATUS CHAR(2);
  DECLARE VARIABLE CTIPOPROD varCHAR(2);
  DECLARE VARIABLE NPRECO NUMERIC(15, 5);
  DECLARE VARIABLE visualizalucr CHAR(1);
  DECLARE VARIABLE custopeps NUMERIC(15, 5);
  DECLARE VARIABLE custompm NUMERIC(15, 5);
  DECLARE VARIABLE custouc NUMERIC(15, 5);
  declare variable subtipovenda char(2);
  declare variable estoqtipomovpd char(1);

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDITVENDATGAU'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN
  
    estoqtipomovpd = 'S';
  
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
      into :visualizalucr;

    IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) ) THEN
    BEGIN
    
      SELECT VD.DTEMITVENDA,VD.DOCVENDA,VD.STATUSVENDA,VD.FLAG,
             VD.CODEMPTM, VD.CODFILIALTM, VD.CODTIPOMOV, VD.SUBTIPOVENDA
      FROM VDVENDA VD
      WHERE VD.CODVENDA = new.CODVENDA AND VD.TIPOVENDA = new.TIPOVENDA
            AND VD.CODEMP=new.CODEMP AND VD.CODFILIAL = new.CODFILIAL
      INTO :DDTVENDA, :IDOCVENDA, :CSTATUS, :CFLAG, :ICODEMPTM,
          :SCODFILIALTM, :ICODTIPOMOV, :SUBTIPOVENDA;
          
      if ( (subtipovenda is not null) and (subtipovenda='NC') ) then
          estoqtipomovpd = 'N';
          
      SELECT TIPOPROD FROM EQPRODUTO WHERE CODPROD=old.CODPROD
             AND CODEMP=old.CODEMPPD AND CODFILIAL = old.CODFILIALPD
         INTO CTIPOPROD;
      if (SUBSTRING(:CSTATUS FROM 1 FOR 1)!='C') then
      BEGIN
        UPDATE VDVENDA SET VLRDESCITVENDA = VLRDESCITVENDA -old.VLRDESCITVENDA + new.VLRDESCITVENDA,
               VLRPRODVENDA = VLRPRODVENDA - old.VLRPRODITVENDA + new.VLRPRODITVENDA,
               VLRBASEICMSVENDA = VLRBASEICMSVENDA - old.VLRBASEICMSITVENDA + new.VLRBASEICMSITVENDA,
               VLRICMSVENDA = VLRICMSVENDA -old.VLRICMSITVENDA + new.VLRICMSITVENDA,
               VLRISENTASVENDA = VLRISENTASVENDA - old.VLRISENTASITVENDA + new.VLRISENTASITVENDA,
               VLROUTRASVENDA = VLROUTRASVENDA - old.VLROUTRASITVENDA + new.VLROUTRASITVENDA,
               VLRBASEIPIVENDA = VLRBASEIPIVENDA - old.VLRBASEIPIITVENDA + new.VLRBASEIPIITVENDA,
               VLRIPIVENDA = VLRIPIVENDA - old.VLRIPIITVENDA + new.VLRIPIITVENDA,
               VLRLIQVENDA = VLRLIQVENDA - old.VLRLIQITVENDA + new.VLRLIQITVENDA,
               VLRCOMISVENDA = VLRCOMISVENDA - old.VLRCOMISITVENDA + new.VLRCOMISITVENDA,
               VLRBASEISSVENDA = VLRBASEISSVENDA - old.VLRBASEISSITVENDA + new.VLRBASEISSITVENDA,
               VLRISSVENDA = VLRISSVENDA - old.VLRISSITVENDA + new.VLRISSITVENDA,
               VLRBASEICMSSTVENDA = VLRBASEICMSSTVENDA - OLD.vlrbaseicmsstitvenda + NEW.vlrbaseicmsstitvenda,
               VLRICMSSTVENDA = VLRICMSSTVENDA - OLD.vlricmsstitvenda + NEW.vlricmsstitvenda,
               vlrbasecomis = vlrbasecomis - old.vlrbasecomisitvenda + new.vlrbasecomisitvenda
               WHERE CODVENDA=new.CODVENDA AND TIPOVENDA=new.TIPOVENDA
               AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
        
        IF (SUBSTRING(CSTATUS FROM 1 FOR 1) = 'P') THEN
          CSTATUS = 'PV';
        ELSE IF (SUBSTRING(CSTATUS FROM 1 FOR 1) = 'V') THEN
          CSTATUS = 'VD';
      END
      IF (new.QTDITVENDA = 0) THEN
         NPRECO = new.PRECOITVENDA;
      ELSE
         NPRECO = new.VLRLIQITVENDA/new.QTDITVENDA;
      EXECUTE PROCEDURE EQMOVPRODIUDSP('U',new.CODEMPPD, new.CODFILIALPD, new.CODPROD,
        new.CODEMPLE, new.CODFILIALLE, new.CODLOTE, :ICODEMPTM,
        :SCODFILIALTM, :ICODTIPOMOV, null, null, null ,null, null,
        null, null, new.CODEMP, new.CODFILIAL, new.TIPOVENDA, new.CODVENDA,
        new.CODITVENDA, null, null, null, null,null,null,null, null, null,
        new.CODEMPNT, new.CODFILIALNT,
        new.CODNAT, :DDTVENDA, :IDOCVENDA, :CFLAG, new.QTDITVENDA, :NPRECO,
        new.CODEMPAX, new.CODFILIALAX, new.CODALMOX, null, :estoqtipomovpd, 'N');


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

            -- Atualizando registro na tabela de custos de item de venda

            update vditcustovenda icv set vlrprecoultcp=:custouc, vlrcustompm=:custompm, vlrcustopeps=:custopeps
                where icv.codemp=new.codemp and icv.codfilial=new.codfilial and icv.codvenda=new.codvenda
                and icv.tipovenda=new.tipovenda and icv.coditvenda=new.coditvenda;

       end

        -- Executa procedure para atualização de tabela de vinculo para numeros de serie
        execute procedure vditvendaseriesp('U', old.codemp, old.codfilial, old.codvenda, old.tipovenda, old.coditvenda, old.codemppd, old.codfilialpd, old.codprod, new.numserietmp, new.qtditvenda);


        -- Atualizando registros na tabela de informações fiscais complementares (LFITVENDA)
        if(new.coditfisc is not null) then
        begin
            execute procedure lfgeralfitvendasp(
            new.codemp, new.codfilial, new.codvenda, new.tipovenda, new.coditvenda);
        end

        -- Atualizando informações referentes à devolução
        execute procedure cpgeradevolucaosp (
        new.codempcp,new.codfilialcp, new.codcompra, new.coditcompra, new.codemp, new.codfilial,
        new.codvenda, new.coditvenda, new.tipovenda, new.qtditvenda);


    END

  --END
  
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGBD
AS
    declare variable codfilial smallint;
    declare variable fatorcparc char(1);

--  DECLARE VARIABLE V_CHAVE VARCHAR(500);
--  DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

--  V_CHAVE = 'VDITVENDATGBD'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
--  V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

--  EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
--  IF (V_RECURSIVO = 0) THEN
--  BEGIN

    select icodfilial from sgretfilial(old.codemp,'SGPREFERE1') into :codfilial; 
    select fatorcparc from sgprefere1 p 
       where p.codemp=old.codemp and p.codfilial=:codfilial
    into :fatorcparc; 
    
    -- Excluindo vínculos com o orçamento;
    if (fatorcparc='N') then
    begin
       delete from vdvendaorc
       where codemp=old.codemp and codfilial=old.codfilial and tipovenda=old.tipovenda and codvenda=old.codvenda and coditvenda=old.coditvenda;
    end

    -- Executa procedure para exclusão de tabela de vinculo para numeros de serie
    execute procedure vditvendaseriesp('D', old.codemp, old.codfilial, old.codvenda, old.tipovenda, old.coditvenda, old.codemppd, old.codfilialpd, old.codprod, null, old.qtditvenda);

  --END
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGBI
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable precocomisprod numeric(15,5);
    declare variable redfisc numeric(9,2);
    declare variable redbasest char(1);
    declare variable ufcli char(2);
    declare variable ufemp char(2);
    declare variable percicmscm numeric(9,2);
    declare variable codempcl integer;
    declare variable codfilialcl smallint;
    declare variable codcli integer;
    declare variable codemptm integer;
    declare variable codfilialtm smallint;
    declare variable codtipomov integer;

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDITVENDATGBI'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

        -- Inicializando campos nulos.
        if (new.vlrdescitvenda is null) then new.vlrdescitvenda = 0;
        if (new.vlrbaseicmsitvenda is null) then new.vlrbaseicmsitvenda = 0;
        if (new.vlricmsitvenda is null) then new.vlricmsitvenda = 0;
        if (new.vlrbaseipiitvenda is null) then new.vlrbaseipiitvenda = 0;
        if (new.vlripiitvenda is null) then new.vlripiitvenda = 0;
        if (new.vlrliqitvenda is null) then new.vlrliqitvenda = 0;
        if (new.vlrcomisitvenda is null) then new.vlrcomisitvenda = 0;
        if (new.vlradicitvenda is null) then new.vlradicitvenda = 0;
        if (new.vlrissitvenda is null) then new.vlrissitvenda = 0;
        if (new.vlrfreteitvenda is null) then new.vlrfreteitvenda = 0;
        if (new.tipovenda is null) then new.tipovenda = 'V';
        if (new.vlrbaseicmsstitvenda is null) then new.vlrbaseicmsstitvenda = 0;
        if (new.vlrbaseicmsstretitvenda is null) then new.vlrbaseicmsstretitvenda = 0;
        if (new.vlricmsstitvenda is null) then new.vlricmsstitvenda = 0;
        if (new.vlricmsstretitvenda is null) then new.vlricmsstretitvenda = 0;
        if (new.vlrbasecomisitvenda is null) then new.vlrbasecomisitvenda = 0;
        
        if (new.tipovenda='E') then
        begin
           --Busca código do cliente e tipo de movimento para utilizar na procedure lfbuscafiscal.
           select vd.codempcl, vd.codfilialcl, vd.codcli, vd.codemptm,  vd.codfilialtm, vd.codtipomov from vdvenda vd
               where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.tipovenda=new.tipovenda and vd.codvenda=new.codvenda
               into :codempcl, :codfilialcl, :codcli, :codemptm,  :codfilialtm, :codtipomov;
                
           select  codempif, codfilialif, codfisc, coditfisc from lfbuscafiscalsp(new.codemppd,new.codfilialpd,new.codprod,:codempcl,:codfilialcl,:codcli,
               :codemptm,  :codfilialtm, :codtipomov, 'VD',
               new.codnat, null, null, null, null)  v
               into new.codempif, new.codfilialif, new.codfisc, new.coditfisc;
               
        end 
        

        -- Calculando valor liquido do ítem quando zerado.
        if (new.vlrliqitvenda = 0) then
            new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) +
                new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;

        -- Carregando almoxarifado padrão do produto
        if (new.codalmox is null) then
            select codempax, codfilialax, codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
            into new.codempax, new.codfilialax,new.codalmox;

        -- Acertando o codigo de empresa e filial da mensagem, caso a mensagem seja informada.
        if (not new.codmens is null) then
        begin
            select icodfilial from sgretfilial(new.codemp,'LFMENSAGEM') into new.codfilialme;
            new.codempme = new.codemp;
        end

        -- Buscando referência do produto
        select p.refprod, p.tipoprod, p.precocomisprod from eqproduto p
            where p.codemp=new.codemppd and p.codfilial = new.codfilialpd and p.codprod=new.codprod
        into srefprod, stipoprod, precocomisprod;

        -- Acertando referência quando nula
        if (new.refprod is null) then new.refprod = srefprod;

          
        -- Se o item vendido seja um SERVIÇO (Calculo do ISS);
        if (stipoprod = 'S') then
        begin
            -- Carregando aliquota do ISS
            select first 1 coalesce(c.aliqissfisc, f.percissfilial)
            from sgfilial f
            left outer join lfitclfiscal c on
            c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
            where f.codemp=new.codemp and f.codfilial=new.codfilial
            into new.percissitvenda;

            -- Calculando e computando base e valor do ISS;
            if (new.percissitvenda != 0) then
            begin
                new.vlrbaseissitvenda = new.vlrliqitvenda;
                new.vlrissitvenda = new.vlrbaseissitvenda*(new.percissitvenda/100);
            end
        end
        else -- Se o item vendido não for SERVIÇO zera ISS
            new.vlrbaseissitvenda = 0;

        -- Se produto for isento de ICMS
        if (new.tipofisc = 'II') then
        begin
            new.vlrisentasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlroutrasitvenda = 0;
        end
        -- Se produto for de Substituição Tributária
        else if (new.tipofisc = 'FF') then
        begin

           if (new.tipost = 'SI' ) then -- Contribuinte Substituído
                begin
                    new.vlroutrasitvenda = new.vlrliqitvenda;
                    new.vlrbaseicmsitvenda = 0;
                    new.percicmsitvenda = 0;
                    new.vlricmsitvenda = 0;
                    new.vlrisentasitvenda = 0;

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end


                        if(redfisc>0 and redbasest='S') then
                        begin
    
                            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
    
                            new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                            new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));
    
                        end
                        else
                        begin
    
                            -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                            new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                            new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));
    
                        end
                     end



            
            else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
            begin
                percicmscm = 0.00;

                -- Buscando estado do cliente

                select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                into ufcli;

                -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                select coalesce(ic.aliqfiscintra,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S'), coalesce(ic.aliqicmsstcm,0.00) from lfitclfiscal ic
                where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                into percicmsst, redfisc, redbasest, percicmscm  ;
                -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                if (percicmsst = 0) then
                begin
                    select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                    into PERCICMSST;
                end
                if(new.calcstcm = 'N') then
                begin

                    if(redfisc>0 and redbasest='S') then
                    begin
                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
    
                    end
                    else
                    begin
                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                    end
                    new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;
                 end
                 else
                 begin
                    if(redfisc>0 and redbasest='S') then
                    begin

                         -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                         new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                    end
                    else
                    begin
                         new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsbrutitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                    end
                         new.vlroutrasitvenda = 0;
                         new.vlrisentasitvenda = 0;
                         new.vlricmsstitvenda =  ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100)  );
                 end
        end
        end
        -- Se produto não for tributado e não isento
        else if (new.tipofisc = 'NN') then
        begin
            new.vlroutrasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlrisentasitvenda = 0;
        end
        -- Se produto for tributado integralmente
        else if (new.tipofisc = 'TT') then
        begin
            new.vlroutrasitvenda = 0;
            new.vlrisentasitvenda = 0;
        end

        -- Gerando preço especial para comissionamento
        if(precocomisprod is not null) then
        begin

            new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

        end

        -- Atualização de totais no cabeçalho da venda

        update vdvenda vd set vd.vlrdescitvenda = vd.vlrdescitvenda + new.vlrdescitvenda,
            vd.vlrprodvenda = vd.vlrprodvenda + new.vlrproditvenda,
            vd.vlrbaseicmsvenda = vd.vlrbaseicmsvenda + new.vlrbaseicmsitvenda,
            vd.vlricmsvenda = vd.VLRICMSVENDA + new.vlricmsitvenda,
            vd.vlrisentasvenda = vd.VLRISENTASVENDA + new.vlrisentasitvenda,
            vd.vlroutrasvenda = vd.VLROUTRASVENDA + new.vlroutrasitvenda,
            vd.vlrbaseipivenda = vd.VLRBASEIPIVENDA + new.vlrbaseipiitvenda,
            vd.vlripivenda = vd.VLRIPIVENDA + new.vlripiitvenda,
            vd.vlrliqvenda = vd.VLRLIQVENDA + new.vlrliqitvenda,
            vd.vlrbaseissvenda = vd.VLRBASEISSVENDA + new.vlrbaseissitvenda,
            vd.vlrissvenda = vd.VLRISSVENDA + new.vlrissitvenda,
            vd.vlrcomisvenda = vd.VLRCOMISVENDA + new.vlrcomisitvenda,
            vd.vlrbaseicmsstvenda = coalesce(vd.vlrbaseicmsstvenda,0) + new.vlrbaseicmsstitvenda,
            vd.vlricmsstvenda = coalesce(vd.vlricmsstvenda,0) + new.vlricmsstitvenda,
            vd.vlrbasecomis = coalesce(vd.vlrbasecomis, 0) + new.vlrbasecomisitvenda
         where vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial;


  --END

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGBU
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable ntmp numeric(15, 5);
    declare variable precocomisprod numeric(15, 5);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable ufcli char(2);
    declare variable ufemp char(2);
    declare variable redbasest char(1);
    declare variable redfisc numeric(9,2);
    declare variable codfilialpf smallint;
    declare variable fatorcparc char(1);
    declare variable percicmscm numeric(9,2);

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDITVENDATGBU'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODITVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

        percicmscm = 0.00;


        -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
        if ( new.emmanut is null) then
            new.emmanut='N';

        if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
        begin

            -- Computando campos de log
            new.dtalt=cast('now' as date);
            new.idusualt=user;
            new.halt = cast('now' as time);

            -- Verifica se o produto foi alterado
            if (new.codprod != old.codprod) then
                exception vditvendaex01;

            -- Verifica se o lote foi alterado
            if (new.codlote != old.codlote) then
                exception vditvendaex02;

            -- Verifica se o código do almoxarifa está nuloo, se estiver carrega o almoxarifado padrão do produto
            if (new.codalmox is null) then
            begin
                select codempax,codfilialax,codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
                into new.codempax, new.codfilialax,new.codalmox;
            end

            -- Verifica se o almoxarifado anterior estava nulo, se não informa que não é pode ser trocado
            if (old.codalmox is not null) then
            begin
                if (old.codalmox != new.codalmox) then
                    exception eqalmox01;
            end

            -- Carrega a referencia e tipo do produto
            select refprod, tipoprod, precocomisprod
            from eqproduto where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
            into srefprod, stipoprod, precocomisprod;

            if (new.refprod is null) then new.refprod = srefprod;

            -- Caso a nota tenha sido cancelada
            if ( (new.cancitvenda is not null) and (new.cancitvenda = 'S') ) then
            begin
                new.qtditvenda = 0;
                new.vlrliqitvenda = 0;
                new.vlrproditvenda = 0;
                new.vlrbaseicmsitvenda = 0;
                new.vlrbaseipiitvenda = 0;
                new.vlrdescitvenda = 0;
            end

            -- Calculando o valor liquido o ítem
            if ( (new.vlrliqitvenda = 0) and ( (new.cancitvenda is null) or (new.cancitvenda!='S') ) ) then
            begin
                new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) + new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;
            end
            else if (new.vlrdescitvenda > 0 and new.qtditvenda > 0) then
            begin
                ntmp = new.vlrliqitvenda/new.qtditvenda;
                ntmp = ntmp * new.qtditvenda;
                new.vlrdescitvenda = new.vlrdescitvenda + (new.vlrliqitvenda-ntmp);

                -- Recalculando comissão sobre o ítem
                new.vlrcomisitvenda = (new.perccomisitvenda * new.vlrliqitvenda ) / 100;
            end
            if ( (old.qtditvenda<>new.qtditvenda) and (new.qtditvenda<>0) ) then
            begin
               select icodfilial from sgretfilial(old.codemp,'SGPREFERE1') into :codfilialpf; 
               select fatorcparc from sgprefere1 p 
                    where p.codemp=old.codemp and p.codfilial=:codfilialpf
                 into :fatorcparc;
               if (fatorcparc='S') then
               begin
                  exception vditvendaex05;
               end 
            end
            -- Ser for um serviço
            if (stipoprod = 'S') then
            begin
                -- Calculo do ISS
                select first 1 coalesce(c.aliqissfisc, f.percissfilial)
                from sgfilial f
                left outer join lfitclfiscal c on
                c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
                where f.codemp=new.codemp and f.codfilial=new.codfilial
                into new.percissitvenda;

                if (new.percissitvenda != 0) then
                begin
                    new.vlrbaseissitvenda = new.vlrliqitvenda;
                    new.vlrissitvenda = new.vlrbaseissitvenda * (new.percissitvenda/100);
                end
            end
            else
                new.vlrbaseissitvenda = 0;

            -- ìtem isento
            if (new.tipofisc = 'II') then
            begin
                new.vlrisentasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlroutrasitvenda = 0;
            end
            -- Item com substituição tributária
            else if (new.tipofisc = 'FF') then
            begin
                if (new.tipost = 'SI' ) then -- Contribuinte Substituído
                begin
                    new.vlroutrasitvenda = new.vlrliqitvenda;
                    new.vlrbaseicmsitvenda = 0;
                    new.percicmsitvenda = 0;
                    new.vlricmsitvenda = 0;
                    new.vlrisentasitvenda = 0;

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest ;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end

                    if(redfisc>0 and redbasest='S') then
                    begin

                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro

                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
                    else
                    begin

                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
    

                end
                else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
                begin
                     -- Buscando estado do cliente
                    select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    into ufcli;

                   -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0), ic.redbasest, ic.redfisc, coalesce(ic.aliqicmsstcm,0.00) from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into PERCICMSST,redbasest, redfisc, percicmscm;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                        into PERCICMSST;
                    end

                    new.vlroutrasitvenda = 0;
                    new.VLRISENTASITVENDA = 0;

                   if(new.calcstcm = 'N') then
                    begin
                        
                        if(redfisc>0 and redbasest='S') then
                        begin
                            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                            new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
        
                        end
                        else
                        begin
                            -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                            new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                        end
        
                        --new.vlroutrasitvenda = 0;
                        --new.vlrisentasitvenda = 0;
                        new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;
                     end
                     else
                     begin
                         
                        if(redfisc>0 and redbasest='S') then
                        begin
    
                             -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                             new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(:percicmscm,0)/100) ))/(:PERCICMSST/100);
                        end
                        else
                        begin
                             new.vlrbaseicmsstitvenda =   (new.vlricmsitvenda + ( (coalesce(new.vlrbaseicmsbrutitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100) ))/(:PERCICMSST/100);
                        end
                        new.vlroutrasitvenda = 0;
                        new.vlrisentasitvenda = 0;
                        new.vlricmsstitvenda =  ( (coalesce(new.vlrbaseicmsitvenda,0) + coalesce(new.vlripiitvenda,0) ) * (coalesce(percicmscm,0)/100)  );
                     end
                 end
            end
            -- Não insidencia
            else if (new.tipofisc = 'NN') then
            begin
                new.vlroutrasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
            -- Tributado integralmente
            else if (new.tipofisc = 'TT') then
            begin
                new.vlroutrasitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
        end


       -- Atualizando preço especial para comissionamento
       if(precocomisprod is not null) then
       begin

           new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

       end

  --END

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDORCAMENTOTGAI
AS
DECLARE VARIABLE iCodFilialTb SMALLINT;
DECLARE VARIABLE iCodFilialSt SMALLINT;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'VDSTATUSORC') INTO :iCodFilialSt;
  SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGTABELA') INTO :iCodFilialTb;
  INSERT INTO VDSTATUSORC (CODEMP,CODFILIAL,TIPOORC,CODORC,CODEMPTB,CODFILIALTB,CODTB,CODITTB,MOTIVOSTORC)
     SELECT new.CODEMP, new.CODFILIAL, new.TIPOORC, new.CODORC, new.CODEMP,
         :iCodFilialTb, OTB.CODTB, ITB.CODITTB,'SITUAÇÃO INICIAL'
      FROM SGOBJETOTB OTB,SGITTABELA ITB WHERE OTB.CODEMP=new.CODEMP AND OTB.CODFILIALTB=:iCodFilialTb AND
        OTB.IDOBJ='VDSTATUSORC' AND ITB.CODEMP=new.CODEMP AND ITB.CODFILIAL=OTB.CODFILIALTB AND
        ITB.CODTB=OTB.CODTB AND ITB.PADRAOITTB='S';
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDORCAMENTOTGAU
as

    declare variable coditorc integer;
    declare variable percitfrete numeric(15, 5);
    declare variable vlritfrete numeric(15, 5);
    declare variable vlrproditorc numeric(15, 5);

begin

    for select io.coditorc, io.vlrproditorc
    from vditorcamento io
    where io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and io.tipoorc=new.tipoorc
    into :coditorc, :vlrproditorc
    do
    begin

        -- distribuição do frete
        if ( (new.vlrfreteorc != old.vlrfreteorc) or (new.adicfrete!=old.adicfrete)  ) then
        begin
           percitfrete = :vlrproditorc / new.vlrprodorc ;
           vlritfrete =  :percitfrete * new.vlrfreteorc ;


           -- atualizando tabela de ítens
            update vditorcamento io set
            io.vlrfreteitorc = :vlritfrete
            where
            io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and
            io.coditorc=:coditorc and io.tipoorc=new.tipoorc;
        end

        -- Atualização do STATUS desde que o orçamento não tenha sido faturado parcialmente.
        if(old.statusorc != new.statusorc) then
        begin
            update vditorcamento io set io.statusitorc=new.statusorc
            where io.codemp=new.codemp and io.codfilial=new.codfilial
            and io.codorc=new.codorc and io.tipoorc=new.tipoorc
            and io.statusitorc!=new.statusorc;
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDORCAMENTOTGBU
as

    declare variable codatendo integer;
    declare variable codsetor integer;
    declare variable filialpref integer;
    declare variable filialusu integer;
    declare variable vlradic numeric(15,5);

begin

    if (new.emmanut is null) then
        new.emmanut = 'N';

    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null)) ) ) then
    begin
        new.dtalt = cast('now' as date);
        new.idusualt = user;
        new.halt = cast('now' as time);

        if(new.codcli is null and new.codconv is not null) then
        begin

            select codcli, codempcl, codfilialcl, codtpconv, codfilialtc, codemptc
            from atconveniado
            where codemp=new.codempcv and codfilial=new.codfilialcv and codconv=new.codconv into
            new.codcli, new.codempcl, new.codfilialcl, new.codtpconv, new.codfilialtc, new.codemptc;

        end

        if ( (not new.codconv = old.codconv) and (new.codconv is not null) ) then
        begin
            select codcli, codempcl, codfilialcl
            from atconveniado
            where codemp=new.codempcv and codfilial=new.codfilialcv and codconv=new.codconv into
            new.codcli, new.codempcl, new.codfilialcl;
        end

        if (old.statusorc = 'OA' and new.statusorc = 'OC') then
        begin
            select icodfilial from sgretfilial(new.codemp,'SGPREFERE2') into filialpref;
            select icodfilial from sgretfilial(new.codemp,'SGUSUARIO') into filialusu;
            select codsetat2, codtpatendo3 from sgprefere2 where codemp=new.codemp and codfilial=:filialpref into
            codsetor,codatendo;

            if(new.codconv is not null) then
            begin
                execute procedure atadicatendimentosp(new.codconv,:codatendo,new.codatend, codsetor, null, new.codemp, :filialusu, new.codorc );
            end

        end

        if (old.statusorc!='OL' and new.statusorc = 'OL') then -- Caso orçamento tenha sido aprovado
        begin
           new.dtaprovorc = cast('today' as date);
        end
        
        execute procedure vdclienteativosp(new.codempcl, new.codfilialcl, new.codcli);

        if (new.vlrdescorc is null) then
            new.vlrdescorc = 0;

        new.vlrdescorc = new.vlrdescorc - old.vlrdescitorc;
        new.vlrdescorc = new.vlrdescorc + new.vlrdescitorc;

        vlradic = 0;

        if(new.adicfrete='S' and new.vlrfreteorc is not null and new.vlrfreteorc>0) then
        begin
            vlradic = vlradic + new.vlrfreteorc;
        end

        new.vlrliqorc = new.vlrprodorc - new.vlrdescorc + new.vlradicorc + vlradic;

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDPRECOPRODTGAI
AS
begin
    
    -- Se a origem do preco foi o preço base deve indicar no log do produto que o preço foi processado
    if(new.tipoprecoprod='B') then
    begin
    
        update eqprodutolog set precoproc = 'S'
        where codemp=new.codemp and codfilial=new.codfilial and codprod=new.codprod;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDPRECOPRODTGAU
AS
begin

    -- Se a origem do preco foi o preço base deve indicar no log do produto que o preço foi processado
    if(new.tipoprecoprod='B') then
    begin
    
        update eqprodutolog set precoproc = 'S'
        where codemp=new.codemp and codfilial=new.codfilial and codprod=new.codprod;

    end


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDPRECOPRODTGBU
as
begin
  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);

  --Verifica se o preço foi realmente alterado;
  if(new.precoprod != old.precoprod) then
  begin

     new.precoant = old.precoprod;
     new.dtaltpreco = cast('now' as date);
     new.halt = cast('now' as time);
     new.idusualtpreco=USER;

  end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDROMANEIOTGAI
AS
begin

    -- Atualiza vinculo com expedição

    if(new.ticket is not null) then
    begin

        update eqexpedicao ex set ex.codempro=new.codemp, ex.codfilialro=new.codfilial, ex.codroma=new.codroma
        where ex.codemp=new.codempex and ex.codfilial=new.codfilialex and ex.ticket=new.ticket;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDROMANEIOTGAU
AS
begin
    -- Atualiza vinculo com expedição
    if(new.ticket is not null and old.ticket is null) then
    begin

        update eqexpedicao ex set ex.codempro=new.codemp, ex.codfilialro=new.codfilial, ex.codroma=new.codroma
        where ex.codemp=new.codempex and ex.codfilial=new.codfilialex and ex.ticket=new.ticket;

    end

    if(old.ticket is not null and new.ticket is null) then
    begin

        update eqexpedicao ex set ex.codempro=null, ex.codfilialro=null, ex.codroma=null
        where ex.codemp=old.codempex and ex.codfilial=old.codfilialex and ex.ticket=old.ticket;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDTRANSPTGAU
AS
    declare variable filialpref smallint;
    declare variable geracodunif char(1);

begin
      -- Atualização de descrição do código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S' and new.codunifcod is not null) then
    begin
        update sgunifcod set descunifcod=new.raztran
        where codemp=new.codempuc and codfilial=new.codfilialuc and codunifcod=new.codunifcod;
    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDTRANSPTGBI
AS
    declare variable nomemunictran CHAR(30);
    declare variable geracodunif char(1);
    declare variable filialpref smallint;
    declare variable codunifcod integer;
begin

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(new.codmunic is not null) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemunictran;
        if (nomemunictran is not null) then new.cidtran = nomemunictran;
    end

    if(new.siglauf is not null) then
    begin
        new.uftran=new.siglauf;
    end

    /* Fim da atualização do campo cidade e estado*/

    -- Geração de código unificador (SGUNIFCOD)

    select icodfilial from sgretfilial(new.codemp,'SGPREFERE1') into filialpref;
    select geracodunif from sgprefere1 where codemp=new.codemp and codfilial=:filialpref into :geracodunif;
    
    if(:geracodunif = 'S') then
    begin

        select (coalesce(max(codunifcod),0)+1) from sgunifcod where codemp=new.codemp and codfilial=new.codfilial
        into :codunifcod;

        insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod)
        values (new.codemp, new.codfilial, :codunifcod, 'T', new.raztran);
        new.codempuc=new.codemp;
        new.codfilialuc=new.codfilial;
        new.codunifcod=:codunifcod;

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDTRANSPTGBU
as
    declare variable nomemunictran CHAR(30);
begin
    new.DTALT=cast('now' AS DATE);
    new.IDUSUALT=USER;
    new.HALT = cast('now' AS TIME);

    /*Atualização do campo cidade e estado (temporário para retro-compatibilidade)*/

    if(old.codmunic != new.codmunic) then
    begin
        select substr(mun.nomemunic,1,30) from sgmunicipio mun
            where mun.codpais=new.codpais and mun.siglauf=new.siglauf and mun.codmunic=new.codmunic
                into :nomemunictran;
        if (nomemunictran is not null) then new.cidtran = nomemunictran;
    end

    if(new.siglauf is not null) then
    begin
        new.uftran=new.siglauf;
    end

    /* Fim da atualização do campo cidade */


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDAORCTGAI
AS
    declare variable qtditvenda numeric(15,5);
begin
    -- Inserção de registro de movimentação de numero de série,
    -- para faturamento de seviços de conserto (recmerc/Ordens de serviço)
   -- EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'inicio do vdvendaorctgai '|| cast('now' as time);

    insert into eqmovserie (
        codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
        numserie    , codempvd      , codfilialvd   , codvenda      , coditvenda    , tipovenda  ,
        dtmovserie  , tipomovserie  , docmovserie
    )
    select
        ir.codemp, ir.codfilial, coalesce((select max(codmovserie) + 1 from eqmovserie where codemp=vd.codemp and codfilial=vd.codfilial),1), ir.codemppd, ir.codfilialpd, ir.codprod,
        ir.numserie, new.codemp, new.codfilial, new.codvenda, new.coditvenda, new.tipovenda, vd.dtsaidavenda, -1, vd.docvenda
    from eqitrecmercitositorc ro, eqitrecmerc ir, vdvenda vd, vditvenda iv
    where
        ro.codemp=new.codempor and ro.codfilial=new.codfilialor and ro.codorc=new.codorc and ro.tipoorc=new.tipoorc and ro.coditorc=new.coditorc and
        ir.codemp=ro.codemp and ir.codfilial=ro.codfilial and ir.ticket=ro.ticket and ir.coditrecmerc=ro.coditrecmerc and
        iv.codemp=new.codemp and iv.codfilial=new.codfilial and iv.codvenda=new.codvenda and iv.tipovenda=new.tipovenda and iv.coditvenda=new.coditvenda and
        vd.codemp=iv.codemp and vd.codfilial=iv.codfilial and vd.tipovenda=iv.tipovenda and vd.codvenda=iv.codvenda and
        ir.numserie is not null;

--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após insert no eqmovserie '|| cast('now' as time);

    -- Atualizando status do item de orçamento indicando que o mesmo foi faturado.
    select iv.qtditvenda from vditvenda iv where iv.codemp=new.codemp and iv.codfilial=new.codfilial and 
       iv.tipovenda=new.tipovenda and iv.codvenda=new.codvenda and iv.coditvenda=new.coditvenda
       into :qtditvenda;

--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após select vditvenda '|| cast('now' as time);
       
    update vditorcamento io set
    --io.emmanut='S',
    io.statusitorc='OV', io.qtdfatitorc=coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0)
    where io.codemp=new.codempor and io.codfilial=new.codfilialor and io.codorc=new.codorc and io.tipoorc=new.tipoorc and io.coditorc=new.coditorc
    and (io.statusitorc<>'OV' or io.qtdfatitorc is null or io.qtdfatitorc<>coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0) ) ;

--    update vditorcamento io set io.emmanut='N'
--     where io.codemp=new.codempor and io.codfilial=new.codfilialor and io.codorc=new.codorc and io.tipoorc=new.tipoorc and io.coditorc=new.coditorc
--    and io.emmanut='S'
--    and (io.statusitorc<>'OV' or io.qtdfatitorc is null or io.qtdfatitorc<>coalesce(io.qtdfatitorc,0)+coalesce(:qtditvenda,0) ) ;
--    EXECUTE PROCEDURE sgdebugsp 'vdvendaorctgai, orc.: '||new.codorc, 'após update vditorcamento '|| cast('now' as time);


end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGAI
AS
--  DECLARE VARIABLE V_CHAVE VARCHAR(500);
--  DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

--  V_CHAVE = 'VDVENDATGAI'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
--  V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
-- V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

--  EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
--  IF (V_RECURSIVO = 0) THEN
--  BEGIN

    insert into vdvendacomis
    (codemp,codfilial,codvenda,tipovenda,seqvc,
    codemprc, codfilialrc, codregrcomis, seqitrc,
    percvc)
    select vd.codemp, vd.codfilial, vd.codvenda,vd.tipovenda,irc.seqitrc,
        irc.codemp,irc.codfilial,irc.codregrcomis,irc.seqitrc,irc.perccomisitrc
        from
            vdvenda vd, eqtipomov tm, vditregracomis irc
        where
            tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov
            and irc.codemp=tm.codemprc and irc.codfilial=tm.codfilialrc and irc.codregrcomis=tm.codregrcomis
            and vd.codemp=NEW.codemp and vd.codfilial=NEW.codfilial and vd.codvenda=NEW.codvenda
            and vd.tipovenda=NEW.tipovenda;

--  END

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
    declare variable qtditvenda numeric(15,5);
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
    declare variable vlrretensaoiss numeric(15, 5);
    declare variable icodmodnota integer;

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDVENDATGAU'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

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
                into percred, percipiitvenda, tipofisc, tpredicms, redbasefrete;

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


        -- Verica se deve haver retensão de ISS

        select sum(coalesce(iv.vlrissitvenda,0))
        from vditvenda iv left outer join lfitclfiscal cf on
        cf.codemp=iv.codempif and cf.codfilial=iv.codfilialif and cf.codfisc=iv.codfisc and cf.coditfisc=iv.coditfisc and cf.retensaoiss='S'
        where iv.codemp=new.codemp and iv.codfilial=new.codfilial and iv.codvenda=new.codvenda and iv.tipovenda=new.tipovenda
        and iv.vlrissitvenda>0
        into :vlrretensaoiss;

        if(:vlrretensaoiss is null) then
        begin
            vlrretensaoiss = 0;
        end

        -- De pedido para Venda
        if ((SUBSTRING(old.statusvenda FROM 1 FOR 1) = 'P') and (SUBSTRING(new.statusvenda FROM 1 FOR 1) = 'V' ) or
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
                   new.vlrliqvenda,dtrec, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
           end
           else
           begin
               delete from fnreceber where codvenda = new.codvenda and tipovenda=new.tipovenda and codemp=new.codemp and codfilialva = new.codfilial;
           end
           
           SELECT CODMODNOTA FROM EQTIPOMOV m WHERE m.codemp=new.codemptm and m.codfilial=new.codfilialtm and m.codtipomov=new.codtipomov INTO ICODMODNOTA;
           --execute procedure sgdebugsp 'vdvendatgbu', 'PEGOU O MODELO DE NOTA:'||:ICODMODNOTA;
           IF(:ICODMODNOTA=55) THEN
           BEGIN
             --execute procedure sgdebugsp 'vdvendatgbu', 'ENTROU NO IF MODELO DA NOTA É IGUAL A 55:';
             execute procedure sggeracnfsp('VD', new.codemp, new.codfilial, new.tipovenda, new.codvenda,null,null,null);
           END
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob, 
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
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
                   new.vlrliqvenda,new.dtsaidavenda, new.dtcompvenda, new.vlrcomisvenda,new.docvenda,
                   new.codempbo,new.codfilialbo,new.codbanco,
                   new.codemp,new.codfilial,
                   new.codempcb, new.codfilialcb, new.codcartcob,
                   new.codempca, new.codfilialca, new.numconta,
                   new.flag, new.obsrec, new.vlrbasecomis, :vlrretensaoiss);
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
            and codempva=new.codemp and codfilialva = new.codfilial
            into :nvlrparcrec;

       if (new.vlrliqvenda-:vlrretensaoiss != :nvlrparcrec) then
        begin
            exception vdvendaex06;
        end

      end

      -- Caso a data ou o tipo de movimento tenham sido alterados, deve disparar o trigger da vditvenda
    if ( (new.dtsaidavenda != old.dtsaidavenda) or (new.codtipomov!=old.codtipomov) or ( new.docvenda != old.docvenda )  ) then
    begin
        -- Update necessário para disparar o trigger da tabela vditvenda
        update vditvenda set coditvenda=coditvenda
        where codvenda = old.codvenda and tipovenda = old.tipovenda and codemp=old.codemp and codfilial=old.codfilial;
    end
    else if ((SUBSTRING(new.statusvenda FROM 1 FOR 1)='C') and (SUBSTRING(old.statusvenda FROM 1 FOR 1) in ('P','V'))) then
    begin

        delete from vdvendaorc where codemp=new.codemp and codfilial=new.codfilial and
        codvenda=new.codvenda and tipovenda=new.tipovenda;

        update vditvenda set qtditvendacanc=qtditvenda, qtditvenda=0 where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilial=new.codfilial;

        delete from fnreceber where codvenda=new.codvenda and tipovenda=new.tipovenda and
        codemp=new.codemp and codfilialva=new.codfilial;

      end
      -- Verificação se existem registros na tabela eqmovserie com o mesmo código de venda
      -- caso existam registros, os mesmos devem ser atualizados para o novo documento de venda,
      -- para que sejam listados no módulo GMS Consulta Mov. Série, com o documento correto de saída.
      -- antes desta alteração havia apenas a inserção de registros na tabela eqmovserie com número
      -- de documento de pedido, não sendo atualizado após o pedido se transforme em PV PS PR PD
      if (old.docvenda<>new.docvenda) then 
      begin
         update eqmovserie eqm set eqm.docmovserie=new.docvenda 
            where eqm.codempvd=new.codemp and eqm.codfilialvd=new.codfilial and 
              eqm.tipovenda=new.tipovenda and eqm.codvenda=new.codvenda and eqm.docmovserie<>new.docvenda;
      end

    end

  --END
  
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGBD
AS
  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDVENDATGBD'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(OLD.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(OLD.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

    IF ( not ( ( old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
    BEGIN

        if ( (old.BLOQVENDA IS NOT NULL) AND (old.BLOQVENDA='S') ) then
          EXCEPTION VDVENDAEX05 'ESTA VENDA ESTÁ BLOQUEADA!!!';
        DELETE FROM VDFRETEVD WHERE CODVENDA=old.CODVENDA  AND TIPOVENDA=old.TIPOVENDA
          AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;

    END

  --END

END
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGBI
AS
  DECLARE VARIABLE sTipoMov CHAR(2);
--  DECLARE VARIABLE V_CHAVE VARCHAR(500);
--  DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

--  V_CHAVE = 'VDVENDATGBI'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
--  V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
--  V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

--  EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
--  IF (V_RECURSIVO = 0) THEN
--  BEGIN

    EXECUTE PROCEDURE VDCLIENTEATIVOSP(new.CODEMPCL, new.CODFILIALCL, new.CODCLI);
    /*  EXECUTE PROCEDURE FNLIBCREDSP(new.CODVENDA,new.CODCLI,new.CODEMPCL,new.CODFILIALCL,null); */
    IF (new.VLRPRODVENDA IS NULL) THEN new.VLRPRODVENDA = 0;
    IF (new.VLRDESCVENDA IS NULL) THEN new.VLRDESCVENDA = 0;
    IF (new.VLRDESCITVENDA IS NULL) THEN new.VLRDESCITVENDA = 0;
    IF (new.VLRVENDA IS NULL) THEN new.VLRVENDA = 0;
    IF (new.VLRBASEICMSVENDA IS NULL) THEN new.VLRBASEICMSVENDA = 0;
    IF (new.VLRICMSVENDA IS NULL) THEN new.VLRICMSVENDA = 0;
    IF (new.VLRPISVENDA IS NULL) THEN new.VLRPISVENDA = 0;
    IF (new.VLRIRVENDA IS NULL) THEN new.VLRIRVENDA = 0;
    IF (new.VLRCSOCIALVENDA IS NULL) THEN new.VLRCSOCIALVENDA = 0;
    IF (new.VLRISENTASVENDA IS NULL) THEN new.VLRISENTASVENDA = 0;
    IF (new.VLROUTRASVENDA IS NULL) THEN new.VLROUTRASVENDA = 0;
    IF (new.VLRBASEIPIVENDA IS NULL) THEN new.VLRBASEIPIVENDA = 0;
    IF (new.VLRIPIVENDA IS NULL) THEN new.VLRIPIVENDA = 0;
    IF (new.VLRLIQVENDA IS NULL) THEN new.VLRLIQVENDA = 0;
    IF (new.VLRCOMISVENDA IS NULL) THEN new.VLRCOMISVENDA = 0;
    IF (new.VLRFRETEVENDA IS NULL) THEN new.VLRFRETEVENDA = 0;
    IF (new.VLRADICVENDA IS NULL) THEN new.VLRADICVENDA = 0;
    IF (new.TIPOVENDA IS NULL) THEN new.TIPOVENDA = 'V';
    IF (new.VLRBASEISSVENDA IS NULL) THEN new.VLRBASEISSVENDA = 0;
    IF (new.VLRISSVENDA IS NULL) THEN new.VLRISSVENDA = 0;
    IF (new.VLRPISVENDA IS NULL) THEN new.VLRPISVENDA = 0;
    IF (new.VLRIRVENDA IS NULL) THEN new.VLRIRVENDA = 0;
    IF (new.VLRCSOCIALVENDA IS NULL) THEN new.VLRCSOCIALVENDA = 0;
    IF (new.vlrbasepisvenda IS NULL) THEN new.vlrbasepisvenda = 0;
    IF (new.vlrbasecofinsvenda IS NULL) THEN new.vlrbasecofinsvenda = 0;
    IF (new.vlrpisvenda IS NULL) THEN new.vlrpisvenda = 0;
    IF (new.vlrcofinsvenda IS NULL) THEN new.vlrcofinsvenda = 0;
    IF (new.vlrbasecomis IS NULL) THEN new.vlrbasecomis = 0;
    if (new.sitdoc is null) then new.sitdoc='00';
    if (new.dtcompvenda is null) then new.dtcompvenda=new.dtemitvenda;

    IF (new.CODCAIXA IS NULL) THEN
        SELECT CODTERM FROM SGRETCAIXA INTO new.CODCAIXA;

    SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'PVCAIXA') INTO new.CODFILIALCX;

    new.CODEMPCX = new.CODEMP;

    IF (NOT new.tipovenda = 'E') THEN -- Se for ECF não precisa buscar o DOC, porque o DOC é o numero do cupom.
        SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMPSE,new.CODFILIALSE) INTO new.DocVenda;

    SELECT TIPOMOV,FISCALTIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
         AND CODEMP=new.CODEMP AND CODFILIAL = new.CODFILIALTM INTO sTipoMov,new.FLAG;

    IF ( new.FLAG <> 'S') THEN
    BEGIN
        new.FLAG = 'N';
    END

    if ((new.STATUSVENDA is null) OR (TRIM(new.STATUSVENDA) = '*')) then
    BEGIN
        IF (sTipoMov = 'VD') THEN new.STATUSVENDA = 'V1';
            ELSE new.STATUSVENDA = 'P1';
    END

--  END

END
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDATGBU
AS
  DECLARE VARIABLE ICODFILIAL INTEGER;
  DECLARE VARIABLE ICODITVENDA INTEGER;
  DECLARE VARIABLE iCodTipoMov INTEGER;
  DECLARE VARIABLE sSerie CHAR(4);
  DECLARE VARIABLE credicmssimples CHAR(1);
  DECLARE VARIABLE iCodFilialPref smallint;
  DECLARE VARIABLE dDesc NUMERIC(15, 5);
  DECLARE VARIABLE PERCPISFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCCOFINSFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCIRFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCCSOCIALFILIAL NUMERIC(9,2);
  DECLARE VARIABLE PERCSIMPLESFILIAL NUMERIC(9,2);
  DECLARE VARIABLE VLRPRODITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE QTDITVENDA NUMERIC(9,2);
  DECLARE VARIABLE VLRCOMISITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE VLRDESCITVENDA NUMERIC(15, 5);
  DECLARE VARIABLE PERCCOMISITVENDA NUMERIC(9,2);
  DECLARE VARIABLE SIMPLESFILIAL CHAR(1);
  DECLARE VARIABLE SIMPLESCLI CHAR(1);
  DECLARE VARIABLE PESSOACLI CHAR(1);          
  DECLARE VARIABLE NVLRFRETE NUMERIC(15,5);
  DECLARE VARIABLE CADICFRETEVD CHAR(1);
  DECLARE VARIABLE PERCIT NUMERIC(9,2);
  DECLARE VARIABLE RETENSAOIMP CHAR(1);

  --DECLARE VARIABLE V_CHAVE VARCHAR(500);
  --DECLARE VARIABLE V_RECURSIVO INTEGER;
BEGIN

  --V_CHAVE = 'VDVENDATGBU'; -- Nome do objeto (trigger ou procedure) a controlar recursividade
  --V_CHAVE = V_CHAVE || '_' || IIF(INSERTING, 'INS', IIF(UPDATING, 'UPD', 'DEL')); -- tipo de evento
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.TIPOVENDA AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODFILIAL AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária
  --V_CHAVE = V_CHAVE || '_' || CAST(NEW.CODEMP AS VARCHAR(50)); -- concatenação dos campos que formam a chave primária

  --EXECUTE PROCEDURE PRO_RECURSIVO(:V_CHAVE) RETURNING_VALUES (:V_RECURSIVO);

  /* Verifica se é uma chamada recursiva ou não, se não for continua */
  --IF (V_RECURSIVO = 0) THEN
  --BEGIN

    retensaoimp = 'N';

    IF (new.EMMANUT IS NULL) THEN
      new.EMMANUT='N';

    if (new.BLOQVENDA IS NULL) then
      new.BLOQVENDA='N';

    IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
    BEGIN

      if ( ( (old.BLOQVENDA IS  NULL) OR (old.BLOQVENDA='N') ) AND (new.BLOQVENDA='S') )  then
      begin
          new.DTALT=cast('now' AS DATE);
          new.IDUSUALT=user;
          new.HALT=cast('now' AS TIME);
      end

      IF ( (new.DTCOMPVENDA is null) or (old.DTEMITVENDA<>new.DTEMITVENDA)  ) THEN
         new.DTCOMPVENDA=new.DTEMITVENDA;

      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP, 'SGPREFERE1') INTO iCodFilialPref;

      EXECUTE PROCEDURE VDCLIENTEATIVOSP(new.CODEMPCL, new.CODFILIALCL, new.CODCLI);

      if ( ( (old.BLOQVENDA IS NOT NULL AND old.BLOQVENDA='S') or (new.BLOQVENDA='S') ) and coalesce(old.chavenfevenda,'')=coalesce(new.chavenfevenda,'')) then
         EXCEPTION VDVENDAEX07 'ESTA VENDA ESTÁ BLOQUEADA!!!';

      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=user;
      new.HALT=cast('now' AS TIME);
      SELECT CODFILIALSEL FROM SGCONEXAO WHERE NRCONEXAO=CURRENT_CONNECTION AND
          CONECTADO > 0 INTO ICODFILIAL;
      IF (SUBSTRING(old.STATUSVENDA FROM 1 FOR 1) = 'C' and SUBSTRING(new.STATUSVENDA FROM 1 FOR 1) <> 'C' and old.chavenfevenda=new.chavenfevenda ) THEN
        EXCEPTION VDVENDAEX05;
      IF (SUBSTRING(old.STATUSVENDA FROM 1 FOR 1) = 'D' and SUBSTRING(old.STATUSVENDA FROM 1 FOR 1) <> 'D' and old.chavenfevenda=new.chavenfevenda) THEN
        EXCEPTION VDVENDAEX05 'ESTA VENDA FOI DEVOLVIDA!';
      IF ((SUBSTRING(old.STATUSVENDA FROM 1 FOR 1) = 'P') AND (SUBSTRING(new.STATUSVENDA FROM 1 FOR 1) = 'V' ) AND new.IMPNOTAVENDA = 'N') THEN
      BEGIN
        if ( new.subtipovenda = 'NC' ) then
        begin
             SELECT T2.CODTIPOMOV, T2.SERIE FROM EQTIPOMOV T2, EQTIPOMOV T WHERE T2.CODEMP=T.CODEMPTC
               AND T2.CODFILIAL=T.CODFILIALTC AND T2.CODTIPOMOV = T.CODTIPOMOVTC
               AND T.CODEMP=new.CODEMPTM AND T.CODFILIAL=new.CODFILIALTM AND T.CODTIPOMOV=new.CODTIPOMOV
               INTO :iCodTipoMov, :sSerie;
        end
        if ( ( new.subtipovenda <> 'NC') or (iCodTipoMov is null) ) then 
        begin
             SELECT T2.CODTIPOMOV, T2.SERIE FROM EQTIPOMOV T2, EQTIPOMOV T WHERE T2.CODEMP=T.CODEMPTM
               AND T2.CODFILIAL=T.CODFILIALTM AND T2.CODTIPOMOV = T.CODTIPOMOVTM
               AND T.CODEMP=new.CODEMPTM AND T.CODFILIAL=new.CODFILIALTM AND T.CODTIPOMOV=new.CODTIPOMOV
               INTO :iCodTipoMov, :sSerie;
        end
        IF (iCodTipoMov IS NULL) THEN
          SELECT T.CODTIPOMOV, T.SERIE FROM SGPREFERE1 P, EQTIPOMOV T WHERE P.CODEMPTM=T.CODEMP AND
                 P.CODFILIALTM=T.CODFILIAL AND P.CODTIPOMOV = T.CODTIPOMOV
                 AND P.CODEMP=new.CODEMP AND P.CODFILIAL = :iCodFilialPref INTO :iCodTipoMov, :sSerie;
        new.CODTIPOMOV = :iCodTipoMov;
        new.SERIE = :sSerie;
        IF ( ( not (old.IMPNOTAVENDA = 'S') ) AND ( not (new.IMPNOTAVENDA = 'S') ) ) THEN
        BEGIN
            SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMPSE,new.CODFILIALSE) INTO new.DocVenda;
            new.IMPNOTAVENDA = 'S';
        END
      END
      SELECT FISCALTIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
             AND CODEMP=new.CODEMP AND CODFILIAL = new.codfilialtm INTO new.FLAG;
      IF (new.FLAG<>'S') THEN
        new.FLAG = 'N';
      SELECT VLRFRETEVD, ADICFRETEVD FROM VDFRETEVD WHERE CODVENDA=old.CODVENDA AND TIPOVENDA=old.TIPOVENDA AND ADICFRETEVD = 'S'
             AND CODEMP=old.CODEMP AND CODFILIAL = old.codfilial INTO new.VLRFRETEVENDA, :CADICFRETEVD;
      IF (new.VLRDESCVENDA IS NULL) THEN
        new.VLRDESCVENDA = 0;
      IF (new.VLRDESCITVENDA IS NULL) THEN
        new.VLRDESCITVENDA = 0;
      IF (new.VLRADICVENDA IS NULL) THEN
        new.VLRADICVENDA = 0;
      IF (new.VLRFRETEVENDA IS NULL) THEN
        new.VLRFRETEVENDA = 0;
      IF (new.VLRPRODVENDA IS NULL) THEN
        new.VLRPRODVENDA = 0;
      IF (new.VLRIPIVENDA IS NULL) THEN
        new.VLRIPIVENDA = 0;
      IF (new.VLRBASEICMSVENDA IS NULL) THEN
        new.VLRBASEICMSVENDA = 0;
      IF (new.VLRDESCITVENDA > 0) THEN
        dDesc = new.VLRDESCITVENDA;
      ELSE
        dDesc = new.VLRDESCVENDA;
      IF (new.VLRBASEICMSSTVENDA IS NULL) THEN
        new.VLRBASEICMSSTVENDA = 0;
      IF (new.VLRICMSSTVENDA IS NULL) THEN
        new.VLRICMSSTVENDA = 0;

      SELECT C.SIMPLESCLI,C.PESSOACLI FROM VDCLIENTE C WHERE C.CODCLI=new.CODCLI AND
        C.CODFILIAL=new.CODFILIALCL AND C.CODEMP=new.CODEMPCL INTO SIMPLESCLI,PESSOACLI;
      SELECT SIMPLESFILIAL,PERCPISFILIAL,PERCCOFINSFILIAL,PERCIRFILIAL,PERCCSOCIALFILIAL,coalesce(PERCSIMPLESFILIAL,0)
        FROM SGFILIAL WHERE CODEMP=new.CODEMP AND CODFILIAL=:ICODFILIAL
        INTO SIMPLESFILIAL,PERCPISFILIAL,PERCCOFINSFILIAL,PERCIRFILIAL,PERCCSOCIALFILIAL,PERCSIMPLESFILIAL;
      IF (SIMPLESFILIAL = 'N') THEN
      BEGIN
        new.VLRIRVENDA = (new.vlrliqvenda/100)*PERCIRFILIAL;
        new.VLRCSOCIALVENDA = (new.vlrliqvenda/100)*PERCCSOCIALFILIAL;
      END
      ELSE
      BEGIN
        new.VLRIRVENDA = 0;
        new.VLRCSOCIALVENDA = 0;

        /*Verifica se deve destacar crédito de icms (simples)*/
        select p1.credicmssimples,p1.retensaoimp from sgprefere1 p1 where p1.codemp=new.codemp and p1.codfilial=:icodfilialpref
        into credicmssimples,retensaoimp;

        if(credicmssimples='S') then
        begin
            new.vlricmssimples = (new.vlrprodvenda/100) * percsimplesfilial ;
            new.percicmssimples = percsimplesfilial;
        end

      END
      if (CADICFRETEVD = 'S') then
         NVLRFRETE = new.VLRFRETEVENDA;
      else
         NVLRFRETE = 0;

      new.VLRLIQVENDA = coalesce(new.VLRPRODVENDA,0)
                      - coalesce(dDesc,0)
                      + coalesce(new.VLRADICVENDA,0)
                      + coalesce(:NVLRFRETE,0)
                      + coalesce(new.VLRIPIVENDA,0)
                      + coalesce(new.vlricmsstvenda,0);

      if (SIMPLESCLI = 'N' AND PESSOACLI = 'J' AND RETENSAOIMP = 'S') then
      begin
        new.VLRLIQVENDA =
             cast(
                (coalesce(new.VLRLIQVENDA,0)) -
                (case when new.calcpisvenda='S' then coalesce(new.vlrpisvenda,0) else 0 end) -
                (case when new.calccofinsvenda='S' then coalesce(new.vlrcofinsvenda,0) else 0 end) -
                (case when new.calcirvenda='S' then coalesce(new.vlrirvenda,0) else 0 end) -
                (case when new.calccsocialvenda='S' then coalesce(new.vlrcsocialvenda,0) else 0 end)
            as numeric(15, 5));
      end

      IF ((SUBSTRING(old.STATUSVENDA FROM 1 FOR 1) IN ('P','V')) AND (SUBSTRING(new.STATUSVENDA FROM 1 FOR 1)='C')) THEN
      BEGIN
          new.VLRDESCITVENDA = 0;
          new.VLRPRODVENDA = 0;
          new.VLRBASEICMSVENDA = 0;
          new.VLRICMSVENDA = 0;
          new.VLRISENTASVENDA = 0;
          new.VLROUTRASVENDA = 0;
          new.VLRBASEIPIVENDA = 0;
          new.VLRIPIVENDA = 0;
          new.VLRLIQVENDA = 0;
          new.VLRCOMISVENDA = 0;
          new.VLRISSVENDA = 0;
          new.VLRBASEISSVENDA = 0;
          new.vlrpisvenda = 0;
          new.vlrcofinsvenda = 0;
          new.vlrirvenda = 0;
          new.vlrcsocialvenda =0;
          new.vlrbaseicmsstvenda=0;
          new.vlricmsstvenda=0;
      END
      /**
       COMISSAO
      **/
      IF ((NOT NEW.VLRCOMISVENDA IS NULL) AND
          (NEW.VLRLIQVENDA > 0) AND
          ((NOT NEW.VLRDESCVENDA = OLD.VLRDESCVENDA) OR (NOT NEW.PERCMCOMISVENDA = OLD.PERCMCOMISVENDA))) then
      BEGIN
        /* Distribuindo a comissao: */
        FOR SELECT CODITVENDA,VLRPRODITVENDA,QTDITVENDA,coalesce(VLRCOMISITVENDA,0),coalesce(vlrdescitvenda,0),
            coalesce(iv.perccomisitvenda,0)
            FROM VDITVENDA IV
            WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL AND CODVENDA=new.CODVENDA and tipovenda=new.tipovenda
            INTO ICODITVENDA,VLRPRODITVENDA,QTDITVENDA,VLRCOMISITVENDA,VLRDESCITVENDA,PERCCOMISITVENDA DO
        BEGIN
          /* Calculo do item.: */
          /* INCLUIDO PARA DISTRIBUIR A COMISSAO MENOS O DESCONTO PROPORCIONALMENTE*/
          PERCIT = 0;
          IF (new.VLRPRODVENDA > 0 AND NOT new.VLRDESCITVENDA > 0 AND new.VLRDESCVENDA > 0) THEN
          BEGIN
            PERCIT = (100*VLRPRODITVENDA) / new.VLRPRODVENDA;
            VLRDESCITVENDA = (new.VLRDESCVENDA  * PERCIT) / 100;
          END
          IF (new.VLRPRODVENDA > 0 AND new.PERCMCOMISVENDA > 0) THEN
          BEGIN
            PERCCOMISITVENDA = new.PERCMCOMISVENDA;
            /* Retira.. */
            new.VLRCOMISVENDA = new.VLRCOMISVENDA - VLRCOMISITVENDA;
            /* Atualiza.. */
            VLRCOMISITVENDA = ((VLRPRODITVENDA - VLRDESCITVENDA) * PERCCOMISITVENDA) / 100;
            /* Adiciona.. */
            new.VLRCOMISVENDA = new.VLRCOMISVENDA + VLRCOMISITVENDA;
          END
          ELSE IF (new.PERCMCOMISVENDA=0) then
          BEGIN
              VLRCOMISITVENDA = 0;
              PERCCOMISITVENDA = 0;
              new.VLRCOMISVENDA = 0;
          END
          UPDATE VDITVENDA SET VLRCOMISITVENDA=:VLRCOMISITVENDA,PERCCOMISITVENDA=:PERCCOMISITVENDA
          WHERE CODITVENDA=:ICODITVENDA AND CODVENDA=new.CODVENDA AND TIPOVENDA=new.TIPOVENDA AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
        END
      END
      /* Calcula o percentual medio da comissao */
      ELSE IF (new.PERCMCOMISVENDA = old.PERCMCOMISVENDA AND new.VLRLIQVENDA > 0) THEN
      begin
        -- new.PERCMCOMISVENDA = (new.VLRCOMISVENDA/new.VLRLIQVENDA)*100.000;
        -- Modificado, pois causava divergencia em vendas geradas a partir de orçamentos.
        if ((new.vlrprodvenda-new.vlrdescvenda)>0) then
        begin
          new.PERCMCOMISVENDA = (new.VLRCOMISVENDA/(new.vlrprodvenda-new.vlrdescvenda)) * 100;
        end
        else
        begin
          new.PERCMCOMISVENDA = 0;
        end
      end

      IF (new.STATUSVENDA = 'V4') THEN
      BEGIN
        new.IMPNOTAVENDA = 'S';
        new.STATUSVENDA = 'V3';
      END
      IF ((new.IMPNOTAVENDA = 'S') AND (old.IMPNOTAVENDA = 'S')) THEN
      BEGIN
        new.DOCVENDA = old.DOCVENDA;
      END
    END

    -- Atualizando o status do documento fiscal para 02 - Documento cancelado, quando nota for cancelado pelo sistema.
    IF (SUBSTRING(new.STATUSVENDA FROM 1 FOR 1) = 'C' and new.sitdoc!='02') THEN
    begin
      new.sitdoc = '02';
    end

    if(old.chavenfevenda is null and new.chavenfevenda is not null) then
    begin
      new.emmanut = 'N';
    end

  --END

END
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

/* Alter (CPADICCOMPRAPEDSP) */
ALTER PROCEDURE CPADICCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
DOCCOMPRA INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
CODEMPFR INTEGER,
CODFILIALFR SMALLINT,
CODFOR INTEGER,
CODEMPPG INTEGER,
CODFILIALPG SMALLINT,
CODPLANOPAG INTEGER)
 RETURNS(IRET INTEGER)
 AS
declare variable codempse integer;
declare variable codfilialse smallint;
declare variable serie char(4);
declare variable statuscompra char(2);
begin

    --Buscando a série da compra
    select tm.codempse, tm.codfilialse, tm.serie
    from eqtipomov tm
    where tm.codemp=:codemptm and tm.codfilial=:codfilialtm and tm.codtipomov=:codtipomov
    into :codempse, :codfilialse, :serie;

    --Definição do status da compra
    statuscompra = 'P1';

    --Buscando doccompra
    select doc from lfnovodocsp(:serie, :codempse , :codfilialse) into doccompra;

    insert into cpcompra (
    codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor,
    codempse, codfilialse, serie, codemptm, codfilialtm, codtipomov, doccompra, dtentcompra, dtemitcompra, statuscompra, calctrib )
    values (
    :codemp, :codfilial, :codcompra, :codemppg, :codfilialpg, :codplanopag, :codempfr, :codfilialfr, :codfor,
    :codempse, :codfilialse, :serie, :codemptm, :codfilialtm, :codtipomov, :doccompra,
    cast('today' as date), cast('today' as date), :statuscompra, 'S' );

    iret = :codcompra;

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
TPAGRUP CHAR,
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
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
declare variable refprod varchar(20);
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

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
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
declare variable usaprecocot char(1);
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
declare variable aprovpreco char(1);
declare variable codemppp integer;
declare variable codfilialpp smallint;
declare variable codplanopag integer;
declare variable vlrproditcompra numeric(15,5);
declare variable qtditrecmerc numeric(15,5);
declare variable codempns integer;
declare variable codfilialns smallint;
declare variable numserietmp varchar(30);
declare variable percprecocoletacp numeric(15,5);
declare variable permititemrepcp char(1);
declare variable trocaqtd char(1);
begin
    
    -- Carregamdo variaveis
    aprovpreco = 'N';

    -- Verificando se é para buscar as quantidades
    if ( (qtditcompra is null) or (qtditcompra=0)) then
       trocaqtd = 'S';
    else 
       trocaqtd = 'N';
    
    
    -- Buscando preferências
    select coalesce(p1.usaprecocot,'N') usaprecocot
    from sgprefere1 p1
    where p1.codemp=:codemp and p1.codfilial=:codfilial
    into :usaprecocot;
    
    -- Buscando preferências GMS
    select coalesce(p8.percprecocoletacp,100) percprecocoletacp
    , coalesce(permititemrepcp, 'N') 
    from sgprefere8 p8
    where p8.codemp=:codemp and p8.codfilial=:codfilial
    into :percprecocoletacp, :permititemrepcp;

    -- Buscando informações da compra
    select cp.codfilialtm, cp.codtipomov,
    cp.codempfr, cp.codfilialfr, cp.codfor,
    cp.codemppg, cp.codfilialpg, cp.codplanopag
    from cpcompra cp
    where cp.codemp=:codempcp and cp.codfilial=:codfilialcp and cp.codcompra=:codcompra
    into :codfilialtm,  :codtipomov, :codempfr, :codfilialfr, :codfor, :codemppp, :codfilialpp, :codplanopag;

    for select ir.codemppd, ir.codfilialpd, ir.codprod, ir.coditrecmerc, ir.qtditrecmerc,
        ir.codempns, ir.codfilialns, ir.numserie
        from eqitrecmerc ir
        where
        ir.codemp=:codemp and ir.codfilial=:codfilial and ir.ticket=:ticket
        into :codemppd, :codfilialpd, :codprod, :coditrecmerc, :qtditrecmerc,
        :codempns, :codfilialns, :numserietmp
        do
        begin
            if (:trocaqtd='S') then
                 qtditcompra = 0;  
            if(:permititemrepcp='S' or :codprod <> :codprodant or :codprodant is null) then
            begin

                -- Buscando a natureza da operação
                select codnat from lfbuscanatsp(:codfilial,:codemp,:codfilialpd,:codprod,:codemp,null,null,
                :codempfr,:codfilialfr,:codfor,:codfilialtm,:codtipomov,null)
                into :codnat;

                -- Buscando informações do produto
                select pd.codempax, pd.codfilialax, pd.codalmox from eqproduto pd
                where pd.codemp=:codemppd and pd.codfilial=:codfilialpd and pd.codprod=:codprod
                into :codempax, :codfilialax, :codalmox;

                -- Buscando preço da ultima compra
--                select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
--                cast('today' as date),'U',:codempax, :codfilialax, :codalmox,'N')
--                into :precoultcp;

                -- Se deve buscar preço de cotação.
                if( 'S' = :usaprecocot) then
                begin
                    -- Deve implementar ipi, vlrliq etc... futuramente...
                    select first 1 ct.precocot
                    from cpcotacao ct, cpsolicitacao sl, cpitsolicitacao iso
                    left outer join eqrecmerc rm on
                    rm.codemp=:codemp and rm.codfilial=:codfilial and rm.ticket=:ticket
                    where
                    iso.codemp = sl.codemp and iso.codfilial=sl.codfilial and iso.codsol=sl.codsol
                    and ct.codemp=iso.codemp and ct.codfilial=iso.codfilial and ct.codsol=iso.codsol and ct.coditsol=iso.coditsol
                    and iso.codemppd=:codemppd and iso.codfilialpd=:codfilialpd and iso.codprod=:codprod
                    and ct.codempfr=:codempfr and ct.codfilialfr=:codfilialfr and ct.codfor=:codfor
                    and (ct.dtvalidcot>=cast('today' as date) and (ct.dtcot<=cast('today' as date)))
                    and ct.sititsol not in ('EF','CA')

                    and ( (ct.rendacot = rm.rendaamostragem) or ( coalesce(ct.usarendacot,'N') = 'N') )

                    and ( (ct.codemppp=:codemppp and ct.codfilialpp=:codfilialpp and ct.codplanopag=:codplanopag)
                       or (ct.codplanopag is null))

                    order by ct.dtcot desc
                    into :precoitcompra;

                    if(:precoitcompra is not null) then
                    begin
                        -- Indica que o preço é aprovado (cotado anteriormente);
                        aprovpreco = 'S';
                    end
                end

                -- Se não conseguiu obter o preço das cotações
                if(precoitcompra is null) then
                begin
                    -- Buscando preço de compra da tabela de custos de produtos
                    select custounit from eqcustoprodsp(:codemppd, :codfilialpd, :codprod,
                    cast('today' as date),'M',:codempax, :codfilialax, :codalmox,'N')
                    into :precoitcompra;

                end

                -- verifica se quantidade está zerada (coleta) se estiver preechida (trata-se de uma pesagem)
                if ( (qtditcompra is null) or (qtditcompra = 0) ) then 
                begin
                    qtditcompra = qtditrecmerc;
                end

                if ( ( :percprecocoletacp is not null) and (:percprecocoletacp<>100) ) then
                begin
                   precoitcompra = cast( :precoitcompra / 100 * :percprecocoletacp as decimal(15,5) ); 
                end
                 
                vlrproditcompra = :precoitcompra * qtditcompra;

                -- Inserir itens
                
                insert into cpitcompra (
                codemp, codfilial, codcompra, coditcompra,
                codemppd, codfilialpd, codprod,
                codempnt, codfilialnt, codnat,
                codempax, codfilialax, codalmox,
                qtditcompra, precoitcompra, aprovpreco, vlrproditcompra,
                codempns, codfilialns, numserietmp)
                values (:codempcp, :codfilialcp, :codcompra, :coditrecmerc,
                :codemppd, :codfilialpd, :codprod,
                :codemp,:codfilial,:codnat,
                :codempax,:codfilialax,:codalmox,
                :qtditcompra, :precoitcompra, :aprovpreco, :vlrproditcompra,
                :codempns, :codfilialns,  :numserietmp) ;

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
TIPOVENDA CHAR,
CODVENDA INTEGER,
CITEM CHAR)
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
TIPOVENDA CHAR,
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
                                   :CODEMP,:CODFILIALFR,:CODFOR,:CODFILIALTM,:CODTIPOMOV,null) INTO CODNAT;

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
declare variable refprod varchar(20);
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
    from sgretinfousu(:codemprm, user)
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
                     codempos,codfilialos,ticket,coditrecmerc,motivorma)
                     values
                     (:codemprm,:codfilialrma,:codrma,
                      :codemprm, :codfilialusu1,:idusu1,
                      null,null,null,
                      null,null,null,
                      :codemprm, :codfilialtm1, :codtipomov1,
                      :codemprm,:codfilialccusu1,:anoccusu1,:codccusu1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :codemprm,:codfilialrm,:ticket,:coditrecmerc,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OS:'|| :ticket
    );

    -- Loop nos itens de Ordem de Serviço.

    for select os.coditrecmerc, os.coditos, os.codemppd, os.codfilialpd, os.refprodpd, os.codprodpd, os.qtditos,
    (select ncustompm from eqprodutosp01(os.codemppd, os.codfilialpd, os.codprodpd,null,null,null)) custompmit
    from eqitrecmercitos os
    where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
--    and ( (os.coditrecmerc=:coditrecmerc) or (:coditrecmerc is null) )
    and os.gerarma='S'
    into :coditrecmerc, :coditos, :codemppd, :codfilialpd, :refprod, :codprod, :qtd, :custompmit
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
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            CODEMPOS,CODFILIALOS,TICKET,CODITRECMERC,CODITOS
                            )
                            values
                            (:codemprm,:codfilialrma,:codrma, :coditrma,
                            :codemprm,:codfilialpd,
                            :codprod, :refprod, :qtd, :qtdaprov, 0, :custompmit, :codemprm,
                            :codfilialle,:codlote,
                            :codemprm,:codfilialax,:codalmox,
                            :statusrmager,:statusaprovrmager,'PE',
                            :codemprm, :codfilialrm, :ticket, :coditrecmerc,:coditos
                            );

        update eqitrecmercitos os set os.gerarma='N', os.statusitos='EC'
        where os.codemp=:codemprm and os.codfilial=:codfilialrm and os.ticket=:ticket
        and os.coditrecmerc=:coditrecmerc and os.coditos=:coditos;

        suspend;

   end
end
^

/* Alter (EQGERARMASP) */
ALTER PROCEDURE EQGERARMASP(CODEMPOP INTEGER,
CODFILIALOP INTEGER,
CODOP INTEGER,
SEQOP SMALLINT)
 AS
declare variable seqof smallint;
declare variable idusu1 char(8);
declare variable seqitop integer;
declare variable coditrma integer;
declare variable refprod varchar(20);
declare variable codrma integer;
declare variable codtipomov1 integer;
declare variable codccusu1 char(19);
declare variable codfilialccusu1 smallint;
declare variable codfilialpd smallint;
declare variable codfilialtm1 smallint;
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
declare variable codfilialfase smallint;
declare variable qtdaprov numeric(15,5) = 0;
declare variable codfase integer;
declare variable codempos integer;
declare variable codfilialos smallint;
declare variable ticket integer;
declare variable coditrecmerc integer;
declare variable coditos integer;
begin

    -- Buscando informações do usuário
    select codfilialusu,codfilialccusu,codccusu,anoccusu,idusus
    from sgretinfousu(:codempop, user)
    into :CODFILIALUSU1,:CODFILIALCCUSU1,:CODCCUSU1,:ANOCCUSU1,:IDUSU1;

    -- Buscando preferencias de tipo de movimento para OP
    select codfilialt8,codtipomov8
    from sgprefere1 where codemp=:CODEMPOP and codfilial=(select icodfilial from sgretfilial(:CODEMPOP, 'SGPREFERE1'))
    into :CODFILIALTM1,:CODTIPOMOV1;

    --Buscando informações da OP.
    select op.codempos, op.codfilialos, op.ticket, op.coditrecmerc, op.coditos
    from  ppop op
    where op.codemp=:codempop and op.codfilial=:codfilialop and op.codop=:codop and op.seqop=:seqop
    into :codempos, :codfilialos, :ticket, :coditrecmerc, :coditos;

    -- Buscando preferencias para geração de RMA
    select coalesce(SITRMAOP,'PE') from sgprefere5 where codemp=:CODEMPOP and
    codfilial=(select icodfilial from sgretfilial(:CODEMPOP,'SGPREFERE5'))
    into :STATUSRMAGER;

    QTDAPROV = 0;
    STATUSAPROVRMAGER = 'PE';

    for select codfilialfs,codfase,seqof from ppopfase opf
        where opf.codemp=:CODEMPOP and opf.codfilial=:CODFILIALOP and
        opf.codop=:CODOP and  opf.seqop=:SEQOP and
        (select count(1) from ppitop it
        where it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP and
        it.codempfs=opf.codempfs and it.codfilialfs=opf.codfilialfs and
        it.codfase=opf.codfase and it.gerarma='S' and
        it.codop=:CODOP and it.seqop=:SEQOP) > 0
        into :codfilialfase,:codfase,:SEQOF do
    begin
        select coalesce((max(codrma)+1),1)
        from eqrma
        where codemp=:CODEMPOP and codfilial=:CODFILIALOP into :CODRMA;

        insert into eqrma (codemp,codfilial,codrma,
                     codempuu,codfilialuu,idusu,
                     codempua,codfilialua,idusuaprov,
                     codempue,codfilialue,idusuexp,
                     codemptm,codfilialtm,codtipomov,
                     codempcc,codfilialcc,anocc,codcc,
                     dtareqrma,dtaaprovrma,dtaexprma,
                     sitrma,sitaprovrma,sitexprma,
                     codempof,codfilialof,codop,seqop,seqof,
                     motivorma, codempos, codfilialos, ticket, coditrecmerc)
                     values
                     (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQRMA')),:CODRMA,
                      :CODEMPOP, :CODFILIALUSU1,:IDUSU1,
                      null,null,null,
                      null,null,null,
                      :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQTIPOMOV')),
                      :CODTIPOMOV1,
                      :CODEMPOP,:CODFILIALCCUSU1,:ANOCCUSU1,:CODCCUSU1,
                      cast('now' AS DATE),null,null,
                      :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                      :CODEMPOP,:CODFILIALOP,:CODOP,:SEQOP,:SEQOF,
                      'REQUISIÇÃO GERADA PARA ATENDIMENTO À OP:'||:CODOP||' SEQ:'||:SEQOP||' - FASE:'||:CODFASE,
                      :codempos, :codfilialos, :ticket, :coditrecmerc
        );

        for select it.seqitop,it.codfilialpd,it.codprod,it.refprod,it.qtditop-coalesce(it.qtdcopiaitop,0),it.codfilialle,it.codlote,
            (select ncustompm from eqprodutosp01(it.codemppd,it.codfilialpd,it.codprod,null,null,null)),
            (SELECT CODFILIALAX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod),
            (SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=it.codemppd and codfilial=it.codfilialpd and codprod=it.codprod)
            from ppitop it, eqproduto pd
            where
            pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
            and it.codemp=:CODEMPOP and it.codfilial=:CODFILIALOP
            and it.codop=:CODOP and it.seqop=:SEQOP and it.codempfs=:CODEMPOP
            and it.codfilialfs=:CODFILIALFASE
            and it.codfase = :CODFASE and it.gerarma='S'
            and (('S'=(select ratauto from sgprefere5 where codemp=it.codemp and codfilial=it.codfilial))
            and (it.qtditop-coalesce(it.qtdcopiaitop, 0))<=(SELECT L.SLDLOTE FROM EQLOTE L
                                                            WHERE L.CODEMP=it.codemple AND L.CODFILIAL=it.codfilialle AND
                                                            L.CODPROD=it.codprod AND L.CODLOTE=it.codlote)
            or pd.cloteprod = 'N'
                                                            )
            into :SEQITOP, :CODFILIALPD,:CODPROD,:REFPROD,:QTD,
            :CODFILIALLE,:CODLOTE,:CUSTOMPMIT,:CODFILIALAX,:CODALMOX  DO
        begin
            select coalesce((max(coditrma)+1),1) from eqitrma
            where codemp=:CODEMPOP and codfilial=:CODFILIALOP and
            codrma=:CODRMA into :coditrma;

            if(:STATUSRMAGER='AF')then
            begin
                STATUSAPROVRMAGER = 'AT';
                QTDAPROV = :QTD;
            end

            insert into eqitrma (CODEMP,CODFILIAL,CODRMA,CODITRMA,
                            CODEMPPD,CODFILIALPD,CODPROD,REFPROD,
                            QTDITRMA,QTDAPROVITRMA,QTDEXPITRMA,PRECOITRMA,
                            CODEMPLE,CODFILIALLE,CODLOTE,
                            CODEMPAX,CODFILIALAX,CODALMOX,
                            SITITRMA,SITAPROVITRMA,SITEXPITRMA,
                            codempos, codfilialos, ticket, coditrecmerc, coditos
                            )
                            values
                            (:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQITRMA')),:CODRMA,
                            :coditrma,
                            :CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQPRODUTO')),
                            :CODPROD,:REFPROD,:QTD,:QTDAPROV,0,:CUSTOMPMIT,:CODEMPOP,(SELECT ICODFILIAL FROM sgretfilial(:CODEMPOP,'EQLOTE')),:CODLOTE,
                            :CODEMPOP,:CODFILIALAX,:CODALMOX,
                            :STATUSRMAGER,:STATUSAPROVRMAGER,'PE',
                            :codempop, :codfilialos, :ticket, :coditrecmerc, :coditos
                            );

            update ppitop it set it.gerarma='N' where it.CODEMP=:CODEMPOP AND
                it.CODFILIAL=:CODFILIALOP AND
                it.codop=:CODOP and it.seqop=:SEQOP and it.seqitop=:seqitop;

        end

    end

    suspend;

end
^

/* Alter (EQMOVPRODATEQSP) */
ALTER PROCEDURE EQMOVPRODATEQSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPLEOLD INTEGER,
SCODFILIALLEOLD SMALLINT,
CCODLOTEOLD VARCHAR(20),
ICODEMPLENEW INTEGER,
SCODFILIALLENEW SMALLINT,
CCODLOTENEW VARCHAR(20),
SOPERADOR SMALLINT,
NQTDMOVPRODOLD NUMERIC(15,5),
NQTDMOVPRODNEW NUMERIC(15,5),
ICODEMPAXOLD INTEGER,
SCODFILIALAXOLD SMALLINT,
ICODALMOXOLD INTEGER,
ICODEMPAXNEW INTEGER,
SCODFILIALAXNEW SMALLINT,
ICODALMOXNEW INTEGER)
 AS
DECLARE VARIABLE CCLOTEPROD CHAR(1);
DECLARE VARIABLE ICODALMOX INTEGER;
DECLARE VARIABLE ICODEMPAX INTEGER;
DECLARE VARIABLE SCODFILIALAX SMALLINT;
begin
  /* Procedure de atualização do estoque */
  UPDATE EQPRODUTO SET SLDPROD = SLDPROD+((:NQTDMOVPRODNEW-:NQTDMOVPRODOLD)*:SOPERADOR)
            WHERE CODPROD=:ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL=:SCODFILIALPD;

  SELECT CLOTEPROD, CODALMOX,CODEMPAX,CODFILIALAX FROM EQPRODUTO
     WHERE CODPROD = :ICODPROD AND CODEMP=:ICODEMPPD AND CODFILIAL = :SCODFILIALPD
      INTO :CCLOTEPROD, :ICODALMOX, :ICODEMPAX, :SCODFILIALAX;

  EXECUTE PROCEDURE EQSALDOPRODATEQSP(:ICODEMPPD, :SCODFILIALPD, :ICODPROD, :SOPERADOR,
   :NQTDMOVPRODOLD, :NQTDMOVPRODNEW, :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD,
   :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);

  IF (CCLOTEPROD = 'S') THEN
  BEGIN
    IF (CCODLOTENEW IS NULL) THEN
    BEGIN
      EXCEPTION EQPRODUTOEX01 'ESTE MOVIMENTO PRECISA DE UM LOTE!';
    END
    ELSE
    BEGIN
       EXECUTE PROCEDURE EQSALDOLOTEATEQSP(:ICODEMPLEOLD, :SCODFILIALLEOLD, :ICODPROD, :CCODLOTEOLD,
          :ICODEMPLENEW, :SCODFILIALLENEW, :CCODLOTENEW, :SOPERADOR, :NQTDMOVPRODOLD, :NQTDMOVPRODNEW,
          :ICODEMPAXOLD, :SCODFILIALAXOLD, :ICODALMOXOLD, :ICODEMPAXNEW, :SCODFILIALAXNEW, :ICODALMOXNEW);
    END
  END

  suspend;
end
^

/* Alter (EQMOVPRODDSP) */
ALTER PROCEDURE EQMOVPRODDSP(ICODEMPPD INTEGER,
SCODFILIALPD SMALLINT,
ICODPROD INTEGER,
ICODEMPIV INTEGER,
SCODFILIALIV SMALLINT,
ICODINVPROD INTEGER,
ICODEMPCP INTEGER,
SCODFILIALCP SMALLINT,
ICODCOMPRA INTEGER,
SCODITCOMPRA SMALLINT,
ICODEMPVD INTEGER,
SCODFILIALVD SMALLINT,
CTIPOVENDA CHAR,
ICODVENDA INTEGER,
SCODITVENDA SMALLINT,
ICODEMPRM INTEGER,
SCODFILIALRM SMALLINT,
ICODRMA INTEGER,
SCODITRMA SMALLINT,
ICODEMPOP INTEGER,
SCODFILIALOP SMALLINT,
ICODOP INTEGER,
SSEQOP SMALLINT,
SSEQENTOP SMALLINT,
DDTMOVPROD DATE,
ICODEMPAX INTEGER,
SCODFILIALAX SMALLINT,
ICODALMOX INTEGER,
CMULTIALMOX CHAR,
SEQSUBPROD SMALLINT,
TIPONF CHAR)
 AS
declare variable icodemp integer;
declare variable scodfilial smallint;
declare variable icodmovprod integer;
declare variable nsldmovprod numeric(15,5);
declare variable ncustompmmovprod numeric(15,5);
declare variable nsldmovprodax numeric(15,5);
declare variable ncustompmmovprodax numeric(15,5);
begin
  /* Procedure de deleção da tabela eqmovprod */
  SELECT ICODEMP, SCODFILIAL, ICODMOVPROD
  FROM EQMOVPRODRETCODSP(:ICODEMPIV, :SCODFILIALIV, :ICODINVPROD, :ICODEMPCP,
    :SCODFILIALCP, :ICODCOMPRA, :SCODITCOMPRA, :ICODEMPVD, :SCODFILIALVD,
    :CTIPOVENDA, :ICODVENDA, :SCODITVENDA, :ICODEMPRM, :SCODFILIALRM, :ICODRMA,
    :SCODITRMA, :ICODEMPOP,  :SCODFILIALOP,  :ICODOP, :SSEQOP, :sseqentop, :seqsubprod)
    INTO :ICODEMP, :SCODFILIAL, :ICODMOVPROD;

  SELECT NSALDO, NCUSTOMPM, NSALDOAX, NCUSTOMPMAX
  FROM EQMOVPRODSLDSP(:ICODEMP, :SCODFILIAL, :ICODMOVPROD,
   :ICODEMPPD, :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0, 0,
   :ICODEMPAX, :SCODFILIALAX, :ICODALMOX, :CMULTIALMOX)
     INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX  ;

  /* DELETAR EQMOVPROD */
  DELETE FROM EQMOVPROD WHERE CODEMP=:ICODEMP AND CODFILIAL=:SCODFILIAL
    AND CODMOVPROD=:ICODMOVPROD;

  /* REPROCESSAR ESTOQUE */
  SELECT NSLDPRC, NCUSTOMPMPRC, NSLDPRCAX, NCUSTOMPMPRCAX
    FROM EQMOVPRODPRCSLDSP( :SCODFILIAL, :ICODMOVPROD, :ICODEMPPD,
      :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, null, :NSLDMOVPROD, :NCUSTOMPMMOVPROD,
      :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX, :ICODEMPAX, :SCODFILIALAX, :ICODALMOX,
      :CMULTIALMOX, :TIPONF)
    INTO :NSLDMOVPROD, :NCUSTOMPMMOVPROD, :NSLDMOVPRODAX, :NCUSTOMPMMOVPRODAX;

  /* ATUALIZA CUSTO NO CADASTRO DE PRODUTOS
   OPERADOR 1 PARA EFETUAR A ATUALIZAÇÃO SEMPRE
  EXECUTE PROCEDURE EQMOVPRODATCUSTSP( 1, :ICODEMP, :SCODFILIAL,
   :ICODMOVPROD,  :SCODFILIALPD, :ICODPROD, :DDTMOVPROD, 0);
   */

  suspend;
end
^

/* Alter (FNESTORNACOMISSAOSP) */
ALTER PROCEDURE FNESTORNACOMISSAOSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODREC INTEGER,
NPARCITREC SMALLINT)
 AS
declare variable vlrvendacomi numeric(15,5);
declare variable vlrcomi numeric(15,5);
declare variable datacomi date;
declare variable dtcompcomi date;
declare variable dtvenccomi date;
declare variable tipocomi char(1);
declare variable statuscomi char(2);
declare variable dtatual date;
declare variable dtvencitrec date;
declare variable codempvd integer;
declare variable codfilialvd smallint;
declare variable codvend integer;
begin
  /* Procedure Text */
  dtatual = cast( 'now' as date);

  select first 1 c.statuscomi, c.tipocomi, c.codempvd, c.codfilialvd, c.codvend
  , c.vlrvendacomi, c.vlrcomi, c.datacomi, c.dtcompcomi, ir.dtvencitrec , c.dtvenccomi
  from vdcomissao c, fnitreceber ir
  where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec and tipocomi='R'
  and ir.codemp=c.codemprc and ir.codfilial=c.codfilialrc and ir.codrec=c.codrec and ir.nparcitrec=c.nparcitrec
  and c.statuscomi<>'CE'
  order by c.codcomi desc
  into :statuscomi, :tipocomi, :codempvd, :codfilialvd,  :codvend
  , :vlrvendacomi, :vlrcomi, :datacomi, :dtcompcomi, :dtvencitrec, :dtvenccomi;

  if (statuscomi not in ('CP') ) then
  begin
      update vdcomissao c set c.statuscomi='C1'
      where c.codemprc=:codemp and c.codfilialrc=:codfilial and c.codrec=:codrec and c.nparcitrec=:nparcitrec
      and tipocomi='R' and statuscomi not in ('CP','CE');
  end
  else if (statuscomi in ('CP') ) then
  begin
      vlrcomi = vlrcomi * -1; /* Transforma o valor da comissão em negativo */
      /* para gerar estorno */
      execute procedure vdadiccomissaosp(:codemp,:codfilial,:codrec,
         :nparcitrec, :vlrvendacomi, :vlrcomi, :datacomi , :dtcompcomi, :dtvenccomi,
         :tipocomi, :codempvd, :codfilialvd, : codvend );

     -- execute vdadiccomissaosp
  end

  /*UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
    */
--  FOR SELECT C.CODCOMI,C.CODEMPRC, C.CODFILIALRC , C.CODREC, C.NPARCITREC,
--      C.VLRVENDACOMI, C.VLRCOMI, C.DATACOMI , C.DTCOMPCOMI, C.DTVENCCOMI,
--      C.TIPOCOMI, C.STATUSCOMI , IR.STATUSITREC, IR.DTVENCITREC
--    FROM VDCOMISSAO C, FNITRECEBER IR, FNRECEBER R
--    WHERE C.CODEMP=:CODEMP AND C.CODFILIAL=:CODFILIAL AND
--       IR.CODEMP=C.CODEMPRC AND IR.CODFILIAL=C.CODFILIALRC AND
--       IR.CODREC=C.CODREC AND IR.NPARCITREC=C.NPARCITREC AND
--       R.CODEMP=C.CODEMPRC AND R.CODFILIAL=C.CODFILIALRC AND
--       R.CODREC=C.CODREC
       --AND R.CODEMPVD=:CODEMPVD
       --AND
       --R.CODFILIALVD=:CODFILIALVD
       --AND R.CODVEND=:CODVEND AND
       --and ( (:CORDEM = 'V')  OR (C.DATACOMI BETWEEN :DINI AND :DFIM) ) AND
       --( (:CORDEM = 'E')  OR (C.DTVENCCOMI BETWEEN :DINI AND :DFIM) ) AND
--       and C.STATUSCOMI IN ('C2','CP') AND
--       IR.STATUSITREC NOT IN ('RP') AND
--       NOT EXISTS(SELECT * FROM VDCOMISSAO C2 /* Sub-select para verificar a */
          /* existencia de estorno anterior. */
--         WHERE C2.CODEMPRC=C.CODEMPRC AND C2.CODFILIALRC=C.CODFILIALRC AND
--         C2.CODREC=C.CODREC AND C2.NPARCITREC=C.NPARCITREC AND
--         C2.TIPOCOMI=C.TIPOCOMI AND C2.STATUSCOMI IN ('CE') )
--    INTO :CODCOMI, :CODEMP, :CODFILIAL, :CODREC, :NPARCITREC, :VLRVENDACOMI,
--      :VLRCOMI, :DATACOMI, :DTCOMPCOMI, :DTVENCCOMI, :TIPOCOMI, :STATUSCOMI, :STATUSITREC,
--      :DTVENCITREC
--  DO
--  BEGIN
--     IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='C2') ) THEN
     /* Caso a data atual seja maior que a data de vencimento e a */
     /* comissão não esteja paga, passa o status da comissão para não */
     /* liberada. */
--     BEGIN
--        UPDATE VDCOMISSAO SET STATUSCOMI='C1'
--          WHERE CODEMP=:CODEMP AND CODFILIAL=:CODFILIAL AND
--            CODCOMI=:CODCOMI;
--     END
--     ELSE IF ( (DTATUAL>DTVENCITREC) AND (STATUSCOMI='CP') ) THEN
     /* Caso a comissão esteja paga e a parcela esteja vencida, */
     /* gera um estorno da comissão. */
--     BEGIN
--        VLRCOMI = VLRCOMI * -1; /* Transforma o valor da comissão em negativo */
        /* para gerar estorno */
     --   EXECUTE PROCEDURE vdadiccomissaosp(:CODEMP,:CODFILIAL,:CODREC,
       --   :NPARCITREC, :VLRVENDACOMI, :VLRCOMI, :DATACOMI , :DTCOMPCOMI, :DTVENCITREC,
         -- :TIPOCOMI, :codempvd, :codfilialvd, : codvend );
--     END
--  END
 -- suspend;
end
^

/* Alter (FNITRECEBERSP01) */
ALTER PROCEDURE FNITRECEBERSP01(ICODEMP INTEGER,
SCODFILIAL SMALLINT,
ICODREC INTEGER,
NVLRPARCREC NUMERIC(15,5),
NVLRCOMIREC NUMERIC(15,5),
INROPARCREC INTEGER,
CCLASCOMIS CHAR)
 AS
declare variable inparcitrec integer;
declare variable nvlrparcitrec numeric(15,5);
declare variable nperc numeric(10,6);
declare variable nvlrcomiitrec numeric(15,5);
declare variable nresto numeric(15,5);
declare variable dvencitrec date;
begin
    -- Procedure que atualiza a comissão na tabela ITRECEBER
    nResto = nVlrComiRec;

    for select nparcitrec, vlrparcitrec, dtvencitrec from fnitreceber
    where codemp=:icodemp and codfilial=:scodfilial and codrec=:icodrec
    order by nparcitrec
    into :inparcitrec , :nvlrparcitrec, :dvencitrec do

    begin
        nperc = nvlrparcitrec / nvlrparcrec;
        nvlrcomiitrec = cast( (nvlrcomirec * nperc) as numeric(15, 5) );
        nresto = nresto - nvlrcomiitrec;

        if (inparcitrec=inroparcrec) then
        begin
            nvlrcomiitrec = nvlrcomiitrec + nresto;
        end

        update fnitreceber ir set vlrcomiitrec=:nvlrcomiitrec where codemp=:icodemp and codfilial=:scodfilial
        and codrec=:icodrec and nparcitrec=:inparcitrec;

        if (cclascomis='S') then
            execute procedure vdgeracomissaosp(:icodemp, :scodfilial, :icodrec, :inparcitrec, :nvlrcomiitrec, :dvencitrec);
    end
    suspend;
end
^

/* Alter (LFBUSCAFISCALSP) */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR,
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR,
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR,
ALIQISS NUMERIC(6,2),
ADICIPIBASEICMS CHAR,
CALCSTCM CHAR,
ALIQICMSSTCM NUMERIC(9,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial ), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.codtipomov desc, it.codfisccli desc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , :aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial), it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            , it.adicipibaseicms, it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
                , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f.aliqissfisc, f1.percissfilial), f.adicipibaseicms, f.calcstcm, f.aliqicmsstcm
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest
            ,aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(it.aliqissfisc,f.percissfilial), it.adicipibaseicms , it.calcstcm, it.aliqicmsstcm
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest
            , aliqiss, :adicipibaseicms, :calcstcm, :aliqicmsstcm
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

/* empty dependent procedure body */
/* Clear: SGINICONSP for: SGATUALIZABDSP */
ALTER PROCEDURE SGINICONSP(ICODEMP INTEGER,
CIDUSU CHAR(8),
ICODFILIALSEL SMALLINT,
ICODTERM SMALLINT)
 RETURNS(SRET SMALLINT)
 AS
 BEGIN SUSPEND; END
^

/* Alter (SGATUALIZABDSP) */
ALTER PROCEDURE SGATUALIZABDSP(ICODEMP INTEGER)
 AS
DECLARE VARIABLE ICODEMPTMP INTEGER;
begin
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Entrou no atualizabdsp '||cast(ICODEMP AS CHAR(10)));*/

  EXECUTE PROCEDURE SGDADOSINISP(:ICODEMP);
  EXECUTE PROCEDURE SGGRANTUSERSP ;
  FOR SELECT CODEMP FROM SGEMPRESA INTO :ICODEMPTMP DO
  BEGIN
     EXECUTE PROCEDURE SGOBJETOINSTBSP(:ICODEMPTMP);
     EXECUTE PROCEDURE SGGRANTADMSP(:ICODEMPTMP);
  END
  suspend;
end
^

/* Alter (SGINICONSP) */
ALTER PROCEDURE SGINICONSP(ICODEMP INTEGER,
CIDUSU CHAR(8),
ICODFILIALSEL SMALLINT,
ICODTERM SMALLINT)
 RETURNS(SRET SMALLINT)
 AS
DECLARE VARIABLE ICONECTADO INTEGER;
DECLARE VARIABLE ICODFILIALSELCX SMALLINT;
DECLARE VARIABLE ICODEMPTMP INTEGER;
DECLARE VARIABLE ICODFILIAL SMALLINT;
begin
  /* Procedure para inicialização da conexão com o banco de dados */
  SRET = 0;
  if (ICODEMP IS NULL) then
     ICODEMP = 99;
  /*CTEMP = printLog('Vai verificar o usuário');*/
  CIDUSU = lower(CIDUSU);
  IF ( CIDUSU='sysdba' ) THEN
  BEGIN
/*     CTEMP = printLog('Usuário sysdba');*/

     SELECT CODEMP FROM SGEMPRESA WHERE CODEMP=:ICODEMP INTO :ICODEMPTMP;
     IF (ICODEMPTMP IS NULL) THEN
        EXECUTE PROCEDURE SGATUALIZABDSP(:ICODEMP);
  END
  SELECT CODFILIAL FROM SGUSUARIO WHERE IDUSU=:cIdUsu AND
      CODEMP=:ICODEMP  INTO :iCodFilial;
/*  CTEMP = printLog('O usuário é = '||cIdUsu); */

/*  CTEMP = printLog('Contador de usuários '||cIdUsu); */

  if (iCodFilial is null) then
    EXCEPTION SGCONEXAOEX02;

  if (iCodFilialSel is not null) then
  begin
      SRET = 1;
      SELECT CONECTADO, CODFILIALSEL FROM SGCONEXAO
        WHERE NRCONEXAO=CURRENT_CONNECTION INTO :iConectado, iCodFilialSelCX;
      if (iConectado is null) then
          INSERT INTO SGCONEXAO (NRCONEXAO, CODEMP,CODFILIAL,IDUSU,CODFILIALSEL,CONECTADO,CODTERM)
            VALUES (CURRENT_CONNECTION, :iCodemp, :iCodfilial, :cIdusu, :iCodfilialSel, 1, :iCodTerm);
      else
      begin
          if (iConectado<=0) then
             UPDATE SGCONEXAO SET CONECTADO=1, CODFILIALSEL=:iCodfilialSel, CODTERM = :iCodTerm,
               CODEMP=:iCodemp, CODFILIAL=:iCodfilial , IDUSU=:cIdUsu
               WHERE NRCONEXAO=CURRENT_CONNECTION;
          else
          begin
             if (not (iCodfilialSelCX=iCodfilialSel) ) then
                EXCEPTION SGCONEXAOEX01;
             else
                UPDATE SGCONEXAO SET CONECTADO=CONECTADO+1, CODTERM = :iCodTerm,
                   CODEMP=:iCodemp, CODFILIAL=:iCodfilial, IDUSU=:cIdUsu
                WHERE NRCONEXAO=CURRENT_CONNECTION;
          end
      end 
  end
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
COMPONENTES CHAR,
SERVICOS CHAR,
NOVOS CHAR)
 AS
declare variable codemppd integer;
declare variable codfilialpd integer;
declare variable codprod integer;
declare variable coditos integer;
declare variable coditorc integer;
declare variable codprodant integer;
declare variable coditrecmerc integer;
declare variable refprod varchar(20);
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
declare variable refprodir varchar(20);
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

SET TERM ; ^

