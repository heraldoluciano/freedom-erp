/**
 * @version 05/12/2002 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FTrnsLancCat.java <BR>
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
 */
package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.BorderFactory;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFilho;

public class FTrnsLancCat extends FFilho {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad panelRodape = null;
	
	private JPanelPad panelCentro = new JPanelPad();
	
	private JTextFieldPad txtNumContaOrig = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescContaOrgi = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtNumContaDest = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescContaDest = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcPlanOrig = new ListaCampos( this, "PN" );
	
	private ListaCampos lcPlanDest = new ListaCampos( this, "PN" );

	public FTrnsLancCat() {

		super( false );
		setTitulo( " Tramsferência de lançamentos entre categorias" );
		setAtribos( 40, 40, 400, 400 );
		
		montaTela();
		montaListaCampos();
	}
	
	private void montaTela(){
		
		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		
		pnCliente.add( panelCentro,BorderLayout.CENTER );
		panelCentro.setBorder( BorderFactory.createEtchedBorder() );
		panelCentro.setPreferredSize( new Dimension( 600, 200 ) );
		
		/***********************
		 * Categoria de Origem *
		 ********************* */
		
		panelCentro.adic( new JLabelPad("Cód.cat.Origem"), 7, 10, 100, 20 );
		panelCentro.adic( txtNumContaOrig, 7, 30, 100, 20 ); 
		panelCentro.adic( new JLabelPad("Descrição da categoria de Origem"), 110, 10, 200, 20 );
		panelCentro.adic( txtDescContaOrgi, 110, 30, 250, 20 );
		
		/************************
		 * Categoria de Destino *
		 ************************/
		
		panelCentro.adic( new JLabelPad("Cód.cat.Destino"), 7, 50, 100, 20 );
		panelCentro.adic( txtNumContaDest, 7, 70, 100, 20 );
		panelCentro.adic( new JLabelPad("Descrição da categoria de Destino"), 110, 50, 200, 20 );
		panelCentro.adic( txtDescContaDest, 110, 70, 250, 20 );
			
	}
	
	private void montaListaCampos(){
		
		/***********************
		 * Categoria de Origem *
		 ********************* */
		
		lcPlanOrig.add( new GuardaCampo( txtNumContaOrig, "CodPlan", "Cód.planj.", ListaCampos.DB_PK, true ) );
		lcPlanOrig.add( new GuardaCampo( txtDescContaOrgi, "DescPlan", "Descrição do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanOrig.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanOrig.setQueryCommit( false );
		lcPlanOrig.setReadOnly( true );
		txtNumContaOrig.setTabelaExterna( lcPlanOrig );
		txtNumContaOrig.setListaCampos( lcPlanOrig );
		txtNumContaOrig.setFK( true );
		txtNumContaOrig.setRequerido( true );
		txtDescContaOrgi.setListaCampos( lcPlanOrig );

		/************************
		 * Categoria de Destino *
		 ************************/
		
		lcPlanDest.add( new GuardaCampo( txtNumContaDest, "CodPlan", "Cód.planj.", ListaCampos.DB_PK, true ) );
		lcPlanDest.add( new GuardaCampo( txtDescContaDest, "DescPlan", "Descrição do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanDest.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanDest.setQueryCommit( false );
		lcPlanDest.setReadOnly( true );
		txtDescContaDest.setTabelaExterna( lcPlanDest );
		txtNumContaDest.setListaCampos( lcPlanDest );
		txtNumContaDest.setFK( true );
		txtNumContaDest.setRequerido( true );
		txtDescContaOrgi.setListaCampos( lcPlanDest );
	}
	
	public void setConexao( Connection cn ) {
		
		super.setConexao( cn );
		lcPlanOrig.setConexao( cn );
		lcPlanDest.setConexao( cn );
		
	}
}
