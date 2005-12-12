/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFIswara2.java <BR>
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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
public class NFIswara2 extends Layout {
	
	public boolean imprimir(NF nf,ImprimeOS imp) {
		 
	    boolean bRetorno = super.imprimir(nf, imp);
	    boolean bjatem = false;
	    boolean bFat = true;
	    boolean bNat = true;
	    int iNumNota = 0;
	    int iItImp = 0;
	    int iLinPag = imp.verifLinPag("NF");
		int iContaObs = 0; //Contador para a gamb de asterisco
		int iContaObs2 = 0; //Contador para a gamb de '"'
	    Calendar cHora = Calendar.getInstance();
	    Vector vMens = null;
	    Vector vClfisc = new Vector();
	    String sDescAdic = "";
	    String sNumNota = ""; 
		String sTipoTran = "";
	    String[] sNat = new String[2];
	    String[] sVencs = new String[3];
	    String[] sVals = new String[3];
	    String[] sDuplics = new String[3];
		String[] sMatObs = null;
		String[] sMarcs = {"\"","\"\"","\"\"\"","\"\"\"\""}; //Tipos de Marcs. 
		String[] sMarcs2 = {"*","**","***","****"}; //Tipos de Marcs.
		String sMens = "";
		String sSigla = "";
		String sCodfisc = ""; 
		String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"
							+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"
								+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
		
	    try {
	      imp.limpaPags();
	      
	      cab.next();
	         iNumNota = cab.getInt(NF.C_DOC);
	         if (iNumNota==0) {
	            sNumNota = "000000";
	         } 
	         else {
	            sNumNota = Funcoes.strZero(""+iNumNota,6);
	         }
	
	         for (int i=0; i<3; i++) {
	           if (bFat) {
	             if (parc.next()) {
	               sDuplics[i] = sNumNota+"/"+parc.getInt(NF.C_NPARCITREC);
	               sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
	               sVals[i] = Funcoes.strDecimalToStrCurrency(10,2,parc.getString(NF.C_VLRPARC));
	             }
	             else {
	               bFat = false;
	               sDuplics[i] = "*******";
	               sVencs[i] = "";
	               sVals[i] = "";
	             }
	           }
	           else {
	             bFat = false;
	             sDuplics[i] = "*******";
	             sVencs[i] = "";
	             sVals[i] = "";
	           }
	         }
	
	         
	      while (itens.next()) {  
	    	  
	         if (bNat) {
	           sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT),40);
	           sNat[1] = Funcoes.setMascara(""+itens.getInt(NF.C_CODNAT),"#.##");
	           sMatObs = Funcoes.strToStrArray(cab.getString(NF.C_OBSPED),3);
	
	           bNat = false;
	         }
	         
	         if (imp.pRow()==0) {
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   if (nf.getTipoNF()==NF.TPNF_ENTRADA)
		           imp.say(imp.pRow()+0,105,"X");
		       else
		    	   imp.say(imp.pRow()+0,91,"X");
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+3,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,sNat[0]);
	           imp.say(imp.pRow()+0,48,sNat[1]);
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,cab.getInt(NF.C_CODEMIT)+" - "+cab.getString(NF.C_RAZEMIT));
	           imp.say(imp.pRow()+0,126,Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim()).trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
	           imp.say(imp.pRow()+0,78,!cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15) : "");
	           imp.say(imp.pRow()+0,100,Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
	           imp.say(imp.pRow()+0,126,Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,cab.getString(NF.C_CIDEMIT));
	           imp.say(imp.pRow()+0,33,cab.getString(NF.C_UFEMIT));
	           imp.say(imp.pRow()+0,40,(!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+(!cab.getString(NF.C_FONEEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : ""));
	           imp.say(imp.pRow()+0,60,!cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
	           imp.say(imp.pRow()+0,92,!cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
	           imp.say(imp.pRow()+0,126,sHora);
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,5,sNumNota);
			   if (sVencs[1].equals("")) {
			     imp.say(imp.pRow()+0,70,sVals[0]);
	             imp.say(imp.pRow()+0,97,sDuplics[0]);
	             imp.say(imp.pRow()+0,124,(cab.getString(NF.C_DESCPLANOPAG).length()>11?cab.getString(NF.C_DESCPLANOPAG).substring(0,11):cab.getString(NF.C_DESCPLANOPAG)));
			   }
			   else {
			     imp.say(imp.pRow()+0,65,Funcoes.strDecimalToStrCurrency(20,2,itens.getFloat(NF.C_VLRLIQPED)+""));
				 imp.say(imp.pRow()+0,90,"VIDE DESDOBRAMENTO");
			   }
	
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+0,3,""+Funcoes.strZero(""+cab.getInt(NF.C_CODVEND),8));
			   imp.say(imp.pRow()+0,48,cab.getInt(NF.C_CODEMIT)+"");
			   imp.say(imp.pRow()+0,62,!cab.getString(NF.C_DESCSETOR).equals("") ? Funcoes.copy(cab.getString(NF.C_DESCSETOR),0,20) : ""); // descsetor
		
	           imp.say(imp.pRow()+0,96,cab.getString(NF.C_CODVEND));
			   imp.say(imp.pRow()+0,118,!cab.getString(NF.C_CODBANCO).equals("") ? Funcoes.copy(cab.getString(NF.C_CODBANCO)+"-",0,5) : "");
	           
			   imp.say(imp.pRow()+0,124,!cab.getString(NF.C_NOMEBANCO).equals("") ? Funcoes.copy(cab.getString(NF.C_NOMEBANCO),0,12) : ""); // nome do banco
	           
			   imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	         }
	         imp.say(imp.pRow()+1,0,""+imp.comprimido());
	         imp.say(imp.pRow()+0,0,""+itens.getInt(NF.C_CODPROD));
	        			 
			
			//Gambs para colocar o lote.
			if ((itens.getDate(NF.C_VENCLOTE) != null) && (!itens.getString(NF.C_CODLOTE).equals(""))) {
			   sDescAdic = "  - L.:"+itens.getString(NF.C_CODLOTE).trim()+", VC.:"+Funcoes.dateToStrDate(itens.getDate(NF.C_VENCLOTE)).substring(3);
			}
			String sTmp = itens.getString(NF.C_DESCFISC).trim()+'\n' ; 
			//Gambs para colocar '"':
			if (sTmp.length() > 0) {
			  if (sMens.indexOf(sTmp) == -1 && iContaObs < 4) { //Esta mensagem ainda não esta na obs então posso adiciona-la.
				sMens += sMarcs[iContaObs++]+" "+sTmp;
			  }
			  sDescAdic += "  "+sMarcs[iContaObs-1]; 
			}
			String sTmp2 = itens.getString(NF.C_DESCFISC2).trim()+'\n' ;
			//Gambs para colocar '*'
			if (sTmp2.trim().length() > 0 && !sTmp.equals(sTmp2)) {
			  if (sMens.indexOf(sTmp2) == -1 && iContaObs2 < 4) { //Esta mensagem ainda não esta na obs então posso adiciona-la.
				sMens += sMarcs2[iContaObs2++]+" "+sTmp2;
			  }
			  sDescAdic += "  "+sMarcs2[iContaObs2-1]; 
			} 
	       
	      /* sSigla = "";
	       sCodfisc= itens.getString(NF.C_CODFISC);
	       
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
	        	 menssagem++;
	           else {
	             sSigla = "J";
	       	     sFiscAdic = sCodfisc ;
	           }       	   
	       }  */
	       
	       sCodfisc = itens.getString(NF.C_CODFISC);
           
	   		if(!sCodfisc.equals("")){
	   			for(int i=0;i<vClfisc.size();i++){
	   				if(vClfisc.elementAt(i)!=null){
	   					if(sCodfisc.equals((String)vClfisc.elementAt(i))){
	       					bjatem = true;
	       					sSigla = ""+(char)(65 + i);
	   					}
	       				else{
	       					bjatem = false;
	       				}
	   				}
	   			}
	   			if(!bjatem){
	   				vClfisc.addElement(sCodfisc);
	   				sSigla = ""+(char)(64 + vClfisc.size());
	   			}
	   		}
	       
	       imp.say(imp.pRow()+0,8,itens.getString(NF.C_DESCPROD).substring(0,23));
	       imp.say(imp.pRow()+0,34,itens.getString(NF.C_CODLOTE).trim());
	       imp.say(imp.pRow()+0,49,itens.getString(NF.C_VENCLOTE).trim());
	       imp.say(imp.pRow()+0,62,sSigla);
	       imp.say(imp.pRow()+0,66,Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
	       imp.say(imp.pRow()+0,71,itens.getString(NF.C_CODUNID));
	       imp.say(imp.pRow()+0,81,""+itens.getFloat(NF.C_QTDITPED));
	       imp.say(imp.pRow()+0,91,Funcoes.strDecimalToStrCurrency(8,2,""+((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP))));
	       imp.say(imp.pRow()+0,102,Funcoes.strDecimalToStrCurrency(13,2,""+itens.getFloat(NF.C_VLRLIQITPED)));
	       imp.say(imp.pRow()+0,120,""+itens.getFloat(NF.C_PERCICMSITPED));
	       imp.say(imp.pRow()+0,125,""+itens.getFloat(NF.C_PERCIPIITPED));
		   imp.say(imp.pRow()+0,130,Funcoes.strDecimalToStrCurrency(7,2,""+itens.getFloat(NF.C_VLRIPIITPED)));
	         
	       iItImp++;
	       if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == 38)) {
	           imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+0,9,"Valor do desconto : "+Funcoes.strDecimalToStrCurrency(15,2,""+cab.getFloat(NF.C_VLRDESCITPED)));        	 
	           if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
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
			   for (int i=0; i<(39-iRow);i++) {
			      imp.say(imp.pRow()+1,0,"");
			   }
				
			   frete.next();
		       imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
		       imp.say(imp.pRow()+1,0,""+imp.comprimido());
		       imp.say(imp.pRow()+0,1,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRBASEICMSPED)));
		       imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRICMSPED)));
		       imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
		       imp.say(imp.pRow()+1,0,"");
		       imp.say(imp.pRow()+1,0,""+imp.comprimido());
		       imp.say(imp.pRow()+0,2,Funcoes.strDecimalToStrCurrency(20,2,""+frete.getFloat(NF.C_VLRFRETEPED)));
		       imp.say(imp.pRow()+0,62,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRADICPED)));
		       imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRIPIPED)));
		       imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
		       iItImp = 0;
	           }
	           else if (imp.pRow() == 39) {
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
	           imp.say(imp.pRow()+0,2,frete.getString(NF.C_RAZTRANSP));
	           imp.say(imp.pRow()+0,86,frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
	           imp.say(imp.pRow()+0,93,frete.getString(NF.C_PLACAFRETE));
	           imp.say(imp.pRow()+0,110,frete.getString(NF.C_UFFRETE));
	           
			   sTipoTran = frete.getString(NF.C_TIPOTRANSP);
				 if (sTipoTran==null) 
					 sTipoTran = "T";    
				 if ( sTipoTran.equals("C") ){
					imp.say(imp.pRow()+0,115,Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
				 }
				 else {
					imp.say(imp.pRow()+0,115,Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##")); 
				 }
	           
	           
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42)+", "+Funcoes.copy(frete.getString(NF.C_NUMTRANSP),0,6));
	           imp.say(imp.pRow()+0,76,frete.getString(NF.C_CIDTRANSP));
	           imp.say(imp.pRow()+0,110,frete.getString(NF.C_UFTRANSP));
	
				 if (sTipoTran.equals("C") ){
					 imp.say(imp.pRow()+0,115,cab.getString(NF.C_INSCEMIT));
				 }
				 else { 
				  imp.say(imp.pRow()+0,115,frete.getString(NF.C_INSCTRANSP));
				 }

		       imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,2,""+frete.getFloat(NF.C_QTDFRETE));
	           imp.say(imp.pRow()+0,22,frete.getString(NF.C_ESPFRETE));
	           imp.say(imp.pRow()+0,48,frete.getString(NF.C_MARCAFRETE));
	           imp.say(imp.pRow()+0,102,""+frete.getFloat(NF.C_PESOBRUTO));
	           imp.say(imp.pRow()+0,125,""+frete.getFloat(NF.C_PESOLIQ));
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	
	           String sEnt = "";
	           sEnt += cab.getString(NF.C_ENDENTEMIT)+" , "; 
			   sEnt += cab.getString(NF.C_NUMENTEMIT)+"   "; 
			   sEnt += cab.getString(NF.C_COMPLENTEMIT)+"  /  "; 
			   sEnt += cab.getString(NF.C_BAIRENTEMIT)+" - "; 
			   sEnt += cab.getString(NF.C_CIDENTEMIT)+" / "; 
			   sEnt += cab.getString(NF.C_UFENTEMIT); 
			   imp.say(imp.pRow()+0,3,sEnt);
			   
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
			   
		   
	
/*			   if (!sDuplics[0].equals("")) {
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
			   } */
			   
			   vMens = Funcoes.strToVectorSilabas(sMens,28);
			   
			   for (int iPos=0; iPos<6; iPos++) {
				   imp.say(imp.pRow()+1,0,"");
				   if (iPos<sDuplics.length) {
					   imp.say(imp.pRow(),0,sDuplics[iPos]); // imprimir duplicata
					   imp.say(imp.pRow(),10,sVencs[iPos]); // imprimir vencimento 
					   imp.say(imp.pRow(),21,sVals[iPos]); // imprimir valor 
				   }
				   if (iPos<vClfisc.size() && iPos<5) {
					   imp.say(imp.pRow(),33,(vClfisc.elementAt(iPos)!=null?(String)vClfisc.elementAt(iPos):"")); // imprimir classificação fiscal
					   if(vClfisc.size()>(5+iPos))
						   imp.say(imp.pRow(),45,(vClfisc.elementAt(iPos+5)!=null?(String)vClfisc.elementAt(iPos+5):""));// imprimir classificação fiscal
				   }
				   
				   if (iPos<vMens.size()) {
					   imp.say(imp.pRow(),57,(vMens.elementAt(iPos)!=null?(String)vMens.elementAt(iPos):"")); // imprimir observaçoes 
					   
				   }
			   }
			   
			   imp.say(imp.pRow()+6,0,""+imp.comprimido());
			   imp.say(imp.pRow()+0,125,""+iNumNota);
	
	           for (int i=imp.pRow(); i<=iLinPag; i++) { 
	             imp.say(imp.pRow()+1,0,"");
	           }
	           imp.setPrc(0,0);
	           imp.incPags();
	         }
	      }
	      
	      /*if(menssagem>0)
	         JOptionPane.showMessageDialog(null,"Mais de um produto sem classificacao definida,sigla assinalada em branco.");*/
	      
	      imp.fechaGravacao();
	      bRetorno = true;
	    }
	    catch ( Exception err ) {
	      Funcoes.mensagemErro(null,"Erro ao montar nf de venda.\n"+err.getMessage());     
	      err.printStackTrace();
	      bRetorno = false;
	    }
	    return bRetorno;
  }
}

