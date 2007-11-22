package org.freedom.ecf.test;

import java.math.BigDecimal;
import java.util.List;

import org.freedom.ecf.app.Control;
import org.freedom.ecf.driver.AbstractECFDriver;

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

	public void testSuprimento() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.println( "suprimento > " );
		assertTrue( control.suprimento( new BigDecimal( "53.795" ) ) );

		System.out.println( "suprimento Cheque > " );
		assertTrue( control.suprimento( new BigDecimal( "53.793" ), "Cheque" ) );
	}
	
	public void testCupomFiscal() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		/*
		System.out.print( "abre cupom fiscal > " );
		assertTrue( control.abreCupom( "00.000.000/000-00" ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "unidade de medida > " );
		assertTrue( control.unidadeMedida( "Kg" ) );
		System.out.println( control.getMessageLog() );
		*/
		System.out.print( "aumenta descrição do item > " );
		assertTrue( control.aumetaDescricaoItem( 
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " +
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " +
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.TP_QTD_INTEIRO,
				new BigDecimal( "1" ), AbstractECFDriver.DUAS_CASAS_DECIMAIS, new BigDecimal( "15.33" ),
				AbstractECFDriver.TP_DESC_PERCENTUAL, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		/*
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE áàâã éèê íì óòôõ úùûü", new BigDecimal( "18" ), new BigDecimal( "1.5" ),
				new BigDecimal( "15.334" ), new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.TP_QTD_INTEIRO,
				new BigDecimal( "2" ), AbstractECFDriver.TRES_CASAS_DECIMAIS, new BigDecimal( "15.334" ),
				AbstractECFDriver.TP_DESC_PERCENTUAL, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.TP_QTD_DECIMAL,
				new BigDecimal( "1.5" ), AbstractECFDriver.TRES_CASAS_DECIMAIS, new BigDecimal( "15.334" ),
				AbstractECFDriver.TP_DESC_PERCENTUAL, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		*/
	}

	public void testSangria() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.println( "sangria > " );
		assertTrue( control.sangria( new BigDecimal( "53.795" ) ) );

		System.out.println( "sangria Cheque > " );
		assertTrue( control.suprimento( new BigDecimal( "53.793" ), "Cheque" ) );
	}
	
	public void testeGetAllAliquotas() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		List<String> aliquotas = control.getAllAliquotas();
		
		for ( String s : aliquotas ) {
			System.out.println( s );
		}
	}
	
	public void testGetIndexAliquota() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		assertTrue( control.getIndexAliquota( 18f ) != null );
		assertTrue( control.getIndexAliquota( 20.18f ) == null );
		assertTrue( control.getIndexAliquota( 200.185f ) == null );
		assertTrue( control.getIndexAliquota( 0.01f ) != null );
	}
}
