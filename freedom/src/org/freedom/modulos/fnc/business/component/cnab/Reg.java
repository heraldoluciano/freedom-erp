package org.freedom.modulos.fnc.business.component.cnab;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.business.exceptions.ExceptionCnab;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.fnc.library.business.compoent.FbnUtil.ETipo;


public abstract class Reg {
	
	public static final String CNAB_240 = "240";
	public static final String CNAB_400 = "400";

	protected final String LITERAL_REM = "REMESSA";
	protected final String LITERAL_SERV = "COBRANCA";
	protected final String LITERAL_SISTEMA = "MX";

	protected final String DATA_06 = "DDMMAA";
	protected final String DATA_08 = "DDMMAAAA";
	protected final String DATA_08_AAAAMMDD = "AAAAMMDD";

	public abstract void parseLine( String line ) throws ExceptionCnab;
	public abstract String getLine( String padraocnab ) throws ExceptionCnab;
	
	public String format( Object obj, ETipo tipo, int tam, int dec) {
		return format( obj, tipo, tam, dec, false, false);
	}

	public String format( Object obj, ETipo tipo, int tam, int dec, boolean maiusculo, boolean tiraacento ) {

		String retorno = null;
		String str = null;

		if ( obj == null ) {
			str = "";
		} else {
			str = obj.toString();
			if (maiusculo) {
				str = str.toUpperCase();
			}
			if (tiraacento) { 
				str = tiratodoacento(str);
			}
		}

		if ( tipo == ETipo.$9 ) {

			if ( dec > 0 ) {
				retorno = Funcoes.transValor( str, tam, dec, true );
			} else {
				retorno = StringFunctions.strZero( str, tam );
			}
		} else {
			retorno = Funcoes.adicionaEspacos( str, tam );
		}

		return retorno;
	}

	public static String tiratodoacento(String texto) {
		StringBuilder result = new StringBuilder();
 		for (int i=0; i<texto.length(); i++) {
 			char str = texto.charAt( i );
			result.append( tiraacento((char) str) );
		}
		return result.toString();
	}
	
	public static char tiraacento(char cKey) {

		char cTmp = cKey;

		if (contido(cTmp, "ãâáà"))
			cTmp = 'a';
		else if (contido(cTmp, "ÃÂÁÀ"))
			cTmp = 'A';
		else if (contido(cTmp, "êéè"))
			cTmp = 'e';
		else if (contido(cTmp, "ÊÉÈ"))
			cTmp = 'E';
		else if (contido(cTmp, "îíì"))
			cTmp = 'i';
		else if (contido(cTmp, "ÎÍÌ"))
			cTmp = 'I';
		else if (contido(cTmp, "õôóò"))
			cTmp = 'o';
		else if (contido(cTmp, "ÕÔÓÒ"))
			cTmp = 'O';
		else if (contido(cTmp, "ûúù"))
			cTmp = 'u';
		else if (contido(cTmp, "ÛÚÙ"))
			cTmp = 'U';
		else if (contido(cTmp, "ç"))
			cTmp = 'c';
		else if (contido(cTmp, "Ç"))
			cTmp = 'C';

		return cTmp;
	}
	
	public static boolean contido(char cTexto, String sTexto) {
		boolean result = false;
		for (int i = 0; i < sTexto.length(); i++) {
			if (cTexto == sTexto.charAt(i)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
