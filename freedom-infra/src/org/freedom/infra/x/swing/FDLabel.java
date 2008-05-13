package org.freedom.infra.x.swing;

import javax.swing.Icon;
import javax.swing.JLabel;


public class FDLabel extends JLabel {

	private static final long serialVersionUID = 1l;

	public FDLabel() {
		super();
	}

	public FDLabel( Icon image, int horizontalAlignment ) {
		super( image, horizontalAlignment );
	}

	public FDLabel( Icon image ) {
		super( image );
	}

	public FDLabel( String text, Icon icon, int horizontalAlignment ) {
		super( text, icon, horizontalAlignment );
	}

	public FDLabel( String text, int horizontalAlignment ) {
		super( text, horizontalAlignment );
	}

	public FDLabel( String text ) {
		super( text );
	}
}
