/**
 * @version 08/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FAgenda.java <BR>
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
 * Classe para controle de agenda.
 * 
 */

package org.freedom.modulos.tmk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.freedom.componentes.JTabbedPanePad;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;


public class FAgenda extends FFilho implements JComboBoxListener, ActionListener {
  private JPanelPad pinCabAgd = new JPanelPad(0,120);
  private JPanel pnAgd = new JPanel(new BorderLayout());
  private JPanel pnRodAgd = new JPanel(new BorderLayout());
  private JTabbedPanePad tpnAgd = new JTabbedPanePad();
  private Tabela tabAgd = new Tabela();
  private JScrollPane spnAgd = new JScrollPane(tabAgd);
  private JTextFieldPad txtIdUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btNovo = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExcluir = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JComboBoxPad cbPeriodo = null;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private ListaCampos lcUsu = new ListaCampos(this);
  private Vector vCodAgds = new Vector();
  public FAgenda() {
  	setTitulo("Agenda");
  	setAtribos(20,20,540,400);

	vVals.addElement("HO");
	vVals.addElement("PD");
	vVals.addElement("PT");
	vVals.addElement("PS");
	vVals.addElement("PM");
	vLabs.addElement("Hoje");
	vLabs.addElement("Próximo dia");
	vLabs.addElement("Próximo três dias");
	vLabs.addElement("Próxima semana");
	vLabs.addElement("Próximo mes");
	cbPeriodo = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0);
  	
  	txtDataini.setVlrDate(new Date());
  	txtDatafim.setVlrDate(new Date());
  	
