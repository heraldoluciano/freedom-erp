/**
 * @version 05/04/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.gms.view.frame.crud.detail <BR>
 * Classe:
 * @(#)FImportacao.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * Tela de cadastramento de compras de importação.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.detail;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.lvf.view.frame.crud.detail.FCLFiscal;
import org.freedom.modulos.std.view.frame.crud.plain.FUnidade;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.crud.tabbed.FMoeda;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;

public class FImportacao extends FDetalhe implements ActionListener, ChangeListener, InsertListener, DeleteListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad 	tpnGrid 			= 	new JTabbedPanePad();
	private JTabbedPanePad 	tpnCab	 			= 	new JTabbedPanePad();
	
	private JPanelPad 		pnCliCabComplementar= 	new JPanelPad( 500, 300 );
	private JPanelPad 		pnCliCabPrincipal	= 	new JPanelPad( 500, 300 );

	private JTextFieldPad 	txtCodImp 			= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldPad 	txtInvoice 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtDI	 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 10	, 0 );
	private JTextFieldPad 	txtManifesto		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtCertOrigem		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 200	, 0 );
	private JTextFieldPad 	txtLacre			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 100	, 0 );
	private JTextFieldPad 	txtPresCarga		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 200	, 0 );
	private JTextFieldPad 	txtIdentHouse		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 100	, 0 );
	private JTextFieldPad 	txtDta				= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 50	, 0 );
	private JTextFieldPad 	txtConhecCarga		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 100	, 0 );
	private JTextFieldPad 	txtIdentContainer	= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtTipoManifesto	= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtDtImp			= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtEmb			= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtChegada		= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtDesembDI		= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtRegDI			= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextAreaPad 	txtaObs				= 	new JTextAreaPad( 1024 );
	private JTextFieldPad 	txtLocalEmb			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING 	, 200	, 0 );
	private JTextFieldPad 	txtVeiculo			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING 	, 200	, 0 );
	private JTextFieldPad 	txtRecintoAduaneiro	= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING 	, 200	, 0 );
	private JTextFieldPad 	txtLocalDesembDI	= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING 	, 60	, 0 );
	private JTextFieldPad 	txtSiglaUFDesembDI	= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING 	, 2		, 0 );
	private JTextFieldPad 	txtCodPaisDesembDI	= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER 	, 8		, 0 );

	private JTextFieldPad 	txtPesoBrutoTot		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );
	private JTextFieldPad 	txtPesoLiquidoTot	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );
	private JTextFieldPad 	txtVlrFreteMITOT	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrFreteTOT 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLEMITOT		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLDMITOT		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLETOT	 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLDTOT			= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrSeguroMITOT	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrSeguroTOT		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrIITOT	 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrIPITOT 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrPISTOT 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrCOFINSTOT		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrDireitosAD	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrTHCTOT 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrTHCMITOT 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrTXSisComexTOT	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	
	private JTextFieldPad 	txtCotacaoMoeda 		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );

	private JTextFieldPad 	txtCodFor 			= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldFK 	txtRazFor 			= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 50	, 0 );
	private JTextFieldFK 	txtCodPais 			= 	new JTextFieldFK( 	JTextFieldPad.TP_INTEGER	, 4		, 0 );
	
	private JTextFieldPad 	txtCodPlanoPag 		= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldFK 	txtDescPlanoPag 	= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 50	, 0 );
	
	private JTextFieldPad 	txtCodMoeda 		= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 4		, 0 );
	private JTextFieldFK 	txtSingMoeda 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 20	, 0 );

	private JTextFieldPad 	txtCodItImp 		= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 4		, 0 );
	private JTextFieldPad 	txtCodProd 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 4		, 0 );
	private JTextFieldFK 	txtRefProdPd 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldFK 	txtCodFabProd 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldFK 	txtDescProd 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 100	, 0 );
	private JTextFieldPad 	txtCodUnid 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldFK 	txtDescUnid 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 20	, 0 );

	private JTextFieldPad 	txtCodFisc 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 13	, 0 );
	private JTextFieldPad 	txtCodItFisc 		= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 5		, 0 );
	private JTextFieldFK 	txtCodNCM	 		= 	new JTextFieldFK( 	JTextFieldPad.TP_STRING		, 10	, 0 );
	
	private JTextFieldPad	txtQtd				= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 10	, Aplicativo.casasDec );
	private JTextFieldPad 	txtPesoBruto		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );
	private JTextFieldPad 	txtPesoLiquido  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );
	private JTextFieldPad 	txtPrecoMI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecPre );
	
	private JTextFieldPad 	txtVMLEMI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLDMI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLE			  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLD			  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrFreteMI	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrFrete		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrSeguroMI	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrSeguro	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrTHCMI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrTHC		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrADMI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrAD		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqICMSImp	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqICMSUF	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtPercDiferICMS  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqPIS		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqCOFINS	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqII		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtAliqIPI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrII		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrIPI		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrPIS		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrCOFINS	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrBaseICMS	  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrICMS		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrICMSDiferido 	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrICMSDevido 	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrICMSRedPresum	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrICMSRecolhimento 	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL, 15	, Aplicativo.casasDecFin );
	
	private JTextFieldFK 	txtAliqIIFisc 		= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldFK 	txtAliqIPIFisc 		= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldFK 	txtAliqPISFisc 		= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldFK 	txtAliqCOFINSFisc	= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldFK 	txtAliqICMSUFFisc 	= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldFK 	txtAliqICMSImpFisc 	= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	
	private JTextFieldFK 	txtItCodFisc 		= 	new JTextFieldFK( 	JTextFieldPad.TP_INTEGER	, 5		, 0 );
	
	private JTextFieldFK 	txtPesoLiqProd 		= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 10	, Aplicativo.casasDec );
	private JTextFieldFK 	txtPesoBrutProd 	= 	new JTextFieldFK( 	JTextFieldPad.TP_DECIMAL	, 10	, Aplicativo.casasDec );

	private JButtonPad btRateioFrete = new JButtonPad( Icone.novo( "btTrocaNumero.gif" ) );

	private ListaCampos lcForneced = new ListaCampos( this, "FR" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcMoeda = new ListaCampos( this, "MI" );

	private ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private ListaCampos lcUnid = new ListaCampos( this, "UN" );
	
	private ListaCampos lcClFiscal = new ListaCampos( this, "CF" );
	
	private ListaCampos lcItClFiscal = new ListaCampos( this );

	private JPanelPad pinDet = new JPanelPad();

	public JTablePad tabNCM = new JTablePad();
	
	public JScrollPane spTabNCM = new JScrollPane( tabNCM );
	
	private String codmoeda = Aplicativo.codmoeda.trim();

	public FImportacao() {

		nav.setNavigation( true );

		setTitulo( "Compras (Importação)" );

		setAltCab( 300 );
		setAtribos( 20, 20, 930, 700 );

		montaRadios();
		montaListaCampos();

		montaTela();

		adicListeners();

		adicToolTips();

	}

	private void adicToolTips() {

		btRateioFrete.setToolTipText( "Rateia o valor total do frete entre os ítens" );

	}

	private void adicListeners() {

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btRateioFrete.addActionListener( this );
		
		
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );

		lcDet.addPostListener( this );

		lcDet.addDeleteListener( this );

		lcForneced.addCarregaListener( this );
		lcClFiscal.addCarregaListener( this );
		lcItClFiscal.addCarregaListener( this );

		tpnGrid.addChangeListener( this );

	}

	private void montaRadios() {
/*
		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();
		vVals.addElement( "1" );
		vVals.addElement( "2" );
		vLabs.addElement( "CIF" );
		vLabs.addElement( "FOB" );
		rgTipoFrete = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );

		vVals = new Vector<String>();
		vLabs = new Vector<String>();
		vVals.addElement( "P" );
		vVals.addElement( "A" );
		vLabs.addElement( "Pago" );
		vLabs.addElement( "À pagar" );
		rgTipoPgto = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );
		*/
	}

	private void montaListaCampos() {

		/********************
		 * FORNECEDOR *
		 ********************/
		lcForneced.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.For.", ListaCampos.DB_PK, true ) );
		lcForneced.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForneced.add( new GuardaCampo( txtCodPais, "CodPais", "Cód.País", ListaCampos.DB_SI, false ) );

		txtCodFor.setTabelaExterna( lcForneced, FTransp.class.getCanonicalName() );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		
		lcForneced.montaSql( false, "FORNECED", "CP" );
		lcForneced.setReadOnly( true );


		/**********************
		 * PLANO DE PAGAMENTO *
		 *********************/
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.Pag.", ListaCampos.DB_PK, true ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, FCliente.class.getCanonicalName() );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		txtCodPlanoPag.setFK( true );
		
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );

		/************
		 * MOEDA    *
		 ************/
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtSingMoeda, "SingMoeda", "Singular", ListaCampos.DB_SI, false ) );
			
		txtCodMoeda.setTabelaExterna( lcMoeda, FMoeda.class.getCanonicalName() );
		txtCodMoeda.setNomeCampo( "CodCompra" );
		txtCodMoeda.setFK( true );
		
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setReadOnly( true );
		
		/**********************
		 * PRODUTO POR CODIGO *
		 **********************/
		lcProduto.add( new GuardaCampo( txtCodProd		, "CodProd"		, "Cód.Prod."				, ListaCampos.DB_PK, true ) );
		lcProduto.add( new GuardaCampo( txtRefProdPd	, "RefProd"		, "Referência"				, ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodFabProd	, "CodFabProd"	, "Cod.Fabric."				, ListaCampos.DB_SI, false ) );
		
		lcProduto.add( new GuardaCampo( txtDescProd		, "DescProd"	, "Descrição do produto"	, ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodUnid		, "CodUnid"		, "Cód.Unid."				, ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodFisc		, "CodFisc"		, "Cód.Fisc."				, ListaCampos.DB_FK, false ) );
		lcProduto.add( new GuardaCampo( txtPesoLiqProd	, "PesoLiqProd"	, "Peso Liq."				, ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtPesoBrutProd	, "PesoBrutProd", "Peso Bruto"				, ListaCampos.DB_SI, false ) );
		
		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		lcProduto.setReadOnly( true );
		
		/**********************
		 * UNIDADE *
		 **********************/
		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cód.unid.", ListaCampos.DB_PK, false ) );
		lcUnid.add( new GuardaCampo( txtDescUnid, "DescUnid", "Unidade", ListaCampos.DB_SI, false ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setTabelaExterna( lcUnid, FUnidade.class.getCanonicalName() );
		
		/**********************
		 * CLASSIFICAÇÂO FISCAL *
		 **********************/
		lcClFiscal.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_PK, false ) );
		lcClFiscal.add( new GuardaCampo( txtCodNCM, "CodNCM", "NCM", ListaCampos.DB_SI, false ) );
		lcClFiscal.montaSql( false, "CLFISCAL", "LF" );
		lcClFiscal.setReadOnly( true );
		lcClFiscal.setQueryCommit( false );
		txtCodFisc.setTabelaExterna( lcClFiscal, FCLFiscal.class.getCanonicalName() );

		/********************************
		 * ITEM DE CLASSIFICACAO FISCAL *
		 ********************************/

		lcItClFiscal.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_PK, false ) );
		lcItClFiscal.add( new GuardaCampo( txtCodItFisc, "CodItFisc", "Cód.It.fisc.", ListaCampos.DB_PK, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqIIFisc, "AliqIIFisc", "Aliq.II", ListaCampos.DB_SI, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqIPIFisc, "AliqIPIFisc", "Aliq.IPI", ListaCampos.DB_SI, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqPISFisc, "AliqPisFisc", "Aliq.Pis", ListaCampos.DB_SI, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqCOFINSFisc, "AliqCofinsFisc", "Aliq.Cofins", ListaCampos.DB_SI, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqICMSUFFisc, "AliqFisc", "Aliq.ICMS.UF", ListaCampos.DB_SI, false ) );
		lcItClFiscal.add( new GuardaCampo( txtAliqICMSImpFisc, "AliqIcmsImp", "Aliq.ICMS.Imp.", ListaCampos.DB_SI, false ) );

		lcItClFiscal.montaSql( false, "ITCLFISCAL", "LF" );
		lcItClFiscal.setReadOnly( true );
		lcItClFiscal.setQueryCommit( false );
		txtCodItFisc.setTabelaExterna( lcItClFiscal, FCLFiscal.class.getCanonicalName() );

	}

	private void montaTela() {

	   /*********************************
		* 
		* DEFINIÇÕES DO CABEÇALHO
		* 
		********************************/

		setAltCab( 250 );
		
		setListaCampos( lcCampos );
		lcCampos.setNavegador( nav );
		
		pnCab.add( tpnCab );
		
		tpnCab.add( "Principal" 	, pnCliCabPrincipal );
		tpnCab.add( "Complementar" 	, pnCliCabComplementar );
		
		// Criando abas
		
		pnMaster.remove( spTab );
		
		pnMaster.add( tpnGrid );
		
		tpnGrid.add( "Itens", spTab );
		tpnGrid.add( "Agrupamento (NCM)", spTabNCM );
	
		
		JPanelPad pnValoresTotaisMI = new JPanelPad();
		pnValoresTotaisMI.setBorder( SwingParams.getPanelLabel( "Valores em moeda estrangeira", Color.BLUE ) );
		
		JPanelPad pnValoresTotais = new JPanelPad();
		pnValoresTotais.setBorder( SwingParams.getPanelLabel( "Valores em " + codmoeda, SwingParams.COR_VERDE_FREEDOM ) );
		
		
		// Adicionando campos na aba principal
		
		setPainel( pnCliCabPrincipal );
		
		adicCampo( txtCodImp		, 7		, 20	, 70	, 20	, "CodImp"		, "Cód. Imp."	, ListaCampos.DB_PK	, true 	);
		adicCampo( txtCodFor		, 80	, 20	, 60	, 20	, "CodFor"		, "Cód. For."	, ListaCampos.DB_FK	, true 	);		
		adicDescFK( txtRazFor		, 143	, 20	, 250	, 20	, "RazFor"		, "Razão Social do Fornecedor" );

		adicCampo( txtDtImp			, 7		, 60	, 70	, 20	, "DtImp"		, "Data"		, ListaCampos.DB_SI	, true 	);
		
		adicCampo( txtCodPlanoPag	, 80	, 60	, 60	, 20	, "CodPlanoPag"	, "Cód. Pag."	, ListaCampos.DB_FK	, true 	);		
		adicDescFK( txtDescPlanoPag	, 143	, 60	, 250	, 20	, "DescPlanoPag", "Descrição do plano de pagamento" );

		adicCampo( txtCotacaoMoeda	, 7		, 100	, 70	, 20	, "CotacaoMoeda", "Cotação"		, ListaCampos.DB_SI	, true 	);	
		
		adicCampo( txtCodMoeda		, 80	, 100	, 60	, 20	, "CodMoeda"	, "Moeda"		, ListaCampos.DB_FK	, true 	);		
		adicDescFK( txtSingMoeda	, 143	, 100	, 250	, 20	, "SingMoeda"	, "" );
		
		adicCampo( txtPesoBrutoTot	, 7		, 140	, 100	, 20	, "PesoBruto"	, "Peso Bruto"	, ListaCampos.DB_SI	, false	);
		adicCampo( txtPesoLiquidoTot, 110	, 140	, 100	, 20	, "PesoLiquido"	, "Peso Liquido", ListaCampos.DB_SI	, false	);
		
		txtPesoBrutoTot.setSoLeitura( true );
		txtPesoLiquidoTot.setSoLeitura( true );
		
		adic( btRateioFrete, 213, 140, 50, 30 );
		
		pnCliCabPrincipal.adic( pnValoresTotaisMI	, 396	, 0	, 250	, 185 );
		pnCliCabPrincipal.adic( pnValoresTotais		, 649	, 0	, 250	, 185 );
		
		setPainel( pnValoresTotaisMI );
				
		adicCampo( txtVlrFreteMITOT	, 7		, 20	, 100	, 20	, "VlrFreteMI"	, "Frete"		, ListaCampos.DB_SI	, true 	);
		adicCampo( txtVlrSeguroMITOT, 110	, 20	, 100	, 20	, "VlrSeguroMI"	, "Seguro"		, ListaCampos.DB_SI	, true 	);
		adicCampo( txtVlrTHCMITOT	, 7		, 60	, 100	, 20	, "VlrTHCMI"	, "THC"			, ListaCampos.DB_SI	, false	);
		
		adicCampo( txtVMLEMITOT		, 7		, 100	, 100	, 20	, "VMLEMI"		, "VMLE"		, ListaCampos.DB_SI	, false	);
		adicCampo( txtVMLDMITOT		, 110	, 100	, 100	, 20	, "VMLDMI"		, "VMLD"		, ListaCampos.DB_SI	, false	);
		
		setPainel( pnValoresTotais );
		
		adicCampo( txtVlrFreteTOT	, 7		, 20	, 100	, 20	, "VlrFrete"	, "Frete "	+ codmoeda	, ListaCampos.DB_SI	, false	);
		adicCampo( txtVlrSeguroTOT	, 110	, 20	, 100	, 20	, "VlrSeguro"	, "Seguro "	+ codmoeda	, ListaCampos.DB_SI	, false	);		
		adicCampo( txtVlrTHCTOT		, 7		, 60	, 100	, 20	, "VlrTHC"		, "THC "	+ codmoeda	, ListaCampos.DB_SI	, true 	);
		
		adicCampo( txtVMLETOT		, 7		, 100	, 100	, 20	, "VMLE"		, "VMLE "	+ codmoeda	, ListaCampos.DB_SI	, false	);
		adicCampo( txtVMLDTOT		, 110	, 100	, 100	, 20	, "VMLD"		, "VMLD "	+ codmoeda	, ListaCampos.DB_SI	, false	);
		
		txtVlrFreteTOT.setSoLeitura( true );
		txtVlrSeguroTOT.setSoLeitura( true );
		txtVlrTHCMITOT.setSoLeitura( true );
		txtVMLETOT.setSoLeitura( true );
		txtVMLEMITOT.setSoLeitura( true );
		txtVMLDTOT.setSoLeitura( true );
		txtVMLDMITOT.setSoLeitura( true );
		
		
		setPainel( pnCliCabPrincipal );
		
		
		// Adicionando campos na aba complementar
		
		setPainel( pnCliCabComplementar );
		
		adicCampo( txtInvoice			, 7		, 20	, 100	, 20	, "Invoice"			, "Invoice"			, ListaCampos.DB_SI	, false );
		adicCampo( txtDI				, 110	, 20	, 100	, 20	, "DI"				, "D.I."			, ListaCampos.DB_SI	, false );
		
		adicCampo( txtVlrIITOT			, 223	, 20	, 100	, 20	, "VlrII"			, "Vlr.II"			, ListaCampos.DB_SI	, false );
		adicCampo( txtVlrIPITOT			, 336	, 20	, 100	, 20	, "VlrIPI"			, "Vlr.IPI"			, ListaCampos.DB_SI	, false );
		adicCampo( txtVlrPISTOT			, 449	, 20	, 100	, 20	, "VlrPIS"			, "Vlr.PIS"			, ListaCampos.DB_SI	, false );
		
		adicCampo( txtVlrTXSisComexTOT	, 7		, 60	, 100	, 20	, "VlrTXSisComex"	, "Tx.SisComex"		, ListaCampos.DB_SI	, false );
		
		txtVlrIITOT.setSoLeitura( true );
		txtVlrIPITOT.setSoLeitura( true );
		txtVlrPISTOT.setSoLeitura( true );
		
		txtVlrTXSisComexTOT.setSoLeitura( true );
		txtVMLDMITOT.setSoLeitura( true );
		txtVMLEMITOT.setSoLeitura( true );
		txtVMLDTOT.setSoLeitura( true );
		txtVMLETOT.setSoLeitura( true );
		
		
//		JPanelPad pnValoresTotaisTributos = new JPanelPad();
//		pnValoresTotaisTributos.setBorder( SwingParams.getPanelLabel( "Totais", Color.BLUE ) );

//		setPainel( pnValoresTotaisTributos );
		
	
		
		
		// Definindo a tabela do banco de dados
		setListaCampos( true, "IMPORTACAO", "CP" );
		lcCampos.setQueryInsert( true );

	   /*********************************
		* 
		* DEFINIÇÕES DO DETALHAMENTO
		* 
		********************************/
		
		setAltDet( 200 );
				
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		
		lcDet.setMaster( lcCampos );
		
		// Adicionando campos
		adicCampo( txtCodItImp		, 7		, 20	, 50	, 20	, "CodItImp"			, "Item"					, ListaCampos.DB_PK	, true 	);
	
		adicCampoInvisivel( txtRefProdPd, "refprod", "Referência", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodProd		, 60	, 20	, 100	, 20	, "CodProd"				, "Cód.Prod."				, ListaCampos.DB_FK	, true 	);
		
		adicDescFK( txtDescProd		, 163	, 20	, 355	, 20	, "DescProd"			, "Descrição do produto" );
		
		adicCampo( txtCodUnid		, 521	, 20	, 40	, 20	, "CodUnid"				, "Unid."					, ListaCampos.DB_FK	, false	);		
		adicCampo( txtQtd			, 564	, 20	, 80	, 20	, "Qtd"					, "Qtd."					, ListaCampos.DB_SI	, true 	);
		adicCampo( txtPesoBruto		, 647	, 20	, 80	, 20	, "PesoBruto"			, "Peso.Bruto"				, ListaCampos.DB_SI	, true 	);
		adicCampo( txtPesoLiquido	, 730	, 20	, 80	, 20	, "PesoLiquido"			, "Peso.Liq."				, ListaCampos.DB_SI	, true 	);
		
		adicCampo( txtPrecoMI		, 813	, 20	, 80	, 20	, "PrecoMI"				, "Preço " 					, ListaCampos.DB_SI	, false ).setForeground( Color.BLUE );
		
		adicCampo( txtVlrFreteMI	, 7		, 60	, 90	, 20	, "VlrFreteMI"			, "Vlr.Frete" 				, ListaCampos.DB_SI	, false	).setForeground( Color.BLUE );
		adicCampo( txtVlrFrete		, 100	, 60	, 90	, 20	, "VlrFrete"			, "Vlr.Frete " 	+ codmoeda 	, ListaCampos.DB_SI	, false ).setForeground( SwingParams.COR_VERDE_FREEDOM );

		adicCampo( txtVMLEMI		, 193	, 60	, 90	, 20	, "VMLEMI"				, "VMLE"					, ListaCampos.DB_SI	, false ).setForeground( Color.BLUE );
		adicCampo( txtVMLE			, 286	, 60	, 90	, 20	, "VMLE"				, "VMLE " 		+ codmoeda	, ListaCampos.DB_SI	, false ).setForeground( SwingParams.COR_VERDE_FREEDOM );
		
		adicCampo( txtVMLDMI		, 379	, 60	, 90	, 20	, "VMLDMI"				, "VMLD"					, ListaCampos.DB_SI	, false ).setForeground( Color.BLUE );
		adicCampo( txtVMLD			, 471	, 60	, 90	, 20	, "VMLD"				, "VMLD "		+ codmoeda	, ListaCampos.DB_SI	, false ).setForeground( SwingParams.COR_VERDE_FREEDOM );
		
		adicCampo( txtVlrTHCMI		, 564	, 60	, 80	, 20	, "VlrTHCMI"			, "THC"						, ListaCampos.DB_SI	, false ).setForeground( Color.BLUE );
		adicCampo( txtVlrTHC		, 647	, 60	, 80	, 20	, "VlrTHC"				, "THC "		+ codmoeda	, ListaCampos.DB_SI	, false ).setForeground( SwingParams.COR_VERDE_FREEDOM );
		
		adicCampo( txtCodNCM		, 7		, 100	, 90	, 20	, "CodNCM"				, "NCM"						, ListaCampos.DB_SI	, false	);
		
		adicCampoInvisivel( txtCodFisc,		 "CodFisc"				, "CodFisc"				, ListaCampos.DB_FK			, false	);
		adicCampoInvisivel( txtCodItFisc,	 "CodItFisc"			, "CodItFisc"			, ListaCampos.DB_SI			, false	);
		
		adicCampo( txtAliqII		, 100	, 100	, 90	, 20	, "AliqII"				, "% II"					, ListaCampos.DB_SI	, false	);
		adicCampo( txtAliqIPI		, 193	, 100	, 90	, 20	, "AliqIPI"				, "% IPI"					, ListaCampos.DB_SI	, false	);
		adicCampo( txtAliqPIS		, 286	, 100	, 90	, 20	, "AliqPIS"				, "% PIS"					, ListaCampos.DB_SI	, false	);
		adicCampo( txtAliqCOFINS	, 382	, 100	, 90	, 20	, "AliqCOFINS"			, "% COFINS"				, ListaCampos.DB_SI	, false	);
		adicCampo( txtAliqICMSImp	, 475	, 100	, 90	, 20	, "AliqICMSImp"			, "% ICMS Imp."				, ListaCampos.DB_SI	, false	);
		adicCampo( txtAliqICMSUF	, 568	, 100	, 90	, 20	, "AliqICMSUF"			, "% ICMS"					, ListaCampos.DB_SI	, false	);
		
		txtVlrFreteMI.setSoLeitura( true );
		txtVlrFrete.setSoLeitura( true );
		txtVMLEMI.setSoLeitura( true );
		txtVMLE.setSoLeitura( true );
		txtVMLDMI.setSoLeitura( true );
		txtVMLD.setSoLeitura( true );
		txtVlrTHC.setSoLeitura( true );
		txtVlrTHCMI.setSoLeitura( true );
		txtCodUnid.setEditable( false );
		
		txtAliqII.setEditable( false );
		txtAliqIPI.setEditable( false );
		txtAliqPIS.setEditable( false );
		txtAliqPIS.setEditable( false );
		txtAliqCOFINS.setEditable( false );
		txtAliqICMSImp.setEditable( false );
		txtAliqICMSUF.setEditable( false );
		
		// Definindo a tabela do banco de dados
		 
		setListaCampos( true, "ITIMPORTACAO", "CP" );
		lcDet.setQueryInsert( true );
		montaTab();
		
		
	}
	
	private void execRateio() {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			sql.append( "update cpitimportacao set ");

			// Rateando o Frete pelo peso bruto
			sql.append( "vlrfretemi=( ( ? * (pesobruto) ) / ? )," );

			// Rateando o THC pelo valor + frete
			sql.append( "vlrthcmi=( ( ? * (vmldmi) ) / ? ) " );

				
			sql.append( "where codemp=? and codfilial=? and codimp=?" );

			BigDecimal pesobrutoitem = null;
			
			ps = con.prepareStatement( sql.toString() );
			
			ps.setBigDecimal( 1, txtVlrFreteMITOT.getVlrBigDecimal() );
			ps.setBigDecimal( 2, txtPesoBrutoTot.getVlrBigDecimal() );

			ps.setBigDecimal( 3, txtVlrTHCMITOT.getVlrBigDecimal() );
			ps.setBigDecimal( 4, txtVMLDMITOT.getVlrBigDecimal() );
			
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "CPIMPORTACAO" ) );
			ps.setInt( 7, txtCodImp.getVlrInteger() );
			
			ps.execute();
			
			con.commit();
			
			lcDet.carregaItens();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao realizar o rateio do frete.", false, e );
			e.printStackTrace();
		}
	}

	private void buscaClassificacaoFiscal() {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			sql.append( "select coditfisc from lfitclfiscal " );
			sql.append( "where " );
			sql.append( "codemp=? and codfilial=? and codfisc=? and tipousoitfisc='CP' and codpais=?" );

			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFITCLFISCAL" ) );
			ps.setString( 3, txtCodFisc.getVlrString() );
			ps.setInt( 4, txtCodPais.getVlrInteger() );
			
			rs = ps.executeQuery();
			
			if( rs.next() ) {
				txtCodItFisc.setVlrInteger( rs.getInt( "coditfisc" ) );				
			}
			
			lcItClFiscal.carregaDados();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao realizar o rateio do frete.", false, e );
			e.printStackTrace();
		}
	}

	
	public void beforeInsert( InsertEvent e ) {

	}

	public void afterInsert( InsertEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {

		}
		else if ( e.getListaCampos() == lcDet ) {

		}
	}

	public void beforePost( PostEvent e ) {

		if ( e.getListaCampos() == lcDet ) {
			
//			buscaClassificacaoFiscal();

		}
	}
	
	public void afterPost( PostEvent e ) {

		if ( e.getListaCampos() == lcDet ) {
			
			

		}
	}

	public void beforeDelete( DeleteEvent e ) {

		if ( e.getListaCampos() == lcDet ) {
		}
	}

	public void afterDelete( DeleteEvent e ) {

		if ( e.getListaCampos() == lcDet ) {

		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcClFiscal && ( lcDet.getStatus() == ListaCampos.LCS_EDIT || lcDet.getStatus() == ListaCampos.LCS_INSERT ) ) {
			buscaClassificacaoFiscal();
		}
		if ( e.getListaCampos() == lcItClFiscal ) {
			carregaAliquotas();
		}
	}
	
	private void carregaAliquotas() {
		
		txtAliqII.setVlrBigDecimal( txtAliqIIFisc.getVlrBigDecimal() );
		txtAliqIPI.setVlrBigDecimal( txtAliqIPIFisc.getVlrBigDecimal() );
		txtAliqPIS.setVlrBigDecimal( txtAliqPISFisc.getVlrBigDecimal() );
		txtAliqCOFINS.setVlrBigDecimal( txtAliqCOFINSFisc.getVlrBigDecimal() );
		txtAliqICMSImp.setVlrBigDecimal( txtAliqICMSImpFisc.getVlrBigDecimal() );
		txtAliqICMSUF.setVlrBigDecimal( txtAliqICMSUFFisc.getVlrBigDecimal() );
		
		
	}
	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btRateioFrete ) {

			execRateio();
			
		}

		super.actionPerformed( e );
	}

	public void stateChanged( ChangeEvent e ) {

		if ( e.getSource() == tpnGrid ) {
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
	
		lcForneced.setConexao( con );
		lcProduto.setConexao( con );
		lcUnid.setConexao( con );
		lcPlanoPag.setConexao( con );
		lcMoeda.setConexao( con );
		lcClFiscal.setConexao( con );
		lcItClFiscal.setConexao( con );
		
	}
}
