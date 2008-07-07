
package org.freedom.infra.util.logger;

import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.freedom.infra.util.ini.ManagerIni;

/**
 * Sub-classe para personalização de logs utilizando a API Log4J.
 * 
 * @author Anderson Sanchez - 30/06/2008;
 */
public class FreedomLogger extends Logger {

	static String FQCN = FreedomLogger.class.getName() + ".";

	private static FreedomLoggerFactory myFactory = new FreedomLoggerFactory();

	private static String LAYOUT_DEFAULT = "%p >> %d{dd MMM yyyy HH:mm:ss:ms} %c - %m%n";

	public final static String LOGGER_JPA = "arqlog_jpa";

	public final static String LOGGER_SYS = "arqlog_sys";

	public final static String LOGGER_ECF = "arqlog_ecf";

	public FreedomLogger( String name ) {

		super( name );
	}

	public static Logger getLogger( String name ) {

		Logger c = Logger.getLogger( name, myFactory );
		Layout layout = new PatternLayout( LAYOUT_DEFAULT );
		c.addAppender( new ConsoleAppender( layout, ConsoleAppender.SYSTEM_OUT ) );
		return c;
	}

	public static Logger getLogger( Class<?> cl, String property ) {

		Properties properties;
		Logger c = null;
		try {
			ManagerIni mi = ManagerIni.createManagerIniParameter();
			properties = mi.getSession( "FreedomLogger" );
			c = Logger.getLogger( cl.getCanonicalName(), myFactory );
			Layout layout = new PatternLayout( LAYOUT_DEFAULT );
			c.addAppender( new FileAppender( layout, properties.get( property ).toString(), true ) );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return c;
	}

	public static Logger getLogger( String name, String file ) {

		Logger c = Logger.getLogger( name, myFactory );
		try {
			Layout layout = new PatternLayout( LAYOUT_DEFAULT );
			c.addAppender( new FileAppender( layout, file, true ) );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return c;
	}

	public void log( int lv, String sMsg ) {

		if ( lv == Level.INFO_INT ) {
			setLevel( Level.toLevel( lv ) );
			info( sMsg );
		}
		else if ( lv == Level.WARN_INT ) {
			setLevel( Level.toLevel( lv ) );
			warn( sMsg );
		}
		else if ( lv == Level.ERROR_INT ) {
			setLevel( Level.toLevel( lv ) );
			error( sMsg );
		}
		else if ( lv == Level.TRACE_INT ) {
			setLevel( Level.toLevel( lv ) );
			trace( sMsg );
		}
	}

	public void trace( Object message ) {

		super.log( FQCN, XLevel.TRACE, message, null );
	}

	public static void setLayoutDefault( final String ld ) {

		LAYOUT_DEFAULT = ld;
	}
}