/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPlanejamento.java <BR>
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
 * Tela de cadastro de planejamento financeiro.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FPlanejamento extends FFilho implements ActionListener,MouseListener,KeyListener {
	private static final long serialVersionUID = 1L;

  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private FlowLayout flCliRod = new FlowLayout(FlowLayout.CENTER,0,0);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pnRodape = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCliRod = new JPanelPad(JPanelPad.TP_JPANEL,flCliRod);
  private JPanelPad pnBotoes = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,4,2,0));
  private JPanelPad pnImp = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,2,0,0));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btPrim = new JButton("Nivel 1",Icone.novo("btNovo.gif"));
  private JButton btSint = new JButton("Sintética",Icone.novo("btNovo.gif"));
  private JButton btAnal = new JButton("Analítica",Icone.novo("btNovo.gif"));
  private JButton btImp = new JButton(Icone.novo("btImprime.gif"));
  private JButton btPrevimp = new JButton(Icone.novo("btPrevimp.gif"));
  public FPlanejamento() {
  	super(false);
    setTitulo("Planejamento de Contas");
    setAtribos( 25, 25, 650, 380);
    
    Container c = getContentPane();
    
    c.setLayout(new BorderLayout());
    
    pnRodape.setPreferredSize(new Dimension(740, 33));
    pnRodape.setBorder(BorderFactory.createEtchedBorder());
    pnRodape.setLayout(new BorderLayout());
    pnRodape.add(btSair, BorderLayout.EAST);
    pnRodape.add(pnCliRod, BorderLayout.CENTER);
    
    pnCliRod.add(pnBotoes);

    pnImp.add(btImp);
    pnImp.add(btPrevimp);
    
    pnBotoes.setPreferredSize(new Dimension(440,29));
    pnBotoes.add(btPrim);
    pnBotoes.add(btSint);
    pnBotoes.add(btAnal);
    pnBotoes.add(pnImp);

    c.add(pnRodape, BorderLayout.SOUTH);
    
    pnCli.add(spnTab);
    c.add(pnCli, BorderLayout.CENTER);
    
    tab.adicColuna("Código");
    tab.adicColuna("Cód. Red.");
    tab.adicColuna("Descrição");
    tab.adicColuna("R/D");
    tab.adicColuna("Fin.");
    tab.setTamColuna(140,0);
    tab.setTamColuna(70,1);
    tab.setTamColuna(320,2);
    tab.setTamColuna(43,3);
    tab.setTamColuna(43,4);
    
    btSair.addActionListener(this);
    btPrim.addActionListener(this);
    btSint.addActionListener(this);
    btAnal.addActionListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    tab.addMouseListener(this);
    tab.addKeyListener(this);
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    montaTab();
  }
  private void montaTab() {
    String sSQL = "SELECT CODPLAN,CODREDPLAN,DESCPLAN,TIPOPLAN,FINPLAN FROM FNPLANEJAMENTO WHERE CODEMP=? AND CODFILIAL =? ORDER BY CODPLAN";
    PreparedStatement ps = null;
    ResultSet rs = null;
    tab.limpa();
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = ps.executeQuery();
      for (int i=0;rs.next();i++) {
        tab.adicLinha();
        tab.setValor(rs.getString("CodPlan"),i,0);
        tab.setValor(rs.getString("CodRedPlan") != null ? rs.getString("CodRedPlan") : "",i,1);
        tab.setValor(rs.getString("DescPlan"),i,2);
        tab.setValor(rs.getString("TipoPlan"),i,3);
        tab.setValor(rs.getString("FinPlan") != null ? rs.getString("FinPlan") : "",i,4);
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
  }
  private void gravaNovoPrim() {
    String sSQLQuery = "SELECT MAX(CODPLAN) FROM FNPLANEJAMENTO WHERE NIVELPLAN=1 AND CODEMP=? AND CODFILIAL=?";
    String sCodPrim = "";
    String sDescPrim = "";
    String sTipoPrim = "";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    try { 
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela PLAJAMENTO");
        return;
      }
      sCodPrim = rs.getString(1) != null ? ""+(Integer.parseInt(rs.getString(1).trim())+1) : "1";
//      rs.close();
//      psQuery.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err); 
      return;
    }
    DLPlanPrim dl = new DLPlanPrim(this,sCodPrim,null,null);
    dl.setVisible(true);
    if (!dl.OK) 
      return;
    sDescPrim = (dl.getValores())[0];
    sTipoPrim = (dl.getValores())[1];
    dl.dispose();
    String sSQL = "INSERT INTO FNPLANEJAMENTO (CODEMP,CODFILIAL,CODPLAN,DESCPLAN,NIVELPLAN,TIPOPLAN) "+
                  "VALUES(?,?,?,?,1,?)";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp)      ;
      ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      ps.setString(3,sCodPrim);
      ps.setString(4,sDescPrim);
      ps.setString(5,sTipoPrim);
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela PLANEJAMENTO! ! !");
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err); 
      return;
    }
  }
  private void gravaNovoSint() {
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Seleciona a origem na tabela! ! !");
      return;
    }
    String sCodPai = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    if (sCodPai.length() == 13) {
		Funcoes.mensagemInforma(this,"Não é possível criar uma Conta Sintética de uma Conta Analítica! ! !");
      return;
    }
    else if (sCodPai.length() == 9) {
		Funcoes.mensagemInforma(this,"Não é possível criar mais de quatro niveis de contas sintética! ! !");
      return;
    }
    String sDescPai = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    String sDescFilho = "";
    String sCodFilho = "";
    int iCodFilho = 0;
    int iNivelPai = 0;
    int iNivelFilho = 0;
    String sMax = "";
    String sSQLQuery = "SELECT G.NIVELPLAN,(SELECT MAX(M.CODPLAN) FROM FNPLANEJAMENTO M "+
                       "WHERE M.CODSUBPLAN=G.CODPLAN AND M.CODEMP=G.CODEMP AND M.CODFILIAL=G.CODFILIAL)"+
                       "FROM FNPLANEJAMENTO G WHERE G.CODPLAN='"+sCodPai+"' AND G.CODEMP=? AND G.CODFILIAL=?";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    try {
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela PLANEJAMENTO");
        return;
      }
      iNivelPai = rs.getInt(1);
      sMax = rs.getString(2) != null ? rs.getString(2).trim() : sCodPai+"00";
      if (sMax.length() == 13) {
        Funcoes.mensagemInforma(this,"Não é possível criar uma conta sintética desta conta sintética,\n"+
        "pois esta conta sintética possui contas analíticas.");
        return;
      }
      iCodFilho = Integer.parseInt(sMax.substring(sMax.length()-2,sMax.length()));
      sCodFilho = sCodPai+Funcoes.strZero(""+(iCodFilho+1),2);
      iNivelFilho = iNivelPai+1;
