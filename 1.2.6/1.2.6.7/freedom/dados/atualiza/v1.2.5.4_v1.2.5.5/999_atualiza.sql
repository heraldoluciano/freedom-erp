update vdvenda set emmanut='S', subtipovenda='NF';
update vdvenda set emmanut='N' where emmanut='S';
commit work;

