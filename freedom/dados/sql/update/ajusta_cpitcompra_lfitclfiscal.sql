update cpcompra set emmanut = 'S';

update cpitcompra ic set codempif = 68, codfilialif = 1,
codfisc=
( select codfisc from lfbuscafiscalsp(ic.codemppd,ic.codfilialpd,ic.codprod,
68 , 1 , ( select codfor from cpcompra where codcompra = ic.codcompra ) ,
68 , 1 , ( select codtipomov from cpcompra where codcompra = ic.codcompra ) , 'CP',
null, null, null, null,null )
)
,
coditfisc=
( select coditfisc from lfbuscafiscalsp(ic.codemppd,ic.codfilialpd,ic.codprod,
68 , 1 , ( select codfor from cpcompra where codcompra = ic.codcompra ) ,
68 , 1 , ( select codtipomov from cpcompra where codcompra = ic.codcompra ) , 'CP'
, null, null, null, null,null )
)
where ic.coditfisc is null;

update cpcompra set emmanut = 'N';