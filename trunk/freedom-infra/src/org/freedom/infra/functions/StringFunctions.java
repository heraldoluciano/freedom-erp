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


public final class StringFunctions {

	public static String ltrim( String text) {
		if( text==null || "".equals(text)) {
			return "";
		}
		
		while (text.charAt(0) == ' ') { 
			text = text.substring(1);
		}
		return text;
		
	}
	
	public static String alltrim(String text) {
		
		if( text==null || "".equals(text)) {
			return "";
		}
		
		text = ltrim(text.trim());
		
		return text;
		
	}
	
	public static String clearString(String str) {
		
		String sResult = "";
		String sCaracs = "=<>- .,;/\\";
		
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (sCaracs.indexOf(str.substring(i, i + 1)) == -1)
					sResult = sResult + str.substring(i, i + 1);
			}
		}
		return sResult;
	}

	

}

