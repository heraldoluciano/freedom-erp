set term ^ ;

CREATE OR ALTER TRIGGER VDITORCAMENTOTGBI FOR VDITORCAMENTO 
ACTIVE BEFORE INSERT POSITION 0 
as
    declare variable refprod VARchar(20);
    declare variable vlripi numeric(15,5);
    declare variable contribipi char(1);
    declare variable adicfrete char(1);

begin

  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
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
end ^

CREATE OR ALTER TRIGGER VDITORCAMENTOTGAI FOR VDITORCAMENTO 
ACTIVE AFTER INSERT POSITION 0 
AS
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
begin
  if ( ( new.emmanut is null ) or ( new.emmanut='N' ) ) then
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
end ^

commit ^

