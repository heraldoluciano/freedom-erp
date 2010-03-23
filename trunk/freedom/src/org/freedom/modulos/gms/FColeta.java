/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FProduto.java <BR>
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
 * Tela para cadastro de coleta de materiais.
 *
 * @version 23/02/2010 - Anderson Sanchez 
 */

package org.freedom.modulos.gms;

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
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.EmailBean;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.pcp.DLFinalizaOP;
import org.freedom.modulos.std.DLBuscaDescProd;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLGuiaTraf;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.modulos.std.DLRetRemessa;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;
import org.freedom.telas.FPrinterJob;

public class FColeta extends FDetalhe implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private int casasDecFin = Aplicativo.casasDecFin;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JPanelPad pinCabColeta = new JPanelPad();
	
	private JPanelPad pinCabTransp = new JPanelPad();
	
	private JPanelPad pinTot = new JPanelPad( 200, 200 );
	
	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JPanelPad pnTot = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JButtonPad btFechaCompra = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btObs = new JButtonPad( Icone.novo( "btObs.gif" ) );

	private JButtonPad btBuscarRemessa = new JButtonPad( "Buscar remessa", Icone.novo( "btExecuta.gif" ) );

	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

//	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

//	private JTextFieldPad txtDtEmitCompra = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtEnt = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldPad txtCodItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtQtdItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodAlmoxProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodUn = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescTipoRecMerc = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodAlmoxItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldFK txtRazTran = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	
	private JLabelPad lbStatus = new JLabelPad();

	private ListaCampos lcTipoRecMerc = new ListaCampos( this, "TM" );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private ListaCampos lcProd2 = new ListaCampos( this, "PD" );

	private ListaCampos lcNat = new ListaCampos( this, "NT" );

	private ListaCampos lcLote = new ListaCampos( this, "LE" );

	private ListaCampos lcAlmoxItem = new ListaCampos( this, "AX" );

	private ListaCampos lcAlmoxProd = new ListaCampos( this, "AX" );
	
	private final ListaCampos lcTran = new ListaCampos( this, "TN" );
	
	private ListaCampos lcPais = new ListaCampos( this, "" );
	
	private ListaCampos lcUF = new ListaCampos( this );

	private String sOrdNota = "";

	private boolean comref = false;

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
	
	private JLabelPad lbChaveNfe = null;
	
	private boolean novo = false;
	
	private enum PROCEDUREOP {
		  TIPOPROCESS, CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC,  CODFILIALOC,  CODORC, TIPOORC, CODITORC, 
		  QTDSUGPRODOP, DTFABROP, SEQEST, CODEMPET, CODFILIALET, CODEST, AGRUPDATAAPROV, AGRUPDTFABROP, AGRUPCODCLI, CODEMPCL, CODFILIALCL, CODCLI, DATAAPROV,
		  CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA, JUSTFICQTDPROD, CODEMPPDENTRADA, CODFILIALPDENTRADA, CODPRODENTRADA, QTDENTRADA
	}

	public FColeta() {
		setTitulo( "Compra" );
		setAtribos( 15, 10, 770, 500 );
	}

	public void montaTela() {
		
		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Compra", pinCabColeta );
		
		if( "S".equals( abaTransp ) ){
			tpnCab.addTab( "Tranportadora", pinCabTransp );
		}
	
		btBuscarRemessa.setVisible( false );
		pnNavCab.add( btBuscarRemessa, BorderLayout.EAST );
		
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
/*
		lcTipoRecMerc.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoRecMerc.add( new GuardaCampo( txtDescTipoRecMerc, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoRecMerc.add( new GuardaCampo( txtCodModNota, "CodModNota", "Código do modelo de nota", ListaCampos.DB_FK, false ));
		lcTipoRecMerc.add( new GuardaCampo( txtEmitCompra, "EmitNFCpMov", "Emite NF", ListaCampos.DB_SI, false ));
		lcTipoRecMerc.add( new GuardaCampo( txtTipoMov, "TipoMov", "Tipo mov.", ListaCampos.DB_SI, false ) );
		lcTipoRecMerc.add( new GuardaCampo( cbSeqNfTipoMov, "SeqNfTipomov", "Aloc.NF", ListaCampos.DB_SI, true ) );
		lcTipoRecMerc.setWhereAdic( 
				"((ESTIPOMOV = 'E') AND" + 
				" ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + 
				"WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + 
				"TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=" + 
				Aplicativo.iCodEmp + " AND TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + 
				" AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) )" );
		lcTipoRecMerc.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoRecMerc.setQueryCommit( false );
		lcTipoRecMerc.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoRecMerc );

		lcSerie.add( new GuardaCampo( txtSerieCompra, "Serie", "Série", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDocSerie, "DocSerie", "Doc", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtSerieCompra.setTabelaExterna( lcSerie );
*/
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );
/*		lcFor.add( new GuardaCampo( txtEstFor, "UFFor", "UF", ListaCampos.DB_SI, false ) );
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
*/
		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtRazTran, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran );
		
		lcProd.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cod.Barra", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodFabProd, "CodFabProd", "Cod.Fabricante", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodUn, "CodUnid", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodAlmoxProd, "CodAlmox", "Unidade", ListaCampos.DB_SI, false ) );

		txtCodUn.setAtivo( false );
		lcProd.setWhereAdic( "ATIVOPROD='S'" );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );

		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_PK, false ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "codprod", "Cód.prod.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cod.Barras", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodFabProd, "CodFabProd", "Cod.Fabricante", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodUn, "CodUnid", "Unidade", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodAlmoxProd, "CodAlmox", "Unidade", ListaCampos.DB_SI, false ) );

		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setListaCampos( lcDet );
		lcProd2.setWhereAdic( "ATIVOPROD='S'" );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2 );

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

		btFechaCompra.setToolTipText( "Fechar a Compra (F4)" );

		pinCab = new JPanelPad( 740, 130 );
		setListaCampos( lcCampos );
		setAltCab( 160 );
		setPainel( pinCabColeta );
		
		adicCampo( txtTicket, 7, 20, 80, 20, "CodCompra", "Nº Compra", ListaCampos.DB_PK, true );
