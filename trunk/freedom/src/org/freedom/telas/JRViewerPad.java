package org.freedom.telas;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;


public class JRViewerPad extends JRViewer {
	
	private JButton btnEmail = new JButton("Email");

	public JRViewerPad( JasperPrint arg0 ) {

		super( arg0 );
		createButtonEmail();
		
	}

	public JRViewerPad( String arg0, boolean arg1 ) throws JRException {

		super( arg0, arg1 );
		createButtonEmail();
		
	}

	public JRViewerPad( InputStream arg0, boolean arg1 ) throws JRException {

		super( arg0, arg1 );
		createButtonEmail();
		
	}

	public JRViewerPad( JasperPrint arg0, Locale arg1 ) {

		super( arg0, arg1 );
		createButtonEmail();
		
	}

	public JRViewerPad( String arg0, boolean arg1, Locale arg2 ) throws JRException {

		super( arg0, arg1, arg2 );
		createButtonEmail();
		
	}

	public JRViewerPad( InputStream arg0, boolean arg1, Locale arg2 ) throws JRException {

		super( arg0, arg1, arg2 );
		createButtonEmail();
		
	}

	public JRViewerPad( JasperPrint arg0, Locale arg1, ResourceBundle arg2 ) {

		super( arg0, arg1, arg2 );
	}

	public JRViewerPad( String arg0, boolean arg1, Locale arg2, ResourceBundle arg3 ) throws JRException {

		super( arg0, arg1, arg2, arg3 );
		createButtonEmail();
		
	}

	public JRViewerPad( InputStream arg0, boolean arg1, Locale arg2, ResourceBundle arg3 ) throws JRException {

		super( arg0, arg1, arg2, arg3 );
		createButtonEmail();
	}

	public void createButtonEmail() {
		tlbToolBar.add( btnEmail );
	}
}
