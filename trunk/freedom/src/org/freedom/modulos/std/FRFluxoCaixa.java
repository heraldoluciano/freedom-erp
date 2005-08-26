/**
 * @version 23/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRFluxoCaixa.java <BR>
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
 * Relatório de fluxo de caixa.
 * 
 */

package org.freedom.modulos.std;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRFluxoCaixa extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JCheckBoxPad cbLanca = new JCheckBoxPad("Lançamentos","S","N");
  private JCheckBoxPad cbReceber = new JCheckBoxPad("Clientes","S","N");
  private JCheckBoxPad cbPagar = new JCheckBoxPad("Fornecedores","S","N");
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private BigDecimal bLRec = new BigDecimal("0");
  private BigDecimal bLPag = new BigDecimal("0");
  private BigDecimal bRec = new BigDecimal("0");
  private BigDecimal bPag = new BigDecimal("0");
  public FRFluxoCaixa() {
    setTitulo("Fluxo de caixa");
    setAtribos(80,80,270,170);
    
    txtDatafim.setRequerido(true);

    adic(new JLabelPad("Data final"),7,5,120,20);
    adic(txtDatafim,7,25,120,20);
    adic(cbLanca,130,25,120,20);
    adic(cbReceber,7,60,120,20);
    adic(cbPagar,130,60,120,20);

    cbLanca.setVlrString("S");
    cbReceber.setVlrString("S");
    cbPagar.setVlrString("S");

  }
  public void imprimir(boolean bVisualizar) {

    bLRec = new BigDecimal("0");
    bLPag = new BigDecimal("0");
    bRec = new BigDecimal("0");
    bPag = new BigDecimal("0");

    if (txtDatafim.getVlrString().equals("")) {
	  Funcoes.mensagemInforma(this,"Data final não informada!");
      return;
    }
    ImprimeOS imp = new ImprimeOS("",con);
    int linPagi = imp.verifLinPag()-1;
    
    imp.montaCab();
    imp.setTitulo("Fluxo de caixa");
    imp.addSubTitulo("FLUXO DE CAIXA - DATA LIMITE: "+txtDatafim.getVlrString());
    imp.impCab(80, true);
   
    imp.say(imp.pRow()+0,0,""+imp.normal());
    imp.say(imp.pRow()+0,0,"|");
    imp.say(imp.pRow()+0,79,"|");
    
    if (cbLanca.getVlrString().equals("S"))
      impLanca(imp,linPagi);
    if (cbReceber.getVlrString().equals("S"))
      impReceber(imp,linPagi);
    if (cbPagar.getVlrString().equals("S"))
      impPagar(imp,linPagi);
    
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow(),0,"|");
    imp.say(imp.pRow(),19,"| RECEITAS");
    imp.say(imp.pRow(),39,"| DESPESAS");
    imp.say(imp.pRow(),59,"| SALDO");
    imp.say(imp.pRow(),79,"|");
    imp.say(imp.pRow()+1,0,"+"+Funcoes.replicate("-",77)+"+");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow(),0,"|");
    imp.say(imp.pRow(),5,"LANÇAMENTOS: ");
    imp.say(imp.pRow(),19,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bLRec));
    imp.say(imp.pRow(),39,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bLPag));
    imp.say(imp.pRow(),59,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bLRec.subtract(bLPag)));
    imp.say(imp.pRow(),79,"|");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow(),0,"|");
    imp.say(imp.pRow(),5,"CLIENTES: ");
    imp.say(imp.pRow(),19,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bRec));
    imp.say(imp.pRow(),39,"| "+Funcoes.strDecimalToStrCurrency(18,2,"0.00"));
    imp.say(imp.pRow(),59,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bRec));
    imp.say(imp.pRow(),79,"|");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow(),0,"|");
    imp.say(imp.pRow(),5,"FORNECEDORES: ");
    imp.say(imp.pRow(),19,"| "+Funcoes.strDecimalToStrCurrency(18,2,"0.00"));
    imp.say(imp.pRow(),39,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bPag));
    imp.say(imp.pRow(),59,"| "+Funcoes.strDecimalToStrCurrency(18,2,""+bPag.negate()));
    imp.say(imp.pRow(),79,"|");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow(),0,"|");
    imp.say(imp.pRow(),20,"| DIFERENCA FINAL -->");
    imp.say(imp.pRow(),59,Funcoes.strDecimalToStrCurrency(20,2,""+bLRec.subtract(bLPag).add(bRec).subtract(bPag)));
    imp.say(imp.pRow(),79,"|");
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");

    imp.eject();
    
    imp.fechaGravacao();
    
    
    if (bVisualizar) {
    	imp.preview(this);
    }
    else {
    	imp.print();
    }
  }
    
  private void impLanca(ImprimeOS imp, int linPag) {
    String sDescplan = "";
    boolean bPrim = true;  
    String sSQL = "SELECT P.CODPLAN,P.DESCPLAN,P.NIVELPLAN,P.TIPOPLAN,"+
                  "(SELECT SUM(SL.VLRSUBLANCA*-1)"+
                  " FROM FNSUBLANCA SL,FNLANCA L " + 
                  "WHERE L.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
                  " AND SUBSTR(SL.CODPLAN,1,STRLEN(RTRIM(P.CODPLAN)))=RTRIM(P.CODPLAN) AND "+
                  "SL.CODLANCA=L.CODLANCA AND "+
                  "SL.DATASUBLANCA <= ? AND "+
                  "SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL "+
                  " AND L.CODEMP=P.CODEMP AND L.CODFILIAL=?)"+
                  " FROM FNPLANEJAMENTO P"+
                  " WHERE P.CODEMP=? AND P.CODFILIAL=?"+
                  " AND P.TIPOPLAN IN ('R','D')"+
                  " ORDER BY P.CODPLAN,P.DESCPLAN,P.NIVELPLAN ";
                  
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
	  ps.setInt(2,ListaCampos.getMasterFilial("FNLANCA"));
	  ps.setInt(3,Aplicativo.iCodEmp);
	  ps.setInt(4,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));	  
	  rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (bPrim) {
           imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow(),0,"|");
           imp.say(imp.pRow(),35, "LANÇAMENTOS");
           imp.say(imp.pRow(),79,"|");
           imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
           imp.say(imp.pRow()+1,0,"| Código Plan.");
           imp.say(imp.pRow(),15, "| Descrição");
           imp.say(imp.pRow(),65, "| Saldo");
           imp.say(imp.pRow(),79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
           bPrim = false;
        }
        
        if (rs.getString(5)!=null) {
		  int iNivel = rs.getString("nivelplan")==null?1:rs.getInt("nivelplan");
          if (!rs.getString(5).equals("0")) {
            int iNivelplan = iNivel==0||iNivel==2?1:iNivel;
            iNivelplan = (iNivelplan-1)*2;
            
            sDescplan = " "+Funcoes.replicate(" ",iNivelplan)+rs.getString("descplan");
            
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow(),0,"|"+Funcoes.copy(rs.getString("codplan"),0,13)+
                "|"+Funcoes.copy(sDescplan,0,49)+
                "|"+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(5))+
                "|");
                
          }
		  if (iNivel==1) {
             if (rs.getString("TipoPlan").equals("R"))
			   bLRec = bLRec.add(new BigDecimal(rs.getString(5)));
             else if (rs.getString("TipoPlan").equals("D"))
			   bLPag = bLPag.add((new BigDecimal(rs.getString(5))).negate());
		  }
        }

        if (imp.pRow() == (linPag-1)) {
          imp.say(imp.pRow()+1,0,""+imp.normal());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
          imp.eject();
          imp.incPags();          
        }
         
       }
      if (!con.getAutoCommit())
      	con.commit();
    }  
    catch ( SQLException err ) {
    	Funcoes.mensagemErro(this,"Erro consulta ao planejamento!\n"+err.getMessage(),true,con,err);
    	err.printStackTrace();
    }
  }
  private void impReceber(ImprimeOS imp, int linPag) {
  	boolean bPrim = true;  
  	String sSQL = "SELECT C.CODCLI,C.RAZCLI,SUM(IR.VLRPARCITREC)"+
  	" FROM FNITRECEBER IR,FNRECEBER R, VDCLIENTE C" + 
  	" WHERE R.FLAG IN "+
  	Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
  	" AND IR.DTVENCITREC <= ? AND IR.CODEMP=R.CODEMP" +
  	" AND IR.CODFILIAL=R.CODFILIAL AND IR.CODREC=R.CODREC" +
  	" AND C.CODCLI=R.CODCLI AND R.CODEMPCL=C.CODEMP" +
  	" AND R.CODFILIALCL=C.CODFILIAL AND C.CODEMP=? AND C.CODFILIAL=?"+
  	" AND NOT R.STATUSREC IN ('RP') "+
  	" GROUP BY C.CODCLI,C.RAZCLI ORDER BY C.RAZCLI";
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		ps = con.prepareStatement(sSQL);
  		ps.setDate(1,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  		ps.setInt(2,Aplicativo.iCodEmp);
  		ps.setInt(3,ListaCampos.getMasterFilial("VDCLIENTE"));
  		rs = ps.executeQuery();
  		while ( rs.next() ) {
  			if (bPrim) {
  				imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow(),0,"|");
  				imp.say(imp.pRow(),36, "CLIENTES");
  				imp.say(imp.pRow(),79,"|");
  				imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
  				imp.say(imp.pRow()+1,0,"| Código Cli.");
  				imp.say(imp.pRow(),15, "| Razão");
  				imp.say(imp.pRow(),65, "| Saldo");
  				imp.say(imp.pRow(),79,"|");
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
  				bPrim = false;
  			}
  			
  			if (rs.getString(3)!=null) {
  				if (!rs.getString(3).equals("0")) {
  					imp.say(imp.pRow()+1,0,""+imp.normal());
  					imp.say(imp.pRow(),0,"|"+Funcoes.copy(rs.getString("codcli"),0,13)+
  							"|"+Funcoes.copy(rs.getString("RazCli"),0,49)+
  							"|"+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(3))+
  					"|");
  				}
  				bRec = bRec.add(new BigDecimal(rs.getString(3)));
  			}

  			if (imp.pRow() == (linPag-1)) {
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
  				imp.eject();
  				imp.incPags();          
  			}
  		}
  		
