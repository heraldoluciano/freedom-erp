package org.freedom.infra.x.util.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;
import javax.swing.text.PlainDocument;


public class StringDocument extends PlainDocument {
	
	private static final long serialVersionUID = 1l;
	
	private Mask mask;

	private int index = 0;
	

	public StringDocument() {

		this( new GapContent(), null );
	}

	public StringDocument( final Content c ) {

		this( c, null );
	}

	public StringDocument( final int size ) {

		this( new GapContent(), size );
	}

	public StringDocument( final String mask ) {

		this( new GapContent(), new Mask( mask ) );
	}

	public StringDocument( final Mask mask ) {

		this( new GapContent() ,mask );
	}

	public StringDocument( final Content c, final int size ) {

		this( c , Mask.createString( size ) );
	}

	public StringDocument( final Content c, final Mask mask ) {

		super( c );
		setMask( mask );
	}
	
	public Mask getMask() {	
		return mask;
	}
	
	public void setMask( final Mask mask ) {			
		this.mask = mask;
	}

	@Override
	public void insertString( final int offs, final String str, final AttributeSet a ) throws BadLocationException {

		if ( mask != null ) {
			
			final StringBuilder newstr = new StringBuilder();
			
			if ( getLength() >= mask.length() ) {
				return;
			}

			final char[] chars = mask.getChars();
			final char[] value = str.toCharArray();

			for ( int i = 0; i < value.length; i++ ) {
				if ( index >= chars.length ) {
					break;
				}
				validateCharacter( chars, value[ i ], newstr );
			}
			
			super.insertString( offs, newstr.toString(), a );
		}
		else {
			super.insertString( offs, str, a );
		}

	}
	
	@Override
	public void remove( final int arg0, final int arg1 ) throws BadLocationException {

		super.remove( arg0, arg1 );
		index = arg0;
	}
	
	private void validateCharacter( char[] ch, char value, StringBuilder str ) {
		
		if ( getLength()+str.length() >= ch.length ) {
			return;
		}
		if ( ch[index] == Mask.ALL ) {
			str.append( value );
			index++;
		}
		else if ( ch[index] == Mask.INTEGER ) {
			if ( Character.isDigit( value ) ) {						
				str.append( value );						
				index++;
			}
		}
		else if ( ch[index] == Mask.CHAR ) {
			if ( Character.isLetter( value ) ) {						
				str.append( value );						
				index++;
			}
		}
		else {		
			if ( ch[index] == value ) {
				str.append( ch[index++] );
			}
			else {
				str.append( ch[index++] );
				validateCharacter( ch, value, str );
			}
		}
	}

}
