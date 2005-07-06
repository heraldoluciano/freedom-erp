/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: layout <BR>
 * Classe: @(#)NFPomiagro2.java <BR>
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
 * Layout da nota fiscal para a empresa Pomiagro Ltda.
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;
import org.freedom.funcoes.Funcoes;

public class NFPomiagro2 extends Leiaute {
  public boolean imprimir(NF nf,ImprimeOS imp) {
//    GregorianCalendar cHora = new GregorianCalendar();
    boolean bRetorno = false;
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
	TabVector cab = null;
	TabVector itens = null;
	TabVector parc = null;
	int iContaMens = 1;
	String sIncra = "" ;
	Vector vMens = new Vector();
	vMens.clear();
/*	
//    String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
    try {
      cab = nf.getTabVector(NF.T_CAB);
      itens = nf.getTabVector(NF.T_ITENS);
      parc = nf.getTabVector(NF.T_PARC);
      if (cab.next()) {
        iNumNota = cab.getInt(NF.C_DOC);
      }
      for (int i=0; i<4; i++) {
        if (bFat) {
          if (parc.next()) {
            sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
            sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,parc.getString(NF.C_VLRPARC));
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
      while (itens.next()) {
           if (bNat) {
             sNat[0] = itens.getString(NF.C_DESCNAT);
             sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.###");
             bNat = false;
             
           }
         if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido()+imp.expandido());
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,7,Funcoes.strZero(""+iNumNota,6));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.normal()+imp.expandido());
           imp.say(imp.pRow()+0,36,Funcoes.strZero(""+iNumNota,6));
           imp.say(imp.pRow()+1,0,""+imp.retiraExpandido());
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           if (bEntrada)
             imp.say(imp.pRow()+0,105,"X");
           else
             imp.say(imp.pRow()+0,93,"X");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,sNat[0]);
           imp.say(imp.pRow()+0,53,sNat[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
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
           	
           
           sIncra = rs.getString("IncraCli");
           if (sIncra != null ){
			 imp.say(imp.pRow()+0,6,rs.getInt("CodCli")+" - "+sValsCli[1]+"Incra:");
			 imp.say(imp.pRow()+0,71,rs.getString("IncraCli"));
           }
		   else  {
             imp.say(imp.pRow()+0,6,rs.getInt("CodCli")+" - "+sValsCli[1]);
           }        
            
           imp.say(imp.pRow()+0,95,sValsCli[0] != null ? Funcoes.setMascara(sValsCli[0],"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,76,rs.getString("BairCli"));
           imp.say(imp.pRow()+0,106,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           sImpDtSaidaNat = rs.getString("IMPDTSAIDANAT");
           if (sImpDtSaidaNat==null) sImpDtSaidaNat = "S";
           if (sImpDtSaidaNat.equals("S"))
              imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,sValsCli[2] != null ? sValsCli[2] : "");
           imp.say(imp.pRow()+0,63,(rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+
				   				   (rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : "").trim());
           imp.say(imp.pRow()+0,87,sValsCli[3] != null ? sValsCli[3] : "");
           imp.say(imp.pRow()+0,96,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           //imp.say(imp.pRow()+0,128,sHora);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,37,sVencs[0]);
           imp.say(imp.pRow()+0,63,sVencs[1]);
           imp.say(imp.pRow()+0,89,sVencs[2]);
           imp.say(imp.pRow()+0,117,sVencs[3]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,37,sVals[0]);
           imp.say(imp.pRow()+0,63,sVals[1]);
           imp.say(imp.pRow()+0,89,sVals[2]);
           imp.say(imp.pRow()+0,117,sVals[3]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
         }
             
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,2,Funcoes.alinhaDir(rs.getInt("CodProd"),8));


//Descrições adicionais colocadas junto a decrição do produto.
         
         String sDescAdic = ""; 
         //Gambs para colocar o lote:
         if ((rs.getDate(3) != null) && (rs.getString(2) != null)) {
         	sDescAdic = "  - L.:"+rs.getString(2).trim()+", VC.:"+Funcoes.sqlDateToStrDate(rs.getDate(3)).substring(3);
         }
		 String sTmp = rs.getString(5) != null ? rs.getString(5).trim() : ""; 
		 //Gambs para colocar arteriscos:
		 if (sTmp.length() > 0) {
		 	 int iLinha;
		 	 for (iLinha=0;iLinha<vMens.size();iLinha++) {
		 	 	if (((String[])vMens.elementAt(iLinha))[1].equals(sTmp) &&
		 	 		((String[])vMens.elementAt(iLinha))[0].indexOf("*") == 0) {
		 	 		sDescAdic += " "+((String[])vMens.elementAt(iLinha))[0];
		 	 		break;
		 	 	}
		 	 }
		 	 if (iLinha==vMens.size()) {
		 	 	vMens.add(
		 	 			new String[] {
		 	 					Funcoes.replicate("*",iContaMens++),
								sTmp
						}
		 	 	);
		 	 	sDescAdic += " "+((String[])vMens.elementAt(iLinha))[0];
		 	 }
		 	 
		 }
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
		 
		   
         imp.say(imp.pRow()+0,14,Funcoes.copy(rs.getString("DescProd").trim(),0,66-sDescAdic.length())+sDescAdic);
         imp.say(imp.pRow()+0,83,sClasFisc);
         imp.say(imp.pRow()+0,89,rs.getString("CodUnid").substring(0,4));
         imp.say(imp.pRow()+0,95,""+rs.getDouble("QtdItVenda"));
          
         imp.say(imp.pRow()+0,104,Funcoes.strDecimalToStrCurrency(13,2,""+(new BigDecimal(rs.getString("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)));
//         imp.say(imp.pRow()+0,97,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrProdItVenda")));
         imp.say(imp.pRow()+0,119,Funcoes.strDecimalToStrCurrency(13,2,""+rs.getString("VlrLiqItVenda")));
         imp.say(imp.pRow()+0,135,""+rs.getDouble("PercICMSItVenda"));
         
         iItImp++;
         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         if ((iItImp == rs.getInt(1)) || (imp.pRow() == 46)) {
           if (iItImp == rs.getInt(1)) {
             int iRow = imp.pRow();
             for (int i=0; i<(46-iRow);i++) {
                 imp.say(imp.pRow()+1,0,"");
             }
             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             
             imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
             imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
             imp.say(imp.pRow()+0,62,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
             imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             iItImp = 0;
			 sObs += rs.getString("ObsVenda") != null ? rs.getString("ObsVenda").trim()+'\n' : "";
           }
           else if (imp.pRow() == 46) {
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,"***************");
             imp.say(imp.pRow()+0,32,"***************");
             imp.say(imp.pRow()+0,114,"***************");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,"***************");
             imp.say(imp.pRow()+0,62,"***************");
             imp.say(imp.pRow()+0,87,"***************");
             imp.say(imp.pRow()+0,114,"***************");
           }
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,rs.getString("RazTran"));
           imp.say(imp.pRow()+0,82,rs.getString("TipoFreteVD").equals("C") ? "1" : "2");
           imp.say(imp.pRow()+0,90,rs.getString("PlacaFreteVD"));
           imp.say(imp.pRow()+0,104,rs.getString("UfFreteVD"));
           
		   sTipoTran = rs.getString("TipoTran");
			
			   if (sTipoTran==null) sTipoTran = "T";
		         if ( sTipoTran.equals("C") ){
			        imp.say(imp.pRow()+0,111,Funcoes.setMascara(rs.getString("CnpjCli") != null ? rs.getString("CnpjCli") : "","##.###.###/####-##"));
				  }
			  
			  else {
					 imp.say(imp.pRow()+0,111,Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##")); 
			   	  }
            

           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
           imp.say(imp.pRow()+0,69,rs.getString("CidTran"));
           imp.say(imp.pRow()+0,104,rs.getString("UfTran"));

  
		   if (rs.getString("TipoTran").compareTo("C") == 0){
			   imp.say(imp.pRow()+0,111,rs.getString("InscCli"));
		   }
		   else { 
			imp.say(imp.pRow()+0,111,rs.getString("InscTran"));
		   }
           
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,rs.getString("QtdFreteVD"));
           imp.say(imp.pRow()+0,26,rs.getString("EspFreteVD"));
           imp.say(imp.pRow()+0,47,rs.getString("MarcaFreteVD"));
           imp.say(imp.pRow()+0,93,rs.getString("PesoBrutVD"));
           imp.say(imp.pRow()+0,120,rs.getString("PesoLiqVD"));
           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,25,Funcoes.alinhaDir(rs.getInt("CodCli"),10));
           imp.say(imp.pRow()+0,45,Funcoes.alinhaDir(rs.getInt("CodVenda"),10));
           imp.say(imp.pRow()+0,64,rs.getString("DocVenda") != null ? Funcoes.strZero(""+iNumNota,6) : "000000");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,35,rs.getString("CodVend"));
           imp.say(imp.pRow()+0,40,rs.getString("CodClComis") != null ? rs.getString("CodClComis") : "");
           imp.say(imp.pRow()+0,50,rs.getString("PercMComisVenda") != null ? (new BigDecimal(rs.getString("PercMComisVenda"))).setScale(2,BigDecimal.ROUND_HALF_UP).toString() : "");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           for(int i=0;i<vMens.size();i++)
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
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
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
    }*/
    return bRetorno;
  }
}

