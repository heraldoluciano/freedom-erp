/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FLanca.java <BR>
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import org.freedom.componentes.JTabbedPanePad;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.telas.FPrincipal;

public class FLanca extends FFilho implements ActionListener,ChangeListener {
  private JPanelPad pinCab = new JPanelPad(600,64);
  private JPanelPad pnNav = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,7));
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnCentro = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());;
  private JPanelPad pinPeriodo = new JPanelPad(260,50);
  private JLabelPad lbPeriodo = new JLabelPad(" Periodo");
  private JPanelPad pinSaldo = new JPanelPad(310,50);

  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btCalcSaldo = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btPrim = new JButton(Icone.novo("btPrim.gif"));
  private JButton btAnt = new JButton(Icone.novo("btAnt.gif"));
  private JButton btProx = new JButton(Icone.novo("btProx.gif"));
  private JButton btUlt = new JButton(Icone.novo("btUlt.gif"));
  private JButton btNovo = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExcluir = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btEditar = new JButton(Icone.novo("btEditar.gif"));
  private JLabelPad lbA = new JLabelPad("à");
  private JLabelPad lbPinSaldo = new JLabelPad(" Saldo");
  private JPanelPad pinLbPeriodo = new JPanelPad(53,15);
  private JPanelPad pinLbSaldo = new JPanelPad(45,15);
  private JLabelPad lbDataSaldo = new JLabelPad("Data");
  private JLabelPad lbVlrSaldo = new JLabelPad("Saldo");
  private JLabelPad lbAtualSaldo = new JLabelPad("Atualiza");
  private JLabelPad lbDataSaldoVal = new JLabelPad("");
  private JLabelPad lbVlrSaldoVal = new JLabelPad("");
  private JLabelPad lbAtualSaldoVal = new JLabelPad("NÃO");
  private Tabela tab = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tab);
  private JTabbedPanePad tpn = new JTabbedPanePad();
  private String[] sPlanos = null;
  private String sCodPlan = "";
  private String[] sContas = null;
  private String sConta = "";
  private Date dIniLanca = null;
  private Date dFimLanca = null;
  public FLanca() {
  	super(false);
    setTitulo("Lançamentos Financeiros");
    setAtribos(50,25,617,400);
    
    Container c = getContentPane();
    
    c.setLayout(new BorderLayout());
    
    pnRod.setPreferredSize(new Dimension(600,32));
    
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pnCentro,BorderLayout.CENTER);
    c.add(pinCab,BorderLayout.NORTH);
    
    tpn.setTabLayoutPolicy(JTabbedPanePad.SCROLL_TAB_LAYOUT);
    tpn.setPreferredSize(new Dimension(600,30));
    pnCentro.add(tpn,BorderLayout.SOUTH);
    pnCentro.add(spnTab,BorderLayout.CENTER);

    pinLbPeriodo.adic(lbPeriodo,0,0,51,15);
    pinLbPeriodo.tiraBorda();
    pinLbSaldo.adic(lbPinSaldo,0,0,46,15);
    pinLbSaldo.tiraBorda();
    
    pinCab.adic(pinLbPeriodo,10,2,51,15);
    pinCab.adic(pinPeriodo,7,10,260,44);
    pinCab.adic(pinLbSaldo,270,2,46,15);
    pinCab.adic(pinSaldo,270,10,326,44);
    
    pinPeriodo.adic(txtDataini,7,10,100,20);
    pinPeriodo.adic(lbA,110,10,7,20);
    pinPeriodo.adic(txtDatafim,120,10,97,20);
    pinPeriodo.adic(btExec,220,5,30,30);
    
    lbDataSaldoVal.setForeground(new Color(0,140,0));
    lbVlrSaldoVal.setForeground(new Color(0,140,0));
    lbAtualSaldoVal.setForeground(new Color(0,140,0));
    
    pinSaldo.adic(lbDataSaldo,7,6,50,15);
    pinSaldo.adic(lbDataSaldoVal,7,21,100,15);
    pinSaldo.adic(lbVlrSaldo,110,6,50,15);
    pinSaldo.adic(lbVlrSaldoVal,110,21,97,15);
    pinSaldo.adic(lbAtualSaldo,210,6,50,15);
    pinSaldo.adic(lbAtualSaldoVal,210,21,57,15);
    pinSaldo.adic(btCalcSaldo,285,5,30,30);
    
    btSair.setPreferredSize(new Dimension(100,31));

    pnNav.setPreferredSize(new Dimension(210,30));
    
    pnRod.setBorder(BorderFactory.createEtchedBorder());    
    pnRod.add(btSair,BorderLayout.EAST);
    pnRod.add(pnNav,BorderLayout.WEST);
    
    pnNav.add(btPrim);
    pnNav.add(btAnt);
    pnNav.add(btProx);
    pnNav.add(btUlt);
    pnNav.add(btNovo);
    pnNav.add(btExcluir);
    pnNav.add(btEditar);
    
    tab.adicColuna("Nº Lançamento");
    tab.adicColuna("Data");
    tab.adicColuna("Tsf.");
    tab.adicColuna("Orig.");
    tab.adicColuna("Conta tsf.");
    tab.adicColuna("Nº doc.");
    tab.adicColuna("Valor");
    tab.adicColuna("Histórico");
    
    tab.setTamColuna(100,0);
    tab.setTamColuna( 70,1);
    tab.setTamColuna( 40,2);
    tab.setTamColuna( 55,3);
    tab.setTamColuna( 72,4);
    tab.setTamColuna( 65,5);
    tab.setTamColuna(100,6);
    tab.setTamColuna(100,7);
    
    btSair.addActionListener(this);
    btPrim.addActionListener(this);
    btAnt.addActionListener(this);
    btProx.addActionListener(this);
    btUlt.addActionListener(this);
    btNovo.addActionListener(this);
    btEditar.addActionListener(this);
    btExcluir.addActionListener(this);
    btExec.addActionListener(this);
    btCalcSaldo.addActionListener(this);
    tpn.addChangeListener(this);

	Calendar cPeriodo = Calendar.getInstance();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
    
  }
  private void montaTabela(Date dini, Date dfim) {
    tab.limpa();
    String sSQL = "SELECT S.CODLANCA, S.DATASUBLANCA, L.TRANSFLANCA, S.ORIGSUBLANCA,"+
                  "L.DOCLANCA,S.VLRSUBLANCA,L.HISTBLANCA," +
                  "(SELECT C.NUMCONTA FROM FNSUBLANCA S1,FNCONTA C " + 
                  "WHERE S1.CODSUBLANCA=0 AND S1.CODLANCA=S.CODLANCA AND "+
                  "S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL AND "+
                  "C.CODPLAN=S1.CODPLAN AND C.CODEMP=S1.CODEMPPN AND "+
                  "C.CODFILIAL=S1.CODFILIALPN ) NUMCONTA FROM FNSUBLANCA S,"+
                  " FNLANCA L WHERE S.DATASUBLANCA BETWEEN ? AND ? AND"+
                  " S.CODLANCA = L.CODLANCA AND S.CODEMP=L.CODEMP AND S.CODFILIAL=L.CODFILIAL" +
                  " AND S.CODPLAN = ? AND L.CODEMP=? AND L.CODFILIAL=?" +
                  " ORDER BY S.DATASUBLANCA,S.CODLANCA";
    try {
//      System.out.println(sSQL);
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(dini));
//	  System.out.println(""+Funcoes.dateToSQLDate(dini));
      ps.setDate(2,Funcoes.dateToSQLDate(dfim));
//	  System.out.println(""+Funcoes.dateToSQLDate(dfim));
      ps.setString(3,sCodPlan);
//	  System.out.println(sCodPlan);

      ps.setInt(4,Aplicativo.iCodEmp);
//	  System.out.println(""+Aplicativo.iCodEmp);
      ps.setInt(5,ListaCampos.getMasterFilial("FNSUBLANCA"));
//	  System.out.println(ListaCampos.getMasterFilial("FNSUBLANCA"));
      
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tab.adicLinha();
        tab.setValor(rs.getString("CodLanca"),i,0);
        tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DataSubLanca")),i,1);
        tab.setValor(rs.getString("TransfLanca") != null ? rs.getString("TransfLanca") : "",i,2);
        if (rs.getString("TransfLanca").trim().equals("S")) {
          tab.setValor(rs.getString(8) != null ? rs.getString(8) : "",i,4);
        }
        tab.setValor(rs.getString("OrigSubLanca") != null ? rs.getString("OrigSubLanca") : "",i,3);
        tab.setValor(rs.getString("DocLanca") != null ? rs.getString("DocLanca") : "",i,5);
        tab.setValor(Funcoes.strDecimalToStrCurrency(8,2,rs.getString("VlrSubLanca")),i,6);
        tab.setValor(rs.getString("HistBLanca") != null ? rs.getString("HistBLanca") : "",i,7);
      }
      rs.close();
      ps.close();
