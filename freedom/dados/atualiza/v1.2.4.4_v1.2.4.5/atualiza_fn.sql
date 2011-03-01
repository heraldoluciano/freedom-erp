update fnreceber set emmanut='S', dtcomprec=datarec;
update fnreceber set emmanut='N';
commit work;
update fnitreceber set emmanut='S', dtcompitrec=dtitrec;
update fnitreceber set dtliqitrec=dtpagoitrec;
update fnitreceber set emmanut='N';
commit work;
update vdvenda set emmanut='S', dtcompvenda=dtemitvenda;
update vdvenda set emmanut='N';
commit work;
update fnlanca set emmanut='S', dtcomplanca=datalanca;
update fnlanca set emmanut='N';
commit work;
update fnsublanca set emmanut='S', dtcompsublanca=datasublanca;
update fnsublanca set emmanut='N';
commit work;

