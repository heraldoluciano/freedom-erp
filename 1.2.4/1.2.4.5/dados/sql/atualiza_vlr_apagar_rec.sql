update fnreceber r
set r.vlrapagrec=coalesce((select sum(vlrapagitrec) from fnitreceber ir
where ir.CODEMP=r.codemp and ir.CODFILIAL=r.CODFILIAL and ir.CODREC=r.CODREC),0)
where (select sum(ir.VLRapagitrec) from FNITRECEBER ir where 
ir.CODEMP=r.codemp and ir.CODFILIAL=r.CODFILIAL and ir.CODREC=r.CODREC)<>r.vlrapagrec