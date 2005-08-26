/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRInadimplentes.java <BR>
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRInadimplentes extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
  
  private ListaCampos lcVend = new ListaCampos(this);
  
  public FRInadimplentes() {
    setTitulo("Inadimplentes");
    setAtribos(80,80,308,200);
   
	GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());

    adic(new JLabelPad("Periodo:"),7,5,120,20);
    adic(new JLabelPad("De:"),7,25,30,20);
    adic(txtDataini,40,25,97,20);
    adic(new JLabelPad("Até:"),140,25,17,20);
    adic(txtDatafim,160,25,100,20);
    
  	lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false));
  	lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
  	lcVend.montaSql(false, "VENDEDOR", "VD");    
  	lcVend.setQueryCommit(false);
  	lcVend.setReadOnly(true);
  	txtCodVend.setNomeCampo("CodVend");
	txtCodVend.setFK(true);
  	txtCodVend.setTabelaExterna(lcVend);
 
  	adic(new JLabelPad("Cód.comiss."),7,68,200,20);
	adic(txtCodVend,7,88,70,20);
	adic(new JLabelPad("Nome do comissionado"),80,68,200,20);
	adic(txtDescVend,80,88,199,20);
    
    
    
    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcVend.setConexao(con);
    
  }

  public void imprimir(boolean bVisualizar) {
  	
  	 String sWhere = "";
	 String sCab="";
	
	
	 
	 if (txtCodVend.getText().trim().length() > 0) {
		sWhere += " AND R.CODVEND = "+txtCodVend.getText().trim();
		sCab = "COMISS.: "+txtCodVend.getVlrString()+" - "+txtDescVend.getText().trim();
		sWhere += " AND R.CODEMPVD="+Aplicativo.iCodEmp+" AND R.CODFILIALVD="+lcVend.getCodFilial();
	}
	

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    
    BigDecimal bTotalDev = new BigDecimal("0");
    int iNumLanca = 0;
        
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    String sSQL = "SELECT IT.DTVENCITREC,IT.NPARCITREC,R.CODVENDA,"+
                  "R.CODCLI,C.RAZCLI,IT.VLRPARCITREC,C.FONECLI,C.DDDCLI,"+
                  "IT.DTITREC,(SELECT V.STATUSVENDA FROM VDVENDA V"+
                  " WHERE V.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+" AND V.CODVENDA=R.CODVENDA),"+
                  "R.DOCREC,R.CODREC,R.CODVENDA"+
                  " FROM FNITRECEBER IT,FNRECEBER R,VDCLIENTE C"+
                  " WHERE R.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+" AND IT.DTVENCITREC BETWEEN ? AND ? AND"+
                  " R.CODREC = IT.CODREC AND IT.STATUSITREC='R1' AND"+
                  " C.CODCLI=R.CODCLI AND R.CODEMPCL=C.CODEMP AND R.CODFILIALCL=C.CODFILIAL AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL "+sWhere+" ORDER BY IT.DTVENCITREC,C.RAZCLI";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      while ( rs.next() ) {
      	
		 if (imp.pRow() == linPag) {
	                imp.say(imp.pRow()+1,0,""+imp.comprimido());
	                imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
	                imp.eject();
	        	imp.incPags();
	         }
	         if (imp.pRow()==0) {
	         	imp.montaCab();
	          	imp.setTitulo("Relatório de Inadimplentes");
	          	imp.addSubTitulo("RELATORIO DE INADIMPLENTES   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim);
		          if (sCab.length() > 0) {
		      	  	imp.addSubTitulo(sCab);
		      	  }
	          	imp.impCab(136, true);
	          	          
	          imp.say(imp.pRow()+0,0,""+imp.comprimido());
	          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
	          imp.say(imp.pRow()+1,0,""+imp.comprimido());
	          imp.say(imp.pRow()+0,0,"| Vencto.");
	          imp.say(imp.pRow()+0,13,"|Vlr. da Parc.");
	          imp.say(imp.pRow()+0,27,"|Doc.    ");
	          imp.say(imp.pRow()+0,39,"|N.Lancto");
	          imp.say(imp.pRow()+0,48,"|N.Pedido");
	          imp.say(imp.pRow()+0,57,"|Data Emis.");
	          imp.say(imp.pRow()+0,68,"|Devedor");
	          imp.say(imp.pRow()+0,119,"|Telefone");
	          imp.say(imp.pRow()+0,135,"|");
	          imp.say(imp.pRow()+1,0,""+imp.comprimido());
	          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
	        }
	        imp.say(imp.pRow()+1,0,""+imp.comprimido());
	        imp.say(imp.pRow()+0,0,"|");
	        imp.say(imp.pRow()+0,2,Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec"))+"");
	        imp.say(imp.pRow()+0,13,"|"+Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrparcItRec")));
	        imp.say(imp.pRow()+0,27,"|"+(Funcoes.copy(rs.getString(9),0,1).equals("P") ? Funcoes.copy(rs.getString("CodVenda"),0,8) : Funcoes.copy(rs.getString("DocRec"),0,8))+
	             "/"+Funcoes.copy(rs.getString("NParcItRec"),0,2));
	        imp.say(imp.pRow()+0,39,"|"+Funcoes.copy(rs.getString("Codrec"),0,8));
	        imp.say(imp.pRow()+0,48,"|"+Funcoes.copy(rs.getString("Codvenda"),0,8));
	        imp.say(imp.pRow()+0,57,"|"+Funcoes.sqlDateToStrDate(rs.getDate("DtItRec")));
	        imp.say(imp.pRow()+0,68,"|"+Funcoes.copy(rs.getString("CodCli"),0,8)
	            +"-"+Funcoes.copy(rs.getString("RazCli"),0,40));
	        imp.say(imp.pRow()+0,119,"|"+(rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "")+
	  				   (rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : "").trim());
	        imp.say(imp.pRow()+0,135,"|");
	        bTotalDev = bTotalDev.add(new BigDecimal(rs.getString("VlrParcItRec")));
	        iNumLanca++;
	      }
	      imp.say(imp.pRow()+1,0,""+imp.comprimido());
	      imp.say(imp.pRow(),0,"+"+Funcoes.replicate("=",133)+"+");
	      imp.say(imp.pRow()+1,0,""+imp.comprimido());
	      imp.say(imp.pRow()+0,0,"|");
	      imp.say(imp.pRow()+0,40,"Totais Gerais->    Lançamentos: "+Funcoes.strZero(""+iNumLanca,5)+
	        "     Total a Receber: "+Funcoes.strDecimalToStrCurrency(13,2,""+bTotalDev));
	      imp.say(imp.pRow(),135,"|");
	
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
		Funcoes.mensagemErro(this,"Erro consulta ao relatório de inadimplentes!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
