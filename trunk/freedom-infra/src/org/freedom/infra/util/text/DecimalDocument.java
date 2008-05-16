
package org.freedom.infra.util.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;

public class DecimalDocument extends Document {

	private static final long serialVersionUID = 1l;

	private Mask mask;

	private int index = 0;

	private int precision = 0;
	
	private int indexDecimal = -1;

	public DecimalDocument() {

		this( new GapContent(), 15, 5 );
	}

	public DecimalDocument( final Content c ) {

		this( c, 15, 5 );
	}

	public DecimalDocument( final int size, final int precision ) {

		this( new GapContent(), size, precision );
	}

	public DecimalDocument( final Content c, final int size, final int precision ) {

		super( c );
		setMask( Mask.createDecimal( size, precision ) );
		this.precision = precision;
	}

	public Mask getMask() {

		return mask;
	}

	public void setMask( final Mask mask ) {

		this.mask = mask;
	}

	@Override
	public void insertString( final int offs, final String str, final AttributeSet a ) throws BadLocationException {

		StringBuilder newstr = new StringBuilder( mask != null ? formaterMask( str, offs ) : str );
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

		try {

			if ( offs >= mask.length() ) {
				return newstr.toString();
			}

			final char[] value = str.toCharArray();

			indexDecimal = getText( 0, getLength() ).indexOf( Mask.DECIMAL );
			int decimalCount = 0;

			if ( indexDecimal > -1 && indexDecimal < ( mask.length() - 1 ) ) {
				String text = getText( 0, getLength() );
				decimalCount = text.substring( indexDecimal ).length() - 1;
			}

			for ( int i = 0; i < value.length; i++ ) {

				formaterMaskAux( value[i], offs, newstr, decimalCount );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return newstr.toString();
	}
	
	private void formaterMaskAux( char value, 
			                      int offs, 
			                      StringBuilder str, 
			                      int decimalCount ) throws BadLocationException {
				
		if ( index >= mask.length() ) {
			if ( ! formaterMaskAuxNegative( value, offs, str ) ) {
				return;
			}
		}
		else if ( formaterMaskAuxNegative( value, offs, str ) ) {
			// refectore para diminuir a complexdade.
		}
		else if ( index < mask.getChars().length 
					&& formaterMaskAuxDecimal( value, offs, str, decimalCount ) ) {
			// refectore para diminuir a complexdade.
		}
		else if ( formaterMaskAuxDigit( value, offs, str, decimalCount ) ) {
			// refectore para diminuir a complexdade.
		}
	}
	
	private boolean formaterMaskAuxNegative( char value, int offs, StringBuilder str ) throws BadLocationException {
				
		if ( value == Mask.NEGATIVE && offs == 0 
				&& getText( 0, getLength() ).indexOf( Mask.NEGATIVE ) == -1 ) {
			mask.setLength( mask.length() + 1 );
			str.append( value );
			return true;
		}
		
		return false;
	}
	
	private boolean formaterMaskAuxDecimal( char value, int offs, StringBuilder str, int decimalCount ) throws BadLocationException {
		
		boolean decimal = formaterMaskAuxDecimalMask( value, offs, str, decimalCount ) 
							|| formaterMaskAuxDecimalValue( value, offs, str, decimalCount );		
		return decimal;
	}
	
	private boolean formaterMaskAuxDecimalMask( char value, int offs, StringBuilder str, int decimalCount ) throws BadLocationException {
		
		final char[] chars = mask.getChars();
		
		boolean decimal = false;
		
		if ( Character.isDigit( value ) && chars[index] == Mask.DECIMAL ) {
			if ( getText( 0, getLength() ).indexOf( Mask.DECIMAL ) == -1 ) {
				str.append( Mask.DECIMAL );
				index++;
			}
			if ( Character.isDigit( value ) ) {
				str.append( value );
				index++;
			}
			decimal = true;
		}
		
		return decimal;
	}
	
	private boolean formaterMaskAuxDecimalValue( char value, int offs, StringBuilder str, int decimalCount ) throws BadLocationException {
				
		boolean decimal = false;
		
		if ( ( value == Mask.DECIMAL || value == '.' ) 
				&& decimalCount < precision && indexDecimal == -1 ) {
			if ( getLength()+str.length() == 0 
					|| str.length() > 0 && str.charAt( 0 ) == Mask.NEGATIVE ) {
				str.append( '0' );
				index++;
			}
			str.append( Mask.DECIMAL );
			indexDecimal = index;
			index++;
			decimal = true;
		}
		
		return decimal;
	}
	
	private boolean formaterMaskAuxDigit( char value, int offs, StringBuilder str, int decimalCount ) throws BadLocationException {
		
		if ( Character.isDigit( value ) ) {
			int size =
				getText( 0, indexDecimal > -1 ? (indexDecimal<getLength()?indexDecimal:getLength()) : 0 ).length() + 
				precision + 1; // soma um por conta do separador decimal.
			
			if ( ( decimalCount < precision && offs > indexDecimal ) 
					|| ( offs <= indexDecimal && getLength()+str.length() < mask.length() 
						&& size < mask.length() ) ) {
				str.append( value );	
				index++;
			}
			return true;
		}
		
		return false;
	}

}
