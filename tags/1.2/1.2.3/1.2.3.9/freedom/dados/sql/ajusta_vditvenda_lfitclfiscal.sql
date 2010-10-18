update vdvenda set emmanut = 'S';

update vditvenda iv set codempif = 60, codfilialif = 1,
codfisc=
( select codfisc from lfbuscafiscalsp(iv.codemppd,iv.codfilialpd,iv.codprod,
60 , 1 , ( select codcli from vdvenda where codvenda = iv.codvenda ) ,
60 , 1 , ( select codtipomov from vdvenda where codvenda = iv.codvenda ) , 'VD' )
)
,
coditfisc=
( select coditfisc from lfbuscafiscalsp(iv.codemppd,iv.codfilialpd,iv.codprod,
60 , 1 , ( select codcli from vdvenda where codvenda = iv.codvenda ) ,
60 , 1 , ( select codtipomov from vdvenda where codvenda = iv.codvenda ) , 'VD' )
)
where iv.coditfisc is null;

update vdvenda set emmanut = 'N';
