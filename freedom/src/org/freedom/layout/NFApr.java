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
    Calendar cHora = Calendar.getInstance();
    boolean bRetorno;
    int iNumNota = 0;
    String sNumNota = ""; 
    int iItImp = 0;
    int iNumPares = 0;
    int iLinPag = imp.verifLinPag("NF");
    String sTipoTran = "";
    boolean bFat = true;
    String[] sNat = new String[2];
    String[] sVencs = new String[5];
    String[] sVals = new String[5];
    String[] sObs = null;
    String sDesc = "";
    String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
    try {
      for (int i=0; i<5; i++) {
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
      Vector vServ = new Vector();
      int iServAtual = 0;
      BigDecimal bigSomaServ = new BigDecimal(0);
      BigDecimal bigSomaProd = new BigDecimal(0);
      while (rs.next()) {
           if (bNat) {
             sNat[0] = rs.getString("DescNat");
             sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.##");
             iNumNota = rs.getInt("DocVenda");
             if (iNumNota==0) {
             	sNumNota = "0000000000";
             }
             else {
             	sNumNota = Funcoes.strZero(""+iNumNota,10);
             }
             
//             System.out.println("num nota: "+iNumNota);
//             System.out.println("rs 2"+rs.getInt(2));
             bNat = false;
           }
         if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,124,sNumNota);
           imp.say(imp.pRow()+1,90,"X");
           imp.say(imp.pRow()+1,24,Funcoes.copy(rs.getString("EndFilial"),0,30).trim()+", "+rs.getString("NumFilial"));
           imp.say(imp.pRow()+1,24,Funcoes.copy(rs.getString("CidFilial"),0,15).trim());
           imp.say(imp.pRow()+0,39,rs.getString("UfFilial"));
           imp.say(imp.pRow()+0,43,"Bairro: "+ Funcoes.copy(rs.getString("BairFilial"), 0,25).trim());
           imp.say(imp.pRow()+0,70,"Cep: "+  Funcoes.setMascara(rs.getString("CepFilial"),"#####-###"));
           imp.say(imp.pRow()+1,24,"Emitente: " +Aplicativo.strUsuario);
           imp.say(imp.pRow()+0,92,Funcoes.setMascara(rs.getString("Cnpjfilial"),"##.###.###/####-##"));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,sNat[0]);
		   imp.say(imp.pRow()+0,47,sNat[1]);
           imp.say(imp.pRow()+0,93,rs.getString("Inscfilial"));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
           imp.say(imp.pRow()+0,92,rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,124,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,80,Funcoes.copy(rs.getString("BairCli"),0,25));
           imp.say(imp.pRow()+0,105,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           imp.say(imp.pRow()+0,124,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("CidCli"));
           imp.say(imp.pRow()+0,67,Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####"));
           imp.say(imp.pRow()+0,83,rs.getString("UfCli"));
           imp.say(imp.pRow()+0,93,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           imp.say(imp.pRow()+0,124,sHora);
           imp.say(imp.pRow()+2,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,"");
         }
         if (!rs.getString("TipoProd").equals("S")) {
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("RefProd"),9));
           imp.say(imp.pRow()+0,11,Funcoes.copy(rs.getString("DescProd"),0,45));
           imp.say(imp.pRow()+0,57,Funcoes.copy(rs.getString("CodBarProd"),0,9)); 
           imp.say(imp.pRow()+0,73,Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2));
           imp.say(imp.pRow()+0,79,rs.getString("CodUnid").substring(0,4));
           imp.say(imp.pRow()+0,85,""+rs.getDouble("QtdItVenda"));
           imp.say(imp.pRow()+0,89,Funcoes.strDecimalToStrCurrency(13,2,""+((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP))));
           imp.say(imp.pRow()+0,104,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
           imp.say(imp.pRow()+0,119,""+rs.getDouble("PercICMSItVenda"));
           imp.say(imp.pRow()+0,124,""+rs.getDouble("PercIPIItVenda"));
           //imp.say(imp.pRow()+0,127,Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrIPIItVenda")));
           
           bigSomaProd = bigSomaProd.add(((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(rs.getDouble("QtdItVenda"))));
		 }
		 
         else{
             Vector vDesc = new Vector();
         	 vDesc = Funcoes.strToVectorSilabas(rs.getString("ObsItVenda")==null || rs.getString("ObsItVenda").equals("") ? (rs.getString("DescProd").trim()):rs.getString("ObsItVenda"),70);
           	 vServ.addElement(new Object[] {vDesc,new BigDecimal(rs.getDouble("VlrLiqItVenda"))});
         } 	
	      
         iItImp++;
         if ((iItImp == rs.getInt(1)) || (imp.pRow() == 32)) {
		   int iRow = imp.pRow();
		   imp.say(imp.pRow()+1,0,"");
		   for (int i=0; i<(33-iRow);i++) {
			   imp.say(imp.pRow()+1,0,"");
		   }
		   bigSomaServ = new BigDecimal(0);
		   int iServ=0;
           while (iServ<3) {
			 if (iServAtual<vServ.size()) {
               Vector vDesc = (Vector)((Object[])vServ.elementAt(iServAtual))[0];
			   for (int j=0;j<vDesc.size() && iServ<3;j++){
					if (!vDesc.elementAt(j).toString().equals(""))
						sDesc = vDesc.elementAt(j).toString();
					else
						sDesc = "";

					imp.say(imp.pRow()+0,4,Funcoes.copy(sDesc,80));
                    imp.say(imp.pRow()+1,0,""+imp.comprimido());
					iServ++;
			   }	
		       bigSomaServ = bigSomaServ.add((BigDecimal)((Object[])vServ.elementAt(iServAtual))[1]);

		       iServAtual++;
			 }
			 else {
               imp.say(imp.pRow()+1,0,""+imp.comprimido());
               iServ++;
			 }
		   }
		   if (iServ==0)
			 imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,"0.0"));
		   else if (iServ>=3)
		     imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,bigSomaServ.setScale(2,BigDecimal.ROUND_HALF_UP)+""));
           if (iItImp == rs.getInt(1)) {
//             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,1,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrBaseICMSVenda")));
             imp.say(imp.pRow()+0,32,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrICMSVenda")));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrProdVenda")));
