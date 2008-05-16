package org.freedom.infra.util.text;


public class Mask {
	
	public static final char CHAR = 'A';
	
	public static final char INTEGER = '0';
	
	public static final char NEGATIVE = '-';
	
	public static final char DECIMAL = ',';
	
	public static final char ALL = '#';
	
	private int length = 0;

	private char[] chars = null;
	
	
	public Mask( final String mask ) {
		
		length = mask.length();		
		chars = mask.toCharArray();		
	}

	public char[] getChars() {

		char[] retorno = new char[ chars.length ];
		System.arraycopy( chars, 0, retorno, 0, retorno.length );
		
		return retorno;
	}

	public int length() {
		return length;
	}

	public void setLength( final int length ) {
		this.length = length;
	}

	public String getMask() {
		return new String( chars );
	}

	////////////////////////////////////////////////////////////////////////////////
	//                                                                            //
	//                           MASCARAS PRÉ-DEFINIDAS                           //
	//                                                                            //
	////////////////////////////////////////////////////////////////////////////////
	
	public static Mask createInteger( final int size ) {
		
		StringBuilder mask = new StringBuilder();
		
		int count = 0;
		while ( count++ < size ) {
			mask.append( "0" );
		}
		
		return new Mask( mask.toString() );
	}
	
	public static Mask createDecimal( final int size, final int precision ) {
		
		StringBuilder mask = new StringBuilder();
		
		int count = 0;
		while ( count++ < size-precision ) {
			mask.append( "0" );
		}
		
		mask.append( "," );
		count = 0;
		
		while ( count++ < precision ) {
			mask.append( "0" );
		}
		
		return new Mask( mask.toString() );
	}
	
	public static Mask createString( final int size ) {
		
		StringBuilder mask = new StringBuilder();
		
		int count = 0;
		while ( count++ < size ) {
			mask.append( "#" );
		}
		
		return new Mask( mask.toString() );
	}
	
	public static Mask createDateDDMMYYYY() {
				
		return new Mask( "00/00/0000" );
	}
	
	public static Mask createDateYYYYMMDD() {
				
		return new Mask( "0000/00/00" );
	}
	
	public static Mask createTimeHHMMSS() {
				
		return new Mask( "00:00:00" );
	}
	
	public static Mask createTimeHHMMSSmmm() {
				
		return new Mask( "00:00:00.000" );
	}

}