//      rs.close();
//      psQuery.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consulta a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
    DLPlanSin dl = new DLPlanSin(this,sCodPai,sDescPai,sCodFilho,null,sTipoFilho);
    dl.setVisible(true);
    if (!dl.OK)
      return;
    sDescFilho = (dl.getValores())[0];
    sTipoFilho = (dl.getValores())[1];
    dl.dispose();
    String sSQL = "INSERT INTO FNPLANEJAMENTO (CODEMP,CODFILIAL,CODPLAN,DESCPLAN,NIVELPLAN,CODSUBPLAN,TIPOPLAN) "+
                  "VALUES (?,?,?,?,?,?,?)";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      ps.setString(3,sCodFilho);
      ps.setString(4,sDescFilho);
      ps.setInt(5,iNivelFilho);
      ps.setString(6,sCodPai);
      ps.setString(7,sTipoFilho);
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
  }
  private void gravaNovoAnal() {
  	GregorianCalendar cData = new GregorianCalendar();
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Selecione a conta sintética de origem! ! !");
      return;
    }
    String sCodPai = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sDescPai = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    if (sCodPai.length() == 13) {
		Funcoes.mensagemInforma(this,"Não é possível criar uma conta analítica de outra conta analítica! ! !");
      return;
    }
    else if (sCodPai.length() == 1) {
		Funcoes.mensagemInforma(this,"Não é possível criar uma conta analítica apartir do 1º Nivel! ! !");
      return;
    }
    String sSQLQuery = "SELECT MAX(C.CODPLAN), MAX(R.CODREDPLAN) "+
                       "FROM FNPLANEJAMENTO C, FNPLANEJAMENTO R "+
                       "WHERE C.CODSUBPLAN = '"+sCodPai+"' AND C.CODEMP=R.CODEMP"+
                       " AND C.CODFILIAL=R.CODFILIAL AND R.CODEMP=? AND R.CODFILIAL=?";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    int iCodFilho = 0;
    int iCodRed = 0;
    String sCodFilho = "";
    String sMax = "";
    try {
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      rs.next();
      iCodRed = rs.getInt(2)+1;
      sMax = rs.getString(1) != null ? rs.getString(1).trim() : "";
      if ((sMax.trim().length() > sCodPai.trim().length()) &&
          (sMax.length() < 13)) {
			Funcoes.mensagemInforma(this,"Não é possível criar uma conta analítica desta conta sintética,\n"+
                                           "pois esta conta sintética têm sub-divisões.");
        return;
      }
      if (sMax.length() == 0) {
        sCodFilho = sCodPai+Funcoes.replicate("0",12-sCodPai.length())+1;
      }
      else {
        if (sMax.length() > 10)
                iCodFilho = Integer.parseInt(sMax.substring(10));
        else 
                iCodFilho = 0;
        iCodFilho = iCodFilho+1;
        sCodFilho = sCodPai+Funcoes.strZero(""+iCodFilho,(13-(sCodPai.length())));
      }
//      psQuery.close();
//      rs.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
    if ("BC".indexOf(sTipoFilho) >= 0) {
      String[] sContVals = {null,null,null,null};
      DLAnalBanc dl = new DLAnalBanc(this,sCodPai,sDescPai,sCodFilho,null,sTipoFilho,sContVals);
      
//	  DLPlanAnal dl = new DLPlanAnal(this,sCodPai,sDescPai,sCodFilho,null,sTipoFilho);

      dl.setConexao(con);
      dl.setVisible(true);
      if (!dl.OK) {
        dl.dispose();
        return;
      }
      String sDescFilho = (dl.getValores())[0];
      String sAgCont = (dl.getValores())[1];
      String sNumCont = (dl.getValores())[2];
      String sDescCont = (dl.getValores())[3];
      String sCodBanc = (dl.getValores())[4];
      String sDataCont = (dl.getValores())[5];
      String sCodMoeda = (dl.getValores())[6];
      cData.setTime(dl.getData());
      dl.dispose();
      String sSQL = "INSERT INTO FNPLANEJAMENTO (CODEMP,CODFILIAL,CODPLAN,DESCPLAN,NIVELPLAN,CODREDPLAN,CODSUBPLAN,TIPOPLAN) "+
                    "VALUES (?,?,'"+sCodFilho+"','"+sDescFilho+"',6,"+iCodRed+",'"+sCodPai+"','"+sTipoFilho+"')";
      PreparedStatement ps = null;
      try {
        ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
        if (ps.executeUpdate() == 0) {
			Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela PLANEJAMENTO! ! !");
          return;
        }
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
        return;
      }
      String sSQLCont = "INSERT INTO FNCONTA (CODEMP,CODFILIAL,NUMCONTA,CODEMPBO,CODFILIALBO,CODBANCO,CODEMPPN,CODFILIALPN,CODPLAN,DESCCONTA,TIPOCONTA,DATACONTA,AGENCIACONTA,CODEMPMA,CODFILIALMA,CODMOEDA) "+
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement psCont = null;
      try {
        psCont = con.prepareStatement(sSQLCont);
		psCont.setInt(1,Aplicativo.iCodEmp);
		psCont.setInt(2,ListaCampos.getMasterFilial("FNCONTA"));
        psCont.setString(3,sNumCont);
        if (sCodBanc.trim().length() > 0) {
  		  psCont.setInt(4,Aplicativo.iCodEmp);
		  psCont.setInt(5,ListaCampos.getMasterFilial("FNBANCO"));
          psCont.setString(6,sCodBanc);
        }
        else {
        	psCont.setNull(4,Types.INTEGER);
        	psCont.setNull(5,Types.INTEGER);
        	psCont.setNull(6,Types.CHAR);
        }
        psCont.setInt(7,Aplicativo.iCodEmp);
		psCont.setInt(8,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
        psCont.setString(9,sCodFilho);
        psCont.setString(10,sDescCont);
        psCont.setString(11,sTipoFilho);
        psCont.setDate(12,Funcoes.strDateToSqlDate(sDataCont));
        psCont.setString(13,sAgCont);
		psCont.setInt(14,Aplicativo.iCodEmp);
		psCont.setInt(15,ListaCampos.getMasterFilial("FNMOEDA"));
        psCont.setString(16,sCodMoeda);
                                           
        if (psCont.executeUpdate() == 0) {
			Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela PALNEJAMENTO! ! !");
          return;
        }
//        psCont.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela CONTA!\n"+err.getMessage(),true,con,err);
        return;
      }
    }
    else if ("DR".indexOf(sTipoFilho) >= 0) {
      DLPlanAnal dl = new DLPlanAnal(this,sCodPai,sDescPai,sCodFilho,null,sTipoFilho,"");
      dl.setVisible(true);
      if (!dl.OK) {
        dl.dispose();
        return;
      }
      String sDescFilho = dl.getValores()[0];
      String sFinPlan = dl.getValores()[1];
      dl.dispose();
      String sSQL = "INSERT INTO FNPLANEJAMENTO (CODEMP,CODFILIAL,CODPLAN,DESCPLAN,NIVELPLAN,CODREDPLAN,CODSUBPLAN,TIPOPLAN,FINPLAN) "+
                    "VALUES (?,?,'"+sCodFilho+"','"+sDescFilho+"',6,"+iCodRed+",'"+sCodPai+"','"+sTipoFilho+"','"+sFinPlan+"')";
      PreparedStatement ps = null;
      try {
        ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
        if (ps.executeUpdate() == 0) {
			Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela PALNEJAMENTO! ! !");
          return;
        }
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
        return;
      }
    }
  }
  private void editaPrim() {
    String sCodFilho = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sDescFilho = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    DLPlanPrim dl = new DLPlanPrim(this,sCodFilho,sDescFilho,sTipoFilho);
    dl.setVisible(true);
    if (!dl.OK) 
      return;
    sDescFilho = (dl.getValores())[0];
    dl.dispose();
    String sSQL = "UPDATE FNPLANEJAMENTO SET DESCPLAN=? WHERE CODPLAN=? AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setString(1,sDescFilho);
      ps.setString(2,sCodFilho);
	  ps.setInt(3,Aplicativo.iCodEmp);
	  ps.setInt(4,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível editar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao editar um registro na tabela PLANEJAMENTO\n"+err.getMessage(),true,con,err);
      return;
    }
  }
  private void editaSin() {
    String sCodFilho = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sCodPai = sCodFilho.substring(0,sCodFilho.length()-2);
    String sDescFilho = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    String sSQLQuery = "SELECT DESCPLAN FROM FNPLANEJAMENTO WHERE CODPLAN='"+sCodPai+"' AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    String sDescPai = "";
    try {
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela PLANEJAMENTO! ! !");
        return;
      }
      sDescPai = rs.getString("DescPlan").trim();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
    DLPlanSin dl = new DLPlanSin(this,sCodPai,sDescPai,sCodFilho,sDescFilho,sTipoFilho);
    dl.setVisible(true);
    if (!dl.OK) 
      return;
    sDescFilho = (dl.getValores())[0];
    dl.dispose();
    String sSQL = "UPDATE FNPLANEJAMENTO SET DESCPLAN='"+sDescFilho+"' "+
                  "WHERE CODPLAN='"+sCodFilho+"' AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível editar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	 con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao editar um registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
    }
  }
  private void editaAnal() {
    String sCodFilho = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sDescFilho = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    String sFinPlan = (""+tab.getValor(tab.getLinhaSel(),4)).trim();
    String sSQLQuery = "SELECT P.DESCPLAN,F.CODSUBPLAN FROM FNPLANEJAMENTO P, FNPLANEJAMENTO F "+
                       "WHERE F.CODPLAN='"+sCodFilho+"' AND P.CODPLAN=F.CODSUBPLAN AND P.CODEMP=F.CODEMP"+
                       " AND P.CODFILIAL=F.CODFILIAL AND F.CODEMP=? AND F.CODFILIAL=?";
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    String sDescPai = "";
    String sCodPai = "";
    try {
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela PLANEJAMENTO! ! !");
        return;
      }
      sDescPai = rs.getString("DescPlan").trim();
      sCodPai = rs.getString("CodSubPlan").trim();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
    DLPlanAnal dl = new DLPlanAnal(this,sCodPai,sDescPai,sCodFilho,sDescFilho,sTipoFilho,sFinPlan);
    dl.setVisible(true);
    if (!dl.OK) 
      return;
    sDescFilho = dl.getValores()[0];
    sFinPlan = dl.getValores()[1];
    dl.dispose();
    String sSQL = "UPDATE FNPLANEJAMENTO SET DESCPLAN='"+sDescFilho+"', " +
    			  "FINPLAN='"+sFinPlan+"' "+
                  "WHERE CODPLAN='"+sCodFilho+"' AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível editar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao editar um registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
    }
  }
  private void editaAnalBanc() {
    String sCodFilho = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sDescFilho = (""+tab.getValor(tab.getLinhaSel(),2)).trim();
    String sTipoFilho = (""+tab.getValor(tab.getLinhaSel(),3)).trim();
    String sSQLQuery = "SELECT P.DESCPLAN,F.CODSUBPLAN,C.AGENCIACONTA,C.NUMCONTA,C.DESCCONTA,C.CODBANCO,C.DATACONTA,C.CODMOEDA "+
                       "FROM FNPLANEJAMENTO P, FNPLANEJAMENTO F, FNCONTA C "+
                       "WHERE F.CODPLAN='"+sCodFilho+"' AND P.CODPLAN=F.CODSUBPLAN "+
                       " P.CODEMP=F.CODEMP AND P.CODFILIAL=F.CODFILIAL AND F.CODEMP=? AND F.CODFILIAL=? AND C.CODPLAN=F.CODPLAN";    
    PreparedStatement psQuery = null;
    ResultSet rs = null;
    String sDescPai = "";
    String sCodPai = "";
    String sAgConta = "";
    String sNumConta = "";
    String sDescConta = "";
    String sCodBanco = "";
    String sDataConta = "";
    GregorianCalendar cDataConta = new GregorianCalendar();
    String sCodMoeda = "";
    try {
      psQuery = con.prepareStatement(sSQLQuery);
	  psQuery.setInt(1,Aplicativo.iCodEmp);
	  psQuery.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = psQuery.executeQuery();
      if (!rs.next()) {
		Funcoes.mensagemInforma(this,"Não foi possível consultar a tabela PLANEJAMENTO! ! !");
        return;
      }
      sDescPai = rs.getString("DescPlan").trim();
      sCodPai = rs.getString("CodSubPlan").trim();
      sAgConta = rs.getString("AgenciaConta") != null ? rs.getString("AgenciaConta").trim() : "";
      sNumConta = rs.getString("NumConta").trim();
      sDescConta = rs.getString("DescConta").trim();
      sCodBanco = rs.getString("CodBanco") != null ? rs.getString("CodBanco").trim() : "";
      cDataConta.setTime(Funcoes.sqlDateToDate(rs.getDate("DataConta")));
      sCodMoeda = rs.getString("CodMoeda").trim();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
    String[] sContVals = {sAgConta,sNumConta,sDescConta,sCodBanco,Funcoes.dateToStrDate(cDataConta.getTime()),sCodMoeda};
    DLAnalBanc dl = new DLAnalBanc(this,sCodPai,sDescPai,sCodFilho,sDescFilho,sTipoFilho,sContVals);
    dl.setConexao(con);
    dl.setVisible(true);
    if (!dl.OK) 
      return;
    sDescFilho = (dl.getValores())[0];
    sAgConta = (dl.getValores())[1];
    sNumConta = (dl.getValores())[2];
    sDescConta = (dl.getValores())[3];
    sCodBanco = (dl.getValores())[4];
    sDataConta = (dl.getValores())[5];
    sCodMoeda = (dl.getValores())[6];
    dl.dispose();
    String sSQL = "UPDATE FNPLANEJAMENTO SET DESCPLAN='"+sDescFilho+"' "+
                  "WHERE CODPLAN='"+sCodFilho+"' AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível editar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao editar um registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
    }
    String sSQLCont = "UPDATE FNCONTA SET AGENCIACONTA=?, "+
                  "NUMCONTA=?,DESCCONTA=?,CODBANCO=?, "+
                  "DATACONTA=?,CODMOEDA='"+sCodMoeda+"' "+
                  "WHERE CODPLAN='"+sCodFilho+"' AND "+
                  "CODEMP=? AND CODFILIAL=?";
    PreparedStatement psCont = null;
    try {
      psCont = con.prepareStatement(sSQLCont);
      psCont.setString(1,sAgConta);
      psCont.setString(2,sNumConta);
      psCont.setString(3,sDescConta);
      psCont.setString(4,sCodBanco);
      psCont.setDate(5,Funcoes.strDateToSqlDate(sDataConta));
	  psQuery.setInt(6,Aplicativo.iCodEmp);
	  psQuery.setInt(7,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (psCont.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível editar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      psCont.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao editar um registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
    }
  }
  public void deletar() {
    if (tab.getLinhaSel() < 0) {
		Funcoes.mensagemInforma(this,"Selecione a conta na tabela! ! !");
      return;
    }
    String sCod = (""+tab.getValor(tab.getLinhaSel(),0)).trim();
    String sSQL = "DELETE FROM FNPLANEJAMENTO WHERE CODPLAN='"+sCod+"' AND CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      if (ps.executeUpdate() == 0) {
		Funcoes.mensagemInforma(this,"Não foi possível deletar um registro na tabela PLANEJAMENTO! ! !");
        return;
      }
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
      if (err.getErrorCode() == 335544466)
        Funcoes.mensagemErro( this, "O registro possui vínculos, não pode ser deletado! ! !");
      else 
	  Funcoes.mensagemErro(this,"Erro ao deletar um registro na tabela PLANEJAMENTO!\n"+err.getMessage(),true,con,err);
      return;
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btPrim) {
      gravaNovoPrim();
      montaTab();
    }
    else if (evt.getSource() == btSint) {
      gravaNovoSint();
      montaTab();
    }
    else if (evt.getSource() == btAnal) {
      gravaNovoAnal();
      montaTab();
    }
    else if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
  }

  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Planejamento");
    DLRPlanejamento dl = new DLRPlanejamento();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODPLAN,CODREDPLAN,DESCPLAN,TIPOPLAN "
    +"FROM FNPLANEJAMENTO WHERE CODEMP=? AND CODFILIAL=? ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"Código");
            imp.say(imp.pRow()+0,17,"Cód. Red.");
            imp.say(imp.pRow()+0,29,"Descrição");
            imp.say(imp.pRow()+0,71,"Tipo");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
	 imp.say(imp.pRow()+0,0,"");
         imp.say(imp.pRow()+0,2,rs.getString("CodPlan"));
	 if (rs.getString("CodRedPlan") != null) 
	         imp.say(imp.pRow()+0,17,rs.getString("CodRedPlan"));
         imp.say(imp.pRow()+0,29,Funcoes.copy(rs.getString("DescPlan"),0,40));
         imp.say(imp.pRow()+0,71,rs.getString("TipoPlan"));
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de Almoxarifados!"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  public void mouseClicked(MouseEvent mevt) {
    if ((mevt.getSource() == tab) & (mevt.getClickCount() == 2) & (tab.getLinhaSel() >= 0)) {
      if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 1) {
        editaPrim();
        montaTab();
      }
      else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() > 1) &
               ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() < 13)) {
        editaSin();
        montaTab();
      }
      else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) &
               ((""+tab.getValor(tab.getLinhaSel(),2)).trim().compareTo("B") == 0)){
        editaAnalBanc();
        montaTab();
      }
      else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) &
               ((""+tab.getValor(tab.getLinhaSel(),2)).trim().compareTo("C") == 0)){
        editaAnalBanc();
        montaTab();
      }
      else if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) {
        editaAnal();
        montaTab();
      }
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if ((kevt.getKeyCode() == KeyEvent.VK_DELETE) & (kevt.getSource() == tab)) {
      if (Funcoes.mensagemConfirma(this, "Deseja realmente deletar este registro?")==0 )
        deletar();
      montaTab();
    }
    else if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) & (kevt.getSource() == tab) & 
             (tab.getLinhaSel() >= 0)) {
      if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 1) {
        editaPrim();
        montaTab();
      }
      else if (((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() > 1) &
               ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() < 13)) {
        editaSin();
        montaTab();
      }
      else if ((""+tab.getValor(tab.getLinhaSel(),0)).trim().length() == 13) {
        editaAnal();
        montaTab();
      }
    }
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
  public void mouseEntered(MouseEvent mevt) { }
  public void mouseExited(MouseEvent mevt) { }
  public void mousePressed(MouseEvent mevt) { }
  public void mouseReleased(MouseEvent mevt) { }
}
