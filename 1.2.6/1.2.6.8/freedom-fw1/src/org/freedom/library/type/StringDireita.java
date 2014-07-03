/**
 * @version 19/08/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)StringDireita.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários da classe.....
 */

package org.freedom.library.type;

import java.math.BigDecimal;

public class StringDireita implements Comparable<String> {
	private String text = "";

	public StringDireita(String text) {
		if (text != null)
			this.text = text.trim();
	}

	public String toString() {
		return text;
	}

	public int compareTo(String arg0) {
		return text.compareTo(arg0.toString());
	}
	
	public BigDecimal getBigDecimal() {
		BigDecimal result = null;
		if (text!=null && !"".equals(text.trim())) {
			String str = text.trim().replace(',', '.');
			int pos = str.lastIndexOf('.');
			if (pos>-1) {
				String str1 = str.substring(0, pos);
				String str2 = str.substring(pos);
				str = str1.replace(".","")+str2;
			} 
			result = new BigDecimal(str);
		}
		return result;
	}
	
}
