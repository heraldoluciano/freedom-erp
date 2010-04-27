/*
 * Projeto: Freedom Pacote: org.freedom.modules.std Classe: @(#)FCompra.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR> <BR>
 */

package org.freedom.modulos.gms.view.frame.crud.detail;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.business.component.NFEntrada;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.component.Layout;
import org.freedom.library.component.Leiaute;
import org.freedom.library.functions.EmailBean;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FObservacao;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modules.nfe.control.AbstractNFEFactory;
import org.freedom.modulos.gms.business.object.TipoMov;
import org.freedom.modulos.gms.view.dialog.utility.DLBuscaPedCompra;
import org.freedom.modulos.gms.view.dialog.utility.DLLote;
import org.freedom.modulos.gms.view.dialog.utility.DLSerie;
import org.freedom.modulos.gms.view.dialog.utility.DLSerieGrid;
import org.freedom.modulos.nfe.database.jdbc.NFEConnectionFactory;
import org.freedom.modulos.pcp.view.dialog.utility.DLFinalizaOP;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.DLCodProd;
import org.freedom.modulos.std.view.dialog.report.DLRPedido;
import org.freedom.modulos.std.view.dialog.report.DLRetRemessa;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaDescProd;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;
import org.freedom.modulos.std.view.dialog.utility.DLFechaCompra;
import org.freedom.modulos.std.view.dialog.utility.DLGuiaTraf;


/**
 * Tela para cadastro de notas fiscais de compra.
 * 
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva (14/07/2003)
 * @version 31/08/2009 - Alex Rodrigues
 * @version 16/12/2009 - Anderson Sanchez
 * @version 10/03/2010 - Anderson Sanchez
 * @version 22/03/2010 - Anderson Sanchez
 */

