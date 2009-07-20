/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FCLFiscal.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FCLFiscal extends FTabDados implements RadioGroupListener, CarregaListener, InsertListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad panelGeral = new JPanelPad();
	
	private JPanelPad panelNomeComum = new JPanelPad( new BorderLayout() );
	
	private JPanelPad panelNomeComumNCM = new JPanelPad( new BorderLayout() );
	
	private JPanelPad panelNomeComumNCMCampos = new JPanelPad( 500, 60 );
	
	private JSplitPane panelNomeComumNCMDescricoes = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
	
	private JPanelPad panelNomeComumNBM = new JPanelPad( 500, 60 );
	
	// Campos da aba geral

	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodRegra = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescRegra = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTratTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescTratTrib = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodMens = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMens = new JTextFieldFK( JTextFieldPad.TP_STRING, 10000, 0 );

	private JTextFieldPad txtAliqFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtAliqlFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JTextFieldPad txtAliqPisFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JTextFieldPad txtAliqCofinsFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private JTextFieldPad txtMargemVlAgr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JRadioGroup<String, String> rgTipoFisc = null;
	
	private JRadioGroup<String, String> rgTipoST = null;
	
	private JRadioGroup<String, String> rgSitPis = null;

	private JRadioGroup<String, String> rgSitCofins = null;
	
	private JRadioGroup<String, String> rgTpRedIcmsFisc = null;

	private JComboBoxPad cbOrig = null;
	
	private JButton btExcecoes = new JButton( "Exceções", Icone.novo( "btExcecoes.gif" ) );

	// Campos da aba Nomeclarura Comum

	private JTextFieldPad txtCodNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescNCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtExTIPI = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextAreaPad txaDescNCM = new JTextAreaPad( 2000 );
	
	private JScrollPane spDescNCM = new JScrollPane( txaDescNCM );

	private JTextAreaPad txaDescExTIPI = new JTextAreaPad( 1000 );
	
	private JScrollPane spDescExTIPI = new JScrollPane( txaDescExTIPI );

	private JTextFieldPad txtCodNBM = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDescNBM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );		

	private ListaCampos lcRegraFiscal = new ListaCampos( this, "RA" );

	private ListaCampos lcTratTrib = new ListaCampos( this, "TT" );

	private ListaCampos lcMens = new ListaCampos( this, "ME" );

	private ListaCampos lcNCM = new ListaCampos( this );

	private ListaCampos lcNBM = new ListaCampos( this );
	

	public FCLFiscal() {

		super( true );
		setTitulo( "Cadastro de Classificações Fiscais" );
		setAtribos( 50, 30, 515, 580 );
		setImprimir( true );
		
		montaRadioGroups();
		montaComboBox();
		montaListaCampos();
		montaTela();		

		lcCampos.addCarregaListener( this );
		
		lcCampos.addInsertListener( this );
		
		lcCampos.addPostListener( this );

		btExcecoes.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		rgTipoFisc.addRadioGroupListener( this );
		
		rgTipoST.addRadioGroupListener( this );
		
		rgTipoFisc.setVlrString( "TT" );
		rgTipoST.setVlrString( "SI" );
		rgSitPis.setVlrString( "T" );
		rgSitCofins.setVlrString( "T" );
		rgTpRedIcmsFisc.setVlrString( "B" );
		
		rgTipoST.setAtivo( false );
		txtMargemVlAgr.setAtivo( false );
	}
	
	private void montaRadioGroups() {
	
		Vector<String> vTpRedIcmsFiscLabs = new Vector<String>();
		vTpRedIcmsFiscLabs.addElement( "Base ICMS" );
		vTpRedIcmsFiscLabs.addElement( "Valor ICMS" );
		Vector<String> vTpRedIcmsFiscVals = new Vector<String>();
		vTpRedIcmsFiscVals.addElement( "B" );
		vTpRedIcmsFiscVals.addElement( "V" );
		rgTpRedIcmsFisc = new JRadioGroup<String, String>( 2, 1, vTpRedIcmsFiscLabs, vTpRedIcmsFiscVals );
		
		Vector<String> vTipoLabs = new Vector<String>();
		vTipoLabs.addElement( "Isento" );
		vTipoLabs.addElement( "Subst. Trib." );
		vTipoLabs.addElement( "Não inside" );
		vTipoLabs.addElement( "Trib. Integral" );
		Vector<String> vTipoVals = new Vector<String>();
		vTipoVals.addElement( "II" );
		vTipoVals.addElement( "FF" );
		vTipoVals.addElement( "NN" );
		vTipoVals.addElement( "TT" );
		rgTipoFisc = new JRadioGroup<String, String>( 4, 1, vTipoLabs, vTipoVals );		
	
		Vector<String> vSTLabs = new Vector<String>();
		vSTLabs.addElement( "Substituto" );
		vSTLabs.addElement( "Substituído" );
		Vector<String> vSTVals = new Vector<String>();
		vSTVals.addElement( "SU" );
		vSTVals.addElement( "SI" );
		rgTipoST = new JRadioGroup<String, String>( 2, 1, vSTLabs, vSTVals );
	
		Vector<String> vSitPisLabs = new Vector<String>();
		vSitPisLabs.addElement( "Tributado" );
		vSitPisLabs.addElement( "Isento" );
		vSitPisLabs.addElement( "Sub. trib." );
		Vector<String> vSitPisVals = new Vector<String>();
		vSitPisVals.addElement( "T" );
		vSitPisVals.addElement( "I" );
		vSitPisVals.addElement( "S" );
		rgSitPis = new JRadioGroup<String, String>( 3, 1, vSitPisLabs, vSitPisVals );
		
		Vector<String> vSitCofinsLabs = new Vector<String>();
		vSitCofinsLabs.addElement( "Tributado" );
		vSitCofinsLabs.addElement( "Isento" );
		vSitCofinsLabs.addElement( "Sub. trib." );
		Vector<String> vSitCofinsVals = new Vector<String>();
		vSitCofinsVals.addElement( "T" );
		vSitCofinsVals.addElement( "I" );
		vSitCofinsVals.addElement( "S" );
		rgSitCofins = new JRadioGroup<String, String>( 3, 1, vSitCofinsLabs, vSitCofinsVals );
	}

	private void montaComboBox() {
	
		Vector<String> vLabsOrig = new Vector<String>();
		vLabsOrig.addElement( "<--Selecione-->" );
		vLabsOrig.addElement( "Nacional" );
		vLabsOrig.addElement( "Estrangeira - Importação direta" );
		vLabsOrig.addElement( "Estrangeira - Adquirida no mercado interno" );
		Vector<String> vValsOrig = new Vector<String>();
		vValsOrig.addElement( "" );
		vValsOrig.addElement( "0" );
		vValsOrig.addElement( "1" );
		vValsOrig.addElement( "2" );
		cbOrig = new JComboBoxPad( vLabsOrig, vValsOrig, JComboBoxPad.TP_STRING, 1, 0 );
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
	}
	
	private void montaTela() {
		
		// ********** Aba Geral **********
		
		adicTab( "Geral", panelGeral );
		setPainel( panelGeral );
		
		adicCampo( txtCodFisc, 7, 20, 100, 20, "CodFisc", "Cód.c.fisc.", ListaCampos.DB_PK, null, true );
		adicCampo( txtDescFisc, 110, 20, 370, 20, "DescFisc", "Descrição da classificação fiscal", ListaCampos.DB_SI, null, true );		
		adicCampo( txtCodRegra, 7, 60, 100, 20, "CodRegra", "Cód.reg.CFOP", ListaCampos.DB_FK, txtDescRegra, true );
		adicDescFK( txtDescRegra, 110, 60, 370, 20, "DescRegra", "Descrição da regra fiscal" );		

		adicCampo( txtCodTratTrib, 7, 100, 100, 20, "CodTratTrib", "Cód.trat.trib.", ListaCampos.DB_FK, txtDescTratTrib, true );
		adicDescFK( txtDescTratTrib, 110, 100, 370, 20, "DescTratTrib", "Descrição da tratamento tributário" );		

		adicCampo( txtCodMens, 7, 140, 100, 20, "CodMens", "Cód.mens.", ListaCampos.DB_FK, txtDescMens, false );		
		adicDescFK( txtDescMens, 110, 140, 370, 20, "Mens", "Mensagem" );	

		JLabelPad separacao = new JLabelPad();
		separacao.setBorder( BorderFactory.createEtchedBorder() );
		adic( separacao, 7, 170, 475, 2 );
		
		
		adicDB( cbOrig, 7, 190, 473, 30, "OrigFisc", "Origem", true );		

		adicDB( rgTipoFisc, 7, 245, 120, 100, "TipoFisc", "Situação do ICMS:", true );
		adicDB( rgSitPis, 132, 245, 103, 100, "SitPisFisc", "Situação do PIS:", true );
		adicDB( rgSitCofins, 240, 245, 117, 100, "SitCofinsFisc", "Situação do COFINS:", true );
		adicDB( rgTipoST, 362, 245, 120, 60, "TipoST", "Tipo de Sub.Trib.", true );
		adicDB( txtMargemVlAgr, 362, 325, 120, 20, "MargemVlAgr", "% Vlr.Agregado (IVA)" , false );		
		
		adicDB( rgTpRedIcmsFisc, 7, 370, 120, 60, "tpredicmsfisc", "Tipo de Redução", false );
		
		adicCampo( txtRedFisc, 132, 370, 103, 20, "RedFisc", "% Redução ICMS", ListaCampos.DB_SI, null, false );
		adicCampo( txtAliqFisc, 132, 410, 103, 20, "AliqFisc", "% Aliq.ICMS", ListaCampos.DB_SI, null, false );
		
		adicCampo( txtAliqlFisc, 240, 370, 117, 20, "AliqlFisc", "% Aliq.liv.ICMS", ListaCampos.DB_SI, null, false );
		adicCampo( txtAliqIPIFisc, 240, 410, 117, 20, "AliqIPIFisc", "% Aliq.IPI", ListaCampos.DB_SI, null, false );
	
		adicCampo( txtAliqPisFisc, 362, 370, 120, 20, "AliqPisFisc", "% Aliq.PIS", ListaCampos.DB_SI, null, false );
		adicCampo( txtAliqCofinsFisc, 362, 410, 120, 20, "AliqCofinsFisc", "% Aliq.COFINS", ListaCampos.DB_SI, null, false );

		
		adic( btExcecoes, 362, 440, 120, 30 );	

		// *******************************
		
		// ********** Aba Nomeclatura Comum **********
		
		adicTab( "Nomeclatura Comum", panelNomeComum );
		
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

		setListaCampos( false, "CLFISCAL", "LF" );
		lcCampos.setQueryInsert( false );
	}
	
	public void verifItens() {

		boolean bAtivo = true;
		String sSQL = "SELECT COUNT(*) FROM LFITCLFISCAL WHERE CODEMP=? AND CODFILIAL=? AND CODFISC=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setString( 3, txtCodFisc.getVlrString() );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() && rs.getInt( 1 ) > 0 )
				bAtivo = false;

			cbOrig.setAtivo( bAtivo );
			txtCodTratTrib.setAtivo( bAtivo );
			txtRedFisc.setAtivo( bAtivo );
			rgTipoFisc.setAtivo( bAtivo );
			rgTipoST.setAtivo( bAtivo );
			txtMargemVlAgr.setAtivo( bAtivo );

			rs.close();
			ps.close();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao verificar itens!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void abreItClFiscal() {

		if ( !fPrim.temTela( "Item CLFISCAL" ) ) {
			FItCLFiscal tela = new FItCLFiscal();
			fPrim.criatela( "Item CLFISCAL", tela, con );
			tela.exec( txtCodFisc.getVlrString() );
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btExcecoes && lcCampos.getStatus() == ListaCampos.LCS_SELECT ) {
			abreItClFiscal();
		}
		if ( e.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( e.getSource() == btImp ) {
			imprimir( false );
		}

		super.actionPerformed( e );
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
		else if ( e.getSource() == rgTipoST ) {
			if ( "SU".equals( rgTipoST.getVlrString() ) ) { // Substituído
				txtMargemVlAgr.setAtivo( true );
			}
			else {
				txtMargemVlAgr.setVlrBigDecimal( new BigDecimal( 0 ) );
				txtMargemVlAgr.setAtivo( false );
			}
		}
	}

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {	
		if ( e.getListaCampos() == lcCampos )
			verifItens();
	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterInsert( InsertEvent e ) {
		if ( e.getListaCampos() == lcCampos ) {
			verifItens();
		}		
	}

	@Override
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

	private void imprimir( boolean bVisualizar ) {
	
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
	
		DLRClasFiscal dl = new DLRClasFiscal( this );
		String sRets[];
		String sCodFiscAnt = "";
	
		dl.setVisible( true );
		if ( dl.OK ) {
			sRets = dl.getValores();
		}
		else {
			dl.dispose();
			return;
		}
		
		String sSQL = 
			"SELECT F.CODFISC,F.DESCFISC, F.TIPOFISC, F.ALIQFISC, " 
			+ "F.REDFISC, F.ALIQIPIFISC, F.CODREGRA, F.ORIGFISC, F.CODTRATTRIB, " 
			+ "F.CODMENS, FI.CODFISC, FI.CODITFISC,FI.ORIGFISC, FI.TIPOFISC, FI.REDFISC, "
			+ "FI.CODTRATTRIB, FI.NOUFITFISC, FI.CODFISCCLI, FI.ALIQLFISC,  FI.ALIQFISC,FI.CODMENS, " 
			+ "(SELECT TP.DESCFISCCLI FROM LFTIPOFISCCLI TP WHERE TP.CODFISCCLI=FI.CODFISCCLI AND "
			+ " TP.CODEMP=FI.CODEMPFC AND TP.CODFILIAL=FI.CODFILIALFC) FROM LFCLFISCAL F LEFT OUTER JOIN  " 
			+ "LFITCLFISCAL FI ON F.CODFISC=FI.CODFISC " 
			+ "AND F.CODEMP=FI.CODEMP AND F.CODFILIAL=FI.CODFILIAL ORDER BY F." + ( sRets[ 1 ].equals( "C" ) ? "CODFISC" : "DESCFISC" );
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			imp.limpaPags();
			
			while ( rs.next() ) {
				if ( !sRets[ 0 ].equals( "S" ) ) {
					if ( imp.pRow() == 0 ) {
						imp.montaCab();
						imp.setTitulo( "Relatório de Classificação fiscal dos produtos" );
						imp.addSubTitulo( "Relatório de Classificação fiscal dos produtos" );
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 3, "Código" );
						imp.say( imp.pRow() + 0, 18, "|" );
						imp.say( imp.pRow() + 0, 20, "Descrição" );
						imp.say( imp.pRow() + 0, 71, "|" );
						imp.say( imp.pRow() + 0, 72, "T.T" );
						imp.say( imp.pRow() + 0, 76, "|" );
						imp.say( imp.pRow() + 0, 78, "ICMS." );
						imp.say( imp.pRow() + 0, 84, "|" );
						imp.say( imp.pRow() + 0, 85, "RED." );
						imp.say( imp.pRow() + 0, 90, "|" );
						imp.say( imp.pRow() + 0, 91, "IPI" );
						imp.say( imp.pRow() + 0, 98, "|" );
						imp.say( imp.pRow() + 0, 99, "REGRA" );
						imp.say( imp.pRow() + 0, 105, "|" );
						imp.say( imp.pRow() + 0, 107, "Mens." );
						imp.say( imp.pRow() + 0, 114, "|" );
						imp.say( imp.pRow() + 0, 116, "T.F.CLIENTE" );
						imp.say( imp.pRow() + 0, 128, "|" );
						imp.say( imp.pRow() + 0, 130, "EST" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 3, rs.getString( "CodFisc" ) );
					imp.say( imp.pRow() + 0, 18, "|" );
					imp.say( imp.pRow() + 0, 20, rs.getString( "DescFisc" ) );
					imp.say( imp.pRow() + 0, 71, "|" );
					imp.say( imp.pRow() + 0, 73, rs.getString( "OrigFisc" ) );
					imp.say( imp.pRow() + 0, 74, rs.getString( "CodTratTrib" ) );
					imp.say( imp.pRow() + 0, 76, "|" );
					imp.say( imp.pRow() + 0, 78, rs.getString( "AliqFisc" ) );
					imp.say( imp.pRow() + 0, 84, "|" );
					imp.say( imp.pRow() + 0, 85, rs.getString( "RedFisc" ) );
					imp.say( imp.pRow() + 0, 90, "|" );
					imp.say( imp.pRow() + 0, 91, rs.getString( "AliqIPIFisc" ) );
					imp.say( imp.pRow() + 0, 98, "|" );
					imp.say( imp.pRow() + 0, 99, rs.getString( "CodRegra" ) );
					imp.say( imp.pRow() + 0, 105, "|" );
					imp.say( imp.pRow() + 0, 108, rs.getString( "CodMens" ) );
					imp.say( imp.pRow() + 0, 114, "|" );
					imp.say( imp.pRow() + 0, 128, "|" );
					imp.say( imp.pRow() + 0, 131, rs.getString( "NOUFITFISC" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
				}
				else {
					if ( imp.pRow() == 0 ) {
						imp.montaCab();
						imp.setTitulo( "Relatório de Classificação fiscal dos produtos" );
						imp.addSubTitulo( "Relatório de Classificação fiscal dos produtos" );
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "" );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 3, "Código" );
						imp.say( imp.pRow() + 0, 18, "|" );
						imp.say( imp.pRow() + 0, 20, "Descrição" );
						imp.say( imp.pRow() + 0, 71, "|" );
						imp.say( imp.pRow() + 0, 72, "T.T" );
						imp.say( imp.pRow() + 0, 76, "|" );
						imp.say( imp.pRow() + 0, 78, "ICMS." );
						imp.say( imp.pRow() + 0, 84, "|" );
						imp.say( imp.pRow() + 0, 85, "RED." );
						imp.say( imp.pRow() + 0, 90, "|" );
						imp.say( imp.pRow() + 0, 91, "IPI" );
						imp.say( imp.pRow() + 0, 98, "|" );
						imp.say( imp.pRow() + 0, 99, "REGRA" );
						imp.say( imp.pRow() + 0, 105, "|" );
						imp.say( imp.pRow() + 0, 107, "Mens." );
						imp.say( imp.pRow() + 0, 114, "|" );
						imp.say( imp.pRow() + 0, 116, "T.F.CLIENTE" );
						imp.say( imp.pRow() + 0, 128, "|" );
						imp.say( imp.pRow() + 0, 130, "EST." );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					if ( ( !sCodFiscAnt.equals( "" ) ) && ( !sCodFiscAnt.equals( rs.getString( "CodFisc" ) ) ) ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
	
					if ( !sCodFiscAnt.equals( rs.getString( "CodFisc" ) ) ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 3, rs.getString( "CodFisc" ) );
						imp.say( imp.pRow() + 0, 18, "|" );
						imp.say( imp.pRow() + 0, 20, rs.getString( "DescFisc" ) );
						imp.say( imp.pRow() + 0, 71, "|" );
						imp.say( imp.pRow() + 0, 73, rs.getString( "OrigFisc" ) );
						imp.say( imp.pRow() + 0, 74, rs.getString( "CodTratTrib" ) );
						imp.say( imp.pRow() + 0, 76, "|" );
						imp.say( imp.pRow() + 0, 78, rs.getString( "AliqFisc" ) );
						imp.say( imp.pRow() + 0, 84, "|" );
						imp.say( imp.pRow() + 0, 85, rs.getString( "RedFisc" ) );
						imp.say( imp.pRow() + 0, 90, "|" );
						imp.say( imp.pRow() + 0, 91, rs.getString( "AliqIPIFisc" ) );
						imp.say( imp.pRow() + 0, 98, "|" );
						imp.say( imp.pRow() + 0, 99, rs.getString( "CodRegra" ) );
						imp.say( imp.pRow() + 0, 105, "|" );
						imp.say( imp.pRow() + 0, 108, rs.getString( "CodMens" ) );
						imp.say( imp.pRow() + 0, 114, "|" );
						imp.say( imp.pRow() + 0, 128, "|" );
						imp.say( imp.pRow() + 0, 131, rs.getString( "NOUFITFISC" ) );
						imp.say( imp.pRow() + 0, 135, "|" );
					}
	
					if ( rs.getString( 11 ) != null ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 3, "Exceção:" );
						imp.say( imp.pRow() + 0, 18, "|" );
						imp.say( imp.pRow() + 0, 71, "|" );
						imp.say( imp.pRow() + 0, 73, rs.getString( 13 ) );
						imp.say( imp.pRow() + 0, 74, rs.getString( 16 ) );
						imp.say( imp.pRow() + 0, 76, "|" );
						imp.say( imp.pRow() + 0, 78, rs.getString( 19 ) );
						imp.say( imp.pRow() + 0, 84, "|" );
						imp.say( imp.pRow() + 0, 85, rs.getString( 15 ) );
						imp.say( imp.pRow() + 0, 90, "|" );
						imp.say( imp.pRow() + 0, 91, rs.getString( 20 ) );
						imp.say( imp.pRow() + 0, 98, "|" );
						imp.say( imp.pRow() + 0, 105, "|" );
						imp.say( imp.pRow() + 0, 108, rs.getString( 21 ) );
						imp.say( imp.pRow() + 0, 114, "|" );
						imp.say( imp.pRow() + 0, 115, Funcoes.copy( rs.getString( 22 ), 10 ) );
						imp.say( imp.pRow() + 0, 128, "|" );
						imp.say( imp.pRow() + 0, 131, rs.getString( 17 ) );
						imp.say( imp.pRow() + 0, 135, "|" );
					}
	
					sCodFiscAnt = rs.getString( "CodFisc" );
				}
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}
			}
	
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
			imp.eject();
	
			imp.fechaGravacao();
	
			rs.close();
			ps.close();
			
			con.commit();
			
			dl.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de classificacao fiscal do produto!\n" + err.getMessage(), true, con, err );
		}
	
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcRegraFiscal.setConexao( cn );
		lcTratTrib.setConexao( cn );
		lcMens.setConexao( cn );
		lcNCM.setConexao( cn );
		lcNBM.setConexao( cn );
	}
}
