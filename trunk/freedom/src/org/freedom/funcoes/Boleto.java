package org.freedom.funcoes;

import java.math.BigDecimal;


public class Boleto {
	
	private static String codBar = "";
	
	public static String geraCodBar(final String codbanco,  final String codmoeda,
			final String dvbanco, final Long fatvenc, final BigDecimal vlrtitulo, 
			final String convenio, final Long rec, final Long nparc, 
			final String agencia, final String conta, final String carteira, 
			final String modalidade){
		
		final StringBuffer barcode = new StringBuffer();
		final StringBuffer parte1 = new StringBuffer();
		final StringBuffer parte2 = new StringBuffer();
		
		final String bufCodbanco = strZero(codbanco,3);
		final String bufCodmoeda = strZero(codmoeda,1);
		final String bufFatvenc = strZero(fatvenc.toString(),4);
		final String bufVlrtitulo = geraVlrtitulo(vlrtitulo);
		final String bufConvenio = geraConvenio(convenio);
		final String bufModalidade = strZero( modalidade, 2 );
		final String bufNossoNumero = geraNossoNumero(bufModalidade, bufConvenio, rec, nparc);
		final String bufAgencia = strZero( getCodSig( agencia )[0], 4);
		final String bufConta = strZero( getCodSig( conta )[0], 8);
		final String bufCarteira = strZero( carteira, 2);
		
		parte1.append( bufCodbanco );
		parte1.append( bufCodmoeda );
		//parte2.append( strZero(dvbanco,1) );
		parte2.append( bufFatvenc );
		parte2.append( bufVlrtitulo );
		if ("21".equals(bufModalidade) ) {
			parte2.append( bufConvenio );
			parte2.append( bufNossoNumero );
			parte2.append( bufModalidade );
		} else {
			parte2.append( bufNossoNumero );
			parte2.append( bufAgencia );
			parte2.append( bufConta );
			parte2.append( bufCarteira );
		}
		
		barcode.append(parte1);
		barcode.append( digVerif( parte1.toString() + parte2.toString() ));
		barcode.append(parte2);
		return barcode.toString();
	}

	public static String geraVlrtitulo(final BigDecimal vlrtitulo) {
		String retorno = null;
		retorno = transValor( vlrtitulo, 10, 2, true );
		return retorno;
	}

	public static String geraConvenio( final String convenio ) {
		final StringBuffer retorno = new StringBuffer();
		final String bufConvenio;
		if (convenio==null) {
			bufConvenio = "000000";
		} 
		else if ( convenio.length()>6 ) {
			bufConvenio = convenio.substring( convenio.length()-6 );
		} else {
			bufConvenio = convenio;
		}
		if (bufConvenio.length()<=4) {
			retorno.append( strZero( bufConvenio, 4 ));
		} else {
			retorno.append( strZero( bufConvenio, 6 ));
		}
		return retorno.toString();
	}
	
	public static String geraNossoNumero( final String modalidade, final String convenio, 
			final Long rec, final Long nparc ) {
		final StringBuffer retorno = new StringBuffer();
		if ( "21".equals(modalidade) ) {
			retorno.append( getNumCli(rec, nparc, 17) );
		} else {
			retorno.append(  convenio );
			retorno.append( getNumCli(rec, nparc, 7) );
			retorno.append( digVerif(retorno.toString()) );
		}
			
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
	
	public static String getNumCli(Long rec, Long nparc, int tam) {
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
		int posi = 7;
		int resto = 0;
		String dig = null;
		
	    for (int i=codigo.length()-1; i>-1; i--) {
	    	soma +=  ( Integer.parseInt( codigo.substring( i, i+1 ) ) * (peso[posi]) );
	    	posi--;
	    	if (posi<0) {
	    		posi = 7; 
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
		return retorno + vlrdec;
	}
	
}
