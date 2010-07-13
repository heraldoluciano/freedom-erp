-- Após desabilitar todos os triggers...
delete from eqitrma;
delete from eqrma;
delete from ppitop;
delete from ppopfase;
delete from ppop;
delete from eqmovprod;
delete from eqsaldoprod;
delete from fnsublanca;
delete from fnlanca;
delete from fnsaldolanca;
delete from fnitbordero;
delete from fnbordero;
delete from vdcomissao;
delete from lffrete;
delete from lffretecompra;
delete from lffretevenda;
delete from lflivrofiscal;
delete from vdfretevd;
delete from cpfretecp;
delete from fnitreceber;
delete from fnreceber;
delete from fnpagtocomi;
delete from fnitpagar;
delete from fnparcpag;
delete from fnpagar;
delete from vditcustoorc;
delete from vditcustovenda;
delete from vditvenda;
delete from vdvenda;
delete from vdromaneio;
delete from vdstatusorc;
delete from ppprocessaoptmp;
delete from vditorcamento;
delete from vdorcamento;
delete from vdvendaorc;
delete from cpitcompra;
delete from cpcompra;
delete from vdvendacomis;
update lfseqserie set docserie = 0;
update lfserie set docserie = 0;
update sgsequencia set nroseq = 0;

commit work;





