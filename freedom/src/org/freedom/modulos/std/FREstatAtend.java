/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRResumoDiario.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FREstatAtend extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad();
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private JRadioGroup rgFormato=null;
  private int iLinha = 1;
  private int iCol = 1;
  public FREstatAtend() {
    setTitulo("Resumo Diario");
    setAtribos(80,80,295,200);
   
    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());
    
    adic(new JLabelPad("Periodo:"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("Até:"),140,30,30,20);
    adic(txtDatafim,170,30,100,20);
    
    vLabs.addElement("Detalhado");
    vLabs.addElement("Resumido");
    vVals.addElement("D");
    vVals.addElement("R");
    rgFormato= new JRadioGroup(1,2,vLabs, vVals);
    rgFormato.setVlrString("D");
    
    adic(rgFormato,7,80,265,25);
  }

  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    boolean bFimDia = false;
    String sSQL="";
    
    BigDecimal bTotalDiaVal = new BigDecimal("0");
    BigDecimal bTotalDiaDesc = new BigDecimal("0");
    BigDecimal bTotalDiaLiq = new BigDecimal("0");

    BigDecimal bTotalVal = new BigDecimal("0");
    BigDecimal bTotalDesc = new BigDecimal("0");
    BigDecimal bTotalLiq = new BigDecimal("0");
    
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
    String sDtemitvenda = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    
    imp.setTitulo("Resumo Diário de Vendas");
    if (rgFormato.getVlrString().equals("D")) {
        sSQL = "SELECT V.DTEMITVENDA,V.CODTIPOMOV,V.CODVENDA,V.DOCVENDA,V.SERIE,"+
                  "V.STATUSVENDA,V.DOCVENDA,"+
                  "V.DTEMITVENDA,V.VLRPRODVENDA,V.VLRLIQVENDA,"+
                  "V.CODPLANOPAG,P.DESCPLANOPAG,"+
                  "V.VLRCOMISVENDA,V.VLRDESCITVENDA,"+
                  "V.CODCLI,C.RAZCLI,V.STATUSVENDA"+
                  " FROM VDVENDA V,VDCLIENTE C,FNPLANOPAG P, EQTIPOMOV TM "+
                  "WHERE TM.CODTIPOMOV=V.CODTIPOMOV" +
                  " AND TM.CODEMP=V.CODEMPTM" +
                  " AND TM.CODFILIAL=V.CODFILIALTM" +
                  " AND C.CODCLI=V.CODCLI" +
                  " AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL" +
                  " AND V.DTEMITVENDA BETWEEN ? AND ? AND "+
                  "P.CODPLANOPAG=V.CODPLANOPAG AND V.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " AND V.CODEMP=? AND V.CODFILIAL=?" +
				  " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
                  " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ORDER BY V.DTEMITVENDA,V.DOCVENDA";
    }
    else if (rgFormato.getVlrString().equals("R")){
          sSQL= "SELECT V.DTEMITVENDA,SUM(V.VLRLIQVENDA) FROM VDVENDA V,"+
		        " EQTIPOMOV TM WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.FLAG IN "+
				Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				" AND TM.CODEMP=V.CODEMPTM" +
				" AND TM.CODFILIAL=V.CODFILIALTM" +
				" AND TM.CODTIPOMOV=V.CODTIPOMOV" +
				" AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
				" AND V.CODEMP=? AND V.CODFILIAL=? GROUP BY V.DTEMITVENDA" ;
         System.out.println(sSQL);
    }
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      ps.setInt(3,Aplicativo.iCodEmp);
      ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      if (rgFormato.getVlrString().equals("D")) {
      
         while ( rs.next() ) {
              
           if ((!Funcoes.sqlDateToStrDate(rs.getDate("dtemitvenda")).equals(sDtemitvenda)) & (bFimDia)) {
           	imp.impCab(136, false);
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|");
             imp.say(imp.pRow()+0,61,"Totais do Dia-> "+sDtemitvenda+" |"+
               Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDiaVal)+
               Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDiaDesc)+
               Funcoes.strDecimalToStrCurrency(11,2,""+bTotalDiaLiq));
             imp.say(imp.pRow(),136,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             bTotalDiaVal = new BigDecimal("0");
             bTotalDiaDesc = new BigDecimal("0");
             bTotalDiaLiq = new BigDecimal("0");
             bFimDia = false;
           }

           if (imp.pRow()>=(linPag-1)) {
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             imp.incPags();
             imp.eject();
           }

           if (imp.pRow()==0) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
            imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,5,"RESUMO DIARIO DE VENDAS   -   PERIODO DE :"+sDataini+" Até: "+sDatafim);
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"| Dt. Emissao");
            imp.say(imp.pRow()+0,17,"NF./Ped.");
            imp.say(imp.pRow()+0,31,"Cliente");
            imp.say(imp.pRow()+0,88,"|    Valor   Desconto "+
                 "   Liquido F.Pagto.");
            imp.say(imp.pRow()+0,136,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           }
         
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          if (!Funcoes.sqlDateToStrDate(rs.getDate("dtemitvenda")).equals(sDtemitvenda)) {
             imp.say(imp.pRow()+0,3,Funcoes.sqlDateToStrDate(rs.getDate("dtemitvenda")));
          }

          imp.say(imp.pRow()+0,17,
            rs.getString("StatusVenda").substring(0,1).equals("P") ?
                "P-"+Funcoes.copy(rs.getString("codvenda"),0,6) :
		        "V-"+Funcoes.copy(rs.getString("docvenda"),0,6)
		  );
         
          imp.say(imp.pRow()+0,31,Funcoes.copy(rs.getString("codcli"),0,7)+
               "-"+Funcoes.copy(rs.getString("razcli"),0,49)+"|"+
               Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrprodvenda"))+
               Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrdescitvenda"))+
               Funcoes.strDecimalToStrCurrency(11,2,rs.getString("vlrliqvenda"))+
               " "+Funcoes.copy(rs.getString("descplanopag"),0,15)+"|");
         
          if (rs.getString("VlrProdVenda") != null) {
             bTotalDiaVal = bTotalDiaVal.add(new BigDecimal(rs.getString("VlrProdVenda")));
             bTotalVal = bTotalVal.add(new BigDecimal(rs.getString("VlrProdVenda")));
          }

          if (rs.getString("VlrDescitvenda") != null) {
             bTotalDiaDesc = bTotalDiaDesc.add(new BigDecimal(rs.getString("VlrDescitVenda")));
             bTotalDesc = bTotalDesc.add(new BigDecimal(rs.getString("VlrDescitVenda")));
          }

          if (rs.getString("VlrLiqVenda") != null) {
             bTotalDiaLiq = bTotalDiaLiq.add(new BigDecimal(rs.getString("VlrLiqVenda")));
             bTotalLiq = bTotalLiq.add(new BigDecimal(rs.getString("VlrLiqVenda")));
          }
         
          bFimDia = true;
          sDtemitvenda = Funcoes.sqlDateToStrDate(rs.getDate("Dtemitvenda"));
         
       }

       if (bFimDia) {
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,61,"Totais do Dia-> "+sDtemitvenda+" |"+
            Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDiaVal)+
            Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDiaDesc)+
            Funcoes.strDecimalToStrCurrency(11,2,""+bTotalDiaLiq));
          imp.say(imp.pRow(),136,"|");
       }
      
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow(),0,"|"+Funcoes.replicate("-",134)+"|");
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,"|");
        imp.say(imp.pRow()+0,72,"Totais Geral    |"+
             Funcoes.strDecimalToStrCurrency(10,2,""+bTotalVal)+
             Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDesc)+
             Funcoes.strDecimalToStrCurrency(11,2,""+bTotalLiq));
        imp.say(imp.pRow(),136,"|");

        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      
     }
     else if (rgFormato.getVlrString().equals("R")){
      	iLinha = 1;
      	iCol = 0;
      	while (rs.next()) {
      		if (imp.pRow()>=(linPag-1)) {
      			imp.say(imp.pRow()+1,0,""+imp.comprimido());
      			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      			imp.incPags();
      			imp.eject();
      		}
      		if (imp.pRow()==0) {
      			imp.say(imp.pRow()+1,0,""+imp.comprimido());
      			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
      			imp.say(imp.pRow()+1,0,""+imp.comprimido());
      			imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
      			imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
      			imp.say(imp.pRow()+0,136,"|");
      			imp.say(imp.pRow()+1,0,""+imp.comprimido());
      			imp.say(imp.pRow()+0,0,"|");
      			imp.say(imp.pRow()+0,5,"RESUMO DE TOTAL DE VENDAS - PERIODO DE :"+sDataini+" Até: "+sDatafim);
      			imp.say(imp.pRow()+0,136,"|");
      			imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",134)+"|");
      		    imp.say(imp.pRow()+1,0,""+imp.comprimido());
      			imp.say(imp.pRow()+0,0,"|  Data");
      			imp.say(imp.pRow()+0,14,"        Valor");
      			imp.say(imp.pRow()+0,35,"|  Data");
      			imp.say(imp.pRow()+0,49,"        Valor");
      			imp.say(imp.pRow()+0,70,"|  Data");
      			imp.say(imp.pRow()+0,84,"        Valor");
      			imp.say(imp.pRow()+0,105,"|  Data");
      			imp.say(imp.pRow()+0,119,"       Valor");
      			imp.say(imp.pRow()+0,136,"|");
      			imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",134)+"|");
      			
      			      			
      			
      		}
      	
      		imp.say(imp.pRow()+iLinha,iCol,"|  "+ Funcoes.sqlDateToStrDate(rs.getDate(1)));
      		imp.say(imp.pRow()+0,iCol+14," "+ Funcoes.strDecimalToStrCurrency(15,2,""+rs.getString(2)));
      		if (iCol==0) {
      			iLinha = 0;
      			iCol = 35;
      		}
      		else if (iCol==35)
      			iCol = 70;
      		else if (iCol==70)
      			iCol = 105;
      		else {
      			imp.say(imp.pRow()+0,136,"|");
      			iCol=0;
      			iLinha = 1;
      		}
  
      		if (rs.getString(2) != null) {
      			bTotalDiaLiq = bTotalDiaLiq.add(new BigDecimal(rs.getString(2)));
      			bTotalLiq = bTotalLiq.add(new BigDecimal(rs.getString(2)));
      		}
      		     		
      		
       	 }
      }
      if ( (iCol<105) && (iLinha==0) ) {
        	imp.say(imp.pRow()+0,136,"|");
      }
      imp.say(imp.pRow()+1,0,"+"+Funcoes.replicate("-",134)+"+");
      imp.say(imp.pRow()+1,0,"|");
      imp.say(imp.pRow()+0,88,"| Total Geral do Período   | "+
      			Funcoes.strDecimalToStrCurrency(11,2,""+bTotalLiq));
      imp.say(imp.pRow(),136,"|");
      imp.say(imp.pRow()+1,0,"+"+Funcoes.replicate("-",134)+"+");
    
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de vendas!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
