package org.freedom.funcoes;

import java.math.BigDecimal;


public class Boleto {
	
	private static String codBar = "";
	
	public static String geraCodBar(final String codbanco,  final String codmoeda,
			final String dvbanco, final Long fatvenc, final BigDecimal vlrtitulo, 
			final String convenio, final Long rec, final Short nparc, 
			final String agencia, final String conta, final String carteira  ){
		
		final StringBuffer barcode = new StringBuffer();
		final StringBuffer parte1 = new StringBuffer();
		final StringBuffer parte2 = new StringBuffer();
		
		parte1.append( strZero(codbanco,3) );
		parte1.append( strZero(codmoeda,1) );
		parte2.append( strZero(dvbanco,1) );
		parte2.append( strZero(fatvenc.toString(),4) );
		parte2.append( transValor( vlrtitulo, 10, 2, true ) );
		parte2.append(  geraNossoNumero(convenio, rec, nparc) );
		parte2.append( strZero( getCodSig( agencia )[0], 4));
		parte2.append( strZero( getCodSig( conta )[0], 8));
		parte2.append( strZero( carteira, 2));
		
		barcode.append(parte1);
		barcode.append( digVerif( parte1.toString() + parte2.toString() ));
		barcode.append(parte2);
		return barcode.toString();
	}

	public static String geraNossoNumero( final String convenio, 
			final Long rec, final Short nparc ) {
		final StringBuffer retorno = new StringBuffer();
		if ( convenio==null ) {
			retorno.append( strZero( "0", 4 ) );
			retorno.append( getNumCli(rec, nparc, 7) );
		} else if (convenio.length()<=4) {
			retorno.append( strZero( convenio, 4 ));
			retorno.append( getNumCli(rec, nparc, 7) );
		} else {
			retorno.append( strZero( convenio, 6 ));
			retorno.append( getNumCli(rec, nparc, 7) );
		}
		retorno.append( digVerif(retorno.toString()) );
			
		return retorno.toString();
	}

	public static String[] getCodSig(String codigo) {
		final String[] retorno = new String[2];
		final StringBuffer buffer = new StringBuffer();
		final String valido = "0123456789";
		for (int i=0; i<codigo.length(); i++) {
			if (valido.indexOf( codigo.charAt( i ) )>-1) {
				buffer.append(  codigo.charAt( i ) );
			}
			else if (codigo.charAt( i ) == '-') {
				retorno[0] = buffer.toString();
				buffer.delete( 0, buffer.length() );
			}
		}
		if (retorno[0]==null) {
			retorno[0]=buffer.toString();
			retorno[1]="";
		} else {
			retorno[1]=buffer.toString();
		}
		return retorno;
	}
	
	public static String getNumCli(Long rec, Short nparc, int tam) {
		final StringBuffer retorno = new StringBuffer();
		if (rec==null) {
			retorno.append( strZero("0", tam-2) );
		} else {
			retorno.append( strZero(rec.toString(), tam-2));
		}
		if (nparc==null) {
			retorno.append( "00" );
		} else {
			retorno.append( strZero(nparc.toString(), 2) );
		}
		return retorno.toString();
	}
	
	public static String digVerif(final String codigo){
		final int[] peso = {2, 3, 4, 5, 6, 7, 8, 9};
		int soma = 0;
		int posi = 8;
		int resto = 0;
		String dig = null;
		
	    for (int i=codigo.length()-1; i>-1; i--) {
	    	soma +=  ( Integer.parseInt( codigo.substring( i, i+1 ) ) * (peso[posi]) );
	    	posi--;
	    	if (posi<0) {
	    		posi = 8; 
	    	}
	    }
		resto = soma % 11;
		dig = String.valueOf( 11-resto );
		return dig;
	}
	
	public static String strZero(String val, int zeros) {
		if (val == null)
			return val;
		String sRetorno = replicate("0", zeros - val.trim().length());
		sRetorno += val.trim();
		return sRetorno;
	}

	public static String replicate(String texto, int Quant) {
		StringBuffer sRetorno = new StringBuffer();
		sRetorno.append("");
		for (int i = 0; i < Quant; i++) {
			sRetorno.append(texto);
		}
		return sRetorno.toString();
	}

	public static String transValor(final BigDecimal valor, int tam, int dec,
			boolean zerosEsq) {
		final String vlrcalc;
		String vlrdec = "";
		if (valor == null) {
			vlrcalc = new BigDecimal("0").toString();
		}
		else {
			vlrcalc = valor.toString();
		}
		String retorno = vlrcalc;
		for (int i = 0; i < vlrcalc.length(); i++) {
			if ((vlrcalc.substring(i, i + 1).equals("."))
					|| (vlrcalc.substring(i, i + 1).equals(","))) {
				retorno = vlrcalc.substring(0, i);
				vlrdec = vlrcalc.substring(i + 1, vlrcalc.length());
				if (vlrdec.length() < dec) {
					vlrdec += replicate("0", dec - vlrdec.length());
				} else if (vlrdec.length() > dec) {
					vlrdec = vlrdec.substring(0, dec);
				}
				break;
			}
		}
		if ((vlrdec.trim().equals("")) & (dec > 0)) {
			vlrdec = replicate("0", dec);
		}
		if (retorno.length() > (tam - dec)) {
			retorno = retorno.substring(retorno.length() - (tam - dec) - 1,
					(tam - dec));
		}
		if (zerosEsq) {
			if (retorno.length() < (tam - dec))
				retorno = replicate("0", (tam - dec) - retorno.length())
						+ retorno;
		}
		return retorno + dec;
	}
	
}
