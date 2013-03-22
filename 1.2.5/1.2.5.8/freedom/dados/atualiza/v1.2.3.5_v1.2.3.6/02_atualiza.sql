/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da emrpesa do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequência da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por data de aprovação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por data de fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por código de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprovação do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produção'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da emrpesa do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do ítem de orçamento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequência da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da estação de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por data de aprovação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por data de fabricação'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento é por código de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprovação do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do item de compra (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da empresa do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código da filial do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Código do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (conversão de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';

commit;
