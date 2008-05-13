
package org.freedom.infra.x.UIMaker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.freedom.infra.x.swing.FDPanel;

public class Panel extends FDPanel {

	private static final long serialVersionUID = 1l;
	
	public static final float ALFA_NONE = -1f;

	private float alfa = ALFA_NONE;
	
	private int arcWidth;
	
	private int arcHeight; 
	

	public Panel() {

	}

	public Panel( final float alfa ) {

		this();
		setAlfa( alfa );
	}

	public Panel( final float alfa, final Color color ) {

		this();
		setAlfaColor( alfa, color );
	}

	public float getAlfa() {

		return alfa;
	}

	public void setAlfa( final float alfa ) {

		this.alfa = alfa >= 0 ? alfa : ALFA_NONE;
	}

	public void setAlfaColor( final float alfa, final Color color ) {

		setAlfa( alfa );
		setBackground( color );
	}
	
	public int getArcWidth() {	
		return arcWidth;
	}
	
	public void setArcWidth( int arcWidth ) {	
		this.arcWidth = arcWidth;
	}
	
	public int getArcHeight() {	
		return arcHeight;
	}
	
	public void setArcHeight( int arcHeight ) {	
		this.arcHeight = arcHeight;
	}
	
	public void setArc( int arcWidth, int arcHeight ) {	
		setArcWidth( arcWidth );
		setArcHeight( arcHeight );
	}

	@Override
	public void paint( Graphics g ) {

		if ( alfa > ALFA_NONE ) {
			super.paint( paintAlfa( g ) );
		}
		else if ( ( arcWidth > 0 || arcHeight > 0 ) && isOpaque() ) {

			setOpaque( false );
			
			super.paint( paintArc( g ) );
			
			setOpaque( true );
		}
		else {
			super.paint( g );
		}
	}
	
	private Graphics paintAlfa( Graphics g ) {
		
		setOpaque( false );

		Graphics2D g2d = (Graphics2D) g;

		Composite oldComposite = g2d.getComposite();

		AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alfa );
		g2d.setComposite( alphaComposite );

		Rectangle r = g.getClipBounds();
		Color c = getBackground();
		if ( c == null ) {
			c = Color.lightGray;
		}
		g.setColor( c );
		if ( r != null ) {
			g.fillRoundRect( r.x, r.y, r.width, r.height, 
					arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0 );
		}
		else {
			g.fillRoundRect( 0, 0, getWidth(), getHeight(), 
					arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0 );
		}

		g2d.setComposite( oldComposite );
		
		return g2d;
	}
	
	private Graphics paintArc( Graphics g ) {
		
		Rectangle r = g.getClipBounds();
		Color c = getBackground();
		if ( c == null ) {
			c = Color.lightGray;
		}
		g.setColor( c );
		if ( r != null ) {
			g.fillRoundRect( r.x, r.y, r.width, r.height, 
					arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0 );
		}
		else {
			g.fillRoundRect( 0, 0, getWidth(), getHeight(), 
					arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0 );
		}
		
		return g;
	}

}
