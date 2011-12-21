update fnsublanca s
set s.emmanut='S',
s.codemppg=(select codemppg from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codfilialpg=(select codfilialpg from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codpag=(select codpag from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.nparcpag=(select nparcpag from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca);

update fnsublanca
set emmanut='N'
where emmanut='S';

-- select utilizada para gerar update
select slp.codlanca, slp.codsublanca, ip.vlrpagoitpag, slp.vlrsublanca, ip.dtpagoitpag, ip.codemp,  ip.codfilial, ip.codpag, ip.nparcpag
from fnitpagar ip, fnpagar p, fnsublanca slp
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag and
slp.codempfr=p.codempfr and slp.codfilialfr=p.codfilialfr and slp.codfor=p.codfor and
slp.datasublanca=ip.dtpagoitpag and slp.vlrsublanca=ip.vlrpagoitpag and
ip.statusitpag = 'PP' and
not exists (select * from fnsublanca sl2
where sl2.codemppg=ip.codemp and sl2.codfilialpg=ip.codfilial and
sl2.codpag=ip.codpag and sl2.nparcpag=ip.nparcpag);

update fnsublanca sl set
sl.emmanut='S',
sl.codemppg = (select first 1 ip.codemp
from fnitpagar ip, fnpagar p
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag and
sl.codempfr=p.codempfr and sl.codfilialfr=p.codfilialfr and sl.codfor=p.codfor and
sl.datasublanca=ip.dtpagoitpag and sl.vlrsublanca=ip.vlrpagoitpag and
ip.statusitpag = 'PP'),
sl.codfilialpg = (select first 1 ip.codfilial
from fnitpagar ip, fnpagar p
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag and
sl.codempfr=p.codempfr and sl.codfilialfr=p.codfilialfr and sl.codfor=p.codfor and
sl.datasublanca=ip.dtpagoitpag and sl.vlrsublanca=ip.vlrpagoitpag and
ip.statusitpag = 'PP'),
sl.codpag = (select first 1 ip.codpag
from fnitpagar ip, fnpagar p
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag and
sl.codempfr=p.codempfr and sl.codfilialfr=p.codfilialfr and sl.codfor=p.codfor and
sl.datasublanca=ip.dtpagoitpag and sl.vlrsublanca=ip.vlrpagoitpag and
ip.statusitpag = 'PP'),
sl.nparcpag = (select first 1 ip.nparcpag
from fnitpagar ip, fnpagar p
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag and
sl.codempfr=p.codempfr and sl.codfilialfr=p.codfilialfr and sl.codfor=p.codfor and
sl.datasublanca=ip.dtpagoitpag and sl.vlrsublanca=ip.vlrpagoitpag and
ip.statusitpag = 'PP')
where sl.codfor is not null and sl.codpag is null;

update fnsublanca set emmanut='N' where emmanut='S';


commit work;



