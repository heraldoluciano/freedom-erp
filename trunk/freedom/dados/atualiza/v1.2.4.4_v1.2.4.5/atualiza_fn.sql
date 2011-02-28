update fnitreceber set emmanut='S' where emmanut='N';
update fnitreceber set dtliqitrec=dtpagoitrec;
update fnitreceber set emamnut='N';
commit work;
