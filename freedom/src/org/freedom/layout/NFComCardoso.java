/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR> 
 * Classe: @(#)NFComCardoso.java <BR>
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
 * Layout da nota fiscal para a empresa Comercial Cardoso.
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;

public class NFComCardoso extends Leiaute {
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ResultSet rsInfoAdic,ImprimeOS imp) {
    GregorianCalendar cHora = new GregorianCalendar();
    boolean bRetorno;
    int iNumNota = 0;
    int iItImp = 0;
    int iLinPag = imp.verifLinPag("NF");
    String sTipoTran="" ;
    boolean bFat = true;
    String[] sValsCli = new String[4];
    String[] sNat = new String[2];
    String[] sVencs = new String[5];
    String[] sVals = new String[4];
    String sObs = "";
	String[] sMatObs = null;
	String sImpDtSaidaNat = "";
	float ftVlrDesc = 0;
	int iContaMens = 1;	
	String sIncra = "" ;
	Vector vMens = new Vector();
	vMens.clear();
	
	String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2);
    try {
      for (int i=0; i<3; i++) {
        if (bFat) {
          if (rsRec.next()) {
            sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
            sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
          }
          else {
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
      imp.limpaPags();
       boolean bNat = true;
      while (rs.next()) {
           if (bNat) {
             sNat[0] = rs.getString("DescNat");
             sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.###");
             iNumNota = rs.getInt("DocVenda");
             bNat = false;
             
           }
	      if (rsInfoAdic.next()) {
	      	 sValsCli[0] = rsInfoAdic.getString("CpfCliAuxV")  != null ? rsInfoAdic.getString("CpfCliAuxV") : rs.getString("CpfCli");
	      	 sValsCli[1] = rsInfoAdic.getString("NomeCliAuxV")  != null ? rsInfoAdic.getString("NomeCliAuxV") : rs.getString("RazCli");
	      	 sValsCli[2] = rsInfoAdic.getString("CidCliAuxV")  != null ? rsInfoAdic.getString("CidCliAuxV") : rs.getString("CidCli");
	      	 sValsCli[3] = rsInfoAdic.getString("UfCliAuxV")  != null ? rsInfoAdic.getString("UfCliAuxV") : rs.getString("UfCli");
	      }
	      else {
	      	 sValsCli[0] = rs.getString("CpfCli");
	      	 sValsCli[1] = rs.getString("RazCli");
	      	 sValsCli[2] = rs.getString("CidCli");
	      	 sValsCli[3] = rs.getString("UfCli");
	      }  
//Cabeçario da nota  
         if (imp.pRow()==0) {           
	       if (bEntrada){
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,103,"X");
	           imp.say(imp.pRow()+0,0,"");
	       }
           else{
           	   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           	   imp.say(imp.pRow()+0,91,"X");
           	   imp.say(imp.pRow()+0,0,"");
           }
	       
	       imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");        
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());      
           
           imp.say(imp.pRow()+0,6,sNat[0].substring(0,42));
           imp.say(imp.pRow()+0,36,sNat[1]);  
           
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());   
           
           imp.say(imp.pRow()+0,4,sValsCli[1]);
           imp.say(imp.pRow()+0,90,sValsCli[0] != null ? Funcoes.setMascara(sValsCli[0],"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));
           imp.say(imp.pRow()+0,124,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda"))); 
           
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,4,Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,66,rs.getString("BairCli"));
           imp.say(imp.pRow()+0,102,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           
           sImpDtSaidaNat = rs.getString("IMPDTSAIDANAT");           
	           if (sImpDtSaidaNat==null) sImpDtSaidaNat = "S";           
	           if (sImpDtSaidaNat.equals("S"))
	              imp.say(imp.pRow()+0,124,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));

           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,4,sValsCli[2] != null ? sValsCli[2] : "");
           imp.say(imp.pRow()+0,53,Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####"));
           imp.say(imp.pRow()+0,83,sValsCli[3] != null ? sValsCli[3] : "");
           imp.say(imp.pRow()+0,90,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           imp.say(imp.pRow()+0,127,sHora);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
                                 
         }
//Descrições adicionais colocadas junto a decrição do produto.
         
         String sDescAdic = ""; 
         String sTmp = rs.getString(5) != null ? rs.getString(5).trim() : ""; 
		 sTmp = rs.getString(4) != null ? rs.getString(4).trim() : "";
		 String sClasFisc = Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2);
		 if (sTmp.length() > 0) {
		 	int iLinha;
		 	for (iLinha=0;iLinha<vMens.size();iLinha++) {
		 		if (((String[])vMens.elementAt(iLinha))[0].equals(sClasFisc))
		 			break;
		 	}
		 	if (iLinha==vMens.size()) {
		 		vMens.add(
		 				new String[] {
		 						sClasFisc,
								sTmp
		 				}
		 		);
		 	}
		 }
		 
		 imp.say(imp.pRow()+1,0,"" + imp.comprimido());
         imp.say(imp.pRow()+0,4,Funcoes.alinhaDir(rs.getInt("CodProd"),8));           
         imp.say(imp.pRow()+0,15,rs.getString("DescProd").trim());
//         imp.say(imp.pRow()+0,64,sClasFisc);
         imp.say(imp.pRow()+0,79,rs.getString("CodUnid").substring(0,4));
         imp.say(imp.pRow()+0,90,""+rs.getDouble("QtdItVenda"));          
         imp.say(imp.pRow()+0,103,Funcoes.strDecimalToStrCurrency(13,2,""+(new BigDecimal(rs.getString("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)).trim());
         imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrProdItVenda")));
//         imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(13,2,""+rs.getString("VlrLiqItVenda").trim()));
         imp.say(imp.pRow()+0,133,""+rs.getDouble("PercICMSItVenda"));  // espaço para alicota ICMS
//         imp.say(imp.pRow()+0,127,""+rs.getDouble("PercIPIItVenda"));   // espaço para alicota IPI
//         imp.say(imp.pRow()+0,134,Funcoes.strDecimalToStrCurrency(15,3,rs.getString("VlrIPIVenda")).trim());
         
         iItImp++;
         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         
//IMPRIME CALCULO DO IMPOSTO
         if ((iItImp == rs.getInt(1)) || (imp.pRow() == 24)) {
           if (iItImp == rs.getInt(1)) {
             int iRow = imp.pRow();
             for (int i=0; i<(24-iRow);i++) {
                 imp.say(imp.pRow()+1,0,"");
             }
             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             
             //imprime desconto
             ftVlrDesc = rs.getFloat("VlrDescItVenda");
             if ( ftVlrDesc > 0 ){ 
	            imp.say(imp.pRow()+0,98,"Total de descontos = "+Funcoes.strDecimalToStrCurrency(15,2,""+ftVlrDesc));
	         }
             else{             	
             	imp.say(imp.pRow()+0,0,""+imp.comprimido());
             }           	
                         
             imp.say(imp.pRow()+1,4,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
             imp.say(imp.pRow()+0,27,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
             imp.say(imp.pRow()+0,57,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
             imp.say(imp.pRow()+0,85,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             iItImp = 0;
			 //sObs += rs.getString("ObsVenda") != null ? rs.getString("ObsVenda").trim()+'\n' : "";
           }
           else if (imp.pRow() == 24) {  
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,6,"***************");
             imp.say(imp.pRow()+0,33,"***************");
             imp.say(imp.pRow()+0,114,"***************");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,"***************");
             imp.say(imp.pRow()+0,57,"***************");
             imp.say(imp.pRow()+0,85,"***************");
             imp.say(imp.pRow()+0,114,"***************");
           }
           
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,4,rs.getString("RazTran"));
           imp.say(imp.pRow()+0,76,rs.getString("TipoFreteVD").equals("C") ? "1" : "2");
           imp.say(imp.pRow()+0,83,rs.getString("PlacaFreteVD"));
           imp.say(imp.pRow()+0,99,rs.getString("UfFreteVD"));
           
		   sTipoTran = rs.getString("TipoTran");
			
			   if (sTipoTran==null) sTipoTran = "T";
		         if ( sTipoTran.equals("C") ){
			        imp.say(imp.pRow()+0,104,Funcoes.setMascara(rs.getString("CnpjCli") != null ? rs.getString("CnpjCli") : "","##.###.###/####-##"));
				  }
			  
			  else {
					 imp.say(imp.pRow()+0,104,Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##")); 
			   	  }            

           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,4,Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
           imp.say(imp.pRow()+0,63,rs.getString("CidTran"));
           imp.say(imp.pRow()+0,100,rs.getString("UfTran"));
  
		   if (rs.getString("TipoTran").compareTo("C") == 0){
			   imp.say(imp.pRow()+0,104,rs.getString("InscCli"));
		   }
		   else { 
			imp.say(imp.pRow()+0,100,rs.getString("InscTran"));
		   }
           
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,4,rs.getString("QtdFreteVD"));
           imp.say(imp.pRow()+0,26,rs.getString("EspFreteVD"));
           imp.say(imp.pRow()+0,57,rs.getString("MarcaFreteVD"));
           imp.say(imp.pRow()+0,100,rs.getString("PesoBrutVD"));
           imp.say(imp.pRow()+0,125,rs.getString("PesoLiqVD"));
           
           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           /*imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,rs.getString("CodVend"));
           imp.say(imp.pRow()+0,20,rs.getString("NomeVend") != null ? rs.getString("CodClComis") : "");*/
                      
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");           
           imp.say(imp.pRow()+3,0,""+imp.normal()+imp.expandido());
           imp.say(imp.pRow()+0,114,rs.getString("DocVenda") != null ? Funcoes.strZero(""+iNumNota,6) : "000000");
                    
           
           /*for(int i=0;i<vMens.size();i++)
            	sObs += ((String[])vMens.elementAt(i))[0] + " - " +((String[])vMens.elementAt(i))[1]+ '\n';
           
           sMatObs = Funcoes.strToStrArray(sObs,5);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sMatObs[0]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sMatObs[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sMatObs[2]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sMatObs[3]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sMatObs[4]);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());*/
           
           System.out.println(imp.pRow()+" =T Lins: "+iLinPag);
           
           for (int i=imp.pRow(); i<=iLinPag; i++) { 
             imp.say(imp.pRow()+1,0,"");
           }
           imp.setPrc(0,0);
           imp.incPags();
         }
      }
      imp.fechaGravacao();
      bRetorno = true;
    }
    catch ( SQLException err ) {
      JOptionPane.showMessageDialog(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
    }
    return bRetorno;
  }
}

