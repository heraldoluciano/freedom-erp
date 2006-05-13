/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR> 
 * Classe: @(#)NFAmazonDiesel.java <BR>
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
 * Layout da nota fiscal para a empresa AmazonDiesel Peças e Serviços peças.
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;

public class NFAmazonDiesel extends Leiaute {
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ResultSet rsInfoAdic,ImprimeOS imp) {
	
		boolean bRetorno;
		int iNumNota = 0;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		float ftVlrDesc = 0;
		boolean bFat = true;
		boolean bNat = true;
		String sTipoTran= null ;
		String sHora = null;
		String sImpDtSaidaNat = null;
		String sTmp = null;
		String sClasFisc = null;
		String[] sValsCli = new String[4];
		String[] sNat = new String[2];
		String[] sVencs = new String[4];
		String[] sVals = new String[4];
		Vector vMens = new Vector();
		Calendar cHora = Calendar.getInstance();
		
		try {
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2);
			
			imp.limpaPags();
			
			for (int i=0; i<3; i++) {
				if (bFat) {
					if (rsRec.next()) {
						sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
						sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
					} else {
						bFat = false;
						sVencs[i] = "";
						sVals[i] = "";
					}
				}
				else {
					bFat = false;
					sVencs[i] = "";
					sVals[i] = "";
				}
			}
			
						
			while (rs.next()) {				

				sImpDtSaidaNat = rs.getString("IMPDTSAIDANAT"); 
				
				if (bNat) {
					sNat[0] = rs.getString("DescNat");
					sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.###");
					iNumNota = rs.getInt("DocVenda");
					bNat = false;				     
				}
				if (rsInfoAdic.next()) {
					sValsCli[0] = rsInfoAdic.getString("CpfCliAuxV") != null ? rsInfoAdic.getString("CpfCliAuxV") : rs.getString("CpfCli");
					sValsCli[1] = rsInfoAdic.getString("NomeCliAuxV") != null ? rsInfoAdic.getString("NomeCliAuxV") : rs.getString("RazCli");
					sValsCli[2] = rsInfoAdic.getString("CidCliAuxV") != null ? rsInfoAdic.getString("CidCliAuxV") : rs.getString("CidCli");
					sValsCli[3] = rsInfoAdic.getString("UfCliAuxV") != null ? rsInfoAdic.getString("UfCliAuxV") : rs.getString("UfCli");
				} else {
					sValsCli[0] = rs.getString("CpfCli");
					sValsCli[1] = rs.getString("RazCli");
					sValsCli[2] = rs.getString("CidCli");
					sValsCli[3] = rs.getString("UfCli");
				}  
				
				if (imp.pRow()==0) {           
					if (bEntrada){	       	   
						imp.pulaLinha(2, imp.comprimido());
						imp.say(108, "X");
					} else{              	   
						imp.pulaLinha(2, imp.comprimido());
						imp.say( 93, "X");
					}
					
					imp.pulaLinha(5, imp.comprimido());
					imp.say(  4, sNat[0].substring(0,42));
					imp.say( 44, sNat[1]);  					   
					imp.pulaLinha(3, imp.comprimido());
					imp.say(  4, sValsCli[1]);
					imp.say( 92, sValsCli[0] != null ? Funcoes.setMascara(sValsCli[0],"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));
					imp.say(128, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  4, Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
					imp.say( 65, rs.getString("BairCli"));
					imp.say(100, Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
					    
					if (sImpDtSaidaNat.equals("S"))
						imp.say(128, Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					       
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  4, sValsCli[2] != null ? sValsCli[2] : "");
					imp.say( 56, (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : ""));
					imp.say( 85, sValsCli[3] != null ? sValsCli[3] : "");
					imp.say( 96, rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
					imp.say(130, sHora);
					imp.pulaLinha(3, imp.comprimido());
					  
				}
				
				sTmp = rs.getString(5) != null ? rs.getString(5).trim() : "";
				sTmp += rs.getString(4) != null ? rs.getString(4).trim() : "";
				
				sClasFisc = Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2);
				
				if (sTmp.length() > 0) {
					int iLinha;
					for (iLinha=0;iLinha<vMens.size();iLinha++) {
						if (((String[])vMens.elementAt(iLinha))[0].equals(sClasFisc))
							break;
					}
					if (iLinha==vMens.size()) {
						vMens.add( new String[] { sClasFisc,
											      sTmp } );
					}
				}
				
				imp.pulaLinha(1, imp.comprimido());
				imp.say(  0, Funcoes.alinhaDir(rs.getInt("CodProd"),8));           
				imp.say( 11, rs.getString("DescProd").trim());
				imp.say( 74, sClasFisc);
				imp.say( 83, rs.getString("CodUnid").substring(0,4));
				imp.say( 90, String.valueOf(rs.getDouble("QtdItVenda")));          
				imp.say(101, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf((new BigDecimal(rs.getString("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)).trim()));
				imp.say(113, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(rs.getString("VlrLiqItVenda").trim())));
				imp.say(133, String.valueOf(rs.getDouble("PercICMSItVenda")));  // espaço para alicota ICMS
				 
				iItImp++;
				 
				
				if ((iItImp == rs.getInt(1)) || (imp.pRow() == 35)) {         	
					if (iItImp == rs.getInt(1)) {               

						imp.pulaLinha(35 - imp.pRow(), imp.comprimido());
						 
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");					  	
						imp.pulaLinha(1, imp.comprimido());
						 						 
						ftVlrDesc = rs.getFloat("VlrDescItVenda");
						
						if ( ftVlrDesc != 0 )      
							imp.say( 98, "Total de descontos = " + Funcoes.strDecimalToStrCurrency(15,2,String.valueOf(ftVlrDesc)));
										  	
						imp.pulaLinha(2, imp.comprimido());             
						imp.say(  4, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
						imp.say( 27, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));						
						imp.say(114, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  4, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
						imp.say( 57, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
						imp.say( 85, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
						imp.say(114, Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
						iItImp = 0;
						 
					} else if ( imp.pRow() == 35 ) {
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");
						imp.pulaLinha(1, imp.comprimido());
						imp.say( 60, "*");					  	
						imp.pulaLinha(3, imp.comprimido());
						imp.say(  4, "***************");
						imp.say( 27, "***************");
						imp.say(114, "***************");
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  4, "***************");
						imp.say( 57, "***************");
						imp.say( 85, "***************");
						imp.say(114, "***************");
					}
					   
					imp.pulaLinha(3, imp.comprimido());
					imp.say( 82, rs.getString("TipoFreteVD").equals("C") ? "1" : "2");
					imp.pulaLinha(1, imp.comprimido());
					imp.say(  4, rs.getString("RazTran"));           
					imp.say( 90, rs.getString("PlacaFreteVD"));
					imp.say(106, rs.getString("UfFreteVD"));
					   
					sTipoTran = rs.getString("TipoTran") != null ? rs.getString("TipoTran") : "";
					
					if ( sTipoTran.equals("C") )
						imp.say(112, Funcoes.setMascara(rs.getString("CnpjCli") != null ? rs.getString("CnpjCli") : "","##.###.###/####-##"));					 
					else 
						imp.say(112, Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##")); 
					    
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  4, Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
					imp.say( 75, rs.getString("CidTran"));
					imp.say(106, rs.getString("UfTran"));
					  
					if ( sTipoTran.equals("C") )
						imp.say(112, rs.getString("InscCli"));
					else
						imp.say(112, rs.getString("InscTran"));
					   
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, rs.getString("QtdFreteVD"));
					imp.say( 26, rs.getString("EspFreteVD"));
					imp.say( 47, rs.getString("MarcaFreteVD"));
					imp.say(100, rs.getString("PesoBrutVD"));
					imp.say(125, rs.getString("PesoLiqVD"));
					    
					imp.pulaLinha(12, imp.comprimido());
					              
					imp.pulaLinha(1, imp.normal() + imp.comprimido());
					imp.say(112,rs.getString("DocVenda") != null ? Funcoes.strZero(String.valueOf(iNumNota),6) : "000000");
					  					   
					imp.pulaLinha(iLinPag + 1, imp.comprimido());

					imp.setPrc(0,0);
					imp.incPags();
				}
			}
			imp.fechaGravacao();
			bRetorno = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao consultar tabela de Venda!" + err.getMessage() );      
			bRetorno = false;
		} finally {
			sTipoTran = null;
			sHora = null;
			sImpDtSaidaNat = null;
			sTmp = null;
			sClasFisc = null;
			sValsCli = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			vMens = null;
			cHora = null;
			System.gc();
		}
		
		return bRetorno;
		
	}
}