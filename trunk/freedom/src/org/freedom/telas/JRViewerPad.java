package org.freedom.telas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;


public class JRViewerPad extends JRViewer {
	
	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelButtonsStp = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.RIGHT, 3, 3 ) );
	
	private JButton btnEmail;

	private JasperPrint report = null;

	public JRViewerPad( JasperPrint arg0 ) {

		super( arg0 );
		report = arg0;
		init();
	}

	public JRViewerPad( JasperPrint arg0, Locale arg1 ) {

		super( arg0, arg1 );
		init();
		report = arg0;		
	}

	public JRViewerPad( JasperPrint arg0, Locale arg1, ResourceBundle arg2 ) {

		super( arg0, arg1, arg2 );
		init();
		report = arg0;
	}
	
	private void init() {
		
		tlbToolBar.add( panelButtonsStp );
		panelButtonsStp.setPreferredSize( new Dimension( 115, 30 ) );
		
		createButtonEmail();
	}

	private void createButtonEmail() {
		
		btnEmail = new JButton( "e-mail", Icone.novo( "mail.gif" ) );
		btnEmail.setToolTipText( "Enviar arquivo por e-mail" );
		btnEmail.setPreferredSize( new Dimension( 100, 23 ) );
		
		btnEmail.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				showDLEnviarEmail();
			} 			
		} );		
		
		panelButtonsStp.add( btnEmail );
	}
	
	private void showDLEnviarEmail() {
		
		DLEnviarEmail enviaemail = new DLEnviarEmail( this, null );
		enviaemail.setReport( report );
		enviaemail.preparar();
		if ( enviaemail.preparado() ) {
			enviaemail.setVisible( true );
		} else {
			enviaemail.dispose();
		}
	}
}
