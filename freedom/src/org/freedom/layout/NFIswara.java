/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFIswara.java <BR>
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
 * Layout da nota fiscal para a empresa Iswara Ltda.
 */

package org.freedom.layout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
public class NFIswara extends Leiaute {
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
    Calendar cHora = Calendar.getInstance();
    boolean bRetorno;
    int iNumNota = 0;
    String sNumNota = ""; 
    int iItImp = 0;
    int iLinPag = imp.verifLinPag("NF");
	int iContaObs = 0; //Contador para a gamb de asterisco
	int iContaObs2 = 0; //Contador para a gamb de '"'
	String sTipoTran = "";
    boolean bFat = true;
    String[] sNat = new String[2];
    String[] sVencs = new String[3];
    String[] sVals = new String[3];
    String[] sDuplics = new String[3];
	String sMens = "";
	String sFiscAdic = "";
	String[] sMatObs = null;
	String[] sMarcs = {"\"","\"\"","\"\"\"","\"\"\"\""}; //Tipos de Marcs. 
	String[] sMarcs2 = {"*","**","***","****"}; //Tipos de Marcs.
	String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
    try {
      imp.limpaPags();
      boolean bNat = true;
      while (rs.next()) {

         iNumNota = rs.getInt("DocVenda");
         if (iNumNota==0) {
            sNumNota = "000000";
         } 
         else {
            sNumNota = Funcoes.strZero(""+iNumNota,6);
         }

         for (int i=0; i<3; i++) {
           if (bFat) {
             if (rsRec.next()) {
               sDuplics[i] = sNumNota+"/"+rsRec.getInt("NPARCITREC");
               sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
               sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
             }
             else {
               bFat = false;
               sDuplics[i] = "********";
               sVencs[i] = "";
               sVals[i] = "";
             }
           }
           else {
             bFat = false;
             sDuplics[i] = "********";
             sVencs[i] = "";
             sVals[i] = "";
           }
         }


         if (bNat) {
           sNat[0] = rs.getString("DescNat");
           sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.##");
           sMatObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",3);
             
//             System.out.println("num nota: "+iNumNota);
//             System.out.println("rs 2"+rs.getInt(2));
           bNat = false;
         }
         if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		// imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,90,"X");
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
        //   imp.say(imp.pRow()+0,126,sNumNota);
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+3,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,sNat[0]);
           imp.say(imp.pRow()+0,47,sNat[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
           imp.say(imp.pRow()+0,83,rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("EndCli"),0,50).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,78,rs.getString("BairCli")!=null ? Funcoes.copy(rs.getString("BairCli"),0,15) : "");
           imp.say(imp.pRow()+0,100,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("CidCli"));
           imp.say(imp.pRow()+0,67,Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####"));
           imp.say(imp.pRow()+0,84,rs.getString("UfCli"));
           imp.say(imp.pRow()+0,92,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           imp.say(imp.pRow()+0,126,sHora);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+0,5,sNumNota);
		   if (sVencs[1].equals("")) {
		     imp.say(imp.pRow()+0,70,sVals[0]);
             imp.say(imp.pRow()+0,95,sDuplics[0]);
             imp.say(imp.pRow()+0,126,sVencs[0]);
		   }
		   else {
		     imp.say(imp.pRow()+0,65,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
			 imp.say(imp.pRow()+0,90,"VIDE DESDOBRAMENTO");
		   }

		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+0,3,""+Funcoes.strZero(""+rs.getInt("CodVenda"),8));
		   imp.say(imp.pRow()+0,48,rs.getInt("CodCli")+"");
		   imp.say(imp.pRow()+0,62,rs.getString(6) != null ? Funcoes.copy(rs.getString(6),0,20) : "");
	
           imp.say(imp.pRow()+0,96,rs.getString("CodVend"));
		   imp.say(imp.pRow()+0,118,rs.getString("CodBanco") != null ? Funcoes.copy(rs.getString("CodBanco")+"-",0,5) : "");
           
		   imp.say(imp.pRow()+0,124,rs.getString(7)!=null ? Funcoes.copy(rs.getString(7),0,12) : "");
           
		   imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,2,rs.getString("CodProd"));
         
		String sDescAdic = ""; 
		
		//Gambs para colocar o lote.
		if ((rs.getDate(3) != null) && (rs.getString(2) != null)) {
		   sDescAdic = "  - L.:"+rs.getString(2).trim()+", VC.:"+Funcoes.sqlDateToStrDate(rs.getDate(3)).substring(3);
		}
		String sTmp = rs.getString(4) != null ? rs.getString(4).trim()+'\n' : ""; 
		//Gambs para colocar '"':
		if (sTmp.length() > 0) {
		  if (sMens.indexOf(sTmp) == -1 && iContaObs < 4) { //Esta mensagem ainda não esta na obs então posso adiciona-la.
			sMens += sMarcs[iContaObs++]+" "+sTmp;
		  }
		  sDescAdic += "  "+sMarcs[iContaObs-1]; 
		}
		String sTmp2 = rs.getString(5) != null ? rs.getString(5).trim()+'\n' : "";
		//Gambs para colocar '*'
		if (sTmp2.length() > 0 && !sTmp.equals(sTmp2)) {
		  if (sMens.indexOf(sTmp2) == -1 && iContaObs2 < 4) { //Esta mensagem ainda não esta na obs então posso adiciona-la.
			sMens += sMarcs2[iContaObs2++]+" "+sTmp2;
		  }
		  sDescAdic += "  "+sMarcs2[iContaObs2-1]; 
		}
		
		imp.say(imp.pRow()+0,8,Funcoes.copy(rs.getString("DescProd").trim(),0,55-sDescAdic.length())+sDescAdic);        
         
       
       
       
       String sSigla = "";
       String sCodfisc= rs.getString("CodFisc");
         
       if (sCodfisc == null)
       	   sSigla="";
       else if (sCodfisc.equals("3402.20.00"))
       	   sSigla = "A"; 	   
	   else if (sCodfisc.equals("2828.90.11"))
		   sSigla = "B"; 	   	   
	   else if (sCodfisc.equals("3808.40.10"))
		   sSigla = "C"; 	    	   
	   else if (sCodfisc.equals("3910.00.12"))
		   sSigla = "D"; 	   
       else if (sCodfisc.equals("1520.00.20"))
		   sSigla = "E"; 	   
	   else if (sCodfisc.equals("3404.20.10"))
		   sSigla = "F"; 	   
	   else if (sCodfisc.equals("2710.00.19"))
		   sSigla = "G"; 	   
       else if (sCodfisc.equals("3810.10.10"))
		   sSigla = "H";
	   else if (sCodfisc.equals("1515.90.00"))
		   sSigla = "I"; 	    	   
       else {
           if (!sFiscAdic.equals(""))
           	 JOptionPane.showMessageDialog(null,"Mais de um produto sem classificacao definida,sigla assinalada em branco.");
           else {
             sSigla = "J";
       	     sFiscAdic = sCodfisc ;
           }       	   
       }  
         
                    
         imp.say(imp.pRow()+0,65,sSigla);
         imp.say(imp.pRow()+0,67,Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2));
         imp.say(imp.pRow()+0,72,rs.getString("CodUnid").substring(0,4));
         imp.say(imp.pRow()+0,80,""+rs.getDouble("QtdItVenda"));
         imp.say(imp.pRow()+0,91,Funcoes.strDecimalToStrCurrency(8,2,""+((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP))));
         imp.say(imp.pRow()+0,102,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
         imp.say(imp.pRow()+0,120,""+rs.getDouble("PercICMSItVenda"));
         imp.say(imp.pRow()+0,125,""+rs.getDouble("PercIPIItvenda"));
		 imp.say(imp.pRow()+0,130,Funcoes.strDecimalToStrCurrency(7,2,rs.getString("VlrIPIItvenda")));
         
         iItImp++;
//         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         if ((iItImp == rs.getInt(1)) || (imp.pRow() == 41)) {
           if (iItImp == rs.getInt(1)) {
             int iRow = imp.pRow();
			if (!sMatObs[0].equals("")) {
			  imp.say(imp.pRow()+1,0,"");
			  imp.say(imp.pRow()+0,23,sMatObs[0]);
			}
			if (!sMatObs[1].equals("")) {
			  imp.say(imp.pRow()+1,0,"");
			  imp.say(imp.pRow()+0,23,sMatObs[1]);
			}
			if (!sMatObs[2].equals("")) {
			  imp.say(imp.pRow()+1,0,"");
			  imp.say(imp.pRow()+0,23,sMatObs[2]);
			}
			for (int i=0; i<(41-iRow);i++) {
				imp.say(imp.pRow()+1,0,"");
			}
//             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,1,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
             imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,2,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
             imp.say(imp.pRow()+0,62,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
             imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             iItImp = 0;
           }
           else if (imp.pRow() == 41) {
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,2,"***************");
             imp.say(imp.pRow()+0,32,"***************");
             imp.say(imp.pRow()+0,116,"***************");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,2,"***************");
             imp.say(imp.pRow()+0,62,"***************");
             imp.say(imp.pRow()+0,87,"***************");
             imp.say(imp.pRow()+0,116,"***************");
           }
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("RazTran"));
           imp.say(imp.pRow()+0,86,rs.getString("TipoFreteVD").equals("C") ? "1" : "2");
           imp.say(imp.pRow()+0,93,rs.getString("PlacaFreteVD"));
           imp.say(imp.pRow()+0,110,rs.getString("UfFreteVD"));
           
