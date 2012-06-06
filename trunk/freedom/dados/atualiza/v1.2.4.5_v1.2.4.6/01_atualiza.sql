/* Server Version: LI-V6.3.3.4870 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

SET AUTODDL ON;

ALTER TABLE SGPREFERE3 DROP CONSTRAINT SGPREFERE3FKTKEMAILEC;

--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.4.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

/* Empty trigger body before drop... */
SET TERM ^ ;

ALTER TRIGGER ATATENDIMENTTGBU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Drop Trigger... */
SET TERM ; ^

DROP TRIGGER ATATENDIMENTTGBU;


Update Rdb$Relations set Rdb$Description =
'Tabela para vinculo entre item de contas a receber e atendimentos, a fim de registrar contatos e atendimentos,
referentes a cobranï¿½as e negociaï¿½ï¿½es de tï¿½tulos.'
where Rdb$Relation_Name='ATATENDIMENTOITREC';

Update Rdb$Relations set Rdb$Description =
'Especificaï¿½ï¿½o dos atendimentos'
where Rdb$Relation_Name='ATESPECATEND';

Update Rdb$Relations set Rdb$Description =
'Tabela de contas contï¿½beis externas.'
where Rdb$Relation_Name='CBCONTAEXT';

Update Rdb$Relations set Rdb$Description =
'Tabela de vinculos para amarraï¿½ï¿½o entre solicitaï¿½ï¿½es de compra
e pedidos/nf de compra.'
where Rdb$Relation_Name='CPCOMPRASOL';

Update Rdb$Relations set Rdb$Description =
'Tabela para relacionamento entre itens de compra e venda, caracterizando a devoluï¿½ï¿½o de mercadorias.'
where Rdb$Relation_Name='CPDEVOLUCAO';

Update Rdb$Relations set Rdb$Description =
'Tabela de informaï¿½ï¿½es sobre importaï¿½ï¿½es.'
where Rdb$Relation_Name='CPIMPORTACAO';

Update Rdb$Relations set Rdb$Description =
'Tabela de adiï¿½ï¿½es da importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAOADIC';

Update Rdb$Relations set Rdb$Description =
'Tabela de item de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO';

Update Rdb$Relations set Rdb$Description =
'Tabela para qualificaï¿½ï¿½o de chamados/atendimentos.'
where Rdb$Relation_Name='CRQUALIFICACAO';

Update Rdb$Relations set Rdb$Description =
'Amarraï¿½ï¿½o Almoxarifado x Filiais.'
where Rdb$Relation_Name='EQALMOXFILIAL';

Update Rdb$Relations set Rdb$Description =
'Amarraï¿½ï¿½o Cliente x Fornecedor.'
where Rdb$Relation_Name='EQCLIFOR';

Update Rdb$Relations set Rdb$Description =
'Tabela de cï¿½digos alternativos para produtos.'
where Rdb$Relation_Name='EQCODALTPROD';

Update Rdb$Relations set Rdb$Description =
'Mï¿½dia entre as amostragens.'
where Rdb$Relation_Name='EQITRECMERC';

Update Rdb$Relations set Rdb$Description =
'Tabela de vinculo entre ï¿½tens de recebimento de mercadoria e itens de compra.'
where Rdb$Relation_Name='EQITRECMERCITCP';

Update Rdb$Relations set Rdb$Description =
'Tabela para lanï¿½amento de componentes e serviï¿½os utilizando em ordem de produï¿½ï¿½o /eqitrecmerc.'
where Rdb$Relation_Name='EQITRECMERCITOS';

Update Rdb$Relations set Rdb$Description =
'ï¿½tens de RMA.'
where Rdb$Relation_Name='EQITRMA';

Update Rdb$Relations set Rdb$Description =
'Tabela de modelos de lotes, para geraï¿½ï¿½o automatica de lotes a partir da Ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQMODLOTE';

Update Rdb$Relations set Rdb$Description =
'Tabela de controle de movimentaï¿½ï¿½es de nemeros de sï¿½rie de produtos.'
where Rdb$Relation_Name='EQMOVSERIE';

Update Rdb$Relations set Rdb$Description =
'Tabela de controle de acesso do produto para RMA, PDV e outras implementaï¿½ï¿½es futuras.'
where Rdb$Relation_Name='EQPRODACESSO';

Update Rdb$Relations set Rdb$Description =
'Tabela de vï¿½nculo produto x planejamento, para uso nos rateios.'
where Rdb$Relation_Name='EQPRODPLAN';

Update Rdb$Relations set Rdb$Description =
'Tabela de registro de informaï¿½ï¿½es do recebimento de cargas de mercadorias.'
where Rdb$Relation_Name='EQRECMERC';

Update Rdb$Relations set Rdb$Description =
'Tabela de seï¿½ï¿½es de produï¿½ï¿½o/estoque.'
where Rdb$Relation_Name='EQSECAO';

Update Rdb$Relations set Rdb$Description =
'Tabela para controle de nÃºmeros de sï¿½rie de produtos.'
where Rdb$Relation_Name='EQSERIE';

Update Rdb$Relations set Rdb$Description =
'Tabela de vï¿½nculo de tipos de movimento com usuï¿½rios.'
where Rdb$Relation_Name='EQTIPOMOVUSU';

Update Rdb$Relations set Rdb$Description =
'Carteiras de cobranï¿½a.'
where Rdb$Relation_Name='FNCARTCOB';

Update Rdb$Relations set Rdb$Description =
'Tabela de vinculo entre contas para composisï¿½o de saldo interno/externo'
where Rdb$Relation_Name='FNCONTAVINCULADA';

Update Rdb$Relations set Rdb$Description =
'Classificaï¿½ï¿½o de finalidade das contas financeiras.'
where Rdb$Relation_Name='FNFINALIDADE';

Update Rdb$Relations set Rdb$Description =
'Tabela de histï¿½rico padrï¿½o.'
where Rdb$Relation_Name='FNHISTPAD';

Update Rdb$Relations set Rdb$Description =
'Tabela de amarraï¿½ï¿½o modelo de boleto e banco'
where Rdb$Relation_Name='FNITMODBOLETO';

Update Rdb$Relations set Rdb$Description =
'Tabela para gerenciamento de tï¿½tulos renegociados.'
where Rdb$Relation_Name='FNRENEGREC';

Update Rdb$Relations set Rdb$Description =
'Tabela de restriï¿½ï¿½es dos clientes.'
where Rdb$Relation_Name='FNRESTRICAO';

Update Rdb$Relations set Rdb$Description =
'Tabela de sinalizaï¿½ï¿½o de lanï¿½amentos.'
where Rdb$Relation_Name='FNSINAL';

Update Rdb$Relations set Rdb$Description =
'Tabela de talonï¿½rio de cheques.'
where Rdb$Relation_Name='FNTALAOCHEQ';

Update Rdb$Relations set Rdb$Description =
'Tipos de restriï¿½ï¿½es.'
where Rdb$Relation_Name='FNTIPORESTR';

Update Rdb$Relations set Rdb$Description =
'Tabela de situaï¿½ï¿½o da operaï¿½ï¿½o no simples nacional.'
where Rdb$Relation_Name='LFCSOSN';

Update Rdb$Relations set Rdb$Description =
'Tabela para lanï¿½amento de conhecimentos de frete.'
where Rdb$Relation_Name='LFFRETE';

Update Rdb$Relations set Rdb$Description =
'Tabela auxiliar de informaï¿½ï¿½es fiscais do item de compra.'
where Rdb$Relation_Name='LFITCOMPRA';

Update Rdb$Relations set Rdb$Description =
'Tabela auxiliar de informaï¿½ï¿½es fiscais do item de venda.'
where Rdb$Relation_Name='LFITVENDA';

Update Rdb$Relations set Rdb$Description =
'Tabela de sequencia de sï¿½ries.'
where Rdb$Relation_Name='LFSEQSERIE';

Update Rdb$Relations set Rdb$Description =
'Tabela de situaï¿½ï¿½es tributï¿½rias para os impostos:
PIS e Cofins.'
where Rdb$Relation_Name='LFSITTRIB';

Update Rdb$Relations set Rdb$Description =
'Tabela de tratamentos tributï¿½rios de ICMS.'
where Rdb$Relation_Name='LFTRATTRIB';

Update Rdb$Relations set Rdb$Description =
'Distribuiï¿½ï¿½o da estrutura para mais de um produto final.'
where Rdb$Relation_Name='PPDISTRIB';

Update Rdb$Relations set Rdb$Description =
'Vï¿½nculo estrutura, fase de produï¿½ï¿½o e recursos de produï¿½ï¿½o.
'
where Rdb$Relation_Name='PPESTRUFASE';

Update Rdb$Relations set Rdb$Description =
'Fases de produï¿½ï¿½o
'
where Rdb$Relation_Name='PPFASE';

Update Rdb$Relations set Rdb$Description =
'Fotos das caracterï¿½sticas dos mï¿½todos analï¿½ticos.'
where Rdb$Relation_Name='PPFOTOMTAN';

Update Rdb$Relations set Rdb$Description =
'ï¿½tens de estrutura de produtos.'
where Rdb$Relation_Name='PPITESTRUTURA';

Update Rdb$Relations set Rdb$Description =
'ï¿½tens de ordens de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPITOP';

Update Rdb$Relations set Rdb$Description =
'Cadastro de mï¿½todos de anï¿½lise.'
where Rdb$Relation_Name='PPMETODOANALISE';

Update Rdb$Relations set Rdb$Description =
'Ordens de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOP';

Update Rdb$Relations set Rdb$Description =
'Cadastro de aï¿½ï¿½es corretivas.'
where Rdb$Relation_Name='PPOPACAOCORRET';

Update Rdb$Relations set Rdb$Description =
'Cadastros de lanï¿½amento de resultados de anï¿½lises de controle de qualidade.'
where Rdb$Relation_Name='PPOPCQ';

Update Rdb$Relations set Rdb$Description =
'Vï¿½nculo OP x Fase
'
where Rdb$Relation_Name='PPOPFASE';

Update Rdb$Relations set Rdb$Description =
'Tabela para lanï¿½amento de sub-produtos em ordens de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPSUBPROD';

Update Rdb$Relations set Rdb$Description =
'Tabela temporï¿½ria para geraï¿½ï¿½o de ordens de produï¿½ï¿½o com base em orï¿½amentos.'
where Rdb$Relation_Name='PPPROCESSAOPTMP';

Update Rdb$Relations set Rdb$Description =
'Recursos de produï¿½ï¿½o
'
where Rdb$Relation_Name='PPRECURSO';

Update Rdb$Relations set Rdb$Description =
'Retenï¿½ï¿½o de contra-provas (controle de qualidade).'
where Rdb$Relation_Name='PPRETCP';

Update Rdb$Relations set Rdb$Description =
'Tabela para cadastro dos tipos de anï¿½lise de controle de qualidade.'
where Rdb$Relation_Name='PPTIPOANALISE';

Update Rdb$Relations set Rdb$Description =
'Tipos de recurso de produï¿½ï¿½o.
'
where Rdb$Relation_Name='PPTIPOREC';

Update Rdb$Relations set Rdb$Description =
'Areas de negï¿½cio para cursos e vagas mï¿½dulo RH (Recrutamento e seleï¿½ï¿½o).'
where Rdb$Relation_Name='RHAREA';

Update Rdb$Relations set Rdb$Description =
'Cadastro de benefï¿½cios.'
where Rdb$Relation_Name='RHBENEFICIO';

Update Rdb$Relations set Rdb$Description =
'Cadastro de candidatos a vaga para uso no mï¿½dulo de recrutamento e seleï¿½ï¿½o.'
where Rdb$Relation_Name='RHCANDIDATO';

Update Rdb$Relations set Rdb$Description =
'Tabela de histï¿½rico dos status do candidato.'
where Rdb$Relation_Name='RHCANDIDATOSTATUS';

Update Rdb$Relations set Rdb$Description =
'Cadastro de cursos para utilizaï¿½ï¿½o no mï¿½dulo de RH (Recrutamento e seleï¿½ï¿½o).'
where Rdb$Relation_Name='RHCURSO';

Update Rdb$Relations set Rdb$Description =
'Tabela de vï¿½nculos entre empregados e seus benefï¿½cios.'
where Rdb$Relation_Name='RHEMPREGADOBENEF';

Update Rdb$Relations set Rdb$Description =
'Cadastro de empregadores conveniados para mï¿½dulo de recrutamento e seleï¿½ï¿½o.'
where Rdb$Relation_Name='RHEMPREGADOR';

Update Rdb$Relations set Rdb$Description =
'Table de histï¿½rio salarial do empregado.'
where Rdb$Relation_Name='RHEMPREGADOSAL';

Update Rdb$Relations set Rdb$Description =
'Tabela de funï¿½ï¿½es profissionais.'
where Rdb$Relation_Name='RHFUNCAO';

Update Rdb$Relations set Rdb$Description =
'Cadastro dos nï¿½veis dos cursos do mï¿½dulo de RH (Recrutamento e seleï¿½ï¿½o).'
where Rdb$Relation_Name='RHNIVELCURSO';

Update Rdb$Relations set Rdb$Description =
'Tabela para registro do ponto dos funcionï¿½rios.'
where Rdb$Relation_Name='RHPONTO';

Update Rdb$Relations set Rdb$Description =
'Tabela de vï¿½nculo entre vaga e candidatos.'
where Rdb$Relation_Name='RHVAGACANDIDATO';

Update Rdb$Relations set Rdb$Description =
'Tabela de relacionamento entre vagas e atribuiï¿½ï¿½es restritivas.'
where Rdb$Relation_Name='RHVAGACARACREST';

Update Rdb$Relations set Rdb$Description =
'Tabela de vï¿½nculo entre vagas e cursos.'
where Rdb$Relation_Name='RHVAGACURSO';

Update Rdb$Relations set Rdb$Description =
'Tabela histï¿½rica dos status das vagas.'
where Rdb$Relation_Name='RHVAGASTATUS';

Update Rdb$Relations set Rdb$Description =
'Tabela de atividades padrï¿½o CNAE.'
where Rdb$Relation_Name='SGCNAE';

Update Rdb$Relations set Rdb$Description =
'Tabela de preferï¿½ncias Febraban detalhada por banco e tipo (SIACC E CNAB)'
where Rdb$Relation_Name='SGITPREFERE6';

Update Rdb$Relations set Rdb$Description =
'Preferï¿½ncias do mï¿½dulo STD (Gerais).'
where Rdb$Relation_Name='SGPREFERE1';

Update Rdb$Relations set Rdb$Description =
'Preferï¿½ncias do mï¿½dulo ATD.'
where Rdb$Relation_Name='SGPREFERE2';

Update Rdb$Relations set Rdb$Description =
'Preferencias do mï¿½dulo TMK.'
where Rdb$Relation_Name='SGPREFERE3';

Update Rdb$Relations set Rdb$Description =
'Preferï¿½ncias do mï¿½dulo de PDV
'
where Rdb$Relation_Name='SGPREFERE4';

Update Rdb$Relations set Rdb$Description =
'Preferï¿½ncias do mï¿½dulo de PCP
'
where Rdb$Relation_Name='SGPREFERE5';

Update Rdb$Relations set Rdb$Description =
'Tabela de preferï¿½ncias Febraban (SIACC E CNAB)'
where Rdb$Relation_Name='SGPREFERE6';

Update Rdb$Relations set Rdb$Description =
'Tabela de preferï¿½ncias para mecanismo de venda consignada.'
where Rdb$Relation_Name='SGPREFERE7';

Update Rdb$Relations set Rdb$Description =
'Tabela de preferencias do mï¿½dulo GMS.'
where Rdb$Relation_Name='SGPREFERE8';

Update Rdb$Relations set Rdb$Description =
'Tabela de unificaï¿½ï¿½o de cï¿½digos para realizaï¿½ï¿½o de vï¿½nculos em tabelas heterogï¿½neas;'
where Rdb$Relation_Name='SGUNIFCOD';

Update Rdb$Relations set Rdb$Description =
'Cabeï¿½alho para ordem de serviï¿½o.
'
where Rdb$Relation_Name='SVOS';

Update Rdb$Relations set Rdb$Description =
'Tabela de configuraï¿½ï¿½es para utilizaï¿½ï¿½o nos emails de campanhas de marketing.'
where Rdb$Relation_Name='TKCONFEMAIL';

Update Rdb$Relations set Rdb$Description =
'Tabela da situaï¿½ï¿½o do cantato com relaï¿½ï¿½o Ã  campanha, de acordo com a atividade desenvolvida.'
where Rdb$Relation_Name='TKSITCAMP';

Update Rdb$Relations set Rdb$Description =
'Tabela da situaï¿½ï¿½o do cliente com relaï¿½ï¿½o Ã  campanha, de acordo com a atividade desenvolvida.'
where Rdb$Relation_Name='TKSITCAMPCL';

Update Rdb$Relations set Rdb$Description =
'Classificaï¿½ï¿½o de comissï¿½es
'
where Rdb$Relation_Name='VDCLCOMIS';

Update Rdb$Relations set Rdb$Description =
'Ficha de informaï¿½ï¿½es complementares do cliente.'
where Rdb$Relation_Name='VDCLICOMPL';

Update Rdb$Relations set Rdb$Description =
'Referï¿½ncias comerciais do cliente.'
where Rdb$Relation_Name='VDCLIREFC';

Update Rdb$Relations set Rdb$Description =
'Veï¿½culos do cliente.'
where Rdb$Relation_Name='VDCLIVEIC';

Update Rdb$Relations set Rdb$Description =
'  tabela temporï¿½ria para impressï¿½o de etiquetas de cliente.'
where Rdb$Relation_Name='VDETIQCLI';

Update Rdb$Relations set Rdb$Description =
'Tabela de ï¿½tens de contrato.'
where Rdb$Relation_Name='VDITCONTRATO';

Update Rdb$Relations set Rdb$Description =
'Tabela para lanï¿½amento do andamento de execuï¿½ï¿½o de um projeto.'
where Rdb$Relation_Name='VDITCONTRATOAND';

Update Rdb$Relations set Rdb$Description =
'Regras de comissï¿½'
where Rdb$Relation_Name='VDREGRACOMIS';

Update Rdb$Relations set Rdb$Description =
'Tabela de vendas em consignaï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDACONSIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para observaï¿½ï¿½es de interesse interno.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='OBSINTERNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do contrato.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODEMPCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do contrato.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODFILIALCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contrato.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de contrato.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do chamado.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODEMPCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do chamado.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODFILIALCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do Chamado.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODCHAMADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de especificaï¿½ï¿½o.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODEMPEA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de especificaï¿½ï¿½o.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODFILIALEA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de especificaï¿½ï¿½o.'
where Rdb$Relation_Name='ATATENDIMENTO' and Rdb$Field_Name='CODESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar cobranï¿½a no relatï¿½rio de atendimentos.'
where Rdb$Relation_Name='ATCLASATENDO' and Rdb$Field_Name='GERACOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve aparecer no relatï¿½rio de atendimentos.'
where Rdb$Relation_Name='ATCLASATENDO' and Rdb$Field_Name='GERAREL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa - Encaminhador
'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODEMPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de encaminhador.
'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODFILIALEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do encaminhador.
'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela RHFUNCAO'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODEMPFC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela RHFUNCAO.'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODFILIALFC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da funï¿½ï¿½o.'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='CODFUNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo DDD do telefone principal do conveniado.'
where Rdb$Relation_Name='ATCONVENIADO' and Rdb$Field_Name='DDDCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do encaminhador
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='CODENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='ENDENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do logradouro
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='NUMENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Complemento do endereï¿½o
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='COMPLENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do fax
'
where Rdb$Relation_Name='ATENCAMINHADOR' and Rdb$Field_Name='FAXENC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da especificaï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='CODESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da especificaï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='DESCESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Paga comissï¿½o (S/N).'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='PGCOMIESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Tempo mï¿½nimo a contabilizar.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='TEMPOMINCOBESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Tempo mï¿½ximo a contabilizar.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='TEMPOMAXCOBESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de comissï¿½o do atendente.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='PERCCOMIESPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que fez a Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='ATESPECATEND' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='DTCOMPCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de cï¿½lculo do ICMS de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='VLRBASEICMSSTCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do ICMS de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='VLRICMSSTCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da retenï¿½ï¿½o referente ao Funrural.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='VLRFUNRURALCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela tipo de cobranï¿½a.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela tipo de cobranï¿½a.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de cobranï¿½a.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODTIPOCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a tabela estï¿½ em manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo "coringa" para outras observaï¿½ï¿½es.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='OBS01';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo "coringa" para outras observaï¿½ï¿½es.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='OBS02';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo "coringa" para outras observaï¿½ï¿½es.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='OBS03';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo "coringa" para outras observaï¿½ï¿½es.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='OBS04';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do documento de importaï¿½ï¿½o DI/DSI/DA'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='NRODI';

Update Rdb$Relation_Fields set Rdb$Description =
'Loca de desembaraï¿½ï¿½ da DI'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='LOCDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado onde ocorreu o desembaraï¿½o da DI'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='SIGLAUFDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s onde ocorreu o desembaraï¿½o da DI (serï¿½ sempre o pais da filial, mantido por integridade referencial tabela SGUF)'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODPAISDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Data do desembaraï¿½o da DI'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='DTDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do container da compra (importaï¿½ï¿½o)'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='IDENTCONTAINER';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do recebimento de mercadoria vinculado.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPRM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do recebimento de mercadoria vinculado.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALRM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da compra.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='NUMCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do centro de custos'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do centro de custos.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custos'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do filial do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento financeiro.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Informaï¿½ï¿½es complementares (interesse do fisco)'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='INFCOMPL';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do ato concessï¿½rio do regime drawback (importaï¿½ï¿½o)'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='NUMACDRAW';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de documento de importaï¿½ï¿½o:
0 - Declaraï¿½ï¿½o de importaï¿½ï¿½o
1 - Declaraï¿½ï¿½o simplificada de importaï¿½ï¿½'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='TIPODOCIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do documento fiscal:
00-Documento regular;
01-Documento regular expontï¿½neo;
02-Documento cancelado;
03-Documento cancelado expontï¿½neo
04-NFE Denegada;
05-NFE Numeraï¿½ï¿½o inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expontï¿½neo;
08-Documento emitido com base em Regime Especial ou Norma Especï¿½fica;'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='SITDOC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODEMPIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODFILIALIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da importaï¿½ï¿½o.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='CODIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do imposto de importaï¿½ï¿½o.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='VLRBASEIICOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do imposto de importaï¿½ï¿½o da compra.
'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='VLRIICOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra.'
where Rdb$Relation_Name='CPCOMPRASOL' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da solicitaï¿½ï¿½o de compra.'
where Rdb$Relation_Name='CPCOMPRASOL' and Rdb$Field_Name='CODSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de validade da proposta de preï¿½o.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='DTVALIDCOT';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve usar o critï¿½rio da renda na busca do preï¿½o de cotaï¿½ï¿½es.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='USARENDACOT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODEMPPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODFILIALPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do plano de pagamento.'
where Rdb$Relation_Name='CPCOTACAO' and Rdb$Field_Name='CODPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da compra.'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da compra.'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra.'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de compra.'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da venda (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da venda (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo da venda (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='TIPOVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem da venda (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='CPDEVOLUCAO' and Rdb$Field_Name='CODITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do celular
'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CELFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Orgï¿½o de espediï¿½ï¿½o do rg.'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='SSPFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o eletrÃ´nico do site do fornecedor.'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='SITEFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo contabil'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CODFORCONTAB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo na tabela de unificaï¿½ï¿½o de cï¿½digos (SGUNIFCOD).'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CODUNIFCOD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo fiscal do fornecedor.'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CODEMPFF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo fiscal do fornecedor.'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CODFILIALFF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo fiscal do fornecedor'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='CODFISCFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do SUFRAMA do fornecedor.'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='SUFRAMAFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dependes do fornecedor (pessoa fï¿½sica) (calculo de irrf)'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='NRODEPENDFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Inscriï¿½ï¿½o no conselho regional (contabilidae, administraï¿½ï¿½o, medicina, etc..)'
where Rdb$Relation_Name='CPFORNECED' and Rdb$Field_Name='INSCCONREG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODEMPMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFILIALMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODMOEDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Fator de conversï¿½o da moeda de importaï¿½ï¿½o para moeda corrente.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='COTACAOMOEDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do plano de pagamento.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODEMPPG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do plano de pagamento'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFILIALPG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do plano de pagamento acordado no invoice.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do fornecedor.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODEMPFR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do fornecedor.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFILIALFR';

Update Rdb$Relation_Fields set Rdb$Description =
' Cï¿½digo do fornecedor na importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do invoice (pedido de compra de importaï¿½ï¿½o)'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='INVOICE';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da declaraï¿½ï¿½o de importaï¿½ï¿½o .'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DI';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do manifesto de carga.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='MANIFESTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do lacre'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='LACRE';

Update Rdb$Relation_Fields set Rdb$Description =
'Presenï¿½a de carga'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='PRESCARGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o House B/L'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='IDENTHOUSE';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do conhecimento de carga.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CONHECCARGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do container'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='IDENTCONTAINER';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DTIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Data do desembaraï¿½o da declaraï¿½ï¿½o de importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DTDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de registro da declaraï¿½ï¿½o de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DTREGDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Local do desembaraï¿½o da declaraï¿½ï¿½o de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='LOCALEMB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s do desembaraï¿½o da DI'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='CODPAISDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla da unidade da federaï¿½ï¿½o do desembaraï¿½o da DI'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='SIGLAUFDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
' Local de desembaraï¿½o da DI.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='LOCDESEMBDI';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='OBS';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do veï¿½culo (ex.: Nome do navio)'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VEICULO';

Update Rdb$Relation_Fields set Rdb$Description =
'Peso lï¿½quido total'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='PESOLIQUIDO';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do frete na moeda de importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRFRETEMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor liquido sem o frete na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VMLEMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor liquido mais o frete na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VMLDMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do seguro na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRSEGUROMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do imposto de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da taxa de manutenï¿½ï¿½o no terminal (Terminal Handling Charges)'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRTHC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do THC em moeda de importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRTHCMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor aduaneiro em moeda de importaï¿½ï¿½o
'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='VLRADMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que inseriu o registro'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='CPIMPORTACAO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da importaï¿½ï¿½'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='CODIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da NCM'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='CODNCM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da adicao'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='CODADIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da taxa siscomex para a adicï¿½'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='VLRTXSISCOMEX';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio de inseriu o registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que alterou o registro'
where Rdb$Relation_Name='CPIMPORTACAOADIC' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de almoxarifados.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de almoxarifados.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor para base de calculo da retenï¿½ï¿½o do funrural.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='VLRBASEFUNRURALITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de retenï¿½ï¿½o do funrural no item de compra.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='ALIQFUNRURALITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de retenï¿½ï¿½o do funrural do item.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='VLRFUNRURALITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Custo para formaï¿½ï¿½o de preï¿½os
'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CUSTOVDITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da empresa do item de classificaï¿½ï¿½o (regra no momento da inserï¿½ï¿½o)'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODEMPIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da filial do item de classificaï¿½ï¿½o (regra no momento da inserï¿½ï¿½o)'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODFILIALIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da classificaï¿½ï¿½o (regra no momento da inserï¿½ï¿½o)'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a tabela estï¿½ em manutenï¿½ï¿½o.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do nÃºmero de sï¿½rie'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODEMPNS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do nÃºmero de sï¿½rie
'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='CODFILIALNS';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para abrigar temporariamente o nÃºmero de serie do produto (para uso do trigger quando produto for unitï¿½rio)
'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='NUMSERIETMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da adicao (entrada de importaï¿½ï¿½o)'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='NADICAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial do ï¿½tem dentro da adiï¿½ï¿½o.'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='SEQADIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o ï¿½tem de compra jï¿½ foi emitido em compra compra (processo de faturamento de pedidos de compra)'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='EMITITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o preï¿½o da compra foi aprovado (veio de cotaï¿½ï¿½o anterior);'
where Rdb$Relation_Name='CPITCOMPRA' and Rdb$Field_Name='APROVPRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODITIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODEMPPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do produto.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODFILIALPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Referï¿½ncia do produto.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='REFPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da unidade de medida.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODEMPUN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da unidade de medida.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODFILIALUN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da unidade de medida
'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODUNID';

Update Rdb$Relation_Fields set Rdb$Description =
'Preco na moeda de importaï¿½ï¿½'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='PRECOMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o na moeda corrente.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='PRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor bruto da mercadoria na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VMLEMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor bruto da mercadoria + frete e seguro, na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VMLDMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do frete na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRFRETEMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do seguro na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRSEGUROMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do THC na moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRTHCMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor aduaneiro (Valor bruto+frete+seguro+thc) em moeda de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRADMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do ICMS de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='ALIQICMSIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do ICMS da UF (Operaï¿½ï¿½o futura)'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='ALIQICMSUF';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do imposto de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='ALIQII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do imposto de importaï¿½ï¿½o.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do crï¿½dito do ICMS presumido.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRICMSCREDPRESUM';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da taxa siscomex rateada por ï¿½tem.
'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='VLRTXSISCOMEX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do da classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODEMPCF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODFILIALCF';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODITFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da NCM'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='CODNCM';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do item na adiï¿½ï¿½o.
'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='SEQADIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='CPITIMPORTACAO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da solicitaï¿½ï¿½'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial do item'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para produtos'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODEMPPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para produtos'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODFILIALPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto ou serviï¿½'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Referï¿½ncia do produto.'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='REFPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que solicitou'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='IDUSUITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da solicitaï¿½ï¿½o do item'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='DTITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que aprovou'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='IDUSUAPROVITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da aprovaï¿½ï¿½o do item'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='DTAPROVITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da aprovaï¿½ï¿½o do item:
PE - Pendente
AP - Aprovaï¿½ï¿½o parcial
AT - Aprovaï¿½ï¿½o total
NA - Nï¿½o aprovada'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='SITAPROVITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da compra:
PE - Pendente
CP - Compra parcial
CT - Compra total
'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='SITCOMPITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do item da solicitaï¿½ï¿½o:
PE - Pendente
FN - Solicitaï¿½ï¿½o finalizada
CA - Cancelada'
where Rdb$Relation_Name='CPITSOLICITACAO' and Rdb$Field_Name='SITITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial do rateio.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='SEQRATEIO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNPLANEJAMENTO'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODEMPFN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNPLANEJAMENTO.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODFILIALFN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCC.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCC'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custo.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da conciliaï¿½ï¿½o do rateio:
N-Pendente
P-Parcial
T-Total'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='SITRATEIO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que fez a Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='CPRATEIO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da solicitaï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='CODSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de emissï¿½o da solicitaï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='DTEMITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'PE - Pendente
CT - Em Cotaï¿½ï¿½o
EF - Finalizada
CA - Cancelada'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='SITSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Origem da solicitaï¿½ï¿½o
RM - RMA
AX - ALMOXARIFE'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='ORIGSOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio da Ãºltima alteraï¿½ï¿½'
where Rdb$Relation_Name='CPSOLICITACAO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a retenï¿½ï¿½o do inss nos pagamentos (autonomo).'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOINSS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve calcular outras retenï¿½ï¿½es agregadas ao INSS ex.: SEST/SENAT'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOOUTROS';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de retenï¿½ï¿½o de outros tributos agregados ao inss (SEST/SENAT)'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='PERCRETOUTROS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a retenï¿½ï¿½o do imposto de renda nos pagamentos (autonomo).'
where Rdb$Relation_Name='CPTIPOFOR' and Rdb$Field_Name='RETENCAOIRRF';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o da aï¿½ï¿½o que gerou o problema.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='FATOGERADOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Informaï¿½ï¿½es sobre o ambiente onde ocorreu o fato;
'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='AMBIENTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Outras observaï¿½ï¿½es sobre o chamado'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='OBSCHAMADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de chamado'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de chamado'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de chamado.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODTPCHAMADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Status do chamado:
PE - Pendente
AN - Em analise
EA - Em andamento
CA - Cancelado
CO - Concluï¿½do'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='STATUS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de previsï¿½o do encerramento.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='DTPREVISAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade de horas prevista pra execuï¿½ï¿½o do chamado.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='QTDHORASPREVISAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de conclusï¿½o do chamado'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='DTCONCLUSAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do atendente designado.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODEMPAE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do atendente designado.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODATEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do item de ordem de serviï¿½o.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODEMPOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ordem de serviï¿½o.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODFILIALOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket da ordem de serviï¿½o.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='TICKET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de recebimento de mercadorias.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem da ordem de serviï¿½o.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODITOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o chamado estï¿½ sendo atendido neste momento.'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='EMATENDIMENTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da qualificaï¿½ï¿½o do chamado'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODEMPQL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da qualificaï¿½ï¿½o do chamado.
'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODFILIALQL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da qualificaï¿½ï¿½o do atendimento.
'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='CODQUALIFIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do anexo'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do anexo;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½diugo do chamado;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='CODCHAMADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do anexo;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='CODANEXO';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do anexo;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='DESCANEXO';

Update Rdb$Relation_Fields set Rdb$Description =
'Arquivo Binï¿½rio.
'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='ANEXO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro no banco de dados;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro no banco de dados'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro no banco de dados;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o do registro no banco de dados;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do registro no banco de dados;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que alterou o registro no banco de dados;'
where Rdb$Relation_Name='CRCHAMADOANEXO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da qualificaï¿½ï¿½'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='CODQUALIFIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da qualificaï¿½ï¿½'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='DESCQUALIFIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a qualificaï¿½ï¿½o finaliza o processo (chamado/atendimento)
"S" - Sim
"N" - Nï¿½'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='FINALIZA';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que inseriu o registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de altereï¿½ï¿½o do registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do usuï¿½rio que alterou o registro'
where Rdb$Relation_Name='CRQUALIFICACAO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela EQALMOX.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela EQALMOX.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela SGFILIAL.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='CODEMPAF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela SGFILIAL.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='CODFILIALAF';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se ï¿½ possï¿½vel baixar o estoque dessa filial.'
where Rdb$Relation_Name='EQALMOXFILIAL' and Rdb$Field_Name='BAIXAESTOQAF';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o tipo de conversï¿½o:
"U" - Apenas converte a unidade no mesmo produto;
"P" - Executa processo de conversï¿½o de produtos atravï¿½s de estrutura;'
where Rdb$Relation_Name='EQFATCONV' and Rdb$Field_Name='TIPOCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a unidade ï¿½ preferencial para compras.'
where Rdb$Relation_Name='EQFATCONV' and Rdb$Field_Name='CPFATCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto da estrutura de conversï¿½o.'
where Rdb$Relation_Name='EQFATCONV' and Rdb$Field_Name='CODPRODET';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es relativas ao inventï¿½rio, como motivo, justificativa, etc.'
where Rdb$Relation_Name='EQINVPROD' and Rdb$Field_Name='OBSINVP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o detalhada do item recebido.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='DESCITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de sï¿½rie.
'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='NUMSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o produto recebido estï¿½ na garantia - (S/N)'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='GARANTIA';

Update Rdb$Relation_Fields set Rdb$Description =
'Referï¿½ncia do produto
'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='REFPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do processo de recebimento.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='CODEMPTP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do processo de recebimento.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='CODFILIALTP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de recebimento de mercadorial.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='CODTIPORECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do processo de recepï¿½ï¿½o de mercadoria.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='CODPROCRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para informar o serviï¿½o solicitado pelo cliente no item recebido.
'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='SERVICOSOLICITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para informar os acessï¿½rios que acompanham o item recebido.'
where Rdb$Relation_Name='EQITRECMERC' and Rdb$Field_Name='OBSITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo sequencial do item de ordem de serviï¿½o.
'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='CODITOS';

Update Rdb$Relation_Fields set Rdb$Description =
'"PE" - Item pendente / previsto / Orï¿½ado
"EC" - Encaminhado / Chamado aberto / RMA aberta
"EA" - Em andamento
"CO" - Item Concluï¿½do;'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='STATUSITOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar RMA para o ï¿½tem de O.S.'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='GERARMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o item de OS ï¿½ um produto novo para substituiï¿½ï¿½o, 
ao com defeito;'
where Rdb$Relation_Name='EQITRECMERCITOS' and Rdb$Field_Name='GERANOVO';

Update Rdb$Relation_Fields set Rdb$Description =
'Coluna de status para repasse via triggers Ã  tabela de ordem de seviï¿½o na atualizaï¿½ï¿½o do status do orï¿½amento.
(Vide status da ordem de seviï¿½o).'
where Rdb$Relation_Name='EQITRECMERCITOSITORC' and Rdb$Field_Name='STATUS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de almoxarifados.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filail na tabela de almoxarifados.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da aprovaï¿½ï¿½o do item.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='DTAPROVITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o geral do item de rma:
PE - Pendente 
EA - Em andamento
AF - Aprovaï¿½ï¿½o finalizada
EF - Expediï¿½ï¿½o finalizada
CA - Cancelado 
'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='SITITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da aprovaï¿½ï¿½o do item:
"PE" - Aprovaï¿½ï¿½o pendente
"AP" - Aprovaï¿½ï¿½o parcial
"AT" - Aprovaï¿½ï¿½o total
"NA" - Nï¿½o aprovado'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='SITAPROVITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da expediï¿½ï¿½o do item:
"PE" - Expediï¿½ï¿½o pendente
"EP" - Expediï¿½ï¿½o parcial
"ET" - Expediï¿½ï¿½o total
"NE" - Nï¿½o expedida'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='SITEXPITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Motivo do cancelamento do ï¿½tem de rma.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='MOTIVOCANCITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODEMPOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODFILIALOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='TICKET';

Update Rdb$Relation_Fields set Rdb$Description =
'Item de recebimento da ordem de serviï¿½o vinculada'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Item de suplemento da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='EQITRMA' and Rdb$Field_Name='CODITOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do modelo de lote.'
where Rdb$Relation_Name='EQMODLOTE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do modelo de lote.'
where Rdb$Relation_Name='EQMODLOTE' and Rdb$Field_Name='CODMODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do modelo de lote.'
where Rdb$Relation_Name='EQMODLOTE' and Rdb$Field_Name='DESCMODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de almoxarifados.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de almoxarifados.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODEMPOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da filial da Ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODFILIALOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da Ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='CODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da OP.'
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial de entrada da ordem de produï¿½ï¿½o '
where Rdb$Relation_Name='EQMOVPROD' and Rdb$Field_Name='SEQENTOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da movimentaï¿½ï¿½o da sï¿½rie do produto.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODMOVSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de sï¿½rie.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='NUMSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do inventï¿½rio.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODINVPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da movimentaï¿½ï¿½o.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='DTMOVSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade movimentada:
1 - Entrada em estoque;
0 - Sem movimentaï¿½ï¿½o de estoque;
-1 - Saï¿½da de estoque;'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='TIPOMOVSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lote do produto.
'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do lote.
'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODEMPLE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do almoxarifado.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do almoxarifado.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do recebimento de mercadoria.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODEMPRC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do recebimento de mercadoria.
'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODFILIALRC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do recebimento de mercadoria.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='TICKET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de recebimento de mercadoria.'
where Rdb$Relation_Name='EQMOVSERIE' and Rdb$Field_Name='CODITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o numero de amostras necessï¿½rias para finalizar o processo.'
where Rdb$Relation_Name='EQPROCRECMERC' and Rdb$Field_Name='NROAMOSTPROCRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do acesso.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODPA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de centro de custos.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de centro de custos.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custo.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de caixa ECF.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODEMPCX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de caixa ECF.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODFILIALCX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do caixa ECF.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='CODCAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Horï¿½rio de inserï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID. do usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID. do usuï¿½rio que fez a Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODACESSO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela EQPRODUTO'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela EQPRODUTO'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='SEQPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela EQPLANEJAMENTO.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODEMPPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela EQPLANEJAMENTO.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODFILIALPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCC.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCC.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custo.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODPLAN' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de produto (campo "fluxo" do cadastro de produtos)
P  - Mercadoria p/revenda
S  - Serviï¿½o
E  - Equipamento
F  - Produto acabado
M  - Matï¿½ria prima
O  - Ativo imobilizado
C  - Material de Consumo
02 - Embalagem
03 - Em processo
05 - SubProduto
06 - Produto Intermediï¿½rio
10 - Outros insumos
99 - Outros'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='TIPOPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o produto ï¿½ controlado em lotes.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CLOTEPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se serï¿½ exigido o nÃºmero de sï¿½rie para o produto.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='SERIEPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Custo informado do produto. Pode incluir os custos de aquisiï¿½ï¿½o, tributos, e rateio de custos fixo.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CUSTOINFOPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o base do produto (primeiro preï¿½o de venda) base para geraï¿½ï¿½o de outras tabelas de preï¿½o.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='PRECOBASEPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o base do produto para cï¿½lculo de comissï¿½es especiais.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='PRECOCOMISPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do prazo de entrega padrï¿½o.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODEMPPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do prazo de entrega padrï¿½o.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODFILIALPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do prazo de entrega.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag indicando se o produto abre uma tela para informaï¿½ï¿½es adicionais na venda do PDV'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='USATELAADICPDV';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da concentraï¿½ï¿½o.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='VLRCONCENT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da seï¿½ï¿½o de produï¿½ï¿½o/estoque.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODEMPSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da seï¿½ï¿½o de produï¿½ï¿½o/estoque.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODFILIALSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da secï¿½o de produï¿½ï¿½o/estoque.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODSECAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de chamado vinculado ao serviï¿½o (Integraï¿½ï¿½o CRM/GMS)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de chamado vinculado ao serviï¿½o (Integraï¿½ï¿½o CRM/GMS)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de chamado vinculado ao serviï¿½o (Integraï¿½ï¿½o CRM/GMS)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CODTPCHAMADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade de horas padrï¿½o para execuï¿½ï¿½o do serviï¿½o (Integraï¿½ao CRM/GMS)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='QTDHORASSERV';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dias para validade do produto.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='NRODIASVALID';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve aplicar o desconto padrï¿½o do cliente.'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='DESCCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica a quantidade de produto acabado por plano da folha (industria grï¿½fica/etiquetas)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='QTDPORPLANO';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o nÃºmero de planos por folha (industria grï¿½fica/etiquetas)'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='NROPLANOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o produto participa da Certificaï¿½ï¿½o FSC.
Forest Stewardship Council (Conselho de Manejo Florestal).
"S" - Sim
"N" - Nï¿½o
'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='CERTFSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Fator para calculo dos relatï¿½rios FSC em folhas.
'
where Rdb$Relation_Name='EQPRODUTO' and Rdb$Field_Name='FATORFSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Novo preï¿½o base.'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='PRECOBASEPRODNOVO';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia do log'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='SEQLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o preï¿½o base jï¿½ foi processado (tabelas de preï¿½o)'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='PRECOPROC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do registro'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='EQPRODUTOLOG' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Peso 2 (Pesagem em balanï¿½a hidrostï¿½tica)'
where Rdb$Relation_Name='EQRECAMOSTRAGEM' and Rdb$Field_Name='PESOAMOST2';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencia para controle das pesagens.'
where Rdb$Relation_Name='EQRECAMOSTRAGEM' and Rdb$Field_Name='SEQAMOSTRAGEM';

Update Rdb$Relation_Fields set Rdb$Description =
'Referï¿½ncia do produto'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='REFPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de recepï¿½ï¿½o de mercadorias.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODEMPTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial to tipo de recepï¿½ï¿½o de mercadorias.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODFILIALTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de recepï¿½ï¿½o de mercadorias.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODTIPORECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Status do recebimento da mercadoria.
PE - Pendente;
AN - Em anï¿½lise (OS)
EA - Em andamento (OS)
PT - Pronto (OS)
EO - Em orï¿½amento (entrada para concerto);
CA - Cancelada;
OA - Orï¿½amento aprovado (entrada para concerto);
E1 - Em andamento finalizada primeira etapa;
E2 - Em andamento finalizada segunda etapa;
FN - Finalizado;
PC - Pedido de compra emitido;
NE - Nota de entrada emitida;'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='STATUS';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do documento (nota de entrada)'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='DOCRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do vendedor (coleta)'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do vendedor (coleta)'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do vendedor (coleta)'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do cliente (coleta)
'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODEMPCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do cliente (coleta)
'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODFILIALCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente (coleta)
'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome do solicitante do serviï¿½o/entrega da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='SOLICITANTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado de descarregamento da mercadoria.'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es.
'
where Rdb$Relation_Name='EQRECMERC' and Rdb$Field_Name='OBSRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Motivo da solicitaï¿½ï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='MOTIVORMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o geral da rma:
PE - Pendente (nï¿½o foi realizado nenhum procedimento de aprovaï¿½ï¿½o, ou expediï¿½ï¿½o)
EA - Em andamento
AF - Aprovaï¿½ï¿½o finalizada
EF - Expediï¿½ï¿½o finalizada
CA - Cancelada (requisiï¿½ï¿½o foi cancelada)
'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='SITRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da requisiï¿½ï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='DTAREQRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da expediï¿½ï¿½o da RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='DTAEXPRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Status da aprovaï¿½ï¿½o da RMA.
"PE" - Aprovaï¿½ï¿½o pendente.
"AP" - Aprovaï¿½ï¿½o parcial.
"AT" - Aprovaï¿½ï¿½o total.
"NA" - Nï¿½o aprovada.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='SITAPROVRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Status da expediï¿½ï¿½o da RMA.
"PE" - Expediï¿½ï¿½o pendente.
"EP" - Expedida parcial.
"ET" - Expedida total.
"NE" - Nï¿½o expedida.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='SITEXPRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que aprovou/aprovarï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='IDUSUAPROV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do usuï¿½rio que aprovou/aprovarï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPUA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do usuï¿½rio que aprovou/aprovarï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALUA';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que expediu/expedirï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='IDUSUEXP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do usuï¿½rio que expediu/expedirï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPUE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do usuï¿½rio que expediu/expedirï¿½ a RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALUE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da aprovaï¿½ï¿½o da RMA.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='DTAAPROVRMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa a OP/Fase'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPOF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da OP X Fase'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALOF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da OP.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial da fase de produï¿½ï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='SEQOF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do contrato/projeto'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da empresa/contrato'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contrato/projeto'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de contrato/projeto'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo da empresa da ordem de serviï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODEMPOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ordem de serviï¿½o.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODFILIALOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do ticket da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='TICKET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de recebimento de mercadoria (Ordem de serviï¿½o)'
where Rdb$Relation_Name='EQRMA' and Rdb$Field_Name='CODITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de fabricaï¿½ï¿½o do produto'
where Rdb$Relation_Name='EQSERIE' and Rdb$Field_Name='DTFABRICSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es
'
where Rdb$Relation_Name='EQSERIE' and Rdb$Field_Name='OBSSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se todos os usuï¿½rios podem utilizar o tipo de movimento.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='TUSUTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se as movimentaï¿½ï¿½es geradas com esse tipo de movimento devem aparecer nos relatï¿½rios de venda.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='SOMAVDTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica alocaï¿½ï¿½o do nÃºmero da nota fiscal.
'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='SEQNFTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o sistema de comissï¿½es ï¿½ com mÃºltiplos comissionados.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='MCOMISTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da transportadora padrï¿½o para o tipo de movimento.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODEMPTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da transportadora padrï¿½o para o tipo de movimento.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODFILIALTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da transportadora padrï¿½o para o tipo de movimento.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a nota serï¿½ digitada ou emitida na entrada.'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='EMITNFCPMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o plano de pagamento preferencial
'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODEMPPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para o plano de pagamento preferencial
'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODFILIALPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do plano de pagamento preferencial
'
where Rdb$Relation_Name='EQTIPOMOV' and Rdb$Field_Name='CODPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de tipos de movimento.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de tipos de movimento.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='CODTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de empresa na tabela de usuï¿½rios.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='CODEMPUS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de usuï¿½rios.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='CODFILIALUS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio.'
where Rdb$Relation_Name='EQTIPOMOVUSU' and Rdb$Field_Name='IDUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de recepï¿½ï¿½o de mercadoria:
"RP" - Recebimento com pesagem;
"CM" - Coleta de materiais;
'
where Rdb$Relation_Name='EQTIPORECMERC' and Rdb$Field_Name='TIPORECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto padrï¿½o para recebimento de mercadorias.'
where Rdb$Relation_Name='EQTIPORECMERC' and Rdb$Field_Name='CODEMPPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do produto padrï¿½o para recebimeno de mercadorias.'
where Rdb$Relation_Name='EQTIPORECMERC' and Rdb$Field_Name='CODFILIALPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto padrï¿½o para recebimento de mercadorias.'
where Rdb$Relation_Name='EQTIPORECMERC' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de casas decimais utilizadas para a unidade.'
where Rdb$Relation_Name='EQUNIDADE' and Rdb$Field_Name='CASASDEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Dï¿½gito verificador do banco.'
where Rdb$Relation_Name='FNBANCO' and Rdb$Field_Name='DVBANCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Layout de impressï¿½o de cheques.'
where Rdb$Relation_Name='FNBANCO' and Rdb$Field_Name='LAYOUTCHEQBANCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNBANCO.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODEMPBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNBANCO.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODFILIALBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do banco.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODBANCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Variaï¿½ï¿½o da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='VARIACAOCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='DESCCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Carteira de cobranï¿½a para CNAB.'
where Rdb$Relation_Name='FNCARTCOB' and Rdb$Field_Name='CARTCOBCNAB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo empresa na tabela banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODEMPBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODFILIALBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do banco.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CODBANC';

Update Rdb$Relation_Fields set Rdb$Description =
'Agï¿½ncia.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='AGENCIACHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='CONTACHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do cheque.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='NUMCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Emissï¿½'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTEMITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de compensaï¿½ï¿½'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTCOMPCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Cheque prï¿½-datado (S/N)
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='PREDATCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do cheque.
CA - Cadastrado;
ED - Emitido;
CD - Compensado;
DV - Devolvido.
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='SITCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Histï¿½rico.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HISTCHEQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Ident. do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o.
'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Ident. usuï¿½rio que alterou.'
where Rdb$Relation_Name='FNCHEQUE' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de histï¿½rico padrï¿½o.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CODEMPHP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de histï¿½rico padrï¿½o.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CODFILIALHP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do histï¿½rico padrï¿½o.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CODHIST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta dï¿½bito contï¿½bil.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CODCONTDEB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta crï¿½dito contï¿½bil.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CODCONTCRED';

Update Rdb$Relation_Fields set Rdb$Description =
'Convï¿½nio de cobranï¿½'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='CONVCOBCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a conta estï¿½ ativa.'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='ATIVACONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o tipo de caixa
"F" - Fï¿½sico
"P" - Previsionamentos'
where Rdb$Relation_Name='FNCONTA' and Rdb$Field_Name='TIPOCAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta vinculada'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CODEMPCV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta vinculada.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CODFILIALCV';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta vinculada.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='NUMCONTACV';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a conta ï¿½ para controle de compensaï¿½ï¿½o de cheques.'
where Rdb$Relation_Name='FNCONTAVINCULADA' and Rdb$Field_Name='CONTACHEQUE';

Update Rdb$Relation_Fields set Rdb$Description =
'Sub-Tipo Febraban 01=Dï¿½bito em folha / 02=Dï¿½bito em conta corrente.'
where Rdb$Relation_Name='FNFBNCLI' and Rdb$Field_Name='STIPOFEBRABAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o tipo de ocorrï¿½ncia:
RE - Rejeiï¿½ï¿½o de entrada;
CE - Confirmaï¿½ï¿½o de entrada;
AD - Advertï¿½ncia;
CB - Confirmaï¿½ï¿½o de baixa;
RB - Rejeiï¿½ï¿½o de baixa;
IN - Indefinido;
'
where Rdb$Relation_Name='FNFBNCODRET' and Rdb$Field_Name='TIPORET';

Update Rdb$Relation_Fields set Rdb$Description =
'Sub-Tipo Febraban 01=Dï¿½bito em folha / 02=Dï¿½bito em conta corrente.'
where Rdb$Relation_Name='FNFBNREC' and Rdb$Field_Name='STIPOFEBRABAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da remessa 00=Selecionado / 01=Exportada'
where Rdb$Relation_Name='FNFBNREC' and Rdb$Field_Name='SITREMESSA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do retorno. 00=Sem retorno.'
where Rdb$Relation_Name='FNFBNREC' and Rdb$Field_Name='SITRETORNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da finalidade.'
where Rdb$Relation_Name='FNFINALIDADE' and Rdb$Field_Name='CODFIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da finalidade.'
where Rdb$Relation_Name='FNFINALIDADE' and Rdb$Field_Name='DESCFIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se ï¿½ entrada ou saï¿½da (E/S).'
where Rdb$Relation_Name='FNFINALIDADE' and Rdb$Field_Name='ESFIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Classificaï¿½ï¿½o operacional ou nï¿½o operacional (O/N).'
where Rdb$Relation_Name='FNFINALIDADE' and Rdb$Field_Name='CLASFIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para criaï¿½ï¿½o de modelo dinï¿½mico de histï¿½rico padrï¿½o.'
where Rdb$Relation_Name='FNHISTPAD' and Rdb$Field_Name='TXAHISTPAD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo filial.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do modelo de boleto.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODMODBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNBANCO.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODEMPBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNBANCO.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODFILIALBO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do banco.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODBANCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODEMPCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODFILIALCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Convï¿½nio de cobranï¿½a.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='CONVCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Dï¿½gito do codigo da empresa.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='DVCONVCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do nosso numero, para geraï¿½ï¿½o de nosso numero no padrï¿½o "S" SGPREFER1.TPNOSSONUMERO.'
where Rdb$Relation_Name='FNITMODBOLETO' and Rdb$Field_Name='SEQNOSSONUMERO';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor das devoluï¿½ï¿½es de compra da parcela.'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='VLRDEVITPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='DTCOMPITPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do sinalizador.
'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='CODEMPSN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do sinalizador.
'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='CODFILIALSN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do sinalizador.
'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='CODSINAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o tï¿½tulo serï¿½ baixado atravï¿½s de baixa multipla.
S - Sim;
N - Nï¿½'
where Rdb$Relation_Name='FNITPAGAR' and Rdb$Field_Name='MULTIBAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de devoluï¿½ï¿½o da parcela.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='VLRDEVITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de comissï¿½o por parcela.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='VLRCOMIITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.
'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='DTCOMPITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de pagamento ou data de compensaï¿½ï¿½o.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='DTPAGOITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de liquidaï¿½ï¿½o do tï¿½tulo.
'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='DTLIQITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial em FNTIPOCOB.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo filial em FNTIPOCOB.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de cobranï¿½a.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODTIPOCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODEMPCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo filial na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODFILIALCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se serï¿½ impresso recibo.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='IMPRECIBOITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se estï¿½ sendo feita alteraï¿½ï¿½o de item de contas a receber pelo usuï¿½rio.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='ALTUSUITREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Definie se recebimento estï¿½ em processo de cobranï¿½'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='RECEMCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inicio de cobranï¿½'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='DTINIEMCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Fim do processo de cobranï¿½'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='DTFIMEMCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Nosso nÃºmero (utilizado para armazenar o nosso nÃºmero quando gerado pelo banco e retornado atravï¿½s do CNAB)
'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='NOSSONUMERO';

Update Rdb$Relation_Fields set Rdb$Description =
'Coloca registro em estado de manutenï¿½ï¿½o.
'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='DTCOMPLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Histï¿½rico bancï¿½rio.'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='HISTBLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente, para vï¿½nculo com contas a receber.'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do cliente'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='CODEMPCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do cliente.'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='CODFILIALCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de manutenï¿½ï¿½o (S/N).
'
where Rdb$Relation_Name='FNLANCA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da venda no momento da liberaï¿½ï¿½o de crï¿½dito.'
where Rdb$Relation_Name='FNLIBCRED' and Rdb$Field_Name='VLRVENDACRED';

Update Rdb$Relation_Fields set Rdb$Description =
'Carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='CARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Modalidade de cobranï¿½a.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='MDECOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag indicador para aceitaï¿½ï¿½o do boleto pelo cliente.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='ACEITEMODBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do local de pagamento do boleto.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='DESCLPMODBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Instruï¿½ï¿½es para o pagamento do boleto.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='INSTPAGMODBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve imprimir informaï¿½ï¿½es da parcela nas intruï¿½ï¿½es de cobranï¿½a para boletos grï¿½ficos.'
where Rdb$Relation_Name='FNMODBOLETO' and Rdb$Field_Name='IMPINFOPARC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da moeda padrï¿½o Febraban.'
where Rdb$Relation_Name='FNMOEDA' and Rdb$Field_Name='CODFBNMOEDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor dos descontos. (Deve ser incluidos as retenï¿½ï¿½es se houverem)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRDESCPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor das devoluï¿½ï¿½es de compra.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRDEVPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='DTCOMPPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de desconto/retenï¿½ï¿½o do IRRF (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRRETIRRF';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do desconto/retenï¿½ï¿½o do INSS (Pagamento de autonomos)'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='VLRRETINSS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODEMPPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODFILIALPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento financeiro.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custo.'
where Rdb$Relation_Name='FNPAGAR' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNPAGAR.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNPAGAR.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do pagamento.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='NPARCPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCHEQUE.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODEMPCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo filial na tabela FNCHEQUE.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='CODFILIALCH';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a baixa do titulo vinculado (gatilho cheque emitido)
S - Sim;
N - Nï¿½o;
'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='BAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve transferir o titulo vinculado da conta de cheques para conta real (gatilho cheque compensado)
S - Sim;
N - Nï¿½o;'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='TRANSFERE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Id. do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Id. do usuï¿½rio que alterou.'
where Rdb$Relation_Name='FNPAGCHEQ' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNPAGTOCOMI' and Rdb$Field_Name='DTCOMPPCOMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='FNPAGTOCOMI' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para marcar baixa automï¿½tica da parcela.'
where Rdb$Relation_Name='FNPARCPAG' and Rdb$Field_Name='AUTOBAIXAPARC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo reduzido'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='CODREDPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag indicando se compï¿½e saldo de caixa.'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='COMPSLDCXPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta crï¿½dito para contabilidade.'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='CODCONTCRED';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta dï¿½bido para contabilidade.'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='CODCONTDEB';

Update Rdb$Relation_Fields set Rdb$Description =
'Natureza da categoria E-Entrada / S-Saï¿½da'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='ESFINPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Classificaï¿½ï¿½o da categoria O-Operacional / N-Nï¿½o Operacional.'
where Rdb$Relation_Name='FNPLANEJAMENTO' and Rdb$Field_Name='CLASFINPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se orï¿½amento serï¿½ aprovado por padrï¿½o na tela de fechamento de orï¿½amento.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='APORCPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se o plano de pagamento estï¿½ ativo "S" ou inativo "N".'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='ATIVOPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o plano de pagamento ï¿½ para compra "C", venda "V" ou ambos "A", deve ser usado como filtro nas telas correspondentes.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='CVPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da tabela de juros.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='CODEMPTBJ';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da tabela de juros.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='CODFILIALTBJ';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da tabela de juros.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='CODTBJ';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se a regra de vencimento vale para os sï¿½bados.'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='RVSABPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Regra do dia de vencimento
A - Automï¿½tico
F - Dia fixo
U - Dia util
'
where Rdb$Relation_Name='FNPLANOPAG' and Rdb$Field_Name='RVDIAPLANOPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa em FNTIPOCOB.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial em FNTIPOCOB.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de cobranï¿½a.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODTIPOCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCARTCOB.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor total das devoluï¿½ï¿½es.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='VLRDEVREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='DTCOMPREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALPN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento financeiro.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODPLAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do centro de custo.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODCC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da renegociaï¿½ï¿½o.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODEMPRR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da renegociaï¿½ï¿½o.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODFILIALRR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da renegociaï¿½ï¿½o.'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='CODRENEGREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='FNRECEBER' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ao da restriï¿½ï¿½o 
I - Inclusï¿½o
C - Cancelada'
where Rdb$Relation_Name='FNRESTRICAO' and Rdb$Field_Name='SITRESTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o caixa foi fechado para este dia, se "S" nï¿½o deverï¿½ permitir lanï¿½amentos para esta data.'
where Rdb$Relation_Name='FNSALDOLANCA' and Rdb$Field_Name='FECHADO';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o registro estï¿½ em processo de manutenï¿½ï¿½o.'
where Rdb$Relation_Name='FNSALDOLANCA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de sinalizaï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='CODSINAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da sinalizaï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='DESCSINAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cor da sinalizaï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='CORSINAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da inserï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºlt. alteraï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºlt. alteraï¿½ï¿½'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que alterou'
where Rdb$Relation_Name='FNSINAL' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='DTCOMPSUBLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do contrato/projeto'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='CODEMPCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da empresa/contrato'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='CODFILIALCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contrato/projeto'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='CODCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de contrato/projeto'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='CODITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='FNSUBLANCA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='NUMCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do talï¿½o.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='SEQTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data do talï¿½o.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='DTTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do cheque inicial.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CHEQINITALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero final do cheque.'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='CHEQFIMTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Talonï¿½rio ativo (S/N).'
where Rdb$Relation_Name='FNTALAOCHEQ' and Rdb$Field_Name='ATIVOTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo da tabela de juros: 
"D" = Diï¿½rio
"M" = Mensal
"B" = Bimestral
"T" = Trimestral
"S" = Semestral
"A" = Anual
"F" = Fixo'
where Rdb$Relation_Name='FNTBJUROS' and Rdb$Field_Name='TIPOTBJ';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se ï¿½ febraban. 00=nï¿½o / 01-SIACC / 02-CNAB'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='TIPOFEBRABAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Indicador do tipo de tï¿½tulo de crï¿½dito para o SPED:
00 - Duplicata
01 - Cheque
02 - Promissï¿½ria
03 - recibo
'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='TIPOSPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se ï¿½ obrigatï¿½rio carteira de cobranï¿½a.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='OBRIGCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de contas.
'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='CODEMPCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de contas.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='CODFILIALCT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta para impressï¿½o de cheques.
'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='NUMCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia do talonï¿½rio.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='SEQTALAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Numero de dias para compensaï¿½ï¿½o do valor.'
where Rdb$Relation_Name='FNTIPOCOB' and Rdb$Field_Name='NRODIASCOMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se a restriï¿½ï¿½o bloqueia os lanï¿½amentos.'
where Rdb$Relation_Name='FNTIPORESTR' and Rdb$Field_Name='BLOQTPRESTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do serviï¿½o (tabela de serviï¿½os)'
where Rdb$Relation_Name='LFCLFISCAL' and Rdb$Field_Name='CODSERV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de situaï¿½ï¿½o da operaï¿½ï¿½o no simples nacional.'
where Rdb$Relation_Name='LFCSOSN' and Rdb$Field_Name='CSOSN';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da situaï¿½ï¿½o da operaï¿½ï¿½o no simples nacional.'
where Rdb$Relation_Name='LFCSOSN' and Rdb$Field_Name='DESCCSOSN';

Update Rdb$Relation_Fields set Rdb$Description =
'Natureza da operaï¿½ï¿½o.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODNAT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do remetente. Relacionada Ã  tabela de cï¿½digos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODREMET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do destinatario. Relacionada Ã  tabela de cï¿½digos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODDESTINAT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de emissï¿½o do conhecimento de frete.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='DTEMITFRETE';

Update Rdb$Relation_Fields set Rdb$Description =
'Sï¿½rie do documento de conhecimento de frete.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='SERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do recebimento de mercadorias.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODEMPRM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do recebimento de mercadorias.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODFILIALRM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tratamento tributario do icms.'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODEMPTT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tratamento tributï¿½rio do ICMS'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODFILIALTT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tratamento tributï¿½rio do ICMS'
where Rdb$Relation_Name='LFFRETE' and Rdb$Field_Name='CODTRATTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importaï¿½ï¿½o direta
2 - Estrangeira - Adquirida no mercado interno'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ORIGFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de reduï¿½ï¿½o de ICMS
B - BASE DE CÃLCULO
V - VALOR DO ICMS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='TPREDICMSFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota interestadual do produto.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota intraestadual de ICMS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQFISCINTRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota de IPI'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQIPIFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota diferenciada do PIS para a classificaï¿½ï¿½o fiscal (sobrepï¿½e aliquota da filial)'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQPISFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota diferenciada do Cofins para a classificaï¿½ï¿½o fiscal (sobrepï¿½e alï¿½quota da filial)'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQCOFINSFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota da contribuiï¿½ï¿½o social.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQCSOCIALFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do imposto de importaï¿½ï¿½o para a classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQIIFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo da Substituiï¿½ï¿½o tributï¿½ria.
SU - Substituto (Responsï¿½vel pela retenï¿½ï¿½o e recolhimento de todo o imposto (subsequente tambï¿½m);
SI - Substituï¿½do (Imposto pago pelo contribuite substituto);'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='TIPOST';

Update Rdb$Relation_Fields set Rdb$Description =
'Margem de valor agregado para calculo da base de calculo do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='MARGEMVLAGR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se ï¿½ a regra geral da classificaï¿½ï¿½o fiscal.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='GERALFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da situaï¿½ï¿½o tributï¿½ria do PIS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPSP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da situaï¿½ï¿½o tributï¿½ria do PIS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALSP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da situaï¿½ï¿½o tributï¿½rio do PIS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODSITTRIBPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do imposto da situaï¿½ï¿½o tributï¿½ria do PIS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='IMPSITTRIBPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da situaï¿½ï¿½o tributï¿½ria do COFINS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da situaï¿½ï¿½o tributï¿½ria do COFINS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da situaï¿½ï¿½o tributï¿½ria do COFINS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODSITTRIBCOF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do imposto da situaï¿½ï¿½o tributï¿½ria do COFINS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='IMPSITTRIBCOF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da situaï¿½ï¿½o tributï¿½ria do IPI'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPSI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da situaï¿½ï¿½o tributï¿½ria do IPI'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALSI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da situaï¿½ï¿½o tributï¿½ria do IPI'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODSITTRIBIPI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do imposto da situaï¿½ï¿½o tributï¿½ria do IPI'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='IMPSITTRIBIPI';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de cï¿½lculo do IPI 
P - Percentual
V - Valor'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='TPCALCIPI';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do IPI por unidade tributï¿½vel.
Utilizado caso o TPCALCIPI = V'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='VLRIPIUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Modalidade de determinaï¿½ï¿½o da base de cï¿½lculo do ICMS.
0 - Margem Valor Agregado (%)
1 - Pauta (Valor)
2 - Preï¿½o Tabelado Max. (valor)
3 - Valor da operaï¿½ï¿½'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='MODBCICMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Modalidade de determinaï¿½ï¿½o da base de cï¿½lculo do ICMS substituiï¿½ï¿½o tributï¿½ria.
0 - Preï¿½o tabelado ou mï¿½ximo sugerido;
1 - Lista Negativa (valor)
2 - Lista Positiva (valor)
3 - Lista Neutra (valor)
4 - Margem Valor Agregado (%);
5 - Pauta (valor)'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='MODBCICMSST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s para amarraï¿½ï¿½o com estado.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de tributaï¿½ï¿½o do PIS por unidade vendida.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='VLRPISUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor de tributaï¿½ï¿½o do COFINS por unidade vendida.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='VLRCOFUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de uso para a regra de classificaï¿½ï¿½o fiscal:
VD - Venda
CP - Compra'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='TIPOUSOITFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se hï¿½ reduï¿½ï¿½o na base do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='REDBASEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da situaï¿½ï¿½o tributï¿½rio para o ISS.'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para a situaï¿½ï¿½o tributï¿½ria do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da situaï¿½ï¿½o tributï¿½ria para o ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODSITTRIBISS';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla do imposto definido para situaï¿½ï¿½o tributï¿½ria do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='IMPSITTRIBISS';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota do ISS'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQISSFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indicativo de apuraï¿½ï¿½o do IPI
0 - Mensal
1 - Decendial'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='INDAPURIPI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do CSOSN
'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODEMPCN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do CSOSN
'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CODFILIALCN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de situaï¿½ï¿½o da operaï¿½ï¿½o no simples nacional.
'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='CSOSN';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do ICMS de importaï¿½ï¿½o.
'
where Rdb$Relation_Name='LFITCLFISCAL' and Rdb$Field_Name='ALIQICMSIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota da reduï¿½ï¿½o da base de calculo do ICMS.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ALIQREDBCICMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota da reduï¿½ï¿½o da base de calculo do ICMS Substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ALIQREDBCICMSST';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ALIQICMSST';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota do PIS'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ALIQPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor tributï¿½vel para o PIS'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRPISUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de cï¿½lculo do PIS'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRBASEPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de cï¿½lculo do COFINS'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRBASECOFINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor tributï¿½vel do pis por unidade comprada.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRCOFUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do IR sobre o ï¿½tem de compra.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRIR';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do PIS sobre o ï¿½tem de compra.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da Contribuiï¿½ï¿½o Social sobre o item de venda.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRCSOCIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importaï¿½ï¿½o direta
2 - Estrangeira - Adquirida no mercado interno'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ORIGFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tratamento tributï¿½rio.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='CODTRATTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de crï¿½dito de icms (simples)
'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='PERCICMSSIMPLES';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a tabela estï¿½ em manutenï¿½ï¿½o.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Base de calculo do icms st retido na operaï¿½ï¿½o anterior.'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRBASEICMSSTITRETCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do ICMS ST retido na operaï¿½ï¿½o anterior.
'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRICMSSTRETITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do imposto de importaï¿½ï¿½o do item.
'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='ALIQII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do imposto de importaï¿½ï¿½o do item.
'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRBASEII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do imposto de importaï¿½ï¿½o do item.
'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRII';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do crï¿½dito presumido do ICMS'
where Rdb$Relation_Name='LFITCOMPRA' and Rdb$Field_Name='VLRICMSCREDPRESUM';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota da reduï¿½ï¿½o da base de calculo do ICMS.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQREDBCICMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota da reduï¿½ï¿½o da base de calculo do ICMS Substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQREDBCICMSST';

Update Rdb$Relation_Fields set Rdb$Description =
'Aliquota do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQICMSST';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota do PIS'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='ALIQPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor tributï¿½vel para o PIS'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRPISUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de cï¿½lculo do PIS'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRBASEPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de cï¿½lculo do COFINS'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRBASECOFINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor tributï¿½vel do pis por unidade vendida'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRCOFUNIDTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do IR sobre o ï¿½tem de venda.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRIR';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do PIS sobre o ï¿½tem de venda.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRPIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da Contribuiï¿½ï¿½o Social sobre o item de venda.'
where Rdb$Relation_Name='LFITVENDA' and Rdb$Field_Name='VLRCSOCIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do registro:
"N" - Documento fiscal normal;
"S" - Documento fiscal cancelado;
"E" - Documento fiscal extemporï¿½neo;
"X" - Documento fiscal extemporï¿½neo cancelado;'
where Rdb$Relation_Name='LFLIVROFISCAL' and Rdb$Field_Name='SITUACAOLF';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para impressï¿½o de data de saida na nota fiscal.
'
where Rdb$Relation_Name='LFNATOPER' and Rdb$Field_Name='IMPDTSAIDANAT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa conforme a tabela LFSERIE.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial conforme a tabela LFSERIE.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da sï¿½rie.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='SERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='CODEMPSS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='CODFILIALSS';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia da sï¿½rie.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='SEQSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencia do documento.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='DOCSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se a sequencia estï¿½ ativa (S/N).'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='ATIVSERIE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que alterou.'
where Rdb$Relation_Name='LFSEQSERIE' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Imposto, pode ser: CO (Cofins),PI (PIS),IC (ICMS),IR (IMPOSTO DE RENDA),CS (Contribuiï¿½ï¿½o Social), IP (IPI), IS (ISS)'
where Rdb$Relation_Name='LFSITTRIB' and Rdb$Field_Name='IMPSITTRIB';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se calcula a contribuiï¿½ï¿½o social.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CALCCSOCIALTF';

Update Rdb$Relation_Fields set Rdb$Description =
'indica se imprime a contribuiï¿½ï¿½o social.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='IMPCSOCIALTF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o tipo de movimento de orï¿½amento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODEMPOC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para o tipo de movimento de orï¿½amento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODFILIALOC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento de orï¿½amento para o tipo fiscal de cliente.'
where Rdb$Relation_Name='LFTIPOFISCCLI' and Rdb$Field_Name='CODTIPOMOVOC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto referente a estrutura.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da estrutura.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='SEQEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela PPFASE.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODEMPFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela PPFASE.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODFILIALFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da fase.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODFASE';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia da distribuiï¿½ï¿½o da estrutura.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='SEQDE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para vï¿½nculo com estrutura usada para distribuiï¿½ï¿½o.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODEMPDE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para estrutura de distribuiï¿½ï¿½o.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODFILIALDE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto para estrutura de distribuiï¿½ï¿½o.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='CODPRODDE';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da estrutura de distribuiï¿½ï¿½o.'
where Rdb$Relation_Name='PPDISTRIB' and Rdb$Field_Name='SEQESTDE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da estrutra x analise.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODESTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto vinculado a estrutura.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia da estrutura.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='SEQEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia da estrutura x fase.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='SEQEF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da emprea da estrutura x fase.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODEMPFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da estrutura x fase.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODFILIALFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da fase.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODFASE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de analise.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODEMPTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de analise.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODFILIALTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de anï¿½lise.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='CODTPANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor mï¿½nimo de tolerï¿½ncia.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='VLRMIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor mï¿½ximo de tolerï¿½ncia.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='VLRMAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Espessificaï¿½ï¿½o padrï¿½o da anï¿½lise, utilizada quando a anï¿½lise ï¿½ descritiva.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='ESPECIFICACAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da ultima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='PPESTRUANALISE' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da estrutura.'
where Rdb$Relation_Name='PPESTRUFASE' and Rdb$Field_Name='SEQEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a fase finaliza o processo de produï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='PPESTRUFASE' and Rdb$Field_Name='FINALIZAOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Instruï¿½ï¿½es da fase de produï¿½ï¿½o para a estrutura.'
where Rdb$Relation_Name='PPESTRUFASE' and Rdb$Field_Name='INSTRUCOES';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da estrutura.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='SEQEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a estrutura estï¿½ ativa.(S/N)'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='ATIVOEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o modelo de lote.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='CODEMPML';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do modelo de lote.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='CODFILIALML';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do modelo de lote.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='CODMODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dias de validade do produto fabricado.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='NRODIASVALID';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a estrutura ï¿½ dinï¿½mica.'
where Rdb$Relation_Name='PPESTRUTURA' and Rdb$Field_Name='ESTDINAMICA';

Update Rdb$Relation_Fields set Rdb$Description =
'"EX" = Execuï¿½ï¿½o;
"CQ" = Controle de qualidade;
"EB" = Embalagem.
'
where Rdb$Relation_Name='PPFASE' and Rdb$Field_Name='TIPOFASE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a fase ï¿½ realizada externamente (terceirizaï¿½ï¿½o)
S - Sim
N - Nao
'
where Rdb$Relation_Name='PPFASE' and Rdb$Field_Name='EXTERNAFASE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPFOTOMTAN' and Rdb$Field_Name='CODMTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da foto.'
where Rdb$Relation_Name='PPFOTOMTAN' and Rdb$Field_Name='CODFOTOMTAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da foto.'
where Rdb$Relation_Name='PPFOTOMTAN' and Rdb$Field_Name='DESCFOTOMTAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da estrutura.'
where Rdb$Relation_Name='PPITESTRUTURA' and Rdb$Field_Name='SEQEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para geraï¿½ï¿½o de RMA automï¿½tica (S/N)'
where Rdb$Relation_Name='PPITESTRUTURA' and Rdb$Field_Name='RMAAUTOITEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o ï¿½tem serï¿½ utilizado para retenï¿½ï¿½o de contra-prova (controle de qualidade).'
where Rdb$Relation_Name='PPITESTRUTURA' and Rdb$Field_Name='CPROVA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a quantidade nï¿½o deve ser multiplicada pela quantidade de itens de estrutura produzida.'
where Rdb$Relation_Name='PPITESTRUTURA' and Rdb$Field_Name='QTDFIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve permitir o ajuste da quantidade a ser utilizad na produï¿½ï¿½o no momento da OP.
'
where Rdb$Relation_Name='PPITESTRUTURA' and Rdb$Field_Name='PERMITEAJUSTEITEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da OP.'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa - lote
'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODEMPLE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial - Lote
'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODFILIALLE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lote
'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia do ï¿½tem copiado (utilizado no rateamento automï¿½tico de lotes)'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='SEQITOPCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lote rateado'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODLOTERAT';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade da inserida na cï¿½pia do item. Este valor dispara o trigger para criaï¿½ï¿½o da cï¿½pia.'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='QTDCOPIAITOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar rma para o item. pode ser que nï¿½o deva ser gerado por padrï¿½o, ou por jï¿½ ter sido gerado.'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='GERARMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial da aï¿½ï¿½o corretiva.'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='SEQAC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da venda (remessa ou retorno industrializaï¿½ï¿½o);
'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da venda (remessa ou retorno industrializaï¿½ï¿½o);'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo da venda (remessa ou retorno industrializaï¿½ï¿½o);'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='TIPOVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda (remessa ou retorno industrializaï¿½ï¿½o);'
where Rdb$Relation_Name='PPITOP' and Rdb$Field_Name='CODVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do tipo de anï¿½lise.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='DESCMTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Tï¿½tulo do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='TITULOANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Fonte do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='FONTEMTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Material utilizado para a anï¿½lise.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='MATANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Reagente da anï¿½lise.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='REAGANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da ultima alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da ultima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que realizou a ultima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPMETODOANALISE' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da OP.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de emissï¿½o
'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='DTEMITOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de fabricaï¿½ï¿½o do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='DTFABROP';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade final sugerida de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='QTDSUGPRODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade distribuï¿½da referente a OP principal.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='QTDDISTPOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade distribuï¿½da referente a OP de distribuiï¿½ï¿½o.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='QTDDISTIOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do lote do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPLE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do lote do produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALLE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do Lote para o produto acabado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODLOTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do almoxarifado.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da ordem de produï¿½ï¿½o mestre (principal).'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPOPM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ordem de produï¿½ï¿½o mestre (principal).'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALOPM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½o mestre (principal).'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODOPM';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial da ordem de produï¿½ï¿½o mestre (principal).'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='SEQOPM';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da OP.
PE - Pendente;
CA - Cancelada;
FN - Finalizada;
BL - Bloqueada;'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='SITOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para observaï¿½ï¿½es a cerca da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='OBSOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que cancelou a O.P.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='IDUSUCANC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do item de compra vinculado Ã  OP (conversï¿½o de produtos)'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do item de compra vinculado Ã  OP (conversï¿½o de produtos)'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra do item vinculado Ã  OP (conversï¿½o de produtos)'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de compra vinculado Ã  OP (conversï¿½o de produtos)'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a ordem de produï¿½ï¿½o deve utilizar o mecanismo de estruturas dinï¿½micas.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='ESTDINAMICA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a ordem de produï¿½ï¿½o e proveniente de garantia.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='GARANTIA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da Ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODEMPOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODFILIALOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Ticket da ordem de serviï¿½o vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='TICKET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de recebimento da OS vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODITRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item da OS vinculada.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='CODITOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Id do usuï¿½rio que realizou a ultima alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPOP' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de causas fundamentais:
"1M" - Material;
"2M" - Mï¿½quina;
"3M" - Mï¿½todo;
"4M" - Meio ambiente;
"5M" - Mï¿½o-de-obra;
"6M" - Medida;


'
where Rdb$Relation_Name='PPOPACAOCORRET' and Rdb$Field_Name='TPCAUSA';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de aï¿½ï¿½o corretiva prï¿½-definida:
"II" - Inclusï¿½o de insumos;
"DP" - Descarte da produï¿½ï¿½o;'
where Rdb$Relation_Name='PPOPACAOCORRET' and Rdb$Field_Name='TPACAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia da ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequï¿½ncia do controle de qualidade.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='SEQOPCQ';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencial da aï¿½ï¿½o corretiva para o problema detectado.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='SEQAC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da estrutura x analise.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODEMPEA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da estrutura x analise.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODFILIALEA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da estrutura x analise.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='CODESTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da aferiï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='VLRAFER';

Update Rdb$Relation_Fields set Rdb$Description =
'Descritivo da aferiï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='DESCAFER';

Update Rdb$Relation_Fields set Rdb$Description =
'Status da anï¿½lise de controle de qualidade.
"PE" - Pendente;
"AP" - Aprovado;
"RC" - Recusado;
"CO" - Corrigido.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='STATUS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='PPOPCQ' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero sequencial da OP.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para tabela de tipos de recurso.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='CODEMPTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para tabela de tipos de recurso.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='CODFILIALTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de recurso.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='CODTPREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inicio da produï¿½ï¿½o da fase.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='DATAINIPRODFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inicio da produï¿½ï¿½o da fase.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='HINIPRODFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de termino da produï¿½ï¿½o da fase.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='DATAFIMPRODFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora final da produï¿½ï¿½o da fase.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='HFIMPRODFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es referentes a fase de produï¿½ï¿½o.'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='OBSFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se da fase estï¿½ pendente ou finalizada.
"PE" - Pendente
"FN" - Finalizada'
where Rdb$Relation_Name='PPOPFASE' and Rdb$Field_Name='SITFS';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade do item de orï¿½amento que deverï¿½ ser produzida nessa OP. (previsï¿½o)'
where Rdb$Relation_Name='PPOPITORC' and Rdb$Field_Name='QTDPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade final produzida para atendimento a este ï¿½tem de orï¿½amento.
'
where Rdb$Relation_Name='PPOPITORC' and Rdb$Field_Name='QTDFINALPRODITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento.'
where Rdb$Relation_Name='PPOPSUBPROD' and Rdb$Field_Name='CODEMPTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento'
where Rdb$Relation_Name='PPOPSUBPROD' and Rdb$Field_Name='CODFILIALTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento.'
where Rdb$Relation_Name='PPOPSUBPROD' and Rdb$Field_Name='CODTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Data para entrada da subproducao (finalizaï¿½ï¿½o da fase)
'
where Rdb$Relation_Name='PPOPSUBPROD' and Rdb$Field_Name='DTSUBPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='PPPROCESSAOPTMP' and Rdb$Field_Name='CODEMPET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½diga da filial da estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='PPPROCESSAOPTMP' and Rdb$Field_Name='CODFILIALET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='PPPROCESSAOPTMP' and Rdb$Field_Name='CODEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de anï¿½lise.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODTPANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do tipo de anï¿½lise.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='DESCTPANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½o relativa ao tipo de anï¿½lise, pode descrever o mï¿½todo de anï¿½lise, instruï¿½ï¿½es de coleta e/ou aferiï¿½ï¿½o, etc...'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='OBSTPANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de registro de especificaï¿½ï¿½es.
"MM" - Mï¿½nimo e mï¿½ximo;
"DT" - Descritivo.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='TIPOEXPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da unidade.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODEMPUD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da unidade.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODFILIALUD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da unidade de medida da anï¿½lise.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODUNID';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODEMPMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODFILIALMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do mï¿½todo analï¿½tico.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='CODMTANALISE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que inseriu o registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da alteraï¿½ï¿½o no registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que alterou o registro.'
where Rdb$Relation_Name='PPTIPOANALISE' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='PVCAIXA' and Rdb$Field_Name='CODEMPET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de estaï¿½ï¿½es de trabalho.'
where Rdb$Relation_Name='PVCAIXA' and Rdb$Field_Name='CODFILIALET';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='PVCAIXA' and Rdb$Field_Name='CODEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Habilita a venda para pdv somente com orï¿½amento.'
where Rdb$Relation_Name='PVCAIXA' and Rdb$Field_Name='ORCCAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ï¿½rea.'
where Rdb$Relation_Name='RHAREA' and Rdb$Field_Name='CODAREA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do estado civil.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='CODEMPES';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do estado civil.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='CODFILIALES';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do estado civil.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='CODESTCIVIL';

Update Rdb$Relation_Fields set Rdb$Description =
'Pretensï¿½o salarial.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='PRETENSAOSAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Status do candidato:
"IN" - Inativo;
"DI" - Disponï¿½vel;
"EN" - Encaminhado;
"EL" - Eliminado de processo seletivo;
"EF" - Efetivado;
"EM" - Empregado;
"EV" - Entrevistado;
"DL" - Desligado;'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='STCAND';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es do candidato.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='OBSCAND';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de filhos.'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='NROFILHOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se candidato ï¿½ isento de tarifa de transporte - S/N'
where Rdb$Relation_Name='RHCANDIDATO' and Rdb$Field_Name='ISENCTRANSP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do curso.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='DESCCURSO';

Update Rdb$Relation_Fields set Rdb$Description =
'ConteÃºdo programï¿½tico do curso.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CONTPROGCURSO';

Update Rdb$Relation_Fields set Rdb$Description =
'Instituiï¿½ï¿½o de ensino onde foi realizado o curso.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='INSTITUICAOCURSO';

Update Rdb$Relation_Fields set Rdb$Description =
'Duraï¿½ï¿½o do curso (em meses).'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='DURACAOCURSO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da ï¿½rea '
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODAREA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da ï¿½rea.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODEMPAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da ï¿½rea.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODFILIALAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½vel do curso'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODNIVELCURSO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do nï¿½vel do curso.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODEMPNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do nï¿½vel do curso.'
where Rdb$Relation_Name='RHCURSO' and Rdb$Field_Name='CODFILIALNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de admissï¿½o do empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='DTADMISSAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Status do empregado
"AD" - Admitido;
"DE" - Demitido;
"EF" - Em fï¿½rias;
"LM" - Licenï¿½a maternidade;
"AI" - Afastamento INSS;
"AP" - Aposentado;'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='STATUSEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o da Carteira de Trabalho e Previdï¿½ncia Social.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='CTPSEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Sï¿½rie da carteira de trabalho e previdï¿½ncia social.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='SERIECTPSEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de emissï¿½o da carteira de trabalho e previdï¿½ncia social.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='UFCTPSEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Certificado de reservista (Exï¿½rcito)'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='CERTRESERVEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Orgï¿½o de expediï¿½ï¿½o do RG do Empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='ORGEXPRHEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de expediï¿½ï¿½o do RG.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='UFRGEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de expediï¿½ï¿½o do RG.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='DTEXPRGEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Carteira nacional de habilitaï¿½ï¿½o do empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='CNHEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome da mï¿½e do empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='MAEEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Complemento do endereï¿½o do empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='COMPLENDEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado do empregado (Endereï¿½o).'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='UFEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de demissï¿½o do empregado.'
where Rdb$Relation_Name='RHEMPREGADO' and Rdb$Field_Name='DTDEMISSAOEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se empregador estï¿½ (A) ativo ou inativo (I).'
where Rdb$Relation_Name='RHEMPREGADOR' and Rdb$Field_Name='ATIVOEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Inscriï¿½ï¿½o estadual do empregador conveniado.'
where Rdb$Relation_Name='RHEMPREGADOR' and Rdb$Field_Name='INSCEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o do empregador conveniado.'
where Rdb$Relation_Name='RHEMPREGADOR' and Rdb$Field_Name='ENDEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do endereï¿½o do empregador conveniado.'
where Rdb$Relation_Name='RHEMPREGADOR' and Rdb$Field_Name='NUMEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do salï¿½rio mensal.'
where Rdb$Relation_Name='RHEMPREGADOSAL' and Rdb$Field_Name='VALORSAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de vigor do novo salï¿½rio.'
where Rdb$Relation_Name='RHEMPREGADOSAL' and Rdb$Field_Name='DTVIGOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es a respeito do salï¿½rio.'
where Rdb$Relation_Name='RHEMPREGADOSAL' and Rdb$Field_Name='OBSSAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo brasileiro de ocupaï¿½ï¿½es (CBO)'
where Rdb$Relation_Name='RHFUNCAO' and Rdb$Field_Name='CBOFUNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Deduï¿½ï¿½'
where Rdb$Relation_Name='RHTABELAIRRF' and Rdb$Field_Name='DEDUCAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do turno.'
where Rdb$Relation_Name='RHTURNO' and Rdb$Field_Name='DESCTURNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de horas semanais.'
where Rdb$Relation_Name='RHTURNO' and Rdb$Field_Name='NHSTURNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de turno:
N - Normal (Manhï¿½ e tarde)
M - Manhï¿½
T - Tarde
O - Noite
E - Especial
'
where Rdb$Relation_Name='RHTURNO' and Rdb$Field_Name='TIPOTURNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Tolerï¿½ncia na entrada (minutos).'
where Rdb$Relation_Name='RHTURNO' and Rdb$Field_Name='TOLENTRADA';

Update Rdb$Relation_Fields set Rdb$Description =
'Tolerï¿½ncia na saï¿½da (minutos).'
where Rdb$Relation_Name='RHTURNO' and Rdb$Field_Name='TOLSAIDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da vaga.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODVAGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do empregador.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODEMPR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da funï¿½ï¿½o.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODFUNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do turno.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODEMPTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do turno.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODFILIALTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do turno.'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='CODTURNO';

Update Rdb$Relation_Fields set Rdb$Description =
'Status da vaga:
"AB" - Aberta;
"SL" - Em processo de seleï¿½ï¿½o;
"CA" - Cancelada;
"PR" - Preenchida;'
where Rdb$Relation_Name='RHVAGA' and Rdb$Field_Name='STVAGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do candidato na vaga:
"EN" Encaminhado
"EF" Efetivado '
where Rdb$Relation_Name='RHVAGACANDIDATO' and Rdb$Field_Name='STVAGACAND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da vaga.'
where Rdb$Relation_Name='RHVAGASTATUS' and Rdb$Field_Name='CODVAGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Status da vaga:
""AB"" - Aberta;
""SL"" - Em processo de seleï¿½ï¿½o;
""CA"" - Cancelada;
""PR"" - Preenchida;'
where Rdb$Relation_Name='RHVAGASTATUS' and Rdb$Field_Name='STVAGA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do agente.'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODAGE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do agente criador do agendamento'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODAGEEMIT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de agendamento.'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODTIPOAGD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do agendamento vinculado (repetitivos).'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODEMPAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do agendamento vinculado (repetitivos).'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODFILIALAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da tipo do agendamento vinculado (repetitivos).'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='TIPOAGEAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do agente do agendamento vinculado (repetitivos).'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODAGEAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do agendamento vinculado (repetitivos).'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='CODAGDAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se compromisso ocuparï¿½ o dia inteiro.'
where Rdb$Relation_Name='SGAGENDA' and Rdb$Field_Name='DIATODO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa
'
where Rdb$Relation_Name='SGAGENTE' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='SGAGENTE' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo do agente:
SGUSU - Usuï¿½rio
'
where Rdb$Relation_Name='SGAGENTE' and Rdb$Field_Name='TIPOAGE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do agente.'
where Rdb$Relation_Name='SGAGENTE' and Rdb$Field_Name='CODAGE';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o ou nome do agente.'
where Rdb$Relation_Name='SGAGENTE' and Rdb$Field_Name='DESCAGE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do Ãºltimo usuï¿½rio que alterou.'
where Rdb$Relation_Name='SGATRIBUSU' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se o estoque serï¿½ controlado por multiplos almoxarifados.'
where Rdb$Relation_Name='SGEMPRESA' and Rdb$Field_Name='MULTIALMOXEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo inutilizado (transferï¿½ncia para a tabela SGFILIAL)'
where Rdb$Relation_Name='SGEMPRESA' and Rdb$Field_Name='PERCISSEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo inutilizado (transferï¿½ncia para a tabela SGPAIS)'
where Rdb$Relation_Name='SGEMPRESA' and Rdb$Field_Name='CODPAISEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a estaï¿½ï¿½o estï¿½ em modo demonstrativo.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='MODODEMOEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Habilita NFE para estaï¿½ï¿½o de trabalho (S/N).'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='NFEEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Tamanho da fonte para visualizaï¿½ï¿½o dos relatï¿½rios texto.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='TAMFONTETXT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome da fonte para visualizaï¿½ï¿½o dos relatï¿½rios texto.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='FONTETXT';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de inserï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do Ãºltimo usuï¿½rio que alterou.'
where Rdb$Relation_Name='SGESTACAO' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Driver de comunicaï¿½ï¿½o com a balanï¿½a.'
where Rdb$Relation_Name='SGESTACAOBAL' and Rdb$Field_Name='DRIVERBAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o uso principal da balanï¿½a.
"TO" - Todos;
"PI" - Pesagem inicial;
"TR" - Tiragem de renda/descarregamento;
"PF" - Pesagem final;'
where Rdb$Relation_Name='SGESTACAOBAL' and Rdb$Field_Name='TIPOPROCRECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de estaï¿½ï¿½es de trabalho.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da estaï¿½ï¿½o de trabalho.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODEST';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da impressora na estaï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='NROIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de impressoras.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODEMPIP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de impressoras.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODFILIALIP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da impressora.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de papel.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODEMPPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela papel.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODFILIALPP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do papel.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='CODPAPEL';

Update Rdb$Relation_Fields set Rdb$Description =
'Porta de impressï¿½o no windows.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='PORTAWIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Porta de impressï¿½o no linux.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='PORTALIN';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que define se a impressora ï¿½ padrï¿½o.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='IMPPAD';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de uso da impressora.:
NF - Nota fiscal;
NS - Nota fiscal - serviï¿½o;
PD - Pedido;
RS - Relatï¿½rio simples;
RG - Relatï¿½rio grï¿½fico;
TO - Todos;
'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='TIPOUSOIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que define se a impressï¿½o ï¿½ grï¿½fica.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='IMPGRAFICA';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de inserï¿½ï¿½o do registro.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='DTINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da inserï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='HINS';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do usuï¿½rio que inseriu.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='IDUSUINS';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='DTALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora da Ãºltima alteraï¿½ï¿½o.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='HALT';

Update Rdb$Relation_Fields set Rdb$Description =
'ID do Ãºltimo usuï¿½rio que alterou.'
where Rdb$Relation_Name='SGESTACAOIMP' and Rdb$Field_Name='IDUSUALT';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do estado civil.'
where Rdb$Relation_Name='SGESTCIVIL' and Rdb$Field_Name='DESCESTCIVIL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se ï¿½ um feriado bancï¿½rio.'
where Rdb$Relation_Name='SGFERIADO' and Rdb$Field_Name='BANCFER';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se ï¿½ um feriado trabalhista.'
where Rdb$Relation_Name='SGFERIADO' and Rdb$Field_Name='TRABFER';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de tributaï¿½ï¿½o da empresa, no caso de enquadramento no simples.'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='PERCSIMPLESFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo na tabela de unificaï¿½ï¿½o de cï¿½digos (SGUNIFCOD).'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CODUNIFCOD';

Update Rdb$Relation_Fields set Rdb$Description =
'Inscriï¿½ï¿½o municipal'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='INSCMUNFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Classificaï¿½ï¿½o Nacional de Atividades EconÃ´micas (CNAE).'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CNAEFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a filial ï¿½ contribuinte do IPI.'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CONTRIBIPIFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do fornecedor (contador)'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CODEMPCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do fornecedor (contador)'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CODFILIALCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do fornecedor (contador)'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='CODFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial no suframa'
where Rdb$Relation_Name='SGFILIAL' and Rdb$Field_Name='SUFRAMA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do convï¿½nio.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CODCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Convï¿½nio de cobranï¿½a do bloqueto.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CONVCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Versï¿½o do layout.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='VERLAYOUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do serviï¿½o.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='IDENTSERV';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o de ambiente do cliente. P=Produï¿½ï¿½o - T=Teste.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='IDENTAMBCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o do ambiente do banco. P=Produï¿½ï¿½o - T=Teste.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='IDENTAMBBCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da conta corrente.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='NUMCONTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o da emissï¿½o de bloqueto.
1 - Banco emite.
2 - Cliente emite.
3 - Banco prï¿½-emite e o cliente completa.
4 - Banco reemite.
5 - Banco nï¿½o reemite.
6 - Cobranï¿½a sem papel.
Obs.: Os campos 4 e 5 sï¿½ serï¿½o aceitos para cï¿½digo de movimento para remessa 31.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='IDENTEMITBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificaï¿½ï¿½o da distribuiï¿½ï¿½o.
1 - Banco.
2 - Cliente.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='IDENTDISTBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Especie do titulo.
01 - CH Cheque.
02 - DM Duplicata mercantï¿½l.
03 - DMI Duplicata mercantï¿½l p/ indicaï¿½ï¿½o.
04 - DS Duplicata de serviï¿½o.
05 - DSI DUplicata de serviï¿½ï¿½ p/ indicaï¿½ï¿½o.
06 - DR Duplicata rural.
07 - LC Letra de cambio.
08 - NCC Nota de crï¿½dito comercial.
09 - NCE Nota de crï¿½dito a exportaï¿½ï¿½o.
10 - NCI Nota de crï¿½dito indÃºstria.
11 - NCR Nota de crï¿½dito rural.
12 - NP Nota promissï¿½ria.
13 - NPR Nota promissï¿½ria rural.
14 - TM Triplicata mercantï¿½l.
15 - TS Triplicata de serviï¿½o.
16 - NS Nota de seguro.
17 - RC Recibo.
18 - FAT Fatura.
19 - ND Nota de dï¿½bito.
20 - AP Apolice de seguro.
21 - ME Mensalidade escolar.
22 - PC Parcela de consï¿½rcio.
99 - Outros.

'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='ESPECTIT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do juros de mora.
1 - Valor por dia.
2 - Taxa nensal.
3 - Isento.
'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CODJUROS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do desconto.
1 - Valor fixo atï¿½ a data informada.
2 - Percentual atï¿½ a data informada.
3 - Valor por antecipaï¿½ï¿½o por dia corrido.
4 - Valor por antecipaï¿½ï¿½o por dia util.
5 - Percentual sobre o valor nominal dia corrido.
6 - Percentual sobre o valor nominal dia util.
Obs.: Para as opï¿½ï¿½es 1 e 2 serï¿½ obrigatï¿½rio a informaï¿½ï¿½o da data.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CODDESC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo para protesto.
1 - Dias corridos.
2 - Dias utï¿½is.
3 - Nï¿½o protestar.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CODPROT';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dias para protesto.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='DIASPROT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo para baixa/devoluï¿½ï¿½o.
1 - Baixar/Devolver.
2 - Nï¿½o baixar/ Nï¿½o devolver.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CODBAIXADEV';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dias para a Baixa / Devoluï¿½ï¿½o.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='DIASBAIXADEV';

Update Rdb$Relation_Fields set Rdb$Description =
'Defini o aceite do arquivo de cnab S - sim e N - nï¿½'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='ACEITE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o padrï¿½o CNAB, pode ser: 240 ou 400
'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='PADRAOCNAB';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo do log:
LIB - Liberaï¿½ï¿½o de venda abaixo do custo
'
where Rdb$Relation_Name='SGLOG' and Rdb$Field_Name='TIPOLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o resumida da operaï¿½ï¿½o
'
where Rdb$Relation_Name='SGLOG' and Rdb$Field_Name='DESCLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o completa e observaï¿½ï¿½es sobre a operaï¿½ï¿½o
'
where Rdb$Relation_Name='SGLOG' and Rdb$Field_Name='OBSLOG';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de espaï¿½os entre colunas.'
where Rdb$Relation_Name='SGMODETIQUETA' and Rdb$Field_Name='EECMODETIQ';

Update Rdb$Relation_Fields set Rdb$Description =
'indica se a etiqueta ï¿½ pï¿½s script.'
where Rdb$Relation_Name='SGMODETIQUETA' and Rdb$Field_Name='POSSCRIPT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s segundo o ISO 3166-1'
where Rdb$Relation_Name='SGPAIS' and Rdb$Field_Name='CODPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla do paï¿½s com 3 caracteres.'
where Rdb$Relation_Name='SGPAIS' and Rdb$Field_Name='SIGLA3CPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de discagem direta a distancia do pais (ddi)'
where Rdb$Relation_Name='SGPAIS' and Rdb$Field_Name='DDIPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s na tabela do BACEN (Brasil=1058)'
where Rdb$Relation_Name='SGPAIS' and Rdb$Field_Name='CODBACENPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s na tabela EAN.'
where Rdb$Relation_Name='SGPAIS' and Rdb$Field_Name='CODEANPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de movimento para orï¿½amento'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV2';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica quando deve ser feita a verificaï¿½ï¿½o do crï¿½dito:
FV - Fechamento de venda;
II - Inserï¿½ï¿½o de ï¿½tem;
AB - Ambos;'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TIPOPREFCRED';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de movimento para serviï¿½os'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV4';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a aba frete serï¿½ obrigatï¿½ria na tela de vendas.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TABFRETEVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a aba adicionais serï¿½ obrigatï¿½ria na tela de vendas.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TABADICVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Trava tela de venda para nï¿½o receber tipo de movimento de NF na inserï¿½ï¿½o.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TRAVATMNFVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Opï¿½ï¿½o de validade para impressï¿½o nos orï¿½amentos:
"N"=nÃºmero de dias ; 
"D" data 
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TIPOVALIDORC';

Update Rdb$Relation_Fields set Rdb$Description =
'CNPJ Obrigatï¿½rio para o cadastro de clientes S ou N
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CNPJOBRIGCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para transportadora'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para transportadora.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da transportadora.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o de custo na compra sem ICMS.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CUSTOSICMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½lculo de comissï¿½es por duplicata
"S" calcula pelas parcelas do contas a receber.
"N" calcula pelo valor da venda.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='COMISPDUPL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para tipo de movimento de inventï¿½rio'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPT6';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para tipo de movimento de inventï¿½rio'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALT6';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de tipo de movimento de inventï¿½rio'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV6';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para permitir venda de matï¿½ria prima (S/N).'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='VENDAMATPRIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para permitir venda de matï¿½ria prima (S/N)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='VENDAPATRIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se o CNPJ do fornecedor ï¿½ obrigatï¿½rio.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CNPJFOROBRIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a insc. est. do fornecedor ï¿½ obrigatï¿½ria.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='INSCESTFOROBRIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se ao entrar com cï¿½digo de produto em textfield deverï¿½ buscar os produtos similares.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='BUSCAPRODSIMILAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cod. emp. do tipo de movimento padrï¿½o para RMA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPT8';

Update Rdb$Relation_Fields set Rdb$Description =
'Cod. filial do tipo de movimento padrï¿½o para RMA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALT8';

Update Rdb$Relation_Fields set Rdb$Description =
'Codigo do tipo de movimento padrï¿½o para RMA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV8';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se serï¿½ utilizada a descriï¿½ï¿½o completa nos orï¿½amentos e pedidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='DESCCOMPPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para mostrar observaï¿½ï¿½es do cliente na tela de venda.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='OBSCLIVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'flag que marca se deve recalcular os valores do item quando alterar o cabeï¿½alho'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='RECALCPCVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'flag que marca se deve recalcular os valores do item quando alterar o cabeï¿½alho'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='RECALCPCORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Busca produro por cï¿½digo de fornecedor.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='FILBUSCGENCODFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para usar imagem de assinatura no orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USAIMGASSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Imagem de assinatura pra orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='IMGASSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve validar o CPF do fornecedor pessoa fï¿½sica.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CONSISTECPFFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag "S" para indicar o uso de nome do vendedor no orï¿½amento, caso contrï¿½rio utilizarï¿½ o nome da empresa.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USANOMEVENDORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Seleciona o sistema de contabilidade utilizado.
00 - Nenhum
01 - Freedom Contï¿½bil
02 - Safe Contï¿½bil
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='SISCONTABIL';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se o banco poderï¿½ ser atualizado na tela de impressï¿½o de boleto.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ATBANCOIMPBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de cï¿½digo de barras.
1-EAN
2-39'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TIPOCODBAR';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se deve carregar os cï¿½digos dos orï¿½amentos nas observaï¿½ï¿½es dos pedidos, gerados a partir de orï¿½amentos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ADICORCOBSPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para a mensagem padrï¿½o para orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPMENSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para mensagem padrï¿½o para orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALMENSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da mensagem padrï¿½o para orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODMENSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Habilita para digitaï¿½ï¿½o campo de custo na compra.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CUSTOCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve mostrar aba de transportadora na tela de orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TABTRANSPORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Habilita e desabilita aba de solicitaï¿½ï¿½o na tela de compras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TABSOLCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve mostrar o preï¿½o de compra nos relatï¿½rios de compra.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='PRECOCPREL';

Update Rdb$Relation_Fields set Rdb$Description =
'S - Define controle de acesso para ativaï¿½ï¿½o de cliente por usuï¿½rio.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USUATIVCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a receber.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPHISTREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a receber.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALHISTREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a receber.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODHISTREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a pagar.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPHISTPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a pagar.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALHISTPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do histï¿½rico padrï¿½o para lanï¿½amentos financeiros provenientes do contas a pagar.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODHISTPAG';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se a liberaï¿½ï¿½o de crï¿½dito deve ser agrupada, para clientes com sub-clientes.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LCREDGLOBAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o filtro de vendedor ï¿½ obrigatï¿½ria na tela de manutenï¿½ï¿½o de comissï¿½es.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='VDMANUTCOMOBRIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Classe padrï¿½o para pedidos grï¿½ficos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CLASSPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o tipo de classe padrï¿½o para orï¿½amentos.
QA - Resultset como parï¿½metro (Query na Aplicaï¿½ï¿½o);
QJ - Parametros de filtro (Query no Jasper).
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TIPOCLASSPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve somar as quantidades dos itens de venda e lanï¿½ar no campo "volumes".'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='SOMAVOLUMES';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar o botï¿½o para busca de ceps nos cadastros.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='BUSCACEP';

Update Rdb$Relation_Fields set Rdb$Description =
'URL do web service de busca de endereï¿½o pelo cep.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='URLWSCEP';

Update Rdb$Relation_Fields set Rdb$Description =
'Classe padrï¿½o para pedido de compra.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CLASSCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o para o campo coringa obs01 da tabela CPCOMPRA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LABELOBS01CP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o para o campo coringa obs02 da tabela CPCOMPRA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LABELOBS02CP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o para o campo coringa obs03 da tabela CPCOMPRA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LABELOBS03CP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o para o campo coringa obs04 da tabela CPCOMPRA.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LABELOBS04CP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve consistir a inscriï¿½ï¿½o estadual de clientes do tipo pessoa fï¿½sica.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CONSISTEIEPF';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve destacar crï¿½dito de ICMS (empesa simples.)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CREDICMSSIMPLES';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da mensagem de destaque de ICMS de empresa enquadrada no simples.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da mensagem de destaque de ICMS para empresa enquadrada no simples.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALMS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da mensagem a ser destacada na nota fiscal, quando empresa destacar crï¿½dito de icms, estando enquadrada no simples.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODMENSICMSSIMPLES';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar gerar comissï¿½o padrï¿½o nas vendas 
geradas atravï¿½s de busca de orï¿½amentos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='GERACOMISVENDAORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar cï¿½digo unificado na tabela SGUNIFCOD 
para uso como destinatario ou remetente no conhecimento de frete.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='GERACODUNIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para conhecimento de frete.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOV9';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento para conheciemento de frete.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALT9';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de moviento padrï¿½o para conhecimento de frete.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPT9';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta de planejamento de Juros Pagos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPJP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta de planejamento de Juros Pagos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALJP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta de planejamento de Juros Pagos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANJP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta de planejamento de Juros Recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPJR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta de planejamento de Juros Recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALJR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta de planejamento de Juros Recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANJR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta de planejamento de Descontos recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPDR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta de planejamento de Descontos recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALDR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta de planejamento de Descontos recebidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANDR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta de planejamento de Descontos concedidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPDC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta de planejamento de Descontos concedidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALDC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da conta de planejamento de Descontos concedidos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANDC';

Update Rdb$Relation_Fields set Rdb$Description =
'Geraï¿½ï¿½o de contas a pagar por data de emissï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='GERAPAGEMIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar o vinculo entre lanï¿½amento financeiro (fnsublanca) e contrato/projeto. Para
apuraï¿½ï¿½o de custos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LANCAFINCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se eve habilitar o vinculo entre rma e contrato/projeto. Para apuraï¿½ï¿½o de custos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='LANCARMACONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Classe do plugin de integraï¿½ï¿½o Nfe'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CLASSNFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Diretï¿½rio padrï¿½o para arquivos NFE (windows).'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='DIRNFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Diretï¿½rio padrï¿½o para arquivos NFE (linux).'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='DIRNFELIN';

Update Rdb$Relation_Fields set Rdb$Description =
'1 - Produï¿½ï¿½o, 2 - Homologaï¿½ï¿½'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='AMBIENTENFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificador do processo de emissï¿½o da NFe:
0 - emissï¿½o de NF-e com aplicativo do contribuinte;
3 - emissï¿½o NF-e pelo contribuinte com aplicativo fornecido pelo Fisco.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='PROCEMINFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Identificador da versï¿½o do processo de emissï¿½o (informar a ersï¿½o do aplicativo emissor de NFe)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='VERPROCNFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de vencimento da licenï¿½a NFE.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='DTVENCTONFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve incluir as informaï¿½ï¿½es adicionais do produto na nota fiscal eletronica (Campo HinfAdProd)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='INFADPRODNFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do email padrï¿½o para envio de nfe.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPNF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do email padrï¿½o para envio nfe'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALNF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do email padrï¿½o para envio de nfe.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMAILNF';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve adicionar o desdobramento das parcelas nas observaï¿½ï¿½es da DANFE.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='EXIBEPARCOBSDANFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Regime tributï¿½rio para NFE.
1 - Simples Nacional
2 - Simples Nacional (excesso de sub-limite)
3 - Normal'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='REGIMETRIBNFE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve informar a compra na devoluï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='INFCPDEVOLUCAO';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar contas a receber a partir da data de emissï¿½o do pedido.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='GERARECEMIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a retensï¿½o de impostos na emissï¿½o da nota, reduzindo o valor liquido da nota fiscal.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='RETENSAOIMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Informa qual o tipo de custo deve ser usado no cï¿½lculo de lucratividade de orï¿½amentos/pedidos.
U - Ultima compra;
M - MPM
P - PEPS'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TIPOCUSTOLUC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar a aba importaï¿½ï¿½o na tela de compras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TABIMPORTCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar o valor total do item para digitaï¿½ï¿½o, na tela de orï¿½amentos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='HABVLRTOTITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar a busca genï¿½rica de produtos na compra.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USABUSCAGENPRODCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve transferir as observaï¿½ï¿½es do orï¿½amento para o pedido.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ADICOBSORCPED';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o preï¿½o de cotaï¿½ï¿½es para pedidos de compras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USAPRECOCOT';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve bloquear o faturamento de pedido de compra com preï¿½o nï¿½o aprovado.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='BLOQPRECOAPROV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPFT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALFT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de fornecedor para transportadoras.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOFORFT';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o preï¿½o para comissionamento do cadastro de produtos, para calculo de comissioament especial por seï¿½ï¿½o de produï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='USAPRECOCOMIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o mecanismo de comissionamento especial (por setor de produï¿½ï¿½o)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ESPECIALCOMIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento para orï¿½amentos de serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALTS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento para orï¿½amentos de serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOVS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento para orï¿½amentos de serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPTS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa de plano de pagamento sem valor financeiro para uso em devoluï¿½ï¿½es e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPSV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial de plano de pagamento sem valor financeiro para uso em devoluï¿½ï¿½es e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALSV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de plano de pagamento sem valor financeiro para uso em devoluï¿½ï¿½es e afins.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANOPAGSV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa padrï¿½o planejamento para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPPC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o planejamento padrï¿½o para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALPC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento padrï¿½o para pagamento com cheques.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODPLANPC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o padrï¿½o para geraï¿½ï¿½o do nosso nÃºmero (boletos e arquivos de remessa)
D - Nï¿½mero do documento (doc)
R - Nï¿½mero do contas a receber (codrec)
S - Sequencial Ãºnico (recomendado)'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='TPNOSSONUMERO';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se o nÃºmero  NF serï¿½ impresso no campo documento do boleto (S/N).
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='IMPDOCBOL';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve bloquear caixas para lanï¿½amentos retroativos.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='FECHACAIXA';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve realizar o fechamento de caixas automaticamente, 
ou atravï¿½s de procedimento manual.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='FECHACAIXAAUTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Chave de licenciamento do mï¿½dulo Sped EFD.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='KEYLICEFD';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve encaminhar orï¿½amentos contendo produtos acabados para a produï¿½ï¿½o (Sistema Pull)
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ENCORCPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento para NF de importaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento para NF de importaï¿½ï¿½'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento para nota fiscal de importaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODTIPOMOVIM';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar o mecanismo de comissionamento progressivo, de acordo com o desconto concedido.
"S" - Sim
"N" - Nï¿½o
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='COMISSAODESCONTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para histï¿½rico padrï¿½o para baixa CNAB.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODEMPHC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para histï¿½rico padrï¿½o para baixa CNAB.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODFILIALHC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do histï¿½rico padrï¿½o para baixa CNAB.
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='CODHISTCNAB';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve alinhar a tela de lanï¿½amentos financeiros a direta.
S - Sim
N - Nï¿½o
'
where Rdb$Relation_Name='SGPREFERE1' and Rdb$Field_Name='ALINHATELALANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cabeï¿½alho 1 do termo de recebimento.'
where Rdb$Relation_Name='SGPREFERE2' and Rdb$Field_Name='CABTERMR01';

Update Rdb$Relation_Fields set Rdb$Description =
'Cabeï¿½alho 2 do termo de recebimento.'
where Rdb$Relation_Name='SGPREFERE2' and Rdb$Field_Name='CABTERMR02';

Update Rdb$Relation_Fields set Rdb$Description =
'Rodapï¿½ do termo de recebimento.'
where Rdb$Relation_Name='SGPREFERE2' and Rdb$Field_Name='RODTERMR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da atividade padrï¿½o para envio de campanha.'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODATIVCE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da atividade padrï¿½o para envio de campanha frustrado.'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODATIVTE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve bloquear atendimentos para clientes em atraso. S - Sim
N - Nï¿½o
'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='BLOQATENDCLIATRASO';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve exibir informaï¿½ï¿½o de cliente em atraso, na tela de gerenciamento de atendimentos.
S - Sim
N - Nï¿½'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='MOSTRACLIATRASO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do email de notificaï¿½ï¿½o de chamados a tï¿½cnicos designados.'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODEMPNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do email de notificaï¿½ï¿½o de chamados a tï¿½cnicos designados.'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODFILIALNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do email de notificaï¿½ï¿½o de chamados a tï¿½cnicos designados.'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODEMAILNC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do email de notificaï¿½ï¿½o de chamados ao cliente.
'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODEMPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do email de notificaï¿½ï¿½o de chamados ao cliente.
'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODFILIALEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do email de notificaï¿½ï¿½o de chamados ao cliente.
'
where Rdb$Relation_Name='SGPREFERE3' and Rdb$Field_Name='CODEMAILEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para cliente.'
where Rdb$Relation_Name='SGPREFERE4' and Rdb$Field_Name='CODEMPCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo filial para cliente.'
where Rdb$Relation_Name='SGPREFERE4' and Rdb$Field_Name='CODFILIALCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente padrï¿½o para venda.'
where Rdb$Relation_Name='SGPREFERE4' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag indica se chama a tela de fecha venda automatico quando buscar orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE4' and Rdb$Field_Name='AUTOFECHAVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se usa lote na tela de orï¿½amento.'
where Rdb$Relation_Name='SGPREFERE4' and Rdb$Field_Name='USALOTEORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de meses para descarte de anï¿½lises de contra-prova.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='MESESDESCCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para Ordem de produï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento padrï¿½o para Ordem de Produï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODEMPTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento padrï¿½o para Ordem de Produï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODFILIALTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da RMA gerada pela OP.
Pode ser:
"PE" - Pendente
"AF" - Aprovada 
"EF" - Expedida '
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='SITRMAOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Imagem da assinatura do responsï¿½vel tï¿½cnico.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='IMGASSRESP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve baixar o estoque de insumos em RMA"s aprovadas nï¿½o expedidas.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='BAIXARMAAPROV';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve permitir a exclusï¿½o de RMAs geradas por outro usuï¿½rio.
Regra se aplica apenas em RMA vinculadas a Ordens de produï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='APAGARMAOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica qual o nome a ser impresso no relatï¿½rio de anï¿½lise.
"U" - Usuï¿½rio de cadastrou a anï¿½se;
"R" - Responsï¿½vel pela produï¿½ï¿½o;'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='NOMERELANAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Status padrï¿½o da OP apï¿½s inserï¿½ï¿½o.
"PE" Pendente
"FN" Finalizada'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='SITPADOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Status padrï¿½o da OP de conversï¿½o de produtos apï¿½s inserï¿½ï¿½o.
"PE" Pendente
"FN" Finalizada'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='SITPADOPCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve habilitar conversï¿½o de produtos na compra.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='HABCONVCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve permitir a finalizaï¿½ï¿½o de Ops em etapas.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='PRODETAPAS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para tipo de movimento para entrada de subprodutos.
'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODEMPTS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para tipo de movimento para entrada de subprodutos.
'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODFILIALTS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento para entrada de subprodutos.
'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODTIPOMOVSP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento padrï¿½o para envio de remessa para produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODEMPEN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento padrï¿½o para envio de remessa para produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODFILIALEN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para envio de remessa para produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODTIPOMOVEN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento padrï¿½o para retorno de produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODEMPRE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento padrï¿½o para retorno de produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODFILIALRE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para retorno de produï¿½ï¿½o externa.'
where Rdb$Relation_Name='SGPREFERE5' and Rdb$Field_Name='CODTIPOMOVRE';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de movimento de consignaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODTIPOMOVCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do planejamento padrï¿½o para venda consignada.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODEMPPV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do planejamento padrï¿½o para consignaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODEMPPC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do planejamento padrï¿½o para consignaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODFILIALPC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento padrï¿½o para consignaï¿½ï¿½o.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODPLANCONSIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do planejamento padrï¿½o para venda consignada.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODFILIALPV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do planejamento padrï¿½o para venda consignada.'
where Rdb$Relation_Name='SGPREFERE7' and Rdb$Field_Name='CODPLANVDCONSIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o tipo de recepï¿½ï¿½o padrï¿½o para recebimento com pesagem.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para o tipo de recepï¿½ï¿½o padrï¿½o para recebimento com pesagem.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de recepï¿½ï¿½o padrï¿½o para recebimento com pesagem.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODTIPORECMERC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa to tipo de recebimento para coletas'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPCM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de recebimento para coleta.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALCM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de recebimento para coleta.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODTIPORECMERCCM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o tipo de movimento de compra gera a partir de pedidos de compra.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para o tipo de movimento de compra gerada a partir de pedidos de compra.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para compra, gerada a partir de pedidos de compra. '
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODTIPOMOVTC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve gerar chamado (CRM) a partir de itens de ordem de serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='GERACHAMADOOS';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve utilizar o preï¿½o do peï¿½a consertada no orï¿½amento de serviï¿½os.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='USAPRECOPECASERV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para o tipo de movimento padrï¿½o para devoluï¿½ï¿½o de peï¿½as consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPDS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para o tipo de movimento padrï¿½o devoluï¿½ï¿½o de peï¿½as consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALDS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento padrï¿½o para devoluï¿½ï¿½o de peï¿½as consertadas.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODTIPOMOVDS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto padrï¿½o para serviï¿½os.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODEMPSE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para produto padrï¿½o para serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODFILIALSE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto padrï¿½o para serviï¿½o.'
where Rdb$Relation_Name='SGPREFERE8' and Rdb$Field_Name='CODPRODSE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de agendamento'
where Rdb$Relation_Name='SGTIPOAGENDA' and Rdb$Field_Name='CODTIPOAGD';

Update Rdb$Relation_Fields set Rdb$Description =
'Regiï¿½o geogrï¿½fica
N - Norte
NE - Nordeste
S - Sul
SE - Sudeste
CO - Centro Oeste'
where Rdb$Relation_Name='SGUF' and Rdb$Field_Name='REGIAOUF';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de cï¿½digo:
"C" - Cliente;
"F" - Fornecedor;
"T" - Transportadora;
"E" - Empresa/Filial;'
where Rdb$Relation_Name='SGUNIFCOD' and Rdb$Field_Name='TIPOUNIFCOD';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do cï¿½digo unificado (no caso de cliente/fornecedor/transportadora utilizar a razï¿½o social).'
where Rdb$Relation_Name='SGUNIFCOD' and Rdb$Field_Name='DESCUNIFCOD';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o usuï¿½rio pode liberar venda abaixo do custo.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='BAIXOCUSTOUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o nï¿½vel de aprovaï¿½ï¿½o do usuario para solicitaï¿½ï¿½o de compras.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='APROVCPSOLICITACAOUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica o nï¿½vel de aprovaï¿½ï¿½o do usuario para RMA.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='APROVRMAUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se o usuï¿½rio tem acesso para ativar clientes.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='ATIVCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cor representativa para visualizaï¿½ï¿½o na agenda corporativa.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='CORAGENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para configuraï¿½ï¿½o de email.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='CODEMPCE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para configuraï¿½ï¿½o de email.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='CODFILIALCE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da configuraï¿½ï¿½o de email.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='CODCONFEMAIL';

Update Rdb$Relation_Fields set Rdb$Description =
'Permite que o usuï¿½rio cancele Ordens de Produï¿½ï¿½o geradas
por outros usuï¿½rios.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='CANCELAOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Define se o usuï¿½rio pode liberar venda de produtos do patrimÃ´nio (imobilizado).'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='VENDAPATRIMUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o usuï¿½rio estï¿½ ativo.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='ATIVOUSU';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o usuï¿½rio pode visualizar a aba lucratividade na tela de venda.'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='VISUALIZALUCR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o usuï¿½rio possui permissï¿½o para digitaï¿½ï¿½o
do peso nas telas de pesagem (recebimento de mercadoria)'
where Rdb$Relation_Name='SGUSUARIO' and Rdb$Field_Name='LIBERACAMPOPESAGEM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da campanha.'
where Rdb$Relation_Name='TKCAMPANHA' and Rdb$Field_Name='CODCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o da campanha.'
where Rdb$Relation_Name='TKCAMPANHA' and Rdb$Field_Name='DESCCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es relativas Ã  campanha.'
where Rdb$Relation_Name='TKCAMPANHA' and Rdb$Field_Name='OBSCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da campanha.'
where Rdb$Relation_Name='TKCAMPANHACLI' and Rdb$Field_Name='CODCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente.'
where Rdb$Relation_Name='TKCAMPANHACLI' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da campanha.'
where Rdb$Relation_Name='TKCAMPANHACTO' and Rdb$Field_Name='CODCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contato.'
where Rdb$Relation_Name='TKCAMPANHACTO' and Rdb$Field_Name='CODCTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da origem do contato.'
where Rdb$Relation_Name='TKCONTATO' and Rdb$Field_Name='CODORIGCONT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de contato.'
where Rdb$Relation_Name='TKCONTATO' and Rdb$Field_Name='CODEMPTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de contato.'
where Rdb$Relation_Name='TKCONTATO' and Rdb$Field_Name='CODFILIALTO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de contato.'
where Rdb$Relation_Name='TKCONTATO' and Rdb$Field_Name='CODTIPOCONT';

Update Rdb$Relation_Fields set Rdb$Description =
'Formato da pagina de cï¿½digos.'
where Rdb$Relation_Name='TKEMAIL' and Rdb$Field_Name='CHARSET';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de histï¿½rico: 
H - Histï¿½rico 
V - Visita ao cliente
N - Visita a novos clientes(nï¿½o cadastrados)
C - Campanha
O - Cobranï¿½a
L - Ligaï¿½ï¿½o Prï¿½-venda
P - Ligaï¿½ï¿½o Pï¿½s-venda
I - Indefinida'
where Rdb$Relation_Name='TKHISTORICO' and Rdb$Field_Name='TIPOHISTTK';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da campanha.'
where Rdb$Relation_Name='TKHISTORICO' and Rdb$Field_Name='CODEMPCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da campanha.'
where Rdb$Relation_Name='TKHISTORICO' and Rdb$Field_Name='CODFILIALCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da campanha.'
where Rdb$Relation_Name='TKHISTORICO' and Rdb$Field_Name='CODCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sequencia da situaï¿½ï¿½o da campanha (para fk com tksitcamp)'
where Rdb$Relation_Name='TKHISTORICO' and Rdb$Field_Name='SEQSITCAMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla ou abreviaï¿½ï¿½o da descriï¿½ï¿½o da classificaï¿½ï¿½o do cliente (utilizado em alguns relatï¿½rios)'
where Rdb$Relation_Name='VDCLASCLI' and Rdb$Field_Name='SIGLACLASCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do endereï¿½o da pessoa autorizada a comprar em nome do cliente.'
where Rdb$Relation_Name='VDCLIAUTP' and Rdb$Field_Name='NUMAUTP';

Update Rdb$Relation_Fields set Rdb$Description =
'Complemento do endereï¿½o da pessoa autorizada a comprar em nome do cliente.'
where Rdb$Relation_Name='VDCLIAUTP' and Rdb$Field_Name='COMPLAUTP';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de admissï¿½o do cliente.'
where Rdb$Relation_Name='VDCLICOMPL' and Rdb$Field_Name='DTADMTRABCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o do trabalho do cliente.'
where Rdb$Relation_Name='VDCLICOMPL' and Rdb$Field_Name='ENDTRABCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do logradouro do trabalho do cliente.'
where Rdb$Relation_Name='VDCLICOMPL' and Rdb$Field_Name='NUMTRABCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa para tabela de estado civil.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODEMPEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial para tabela de estado civil.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODFILIALEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da tabela de estado civil.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODTBEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do estado civil.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODITTBEC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCARTCOB.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODEMPCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCARTCOB.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODFILIALCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
' Inscriï¿½ï¿½o estadual do cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='INSCCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Orgï¿½o de espediï¿½ï¿½o do RG.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SSPCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Email de conbranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='EMAILCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de abertura do crï¿½dito'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='DTINITR';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do Celular
'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CELCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Tempo de residï¿½ncia do cliente'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='TEMPORESCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o eletrÃ´nico do site do cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SITECLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo contabil de debito.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODCONTDEB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo contabil de credito'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODCONTCRED';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo contabil'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODCLICONTAB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do municipio (IBGE)'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODMUNIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla da Unidade da Federeï¿½ï¿½o (Estado)'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SIGLAUF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do pais.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODPAIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do municipio (IBGE) do endereï¿½o de entrega.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODMUNICENT';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla da Unidade da Federaï¿½ï¿½o do endereï¿½o de entrega.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SIGLAUFENT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s do endereï¿½o de entrega.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODPAISENT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do municipio (IBGE) do endereï¿½o de cobranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODMUNICCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla da Unidade da Federaï¿½ï¿½o do endereï¿½o de cobranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SIGLAUFCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do paï¿½s do endereï¿½o de cobranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODPAISCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo na tabela de unificaï¿½ï¿½o de cï¿½digos (SGUNIFCOD).'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODUNIFCOD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do SUFRAMA do cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='SUFRAMACLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da atividade principal, padrï¿½o CNAE.
'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CODCNAE';

Update Rdb$Relation_Fields set Rdb$Description =
'Inscriï¿½ï¿½o municipal do cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='INSCMUNCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual padrï¿½o de desconto para o cliente.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='PERCDESCCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Contato no cliente, para cobranï¿½a.'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='CONTCLICOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se deve haver o desconto do IPI ao preï¿½o da mercadoria.
'
where Rdb$Relation_Name='VDCLIENTE' and Rdb$Field_Name='DESCIPI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo complementar ou da campanha que o cliente participa  junto ao fornecedor.'
where Rdb$Relation_Name='VDCLIENTEFOR' and Rdb$Field_Name='CODCPCLIFOR';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es referentes a meta de vendas para um determinado ano.'
where Rdb$Relation_Name='VDCLIMETAVEND' and Rdb$Field_Name='OBSMETAVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se o cliente ï¿½ pontual na referencia comercial. (S ou N)'
where Rdb$Relation_Name='VDCLIREFC' and Rdb$Field_Name='PONTUAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Mï¿½dia de atraso do cliente na referencia comercial.'
where Rdb$Relation_Name='VDCLIREFC' and Rdb$Field_Name='MEDIAATRASO';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a empresa ï¿½ avalista do cliente.'
where Rdb$Relation_Name='VDCLIREFC' and Rdb$Field_Name='AVALISTA';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es.'
where Rdb$Relation_Name='VDCLIREFC' and Rdb$Field_Name='OBSREFC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome da referï¿½ncia pessoal.'
where Rdb$Relation_Name='VDCLIREFP' and Rdb$Field_Name='NOMEREFP';

Update Rdb$Relation_Fields set Rdb$Description =
'Complemento do endereï¿½o.'
where Rdb$Relation_Name='VDCLIREFP' and Rdb$Field_Name='COMPLREFP';

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o da terra.'
where Rdb$Relation_Name='VDCLITERRA' and Rdb$Field_Name='ENDTERRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Placa do veï¿½culo.'
where Rdb$Relation_Name='VDCLIVEIC' and Rdb$Field_Name='PLACAVEIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Modelo do veï¿½culo.'
where Rdb$Relation_Name='VDCLIVEIC' and Rdb$Field_Name='MODELOVEIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o veï¿½culo estï¿½ alienado.'
where Rdb$Relation_Name='VDCLIVEIC' and Rdb$Field_Name='ALIENADOVEIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Ano de fabricaï¿½ï¿½o.'
where Rdb$Relation_Name='VDCLIVEIC' and Rdb$Field_Name='ANOVEIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do veï¿½culo.'
where Rdb$Relation_Name='VDCLIVEIC' and Rdb$Field_Name='VALORVEIC';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de comissï¿½o:
F-Valor gerado no faturamento
R-Valor gerado no recebimento
'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='TIPOCOMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.
'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='DTCOMPCOMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da comissï¿½o:
C1 - Em aberto
C2 - Liberada
CP - Paga'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='STATUSCOMI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do vendedor.'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do vendedor.'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do vendedor.'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da venda vinculada (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODEMPVE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da venda vinculada (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODFILIALVE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda (comissionamento especial)'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='CODVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Estado de manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='VDCOMISSAO' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Documento da consignaï¿½ï¿½'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='DOCCONSIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODEMPSL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODFILIALSL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODSUBLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do sub-lanï¿½amento financeiro de devoluï¿½ï¿½o.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODEMPSD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do sub-lanï¿½amento financeiro de devoluï¿½ï¿½o.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODFILIALSD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lanï¿½amento financeiro de devoluï¿½ï¿½o.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODLANCASD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do sub-lanï¿½amento financeiro de devoluï¿½ï¿½o.'
where Rdb$Relation_Name='VDCONSIGNACAO' and Rdb$Field_Name='CODSUBLANCASD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contrato.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='CODCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do contrato.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='DESCCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do cliente'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='CODEMPCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do cliente.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='CODFILIALCL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de cobranï¿½a do contrato:
"ME" - Contrato Mensal
"BI" - Contrato Bimestral
"AN" - Contrato Anual
"ES" - Contrato Esporï¿½dico'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='TPCOBCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Dia de vencimento da cobranï¿½a.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='DIAVENCCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Dia do mes para fechamento das cobranï¿½as.'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='DIAFECHCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se tem caracterï¿½sticas de contrato ou de projeto.
"C" - Contrato;
"P" - Projeto;'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='TPCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do item de contrato'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='DESCITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODEMPPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do produto.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODFILIALPD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade do produto no ï¿½tem de contrato.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='QTDITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do produto excedente.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODEMPPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do produto excedente.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODFILIALPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto excedente.'
where Rdb$Relation_Name='VDITCONTRATO' and Rdb$Field_Name='CODPRODPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do contrato.'
where Rdb$Relation_Name='VDITCONTRATOAND' and Rdb$Field_Name='CODCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem de contrato.'
where Rdb$Relation_Name='VDITCONTRATOAND' and Rdb$Field_Name='CODITCONTR';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½o sobre o andamento do projeto.'
where Rdb$Relation_Name='VDITCONTRATOAND' and Rdb$Field_Name='OBSAND';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de conclusï¿½o do projeto.'
where Rdb$Relation_Name='VDITCONTRATOAND' and Rdb$Field_Name='PERCAND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de almoxarifados.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de almoxarifados.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'"*"  - Orï¿½amento em aberto;
"OA" - Orï¿½amento em aberto;
"OC" - Orï¿½amento completo/impresso;
"OL" - Orï¿½amento liberado/aprovado;
"OV" - Orï¿½amento faturado.
"OP" - Orï¿½amento produzido.
"CA" - Orï¿½amento Cancelado/Nï¿½o Aprovado.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='STATUSITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do prazo de entrega.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODEMPPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do prazo de entrega.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODFILIALPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do prazo de entrega.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='CODPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Prazo de entrega (em dias) no ï¿½tem.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='DIASPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para por a tabela em manutenï¿½ao (S/N).'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da entrega 
N - Nï¿½o entregue
E - Entregue
'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='SITENTITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do termo de recebimento.
E - Emitir
N - Nï¿½o emitir
O - Emitido'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='SITTERMRITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag de faturamento S/N/P
(Sim, nï¿½o, parcial)'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='FATITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor previsto de comissï¿½o no item de orï¿½amento.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='VLRCOMISITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual previsto de comissï¿½o no item de orï¿½amento.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='PERCCOMISITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor previsto de frete por item do orï¿½amento.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='VLRFRETEITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de aprovaï¿½ï¿½o do ï¿½tem de orï¿½amento.'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='DTAPROVITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o da produï¿½ï¿½o do ï¿½tem de orï¿½amento.
PE - Pendente
EP - Em produï¿½ï¿½o
NP - Nï¿½o produzir
PD - Produzido'
where Rdb$Relation_Name='VDITORCAMENTO' and Rdb$Field_Name='SITPRODITORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do vendedor.'
where Rdb$Relation_Name='VDITREGRACOMIS' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do vendedor '
where Rdb$Relation_Name='VDITREGRACOMIS' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do vendedor.'
where Rdb$Relation_Name='VDITREGRACOMIS' and Rdb$Field_Name='CODVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela de almoxarifados.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela de almoxarifados.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALAX';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do almoxarifado.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODALMOX';

Update Rdb$Relation_Fields set Rdb$Description =
'Base de cï¿½lculo do ICMS sem reduï¿½ï¿½o e outras alteraï¿½ï¿½es.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRBASEICMSBRUTITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do ICMS da substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRBASEICMSSTITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do ICMS da substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRICMSSTITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Margem de valor agregado para calculo da base de calculo do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='MARGEMVLAGRITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='TIPOST';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do prazo de entrega.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do prazo de entrega.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do prazo de entrega.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Prazo de entrega (em dias) no ï¿½tem.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='DIASPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do conveniado'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODCONV';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do item de classificaï¿½ï¿½o fiscal (fk transitï¿½ria, utilizada no trigger que carrega a tabela lfitvenda)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do item de classificaï¿½ï¿½o fiscal (fk transitï¿½ria, utilizada no trigger que carrega a tabela lfitvenda)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da classificaï¿½ï¿½o fiscal (fk transitï¿½ria, utilizada no trigger que carrega a tabela lfitvenda)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tem de classificaï¿½ï¿½o fiscal (fk transitï¿½ria, utilizada no trigger que carrega a tabela lfitvenda)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODITFISC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da compra (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da compra (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALCP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da compra (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do ï¿½tem da compra (devoluï¿½ï¿½o)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODITCOMPRA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do item da nota de remessa'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPVR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do item de nota de remessa'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALVR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda do item de nota de remessa'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODVENDAVR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de nota de remessa'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODITVENDAVR';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do numero de serie'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODEMPNS';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do nÃºmero de sï¿½rie.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='CODFILIALNS';

Update Rdb$Relation_Fields set Rdb$Description =
'Campo para abrigar temporariamente o nÃºmero de sï¿½rie do produto (para uso do trigger quando produto for unitï¿½rio)'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='NUMSERIETMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para manutenï¿½ï¿½o da tabela (S/N).'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do icms st retido na opearaï¿½ï¿½o anterior.'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRBASEICMSSTRETITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do icms st retido na operaï¿½ï¿½o anterior.
'
where Rdb$Relation_Name='VDITVENDA' and Rdb$Field_Name='VLRICMSSTRETITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'"*"  - Orï¿½amento em aberto;
"OA" - Orï¿½amento em aberto;
"OC" - Orï¿½amento completo/impresso;
"OL" - Orï¿½amento liberado/aprovado;
"OV" - Orï¿½amento faturado.
"OP" - Orï¿½amento produzido.
"CA" - Orï¿½amento Cancelado/Nï¿½o Aprovado.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='STATUSORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para por a tabela em manutenï¿½ao (S/N).'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da transportadora.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODEMPTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da transportadora.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODFILIALTN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da transportadora.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor previsto de comissï¿½o no orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='VLRCOMISORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do tipo de movimento previsto para o faturamento do orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODEMPTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do tipo de movimento previsto para o faturamento do orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODFILIALTM';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do tipo de movimento previsto para o faturamento do orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='CODTIPOMOV';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da aprovaï¿½ï¿½o do orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='DTAPROVORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Justificativa pelo cancelamento do orï¿½amento.'
where Rdb$Relation_Name='VDORCAMENTO' and Rdb$Field_Name='JUSTIFICCANCORC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa.'
where Rdb$Relation_Name='VDPRAZOENT' and Rdb$Field_Name='CODEMP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial'
where Rdb$Relation_Name='VDPRAZOENT' and Rdb$Field_Name='CODFILIAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½d. prazo de entrega.'
where Rdb$Relation_Name='VDPRAZOENT' and Rdb$Field_Name='CODPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Descriï¿½ï¿½o do prazo de entrega'
where Rdb$Relation_Name='VDPRAZOENT' and Rdb$Field_Name='DESCPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dias para entrega.'
where Rdb$Relation_Name='VDPRAZOENT' and Rdb$Field_Name='DIASPE';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica qual o tipo de preï¿½o que originï¿½rio.
B - Preï¿½o Base
I - Custo informado;
O - Outros;
'
where Rdb$Relation_Name='VDPRECOPROD' and Rdb$Field_Name='TIPOPRECOPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Data da alteraï¿½ï¿½o do preï¿½o.
'
where Rdb$Relation_Name='VDPRECOPROD' and Rdb$Field_Name='DTALTPRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Hora de alteraï¿½ï¿½o do preï¿½o.
'
where Rdb$Relation_Name='VDPRECOPROD' and Rdb$Field_Name='HALTPRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o anterior Ã  Ãºltima alteraï¿½ï¿½o.
'
where Rdb$Relation_Name='VDPRECOPROD' and Rdb$Field_Name='PRECOANT';

Update Rdb$Relation_Fields set Rdb$Description =
'Usuï¿½rio que realizou a alteraï¿½ï¿½o no preï¿½o.
'
where Rdb$Relation_Name='VDPRECOPROD' and Rdb$Field_Name='IDUSUALTPRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Percentual de comissï¿½o para o grupo de comissionados (comissionamento especial por producao)'
where Rdb$Relation_Name='VDREGRACOMIS' and Rdb$Field_Name='PERCCOMISGERAL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da remessa de consignaï¿½ï¿½o.'
where Rdb$Relation_Name='VDREMCONSIG' and Rdb$Field_Name='CODREMCO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto remetido.'
where Rdb$Relation_Name='VDREMCONSIG' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade remetida em bonificaï¿½ï¿½o.'
where Rdb$Relation_Name='VDREMCONSIG' and Rdb$Field_Name='QTDBONIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o do produto remetido.'
where Rdb$Relation_Name='VDREMCONSIG' and Rdb$Field_Name='PRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (cheque).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='CHEQTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (pessoa fï¿½sica).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='FISTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (pessoa jurï¿½dica).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='JURTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (Filiaï¿½ï¿½o).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='FILTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (local de trabalho).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='LOCTRABTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (Referï¿½ncias comerciais).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='REFCOMLTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (Dados bancï¿½rios).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='BANCTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (referï¿½ncias pessoais).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='REFPESTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (dados do cÃ´njuge jurï¿½dica).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='CONJTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (veï¿½culos).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='VEICTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (imï¿½veis).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='IMOVTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se haverï¿½o aba adicional no cadastro de clientes (terras).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='TERRATIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag para dados complementares (sï¿½cios).'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='SOCIOTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se cliente ï¿½ produtor rural.'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='PRODRURALTIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Sigla ou abreviaï¿½ï¿½o da descriï¿½ï¿½o do tipo de cliente (utilizado em alguns relatï¿½rios)'
where Rdb$Relation_Name='VDTIPOCLI' and Rdb$Field_Name='SIGLATIPOCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo na tabela de unificaï¿½ï¿½o de cï¿½digos (SGUNIFCOD).'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='CODFILIALUC';

Update Rdb$Relation_Fields set Rdb$Description =
'Nome do conjuge do transportado (pessoa fï¿½sica)'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='CONJUGETRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Placa do veï¿½culo do transportador (pessoa fï¿½sica)'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='PLACATRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero de dependentes do transportador (pessoa fï¿½sica)'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='NRODEPENDTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero da Identidade.'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='RGTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo de pagamento do gps/inss.'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='CODGPS';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do PIS.'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='NROPISTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es.'
where Rdb$Relation_Name='VDTRANSP' and Rdb$Field_Name='OBSTRAN';

Update Rdb$Relation_Fields set Rdb$Description =
'Data de competï¿½ncia.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='DTCOMPVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa na tabela FNCARTCOB.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODEMPCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial na tabela FNCARTCOB.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODFILIALCB';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da carteira de cobranï¿½a.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODCARTCOB';

Update Rdb$Relation_Fields set Rdb$Description =
'Nï¿½mero do pedido do cliente.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='PEDCLIVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do icms da substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='VLRICMSSTVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor da base de calculo do icms de substituiï¿½ï¿½o tributï¿½ria.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='VLRBASEICMSSTVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Flag que indica se a tabela estï¿½ em manutenï¿½ï¿½o (S/N).'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='EMMANUT';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do crï¿½dito de ICMS destacado na venda quando do enquadramento no simples.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='VLRICMSSIMPLES';

Update Rdb$Relation_Fields set Rdb$Description =
'Alï¿½quota do crï¿½dito de ICMS destacado na venda quando do enquadramento no simples.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='PERCICMSSIMPLES';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor base para calculo das comissï¿½es especiais.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='VLRBASECOMIS';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½o a ser repassada para a tabela fnreceber no trigger/procedure de inserï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='OBSREC';

Update Rdb$Relation_Fields set Rdb$Description =
'Informaï¿½ï¿½es complementares da nota fiscal ( de interesse do fisco );'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='INFCOMPL';

Update Rdb$Relation_Fields set Rdb$Description =
'Situaï¿½ï¿½o do documento fiscal:
00-Documento regular;
01-Documento regular expontï¿½neo;
02-Documento cancelado;
03-Documento cancelado expontï¿½neo
04-NFE Denegada;
05-NFE Numeraï¿½ï¿½o inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expontï¿½neo;
08-Documento emitido com base em Regime Especial ou Norma Especï¿½fica;'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='SITDOC';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o valor do IPI foi descontado no preï¿½o dos ï¿½tens.
'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='DESCIPIVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da OP vinculada (remessa industrializaï¿½ï¿½o)
'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODEMPOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da OP vinculada (remessa industrializaï¿½ï¿½o)
'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODFILIALOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da sequencia da OP vinculada (remessa industrializaï¿½ï¿½o)
'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='SEQOP';

Update Rdb$Relation_Fields set Rdb$Description =
'CCï¿½digo da OP vinculada (remessa industrializaï¿½ï¿½o)
'
where Rdb$Relation_Name='VDVENDA' and Rdb$Field_Name='CODOP';

Update Rdb$Relation_Fields set Rdb$Description =
'Tipo de venda
O = Orï¿½amento
V = Venda
E = Vencda Ecf
'
where Rdb$Relation_Name='VDVENDACOMIS' and Rdb$Field_Name='TIPOVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da consignaï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODCONSIG';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda em consignaï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODVENDACO';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do cliente.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODCLI';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do produto.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODPROD';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o praticado.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='PRECO';

Update Rdb$Relation_Fields set Rdb$Description =
'Preï¿½o aplicado ao cliente.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='PRECOVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Quantidade de bonificaï¿½ï¿½es.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='QTDBONIF';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do item de venda'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODEMPVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do item de venda.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODFILIALVD';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da venda do item de venda.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do item de venda.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODITVENDA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODEMPSL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODFILIALSL';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do sub-lanï¿½amento financeiro.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODSUBLANCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa do sub-lanï¿½amento financeiro de contra-partida.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODEMPSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial do sub-lanï¿½amento financeiro de contra-partida.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODFILIALSC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do lanï¿½amento financeiro de contra-partida.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODLANCASC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo do sub-lanï¿½amento financeiro de contra-partida.'
where Rdb$Relation_Name='VDVENDACONSIG' and Rdb$Field_Name='CODSUBLANCASC';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da funcao/cargo do vendedor.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='CODEMPFU';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da funcao do comissionado.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='CODFILIALFU';

Update Rdb$Relation_Fields set Rdb$Description =
'Orgï¿½o de emissï¿½o do RG do vendedor.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='SSPVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Observaï¿½ï¿½es do vendedor.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='OBSVEND';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da empresa da conta do vendedor.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='CODEMPCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Cï¿½digo da filial da conta do vendedor.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='CODFILIALCA';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do abono (utilizado nos relatï¿½rios de comissionamento pela produï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='VLRABONO';

Update Rdb$Relation_Fields set Rdb$Description =
'Valor do desconto (utilizado nos relatï¿½rios de comissionamento pela produï¿½ï¿½o.'
where Rdb$Relation_Name='VDVENDEDOR' and Rdb$Field_Name='VLRDESCONTO';

ALTER TABLE ATATENDIMENTO ADD EMMANUT CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE ATATENDIMENTO ADD BLOQATENDO CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE ATESPECATEND ADD BHESPEC CHAR(1) DEFAULT 'S' NOT NULL;

ALTER TABLE CPCOMPRA ADD OBSPAG VARCHAR(250);

Update Rdb$Relation_Fields set Rdb$Description =
'Campo dinï¿½mico de observaï¿½ï¿½es para repasse ï¿½ tabela de contas a pagar.'
where Rdb$Relation_Name='CPCOMPRA' and Rdb$Field_Name='OBSPAG';

ALTER TABLE CRCHAMADO ADD EMAILSOLICITANTE CHAR(60);

Update Rdb$Relation_Fields set Rdb$Description =
'Endereï¿½o eletrï¿½nico do solicitante.
'
where Rdb$Relation_Name='CRCHAMADO' and Rdb$Field_Name='EMAILSOLICITANTE';

ALTER TABLE FNITRECEBER ADD MULTIBAIXA CHAR(1) DEFAULT 'N';

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o tï¿½tulo serï¿½ baixado atravï¿½s de baixa multipla.'
where Rdb$Relation_Name='FNITRECEBER' and Rdb$Field_Name='MULTIBAIXA';

ALTER TABLE RHTURNO ADD TRABSABTURNO CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE RHTURNO ADD TRABDOMTURNO CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE RHTURNO ADD PERCBHTBSABTURNO DECIMAL(15,5) DEFAULT 0 NOT NULL;

ALTER TABLE RHTURNO ADD PERCBHTBDOMTURNO DECIMAL(15,5) DEFAULT 0 NOT NULL;

ALTER TABLE RHTURNO ADD PERCBHTBFERTURNO DECIMAL(15,5) DEFAULT 0 NOT NULL;

ALTER TABLE SGFERIADO ADD OPTFER CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE SGITPREFERE6 ADD CAMINHOREMESSA VARCHAR(300);

Update Rdb$Relation_Fields set Rdb$Description =
'Pasta padrï¿½o para arquivo de remessa CNAB.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CAMINHOREMESSA';

ALTER TABLE SGITPREFERE6 ADD CAMINHORETORNO VARCHAR(300);

Update Rdb$Relation_Fields set Rdb$Description =
'Pasta padrï¿½o para arquivo de retorno CNAB.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='CAMINHORETORNO';

ALTER TABLE SGITPREFERE6 ADD BACKUPREMESSA VARCHAR(300);

Update Rdb$Relation_Fields set Rdb$Description =
'Pasta padrï¿½o para backup dos arquivos de remessa processados CNAB.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='BACKUPREMESSA';

ALTER TABLE SGITPREFERE6 ADD BACKUPRETORNO VARCHAR(300);

Update Rdb$Relation_Fields set Rdb$Description =
'Pasta padrï¿½o para backup dos arquivos de retorno processados CNAB.'
where Rdb$Relation_Name='SGITPREFERE6' and Rdb$Field_Name='BACKUPRETORNO';

ALTER TABLE SGPREFERE3 ADD CODEMPAO INTEGER;

ALTER TABLE SGPREFERE3 ADD CODFILIALAO SMALLINT;

ALTER TABLE SGPREFERE3 ADD CODATENDO INTEGER;

ALTER TABLE VDCONTRATO ADD ATIVO CHAR(1) DEFAULT 'S' NOT NULL;

Update Rdb$Relation_Fields set Rdb$Description =
'Indica se o contrato estï¿½ ativo.
'
where Rdb$Relation_Name='VDCONTRATO' and Rdb$Field_Name='ATIVO';

/* Create Table... */
CREATE TABLE RHEXPEDIENTE(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODTURNO SMALLINT NOT NULL,
DTEXPED DATE DEFAULT 'today' NOT NULL,
ANOEXPED SMALLINT DEFAULT 0 NOT NULL,
MESEXPED SMALLINT DEFAULT 0 NOT NULL,
HORASEXPED DECIMAL(15,5) DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);


CREATE TABLE RHEXPEDMES(CODEMP INTEGER NOT NULL,
CODFILIAL SMALLINT NOT NULL,
CODTURNO SMALLINT NOT NULL,
ANOEXPED SMALLINT DEFAULT 0 NOT NULL,
MESEXPED SMALLINT DEFAULT 0 NOT NULL,
HORASEXPED NUMERIC(15,5) DEFAULT 0 NOT NULL,
DTINS DATE DEFAULT 'now' NOT NULL,
HINS TIME DEFAULT 'now' NOT NULL,
IDUSUINS CHAR(8) DEFAULT USER NOT NULL,
DTALT DATE DEFAULT 'now',
HALT TIME DEFAULT 'now',
IDUSUALT CHAR(8) DEFAULT USER);



/* Create Procedure... */
SET TERM ^ ;

CREATE PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
 BEGIN EXIT; END
^


/* Create Views... */
/* Alter view (drop/create): ATATENDIMENTOVW01 for view: ATATENDIMENTOVW02 */
/* Alter view (drop): ATATENDIMENTOVW01 for view: ATATENDIMENTOVW02 */
SET TERM ; ^

DROP VIEW ATATENDIMENTOVW01;

/* Alter view (create): ATATENDIMENTOVW01 for view: ATATENDIMENTOVW02 */
/* Create view: ATATENDIMENTOVW01 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW01(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
COEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
QTDITCONTR,
VLRITCONTR,
VLRITCONTREXCED,
DTINICIO,
STATUSATENDO,
RAZCLI,
NOMECLI,
CODCLI,
CODEMPCL,
CODFILIALCL,
CODEMPCH,
CODFILIALCH,
CODCHAMADO,
DESCCHAMADO,
CODEMPTO,
CODFILIALTO,
CODTPATENDO,
DESCTPATENDO,
OBSATENDO,
DATAATENDO,
DATAATENDOFIN,
HORAATENDO,
HORAATENDOFIN,
PGCOMIESPEC,
COBCLIESPEC,
CONTMETAESPEC,
MRELCOBESPEC,
BHESPEC,
TEMPOMINCOBESPEC,
TEMPOMAXCOBESPEC,
PERCCOMIESPEC,
TOTALMIN)
 AS 
select a.codemp, a.codfilial, a.codatendo, 
  a.codempae, a.codfilialae, a.codatend, ate.nomeatend, ate.codempep, codfilialep, matempr,
  a.codempea, a.codfilialea, a.codespec, e.descespec, 
  a.codempct, a.codfilialct, a.codcontr, a.coditcontr, ct.tpcobcontr,
  extract(year from a.dataatendo) anoatendo, extract(month from a.dataatendo) mesatendo, 
  ict.qtditcontr, ict.vlritcontr, ict.vlritcontrexced, ct.dtinicio,
  a.statusatendo, c.razcli, c.nomecli, c.codcli, c.codemp, c.codfilial,
  a.codempch, a.codfilialch, a.codchamado, ch.descchamado,
  a.codempto, a.codfilialto, a.codtpatendo, ta.desctpatendo,
  a.obsatendo, a.dataatendo, a.dataatendofin, a.horaatendo, a.horaatendofin,
  e.pgcomiespec, e.cobcliespec, e.contmetaespec, e.mrelcobespec, e.bhespec,
  e.tempomincobespec, e.tempomaxcobespec, e.perccomiespec, ((a.horaatendofin-a.horaatendo) / 60) TOTALMIN
from atatendente ate, atespecatend e, vdcliente c, attipoatendo ta, atatendimento a
left outer join crchamado ch on 
ch.codemp=a.codempch and ch.codfilial=a.codfilialch and ch.codchamado=a.codchamado 
left outer join vdcontrato ct on
ct.codemp=a.codempct and ct.codfilial=a.codfilialct and ct.codcontr=a.codcontr
left outer join vditcontrato ict on
ict.codemp=a.codempct and ict.codfilial=a.codfilialct and ict.codcontr=a.codcontr and ict.coditcontr=a.coditcontr
where ate.codemp=a.codempae and ate.codfilial=a.codfilialae and ate.codatend=a.codatend and
e.codemp=a.codempea and e.codfilial=a.codfilialea and e.codespec=a.codespec and 
c.codemp=a.codempcl and c.codfilial=a.codfilialcl and c.codcli=a.codcli and
ta.codemp=a.codempto and ta.codfilial=a.codfilialto and ta.codtpatendo=a.codtpatendo
;

/* Create view: ATATENDIMENTOVW02 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW02(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
QTDITCONTR,
VLRITCONTR,
VLRITCONTREXCED,
DTINICIO,
STATUSATENDO,
RAZCLI,
NOMECLI,
CODCLI,
CODEMPCL,
CODFILIALCL,
CODEMPCH,
CODFILIALCH,
CODCHAMADO,
DESCCHAMADO,
CODEMPTO,
CODFILIALTO,
CODTPATENDO,
DESCTPATENDO,
OBSATENDO,
DATAATENDO,
DATAATENDOFIN,
HORAATENDO,
HORAATENDOFIN,
PGCOMIESPEC,
COBCLIESPEC,
CONTMETAESPEC,
MRELCOBESPEC,
BHESPEC,
TEMPOMINCOBESPEC,
TEMPOMAXCOBESPEC,
PERCCOMIESPEC,
TOTALMIN,
TOTALGERAL,
TOTALMETA,
TOTALCOMIS,
TOTALCOBCLI,
TOTALBH)
 AS 
select A.CODEMP, A.CODFILIAL, A.CODATENDO, 
A.CODEMPAE, A.CODFILIALAE, A.CODATEND, A.NOMEATEND, A.CODEMPEP, CODFILIALEP, MATEMPR,
A.COEMPEA, A.CODFILIALEA, A.CODESPEC, A.DESCESPEC, 
 A.CODEMPCT, A.CODFILIALCT, A.CODCONTR, A.CODITCONTR, A.TPCOBCONTR,
 A.ANOATENDO, A.MESATENDO,
 A.QTDITCONTR, A.VLRITCONTR, A.VLRITCONTREXCED, A.DTINICIO,
 A.STATUSATENDO, A.RAZCLI, A.NOMECLI, A.CODCLI,
  A.CODEMPCL, A.CODFILIALCL, A.CODEMPCH, A.CODFILIALCH, A.CODCHAMADO, A.DESCCHAMADO,
  A.CODEMPTO, A.CODFILIALTO, A.CODTPATENDO,
   A.DESCTPATENDO, A.OBSATENDO, A.DATAATENDO, A.DATAATENDOFIN, A.HORAATENDO, A.HORAATENDOFIN,
    A.PGCOMIESPEC, A.COBCLIESPEC, A.CONTMETAESPEC, A.MRELCOBESPEC, A.BHESPEC, A.TEMPOMINCOBESPEC, A.TEMPOMAXCOBESPEC,
     A.PERCCOMIESPEC, A.TOTALMIN, 
  (a.totalmin) / 60  totalgeral, 
(( (case when a.contmetaespec='S' then (case when 
a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalmeta, 
(( (case when a.pgcomiespec='S' then (case when a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalcomis, 
(( (case when a.cobcliespec='S' and a.statusatendo<>'NC' then (case when a.totalmin<a.tempomincobespec 
then a.tempomincobespec else 
(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 
then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) 
)/60 ) totalcobcli,
( (case when a.bhespec='S' then a.totalmin else 0 end)/60 ) totalbh
from atatendimentovw01 a
;

/* Create view: ATATENDIMENTOVW03 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW03(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
NOMEEMPR,
DATAATENDO,
HORAATENDO,
HORAATENDOFIN,
CODEMPTO,
CODFILIALTO,
CODTURNO,
DESCTURNO,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
PERCCOMIESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
HORASEXPED,
TOTALCOMIS,
TOTALGERAL,
TOTALBH)
 AS 
select a.codemp, a.codfilial, a.codatendo, 
    a.codempae, a.codfilialae, a.codatend, a.nomeatend,
     a.codempep, a.codfilialep, a.matempr, e.nomeempr,
     a.dataatendo, a.horaatendo, a.horaatendofin,
    e.codempto, e.codfilialto, e.codturno, t.descturno,
    a.codempea, a.codfilialea, a.codespec, a.descespec, a.perccomiespec,
    a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.tpcobcontr,
    a.anoatendo, a.mesatendo, 
    x.horasexped, a.totalcomis, a.totalgeral,
    ( a.totalbh * ( 1 +  
       ((case when extract(weekday from a.dataatendo)=6 then t.percbhtbsabturno 
          when extract(weekday from a.dataatendo)=0 then t.percbhtbdomturno
          when coalesce(f.trabfer,'N')='S' then t.percbhtbferturno
          else 0 end
       )/100 ) )
    ) totalbh
    from atatendimentovw02 a
    left outer join rhempregado e on
    e.codemp=a.codempep and e.codfilial=a.codfilialep and e.matempr=a.matempr
    left outer join rhturno t on
    t.codemp=e.codempto and t.codfilial=e.codfilialto and t.codturno=e.codturno
    left outer join rhexpedmes x on
    x.codemp=e.codempto and x.codfilial=e.codfilialto and x.codturno=e.codturno and 
    x.anoexped=extract(year from a.dataatendo) and x.mesexped=extract(month from a.dataatendo)
    left outer join sgferiado f on
    f.codemp=a.codemp and f.codfilial=a.codfilial and f.datafer=a.dataatendo
;

/* Create view: ATATENDIMENTOVW04 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW04(
DATAATENDO,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
ANOATENDO,
MESATENDO,
TOTALHORASTRAB)
 AS 
select a.dataatendo, a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.anoatendo, a.mesatendo, sum(totalmin)/60 totalhorastrab
from atatendimentovw01 a
group by a.dataatendo, a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.anoatendo, a.mesatendo
;

/* Create view: ATATENDIMENTOVW05 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW05(
CODEMP,
CODFILIAL,
TIPOVENDA,
CODVENDA,
CODITVENDA,
CODEMPCL,
CODFILIALCL,
CODCLI,
SERIE,
DOCVENDA,
DTEMITVENDA,
DTSAIDAVENDA,
QTDITVENDA,
PRECOITVENDA,
VLRLIQITVENDA,
CODEMPPD,
CODFILIALPD,
CODPROD,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
DTINIAPURA,
DTFINAPURA)
 AS 
select iv.codemp, iv.codfilial, iv.tipovenda, iv.codvenda, iv.coditvenda, 
v.codempcl, v.codfilialcl, v.codcli,
v.serie, v.docvenda, v.dtemitvenda, v.dtsaidavenda, iv.qtditvenda, iv.precoitvenda, iv.vlrliqitvenda,
iv.codemppd, iv.codfilialpd, iv.codprod,
ic.codempct, ic.codfilialct, ic.codcontr, ic.coditcontr, ic.dtiniapura, ic.dtfinapura 
from  vdvenda v, vditvenda iv
left outer join vditvendavditcontr ic
on ic.codemp=iv.codemp and ic.codfilial=iv.codfilial and ic.tipovenda=iv.tipovenda and 
ic.codvenda=iv.codvenda and ic.coditvenda=iv.coditvenda
where v.codemp=iv.codemp and v.codfilial=iv.codfilial and v.tipovenda=iv.tipovenda and 
v.codvenda=iv.codvenda and  
iv.qtditvenda is not null and 
iv.qtditvenda>0
;

/* Create view: ATATENDIMENTOVW06 (ViwData.CreateDependDef) */
CREATE VIEW ATATENDIMENTOVW06(
CODEMP,
CODFILIAL,
CODATENDO,
CODEMPAE,
CODFILIALAE,
CODATEND,
NOMEATEND,
CODEMPEP,
CODFILIALEP,
MATEMPR,
NOMEEMPR,
DATAATENDO,
HORAATENDO,
HORAATENDOFIN,
CODEMPTO,
CODFILIALTO,
CODTURNO,
DESCTURNO,
CODEMPEA,
CODFILIALEA,
CODESPEC,
DESCESPEC,
PERCCOMIESPEC,
CODEMPCT,
CODFILIALCT,
CODCONTR,
CODITCONTR,
TPCOBCONTR,
ANOATENDO,
MESATENDO,
HORASEXPED,
TOTALCOMIS,
TOTALGERAL,
TOTALBH,
TOTALHORASTRAB,
VLRLIQITVENDA)
 AS 
select a.codemp, a.codfilial, a.codatendo, a.codempae, a.codfilialae, a.codatend, 
a.nomeatend, a.codempep, a.codfilialep, a.matempr, a.nomeempr, a.dataatendo, 
a.horaatendo, a.horaatendofin, a.codempto, a.codfilialto, a.codturno, a.descturno,
a.codempea, a.codfilialea, a.codespec, a.descespec, perccomiespec,
a.codempct, a.codfilialct, a.codcontr, a.coditcontr, a.tpcobcontr,
a.anoatendo, a.mesatendo, 
a.horasexped, a.totalcomis, a.totalgeral, a.totalbh,
 ( case when a.tpcobcontr='ES' then ( select s1.totalhorastrab from atatendimentovw04 s1
        where s1.codempct=a.codempct and s1.codfilialct=a.codfilialct and 
        s1.codcontr=a.codcontr and s1.coditcontr=a.coditcontr ) 
   else ( select s1.totalhorastrab from atatendimentovw04 s1
        where s1.codempct=a.codempct and s1.codfilialct=a.codfilialct and 
        s1.codcontr=a.codcontr and s1.coditcontr=a.coditcontr and 
        s1.anoatendo=a.anoatendo and s1.mesatendo=a.mesatendo ) end) totalhorastrab, 
  ( case when a.tpcobcontr='ES' then ( select s2.vlrliqitvenda from atatendimentovw05 s2
        where s2.codempct=a.codempct and s2.codfilialct=a.codfilialct and 
         s2.codcontr=a.codcontr and s2.coditcontr=a.coditcontr)
        else ( select s2.vlrliqitvenda from atatendimentovw05 s2
        where s2.codempct=a.codempct and s2.codfilialct=a.codfilialct and 
         s2.codcontr=a.codcontr and s2.coditcontr=a.coditcontr and
         a.dataatendo between s2.dtiniapura and s2.dtfinapura) end ) vlrliqitvenda
from atatendimentovw03 a
;


/* Create Exception... */
CREATE EXCEPTION ATATENDIMENTOEX01 'Atendimento bloqueado!';


/* Create Primary Key... */
ALTER TABLE RHEXPEDIENTE ADD CONSTRAINT RHEXPEDIENTEPK PRIMARY KEY (DTEXPED,CODTURNO,CODFILIAL,CODEMP);

ALTER TABLE RHEXPEDMES ADD CONSTRAINT RHEXPEDMESPK PRIMARY KEY (MESEXPED,ANOEXPED,CODTURNO,CODFILIAL,CODEMP);

/* Create Foreign Key... */
--CONNECT 'localhost:/opt/firebird/dados/desenv/1.2.4.5/freedom.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

ALTER TABLE RHEXPEDIENTE ADD CONSTRAINT RHEXPEDIENTEFKRHTU FOREIGN KEY (CODTURNO,CODFILIAL,CODEMP) REFERENCES RHTURNO(CODTURNO,CODFILIAL,CODEMP);

ALTER TABLE RHEXPEDMES ADD CONSTRAINT RHEXPEDMESFKRHTURN FOREIGN KEY (CODTURNO,CODFILIAL,CODEMP) REFERENCES RHTURNO(CODTURNO,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKATATTO FOREIGN KEY (CODATENDO,CODFILIALAO,CODEMPAO) REFERENCES ATATENDIMENTO(CODATENDO,CODFILIAL,CODEMP);

ALTER TABLE SGPREFERE3 ADD CONSTRAINT SGPREFERE3FKTKEMAILEC FOREIGN KEY (CODEMAILEC,CODFILIALEC,CODEMPEC) REFERENCES TKEMAIL(CODEMAIL,CODFILIAL,CODEMP);

ALTER TABLE SGUSUARIO ADD CONSTRAINT SGUSUARIOFKTKCONFE FOREIGN KEY (CODCONFEMAIL,CODFILIALCE,CODEMPCE) REFERENCES TKCONFEMAIL(CODCONFEMAIL,CODFILIAL,CODEMP);

SET TERM ^ ;

ALTER TRIGGER CPCOMPRATGAU
 AS Declare variable I integer;
BEGIN I = 0; END
^

/* Alter Procedure... */
/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DTCOMPCOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
NUMCONTA CHAR(10),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
OBSPAG VARCHAR(250))
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG, DTCOMPPAG, STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB,
                          codempca, codfilialca,  numconta, codempcc, codfilialcc, anocc, codcc, codemppn, codfilialpn, codplan, obspag)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra, :DTCOMPCOMPRA, 'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB,
                                 :codempct, :codfilialct, :numconta, :codempcc, :codfilialcc, :anocc, :codcc, :codemppn, :codfilialpn, :codplan, :obspag
                          );
   END
END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retensï¿½o do tributo ISS (descontando do valor final do tï¿½tulo)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

/* Alter (GERAEXPEDIENTESP) */
SET TERM ^ ;

ALTER PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
DECLARE VARIABLE codturno smallint; 
DECLARE VARIABLE dataini date;
DECLARE VARIABLE datafer date;
DECLARE VARIABLE mesexped smallint;
DECLARE VARIABLE anotmp smallint;
DECLARE VARIABLE horasexped decimal(15,5);
DECLARE VARIABLE trabsabturno char(1);
DECLARE VARIABLE trabdomturno char(1);
DECLARE VARIABLE diasemana smallint;
BEGIN
   dataini = null;
   FOR SELECT T.CODTURNO, T.TRABSABTURNO, T.TRABDOMTURNO, 
     ((T.HINIINTTURNO-T.HINITURNO)+(T.HFIMTURNO-T.HFIMINTTURNO))/60/60 HORASEXPED 
     FROM RHTURNO T
     WHERE T.CODEMP=:codempto and T.CODFILIAL=:codfilialto
     INTO :codturno, :trabsabturno, :trabdomturno, :horasexped DO 
   begin
      DELETE FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      DELETE FROM RHEXPEDMES
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      dataini = cast( '01.01.'||cast(:anoexp as char(4)) as date);  
      anotmp = anoexp; 
      WHILE ( :anotmp=:anoexp )  DO 
      begin
         DATAFER=NULL;
         -- Verificação de sábados e domingos
         -- Expressão weekday retorna 0 para domingo e 6 para sábado
         diasemana = extract(weekday from :dataini);
         if ( ( (:trabsabturno='S') or (:diasemana<>6) ) and ( (:trabdomturno='S') or (:diasemana<>0) ) )  then
         begin
            --exception VDVENDAEX01 'Teste'||:dataini;

            SELECT F.DATAFER FROM SGFERIADO F
              WHERE F.CODEMP=:codempfe and F.CODFILIAL=:codfilialfe and F.DATAFER=:dataini and F.TRABFER='S' 
              INTO :DATAFER;
            if ( (:datafer is null) or (:datafer<>:dataini)) then
            begin
               mesexped = extract( month from :dataini);
               INSERT INTO RHEXPEDIENTE 
                        (CODEMP, CODFILIAL, CODTURNO, DTEXPED, ANOEXPED, MESEXPED, HORASEXPED)
                 VALUES (:codempto, :codfilialto, :codturno, :dataini, :anoexp, :mesexped, :horasexped);
            end
         end
         DATAINI=:DATAINI+1;
         anotmp = EXTRACT( YEAR FROM :DATAINI); 
      end
      INSERT INTO RHEXPEDMES( CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, HORASEXPED )
        SELECT CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, SUM(HORASEXPED) HORASEXPED
        FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno
        GROUP BY CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED;
   end
   suspend;
END
^

/* empty dependent procedure body */
/* Clear: CPADICITCOMPRAPEDSP for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: LFBUSCAPREVTRIBORC for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: LFBUSCATRIBCOMPRA for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDADICITEMPDVSP for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDADICITVENDAORCSP for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* empty dependent procedure body */
/* Clear: VDBUSCACUSTOVENDASP for: LFBUSCAFISCALSP */
/* AssignEmptyBody proc */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
 BEGIN EXIT; END
^

/* Alter (LFBUSCAFISCALSP) */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial )
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f.aliqissfisc, f1.percissfilial)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(it.aliqissfisc,f.percissfilial)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca ï¿½ para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do item de classificaï¿½ï¿½o fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do ï¿½tem de classificaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da classificaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do ï¿½tem de classficiaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

Update Rdb$Procedures set Rdb$Description =
'Procedure para busca de informaï¿½ï¿½es fiscais de um ï¿½tem de venda, utilizada para preencher dados da tabela lfitvenda.'
where Rdb$Procedure_Name='LFBUSCAFISCALSP02';

/* Alter (LFBUSCAPREVTRIBORC) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc smallint;
declare variable vlrbasepis numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrprod integer;
declare variable aliqpis numeric(6,2);
declare variable qtd numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(6,2);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrliq numeric(15,5);
declare variable vlrfrete numeric(15,5);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable aliqipi numeric(6,2);
declare variable tpcalcipi char(1);
declare variable vlrbaseipi numeric(15,5);
declare variable aliqcsocial numeric(6,2);
declare variable aliqir numeric(6,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable tipoprod varchar(2);
declare variable aliqiss numeric(6,2);
declare variable aliqicms numeric(6,2);
declare variable tpredicms char(1);
declare variable redicms numeric(15,5);
declare variable baseicms numeric(15,5);
declare variable codtrattrib char(2);
declare variable codsittribpis char(2);
declare variable ufcli char(2);
begin

    -- Inicializando variáveis

    vlripi = 0;
    vlrfrete = 0;

    -- Buscando informações no orçamento (cliente e tipo de movimento)
    select oc.codempcl,oc.codfilialcl,oc.codcli,tm.codemptm,tm.codfilialtm,tm.codtipomovtm,coalesce(cl.siglauf,cl.ufcli)
    from vdorcamento oc, vdcliente cl, eqtipomov tm
    where oc.codemp=:codemp and oc.codfilial=:codfilial and oc.codorc=:codorc and oc.tipoorc=:tipoorc
    and cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli
    and tm.codemp=oc.codemp and tm.codfilial=oc.codfilialtm and tm.codtipomov=oc.codtipomov
    into :codempcl, :codfilialcl, :codcli, :codemptm, :codfilialtm, :codtipomov, :ufcli;

    -- Buscando informações do produto no item de orçamento
    select io.codemppd, io.codfilialpd, io.codprod, io.vlrproditorc, io.qtditorc, io.vlrliqitorc, coalesce(io.vlrfreteitorc,0),
    pd.tipoprod

    from vditorcamento io, eqproduto pd
    where io.codemp=:codemp and io.codfilial=:codfilial and io.codorc=:codorc and io.tipoorc=:tipoorc and io.coditorc=:coditorc
    and pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod
    into :codemppd, :codfilialpd, :codprod, :vlrprod, :qtd, :vlrliq, :vlrfrete, :tipoprod;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempcl, :codfilialcl, :codcli,
    :codemptm, :codfilialtm, :codtipomov, 'VD',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.codsittribpis, cf.aliqpisfisc, cf.vlrpisunidtrib, cf.codsittribcof, cf.aliqcofinsfisc, cf.vlrcofunidtrib,
    cf.vlripiunidtrib, cf.aliqipifisc, cf.codsittribipi, cf.tpcalcipi,
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(cf.aliqissfisc, fi.percissfilial),
    cf.tpredicmsfisc, cf.redfisc, cf.codtrattrib
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :codsittribpis, :aliqpis, :vlrpisunidtrib, :codsittribcof, :aliqcofins, :vlrcofunidtrib,
    :vlripiunidtrib, :aliqipi, :codsittribipi,:tpcalcipi, :aliqcsocial, :aliqir, :aliqiss,
    :tpredicms, :redicms, :codtrattrib;

    -- Definição do IPI
    if(:codsittribipi not in ('52','53','54')) then -- IPI Tributado
    begin
        if(:tpcalcipi='P' and aliqipi is not null and aliqipi > 0) then -- Calculo pela aliquota
        begin
            vlrbaseipi = :vlrliq; -- (Base do IPI = Valor total dos produtos - Implementar situações distintas futuramente)
            vlripi = (vlrbaseipi * aliqipi) / 100;
        end
        else if (vlripiunidtrib is not null and vlripiunidtrib > 0) then -- Calculo pela quantidade
        begin
            vlripi = qtd * vlripiunidtrib;
        end
    end

    -- Definição do PIS

    if(:codsittribpis in ('01','02','99') and aliqpis is not null and aliqpis > 0 ) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprod; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:codsittribpis in ('03') and vlrpisunidtrib is not null and vlrpisunidtrib > 0) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtd * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:codsittribcof in ('01','02','99') and aliqcofins is not null and aliqcofins > 0 ) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprod; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:codsittribpis in ('03') and vlrcofunidtrib is not null and vlrcofunidtrib > 0) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtd * vlrcofunidtrib;
    end

    -- Definição do IR

    if(aliqir is not null and aliqir > 0) then
    begin
        vlrir = ((:vlrliq + :vlripi + :vlrfrete) * aliqir) / 100;
    end

    -- Definição da CSocial

    vlrcsocial = ((:vlrliq + :vlripi + :vlrfrete) * aliqcsocial) / 100;

    -- Definição do ISS se for um serviço
    if (tipoprod = 'S') then
    begin
        if ( aliqiss is not null and aliqiss > 0 ) then
        begin
            vlriss = vlrliq * (aliqiss/100);
        end
    end

    -- Definição do ICMS
    -- Calcular icms quando aliquota maio que zero e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

    if(codtrattrib not in ('40','30','41','50')) then
    begin

        if(aliqicms is null) then
        begin
            select ti.aliqti from lftabicms ti
            where codemp=:codemp and codfilial=:codfilial and ufti=:ufcli
            into aliqicms;
        end

        if (redicms>0) then -- Com redução
        begin
            if(tpredicms='B') then -- Redução na base de cálculo
            begin
                baseicms = vlrliq * ( 1 - (redicms / 100));
                vlricms = baseicms * (aliqicms / 100);
            end
            else -- Redução no valor
            begin

                baseicms = vlrliq;
                vlricms = baseicms * ( aliqicms / 100 );
                vlricms = vlricms * (( 100 - redicms ) / 100);


            end
        end
        else -- Sem redução
        begin

            baseicms = vlrliq;
            vlricms = baseicms * (aliqicms / 100);

        end

    end
  suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para busca de previsï¿½o de tributos incidentes em ï¿½tem de orï¿½amento para calculo da previsï¿½o de lucratividade.'
where Rdb$Procedure_Name='LFBUSCAPREVTRIBORC';

/* Alter (LFBUSCATRIBCOMPRA) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
begin

    -- Inicializando variáveis

    vlrfunrural = 0;

    -- Buscando informações na compra (fornecedor e tipo de movimento)
    select cp.codempfr,cp.codfilialfr,cp.codfor,tm.codemptm,tm.codfilialtm,tm.codtipomovtm
    from cpcompra cp, cpforneced fr, eqtipomov tm
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra
    and fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    and tm.codemp=cp.codemp and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov
    into :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.aliqfunruralfisc
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :aliqfunrural;

    -- Definição do Funrural
    if(:aliqfunrural>0) then -- Retenção do funrural
    begin
        vlrbasefunrural = :vlrliq; -- (Base do Funrural = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrfunrural = (vlrbasefunrural * aliqfunrural) / 100;
    end


    suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Retorna o custo unitï¿½rio do produto'
where Rdb$Procedure_Name='PPCUSTOPRODSP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da emrpesa do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do item de orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequï¿½ncia da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por data de aprovaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por data de fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por cï¿½digo de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprovaï¿½ï¿½o do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';

Update Rdb$Procedures set Rdb$Description =
'Procedure disparada apï¿½s a inserï¿½ï¿½o na tabela PPOP, realiza varredura na estrutura do produto, verificando as anï¿½lises necessï¿½rias
no controle de qualidade e gerando registros referentes ao controle de qualidade na tabela PPOPCQ.'
where Rdb$Procedure_Name='PPGERAOPCQ';

/* Alter (PPITOPSP01) */
SET TERM ^ ;

ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable gerarma char(1);
declare variable crefprod varchar(20);
declare variable icodprodpd integer;
declare variable nqtditop numeric(15,5);
declare variable icodemple integer;
declare variable icodfilialle smallint;
declare variable ccodlote varchar(20);
declare variable icodempfs integer;
declare variable icodfilialfs smallint;
declare variable icodfase integer;
declare variable icodemptr integer;
declare variable icodfilialtr smallint;
declare variable icodtprec integer;
declare variable icodemprp integer;
declare variable icodfilialrp smallint;
declare variable icodrecp integer;
declare variable dtempoof numeric(15,5);
declare variable iseqof smallint;
declare variable iseqppitop integer;
declare variable qtditest numeric(15,5);
declare variable qtdest numeric(15,5);
declare variable qtdprevprodop numeric(15,5);
declare variable qtdfixa char(1);
declare variable estdinamica char(1);
declare variable permiteajusteitop char(1);
declare variable iseqsubprod integer;
declare variable qtditestsp numeric(15,5);
declare variable codempts integer;
declare variable codfilialts smallint;
declare variable codtipomovsp integer;
declare variable tipoexterno char(10);
begin

    --Loop nas fases da estrutura para geração da tabela de fases da OP.
    for select ef.seqef, ef.codempfs, ef.codfilialfs, ef.codfase, ef.codemptr, ef.codfilialtr, ef.codtprec, ef.tempoef, o.estdinamica
    from ppestrufase ef, ppop o, ppestrutura e
    where
        o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
        e.codemp=o.codemppd and e.codfilial=o.codfilialpd and e.codprod=o.codprod and e.seqest=o.seqest and
        ef.codemp=e.codemp and ef.codfilial=e.codfilial and ef.codprod=e.codprod and ef.seqest=E.seqest
    into
        :iseqof, :icodempfs, :icodfilialfs, :icodfase, :icodemptr, :icodfilialtr, :icodtprec, :dtempoof, :estdinamica
    do
    begin
        -- Buscando o primeiro recurso para inserção na fase (provisório)
        select first 1 codemp, codfilial, codrecp from pprecurso r
        where r.codemp=:icodemptr and r.codfilial=:icodfilialtr and r.codtprec=:icodtprec
        into :icodemprp, :icodfilialrp, :icodrecp;

        -- Inserindo na tabela de fase por op
        insert into
            ppopfase (
                codemp, codfilial, codop, seqop, seqof, codempfs, codfilialfs, codfase, codemprp, codfilialrp, codrecp, tempoof,
                codemptr, codfilialtr, codtprec
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqof, :icodempfs, :icodfilialfs,:icodfase, :icodemprp, :icodfilialrp,
                :icodrecp,:dtempoof, :icodemptr, :icodfilialtr, :icodtprec
            );
    end

    -- Se a estrutura não for dinâmica, deve inserir os ítens

    if(coalesce(:estdinamica,'N')='N'  ) then    
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, ie.permiteajusteitest, ie.tipoexterno
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote,:permiteajusteitop,:tipoexterno
        do
        begin
            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqppitop = :iseqppitop + 1;

            if ('S'=qtdfixa) then
            begin
                nqtditop = :qtditest;
            end
            else
            begin
                nqtditop = cast(:qtditest/:qtdest as numeric(15,5) ) * cast(:qtdprevprodop as numeric(15, 5));
            end

            insert into ppitop (
                codemp, codfilial, codop, seqop, seqitop, codemppd, codfilialpd, codprod, refprod,
                codempfs, codfilialfs, codfase, qtditop, gerarma, permiteajusteitop, tipoexterno
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma,
                :permiteajusteitop, :tipoexterno
            );

        end

        -- Inserindo tabela de subprodutos

        iseqsubprod = 0;

        -- Buscando tipo de movimento para subproducao
        select codempts, codfilialts, codtipomovsp from sgprefere5 where codemp=:icodemp and codfilial=:icodfilial
        into :codempts, :codfilialts, :codtipomovsp;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitestsp, ie.codprodpd, ie.refprodpd, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditestsp, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, fs.seqof
            from
                ppitestruturasubprod ie, ppestrutura e, ppop o, ppopfase fs
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
                fs.codemp=ie.codempfs and fs.codfilial=ie.codfilialfs and fs.codfase=ie.codfase and fs.codop=o.codop and fs.seqop=o.seqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqsubprod, :icodprodpd, :crefprod, :icodempfs, :icodfilialfs, :icodfase,
        :qtditestsp,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote, :iseqof
        do
        begin

            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqsubprod = :iseqsubprod + 1;

           insert into ppopsubprod (codemp, codfilial, codop, seqop, seqsubprod, codemppd, codfilialpd, codprod,
                refprod, qtditsp, codempfs, codfilialfs, codfase, codemple, codfilialle, codlote, seqof, codemptm, codfilialtm, codtipomov
           )
           values(
                :icodemp, :icodfilial,:icodop,:iseqop, :iseqsubprod, :icodemppd, :icodfilialpd, :icodprodpd,
                :crefprod, :qtditestsp, :icodempfs, :icodfilialfs, :icodfase, :icodemple, :icodfilialle, :ccodlote, :iseqof, :codempts, :codfilialts, :codtipomovsp
           );



        end


    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da vaga'
where Rdb$Procedure_Name='RHLISTACANDVAGASP' and Rdb$Parameter_Name='ICODVAGA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da funï¿½ï¿½'
where Rdb$Procedure_Name='RHLISTACANDVAGASP' and Rdb$Parameter_Name='ICODFUNC';

/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.6 (28/06/2011)';
    suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para geraï¿½ï¿½o de registros nas tabelas TKCAMPANHACTO e TKSITCAMP.'
where Rdb$Procedure_Name='TKGERACAMPANHACTO';

/* Alter (VDADICITEMPDVSP) */
SET TERM ^ ;

ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod varchar(20);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;

  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,null)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT,null,null,null,null)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(15,5);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(15,5);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(15,5);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(15,5);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,5);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod varchar(20);
declare variable stipoprod varchar(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

    -- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

    -- Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)

    select codempif,codfilialif,codfisc,coditfisc
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',null,null,null,null,null)
    into CODEMPIF,CODFILIALIF,CODFISC,CODITFISC;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,
    :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,:coditfisc)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda (já sabe o coditfisc)

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
            VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
    -- Bloco comentado, pois já buscou o percentual do iss através da procedure.
   --     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
   --     into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Alter (VDBUSCACUSTOVENDASP) */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
declare variable aliqicms numeric(9,2);
declare variable aliqipi numeric(9,2);
declare variable aliqpis numeric(9,2);
declare variable aliqir numeric(9,2);
declare variable aliqcofins numeric(9,2);
declare variable aliqcsocial numeric(9,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codfilialpf smallint;
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable vlrliq numeric(15,5);
declare variable redbase numeric(9,2);
declare variable base numeric(15,5);
declare variable ufcli char(2);
declare variable codtrattrib char(2);
declare variable comisprod numeric(6,2);
declare variable perccomvend numeric(6,2);
declare variable codnat char(4);
declare variable codregra char(4);
begin

    --Verifica se deve buscar custos para venda .
    if(:CODVENDA is not null) then
    begin

        select
            coalesce(vd.vlrprodvenda,0), coalesce(vd.vlrdescvenda,0), coalesce(vd.vlricmsvenda,0),
            coalesce(vd.vlroutrasvenda,0), coalesce(vd.vlrcomisvenda,0), coalesce(vd.vlradicvenda,0),
            coalesce(vd.vlripivenda,0), coalesce(vd.vlrpisvenda,0), coalesce(vd.vlrcofinsvenda,0),
            coalesce(vd.vlrirvenda,0), coalesce(vd.vlrcsocialvenda,0),
            coalesce(fr.vlrfretevd,0), fr.tipofretevd, fr.adicfretevd,
            
            sum(icv.vlrcustopeps * iv.qtditvenda),
            sum(icv.vlrcustompm * iv.qtditvenda),
            sum(icv.vlrprecoultcp * iv.qtditvenda)
            
            from
            vdvenda vd left outer join vdfretevd fr on
            fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda,
            
            vditvenda iv left outer join vditcustovenda icv on
            icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda
            and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda
            
            where vd.codemp=:CODEMPVD and vd.codfilial=:CODFILIALVD and vd.codvenda=:CODVENDA and vd.tipovenda=:TIPOVENDA and
            iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda
            
            group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14

            into vlrprod,vlrdesc,vlricms,vlroutras,vlrcomis,vlradic,vlripi,vlrpis,vlrcofins,vlrir,vlrcsocial,
                 vlrfrete,tipofrete,adicfrete,vlrcustopeps,vlrcustompm,vlrprecoultcp;

            suspend;

    end
    else
    begin
        --Buscando informações sobre o produto do item de orçamento
        select io.codemppd,io.codfilialpd,io.codprod,pd.comisprod,
        coalesce(io.vlrproditorc,0),coalesce(io.vlrdescitorc,0),coalesce(io.vlrliqitorc,0),
        ico.vlrcustopeps * io.qtditorc, ico.vlrcustompm * io.qtditorc, ico.vlrprecoultcp * io.qtditorc,
        cf.codregra
        from lfclfiscal cf, eqproduto pd, vditorcamento io left outer join vditcustoorc ico on
        ico.codemp=io.codemp and ico.codfilial=io.codfilial and ico.codorc = io.codorc and
        ico.tipoorc=io.tipoorc and ico.coditorc=io.coditorc
        where io.codemp=:CODEMPOC and io.codfilial=:CODFILIALOC and io.codorc=:CODORC and io.tipoorc=:TIPOORC and io.coditorc=:CODITORC and
        pd.codemp=io.codemppd and pd.codfilial=io.codfilial and pd.codprod=io.codprod and
        cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc
        into :CODEMPPD,:CODFILIALPD,:CODPROD,:VLRPROD,:VLRDESC,:VLRLIQ,:COMISPROD,:VLRCUSTOPEPS,:VLRCUSTOMPM,:VLRPRECOULTCP,:CODREGRA;

        -- Buscanco informações do orçamento,cliente,vendedor
        select oc.codempcl,oc.codfilialcl,oc.codcli,coalesce(cl.siglauf,cl.ufcli),vd.perccomvend,
        oc.tipofrete,oc.adicfrete
        from vdorcamento oc, vdcliente cl, vdvendedor vd
        where oc.codemp=:CODEMPOC and oc.codfilial=:CODFILIALOC and oc.tipoorc=:TIPOORC and oc.codorc=:CODORC and
        cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli and
        vd.codemp=oc.codempvd and vd.codfilial=oc.codfilialvd and vd.codvend=oc.codvend
        into :CODEMPCL,:CODFILIALCL,:CODCLI,:UFCLI,:PERCCOMVEND,:TIPOFRETE,:ADICFRETE;

        --Buscando o tipo de movimento a ser utilizado na venda futura
        select p1.codempt2,p1.codfilialt2,p1.codtipomov2 from sgprefere1 p1
        where p1.codemp=:CODEMPOC and p1.codfilial=:CODFILIALPF
        into :CODEMPTM,:CODFILIALTM,:CODTIPOMOV;

        -- Buscando natureza de operação da venda futura
        select first 1 nt.codnat from lfnatoper nt, lfitregrafiscal irf
        where nt.codemp=irf.codemp and nt.codfilial=irf.codfilial and nt.codnat=irf.codnat
        and (irf.codtipomov=:CODTIPOMOV or irf.codtipomov is null)
        and (irf.codemp=:CODEMPTM or irf.codemp is null)
        and (irf.codfilial=:CODFILIALTM or irf.codfilial is null)
        and irf.codregra=:CODREGRA and irf.noufitrf='N' and irf.cvitrf='V'
        into :CODNAT;

         -- Buscando informações fiscais
        select codtrattrib,coalesce(aliqfisc,0),coalesce(aliqipifisc,0),coalesce(aliqpis,0),coalesce(aliqcofins,0),coalesce(aliqcsocial,0),
        coalesce(aliqir,0),coalesce(redfisc,0)
        from lfbuscafiscalsp(:CODEMPPD,:CODFILIALPD,:CODPROD,:CODEMPCL,:CODFILIALCL,:CODCLI,:CODEMPTM,:CODFILIALTM,
        :CODTIPOMOV,'VD',:codnat,null,null,null,null)
        into :CODTRATTRIB,:ALIQICMS,:ALIQIPI,:ALIQPIS,:ALIQCOFINS,:ALIQCSOCIAL,:ALIQIR,:REDBASE;

        -- Caso o ICMS não seja definido na classifificação, buscar da tabela de aliquotas.
        if(:ALIQICMS = 0 and :CODTRATTRIB in('00','51','20','70','10') ) then
        begin
            select coalesce(PERCICMS,0) from lfbuscaicmssp (:CODNAT,:UFCLI,:CODEMPOC,:CODFILIALPF)
            into :ALIQICMS;
        end

        -- Buscando custo do ítem

        if(:REDBASE >0) then
        begin
            BASE = :VLRLIQ - ((:VLRLIQ * :REDBASE) /100 );
        end

        BASE = :VLRLIQ;

        vlricms = :BASE * :ALIQICMS / 100;

--      vlroutras =
        vlrcomis = :VLRLIQ * ((:COMISPROD * :PERCCOMVEND) / 10000 );

--      vlradic =
        vlripi = :VLRLIQ * :ALIQIPI / 100;
        vlrpis = :VLRLIQ * :ALIQCOFINS / 100;
        vlrcofins = :VLRLIQ * :ALIQCOFINS / 100;
        vlrir = :VLRLIQ * :ALIQIR /100;
        vlrcsocial = :VLRLIQ * :ALIQCSOCIAL / 100;
--      vlrfrete =

    end

end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para relatï¿½rio de ultimas vendas por cliente/produto.'
where Rdb$Procedure_Name='VDRETULTVDCLIPROD';

/* Restore procedure body: CPADICITCOMPRAPEDSP */
SET TERM ^ ;

ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod varchar(20);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Create Trigger... */
CREATE TRIGGER ATATENDIMENTOTGBU FOR ATATENDIMENTO
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  IF (new.EMMANUT IS NULL) THEN
     new.EMMANUT='N';
  IF (new.BLOQATENDO IS NULL) THEN
     new.BLOQATENDO='N';
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      if ( ( (old.BLOQATENDO IS  NULL) OR (old.BLOQATENDO='N') ) AND (new.BLOQATENDO='S') )  then
      begin
          new.DTALT=cast('now' AS DATE);
          new.IDUSUALT=user;
          new.HALT=cast('now' AS TIME);
      end
      if ( (new.BLOQATENDO='S') and (old.BLOQATENDO='S') ) then
         EXCEPTION ATATENDIMENTOEX01;
  END
end
^

CREATE TRIGGER RHEXPEDIENTETGBU FOR RHEXPEDIENTE
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' as date);
  new.IDUSUALT=USER;
  new.HALT = cast('now' as time);
end
^

CREATE TRIGGER RHEXPEDMESGBU FOR RHEXPEDMES
ACTIVE BEFORE UPDATE POSITION 0 
as
begin
  new.DTALT=cast('now' as date);
  new.IDUSUALT=USER;
  new.HALT = cast('now' as time);
end
^


/* Create Views... */

/* Alter exist trigger... */
ALTER TRIGGER CPCOMPRATGAU
AS
  DECLARE VARIABLE iCodPag INTEGER;
  DECLARE VARIABLE dVLR NUMERIC(15, 5);
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE TIPOMOV CHAR(2);        
  DECLARE VARIABLE ICODITEM SMALLINT;
  DECLARE VARIABLE VLRITEM NUMERIC(15, 5);  
  DECLARE VARIABLE QTDITEM NUMERIC(15, 5);
  DECLARE VARIABLE PERCITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE VLRITFRETE NUMERIC(15, 5);
  DECLARE VARIABLE PERCITADIC NUMERIC(15, 5);
  DECLARE VARIABLE VLRITADIC NUMERIC(15, 5);
  DECLARE VARIABLE SCODFILIALP1 SMALLINT;
  DECLARE VARIABLE GERAPAGEMIS CHAR(1);
  DECLARE VARIABLE DTBASE DATE;
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) )) ) THEN
  BEGIN
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :SCODFILIALP1;
      SELECT P1.GERAPAGEMIS FROM SGPREFERE1 P1 WHERE  P1.CODEMP=new.CODEMP AND
         P1.CODFILIAL=:SCODFILIALP1 INTO :GERAPAGEMIS;
      IF (GERAPAGEMIS IS NULL) THEN
      BEGIN
        GERAPAGEMIS = 'N';
      END
      IF (GERAPAGEMIS='S') THEN
      BEGIN
        DTBASE = new.DTEMITCOMPRA;
      END
      ELSE
      BEGIN
        DTBASE = new.DTENTCOMPRA;
      END
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
      SELECT CODPAG FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA
             AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALPAG INTO iCodPag;
      SELECT TIPOMOV FROM EQTIPOMOV WHERE CODTIPOMOV=new.CODTIPOMOV
             AND CODEMP=new.CODEMPTM AND CODFILIAL=new.CODFILIALTM INTO TIPOMOV;
      IF ((NOT TIPOMOV IN ('DV','TR')) AND (
         (iCodPag IS NULL) OR (
         (new.CODPLANOPAG != old.CODPLANOPAG) OR
         (new.CODFOR != old.CODFOR) OR
         (new.VLRLIQCOMPRA != old.VLRLIQCOMPRA) OR
         (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
         (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
         (new.DOCCOMPRA != old.DOCCOMPRA) OR
         (new.CODBANCO != old.CODBANCO)))) THEN
      BEGIN
        dVLR = new.VLRLIQCOMPRA;
        IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P1','C1'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=:IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,
               new.CODPLANOPAG,new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,
               :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,
               new.FLAG,new.CODEMP,new.CODFILIAL, new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P2','C2')) AND (old.STATUSCOMPRA IN ('P2','C2'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = old.CODFILIAL;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA,new.CODEMPPG,new.CODFILIALPG,new.CODPLANOPAG,
               new.CODEMPFR,new.CODFILIALFR,new.CODFOR,:dVLR,:DTBASE , new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO,new.CODFILIALBO,new.CODBANCO,new.FLAG,new.CODEMP,new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag );
        END
        ELSE IF ((new.STATUSCOMPRA IN ('P3','C3')) AND (old.STATUSCOMPRA IN ('P3','C3'))) THEN
        BEGIN
          DELETE FROM FNPAGAR WHERE CODCOMPRA=old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL = :IFILIALPAG;
          IF (dVlr > 0) THEN
            EXECUTE PROCEDURE FNADICPAGARSP01(new.CODCOMPRA, new.CODEMPPG, new.CODFILIALPG, new.CODPLANOPAG,
               new.CODEMPFR, new.CODFILIALFR, new.CODFOR, :dVLR, :DTBASE, new.DTCOMPCOMPRA, new.DOCCOMPRA,
               new.CODEMPBO, new.CODFILIALBO, new.CODBANCO, new.FLAG, new.CODEMP, new.CODFILIAL,
               new.CODEMPTC, new.CODFILIALTC, new.CODTIPOCOB,
               new.codempct, new.codfilialct, new.numconta, new.codempcc,  new.codfilialcc, new.anocc, new.codcc, new.codemppn, new.codfilialpn, new.codplan, new.obspag);
        END
      END
    /**
     Movimento do estoque
    **/
    /* Avisa os itens que a data de saida foi alterada */
      IF ( (new.DTENTCOMPRA != old.DTENTCOMPRA) OR
           (new.DTEMITCOMPRA != old.DTEMITCOMPRA) OR
           (new.DOCCOMPRA != old.DOCCOMPRA) OR
           (new.CODTIPOMOV != old.CODTIPOMOV) )  THEN
        UPDATE CPITCOMPRA SET CODITCOMPRA=CODITCOMPRA WHERE
             CODCOMPRA = old.CODCOMPRA AND CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;

      IF ( new.codimp is null and (((new.VLRFRETECOMPRA > 0) AND ( NOT new.VLRFRETECOMPRA = old.VLRFRETECOMPRA)) or ((new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)))  ) THEN
      BEGIN
          FOR SELECT IT.CODITCOMPRA, IT.VLRLIQITCOMPRA, IT.QTDITCOMPRA FROM CPITCOMPRA IT
              WHERE IT.CODEMP=new.CODEMP AND IT.CODFILIAL=new.CODFILIAL AND IT.CODCOMPRA=new.CODCOMPRA
              INTO :ICODITEM, :VLRITEM, :QTDITEM
              DO
              BEGIN

                  IF ( (new.vlrfretecompra > 0) AND ( NOT new.vlrfretecompra = old.vlrfretecompra)  ) THEN
                  begin
                      PERCITFRETE = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITFRETE =  (:PERCITFRETE * (new.VLRFRETECOMPRA / 100)) / COALESCE(:QTDITEM, 1);

                      UPDATE CPITCOMPRA CIT
                          SET VLRFRETEITCOMPRA=:VLRITFRETE
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;
                  end
                  IF ( (new.vlradiccompra > 0) AND ( NOT new.vlradiccompra = old.vlradiccompra)  ) THEN
                  begin
                      PERCITADIC = :VLRITEM / (old.VLRLIQCOMPRA / 100);
                      VLRITADIC =  (:PERCITADIC * (new.VLRADICCOMPRA / 100)) / COALESCE(:QTDITEM, 1);
                      UPDATE CPITCOMPRA CIT
                          SET VLRADICITCOMPRA=:VLRITADIC
                            WHERE CIT.CODEMP=new.CODEMP AND CIT.CODFILIAL=new.CODFILIAL
                                AND CIT.CODCOMPRA=new.CODCOMPRA AND CIT.CODITCOMPRA=:ICODITEM;

                  end


              END
        END
          IF ((substr(new.STATUSCOMPRA,1,1)='X') AND (substr(old.STATUSCOMPRA,1,1) IN ('P','C'))) THEN
          BEGIN
              UPDATE CPITCOMPRA SET QTDITCOMPRACANC=QTDITCOMPRA, QTDITCOMPRA=0 WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
              DELETE FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA AND CODEMP=new.CODEMP
              AND CODFILIAL=new.CODFILIAL;
          END

        -- Atualização do status do recebimento da mercadoria quando a nota for emitida.
        if( old.statuscompra!='ET' and new.statuscompra='ET') then
        begin
            
           update eqrecmerc rm set rm.status='NE'
           where rm.codemp=new.codemp and rm.codfilial=new.codfilial
           and rm.ticket in
           (
            select ticket
            from eqitrecmercitcp rm
            where rm.codempcp=new.codemp and rm.codfilialcp=new.codfilial
            and rm.codcompra=new.codcompra
           );

        end

    END
END
^

/* Alter exist trigger... */
ALTER TRIGGER CPCOMPRATGBU
AS
  DECLARE VARIABLE dVlrPagar NUMERIC(15, 5);
  DECLARE VARIABLE sStatusPag CHAR(2);
  DECLARE VARIABLE IFILIALTIPOMOV INTEGER;
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE CSEQNFTIPOMOV CHAR(1);
BEGIN
  IF (new.EMMANUT IS NULL) THEN
     new.EMMANUT='N';
  if (new.BLOQCOMPRA IS NULL) then
     new.BLOQCOMPRA='N';
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
      if ( (old.BLOQCOMPRA IS NOT NULL AND old.BLOQCOMPRA='S') or (new.BLOQCOMPRA='S') ) then
         EXCEPTION CPCOMPRAEX04 'ESTA COMPRA ESTÁ BLOQUEADA!!!';
      new.DTALT=cast('now' AS DATE);
      new.IDUSUALT=USER;
      new.HALT = cast('now' AS TIME);
      if ( (new.DTCOMPCOMPRA IS NULL) OR (new.DTEMITCOMPRA<>old.DTEMITCOMPRA) )  then
         new.DTCOMPCOMPRA = new.DTEMITCOMPRA;
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'EQTIPOMOV') INTO IFILIALTIPOMOV;
      SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
      SELECT T.FISCALTIPOMOV, T.SEQNFTIPOMOV FROM EQTIPOMOV T WHERE T.CODTIPOMOV=new.CODTIPOMOV
             AND T.CODEMP=new.CODEMP AND T.CODFILIAL = :IFILIALTIPOMOV INTO new.FLAG, :CSEQNFTIPOMOV;
      IF (old.IMPNOTACOMPRA IS NULL) THEN
         new.IMPNOTACOMPRA = 'N';
      IF ( (CSEQNFTIPOMOV IS NOT NULL) AND (CSEQNFTIPOMOV='S') ) THEN
      BEGIN
         IF  (new.IMPNOTACOMPRA='N') THEN
         BEGIN
            SELECT DOC FROM LFNOVODOCSP(new.Serie,new.CODEMPSE,new.CODFILIALSE) INTO new.DocCompra;
            new.IMPNOTACOMPRA = 'S';
         END
         ELSE IF (old.DOCCOMPRA != new.DOCCOMPRA) THEN
         BEGIN
            new.DOCCOMPRA = old.DOCCOMPRA;
         END
      END

      IF ( new.FLAG <> 'S') THEN
      BEGIN
        new.FLAG = 'N';
      END
      IF (new.VLRDESCCOMPRA IS NULL) THEN
        new.VLRDESCCOMPRA = 0;
      IF (new.VLRADICCOMPRA IS NULL) THEN
        new.VLRADICCOMPRA = 0;
      IF (new.VLRFRETECOMPRA IS NULL) THEN
        new.VLRFRETECOMPRA = 0;
      IF (old.STATUSCOMPRA = 'P1')  THEN
      BEGIN
        IF (new.STATUSCOMPRA = 'C1') THEN
        BEGIN
          new.STATUSCOMPRA = 'C2';
        END
      END
      ELSE IF ((old.STATUSCOMPRA IN ('P4','C4')) AND (new.STATUSCOMPRA IN ('P4','C4'))) THEN
      BEGIN
        EXCEPTION CPCOMPRAEX03;
      END
      ELSE IF ((old.STATUSCOMPRA IN ('P2','C2')) AND (new.STATUSCOMPRA IN ('P3','C3'))) THEN
      BEGIN
        SELECT STATUSPAG,VLRPARCPAG FROM FNPAGAR WHERE CODCOMPRA=new.CODCOMPRA
             AND CODEMP=new.CODEMP AND CODFILIAL = :IFILIALPAG INTO :sStatusPag,:dVlrPagar;
        IF ((sStatusPag = 'PD') AND (new.VLRLIQCOMPRA != :dVlrPagar)) THEN
          EXCEPTION CPCOMPRAEX04;
      END
       IF ((substr(old.STATUSCOMPRA,1,1) IN ('P','C')) AND (substr(new.STATUSCOMPRA,1,1)='X')) THEN
      BEGIN
           new.vlrdescitcompra = 0;
           new.vlrprodcompra = 0;
           new.vlrbaseicmscompra = 0;
           new.vlricmscompra = 0;
           new.vlrisentascompra = 0;
           new.vlroutrascompra = 0;
           new.vlrbaseipicompra = 0;
           new.vlripicompra = 0;
           new.vlrliqcompra = 0;
           new.vlripicompra = 0;
      END

  END
    -- Atualizando o status do documento fiscal para 02 - Documento cancelado, quando nota for cancelado pelo sistema.
  IF (substr(new.statuscompra,1,1) = 'X' and new.sitdoc!='02') THEN
  begin
    new.sitdoc = '02';
  end

  if(old.chavenfecompra is null and new.chavenfecompra is not null) then
  begin
    new.emmanut = 'N';
  end

END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITPAGARTGBU
AS
  DECLARE VARIABLE IFILIALPAG INTEGER;
  DECLARE VARIABLE ICODFOR INTEGER;
  DECLARE VARIABLE ICODEMPFR INTEGER;
  DECLARE VARIABLE ICODFILIALFR INTEGER;

  DECLARE VARIABLE SCODFILIALLC SMALLINT;
  DECLARE VARIABLE COUNTLANCA INTEGER;
BEGIN

  new.DTALT=cast('now' AS DATE);
  new.IDUSUALT=USER;
  new.HALT = cast('now' AS TIME);

  IF ( new.EMMANUT IS NULL ) THEN
  BEGIN
      new.EMMANUT = 'N';
  END

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNPAGAR') INTO IFILIALPAG;
     IF ((old.STATUSITPAG IN ('PP','PL') )  AND (new.STATUSITPAG='P1') ) THEN
     BEGIN
       DELETE FROM FNLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG
              AND CODEMPPG=new.CODEMP AND CODFILIALPG = new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:IFILIALPAG;

       new.VLRPAGOITPAG = 0;
       new.DTPAGOITPAG = NULL;

     END
     ELSE IF ( (old.STATUSITPAG='P1') AND ( new.STATUSITPAG='CP' ) ) THEN
     BEGIN
        IF ( (new.OBSITPAG IS NULL) OR (rtrim(new.OBSITPAG)='') ) THEN
        BEGIN
           EXCEPTION FNITPAGAREX01;
        END
        new.VLRCANCITPAG = new.VLRAPAGITPAG;
        new.VLRPARCITPAG = 0;
        new.VLRDESCITPAG = 0;
        new.VLRJUROSITPAG = 0;
        new.VLRDEVITPAG = 0;
        new.VLRITPAG = 0;
     END

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :SCODFILIALLC;
     SELECT COUNT (CODLANCA) FROM FNLANCA WHERE CODPAG=new.CODPAG AND NPARCPAG=new.NPARCPAG
              AND CODEMPPG= new.CODEMP AND CODFILIALPG=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL = :SCODFILIALLC INTO :COUNTLANCA;

     new.VLRITPAG = new.VLRPARCITPAG - new.VLRDESCITPAG - new.VLRDEVITPAG + new.VLRJUROSITPAG + new.VLRMULTAITPAG + new.VLRADICITPAG;

     IF ( new.STATUSITPAG = 'P1' ) THEN
     BEGIN
         new.VLRAPAGITPAG = new.VLRITPAG - new.VLRPAGOITPAG;
     END
  
     if( :countlanca <= 1 ) then
     begin
         if (new.VLRAPAGITPAG < 0) then /* se o valor a pagar for menor que zero */
           new.VLRAPAGITPAG = 0;  /* então valor a pagar será zero */
         if ( (new.VLRAPAGITPAG=0) AND (new.VLRITPAG>0) ) then /* se o valor a pagar for igual a zero e existir valor na parcela*/
           new.STATUSITPAG = 'PP';  /* então o status será PP(pagamento completo) */
         else if ( (new.VLRPAGOITPAG>0) AND (new.VLRITPAG>0) ) then /* caso contrário e o valor pago maior que zero e existir valor na parcela*/
           new.STATUSITPAG = 'PL'; /*  então o status será PL(pagamento parcial) */
     end

     IF ((old.STATUSITPAG='P1' AND new.STATUSITPAG in ('PP','PL')) OR
            (old.STATUSITPAG in ('PL') AND new.STATUSITPAG in ('PP','PL') AND new.VLRPAGOITPAG > 0) ) THEN
     BEGIN
       /* faz o lançamento */
       SELECT CODFOR,CODEMPFR,CODFILIALFR FROM FNPAGAR WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL AND CODPAG=new.CODPAG
         INTO ICODFOR,ICODEMPFR,ICODFILIALFR;

       IF(new.multibaixa = 'N')THEN
       BEGIN
           EXECUTE PROCEDURE FNADICLANCASP02(new.CodPag,new.NParcPag,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODFOR,:ICODEMPFR,:ICODFILIALFR,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.AnoCC,new.CodCC,new.CODEMPCC,new.CODFILIALCC, new.DTCOMPITPAG,
                              new.DtPagoItPag,new.DocLancaItPag,new.ObsItPag,new.VlrPagoItPag,new.CODEMP,new.CODFILIAL,new.vlrjurositpag,new.vlrdescitpag);
       END

       /* Altera o valor pago e o valor a pagar */
       new.VLRPAGOITPAG = new.VLRPAGOITPAG + old.VLRPAGOITPAG;
       new.VLRAPAGITPAG = new.VLRITPAG - new.VLRPAGOITPAG;

       if (new.VLRAPAGITPAG < 0) then /* se o valor a pagar for menor que zero */
         new.VLRAPAGITPAG = 0;  /* então valor a pagar será zero */

       if (new.VLRAPAGITPAG=0) then /* se o valor a pagar for igual a zero */
         new.STATUSITPAG = 'PP';  /* então o status será PP(pagamento completo) */
       else if (new.VLRPAGOITPAG>0) then /* caso contrário e o valor pago maior que zero */
         new.STATUSITPAG = 'PL'; /*  então o status será PL(pagamento parcial) */

     END
     ELSE IF (new.VLRPAGOITPAG < 0 )THEN
     BEGIN
         new.VLRPAGOITPAG = new.VLRPAGOITPAG * -1;
         new.VLRAPAGITPAG = new.VLRAPAGITPAG + new.VLRPAGOITPAG;
         new.VLRPAGOITPAG = old.VLRPAGOITPAG - new.VLRPAGOITPAG;
     END
     ELSE IF ((old.STATUSITPAG='PP') AND (new.STATUSITPAG='PP')) THEN
     BEGIN
        EXCEPTION FNPAGAREX01;
     END

   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGAU
AS
  DECLARE VARIABLE ICODCLI INTEGER;
  DECLARE VARIABLE ICODEMPCL INTEGER;
  DECLARE VARIABLE ICODFILIALCL INTEGER;
  DECLARE VARIABLE SCODFILIALCI SMALLINT;
  DECLARE VARIABLE SCODFILIALLC SMALLINT;
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
BEGIN
  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN

     SELECT ESTITRECALTDTVENC FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;
     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='R1') ) OR
           ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC='RR') ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNCOMISSAO') INTO :SCODFILIALCI;
       SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :SCODFILIALLC;
       UPDATE VDCOMISSAO SET STATUSCOMI='C1'
              WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC = new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL=:SCODFILIALCI
              AND STATUSCOMI NOT IN ('CE') AND TIPOCOMI='R';
       DELETE FROM FNLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC= new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL = :SCODFILIALLC;
     END
     ELSE IF ((old.STATUSITREC='R1' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC='RR' AND new.STATUSITREC in ('RP','RL')) OR
              (old.STATUSITREC in ('RP','RL') AND new.STATUSITREC in ('RP','RL') AND new.VLRPAGOITREC > 0) OR
              (old.STATUSITREC = 'RB' AND new.STATUSITREC = 'RP')) THEN
     BEGIN
        SELECT CODCLI,CODEMPCL,CODFILIALCL FROM FNRECEBER WHERE CODEMP=new.CODEMP AND
           CODFILIAL=new.CODFILIAL AND CODREC=new.CODREC
           INTO ICODCLI,ICODEMPCL,ICODFILIALCL;
        IF ((new.VLRPAGOITREC-old.VLRPAGOITREC) > 0) THEN
        BEGIN
       IF(new.multibaixa is null or new.multibaixa = 'N')THEN
           BEGIN
               EXECUTE PROCEDURE FNADICLANCASP01(new.CodRec,new.NParcItRec,new.PDVITREC,new.NumConta,new.CODEMPCA,new.CODFILIALCA,:ICODCLI,:ICODEMPCL,:ICODFILIALCL,
                              new.CodPlan,new.CODEMPPN,new.CODFILIALPN,new.ANOCC,new.CODCC,new.CODEMPCC,new.CODFILIALCC, new.dtCompItRec, new.DtPagoItRec,new.DocLancaItRec,
                              SUBSTRING(new.ObsItRec FROM 1 FOR 50),new.VlrPagoItRec-old.VlrPagoItRec,new.CODEMP,new.CODFILIAL,new.vlrjurositrec,new.vlrdescitrec);
           END
        END
        IF (new.STATUSITREC = 'RP') THEN
        BEGIN
            UPDATE VDCOMISSAO SET STATUSCOMI='C2'
               WHERE CODREC=old.CODREC
               AND CODEMPRC=old.CODEMP
               AND CODFILIALRC=old.CODFILIAL
               AND NPARCITREC=old.NPARCITREC
               AND NOT STATUSCOMI IN ('CP','C2')
               AND CODEMP=old.CODEMP;
        END
     END
     ELSE IF (old.VLRCOMIITREC != new.VLRCOMIITREC) THEN
     BEGIN
        EXECUTE PROCEDURE vdgeracomissaosp(new.CODEMP, new.CODFILIAL, new.CODREC,
           new.NPARCITREC, new.VLRCOMIITREC, new.DTVENCITREC);
     END

     IF ( (new.ALTUSUITREC='S') AND ( (old.VLRITREC!=new.VLRITREC) OR
          (old.VLRDESCITREC!=new.VLRDESCITREC) OR (old.VLRMULTAITREC!=new.VLRMULTAITREC) OR
          (old.VLRJUROSITREC!=new.VLRJUROSITREC) OR (old.VLRPARCITREC!=new.VLRPARCITREC) OR
          (old.VLRPAGOITREC!=new.VLRPAGOITREC) OR (old.VLRAPAGITREC!=new.VLRAPAGITREC) ) ) THEN
        UPDATE FNRECEBER SET VLRREC = VLRREC - old.VLRITREC + new.VLRITREC,
            VLRDESCREC = VLRDESCREC - old.VLRDESCITREC + new.VLRDESCITREC,
            VLRMULTAREC = VLRMULTAREC - old.VLRMULTAITREC + new.VLRMULTAITREC,
            VLRJUROSREC = VLRJUROSREC - old.VLRJUROSITREC + new.VLRJUROSITREC,
            VLRDEVREC = VLRDEVREC - old.VLRDEVITREC + new.VLRDEVITREC,
            VLRPARCREC = VLRPARCREC - old.VLRPARCITREC + new.VLRPARCITREC,
            VLRPAGOREC = VLRPAGOREC - old.VLRPAGOITREC + new.VLRPAGOITREC,
            VLRAPAGREC = VLRAPAGREC - old.VLRAPAGITREC + new.VLRAPAGITREC,
            ALTUSUREC = 'S' WHERE CODREC=new.CODREC
           AND CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL;
   END
END
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGBI
as

    declare variable seqnossonumero int;

begin

    --Setando valor padrão de campos para 0;
    if (new.vlrparcitrec is null) then
        new.vlrparcitrec = 0;

    if (new.vlrdescitrec is null) then
        new.vlrdescitrec = 0;

    if (new.vlrjurositrec is null) then
        new.vlrjurositrec = 0;

    if (new.vlrmultaitrec is null) then
        new.vlrmultaitrec = 0;

    -- Calculando o valor liquido da parcela...
    new.vlritrec = new.vlrparcitrec - new.vlrdescitrec - new.vlrdevitrec + new.vlrjurositrec + new.vlrmultaitrec;
    new.vlrapagitrec = new.vlritrec - new.vlrpagoitrec;

    -- Zerando valores caso fique negativo...
    if (new.vlrapagitrec<0) then
        new.vlrapagitrec = 0;

    if(new.dtprevitrec is null) then
        new.dtprevitrec = new.dtvencitrec;

   -- Buscando informações da conta (necessário quando é alterado o plano de pagamento na venda)
    if(new.numconta is null) then
    begin
    
        select vd.codempca, vd.codfilialca, vd.numconta
        from fnreceber rc, vdvenda vd
        where
        rc.codemp=new.codemp and rc.codfilial=new.codfilial and rc.codrec=new.codrec and
        vd.codemp=rc.codempva and vd.codfilial=rc.codfilialva and vd.codvenda=rc.codvenda and vd.tipovenda=rc.tipovenda
        into new.codempca, new.codfilialca, new.numconta;

    end

    --Buscando sequencial caso informações de banco e carteira já tenham sido informadas...

    if(new.codbanco is not null and new.codcartcob is not null and new.numconta is not null) then
    begin

       select seqnossonumero
       from fngeraseqnossonumero( new.codempbo, new.codfilialbo, new.codbanco, new.codempcb, new.codfilialcb, new.codcartcob, new.codempca, new.codfilialca, new.numconta)
       into :seqnossonumero;

       if (:seqnossonumero is not null and :seqnossonumero >0 ) then
       begin
          new.seqnossonumero = :seqnossonumero;
       end

    end



end
^

/* Alter exist trigger... */
ALTER TRIGGER FNITRECEBERTGBU
AS

  DECLARE VARIABLE SCODFILIALPF SMALLINT;
  DECLARE VARIABLE CCOMISPDUPL CHAR(1);
  DECLARE VARIABLE NVLRPARCREC NUMERIC(15, 5);
  DECLARE VARIABLE NVLRCOMIREC NUMERIC(15, 5);
  DECLARE VARIABLE ESTITRECALTDTVENC CHAR(1);
  DECLARE VARIABLE AUTOBAIXAPARC CHAR(1);
  declare variable seqnossonumero int;
  DECLARE VARIABLE SCODFILIALLC SMALLINT;
  DECLARE VARIABLE COUNTLANCA INTEGER;
BEGIN
  IF (new.EMMANUT IS NULL) THEN   /* Evita flag de manutenÃ§Ã£o nulo */
     new.EMMANUT='N';

  IF ( new.ALTUSUITREC IS NULL ) THEN /* Para nÃ£o permitir flag de usuÃ¡rio nulo */
     new.ALTUSUITREC = 'S';

  IF ( not ( (new.EMMANUT='S') or ( (old.EMMANUT='S') and (old.EMMANUT is not null)) ) ) THEN
  BEGIN
     new.DTALT=cast('now' AS DATE);
     new.IDUSUALT=USER;
     new.HALT = cast('now' AS TIME);

     IF ( (new.DTPAGOITREC is not null) AND (new.DTLIQITREC is null) ) THEN
     BEGIN
        new.DTLIQITREC = new.DTPAGOITREC;
     END

     SELECT ESTITRECALTDTVENC FROM SGPREFERE1 WHERE CODEMP=new.CODEMP AND CODFILIAL=new.CODFILIAL INTO :ESTITRECALTDTVENC;
     SELECT ITPP.AUTOBAIXAPARC FROM FNPARCPAG ITPP, FNRECEBER R
       WHERE ITPP.CODEMP=R.CODFILIALPG AND ITPP.CODFILIAL=R.CODFILIALPG AND ITPP.CODPLANOPAG=R.CODPLANOPAG AND
         ITPP.NROPARCPAG=new.NPARCITREC AND
          R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND R.CODREC=new.CODREC
       INTO :AUTOBAIXAPARC;

     IF  ( ( (old.STATUSITREC IN ('RP','RL') )  AND (new.STATUSITREC IN ('R1', 'RR')) ) OR
           ( (ESTITRECALTDTVENC='S') AND (AUTOBAIXAPARC='S') AND
             (old.DTVENCITREC<>new.DTVENCITREC) ) ) THEN
     BEGIN
       IF(new.STATUSITREC != 'RR')THEN
       BEGIN
        new.STATUSITREC = 'R1';
       END
       new.VLRPAGOITREC = 0;
     END
     ELSE IF ( (old.STATUSITREC='R1') AND ( new.STATUSITREC='CR' ) ) THEN
     BEGIN
        IF ( (new.OBSITREC IS NULL) OR (rtrim(new.OBSITREC)='') ) THEN
        BEGIN
           EXCEPTION FNITRECEBEREX02;
        END
        new.VLRCANCITREC = new.VLRAPAGITREC;
        new.VLRPARCITREC = 0;
        new.VLRDESCITREC = 0;
        new.VLRJUROSITREC = 0;
        new.VLRDEVITREC = 0;
        new.VLRITREC = 0;
     END

     SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'FNLANCA') INTO :SCODFILIALLC;
     SELECT COUNT (CODLANCA) FROM FNLANCA WHERE CODREC=new.CODREC AND NPARCITREC=new.NPARCITREC
              AND CODEMPRC= new.CODEMP AND CODFILIALRC=new.CODFILIAL
              AND CODEMP=new.CODEMP AND CODFILIAL = :SCODFILIALLC INTO :COUNTLANCA;

     new.VLRITREC = new.VLRPARCITREC - new.VLRDESCITREC - new.VLRDEVITREC + new.VLRJUROSITREC + new.VLRMULTAITREC;
     new.VLRAPAGITREC = new.VLRITREC - new.VLRPAGOITREC;
     if (new.VLRAPAGITREC < 0 or new.VLRAPAGITREC is null ) then /* se o valor a pagar for maior que zero */
        new.VLRAPAGITREC = 0;  /* entÃ£o valor a pagar serÃ¡ zero */

     if(:countlanca <= 1)then
     begin
        if ( (new.VLRAPAGITREC=0) AND (new.STATUSITREC<>'CR') ) then /* se o valor a pagar for igual a zero */
            new.STATUSITREC = 'RP';  /* entÃ£o o status serÃ¡ RP(pagamento completo) */
        else if (new.VLRPAGOITREC>0) then /* caso contrÃ¡rio e o valor pago maior que zero */
            new.STATUSITREC = 'RL'; /*  entÃ£o o status serÃ¡ RL(pagamento parcial) */
     end
     /*
       Esta seÃ§Ã£o Ã© destinada e ajustar as comissÃµes conforme os valores de parcelas
       caso o preferÃªncias esteja ajustado para isso.
     */
     IF ( (new.VLRPARCITREC!=old.VLRPARCITREC) AND (new.VLRPARCITREC!=0) ) THEN
     BEGIN
        SELECT ICODFILIAL FROM SGRETFILIAL(new.CODEMP,'SGPREFERE1') INTO :SCODFILIALPF;
        SELECT P1.COMISPDUPL  FROM SGPREFERE1 P1
            WHERE P1.CODEMP=new.CODEMP AND P1.CODFILIAL=:SCODFILIALPF INTO :CCOMISPDUPL;
        IF (CCOMISPDUPL='S') THEN
        BEGIN
           SELECT V.VLRLIQVENDA, R.VLRCOMIREC FROM FNRECEBER R, VDVENDA V
             WHERE R.CODEMP=new.CODEMP AND R.CODFILIAL=new.CODFILIAL AND
                 R.CODREC=new.CODREC AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND
                 V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA INTO :NVLRPARCREC, :NVLRCOMIREC;
           IF (NVLRPARCREC!=0) THEN
             new.VLRCOMIITREC = cast( new.VLRPARCITREC * :NVLRCOMIREC / :NVLRPARCREC as NUMERIC(15, 5) );
        END
     END
     IF ((old.IMPRECIBOITREC='N') AND (new.IMPRECIBOITREC='S') AND (new.RECIBOITREC IS NULL)) THEN
     BEGIN
        SELECT ISEQ FROM SPGERANUM(new.CODEMP,new.CODFILIAL,'RB') INTO new.RECIBOITREC;
     END
     /*AlteraÃ§Ã£o das datas de entrada e saida do estado de 'em cobranÃ§a'*/
     IF (new.RECEMCOB='S') THEN
     BEGIN
       new.DTINIEMCOB=CURRENT_DATE;
       new.DTFIMEMCOB=NULL;
     END
     ELSE IF (new.RECEMCOB='N') THEN
     BEGIN
       new.DTFIMEMCOB=CURRENT_DATE;
     END
     if(new.dtprevitrec is null) then
       new.dtprevitrec = new.dtvencitrec;

    --Buscando sequencial caso informaÃ§Ãµes de banco e carteira tenham sido alteradas...

       if ( (old.codbanco is null or old.codcartcob is null or old.numconta is null )
             or
            (new.codbanco != old.codbanco or new.codcartcob != old.codcartcob or new.numconta != old.numconta)
             and
            (new.codbanco is not null and new.codcartcob is not null and new.numconta is not null ) ) then

       begin

           seqnossonumero = 0;

          select seqnossonumero
          from fngeraseqnossonumero( new.codempbo, new.codfilialbo, new.codbanco, new.codempcb, new.codfilialcb, new.codcartcob, new.codempca, new.codfilialca, new.numconta)
          into :seqnossonumero;

          if (:seqnossonumero is not null and :seqnossonumero >0 ) then
          begin
             new.seqnossonumero = :seqnossonumero;
          end

       end
   end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGAD
AS
BEGIN
  IF ( not ( (old.EMMANUT='S') and (old.EMMANUT IS NOT NULL) ) ) THEN
  BEGIN

    UPDATE VDORCAMENTO SET VLRDESCITORC = VLRDESCITORC -old.VLRDESCITORC,
         VLRPRODORC = VLRPRODORC - old.VLRPRODITORC,
         VLRLIQORC = VLRLIQORC - old.VLRLIQITORC
         WHERE CODORC=old.CODORC AND TIPOORC=old.tipoorc AND
         CODEMP=old.CODEMP AND CODFILIAL=old.CODFILIAL;
  END
END
^

/* Alter exist trigger... */
ALTER TRIGGER VDITORCAMENTOTGAU
as
    declare variable visualizalucr char(1);
    declare variable custopeps numeric(15, 5);
    declare variable custompm numeric(15, 5);
    declare variable custouc numeric(15, 5);
    declare variable vlricmsitorc numeric(15,5);
    declare variable vlripiitorc numeric(15,5);
    declare variable vlrpisitorc numeric(15,5);
    declare variable vlrcofinsitorc numeric(15,5);
    declare variable vlriritorc numeric(15,5);
    declare variable vlrcsocialitorc numeric(15,5);
    declare variable vlrissitorc numeric(15,5);
    declare variable qtdstatusitem integer;
    declare variable qtdstatustot integer;

begin
    if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) ) ) ) then
    begin

    update vdorcamento set
        VLRDESCITORC = VLRDESCITORC - old.VLRDESCITORC + new.VLRDESCITORC,
        VLRPRODORC = VLRPRODORC - old.VLRPRODITORC + new.VLRPRODITORC,
        VLRLIQORC = VLRLIQORC - old.VLRLIQITORC + new.VLRLIQITORC
        where CODORC=new.CODORC and TIPOORC='O' and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL;

    -- Carregamento de preferencias
    select visualizalucr from sgprefere1 where codemp=new.codemp and codfilial = new.codfilial
    into :visualizalucr;

    if( visualizalucr = 'S' ) then
       begin

            -- Busca do custo da ultima compra;
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'U',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custouc;

            -- Busca do custo médio (MPM)
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'M',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custompm;

            -- Busca do custo peps
            select custounit from eqcustoprodsp(new.codemppd, new.codfilialpd, new.codprod,
                new.dtins,'P',new.codempax, new.codfilialax, new.codalmox,'N')
            into :custopeps;

            -- Atualizando registro na tabela de custos de item de orçamento

            update vditcustoorc ico set vlrprecoultcp=:custouc, vlrcustompm=:custompm, vlrcustopeps=:custopeps
                where ico.codemp=new.codemp and ico.codfilial=new.codfilial and ico.codorc=new.codorc
                and ico.tipoorc=new.tipoorc and ico.coditorc=new.coditorc;

            -- Buscando e inserindo previsão de tributos
            select vlricms, vlripi, vlrpis, vlrcofins, vlrir, vlrcsocial, vlriss
            from lfbuscaprevtriborc(new.codemp, new.codfilial, new.codorc, new.tipoorc, new.coditorc)
            into :vlricmsitorc, :vlripiitorc, :vlrpisitorc, :vlrcofinsitorc, :vlriritorc, :vlrcsocialitorc, :vlrissitorc;

            update vdprevtribitorc po set
            po.vlricmsitorc=:vlricmsitorc, po.vlripiitorc=:vlripiitorc, po.vlrpisitorc=:vlrpisitorc,
            po.vlrcofinsitorc=:vlrcofinsitorc, po.vlriritorc=:vlriritorc, po.vlrcsocialitorc=:vlrcsocialitorc,
            po.vlrissitorc=:vlrissitorc
            where po.codemp=new.codemp and po.codfilial=new.codfilial and po.codorc=new.codorc
            and po.tipoorc=new.tipoorc and po.coditorc=new.coditorc;

       end

       -- Se o status foi alterado para Aprovado (OL), deve atualizar o status das ordens de serviço vinculadas
       -- para OA (Orçamento aprovado)
       if(new.statusitorc!=old.statusitorc and new.statusitorc='OL' ) then
       begin
            update eqitrecmercitositorc ios set ios.status='OA'
            where ios.codempoc=new.codemp and ios.codfilialoc=new.codfilial and ios.codorc=new.codorc
            and ios.tipoorc=new.tipoorc and ios.coditorc=new.coditorc;
       end

       -- Contando a quantidade de itens do orçamento com o status do item atual
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL and statusitorc=new.statusitorc
       into :qtdstatusitem;

       -- Contando a quantidade de itens total do orçamento
       select count(*) from vditorcamento
       where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL 
       into :qtdstatustot;

       -- Se todos os itens do orçamento tem o mesmo status, deve ataulizar o status do cabeçalho do orçamento;
       if(:qtdstatusitem > 0 and :qtdstatusitem = :qtdstatustot) then
       begin
            update vdorcamento set statusorc=new.statusitorc
            where CODORC=new.CODORC and TIPOORC=new.tipoorc and CODEMP=new.CODEMP and CODFILIAL=new.CODFILIAL;
       end

    end
end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGBI
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable precocomisprod numeric(15,5);
    declare variable redfisc numeric(9,2);
    declare variable redbasest char(1);
    declare variable ufcli char(2);
    declare variable ufemp char(2);

    begin

        -- Inicializando campos nulos.
        if (new.vlrdescitvenda is null) then new.vlrdescitvenda = 0;
        if (new.vlrbaseicmsitvenda is null) then new.vlrbaseicmsitvenda = 0;
        if (new.vlricmsitvenda is null) then new.vlricmsitvenda = 0;
        if (new.vlrbaseipiitvenda is null) then new.vlrbaseipiitvenda = 0;
        if (new.vlripiitvenda is null) then new.vlripiitvenda = 0;
        if (new.vlrliqitvenda is null) then new.vlrliqitvenda = 0;
        if (new.vlrcomisitvenda is null) then new.vlrcomisitvenda = 0;
        if (new.vlradicitvenda is null) then new.vlradicitvenda = 0;
        if (new.vlrissitvenda is null) then new.vlrissitvenda = 0;
        if (new.vlrfreteitvenda is null) then new.vlrfreteitvenda = 0;
        if (new.tipovenda is null) then new.tipovenda = 'V';
        if (new.vlrbaseicmsstitvenda is null) then new.vlrbaseicmsstitvenda = 0;
        if (new.vlrbaseicmsstretitvenda is null) then new.vlrbaseicmsstretitvenda = 0;
        if (new.vlricmsstitvenda is null) then new.vlricmsstitvenda = 0;
        if (new.vlricmsstretitvenda is null) then new.vlricmsstretitvenda = 0;
        if (new.vlrbasecomisitvenda is null) then new.vlrbasecomisitvenda = 0;

        -- Calculando valor liquido do ítem quando zerado.
        if (new.vlrliqitvenda = 0) then
            new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) +
                new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;

        -- Carregando almoxarifado padrão do produto
        if (new.codalmox is null) then
            select codempax, codfilialax, codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
            into new.codempax, new.codfilialax,new.codalmox;

        -- Acertando o codigo de empresa e filial da mensagem, caso a mensagem seja informada.
        if (not new.codmens is null) then
        begin
            select icodfilial from sgretfilial(new.codemp,'LFMENSAGEM') into new.codfilialme;
            new.codempme = new.codemp;
        end

        -- Buscando referência do produto
        select p.refprod, p.tipoprod, p.precocomisprod from eqproduto p
            where p.codemp=new.codemppd and p.codfilial = new.codfilialpd and p.codprod=new.codprod
        into srefprod, stipoprod, precocomisprod;

        -- Acertando referência quando nula
        if (new.refprod is null) then new.refprod = srefprod;

          
        -- Se o item vendido seja um SERVIÇO (Calculo do ISS);
        if (stipoprod = 'S') then
        begin
            -- Carregando aliquota do ISS
            select first 1 coalesce(c.aliqissfisc, f.percissfilial)
            from sgfilial f
            left outer join lfitclfiscal c on
            c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
            where codemp=new.codemp and codfilial=new.codfilial
            into new.percissitvenda;

            -- Calculando e computando base e valor do ISS;
            if (new.percissitvenda != 0) then
            begin
                new.vlrbaseissitvenda = new.vlrliqitvenda;
                new.vlrissitvenda = new.vlrbaseissitvenda*(new.percissitvenda/100);
            end
        end
        else -- Se o item vendido não for SERVIÇO zera ISS
            new.vlrbaseissitvenda = 0;

        -- Se produto for isento de ICMS
        if (new.tipofisc = 'II') then
        begin
            new.vlrisentasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlroutrasitvenda = 0;
        end
        -- Se produto for de Substituição Tributária
        else if (new.tipofisc = 'FF') then
        begin

           if (new.tipost = 'SI' ) then -- Contribuinte Substituído
                begin
                    new.vlroutrasitvenda = new.vlrliqitvenda;
                    new.vlrbaseicmsitvenda = 0;
                    new.percicmsitvenda = 0;
                    new.vlricmsitvenda = 0;
                    new.vlrisentasitvenda = 0;

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest ;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end

                    if(redfisc>0 and redbasest='S') then
                    begin

                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro

                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
                    else
                    begin

                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end

            end
            else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
            begin

                -- Buscando estado do cliente

                select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                into ufcli;

                -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                select coalesce(ic.aliqfiscintra,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                into percicmsst, redfisc, redbasest ;
                -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                if (percicmsst = 0) then
                begin
                    select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                    into PERCICMSST;
                end

                if(redfisc>0 and redbasest='S') then
                begin
                    -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                    new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );

                end
                else
                begin
                    -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                    new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                end

                new.vlroutrasitvenda = 0;
                new.vlrisentasitvenda = 0;


                new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;

            end
        end
        -- Se produto não for tributado e não isento
        else if (new.tipofisc = 'NN') then
        begin
            new.vlroutrasitvenda = new.vlrliqitvenda;
            new.vlrbaseicmsitvenda = 0;
            new.percicmsitvenda = 0;
            new.vlricmsitvenda = 0;
            new.vlrisentasitvenda = 0;
        end
        -- Se produto for tributado integralmente
        else if (new.tipofisc = 'TT') then
        begin
            new.vlroutrasitvenda = 0;
            new.vlrisentasitvenda = 0;
        end

        -- Gerando preço especial para comissionamento
        if(precocomisprod is not null) then
        begin

            new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

        end

        -- Atualização de totais no cabeçalho da venda

        update vdvenda vd set vd.vlrdescitvenda = vd.vlrdescitvenda + new.vlrdescitvenda,
            vd.vlrprodvenda = vd.vlrprodvenda + new.vlrproditvenda,
            vd.vlrbaseicmsvenda = vd.vlrbaseicmsvenda + new.vlrbaseicmsitvenda,
            vd.vlricmsvenda = vd.VLRICMSVENDA + new.vlricmsitvenda,
            vd.vlrisentasvenda = vd.VLRISENTASVENDA + new.vlrisentasitvenda,
            vd.vlroutrasvenda = vd.VLROUTRASVENDA + new.vlroutrasitvenda,
            vd.vlrbaseipivenda = vd.VLRBASEIPIVENDA + new.vlrbaseipiitvenda,
            vd.vlripivenda = vd.VLRIPIVENDA + new.vlripiitvenda,
            vd.vlrliqvenda = vd.VLRLIQVENDA + new.vlrliqitvenda,
            vd.vlrbaseissvenda = vd.VLRBASEISSVENDA + new.vlrbaseissitvenda,
            vd.vlrissvenda = vd.VLRISSVENDA + new.vlrissitvenda,
            vd.vlrcomisvenda = vd.VLRCOMISVENDA + new.vlrcomisitvenda,
            vd.vlrbaseicmsstvenda = coalesce(vd.vlrbaseicmsstvenda,0) + new.vlrbaseicmsstitvenda,
            vd.vlricmsstvenda = coalesce(vd.vlricmsstvenda,0) + new.vlricmsstitvenda,
            vd.vlrbasecomis = coalesce(vd.vlrbasecomis, 0) + new.vlrbasecomisitvenda
         where vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
            vd.codemp=new.codemp and vd.codfilial=new.codfilial;



end
^

/* Alter exist trigger... */
ALTER TRIGGER VDITVENDATGBU
as
    declare variable srefprod VARchar(20);
    declare variable stipoprod varchar(2);
    declare variable ntmp numeric(15, 5);
    declare variable precocomisprod numeric(15, 5);
    declare variable percicmsst numeric(9,2);
    declare variable percicms numeric(9,2);
    declare variable ufcli char(2);
    declare variable ufemp char(2);
    declare variable redbasest char(1);
    declare variable redfisc numeric(9,2);


    begin
        -- Verifica se tabela está em manutenção // caso não esteja inicia procedimentos
        if ( new.emmanut is null) then
            new.emmanut='N';

        if ( not ( (new.emmanut='S') or ( (old.emmanut='S') and (old.emmanut is not null) )) ) then
        begin

            -- Computando campos de log
            new.dtalt=cast('now' as date);
            new.idusualt=user;
            new.halt = cast('now' as time);

            -- Verifica se o produto foi alterado
            if (new.codprod != old.codprod) then
                exception vditvendaex01;

            -- Verifica se o lote foi alterado
            if (new.codlote != old.codlote) then
                exception vditvendaex02;

            -- Verifica se o código do almoxarifa está nuloo, se estiver carrega o almoxarifado padrão do produto
            if (new.codalmox is null) then
            begin
                select codempax,codfilialax,codalmox from eqproduto
                where codemp=new.codemppd and codfilial=new.codfilialpd and codprod=new.codprod
                into new.codempax, new.codfilialax,new.codalmox;
            end

            -- Verifica se o almoxarifado anterior estava nulo, se não informa que não é pode ser trocado
            if (old.codalmox is not null) then
            begin
                if (old.codalmox != new.codalmox) then
                    exception eqalmox01;
            end

            -- Carrega a referencia e tipo do produto
            select refprod, tipoprod, precocomisprod
            from eqproduto where codprod=new.codprod and codemp=new.codemppd and codfilial=new.codfilialpd
            into srefprod, stipoprod, precocomisprod;

            if (new.refprod is null) then new.refprod = srefprod;

            -- Caso a nota tenha sido cancelada
            if ( (new.cancitvenda is not null) and (new.cancitvenda = 'S') ) then
            begin
                new.qtditvenda = 0;
                new.vlrliqitvenda = 0;
                new.vlrproditvenda = 0;
                new.vlrbaseicmsitvenda = 0;
                new.vlrbaseipiitvenda = 0;
                new.vlrdescitvenda = 0;
            end

            -- Calculando o valor liquido o ítem
            if ( (new.vlrliqitvenda = 0) and ( (new.cancitvenda is null) or (new.cancitvenda!='S') ) ) then
            begin
                new.vlrliqitvenda = (new.qtditvenda * new.precoitvenda) + new.vlradicitvenda + new.vlrfreteitvenda - new.vlrdescitvenda;
            end
            else if (new.vlrdescitvenda > 0 and new.qtditvenda > 0) then
            begin
                ntmp = new.vlrliqitvenda/new.qtditvenda;
                ntmp = ntmp * new.qtditvenda;
                new.vlrdescitvenda = new.vlrdescitvenda + (new.vlrliqitvenda-ntmp);

                -- Recalculando comissão sobre o ítem
                new.vlrcomisitvenda = (new.perccomisitvenda * new.vlrliqitvenda ) / 100;
            end

            -- Ser for um serviço
            if (stipoprod = 'S') then
            begin
                -- Calculo do ISS
                select first 1 coalesce(c.aliqissfisc, f.percissfilial)
                from sgfilial f
                left outer join lfitclfiscal c on
                c.codemp=new.codempif and c.codfilial=new.codfilialif and c.codfisc=new.codfisc and c.coditfisc=new.coditfisc
                where codemp=new.codemp and codfilial=new.codfilial
                into new.percissitvenda;

                if (new.percissitvenda != 0) then
                begin
                    new.vlrbaseissitvenda = new.vlrliqitvenda;
                    new.vlrissitvenda = new.vlrbaseissitvenda * (new.percissitvenda/100);
                end
            end
            else
                new.vlrbaseissitvenda = 0;

            -- ìtem isento
            if (new.tipofisc = 'II') then
            begin
                new.vlrisentasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlroutrasitvenda = 0;
            end
            -- Item com substituição tributária
            else if (new.tipofisc = 'FF') then
            begin
                if (new.tipost = 'SI' ) then -- Contribuinte Substituído
                begin
                    new.vlroutrasitvenda = new.vlrliqitvenda;
                    new.vlrbaseicmsitvenda = 0;
                    new.percicmsitvenda = 0;
                    new.vlricmsitvenda = 0;
                    new.vlrisentasitvenda = 0;

                      -- Buscando estado do cliente e da empresa

                    select coalesce(cl.siglauf,cl.ufcli), fi.siglauf from vdcliente cl, vdvenda vd, sgfilial fi
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    and fi.codemp=new.codemp and fi.codfilial=new.codfilial
                    into ufcli, ufemp;

                    -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0),coalesce(ic.aliqfisc,0), coalesce(ic.redfisc,0), coalesce(ic.redbasest,'S') from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into percicmsst, percicms, redfisc, redbasest ;

                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca anterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0),coalesce(PERCICMS,0) from lfbuscaicmssp (new.codnat,:ufemp,new.codemp,new.codfilial)
                        into PERCICMSST, percicms;
                    end

                    if(redfisc>0 and redbasest='S') then
                    begin

                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro

                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) * (1-(redfisc/100.00)) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
                    else
                    begin

                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstretitvenda = ( (new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda )  / (1+(new.margemvlagritvenda / 100.00))) ;
                        new.vlricmsstretitvenda = ((new.vlrproditvenda - new.vlrdescitvenda + new.vlripiitvenda ) * (:percicmsst / 100.00)) - (new.vlrbaseicmsstretitvenda * (:percicms/100.00));

                    end
    

                end
                else if (new.tipost = 'SU' ) then -- Contribuinte Substituto
                begin

                    -- Buscando estado do cliente
                    select coalesce(cl.siglauf,cl.ufcli) from vdcliente cl, vdvenda vd
                    where vd.codemp=new.codemp and vd.codfilial=new.codfilial and vd.codvenda=new.codvenda and vd.tipovenda=new.tipovenda and
                    cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli
                    into ufcli;

                   -- Buscando aliquota do ICMS ST da tabela de classificação fiscal
                    select coalesce(ic.aliqfiscintra,0), ic.redbasest, ic.redfisc from lfitclfiscal ic
                    where ic.codemp=new.codempif and ic.codfilial=new.codfilialif and ic.codfisc=new.codfisc and ic.coditfisc=new.coditfisc
                    into PERCICMSST,redbasest, redfisc;
                    -- Buscando aliquota do ICMS ST da tabela de alíquotas (caso não encontre na busca naterior)
                    if (percicmsst = 0) then
                    begin
                        select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (new.codnat,:ufcli,new.codemp,new.codfilial)
                        into PERCICMSST;
                    end

                    new.vlroutrasitvenda = 0;
                    new.VLRISENTASITVENDA = 0;

                   if(redfisc>0 and redbasest='S') then
                    begin
                        -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                    end
                    else
                    begin
                        -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                        new.vlrbaseicmsstitvenda = (  (coalesce(new.margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(new.vlrbaseicmsbrutitvenda,0) ) + (coalesce(new.vlripiitvenda,0)) );
                    end

                    new.vlricmsstitvenda = ( (new.vlrbaseicmsstitvenda * :percicmsst) / 100 ) - (new.vlricmsitvenda) ;

                end
            end
            -- Não insidencia
            else if (new.tipofisc = 'NN') then
            begin
                new.vlroutrasitvenda = new.vlrliqitvenda;
                new.vlrbaseicmsitvenda = 0;
                new.percicmsitvenda = 0;
                new.vlricmsitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
            -- Tributado integralmente
            else if (new.tipofisc = 'TT') then
            begin
                new.vlroutrasitvenda = 0;
                new.vlrisentasitvenda = 0;
            end
        end


       -- Atualizando preço especial para comissionamento
       if(precocomisprod is not null) then
       begin

           new.vlrbasecomisitvenda = new.qtditvenda * precocomisprod;

       end



    end
^

/* Alter exist trigger... */
ALTER TRIGGER VDORCAMENTOTGAU
as

    declare variable coditorc integer;
    declare variable percitfrete numeric(15, 5);
    declare variable vlritfrete numeric(15, 5);
    declare variable vlrproditorc numeric(15, 5);

begin

    for select io.coditorc, io.vlrproditorc
    from vditorcamento io
    where io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and io.tipoorc=new.tipoorc
    into :coditorc, :vlrproditorc
    do
    begin

        -- distribuição do frete
        if ( new.vlrfreteorc != old.vlrfreteorc ) then
        begin
           percitfrete = :vlrproditorc / new.vlrprodorc ;
           vlritfrete =  :percitfrete * new.vlrfreteorc ;


           -- atualizando tabela de ítens
            update vditorcamento io set
            io.vlrfreteitorc = :vlritfrete
            where
            io.codemp=new.codemp and io.codfilial=new.codfilial and io.codorc=new.codorc and
            io.coditorc=:coditorc and io.tipoorc=new.tipoorc;
        end

        -- Atualização do STATUS desde que o orçamento não tenha sido faturado parcialmente.
        if(old.statusorc != new.statusorc) then
        begin
            update vditorcamento io set io.statusitorc=new.statusorc
            where io.codemp=new.codemp and io.codfilial=new.codfilial
            and io.codorc=new.codorc and io.tipoorc=new.tipoorc
            and io.statusitorc!=new.statusorc;
        end

    end

end
^

/* Alter exist trigger... */
ALTER TRIGGER VDVENDAORCTGAI
AS

begin
    -- Inserção de registro de movimentação de numero de série,
    -- para faturamento de seviços de conserto (recmerc/Ordens de serviço)

    insert into eqmovserie (
        codemp      , codfilial     , codmovserie   , codemppd      , codfilialpd   , codprod    ,
        numserie    , codempvd      , codfilialvd   , codvenda      , coditvenda    , tipovenda  ,
        dtmovserie  , tipomovserie  , docmovserie
    )
    select
        ir.codemp, ir.codfilial, coalesce((select max(codmovserie) + 1 from eqmovserie where codemp=vd.codemp and codfilial=vd.codfilial),1), ir.codemppd, ir.codfilialpd, ir.codprod,
        ir.numserie, new.codemp, new.codfilial, new.codvenda, new.coditvenda, new.tipovenda, vd.dtsaidavenda, -1, vd.docvenda
    from eqitrecmercitositorc ro, eqitrecmerc ir, vdvenda vd, vditvenda iv
    where
        ro.codemp=new.codempor and ro.codfilial=new.codfilialor and ro.codorc=new.codorc and ro.tipoorc=new.tipoorc and ro.coditorc=new.coditorc and
        ir.codemp=ro.codemp and ir.codfilial=ro.codfilial and ir.ticket=ro.ticket and ir.coditrecmerc=ro.coditrecmerc and
        iv.codemp=new.codemp and iv.codfilial=new.codfilial and iv.codvenda=new.codvenda and iv.tipovenda=new.tipovenda and iv.coditvenda=new.coditvenda and
        vd.codemp=iv.codemp and vd.codfilial=iv.codfilial and vd.tipovenda=iv.tipovenda and vd.codvenda=iv.codvenda and
        ir.numserie is not null;

    -- Atualizando status do item de orçamento indicando que o mesmo foi faturado.
    update vditorcamento io set io.statusitorc='OV'
    where io.codemp=new.codempor and io.codfilial=new.codfilialor and io.codorc=new.codorc and io.tipoorc=new.tipoorc and io.coditorc=new.coditorc;




end
^

/* Alter Procedure... */
/* Alter (CPADICITCOMPRAPEDSP) */
ALTER PROCEDURE CPADICITCOMPRAPEDSP(CODEMP INTEGER,
CODFILIAL SMALLINT,
CODCOMPRA INTEGER,
CODEMPPC INTEGER,
CODFILIALPC SMALLINT,
CODCOMPRAPC INTEGER,
CODITCOMPRAPC INTEGER,
TPAGRUP CHAR(1),
QTDITCOMPRA FLOAT,
VLRDESCITCOMPRA NUMERIC(15,5),
PRECOITCOMPRA NUMERIC(15,5))
 AS
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable coditcompra integer;
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable refprod varchar(20);
declare variable percdescitcompra numeric(15,5);
declare variable vlrliqitcompra numeric(15,5);
declare variable codempax integer;
declare variable codfilialax smallint;
declare variable codalmox integer;
declare variable codlote varchar(20);
declare variable cloteprod char(1);
declare variable uffor char(2);
declare variable codempnt integer;
declare variable codfilialnt smallint;
declare variable codnat char(4);
declare variable tipofisc char(2);
declare variable percred numeric(9,2);
declare variable codtrattrib char(2);
declare variable origfisc char(1);
declare variable codmens smallint;
declare variable percicmsitcompra numeric(9,2);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable tipost char(2);
declare variable margemvlagr numeric(15,5);
declare variable percipiitcompra numeric(9,2);
declare variable percicmsst numeric(9,2);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
declare variable vlrproditcompra numeric(15,5);
declare variable vlrbaseipiitcompra numeric(15,5);
declare variable vlripiitcompra numeric(15,5);
declare variable vlrbaseicmsitcompra numeric(15,5);
declare variable vlricmsitcompra numeric(15,5);
declare variable vlrbaseicmsbrutitcompra numeric(15,5);
declare variable vlrisentasitcompra numeric(15,5);
declare variable vlroutrasitcompra numeric(15,5);
declare variable vlrbaseicmsstitcompra numeric(15,5);
declare variable vlricmsstitcompra numeric(15,5);
begin

-- Inicialização de variaveis

    select icodfilial from sgretfilial(:codemp, 'LFNATOPER') into :codfilialnt;

    select cp.codemptm, cp.codfilialtm, cp.codtipomov, cp.codempfr,  cp.codfilialfr, cp.codfor, coalesce(fr.siglauf, fr.uffor) uffor
    from cpcompra cp, cpforneced fr
    where cp.codcompra=:codcompra and cp.codfilial=:codfilial and cp.codemp=:codemp and
    fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    into :codemptm, :codfilialtm, :codtipomov, :codempfr, :codfilialfr, :codfor, :uffor ;

-- Busca sequencia de numeração do ítem de compra

    select coalesce(max(coditcompra),0)+1 from cpitcompra where codcompra=:codcompra and codfilial=:codfilial and codemp=:codemp
    into :coditcompra;

-- Informações do item do pedido de compra

    select it.codemppd, it.codfilialpd, it.codprod, it.percdescitcompra, it.vlrliqitcompra, it.vlrproditcompra, it.refprod,
    it.codempax, it.codfilialax, it.codalmox, it.codlote, pd.cloteprod
    from cpitcompra it, eqproduto pd
    where it.coditcompra=:coditcomprapc and it.codcompra=:codcomprapc and it.codfilial=:codfilialpc and it.codemp=:codemppc
    and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into :codemppd, :codfilialpd, :codprod, :percdescitcompra, :vlrliqitcompra, :vlrproditcompra, :refprod,
    :codempax, :codfilialax, :codalmox, :codlote, :cloteprod;

-- Buscando a natureza de operação para o ítem de compra

    select codnat from lfbuscanatsp(:codfilial, :codemp, :codfilialpd, :codprod, null, null, null, :codempfr, :codfilialfr, :codfor,
    :codfilialtm, :codtipomov, null)
    into :codnat;
    
    if (:codnat is null) then
    begin
        exception cpitcompraex04 ' produto:' || :refprod; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de compra

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,margemvlagr,
    aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr,:codfilialfr, :codfor,
    :codemptm, :codfilialtm, :codtipomov, 'CP', :codnat,null,null,null,null)
    into :tipofisc, :percred, :codtrattrib, :origfisc, :codmens, :percicmsitcompra, :codempif, :codfilialif, :codfisc, :coditfisc, :tipost,
    :margemvlagr, :percipiitcompra, :percicmsst, :tpredicms, :redbaseicmsst;
    
-- Inicializando valores

    vlrproditcompra = :precoitcompra * :qtditcompra;
    vlrliqitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseipiitcompra = 0;
    vlrbaseicmsitcompra = 0;
    vlricmsitcompra = 0;
    vlripiitcompra = 0;

    if (:percicmsitcompra = 0 or :percicmsitcompra is null) then
    begin
        select coalesce(percicms, 0) from lfbuscaicmssp (:codnat, :uffor,:codemp, :codfilial) into :percicmsst;
    end

    if (:percred is null) then
    begin
        percred = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            vlrbaseicmsitcompra = (:vlrproditcompra - :vlrdescitcompra) - ( (:vlrproditcompra - :vlrdescitcompra) * ( :percred / 100 ) );
        end
        else if(:tpredicms='V') then
        begin
            vlricmsitcompra = (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 )) -  ( (:vlrbaseicmsitcompra * ( :percicmsitcompra / 100 ) * ( :percred / 100 ) )) ;
        end
    end
    else
    begin
        vlrbaseicmsitcompra = :vlrproditcompra - :vlrdescitcompra;
        vlricmsitcompra = :vlrbaseicmsitcompra * ( :percicmsitcompra / 100 );
    end

    vlrbaseipiitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlrbaseicmsbrutitcompra = :vlrproditcompra - :vlrdescitcompra;
    vlripiitcompra = :vlrbaseipiitcompra * ( :percipiitcompra / 100 );

-- **** Calculo dos tributos ***

    -- Se produto for isento de ICMS
    if (:tipofisc = 'II') then
    begin
        vlrisentasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlroutrasitcompra = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:tipofisc = 'FF') then
    begin
        if (:tipost = 'SI' ) then -- Contribuinte Substituído
        begin
            vlroutrasitcompra = :vlrliqitcompra;
            vlrbaseicmsitcompra = 0;
            percicmsitcompra = 0;
            vlricmsitcompra = 0;
            vlrisentasitcompra = 0;
        end
        else if (:tipost = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:percicmsst is null) or (:percicmsst=0) ) then
            begin
                select coalesce(percicmsintra,0) from lfbuscaicmssp (:codnat,:uffor,:codemp,:codfilial)
                into :percicmsst;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitcompra = (  (coalesce(:margemvlagr,0) + 100) / 100 )  * (  (coalesce(:vlrbaseicmsbrutitcompra,0) ) + (coalesce(:vlripiitcompra,0)) );
            end

            vlroutrasitcompra = 0;
            vlrisentasitcompra = 0;
            vlricmsstitcompra = ( (:vlrbaseicmsstitcompra * :percicmsst) / 100 ) - (:vlricmsitcompra) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:tipofisc = 'NN') then
    begin
        vlroutrasitcompra = :vlrliqitcompra;
        vlrbaseicmsitcompra = 0;
        percicmsitcompra = 0;
        vlricmsitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Se produto for tributado integralmente

    else if (:tipofisc = 'TT') then
    begin
        vlroutrasitcompra = 0;
        vlrisentasitcompra = 0;
    end

    -- Inserindo dados na tabela de ítens de compra

    if ( :tpagrup <> 'F' ) then
    begin

        insert into cpitcompra (codemp, codfilial, codcompra, coditcompra, codempnt, codfilialnt, codnat, codemppd,
        codfilialpd, codprod, codemple, codfilialle, codlote, qtditcompra, precoitcompra, percdescitcompra,vlrdescitcompra,
        percicmsitcompra,vlrbaseicmsitcompra,vlricmsitcompra,percipiitcompra,vlrbaseipiitcompra,vlripiitcompra,vlrliqitcompra,
        vlrproditcompra,refprod, codempax,codfilialax,codalmox, codempif,codfilialif,codfisc,coditfisc,vlrisentasitcompra, vlroutrasitcompra)
        values (:codemp, :codfilial, :codcompra, :coditcompra, :codemp, :codfilialnt, :codnat, :codemp,:codfilialpd, :codprod,
        :codemp, :codfilialpd, :codlote, :qtditcompra, :precoitcompra,:percdescitcompra,:vlrdescitcompra,:percicmsitcompra,:vlrbaseicmsitcompra,
        :vlricmsitcompra, :percipiitcompra, :vlrbaseipiitcompra, :vlripiitcompra, :vlrliqitcompra,:vlrproditcompra,:refprod,
        :codempax, :codfilialax, :codalmox, :codempif, :codfilialif, :codfisc, :coditfisc, :vlrisentasitcompra,:vlroutrasitcompra
        );
    end

-- Atualizando informações de vínculo

    execute procedure cpupcomprapedsp(:codemp, :codfilial,:codcompra, :coditcompra, :codemppc, :codfilialpc, :codcomprapc, :coditcomprapc);

end
^

/* Alter (FNADICPAGARSP01) */
ALTER PROCEDURE FNADICPAGARSP01(CODCOMPRA INTEGER,
ICODEMPPG INTEGER,
ICODFILIALPG SMALLINT,
CODPLANOPAG INTEGER,
ICODEMPFR INTEGER,
ICODFILIALFR SMALLINT,
CODFOR INTEGER,
VLRLIQCOMPRA NUMERIC(18,2),
DTSAIDACOMPRA DATE,
DTCOMPCOMPRA DATE,
DOCCOMPRA INTEGER,
ICODEMPBO INTEGER,
ICODFILIALBO SMALLINT,
CODBANCO CHAR(3),
FLAG CHAR(1),
ICODEMP INTEGER,
ICODFILIAL SMALLINT,
CODEMPTC INTEGER,
CODFILIALTC SMALLINT,
CODTIPOCOB INTEGER,
CODEMPCT INTEGER,
CODFILIALCT SMALLINT,
NUMCONTA CHAR(10),
CODEMPCC INTEGER,
CODFILIALCC SMALLINT,
ANOCC SMALLINT,
CODCC CHAR(19),
CODEMPPN INTEGER,
CODFILIALPN SMALLINT,
CODPLAN CHAR(13),
OBSPAG VARCHAR(250))
 AS
declare variable icodpagar integer;
declare variable ifilialpagar integer;
declare variable numparcs integer;
BEGIN
  SELECT ICODFILIAL FROM SGRETFILIAL(:ICODEMP,'FNPAGAR') INTO IFILIALPAGAR;
  SELECT ISEQ FROM SPGERANUM(:ICODEMP,:IFILIALPAGAR,'PA') INTO ICODPAGAR;
  SELECT COALESCE(PARCPLANOPAG,0) FROM FNPLANOPAG WHERE CODEMP=:icodemppg AND CODFILIAL=:icodfilialpg AND codplanopag=:codplanopag
  INTO NUMPARCS;

  IF(numparcs>0) THEN
  BEGIN
      INSERT INTO FNPAGAR (CODEMP,CODFILIAL,CODPAG,CODPLANOPAG,CODEMPPG,CODFILIALPG,CODFOR,CODEMPFR,
                          CODFILIALFR,CODCOMPRA,CODEMPCP,CODFILIALCP,VLRPAG,
                          VLRDESCPAG,VLRMULTAPAG,VLRJUROSPAG,VLRPARCPAG,VLRPAGOPAG,
                          VLRAPAGPAG,DATAPAG, DTCOMPPAG, STATUSPAG,DOCPAG,CODBANCO,CODEMPBO,CODFILIALBO,FLAG,
                          CODEMPTC, CODFILIALTC, CODTIPOCOB,
                          codempca, codfilialca,  numconta, codempcc, codfilialcc, anocc, codcc, codemppn, codfilialpn, codplan, obspag)
                          VALUES (
                                 :ICODEMP,:IFILIALPAGAR,:ICODPAGAR,:CodPlanoPag,:ICODEMPPG,:ICODFILIALPG,:CodFor,:ICODEMPFR,
                                 :ICODFILIALFR,:CodCompra,:ICODEMP,:ICODFILIAL,:VlrLiqCompra,
                                 0,0,0,:VlrLiqCompra,0,:VlrLiqCompra,:dtSaidaCompra, :DTCOMPCOMPRA, 'P1',:DocCompra,
                                 :CodBanco,:ICODEMPBO,:ICODFILIALBO,:FLAG,
                                 :CODEMPTC, :CODFILIALTC, :CODTIPOCOB,
                                 :codempct, :codfilialct, :numconta, :codempcc, :codfilialcc, :anocc, :codcc, :codemppn, :codfilialpn, :codplan, :obspag
                          );
   END
END
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retensï¿½o do tributo ISS (descontando do valor final do tï¿½tulo)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

/* Alter (GERAEXPEDIENTESP) */
SET TERM ^ ;

ALTER PROCEDURE GERAEXPEDIENTESP(CODEMPFE INTEGER,
CODFILIALFE SMALLINT,
CODEMPTO INTEGER,
CODFILIALTO SMALLINT,
ANOEXP SMALLINT)
 AS
DECLARE VARIABLE codturno smallint; 
DECLARE VARIABLE dataini date;
DECLARE VARIABLE datafer date;
DECLARE VARIABLE mesexped smallint;
DECLARE VARIABLE anotmp smallint;
DECLARE VARIABLE horasexped decimal(15,5);
DECLARE VARIABLE trabsabturno char(1);
DECLARE VARIABLE trabdomturno char(1);
DECLARE VARIABLE diasemana smallint;
BEGIN
   dataini = null;
   FOR SELECT T.CODTURNO, T.TRABSABTURNO, T.TRABDOMTURNO, 
     ((T.HINIINTTURNO-T.HINITURNO)+(T.HFIMTURNO-T.HFIMINTTURNO))/60/60 HORASEXPED 
     FROM RHTURNO T
     WHERE T.CODEMP=:codempto and T.CODFILIAL=:codfilialto
     INTO :codturno, :trabsabturno, :trabdomturno, :horasexped DO 
   begin
      DELETE FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      DELETE FROM RHEXPEDMES
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno and ANOEXPED=:anoexp;
      dataini = cast( '01.01.'||cast(:anoexp as char(4)) as date);  
      anotmp = anoexp; 
      WHILE ( :anotmp=:anoexp )  DO 
      begin
         DATAFER=NULL;
         -- Verificação de sábados e domingos
         -- Expressão weekday retorna 0 para domingo e 6 para sábado
         diasemana = extract(weekday from :dataini);
         if ( ( (:trabsabturno='S') or (:diasemana<>6) ) and ( (:trabdomturno='S') or (:diasemana<>0) ) )  then
         begin
            --exception VDVENDAEX01 'Teste'||:dataini;

            SELECT F.DATAFER FROM SGFERIADO F
              WHERE F.CODEMP=:codempfe and F.CODFILIAL=:codfilialfe and F.DATAFER=:dataini and F.TRABFER='S' 
              INTO :DATAFER;
            if ( (:datafer is null) or (:datafer<>:dataini)) then
            begin
               mesexped = extract( month from :dataini);
               INSERT INTO RHEXPEDIENTE 
                        (CODEMP, CODFILIAL, CODTURNO, DTEXPED, ANOEXPED, MESEXPED, HORASEXPED)
                 VALUES (:codempto, :codfilialto, :codturno, :dataini, :anoexp, :mesexped, :horasexped);
            end
         end
         DATAINI=:DATAINI+1;
         anotmp = EXTRACT( YEAR FROM :DATAINI); 
      end
      INSERT INTO RHEXPEDMES( CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, HORASEXPED )
        SELECT CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED, SUM(HORASEXPED) HORASEXPED
        FROM RHEXPEDIENTE 
        WHERE CODEMP=:codempto and CODFILIAL=:codfilialto and CODTURNO=:codturno
        GROUP BY CODEMP, CODFILIAL, CODTURNO, ANOEXPED, MESEXPED;
   end
   suspend;
END
^

/* Alter (LFBUSCAFISCALSP) */
ALTER PROCEDURE LFBUSCAFISCALSP(CODEMP INTEGER,
CODFILIAL INTEGER,
CODPROD INTEGER,
CODEMPCF INTEGER,
CODFILIALCF INTEGER,
CODCF INTEGER,
CODEMPTM INTEGER,
CODFILIALTM SMALLINT,
CODTIPOMOV INTEGER,
TIPOBUSCA CHAR(2),
CODNAT CHAR(4),
CODEMPIFP INTEGER,
CODFILIALIFP SMALLINT,
CODFISCP CHAR(13),
CODITFISCP INTEGER)
 RETURNS(ORIGFISC CHAR(1),
CODTRATTRIB CHAR(2),
REDFISC NUMERIC(9,2),
TIPOFISC CHAR(2),
CODMENS INTEGER,
ALIQFISC NUMERIC(9,2),
ALIQIPIFISC NUMERIC(9,2),
TPREDICMSFISC CHAR(1),
TIPOST CHAR(2),
MARGEMVLAGR NUMERIC(15,2),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC INTEGER,
ALIQFISCINTRA NUMERIC(9,2),
ALIQPIS NUMERIC(9,2),
ALIQCOFINS NUMERIC(9,2),
ALIQCSOCIAL NUMERIC(9,2),
ALIQIR NUMERIC(9,2),
REDBASEST CHAR(1),
ALIQISS NUMERIC(6,2))
 AS
declare variable noestcf char(1);
declare variable codfisccf integer;
declare variable codempfccf integer;
declare variable codfilialfccf integer;
declare variable ufcf char(2);
declare variable uffilial char(2);
begin

    -- Se for uma busca para venda
    if(:tipobusca='VD') then
    begin
    select coalesce(siglauf,ufcli),codempfc,codfilialfc,codfisccli
        from vdcliente
        where codemp=:codempcf and codfilial=:codfilialcf and codcli=:codcf
        into ufcf,codempfccf,codfilialfccf,codfisccf;
        end
    -- Se for uma busca para compra
    else if(:tipobusca='CP') then
    begin
        select coalesce(siglauf,uffor),codempff,codfilialff,codfiscfor
        from cpforneced fr
        where codemp=:codempcf and codfilial=:codfilialcf and codfor=:codcf
            into ufcf,codempfccf,codfilialfccf,codfisccf;
    end

    --Busca o estado da Filial
    select siglauf from sgfilial where codemp=:codemp and codfilial=:codfilial
    into uffilial;

    --Compara estado da filial com estado do cliente ou fornecedor
    if(uffilial=ufcf) then
    begin
        NOESTCF='S';
    end
    else
    begin
        NOESTCF='N';
    end

    -- Se o ítem de classificação fiscal não foi informado deve realizar as buscas para descobrir...
    if(coditfiscp is null) then
    begin

       /*Primeira busca, mais específica para o tipo fiscal do cliente ou fornecedor e estado de destino da mercadoria*/

        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial )
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and  ((it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or (it.codfisccli is null)))
                and it.siglauf=:ufcf and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, :aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Segunda busca, mais específica para o tipo fiscal do cliente*/
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
                   it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
                   it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
                   ,coalesce(it.aliqissfisc, f.percissfilial)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and it.codemp=p.codempfc
                and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and it.noufitfisc=:noestcf
                and (((it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or (it.codtipomov is null))
                and   (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf))
                and it.tipousoitfisc=:tipobusca
                and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin

            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

        /* Terceira busca, mais genérica, pega exceções sem tipo de movimento e tipo fiscal do cliente,
          só é executada quando a SELECT acima não retornar nenhum valor. */
        for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,it.tpredicmsfisc,
            it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,it.aliqfiscintra,it.aliqpisfisc,
            it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest,coalesce(it.aliqissfisc, f.percissfilial)
            from lfitclfiscal it, eqproduto p, sgfilial f
            where
                p.codprod=:codprod and p.codfilial=:codfilial and p.codemp=:codemp and
                it.codemp=p.codempfc and it.codfilial=p.codfilialfc and it.codfisc=p.codfisc and
                it.noufitfisc=:noestcf and
                ( (it.codtipomov=:codtipomov and it.codemptm=:codemptm and it.codfilial=:codfilialtm) or
                  (it.codfisccli=:codfisccf and it.codempfc=:codempfccf and it.codfilialfc=:codfilialfccf) or
                  (it.codfisccli is null and it.codtipomov is null) ) and it.tipousoitfisc=:tipobusca
                   and f.codemp=:codemp and f.codfilial=:codfilial
            order by it.coditfisc
            into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
                codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

        suspend;
            exit;
        end

        /*Quarta busca, mais genérica, sem filtros por tipo de movimento e tipo fiscal.
        só é executada quando as SELECTS acima não retornarem nenhum valor.*/
        select f.origfisc,f.codtrattrib,f.redfisc,f.aliqfisc,f.tipofisc, f.aliqipifisc, f.tpredicmsfisc, f.tipost, f.margemvlagr,
            f.codemp,f.codfilial,f.codfisc,f.coditfisc,f.aliqfiscintra,f.aliqpisfisc,f.aliqcofinsfisc,f.aliqcsocialfisc,f.aliqirfisc,f.redbasest
            ,coalesce(f.aliqissfisc, f1.percissfilial)
        from lfitclfiscal f, eqproduto p, sgfilial f1
        where
            p.codprod=:CODPROD and p.codfilial=:CODFILIAL and p.codemp=:CODEMP and
            f.codemp=p.codempfc and f.codfilial=p.codfilialfc and f.codfisc=p.codfisc and
            f.geralfisc='S' and f.tipousoitfisc=:tipobusca
            and f1.codemp=:codemp and f1.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir,redbasest,aliqiss;
    
        -- Definição do ICMS
        -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

        if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
        begin
            select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
            into aliqfisc;
        end


        suspend;
    end
    -- Se o ítem de classificação fiscal foi informado
    else if(coditfiscp is not null) then
    begin

       for select it.origfisc,it.codtrattrib,it.redfisc,it.aliqfisc,it.tipofisc,it.codmens,it.aliqipifisc,
            it.tpredicmsfisc,it.tipost,it.margemvlagr,it.codemp,it.codfilial,it.codfisc,it.coditfisc,
            it.aliqfiscintra,it.aliqpisfisc,it.aliqcofinsfisc,it.aliqcsocialfisc,it.aliqirfisc, it.redbasest
            ,coalesce(it.aliqissfisc,f.percissfilial)
            from lfitclfiscal it, sgfilial f
            where it.codemp=:codempifp and it.codfilial=:codfilialifp and it.codfisc=:codfiscp and it.coditfisc=:coditfiscp
             and f.codemp=:codemp and f.codfilial=:codfilial
        into origfisc,codtrattrib,redfisc,aliqfisc,tipofisc,codmens,aliqipifisc,tpredicmsfisc,tipost,margemvlagr,
            codempif,codfilialif,codfisc,coditfisc,aliqfiscintra,aliqpis,aliqcofins,aliqcsocial,aliqir, redbasest, aliqiss
        do
        begin
            -- Definição do ICMS
            -- caso não tenha encontrato aliquota de icms e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)
    
            if(codtrattrib not in ('40','30','41','50') and (aliqfisc is null or aliqfisc=0 ) ) then
            begin
                select coalesce(percicms,0) from lfbuscaicmssp (:codnat,:ufcf,:codemp,:codfilial)
                into aliqfisc;
            end

            suspend;
            exit;
        end

    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do cliente ou fornecedor'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODCF';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se a busca ï¿½ para VD venda ou CP compra'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='TIPOBUSCA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do item de classificaï¿½ï¿½o fiscal '
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODEMPIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do ï¿½tem de classificaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFILIALIFP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da classificaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODFISCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do ï¿½tem de classficiaï¿½ï¿½o fiscal'
where Rdb$Procedure_Name='LFBUSCAFISCALSP' and Rdb$Parameter_Name='CODITFISCP';

Update Rdb$Procedures set Rdb$Description =
'Procedure para busca de informaï¿½ï¿½es fiscais de um ï¿½tem de venda, utilizada para preencher dados da tabela lfitvenda.'
where Rdb$Procedure_Name='LFBUSCAFISCALSP02';

/* Alter (LFBUSCAPREVTRIBORC) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCAPREVTRIBORC(CODEMP INTEGER,
CODFILIAL INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC SMALLINT)
 RETURNS(VLRICMS NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRISS NUMERIC(15,5))
 AS
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc smallint;
declare variable vlrbasepis numeric(15,5);
declare variable vlrbasecofins numeric(15,5);
declare variable vlrprod integer;
declare variable aliqpis numeric(6,2);
declare variable qtd numeric(15,5);
declare variable vlrpisunidtrib numeric(15,5);
declare variable codsittribcof char(2);
declare variable aliqcofins numeric(6,2);
declare variable vlrcofunidtrib numeric(15,5);
declare variable vlrliq numeric(15,5);
declare variable vlrfrete numeric(15,5);
declare variable codsittribipi char(2);
declare variable vlripiunidtrib numeric(15,5);
declare variable aliqipi numeric(6,2);
declare variable tpcalcipi char(1);
declare variable vlrbaseipi numeric(15,5);
declare variable aliqcsocial numeric(6,2);
declare variable aliqir numeric(6,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable tipoprod varchar(2);
declare variable aliqiss numeric(6,2);
declare variable aliqicms numeric(6,2);
declare variable tpredicms char(1);
declare variable redicms numeric(15,5);
declare variable baseicms numeric(15,5);
declare variable codtrattrib char(2);
declare variable codsittribpis char(2);
declare variable ufcli char(2);
begin

    -- Inicializando variáveis

    vlripi = 0;
    vlrfrete = 0;

    -- Buscando informações no orçamento (cliente e tipo de movimento)
    select oc.codempcl,oc.codfilialcl,oc.codcli,tm.codemptm,tm.codfilialtm,tm.codtipomovtm,coalesce(cl.siglauf,cl.ufcli)
    from vdorcamento oc, vdcliente cl, eqtipomov tm
    where oc.codemp=:codemp and oc.codfilial=:codfilial and oc.codorc=:codorc and oc.tipoorc=:tipoorc
    and cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli
    and tm.codemp=oc.codemp and tm.codfilial=oc.codfilialtm and tm.codtipomov=oc.codtipomov
    into :codempcl, :codfilialcl, :codcli, :codemptm, :codfilialtm, :codtipomov, :ufcli;

    -- Buscando informações do produto no item de orçamento
    select io.codemppd, io.codfilialpd, io.codprod, io.vlrproditorc, io.qtditorc, io.vlrliqitorc, coalesce(io.vlrfreteitorc,0),
    pd.tipoprod

    from vditorcamento io, eqproduto pd
    where io.codemp=:codemp and io.codfilial=:codfilial and io.codorc=:codorc and io.tipoorc=:tipoorc and io.coditorc=:coditorc
    and pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod
    into :codemppd, :codfilialpd, :codprod, :vlrprod, :qtd, :vlrliq, :vlrfrete, :tipoprod;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempcl, :codfilialcl, :codcli,
    :codemptm, :codfilialtm, :codtipomov, 'VD',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.codsittribpis, cf.aliqpisfisc, cf.vlrpisunidtrib, cf.codsittribcof, cf.aliqcofinsfisc, cf.vlrcofunidtrib,
    cf.vlripiunidtrib, cf.aliqipifisc, cf.codsittribipi, cf.tpcalcipi,
    coalesce(cf.aliqcsocialfisc, fi.perccsocialfilial), coalesce(cf.aliqirfisc, fi.percirfilial), coalesce(cf.aliqissfisc, fi.percissfilial),
    cf.tpredicmsfisc, cf.redfisc, cf.codtrattrib
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :codsittribpis, :aliqpis, :vlrpisunidtrib, :codsittribcof, :aliqcofins, :vlrcofunidtrib,
    :vlripiunidtrib, :aliqipi, :codsittribipi,:tpcalcipi, :aliqcsocial, :aliqir, :aliqiss,
    :tpredicms, :redicms, :codtrattrib;

    -- Definição do IPI
    if(:codsittribipi not in ('52','53','54')) then -- IPI Tributado
    begin
        if(:tpcalcipi='P' and aliqipi is not null and aliqipi > 0) then -- Calculo pela aliquota
        begin
            vlrbaseipi = :vlrliq; -- (Base do IPI = Valor total dos produtos - Implementar situações distintas futuramente)
            vlripi = (vlrbaseipi * aliqipi) / 100;
        end
        else if (vlripiunidtrib is not null and vlripiunidtrib > 0) then -- Calculo pela quantidade
        begin
            vlripi = qtd * vlripiunidtrib;
        end
    end

    -- Definição do PIS

    if(:codsittribpis in ('01','02','99') and aliqpis is not null and aliqpis > 0 ) then -- PIS Tributado pela alíquota
    begin
        vlrbasepis = :vlrprod; -- (Base do PIS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrpis = (vlrbasepis * aliqpis) / 100;
    end
    else if (:codsittribpis in ('03') and vlrpisunidtrib is not null and vlrpisunidtrib > 0) then -- PIS Tributado pela quantidade
    begin
        vlrpis = qtd * vlrpisunidtrib;
    end

    -- Definição do COFINS

    if(:codsittribcof in ('01','02','99') and aliqcofins is not null and aliqcofins > 0 ) then -- COFINS Tributado pela alíquota
    begin
        vlrbasecofins = :vlrprod; -- (Base do COFINS = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrcofins = (vlrbasecofins * aliqcofins) / 100;
    end
    else if (:codsittribpis in ('03') and vlrcofunidtrib is not null and vlrcofunidtrib > 0) then -- COFINS Tributado pela quantidade
    begin
        vlrcofins = qtd * vlrcofunidtrib;
    end

    -- Definição do IR

    if(aliqir is not null and aliqir > 0) then
    begin
        vlrir = ((:vlrliq + :vlripi + :vlrfrete) * aliqir) / 100;
    end

    -- Definição da CSocial

    vlrcsocial = ((:vlrliq + :vlripi + :vlrfrete) * aliqcsocial) / 100;

    -- Definição do ISS se for um serviço
    if (tipoprod = 'S') then
    begin
        if ( aliqiss is not null and aliqiss > 0 ) then
        begin
            vlriss = vlrliq * (aliqiss/100);
        end
    end

    -- Definição do ICMS
    -- Calcular icms quando aliquota maio que zero e tratamento tributário não for (isento, isento ou n.trib, n.trib., suspenso)

    if(codtrattrib not in ('40','30','41','50')) then
    begin

        if(aliqicms is null) then
        begin
            select ti.aliqti from lftabicms ti
            where codemp=:codemp and codfilial=:codfilial and ufti=:ufcli
            into aliqicms;
        end

        if (redicms>0) then -- Com redução
        begin
            if(tpredicms='B') then -- Redução na base de cálculo
            begin
                baseicms = vlrliq * ( 1 - (redicms / 100));
                vlricms = baseicms * (aliqicms / 100);
            end
            else -- Redução no valor
            begin

                baseicms = vlrliq;
                vlricms = baseicms * ( aliqicms / 100 );
                vlricms = vlricms * (( 100 - redicms ) / 100);


            end
        end
        else -- Sem redução
        begin

            baseicms = vlrliq;
            vlricms = baseicms * (aliqicms / 100);

        end

    end
  suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para busca de previsï¿½o de tributos incidentes em ï¿½tem de orï¿½amento para calculo da previsï¿½o de lucratividade.'
where Rdb$Procedure_Name='LFBUSCAPREVTRIBORC';

/* Alter (LFBUSCATRIBCOMPRA) */
SET TERM ^ ;

ALTER PROCEDURE LFBUSCATRIBCOMPRA(CODEMP INTEGER,
CODFILIAL INTEGER,
CODCOMPRA INTEGER,
CODEMPPD INTEGER,
CODFILIALPD SMALLINT,
CODPROD INTEGER,
VLRLIQ NUMERIC(15,5))
 RETURNS(VLRBASEFUNRURAL NUMERIC(15,5),
ALIQFUNRURAL NUMERIC(6,2),
VLRFUNRURAL NUMERIC(15,5),
CODEMPIF INTEGER,
CODFILIALIF SMALLINT,
CODFISC CHAR(13),
CODITFISC SMALLINT)
 AS
declare variable codempfr integer;
declare variable codfilialfr smallint;
declare variable codfor integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
begin

    -- Inicializando variáveis

    vlrfunrural = 0;

    -- Buscando informações na compra (fornecedor e tipo de movimento)
    select cp.codempfr,cp.codfilialfr,cp.codfor,tm.codemptm,tm.codfilialtm,tm.codtipomovtm
    from cpcompra cp, cpforneced fr, eqtipomov tm
    where cp.codemp=:codemp and cp.codfilial=:codfilial and cp.codcompra=:codcompra
    and fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor
    and tm.codemp=cp.codemp and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov
    into :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov;

    -- Buscando a regra de classificação para o ítem
    select bf.codempif, bf.codfilialif, bf.codfisc, bf.coditfisc
    from lfbuscafiscalsp(:codemppd, :codfilialpd, :codprod, :codempfr, :codfilialfr, :codfor, :codemptm, :codfilialtm, :codtipomov, 'CP',null,null,null,null,null) bf
    into :codempif, :codfilialif, :codfisc, :coditfisc;

    -- Buscando informacoes fiscais na tabela de regras
    select cf.aliqfunruralfisc
    from lfitclfiscal cf
    left outer join sgfilial fi on
    fi.codemp=:codemp and fi.codfilial=:codfilial
    where cf.codemp=:codempif and cf.codfilial=:codfilialif and cf.codfisc=:codfisc and cf.coditfisc=:coditfisc
    into :aliqfunrural;

    -- Definição do Funrural
    if(:aliqfunrural>0) then -- Retenção do funrural
    begin
        vlrbasefunrural = :vlrliq; -- (Base do Funrural = Valor total dos produtos - Implementar situações distintas futuramente)
        vlrfunrural = (vlrbasefunrural * aliqfunrural) / 100;
    end


    suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Retorna o custo unitï¿½rio do produto'
where Rdb$Procedure_Name='PPCUSTOPRODSP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da emrpesa do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do item de orï¿½amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequï¿½ncia da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da estaï¿½ï¿½o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por data de aprovaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por data de fabricaï¿½ï¿½'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento ï¿½ por cï¿½digo de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprovaï¿½ï¿½o do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do item de compra (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da empresa do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da filial do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (conversï¿½o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';

Update Rdb$Procedures set Rdb$Description =
'Procedure disparada apï¿½s a inserï¿½ï¿½o na tabela PPOP, realiza varredura na estrutura do produto, verificando as anï¿½lises necessï¿½rias
no controle de qualidade e gerando registros referentes ao controle de qualidade na tabela PPOPCQ.'
where Rdb$Procedure_Name='PPGERAOPCQ';

/* Alter (PPITOPSP01) */
SET TERM ^ ;

ALTER PROCEDURE PPITOPSP01(ICODEMP INTEGER,
ICODFILIAL SMALLINT,
ICODOP INTEGER,
ISEQOP SMALLINT)
 AS
declare variable icodemppd integer;
declare variable icodfilialpd smallint;
declare variable gerarma char(1);
declare variable crefprod varchar(20);
declare variable icodprodpd integer;
declare variable nqtditop numeric(15,5);
declare variable icodemple integer;
declare variable icodfilialle smallint;
declare variable ccodlote varchar(20);
declare variable icodempfs integer;
declare variable icodfilialfs smallint;
declare variable icodfase integer;
declare variable icodemptr integer;
declare variable icodfilialtr smallint;
declare variable icodtprec integer;
declare variable icodemprp integer;
declare variable icodfilialrp smallint;
declare variable icodrecp integer;
declare variable dtempoof numeric(15,5);
declare variable iseqof smallint;
declare variable iseqppitop integer;
declare variable qtditest numeric(15,5);
declare variable qtdest numeric(15,5);
declare variable qtdprevprodop numeric(15,5);
declare variable qtdfixa char(1);
declare variable estdinamica char(1);
declare variable permiteajusteitop char(1);
declare variable iseqsubprod integer;
declare variable qtditestsp numeric(15,5);
declare variable codempts integer;
declare variable codfilialts smallint;
declare variable codtipomovsp integer;
declare variable tipoexterno char(10);
begin

    --Loop nas fases da estrutura para geração da tabela de fases da OP.
    for select ef.seqef, ef.codempfs, ef.codfilialfs, ef.codfase, ef.codemptr, ef.codfilialtr, ef.codtprec, ef.tempoef, o.estdinamica
    from ppestrufase ef, ppop o, ppestrutura e
    where
        o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
        e.codemp=o.codemppd and e.codfilial=o.codfilialpd and e.codprod=o.codprod and e.seqest=o.seqest and
        ef.codemp=e.codemp and ef.codfilial=e.codfilial and ef.codprod=e.codprod and ef.seqest=E.seqest
    into
        :iseqof, :icodempfs, :icodfilialfs, :icodfase, :icodemptr, :icodfilialtr, :icodtprec, :dtempoof, :estdinamica
    do
    begin
        -- Buscando o primeiro recurso para inserção na fase (provisório)
        select first 1 codemp, codfilial, codrecp from pprecurso r
        where r.codemp=:icodemptr and r.codfilial=:icodfilialtr and r.codtprec=:icodtprec
        into :icodemprp, :icodfilialrp, :icodrecp;

        -- Inserindo na tabela de fase por op
        insert into
            ppopfase (
                codemp, codfilial, codop, seqop, seqof, codempfs, codfilialfs, codfase, codemprp, codfilialrp, codrecp, tempoof,
                codemptr, codfilialtr, codtprec
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqof, :icodempfs, :icodfilialfs,:icodfase, :icodemprp, :icodfilialrp,
                :icodrecp,:dtempoof, :icodemptr, :icodfilialtr, :icodtprec
            );
    end

    -- Se a estrutura não for dinâmica, deve inserir os ítens

    if(coalesce(:estdinamica,'N')='N'  ) then    
    begin

        iseqppitop = 0;

        for select
            ie.codemppd, ie.codfilialpd, ie.codprodpd, ie.refprodpd, ie.rmaautoitest, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditest, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, ie.permiteajusteitest, ie.tipoexterno
            from
                ppitestrutura ie, ppestrutura e, ppop o
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :icodprodpd, :crefprod, :gerarma, :icodempfs, :icodfilialfs, :icodfase,
        :qtditest,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote,:permiteajusteitop,:tipoexterno
        do
        begin
            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqppitop = :iseqppitop + 1;

            if ('S'=qtdfixa) then
            begin
                nqtditop = :qtditest;
            end
            else
            begin
                nqtditop = cast(:qtditest/:qtdest as numeric(15,5) ) * cast(:qtdprevprodop as numeric(15, 5));
            end

            insert into ppitop (
                codemp, codfilial, codop, seqop, seqitop, codemppd, codfilialpd, codprod, refprod,
                codempfs, codfilialfs, codfase, qtditop, gerarma, permiteajusteitop, tipoexterno
            )
            values (
                :icodemp, :icodfilial, :icodop, :iseqop, :iseqppitop, :icodemppd, :icodfilialpd,
                :icodprodpd, :crefprod,:icodempfs, :icodfilialfs, :icodfase, :nqtditop, :gerarma,
                :permiteajusteitop, :tipoexterno
            );

        end

        -- Inserindo tabela de subprodutos

        iseqsubprod = 0;

        -- Buscando tipo de movimento para subproducao
        select codempts, codfilialts, codtipomovsp from sgprefere5 where codemp=:icodemp and codfilial=:icodfilial
        into :codempts, :codfilialts, :codtipomovsp;

        for select
            ie.codemppd, ie.codfilialpd, ie.seqitestsp, ie.codprodpd, ie.refprodpd, ie.codempfs, ie.codfilialfs, ie.codfase,
            ie.qtditestsp, e.qtdest, o.qtdprevprodop, ie.qtdfixa,
            (   select min(l.codlote) from eqlote l
                where
                l.codemp=e.codemp and l.codfilial=e.codfilial and l.codprod=e.codprod and l.sldliqlote > 0 and l.venctolote =
                (   select min( ls.venctolote ) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and
                    ls.codemp=l.codemp and ls.sldliqlote>0)) codlote, fs.seqof
            from
                ppitestruturasubprod ie, ppestrutura e, ppop o, ppopfase fs
            where
                ie.codemp=e.codemp and ie.codfilial=e.codfilial and ie.codprod=e.codprod and ie.seqest=e.seqest and
                o.codemppd=e.codemp and o.codfilialpd=e.codfilial and o.codprod=e.codprod and o.seqest=e.seqest and
                o.codemp=:icodemp and o.codfilial=:icodfilial and o.codop=:icodop and o.seqop=:iseqop and
                fs.codemp=ie.codempfs and fs.codfilial=ie.codfilialfs and fs.codfase=ie.codfase and fs.codop=o.codop and fs.seqop=o.seqop
            order by ie.codfase, ie.seqef
        into
        :icodemppd, :icodfilialpd, :iseqsubprod, :icodprodpd, :crefprod, :icodempfs, :icodfilialfs, :icodfase,
        :qtditestsp,:qtdest,:qtdprevprodop,:qtdfixa,:ccodlote, :iseqof
        do
        begin

            if (:ccodlote is null ) then
            begin
                icodemple = null;
                icodfilialle = null;
            end
            else
            begin
                icodemple = icodemppd;
                icodfilialle = icodfilialpd;
            end

            iseqsubprod = :iseqsubprod + 1;

           insert into ppopsubprod (codemp, codfilial, codop, seqop, seqsubprod, codemppd, codfilialpd, codprod,
                refprod, qtditsp, codempfs, codfilialfs, codfase, codemple, codfilialle, codlote, seqof, codemptm, codfilialtm, codtipomov
           )
           values(
                :icodemp, :icodfilial,:icodop,:iseqop, :iseqsubprod, :icodemppd, :icodfilialpd, :icodprodpd,
                :crefprod, :qtditestsp, :icodempfs, :icodfilialfs, :icodfase, :icodemple, :icodfilialle, :ccodlote, :iseqof, :codempts, :codfilialts, :codtipomovsp
           );



        end


    end

end
^

SET TERM ; ^

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da vaga'
where Rdb$Procedure_Name='RHLISTACANDVAGASP' and Rdb$Parameter_Name='ICODVAGA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Cï¿½digo da funï¿½ï¿½'
where Rdb$Procedure_Name='RHLISTACANDVAGASP' and Rdb$Parameter_Name='ICODFUNC';

/* Alter (SGRETVERSAO) */
SET TERM ^ ;

ALTER PROCEDURE SGRETVERSAO RETURNS(VERSAO VARCHAR(30))
 AS
begin
    versao = '1.2.4.6 (28/06/2011)';
    suspend;
end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para geraï¿½ï¿½o de registros nas tabelas TKCAMPANHACTO e TKSITCAMP.'
where Rdb$Procedure_Name='TKGERACAMPANHACTO';

/* Alter (VDADICITEMPDVSP) */
SET TERM ^ ;

ALTER PROCEDURE VDADICITEMPDVSP(CODVENDA INTEGER,
CODEMP INTEGER,
CODFILIAL CHAR(8),
CODPROD INTEGER,
CODEMPPD INTEGER,
CODFILIALPD INTEGER,
QTDITVENDA NUMERIC(9,5),
VLRPRECOITVENDA NUMERIC(18,5),
VLRDESCITVENDA NUMERIC(18,5),
PERCDESCITVENDA NUMERIC(15,5),
VLRCOMISITVENDA NUMERIC(15,5),
PERCCOMISITVENDA NUMERIC(15,5),
SCODLOTE VARCHAR(20),
CODEMPLE INTEGER,
CODFILIALLE SMALLINT,
CODFILIALCV SMALLINT,
CODCONV INTEGER)
 RETURNS(CODITVENDA INTEGER,
PERCICMSITVENDA NUMERIC(9,2),
VLRBASEICMSITVENDA NUMERIC(18,3),
VLRICMSITVENDA NUMERIC(18,3),
VLRLIQITVENDA NUMERIC(18,3),
TIPOFISC CHAR(2))
 AS
declare variable icodfilialnt smallint;
declare variable icodcli integer;
declare variable icodfilialcl integer;
declare variable icodtipomov integer;
declare variable icodfilialtm integer;
declare variable scodnat char(4);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percipiitvenda numeric(9,2);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable srefprod varchar(20);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(9,2);
begin

  SELECT MAX(CODITVENDA)+1 FROM VDITVENDA WHERE CODVENDA=:CODVENDA
    AND CODFILIAL=:CODFILIAL AND CODEMP=:CODEMP INTO CODITVENDA;

  IF (CODITVENDA IS NULL) THEN
    CODITVENDA = 1;

/*Informações da Venda.:*/

  SELECT V.CODCLI,V.CODFILIALCL,C.UFCLI,V.CODTIPOMOV,V.CODFILIALTM FROM VDVENDA V, VDCLIENTE C
    WHERE V.CODEMP=:CODEMP AND V.CODFILIAL=:CODFILIAL
    AND V.CODVENDA=:CODVENDA AND V.TIPOVENDA='E' AND
    C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND
    C.CODFILIAL=V.CODFILIALCL INTO ICODCLI,ICODFILIALCL,UFCLI,ICODTIPOMOV,ICODFILIALTM;


  UFFLAG = 'N';

  SELECT 'S' FROM SGFILIAL  WHERE CODEMP=:CODEMP
    AND CODFILIAL=:CODFILIAL AND UFFILIAL=:UFCLI INTO UFFLAG;


  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFNATOPER') INTO ICODFILIALNT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFTRATTRIB') INTO ICODFILIALTT;
  SELECT ICODFILIAL FROM SGRETFILIAL(:CODEMP,'LFMENSAGEM') INTO ICODFILIALME;

  SELECT C.ALIQIPIFISC
      FROM EQPRODUTO P, LFITCLFISCAL C
         WHERE P.CODPROD=:CODPROD AND P.CODFILIAL=:CODFILIALPD
         AND P.CODEMP=:CODEMPPD AND C.CODFISC=P.CODFISC AND C.CODFILIAL=P.CODFILIALFC and
         C.geralfisc='S'
         AND C.CODEMP=P.CODEMPFC INTO PERCIPIITVENDA;

  SELECT CODNAT FROM
      LFBUSCANATSP(:CODFILIAL,:CODEMP,:CODFILIALPD,
                   :CODPROD,:CODEMP,:ICODFILIALCL,
                   :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,null)
      INTO SCODNAT;

  IF (SCODNAT IS NULL) THEN
      EXCEPTION VDITVENDAEX03;

  SELECT TIPOFISC,REDFISC,CODTRATTRIB,ORIGFISC,CODMENS,ALIQFISC FROM
      LFBUSCAFISCALSP(:CODEMP,:CODFILIALPD,:CODPROD,
                      :CODEMP,:ICODFILIALCL,:ICODCLI,
                      :CODEMP,:ICODTIPOMOV,:ICODFILIALTM, 'VD',:SCODNAT,null,null,null,null)
      INTO TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA;

  VLRPRODITVENDA = (QTDITVENDA*VLRPRECOITVENDA);
  VLRBASEIPIITVENDA = 0;
  VLRBASEICMSITVENDA = 0;
  VLRICMSITVENDA = 0;
  VLRIPIITVENDA = 0;
  IF (PERCIPIITVENDA IS NULL) THEN
     PERCIPIITVENDA = 0;

  IF ( TIPOFISC = 'II') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'FF') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'NN') THEN
  BEGIN
    PERCICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    PERCIPIITVENDA = 0;
    VLRIPIITVENDA = 0;
    VLRBASEIPIITVENDA = 0;
  END
  ELSE IF ( TIPOFISC = 'TT') THEN
  BEGIN
    IF (PERCICMSITVENDA = 0 OR PERCICMSITVENDA IS NULL) THEN
      SELECT PERCICMS FROM LFBUSCAICMSSP (:SCODNAT,:UFCLI,:CODEMP,:CODFILIAL) INTO PERCICMSITVENDA;
    IF (PERCRED IS NULL) THEN
      PERCRED = 0;
    VLRBASEICMSITVENDA = (VLRPRODITVENDA-VLRDESCITVENDA) - ((VLRPRODITVENDA-VLRDESCITVENDA)*(PERCRED/100));
    VLRBASEIPIITVENDA = VLRPRODITVENDA-VLRDESCITVENDA;
    VLRICMSITVENDA = VLRBASEICMSITVENDA*(PERCICMSITVENDA/100);
    VLRIPIITVENDA = VLRBASEIPIITVENDA*(PERCIPIITVENDA/100);
  END
  VLRLIQITVENDA= VLRPRODITVENDA+VLRIPIITVENDA-VLRDESCITVENDA;
  INSERT INTO VDITVENDA (
     CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,CODITVENDA,
     CODEMPNT,CODFILIALNT,CODNAT,
     CODEMPPD,CODFILIALPD,CODPROD,
     CODEMPLE,CODFILIALLE,CODLOTE,
     QTDITVENDA,PRECOITVENDA,PERCDESCITVENDA,VLRDESCITVENDA,
     VLRCOMISITVENDA,PERCCOMISITVENDA,
     PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,
     PERCIPIITVENDA,VLRBASEIPIITVENDA,VLRIPIITVENDA,VLRLIQITVENDA,
     VLRPRODITVENDA,REFPROD,ORIGFISC,
     CODEMPTT,CODFILIALTT,CODTRATTRIB,TIPOFISC,
     CODEMPME,CODFILIALME,CODMENS,OBSITVENDA,
     CODEMPCV,CODFILIALCV,CODCONV) VALUES (
     :CODEMP,:CODFILIAL,'E',:CODVENDA,:CODITVENDA,
     :CODEMP,:ICODFILIALNT,:SCODNAT,
     :CODEMPPD,:CODFILIALPD,:CODPROD,
     :CODEMPLE,:CODFILIALLE,:SCODLOTE,
     :QTDITVENDA,:VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,
     :VLRCOMISITVENDA,:PERCCOMISITVENDA,
     :PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
     :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,
     :VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
     :CODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,
     :CODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
     :CODEMP, :CODFILIALCV,:CODCONV);
  SUSPEND;
END
^

/* Alter (VDADICITVENDAORCSP) */
ALTER PROCEDURE VDADICITVENDAORCSP(FILIALATUAL INTEGER,
ICODVENDA INTEGER,
ICODORC INTEGER,
ICODITORC INTEGER,
ICODFILIAL SMALLINT,
ICODEMP INTEGER,
STIPOVENDA CHAR(10),
TPAGRUP CHAR(1),
IQTDITVENDA NUMERIC(15,5),
VLRDESCITVENDA NUMERIC(15,5))
 AS
declare variable icoditvenda integer;
declare variable icodfilialnt smallint;
declare variable codempax integer;
declare variable codfilialax integer;
declare variable codalmox integer;
declare variable icodcli integer;
declare variable icodfilialtm integer;
declare variable icodtipomov integer;
declare variable icodfilialcl integer;
declare variable scodnat char(4);
declare variable icodfilialle smallint;
declare variable scodlote varchar(20);
declare variable tipofisc char(2);
declare variable sorigfisc char(1);
declare variable scodtrattrib char(2);
declare variable icodfilialtt smallint;
declare variable icodfilialme smallint;
declare variable icodmens smallint;
declare variable percicmsitvenda numeric(15,5);
declare variable vlrbaseicmsitvenda numeric(15,5);
declare variable vlricmsitvenda numeric(15,5);
declare variable percipiitvenda numeric(15,5);
declare variable vlrbaseipiitvenda numeric(15,5);
declare variable vlripiitvenda numeric(15,5);
declare variable icodprod integer;
declare variable icodfilialpd integer;
declare variable vlrprecoitvenda numeric(15,5);
declare variable percdescitvenda numeric(15,5);
declare variable vlrliqitvenda numeric(15,5);
declare variable vlrproditvenda numeric(15,5);
declare variable obsitorc varchar(500);
declare variable ufcli char(2);
declare variable ufflag char(1);
declare variable percred numeric(15,5);
declare variable cloteprod char(1);
declare variable perccomisitvenda numeric(15,5);
declare variable geracomis char(1);
declare variable vlrcomisitvenda numeric(15,5);
declare variable codempif integer;
declare variable codfilialif smallint;
declare variable codfisc char(13);
declare variable coditfisc integer;
declare variable percissitvenda numeric(15,5);
declare variable vlrbaseissitvenda numeric(15,5);
declare variable vlrissitvenda numeric(15,5);
declare variable vlrisentasitvenda numeric(15,5);
declare variable vlroutrasitvenda numeric(15,5);
declare variable tipost char(2);
declare variable vlrbaseicmsstitvenda numeric(15,5);
declare variable vlricmsstitvenda numeric(15,5);
declare variable margemvlagritvenda numeric(15,5);
declare variable srefprod varchar(20);
declare variable stipoprod varchar(2);
declare variable percicmsst numeric(15,5);
declare variable vlrbaseicmsbrutitvenda numeric(15,5);
declare variable tpredicms char(1);
declare variable redbaseicmsst char(1);
begin
-- Inicialização de variaveis

    UFFLAG = 'N';

    select icodfilial from sgretfilial(:ICODEMP,'LFNATOPER') into ICODFILIALNT;
    select icodfilial from sgretfilial(:ICODEMP,'LFTRATTRIB') into ICODFILIALTT;
    select icodfilial from sgretfilial(:ICODEMP,'LFMENSAGEM') into ICODFILIALME;

    select vd.codfilialtm,vd.codtipomov from vdvenda vd where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODFILIALTM,ICODTIPOMOV;

-- Verifica se deve gerar comissão para a venda

    select geracomisvendaorc from sgprefere1 where codemp=:ICODEMP and codfilial=:ICODFILIAL into GERACOMIS;

-- Busca sequencia de numeração do ítem de venda

    select coalesce(max(coditvenda),0)+1 from vditvenda where codvenda=:ICODVENDA and codfilial=:ICODFILIAL and codemp=:ICODEMP and tipovenda=:STIPOVENDA
    into ICODITVENDA;

-- Informações do Orcamento

    select codcli,codfilialcl from vdorcamento where codemp=:ICODEMP and codfilial=:ICODFILIAL and codorc=:ICODORC into ICODCLI,ICODFILIALCL;

-- Informações do item de orçamento

    select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc,it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc,
    it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod,pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc
    from vditorcamento it, eqproduto pd
    where it.coditorc=:ICODITORC and it.codorc=:ICODORC and it.codfilial=:ICODFILIAL and it.codemp=:ICODEMP and
    pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod
    into ICODEMP,ICODFILIALPD,ICODPROD,VLRPRECOITVENDA,PERCDESCITVENDA,VLRLIQITVENDA,VLRPRODITVENDA,SREFPROD,OBSITORC,
    CODEMPAX,CODFILIALAX,CODALMOX,SCODLOTE,CLOTEPROD,perccomisitvenda,STIPOPROD,perccomisitvenda,vlrcomisitvenda;

    -- Informações fiscais para a venda

    select coalesce(c.siglauf,c.ufcli)
    from vdorcamento o, vdcliente c
    where o.codorc=:ICODORC and o.codfilial=:ICODFILIAL and o.codemp=:ICODEMP and
    c.codcli=o.codcli and c.codfilial=o.codfilialcl and c.codemp=o.codempcl
    into ufcli;

    -- Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)

    select codempif,codfilialif,codfisc,coditfisc
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',null,null,null,null,null)
    into CODEMPIF,CODFILIALIF,CODFISC,CODITFISC;

-- Verifica se a venda é para outro estado

    select codnat from lfbuscanatsp(:FILIALATUAL,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,
    :ICODCLI,NULL,NULL,NULL,:ICODFILIALTM,:ICODTIPOMOV,:coditfisc)
    into SCODNAT;
    
    if (SCODNAT IS NULL) then
    begin
        exception vditvendaex03 :SREFPROD; -- NÃO FOI POSSÍVEL ENCONTRAR A NATUREZA DA OPERAÇÃO PARA O ÍTEM
    end

-- Busca informações fiscais para o ítem de venda (já sabe o coditfisc)

    select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost,
    margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest,aliqiss
    from lfbuscafiscalsp(:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALCL,:ICODCLI,:ICODEMP,:ICODFILIALTM,
    :ICODTIPOMOV, 'VD',:scodnat,:codempif,:codfilialif,:codfisc,:coditfisc)
    into TIPOFISC,PERCRED,SCODTRATTRIB,SORIGFISC,ICODMENS,PERCICMSITVENDA,CODEMPIF,CODFILIALIF,CODFISC,CODITFISC,TIPOST,MARGEMVLAGRITVENDA,
    PERCIPIITVENDA,PERCICMSST, tpredicms, redbaseicmsst, PERCISSITVENDA;

-- Busca lote, caso seja necessário

    if (CLOTEPROD = 'S' and SCODLOTE is null) then
    begin
        select first 1 l.codlote from eqlote l
        where l.codprod=:ICODPROD and l.codfilial=:ICODFILIALPD and l.codemp=:ICODEMP and
        l.venctolote = ( select min(venctolote) from eqlote ls where ls.codprod=l.codprod and ls.codfilial=l.codfilial and ls.codemp=L.codemp and
                         ls.sldliqlote>=:IQTDITVENDA ) and
        l.sldliqlote>=:IQTDITVENDA
        into SCODLOTE;

        ICODFILIALLE = ICODFILIALPD;
    end
    
-- Inicializando valores

    VLRPRODITVENDA = VLRPRECOITVENDA * :IQTDITVENDA;
    VLRLIQITVENDA = VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEIPIITVENDA = 0;
    VLRBASEICMSITVENDA = 0;
    VLRICMSITVENDA = 0;
    VLRIPIITVENDA = 0;

    if (PERCICMSITVENDA = 0 or PERCICMSITVENDA is null) then
    begin
        select coalesce(PERCICMS,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL) into PERCICMSST;
    end

    if (PERCRED is null) then
    begin
        PERCRED = 0;
    end

    if(percred>0) then
    begin
        if(:tpredicms='B') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA) - ( (VLRPRODITVENDA - :VLRDESCITVENDA) * ( PERCRED / 100 ) );
            VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
        end
        else if(:tpredicms='V') then
        begin
            VLRBASEICMSITVENDA = (:VLRPRODITVENDA - :VLRDESCITVENDA);
            VLRICMSITVENDA = (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 )) -  ( (VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 ) * ( PERCRED / 100 ) )) ;
        end
    end
    else
    begin
        VLRBASEICMSITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
        VLRICMSITVENDA = VLRBASEICMSITVENDA * ( PERCICMSITVENDA / 100 );
    end

    VLRBASEIPIITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRBASEICMSBRUTITVENDA = :VLRPRODITVENDA - :VLRDESCITVENDA;
    VLRIPIITVENDA = VLRBASEIPIITVENDA * ( PERCIPIITVENDA / 100 );

-- **** Calculo dos tributos ***

-- Verifica se é um serviço (Calculo do ISS);

    if (:STIPOPROD = 'S') then
    begin
    -- Carregando aliquota do ISS
    -- Bloco comentado, pois já buscou o percentual do iss através da procedure.
   --     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
   --     into PERCISSITVENDA;

    -- Calculando e computando base e valor do ISS;
        if (:PERCISSITVENDA != 0) then
        begin
            VLRBASEISSITVENDA = :VLRLIQITVENDA;
            VLRISSITVENDA = :VLRBASEISSITVENDA * (:PERCISSITVENDA/100);
        end
    end
    else -- Se o item vendido não for SERVIÇO zera ISS
        VLRBASEISSITVENDA = 0;

    -- Se produto for isento de ICMS
    if (:TIPOFISC = 'II') then
    begin
        VLRISENTASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLROUTRASITVENDA = 0;
    end
    -- Se produto for de Substituição Tributária
    else if (:TIPOFISC = 'FF') then
    begin
        if (:TIPOST = 'SI' ) then -- Contribuinte Substituído
        begin
            VLROUTRASITVENDA = :VLRLIQITVENDA;
            VLRBASEICMSITVENDA = 0;
            PERCICMSITVENDA = 0;
            VLRICMSITVENDA = 0;
            VLRISENTASITVENDA = 0;
        end
        else if (:TIPOST = 'SU' ) then -- Contribuinte Substituto
        begin

            if( (:PERCICMSST is null) or (:PERCICMSST=0) ) then
            begin
                select coalesce(PERCICMSINTRA,0) from lfbuscaicmssp (:SCODNAT,:UFCLI,:ICODEMP,:ICODFILIAL)
                into PERCICMSST;
            end

            if(percred>0 and redbaseicmsst='S') then
            begin
            -- Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
               vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end
            else
            begin
                -- Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
                vlrbaseicmsstitvenda = (  (coalesce(margemvlagritvenda,0) + 100) / 100 )  * (  (coalesce(vlrbaseicmsbrutitvenda,0) ) + (coalesce(vlripiitvenda,0)) );
            end

            VLROUTRASITVENDA = 0;
            VLRISENTASITVENDA = 0;
            VLRICMSSTITVENDA = ( (:VLRBASEICMSSTITVENDA * :PERCICMSST) / 100 ) - (:VLRICMSITVENDA) ;

        end
    end

    -- Se produto não for tributado e não isento

    else if (:TIPOFISC = 'NN') then
    begin
        VLROUTRASITVENDA = :VLRLIQITVENDA;
        VLRBASEICMSITVENDA = 0;
        PERCICMSITVENDA = 0;
        VLRICMSITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Se produto for tributado integralmente

    else if (:TIPOFISC = 'TT') then
    begin
        VLROUTRASITVENDA = 0;
        VLRISENTASITVENDA = 0;
    end

    -- Inserindo dados na tabela de ítens de venda

    if ( TPAGRUP <> 'F' ) then
    begin

        insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd,
        codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda,
        percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda,
        vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda,
        codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda,
        vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda,
        margemvlagritvenda,vlrbaseicmsbrutitvenda)
        values (:ICODEMP,:ICODFILIAL,:ICODVENDA,:STIPOVENDA,:ICODITVENDA,:ICODEMP,
        :ICODFILIALNT,:SCODNAT,:ICODEMP,:ICODFILIALPD,:ICODPROD,:ICODEMP,:ICODFILIALPD,:SCODLOTE,:IQTDITVENDA,
        :VLRPRECOITVENDA,:PERCDESCITVENDA,:VLRDESCITVENDA,:PERCICMSITVENDA,:VLRBASEICMSITVENDA,:VLRICMSITVENDA,
        :PERCIPIITVENDA,:VLRBASEIPIITVENDA,:VLRIPIITVENDA,:VLRLIQITVENDA,:VLRPRODITVENDA,:SREFPROD,:SORIGFISC,
        :ICODEMP,:ICODFILIALTT,:SCODTRATTRIB,:TIPOFISC,:ICODEMP,:ICODFILIALME,:ICODMENS,:OBSITORC,
        :CODEMPAX,:CODFILIALAX,:CODALMOX,:perccomisitvenda,:vlrcomisitvenda,:CODEMPIF,:CODFILIALIF,:CODFISC,:CODITFISC,
        :PERCISSITVENDA,:VLRBASEISSITVENDA,:VLRISSITVENDA,:VLRISENTASITVENDA,:VLROUTRASITVENDA,:TIPOST,
        :VLRBASEICMSSTITVENDA,:VLRICMSSTITVENDA,:MARGEMVLAGRITVENDA,:vlrbaseicmsbrutitvenda);
    end

-- Atualizando informações de vínculo

    execute procedure vdupvendaorcsp(:ICODEMP,:ICODFILIAL,:ICODORC,:ICODITORC,:ICODFILIAL,:ICODVENDA,:ICODITVENDA,:STIPOVENDA);

end
^

/* Alter (VDBUSCACUSTOVENDASP) */
ALTER PROCEDURE VDBUSCACUSTOVENDASP(CODEMPVD INTEGER,
CODFILIALVD SMALLINT,
CODVENDA INTEGER,
TIPOVENDA CHAR(1),
CODEMPOC INTEGER,
CODFILIALOC INTEGER,
CODORC INTEGER,
TIPOORC CHAR(1),
CODITORC INTEGER)
 RETURNS(VLRPROD NUMERIC(15,5),
VLRDESC NUMERIC(15,5),
VLRICMS NUMERIC(15,5),
VLROUTRAS NUMERIC(15,5),
VLRCOMIS NUMERIC(15,5),
VLRADIC NUMERIC(15,5),
VLRIPI NUMERIC(15,5),
VLRPIS NUMERIC(15,5),
VLRCOFINS NUMERIC(15,5),
VLRIR NUMERIC(15,5),
VLRCSOCIAL NUMERIC(15,5),
VLRFRETE NUMERIC(15,5),
TIPOFRETE CHAR(1),
ADICFRETE CHAR(1),
VLRCUSTOPEPS NUMERIC(15,5),
VLRCUSTOMPM NUMERIC(15,5),
VLRPRECOULTCP NUMERIC(15,5))
 AS
declare variable aliqicms numeric(9,2);
declare variable aliqipi numeric(9,2);
declare variable aliqpis numeric(9,2);
declare variable aliqir numeric(9,2);
declare variable aliqcofins numeric(9,2);
declare variable aliqcsocial numeric(9,2);
declare variable codemppd integer;
declare variable codfilialpd smallint;
declare variable codprod integer;
declare variable codemptm integer;
declare variable codfilialtm smallint;
declare variable codtipomov integer;
declare variable codfilialpf smallint;
declare variable codempcl integer;
declare variable codfilialcl smallint;
declare variable codcli integer;
declare variable vlrliq numeric(15,5);
declare variable redbase numeric(9,2);
declare variable base numeric(15,5);
declare variable ufcli char(2);
declare variable codtrattrib char(2);
declare variable comisprod numeric(6,2);
declare variable perccomvend numeric(6,2);
declare variable codnat char(4);
declare variable codregra char(4);
begin

    --Verifica se deve buscar custos para venda .
    if(:CODVENDA is not null) then
    begin

        select
            coalesce(vd.vlrprodvenda,0), coalesce(vd.vlrdescvenda,0), coalesce(vd.vlricmsvenda,0),
            coalesce(vd.vlroutrasvenda,0), coalesce(vd.vlrcomisvenda,0), coalesce(vd.vlradicvenda,0),
            coalesce(vd.vlripivenda,0), coalesce(vd.vlrpisvenda,0), coalesce(vd.vlrcofinsvenda,0),
            coalesce(vd.vlrirvenda,0), coalesce(vd.vlrcsocialvenda,0),
            coalesce(fr.vlrfretevd,0), fr.tipofretevd, fr.adicfretevd,
            
            sum(icv.vlrcustopeps * iv.qtditvenda),
            sum(icv.vlrcustompm * iv.qtditvenda),
            sum(icv.vlrprecoultcp * iv.qtditvenda)
            
            from
            vdvenda vd left outer join vdfretevd fr on
            fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda,
            
            vditvenda iv left outer join vditcustovenda icv on
            icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda
            and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda
            
            where vd.codemp=:CODEMPVD and vd.codfilial=:CODFILIALVD and vd.codvenda=:CODVENDA and vd.tipovenda=:TIPOVENDA and
            iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda
            
            group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14

            into vlrprod,vlrdesc,vlricms,vlroutras,vlrcomis,vlradic,vlripi,vlrpis,vlrcofins,vlrir,vlrcsocial,
                 vlrfrete,tipofrete,adicfrete,vlrcustopeps,vlrcustompm,vlrprecoultcp;

            suspend;

    end
    else
    begin
        --Buscando informações sobre o produto do item de orçamento
        select io.codemppd,io.codfilialpd,io.codprod,pd.comisprod,
        coalesce(io.vlrproditorc,0),coalesce(io.vlrdescitorc,0),coalesce(io.vlrliqitorc,0),
        ico.vlrcustopeps * io.qtditorc, ico.vlrcustompm * io.qtditorc, ico.vlrprecoultcp * io.qtditorc,
        cf.codregra
        from lfclfiscal cf, eqproduto pd, vditorcamento io left outer join vditcustoorc ico on
        ico.codemp=io.codemp and ico.codfilial=io.codfilial and ico.codorc = io.codorc and
        ico.tipoorc=io.tipoorc and ico.coditorc=io.coditorc
        where io.codemp=:CODEMPOC and io.codfilial=:CODFILIALOC and io.codorc=:CODORC and io.tipoorc=:TIPOORC and io.coditorc=:CODITORC and
        pd.codemp=io.codemppd and pd.codfilial=io.codfilial and pd.codprod=io.codprod and
        cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc
        into :CODEMPPD,:CODFILIALPD,:CODPROD,:VLRPROD,:VLRDESC,:VLRLIQ,:COMISPROD,:VLRCUSTOPEPS,:VLRCUSTOMPM,:VLRPRECOULTCP,:CODREGRA;

        -- Buscanco informações do orçamento,cliente,vendedor
        select oc.codempcl,oc.codfilialcl,oc.codcli,coalesce(cl.siglauf,cl.ufcli),vd.perccomvend,
        oc.tipofrete,oc.adicfrete
        from vdorcamento oc, vdcliente cl, vdvendedor vd
        where oc.codemp=:CODEMPOC and oc.codfilial=:CODFILIALOC and oc.tipoorc=:TIPOORC and oc.codorc=:CODORC and
        cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli and
        vd.codemp=oc.codempvd and vd.codfilial=oc.codfilialvd and vd.codvend=oc.codvend
        into :CODEMPCL,:CODFILIALCL,:CODCLI,:UFCLI,:PERCCOMVEND,:TIPOFRETE,:ADICFRETE;

        --Buscando o tipo de movimento a ser utilizado na venda futura
        select p1.codempt2,p1.codfilialt2,p1.codtipomov2 from sgprefere1 p1
        where p1.codemp=:CODEMPOC and p1.codfilial=:CODFILIALPF
        into :CODEMPTM,:CODFILIALTM,:CODTIPOMOV;

        -- Buscando natureza de operação da venda futura
        select first 1 nt.codnat from lfnatoper nt, lfitregrafiscal irf
        where nt.codemp=irf.codemp and nt.codfilial=irf.codfilial and nt.codnat=irf.codnat
        and (irf.codtipomov=:CODTIPOMOV or irf.codtipomov is null)
        and (irf.codemp=:CODEMPTM or irf.codemp is null)
        and (irf.codfilial=:CODFILIALTM or irf.codfilial is null)
        and irf.codregra=:CODREGRA and irf.noufitrf='N' and irf.cvitrf='V'
        into :CODNAT;

         -- Buscando informações fiscais
        select codtrattrib,coalesce(aliqfisc,0),coalesce(aliqipifisc,0),coalesce(aliqpis,0),coalesce(aliqcofins,0),coalesce(aliqcsocial,0),
        coalesce(aliqir,0),coalesce(redfisc,0)
        from lfbuscafiscalsp(:CODEMPPD,:CODFILIALPD,:CODPROD,:CODEMPCL,:CODFILIALCL,:CODCLI,:CODEMPTM,:CODFILIALTM,
        :CODTIPOMOV,'VD',:codnat,null,null,null,null)
        into :CODTRATTRIB,:ALIQICMS,:ALIQIPI,:ALIQPIS,:ALIQCOFINS,:ALIQCSOCIAL,:ALIQIR,:REDBASE;

        -- Caso o ICMS não seja definido na classifificação, buscar da tabela de aliquotas.
        if(:ALIQICMS = 0 and :CODTRATTRIB in('00','51','20','70','10') ) then
        begin
            select coalesce(PERCICMS,0) from lfbuscaicmssp (:CODNAT,:UFCLI,:CODEMPOC,:CODFILIALPF)
            into :ALIQICMS;
        end

        -- Buscando custo do ítem

        if(:REDBASE >0) then
        begin
            BASE = :VLRLIQ - ((:VLRLIQ * :REDBASE) /100 );
        end

        BASE = :VLRLIQ;

        vlricms = :BASE * :ALIQICMS / 100;

--      vlroutras =
        vlrcomis = :VLRLIQ * ((:COMISPROD * :PERCCOMVEND) / 10000 );

--      vlradic =
        vlripi = :VLRLIQ * :ALIQIPI / 100;
        vlrpis = :VLRLIQ * :ALIQCOFINS / 100;
        vlrcofins = :VLRLIQ * :ALIQCOFINS / 100;
        vlrir = :VLRLIQ * :ALIQIR /100;
        vlrcsocial = :VLRLIQ * :ALIQCSOCIAL / 100;
--      vlrfrete =

    end

end
^

SET TERM ; ^

Update Rdb$Procedures set Rdb$Description =
'Procedure para relatï¿½rio de ultimas vendas por cliente/produto.'
where Rdb$Procedure_Name='VDRETULTVDCLIPROD';

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODATENDO POSITION 3;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCONV POSITION 4;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCV POSITION 5;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCV POSITION 6;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPTO POSITION 7;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALTO POSITION 8;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODTPATENDO POSITION 9;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPAE POSITION 10;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALAE POSITION 11;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODATEND POSITION 12;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPSA POSITION 13;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALSA POSITION 14;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODSETAT POSITION 15;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DATAATENDO POSITION 16;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HORAATENDO POSITION 17;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DATAATENDOFIN POSITION 18;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HORAATENDOFIN POSITION 19;

ALTER TABLE ATATENDIMENTO ALTER COLUMN OBSATENDO POSITION 20;

ALTER TABLE ATATENDIMENTO ALTER COLUMN OBSINTERNO POSITION 21;

ALTER TABLE ATATENDIMENTO ALTER COLUMN STATUSATENDO POSITION 22;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCL POSITION 23;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCL POSITION 24;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCLI POSITION 25;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSU POSITION 26;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPUS POSITION 27;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALUS POSITION 28;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DOCATENDO POSITION 29;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCT POSITION 30;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCT POSITION 31;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCONTR POSITION 32;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODITCONTR POSITION 33;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCA POSITION 34;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCA POSITION 35;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCLASATENDO POSITION 36;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPCH POSITION 37;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALCH POSITION 38;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODCHAMADO POSITION 39;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CONCLUICHAMADO POSITION 40;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODEMPEA POSITION 41;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODFILIALEA POSITION 42;

ALTER TABLE ATATENDIMENTO ALTER COLUMN CODESPEC POSITION 43;

ALTER TABLE ATATENDIMENTO ALTER COLUMN EMMANUT POSITION 44;

ALTER TABLE ATATENDIMENTO ALTER COLUMN BLOQATENDO POSITION 45;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DTINS POSITION 46;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HINS POSITION 47;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSUINS POSITION 48;

ALTER TABLE ATATENDIMENTO ALTER COLUMN DTALT POSITION 49;

ALTER TABLE ATATENDIMENTO ALTER COLUMN HALT POSITION 50;

ALTER TABLE ATATENDIMENTO ALTER COLUMN IDUSUALT POSITION 51;

ALTER TABLE ATESPECATEND ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE ATESPECATEND ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE ATESPECATEND ALTER COLUMN CODESPEC POSITION 3;

ALTER TABLE ATESPECATEND ALTER COLUMN DESCESPEC POSITION 4;

ALTER TABLE ATESPECATEND ALTER COLUMN PGCOMIESPEC POSITION 5;

ALTER TABLE ATESPECATEND ALTER COLUMN COBCLIESPEC POSITION 6;

ALTER TABLE ATESPECATEND ALTER COLUMN CONTMETAESPEC POSITION 7;

ALTER TABLE ATESPECATEND ALTER COLUMN TEMPOMINCOBESPEC POSITION 8;

ALTER TABLE ATESPECATEND ALTER COLUMN TEMPOMAXCOBESPEC POSITION 9;

ALTER TABLE ATESPECATEND ALTER COLUMN PERCCOMIESPEC POSITION 10;

ALTER TABLE ATESPECATEND ALTER COLUMN MRELCOBESPEC POSITION 11;

ALTER TABLE ATESPECATEND ALTER COLUMN BHESPEC POSITION 12;

ALTER TABLE ATESPECATEND ALTER COLUMN DTINS POSITION 13;

ALTER TABLE ATESPECATEND ALTER COLUMN IDUSUINS POSITION 14;

ALTER TABLE ATESPECATEND ALTER COLUMN DTALT POSITION 15;

ALTER TABLE ATESPECATEND ALTER COLUMN IDUSUALT POSITION 16;

ALTER TABLE ATESPECATEND ALTER COLUMN HINS POSITION 17;

ALTER TABLE ATESPECATEND ALTER COLUMN HALT POSITION 18;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCOMPRA POSITION 3;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPG POSITION 4;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPG POSITION 5;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLANOPAG POSITION 6;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPFR POSITION 7;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALFR POSITION 8;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFOR POSITION 9;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSE POSITION 10;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSE POSITION 11;

ALTER TABLE CPCOMPRA ALTER COLUMN SERIE POSITION 12;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTM POSITION 13;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTM POSITION 14;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOMOV POSITION 15;

ALTER TABLE CPCOMPRA ALTER COLUMN DOCCOMPRA POSITION 16;

ALTER TABLE CPCOMPRA ALTER COLUMN DTENTCOMPRA POSITION 17;

ALTER TABLE CPCOMPRA ALTER COLUMN DTEMITCOMPRA POSITION 18;

ALTER TABLE CPCOMPRA ALTER COLUMN DTCOMPCOMPRA POSITION 19;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCDESCCOMPRA POSITION 20;

ALTER TABLE CPCOMPRA ALTER COLUMN PERCIPICOMPRA POSITION 21;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPRODCOMPRA POSITION 22;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRLIQCOMPRA POSITION 23;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOMPRA POSITION 24;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCCOMPRA POSITION 25;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRDESCITCOMPRA POSITION 26;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRADICCOMPRA POSITION 27;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSCOMPRA POSITION 28;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEICMSSTCOMPRA POSITION 29;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIPICOMPRA POSITION 30;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEPISCOMPRA POSITION 31;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASECOFINSCOMPRA POSITION 32;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCOMPRA POSITION 33;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSSTCOMPRA POSITION 34;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIPICOMPRA POSITION 35;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRPISCOMPRA POSITION 36;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRCOFINSCOMPRA POSITION 37;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFUNRURALCOMPRA POSITION 38;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRFRETECOMPRA POSITION 39;

ALTER TABLE CPCOMPRA ALTER COLUMN VLROUTRASCOMPRA POSITION 40;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRISENTASCOMPRA POSITION 41;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTC POSITION 42;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTC POSITION 43;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTIPOCOB POSITION 44;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPBO POSITION 45;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALBO POSITION 46;

ALTER TABLE CPCOMPRA ALTER COLUMN CODBANCO POSITION 47;

ALTER TABLE CPCOMPRA ALTER COLUMN IMPNOTACOMPRA POSITION 48;

ALTER TABLE CPCOMPRA ALTER COLUMN BLOQCOMPRA POSITION 49;

ALTER TABLE CPCOMPRA ALTER COLUMN EMMANUT POSITION 50;

ALTER TABLE CPCOMPRA ALTER COLUMN FLAG POSITION 51;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETECOMPRA POSITION 52;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPOFRETECOMPRA POSITION 53;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPSOL POSITION 54;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALSOL POSITION 55;

ALTER TABLE CPCOMPRA ALTER COLUMN CODSOL POSITION 56;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPTN POSITION 57;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALTN POSITION 58;

ALTER TABLE CPCOMPRA ALTER COLUMN CODTRAN POSITION 59;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSERVACAO POSITION 60;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS01 POSITION 61;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS02 POSITION 62;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS03 POSITION 63;

ALTER TABLE CPCOMPRA ALTER COLUMN OBS04 POSITION 64;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICCOMPRA POSITION 65;

ALTER TABLE CPCOMPRA ALTER COLUMN QTDFRETECOMPRA POSITION 66;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICFRETEBASEICMS POSITION 67;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICADICBASEICMS POSITION 68;

ALTER TABLE CPCOMPRA ALTER COLUMN ADICIPIBASEICMS POSITION 69;

ALTER TABLE CPCOMPRA ALTER COLUMN CHAVENFECOMPRA POSITION 70;

ALTER TABLE CPCOMPRA ALTER COLUMN STATUSCOMPRA POSITION 71;

ALTER TABLE CPCOMPRA ALTER COLUMN NRODI POSITION 72;

ALTER TABLE CPCOMPRA ALTER COLUMN DTREGDI POSITION 73;

ALTER TABLE CPCOMPRA ALTER COLUMN LOCDESEMBDI POSITION 74;

ALTER TABLE CPCOMPRA ALTER COLUMN SIGLAUFDESEMBDI POSITION 75;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPAISDESEMBDI POSITION 76;

ALTER TABLE CPCOMPRA ALTER COLUMN DTDESEMBDI POSITION 77;

ALTER TABLE CPCOMPRA ALTER COLUMN IDENTCONTAINER POSITION 78;

ALTER TABLE CPCOMPRA ALTER COLUMN CALCTRIB POSITION 79;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPRM POSITION 80;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALRM POSITION 81;

ALTER TABLE CPCOMPRA ALTER COLUMN TICKET POSITION 82;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCT POSITION 83;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCT POSITION 84;

ALTER TABLE CPCOMPRA ALTER COLUMN NUMCONTA POSITION 85;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPCC POSITION 86;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALCC POSITION 87;

ALTER TABLE CPCOMPRA ALTER COLUMN ANOCC POSITION 88;

ALTER TABLE CPCOMPRA ALTER COLUMN CODCC POSITION 89;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPPN POSITION 90;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALPN POSITION 91;

ALTER TABLE CPCOMPRA ALTER COLUMN CODPLAN POSITION 92;

ALTER TABLE CPCOMPRA ALTER COLUMN INFCOMPL POSITION 93;

ALTER TABLE CPCOMPRA ALTER COLUMN NUMACDRAW POSITION 94;

ALTER TABLE CPCOMPRA ALTER COLUMN TIPODOCIMP POSITION 95;

ALTER TABLE CPCOMPRA ALTER COLUMN SITDOC POSITION 96;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSNFE POSITION 97;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPOP POSITION 98;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALOP POSITION 99;

ALTER TABLE CPCOMPRA ALTER COLUMN CODOP POSITION 100;

ALTER TABLE CPCOMPRA ALTER COLUMN SEQOP POSITION 101;

ALTER TABLE CPCOMPRA ALTER COLUMN CODEMPIM POSITION 102;

ALTER TABLE CPCOMPRA ALTER COLUMN CODFILIALIM POSITION 103;

ALTER TABLE CPCOMPRA ALTER COLUMN CODIMP POSITION 104;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRBASEIICOMPRA POSITION 105;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRIICOMPRA POSITION 106;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSDIFERIDO POSITION 107;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSDEVIDO POSITION 108;

ALTER TABLE CPCOMPRA ALTER COLUMN VLRICMSCREDPRESUM POSITION 109;

ALTER TABLE CPCOMPRA ALTER COLUMN OBSPAG POSITION 110;

ALTER TABLE CPCOMPRA ALTER COLUMN DTINS POSITION 111;

ALTER TABLE CPCOMPRA ALTER COLUMN HINS POSITION 112;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUINS POSITION 113;

ALTER TABLE CPCOMPRA ALTER COLUMN DTALT POSITION 114;

ALTER TABLE CPCOMPRA ALTER COLUMN HALT POSITION 115;

ALTER TABLE CPCOMPRA ALTER COLUMN IDUSUALT POSITION 116;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE CRCHAMADO ALTER COLUMN CODCHAMADO POSITION 3;

ALTER TABLE CRCHAMADO ALTER COLUMN DESCCHAMADO POSITION 4;

ALTER TABLE CRCHAMADO ALTER COLUMN FATOGERADOR POSITION 5;

ALTER TABLE CRCHAMADO ALTER COLUMN AMBIENTE POSITION 6;

ALTER TABLE CRCHAMADO ALTER COLUMN DETCHAMADO POSITION 7;

ALTER TABLE CRCHAMADO ALTER COLUMN OBSCHAMADO POSITION 8;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMPCL POSITION 9;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIALCL POSITION 10;

ALTER TABLE CRCHAMADO ALTER COLUMN CODCLI POSITION 11;

ALTER TABLE CRCHAMADO ALTER COLUMN SOLICITANTE POSITION 12;

ALTER TABLE CRCHAMADO ALTER COLUMN PRIORIDADE POSITION 13;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMPTC POSITION 14;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIALTC POSITION 15;

ALTER TABLE CRCHAMADO ALTER COLUMN CODTPCHAMADO POSITION 16;

ALTER TABLE CRCHAMADO ALTER COLUMN STATUS POSITION 17;

ALTER TABLE CRCHAMADO ALTER COLUMN DTCHAMADO POSITION 18;

ALTER TABLE CRCHAMADO ALTER COLUMN DTPREVISAO POSITION 19;

ALTER TABLE CRCHAMADO ALTER COLUMN QTDHORASPREVISAO POSITION 20;

ALTER TABLE CRCHAMADO ALTER COLUMN DTCONCLUSAO POSITION 21;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMPAE POSITION 22;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIALAE POSITION 23;

ALTER TABLE CRCHAMADO ALTER COLUMN CODATEND POSITION 24;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMPOS POSITION 25;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIALOS POSITION 26;

ALTER TABLE CRCHAMADO ALTER COLUMN TICKET POSITION 27;

ALTER TABLE CRCHAMADO ALTER COLUMN CODITRECMERC POSITION 28;

ALTER TABLE CRCHAMADO ALTER COLUMN CODITOS POSITION 29;

ALTER TABLE CRCHAMADO ALTER COLUMN EMATENDIMENTO POSITION 30;

ALTER TABLE CRCHAMADO ALTER COLUMN CODEMPQL POSITION 31;

ALTER TABLE CRCHAMADO ALTER COLUMN CODFILIALQL POSITION 32;

ALTER TABLE CRCHAMADO ALTER COLUMN CODQUALIFIC POSITION 33;

ALTER TABLE CRCHAMADO ALTER COLUMN EMAILSOLICITANTE POSITION 34;

ALTER TABLE CRCHAMADO ALTER COLUMN DTINS POSITION 35;

ALTER TABLE CRCHAMADO ALTER COLUMN HINS POSITION 36;

ALTER TABLE CRCHAMADO ALTER COLUMN IDUSUINS POSITION 37;

ALTER TABLE CRCHAMADO ALTER COLUMN DTALT POSITION 38;

ALTER TABLE CRCHAMADO ALTER COLUMN HALT POSITION 39;

ALTER TABLE CRCHAMADO ALTER COLUMN IDUSUALT POSITION 40;

ALTER TABLE FNCARTCOB ALTER COLUMN CODEMPBO POSITION 1;

ALTER TABLE FNCARTCOB ALTER COLUMN CODFILIALBO POSITION 2;

ALTER TABLE FNCARTCOB ALTER COLUMN CODBANCO POSITION 3;

ALTER TABLE FNCARTCOB ALTER COLUMN CODEMP POSITION 4;

ALTER TABLE FNCARTCOB ALTER COLUMN CODFILIAL POSITION 5;

ALTER TABLE FNCARTCOB ALTER COLUMN CODCARTCOB POSITION 6;

ALTER TABLE FNCARTCOB ALTER COLUMN VARIACAOCARTCOB POSITION 7;

ALTER TABLE FNCARTCOB ALTER COLUMN DESCCARTCOB POSITION 8;

ALTER TABLE FNCARTCOB ALTER COLUMN CARTCOBCNAB POSITION 9;

ALTER TABLE FNCARTCOB ALTER COLUMN CODCARTCOBCNAB POSITION 10;

ALTER TABLE FNCARTCOB ALTER COLUMN DTINS POSITION 11;

ALTER TABLE FNCARTCOB ALTER COLUMN HINS POSITION 12;

ALTER TABLE FNCARTCOB ALTER COLUMN IDUSUINS POSITION 13;

ALTER TABLE FNCARTCOB ALTER COLUMN DTALT POSITION 14;

ALTER TABLE FNCARTCOB ALTER COLUMN HALT POSITION 15;

ALTER TABLE FNCARTCOB ALTER COLUMN IDUSUALT POSITION 16;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE FNITRECEBER ALTER COLUMN CODREC POSITION 3;

ALTER TABLE FNITRECEBER ALTER COLUMN NPARCITREC POSITION 4;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRITREC POSITION 5;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRDESCITREC POSITION 6;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRMULTAITREC POSITION 7;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRJUROSITREC POSITION 8;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRDEVITREC POSITION 9;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRPARCITREC POSITION 10;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRPAGOITREC POSITION 11;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRAPAGITREC POSITION 12;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRCOMIITREC POSITION 13;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRCANCITREC POSITION 14;

ALTER TABLE FNITRECEBER ALTER COLUMN VLRBASECOMIS POSITION 15;

ALTER TABLE FNITRECEBER ALTER COLUMN DESCPONT POSITION 16;

ALTER TABLE FNITRECEBER ALTER COLUMN DTITREC POSITION 17;

ALTER TABLE FNITRECEBER ALTER COLUMN DTCOMPITREC POSITION 18;

ALTER TABLE FNITRECEBER ALTER COLUMN DTVENCITREC POSITION 19;

ALTER TABLE FNITRECEBER ALTER COLUMN DTPREVITREC POSITION 20;

ALTER TABLE FNITRECEBER ALTER COLUMN DTPAGOITREC POSITION 21;

ALTER TABLE FNITRECEBER ALTER COLUMN DTLIQITREC POSITION 22;

ALTER TABLE FNITRECEBER ALTER COLUMN STATUSITREC POSITION 23;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPPN POSITION 24;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALPN POSITION 25;

ALTER TABLE FNITRECEBER ALTER COLUMN CODPLAN POSITION 26;

ALTER TABLE FNITRECEBER ALTER COLUMN OBSITREC POSITION 27;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCA POSITION 28;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCA POSITION 29;

ALTER TABLE FNITRECEBER ALTER COLUMN NUMCONTA POSITION 30;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPBO POSITION 31;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALBO POSITION 32;

ALTER TABLE FNITRECEBER ALTER COLUMN CODBANCO POSITION 33;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPTC POSITION 34;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALTC POSITION 35;

ALTER TABLE FNITRECEBER ALTER COLUMN CODTIPOCOB POSITION 36;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCB POSITION 37;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCB POSITION 38;

ALTER TABLE FNITRECEBER ALTER COLUMN CODCARTCOB POSITION 39;

ALTER TABLE FNITRECEBER ALTER COLUMN DOCLANCAITREC POSITION 40;

ALTER TABLE FNITRECEBER ALTER COLUMN FLAG POSITION 41;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPCC POSITION 42;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALCC POSITION 43;

ALTER TABLE FNITRECEBER ALTER COLUMN ANOCC POSITION 44;

ALTER TABLE FNITRECEBER ALTER COLUMN CODCC POSITION 45;

ALTER TABLE FNITRECEBER ALTER COLUMN IMPRECIBOITREC POSITION 46;

ALTER TABLE FNITRECEBER ALTER COLUMN RECIBOITREC POSITION 47;

ALTER TABLE FNITRECEBER ALTER COLUMN ALTUSUITREC POSITION 48;

ALTER TABLE FNITRECEBER ALTER COLUMN PDVITREC POSITION 49;

ALTER TABLE FNITRECEBER ALTER COLUMN RECEMCOB POSITION 50;

ALTER TABLE FNITRECEBER ALTER COLUMN DTINIEMCOB POSITION 51;

ALTER TABLE FNITRECEBER ALTER COLUMN DTFIMEMCOB POSITION 52;

ALTER TABLE FNITRECEBER ALTER COLUMN SEQNOSSONUMERO POSITION 53;

ALTER TABLE FNITRECEBER ALTER COLUMN NOSSONUMERO POSITION 54;

ALTER TABLE FNITRECEBER ALTER COLUMN EMMANUT POSITION 55;

ALTER TABLE FNITRECEBER ALTER COLUMN CODEMPSN POSITION 56;

ALTER TABLE FNITRECEBER ALTER COLUMN CODFILIALSN POSITION 57;

ALTER TABLE FNITRECEBER ALTER COLUMN CODSINAL POSITION 58;

ALTER TABLE FNITRECEBER ALTER COLUMN MULTIBAIXA POSITION 59;

ALTER TABLE FNITRECEBER ALTER COLUMN DTINS POSITION 60;

ALTER TABLE FNITRECEBER ALTER COLUMN HINS POSITION 61;

ALTER TABLE FNITRECEBER ALTER COLUMN IDUSUINS POSITION 62;

ALTER TABLE FNITRECEBER ALTER COLUMN DTALT POSITION 63;

ALTER TABLE FNITRECEBER ALTER COLUMN HALT POSITION 64;

ALTER TABLE FNITRECEBER ALTER COLUMN IDUSUALT POSITION 65;

ALTER TABLE RHTURNO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE RHTURNO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE RHTURNO ALTER COLUMN CODTURNO POSITION 3;

ALTER TABLE RHTURNO ALTER COLUMN DESCTURNO POSITION 4;

ALTER TABLE RHTURNO ALTER COLUMN NHSTURNO POSITION 5;

ALTER TABLE RHTURNO ALTER COLUMN TIPOTURNO POSITION 6;

ALTER TABLE RHTURNO ALTER COLUMN HINITURNO POSITION 7;

ALTER TABLE RHTURNO ALTER COLUMN HFIMTURNO POSITION 8;

ALTER TABLE RHTURNO ALTER COLUMN HINIINTTURNO POSITION 9;

ALTER TABLE RHTURNO ALTER COLUMN HFIMINTTURNO POSITION 10;

ALTER TABLE RHTURNO ALTER COLUMN TOLENTRADA POSITION 11;

ALTER TABLE RHTURNO ALTER COLUMN TOLSAIDA POSITION 12;

ALTER TABLE RHTURNO ALTER COLUMN TRABSABTURNO POSITION 13;

ALTER TABLE RHTURNO ALTER COLUMN TRABDOMTURNO POSITION 14;

ALTER TABLE RHTURNO ALTER COLUMN PERCBHTBSABTURNO POSITION 15;

ALTER TABLE RHTURNO ALTER COLUMN PERCBHTBDOMTURNO POSITION 16;

ALTER TABLE RHTURNO ALTER COLUMN PERCBHTBFERTURNO POSITION 17;

ALTER TABLE RHTURNO ALTER COLUMN DTINS POSITION 18;

ALTER TABLE RHTURNO ALTER COLUMN HINS POSITION 19;

ALTER TABLE RHTURNO ALTER COLUMN IDUSUINS POSITION 20;

ALTER TABLE RHTURNO ALTER COLUMN DTALT POSITION 21;

ALTER TABLE RHTURNO ALTER COLUMN HALT POSITION 22;

ALTER TABLE RHTURNO ALTER COLUMN IDUSUALT POSITION 23;

ALTER TABLE SGFERIADO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGFERIADO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGFERIADO ALTER COLUMN DATAFER POSITION 3;

ALTER TABLE SGFERIADO ALTER COLUMN DESCFER POSITION 4;

ALTER TABLE SGFERIADO ALTER COLUMN BANCFER POSITION 5;

ALTER TABLE SGFERIADO ALTER COLUMN TRABFER POSITION 6;

ALTER TABLE SGFERIADO ALTER COLUMN OPTFER POSITION 7;

ALTER TABLE SGFERIADO ALTER COLUMN DTINS POSITION 8;

ALTER TABLE SGFERIADO ALTER COLUMN HINS POSITION 9;

ALTER TABLE SGFERIADO ALTER COLUMN IDUSUINS POSITION 10;

ALTER TABLE SGFERIADO ALTER COLUMN DTALT POSITION 11;

ALTER TABLE SGFERIADO ALTER COLUMN HALT POSITION 12;

ALTER TABLE SGFERIADO ALTER COLUMN IDUSUALT POSITION 13;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODEMPBO POSITION 3;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODFILIALBO POSITION 4;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODBANCO POSITION 5;

ALTER TABLE SGITPREFERE6 ALTER COLUMN TIPOFEBRABAN POSITION 6;

ALTER TABLE SGITPREFERE6 ALTER COLUMN MDECOB POSITION 7;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODCONV POSITION 8;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CONVCOB POSITION 9;

ALTER TABLE SGITPREFERE6 ALTER COLUMN VERLAYOUT POSITION 10;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDENTSERV POSITION 11;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CONTACOMPR POSITION 12;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDENTAMBCLI POSITION 13;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDENTAMBBCO POSITION 14;

ALTER TABLE SGITPREFERE6 ALTER COLUMN NROSEQ POSITION 15;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODEMPCA POSITION 16;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODFILIALCA POSITION 17;

ALTER TABLE SGITPREFERE6 ALTER COLUMN NUMCONTA POSITION 18;

ALTER TABLE SGITPREFERE6 ALTER COLUMN FORCADTIT POSITION 19;

ALTER TABLE SGITPREFERE6 ALTER COLUMN TIPODOC POSITION 20;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDENTEMITBOL POSITION 21;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDENTDISTBOL POSITION 22;

ALTER TABLE SGITPREFERE6 ALTER COLUMN ESPECTIT POSITION 23;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODJUROS POSITION 24;

ALTER TABLE SGITPREFERE6 ALTER COLUMN VLRPERCJUROS POSITION 25;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODDESC POSITION 26;

ALTER TABLE SGITPREFERE6 ALTER COLUMN VLRPERCDESC POSITION 27;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODPROT POSITION 28;

ALTER TABLE SGITPREFERE6 ALTER COLUMN DIASPROT POSITION 29;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CODBAIXADEV POSITION 30;

ALTER TABLE SGITPREFERE6 ALTER COLUMN DIASBAIXADEV POSITION 31;

ALTER TABLE SGITPREFERE6 ALTER COLUMN ACEITE POSITION 32;

ALTER TABLE SGITPREFERE6 ALTER COLUMN PADRAOCNAB POSITION 33;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CAMINHOREMESSA POSITION 34;

ALTER TABLE SGITPREFERE6 ALTER COLUMN CAMINHORETORNO POSITION 35;

ALTER TABLE SGITPREFERE6 ALTER COLUMN BACKUPREMESSA POSITION 36;

ALTER TABLE SGITPREFERE6 ALTER COLUMN BACKUPRETORNO POSITION 37;

ALTER TABLE SGITPREFERE6 ALTER COLUMN DTINS POSITION 38;

ALTER TABLE SGITPREFERE6 ALTER COLUMN HINS POSITION 39;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDUSUINS POSITION 40;

ALTER TABLE SGITPREFERE6 ALTER COLUMN DTALT POSITION 41;

ALTER TABLE SGITPREFERE6 ALTER COLUMN HALT POSITION 42;

ALTER TABLE SGITPREFERE6 ALTER COLUMN IDUSUALT POSITION 43;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPMAIL POSITION 3;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPSSLMAIL POSITION 4;

ALTER TABLE SGPREFERE3 ALTER COLUMN SMTPAUTMAIL POSITION 5;

ALTER TABLE SGPREFERE3 ALTER COLUMN PORTAMAIL POSITION 6;

ALTER TABLE SGPREFERE3 ALTER COLUMN USERMAIL POSITION 7;

ALTER TABLE SGPREFERE3 ALTER COLUMN PASSMAIL POSITION 8;

ALTER TABLE SGPREFERE3 ALTER COLUMN ENDMAIL POSITION 9;

ALTER TABLE SGPREFERE3 ALTER COLUMN AUTOHORATEND POSITION 10;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPCE POSITION 11;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALCE POSITION 12;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATIVCE POSITION 13;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPTE POSITION 14;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALTE POSITION 15;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATIVTE POSITION 16;

ALTER TABLE SGPREFERE3 ALTER COLUMN BLOQATENDCLIATRASO POSITION 17;

ALTER TABLE SGPREFERE3 ALTER COLUMN MOSTRACLIATRASO POSITION 18;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPNC POSITION 19;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALNC POSITION 20;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILNC POSITION 21;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPAO POSITION 22;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALAO POSITION 23;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODATENDO POSITION 24;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTINS POSITION 25;

ALTER TABLE SGPREFERE3 ALTER COLUMN HINS POSITION 26;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUINS POSITION 27;

ALTER TABLE SGPREFERE3 ALTER COLUMN DTALT POSITION 28;

ALTER TABLE SGPREFERE3 ALTER COLUMN HALT POSITION 29;

ALTER TABLE SGPREFERE3 ALTER COLUMN IDUSUALT POSITION 30;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEC POSITION 31;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEC POSITION 32;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEC POSITION 33;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMPEA POSITION 34;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODFILIALEA POSITION 35;

ALTER TABLE SGPREFERE3 ALTER COLUMN CODEMAILEA POSITION 36;

ALTER TABLE VDCONTRATO ALTER COLUMN CODEMP POSITION 1;

ALTER TABLE VDCONTRATO ALTER COLUMN CODFILIAL POSITION 2;

ALTER TABLE VDCONTRATO ALTER COLUMN CODCONTR POSITION 3;

ALTER TABLE VDCONTRATO ALTER COLUMN DESCCONTR POSITION 4;

ALTER TABLE VDCONTRATO ALTER COLUMN MINUTACONTR POSITION 5;

ALTER TABLE VDCONTRATO ALTER COLUMN CODEMPCL POSITION 6;

ALTER TABLE VDCONTRATO ALTER COLUMN CODFILIALCL POSITION 7;

ALTER TABLE VDCONTRATO ALTER COLUMN CODCLI POSITION 8;

ALTER TABLE VDCONTRATO ALTER COLUMN DTINICIO POSITION 9;

ALTER TABLE VDCONTRATO ALTER COLUMN DTFIM POSITION 10;

ALTER TABLE VDCONTRATO ALTER COLUMN TPCOBCONTR POSITION 11;

ALTER TABLE VDCONTRATO ALTER COLUMN DIAVENCCONTR POSITION 12;

ALTER TABLE VDCONTRATO ALTER COLUMN DIAFECHCONTR POSITION 13;

ALTER TABLE VDCONTRATO ALTER COLUMN TPCONTR POSITION 14;

ALTER TABLE VDCONTRATO ALTER COLUMN ATIVO POSITION 15;

ALTER TABLE VDCONTRATO ALTER COLUMN DTINS POSITION 16;

ALTER TABLE VDCONTRATO ALTER COLUMN HINS POSITION 17;

ALTER TABLE VDCONTRATO ALTER COLUMN IDUSUINS POSITION 18;

ALTER TABLE VDCONTRATO ALTER COLUMN DTALT POSITION 19;

ALTER TABLE VDCONTRATO ALTER COLUMN HALT POSITION 20;

ALTER TABLE VDCONTRATO ALTER COLUMN IDUSUALT POSITION 21;

/* Create(Add) Crant */
GRANT DELETE, INSERT, SELECT, UPDATE ON RHEXPEDIENTE TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON RHEXPEDMES TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON SGFERIADO TO ADM;

GRANT DELETE, INSERT, SELECT, UPDATE ON VDITVENDAVDITCONTR TO ADM;

GRANT EXECUTE ON PROCEDURE GERAEXPEDIENTESP TO ADM;

GRANT SELECT ON ATATENDIMENTOVW01 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW02 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW03 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW04 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW05 TO ADM;

GRANT SELECT ON ATATENDIMENTOVW06 TO ADM;

commit;