//	  con.commit();
	  atualizaSaldo();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela!\n"+err.getMessage(),true,con,err);
    }
  }
  private void montaTabs() {
    tpn.setTabPlacement(SwingConstants.BOTTOM);
    String sSQL = "SELECT (SELECT COUNT(C1.NUMCONTA) FROM FNCONTA C1,FNPLANEJAMENTO P1 "+
                  "WHERE P1.NIVELPLAN = 6 AND P1.TIPOPLAN IN ('B','C') AND C1.CODPLAN=P1.CODPLAN" +
                  " AND C1.CODEMP=P1.CODEMP AND C1.CODFILIAL=P1.CODFILIAL AND P1.CODEMP=P.CODEMP" +
                  " AND P1.CODFILIAL=P.CODFILIAL),P.CODPLAN,C.NUMCONTA,C.DESCCONTA"+
                  " FROM FNPLANEJAMENTO P,FNCONTA C WHERE P.NIVELPLAN = 6"+
                  " AND P.TIPOPLAN IN ('B','C') AND C.CODPLAN = P.CODPLAN" +
                  " AND C.CODEMP = P.CODEMP AND C.CODFILIAL=P.CODFILIAL AND P.CODEMP=? AND P.CODFILIAL=?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
      ResultSet rs = ps.executeQuery();
      for (int i=0;rs.next();i++) {
        if (i == 0) {
          sPlanos = new String[rs.getInt(1)];
          sContas = new String[rs.getInt(1)];
        }
        sContas[i] = rs.getString("NumConta");
        sPlanos[i] = rs.getString("CodPlan");
        tpn.addTab(rs.getString("DescConta").trim(),new JPanelPad(JPanelPad.TP_JPANEL));
      }
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();

    }
    catch (SQLException err) {
		System.out.println("Erro ao montar as tabs!\n"+err.getMessage());
		err.printStackTrace();      
    }
  }
  private void atualizaSaldo() {
  	int iCodEmp = 0;
  	int iCodFilial = 0;
  	
    String sSQL = "SELECT S.DATASL,S.SALDOSL FROM FNSALDOLANCA S WHERE S.CODPLAN=?"+
                  " AND S.CODEMP=? AND S.CODFILIAL=? AND S.CODEMPPN=? AND S.CODFILIALPN=?" +
                  " AND S.DATASL=(SELECT MAX(S1.DATASL)" +
                  " FROM FNSALDOLANCA S1 WHERE S1.DATASL <= ? AND S1.CODPLAN=S.CODPLAN" +
                  " AND S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL" +
                  " AND S1.CODEMPPN=S.CODEMPPN AND S1.CODFILIALPN=S.CODFILIALPN)";
    try {
      iCodEmp = Aplicativo.iCodEmp;
      iCodFilial = ListaCampos.getMasterFilial("FNSALDOLANCA"); 
      PreparedStatement ps = con.prepareStatement(sSQL);
      
      ps.setString(1,sCodPlan);
	  ps.setInt(2,iCodEmp);
	  ps.setInt(3,iCodFilial);
	  ps.setInt(4,iCodEmp);
	  ps.setInt(5,iCodFilial);
	  ps.setDate(6,Funcoes.dateToSQLDate(dFimLanca));
	  
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        lbDataSaldoVal.setText(Funcoes.sqlDateToStrDate(rs.getDate("DataSl")));
        lbVlrSaldoVal.setText(Funcoes.strDecimalToStrCurrency(10,2,rs.getString("SaldoSl")));
        lbAtualSaldoVal.setText("SIM");
      }
      else {
        lbDataSaldoVal.setText("");
        lbVlrSaldoVal.setText("");
        lbAtualSaldoVal.setText("SEM");
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao atualizar o saldo!\n"+err.getMessage(),true,con,err);
    }
  }
  private void prim() {
    if ((tab != null) & (tab.getNumLinhas() > 0)) 
      tab.setLinhaSel(0);
  }
  private void ant() {
    int iLin = 0;
    if ((tab != null) & (tab.getNumLinhas() > 0)) {
      iLin = tab.getLinhaSel();
      if (iLin > 0) 
        tab.setLinhaSel(iLin-1);
    }
  }
  private void prox() {
    int iLin = 0;
    if ((tab != null) & (tab.getNumLinhas() > 0)) {
      iLin = tab.getLinhaSel();      
      if (iLin < (tab.getNumLinhas()-1)) 
        tab.setLinhaSel(iLin+1);
    }
  }
  private void ult() {
    if ((tab != null) & (tab.getNumLinhas() > 0)) 
      tab.setLinhaSel(tab.getNumLinhas()-1);
  }
  private boolean validaPeriodo() {
    boolean bRetorno = false;
    if (txtDataini.getText().trim().length() == 0) {
    }
    else if (txtDataini.getText().trim().length() < 10) {
		Funcoes.mensagemInforma(this,"Data inicial inválida!");
    }
    else if (txtDatafim.getText().trim().length() < 10) {
		Funcoes.mensagemInforma(this,"Data final inválida!");
    }
    else if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final inicial que a data final!");
    }
    else {
      dIniLanca = txtDataini.getVlrDate();
      dFimLanca = txtDatafim.getVlrDate();
      bRetorno = true;
    }
    return bRetorno;
  }
  private void excluir() {
    if ((tab.getLinhaSel() >= 0) & 
        (Funcoes.mensagemConfirma(this, "Deseja realmente excluir este lancamento?")==0)) {
      try {
        PreparedStatement ps = con.prepareStatement("DELETE FROM FNLANCA WHERE CODLANCA=? AND CODEMP=? AND CODFILIAL=?");
        ps.setString(1,(String)tab.getValor(tab.getLinhaSel(),0));
		ps.setInt(2,Aplicativo.iCodEmp);
		ps.setInt(3,ListaCampos.getMasterFilial("FNLANCA"));
        ps.executeUpdate();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        montaTabela(dIniLanca,dFimLanca);
      }
      catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao excluir o lançamento!\n"+err.getMessage(),true,con,err);
      }
    }
  }
  private void novo() {
    if (validaPeriodo()) {
      Container cont = getContentPane();
      while (true) {
        if (cont instanceof FPrincipal) 
          break;
        cont = cont.getParent();
      }
      if (!((FPrincipal)cont).temTela("FSubLanca")) {
        FSubLanca form = new FSubLanca(null,sCodPlan,dIniLanca,dFimLanca);
        ((FPrincipal)cont).criatela("FSubLanca",form,con);               
        form.addInternalFrameListener(
          new InternalFrameAdapter() {
            public void internalFrameClosed(InternalFrameEvent ievt) {
              adicLanca(((FSubLanca)ievt.getSource()).getValores());
            }
          }
        );
      }
    }
  }
	/*
	* Se o lancamento nao for uma transferencia e nao ter origem 
	* nesta conta ele nao pode ser editado
	*/
  private void editar() {
	if ((tab.getLinhaSel() >= 0) & (validaPeriodo())) {
		if ((tab.getValor(tab.getLinhaSel(),3).equals("N")) & (tab.getValor(tab.getLinhaSel(),2).equals("N"))) {
			Funcoes.mensagemInforma(this,"Este lançamento não pode ser editato nesta conta!");
		}
		else if ((tab.getValor(tab.getLinhaSel(),3).equals("N")) & (tab.getValor(tab.getLinhaSel(),2).equals("S"))) {
			DLDataTransf dl = new DLDataTransf(this);
			dl.setVisible(true);
			if (!dl.OK) {
				dl.dispose();
				return;
			}
			Date dDtNova = dl.getValor();
			dl.dispose();
			String sSQL = "UPDATE FNSUBLANCA SET DATASUBLANCA=? WHERE CODLANCA = ? AND CODEMP=? AND CODFILIAL=? AND CODSUBLANCA > 0";
			try {
			  PreparedStatement ps = con.prepareStatement(sSQL);
			  ps.setDate(1,Funcoes.dateToSQLDate(dDtNova));
			  ps.setInt(2,Integer.parseInt((String)tab.getValor(tab.getLinhaSel(),0)));
			  ps.setInt(3,Aplicativo.iCodEmp);
			  ps.setInt(4,ListaCampos.getMasterFilial("FNSUBLANCA"));
			  ps.executeUpdate();
			  ps.close();
			  if (!con.getAutoCommit())
			    con.commit();
			}
			catch (SQLException err) {
			  Funcoes.mensagemErro(this,"Erro ao atualizar a data da transferência!\n"+err.getMessage(),true,con,err);
			}
			tab.setValor(Funcoes.dateToStrDate(dDtNova),tab.getLinhaSel(),1);
				
		}
		else {
			Container cont = getContentPane();
			while (true) { 
				if (cont instanceof FPrincipal) 
					break;
				cont = cont.getParent();
			}
			if (!((FPrincipal)cont).temTela("FSubLanca")) {
				FSubLanca form = new FSubLanca((String)tab.getValor(tab.getLinhaSel(),0),sCodPlan,dIniLanca,dFimLanca);
				((FPrincipal)cont).criatela("FSubLanca",form,con);               
				form.addInternalFrameListener(
					new InternalFrameAdapter() {
						public void internalFrameClosed(InternalFrameEvent ievt) {
							altLanca(((FSubLanca)ievt.getSource()).getValores());
						}
					}
				);
			}
		}
	}
  }
  private void adicLanca(String[] sVals) {
    int iLin = -1;
    if ((sVals[0].length() > 0) &&
        (testaCodLanca(Integer.parseInt(sVals[0]))) &&
        (sCodPlan.equals(sVals[6])) &&
        (!dIniLanca.after(Funcoes.strDateToDate(sVals[1]))) &&
        (!dFimLanca.before(Funcoes.strDateToDate(sVals[1])))) {
      for (int i=0; i<tab.getNumLinhas(); i++) {
        if (((String)tab.getValor(i,0)).trim().equals(sVals[0])) {
          tab.tiraLinha(i);
          break;
        }
      }
      tab.adicLinha();
      iLin = tab.getNumLinhas()-1;
      tab.setValor(sVals[0],iLin,0);
      tab.setValor(sVals[1],iLin,1);
      tab.setValor(sVals[2],iLin,2);
      tab.setValor("S",iLin,3);
      if (sVals[2].equals("S"))
        tab.setValor(sConta,iLin,4);
      else 
        tab.setValor("",iLin,4);
      tab.setValor(sVals[3],iLin,5);
      tab.setValor(sVals[4],iLin,6);
      tab.setValor(sVals[5],iLin,7);
    }
    lbAtualSaldoVal.setText("NÃO");
  }
  private void altLanca(String[] sVals) {
    int iLin = -1;
    if ((sCodPlan.equals(sVals[6])) &
       (!dIniLanca.after(Funcoes.strDateToDate(sVals[1]))) &
       (!dFimLanca.before(Funcoes.strDateToDate(sVals[1])))) {
      for (int i=0; i<tab.getNumLinhas(); i++) {
        if (((String)tab.getValor(i,0)).trim().equals(sVals[0])) {
          iLin = i;
          break;
        }
      }
      tab.setValor(sVals[0],iLin,0);
      tab.setValor(sVals[1],iLin,1);
      tab.setValor(sVals[2],iLin,2);
      tab.setValor("S",iLin,3);
      if (sVals[2].equals("S"))
        tab.setValor(sConta,iLin,4);
      tab.setValor(sVals[3],iLin,5);
      tab.setValor(sVals[4],iLin,6);
      tab.setValor(sVals[5],iLin,7);
    }
    lbAtualSaldoVal.setText("NÃO");
  }
  private boolean testaCodLanca(int iCodLanca) {
    boolean bRetorno = false;
    try {
      PreparedStatement ps = con.prepareStatement("SELECT CODLANCA FROM FNLANCA WHERE CODLANCA=? AND CODEMP=? AND CODFILIAL=?");
      ps.setInt(1,iCodLanca);
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNLANCA"));
      if ((ps.executeQuery()).next()) 
        bRetorno = true;
      
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao testar o código do lnaçamento!\n"+err.getMessage(),true,con,err);
    }
    return bRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) 
      dispose();
    else if (evt.getSource() == btPrim)
      prim();
    else if (evt.getSource() == btAnt)
      ant();
    else if (evt.getSource() == btProx)
      prox();
    else if (evt.getSource() == btUlt)
      ult();
    else if (evt.getSource() == btNovo)
      novo();
    else if (evt.getSource() == btEditar)
      editar();
    else if (evt.getSource() == btExcluir)
      excluir();
    else if (evt.getSource() == btExec) {
      if (validaPeriodo()) {
        montaTabela(dIniLanca,dFimLanca);
      }
    }
    else if (evt.getSource() == btCalcSaldo) {
      if (validaPeriodo())
        atualizaSaldo();
    }
  }
  public void stateChanged(ChangeEvent cevt) {
    sCodPlan = sPlanos[tpn.getSelectedIndex()];
    sConta = sContas[tpn.getSelectedIndex()];
    if (validaPeriodo()) {
      montaTabela(dIniLanca,dFimLanca);
    }
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    montaTabs();
  }
}