update fnsublanca s
set s.emmanut='S',
s.codemprc=(select codemprc from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codfilialrc=(select codfilialrc from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codrec=(select codrec from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.nparcitrec=(select nparcitrec from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca);

update fnsublanca
set emmanut='N'
where emmanut='S';

commit work;



