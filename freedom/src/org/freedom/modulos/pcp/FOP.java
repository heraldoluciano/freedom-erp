/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FOP.java <BR>
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
 * Tela de cadastro de emissão de ordens de produção.
 * 
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.freedom.layout.LeiauteGR;
import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FPrincipal;
import org.freedom.telas.FPrinterJob;
import org.freedom.bmps.Icone;
public class FOP extends FDetalhe implements PostListener,CancelListener,InsertListener,ActionListener,CarregaListener { 
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodOP = new JTextFieldPad(8);
  private JTextFieldPad txtCodProdEst = new JTextFieldPad(8);
  private JTextFieldFK txtDescEst = new JTextFieldFK();
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,3);
  private JTextFieldPad txtCodProdDet = new JTextFieldPad(8);
  private JTextFieldPad txtRefProdDet = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescProdDet = new JTextFieldFK();
  private JTextFieldPad txtDtFabProd = new JTextFieldPad();
  private JTextFieldPad txtQtdProdOP = new JTextFieldPad();
  private JTextFieldPad txtDtValidOP = new JTextFieldPad();
  private JTextFieldPad txtSeqItOp = new JTextFieldPad(); 
  private JTextFieldPad txtQtdItOp = new JTextFieldPad();
  private JTextFieldPad txtCodLoteProdDet = new JTextFieldPad();
  private ListaCampos lcProdEst = new ListaCampos(this,"PD");
  private ListaCampos lcProdDetCod = new ListaCampos(this,"PD");
  private ListaCampos lcProdDetRef = new ListaCampos(this,"PD");
  private JButton btFase = new JButton("Fases",Icone.novo("btExecuta.gif"));
  private boolean bPrefs[] = null;
  private FPrincipal fPrim = null;
  private FPrinterJob dl = null;
  public FOP () { }
  private void montaTela() {
/*  	
	pnGImp.removeAll(); //Remove os botões de impressão para adicionar logo embaixo
	pnGImp.setLayout(new GridLayout(1,5)); //redimensiona o painel de impressão
	pnGImp.setPreferredSize(new Dimension( 210, 26));
	pnGImp.add(btPrevimp);
	pnGImp.add(btImp);  	
  */	
  	setTitulo("Cadastro de Ordens de produção");
  	setAtribos( 10, 10, 600, 500);
  	setAltCab(130);
    
  	txtSeqItOp.setAtivo(false);
  	txtQtdItOp.setAtivo(false);
  	txtCodProdDet.setAtivo(false);
  	txtRefProdDet.setAtivo(false);
  	txtCodProdEst.setAtivo(false);
    btFase.setEnabled(false);
  	
  	lcProdEst.add(new GuardaCampo( txtCodProdEst, 7, 100, 80, 20, "Codprod", "Código", true, false, txtDescEst, JTextFieldPad.TP_INTEGER,true));
  	lcProdEst.add(new GuardaCampo( txtDescEst, 7, 100, 80, 20, "DescEst", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false));
    lcProdEst.add(new GuardaCampo( txtQtdEst, 7, 100, 80, 20, "QtdEst", "Qtd.", false, false, null, JTextFieldPad.TP_NUMERIC,false));
  	lcProdEst.montaSql(false, "ESTRUTURA", "PP");    
  	lcProdEst.setQueryCommit(false);
  	lcProdEst.setReadOnly(true);
  	txtCodProdEst.setTabelaExterna(lcProdEst);
  	txtCodProdEst.setNomeCampo("codprod");
	
  	lcProdDetCod.add(new GuardaCampo( txtCodProdDet, 7, 100, 80, 20, "Codprod", "Código", true, false, txtDescProdDet, JTextFieldPad.TP_INTEGER,true));
  	lcProdDetCod.add(new GuardaCampo( txtDescProdDet, 7, 100, 80, 20, "Descprod", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false));
    lcProdDetCod.add(new GuardaCampo( txtRefProdDet, 7, 100, 80, 20, "refprod", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false));
  	lcProdDetCod.montaSql(false, "PRODUTO", "EQ");    
  	lcProdDetCod.setQueryCommit(false);
  	lcProdDetCod.setReadOnly(true);
  	txtCodProdDet.setTabelaExterna(lcProdDetCod);
  	txtCodProdDet.setNomeCampo("codprod");

  	lcProdDetRef.add(new GuardaCampo( txtRefProdDet, 7, 100, 80, 20, "refprod", "Código", true, false, txtDescProdDet, JTextFieldPad.TP_STRING,true),"txtCodProdDet");
  	lcProdDetRef.add(new GuardaCampo( txtDescProdDet, 7, 100, 80, 20, "Descprod", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdDet");
  	lcProdDetRef.montaSql(false, "PRODUTO", "EQ");    
  	lcProdDetRef.setQueryCommit(false);
  	lcProdDetRef.setReadOnly(true);
  	txtRefProdDet.setTabelaExterna(lcProdDetRef);
  	txtRefProdDet.setNomeCampo("refprod");
  	txtRefProdDet.setFK(true);
  	
  	setListaCampos(lcCampos);
  	setPainel( pinCab, pnCliCab);
  	adicCampo(txtCodOP, 7, 20, 70, 20,"CodOP","No. OP.",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);

  	adicCampo(txtCodProdEst, 80, 20, 70, 20,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,null,true);
  	adicDescFK(txtDescEst, 153, 20, 250, 20, "descprod", "e descrição da estrutura", JTextFieldPad.TP_STRING, 50, 0);
  	adicCampo(txtQtdProdOP,406,20,70,20,"qtdprodop","Qtd.",JTextFieldPad.TP_NUMERIC,15,2,false,false,null,true);
  	adicCampo(txtDtFabProd,7,60,100,20,"dtfabrop","Dt. Fabricação",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
  	adicCampo(txtDtValidOP,110,60,100,20,"dtvalidpdop","Dt. Validade",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
    adic(btFase,220,50,100,30);

  	setListaCampos( true, "OP", "PP");
  	lcCampos.setQueryInsert(true);  

  	setAltDet(60);
  	pinDet = new Painel(440,50);
  	setPainel( pinDet, pnDet);
  	setListaCampos(lcDet);
  	setNavegador(navRod);

  	adicCampo(txtSeqItOp,7,20,50,20,"seqitop","Seq.",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
  	if (!bPrefs[0])
  		adicCampo(txtCodProdDet,60,20,70,20,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProdDet,true);
  	else {
  		adic(new JLabel("Referência"),60,0,70,20);
  		adic(txtRefProdDet,60,20,70,20);
  		adicCampoInvisivel(txtCodProdDet,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProdDet,true);
  	}
  	adicDescFK(txtDescProdDet,133,20,250,20,"descprod", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
  	adicCampo(txtCodLoteProdDet,386,20,90,20,"codlote","Lote", JTextFieldPad.TP_STRING,13,0,false,false,null,false);
  	adicCampo(txtQtdItOp,479,20,70,20,"qtditop","Quantidade", JTextFieldPad.TP_DECIMAL,15,3,false,false,null,false);
  	setListaCampos( true, "ITOP", "PP");
  	lcDet.setQueryInsert(false);    

  	navRod.setAtivo(4,false);
  	navRod.setAtivo(5,false);
    
    montaTab();
    
    tab.setTamColuna(150,2);

    lcCampos.addCancelListener(this);
  	lcCampos.addPostListener(this);
    lcCampos.addCarregaListener(this);
  	lcCampos.addInsertListener(this);
    lcProdEst.addCarregaListener(this);
  	
    btFase.addActionListener(this);
    btImp.addActionListener(this);
  	btPrevimp.addActionListener(this);     

  }
  private void abreFase() {
    if (fPrim.temTela("OP x Fases")==false) {
      FOPFase tela = new FOPFase(txtCodOP.getVlrInteger().intValue());
      fPrim.criatela("OP x Fases",tela,con);
      tela.setConexao(con);
    }
  }
  public void setTelaPrim(FPrincipal fP) {
    fPrim = fP;
  }
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);    
	if (evt.getSource() == btImp) 
	  imprimir(false);
	else if (evt.getSource() == btPrevimp) 
	  imprimir(true);
    else if (evt.getSource() == btFase)
      abreFase();
    super.actionPerformed(evt);
  }

  public void beforeCarrega(CarregaEvent cevt) { }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcCampos) {
       btFase.setEnabled(lcCampos.getStatus() == ListaCampos.LCS_SELECT);   
    }
    if (cevt.getListaCampos() == lcProdEst) {
        if (lcCampos.getStatus() == ListaCampos.LCS_INSERT)
        	txtQtdProdOP.setVlrString(txtQtdEst.getVlrString());
    }
  }        
  public void afterPost(PostEvent pevt) { 
  	if (lcCampos.getStatusAnt() == ListaCampos.LCS_INSERT) { 
  	  txtCodProdEst.setAtivo(false);
  	}  
  }
  public void beforeCancel(CancelEvent cevt) {
  	txtCodProdEst.setAtivo(false);
  }
  public void afterInsert(InsertEvent ievt) {
  	txtCodProdEst.setAtivo(true);
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void afterCancel(CancelEvent cevt) { }
  public void execShow(Connection cn) {
  	bPrefs=prefs(cn);
  	montaTela();
  	lcProdEst.setConexao(cn);
  	lcProdDetCod.setConexao(cn);
  	lcProdDetRef.setConexao(cn);
  	super.execShow(cn);
  }
  
  private void imprimir(boolean bVisualizar) {
	Vector vParamOP = new Vector();
  	String sClassOP = "";
	String sSql = "SELECT CLASSOP FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";
	LeiauteGR leiOP = null;
	try {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = con.prepareStatement(sSql);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE5"));
		rs = ps.executeQuery();

		if (rs.next()) {
			if (rs.getString("CLASSOP")!=null){
				sClassOP = rs.getString("CLASSOP").trim();              
			}
		} 
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela SGPREFERE5!\n"+err.getMessage());
	}
	if (sClassOP.trim().equals("")) {          
		Funcoes.mensagemErro(this,"Não existe layout para ordem de produção. \n Cadastre o layout no documento de preferências do módulo de produção \n e tente novamente.");
	}
	else {
	  try {
		leiOP = (LeiauteGR)Class.forName("org.freedom.layout."+sClassOP).newInstance();      
		leiOP.setConexao(con);
		vParamOP.clear();
		vParamOP.addElement(txtCodOP.getText());
		leiOP.setParam(vParamOP);
		if (bVisualizar) { 
		  dl = new FPrinterJob(leiOP,this);
          dl.setVisible(true);
        }
		else
		  leiOP.imprimir(true);
	  }
	  catch (Exception err) {
		Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Ordem de produção!\n"+err.getMessage());
		err.printStackTrace();
	  }
	}
  }
  
  private boolean[] prefs(Connection con) {
  	boolean[] bRetorno = new boolean[1];
   String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";;;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		bRetorno[0] = false;
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
  		rs = ps.executeQuery();
  		if (rs.next()) {
  			bRetorno[0]=rs.getString("UsaRefProd").trim().equals("S");
  		}
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch (SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
  	}
  	return bRetorno;
  }
}
