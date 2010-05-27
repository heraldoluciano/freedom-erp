insert into tkcontato (
codemp,codfilial,codcto,razcto,nomecto,pessoacto,ativocto,
codempsr,codfilialsr,codsetor,
cnpjcto,insccto,contcto,cepcto,endcto,numcto,complcto,
baircto,fonecto,faxcto,dddcto,emailcto,
codemptc, codfilialtc, codtipocli,
codpais,siglauf,cidcto,codmunic,
codempoc,codfilialoc,codorigcont)
select
f.codemp,f.codfilial,coalesce((select max(codcto) from tkcontato c),0)+1,
f.razfor,f.nomefor,f.pessoafor,f.ativofor,
?,?,?,
f.cnpjfor,f.inscfor,f.contfor,f.cepfor,f.endfor,f.numfor,f.complfor,
f.bairfor,f.fonefor,f.faxfor,f.dddfonefor,f.emailfor,
?,?,?,
f.codpais,f.siglauf,f.cidfor,f.codmunic,
?,?,?
from cpforneced f;

