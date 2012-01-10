alter table cpprodfor add codemppd integer;
alter table cpprodfor add codfilialpd smallint;
update cpprodfor set codemppd=codemp, codfilialpd=codfilial;
delete from cpprodfor pf where not exists(select * from eqproduto p where p.codemp=pf.codemppd and p.codfilial=pf.codfilialpd and p.codprod=pf.codprod);
commit work;
