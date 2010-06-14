update vdcliente set RAZCLI='CLIENTE '||cast(codcli as varchar(10));
update vdcliente set NOMECLI='CLIENTE '||cast(codcli as varchar(10));
update vdcliente set EMAILCLI='comercial@stpinf.com';
update vdcliente set ENDCLI='ROD. DEP. JOÃO LEOPOLDO JACOMEL';
update vdcliente set NUMCLI=CODCLI;
update vdcliente set SITECLI ='www.stpinf.com';
update vdcliente set ENDCOB=ENDCLI;
update vdcliente set ENDENT=ENDCLI;
update vdcliente set NUMCOB=CODCLI;
update vdcliente set NUMENT=CODCLI;
update vdcliente set CODPAIS=76;
update vdcliente set CNPJCLI='97324966000116';
update vdcliente set INSCCLI='ISENTA';
update vdcliente set FONECLI='36686500';
update vdcliente set FAXCLI='36686500';
update vdcliente set FONECOB=FONECLI;
update vdcliente set FAXCOB=FAXCLI;
update vdcliente set FONEENT=FONECLI;
update vdcliente set FAXENT=FAXCLI;
update cpforneced set RAZFOR='FORNECEDOR '||cast(codfor as varchar(10));
update cpforneced set NOMEFOR='FORNECEDOR '||cast(codfor as varchar(10));
update cpforneced set EMAILFOR='comercial@stpinf.com';
update cpforneced set ENDFOR='ROD. DEP. JOÃO LEOPOLDO JACOMEL';
update cpforneced set NUMFOR=CODFOR;
update cpforneced set SITEFOR ='www.stpinf.com';
update cpforneced set CODPAIS=76;
update cpforneced set CNPJFOR='97324966000116';
update cpforneced set INSCFOR='ISENTA';
update cpforneced set FONEFOR='36686500';
update cpforneced set FAXFOR='36686500';
update vdvendedor v set v.nomevend='COMISSIONADO '||cast(codvend as varchar(10));
update vdvendedor v set v.emailvend='comercial@stpinf.com';
update vdvendedor v set v.endvend='ROD. DEP. JOÃO LEOPOLDO JACOMEL';
update vdvendedor v set v.numvend=codvend;
update eqproduto p set p.descprod='PRODUTO '||cast(codprod as varchar(13));
update eqproduto p set p.codmarca='geral';
update eqgrupo g set g.descgrup='GRUPO '||G.codgrup;
update eqgrupo g set g.siglagrup=substring( g.descgrup from 1 for 10);
commit work;







