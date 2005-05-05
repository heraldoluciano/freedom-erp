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
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
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
import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FAprovaOrc;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FCancVenda;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FCompra;
import org.freedom.modulos.std.FConsOrc;
import org.freedom.modulos.std.FConsPreco;
import org.freedom.modulos.std.FConsulta;
import org.freedom.modulos.std.FDevolucao;
import org.freedom.modulos.std.FEstacao;
import org.freedom.modulos.std.FFornecedor;
import org.freedom.modulos.std.FFrete;
import org.freedom.modulos.std.FGrade;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FImpTabFor;
import org.freedom.modulos.std.FImpressora;
import org.freedom.modulos.std.FInventario;
import org.freedom.modulos.std.FKardex;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FModEtiqueta;
import org.freedom.modulos.std.FModGrade;
import org.freedom.modulos.std.FModNota;
import org.freedom.modulos.std.FNatoPer;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.modulos.std.FPapel;
import org.freedom.modulos.std.FPlanoPag;
import org.freedom.modulos.std.FPrefereGeral;
import org.freedom.modulos.std.FProcessaEQ;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FRComprasFor;
import org.freedom.modulos.std.FRConfEstoq;
import org.freedom.modulos.std.FRDemanda;
import org.freedom.modulos.std.FREstoqueMin;
import org.freedom.modulos.std.FREtiqueta;
import org.freedom.modulos.std.FREvoluVendas;
import org.freedom.modulos.std.FRInvPeps;
import org.freedom.modulos.std.FRMediaItem;
import org.freedom.modulos.std.FRMovProd;
import org.freedom.modulos.std.FRResumoDiario;
import org.freedom.modulos.std.FRSaldoLote;
import org.freedom.modulos.std.FRUltimaVenda;
import org.freedom.modulos.std.FRVencLote;
import org.freedom.modulos.std.FRVendaSetor;
import org.freedom.modulos.std.FRVendasDet;
import org.freedom.modulos.std.FRVendasFisico;
import org.freedom.modulos.std.FRVendasGeral;
import org.freedom.modulos.std.FRVendasItem;
import org.freedom.modulos.std.FRomaneio;
import org.freedom.modulos.std.FSerie;
import org.freedom.modulos.std.FSimilar;
import org.freedom.modulos.std.FStatusItOrc;
import org.freedom.modulos.std.FTipoCob;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FTipoMov;
import org.freedom.modulos.std.FTransp;
import org.freedom.modulos.std.FTratTrib;
import org.freedom.modulos.std.FUnidade;
import org.freedom.modulos.std.FVariantes;
import org.freedom.modulos.std.FVenda;
import org.freedom.telas.Aplicativo;

public class FreedomGMS extends Aplicativo {

