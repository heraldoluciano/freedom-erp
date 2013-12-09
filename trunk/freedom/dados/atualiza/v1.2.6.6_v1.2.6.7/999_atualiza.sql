update lffrete set emmanut='S', dtmovfrete=dtemitfrete where dtmovfrete is null;

update lffrete set emmentu='N' where emmanut='S';

commit work;
