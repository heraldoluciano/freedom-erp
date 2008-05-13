
package org.freedom.infra.x.swing;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class FDTextField extends JTextField {

	private static final long serialVersionUID = 1l;

	public FDTextField() {

		this( null, null, 0 );
	}

	public FDTextField( String text ) {

		this( null, text, 0 );
	}

	public FDTextField( int columns ) {

		this( null, null, columns );
	}

	public FDTextField( String text, int columns ) {

		this( null, text, columns );
	}

	public FDTextField( Document doc, String text, int columns ) {

		super( doc, text, columns );
	}

}
