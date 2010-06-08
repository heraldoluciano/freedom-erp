package org.freedom.infra.functions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

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
	
	public static String getVersion(Class<?> clazz) {
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

	
}