		   sTipoTran = rs.getString("TipoTran");
					 if (sTipoTran==null) sTipoTran = "T";
		    
					 if ( sTipoTran.equals("C") ){
						imp.say(imp.pRow()+0,115,Funcoes.setMascara(rs.getString("CnpjCli") != null ? rs.getString("CnpjCli") : "","##.###.###/####-##"));
					 }
					 else {
						imp.say(imp.pRow()+0,115,Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##")); 
					 }
           
           
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
           imp.say(imp.pRow()+0,76,rs.getString("CidTran"));
           imp.say(imp.pRow()+0,110,rs.getString("UfTran"));

		   sTipoTran = rs.getString("TipoTran");
					 if (sTipoTran==null)
						sTipoTran = "T";
					 if (sTipoTran.equals("C") ){
						 imp.say(imp.pRow()+0,115,rs.getString("InscCli"));
					 }
					 else { 
					  imp.say(imp.pRow()+0,115,rs.getString("InscTran"));
					 }

           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("QtdFreteVD"));
           imp.say(imp.pRow()+0,22,rs.getString("EspFreteVD"));
           imp.say(imp.pRow()+0,48,rs.getString("MarcaFreteVD"));
           imp.say(imp.pRow()+0,102,rs.getString("PesoBrutVD"));
           imp.say(imp.pRow()+0,125,rs.getString("PesoLiqVD"));
//           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());

