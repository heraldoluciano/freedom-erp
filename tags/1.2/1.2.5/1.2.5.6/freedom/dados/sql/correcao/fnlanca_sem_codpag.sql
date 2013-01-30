drop table tmppag

create table tmppag
(
codemp integer, codfilial smallint, codpag integer
, nparcpag integer, vlrpagoitpag numeric(15,5)
, dtpagoitpag date, multibaixa char(1)
, codempfr integer, codfilialfr smallint , codfor integer
, doclancaitpag varchar(10)
);


insert into tmppag
select ip.codemp, ip.codfilial, ip.codpag, ip.nparcpag, ip.vlrpagoitpag
, ip.dtpagoitpag, ip.multibaixa, p.codempfr, p.codfilialfr, p.codfor
, ip.doclancaitpag
from fnitpagar ip, fnpagar p
where p.codemp=ip.codemp and p.codfilial=ip.codfilial and p.codpag=ip.codpag
and not exists(select * from fnlanca l
where l.codpag=ip.codpag and l.nparcpag=ip.nparcpag and l.codemppg=ip.codemp and l.codfilialpg=ip.codfilial)
and ip.statusitpag in ('PP')
order by dtpagoitpag ;


create index tmppagidx01 on tmppag (codpag, nparcpag, codemp, codfilial);

select sl.codemp, sl.codfilial, sl.codlanca, sl.codsublanca
, t.codemp codemppg, t.codfilial codfilialpg, t.codpag, t.nparcpag
, sl.vlrsublanca
, t.vlrpagoitpag, sl.datasublanca, t.dtpagoitpag
from fnlanca l, tmppag t, fnsublanca sl
where sl.codempfr=t.codempfr and sl.codfilialfr=t.codfilialfr and sl.codfor=t.codfor
and sl.codemp=l.codemp and sl.codfilial=l.codfilial and sl.codlanca=l.codlanca
and sl.vlrsublanca=t.vlrpagoitpag
and l.datalanca=t.dtpagoitpag
and coalesce(t.multibaixa,'N')='S';

