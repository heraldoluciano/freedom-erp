/**
 * @version 29/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FreedomFNC.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal do módulo financeiro.
 * 
 */

package org.freedom.modulos.fnc;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.cfg.FFeriados;
import org.freedom.modulos.std.view.frame.comum.FClasCli;
import org.freedom.modulos.std.view.frame.comum.FEstacao;
import org.freedom.modulos.std.view.frame.comum.FImpressora;
import org.freedom.modulos.std.view.frame.comum.FLiberaCredito;
import org.freedom.modulos.std.view.frame.comum.FPapel;
import org.freedom.modulos.std.view.frame.comum.FSetor;
import org.freedom.modulos.std.view.frame.comum.FTipoCli;
import org.freedom.modulos.std.view.frame.comum.FTipoCob;
import org.freedom.modulos.std.view.frame.comum.FTipoCred;
import org.freedom.modulos.std.view.frame.comum.FTipoFor;
import org.freedom.modulos.std.view.frame.comum.FTipoRestr;
import org.freedom.modulos.std.view.frame.detail.FEmpresa;
import org.freedom.modulos.std.view.frame.detail.FPlanoPag;
import org.freedom.modulos.std.view.frame.report.FRBalancete;
import org.freedom.modulos.std.view.frame.report.FRBalanceteGrafico;
import org.freedom.modulos.std.view.frame.report.FRBoleto;
import org.freedom.modulos.std.view.frame.report.FRCentroCusto;
import org.freedom.modulos.std.view.frame.report.FRExtrato;
import org.freedom.modulos.std.view.frame.report.FRGraficoCC;
import org.freedom.modulos.std.view.frame.report.FRInadimplentes;
import org.freedom.modulos.std.view.frame.report.FRPagar;
import org.freedom.modulos.std.view.frame.report.FRPontoEqui;
import org.freedom.modulos.std.view.frame.report.FRRazCli;
import org.freedom.modulos.std.view.frame.report.FRRazFor;
import org.freedom.modulos.std.view.frame.report.FRRazaoFin;
import org.freedom.modulos.std.view.frame.report.FRRestricao;
import org.freedom.modulos.std.view.frame.report.FRestrCli;
import org.freedom.modulos.std.view.frame.special.FCentroCusto;
import org.freedom.modulos.std.view.frame.special.FLanca;
import org.freedom.modulos.std.view.frame.special.FPlanejamento;
import org.freedom.modulos.std.view.frame.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.tabbed.FCredCli;
import org.freedom.modulos.std.view.frame.tabbed.FFornecedor;
import org.freedom.modulos.std.view.frame.tabbed.FModBoleto;
import org.freedom.modulos.std.view.frame.tabbed.FMoeda;
import org.freedom.modulos.std.view.frame.tabbed.FVendedor;
import org.freedom.modulos.std.view.frame.tools.FProcessaSL;

public class FreedomFNC extends AplicativoPD {

