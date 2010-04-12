/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FVenda.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.Lucratividade;
import org.freedom.library.business.component.NFSaida;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.component.Layout;
import org.freedom.library.component.Leiaute;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPassword;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.gms.DLLote;
import org.freedom.modulos.nfe.database.jdbc.NFEConnectionFactory;
import org.freedom.modulos.std.DLBuscaCompra;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.view.dialog.report.DLRPedido;
import org.freedom.modulos.std.view.dialog.utility2.DLAdicOrc;
import org.freedom.modulos.std.view.dialog.utility2.DLAltComisVend;
import org.freedom.modulos.std.view.dialog.utility2.DLAltFatLucro;
import org.freedom.modulos.std.view.dialog.utility2.DLBuscaProd;
import org.freedom.modulos.std.view.dialog.utility2.DLBuscaRemessa;
import org.freedom.modulos.std.view.dialog.utility2.DLConsultaPgto;
import org.freedom.modulos.std.view.dialog.utility2.DLFechaVenda;
import org.freedom.modulos.std.view.dialog.utility2.DLMultiComiss;
import org.freedom.modulos.std.view.frame.crud.comum.FLiberaCredito;
import org.freedom.modulos.std.view.frame.report.FRBoleto;
import org.freedom.modulos.util.CtrlMultiComis;


public class FVenda extends FVD implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener, DeleteListener, KeyListener  {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JScrollPane spnCabComis = null;

	private JPanelPad pinCabVenda = new JPanelPad();

	private JPanelPad pinCabComis = null;

	private JPanelPad pinCabFiscal = new JPanelPad();
	
	private JPanelPad pinCabLucratividade = new JPanelPad();
	
	private JPanelPad pnLucrGeral = new JPanelPad();
	
	private JPanelPad pnLucrItem = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JPanelPad pinTot = new JPanelPad( 200, 200 );

	private JPanelPad pnTot = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JButtonPad btObs = new JButtonPad( Icone.novo( "btObs.gif" ) );

	private JButtonPad btFechaVenda = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btComiss = new JButtonPad( "Multi Comiss.", Icone.novo( "btMultiComis.gif" ) );
	
	private JButtonPad btConsPgto = new JButtonPad( Icone.novo( "btConsPgto.gif" ) );

	private JButtonPad btBuscaOrc = new JButtonPad( "Busca Orçamento", Icone.novo( "btVenda2.gif" ) );

