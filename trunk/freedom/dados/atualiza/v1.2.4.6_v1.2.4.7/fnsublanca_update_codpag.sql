update fnsublanca s
set s.emmanut='S',
s.codemppg=(select codemppg from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codfilialpg=(select codfilialpg from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codpag=(select codpag from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.nparcpag=(select nparcpag from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca);

update fnsublanca
set emmanut='N'
where emmanut='S';

commit work;



