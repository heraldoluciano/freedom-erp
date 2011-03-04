/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FManutRec.java <BR>
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
 *                    Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.fnc.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.component.Banco;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.crm.view.frame.utility.FCRM;
import org.freedom.modulos.fnc.view.dialog.report.DLImpBoletoRec;
import org.freedom.modulos.fnc.view.dialog.utility.DLBaixaRec;
import org.freedom.modulos.fnc.view.dialog.utility.DLBordero;
import org.freedom.modulos.fnc.view.dialog.utility.DLConsultaBaixa;
import org.freedom.modulos.fnc.view.dialog.utility.DLEditaRec;
import org.freedom.modulos.fnc.view.dialog.utility.DLEditaRec.EColEdit;
import org.freedom.modulos.fnc.view.dialog.utility.DLEditaRec.EColRet;
import org.freedom.modulos.fnc.view.dialog.utility.DLNovoRec;
import org.freedom.modulos.fnc.view.dialog.utility.DLRenegRec;
import org.freedom.modulos.std.view.dialog.utility.DLCancItem;
import org.freedom.modulos.std.view.dialog.utility.DLConsultaVenda;

public class FManutRec extends FFilho implements ActionListener, CarregaListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	// private static final String HISTORICO_PADRAO = "RECEBIMENTO REF. AO PED.: <DOCUMENTO>";

	private enum EColTabManut {
		IMGSTATUS, STATUS, DTVENC, DTEMIT, DTPREV, CODCLI, RAZCLI, CODREC, NPARCITREC, DOCLANCA, DOCVENDA, VLRPARC, DTLIQ, DTPAGTO, VLRPAGO, VLRDESC, VLRJUROS, VLRDEVOLUCAO, VLRAPAG, VLRCANC, NUMCONTA, DESCCONTA, CODPLAN, DESCPLAN, CODCC, DESCCC, CODTIPOCOB, DESCTIPOCOB, CODBANCO, NOMEBANCO, CODCARTCOB, DESCCARTCOB, OBS, DESCPONT, SEQNOSSONUMERO
	};

	private enum EColTabBaixa {
		IMGSTATUS, DTVENC, CODREC, NPARCITREC, DOC, CODVENDA, VLRPARC, DTLIQ, DTPAGTO, VLRPAGO, VLRDESC, VLRJUROS, VLRAPAG, VLRCANC, NUMCONTA, DESCCONTA, CODPLAN, DESCPLAN, CODCC, DESCCC, OBS
	};

	private enum EColTabConsulta {
		IMGSTATUS, DTVENC, CODREC, NPARCITREC, DOC, DTVENDA, VLRPARC, VLRDESC, VLRPAGO, DTLIQ, DTPAGTO, DIASATRASO, VLRJUROS, VLRCANC, SERIE, CODVENDA, CODBANCO, NOMEBANCO, OBS, TV
	};

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private JTablePad tabBaixa = new JTablePad();

	private JScrollPane spnBaixa = new JScrollPane( tabBaixa );

	private JTablePad tabManut = new JTablePad();

	private JScrollPane spnManut = new JScrollPane( tabManut );

	private JTablePad tabConsulta = new JTablePad();

	private JScrollPane spnConsulta = new JScrollPane( tabConsulta );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL );

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

	private JPanelPad pinBotoesManut = new JPanelPad( 70, 70 );

	private JPanelPad pinManut = new JPanelPad( 800, 155 );

	private JPanelPad pnManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodCliFiltro = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjCliFiltro = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

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

	private JTextFieldPad txtCnpjCliBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtTotRecBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtJurosBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDatainiManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtTotalVencido = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalParcial = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalRecebido = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalVencer = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalCancelado = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalEmBordero = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtTotalRenegociado = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtTotalEmRenegociacao = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldFK txtDocManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtPedidoManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCodCliManut = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjCliManut = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCliManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDtEmitManut = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazCliFiltro = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazCliBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JPanelPad pinFiltroStatus = new JPanelPad( 350, 150 );

	private JButtonPad btBaixaConsulta = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btBaixaManut = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btEditManut = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btNovoManut = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btExcluirManut = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btEstorno = new JButtonPad( Icone.novo( "btCancelar.gif" ) );

	private JButtonPad btCancItem = new JButtonPad( Icone.novo( "btCancItem.png" ) );

	private JButtonPad btCarregaGridManut = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btCarregaBaixas = new JButtonPad( Icone.novo( "btConsBaixa.gif" ) );

	private JButtonPad btCarregaBaixasMan = new JButtonPad( Icone.novo( "btConsBaixa.gif" ) );

	private JButtonPad btBaixa = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btImpBol = new JButtonPad( Icone.novo( "btCodBar.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btCarregaVenda = new JButtonPad( "Consulta venda", Icone.novo( "btSaida.gif" ) );

	private JButtonPad btHistorico = new JButtonPad( Icone.novo( "btTelefone.png" ) );
	
	private JButtonPad btRenegRec = new JButtonPad( Icone.novo( "btRenegRec.png" ) );

	private JButtonPad btBordero = new JButtonPad( Icone.novo( "clPriorAlta.gif" ) );

	private JCheckBoxPad cbRecebidas = new JCheckBoxPad( "Recebidas", "S", "N" );

	private JCheckBoxPad cbAReceber = new JCheckBoxPad( "À Receber", "S", "N" );

	private JCheckBoxPad cbRecParcial = new JCheckBoxPad( "Rec. Parcial", "S", "N" );

	private JCheckBoxPad cbCanceladas = new JCheckBoxPad( "Canceladas", "S", "N" );

	private JCheckBoxPad cbEmBordero = new JCheckBoxPad( "Descontados", "S", "N" );
	
	private JCheckBoxPad cbRenegociado = new JCheckBoxPad( "Renegociados", "S", "N" );
	
	private JCheckBoxPad cbEmRenegociacao = new JCheckBoxPad( "Em Reneg.", "S", "N" );

	private JRadioGroup<?, ?> rgData = null;

	private JRadioGroup<?, ?> rgVenc = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcCliBaixa = new ListaCampos( this );

	private ListaCampos lcCliFiltro = new ListaCampos( this );

	private ListaCampos lcCliManut = new ListaCampos( this, "CL" );

	private ListaCampos lcVendaBaixa = new ListaCampos( this );

	private ListaCampos lcRecManut = new ListaCampos( this );

	private ListaCampos lcRecBaixa = new ListaCampos( this );

	private ListaCampos lcBancoBaixa = new ListaCampos( this, "BC" );

	private Vector<?> vNParcBaixa = new Vector<Object>();

	private Vector<String> vValsData = new Vector<String>();

	private Vector<String> vLabsData = new Vector<String>();

	private Vector<String> vValsVenc = new Vector<String>();

	private Vector<String> vLabsVenc = new Vector<String>();

	private Date dIniManut = null;

	private Date dFimManut = null;

	private ImageIcon imgVencido = Icone.novo( "clVencido.gif" );

	private ImageIcon imgVencido2 = Icone.novo( "clVencido2.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgPago2 = Icone.novo( "clPago2.gif" );

	private ImageIcon imgPagoParcial = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgPagoParcial2 = Icone.novo( "clPagoParcial2.gif" );

	private ImageIcon imgNaoVencido = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgNaoVencido2 = Icone.novo( "clNaoVencido2.gif" );

	private ImageIcon imgCancelado = Icone.novo( "clCancelado.gif" );

	private ImageIcon imgCancelado2 = Icone.novo( "clCancelado2.gif" );

	private ImageIcon imgBordero = Icone.novo( "clEstrela.gif" );
	
	private ImageIcon imgRenegociado = Icone.novo( "clRenegociado.png" );
	
	private ImageIcon imgEmRenegociacao = Icone.novo( "btRenegRec.png" );
	
	private ImageIcon imgRenegociadoVencido = Icone.novo( "clVencido3.gif" );
	
	private ImageIcon imgRenegociadoPago = Icone.novo( "clPago3.gif" );
	
	private ImageIcon imgRenegociadoNaoVencido = Icone.novo( "clNaoVencido3.gif" );

	private JLabelPad lbVencido = new JLabelPad( "Vencido", imgVencido, SwingConstants.LEFT );

	private JLabelPad lbParcial = new JLabelPad( "Parcial", imgPagoParcial, SwingConstants.LEFT );

	private JLabelPad lbPago = new JLabelPad( "Pago", imgPago, SwingConstants.LEFT );

	private JLabelPad lbVencer = new JLabelPad( "À vencer", imgNaoVencido, SwingConstants.LEFT );

	private JLabelPad lbCancelado = new JLabelPad( "Cancelado", imgCancelado, SwingConstants.LEFT );

	private JLabelPad lbEmBordero = new JLabelPad( "Bordero", imgBordero, SwingConstants.LEFT );
	
	private JLabelPad lbRenegociado = new JLabelPad( "Renegociados", imgRenegociado, SwingConstants.LEFT );
	
	private JLabelPad lbEmRenegociacao = new JLabelPad( "Em Reneg.", imgEmRenegociacao, SwingConstants.LEFT );

	private ImageIcon imgColuna = null;

	private boolean bBuscaAtual = true;

	private int iAnoCC = 0;

	private Map<String, Integer> prefere = null;

	public FManutRec() {

		super( false );
		setTitulo( "Manutenção de contas a receber" );
		setAtribos( 20, 20, 940, 570 );

		cbAReceber.setVlrString( "S" );

		btBaixaConsulta.setToolTipText( "Baixar" );
		btBaixaManut.setToolTipText( "Baixar" );
		btEditManut.setToolTipText( "Editar lançamento" );
		btNovoManut.setToolTipText( "Novo lançamento" );
		btExcluirManut.setToolTipText( "Excluir" );
		btEstorno.setToolTipText( "Estorno" );
		btCancItem.setToolTipText( "Cancela item" );
		btCarregaGridManut.setToolTipText( "Executar consulta" );
		btCarregaBaixas.setToolTipText( "Carrega baixas" );
		btCarregaBaixasMan.setToolTipText( "Carrega baixas" );
		btBaixa.setToolTipText( "Baixar" );
		btImpBol.setToolTipText( "Imprimir boleto" );
		btSair.setToolTipText( "Sair" );
		btCarregaVenda.setToolTipText( "Consulta venda" );
		btHistorico.setToolTipText( "Histório de contatos" );
		btBordero.setToolTipText( "Adiantamento de recebivéis" );
		btRenegRec.setToolTipText( "Renegociação de Titulos" );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( tpn, BorderLayout.CENTER );
		btSair.setPreferredSize( new Dimension( 90, 40 ) );

		pnLegenda.setPreferredSize( new Dimension( 800, 40 ) );
		pnLegenda.setLayout( null );

		lbVencido.setBounds( 5, 0, 90, 17 );
		txtTotalVencido.setBounds( 5, 18, 90, 18 );
		lbParcial.setBounds( 100, 0, 90, 17 );
		txtTotalParcial.setBounds( 100, 18, 90, 18 );
		lbPago.setBounds( 195, 0, 90, 17 );
		txtTotalRecebido.setBounds( 195, 18, 90, 18 );
		lbVencer.setBounds( 290, 0, 90, 17 );
		txtTotalVencer.setBounds( 290, 18, 90, 18 );
		lbCancelado.setBounds( 385, 0, 90, 17 );
		txtTotalCancelado.setBounds( 385, 18, 90, 18 );
		lbEmBordero.setBounds( 480, 0, 90, 17 );
		txtTotalEmBordero.setBounds( 480, 18, 90, 18 );
		lbRenegociado.setBounds( 573, 0, 90, 17 );
		txtTotalRenegociado.setBounds( 573, 18, 90, 18 );
		lbEmRenegociacao.setBounds( 665, 0, 90, 17 );
		txtTotalEmRenegociacao.setBounds( 665, 18, 90, 18 );
		
		pnLegenda.add( lbVencido );
		pnLegenda.add( txtTotalVencido );
		pnLegenda.add( lbParcial );
		pnLegenda.add( txtTotalParcial );
		pnLegenda.add( lbPago );
		pnLegenda.add( txtTotalRecebido );
		pnLegenda.add( lbVencer );
		pnLegenda.add( txtTotalVencer );
		pnLegenda.add( lbCancelado );
		pnLegenda.add( txtTotalCancelado );
		pnLegenda.add( lbEmBordero );
		pnLegenda.add( txtTotalEmBordero );
		pnLegenda.add( lbRenegociado );
		pnLegenda.add( txtTotalRenegociado );
		pnLegenda.add( lbEmRenegociacao );
		pnLegenda.add( txtTotalEmRenegociacao );

		txtTotalVencido.setSoLeitura( true );
		txtTotalParcial.setSoLeitura( true );
		txtTotalRecebido.setSoLeitura( true );
		txtTotalVencer.setSoLeitura( true );
		txtTotalCancelado.setSoLeitura( true );
		txtTotalEmBordero.setSoLeitura( true );
		txtTotalRenegociado.setSoLeitura( true );
		txtTotalEmRenegociacao.setSoLeitura( true );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 500, 42 ) );
		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnLegenda, BorderLayout.WEST );

		btSair.addActionListener( this );

		// Consulta:

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do clliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCnpjCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, null );
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
		pinConsulta.adic( txtVlrMaxAcum, 373, 60, 137, 20 );
		pinConsulta.adic( new JLabelPad( "Total de compras" ), 7, 80, 150, 20 );
		pinConsulta.adic( txtVlrTotCompr, 7, 100, 165, 20 );
		pinConsulta.adic( new JLabelPad( "Total pago" ), 175, 80, 97, 20 );
		pinConsulta.adic( txtVlrTotPago, 175, 100, 165, 20 );
		pinConsulta.adic( new JLabelPad( "Total em aberto" ), 343, 80, 117, 20 );
		pinConsulta.adic( txtVlrTotAberto, 343, 100, 167, 20 );
		pinConsulta.adic( btCarregaVenda, 575, 93, 150, 30 );

		btCarregaVenda.addActionListener( this );

		pinBotoesConsulta.adic( btBaixaConsulta, 3, 10, 30, 30 );
		tabConsulta.adicColuna( "" );
		tabConsulta.adicColuna( "Dt.vencto." );// 1
		tabConsulta.adicColuna( "Cód.rec." );// 2
		tabConsulta.adicColuna( "Nro.parc." );// 3
		tabConsulta.adicColuna( "Doc." );// 4
		tabConsulta.adicColuna( "Dt.venda." );// 5
		tabConsulta.adicColuna( "Valor parc." );// 6
		tabConsulta.adicColuna( "Desc.fin" );// 7
		tabConsulta.adicColuna( "Valor pago." );// 8
		tabConsulta.adicColuna( "Dt.liq."); // 9
		tabConsulta.adicColuna( "Dt.pagto." );// 10
		tabConsulta.adicColuna( "Atraso" );// 11
		tabConsulta.adicColuna( "Valor juros" );// 12
		tabConsulta.adicColuna( "Valor cancelado" );// 13
		tabConsulta.adicColuna( "Série" );// 14
		tabConsulta.adicColuna( "Cód.venda" );// 15
		tabConsulta.adicColuna( "Cód.banco" ); // 16
		tabConsulta.adicColuna( "Nome banco" );// 17
		tabConsulta.adicColuna( "Observações" );// 18
		tabConsulta.adicColuna( "TV" );// 19

		tabConsulta.setTamColuna( 0, EColTabConsulta.IMGSTATUS.ordinal() );// status
		tabConsulta.setTamColuna( 90, EColTabConsulta.DTVENC.ordinal() );// venc
		tabConsulta.setTamColuna( 80, EColTabConsulta.CODREC.ordinal() );// codrec
		tabConsulta.setTamColuna( 80, EColTabConsulta.NPARCITREC.ordinal() );// nparcitrec
		tabConsulta.setTamColuna( 80, EColTabConsulta.DOC.ordinal() );// doc
		tabConsulta.setTamColuna( 90, EColTabConsulta.DTVENDA.ordinal() );// data venda
		tabConsulta.setTamColuna( 100, EColTabConsulta.VLRPARC.ordinal() );// valor
		tabConsulta.setTamColuna( 100, EColTabConsulta.VLRDESC.ordinal() );// Desc.Fin
		tabConsulta.setTamColuna( 100, EColTabConsulta.VLRPAGO.ordinal() );// valor pago
		tabConsulta.setTamColuna( 120, EColTabConsulta.DTLIQ.ordinal() );// data liquidação
		tabConsulta.setTamColuna( 120, EColTabConsulta.DTPAGTO.ordinal() );// data pagamento/compensação
		tabConsulta.setTamColuna( 60, EColTabConsulta.DIASATRASO.ordinal() );// atraso
		tabConsulta.setTamColuna( 100, EColTabConsulta.VLRJUROS.ordinal() );// juros
		tabConsulta.setTamColuna( 100, EColTabConsulta.VLRCANC.ordinal() );// cancelado
		tabConsulta.setTamColuna( 50, EColTabConsulta.SERIE.ordinal() );// serie
		tabConsulta.setTamColuna( 80, EColTabConsulta.CODVENDA.ordinal() );// codvenda
		tabConsulta.setTamColuna( 50, EColTabConsulta.CODBANCO.ordinal() );// codbanco
		tabConsulta.setTamColuna( 200, EColTabConsulta.NOMEBANCO.ordinal() );// nome banco
		tabConsulta.setTamColuna( 200, EColTabConsulta.OBS.ordinal() );// observ
		tabConsulta.setTamColuna( 20, EColTabConsulta.TV.ordinal() );// Tipo venda

		// Baixa:

		lcVendaBaixa.add( new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_PK, false ) );
		lcVendaBaixa.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVendaBaixa.montaSql( false, "VENDA", "VD" );
		lcVendaBaixa.setQueryCommit( false );
		lcVendaBaixa.setReadOnly( true );
		txtCodVendaBaixa.setTabelaExterna( lcVendaBaixa, null );
		txtCodVendaBaixa.setFK( true );
		txtCodVendaBaixa.setNomeCampo( "CodVenda" );

		lcCliBaixa.add( new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli", ListaCampos.DB_PK, false ) );
		lcCliBaixa.add( new GuardaCampo( txtRazCliBaixa, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliBaixa.add( new GuardaCampo( txtCnpjCliBaixa, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		lcCliBaixa.montaSql( false, "CLIENTE", "VD" );
		lcCliBaixa.setQueryCommit( false );
		lcCliBaixa.setReadOnly( true );
		txtCodCliBaixa.setTabelaExterna( lcCliBaixa, null );
		txtCodCliBaixa.setFK( true );
		txtCodCliBaixa.setNomeCampo( "CodCli" );

		lcBancoBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBancoBaixa.add( new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBancoBaixa.montaSql( false, "BANCO", "FN" );
		lcBancoBaixa.setQueryCommit( false );
		lcBancoBaixa.setReadOnly( true );
		txtCodBancoBaixa.setTabelaExterna( lcBancoBaixa, null );
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
		txtCodRecBaixa.setTabelaExterna( lcRecBaixa, null );
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
		pinBaixa.adic( new JLabelPad( "Doc." ), 90, 0, 100, 20 );
		pinBaixa.adic( txtDoc, 90, 20, 100, 20 );
		pinBaixa.adic( new JLabelPad( "-", SwingConstants.CENTER ), 190, 20, 10, 20 );
		pinBaixa.adic( new JLabelPad( "Série" ), 200, 0, 40, 20 );
		pinBaixa.adic( txtSerie, 200, 20, 40, 20 );
		pinBaixa.adic( new JLabelPad( "Pedido" ), 243, 0, 100, 20 );
		pinBaixa.adic( txtCodVendaBaixa, 243, 20, 97, 20 );

		pinBaixa.adic( new JLabelPad( "Cód.cli." ), 7, 40, 80, 20 );
		pinBaixa.adic( txtCodCliBaixa, 7, 60, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do cliente" ), 90, 40, 250, 20 );
		pinBaixa.adic( txtRazCliBaixa, 90, 60, 250, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.banco" ), 343, 40, 80, 20 );
		pinBaixa.adic( txtCodBancoBaixa, 343, 60, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do banco" ), 426, 40, 250, 20 );
		pinBaixa.adic( txtDescBancoBaixa, 426, 60, 250, 20 );

		pinBaixa.adic( new JLabelPad( "Data de emissão" ), 7, 80, 110, 20 );
		pinBaixa.adic( txtDtEmisBaixa, 7, 100, 110, 20 );
		pinBaixa.adic( new JLabelPad( "Total a pagar" ), 120, 80, 110, 20 );
		pinBaixa.adic( txtTotRecBaixa, 120, 100, 110, 20 );
		pinBaixa.adic( new JLabelPad( "Total pago" ), 233, 80, 107, 20 );
		pinBaixa.adic( txtTotPagoBaixa, 233, 100, 107, 20 );
		pinBaixa.adic( new JLabelPad( "Total em aberto" ), 343, 80, 110, 20 );
		pinBaixa.adic( txtTotAbertoBaixa, 343, 100, 110, 20 );
		pinBaixa.adic( new JLabelPad( "Juros" ), 456, 80, 110, 20 );
		pinBaixa.adic( txtJurosBaixa, 456, 100, 110, 20 );
		pinBaixa.adic( btCarregaBaixas, 646, 90, 30, 30 );

		pinBotoesBaixa.adic( btBaixa, 3, 10, 30, 30 );

		tabBaixa.adicColuna( "" );// 0
		tabBaixa.adicColuna( "Vencimento" ); // 1
		tabBaixa.adicColuna( "Cód.rec." ); // 2
		tabBaixa.adicColuna( "Nro.parc." ); // 3
		tabBaixa.adicColuna( "Doc." ); // 4
		tabBaixa.adicColuna( "Pedido" ); // 5
		tabBaixa.adicColuna( "Valor parcela" ); // 6
		tabBaixa.adicColuna( "Data liquidação" ); // 7
		tabBaixa.adicColuna( "Data pagamento" ); // 8
		tabBaixa.adicColuna( "Valor pago" ); // 9
		tabBaixa.adicColuna( "Valor desc." ); // 10
		tabBaixa.adicColuna( "Valor juros" ); // 11
		tabBaixa.adicColuna( "Valor aberto" ); // 12
		tabBaixa.adicColuna( "Valor cancelado" ); // 13
		tabBaixa.adicColuna( "Nro.Conta" ); // 14
		tabBaixa.adicColuna( "Descrição conta" ); // 15
		tabBaixa.adicColuna( "Cód.planej." ); // 16
		tabBaixa.adicColuna( "Descrição planej." ); // 17
		tabBaixa.adicColuna( "Cód.c.c." ); // 18
		tabBaixa.adicColuna( "Descrição c.c." ); // 19
		tabBaixa.adicColuna( "Observação" ); // 20

		tabBaixa.setTamColuna( 0, EColTabBaixa.IMGSTATUS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.DTVENC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.CODREC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.NPARCITREC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.DOC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.CODVENDA.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPARC.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.DTLIQ.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.DTPAGTO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPAGO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRDESC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRJUROS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRAPAG.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRCANC.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.NUMCONTA.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCCONTA.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.CODPLAN.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCPLAN.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.CODCC.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCCC.ordinal() );
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
		txtCodRecManut.setTabelaExterna( lcRecManut, null );
		txtCodRecManut.setFK( true );
		txtCodRecManut.setNomeCampo( "CodRec" );

		txtDatainiManut.setVlrDate( new Date() );
		txtDatafimManut.setVlrDate( new Date() );

		pinManut.adic( new JLabelPad( "Periodo" ), 7, 0, 250, 20 );
		pinManut.adic( txtDatainiManut, 7, 20, 100, 20 );
		pinManut.adic( new JLabelPad( "até", SwingConstants.CENTER ), 107, 20, 30, 20 );
		pinManut.adic( txtDatafimManut, 137, 20, 100, 20 );

		pinManut.adic( new JLabelPad( "Cód.cli." ), 7, 45, 80, 20 );
		pinManut.adic( txtCodCliFiltro, 7, 65, 80, 20 );
		pinManut.adic( new JLabelPad( "Razão social do cliente" ), 90, 45, 215, 20 );
		pinManut.adic( txtRazCliFiltro, 90, 65, 215, 20 );

		vValsData.addElement( "V" );
		vValsData.addElement( "E" );
		vValsData.addElement( "P" );
		vLabsData.addElement( "Vencto." );
		vLabsData.addElement( "Emissão" );
		vLabsData.addElement( "Previsão" );

		rgData = new JRadioGroup<String, String>( 3, 1, vLabsData, vValsData );
		rgData.setVlrString( "V" );
		pinManut.adic( new JLabelPad( "Filtrar por:" ), 310, 0, 100, 20 );
		pinManut.adic( rgData, 310, 20, 100, 65 );

		vValsVenc.addElement( "VE" );
		vValsVenc.addElement( "AV" );
		vValsVenc.addElement( "TT" );

		vLabsVenc.addElement( "Vencidas" );
		vLabsVenc.addElement( "À vencer" );
		vLabsVenc.addElement( "Ambas" );

		rgVenc = new JRadioGroup<String, String>( 3, 2, vLabsVenc, vValsVenc );
		rgVenc.setVlrString( "TT" );
		pinManut.adic( new JLabelPad( "Filtrar por:" ), 415, 0, 100, 20 );
		pinManut.adic( rgVenc, 415, 20, 100, 65 );

		pinFiltroStatus.adic( cbAReceber, 5, 0, 90, 20 );
		pinFiltroStatus.adic( cbRecebidas, 5, 20, 90, 20 );
		pinFiltroStatus.adic( cbEmBordero, 5, 40, 120, 20 );
		pinFiltroStatus.adic( cbEmRenegociacao, 220, 0, 120, 20 );
		pinFiltroStatus.adic( cbRecParcial, 107, 0, 120, 20 );
		pinFiltroStatus.adic( cbCanceladas, 107, 20, 120, 20 );
		pinFiltroStatus.adic( cbRenegociado, 107, 40, 120, 20 );

		pinManut.adic( new JLabelPad( "Filtrar por:" ), 520, 0, 100, 20 );
		pinManut.adic( pinFiltroStatus, 520, 20, 330, 65 );

		pinManut.adic( btCarregaGridManut, 855, 55, 30, 30 );

		lcCliFiltro.add( new GuardaCampo( txtCodCliFiltro, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliFiltro.add( new GuardaCampo( txtRazCliFiltro, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliFiltro.add( new GuardaCampo( txtCnpjCliFiltro, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcCliFiltro.montaSql( false, "CLIENTE", "VD" );
		lcCliFiltro.setQueryCommit( false );
		lcCliFiltro.setReadOnly( true );
		txtCodCliFiltro.setTabelaExterna( lcCliFiltro, null );
		txtCodCliFiltro.setFK( true );
		txtCodCliFiltro.setNomeCampo( "CodCli" );

		lcCliManut.add( new GuardaCampo( txtCodCliManut, "CodCli", "Cód.cli", ListaCampos.DB_PK, false ) );
		lcCliManut.add( new GuardaCampo( txtRazCliManut, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliManut.add( new GuardaCampo( txtCnpjCliManut, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		lcCliManut.montaSql( false, "CLIENTE", "VD" );
		lcCliManut.setQueryCommit( false );
		lcCliManut.setReadOnly( true );
		txtCodCliManut.setTabelaExterna( lcCliManut, null );
		txtCodCliManut.setFK( true );
		txtCodCliManut.setNomeCampo( "CodCli" );

		JLabelPad separacao = new JLabelPad();
		separacao.setBorder( BorderFactory.createEtchedBorder() );
		pinManut.adic( separacao, 7, 95, 726, 2 );

		pinManut.adic( new JLabelPad( "Cod.rec." ), 7, 100, 80, 20 );
		pinManut.adic( txtCodRecManut, 7, 120, 80, 20 );
		pinManut.adic( new JLabelPad( "Doc." ), 90, 100, 77, 20 );
		pinManut.adic( txtDocManut, 90, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Pedido" ), 170, 100, 77, 20 );
		pinManut.adic( txtPedidoManut, 170, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Cód.cli." ), 250, 100, 300, 20 );
		pinManut.adic( txtCodCliManut, 250, 120, 77, 20 );
		pinManut.adic( new JLabelPad( "Razão social do cliente" ), 330, 100, 300, 20 );
		pinManut.adic( txtRazCliManut, 330, 120, 300, 20 );
		pinManut.adic( new JLabelPad( "Data emissão " ), 633, 100, 100, 20 );
		pinManut.adic( txtDtEmitManut, 633, 120, 100, 20 );

		pinBotoesManut.adic( btCarregaBaixasMan, 3,   3, 30, 30 );
		pinBotoesManut.adic( btBaixaManut, 		 3,  34, 30, 30 );
		pinBotoesManut.adic( btEditManut, 		 3,  65, 30, 30 );
		pinBotoesManut.adic( btNovoManut, 		 3,  96, 30, 30 );
		pinBotoesManut.adic( btHistorico, 		 3, 127, 30, 30 );
		pinBotoesManut.adic( btRenegRec, 		 3, 158, 30, 30 );
		
		pinBotoesManut.adic( btBordero, 		34,   3, 30, 30 );
		pinBotoesManut.adic( btEstorno, 		34,  34, 30, 30 );
		pinBotoesManut.adic( btExcluirManut, 	34,  65, 30, 30 );
		pinBotoesManut.adic( btCancItem,		34,  96, 30, 30 );
		pinBotoesManut.adic( btImpBol, 			34, 127, 30, 30 );
		

		tabManut.adicColuna( "" ); // 0
		tabManut.adicColuna( "St." ); // 1
		tabManut.adicColuna( "Vencto." ); // 2
		tabManut.adicColuna( "Emissão" ); // 3
		tabManut.adicColuna( "Previsão" ); // 4
		tabManut.adicColuna( "Cód.cli." ); // 5
		tabManut.adicColuna( "Razão social do cliente" ); // 6
		tabManut.adicColuna( "Cód.rec." ); // 7
		tabManut.adicColuna( "Parc." ); // 8
		tabManut.adicColuna( "Doc.lanca" ); // 9
		tabManut.adicColuna( "Doc.venda" ); // 10
		tabManut.adicColuna( "Valor parc." ); // 11
		tabManut.adicColuna( "Data liquidação" ); // 12
		tabManut.adicColuna( "Data pagamento" ); // 13
		tabManut.adicColuna( "Valor pago" ); // 14
		tabManut.adicColuna( "Valor desconto" ); // 15
		tabManut.adicColuna( "Valor juros" ); // 16
		tabManut.adicColuna( "Valor devolução" ); // 17
		tabManut.adicColuna( "Valor aberto" ); // 18
		tabManut.adicColuna( "Valor cancelado" ); // 19
		tabManut.adicColuna( "Nro.conta" ); // 20
		tabManut.adicColuna( "Descrição da conta" ); // 21
		tabManut.adicColuna( "Cód.categ." ); // 22
		tabManut.adicColuna( "Categoria" ); // 23
		tabManut.adicColuna( "Cód.c.c." ); // 24
		tabManut.adicColuna( "Descrição do centro de custo" ); // 25
		tabManut.adicColuna( "Cód.tp.cob" ); // 26
		tabManut.adicColuna( "Descrição do tipo de cobrança" ); // 27
		tabManut.adicColuna( "Cód.banco" ); // 28
		tabManut.adicColuna( "Nome do banco" ); // 29
		tabManut.adicColuna( "Cód.cart.cob." ); // 30
		tabManut.adicColuna( "Descrição da carteira de cobrança" ); // 31
		tabManut.adicColuna( "Observação" ); // 32
		tabManut.adicColuna( "pontualidade" ); // 33
		tabManut.adicColuna( "Seq.Nosso.Nro." ); // 34

		tabManut.setTamColuna( 0, EColTabManut.IMGSTATUS.ordinal() );
		tabManut.setColunaInvisivel( EColTabManut.STATUS.ordinal() );

		tabManut.setTamColuna( 62, EColTabManut.DTVENC.ordinal() );
		tabManut.setTamColuna( 62, EColTabManut.DTEMIT.ordinal() );
		tabManut.setTamColuna( 62, EColTabManut.DTPREV.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODCLI.ordinal() );
		tabManut.setTamColuna( 200, EColTabManut.RAZCLI.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODREC.ordinal() );
		tabManut.setTamColuna( 30, EColTabManut.NPARCITREC.ordinal() );
		tabManut.setTamColuna( 60, EColTabManut.DOCLANCA.ordinal() );
		tabManut.setTamColuna( 60, EColTabManut.DOCVENDA.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRPARC.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DTLIQ.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DTPAGTO.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRPAGO.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRDESC.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRJUROS.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRDEVOLUCAO.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRAPAG.ordinal() );
		tabManut.setTamColuna( 90, EColTabManut.VLRCANC.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.NUMCONTA.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DESCCONTA.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.CODPLAN.ordinal() );
		tabManut.setTamColuna( 100, EColTabManut.DESCPLAN.ordinal() );
		tabManut.setTamColuna( 80, EColTabManut.CODCC.ordinal() );
		tabManut.setTamColuna( 130, EColTabManut.DESCCC.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODTIPOCOB.ordinal() );
		tabManut.setTamColuna( 230, EColTabManut.DESCTIPOCOB.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODBANCO.ordinal() );
		tabManut.setTamColuna( 240, EColTabManut.NOMEBANCO.ordinal() );
		tabManut.setTamColuna( 50, EColTabManut.CODCARTCOB.ordinal() );
		tabManut.setTamColuna( 240, EColTabManut.DESCCARTCOB.ordinal() );
		tabManut.setTamColuna( 240, EColTabManut.OBS.ordinal() );
		tabManut.setTamColuna( 20, EColTabManut.DESCPONT.ordinal() );
		tabManut.setTamColuna( 20, EColTabManut.SEQNOSSONUMERO.ordinal() ); 

		lcCli.addCarregaListener( this );
		lcRecBaixa.addCarregaListener( this );
		lcRecManut.addCarregaListener( this );
		btBaixa.addActionListener( this );
		btBaixaConsulta.addActionListener( this );
		btBaixaManut.addActionListener( this );
		btEditManut.addActionListener( this );
		btNovoManut.addActionListener( this );
		btExcluirManut.addActionListener( this );
		btImpBol.addActionListener( this );
		btCarregaGridManut.addActionListener( this );
		btEstorno.addActionListener( this );
		btCancItem.addActionListener( this );
		btCarregaBaixas.addActionListener( this );
		btCarregaBaixasMan.addActionListener( this );
		btHistorico.addActionListener( this );
		btBordero.addActionListener( this );
		btRenegRec.addActionListener( this );
		tpn.addChangeListener( this );

		tabManut.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent e ) {

				if ( e.getSource() == tabManut && e.getClickCount() == 2 ) {
					visualizaRec();
				}
				else if ( e.getSource() == tabManut && e.getClickCount() == 1 ) {
					Object banco = tabManut.getValor( tabManut.getSelectedRow(), EColTabManut.CODBANCO.ordinal() );
					if ( Banco.BANCO_DO_BRASIL.equals( banco ) ) {
						btImpBol.setIcon( Icone.novo( "btBB.gif" ) );
					}
					else if ( Banco.CAIXA_ECONOMICA.equals( banco ) ) {
						btImpBol.setIcon( Icone.novo( "btCEF.gif" ) );
					}
					else if ( Banco.BRADESCO.equals( banco ) ) {
						btImpBol.setIcon( Icone.novo( "btBD.png" ) );
					}
					else {
						btImpBol.setIcon( Icone.novo( "btCodBar.gif" ) );
					}
				}
			}
		} );

	}

	private void visualizaRec() {

		DLEditaRec dl = null;
		Object[] sVals = new Object[ 18 ];
		try {
			dl = new DLEditaRec( this, false );
			sVals = getTabManutValores();
			dl.setConexao( con );
			dl.setValores( sVals );
			dl.setVisible( true );
			dl.dispose();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
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
			sSQL.append( "select sum(ir.vlritrec), sum(ir.vlrpagoitrec), sum(ir.vlrapagitrec),MIN(DATAREC),MAX(DATAREC) " );
			sSQL.append( "FROM FNRECEBER rc, fnitreceber ir " );
			sSQL.append( "where rc.codemp=ir.codemp and rc.codfilial=ir.codfilial and rc.codrec=ir.codrec and " );
			sSQL.append( "ir.CODEMP=? AND ir.CODFILIAL=? AND rc.CODEMPCL=? and rc.codfilialcl=? and CODCLI=? " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 5, txtCodCli.getVlrInteger() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtVlrTotCompr.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 1 ) ) );
				txtVlrTotPago.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 2 ) ) );
				txtVlrTotAberto.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 3 ) ) );
				txtPrimCompr.setVlrString( rs.getDate( 4 ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( 4 ) ) : "" );
				txtUltCompr.setVlrString( rs.getDate( 5 ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( 5 ) ) : "" );
			}

			rs.close();
			ps.close();

			con.commit();

			// Busca a maior fatura ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT MAX(VLRREC),DATAREC " );
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
				txtVlrMaxFat.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs1.getString( 1 ) ) );
				txtDataMaxFat.setVlrString( StringFunctions.sqlDateToStrDate( rs1.getDate( "DATAREC" ) ) );
			}

			rs1.close();
			ps1.close();

			con.commit();

			// Busca o maior acumulo ...
			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT EXTRACT(MONTH FROM DATAREC), SUM(VLRREC), EXTRACT(YEAR FROM DATAREC) " );
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
				txtVlrMaxAcum.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs2.getString( 2 ) ) );
			}

			rs2.close();
			ps2.close();

			con.commit();

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

		Float bdTotVencido = 0.0f;
		Float bdTotParcial = 0.0f;
		Float bdTotRecebido = 0.0f;
		Float bdTotVencer = 0.0f;
		Float bdTotCancelado = 0.0f;

		try {

			sSQL.append( "SELECT IT.DTVENCITREC,IT.STATUSITREC," );
			sSQL.append( "(SELECT SERIE FROM VDVENDA V " );
			sSQL.append( "WHERE V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA " );
			sSQL.append( "AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) SERIE," );
			sSQL.append( "R.DOCREC,R.CODVENDA,R.DATAREC,IT.VLRPARCITREC,IT.DTLIQITREC, IT.DTPAGOITREC,IT.VLRPAGOITREC," );
			sSQL.append( "(CASE WHEN IT.DTLIQITREC IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITREC " );
			sSQL.append( "ELSE IT.DTLIQITREC - IT.DTVENCITREC END ) DIASATRASO, R.OBSREC," );
			sSQL.append( "IT.CODBANCO, (SELECT B.NOMEBANCO FROM FNBANCO B " );
			sSQL.append( "WHERE B.CODBANCO=IT.CODBANCO AND B.CODEMP=IT.CODEMPBO AND B.CODFILIAL=IT.CODFILIALBO) NOMEBANCO," );
			sSQL.append( "R.CODREC,IT.NPARCITREC,IT.VLRDESCITREC,IT.VLRJUROSITREC,R.TIPOVENDA,IT.VLRAPAGITREC, IT.VLRCANCITREC " );
			sSQL.append( "FROM FNRECEBER R,FNITRECEBER IT " );
			sSQL.append( "WHERE R.CODEMPCL=? AND R.CODFILIALCL=? AND R.CODCLI=? AND R.CODEMP=? AND R.CODFILIAL=? AND IT.CODREC=R.CODREC " );
			sSQL.append( "AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL " );
			sSQL.append( "ORDER BY R.CODREC DESC,IT.NPARCITREC DESC" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCli.getCodFilial() );
			ps.setInt( 3, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );

			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0.0f ) {
					imgColuna = imgPago;
					bdTotRecebido += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPAGOITREC" ) ).floatValue();
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
					bdTotParcial += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPAGOITREC" ) ).floatValue();
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
					bdTotVencido += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPARCITREC" ) ).floatValue();
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
					bdTotVencer += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				}

				tabConsulta.adicLinha();
				tabConsulta.setValor( imgColuna, i, EColTabConsulta.IMGSTATUS.ordinal() );
				tabConsulta.setValor( ( rs.getDate( "DTVENCITREC" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCITREC" ) ) : "" ), i, EColTabConsulta.DTVENC.ordinal() );
				tabConsulta.setValor( rs.getInt( "CODREC" ), i, EColTabConsulta.CODREC.ordinal() );
				tabConsulta.setValor( rs.getInt( "NPARCITREC" ), i, EColTabConsulta.NPARCITREC.ordinal() );
				tabConsulta.setValor( ( rs.getString( "DOCREC" ) != null ? rs.getString( "DOCREC" ) : "" ), i, EColTabConsulta.DOC.ordinal() );
				tabConsulta.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DATAREC" ) ), i, EColTabConsulta.DTVENDA.ordinal() );
				tabConsulta.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPARCITREC" ) ), i, EColTabConsulta.VLRPARC.ordinal() );
				tabConsulta.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITREC" ) ), i, EColTabConsulta.VLRDESC.ordinal() );
				tabConsulta.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPAGOITREC" ) ), i, EColTabConsulta.VLRPAGO.ordinal() );
				tabConsulta.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRCANCITREC" ) ), i, EColTabConsulta.VLRCANC.ordinal() );
				tabConsulta.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTLIQITREC" ) ), i, EColTabConsulta.DTLIQ.ordinal() );
				tabConsulta.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTPAGOITREC" ) ), i, EColTabConsulta.DTPAGTO.ordinal() );
				tabConsulta.setValor( rs.getInt( "DIASATRASO" ), i, EColTabConsulta.DIASATRASO.ordinal() );
				tabConsulta.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRJUROSITREC" ) ), i, EColTabConsulta.VLRJUROS.ordinal() );
				tabConsulta.setValor( ( rs.getString( "SERIE" ) != null ? rs.getString( "SERIE" ) : "" ), i, EColTabConsulta.SERIE.ordinal() );
				tabConsulta.setValor( rs.getInt( "CODVENDA" ), i, EColTabConsulta.CODVENDA.ordinal() );
				tabConsulta.setValor( rs.getString( "CODBANCO" ) != null ? rs.getString( "CODBANCO" ) : "", i, EColTabConsulta.CODBANCO.ordinal() );
				tabConsulta.setValor( rs.getString( "NOMEBANCO" ) != null ? rs.getString( "NOMEBANCO" ) : "", i, EColTabConsulta.NOMEBANCO.ordinal() );
				tabConsulta.setValor( rs.getString( "OBSREC" ) != null ? rs.getString( "OBSREC" ) : "", i, EColTabConsulta.OBS.ordinal() );
				tabConsulta.setValor( rs.getString( "TIPOVENDA" ), i, EColTabConsulta.TV.ordinal() );
			}

			rs.close();
			ps.close();

			txtTotalVencido.setVlrDouble( Funcoes.arredDouble( bdTotVencido.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalParcial.setVlrDouble( Funcoes.arredDouble( bdTotParcial.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalRecebido.setVlrDouble( Funcoes.arredDouble( bdTotRecebido.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalVencer.setVlrDouble( Funcoes.arredDouble( bdTotVencer.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalCancelado.setVlrDouble( Funcoes.arredDouble( bdTotCancelado.doubleValue(), Aplicativo.casasDecFin ) );

			con.commit();
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
		float bdVlrRecebido = 0.0f;

		Float bdTotVencido = 0.0f;
		Float bdTotParcial = 0.0f;
		Float bdTotRecebido = 0.0f;
		Float bdTotVencer = 0.0f;
		Float bdTotCancelado = 0.0f;

		try {

			vNParcBaixa.clear();
			tabBaixa.limpa();

			sSQL.append( "SELECT IR.DTVENCITREC,IR.STATUSITREC,R.CODREC,IR.DOCLANCAITREC,R.DOCREC," );
			sSQL.append( "R.CODVENDA,IR.VLRPARCITREC, IR.DTLIQITREC, IR.DTPAGOITREC,IR.VLRPAGOITREC," );
			sSQL.append( "IR.VLRAPAGITREC,IR.NUMCONTA,IR.VLRDESCITREC," );
			sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
			sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
			sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA) DESCCONTA,IR.CODPLAN," );
			sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
			sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," );
			sSQL.append( "IR.CODCC," );
			sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
			sSQL.append( "WHERE CC.CODCC=IR.CODCC AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC AND CC.ANOCC=IR.ANOCC) DESCCC," );
			sSQL.append( "IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC,IR.DTITREC," );
			sSQL.append( "(SELECT V.DOCVENDA FROM VDVENDA V " );
			sSQL.append( "WHERE V.CODEMP=R.CODEMPVA " );
			sSQL.append( "AND V.CODFILIAL=R.CODFILIALVA AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) DOCVENDA," );
			sSQL.append( "IR.CODBANCO, IR.VLRCANCITREC " );
			sSQL.append( "FROM FNITRECEBER IR,FNRECEBER R  " );
			sSQL.append( "WHERE IR.CODEMP=R.CODEMP AND IR.CODFILIAL=R.CODFILIAL AND IR.CODREC=R.CODREC AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? " );
			sSQL.append( "ORDER BY IR.DTVENCITREC,IR.STATUSITREC" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodRecBaixa.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrRecebido = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( "CR".equals( rs.getString( "StatusItRec" ) ) ) {
					imgColuna = imgCancelado;
					bdTotCancelado += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRCANCITREC" ) ).floatValue();
				}
				else if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0.0f ) {
					imgColuna = imgPago;
					bdTotRecebido += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPAGOITREC" ) ).floatValue();
				}
				else if ( bdVlrRecebido > 0 ) {
					imgColuna = imgPagoParcial;
					bdTotParcial += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPAGOITREC" ) ).floatValue();
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
					bdTotVencido += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VLRPARCITREC" ) ).floatValue();
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
					bdTotVencer += Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				}

				tabBaixa.adicLinha();
				tabBaixa.setValor( imgColuna, i, EColTabBaixa.IMGSTATUS.ordinal() );
				tabBaixa.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCITREC" ) ), i, EColTabBaixa.DTVENC.ordinal() );
				tabBaixa.setValor( rs.getInt( "CODREC" ), i, EColTabBaixa.CODREC.ordinal() );
				tabBaixa.setValor( rs.getInt( "NPARCITREC" ), i, EColTabBaixa.NPARCITREC.ordinal() );

				String doc = ( (rs.getString( "DocLancaItRec" ) != null && ! "".equals( rs.getString( "DocLancaItRec" ))) ? 
								rs.getString( "DocLancaItRec" ) : 
							 ( rs.getString( "DocRec" ) != null ? rs.getString( "DocRec" ) + "/" + rs.getString( "NParcItRec" ) : "" ) );
				
				tabBaixa.setValor( doc, i, EColTabBaixa.DOC.ordinal() );
				
				tabBaixa.setValor( rs.getInt( "CODVENDA" ), i, EColTabBaixa.CODVENDA.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPARCITREC" ) ), i, EColTabBaixa.VLRPARC.ordinal() );
				tabBaixa.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTLIQITREC" ) ), i, EColTabBaixa.DTLIQ.ordinal() );
				tabBaixa.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTPAGOITREC" ) ), i, EColTabBaixa.DTPAGTO.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPAGOITREC" ) ), i, EColTabBaixa.VLRPAGO.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITREC" ) ), i, EColTabBaixa.VLRDESC.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRJUROSITREC" ) ), i, EColTabBaixa.VLRJUROS.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRAPAGITREC" ) ), i, EColTabBaixa.VLRAPAG.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRCANCITREC" ) ), i, EColTabBaixa.VLRCANC.ordinal() );
				tabBaixa.setValor( rs.getString( "NUMCONTA" ) != null ? rs.getString( "NUMCONTA" ) : "", i, EColTabBaixa.NUMCONTA.ordinal() );
				tabBaixa.setValor( rs.getString( "DESCCONTA" ) != null ? rs.getString( "DESCCONTA" ) : "", i, EColTabBaixa.DESCCONTA.ordinal() );
				tabBaixa.setValor( rs.getString( "CODCC" ) != null ? rs.getString( "CODCC" ) : "", i, EColTabBaixa.CODCC.ordinal() );
				tabBaixa.setValor( rs.getString( "DESCCC" ) != null ? rs.getString( "DESCCC" ) : "", i, EColTabBaixa.DESCCC.ordinal() );
				tabBaixa.setValor( rs.getInt( "NPARCITREC" ), i, EColTabBaixa.NPARCITREC.ordinal() );
				tabBaixa.setValor( rs.getString( "OBSITREC" ) != null ? rs.getString( "OBSITREC" ) : "", i, EColTabBaixa.OBS.ordinal() );
				
				tabBaixa.setValor( rs.getString( "CODPLAN" ), i, EColTabBaixa.CODPLAN.ordinal() );
				

				sCodBanco = rs.getString( "CODBANCO" );

			}

			txtCodBancoBaixa.setVlrString( sCodBanco );
			lcBancoBaixa.carregaDados();

			txtTotalVencido.setVlrDouble( Funcoes.arredDouble( bdTotVencido.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalParcial.setVlrDouble( Funcoes.arredDouble( bdTotParcial.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalRecebido.setVlrDouble( Funcoes.arredDouble( bdTotRecebido.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalVencer.setVlrDouble( Funcoes.arredDouble( bdTotVencer.doubleValue(), Aplicativo.casasDecFin ) );
			txtTotalCancelado.setVlrDouble( Funcoes.arredDouble( bdTotCancelado.doubleValue(), Aplicativo.casasDecFin ) );

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private ResultSet getResultSetManut( boolean bAplicFiltros ) throws SQLException {

		return getResultSetManut( bAplicFiltros, false, false );
	}

	private ResultSet getResultSetManut( boolean bAplicFiltros, boolean bordero, boolean renegociveis ) throws SQLException {

		ResultSet rs = null;

		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhereManut = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();

		if ( bAplicFiltros ) {

			if ( !validaPeriodo() ) {
				return null;
			}

			sWhereManut.append( " AND " );

			if ( "V".equals( rgData.getVlrString() ) ) {
				sWhereManut.append( "IR.DTVENCITREC" );
			}
			else if ( "E".equals( rgData.getVlrString() ) ) {
				sWhereManut.append( "IR.DTITREC" );
			}
			else {
				sWhereManut.append( "COALESCE(IR.DTPREVITREC,IR.DTVENCITREC)" );
			}

			// sWhereManut.append( rgData.getVlrString().equals( "V" ) ? "IR.DTVENCITREC" : "IR.DTITREC" );
			sWhereManut.append( " BETWEEN ? AND ? AND R.CODEMP=? AND R.CODFILIAL=?" );

			if ( "S".equals( cbRecebidas.getVlrString() ) || "S".equals( cbAReceber.getVlrString() ) || "S".equals( cbRecParcial.getVlrString() ) || 
					"S".equals( cbCanceladas.getVlrString() ) || "S".equals( cbEmBordero.getVlrString() )  || "S".equals( cbRenegociado.getVlrString() ) || 
					"S".equals( cbEmRenegociacao.getVlrString() ) ) {

				boolean bStatus = false;

				if ( "S".equals( cbRecebidas.getVlrString() ) && !renegociveis) {
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
				if ( "S".equals( cbCanceladas.getVlrString() ) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='CR'" : " IR.STATUSITREC='CR' " );
					bStatus = true;
				}
				if ( "S".equals( cbEmBordero.getVlrString() ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RB'" : " IR.STATUSITREC='RB' " );
					bStatus = true;
				}
				if ( "S".equals( cbRenegociado.getVlrString() ) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RN'" : " IR.STATUSITREC='RN' " );
					bStatus = true;
				}
				if ( "S".equals( cbEmRenegociacao.getVlrString() ) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RR'" : " IR.STATUSITREC='RR' " );
					bStatus = true;
				}

				sWhereManut.append( " AND (" );
				sWhereManut.append( sWhereStatus );
				sWhereManut.append( ")" );
			}
			else {
				Funcoes.mensagemInforma( null, "Você deve selecionar ao menos um filtro de status!" );
				return null;
			}

			if ( !"TT".equals( rgVenc.getVlrString() ) ) {

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
			if ( !"".equals( txtCodCliFiltro.getVlrString().trim() ) ) {
				sWhereManut.append( " AND R.CODCLI=" );
				sWhereManut.append( txtCodCliFiltro.getVlrString() );
			}

			if ( bordero ) {
				sWhereManut.append( " AND NOT EXISTS (SELECT B.NPARCITREC FROM FNITBORDERO B " );
				sWhereManut.append( "WHERE B.CODEMPRC=IR.CODEMP AND B.CODFILIALRC=IR.CODFILIAL AND " );
				sWhereManut.append( "B.CODREC=IR.CODREC AND B.NPARCITREC=IR.NPARCITREC) " );
			}
			
			if( renegociveis ){
				sWhereManut.append( " AND NOT EXISTS (SELECT B.NPARCITREC FROM FNITRENEGREC B " );
				sWhereManut.append( "WHERE B.CODEMPIR=IR.CODEMP AND B.CODFILIALIR=IR.CODFILIAL AND " );
				sWhereManut.append( "B.CODREC=IR.CODREC AND B.NPARCITREC=IR.NPARCITREC) " );
			}
		}
		else {
			sWhereManut.append( " AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? " );
		}

		sSQL.append( "SELECT IR.DTVENCITREC,IR.DTPREVITREC,IR.STATUSITREC,R.CODCLI,C.RAZCLI,R.CODREC,IR.DOCLANCAITREC," );
		sSQL.append( "R.CODVENDA,IR.VLRPARCITREC, IR.DTLIQITREC, IR.DTPAGOITREC,IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA," );
		sSQL.append( "IR.VLRDESCITREC,IR.CODPLAN,IR.CODCC,IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC," );
		sSQL.append( "IR.DTITREC,IR.CODBANCO,IR.CODCARTCOB, " );
		sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
		sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
		sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA) DESCCONTA," );
		sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
		sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN " );
		sSQL.append( "AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," );
		sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
		sSQL.append( "WHERE CC.CODCC=IR.CODCC " );
		sSQL.append( "AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC AND CC.ANOCC=IR.ANOCC) DESCCC," );
		sSQL.append( "(SELECT VD.DOCVENDA FROM VDVENDA VD " );
		sSQL.append( "WHERE VD.TIPOVENDA=R.TIPOVENDA AND VD.CODVENDA=R.CODVENDA AND " );
		sSQL.append( " VD.CODEMP=R.CODEMPVA AND VD.CODFILIAL=R.CODFILIALVA) DOCVENDA," );
		sSQL.append( "IR.CODTIPOCOB, " );
		sSQL.append( "(SELECT TP.DESCTIPOCOB FROM FNTIPOCOB TP " );
		sSQL.append( "WHERE TP.CODEMP=IR.CODEMPTC " );
		sSQL.append( "AND TP.CODFILIAL=IR.CODFILIALTC AND TP.CODTIPOCOB=IR.CODTIPOCOB) DESCTIPOCOB, " );
		sSQL.append( "(SELECT BO.NOMEBANCO FROM FNBANCO BO WHERE BO.CODBANCO=IR.CODBANCO " );
		sSQL.append( "AND BO.CODEMP=IR.CODEMPBO AND BO.CODFILIAL=IR.CODFILIALBO) NOMEBANCO," );
		sSQL.append( "(SELECT CB.DESCCARTCOB FROM FNCARTCOB CB WHERE CB.CODBANCO=IR.CODBANCO " );
		sSQL.append( "AND CB.CODEMP=IR.CODEMPBO AND CB.CODFILIAL=IR.CODFILIALBO AND CB.CODCARTCOB=IR.CODCARTCOB) DESCCARTCOB, " );
		sSQL.append( "R.DOCREC, IR.VLRDEVITREC, IR.DESCPONT, IR.VLRCANCITREC, IR.SEQNOSSONUMERO, " );

		sSQL.append( "(SELECT FIRST 1 ITR.CODATENDO FROM ATATENDIMENTOITREC ITR " );
		sSQL.append( "WHERE ITR.CODEMPIR=IR.CODEMP AND ITR.CODFILIALIR=IR.CODFILIAL " );
		sSQL.append( "AND ITR.CODREC=IR.CODREC AND ITR.NPARCITREC=IR.NPARCITREC ) AS ATEND " );

		sSQL.append( "FROM FNITRECEBER IR, FNRECEBER R, VDCLIENTE C " );
		sSQL.append( "WHERE IR.CODEMP=R.CODEMP AND IR.CODFILIAL=R.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sSQL.append( "C.CODCLI=R.CODCLI AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL " );
		sSQL.append( sWhereManut );
		sSQL.append( " ORDER BY IR.DTVENCITREC,IR.STATUSITREC,IR.CODREC,IR.NPARCITREC" );

		PreparedStatement ps = con.prepareStatement( sSQL.toString() );

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

		return rs;
	}

	private void carregaGridManut( boolean bAplicFiltros ) {

		try {

			ResultSet rs = getResultSetManut( bAplicFiltros );

			if ( rs == null ) {
				return;
			}

			BigDecimal bdVlrAReceber = new BigDecimal( "0.00" );
			BigDecimal bdVlrRecebido = new BigDecimal( "0.00" );
			BigDecimal bdTotVencido = new BigDecimal( "0.00" );
			BigDecimal bdTotParcial = new BigDecimal( "0.00" );
			BigDecimal bdTotRecebido = new BigDecimal( "0.00" );
			BigDecimal bdTotVencer = new BigDecimal( "0.00" );
			BigDecimal bdTotCancelado = new BigDecimal( "0.00" );
			BigDecimal bdTotBordero = new BigDecimal( "0.00" );
			BigDecimal bdTotRenegociado = new BigDecimal( "0.00" );
			BigDecimal bdTotEmRenegociacao = new BigDecimal( "0.00" );

			tabManut.limpa();

			for ( int i = 0; rs.next(); i++ ) {

				tabManut.adicLinha();

				bdVlrAReceber = rs.getBigDecimal( "VlrApagItRec" );
				bdVlrRecebido = rs.getBigDecimal( "VlrPagoItRec" );

				if ( "CR".equals( rs.getString( "StatusItRec" ) ) ) {
					imgColuna = rs.getString( "ATEND" ) == null ? imgCancelado : imgCancelado2;
					bdTotCancelado = bdTotCancelado.add( rs.getBigDecimal( "VLRCANCITREC" ) );
				}
				else if ( "RB".equals( rs.getString( "StatusItRec" ) ) ) {
					imgColuna = imgBordero;
					bdTotBordero = bdTotBordero.add( rs.getBigDecimal( "VLRPARCITREC" ) );
				}
				else if ( "RP".equals( rs.getString( "StatusItRec" ) ) && bdVlrAReceber.floatValue() == 0 ) {
					imgColuna = rs.getString( "ATEND" ) == null ? imgPago : imgPago2;
					bdTotRecebido = bdTotRecebido.add( rs.getBigDecimal( "VLRPAGOITREC" ) );
				}
				else if ( "RN".equals( rs.getString( "StatusItRec" ) ) ) {
					imgColuna = imgRenegociado;
					bdTotRenegociado = bdTotRenegociado.add( rs.getBigDecimal( "VLRPARCITREC" ) );
				}
				else if ( "RR".equals( rs.getString( "StatusItRec" ) ) ) {
					
					if ( bdVlrRecebido.floatValue() > 0 ) {
						imgColuna = rs.getString( "ATEND" ) == null ? imgPagoParcial : imgPagoParcial2;
						bdTotEmRenegociacao = bdTotEmRenegociacao.add( rs.getBigDecimal( "VLRPAGOITREC" ) );
					}
					else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgRenegociadoVencido;
						bdTotEmRenegociacao = bdTotEmRenegociacao.add( rs.getBigDecimal( "VLRPARCITREC" ) );
					}
					else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgRenegociadoNaoVencido;
						bdTotEmRenegociacao = bdTotEmRenegociacao.add( rs.getBigDecimal( "VlrApagItRec" ) );
					}
					
					//imgColuna = imgRenegociadoNaoVencido;
					//bdTotEmRenegociacao = bdTotEmRenegociacao.add( rs.getBigDecimal( "VLRPARCITREC" ) );
				}
				else if ( bdVlrRecebido.floatValue() > 0 ) {
					imgColuna = rs.getString( "ATEND" ) == null ? imgPagoParcial : imgPagoParcial2;
					bdTotParcial = bdTotParcial.add( rs.getBigDecimal( "VLRPAGOITREC" ) );
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = rs.getString( "ATEND" ) == null ? imgVencido : imgVencido2;
					bdTotVencido = bdTotVencido.add( rs.getBigDecimal( "VLRPARCITREC" ) );
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = rs.getString( "ATEND" ) == null ? imgNaoVencido : imgNaoVencido2;
					bdTotVencer = bdTotVencer.add( rs.getBigDecimal( "VlrApagItRec" ) );
				}

				tabManut.setValor( imgColuna, i, EColTabManut.IMGSTATUS.ordinal() );
				tabManut.setValor( rs.getString( "STATUSITREC" ), i, EColTabManut.STATUS.ordinal() );
				tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCITREC" ) ), i, EColTabManut.DTVENC.ordinal() );
				tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTITREC" ) ), i, EColTabManut.DTEMIT.ordinal() );

				tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTPREVITREC" ) ), i, EColTabManut.DTPREV.ordinal() );

				tabManut.setValor( rs.getInt( "CODCLI" ), i, EColTabManut.CODCLI.ordinal() );
				tabManut.setValor( rs.getString( "RAZCLI" ), i, EColTabManut.RAZCLI.ordinal() );
				tabManut.setValor( rs.getInt( "CODREC" ), i, EColTabManut.CODREC.ordinal() );
				tabManut.setValor( rs.getInt( "NPARCITREC" ), i, EColTabManut.NPARCITREC.ordinal() );
				tabManut.setValor( ( rs.getString( "DocLancaItRec" ) != null ? rs.getString( "DocLancaItRec" ) : ( rs.getString( "DocRec" ) != null ? rs.getString( "DocRec" ) + "/" + rs.getString( "NParcItRec" ) : "" ) ), i, EColTabManut.DOCLANCA.ordinal() );
				tabManut.setValor( rs.getInt( "DOCVENDA" ), i, EColTabManut.DOCVENDA.ordinal() );// DOCVENDA
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPARCITREC" ) ), i, EColTabManut.VLRPARC.ordinal() );
				tabManut.setValor( ( rs.getDate( "DTLIQITREC" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DtLiqItRec" ) ) : "" ), i, EColTabManut.DTLIQ.ordinal() );
				tabManut.setValor( ( rs.getDate( "DTPAGOITREC" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItRec" ) ) : "" ), i, EColTabManut.DTPAGTO.ordinal() );

				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPAGOITREC" ) ), i, EColTabManut.VLRPAGO.ordinal() );
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITREC" ) ), i, EColTabManut.VLRDESC.ordinal() );
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRJUROSITREC" ) ), i, EColTabManut.VLRJUROS.ordinal() );
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDEVITREC" ) ), i, EColTabManut.VLRDEVOLUCAO.ordinal() );
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRAPAGITREC" ) ), i, EColTabManut.VLRAPAG.ordinal() );
				tabManut.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRCANCITREC" ) ), i, EColTabManut.VLRCANC.ordinal() );
				
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
				tabManut.setValor( rs.getString( "CODCARTCOB" ) != null ? rs.getString( "CODCARTCOB" ) : "", i, EColTabManut.CODCARTCOB.ordinal() );// CODBANCO
				tabManut.setValor( rs.getString( "DESCCARTCOB" ) != null ? rs.getString( "DESCCARTCOB" ) : "", i, EColTabManut.DESCCARTCOB.ordinal() );// NOMEBANCO
				tabManut.setValor( rs.getString( "ObsItRec" ) != null ? rs.getString( "ObsItRec" ) : "", i, EColTabManut.OBS.ordinal() );
				tabManut.setValor( rs.getString( "DescPont" ) != null ? rs.getString( "DescPont" ) : "", i, EColTabManut.DESCPONT.ordinal() );
				tabManut.setValor( rs.getString( "SeqNossoNumero" ) != null ? rs.getString( "SeqNossoNumero" ) : 0, i, EColTabManut.SEQNOSSONUMERO.ordinal() );
				// tabManut.setValor( rs.getString( "CODREC" ) != null ? rs.getString( "CODREC" ) : "", i, EColTabManut.CODREC.ordinal() );

			}

			txtTotalVencido.setVlrBigDecimal( bdTotVencido );
			txtTotalParcial.setVlrBigDecimal( bdTotParcial );
			txtTotalRecebido.setVlrBigDecimal( bdTotRecebido );
			txtTotalVencer.setVlrBigDecimal( bdTotVencer );
			txtTotalCancelado.setVlrBigDecimal( bdTotCancelado );
			txtTotalEmBordero.setVlrBigDecimal( bdTotBordero );
			txtTotalRenegociado.setVlrBigDecimal( bdTotRenegociado );
			txtTotalEmRenegociacao.setVlrBigDecimal( bdTotEmRenegociacao );

			con.commit();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void carregaVenda() {

		int iLin = tabConsulta.getLinhaSel();
		if ( iLin < 0 ) {
			Funcoes.mensagemInforma( this, "Selecione uma parcela." );
			return;
		}

		int iCodVenda = (Integer) tabConsulta.getValor( iLin, EColTabConsulta.CODVENDA.ordinal() );

		String sTipoVenda = (String) tabConsulta.getValor( iLin, EColTabConsulta.TV.ordinal() );

		if ( iCodVenda > 0 ) {

			DLConsultaVenda dl = new DLConsultaVenda( this, con, iCodVenda, sTipoVenda );
			dl.setVisible( true );
			dl.dispose();
		}
		else {
			Funcoes.mensagemInforma( null, "Este lançamento não possui vínculo com uma venda!" );
		}
	}

	private boolean getUsaBol() {

		return true;
		/*
		 * boolean retorno = false; StringBuilder sSQL = new StringBuilder(); ResultSet rs = null; PreparedStatement ps = null;
		 * 
		 * sSQL.append( "SELECT COUNT(*) FROM fnitmodboleto IM WHERE " ); sSQL.append( "IM.CODEMP=? AND IM.CODFILIAL=? AND IM.CODBANCO IN ('001','104')" );
		 * 
		 * try {
		 * 
		 * ps = con.prepareStatement( sSQL.toString() ); ps.setInt( 1, Aplicativo.iCodEmp ); ps.setInt( 2, ListaCampos.getMasterFilial( "FNITMODBOLETO" ) ); rs = ps.executeQuery();
		 * 
		 * if( rs.next() ){ if( rs.getInt( 1 ) == 0 ){ retorno = false; }else{ retorno = true; } }
		 * 
		 * } catch ( SQLException err ) { err.printStackTrace(); Funcoes.mensagemErro( this, "Erro ao verificar utilização de boleto!\n" + err.getMessage() ); }
		 * 
		 * return retorno;
		 */
	}

	private Object[] getTabManutValores() {

		Object[] sRet = new Object[ EColEdit.values().length ];
		Integer iCodRec;
		Integer iNParcItRec;
		// ObjetoHistorico historico = null;
		Integer codhistrec = null;

		try {

			/*
			 * codhistrec = (Integer) prefere.get( "codhistrec" );
			 * 
			 * if ( codhistrec != 0 ) { historico = new ObjetoHistorico( codhistrec, con ); } else { historico = new ObjetoHistorico(); historico.setHistoricocodificado( HISTORICO_PADRAO ); }
			 */
			int iLin = tabManut.getLinhaSel();

			sRet[ EColEdit.CODCLI.ordinal() ] = (Integer) tabManut.getValor( iLin, EColTabManut.CODCLI.ordinal() );
			sRet[ EColEdit.RAZCLI.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.RAZCLI.ordinal() ) );
			sRet[ EColEdit.NUMCONTA.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.NUMCONTA.ordinal() ) );
			sRet[ EColEdit.CODPLAN.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODPLAN.ordinal() ) );
			sRet[ EColEdit.CODCC.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODCC.ordinal() ) );
			if ( "".equals( tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() ) ) ) {
				sRet[ EColEdit.DOC.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.DOCVENDA.ordinal() ) );
			}
			else {
				sRet[ EColEdit.DOC.ordinal() ] = tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() );
			}
			sRet[ EColEdit.DOC.ordinal() ] = (String) tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() );
			sRet[ EColEdit.DTEMIS.ordinal() ] = Funcoes.strDateToDate( tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() ).toString() );
			sRet[ EColEdit.DTVENC.ordinal() ] = Funcoes.strDateToDate( tabManut.getValor( iLin, EColTabManut.DTVENC.ordinal() ).toString() );
			sRet[ EColEdit.VLRJUROS.ordinal() ] = ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRJUROS.ordinal() ) );
			sRet[ EColEdit.VLRDEVOLUCAO.ordinal() ] = ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRDEVOLUCAO.ordinal() ) );
			sRet[ EColEdit.VLRDESC.ordinal() ] = ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRDESC.ordinal() ) );
			sRet[ EColEdit.VLRPARC.ordinal() ] = ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRPARC.ordinal() ) );
			sRet[ EColEdit.NPARCITREC.ordinal() ] = tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() );

			sRet[ EColEdit.CODREC.ordinal() ] = tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() );
			/*
			 * if ( "".equals( String.valueOf( tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) ).trim() ) ) { historico.setData( Funcoes.strDateToDate( tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() ).toString() ) ); historico.setDocumento( tabManut.getValor( iLin,
			 * EColTabManut.DOCVENDA.ordinal() ).toString().trim() ); historico.setPortador( tabManut.getValor( iLin, EColTabManut.RAZCLI.ordinal() ).toString().trim() ); historico.setValor( Funcoes.strToBd( tabManut.getValor( iLin, EColTabManut.VLRPARC.ordinal() ).toString() ) ); sRet[
			 * EColEdit.OBS.ordinal() ] = historico.getHistoricodecodificado(); } else { sRet[ EColEdit.OBS.ordinal() ] = tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ); }
			 */

			sRet[ EColEdit.OBS.ordinal() ] = tabManut.getValor( iLin, EColTabManut.OBS.ordinal() );

			sRet[ EColEdit.CODBANCO.ordinal() ] = tabManut.getValor( iLin, EColTabManut.CODBANCO.ordinal() );
			sRet[ EColEdit.CODTPCOB.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODTIPOCOB.ordinal() ) );
			sRet[ EColEdit.DESCTPCOB.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.DESCTIPOCOB.ordinal() ) );
			sRet[ EColEdit.CODCARTCOB.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.CODCARTCOB.ordinal() ) );
			sRet[ EColEdit.DESCCARTCOB.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.DESCCARTCOB.ordinal() ) );
			sRet[ EColEdit.DESCPONT.ordinal() ] = String.valueOf( tabManut.getValor( iLin, EColTabManut.DESCPONT.ordinal() ) );
			sRet[ EColEdit.DTPREV.ordinal() ] = Funcoes.strDateToDate( tabManut.getValor( iLin, EColTabManut.DTPREV.ordinal() ).toString() );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return sRet;
	}

	private String[] getPlanejamentoConta( int iCodRec ) {

		String[] retorno = new String[ 4 ];

		try {

			StringBuffer sSQL = new StringBuffer();
			sSQL.append( " SELECT V.CODPLANOPAG, P.CODPLAN, P.NUMCONTA, P.CODCC" );
			sSQL.append( " FROM VDVENDA V, FNPLANOPAG P, FNRECEBER R" );
			sSQL.append( " WHERE V.CODEMPPG=P.CODEMP AND V.CODFILIALPG=P.CODFILIAL AND V.CODPLANOPAG=P.CODPLANOPAG" );
			sSQL.append( " AND V.CODEMP=R.CODEMPVD AND V.CODFILIAL=R.CODFILIALVD AND V.CODVENDA=R.CODVENDA AND V.TIPOVENDA=R.TIPOVENDA" );
			sSQL.append( " AND R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=?" );

			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, iCodRec );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {

				for ( int i = 0; i < retorno.length; i++ ) {

					retorno[ i ] = rs.getString( i + 1 ) == null ? "" : rs.getString( i + 1 );
				}
			}

			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar Conta!\n" + err.getMessage(), true, con, err );
		}

		return retorno;
	}

	private Map<String, Integer> getPrefere() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer anocc = null;
		Integer codhistrec = null;

		Map<String, Integer> retorno = new HashMap<String, Integer>();

		try {

			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO,CODHISTREC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				anocc = rs.getInt( "ANOCENTROCUSTO" );
				codhistrec = rs.getInt( "CODHISTREC" );
			}

			retorno.put( "codhistrec", codhistrec );
			retorno.put( "anocc", anocc );

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		return retorno;
	}

	private void cancelaItem() {

		String sit = "";
		int sel = tabManut.getSelectedRow();
		int codrec = 0;
		int nparcitrec = 0;
		if ( sel < 0 ) {
			Funcoes.mensagemInforma( this, "Selecione um título!" );
		}
		else {
			sit = tabManut.getValor( sel, EColTabManut.STATUS.ordinal() ).toString();
			if ( "R1".equals( sit ) ) {
				if ( Funcoes.mensagemConfirma( this, "Confirma cancelamento do título?" ) == JOptionPane.YES_OPTION ) {
					DLCancItem dlCanc = new DLCancItem( this );
					dlCanc.setVisible( true );
					if ( dlCanc.OK ) {
						codrec = ( (Integer) tabManut.getValor( sel, EColTabManut.CODREC.ordinal() ) ).intValue();
						nparcitrec = ( (Integer) tabManut.getValor( sel, EColTabManut.NPARCITREC.ordinal() ) ).intValue();
						execCancItem( codrec, nparcitrec, dlCanc.getValor() );
						carregaGridManut( bBuscaAtual );
					}
				}
			}
			else if ( "CR".equals( sit ) ) {
				Funcoes.mensagemInforma( this, "Título já está cancelado!" );
			}
			else {
				Funcoes.mensagemInforma( this, "Situação do título não permite cancelamento!" );
			}
		}
	}

	private void execCancItem( int codrec, int nparcitrec, String obs ) {

		StringBuilder sql = new StringBuilder( "UPDATE FNITRECEBER SET STATUSITREC='CR', OBSITREC=? " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=? " );
		try {
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setString( 1, obs );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 4, codrec );
			ps.setInt( 5, nparcitrec );
			ps.executeUpdate();
			ps.close();
			con.commit();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Não foi possível efetuar o cancelamento!\n" + e.getMessage() );
		}

	}

	private void novo() {

		DLNovoRec dl = new DLNovoRec( this );
		dl.setConexao( con );
		dl.setVisible( true );
		dl.dispose();
		carregaGridManut( bBuscaAtual );
	}

	private void editar() {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		Object[] sVals = new Object[ 18 ];
		Object[] oRets = null;
		DLEditaRec dl = null;
		ImageIcon imgStatusAt = null;
		BigDecimal vlrPago = new BigDecimal( 0 );
		int iCodRec = 0;
		int iNParcItRec = 0;

		try {
			int iLin = tabManut.getLinhaSel();

			if ( tabManut.getLinhaSel() > -1 ) {

				imgStatusAt = (ImageIcon) tabManut.getValor( iLin, EColTabManut.IMGSTATUS.ordinal() );
				vlrPago = ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRPAGO.ordinal() ) );

				if ( imgStatusAt != imgPago || !vlrPago.equals( new BigDecimal( 0 ) ) ) {

					iCodRec = (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() );
					iNParcItRec = (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() );

					dl = new DLEditaRec( this, true );

					sVals = getTabManutValores();

					dl.setConexao( con );
					dl.setValores( sVals );
					dl.setVisible( true );

					if ( dl.OK ) {

						oRets = dl.getValores();

						sSQL.append( "UPDATE FNITRECEBER SET " );
						sSQL.append( "NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
						sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,VLRJUROSITREC=?,VLRDEVITREC=?," );
						sSQL.append( "VLRDESCITREC=?,DTVENCITREC=?,OBSITREC=?,CODEMPBO=?,CODFILIALBO=?,CODBANCO=?," );
						sSQL.append( "CODEMPTC=?,CODFILIALTC=?,CODTIPOCOB=?," );
						sSQL.append( "CODEMPCB=?,CODFILIALCB=?,CODCARTCOB=?, DESCPONT=?, DTPREVITREC=?, VLRPARCITREC=? " );
						sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );

						try {
							ps = con.prepareStatement( sSQL.toString() );

							if ( "".equals( oRets[ EColRet.NUMCONTA.ordinal() ] ) ) {
								ps.setNull( 1, Types.CHAR );
								ps.setNull( 2, Types.INTEGER );
								ps.setNull( 3, Types.INTEGER );
							}
							else {
								ps.setString( 1, (String) oRets[ EColRet.NUMCONTA.ordinal() ] );
								ps.setInt( 2, Aplicativo.iCodEmp );
								ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
							}

							if ( "".equals( String.valueOf( oRets[ EColRet.CODPLAN.ordinal() ] ).trim() ) ) {
								ps.setNull( 4, Types.CHAR );
								ps.setNull( 5, Types.INTEGER );
								ps.setNull( 6, Types.INTEGER );
							}
							else {
								ps.setString( 4, (String) oRets[ EColRet.CODPLAN.ordinal() ] );
								ps.setInt( 5, Aplicativo.iCodEmp );
								ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
							}

							if ( "".equals( String.valueOf( oRets[ EColRet.CODCC.ordinal() ] ).trim() ) ) {
								ps.setNull( 7, Types.INTEGER );
								ps.setNull( 8, Types.CHAR );
								ps.setNull( 9, Types.INTEGER );
								ps.setNull( 10, Types.INTEGER );
							}
							else {
								ps.setInt( 7, iAnoCC );
								ps.setString( 8, (String) oRets[ EColRet.CODCC.ordinal() ] );
								ps.setInt( 9, Aplicativo.iCodEmp );
								ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
							}

							if ( "".equals( String.valueOf( oRets[ EColRet.DOC.ordinal() ] ).trim() ) ) {
								ps.setNull( 11, Types.CHAR );
							}
							else {
								ps.setString( 11, (String) oRets[ EColRet.DOC.ordinal() ] );
							}

							if ( "".equals( oRets[ EColRet.VLRJUROS.ordinal() ] ) ) {
								ps.setNull( 12, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 12, (BigDecimal) oRets[ EColRet.VLRJUROS.ordinal() ] );
							}

							if ( "".equals( oRets[ EColRet.VLRDEVOLUCAO.ordinal() ] ) ) {
								ps.setNull( 13, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 13, (BigDecimal) oRets[ EColRet.VLRDEVOLUCAO.ordinal() ] );
							}

							if ( "".equals( oRets[ EColRet.VLRDESC.ordinal() ] ) ) {
								ps.setNull( 14, Types.DECIMAL );
							}
							else {
								ps.setBigDecimal( 14, (BigDecimal) ( oRets[ EColRet.VLRDESC.ordinal() ] ) );
							}

							if ( "".equals( oRets[ EColRet.DTVENC.ordinal() ] ) ) {
								ps.setNull( 15, Types.DECIMAL );
							}
							else {
								ps.setDate( 15, Funcoes.dateToSQLDate( (java.util.Date) oRets[ EColRet.DTVENC.ordinal() ] ) );
							}

							if ( "".equals( oRets[ EColRet.OBS.ordinal() ] ) ) {
								ps.setNull( 16, Types.CHAR );
							}
							else {
								ps.setString( 16, (String) oRets[ EColRet.OBS.ordinal() ] );
							}

							if ( "".equals( oRets[ EColRet.CODBANCO.ordinal() ] ) ) {
								ps.setNull( 17, Types.INTEGER );
								ps.setNull( 18, Types.INTEGER );
								ps.setNull( 19, Types.CHAR );
							}
							else {
								ps.setInt( 17, Aplicativo.iCodEmp );
								ps.setInt( 18, ListaCampos.getMasterFilial( "FNBANCO" ) );
								ps.setString( 19, (String) oRets[ EColRet.CODBANCO.ordinal() ] );
							}

							if ( "".equals( oRets[ EColRet.CODTPCOB.ordinal() ] ) ) {
								ps.setNull( 20, Types.INTEGER );
								ps.setNull( 21, Types.INTEGER );
								ps.setNull( 22, Types.INTEGER );
							}
							else {
								ps.setInt( 20, Aplicativo.iCodEmp );
								ps.setInt( 21, ListaCampos.getMasterFilial( "FNTIPOCOB" ) );
								ps.setInt( 22, Integer.parseInt( (String) oRets[ EColRet.CODTPCOB.ordinal() ] ) );
							}

							if ( "".equals( oRets[ EColRet.CODCARTCOB.ordinal() ] ) ) {
								ps.setNull( 23, Types.INTEGER );
								ps.setNull( 24, Types.INTEGER );
								ps.setNull( 25, Types.CHAR );
							}
							else {
								ps.setInt( 23, Aplicativo.iCodEmp );
								ps.setInt( 24, ListaCampos.getMasterFilial( "FNCARTCOB" ) );
								ps.setString( 25, ( (String) oRets[ EColRet.CODCARTCOB.ordinal() ] ) );
							}
							if ( "".equals( oRets[ EColRet.DESCPONT.ordinal() ] ) ) {
								ps.setNull( 26, Types.CHAR );
							}
							else {
								ps.setString( 26, ( (String) oRets[ EColRet.DESCPONT.ordinal() ] ) );
							}
							if ( oRets[ EColRet.DTPREV.ordinal() ] == null || "".equals( oRets[ EColRet.DTPREV.ordinal() ] ) ) {
								ps.setNull( 27, Types.DECIMAL );
							}
							else {
								ps.setDate( 27, Funcoes.dateToSQLDate( (java.util.Date) oRets[ EColRet.DTPREV.ordinal() ] ) );
							}
							
							ps.setBigDecimal( 28, (BigDecimal) ( oRets[ EColRet.VLRPARC.ordinal() ] ) );

							ps.setInt( 29, iCodRec );
							ps.setInt( 30, iNParcItRec );
							ps.setInt( 31, Aplicativo.iCodEmp );
							ps.setInt( 32, ListaCampos.getMasterFilial( "FNRECEBER" ) );

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
			oRets = null;
			dl = null;
			imgStatusAt = null;
		}
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
							ps.setInt( 1, (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.CODREC.ordinal() ) );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );

							ps.executeUpdate();

							con.commit();

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
		int iCodRec = 0;
		int iNParcItRec = 0;

		try {

			if ( tabManut.getLinhaSel() > -1 ) {

				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );

				if ( ( imgStatusAt == imgPago ) || ( imgStatusAt == imgPagoParcial ) || ( imgStatusAt == imgPago2 ) || ( imgStatusAt == imgPagoParcial2 ) ) {

					if ( Funcoes.mensagemConfirma( this, "Confirma o estorno do lançamento?" ) == 0 ) {

						int iLin = tabManut.getLinhaSel();

						iCodRec = (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() );
						iNParcItRec = (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() );

						try {

							ps = con.prepareStatement( "UPDATE FNITRECEBER SET STATUSITREC='R1', DTPAGOITREC=null, DTLIQITREC=null WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
							ps.setInt( 1, iCodRec );
							ps.setInt( 2, iNParcItRec );
							ps.setInt( 3, Aplicativo.iCodEmp );
							ps.setInt( 4, ListaCampos.getMasterFilial( "FNRECEBER" ) );

							ps.executeUpdate();

							con.commit();
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

	private void consBaixa( int codRec, int nparcItRec, BigDecimal vlrParc, BigDecimal vlrPago, BigDecimal vlrDesc, BigDecimal vlrJuros, BigDecimal vlrApag ) {

		DLConsultaBaixa dl = new DLConsultaBaixa( this, con, codRec, nparcItRec );

		dl.setValores( new BigDecimal[] { vlrParc, vlrPago, vlrDesc, vlrJuros, vlrApag } );

		dl.setVisible( true );
		dl.dispose();
	}

	private void baixaConsulta() {

		if ( tabConsulta.getLinhaSel() != -1 ) {

			txtCodRecBaixa.setVlrInteger( (Integer) tabConsulta.getValor( tabConsulta.getLinhaSel(), EColTabConsulta.CODREC.ordinal() ) );

			lcRecBaixa.carregaDados();
			tpn.setSelectedIndex( 1 );
			btBaixa.requestFocus();

		}
	}

	private void baixar( char cOrig ) {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		DLBaixaRec dl = null;
		ImageIcon imgStatusAt = null;
		int iCodRec = 0;
		int iNParcItRec = 0;
		// ObjetoHistorico historico = null;
		Integer codhistrec = null;

		try {

			codhistrec = (Integer) prefere.get( "codhistrec" );

			/*
			 * if ( codhistrec != 0 ) { historico = new ObjetoHistorico( codhistrec, con ); } else { historico = new ObjetoHistorico(); historico.setHistoricocodificado( HISTORICO_PADRAO ); }
			 */
			if ( 'M' == cOrig && tabManut.getLinhaSel() > -1 ) {

				imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.IMGSTATUS.ordinal() );

				if ( imgStatusAt == imgPago ) {

					Funcoes.mensagemInforma( this, "Parcela já foi baixada!" );
					return;
				}
				
				if ( imgStatusAt == imgRenegociado ) {

					Funcoes.mensagemInforma( this, "Parcela renegociada!" );
					return;
				}

				int iLin = tabManut.getLinhaSel();

				if ( iLin < 0 ) {
					Funcoes.mensagemInforma( this, "Selecionde uma parcela." );
					return;
				}

				iCodRec = Integer.parseInt( tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() ).toString() );
				iNParcItRec = Integer.parseInt( tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() ).toString() );

				String[] sPlanoConta = getPlanejamentoConta( iCodRec );

				String numconta = (String) tabManut.getValor( iLin, EColTabManut.NUMCONTA.ordinal() );
				String codplan = (String) tabManut.getValor( iLin, EColTabManut.CODPLAN.ordinal() );
				String codcc = (String) tabManut.getValor( iLin, EColTabManut.CODCC.ordinal() );

				if ( "".equals( numconta ) || numconta == null ) {
					numconta = sPlanoConta[ 2 ];
				}

				if ( numconta == null ) {
					numconta = "";
				}

				if ( "".equals( codplan ) || codplan == null ) {
					codplan = sPlanoConta[ 1 ];
				}

				if ( codplan == null ) {
					codplan = "";
				}

				if ( "".equals( codcc ) || codcc == null ) {
					codcc = sPlanoConta[ 3 ];
				}

				if ( codcc == null ) {
					codcc = "";
				}

				dl = new DLBaixaRec( this );
				DLBaixaRec.BaixaRecBean baixaRecBean = dl.new BaixaRecBean();

				baixaRecBean.setRecebimento( (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() ) );
				baixaRecBean.setParcela( (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() ) );
				baixaRecBean.setCliente( (Integer) tabManut.getValor( iLin, EColTabManut.CODCLI.ordinal() ) );
				baixaRecBean.setRazaoSocialCliente( (String) tabManut.getValor( iLin, EColTabManut.RAZCLI.ordinal() ) );

				baixaRecBean.setConta( numconta );
				baixaRecBean.setPlanejamento( codplan );
				baixaRecBean.setCentroCusto( codcc );

				baixaRecBean.setDocumento( "".equals( tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() ) ) ? String.valueOf( tabManut.getValor( iLin, EColTabManut.DOCVENDA.ordinal() ) ) : String.valueOf( tabManut.getValor( iLin, EColTabManut.DOCLANCA.ordinal() ) ) );
				baixaRecBean.setDataEmissao( Funcoes.strDateToDate( (String) tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() ) ) );
				baixaRecBean.setDataVencimento( Funcoes.strDateToDate( (String) tabManut.getValor( iLin, EColTabManut.DTVENC.ordinal() ) ) );
				baixaRecBean.setValorParcela( ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRPARC.ordinal() ) ) );
				baixaRecBean.setValorAPagar( ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRAPAG.ordinal() ) ) );

				baixaRecBean.setValorDesconto( ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRDESC.ordinal() ) ) );
				baixaRecBean.setValorJuros( ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRJUROS.ordinal() ) ) );

				baixaRecBean.setValorPagoParc( ConversionFunctions.stringToBigDecimal( tabManut.getValor( iLin, EColTabManut.VLRPAGO.ordinal() ) ) );

				if ( "".equals( tabManut.getValor( iLin, EColTabManut.DTPAGTO.ordinal() ) ) ) {
					baixaRecBean.setDataPagamento( new Date() );
					baixaRecBean.setValorPago( new BigDecimal( 0 ) );
					// baixaRecBean.setValorPago( Funcoes.strToBd( tabManut.getValor( iLin, EColTabManut.VLRPAGO.ordinal() ) ) );
				}
				else {
					baixaRecBean.setDataPagamento( Funcoes.strDateToDate( (String) tabManut.getValor( iLin, EColTabManut.DTPAGTO.ordinal() ) ) );
					baixaRecBean.setValorPago( new BigDecimal( 0 ) );
					// baixaRecBean.setValorPago( Funcoes.strToBd( tabManut.getValor( iLin, EColTabManut.VLRPAGO.ordinal() ) ) );
				}
				/*
				 * if ( "".equals( tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) ) ) { historico.setData( Funcoes.strDateToDate( tabManut.getValor( iLin, EColTabManut.DTEMIT.ordinal() ).toString() ) ); historico.setDocumento( tabManut.getValor( iLin, EColTabManut.DOCVENDA.ordinal()
				 * ).toString().trim() ); historico.setPortador( tabManut.getValor( iLin, EColTabManut.RAZCLI.ordinal() ).toString().trim() ); historico.setValor( Funcoes.strToBd( tabManut.getValor( iLin, EColTabManut.VLRPARC.ordinal() ).toString() ) ); historico.setHistoricoant( (String)
				 * tabManut.getValor( iLin, EColTabManut.OBS.ordinal() )) ; baixaRecBean.setObservacao( historico.getHistoricodecodificado() ); } else { baixaRecBean.setObservacao( (String) tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) ); }
				 */

				baixaRecBean.setObservacao( (String) tabManut.getValor( iLin, EColTabManut.OBS.ordinal() ) );

				baixaRecBean.setEmBordero( "RB".equals( tabManut.getValor( iLin, EColTabManut.STATUS.ordinal() ) ) );

				dl.setConexao( con );
				dl.setValores( baixaRecBean );
				dl.setVisible( true );

				if ( dl.OK ) {

					baixaRecBean = dl.getValores();

					sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
					sSQL.append( "DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?,VLRDESCITREC=?,VLRJUROSITREC=?,ANOCC=?," );
					sSQL.append( "CODCC=?,CODEMPCC=?,CODFILIALCC=?,OBSITREC=?,STATUSITREC='RP' " );
					sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );

					try {

						ps = con.prepareStatement( sSQL.toString() );
						ps.setString( 1, baixaRecBean.getConta() );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
						ps.setString( 4, baixaRecBean.getPlanejamento() );
						ps.setInt( 5, Aplicativo.iCodEmp );
						ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
						ps.setString( 7, baixaRecBean.getDocumento() );
						ps.setDate( 8, Funcoes.dateToSQLDate( baixaRecBean.getDataPagamento() ) );
						ps.setBigDecimal( 9, baixaRecBean.getValorPago() );
						ps.setBigDecimal( 10, baixaRecBean.getValorDesconto() );
						ps.setBigDecimal( 11, baixaRecBean.getValorJuros() );
						if ( baixaRecBean.getCentroCusto() == null || "".equals( baixaRecBean.getCentroCusto().trim() ) ) {
							ps.setNull( 12, Types.INTEGER );
							ps.setNull( 13, Types.CHAR );
							ps.setNull( 14, Types.INTEGER );
							ps.setNull( 15, Types.INTEGER );
						}
						else {
							ps.setInt( 12, iAnoCC );
							ps.setString( 13, baixaRecBean.getCentroCusto() );
							ps.setInt( 14, Aplicativo.iCodEmp );
							ps.setInt( 15, ListaCampos.getMasterFilial( "FNCC" ) );
						}

						ps.setString( 16, baixaRecBean.getObservacao() );
						ps.setInt( 17, iCodRec );
						ps.setInt( 18, iNParcItRec );
						ps.setInt( 19, Aplicativo.iCodEmp );
						ps.setInt( 20, ListaCampos.getMasterFilial( "FNRECEBER" ) );

						ps.executeUpdate();

						con.commit();
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
					}
				}

				dl.dispose();
				carregaGridManut( bBuscaAtual );
			}
			else if ( cOrig == 'B' && tabBaixa.getLinhaSel() > -1 ) {

				imgStatusAt = (ImageIcon) tabBaixa.getValor( tabBaixa.getLinhaSel(), 0 );

				if ( imgStatusAt == imgPago ) {
					Funcoes.mensagemInforma( this, "Parcela já foi baixada!" );
					return;
				}

				int iLin = tabBaixa.getLinhaSel();

				iCodRec = (Integer) tabBaixa.getValor( iLin, EColTabBaixa.CODREC.ordinal() );
				iNParcItRec = (Integer) tabBaixa.getValor( iLin, EColTabBaixa.NPARCITREC.ordinal() );

				dl = new DLBaixaRec( this );

				DLBaixaRec.BaixaRecBean baixaRecBean = dl.new BaixaRecBean();

				baixaRecBean.setRecebimento( (Integer) tabManut.getValor( iLin, EColTabManut.CODREC.ordinal() ) );
				baixaRecBean.setParcela( (Integer) tabManut.getValor( iLin, EColTabManut.NPARCITREC.ordinal() ) );
				baixaRecBean.setCliente( txtCodCliBaixa.getVlrInteger() );
				baixaRecBean.setRazaoSocialCliente( txtRazCliBaixa.getVlrString() );
				baixaRecBean.setConta( (String) tabBaixa.getValor( iLin, EColTabBaixa.NUMCONTA.ordinal() ) );
				baixaRecBean.setPlanejamento( (String) tabBaixa.getValor( iLin, EColTabBaixa.CODPLAN.ordinal() ) );
				baixaRecBean.setDocumento( (String) tabBaixa.getValor( iLin, EColTabBaixa.DOC.ordinal() ) );
				baixaRecBean.setDataEmissao( txtDtEmisBaixa.getVlrDate() );
				baixaRecBean.setDataVencimento( Funcoes.strDateToDate( (String) tabBaixa.getValor( iLin, EColTabBaixa.DTVENC.ordinal() ) ) );
				baixaRecBean.setValorParcela( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRPARC.ordinal() ) ) );
				baixaRecBean.setValorAPagar( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRAPAG.ordinal() ) ) );
				baixaRecBean.setValorDesconto( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRDESC.ordinal() ) ) );
				baixaRecBean.setValorJuros( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRJUROS.ordinal() ) ) );
				baixaRecBean.setCentroCusto( (String) tabBaixa.getValor( iLin, EColTabBaixa.CODCC.ordinal() ) );

				if ( "".equals( tabBaixa.getValor( iLin, EColTabBaixa.DTPAGTO.ordinal() ) ) ) {
					baixaRecBean.setDataPagamento( new Date() );
					baixaRecBean.setValorPago( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ) ) );
				}
				else {
					baixaRecBean.setDataPagamento( Funcoes.strDateToDate( (String) tabBaixa.getValor( iLin, EColTabBaixa.DTPAGTO.ordinal() ) ) );
					baixaRecBean.setValorPago( ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ) ) );
				}
				/*
				 * if ( "".equals( ( (String) tabBaixa.getValor( iLin, EColTabBaixa.OBS.ordinal() ) ).trim() ) ) { historico.setData( txtDtEmisBaixa.getVlrDate() ); historico.setDocumento( txtCodVendaBaixa.getVlrString() ); historico.setPortador( txtRazCliBaixa.getVlrString().trim() );
				 * historico.setValor( Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRPARC.ordinal() ).toString() ) ); baixaRecBean.setObservacao( historico.getHistoricodecodificado() ); } else { baixaRecBean.setObservacao( (String) tabBaixa.getValor( iLin, EColTabBaixa.OBS.ordinal() ) );
				 * }
				 */

				baixaRecBean.setObservacao( (String) tabBaixa.getValor( iLin, EColTabBaixa.OBS.ordinal() ) );
				dl.setConexao( con );
				dl.setValores( baixaRecBean );
				dl.setVisible( true );

				if ( dl.OK ) {

					baixaRecBean = dl.getValores();

					sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
					sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?," );
					sSQL.append( "VLRDESCITREC=?,VLRJUROSITREC=?,OBSITREC=?,STATUSITREC='RP' " );
					sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );

					try {

						ps = con.prepareStatement( sSQL.toString() );
						ps.setString( 1, baixaRecBean.getConta() );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
						ps.setString( 4, baixaRecBean.getPlanejamento() );
						ps.setInt( 5, Aplicativo.iCodEmp );
						ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );

						if ( baixaRecBean.getCentroCusto() == null || "".equals( baixaRecBean.getCentroCusto().trim() ) ) {
							ps.setNull( 7, Types.INTEGER );
							ps.setNull( 8, Types.CHAR );
							ps.setNull( 9, Types.INTEGER );
							ps.setNull( 10, Types.INTEGER );
						}
						else {
							ps.setInt( 7, iAnoCC );
							ps.setString( 8, baixaRecBean.getCentroCusto() );
							ps.setInt( 9, Aplicativo.iCodEmp );
							ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
						}

						ps.setString( 11, baixaRecBean.getDocumento() );
						ps.setDate( 12, Funcoes.dateToSQLDate( baixaRecBean.getDataPagamento() ) );
						ps.setBigDecimal( 13, baixaRecBean.getValorPago() );
						ps.setBigDecimal( 14, baixaRecBean.getValorDesconto() );
						ps.setBigDecimal( 15, baixaRecBean.getValorJuros() );
						ps.setString( 16, baixaRecBean.getObservacao() );
						ps.setInt( 17, iCodRec );
						ps.setInt( 18, iNParcItRec );
						ps.setInt( 19, Aplicativo.iCodEmp );
						ps.setInt( 20, ListaCampos.getMasterFilial( "FNRECEBER" ) );

						ps.executeUpdate();

						con.commit();
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
					}
				}

				dl.dispose();
				carregaGridBaixa();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
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

	private void impBoleto() {

		DLImpBoletoRec dl = null;

		if ( tabManut.getLinhaSel() < 0 ) {
			Funcoes.mensagemInforma( this, "Selecione uma parcela no grid!" );
			return;
		}

		dl = new DLImpBoletoRec( this, con, (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.CODREC.ordinal() ), (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.NPARCITREC.ordinal() ) );

		dl.setVisible( true );

		if ( dl.OK ) {

			dl.imprimir();
		}
	}

	private void abreHistorico() {

		try {

			if ( tabManut.getLinhaSel() < 0 ) {
				Funcoes.mensagemInforma( this, "Selecione uma parcela!" );
				return;
			}

			FCRM tela = null;

			if ( fPrim.temTela( "Atendimento" ) ) {
				tela = (FCRM) fPrim.getTela( "org.freedom.modulos.crm.FAtendimento" );
				if ( tela != null ) {
					tela.show();
				}
			}
			else {
				Integer codcli = ( (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.CODCLI.ordinal() ) );
				Integer codrec = ( (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.CODREC.ordinal() ) );
				Integer nparcitrec = ( (Integer) tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.NPARCITREC.ordinal() ) );
				tela = new FCRM( codcli, codrec, nparcitrec, true );
				fPrim.criatela( "Atendimento", tela, con );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void abreBordero() {

		try {

			DLBordero bordero = new DLBordero();
			List<DLBordero.GridBordero> gridBordero = new ArrayList<DLBordero.GridBordero>();

			ResultSet rs = getResultSetManut( true, true, false );
			DLBordero.GridBordero grid = null;

			while ( rs.next() ) {

				grid = bordero.new GridBordero();
				grid.setStatus( rs.getString( "STATUSITREC" ) );
				grid.setDataVencimento( Funcoes.sqlDateToDate( rs.getDate( "DTVENCITREC" ) ) );
				grid.setCodigoReceber( rs.getInt( "CODREC" ) );
				grid.setParcela( rs.getInt( "NPARCITREC" ) );
				grid.setDocumentoLancamento( rs.getString( "DOCLANCAITREC" ) != null ? rs.getString( "DOCLANCAITREC" ) : ( rs.getString( "DOCREC" ) != null ? rs.getString( "DOCREC" ) + "/" + rs.getString( "NPARCITREC" ) : "" ) );
				grid.setCodigoCliente( rs.getInt( "CODCLI" ) );
				grid.setRazaoCliente( rs.getString( "RAZCLI" ) );
				grid.setDocumentoVenda( rs.getString( "DOCVENDA" ) );
				grid.setValorParcela( rs.getBigDecimal( "VLRPARCITREC" ) );
				grid.setDataPagamento( Funcoes.sqlDateToDate( rs.getDate( "DTPAGOITREC" ) ) );
				grid.setValorPago( rs.getBigDecimal( "VLRPAGOITREC" ) );
				grid.setValorDesconto( rs.getBigDecimal( "VLRDESCITREC" ) );
				grid.setValorJuros( rs.getBigDecimal( "VLRJUROSITREC" ) );
				grid.setValorAReceber( rs.getBigDecimal( "VLRAPAGITREC" ) );
				grid.setConta( rs.getString( "NUMCONTA" ) );
				grid.setDescricaoConta( rs.getString( "DESCCONTA" ) );
				grid.setPlanejamento( rs.getString( "CODPLAN" ) );
				grid.setDescricaoPlanejamento( rs.getString( "DESCPLAN" ) );
				grid.setBanco( rs.getString( "CODBANCO" ) );
				grid.setNomeBanco( rs.getString( "NOMEBANCO" ) );
				grid.setObservacao( rs.getString( "OBSITREC" ) );

				gridBordero.add( grid );
			}

			con.commit();

			bordero.setConexao( con );
			bordero.carregaGrid( gridBordero );

			bordero.setVisible( true );
			bordero.dispose();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void abreRenegocReceb() {

		try {

			DLRenegRec renegociacao = new DLRenegRec();
			List<DLRenegRec.GridRenegRec> gridRenegociacao = new ArrayList<DLRenegRec.GridRenegRec>();

			ResultSet rs = getResultSetManut( true, false, true );
			DLRenegRec.GridRenegRec grid = null;

			while ( rs.next() ) {

				grid = renegociacao.new GridRenegRec();
				
				grid.setStatus( rs.getString( "STATUSITREC" ) );
				grid.setDataVencimento( Funcoes.sqlDateToDate( rs.getDate( "DTVENCITREC" ) ) );
				grid.setCodigoReceber( rs.getInt( "CODREC" ) );
				grid.setParcela( rs.getInt( "NPARCITREC" ) );
				grid.setDocumentoLancamento( rs.getString( "DOCLANCAITREC" ) != null ? rs.getString( "DOCLANCAITREC" ) : ( rs.getString( "DOCREC" ) != null ? rs.getString( "DOCREC" ) + "/" + rs.getString( "NPARCITREC" ) : "" ) );
				grid.setCodigoCliente( rs.getInt( "CODCLI" ) );
				grid.setRazaoCliente( rs.getString( "RAZCLI" ) );
				grid.setDocumentoVenda( rs.getString( "DOCVENDA" ) );
				grid.setValorParcela( rs.getBigDecimal( "VLRPARCITREC" ) );
				grid.setDataPagamento( Funcoes.sqlDateToDate( rs.getDate( "DTPAGOITREC" ) ) );
				grid.setValorPago( rs.getBigDecimal( "VLRPAGOITREC" ) );
				grid.setValorDesconto( rs.getBigDecimal( "VLRDESCITREC" ) );
				grid.setValorJuros( rs.getBigDecimal( "VLRJUROSITREC" ) );
				grid.setValorAReceber( rs.getBigDecimal( "VLRAPAGITREC" ) );
				grid.setConta( rs.getString( "NUMCONTA" ) );
				grid.setDescricaoConta( rs.getString( "DESCCONTA" ) );
				grid.setPlanejamento( rs.getString( "CODPLAN" ) );
				grid.setDescricaoPlanejamento( rs.getString( "DESCPLAN" ) );
				grid.setBanco( rs.getString( "CODBANCO" ) );
				grid.setNomeBanco( rs.getString( "NOMEBANCO" ) );
				grid.setObservacao( rs.getString( "OBSITREC" ) );

				gridRenegociacao.add( grid );
			}

			con.commit();

			renegociacao.setConexao( con );
			renegociacao.carregaGrid( gridRenegociacao );
			renegociacao.txtCodCli.setVlrInteger( txtCodCliFiltro.getVlrInteger() );

			renegociacao.setVisible( true );
			renegociacao.dispose();
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void setRec( int codigoRecebimento ) {

		txtCodRecManut.setVlrInteger( codigoRecebimento );
		lcRecManut.carregaDados();
		tpn.setSelectedIndex( 2 );
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
		else if ( evt.getSource() == btCancItem ) {
			cancelaItem();
		}
		else if ( evt.getSource() == btCarregaBaixas ) {
			if ( tabBaixa.getLinhaSel() > -1 ) {
				consBaixa( txtCodRecBaixa.getVlrInteger().intValue(), Integer.parseInt( tabBaixa.getValor( tabBaixa.getLinhaSel(), 3 ).toString() ), ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( tabBaixa.getLinhaSel(), EColTabBaixa.VLRPARC.ordinal() ) ), ConversionFunctions
						.stringToBigDecimal( tabBaixa.getValor( tabBaixa.getLinhaSel(), EColTabBaixa.VLRPAGO.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( tabBaixa.getLinhaSel(), EColTabBaixa.VLRDESC.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabBaixa
						.getValor( tabBaixa.getLinhaSel(), EColTabBaixa.VLRJUROS.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabBaixa.getValor( tabBaixa.getLinhaSel(), EColTabBaixa.VLRAPAG.ordinal() ) ) );
			}
			else {
				Funcoes.mensagemInforma( this, "Selecione um título no grid!" );
			}
		}
		else if ( evt.getSource() == btCarregaBaixasMan ) {
			consBaixa( Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.CODREC.ordinal() ).toString() ), Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.NPARCITREC.ordinal() ).toString() ), ConversionFunctions.stringToBigDecimal( tabManut.getValor(
					tabManut.getLinhaSel(), EColTabManut.VLRPARC.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.VLRPAGO.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabManut.getValor( tabManut.getLinhaSel(),
					EColTabManut.VLRDESC.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.VLRJUROS.ordinal() ) ), ConversionFunctions.stringToBigDecimal( tabManut.getValor( tabManut.getLinhaSel(), EColTabManut.VLRAPAG.ordinal() ) ) );
		}
		else if ( evt.getSource() == btCarregaGridManut ) {
			bBuscaAtual = true;
			carregaGridManut( bBuscaAtual );
		}
		else if ( evt.getSource() == btCarregaVenda ) {
			carregaVenda();
		}
		else if ( evt.getSource() == btImpBol ) {
			impBoleto();
		}
		else if ( evt.getSource() == btHistorico ) {
			abreHistorico();
		}
		else if ( evt.getSource() == btBordero ) {
			abreBordero();
		}
		else if ( evt.getSource() == btRenegRec ) {
			if(txtCodCliFiltro.getText().trim().equals( "" ) ){
				Funcoes.mensagemInforma( this, "Selecione um cliente para renegocição." );
				return;
			}
			abreRenegocReceb();
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
		else if ( cevt.getListaCampos() == lcCli ) {

			carregaConsulta();
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

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcCliBaixa.setConexao( cn );
		lcCliFiltro.setConexao( cn );
		lcCliManut.setConexao( cn );
		lcVendaBaixa.setConexao( cn );
		lcBancoBaixa.setConexao( cn );
		lcRecBaixa.setConexao( cn );
		lcRecManut.setConexao( cn );

		prefere = getPrefere();

		iAnoCC = (Integer) prefere.get( "anocc" );

		btImpBol.setEnabled( getUsaBol() );
	}
}
