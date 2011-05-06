-- Inserindo movimentações de venda
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codempvd      , codfilialvd   , codvenda      , tipovenda     , coditvenda ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox,
codemptm    , codfilialtm   , codtipomov
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd   , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.codvenda   , vd.tipovenda      , vd.coditvenda,
v.dtsaidavenda  , -1            , pd.codalmox                                                   , pd.codfilialax, pd.codalmox       ,
v.codemptm      , v.codfilialtm , v.codtipomov

from vditvendaserie vd, vdvenda v, vditvenda iv, eqproduto pd
where
v.codemp=vd.codemp and v.codfilial=vd.codfilial and v.codvenda=vd.codvenda and v.tipovenda=vd.tipovenda and
iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.coditvenda=vd.coditvenda and
pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod
;
-- Inserindo movimentações de compra
insert into eqmovserie
(
codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
numserie    , codempcp      , codfilialcp   , codcompra     , coditcompra ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox,
codemptm    , codfilialtm   , codtipomov
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd       , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.codcompra      , vd.coditcompra    ,
v.dtentcompra   , 1             , pd.codalmox                                                   , pd.codfilialax    , pd.codalmox       ,
v.codemptm      , v.codfilialtm , v.codtipomov

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
numserie    , codemprc      , codfilialrc   , ticket        , coditrecmerc ,
dtmovserie  , tipomovserie  , codempax      , codfilialax   , codalmox
)
select
vd.codemp       , vd.codfilial  , coalesce((select max(codmovserie) + 1 from eqmovserie ),1)    , vd.codemppd       , vd.codfilialpd    , vd.codprod,
vd.numserie     , vd.codemp     , vd.codfilial                                                  , vd.ticket         , vd.coditrecmerc   ,
v.dtent         , 1             , pd.codalmox                                                   , pd.codfilialax    , pd.codalmox

from eqitrecmercserie vd, eqrecmerc v, eqitrecmerc iv, eqproduto pd
where
v.codemp=vd.codemp and v.codfilial=vd.codfilial and v.ticket=vd.ticket and
iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.ticket=vd.ticket and iv.coditrecmerc=vd.coditrecmerc and
pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod
;






