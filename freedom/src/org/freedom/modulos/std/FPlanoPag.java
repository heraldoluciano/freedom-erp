/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPlanoPag.java <BR>
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
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FPlanoPag extends FDetalhe implements CarregaListener, InsertListener, PostListener {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(5);
  private JTextFieldPad txtDescPlanoPag = new JTextFieldPad(40);
  private JTextFieldPad txtNumParc = new JTextFieldPad(5);
  private JTextFieldPad txtNumItemPag = new JTextFieldPad(2);
  private JTextFieldPad txtPercItemPag = new JTextFieldPad(7);
  private JTextFieldPad txtDiasItemPag = new JTextFieldPad(3);
  private JTextFieldPad txtDescItemPag = new JTextFieldPad(3);
  private JTextFieldPad txtNumConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescConta = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcConta = new ListaCampos(this,"CA");
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  private ListaCampos lcCC = new ListaCampos(this,"CC");
  private JCheckBoxPad cbAutoBaixa = new JCheckBoxPad("Gerar lançamento financeiro já quitado?","S","N");
  public FPlanoPag () {
    setTitulo("Cadastro de Planos de Pagamento");
    setAtribos( 50, 50, 700, 350);
    
    lcConta.add(new GuardaCampo( txtNumConta, "NumConta", "N.Conta", ListaCampos.DB_PK, false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição", ListaCampos.DB_SI, false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setQueryCommit(false);
    lcConta.setReadOnly(true);
    txtNumConta.setTabelaExterna(lcConta);
    
    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "C.Plan.", ListaCampos.DB_PK, false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição", ListaCampos.DB_SI, false));
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "C.Plan.", ListaCampos.DB_PK, false));
    lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descrição", ListaCampos.DB_SI, false));
    lcCC.add(new GuardaCampo( txtAnoCC, "AnoCC", "Ano", ListaCampos.DB_PK, false));
	lcCC.setWhereAdic("NIVELCC=10");
    lcCC.montaSql(false, "CC", "FN");
    lcCC.setQueryCommit(false);
    lcCC.setReadOnly(true);
    txtCodCC.setTabelaExterna(lcCC);
    
    
    setAltCab(160);
    pinCab = new Painel();
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodPlanoPag, 7, 20, 70, 20,"CodPlanoPag","Código",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDescPlanoPag, 80, 20, 217, 20,"DescPlanoPag","Descrição",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtNumParc, 300, 20, 67, 20,"ParcPlanoPag","Nº Parcs.",JTextFieldPad.TP_INTEGER,8,0,false,false,null,true);
    adicCampo(txtNumConta, 370, 20, 97, 20,"NumConta","N.Conta",ListaCampos.DB_FK,false);
    adicDescFK(txtDescConta,470,20,200,20,"DescConta","descrição da conta",JTextFieldPad.TP_STRING,50,0);
    adicCampo(txtCodPlan, 7, 60, 100, 20,"CodPlan","Cód. Planejamento",ListaCampos.DB_FK,false);
    adicDescFK(txtDescPlan,110,60,197,20,"DescPlan","descrição do planejamento",JTextFieldPad.TP_STRING,50,0);
    adicCampo(txtCodCC, 310, 60, 97, 20,"CodCC","Centro de Custo",ListaCampos.DB_FK,false);
    adicDescFK(txtDescCC,410,60,200,20,"DescCC","descrição do centro de custo",JTextFieldPad.TP_STRING,50,0);
    adicCampoInvisivel(txtAnoCC, "AnoCC","Ano.C.C.",JTextFieldPad.TP_STRING,4,0,false,false,null,false);
    adicDB(cbAutoBaixa, 7, 80, 300, 20,"AutoBaixaPlanoPag","",JTextFieldPad.TP_STRING,false);
    setListaCampos( true, "PLANOPAG", "FN");
    lcCampos.setQueryInsert(true);    

    setAltDet(60);
    pinDet = new Painel(440,50);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumItemPag, 7, 20, 60, 20,"NroParcPag","Item",JTextFieldPad.TP_INTEGER,2,0,true,false,null,true);
    adicCampo(txtPercItemPag, 70, 20, 97, 20,"PercPag","Percento",JTextFieldPad.TP_DECIMAL,9,6,false,false,null,true);
    adicCampo(txtDiasItemPag, 170, 20, 57, 20,"DiasPag","Dias",JTextFieldPad.TP_INTEGER,3,0,false,false,null,true);
    adicCampo(txtDescItemPag, 230, 20, 143, 20,"DescParcPag","Descrição",JTextFieldPad.TP_STRING,30,0,false,false,null,false);
    setListaCampos( true, "PARCPAG", "FN");
    lcDet.setQueryInsert(false);    

    navRod.setAtivo(4,false);
    navRod.setAtivo(5,false);
    montaTab();
    lcCC.addCarregaListener(this);
    lcCampos.addCarregaListener(this);
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
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
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Planos de Pagamento");
    DLRPlanoPag dl = new DLRPlanoPag();
    dl.show();
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT PLANO.CODPLANOPAG,PLANO.DESCPLANOPAG,PLANO.PARCPLANOPAG,"+
                  "PARC.NROPARCPAG,PARC.PERCPAG,PARC.DIASPAG "+
                  "FROM FNPLANOPAG PLANO,FNPARCPAG PARC "+
                  "WHERE PARC.CODPLANOPAG=PLANO.CODPLANOPAG "+
                  "ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sCodMaster = "";
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      sCodMaster = "";
      while (rs.next()) {
         if (imp.pRow()==0) {
            imp.impCab(80);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,20,"Descrição");
            imp.say(imp.pRow()+0,70,"N. Parcel.");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("*",80));
         }

         if (!rs.getString("CodPlanoPag").equals(sCodMaster)) {
           if (sCodMaster.trim().length()!=0) {
             imp.say(imp.pRow()+1,0,""+imp.normal());
             imp.say(imp.pRow()+0,0,Funcoes.replicate("*",80));
           }
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,2,rs.getString("CodPlanoPag"));
           imp.say(imp.pRow()+0,20,rs.getString("DescPlanoPag"));
           imp.say(imp.pRow()+0,70,rs.getString("ParcPlanoPag"));
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,10,"Item");
           imp.say(imp.pRow()+0,35,"Perc.");
           imp.say(imp.pRow()+0,60,"Dias");
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,10,rs.getString("NroParcPag"));
         imp.say(imp.pRow()+0,35,rs.getString("PercPag"));
         imp.say(imp.pRow()+0,60,rs.getString("DiasPag"));
         
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }

         sCodMaster = rs.getString("CodPlanoPag");
         
      }
      
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de Almoxarifados!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void beforePost(PostEvent pevt) {
  	if (pevt.getListaCampos() == lcCampos) {
  		if (cbAutoBaixa.getVlrString().equals("S") &&
  				(txtNumConta.getVlrString().equals("") ||
  				 txtCodPlan.getVlrString().equals(""))
			)
  		{
  			Funcoes.mensagemInforma(this,"Para quitar os pagamentos é necessário um 'N.Conta' e um 'Cód. Planejamento'!");
  			pevt.cancela();
  		}
  	}
  }
  public void beforeCarrega(CarregaEvent cevt) {
	if (cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0) {
		txtAnoCC.setVlrInteger(new Integer(buscaAnoBaseCC()));
	}
  }
  public void afterCarrega(CarregaEvent cevt) { 
    if (cevt.getListaCampos() == lcCampos && cevt.ok)
      txtNumParc.setEditable(false);
  }
  public void afterInsert(InsertEvent ievt) { 
    txtNumParc.setEditable(true);
  }
  public void execShow(Connection cn) {
    lcConta.setConexao(cn);      
	lcPlan.setConexao(cn);      
	lcCC.setConexao(cn);      
    super.execShow(cn);
  }

}