	public FreedomGMS() {
		super("iconAtendimento32.gif", "splashGMS.jpg",	1, "Freedom", 8, "Gestão de Materiais e Serviços", "freedom.ini", null);

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
					addOpcao(100103000, TP_OPCAO_ITEM, "Tratamentos tributários","Tratamento Tributário", 't', 100103010, 3, true,FTratTrib.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Classificações fiscais","Classificações", 'l', 100103020, 3, true, FCLFiscal.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Almoxarifados", "Almoxarifado",'x', 100103030, 3, true, FAlmox.class);
					addOpcao(100103000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'r', 100103040,3, true, FGrupo.class);
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
				addOpcao(100100000, TP_OPCAO_MENU, "Atribuições", "", 't', 100105000,3, false, null);
					addOpcao(100105000, TP_OPCAO_ITEM, "Atribuições", "Atribuição", 'r',100105010, 4, true, FAtribuicao.class);
					addOpcao(100105000, TP_OPCAO_ITEM, "Atribuições por usuário","Atribuição por usuário", 'u', 100105020, 4, true,FAtribUsu.class);
	
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
					addOpcao(100304000, TP_OPCAO_ITEM, "Preferências gerais","Pref. Gerais", 'g', 100304010, 3, true, FPrefereGeral.class);
					addOpcao(100304000, TP_OPCAO_ITEM, "Série de NFs", "", 'N', 100304020,3, true, FSerie.class);
					addOpcao(100304000, TP_OPCAO_ITEM, "Modelo de NFs", "Modelo NF", 'M',100304030, 3, true, FModNota.class);

		addOpcao(-1, TP_OPCAO_MENU, "Entrada", "", 'E', 200000000, 0, false,null);
			addOpcao(200000000, TP_OPCAO_ITEM, "Solicitação de Compra",	"Solicitação de Compra", 'S', 200100000, 1, true,FSolicitacaoCompra.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Aprova Solicitação de Compra","Aprova Solicitação de Compra", 'A', 200200000, 1, true,FAprovaSolicitacaoCompra.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa Solicitações de Compra","Pesquisa Solicitações de Compra", 'P', 200300000, 1, true,FConsSol.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Cotação de Preços",	"Cotação de Preços", 'T', 200400000, 1, true,FCotacaoPrecos.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Aprova Cotação de Preços","Aprova Cotação de Preços", 'P', 200500000, 1, true,FAprovaCotacaoPrecos.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Compra", "", 'C', 200600000, 1,true, FCompra.class);
			addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200700000, 1,false, null);
				addOpcao(200700000, TP_OPCAO_ITEM, "Compras por fornecedor","Compras por Fornecedor", 'F', 200701000, 2, true,FRComprasFor.class);

		addOpcao(-1, TP_OPCAO_MENU, "Saída", "", 'S', 300000000, 0, false, null);
			addOpcao(300000000, TP_OPCAO_ITEM, "Venda", "Venda", 'V', 300100000, 1,true, FVenda.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Cancela venda", "Cancelamento",'C', 300200000, 1, true, FCancVenda.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Devolução de vendas","Devolução de vendas", 'D', 300300000, 1, true,FDevolucao.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Lançamento de Frete","Lançamento de Frete", 'L', 300400000, 1, true, FFrete.class);
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_ITEM, "Aprova orçamento","Aprova Orcamento", 'A', 300500000, 1, true, FAprovaOrc.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Orçamento", "Orçamento", 'O',300600000, 1, true, FOrcamento.class);
			addOpcao(300000000, TP_OPCAO_ITEM, "Pesquisa Orçamento","Pesquisa Orçamento", 'P', 300700000, 1, true, FConsOrc.class);	
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_ITEM, "Romaneio", "Romaneio", 'R',300800000, 1, true, FRomaneio.class);
			addSeparador(300000000);
			addOpcao(300000000, TP_OPCAO_ITEM, "Requisição de material", "Requisição de material", 'm',300900000, 1, true, FRma.class);			
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
				addOpcao(400700000, TP_OPCAO_ITEM, "Estoque mínimo", "Estoque Mínimo",'s', 400701000, 2, true, FREstoqueMin.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Produtos/Movimentos","Listagem de Produtos", 'P', 400702000, 2, true,FRMovProd.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Vencimentos de lote","Vencimento Lote", 'V', 400703000, 2, true, FRVencLote.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Saldos de lote", "Saldos de Lote",'l', 400704000, 2, true, FRSaldoLote.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Demanda", "Demanda", 'D',400705000, 2, true, FRDemanda.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Conferência","Conferência de Estoque", 'C', 400706000, 2, true,FRConfEstoq.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Inventário PEPS","Inventário PEPS", 'I', 400707000, 2, true, FRInvPeps.class);

				addBotao("btCliente.gif","Cliente","Clientes", 100101010, FCliente.class);
				addBotao("btEntrada.gif","Compra","Compras", 200600000, FCompra.class);
				addBotao("btEstoque.gif","Consulta estoque","Consulta", 400300000, FConsulta.class);   
				addBotao("btProduto.gif","Cadastro de produtos","Produtos", 100103090, FProduto.class);
				addBotao("btSimilar.gif","Cadastro de similaridades","Similaridade", 100103080, FSimilar.class);
				addBotao("btOrcamento.gif", "Orçamento", "Orcamento", 300600000, FOrcamento.class);
				addBotao("btConsOrcamento.gif", "Pesquisa Orçamento", "Pesquisa Orcamentos", 300700000, FConsOrc.class);
				addBotao("btAprovaOrc.gif", "Aprovações de Orçamantos", "Aprova Orcamento", 300500000, FAprovaOrc.class);				
		
		ajustaMenu();
		
		sNomeModulo = "Gestão de Materiais e Serviços";
		sNomeSis = "Freedom";
	    sMailSuporte = "suporte@stpinf.com";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add("Robson Sanchez - Supervisão / Analise");
		vEquipeSis.add("Anderson Sanchez - Supervisão / Programação");
		vEquipeSis.add("Alex Rodrigues - Programação");
		vEquipeSis.add("Alexandre Marcondes - Programação");
		vEquipeSis.add("Fernando Oliveira - Programação");
		vEquipeSis.add("Moyzes Braz - Arte gráfica");
		vEquipeSis.add("Leandro Oliveira - Testes / Suporte");
		  
	}

	public static void main(String sParams[]) {
		try {
			FreedomGMS freedom = new FreedomGMS();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}