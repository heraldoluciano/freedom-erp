update fnsublanca sl 
   set sl.emmanut='S', sl.tiposublanca='D' 
    ( codplan=(select codplandc from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) or 
      codplan=(select codplandr from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) );
      
update fnsublanca sl 
   set sl.emmanut='S', sl.tiposublanca='J' 
    ( codplan=(select codplandc from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) or 
      codplan=(select codplandr from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) );
    
update fnsublanca set emmanut='N' where emmanut='S';

commit work;

