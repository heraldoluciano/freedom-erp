-- Após desabilitar todos os triggers...

update eqrma rm set rm.sitexprma='PE';
update eqrma rm set rm.sitaprovrma='PE';
update eqrma rm set rm.sitrma='PE';

update eqitrma ir set ir.sitexpitrma='PE';
update eqitrma ir set ir.sitaprovitrma='PE';
update eqitrma ir set ir.sititrma='PE';

update eqitrma ir set ir.idusuins=(select idusus from sgretinfousu(USER));
update eqrma ir set ir.idusu=(select idusus from sgretinfousu(USER));

commit;
delete from eqitrma;
delete from eqrma;
delete from ppitop;
delete from ppopfase;
delete from ppop;

update sgprefere1 p1 set p1.estneg='S';
update sgprefere1 p1 set p1.estlotneg='S';
update sgprefere1 p1 set p1.estneggrup='N';
commit;
delete from eqsaldoprod;
delete from eqmovprod;

update cpcompra set emmanut='S';
update cpcompra set bloqcompra='N';

update fnitpagar ip set ip.statusitpag='P1';
update fnpagar ip set ip.statuspag='P1';

delete from fnitpagar;
delete from fnparcpag;
delete from fnpagar;

delete from cpitcompra;
delete from cpcompra;


delete from fnsublanca;
delete from fnlanca;
delete from fnsaldolanca;
delete from fnitbordero;
delete from fnbordero;

delete from vdcomissao;

alter trigger fnitrecebertgbu inactive;
alter trigger fnitrecebertgau inactive;

update fnreceber ip set ip.statusrec='R1';
update fnitreceber ip set ip.statusitrec='R1';

alter trigger fnitrecebertgbd active;
delete from fnitreceber;
delete from fnreceber;
alter trigger fnitrecebertgbu active;
alter trigger fnitrecebertgau active;
commit;


delete from lffrete;
delete from lffretecompra;
delete from lffretevenda;
delete from lflivrofiscal;
delete from vdfretevd;
delete from cpfretecp;
delete from fnpagtocomi;
delete from vditcustoorc;
delete from vditcustovenda;

commit;
alter trigger vdvendaorctgad inactive;
delete from vdvendaorc;
alter trigger vdvendaorctgad active;
alter trigger vditvendatgbd inactive;
update vditvenda set emmanut='S';
update vdvenda set emmanut='S';

delete from vditvenda;
delete from vdvenda;

commit;

alter trigger vditvendatgbd active;

delete from vdromaneio;
delete from vdstatusorc;
delete from ppprocessaoptmp;

alter trigger vdorcamentotgbd inactive;
alter trigger vditorcamentotgad inactive;
delete from vditorcamento;
delete from vdorcamento;
alter trigger vdorcamentotgbd active;
alter trigger vditorcamentotgad active;

delete from vdvendacomis;
update lfseqserie set docserie = 0;
update lfserie set docserie = 0;
update sgsequencia set nroseq = 0;
update sgprefere1 p1 set p1.estneg='N';
update sgprefere1 p1 set p1.estneggrup='N';


commit work;





