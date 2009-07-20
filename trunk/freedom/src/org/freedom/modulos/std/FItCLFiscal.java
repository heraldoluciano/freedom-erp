/**
 * @version 29/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FItCLFiscal.java <BR>
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
 * Detalhe para classificação fiscal.
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;

public class FItCLFiscal extends FDetalhe implements CarregaListener, FocusListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipoFisc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMens = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtMens = new JTextFieldFK( JTextFieldPad.TP_STRING, 10000, 0 );

	private JTextFieldPad txtCodItClFiscal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodTratTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescTratTrib = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtAliqICMS = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtAliqIPI = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 2 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 200, 0 );

	private JCheckBoxPad cbNoUF = new JCheckBoxPad( "Sim", "S", "N" );

	private JComboBoxPad cbOrig = null;

	private JRadioGroup<?, ?> rgTpRedIcmsFisc = null;

	private Vector<String> vTpRedIcmsFiscLabs = new Vector<String>();

	private Vector<String> vTpRedIcmsFiscVals = new Vector<String>();

	private Vector<String> vValsOrig = new Vector<String>();

	private Vector<String> vLabsOrig = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoFisc = null;
	
	private JRadioGroup<?, ?> rgTipoST = null;
	
	private JTextFieldPad txtMargemVlAgr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );
	
	private Vector<String> vSTLabs = new Vector<String>();
	
	private Vector<String> vSTVals = new Vector<String>();

	private Vector<String> vTipoVals = new Vector<String>();

	private Vector<String> vTipoLabs = new Vector<String>();

	private ListaCampos lcTratTrib = new ListaCampos( this, "TT" );

	private ListaCampos lcTipoFiscCli = new ListaCampos( this, "FC" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcMens = new ListaCampos( this, "ME" );

	public FItCLFiscal() {

		setTitulo( "Cadastro de variações de ICMS" );
		setAtribos( 50, 50, 510, 640 );

		txtCodFisc.setAtivo( false );
		txtDescFisc.setAtivo( false );

		setListaCampos( lcCampos );
		pnCab.remove( 1 ); // Remove o navegador do cabeçalho.
		setAltCab( 60 );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodFisc, 7, 20, 100, 20, "CodFisc", "Cód.c.fisc.", ListaCampos.DB_PK, true );
		adicCampo( txtDescFisc, 110, 20, 340, 20, "DescFisc", "Descrição do classificação fiscal", ListaCampos.DB_SI, true );
		setListaCampos( true, "CLFISCAL", "LF" );
		lcCampos.setReadOnly( true );

		lcTratTrib.add( new GuardaCampo( txtCodTratTrib, "CodTratTrib", "Cód.tr.trib.", ListaCampos.DB_PK, true ) );
		lcTratTrib.add( new GuardaCampo( txtDescTratTrib, "DescTratTrib", "Descrição do tratamento tributário", ListaCampos.DB_SI, false ) );
		lcTratTrib.montaSql( false, "TRATTRIB", "LF" );
		lcTratTrib.setQueryCommit( false );
		lcTratTrib.setReadOnly( true );
		txtCodTratTrib.setTabelaExterna( lcTratTrib );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.Mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );

		lcTipoFiscCli.add( new GuardaCampo( txtCodTipoFisc, "CodFiscCli", "Cód.c.fisc.", ListaCampos.DB_PK, false ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição da classificação fiscal", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodTipoFisc.setTabelaExterna( lcTipoFiscCli );

		lcMens.add( new GuardaCampo( txtCodMens, "CodMens", "Cód.mems.", ListaCampos.DB_PK, false ) );
		lcMens.add( new GuardaCampo( txtMens, "Mens", "Mensagem", ListaCampos.DB_SI, false ) );
		lcMens.montaSql( false, "MENSAGEM", "LF" );
		lcMens.setQueryCommit( false );
		lcMens.setReadOnly( true );
		txtCodMens.setTabelaExterna( lcMens );

		vTipoLabs.addElement( "Isensão (não tributada)" );
		vTipoLabs.addElement( "Substituição Tributária" );
		vTipoLabs.addElement( "Não inside (outras)" );
		vTipoLabs.addElement( "Tributação Integral" );
		vTipoVals.addElement( "II" );
		vTipoVals.addElement( "FF" );
		vTipoVals.addElement( "NN" );
		vTipoVals.addElement( "TT" );
		rgTipoFisc = new JRadioGroup<String, String>( 2, 2, vTipoLabs, vTipoVals );

		// Tipo de substituição tributária
		
		vSTLabs.addElement( "Substituto" );
		vSTLabs.addElement( "Substituído" );
		vSTVals.addElement( "SU" );
		vSTVals.addElement( "SI" );
		rgTipoST = new JRadioGroup<String, String>( 1, 2, vSTLabs, vSTVals );

		// Origem da mercadoria
		
		vLabsOrig.addElement( "<--Selecione-->" );
		vLabsOrig.addElement( "Nacional" );
		vLabsOrig.addElement( "Estrangeira - Importação direta" );
		vLabsOrig.addElement( "Estrangeira - Adquirida no mercado interno" );
		vValsOrig.addElement( "" );
		vValsOrig.addElement( "0" );
		vValsOrig.addElement( "1" );
		vValsOrig.addElement( "2" );
		cbOrig = new JComboBoxPad( vLabsOrig, vValsOrig, JComboBoxPad.TP_STRING, 1, 0 );

		vTpRedIcmsFiscLabs.addElement( "Base ICMS" );
		vTpRedIcmsFiscLabs.addElement( "Valor ICMS" );
		vTpRedIcmsFiscVals.addElement( "B" );
		vTpRedIcmsFiscVals.addElement( "V" );
		rgTpRedIcmsFisc = new JRadioGroup<String, String>( 2, 1, vTpRedIcmsFiscLabs, vTpRedIcmsFiscVals );
		rgTpRedIcmsFisc.setVlrString( "B" );

		setAltDet( 400 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItClFiscal, 7, 20, 60, 20, "CodItFisc", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodTratTrib, 70, 20, 77, 20, "CodTratTrib", "Cód.tr.tab.", ListaCampos.DB_FK, txtDescTratTrib, true );
		adicDescFK( txtDescTratTrib, 150, 20, 300, 20, "DescTratTrib", "Descrição da tratamento tributário" );
		adicDB( cbOrig, 7, 60, 355, 25, "OrigFisc", "Origem", true );
		adicDB( cbNoUF, 380, 60, 80, 20, "NOUFITFISC", "No Estado", true );
		adicCampo( txtAliqIPI, 7, 105, 95, 20, "AliqIPIFisc", "% Alíq.IPI", ListaCampos.DB_SI, false );
		adicCampo( txtAliqICMS, 110, 105, 95, 20, "AliqFisc", "% Alíq.ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtRedFisc, 213, 105, 95, 20, "RedFisc", "% Redução ICMS", ListaCampos.DB_SI, false );
		adicDB( rgTpRedIcmsFisc, 320, 145, 140, 60, "tpredicmsfisc", "Tipo de Redução", false );
		adicCampo( txtCodTipoFisc, 7, 145, 80, 20, "CodFiscCli", "Cód.fisc.cli.", ListaCampos.DB_FK, txtDescFiscCli, false );
		adicDescFK( txtDescFiscCli, 90, 145, 223, 20, "DescTratTrib", "Descrição do tipo fiscal de cliente" );
		adicCampo( txtCodTipoMov, 7, 185, 80, 20, "CodTipoMov", "Cód.tp.Mov", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMov, 90, 185, 223, 20, "DescTipoMov", "Descrição do tipo de movimento" );
		adicDB( rgTipoFisc, 7, 225, 450, 53, "TipoFisc", "Situação do ICMS:", true );
		
		adicDB( rgTipoST, 7, 298, 240, 30, "TipoST", "Tipo de Sub.Trib.", true );
		adicDB( txtMargemVlAgr, 253, 298, 120, 20, "MargemVlAgr", "% Vlr.Agregado" , false );
		
		adicCampo( txtCodMens, 7, 350, 80, 20, "CodMens", "Cód.mens.", ListaCampos.DB_FK, txtMens, false );
		adicDescFK( txtMens, 90, 350, 360, 20, "Mens", "Mensagem" );
		setListaCampos( true, "ITCLFISCAL", "LF" );
		lcDet.setQueryInsert( false );

		montaTab();

		tab.setTamColuna( 190, 2 );
				
		txtCodTipoFisc.addFocusListener( this );
		txtCodTipoMov.addFocusListener( this );
		lcCampos.addCarregaListener( this );
		rgTipoFisc.addRadioGroupListener( this );
		rgTipoST.addRadioGroupListener( this );
		
		rgTipoFisc.setVlrString( "TT" );
		rgTipoST.setVlrString( "SI" );
		txtMargemVlAgr.setAtivo( false );

		
	}
	
	private void travaTipoMov() {
		
		if ( txtCodTipoFisc.getVlrInteger() == 0 && txtCodTipoMov.getVlrInteger() == 0 ) {
			txtCodTipoFisc.setEnabled( true );
			txtCodTipoMov.setEnabled( true );
		}
		else if ( txtCodTipoFisc.getVlrInteger() != 0 ) {
			txtCodTipoFisc.setEnabled( true );
			txtCodTipoMov.setEnabled( false );
			txtCodTipoMov.setVlrString( "" );
			lcTipoFiscCli.carregaDados();
		}
		else if ( txtCodTipoMov.getVlrInteger() != 0 ) {
			txtCodTipoMov.setEnabled( true );
			txtCodTipoFisc.setEnabled( false );
			txtCodTipoFisc.setVlrString( "" );
			lcTipoMov.carregaDados();
		}
	}
	public void beforeCarrega( CarregaEvent cevt ) { }

	public void afterCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcCampos ) {
			travaTipoMov();
		}
	}

	public void focusGained( FocusEvent e ) { }

	public void focusLost( FocusEvent e ) {
		if ( e.getSource() == txtCodTipoFisc || e.getSource() == txtCodTipoMov ) {
			travaTipoMov();
		}
	}

	public void exec( String sCodFisc ) {
		txtCodFisc.setVlrString( sCodFisc );
		lcCampos.carregaDados();
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcMens.setConexao( cn );
		lcTratTrib.setConexao( cn );
		lcTipoFiscCli.setConexao( cn );
		lcTipoMov.setConexao( cn );
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if(evt.getSource()==rgTipoFisc) {
			if("FF".equals( rgTipoFisc.getVlrString() )) { // Caso seja substituição tributária
				rgTipoST.setAtivo( true );
			}
			else {
				rgTipoST.setVlrString( "SI" );
				rgTipoST.setAtivo( false );				
			}
		}
		else if (evt.getSource()==rgTipoST) {
			if("SU".equals( rgTipoST.getVlrString() )) { // Substituído
				txtMargemVlAgr.setAtivo( true );
			}
			else {
				txtMargemVlAgr.setVlrBigDecimal( new BigDecimal(0) );
				txtMargemVlAgr.setAtivo( false );				
			}
		}
		
	}
}
