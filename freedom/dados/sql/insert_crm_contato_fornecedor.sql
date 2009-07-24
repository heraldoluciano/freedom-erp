insert into tkcontato (
codemp,codfilial,codcto,razcto,nomecto,pessoacto,ativocto,
codempsr,codfilialsr,codsetor,
cnpjcto,insccto,contcto,cepcto,endcto,numcto,complcto,
baircto,fonecto,faxcto,dddcto,emailcto,
codemptc, codfilialtc, codtipocli,
codpais,siglauf,cidcto,codmunic,
codempoc,codfilialoc,codorigcont)
select
f.codemp,f.codfilial,coalesce((select max(ccodcto) from tkcontato c),0)+1 
f.codfor,f.razfor,f.nomefor,f.pessoafor,f.ativofor,
f.codempsr,f.codfilialsr,?,
f.cnpjfor,f.inscfor,f.contfor,f.cepfor,f.endfor,f.numfor,f.complfor,
f.bairfor,f.fonefor,f.faxfor,f.dddfor,f.emailfor,
f.codemp,f.codfilial,?,
f.codpais,f.siglauf,f.cidfor,f.codmunic,
?,?,?
from cpforneced f;

