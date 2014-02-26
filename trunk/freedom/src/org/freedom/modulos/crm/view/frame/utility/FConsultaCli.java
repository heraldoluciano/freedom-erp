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
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
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
import org.freedom.modulos.crm.dao.DAOConsultaCli;
import org.freedom.modulos.crm.dao.DAOConsultaCli.ITENSVENDA;
import org.freedom.modulos.crm.dao.DAOConsultaCli.RESULT_RECEBER;
import org.freedom.modulos.crm.dao.DAOConsultaCli.RESULT_ULTVENDA;
import org.freedom.modulos.crm.dao.DAOConsultaCli.VENDAS;
import org.freedom.modulos.std.orcamento.bussiness.CestaFactory;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;

/**
 * Consulta de informações referente a clientes.
 * 
 * @author Setpoint Informática Ltda./Alex Rodrigues
 * @version 24/08/2009
 */
public class FConsultaCli extends FFilho implements ActionListener, TabelaSelListener, MouseListener
			, KeyListener, CarregaListener, FocusListener {

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

	private JPanelPad panelCesta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelResumoVendas = new JPanelPad( 700, 73 );

	private JPanelPad panelTabProdVendas = new JPanelPad( 700, 125 );
	
	private JPanelPad panelGridVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelGridProdVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabVendasNotas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabItensVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );

	// *** Geral

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

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

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTablePad tabVendas = new JTablePad();

	private JTablePad tabItensVenda = new JTablePad();
	
	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcProd = new ListaCampos( this );

	private boolean carregandoVendas = false;
	
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
	
	public FConsultaCli() {

		super( false );
		setTitulo( "Consulta de clientes", this.getClass().getName() );
		setAtribos( 20, 20, 780, 600 );
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
		tabItensVenda.addMouseListener( this );
		btBuscar.addKeyListener( this );
		txtCodCli.addFocusListener( this );
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

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		// ***** Cabeçalho
		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		panelMaster.adic( new JLabelPad( "Cód.Cli" ), 7, 5, 60, 20 );
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
		panelMaster.adic( new JLabelPad( "Cód.Prod." ), 7, 85, 60, 20 );
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

		panelResumoVendas.setPreferredSize( new Dimension( 700, 73 ) );
		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelDetail.add( panelResumoVendas, BorderLayout.NORTH );
		panelDetail.add( tabbedDetail, BorderLayout.CENTER );
		
		tabbedVendas.addTab( "Todas", panelTodasVendas );
		tabbedVendas.addTab( "Produtos", panelProdVendas );
	
		tabbedDetail.addTab("Vendas", tabbedVendas);
		tabbedDetail.addTab( "Cesta", panelCesta );

		// ***** Todas Vendas

		//panelTodasVendas.add( panelResumoVendas, BorderLayout.NORTH );
		panelTodasVendas.add( panelGridVendas, BorderLayout.CENTER );
		panelGridVendas.add( panelTabVendasNotas );
		panelGridVendas.add( panelTabItensVendas );

		panelTabVendasNotas.setBorder( BorderFactory.createTitledBorder( "Vendas" ) );
		panelTabItensVendas.setBorder( BorderFactory.createTitledBorder( "Itens da venda selecionada" ) );
		panelTabItensVendas.setPreferredSize( new Dimension( 700, 120 ) );

		// ***** Prod Vendas
		panelProdVendas.add( panelGridProdVendas, BorderLayout.CENTER );
		panelGridProdVendas.add( panelTabProdVendas );
		panelTabProdVendas.setBorder( BorderFactory.createTitledBorder( "Itens adquiridos no perído" ) );
		// Final prodVendas
		
		panelResumoVendas.adic( new JLabelPad( "Última Venda" ), 10, 10, 90, 20 );
		panelResumoVendas.adic( txtUltimaVenda, 10, 30, 90, 20 );
		panelResumoVendas.adic( new JLabelPad( "Vlr. últ. venda" ), 103, 10, 95, 20 );
		panelResumoVendas.adic( txtVlrUltimaVenda, 103, 30, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Total de vendas" ), 201, 10, 95, 20 );
		panelResumoVendas.adic( txtTotalVendas, 201, 30, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Valor em aberto" ), 299, 10, 95, 20 );
		panelResumoVendas.adic( txtTotalAberto, 299, 30, 95, 20 );
		panelResumoVendas.adic( new JLabelPad( "Valor em atraso" ), 397, 10, 95, 20 );
		panelResumoVendas.adic( txtTotalAtraso, 397, 30, 95, 20 );

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

		tabVendas.adicColuna( "" );
		tabVendas.adicColuna( "Código" );
		tabVendas.adicColuna( "Doc." );
		tabVendas.adicColuna( "Data" );
		tabVendas.adicColuna( "Plano Pgto." );
		tabVendas.adicColuna( "Vendedor" );
		tabVendas.adicColuna( "V.Produtos" );
		tabVendas.adicColuna( "V.Desc." );
		tabVendas.adicColuna( "V.Adic." );
		tabVendas.adicColuna( "Tp.Frete" );
		tabVendas.adicColuna( "V.Líquido" );
		tabVendas.adicColuna( "Tipo Venda" );
		tabVendas.adicColuna( "" );

		tabVendas.setTamColuna( 20, VENDAS.STATUSPGTO.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.CODVENDA.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.NOTA.ordinal() );
		tabVendas.setTamColuna( 70, VENDAS.DATA.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.PAGAMENTO.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.VENDEDOR.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_PRODUTOS.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_DESCONTO.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_ADICIONAL.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.FRETE.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_LIQUIDO.ordinal() );
		tabVendas.setTamColuna( 20, VENDAS.STATUS.ordinal() );
		tabVendas.setColunaInvisivel( VENDAS.TIPOVENDA.ordinal() );

		panelTabVendasNotas.add( new JScrollPane( tabVendas ) );

		tabItensVenda.adicColuna( "Item" );
		tabItensVenda.adicColuna( "Código" );
		tabItensVenda.adicColuna( "Descrição do produto" );
		tabItensVenda.adicColuna( "Lote" );
		tabItensVenda.adicColuna( "Qtd." );
		tabItensVenda.adicColuna( "Preço" );
		tabItensVenda.adicColuna( "V.Desc." );
		tabItensVenda.adicColuna( "V.Frete" );
		tabItensVenda.adicColuna( "V.líq." );
		tabItensVenda.adicColuna( "TipoVenda" );

		tabItensVenda.setTamColuna( 30, ITENSVENDA.ITEM.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.CODPROD.ordinal() );
		tabItensVenda.setTamColuna( 300, ITENSVENDA.DESCPROD.ordinal() );
		tabItensVenda.setTamColuna( 70, ITENSVENDA.LOTE.ordinal() );
		tabItensVenda.setTamColuna( 50, ITENSVENDA.QUANTIDADE.ordinal() );
		tabItensVenda.setTamColuna( 70, ITENSVENDA.PRECO.ordinal() );
		tabItensVenda.setTamColuna( 80, ITENSVENDA.DESCONTO.ordinal() );
		tabItensVenda.setTamColuna( 80, ITENSVENDA.FRETE.ordinal() );
		tabItensVenda.setTamColuna( 90, ITENSVENDA.TOTAL.ordinal() );
		tabItensVenda.setColunaInvisivel( ITENSVENDA.TIPOVENDA.ordinal() );

		panelTabItensVendas.add( new JScrollPane( tabItensVenda ) );

		// ***** Rodapé

		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
	}

	private void clearFields() {
		tabVendas.limpa();
		tabItensVenda.limpa();
		txtUltimaVenda.setVlrString("");
		txtVlrUltimaVenda.setVlrString( "" );
		txtTotalVendas.setVlrString( "" );
		txtTotalAberto.setVlrString( "" );
		txtTotalAtraso.setVlrString( "" );
	}

	private void loadVendas() {
		clearFields();
		if ( txtCodCli.getVlrInteger() == 0 && txtCodProd.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione um cliente ou produto!" );
			txtCodCli.requestFocus();
			return;
		}
		try {
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
			Vector<Vector<Object>> vendas = daoconsultacli.loadVendas(codempvd, codfilialvd
					, codempcl, codfilialcl, codcli, codemppd, codfilialpd, codprod, dtini, dtfim);
			carregandoVendas = true;
			for (Vector<Object> row: vendas) {
				tabVendas.adicLinha( row );
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
			BigDecimal totalVendas = (BigDecimal) receber[RESULT_RECEBER.TOTAL_VENDAS.ordinal()];
			BigDecimal totalAberto = (BigDecimal) receber[RESULT_RECEBER.TOTAL_ABERTO.ordinal()];
			BigDecimal totalAtraso = (BigDecimal) receber[RESULT_RECEBER.TOTAL_ATRASO.ordinal()];
			if (totalVendas != null && !totalVendas.equals( BigDecimal.ZERO )){
				txtTotalVendas.setVlrBigDecimal( totalVendas );
			}
			if (totalAberto != null && !totalAberto.equals( BigDecimal.ZERO )){
				txtTotalAberto.setVlrBigDecimal( totalAberto );
			}
			if (totalAtraso != null  && !totalAtraso.equals( BigDecimal.ZERO )){
				txtTotalAtraso.setVlrBigDecimal( totalAtraso);
			}
			if (tabVendas.getDataVector().size()>0) {
				tabVendas.setLinhaSel( 0 );
				buscaItensVenda( codempvd, codfilialvd, "V", (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() )
						, codemppd, codfilialpd, codprod);
			}
			carregandoVendas = false;
			tabVendas.requestFocus();
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregando vendas !\n"+e.getMessage() );
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
		}
	}

	public void valorAlterado( TabelaSelEvent e ) {

		if ( e.getTabela() == tabVendas && tabVendas.getLinhaSel() > -1 && !carregandoVendas ) {
			Integer codempvd = Aplicativo.iCodEmp;
			Integer codfilialvd = ListaCampos.getMasterFilial( "VDVENDA" );
			Integer codemppd = Aplicativo.iCodEmp;
			Integer codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
			Integer codprod = txtCodProd.getVlrInteger();
			String tipovenda = (String) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.TIPOVENDA.ordinal() );
			Integer codvenda = (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() );
			buscaItensVenda( codempvd, codfilialvd, tipovenda, codvenda, codemppd, codfilialpd, codprod );
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
			}
			else if ( e.getSource() == tabItensVenda && tabItensVenda.getLinhaSel() > -1 ) {
				FVenda venda = null;
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}
				venda.exec( (Integer) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() )
						, (Integer) tabItensVenda.getValor( tabItensVenda.getLinhaSel(), ITENSVENDA.ITEM.ordinal() )
						, (String) tabItensVenda.getValor( tabItensVenda.getLinhaSel(), ITENSVENDA.TIPOVENDA
						.ordinal() ) );
			}
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
		// Montar a tela após a instância de daoconsultacli


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
}
