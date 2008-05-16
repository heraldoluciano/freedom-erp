
package org.freedom.infra.util.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;

public class IntegerDocument extends Document {

	private static final long serialVersionUID = 1l;

	private Mask mask;

	private int index = 0;

	public IntegerDocument() {

		this( new GapContent(), 10 );
	}

	public IntegerDocument( final Content c ) {

		this( c, 10 );
	}

	public IntegerDocument( final int size ) {

		this( new GapContent(), size );
	}

	public IntegerDocument( final Content c, final int size ) {

		super( c );
		setMask( Mask.createInteger( size ) );
	}

	public Mask getMask() {

		return mask;
	}

	public void setMask( final Mask mask ) {

		this.mask = mask;
	}

	@Override
	public void insertString( final int offs, final String str, final AttributeSet a ) throws BadLocationException {

		StringBuilder newstr = new StringBuilder(
				mask != null ? formaterMask( str, offs ) : formaterInteger( str ) );

		super.insertString( offs, newstr.toString(), a );

	}

	@Override
	public void remove( final int offs, final int len ) throws BadLocationException {

		if ( mask != null ) {
			if ( getText( offs, len ).indexOf( '-' ) > -1 ) {
				mask.setLength( mask.length() - 1 );
				super.remove( offs, len );
			}
			else {
				super.remove( offs, len );
				index = getLength();
				if ( getText( 0, index ).indexOf( '-' ) > -1 ) {
					--index;
				}
			}
		}
		else {
			super.remove( offs, len );
		}
	}
	
	private String formaterMask( String str, int offs ) throws BadLocationException {
		
		StringBuilder newstr = new StringBuilder();
		final char[] value = str.toCharArray();
		
		if ( offs >= mask.length() ) {
			return newstr.toString();
		}

		for ( int i = 0; i < value.length; i++ ) {
			if ( ! formaterMaskAux( value[i], newstr, offs ) ) {
				break;
			}
		}

		return newstr.toString();
	}
	
	private boolean formaterMaskAux( char value, StringBuilder str, int offs ) throws BadLocationException {
		
		final char[] chars = mask.getChars();

		if ( index >= chars.length ) {
			if ( ! formaterMaskAuxNegative( value, str, offs ) ) {
				return false;
			}
		}
		else if ( ! formaterMaskAuxNegative( value, str, offs ) ) {
			formaterMaskAuxInteger( value, str );
		}
		
		return true;
	}
	
	private boolean formaterMaskAuxNegative( char value, StringBuilder str, int offs ) throws BadLocationException {
		
		if ( value == Mask.NEGATIVE 
				&& offs == 0 && getText( 0, getLength() ).indexOf( Mask.NEGATIVE ) == -1 ) {
			mask.setLength( mask.length()+1 );
			str.append( value );
			return true;
		}
			
		return false;
	}
	
	private void formaterMaskAuxInteger( char value, StringBuilder str) throws BadLocationException {
		
		final char[] chars = mask.getChars();
		
		if ( chars[ index ] == Mask.INTEGER ) {
			if ( Character.isDigit( value ) ) {
				str.append( value );
				index++;
			}
		}
	}
	
	private String formaterInteger( String str ) {
		
		StringBuilder newstr = new StringBuilder();
		final char[] value = str.toCharArray();
		
		for ( int i = 0; i < value.length; i++ ) {
			if ( Character.isDigit( value[ i ] ) || value[ i ] == Mask.NEGATIVE ) {
				newstr.append( value[ i ] );
			}
		}

		return newstr.toString();
	}

}
