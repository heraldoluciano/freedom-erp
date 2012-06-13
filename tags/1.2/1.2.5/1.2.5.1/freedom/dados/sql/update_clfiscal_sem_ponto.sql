update lfclfiscal cl set cl.codncm=
(select first 1 codncm 
from lfncmnbm cb
where (substring(cb.codncm from 1 for 4)||
substring(cb.codncm from 6 for 2)||
substring(cb.codncm from 9 for 2))=substring(cl.codfisc from 1 for 8) 
order by cb.codncm, cb.codnbm) ,
cl.codnbm=(select first 1 codnbm from lfncmnbm cb
where (substring(cb.codncm from 1 for 4)||
substring(cb.codncm from 6 for 2)||
substring(cb.codncm from 9 for 2))=substring(cl.codfisc from 1 for 8) 
order by cb.codncm, cb.codnbm)
where exists (select * from lfncmnbm cb
where (substring(cb.codncm from 1 for 4)||
substring(cb.codncm from 6 for 2)||
substring(cb.codncm from 9 for 2))=substring(cl.codfisc from 1 for 8));
commit;