	public FreedomFNC() {

		super( "iconfnc.png", "splashFNC.png", 1, "Freedom", 6, "Financeiro", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100101000, 2, false, null );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100101010, 3, true, FSetor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Comissionado", "Comissionado", 's', 100101020, 3, true, FVendedor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Tipos de clientes", "TipoCli", 'T', 100101030, 3, true, FTipoCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Classificação de clientes", "Classifição de Clientes", 'f', 100101040, 3, true, FClasCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Clientes", "Clientes", 'C', 100101050, 3, true, FCliente.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Crédito por cliente", "Crédito por cliente", 'r', 100101060, 3, true, FCredCli.class );
				
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100102000, 2, true, FMoeda.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Banco", "Banco", 'B', 100103000, 2, true, FBanco.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de cobrança", "TipoCob", 'T', 100104000, 2, true, FTipoCob.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Carteira de cobrança", "Carteira de cobrança", 'C', 100105000, 2, true, FCartCob.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Plano de pagamento", "PlanoPag", 'P', 100106000, 2, true, FPlanoPag.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Feriados", "Feriados", 'F', 100107000, 2, true, FFeriados.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de fornecedor", "TipoFor", 'i', 100107000, 2, true, FTipoFor.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100108000, 2, true, FFornecedor.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_MENU, "Febraban", "", 'b', 100109000, 2, false, null );
					addOpcao( 100109000, TP_OPCAO_ITEM, "Códigos de retorno", "Códigos de retorno", 'C', 100109010, 3, true, FCodRetorno.class );
					addOpcao( 100109000, TP_OPCAO_ITEM, "Manutenção de clientes", "Manutenção de clientes", 'M', 100109020, 3, true, FManutCli.class );
			addOpcao( 100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000, 1, false, null );
					addOpcao( 100200000, TP_OPCAO_ITEM, "Transf. lançtos. entre categorias", "Transf. lançtos. entre categorias", 'F', 100200010, 1, true, FTrnsLancCat.class );
			addOpcao( 100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100300000, 1, false, null );
				addOpcao( 100300000, TP_OPCAO_ITEM, "Preferências gerais", "Preferência Gerais", 'G', 100310000, 2, true, FPrefereFNC.class );
				addOpcao( 100300000, TP_OPCAO_ITEM, "Preferências febraban", "Preferências Febraban", 'F', 100310000, 2, true, FPrefereFBB.class );
			//addSeparador( 100100000 );
			addOpcao( 100000000, TP_OPCAO_MENU, "Configurações", "", 'C', 100400000, 1, false, null );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Estação de trabalho", "Estações de trabalho", 't', 100401000, 2, true, FEstacao.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I', 100402000, 2, true, FImpressora.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100403000, 2, true, FPapel.class );
				addSeparador( 100400000 );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100404000, 2, true, FEmpresa.class );

		addOpcao( -1, TP_OPCAO_MENU, "Pagar", "", 'P', 200000000, 0, false, null );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Manutenção", "Manutenção de contas a pagar", 'M', 200100000, 1, true, FManutPag.class );
		addOpcao( 200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200200000, 1, false, null );
			addOpcao( 200200000, TP_OPCAO_ITEM, "Pagar/Pagas", "Pagar/Pagas", 'P', 200201000, 2, true, FRPagar.class );
			addOpcao( 200200000, TP_OPCAO_ITEM, "Razão", "Razão", 'R', 200202000, 2, true, FRRazFor.class );

		addOpcao( -1, TP_OPCAO_MENU, "Receber", "", 'R', 300000000, 0, false, null );
		addOpcao( 300000000, TP_OPCAO_ITEM, "Manutenção", "Manutenção de contas a receber", 'M', 300100000, 1, true, FManutRec.class );
		addOpcao( 300000000, TP_OPCAO_ITEM, "Bordero", "Bordero", 'B', 300200000, 1, true, FBordero.class );
		addOpcao( 300000000, TP_OPCAO_MENU, "Febraban", "", 'F', 300300000, 1, false, null );
  		    addOpcao( 300300000, TP_OPCAO_MENU, "SIACC", "SIACC", 'S', 300301000, 2, false, null );
			    addOpcao( 300301000, TP_OPCAO_ITEM, "Remessa", "Remessa Siacc", 'm', 300301010, 2, true, FRemSiacc.class );
			    addOpcao( 300301000, TP_OPCAO_ITEM, "Retorno", "Retorno Siacc", 't', 300301020, 2, true, FRetSiacc.class );
			addOpcao( 300300000, TP_OPCAO_MENU, "CNAB", "CNAB", 'C', 300302000, 2, false, null );
			    addOpcao( 300302000, TP_OPCAO_ITEM, "Remessa", "Remessa Cnab", 'm', 300302010, 2, true, FRemCnab.class );
			    addOpcao( 300302000, TP_OPCAO_ITEM, "Retorno", "Retorno Cnab", 't', 300302020, 2, true, FRetCnab.class );
		addOpcao( 300000000, TP_OPCAO_MENU, "Listagens", "", 'L', 300400000, 1, false, null );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Receber/Recebidas", "Receber/Recebidas", 'R', 300401000, 2, true, FRReceber.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Inadimplentes", "Inadimplentes", 'I', 300402000, 2, true, FRInadimplentes.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Bordero de cobrança", "Bordero de cobrança", 'B', 300403000, 2, true, FRBordero.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Descontos por setor", "Descontos por setor", 'D', 300404000, 2, true, FRReceberSetor.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Razão", "Razão", 'R', 300405000, 2, true, FRRazCli.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Recebimentos por Mês", "Recebimentos por Mês", 'M', 300407000, 2, true, FRReceberMes.class );
			addOpcao( 300400000, TP_OPCAO_ITEM, "Relatório de cobrança", "Relatório de cobrança", 'o', 300408000, 2, true, FRCobranca.class );

		addOpcao( -1, TP_OPCAO_MENU, "Financeiro", "", 'F', 400000000, 0, false, null );
			addOpcao( 400000000, TP_OPCAO_MENU, "Boleto/Recibo", "", 'o', 400100000, 1, false, null );
				addOpcao( 400100000, TP_OPCAO_ITEM, "Modelo", "Modelo de boleto/recibo", 'M', 400101000, 2, true, FModBoleto.class );
				addOpcao( 400100000, TP_OPCAO_ITEM, "Imprimir", "Boleto/Recibo", 'I', 400101000, 2, true, FRBoleto.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Banco", "Banco", 'B', 400200000, 1, true, FBanco.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Planejamento", "Planejamento", 'P', 400300000, 1, true, FPlanejamento.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Centro de custo", "Centro de Custos", 'C', 400400000, 1, true, FCentroCusto.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Contas", "Contas", 't', 400500000, 1, true, FConta.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Histórico", "Histórico", 't', 400600000, 1, true, FHistPad.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Lançamentos", "Lançamentos", 'L', 400700000, 1, true, FLanca.class );
			
				addSeparador( 400000000 );
				
				addOpcao( 400000000, TP_OPCAO_ITEM, "Tipo de crédito", "Tipo de crédito", 'T', 400800000, 1, true, FTipoCred.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Liberação de crédito", "Liberação de crédito", 'b', 400900000, 1, true, FLiberaCredito.class );

				addOpcao( 400000000, TP_OPCAO_ITEM, "Tipo de Restrição", "Tipo de Restrição", 's', 401000000, 1, true, FTipoRestr.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Restrição de clientes", "Restrição de clientes", 'r', 401100000, 1, true, FRestrCli.class );
				addOpcao( 400000000, TP_OPCAO_ITEM, "Receber/Pagar", "Receber/Pagar", 'g', 401400000, 1, true, FRRecPag.class );

				addSeparador( 400000000 );
			
			addOpcao( 400000000, TP_OPCAO_ITEM, "Reprocessa saldo", "Reprocessamento de saldos", 'R', 401000000, 1, true, FProcessaSL.class );
			addSeparador( 400000000 );
			
			addOpcao( 400000000, TP_OPCAO_MENU, "Listagens", "", 's', 401200000, 1, false, null );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Extrato", "Extrato", 'E', 401201000, 2, true, FRExtrato.class );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Extrato Previsionado", "Extrato Previsionado", 'P', 401210000, 2, true, FRExtrato.class );				
				addOpcao( 401200000, TP_OPCAO_ITEM, "Balancete", "Balancete", 'B', 401202000, 2, true, FRBalancete.class );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Relatório financeiro por C.C.", "Relatorio Financeiro por C.C.", 'R', 401203000, 2, true, FRCentroCusto.class );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Razão financeiro", "Razão financeiro", 'z', 401204000, 2, true, FRRazaoFin.class );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Fluxo de caixa", "Fluxo de caixa", 'F', 401205000, 2, true, FRFluxoCaixa.class );	
				addOpcao( 401200000, TP_OPCAO_ITEM, "Fluxo de caixa resumido", "Fluxo de caixa resumido", 'F', 401207000, 2, true, FRFluxoCaixaRes.class );
				addOpcao( 401200000, TP_OPCAO_ITEM, "Restrição/clientes", "Restrição/clientes", 'R', 401206000, 2, true, FRRestricao.class );	
				addOpcao( 401200000, TP_OPCAO_ITEM, "Ponto de equilibrio", "Ponto de equilibrio", 'P', 401208000, 2, true, FRPontoEqui.class );	
				addOpcao( 401200000, TP_OPCAO_ITEM, "Fluxo de caixa realizado", "Fluxo de caixa realizado", 'c', 401209000, 2, true, FRFluxoCaixaReal.class );	
			
			addOpcao( 400000000, TP_OPCAO_MENU, "Gráficos", "", 'G', 401300000, 1, false, null );
				addOpcao( 401300000, TP_OPCAO_ITEM, "Balancete Gráfico", "Balancete Gráfico", 'B', 401201000, 2, true, FRBalanceteGrafico.class );
				addOpcao( 401300000, TP_OPCAO_ITEM, "Gráfico financeiro por C.C", "Gráfico Financeiro por C.C", 'F', 401202000, 2, true, FRGraficoCC.class );

		addBotao( "barraUsuario.gif", "Cliente", "Clientes", 100101050, FCliente.class );
		addBotao( "btContaPagar.gif", "Contas a pagar", "Manutenção de contas a pagar", 200100000, FManutPag.class );
		addBotao( "btContaReceber.gif", "Contas a receber", "Manutenção de contas a receber", 300100000, FManutRec.class );
		addBotao( "btLancamentoFin.gif", "Lançamentos financeiros", "Lançamentos", 400600000, FLanca.class );
		
		ajustaMenu();

		nomemodulo = "Financeiro";

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomFNC freedom = new FreedomFNC();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execução" );
			e.printStackTrace();
		}
	}
}