//      rs.close();
//      ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
//      dl.dispose();
  	}  
  	catch ( SQLException err ) {
  		Funcoes.mensagemErro(this,"Erro consulta ao receber!\n"+err.getMessage(),true,con,err);
  		err.printStackTrace();
  	}
  }
  private void impPagar(ImprimeOS imp, int linPag) {
  	boolean bPrim = true;   
  	String sSQL = "SELECT F.CODFOR,F.RAZFOR,SUM(IP.VLRPARCITPAG)"+
  	" FROM FNITPAGAR IP,FNPAGAR P, CPFORNECED F" + 
  	" WHERE P.FLAG IN "+
  	Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
  	" AND IP.DTVENCITPAG <= ? AND IP.CODEMP=P.CODEMP" +
  	" AND IP.CODFILIAL=P.CODFILIAL AND IP.CODPAG=P.CODPAG" +
  	" AND P.CODFOR=F.CODFOR AND P.CODEMPFR=F.CODEMP" +
  	" AND P.CODFILIALFR=F.CODFILIAL AND F.CODEMP=? AND F.CODFILIAL=?"+
  	" AND NOT P.STATUSPAG IN ('PP') "+
  	" GROUP BY F.CODFOR,F.RAZFOR ORDER BY F.RAZFOR";
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		ps = con.prepareStatement(sSQL);
  		ps.setDate(1,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
  		ps.setInt(2,Aplicativo.iCodEmp);
  		ps.setInt(3,ListaCampos.getMasterFilial("CPFORNECED"));
  		rs = ps.executeQuery();
  		while ( rs.next() ) {
  			if (bPrim) {
  				imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow(),0,"|");
  				imp.say(imp.pRow(),34, "FORNECEDORES");
  				imp.say(imp.pRow(),79,"|");
  				imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
  				imp.say(imp.pRow()+1,0,"| Código For.");
  				imp.say(imp.pRow(),15, "| Razão");
  				imp.say(imp.pRow(),65, "| Saldo");
  				imp.say(imp.pRow(),79,"|");
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
  				bPrim = false;
  			}
  			
  			if (rs.getString(3)!=null) {
  				if (!rs.getString(3).equals("0")) {
  					imp.say(imp.pRow()+1,0,""+imp.normal());
  					imp.say(imp.pRow(),0,"|"+Funcoes.copy(rs.getString("codfor"),0,13)+
  							"|"+Funcoes.copy(rs.getString("RazFor"),0,49)+
  							"|"+Funcoes.strDecimalToStrCurrency(13,2,rs.getString(3))+
  					"|");
  				}
			    bPag = bPag.add(new BigDecimal(rs.getString(3)));
  			}

  			if (imp.pRow() == (linPag-1)) {
  				imp.say(imp.pRow()+1,0,""+imp.normal());
  				imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
  				imp.eject();
  				imp.incPags();          
  			}
  		}
  		
//      rs.close();
//      ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
//      dl.dispose();
  	}  
  	catch ( SQLException err ) {
  		Funcoes.mensagemErro(this,"Erro consulta ao pagar!\n"+err.getMessage(),true,con,err);
  		err.printStackTrace();
  	}
  }
}
