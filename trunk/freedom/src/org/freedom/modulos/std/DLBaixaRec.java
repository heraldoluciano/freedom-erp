/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBaixaRec.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLBaixaRec extends FFDialogo implements CarregaListener, FocusListener, EditListener {
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldPad txtDescCli = new JTextFieldPad();
  private JTextFieldPad txtCodConta = new JTextFieldPad();
  private JTextFieldPad txtDescConta = new JTextFieldPad();
  private JTextFieldPad txtCodPlan = new JTextFieldPad();
  private JTextFieldPad txtDescPlan = new JTextFieldPad();
//  private JTextFieldPad txtCodBanco = new JTextFieldPad();
//  private JTextFieldFK txtDescBanco = new JTextFieldFK();
  private JTextFieldPad txtCodCC = new JTextFieldPad();
  private JTextFieldPad txtAnoCC = new JTextFieldPad();
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK();
  private JTextFieldFK  txtDescCC = new JTextFieldFK();
  private JTextFieldPad txtDoc = new JTextFieldPad();
  private JTextFieldPad txtDtEmis = new JTextFieldPad();
  private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtPercDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtPercJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
  private JTextFieldPad txtVlrJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtVlrAberto = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtVenc = new JTextFieldPad();
  private JTextFieldPad txtDtPagto = new JTextFieldPad();
  private JTextFieldPad txtVlrPago = new JTextFieldPad();
  private JTextFieldPad txtVlr = new JTextFieldPad();
  private JTextFieldPad txtObs = new JTextFieldPad();
  private ListaCampos lcConta = new ListaCampos(this);
  private ListaCampos lcPlan = new ListaCampos(this);
  private ListaCampos lcCC = new ListaCampos(this);
//  private ListaCampos lcBanco = new ListaCampos(this);
  boolean bJurosPosCalc = false;
  private Connection con = null;
  public DLBaixaRec(Component cOrig) {
  	super(cOrig);
    setTitulo("Baixa");
    setAtribos(380,450);
    
    txtDoc.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtDtPagto.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtVlr.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtObs.setTipo(JTextFieldPad.TP_STRING,250,0);
    
    txtCodPlan.setRequerido(true);
    txtCodConta.setRequerido(true);
    txtDoc.setRequerido(true);
    txtDtPagto.setRequerido(true);
    txtVlr.setRequerido(true);
    txtObs.setRequerido(true);

    /*txtCodBanco.setNomeCampo("CodBanco");
    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,3,0);
    txtDescBanco.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcBanco.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBancox");
    lcBanco.add(new GuardaCampo( txtDescBanco, 90, 100, 207, 20, "NomeBanco", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBancox");
    txtDescBanco.setListaCampos(lcBanco);
	txtCodBanco.setFK(true);
    lcBanco.montaSql(false, "BANCO", "FN");
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco); */
    
    txtCodConta.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtDescConta.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcConta.add(new GuardaCampo( txtCodConta, 7, 100, 80, 20, "NumConta", "Nº Conta", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodConta");
    lcConta.add(new GuardaCampo( txtDescConta, 90, 100, 207, 20, "DescConta", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescConta");
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

    txtCodPlan.setTipo(JTextFieldPad.TP_STRING,13,0);
    txtDescPlan.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlan.add(new GuardaCampo( txtCodPlan, 7, 100, 80, 20, "CodPlan", "Cód. Plan.", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlan");
    lcPlan.add(new GuardaCampo( txtDescPlan, 90, 100, 207, 20, "DescPlan", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlan");
    lcPlan.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

	txtCodCC.setTipo(JTextFieldPad.TP_STRING,19,0);
	txtSiglaCC.setTipo(JTextFieldPad.TP_STRING,10,0);    
	txtDescCC.setTipo(JTextFieldPad.TP_STRING,50,0);    
	txtAnoCC.setTipo(JTextFieldPad.TP_INTEGER,4,0);    
	lcCC.add(new GuardaCampo( txtCodCC, 7, 100, 80, 20, "CodCC", "Código", true, false, txtDescCC, JTextFieldPad.TP_STRING,false),"txtCodProdx");
	lcCC.add(new GuardaCampo( txtSiglaCC, 90, 100, 207, 20, "SiglaCC", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcCC.add(new GuardaCampo( txtDescCC, 90, 100, 207, 20, "DescCC", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcCC.add(new GuardaCampo( txtAnoCC, 7, 100, 80, 20, "AnoCC", "Ano-Base", true, false, txtDescCC, JTextFieldPad.TP_INTEGER,false),"txtCodProdx");
	lcCC.setReadOnly(true);
	lcCC.setQueryCommit(false);
	lcCC.setWhereAdic("NIVELCC=10");
	lcCC.montaSql(false, "CC", "FN");    
	txtCodCC.setTabelaExterna(lcCC);
	txtCodCC.setFK(true);
	txtCodCC.setNomeCampo("CodCC");
	txtAnoCC.setTabelaExterna(lcCC);
	txtAnoCC.setFK(true);
	txtAnoCC.setNomeCampo("AnoCC");

    txtCodCli.setAtivo(false);
    txtDescCli.setAtivo(false);
    txtDescConta.setAtivo(false);
    txtDescPlan.setAtivo(false);
    txtDtEmis.setAtivo(false);
    txtDtVenc.setAtivo(false);
    txtVlrAberto.setAtivo(false);
    
    adic(new JLabel("Código e razão do cliente"),7,0,250,20);
    adic(txtCodCli,7,20,80,20);
    adic(txtDescCli,90,20,200,20);
    adic(new JLabel("Número e descrição da conta"),7,40,250,20);
    adic(txtCodConta,7,60,80,20);
    adic(txtDescConta,90,60,200,20);
    adic(new JLabel("Código e descrição da categoria"),7,80,250,20);
    adic(txtCodPlan,7,100,100,20);
    adic(txtDescPlan,110,100,200,20);
	adic(new JLabel("Código e descrição do centro de custo"),7,120,250,20);
	adic(txtCodCC,7,140,100,20);
	adic(txtDescCC,110,140,200,20);
    adic(new JLabel("Doc."),7,160,110,20);
    adic(txtDoc,7,180,110,20);
    adic(new JLabel("Emissão"),120,160,107,20);
    adic(txtDtEmis,120,180,107,20);
    adic(new JLabel("Vencimento"),230,160,110,20);
    adic(txtDtVenc,230,180,110,20);
    adic(new JLabel("% Desc."),7,200,60,20);
    adic(txtPercDesc,7,220,60,20);
    adic(new JLabel("Vlr. Desc."),70,200,107,20);
    adic(txtVlrDesc,70,220,107,20);
//A label vai no set conexao...    
    adic(txtPercJuros,180,220,57,20);
    adic(new JLabel("Vlr. Juros."),240,200,100,20);
    adic(txtVlrJuros,240,220,100,20);
    adic(new JLabel("Vlr. Aberto."),7,240,110,20);
    adic(txtVlrAberto,7,260,110,20);
    adic(new JLabel("Dt. Pagto."),120,240,107,20);
    adic(txtDtPagto,120,260,107,20);
    adic(new JLabel("Vlr. Pago"),230,240,110,20);
    adic(txtVlr,230,260,110,20);
    adic(new JLabel("Observações"),7,280,200,20);
    adic(txtObs,7,300,333,20);
    
	lcCC.addCarregaListener(this);
    txtVlr.addFocusListener(this);
	txtPercDesc.addFocusListener(this);
	txtVlrDesc.addFocusListener(this);
	txtVlrDesc.addEditListener(this);
	txtPercJuros.addFocusListener(this);
	txtVlrJuros.addFocusListener(this);
	txtVlrJuros.addEditListener(this);
  }
  public void setValores(String[] sVals) {
    txtCodCli.setVlrString(sVals[0]);
    txtDescCli.setVlrString(sVals[1]);
    txtCodConta.setVlrString(sVals[2]);
    txtCodPlan.setVlrString(sVals[3]);
    txtDoc.setVlrString(sVals[4]);
    txtDtEmis.setVlrString(sVals[5]);
    txtDtVenc.setVlrString(sVals[6]);
    txtVlrParc.setVlrString(sVals[7]);
    txtVlrDesc.setVlrString(sVals[8]);
    txtVlrJuros.setVlrString(sVals[9]);
    txtVlrAberto.setVlrString(sVals[10]);
    txtDtPagto.setVlrString(sVals[11].equals("") ? Funcoes.dateToStrDate(new Date()) : sVals[11]);
    txtVlrPago.setVlrString(sVals[12]);
	txtCodCC.setVlrString(sVals[13]);
	txtObs.setVlrString(sVals[14]);
  }
  public String[] getValores() {
    String[] sRetorno = new String[9];
    sRetorno[0] = txtCodConta.getVlrString();
    sRetorno[1] = txtCodPlan.getVlrString();
    sRetorno[2] = txtDoc.getVlrString();
    sRetorno[3] = txtDtPagto.getVlrString();
    sRetorno[4] = txtVlr.getVlrString();
    sRetorno[5] = txtVlrDesc.getVlrString();
    sRetorno[6] = txtVlrJuros.getVlrString();
	sRetorno[7] = txtCodCC.getVlrString();
	sRetorno[8] = txtObs.getVlrString();
    return sRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodConta.getVlrString().length() < 1) {
		Funcoes.mensagemInforma(this,"Número da conta é requerido!");
      }
      else if (txtCodPlan.getVlrString().length() < 13) {
		Funcoes.mensagemInforma(this,"Código da categoria é requerido!");
      }
      else if (txtDtPagto.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Data do pagamento é requerido!");
      }
      else if (txtVlr.getVlrString().length() < 4) {
		Funcoes.mensagemInforma(this,"Valor pago é requerido!");
      }
      else {
        super.actionPerformed(evt);
      }
    }
    else {
      super.actionPerformed(evt);
    }
  }
  private void calcDesc() {
  	if (txtPercDesc.getVlrDouble().doubleValue() != 0) {
  		txtVlrDesc.setVlrBigDecimal(
  				txtPercDesc.getVlrBigDecimal().multiply(
  						txtVlrParc.getVlrBigDecimal()).divide(
  								new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)
  		);
  	}
  	atualizaAberto();
  }
  private void atualizaPagto() {
    if (txtVlr.getVlrBigDecimal().compareTo(txtVlrAberto.getVlrBigDecimal()) > 0) {
      txtVlrJuros.setVlrBigDecimal(
        txtVlrJuros.getVlrBigDecimal().add(
          txtVlr.getVlrBigDecimal().subtract(txtVlrAberto.getVlrBigDecimal())
        )
      );
      atualizaAberto();
    }
  }
  private void calcJuros() {
  	if (!bJurosPosCalc) {
        if (txtPercJuros.getVlrDouble().doubleValue() != 0) {
        	txtVlrJuros.setVlrBigDecimal(
  				txtPercJuros.getVlrBigDecimal().multiply(
  						txtVlrParc.getVlrBigDecimal()).divide(
  								new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)
        	);
        }
  	}
    else 
        txtVlrJuros.setVlrBigDecimal(
                txtPercJuros.getVlrBigDecimal().multiply(
                        txtVlrParc.getVlrBigDecimal()).divide(
                                new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).multiply(
                                        new BigDecimal(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date())))
        );
  	atualizaAberto();
  }
  private void atualizaAberto() {
  	 txtVlrAberto.setVlrBigDecimal(
  			txtVlrParc.getVlrBigDecimal().subtract(
  					txtVlrDesc.getVlrBigDecimal()).add(
  							txtVlrJuros.getVlrBigDecimal()).subtract(
  									txtVlrPago.getVlrBigDecimal())
  	 );
  }
  private void aplicaJuros() {
     String sSQL = "SELECT P.CODTBJ,T.TIPOTBJ,SUM(IT.PERCITTBJ) FROM " +
     		               "SGPREFERE1 P, FNTBJUROS T, FNITTBJUROS IT WHERE " +
     		               "T.CODEMP=P.CODEMPTJ AND " +
     		               "T.CODFILIAL=P.CODFILIALTJ AND " +
     		               "T.CODTBJ=P.CODTBJ AND " +
     		               "IT.CODEMP=T.CODEMP AND " +
     		               "IT.CODFILIAL=T.CODFILIAL AND " +
     		               "IT.CODTBJ=T.CODTBJ AND " +
     		               "(IT.ANOITTBJ > ? OR (IT.ANOITTBJ = ? AND IT.MESITTBJ >= ?)) AND " +
     		               "(IT.ANOITTBJ < ? OR (IT.ANOITTBJ = ? AND IT.MESITTBJ <= ?)) AND " +
						   "P.CODEMP=? AND P.CODFILIAL=? " +
						   "GROUP BY P.CODTBJ,T.TIPOTBJ ";
     try {
     	PreparedStatement ps = con.prepareStatement(sSQL);
     	GregorianCalendar cal = new GregorianCalendar();
     	GregorianCalendar calVenc = new GregorianCalendar();
     	calVenc.setTime(txtDtVenc.getVlrDate());
     	ps.setInt(1,calVenc.get(Calendar.YEAR));
     	ps.setInt(2,calVenc.get(Calendar.YEAR));
     	ps.setInt(3,calVenc.get(Calendar.MONTH)+1);
     	ps.setInt(4,cal.get(Calendar.YEAR));
     	ps.setInt(5,cal.get(Calendar.YEAR));
     	ps.setInt(6,cal.get(Calendar.MONTH)+1);
     	ps.setInt(7,Aplicativo.iCodEmp);
     	ps.setInt(8,Aplicativo.iCodFilial);
     	ResultSet rs = ps.executeQuery();
     	if (rs.next()) {
     	  switch(rs.getString("TipoTBJ").toCharArray()[0]) {
     	    case 'D':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	    case 'M':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100*30),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	    case 'B':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100*60),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	    case 'T':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100*90),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	    case 'S':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100*182),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	    case 'A':
     	    	txtVlrJuros.setVlrBigDecimal(
     	    			txtVlrParc.getVlrBigDecimal().multiply(
     	    					new BigDecimal(
     	    							(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*
										rs.getDouble(3)
     	    					)
						).divide(new BigDecimal(100*365),2,BigDecimal.ROUND_HALF_UP)
				);
     	    	break;
     	  }
     	}
     	rs.close();
     	ps.close();
     }
     catch (SQLException err) {
     	Funcoes.mensagemErro(this,"Erro ao buscar juros do sistema!\n"+err.getMessage());
     	err.printStackTrace();
     }
  }
  private boolean jurosPosCalc() {
    boolean bRet = false;
    String sSQL = "SELECT JUROSPOSCALC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            bRet = rs.getString("JUROSPOSCALC") != null && rs.getString("JUROSPOSCALC").equals("S");
        rs.close();
        ps.close();
    }
    catch (SQLException err) {
        Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage());
    }
    return bRet;
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
  public void beforeCarrega(CarregaEvent cevt) {
	if (cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0) {
		txtAnoCC.setVlrInteger(new Integer(buscaAnoBaseCC()));
	}
  }
  public void afterCarrega(CarregaEvent cevt) {
  }
  public void setConexao(Connection cn) {
	con = cn;
    //lcBanco.setConexao(cn);
    lcConta.setConexao(cn);
    lcConta.carregaDados();
    lcPlan.setConexao(cn);
    lcPlan.carregaDados();
	lcCC.setConexao(cn);
	lcCC.carregaDados();
    if (!(bJurosPosCalc = jurosPosCalc()) &&
        txtVlrJuros.getVlrBigDecimal().doubleValue() == 0) {
        adic(new JLabel("% Juros."),180,200,57,20);
        aplicaJuros();
    }
    else
        adic(new JLabel("% Dia."),180,200,57,20);

  }
  public void focusGained(FocusEvent fevt) { }
  public void focusLost(FocusEvent fevt) {
  	if (fevt.getSource() == txtPercDesc)
  		calcDesc();
  	else if (fevt.getSource() == txtPercJuros)
  		calcJuros();
  	else if (fevt.getSource() == txtVlrDesc)
  		atualizaAberto();
  	else if (fevt.getSource() == txtVlrJuros)
  		atualizaAberto();
    else if (fevt.getSource() == txtVlr)
        atualizaPagto();
  }
  public void edit(EditEvent eevt) {
  	if (eevt.getSource() == txtVlrDesc)
  		txtPercDesc.setVlrString("");
  	else if (eevt.getSource() == txtVlrJuros)
  		txtPercJuros.setVlrString("");
  }
  public void beforeEdit(EditEvent eevt) { }
  public void afterEdit(EditEvent eevt) { }
}
