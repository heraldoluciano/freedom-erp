-- Atualizando valor not null na tabela lfitclfiscal

update lfclfiscal set aliqipifisc=0 where aliqipifisc is null;
-- Inserindo item geral

insert into lfitclfiscal (
codemp,codfilial,codfisc,
coditfisc,
origfisc,codemptt,codfilialtt,codtrattrib,
tipofisc,tpredicmsfisc,redfisc,noufitfisc,
aliqfisc,aliqlfisc,aliqipifisc,
codempme,codfilialme,codmens,tipost,margemvlagr,geralfisc)
select
cl.codemp, cl.codfilial, cl.codfisc,
coalesce((select max(coalesce(coditfisc,0)) + 1 from lfitclfiscal it2
 where it2.codemp=cl.codemp and it2.codfilial=cl.codfilial and it2.codfisc=cl.codfisc
),1),
cl.origfisc,cl.codemptt, cl.codfilialtt, cl.codtrattrib,
cl.tipofisc,coalesce(cl.tpredicmsfisc,0),cl.redfisc,'S',
cl.aliqfisc,cl.aliqlfisc,cl.aliqipifisc,
cl.codempme,cl.codfilialme,cl.codmens,cl.tipost,cl.margemvlagr,'S'
from
lfclfiscal cl
where not exists(select geralfisc from lfitclfiscal it3 where codemp=cl.codemp and codfilial=cl.codfilial and codfisc=cl.codfisc and geralfisc='S' )
;

commit;







