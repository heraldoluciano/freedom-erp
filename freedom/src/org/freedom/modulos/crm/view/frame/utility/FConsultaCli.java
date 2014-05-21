/**
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * @version 25/02/2014  
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.frame.utiliy <BR>
 *         Classe: @(#)FConsultaCli.java <BR>
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Classe de visualização de histórico de vendas para clientes
 * 
 */
package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.type.StringDireita;
import org.freedom.modulos.crm.dao.DAOConsultaCli;
import org.freedom.modulos.crm.dao.DAOConsultaCli.CESTAS;
import org.freedom.modulos.crm.dao.DAOConsultaCli.ITENSCESTA;
import org.freedom.modulos.crm.dao.DAOConsultaCli.ITENSVENDA;
import org.freedom.modulos.crm.dao.DAOConsultaCli.PRODVENDAS;
import org.freedom.modulos.crm.dao.DAOConsultaCli.RESULT_RECEBER;
import org.freedom.modulos.crm.dao.DAOConsultaCli.RESULT_ULTVENDA;
import org.freedom.modulos.crm.dao.DAOConsultaCli.VENDAS;
import org.freedom.modulos.crm.view.dialog.utility.DLConfirmItem;
import org.freedom.modulos.std.business.object.VDOrcamento;
import org.freedom.modulos.std.dao.DAOOrcamento;
import org.freedom.modulos.std.orcamento.bean.Cesta;
import org.freedom.modulos.std.orcamento.bean.Item;
import org.freedom.modulos.std.orcamento.bussiness.CestaFactory;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;
public class FConsultaCli extends FFilho implements ActionListener, TabelaSelListener, MouseListener
			, KeyListener, CarregaListener, FocusListener, ChangeListener , TabelaEditListener {

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 60 );

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 140 );

	//private JPanelPad panelDetail = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 2 ) );

	private JPanelPad panelDetail = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tabbedDetail = new JTabbedPanePad();

	private JTabbedPanePad tabbedVendas = new JTabbedPanePad(JTabbedPanePad.BOTTOM);

	private JPanelPad panelTodasVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelProdVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTodasCestas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelCestas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelResumoVendas = new JPanelPad( 700, 73 );

	//private JPanelPad panelTabProdVendas = new JPanelPad();
	
	private JPanelPad panelTabProdVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad panelGridVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelGridProdVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabVendasNotas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabItensVenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelGridCestas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelTabCestas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabItensCesta = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );

	// *** Geral

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtAtivoCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JLabelPad lbAtivoCli = new JLabelPad( "Ativo", SwingConstants.CENTER );

	private JButtonPad btBuscar = new JButtonPad( "Buscar vendas", Icone.novo( "btExecuta.png" ) );

	// *** Vendas

	private JTextFieldFK txtUltimaVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtVlrUltimaVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );

	private JTextFieldFK txtTotalVendas = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );

	private JTextFieldFK txtTotalAberto = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );
	
	private JTextFieldFK txtTotalAtraso = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );
	
	private JTextFieldFK txtMediaVendas = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );
	
	private JTextFieldFK txtMaiorVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );

	private JTextFieldFK txtAtrasoMedio = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTablePad tabVendas = new JTablePad();

	private JTablePad tabProdVendas = new JTablePad();

	private JTablePad tabItensVenda = new JTablePad();

	private JTablePad tabCestas = new JTablePad();

	private JTablePad tabItensCesta = new JTablePad();

	// *** Itens para barra de ferramentas
	private JPanelPad pinToolBar = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ));
	
	private JPanelPad pinToolBarCesta = new JPanelPad(40, 120);
	
	private JPanelPad pinToolBarVendas = new JPanelPad(40, 120);

	private JButtonPad btResetCesta = new JButtonPad( Icone.novo( "btExcluir.png" ) );

	private JButtonPad btAddCesta = new JButtonPad( Icone.novo( "btAdic2.gif" ) );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.png" ) );

	private JButtonPad btSelecionar = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btDesselecionar = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btEditQtd = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btTudoIt = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btNadaIt = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private JButtonPad btResetOrc = new JButtonPad( Icone.novo( "btReset.png" ) );

	private JButtonPad btResetItOrc = new JButtonPad( Icone.novo( "btReset.png" ) );
	
	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcProd = new ListaCampos( this );

	private boolean loadingVendas = false;
	
	private boolean loadingItensCesta = false;
	
	private boolean loadingCestas = false;
	
	private CestaFactory cestaFactory;

	private DAOConsultaCli daoconsultacli;
	
	private ImageIcon imgVencida = Icone.novo( "clVencido.gif" );

	private ImageIcon imgAVencer = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgPgEmAtraso = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgPgEmDia = Icone.novo( "clPago.gif" );
	
	private ImageIcon imgCancelado = Icone.novo( "btRecusado.gif" );

	private ImageIcon imgPedido = Icone.novo( "clPriorBaixa.gif" );

	private ImageIcon imgFaturado = Icone.novo( "clPriorAlta.png" );
	
	private ImageIcon imgColuna = null;
	
	private ImageIcon imgVencimento = null;
	
	private Integer codcliatual = null;
	
	private DAOOrcamento daoorcamento = null;

	private Object[] preforc = null;
	
	public FConsultaCli() {

		super( false );
		setTitulo( "Consulta de clientes", this.getClass().getName() );
		setAtribos( 10, 10, 840, 600 );
		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;
		setLocation( x, y );
		montaListaCampos();
		montaTela();
		lcCliente.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		btBuscar.addActionListener( this );
		tabVendas.addTabelaSelListener( this );
		tabVendas.addMouseListener( this );
		tabCestas.addTabelaSelListener( this );
		tabCestas.addTabelaEditListener( this );
		tabCestas.addMouseListener( this );
		tabProdVendas.addTabelaSelListener( this );
		tabProdVendas.addMouseListener( this );
		tabItensVenda.addMouseListener( this );
		btBuscar.addKeyListener( this );
		btResetCesta.addActionListener( this );
		btSelecionar.addActionListener( this );
		btDesselecionar.addActionListener( this );
		btGerar.addActionListener( this );
		btAddCesta.addActionListener( this );
		txtCodCli.addFocusListener( this );
		tabbedDetail.addChangeListener( this );
		Calendar periodo = Calendar.getInstance();
		txtDatafim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.YEAR, periodo.get( Calendar.YEAR ) - 1 );
		txtDataini.setVlrDate( periodo.getTime() );
	}

	private void montaListaCampos() {

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome/Fantasia", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtEmailCli, "EmailCli", "E-Mail", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtAtivoCli, "AtivoCli", "ativo", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

	}

	private void montaTela() {

		// **** Preparando barra de ferramentas
		btResetCesta.setToolTipText( "Apaga todas as cestas da memória" );
		btSelecionar.setToolTipText( "Seleciona todas as cestas" );
		btDesselecionar.setToolTipText( "Desseleciona todas as cestas" );
		btGerar.setToolTipText( "Gerar orçamento(s)" ); 
		pinToolBar.add( pinToolBarCesta, BorderLayout.NORTH );
		pinToolBar.add( pinToolBarVendas, BorderLayout.CENTER );
		pinToolBarCesta.adic( btResetCesta, 3, 3, 30, 30 );
		pinToolBarCesta.adic( btSelecionar, 3, 33, 30, 30 );
		pinToolBarCesta.adic( btDesselecionar, 3, 63, 30, 30 );
		pinToolBarCesta.adic( btGerar, 3, 93, 30, 30 );
		pinToolBarVendas.adic( btAddCesta, 3, 3, 30, 30 );
		// ToolBarCesta inicializa invisível
		pinToolBarCesta.setVisible( false );
		// *********
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		// ***** Cabeçalho
		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		panelMaster.adic( new JLabelPad( "Cód.cli" ), 7, 5, 60, 20 );
		panelMaster.adic( txtCodCli, 7, 25, 60, 20 );
		panelMaster.adic( new JLabelPad( "Razão social do cliente" ), 70, 5, 340, 20 );
		panelMaster.adic( txtRazCli, 70, 25, 340, 20 );
		panelMaster.adic( new JLabelPad( "Contato" ), 413, 5, 100, 20 );
		panelMaster.adic( txtContCli, 413, 25, 100, 20 );
		panelMaster.adic( new JLabelPad( "DDD" ), 7, 45, 60, 20 );
		panelMaster.adic( txtDDDCli, 7, 65, 60, 20 );
		panelMaster.adic( new JLabelPad( "Fone" ), 70, 45, 75, 20 );
		panelMaster.adic( txtFoneCli, 70, 65, 75, 20 );
		panelMaster.adic( new JLabelPad( "e-mail" ), 148, 45, 262, 20 );
		panelMaster.adic( txtEmailCli, 148, 65, 262, 20 );
		panelMaster.adic( lbAtivoCli, 413, 65, 100, 20 );
		panelMaster.adic( new JLabelPad( "Cód.prod." ), 7, 85, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 105, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descrição do produto" ), 70, 85, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 105, 340, 20 );
		panelMaster.adic( periodo, 540, 5, 60, 20 );
		panelMaster.adic( borda, 530, 15, 220, 73 );
		panelMaster.adic( txtDataini, 540, 40, 80, 20 );
		panelMaster.adic( new JLabel( "até", SwingConstants.CENTER ), 620, 40, 40, 20 );
		panelMaster.adic( txtDatafim, 660, 40, 80, 20 );
		panelMaster.adic( btBuscar, 530, 100, 220, 30 );
		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		lbAtivoCli.setOpaque( true );
		lbAtivoCli.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbAtivoCli.setBackground( GREEN );
		lbAtivoCli.setForeground( Color.WHITE );
		
		// ***** Detalhamento (abas)

		panelResumoVendas.setPreferredSize( new Dimension( 700, 80 ) );
		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelDetail.add( panelResumoVendas, BorderLayout.NORTH );
		panelDetail.add( tabbedDetail, BorderLayout.CENTER );
		panelDetail.add(pinToolBar, BorderLayout.EAST);
		
		tabbedVendas.addTab( "Todas", panelTodasVendas );
		tabbedVendas.addTab( "Produtos", panelProdVendas);
	
		tabbedDetail.addTab("Vendas", tabbedVendas);
		tabbedDetail.addTab( "Cestas", panelCestas );

		// ***** Todas Vendas

		//panelTodasVendas.add( panelResumoVendas, BorderLayout.NORTH );
		panelTodasVendas.add( panelGridVendas, BorderLayout.CENTER );
		panelGridVendas.add( panelTabVendasNotas );
		panelGridVendas.add( panelTabItensVenda );
		panelTabVendasNotas.setBorder( BorderFactory.createTitledBorder( "Vendas" ) );
		panelTabItensVenda.setBorder( BorderFactory.createTitledBorder( "Itens da venda selecionada" ) );
		panelTabItensVenda.setPreferredSize( new Dimension( 700, 120 ) );
		// ***** Prod Vendas
		panelProdVendas.add( panelGridProdVendas, BorderLayout.CENTER );
		panelGridProdVendas.add( panelTabProdVendas, BorderLayout.CENTER);
		//panelTabProdVendas.setBorder( BorderFactory.createTitledBorder( "Itens adquiridos no perído" ) );
		// Final prodVendas
		// **** Paineis para Cestas;
		panelCestas.add( panelGridCestas, BorderLayout.CENTER );
		panelGridCestas.add( panelTabCestas );
		panelGridCestas.add( panelTabItensCesta );
		panelTabCestas.setBorder( BorderFactory.createTitledBorder( "Cestas" ) );
		panelTabItensCesta.setBorder( BorderFactory.createTitledBorder( "Itens da cesta selecionada" ) );
		panelTabItensCesta.setPreferredSize( new Dimension( 700, 120 ) );
		// Final de configurações tabCestas
		panelResumoVendas.adic( new JLabelPad( "Última venda" ), 10, 0, 90, 18 );
		panelResumoVendas.adic( txtUltimaVenda, 10, 18, 90, 20 );
		panelResumoVendas.adic( new JLabelPad( "Vlr. últ. venda" ), 103, 0, 95, 18 );
		panelResumoVendas.adic( txtVlrUltimaVenda, 103, 18, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Total de vendas" ), 201, 0, 95, 18 );
		panelResumoVendas.adic( txtTotalVendas, 201, 18, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Valor em aberto" ), 299, 0, 95, 18 );
		panelResumoVendas.adic( txtTotalAberto, 299, 18, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Valor em atraso" ), 397, 0, 95, 18 );
		panelResumoVendas.adic( txtTotalAtraso, 397, 18, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Média vendas/mês" ), 10, 38, 110, 18 );
		panelResumoVendas.adic( txtMediaVendas, 10, 54, 110, 20 );
		panelResumoVendas.adic( new JLabelPad( "Maior venda" ), 123, 38, 95, 18 );
		panelResumoVendas.adic( txtMaiorVenda, 123, 54, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Atraso médio" ), 221, 38, 85, 18 );
		panelResumoVendas.adic( txtAtrasoMedio, 221, 54, 85, 20 );

		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = new Font( "Tomoha", Font.PLAIN, 11 );
		
		panelResumoVendas.adic( new JLabelPad( imgPgEmDia ), 500, 5, 20, 15 );
		JLabelPad pgtoDia = new JLabelPad( "pagas em dia" );
		pgtoDia.setForeground( statusColor );
		pgtoDia.setFont( statusFont );
		panelResumoVendas.adic( pgtoDia, 520, 5, 100, 15 );

		panelResumoVendas.adic( new JLabelPad( imgPgEmAtraso ), 500, 20, 20, 15 );
		JLabelPad pgtoAtraso = new JLabelPad( "pagas em atraso" );
		pgtoAtraso.setForeground( statusColor );
		pgtoAtraso.setFont( statusFont );
		panelResumoVendas.adic( pgtoAtraso, 520, 20, 100, 15 );
		
		panelResumoVendas.adic( new JLabelPad( imgVencida ), 500, 35, 20, 15 );
		JLabelPad vencidas = new JLabelPad( "vencidas" );
		vencidas.setForeground( statusColor );
		vencidas.setFont( statusFont );
		panelResumoVendas.adic( vencidas, 520, 35, 100, 15 );

		panelResumoVendas.adic( new JLabelPad( imgAVencer ), 500, 50, 20, 15 );
		JLabelPad aVencer = new JLabelPad( "a vencer" );
		aVencer.setForeground( statusColor );
		aVencer.setFont( statusFont );
		panelResumoVendas.adic( aVencer, 520, 50, 100, 15 );

		panelResumoVendas.adic( new JLabelPad( imgCancelado ), 650, 5, 20, 15 );
		JLabelPad canceladas = new JLabelPad( "canceladas" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelResumoVendas.adic( canceladas, 670, 5, 100, 15 );

		panelResumoVendas.adic( new JLabelPad( imgPedido ), 650, 20, 20, 15 );
		JLabelPad pedidos = new JLabelPad( "pedidos" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelResumoVendas.adic( pedidos, 670, 20, 100, 15 );

		panelResumoVendas.adic( new JLabelPad( imgFaturado ), 650, 35, 20, 15 );
		JLabelPad faturadas = new JLabelPad( "faturadas" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelResumoVendas.adic( faturadas, 670, 35, 100, 15 );
		// Grid de vendas
		tabVendas.adicColuna( "S.V." );
		tabVendas.adicColuna( "S.R." );
		tabVendas.adicColuna( "Atraso" );
		tabVendas.adicColuna( "Código" );
		tabVendas.adicColuna( "Doc." );
		tabVendas.adicColuna( "Data" );
		tabVendas.adicColuna( "Plano Pgto." );
		tabVendas.adicColuna( "Vendedor" );
		tabVendas.adicColuna( "V.produtos" );
		tabVendas.adicColuna( "V.desc." );
		tabVendas.adicColuna( "V.adic." );
		tabVendas.adicColuna( "Tp.fr." );
		tabVendas.adicColuna( "V.líquido" );
		tabVendas.adicColuna( "Tipo venda" );
		tabVendas.setTamColuna( 30, VENDAS.STATUSVENDA.ordinal() );
		tabVendas.setTamColuna( 30, VENDAS.STATUSPGTO.ordinal() );
		tabVendas.setTamColuna( 40, VENDAS.ATRASO.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.CODVENDA.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.NOTA.ordinal() );
		tabVendas.setTamColuna( 70, VENDAS.DATA.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.PAGAMENTO.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.VENDEDOR.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_PRODUTOS.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_DESCONTO.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_ADICIONAL.ordinal() );
		tabVendas.setTamColuna( 40, VENDAS.FRETE.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_LIQUIDO.ordinal() );
		tabVendas.setColunaInvisivel( VENDAS.TIPOVENDA.ordinal() );
		panelTabVendasNotas.add( new JScrollPane( tabVendas ) );
		// Gride de itens de venda
		tabItensVenda.adicColuna( "Item" );
		tabItensVenda.adicColuna( "Código" );
		tabItensVenda.adicColuna( "Descrição do produto" );
		tabItensVenda.adicColuna( "Lote" );
		tabItensVenda.adicColuna( "Qtd." );
		tabItensVenda.adicColuna( "Preço" );
		tabItensVenda.adicColuna( "V.desc." );
		tabItensVenda.adicColuna( "V.frete" );
		tabItensVenda.adicColuna( "V.líq." );
		tabItensVenda.adicColuna( "TipoVenda" );
		tabItensVenda.adicColuna( "Cód.p.pag." );
		tabItensVenda.adicColuna( "Descrição do plano de pagto." );
		tabItensVenda.adicColuna( "Cód.comis." );
		tabItensVenda.adicColuna( "Nome do comissionado" );
		tabItensVenda.setTamColuna( 30, ITENSVENDA.CODITVENDA.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.CODPROD.ordinal() );
		tabItensVenda.setTamColuna( 250, ITENSVENDA.DESCPROD.ordinal() );
		tabItensVenda.setTamColuna( 70, ITENSVENDA.CODLOTE.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.QTDITVENDA.ordinal() );
		tabItensVenda.setTamColuna( 70, ITENSVENDA.PRECOITVENDA.ordinal() );
		tabItensVenda.setTamColuna( 80, ITENSVENDA.VLRDESCITVENDA.ordinal() );
		tabItensVenda.setTamColuna( 80, ITENSVENDA.VLRFRETEITVENDA.ordinal() );
		tabItensVenda.setTamColuna( 90, ITENSVENDA.VLRLIQITVENDA.ordinal() );
		tabItensVenda.setColunaInvisivel( ITENSVENDA.TIPOVENDA.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.CODPLANOPAG.ordinal() );
		tabItensVenda.setTamColuna( 250, ITENSVENDA.DESCPLANOPAG.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.CODVEND.ordinal() );
		tabItensVenda.setTamColuna( 250, ITENSVENDA.NOMEVEND.ordinal() );

		panelTabItensVenda.add( new JScrollPane( tabItensVenda ) );
		// Gride de produtos vendidos 
		tabProdVendas.adicColuna( "S.V." );
		tabProdVendas.adicColuna( "S.R." );
		tabProdVendas.adicColuna( "Pedido" );
		tabProdVendas.adicColuna( "Doc." );
		tabProdVendas.adicColuna( "Data" );
		tabProdVendas.adicColuna( "Cód.prod." );
		tabProdVendas.adicColuna( "Descrição do produto" );
		tabProdVendas.adicColuna( "Quantidade" );
		tabProdVendas.adicColuna( "Preço" );
		tabProdVendas.adicColuna( "% desc." );
		tabProdVendas.adicColuna( "V.desc." );
		tabProdVendas.adicColuna( "V.líquido" );
		tabProdVendas.adicColuna( "Tipo Venda" );
		tabProdVendas.adicColuna( "Cód.p.pag." );
		tabProdVendas.adicColuna( "Descrição do plano de pagto." );
		tabProdVendas.adicColuna( "Cód.comis." );
		tabProdVendas.adicColuna( "Nome do comissionado" );
		tabProdVendas.setTamColuna( 30, PRODVENDAS.STATUSPGTO.ordinal() );
		tabProdVendas.setTamColuna( 30, PRODVENDAS.STATUSVENDA.ordinal() );
		tabProdVendas.setTamColuna( 55, PRODVENDAS.CODVENDA.ordinal() );
		tabProdVendas.setTamColuna( 55, PRODVENDAS.DOCVENDA.ordinal() );
		tabProdVendas.setTamColuna( 70, PRODVENDAS.DTEMITVENDA.ordinal() );
		tabProdVendas.setTamColuna( 65, PRODVENDAS.CODPROD.ordinal() );
		tabProdVendas.setTamColuna( 250, PRODVENDAS.DESCPROD.ordinal() );
		tabProdVendas.setTamColuna( 80, PRODVENDAS.QTDITVENDA.ordinal() );
		tabProdVendas.setTamColuna( 80, PRODVENDAS.PRECOITVENDA.ordinal() );
		tabProdVendas.setTamColuna( 60, PRODVENDAS.PERCDESCITVENDA.ordinal() );
		tabProdVendas.setTamColuna( 80, PRODVENDAS.VLRDESCITVENDA.ordinal() );
		tabProdVendas.setTamColuna( 90, PRODVENDAS.VLRLIQITVENDA.ordinal() );
		tabProdVendas.setColunaInvisivel( PRODVENDAS.TIPOVENDA.ordinal() );
		tabProdVendas.setTamColuna( 55, PRODVENDAS.CODPLANOPAG.ordinal() );
		tabProdVendas.setTamColuna( 250, PRODVENDAS.DESCPLANOPAG.ordinal() );
		tabProdVendas.setTamColuna( 55, PRODVENDAS.CODVEND.ordinal() );
		tabProdVendas.setTamColuna( 250, PRODVENDAS.NOMEVEND.ordinal() );
		panelTabProdVendas.add( new JScrollPane( tabProdVendas ) );
		// Gride de cestas
		tabCestas.adicColuna( "Sel." );
		tabCestas.adicColuna( "Cód.orç." );
		tabCestas.adicColuna( "Cód.cli." );
		tabCestas.adicColuna( "Razão social" );
		tabCestas.adicColuna( "Data" );
		tabCestas.adicColuna( "Quantidade" );
		tabCestas.adicColuna( "V.desconto" );
		tabCestas.adicColuna( "V.líquido" );
		tabCestas.adicColuna( "Cód.p.pg." );
		tabCestas.adicColuna( "Descrição do plano de pagto." );
		tabCestas.adicColuna( "Cód.comis." );
		tabCestas.adicColuna( "Nome do comissionado" );
		tabCestas.setTamColuna( 30, CESTAS.SELECAO.ordinal() );
		tabCestas.setTamColuna( 60, CESTAS.CODORC.ordinal() );
		tabCestas.setTamColuna( 90, CESTAS.CODCLI.ordinal() );
		tabCestas.setTamColuna( 250, CESTAS.RAZCLI.ordinal() );
		tabCestas.setTamColuna( 90, CESTAS.DATACESTA.ordinal() );
		tabCestas.setTamColuna( 100, CESTAS.QTDCESTA.ordinal() );
		tabCestas.setTamColuna( 100, CESTAS.VLRDESCCESTA.ordinal() );
		tabCestas.setTamColuna( 100, CESTAS.VLRLIQCESTA.ordinal() );
		tabCestas.setTamColuna( 90, CESTAS.CODPLANOPAG.ordinal() );
		tabCestas.setTamColuna( 250, CESTAS.DESCPLANOPAG.ordinal() );
		tabCestas.setTamColuna( 90, CESTAS.CODVEND.ordinal() );
		tabCestas.setTamColuna( 250, CESTAS.NOMEVEND.ordinal() );
		tabCestas.setColunaEditavel( CESTAS.SELECAO.ordinal(), true );
		panelTabCestas.add( new JScrollPane( tabCestas ) );
		// Gride de itens da cesta
		tabItensCesta.adicColuna( "Sel." );
		tabItensCesta.adicColuna( "Cód.prod." );
		tabItensCesta.adicColuna( "Descrição do produto" );
		tabItensCesta.adicColuna( "Quantidade" );
		tabItensCesta.adicColuna( "Preço" );
		tabItensCesta.adicColuna( "V.Desconto" );
		tabItensCesta.adicColuna( "V.líquido" );
		tabItensCesta.setTamColuna( 30, ITENSCESTA.SELECAO.ordinal() );
		tabItensCesta.setTamColuna( 90, ITENSCESTA.CODPROD.ordinal() );
		tabItensCesta.setTamColuna( 300, ITENSCESTA.DESCPROD.ordinal() );
		tabItensCesta.setTamColuna( 60, ITENSCESTA.QTDITCESTA.ordinal() );
		tabItensCesta.setTamColuna( 90, ITENSCESTA.PRECOITCESTA.ordinal() );
		tabItensCesta.setTamColuna( 90, ITENSCESTA.VLRDESCITCESTA.ordinal() );
		tabItensCesta.setTamColuna( 90, ITENSCESTA.VLRLIQITCESTA.ordinal() );
		panelTabItensCesta.add( new JScrollPane( tabItensCesta ) );		
		tabItensCesta.setColunaEditavel( ITENSCESTA.SELECAO.ordinal(), true );
		// ***** Rodapé
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
	}

	private void clearFields() {
		tabVendas.limpa();
		tabItensVenda.limpa();
		tabProdVendas.limpa();
		txtUltimaVenda.setVlrString("");
		txtVlrUltimaVenda.setVlrString( "" );
		txtTotalVendas.setVlrString( "" );
		txtTotalAberto.setVlrString( "" );
		txtTotalAtraso.setVlrString( "" );
		txtMediaVendas.setVlrString( "" );
		txtMaiorVenda.setVlrString( "" );
		txtAtrasoMedio.setVlrString( "" );
	}

	private void loadVendas() {
		clearFields();
		if ( txtCodCli.getVlrInteger() == 0 && txtCodProd.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione um cliente ou produto!" );
			txtCodCli.requestFocus();
			return;
		}
		try {
			loadingVendas = true;
			Integer codempvd = Aplicativo.iCodEmp;
			Integer codfilialvd = ListaCampos.getMasterFilial( "VDVENDA" );
			Integer codemprc = Aplicativo.iCodEmp;
			Integer codfilialrc = ListaCampos.getMasterFilial( "FNRECEBER" );
			Integer codempcl = Aplicativo.iCodEmp;
			Integer codfilialcl = ListaCampos.getMasterFilial( "VDCLIENTE" );
			Integer codcli = txtCodCli.getVlrInteger();
			Integer codemppd = Aplicativo.iCodEmp;
			Integer codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
			Integer codprod = txtCodProd.getVlrInteger();
			Date dtini = txtDataini.getVlrDate();
			Date dtfim = txtDatafim.getVlrDate();
			BigDecimal totalVendas = new BigDecimal(0);
			BigDecimal mediaVendas = new BigDecimal(0);
			BigDecimal maiorVenda = new BigDecimal(0);
			Vector<Vector<Object>> vendas = daoconsultacli.loadVendas(codempvd, codfilialvd
					, codempcl, codfilialcl, codcli, codemppd, codfilialpd, codprod, dtini, dtfim);
			for (Vector<Object> row: vendas) {
				tabVendas.adicLinha( row );
				StringDireita strVlrItem = (StringDireita) row.elementAt(VENDAS.VALOR_LIQUIDO.ordinal());
				ImageIcon imgStatusVenda = (ImageIcon ) row.elementAt(VENDAS.STATUSVENDA.ordinal());
				BigDecimal vlrItem = strVlrItem.getBigDecimal();
				if ( imgStatusVenda!=null && imgStatusVenda==imgCancelado ) {
					vlrItem = BigDecimal.ZERO;
				}
				if (vlrItem!=null) {
					totalVendas = totalVendas.add( vlrItem );
					if (vlrItem.compareTo( maiorVenda )>0) {
						maiorVenda = vlrItem;
					}
				}
			}
			Object[] ultimaVenda = daoconsultacli.loadUltimaVenda(Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDVENDA" )
					, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodCli.getVlrInteger()
					, txtDataini.getVlrDate(), txtDatafim.getVlrDate());
			if ( ultimaVenda[RESULT_ULTVENDA.DTEMITVENDA.ordinal()]!=null ) {
				txtUltimaVenda.setVlrDate( Funcoes.sqlDateToDate( (java.sql.Date) ultimaVenda[RESULT_ULTVENDA.DTEMITVENDA.ordinal()] ) );
				txtVlrUltimaVenda.setVlrBigDecimal( (BigDecimal) ultimaVenda[RESULT_ULTVENDA.VLRLIQVENDA.ordinal()] );
			}
			Object[] receber = daoconsultacli.loadReceber( codemprc, codfilialrc, codempcl
					, codfilialcl, codcli, codemppd, codfilialpd, codprod, dtini, dtfim );
			//BigDecimal totalVendas = (BigDecimal) receber[RESULT_RECEBER.TOTAL_VENDAS.ordinal()];
			BigDecimal totalAberto = (BigDecimal) receber[RESULT_RECEBER.TOTAL_ABERTO.ordinal()];
			BigDecimal totalAtraso = (BigDecimal) receber[RESULT_RECEBER.TOTAL_ATRASO.ordinal()];
			BigDecimal atrasoMedio = (BigDecimal) receber[RESULT_RECEBER.ATRASO_MEDIO.ordinal()];
			if (totalVendas != null && !totalVendas.equals( BigDecimal.ZERO )){
				txtTotalVendas.setVlrBigDecimal( totalVendas );
				long dias = Funcoes.getNumDiasAbs( txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
				double meses = dias / 30;
				BigDecimal numMeses = new BigDecimal(meses); 
				if (numMeses.doubleValue()==0) {
					mediaVendas = BigDecimal.ZERO;
				} else {
					mediaVendas = totalVendas.divide( numMeses, BigDecimal.ROUND_HALF_EVEN );
				}
				if (mediaVendas!=null && !mediaVendas.equals( BigDecimal.ZERO)) {
					txtMediaVendas.setVlrBigDecimal( mediaVendas );
				}
			}
			if (totalAberto != null && !totalAberto.equals( BigDecimal.ZERO )){
				txtTotalAberto.setVlrBigDecimal( totalAberto );
			}
			if (totalAtraso != null  && !totalAtraso.equals( BigDecimal.ZERO )){
				txtTotalAtraso.setVlrBigDecimal( totalAtraso);
			}
			if (atrasoMedio != null && !atrasoMedio.equals( BigDecimal.ZERO )) {
				txtAtrasoMedio.setVlrInteger( atrasoMedio.intValue() );
			}
			if (maiorVenda != null  && !maiorVenda.equals( BigDecimal.ZERO )){
				txtMaiorVenda.setVlrBigDecimal( maiorVenda );
			}
			if (tabVendas.getDataVector().size()>0) {
				tabVendas.setLinhaSel( 0 );
				buscaItensVenda( codempvd, codfilialvd, "V", (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() )
						, codemppd, codfilialpd, codprod);
			}
			tabVendas.requestFocus();
			Vector<Vector<Object>> prodVendas = daoconsultacli.loadProdVendas(codempvd, codfilialvd
					, codempcl, codfilialcl, codcli, codemppd, codfilialpd, codprod, dtini, dtfim);
			for (Vector<Object> row: prodVendas) {
				tabProdVendas.adicLinha( row );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregando vendas !\n"+e.getMessage() );
		} finally {
			loadingVendas = false;
		}
	}
	

	private void buscaItensVenda( Integer codempvd, Integer codfilialvd, String tipovenda, Integer codvenda
			, Integer codemppd, Integer codfilialpd, Integer codprod  ) {

		try {
			tabItensVenda.limpa();
			Vector<Vector<Object>> itensVenda = daoconsultacli.loadItensVenda( codempvd, codfilialvd, tipovenda, codvenda, codemppd, codfilialpd, codprod );
			for (Vector<Object> row: itensVenda) {
				tabItensVenda.adicLinha( row );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			loadVendas();
		} else if ( e.getSource() == btAddCesta ) {
			addCesta();
		} else if ( e.getSource() == btResetCesta ) {
			resetCesta();
		} else if ( e.getSource() == btSelecionar ) {
			selAllCestas(true);
		} else if ( e.getSource() == btDesselecionar ) {
			selAllCestas(false);
		} else if ( e.getSource() == btGerar ) {
			gerarOrcamento();
		}
	}

	private void gerarOrcamento() {
		boolean hasOrcamento = false;
		for (Cesta cesta: cestaFactory.getCestas()) {
			if (cesta.getSel()) {
				hasOrcamento = true;
				break;
			}
		}
		if ( !hasOrcamento ) {
			Funcoes.mensagemInforma( this, "Selecione um orçamento para gerar !" );
			return;
		} else {
			if (Funcoes.mensagemConfirma( this, "Confirma geração do(s) orçamento(s)?")!=JOptionPane.YES_OPTION) {
				return;
			}
		}
		for (Cesta cesta: cestaFactory.getCestas()) {
			if (cesta.getSel() && ( cesta.getCodorc()==null || cesta.getCodorc().intValue()==0) ) {
				Integer codorc = insertOrcamento(cesta);
				if (codorc!=null) {
					cesta.setCodorc( codorc );
					cesta.setSel( false );
					for (Item item: cesta.getItens()) {
						if (item.getSel()) {
							
						}
					}
				}
			}
		}
		loadTabCestas();
	}
	
	private Integer insertOrcamento(Cesta cesta) {
		Integer result = null;
		Integer codemp = Aplicativo.iCodEmp;
		VDOrcamento orcamento = new VDOrcamento();
		orcamento.setCodempcl( codemp );
		orcamento.setCodfilialcl( (short) ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		orcamento.setCodcli( cesta.getCodcli() );
		orcamento.setCodemppg( codemp );
		orcamento.setCodfilialpg( ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
		orcamento.setCodplanopag( cesta.getCodplanopag() );
		orcamento.setDtorc( new Date() );
		orcamento.setCodempvd( codemp );
		orcamento.setCodfilialvd( (short) ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
		orcamento.setCodvend( cesta.getCodvend() );
		//orcamento.setDtpreventorc( )
		try {
			if ( daoorcamento.insertOrcamento( orcamento, preforc ) ) {
				result = orcamento.getCodorc();
			}
		} catch (Exception err) {
			Funcoes.mensagemErro( this, err.getMessage());
			return result;
		}
		return result;
	}
	
	private void selAllCestas(boolean sel) {
		for (Cesta cesta: cestaFactory.getCestas() ) {
			cesta.setSel( sel );
			for (Item item: cesta.getItens()) {
				item.setSel( sel );
			}
		}
		loadTabCestas();
	}
	
	private void resetCesta() {
		if (Funcoes.mensagemConfirma( this, "Confirma exclusão de todas as cestas da memória ?" )==JOptionPane.YES_OPTION) {
			cestaFactory.resetCestas();
			loadTabCestas();
		}
	}
	
	private void addCesta() {
		int selectedRow = -1;
		boolean prodShowing = panelProdVendas.isShowing();
		if (prodShowing) {
			selectedRow = tabProdVendas.getSelectedRow();
		} else {
			selectedRow = tabItensVenda.getSelectedRow();
		}
		if ( selectedRow == -1 ) {
			Funcoes.mensagemInforma( this, "Selecione um item para inclusão na lista !" );
			//tabItensVenda.requestFocus();
			return;
		}
		Integer codempcl = Aplicativo.iCodEmp;
		Integer codfilialcl = ListaCampos.getMasterFilial( "VDCLIENTE" );
		Integer codcli = txtCodCli.getVlrInteger();
		String razcli = txtRazCli.getVlrString();
		Integer codemppd = Aplicativo.iCodEmp;
		Integer codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
		Integer codprod;
		String descprod;
		Integer codplanopag;
		String descplanopag;
		Integer codvend;
		String nomevend;
		BigDecimal qtditvenda;
		BigDecimal precoitvenda;
		BigDecimal percdescitvenda;
		BigDecimal vlrdescitvenda;
		BigDecimal vlrliqitvenda;
		if (prodShowing) {
			codprod = (Integer) tabProdVendas.getValor( selectedRow, PRODVENDAS.CODPROD.ordinal() );
			descprod = (String) tabProdVendas.getValor( selectedRow, PRODVENDAS.DESCPROD.ordinal() );
			qtditvenda = ((StringDireita) tabProdVendas.getValor( selectedRow, PRODVENDAS.QTDITVENDA.ordinal() )).getBigDecimal();
			precoitvenda = ((StringDireita) tabProdVendas.getValor( selectedRow, PRODVENDAS.PRECOITVENDA.ordinal() )).getBigDecimal();
			percdescitvenda = ((StringDireita) tabProdVendas.getValor( selectedRow, PRODVENDAS.PERCDESCITVENDA.ordinal() )).getBigDecimal();
			vlrdescitvenda = ((StringDireita) tabProdVendas.getValor( selectedRow, PRODVENDAS.VLRDESCITVENDA.ordinal() )).getBigDecimal();
			vlrliqitvenda = ((StringDireita) tabProdVendas.getValor( selectedRow, PRODVENDAS.VLRLIQITVENDA.ordinal() )).getBigDecimal();
			codplanopag = (Integer) tabProdVendas.getValor( selectedRow, PRODVENDAS.CODPLANOPAG.ordinal() );
			descplanopag = (String) tabProdVendas.getValor( selectedRow, PRODVENDAS.DESCPLANOPAG.ordinal() );
			codvend = (Integer) tabProdVendas.getValor( selectedRow, PRODVENDAS.CODVEND.ordinal() );
			nomevend = (String) tabProdVendas.getValor( selectedRow, PRODVENDAS.NOMEVEND.ordinal() );
		} else {
			codprod = (Integer) tabItensVenda.getValor( selectedRow, ITENSVENDA.CODPROD.ordinal() );
			descprod = (String) tabItensVenda.getValor( selectedRow, ITENSVENDA.DESCPROD.ordinal() );
			qtditvenda = ((StringDireita) tabItensVenda.getValor( selectedRow, ITENSVENDA.QTDITVENDA.ordinal() )).getBigDecimal();
			precoitvenda = ((StringDireita) tabItensVenda.getValor( selectedRow, ITENSVENDA.PRECOITVENDA.ordinal() )).getBigDecimal();
			percdescitvenda = new BigDecimal(0);
			vlrdescitvenda = ((StringDireita) tabItensVenda.getValor( selectedRow, ITENSVENDA.VLRDESCITVENDA.ordinal() )).getBigDecimal();
			vlrliqitvenda = ((StringDireita) tabItensVenda.getValor( selectedRow, ITENSVENDA.VLRLIQITVENDA.ordinal() )).getBigDecimal();
			codplanopag = (Integer) tabItensVenda.getValor( selectedRow, ITENSVENDA.CODPLANOPAG.ordinal() );
			descplanopag = (String) tabItensVenda.getValor( selectedRow, ITENSVENDA.DESCPLANOPAG.ordinal() );
			codvend = (Integer) tabItensVenda.getValor( selectedRow, ITENSVENDA.CODVEND.ordinal() );
			nomevend = (String) tabItensVenda.getValor( selectedRow, ITENSVENDA.NOMEVEND.ordinal() );
		}
		//Item
		Item item = new Item(codemppd, codfilialpd, codprod, descprod);
		item.setQtd( qtditvenda );
		item.setPreco( precoitvenda );
		item.setPercdesc( percdescitvenda );
		item.setVlrdesc( vlrdescitvenda );
		item.setVlrliq( vlrliqitvenda );
		DLConfirmItem dlconfirm = new DLConfirmItem(this);
		dlconfirm.setValues( item );
		dlconfirm.setVisible( true );
		if (dlconfirm.OK) {
			item = dlconfirm.getResult();
			dlconfirm.dispose();
			if (item!=null) {
				Cesta cesta = cestaFactory.createNewCesta(codempcl, codfilialcl, codcli, razcli, codplanopag, descplanopag);
				if (cesta!=null) {
					cesta.addItem( item );
				}
			}
		}
		
	}
	
	private void loadTabVendas() {
		Integer codempvd = Aplicativo.iCodEmp;
		Integer codfilialvd = ListaCampos.getMasterFilial( "VDVENDA" );
		Integer codemppd = Aplicativo.iCodEmp;
		Integer codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
		Integer codprod = txtCodProd.getVlrInteger();
		String tipovenda = (String) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.TIPOVENDA.ordinal() );
		Integer codvenda = (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() );
		buscaItensVenda( codempvd, codfilialvd, tipovenda, codvenda, codemppd, codfilialpd, codprod );
	}

	private void loadTabCestas() {
		if (cestaFactory!=null) {
			List<Cesta> cestas = cestaFactory.getCestas();
			tabCestas.limpa();
			if (cestas.size()>0) {
				int row = 0;
				for (Cesta cesta: cestas) {
					tabCestas.adicLinha();
					tabCestas.setValor( cesta.getSel(), row, CESTAS.SELECAO.ordinal() );
					tabCestas.setValor( cesta.getCodorc(), row, CESTAS.CODORC.ordinal() );
					tabCestas.setValor( cesta.getCodcli(), row, CESTAS.CODCLI.ordinal() );
					tabCestas.setValor( cesta.getRazcli(), row, CESTAS.RAZCLI.ordinal() );
					tabCestas.setValor( cesta.getDatacesta(), row, CESTAS.DATACESTA.ordinal() );
					tabCestas.setValor( cesta.getQtdcesta(), row, CESTAS.QTDCESTA.ordinal() );
					tabCestas.setValor( cesta.getVlrdesccesta(), row, CESTAS.VLRDESCCESTA.ordinal() );
					tabCestas.setValor( cesta.getVlrliqcesta(), row, CESTAS.VLRLIQCESTA.ordinal() );
					tabCestas.setValor( cesta.getCodplanopag(), row, CESTAS.CODPLANOPAG.ordinal() );
					tabCestas.setValor( cesta.getDescplanopag(), row, CESTAS.DESCPLANOPAG.ordinal() );
					tabCestas.setValor( cesta.getCodvend(), row, CESTAS.CODVEND.ordinal() );
					tabCestas.setValor( cesta.getNomevend(), row, CESTAS.NOMEVEND.ordinal() );
					row ++;
				}
			}
			if (! loadingItensCesta ) {
				loadTabItensCesta();
			}
		}
	}
	public void loadTabItensCesta() {
		loadingItensCesta = true;
		try {		
			tabItensCesta.limpa();
			if (tabCestas.getNumLinhas()>0) {
				int selectedRow = tabCestas.getSelectedRow();
				if (selectedRow==-1) {
					selectedRow = 0;
				}
				Integer codemp = Aplicativo.iCodEmp;
				Integer codfilial = ListaCampos.getMasterFilial( "VDCLIENTE" );
				Integer codcli = (Integer) tabCestas.getValor( selectedRow, CESTAS.CODCLI.ordinal() );
				Cesta cesta = cestaFactory.getCesta( codemp, codfilial, codcli );
				if (cesta!=null) {
					loadTabItensCesta(cesta);
				}
			}
		} finally {
			loadingItensCesta = false;
		}		
	}
	public void loadTabItensCesta(Cesta cesta) {

			if (cesta!=null) {
				int row = 0;
				tabItensCesta.limpa();
				for (Item item: cesta.getItens()) {
					tabItensCesta.adicLinha();
					tabItensCesta.setValor( item.getSel(), row, ITENSCESTA.SELECAO.ordinal() );
					tabItensCesta.setValor( item.getCodprod(), row, ITENSCESTA.CODPROD.ordinal() );
					tabItensCesta.setValor( item.getDescprod(), row, ITENSCESTA.DESCPROD.ordinal() );
					tabItensCesta.setValor( item.getQtd(), row, ITENSCESTA.QTDITCESTA.ordinal() );
					tabItensCesta.setValor( item.getPreco(), row, ITENSCESTA.PRECOITCESTA.ordinal() );
					tabItensCesta.setValor( item.getVlrdesc(), row, ITENSCESTA.VLRDESCITCESTA.ordinal() );
					tabItensCesta.setValor( item.getVlrliq(), row, ITENSCESTA.VLRLIQITCESTA.ordinal() );
					row ++;
				}
			}

	}
	public void valorAlterado( TabelaSelEvent e ) {

		if ( e.getTabela() == tabVendas && tabVendas.getLinhaSel() > -1 && !loadingVendas ) {
			loadTabVendas();
		} else if (e.getTabela() == tabCestas && !loadingCestas) {
			loadTabItensCesta();
		}
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getClickCount() == 2 ) {
			if ( e.getSource() == tabVendas && tabVendas.getLinhaSel() > -1 ) {
				FVenda venda = null;
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}
				venda.exec( (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() )
						, (String) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.TIPOVENDA.ordinal() ) );
			} else if ( e.getSource() == tabItensVenda && tabItensVenda.getLinhaSel() > -1 ) {
				FVenda venda = null;
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}
				venda.exec( (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() )
						, (Integer) tabItensVenda.getValor( tabItensVenda.getLinhaSel(), ITENSVENDA.CODITVENDA.ordinal() )
						, (String) tabItensVenda.getValor( tabItensVenda.getLinhaSel(), ITENSVENDA.TIPOVENDA
						.ordinal() ) );
			} /* else if ( e.getSource() == tabCestas ) {
				int selectedRow = tabCestas.getLinhaSel();
				if (selectedRow>-1) {
					Boolean sel = (Boolean) tabCestas.getValor( selectedRow, CESTAS.SELECAO.ordinal() );
					sel = new Boolean(!sel.booleanValue());
					
				}
			}*/
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) {

	}

	public void keyTyped( KeyEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( lcCliente == e.getListaCampos() ) {
			if ( "S".equals( txtAtivoCli.getVlrString().trim() ) ) {
				lbAtivoCli.setText( "Ativo" );
				lbAtivoCli.setBackground( GREEN );
			}
			else {
				lbAtivoCli.setText( "Inativo" );
				lbAtivoCli.setBackground( Color.RED );
			}
			loadVendas();
		}
		if ( lcProd == e.getListaCampos() ) {
			loadVendas();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		// Carregar a cesta de compras
		daoconsultacli = new DAOConsultaCli( cn, imgVencida, imgAVencer, imgPgEmAtraso
				, imgPgEmDia, imgCancelado, imgPedido, imgFaturado );
		cestaFactory = Aplicativo.getInstace().getCestaFactory();
		daoorcamento = new DAOOrcamento( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
		try {
			preforc = daoorcamento.getPrefere( ListaCampos.getMasterFilial( "SGPREFERE1" ),  ListaCampos.getMasterFilial( "SGESTACAOIMP" ), Aplicativo.iNumEst );
		} catch (Exception err) {
			Funcoes.mensagemErro( null, err.getMessage() );
			dispose();
			return;
		}
		
	}

	public void focusGained( FocusEvent e ) {
		
		if (e.getSource()==txtCodCli) {
			codcliatual = txtCodCli.getVlrInteger();
		}
		
	}

	public void focusLost( FocusEvent e ) {

		if (e.getSource()==txtCodCli) {
			if ( codcliatual!=null  && !codcliatual.equals(txtCodCli.getVlrInteger()) && txtCodCli.getVlrInteger().intValue()==0) {
				clearFields();
			}
		}
	}

	public void stateChanged( ChangeEvent e ) {

		if (e.getSource() == tabbedDetail) {
			//System.out.println(tabbedDetail.getSelectedComponent().getName());
			if (tabbedDetail.getSelectedComponent()==panelCestas) {
				pinToolBarVendas.setVisible( false );
				pinToolBarCesta.setVisible( true );
				loadTabCestas();
			} else  {
				pinToolBarVendas.setVisible( true );
				pinToolBarCesta.setVisible( false );
			}
		}
		
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		if (evt.getTabela()==tabCestas && !loadingCestas && !loadingItensCesta) {
			int editedRow = tabCestas.getLinhaEditada();
			int editedCol = tabCestas.getColunaEditada();
			if (editedRow>-1 && editedCol==CESTAS.SELECAO.ordinal()) {
				boolean sel = (Boolean) tabCestas.getValor( editedRow, CESTAS.SELECAO.ordinal() );
				Cesta cesta = cestaFactory.getCestas().get( editedRow );
				cesta.setSel( sel );
				for (Item item: cesta.getItens()) {
					item.setSel( sel );
				}
/*				for (int i=0; i<tabItensCesta.getNumLinhas(); i++) {
					tabItensCesta.setValor( sel, i, ITENSCESTA.SELECAO.ordinal() );
				} */
				loadTabItensCesta();
			}
		}
	}
}
