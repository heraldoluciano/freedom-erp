package org.freedom.infra.beans;


public interface Component {
	
	void setValue( Object value );
	
	Object getValue();
	
	Field getField();

}
