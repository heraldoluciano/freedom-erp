/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFPotencia.java <BR>
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
 * Layout da nota fiscal para a empresa Potência Máxima Ltda.
 */

package org.freedom.layout;
import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.math.BigDecimal;
public class NFPotencia extends Leiaute {
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
    Calendar cHora = Calendar.getInstance();
    boolean bRetorno;
    int iNumNota = 0;
    String sNumNota = ""; 
    int iItImp = 0;
    int iNumPares = 0;
    int iLinPag = imp.verifLinPag("NF");
    boolean bFat = true;
    String[] sNat = new String[2];
    String[] sVencs = new String[5];
    String[] sVals = new String[5];
    String[] sObs = null;
    String sHora = Funcoes.strZero(""+cHora.get(Calendar.HOUR),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.MINUTE),2)+":"+Funcoes.strZero(""+cHora.get(Calendar.SECOND),2);
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
           imp.say(imp.pRow()+0,91,"X");
           imp.say(imp.pRow()+0,126,sNumNota);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,sNat[0]);
           imp.say(imp.pRow()+0,54,sNat[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
           imp.say(imp.pRow()+0,92,rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
           imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("EndCli"),0,30).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
           imp.say(imp.pRow()+0,73,rs.getString("BairCli"));
           imp.say(imp.pRow()+0,103,Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
           imp.say(imp.pRow()+0,126,Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("CidCli"));
           imp.say(imp.pRow()+0,56,Funcoes.setMascara(rs.getString("FoneCli"),"(####)####-####")+" - "+Funcoes.setMascara(rs.getString("FaxCli"),"####-####"));
           imp.say(imp.pRow()+0,86,rs.getString("UfCli"));
           imp.say(imp.pRow()+0,92,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
           imp.say(imp.pRow()+0,126,sHora);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,26,sVencs[0]);
           imp.say(imp.pRow()+0,43,sVencs[1]);
           imp.say(imp.pRow()+0,60,sVencs[2]);
           imp.say(imp.pRow()+0,77,sVencs[3]);
           imp.say(imp.pRow()+0,94,sVencs[4]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,25,sVals[0]);
           imp.say(imp.pRow()+0,42,sVals[1]);
           imp.say(imp.pRow()+0,59,sVals[2]);
           imp.say(imp.pRow()+0,76,sVals[3]);
           imp.say(imp.pRow()+0,93,sVals[4]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,2,rs.getString("RefProd"));
         imp.say(imp.pRow()+0,14,Funcoes.copy(rs.getString("DescProd"),0,45));
         imp.say(imp.pRow()+0,64,Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2));
         imp.say(imp.pRow()+0,70,rs.getString("CodUnid").substring(0,4));
         imp.say(imp.pRow()+0,75,""+rs.getDouble("QtdItVenda"));
         imp.say(imp.pRow()+0,80,Funcoes.strDecimalToStrCurrency(13,2,""+((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP))));
//         imp.say(imp.pRow()+0,97,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrProdItVenda")));
         imp.say(imp.pRow()+0,97,Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
         imp.say(imp.pRow()+0,111,""+rs.getDouble("PercICMSItVenda"));
         imp.say(imp.pRow()+0,117,""+rs.getDouble("PercIPIItVenda"));
         imp.say(imp.pRow()+0,121,Funcoes.strDecimalToStrCurrency(14,2,rs.getString("VlrIPIItVenda")));
	 if (rs.getString("CodUnid").trim().equals("PAR")) {
	   iNumPares += rs.getInt("QtdItVenda");
         }
         iItImp++;
//         System.out.println(imp.pRow()+" = iItImp : "+iItImp);
         if ((iItImp == rs.getInt(1)) || (imp.pRow() == 42)) {
           if (iItImp == rs.getInt(1)) {
             int iRow = imp.pRow();
             for (int i=0; i<(42-iRow);i++) {
                 imp.say(imp.pRow()+1,0,"");
             }
//             System.out.println(imp.pRow()+" = iItImp - 2 : "+iItImp);
             imp.say(imp.pRow()+1,0,"");
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
           else if (imp.pRow() == 42) {
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
           imp.say(imp.pRow()+0,107,rs.getString("UfFreteVD"));
           imp.say(imp.pRow()+0,115,Funcoes.setMascara(rs.getString("CnpjTran") != null ? rs.getString("CnpjTran") : "","##.###.###/####-##"));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,Funcoes.copy(rs.getString("EndTran"),0,42)+", "+Funcoes.copy(rs.getString("NumTran"),0,6));
           imp.say(imp.pRow()+0,76,rs.getString("CidTran"));
           imp.say(imp.pRow()+0,107,rs.getString("UfTran"));
           imp.say(imp.pRow()+0,115,rs.getString("InscTran"));
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,rs.getString("QtdFreteVD"));
           imp.say(imp.pRow()+0,22,rs.getString("EspFreteVD"));
           imp.say(imp.pRow()+0,58,rs.getString("MarcaFreteVD"));
           imp.say(imp.pRow()+0,100,rs.getString("PesoBrutVD"));
           imp.say(imp.pRow()+0,122,rs.getString("PesoLiqVD"));
//           System.out.println(imp.pRow()+" 1= Lins: "+iLinPag);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"ENTREGA PREVISTA : "+Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"VENDEDOR : "+rs.getString("NomeVend"));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"FORMA DE PAGAMENTO : "+rs.getString("DescPlanoPag"));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,2,"PEDIDO No.: "+Funcoes.strZero(""+rs.getInt("CodVenda"),8));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
//           System.out.println(imp.pRow()+" 2= Lins: "+iLinPag);
	   if (iNumPares>0) {
             imp.say(imp.pRow()+0,2,"Numero de Pares: "+iNumPares);
           }
           else
             imp.say(imp.pRow()+0,2,"");
//           System.out.println(imp.pRow()+" =3 Lins: "+iLinPag);
           sObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",3);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sObs[0]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sObs[1]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+0,2,sObs[2]);
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,116,sNumNota);
//           System.out.println(imp.pRow()+" =T Lins: "+iLinPag);
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

