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
 * Tela para cadastro de historicos padrão.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FChamado extends FDados implements ActionListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescChamado = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JTextAreaPad txaDetChamado = new JTextAreaPad( 1000 );
	
	private final JTextAreaPad txaObsChamado = new JTextAreaPad( 1000 );
	
	private final JScrollPane spnDetChamado = new JScrollPane( txaDetChamado );
	
	private final JScrollPane spnObsChamado = new JScrollPane( txaObsChamado );
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
//	private final JPanelPad panelCampos = new JPanelPad();
	
	private JPanelPad panelCabecalho = new JPanelPad( 700, 140 );
	
	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad panelDetalhamento = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JPanelPad panelTxa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JComboBoxPad cbTpChamado = null;	

	public FChamado() {

		super();
		setTitulo( "Cadastro de chamados" );
		setAtribos( 50, 50, 440, 380 );

		montaCombos();
		montaTela();
				
		cbTpChamado.addComboBoxListener( this );
		
	}
	
	private void montaTela() {

		pinCab.add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelCabecalho, BorderLayout.NORTH );
		
		setPainel( panelCabecalho );
				
		adicCampo( txtCodChamado, 7, 20, 70, 20, "CodChamado", "Cód.Cham.", ListaCampos.DB_PK, true );
		adicCampo( txtDescChamado, 80, 20, 330, 20, "DescChamado", "Descrição do chamado", ListaCampos.DB_SI, true );
		adicDBLiv( txaDetChamado, "DetChamado", "Detalhamamento", false );		
		adicDBLiv( txaObsChamado, "ObsChamado", "Observações", false );
		
		setListaCampos( true, "CHAMADO", "CR" );
		
		adic( new JLabelPad( "Tipo de chamado" ), 7, 40, 223, 20 );
		adic( cbTpChamado, 7, 60, 223, 20 );

		panelGeral.add( panelDetalhamento, BorderLayout.CENTER );
		panelDetalhamento.add(spnDetChamado);
		panelDetalhamento.add(spnObsChamado);
		
		spnDetChamado.setBorder( BorderFactory.createTitledBorder( "Detalhamento" ) );
		spnObsChamado.setBorder( BorderFactory.createTitledBorder( "Observações" ) );
		
		this.add( pinCab );
		
	}
	
	private void montaCombos() {
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Portador" );
		vLabs.addElement( "Valor" );
		vLabs.addElement( "Número do documento" );
		vLabs.addElement( "Data" );
		vLabs.addElement( "Histórico digitado" );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "" );
		vVals.addElement( "<PORTADOR>" );
		vVals.addElement( "<VALOR>" );
		vVals.addElement( "<DOCUMENTO>" );
		vVals.addElement( "<DATA>" );
		vVals.addElement( "<HISTORICO>" );

		cbTpChamado = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 20, 0 );

	}

	public void valorAlterado( JComboBoxEvent evt ) {


		
	}

}