//             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,bigSomaProd+""));
             imp.say(imp.pRow()+1,0,"");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,2,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrFreteVenda")));
             imp.say(imp.pRow()+0,62,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrAdicVenda")));
             imp.say(imp.pRow()+0,87,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrIPIVenda")));
             imp.say(imp.pRow()+0,116,Funcoes.strDecimalToStrCurrency(20,2,rs.getString("VlrLiqVenda")));
             iItImp = 0;
           }
           else if (iRow == 32) {
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
           imp.say(imp.pRow()+0,108,rs.getString("UfFreteVD"));
           
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
           imp.say(imp.pRow()+0,108,rs.getString("UfTran"));
           
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
           imp.say(imp.pRow()+0,58,rs.getString("MarcaFreteVD"));
           imp.say(imp.pRow()+0,100,rs.getString("PesoBrutVD"));
           imp.say(imp.pRow()+0,124,rs.getString("PesoLiqVD"));
//           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"ENTREGA PREVISTA : "+Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"VENDEDOR : "+rs.getString("NomeVend"));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"FORMA DE PAGAMENTO : "+rs.getString("DescPlanoPag"));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"PEDIDO No.: "+Funcoes.strZero(""+rs.getInt("CodVenda"),8));
//           System.out.println(imp.pRow()+" 2= Lins: "+iLinPag);
	       if (iNumPares>0) {
             imp.say(imp.pRow()+0,2,"Numero de Pares: "+iNumPares);
           }
           else
             imp.say(imp.pRow()+0,2,"");
//           System.out.println(imp.pRow()+" =3 Lins: "+iLinPag);
           sObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",3);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sObs[0].trim());
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sObs[1].trim());
//         imp.say(imp.pRow()+1,0,"");
//         imp.say(imp.pRow()+0,2,sObs[2]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+1,0,"");
		   imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,124,sNumNota);
//           System.out.println(imp.pRow()+" =T Lins: "+iLinPag);
           for (int j=imp.pRow(); j<=iLinPag; j++) { 
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

