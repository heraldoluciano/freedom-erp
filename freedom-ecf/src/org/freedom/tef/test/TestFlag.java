package org.freedom.tef.test;

import java.io.File;

import org.freedom.tef.driver.Flag;

import junit.framework.TestCase;


public class TestFlag extends TestCase {

	public TestFlag( String name ) {

		super( name );
	}
	
	public void testLoadTextTefFlagsMap() {
		
		boolean ok = true;

		try {

			Flag.loadParametrosOfInitiation( new File( "C:\\bandeiras.ini" ) ); // torna private...
			
			Flag.getTextTefFlagsMap().get( "VISA" ).newInstance();
		
		} catch ( Exception e ) {
			e.printStackTrace();
			ok = false;
		}
		assertTrue( ok );
	}

}
