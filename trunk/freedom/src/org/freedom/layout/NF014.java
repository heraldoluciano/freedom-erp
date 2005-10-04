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
 * Layout da nota fiscal para a empresa ModelCraft
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NF014 extends Layout {
	public boolean imprimir(NF nf,ImprimeOS imp) {
	boolean bRetorno = super.imprimir(nf, imp);
	final int iLinMaxItens = 45;
    Calendar cHora = Calendar.getInstance();
    int iNumNota = 0;
    int iItImp = 0;
    int iLinPag = imp.verifLinPag("NF");
    boolean bjatem = false;
    boolean bFat = true;
    String sCodfisc = "";
    String sSigla = "";
    String sNumNota = ""; 
	String sTipoTran = "";
    String[] sNat = new String[2];
    String[] sVencs = new String[2];
    String[] sVals = new String[2];
    String[] sDuplics = new String[4];
	//String[] sMatObs = null;
	String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
	Vector vClfiscal = new Vector();
	Vector vSigla = new Vector();
	
    try {
	     imp.limpaPags();
    	 if(cab.next()){
    		 iNumNota = cab.getInt(NF.C_DOC);
	         //sMatObs = Funcoes.strToStrArray(!cab.getString(NF.C_OBSPED).equals("") ? cab.getString(NF.C_OBSPED) : "",3);
    	 }
         if (iNumNota==0) {
            sNumNota = "000000";
         } 
         else {
            sNumNota = Funcoes.strZero(""+iNumNota,6);
         }

         for (int i=0; i<2; i++) {
	           if (bFat) {
		             if (parc.next()) {
		               sDuplics[i] = sNumNota+"/"+parc.getFloat(NF.C_NPARCITREC);
		               sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
		               sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,parc.getString(NF.C_VLRPARC));
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


     boolean bNat = true;
     while (itens.next()) {
         if (bNat) {
	           sNat[0] = itens.getString(NF.C_DESCNAT);
	           sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.##");
	           bNat = false;
         }
         if (imp.pRow()==0) {
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           if (nf.getTipoNF()==NF.TPNF_ENTRADA)
	               imp.say(imp.pRow()+0,109,"X");
	             else
	               imp.say(imp.pRow()+0,93,"X");
	           imp.say(imp.pRow()+0,125,sNumNota);
			   imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+0,9,sNat[0]);
	           imp.say(imp.pRow()+0,57,sNat[1]);
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,9,cab.getInt(NF.C_CODEMIT)+" - "+cab.getString(NF.C_RAZEMIT));
	           imp.say(imp.pRow()+0,92,!cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
	           imp.say(imp.pRow()+0,125,Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,9,Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(!cab.getString(NF.C_NUMEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() : "").trim()+" - "+(!cab.getString(NF.C_COMPLEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
	           imp.say(imp.pRow()+0,80,!cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15) : "");
	           imp.say(imp.pRow()+0,104,Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
	           imp.say(imp.pRow()+0,125,Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,""+imp.comprimido());
	           imp.say(imp.pRow()+0,9,cab.getString(NF.C_CIDEMIT));
	           imp.say(imp.pRow()+0,58,(!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+
					   				   (!cab.getString(NF.C_FONEEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : "").trim());
	           imp.say(imp.pRow()+0,80,cab.getString(NF.C_UFEMIT));
	           imp.say(imp.pRow()+0,92,!cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
	           imp.say(imp.pRow()+0,125,sHora);
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
	           imp.say(imp.pRow()+1,0,"");
		
			   imp.say(imp.pRow()+0,24,sVals[0]);
			   imp.say(imp.pRow()+0,42,sVencs[0]);
			   imp.say(imp.pRow()+0,76,sVals[1]);
			   imp.say(imp.pRow()+0,94,sVencs[1]);
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
			   imp.say(imp.pRow()+1,0,"");
         }
	
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,6,""+itens.getInt(NF.C_CODPROD));
         imp.say(imp.pRow()+0,11,itens.getString(NF.C_DESCPROD).trim());	
		
         sCodfisc = (!itens.getString(NF.C_CODFISC).equals("") ? itens.getString(NF.C_CODFISC) : "");
         
 		 if(!sCodfisc.equals("")){
 			for(int i=0;i<vClfiscal.size();i++){
 				if(vClfiscal.elementAt(i)!=null){
 					if(sCodfisc.equals((String)vClfiscal.elementAt(i))){
     					bjatem = true;
     					sSigla = ""+(char)(64 + i);
 					}
     				else{
     					bjatem = false;
     				}
 				}
 			}
 			if(!bjatem){
 				vClfiscal.addElement(sCodfisc);
 				sSigla = ""+(char)(64 + vClfiscal.size());
 				vSigla.addElement(sSigla + " = " + sCodfisc);
 			}
 		 }     
                    
         imp.say(imp.pRow()+0,60,sSigla);
         imp.say(imp.pRow()+0,65,Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
         imp.say(imp.pRow()+0,71,itens.getString(NF.C_CODUNID).substring(0,4));
         imp.say(imp.pRow()+0,77,""+itens.getFloat(NF.C_QTDITPED));
         imp.say(imp.pRow()+0,88,Funcoes.strDecimalToStrCurrency(8,2,""+((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP))));
         imp.say(imp.pRow()+0,100,Funcoes.strDecimalToStrCurrency(13,2,""+itens.getFloat(NF.C_VLRLIQITPED)));
         imp.say(imp.pRow()+0,116,""+itens.getFloat(NF.C_PERCICMSITPED));
         imp.say(imp.pRow()+0,122,""+itens.getFloat(NF.C_PERCIPIITPED));
		 imp.say(imp.pRow()+0,128,Funcoes.strDecimalToStrCurrency(7,2,""+itens.getFloat(NF.C_VLRIPIPED)));
         
         iItImp++;
//         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() >= iLinMaxItens)) {
        	 frete.next();
           if (imp.pRow()> iLinMaxItens) {
      	 	 Funcoes.mensagemInforma(null,"Número de itens ultrapassa capacidade do formulário!");
      	 	 imp.fechaGravacao();
      	 	 return false;
      	 	 
           }
           if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
	           	 int iRow = imp.pRow();
	             for (int i=0; i<(iLinMaxItens-6-iRow);i++) {
	             	 imp.say(imp.pRow()+1,0,"");
	             }
	             /*if (!sMatObs[0].equals("")) {
					 imp.say(imp.pRow()+1,0,"");
					 imp.say(imp.pRow()+0,27,sMatObs[0]);
				 }
				 if (!sMatObs[1].equals("")) {
					 imp.say(imp.pRow()+1,0,"");
					 imp.say(imp.pRow()+0,27,sMatObs[1]);
				 }
				 if (!sMatObs[2].equals("")) {
					 imp.say(imp.pRow()+1,0,"");
					 imp.say(imp.pRow()+0,27,sMatObs[2]);
				 }*/
				 for (int i=0; i<(iLinMaxItens-imp.pRow());i++) {
					 imp.say(imp.pRow()+1,0,"");
				 }
	             imp.say(imp.pRow()+1,0,"");
	             imp.say(imp.pRow()+1,0,""+imp.comprimido());
	             imp.say(imp.pRow()+0,5,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRBASEICMSPED)));
	             imp.say(imp.pRow()+0,33,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRICMSPED)));
	             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRPRODPED)));
	             imp.say(imp.pRow()+1,0,"");
	             imp.say(imp.pRow()+1,0,""+imp.comprimido());
	             imp.say(imp.pRow()+0,6,Funcoes.strDecimalToStrCurrency(20,2,""+frete.getFloat(NF.C_VLRFRETEPED)));
	             imp.say(imp.pRow()+0,65,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRADICPED)));
	             imp.say(imp.pRow()+0,90,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRIPIPED)));
	             imp.say(imp.pRow()+0,114,Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
	             iItImp = 0;
           }
           else if (imp.pRow() == iLinMaxItens) {
	             imp.say(imp.pRow()+1,0,""); 
	             imp.say(imp.pRow()+1,0,"");
	             imp.say(imp.pRow()+1,0,""+imp.comprimido());
	             imp.say(imp.pRow()+0,6,"***************");
	             imp.say(imp.pRow()+0,36,"***************");
	             imp.say(imp.pRow()+0,113,"***************");
	             imp.say(imp.pRow()+1,0,"");
	             imp.say(imp.pRow()+1,0,""+imp.comprimido());
	             imp.say(imp.pRow()+0,6,"***************");
	             imp.say(imp.pRow()+0,65,"***************");
	             imp.say(imp.pRow()+0,90,"***************");
	             imp.say(imp.pRow()+0,113,"***************");
           }
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,6,frete.getString(NF.C_RAZTRANSP));
           imp.say(imp.pRow()+0,87,frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
           imp.say(imp.pRow()+0,93,frete.getString(NF.C_PLACAFRETE));
           imp.say(imp.pRow()+0,109,frete.getString(NF.C_UFFRETE));
           
		   sTipoTran = frete.getString(NF.C_TIPOTRANSP);
				 if (sTipoTran==null) sTipoTran = "T";
	    
				 if ( sTipoTran.equals("C") ){
					imp.say(imp.pRow()+0,116,Funcoes.setMascara(!cab.getString(NF.C_CNPJEMIT).equals("") ? cab.getString(NF.C_CNPJEMIT) : "","##.###.###/####-##"));
				 }
				 else {
					imp.say(imp.pRow()+0,116,Funcoes.setMascara(!frete.getString(NF.C_CNPJTRANSP).equals("") ? frete.getString(NF.C_CNPJTRANSP) : "","##.###.###/####-##")); 
				 }
           
           
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42)+"   "+Funcoes.copy(""+frete.getInt(NF.C_NUMTRANSP),0,6));
           imp.say(imp.pRow()+0,77,Funcoes.copy(frete.getString(NF.C_CIDTRANSP),0,30));
           imp.say(imp.pRow()+0,108,frete.getString(NF.C_UFTRANSP));

		   sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					 if (sTipoTran.equals(""))
						sTipoTran = "T";
					 if (sTipoTran.equals("C") ){
						 imp.say(imp.pRow()+0,116,cab.getString(NF.C_INSCEMIT));
					 }
					 else { 
					  imp.say(imp.pRow()+0,116,frete.getString(NF.C_INSCTRANSP));
					 }

           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,frete.getString(NF.C_QTDFRETE));
           imp.say(imp.pRow()+0,26,frete.getString(NF.C_ESPFRETE));
           imp.say(imp.pRow()+0,55,frete.getString(NF.C_MARCAFRETE));
           imp.say(imp.pRow()+0,106,Funcoes.strDecimalToStrCurrency(5,casasDec,""+frete.getString(NF.C_PESOBRUTO)));
           imp.say(imp.pRow()+0,129,Funcoes.strDecimalToStrCurrency(5,casasDec,""+frete.getString(NF.C_PESOLIQ)));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());

          
		   /*imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+9-iSigla,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());*/
           imp.say(imp.pRow()+1,0,""+imp.comprimido());           
           imp.say(imp.pRow()+10,125,sNumNota);
           
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
    catch ( Exception err ) {
      Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
      err.getStackTrace();
    }
    return bRetorno;
  }
}

