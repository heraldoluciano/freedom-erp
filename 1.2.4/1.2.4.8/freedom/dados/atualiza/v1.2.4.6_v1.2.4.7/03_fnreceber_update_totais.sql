
-- 1a. Consulta tabela de contas a receber com totalizador de descontos com valor incorreto
select count(*) from fnreceber r
where r.vlrdescrec<>coalesce((select sum(ir.vlrdescitrec) 
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 1b. Atualiza tabela de contas a receber com soma de descontos correta
update fnreceber r set r.emmanut='S',
  r.vlrdescrec=coalesce((select sum(ir.vlrdescitrec) 
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrdescrec<>coalesce((select sum(ir.vlrdescitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

-- 2a. Consulta tabela de contas a receber com totalizador de juros com valor incorreto
select count(*) from fnreceber r
where r.vlrjurosrec<>coalesce((select sum(ir.vlrjurositrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 2b. Atualiza tabela de contas a receber com soma de juros correta
update fnreceber r set r.emmanut='S',
  r.vlrjurosrec=coalesce((select sum(ir.vlrjurositrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrjurosrec<>coalesce((select sum(ir.vlrjurositrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);


-- 3a. Consulta tabela de contas a receber com totalizador de multa com valor incorreto
select count(*) from fnreceber r
where r.vlrmultarec<>coalesce((select sum(ir.vlrmultaitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 3b. Atualiza tabela de contas a receber com soma de multa correta
update fnreceber r set r.emmanut='S',
  r.vlrmultarec=coalesce((select sum(ir.vlrmultaitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrmultarec<>coalesce((select sum(ir.vlrmultaitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

-- 4a. Consulta tabela de contas a receber com totalizador de valor a receber com valor incorreto
select count(*) from fnreceber r
where r.vlrapagrec<>coalesce((select sum(ir.vlrapagitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 4b. Atualiza tabela de contas a receber com soma de valor a receber correta
update fnreceber r set r.emmanut='S',
  r.vlrapagrec=coalesce((select sum(ir.vlrapagitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrapagrec<>coalesce((select sum(ir.vlrapagitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

update fnreceber r set r.emmanut='N' where r.emmanut='S';
-- Atualiza situação de manutenção da tabela fnreceber
update fnreceber r set r.emmanut='N' where r.emmanut='S';

-- 5a. Consulta tabela de contas a receber com totalizador de valor recebido incorreto
select count(*) from fnreceber r
where r.vlrpagorec<>coalesce((select sum(ir.vlrpagoitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 5b. Atualiza tabela de contas a receber com soma de valor recebido correto
update fnreceber r set r.emmanut='S',
  r.vlrpagorec=coalesce((select sum(ir.vlrpagoitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrpagorec<>coalesce((select sum(ir.vlrpagoitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

-- 6a. Consulta tabela de contas a receber com totalizador de valor parcela incorreto
select count(*) from fnreceber r
where r.vlrparcrec<>coalesce((select sum(ir.vlrparcitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 6b. Atualiza tabela de contas a receber com soma de valor parcela correto
update fnreceber r set r.emmanut='S',
  r.vlrparcrec=coalesce((select sum(ir.vlrparcitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrparcrec<>coalesce((select sum(ir.vlrparcitrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

-- 7a. Consulta tabela de contas a receber com totalizador de valor receber incorreto
select count(*) from fnreceber r
where r.vlrrec<>coalesce((select sum(ir.vlritrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0) ;

-- 7b. Atualiza tabela de contas a receber com soma de valor receber correto
update fnreceber r set r.emmanut='S',
  r.vlrrec=coalesce((select sum(ir.vlritrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0)
where r.vlrrec<>coalesce((select sum(ir.vlritrec)
  from fnitreceber ir where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ),0);

-- Atualiza situação de manutenção da tabela fnreceber
update fnreceber r set r.emmanut='N' where r.emmanut='S';

commit work;