//		adicCampo( txtCodTipoMov, 90, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoRecMerc, true );
		adicDescFK( txtDescTipoRecMerc, 170, 20, 247, 20, "DescTipoMov", "Descrição do tipo de movimento" );

		adicCampo( txtDoc, 500, 20, 77, 20, "DocCompra", "Doc", ListaCampos.DB_SI, true );

		adicCampo( txtDtEnt, 658, 20, 75, 20, "DtEntCompra", "Dt.entrada", ListaCampos.DB_SI, true );
		adicCampo( txtCodFor, 7, 60, 80, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtRazFor, true );
		adicDescFK( txtRazFor, 90, 60, 327, 20, "RazFor", "Razão social do fornecedor" );
				
		adicDBLiv( txaObs01, "Obs01", labelobs01cp==null?"Observações":labelobs01cp, false );
		adicDBLiv( txaObs02, "Obs02", labelobs01cp==null?"Observações":labelobs01cp, false );
		adicDBLiv( txaObs03, "Obs03", labelobs01cp==null?"Observações":labelobs01cp, false );
		adicDBLiv( txaObs04, "Obs04", labelobs01cp==null?"Observações":labelobs01cp, false );
		
		if( abaTransp.equals( "S" ) ){
			setListaCampos( lcCampos );
			setPainel( pinCabTransp );
			adicCampo( txtCodTran, 7, 25, 70, 20, "Codtran", "Cód.transp.", ListaCampos.DB_FK, false );
			adicDescFK( txtRazTran, 80, 25, 250, 20, "Raztran", "Razão social da transportadora" );
		}
		
		adicCampoInvisivel( txtStatus, "StatusCompra", "Status", ListaCampos.DB_SI, false );

		setListaCampos( true, "COMPRA", "CP" );
		lcCampos.setQueryInsert( false );
		
		
		btFechaCompra.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );		
		btObs.addActionListener( this );	
		btBuscarRemessa.addActionListener( this );
		
		txtQtdItCompra.addFocusListener( this );
		
		txtDescProd.setToolTipText( "Clique aqui duas vezes para alterar a descrição." );
				
		lcCampos.addCarregaListener( this );
		lcFor.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		lcProd2.addCarregaListener( this );

		lcNat.addCarregaListener( this );
		lcLote.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcTipoRecMerc.addCarregaListener( this );
		lcAlmoxProd.addCarregaListener( this );

		
		lcCampos.addInsertListener( this );
		
		lcCampos.addPostListener( this );
		lcDet.addPostListener( this );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setVisible( false );
		
		JPanelPad navEast = new JPanelPad();
		navEast.setPreferredSize( new Dimension( 150, 30 ) );
		navEast.adic( lbStatus, 26, 3, 110, 20 );
		navEast.tiraBorda();	
		pnNavCab.add( navEast, BorderLayout.EAST );

		setImprimir( true );
		
	}

	private void montaDetalhe() {

		setAltDet( 100 );
		pinDet = new JPanelPad( 740, 100 );
		
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		
		adicCampo( txtCodItCompra, 7, 20, 30, 20, "CodItCompra", "N.item", ListaCampos.DB_PK, true );
		
		if ( comref ) {
			txtRefProd.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProd2.getWhereAdic() ) );
			adicCampoInvisivel( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false );
			adicCampoInvisivel( txtRefProd, "RefProd", "Referência", ListaCampos.DB_FK, false );

			adic( new JLabelPad( "Referência" ), 40, 0, 67, 20 );
			adic( txtRefProd, 40, 20, 67, 20 );
			txtRefProd.setFK( true );
		}
		else {
			txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
			adicCampo( txtCodProd, 40, 20, 67, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false );
		}
		

		
		adicDescFK( txtDescProd, 110, 20, 197, 20, "DescProd", "Descrição do produto" );
		adic( new JLabelPad( "Unidade" ), 310, 0, 60, 20 );
		adic( txtCodUn, 310, 20, 60, 20 );
		adicCampo( txtCodLote, 373, 20, 95, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLote, false );
		adicCampo( txtQtdItCompra, 470, 20, 67, 20, "qtditcompra", "Qtd.", ListaCampos.DB_SI, true );

		adicCampoInvisivel( txtCodAlmoxItCompra, "codalmox", "Cod.Almox", ListaCampos.DB_FK, false );

		txtQtdItCompra.setBuscaAdic( new DLBuscaEstoq( lcDet, lcAlmoxItem, lcProd, con, "qtditcompra" ) );

		pinTot.adic( new JLabelPad( "Tot. IPI" ), 7, 0, 120, 20 );

		pinTot.adic( new JLabelPad( "Tot. Desc." ), 7, 40, 120, 20 );

		pinTot.adic( new JLabelPad( "Total Geral" ), 7, 80, 120, 20 );


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
		
		if ( txtTicket.getVlrInteger() == 0 ) {
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
			iCodCompra = txtTicket.getVlrInteger().intValue();
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

	public void exec( int iCodCompra ) {

		txtTicket.setVlrString( iCodCompra + "" );
		lcCampos.carregaDados();
	}

	public void execDev( int iCodFor, int iCodTipoMov, String sSerie, int iDoc ) {

		lcCampos.insert( true );
		txtCodFor.setVlrString( iCodFor + "" );
		lcFor.carregaDados();
//		txtCodTipoMov.setVlrString( iCodTipoMov + "" );
		lcTipoRecMerc.carregaDados();
//		txtSerieCompra.setVlrString( sSerie );
		txtDoc.setVlrString( iDoc + "" );
	}

	private void getPrefere() {
		StringBuffer sql = new StringBuffer();
		try {

			sql.append( "SELECT P1.USAREFPROD,P1.ORDNOTA,P1.BLOQCOMPRA,P1.BUSCAVLRULTCOMPRA,P1.CUSTOCOMPRA, "); 
			sql.append( "P1.TABTRANSPCP, P1.TABSOLCP,P1.TABIMPORTCP, P1.CLASSCP, P1.LABELOBS01CP, P1.LABELOBS02CP, ");
			sql.append( "P1.LABELOBS03CP, P1.LABELOBS04CP, P5.HABCONVCP " ); 
			sql.append(	"FROM SGPREFERE1 P1 LEFT OUTER JOIN SGPREFERE5 P5 ON ");
			sql.append(	"P1.CODEMP=P5.CODEMP AND P1.CODFILIAL=P5.CODFILIAL ");
			sql.append(	"WHERE P1.CODEMP=? AND P1.CODFILIAL=?" );			
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				comref = rs.getString( "USAREFPROD" ).trim().equals( "S" );
				podeBloq = rs.getString( "BLOQCOMPRA" ).trim().equals( "S" );
				buscaVlrUltCompra = rs.getString( "BUSCAVLRULTCOMPRA" ).trim().equals( "S" );
				sOrdNota = rs.getString( "ORDNOTA" );
				habilitaCusto = rs.getString( "CUSTOCOMPRA" ).trim().equals( "S" );
				abaTransp = rs.getString( "TABTRANSPCP");
				abaSolCompra = rs.getString( "TABSOLCP" );
				abaImport = rs.getString( "TABIMPORTCP" );
				classcp = rs.getString( "CLASSCP" );
				labelobs01cp = rs.getString( "LABELOBS01CP" );
				labelobs02cp = rs.getString( "LABELOBS02CP" );
				labelobs03cp = rs.getString( "LABELOBS03CP" );
				labelobs04cp = rs.getString( "LABELOBS04CP" );
				habconvcp = rs.getString( "HABCONVCP" )==null? false : rs.getString( "HABCONVCP" ).equals( "S" );
								
			}
			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + e.getMessage(), true, con, e );
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
		sSQL.append( "SG.LABELOBS01CP, SG.LABELOBS02CP, SG.LABELOBS03CP, SG.LABELOBS04CP ");
		sSQL.append( "FROM CPCOMPRA C, CPFORNECED F,CPITCOMPRA I, EQPRODUTO P, FNPLANOPAG PG, SGPREFERE1 SG " );
		sSQL.append( "WHERE C.CODCOMPRA=? AND C.CODEMP=? AND C.CODFILIAL=? AND " );
		sSQL.append( "F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND F.CODFOR=C.CODFOR AND " );
		sSQL.append( "I.CODEMP=C.CODEMP AND I.CODFILIAL=C.CODFILIAL AND I.CODCOMPRA=C.CODCOMPRA AND ");
		sSQL.append( "P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD AND " );
		sSQL.append( "PG.CODEMP=C.CODEMPPG AND PG.CODFILIAL=C.CODFILIALPG AND PG.CODPLANOPAG=C.CODPLANOPAG AND " );
		sSQL.append( "SG.CODEMP=? AND SG.CODFILIAL=? ");
		sSQL.append( "ORDER BY C.CODCOMPRA,P." + dl.getValor());
		
		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, iCodCompra );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			System.out.println(sSQL.toString());
			
			rs = ps.executeQuery();
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados da compra" );
		}
		if( dl.getTipo() == "G" ){
			
			imprimiGrafico( rs, bVisualizar );
			
		}else{
			
			imprimeTexto( rs, bVisualizar );
		}
		
	}
	public void imprimeTexto( final ResultSet rs, final boolean bVisualizar ){
	
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
		}catch ( Exception err ) {			
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
		hParam.put( "CODCOMPRA", txtTicket.getVlrInteger() );

		EmailBean email  =  Aplicativo.getEmailBean();
//		email.setPara( txtEmailFor.getVlrString() );
		
		if(classcp==null || "".equals(classcp.trim())) {		
//			dlGr = new FPrinterJob( "relatorios/ordemCompra.jasper", "Ordem de Compra", "", rs, hParam, this );
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
			iCodCompra = txtTicket.getVlrInteger().intValue();
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
			Funcoes.mensagemErro( this, "Erro verificando bloqueio da compra!\n" + err.getMessage(), true, con, err );
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
			txtTicket.setVlrString( rs.getString( 1 ) );
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

	public void actionPerformed( ActionEvent evt ) {
 
		String[] sValores = null;

		
		if ( evt.getSource() == btPrevimp ) {
			imprimir( true, txtTicket.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false, txtTicket.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btObs ) {
			mostraObs( "CPCOMPRA", txtTicket.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btBuscarRemessa ) {
			buscaRetornoRemessa();
		}
		
		super.actionPerformed( evt );
	}

	public void focusGained( FocusEvent fevt ) { }

	public void focusLost( FocusEvent fevt ) {

		
	}

	public void keyPressed( KeyEvent kevt ) {

	/*	if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			
		
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F4 ) {
			btFechaCompra.doClick();
		}
		if ( kevt.getSource() == txtRefProd ){
			lcDet.edit();
		}*/

		super.keyPressed( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {

		super.keyReleased( kevt );
	}

	public void keyTyped( KeyEvent kevt ) {

		super.keyTyped( kevt );
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProd || cevt.getListaCampos() == lcProd2 ) {
			if ( txtCLoteProd.getText().trim().equals( "N" ) ) {
				txtCodLote.setAtivo( false );// Desativa o Cógigo do lote por o
				// produto não possuir lote
			}
			else if ( txtCLoteProd.getText().trim().equals( "S" ) ) {
				txtCodLote.setAtivo( true );// Ativa o Cógigo do Lote pois o
				// produto tem lote
			}
			lcAlmoxProd.carregaDados();
		}
		
		
		if ( txtStatus.getVlrString().trim().length() > 0 && ( txtStatus.getVlrString().trim().equals( "C2" ) || txtStatus.getVlrString().trim().equals( "C3" ) ) ) {
			lbStatus.setText( "RECEBIDA" );
			lbStatus.setBackground( new Color( 45, 190, 60 ) );
			lbStatus.setVisible( true );
		}
		else if ( verificaBloq() ) {
			lbStatus.setText( "BLOQUEADA" );
			lbStatus.setBackground( Color.BLUE );
			lbStatus.setVisible( true );
		}
		else if( txtStatus.getVlrString().trim().length() > 0 && txtStatus.getVlrString().substring( 0, 1 ).equals( "X" ) ){
			lbStatus.setText( "CANCELADA" );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		}
		else {
			lbStatus.setText( "terste" );
//			lbStatus.setVisible( false );
		}

	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterInsert( InsertEvent e ) {
		if ( e.getListaCampos() == lcCampos ) {
			txtDtEnt.setVlrDate( new Date() );
		
		}
	}

	public void afterPost( PostEvent pevt ) {
		String s = txtTicket.getText();
		txtTicket.setVlrString( s );
		
		if(pevt.getListaCampos()==lcDet && novo) {
			if(habconvcp) {
				geraOpConversao();
			}
		}
		novo = false;
	}
	
	public void beforePost( PostEvent pevt ) {

		boolean tem = false;
		if ( pevt.getListaCampos() == lcDet ) {
			txtRefProd.setVlrString( txtRefProd.getText() ); // ?
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				if ( !testaCodLote() ) {
					pevt.cancela();
				}
			}			
			if ( getGuiaTraf() ) {
				DLGuiaTraf dl = new DLGuiaTraf( txtTicket.getVlrInteger(), txtCodItCompra.getVlrInteger(), con );				
				tem = dl.getGuiaTraf();				
				dl.setVisible( true );				
				if( dl.OK ){					
					if( tem ){						
						dl.updatGuiaTraf();
					}
					else{						
						dl.insertGuiaTraf();
					}					
				}
				else{
					pevt.cancela();
				}
			}	
		}
		if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
			testaCodCompra();
			//txtStatusCompra.setVlrString( "*" );
		}
		
		if(pevt.getEstado() == ListaCampos.LCS_INSERT) {
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
		lcTipoRecMerc.setConexao( cn );

		lcFor.setConexao( cn );
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		lcNat.setConexao( cn );
		lcLote.setConexao( cn );
		lcAlmoxItem.setConexao( cn );
		lcAlmoxProd.setConexao( cn );
		lcTran.setConexao( cn );
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

		} 
		catch ( SQLException err ) {
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
						
			//Query para busca de informçõs da estrutura para conversão e informações sobre o item de estrutura vinculado ao produto da compra.
			  
			sql.append( "select first 1 fc.codempet, fc.codfilialet, fc.codprodet, fc.seqest, et.qtdest, ie.qtditest  ");
			sql.append( "from eqfatconv fc, ppestrutura et, ppitestrutura ie " );
			sql.append( "where fc.codemp=? and fc.codfilial=? and fc.codprod=? and fc.cpfatconv='S' ");
			sql.append( "and et.codemp=fc.codempet and et.codfilial=fc.codfilialet and et.codprod=fc.codprodet and et.seqest=fc.seqest ");
			sql.append( "and ie.codemp=et.codemp and ie.codfilial=et.codfilial and ie.codprod=et.codprod and ie.seqest=et.seqest ");
			sql.append( "and ie.codemppd=fc.codemp and ie.codfilialpd=fc.codfilial and ie.codprodpd=fc.codprod and ie.qtdvariavel='S'" );
			sql.append( "and et.ativoest='S'");
							
			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, lcProd.getCodEmp() );
			ps.setInt( 2, lcProd.getCodFilial() );
			ps.setInt( 3, codprod );
			
			rs = ps.executeQuery();
			
			if(rs.next()) {

				codempet = rs.getInt( "codempet" );
				codfilialet = rs.getInt( "codfilialet" );
				codprodet = rs.getInt( "codprodet" );
				seqest = rs.getInt( "seqest" );
				qtdest = rs.getBigDecimal( "qtdest" );				
				qtdformula = rs.getBigDecimal( "qtditest" );
					
				if ( qtdformula!=null && qtdformula.floatValue()>0 ) {
				
					qtdsugerida = (qtditcompra.divide( qtdformula ));
						
				}
				
			}

			rs.close();
			con.commit();
			
			
			if( qtdsugerida!=null && qtdsugerida.floatValue()>0 ) {
				
				// Dialog de confirmação

				dl = new DLFinalizaOP(this,qtdsugerida);
				
				dl.setVisible(true);
				
				if (dl.OK) {					
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
				ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.dateToSQLDate( txtDtEnt.getVlrDate() ) );						
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
				ps.setInt( PROCEDUREOP.CODCOMPRA.ordinal() + 1, txtTicket.getVlrInteger() );						
				ps.setInt( PROCEDUREOP.CODITCOMPRA.ordinal() + 1, txtCodItCompra.getVlrInteger() );
																
				ps.setString( PROCEDUREOP.JUSTFICQTDPROD.ordinal() + 1, justificativa );
				
				ps.setInt( PROCEDUREOP.CODEMPPDENTRADA.ordinal() + 1, lcProd.getCodEmp() );
				ps.setInt( PROCEDUREOP.CODFILIALPDENTRADA.ordinal() + 1, lcProd.getCodEmp() );
				ps.setInt( PROCEDUREOP.CODPRODENTRADA.ordinal() + 1, codprod );
				ps.setBigDecimal( PROCEDUREOP.QTDENTRADA.ordinal() + 1, qtditcompra );
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					codops.addElement( rs.getInt( 1 ) );
					seqops.addElement( rs.getInt( 2 ) );
				}
				
				con.commit();

				if(codops.size()>0) {

					sql = new StringBuilder();
					sql.append( "update ppitop io set io.qtditop=? ");
					sql.append( "where io.codemp=? and io.codfilial=? and io.codop=? and io.seqop=?");
					sql.append( "and io.codemppd=? and io.codfilialpd=? and io.codprod=?");
					
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				
				rs = null;
				ps = null;
				codops = null;
				seqops = null;
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
	}
	
	
	
}
