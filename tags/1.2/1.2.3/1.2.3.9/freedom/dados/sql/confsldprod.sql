select * from eqproduto p
where p.sldprod<>(
  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
);

update eqproduto p set p.sldprod =
  (
  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
  )
  where p.sldprod<>
   (  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
  )
;


select p.codalmox,p.codprod from eqsaldoprod p
where p.sldprod<>(
  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        ic.codalmox=p.codalmox and ic.codempax=p.codempax and ic.codfilialax=p.codfilialax and
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        i.codalmox=p.codalmox and i.codempax=p.codempax and i.codfilialax=p.codfilialax and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        iv.codalmox=p.codalmox and iv.codempax=p.codempax and iv.codfilialax=p.codfilialax and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
);

update eqsaldoprod p set p.sldprod =
(
  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        ic.codalmox=p.codalmox and ic.codempax=p.codempax and ic.codfilialax=p.codfilialax and
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        i.codalmox=p.codalmox and i.codempax=p.codempax and i.codfilialax=p.codfilialax and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        iv.codalmox=p.codalmox and iv.codempax=p.codempax and iv.codfilialax=p.codfilialax and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
)
where p.sldprod<>(
  coalesce((select sum(ic.qtditcompra) from cpcompra c, cpitcompra ic, eqtipomov tm
     where ic.codprod=p.codprod and ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and 
        c.codcompra=ic.codcompra and c.codemp=ic.codemp and c.codfilial=ic.codfilial and 
        ic.codalmox=p.codalmox and ic.codempax=p.codempax and ic.codfilialax=p.codfilialax and
        tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and
        tm.estoqtipomov='S'),0) +
  coalesce((select sum(i.qtdinvp) from eqinvprod i, eqtipomov tm
     where i.codprod=p.codprod and i.codemppd=p.codemp and i.codfilialpd=p.codfilial and
        i.codalmox=p.codalmox and i.codempax=p.codempax and i.codfilialax=p.codfilialax and
        tm.codtipomov=i.codtipomov and tm.codemp=i.codemptm and tm.codfilial=i.codfilialtm and
        tm.estoqtipomov='S'),0) -
  coalesce((select sum(iv.qtditvenda) from vditvenda iv, vdvenda v, eqtipomov tm
     where iv.codprod=p.codprod  and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and
        v.codvenda=iv.codvenda and v.tipovenda=iv.tipovenda and v.codemp=iv.codemp and
        iv.codalmox=p.codalmox and iv.codempax=p.codempax and iv.codfilialax=p.codfilialax and
        v.codfilial=iv.codfilial and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm and
        tm.codfilial=v.codfilialtm and tm.estoqtipomov='S'),0)
);

