/*
   Rodar este script  APÓS o processamento de estoque para reestabelecer o parâmetro inicial de controle de estoque.
*/

update sgprefere1 p1 set p1.estlotneg=(select p.estlotneg from tmpprefere p where p.codemp=p1.codemp and p.codfilial=p1.codfilial)
, p1.estneg=(select p.estneg from tmpprefere p where p.codemp=p1.codemp and p.codfilial=p1.codfilial);

drop table tmpprefere;

commit work;


