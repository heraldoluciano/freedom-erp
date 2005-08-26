/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLEditaPag.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLEditaPag extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;
    
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtRazFor = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDescPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK  txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDtEmis = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtVenc = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtVlrJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtVlrAdic = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtObs = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcConta = new ListaCampos(this);
  private ListaCampos lcPlan = new ListaCampos(this);
  private ListaCampos lcCC = new ListaCampos(this);
  public DLEditaPag(Component cOrig) {
  	super(cOrig);
    setTitulo("Editar");
    setAtribos(360,380);

    lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK,false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI,false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.",  ListaCampos.DB_PK, false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do planejamento",  ListaCampos.DB_SI,false));
    lcPlan.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

	lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "Cód.c.c.",  ListaCampos.DB_PK,false));
	lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla",  ListaCampos.DB_SI,false));
	lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descrição",  ListaCampos.DB_SI,false));
	lcCC.add(new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base",  ListaCampos.DB_PK,false));
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

    txtCodFor.setAtivo(false);
    txtRazFor.setAtivo(false);
    txtDescConta.setAtivo(false);
    txtDescPlan.setAtivo(false);
    txtDtEmis.setAtivo(false);
    
    adic(new JLabelPad("Cód.for."),7,0,250,20);
    adic(txtCodFor,7,20,80,20);
    adic(new JLabelPad("Razão social do fornecedor"),90,0,250,20);
    adic(txtRazFor,90,20,250,20);
    adic(new JLabelPad("Nº conta"),7,40,250,20);
    adic(txtCodConta,7,60,80,20);
    adic(new JLabelPad("Descrição da conta"),90,40,250,20);
    adic(txtDescConta,90,60,250,20);
    adic(new JLabelPad("Cód.catg."),7,80,250,20);
    adic(txtCodPlan,7,100,100,20);
    adic(new JLabelPad("Descrição da categoria"),110,80,250,20);
    adic(txtDescPlan,110,100,230,20);
	adic(new JLabelPad("Cód.c.c."),7,120,250,20);
	adic(txtCodCC,7,140,100,20);
	adic(new JLabelPad("Descrição do centro de custo"),110,120,250,20);
	adic(txtDescCC,110,140,230,20);
    adic(new JLabelPad("Doc."),7,160,110,20);
    adic(txtDoc,7,180,110,20);
    adic(new JLabelPad("Emissão"),120,160,107,20);
    adic(txtDtEmis,120,180,107,20);
    adic(new JLabelPad("Vencimento"),230,160,110,20);
    adic(txtDtVenc,230,180,110,20);
    adic(new JLabelPad("Vlr.parc."),7,200,100,20);
    adic(txtVlrParc,7,220,100,20);
    adic(new JLabelPad("Vlr.juros."),110,200,67,20);
    adic(txtVlrJuros,110,220,67,20);
    adic(new JLabelPad("Vlr.desc."),180,200,77,20);
    adic(txtVlrDesc,180,220,77,20);
    adic(new JLabelPad("Vlr.adic."),260,200,80,20);
    adic(txtVlrAdic,260,220,80,20);
    adic(new JLabelPad("Observações"),7,240,200,20);
    adic(txtObs,7,260,333,20);
    
	lcCC.addCarregaListener(this);
  }
  public void setValores(String[] sVals, boolean bLancaUsu) {
    txtCodFor.setVlrString(sVals[0]);
    txtRazFor.setVlrString(sVals[1]);
    txtCodConta.setVlrString(sVals[2]);
    txtCodPlan.setVlrString(sVals[3]);
	txtCodCC.setVlrString(sVals[4]);
    txtDoc.setVlrString(sVals[5]);
    txtDtEmis.setVlrString(sVals[6]);
    txtDtVenc.setVlrString(sVals[7]);
    txtVlrParc.setVlrString(sVals[8]);
    txtVlrJuros.setVlrString(sVals[9]);
    txtVlrDesc.setVlrString(sVals[10]);
    txtVlrAdic.setVlrString(sVals[11]);
	txtObs.setVlrString(sVals[12]);
	txtVlrParc.setAtivo(bLancaUsu);
  }
  public String[] getValores() {
    String[] sRetorno = new String[10];
    sRetorno[0] = txtCodConta.getVlrString();
    sRetorno[1] = txtCodPlan.getVlrString();
	sRetorno[2] = txtCodCC.getVlrString();
    sRetorno[3] = txtDoc.getVlrString();
    sRetorno[4] = txtVlrParc.getVlrString();
    sRetorno[5] = txtVlrJuros.getVlrString();
    sRetorno[6] = txtVlrAdic.getVlrString();
    sRetorno[7] = txtVlrDesc.getVlrString();
    sRetorno[8] = txtDtVenc.getVlrString();
    sRetorno[9] = txtObs.getVlrString();
    return sRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDtVenc.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Data do vencimento é requerido!");
      }
      else {
        super.actionPerformed(evt);
      }
    }
    else {
      super.actionPerformed(evt);
    }
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
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage(),true,con,err);
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
	super.setConexao(cn);
    lcConta.setConexao(cn);
    lcConta.carregaDados();
    lcPlan.setConexao(cn);
    lcPlan.carregaDados();
	lcCC.setConexao(cn);
	lcCC.carregaDados();
    //txtCodPlan.setBuscaAdic("CodRedPlan",cn);
  }
}
