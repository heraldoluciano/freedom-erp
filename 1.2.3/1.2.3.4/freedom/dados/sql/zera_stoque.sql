insert into eqinvprod (
codemp, codfilial,
codinvprod,
codemppd, codfilialpd, codprod,
codemptm, codfilialtm, codtipomov, datainvp,
qtdinvp, precoinvp,
codempax, codfilialax, codalmox, refprod,
sldatualinvp,
slddiginvp,
flag, obsinvp)
select
pd.codemp, pd.codfilial,
( select max(iv.codinvprod)+1 from eqinvprod iv
) as codinv,
pd.codemp, pd.codfilial , pd.codprod,
pd.codemp, pd.codfilial, pf.codtipomov6 as codtipomov, cast('now' as date) as datainvp,
coalesce(( select sp.sldliqprod * -1 from eqsaldoprod sp
  where sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod
  and sp.codempax=ax.codemp and sp.codfilialax=ax.codfilial and sp.codalmox=ax.codalmox
),0) as saldoatual,
pd.custompmprod,
ax.codemp, ax.codfilial, ax.codalmox, PD.refprod,
coalesce(( select sp.sldliqprod from eqsaldoprod sp
  where sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod
  and sp.codempax=ax.codemp and sp.codfilialax=ax.codfilial and sp.codalmox=ax.codalmox
),0) as saldodigitado,
0,
'S','Limpeza de saldos para inventário 1 DE ABRIL DE 2009'
from eqproduto pd, eqalmox ax, sgprefere1 pf
where pf.codemp=pd.codemp and pf.codfilial=pd.codfilial
and  ax.codalmox
in (select spd.codalmox from eqsaldoprod spd where spd.codemp=pd.codemp and 
spd.codfilial=pd.codfilial and spd.codprod=pd.codprod)
