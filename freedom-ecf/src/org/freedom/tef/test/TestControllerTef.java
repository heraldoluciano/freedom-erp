package org.freedom.tef.test;

import java.io.File;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import junit.framework.TestCase;

import org.freedom.tef.app.ControllerTef;
import org.freedom.tef.app.ControllerTefEvent;
import org.freedom.tef.app.ControllerTefListener;
import org.freedom.tef.driver.text.TextTefProperties;

import sun.misc.MessageUtils;


public class TestControllerTef extends TestCase implements ControllerTefListener {

	public TestControllerTef( String name ) {

		super( name );
	}

	public void testSale() {
		
		boolean ok = true;

		try {		
			
			ControllerTef controllerTef = new ControllerTef(
					getTextTefProperties(),
					new File( "C:\\bandeiras.ini" ),
					ControllerTef.TEF_TEXT );
			
			controllerTef.addControllerMessageListener( this );
			
			ok = controllerTef.requestSale( 2, new BigDecimal( "9.99" ), "VISA" );
			
		} catch ( Exception e ) {
			e.printStackTrace();
			ok = false;
		}
		assertTrue( ok );
	}

	private TextTefProperties getTextTefProperties() {	
		
		final TextTefProperties textTefProperties = new TextTefProperties();
		
		textTefProperties.set( TextTefProperties.PATH_SEND, "C:/Client/Req" );
		textTefProperties.set( TextTefProperties.PATH_RESPONSE, "C:/Client/Resp" );
		
		return textTefProperties;
	}

	public boolean actionTef( ControllerTefEvent event ) {
		
		boolean actionTef = false;

		if ( event.getAction() == ControllerTefEvent.WARNING ) {
			MessageUtils.out( "\n[ WARNING ] " + event.getMessage() + "\n" );
			actionTef = true;
		}	
		else if ( event.getAction() == ControllerTefEvent.ERROR ) {
			MessageUtils.err( "\n[  ERROR  ] " + event.getMessage() + "\n" );
			actionTef = true;
		}		
		else if ( event.getAction() == ControllerTefEvent.CONFIRM ) {
			int option = JOptionPane.showConfirmDialog( null, event.getMessage(), "CONFIM", JOptionPane.YES_NO_OPTION, JOptionPane.CANCEL_OPTION );
			MessageUtils.out( "[ option ] " + ( option == JOptionPane.YES_OPTION ) );
			actionTef = option == JOptionPane.YES_OPTION;
		}	
		else if ( event.getAction() == ControllerTefEvent.BEGIN_PRINT ) {
			MessageUtils.out( "[ Início da impressão do comprovante ] ...\nAbrir Comprovante não Fiscal Vinculado." );
			actionTef = true;
		}	
		else if ( event.getAction() == ControllerTefEvent.PRINT ) {
			MessageUtils.out( event.getMessage() );
			actionTef = false;
		}	
		else if ( event.getAction() == ControllerTefEvent.END_PRINT ) {
			MessageUtils.out( "[ Término da impressão do comprovante ] ...\nFechar Comprovante não Fiscal Vinculado." );
			actionTef = true;
		}
		
		return actionTef;
	} 
}
