/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCpProd.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FCpProd extends FFilho implements ActionListener,CheckBoxListener {
  private JPanel pnCli = new JPanel(new BorderLayout());
  private JPanel pnRod = new JPanel(new BorderLayout());
  private Painel pinFiltro = new Painel(350,80);
  private Painel pinOrig = new Painel(330,150);
  private Painel pinDest = new Painel(330,150);
  private Painel pinInc = new Painel(500,80);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodClasCli2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTab2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodPlanoPag2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtVlr = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtPerc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,10,2);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescClasCli2 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTab2 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescPlanoPag2 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbPrecoBaseOrig = null;
  private JCheckBoxPad cbPrecoBaseDest = null;
  
  
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgInc = null;
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcClasCli = new ListaCampos(this);
  private ListaCampos lcTab = new ListaCampos(this);
  private ListaCampos lcPlanoPag = new ListaCampos(this);
  private ListaCampos lcClasCli2 = new ListaCampos(this);
  private ListaCampos lcTab2 = new ListaCampos(this);
  private ListaCampos lcPlanoPag2 = new ListaCampos(this);
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btGerar = new JButton("Gerar",Icone.novo("btGerar.gif"));
  public FCpProd() {
    setTitulo("Cópia de preços");
    setAtribos(50,20,680,440);
    
    Funcoes.setBordReq(txtCodGrup);
    Funcoes.setBordReq(txtCodPlanoPag);
    Funcoes.setBordReq(txtCodTab);
    Funcoes.setBordReq(txtCodPlanoPag2);
    Funcoes.setBordReq(txtCodTab2);

    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Decrição do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false,"GRUPO", "EQ");
    lcGrup.setQueryCommit(false);
    lcGrup.setReadOnly(true);
    txtCodGrup.setPK(true);
    txtCodGrup.setListaCampos(lcGrup);
    txtDescGrup.setListaCampos(lcGrup);

    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
    lcPlanoPag.montaSql(false,"PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
	txtCodPlanoPag.setPK(true);
    txtCodPlanoPag.setListaCampos(lcPlanoPag);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);

    txtCodClasCli.setNomeCampo("CodClasCli");
    lcClasCli.add(new GuardaCampo( txtCodClasCli,"CodClasCli", "Cód.c.cli.", ListaCampos.DB_PK, false));
    lcClasCli.add(new GuardaCampo( txtDescClasCli, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
    lcClasCli.montaSql(false,"CLASCLI", "VD");
    lcClasCli.setQueryCommit(false);
    lcClasCli.setReadOnly(true);
	txtCodClasCli.setPK(true);
	txtCodClasCli.setListaCampos(lcClasCli);
    txtDescClasCli.setListaCampos(lcClasCli);

    txtCodTab.setNomeCampo("CodTab");
    lcTab.add(new GuardaCampo( txtCodTab, "CodTab", "Cód.tab,pç.", ListaCampos.DB_PK, false));
    lcTab.add(new GuardaCampo( txtDescTab, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
    lcTab.montaSql(false,"TABPRECO", "VD");
    lcTab.setQueryCommit(false);
    lcTab.setReadOnly(true);
	txtCodTab.setPK(true);
    txtCodTab.setListaCampos(lcTab);
    txtDescTab.setListaCampos(lcTab);

    txtCodPlanoPag2.setNomeCampo("CodPlanoPag");
    lcPlanoPag2.add(new GuardaCampo( txtCodPlanoPag2, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
    lcPlanoPag2.add(new GuardaCampo( txtDescPlanoPag2, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
    lcPlanoPag2.montaSql(false,"PLANOPAG", "FN");
    lcPlanoPag2.setQueryCommit(false);
    lcPlanoPag2.setReadOnly(true);
	txtCodPlanoPag2.setPK(true);
    txtCodPlanoPag2.setListaCampos(lcPlanoPag2);
    txtDescPlanoPag2.setListaCampos(lcPlanoPag2);

    txtCodClasCli2.setNomeCampo("CodClasCli");
    lcClasCli2.add(new GuardaCampo( txtCodClasCli2, "CodClasCli", "Cód.c.cli", ListaCampos.DB_PK, false));
    lcClasCli2.add(new GuardaCampo( txtDescClasCli2, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
    lcClasCli2.montaSql(false,"CLASCLI", "VD");
    lcClasCli2.setQueryCommit(false);
    lcClasCli2.setReadOnly(true);
	txtCodClasCli2.setPK(true);
    txtCodClasCli2.setListaCampos(lcClasCli2);
    txtDescClasCli2.setListaCampos(lcClasCli2);

    txtCodTab2.setNomeCampo("CodTab");
    lcTab2.add(new GuardaCampo( txtCodTab2, "CodTab", "Cód.tab.pç.", ListaCampos.DB_PK, false));
    lcTab2.add(new GuardaCampo( txtDescTab2, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
    lcTab2.montaSql(false,"TABPRECO", "VD");
    lcTab2.setQueryCommit(false);
    lcTab2.setReadOnly(true);
	txtCodTab2.setPK(true);
    txtCodTab2.setListaCampos(lcTab2);
    txtDescTab2.setListaCampos(lcTab2);

    
    
    
    cbPrecoBaseOrig = new JCheckBoxPad("Atualizar preço base","S","N");
    cbPrecoBaseOrig.setVlrString("N");
    cbPrecoBaseOrig.addCheckBoxListener(this);
    
    cbPrecoBaseDest= new JCheckBoxPad("Atualizar preço base","S","N");
    cbPrecoBaseDest.setVlrString("N");
    cbPrecoBaseDest.addCheckBoxListener(this);
    
    
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());

    pnRod.setPreferredSize(new Dimension(350,30));

    c.add(pnRod, BorderLayout.SOUTH);
    c.add(pnCli, BorderLayout.CENTER);

    pnCli.add(pinFiltro, BorderLayout.NORTH);
    pnCli.add(pinOrig, BorderLayout.WEST);
    pnCli.add(pinDest, BorderLayout.EAST);
    pnCli.add(pinInc, BorderLayout.SOUTH);

    JLabel lbFaixa = new JLabel("FAIXA DE DADOS");
    lbFaixa.setForeground(new Color(0,130,0));

    JLabel lbOrigem = new JLabel("ORIGEM");
    lbOrigem.setForeground(new Color(150,150,12));

    JLabel lbDestino = new JLabel("DESTINO");
    lbDestino.setForeground(new Color(150,80,10));

    JLabel lbInc = new JLabel("INCREMENTO");
    lbInc.setForeground(new Color(130,0,0));

    pinFiltro.adic(lbFaixa,7,0,270,20);
    pinFiltro.adic(new JLabel("Cód.grupo"),7,30,280,20);
    pinFiltro.adic(txtCodGrup,7,50,80,20);
    pinFiltro.adic(new JLabel("Descrição do grupo"),90,30,280,20);
    pinFiltro.adic(txtDescGrup,90,50,200,20);

    pinOrig.adic(lbOrigem,7,0,270,20);
    pinOrig.adic(new JLabel("Cód.p.pag."),7,30,280,20);
    pinOrig.adic(txtCodPlanoPag,7,50,80,20);
    pinOrig.adic(new JLabel("Descrição do plano de pgto."),90,30,280,20);
    pinOrig.adic(txtDescPlanoPag,90,50,200,20);

    pinOrig.adic(new JLabel("Cód.tab.pç."),7,70,280,20);
    pinOrig.adic(txtCodTab,7,90,80,20);
    pinOrig.adic(new JLabel("Descrição da tabela de precos"),90,70,280,20);
    pinOrig.adic(txtDescTab,90,90,200,20);

    pinOrig.adic(new JLabel("Cód.c.cli."),7,110,280,20);
    pinOrig.adic(txtCodClasCli,7,130,80,20);
    pinOrig.adic(new JLabel("Descrição da classif. do cliente"),90,110,280,20);
    pinOrig.adic(txtDescClasCli,90,130,200,20);

    pinOrig.adic(cbPrecoBaseOrig,7,160,180,30);
    
    
    pinDest.adic(lbDestino,7,0,270,20);
    pinDest.adic(new JLabel("Cód.p.pag."),7,30,280,20);
    pinDest.adic(txtCodPlanoPag2,7,50,80,20);
    pinDest.adic(new JLabel("Descrição do plano de pgto."),90,30,280,20);
    pinDest.adic(txtDescPlanoPag2,90,50,200,20);

    pinDest.adic(new JLabel("Cód.tab.pç."),7,70,280,20);
    pinDest.adic(txtCodTab2,7,90,80,20);
    pinDest.adic(new JLabel("Descrição da tabela de precos"),90,70,280,20);
    pinDest.adic(txtDescTab2,90,90,200,20);

    pinDest.adic(new JLabel("Cód.c.cli"),7,110,280,20);
    pinDest.adic(txtCodClasCli2,7,130,80,20);
    pinDest.adic(new JLabel("Descrição da classif. do cliente"),90,110,280,20);
    pinDest.adic(txtDescClasCli2,90,130,200,20);

    pinDest.adic(cbPrecoBaseDest,7,160,180,30);
    
    vVals.addElement("P");
    vVals.addElement("V");
    vLabs.addElement("Perc.");
    vLabs.addElement("Valor");

    rgInc = new JRadioGroup(1,2,vLabs,vVals);

    pinInc.adic(lbInc,7,0,270,20);
    pinInc.adic(new JLabel("Perc."),7,30,77,20);
    pinInc.adic(txtPerc,7,50,77,20);
    pinInc.adic(new JLabel("Valor"),90,30,77,20);
    pinInc.adic(txtVlr,90,50,77,20);
    pinInc.adic(rgInc,170,40,177,30);
    pinInc.adic(btGerar,350,40,100,30); 

    btSair.setPreferredSize(new Dimension(100,30));

    pnRod.add(btSair, BorderLayout.EAST);

    btSair.addActionListener(this);
    btGerar.addActionListener(this);
  }
  public void gerar() {
    String sInc = rgInc.getVlrString();
    if (txtCodGrup.getVlrString().equals("")) {
		Funcoes.mensagemInforma(this,"Código do grupo é requerido!");
      txtCodGrup.requestFocus();
    }
    else if (txtCodPlanoPag.getVlrString().equals("") && cbPrecoBaseOrig.getVlrString().equals("N")) {
		Funcoes.mensagemInforma(this,"Código do plano de pgto. é requerido! (ORIGEM)");
      txtCodPlanoPag.requestFocus();
    }
    else if (txtCodTab.getVlrString().equals("") && cbPrecoBaseOrig.getVlrString().equals("N")) {
		Funcoes.mensagemInforma(this,"Código da tab. de preços. é requerido! (ORIGEM)");
      txtCodTab.requestFocus();
    }
    else if (txtCodPlanoPag2.getVlrString().equals("") && cbPrecoBaseDest.getVlrString().equals("N")) {
		Funcoes.mensagemInforma(this,"Código do plano de pgto. é requerido! (DESTINO)");
      txtCodPlanoPag2.requestFocus();
    }
    else if (txtCodTab2.getVlrString().equals("") && cbPrecoBaseDest.getVlrString().equals("N")) {
		Funcoes.mensagemInforma(this,"Código da tab. de preços. é requerido! (DESTINO)");
      txtCodTab2.requestFocus();
    }
    else if ((sInc.equals("V")) & 
             (txtVlr.getVlrString().equals(""))) {
				Funcoes.mensagemInforma(this,"Valor é requerido!");
      txtVlr.requestFocus();
    }
    else if ((sInc.equals("P")) & 
             (txtPerc.getVlrString().equals(""))) {
				Funcoes.mensagemInforma(this,"Percento é requerido!");
      txtPerc.requestFocus();
    }
    else {
    	String sSQL = "";
    	if (cbPrecoBaseOrig.getVlrString().equals("S"))
            sSQL = "SELECT P.CODPROD,P.PRECOBASEPROD,"+
	          (cbPrecoBaseDest.equals("S") ? "0" :
             "(SELECT MAX(PC1.CODPRECOPROD) FROM VDPRECOPROD PC1"+
             " WHERE PC1.CODPROD=P.CODPROD AND"+
             " PC1.CODPLANOPAG = "+txtCodPlanoPag2.getVlrInteger().intValue()+" AND"+
             " PC1.CODCLASCLI "+(txtCodClasCli2.getVlrString().equals("") == false ? " = "+txtCodClasCli2.getVlrInteger().intValue() : "IS NULL")+" AND"+
             " PC1.CODTAB = "+txtCodTab2.getVlrInteger().intValue()+") AS CODPRECOPROD")+
			   " FROM EQPRODUTO P"+
			   " WHERE P.CODGRUP LIKE '"+txtCodGrup.getVlrString()+"%"+"' AND"+
			   " P.ATIVOPROD='S' AND P.CODEMP=? AND P.CODFILIAL=?"+
			   " ORDER BY P.CODPROD";
    	else
            sSQL = "SELECT P.CODPROD,PC.PRECOPROD,"+
		      (cbPrecoBaseDest.equals("S") ? "0" :
              "(SELECT MAX(PC1.CODPRECOPROD) FROM VDPRECOPROD PC1"+
              " WHERE PC1.CODPROD=P.CODPROD AND"+
              " PC1.CODPLANOPAG = "+txtCodPlanoPag2.getVlrInteger().intValue()+" AND"+
              " PC1.CODCLASCLI "+(txtCodClasCli2.getVlrString().equals("") == false ? " = "+txtCodClasCli2.getVlrInteger().intValue() : "IS NULL")+" AND"+
              " PC1.CODTAB = "+txtCodTab2.getVlrInteger().intValue()+") AS CODPRECOPROD")+
            " FROM EQPRODUTO P,VDPRECOPROD PC"+
            " WHERE P.CODGRUP LIKE '"+txtCodGrup.getVlrString()+"%"+"' AND"+
            " PC.CODPROD = P.CODPROD AND P.ATIVOPROD='S' AND"+
            " PC.CODPLANOPAG = "+txtCodPlanoPag.getVlrInteger().intValue()+" AND"+
            " PC.CODCLASCLI "+(txtCodClasCli.getVlrString().equals("") == false ? " = "+txtCodClasCli.getVlrInteger().intValue() : "IS NULL")+" AND"+
            " PC.CODTAB = "+txtCodTab.getVlrInteger().intValue()+
            " AND P.CODEMP=? AND P.CODFILIAL=? ORDER BY P.CODPROD";
    		
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
      ResultSet rs = ps.executeQuery();

        for (int i=0; rs.next(); i++) {
          BigDecimal bVal = rs.getBigDecimal(2) != null ? rs.getBigDecimal(2) : new BigDecimal(0);
          if (sInc.equals("P")) {
            bVal = bVal.add(
                   bVal.multiply(
                   txtPerc.getVlrBigDecimal().divide(
                   new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP)));
          }
          else if (sInc.equals("V")) {
            bVal = bVal.add(
              txtVlr.getVlrBigDecimal());
          }
          if (cbPrecoBaseDest.getVlrString().equals("N") && rs.getInt(3) == 0)
            inserir(bVal,rs.getInt(1));
          else 
            atualizar(bVal,rs.getInt(1),rs.getInt(3));
        }
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela VDPRECOPROD!\n"+err.getMessage());
      }
    }
  }
  private void inserir(BigDecimal bVal,int iCodProd) {
    String sSQL = "INSERT INTO VDPRECOPROD (CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,"+
                  "CODTAB,CODEMPTB,CODFILIALTB,CODCLASCLI,CODEMPCC,CODFILIALCC,CODPLANOPAG,CODEMPPG,CODFILIALPG,PRECOPROD) "+
                  "VALUES (?,?,?,COALESCE((SELECT MAX(CODPRECOPROD) FROM VDPRECOPROD "+
                  "WHERE CODPROD=?),0)+1,?,?,?,?,?,?,?,?,?,?)";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("VDPRECOPROD"));
      ps.setInt(3,iCodProd);
      ps.setInt(4,iCodProd);
      ps.setInt(5,txtCodTab2.getVlrInteger().intValue());
      ps.setInt(6,Aplicativo.iCodEmp);
      ps.setInt(7,ListaCampos.getMasterFilial("VDTABPRECO"));
      if (!txtCodClasCli2.getVlrString().equals("")) {
        ps.setInt(8,txtCodClasCli2.getVlrInteger().intValue());
        ps.setInt(9,Aplicativo.iCodEmp);
        ps.setInt(10,ListaCampos.getMasterFilial("VDCLASCLI"));
      }
      else {
      	ps.setNull(8,Types.INTEGER);
      	ps.setNull(9,Types.INTEGER);
      	ps.setNull(10,Types.INTEGER);
      }
      ps.setInt(11,txtCodPlanoPag2.getVlrInteger().intValue());
      ps.setInt(12,Aplicativo.iCodEmp);
      ps.setInt(13,ListaCampos.getMasterFilial("FNPLANOPAG"));
      ps.setBigDecimal(14,bVal);
      ps.executeUpdate();
//      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela VDPRECOPROD!\n"+err.getMessage());
    }
  }
  private void atualizar(BigDecimal bVal,int iCodProd,int iCodPrecoProd) {
    String sSQL = "";
    if (iCodPrecoProd == 0)
    	sSQL = "UPDATE EQPRODUTO SET PRECOBASEPROD=? WHERE CODPROD=? AND CODEMP=? AND CODFILIAL=?";
    else
    	sSQL = "UPDATE VDPRECOPROD SET PRECOPROD=? WHERE CODPRECOPROD=? AND CODPROD=? AND CODEMP=? AND CODFILIAL=?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      int iParam = 1;
      ps.setBigDecimal(iParam++,bVal);
      if (iCodPrecoProd != 0)
        ps.setInt(iParam++,iCodPrecoProd);
      ps.setInt(iParam++,iCodProd);
      ps.setInt(iParam++,Aplicativo.iCodEmp);
      ps.setInt(iParam++,ListaCampos.getMasterFilial("EQPRODUTO"));
      ps.executeUpdate();
//      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao atualizar registro '"+iCodProd+"' na tabela VDPRECOPROD!\n"+err.getMessage());
    }
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcClasCli.setConexao(cn);
    lcTab.setConexao(cn);
    lcPlanoPag.setConexao(cn);
    lcClasCli2.setConexao(cn);
    lcTab2.setConexao(cn);
    lcPlanoPag2.setConexao(cn);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btGerar) {
      gerar();
    }
  }
/* (non-Javadoc)
 * @see org.freedom.acao.CheckBoxListener#valorAlterado(org.freedom.acao.CheckBoxEvent)
 */
  public void valorAlterado(CheckBoxEvent evt) {
	
		   if (cbPrecoBaseOrig.getVlrString().equals("S")){
	  	       txtCodPlanoPag.setAtivo(false);
	  	       txtCodTab.setAtivo(false);
	  	       txtCodClasCli.setAtivo(false);
	  	       cbPrecoBaseDest.setEnabled(false); 
	       }
	   
	       else {
	           txtCodPlanoPag.setAtivo(true);
	  	       txtCodTab.setAtivo(true);
	  	       txtCodClasCli.setAtivo(true);
	  	       cbPrecoBaseDest.setEnabled(true); 
	       }
	       
	       if (cbPrecoBaseDest.getVlrString().equals("S")){
	  	       txtCodPlanoPag2.setAtivo(false);
	  	       txtCodTab2.setAtivo(false);
	  	       txtCodClasCli2.setAtivo(false);
	  	       cbPrecoBaseOrig.setEnabled(false); 
	       }
	       
	       else {
	           txtCodPlanoPag2.setAtivo(true);
	  	       txtCodTab2.setAtivo(true);
	  	       txtCodClasCli2.setAtivo(true);
	  	       cbPrecoBaseOrig.setEnabled(true); 
	       }
	       
	
    }
 }
