update fnitreceber set emmanut='S';
update fnitreceber set dtliqitrec=dtpagoitrec;
update fnitreceber set emamnut='N';
update vdvenda set emmanut='S';
update vdvenda set dtcompvenda=dtemitvenda;
update vdvenda set emmanut='N';
commit work;