           String sEnt = "";
           sEnt += rs.getString("EndEnt") != null ? rs.getString("EndEnt").trim() : ""; 
		   sEnt += rs.getString("NumEnt") != null ? ", "+rs.getString("NumEnt").trim() : ""; 
		   sEnt += rs.getString("ComplEnt") != null ? " - "+rs.getString("ComplEnt").trim() : ""; 
		   sEnt += rs.getString("BairEnt") != null ? "   "+rs.getString("CidEnt").trim() : ""; 
		   sEnt += rs.getString("CidEnt") != null ? " - "+rs.getString("CidEnt").trim() : ""; 
		   sEnt += rs.getString("UfEnt") != null ? "/"+rs.getString("UfEnt").trim() : ""; 
		   imp.say(imp.pRow()+0,3,sEnt);
		   
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());

		   if (!sDuplics[0].equals("")) {
			imp.say(imp.pRow()+1,0,sDuplics[0]);
			imp.say(imp.pRow()+0,17,sVencs[0]);
			imp.say(imp.pRow()+0,29,sVals[0]);
		   }
		   if (!sDuplics[1].equals("")) {
			imp.say(imp.pRow()+1,0,sDuplics[1]);
			imp.say(imp.pRow()+0,17,sVencs[1]);
			imp.say(imp.pRow()+0,29,sVals[1]);
		   }
		   if (!sDuplics[2].equals("")) {
			imp.say(imp.pRow()+1,0,sDuplics[2]);
			imp.say(imp.pRow()+0,17,sVencs[2]);
			imp.say(imp.pRow()+0,29,sVals[2]);
		   }
		   
		   String[] sMatMens = Funcoes.strToStrArray(sMens,3);


		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+0,0,sMatMens[0]);
		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+0,0,sMatMens[1]);
		   imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+0,0,sMatMens[2]);
           imp.say(imp.pRow()+0,63,sFiscAdic);
           
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+0,3,"Total: "+Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
		   imp.say(imp.pRow()+0,35,rs.getString("NomeCli") != null ? "Cliente: "+rs.getString("NomeCli") : "");
		   imp.say(imp.pRow()+0,100,"Emit.: "+Aplicativo.strUsuario);
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
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
      Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
    }
    return bRetorno;
  }
}

