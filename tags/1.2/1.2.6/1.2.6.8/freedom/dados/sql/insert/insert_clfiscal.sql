insert into lfclfiscal
(codemp, codfilial, codfisc, descfisc, codncm, codnbm
, codempra, codfilialra, codregra)
select f.codemp, f.codfilial, ncm.codncm codfisc, substring(ncm.descncm from 1 for 50) descfisc, ncm.codncm
, (select min(codnbm) from lfncmnbm nn where nn.codncm=ncm.codncm) codnbm
, f.codemp codempra, f.codfilial codfilialra, (select min(codregra) from lfregrafiscal) codregra
from lfncm ncm, sgfilial f
where exists(select * from lfncmnbm nn where nn.codncm=ncm.codncm)
and not exists(select * from lfclfiscal cf where cf.codfisc=ncm.codncm);

