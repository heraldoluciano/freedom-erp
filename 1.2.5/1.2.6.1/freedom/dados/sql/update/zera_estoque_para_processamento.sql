delete from eqmovprod m where m.codtipomov is null;

update eqmovprod set emmanut='S';

delete from eqmovprod;

/*
   Rodar este script ANTES do processamento de estoque.
*/
create table tmpprefere ( codemp integer, codfilial smallint, estlotneg char(1), estneg char(1) );

insert into tmpprefere (codemp, codfilial, estlotneg, estneg)
select codemp, codfilial, estlotneg, estneg from sgprefere1 ;

commit work;

delete from eqsaldoprod;

update eqproduto p set p.sldprod=0, p.sldresprod=0, p.sldconsigprod=0, p.sldliqprod=0 ;

update eqsaldoprod sp set sp.sldprod=0, sp.sldresprod=0, sp.sldconsigprod=0, sp.sldliqprod=0;

update sgprefere1 p set p.estlotneg='S', p.estneg='S';

commit work;



