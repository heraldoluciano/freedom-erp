/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.gms <BR>
 * Classe:
 * @(#)FreedomGMS.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal para o módulo de gestão de materiais.
 *  
 */

package org.freedom.modulos.gms;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.cfg.FBairro;
import org.freedom.modulos.cfg.FMunicipio;
import org.freedom.modulos.cfg.FPais;
import org.freedom.modulos.cfg.FUF;
import org.freedom.modulos.std.FPrefereGeral;
import org.freedom.modulos.std.view.frame.comum.FAlmox;
import org.freedom.modulos.std.view.frame.comum.FEstacao;
import org.freedom.modulos.std.view.frame.comum.FFrete;
import org.freedom.modulos.std.view.frame.comum.FImpressora;
import org.freedom.modulos.std.view.frame.comum.FInventario;
import org.freedom.modulos.std.view.frame.comum.FMarca;
import org.freedom.modulos.std.view.frame.comum.FModEtiqueta;
import org.freedom.modulos.std.view.frame.comum.FModNota;
import org.freedom.modulos.std.view.frame.comum.FNatoPer;
import org.freedom.modulos.std.view.frame.comum.FPapel;
import org.freedom.modulos.std.view.frame.comum.FSerie;
import org.freedom.modulos.std.view.frame.comum.FTipoCob;
import org.freedom.modulos.std.view.frame.comum.FTipoFor;
import org.freedom.modulos.std.view.frame.comum.FUnidade;
import org.freedom.modulos.std.view.frame.comum.FVariantes;
import org.freedom.modulos.std.view.frame.detail.FCompra;
import org.freedom.modulos.std.view.frame.detail.FConhecFrete;
import org.freedom.modulos.std.view.frame.detail.FModGrade;
import org.freedom.modulos.std.view.frame.detail.FOrcamento;
import org.freedom.modulos.std.view.frame.detail.FPlanoPag;
import org.freedom.modulos.std.view.frame.detail.FSimilar;
import org.freedom.modulos.std.view.frame.detail.FVenda;
import org.freedom.modulos.std.view.frame.report.FRCodbarProd;
import org.freedom.modulos.std.view.frame.report.FRCodficProd;
import org.freedom.modulos.std.view.frame.report.FRCompras;
import org.freedom.modulos.std.view.frame.report.FRComprasFor;
import org.freedom.modulos.std.view.frame.report.FRConfEstoq;
import org.freedom.modulos.std.view.frame.report.FRContaEstoque;
import org.freedom.modulos.std.view.frame.report.FRCpItem;
import org.freedom.modulos.std.view.frame.report.FRCpProd;
import org.freedom.modulos.std.view.frame.report.FRCpTipoMov;
import org.freedom.modulos.std.view.frame.report.FRDemanda;
import org.freedom.modulos.std.view.frame.report.FREstoqueLiquido;
import org.freedom.modulos.std.view.frame.report.FREstoqueMin;
import org.freedom.modulos.std.view.frame.report.FREtiqueta;
import org.freedom.modulos.std.view.frame.report.FREvoluVendas;
import org.freedom.modulos.std.view.frame.report.FRGiroEstoque;
import org.freedom.modulos.std.view.frame.report.FRInvPeps;
import org.freedom.modulos.std.view.frame.report.FRMediaItem;
import org.freedom.modulos.std.view.frame.report.FRMovProd;
import org.freedom.modulos.std.view.frame.report.FRMovProdCont;
import org.freedom.modulos.std.view.frame.report.FRProdGrup;
import org.freedom.modulos.std.view.frame.report.FRResumoDiario;
import org.freedom.modulos.std.view.frame.report.FRSaldoLote;
import org.freedom.modulos.std.view.frame.report.FRUltimaVenda;
import org.freedom.modulos.std.view.frame.report.FRVencLote;
import org.freedom.modulos.std.view.frame.report.FRVendaSetor;
import org.freedom.modulos.std.view.frame.report.FRVendasDet;
import org.freedom.modulos.std.view.frame.report.FRVendasFisico;
import org.freedom.modulos.std.view.frame.report.FRVendasGeral;
import org.freedom.modulos.std.view.frame.report.FRVendasItem;
import org.freedom.modulos.std.view.frame.report.FRomaneio;
import org.freedom.modulos.std.view.frame.special.FDevolucao;
import org.freedom.modulos.std.view.frame.special.FGrade;
import org.freedom.modulos.std.view.frame.special.FGrupo;
import org.freedom.modulos.std.view.frame.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.tabbed.FFornecedor;
import org.freedom.modulos.std.view.frame.tabbed.FTransp;
import org.freedom.modulos.std.view.frame.tools.FAprovCancOrc;
import org.freedom.modulos.std.view.frame.tools.FCancVenda;
import org.freedom.modulos.std.view.frame.tools.FConsPreco;
import org.freedom.modulos.std.view.frame.tools.FConsulta;
import org.freedom.modulos.std.view.frame.tools.FImpTabFor;
import org.freedom.modulos.std.view.frame.tools.FKardex;
import org.freedom.modulos.std.view.frame.tools.FPesquisaOrc;
import org.freedom.modulos.std.view.frame.tools.FProcessaEQ;
import org.freedom.modulos.std.view.frame.tools.FStatusItOrc;

public class FreedomGMS extends AplicativoPD {

	public FreedomGMS() {
		super("icongms.png", "splashGMS.png",	1, "Freedom", 8, "Gestão de Materiais e Serviços", null, new FPrincipalPD(null, "bgFreedom2.jpg"),LoginPD.class);

		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,null);
			addOpcao(100000000, TP_OPCAO_MENU, "Cadastros", "", 'T', 100100000, 1,false, null);
				addOpcao(100100000, TP_OPCAO_MENU, "Clientes", "", 'C', 100101000, 2,false, null);
					addOpcao(100101000, TP_OPCAO_ITEM, "Clientes", "Clientes", 'C',	100101010, 3, true, FCliente.class);
				addSeparador(100100000);
				addOpcao(100100000, TP_OPCAO_MENU, "Fornecedores", "", 'C', 100102000,2, false, null);
					addOpcao(100102000, TP_OPCAO_ITEM, "Tipos de fornecedores", "TipoFor",'e', 100102010, 3, true, FTipoFor.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Fornecedores", "Fornecedor", 'r',100102020, 3, true, FFornecedor.class);
				addSeparador(100102000);
				addOpcao(100100000, TP_OPCAO_MENU, "Produtos", "", 'u', 100103000, 2,false, null);
					addOpcao(100103000, TP_OPCAO_ITEM, "Almoxarifados", "Almoxarifado",'x', 100103030, 3, true, FAlmox.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'r', 100103040,3, true, FGrupo.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Seções", "Seções", 'e', 100103091,3, true, FSecaoProd.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Marcas", "Marcas", 'c', 100103050,3, true, FMarca.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Unidades", "Unidades", 'U',100103060, 3, true, FUnidade.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Kits de produtos","Kits de produtos", 'K', 100103070, 3, true, FGrupo.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Similaridade","Similar",'S',100103080,3, true, FSimilar.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Produtos", "Produtos", 'P',100103090, 3, true, FProduto.class);
					addSeparador(100100000);
					addOpcao(100100000, TP_OPCAO_MENU, "Grade de produtos", "", 'G',100103100, 3, false, null);
						addOpcao(100103100, TP_OPCAO_ITEM, "Variantes", "Variantes", 'V',100103101, 4, true, FVariantes.class);
						addOpcao(100103100, TP_OPCAO_ITEM, "Modelos", "Modelo de Grade", 'M',100103102, 4, true, FModGrade.class);
						addOpcao(100103100, TP_OPCAO_ITEM, "Grades", "Grade", 'r', 100103103,4, true, FGrade.class);
				addSeparador(100100000);
				addOpcao(100100000, TP_OPCAO_MENU, "Outros cadastros", "", 'C',100104000, 2, false, null);
					addOpcao(100104000, TP_OPCAO_ITEM, "Transportadoras", "Transportadora",'p', 100104010, 3, true, FTransp.class);
					addSeparador(100104000);
					addOpcao(100104000, TP_OPCAO_ITEM, "Tipo de cobrança", "TipoCob", 'o',100104020, 3, true, FTipoCob.class);
					addOpcao(100104000, TP_OPCAO_ITEM, "Plano de pagamento", "PlanoPag",'s', 100104030, 3, true, FPlanoPag.class);
					addSeparador(100104000);
					addOpcao(100104000, TP_OPCAO_ITEM, "Natureza de operação", "Naturezas",'z', 100104040, 3, true, FNatoPer.class);
					addSeparador(100104000);
				addOpcao(100100000, TP_OPCAO_MENU, "Atribuições", "", 't', 100105000, 3, false, null);
					addOpcao(100105000, TP_OPCAO_ITEM, "Atribuições", "Atribuição", 'r', 100105010, 4, true, FAtribuicao.class);
					addOpcao(100105000, TP_OPCAO_ITEM, "Atribuições por usuário","Atribuição por usuário", 'u', 100105020, 4, true,FAtribUsu.class);

				addOpcao( 100100000, TP_OPCAO_MENU, "Tabela Geográficas", "", 'C', 100106000, 2, false, null );					
					addOpcao( 100106000, TP_OPCAO_ITEM, "Paises", "Paises", 'P', 100106010, 3, true, FPais.class );
					addOpcao( 100106000, TP_OPCAO_ITEM, "Cidades", "Cidades", 'd', 100106020, 3, true, FMunicipio.class );
					addOpcao( 100106000, TP_OPCAO_ITEM, "Estados", "Estados", 'E', 100106030, 3, true, FUF.class );
					addOpcao( 100106000, TP_OPCAO_ITEM, "Bairros", "Bairros", 'B', 100106040, 3, true, FBairro.class );
					
			addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000,1, false, null);
				addOpcao(100200000, TP_OPCAO_MENU, "Etiquetas", "", 't', 100201000, 2,false, null);
					addOpcao(100201000, TP_OPCAO_ITEM, "Modelo", "Modelo de etiquetas",'M', 100201010, 3, true, FModEtiqueta.class);
					addOpcao(100201000, TP_OPCAO_ITEM, "Imprimir", "Etiquetas", 'I',100201020, 3, true, FREtiqueta.class); // LOM
				addSeparador(100200000);
				addOpcao(100200000, TP_OPCAO_ITEM, "Imp. tabelas de fornecedores","Imp. tabelas de fornecedores", 'I', 100202000, 2, true,FImpTabFor.class);
				addSeparador(100200000);
				addOpcao(100200000, TP_OPCAO_ITEM, "Ajuste do item do orçamento","Ajuste do item do orçamento", 'A', 100203000, 2, true,FStatusItOrc.class);
	
			addOpcao(100000000, TP_OPCAO_MENU, "Configurações", "", 'C', 100300000,1, false, null);
				addOpcao(100300000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I',100301000, 2, true, FImpressora.class);
				addOpcao(100300000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100302000,2, true, FPapel.class);
				addOpcao(100300000, TP_OPCAO_ITEM, "Estação", "FEstacao", 'E',100303000, 2, true, FEstacao.class);// lom
				addSeparador(100300000);
				addOpcao(100300000, TP_OPCAO_MENU, "Preferências", "", 'P', 100304000,2, false, null);
   					addOpcao(100304000, TP_OPCAO_ITEM, "Preferências Gerais","Preferências Gerais", 'G', 100304010, 3, true, FPrefereGeral.class);
   					addOpcao(100304000, TP_OPCAO_ITEM, "Preferências GMS","Preferências GMS", 'S', 100304020, 3, true, FPrefereGMS.class);
   					addOpcao(100304000, TP_OPCAO_ITEM, "Série de NFs", "", 'N', 100304030,3, true, FSerie.class);
					addOpcao(100304000, TP_OPCAO_ITEM, "Modelo de NFs", "Modelo NF", 'M',100304040, 3, true, FModNota.class);

		addOpcao(-1, TP_OPCAO_MENU, "Entrada", "", 'E', 200000000, 0, false,null);
			addOpcao(200000000, TP_OPCAO_ITEM, "Solicitação de Compra",	"Solicitação de Compra", 'S', 200100000, 1, true,FSolicitacaoCompra.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa Solicitações de Compra","Pesquisa Solicitações de Compra", 'P', 200300000, 1, true,FConsSol.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa Compra","Pesquisa Compra", 'P', 200400000, 1, true,FConsCompra.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Sumário de Solicitações de Compra","Sumário de Solicitações de Compra", 'M', 200300010, 1, true,FConsSolItem.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Cotação Sumarizada de Preços","Cotação Sumarizada de Preços", 'Z', 200300020, 1, true,FCotacaoItens.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Cotação de Preços",	"Cotação de Preços", 'T', 200400000, 1, true,FCotacaoPrecos.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Compra", "Compra", 'C', 200600000, 1,true, FCompra.class);
			addOpcao( 200000000, TP_OPCAO_ITEM, "Conhecimento de Frete", "Conhecimento de Frete", 'F', 200800000, 1, true, FConhecFrete.class );			
						
			addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200700000, 1,false, null);
				addOpcao(200700000, TP_OPCAO_ITEM, "Compras por fornecedor","Compras por Fornecedor", 'F', 200701000, 2, true,FRComprasFor.class);
				addOpcao( 200700000, TP_OPCAO_ITEM, "Compras geral", "Compras geral", 'p', 200702000, 2, true, FRCompras.class );
				addOpcao( 200700000, TP_OPCAO_ITEM, "Compras por tipo de movimento ", "Compras por tipo de movimento", 'p', 200703000, 2, true, FRCpTipoMov.class );
				addOpcao( 200700000, TP_OPCAO_ITEM, "Compras por ítem ", "Compras por ítem", 'p', 200704000, 2, true, FRCpItem.class );
				
		addOpcao(-1, TP_OPCAO_MENU, "Saída", "", 'S', 300000000, 0, false, null);
			addOpcao(300000000, TP_OPCAO_ITEM, "Venda", "Venda", 'V', 300100000, 1,true, FVenda.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Cancela venda", "Cancelamento",'C', 300200000, 1, true, FCancVenda.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Devolução de vendas","Devolução de vendas", 'D', 300300000, 1, true,FDevolucao.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Lançamento de Frete","Lançamento de Frete", 'L', 300400000, 1, true, FFrete.class);
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_ITEM, "Aprova orçamento","Aprova Orçamento", 'A', 300500000, 1, true, FAprovCancOrc.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Orçamento", "Orçamento", 'O',300600000, 1, true, FOrcamento.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Pesquisa Orçamento","Pesquisa Orçamento", 'P', 300700000, 1, true, FPesquisaOrc.class);	
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_ITEM, "Romaneio", "Romaneio", 'R',300800000, 1, true, FRomaneio.class);
			addSeparador(300000000);			
			addOpcao(300000000, TP_OPCAO_ITEM, "Requisição de material", "Requisição de material", 'm',300900000, 1, true, FRma.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Pesquisa requisição de material", "Pesquisa requisição de material", 'm',301300000, 1, true, FConsRMA.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Pesquisa item de Rma", "Pesquisa item de requisição de material", 'i',301400000, 1, true, FConsRmaItem.class);
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_MENU, "Listagens", "", 's', 301000000, 1,false, null);
				addOpcao(301000000, TP_OPCAO_ITEM, "Resumo diário", "Resumo Diário",'R', 301001000, 2, true, FRResumoDiario.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Vendas geral", "Vendas em Geral",'V', 301002000, 2, true, FRVendasGeral.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Vendas físico", "Físico de Vendas",	'd', 301003000, 2, true, FRVendasFisico.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Vendas detalhado","Vendas Detalhadas", 'n', 301004000, 2, true, FRVendasDet.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Vendas por ítem","Vendas por Item", 'e', 301005000, 2, true, FRVendasItem.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Média de vendas por ítem","Media de vendas por item", 'o', 301006000, 2, true,FRMediaItem.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Ultimas Vendas por Cliente", "",'C', 301007000, 2, true, FRUltimaVenda.class);
				addOpcao(301000000, TP_OPCAO_ITEM, "Vendas por Setor","Vendas por Setor", 't', 301008000, 2, true, FRVendaSetor.class);
			addOpcao(300000000, TP_OPCAO_MENU, "Gráficos", "", 'G', 301100000, 1,false, null);
				addOpcao(301100000, TP_OPCAO_ITEM, "Evolução de vendas","Evolução de vendas", 'E', 301101000, 2, true,FREvoluVendas.class);	
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_MENU, "Consultas", "", 'n', 301200000, 1,false, null);
				addOpcao(301200000, TP_OPCAO_ITEM, "Preços", "Consulta de preços", 'P',	301201000, 2, true, FConsPreco.class);

		addOpcao(-1, TP_OPCAO_MENU, "Estoque", "", 'E', 400000000, 0, false,null);
			addOpcao(400000000, TP_OPCAO_ITEM, "Kardex", "Kardex", 'K', 400100000,1, true, FKardex.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Inventário", "Inventário", 'I',	400200000, 1, true, FInventario.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Consulta estoque", "Consulta", 'C',	400300000, 1, true, FConsulta.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Consulta produto","Consulta produto", 'P', 400400000, 1, true, FConsPreco.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Tipos de movimentos","Tipo de Movimento", 'T', 400500000, 1, true, FTipoMov.class);
			addSeparador(400000000);
			addOpcao(400000000, TP_OPCAO_ITEM, "Reprocessa estoque","Reprocessa estoque", 'R', 400600000, 1, true,FProcessaEQ.class);
			addSeparador(400000000);
			addOpcao(400000000, TP_OPCAO_MENU, "Listagens", "", 'L', 400700000, 1,false, null);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Estoque mínimo", "Estoque Mínimo",'s', 400701000, 2, true, FREstoqueMin.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Produtos/Movimentos","Listagem de Produtos", 'P', 400702000, 2, true,FRMovProd.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Vencimentos de lote","Vencimento Lote", 'V', 400703000, 2, true, FRVencLote.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Saldos de lote", "Saldos de Lote",'l', 400704000, 2, true, FRSaldoLote.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Demanda", "Demanda", 'D',400705000, 2, true, FRDemanda.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Conferência","Conferência de Estoque", 'C', 400706000, 2, true,FRConfEstoq.class);
				addOpcao( 400700000, TP_OPCAO_ITEM, "Inventário","Inventário", 'I', 400707000, 2, true, FRInvPeps.class);
				
				addOpcao( 400700000, TP_OPCAO_ITEM, "Codifição de produto", "Codificação de produto", 'P', 400700800, 2, true, FRCodficProd.class );
				addOpcao( 400700000, TP_OPCAO_ITEM, "Etiquetas de código de barras", "Etiquetas de código de barras", 'E', 400700900, 2, true, FRCodbarProd.class );
				addOpcao( 400700000, TP_OPCAO_ITEM, "Ultimas compras/produto", "Ultimas compras/produto", 'E', 400701000, 2, true, FRCpProd.class );
				addOpcao( 400700000, TP_OPCAO_ITEM, "Movimentação de Produto Controlado", "Movimentação de Produto Controlado", 'M', 400702000, 2, true, FRMovProdCont.class );
				addOpcao( 400700000, TP_OPCAO_ITEM, "Estoque liquido", "Estoque liquido", 'L', 400703000, 2, true, FREstoqueLiquido.class );
				addOpcao( 400700000, TP_OPCAO_ITEM, "Produtos por grupo", "Produtos pro grupo",'G', 400704000, 2, true, FRProdGrup.class ) ;
				addOpcao( 400700000, TP_OPCAO_ITEM, "Giro de estoque", "Giro de estoque",'i', 400705000, 2, true, FRGiroEstoque.class ) ;
				addOpcao( 400700000, TP_OPCAO_ITEM, "Contagem de estoque", "Contagem de estoque",'s', 700706000, 2, true, FRContaEstoque.class ) ;				
				addOpcao( 400700000, TP_OPCAO_ITEM, "Valor em estoque", "Valor em estoque",'v', 400707000, 2, true, FRValorEstoque.class ) ;

		addOpcao(-1, TP_OPCAO_MENU, "Recepção", "", 'R', 500000000, 0, false,null);	
				addOpcao(500000000, TP_OPCAO_ITEM, "Painel de controle", "Painel de Controle", 'P', 500100000,1, true, FControleRecMerc.class);		
				addSeparador(500000000);
				addOpcao(500000000, TP_OPCAO_ITEM, "Recepção de matéria prima", "Recepção de matéria prima", 'R', 500200000,1, true, FRecMerc.class);
				addOpcao(500000000, TP_OPCAO_ITEM, "Coleta de materiais", "Coleta de materiais", 'C', 500400000,1, true, FColeta.class);
				addSeparador(500000000);
				addOpcao(500000000, TP_OPCAO_ITEM, "Tipos de Recepção", "Cadastro de tipos de recepção de mercadorias", 'T', 500300000,1, true, FTipoRecMerc.class);
				addSeparador(500000000);
				addOpcao(500000000, TP_OPCAO_MENU, "Listagens", "", 'L', 500500000, 1,false, null);
					addOpcao( 500500000, TP_OPCAO_ITEM, "Coletas por dia", "Coletas por dia",'o', 500501000, 2, true, FRColetas.class);
			
				
		addOpcao(-1, TP_OPCAO_MENU, "Expedição", "", 'x', 600000000, 0, false,null);
				
				addBotao( "btPrefere.png", "Preferências gerais", "Preferências Gerais", 100304010, FPrefereGeral.class );
				addBotao("btCliente.gif","Cliente","Clientes", 100101010, FCliente.class);
				addBotao("btForneced.gif","Fornecedor","Fornecedor",100102020,FFornecedor.class);
				addBotao("btRma.gif","Requisição de material", "Requisição de material", 300900000,FRma.class);
				addBotao("btsoliccp.gif","Solicitação de Compra","Solicitação de Compra",200100000, FSolicitacaoCompra.class);	
				
				addBotao("btEntrada.png","Compra","Compras", 200600000, FCompra.class);
				
				addBotao( "btTransp.png", "Transportadora", "Transportadora", 100117000, FTransp.class );
				addBotao( "btConFrete.png", "Conhecimento de Frete", "Conhecimento de Frete", 200300000, FConhecFrete.class  );
				
				addBotao("btEstoque.gif","Consulta estoque","Consulta", 400300000, FConsulta.class);   
				addBotao("btProduto.gif","Cadastro de produtos","Produtos", 100103090, FProduto.class);
				addBotao("btSimilar.gif","Cadastro de similaridades","Similaridade", 100103080, FSimilar.class);
				addBotao("btOrcamento.png", "Orçamento", "Orcamento", 300600000, FOrcamento.class);
				addBotao("btConsOrcamento.gif", "Pesquisa Orçamentos", "Pesquisa Orcamentos", 300700000, FPesquisaOrc.class);
				addBotao("btAprovaOrc.gif", "Aprovações de Orçamentos", "Aprova Orcamento", 300500000, FAprovCancOrc.class);
				
				addBotao("btColeta.png", "Coleta de materiais", "Coleta de Materiais", 500400000, FColeta.class );
				addBotao("btRecMatPrim.png", "Painel de Controle", "Painel de Controle", 300500000, FControleRecMerc.class);
				
				
		
		ajustaMenu();
		
		nomemodulo = "Gestão de Materiais e Serviços";

	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomGMS freedom = new FreedomGMS();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}