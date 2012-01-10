alter table cpprodfor add codemppd integer;
alter table cpprodfor add codfilialpd smallint;
update cpprodfor set codempfr=codemp, codfilialfr=codfilial;
delete from cpprodfor pf where not exists(select * from eqproduto p where p.codemp=pf.codemp and p.codfilial=pf.codfilial and p.codprod=pf.codprod);
delete from cpprodfor pf where not exists(select * from cpforneced f where f.codemp=pf.codempfr and f.codfilial=pf.codfilialfr and p.codprod=pf.codprod);

commit work;
