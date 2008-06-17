/**
 * @version 16/50/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FHistPad.java <BR>
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
 * Tela para cadastro de historicos padrão.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FHistPad extends FTabDados implements ActionListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodHist = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescHist = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextAreaPad txaHistPad = new JTextAreaPad( 500 );
	
	private final JScrollPane spnHist = new JScrollPane( txaHistPad );
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JComboBoxPad cbCamposDin = null;
	

	public FHistPad() {

		super();
		setTitulo( "Cadastro de Históricos padrão" );
		setAtribos( 50, 50, 440, 380 );

		montaCombos();
		montaTela();
				
		cbCamposDin.addComboBoxListener( this );
		
	}
	
	private void montaTela() {

		txaHistPad.setFont( new Font( "Courier", Font.PLAIN, 11 ) );
		txaHistPad.setTabSize( 0 );
		
		panelCampos.setPreferredSize( new Dimension( 440, 90 ) );		
		setPainel( panelCampos );
				
		adicCampo( txtCodHist, 7, 20, 70, 20, "CodHist", "Cód.hist.", ListaCampos.DB_PK, true );
		adicCampo( txtDescHist, 80, 20, 330, 20, "DescHist", "Descrição do historico", ListaCampos.DB_SI, true );
		adicDBLiv( txaHistPad, "TxaHistPad", "Corpo", false );
		setListaCampos( true, "HISTPAD", "FN" );
		
		adic( new JLabelPad( "Campos de dados" ), 7, 40, 223, 20 );
		adic( cbCamposDin, 7, 60, 223, 20 );

		pinCab.add( panelCampos, BorderLayout.NORTH );			
		pinCab.add( spnHist, BorderLayout.CENTER );
		
		this.add( pinCab );
		
	}
	
	private void montaCombos() {
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Portador" );
		vLabs.addElement( "Série" );		
		vLabs.addElement( "Valor" );
		vLabs.addElement( "Número do documento" );
		vLabs.addElement( "Data" );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "" );
		vVals.addElement( "<PORTADOR>" );
		vVals.addElement( "<SERIE>" );
		vVals.addElement( "<VALOR>" );
		vVals.addElement( "<DOCUMENTO>" );
		vVals.addElement( "<DATA>" );

		cbCamposDin = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 20, 0 );

	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbCamposDin ) {
			
			txaHistPad.insert( cbCamposDin.getVlrString(), txaHistPad.getCaretPosition() );
		}
		
	}

}
