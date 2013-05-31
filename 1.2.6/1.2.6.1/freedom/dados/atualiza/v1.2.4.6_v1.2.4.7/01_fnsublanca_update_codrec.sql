update fnsublanca s
set s.emmanut='S',
s.codemprc=(select codemprc from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codfilialrc=(select codfilialrc from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.codrec=(select codrec from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca),
s.nparcitrec=(select nparcitrec from fnlanca l where l.codemp=s.codemp and l.codfilial=s.codfilial and l.codlanca=s.codlanca);

update fnsublanca
set emmanut='N'
where emmanut='S';

-- select utilizada para gerar update

select slp.codlanca, slp.codsublanca, ir.vlrpagoitrec, slp.vlrsublanca, ir.dtpagoitrec, ir.codemp,  ir.codfilial, ir.codrec, ir.nparcitrec
from fnitreceber ir, fnreceber r, fnsublanca slp
where r.codemp=ir.codemp and r.codfilial=ir.codfilial and r.codrec=ir.codrec and
slp.codempcl=r.codempcl and slp.codfilialcl=r.codfilialcl and slp.codcli=r.codcli and
slp.datasublanca=ir.dtpagoitrec and
slp.vlrsublanca=(ir.vlrpagoitrec*-1) and
ir.statusitrec = 'RP' and
not exists (select * from fnsublanca sl2
where sl2.codemprc=ir.codemp and sl2.codfilialrc=ir.codfilial and
sl2.codrec=ir.codrec and sl2.nparcitrec=ir.nparcitrec);

update fnsublanca sl set
sl.emmanut='S',
sl.codemprc = (select first 1 ir.codemp
from fnitreceber ir, fnreceber r
where r.codemp=ir.codemp and r.codfilial=ir.codfilial and r.codrec=ir.codrec and
sl.codempcl=r.codempcl and sl.codfilialcl=r.codfilialcl and sl.codcli=r.codcli and
sl.datasublanca=ir.dtpagoitrec and sl.vlrsublanca=(ir.vlrpagoitrec*-1) and
ir.statusitrec = 'RP'),
sl.codfilialrc = (select first 1 ir.codfilial
from fnitreceber ir, fnreceber r
where r.codemp=ir.codemp and r.codfilial=ir.codfilial and r.codrec=ir.codrec and
sl.codempcl=r.codempcl and sl.codfilialcl=r.codfilialcl and sl.codcli=r.codcli and
sl.datasublanca=ir.dtpagoitrec and sl.vlrsublanca=(ir.vlrpagoitrec*-1) and
ir.statusitrec = 'RP'),
sl.codrec = (select first 1 ir.codrec
from fnitreceber ir, fnreceber r
where r.codemp=ir.codemp and r.codfilial=ir.codfilial and r.codrec=ir.codrec and
sl.codempcl=r.codempcl and sl.codfilialcl=r.codfilialcl and sl.codcli=r.codcli and
sl.datasublanca=ir.dtpagoitrec and sl.vlrsublanca=(ir.vlrpagoitrec*-1) and
ir.statusitrec = 'RP'),
sl.nparcitrec = (select first 1 ir.nparcitrec
from fnitreceber ir, fnreceber r
where r.codemp=ir.codemp and r.codfilial=ir.codfilial and r.codrec=ir.codrec and
sl.codempcl=r.codempcl and sl.codfilialcl=r.codfilialcl and sl.codcli=r.codcli and
sl.datasublanca=ir.dtpagoitrec and sl.vlrsublanca=(ir.vlrpagoitrec*-1) and
ir.statusitrec = 'RP')
where sl.codcli is not null and sl.codrec is null;

update fnsublanca set emmanut='N' where emmanut='S';

commit work;



