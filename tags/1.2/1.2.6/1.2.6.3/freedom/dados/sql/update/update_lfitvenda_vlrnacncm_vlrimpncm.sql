update lfitvenda liv set liv.emmanut='S',
liv.vlrnacncm=0
where liv.vlrbasencm>0
and coalesce(
( select nc.aliqimp from lfncm nc, vditvenda iv, lfclfiscal cl, lfitclfiscal icl
   where iv.codemp=liv.codemp and iv.codfilial=liv.codfilial and iv.tipovenda=liv.tipovenda
   and iv.codvenda=liv.codvenda and iv.coditvenda=liv.coditvenda
   and cl.codemp=iv.codempif and cl.codfilial=iv.codfilialif and cl.codfisc=iv.codfisc
   and nc.codncm=cl.codncm
   and icl.codemp=iv.codempif and icl.codfilial=iv.codfilialif and icl.codfisc=iv.codfisc and icl.coditfisc=iv.coditfisc
   and icl.origfisc in ('1','2','6','7')
)
,0) > 0;

update lfitvenda liv set liv.emmanut='S',
liv.vlrimpncm=0
where liv.vlrbasencm>0
and coalesce(
( select nc.aliqnac from lfncm nc, vditvenda iv, lfclfiscal cl, lfitclfiscal icl
   where iv.codemp=liv.codemp and iv.codfilial=liv.codfilial and iv.tipovenda=liv.tipovenda
   and iv.codvenda=liv.codvenda and iv.coditvenda=liv.coditvenda
   and cl.codemp=iv.codempif and cl.codfilial=iv.codfilialif and cl.codfisc=iv.codfisc
   and nc.codncm=cl.codncm
   and icl.codemp=iv.codempif and icl.codfilial=iv.codfilialif and icl.codfisc=iv.codfisc and icl.coditfisc=iv.coditfisc
   and icl.origfisc not in ('1','2','6','7')
)
,0) > 0;

update lfitvenda set emmanut='N' where emmanut='S';

select liv.vlrbasencm, liv.aliqnacncm, liv.aliqimpncm, liv.vlrnacncm, liv.vlrimpncm
 from lfitvenda liv
 where liv.vlrbasencm>0;


commit work;














