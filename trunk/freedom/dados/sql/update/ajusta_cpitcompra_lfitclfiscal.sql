update cpcompra set emmanut = 'S';

update cpitcompra ic set codempif = 68, codfilialif = 1,
codfisc=
( select codfisc from lfbuscafiscalsp(ic.codemppd,ic.codfilialpd,ic.codprod,
( select codempfr from cpcompra where codcompra = ic.codcompra) ,
( select codfilialfr from cpcompra where codcompra = ic.codcompra) ,
( select codfor from cpcompra where codcompra = ic.codcompra ) ,
( select codemptm from cpcompra where codcompra = ic.codcompra) ,
( select codfilialtm from cpcompra where codcompra = ic.codcompra),
( select codtipomov from cpcompra where codcompra = ic.codcompra ) , 'CP',
null, null, null, null,null )
)
,
coditfisc=
( select coditfisc from lfbuscafiscalsp(ic.codemppd,ic.codfilialpd,ic.codprod,
( select codempfr from cpcompra f where codcompra = ic.codcompra),
( select codfilialfr from cpcompra where codcompra = ic.codcompra) ,
( select codfor from cpcompra where codcompra = ic.codcompra ) ,
( select codemptm from cpcompra where codcompra = ic.codcompra),
( select codfilialtm from cpcompra where codcompra = ic.codcompra),
( select codtipomov from cpcompra where codcompra = ic.codcompra ) , 'CP'
, null, null, null, null,null )
)
where ic.coditfisc is null;

update cpcompra set emmanut = 'N';

commit work;