public class FCompra extends FDetalhe implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private String codProdutoFornecedor;

	private int casasDecFin = Aplicativo.casasDecFin;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JPanelPad pinCabCompra = new JPanelPad();

	private JPanelPad pinCabTransp = new JPanelPad();

	private JPanelPad pinCabObs01 = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinCabObs02 = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinCabObs03 = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinCabObs04 = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinCabSolCompra = new JPanelPad();

	private JPanelPad pinCabImportacao = new JPanelPad();

	private JPanelPad pinTot = new JPanelPad( 200, 200 );

	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JPanelPad pnTot = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JButtonPad btFechaCompra = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btObs = new JButtonPad( Icone.novo( "btObs.gif" ) );

	private JButtonPad btBuscarRemessa = new JButtonPad( "Buscar remessa", Icone.novo( "btExecuta.gif" ) );

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtEmitCompra = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtEntCompra = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtQtdItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtSerieProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtPrecoItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtPercDescItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrDescItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtPercComItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtCalcTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodNat = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtVlrBaseICMSItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtPercICMSItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrICMSItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrLiqItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtNumSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtObsSerie = new JTextFieldFK( JTextFieldPad.TP_STRING, 150, 0 );
	
	private JTextFieldPad txtCodAlmoxProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtTipoFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtTpRedIcmsFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCustoPEPSProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCustoMPMProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtVlrIPICompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrDescCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrProdItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtVlrBaseIPIItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtAliqIPIItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrIPIItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtCustoItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrBrutCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtSerieCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDocSerie = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 ); // Tem que ter esse campo para não gerar N.de documento automático

	private JTextFieldPad txtDocCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtStatusCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtEstFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtEmailFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescNat = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAlmoxItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodSol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtChaveNfe = new JTextFieldPad( JTextFieldPad.TP_STRING, 44, 0 );

	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtEmitCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtTipoModNota = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextAreaPad txaObsItCompra = new JTextAreaPad( 500 );

	private JTextFieldPad txtCodPaisDesembDI = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPaisDesembDI = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaUFDesembDI = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNomeUFDEsembDI = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtNroDI = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtRegDI = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtDesembDI = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtIdentContainer = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtLocDesembDI = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private JLabelPad lbStatus = new JLabelPad();
	
	private JLabelPad lbCodLote = new JLabelPad();
	
	private JLabelPad lbNumSerie = new JLabelPad();

	private JCheckBoxPad cbSeqNfTipoMov = new JCheckBoxPad( "Aloc.NF", "S", "N" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcSerie = new ListaCampos( this, "SE" );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private ListaCampos lcProd2 = new ListaCampos( this, "PD" );

	private ListaCampos lcNat = new ListaCampos( this, "NT" );

	private ListaCampos lcLote = new ListaCampos( this, "LE" );

	private ListaCampos lcNumSerie = new ListaCampos( this, "NS" );
	
	private ListaCampos lcFisc = new ListaCampos( this );

	private ListaCampos lcCompra2 = new ListaCampos( this );

	private ListaCampos lcAlmoxItem = new ListaCampos( this, "AX" );

	private ListaCampos lcAlmoxProd = new ListaCampos( this, "AX" );

	private ListaCampos lcSolCompra = new ListaCampos( this, "SOL" );

	private ListaCampos lcModNota = new ListaCampos( this, "MN" );

	private final ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcPais = new ListaCampos( this, "" );

	private ListaCampos lcUF = new ListaCampos( this );

	private String sOrdNota = "";

	private boolean comref = false;

	private boolean buscagenericaprod = false;

	private boolean podeBloq = false;

	private boolean buscaVlrUltCompra = false;

	private boolean habconvcp = false;

	private boolean habilitaCusto = false;
	
	private String abaTransp = "N";

	private String abaSolCompra = "N";

	private String abaImport = "N";

	private String classcp = "";

	private String labelobs01cp = "";

	private String labelobs02cp = "";

	private String labelobs03cp = "";

	private String labelobs04cp = "";

	private JTextAreaPad txaObs01 = new JTextAreaPad();

	private JTextAreaPad txaObs02 = new JTextAreaPad();

	private JTextAreaPad txaObs03 = new JTextAreaPad();

	private JTextAreaPad txaObs04 = new JTextAreaPad();

	private JScrollPane spnObs01 = new JScrollPane( txaObs01 );

	private JScrollPane spnObs02 = new JScrollPane( txaObs02 );

	private JScrollPane spnObs03 = new JScrollPane( txaObs03 );

	private JScrollPane spnObs04 = new JScrollPane( txaObs04 );
	
	private JButtonPad btBuscaCompra = new JButtonPad( "Busca Pedido", Icone.novo( "btEntrada.png" ) );

	private JLabelPad lbChaveNfe = null;

	private boolean novo = false;
	
	private JPanelPad pnAdicionalCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 4 ) );

	private NFEConnectionFactory nfecf = null;

	private enum PROCEDUREOP {
		TIPOPROCESS, CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC, CODFILIALOC, CODORC, TIPOORC, CODITORC, QTDSUGPRODOP, DTFABROP, SEQEST, CODEMPET, CODFILIALET, CODEST, AGRUPDATAAPROV, AGRUPDTFABROP, AGRUPCODCLI, CODEMPCL, CODFILIALCL, CODCLI, DATAAPROV, CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA, JUSTFICQTDPROD, CODEMPPDENTRADA, CODFILIALPDENTRADA, CODPRODENTRADA, QTDENTRADA
	}

	public FCompra() {

		setTitulo( "Compra" );
		setAtribos( 15, 10, 770, 500 );
		
	}

	public void montaTela() {
		
		adicAbas();

		adicPaineis();

		adicListaCampos();
		
		adicToolTips();
		
		

		txtVlrIPICompra.setAtivo( false );
		txtVlrDescCompra.setAtivo( false );
		txtVlrLiqCompra.setAtivo( false );

		pinCab = new JPanelPad( 740, 130 );
		setListaCampos( lcCampos );
		setAltCab( 160 );
		setPainel( pinCabCompra );

		adicCampo( txtCodCompra, 7, 20, 80, 20, "CodCompra", "Nº Compra", ListaCampos.DB_PK, true );
		adicCampo( txtCodTipoMov, 90, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 170, 20, 247, 20, "DescTipoMov", "Descrição do tipo de movimento" );
		adicCampo( txtSerieCompra, 420, 20, 77, 20, "Serie", "Série", ListaCampos.DB_FK, true );
		adicCampo( txtDocCompra, 500, 20, 77, 20, "DocCompra", "Doc", ListaCampos.DB_SI, true );
		adicCampo( txtDtEmitCompra, 580, 20, 75, 20, "DtEmitCompra", "Dt.emissão", ListaCampos.DB_SI, true );
		adicCampo( txtDtEntCompra, 658, 20, 75, 20, "DtEntCompra", "Dt.entrada", ListaCampos.DB_SI, true );
		adicCampo( txtCodFor, 7, 60, 80, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtDescFor, true );
		adicDescFK( txtDescFor, 90, 60, 327, 20, "RazFor", "Razão social do fornecedor" );
		adicCampo( txtCodPlanoPag, 420, 60, 77, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, true );
		adicDescFK( txtDescPlanoPag, 500, 60, 233, 20, "DescPlanoPag", "Descrição do p.pagto." );
		lbChaveNfe = adicCampo( txtChaveNfe, 7, 100, 410, 20, "ChaveNfeCompra", "", ListaCampos.DB_SI, false );

		adicDBLiv( txaObs01, "Obs01", labelobs01cp == null ? "Observações" : labelobs01cp, false );
		adicDBLiv( txaObs02, "Obs02", labelobs01cp == null ? "Observações" : labelobs01cp, false );
		adicDBLiv( txaObs03, "Obs03", labelobs01cp == null ? "Observações" : labelobs01cp, false );
		adicDBLiv( txaObs04, "Obs04", labelobs01cp == null ? "Observações" : labelobs01cp, false );

		if ( abaTransp.equals( "S" ) ) {
			setListaCampos( lcCampos );
			setPainel( pinCabTransp );
			adicCampo( txtCodTran, 7, 25, 70, 20, "Codtran", "Cód.transp.", ListaCampos.DB_FK, false );
			adicDescFK( txtRazTran, 80, 25, 250, 20, "Raztran", "Razão social da transportadora" );
		}

		if ( "S".equals( abaImport ) ) {

			setPainel( pinCabImportacao );

			adicCampo( txtNroDI, 7, 25, 85, 20, "NroDI", "Nro. da DI", ListaCampos.DB_SI, false );
			adicCampo( txtDtRegDI, 95, 25, 70, 20, "DtRegDI", "Dt.reg. DI", ListaCampos.DB_SI, false );
			adicCampo( txtDtDesembDI, 168, 25, 70, 20, "DtDesembDI", "Dt.desemb.", ListaCampos.DB_SI, false );
			adicCampo( txtIdentContainer, 241, 25, 150, 20, "IdentContainer", "Identificação do container", ListaCampos.DB_SI, false );

			adicCampo( txtCodPaisDesembDI, 394, 25, 70, 20, "CodPaisDesembDI", "Cod.país", ListaCampos.DB_FK, txtDescPaisDesembDI, false );
			adicDescFK( txtDescPaisDesembDI, 467, 25, 227, 20, "NomePais", "Nome do país" );

			adicCampo( txtLocDesembDI, 7, 65, 384, 20, "LocDesembDI", "Local do desembaraço", ListaCampos.DB_SI, false );

			adicCampo( txtSiglaUFDesembDI, 394, 65, 70, 20, "SiglaUfDesembDI", "Sigla UF", ListaCampos.DB_FK, txtNomeUFDEsembDI, false );
			adicDescFK( txtNomeUFDEsembDI, 467, 65, 227, 20, "NomeUF", "Nome UF" );

		}

		adicCampoInvisivel( txtCalcTrib, "CalcTrib", "Calculo de tributos", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false );

		setListaCampos( true, "COMPRA", "CP" );
		lcCampos.setQueryInsert( false );

		if ( "S".equals( abaSolCompra ) ) {
			setListaCampos( lcSolCompra );
			setPainel( pinCabSolCompra );
			adicCampo( txtCodSol, 7, 25, 70, 20, "CodSol", "Cód.sol.", ListaCampos.DB_FK, false );
			adicCampo( txtIDUsu, 451, 20, 80, 20, "IdUsu", "Id do usuário", ListaCampos.DB_FK, true );
			adicCampo( txtDtEmitSolicitacao, 539, 20, 86, 20, "DtEmitSol", "Data da Sol.", ListaCampos.DB_SI, true );
			adicDescFKInvisivel( txtDescCC, "DescCC", "Descrição do centro de custos" );
			adicCampo( txtCodCC, 80, 20, 130, 20, "CodCC", "Cód.CC.", ListaCampos.DB_FK, txtDescCC, true );
			adicCampo( txtAnoCC, 213, 20, 70, 20, "AnoCC", "Ano CC.", ListaCampos.DB_FK, true );
			adicDescFK( txtDescCC, 286, 20, 162, 20, "DescCC", "Descrição do centro de custos" );

			txtCodSol.setNaoEditavel( true );
			txtIDUsu.setNaoEditavel( true );
			txtDtEmitSolicitacao.setNaoEditavel( true );
			txtDescCC.setNaoEditavel( true );
			txtCodCC.setNaoEditavel( true );
			txtAnoCC.setNaoEditavel( true );
		}

		
		adicListeners();

		setImprimir( true );

	}
	
	private void adicToolTips() {
		btFechaCompra.setToolTipText( "Fechar a Compra (F4)" );
		txtDescProd.setToolTipText( "Clique aqui duas vezes para alterar a descrição." );
	}
	
	private void adicListaCampos() {
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodModNota, "CodModNota", "Código do modelo de nota", ListaCampos.DB_FK, false ) );
		lcTipoMov.add( new GuardaCampo( txtEmitCompra, "EmitNFCpMov", "Emite NF", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtTipoMov, "TipoMov", "Tipo mov.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( cbSeqNfTipoMov, "SeqNfTipomov", "Aloc.NF", ListaCampos.DB_SI, true ) );
		lcTipoMov.setWhereAdic( "((ESTIPOMOV = 'E') AND" + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=" + Aplicativo.iCodEmp
				+ " AND TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) )" );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );

		lcSerie.add( new GuardaCampo( txtSerieCompra, "Serie", "Série", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDocSerie, "DocSerie", "Doc", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtSerieCompra.setTabelaExterna( lcSerie );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtEstFor, "UFFor", "UF", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtEmailFor, "EmailFor", "Email", ListaCampos.DB_SI, false ) );

		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFor );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('C','A')" );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtRazTran, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran );

		lcSolCompra.add( new GuardaCampo( txtCodSol, "CodSol", "Cód.sol.", ListaCampos.DB_PK, false ) );
		lcSolCompra.add( new GuardaCampo( txtIDUsu, "IDUsu", "Cód.Usu.", ListaCampos.DB_SI, false ) );
		lcSolCompra.add( new GuardaCampo( txtDtEmitSolicitacao, "Dt.Emit.Solicitacao", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcSolCompra.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.CC", ListaCampos.DB_SI, false ) );
		lcSolCompra.add( new GuardaCampo( txtDescCC, "DescCC", "Desc.CC", ListaCampos.DB_SI, false ) );
		lcSolCompra.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.CC", ListaCampos.DB_SI, false ) );
		lcSolCompra.montaSql( false, "SOLICITACAO", "CP" );
		lcSolCompra.setQueryCommit( false );
		lcSolCompra.setReadOnly( true );
		txtCodSol.setTabelaExterna( lcSolCompra );

		lcFisc.add( new GuardaCampo( txtCodFisc, "CodFisc", "Código", ListaCampos.DB_PK, false ) );
		lcFisc.add( new GuardaCampo( txtDescFisc, "DescFisc", "Descrição", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtTipoFisc, "TipoFisc", "Tipo", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtTpRedIcmsFisc, "TpRedIcmsFisc", "Tp.red.", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtRedFisc, "RedFisc", "Redução", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtAliqIPIFisc, "AliqIPIFisc", "% IPI", ListaCampos.DB_SI, false ) );
		lcFisc.montaSql( false, "CLFISCAL", "LF" );
		lcFisc.setQueryCommit( false );
		lcFisc.setReadOnly( true );
		txtCodFisc.setTabelaExterna( lcFisc );
		txtDescFisc.setListaCampos( lcFisc );

		lcProd.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, false ) );		
		lcProd.add( new GuardaCampo( txtCodFabProd, "CodFabProd", "Cod.Fabricante", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cod.Barra", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cod.Fiscal", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodUnid, "CodUnid", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodAlmoxProd, "CodAlmox", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtSerieProd, "SerieProd", "C/Série", ListaCampos.DB_SI, false ) );
		
		txtCodUnid.setAtivo( false );
		lcProd.setWhereAdic( "ATIVOPROD='S'" );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );

		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_PK, false ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_SI, false ) );		

		lcProd2.add( new GuardaCampo( txtCodFabProd, "CodFabProd", "Cod.Fabricante", ListaCampos.DB_SI, false ) );		
		lcProd2.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cod.Barras", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.Fisc.", ListaCampos.DB_FK, false ) );
		
		lcProd2.add( new GuardaCampo( txtCodUnid, "CodUnid", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodAlmoxProd, "CodAlmox", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtSerieProd, "SerieProd", "C/Série", ListaCampos.DB_SI, false ) );
		
		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setListaCampos( lcDet );
		lcProd2.setWhereAdic( "ATIVOPROD='S'" );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2 );

		// FK do Lote
		
		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Cód.lote", ListaCampos.DB_PK, false ) );
		lcLote.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcLote.add( new GuardaCampo( txtDescLote, "VenctoLote", "Vencimento", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N", txtCodProd );
		lcLote.setAutoLimpaPK( false );
		lcLote.montaSql( false, "LOTE", "EQ" );
		lcLote.setQueryCommit( false );
		lcLote.setReadOnly( true );
		txtCodLote.setTabelaExterna( lcLote );
		txtDescLote.setListaCampos( lcLote );
		txtDescLote.setNomeCampo( "VenctoLote" );
		txtDescLote.setLabel( "Vencimento" );

		// FK do número de série
		
		lcNumSerie.add( new GuardaCampo( txtNumSerie, "NumSerie", "Num.Série", ListaCampos.DB_PK, false ) );
		lcNumSerie.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcNumSerie.add( new GuardaCampo( txtObsSerie, "ObsSerie", "Observações", ListaCampos.DB_SI, false ) );
		lcNumSerie.setDinWhereAdic( "CODPROD=#N", txtCodProd );
		lcNumSerie.setAutoLimpaPK( false );
		lcNumSerie.montaSql( false, "SERIE", "EQ" );
		lcNumSerie.setQueryCommit( false );
		lcNumSerie.setReadOnly( true );
		txtNumSerie.setTabelaExterna( lcNumSerie );
		txtObsSerie.setListaCampos( lcNumSerie );
		txtObsSerie.setNomeCampo( "ObsSerie" );
		txtObsSerie.setLabel( "Observações" );
		
		// FK de Almoxarifado Produto

		lcAlmoxProd.add( new GuardaCampo( txtCodAlmoxProd, "codalmox", "Cod.Almox.", ListaCampos.DB_PK, false ) );
		lcAlmoxProd.montaSql( false, "ALMOX", "EQ" );
		lcAlmoxProd.setQueryCommit( false );
		lcAlmoxProd.setReadOnly( true );
		txtCodAlmoxItCompra.setTabelaExterna( lcAlmoxProd );

		// FK de Almoxarifado Item

		lcAlmoxItem.add( new GuardaCampo( txtCodAlmoxItCompra, "codalmox", "Cod.Almox.", ListaCampos.DB_PK, false ) );
		lcAlmoxItem.montaSql( false, "ALMOX", "EQ" );
		lcAlmoxItem.setQueryCommit( false );
		lcAlmoxItem.setReadOnly( true );
		txtCodAlmoxItCompra.setTabelaExterna( lcAlmoxItem );

		lcNat.add( new GuardaCampo( txtCodNat, "CodNat", "CFOP", ListaCampos.DB_PK, false ) );
		lcNat.add( new GuardaCampo( txtDescNat, "DescNat", "Descrição da CFOP", ListaCampos.DB_SI, false ) );
		lcNat.montaSql( false, "NATOPER", "LF" );
		lcNat.setQueryCommit( false );
		lcNat.setReadOnly( true );
		txtCodNat.setTabelaExterna( lcNat );
		txtDescNat.setListaCampos( lcNat );

		lcCompra2.add( new GuardaCampo( txtCodCompra, "CodCompra", "Código", ListaCampos.DB_PK, false ) );
		lcCompra2.add( new GuardaCampo( txtVlrIPICompra, "VlrIPICompra", "IPI", ListaCampos.DB_SI, false ) );
		lcCompra2.add( new GuardaCampo( txtVlrDescCompra, "VlrDescItCompra", "Desconto", ListaCampos.DB_SI, false ) );
		lcCompra2.add( new GuardaCampo( txtVlrLiqCompra, "VlrLiqCompra", "Geral", ListaCampos.DB_SI, false ) );
		lcCompra2.add( new GuardaCampo( txtVlrBrutCompra, "VlrProdCompra", "Geral", ListaCampos.DB_SI, false ) );
		lcCompra2.montaSql( false, "COMPRA", "CP" );
		lcCompra2.setQueryCommit( false );
		lcCompra2.setReadOnly( true );

		lcModNota.add( new GuardaCampo( txtCodModNota, "CodModNota", "Cód.Mod.Nota", ListaCampos.DB_PK, false ) );
		lcModNota.add( new GuardaCampo( txtTipoModNota, "TipoModNota", "Tipo. Mod. nota", ListaCampos.DB_SI, false ) );
		lcModNota.montaSql( false, "MODNOTA", "LF" );
		lcModNota.setQueryCommit( false );
		lcModNota.setReadOnly( true );
		txtCodModNota.setTabelaExterna( lcModNota );
		txtTipoModNota.setListaCampos( lcModNota );

		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPaisDesembDI, "CodPais", "Cod.país.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtDescPaisDesembDI, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPaisDesembDI.setTabelaExterna( lcPais );

		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUFDesembDI, "SiglaUf", "Sigla", ListaCampos.DB_PK, false ) );
		lcUF.add( new GuardaCampo( txtNomeUFDEsembDI, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcUF.setDinWhereAdic( "CODPAIS = #N", txtCodPaisDesembDI );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUFDesembDI.setTabelaExterna( lcUF );
	}
	
	private void adicPaineis() {
		pnNavCab.add( pnAdicionalCab, BorderLayout.EAST );		
		
		btBuscarRemessa.setVisible( false );
		pnAdicionalCab.add( btBuscarRemessa );

		btBuscaCompra.setPreferredSize( new Dimension( 110, 0 ) );
		pnAdicionalCab.add( btBuscaCompra );
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setBackground( Color.BLACK );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setText( "NÃO SALVO" );

		JPanelPad navEast = new JPanelPad();
		navEast.setPreferredSize( new Dimension( 150, 30 ) );
		navEast.adic( lbStatus, 26, 3, 110, 20 );
		navEast.tiraBorda();
		pnAdicionalCab.add( navEast );

		pnMaster.remove( 2 );
		pnGImp.removeAll();
		pnGImp.setLayout( new GridLayout( 1, 4 ) );
		pnGImp.setPreferredSize( new Dimension( 220, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );
		pnGImp.add( btFechaCompra );
		pnGImp.add( btObs );

		pnTot.setPreferredSize( new Dimension( 140, 200 ) );
		pnTot.add( pinTot );
		pnCenter.add( pnTot, BorderLayout.EAST );
		pnCenter.add( spTab, BorderLayout.CENTER );

		JPanelPad pnLab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnLab.add( new JLabelPad( " Totais:" ) );

		pnMaster.add( pnCenter, BorderLayout.CENTER );
	}
	
	private void adicAbas() {
		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Compra", pinCabCompra );

		if ( "S".equals( abaTransp ) ) {
			tpnCab.addTab( "Tranportadora", pinCabTransp );
		}
		if ( "S".equals( abaSolCompra ) ) {
			tpnCab.addTab( "Solicitação de Compra", pinCabSolCompra );
		}
		if ( "S".equals( abaImport ) ) {
			tpnCab.addTab( "Importação", pinCabImportacao );
		}

		if ( labelobs01cp != null && !"".equals( labelobs01cp.trim() ) ) {
			pinCabObs01.add( spnObs01 );
			tpnCab.addTab( labelobs01cp.trim(), pinCabObs01 );
		}
		if ( labelobs02cp != null && !"".equals( labelobs02cp.trim() ) ) {
			pinCabObs02.add( spnObs02 );
			tpnCab.addTab( labelobs02cp.trim(), pinCabObs02 );
		}
		if ( labelobs03cp != null && !"".equals( labelobs03cp.trim() ) ) {
			pinCabObs03.add( spnObs03 );
			tpnCab.addTab( labelobs03cp.trim(), pinCabObs03 );
		}
		if ( labelobs04cp != null && !"".equals( labelobs04cp.trim() ) ) {
			pinCabObs04.add( spnObs04 );
			tpnCab.addTab( labelobs04cp.trim(), pinCabObs04 );
		}
	}
	
	private void adicListeners() {

		// Mouse Listeners
		
		txtDescProd.addMouseListener( this );
		
		//Action Listeners
		
		btFechaCompra.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btObs.addActionListener( this );
		btBuscarRemessa.addActionListener( this );
		btBuscaCompra.addActionListener( this );
		
		// Focus Listeners
		
		txtPercDescItCompra.addFocusListener( this );
		txtPercComItCompra.addFocusListener( this );
		txtVlrDescItCompra.addFocusListener( this );
		txtQtdItCompra.addFocusListener( this );
		txtCodNat.addFocusListener( this );
		txtPrecoItCompra.addFocusListener( this );
		txtPercICMSItCompra.addFocusListener( this );
		txtVlrIPIItCompra.addFocusListener( this );
		txtCodLote.addFocusListener( this );
		txtNumSerie.addFocusListener( this );
		
		// Key Listeners
		
		txtCodPlanoPag.addKeyListener( this );

		// Carrega Listeners 
		
		lcCampos.addCarregaListener( this );
		lcFor.addCarregaListener( this );
		lcSerie.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		lcProd2.addCarregaListener( this );
		lcFisc.addCarregaListener( this );
		lcNat.addCarregaListener( this );
		lcLote.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcTipoMov.addCarregaListener( this );
		lcAlmoxProd.addCarregaListener( this );
		lcModNota.addCarregaListener( this );
		
		// Insert Listeners
		lcCampos.addInsertListener( this );

		// Post Listeners
		lcCampos.addPostListener( this );
		lcDet.addPostListener( this );
				
	}

	private void redimensionaDet(int alt) {
		
		setAltDet( alt );
		pinDet.setPreferredSize( new Dimension(740, alt) );

	}
	
	private void montaDetalhe() {

		redimensionaDet( 100 );

		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtCodItCompra, 7, 20, 45, 20, "CodItCompra", "Item", ListaCampos.DB_PK, true );

		if ( comref ) {
			txtRefProd.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProd2.getWhereAdic() ) );
			adicCampoInvisivel( txtCodProd, "CodProd", "Cód.Prod.", ListaCampos.DB_FK, txtDescProd, false );
			adicCampoInvisivel( txtRefProd, "RefProd", "Referência", ListaCampos.DB_FK, false );

			adic( new JLabelPad( "Referência" ), 55, 0, 70, 20 );
			adic( txtRefProd, 55, 20, 70, 20 );
			txtRefProd.setFK( true );
		}
		else {
			txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
			adicCampo( txtCodProd, 55, 20, 70, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false );
		}

		txtCustoItCompra.setSoLeitura( !habilitaCusto );

		adicDescFK( txtDescProd, 128, 20, 220, 20, "DescProd", "Descrição do produto" );
		adic( new JLabelPad( "Unid." ), 351, 0, 50, 20 );
		adic( txtCodUnid, 351, 20, 50, 20 );
		adicCampo( txtQtdItCompra, 404, 20, 70, 20, "qtditcompra", "Quant.", ListaCampos.DB_SI, true );

		adicCampoInvisivel( txtCodAlmoxItCompra, "codalmox", "Cod.Almox", ListaCampos.DB_FK, false );

		txtQtdItCompra.setBuscaAdic( new DLBuscaEstoq( lcDet, lcAlmoxItem, lcProd, con, "qtditcompra" ) );

		adicCampo( txtPrecoItCompra, 477, 20, 70, 20, "PrecoItCompra", "Preço", ListaCampos.DB_SI, true );

		adicCampo( txtPercDescItCompra, 550, 20, 50, 20, "PercDescItCompra", "% Desc.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrDescItCompra, 603, 20, 70, 20, "VlrDescItCompra", "Vlr.Desc.", ListaCampos.DB_SI, false );

		adicCampo( txtVlrLiqItCompra, 676, 20, 67, 20, "VlrLiqItCompra", "Valor Item", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodNat, 7, 60, 45, 20, "CodNat", "CFOP", ListaCampos.DB_FK, txtDescNat, true );
		adicDescFK( txtDescNat, 55, 60, 220, 20, "DescNat", "Descrição da CFOP" );
		
		adicCampo( txtVlrBaseICMSItCompra, 278, 60, 70, 20, "VlrBaseICMSItCompra", "B. ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtPercICMSItCompra, 351, 60, 50, 20, "PercICMSItCompra", "% ICMS", ListaCampos.DB_SI, true );
		adicCampo( txtVlrICMSItCompra, 404, 60, 70, 20, "VlrICMSItCompra", "Vlr. ICMS", ListaCampos.DB_SI, false );

		adicCampo( txtVlrBaseIPIItCompra, 477, 60, 70, 20, "VlrBaseIPIItCompra", "B. IPI", ListaCampos.DB_SI, false );
		adicCampo( txtAliqIPIItCompra, 550, 60, 50, 20, "PercIPIItCompra", "% IPI", ListaCampos.DB_SI, false );		
		adicCampo( txtVlrIPIItCompra, 603, 60, 70, 20, "VlrIPIItCompra", "Vlr. IPI", ListaCampos.DB_SI, false );
		
		adicCampoInvisivel( txtVlrProdItCompra, "VlrProdItCompra", "V. Bruto", ListaCampos.DB_SI, false );

		adicCampo( txtCustoItCompra, 676, 60, 67, 20, "CustoItCompra", "Custo", ListaCampos.DB_SI, false );

		adicDBLiv( txaObsItCompra, "ObsItCompra", "Observação", false );

		lbCodLote = adicCampo( txtCodLote, 7, 100, 117, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLote, false );
		lbNumSerie = adicCampo( txtNumSerie, 127, 100, 150, 20, "NumSerieTmp", "Número de série", ListaCampos.DB_FK, txtObsSerie, false );
		
		lbNumSerie.setVisible( false );
		lbCodLote.setVisible( false );
		
		pinTot.adic( new JLabelPad( "Tot. IPI" ), 7, 0, 120, 20 );
		pinTot.adic( txtVlrIPICompra, 7, 20, 120, 20 );
		pinTot.adic( new JLabelPad( "Tot. Desc." ), 7, 40, 120, 20 );
		pinTot.adic( txtVlrDescCompra, 7, 60, 120, 20 );
		pinTot.adic( new JLabelPad( "Total Geral" ), 7, 80, 120, 20 );
		pinTot.adic( txtVlrLiqCompra, 7, 100, 120, 20 );
		txtCodNat.setStrMascara( "#.###" );
		/*
		 * txtRefProd.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent kevt) { lcDet.edit(); } });
		 */
		setListaCampos( true, "ITCOMPRA", "CP" );
		lcDet.setQueryInsert( false );

		montaTab();

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 70, 3 );
		tab.setTamColuna( 80, 4 );
		tab.setTamColuna( 70, 6 );
		tab.setTamColuna( 70, 7 );
		tab.setTamColuna( 60, 8 );
		tab.setTamColuna( 70, 9 );
		tab.setTamColuna( 60, 10 );
		tab.setTamColuna( 70, 11 );
		tab.setTamColuna( 200, 12 );
		tab.setTamColuna( 70, 13 );
		tab.setTamColuna( 60, 14 );
		tab.setTamColuna( 70, 15 );
		tab.setTamColuna( 70, 16 );
		tab.setTamColuna( 60, 17 );
		tab.setTamColuna( 70, 18 );
		tab.setTamColuna( 80, 19 );
		tab.setTamColuna( 90, 20 );

	}

	private void adicIPI() {

		double deVlrProd = Funcoes.arredDouble( txtVlrProdItCompra.doubleValue() - txtVlrDescItCompra.doubleValue() + txtVlrIPIItCompra.doubleValue(), casasDecFin );
		txtVlrLiqItCompra.setVlrBigDecimal( new BigDecimal( deVlrProd ) );
	}

	public void mostraObs( String sTabela, int iCod ) {

		FObservacao obs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQLselect = null;
		String sSQLupdate = null;

		try {

			try {

				if ( sTabela.equals( "CPCOMPRA" ) ) {

					sSQLselect = "SELECT OBSERVACAO FROM CPCOMPRA WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=?";
					sSQLupdate = "UPDATE CPCOMPRA SET OBSERVACAO=? WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=?";
				}

				ps = con.prepareStatement( sSQLselect );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( sTabela ) );
				ps.setInt( 3, iCod );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					obs = new FObservacao( ( rs.getString( 1 ) != null ? rs.getString( 1 ) : "" ) );
				}
				else {
					obs = new FObservacao( "" );
				}

				rs.close();
				ps.close();

				con.commit();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar a observação!\n" + err.getMessage(), true, con, err );
			}
			if ( obs != null ) {

				obs.setVisible( true );

				if ( obs.OK ) {

					try {
						ps = con.prepareStatement( sSQLupdate );
						ps.setString( 1, obs.getTexto() );
						ps.setInt( 2, Aplicativo.iCodEmp );
						ps.setInt( 3, ListaCampos.getMasterFilial( sTabela ) );
						ps.setInt( 4, iCod );
						ps.executeUpdate();

						ps.close();

						con.commit();

					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao inserir observação no orçamento!\n" + err.getMessage(), true, con, err );
					}
				}

				obs.dispose();

			}

		} finally {
			ps = null;
			rs = null;
			sSQLselect = null;
			sSQLupdate = null;
		}

	}

	public void buscaRetornoRemessa() {

		if ( txtCodCompra.getVlrInteger() == 0 ) {
			return;
		}

		DLRetRemessa buscaRemessa = new DLRetRemessa( "VR" );
		buscaRemessa.setConexao( con );
		buscaRemessa.setVisible( true );

		if ( buscaRemessa.OK ) {

			List<DLRetRemessa.GridBuscaRetorno> gridBuscaRemessa = buscaRemessa.getGridBuscaRemessa();

			for ( DLRetRemessa.GridBuscaRetorno g : gridBuscaRemessa ) {

				lcDet.cancel( true );
				lcDet.insert( true );

				txtCodProd.setVlrInteger( g.getCodigoProduto() );

				lcProd.carregaDados();

				txtQtdItCompra.setVlrBigDecimal( g.getSaldo() );
				txtPrecoItCompra.setVlrBigDecimal( g.getPrecoRemessa() );

				calcVlrProd();
				calcImpostos( true );

				lcDet.post();
			}

			lcCampos.carregaDados();
		}

		buscaRemessa.dispose();
	}

	private void bloqCompra() {

		PreparedStatement ps = null;
		String sSQL = null;
		int iCodCompra = 0;
		try {
			iCodCompra = txtCodCompra.getVlrInteger().intValue();
			if ( iCodCompra != 0 ) {
				sSQL = "EXECUTE PROCEDURE CPBLOQCOMPRASP(?,?,?,?)";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				ps.setInt( 3, iCodCompra );
				ps.setString( 4, "S" );
				ps.executeUpdate();
				ps.close();
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro bloqueando a compra!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			sSQL = null;
		}
	}

	private void calcImpostos( boolean bCalcBase ) {

		String tpredicmfisc = txtTpRedIcmsFisc.getVlrString();
		float fRed = txtRedFisc.floatValue();
		float fVlrProd = Funcoes.arredFloat( txtVlrProdItCompra.floatValue() - txtVlrDescItCompra.floatValue(), casasDecFin );
		float fBaseICMS = Funcoes.arredFloat( txtVlrBaseICMSItCompra.floatValue(), casasDecFin );
		float fBaseIPI = txtVlrBaseIPIItCompra.floatValue();

		float fICMS = 0;
		if ( fVlrProd > 0 ) {
			if ( bCalcBase ) {
				if ( "B".equals( tpredicmfisc ) ) {
					fBaseICMS = Funcoes.arredFloat( fVlrProd - ( fVlrProd * fRed / 100 ), casasDecFin );
				}
				else {
					fBaseICMS = Funcoes.arredFloat( fVlrProd, casasDecFin );
				}
				fBaseIPI = fVlrProd;
			}
			if ( ( "V".equals( tpredicmfisc ) ) && ( fRed > 0 ) ) {
				fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItCompra.floatValue() / 100, casasDecFin );
				fICMS -= Funcoes.arredFloat( fICMS * fRed / 100, casasDecFin );
			}
			else {
				fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItCompra.floatValue() / 100, casasDecFin );
			}
		}
		txtVlrICMSItCompra.setVlrBigDecimal( new BigDecimal( fICMS ) );
		if ( bCalcBase ) {
			txtVlrBaseICMSItCompra.setVlrBigDecimal( new BigDecimal( String.valueOf( fBaseICMS ) ) );
			txtVlrBaseIPIItCompra.setVlrBigDecimal( new BigDecimal( String.valueOf( fBaseIPI ) ) );
		}
		txtVlrLiqItCompra.setVlrBigDecimal( new BigDecimal( String.valueOf( fVlrProd ) ) );
		txtAliqIPIItCompra.setVlrBigDecimal( txtAliqIPIFisc.getVlrBigDecimal() );

	}

	private void calcIpi( boolean vlr ) {

		BigDecimal vlripi = null;
		BigDecimal baseipi = null;
		BigDecimal percipi = null;

		try {

			baseipi = txtVlrBaseIPIItCompra.getVlrBigDecimal();
			percipi = txtAliqIPIItCompra.getVlrBigDecimal();
			vlripi = txtVlrIPIItCompra.getVlrBigDecimal();

			if ( baseipi.floatValue() > 0 ) {
				if ( vlr && percipi.floatValue() > 0 ) {
					vlripi = ( baseipi.multiply( percipi ) ).divide( new BigDecimal( 100 ), 5, BigDecimal.ROUND_HALF_EVEN );
					txtVlrIPIItCompra.setVlrBigDecimal( vlripi );
				}
				else if ( vlripi.floatValue() > 0 ) {
					percipi = vlripi.divide( baseipi, 5, BigDecimal.ROUND_HALF_EVEN ).multiply( new BigDecimal( 100 ) );
					txtAliqIPIItCompra.setVlrBigDecimal( percipi );
				}
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void calcVlrProd() {

		BigDecimal preco = txtPrecoItCompra.getVlrBigDecimal();
		BigDecimal qtd = txtQtdItCompra.getVlrBigDecimal();
		BigDecimal vlrtot = qtd.multiply( preco );
		txtVlrProdItCompra.setVlrBigDecimal( vlrtot );
	}

	private void emiteNFE() {

		nfecf.setTpNF( AbstractNFEFactory.TP_NF_IN );

		nfecf.setKey( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "CPCOMPRA" ), txtCodCompra.getVlrInteger(), txtDocCompra.getVlrInteger() );

		nfecf.post();

	}

	private void emiteNotaFiscal( final String sTipo ) {

		if ( ( nfecf.getHasNFE() && "E".equals( txtTipoModNota.getVlrString() ) ) ) {
			emiteNFE();
		}
		else {
			emiteNF( sTipo );
		}
	}

	private void emiteNF( String tipo ) {

		Object layNF = null;
		Vector<Integer> parans = null;
		NFEntrada nf = null;
		String sTipo = tipo;
		boolean bImpOK = false;
		int iCodCompra = txtCodCompra.getVlrInteger().intValue();
		ImprimeOS imp = new ImprimeOS( "", con, sTipo, true );
		imp.verifLinPag( sTipo );
		imp.setTitulo( "Nota Fiscal" );
		DLRPedido dl = new DLRPedido( sOrdNota, false );
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}
		try {
			layNF = Class.forName( "org.freedom.layout.nf." + imp.getClassNota() ).newInstance();
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "Não foi possível carregar o leiaute de Nota Fiscal!\n" + err.getMessage() );
		}
		try {
			if ( layNF != null ) {
				if ( layNF instanceof Layout ) {
					parans = new Vector<Integer>();
					parans.addElement( new Integer( Aplicativo.iCodEmp ) );
					parans.addElement( new Integer( ListaCampos.getMasterFilial( "CPCOMPRA" ) ) );
					parans.addElement( new Integer( iCodCompra ) );
					nf = new NFEntrada( casasDec );
					nf.carregaTabelas( con, parans );
					bImpOK = ( (Layout) layNF ).imprimir( nf, imp );
				}
				else if ( layNF instanceof Leiaute ) {
					Funcoes.mensagemInforma( this, "O layout de Nota Fiscal\nnão se aplica para nota de entrada " );
				}
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao emitir nota de Compra\n!" + err.getMessage(), true, con, err );
		}
		dl.dispose();
		if ( bImpOK )
			imp.preview( this );
		imp.fechaPreview();
	}

	public void exec( int iCodCompra ) {

		txtCodCompra.setVlrString( iCodCompra + "" );
		lcCampos.carregaDados();
	}

	public void execDev( int iCodFor, int iCodTipoMov, String sSerie, int iDoc ) {

		lcCampos.insert( true );
		txtCodFor.setVlrString( iCodFor + "" );
		lcFor.carregaDados();
		txtCodTipoMov.setVlrString( iCodTipoMov + "" );
		lcTipoMov.carregaDados();
		txtSerieCompra.setVlrString( sSerie );
		txtDocCompra.setVlrString( iDoc + "" );
	}

	/**
	 * Busca da Natureza de Operação . Busca a natureza de operação através da tabela de regras fiscais.
	 */
	private void getCFOP() {

		String sSQL = "SELECT CODNAT FROM LFBUSCANATSP (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodFilial );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcProd.getCodFilial() );
			ps.setInt( 4, txtCodProd.getVlrInteger().intValue() );
			ps.setNull( 5, Types.INTEGER );
			ps.setNull( 6, Types.INTEGER );
			ps.setNull( 7, Types.INTEGER );
			ps.setInt( 8, Aplicativo.iCodEmp );
			ps.setInt( 9, lcFor.getCodFilial() );
			ps.setInt( 10, txtCodFor.getVlrInteger().intValue() );
			ps.setInt( 11, lcTipoMov.getCodFilial() );
			ps.setInt( 12, txtCodTipoMov.getVlrInteger().intValue() );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				txtCodNat.setVlrString( rs.getString( "CODNAT" ) );
				lcNat.carregaDados();
			}
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar natureza da operação!\n" + err.getMessage(), true, con, err );
		}
	}

	/**
	 * Busca de icms. Busca a percentagem de ICMS conforme a regra fiscal.
	 */
	private void getICMS() {

		String sSQL = "SELECT PERCICMS FROM LFBUSCAICMSSP(?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCodNat.getVlrString() );
			ps.setString( 2, txtEstFor.getVlrString() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilialMz );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				txtPercICMSItCompra.setVlrBigDecimal( new BigDecimal( rs.getString( 1 ) ) );
			}
			calcImpostos( true );
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar percentual de ICMS!\n" + err.getMessage(), true, con, err );
		}
	}

	private void getCustoProd() {

		try {
			PreparedStatement ps = con.prepareStatement( "SELECT NCUSTOPEPS, NCUSTOMPM FROM EQPRODUTOSP01(?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "EQALMOX" ) );
			ps.setInt( 6, txtCodAlmoxProd.getVlrInteger().intValue() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCustoPEPSProd.setVlrBigDecimal( rs.getBigDecimal( "NCUSTOPEPS" ) == null ? new BigDecimal( 0 ) : rs.getBigDecimal( "NCUSTOPEPS" ) );
				txtCustoMPMProd.setVlrBigDecimal( rs.getBigDecimal( "NCUSTOMPM" ) == null ? new BigDecimal( 0 ) : rs.getBigDecimal( "NCUSTOMPM" ) );

				txtCustoItCompra.setVlrBigDecimal( txtCustoMPMProd.getVlrBigDecimal() );

				/*
				 * if ( ? ) { // Implementar futuramente caso deva utilizar mpm ou peps. txtCustoItCompra.setVlrBigDecimal( txtCustoMPMProd.getVlrBigDecimal() ); } else { txtCustoItCompra.setVlrBigDecimal( txtCustoPEPSProd.getVlrBigDecimal() ); }
				 */
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar custo do produto.", true, con, e );
		}
	}

	private void getPrefere() {

		StringBuffer sql = new StringBuffer();
		try {

			sql.append( "SELECT P1.USAREFPROD,P1.ORDNOTA,P1.BLOQCOMPRA,P1.BUSCAVLRULTCOMPRA,P1.CUSTOCOMPRA, " );
			sql.append( "P1.TABTRANSPCP, P1.TABSOLCP,P1.TABIMPORTCP, P1.CLASSCP, P1.LABELOBS01CP, P1.LABELOBS02CP, " );
			sql.append( "P1.LABELOBS03CP, P1.LABELOBS04CP, P5.HABCONVCP, P1.USABUSCAGENPRODCP " );
			sql.append( "FROM SGPREFERE1 P1 LEFT OUTER JOIN SGPREFERE5 P5 ON " );
			sql.append( "P1.CODEMP=P5.CODEMP AND P1.CODFILIAL=P5.CODFILIAL " );
			sql.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=?" );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {

				comref = rs.getString( "USAREFPROD" ).trim().equals( "S" );
				buscagenericaprod = rs.getString( "USABUSCAGENPRODCP" ).trim().equals( "S" );
				podeBloq = rs.getString( "BLOQCOMPRA" ).trim().equals( "S" );
				buscaVlrUltCompra = rs.getString( "BUSCAVLRULTCOMPRA" ).trim().equals( "S" );
				sOrdNota = rs.getString( "ORDNOTA" );
				habilitaCusto = rs.getString( "CUSTOCOMPRA" ).trim().equals( "S" );
				abaTransp = rs.getString( "TABTRANSPCP" );
				abaSolCompra = rs.getString( "TABSOLCP" );
				abaImport = rs.getString( "TABIMPORTCP" );
				classcp = rs.getString( "CLASSCP" );
				labelobs01cp = rs.getString( "LABELOBS01CP" );
				labelobs02cp = rs.getString( "LABELOBS02CP" );
				labelobs03cp = rs.getString( "LABELOBS03CP" );
				labelobs04cp = rs.getString( "LABELOBS04CP" );
				habconvcp = rs.getString( "HABCONVCP" ) == null ? false : rs.getString( "HABCONVCP" ).equals( "S" );

			}
			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + e.getMessage(), true, con, e );
		}
	}

	private void getVlrUltimaCompra() {

		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT FIRST 1 IT.PRECOITCOMPRA " + "FROM CPCOMPRA C, CPITCOMPRA IT " + "WHERE C.CODEMP=IT.CODEMP AND C.CODFILIAL=IT.CODFILIAL AND C.CODCOMPRA=IT.CODCOMPRA " + "AND IT.CODEMP=? AND IT.CODFILIAL=? AND IT.CODPROD=? " + "ORDER BY C.DTENTCOMPRA DESC, C.CODCOMPRA DESC";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );

			if ( comref ) {
				ps.setInt( 3, txtRefProd.getVlrInteger().intValue() );
			}
			else {
				ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			}

			rs = ps.executeQuery();
			if ( rs.next() )
				txtPrecoItCompra.setVlrBigDecimal( new BigDecimal( rs.getString( 1 ) ) );

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar valor da ultima compra!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimir( boolean bVisualizar, int iCodCompra ) {

		DLRPedido dl = new DLRPedido( sOrdNota, false );
		dl.setConexao( con );
		dl.setVisible( true );
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		if ( dl.OK == false ) {

			dl.dispose();
			return;
		}

		sSQL.append( "SELECT (SELECT COUNT(IC.CODITCOMPRA) FROM CPITCOMPRA IC WHERE IC.CODCOMPRA=C.CODCOMPRA " );
		sSQL.append( "AND IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL), " );
		sSQL.append( "C.CODCOMPRA,C.CODFOR,F.RAZFOR,F.CNPJFOR,F.CPFFOR,C.DTEMITCOMPRA,F.ENDFOR, " );
		sSQL.append( "F.BAIRFOR,F.CEPFOR,C.DTENTCOMPRA,F.CIDFOR,F.UFFOR,F.FONEFOR,F.DDDFONEFOR, " );
		sSQL.append( "F.FAXFOR,F.INSCFOR,F.RGFOR,I.CODPROD,P.REFPROD,P.DESCPROD,P.CODUNID, " );
		sSQL.append( "I.QTDITCOMPRA,I.PRECOITCOMPRA,I.VLRPRODITCOMPRA,I.CODNAT,I.PERCICMSITCOMPRA, " );
		sSQL.append( "PERCIPIITCOMPRA,VLRIPIITCOMPRA,C.VLRBASEICMSCOMPRA,C.VLRICMSCOMPRA,C.VLRPRODCOMPRA, " );
		sSQL.append( "C.VLRDESCCOMPRA,C.VLRDESCITCOMPRA,C.VLRADICCOMPRA,C.VLRIPICOMPRA,F.CONTFOR, C.TIPOFRETECOMPRA, C.VLRFRETECOMPRA, C.OBSERVACAO, " );
		sSQL.append( "C.VLRLIQCOMPRA,C.CODPLANOPAG,PG.DESCPLANOPAG, C.OBS01, C.OBS02, C.OBS03, C.OBS04, " );
		sSQL.append( "SG.LABELOBS01CP, SG.LABELOBS02CP, SG.LABELOBS03CP, SG.LABELOBS04CP " );
		sSQL.append( "FROM CPCOMPRA C, CPFORNECED F,CPITCOMPRA I, EQPRODUTO P, FNPLANOPAG PG, SGPREFERE1 SG " );
		sSQL.append( "WHERE C.CODCOMPRA=? AND C.CODEMP=? AND C.CODFILIAL=? AND " );
		sSQL.append( "F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND F.CODFOR=C.CODFOR AND " );
		sSQL.append( "I.CODEMP=C.CODEMP AND I.CODFILIAL=C.CODFILIAL AND I.CODCOMPRA=C.CODCOMPRA AND " );
		sSQL.append( "P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD AND " );
		sSQL.append( "PG.CODEMP=C.CODEMPPG AND PG.CODFILIAL=C.CODFILIALPG AND PG.CODPLANOPAG=C.CODPLANOPAG AND " );
		sSQL.append( "SG.CODEMP=? AND SG.CODFILIAL=? " );
		sSQL.append( "ORDER BY C.CODCOMPRA,P." + dl.getValor() );

		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, iCodCompra );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			System.out.println( sSQL.toString() );

			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados da compra" );
		}
		if ( dl.getTipo() == "G" ) {

			imprimiGrafico( rs, bVisualizar );

		}
		else {

			imprimeTexto( rs, bVisualizar );
		}

	}

	public void imprimeTexto( final ResultSet rs, final boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int iItImp = 0;
		int iMaxItem = 0;

		try {

			imp.limpaPags();
			iMaxItem = imp.verifLinPag() - 23;

			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.montaCab();
					imp.setTitulo( "Relatório de Pedidos de Compras" );
					imp.impCab( 136, false );

					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 4, "PEDIDO DE COMPRA No.: " );
					imp.say( imp.pRow() + 0, 25, rs.getString( "CodCompra" ) );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 62, "FORNECEDOR" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, "[ Nome/Razao Social ]" );
					imp.say( imp.pRow() + 0, 76, rs.getString( "CpfFor" ) != null ? "[ CPF ]" : "[ CNPJ ]" );
					imp.say( imp.pRow() + 0, 96, "[ Data de Emissão ]" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, rs.getString( "CodFor" ) + " - " + rs.getString( "RazFor" ) );
					imp.say( imp.pRow() + 0, 76, rs.getString( "CpfFor" ) != null ? Funcoes.setMascara( rs.getString( "CpfFor" ), "###.###.###-##" ) : Funcoes.setMascara( rs.getString( "CnpjFor" ), "##.###.###/####-##" ) );
					imp.say( imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate( rs.getDate( "DtEmitCompra" ) ) );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, "[ Endereco ]" );
					imp.say( imp.pRow() + 0, 55, "[ Bairro ]" );
					imp.say( imp.pRow() + 0, 86, "[ CEP ]" );
					imp.say( imp.pRow() + 0, 96, "[ Data de Saída ]" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, rs.getString( "EndFor" ) );
					imp.say( imp.pRow() + 0, 55, rs.getString( "BairFor" ) );
					imp.say( imp.pRow() + 0, 86, Funcoes.setMascara( rs.getString( "CepFor" ), "#####-###" ) );
					imp.say( imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate( rs.getDate( "DtEntCompra" ) ) );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, "[ Municipio ]" );
					imp.say( imp.pRow() + 0, 39, "[ UF ]" );
					imp.say( imp.pRow() + 0, 46, "[ Fone/Fax ]" );
					imp.say( imp.pRow() + 0, 76, rs.getString( "RgFor" ) != null ? "[ RG ]" : "[ Insc. Est. ]" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, rs.getString( "CidFor" ) );
					imp.say( imp.pRow() + 0, 39, rs.getString( "UfFor" ) );
					imp.say( imp.pRow() + 0, 46, ( rs.getString( "DDDFONEFOR" ) != null ? "(" + rs.getString( "DDDFONEFOR" ) + ")" : "" ) + ( rs.getString( "FoneFor" ) != null ? Funcoes.setMascara( rs.getString( "FoneFor" ).trim(), "####-####" ) : "" ).trim() + " - "
							+ Funcoes.setMascara( rs.getString( "FaxFor" ), "####-####" ) );
					imp.say( imp.pRow() + 0, 76, rs.getString( "RgFor" ) != null ? rs.getString( "RgFor" ) : rs.getString( "CnpjFor" ) );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 4, "Referencia" );
					imp.say( imp.pRow() + 0, 18, "Descrição dos Produtos" );
					imp.say( imp.pRow() + 0, 56, "Unidade" );
					imp.say( imp.pRow() + 0, 65, "Quant." );
					imp.say( imp.pRow() + 0, 72, "Valor Unit." );
					imp.say( imp.pRow() + 0, 87, "Valor Total" );
					imp.say( imp.pRow() + 0, 102, "ICM%" );
					imp.say( imp.pRow() + 0, 108, "IPI%" );
					imp.say( imp.pRow() + 0, 114, "Valor do IPI" );
					imp.say( imp.pRow() + 0, 129, "Nat." );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 4, rs.getString( "RefProd" ) );
				imp.say( imp.pRow() + 0, 18, rs.getString( "DescProd" ).substring( 0, 39 ) );
				imp.say( imp.pRow() + 0, 56, rs.getString( "CodUnid" ) );
				imp.say( imp.pRow() + 0, 65, "" + rs.getDouble( "QtdItCompra" ) );
				imp.say( imp.pRow() + 0, 72, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "PrecoItCompra" ) ) );
				imp.say( imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrProdItCompra" ) ) );
				imp.say( imp.pRow() + 0, 102, "" + rs.getDouble( "PercICMSItCompra" ) );
				imp.say( imp.pRow() + 0, 108, "" + rs.getDouble( "PercIPIItCompra" ) );
				imp.say( imp.pRow() + 0, 114, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrIPIItCompra" ) ) );
				imp.say( imp.pRow() + 0, 129, Funcoes.setMascara( rs.getString( "CodNat" ), "#.###" ) );

				iItImp++;

				if ( ( imp.pRow() >= iMaxItem ) | ( iItImp == rs.getInt( 1 ) ) ) {
					if ( ( iItImp == rs.getInt( 1 ) ) ) {
						int iRow = imp.pRow();
						for ( int i = 0; i < ( iMaxItem - iRow ); i++ ) {
							imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
							imp.say( imp.pRow() + 0, 0, "" );
						}
					}

					if ( rs.getInt( 1 ) == iItImp ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 56, "CALCULO DO(S) IMPOSTO(S)" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "[ Base de Calculo ICMS ]" );
						imp.say( imp.pRow() + 0, 29, "[ Valor do ICMS ]" );
						imp.say( imp.pRow() + 0, 54, "[ B. Calc. ICMS Subst. ]" );
						imp.say( imp.pRow() + 0, 79, "[ Valor do ICMS Subst. ]" );
						imp.say( imp.pRow() + 0, 104, "[ Valor dos Produtos ]" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrBaseICMSCompra" ) ) );
						imp.say( imp.pRow() + 0, 29, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrICMSCompra" ) ) );
						imp.say( imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrProdCompra" ) ) );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "[ Valor do Frete ]" );
						imp.say( imp.pRow() + 0, 29, "[ Valor do Desconto ]" );
						imp.say( imp.pRow() + 0, 54, "[ Outras Despesas ]" );
						imp.say( imp.pRow() + 0, 79, "[ Valor do IPI ]" );
						imp.say( imp.pRow() + 0, 104, "[ VALOR TOTAL ]" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 29, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrDescCompra" ) == null ? rs.getString( "VlrDescItCompra" ) : rs.getString( "VlrDescCompra" ) ) );
						imp.say( imp.pRow() + 0, 64, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrAdicCompra" ) ) );
						imp.say( imp.pRow() + 0, 79, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrIPICompra" ) ) );
						imp.say( imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrLiqCompra" ) ) );
						iItImp = 0;
					}
					else {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 56, "CALCULO DO(S) IMPOSTO(S)" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "[ Base de Calculo ICMS ]" );
						imp.say( imp.pRow() + 0, 29, "[ Valor do ICMS ]" );
						imp.say( imp.pRow() + 0, 54, "[ B. Calc. ICMS Subst. ]" );
						imp.say( imp.pRow() + 0, 79, "[ Valor do ICMS Subst. ]" );
						imp.say( imp.pRow() + 0, 104, "[ Valor dos Produtos ]" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "***************" );
						imp.say( imp.pRow() + 0, 29, "***************" );
						imp.say( imp.pRow() + 0, 104, "***************" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "[ Valor do Frete ]" );
						imp.say( imp.pRow() + 0, 29, "[ Valor do Desconto ]" );
						imp.say( imp.pRow() + 0, 54, "[ Outras Despesas ]" );
						imp.say( imp.pRow() + 0, 79, "[ Valor do IPI ]" );
						imp.say( imp.pRow() + 0, 104, "[ VALOR TOTAL ]" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 4, "***************" );
						imp.say( imp.pRow() + 0, 29, "***************" );
						imp.say( imp.pRow() + 0, 54, "***************" );
						imp.say( imp.pRow() + 0, 79, "***************" );
						imp.say( imp.pRow() + 0, 104, "***************" );
						imp.incPags();
					}
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 60, "DADOS ADICIONAIS" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, ( 116 - rs.getString( "DescPlanoPag" ).trim().length() ) / 2, "FORMA DE PAGAMENTO : " + rs.getString( "DescPlanoPag" ) );
					imp.eject();
				}
			}
			imp.fechaGravacao();

			con.commit();
		} catch ( Exception err ) {
			err.printStackTrace();
		}
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}

	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "CODCOMPRA", txtCodCompra.getVlrInteger() );

		EmailBean email = Aplicativo.getEmailBean();
		email.setPara( txtEmailFor.getVlrString() );

		if ( classcp == null || "".equals( classcp.trim() ) ) {
			// dlGr = new FPrinterJob( "relatorios/ordemCompra.jasper", "Ordem de Compra", "", rs, hParam, this );
			dlGr = new FPrinterJob( "relatorios/ordemCompra.jasper", "Ordem de Compra", "", rs, hParam, this, email );

		}
		else {
			dlGr = new FPrinterJob( "layout/oc/" + classcp, "Ordem de Compra", "", rs, hParam, this, email );
		}

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
			}
		}
	}

	private boolean verificaBloq() {

		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSQL = null;
		int iCodCompra = 0;
		try {
			iCodCompra = txtCodCompra.getVlrInteger().intValue();
			if ( iCodCompra != 0 ) {
				sSQL = "SELECT BLOQCOMPRA FROM CPCOMPRA WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=? ";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				ps.setInt( 3, iCodCompra );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					if ( rs.getString( 1 ).equals( "S" ) )
						retorno = true;
				}

				ps.close();
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro verificando bloqueido da compra!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return retorno;
	}

	private void testaCodCompra() { // Traz o verdadeiro número do codCompra

		// através do generator do banco
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT * FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, "CP" );
			rs = ps.executeQuery();
			rs.next();
			txtCodCompra.setVlrString( rs.getString( 1 ) );
			// rs.close();
			// ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao confirmar código da Compra!\n" + err.getMessage(), true, con, err );
		}
	}

	public boolean testaCodLote() {

		boolean bRetorno = false;
		boolean bValido = false;
		String sSQL = "SELECT COUNT(*) FROM EQLOTE WHERE CODLOTE=? AND CODPROD=?" + " AND CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCodLote.getText().trim() );
			ps.setInt( 2, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, lcLote.getCodFilial() );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getInt( 1 ) > 0 ) {
					bValido = true;
				}
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela EQLOTE!\n" + err.getMessage(), true, con, err );
		}
		if ( !bValido ) {
			DLLote dl = new DLLote( this, txtCodLote.getText(), txtCodProd.getText(), txtDescProd.getText(), con );
			dl.setVisible( true );
			if ( dl.OK ) {
				bRetorno = true;
				txtCodLote.setVlrString( dl.getValor() );
				lcLote.carregaDados();
			}
			dl.dispose();
		}
		else {
			bRetorno = true;
		}
		return bRetorno;
	}

	public boolean testaNumSerie() {

		
		
		boolean bRetorno = false;
		boolean bValido = false;

		// Validação e abertura da tela para cadastramento da serie unitária
		if(txtNumSerie.isEditable()) {
		
			String sSQL = "SELECT COUNT(*) FROM EQSERIE WHERE NUMSERIE=? AND CODPROD=? AND CODEMP=? AND CODFILIAL=?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
			
				ps = con.prepareStatement( sSQL );
				ps.setString( 1, txtNumSerie.getVlrString() );
				ps.setInt( 2, txtCodProd.getVlrInteger() );
				ps.setInt( 3, Aplicativo.iCodEmp );
				ps.setInt( 4, lcSerie.getCodFilial() );
				
				rs = ps.executeQuery();
				
				if ( rs.next() ) {
					if ( rs.getInt( 1 ) > 0 ) {
						bValido = true;
					}
				}
				
				rs.close();
				ps.close();
				con.commit();
				
			} 
			catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao consultar a tabela EQSERIE!\n" + err.getMessage(), true, con, err );
			}
			if ( !bValido ) {
				
				DLSerie dl = new DLSerie( this, txtNumSerie.getVlrString(), txtCodProd.getVlrInteger(), txtDescProd.getVlrString(), con );
				
				dl.setVisible( true );
				
				if ( dl.OK ) {
					bRetorno = true;
					txtNumSerie.setVlrString( dl.getNumSerie() );
					lcSerie.carregaDados();
				}
				dl.dispose();
			}
			else {
				bRetorno = true;
			}
		}
		// Tela para cadastramento da série para quantidade maior que 1
		else {
			
			abreDlSerieMuitiplos();
			
		}
		
		
		return bRetorno;
	}

	
	public void actionPerformed( ActionEvent evt ) {

		String[] sValores = null;

		if ( evt.getSource() == btFechaCompra ) {
			lcCampos.carregaDados();
			DLFechaCompra dl = new DLFechaCompra( con, txtCodCompra.getVlrInteger(), this, getVolumes(), ( nfecf.getHasNFE() && "E".equals( txtTipoModNota.getVlrString() ) ) );
			dl.setVisible( true );
			if ( dl.OK ) {
				sValores = dl.getValores();
				dl.dispose();
			}
			else {
				dl.dispose();
			}
			lcCampos.carregaDados();
			if ( sValores != null ) {
				lcCampos.edit();
				if ( sValores[ 4 ].equals( "S" ) ) {
					if ( txtTipoMov.getVlrString().equals( TipoMov.TM_PEDIDO_COMPRA.getValue() ) || txtTipoMov.getVlrString().equals( TipoMov.TM_COMPRA.getValue() ) || txtTipoMov.getVlrString().equals( TipoMov.TM_DEVOLUCAO_VENDA.getValue() )
							|| txtTipoMov.getVlrString().equals( TipoMov.TM_DEVOLUCAO_CONSIGNACAO.getValue() ) || txtTipoMov.getVlrString().equals( TipoMov.TM_DEVOLUCAO_REMESSA.getValue() ) ) {

						emiteNotaFiscal( "NF" );

					}

					else if ( "CP,CO".indexOf( txtTipoMov.getVlrString() ) > -1 && "S".equals( cbSeqNfTipoMov.getVlrString() ) ) {
						emiteNotaFiscal( "NF" );
					}
					// else if ( txtTipoMov.getVlrString().equals( "SE" ) ) {
					// emiteNotaFiscal( "NS" );
					// }
					else {
						Funcoes.mensagemErro( this, "O tipo de movimento utilizado não permite impressão de nota!\n" + "Verifique o cadastro do tipo de movimento." );
						return;
					}
				}
				else if ( sValores[ 3 ].equals( "S" ) ) {
					imprimir( true, txtCodCompra.getVlrInteger().intValue() );
				}

				lcCampos.post();

				if ( podeBloq ) {
					bloqCompra();
				}
			}
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( true, txtCodCompra.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false, txtCodCompra.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btObs ) {
			mostraObs( "CPCOMPRA", txtCodCompra.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btBuscarRemessa ) {
			buscaRetornoRemessa();
		}
		else if ( evt.getSource() == btBuscaCompra ) {
			abreBuscaCompra();
		}

		super.actionPerformed( evt );
	}
	
	private void abreBuscaCompra() {
		
		if ( !Aplicativo.telaPrincipal.temTela( "Busca pedido de compra" ) ) {
			DLBuscaPedCompra tela = new DLBuscaPedCompra( this );
			Aplicativo.telaPrincipal.criatela( "Busca pedido de compra", tela, con );
		}
	}

	public void focusGained( FocusEvent fevt ) {

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescItCompra ) {
			if ( txtPercDescItCompra.getText().trim().length() < 1 ) {
				txtVlrDescItCompra.setAtivo( true );
			}
			else {
				txtVlrDescItCompra.setVlrBigDecimal( new BigDecimal( Funcoes.arredDouble( txtVlrProdItCompra.doubleValue() * txtPercDescItCompra.doubleValue() / 100, casasDecFin ) ) );
				calcVlrProd();
				calcImpostos( true );
				txtVlrDescItCompra.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtVlrIPIItCompra ) {
			adicIPI();
		}
		else if ( fevt.getSource() == txtVlrDescItCompra ) {
			if ( txtVlrDescItCompra.getText().trim().length() < 1 ) {
				txtPercDescItCompra.setAtivo( true );
			}
			else if ( txtVlrDescItCompra.getAtivo() ) {
				txtPercDescItCompra.setAtivo( false );
			}
		}
		else if ( ( fevt.getSource() == txtQtdItCompra ) || ( fevt.getSource() == txtPrecoItCompra ) || ( fevt.getSource() == txtCodNat ) ) {
			calcVlrProd();
			calcImpostos( true );
			habilitaSerie();
		}
		else if ( fevt.getSource() == txtPercICMSItCompra ) {
			calcVlrProd();
			calcImpostos( false );
		}
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( kevt.getSource() == txtCodPlanoPag ) {// Talvez este possa ser o
				// ultimo
				// campo do itvenda.
				if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT || lcCampos.getStatus() == ListaCampos.LCS_EDIT ) {
					lcCampos.post();
					lcCampos.carregaDados();

					lcDet.cancel( true );
					lcDet.insert( true );
					txtRefProd.requestFocus();
				}
				else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
					lcCampos.post();
					txtCodItCompra.requestFocus();
				}
			}
			else if ( kevt.getSource() == txtVlrIPIItCompra ) {
				// É o último se o custo/serie e lote não estiver habilitado.
				if ( !habilitaCusto  && ("N".equals(txtSerieProd.getVlrString()) || txtQtdItCompra.getVlrBigDecimal().floatValue()>1 ) && "N".equals(txtCLoteProd.getVlrString()) ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						if ( comref ) {
							txtRefProd.requestFocus();
						}
						else {
							txtCodProd.requestFocus();
						}
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItCompra.requestFocus();
					}
				}
				else {
					txtCustoItCompra.requestFocus();
				}
			}
			
			else if ( kevt.getSource() == txtCodLote ) {
				// É o último se estiver habilitado.
				if ( txtCodLote.isEditable() && !txtNumSerie.isEditable() ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						if ( comref ) {
							txtRefProd.requestFocus();
						}
						else {
							txtCodProd.requestFocus();
						}
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItCompra.requestFocus();
					}
				}
			}
			else if ( kevt.getSource() == txtNumSerie ) {
				// É o último se estiver habilitado.
				if ( txtNumSerie.isEditable() ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						if ( comref ) {
							txtRefProd.requestFocus();
						}
						else {
							txtCodProd.requestFocus();
						}
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItCompra.requestFocus();
					}
				}
			}
			else if ( kevt.getSource() == txtCustoItCompra ) {
				// É o último se estiver habilitado.
				if ( habilitaCusto ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						if ( comref ) {
							txtRefProd.requestFocus();
						}
						else {
							txtCodProd.requestFocus();
						}
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItCompra.requestFocus();
					}
				}
			}
			else if ( kevt.getSource() == txtAliqIPIItCompra ) {
				if ( txtAliqIPIItCompra.floatValue() > 0 ) {
					calcIpi( true );
				}
			}
			else if ( kevt.getSource() == txtVlrIPIItCompra ) {
				if ( txtVlrIPIItCompra.floatValue() > 0 ) {
					calcIpi( false );
				}
			}
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F4 ) {
			btFechaCompra.doClick();
		}
		if ( kevt.getSource() == txtRefProd ) {
			lcDet.edit();
		}

		super.keyPressed( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {

		super.keyReleased( kevt );
	}

	public void keyTyped( KeyEvent kevt ) {

		super.keyTyped( kevt );
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( lcDet.getStatus() != ListaCampos.LCS_INSERT ) {
			if ( ( cevt.getListaCampos() == lcProd ) || ( cevt.getListaCampos() == lcProd2 ) ) {
				cevt.getListaCampos().cancLerCampo( 6, true ); // Código da Classificação Fiscal
			}
		}
		else {
			if ( ( cevt.getListaCampos() == lcProd ) || ( cevt.getListaCampos() == lcProd2 ) ) {
				cevt.getListaCampos().cancLerCampo( 6, false ); // Código da Classificação Fiscal
				if ( buscaVlrUltCompra )
					getVlrUltimaCompra();
			}
		}
		if ( cevt.getListaCampos() == lcLote ) {
			if ( txtCodLote.getText().trim().length() == 0 ) {
				cevt.cancela(); // Cancela o carregaDados do lcLote para não
				// zerar o codprod.
			}
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProd || cevt.getListaCampos() == lcProd2 ) {
			if ( "S".equals( txtCLoteProd.getVlrString() ) || "S".equals( txtSerieProd.getVlrString() ) ) {
				
				redimensionaDet( 140 );
				
				lbCodLote.setVisible( true );
				txtCodLote.setVisible( true );
				
				lbNumSerie.setVisible( true );
				txtNumSerie.setVisible( true );
				
				if( "S".equals( txtCLoteProd.getVlrString() ) ) {
					txtCodLote.setEditable(  true );
				}
				else {
					txtCodLote.setEditable( false );
				}
				habilitaSerie();
				
			}
			else  {
				
				redimensionaDet( 100 );
				
				lbCodLote.setVisible( false );
				txtCodLote.setVisible( false );
				
				lbNumSerie.setVisible( false );
				txtNumSerie.setVisible( false );
				
			}
			
			lcAlmoxProd.carregaDados();
		}
		else if ( cevt.getListaCampos() == lcFisc && lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
			getCFOP();
		}
		else if ( cevt.getListaCampos() == lcDet ) {
			String s = txtCodCompra.getText();
			lcCompra2.carregaDados(); // Carrega os Totais
			txtCodCompra.setVlrString( s );
			
			habilitaSerie();
			
		}
		else if ( cevt.getListaCampos() == lcCampos ) {
			String s = txtCodCompra.getText();
			lcCompra2.carregaDados(); // Carrega os Totais
			txtCodCompra.setVlrString( s );

			if ( buscagenericaprod ) {

				if ( comref ) {
					txtRefProd.setBuscaGenProd( new DLCodProd( con, null, txtCodFor.getVlrInteger() ) );
				}
				else {
					txtCodProd.setBuscaGenProd( new DLCodProd( con, null, txtCodFor.getVlrInteger() ) );
				}
			}

		}
		else if ( cevt.getListaCampos() == lcSerie ) {
			if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT && "S".equals( cbSeqNfTipoMov.getVlrString() ) ) {
				// Busca de sequencia pela LFSEQSERIE
				try {
					  PreparedStatement ps = con.prepareStatement( "SELECT DOCSERIE FROM LFSEQSERIE " +
					  		"WHERE CODEMP=? AND CODFILIAL=? AND SERIE=? AND " +
					  		"CODEMPSS=? AND CODFILIALSS=? AND ATIVSERIE='S'" );
					  ps.setInt( 1, Aplicativo.iCodEmp );
					  ps.setInt( 2, ListaCampos.getMasterFilial( "LFSERIE" ) );
					  ps.setString( 3, txtSerieCompra.getVlrString() );
					  ps.setInt( 4, Aplicativo.iCodEmp );
					  ps.setInt( 5, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
					  ResultSet rs = ps.executeQuery();
					  if (rs.next()) {
						  txtDocSerie.setVlrInteger( rs.getInt( "DOCSERIE" ) );
					  } else {
						  txtDocSerie.setVlrInteger( 0 );
					  }
					  con.commit();
				} catch (Exception e) {
				    	e.printStackTrace();
				}
				txtDocCompra.setVlrInteger( new Integer( txtDocSerie.getVlrInteger().intValue() + 1 ) );
			}
		}
		else if ( cevt.getListaCampos() == lcNat ) {
			if ( cevt.ok && lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
				getICMS();
			}
		}
		else if ( cevt.getListaCampos() == lcTipoMov ) {
			txtCalcTrib.setVlrString( txtEmitCompra.getVlrString() );
			if ( "S".equals( cbSeqNfTipoMov.getVlrString() ) ) {
				txtDocCompra.setAtivo( false );
			}
			else {
				txtDocCompra.setAtivo( true );
			}
			btBuscarRemessa.setVisible( "DR".equals( txtTipoMov.getVlrString() ) );
		}
		else if ( cevt.getListaCampos() == lcAlmoxProd && lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
			if ( habilitaCusto ) {
				getCustoProd();
			}
		}
		else if ( cevt.getListaCampos() == lcModNota ) {
			// Caso seja nota fiscal eletrônica deve aparecer o campo de para chave da nota fiscal
			/*
			 * if( "E".equals( txtTipoModNota.getVlrString() ) ) { lbChaveNfe.setText( "Chave de acesso NFe" ); setAltCab( 195 ); setSize( 770, 535 ); } else { lbChaveNfe.setText( "" ); setAltCab( 160 ); setSize( 770, 500 ); }
			 */
		}

		String statuscompra = txtStatusCompra.getVlrString().trim();
		
		if ( statuscompra.length() > 0 && ( statuscompra.equals( "C2" ) || statuscompra.equals( "C3" ) ) ) {
			lbStatus.setText( "EMITIDA" );
			lbStatus.setBackground( new Color( 45, 190, 60 ) );
			lbStatus.setVisible( true );
		}
		else if ( verificaBloq() ) {
			lbStatus.setText( "BLOQUEADA" );
			lbStatus.setBackground( Color.BLUE );
			lbStatus.setVisible( true );
		}
		else if ( statuscompra.length() > 0 && statuscompra.substring( 0, 1 ).equals( "X" ) ) {
			lbStatus.setText( "CANCELADA" );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		}
		else if ( statuscompra.length() > 0 && ( statuscompra.equals( "P2" ) || statuscompra.equals( "P3" ) ) ) {
			lbStatus.setText( "EM ABERTO" );
			lbStatus.setBackground( Color.ORANGE );
			lbStatus.setVisible( true );
		}
		else if ( statuscompra.equals( "P1" )) {
			lbStatus.setText( "PENDENTE" );
			lbStatus.setBackground( Color.ORANGE );
			lbStatus.setVisible( true );
		}

	}

	private void habilitaSerie() {
		if( "S".equals( txtSerieProd.getVlrString() ) && txtQtdItCompra.getVlrBigDecimal().compareTo( new BigDecimal( 1 ) )==0 ) {
			
			txtNumSerie.setEditable( true );
		}
		else {
			txtNumSerie.setEditable( false );
		}				

	}
	
	public void beforeInsert( InsertEvent e ) {

	}

	public void afterInsert( InsertEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {
			txtDtEntCompra.setVlrDate( new Date() );
			txtDtEmitCompra.setVlrDate( new Date() );
		}
	}

	public void afterPost( PostEvent pevt ) {

		String s = txtCodCompra.getText();
		lcCompra2.carregaDados(); // Carrega os Totais
		txtCodCompra.setVlrString( s );

		if ( pevt.getListaCampos() == lcDet && novo ) {
			if ( habconvcp ) {
				geraOpConversao();
			}
			
		}
		if (pevt.getListaCampos() == lcDet) {
			if ( txtSerieProd.getVlrString().equals( "S" ) && txtQtdItCompra.getVlrBigDecimal().floatValue()>1  ) {
				testaNumSerie();								
			}			
		}
		novo = false;
	}

	public void beforePost( PostEvent pevt ) {

		boolean tem = false;
		if ( pevt.getListaCampos() == lcDet ) {
			txtRefProd.setVlrString( txtRefProd.getText() ); // ??? que que é isso.
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				if ( !testaCodLote() ) {
					pevt.cancela();
				}
			}
			if ( txtSerieProd.getVlrString().equals( "S" ) && txtQtdItCompra.getVlrBigDecimal().floatValue()==1  ) {
				if ( !testaNumSerie() ) {
					pevt.cancela();
				}
			}
			if ( getGuiaTraf() ) {
				DLGuiaTraf dl = new DLGuiaTraf( txtCodCompra.getVlrInteger(), txtCodItCompra.getVlrInteger(), con );
				tem = dl.getGuiaTraf();
				dl.setVisible( true );
				if ( dl.OK ) {
					if ( tem ) {
						dl.updatGuiaTraf();
					}
					else {
						dl.insertGuiaTraf();
					}
				}
				else {
					pevt.cancela();
				}
			}
		}
		if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
			testaCodCompra();
			// txtStatusCompra.setVlrString( "*" );
		}

		if ( pevt.getEstado() == ListaCampos.LCS_INSERT ) {
			novo = true;
		}

	}

	private boolean getGuiaTraf() {

		boolean retorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();

		try {

			sSQL.append( "SELECT GUIATRAFPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?  " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( rs.getString( 1 ).equals( "S" ) ) {
					retorno = true;
				}
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados do produto " + e.getMessage() );
		}

		return retorno;
	}

	public BigDecimal getVolumes() {

		BigDecimal retorno = new BigDecimal( 0 );

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

			retorno = retorno.add( ConversionFunctions.stringCurrencyToBigDecimal( tab.getValor( i, 5 ).toString() ) );
		}

		return retorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		setNfecf( new NFEConnectionFactory( cn, AbstractNFEFactory.TP_NF_IN ) );

		lcTipoMov.setConexao( cn );
		lcSerie.setConexao( cn );
		lcNumSerie.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		lcNat.setConexao( cn );
		lcLote.setConexao( cn );
		lcFisc.setConexao( cn );
		lcCompra2.setConexao( cn );
		lcAlmoxItem.setConexao( cn );
		lcAlmoxProd.setConexao( cn );
		lcTran.setConexao( cn );
		lcSolCompra.setConexao( cn );
		lcModNota.setConexao( cn );
		lcPais.setConexao( cn );
		lcUF.setConexao( cn );

		getPrefere();
		montaTela();
		montaDetalhe();
	}

	/**
	 * mostra uma FObsevacao contendo a descrição completa do produto, quando clicado duas vezes sobre o JTextFieldFK do item.
	 * 
	 * @param txaObsIt
	 *            JTextAreaPad.
	 * @param iCodProd
	 *            codigo do produto.
	 * @param sDescProd
	 *            descrição do produto.
	 */
	protected void mostraTelaDecricao( JTextAreaPad txaObsIt, int iCodProd, String sDescProd ) {

		if ( iCodProd == 0 ) {
			return;
		}

		String sDesc = txaObsIt.getVlrString();

		if ( sDesc.equals( "" ) ) {
			sDesc = buscaDescComp( iCodProd );
		}
		if ( sDesc.equals( "" ) ) {
			sDesc = sDescProd;
		}

		DLBuscaDescProd obs = new DLBuscaDescProd( sDesc );
		obs.setConexao( con );
		obs.setVisible( true );

		if ( obs.OK ) {
			txaObsIt.setVlrString( obs.getTexto() );
			lcDet.edit();
		}

		obs.dispose();

	}

	/**
	 * Busca descrição completa do produto na tabela de produtos .
	 * 
	 * @param iCodProd
	 *            codigo do produto a pesquizar.
	 * @return String contendo a descrição completa do produto.
	 */
	private String buscaDescComp( int iCodProd ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sRet = "";
		String sSQL = "SELECT DESCCOMPPROD FROM EQPRODUTO WHERE CODPROD=?" + " AND CODEMP=? AND CODFILIAL=?";

		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, iCodProd );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				sRet = rs.getString( "DescCompProd" );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar descrição completa!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return sRet != null ? sRet : "";
	}

	private void geraOpConversao() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Integer> codops = new Vector<Integer>();
		Vector<Integer> seqops = new Vector<Integer>();

		Integer codempet = null;
		Integer codfilialet = null;
		Integer codprodet = null;
		Integer seqest = null;

		BigDecimal qtdest = null;
		BigDecimal qtditcompra = null;
		BigDecimal qtdformula = null;
		BigDecimal qtdsugerida = null;
		Integer codprod = null;
		String justificativa = "";

		DLFinalizaOP dl = null;

		try {

			codprod = txtCodProd.getVlrInteger();
			qtditcompra = txtQtdItCompra.getVlrBigDecimal();

			// Query para busca de informçõs da estrutura para conversão e informações sobre o item de estrutura vinculado ao produto da compra.

			sql.append( "select first 1 fc.codempet, fc.codfilialet, fc.codprodet, fc.seqest, et.qtdest, ie.qtditest  " );
			sql.append( "from eqfatconv fc, ppestrutura et, ppitestrutura ie " );
			sql.append( "where fc.codemp=? and fc.codfilial=? and fc.codprod=? and fc.cpfatconv='S' " );
			sql.append( "and et.codemp=fc.codempet and et.codfilial=fc.codfilialet and et.codprod=fc.codprodet and et.seqest=fc.seqest " );
			sql.append( "and ie.codemp=et.codemp and ie.codfilial=et.codfilial and ie.codprod=et.codprod and ie.seqest=et.seqest " );
			sql.append( "and ie.codemppd=fc.codemp and ie.codfilialpd=fc.codfilial and ie.codprodpd=fc.codprod and ie.qtdvariavel='S'" );
			sql.append( "and et.ativoest='S'" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, lcProd.getCodEmp() );
			ps.setInt( 2, lcProd.getCodFilial() );
			ps.setInt( 3, codprod );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				codempet = rs.getInt( "codempet" );
				codfilialet = rs.getInt( "codfilialet" );
				codprodet = rs.getInt( "codprodet" );
				seqest = rs.getInt( "seqest" );
				qtdest = rs.getBigDecimal( "qtdest" );
				qtdformula = rs.getBigDecimal( "qtditest" );

				if ( qtdformula != null && qtdformula.floatValue() > 0 ) {

					qtdsugerida = ( qtditcompra.divide( qtdformula ) );

				}

			}

			rs.close();
			con.commit();

			if ( qtdsugerida != null && qtdsugerida.floatValue() > 0 ) {

				// Dialog de confirmação

				dl = new DLFinalizaOP( this, qtdsugerida );

				dl.setVisible( true );

				if ( dl.OK ) {
					qtdsugerida = dl.getValor();
					justificativa = dl.getObs();
				}

				dl.dispose();

				sql = new StringBuilder();

				sql.append( "select codopret,seqopret " );
				sql.append( "from ppgeraop(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) " );

				ps = con.prepareStatement( sql.toString() );

				ps.setString( PROCEDUREOP.TIPOPROCESS.ordinal() + 1, "C" );
				ps.setInt( PROCEDUREOP.CODEMPOP.ordinal() + 1, Aplicativo.iCodEmp );
				ps.setInt( PROCEDUREOP.CODFILIALOP.ordinal() + 1, Aplicativo.iCodFilial );
				ps.setNull( PROCEDUREOP.CODOP.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.SEQOP.ordinal() + 1, Types.INTEGER );
				ps.setInt( PROCEDUREOP.CODEMPPD.ordinal() + 1, codempet );
				ps.setInt( PROCEDUREOP.CODFILIALPD.ordinal() + 1, codfilialet );
				ps.setInt( PROCEDUREOP.CODPROD.ordinal() + 1, codprodet );
				ps.setNull( PROCEDUREOP.CODEMPOC.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODFILIALOC.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODORC.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODITORC.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.TIPOORC.ordinal() + 1, Types.CHAR );
				ps.setBigDecimal( PROCEDUREOP.QTDSUGPRODOP.ordinal() + 1, qtdsugerida );
				ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.dateToSQLDate( txtDtEntCompra.getVlrDate() ) );
				ps.setInt( PROCEDUREOP.SEQEST.ordinal() + 1, seqest );

				ps.setNull( PROCEDUREOP.CODEMPET.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODFILIALET.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODEST.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.AGRUPDATAAPROV.ordinal() + 1, Types.CHAR );
				ps.setNull( PROCEDUREOP.AGRUPDTFABROP.ordinal() + 1, Types.CHAR );
				ps.setNull( PROCEDUREOP.AGRUPCODCLI.ordinal() + 1, Types.CHAR );
				ps.setNull( PROCEDUREOP.CODEMPCL.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODFILIALCL.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.CODCLI.ordinal() + 1, Types.INTEGER );
				ps.setNull( PROCEDUREOP.DATAAPROV.ordinal() + 1, Types.DATE );

				ps.setInt( PROCEDUREOP.CODEMPCP.ordinal() + 1, lcDet.getCodEmp() );
				ps.setInt( PROCEDUREOP.CODFILIALCP.ordinal() + 1, lcDet.getCodFilial() );
				ps.setInt( PROCEDUREOP.CODCOMPRA.ordinal() + 1, txtCodCompra.getVlrInteger() );
				ps.setInt( PROCEDUREOP.CODITCOMPRA.ordinal() + 1, txtCodItCompra.getVlrInteger() );

				ps.setString( PROCEDUREOP.JUSTFICQTDPROD.ordinal() + 1, justificativa );

				ps.setInt( PROCEDUREOP.CODEMPPDENTRADA.ordinal() + 1, lcProd.getCodEmp() );
				ps.setInt( PROCEDUREOP.CODFILIALPDENTRADA.ordinal() + 1, lcProd.getCodEmp() );
				ps.setInt( PROCEDUREOP.CODPRODENTRADA.ordinal() + 1, codprod );
				ps.setBigDecimal( PROCEDUREOP.QTDENTRADA.ordinal() + 1, qtditcompra );

				rs = ps.executeQuery();

				if ( rs.next() ) {
					codops.addElement( rs.getInt( 1 ) );
					seqops.addElement( rs.getInt( 2 ) );
				}

				con.commit();

				if ( codops.size() > 0 ) {

					sql = new StringBuilder();
					sql.append( "update ppitop io set io.qtditop=? " );
					sql.append( "where io.codemp=? and io.codfilial=? and io.codop=? and io.seqop=?" );
					sql.append( "and io.codemppd=? and io.codfilialpd=? and io.codprod=?" );

					ps = con.prepareStatement( sql.toString() );

					ps.setBigDecimal( 1, qtditcompra );

					ps.setInt( 2, Aplicativo.iCodEmp );
					ps.setInt( 3, Aplicativo.iCodFilial );
					ps.setInt( 4, (Integer) codops.elementAt( 0 ) );
					ps.setInt( 5, (Integer) seqops.elementAt( 0 ) );
					ps.setInt( 6, lcProd.getCodEmp() );
					ps.setInt( 7, lcProd.getCodFilial() );
					ps.setInt( 8, codprod );

					ps.executeUpdate();

					con.commit();

					Funcoes.mensagemInforma( this, "A seguinte ordem de produção foi gerada:\n" + codops.toString() );

				}

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();

				rs = null;
				ps = null;
				codops = null;
				seqops = null;
			} catch ( Exception e2 ) {
				e2.printStackTrace();
			}

		}
	}

	public NFEConnectionFactory getNfecf() {

		return nfecf;
	}

	public void setNfecf( NFEConnectionFactory nfecf ) {

		this.nfecf = nfecf;
	}
	
	private void abreDlSerieMuitiplos() {
		
		DLSerieGrid dl = new DLSerieGrid();
		dl.setCodemp( lcDet.getCodEmp() );
		dl.setCodfilial( lcDet.getCodFilial() );
		dl.setCodcompra( txtCodCompra.getVlrInteger() );
		dl.setCoditcompra( txtCodItCompra.getVlrInteger() );
		dl.setCodemppd( lcProd.getCodEmp() );
		dl.setCodfilialpd( lcProd.getCodFilial() );
		dl.setCodprod( txtCodProd.getVlrInteger() );
		dl.setDescprod( txtDescProd.getVlrString().trim() );
		
		dl.setConexao( con );
		dl.setVisible( true );
		HashMap<String,Integer> retorno = null;
		
		if ( dl.OK ) {

		}
		else {	
		}
		
		dl.dispose();
	}

	public void mouseClicked( MouseEvent mevt ) {
		if(mevt.getSource()==txtDescProd) {
			if ( mevt.getClickCount() == 2 ) {
				mostraTelaDecricao( txaObsItCompra, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString() );
			}
		}
	}

	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

}
