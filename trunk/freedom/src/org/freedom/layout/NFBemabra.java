 /**
 * @version 13/04/2004 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFBemabra.java <BR>
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;

public class NFBemabra extends Leiaute {
  private BigDecimal bigSomaServ = new BigDecimal(0);
  private String sMensAdic="";
  
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
    Calendar cHora = Calendar.getInstance();
    boolean bRetorno;
    int iNumNota = 0;
    String sNumNota = ""; 
	int iProd = 0;
	int iServ = 0;
	String sTipoTran="";
    boolean bFat = true;
    boolean bTotalizou = false;
    Vector vValores = new Vector();
    Vector vDescServ = new Vector();
    String[] sNat = new String[4];
    String[] sVencs = new String[4];
    String[] sVals = new String[4];
    String[] sDuplics = new String[4];
    String[] sMatObs = null;
    
    
//	String sFiscAdic = "";
	bigSomaServ = new BigDecimal(0);
	//	String[] sMarcs = {"\"","\"\"","\"\"\"","\"\"\"\""}; //Tipos de Marcs. 
//	String[] sMarcs2 = {"*","**","***","****"}; //Tipos de Marcs.
	String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
    try {
      imp.limpaPags();
      boolean bNat = true;
//      Vector vServ = new Vector();
  //    int iServAtual = 0;
      
      while (rs.next()) {

         iNumNota = rs.getInt("DocVenda");
         if (iNumNota==0) {
            sNumNota = "000000";
         } 
         else {
            sNumNota = Funcoes.strZero(""+iNumNota,6);
         }
         for (int i=0; i<4; i++) {
           if (bFat) {
             if (rsRec.next()) {
               sDuplics[i] = sNumNota+"/"+rsRec.getInt("NPARCITREC");
               sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
               sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
             }
             else {
               bFat = false;
               sDuplics[i] = "********";
               sVencs[i] = "********";
               sVals[i] = "********";
             }
           }
          
         }

         if (bNat) {
           sNat[0] = rs.getString("DescNat");
           sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.##");
           sMatObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",3);
           bNat = false;
         }
         if (imp.pRow()==0) {
           //imp.say(imp.pRow()+1,0,""+imp.comprimido());
          // imp.say(imp.pRow()+1,0,""+imp.comprimido());
//           imp.say(imp.pRow()+1,0,""+imp.comprimido());
          // imp.say(imp.pRow()+1,0,""+imp.comprimido());
           //imp.say(imp.pRow()+1,0,""+imp.comprimido());
          // imp.say(imp.pRow()+1,0,""+imp.comprimido());
          // imp.say(imp.pRow()+1,0,""+imp.comprimido());
		  // imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,85,"X");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
		   imp.say(imp.pRow()+0,118,sNumNota);
		   imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
         //  imp.say(imp.pRow()+1,0,"");
          // imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,6,sNat[0]);
           imp.say(imp.pRow()+0,50,sNat[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
           imp.say(imp.pRow()+0,85,rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,120,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,6,Funcoes.copy(rs.getString("EndCli"),0,50).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,71,rs.getString("BairCli")!=null ? Funcoes.copy(rs.getString("BairCli"),0,15) : "");
           imp.say(imp.pRow()+0,99,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           imp.say(imp.pRow()+0,120,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           if (rs.getString(8) == null) //Se não tem país:
             imp.say(imp.pRow()+0,6,Funcoes.copy(rs.getString("CidCli"),30));
           else //Se tiver:
             imp.say(imp.pRow()+0,6,rs.getString("CidCli").trim() + "-" + rs.getString("UfCli"));
           imp.say(imp.pRow()+0,48,Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####"));
           if (rs.getString(8) == null) //Se não tem país:
             imp.say(imp.pRow()+0,76,rs.getString("UfCli"));
           else //Se tiver:
             imp.say(imp.pRow()+0,76,Funcoes.copy(rs.getString(8),5));
           imp.say(imp.pRow()+0,85,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           imp.say(imp.pRow()+0,120,sHora);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");

           imp.say(imp.pRow()+1,0,""+imp.comprimido());		
//           imp.say(imp.pRow()+0,8,sDuplics[0]); //Comentado conf. cliente pediu.
//           imp.say(imp.pRow()+0,24,sVencs[0]);             
//           imp.say(imp.pRow()+0,35,sVals[0]);
           
//           imp.say(imp.pRow()+0,52,sDuplics[1]);
//           imp.say(imp.pRow()+0,66,sVencs[1]);             
//           imp.say(imp.pRow()+0,77,sVals[1]);
             
//           imp.say(imp.pRow()+0,94,sDuplics[2]);
//           imp.say(imp.pRow()+0,108,sVencs[2]);             
//           imp.say(imp.pRow()+0,119,sVals[2]);
             
          // imp.say(imp.pRow()+0,110,sDuplics[3]);
          // imp.say(imp.pRow()+0,125,sVencs[3]);             
          // imp.say(imp.pRow()+0,134,sVals[3]);
 
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           //imp.say(imp.pRow()+0,115,sDuplics[4]);
           //imp.say(imp.pRow()+0,125,sVencs[4]);             
           //imp.say(imp.pRow()+0,134,sVals[4]);
		   
		 //  imp.say(imp.pRow()+0,05,rs.getString("CidCob"));	   
		 //  imp.say(imp.pRow()+0,40,rs.getString("EndCob") != null ? rs.getString("EndCob").trim()+", " + (rs.getString("NumCob") != null ? rs.getString("NumCob") : "") : "");
		   
		  // String sValorTotLiqVenda = Extenso.extenso(rs.getDouble("VlrLiqVenda"),"real","reais","centavo","centavos").toUpperCase(); 
		//   imp.say(imp.pRow()+1,3,sValorTotLiqVenda);
           imp.say(imp.pRow()+3,0,"");
         }
         
         if (!rs.getString("TipoProd").equals("S")) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());            
            imp.say(imp.pRow()+0,6,rs.getString("CodProd"));  
            String sDescProdConcatenada = rs.getString("DescProd").trim() + " - " +((rs.getString("DescAuxProd"))!=null?(rs.getString("DescAuxProd")):"");
            Vector vDesc = Funcoes.strToVectorSilabas(rs.getString("ObsItVenda")==null || rs.getString("ObsItVenda").equals("") ? (sDescProdConcatenada):rs.getString("ObsItVenda"),46);
            String sDesc = "";
            for (int iConta=0;( (iConta < 20) && (vDesc.size()>iConta) );iConta++){
            	if (!vDesc.elementAt(iConta).toString().equals(""))
            		sDesc = vDesc.elementAt(iConta).toString();
            	else
            		sDesc = "";
            	if (iConta > 0)
            	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
            	imp.say(imp.pRow()+0,14, sDesc);
                iProd = iProd+vDesc.size();
                
                sMensAdic = rs.getString(5) != null ? rs.getString(5).trim() : "";
            }
            
/*
            String sSigla = "";
            String sCodfisc= rs.getString("CodFisc");
            
            if (sCodfisc == null)
       	      sSigla="";
            sCodfisc = sCodfisc.trim();
            if (sCodfisc.equals("84220202"))
       	      sSigla = "1"; 	   
	        else if (sCodfisc.equals("84220500"))
		      sSigla = "2"; 	   	   
	        else if (sCodfisc.equals("84631700"))
		      sSigla = "3"; 	    	   
	        else if (sCodfisc.equals("84631800"))
		      sSigla = "4"; 	   
            else if (sCodfisc.equals("84229000"))
		      sSigla = "5"; 	   
	        else if (sCodfisc.equals("84220399"))
		      sSigla = "6";
	        else {
              if (!sFiscAdic.equals(""))
           	    JOptionPane.showMessageDialog(null,"Mais de um produto sem classificacao definida,sigla assinalada em branco.");
              else {
                sSigla = " ";
       	        sFiscAdic = sCodfisc ;
       	        
              }
            
            }  
*/                        
            //imp.say(imp.pRow()+0,56,sSigla);
            imp.say(imp.pRow()+0,81,Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2));
            imp.say(imp.pRow()+0,87,rs.getString("CodUnid").substring(0,4));
            imp.say(imp.pRow()+0,92,""+rs.getDouble("QtdItVenda"));
            imp.say(imp.pRow()+0,102,Funcoes.strDecimalToStrCurrency(8,2,""+((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP))));
            imp.say(imp.pRow()+0,113,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
            imp.say(imp.pRow()+0,130,""+rs.getDouble("PercICMSItVenda"));
            //imp.say(imp.pRow()+0,120,""+rs.getDouble("PercIPIItvenda"));
		    //imp.say(imp.pRow()+0,126,Funcoes.strDecimalToStrCurrency(7,2,rs.getString("VlrIPIItvenda")));
         }
        else {
            Vector vDesc = new Vector();
            vDesc.addElement(Funcoes.strToVectorSilabas(rs.getString("ObsItVenda")==null || rs.getString("ObsItVenda").equals("") ? (rs.getString("DescProd").trim()):rs.getString("ObsItVenda"),45)); 
            vDesc.addElement(Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda"))); 
            if (vDesc!=null) {
            	vDescServ.addElement(vDesc.clone());
         	}

         	bigSomaServ = bigSomaServ.add(new BigDecimal(rs.getDouble("VlrLiqItVenda")));
         	
         	iServ = iServ+vDescServ.size();
            
         }
         
         if (!bTotalizou) {
         
           vValores.addElement(rs.getString("VlrBaseICMSVenda")); //0
           vValores.addElement(rs.getString("VlrICMSVenda")); //1
           vValores.addElement(""+
           		rs.getBigDecimal("VlrLiqVenda").subtract(
           				rs.getBigDecimal("VlrFreteVenda")).subtract(
           						rs.getBigDecimal("VlrAdicVenda")).subtract(
           								rs.getBigDecimal("VlrIPIVenda"))); // 2
           //           vValores.addElement(rs.getString("VlrLiqVenda"));//2
           vValores.addElement(rs.getString("VlrFreteVenda"));//3
           vValores.addElement(rs.getString("VlrAdicVenda"));//4
           vValores.addElement(rs.getString("VlrIPIVenda"));//5
           vValores.addElement(rs.getString("VlrLiqVenda"));//6
           vValores.addElement(rs.getString("RazTran"));//7
           vValores.addElement(rs.getString("TipoFreteVD"));//8
           vValores.addElement(rs.getString("PlacaFreteVD"));//9
           vValores.addElement(rs.getString("UfFreteVD"));      //10   
           sTipoTran = rs.getString("TipoTran");
           vValores.addElement(sTipoTran);//11
           vValores.addElement(rs.getString("CnpjCli"));//12
           vValores.addElement(rs.getString("CnpjTran"));   //13         
           vValores.addElement(rs.getString("EndTran")!=null?rs.getString("EndTran"):"");//14
           
           if (sTipoTran.equals("C")){
             vValores.addElement("");//15
             vValores.addElement("");//16
             vValores.addElement("");//17
             vValores.addElement(""); //18
           }
           else {
           	vValores.addElement(rs.getString("NumTran")!=null?rs.getString("NumTran"):"");//15
           	vValores.addElement(rs.getString("CidTran")!=null?rs.getString("CidTran"):"");//16
           	vValores.addElement(rs.getString("UfTran")!=null?rs.getString("UfTran"): "");//17
           	vValores.addElement(rs.getString("InscTran")!=null?rs.getString("InscTran"):""); //18
           }
           
           vValores.addElement(rs.getString("QtdFreteVD"));//19
           vValores.addElement(rs.getString("EspFreteVD"));//20
           vValores.addElement(rs.getString("MarcaFreteVD"));//21
           vValores.addElement(rs.getString("PesoBrutVD"));//22
           vValores.addElement(rs.getString("PesoLiqVD"));//23
           vValores.addElement(rs.getString("VlrIssVenda"));//24
           vValores.addElement(rs.getString("CodVend"));//25
           if (rs.getString("NomeVend")==null)
              vValores.addElement(Funcoes.replicate(" ",25)); // 26
           else 
              vValores.addElement(rs.getString("NomeVend")+Funcoes.replicate(" ",25-rs.getString("NomeVend").length()));
           bTotalizou = true;
         } 
         
      }
      if (imp.pRow()<39) {
      	imp.say(imp.pRow()+1,0,""+imp.comprimido());
      	for (int i=0;i<3;i++) {
      		imp.say(imp.pRow()+1,0,""+imp.comprimido());
      		imp.say(imp.pRow()+0,21,sMatObs[i]);
      	}
      }
     /* if (vDescServ.size()>0) {           
      	impServ(vDescServ,bigSomaServ,vValores.elementAt(24).toString(),imp);                  
      } */     
      impTotais(imp,vValores);
      imp.fechaGravacao();
      bRetorno = true;
      if (iProd>20) {
      	Funcoes.mensagemInforma(null,"Podem haver erros na impressão da nota fiscal."+"\n"+"Produtos ultrapassam vinte linhas!");      	
      }
      /*if (iServ>4) {
      	Funcoes.mensagemInforma(null,"Podem haver erros na impressão da nota fiscal."+"\n"+"Servicos ultrapassam quatro linhas!");
      }*/
    }
    catch ( SQLException err ) {
      Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());      
      bRetorno = false;
    }
    return bRetorno;
  }
  private void impTotais(ImprimeOS imp,Vector vValores){
    try {	
      for (int i=0;(imp.pRow()<40);i++) {
        imp.say(imp.pRow()+1,0,"");
      }
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(0).toString()));
      imp.say(imp.pRow()+0,33,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(1).toString()));
      imp.say(imp.pRow()+0,115,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(2).toString()));
      imp.say(imp.pRow()+2,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,4,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(3).toString()));
      imp.say(imp.pRow()+0,57,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(4).toString()));
      imp.say(imp.pRow()+0,83,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(5).toString()));
      imp.say(imp.pRow()+0,115,Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(6).toString()));
      imp.say(imp.pRow()+3,0,""+imp.comprimido());
      
      imp.say(imp.pRow()+0,6,vValores.elementAt(7).toString());
      imp.say(imp.pRow()+0,83,vValores.elementAt(8).toString().equals("C") ? "1" : "2");
      imp.say(imp.pRow()+0,90,vValores.elementAt(9).toString());
      imp.say(imp.pRow()+0,102,vValores.elementAt(10).toString());
      
      String sTipoTran = vValores.elementAt(11).toString();
      if (sTipoTran==null) sTipoTran = "T";
      
      if ( sTipoTran.equals("C") ){
        imp.say(imp.pRow()+0,113,"");
        
      }
      else {
        imp.say(imp.pRow()+0,113,Funcoes.setMascara(vValores.elementAt(13).toString() != null ? vValores.elementAt(13).toString() : "","##.###.###/####-##")); 
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      // imp.say(imp.pRow()+0,2,Funcoes.copy(vValores.elementAt(11).toString(),0,42)+", "+Funcoes.copy(vValores.elementAt(12).toString(),0,6));
      
      
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,6,vValores.elementAt(14).toString().trim());
      // imp.say(imp.pRow()+0,50,vValores.elementAt(15).toString());
      imp.say(imp.pRow()+0,66,vValores.elementAt(16).toString().trim());
      imp.say(imp.pRow()+0,102,vValores.elementAt(17).toString().trim());
      imp.say(imp.pRow()+0,119,vValores.elementAt(18).toString());
      
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      
      imp.say(imp.pRow()+0,6,vValores.elementAt(19).toString());
      imp.say(imp.pRow()+0,23,vValores.elementAt(20).toString());
      imp.say(imp.pRow()+0,46,vValores.elementAt(21).toString());
      imp.say(imp.pRow()+0,97,vValores.elementAt(22).toString());
      imp.say(imp.pRow()+0,120,vValores.elementAt(23).toString());
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      
      
      String[] sMatObs = Funcoes.strToStrArray(sMensAdic,5);
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,2,sMatObs[0]);
      // imp.say(imp.pRow()+0,55,"-Vend.: "+vValores.elementAt(25).toString());
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,2,sMatObs[1]);
      //imp.say(imp.pRow()+0,55,vValores.elementAt(26).toString().substring(0,20));
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,2,sMatObs[2]);
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,2,sMatObs[3]);
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+0,2,sMatObs[4]);
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      
      //imp.say(imp.pRow()+0,45,"Cod.Vendedor: "+vValores.elementAt(25).toString());
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,1,"");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+1,0,"");
      imp.say(imp.pRow()+1,0,"");
      
      //		imp.say(imp.pRow()+0,124,sNumNota);
      
      imp.setPrc(0,0);
    }
    catch (Exception e) {
      e.printStackTrace();
    }  		  
  }
}


