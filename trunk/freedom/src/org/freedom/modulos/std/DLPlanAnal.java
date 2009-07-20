/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freeedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLPlanAnal.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas analíticas).
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLPlanAnal extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private final JTabbedPanePad tabbedpanel = new JTabbedPanePad();
	
	private final JPanelPad panelgeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelfields = new JPanelPad();
	
	private final JPanelPad panelcontabil = new JPanelPad();

	private final JTextFieldPad txtCodPai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescPai = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodAnal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescAnal = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodContDeb = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCodContCred = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCodForContab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtCodHistPad = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescHistPad = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private Vector<String> vValsTipoPlan = new Vector<String>();

	private Vector<String> vLabsTipoPlan = new Vector<String>();

	private Vector<String> vValsFinPlan = new Vector<String>();

	private Vector<String> vLabsFinPlan = new Vector<String>();

	private JRadioGroup<String, String> rgTipoPlan = null;

	private JRadioGroup<String, String> rgFinPlan = null;

	private ListaCampos lcHist = new ListaCampos( this, "HP" );
	

	public DLPlanAnal( Component cOrig, String sCodPai, String sDescPai, 
			String sCod, String sDesc, String sTipo, String sFin,
			String sCodContCred, String sCodContDeb, int iCodHist, String sFinalidade ) {

		super( cOrig );
		setTitulo( "Planejamento financeiro (Conta Analítica)" );
		setAtribos( 430, 430 );
		
		montaListaCampos();
		montaRadioGroup();
		montaTela();
		
		cancText( txtCodPai );
		cancText( txtDescPai );
		cancText( txtCodAnal );
		Funcoes.setBordReq( txtDescAnal );
		
		txtCodPai.setVlrString( sCodPai );
		txtDescPai.setVlrString( sDescPai );
		txtCodAnal.setVlrString( sCod );	
		txtCodContCred.setVlrString( sCodContCred );	
		txtCodContDeb.setVlrString( sCodContDeb );	
		txtCodHistPad.setVlrInteger( iCodHist );	
		rgFinPlan.setVlrString( sFinalidade );

		rgTipoPlan.setVlrString( sTipo );
		
		if ( "".equals( sFin.trim() ) ) {
			rgFinPlan.setVlrString( sFin );
		}
		
		if ( sDesc != null ) {
			setTitulo( "Edição de Conta Analítica" );
			txtDescAnal.setVlrString( sDesc );
			txtDescAnal.selectAll();
		}
		
		txtDescAnal.requestFocus();
	}
	
	private void montaListaCampos() {
				
		lcHist.add( new GuardaCampo( txtCodHistPad, "CodHist", "Cód.hist.", ListaCampos.DB_PK, false ) );
		lcHist.add( new GuardaCampo( txtDescHistPad, "DescHist", "Descrição do historico padrão", ListaCampos.DB_SI, false ) );
		lcHist.montaSql( false, "HISTPAD", "FN" );
		lcHist.setQueryCommit( false );
		lcHist.setReadOnly( true );
		txtCodHistPad.setTabelaExterna( lcHist );
		txtCodHistPad.setListaCampos( lcHist );
		txtCodHistPad.setNomeCampo( "CodHist" );
		txtCodHistPad.setChave( ListaCampos.DB_PK );
		txtDescHistPad.setListaCampos( lcHist );
		txtDescHistPad.setNomeCampo( "DescHist" );
		txtDescHistPad.setLabel( "Descrição do historico padrão" );
	}
	
	private void montaRadioGroup() {
	
		vValsTipoPlan.addElement( "B" );
		vValsTipoPlan.addElement( "C" );
		vValsTipoPlan.addElement( "D" );
		vValsTipoPlan.addElement( "R" );
		vLabsTipoPlan.addElement( "Bancos" );
		vLabsTipoPlan.addElement( "Caixa" );
		vLabsTipoPlan.addElement( "Despesas" );
		vLabsTipoPlan.addElement( "Receitas" );
		rgTipoPlan = new JRadioGroup<String, String>( 2, 2, vLabsTipoPlan, vValsTipoPlan );
		rgTipoPlan.setAtivo( 0, false );
		rgTipoPlan.setAtivo( 1, false );
		rgTipoPlan.setAtivo( 2, false );
		rgTipoPlan.setAtivo( 3, false );
	
		vValsFinPlan.addElement( "RV" );
		vValsFinPlan.addElement( "OR" );
		vValsFinPlan.addElement( "ER" );
		vValsFinPlan.addElement( "CF" );
		vValsFinPlan.addElement( "CV" );
		vValsFinPlan.addElement( "II" );
		vValsFinPlan.addElement( "RF" );
		vValsFinPlan.addElement( "DF" );
		vValsFinPlan.addElement( "CS" );
		vValsFinPlan.addElement( "IR" );
		vValsFinPlan.addElement( "OO" );
		vLabsFinPlan.addElement( "RV - Receitas sobre vendas" );
		vLabsFinPlan.addElement( "OR - Outras receitas" );
		vLabsFinPlan.addElement( "ER - Estorno de receitas" );
		vLabsFinPlan.addElement( "CF - Custo fixo" );
		vLabsFinPlan.addElement( "CV - Custo variável" );
		vLabsFinPlan.addElement( "II - Investimentos" );
		vLabsFinPlan.addElement( "RF - Receitas financeiras" );
		vLabsFinPlan.addElement( "DF - Despesas financeiras" );
		vLabsFinPlan.addElement( "CS - Contribuição social" );
		vLabsFinPlan.addElement( "IR - Imposto de renda" );
		vLabsFinPlan.addElement( "OO - Outros" );
		rgFinPlan = new JRadioGroup<String, String>( 6, 2, vLabsFinPlan, vValsFinPlan );		
	}

	private void montaTela() {
				
		panelfields.adic( new JLabelPad( "Cód.origem" ), 7, 0, 80, 20 );
		panelfields.adic( txtCodPai, 7, 20, 80, 20 );
		panelfields.adic( new JLabelPad( "Descrição da origem" ), 90, 0, 300, 20 );
		panelfields.adic( txtDescPai, 90, 20, 300, 20 );
		panelfields.adic( new JLabelPad( "Código" ), 7, 40, 110, 20 );
		panelfields.adic( txtCodAnal, 7, 60, 110, 20 );
		panelfields.adic( new JLabelPad( "Descrição" ), 120, 40, 270, 20 );
		panelfields.adic( txtDescAnal, 120, 60, 270, 20 );
		panelfields.adic( rgTipoPlan, 7, 90, 383, 60 );
		panelfields.adic( new JLabelPad( "Finalidade" ), 7, 155, 270, 20 );
		panelfields.adic( rgFinPlan, 7, 175, 383, 130 );
		
		panelcontabil.adic( new JLabelPad( "Cód.cont.débito" ), 7, 0, 150, 20 );
		panelcontabil.adic( txtCodContDeb, 7, 20, 150, 20 );
		panelcontabil.adic( new JLabelPad( "Cód.cont.crédito" ), 160, 0, 150, 20 );
		panelcontabil.adic( txtCodContCred, 160, 20, 150, 20 );
		panelcontabil.adic( new JLabelPad( "Cód.hist." ), 7, 40, 80, 20 );
		panelcontabil.adic( txtCodHistPad, 7, 60, 80, 20 );
		panelcontabil.adic( new JLabelPad( "Descrição do historico padrão" ), 90, 40, 300, 20 );
		panelcontabil.adic( txtDescHistPad, 90, 60, 300, 20 );		
		
		tabbedpanel.add( "Geral", panelfields );
		tabbedpanel.add( "Contábil", panelcontabil );
		
		panelgeral.add( tabbedpanel, BorderLayout.CENTER );
		setPanel( panelgeral );
	}
	
	private void cancText( JTextFieldPad txt ) {

		txt.setBackground( Color.lightGray );
		txt.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
		txt.setEditable( false );
		txt.setForeground( new Color( 118, 89, 170 ) );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtDescAnal.getText().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "O campo descrição está em branco!" );
				txtDescAnal.requestFocus();
				return;
			}
		}
		super.actionPerformed( evt );
	}

	public Object[] getValores() {

		Object[] ret = new Object[ 5 ];
		ret[ 0 ] = txtDescAnal.getVlrString();
		ret[ 1 ] = rgFinPlan.getVlrString();
		ret[ 2 ] = txtCodContCred.getVlrString();
		ret[ 3 ] = txtCodContDeb.getVlrString();
		ret[ 4 ] = txtCodHistPad.getVlrInteger();
		
		return ret;
	}

	@ Override
	public void setConexao( DbConnection cn ) {
		
		super.setConexao( cn );
		
		lcHist.setConexao( cn );
		lcHist.carregaDados();
	}
}
