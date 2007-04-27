/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FManutRec.java <BR>
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
 * Comentários sobre a classe...
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
import java.math.BigDecimal;
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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBaixaRec.EColBaixa;
import org.freedom.modulos.std.DLEditaRec.EColEdit;
import org.freedom.modulos.std.DLEditaRec.EColRet;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutRec extends FFilho implements ActionListener, KeyListener, CarregaListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private enum EColTabManut{IMGSTATUS, DTVENC, DTEMIT, CODCLI, RAZCLI, CODREC, NPARCITREC, 
		DOCLANCA, DOCVENDA, VLRPARC, DTPAGTO, VLRPAGO, VLRDESC, VLRJUROS, VLRAPAG, 
		NUMCONTA, DESCCONTA, CODPLAN, DESCPLAN, CODCC, DESCCC, CODTIPOCOB, 
		DESCTIPOCOB, CODBANCO, NOMEBANCO, OBS};

	private enum EColTabBaixa{IMGSTATUS, DTVENC, NPARCITREC, DOC, CODVENDA, VLRPARC,
		DTPAGTO, VLRPAGO, VLRDESC, VLRJUROS, VLRAPAG, NUMCONTA, CODPLAN, CODCC, OBS};

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private Tabela tabBaixa = new Tabela();

	private JScrollPane spnBaixa = new JScrollPane( tabBaixa );

	private Tabela tabManut = new Tabela();

	private JScrollPane spnManut = new JScrollPane( tabManut );

	private Tabela tabConsulta = new Tabela();

	private JScrollPane spnConsulta = new JScrollPane( tabConsulta );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 0, 4 ) );

	private JPanelPad pnTabConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesConsulta = new JPanelPad( 40, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinConsulta = new JPanelPad( 500, 140 );

	private JPanelPad pnConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesBaixa = new JPanelPad( 40, 100 );

	private JPanelPad pinBaixa = new JPanelPad( 500, 140 );

	private JPanelPad pnBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesManut = new JPanelPad( 40, 130 );

	private JPanelPad pinManut = new JPanelPad( 500, 155 );

	private JPanelPad pnManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCliFiltro = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtPrimCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtUltCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataMaxFat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrMaxFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotCompr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataMaxAcum = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCodRecBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodRecManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodVendaBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCliBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtTotRecBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtJurosBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDatainiManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDocManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtPedidoManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCodCliManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCliManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDtEmitManut = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazCliFiltro = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazCliBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbFiltroStatus = new JLabelPad( "Filtrar por:" );

	private JPanelPad pinLbFiltroStatus = new JPanelPad( 53, 15 );

	private JPanelPad pinFiltroStatus = new JPanelPad( 300, 150 );

	private JButton btBaixaConsulta = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btBaixaManut = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btEditManut = new JButton( Icone.novo( "btEditar.gif" ) );

	private JButton btNovoManut = new JButton( Icone.novo( "btNovo.gif" ) );

	private JButton btExcluirManut = new JButton( Icone.novo( "btExcluir.gif" ) );

	private JButton btEstorno = new JButton( Icone.novo( "btCancelar.gif" ) );

	private JButton btCarregaGridManut = new JButton( Icone.novo( "btExecuta.gif" ) );

	private JButton btCarregaBaixas = new JButton( Icone.novo( "btConsBaixa.gif" ) );

	private JButton btBaixa = new JButton( Icone.novo( "btOk.gif" ) );

	private JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private JButton btCarregaVenda = new JButton( "Consulta venda", Icone.novo( "btSaida.gif" ) );

	private JCheckBoxPad cbRecebidas = new JCheckBoxPad( "Recebidas", "S", "N" );

	private JCheckBoxPad cbAReceber = new JCheckBoxPad( "À Receber", "S", "N" );

	private JCheckBoxPad cbRecParcial = new JCheckBoxPad( "Rec. Parcial", "S", "N" );

	private JRadioGroup rgData = null;

	private JRadioGroup rgVenc = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcCliBaixa = new ListaCampos( this );

	private ListaCampos lcCliFiltro = new ListaCampos( this );

	private ListaCampos lcCliManut = new ListaCampos( this, "CL" );

	private ListaCampos lcVendaBaixa = new ListaCampos( this );

	private ListaCampos lcRecManut = new ListaCampos( this );

	private ListaCampos lcRecBaixa = new ListaCampos( this );

	private ListaCampos lcBancoBaixa = new ListaCampos( this, "BC" );

	private Vector vCodRec = new Vector();

	private Vector vNParcItRec = new Vector();

	private Vector vNParcBaixa = new Vector();

	//private Vector vCodPed = new Vector();

	//private Vector vNumContas = new Vector();

	//private Vector vCodPlans = new Vector();

	//private Vector vCodCCs = new Vector();

	//private Vector vCodBOs = new Vector();

	//private Vector vDtEmiss = new Vector();

	private Vector vValsData = new Vector();

	private Vector vLabsData = new Vector();

	private Vector vValsVenc = new Vector();

	private Vector vLabsVenc = new Vector();

	private Date dIniManut = null;

	private Date dFimManut = null;

	private ImageIcon imgVencido = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgPagoParcial = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgNaoVencido = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgColuna = null;
	
	boolean bBuscaAtual = true;

	int iCodRec = 0;

	int iNParcItRec = 0;

	int iAnoCC = 0;

	public FManutRec() {

		super( false );
		setTitulo( "Manutenção de contas a receber" );
		setAtribos( 20, 20, 750, 450 );

		cbAReceber.setVlrString( "S" );

		btBaixaConsulta.setToolTipText( "Baixar" );
		btBaixaManut.setToolTipText( "Baixar" );
		btEditManut.setToolTipText( "Editar lançamento" );
		btNovoManut.setToolTipText( "Novo lançamento" );
		btExcluirManut.setToolTipText( "Excluir" );
		btEstorno.setToolTipText( "Estorno" );
		btCarregaGridManut.setToolTipText( "Executar consulta" );
		btCarregaBaixas.setToolTipText( "Carrega baixas" );
		btBaixa.setToolTipText( "Baixar" );
		btSair.setToolTipText( "Sair" );
		btCarregaVenda.setToolTipText( "Consulta venda" );

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

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do clliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		txtPrimCompr.setAtivo( false );
		txtUltCompr.setAtivo( false );
		txtDataMaxFat.setAtivo( false );
		txtVlrMaxFat.setAtivo( false );
		txtVlrTotCompr.setAtivo( false );
		txtVlrTotPago.setAtivo( false );
		txtVlrTotAberto.setAtivo( false );
		txtDataMaxAcum.setAtivo( false );
		txtVlrMaxAcum.setAtivo( false );

		txtCodCli.setRequerido( true );
		txtCodRecManut.setRequerido( true );

		tpn.addTab( "Consulta", pnConsulta );

		pnConsulta.add( pinConsulta, BorderLayout.NORTH );

		pnTabConsulta.add( pinBotoesConsulta, BorderLayout.EAST );
		pnTabConsulta.add( spnConsulta, BorderLayout.CENTER );
		pnConsulta.add( pnTabConsulta, BorderLayout.CENTER );

		pinConsulta.adic( new JLabelPad( "Cód.cli." ), 7, 0, 250, 20 );
		pinConsulta.adic( txtCodCli, 7, 20, 80, 20 );
		pinConsulta.adic( new JLabelPad( "Razão social do cliente" ), 90, 0, 250, 20 );
		pinConsulta.adic( txtRazCli, 90, 20, 217, 20 );
		pinConsulta.adic( new JLabelPad( "P. compra" ), 310, 0, 97, 20 );
		pinConsulta.adic( txtPrimCompr, 310, 20, 97, 20 );
		pinConsulta.adic( new JLabelPad( "U. compra" ), 410, 0, 100, 20 );
		pinConsulta.adic( txtUltCompr, 410, 20, 100, 20 );
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
		pinConsulta.adic( txtVlrTotAberto, 343, 100, 166, 20 );
		pinConsulta.adic( btCarregaVenda, 575, 93, 150, 30 );

		btCarregaVenda.addActionListener( this );

		pinBotoesConsulta.adic( btBaixaConsulta, 3, 10, 30, 30 );
		tabConsulta.adicColuna( "" );
		tabConsulta.adicColuna( "Vencimento" );// 1
		tabConsulta.adicColuna( "Doc." );// 2
		tabConsulta.adicColuna( "Dt. venda." );// 3
		tabConsulta.adicColuna( "Valor" );// 4
		tabConsulta.adicColuna( "Desc.fin" );// 5
		tabConsulta.adicColuna( "Valor pago." );// 6
		tabConsulta.adicColuna( "Data pagamento" );// 7
		tabConsulta.adicColuna( "Atraso" );// 8
		tabConsulta.adicColuna( "Juros" );// 9
		tabConsulta.adicColuna( "Série" );// 10
		tabConsulta.adicColuna( "Cód.venda" );// 11
		tabConsulta.adicColuna( "Banco" );// 12
		tabConsulta.adicColuna( "Observações" );// 13
		tabConsulta.adicColuna( "TV" );// 14

		tabConsulta.setTamColuna( 0, 0 );// status
		tabConsulta.setTamColuna( 90, 1 );// venc
		tabConsulta.setTamColuna( 80, 2 );// doc
		tabConsulta.setTamColuna( 90, 3 );// data venda
		tabConsulta.setTamColuna( 100, 4 );// valor
		tabConsulta.setTamColuna( 100, 5 );// Desc.Fin
		tabConsulta.setTamColuna( 100, 6 );// valor pago
		tabConsulta.setTamColuna( 120, 7 );// data pagamento
		tabConsulta.setTamColuna( 60, 8 );// atraso
		tabConsulta.setTamColuna( 100, 9 );// juros
		tabConsulta.setTamColuna( 50, 10 );// serie
		tabConsulta.setTamColuna( 80, 11 );// codvenda
		tabConsulta.setTamColuna( 200, 12 );// banco
		tabConsulta.setTamColuna( 200, 13 );// observ
		tabConsulta.setTamColuna( 20, 14 );// Tipo venda

		// Baixa:

		lcVendaBaixa.add( new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_PK, false ) );
		lcVendaBaixa.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVendaBaixa.montaSql( false, "VENDA", "VD" );
		lcVendaBaixa.setQueryCommit( false );
		lcVendaBaixa.setReadOnly( true );
		txtCodVendaBaixa.setTabelaExterna( lcVendaBaixa );
		txtCodVendaBaixa.setFK( true );
		txtCodVendaBaixa.setNomeCampo( "CodVenda" );

		lcCliBaixa.add( new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli", ListaCampos.DB_PK, false ) );
		lcCliBaixa.add( new GuardaCampo( txtRazCliBaixa, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliBaixa.montaSql( false, "CLIENTE", "VD" );
		lcCliBaixa.setQueryCommit( false );
		lcCliBaixa.setReadOnly( true );
		txtCodCliBaixa.setTabelaExterna( lcCliBaixa );
		txtCodCliBaixa.setFK( true );
		txtCodCliBaixa.setNomeCampo( "CodCli" );

		lcBancoBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBancoBaixa.add( new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBancoBaixa.montaSql( false, "BANCO", "FN" );
		lcBancoBaixa.setQueryCommit( false );
		lcBancoBaixa.setReadOnly( true );
		txtCodBancoBaixa.setTabelaExterna( lcBancoBaixa );
		txtCodBancoBaixa.setFK( true );
		txtCodBancoBaixa.setNomeCampo( "CodBanco" );

		lcRecBaixa.add( new GuardaCampo( txtCodRecBaixa, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotRecBaixa, "VlrRec", "Tot.rec.", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli.", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtDtEmisBaixa, "DataRec", "Data emissão", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotAbertoBaixa, "VlrApagRec", "Total aberto", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotPagoBaixa, "VlrPagoRec", "Total pago", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtJurosBaixa, "VlrJurosRec", "Total juros", ListaCampos.DB_SI, false ) );
		lcRecBaixa.montaSql( false, "RECEBER", "FN" );
		lcRecBaixa.setQueryCommit( false );
		lcRecBaixa.setReadOnly( true );
		txtCodRecBaixa.setTabelaExterna( lcRecBaixa );
		txtCodRecBaixa.setFK( true );
		txtCodRecBaixa.setNomeCampo( "CodRec" );

		txtDoc.setAtivo( false );
		txtCodVendaBaixa.setAtivo( false );
		txtSerie.setAtivo( false );
		txtCodCliBaixa.setAtivo( false );
		txtDtEmisBaixa.setAtivo( false );
		txtCodBancoBaixa.setAtivo( false );
		txtTotRecBaixa.setAtivo( false );
		txtTotAbertoBaixa.setAtivo( false );
		txtTotPagoBaixa.setAtivo( false );
		txtJurosBaixa.setAtivo( false );

		txtCodRecBaixa.setRequerido( true );

		tpn.addTab( "Baixa", pnBaixa );

		pnBaixa.add( pinBaixa, BorderLayout.NORTH );

		pnTabBaixa.add( pinBotoesBaixa, BorderLayout.EAST );
		pnTabBaixa.add( spnBaixa, BorderLayout.CENTER );

		pnBaixa.add( pnTabBaixa, BorderLayout.CENTER );

		pinBaixa.adic( new JLabelPad( "Cód.rec" ), 7, 0, 80, 20 );
		pinBaixa.adic( txtCodRecBaixa, 7, 20, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Doc." ), 90, 0, 77, 20 );
		pinBaixa.adic( txtDoc, 90, 20, 77, 20 );
		pinBaixa.adic( new JLabelPad( " -" ), 170, 20, 7, 20 );
		pinBaixa.adic( new JLabelPad( "Série" ), 180, 0, 50, 20 );
		pinBaixa.adic( txtSerie, 180, 20, 50, 20 );
		pinBaixa.adic( new JLabelPad( "Pedido" ), 240, 0, 77, 20 );
		pinBaixa.adic( txtCodVendaBaixa, 240, 20, 77, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.cli." ), 7, 40, 250, 20 );
		pinBaixa.adic( txtCodCliBaixa, 7, 60, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do cliente" ), 90, 40, 250, 20 );
		pinBaixa.adic( txtRazCliBaixa, 90, 60, 207, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.banco" ), 300, 40, 250, 20 );
		pinBaixa.adic( txtCodBancoBaixa, 300, 60, 77, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do banco" ), 380, 40, 250, 20 );
		pinBaixa.adic( txtDescBancoBaixa, 380, 60, 150, 20 );
		pinBaixa.adic( new JLabelPad( "Data de emissão" ), 7, 80, 100, 20 );
		pinBaixa.adic( txtDtEmisBaixa, 7, 100, 120, 20 );
		pinBaixa.adic( new JLabelPad( "Total a pagar" ), 130, 80, 97, 20 );
		pinBaixa.adic( txtTotRecBaixa, 130, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total pago" ), 230, 80, 97, 20 );
		pinBaixa.adic( txtTotPagoBaixa, 230, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total em aberto" ), 330, 80, 107, 20 );
		pinBaixa.adic( txtTotAbertoBaixa, 330, 100, 107, 20 );
		pinBaixa.adic( new JLabelPad( "Juros" ), 440, 80, 80, 20 );
		pinBaixa.adic( txtJurosBaixa, 440, 100, 90, 20 );
		pinBaixa.adic( btCarregaBaixas, 540, 90, 30, 30 );

		pinBotoesBaixa.adic( btBaixa, 3, 10, 30, 30 );

		tabBaixa.adicColuna( "" );// 0
		tabBaixa.adicColuna( "Vencimento" ); // 1
		tabBaixa.adicColuna( "Nº Parcelas" ); // 2
		tabBaixa.adicColuna( "Doc." ); // 3
		tabBaixa.adicColuna( "Pedido" ); // 4
		tabBaixa.adicColuna( "Valor parcela" ); // 5
		tabBaixa.adicColuna( "Data Pagamento" ); // 6
		tabBaixa.adicColuna( "Valor pago" ); // 7
		tabBaixa.adicColuna( "Valor desc." ); // 8
		tabBaixa.adicColuna( "Valor juros" ); // 9
		tabBaixa.adicColuna( "Valor aberto" ); // 10
		tabBaixa.adicColuna( "Conta" ); // 11
		tabBaixa.adicColuna( "Categoria" ); // 12
		tabBaixa.adicColuna( "Centro de custo" ); // 13
		tabBaixa.adicColuna( "Observação" ); // 14

		tabBaixa.setTamColuna( 0, EColTabBaixa.IMGSTATUS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.DTVENC.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.NPARCITREC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.DOC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.CODVENDA.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPARC.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.DTPAGTO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPAGO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRDESC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRJUROS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRAPAG.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.NUMCONTA.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.CODPLAN.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.CODCC.ordinal() );
		tabBaixa.setTamColuna( 200, EColTabBaixa.OBS.ordinal() );

		// Manutenção

		tpn.addTab( "Manutenção", pnManut );

		pnManut.add( pinManut, BorderLayout.NORTH );

		pnTabManut.add( pinBotoesManut, BorderLayout.EAST );
		pnTabManut.add( spnManut, BorderLayout.CENTER );

		pnManut.add( pnTabManut, BorderLayout.CENTER );

		lcRecManut.add( new GuardaCampo( txtCodRecManut, "CodRec", "Cód.rec", ListaCampos.DB_PK, false ) );
		lcRecManut.add( new GuardaCampo( txtDocManut, "DocRec", "Doc.rec", ListaCampos.DB_SI, false ) );
		lcRecManut.add( new GuardaCampo( txtPedidoManut, "CodVenda", "Pedido", ListaCampos.DB_SI, false ) );
		lcRecManut.add( new GuardaCampo( txtCodCliManut, "CodCli", "Cod.cli", ListaCampos.DB_FK, false ) );
		lcRecManut.add( new GuardaCampo( txtDtEmitManut, "DataRec", "Data emissão", ListaCampos.DB_SI, false ) );
		lcRecManut.montaSql( false, "RECEBER", "FN" );
		lcRecManut.setQueryCommit( false );
		lcRecManut.setReadOnly( true );
		txtCodRecManut.setTabelaExterna( lcRecManut );
		txtCodRecManut.setFK( true );
		txtCodRecManut.setNomeCampo( "CodRec" );

		txtDatainiManut.setVlrDate( new Date() );
		txtDatafimManut.setVlrDate( new Date() );

		pinManut.adic( new JLabelPad( "Periodo" ), 7, 0, 200, 20 );
		pinManut.adic( txtDatainiManut, 7, 20, 100, 20 );
		pinManut.adic( new JLabelPad( "até" ), 113, 20, 27, 20 );
		pinManut.adic( txtDatafimManut, 140, 20, 100, 20 );
		pinManut.adic( btCarregaGridManut, 690, 55, 30, 30 );

		pinManut.adic( new JLabelPad( "Cód.cli." ), 7, 45, 250, 20 );
		pinManut.adic( txtCodCliFiltro, 7, 65, 50, 20 );
		pinManut.adic( new JLabelPad( "Razão social do cliente" ), 90, 45, 250, 20 );
		pinManut.adic( txtRazCliFiltro, 60, 65, 180, 20 );

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

		rgVenc = new JRadioGroup( 3, 2, vLabsVenc, vValsVenc );
		rgVenc.setVlrString( "TT" );
		pinManut.adic( new JLabelPad( "Filtrar por:" ), 365, 0, 150, 20 );
		pinManut.adic( rgVenc, 365, 20, 115, 65 );

		pinLbFiltroStatus.adic( lbFiltroStatus, 0, 0, 350, 15 );
		pinLbFiltroStatus.tiraBorda();

		pinManut.adic( pinLbFiltroStatus, 488, 3, 80, 15 );
		pinManut.adic( pinFiltroStatus, 488, 20, 130, 65 );

		pinFiltroStatus.adic( cbAReceber, 5, 0, 120, 20 );
		pinFiltroStatus.adic( cbRecebidas, 5, 20, 120, 20 );
		pinFiltroStatus.adic( cbRecParcial, 5, 40, 120, 20 );

		lcCliFiltro.add( new GuardaCampo( txtCodCliFiltro, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliFiltro.add( new GuardaCampo( txtRazCliFiltro, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliFiltro.montaSql( false, "CLIENTE", "VD" );
		lcCliFiltro.setQueryCommit( false );
		lcCliFiltro.setReadOnly( true );
		txtCodCliFiltro.setTabelaExterna( lcCliFiltro );
		txtCodCliFiltro.setFK( true );
		txtCodCliFiltro.setNomeCampo( "CodCli" );

		lcCliManut.add( new GuardaCampo( txtCodCliManut, "CodCli", "Cód.cli", ListaCampos.DB_PK, false ) );
		lcCliManut.add( new GuardaCampo( txtRazCliManut, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliManut.montaSql( false, "CLIENTE", "VD" );
		lcCliManut.setQueryCommit( false );
		lcCliManut.setReadOnly( true );
		txtCodCliManut.setTabelaExterna( lcCliManut );
		txtCodCliManut.setFK( true );
		txtCodCliManut.setNomeCampo( "CodCli" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		pinManut.adic( lbLinha, 5, 95, 720, 2 );

		pinManut.adic( new JLabelPad( "Cod.rec." ), 7, 100, 80, 20 );
		pinManut.adic( txtCodRecManut, 7, 120, 80, 20 );
		pinManut.adic( new JLabelPad( "Doc." ), 90, 100, 77, 20 );
		pinManut.adic( txtDocManut, 90, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Pedido" ), 170, 100, 77, 20 );
		pinManut.adic( txtPedidoManut, 170, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Cód.cli." ), 250, 100, 300, 20 );
		pinManut.adic( txtCodCliManut, 250, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Razão social do cliente" ), 350, 100, 300, 20 );
		pinManut.adic( txtRazCliManut, 330, 120, 247, 20 );
		pinManut.adic( new JLabelPad( "Data emissão " ), 580, 100, 100, 20 );
		pinManut.adic( txtDtEmitManut, 580, 120, 100, 20 );

		pinBotoesManut.adic( btBaixaManut, 3, 10, 30, 30 );
		pinBotoesManut.adic( btEditManut, 3, 40, 30, 30 );
		pinBotoesManut.adic( btNovoManut, 3, 70, 30, 30 );
		pinBotoesManut.adic( btEstorno, 3, 100, 30, 30 );
		pinBotoesManut.adic( btExcluirManut, 3, 130, 30, 30 );

		tabManut.adicColuna( "" ); // 0
		tabManut.adicColuna( "Dt.vencto." ); // 1
		tabManut.adicColuna( "Dt.emissão" ); // 2
		tabManut.adicColuna( "Cód.cli." ); // 3
		tabManut.adicColuna( "Razão social do cliente" ); // 4
		tabManut.adicColuna( "Cód.rec." ); // 5
		tabManut.adicColuna( "Nº parcela" ); // 6
		tabManut.adicColuna( "Doc.lanca" ); // 7
		tabManut.adicColuna( "Doc.venda" ); // 8
		tabManut.adicColuna( "Valor parc." ); // 9
		tabManut.adicColuna( "Data pagamento" ); // 10
		tabManut.adicColuna( "Valor.pago" ); // 11
		tabManut.adicColuna( "Valor desconto" ); // 12
		tabManut.adicColuna( "Valor juros" ); // 13
		tabManut.adicColuna( "Valor aberto" ); // 14
		tabManut.adicColuna( "Nro.conta" ); // 15
		tabManut.adicColuna( "Descrição da conta" ); // 16
		tabManut.adicColuna( "Cód.categ." ); // 17
		tabManut.adicColuna( "Categoria" ); // 18
		tabManut.adicColuna( "Cód.c.c." ); // 19 
		tabManut.adicColuna( "Descrição do centro de custo" ); // 20
		tabManut.adicColuna( "Cód.tp.cob" ); // 21
		tabManut.adicColuna( "Descrição do tipo de cobrança" ); // 22
		tabManut.adicColuna( "Cód.banco" ); // 23 
		tabManut.adicColuna( "Nome do banco" ); // 24
		tabManut.adicColuna( "Observação" ); // 25

		tabManut.setTamColuna( 0, EColTabManut.IMGSTATUS.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DTVENC.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.CODCLI.ordinal() );
		tabManut.setTamColuna( 200, EColTabManut.RAZCLI.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.CODREC.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.NPARCITREC.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.DOCLANCA.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.DOCVENDA.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRPARC.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DTPAGTO.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRPAGO.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.VLRDESC.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.VLRJUROS.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.VLRAPAG.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.NUMCONTA.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DESCCONTA.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.CODPLAN.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DESCPLAN.ordinal());
		tabManut.setTamColuna( 80, EColTabManut.CODCC.ordinal() );
		tabManut.setTamColuna( 130, EColTabManut.DESCCC.ordinal() );
		tabManut.setTamColuna( 230, EColTabManut.CODTIPOCOB.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODBANCO.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.DESCTIPOCOB.ordinal() );
		tabManut.setTamColuna( 240, EColTabManut.NOMEBANCO.ordinal() );
		tabManut.setTamColuna( 240, EColTabManut.OBS.ordinal() );

		lcRecBaixa.addCarregaListener( this );
		lcRecManut.addCarregaListener( this );
		txtCodCli.addKeyListener( this );
		txtCodCliBaixa.addKeyListener( this );
		btBaixa.addActionListener( this );
		btBaixaConsulta.addActionListener( this );
		btBaixaManut.addActionListener( this );
		btEditManut.addActionListener( this );
		btNovoManut.addActionListener( this );
		btExcluirManut.addActionListener( this );
		btCarregaGridManut.addActionListener( this );
		btEstorno.addActionListener( this );
		btBaixa.addActionListener( this );
		btCarregaBaixas.addActionListener( this );
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

			// Busca totais ...
			sSQL.append( "SELECT SUM(VLRPARCREC),SUM(VLRPAGOREC),SUM(VLRAPAGREC),MIN(DATAREC),MAX(DATAREC) " ); 
			sSQL.append( "FROM FNRECEBER " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, txtCodCli.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				txtVlrTotCompr.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 1 ) ) );
				txtVlrTotPago.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 2 ) ) );
				txtVlrTotAberto.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( 3 ) ) );
				txtPrimCompr.setVlrString( rs.getDate( 4 ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( 4 ) ) : "" );
				txtUltCompr.setVlrString( rs.getDate( 5 ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( 5 ) ) : "" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			// Busca a maior fatura ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT MAX(VLRPARCREC),DATAREC " ); 
			sSQL.append( "FROM FNRECEBER " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " ); 
			sSQL.append( "GROUP BY DATAREC " );
			sSQL.append( "ORDER BY 1 DESC" );

			ps1 = con.prepareStatement( sSQL.toString() );
			ps1.setInt( 1, Aplicativo.iCodEmp );
			ps1.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps1.setInt( 3, txtCodCli.getVlrInteger().intValue() );
			
			rs1 = ps1.executeQuery();
			
			if ( rs1.next() ) {
				txtVlrMaxFat.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs1.getString( 1 ) ) );
				txtDataMaxFat.setVlrString( rs1.getDate( "DATAREC" ) != null ? Funcoes.sqlDateToStrDate( rs1.getDate( "DATAREC" ) ) : "" );
			}
			
			rs1.close();
			ps1.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			// Busca o maior acumulo ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT EXTRACT(MONTH FROM DATAREC), SUM(VLRPARCREC), EXTRACT(YEAR FROM DATAREC) " ); 
			sSQL.append( "FROM FNRECEBER " ); 
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " ); 
			sSQL.append( "GROUP BY 1, 3 " ); 
			sSQL.append( "ORDER BY 2 DESC" );
			
			ps2 = con.prepareStatement( sSQL.toString() );
			ps2.setInt( 1, Aplicativo.iCodEmp );
			ps2.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps2.setInt( 3, txtCodCli.getVlrInteger().intValue() );
			
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
			rs = null;
			sSQL = null;
		}
	}

	private void carregaGridConsulta() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		float bdVlrAReceber = 0.0f;
		float bdVlrPago = 0.0f;

		try {

			vCodRec.clear();
			vNParcItRec.clear();

			sSQL.append( "SELECT IT.DTVENCITREC,IT.STATUSITREC," ); 
			sSQL.append( "(SELECT SERIE FROM VDVENDA V " );
			sSQL.append( "WHERE V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA " ); 
			sSQL.append( "AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) AS SERIE," ); 
			sSQL.append( "R.DOCREC,R.CODVENDA,R.DATAREC,IT.VLRPARCITREC,IT.DTPAGOITREC,IT.VLRPAGOITREC," ); 
			sSQL.append( "(CASE WHEN IT.DTPAGOITREC IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITREC " );
			sSQL.append( "ELSE IT.DTPAGOITREC - IT.DTVENCITREC END ) ATRASO,R.OBSREC," ); 
			sSQL.append( "(SELECT B.NOMEBANCO FROM FNBANCO B " );
			sSQL.append( "WHERE B.CODBANCO=IT.CODBANCO AND B.CODEMP=IT.CODEMPBO AND B.CODFILIAL=IT.CODFILIALBO) AS NOMEBANCO," ); 
			sSQL.append( "R.CODREC,IT.NPARCITREC,IT.VLRDESCITREC,IT.VLRJUROSITREC,R.TIPOVENDA,IT.VLRAPAGITREC " );
			sSQL.append( "FROM FNRECEBER R,FNITRECEBER IT " );
			sSQL.append( "WHERE R.CODCLI=? AND R.CODEMP=? AND R.CODFILIAL=? AND IT.CODREC=R.CODREC " ); 
			sSQL.append( "AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL " );
			sSQL.append( "ORDER BY R.CODREC,IT.NPARCITREC" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			
			rs = ps.executeQuery();
			
			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0.0f ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabConsulta.adicLinha();
				tabConsulta.setValor( imgColuna, i, 0 );
				tabConsulta.setValor( ( rs.getDate( "DtVencItRec" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItRec" ) ) : "" ), i, 1 );
				tabConsulta.setValor( ( rs.getString( "DocRec" ) != null ? rs.getString( "DocRec" ) : "" ), i, 2 );
				tabConsulta.setValor( ( rs.getDate( "DataRec" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DataRec" ) ) : "" ), i, 3 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItRec" ) ), i, 4 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrDescItRec" ) ), i, 5 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrPagoItRec" ) ), i, 6 );
				tabConsulta.setValor( ( rs.getDate( "DtPagoItRec" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItRec" ) ) : "" ), i, 7 );
				tabConsulta.setValor( new Integer( rs.getInt( 10 ) ), i, 8 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrJurosItRec" ) ), i, 9 );
				tabConsulta.setValor( ( rs.getString( "Serie" ) != null ? rs.getString( "Serie" ) : "" ), i, 10 );
				tabConsulta.setValor( String.valueOf( rs.getInt( "CodVenda" ) ), i, 11 );
				tabConsulta.setValor( rs.getString( 11 ) != null ? rs.getString( 11 ) : "", i, 12 );
				tabConsulta.setValor( rs.getString( "ObsRec" ) != null ? rs.getString( "ObsRec" ) : "", i, 13 );
				tabConsulta.setValor( rs.getString( "TipoVenda" ), i, 14 );
				vCodRec.addElement( rs.getInt( "CodRec" ) );
				vNParcItRec.addElement( rs.getInt( "NParcItRec" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de consulta!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
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
		String sCodBanco = null;
		float bdVlrAReceber = 0.0f;
		float bdVlrPago = 0.0f;

		try {

			vNParcBaixa.clear();
			//vNumContas.clear();
			//vCodPlans.clear();
			//vCodCCs.clear();
			//vCodPed.clear();
			tabBaixa.limpa();

			sSQL.append( "SELECT IR.DTVENCITREC,IR.STATUSITREC,R.CODREC,IR.DOCLANCAITREC," );
			sSQL.append( "R.CODVENDA,IR.VLRPARCITREC,IR.DTPAGOITREC,IR.VLRPAGOITREC," );
			sSQL.append( "IR.VLRAPAGITREC,IR.NUMCONTA,IR.VLRDESCITREC," );
			sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
			sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
			sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA),IR.CODPLAN," ); 
			sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
			sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN),IR.CODCC," ); 
			sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
			sSQL.append( "WHERE CC.CODCC=IR.CODCC AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC)," ); 
			sSQL.append( "IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC,IR.DTITREC," );
			sSQL.append( "(SELECT V.DOCVENDA FROM VDVENDA V " );
			sSQL.append( "WHERE V.CODEMP=R.CODEMPVA " );
			sSQL.append( "AND V.CODFILIAL=R.CODFILIALVA AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) AS DOCVENDA," ); 
			sSQL.append( "(SELECT C.CODBANCO FROM VDCLIENTE C " );
			sSQL.append( "WHERE C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI) AS CODBANCO " ); 
			sSQL.append( "FROM FNITRECEBER IR,FNRECEBER R  " );
			sSQL.append( "WHERE IR.CODREC=R.CODREC AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? " ); 
			sSQL.append( "ORDER BY IR.DTVENCITREC,IR.STATUSITREC" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodRecBaixa.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0.0f ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabBaixa.adicLinha();
				tabBaixa.setValor( imgColuna, i, 0 );
				tabBaixa.setValor( ( rs.getDate( "DtVencItRec" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItRec" ) ) : "" ), i, 1 );
				tabBaixa.setValor( rs.getString( "NParcItRec" ), i, 2 );
				tabBaixa.setValor( ( rs.getString( "DocLancaItRec" ) != null ? rs.getString( "DocLancaItRec" ) : rs.getString( "DocVenda" ) ), i, 3 );
				tabBaixa.setValor( rs.getString( "CodVenda" ), i, 4 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItRec" ) ), i, 5 );
				tabBaixa.setValor( ( rs.getDate( "DtPagoItRec" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItRec" ) ) : "" ), i, 6 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrPagoItRec" ) ), i, 7 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrDescItRec" ) ), i, 8 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrJurosItRec" ) ), i, 9 );
				tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrApagItRec" ) ), i, 10 );
				tabBaixa.setValor( rs.getString( 12 ) != null ? rs.getString( 12 ) : "", i, 11 );
				tabBaixa.setValor( rs.getString( 14 ) != null ? rs.getString( 14 ) : "", i, 12 );
				tabBaixa.setValor( rs.getString( 16 ) != null ? rs.getString( 16 ) : "", i, 13 );
				tabBaixa.setValor( rs.getString( "ObsItRec" ) != null ? rs.getString( "ObsItRec" ) : "", i, 14 );
				//vCodPed.addElement( rs.getString( "CodVenda" ) );
				vNParcBaixa.addElement( rs.getString( "NParcItRec" ) );
				//vNumContas.addElement( rs.getString( "NumConta" ) != null ? rs.getString( "NumConta" ) : "" );
				//vCodPlans.addElement( rs.getString( "CodPlan" ) != null ? rs.getString( "CodPlan" ) : "" );
				//vCodCCs.addElement( rs.getString( "CodCC" ) != null ? rs.getString( "CodCC" ) : "" );
				sCodBanco = rs.getString( "CODBANCO" );

			}
			
			txtCodBancoBaixa.setVlrString( sCodBanco );
			lcBancoBaixa.carregaDados();
			
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

	private void carregaGridManut( boolean bAplicFiltros ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhereManut = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();
		float bdVlrAReceber = 0.0f;
		float bdVlrPago = 0.0f;

		if ( bAplicFiltros ) {
			
			if ( ! validaPeriodo() ) {
				return;
			}

			sWhereManut.append( " AND " ); 
			sWhereManut.append( rgData.getVlrString().equals( "V" ) ? "IR.DTVENCITREC" : "IR.DTITREC" ); 
			sWhereManut.append( " BETWEEN ? AND ? AND R.CODEMP=? AND R.CODFILIAL=?" );

			if ( "S".equals( cbRecebidas.getVlrString() ) || 
					"S".equals( cbAReceber.getVlrString() ) || 
						"S".equals( cbRecParcial.getVlrString() ) ) {

				boolean bStatus = false;
				
				if ( "S".equals( cbRecebidas.getVlrString() ) ) {
					sWhereStatus.append( "IR.STATUSITREC='RP'" );
					bStatus = true;
				}
				if ( "S".equals( cbAReceber.getVlrString() ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='R1' " : " IR.STATUSITREC='R1' " );
					bStatus = true;
				}
				if ( "S".equals( cbRecParcial.getVlrString() ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RL' " : " IR.STATUSITREC='RL' " );
					bStatus = true;
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
				
				sWhereManut.append( " AND IR.DTVENCITREC" );
				
				if ( rgVenc.getVlrString().equals( "VE" ) ) {
					sWhereManut.append( " <'" );
					sWhereManut.append( Funcoes.dateToStrDB( Calendar.getInstance().getTime() ) );
					sWhereManut.append( "'" );
				}
				else {
					sWhereManut.append( " >='" );
					sWhereManut.append( Funcoes.dateToStrDB( Calendar.getInstance().getTime() ) );
					sWhereManut.append( "'" );
				}
			}
			if ( ! "".equals( txtCodCliFiltro.getVlrString().trim() ) ) {
				sWhereManut.append( " AND R.CODCLI=" );
				sWhereManut.append( txtCodCliFiltro.getVlrString() );
			}
		}
		else {
			sWhereManut.append( " AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? " );
		}

		try {

			tabManut.limpa();
			//vNumContas.clear();
			//vCodPlans.clear();
			//vCodCCs.clear();
			//vCodBOs.clear();
			//vDtEmiss.clear();
			//vCodPed.clear();

			sSQL.append( "SELECT IR.DTVENCITREC,IR.STATUSITREC,R.CODCLI,C.RAZCLI,R.CODREC,IR.DOCLANCAITREC," );
			sSQL.append( "R.CODVENDA,IR.VLRPARCITREC,IR.DTPAGOITREC,IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA," ); 
			sSQL.append( "IR.VLRDESCITREC,IR.CODPLAN,IR.CODCC,IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC," ); 
			sSQL.append( "IR.DTITREC,IR.CODBANCO," ); 
			sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " ); 
			sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " ); 
			sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODEMP=IR.CODEMPCA) DESCCONTA," ); 
			sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " ); 
			sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN " ); 
			sSQL.append( "AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," ); 
			sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " ); 
			sSQL.append( "WHERE CC.CODCC=IR.CODCC " ); 
			sSQL.append( "AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC) DESCCC," ); 
			sSQL.append( "(SELECT VD.DOCVENDA FROM VDVENDA VD " ); 
			sSQL.append( "WHERE VD.TIPOVENDA=R.TIPOVENDA AND VD.CODVENDA=R.CODVENDA AND " ); 
			sSQL.append( " VD.CODEMP=R.CODEMPVA AND VD.CODFILIAL=R.CODFILIALVA) DOCVENDA," );
			sSQL.append( "IR.CODTIPOCOB, " );
			sSQL.append( "(SELECT TP.DESCTIPOCOB FROM FNTIPOCOB TP " ); 
			sSQL.append( "WHERE TP.CODEMP=IR.CODEMPTC " ); 
			sSQL.append( "AND TP.CODFILIAL=IR.CODFILIALTC AND TP.CODTIPOCOB=IR.CODTIPOCOB) DESCTIPOCOB, " ); 
			sSQL.append( "(SELECT BO.NOMEBANCO FROM FNBANCO BO WHERE BO.CODBANCO=IR.CODBANCO " ); 
			sSQL.append( "AND BO.CODEMP=IR.CODEMPBO AND BO.CODFILIAL=IR.CODFILIALBO) NOMEBANCO " ); 
			sSQL.append( "FROM FNITRECEBER IR, FNRECEBER R, VDCLIENTE C " ); 
			sSQL.append( "WHERE R.CODREC=IR.CODREC AND C.CODCLI=R.CODCLI " ); 
			sSQL.append( sWhereManut ); 
			sSQL.append( " ORDER BY IR.DTVENCITREC,IR.STATUSITREC" );

			ps = con.prepareStatement( sSQL.toString() );
			
			if ( bAplicFiltros ) {
				ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
				ps.setInt( 3, Aplicativo.iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			}
			else {
				ps.setInt( 1, txtCodRecManut.getVlrInteger().intValue() );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			}
			
			rs = ps.executeQuery();
			
			for ( int i = 0; rs.next(); i++ ) {
				
				tabManut.adicLinha();

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0 ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabManut.setValor( imgColuna, i, EColTabManut.IMGSTATUS.ordinal() );
				tabManut.setValor( Funcoes.sqlDateToDate( rs.getDate( "DTVENCITREC" ) ) , i, EColTabManut.DTVENC.ordinal() );
				tabManut.setValor( Funcoes.sqlDateToDate( rs.getDate( "DTITREC" ) ) , i, EColTabManut.DTEMIT.ordinal() );
				tabManut.setValor( rs.getInt( "CODCLI" ), i, EColTabManut.CODCLI.ordinal() );
				tabManut.setValor( rs.getString( "RAZCLI" ), i, EColTabManut.RAZCLI.ordinal() );
				tabManut.setValor( rs.getInt( "CODREC" ), i, EColTabManut.CODREC.ordinal() );
				tabManut.setValor( rs.getInt( "NPARCITREC" ), i, EColTabManut.NPARCITREC.ordinal() );
				tabManut.setValor( ( rs.getString( "DOCLANCAITREC" ) != null ? rs.getString( "DocLancaItRec" ) : "" ), i, EColTabManut.DOCLANCA.ordinal() );
				tabManut.setValor( rs.getInt( "DOCVENDA" ), i, EColTabManut.DOCVENDA.ordinal() );// DOCVENDA
				tabManut.setValor( rs.getBigDecimal(  "VLRPARCITREC" ) , i, EColTabManut.VLRPARC.ordinal() );
				tabManut.setValor( ( rs.getDate( "DTPAGOITREC" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItRec" ) ) : "" ), i, EColTabManut.DTPAGTO.ordinal() );
				tabManut.setValor( rs.getBigDecimal( "VLRPAGOITREC" ), i, EColTabManut.VLRPAGO.ordinal() );
				tabManut.setValor( rs.getBigDecimal( "VLRDESCITREC" ), i, EColTabManut.VLRDESC.ordinal() );
				tabManut.setValor( rs.getBigDecimal( "VLRJUROSITREC"  ), i, EColTabManut.VLRJUROS.ordinal() );
				tabManut.setValor( rs.getBigDecimal( "VLRAPAGITREC" ), i, EColTabManut.VLRAPAG.ordinal() );
				tabManut.setValor( rs.getString( "NUMCONTA" ) != null ? rs.getString( "NUMCONTA" ) : "", i, EColTabManut.NUMCONTA.ordinal() );// NUMCONTA
				tabManut.setValor( rs.getString( "DESCCONTA" ) != null ? rs.getString( "DESCCONTA" ) : "", i, EColTabManut.DESCCONTA.ordinal() );// DESCCONTA
				tabManut.setValor( rs.getString( "CODPLAN" ) != null ? rs.getString( "CODPLAN" ) : "", i, EColTabManut.CODPLAN.ordinal() );// CODPLAN
				tabManut.setValor( rs.getString( "DESCPLAN" ) != null ? rs.getString( "DESCPLAN" ) : "", i, EColTabManut.DESCPLAN.ordinal() );// DESCPLAN
				tabManut.setValor( rs.getString( "CODCC" ) != null ? rs.getString( "CODCC" ) : "", i, EColTabManut.CODCC.ordinal() );// CODCC
				tabManut.setValor( rs.getString( "DESCCC" ) != null ? rs.getString( "DESCCC" ) : "", i, EColTabManut.DESCCC.ordinal() );// DESCCC
				tabManut.setValor( rs.getString( "CODTIPOCOB" ) != null ? rs.getString( "CODTIPOCOB" ) : "", i, EColTabManut.CODTIPOCOB.ordinal() );// TIPOCOB
				tabManut.setValor( rs.getString( "DESCTIPOCOB" ) != null ? rs.getString( "DESCTIPOCOB" ) : "", i, EColTabManut.DESCTIPOCOB.ordinal() );// DESCTIPOCOB
				tabManut.setValor( rs.getString( "CODBANCO" ) != null ? rs.getString( "CODBANCO" ) : "", i, EColTabManut.CODBANCO.ordinal() );// CODBANCO
				tabManut.setValor( rs.getString( "NOMEBANCO" ) != null ? rs.getString( "NOMEBANCO" ) : "", i, EColTabManut.NOMEBANCO.ordinal() );// NOMEBANCO
				tabManut.setValor( rs.getString( "ObsItRec" ) != null ? rs.getString( "ObsItRec" ) : "", i, EColTabManut.OBS.ordinal() );
				//vCodPed.addElement( rs.getString( "CodVenda" ) );
				//vNumContas.addElement( rs.getString( "NumConta" ) != null ? rs.getString( "NumConta" ) : "" );
				//vCodPlans.addElement( rs.getString( "CodPlan" ) != null ? rs.getString( "CodPlan" ) : "" );
				//vCodCCs.addElement( rs.getString( "CodCC" ) != null ? rs.getString( "CodCC" ) : "" );
				//vCodBOs.addElement( rs.getString( "CodBanco" ) != null ? rs.getString( "CodBanco" ) : "" );
				//vDtEmiss.addElement( Funcoes.sqlDateToDate( rs.getDate( "DtItRec" ) ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhereManut = null;
			sWhereStatus = null;
		}
	}

	private void editar() {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		Object[] sVals = new Object[ 13 ];
		Object[] sRets = null;
		DLEditaRec dl = null;
		ImageIcon imgStatusAt = null;
		
		try {
			
			if ( tabManut.getLinhaSel() > -1 ) {
				
				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.IMGSTATUS.ordinal() );
				
				if ( imgStatusAt != imgPago ) {
					
					int iLin = tabManut.getLinhaSel();
					iCodRec =  (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() );
					iNParcItRec =  (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() );
					
					dl = new DLEditaRec( this );

					sVals[ EColEdit.CODCLI.ordinal() ] = (Integer) tabManut.getValor( iLin, EColTabManut.CODCLI.ordinal() );
					sVals[ EColEdit.RAZCLI.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.RAZCLI.ordinal() ) );
					sVals[ EColEdit.NUMCONTA.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.NUMCONTA.ordinal() ) );
					sVals[ EColEdit.CODPLAN.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODPLAN.ordinal() ) );
					sVals[ EColEdit.CODCC.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODCC.ordinal() ) );
					if ("".equals(  tabManut.getValor(iLin, EColTabManut.DOCLANCA.ordinal()))) {
						sVals[ EColEdit.DOC.ordinal() ] = String.valueOf(tabManut.getValor(iLin, EColTabManut.DOCVENDA.ordinal()));
					} else {
						sVals[ EColEdit.DOC.ordinal() ] = tabManut.getValor(iLin, EColTabManut.DOCLANCA.ordinal());
					}
					sVals[ EColEdit.DOC.ordinal() ] = (String) tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() );
					sVals[ EColEdit.DTEMIS.ordinal() ] = tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() );
					sVals[ EColEdit.DTVENC.ordinal() ] =  tabManut.getValor( iLin, EColTabManut.DTVENC.ordinal() ) ;
					sVals[ EColEdit.VLRJUROS.ordinal() ] = (BigDecimal) tabManut.getValor( iLin, EColTabManut.VLRJUROS.ordinal() ) ;
					sVals[ EColEdit.VLRDESC.ordinal() ] = (BigDecimal) tabManut.getValor( iLin, EColTabManut.VLRDESC.ordinal() ) ;
					sVals[ EColEdit.VLRPARC.ordinal() ] = (BigDecimal) tabManut.getValor( iLin, EColTabManut.VLRPARC.ordinal() ) ;

					if ( "".equals( String.valueOf( tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) ).trim() ) ) {
						sVals[ EColEdit.OBS.ordinal() ] = "PAGAMENTO REF. A VENDA: " + tabManut.getValor( iLin, EColTabManut.DOCVENDA.ordinal() );
					}
					else {
						sVals[ EColEdit.OBS.ordinal() ] = tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) ;
					}

					sVals[ EColEdit.CODBANCO.ordinal() ] = tabManut.getValor( iLin, EColTabManut.CODBANCO.ordinal() ) ;
					
					dl.setValores( sVals );
					dl.setConexao( con );	
					dl.setVisible( true );

					if ( dl.OK ) {
						
						sRets = dl.getValores();
						
						sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," ); 
						sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC =?,VLRJUROSITREC=?," );
						sSQL.append( "VLRDESCITREC=?,DTVENCITREC=?,OBSITREC=?,CODEMPBO=?,CODFILIALBO=?,CODBANCO=? " ); 
						sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
						
						try {
							ps = con.prepareStatement( sSQL.toString() );
							
							if ( "".equals( sRets[ EColRet.NUMCONTA.ordinal() ] ) ) {
								ps.setNull( 1, Types.CHAR );
								ps.setNull( 2, Types.INTEGER );
								ps.setNull( 3, Types.INTEGER );
							}
							else {
								ps.setString( 1, (String) sRets[ EColRet.NUMCONTA.ordinal() ] );
								ps.setInt( 2, Aplicativo.iCodEmp );
								ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
							}
							if ( "".equals( String.valueOf( sRets[ EColRet.CODPLAN.ordinal() ] ).trim() ) ) {
								ps.setNull( 4, Types.CHAR );
								ps.setNull( 5, Types.INTEGER );
								ps.setNull( 6, Types.INTEGER );
							}
							else {
								ps.setString( 4, (String) sRets[ EColRet.CODPLAN.ordinal() ] );
								ps.setInt( 5, Aplicativo.iCodEmp );
								ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
							}
							if ( "".equals( String.valueOf(sRets[ EColRet.CODCC.ordinal() ]).trim() ) ) {
								ps.setNull( 7, Types.INTEGER );
								ps.setNull( 8, Types.CHAR );
								ps.setNull( 9, Types.INTEGER );
								ps.setNull( 10, Types.INTEGER );
							}
							else {
								ps.setInt( 7, iAnoCC );
								ps.setString( 8, (String) sRets[ EColRet.CODCC.ordinal() ] );
								ps.setInt( 9, Aplicativo.iCodEmp );
								ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
							}
							if ( "".equals( String.valueOf(sRets[ EColRet.DOC.ordinal() ]).trim() ) ) {
								ps.setNull( 11, Types.CHAR );
							}
							else {
								ps.setString( 11, (String) sRets[ EColRet.DOC.ordinal() ] );
							}
							if ( "".equals( sRets[ EColRet.VLRJUROS.ordinal() ] ) ) {
								ps.setNull( 12, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 12, (BigDecimal) sRets[ EColRet.VLRJUROS.ordinal() ] );
							}
							if ( "".equals( sRets[ EColRet.VLRDESC.ordinal() ] ) ) {
								ps.setNull( 13, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 13, (BigDecimal)( sRets[ EColRet.VLRDESC.ordinal() ] ) );
							}
							if ( "".equals( sRets[ EColRet.DTVENC.ordinal() ] ) ) {
								ps.setNull( 14, Types.DECIMAL );
							}
							else {
								ps.setDate( 14, Funcoes.dateToSQLDate( (java.util.Date) sRets[ EColRet.DTVENC.ordinal() ] ) );
							}
							if ( "".equals( sRets[ EColRet.OBS.ordinal() ]) ) {
								ps.setNull( 15, Types.CHAR );
							}
							else {
								ps.setString( 15, (String) sRets[ EColRet.OBS.ordinal() ] );
							}
							if ( "".equals( sRets[ EColRet.CODBANCO.ordinal() ] ) ) {
								ps.setNull( 16, Types.INTEGER );
								ps.setNull( 17, Types.INTEGER );
								ps.setNull( 18, Types.CHAR );
							}
							else {
								ps.setInt( 16, Aplicativo.iCodEmp );
								ps.setInt( 17, ListaCampos.getMasterFilial( "FNBANCO" ) );
								ps.setString( 18, (String) sRets[ EColRet.CODBANCO.ordinal() ] );
							}
							
							ps.setInt( 19, iCodRec );
							ps.setInt( 20, iNParcItRec );
							ps.setInt( 21, Aplicativo.iCodEmp );
							ps.setInt( 22, ListaCampos.getMasterFilial( "FNRECEBER" ) );
							
							ps.executeUpdate();
							
						} catch ( SQLException err ) {
							Funcoes.mensagemErro( this, "Erro ao editar parcela!\n" + err.getMessage(), true, con, err );
							err.printStackTrace();
						}
					}
					
					dl.dispose();
					
					carregaGridManut( bBuscaAtual );
				}
				else {
					Funcoes.mensagemErro( this, "NÃO É POSSIVEL EDITAR.\n" + "A PARCELA JÁ FOI PAGA." );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			sSQL = null;
			sVals = new String[ 13 ];
			sRets = null;
			dl = null;
			imgStatusAt = null;
		}
	}
	
	private void carregaVenda() {
		
		int iLin = tabConsulta.getLinhaSel();		
		if ( iLin < 0 ) {
			Funcoes.mensagemInforma( this, "Selecione uma parcela." );
			return;
		}
			
		int iCodVenda = Integer.parseInt( (String) tabConsulta.getValor( iLin, 11 ) );
		
		String sTipoVenda = (String) tabConsulta.getValor( iLin, 14 );
		
		if ( iCodVenda > 0 ) {
			
			DLConsultaVenda dl = new DLConsultaVenda( this, con, iCodVenda, sTipoVenda );
			dl.setVisible( true );
			dl.dispose();
		}
		else {
			Funcoes.mensagemInforma( null, "Este lançamento não possui vínculo com uma venda!" );
		}
	}

	private void consBaixa() {

		int iLin = tabBaixa.getLinhaSel();
		if ( iLin < 0 ) {
			Funcoes.mensagemInforma( this, "Selecione uma parcela." );
			return;
		}
		
		DLConsultaBaixa dl = new DLConsultaBaixa( this, 
					con, 
					txtCodRecBaixa.getVlrInteger().intValue(), 
					Integer.parseInt( tabBaixa.getValor( iLin, 2 ).toString() ) );
		
		dl.setValores( new BigDecimal[] { Funcoes.strCurrencyToBigDecimal( tabBaixa.getValor( iLin, 5 ).toString() ), 
				Funcoes.strCurrencyToBigDecimal( tabBaixa.getValor( iLin, 7 ).toString() ), 
				Funcoes.strCurrencyToBigDecimal( tabBaixa.getValor( iLin, 8 ).toString() ),
				Funcoes.strCurrencyToBigDecimal( tabBaixa.getValor( iLin, 9 ).toString() ), 
				Funcoes.strCurrencyToBigDecimal( tabBaixa.getValor( iLin, 10 ).toString() ) } 
		);
		
		dl.setVisible( true );
		dl.dispose();
	}

	private void baixaConsulta() {

		if ( tabConsulta.getLinhaSel() != -1 ) {
			
			txtCodRecBaixa.setVlrString( (String) vCodRec.elementAt( tabConsulta.getLinhaSel() ) );
			int iNParc = ( new Integer( (String) vNParcItRec.elementAt( tabConsulta.getLinhaSel() ) ) ).intValue();
			
			lcRecBaixa.carregaDados();
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
		Object[] sVals = null;
		String[] sRelPlanPag = null;
		String[] sRets = null;
		DLBaixaRec dl = null;
		ImageIcon imgStatusAt = null;
		
		try {
			
			if ( ( 'M' == cOrig ) & ( tabManut.getLinhaSel() > -1 ) ) {
				
				imgStatusAt = ( (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.IMGSTATUS.ordinal() ) );
				
				if ( imgStatusAt == imgPago ) {
					
					Funcoes.mensagemInforma( this, "Parcela já foi baixada!" );
					return;
				}
				
				int iLin = tabManut.getLinhaSel();
				
				if ( iLin < 0 ) {
					
					Funcoes.mensagemInforma( this, "Selecionde uma parcela." );
					return;
				}

				iCodRec = (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() );
				iNParcItRec = (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() );
				
				sVals = new Object[ 15 ];
				sRelPlanPag = buscaRelPlanPag( (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() ) );
				
				dl = new DLBaixaRec( this );
				
				sVals[ EColBaixa.CODCLI.ordinal() ] = (String) tabManut.getValor( iLin, 2 );
				sVals[ EColBaixa.RAZCLI.ordinal() ] = (String) tabManut.getValor( iLin, 3 );
				sVals[ EColBaixa.NUMCONTA.ordinal() ] = tabManut.getValor( iLin, EColTabManut.NUMCONTA.ordinal() );
				sVals[ EColBaixa.CODPLAN.ordinal() ] = tabManut.getValor( iLin, EColTabManut.CODPLAN.ordinal() );
				sVals[ EColBaixa.DOC.ordinal() ] = (String) ( "".equals( tabManut.getValor( iLin, 6 ) ) ? tabManut.getValor( iLin, 7 ) : tabManut.getValor( iLin, 6 ) );
				sVals[ EColBaixa.DTEMIT.ordinal() ] = tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() );
				sVals[ EColBaixa.DTVENC.ordinal() ] = (String) tabManut.getValor( iLin, 1 );
				sVals[ EColBaixa.VLRPARC.ordinal() ] = (String) tabManut.getValor( iLin, 8 );
				sVals[ 8 ] = (String) tabManut.getValor( iLin, 11 );
				sVals[ 9 ] = (String) tabManut.getValor( iLin, 12 );
				sVals[ 10 ] = (String) tabManut.getValor( iLin, 13 );
				sVals[ 13 ] = tabManut.getValor( iLin, EColTabManut.CODCC.ordinal() );
				if (  "".trim().equals( (String) tabManut.getValor( iLin, 9 ) ) ) {
					sVals[ 11 ] = Funcoes.dateToStrDate( new Date() );
					sVals[ 12 ] = (String) tabManut.getValor( iLin, 10 );
				}
				else {
					sVals[ 11 ] = (String) tabManut.getValor( iLin, 9 );
					sVals[ 12 ] = (String) tabManut.getValor( iLin, 10 );
				}
				if ( "".trim().equals( (String) tabManut.getValor( iLin, 20 ) ) ) {
					sVals[ 14 ] = "RECEBIMENTO REF. AO PED.: " + tabManut.getValor( iLin, 7 );
				}
				else {
					sVals[ 14 ] = (String) tabManut.getValor( iLin, 20 );
				}

				dl.setValores( sVals );
				dl.setConexao( con );
				dl.setVisible( true );
				
				if ( dl.OK ) {
					
					sRets = dl.getValores();
					
					sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," ); 
					sSQL.append( "DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?,VLRDESCITREC=?,VLRJUROSITREC=?,ANOCC=?," ); 
					sSQL.append( "CODCC=?,CODEMPCC=?,CODFILIALCC=?,OBSITREC=?,STATUSITREC='RP' " );
					sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
					
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
						ps.setBigDecimal( 10, Funcoes.strCurrencyToBigDecimal( sRets[ 5 ] ) );
						ps.setBigDecimal( 11, Funcoes.strCurrencyToBigDecimal( sRets[ 6 ] ) );
						if ( "".equals( sRets[ 7 ].trim() ) ) {
							ps.setNull( 12, Types.INTEGER );
							ps.setNull( 13, Types.CHAR );
							ps.setNull( 14, Types.INTEGER );
							ps.setNull( 15, Types.INTEGER );
						}
						else {
							ps.setInt( 12, iAnoCC );
							ps.setString( 13, sRets[ 7 ] );
							ps.setInt( 14, Aplicativo.iCodEmp );
							ps.setInt( 15, ListaCampos.getMasterFilial( "FNCC" ) );
						}

						ps.setString( 16, sRets[ 8 ] );
						ps.setInt( 17, iCodRec );
						ps.setInt( 18, iNParcItRec );
						ps.setInt( 19, Aplicativo.iCodEmp );
						ps.setInt( 20, ListaCampos.getMasterFilial( "FNRECEBER" ) );
						
						ps.executeUpdate();
						
						if ( ! con.getAutoCommit() ) {
							con.commit();
						}
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
					}
				}
				
				dl.dispose();
				carregaGridManut( bBuscaAtual );
			}
			else if ( ( cOrig == 'B' ) & ( tabBaixa.getLinhaSel() > -1 ) ) {
				
				imgStatusAt = ( (ImageIcon) tabBaixa.getValor( tabBaixa.getLinhaSel(), 0 ) );
				
				if ( imgStatusAt == imgPago ) {
					
					Funcoes.mensagemInforma( this, "Parcela já foi baixada!" );
					return;
				}
				
				int iLin = tabBaixa.getLinhaSel();
				
				iCodRec = txtCodRecBaixa.getVlrInteger().intValue();
				iNParcItRec = Integer.parseInt( (String) tabBaixa.getValor( iLin, 2 ) );
				
				sVals = new String[ 15 ];
				sRelPlanPag = buscaRelPlanPag( txtCodRecBaixa.getVlrInteger().intValue() );
				
				dl = new DLBaixaRec( this );

				sVals[ EColBaixa.CODCLI.ordinal() ] = (String) txtCodCliBaixa.getVlrString(); // Codcli
				sVals[ EColBaixa.RAZCLI.ordinal() ] = (String) txtRazCliBaixa.getVlrString(); // Razcli
				sVals[ EColBaixa.NUMCONTA.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.NUMCONTA.ordinal() ); // NumConta
				sVals[ EColBaixa.CODPLAN.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.CODPLAN.ordinal() ); // Codplan
				sVals[ EColBaixa.DOC.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.DOC.ordinal() ); // Doc
				sVals[ EColBaixa.DTEMIT.ordinal() ] = (String) txtDtEmisBaixa.getVlrString(); // Data emissão
				sVals[ EColBaixa.DTVENC.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.DTVENC.ordinal() ); // Vencimento
				sVals[ EColBaixa.VLRPARC.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRPARC.ordinal() ); // Vlrparc
				sVals[ EColBaixa.VLRDESC.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRDESC.ordinal() ); // Vlrdesc
				sVals[ EColBaixa.VLRJUROS.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRJUROS.ordinal() ); // Vlrjuros
				sVals[ EColBaixa.VLRAPAG.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRAPAG.ordinal() ); // Vlraberto
				sVals[ EColBaixa.CODCC.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.CODCC.ordinal() ); // Codcc

				if ( "".equals( ( (String) tabBaixa.getValor( iLin, 6 ) ).trim() ) ) { // Data de pagamento branco
					sVals[ EColBaixa.DTPGTO.ordinal() ] = Funcoes.dateToStrDate( new Date() ); // Data pagto
					sVals[ EColBaixa.VLRPAGO.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ); // valor pago
				}
				else {
					sVals[ EColBaixa.DTPGTO.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.DTPAGTO.ordinal() ); // Data pagto
					sVals[ EColBaixa.VLRPAGO.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ); // valor pago
				}
				if ( "".equals( ( (String) tabBaixa.getValor( iLin, 14 ) ).trim() ) ) {
					sVals[ 14 ] = "RECEBIMENTO REF. AO PED.: " + txtCodVendaBaixa.getVlrString(); // histórico
				}
				else {
					sVals[ 14 ] = (String) tabBaixa.getValor( iLin, 14 ); // histórico
				}

				dl.setValores( sVals );
				dl.setConexao( con );
				dl.setVisible( true );

				if ( dl.OK ) {
					
					sRets = dl.getValores();
					
					sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," ); 
					sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?," ); 
					sSQL.append( "VLRDESCITREC=?,VLRJUROSITREC=?,OBSITREC=?,STATUSITREC='RP' " );
					sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
					
					try {
						
						ps = con.prepareStatement( sSQL.toString() );
						ps.setString( 1, sRets[ 0 ] );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
						ps.setString( 4, sRets[ 1 ] );
						ps.setInt( 5, Aplicativo.iCodEmp );
						ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );

						if ( "".equals( sRets[ 7 ].trim() ) ) {
							ps.setNull( 7, Types.INTEGER );
							ps.setNull( 8, Types.CHAR );
							ps.setNull( 9, Types.INTEGER );
							ps.setNull( 10, Types.INTEGER );
						}
						else {
							ps.setInt( 7, iAnoCC );
							ps.setString( 8, sRets[ 7 ] );
							ps.setInt( 9, Aplicativo.iCodEmp );
							ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
						}

						ps.setString( 11, sRets[ 2 ] );
						ps.setDate( 12, Funcoes.strDateToSqlDate( sRets[ 3 ] ) );
						ps.setBigDecimal( 13, Funcoes.strCurrencyToBigDecimal( sRets[ 4 ] ) );
						ps.setBigDecimal( 14, Funcoes.strCurrencyToBigDecimal( sRets[ 5 ] ) );
						ps.setBigDecimal( 15, Funcoes.strCurrencyToBigDecimal( sRets[ 6 ] ) );
						ps.setString( 16, sRets[ 8 ] );
						ps.setInt( 17, iCodRec );
						ps.setInt( 18, iNParcItRec );
						ps.setInt( 19, Aplicativo.iCodEmp );
						ps.setInt( 20, ListaCampos.getMasterFilial( "FNRECEBER" ) );
						
						ps.executeUpdate();

						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					} catch ( SQLException err ) {
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

	private void novo() {

		DLNovoRec dl = new DLNovoRec( this );
		dl.setConexao( con );
		dl.setVisible( true );
		dl.dispose();
		carregaGridManut( bBuscaAtual );
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
							
							ps = con.prepareStatement( "DELETE FROM FNRECEBER WHERE CODREC=? AND CODEMP=? AND CODFILIAL=?" );
							ps.setInt( 1, Integer.parseInt( String.valueOf( tabManut.getValor( tabManut.getLinhaSel(), 4 ) ) ) );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
							
							ps.executeUpdate();
							
							if ( !con.getAutoCommit() ) {
								con.commit();
							}
							
							carregaGridManut( bBuscaAtual );
						} catch ( SQLException err ) {
							if ( err.getErrorCode() == 335544517 ) {
								Funcoes.mensagemErro( this, "NÃO FOI POSSIVEL EXCLUIR.\n" + "A PARCELA JÁ FOI PAGA." );
							}
							else {
								Funcoes.mensagemErro( this, "Erro ao excluir parcela!\n" + err.getMessage(), true, con, err );
							}
							err.printStackTrace();
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
			Funcoes.mensagemErro( null, "Erro ao excluir recebimento.", true, con, e );
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
				
				if ( ( imgStatusAt == imgPago ) || ( imgStatusAt == imgPagoParcial ) ) {
					
					if ( Funcoes.mensagemConfirma( this, "Confirma o estorno do lançamento?" ) == 0 ) {
						
						int iLin = tabManut.getLinhaSel();
						
						iCodRec = Integer.parseInt( (String) tabManut.getValor( iLin, 4 ) );
						iNParcItRec = Integer.parseInt( String.valueOf( tabManut.getValor( iLin, 5 ) ) );
												
						try {
							
							ps = con.prepareStatement( "UPDATE FNITRECEBER SET STATUSITREC='R1' WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
							ps.setInt( 1, iCodRec );
							ps.setInt( 2, iNParcItRec );
							ps.setInt( 3, Aplicativo.iCodEmp );
							ps.setInt( 4, ListaCampos.getMasterFilial( "FNRECEBER" ) );
							
							ps.executeUpdate();
							
							if ( !con.getAutoCommit() ) {
								con.commit();
							}
						} catch ( SQLException err ) {
							Funcoes.mensagemErro( this, "Erro ao estornar registro!\n" + err.getMessage(), true, con, err );
						}
						carregaGridManut( bBuscaAtual );
					}
				}
				else {
					Funcoes.mensagemInforma( this, "PARCELA AINDA NÃO FOI PAGA!" );
				}
			}
			else {
				Funcoes.mensagemInforma( this, "Não ha nenhum item selecionado." );
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

	private String[] buscaRelPlanPag( int iCodRec ) {

		String[] retorno = new String[ 4 ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			sSQL.append( " SELECT V.CODPLANOPAG, P.CODPLAN, P.NUMCONTA, P.CODCC" );
			sSQL.append( " FROM VDVENDA V, FNPLANOPAG P, FNRECEBER R" );
			sSQL.append( " WHERE V.CODEMPPG=P.CODEMP AND V.CODFILIALPG=P.CODFILIAL AND V.CODPLANOPAG=P.CODPLANOPAG" ); 
			sSQL.append( " AND V.CODEMP=R.CODEMPVD AND V.CODFILIAL=R.CODFILIALVD AND V.CODVENDA=R.CODVENDA AND V.TIPOVENDA=R.TIPOVENDA" ); 
			sSQL.append( " AND R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=?" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, iCodRec );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				
				for ( int i = 0; i < retorno.length; i++ ) {
					
					retorno[ i ] = rs.getString( i + 1 ) == null ? "" : rs.getString( i + 1 );
				}
			}
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar Conta!\n" + err.getMessage(), true, con, err );
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
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		return iRet;
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
		else if ( evt.getSource() == btEstorno ) {
			estorno();
		}
		else if ( evt.getSource() == btCarregaBaixas ) {
			consBaixa();
		}
		else if ( evt.getSource() == btCarregaGridManut ) {
			bBuscaAtual = true;
			carregaGridManut( bBuscaAtual );
		}
		else if ( evt.getSource() == btCarregaVenda ) {
			carregaVenda();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcRecBaixa ) {
			
			tabBaixa.limpa();
			carregaGridBaixa();
			lcBancoBaixa.carregaDados();
		}
		else if ( cevt.getListaCampos() == lcRecManut ) {
			
			bBuscaAtual = false;
			carregaGridManut( bBuscaAtual );
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( tpn.getSelectedIndex() == 0 ) {
			
			carregaConsulta();
		}
		if ( tpn.getSelectedIndex() == 1 ) {
			
			lcRecBaixa.carregaDados();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcCliBaixa.setConexao( cn );
		lcCliFiltro.setConexao( cn );
		lcCliManut.setConexao( cn );
		lcVendaBaixa.setConexao( cn );
		lcBancoBaixa.setConexao( cn );
		lcRecBaixa.setConexao( cn );
		lcRecManut.setConexao( cn );
		iAnoCC = buscaAnoBaseCC();
	}
}
