/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FManutPag.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de manutenção de contas a pagar.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import org.freedom.componentes.JTabbedPanePad;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutPag extends FFilho implements ActionListener, KeyListener, CarregaListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 0, 4 ) );

	private JPanelPad pnTabConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesConsulta = new JPanelPad( 40, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinConsulta = new JPanelPad( 500, 140 );

	private JPanelPad pnConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private Tabela tabConsulta = new Tabela();

	private JScrollPane spnConsulta = new JScrollPane( tabConsulta );

	private JPanelPad pnTabBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesBaixa = new JPanelPad( 40, 100 );

	private JPanelPad pinBaixa = new JPanelPad( 500, 140 );

	private JPanelPad pnBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private Tabela tabBaixa = new Tabela();

	private JScrollPane spnBaixa = new JScrollPane( tabBaixa );

	private JPanelPad pnTabManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesManut = new JPanelPad( 40, 180 );

	private JPanelPad pinManut = new JPanelPad( 500, 100 );

	private JPanelPad pnManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private Tabela tabManut = new Tabela();

	private JScrollPane spnManut = new JScrollPane( tabManut );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodForManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtPrimCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtUltCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrMaxFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataMaxFat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrTotCompr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataMaxAcum = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCodPagBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodCompraBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodForBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtTotPagBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtJurosBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDatainiManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazForManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazForBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbFiltroStatus = new JLabelPad( "Filtrar por:" );

	private JPanelPad pinLbFiltroStatus = new JPanelPad( 53, 15 );

	private JPanelPad pinFiltroStatus = new JPanelPad( 300, 150 );

	private JButton btBaixaConsulta = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btBaixaManut = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btEditManut = new JButton( Icone.novo( "btEditar.gif" ) );

	private JButton btNovoManut = new JButton( Icone.novo( "btNovo.gif" ) );

	private JButton btExcluirManut = new JButton( Icone.novo( "btExcluir.gif" ) );

	private JButton btEstManut = new JButton( Icone.novo( "btCancelar.gif" ) );

	private JButton btExecManut = new JButton( Icone.novo( "btExecuta.gif" ) );

	private JButton btBaixa = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private JCheckBoxPad cbPagas = new JCheckBoxPad( "Pagas", "S", "N" );

	private JCheckBoxPad cbAPagar = new JCheckBoxPad( "À Pagar", "S", "N" );

	private JCheckBoxPad cbPagParcial = new JCheckBoxPad( "Pag. Parcial", "S", "N" );

	private JRadioGroup rgData = null;

	private JRadioGroup rgVenc = null;

	private Vector vCodPag = new Vector();

	private Vector vNParcPag = new Vector();

	private Vector vNParcBaixa = new Vector();

	private Vector vCodPed = new Vector();

	private Vector vNumContas = new Vector();

	private Vector vCodPlans = new Vector();

	private Vector vCodCCs = new Vector();

	private Vector vDtEmiss = new Vector();

	private Vector vValsData = new Vector();

	private Vector vLabsData = new Vector();

	private Vector vValsVenc = new Vector();

	private Vector vLabsVenc = new Vector();

	private ImageIcon imgVencido = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgPagoParcial = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgNaoVencido = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgColuna = null;

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcForBaixa = new ListaCampos( this );

	private ListaCampos lcForManut = new ListaCampos( this );

	private ListaCampos lcCompraBaixa = new ListaCampos( this );

	private ListaCampos lcPagBaixa = new ListaCampos( this );

	private ListaCampos lcBancoBaixa = new ListaCampos( this );

	private Date dIniManut = null;

	private Date dFimManut = null;

	private int iCodPag = 0;

	private int iNParcPag = 0;

	private int iAnoCC = 0;

	public FManutPag() {

		super( false );
		setTitulo( "Manutenção de contas a pagar" );
		setAtribos( 20, 20, 750, 450 );

		cbAPagar.setVlrString( "S" );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( tpn, BorderLayout.CENTER );
		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnLegenda.add( new JLabelPad( "Vencido", imgVencido, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Parcial", imgPagoParcial, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Pago", imgPago, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "À vencer", imgNaoVencido, SwingConstants.CENTER ) );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 500, 32 ) );

		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnLegenda, BorderLayout.WEST );

		btSair.addActionListener( this );

		// Consulta:

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Descrição do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );

		txtPrimCompr.setAtivo( false );
		txtUltCompr.setAtivo( false );
		txtDataMaxFat.setAtivo( false );
		txtVlrMaxFat.setAtivo( false );
		txtVlrTotCompr.setAtivo( false );
		txtVlrTotPago.setAtivo( false );
		txtVlrTotAberto.setAtivo( false );
		txtDataMaxAcum.setAtivo( false );
		txtVlrMaxAcum.setAtivo( false );

		Funcoes.setBordReq( txtCodFor );

		tpn.addTab( "Consulta", pnConsulta );

		btBaixaConsulta.setToolTipText( "Baixa" );

		pnConsulta.add( pinConsulta, BorderLayout.NORTH );
		pnTabConsulta.add( pinBotoesConsulta, BorderLayout.EAST );
		pnTabConsulta.add( spnConsulta, BorderLayout.CENTER );
		pnConsulta.add( pnTabConsulta, BorderLayout.CENTER );

		pinConsulta.adic( new JLabelPad( "Cód.for." ), 7, 0, 250, 20 );
		pinConsulta.adic( txtCodFor, 7, 20, 80, 20 );
		pinConsulta.adic( new JLabelPad( "Descrição do fornecedor" ), 90, 0, 250, 20 );
		pinConsulta.adic( txtRazFor, 90, 20, 217, 20 );
		pinConsulta.adic( new JLabelPad( "Primeira compra" ), 310, 0, 98, 20 );
		pinConsulta.adic( txtPrimCompr, 310, 20, 98, 20 );
		pinConsulta.adic( new JLabelPad( "Ultima compra" ), 411, 0, 97, 20 );
		pinConsulta.adic( txtUltCompr, 411, 20, 100, 20 );
		pinConsulta.adic( new JLabelPad( "Data" ), 7, 40, 98, 20 );
		pinConsulta.adic( txtDataMaxFat, 7, 60, 98, 20 );
		pinConsulta.adic( new JLabelPad( "Valor da maior fatura" ), 108, 40, 139, 20 );
		pinConsulta.adic( txtVlrMaxFat, 108, 60, 139, 20 );
		pinConsulta.adic( new JLabelPad( "Data" ), 250, 40, 98, 20 );
		pinConsulta.adic( txtDataMaxAcum, 250, 60, 120, 20 );
		pinConsulta.adic( new JLabelPad( "Valor do maior acumulo" ), 373, 40, 139, 20 );
		pinConsulta.adic( txtVlrMaxAcum, 373, 60, 139, 20 );
		pinConsulta.adic( new JLabelPad( "Total de compras" ), 7, 80, 150, 20 );
		pinConsulta.adic( txtVlrTotCompr, 7, 100, 165, 20 );
		pinConsulta.adic( new JLabelPad( "Total pago" ), 175, 80, 97, 20 );
		pinConsulta.adic( txtVlrTotPago, 175, 100, 165, 20 );
		pinConsulta.adic( new JLabelPad( "Total em aberto" ), 343, 80, 117, 20 );
		pinConsulta.adic( txtVlrTotAberto, 343, 100, 167, 20 );
		pinBotoesConsulta.adic( btBaixaConsulta, 5, 10, 30, 30 );

		tabConsulta.adicColuna( "" );
		tabConsulta.adicColuna( "Vencimento" );
		tabConsulta.adicColuna( "Série" );
		tabConsulta.adicColuna( "Doc." );
		tabConsulta.adicColuna( "Cód. compra" );
		tabConsulta.adicColuna( "Data da compra" );
		tabConsulta.adicColuna( "Valor" );
		tabConsulta.adicColuna( "Data pagamento" );
		tabConsulta.adicColuna( "Valor pago" );
		tabConsulta.adicColuna( "Atraso" );
		tabConsulta.adicColuna( "Observações" );
		tabConsulta.adicColuna( "Banco" );

		tabConsulta.setTamColuna( 0, 0 );
		tabConsulta.setTamColuna( 90, 1 );
		tabConsulta.setTamColuna( 50, 2 );
		tabConsulta.setTamColuna( 50, 3 );
		tabConsulta.setTamColuna( 90, 4 );
		tabConsulta.setTamColuna( 110, 5 );
		tabConsulta.setTamColuna( 90, 6 );
		tabConsulta.setTamColuna( 110, 7 );
		tabConsulta.setTamColuna( 100, 8 );
		tabConsulta.setTamColuna( 60, 9 );
		tabConsulta.setTamColuna( 100, 10 );
		tabConsulta.setTamColuna( 80, 11 );

		// Baixa:

		lcCompraBaixa.add( new GuardaCampo( txtCodCompraBaixa, "CodCompra", "Cód.compra", ListaCampos.DB_PK, false ) );
		lcCompraBaixa.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcCompraBaixa.montaSql( false, "COMPRA", "CP" );
		lcCompraBaixa.setQueryCommit( false );
		lcCompraBaixa.setReadOnly( true );
		txtCodCompraBaixa.setTabelaExterna( lcCompraBaixa );
		txtCodCompraBaixa.setFK( true );
		txtCodCompraBaixa.setNomeCampo( "CodCompra" );

		lcForBaixa.add( new GuardaCampo( txtCodForBaixa, "CodFor", "Cód.for", ListaCampos.DB_PK, false ) );
		lcForBaixa.add( new GuardaCampo( txtRazForBaixa, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForBaixa.montaSql( false, "FORNECED", "CP" );
		lcForBaixa.setQueryCommit( false );
		lcForBaixa.setReadOnly( true );
		txtCodForBaixa.setTabelaExterna( lcForBaixa );
		txtCodForBaixa.setFK( true );
		txtCodForBaixa.setNomeCampo( "CodFor" );

		lcBancoBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBancoBaixa.add( new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome banco", ListaCampos.DB_SI, false ) );
		lcBancoBaixa.montaSql( false, "BANCO", "FN" );
		lcBancoBaixa.setQueryCommit( false );
		lcBancoBaixa.setReadOnly( true );
		txtCodBancoBaixa.setTabelaExterna( lcBancoBaixa );
		txtCodBancoBaixa.setFK( true );
		txtCodBancoBaixa.setNomeCampo( "CodBanco" );

		lcPagBaixa.add( new GuardaCampo( txtCodPagBaixa, "CodPag", "Cód.pag", ListaCampos.DB_PK, false ) );
		lcPagBaixa.add( new GuardaCampo( txtCodCompraBaixa, "CodCompra", "Cód.compra", ListaCampos.DB_FK, false ) );
		lcPagBaixa.add( new GuardaCampo( txtDoc, "DocPag", "Doc.", ListaCampos.DB_SI, false ) );
		lcPagBaixa.add( new GuardaCampo( txtTotPagBaixa, "VlrPag", "Total pag.", ListaCampos.DB_SI, false ) );
		lcPagBaixa.add( new GuardaCampo( txtCodForBaixa, "CodFor", "Cód.for", ListaCampos.DB_FK, false ) );
		lcPagBaixa.add( new GuardaCampo( txtDtEmisBaixa, "DataPag", "Data emis.", ListaCampos.DB_SI, false ) );
		lcPagBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcPagBaixa.add( new GuardaCampo( txtTotAbertoBaixa, "VlrApagPag", "Total aberto", ListaCampos.DB_SI, false ) );
		lcPagBaixa.add( new GuardaCampo( txtTotPagoBaixa, "VlrPagoPag", "Total pago", ListaCampos.DB_SI, false ) );
		lcPagBaixa.add( new GuardaCampo( txtJurosBaixa, "VlrJurosPag", "Total juros", ListaCampos.DB_SI, false ) );
		lcPagBaixa.montaSql( false, "PAGAR", "FN" );
		lcPagBaixa.setQueryCommit( false );
		lcPagBaixa.setReadOnly( true );

		txtCodPagBaixa.setTabelaExterna( lcPagBaixa );
		txtCodPagBaixa.setFK( true );
		txtCodPagBaixa.setNomeCampo( "CodPag" );
		txtDoc.setAtivo( false );
		txtCodCompraBaixa.setAtivo( false );
		txtSerie.setAtivo( false );
		txtCodForBaixa.setAtivo( false );
		txtDtEmisBaixa.setAtivo( false );
		txtCodBancoBaixa.setAtivo( false );
		txtTotPagBaixa.setAtivo( false );
		txtTotAbertoBaixa.setAtivo( false );
		txtTotPagoBaixa.setAtivo( false );
		txtJurosBaixa.setAtivo( false );

		Funcoes.setBordReq( txtCodPagBaixa );

		tpn.addTab( "Baixa", pnBaixa );

		btBaixa.setToolTipText( "Baixa" );

		pnBaixa.add( pinBaixa, BorderLayout.NORTH );
		pnTabBaixa.add( pinBotoesBaixa, BorderLayout.EAST );
		pnTabBaixa.add( spnBaixa, BorderLayout.CENTER );
		pnBaixa.add( pnTabBaixa, BorderLayout.CENTER );

		pinBaixa.adic( new JLabelPad( "Cód.pag" ), 7, 0, 80, 20 );
		pinBaixa.adic( txtCodPagBaixa, 7, 20, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Doc." ), 90, 0, 77, 20 );
		pinBaixa.adic( txtDoc, 90, 20, 77, 20 );
		pinBaixa.adic( new JLabelPad( " -" ), 170, 20, 7, 20 );
		pinBaixa.adic( new JLabelPad( "Série" ), 180, 0, 50, 20 );
		pinBaixa.adic( txtSerie, 180, 20, 50, 20 );
		pinBaixa.adic( new JLabelPad( "Pedido" ), 240, 0, 77, 20 );
		pinBaixa.adic( txtCodCompraBaixa, 240, 20, 77, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.for." ), 7, 40, 250, 20 );
		pinBaixa.adic( txtCodForBaixa, 7, 60, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do fornecedor" ), 90, 40, 250, 20 );
		pinBaixa.adic( txtRazForBaixa, 90, 60, 197, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.banco" ), 290, 40, 250, 20 );
		pinBaixa.adic( txtCodBancoBaixa, 290, 60, 67, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do banco" ), 360, 40, 250, 20 );
		pinBaixa.adic( txtDescBancoBaixa, 360, 60, 150, 20 );
		pinBaixa.adic( new JLabelPad( "Data de emissão" ), 7, 80, 100, 20 );
		pinBaixa.adic( txtDtEmisBaixa, 7, 100, 100, 20 );
		pinBaixa.adic( new JLabelPad( "Total a pagar" ), 110, 80, 97, 20 );
		pinBaixa.adic( txtTotPagBaixa, 110, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total pago" ), 210, 80, 97, 20 );
		pinBaixa.adic( txtTotPagoBaixa, 210, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total em aberto" ), 310, 80, 107, 20 );
		pinBaixa.adic( txtTotAbertoBaixa, 310, 100, 107, 20 );
		pinBaixa.adic( new JLabelPad( "Juros" ), 420, 80, 80, 20 );
		pinBaixa.adic( txtJurosBaixa, 420, 100, 90, 20 );

		pinBotoesBaixa.adic( btBaixa, 5, 10, 30, 30 );

		tabBaixa.adicColuna( "" );
		tabBaixa.adicColuna( "Vencimento" );
		tabBaixa.adicColuna( "Nº de parcelas" );
		tabBaixa.adicColuna( "Doc." );
		tabBaixa.adicColuna( "Pedido" );
		tabBaixa.adicColuna( "Valor da parcela" );
		tabBaixa.adicColuna( "Data pagamento" );
		tabBaixa.adicColuna( "Valor pago" );
		tabBaixa.adicColuna( "Valor desconto" );
		tabBaixa.adicColuna( "Valor juros" );
		tabBaixa.adicColuna( "Valor aberto" );
		tabBaixa.adicColuna( "Conta" );
		tabBaixa.adicColuna( "Categoria" );
		tabBaixa.adicColuna( "Centro de custo" );
		tabBaixa.adicColuna( "Observação" );

		tabBaixa.setTamColuna( 0, 0 );
		tabBaixa.setTamColuna( 110, 1 );
		tabBaixa.setTamColuna( 120, 2 );
		tabBaixa.setTamColuna( 50, 3 );
		tabBaixa.setTamColuna( 70, 4 );
		tabBaixa.setTamColuna( 140, 5 );
		tabBaixa.setTamColuna( 110, 6 );
		tabBaixa.setTamColuna( 90, 7 );
		tabBaixa.setTamColuna( 110, 8 );
		tabBaixa.setTamColuna( 100, 9 );
		tabBaixa.setTamColuna( 100, 10 );
		tabBaixa.setTamColuna( 80, 11 );
		tabBaixa.setTamColuna( 100, 12 );
		tabBaixa.setTamColuna( 120, 13 );
		tabBaixa.setTamColuna( 100, 14 );

		// Manutenção

		tpn.addTab( "Manutenção", pnManut );

		btBaixaManut.setToolTipText( "Baixa" );
		btEditManut.setToolTipText( "Editar" );
		btNovoManut.setToolTipText( "Novo" );
		btExcluirManut.setToolTipText( "Excluir" );
		btExecManut.setToolTipText( "Listar" );

		pnManut.add( pinManut, BorderLayout.NORTH );
		pnTabManut.add( pinBotoesManut, BorderLayout.EAST );
		pnTabManut.add( spnManut, BorderLayout.CENTER );
		pnManut.add( pnTabManut, BorderLayout.CENTER );

		txtDatainiManut.setVlrDate( new Date() );
		txtDatafimManut.setVlrDate( new Date() );

		pinManut.adic( new JLabelPad( "Periodo" ), 7, 0, 200, 20 );
		pinManut.adic( txtDatainiManut, 7, 20, 100, 20 );
		pinManut.adic( new JLabelPad( "até" ), 113, 20, 27, 20 );
		pinManut.adic( txtDatafimManut, 140, 20, 100, 20 );
		pinManut.adic( btExecManut, 690, 55, 30, 30 );

		pinManut.adic( new JLabelPad( "Cód.for." ), 7, 45, 250, 20 );
		pinManut.adic( txtCodForManut, 7, 65, 50, 20 );
		pinManut.adic( new JLabelPad( "Descrição do fornecedor" ), 60, 45, 250, 20 );
		pinManut.adic( txtRazForManut, 60, 65, 180, 20 );

		vValsData.addElement( "V" );
		vValsData.addElement( "E" );
		vLabsData.addElement( "Vencimento" );
		vLabsData.addElement( "Emissão" );

		rgData = new JRadioGroup( 2, 1, vLabsData, vValsData );
		rgData.setVlrString( "V" );
		pinManut.adic( new JLabelPad( "Filtrar por:" ), 247, 0, 115, 20 );
		pinManut.adic( rgData, 247, 20, 115, 65 );

		vValsVenc.addElement( "VE" );
		vValsVenc.addElement( "AV" );
		vValsVenc.addElement( "TT" );
		vLabsVenc.addElement( "Vencidas" );
		vLabsVenc.addElement( "À vencer" );
		vLabsVenc.addElement( "Ambas" );

		pinLbFiltroStatus.adic( lbFiltroStatus, 0, 0, 350, 15 );
		pinLbFiltroStatus.tiraBorda();

		pinManut.adic( pinLbFiltroStatus, 488, 3, 80, 15 );
		pinManut.adic( pinFiltroStatus, 488, 20, 130, 65 );

		pinFiltroStatus.adic( cbAPagar, 5, 0, 120, 20 );
		pinFiltroStatus.adic( cbPagas, 5, 20, 120, 20 );
		pinFiltroStatus.adic( cbPagParcial, 5, 40, 120, 20 );

		rgVenc = new JRadioGroup( 3, 2, vLabsVenc, vValsVenc );
		rgVenc.setVlrString( "TT" );
		pinManut.adic( new JLabelPad( "Filtrar por:" ), 365, 0, 150, 20 );
		pinManut.adic( rgVenc, 365, 20, 115, 65 );

		lcForManut.add( new GuardaCampo( txtCodForManut, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcForManut.add( new GuardaCampo( txtRazForManut, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForManut.montaSql( false, "FORNECED", "CP" );
		lcForManut.setQueryCommit( false );
		lcForManut.setReadOnly( true );

		txtCodForManut.setTabelaExterna( lcForManut );
		txtCodForManut.setFK( true );
		txtCodForManut.setNomeCampo( "CodFor" );

		pinBotoesManut.adic( btBaixaManut, 5, 10, 30, 30 );
		pinBotoesManut.adic( btEditManut, 5, 40, 30, 30 );
		pinBotoesManut.adic( btNovoManut, 5, 70, 30, 30 );
		pinBotoesManut.adic( btEstManut, 5, 100, 30, 30 );
		pinBotoesManut.adic( btExcluirManut, 5, 130, 30, 30 );

		tabManut.adicColuna( "" ); // 0
		tabManut.adicColuna( "Vencimento" ); // 1
		tabManut.adicColuna( "Status" ); // 2
		tabManut.adicColuna( "Cód.for." ); // 3
		tabManut.adicColuna( "Razão social do fornecedor" ); // 4
		tabManut.adicColuna( "Cód.pag." ); // 5
		tabManut.adicColuna( "Nº parc." ); // 6
		tabManut.adicColuna( "Doc. lanca" ); // 7
		tabManut.adicColuna( "Doc. compra" ); // 8
		tabManut.adicColuna( "Valor parcelamento" ); // 9
		tabManut.adicColuna( "Data pagto." ); // 10
		tabManut.adicColuna( "Valor pago" ); // 11
		tabManut.adicColuna( "Valor desc." ); // 12
		tabManut.adicColuna( "Valor juros" ); // 13
		tabManut.adicColuna( "Valor adic" ); // 14
		tabManut.adicColuna( "Valro aberto" ); // 15
		tabManut.adicColuna( "Conta" ); // 16
		tabManut.adicColuna( "Categoria" ); // 17
		tabManut.adicColuna( "Centro de custo" ); // 18
		tabManut.adicColuna( "Observação" ); // 19

		tabManut.setTamColuna( 0, 0 );
		tabManut.setTamColuna( 80, 1 );
		tabManut.setTamColuna( 50, 2 );
		tabManut.setTamColuna( 65, 3 );
		tabManut.setTamColuna( 200, 4 );
		tabManut.setTamColuna( 70, 5 );
		tabManut.setTamColuna( 60, 6 );
		tabManut.setTamColuna( 75, 7 );
		tabManut.setTamColuna( 90, 8 );
		tabManut.setTamColuna( 140, 9 );
		tabManut.setTamColuna( 100, 10 );
		tabManut.setTamColuna( 100, 11 );
		tabManut.setTamColuna( 100, 12 );
		tabManut.setTamColuna( 100, 13 );
		tabManut.setTamColuna( 100, 14 );
		tabManut.setTamColuna( 100, 15 );
		tabManut.setTamColuna( 130, 16 );
		tabManut.setTamColuna( 210, 17 );
		tabManut.setTamColuna( 220, 18 );
		tabManut.setTamColuna( 260, 19 );

		lcPagBaixa.addCarregaListener( this );
		txtCodFor.addKeyListener( this );
		txtCodForBaixa.addKeyListener( this );
		btBaixa.addActionListener( this );
		btBaixaConsulta.addActionListener( this );
		btBaixaManut.addActionListener( this );
		btEditManut.addActionListener( this );
		btNovoManut.addActionListener( this );
		btExcluirManut.addActionListener( this );
		btExecManut.addActionListener( this );
		btEstManut.addActionListener( this );
		tpn.addChangeListener( this );
	}

	private void limpaConsulta() {

		txtPrimCompr.setVlrString( "" );
		txtUltCompr.setVlrString( "" );
		txtDataMaxFat.setVlrString( "" );
		txtVlrMaxFat.setVlrString( "" );
		txtVlrTotCompr.setVlrString( "" );
		txtVlrTotPago.setVlrString( "" );
		txtVlrTotAberto.setVlrString( "" );
		txtDataMaxAcum.setVlrString( "" );
		txtVlrMaxAcum.setVlrString( "" );
	}

	private void carregaConsulta() {

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		StringBuffer sSQL = new StringBuffer();
		
		limpaConsulta();		
		tabConsulta.limpa();
		
		try {

			// Busca os totais ...
			sSQL.append( "SELECT SUM(VLRPARCPAG),SUM(VLRPAGOPAG),SUM(VLRAPAGPAG),MIN(DATAPAG),MAX(DATAPAG) " );
			sSQL.append( "FROM FNPAGAR " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps.setInt( 3, txtCodFor.getVlrInteger().intValue() );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				txtVlrTotCompr.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 1 ) ) );
				txtVlrTotPago.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 2 ) ) );
				txtVlrTotAberto.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 3 ) ) );
				txtPrimCompr.setVlrString( rs.getDate( 4 ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( 4 ) ) );
				txtUltCompr.setVlrString( rs.getDate( 5 ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( 5 ) ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			// Busca a maior fatura ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT MAX(VLRPARCPAG),DATAPAG " );
			sSQL.append( "FROM FNPAGAR " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );
			sSQL.append( "GROUP BY DATAPAG " );
			sSQL.append( "ORDER BY 1 DESC" );

			ps1 = con.prepareStatement( sSQL.toString() );
			ps1.setInt( 1, Aplicativo.iCodEmp );
			ps1.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps1.setInt( 3, txtCodFor.getVlrInteger().intValue() );
			
			rs1 = ps1.executeQuery();
			
			if ( rs1.next() ) {
			
				txtVlrMaxFat.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs1.getString( 1 ) ) );
				txtDataMaxFat.setVlrString( rs1.getDate( "DATAPAG" ) == null ? "" : Funcoes.sqlDateToStrDate( rs1.getDate( "DATAPAG" ) ) );
			}
			
			rs1.close();
			ps1.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			// Busca o maior acumulo ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT EXTRACT(MONTH FROM DATAPAG), SUM(VLRPARCPAG), EXTRACT(YEAR FROM DATAPAG) " );
			sSQL.append( "FROM FNPAGAR " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );
			sSQL.append( "GROUP BY 1, 3 " );
			sSQL.append( "ORDER BY 2 DESC" );
			
			ps2 = con.prepareStatement( sSQL.toString() );
			ps2.setInt( 1, Aplicativo.iCodEmp );
			ps2.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps2.setInt( 3, txtCodFor.getVlrInteger().intValue() );
			
			rs2 = ps2.executeQuery();
			
			if ( rs2.next() ) {
				
				txtDataMaxAcum.setVlrString( Funcoes.strMes( rs2.getInt( 1 ) ) + " de " + rs2.getInt( 3 ) );
				txtVlrMaxAcum.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs2.getString( 2 ) ) );
			}
			
			rs2.close();
			ps2.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			carregaGridConsulta();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a consulta!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			ps1 = null;
			ps2 = null;
			rs = null;
			rs1 = null;
			rs2 = null;
			sSQL = null;
		}
	}

	private void carregaGridConsulta() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		float bdVlrAPagar = 0.0f;
		float bdVlrPago = 0.0f;
		
		try {

			vCodPag.clear();
			vNParcPag.clear();

			sSQL.append( "SELECT IT.DTVENCITPAG," );
			sSQL.append( "(SELECT C.SERIE FROM CPCOMPRA C " );
			sSQL.append( "WHERE C.CODCOMPRA=P.CODCOMPRA AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP)," );
			sSQL.append( "P.DOCPAG,P.CODCOMPRA,P.DATAPAG,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG," );
			sSQL.append( "(CASE WHEN IT.DTPAGOITPAG IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITPAG " );
			sSQL.append( "ELSE IT.DTPAGOITPAG-IT.DTVENCITPAG END ) ATRASO, P.OBSPAG," );
			sSQL.append( "(SELECT B.NOMEBANCO FROM FNBANCO B " );
			sSQL.append( "WHERE B.CODBANCO=P.CODBANCO AND B.CODEMP=P.CODEMPBO AND B.CODFILIAL=P.CODFILIALBO) AS NOMEBANCO," );
			sSQL.append( "P.CODPAG,IT.NPARCPAG,IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.STATUSITPAG " );
			sSQL.append( "FROM FNPAGAR P,FNITPAGAR IT " );
			sSQL.append( "WHERE P.CODFOR=? AND P.CODEMP=? AND P.CODFILIAL=? " );
			sSQL.append( "AND IT.CODPAG = P.CODPAG AND IT.CODEMP=P.CODEMP " );
			sSQL.append( "AND IT.CODFILIAL=P.CODFILIAL " );
			sSQL.append( "ORDER BY P.CODPAG,IT.NPARCPAG" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodFor.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			
			rs = ps.executeQuery();
			
			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAPagar = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItPag" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItPag" ) ).floatValue();

				if ( rs.getString( "StatusItPag" ).equals( "PP" ) && bdVlrAPagar == 0.0f ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabConsulta.adicLinha();
				tabConsulta.setValor( imgColuna, i, 0 );
				tabConsulta.setValor( rs.getDate( "DtVencItPag" ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
				tabConsulta.setValor( rs.getString( 2 ) == null ? "" : rs.getString( 2 ), i, 2 );
				tabConsulta.setValor( rs.getString( "DocPag" ) == null ? "" : rs.getString( "DocPag" ), i, 3 );
				tabConsulta.setValor( String.valueOf( rs.getInt( "CodCompra" ) ), i, 4 );
				tabConsulta.setValor( rs.getDate( "DataPag" ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( "DataPag" ) ), i, 5 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItPag" ) ), i, 6 );
				tabConsulta.setValor( rs.getDate( "DtPagoItPag" ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 7 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrPagoItPag" ) ), i, 8 );
				tabConsulta.setValor( new Integer( rs.getInt( 9 ) ), i, 9 );
				tabConsulta.setValor( rs.getString( "ObsPag" ) == null ? "" : rs.getString( "ObsPag" ), i, 10 );
				tabConsulta.setValor( rs.getString( 11 ) == null ? "" : rs.getString( 11 ), i, 11 );
				vCodPag.addElement( rs.getString( "CodPag" ) );
				vNParcPag.addElement( rs.getString( "NParcPag" ) );
			}
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de consulta!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void carregaGridBaixa() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		
		try {
			
			vNParcBaixa.clear();
			vNumContas.clear();
			vCodPlans.clear();
			vCodCCs.clear();
			vCodPed.clear();
			tabBaixa.limpa();
			
			sSQL.append( "SELECT IT.DTVENCITPAG,IT.STATUSITPAG,P.CODPAG,IT.DOCLANCAITPAG, " );
			sSQL.append( "P.CODCOMPRA,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG, " );
			sSQL.append( "IT.VLRAPAGITPAG,IT.NUMCONTA,IT.VLRDESCITPAG, " );
			sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
			sSQL.append( "WHERE C.NUMCONTA=IT.NUMCONTA AND C.CODEMP=IT.CODEMPCA AND C.CODFILIAL=IT.CODFILIALCA), IT.CODPLAN, " );
			sSQL.append( "(SELECT PL.DESCPLAN FROM FNPLANEJAMENTO PL " );
			sSQL.append( "WHERE PL.CODPLAN=IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN AND PL.CODFILIAL=IT.CODFILIALPN), IT.CODCC, " );
			sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
			sSQL.append( "WHERE CC.CODCC=IT.CODCC AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC), " );
			sSQL.append( "IT.OBSITPAG,IT.NPARCPAG,IT.VLRJUROSITPAG,IT.DTITPAG " );
			sSQL.append( "FROM FNITPAGAR IT,FNPAGAR P " );
			sSQL.append( "WHERE P.CODPAG=? AND P.CODEMP=? AND P.CODFILIAL=? " );
			sSQL.append( "AND IT.CODPAG=P.CODPAG AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL " );
			sSQL.append( "ORDER BY IT.DTVENCITPAG,IT.STATUSITPAG " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodPagBaixa.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			
			rs = ps.executeQuery();
			
			double bdVlrAPagar = 0.0;
			double bdVlrPago = 0.0;

			for ( int i = 0; rs.next(); i++ ) {
				
				bdVlrAPagar = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItPag" ) ).doubleValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItPag" ) ).doubleValue();

				if ( "PP".equals( rs.getString( "StatusItPag" ) ) && bdVlrAPagar == 0.0 ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabBaixa.adicLinha();
				tabBaixa.setValor( imgColuna, i, 0 );
				tabBaixa.setValor( rs.getDate( "DtVencItPag" ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
				tabBaixa.setValor( rs.getString( "NParcPag" ), i, 2 );
				tabBaixa.setValor( rs.getString( "DocLancaItPag" ) == null ? "" : rs.getString( "DocLancaItPag" ), i, 3 );
				tabBaixa.setValor( String.valueOf( rs.getInt( "CodCompra" ) ), i, 4 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItPag" ) ), i, 5 );
				tabBaixa.setValor( rs.getDate( "DtPagoItPag" ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 6 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrPagoItPag" ) ), i, 7 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrDescItPag" ) ), i, 8 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrJurosItPag" ) ), i, 9 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrApagItPag" ) ), i, 10 );
				tabBaixa.setValor( rs.getString( 12 ) == null ? "" : rs.getString( 12 ), i, 11 );
				tabBaixa.setValor( rs.getString( 14 ) == null ? "" : rs.getString( 14 ), i, 12 );
				tabBaixa.setValor( rs.getString( 16 ) == null ? "" : rs.getString( 16 ), i, 13 );
				tabBaixa.setValor( rs.getString( "ObsItPag" ) == null ? "" : rs.getString( "ObsItPag" ), i, 14 );
				vCodPed.addElement( rs.getString( "CodCompra" ) );
				vNParcBaixa.addElement( rs.getString( "NParcPag" ) );
				vNumContas.addElement( rs.getString( "NumConta" ) == null ? "" : rs.getString( "NumConta" ) );
				vCodPlans.addElement( rs.getString( "CodPlan" ) == null ? "" : rs.getString( "CodPlan" ) );
				vCodCCs.addElement( rs.getString( "CodCC" ) == null ? "" : rs.getString( "CodCC" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void carregaGridManut() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhereManut = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();
		
		try {
			
			if ( validaPeriodo() ) {

				tabManut.limpa();
				vNumContas.clear();
				vCodPlans.clear();
				vCodCCs.clear();
				vDtEmiss.clear();
				vCodPed.clear();
				
				sWhereManut.append( " AND " );
				sWhereManut.append( "V".equals( rgData.getVlrString() ) ? "IT.DTVENCITPAG" : "IT.DTITPAG" );
				sWhereManut.append( " BETWEEN ? AND ? AND P.CODEMP=? AND P.CODFILIAL=?" );

				if ( "S".equals( cbPagas.getVlrString() ) ||
						"S".equals( cbAPagar.getVlrString() ) ||
							"S".equals( cbPagParcial.getVlrString() ) ) {

					boolean bStatus = false;
					
					if ( "S".equals( cbPagas.getVlrString() ) ) {
						
						sWhereStatus.append( "IT.STATUSITPAG='PP'" );
						bStatus = true;
					}
					if ( "S".equals( cbAPagar.getVlrString() ) ) {
						
						sWhereStatus.append( bStatus ? " OR IT.STATUSITPAG='P1'" : " IT.STATUSITPAG='P1'" );
						bStatus = true;
					}
					if ( "S".equals( cbPagParcial.getVlrString() ) ) {
						
						sWhereStatus.append( bStatus ? " OR IT.STATUSITPAG='PL'" : " IT.STATUSITPAG='PL'" );
					}
					
					sWhereManut.append( " AND (" );
					sWhereManut.append( sWhereStatus );
					sWhereManut.append( ")" );
				}
				else {
					
					Funcoes.mensagemInforma( null, "Você deve selecionar ao menos um filtro de status!" );
					return;
				}

				if ( ! "TT".equals( rgVenc.getVlrString() ) ) {
					
					sWhereManut.append( " AND IT.DTVENCITPAG" );
					
					if ( "VE".equals( rgVenc.getVlrString() ) ) {
					
						sWhereManut.append( " <'" );
						sWhereManut.append( Funcoes.dateToStrDB( new Date() ) );
						sWhereManut.append( "'" );
					}
					else {
						
						sWhereManut.append( " >='" );
						sWhereManut.append( Funcoes.dateToStrDB( new Date() ) );
						sWhereManut.append( "'" );
					}
				}
				
				if ( ! "".equals( txtCodForManut.getText().trim() ) ) {
					
					sWhereManut.append( " AND P.CODFOR=" );
					sWhereManut.append( txtCodForManut.getText().trim() );
				}

				sSQL.append( "SELECT IT.DTITPAG,IT.DTVENCITPAG,IT.STATUSITPAG,P.CODFOR,F.RAZFOR,P.CODPAG," );
				sSQL.append( "IT.DOCLANCAITPAG,P.CODCOMPRA,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG," );
				sSQL.append( "IT.VLRAPAGITPAG,IT.NUMCONTA,IT.VLRDESCITPAG," );
				sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
				sSQL.append( "WHERE C.NUMCONTA=IT.NUMCONTA AND C.CODEMP=IT.CODEMPCA AND C.CODFILIAL=IT.CODFILIALCA), IT.CODPLAN," );
				sSQL.append( "(SELECT PL.DESCPLAN FROM FNPLANEJAMENTO PL " );
				sSQL.append( "WHERE PL.CODPLAN=IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN AND PL.CODFILIAL=IT.CODFILIALPN), IT.CODCC," );
				sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
				sSQL.append( "WHERE CC.CODCC=IT.CODCC AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC)," );
				sSQL.append( "IT.OBSITPAG,IT.NPARCPAG,IT.VLRJUROSITPAG," );
				sSQL.append( "(SELECT CO.DOCCOMPRA FROM CPCOMPRA CO " );
				sSQL.append( "WHERE CO.CODCOMPRA=P.CODCOMPRA AND CO.CODEMP=P.CODEMPCP AND CO.CODFILIAL=P.CODFILIALCP)," );
				sSQL.append( "IT.DTITPAG,IT.VLRADICITPAG " );
				sSQL.append( "FROM FNITPAGAR IT,FNPAGAR P,CPFORNECED F " );
				sSQL.append( "WHERE P.CODPAG=IT.CODPAG AND F.CODFOR=P.CODFOR " );
				sSQL.append( sWhereManut );
				sSQL.append( " AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL " );
				sSQL.append( "ORDER BY IT.DTVENCITPAG,IT.STATUSITPAG" );

				try {
					
					ps = con.prepareStatement( sSQL.toString() );
					ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
					ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
					ps.setInt( 3, Aplicativo.iCodEmp );
					ps.setInt( 4, ListaCampos.getMasterFilial( "FNPAGAR" ) );
					
					rs = ps.executeQuery();
					
					double bdVlrAPagar = 0.0;
					double bdVlrPago = 0.0;

					for ( int i = 0; rs.next(); i++ ) {
						
						tabManut.adicLinha();
						
						bdVlrAPagar = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItPag" ) ).doubleValue();
						bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItPag" ) ).doubleValue();

						if ( "PP".equals( rs.getString( "StatusItPag" ) ) && bdVlrAPagar == 0.0 ) {
							imgColuna = imgPago;
						}
						else if ( bdVlrPago > 0 ) {
							imgColuna = imgPagoParcial;
						}
						else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
							imgColuna = imgVencido;
						}
						else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
							imgColuna = imgNaoVencido;
						}

						tabManut.setValor( imgColuna, i, 0 );
						tabManut.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
						tabManut.setValor( rs.getString( "StatusItPag" ), i, 2 );
						tabManut.setValor( rs.getString( "CodFor" ), i, 3 );
						tabManut.setValor( rs.getString( "RazFor" ), i, 4 );
						tabManut.setValor( rs.getString( "CodPag" ), i, 5 );
						tabManut.setValor( rs.getString( "NParcPag" ), i, 6 );
						tabManut.setValor( ( rs.getString( "DocLancaItPag" ) != null ? rs.getString( "DocLancaItPag" ) : "" ), i, 7 );
						tabManut.setValor( Funcoes.copy( rs.getString( 23 ), 0, 10 ).trim(), i, 8 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItPag" ) ), i, 9 );
						tabManut.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 10 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrPagoItPag" ) ), i, 11 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrDescItPag" ) ), i, 12 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrJurosItPag" ) ), i, 13 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrAdicItPag" ) ), i, 14 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrApagItPag" ) ), i, 15 );
						tabManut.setValor( rs.getString( 13 ) != null ? rs.getString( 13 ) : "", i, 16 );
						tabManut.setValor( rs.getString( 17 ) != null ? rs.getString( 17 ) : "", i, 17 );
						tabManut.setValor( rs.getString( 19 ) != null ? rs.getString( 19 ) : "", i, 18 );
						tabManut.setValor( rs.getString( "ObsItPag" ) != null ? rs.getString( "ObsItPag" ) : "", i, 19 );
						vCodPed.addElement( rs.getString( "CodCompra" ) );
						vNumContas.addElement( rs.getString( "NumConta" ) != null ? rs.getString( "NumConta" ) : "" );
						vCodPlans.addElement( rs.getString( "CodPlan" ) != null ? rs.getString( "CodPlan" ) : "" );
						vCodCCs.addElement( rs.getString( "CodCC" ) != null ? rs.getString( "CodCC" ) : "" );
						vDtEmiss.addElement( rs.getDate( "DtItPag" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtItPag" ) ) : "" );
					}
					
					rs.close();
					ps.close();
					
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				} catch ( SQLException err ) {
					err.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao montar a tabela de manutenção!\n" + err.getMessage(), true, con, err );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhereManut = null;
			sWhereStatus = null;
		}
	}

	private void baixaConsulta() {

		if ( tabConsulta.getLinhaSel() != -1 ) {
			
			txtCodPagBaixa.setVlrString( (String) vCodPag.elementAt( tabConsulta.getLinhaSel() ) );
			
			int iNParc = ( new Integer( (String) vNParcPag.elementAt( tabConsulta.getLinhaSel() ) ) ).intValue();
			
			lcPagBaixa.carregaDados();
			tpn.setSelectedIndex( 1 );
			
			btBaixa.requestFocus();
			
			for ( int i = 0; i < vNParcBaixa.size(); i++ ) {
				
				if ( iNParc == ( new Integer( (String) vNParcBaixa.elementAt( i ) ) ).intValue() ) {
					
					tabBaixa.setLinhaSel( i );
					break;
				}
			}
		}
	}

	private void baixar( char cOrig ) {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		String[] sVals = null;
		String[] sRelPlanPag = null;
		String[] sRets = null;
		DLBaixaPag dl = null;
		ImageIcon imgStatusAt = null;
		
		try {
			if ( ( cOrig == 'M' ) & ( tabManut.getLinhaSel() > -1 ) ) { // Quando a função eh chamada da tab MANUTENÇÂO

				imgStatusAt = ( (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 ) );
				
				if ( imgStatusAt == imgPago ) {
					
					Funcoes.mensagemInforma( this, "A PARCELA JÁ FOI PAGA!" );
					return;
				}

				int iLin = tabManut.getLinhaSel();
				
				iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, 5 ) );
				iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, 6 ) );
				
				sVals = new String[ 12 ];
				
				sRelPlanPag = buscaRelPlanPag( Integer.parseInt( (String) tabManut.getValor( iLin, 5 ) ) );
				sRets = null;
				
				dl = new DLBaixaPag( this );

				sVals[ 0 ] = (String) tabManut.getValor( iLin, 3 );
				sVals[ 1 ] = (String) tabManut.getValor( iLin, 4 );
				sVals[ 2 ] = "".equals( (String) vNumContas.elementAt( iLin ) ) ? sRelPlanPag[ 2 ] : (String) vNumContas.elementAt( iLin );
				sVals[ 3 ] = "".equals( (String) vCodPlans.elementAt( iLin ) ) ? sRelPlanPag[ 1 ] : (String) vCodPlans.elementAt( iLin );
				sVals[ 4 ] = "".equals( (String) tabManut.getValor( iLin, 7 ) ) ? (String) tabManut.getValor( iLin, 8 ) : (String) tabManut.getValor( iLin, 7 );
				sVals[ 5 ] = (String) tabManut.getValor( iLin, 1 );
				sVals[ 6 ] = (String) tabManut.getValor( iLin, 1 );
				sVals[ 7 ] = (String) tabManut.getValor( iLin, 9 );
				sVals[ 8 ] = Funcoes.dateToStrDate( new Date() );
				sVals[ 10 ] = "".equals( (String) vCodCCs.elementAt( iLin ) ) ? sRelPlanPag[ 3 ] : (String) vCodCCs.elementAt( iLin );

				if ( "".equals( ( (String) tabManut.getValor( iLin, 10 ) ).trim() ) ) {// Para verificar c jah esta pago testa se a data de pgto esta setada.
					
					if ( "".equals( ( (String) tabManut.getValor( iLin, 19 ) ).trim() ) ) {
					
						sVals[ 11 ] = "PAGAMENTO REF. A COMPRA: " + tabManut.getValor( iLin, 8 );
					}
					else {
					
						sVals[ 11 ] = (String) tabManut.getValor( iLin, 19 );
					}

					sVals[ 9 ] = (String) tabManut.getValor( iLin, 11 );
				}
				else {
					
					sVals[ 11 ] = (String) tabManut.getValor( iLin, 19 );
					sVals[ 9 ] = (String) tabManut.getValor( iLin, 11 );
				}

				dl.setValores( sVals );
				dl.setConexao( con );
				dl.setVisible( true );

				if ( dl.OK ) {
					
					sRets = dl.getValores();
					
					sSQL.append( "UPDATE FNITPAGAR " );
					sSQL.append( "SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
					sSQL.append( "DOCLANCAITPAG=?,DTPAGOITPAG=?,VLRPAGOITPAG=?,ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?," );
					sSQL.append( "OBSITPAG=?,STATUSITPAG='PP' " );
					sSQL.append( "WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );

					try {
						
						ps = con.prepareStatement( sSQL.toString() );
						ps.setString( 1, sRets[ 0 ] );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
						ps.setString( 4, sRets[ 1 ] );
						ps.setInt( 5, Aplicativo.iCodEmp );
						ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
						ps.setString( 7, sRets[ 2 ] );
						ps.setDate( 8, Funcoes.strDateToSqlDate( sRets[ 3 ] ) );
						ps.setBigDecimal( 9, Funcoes.strCurrencyToBigDecimal( sRets[ 4 ] ) );
						
						if ( "".equals( sRets[ 5 ].trim() ) ) {
							
							ps.setNull( 10, Types.INTEGER );
							ps.setNull( 11, Types.CHAR );
							ps.setNull( 12, Types.INTEGER );
							ps.setNull( 13, Types.INTEGER );
						}
						else {
							
							ps.setInt( 10, iAnoCC );
							ps.setString( 11, sRets[ 5 ] );
							ps.setInt( 12, Aplicativo.iCodEmp );
							ps.setInt( 13, ListaCampos.getMasterFilial( "FNCC" ) );
						}
						
						ps.setString( 14, sRets[ 6 ] );
						ps.setInt( 15, iCodPag );
						ps.setInt( 16, iNParcPag );
						ps.setInt( 17, Aplicativo.iCodEmp );
						ps.setInt( 18, ListaCampos.getMasterFilial( "FNPAGAR" ) );
						
						ps.executeUpdate();
						
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					} catch ( SQLException err ) {
						err.printStackTrace();
						Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
					}
				}
				
				dl.dispose();
				carregaGridManut();
			}
			else if ( ( cOrig == 'B' ) & ( tabBaixa.getLinhaSel() > -1 ) ) { // Quando a função eh chamada da tab BAIXAR

				imgStatusAt = ( (ImageIcon) tabBaixa.getValor( tabBaixa.getLinhaSel(), 0 ) );
				
				if ( imgStatusAt == imgPago ) {
					
					Funcoes.mensagemInforma( this, "A PARCELA JÁ FOI PAGA!" );
					return;
				}

				int iLin = tabBaixa.getLinhaSel();
				
				iCodPag = txtCodPagBaixa.getVlrInteger().intValue();
				iNParcPag = Integer.parseInt( (String) tabBaixa.getValor( iLin, 2 ) );
				
				sVals = new String[ 12 ];
				sRelPlanPag = buscaRelPlanPag( txtCodPagBaixa.getVlrInteger().intValue() );
				
				dl = new DLBaixaPag( this );

				sVals[ 0 ] = txtCodForBaixa.getVlrString();
				sVals[ 1 ] = txtRazForBaixa.getVlrString();
				sVals[ 2 ] = "".equals( (String) vNumContas.elementAt( iLin ) ) ? sRelPlanPag[ 2 ] : (String) vNumContas.elementAt( iLin );
				sVals[ 3 ] = "".equals( (String) vCodPlans.elementAt( iLin ) ) ? sRelPlanPag[ 1 ] : (String) vCodPlans.elementAt( iLin );
				sVals[ 4 ] = txtDoc.getVlrString();
				sVals[ 5 ] = txtDtEmisBaixa.getVlrString();
				sVals[ 6 ] = (String) tabBaixa.getValor( iLin, 1 );
				sVals[ 7 ] = (String) tabBaixa.getValor( iLin, 5 );
				sVals[ 8 ] = Funcoes.dateToStrDate( new Date() );
				sVals[ 10 ] = "".equals( (String) vCodCCs.elementAt( iLin ) ) ? sRelPlanPag[ 3 ] : (String) vCodCCs.elementAt( iLin );

				if ( "".equals( ( (String) tabBaixa.getValor( iLin, 6 ) ).trim() ) ) {
					
					if ( "".equals( ( (String) tabBaixa.getValor( iLin, 13 ) ).trim() ) ) {
					
						sVals[ 11 ] = "PAGAMENTO REF. A COMPRA: " + txtCodCompraBaixa.getVlrString();
					}
					else {
					
						sVals[ 11 ] = (String) tabBaixa.getValor( iLin, 13 );
					}
					
					sVals[ 9 ] = (String) tabBaixa.getValor( iLin, 5 );
				}
				else {
					sVals[ 11 ] = (String) tabBaixa.getValor( iLin, 13 );
					sVals[ 9 ] = (String) tabBaixa.getValor( iLin, 7 );
				}

				dl.setValores( sVals );
				dl.setConexao( con );
				dl.setVisible( true );

				if ( dl.OK ) {
					
					sRets = dl.getValores();
					
					sSQL.append( "UPDATE FNITPAGAR " );
					sSQL.append( "SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
					sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITPAG =?,DTPAGOITPAG=?,VLRPAGOITPAG=?," );
					sSQL.append( "OBSITPAG=?,STATUSITPAG='PP' " );
					sSQL.append( "WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );
					
					try {
						
						ps = con.prepareStatement( sSQL.toString() );
						ps.setString( 1, sRets[ 0 ] );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
						ps.setString( 4, sRets[ 1 ] );
						ps.setInt( 5, Aplicativo.iCodEmp );
						ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
						
						if ( "".equals( sRets[ 5 ].trim() ) ) {
							
							ps.setNull( 7, Types.INTEGER );
							ps.setNull( 8, Types.CHAR );
							ps.setNull( 9, Types.INTEGER );
							ps.setNull( 10, Types.INTEGER );
						}
						else {
							
							ps.setInt( 7, iAnoCC );
							ps.setString( 8, sRets[ 5 ] );
							ps.setInt( 9, Aplicativo.iCodEmp );
							ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
						}
						
						ps.setString( 11, sRets[ 2 ] );
						ps.setDate( 12, Funcoes.strDateToSqlDate( sRets[ 3 ] ) );
						ps.setBigDecimal( 13, Funcoes.strCurrencyToBigDecimal( sRets[ 4 ] ) );
						ps.setString( 14, sRets[ 6 ] );
						ps.setInt( 15, iCodPag );
						ps.setInt( 16, iNParcPag );
						ps.setInt( 17, Aplicativo.iCodEmp );
						ps.setInt( 18, ListaCampos.getMasterFilial( "FNPAGAR" ) );
						
						ps.executeUpdate();
						
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					} catch ( SQLException err ) {
						err.printStackTrace();
						Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
					}
				}
				
				carregaGridBaixa();
				dl.dispose();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			sSQL = null;
			sVals = null;
			sRelPlanPag = null;
			sRets = null;
			dl = null;
			imgStatusAt = null;
		}
	}

	private void editar() {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		String[] sVals = null;
		String[] sRets = null;
		DLEditaPag dl = null;
		ImageIcon imgStatusAt = null;
		int iLin;
		
		try {
			
			if ( tabManut.getLinhaSel() > -1 ) {
				
				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );
				
				if ( imgStatusAt != imgPago ) {
					
					iLin = tabManut.getLinhaSel();
					
					iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, 5 ) );
					iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, 6 ) );
					
					sVals = new String[ 13 ];
					
					dl = new DLEditaPag( this );
					
					sVals[ 0 ] = (String) tabManut.getValor( iLin, 3 );
					sVals[ 1 ] = (String) tabManut.getValor( iLin, 4 );
					sVals[ 2 ] = String.valueOf( vNumContas.elementAt( iLin ) );
					sVals[ 3 ] = String.valueOf( vCodPlans.elementAt( iLin ) );
					sVals[ 4 ] = String.valueOf( vCodCCs.elementAt( iLin ) );
					sVals[ 5 ] = (String) tabManut.getValor( iLin, 5 );
					sVals[ 6 ] = String.valueOf( vDtEmiss.elementAt( iLin ) );
					sVals[ 7 ] = (String) tabManut.getValor( iLin, 1 );
					sVals[ 8 ] = (String) tabManut.getValor( iLin, 9 );
					sVals[ 9 ] = (String) tabManut.getValor( iLin, 13 );
					sVals[ 10 ] = (String) tabManut.getValor( iLin, 12 );
					sVals[ 11 ] = (String) tabManut.getValor( iLin, 14 );
					
					if ( "".equals( ( (String) tabManut.getValor( iLin, 10 ) ).trim() ) ) {
						
						if ( "".equals( ( (String) tabManut.getValor( iLin, 19 ) ).trim() ) ) {
						
							sVals[ 12 ] = "PAGAMENTO REF. A COMPRA: " + tabManut.getValor( iLin, 8 );
						}
						else {
						
							sVals[ 12 ] = (String) tabManut.getValor( iLin, 19 );
						}
					}
					else {
						
						sVals[ 12 ] = (String) tabManut.getValor( iLin, 18 );
					}
					
					// SE o doccompra estiver em branco getvalor(8) quer dizer que o lançamento foi feito pelo usuário.
					dl.setValores( sVals, "".equals( tabManut.getValor( iLin, 8 ).toString().trim() ) );
					dl.setConexao( con );
					dl.setVisible( true );
					
					if ( dl.OK ) {
						
						sRets = dl.getValores();
						
						sSQL.append( "UPDATE FNITPAGAR SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
						sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITPAG =?,VLRPARCITPAG=?,VLRJUROSITPAG=?," );
						sSQL.append( "VLRADICITPAG =?,VLRDESCITPAG=?,DTVENCITPAG=?,OBSITPAG=?" );
						sSQL.append( " WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );
						
						try {
							
							ps = con.prepareStatement( sSQL.toString() );
							
							if ( "".equals( sRets[ 0 ].trim() ) ) {
								ps.setNull( 1, Types.CHAR );
								ps.setNull( 2, Types.INTEGER );
								ps.setNull( 3, Types.INTEGER );
							}
							else {
								ps.setString( 1, sRets[ 0 ] );
								ps.setInt( 2, Aplicativo.iCodEmp );
								ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
							}
							if ( "".equals( sRets[ 1 ].trim() ) ) {
								ps.setNull( 4, Types.CHAR );
								ps.setNull( 5, Types.INTEGER );
								ps.setNull( 6, Types.INTEGER );
							}
							else {
								ps.setString( 4, sRets[ 1 ] );
								ps.setInt( 5, Aplicativo.iCodEmp );
								ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
							}
							if ( "".equals( sRets[ 2 ].trim() ) ) {
								ps.setNull( 7, Types.INTEGER );
								ps.setNull( 8, Types.CHAR );
								ps.setNull( 9, Types.INTEGER );
								ps.setNull( 10, Types.INTEGER );
							}
							else {
								ps.setInt( 7, iAnoCC );
								ps.setString( 8, sRets[ 2 ] );
								ps.setInt( 9, Aplicativo.iCodEmp );
								ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
							}
							if ( "".equals( sRets[ 3 ].trim() ) ) {
								ps.setNull( 11, Types.CHAR );
							}
							else {
								ps.setString( 11, sRets[ 3 ] );
							}
							if ( "".equals( sRets[ 4 ].trim() ) ) {
								ps.setNull( 12, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 12, Funcoes.strCurrencyToBigDecimal( sRets[ 4 ] ) );
							}
							if ( "".equals( sRets[ 5 ].trim() ) ) {
								ps.setNull( 13, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 13, Funcoes.strCurrencyToBigDecimal( sRets[ 5 ] ) );
							}
							if ( "".equals( sRets[ 6 ].trim() ) ) {
								ps.setNull( 14, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 14, Funcoes.strCurrencyToBigDecimal( sRets[ 6 ] ) );
							}
							if ( "".equals( sRets[ 7 ].trim() ) ) {
								ps.setNull( 15, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 15, Funcoes.strCurrencyToBigDecimal( sRets[ 7 ] ) );
							}
							if ( "".equals( sRets[ 8 ].trim() ) ) {
								ps.setNull( 16, Types.DECIMAL );
							}
							else {
								ps.setDate( 16, Funcoes.strDateToSqlDate( sRets[ 8 ] ) );
							}
							if ( "".equals( sRets[ 9 ].trim() ) ) {
								ps.setNull( 17, Types.CHAR );
							}
							else {
								ps.setString( 17, sRets[ 9 ] );
							}

							ps.setInt( 18, iCodPag );
							ps.setInt( 19, iNParcPag );
							ps.setInt( 20, Aplicativo.iCodEmp );
							ps.setInt( 21, ListaCampos.getMasterFilial( "FNPAGAR" ) );
							
							ps.executeUpdate();
							
							if ( !con.getAutoCommit() ) {
								con.commit();
							}
						} catch ( SQLException err ) {
							Funcoes.mensagemErro( this, "Erro ao editar parcela!\n" + err.getMessage(), true, con, err );
							err.printStackTrace();
						}
					}
					
					dl.dispose();
					carregaGridManut();
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			sSQL = null;
			sVals = null;
			sRets = null;
			dl = null;
			imgStatusAt = null;
		}
	}

	private void novo() {

		DLNovoPag dl = new DLNovoPag( this );
		dl.setConexao( con );
		dl.setVisible( true );
		dl.dispose();
		carregaGridManut();
	}

	private void excluir() {

		PreparedStatement ps = null;
		ImageIcon imgStatusAt = null;
		
		try {
			
			if ( tabManut.getLinhaSel() > -1 ) {
				
				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );
				
				if ( ! ( imgStatusAt == imgPagoParcial || imgStatusAt == imgPago ) ) {
					
					if ( Funcoes.mensagemConfirma( this, "Deseja realmente excluir esta conta e todas as suas parcelas?" ) == 0 ) {
						
						try {
							
							ps = con.prepareStatement( "DELETE FROM FNPAGAR WHERE CODPAG=? AND CODEMP=? AND CODFILIAL=?" );
							ps.setInt( 1, Integer.parseInt( (String) tabManut.getValor( tabManut.getLinhaSel(), 5 ) ) );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );
							
							ps.executeUpdate();
							
							if ( !con.getAutoCommit() ) {
								con.commit();
							}
							
							carregaGridManut();
							
						} catch ( SQLException err ) {
							err.printStackTrace();
							Funcoes.mensagemErro( this, "Erro ao excluir parcela!\n" + err.getMessage(), true, con, err );
						}
					}
				}
				else {
					Funcoes.mensagemErro( this, "NÃO FOI POSSIVEL EXCLUIR.\n" + "A PARCELA JÁ FOI PAGA." );
				}
			}
			else {
				Funcoes.mensagemInforma( this, "Nenhum item foi selecionado." );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			imgStatusAt = null;
		}
	}

	private void estorno() {

		PreparedStatement ps = null;
		ImageIcon imgStatusAt = null;
		
		try {
			
			if ( tabManut.getLinhaSel() > -1 ) {
				
				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );
				
				if ( ( ( imgStatusAt == imgPagoParcial || imgStatusAt == imgPago ) ) ) {
					
					if ( Funcoes.mensagemConfirma( this, "Confirma o estorno do lançamento?" ) == JOptionPane.YES_OPTION ) {
						
						int iLin = tabManut.getLinhaSel();
						
						iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, 5 ) );
						iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, 6 ) );
						
						try {
						
							ps = con.prepareStatement( "UPDATE FNITPAGAR SET STATUSITPAG='P1' WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );
							ps.setInt( 1, iCodPag );
							ps.setInt( 2, iNParcPag );
							ps.setInt( 3, Aplicativo.iCodEmp );
							ps.setInt( 4, ListaCampos.getMasterFilial( "FNPAGAR" ) );
							
							ps.executeUpdate();

							if ( !con.getAutoCommit() ) {
								con.commit();
							}
						} catch ( SQLException err ) {
							err.printStackTrace();
							Funcoes.mensagemErro( this, "Erro ao estornar registro!\n" + err.getMessage(), true, con, err );
						}
						
						carregaGridManut();
					}
				}
				else {
					Funcoes.mensagemInforma( this, "PARCELA AINDA NÃO FOI PAGA." );
				}
			}
			else {
				Funcoes.mensagemInforma( this, "Nenhum item foi selecionado." );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			imgStatusAt = null;
		}
	}

	private boolean validaPeriodo() {

		boolean bRetorno = false;
		
		if ( txtDatainiManut.getText().trim().length() < 10 ) {
			
			Funcoes.mensagemInforma( this, "Data inicial inválida!" );
		}
		else if ( txtDatafimManut.getText().trim().length() < 10 ) {
			
			Funcoes.mensagemInforma( this, "Data final inválida!" );
		}
		else if ( txtDatafimManut.getVlrDate().before( txtDatainiManut.getVlrDate() ) ) {
			
			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
		}
		else {
			
			dIniManut = txtDatainiManut.getVlrDate();
			dFimManut = txtDatafimManut.getVlrDate();
			bRetorno = true;
		}
		
		return bRetorno;
	}

	private String[] buscaRelPlanPag( int iCodPag ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String[] retorno = new String[ 4 ];
		
		try {
			
			sSQL.append( " SELECT C.CODPLANOPAG, PP.CODPLAN, PP.NUMCONTA, PP.CODCC " );
			sSQL.append( "FROM CPCOMPRA C, FNPLANOPAG PP, FNPAGAR P " );
			sSQL.append( "WHERE C.CODEMPPG=PP.CODEMP AND C.CODFILIALPG=PP.CODFILIAL AND C.CODPLANOPAG=PP.CODPLANOPAG " );
			sSQL.append( "AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA " );
			sSQL.append( "AND P.CODEMP=? AND P.CODFILIAL=? AND P.CODPAG=?" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps.setInt( 3, iCodPag );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				
				for ( int i = 0; i < retorno.length; i++ ) {
				
					retorno[ i ] = rs.getString( i + 1 ) == null ? "" : rs.getString( i + 1 );
				}
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar Conta!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return retorno;
	}

	private int buscaAnoBaseCC() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRet = 0;
		
		try {
			
			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		
		return iRet;
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		tabBaixa.limpa();
		carregaGridBaixa();
	}

	public void keyReleased( KeyEvent kevt ) {

	}

	public void keyTyped( KeyEvent kevt ) {

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			carregaConsulta();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btBaixaConsulta ) {
			baixaConsulta();
		}
		else if ( evt.getSource() == btBaixa ) {
			baixar( 'B' );
		}
		else if ( evt.getSource() == btBaixaManut ) {
			baixar( 'M' );
		}
		else if ( evt.getSource() == btEditManut ) {
			editar();
		}
		else if ( evt.getSource() == btNovoManut ) {
			novo();
		}
		else if ( evt.getSource() == btExcluirManut ) {
			excluir();
		}
		else if ( evt.getSource() == btExecManut ) {
			carregaGridManut();
		}
		else if ( evt.getSource() == btEstManut ) {
			estorno();
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( tpn.getSelectedIndex() == 0 ) {
			carregaConsulta();
		}
		if ( tpn.getSelectedIndex() == 1 ) {
			lcPagBaixa.carregaDados();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcForBaixa.setConexao( cn );
		lcForManut.setConexao( cn );
		lcCompraBaixa.setConexao( cn );
		lcBancoBaixa.setConexao( cn );
		lcPagBaixa.setConexao( cn );
		iAnoCC = buscaAnoBaseCC();
	}

}
