/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRExtrato.java <BR>
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

public class FRExtrato extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0); 
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldFK txtDescConta = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0); 
  private ListaCampos lcConta = new ListaCampos(this);
  public FRExtrato() {
    setTitulo("Extrato");
    setAtribos(80,80,330,170);

    txtCodConta.setRequerido(true);
    lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "Cód.conta", ListaCampos.DB_PK, false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

    adic(new JLabelPad("Periodo:"),7,5,120,20);
    adic(new JLabelPad("De:"),7,25,30,20);
    adic(txtDataini,40,25,117,20);
    adic(new JLabelPad("Até:"),160,25,22,20);
    adic(txtDatafim,185,25,120,20);
    adic(new JLabelPad("Nº conta"),7,50,250,20);
    adic(txtCodConta,7,70,80,20);
    adic(new JLabelPad("Descrição da conta"),90,50,250,20);
    adic(txtDescConta,90,70,200,20);

	GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcConta.setConexao(cn);
  }
  public void imprimir(boolean bVisualizar) {
    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }
    else if (txtCodConta.getVlrString().equals("")) {
		Funcoes.mensagemInforma(this,"Número da conta é requerido!");
      return;
    }
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    String sCodConta = txtCodConta.getVlrString();
    boolean bPrim = true;
    
    BigDecimal bTotal = new BigDecimal("0");
    BigDecimal bSaldo = new BigDecimal("0");
    BigDecimal bAnt = buscaSaldoAnt();
        
    String sDataLanca = "";
    
    imp.setTitulo("Extrato Bancário");
    String sSQL = "SELECT S.DATASL,L.HISTBLANCA,L.DOCLANCA," +
	              "SL.VLRSUBLANCA,S.SALDOSL FROM FNSALDOLANCA S," +
				  "FNLANCA L,FNCONTA C, FNSUBLANCA SL WHERE L.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+" "+
                  "AND C.CODEMP=? AND C.CODFILIAL=? " +
	 			  "AND C.NUMCONTA=? " +
	 			  "AND L.CODEMP=? AND L.CODFILIAL=? " +
	 			  "AND L.CODLANCA=SL.CODLANCA " +
	 			  "AND S.CODPLAN=SL.CODPLAN " +
	  			  "AND S.CODEMP=SL.CODEMPPN AND S.CODFILIAL=SL.CODFILIALPN " +
                  "AND SL.DATASUBLANCA BETWEEN ? AND ? " +
                  "AND S.DATASL=SL.DATASUBLANCA " +
	 			  "AND SL.CODPLAN = C.CODPLAN " +
	 			  "AND SL.CODEMPPN = C.CODEMPPN AND SL.CODFILIALPN = C.CODFILIALPN " +
	              "AND SL.CODEMP=? AND SL.CODFILIAL=? " +
	              "ORDER BY S.DATASL,L.CODLANCA;";
	              
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("FNCONTA"));
      ps.setString(3,sCodConta);
	  ps.setInt(4,Aplicativo.iCodEmp);
	  ps.setInt(5,ListaCampos.getMasterFilial("FNLANCA"));
	  ps.setDate(6,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
	  ps.setDate(7,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
	  ps.setInt(8,Aplicativo.iCodEmp);
	  ps.setInt(9,ListaCampos.getMasterFilial("FNSUBLANCA"));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      imp.setTitulo("Extrato Bancário");
  	  imp.addSubTitulo("EXTRATO BANCÁRIO");
  	  String sConta = "CONTA: "+sCodConta+" - "+txtDescConta.getVlrString();
      imp.addSubTitulo(sConta);
      
      while ( rs.next() ) {
        if (!bPrim) {
          if (!(sDataLanca.equals(rs.getString("DataSL")))) {
            imp.say(imp.pRow()+0,103,Funcoes.strDecimalToStrCurrency(15,2,""+bSaldo)+" | ");
            imp.say(imp.pRow()+0,135,"|");
            bTotal = new BigDecimal(rs.getString("SaldoSL"));
          }
          else {
            imp.say(imp.pRow()+0,119,"|");
            imp.say(imp.pRow()+0,135,"|");
          }
        }
        else
  		  bTotal = new BigDecimal(rs.getString("SaldoSL"));
        if (imp.pRow() == linPag) {
          imp.eject();
          imp.incPags();          
        }
        if (imp.pRow()==0) {
        	imp.montaCab(); 
            imp.impCab(136, true);
           
           imp.say(imp.pRow()+0,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Data       |");
           imp.say(imp.pRow()+0,14," Historico                                          |");
           imp.say(imp.pRow()+0,68," Doc           |");
           imp.say(imp.pRow()+0,83," Valor           |");
           imp.say(imp.pRow()+0,102," Saldo           |");
           imp.say(imp.pRow()+0,135,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           if (bPrim) {
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|");
             imp.say(imp.pRow()+0,50," Saldo Anterior: "+Funcoes.strDecimalToStrCurrency(15,2,""+bAnt));
             imp.say(imp.pRow()+0,135,"|");
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           }
         }
         bPrim = false;
         bSaldo = new BigDecimal(rs.getString("SaldoSL"));
         bAnt = bSaldo;
         sDataLanca = rs.getString("DataSL");
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+Funcoes.sqlDateToStrDate(rs.getDate("DataSL")));
         imp.say(imp.pRow()+0,14,"| "+Funcoes.copy(rs.getString("HistBLanca"),0,50));
         imp.say(imp.pRow()+0,67,"| "+Funcoes.copy((rs.getString("DocLanca")!=null?rs.getString("DocLanca"):""),0,11));
         imp.say(imp.pRow()+0,83,"| "+Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrSubLanca"))+" | ");
       }
       imp.say(imp.pRow()+0,102,""+Funcoes.strDecimalToStrCurrency(15,2,""+bSaldo)+" |");
       imp.say(imp.pRow()+0,135,"|");
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"|");
       imp.say(imp.pRow()+0,50," Saldo Atual: "+Funcoes.strDecimalToStrCurrency(15,2,""+bTotal));
       imp.say(imp.pRow()+0,135,"|");
       imp.say(imp.pRow()+1,0,""+imp.comprimido());
       imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
      
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
  private BigDecimal buscaSaldoAnt() {
    BigDecimal bigRetorno = new BigDecimal("0.00");
    String sSQL = "SELECT S.SALDOSL FROM FNSALDOLANCA S,FNCONTA C"+
				  " WHERE C.NUMCONTA=?"+
				  " AND C.CODEMP=? AND C.CODFILIAL=?"+
				  " AND S.CODEMP=C.CODEMPPN AND S.CODFILIAL=C.CODFILIALPN"+
                  " AND S.CODPLAN = C.CODPLAN AND S.DATASL ="+
                  " (SELECT MAX(S1.DATASL) FROM FNSALDOLANCA S1"+
                  " WHERE S1.DATASL < ?"+
                  " AND S1.CODPLAN = S.CODPLAN" +
                  " AND S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL)";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setString(1,txtCodConta.getVlrString());
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNCONTA"));
	  ps.setDate(4,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        bigRetorno = new BigDecimal(rs.getString("SaldoSL"));
      }
      rs.close();
      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar saldo anterior!\n"+err.getMessage(),true,con,err);
    }
    return bigRetorno;
  }
}
