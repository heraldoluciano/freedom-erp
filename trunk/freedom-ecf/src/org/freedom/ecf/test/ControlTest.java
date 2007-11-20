package org.freedom.ecf.test;

import org.freedom.ecf.app.Control;

import junit.framework.TestCase;


public class ControlTest extends TestCase {

	public ControlTest( String name ) {

		super( name );
	}
	
	public void testInstanciarControl() {
		
		Control control = null;
		try {
			control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		} catch ( IllegalArgumentException e ) {
		} catch ( NullPointerException e ) {
		}		
		assertTrue( "Control instaciado", control != null );
	}
	
	public void testNaoInstanciarControl_Ecf_Nulo() {
		
		boolean assertresult = false;		
		try {
			new Control( "nome errado" );
		} catch ( NullPointerException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por não carregar ecfdriver.", assertresult );
	}
	
	public void testNaoInstanciarControl_Null() {
		
		boolean assertresult = false;		
		try {
			new Control( null );
		} catch ( IllegalArgumentException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por driver nulo.", assertresult );
	}
	
	public void testNaoInstanciarControl_Invalido() {
		
		boolean assertresult = false;		
		try {
			new Control( "" );
		} catch ( IllegalArgumentException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por parametro invalido.", assertresult );
	}

	public void testLeituraX() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "leitura X > " );
		assertTrue( control.leituraX() );	
		System.out.println( control.getMessageLog() );

		System.out.print( "leitura X > " );
		assertTrue( control.leituraX( true ) );	
		System.out.println( control.getMessageLog() );
		System.out.println( control.readSerialPort() );
	}
	
	public void testLeituraX_ModoDemostracao() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech", true );
		
		System.out.print( "leitura X > " );
		assertTrue( control.leituraX() );	
		System.out.println( control.getMessageLog() );

		System.out.print( "leitura X > " );
		assertTrue( control.leituraX( true ) );	
		System.out.println( control.getMessageLog() );
		System.out.println( control.readSerialPort() );
	}
}
