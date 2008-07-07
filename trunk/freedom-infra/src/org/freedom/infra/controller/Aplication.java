package org.freedom.infra.controller;

import java.awt.Window;
import java.io.IOException;
import java.util.Properties;

import org.freedom.infra.util.ini.ManagerIni;


public abstract class Aplication {

	private Window window;
	
	private Properties properties;	
	
	
	public Aplication() throws IOException {
		
		super();

		initParameters();	
		init();		
		show();	
	}
	
	protected abstract void init();
	
	protected void initParameters() throws IOException {

		ManagerIni mi = ManagerIni.createManagerIniParameter();
		properties = mi.getSession( "totem" );
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
	
	public Object getPropertie( Object propertie ) {
		return properties.get( propertie );
	}
	
	public Object putPropertie( String name, Object propertie ) {
		return properties.put( name, propertie );
	}

}
