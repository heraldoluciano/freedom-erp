package org.freedom.tef.test;

import java.io.File;
import java.math.BigDecimal;

import junit.framework.TestCase;

import org.freedom.tef.driver.Flag;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefFactore;
import org.freedom.tef.driver.text.TextTefProperties;


public class TestVisaTextTef extends TestCase {

	public TestVisaTextTef( String name ) {

		super( name );
	}

	public void testCreateVisaTextTef1() {
		
		boolean ok = false;

		try {
			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
		
			ok = textTef != null;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		assertTrue( ok );
	}

	public void testCreateVisaTextTef2() {
		
		boolean ok = false;

		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA", new File( "C:\\bandeiras.ini" ) );		
			ok = textTef != null;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		assertTrue( ok );
	}

	public void testCreateVisaTextTef3() {
		
		boolean ok = false;

		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA", "C:\\bandeiras.ini" );		
			ok = textTef != null;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		assertTrue( ok );
	}
	
	public void testIsActive() {
		
		boolean ok = false;

		try {
			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
			
			ok = textTef.isActive();		
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		assertTrue( ok );		
	}

	public void testRequestSale() {
		
		boolean ok = false;
		String message = "";

		try {
			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
			
			ok = textTef.requestSale( 2, new BigDecimal( "9.99" ) );
			
			if ( ok ) {
				message = "\n[ CONFIRMATION OF SALE ]";
				ok = textTef.confirmationOfSale();
			} 
			else {
				message = "\n[ NOT CONFIRMATION OF SALE ]";
				ok = textTef.noConfirmationOfSale();
			}		
		} catch ( Exception e ) {
			System.out.println( "[ ERROR ] ... Erro ao testar requestSale( mumberDoc, valor )." + message );
			e.printStackTrace();
		}
		
		assertTrue( ok );
	}
	
	public void testRequestAdministrator() {
		
		boolean ok = false;

		try {
			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) );			
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), "VISA" );
			
			ok = textTef.requestAdministrator();		
		} catch ( Exception e ) {
			e.printStackTrace();
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
