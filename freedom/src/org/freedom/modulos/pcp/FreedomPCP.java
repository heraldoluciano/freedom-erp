/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
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
 * Tela principal do módulo de produção.
 *  
 */

package org.freedom.modulos.pcp;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.gms.FConsRMA;
import org.freedom.modulos.gms.FConsRmaItem;
import org.freedom.modulos.gms.FProduto;
import org.freedom.modulos.gms.FRma;
import org.freedom.modulos.gms.FTipoMov;
import org.freedom.modulos.std.view.frame.comum.FAlmox;
import org.freedom.modulos.std.view.frame.comum.FInventario;
import org.freedom.modulos.std.view.frame.comum.FMarca;
import org.freedom.modulos.std.view.frame.comum.FUnidade;
import org.freedom.modulos.std.view.frame.report.FRConfEstoq;
import org.freedom.modulos.std.view.frame.report.FRDemanda;
import org.freedom.modulos.std.view.frame.report.FREstoqueMin;
import org.freedom.modulos.std.view.frame.report.FRInvPeps;
import org.freedom.modulos.std.view.frame.report.FRMovProd;
import org.freedom.modulos.std.view.frame.report.FRSaldoLote;
import org.freedom.modulos.std.view.frame.report.FRVencLote;
import org.freedom.modulos.std.view.frame.special.FGrupo;
import org.freedom.modulos.std.view.frame.tools.FConsPreco;
import org.freedom.modulos.std.view.frame.tools.FConsulta;
import org.freedom.modulos.std.view.frame.tools.FKardex;
import org.freedom.modulos.std.view.frame.tools.FProcessaEQ;

public class FreedomPCP extends AplicativoPD implements ActionListener {
	public FreedomPCP() {

		super(
				"iconpcp.png", "splashPCP.png", 1, "Freedom", 5, "Planejamento e Controle da Produção", null,new FPrincipalPD(null, "bgFreedom2.jpg"),LoginPD.class);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,null);
			addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,false, null);			
				addOpcao(100100000, TP_OPCAO_MENU, "Produtos", "", 'P', 100101000, 2,false, null);
					addOpcao(100101000, TP_OPCAO_ITEM, "Almoxarifado", "Almoxarifado", 'x',100120030, 3, true, FAlmox.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Grupo", "Grupo", 'r', 100120040, 3,true, FGrupo.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Marca", "Marca", 'c', 100120050, 3,true, FMarca.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Unidade", "Unidade", 'U',100120060, 3, true, FUnidade.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Produto", "Produto", 'P',100120070, 3, true, FProduto.class);

					addSeparador(100100000);
		
				addOpcao(100100000, TP_OPCAO_MENU, "PCP","PCP", 'C', 100102000, 1, false, null);
					addOpcao(100102000, TP_OPCAO_ITEM, "Tipos de recursos","Tipos de recursos", 'T', 100102010, 2, true, FTipoRec.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Recursos de produção","Recursos de produção", 'R', 100102020, 2, true,FRecursos.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Fases de produção","Fases de produção", 'F', 100102030, 2, true, FFase.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Estrutura", "Estrutura de produto",'E', 100102040, 2, true, FEstrutura.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Modelos de Lote", "Modelo de lote",'E', 100102050, 2, true, FModLote.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Tipo de Analise", "Tipo de Analise",'A', 100102060, 2, true, FTipoAnalise.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Método Analitico", "Método Analitico",'A', 100102070, 2, true, FMetodoAnalitico.class);
										
		    addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'F', 110100000,1, false, null);
				addOpcao(110100000, TP_OPCAO_ITEM, "Preferências de Produção","Preferências de Produção", 'u', 110105000, 2, true,FPrefereProd.class);
			
		addOpcao(-1, TP_OPCAO_MENU, "Produção", "", 'P', 200000000, 0, false,null);
			addOpcao(200000000, TP_OPCAO_ITEM, "Ordens de produção","Ordens de produção", 'O', 200100000, 1, true, FOP.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Simulação de OP","Simulação de OP", 'S', 200200000, 1, true, FSimulaOP.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Acompanhamento da produção","Acompanhamento da produção", 'o', 200300000, 1, true, FAcompanhaProd.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Planejamento mestre da produção","Planejamento mestre da produção", 'P', 200400000, 1, true, FPMP.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Contra Prova","Contra prova", 'P', 200400000, 1, true, FContraProva.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_ITEM, "Requisição de material", "Requisição de material", 'R',200500000, 1, true, FRma.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa requisição de material", "Pesquisa requisição de material", 'P',200600000, 1, true, FConsRMA.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa RMA por item", "Pesquisa RMA por item", 'i',200700000, 1, true, FConsRmaItem.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Baixa RMA via Cód.Barras","Baixa RMA", 'B', 200800000, 1, true,FBaixaRMACodBar.class);
			addSeparador(200000000);
			addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200900000,1, false, null);
				addOpcao(200900000, TP_OPCAO_ITEM, "Certificação de análise","Certificação de análise", 'C', 200900100, 1, true,FRCertAnalise.class);
				addOpcao(200900000, TP_OPCAO_ITEM, "Análises","Análises", 'C', 200900200, 1, true, FRAnalise.class);
				addOpcao(200900000, TP_OPCAO_ITEM, "Contra-Provas","Contra-Provas", 'C', 200900300, 1, true, FRContraProva.class);
				addOpcao(200900000, TP_OPCAO_ITEM, "Produção","Produção", 'P', 200900400, 1, true, FRProducao.class);
			
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
				addOpcao(400700000, TP_OPCAO_ITEM, "Custo de Produção","Custo de Produção", 'u', 400708000, 2, true, FRCustoProducao.class);
					
		addBotao("btEstProduto.gif", "Estrutura de produto","Estrutura de produto", 100102040, FEstrutura.class);
		addBotao("btProduto.gif", "Produtos", "Produto", 100101000,FProduto.class);
		addBotao("btOP.gif","Ordens de Produção","Ordens de Produção",200100000,FOP.class);
		addBotao("btRma.gif","Requisição de material", "Requisição de material", 200200000,FRma.class);
		addBotao("btEstoque.gif","Consulta estoque","Consulta", 400300000, FConsulta.class);
		addBotao("btAcompanhaProd.png","Acompanhamento da produção","Acompanhamento da produção", 200300000, FAcompanhaProd.class);
		addBotao("btPCP.png","Planejamento mestre da produção","Planejamento da produção", 200400000, FPMP.class);
		
		ajustaMenu(); 
		
		nomemodulo = "Produção";
		
	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomPCP freedom = new FreedomPCP();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}