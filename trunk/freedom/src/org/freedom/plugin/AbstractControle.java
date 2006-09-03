package org.freedom.plugin;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractControle {

	private Map objects;

	public void addAttribute( String name, Object obj ) {

		if ( objects == null ) {
			objects = new HashMap();
		}
		
		objects.put( name, obj );
	}

	public Object getAttribute( String name ) {

		Object ret = null;
		
		if ( objects != null ) {
			ret = objects.get( name );
		}
		
		return ret;
	}
	
	public void setAttribute( String name, Object value ) {
		
		if ( objects != null ) {
			objects.remove( name );
			objects.put( name, value );
		}
		
	}
	
	public abstract void inicializa();

}
