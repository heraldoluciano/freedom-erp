package org.freedom.funcoes.exporta;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.funcoes.Funcoes;

public abstract class Contabil {

	public static final String FREEDOM_CONTABIL = "01";

	public static final String SAFE_CONTABIL = "02";

	public static final String EBS_CONTABIL = "03";

	protected static final int NUMERIC = 0;

	protected static final int CHAR = 1;

	protected static final int DATE = 2;

	protected static int ESQUERDA = 0;

	protected static int DIREITA = 1;
	
	protected String format( Object obj, int tipo, int tam, int dec ) {

		String retorno = null;
		String str = null;

		if ( obj == null ) {
			str = "";
		}
		else {
			str = obj.toString();
		}

		if ( tipo == NUMERIC ) {
			if ( dec > 0 ) {
				retorno = Funcoes.transValor( str, tam - 1, dec, true );
				retorno = retorno.substring( 0, tam - dec - 1 ) + "," + retorno.substring( tam - dec - 1 );
			}
			else {
				retorno = Funcoes.strZero( str, tam );
			}
		}
		else if ( tipo == CHAR ) {
			retorno = Funcoes.adicionaEspacos( str, tam );
		}
		else if ( tipo == DATE ) {
			int[] args = Funcoes.decodeDate( (Date) obj );
			retorno = Funcoes.strZero( String.valueOf( args[2] ), 2 ) + Funcoes.strZero( String.valueOf( args[1] ), 2 ) + Funcoes.strZero( String.valueOf( args[0] ), 4 );
		}

		return retorno;
	}

	private String transData( Date data ) {
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime( data );
		StringBuffer result = new StringBuffer();
		result.append( strZero( cal.get( Calendar.DAY_OF_MONTH ), 2 ) );
		result.append( strZero( cal.get( Calendar.MONTH ) + 1, 2 ) );
		result.append( strZero( cal.get( Calendar.YEAR ), 4 ) );
		return result.toString();
	}

	private String strZero( int valor, int tam ) {
		
		StringBuffer result = new StringBuffer();
		if ( String.valueOf( valor ).length() < tam ) {
			result.append( replicate( "0", tam - String.valueOf( valor ).length() ) );
		}
		result.append( String.valueOf( valor ) );

		return result.toString();
	}

	private String transValor( BigDecimal valor, int tam ) {
		
		StringBuffer result = new StringBuffer();
		if ( valor != null ) {
			result.append( adicEspacos( String.valueOf( valor ).replace( ".", "," ), tam, ESQUERDA ) );
		}
		return result.toString();
	}

	private String adicEspacos( String valor, int tam, int posicao ) {
		
		StringBuffer result = new StringBuffer();
		if ( valor == null ) {
			result.append( replicate( " ", tam ) );
		}
		else if ( valor.length() > tam ) {
			if ( posicao == DIREITA ) {
				result.append( valor.substring( 0, tam ) );
			}
			else {
				result.append( valor.substring( valor.length() - tam + 1 ) );
			}
		}
		else {
			result.append( valor );
			result.append( replicate( " ", tam - valor.length() ) );
		}
		return result.toString();
	}

	private String replicate( String valor, int qtd ) {
		
		StringBuffer result = new StringBuffer();
		if ( valor != null ) {
			for ( int i = 0; i < qtd; i++ ) {
				result.append( valor );
			}
		}
		return result.toString();
	}
}
