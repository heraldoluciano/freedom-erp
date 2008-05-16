package org.freedom.infra.beans;

import org.freedom.infra.util.text.Mask;


public class Field {
	
	private Object value;
	
	private Mask mask;
	
	
	public Field() { }
	
	public void setValue( final Object value ) {	
		this.value = value;
	}
	
	public Object getValue() {	
		return value;
	}
		
	public Mask getMask() {	
		return mask;
	}
	
	public void setMask( final Mask mask ) {	
		this.mask = mask;
	}
	
	public String toString() {
		return value.toString();
	}

	public Integer toInteger() {
		
		Integer integer = null;
		try {
			if ( value != null ) {
				integer = new Integer( value.toString() );
			}
		}
		catch ( NumberFormatException e ) {
			e.printStackTrace();
		}
		
		return integer ;
	}

	public Float toFloat() {
		
		Float floatvalue = null;
		try {
			if ( value != null ) {
				floatvalue = new Float( value.toString().replace( "\\.", "" ).replace( ',', '.' ) );
			}
		}
		catch ( NumberFormatException e ) {
			e.printStackTrace();
		}
		
		return floatvalue ;
	}

}
