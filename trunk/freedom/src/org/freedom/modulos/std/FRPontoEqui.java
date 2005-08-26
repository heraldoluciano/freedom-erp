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
	private static final long serialVersionUID = 1L;

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
    setAtribos(80,80,330,120);

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
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo!\n"+err.getMessage(),true,con,err);
	}
	return iRet;
  }

  public BigDecimal getRecDesp(String sGet) {
  	BigDecimal bRet = new BigDecimal("0");  	
    String sSQL = "SELECT SUM(SL.vlrsublanca * -1)" +
	  "FROM FNPLANEJAMENTO P, fnsublanca SL,FNLANCA L "+
	  "WHERE P.CODEMP=? AND P.CODFILIAL=? "+
	  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
	  "AND L.codemp = P.codemp AND L.codfilial = P.codfilial AND L.codlanca = SL.codLANca "+
	  "AND sl.datasublanca between ? and ? AND TIPOPLAN=?"+
	  "AND L.FLAG IN "+
    Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp);

	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("FNSUBLANCA"));
	    ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
	    ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));		
		ps.setString(5,sGet);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			bRet =  ( (rs.getBigDecimal(1)==null) ? (new BigDecimal(0)) : (rs.getBigDecimal(1).abs()) );
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar os valores de:"+sGet+".\n"+err.getMessage(),true,con,err);
	}	
  	return bRet;
  }

  public BigDecimal getFIN(String sFin) {
  	BigDecimal bFin = new BigDecimal("0");
  	
    String sSQL = "SELECT SUM(SL.vlrsublanca * -1)" +
	  "FROM FNPLANEJAMENTO P, fnsublanca SL,FNLANCA L "+
	  "WHERE P.TIPOPLAN IN ('R','D') "+
	  "AND P.CODEMP=? AND P.CODFILIAL=? "+
	  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
	  "AND L.codemp = P.codemp AND L.codfilial = P.codfilial AND L.codlanca = SL.codLANca "+
	  "AND sl.datasublanca between ? and ? AND P.FINPLAN=? "+
	  "AND L.FLAG IN "+
    Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp);

	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("FNSUBLANCA"));
	    ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
	    ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		ps.setString(5,sFin);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			bFin = ( (rs.getBigDecimal(1)==null) ? (new BigDecimal(0)) : (rs.getBigDecimal(1).abs()) );
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar os valores de:"+sFin+".\n"+err.getMessage(),true,con,err);
	}	
  	return bFin;
  }

  public void imprimir(boolean bVisualizar) {
    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
        
    String sDataini = "";
    String sDatafim = "";
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();

    BigDecimal bdRec = getRecDesp("R");
    
    if (bdRec.equals(new BigDecimal("0"))) {
    	Funcoes.mensagemErro(this,"Não existem valores para o período especificado!");
    	return;
    }
    		
    
    BigDecimal bdRV = getFIN("RV");
    BigDecimal bdER = getFIN("ER");
    BigDecimal bd01 = bdRV.subtract(bdER);
    BigDecimal bdCV = getFIN("CV");
    BigDecimal bd03 = bd01.subtract(bdCV);
    BigDecimal bdDesp = getRecDesp("D");
    BigDecimal bdCF = getFIN("CF");
    BigDecimal bdLO = bd03.subtract(bdCF);
    BigDecimal bdPE = new BigDecimal("0");
    if (!bdCF.equals(new BigDecimal("0")))	   
    	bdPE = (bd03.divide(bdCF,6)).multiply(bd01);
    BigDecimal bdI = getFIN("I");
    BigDecimal bdRF = getFIN("RF");
    BigDecimal bdDF = getFIN("DF");
    BigDecimal bdCS = getFIN("CS");
    BigDecimal bdIR = getFIN("IR");
    
    imp.setTitulo("Apuração de resultados");
    
    /*String sSQLX = "SELECT p.tipoplan,p.FINPLAN,SUM(SL.vlrsublanca * -1)" +
				  "FROM FNPLANEJAMENTO P, fnsublanca SL,FNLANCA L "+
				  "WHERE P.TIPOPLAN IN ('R','D') "+
				  "AND P.CODEMP=? AND P.CODFILIAL=? "+
				  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
				  "AND L.codemp = P.codemp AND L.codfilial = P.codfilial AND L.codlanca = SL.codLANca "+
				  "AND sl.datasublanca between ? and ? "+		
				  "AND L.FLAG IN "+
                  Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+
				  " group BY 1,2 order by 1,2 desc";*/
        
    try {
      imp.limpaPags();

        if (imp.pRow()==0) {
        	imp.montaCab();
        	imp.setTitulo("Apuração de Resultados");
        	imp.addSubTitulo("APURAÇÃO DE RESULTADOS DE "+sDataini+" A "+sDatafim);
        	imp.impCab(80, true);
                      
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",77)+"|");
           imp.say(imp.pRow()+1,0,"|");
           imp.say(imp.pRow(),49,"|    Valor");
           imp.say(imp.pRow(),63,"|       %");
           imp.say(imp.pRow(),79,"|");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
        }
        
        imp.say(imp.pRow()+1,0,"|    Receitas:");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdRec)+" |");		  	 
        imp.say(imp.pRow(),65,"      100,00"+" %|");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|  | Receitas S/V (RV):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdRV)+" |");
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdRV.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|  | Estorno de receitas (ER):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdER)+" |");
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdER.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|01| Receita liquida (RV-ER):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bd01)+" |");
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bd01.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|02| Custos variaveis (CV):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdCV)+" |");
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdCV.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|03| Margem contribuicao (01-02):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bd03)+" |");
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bd03.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
               
        imp.say(imp.pRow()+1,0,"|    Despesas:");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdDesp)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdDesp.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
       
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|04| Custos fixos (CF):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdCF)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdCF.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|05| Lucro operacional (03-04):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdLO)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdLO.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|06| Ponto de equilibrio ((03/04)*01):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdPE)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdPE.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|07| Investimentos (I):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdI)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdI.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|08| Receitas financeiras (RF):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdRF)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdRF.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|09| Lucro operacional (DF):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdDF)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdDF.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|10| Contribuição social (CS):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdCS)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdCS.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");
        imp.say(imp.pRow()+1,0,"|11| IRPJ (IR):");
        imp.say(imp.pRow(),49,"|"+Funcoes.strDecimalToStrCurrency(12,2,""+bdIR)+" |");		  	 
        imp.say(imp.pRow(),65,Funcoes.strDecimalToStrCurrency(12,2,""+bdIR.multiply(new BigDecimal(100)).divide(bdRec,6))+" %|");
        imp.say(imp.pRow()+1,0,"+--+"+Funcoes.replicate("-",44)+"+"+Funcoes.replicate("-",13)+"+"+Funcoes.replicate("-",15)+"+");          
        
        if (imp.pRow() == (linPag-1)) {
          imp.say(imp.pRow()+1,0,""+imp.normal());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",77)+"+");
          imp.eject();
          imp.incPags();          
        }

      imp.eject();
      
      imp.fechaGravacao();
      
      if (!con.getAutoCommit())
      	con.commit();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar as bases financeiras!\n"+err.getMessage(),true,con,err);      
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
