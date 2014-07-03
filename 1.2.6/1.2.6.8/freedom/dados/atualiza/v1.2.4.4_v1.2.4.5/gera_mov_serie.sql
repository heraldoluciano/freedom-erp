-- Limpando movimentação anterior
delete from eqmovserie;
-- Inserindo movimentações de venda
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codempvd      , codfilialvd   , codvenda      , tipovenda     , coditvenda ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox,
codemptm    , codfilialtm   , codtipomov    , docmovserie
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd   , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.codvenda   , vd.tipovenda      , vd.coditvenda,
v.dtsaidavenda  , -1            , pd.codalmox                                                   , pd.codfilialax, pd.codalmox       ,
v.codemptm      , v.codfilialtm , v.codtipomov, v.docvenda

from vditvendaserie vd, vdvenda v, vditvenda iv, eqproduto pd
where
v.codemp=vd.codemp and v.codfilial=vd.codfilial and v.codvenda=vd.codvenda and v.tipovenda=vd.tipovenda and
iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.coditvenda=vd.coditvenda and
pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod and vd.numserie is not null
;
-- Inserindo movimentações de compra
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codempcp      , codfilialcp   , codcompra     , coditcompra ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox,
codemptm    , codfilialtm   , codtipomov    , docmovserie
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd       , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.codcompra      , vd.coditcompra    ,
v.dtentcompra   , 1             , pd.codalmox                                                   , pd.codfilialax    , pd.codalmox       ,
v.codemptm      , v.codfilialtm , v.codtipomov, v.doccompra

from cpitcompraserie vd, cpcompra v, cpitcompra iv, eqproduto pd
where
v.codemp=vd.codemp and v.codfilial=vd.codfilial and v.codcompra=vd.codcompra and
iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codcompra=vd.codcompra and iv.coditcompra=vd.coditcompra and
pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod
;
-- Inserindo movimentações de coleta
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codemprc      , codfilialrc   , ticket        , coditrecmerc  ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox      , docmovserie
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd       , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.ticket         , vd.coditrecmerc   ,
v.dtent         , 1             , pd.codalmox                                                   , pd.codfilialax    , pd.codalmox,
v.docrecmerc

from eqitrecmercserie vd, eqrecmerc v, eqitrecmerc iv, eqproduto pd
where
v.codemp=vd.codemp and v.codfilial=vd.codfilial and v.ticket=vd.ticket and
iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.ticket=vd.ticket and iv.coditrecmerc=vd.coditrecmerc and
pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod
;
-- Ingerindo saidas referentes a consertos
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codempvd      , codfilialvd   , codvenda      , coditvenda    , tipovenda  ,
dtmovserie  , tipomovserie  , docmovserie
)

select ir.codemp, ir.codfilial, coalesce((select max(codmovserie) + 1 from eqmovserie ),1), ir.codemppd, ir.codfilialpd, ir.codprod,
ir.numserie, vo.codemp, vo.codfilial, vo.codvenda, vo.coditvenda, vo.tipovenda, vd.dtsaidavenda, -1, vd.docvenda
from eqitrecmercitositorc ro, eqitrecmerc ir, vdvendaorc vo, vdvenda vd, vditvenda iv
where
ro.codemp=vo.codempor and ro.codfilial=vo.codfilialor and ro.codorc=vo.codorc and ro.tipoorc=vo.tipoorc and
ro.coditorc = vo.coditorc and
ir.codemp=ro.codemp and ir.codfilial=ro.codfilial and ir.ticket=ro.ticket and ir.coditrecmerc=ro.coditrecmerc and
vo.codemp=iv.codemp and vo.codfilial=iv.codfilial and vo.codvenda=iv.codvenda and vo.tipovenda=iv.tipovenda and
vo.coditvenda=iv.coditvenda
and vd.codemp=iv.codemp and vd.codfilial=iv.codfilial and vd.tipovenda=iv.tipovenda and vd.codvenda=iv.codvenda
and ir.numserie is not null;

commit;






