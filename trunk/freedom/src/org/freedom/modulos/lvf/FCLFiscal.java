/**
 * @version 05/03/2009 <BR>
 * @author Setpoint Informática Ltda / Anderson Sanchez.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FVendaConsig.java <BR>
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
 * Cadastro de classificações fiscais e suas exceções <BR>
 * 
 */

package org.freedom.modulos.lvf;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FDetalhe;

public class FCLFiscal extends FDetalhe implements MouseListener, ChangeListener, CarregaListener, InsertListener, RadioGroupListener, PostListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnPrincipal = new JTabbedPanePad();

	private JTabbedPanePad tpnGeral = new JTabbedPanePad();

	private JPanelPad panelGeral = new JPanelPad( 500, 80 );

	private JPanelPad panelVariantesCampos = new JPanelPad( 500, 80 );

	private JPanelPad panelVariantes = new JPanelPad( new GridLayout( 1, 1 ) );

	private JPanelPad panelICMS = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private JPanelPad panelICMSCampos = new JPanelPad( 500, 80 );
	
	private JPanelPad panelIPI = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private JPanelPad panelIPICampos = new JPanelPad( 500, 80 );
	
	private JPanelPad panelPIS = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private JPanelPad panelPISCampos = new JPanelPad( 500, 80 );
	
	private JPanelPad panelCOFINS = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private JPanelPad panelCOFINSCampos = new JPanelPad( 500, 80 );
	
	private JPanelPad panelNomeComum = new JPanelPad( new BorderLayout() );
	
	private JPanelPad panelNomeComumNCM = new JPanelPad( new BorderLayout() );
	
	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodRegra = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescRegra = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcRegraFiscal = new ListaCampos( this, "RA" );
	
	private ListaCampos lcTratTrib = new ListaCampos( this, "TT" );

	private ListaCampos lcMens = new ListaCampos( this, "ME" );

	private ListaCampos lcNCM = new ListaCampos( this );

	private ListaCampos lcNBM = new ListaCampos( this );
	
	private JTextFieldPad txtCodTratTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescTratTrib = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodMens = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMens = new JTextFieldFK( JTextFieldPad.TP_STRING, 10000, 0 );

	private JTextFieldPad txtCodNBM = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDescNBM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );		

	private JTextFieldPad txtCodNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescNCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtExTIPI = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextAreaPad txaDescExTIPI = new JTextAreaPad( 1000 );
	
	private JScrollPane spDescExTIPI = new JScrollPane( txaDescExTIPI );

	private JTextAreaPad txaDescNCM = new JTextAreaPad( 2000 );
	
	private JTextFieldPad txtCodItClFiscal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 2 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 200, 0 );
	
	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	
	private JTextFieldPad txtCodTipoFisc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcTipoFiscCli = new ListaCampos( this, "FC" );
	
	private JRadioGroup<?, ?> rgNoUF = null;
	
	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtAliqFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );
	
	private JTextFieldPad txtVlrIpiUnidTrib = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtAliqLFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JTextFieldPad txtAliqPisFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JTextFieldPad txtAliqCofinsFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JComboBoxPad cbOrig = null;
	
	private JComboBoxPad cbTpCalcIPI = null;
	
	private JComboBoxPad cbModBCICMS = null;
	
	private JComboBoxPad cbModBCICMSST = null;
	
	private JRadioGroup<?, ?> rgTipoFisc = null;
	
	private Vector<String> vTipoVals = new Vector<String>();

	private Vector<String> vTipoLabs = new Vector<String>();
	
	private JRadioGroup<String, String> rgTipoST = null;
	
	private JTextFieldPad txtMargemVlAgr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JRadioGroup<String, String> rgTpRedIcmsFisc = null;
		
	private ListaCampos lcSitTribPIS = new ListaCampos( this, "SP" );
	
	private ListaCampos lcSitTribIPI = new ListaCampos( this, "SI" );
	
	private JTextFieldPad txtCodSitTribPIS = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtImpSitTribPIS = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescSitTribPIS = new JTextFieldFK( JTextFieldPad.TP_STRING, 200, 0 );
	
	private JTextFieldPad txtCodSitTribIPI = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtImpSitTribIPI = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescSitTribIPI = new JTextFieldFK( JTextFieldPad.TP_STRING, 200, 0 );

	private ListaCampos lcSitTribCOF = new ListaCampos( this, "SC" );
	
	private JTextFieldPad txtCodSitTribCOF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtImpSitTribCOF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescSitTribCOF = new JTextFieldFK( JTextFieldPad.TP_STRING, 200, 0 );
	
	private JPanelPad panelNomeComumNCMCampos = new JPanelPad( 500, 60 );
	
	private JSplitPane panelNomeComumNCMDescricoes = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
	
	private JPanelPad panelNomeComumNBM = new JPanelPad( 500, 60 );
	
	private JScrollPane spDescNCM = new JScrollPane( txaDescNCM );
	
	private JCheckBoxPad cbGeralFisc = new JCheckBoxPad( "Regra geral?", "S", "N" );
	
	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 ); 
	
	private ListaCampos lcPais = new ListaCampos( this, "" );
	
	private ListaCampos lcUF = new ListaCampos( this );

	
	public FCLFiscal() {

		super( false );
		setTitulo( "Classificações Fiscais" );
		setAtribos( 50, 50, 765, 600 );

		montaListaCampos();

		montaTela();

		adicListeners();

		
	}
	
	private void adicListeners() {

		// Adicionando valores padrão
		
		rgTipoFisc.setVlrString( "TT" );
		rgTipoST.setVlrString( "SI" );
		rgTpRedIcmsFisc.setVlrString( "B" );
		
		rgTipoST.setAtivo( false );
		txtMargemVlAgr.setAtivo( false );
		cbModBCICMSST.setAtivo( false );
		rgTpRedIcmsFisc.setAtivo( false );
		txtRedFisc.setAtivo( false );
		rgTipoFisc.setAtivo( false );
				
		//Adicionando Listeners
				
		lcCampos.addCarregaListener( this );		
		lcCampos.addInsertListener( this );		
		lcCampos.addPostListener( this );
		lcTratTrib.addCarregaListener( this );
		
		lcDet.addInsertListener( this );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		rgTipoFisc.addRadioGroupListener( this );		
		rgTipoST.addRadioGroupListener( this );

		tpnGeral.addChangeListener( this );
		tpnPrincipal.addChangeListener( this );
		
	}

	private void montaListaCampos() {
		
		lcRegraFiscal.add( new GuardaCampo( txtCodRegra, "CodRegra", "Cód.reg.fisc.", ListaCampos.DB_PK, null, true ) );
		lcRegraFiscal.add( new GuardaCampo( txtDescRegra, "DescRegra", "Descrição da regra fiscal", ListaCampos.DB_SI, null, false ) );
		lcRegraFiscal.montaSql( false, "REGRAFISCAL", "LF" );
		lcRegraFiscal.setQueryCommit( false );
		lcRegraFiscal.setReadOnly( true );
		txtCodRegra.setTabelaExterna( lcRegraFiscal );
		
		lcTratTrib.add( new GuardaCampo( txtCodTratTrib, "CodTratTrib", "Cód.t.trib.", ListaCampos.DB_PK, null, true ) );
		lcTratTrib.add( new GuardaCampo( txtDescTratTrib, "DescTratTrib", "Descrição do tratamento tributario", ListaCampos.DB_SI, null, false ) );
		lcTratTrib.montaSql( false, "TRATTRIB", "LF" );
		lcTratTrib.setQueryCommit( false );
		lcTratTrib.setReadOnly( true );
		txtCodTratTrib.setTabelaExterna( lcTratTrib );

		lcMens.add( new GuardaCampo( txtCodMens, "CodMens", "Cód.mens.", ListaCampos.DB_PK, null, false ) );
		lcMens.add( new GuardaCampo( txtDescMens, "Mens", "Mensagem", ListaCampos.DB_SI, null, false ) );
		lcMens.montaSql( false, "MENSAGEM", "LF" );
		lcMens.setQueryCommit( false );
		lcMens.setReadOnly( true );
		txtCodMens.setTabelaExterna( lcMens );

		lcNCM.setUsaME( false );
		lcNCM.add( new GuardaCampo( txtCodNCM, "CodNCM", "Cód.NCM", ListaCampos.DB_PK, txtDescNCM, false ) );
		lcNCM.add( new GuardaCampo( txtDescNCM, "DescNCM", "Descrição da nomeclatura comum do Mercosul", ListaCampos.DB_SI, null, false ) );
		lcNCM.add( new GuardaCampo( txaDescNCM, "TextoNCM", "Descrição completa da nomeclatura comum do Mercosul", ListaCampos.DB_SI, null, false ) );
		lcNCM.add( new GuardaCampo( txaDescExTIPI, "ExcecaoNCM", "Descrição das exceções", ListaCampos.DB_SI, null, false ) );
		lcNCM.setDinWhereAdic( "EXISTS(SELECT NN.CODNCM FROM LFNCMNBM NN WHERE NN.CODNCM=LFNCM.CODNCM)", txtCodNCM );
		lcNCM.montaSql( false, "NCM", "LF" );
		lcNCM.setQueryCommit( false );
		lcNCM.setReadOnly( true );
		txtCodNCM.setTabelaExterna( lcNCM );

		lcNBM.setUsaME( false );
		lcNBM.add( new GuardaCampo( txtCodNBM, "CodNBM", "Cód.NBM", ListaCampos.DB_PK, txtDescNBM, false ) );
		lcNBM.add( new GuardaCampo( txtDescNBM, "DescNBM", "Descrição da nomeclatura brasileira de mercadorias", ListaCampos.DB_SI, null, false ) );
		lcNBM.setDinWhereAdic( "EXISTS(SELECT NN.CODNBM FROM LFNCMNBM NN WHERE NN.CODNCM = #S AND NN.CODNBM=LFNBM.CODNBM)", txtCodNCM );
		lcNBM.montaSql( false, "NBM", "LF" );
		lcNBM.setQueryCommit( false );
		lcNBM.setReadOnly( true );
		txtCodNBM.setTabelaExterna( lcNBM ); 
		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.Mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		
		lcSitTribCOF.add( new GuardaCampo( txtCodSitTribCOF, "CodSitTrib", "Cód.sit.trib.", ListaCampos.DB_PK, false ) );
		lcSitTribCOF.add( new GuardaCampo( txtImpSitTribCOF, "ImpSitTrib", "Cofins", ListaCampos.DB_PK, false ) );
		lcSitTribCOF.add( new GuardaCampo( txtDescSitTribCOF, "DescSitTrib", "Descrição da Situação Tributária", ListaCampos.DB_SI, false ) );
		lcSitTribCOF.montaSql( false, "SITTRIB", "LF" );
		lcSitTribCOF.setQueryCommit( false );
		lcSitTribCOF.setReadOnly( true );
		txtCodSitTribCOF.setTabelaExterna( lcSitTribCOF );
		txtImpSitTribCOF.setTabelaExterna( lcSitTribCOF );
		lcSitTribCOF.setWhereAdic( "IMPSITTRIB='CO'" );
		
		lcTipoFiscCli.add( new GuardaCampo( txtCodTipoFisc, "CodFiscCli", "Cód.c.fisc.", ListaCampos.DB_PK, false ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição da classificação fiscal", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodTipoFisc.setTabelaExterna( lcTipoFiscCli );
		
		lcSitTribPIS.add( new GuardaCampo( txtCodSitTribPIS, "CodSitTrib", "Cód.sit.trib.", ListaCampos.DB_PK, false ) );
		lcSitTribPIS.add( new GuardaCampo( txtImpSitTribPIS, "ImpSitTrib", "Pis", ListaCampos.DB_PK, false ) );
		lcSitTribPIS.add( new GuardaCampo( txtDescSitTribPIS, "DescSitTrib", "Descrição da Situação Tributária", ListaCampos.DB_SI, false ) );
		lcSitTribPIS.montaSql( false, "SITTRIB ", "LF" ); // Nome da tabela com espaço em branco no final, para contornar bug do lista campos 
		lcSitTribPIS.setQueryCommit( false );
		lcSitTribPIS.setReadOnly( true );
		txtCodSitTribPIS.setTabelaExterna( lcSitTribPIS );
		txtImpSitTribPIS.setTabelaExterna( lcSitTribPIS );
		lcSitTribPIS.setWhereAdic( "IMPSITTRIB='PI'" );
		
		lcSitTribIPI.add( new GuardaCampo( txtCodSitTribIPI, "CodSitTrib", "Cód.sit.trib.", ListaCampos.DB_PK, false ) );
		lcSitTribIPI.add( new GuardaCampo( txtImpSitTribIPI, "ImpSitTrib", "IPI", ListaCampos.DB_PK, false ) );
		lcSitTribIPI.add( new GuardaCampo( txtDescSitTribIPI, "DescSitTrib", "Descrição da Situação Tributária", ListaCampos.DB_SI, false ) );
		lcSitTribIPI.montaSql( false, "SITTRIB  ", "LF" ); // Nome da tabela com 2 espaços em branco no final, para contornar bug do lista campos
		lcSitTribIPI.setQueryCommit( false );
		lcSitTribIPI.setReadOnly( true );
		txtCodSitTribIPI.setTabelaExterna( lcSitTribIPI );
		txtImpSitTribIPI.setTabelaExterna( lcSitTribIPI );
		lcSitTribIPI.setWhereAdic( "IMPSITTRIB='IP'" );
		
		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );

		lcUF.setUsaME( false );		
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, false ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcUF.setDinWhereAdic( "CODPAIS = #S", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF );
		
				
	}

	private void montaTela() {

		pnPrincipal.add( tpnPrincipal );

		lcDet.setMaster( lcCampos );


		// ********** Início aba Geral **********

		tpnPrincipal.addTab( "Geral", pnCliente );
		pnCab.add( panelGeral );

		setPainel( panelGeral );
		setAltCab( 130 );

		setListaCampos( lcCampos );
		
		adicCampo( txtCodFisc, 7, 20, 100, 20, "CodFisc", "Cód.class.fiscal", ListaCampos.DB_PK, true );
		adicCampo( txtDescFisc, 110, 20, 595, 20, "DescFisc", "Descrição da classificação fiscal", ListaCampos.DB_SI, true );		
		adicCampo( txtCodRegra, 7, 60, 100, 20, "CodRegra", "Cód.reg.CFOP", ListaCampos.DB_FK, txtDescRegra, true );
		adicDescFK( txtDescRegra, 110, 60, 595, 20, "DescRegra", "Descrição da regra fiscal" );		
		
	// ********** Aba Nomeclatura Comum **********

		tpnPrincipal.addTab( "Nomeclatura Comum", panelNomeComum );
		
		panelNomeComum.add( panelNomeComumNCM, BorderLayout.CENTER );
		
		panelNomeComumNCM.add( panelNomeComumNCMCampos, BorderLayout.NORTH );
		panelNomeComumNCM.add( panelNomeComumNCMDescricoes, BorderLayout.CENTER );
		
		panelNomeComumNCMDescricoes.setTopComponent( spDescNCM );
		panelNomeComumNCMDescricoes.setBottomComponent( spDescExTIPI );
		panelNomeComumNCMDescricoes.setDividerLocation( 220 );
		panelNomeComumNCMDescricoes.setDividerSize( 3 );
		panelNomeComumNCMDescricoes.setBorder( BorderFactory.createEmptyBorder() );
		
		panelNomeComum.add( panelNomeComumNBM, BorderLayout.SOUTH );
		
		spDescNCM.setBorder( BorderFactory.createTitledBorder( "Descrição completa da nomeclatura comum do Mercosul" ) );		
		spDescExTIPI.setBorder( BorderFactory.createTitledBorder( "Texto auxíliar para exceções" ) );		
		txaDescNCM.setBorder( BorderFactory.createEtchedBorder() );
		txaDescExTIPI.setBorder( BorderFactory.createEtchedBorder() );
		
		setPainel( panelNomeComumNCMCampos );
		
		adicCampo( txtCodNCM, 7, 20, 100, 20, "CodNCM", "Cód.NCM", ListaCampos.DB_FK, txtDescNCM, false );
		adicDescFK( txtDescNCM, 110, 20, 320, 20, "DescNCM", "Descrição da nomeclatura comum do Mercosul" );	
		adicCampo( txtExTIPI, 433, 20, 47, 20, "ExTIPI", "Cód.ex.", ListaCampos.DB_SI, null, false );	
		
		setPainel( panelNomeComumNBM );
		
		adicCampo( txtCodNBM, 7, 25, 100, 20, "CodNBM", "Cód.NBM", ListaCampos.DB_FK, txtDescNBM, false );
		adicDescFK( txtDescNBM, 110, 25, 370, 20, "DescNBM", "Descrição da nomeclatura brasileira de mercadorias" );		

		// *******************************
		
		txtCodFisc.setTabelaExterna( lcCampos );
		setListaCampos( true, "CLFISCAL", "LF" );
		lcCampos.setQueryInsert( false );

		/*****************
		 * ABA VARIANTES
		 ****************/

		pnDet.add( tpnGeral );
		tpnGeral.addTab( "Variantes", panelVariantes );

		setPainel( panelVariantesCampos );
		setAltDet( 230 );

		setListaCampos( lcDet );
		setNavegador( navRod );

		Vector<String> vNoUFLabs = new Vector<String>();
		Vector<String> vNoUFVals = new Vector<String>();
		
		vNoUFLabs.addElement( "Dentro do Estado" );
		vNoUFLabs.addElement( "Fora do Estado" );
				
		vNoUFVals.addElement( "S" );
		vNoUFVals.addElement( "N" );
		
		rgNoUF = new JRadioGroup<String, String>( 2, 1, vNoUFLabs, vNoUFVals );
		rgNoUF.setVlrString( "S" );
				
		adicCampoInvisivel( txtCodItClFiscal, "CodItFisc", "Item", ListaCampos.DB_PK, true );

		adicCampo( txtCodTipoFisc, 7, 20, 70, 20, "CodFiscCli", "Cód.fisc.cli.", ListaCampos.DB_FK, txtDescFiscCli, false );
		adicDescFK( txtDescFiscCli, 80, 20, 388, 20, "DescFiscCli", "Descrição do tipo fiscal de cliente" );

		adicCampo( txtCodTipoMov, 7, 60, 70, 20, "CodTipoMov", "Cód.tp.Mov", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMov, 80, 60, 388, 20, "DescTipoMov", "Descrição do tipo de movimento" );
		
		adicCampo( txtCodMens, 7, 100, 70, 20, "CodMens", "Cód.mens.", ListaCampos.DB_FK, txtDescMens, false );
		adicDescFK( txtDescMens, 80, 100, 388, 20, "Mens", "Mensagem" );

		adicDB( rgNoUF, 471, 20, 150, 60, "NoUFItFisc", "Destino da mercadoria", true );
		
		adicDB( cbGeralFisc, 471,100,150,20, "GeralFisc", "Padrão", true );
		
		adicCampo( txtCodPais, 7, 140, 70, 20, "CodPais", "Cod.país", ListaCampos.DB_FK, txtDescPais, false );
		adicDescFK( txtDescPais, 80, 140, 227, 20, "NomePais", "Nome do país" );
		adicCampo( txtSiglaUF, 310, 140, 70, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_FK, txtNomeUF, false );
		adicDescFK( txtNomeUF, 383, 140, 238, 20, "NomeUF", "Nome UF" );

		
		
		
		
		/*****************
		 * ABA ICMS
		 ****************/

		tpnGeral.addTab( "ICMS", panelICMS );
		
		setPainel( panelICMSCampos );
		
		/*********************************************
		 * 
		 * Origem da mercadoria  
		 * 
		 *********************************************/
		
		Vector<String> vLabsOrig = new Vector<String>();		
		Vector<String> vValsOrig = new Vector<String>();
		
		vLabsOrig.addElement( "<--Selecione-->" );
		vLabsOrig.addElement( "Nacional" );
		vLabsOrig.addElement( "Estrangeira - Importação direta" );
		vLabsOrig.addElement( "Estrangeira - Adquirida no mercado interno" );
		vValsOrig.addElement( "" );
		vValsOrig.addElement( "0" );
		vValsOrig.addElement( "1" );
		vValsOrig.addElement( "2" );

		cbOrig = new JComboBoxPad( vLabsOrig, vValsOrig, JComboBoxPad.TP_STRING, 1, 0 );
		
		/*********************************************
		 * 
		 * Tipo do ICMS  
		 * 
		 *********************************************/
		
		vTipoLabs.addElement( "Isento" );
		vTipoLabs.addElement( "Subst. Trib." );
		vTipoLabs.addElement( "Não inside" );
		vTipoLabs.addElement( "Trib. Integral" );
		vTipoVals.addElement( "II" );
		vTipoVals.addElement( "FF" );
		vTipoVals.addElement( "NN" );
		vTipoVals.addElement( "TT" );
		rgTipoFisc = new JRadioGroup<String, String>( 2, 2, vTipoLabs, vTipoVals );
		
		/*********************************************
		 * 
		 * Tipo de substituição tributária 
		 * 
		 *********************************************/
		
		Vector<String> vSTLabs = new Vector<String>();
		vSTLabs.addElement( "Substituto" );
		vSTLabs.addElement( "Substituído" );
		Vector<String> vSTVals = new Vector<String>();
		vSTVals.addElement( "SU" );
		vSTVals.addElement( "SI" );
		rgTipoST = new JRadioGroup<String, String>( 2, 1, vSTLabs, vSTVals );

		Vector<String> vTpRedIcmsFiscLabs = new Vector<String>();
		vTpRedIcmsFiscLabs.addElement( "Base ICMS" );
		vTpRedIcmsFiscLabs.addElement( "Valor ICMS" );
		Vector<String> vTpRedIcmsFiscVals = new Vector<String>();
		vTpRedIcmsFiscVals.addElement( "B" );
		vTpRedIcmsFiscVals.addElement( "V" );
		rgTpRedIcmsFisc = new JRadioGroup<String, String>( 2, 1, vTpRedIcmsFiscLabs, vTpRedIcmsFiscVals );

		/*********************************************
		 * 
		 * Modalidade de determinação da BC do ICMS
		 * 
		 *********************************************/
		
		Vector<String> vLabsModBCICMS = new Vector<String>();		
		Vector<Integer> vValsModBCICMS = new Vector<Integer>();
		
		vLabsModBCICMS.addElement( "0-Margem Valor Agregado (%)" );
		vLabsModBCICMS.addElement( "1-Pauta (valor)" );
		vLabsModBCICMS.addElement( "2-Preço Tabelado Máx.(valor)" );
		vLabsModBCICMS.addElement( "3-Valor da Operação" );
		vValsModBCICMS.addElement( new Integer(0) );
		vValsModBCICMS.addElement( new Integer(1) );
		vValsModBCICMS.addElement( new Integer(2) );
		vValsModBCICMS.addElement( new Integer(3) );
		
		cbModBCICMS = new JComboBoxPad( vLabsModBCICMS, vValsModBCICMS, JComboBoxPad.TP_INTEGER, 1, 0 );
		cbModBCICMS.setVlrInteger( new Integer(3) );

		/*********************************************
		 * 
		 * Modalidade de determinação da BC do ICMS de Substituição tributária
		 * 
		 *********************************************/
		
		Vector<String> vLabsModBCICMSST = new Vector<String>();		
		Vector<Integer> vValsModBCICMSST = new Vector<Integer>();
		
		vLabsModBCICMSST.addElement( "0-Preço tabelado ou máx. sugerido" );
		vLabsModBCICMSST.addElement( "1-Lista Negativa (valor)" );
		vLabsModBCICMSST.addElement( "2-Lista Positiva (valor)" );
		vLabsModBCICMSST.addElement( "3-Lista Neutra (valor)" );
		vLabsModBCICMSST.addElement( "4-Margem valor agregado (%)" );
		vLabsModBCICMSST.addElement( "5-Pauta (valor)" );

		vValsModBCICMSST.addElement( new Integer(0) );
		vValsModBCICMSST.addElement( new Integer(1) );
		vValsModBCICMSST.addElement( new Integer(2) );
		vValsModBCICMSST.addElement( new Integer(3) );
		vValsModBCICMSST.addElement( new Integer(4) );
		vValsModBCICMSST.addElement( new Integer(5) );
		
		cbModBCICMSST = new JComboBoxPad( vLabsModBCICMSST, vValsModBCICMSST, JComboBoxPad.TP_INTEGER, 1, 0 );		
		cbModBCICMSST.setVlrInteger( new Integer(4) );
		
		/*********************************************
		 * 
		 * Tipo de cálculo do IPI  
		 * 
		 *********************************************/
		
		Vector<String> vLabsTpCalcIPI = new Vector<String>();		
		Vector<String> vValsTpCalcIPI = new Vector<String>();
		
		vLabsTpCalcIPI.addElement( "<--Selecione-->" );
		vLabsTpCalcIPI.addElement( "Percentual" );
		vLabsTpCalcIPI.addElement( "Em valor" );
		vValsTpCalcIPI.addElement( "" );
		vValsTpCalcIPI.addElement( "P" );
		vValsTpCalcIPI.addElement( "V" );
		

		cbTpCalcIPI = new JComboBoxPad( vLabsTpCalcIPI, vValsTpCalcIPI, JComboBoxPad.TP_STRING, 1, 0 );
		
		
		/*********************************************
		 * 
		 * Inclusão dos campos
		 * 
		 *********************************************/		

		adicCampo( txtCodTratTrib, 7, 20, 50, 20, "CodTratTrib", "Cód.trat.", ListaCampos.DB_FK, txtDescTratTrib, true );
		adicDescFK( txtDescTratTrib, 60, 20, 200, 20, "DescTratTrib", "Descrição da tratamento tributário" );

		adicDB( cbOrig, 7, 60, 253, 25, "OrigFisc", "Origem", true );
		adicDB( cbModBCICMS, 7, 110, 253, 25, "ModBcIcms", "Modalidade da base de cálculo ", true );
		adicDB( cbModBCICMSST, 7, 160, 253, 25, "ModBcIcmsST", "Modalidade da base de cálculo ST", true );
		
		JLabelPad separacao = new JLabelPad();
		separacao.setBorder( BorderFactory.createEtchedBorder() );
		adic( separacao, 272, 7, 2, 180 );
		
		adicDB( rgTipoFisc, 283, 20, 220, 70, "TipoFisc", "Situação do ICMS:", true );		
		adicDB( rgTipoST, 506, 20, 110, 70, "TipoST", "Tipo de Sub.Trib.", true );
		adicDB( rgTpRedIcmsFisc, 619, 20, 110, 70, "tpredicmsfisc", "Tipo de Redução", false );
		
		adicCampo( txtMargemVlAgr, 506, 110, 110, 20, "MargemVlAgr", "% Vlr.Agregado" , ListaCampos.DB_SI,  false );
		adicCampo( txtRedFisc, 619, 110, 110, 20, "RedFisc", "% Redução ICMS", ListaCampos.DB_SI, false );
		
		adicCampo( txtAliqFisc, 283, 110, 108, 20, "AliqFisc", "% Alíq.ICMS", ListaCampos.DB_SI, false );		
		adicCampo( txtAliqLFisc, 394, 110, 110, 20, "AliqlFisc", "% Aliq.liv.ICMS", ListaCampos.DB_SI, null, false );

		/*****************
		 * ABA IPI
		 ****************/
		
		tpnGeral.addTab( "IPI", panelIPI );
		setPainel( panelIPICampos );
		
		adicCampo( txtCodSitTribIPI, 7, 20, 80, 20, "CodSitTribIPI", "Cód.sit.trib.", ListaCampos.DB_FK, txtDescSitTribPIS, false );
		adicCampoInvisivel( txtImpSitTribIPI, "ImpSitTribIPI", "Imposto", ListaCampos.DB_FK, false );
		adicDescFK( txtDescSitTribIPI, 90, 20, 300, 20, "DescSitTrib", "Descrição da Situação Tributária" );
		
		adicDB( cbTpCalcIPI, 7, 60, 200, 20, "TpCalcIPI", "Tipo de cálculo", false );
				
		adicCampo( txtAliqIPIFisc, 7, 100, 98, 20, "AliqIPIFisc", "% Alíq.IPI", ListaCampos.DB_SI, false );
		adicCampo( txtVlrIpiUnidTrib, 108, 100, 99, 20, "VlrIPIUnidTrib", "Vlr.por unidade", ListaCampos.DB_SI, false );
		
		/*****************
		 * ABA PIS
		 ****************/
						
		tpnGeral.addTab( "PIS", panelPIS );
		setPainel( panelPISCampos );
		
		adicCampo( txtCodSitTribPIS, 7, 20, 80, 20, "CodSitTribPIS", "Cód.sit.trib.", ListaCampos.DB_FK, txtDescSitTribPIS, false );
		adicCampoInvisivel( txtImpSitTribPIS, "ImpSitTribPIS", "Imposto", ListaCampos.DB_FK, false );
		adicDescFK( txtDescSitTribPIS, 90, 20, 300, 20, "DescSitTrib", "Descrição da Situação Tributária" );		
		adicCampo( txtAliqPisFisc, 7, 60, 80, 20, "AliqPisFisc", "Aliq.PIS", ListaCampos.DB_SI, null, false );	
		
		
		/*****************
		 * ABA COFINS
		 ****************/
				
		tpnGeral.addTab( "COFINS", panelCOFINS );
		setPainel( panelCOFINSCampos );
		
		adicCampo( txtCodSitTribCOF, 7, 20, 80, 20, "CodSitTribCOF", "Cód.sit.trib.", ListaCampos.DB_FK, txtDescSitTribCOF, false );	
		adicCampoInvisivel( txtImpSitTribCOF, "ImpSitTribCOF", "Imposto", ListaCampos.DB_FK, false );
		adicDescFK( txtDescSitTribCOF, 90, 20, 300, 20, "DescSitTrib", "Descrição da Situação Tributária" );		
	
		adicCampo( txtAliqCofinsFisc, 7, 60, 80, 20, "AliqCofinsFisc", "Aliq.Cofins", ListaCampos.DB_SI, null, false );	
			
		
		
		setListaCampos( true, "ITCLFISCAL", "LF" );
		lcDet.setQueryInsert( false );

		
		montaTab();

		tab.setTamColuna( 30, 0 ); // Ítem
		tab.setColunaInvisivel( 1 ); //Cod. fisc. cli
		tab.setTamColuna( 150, 2 ); // Desc fisc. cli
		tab.setTamColuna( 30, 3 ); // cod. tipomov
		tab.setTamColuna( 150, 4 ); // desc. tipomo
		tab.setColunaInvisivel( 5 ); // cod. mensagem
		tab.setTamColuna( 150, 6 ); // desc. mensagem
		tab.setTamColuna( 30, 7 ); // Destino
		tab.setTamColuna( 30, 8 ); // Padrão
		tab.setColunaInvisivel( 9 ); // cod.trat.trib.
		tab.setColunaInvisivel( 10 ); // desc.trat.trib.
		tab.setColunaInvisivel( 11 ); // origem
		tab.setColunaInvisivel( 12 ); // situação tributaria
		tab.setColunaInvisivel( 13 ); // tipo de substituição
		tab.setColunaInvisivel( 14 ); // tipo de redução
		tab.setTamColuna( 50, 15 ); // IVA
		tab.setTamColuna( 50, 16 ); // % redução
		tab.setTamColuna( 50, 17 ); // % ICMS
		tab.setTamColuna( 50, 18 ); // % Liv. ICMS
		tab.setTamColuna( 50, 19 ); // % IPI
		tab.setColunaInvisivel( 20 ); // % Cód.sittrib.pis
		tab.setColunaInvisivel( 21 ); // % Imposto PIS
		tab.setColunaInvisivel( 22 ); // % Desc. Sit. Trib. PIS
		tab.setTamColuna( 50, 23 ); // % Aliq. PIS
		tab.setColunaInvisivel( 24 ); // % Cód.sittrib.COFINS
		tab.setColunaInvisivel( 25 ); // % Imposto COFINS
		tab.setColunaInvisivel( 26 ); // % Desc. Sit. Trib. COFINS
		tab.setTamColuna( 50, 27 ); // % Aliq. COFINS
		
	
		
		
		panelVariantes.add( panelVariantesCampos );
		panelICMS.add( panelICMSCampos );
		panelIPI.add( panelIPICampos );
		panelPIS.add( panelPISCampos );
		panelCOFINS.add( panelCOFINSCampos );
		
//		navRod.setListaCampos( lcDet );
		
	}

	
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );

	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getClickCount() == 2 ) { }
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void stateChanged( ChangeEvent e ) {	}

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) { 
		if(e.getListaCampos()==lcTratTrib) {
			// Redução na base de calculo.
			if( "20".equals( txtCodTratTrib.getVlrString()) || ("51".equals( txtCodTratTrib.getVlrString() ) ) )  {
				rgTpRedIcmsFisc.setAtivo( true );
				txtRedFisc.setAtivo( true );
				rgTipoFisc.setVlrString( "TT" );
			}
			else {
				
				rgTpRedIcmsFisc.setAtivo( false );
				txtRedFisc.setVlrBigDecimal( new BigDecimal(0) );
				txtRedFisc.setAtivo( false );
				
				// Substituição tributária
				if("10".equals( txtCodTratTrib.getVlrString() )) {
					rgTipoST.setAtivo( true );
					rgTipoFisc.setVlrString( "FF" );
				}
				else {
					rgTipoST.setAtivo( false );

					//Tributado integralmente
					if("00".equals( txtCodTratTrib.getVlrString() )) {
						rgTipoFisc.setVlrString( "TT" );						
					}
					//Isento ou não tribut.
					else if( "30".equals( txtCodTratTrib.getVlrString()) || ("40".equals( txtCodTratTrib.getVlrString() ) ) )  {
						rgTipoFisc.setVlrString( "II" );
						txtAliqFisc.setVlrBigDecimal( new BigDecimal(0) );
					}
					// Não insidência
					else if( "41".equals( txtCodTratTrib.getVlrString()) || ("50".equals( txtCodTratTrib.getVlrString() ) ) )  {
						rgTipoFisc.setVlrString( "NN" );
						txtAliqFisc.setVlrBigDecimal( new BigDecimal(0) );
					}
				}
				
			}
				
		}
	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterInsert( InsertEvent e ) { }

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		
		lcRegraFiscal.setConexao( con );
		lcNBM.setConexao( con );
		lcNCM.setConexao( con );
		lcTratTrib.setConexao( con );
		lcMens.setConexao( con );
		lcTipoMov.setConexao( con );
		lcTipoFiscCli.setConexao( con );
		lcSitTribCOF.setConexao( con );
		lcSitTribPIS.setConexao( con );
		lcSitTribIPI.setConexao( con );
		lcPais.setConexao( con );
		lcUF.setConexao( con );

	}


	public void valorAlterado( RadioGroupEvent e ) {
		
		if ( e.getSource() == rgTipoFisc ) {
			if ( "FF".equals( rgTipoFisc.getVlrString() ) ) { // Caso seja substituição tributária
				rgTipoST.setAtivo( true );
			}
			else {
				rgTipoST.setVlrString( "SI" );
				rgTipoST.setAtivo( false );
			}
		}
		else if ( e.getSource() == rgTipoST  || e.getSource() == rgTipoFisc ) {
			if ( "SU".equals( rgTipoST.getVlrString() ) && "FF".equals( rgTipoFisc.getVlrString() ) ) { // Substituído
				txtMargemVlAgr.setAtivo( true );
				cbModBCICMSST.setAtivo( true );
			}
			else {
				txtMargemVlAgr.setVlrBigDecimal( new BigDecimal( 0 ) );
				txtMargemVlAgr.setAtivo( false );
				cbModBCICMSST.setAtivo( false );
			}
		}
	}
	
	public void beforePost( PostEvent e ) {
		
		if ( e.getListaCampos() == lcCampos ) {
			if ( txtCodNCM.getVlrString().trim().length() > 0 && txtCodNBM.getVlrString().trim().length() == 0 ) {
				lcCampos.cancelPost();
				Funcoes.mensagemInforma( this, "A nomeclatura brasileira de mercadorias deve estar amarrada a nomeclatura comum do mercosul!" );
				txtCodNBM.requestFocus();
			}
		}
		
		super.beforePost( e );
	}

	
}
