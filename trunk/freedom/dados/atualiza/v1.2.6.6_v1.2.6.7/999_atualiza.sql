update lffrete set emmanut='S', dtmovfrete=dtemitfrete where dtmovfrete is null;

update lffrete set emmanut='N' where emmanut='S';

commit work;

update fnitreceber r set r.emmanut='S', r.vlrparcitrec=r.vlrcancitrec where r.statusitrec='CR';

commit work;

update fnitreceber r set r.emmanut='N' where r.emmanut='S';

commit work;

update fnitpagar p set p.emmanut='S', p.vlrparcitpag=p.vlrcancitpag where p.statusitpag='CP';

commit work;

update fnitpagar p set p.emmanut='N' where p.emmanut='S';

commit work;

