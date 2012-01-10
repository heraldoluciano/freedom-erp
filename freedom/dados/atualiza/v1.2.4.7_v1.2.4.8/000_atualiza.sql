alter table cpprodfor add codemppd integer;
alter table cpprodfor add codfilialpd smallint;
update cpprodfor set codemppd=codemp, codfilialpd=codfilial;
commit work;
