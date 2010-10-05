update lfclfiscal cl set cl.codncm=(select first 1 codncm from lfncmnbm cb
where cb.codncm=cl.codfisc order by cb.codncm, cb.codnbm) ,
cl.codnbm=(select first 1 codnbm from lfncmnbm cb
where cb.codncm=cl.codfisc order by cb.codncm, cb.codnbm)
where exists (select * from lfncmnbm cb
where cb.codncm=cl.codfisc);
commit;


