package org.freedom.infra.functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.freedom.infra.model.jdbc.DbConnection;

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
	
	public static java.util.Date getClassDateCompilation(Class<?> clazz) throws IOException {  
	    String className = clazz.getName();  
	    className = className.replaceAll("\\.", "/");  
	    className = "/" + className + ".class";  
	    URL url = Class.class.getResource(className);
	    URLConnection urlConnection = url.openConnection();  
	    java.util.Date lastModified = new java.util.Date(urlConnection.getLastModified());  
	    return lastModified;  
	}
	
	public static String getVersionSis(Class<?> clazz) {
		String versao = "";
		
		try {
			
			 File file =  new File ( clazz.getProtectionDomain().getCodeSource().getLocation().toURI());			 
			 JarFile jarfile = new JarFile(file.getAbsolutePath());
			 
			 Manifest manifest = jarfile.getManifest();
			 
			 Attributes att = manifest.getMainAttributes();
			 
			 versao = att.getValue("Signature-Version");			
			
		}
		catch (Exception e) {
			versao = "Indefinida";
		}
		
		return versao;
		
	}
	
	public static String getVersionDB(DbConnection con) {
		
		String ret="Indefinida";
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null; 
		PreparedStatement ps = null;
		try {
			
			sql.append("select versao from sgretversao");

			ps = con.prepareStatement( sql.toString() );

			rs = ps.executeQuery();

			if(rs.next()) {
				ret = rs.getString("versao");
			}
			
			ps.close();
			rs.close();
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getTxtFile( URL url ) {

		String ret = "";
		int size = 0;
		char c = (char) 0;

		try {
			
			File fArq = new File( url.getFile() );
			FileReader frArq = new FileReader( fArq );
		
			try {
							
				size = (int) fArq.length();
				
				for ( int i = 0; i < size; i++ ) {
					c = (char) frArq.read();
					ret += c;
				}				
			} 
			catch ( IOException err ) {
				err.printStackTrace();
				System.exit( 0 );
			}
		} catch ( FileNotFoundException err ) {
			err.printStackTrace();
			//System.exit( 0 );
		}
		return ret;
	}
	
}
