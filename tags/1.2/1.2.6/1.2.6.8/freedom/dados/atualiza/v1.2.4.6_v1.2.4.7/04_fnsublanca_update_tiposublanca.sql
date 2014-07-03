update fnsublanca sl 
   set sl.emmanut='S', sl.tiposublanca='D'
   where ( codplan=(select codplandc from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) or 
      codplan=(select codplandr from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) );
      
update fnsublanca sl 
   set sl.emmanut='S', sl.tiposublanca='J' 
   where ( codplan=(select codplanjr from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) or
      codplan=(select codplanjp from sgprefere1 p1 where p1.codemp=sl.codemp and p1.codfilial=sl.codfilial ) );
    
update fnsublanca set emmanut='N' where emmanut='S';

commit work;

