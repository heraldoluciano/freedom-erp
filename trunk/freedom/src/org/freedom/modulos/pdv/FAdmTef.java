package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FFechaVenda.java <BR>
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
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.tef.app.ControllerTef;
import org.freedom.tef.app.ControllerTefEvent;
import org.freedom.tef.app.ControllerTefListener;
import org.freedom.tef.driver.Flag;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefAction;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFilho;

public class FAdmTef extends FFilho implements ControllerTefListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String ADM = "ADM";

	private final JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelExit = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 0, 0 ) );

	private final JPanelPad panelCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFields = new JPanelPad();

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private JComboBoxPad cbComando;

	private JComboBoxPad cbBandeiras;

	private final JButton btCommand = new JButton( Icone.novo( "btExecuta.gif" ) );

	private final JLabel lbWarnig = new JLabel( "Selecione o comando e a rede.", SwingConstants.CENTER );

	private final ControllerECF ecf;

	private ControllerTef tef;
	
	private boolean open = false;
	

	public FAdmTef() {

		super( false );
		setTitulo( "Administração TEF" );
		setAtribos( 100, 100, 335, 210 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo );

		if ( AplicativoPDV.bTEFTerm ) {
			
			try {
				tef = AplicativoPDV.getControllerTef();
				tef.setControllerMessageListener( this );
				if ( tef.standardManagerActive() ) {
					montaComboBox();
					montaTela();
					open = true;
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, e.getMessage() );
			}

			btCommand.addActionListener( this );
			btSair.addActionListener( this );
		}
		else {
			Funcoes.mensagemInforma( null, "Esta estação de trabalho não está habilitada para TEF." );
		}
	}

	private void montaTela() {

		Container c = getContentPane();
		c.removeAll();
		c.setLayout( new BorderLayout() );

		panelFields.adic( new JLabelPad( "Comando a ser disparado:" ), 7, 10, 300, 20 );
		panelFields.adic( cbComando, 7, 30, 300, 20 );
		panelFields.adic( new JLabelPad( "Rede:" ), 7, 50, 200, 20 );
		panelFields.adic( cbBandeiras, 7, 70, 200, 20 );
		panelFields.adic( btCommand, 250, 65, 60, 30 );

		lbWarnig.setFont( new Font( "Arial", Font.BOLD, 12 ) );
		lbWarnig.setForeground( Color.BLUE );
		lbWarnig.setPreferredSize( new Dimension( 335, 30 ) );
		lbWarnig.setBorder( BorderFactory.createEtchedBorder() );
		
		btSair.setPreferredSize( new Dimension( 100, 30 ) );
		panelSouth.setPreferredSize( new Dimension( 100, 34 ) );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );

		panelCenter.add( panelFields, BorderLayout.CENTER );
		panelCenter.add( lbWarnig, BorderLayout.SOUTH );
		
		panelExit.add( btSair );
		
		panelSouth.add( panelExit, BorderLayout.EAST );

		c.add( panelCenter, BorderLayout.CENTER );
		c.add( panelSouth, BorderLayout.SOUTH );
	}

	private void montaComboBox() {

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Administrativas - Outras" );
		vVals.addElement( "" );
		vVals.addElement( ADM );
		cbComando = new JComboBoxPad( vLabs, vVals, JTextFieldPad.TP_STRING, 2, 0 );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		vLabs1.addElement( "<--Selecione-->" );
		vVals1.addElement( "" );

		Map<String, Class<TextTef>> flags = Flag.getTextTefFlagsMap();
		Iterable<String> iflag = flags.keySet();
		for ( String flag : iflag ) {
			vLabs1.addElement( flag );
			vVals1.addElement( flag );
		}

		cbBandeiras = new JComboBoxPad( vLabs1, vVals1, JTextFieldPad.TP_STRING, 2, 0 );
	}

	private void invokeAdminTef() {

		if ( "".equals( cbBandeiras.getVlrString() ) ) {
			Funcoes.mensagemInforma( this, "Selecione a rede!" );
		}
		else {
			tef.requestAdministrator( cbBandeiras.getVlrString() );
		}
	}

	public boolean actionTef( final ControllerTefEvent e ) {

		boolean actionTef = false;

		if ( e.getAction() == TextTefAction.WARNING ) {
			lbWarnig.setText( e.getMessage() );
			actionTef = true;
		}
		else if ( e.getAction() == TextTefAction.ERROR ) {
			Funcoes.mensagemErro( null, e.getMessage() );
			actionTef = true;
		}
		else if ( cbComando != null && ADM.equals( cbComando.getVlrString() ) ) {
			actionTef = actionTefADM( e );
		}

		return actionTef;
	}

	private boolean actionTefADM( final ControllerTefEvent e ) {

		boolean actionTef = false;

		if ( e.getAction() == TextTefAction.BEGIN_PRINT ) {
			actionTef = true;
		}
		else if ( e.getAction() == TextTefAction.PRINT ) {
			actionTef = ecf.relatorioGerencial( e.getMessage() );
		}
		else if ( e.getAction() == TextTefAction.END_PRINT ) {
			actionTef = ecf.fecharRelatorioGerencial();
		}
		else if ( e.getAction() == TextTefAction.RE_PRINT ) {
			actionTef = ecf.fecharRelatorioGerencial();
		}
		else if ( e.getAction() == TextTefAction.CONFIRM ) {
			actionTef = Funcoes.mensagemConfirma( this, e.getMessage() ) == JOptionPane.YES_OPTION;
		}

		return actionTef;
	}

	public void actionPerformed( final ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btCommand ) {			
			lbWarnig.setText( "" );			
			if ( ADM.equals( cbComando.getVlrString() ) ) {
				invokeAdminTef();
			}
		}
	}

	@ Override
	public synchronized void execShow() {

		if ( open ) {
			super.execShow();
		}
		else {
			dispose();
		}
	}
}