	lcUsu.add(new GuardaCampo( txtIdUsu, "IdUsu", "ID usuario", ListaCampos.DB_PK, txtNomeUsu, false));
	lcUsu.add(new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome do usuario", ListaCampos.DB_SI, false));
	lcUsu.montaSql(false, "USUARIO", "SG");    
	lcUsu.setReadOnly(true);
	txtIdUsu.setTabelaExterna(lcUsu);
	txtIdUsu.setFK(true);
	txtIdUsu.setNomeCampo("IdUsu");
    
  	tpnAgd.add("Agenda do usuário",pnAgd);
  	
  	pnAgd.add(pinCabAgd,BorderLayout.NORTH);
	pnAgd.add(spnAgd,BorderLayout.CENTER);
	
	getTela().add(tpnAgd);
	
	pinCabAgd.adic(new JLabelPad("ID"),7,10,250,20);
	pinCabAgd.adic(txtIdUsu,7,30,80,20);
	pinCabAgd.adic(new JLabelPad("Nome do usuário"),90,10,250,20);
	pinCabAgd.adic(txtNomeUsu,90,30,197,20);
	pinCabAgd.adic(new JLabelPad("Periodo rápido"),290,10,200,20);
	pinCabAgd.adic(cbPeriodo,290,30,200,20);
	pinCabAgd.adic(new JLabelPad("Período:"),7,50,200,20);
	pinCabAgd.adic(txtDataini,7,70,100,20);
	pinCabAgd.adic(txtDatafim,110,70,100,20);
	pinCabAgd.adic(btExec,220,60,30,30);
	
	tabAgd.adicColuna("Ind.");
	tabAgd.adicColuna("Sit.");
	tabAgd.adicColuna("Data ini.");
	tabAgd.adicColuna("Hora ini.");
	tabAgd.adicColuna("Data fim.");
	tabAgd.adicColuna("Hora fim.");
	tabAgd.adicColuna("Assunto");
	
	tabAgd.setTamColuna(40,0);
	tabAgd.setTamColuna(40,1);
	tabAgd.setTamColuna(80,2);
	tabAgd.setTamColuna(70,3);
	tabAgd.setTamColuna(80,4);
	tabAgd.setTamColuna(70,5);
	tabAgd.setTamColuna(145,6);
	
	JPanel pnBot = new JPanel(new GridLayout(1,2));
	pnBot.setPreferredSize(new Dimension(60,30));
	pnBot.add(btNovo);
	pnBot.add(btExcluir);
	
	pnRodAgd.add(pnBot,BorderLayout.WEST);
	
	btSair.setPreferredSize(new Dimension(110,30));        
	pnRodAgd.add(btSair,BorderLayout.EAST);
	
	btSair.addActionListener(this);
	
	tabAgd.addMouseListener(
	  new MouseAdapter() {
	  	public void mouseClicked(MouseEvent mevt) {
	  	  if (mevt.getClickCount() == 2) {
	  	  	editaAgd();
	  	  }
	  	}
	  }
	);
	
	pnAgd.add(pnRodAgd,BorderLayout.SOUTH);
	
	btNovo.addActionListener(this);
	btExcluir.addActionListener(this);
	btExec.addActionListener(this);
	cbPeriodo.addComboBoxListener(this);
}
  private void carregaTabAgd() {
  	String sSQL = "SELECT A.CODAGD,A.SITAGD,A.DTAINIAGD,A.HRINIAGD,A.DTAFIMAGD,A.HRFIMAGD,A.ASSUNTOAGD" +
 		                  " FROM SGAGENDA A WHERE A.CODEMPUD=? AND A.CODFILIALUD=? AND A.IDUSUD=? " +
 		                  " AND DTAINIAGD BETWEEN ? AND ?" +
  		                  " ORDER BY A.DTAINIAGD DESC,A.HRINIAGD DESC,A.DTAFIMAGD DESC,A.HRFIMAGD DESC";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,Aplicativo.iCodFilialPad);
      ps.setString(3,txtIdUsu.getVlrString());
      ps.setDate(4,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(5,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      ResultSet rs = ps.executeQuery();
      tabAgd.limpa();
      vCodAgds.clear();
      for (int i=0;rs.next();i++) {
      	vCodAgds.addElement(rs.getString("CodAgd")); 
      	tabAgd.adicLinha();
		tabAgd.setValor(rs.getString("CodAgd"),i,0);
		tabAgd.setValor(rs.getString("SitAgd"),i,1);
		tabAgd.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtaIniAgd")),i,2);
		tabAgd.setValor(rs.getString("HrIniAgd"),i,3);
      	tabAgd.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DtaFimAgd")),i,4);
      	tabAgd.setValor(rs.getString("HrFimAgd"),i,5);
      	tabAgd.setValor(rs.getString("AssuntoAgd"),i,6);
      }
      rs.close();
      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar agenda!\n"+err.getMessage());
    }
  }
  private void editaAgd() {
  	int iLin = 0;
  	if ((iLin = tabAgd.getLinhaSel()) < 0) {
  		Funcoes.mensagemInforma(this,"Não ha nenhum agendamento selecionado!");
  		return;
  	}
  	String sSQL = "SELECT DTAINIAGD, HRINIAGD, DTAFIMAGD, HRFIMAGD," +
  			              "ASSUNTOAGD,DESCAGD,TIPOAGD,IDUSUD FROM SGAGENDA " +
  			              "WHERE CODEMP=? AND CODFILIAL=? AND CODAGD=?";
  	try {
  		PreparedStatement ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGAGENDA"));
  		ps.setInt(3,Integer.parseInt((String)tabAgd.getValor(iLin,0)));
  		ResultSet rs = ps.executeQuery();
  		if (rs.next()) {
  			GregorianCalendar calIni = new GregorianCalendar();
  			GregorianCalendar calFim = new GregorianCalendar();
  			DLNovoAgen dl = new DLNovoAgen(rs.getString("IdUsuD"),this);
  			dl.setConexao(con);
  			calIni.setTime(rs.getTime("HrIniAgd"));
  			calFim.setTime(rs.getTime("HrFimAgd"));
  			dl.setValores(
  					new String[] {
							   Funcoes.sqlDateToStrDate(rs.getDate("DtaIniAgd")),
							   Funcoes.strZero(""+calIni.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+calIni.get(Calendar.MINUTE),2),
							   Funcoes.sqlDateToStrDate(rs.getDate("DtaFimAgd")),
							   Funcoes.strZero(""+calFim.get(Calendar.HOUR_OF_DAY),2)+":"+Funcoes.strZero(""+calIni.get(Calendar.MINUTE),2),
							   rs.getString("AssuntoAgd"),
							   rs.getString("DescAgd"),
							   rs.getString("TipoAgd"),
						   }
  			);
  			dl.setVisible(true);
  			if (dl.OK) {
  			String[] sRets = dl.getValores();
  				try {
  					sSQL = "EXECUTE PROCEDURE SGSETAGENDASP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  					PreparedStatement ps2 = con.prepareStatement(sSQL);
  					ps2.setInt(1,Integer.parseInt((String)tabAgd.getValor(iLin,0)));
  					ps2.setInt(2,Aplicativo.iCodEmp);
  					ps2.setString(3,"AGEND");
  					ps2.setInt(4,0);
  					ps2.setInt(5,0);
  					ps2.setDate(6,Funcoes.strDateToSqlDate(sRets[0]));
  					ps2.setString(7,sRets[1]+":00");
  					ps2.setDate(8,Funcoes.strDateToSqlDate(sRets[2]));
  					ps2.setString(9,sRets[3]+":00");
  					ps2.setString(10,sRets[4]);
  					ps2.setString(11,sRets[5]);
  					ps2.setString(12,sRets[6]);
  					ps2.setInt(13,5);
  					ps2.setInt(14,Aplicativo.iCodFilialPad);
  					ps2.setString(15,Aplicativo.strUsuario);
  					ps2.setString(16,sRets[7]);
  					ps2.setString(17,sRets[8]);
  					ps2.execute();
  					ps2.close();
  				}
  				catch(SQLException err) {
  					Funcoes.mensagemErro(this,"Erro ao salvar o agendamento!\n"+err.getMessage());
  				}
  				carregaTabAgd();
  			}
  		    dl.dispose();
  		}
  		rs.close();
  		ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch (SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao buscar informações!\n"+err.getMessage());
  		err.printStackTrace();
  	}
  }	
  private void excluiAgd() {
  	if (tabAgd.getLinhaSel() == -1) { 
		Funcoes.mensagemInforma(this,"Selecione um item na lista!");
  	  return;
    } 
  	else if (Funcoes.mensagemConfirma(this,"Deseja relamente excluir o agendamento '"+vCodAgds.elementAt(tabAgd.getLinhaSel())+"'?") != JOptionPane.YES_OPTION) {
  		return;
  	}
    try {
	  String sSQL = "DELETE FROM SGAGENDA WHERE CODAGD=? AND CODEMP=? AND CODFILIAL=?";
	  PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setString(1,""+vCodAgds.elementAt(tabAgd.getLinhaSel()));
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("SGAGENDA"));
	  ps.execute();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
	}
	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao excluir agendamento!\n"+err.getMessage());
	}
	carregaTabAgd();
  }
  private void novoAgd() {
  	if (txtIdUsu.getVlrString().equals("")) {
		Funcoes.mensagemInforma(this,"Não ha nenhum usuário selecionado!");
  		txtIdUsu.requestFocus();
  		return;
  	}
  	String sRets[];
  	DLNovoAgen dl = new DLNovoAgen(txtIdUsu.getVlrString(),this);
  	dl.setConexao(con);
  	dl.setVisible(true);
  	if (dl.OK) {
  	  sRets = dl.getValores();
  	  try {
	    String sSQL = "EXECUTE PROCEDURE SGSETAGENDASP(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement ps = con.prepareStatement(sSQL);
	    ps.setInt(1,Aplicativo.iCodEmp);
	    ps.setString(2,"AGEND");
	    ps.setInt(3,0);
	    ps.setInt(4,0);
	    ps.setDate(5,Funcoes.strDateToSqlDate(sRets[0]));
	    ps.setString(6,sRets[1]+":00");
	    ps.setDate(7,Funcoes.strDateToSqlDate(sRets[2]));
	    ps.setString(8,sRets[3]+":00");
	    ps.setString(9,sRets[4]);
	    ps.setString(10,sRets[5]);
	    ps.setString(11,sRets[6]);
	    ps.setInt(12,5);
	    ps.setInt(13,Aplicativo.iCodFilialPad);
	    ps.setString(14,Aplicativo.strUsuario);
	    ps.setString(15,sRets[7]);
	    ps.setString(16,sRets[8]);
	    ps.execute();
	    ps.close();
	    if (!con.getAutoCommit())
	    	con.commit();
      }
  	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao salvar o agendamento!\n"+err.getMessage());
  	  }
	  carregaTabAgd();
    }
    dl.dispose();
  }
  public void valorAlterado(JComboBoxEvent evt) {
  	 if (evt.getComboBoxPad() == cbPeriodo) {
  	 	GregorianCalendar calHoje = new GregorianCalendar();
  	 	GregorianCalendar cal = new GregorianCalendar();
  	 	if (cbPeriodo.getVlrString().equals("HO"))
  	 		cal.add(Calendar.DATE,0);
  	 	else if (cbPeriodo.getVlrString().equals("PD"))
  	 		cal.add(Calendar.DATE,1);
  	 	else if (cbPeriodo.getVlrString().equals("PT"))
  	 		cal.add(Calendar.DATE,3);
  	 	else if (cbPeriodo.getVlrString().equals("PS"))
  	 		cal.add(Calendar.WEEK_OF_MONTH,1);
  	 	else if (cbPeriodo.getVlrString().equals("PM"))
  	 		cal.add(Calendar.MONTH,1);
  	 	txtDataini.setVlrDate(calHoje.getTime());
  	 	txtDatafim.setVlrDate(cal.getTime());
  	 	btExec.doClick();
  	 }
  }
  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btSair) {
		dispose();
  	}
  	else if (evt.getSource() == btNovo) {
  		novoAgd();
  	}
	else if (evt.getSource() == btExcluir) {
		excluiAgd();
	}
	else if (evt.getSource() == btExec &&
			   !txtDataini.getVlrString().equals("") &&
			   !txtDatafim.getVlrString().equals("")) {
		carregaTabAgd();
	}
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	lcUsu.setConexao(cn);
  }
}
