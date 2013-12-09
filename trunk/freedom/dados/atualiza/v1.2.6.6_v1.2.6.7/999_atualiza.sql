update lffrete set emmanut='S', dtmovfrete=dtemitfrete where dtmovfrete is null;

update lffrete set emmanut='N' where emmanut='S';

commit work;
