update vdcomissao c

set c.codempvd=( select vd.codempvd from vdcomissao v, fnreceber fr, vdvenda vd
                 where v.codemprc=fr.codemp and v.codfilialrc=fr.codfilial and v.codrec=fr.codrec
                 and vd.codemp=fr.codempva and vd.codfilial=fr.codfilialva and vd.codvenda=fr.codvenda
                 and vd.tipovenda=fr.tipovenda
                 and v.codemp=c.codemp and v.codfilial=c.codfilial and v.codcomi=c.codcomi ),

c.codfilialvd=( select vd.codfilialvd from vdcomissao v, fnreceber fr, vdvenda vd
                 where v.codemprc=fr.codemp and v.codfilialrc=fr.codfilial and v.codrec=fr.codrec
                 and vd.codemp=fr.codempva and vd.codfilial=fr.codfilialva and vd.codvenda=fr.codvenda
                 and vd.tipovenda=fr.tipovenda
                 and v.codemp=c.codemp and v.codfilial=c.codfilial and v.codcomi=c.codcomi  ),


c.codvend=( select vd.codvend from vdcomissao v, fnreceber fr, vdvenda vd
                 where v.codemprc=fr.codemp and v.codfilialrc=fr.codfilial and v.codrec=fr.codrec
                 and vd.codemp=fr.codempva and vd.codfilial=fr.codfilialva and vd.codvenda=fr.codvenda
                 and vd.tipovenda=fr.tipovenda
                 and v.codemp=c.codemp and v.codfilial=c.codfilial and v.codcomi=c.codcomi )



