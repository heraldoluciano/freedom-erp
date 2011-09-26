package org.freedom.modulos.crm.business.component;

import java.awt.Color;


public class Constant extends org.freedom.infra.pojos.Constant {
	
	private static final long serialVersionUID = 1L;
	private Color color = null;

	public Constant( String name, Object value ) {

		super( name, value );
		
	}
	
	public Constant( String name, Object value, Color color ) {
		super( name, value);
		setColor( color );
	}
	
	public Color getColor() {
	
		return color;
	}

	
	public void setColor( Color color ) {
	
		this.color = color;
	}


}
