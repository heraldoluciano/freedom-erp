update fnreceber r
set r.vlrpagorec=coalesce((select sum(vlrpagoitrec) from fnitreceber ir
where ir.CODEMP=r.codemp and ir.CODFILIAL=r.CODFILIAL and ir.CODREC=r.CODREC),0)
where (select sum(ir.VLRPAGOitrec) from FNITRECEBER ir where 
ir.CODEMP=r.codemp and ir.CODFILIAL=r.CODFILIAL and ir.CODREC=r.CODREC)<>r.vlrpagorec