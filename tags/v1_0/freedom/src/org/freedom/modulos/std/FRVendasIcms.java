/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasIcms.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JLabel;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FRelatorio;
public class FRVendasIcms extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(); 
  private Connection con = null;
  public FRVendasIcms() {
    setTitulo("Icms sobre Vendas e Compras");
    setAtribos(80,80,300,130);
   
    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());
    adic(new JLabel("Periodo:"),7,5,120,20);
    adic(new JLabel("De:"),7,25,30,20);
    adic(txtDataini,40,25,97,20);
    adic(new JLabel("Até:"),140,25,17,20);
    adic(txtDatafim,160,25,100,20);
    
    
  }
  public void setConexao(Connection cn) {
    con = cn;
  }

  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    
    BigDecimal bVlrLiqVenda = new BigDecimal("0");
    BigDecimal bVlrBaseIcmsVenda = new BigDecimal("0");
    BigDecimal bVlrIcmsVenda = new BigDecimal("0");

    BigDecimal bVlrLiqCompra = new BigDecimal("0");
    BigDecimal bVlrBaseIcmsCompra = new BigDecimal("0");
    BigDecimal bVlrIcmsCompra = new BigDecimal("0");
    
    BigDecimal bVlrIcmsPagar = new BigDecimal("0");
    
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    imp.setTitulo("Icms sobre Vendas");
    
    String sSQL = "SELECT V.DTEMITVENDA,T.ESTIPOMOV,SUM(V.VLRLIQVENDA),"+
                  "SUM(V.VLRBASEICMSVENDA),SUM(V.VLRICMSVENDA) "+
                  "FROM VDVENDA V,EQTIPOMOV T "+
                  "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND "+
                  "V.CODTIPOMOV=T.CODTIPOMOV AND T.FISCALTIPOMOV='S' "+
                  "AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') GROUP BY V.DTEMITVENDA,T.ESTIPOMOV "+
                  "UNION "+
                  "SELECT C.DTENTCOMPRA,T1.ESTIPOMOV,SUM(C.VLRLIQCOMPRA),"+
                  "SUM(C.VLRBASEICMSCOMPRA),SUM(C.VLRICMSCOMPRA) "+
                  "FROM CPCOMPRA C,EQTIPOMOV T1 "+
                  "WHERE C.DTENTCOMPRA BETWEEN ? AND ? AND "+
                  "C.CODTIPOMOV=T1.CODTIPOMOV AND T1.FISCALTIPOMOV='S' "+
                  "GROUP BY C.DTENTCOMPRA,T1.ESTIPOMOV ORDER BY 1,3";

    			  System.out.println(sSQL);
    			
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      while ( rs.next() ) {
              

        if (imp.pRow()>=(linPag-1)) {
             imp.say(imp.pRow()+1,0,""+imp.normal());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
             imp.incPags();
             imp.eject();
        }

        if (imp.pRow()==0) {
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,60,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"| ICMS SOBRE COMPRAS E VENDAS - PERIODO DE :"+sDataini+" Até: "+sDatafim);
           imp.say(imp.pRow()+0,79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"| Dt. Emissao | Tipo | Valor Liq.   | Base Calc.   | Valor Icms");
           imp.say(imp.pRow()+0,79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
         }
         
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,0,"| "+Funcoes.sqlDateToStrDate(rs.getDate(1)));
         imp.say(imp.pRow()+0,15,"| "+Funcoes.copy(rs.getString(2),0,1));
         imp.say(imp.pRow()+0,22,"| "+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(3))+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(4))+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(5)));
         imp.say(imp.pRow(),79,"|");
         
         if (Funcoes.copy(rs.getString(2),0,1).equals("S")) {
            bVlrLiqVenda = bVlrLiqVenda.add(new BigDecimal((rs.getString(3)!=null?rs.getString(3):"0")));
            bVlrBaseIcmsVenda = bVlrBaseIcmsVenda.add(new BigDecimal((rs.getString(4)!=null?rs.getString(4):"0")));
            bVlrIcmsVenda = bVlrIcmsVenda.add(new BigDecimal((rs.getString(5)!=null?rs.getString(5):"0")));
         }
         else {
            bVlrLiqCompra = bVlrLiqCompra.add(new BigDecimal((rs.getString(3)!=null?rs.getString(3):"0")));
            bVlrBaseIcmsCompra = bVlrBaseIcmsCompra.add(new BigDecimal((rs.getString(4)!=null?rs.getString(4):"0")));
            bVlrIcmsCompra = bVlrIcmsCompra.add(new BigDecimal((rs.getString(5)!=null?rs.getString(5):"0")));
         }
         
      }
      
      bVlrIcmsPagar = bVlrIcmsPagar.add(bVlrIcmsVenda);
      bVlrIcmsPagar = bVlrIcmsPagar.add(bVlrIcmsCompra.negate());
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow(),0,"|"+Funcoes.replicate("-",77)+"|");
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,2,"TOTAL COMPRAS");
      imp.say(imp.pRow()+0,22,"| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrLiqCompra)+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrBaseIcmsCompra)+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrIcmsCompra));
      imp.say(imp.pRow(),79,"|");
            
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,2,"TOTAL VENDAS");
      imp.say(imp.pRow()+0,22,"| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrLiqVenda)+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrBaseIcmsVenda)+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrIcmsVenda));
      imp.say(imp.pRow(),79,"|");
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,2,"ICMS A PAGAR");
      imp.say(imp.pRow()+0,22,"| "+Funcoes.replicate(" ",13)+
              "| "+Funcoes.replicate(" ",13)+
              "| "+Funcoes.strDecimalToStrCurrency(13,2,""+bVlrIcmsPagar));
      imp.say(imp.pRow(),79,"|");

      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de preços!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
