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
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class PVNCACongelados extends Leiaute {
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
		boolean bRetorno;
		boolean bRec = false;
		final int casaDecFin = Aplicativo.casasDecFin;
		final int maxLine = 27;
		int iLinha;
		int contItem = 0;
		int iQtdItem = 0;
		BigDecimal bdTotalItem = new BigDecimal(0);
		String[] sVal;
		String sHora = "";
	    Calendar cHora = Calendar.getInstance();
	    
		try {
			
	        imp.limpaPags();
			imp.setTitulo("Pedido de Venda.");
			sVal = imp.getValCab();
			
			sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"
						+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"
							+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
			
			bRec = rsRec.next();
			
			while (rs.next()) {
				
				if (imp.pRow() == 0) {

					imp.montaCab();
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",133) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, sVal[0]);//Razão social
					imp.say(imp.pRow() + 0, 121, "PÁG.: " + imp.getPagAtual()+1 + " de " + imp.getNumPags());//pagina
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
					imp.say(imp.pRow() + 0, 4, "Pedido: " + rs.getString("CODVENDA"));
					imp.say(imp.pRow() + 0, 22, "Cliente: " + (rs.getString("RAZCLI") !=null ? rs.getString("RAZCLI").trim() + " - " + rs.getInt("CODCLI") : ""));
					imp.say(imp.pRow() + 0, 102, "Data: " + Funcoes.sqlDateToStrDate(rs.getDate("DTEMITVENDA")));
					imp.say(imp.pRow() + 0, 121, "Hora: " + sHora);
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Endereço: " + (rs.getString("ENDCLI") != null ? (rs.getString("ENDCLI").trim() +
																							    (rs.getString("NUMCLI")!= null ? " , " + rs.getString("NUMCLI").trim() : "") + 
																							    (rs.getString("CIDCLI")!= null ? " / " + rs.getString("CIDCLI").trim() : "") + 
																							    (rs.getString("UFCLI") != null ? " - " + rs.getString("UFCLI").trim()  : "")) : ""));
					imp.say(imp.pRow() + 0, 104, "Fone/Fax: " + (rs.getString("FONECLI") != null ? Funcoes.setMascara(rs.getString("FONECLI").trim(),"####-####") : "") + " / " +
																  (rs.getString("FAXCLI")  != null ? Funcoes.setMascara(rs.getString("FAXCLI").trim() ,"####-####") : ""));
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|" + Funcoes.replicate("-",9) + "+");	
					imp.say(imp.pRow() + 0, 12, Funcoes.replicate("-",6) + "+");
					imp.say(imp.pRow() + 0, 19, Funcoes.replicate("-",85) + "+");
					imp.say(imp.pRow() + 0, 105, Funcoes.replicate("-",14) + "+");
					imp.say(imp.pRow() + 0, 120, Funcoes.replicate("-",15) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "  QTD   |");
					imp.say(imp.pRow() + 0, 14, " UN  |");
					imp.say(imp.pRow() + 0, 21, "  Produto / Serviço");
					imp.say(imp.pRow() + 0, 100, "Cod  |");
					imp.say(imp.pRow() + 0, 107, "P. unit.     |");
					imp.say(imp.pRow() + 0, 126, "Total");
					imp.say(imp.pRow() + 0, 135, " |");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|" + Funcoes.replicate("-",9) + "+");	
					imp.say(imp.pRow() + 0, 12, Funcoes.replicate("-",6) + "+");
					imp.say(imp.pRow() + 0, 19, Funcoes.replicate("-",85) + "+");
					imp.say(imp.pRow() + 0, 105, Funcoes.replicate("-",14) + "+");
					imp.say(imp.pRow() + 0, 120, Funcoes.replicate("-",15) + "+");
					
				}

				iQtdItem += rs.getInt("QTDITVENDA");
				bdTotalItem = bdTotalItem.add(new BigDecimal(rs.getFloat("VLRLIQITVENDA")).divide(new BigDecimal(1), casaDecFin, BigDecimal.ROUND_HALF_UP));
				
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|" + (Funcoes.alinhaCentro( rs.getInt("QTDITVENDA"), 8)));
				imp.say(imp.pRow() + 0, 12, "| " + (rs.getString("CODUNID") != null ? rs.getString("CODUNID").substring(0,4) : ""));
				imp.say(imp.pRow() + 0, 19, "| " + (rs.getString("DESCPROD") != null ? rs.getString("DESCPROD").trim() : ""));
				imp.say(imp.pRow() + 0, 96, (Funcoes.alinhaDir( rs.getInt("CODPROD"), 8)));
				imp.say(imp.pRow() + 0, 105, "| " + Funcoes.strDecimalToStrCurrency(12,casaDecFin,(rs.getString("PRECOITVENDA") != null ? rs.getString("PRECOITVENDA").trim() : "")));
				imp.say(imp.pRow() + 0, 120, "| " + Funcoes.strDecimalToStrCurrency(13,casaDecFin,(rs.getString("VLRLIQITVENDA") != null ? rs.getString("VLRLIQITVENDA").trim() : "")));
				imp.say(imp.pRow() + 0, 135, " |");	
				
				contItem++;
				
				if(rs.getInt(1) == contItem || imp.pRow() == maxLine) {
		            iLinha = imp.pRow();
					if(rs.getInt(1) == contItem) {
			            for (int i=0; i<(maxLine-iLinha);i++) {
			            	imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "|" + Funcoes.replicate(" ",9) + "|");	
							imp.say(imp.pRow() + 0, 12, Funcoes.replicate(" ",6) + "|");
							imp.say(imp.pRow() + 0, 19, Funcoes.replicate(" ",85) + "|");
							imp.say(imp.pRow() + 0, 105, Funcoes.replicate(" ",14) + "|");
							imp.say(imp.pRow() + 0, 120, Funcoes.replicate(" ",15) + "|");
			            }
					}

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|" + Funcoes.replicate("-",9) + "+");	
					imp.say(imp.pRow() + 0, 12, Funcoes.replicate("-",6) + "+");
					imp.say(imp.pRow() + 0, 19, Funcoes.replicate("-",34) + "+");
					imp.say(imp.pRow() + 0, 54, Funcoes.replicate("-",50) + "+");
					imp.say(imp.pRow() + 0, 105, Funcoes.replicate("-",14) + "+");
					imp.say(imp.pRow() + 0, 120, Funcoes.replicate("-",15) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|" + (Funcoes.alinhaDir( iQtdItem, 8)));
					imp.say(imp.pRow() + 0, 12, "| ---- |" );
					imp.say(imp.pRow() + 0, 21, "Ass.:");
					imp.say(imp.pRow() + 0, 54, "|Pg.: " + (rs.getString("DESCPLANOPAG") != null ? rs.getString("DESCPLANOPAG").substring(0,28) : ""));
					imp.say(imp.pRow() + 0, 88, "Venc.: " + (bRec ? (rsRec.getString("DTVENCITREC") != null ? Funcoes.dateToStrDate(rsRec.getDate("DTVENCITREC")) : "") : ""));
					if(iLinha == maxLine)
						imp.say(imp.pRow() + 0, 105, "| Total parc.:");
					else 
						imp.say(imp.pRow() + 0, 105, "| Total :");
					imp.say(imp.pRow() + 0, 122, Funcoes.strDecimalToStrCurrency(13,casaDecFin,bdTotalItem.toString()));
					imp.say(imp.pRow() + 0, 135, " |");	
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|" + Funcoes.replicate("-",9) + "+");	
					imp.say(imp.pRow() + 0, 12, Funcoes.replicate("-",6) + "+");
					imp.say(imp.pRow() + 0, 19, Funcoes.replicate("-",34) + "+");
					imp.say(imp.pRow() + 0, 54, Funcoes.replicate("-",50) + "+");
					imp.say(imp.pRow() + 0, 105, Funcoes.replicate("-",30) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "| Obrigado pela prefência.");
					imp.say(imp.pRow() + 0, 54, "|OBS.: " + ((rs.getString("OBSCLI") != null && !rs.getString("OBSCLI").trim().equals("")) ? 
							(rs.getString("OBSCLI").length() > 74 ? rs.getString("OBSCLI").substring(0,74) : rs.getString("OBSCLI")).replaceAll("\n\t", " ") : ""));
					imp.say(imp.pRow() + 0, 136, "|");

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-",51) + "+");	
					imp.say(imp.pRow() + 0, 54, Funcoes.replicate("-",81) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			        imp.setPrc(0,0);
			        imp.incPags();
				}
				
			}
			
			imp.fechaGravacao();
			bRetorno = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());  
			err.printStackTrace();
			bRetorno = false;
		} catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao montar pedido de venda!"+err.getMessage());      
			err.printStackTrace();  
			bRetorno = false;
		} finally {
			bdTotalItem = null;
			sVal = null;
			sHora = null;
		    cHora = null;
		}
		
		return bRetorno;
	}
}

