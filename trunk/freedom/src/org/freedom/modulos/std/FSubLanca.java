/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSubLanca.java <BR>
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;

import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FSubLanca extends FDetalhe implements RadioGroupListener,FocusListener,EditListener,PostListener,DeleteListener,ActionListener {
  private JPanelPad pinCab = new JPanelPad(500,200);
  private JPanelPad pinDet = new JPanelPad(500,100);
  private JTextFieldPad txtCodLanca = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDataLanca = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDocLanca = new JTextFieldPad(JTextFieldPad.TP_STRING,15,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtCodEmpPlan = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodFilialPlan = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtHistLanca = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtHistSubLanca=new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodPlanSub = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK  txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK  txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtVlrLanca = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtCodSubLanca = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0);
  private JTextFieldPad txtVlrAtualLanca = new JTextFieldPad();
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtRazFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextAreaPad txaHistLanca = new JTextAreaPad();
  private JCheckBoxPad cbTransf = new JCheckBoxPad("Sim!","S","N");
  private JScrollPane spnTxa = new JScrollPane(txaHistLanca);
  private JButton btSalvar = new JButton(Icone.novo("btSalvar.gif"));
  private JButton btNovo = new JButton(Icone.novo("btNovo.gif"));
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  private ListaCampos lcCC = new ListaCampos(this,"CC");
  private ListaCampos lcCli = new ListaCampos(this,"CL");
  private ListaCampos lcFor = new ListaCampos(this,"FR");  
  private String sCodLanca = "";
  private String sCodPlan = "";
  private Date dIni = null;
  private Date dFim = null;
  private JRadioGroup rgTipoLanca;
  private JLabel lbRazCli = null;
  private JLabel lbRazFor = null;
  private JLabel lbCodCli = null;
  private JLabel lbCodFor = null;
  public FSubLanca(String sCodL,String sCodP,Date dini, Date dfim) {
     dIni = dini;
     dFim = dfim;     
          
    sCodLanca = sCodL;
    sCodPlan = sCodP;

    pnCab.remove(1); //Remove o navegador do cabeçalho
    
    setTitulo("Sub-Lançamentos");
    setAtribos(20,1,615,480);

	Vector vVals = new Vector();
	vVals.addElement("A");
	vVals.addElement("F");
	vVals.addElement("C");	
	Vector vLabs = new Vector();
	vLabs.addElement("Avulso");
	vLabs.addElement("Fornecedor");
	vLabs.addElement("Cliente");	
	rgTipoLanca = new JRadioGroup(1,3,vLabs,vVals);
	rgTipoLanca.addRadioGroupListener(this);   
		
    txtVlrAtualLanca.setAtivo(false);
    txtVlrAtualLanca.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    
	txtCodPlan.setTipo(JTextFieldPad.TP_STRING,13,0);
    lcPlan.add(new GuardaCampo( txtCodPlanSub, "CodPlan", "Código", ListaCampos.DB_PK, txtDescPlan, false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição", ListaCampos.DB_SI, false));
    lcPlan.setReadOnly(true);
    lcPlan.setQueryCommit(false);
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    txtCodPlanSub.setTabelaExterna(lcPlan);
    txtDescPlan.setListaCampos(lcPlan);

	lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Código", ListaCampos.DB_PK, txtDescPlan, false));
	lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Razão", ListaCampos.DB_SI, false));
	lcCli.setReadOnly(true);
	lcCli.setQueryCommit(false);
	lcCli.montaSql(false, "CLIENTE", "VD");    
	txtCodCli.setTabelaExterna(lcCli);
	txtRazCli.setListaCampos(lcCli);

	lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Código", ListaCampos.DB_PK, txtDescPlan, false));
	lcFor.add(new GuardaCampo( txtRazFor, "RazFor", "Razão", ListaCampos.DB_SI, false));
	lcFor.setReadOnly(true);
	lcFor.setQueryCommit(false);
	lcFor.montaSql(false, "FORNECED", "CP");    
	txtCodFor.setTabelaExterna(lcFor);
	txtRazFor.setListaCampos(lcFor);
    
	lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "Código", ListaCampos.DB_PK, txtDescCC, false));
	lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false));
	lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descrição", ListaCampos.DB_SI, false));
	lcCC.add(new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, txtDescCC, false));
	lcCC.setReadOnly(true);
	lcCC.setQueryCommit(false);
	lcCC.setWhereAdic("NIVELCC=10");
	lcCC.montaSql(false, "CC", "FN");    
	txtCodCC.setTabelaExterna(lcCC);
	txtAnoCC.setTabelaExterna(lcCC);
	txtDescCC.setListaCampos(lcCC);
	txtSiglaCC.setListaCampos(lcCC);

    setAltCab(190);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodLanca, 7, 20, 80, 20,"CodLanca","Nº Lçto.",ListaCampos.DB_PK, true);
	adicCampoInvisivel(txtCodPlan, "CodPlan","Cod. Planejamento",ListaCampos.DB_SI, true);
	adicCampoInvisivel(txtCodEmpPlan, "CodEmpPN","Emp. Planejamento",ListaCampos.DB_SI, false);
	adicCampoInvisivel(txtCodFilialPlan, "CodFilialPN","Filial. Planejamento",ListaCampos.DB_SI, false);
    adicCampo(txtDataLanca, 90, 20, 97, 20,"DataLanca","Data",ListaCampos.DB_SI, true);
    adicCampo(txtDocLanca, 190, 20, 77, 20,"DocLanca","Doc.",ListaCampos.DB_SI, false);
    adicCampo(txtHistLanca, 270, 20, 320, 20,"HistBLanca","Histório Bancário",ListaCampos.DB_SI, true);
    adicDB(cbTransf, 7, 60, 80, 20,"TransfLanca","Transferência", true);
    adic(new JLabel("Vlr. Lançamento"),95,40,100,20);
    adic(txtVlrAtualLanca,95,60,97,20);
    adicDB(rgTipoLanca,210,60,379,28,"tipolanca","Tipo de lançamento", true);
    
    JPanel pnTxa = new JPanel(new GridLayout(1,1));
    pnTxa.add(spnTxa);
    adic(pnTxa,7,95,583,50);
    adicDBLiv(txaHistLanca, "HistLanca", "Histórico descriminado", false);
    adic(btNovo,7,150,30,30);
	adic(btSalvar,37,150,30,30);
    setListaCampos( true, "LANCA", "FN");
    setAltDet(150);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampoInvisivel(txtCodSubLanca, "CodSubLanca","Item",ListaCampos.DB_PK, false);
    adicCampo(txtCodPlanSub, 7, 20, 80, 20,"CodPlan","Código", ListaCampos.DB_FK, txtDescPlan, true);
    adicDescFK(txtDescPlan, 90, 20, 247, 20, "DescPlan", "e desc. do plano de contas");
	adicCampo(txtCodCC, 7, 60, 80, 20,"CodCC","Código", ListaCampos.DB_FK, txtDescCC, false);
	adicCampoInvisivel(txtAnoCC, "AnoCC","Ano-base",ListaCampos.DB_FK, txtDescCC, false);
	adicDescFK(txtDescCC, 90, 60, 197, 20, "DescCC", "e desc. do centro de custo");
    adicCampo(txtVlrLanca,340,20,120,20, "VlrSubLanca","Valor",ListaCampos.DB_SI, true);
	adicCampo(txtHistSubLanca,7,105,454,20,"HistSubLanca","Histórico do Lancamento",ListaCampos.DB_SI, false);


	txtCodCli.setRequerido(true);
    txtCodFor.setRequerido(true);
	txtCodCli.setVisible(false);
	txtRazCli.setVisible(false);
	txtCodFor.setVisible(false);
	txtRazFor.setVisible(false); 
	
	lbCodCli = adicCampo(txtCodCli, 290, 60, 50, 20, "CodCli", "Código", ListaCampos.DB_FK, false);
	lbRazCli = adicDescFK(txtRazCli, 343, 60, 247, 20, "RazCli", "Razão social do cliente"); 

	lbCodFor = adicCampo(txtCodFor, 290, 60, 50, 20, "CodFor", "Código", ListaCampos.DB_FK, false);
	lbRazFor = adicDescFK(txtRazFor, 343, 60, 247, 20, "RazFor", "Razão social do fornecedor"); 

    lbCodCli.setVisible(false);
    lbRazCli.setVisible(false);
    lbCodFor.setVisible(false);
    lbRazFor.setVisible(false);
    
    lcDet.setWhereAdic("CODSUBLANCA > 0");
    setListaCampos( true, "SUBLANCA", "FN");
    montaTab();
    
    tab.setTamColuna(100,1);
    tab.setTamColuna(200,2);

    txtCodPlanSub.addFocusListener(this);    
    lcCampos.addPostListener(this);
	lcCampos.addEditListener(this);
    lcDet.addPostListener(this);
    lcDet.addDeleteListener(this);
    btSalvar.addActionListener(this);
	btNovo.addActionListener(this);
  }
  private void atualizaSaldo() {
    try {
      PreparedStatement ps = con.prepareStatement("SELECT VLRLANCA FROM FNLANCA WHERE CODLANCA=?");
      ps.setInt(1,txtCodLanca.getVlrInteger().intValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next())
        txtVlrAtualLanca.setVlrBigDecimal(new BigDecimal(rs.getString("VlrLanca")));
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao atualizar o saldo!\n"+err.getMessage());
    }
  }
  public void valorAlterado(RadioGroupEvent rgevt) { 

	  if (rgTipoLanca.getVlrString().compareTo("A") == 0) {
	    txtCodCli.setVisible(false);
	    txtRazCli.setVisible(false);
	    txtCodFor.setVisible(false);
	    txtRazFor.setVisible(false);	  

	    lbCodCli.setVisible(false);
	    lbRazCli.setVisible(false);
	    lbCodFor.setVisible(false);
	    lbRazFor.setVisible(false);	  
	  	  
	  }
	  else if (rgTipoLanca.getVlrString().compareTo("F") == 0) {
		  txtCodCli.setVisible(false);
		  txtRazCli.setVisible(false);
		  txtCodFor.setVisible(true);
		  txtRazFor.setVisible(true);
		
		  lbCodCli.setVisible(false);
		  lbRazCli.setVisible(false);
		  lbCodFor.setVisible(true);
		  lbRazFor.setVisible(true);			  
			  
	  }
	  else if (rgTipoLanca.getVlrString().compareTo("C") == 0) {
		  txtCodCli.setVisible(true);
		  txtRazCli.setVisible(true);
		  txtCodFor.setVisible(false);
		  txtRazFor.setVisible(false);	  
		
		  lbCodCli.setVisible(true);
		  lbRazCli.setVisible(true);
		  lbCodFor.setVisible(false);
		  lbRazFor.setVisible(false);

	}

  }

  private void carregar() {
    if (sCodLanca == null) {
      lcCampos.insert(true);
      txtCodPlan.setText(sCodPlan);
	  txtCodEmpPlan.setVlrString(""+Aplicativo.iCodEmp);
	  txtCodFilialPlan.setVlrString(""+ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
	  txtVlrAtualLanca.setVlrBigDecimal(new BigDecimal(0));
      cbTransf.setVlrString("N");
      cbTransf.setEnabled(true);
      txtDataLanca.setVlrDate(dFim);
      txtDataLanca.requestFocus();
    }
    else {
      txtCodLanca.setText(sCodLanca);
      lcCampos.carregaDados();
      lcCampos.edit();
      atualizaSaldo();
      txtDataLanca.requestFocus();
      if (tab.getNumLinhas() > 0){
        cbTransf.setEnabled(false);
      	rgTipoLanca.setAtivo(false);  
      }
      else {
      	rgTipoLanca.setAtivo(true);
      }
    }      
    
  }
  private void novo() {
	fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSED);
	sCodLanca = null;
	carregar();
  }


  private boolean testaData() {
    boolean bRetorno = false;
    if ((txtDataLanca.getVlrDate().after(dFim)) ||
        (txtDataLanca.getVlrDate().before(dIni))) {
      txtDataLanca.requestFocus();

/*	  Será passado NULL para abrir um novo frame não um internalframe,
 *     por que se abrir um internalframe o java se perde com o foco.
 */
	  Funcoes.mensagemInforma(null,"Data não está contida no periodo inicial!");
    }
    else 
      bRetorno = true;
    return bRetorno;
  }
  public String[] getValores() {
    String[] sRetorno = new String[8];
    sRetorno[0] = txtCodLanca.getVlrString().trim();
    sRetorno[1] = txtDataLanca.getVlrString().trim();
    sRetorno[2] = cbTransf.getVlrString().trim();
    sRetorno[3] = txtDocLanca.getVlrString().trim();
    sRetorno[4] = txtVlrAtualLanca.getVlrString().trim();
    sRetorno[5] = txtHistLanca.getVlrString().trim();
    sRetorno[6] = txtCodPlan.getVlrString().trim();
    return sRetorno;
  }
  public void focusGained(FocusEvent fevt) {
    if (fevt.getSource() == txtCodPlanSub) {
      if (((lcCampos.getStatus() == ListaCampos.LCS_EDIT) || 
          (lcCampos.getStatus() == ListaCampos.LCS_INSERT)) &&
          (testaData()))
        lcCampos.post();
      if (cbTransf.getVlrString() == "S") {
        lcPlan.setWhereAdic("NIVELPLAN=6 AND TIPOPLAN IN ('C','B')");
        txtAnoCC.setVlrString("");
        txtCodCC.setAtivo(false);
	  }
      else { 
        lcPlan.setWhereAdic("NIVELPLAN=6 AND TIPOPLAN IN ('D','R')");
		txtAnoCC.setVlrInteger(new Integer(buscaAnoBaseCC()));
		txtCodCC.setAtivo(true);
	  }
      lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    }
  }


  private void testaCodLanca() { //Traz o verdadeiro número do codvenda através do generator do banco
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
  	  ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNLANCA"));
	  ps.setString(3,"LA");
      rs = ps.executeQuery();
      rs.next();
      txtCodLanca.setText(rs.getString(1));
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao confirmar código do lanca!\n"+err.getMessage());
    }
  }
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btNovo) {
	  novo();
	}
    else if (evt.getSource() == btSalvar) {
      if (((lcCampos.getStatus() == ListaCampos.LCS_EDIT) ||
          (lcCampos.getStatus() == ListaCampos.LCS_INSERT)) &&
          (testaData()))
        lcCampos.post();
      if (cbTransf.getVlrString() == "S") {
        lcPlan.setWhereAdic("NIVELPLAN=6 AND TIPOPLAN IN ('C','B')");
		txtAnoCC.setVlrString("");
		txtCodCC.setAtivo(false);
      }
      else {
        lcPlan.setWhereAdic("NIVELPLAN=6 AND TIPOPLAN IN ('D','R')");
		txtAnoCC.setVlrInteger(new Integer(buscaAnoBaseCC()));
		txtCodCC.setAtivo(true);
	  }
      lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    }
    super.actionPerformed(evt);
  }
  public void afterPost(PostEvent pevt) {
    if (pevt.getListaCampos() == lcDet) {
      atualizaSaldo();
      if (tab.getNumLinhas() > 0)
        cbTransf.setEnabled(false);
    }
	else if (pevt.getListaCampos() == lcCampos) {
		btSalvar.setEnabled(false);
	}

	if (tab.getNumLinhas() > 0){
	  cbTransf.setEnabled(false);
	  rgTipoLanca.setAtivo(false);  
	}
	else {
	  cbTransf.setEnabled(true);
	  rgTipoLanca.setAtivo(true);
	}

  }

  public void beforePost(PostEvent pevt) { 
    if (pevt.getListaCampos() == lcCampos) {
      if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
        testaCodLanca();        	
      }      
    }

	if (pevt.getListaCampos() == lcDet) {

		if ( (rgTipoLanca.getVlrString().equals("C")) && (txtCodCli.getVlrString().trim().equals("")) ) {
			Funcoes.mensagemErro(this,"Código do cliente não pode ser nulo!");
			pevt.cancela();        	
		}
		else if ( (rgTipoLanca.getVlrString().equals("F")) && (txtCodFor.getVlrString().trim().equals("")) ) {
			Funcoes.mensagemErro(this,"Código do fornecedor não pode ser nulo!");
			pevt.cancela();        	        	
		}
         
    }
  }
  public void afterEdit(EditEvent eevt) {
	if (eevt.getListaCampos() == lcCampos) {
		btSalvar.setEnabled(true);
	}
  }
  public void afterDelete(DeleteEvent devt) {
    atualizaSaldo();
  }

  public void beforeDelete(DeleteEvent devt) { }
  public void beforeEdit(EditEvent eevt) { }
  public void focusLost(FocusEvent fevt) { }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcPlan.setConexao(cn);
	lcCC.setConexao(cn);
	lcFor.setConexao(cn);
	lcCli.setConexao(cn);

    carregar();
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
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o .\n"+err.getMessage());
  	}
  	return iRet;
  }
  public void edit(EditEvent eevt) { }
}
