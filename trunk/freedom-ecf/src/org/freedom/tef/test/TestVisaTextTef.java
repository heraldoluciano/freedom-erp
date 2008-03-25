package org.freedom.tef.test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;

import junit.framework.TestCase;

import org.freedom.tef.driver.Flag;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefFactore;
import org.freedom.tef.driver.text.TextTefProperties;


public class TestVisaTextTef extends TestCase {

	public TestVisaTextTef( String name ) {

		super( name );
	}

	public void testCreateVisaTextTef() {
		
		boolean ok = true;

		try {
			
			// testado instanciar pela fabrica  OK
			// testado verificar se está ativo (#isActive())  OK  obs.: verificar condições para "ATIVO"

			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
		
		} catch ( Exception e ) {
			e.printStackTrace();
			ok = false;
		}
		assertTrue( ok );
	}
	
	public void testIsActive() {
		
		boolean ok = true;

		try {

			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
			
			ok = textTef.isActive();
		
		} catch ( Exception e ) {
			e.printStackTrace();
			ok = false;
		}
		assertTrue( ok );		
	}

	public void testRequestSale() {
		
		boolean ok = true;

		try {

			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
			
			ok = textTef.requestSale( 2, new BigDecimal( "9.99" ) );
			
			if ( ok ) {
				String message = textTef.getTextTefProperties().
									getProperty( TextTefProperties.MESSAGE_OPERATOR );
				JOptionPane.showMessageDialog( null, message );
				
				List<String> responseToPrint = textTef.getResponseToPrint();
				
				for ( String s : responseToPrint ) {
					System.out.println( s );
				}
			}
		
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
}
