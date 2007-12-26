package org.freedom.infra.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class LoggerManager {

	private static Map<String,Logger> loggerList = new HashMap<String, Logger>();
	
	private LoggerManager() {
	}
	
	private static void createLooger( final String loggerName ) 
		throws IllegalArgumentException {
		
		if ( loggerName == null || loggerName.trim().length() == 0 ) {
			throw new IllegalArgumentException( "Nome de log inválido!" );
		}
		
		Logger logger = Logger.getLogger( loggerName );		
		loggerList.put( loggerName, logger );
		configureLogger( logger);
	}
	
	private static void configureLogger( final Logger logger ) {
		
		Appender appender = null;
		try {
			PatternLayout layout = new PatternLayout( PatternLayout.TTCC_CONVERSION_PATTERN );
			appender = new FileAppender( layout, logger.getName() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		logger.addAppender( appender );
		logger.setLevel( Level.INFO );
		BasicConfigurator.configure( appender );
	}
	
	public static Logger getLogger( final String loggerName )  
		throws IllegalArgumentException {
		
		Logger logger = loggerList.get( loggerName );
				
		if ( logger == null ) {			
			createLooger( loggerName );
			logger = loggerList.get( loggerName );
		}
		
		return logger;
	}

}
