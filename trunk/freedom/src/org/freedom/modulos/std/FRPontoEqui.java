/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBalancete.java <BR>
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
 * Tela de filtros e geração de relatório financeiro de ponto de equilibrio.
 * 
 */

package org.freedom.modulos.std;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRPontoEqui extends FRelatorio {
  private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0); 
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldFK txtDescConta = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0); 
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);   
  private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
  private ListaCampos lcCC = new ListaCampos(this);
  private ListaCampos lcConta = new ListaCampos(this);
  public FRPontoEqui() {
    setTitulo("Apuração de resultados");
    setAtribos(80,80,330,210);

    lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "Cód.conta", ListaCampos.DB_PK, false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI,false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

	lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "Cód.cc.", ListaCampos.DB_PK, false));
	lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI,false));
	lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descrição do centro de custos", ListaCampos.DB_SI,false));
	lcCC.setReadOnly(true);
	lcCC.montaSql(false, "CC", "FN");
	txtCodCC.setTabelaExterna(lcCC);
	txtCodCC.setFK(true);
	txtCodCC.setNomeCampo("CodCC");
	txtSiglaCC.setListaCampos(lcCC);

    adic(new JLabelPad("Periodo:"),7,5,120,20);
    adic(new JLabelPad("De:"),7,25,30,20);
    adic(txtDataini,40,25,117,20);
    adic(new JLabelPad("Até:"),160,25,22,20);
    adic(txtDatafim,185,25,120,20);
//    adic(new JLabelPad("Nº conta"),7,50,250,20);
//    adic(txtCodConta,7,70,80,20);
//  adic(new JLabelPad("Descrição da conta"),90,50,250,20);
//    adic(txtDescConta,90,70,200,20);
//	adic(new JLabelPad("Cód.cc."),7,90,250,20);
//	adic(txtCodCC,7,110,80,20);
//	adic(new JLabelPad("Descrição do centro de custo"),90,90,250,20);
//	adic(txtDescCC,90,110,200,20);

	Calendar cPeriodo = Calendar.getInstance();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcConta.setConexao(cn);
	lcCC.setConexao(cn);
    lcCC.setWhereAdic("NIVELCC=10 AND ANOCC="+buscaAnoBaseCC());
  }
  private int buscaAnoBaseCC() {
	int iRet = 0;
	String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			iRet = rs.getInt("ANOCENTROCUSTO");
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage());
	}
	return iRet;
  }
  public void imprimir(boolean bVisualizar) {
    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    String sCodConta = txtCodConta.getVlrString();
	String sCodCC = txtCodCC.getVlrString().trim();
	String sCC = "";
    String sConta = "";
    
    BigDecimal bTotal = new BigDecimal("0");
    
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
//    String sDescplan = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    
    imp.setTitulo("Apuração de resultados");
    

    String sSQL = "SELECT p.FINPLAN,SUM(SL.vlrsublanca * -1)" +
				  "FROM FNPLANEJAMENTO P, fnsublanca SL "+
				  "WHERE P.TIPOPLAN IN ('R','D') "+
				  "AND P.CODEMP=? AND P.CODFILIAL=? "+
				  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
				  "AND sl.datasublanca between ? and ? "+		
				  "AND L.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " group BY 1";
    
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      int iParam = 1;
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNSUBLANCA"));
      ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
/*
      if (!sCodConta.trim().equals("")) {
		 ps.setInt(iParam++,ListaCampos.getMasterFilial("FNCONTA"));
		 ps.setString(iParam++,sCodConta);
	  }
	  if (!sCodCC.trim().equals("")) {
		 ps.setInt(iParam++,ListaCampos.getMasterFilial("FNCC"));
		 ps.setString(iParam++,sCodCC);
	  }
*/	  
	  rs = ps.executeQuery();
      imp.limpaPags();
      BigDecimal bigValMaster = null;
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           String sTitulo = "APURAÇÃO DE RESULTADOS DE "+sDataini+" A "+sDatafim;
           imp.say(imp.pRow()+0,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,65,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,(80-sTitulo.length())/2,sTitulo);
           imp.say(imp.pRow()+0,79,"|");


           imp.say(imp.pRow()+1,0,"|"+Funcoes.replicate("-",77)+"|");
           imp.say(imp.pRow()+1,0,"| Código Plan.");
           imp.say(imp.pRow(),15, "| Descrição");
		   imp.say(imp.pRow(),59, "|  %   ");
           imp.say(imp.pRow(),66, "| Valor");
           imp.say(imp.pRow(),79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
        }
        

          	BigDecimal bigBasePerc = null;

				bigBasePerc = new BigDecimal(rs.getString(2));


            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow(),0,"|"+Funcoes.copy(rs.getString(1),0,13)+
			    "|"+(bigBasePerc == null ? "  --  " : Funcoes.strDecimalToStrCurrency(6,2,""+bigBasePerc.multiply(new BigDecimal(100)).divide(bigValMaster,2,BigDecimal.ROUND_HALF_UP)))+ //Não imprime nada se o nivel superior tiver -1.
                "|"+Funcoes.strDecimalToStrCurrency(12,2,rs.getString(4))+
                "|");
                
          }

		  	 bigValMaster = new BigDecimal(rs.getString(4));
			 bTotal = bTotal.add(new BigDecimal(rs.getString(4)));



        if (imp.pRow() == (linPag-1)) {
          imp.say(imp.pRow()+1,0,""+imp.normal());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
          imp.eject();
          imp.incPags();          
        }
         
       

      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow(),0,"|");
      imp.say(imp.pRow(),40,"TOTAL RECEITAS/DESPESAS");
      imp.say(imp.pRow(),66,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bTotal)+"|");
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");

      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consultar as bases financeiras!"+err.getMessage());      
		err.printStackTrace();      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
