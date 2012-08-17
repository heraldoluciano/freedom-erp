insert into tkcontato (
codemp,codfilial,codcto,razcto,nomecto,pessoacto,ativocto,
codempsr,codfilialsr,codsetor,
cnpjcto,insccto,contcto,cepcto,endcto,numcto,complcto,
baircto,fonecto,faxcto,dddcto,emailcto,
codemptc, codfilialtc, codtipocli,
codpais,siglauf,cidcto,codmunic,
codempoc,codfilialoc,codorigcont)
select
cl.codemp,cl.codfilial,cl.codcli,cl.razcli,cl.nomecli,cl.pessoacli,cl.ativocli,
cl.codempsr,cl.codfilialsr,cl.codsetor,
cl.cnpjcli,cl.insccli,cl.contcli,cl.cepcli,cl.endcli,cl.numcli,cl.complcli,
cl.baircli,cl.fonecli,cl.faxcli,cl.dddcli,cl.emailcli,
cl.codempti,cl.codfilialti,cl.codtipocli,
cl.codpais,cl.siglauf,cl.cidcli,cl.codmunic,
?,?,?
from vdcliente cl;

