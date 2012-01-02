update fnlanca l set l.codempfr= (select codempfr from fnpagar p where p.codemp=l.codemppg and
p.codfilial=l.codfilialpg and p.codpag=l.codpag),
l.codfilialfr= (select codfilialfr from fnpagar p where p.codemp=l.codemppg and
p.codfilial=l.codfilialpg and p.codpag=l.codpag),
l.codfor= (select codfor from fnpagar p where p.codemp=l.codemppg and
p.codfilial=l.codfilialpg and p.codpag=l.codpag)
where exists (select codfor from fnpagar p where p.codemp=l.codemppg and
p.codfilial=l.codfilialpg and p.codpag=l.codpag);

update fnlanca l set l.codempcl= (select codempcl from fnreceber r where r.codemp=l.codemprc and
r.codfilial=l.codfilialrc and r.codrec=l.codrec),
l.codfilialcl= (select codfilialcl from fnreceber r where r.codemp=l.codemprc and
r.codfilial=l.codfilialrc and r.codrec=l.codrec),
l.codcli= (select codcli from fnreceber r where r.codemp=l.codemprc and
r.codfilial=l.codfilialrc and r.codrec=l.codrec)
where exists (select codempcl from fnreceber r where r.codemp=l.codemprc and
r.codfilial=l.codfilialrc and r.codrec=l.codrec);

commit work;
