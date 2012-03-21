insert into eqprodutolog
( codemp, codfilial, codprod, seqlog, precobaseprodant, precobaseprodnovo )
select pd.codemp, pd.codfilial, pd.codprod, 1, precobaseprod, precobaseprod
from eqproduto pd
where not exists(select seqlog from eqprodutolog where codemp=pd.codemp and codfilial=pd.codfilial and codprod=pd.codprod );
