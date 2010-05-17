/**
 * @version 01/03/2010 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez
 *         <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.funcoes <BR>
 * Classe:
 * @(#)Funcoes.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Classe de funções de tratamento de texto.
 */

package org.freedom.infra.functions;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public final class ConversionFunctions {

	public static BigDecimal stringToBigDecimal(Object vlr) {
		BigDecimal retorno = null;
		if (vlr==null) {
			retorno = new BigDecimal(0);
		}
		else {
			retorno = stringCurrencyToBigDecimal( vlr.toString() );
		}
		return retorno;
	}

	public static BigDecimal stringCurrencyToBigDecimal( String strvalue ) {
		
		BigDecimal retvalue = new BigDecimal("0");
		
		try {
		
			if (strvalue == null) {
				return new BigDecimal("0");
			}
			
			int pospoint = strvalue.indexOf('.');
			
			if (pospoint > -1) {
				strvalue  = strvalue.substring(0, pospoint) + strvalue.substring(pospoint + 1);
			}
			
			char[] charvalue = strvalue.toCharArray();
			
			int iPos = strvalue.indexOf(",");
			
			if (iPos >= 0) {
				charvalue[iPos] = '.';
			}
			
			strvalue  = new String( charvalue );
		
			retvalue = new BigDecimal( strvalue.trim() );
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return retvalue;
	}
	
	public static Date strDate6digToDate( String strdate ) {
		
		GregorianCalendar cal = new GregorianCalendar();
		
		if ( strdate .trim().length() == 8 ) {
		
			strdate  = strdate .trim();
			
			try {
				
				int day = Integer.parseInt(strdate .substring(0, 2));
				
				int mounth = Integer.parseInt(strdate .substring(3, 5)) - 1;
				
				int year = Integer.parseInt(strdate .substring(6));
				
				cal = (GregorianCalendar) GregorianCalendar.getInstance();
				
				String milenio = ( cal.get(Calendar.YEAR) + "" ).substring( 0, 2 ) ;
				
				year = Integer.parseInt( milenio + year );
				
				cal = new GregorianCalendar(year, mounth, day);
				
			} 
			catch (Exception err) {
				err.printStackTrace();
				cal = null;
			}
			
		} 
		else {
			cal = null;
		}
		if (cal == null) {
			return null;
		}
		
		return cal.getTime();
		
	}
	
	public static Time strTimetoTime(String strtime) {
		
		Time time = null;
		
		try {

			strtime = StringFunctions.clearString(strtime);
			
			int hours = Integer.parseInt( strtime.substring(0, 2) );
			int minutes = Integer.parseInt( strtime.substring(2,4));
			int seconds = 0;
			
			if( strtime.length() > 4 ) {
			
				seconds = Integer.parseInt( strtime.substring(4));
				
			}
			
			Calendar cal = new GregorianCalendar();
			cal = Calendar.getInstance();
			
			cal.set( Calendar.HOUR_OF_DAY, hours );
			cal.set( Calendar.MINUTE, minutes );
			cal.set( Calendar.SECOND, seconds );
			
			time = new Time(cal.getTimeInMillis());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return time;		
		
	}
	

}

