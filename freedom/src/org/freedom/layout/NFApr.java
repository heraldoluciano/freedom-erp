/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFApr.java <BR>
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
 * Layout da nota fiscal para a empresa Associação Paranaense de Reabilitação.
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class NFApr extends Leiaute {
	
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
				
		boolean bRetorno;
		int iNumNota = 0;
		int iItImp = 0;
		int iNumPares = 0;
		int iServAtual = 0;
		int iRow = 0;
		int iServ = 0;
		int iLinPag = imp.verifLinPag("NF");		
		boolean bFat = true;
		boolean bNat = true;
		String sTipoTran = "";
		String sNumNota = ""; 
		String[] sNat = new String[2];
		String[] sVencs = new String[5];
		String[] sVals = new String[5];
		String[] sObs = null;
		String sDesc = "";
		String sHora = null;
		Calendar cHora = Calendar.getInstance();
		Vector vServ = new Vector();
		Vector vDesc = null;
		Vector vDescTemp = null;
		BigDecimal bigSomaServ = new BigDecimal(0);
		BigDecimal bigSomaProd = new BigDecimal(0);
		
		try {
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
	
			for (int i=0; i<5; i++) {
				if (bFat) {
					if (rsRec.next()) {
						sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
						sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
					} else {
						bFat = false;
						sVencs[i] = "";
						sVals[i] = "";
					}
				} else {
					bFat = false;
					sVencs[i] = "";
					sVals[i] = "";
				}
			}
			
			imp.limpaPags();
			
			while (rs.next()) {
				
				if (bNat) {
					
					sNat[0] = rs.getString("DescNat");
					sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.##");
					iNumNota = rs.getInt("DocVenda");
					if (iNumNota == 0)
						sNumNota = "0000000000";
					else
						sNumNota = Funcoes.strZero(""+iNumNota,10);
					bNat = false;
					
				}
				
				if (imp.pRow()==0) {
					
					imp.pulaLinha(2, imp.comprimido());
					imp.say(124, sNumNota);
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 90, "X");
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 24, Funcoes.copy(rs.getString("EndFilial"),0,30).trim()+", "+rs.getString("NumFilial"));
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 24, Funcoes.copy(rs.getString("CidFilial"),0,15).trim());
					imp.say( 39, rs.getString("UfFilial"));
					imp.say( 43, "Bairro: " + Funcoes.copy(rs.getString("BairFilial"), 0,25).trim());
					imp.say( 70, "Cep: " +  Funcoes.setMascara(rs.getString("CepFilial"),"#####-###"));
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 24, "Emitente: " + Aplicativo.strUsuario);
					imp.say( 92, Funcoes.setMascara(rs.getString("Cnpjfilial"),"##.###.###/####-##"));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, sNat[0]);
					imp.say( 47, sNat[1]);
					imp.say( 93, rs.getString("Inscfilial"));
					imp.pulaLinha(3, imp.comprimido());
					imp.say(  2, rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
					imp.say( 92, rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
					imp.say(124, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
					imp.say( 80, Funcoes.copy(rs.getString("BairCli"),0,25));
					imp.say(105, Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
					imp.say(124, Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, rs.getString("CidCli"));
					imp.say( 67, (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : ""));
					imp.say( 83, rs.getString("UfCli"));
					imp.say( 93, rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
					imp.say(124, sHora);
					imp.pulaLinha(2, imp.comprimido());
					
				}
				
				if (!rs.getString("TipoProd").equals("S")) {
					
					imp.pulaLinha(1, imp.comprimido());
					imp.say(  2, Funcoes.copy(rs.getString("RefProd"),9));
					imp.say( 11, Funcoes.copy(rs.getString("DescProd"),0,45));
					imp.say( 57, Funcoes.copy(rs.getString("CodBarProd"),0,9)); 
					imp.say( 73, Funcoes.copy(rs.getString("OrigFisc"),0,1) + Funcoes.copy(rs.getString("CodTratTrib"),0,2));
					imp.say( 79, rs.getString("CodUnid").substring(0,4));
					imp.say( 85, String.valueOf(rs.getDouble("QtdItVenda")));
					imp.say( 89, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)))));
					imp.say(104, Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
					imp.say(119, String.valueOf(rs.getDouble("PercICMSItVenda")));
					imp.say(124, String.valueOf(rs.getDouble("PercIPIItVenda")));
										   
					bigSomaProd = bigSomaProd.add(((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(rs.getDouble("QtdItVenda"))));
				
				} else {
					vDesc = Funcoes.strToVectorSilabas(rs.getString("ObsItVenda")==null || rs.getString("ObsItVenda").equals("") ? (rs.getString("DescProd").trim()):rs.getString("ObsItVenda"),70);
					vServ.addElement(new Object[] {vDesc,new BigDecimal(rs.getDouble("VlrLiqItVenda"))});
				} 	
				  
				iItImp++;
				if ((iItImp == rs.getInt(1)) || (imp.pRow() == 32)) {
					
					iRow = imp.pRow();
					
					imp.pulaLinha(34 - iRow, imp.comprimido());

					bigSomaServ = new BigDecimal(0);
					
					iServ = 0;
					
					while (iServ < 3) {
						
						if (iServAtual<vServ.size()) {
							
							vDescTemp = (Vector)((Object[])vServ.elementAt(iServAtual))[0];
							
							for (int j=0; j < vDescTemp.size() && iServ < 3; j++){
								if (!vDescTemp.elementAt(j).toString().equals(""))
									sDesc = vDescTemp.elementAt(j).toString();
								else
									sDesc = "";
								
								imp.say(  4, Funcoes.copy(sDesc,80));
								imp.pulaLinha(1, imp.comprimido());
								iServ++;
							}	
							
							bigSomaServ = bigSomaServ.add((BigDecimal)((Object[])vServ.elementAt(iServAtual))[1]);							
							iServAtual++;
							
						} else {
							imp.pulaLinha(1, imp.comprimido());
							iServ++;
						}
						
					}
					
					if (iServ==0)
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,"0.0"));
					else if (iServ>=3)
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(bigSomaServ.setScale(2,BigDecimal.ROUND_HALF_UP))));
					
					if (iItImp == rs.getInt(1)) {

						imp.pulaLinha(3, imp.comprimido());
						imp.say(  1, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
						imp.say( 32, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(bigSomaProd)));
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  2, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
						imp.say( 62, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
						imp.say( 87, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
						iItImp = 0;
						
					} 
					else if (iRow == 32) {
						
						imp.pulaLinha(3, imp.comprimido());
						imp.say(  2, "***************");
						imp.say( 32, "***************");
						imp.say(116, "***************");
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  2, "***************");
						imp.say( 62, "***************");
						imp.say( 87, "***************");
						imp.say(116, "***************");
						
					}
					
					imp.pulaLinha(3, imp.comprimido());
					imp.say(  2, rs.getString("RazTran"));
					imp.say( 86, rs.getString("TipoFreteVD").equals("C") ? "1" : "2");
					imp.say( 93, rs.getString("PlacaFreteVD"));
					imp.say(108, rs.getString("UfFreteVD"));
					   
					sTipoTran = rs.getString("TipoTran");					    
					
					if ( sTipoTran.equals("C") )
						imp.say(115, Funcoes.setMascara(rs.getString("CnpjCli") != null ? rs.getString("CnpjCli") : "","##.###.###/####-##"));
					else
						imp.say(115, Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##")); 

					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
					imp.say( 76, rs.getString("CidTran"));
					imp.say(108, rs.getString("UfTran"));
					   
					if (sTipoTran.equals("C") )
						imp.say(115, rs.getString("InscCli"));
					else 
						imp.say(115, rs.getString("InscTran"));
					   
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, rs.getString("QtdFreteVD"));
					imp.say( 22, rs.getString("EspFreteVD"));
					imp.say( 58, rs.getString("MarcaFreteVD"));
					imp.say(100, rs.getString("PesoBrutVD"));
					imp.say(124, rs.getString("PesoLiqVD"));
					imp.pulaLinha(1, imp.comprimido());
					//imp.say(  2, "ENTREGA PREVISTA : "+Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.pulaLinha(0, imp.comprimido());
					//imp.say(  2, "VENDEDOR : "+rs.getString("NomeVend"));
					imp.pulaLinha(0, imp.comprimido());
					//imp.say(  2, "FORMA DE PAGAMENTO : "+rs.getString("DescPlanoPag"));
					imp.pulaLinha(1, imp.comprimido());
					imp.say(  2, "PEDIDO No.: "+Funcoes.strZero(""+rs.getInt("CodVenda"),8));
					
					if (iNumPares>0)
						imp.say(  2, "Numero de Pares: " + iNumPares);

					sObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",6);
					imp.pulaLinha(1, imp.comprimido());
					//imp.say(2, sObs[0].trim().length() > 58 ? sObs[0].substring( 0,58 )? Funcoes.mensagemErro(null,"Digite apenas 58 caracteres por linha no campo observações!"+err.getMessage()) : sObs[0].trim()));
					
					for (int i=0;i<6;i++) {
						if (sObs[i].trim().length() > 58) {
							Funcoes.mensagemErro(null," O campo de observação comporta apenas 6 linhas e 50 caracteres por linha! Favor revisar as observações!");
							break;
						}
						else
							imp.say(2, sObs[i]); 
						imp.pulaLinha(1, imp.comprimido());
					}
					imp.pulaLinha(3, imp.comprimido());
							
/*					imp.pulaLinha(1, imp.comprimido());
					imp.say( 2, sObs[1].trim().length() > 58 ? sObs[1].substring(0,58) : sObs[1].trim());
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 2, sObs[2].trim().length() > 58 ? sObs[1].substring(0,58) : sObs[2].trim());
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 2, sObs[3].trim().length() > 58 ? sObs[1].substring(0,58) : sObs[3].trim());
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 2, sObs[4].trim().length() > 58 ? sObs[1].substring(0,58) : sObs[4].trim());
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 2, sObs[5].trim().length() > 58 ? sObs[1].substring(0,58) : sObs[5].trim());
					imp.pulaLinha(3, imp.comprimido()); */
					imp.say(124, sNumNota);

					imp.pulaLinha(5, imp.comprimido());
					//imp.pulaLinha(iLinPag + 1, imp.comprimido());

					imp.setPrc(0,0);
					imp.incPags();
				}
			}
			imp.fechaGravacao();
			bRetorno = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
			bRetorno = false;
		} finally {
			sTipoTran = null;
			sNumNota = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			sObs = null;
			sDesc = null;
			sHora = null;
			cHora = null;
			vServ = null;
			vDesc = null;
			vDescTemp = null;
			bigSomaServ = null;
			bigSomaProd = null;
			System.gc();
		}
		
		return bRetorno;
		
	}
}