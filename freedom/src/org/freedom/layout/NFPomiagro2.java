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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NFPomiagro2 extends Layout {
  public boolean imprimir(NF nf,ImprimeOS imp) {
    boolean retorno = super.imprimir(nf, imp);
    GregorianCalendar cHora = new GregorianCalendar();
    
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
	//TabVector 
	int iContaMens = 1;
	String sIncra = "" ;
	Vector vMens = new Vector();
	vMens.clear();
	
    String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
    /*
    try {
    */
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
           
           if (adic.next()) {
           	 sValsCli[0] = adic.getString(NF.C_CPFEMITAUX)  != null ? adic.getString(NF.C_CPFEMITAUX) : cab.getString(NF.C_CPFEMIT);
           	 sValsCli[1] = adic.getString(NF.C_NOMEEMITAUX)  != null ? adic.getString(NF.C_NOMEEMITAUX) : cab.getString(NF.C_RAZEMIT);
           	 sValsCli[2] = adic.getString(NF.C_CIDEMITAUX)  != null ? adic.getString(NF.C_CIDEMITAUX) : cab.getString(NF.C_CIDEMIT);
           	 sValsCli[3] = adic.getString(NF.C_UFEMITAUX)  != null ? adic.getString(NF.C_UFEMITAUX) : cab.getString(NF.C_UFEMIT);
           }
           else {
           	 sValsCli[0] = cab.getString(NF.C_CPFEMIT);
           	 sValsCli[1] = cab.getString(NF.C_RAZEMIT);
           	 sValsCli[2] = cab.getString(NF.C_CIDEMIT);
           	 sValsCli[3] = cab.getString(NF.C_UFEMIT); 
           }
           	
           
           sIncra = cab.getString(NF.C_INCRAEMIT);
           if (sIncra != null ){
			 imp.say(imp.pRow()+0,6,cab.getInt(NF.C_CODEMIT)+" - "+sValsCli[1]+"Incra:");
			 imp.say(imp.pRow()+0,71,cab.getString(NF.C_INCRAEMIT));
           }
		   else  {
             imp.say(imp.pRow()+0,6,cab.getInt(NF.C_CODEMIT)+" - "+sValsCli[1]);
           }        
            
           imp.say(imp.pRow()+0,95,sValsCli[0] != null ? Funcoes.setMascara(sValsCli[0],"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,126,Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMIT)));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,30).trim()+", "+(cab.getString(NF.C_NUMEMIT) != null ? Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() : "").trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,76,cab.getString(NF.C_BAIREMIT));
           imp.say(imp.pRow()+0,106,Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
           sImpDtSaidaNat = cab.getString(NF.C_IMPDTSAIDA);
           if (sImpDtSaidaNat==null) sImpDtSaidaNat = "S";
           if (sImpDtSaidaNat.equals("S"))
              imp.say(imp.pRow()+0,126,Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,sValsCli[2] != null ? sValsCli[2] : "");
           imp.say(imp.pRow()+0,63,(cab.getString(NF.C_DDDEMIT) != null ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+
				   				   (cab.getString(NF.C_FONEEMIT) != null ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : "").trim());
           imp.say(imp.pRow()+0,87,sValsCli[3] != null ? sValsCli[3] : "");
           imp.say(imp.pRow()+0,96,cab.getString(NF.C_RGEMIT) != null ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
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
         imp.say(imp.pRow()+0,2,Funcoes.alinhaDir(itens.getInt(NF.C_CODPROD),8));


//Descrições adicionais colocadas junto a decrição do produto.
         
         String sDescAdic = ""; 
         //Gambs para colocar o lote:
         if ((itens.getDate(NF.C_VENCLOTE) != null) && (itens.getString(NF.C_CODLOTE) != null)) {
         	sDescAdic = "  - L.:"+itens.getString(NF.C_CODLOTE).trim()+", VC.:"+Funcoes.dateToStrDate(itens.getDate(NF.C_VENCLOTE)).substring(3);
         }
		 String sTmp = cab.getString(NF.C_CODEMIT) != null ? cab.getString(NF.C_CODEMIT).trim() : ""; 
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
		 sTmp = cab.getString(NF.C_CODVEND) != null ? cab.getString(NF.C_CODVEND).trim() : "";
		 String sClasFisc = Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2);
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
		 
		   
         imp.say(imp.pRow()+0,14,Funcoes.copy(itens.getString(NF.C_DESCPROD).trim(),0,66-sDescAdic.length())+sDescAdic);
         imp.say(imp.pRow()+0,83,sClasFisc);
         imp.say(imp.pRow()+0,89,itens.getString(NF.C_CODUNID).substring(0,4));
         imp.say(imp.pRow()+0,95,""+itens.getFloat(NF.C_QTDITPED));
          
         imp.say(imp.pRow()+0,104,Funcoes.strDecimalToStrCurrency(13,2,""+(new BigDecimal(itens.getString(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)));
//         imp.say(imp.pRow()+0,97,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrProdItVenda")));
         imp.say(imp.pRow()+0,119,Funcoes.strDecimalToStrCurrency(13,2,""+itens.getString(NF.C_VLRLIQITPED)));
         imp.say(imp.pRow()+0,135,""+itens.getFloat(NF.C_PERCICMSITPED));
         
         iItImp++;
         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         if ((iItImp == itens.getInt(NF.C_CODITPED)) || (imp.pRow() == 46)) {
           if (iItImp == itens.getInt(NF.C_CODITPED)) {
             int iRow = imp.pRow();
             for (int i=0; i<(46-iRow);i++) {
                 imp.say(imp.pRow()+1,0,"");
             }
             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             
             imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRBASEICMSPED)));
             imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRICMSPED)));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRLIQITPED)));
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRFRETEPED)));
             imp.say(imp.pRow()+0,62,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRADICPED)));
             imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRIPIPED)));
             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,transp.getString(NF.C_VLRLIQPED)));
             iItImp = 0;
			 sObs += transp.getString(NF.C_OBSPED) != null ? transp.getString(NF.C_OBSPED).trim()+'\n' : "";
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
           imp.say(imp.pRow()+0,6,transp.getString(NF.C_RAZTRANSP));
           imp.say(imp.pRow()+0,82,transp.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
           imp.say(imp.pRow()+0,90,transp.getString(NF.C_PLACAFRETE));
           imp.say(imp.pRow()+0,104,transp.getString(NF.C_UFFRETE));
           
		   sTipoTran = transp.getString(NF.C_TIPOTRANSP);
			
			   if (sTipoTran==null) sTipoTran = "T";
		         if ( sTipoTran.equals("C") ){
			        imp.say(imp.pRow()+0,111,Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT) != null ? transp.getString(NF.C_CNPJEMIT) : "","##.###.###/####-##"));
				  }
			  
			  else {
					 imp.say(imp.pRow()+0,111,Funcoes.setMascara(transp.getString(NF.C_CNPJTRANSP) != null ? transp.getString(NF.C_CNPJTRANSP) : "","##.###.###/####-##")); 
			   	  }
            

           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(transp.getString(NF.C_ENDTRANSP),0,42)+", "+Funcoes.copy(transp.getString(NF.C_NUMTRANSP),0,6));
           imp.say(imp.pRow()+0,69,transp.getString(NF.C_CIDTRANSP));
           imp.say(imp.pRow()+0,104,transp.getString(NF.C_UFTRANSP));

  
		   if (transp.getString(NF.C_TIPOTRANSP).compareTo("C") == 0){
			   imp.say(imp.pRow()+0,111,cab.getString(NF.C_INSCEMIT));
		   }
		   else { 
			imp.say(imp.pRow()+0,111,transp.getString(NF.C_INSCTRANSP));
		   }
           
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,transp.getString(NF.C_QTDFRETE));
           imp.say(imp.pRow()+0,26,transp.getString(NF.C_ESPFRETE));
           imp.say(imp.pRow()+0,47,transp.getString(NF.C_MARCAFRETE));
           imp.say(imp.pRow()+0,93,transp.getString(NF.C_PESOBRUTO));
           imp.say(imp.pRow()+0,120,transp.getString(NF.C_PESOLIQ));
           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,25,Funcoes.alinhaDir(cab.getInt(NF.C_CODEMIT),10));
           imp.say(imp.pRow()+0,45,Funcoes.alinhaDir(cab.getInt(NF.C_CODVEND),10));
           imp.say(imp.pRow()+0,64,transp.getString(NF.C_DOC) != null ? Funcoes.strZero(""+iNumNota,6) : "000000");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,35,cab.getString(NF.C_CODVEND));
           imp.say(imp.pRow()+0,40,transp.getString(NF.C_CODCLCOMIS) != null ? transp.getString(NF.C_CODCLCOMIS) : "");
           imp.say(imp.pRow()+0,50,transp.getString(NF.C_PERCMCOMISPED) != null ? (new BigDecimal(transp.getString(NF.C_PERCMCOMISPED))).setScale(2,BigDecimal.ROUND_HALF_UP).toString() : "");
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
      retorno = true;
    /*}
    catch ( SQLException err ) {
      JOptionPane.showMessageDialog(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
    }*/
    return retorno;
  }
}

