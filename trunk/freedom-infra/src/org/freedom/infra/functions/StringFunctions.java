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
 * Classe de funções para tratamento de texto.
 */

package org.freedom.infra.functions;

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class StringFunctions {

	public static String ltrim(String text) {
		if (text == null || "".equals(text)) {
			return "";
		}

		while (text.charAt(0) == ' ') {
			text = text.substring(1);
		}
		return text;

	}

	public static String alltrim(String text) {

		if (text == null || "".equals(text)) {
			return "";
		}

		text = ltrim(text.trim());

		return text;

	}

	public static String sqlDateToStrDate(java.sql.Date d) {

		String ret = "";
		GregorianCalendar cal = new GregorianCalendar();

		int iDia = cal.get(Calendar.DAY_OF_MONTH);
		int iMes = cal.get(Calendar.MONTH) + 1;
		int iAno = cal.get(Calendar.YEAR);

		if (d == null) {
			return ret;
		}

		try {

			cal.setTime(d);

			iDia = cal.get(Calendar.DAY_OF_MONTH);
			iMes = cal.get(Calendar.MONTH) + 1;
			iAno = cal.get(Calendar.YEAR);

			ret = StringFunctions.strZero("" + iDia, 2) + "/" + StringFunctions.strZero("" + iMes, 2) + "/" + iAno;

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

	public static String clearString(String str) {
		return clearString(str, "");
	}

	public static String clearStringOld(String str) {

		String sResult = "";
		String sCaracs = "=<>-.,;/\\?";

		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (sCaracs.indexOf(str.substring(i, i + 1)) == -1)
					sResult = sResult + str.substring(i, i + 1);
			}
		}
		return sResult;
	}

	public static String clearString(String str, String strnew) {

		if (str == null) {
			return "";
		}

		StringBuilder validstring = new StringBuilder();

		validstring.append(str.replaceAll("\\W", strnew));

		return validstring.toString().trim();

	}

	public static String replicate(String text, int times) {

		StringBuffer ret = new StringBuffer("");

		try {
			for (int i = 0; i < times; i++) {
				ret.append(text);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ret.toString();

	}

	public static String strZero(String text, int times) {

		String ret = null;

		try {
			if (text.length() > times) {
				return text.substring(0, times);
			}
			if (text == null) {
				ret = replicate("0", times);
			}
			else {
				ret = replicate("0", times - text.trim().length());
				ret += text.trim();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

	public static char clearAccent(char cKey) {

		char cTmp = cKey;

		if (isContained(cTmp, "ãâáàä"))
			cTmp = 'a';
		else if (isContained(cTmp, "ÃÂÁÀÄ"))
			cTmp = 'A';
		else if (isContained(cTmp, "êéèë"))
			cTmp = 'e';
		else if (isContained(cTmp, "ÊÉÈË"))
			cTmp = 'E';
		else if (isContained(cTmp, "îíìï"))
			cTmp = 'i';
		else if (isContained(cTmp, "ÎÍÌÏ"))
			cTmp = 'I';
		else if (isContained(cTmp, "õôóòö"))
			cTmp = 'o';
		else if (isContained(cTmp, "ÕÔÓÒÖ"))
			cTmp = 'O';
		else if (isContained(cTmp, "ûúùü"))
			cTmp = 'u';
		else if (isContained(cTmp, "ÛÚÙÜ"))
			cTmp = 'U';
		else if (isContained(cTmp, "ç"))
			cTmp = 'c';
		else if (isContained(cTmp, "Ç"))
			cTmp = 'C';
		return cTmp;
	}

	public static boolean isContained(char cTexto, String sTexto) {
		boolean bRetorno = false;
		for (int i = 0; i < sTexto.length(); i++) {
			if (cTexto == sTexto.charAt(i)) {
				bRetorno = true;
				break;
			}
		}
		return bRetorno;
	}

	public static String clearAccents(String sTexto) {
		String sRet = "";
		char cVals[] = sTexto.toCharArray();
		for (int i = 0; i < cVals.length; i++) {
			cVals[i] = clearAccent(cVals[i]);
		}
		sRet = new String(cVals);
		return sRet;
	}

}
