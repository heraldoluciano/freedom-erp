/**
 * @version 19/09/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe:
 * @(#)FreedomTMK.java <BR>
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
 * Tela principal do módulo telemarketing.
 *  
 */

package org.freedom.modulos.crm;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FAtendente;
import org.freedom.modulos.atd.FTipoAtend;
import org.freedom.modulos.crm.agenda.FAgenda;
import org.freedom.modulos.crm.agenda.FTipoAgenda;
import org.freedom.modulos.std.FAprovCancOrc;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FConsPreco;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.modulos.std.FPesquisaOrc;
import org.freedom.modulos.std.FRClientesSemVendas;
import org.freedom.modulos.std.FRDesempVend;
import org.freedom.modulos.std.FRDevolucao;
import org.freedom.modulos.std.FREvoluVendas;
import org.freedom.modulos.std.FRFechaDiario;
import org.freedom.modulos.std.FRGerContas;
import org.freedom.modulos.std.FRMediaItem;
import org.freedom.modulos.std.FROrcamento;
import org.freedom.modulos.std.FRResumoDiario;
import org.freedom.modulos.std.FRUltimaVenda;
import org.freedom.modulos.std.FRVendaSetor;
import org.freedom.modulos.std.FRVendasCFOP;
import org.freedom.modulos.std.FRVendasCanc;
import org.freedom.modulos.std.FRVendasCli;
import org.freedom.modulos.std.FRVendasCliProd;
import org.freedom.modulos.std.FRVendasDet;
import org.freedom.modulos.std.FRVendasFisico;
import org.freedom.modulos.std.FRVendasGeral;
import org.freedom.modulos.std.FRVendasItem;
import org.freedom.modulos.std.FRVendasPlanoPag;
import org.freedom.modulos.std.FRVendasVend;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomCRM extends AplicativoPD {
	private MenuItem miAtendimento = new MenuItem();
	public FreedomCRM() {
		super("iconcrm.png", "splashCRM.png", 1, "Freedom", 7, "CRM", null, new FPrincipalPD(null, "bgFreedom2.jpg"),LoginPD.class);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);

		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,false, null);

		addOpcao(100100000, TP_OPCAO_MENU, "Contato", "", 'C', 1001010000, 2,false, null);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Atividade", "Atividade", 'A',	100101010, 3, true, FAtividade.class);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Contato", "Contatos", 'C',	100101020, 3, true, FContato.class);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Tipo de contato", "Tipo de contato", 'o',	100101030, 3, true, FTipoCont.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Atendente", "", 'A', 100102000, 2,	false, null);
		addOpcao(100102000, TP_OPCAO_ITEM, "Tipo de Atendente","Tipo de atendente", 'T', 100102010, 3, true, FTipoAtend.class);
		addOpcao(100102000, TP_OPCAO_ITEM, "Atendentes", "Atendente", 'A',100110020, 3, true, FAtendente.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100104000, 2,false, null);
		addOpcao(100104000, TP_OPCAO_ITEM, "Setor do cliente", "Setor", 'S', 100104010, 3,	true, FSetor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Vendedor", "Vendedor", 'V',100104020, 3, true, FVendedor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Tipos de cliente","Tipo de cliente", 'T', 100104030, 3, true, FTipoCli.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Classificação de cliente","Classificação do cliente", 'l', 100104040, 3, true,FClasCli.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Cliente", "Clientes", 'C',100104060, 3, true, FCliente.class);
		addSeparador(100100000);
		
		addOpcao(100100000, TP_OPCAO_ITEM, "Campanha", "Campanha", 'C', 100104070, 2, true, FCampanha.class );
		addOpcao(100100000, TP_OPCAO_ITEM, "Configuração de email", "Configuração de email", 'E', 100104080, 2, true, FConfEmail.class );
		addOpcao(100100000, TP_OPCAO_ITEM, "Origem do contato", "Origem do contato", 'O', 100104090, 2, true, FOrigContato.class );
		addOpcao(100100000, TP_OPCAO_ITEM, "Email", "Email", 'M', 100105000, 2, true, FEmail.class );
		addOpcao(100100000, TP_OPCAO_ITEM, "Tipo de agendamento", "Tipo de agendamento", 'g', 100106000, 2, true, FTipoAgenda.class );
	//	addSeparador( 100100000 ); 

		addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100200000,1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Preferências Gerais.","Pref. Gerais", 'G', 100201000, 2, true, FPrefere.class);
		addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100300000,1, false, null);
		addOpcao(100300000, TP_OPCAO_ITEM, "Importação de Contatos","Importação de contatos", 'I', 100301000, 2, true,	FImportaCto.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Cadastro de org.freedom.layout","Cadastro de org.freedom.layout", 'C', 100302000, 2, true,FTipoImp.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Envio de e-mail aos contatos","Envia e-mail", 'E', 100303000, 2, true, FEnviaMail.class);

		addOpcao(100300000, TP_OPCAO_ITEM, "Gerenciamento de campanhas","Gerenciamento de campanhas", 'G', 100304000, 2, true, FGerencCampanhas.class);

		addOpcao(-1, TP_OPCAO_MENU, "Contatos", "", 'C', 200000000, 0, false,null);
		addOpcao(200000000, TP_OPCAO_ITEM, "Histórico", "Historico", 'H',	200100000, 1, true, FHistorico.class);
		addOpcao(200000000, TP_OPCAO_ITEM, "Agenda", "Agenda", 'A', 200200000,1, true, FAgenda.class);
		addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200300000, 1,false, null);
		addOpcao(200300000, TP_OPCAO_ITEM, "Relatório diário","Relatório diário", 'R', 200301000, 1, true, FRDiario.class);

		addOpcao(-1, TP_OPCAO_MENU, "Atendimento", "", 'A', 300000000, 0, false,null);
		addOpcao(300000000, TP_OPCAO_ITEM, "Atendimentos", "Atendimentos", 'A',	300100000, 1, true, FAtendimento.class);
		
		addSeparador( 300000000 );
		
		addOpcao(300000000, TP_OPCAO_ITEM, "Tipo de atendimento", "Tipo de atendimento", 'A',	300200000, 1, true, FTipoAtendo.class);
		addOpcao(300000000, TP_OPCAO_ITEM, "Classificação de Atendimentos", "Classificação de Atendimento", 'i',	300250000, 1, true, FClasAtendo.class);
		addOpcao(300000000, TP_OPCAO_ITEM, "Setor de Atendimentos", "Setor de Atendimento", 'i',	300260000, 1, true, FSetorAtend.class);
		
		addSeparador( 300000000 );
		
		addOpcao(300000000, TP_OPCAO_MENU, "Listagens", "", 'L',	300300000, 1, false,null);
		addOpcao(300300000, TP_OPCAO_ITEM, "Atendimentos", "Atendimentos", 'T',	300100000, 2, true, FRAtendimentos.class);
		
		addOpcao( -1, TP_OPCAO_MENU, "Saída", "", 'S', 400000000, 0, false, null );
		addOpcao( 400000000, TP_OPCAO_ITEM, "Aprova orçamento", "Aprova Orçamento", 'A', 400100000, 1, true, FAprovCancOrc.class );
		addOpcao( 400000000, TP_OPCAO_ITEM, "Orçamento", "Orçamento", 'O', 400200000, 1, true, FOrcamento.class );
		addOpcao( 400000000, TP_OPCAO_ITEM, "Pesquisa Orçamento", "Pesquisa Orçamento", 'P', 400300000, 1, true, FPesquisaOrc.class );
		addSeparador(400000000 );
		addOpcao( 400000000, TP_OPCAO_MENU, "Listagens", "", 's', 401000000, 1, false, null );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Resumo diário", "Resumo Diário", 'R', 401000100, 2, true, FRResumoDiario.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas geral", "Vendas Geral", 'V', 401000200, 2, true, FRVendasGeral.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas físico", "Físico de Vendas", 'd', 401000300, 2, true, FRVendasFisico.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas detalhado", "Vendas Detalhadas", 'n', 401000400, 2, true, FRVendasDet.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas por ítem", "Vendas por Item", 'e', 401000500, 2, true, FRVendasItem.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Média de vendas por ítem", "Media de vendas por item", 'o', 401000600, 2, true, FRMediaItem.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Ultimas Vendas por Cliente", "Ultimas Vendas por Cliente", 'U', 401000700, 2, true, FRUltimaVenda.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas por Cliente", "Vendas por Cliente", 'C', 401000800, 2, true, FRVendasCli.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas por Setor", "Vendas por Setor", 't', 401000900, 2, true, FRVendaSetor.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas por CFOP", "Vendas por CFOP", 'F', 401001000, 2, true, FRVendasCFOP.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Gerenciamento de contas", "Gerenciamento de contas", 'i', 401001100, 2, true, FRGerContas.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Fechamento diário", "Fechamento diário", 'i', 401001200, 2, true, FRFechaDiario.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Relatório de devolução", "Relatório de devolução", 'd', 401001300, 2, true, FRDevolucao.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Ultimas Vendas por Cli/Produto", "Ultimas Vendas por Cliente/Produto", 'd', 401001400, 2, true, FRVendasCliProd.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Ultimas Vendas por Plano de Pagamento", "Ultimas Vendas por Plano de pagamento", 'd', 401001500, 2, true, FRVendasPlanoPag.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Desempenho por vendedor", "Desempenho por vendedor", 'v', 401001600, 2, true, FRDesempVend.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas por vendedor", "Vendas por vendedor", 'v', 401001700, 2, true, FRVendasVend.class );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Vendas canceladas", "Vendas canceladas", 'v', 401001800, 2, true, FRVendasCanc.class );
			addSeparador( 401000000 );
			addOpcao( 401000000, TP_OPCAO_ITEM, "Orçamentos", "Orçamentos", 'O', 401001700, 2, true, FROrcamento.class );
			addSeparador( 301000000 );
			addOpcao( 301000000, TP_OPCAO_ITEM, "Clientes sem movimento", "Clientes sem movimento", 'm', 301002000, 2, true, FRClientesSemVendas.class );

		addOpcao( 400000000, TP_OPCAO_MENU, "Gráficos", "", 'G', 401100000, 1, false, null );
			addOpcao( 401100000, TP_OPCAO_ITEM, "Evolução de vendas", "Evolução de vendas", 'E', 401100100, 2, true, FREvoluVendas.class );
		addSeparador( 400000000 );
		addOpcao( 400000000, TP_OPCAO_MENU, "Consultas", "", 'n', 401200000, 1, false, null );
			addOpcao( 401200000, TP_OPCAO_ITEM, "Preços", "Consulta de preços", 'P', 401200100, 2, true, FConsPreco.class );
			addOpcao( 401200000, TP_OPCAO_ITEM, "Clientes", "Consulta de clientes", 'C', 301200200, 2, true, FConsultaCli.class );

		addOpcao( -1, TP_OPCAO_MENU, "Projetos", "", 'S', 500000000, 0, false, null );
		addOpcao( 500000000, TP_OPCAO_ITEM, "Projetos", "Projetos/Contratos", 'P', 500100000, 1, true, FContrato.class );
		addOpcao(500000000, TP_OPCAO_MENU, "Listagens", "", 'L',	500200000, 1, false,null);
		addOpcao(500200000, TP_OPCAO_ITEM, "Detalhamento de custos", "Custos de Projeto", 'u',	500201000, 2, true, FRCustoProj.class);

		addBotao( "btCliente.gif", "Cliente", "Clientes", 100104000, FCliente.class );		
		addBotao("btAtendimentos.gif", "Atendimentos", "Atendimentos", 300100000,FAtendimento.class);
		addBotao("btCampanha.gif", "Campanha", "Campanha", 100110020,FCampanha.class);
		addBotao("btEmail.gif", "Email", "Email", 100110020,FEmail.class);
		addBotao("btContato.gif", "Contato", "Contato", 100110020,FContato.class);
		addBotao("btGerencCampanha.gif", "Gerenciamento de campanhas", "Gerenciamento de campanhas", 100304000,FGerencCampanhas.class);
		addBotao("btProjeto.png", "Projetos", "Projetos/Contratos", 500100000,FContrato.class);
		addBotao( "btOrcamento.png", "Orçamento", "Orçamento", 400200000, FOrcamento.class );
		addBotao("btConsultaCli.png", "Consulta de Clientes", "Consulta de Clientes", 301200200, FConsultaCli.class );
		
		ajustaMenu();
		
		sNomeModulo = "CRM";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add("Robson Sanchez - Supervisão / Analise");
		vEquipeSis.add("Anderson Sanchez - Supervisão / Programação");
		vEquipeSis.add("Alex Rodrigues - Programação");
		vEquipeSis.add("Alexandre Marcondes - Programação");
		vEquipeSis.add("Fernando Oliveira - Programação");
		vEquipeSis.add("Moyzes Braz - Arte gráfica");
		  
		pm = new PopupMenu();
		  
		MenuItem mi = new MenuItem();
		miAtendimento.setName( "miAtendimento" );
		miAtendimento.setLabel( "Atendimento" );
		miAtendimento.addActionListener(this);
		  
		pm.add(miAtendimento);
		
		telaPrincipal.setTrayIcon(pm);

		
	}

	  public void actionPerformed(ActionEvent evt) {  
		    if (evt.getSource() == miAtendimento) {		    	
		    	DLNovoAtend dl = new DLNovoAtend(0,telaPrincipal,false,con,0,0,"A");
				dl.setVisible( true );				
				dl.dispose();			    	
		    }
		    super.actionPerformed( evt );
	  }

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomCRM fFreedomtmk = new FreedomCRM();
			fFreedomtmk.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}