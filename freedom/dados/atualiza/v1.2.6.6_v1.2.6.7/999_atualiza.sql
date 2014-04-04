update lffrete set emmanut='S', dtmovfrete=dtemitfrete where dtmovfrete is null;

update lffrete set emmanut='N' where emmanut='S';

commit work;

update fnitreceber r set r.emmanut='S', r.vlrparcitrec=r.vlrcancitrec where r.statusitrec='CR';

commit work;

update fnitreceber r set r.emmanut='N' where emmanut='S';

commit work;
