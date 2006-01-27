/**
 * @version 27/01/2006 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)PVNCACongelados.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Layout de pedido de venda para empresa NCA Congelados Ltda.
 */

package org.freedom.layout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class PVNCACongelados extends Leiaute {
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
		boolean bRetorno;
		String[] sVal;
		String sHora = "";
	    Calendar cHora = Calendar.getInstance();
	    
		try {
	        imp.limpaPags();
			imp.setTitulo("Pedido de Venda.");
			imp.montaCab();
			sVal = imp.getValCab();
			
			sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"
						+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"
							+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
			
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",133) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, sVal[0]);//Razão social
					imp.say(imp.pRow() + 0, 122, "PÁG.: " + Funcoes.strZero("" + (imp.getNumPags()), 5));//pagina
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Fone: " + Funcoes.setMascara(sVal[1],"####-####"));//fone
					imp.say(imp.pRow() + 0, 25, "site: " + sVal[4].trim());//site
					imp.say(imp.pRow() + 0, 60, "e-mail: " + sVal[3].trim());//e-mail
					imp.say(imp.pRow() + 0, 118, "ID.USU.: " + Aplicativo.strUsuario.toUpperCase());//usuario
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",133) + "+");	
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Pedido: " + rs.getString("DOCVENDA"));
					imp.say(imp.pRow() + 0, 22, "Cliente: " + (rs.getString("RAZCLI") !=null ? rs.getString("RAZCLI").trim() : ""));
					imp.say(imp.pRow() + 0, 102, "Data: " + Funcoes.sqlDateToStrDate(rs.getDate("DTEMITVENDA")));
					imp.say(imp.pRow() + 0, 121, "Hora: " + sHora);
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Endereço: " + (rs.getString("ENDCLI")!= null ? (rs.getString("ENDCLI").trim() +
																							 (rs.getString("NUMCLI")!= null ? " , " + rs.getString("NUMCLI").trim() : "") + 
																							 (rs.getString("CIDCLI")!= null ? " / " + rs.getString("CIDCLI").trim() : "") + 
																							 (rs.getString("UFCLI")!= null ? " - " + rs.getString("UFCLI").trim() : "")) : ""));
					imp.say(imp.pRow() + 0, 102, "Fone / Fax: " + (rs.getString("FONECLI") != null ? Funcoes.setMascara(rs.getString("FONECLI").trim(),"####-####") : "") + " / " +
																 (rs.getString("FAXCLI") != null ? Funcoes.setMascara(rs.getString("FAXCLI").trim(),"####-####") : ""));
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",133) + "+");	
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "  QTD   |");
					imp.say(imp.pRow() + 0, 14, " UN  |");
					imp.say(imp.pRow() + 0, 21, "  Produto / Serviço");
					imp.say(imp.pRow() + 0, 100, "Cod  |");
					imp.say(imp.pRow() + 0, 107, "P. unit.   |");
					imp.say(imp.pRow() + 0, 125, "Total");
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",133) + "+");	
				}
			}
			imp.fechaGravacao();
			bRetorno = true;
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
			bRetorno = false;
		}
		return bRetorno;
	}
}

