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

import javax.swing.JLabel;

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
  private JTextFieldPad txtCodFor = new JTextFieldPad();
  private JTextFieldPad txtDescFor = new JTextFieldPad();
  private JTextFieldPad txtCodConta = new JTextFieldPad();
  private JTextFieldPad txtDescConta = new JTextFieldPad();
  private JTextFieldPad txtCodPlan = new JTextFieldPad();
  private JTextFieldPad txtDescPlan = new JTextFieldPad();
  private JTextFieldPad txtCodCC = new JTextFieldPad();
  private JTextFieldPad txtAnoCC = new JTextFieldPad();
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK();
  private JTextFieldFK  txtDescCC = new JTextFieldFK();
  private JTextFieldPad txtDoc = new JTextFieldPad();
  private JTextFieldPad txtDtEmis = new JTextFieldPad();
  private JTextFieldPad txtDtVenc = new JTextFieldPad();
  private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtVlrJuros = new JTextFieldPad();
  private JTextFieldPad txtVlrDesc = new JTextFieldPad();
  private JTextFieldPad txtVlrAdic = new JTextFieldPad();
  private JTextFieldPad txtObs = new JTextFieldPad();
  private ListaCampos lcConta = new ListaCampos(this);
  private ListaCampos lcPlan = new ListaCampos(this);
  private ListaCampos lcCC = new ListaCampos(this);
  private Connection con = null;
  public DLEditaPag(Component cOrig) {
  	super(cOrig);
    setTitulo("Editar");
    setAtribos(360,380);
    
    txtDoc.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtVlrDesc.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrJuros.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrAdic.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtDtVenc.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtObs.setTipo(JTextFieldPad.TP_STRING,50,0);
    

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
    lcPlan.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
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

    txtCodFor.setAtivo(false);
    txtDescFor.setAtivo(false);
    txtDescConta.setAtivo(false);
    txtDescPlan.setAtivo(false);
    txtDtEmis.setAtivo(false);
    
    adic(new JLabel("Código e razão do fornecedor"),7,0,250,20);
    adic(txtCodFor,7,20,80,20);
    adic(txtDescFor,90,20,250,20);
    adic(new JLabel("Número e descrição da conta"),7,40,250,20);
    adic(txtCodConta,7,60,80,20);
    adic(txtDescConta,90,60,250,20);
    adic(new JLabel("Código e descrição da categoria"),7,80,250,20);
    adic(txtCodPlan,7,100,100,20);
    adic(txtDescPlan,110,100,230,20);
	adic(new JLabel("Código e descrição do centro de custo"),7,120,250,20);
	adic(txtCodCC,7,140,100,20);
	adic(txtDescCC,110,140,230,20);
    adic(new JLabel("Doc."),7,160,110,20);
    adic(txtDoc,7,180,110,20);
    adic(new JLabel("Emissão"),120,160,107,20);
    adic(txtDtEmis,120,180,107,20);
    adic(new JLabel("Vencimento"),230,160,110,20);
    adic(txtDtVenc,230,180,110,20);
    adic(new JLabel("Vlr. Parc."),7,200,100,20);
    adic(txtVlrParc,7,220,100,20);
    adic(new JLabel("Vlr. Juros."),110,200,67,20);
    adic(txtVlrJuros,110,220,67,20);
    adic(new JLabel("Vlr. Desc."),180,200,77,20);
    adic(txtVlrDesc,180,220,77,20);
    adic(new JLabel("Vlr. Adic."),260,200,80,20);
    adic(txtVlrAdic,260,220,80,20);
    adic(new JLabel("Observações"),7,240,200,20);
    adic(txtObs,7,260,333,20);
    
	lcCC.addCarregaListener(this);
  }
  public void setValores(String[] sVals, boolean bLancaUsu) {
    txtCodFor.setVlrString(sVals[0]);
    txtDescFor.setVlrString(sVals[1]);
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
    lcConta.setConexao(cn);
    lcConta.carregaDados();
    lcPlan.setConexao(cn);
    lcPlan.carregaDados();
	lcCC.setConexao(cn);
	lcCC.carregaDados();
    //txtCodPlan.setBuscaAdic("CodRedPlan",cn);
  }
}
