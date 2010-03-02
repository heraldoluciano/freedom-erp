package org.freedom.infra.functions;

public class SystemFunctions {

	public static final int OS_LINUX = 0;

	public static final int OS_WINDOWS = 1;

	
	/**
	 * 
	 * @return A name of operational system.<BR>
	 */
	public static int getOS() {
		
		int ret = -1;
		
		final String system = System.getProperty( "os.name" ).toLowerCase();

		if ( system.indexOf( "linux" ) > -1 ) {
			ret = OS_LINUX;
		} 
		else if ( system.indexOf( "windows" ) > -1 ) {
			ret = OS_WINDOWS;
		}

		return ret;

	}
	
}
