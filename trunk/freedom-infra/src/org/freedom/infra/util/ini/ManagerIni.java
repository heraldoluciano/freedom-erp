package org.freedom.infra.util.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ManagerIni {
	
	public static final String FILE_INIT_DEFAULT = "INITFILE";
	
	private static final String PROPERTIES_DEFAULT = "PROPERTIES_DEFAULT";
	
	private Map<String, Properties> sessions = new HashMap<String, Properties>();
	
	private Properties properties;
	
	private final File file;

	
	public static ManagerIni createManagerIniParameter() throws IOException {
		
		return new ManagerIni( new File( System.getProperty( FILE_INIT_DEFAULT, "" ) ) );
	}
	
	public static ManagerIni createManagerIniParameter( String initFileName ) throws IOException {
		
		return new ManagerIni( new File( System.getProperty( initFileName, "" ) ) );
	}
	
	public static ManagerIni createManagerIniFile( File file ) throws IOException {
		
		return new ManagerIni( file );
	}
	
	public static ManagerIni createManagerIniFile( String fileName ) throws IOException {
		
		return new ManagerIni( new File( fileName ) );
	}
	
	private ManagerIni( File initFile ) throws IOException {
		
		if ( initFile == null || !initFile.exists() ) {
			throw new IllegalArgumentException( "Not found init file." );
		}
		
		file = initFile;
		
		readFile();
	}
	
	private void readFile() throws IOException {
		
		FileReader reader = new FileReader( file );
		BufferedReader buffered = new BufferedReader( reader );
		
		sessions = new HashMap<String, Properties>();
		properties = null;
		
		if ( buffered != null ) {
			
			String line = "";
			String name = null;
			String value = null; 
			int ivl = -1;

			while ( ( line = buffered.readLine() ) != null ) {
				
				if ( properties == null ) {
					properties = new Properties();
					sessions.put( PROPERTIES_DEFAULT, properties );
				}
				
				line = line.trim();
				
				if ( line.length() == 0 ) {
					continue;
				}
				else if ( '#' == line.charAt( 0 ) ) {
					continue;
				}
				else if ( line.length() > 1 && ( '/' == line.charAt( 0 ) && '/' == line.charAt( 1 ) ) ) {
					continue;
				}
				else if ( ckeckNewSession( line ) ) {
					continue;
				}
				
				ivl = line.indexOf( '=' );
				
				if ( ivl > -1 ) {					
					name = line.substring( 0, ivl );
					value = line.substring( ivl + 1 );	
					properties.put( name, value );
				}
			}
		}
	}
	
	public void postProperties() throws IOException {
		
		PrintWriter printWriter = new PrintWriter( file );
		
		if ( printWriter != null ) {
			
			Properties p;
			Object[] pk;
			
			for ( String nameSession : sessions.keySet() ) {
			
				p = sessions.get( nameSession );
				if ( p.size() > 0 ) {
					
					printWriter.println( "[" + nameSession + "]" );
					pk = p.keySet().toArray();
					Arrays.sort( pk );

					for ( Object k : pk ) {
						printWriter.println( k + "=" + p.getProperty( (String)k ) );
					}

					printWriter.println();
				}
			}
			
			if ( sessions.size() > 0 ) {
				printWriter.flush();
				printWriter.close();				
			}
		}
	}
	
	private boolean ckeckNewSession( String line ) {
			
		int ik1 = line.indexOf( '[' );
		int ik2 = line.indexOf( ']' );
		
		if ( ik1 > -1 && ik2 > -1 && ik2 > ik1+1 ) {
			
			properties = new Properties();
			sessions.put( line.substring( ik1+1, ik2 ), properties );
			
			return true;
		}
		
		return false;
	}
	
	public Properties getSession( String session ) {
		
		return sessions.get( session );
	}
	
	public void setSession( String sessionName, Properties session ) {
		
		sessions.put( sessionName, session );
	}
	
	public String getProperty( String session, String key ) {
		
		String value = null;
		
		Properties p = sessions.get( session );
		if ( p != null && key != null ) {
			value = p.getProperty( key );
		}
		
		return value;
	}
	
	public void setProperty( String session, String key, String value ) {
				
		Properties p = sessions.get( session );
		p.setProperty( key, value );
	}
}
