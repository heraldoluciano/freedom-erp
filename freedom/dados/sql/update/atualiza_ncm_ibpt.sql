update lfncm n
set n.aliqnac=(select first 1 t.aliqnac from tabibpt t
  where t.codigo=substring(n.codncm from 1 for 4)||substring(n.codncm from 6 for 2)||substring(n.codncm from 9 for 2)
  )
, n.aliqimp=(select first 1 t.aliqimp from tabibpt t
  where t.codigo=substring( n.codncm from 1 for 4)||substring(n.codncm from 6 for 2)||substring(n.codncm from 9 for 2)
  )
where exists( select * from tabibpt t
  where t.codigo=substring(n.codncm from 1 for 4)||substring(n.codncm from 6 for 2)||substring(n.codncm from 9 for 2)
);

commit work;


