
package org.freedom.infra.util.text;

import javax.swing.text.GapContent;
import javax.swing.text.PlainDocument;

public abstract class Document extends PlainDocument {

	private static final long serialVersionUID = 1l;

	private Mask mask;

	protected int index = 0;

	public Document() {

		this( new GapContent() );
	}

	public Document( final Content c ) {

		super( c );
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask( final Mask mask ) {
		this.mask = mask;
	}

}
