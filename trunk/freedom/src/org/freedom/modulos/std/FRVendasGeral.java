/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasGeral.java <BR>
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

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRVendasGeral extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
  
  private ListaCampos lcVend = new ListaCampos(this);
  
  public FRVendasGeral() {
    setTitulo("Vendas em Geral");
    setAtribos(80,80,295,195);
    
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
    

  	lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false));
  	lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
  	lcVend.montaSql(false, "VENDEDOR", "VD");    
  	lcVend.setQueryCommit(false);
  	lcVend.setReadOnly(true);
  	txtCodVend.setNomeCampo("CodVend");
	txtCodVend.setFK(true);
  	txtCodVend.setTabelaExterna(lcVend);
 
  	adic(new JLabelPad("Cód.comiss."),7,60,210,20);
	adic(txtCodVend,7,80,70,20);
	adic(new JLabelPad("Nome do comissionado"),80,60,210,20);
	adic(txtDescVend,80,80,190,20);
    
    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVend.setConexao(con);
  }

  public void imprimir(boolean bVisualizar) {
     
  	 String sWhere = "";
	 String sCab="";
	 
	 if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }
	 
	 if (txtCodVend.getText().trim().length() > 0) {
		sWhere += " AND V.CODVEND = "+txtCodVend.getText().trim();
		sCab = "REPR.: "+txtCodVend.getVlrString()+" - "+txtDescVend.getText().trim();
		sWhere += " AND V.CODEMPVD="+Aplicativo.iCodEmp+" AND V.CODFILIALVD="+lcVend.getCodFilial();
	}  
  	
  	
  	
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.verifLinPag();
    
    String sDataini = "";
    String sDatafim = "";
    String sDatasaidavenda = "";
   
    BigDecimal bTotalVal = new BigDecimal("0");
    BigDecimal bTotalDesc = new BigDecimal("0");
    BigDecimal bTotalLiq = new BigDecimal("0");
    
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    
    String sSQL = "SELECT V.DTSAIDAVENDA,V.CODVENDA,V.SERIE,"+
                  "V.STATUSVENDA,V.DOCVENDA,"+
                  "V.DTEMITVENDA,V.VLRPRODVENDA,V.VLRLIQVENDA,"+
                  "V.CODPLANOPAG,P.DESCPLANOPAG,"+
                  "V.VLRCOMISVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,"+
                  "V.CODCLI,C.RAZCLI "+
                  " FROM VDVENDA V,VDCLIENTE C,FNPLANOPAG P,"+
                  " EQTIPOMOV TM WHERE C.CODCLI=V.CODCLI"+
                  " AND TM.CODTIPOMOV=V.CODTIPOMOV" +
		          " AND TM.CODEMP=V.CODEMPTM" +
		          " AND TM.CODFILIAL=V.CODFILIALTM" +
				  " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
                  " AND V.DTSAIDAVENDA>=? AND V.DTSAIDAVENDA<=? AND "+
                  "P.CODPLANOPAG=V.CODPLANOPAG AND V.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
                  " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C'"+sWhere+"ORDER BY V.DTSAIDAVENDA,V.DOCVENDA ";
                  
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      imp.setTitulo("Relatório de Vendas Geral");
  	  imp.addSubTitulo("RELATORIO DE VENDAS GERAL   -   PERIODO DE :"+sDataini+" Até: "+sDatafim);
      if (sCab.length() > 0) {
    	  	imp.addSubTitulo(sCab);
    	  }
      
      while ( rs.next() ) {
      	if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
            imp.incPags();
            imp.eject();
       }
        if (imp.pRow()==0) {
        	imp.montaCab(); 
        	imp.impCab(136, true);
                     
           imp.say(imp.pRow()+0,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");           
                    
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|Dt.Saida");
           imp.say(imp.pRow()+0,12,"| NF./Ped.");
		   imp.say(imp.pRow()+0,27,"| Cod.Cli.");
           imp.say(imp.pRow()+0,36,"| Cliente");
           imp.say(imp.pRow()+0,80,"|   Valor");
           imp.say(imp.pRow()+0,93,"| Desconto");
           imp.say(imp.pRow()+0,105,"|  Liquido");
           imp.say(imp.pRow()+0,119,"| F.Pagto.");
           imp.say(imp.pRow()+0,135,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
         }
         
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         if (!rs.getString("dtsaidavenda").equals(sDatasaidavenda)) {
            imp.say(imp.pRow()+0,0,"|"+Funcoes.sqlDateToStrDate(rs.getDate("dtsaidavenda")));
         }
         else {
            imp.say(imp.pRow()+0,0,"|");
         }

         if (Funcoes.copy(rs.getString("statusvenda"),0,1)=="P") {
            imp.say(imp.pRow()+0,12,"|"+Funcoes.copy(rs.getString("codvenda"),0,13));
         }
         else {
            imp.say(imp.pRow()+0,12,"| "+Funcoes.copy(rs.getString("docvenda"),0,13));
            imp.say(imp.pRow()+0,26,"| "+Funcoes.copy(rs.getString("CodCli"),0,8));
            imp.say(imp.pRow()+0,36,"| "+Funcoes.copy(rs.getString("razcli"),0,40));
            imp.say(imp.pRow()+0,80,"| "+Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrprodvenda")));
            imp.say(imp.pRow()+0,93,"|"+(rs.getDouble("VlrDescVenda") > 0 ? Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrdescvenda")) : Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrdescitvenda"))));
            imp.say(imp.pRow()+0,105,"| "+Funcoes.strDecimalToStrCurrency(10,2,rs.getString("vlrliqvenda")));
            imp.say(imp.pRow()+0,119,"| "+Funcoes.copy(rs.getString("descplanopag"),0,13));
            imp.say(imp.pRow()+0,135,"|");
           
         }
             
         if (rs.getString("VlrProdVenda") != null) {
         	bTotalVal = bTotalVal.add(new BigDecimal(rs.getString("VlrProdVenda")));
         }
         if (rs.getString("VlrDescvenda") != null) {
         	bTotalDesc = bTotalDesc.add(new BigDecimal(rs.getString("VlrDescVenda")));
         }

         if (rs.getString("VlrLiqVenda") != null) {
         	bTotalLiq = bTotalLiq.add(new BigDecimal(rs.getString("VlrLiqVenda")));
         }
         sDatasaidavenda = rs.getString("dtsaidavenda");
         
        
      }
     
      imp.say(imp.pRow()+1,0,"+"+Funcoes.replicate("=",133)+"+");
      imp.say(imp.pRow()+1,0,"|");
      imp.say(imp.pRow()+0,64," Total Geral    |"+" "+
      		Funcoes.strDecimalToStrCurrency(10,2,"" +bTotalVal)+" |"+""+
			Funcoes.strDecimalToStrCurrency(10,2,""+bTotalDesc)+" |"+
			Funcoes.strDecimalToStrCurrency(11,2,"" +bTotalLiq)+"  |");
      imp.say(imp.pRow()+0,135,"|");
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de preços!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