	private JButtonPad btAltComis = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodRegrComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtDocVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTratTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtESTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtDtEmitVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaidaVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomeCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtPedCliVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescClComis = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtPercComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );

	private JTextFieldPad txtCodItVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtQtdItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtVerifProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtPrecoItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtPercDescItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );

	private JTextFieldPad txtVlrDescItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrComisItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtPercComItVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );

	private JTextFieldPad txtCodNat = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	
	private JTextFieldFK txtDiasAvisoLote = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtPercICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );

	private JTextFieldPad txtVlrICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrLiqItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtEstCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtClasComis = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodMens = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodFiscIf = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodItFisc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtTipoFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtTipoST = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtTpRedIcmsFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtVlrFreteVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	
	private JTextFieldPad txtMargemVlAgr = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );	

	private JTextFieldPad txtVlrComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtMedComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, casasDecFin );

	private JTextFieldPad txtVlrICMSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrIPIVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrPisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrCofinsVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrIRVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrCSocialVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrBaseICMSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrBaseISSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrISSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrProdVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrDescVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrProdItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtBaseIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtStrDescItVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtBaseICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	
	private JTextFieldPad txtBaseICMSBrutItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtAliqFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, casasDecFin );

	private JTextFieldPad txtAliqIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );

	private JTextFieldPad txtVlrIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );

	private JTextFieldPad txtVlrBrutVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtStatusVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtOrigFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodEmpLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFilialLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodLog = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtTipoFrete = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldFK txtTipoProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtDescTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescProdAux = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescNat = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtFiscalTipoMov1 = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtFiscalTipoMov2 = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAlmoxItVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtUltCamp = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtCodEmpIf = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodFilialIf = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtCodFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModNota = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtTipoModNota = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtTipoVendaRemessa = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodVendaRemessa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodItVendaRemessa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextAreaPad txaObsItVenda = new JTextAreaPad( 500 );

	private JCheckBoxPad chbImpPedTipoMov = new JCheckBoxPad( "Imp.ped.", "S", "N" );

	private JCheckBoxPad chbImpNfTipoMov = new JCheckBoxPad( "Imp.NF", "S", "N" );

	private JCheckBoxPad chbImpBolTipoMov = new JCheckBoxPad( "Imp.bol.?", "S", "N" );

	private JCheckBoxPad chbImpRecTipoMov = new JCheckBoxPad( "Imp.rec.?", "S", "N" );

	private JCheckBoxPad chbReImpNfTipoMov = new JCheckBoxPad( "Reimp.NF?", "S", "N" );

	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

	private JCheckBoxPad cbIPIimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbIPIcalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbPISimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbPIScalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbConfisimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbConfiscalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbContribimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbContribcalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbIRimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbIRcalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbISSimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbISScalc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbICMSimp = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbICMScalc = new JCheckBoxPad( "", "S", "N" );

	private JLabelPad lbStatus = new JLabelPad();

	private ListaCampos lcTratTrib = new ListaCampos( this, "TT" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcModNota = new ListaCampos( this, "MN" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcSerie = new ListaCampos( this, "SE" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private ListaCampos lcProd2 = new ListaCampos( this, "PD" );

	private ListaCampos lcNat = new ListaCampos( this, "NT" );

	private ListaCampos lcLote = new ListaCampos( this, "LE" );

	private ListaCampos lcClComis = new ListaCampos( this, "CM" );

	private ListaCampos lcFisc = new ListaCampos( this );

	private ListaCampos lcVenda2 = new ListaCampos( this );

	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );
	
	private ListaCampos lcItCompra = new ListaCampos( this, "CP" );
	
	private ListaCampos lcItRemessa = new ListaCampos( this, "VR" );

	private CtrlMultiComis ctrlmc = null;

	private int numComissionados = 0;

	private Object[] oPrefs = null;

	private boolean bCtrl = false;

	private String sOrdNota = "";

	private int iCodCliAnt = 0;

	private int codregrcomis = 0;
	
	private String classped = "";
	
	private HashMap<String, Object> permusu = null;
	
	private JTextFieldFK txtTotFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCusto = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );
	
	private JTextFieldFK txtTotLucro = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );
	
	private JProgressBar pbLucrTotal = new JProgressBar();
	
	private JProgressBar pbLucrItem = new JProgressBar();
	
	private JTextFieldFK txtItemFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtItemCusto = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );
	
	private JTextFieldFK txtItemLucro = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private NFEConnectionFactory nfecf = null; 
	
	private BigDecimal fatLucro = null;

	private enum POS_PREFS {
		USAREFPROD, USAPEDSEQ, USALIQREL, TIPOPRECOCUSTO, USACLASCOMIS, TRAVATMNFVD, 
		NATVENDA, BLOQVENDA, VENDAMATPRIM, DESCCOMPPED, TAMDESCPROD, OBSCLIVEND,
		IPIVENDA, CONTESTOQ, DIASPEDT, RECALCCPVENDA, USALAYOUTPED, ICMSVENDA,
		USAPRECOZERO, MULTICOMIS, CONS_CRED_ITEM, CONS_CRED_FECHA, TIPOCLASPED, VENDAIMOBILIZADO, 
		VISUALIZALUCR, INFCPDEVOLUCAO, INFVDREMESSA, TIPOCUSTO
	}

	public FVenda() {

		setTitulo( "Venda" );
		setAtribos( 15, 10, 775, 460 );

	}

	// Função criada para montar a tela conforme a preferência do usuário:
	// com ou sem Referência de PK;
	private void montaTela() {

		oPrefs = prefs(); // Carrega as preferências

		if ( (Boolean) oPrefs[ POS_PREFS.MULTICOMIS.ordinal() ] ) {
			numComissionados = getNumComissionados();		
		}

		pnCliCab.add( tpnCab );

		pinCabVenda.setFirstFocus( txtCodVenda );
		tpnCab.addTab( "Venda", pinCabVenda );

		pinCabComis = new JPanelPad( 750, 70 );

		pinCabComis.adic( btComiss, 560, 50, 140, 30 );

		tpnCab.addTab( "Comissão", pinCabComis );
		// }


		
		btBuscaOrc.setPreferredSize( new Dimension( 170, 0 ) );
		pnNavCab.add( btBuscaOrc, BorderLayout.EAST );

		pnMaster.remove( 2 ); // Remove o JPanelPad predefinido da class FDados
		pnGImp.removeAll(); // Remove os botões de impressão para adicionar logo embaixo
		pnGImp.setLayout( new GridLayout( 1, 4 ) ); // redimensiona o painel de impressão
		pnGImp.setPreferredSize( new Dimension( 280, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );
		pnGImp.add( btFechaVenda );
		pnGImp.add( btConsPgto );
		pnGImp.add( btObs );// Agora o painel está maior

		pnTot.setPreferredSize( new Dimension( 110, 200 ) ); // JPanelPad de Totais
		pnTot.add( pinTot );
		pnCenter.add( pnTot, BorderLayout.EAST );
		pnCenter.add( spTab, BorderLayout.CENTER );

		JPanelPad pnLab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnLab.add( new JLabelPad( " Totais:" ) ); // Label do painel de totais

		pnMaster.add( pnCenter, BorderLayout.CENTER );

		// FK Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtDescCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		txtNomeCli.setSize( 197, 20 );
		lcCli.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comis.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEstCli, "UfCli", "UF", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );

		// FK Vendedor
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.Venda", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( txtCodClComis, "CodClComis", "Cód.c.comis.", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( txtPercComisVenda, "PercComVend", "% Comis.", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( cbAtivo, "AtivoComis", "Ativo", ListaCampos.DB_SI, false ) );
		lcVendedor.setWhereAdic( "ATIVOCOMIS='S'" );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );

		// FK Plano de Pagamento
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('V','A')" );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		// FK Série
		lcSerie.add( new GuardaCampo( txtCodSerie, "Serie", "Série", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDocVenda, "DocSerie", "Doc. atual", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtCodSerie.setTabelaExterna( lcSerie );

		// FK de Lotes
		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLote, false ) );
		lcLote.add( new GuardaCampo( txtDescLote, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtDiasAvisoLote, "DiasAvisoLote", "Dias Aviso", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N AND (VENCTOLOTE >= #D OR #S IN('DV','PE'))", txtCodProd );
		lcLote.setDinWhereAdic( "", txtDtSaidaVenda );
		lcLote.setDinWhereAdic( "", txtTipoMov );
		lcLote.montaSql( false, "LOTE", "EQ" );
		lcLote.setQueryCommit( false );
		lcLote.setReadOnly( true );
		txtCodLote.setTabelaExterna( lcLote );
		txtDescLote.setListaCampos( lcLote );
		txtDescLote.setNomeCampo( "VenctoLote" );
		txtDescLote.setLabel( "Vencimento" );

		// FK de Classificação Fiscal (É acionada também quando o listaCampos de produtos é acionado)

		lcFisc.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_PK, txtDescFisc, false ) );
		lcFisc.add( new GuardaCampo( txtDescFisc, "DescFisc", "Descrição fiscal", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtAliqIPIFisc, "AliqIPIFisc", "% IPI", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtAliqFisc, "AliqFisc", "% ICMS", ListaCampos.DB_SI, false ) );
		lcFisc.montaSql( false, "CLFISCAL", "LF" );
		lcFisc.setQueryCommit( false );
		lcFisc.setReadOnly( true );
		txtCodFisc.setTabelaExterna( lcFisc );
		txtDescFisc.setListaCampos( lcFisc );

		// FK de Natureza de Operação (É acionada também quando o listaCampos de Classificação Fiscal é acionado)

		lcNat.add( new GuardaCampo( txtCodNat, "CodNat", "CFOP", ListaCampos.DB_PK, false ) );
		lcNat.add( new GuardaCampo( txtDescNat, "DescNat", "Descrição da CFOP", ListaCampos.DB_SI, false ) );
		lcNat.montaSql( false, "NATOPER", "LF" );
		lcNat.setQueryCommit( false );
		lcNat.setReadOnly( true );
		txtCodNat.setTabelaExterna( lcNat );
		txtDescNat.setListaCampos( lcNat );

		// FK de Almoxarifado

		lcAlmox.add( new GuardaCampo( txtCodAlmoxItVenda, "codalmox", "Cod.Almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );
		txtCodAlmoxItVenda.setTabelaExterna( lcAlmox );

		// FK de Tratamento Tributário (É acionada também quando o listaCampos de Tratamento tributário é acionado)

		lcTratTrib.add( new GuardaCampo( txtCodTratTrib, "CodTratTrib", "Cód.tr.trib.", ListaCampos.DB_PK, false ) );
		lcTratTrib.montaSql( false, "TRATTRIB", "LF" );
		lcTratTrib.setQueryCommit( false );
		lcTratTrib.setReadOnly( true );
		txtCodTratTrib.setTabelaExterna( lcTratTrib );

		// ListaCampos de Totais (É acionada pelo listaCampos de Venda)

		lcVenda2.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
//		lcVenda2.add(new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.Venda",ListaCampos.DB_PK, false));
		lcVenda2.add( new GuardaCampo( txtVlrFreteVenda, "VlrFreteVenda", "Vlr. frete", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrComisVenda, "VlrComisVenda", "Vlr. comis.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtMedComisVenda, "PercMComisVenda", "Med. comis.", ListaCampos.DB_SI, false ) );

		lcVenda2.add( new GuardaCampo( txtVlrBaseICMSVenda, "VlrBaseICMSVenda", "Vlr. base ICMS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrICMSVenda, "VlrICMSVenda", "Vlr. ICMS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrIPIVenda, "VlrIPIVenda", "Vlr. IPI", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrPisVenda, "VlrPisVenda", "Vlr. PIS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrCofinsVenda, "VlrCofinsVenda", "Vlr. COFINS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrIRVenda, "VlrIRVenda", "Vlr. I.R.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrCSocialVenda, "VlrCSocialVenda", "Vlr. c.social.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrProdVenda, "VlrProdVenda", "Vlr. prod.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrDescVenda, "VlrDescItVenda", "Vlr. desc.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "Vlr. liq.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrBrutVenda, "VlrProdVenda", "Vlr. prod.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrBaseISSVenda, "VlrBaseISSVenda", "Vlr. base ISS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrISSVenda, "VlrISSVenda", "Vlr. ISS", ListaCampos.DB_SI, false ) );
		lcVenda2.setWhereAdic( "TIPOVENDA='V'" );
		lcVenda2.montaSql( false, "VENDA", "VD" );
		lcVenda2.setQueryCommit( false );
		lcVenda2.setReadOnly( true );

		// lc para trazer classificacao da comissao

		lcClComis.add( new GuardaCampo( txtCodClComis, "CodClComis", "Cód.c.comis.", ListaCampos.DB_PK, false ) );
		lcClComis.add( new GuardaCampo( txtDescClComis, "DescClComis", "Descrição da classificação da comissão", ListaCampos.DB_SI, false ) );
		lcClComis.montaSql( false, "CLCOMIS", "VD" );
		lcClComis.setQueryCommit( false );
		lcClComis.setReadOnly( true );
		txtCodClComis.setTabelaExterna( lcClComis );


		// lc para vinculo de ítem de venda com ítem de compra

		lcItCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "Cód.Compra", ListaCampos.DB_PK, false ) );
		lcItCompra.add( new GuardaCampo( txtCodItCompra, "CodItCompra", "Cód.It.Compra", ListaCampos.DB_PK, false ) );
		lcItCompra.montaSql( false, "ITCOMPRA", "CP" );
		lcItCompra.setQueryCommit( false );
		lcItCompra.setReadOnly( true );
		txtCodCompra.setTabelaExterna( lcItCompra );
		txtCodItCompra.setTabelaExterna( lcItCompra );

		// lc para vinculo de ítem de venda com nota de remessa

		lcItRemessa.add( new GuardaCampo( txtCodVendaRemessa, "CodVendaVR", "Cód.venda", ListaCampos.DB_PK, false ) );
		lcItRemessa.add( new GuardaCampo( txtCodItVendaRemessa, "CodItVendaVR", "Cód.it.venda", ListaCampos.DB_PK, false ) );
		lcItRemessa.add( new GuardaCampo( txtTipoVendaRemessa, "TipoVendaVR", "Tipo venda", ListaCampos.DB_PK, false ) );
		lcItRemessa.montaSql( false, "ITVENDA", "VD" );
		lcItRemessa.setQueryCommit( false );
		lcItRemessa.setReadOnly( true );
		txtCodVendaRemessa.setTabelaExterna( lcItRemessa );
		txtCodItVendaRemessa.setTabelaExterna( lcItRemessa );
		txtTipoVendaRemessa.setTabelaExterna( lcItRemessa );
		
		// Coloca os comentrio nos botões

		btFechaVenda.setToolTipText( "Fechar a venda (F4)" );
		btConsPgto.setToolTipText( "Consulta pagamentos (F5)" );
		btObs.setToolTipText( "Observações (Ctrl + O)" );

		// Desativa as os TextFields para que os usuários não possam mexer

		txtCodSerie.setAtivo( false );
		txtDocVenda.setAtivo( false );
		txtVlrFreteVenda.setAtivo( false );
		txtVlrComisVenda.setAtivo( false );
		txtMedComisVenda.setAtivo( false );
		txtVlrICMSVenda.setAtivo( false );
		txtVlrIPIVenda.setAtivo( false );
		txtVlrPisVenda.setAtivo( false );
		txtVlrCofinsVenda.setAtivo( false );
		txtVlrIRVenda.setAtivo( false );
		txtVlrCSocialVenda.setAtivo( false );
		txtVlrBaseICMSVenda.setAtivo( false );
		txtVlrProdVenda.setAtivo( false );
		txtVlrDescVenda.setAtivo( false );
		txtVlrLiqVenda.setAtivo( false );
		txtVlrBaseISSVenda.setAtivo( false );
		txtVlrISSVenda.setAtivo( false );

		// Adiciona os Listeners

		txtDescProd.setToolTipText( "Clique aqui duas vezes para alterar a descrição." );
		txtDescProd.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getClickCount() == 2 ) {
					mostraTelaDecricao( txaObsItVenda, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString() );
				}
			}
		} );

		btFechaVenda.addActionListener( this );
		btConsPgto.addActionListener( this );
		btObs.addActionListener( this );
		btBuscaOrc.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btAltComis.addActionListener( this );
		btComiss.addActionListener( this );		

		txtPercDescItVenda.addFocusListener( this );
		txtVlrDescItVenda.addFocusListener( this );
		txtPercComItVenda.addFocusListener( this );
		txtVlrComisItVenda.addFocusListener( this );
		txtVlrProdItVenda.addFocusListener( this );
		txtQtdItVenda.addFocusListener( this );
		txtCodNat.addFocusListener( this );
		txtPrecoItVenda.addFocusListener( this );
		txtPercICMSItVenda.addFocusListener( this );
		txtAliqIPIItVenda.addFocusListener( this );
		txtCodCli.addFocusListener( this );

		lcCampos.addCarregaListener( this );
		lcVendedor.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		lcFisc.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		lcProd2.addCarregaListener( this );
		lcNat.addCarregaListener( this );
		lcVenda2.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcPlanoPag.addCarregaListener( this );
		lcTipoMov.addCarregaListener( this );

		lcCampos.addPostListener( this );
		lcDet.addPostListener( this );

		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );

		lcDet.addDeleteListener( this );

		this.addKeyListener( this );
		
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setVisible( false );

		setImprimir( true );

		txtVlrLiqItVenda.setAtivo( false );

		txtCodNat.setAtivo( (Boolean) oPrefs[ POS_PREFS.NATVENDA.ordinal() ] );

		txtAliqIPIItVenda.setAtivo( (Boolean) oPrefs[ POS_PREFS.IPIVENDA.ordinal() ] );
		txtVlrIPIItVenda.setAtivo( (Boolean) oPrefs[ POS_PREFS.IPIVENDA.ordinal() ] );

		// Desativa as os TextFields para que os usuários não possam mexer
		// ALTERADO PARA BUSCA DO PREEFERENCIAS.
		txtBaseICMSItVenda.setAtivo( (Boolean) oPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );
		txtPercICMSItVenda.setAtivo( (Boolean) oPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );
		txtVlrICMSItVenda.setAtivo( (Boolean) oPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );

		// FK Produto

		// pra definir o tamanho na tela de pesquisa.
		txtDescProdAux.setSize( 150, 20 );

		lcProd.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produtos", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProdAux, "DescAuxProd", "Descrição auxiliar do produtos", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_FK, false ) );
		lcProd.add( new GuardaCampo( txtPercComItVenda, "ComisProd", "% Comis.", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtVerifProd, "VerifProd", "Verif. custo", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtTipoProd, "TipoProd", "Tipo.Prod", ListaCampos.DB_SI, false ) );

		String sWhereAdicProd = "ATIVOPROD='S' AND TIPOPROD IN ('P','S','F', 'O' " + ( (Boolean) oPrefs[ POS_PREFS.VENDAMATPRIM.ordinal() ] ? ",'M'" : "" ) + ")";

		lcProd.setWhereAdic( sWhereAdicProd );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );

		// FK do produto (*Somente em caso de referências este listaCampos
		// Trabalha como gatilho para o listaCampos de produtos, assim
		// carregando o código do produto que será armazenado no Banco)
		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_PK, false ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtDescProdAux, "DescAuxProd", "Descrição auxiliar do produtos", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_FK, false ) );
		lcProd2.add( new GuardaCampo( txtPercComItVenda, "ComisProd", "% comis.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtVerifProd, "VerifProd", "Verif. custo", ListaCampos.DB_SI, false ) );
	
		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setListaCampos( lcDet );
		lcProd2.setWhereAdic( sWhereAdicProd );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2 );

		// FK Tipo de movimentos
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodSerie, "Serie", "Série", ListaCampos.DB_FK, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodModNota, "CodModNota", "Mod.nota", ListaCampos.DB_FK, false ) );
		lcTipoMov.add( new GuardaCampo( txtTipoMov, "TipoMov", "Tipo mov.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtESTipoMov, "ESTipoMov", "E/S", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpPedTipoMov, "ImpPedTipoMov", "Imp.ped.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpNfTipoMov, "ImpNfTipoMov", "Imp.NF", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpBolTipoMov, "ImpBolTipoMov", "Imp.bol.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpRecTipoMov, "ImpRecTipoMov", "Imp.rec.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbReImpNfTipoMov, "ReImpNfTipoMov", "Reimp.NF", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodRegrComis, "CodRegrComis", "Cód.regr.comis.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.transp.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtTipoFrete, "CTipoFrete", "Tp.Frete", ListaCampos.DB_SI, false ) );

		/*
		 * SELECT CODTIPOMOV, DESCTIPOMOV FROM EQTIPOMOV WHERE ( TUSUTIPOMOV='S' OR EXISTS (SELECT * FROM EQTIPOMOVUSU TU WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=4 AND TU.CODFILIALUS=1 AND TU.IDUSU='sysdba') ) ORDER
		 * BY 1
		 */
		lcTipoMov.setWhereAdic( 
				"( (ESTIPOMOV = 'S' OR TIPOMOV IN ('PV','DV')) AND " + 
				" ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + 
				"WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + 
				"TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
				+ Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + 
				" AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) )" );

		if ( (Boolean) oPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
			txtFiscalTipoMov1.setText( "S" );
			txtFiscalTipoMov2.setText( "N" );
			lcTipoMov.setDinWhereAdic( "FISCALTIPOMOV IN(#S,#S)", txtFiscalTipoMov1 );
			lcTipoMov.setDinWhereAdic( "", txtFiscalTipoMov2 );
		}

		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		
		// FK Modelo de nota
		lcModNota.add( new GuardaCampo( txtCodModNota, "CodModNota", "Cód.mod.nota", ListaCampos.DB_PK, false ) );
		lcModNota.add( new GuardaCampo( txtDescModNota, "DescModNota", "Descrição do modelo de nota", ListaCampos.DB_SI, false ) );
		lcModNota.add( new GuardaCampo( txtTipoModNota, "TipoModNota", "Tipo", ListaCampos.DB_SI, false ) );
		lcModNota.montaSql( false, "MODNOTA", "LF" );
		lcModNota.setQueryCommit( false );
		lcModNota.setReadOnly( true );
		txtCodModNota.setTabelaExterna( lcModNota );
		
		lcTipoMov.adicDetalhe( lcModNota );
		

		// Adiciona os componentes na tela e no ListaCompos da venda
		setListaCampos( lcCampos );
		setAltCab( 160 );
		setPainel( pinCabVenda );
		adicCampo( txtCodVenda, 7, 20, 90, 20, "CodVenda", "Nro. Pedido", ListaCampos.DB_PK, true );
		adicCampo( txtCodTipoMov, 100, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 180, 20, 197, 20, "DescTipoMov", "Descrição do tipo de movimento" );
		adicCampo( txtCodSerie, 380, 20, 77, 20, "Serie", "Série", ListaCampos.DB_FK, false );
		adicCampo( txtDocVenda, 460, 20, 77, 20, "DocVenda", "Nro. Doc.", ListaCampos.DB_SI, false );
		adicCampo( txtDtEmitVenda, 540, 20, 97, 20, "DtEmitVenda", "Data da emissão", ListaCampos.DB_SI, true );
		adicCampo( txtDtSaidaVenda, 640, 20, 97, 20, "DtSaidaVenda", "Data da saída", ListaCampos.DB_SI, true );
		adicCampo( txtCodCli, 7, 60, 80, 20, "CodCli", "Cód. cli.", ListaCampos.DB_FK, txtDescCli, true );
		adicDescFK( txtDescCli, 90, 60, 197, 20, "RazCli", "Razão social do cliente" );
		adicCampo( txtCodPlanoPag, 290, 60, 77, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, true );
		adicDescFK( txtDescPlanoPag, 370, 60, 197, 20, "DescPlanoPag", "Descrição do plano de pag." );
		adicCampo( txtPedCliVenda, 570, 60, 75, 20, "PedCliVenda", "N.ped.cli.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtTipoVenda, "tipovenda", "Tp.Venda", ListaCampos.DB_SI, true );
		adic( lbStatus, 649, 60, 95, 20 );

		setPainel( pinCabComis );

		adicCampo( txtCodVend, 7, 20, 80, 20, "CodVend", "Cód.comis.", ListaCampos.DB_FK, txtNomeVend, true );
		adicDescFK( txtNomeVend, 90, 20, 197, 20, "NomeVend", "Nome do comissionado" );
		if ( (Boolean) oPrefs[ POS_PREFS.USACLASCOMIS.ordinal() ] ) {
			adicCampo( txtCodClComis, 290, 20, 80, 20, "CodClComis", "Cód.c.comis.", ListaCampos.DB_FK, txtDescClComis, true );
			adicDescFK( txtDescClComis, 373, 20, 260, 20, "DescClComis", "Descrição da class. de comis." );
			adicCampo( txtPercComisVenda, 640, 20, 57, 20, "PercComisVenda", "% comis.", ListaCampos.DB_SI, true );
			adic( new JLabelPad( "Vlr. comis." ), 7, 40, 100, 20 );
			adic( txtVlrComisVenda, 7, 60, 100, 20 );
			adic( new JLabelPad( "% M. comis." ), 110, 40, 100, 20 );
			adic( txtMedComisVenda, 110, 60, 80, 20 );
			adic( btAltComis, 200, 50, 30, 30 );
		}
		else {
			adicCampo( txtPercComisVenda, 290, 20, 57, 20, "PercComisVenda", "% comis.", ListaCampos.DB_SI, true );
		}

		adicCampoInvisivel( txtStatusVenda, "StatusVenda", "Sit.", ListaCampos.DB_SI, false );

		// montaMultiComis();

		tpnCab.addTab( "Fiscal", pinCabFiscal );
		
		setListaCampos( lcCampos );
		setNavegador( lcCampos.getNavegador() );

		setPainel( pinCabFiscal );

		adicDB( cbIPIimp, 90, 20, 30, 20, "ImpIpiVenda", "Imp.", false );
		adicDB( cbIPIcalc, 117, 20, 30, 20, "CalcIpiVenda", "calc.", false );
		adicCampo( txtVlrIPIVenda, 7, 20, 80, 20, "VlrIPIVenda", "Vlr. IPI", ListaCampos.DB_SI, false );

		adicDB( cbPISimp, 233, 20, 30, 20, "ImpPisVenda", "imp.", false );
		adicDB( cbPIScalc, 260, 20, 30, 20, "CalcPisVenda", "calc.", false );
		adicCampo( txtVlrPisVenda, 150, 20, 80, 20, "VlrPisVenda", "Vlr. PIS", ListaCampos.DB_SI, false );

		adicDB( cbICMSimp, 470, 20, 30, 20, "ImpIcmsVenda", "imp.", false );
		adicDB( cbICMScalc, 500, 20, 30, 20, "CalcIcmsVenda", "calc.", false );
		adicCampo( txtVlrBaseICMSVenda, 300, 20, 80, 20, "VlrBaseIcmsVenda", "Base ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrICMSVenda, 385, 20, 80, 20, "VlrICMSVenda", "Vlr. ICMS", ListaCampos.DB_SI, false );

		adicDB( cbConfisimp, 90, 60, 30, 20, "ImpCofinsVenda", "imp.", false );
		adicDB( cbConfiscalc, 117, 60, 30, 20, "CalcCofinsVenda", "calc.", false );
		adicCampo( txtVlrCofinsVenda, 7, 60, 80, 20, "VlrCofinsVenda", "Vlr. Cofins", ListaCampos.DB_SI, false );

		adicDB( cbContribimp, 233, 60, 30, 20, "ImpCSocialVenda", "imp.", false );
		adicDB( cbContribcalc, 260, 60, 30, 20, "CalcCSocialVenda", "calc.", false );
		adicCampo( txtVlrCSocialVenda, 150, 60, 80, 20, "VlrCSocialVenda", "Vlr. c. social", ListaCampos.DB_SI, false );

		adicDB( cbIRimp, 383, 60, 30, 20, "ImpIrVenda", "imp.", false );
		adicDB( cbIRcalc, 410, 60, 30, 20, "CalcIrVenda", "calc.", false );
		adicCampo( txtVlrIRVenda, 300, 60, 80, 20, "VlrIRVenda", "Vlr. I.R.", ListaCampos.DB_SI, false );

		adicDB( cbISSimp, 608, 60, 30, 20, "ImpiIssVenda", "imp.", false );
		adicDB( cbISScalc, 635, 60, 30, 20, "CalcIssVenda", "calc.", false );
		adicCampo( txtVlrBaseISSVenda, 440, 60, 80, 20, "VlrBaseISSVenda", "Base ISS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrISSVenda, 525, 60, 80, 20, "VlrISSVenda", "Vlr. ISS", ListaCampos.DB_SI, false );

		lcCampos.setWhereAdic( "TIPOVENDA='V'" );
		lcCampos.setWhereAdicMax( "TIPOVENDA='V'" );
		setListaCampos( true, "VENDA", "VD" );

		setAltDet( 100 );
		pinDet = new JPanelPad( 740, 100 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItVenda, 7, 20, 30, 20, "CodItVenda", "Item", ListaCampos.DB_PK, true );
		if ( (Boolean) oPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			txtRefProd.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProd2.getWhereAdic() ) );
			adicCampoInvisivel( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false );
			adicCampoInvisivel( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, true );
			adic( new JLabelPad( "Referência" ), 40, 0, 70, 20 );
			adic( txtRefProd, 40, 20, 70, 20 );
			txtRefProd.setFK( true );
			txtRefProd.removeKeyListener( this );
		}
		else {
			txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
			adicCampo( txtCodProd, 40, 20, 70, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		}

		adicDescFK( txtDescProd, 113, 20, 190, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtCodLote, 306, 20, 67, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLote, false );
		adicCampo( txtQtdItVenda, 376, 20, 67, 20, "QtdItVenda", "Qtd.", ListaCampos.DB_SI, true );

		txtQtdItVenda.setBuscaAdic( new DLBuscaEstoq( lcDet, lcAlmox, lcProd, con, "qtditvenda" ) );

		adicCampo( txtPrecoItVenda, 446, 20, 67, 20, "PrecoItVenda", "Preço", ListaCampos.DB_SI, true );
		adicCampo( txtPercDescItVenda, 516, 20, 47, 20, "PercDescItVenda", "% desc.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrDescItVenda, 566, 20, 67, 20, "VlrDescItVenda", "V. desc.", ListaCampos.DB_SI, false );
		adicCampo( txtPercComItVenda, 636, 20, 45, 20, "PercComisItVenda", "% com.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrComisItVenda, 684, 20, 50, 20, "VlrComisItVenda", "V. com.", ListaCampos.DB_SI, false );

		adicCampo( txtCodNat, 7, 60, 50, 20, "CodNat", "CFOP", ListaCampos.DB_FK, txtDescNat, true );

		adicDescFK( txtDescNat, 60, 60, 167, 20, "DescNat", "Descrição da CFOP" );
		// txtCodAlmoxItVenda.setSoLeitura(true);
		txtCodAlmoxItVenda.setAtivo( false );
		adicCampo( txtCodAlmoxItVenda, 230, 60, 47, 20, "codalmox", "Cod.ax", ListaCampos.DB_FK, false );
		// colocar aqui o campo de saldo
		adicDescFK( txtSldLiqProd, 280, 60, 67, 20, "SldLiqProd", "Saldo" );
		adicCampo( txtBaseICMSItVenda, 350, 60, 67, 20, "VlrBaseICMSItVenda", "B. ICMS", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtBaseICMSBrutItVenda, "VlrBaseICMSBrutItVenda", "B. ICMS S/Red.", ListaCampos.DB_SI, false );		
		adicCampo( txtPercICMSItVenda, 420, 60, 57, 20, "PercICMSItVenda", "% ICMS", ListaCampos.DB_SI, true );
		adicCampo( txtVlrICMSItVenda, 480, 60, 67, 20, "VlrICMSItVenda", "V. ICMS", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtBaseIPIItVenda, "VlrBaseIPIItVenda", "B. IPI", ListaCampos.DB_SI, false );
		adicCampo( txtAliqIPIItVenda, 550, 60, 47, 20, "PercIPIItVenda", "% IPI", ListaCampos.DB_SI, false );
		adicCampo( txtVlrIPIItVenda, 600, 60, 67, 20, "VlrIPIItVenda", "V. IPI", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrProdItVenda, "VlrProdItVenda", "V. bruto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtStrDescItVenda, "StrDescItVenda", "Descontos", ListaCampos.DB_SI, false );
		adicDBLiv( txaObsItVenda, "ObsItVenda", "Observação", false );
		adicCampoInvisivel( txtOrigFisc, "OrigFisc", "Origem", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodTratTrib, "CodTratTrib", "Cód.tr.trib.", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtTipoFisc, "TipoFisc", "Tipo fisc.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtTipoST, "TipoST", "Tipo Sub.Trib.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodMens, "CodMens", "Cód.mens.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtMargemVlAgr, "MargemVlAgrItVenda", "Margem.Vlr.Agreg.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrLiqItVenda, 670, 60, 65, 20, "VlrLiqItVenda", "Vlr.item", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodEmpLG, "CodEmpLG", "Emp.log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialLG, "CodFilialLG", "Filial log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodLog, "CodLog", "Cód.log.", ListaCampos.DB_SI, false );
		
		adicCampoInvisivel( txtCodEmpIf, "codempif", "Cod.emp.it.fiscal.", ListaCampos.DB_SI, false);  
		adicCampoInvisivel( txtCodFilialIf, "codfilialif", "Cod.filial it.fiscal", ListaCampos.DB_SI, false); 
		adicCampoInvisivel( txtCodFiscIf, "codfisc", "codfisc" , ListaCampos.DB_SI, false);
		adicCampoInvisivel( txtCodItFisc, "coditfisc", "coditfisc" , ListaCampos.DB_SI, false);
		
		adicCampoInvisivel( txtCodCompra, "codcompra", "Cód.Compra", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtCodItCompra, "coditcompra", "Cód.It.Compra", ListaCampos.DB_FK, false );
		
		adicCampoInvisivel( txtCodVendaRemessa, "codVendaVR", "Cód.remessa", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtCodItVendaRemessa, "codItVendaVR", "Cód.it.remessa", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtTipoVendaRemessa, "tipoVendaVR", "tipo remessa", ListaCampos.DB_FK, false );
		
		pinTot.adic( new JLabelPad( "Vlr.prod." ), 7, 0, 90, 20 );
		pinTot.adic( txtVlrProdVenda, 7, 20, 90, 20 );
		pinTot.adic( new JLabelPad( "Vlr.desc." ), 7, 40, 90, 20 );
		pinTot.adic( txtVlrDescVenda, 7, 60, 90, 20 );
		pinTot.adic( new JLabelPad( "Vlr.liq." ), 7, 80, 90, 20 );
		pinTot.adic( txtVlrLiqVenda, 7, 100, 90, 20 );
		txtCodNat.setStrMascara( "#.###" );
		lcDet.setWhereAdic( "TIPOVENDA='V'" );
		setListaCampos( true, "ITVENDA", "VD" );

		montaTab();

		int iIniRef = 3;

		if ( (Boolean) oPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			iIniRef = 4;
		}

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 70, iIniRef );
		tab.setTamColuna( 80, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 200, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 80, iIniRef++ );
		tab.setTamColuna( 90, iIniRef++ );
		tab.setAutoRol( true );

		btComiss.setVisible( false );

		if("S".equals( permusu.get( "VISUALIZALUCR" )) && (Boolean) oPrefs[ POS_PREFS.VISUALIZALUCR.ordinal() ] ) {		
			adicPainelLucr();
		}
		
	}

	private synchronized void focusIni() {
	
		tpnCab.requestFocus( true );
	}

	private void focusCodprod() {
	
		if ( (Boolean) oPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			txtRefProd.requestFocus();
		}
		else {
			txtCodProd.requestFocus();
		}
	}

	public NFEConnectionFactory getNfecf() {	
		return nfecf;
	}

	public void setNfecf( NFEConnectionFactory nfecf ) {	
		this.nfecf = nfecf;
	}

	private boolean getProdUsaReceita() {
	
		boolean retorno = false;
	
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
	
		try {
	
			sSql = "SELECT USARECEITAPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
			ps = con.prepareStatement( sSql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				if ( "S".equals( rs.getString( 1 ) ) ) {
					retorno = true;
				}
			}
	
			rs.close();
			ps.close();
	
			con.commit();
	
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar uso de receita no produto!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
	
		return retorno;
	
	}

	private int getNumComissionados() {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		StringBuffer sql = new StringBuffer();
		try {
	
			if ( txtCodTipoMov.getVlrInteger() == null || txtCodTipoMov.getVlrInteger() == 0 ) {
				return 0;
			}
	
			sql.append( "SELECT FIRST 1 RC.CODREGRCOMIS, COUNT(*) " );
			sql.append( "FROM VDREGRACOMIS RC, VDITREGRACOMIS IRC, EQTIPOMOV TM " );
			sql.append( "WHERE IRC.CODEMP=RC.CODEMP AND IRC.CODFILIAL=RC.CODFILIAL " );
			sql.append( "AND IRC.CODREGRCOMIS=RC.CODREGRCOMIS AND RC.CODEMP=? AND RC.CODFILIAL=? " );
			sql.append( "AND RC.CODEMP=TM.CODEMPRC AND RC.CODFILIAL=TM.CODFILIALRC AND RC.CODREGRCOMIS=TM.CODREGRCOMIS " );
			sql.append( "AND TM.CODEMP=? AND TM.CODFILIAL=? AND TM.CODTIPOMOV=? AND TM.MCOMISTIPOMOV='S' " );
			sql.append( "GROUP BY 1 ORDER BY 2 DESC" );
	
			ps = con.prepareStatement( sql.toString() );
	
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDREGRACOMIS" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( 5, txtCodTipoMov.getVlrInteger() );
	
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				result = rs.getInt( 2 );
			}
	
			rs.close();
			ps.close();
	
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean isComissAtivo() {
	
		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
	
		try {
	
			sSql = "SELECT ATIVOCOMIS FROM VDVENDEDOR WHERE CODEMP=? AND CODFILIAL=? AND CODVEND=?";
	
			ps = con.prepareStatement( sSql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			ps.setInt( 3, txtCodVend.getVlrInteger().intValue() );
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				if ( rs.getString( 1 ).equals( "S" ) ) {
					retorno = true;
				}
			}
	
			rs.close();
			ps.close();
	
			con.commit();
	
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro verificando comissionado ativo!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
	
		return retorno;
	
	}

	private boolean getVendaBloqueada() {
	
		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;
	
		try {
	
			iCodVenda = txtCodVenda.getVlrInteger().intValue();
	
			if ( iCodVenda != 0 ) {
	
				sTipoVenda = "V";
	
				sSql = "SELECT BLOQVENDA FROM VDVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA=?";
				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setInt( 3, iCodVenda );
				ps.setString( 4, sTipoVenda );
				rs = ps.executeQuery();
	
				if ( rs.next() ) {
					if ( rs.getString( 1 ).equals( "S" ) ) {
						retorno = true;
					}
				}
	
				rs.close();
				ps.close();
	
				con.commit();
	
			}
	
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro bloqueando a venda!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
		return retorno;
	}

	private void getLote() {
	
		txtCodLote.setVlrString( getLote( txtCodProd.getVlrInteger().intValue(), (Boolean) oPrefs[ POS_PREFS.CONTESTOQ.ordinal() ] ) );
		lcLote.carregaDados();
	}

	/**
	 * Busca da Natureza de Operação . Busca a natureza de operação através da tabela de regras fiscais.
	 * 
	 */
	private void getCFOP() {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODNAT FROM LFBUSCANATSP (?,?,?,?,?,?,?,?,?,?,?,?)";
	
		try {
	
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodFilial );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcProd.getCodFilial() );
			ps.setInt( 4, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, lcCli.getCodFilial() );
			ps.setInt( 7, txtCodCli.getVlrInteger().intValue() );
			ps.setNull( 8, Types.INTEGER );
			ps.setNull( 9, Types.INTEGER );
			ps.setNull( 10, Types.INTEGER );
			ps.setInt( 11, lcTipoMov.getCodFilial() );
			ps.setInt( 12, txtCodTipoMov.getVlrInteger().intValue() );
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				txtCodNat.setVlrString( rs.getString( "CODNAT" ) );
				lcNat.carregaDados();
			}
	
			rs.close();
			ps.close();
	
			con.commit();
	
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar natureza da operação!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void getTratTrib() { 
	
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer();
			
			sql.append( "select origfisc,codtrattrib,redfisc,tipofisc,codmens,aliqfisc,aliqipifisc,tpredicmsfisc,tipost,margemvlagr," );
			sql.append( "codempif,codfilialif,codfisc,coditfisc " );
			sql.append( "from lfbuscafiscalsp(?,?,?,?,?,?,?,?,?,?,?)" );				
				
			try {
	
				ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, lcProd.getCodFilial() );
				ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, lcCli.getCodFilial() );
				ps.setInt( 6, txtCodCli.getVlrInteger().intValue() );
				ps.setInt( 7, Aplicativo.iCodEmp );
				ps.setInt( 8, Aplicativo.iCodFilial );
				ps.setInt( 9, txtCodTipoMov.getVlrInteger() );
				ps.setString( 10, "VD" );
				ps.setNull( 11, Types.CHAR );
				
				rs = ps.executeQuery();
	
				if ( rs.next() ) {
					txtOrigFisc.setVlrString( rs.getString( "origfisc" ) );
					txtCodTratTrib.setVlrString( rs.getString( "codtrattrib" ) );
					txtRedFisc.setVlrBigDecimal( new BigDecimal( rs.getString( "redfisc" ) != null ? rs.getString( "redfisc" ) : "0" ) );
					txtTipoFisc.setVlrString( rs.getString( "tipofisc" ) );
					txtTipoST.setVlrString( rs.getString( "tipost" ) );
					txtCodMens.setVlrString( rs.getString( "codmens" ) );
					txtAliqFisc.setVlrString( rs.getString( "aliqfisc" ) );
					txtAliqIPIFisc.setVlrBigDecimal( new BigDecimal( rs.getString( "aliqipifisc" ) != null ? rs.getString( "aliqipifisc" ) : "0" ) );
					txtTpRedIcmsFisc.setVlrString( rs.getString( "tpredicmsfisc" ) );
					txtMargemVlAgr.setVlrBigDecimal( rs.getBigDecimal( "margemvlagr" )!= null ? rs.getBigDecimal( "margemvlagr" ) : new BigDecimal(0) );
					
					// Carregando campos para gravação do item de classificação selecionado
					
					if ( rs.getInt( "coditfisc" ) > 0 ) {
						txtCodEmpIf.setVlrInteger( rs.getInt( "codempif" ) );
						txtCodFilialIf.setVlrInteger( rs.getInt( "codfilialif" ) );
						txtCodFiscIf.setVlrString( rs.getString( "codfisc" ) );
						txtCodItFisc.setVlrInteger( rs.getInt( "coditfisc" ) );
					}					
				}
	
				rs.close();
				ps.close();
	
				con.commit();
	
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao buscar tratamento tributário!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				rs = null;
				sql = null;
			}
	
		}

	private void getICMS() {
	
		if ( txtAliqFisc.floatValue() > 0 ) {
			txtPercICMSItVenda.setVlrBigDecimal( txtAliqFisc.getVlrBigDecimal() );
			calcImpostos( true );
			return; // Ele cai fora porque se existe um valor no CLFISCAL ele nem busca a Aliq. por Natureza da operaçao.
		}
	
		String sSQL = "SELECT PERCICMS FROM LFBUSCAICMSSP(?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
	
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCodNat.getVlrString() );
			ps.setString( 2, txtEstCli.getVlrString() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilialMz );
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( rs.getString( 1 ) ) );
			}
	
			calcImpostos( true );
	
			rs.close();
			ps.close();
	
			con.commit();
	
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar percentual de ICMS!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	public BigDecimal getVolumes(){
		
		BigDecimal retorno = new BigDecimal(0);
	
		for( int i=0; i<tab.getNumLinhas(); i++ ){
			
			retorno = retorno.add( ConversionFunctions.stringCurrencyToBigDecimal( tab.getValor( i,  (Boolean) oPrefs[ POS_PREFS.USAREFPROD.ordinal() ]? 6:5 ).toString() ));
		}
		
		return retorno;
		
	}

	public Vector<Object> getParansDesconto() {
	
		Vector<Object> param = new Vector<Object>();
		param.addElement( txtStrDescItVenda );
		param.addElement( txtPrecoItVenda );
		param.addElement( txtVlrDescItVenda );
		param.addElement( txtQtdItVenda );
		return param;
	}

	public String[] getParansPass() {
	
		return new String[] { "venda", String.valueOf( txtCodVenda.getVlrInteger().intValue() ), String.valueOf( txtCodItVenda.getVlrInteger().intValue() ), String.valueOf( txtCodProd.getVlrInteger().intValue() ), String.valueOf( txtVlrProdItVenda.getVlrInteger().intValue() ) };
	}

	public int[] getParansPreco() {
	
		int[] iRetorno = { txtCodProd.getVlrInteger().intValue(), txtCodCli.getVlrInteger().intValue(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodPlanoPag.getVlrInteger().intValue(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "FNPLANOPAG" ),
				txtCodTipoMov.getVlrInteger().intValue(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTIPOMOV" ), Aplicativo.iCodEmp, Aplicativo.iCodFilial, txtCodVenda.getVlrInteger().intValue(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDVENDA" ) };
		return iRetorno;
	}

	public void setLog( String[] args ) {
	
		if ( args != null ) {
			txtCodEmpLG.setVlrString( args[ 0 ] );
			txtCodFilialLG.setVlrString( args[ 1 ] );
			txtCodLog.setVlrString( args[ 2 ] );
		}
	}

	public void setParansPreco( BigDecimal bdPreco ) {
	
		txtPrecoItVenda.setVlrBigDecimal( bdPreco );
	}

	private void carregaPrefTipoFiscCli() {
	
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT T.CALCCOFINSTF,T.CALCCSOCIALTF,T.CALCICMSTF,T.CALCIPITF,T.CALCIRTF,T.CALCISSTF,T.CALCPISTF," );
			sql.append( "T.IMPCOFINSTF,T.IMPCSOCIALTF,T.IMPICMSTF,T.IMPISSTF,T.IMPIPITF,T.IMPIRTF,T.IMPPISTF " );
			sql.append( "FROM LFTIPOFISCCLI T, VDCLIENTE C " );
			sql.append( "WHERE T.CODEMP=C.CODEMPFC AND T.CODFILIAL=C.CODFILIALFC AND T.CODFISCCLI=C.CODFISCCLI AND " );
			sql.append( "C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=?" );
	
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, txtCodCli.getVlrInteger() );
			ResultSet rs = ps.executeQuery();
	
			if ( rs.next() ) {
	
				cbIPIimp.setVlrString( rs.getString( "IMPIPITF" ) );
				cbIPIcalc.setVlrString( rs.getString( "CALCIPITF" ) );
				cbPISimp.setVlrString( rs.getString( "IMPPISTF" ) );
				cbPIScalc.setVlrString( rs.getString( "CALCPISTF" ) );
				cbConfisimp.setVlrString( rs.getString( "IMPCOFINSTF" ) );
				cbConfiscalc.setVlrString( rs.getString( "CALCCOFINSTF" ) );
				cbContribimp.setVlrString( rs.getString( "IMPCSOCIALTF" ) );
				cbContribcalc.setVlrString( rs.getString( "CALCCSOCIALTF" ) );
				cbIRimp.setVlrString( rs.getString( "IMPIRTF" ) );
				cbIRcalc.setVlrString( rs.getString( "CALCIRTF" ) );
				cbISSimp.setVlrString( rs.getString( "IMPISSTF" ) );
				cbISScalc.setVlrString( rs.getString( "CALCISSTF" ) );
				cbICMSimp.setVlrString( rs.getString( "IMPICMSTF" ) );
				cbICMScalc.setVlrString( rs.getString( "CALCICMSTF" ) );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela tipo fiscal do cliente!\n" + e.getMessage(), true, con, e );
		}
	
	}

	private void habilitaMultiComis() {
	
		try {
			numComissionados = getNumComissionados();
	
			if ( numComissionados > 0 ) {
				btComiss.setVisible( true );
			}
			else {
				btComiss.setVisible( false );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void altComisVend() {
	
		if ( lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			return;
		}
	
		DLAltComisVend dl = new DLAltComisVend( this, txtCodVenda.getVlrInteger().intValue(), txtMedComisVenda.getVlrBigDecimal(), con );
		dl.setVisible( true );
		dl.dispose();
	
		lcCampos.carregaDados();
	
	}

	private HashMap<String, Object> getPermissaoUsu() {
		HashMap<String,Object> ret = new HashMap<String, Object>();
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT VISUALIZALUCR FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? AND IDUSU=?" );
	
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, Aplicativo.strUsuario );
			ResultSet rs = ps.executeQuery();
	
			if ( rs.next() ) {
				ret.put( "VISUALIZALUCR", rs.getString( "VISUALIZALUCR" ) );
			}
			
			rs.close();
			ps.close();
			
			con.commit();
			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}
		return ret;
	}

	private void abreBuscaCompra() {
		DLBuscaCompra dl = new DLBuscaCompra(lcDet, lcProd, txtCodVenda.getVlrInteger(), con ); 	
		dl.setConexao( con );
		dl.setVisible( true );
		HashMap<String,Integer> retorno = null;
		
		if ( dl.OK ) {
			retorno = dl.getValor();
			txtCodCompra.setVlrInteger( (Integer) retorno.get( "codcompra" ) );
			txtCodItCompra.setVlrInteger( (Integer) retorno.get( "coditcompra" ) );			
		}
		else {	
			txtCodCompra.setVlrInteger( null );
			txtCodItCompra.setVlrInteger( null );
		}
		
		lcItCompra.carregaDados();		
	}

	private void abreBuscaRemessa() {
		
		DLBuscaRemessa dl = new DLBuscaRemessa( txtCodProd.getVlrInteger() ); 	
		dl.setConexao( con );
		dl.setVisible( true );
		HashMap<String,Integer> retorno = null;
		
		if ( dl.OK ) {
			retorno = dl.getVenda();
			txtCodVendaRemessa.setVlrInteger( (Integer) retorno.get( "codvenda" ) );
			txtCodItVendaRemessa.setVlrInteger( (Integer) retorno.get( "coditvenda" ) );	
			txtTipoVendaRemessa.setVlrString( "V" );			
		}
		else {	
			txtCodVendaRemessa.setVlrInteger( null );
			txtCodItVendaRemessa.setVlrInteger( null );	
			txtTipoVendaRemessa.setVlrString( null );	
		}
		
		lcItRemessa.carregaDados();
		
		dl.dispose();
	}
	
	private void abreComissVend() {
	
		DLMultiComiss dl = new DLMultiComiss( con, txtCodVenda.getVlrInteger() );
		dl.setVisible( true );
	}

	private boolean consisteComisObrig() {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean retorno = false;
		StringBuffer sql = new StringBuffer();
	
		try {
	
			sql.append( "SELECT COUNT(*) " );
			sql.append( "FROM VDVENDACOMIS VC, VDITREGRACOMIS RC " );
			sql.append( "WHERE VC.CODEMP=? AND VC.CODFILIAL=? AND VC.CODVENDA=? AND VC.TIPOVENDA='V' " );
			sql.append( "AND RC.CODEMP=VC.CODEMPRC AND RC.CODFILIAL=VC.CODFILIALRC " );
			sql.append( "AND RC.CODREGRCOMIS=VC.CODREGRCOMIS AND RC.SEQITRC=VC.SEQITRC " );
			sql.append( "AND RC.OBRIGITRC='S' AND VC.CODVEND IS NULL" );
	
			ps = con.prepareStatement( sql.toString() );
	
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDACOMIS" ) );
			ps.setInt( 3, txtCodVenda.getVlrInteger() );
	
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				if ( rs.getInt( 1 ) > 0 ) {
					retorno = false;
				}
				else {
					retorno = true;
				}
			}
	
			rs.close();
			ps.close();
	
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	
		return retorno;
	}

	private void atualizaLucratividade() {
		
		if("S".equals( permusu.get( "VISUALIZALUCR" )) && (Boolean) oPrefs[ POS_PREFS.VISUALIZALUCR.ordinal() ] ) {
				
			Lucratividade luc = new Lucratividade( txtCodVenda.getVlrInteger(), "V", txtCodItVenda.getVlrInteger(),fatLucro, oPrefs[ POS_PREFS.TIPOCUSTO.ordinal()].toString(), con );		   
		
			/****************************
			 * Atualizando painel geral 
			 ****************************/
			
			txtTotFat.setVlrBigDecimal( luc.getTotfat() );
			txtTotCusto.setVlrBigDecimal( luc.getTotcusto());
			txtTotLucro.setVlrBigDecimal( luc.getTotlucro());
			pbLucrTotal.setValue( luc.getPerclucrvenda().toBigInteger().intValue() );
			
			//Lucro menor que 20% (Vermelho)
			if(luc.getPerclucrvenda().compareTo( new BigDecimal(20.00) )<=0) {
				pbLucrTotal.setForeground( new Color(255,0,0) );	
			
			}
			//Lucro maior que 20% menor que 30% (Laranja)
			else if (luc.getPerclucrvenda().compareTo( new BigDecimal(20.00) )>0 && luc.getPerclucrvenda().compareTo( new BigDecimal(30.00) )<=0){
				pbLucrTotal.setForeground( new Color(226,161,35) ) ;	
			}
			//Lucro maior que 30% e menor que 50% (Azul)
			else if (luc.getPerclucrvenda().compareTo( new BigDecimal(30.00) )>0 && luc.getPerclucrvenda().compareTo( new BigDecimal(50.00) )<=0){
				pbLucrTotal.setForeground( new Color(0,0,255) ) ;	
			}
			//Lucro maior que 50% (Verde)
			else {
				pbLucrTotal.setForeground( new Color(0,143,20) );
			}
			
			/****************************
			 * Atulizando painel item 
			 ****************************/
			
			txtItemFat.setVlrBigDecimal( luc.getItemfat() );
			txtItemCusto.setVlrBigDecimal( luc.getItemcusto() == null ? new BigDecimal(0) : luc.getItemcusto() );
			txtItemLucro.setVlrBigDecimal( luc.getItemlucro() == null ? new BigDecimal(0) : luc.getItemlucro() );
			
			pbLucrItem.setValue( luc.getPerclucrit().toBigInteger().intValue() );
			pnLucrItem.setBorder(BorderFactory.createTitledBorder( txtCodItVenda.getVlrString() + "-" + txtDescProd.getVlrString().trim()) );
			
			//Lucro menor que 20% (Vermelho)
			if(luc.getPerclucrit().compareTo( new BigDecimal(20.00) )<=0) {
				pbLucrItem.setForeground( new Color(255,0,0) );	
			
			}
			//Lucro maior que 20% menor que 30% (Laranja)
			else if (luc.getPerclucrit().compareTo( new BigDecimal(20.00) )>0 && luc.getPerclucrit().compareTo( new BigDecimal(30.00) )<=0){
				pbLucrItem.setForeground( new Color(226,161,35) ) ;	
			}
			//Lucro maior que 30% e menor que 50% (Azul)
			else if (luc.getPerclucrit().compareTo( new BigDecimal(30.00) )>0 && luc.getPerclucrit().compareTo( new BigDecimal(50.00) )<=0){
				pbLucrItem.setForeground( new Color(0,0,255) ) ;	
			}
			//Lucro maior que 50% (Verde)
			else {
				pbLucrItem.setForeground( new Color(0,143,20) );
			}
			
			
		}
		
	}

	private void adicPainelLucr() {
	
		try {
			
			tpnCab.addTab( "Lucratividade", pinCabLucratividade );			
			setPainel(pinCabLucratividade) ;
			
			/******************
			* Painel Geral
			******************/
			
			pnLucrGeral.setBorder( BorderFactory.createTitledBorder( "Lucratividade Geral" ) );			
			adic(pnLucrGeral, 4, 0, 230, 92 );			
			setPainel(pnLucrGeral);			
			pbLucrTotal.setStringPainted(true);									
			
			adic( pbLucrTotal, 2 , 2, 215 , 20);			
			
			adic( new JLabelPad("Faturado"), 2, 25, 75 , 20);
			adic( txtTotFat, 2, 45, 70 , 20);
			
			adic( new JLabelPad("Custo"), 75, 25, 75 , 20);
			adic( txtTotCusto, 75, 45, 70 , 20);
			
			adic( new JLabelPad("Lucro"), 153, 25, 75 , 20);
			adic( txtTotLucro, 148, 45, 70 , 20);
	
			/******************
			* Painel Item
			******************/
			
			setPainel(pinCabLucratividade) ;
	
			pnLucrItem.setBorder( BorderFactory.createTitledBorder( "Lucratividade Item" ) );			
			adic(pnLucrItem, 240, 0, 230, 92 );			
			
			setPainel(pnLucrItem);			
			pbLucrItem.setStringPainted(true);									
			
			adic( pbLucrItem, 2 , 2, 215 , 20);			
			
			adic( new JLabelPad("Faturado"), 2, 25, 75 , 20);
			adic( txtItemFat, 2, 45, 70 , 20);
			
			adic( new JLabelPad("Custo"), 75, 25, 75 , 20);
			adic( txtItemCusto, 75, 45, 70 , 20);
			
			adic( new JLabelPad("Lucro"), 153, 25, 75 , 20);
			adic( txtItemLucro, 148, 45, 70 , 20);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bloqvenda() {

		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;

		try {

			iCodVenda = txtCodVenda.getVlrInteger().intValue();

			if ( iCodVenda != 0 ) {

				sTipoVenda = "V";

				sSql = "EXECUTE PROCEDURE VDBLOQVENDASP(?,?,?,?,?)";

				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setString( 3, sTipoVenda );
				ps.setInt( 4, iCodVenda );
				ps.setString( 5, "S" );
				ps.executeUpdate();

				ps.close();

				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro bloqueando a venda!\n" + err.getMessage(), true, con, err );
		}
	}

	private void calcDescIt() {

		if ( txtPercDescItVenda.floatValue() != 0 ) {
			txtVlrDescItVenda.setVlrBigDecimal( new BigDecimal( Funcoes.arredFloat( txtVlrProdItVenda.floatValue() * txtPercDescItVenda.floatValue() / 100, casasDecFin ) ) );
		}
	}

	private void calcComisIt() {

		if ( txtPercComItVenda.floatValue() >= 0 ) {
			txtVlrComisItVenda.setVlrBigDecimal( new BigDecimal( Funcoes.arredFloat( ( txtVlrProdItVenda.floatValue() - txtVlrDescItVenda.floatValue() ) * txtPercComItVenda.floatValue() / 100 * txtPercComisVenda.floatValue() / 100, casasDecFin ) ) );
		}
	}

	private void calcVlrProd() {

		txtVlrProdItVenda.setVlrBigDecimal( calcVlrProd( txtPrecoItVenda.getVlrBigDecimal(), txtQtdItVenda.getVlrBigDecimal() ) );
	}

	private void calcImpostos( boolean bBuscaBase ) {

		float fRed = 0;
		float fVlrProd = 0;
		float fBaseIPI = 0;
		float fBaseICMS = 0;
		float fBaseICMSBrut = 0; // Base do ICMS sem redução
		float fICMS = 0;
		float fIPI = 0;
		String tpredicmsfisc = null;

		try {

			tpredicmsfisc = txtTpRedIcmsFisc.getVlrString();
			fRed = txtRedFisc.getVlrBigDecimal() != null ? txtRedFisc.floatValue() : 0;
			fVlrProd = calcVlrTotalProd( txtVlrProdItVenda.getVlrBigDecimal(), txtVlrDescItVenda.getVlrBigDecimal() ).floatValue();

			if ( !bBuscaBase ) {
				fBaseICMS = txtBaseICMSItVenda.floatValue();
				fBaseICMSBrut = txtBaseICMSBrutItVenda.floatValue();
			}

			if ( txtTipoFisc.getText().trim().equals( "II" ) ) {

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
					txtBaseICMSBrutItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}

			// Substituição tributária - Contribuínte Substituído (não paga ICMS)
			
			else if ( txtTipoFisc.getText().trim().equals( "FF" ) && (txtTipoST.getText().trim().equals( "SI" ))) { 

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
					txtBaseICMSBrutItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtVlrICMSItVenda.getAtivo() ) {
					txtUltCamp = txtVlrICMSItVenda;
				}
				else if ( txtPercICMSItVenda.getAtivo() ) {
					txtUltCamp = txtPercICMSItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}
			else if ( txtTipoFisc.getText().trim().equals( "NN" ) ) {

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
					txtBaseICMSBrutItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}
			
			// Substituição tributária - Contribuínte Substituído (Paga ICMS subsequente)
			
			else if ( ( txtTipoFisc.getText().trim().equals( "TT" )) 
					|| ( txtTipoFisc.getText().trim().equals( "FF" )) 
					|| ( ( (txtTipoFisc.getText().trim().equals( "FF" )) &&  txtTipoST.getText().trim().equals( "SU" ))) ) {

				if ( fVlrProd > 0 ) {

					if ( bBuscaBase ) {
						if ( "B".equals( tpredicmsfisc ) ) {
							fBaseICMS = Funcoes.arredFloat( fVlrProd - fVlrProd * fRed / 100, casasDecFin );
						}
						else {
							fBaseICMS = Funcoes.arredFloat( fVlrProd, casasDecFin );
						}
						
						fBaseICMSBrut =  Funcoes.arredFloat( fVlrProd, casasDecFin );
						
					}

					fBaseIPI = fVlrProd;
					
					if ( ( "V".equals( tpredicmsfisc ) ) && ( fRed > 0 ) ) {
						fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItVenda.floatValue() / 100, casasDecFin );
						fICMS -= Funcoes.arredFloat( fICMS * fRed / 100, casasDecFin );
					}
					else {
						fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItVenda.floatValue() / 100, casasDecFin );
					}
					fIPI = Funcoes.arredFloat( fBaseIPI * txtAliqIPIItVenda.floatValue() / 100, casasDecFin );

				}

				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( fICMS ) );
				txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( fBaseICMS ) );
				txtBaseICMSBrutItVenda.setVlrBigDecimal( new BigDecimal( fBaseICMSBrut ) );
				
				txtVlrIPIItVenda.setVlrBigDecimal( new BigDecimal( fIPI ) );
				txtBaseIPIItVenda.setVlrBigDecimal( new BigDecimal( fBaseIPI ) );
				txtAliqIPIItVenda.setVlrBigDecimal( txtAliqIPIFisc.getVlrBigDecimal() );

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtVlrICMSItVenda.getAtivo() ) {
					txtUltCamp = txtVlrICMSItVenda;
				}
				else if ( txtPercICMSItVenda.getAtivo() ) {
					txtUltCamp = txtPercICMSItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}

			txtVlrLiqItVenda.setVlrBigDecimal( calcVlrTotalProd( txtVlrProdItVenda.getVlrBigDecimal(), txtVlrDescItVenda.getVlrBigDecimal() ) );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void mostraTelaDescont() {

		if ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) {

			txtVlrDescItVenda.setAtivo( true );
			txtVlrDescItVenda.setVlrString( "" );
			txtPercDescItVenda.setAtivo( false );
			txtPercDescItVenda.setVlrString( "" );
			calcVlrProd();
			calcImpostos( true );
			mostraTelaDesconto();
			calcVlrProd();
			calcImpostos( true );
			txtVlrDescItVenda.requestFocus( true );
		}
	}

	private boolean testaPgto() {

		boolean bRetorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String sSQL = "SELECT * FROM FNCHECAPGTOSP(?,?,?)";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( rs.getString( 1 ).trim().equals( "N" ) ) {
					bRetorno = false;
				}
			}
			else {
				Funcoes.mensagemErro( this, "Não foi possível checar os pagamentos do cliente!" );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Não foi possível verificar os pagamentos do cliente!\n" + err.getMessage(), true, con, err );
		}

		return bRetorno;

	}

	private boolean testaLucro() {

		return super.testaLucro( new Object[] { txtCodProd.getVlrInteger(), txtCodAlmoxItVenda.getVlrInteger(), txtPrecoItVenda.getVlrBigDecimal(), } );
	}

	public boolean testaCodLote() {

		boolean retorno = true;

		Integer statuslote = testaCodLote( txtCodLote.getVlrString().trim(), txtCodProd.getVlrInteger().intValue() );
		
		if ( statuslote == DLLote.LOTE_INVALIDO ) {
			
			retorno = txtCodLote.mostraDLF2FK();
			
		}
		else if ( statuslote > 0 ){
			
			Funcoes.mensagemInforma( this, "Faltam " + statuslote + " dias para o vencimento deste lote!" );
			
		}

		return retorno;

	}

	private void senhaCredito( boolean fechamento ) {

		FPassword fpw = new FPassword( this, FPassword.LIBERA_CRED, null, con );
		fpw.execShow();

		if ( fpw.OK ) {

			fpw.dispose();

			if ( !Aplicativo.telaPrincipal.temTela( "Liberação de crédito" ) ) {
				FLiberaCredito tela = new FLiberaCredito();
				Aplicativo.telaPrincipal.criatela( "Liberação de crédito", tela, con );
				tela.open( "V", txtCodVenda.getVlrInteger(), txtCodCli.getVlrInteger(), txtCodItVenda.getVlrInteger(), fechamento ? new BigDecimal( "0.00" ) : txtVlrLiqItVenda.getVlrBigDecimal() );
				tela.setVisible( true );
			}
		}
	}

	private boolean consultaCredito( BigDecimal vlradic, boolean fechamento ) {

		try {
			// Liberação de crédito:
			String sSQL = "EXECUTE PROCEDURE FNLIBCREDSP(?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setString( 3, "V" );
			ps.setInt( 4, txtCodVenda.getVlrInteger() );
			ps.setInt( 5, txtCodCli.getVlrInteger() );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setObject( 8, txtVlrLiqVenda.getVlrBigDecimal() );
			ps.setBigDecimal( 9, vlradic );
			ps.setInt( 10, txtCodItVenda.getVlrInteger() );

			ps.execute();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {

			err.printStackTrace();
			String mens = err.getMessage();
			int index = mens.indexOf( "VENDA" );

			if ( mens.indexOf( "VENDA" ) > -1 ) {

				mens = mens.substring( mens.indexOf( "VENDA" ) );

				if ( Funcoes.mensagemConfirma( this, " O valor da venda ultrapassa o limite de crédito pré-estabelecido!\n\n " + mens + "\n\n" + "Deseja efetuar liberação agora?" ) == JOptionPane.YES_OPTION ) {
					senhaCredito( fechamento );
				}

				Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Problema com limite de crédito." + mens );
			}

			return false;
		}

		return true;
	}

	private void abreAdicOrc() {
	
		if ( !Aplicativo.telaPrincipal.temTela( "Busca orçamento" ) ) {
			DLAdicOrc tela = new DLAdicOrc( this, "V" );
			Aplicativo.telaPrincipal.criatela( "Orcamento", tela, con );
		}
	}

	private void fechaVenda() {
	
		try {
	
			if ( (Boolean) oPrefs[ POS_PREFS.CONS_CRED_FECHA.ordinal() ] ) { // Verifica se deve consultar crédito ;
				if ( !consultaCredito( null, true ) ) {
					return;
				}
			}
	
			List<Integer> lsParcRecibo = null;
			String[] sValores = null;
	
			DLFechaVenda dl = new DLFechaVenda( con, txtCodVenda.getVlrInteger(), this, chbImpPedTipoMov.getVlrString(), 
					chbImpNfTipoMov.getVlrString(), chbImpBolTipoMov.getVlrString(), chbImpRecTipoMov.getVlrString(), 
					chbReImpNfTipoMov.getVlrString(), txtCodTran.getVlrInteger(), txtTipoFrete.getVlrString(), 
					getVolumes(), (nfecf.getHasNFE() && "E".equals(txtTipoModNota.getVlrString())) );
			// dl.getDadosCli();
			dl.setVisible( true );
	
			if ( dl.OK ) {
				sValores = dl.getValores();
				if ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPREC.ordinal() ] ) ) {
					lsParcRecibo = dl.getParcRecibo();
				}
				dl.dispose();
			}
			else {
				dl.dispose();
			} 
	
			lcCampos.carregaDados();
	
			if ( sValores != null ) {
	
				// Ordem dos parâmetros decrescente por que uma tela abre na
				// frente da outra.
				/*{ 0 - CODPLANOPAG, 1 - VLRDESCVENDA, 2 - VLRADICVENDA, 3 - IMPPED, 4 - IMPNOTA, 
					5 - MODBOL1, 6 - IMPREC, 7 - MODBOL2, 8 - REIMPNOTA};*/
					
				if ( ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPBOL.ordinal() ] )  ) && 
					 ( ! "".equals(sValores[ DLFechaVenda.COL_RETDFV.MODBOL1.ordinal() ] ) ) ) {
					FRBoleto fBol = new FRBoleto( this );
					fBol.setConexao( con );
					fBol.txtCodModBol.setVlrInteger( new Integer( sValores[ DLFechaVenda.COL_RETDFV.MODBOL1.ordinal() ] ) );
					fBol.txtCodVenda.setVlrInteger( txtCodVenda.getVlrInteger() );
					fBol.gerar();
					fBol.imprimir( true, this ); 
				}
				else if ( ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPREC.ordinal() ] ) ) && 
						( lsParcRecibo != null ) && ( lsParcRecibo.size() > 0 ) ) { // Logica para impressão do recibo.
					FRBoleto fBol = new FRBoleto( this );
					fBol.setConexao( con );
					if ( "".equals( sValores[ DLFechaVenda.COL_RETDFV.MODBOL2.ordinal() ] ) ) {
						Funcoes.mensagemInforma( this, "Modelo de boleto/recibo não foi selecionado!" );
					}
					else {
						fBol.txtCodModBol.setVlrInteger( new Integer( sValores[ DLFechaVenda.COL_RETDFV.MODBOL2.ordinal() ] ) );
						fBol.txtCodVenda.setVlrInteger( txtCodVenda.getVlrInteger() );
						fBol.setParcelas( lsParcRecibo );
						fBol.gerar();
						fBol.imprimir( true );
					}
				}
	
				if ( ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPNOTA.ordinal() ] ) ) || ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.REIMPNOTA.ordinal() ] ) ) ) {
					if ( txtTipoMov.getVlrString().equals( "VD" ) || 
							txtTipoMov.getVlrString().equals( "VT" ) || 
							txtTipoMov.getVlrString().equals( "TR" ) || 
							txtTipoMov.getVlrString().equals( "CS" ) || 
							txtTipoMov.getVlrString().equals( "CE" ) || 
							txtTipoMov.getVlrString().equals( "PE" ) || 
							txtTipoMov.getVlrString().equals( "DV" ) || 
							txtTipoMov.getVlrString().equals( "VR" ) || 
							txtTipoMov.getVlrString().equals( "BN" ) || 
							txtTipoMov.getVlrString().equals( "CO" ) ) {
						emiteNotaFiscal( "NF" );
					}
					else if ( txtTipoMov.getVlrString().equals( "SE" ) ) {
						emiteNotaFiscal( "NS" );
					}
					else {
						Funcoes.mensagemErro( this, "Não existe nota para o tipo de movimento: '" + txtTipoMov.getVlrString() + "'" );
						return;
					}
					if ( "N".equals( sValores[ DLFechaVenda.COL_RETDFV.REIMPNOTA.ordinal() ] ) ) {
						txtStatusVenda.setVlrString( "V4" );
					}
				}
				else if ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPPED.ordinal() ] ) ) {
					imprimir( true, txtCodVenda.getVlrInteger().intValue() );
				}
				
				if ( "N".equals( sValores[ DLFechaVenda.COL_RETDFV.REIMPNOTA.ordinal() ] ) ) {
					lcCampos.edit();
					lcCampos.post();
				}
				if ( ( "S".equals( sValores[ DLFechaVenda.COL_RETDFV.IMPNOTA.ordinal() ] ) ) && 
						( (Boolean) oPrefs[ POS_PREFS.BLOQVENDA.ordinal() ] ) ) {
					bloqvenda();
				}
			}
	
			tpnCab.setSelectedIndex( 0 );
			txtCodVenda.requestFocus();
	
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private String getLayoutPedido( String tipopedido ) {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String retorno = null;
	
		try {
	
			if ( "T".equals( tipopedido ) ) {
				sSQL.append( "SELECT P.CLASSNOTAPAPEL " );
				sSQL.append( "FROM SGPAPEL P, SGIMPRESSORA I, SGESTACAOIMP EI, SGESTACAO E " );
				sSQL.append( "WHERE P.CODPAPEL=I.CODPAPEL AND P.CODEMP=I.CODEMPPL AND P.CODFILIAL=I.CODFILIALPL " );
				sSQL.append( "AND I.CODIMP=EI.CODIMP AND I.CODEMP=EI.CODEMPIP AND I.CODFILIAL=EI.CODFILIALIP AND EI.TIPOUSOIMP='PD' " );
				sSQL.append( "AND EI.CODEST=E.CODEST AND EI.CODEMP=E.CODEMP AND EI.CODFILIAL=E.CODFILIAL " );
				sSQL.append( "AND E.CODEMP=? AND E.CODFILIAL=? AND E.CODEST=?" );
			}
			else {
				sSQL.append( "SELECT CLASSPED " );
				sSQL.append( "FROM SGPREFERE1 " );
				sSQL.append( "WHERE CODEMP=? AND CODFILIAL=?" );
			}
	
			ps = con.prepareStatement( sSQL.toString() );
	
			ps.setInt( 1, Aplicativo.iCodEmp );
	
			if ( "T".equals( tipopedido ) ) {
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPAPEL" ) );
				ps.setInt( 3, Aplicativo.iNumEst );
			}
			else {
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			}
	
			rs = ps.executeQuery();
	
			if ( rs.next() ) {
				retorno = rs.getString( 1 ).trim();
			}
			
			if( "G".equals( tipopedido ) ){
				if( retorno == null ){
					retorno = "PED_PD.jasper";
				}
			}
			
			rs.close();
			ps.close();
	
//			con.commit();
	
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar layout de pedido.\n" + e.getMessage() );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	
		return retorno;
	}

	private void emiteNotaFiscal( final String sTipo ) {
		if ( (nfecf.getHasNFE() && "E".equals(txtTipoModNota.getVlrString())) ) {
			emiteNFE();
		} else {
			emiteNF( sTipo );
		}
	}

	private void emiteNFE() {
		
		nfecf.setKey( Aplicativo.iCodEmp, 
					  ListaCampos.getMasterFilial( "VDVENDA" ), 
					  txtTipoVenda.getVlrString(), 
					  txtCodVenda.getVlrInteger(), 
					  txtDocVenda.getVlrInteger() );
		
		nfecf.post();
	}

	private void emiteNF( final String sTipo ) {
		
		Object layNF = null;
		NFSaida nf = null;
		Vector<Object> parans = null;
		ImprimeOS imp = null;
		DLRPedido dl = null;
		boolean bImpOK = false;
		int iCodVenda = txtCodVenda.getVlrInteger().intValue();
	
		try {
	
			imp = new ImprimeOS( "", con, sTipo, true );
			imp.verifLinPag( sTipo );
			imp.setTitulo( "Nota Fiscal" );
	
			dl = new DLRPedido( sOrdNota, false );
			dl.setVisible( true );
	
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}
	
			try {
				layNF = Class.forName( "org.freedom.layout.nf." + imp.getClassNota() ).newInstance();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( this, "Não foi possível carregar o layout de Nota Fiscal!\n" + err.getMessage() );
			}
	
			if ( layNF != null ) {
	
				if ( layNF instanceof Layout ) {
	
					parans = new Vector<Object>();
					parans.addElement( new Integer( Aplicativo.iCodEmp ) );
					parans.addElement( new Integer( ListaCampos.getMasterFilial( "VDVENDA" ) ) );
					parans.addElement( new Integer( iCodVenda ) );
					nf = new NFSaida( casasDec );
					nf.carregaTabelas( con, parans );
					bImpOK = ( (Layout) layNF ).imprimir( nf, imp );
				}
				else if ( layNF instanceof Leiaute ) {
	
					StringBuffer sSQL = new StringBuffer();
					sSQL.append( "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND ");
					sSQL.append( "IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA)," );
					sSQL.append( "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD " );
				    sSQL.append( " AND L.CODLOTE=I.CODLOTE AND L.CODEMP=I.CODEMPLE AND L.CODFILIAL=I.CODFILIALLE)," );
					sSQL.append( "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD ");
					sSQL.append( " AND L.CODLOTE=I.CODLOTE AND L.CODEMP=I.CODEMPLE AND L.CODFILIAL=I.CODFILIALLE)," );
					sSQL.append( "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=I.CODMENS" );
					sSQL.append( " AND M.CODFILIAL=I.CODFILIALME AND M.CODEMP=I.CODEMPME)," );
					sSQL.append( "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=CL.CODMENS" );
					sSQL.append( " AND M.CODFILIAL=CL.CODFILIALME AND M.CODEMP=CL.CODEMPME)," );
					sSQL.append( "(SELECT S.DESCSETOR FROM VDSETOR S WHERE S.CODSETOR=C.CODSETOR" );
					sSQL.append( " AND S.CODFILIAL=C.CODFILIALSR AND S.CODEMP=C.CODEMPSR)," );
					sSQL.append( "(SELECT B.NOMEBANCO FROM FNBANCO B WHERE B.CODEMP=V.CODEMPBO" );
					sSQL.append( " AND B.CODFILIAL=V.CODFILIALBO AND B.CODBANCO=V.CODBANCO)," );
					sSQL.append( "(SELECT P.SIGLA2CPAIS FROM SGPAIS P WHERE P.CODPAIS=C.CODPAIS)," );
					sSQL.append( "V.DOCVENDA,V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI," );
					sSQL.append( "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,C.NUMCLI,C.COMPLCLI," );
					sSQL.append( "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD, P.CODUNID,N.CODNAT," );
					sSQL.append( "I.VLRLIQITVENDA,N.DESCNAT,I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA," );
					sSQL.append( "I.PERCISSITVENDA,PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA," );
					sSQL.append( "V.VLRISSVENDA,V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA," );
					sSQL.append( "V.VLRBASEISSVENDA,V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,F.CODTRAN," );
					sSQL.append( "T.RAZTRAN,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,T.TIPOTRAN,T.CNPJTRAN,T.ENDTRAN,T.NUMTRAN,T.CIDTRAN," );
					sSQL.append( "T.UFTRAN,T.INSCTRAN,F.QTDFRETEVD,F.ESPFRETEVD,F.MARCAFRETEVD,F.PESOBRUTVD," );
					sSQL.append( "F.PESOLIQVD, I.ORIGFISC, I.CODTRATTRIB, FL.CNPJFILIAl,FL.INSCFILIAL,FL.ENDFILIAL," );
					sSQL.append( "FL.NUMFILIAL,FL.COMPLFILIAL,FL.BAIRFILIAL,FL.CEPFILIAL,M.NOMEMUNIC,FL.SIGLAUF,FL.FONEFILIAL," );
					sSQL.append( "FL.FAXFILIAL,C.ENDCOB, C.NUMCOB, C.COMPLCOB,C.CEPCOB, C.CIDCOB, P.TIPOPROD, C.INCRACLI, V.CODBANCO," );
					sSQL.append( "P.CODFISC, C.ENDENT, C.NUMENT, C.COMPLENT,C.CIDENT,C.UFENT,C.BAIRENT,C.NOMECLI,I.OBSITVENDA," );
					sSQL.append( "V.VLRPISVENDA,V.VLRCOFINSVENDA,V.VLRIRVENDA,V.VLRCSOCIALVENDA,V.CODCLCOMIS,V.PERCCOMISVENDA," );
					sSQL.append( "V.PERCMCOMISVENDA, N.IMPDTSAIDANAT,P.DESCAUXPROD, C.DDDCLI " );
					sSQL.append( " FROM VDVENDA V, VDCLIENTE C, VDITVENDA I, EQPRODUTO P, VDVENDEDOR VEND, FNPLANOPAG PG," );
					sSQL.append( " VDFRETEVD F, VDTRANSP T, LFNATOPER N, SGFILIAL FL, LFITCLFISCAL CL, SGMUNICIPIO M ");
					sSQL.append( "WHERE V.TIPOVENDA='V' AND V.CODVENDA=" );
					sSQL.append( iCodVenda );
					sSQL.append( " AND V.CODEMP=? AND V.CODFILIAL=? AND FL.CODEMP=V.CODEMP AND FL.CODFILIAL=V.CODFILIAL" );
					sSQL.append( " AND C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL " );
					sSQL.append( " AND I.CODVENDA=V.CODVENDA AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA " );
					sSQL.append( " AND P.CODPROD=I.CODPROD AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD ");
					sSQL.append( " AND VEND.CODVEND=V.CODVEND AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD");
					sSQL.append( " AND PG.CODPLANOPAG=V.CODPLANOPAG AND PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG" );
					sSQL.append( " AND F.CODVENDA=V.CODVENDA AND F.TIPOVENDA=F.TIPOVENDA AND F.CODEMP=V.CODEMP AND F.CODFILIAL=V.CODFILIAL" );
					sSQL.append( " AND T.CODTRAN=F.CODTRAN AND T.CODEMP=F.CODEMPTN AND T.CODFILIAL=F.CODFILIALTN");
					sSQL.append( " AND N.CODNAT=I.CODNAT AND N.CODEMP=I.CODEMPNT AND N.CODFILIAL=I.CODFILIALNT");
					sSQL.append( " AND CL.CODFISC = P.CODFISC AND CL.CODEMP=P.CODEMPFC AND CL.CODFILIAL=P.CODFILIALFC" );
					sSQL.append( " AND CL.GERALFISC='S' AND FL.CODMUNIC=M.CODMUNIC AND FL.CODPAIS=M.CODPAIS AND FL.SIGLAUF=M.SIGLAUF " );
					sSQL.append( "ORDER BY P." + dl.getValor() + ",P.DESCPROD" );
	
					StringBuffer sSQLRec = new StringBuffer();
					sSQLRec.append( "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA=" );
					sSQLRec.append( iCodVenda );
					sSQLRec.append( " AND R.CODEMPVA="+Aplicativo.iCodEmp );
					sSQLRec.append( " AND R.CODFILIALVA="+ListaCampos.getMasterFilial( "VDVENDA" ) );
					sSQLRec.append( " AND I.CODREC=R.CODREC AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL ORDER BY I.DTVENCITREC" );
	
					StringBuffer sSQLInfoAdic = new StringBuffer();
					sSQLInfoAdic.append( "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV " );
					sSQLInfoAdic.append( "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?" );
	
					PreparedStatement ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
					ResultSet rs = ps.executeQuery();
	
					PreparedStatement psRec = con.prepareStatement( sSQLRec.toString() );
					ResultSet rsRec = psRec.executeQuery();
	
					PreparedStatement psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
					psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
					psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
					psInfoAdic.setInt( 3, txtCodVenda.getVlrInteger().intValue() );
					ResultSet rsInfoAdic = psInfoAdic.executeQuery();
	
					bImpOK = ( (Leiaute) layNF ).imprimir( rs, rsRec, rsInfoAdic, imp );
	
					dl.dispose();
					con.commit();
				}
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar tabela de Venda!" + err.getMessage(), true, con, err );
		}
	
		if ( bImpOK ) {
			imp.preview( this );
		}
	
		imp.fechaPreview();		
	}

	private void imprimir( boolean bVisualizar, int iCodVenda ) {
	
			PreparedStatement ps = null;
			PreparedStatement psRec = null;
			PreparedStatement psInfoAdic = null;
			ResultSet rs = null;
			ResultSet rsRec = null;
			ResultSet rsInfoAdic = null;
			StringBuffer sSQL = new StringBuffer();
			StringBuffer sSQLRec = new StringBuffer();
			StringBuffer sSQLInfoAdic = new StringBuffer();
			String sDiasPE = null;
			DLRPedido dl = null;
			GregorianCalendar cal = null;
			Date dtHoje = null;
			Vector<?> vDesc = null;
			Vector<?> vObs = null;
			Object layNF = null;
			ImprimeOS imp = new ImprimeOS( "", con, "PD", true );
			boolean bImpOK = false;
			int linPag = imp.verifLinPag( "PD" ) - 1;
			int iDiasPE = 0;
			String tipoimp = "T";
			HashMap<String,Object> hParam = new HashMap<String,Object>();
			
			try {
	
				dl = new DLRPedido( sOrdNota, false );
				dl.setConexao( con );
				dl.setTipo( "G" );
				dl.setVisible( true );
				tipoimp = dl.getTipo();
	
				if ( dl.OK == false ) {
					dl.dispose();
					return;
				}
				
				sSQL.append( "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC ");
				sSQL.append( "WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL ");
				sSQL.append( "AND IC.TIPOVENDA=V.TIPOVENDA) QTDITENS,(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD ");
				sSQL.append( "AND L.CODLOTE=I.CODLOTE) CODLOTE, ");
				sSQL.append( "(SELECT L.VENCTOLOTE FROM EQLOTE L ");
				sSQL.append( "WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE) VENCTOLOTE,V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI, ");
				sSQL.append( "V.DTEMITVENDA,C.ENDCLI,C.NUMCLI,C.COMPLCLI,C.PESSOACLI,C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI, ");
				sSQL.append( "C.UFCLI,C.FONECLI,C.FONECLI,C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD,P.CODUNID, ");
				sSQL.append( "I.PERCISSITVENDA,I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA,I.PERCIPIITVENDA, ");
				sSQL.append( "VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA,V.VLRFRETEVENDA,V.VLRDESCVENDA,I.VLRDESCITVENDA, ");
				sSQL.append( "V.VLRADICVENDA,V.VLRIPIVENDA,V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG, ");
				sSQL.append( "(SELECT T.RAZTRAN FROM VDTRANSP T, VDFRETEVD F WHERE T.CODEMP=F.CODEMPTN AND T.CODFILIAL=F.CODFILIALTN ");
				sSQL.append( "AND T.CODTRAN=F.CODTRAN AND F.CODEMP=V.CODEMP AND F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA ");
				sSQL.append( "AND F.CODVENDA=V.CODVENDA) AS RAZTRAN, ");
				sSQL.append( "(SELECT F.TIPOFRETEVD FROM VDFRETEVD F WHERE F.CODEMP=V.CODEMP AND F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA ");
				sSQL.append( "AND F.CODVENDA=V.CODVENDA) AS TIPOFRETEVD,I.VLRLIQITVENDA,P.DESCAUXPROD,C.DDDCLI,C.EMAILCLI,I.CODITVENDA, ");
				sSQL.append( "(SELECT PE.DIASPE FROM VDPRAZOENT PE WHERE PE.CODEMP=P.CODEMPPE AND PE.CODFILIAL=P.CODFILIALPE AND PE.CODPE=P.CODPE) DIASPE, ");
				sSQL.append( "C.SITECLI,I.OBSITVENDA,VEND.EMAILVEND, ");
				sSQL.append( "(SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU AND FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC) AS DESCFUNC, ");
				sSQL.append( "V.PEDCLIVENDA,C.CONTCLI,P.CODFISC,FCL.DESCFISC,V.DOCVENDA, C.OBSCLI,C.BAIRENT, C.ENDENT, C.CIDENT, C.UFENT, C.CEPENT, ");
				sSQL.append( "C.FONEENT, VF.PLACAFRETEVD, VF.PESOBRUTVD, VF.PESOLIQVD, VF.QTDFRETEVD, P.DESCCOMPPROD, V.OBSVENDA, VF.QTDFRETEVD ");
				sSQL.append( "FROM VDCLIENTE C, EQPRODUTO P,VDVENDEDOR VEND,FNPLANOPAG PG, VDVENDA V, VDITVENDA I ");
				sSQL.append( "left outer join VDFRETEVD VF on VF.CODEMP=I.CODEMP AND VF.CODFILIAL=I.CODFILIAL AND VF.CODVENDA=I.CODVENDA AND VF.TIPOVENDA=I.TIPOVENDA ");
				sSQL.append( "left outer join lfclfiscal fcl on fcl.CODEMP=I.CODEMPif AND fcl.CODFILIAL=I.CODFILIALif AND fcl.CODfisc=I.codfisc ");
				sSQL.append( "WHERE ");
				sSQL.append( "V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND ");
				sSQL.append( "V.CODVENDA=? AND C.CODEMP=V.CODEMPCL ");
				sSQL.append( "AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL ");
				sSQL.append( "AND I.TIPOVENDA=V.TIPOVENDA AND I.CODVENDA=V.CODVENDA AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD ");
				sSQL.append( "AND P.CODPROD=I.CODPROD AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD ");
				sSQL.append( "AND VEND.CODVEND=V.CODVEND AND PG.CODEMP=V.CODEMPPG ");
				sSQL.append( "AND PG.CODFILIAL=V.CODFILIALPG AND PG.CODPLANOPAG=V.CODPLANOPAG ");
				sSQL.append( "ORDER BY P.DESCPROD,P.DESCPROD ");

				
				ps = con.prepareStatement( sSQL.toString() );
				
				System.out.println( "sql:" + sSQL.toString() );
				
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setInt( 3, iCodVenda );
				rs = ps.executeQuery();
	
				sSQLRec.append( "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA=" );
				sSQLRec.append( iCodVenda );
				sSQLRec.append( " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC" );
	
				sSQLInfoAdic.append( "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV " );
				sSQLInfoAdic.append( "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?" );
	
				dl.dispose();
				
				if ( (Boolean) oPrefs[ POS_PREFS.USALAYOUTPED.ordinal() ] ) {
	
					try {
						if ( "T".equals( tipoimp ) ) {
							layNF = Class.forName( "org.freedom.layout.pd." + getLayoutPedido( tipoimp ) ).newInstance();
						}
						else {
							
							if ( classped.equals( "QA" ) ) {
								hParam.put( "CODEMP", Aplicativo.iCodEmp );
								hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
								hParam.put( "CODVENDA", txtCodVenda.getVlrInteger() );
								hParam.put( "TIPOVENDA", "V" );
								hParam.put( "REPORT_CONNECTION", con.getConnection() ); 
								hParam.put( "SUBREPORT_DIR", "org/freedom/layout/pd/");
															
								System.out.println("SQL:" + sSQL.toString());
								
//								FPrinterJob dlGr = new FPrinterJob("layout/pd/" + getLayoutPedido( tipoimp ),"PEDIDO","",rs,hParam,this,null);
								FPrinterJob dlGr = new FPrinterJob("layout/pd/" + getLayoutPedido( tipoimp ),"PEDIDO","",rs,hParam,this,null);
								
								if ( bVisualizar ) {
									dlGr.setVisible( true );
								}
								else {
									try {
										JasperPrintManager.printReport( dlGr.getRelatorio(), true );
									} catch ( Exception err ) {
										Funcoes.mensagemErro( this, "Erro na impressão do pedido!" + err.getMessage(), true, con, err );
										err.printStackTrace();
									}
								}
								
							}
							else{
								
								hParam.put( "CODEMP", Aplicativo.iCodEmp );
								hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
								hParam.put( "CODVENDA", txtCodVenda.getVlrInteger() );
								hParam.put( "TIPOVENDA", "V" );
								hParam.put( "SUBREPORT_DIR", "org/freedom/layout/pd/"); 
								
								//FPrinterJob dlGr = new FPrinterJob("layout/pd/" + getLayoutPedido( tipoimp ),"PEDIDO","",this,hParam,con);
								
								FPrinterJob dlGr = new FPrinterJob("layout/pd/PED_PD.jasper","PEDIDO","",rs,hParam,this,null);
								
								
								if ( bVisualizar ) {
									dlGr.setVisible( true );
								}
								else {
									try {
										JasperPrintManager.printReport( dlGr.getRelatorio(), true );
									} catch ( Exception err ) {
										Funcoes.mensagemErro( this, "Erro na impressão do pedido!" + err.getMessage(), true, con, err );
										err.printStackTrace();
									}
								}
							}											
						}
					} catch ( Exception err ) {
						err.printStackTrace();
						Funcoes.mensagemInforma( this, "Não foi possível carregar o layout do Pedido!\n" + err.getMessage() );
					}
	
					if ( layNF != null ) {
	
						if ( layNF instanceof Leiaute ) {
	
							/*ps = con.prepareStatement( sSQL.toString() );
							ps.setInt( 1, Aplicativo.iCodEmp );
							ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
							ps.setInt( 3, iCodVenda );
							rs = ps.executeQuery();*/
	
							psRec = con.prepareStatement( sSQLRec.toString() );
							rsRec = psRec.executeQuery();
	
							psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
							psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
							psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
							psInfoAdic.setInt( 3, iCodVenda );
							rsInfoAdic = psInfoAdic.executeQuery();
	
							bImpOK = ( (Leiaute) layNF ).imprimir( rs, rsRec, rsInfoAdic, imp );
	
							if ( bImpOK ) {
								if ( bVisualizar ) {
									imp.preview( this );
								}
								else {
									imp.print();
								}
							}
	
							imp.fechaPreview();
	
						}
	
					}
	
				}
				else {
					if ( "T".equals( tipoimp ) ) {
	
						ps = con.prepareStatement( sSQL.toString() );
						ps.setInt( 1, Aplicativo.iCodEmp );
						ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
						ps.setInt( 3, iCodVenda );
						rs = ps.executeQuery();
	
						psRec = con.prepareStatement( sSQLRec.toString() );
						rsRec = psRec.executeQuery();
	
						psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
						psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
						psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
						psInfoAdic.setInt( 3, iCodVenda );
						rsInfoAdic = psInfoAdic.executeQuery();
	
						imp.limpaPags();
						imp.montaCab();
						imp.setTitulo( "Relatório de Pedidos" );
	
						while ( rs.next() ) {
	
							vDesc = new Vector<Object>();
							if ( (Boolean) oPrefs[ POS_PREFS.DESCCOMPPED.ordinal() ] ) {
								vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsItVenda" ) == null ? rs.getString( "DescProd" ).trim() : rs.getString( "ObsItVenda" ).trim() ), 40 );
							}
							else {
								vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "DescProd" ).trim() ), 50 );
							}
	
							for ( int i = 0; i < vDesc.size(); i++ ) {
	
								if ( imp.pRow() == 0 ) {
	
									imp.impCab( 136, false );
									imp.say( 0, imp.comprimido() );
									imp.say( 1, "CLIENTE: " + rs.getString( "CodCli" ).trim() + " - " + ( rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).trim() : "" ) );// nome cliente															
									imp.say( 70, "PEDIDO N.: " + rs.getString( "CodVenda" ).trim() );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 1, rs.getString( "CpfCli" ) != null ? "CPF    : " + Funcoes.setMascara( rs.getString( "CpfCli" ), "###.###.###-##" ) : "CNPJ   : " + Funcoes.setMascara( rs.getString( "CnpjCli" ), "##.###.###/####-##" ) );// CNJP cliente
									imp.say( 70, "PEDIDO CLIENTE: " + ( rs.getString( "PEDCLIVENDA" ) == null ? "" : rs.getString( "PEDCLIVENDA" ) ) );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 1, ( rs.getString( "RgCli" ) != null ? "R.G.   : " + rs.getString( "RgCli" ) : "I.E.   : " + ( rs.getString( "InscCli" ) != null ? rs.getString( "InscCli" ) : "" ) ) );// IE cliente
									imp.say( 70, "CONTATO: " + ( rs.getString( "ContCli" ) != null ? rs.getString( "ContCli" ).trim() : "" ) );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 1, ( rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).trim() + " N°:" + ( rs.getString( "NumCli" ) != null ? rs.getString( "NumCli" ) : "" ) : "" ) + " " + ( rs.getString( "BairCli" ) != null ? rs.getString( "BairCli" ).trim() : "" ) + ( rs.getString( "CidCli" ) != null ? " - " + rs.getString( "CidCli" ).trim() : "" ) + ( rs.getString( "UFCli" ) != null ? " - " + rs.getString( "UFCli" ).trim() : "" )
											+ ( rs.getString( "CEPCli" ) != null ? " - " + rs.getString( "CEPCli" ).trim() : "" ) + " TEL: " + ( rs.getString( "DDDCli" ) != null ? Funcoes.setMascara( rs.getString( "DDDCli" ), "(####)" ) : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ) + " - FAX:"
											+ ( rs.getString( "FaxCli" ) != null ? Funcoes.setMascara( rs.getString( "FaxCli" ), "####-####" ) : "" ) );// endereço e telefone cliente
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 0, StringFunctions.replicate( "-", 135 ) );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 55, "DADO(S) DO(S) PRODUTO(S)" );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 0, StringFunctions.replicate( "-", 135 ) );
									imp.pulaLinha( 1, imp.comprimido() );
									imp.say( 1, "ITEM|  CÓDIGO  |                 DESCRIÇÃO               |     LOTE     |UN|   QUANT.   |    V.UNIT.  |    V.TOTAL    |  IPI%  |  ICMS% " );
	
								}
	
								imp.pulaLinha( 1, imp.comprimido() );
	
								if ( i == 0 ) {
									imp.say( 1, Funcoes.copy( rs.getString( "CodItVenda" ).trim(), 4 ) );
									imp.say( 6, Funcoes.copy( rs.getString( "RefProd" ).trim(), 10 ) );
								}
	
								imp.say( 17, "" + vDesc.elementAt( i ).toString() );
	
								if ( i == 0 ) {
									imp.say( 59, ( rs.getString( 2 ) != null ? rs.getString( 2 ).trim() : "" ) );
									imp.say( 74, rs.getString( "CodUnid" ).trim() );
									imp.say( 79, Funcoes.strDecimalToStrCurrency( 9, 2, rs.getString( "QtdItVenda" ) ) );
									imp.say( 87, Funcoes.strDecimalToStrCurrency( 13, 2, "" + ( new BigDecimal( rs.getString( "VlrLiqItVenda" ) ) ).divide( new BigDecimal( rs.getDouble( "QtdItVenda" ) ), 2, BigDecimal.ROUND_HALF_UP ) ) );
									imp.say( 106, Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrLiqItVenda" ) ) );
									imp.say( 122, rs.getString( "PercIPIItVenda" ) );
									imp.say( 130, rs.getString( "PercICMSItVenda" ) );
								}
	
							}
	
							if ( iDiasPE < rs.getInt( 57 ) ) {
								iDiasPE = rs.getInt( 57 );
							}
	
						}
	
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, StringFunctions.replicate( "-", 135 ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 4, "TOTAL IPI: " + Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrIPIVenda" ) ) );
						imp.say( 44, "|    TOTAL ICMS: " + Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrICMSVenda" ) ) );
						imp.say( 84, "|    TOTAL PRODUTOS: " + Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrLiqVenda" ) ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, StringFunctions.replicate( "-", 135 ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "PAGAMENTO.........:    " + rs.getString( "CODPLANOPAG" ) + " - " + rs.getString( "DESCPLANOPAG" ) );
						imp.pulaLinha( 1, imp.comprimido() );
						
							
						if ( (Boolean) oPrefs[ POS_PREFS.DIASPEDT.ordinal() ] ) {
	
							dtHoje = new Date();
							cal = new GregorianCalendar();
							cal.setTime( dtHoje );
	
							if ( iDiasPE > 0 ) {
								cal.add( GregorianCalendar.DAY_OF_YEAR, iDiasPE );
								sDiasPE = Funcoes.dateToStrDate( cal.getTime() );
							}
							else {
								sDiasPE = "";
							}
	
							imp.say( 0, "DATA DE ENTREGA...:    " + sDiasPE );
	
						}
						else {
							sDiasPE = ( iDiasPE > 0 ? iDiasPE + " dias" : "" );
							imp.say( 0, "PRAZO DE ENTREGA..:    " + sDiasPE );
						}
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 70, StringFunctions.replicate( "-", 40 ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 85, "Assinatura" );	
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, StringFunctions.replicate( "-", 135 ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 62, "OBSERVACÃO" );
						imp.pulaLinha( 1, imp.comprimido() );
	
						vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsVenda" ) ), 115 );
	
						for ( int i = 0; i < vObs.size(); i++ ) {
	
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 20, vObs.elementAt( i ).toString() );
	
							if ( imp.pRow() >= linPag ) {
								imp.incPags();
								imp.eject();
							}
	
						}
	
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, StringFunctions.replicate( "-", 135 ) );
						imp.pulaLinha( 2, imp.comprimido() );
						
	
						imp.eject();
						imp.fechaGravacao();
	
						if ( bVisualizar ) {
							imp.preview( this );
						}
						else {
							imp.print();
						}
					}
					else {
						//FPrinterJob dlGr = new FPrinterJob( "layout/pd/PED_PD.jasper", "PEDIDO", null, rs, null, this );
						FPrinterJob dlGr = new FPrinterJob("layout/pd/PED_PD.jasper","PEDIDO","",rs,hParam,this,null);
	
						if ( bVisualizar ) {
							dlGr.setVisible( true );
						}
						else {
							try {
								JasperPrintManager.printReport( dlGr.getRelatorio(), true );
							} catch ( Exception err ) {
								Funcoes.mensagemErro( this, "Erro na impressão de pedido!" + err.getMessage(), true, con, err );
							}
						}
	
					}
				}
	
				con.commit();
	
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao consultar a tabela de Venda!" + err.getMessage(), true, con, err );
				err.printStackTrace();
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao montar impressão!" );
				err.printStackTrace();
			} finally {
				ps = null;
				psRec = null;
				psInfoAdic = null;
				rs = null;
				rsRec = null;
				rsInfoAdic = null;
				sSQL = null;
				sSQLRec = null;
				sSQLInfoAdic = null;
				sDiasPE = null;
				dl = null;
				cal = null;
				dtHoje = null;
				vDesc = null;
				vObs = null;
				layNF = null;
				System.gc();
			}
		}

	private Object[] prefs() {
	
		Object[] retorno = new Object[ POS_PREFS.values().length ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL.append( "SELECT USAREFPROD,USAPEDSEQ,USALIQREL,TIPOPRECOCUSTO,ORDNOTA,USAPRECOZERO," );
			sSQL.append( "USACLASCOMIS,TRAVATMNFVD,NATVENDA,IPIVENDA,BLOQVENDA, VENDAMATPRIM, DESCCOMPPED, " );
			sSQL.append( "TAMDESCPROD, OBSCLIVEND, CONTESTOQ, DIASPEDT, RECALCPCVENDA, USALAYOUTPED, " );
			sSQL.append( "ICMSVENDA, MULTICOMIS, TIPOPREFCRED, TIPOCLASSPED, VENDAPATRIM, VISUALIZALUCR, " );
			sSQL.append( "INFCPDEVOLUCAO, INFVDREMESSA, TIPOCUSTOLUC " );
			
			sSQL.append( "FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				retorno[ POS_PREFS.USAREFPROD.ordinal() ] = "S".equals( rs.getString( "USAREFPROD" ) );
				retorno[ POS_PREFS.USAPEDSEQ.ordinal() ] = "S".equals( rs.getString( "USAPEDSEQ" ) );
				if ( rs.getString( "UsaLiqRel" ) == null ) {
					Funcoes.mensagemInforma( this, "Preencha opção de desconto em preferências!" );
				}
				else {
					retorno[ POS_PREFS.USALIQREL.ordinal() ] = "S".equals( rs.getString( "UsaLiqRel" ) );
					sOrdNota = rs.getString( "OrdNota" );
					retorno[ POS_PREFS.TIPOPRECOCUSTO.ordinal() ] = "S".equals( rs.getString( "TipoPrecoCusto" ) );
					retorno[ POS_PREFS.USACLASCOMIS.ordinal() ] = "S".equals( rs.getString( "UsaClasComis" ) );
				}
				retorno[ POS_PREFS.TRAVATMNFVD.ordinal() ] = "S".equals( rs.getString( "TravaTmNfVd" ) );
				retorno[ POS_PREFS.NATVENDA.ordinal() ] = "S".equals( rs.getString( "NatVenda" ) );
				retorno[ POS_PREFS.BLOQVENDA.ordinal() ] = "S".equals( rs.getString( "BloqVenda" ) );
				retorno[ POS_PREFS.VENDAMATPRIM.ordinal() ] = "S".equals( rs.getString( "VendaMatPrim" ) );
				retorno[ POS_PREFS.DESCCOMPPED.ordinal() ] = "S".equals( rs.getString( "DescCompPed" ) );
				retorno[ POS_PREFS.TAMDESCPROD.ordinal() ] = "S".equals( rs.getString( "TAMDESCPROD" ) );
				retorno[ POS_PREFS.OBSCLIVEND.ordinal() ] = "S".equals( rs.getString( "OBSCLIVEND" ) );
				retorno[ POS_PREFS.IPIVENDA.ordinal() ] = "S".equals( rs.getString( "IPIVenda" ) );
				retorno[ POS_PREFS.CONTESTOQ.ordinal() ] = "S".equals( rs.getString( "CONTESTOQ" ) );
				retorno[ POS_PREFS.DIASPEDT.ordinal() ] = "S".equals( rs.getString( "DIASPEDT" ) );
				retorno[ POS_PREFS.RECALCCPVENDA.ordinal() ] = "S".equals( rs.getString( "RECALCPCVENDA" ) );
				retorno[ POS_PREFS.USALAYOUTPED.ordinal() ] = "S".equals( rs.getString( "USALAYOUTPED" ) );
				retorno[ POS_PREFS.ICMSVENDA.ordinal() ] = "S".equals( rs.getString( "ICMSVENDA" ) );
				retorno[ POS_PREFS.USAPRECOZERO.ordinal() ] = "S".equals( rs.getString( "USAPRECOZERO" ) );
				retorno[ POS_PREFS.MULTICOMIS.ordinal() ] = "S".equals( rs.getString( "MULTICOMIS" ) );
	
				retorno[ POS_PREFS.CONS_CRED_FECHA.ordinal() ] = 
					( "FV".equals( rs.getString( "TIPOPREFCRED" ) ) || "AB".equals( rs.getString( "TIPOPREFCRED" ) ) );
				retorno[ POS_PREFS.CONS_CRED_ITEM.ordinal() ] = 
					( "II".equals( rs.getString( "TIPOPREFCRED" ) ) || "AB".equals( rs.getString( "TIPOPREFCRED" ) ) );
				classped = rs.getString( "TIPOCLASSPED" );
				retorno[ POS_PREFS.VENDAIMOBILIZADO.ordinal() ] = "S".equals( rs.getString( "VENDAPATRIM" ) );
				retorno[ POS_PREFS.VISUALIZALUCR.ordinal() ] = "S".equals( rs.getString( "VISUALIZALUCR" ) );
				retorno[ POS_PREFS.INFCPDEVOLUCAO.ordinal() ] = "S".equals( rs.getString( "INFCPDEVOLUCAO" ) );
				retorno[ POS_PREFS.INFVDREMESSA.ordinal() ] = "S".equals( rs.getString( "INFVDREMESSA" ) );
				retorno[ POS_PREFS.TIPOCUSTO.ordinal() ] = rs.getString( "TIPOCUSTOLUC" ) ;
				
	
			}
			rs.close();
			ps.close();
			con.commit();
		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}

	public void exec( int codvenda) {
		exec( codvenda, -1, "V" );
	}
	
	public void exec( int codvenda, String tipovenda ) {
		exec( codvenda, -1, tipovenda );
	}

	public void exec( int codvenda, int coditvenda, String tipovenda ) {
	
		txtCodVenda.setVlrInteger( codvenda );
		txtTipoVenda.setVlrString( tipovenda );
		lcCampos.carregaDados();
		
		if ( coditvenda > 0 ) {
			txtCodItVenda.setVlrInteger( coditvenda );
			lcDet.carregaDados();
		}	
	}

	public void beforeInsert( InsertEvent ievt ) {
	
		try {
	
			lbStatus.setForeground( Color.WHITE );
			lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
			lbStatus.setOpaque( true );
			lbStatus.setVisible( false );
	
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void afterInsert( InsertEvent ievt ) {
	
		if ( ievt.getListaCampos() == lcCampos ) {
	
			habilitaMultiComis();
	
			if ( (Boolean) oPrefs[ POS_PREFS.USAPEDSEQ.ordinal() ] ) {
				txtCodVenda.setVlrInteger( testaCodPK( "VDVENDA" ) );
			}
			if ( (Boolean) oPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "N" );
				txtFiscalTipoMov2.setText( "N" );
			}
			txtDtSaidaVenda.setVlrDate( new Date() );
			txtDtEmitVenda.setVlrDate( new Date() );
		}
		else if ( ievt.getListaCampos() == lcDet ) {
			focusCodprod();
		}
	}

	public void beforePost( PostEvent pevt ) {
	
		PreparedStatement psTipoMov = null;
		ResultSet rsTipoMov = null;
	
		if ( pevt.getListaCampos() == lcCampos ) {
			if ( podeReCalcPreco() && lcDet.getStatus() == ListaCampos.LCS_READ_ONLY ) {
				calcVlrItem( "VDVENDA", true );
				lcDet.carregaDados();
				calcImpostos( true );
				lcDet.edit();
				lcDet.post();
			}
			setReCalcPreco( false );
		}
		if ( ( pevt.getListaCampos() == lcCampos ) && ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) ) {
			if ( txtESTipoMov.getVlrString().equals( "E" ) ) {
				if ( Funcoes.mensagemConfirma( this, "Este movimento irá realizar entradas no estoque.\n" + "Deseja continuar?\n" ) != 0 ) {
					pevt.cancela();
					return;
				}
			}
			if ( !testaPgto() ) {
				if ( Funcoes.mensagemConfirma( this, "Cliente com duplicatas em aberto! Continuar?" ) != 0 ) {
					pevt.cancela();
					return;
				}
			}
			if ( (Boolean) oPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				try {
					psTipoMov = con.prepareStatement( "SELECT CODTIPOMOV,DESCTIPOMOV FROM EQTIPOMOV WHERE " + "CODEMP=? AND CODFILIAL=? AND CODTIPOMOV=? AND FISCALTIPOMOV='N'" );
					psTipoMov.setInt( 1, Aplicativo.iCodEmp );
					psTipoMov.setInt( 2, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
					psTipoMov.setInt( 3, txtCodTipoMov.getVlrInteger().intValue() );
					rsTipoMov = psTipoMov.executeQuery();
					if ( rsTipoMov.next() ) {
						if ( rsTipoMov.getInt( "CODTIPOMOV" ) != txtCodTipoMov.getVlrInteger().intValue() ) {
							Funcoes.mensagemInforma( this, "Tipo de movimento não permitido na inserção!" );
							pevt.cancela();
							return;
						}
					}
					else {
						Funcoes.mensagemInforma( this, "Tipo de movimento não permitido na inserção!" );
						pevt.cancela();
						return;
					}
					con.commit();
					rsTipoMov.close();
					psTipoMov.close();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao pesquisar tipo de movimento!\n" + err.getMessage(), true, con, err );
					pevt.cancela();
				}
			}
			txtStatusVenda.setVlrString( "*" );
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			if ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
				if ( txtQtdItVenda.getVlrBigDecimal().floatValue() <= 0 ) {
					Funcoes.mensagemInforma( this, "Quantidade invalida!" );
					pevt.cancela();
					return;
				}
				if ( numComissionados > 0 ) {
					if ( !consisteComisObrig() ) {
	
						StringBuffer mens = new StringBuffer();
	
						mens.append( "Não é possível inserir o item!\n" );
						mens.append( "Existem comissionados obrigatórios não informados.\n" );
						mens.append( "Deseja informar os comissionados agora?\n" );
	
						if ( Funcoes.mensagemConfirma( this, mens.toString() ) == JOptionPane.YES_OPTION ) {
							abreComissVend();
							if( !consisteComisObrig() ){
								pevt.cancela();
							}
						}
						else {
							pevt.cancela();
						}
					}
				}
				if ( !(Boolean) oPrefs[ POS_PREFS.USAPRECOZERO.ordinal() ] && txtPrecoItVenda.getVlrBigDecimal().floatValue() <= 0 ) {
					Funcoes.mensagemInforma( this, "Preço inválido!" );
					pevt.cancela();
					return;
				}
				if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
					if ( !testaCodLote() ) {
						pevt.cancela();
					}
				}
				if ( txtCodProd.getVlrInteger().intValue() > 0 ) {
					if ( !testaLucro() ) {
						Funcoes.mensagemInforma( this, "Não é permitido a venda deste produto abaixo do custo!!!" );
						pevt.cancela();
					}
				}
	
				// Verificação de crédito
	
				if ( (Boolean) oPrefs[ POS_PREFS.CONS_CRED_ITEM.ordinal() ] ) { // Verifica se deve consultar crédito na inserção do ítem;
					if ( !consultaCredito( txtVlrLiqItVenda.getVlrBigDecimal(), false ) ) {
						pevt.cancela();
					}
				}				
				if ( (Boolean) oPrefs[ POS_PREFS.INFCPDEVOLUCAO.ordinal() ] && "DV".equals( txtTipoMov.getVlrString() ) ) {
					abreBuscaCompra();				
				}
				if ( (Boolean) oPrefs[ POS_PREFS.INFVDREMESSA.ordinal() ] && ! "VR".equals( txtTipoMov.getVlrString() )  ) {
					abreBuscaRemessa();				
				}
	
				calcDescIt();
				calcComisIt();
			}
			
			if ( (Boolean) oPrefs[ POS_PREFS.VENDAIMOBILIZADO.ordinal()] && txtTipoProd.getVlrString().equals( "O" ) ) {
				FPassword pass = new FPassword( this, FPassword.VENDA_IMOBLIZIADO, "Venda imobilizado", con );
				pass.setVisible( true );
				if ( !pass.OK ) {
					pevt.cancela();
				}
				pass.dispose();
			}
		}
		txtTipoVenda.setVlrString( "V" );
	}

	public void afterPost( PostEvent pevt ) {
	
		lcVenda2.carregaDados(); // Carrega os Totais
		if ( pevt.getListaCampos() == lcCampos ) {
			if ( (Boolean) oPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "S" );
				txtFiscalTipoMov2.setText( "N" );
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProd2 ) {
			lcProd.edit();
		}

		/*
		 * if (lcCampos.getStatus() != ListaCampos.LCS_INSERT) { 
		 * // Cancela os auto-incrementos que sobrepõem o que está guardado na tabela venda 
		 * if (cevt.getListaCampos() == lcVendedor) { 
		 * lcVendedor.cancLerCampo(2, true); //Comissão do vendedor; 
		 * } else if ( cevt.getListaCampos() == lcCli) { 
		 * lcCli.cancLerCampo(2, true); //Código de Pagamento 
		 * lcCli.cancLerCampo(3, true); //Código do Vendador 
		 * } 
		 * } else { 
		 * if (cevt.getListaCampos() == lcVendedor) { //Ativa auto-incrementos 
		 * lcVendedor.cancLerCampo(2, false); //Comissão do vendedor; 
		 * } else if (cevt.getListaCampos() == cCli) { 
		 * lcCli.cancLerCampo(2, false); //Código do Pagamento 
		 * lcCli.cancLerCampo(3, false); //Código do Vendedor 
		 * } 
		 * } 
		 * Por que faz a mesma coisa no if e no else?
		 */
		if ( cevt.getListaCampos() == lcVendedor ) {// Ativa auto-incrementos
			lcVendedor.cancLerCampo( 2, false ); // Comissão do vendedor;
			if ( !isComissAtivo() ) { // Verifica se o comissionado é ativo.
				Funcoes.mensagemInforma( this, "Comissionado Inativo!" );
			}
		}
		else if ( cevt.getListaCampos() == lcCli ) {
			lcCli.cancLerCampo( 2, false ); // Código do Pagamento
			lcCli.cancLerCampo( 3, false ); // Código do Vendedor
		}

		if ( lcDet.getStatus() != ListaCampos.LCS_INSERT ) {
			if ( cevt.getListaCampos() == lcProd ) {
				lcProd.cancLerCampo( 5, true ); // Código da Classificação Fiscal
			}
		}
		else {
			if ( cevt.getListaCampos() == lcProd ) {
				lcProd.cancLerCampo( 5, false ); // Código da Classificação Fiscal
			}
		}

		if ( cevt.getListaCampos() == lcCampos ) {
			if ( (Boolean) oPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "S" );
				txtFiscalTipoMov2.setText( "N" );
			}
			if ( (Boolean) oPrefs[ POS_PREFS.OBSCLIVEND.ordinal() ] ) {
				iCodCliAnt = txtCodCli.getVlrInteger().intValue();
			}
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		try {
			if ( cevt.getListaCampos() == lcProd || cevt.getListaCampos() == lcProd2 ) {
				if ( txtCLoteProd.getText().trim().equals( "N" ) ) {
					txtCodLote.setAtivo( false );// Desativa o Cógigo do lote por o produto não possuir lote
				}
				else if ( txtCLoteProd.getText().trim().equals( "S" ) ) {
					txtCodLote.setAtivo( true );// Ativa o Cógigo do Lote pois o produto tem lote
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						getLote();
					}
				}
				if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
					calcVlrItem( null, false );
					getICMS();
				}
			}
			else if ( cevt.getListaCampos() == lcTipoMov ) {
				habilitaMultiComis();
			}
			else if ( cevt.getListaCampos() == lcFisc && lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
				getCFOP();
				getTratTrib();
			} 
			else if ( cevt.getListaCampos() == lcNat ) {
				if ( cevt.ok && lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
					getICMS();
				}
			}
			else if ( cevt.getListaCampos() == lcDet ) {
				lcVenda2.carregaDados();// Carrega os Totais
				atualizaLucratividade();
			}
			else if ( cevt.getListaCampos() == lcCampos ) {
				String codvenda = txtCodVenda.getVlrString();
				lcVenda2.carregaDados();// Carrega os Totais
				txtCodVenda.setVlrString( codvenda );
				codvenda = null;			
			}
			else if ( cevt.getListaCampos() == lcCli ) {
				if ( (Boolean) oPrefs[ POS_PREFS.OBSCLIVEND.ordinal() ] ) {
					if ( iCodCliAnt != txtCodCli.getVlrInteger().intValue() ) {
						iCodCliAnt = txtCodCli.getVlrInteger().intValue();
						mostraObsCli( iCodCliAnt, new Point( this.getX(), this.getY() + tpnCab.getHeight() + pnCab.getHeight() ), new Dimension( spTab.getWidth(), spTab.getHeight() ) );
					}
				}
				if ( (Boolean) oPrefs[ POS_PREFS.RECALCCPVENDA.ordinal() ] ) {
					setReCalcPreco( true );
				}
				if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT || ( lcCampos.getStatus() == ListaCampos.LCS_EDIT && txtCodCli.getVlrInteger() != iCodCliAnt ) ) {
					carregaPrefTipoFiscCli();
				}
			}
			else if ( cevt.getListaCampos() == lcPlanoPag ) {
				if ( (Boolean) oPrefs[ POS_PREFS.RECALCCPVENDA.ordinal() ] ) {
					setReCalcPreco( true );
				}
			}

			if ( txtStatusVenda.getVlrString().trim().length() > 0 && txtStatusVenda.getVlrString().substring( 0, 1 ).equals( "C" ) ) {
				lbStatus.setText( "  CANCELADA" );
				lbStatus.setBackground( Color.RED );
				lbStatus.setVisible( true );
			}
			else if ( getVendaBloqueada() ) {
				lbStatus.setText( "  BLOQUEADA" );
				lbStatus.setBackground( Color.BLUE );
				lbStatus.setVisible( true );
			}
			else if ( txtStatusVenda.getVlrString().trim().length() > 0 && ( txtStatusVenda.getVlrString().trim().equals( "V2" ) || txtStatusVenda.getVlrString().trim().equals( "V3" ) ) ) {
				lbStatus.setText( "  NF EMITIDA" );
				lbStatus.setBackground( new Color( 45, 190, 60 ) );
				lbStatus.setVisible( true );
			}
			else {
				lbStatus.setVisible( false );
			}


		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void beforeDelete( DeleteEvent devt ) { }

	public void afterDelete( DeleteEvent devt ) {

		if ( devt.getListaCampos() == lcDet ) {
			lcVenda2.carregaDados();
		}
	}

	public void actionPerformed( ActionEvent evt ) {
	
		if ( evt.getSource() == btFechaVenda ) { // xxx
			if ( lcCampos.carregaDados() ) {
				fechaVenda();
			}
		}
		else if ( evt.getSource() == btConsPgto ) {
			DLConsultaPgto dl = new DLConsultaPgto( this, con, txtCodCli.getVlrInteger().intValue() );
			dl.setVisible( true );
			dl.dispose();
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( true, txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false, txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btObs ) {
			mostraObs( "VDVENDA", txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btBuscaOrc ) {
			abreAdicOrc();
		}
		else if ( evt.getSource() == btAltComis ) {
			altComisVend();
		}
		else if ( evt.getSource() == btComiss ) {
			abreComissVend();
		}
	
		super.actionPerformed( evt );
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = true;
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_O ) {
			if ( bCtrl ) {
				btObs.doClick();
			}
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( kevt.getSource() == txtPercComisVenda || ( kevt.getSource() == txtClasComis && !txtPercComisVenda.getAtivo() ) ) {// Como estes são os ultimos campos
				// do painel de venda executa-se o
				// post no venda e pula para o campo
				// adequado no item.
				if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
					focusIni();
					focusCodprod();
					lcCampos.post();
					lcDet.edit();
				}
				else if ( lcCampos.getStatus() == ListaCampos.LCS_EDIT ) {
					lcCampos.post();
					focusCodprod();
				}
			}
			else if ( kevt.getSource() == txtPedCliVenda ) {// Como este é o
				// ultimo campo da
				// aba de venda
				// então abre a tab
				// comissao.
				tpnCab.setSelectedIndex( 1 );
				tpnCab.doLayout();
				txtCodVend.requestFocus();
			}
			else if ( kevt.getSource() == txtUltCamp ) {// Como este é o ultimo
				// campo do painel de
				// itvenda executa-se o
				// post no itvenda e
				// pula para o campo
				// adequado no item.
				if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
					lcDet.post();
					lcDet.limpaCampos( true );
					lcDet.setState( ListaCampos.LCS_NONE );
					lcDet.edit();
					focusCodprod();
				}
				else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
					lcDet.post();
					txtCodItVenda.requestFocus();
				}
			}
			else if ( kevt.getSource() == txtCodNat ) {// Talvez este possa ser
				// o ultimo campo por
				// isso o teste.
				if ( !txtBaseICMSItVenda.getAtivo() ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						lcDet.edit();
						focusCodprod();
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItVenda.requestFocus();
					}
				}
			}
			else if ( kevt.getSource() == txtRefProd || kevt.getSource() == txtCodProd ) {
				if ( kevt.getSource() == txtRefProd ) {
					lcDet.edit();
				}
				if ( getProdUsaReceita() ) {
					FPassword pass = new FPassword( this, FPassword.APROV_RECEITA_PROD, "Receita", con );
					pass.setVisible( true );
					if ( !pass.OK ) {
						lcDet.cancel( true );
					}
					pass.dispose();
				}				
			}
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F3 && kevt.getSource() == txtVlrDescItVenda ) {
			mostraTelaDescont();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F4 ) {
			btFechaVenda.doClick();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F5 ) {
			btConsPgto.doClick();
		}

		if(kevt.getKeyCode() == KeyEvent.VK_F12 && ( ("S".equals( permusu.get( "VISUALIZALUCR" )) && (Boolean) oPrefs[ POS_PREFS.VISUALIZALUCR.ordinal() ] ) ) ) {
			DLAltFatLucro dl = new DLAltFatLucro(this, fatLucro);
			dl.setVisible( true );
			if ( dl.OK ) {
				fatLucro = dl.getValor();
				dl.dispose();
			}

			dl.dispose();
			atualizaLucratividade();
		}
		
		
		
		super.keyPressed( kevt );
	}

	public void keyTyped( KeyEvent kevt ) {

		super.keyTyped( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = false;
		}

		super.keyReleased( kevt );
	}

	public void focusGained( FocusEvent fevt ) {

		if ( fevt.getSource() == txtCodCli ) {
			iCodCliAnt = txtCodCli.getVlrInteger();
		}
	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescItVenda ) {
			if ( txtPercDescItVenda.getText().trim().length() < 1 ) {
				txtVlrDescItVenda.setAtivo( true );
			}
			else {
				calcDescIt();
				calcVlrProd();
				calcImpostos( true );
				txtVlrDescItVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtPercComItVenda ) {
			if ( txtPercComItVenda.getText().trim().length() < 1 ) {
				txtVlrComisItVenda.setAtivo( true );
			}
			else {
				calcComisIt();
				calcVlrProd();
				calcImpostos( true );
				txtVlrComisItVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtVlrDescItVenda ) {
			if ( txtVlrDescItVenda.getText().trim().length() < 1 ) {
				txtPercDescItVenda.setAtivo( true );
			}
			else if ( txtVlrDescItVenda.getAtivo() ) {
				txtPercDescItVenda.setAtivo( false );
			}
			calcDescIt();
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( fevt.getSource() == txtVlrComisItVenda ) {
			if ( txtVlrComisItVenda.getText().trim().length() < 1 ) {
				txtPercComItVenda.setAtivo( true );
			}
			else if ( txtVlrComisItVenda.getAtivo() ) {
				txtPercComItVenda.setAtivo( false );
			}
			calcComisIt();
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( ( fevt.getSource() == txtQtdItVenda ) | ( fevt.getSource() == txtPrecoItVenda ) | ( fevt.getSource() == txtCodNat ) ) {
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( ( fevt.getSource() == txtPercICMSItVenda ) | ( fevt.getSource() == txtAliqIPIItVenda ) ) {
			calcImpostos( false );
		}
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		setNfecf( new NFEConnectionFactory( cn ) );
		permusu = getPermissaoUsu();
		montaTela();
		lcTratTrib.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcModNota.setConexao( cn );
		lcCli.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcSerie.setConexao( cn );
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		lcNat.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcLote.setConexao( cn );
		lcFisc.setConexao( cn );
		lcVenda2.setConexao( cn );
		lcClComis.setConexao( cn );
		lcItCompra.setConexao( cn );
		lcItRemessa.setConexao( cn );
	}
}
