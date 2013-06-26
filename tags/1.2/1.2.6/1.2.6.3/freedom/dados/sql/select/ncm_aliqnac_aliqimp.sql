select nc.codncm, nc.descncm, cl.codfisc, icl.coditfisc
from lfncm nc, lfclfiscal cl, lfitclfiscal icl
where ( nc.aliqnac=0 or nc.aliqimp=0 )
and cl.codncm=nc.codncm
and icl.codemp=cl.codemp and icl.codfilial=cl.codfilial and icl.codfisc=cl.codfisc
and exists(
 select * from vditvenda vd
 where vd.codempif=icl.codemp and vd.codfilialif=icl.codfilial
 and  vd.codfisc=icl.codfisc and vd.coditfisc=icl.coditfisc
);

