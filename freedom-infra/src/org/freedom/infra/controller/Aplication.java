package org.freedom.infra.controller;

import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public abstract class Aplication {

	private Window window;
	
	private File fileIni;
	
	private final Properties properties = new Properties();	
	
	
	public Aplication() {
		
		super();

		initParameters();	
		init();		
		show();	
	}
	
	protected abstract void init();
	
	protected void initParameters() {

		try {
			if ( fileIni == null ) {
				fileIni = new File( System.getProperty( "ARQINI", "" ) ) ;
			}
			
			if ( fileIni != null && fileIni.exists() ) {
				FileInputStream fis = new FileInputStream( fileIni );
				properties.load( fis );
				fis.close();
			}
		}
		catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	protected void show() {		
		if ( window != null ) {
			window.setVisible( true );
		}
	}
	
	public Window getWindow() {	
		return window;
	}
	
	public void setWindow( Window window ) {	
		this.window = window;
	}
	
	public File getFileIni() {	
		return fileIni;
	}
	
	public void setFileIni( File fileIni ) {	
		this.fileIni = fileIni;
	}
	
	public Object getPropertie( Object propertie ) {
		return properties.get( propertie );
	}
	
	public Object putPropertie( String name, Object propertie ) {
		return properties.put( name, propertie );
	}

}
