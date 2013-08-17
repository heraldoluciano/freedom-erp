INSERT INTO ATATENDIMENTO (
    CODEMP, CODFILIAL
    , CODATENDO
    , CODEMPTO, CODFILIALTO, CODTPATENDO
    , CODEMPAE, CODFILIALAE, CODATEND
    , CODEMPSA, CODFILIALSA, CODSETAT
    , DATAATENDO, HORAATENDO, DATAATENDOFIN , HORAATENDOFIN
    , OBSATENDO, STATUSATENDO
    , CODEMPCL, CODFILIALCL, CODCLI
    , IDUSU, CODEMPUS, CODFILIALUS
    , CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR
    , CODEMPCA, CODFILIALCA, CODCLASATENDO
    , CODEMPEA, CODFILIALEA, CODESPEC
    , DTINS, HINS, IDUSUINS, DTALT, HALT, IDUSUALT, DOCATENDO
)

SELECT h.codemp, h.codfilial
   , coalesce((select max(a2.codatendo) from atatendimento a2 where a2.codemp=h.codemp and a2.codfilial=h.codfilial  ),1)+1 codatendo
   , ma.codempto, ma.codfilialto, ma.codtpatendo
   , h.codempae, h.codfilialae, h.codatend
   , ma.codempsa, ma.codfilialsa, ma.codsetat
   , h.DATAHISTTK dataatendo, h.HORAHISTTK horaatendo, h.DATAHISTTK dataatendofin, h.HORAHISTTK+1 horaatendofin
   , h.DESCHISTTK obsatendo, ma.statusatendo
   , h.CODEMPCL ,h.CODFILIALCL, h.CODCLI
   , coalesce(ate.idusu,'sysdba'), coalesce(ate.codempus,e.codemp), coalesce(ate.codfilialus,f.codfilial)
   , ma.codempct, ma.codfilialct, ma.codcontr,  ma.coditcontr
   , ma.codempca,  ma.codfilialca, ma.codclasatendo
   , ma.codempea, ma.codfilialea, ma.codespec
    ,h.DTINS ,h.HINS ,coalesce(h.IDUSUINS,'sysdba')
    ,h.DTALT ,h.HALT,coalesce(h.IDUSUALT,'sysdba')
    ,0 DOCATENDO
FROM tkhistorico h, sgprefere3 p, sgempresa e, sgfilial f, atmodatendo ma, atatendente ate
where p.codemp=e.codemp and p.codfilial=f.codfilial
and f.mzfilial='S'
and h.codemp=f.codemp
and h.codfilial=f.codfilial
and ma.codemp=p.codempmc and ma.codfilial=p.codfilialmc and ma.codmodel=p.codmodelmc
and h.codcli is not null and h.codcamp is null
and ate.codemp=h.codempae and ate.codfilial=h.codfilialae and ate.codatend=h.codatend
;

update sgsequencia s set s.nroseq=coalesce((select max(codatendo)
from atatendimento a where a.codemp=s.codemp and a.codfilial=s.codfilial),0)+1
where s.sgtab='AT';

commit work;



