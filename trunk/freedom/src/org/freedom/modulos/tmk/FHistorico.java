/**
 * @version 05/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FHistorico.java <BR>
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
 * Classe para gravação de histórico de contatos realizados.
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
import java.sql.Types;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.bmps.Icone;

public class FHistorico extends FFilho implements CarregaListener, ActionListener {
  private Painel pinCabCont = new Painel(530,200);
  private JPanel pnCabCont = new JPanel(new BorderLayout());
  private JPanel pnCont = new JPanel(new BorderLayout());
  private JPanel pnRodCont = new JPanel(new BorderLayout());
  private JTabbedPane tpnCont = new JTabbedPane();
  private Tabela tabCont = new Tabela();
  private JScrollPane spnCont = new JScrollPane(tabCont);
  private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtTelCont = new JTextFieldFK(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtFaxCont = new JTextFieldFK(JTextFieldPad.TP_STRING,9,0);
  private JTextFieldFK txtEmpCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtEmailCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtEndCont = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtNumCont = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtComplCont = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldFK txtBairCont = new JTextFieldFK(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldFK txtCidCont = new JTextFieldFK(JTextFieldPad.TP_INTEGER,30,0);
  private JTextFieldFK txtUfCont = new JTextFieldFK(JTextFieldPad.TP_STRING,2,0);
  private JButton btNovo = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExcluir = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcCont = new ListaCampos(this);
  private Connection con = null;
  private Vector vCodHists = new Vector();
  private Vector vCodAtends = new Vector();
  public FHistorico() {
  	setTitulo("Histórico de contatos");
  	setAtribos(20,20,540,400);
  	
	lcCont.add(new GuardaCampo( txtCodCont, 7, 20, 80, 20, "CodCto", "Cód.Cont", true, false, txtNomeCont, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtNomeCont, 7, 20, 200, 20, "NomeCto", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtTelCont, 7, 20, 200, 20, "FoneCto", "Fone.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtFaxCont, 7, 20, 200, 20, "FaxCto", "Fax.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtEmpCont, 7, 20, 200, 20, "RazCto", "Empresa", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtEndCont, 7, 20, 200, 20, "EndCto", "Endereco", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtNumCont, 7, 20, 200, 20, "NumCto", "Numero", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtComplCont, 7, 20, 200, 20, "ComplCto", "Compl.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtBairCont, 7, 20, 200, 20, "BairCto", "Compl.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtCidCont, 7, 20, 200, 20, "CidCto", "Compl.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.add(new GuardaCampo( txtUfCont, 7, 20, 200, 20, "UfCto", "Compl.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
	lcCont.montaSql(false, "CONTATO", "TK");    
	lcCont.setReadOnly(true);
	txtCodCont.setTabelaExterna(lcCont);
	txtCodCont.setFK(true);
	txtCodCont.setNomeCampo("CodCto");
	txtTelCont.setMascara(JTextFieldPad.MC_FONEDDD);
	txtFaxCont.setMascara(JTextFieldPad.MC_FONE);
    
  	tpnCont.add("Contato",pnCont);
  	
  	pnCabCont.add(pinCabCont,BorderLayout.CENTER);
  	pnCabCont.setPreferredSize(new Dimension(500,200));
  	
  	pnCont.add(pnCabCont,BorderLayout.NORTH);
	pnCont.add(spnCont,BorderLayout.CENTER);
	
	getTela().add(tpnCont);
	
	pinCabCont.adic(new JLabel("Codigo e nome do contato"),7,10,250,20);
	pinCabCont.adic(txtCodCont,7,30,80,20);
	pinCabCont.adic(txtNomeCont,90,30,197,20);
	pinCabCont.adic(new JLabel("Tel."),290,10,97,20);
	pinCabCont.adic(txtTelCont,290,30,97,20);
	pinCabCont.adic(new JLabel("Fax"),390,10,97,20);
	pinCabCont.adic(txtFaxCont,390,30,97,20);
	pinCabCont.adic(new JLabel("Empresa"),7,50,270,20);
	pinCabCont.adic(txtEmpCont,7,70,270,20);
	pinCabCont.adic(new JLabel("E-mail"),280,50,207,20);
	pinCabCont.adic(txtEmailCont,280,70,207,20);
	pinCabCont.adic(new JLabel("Endereco"),7,90,200,20);
	pinCabCont.adic(txtEndCont,7,110,200,20);
	pinCabCont.adic(new JLabel("Num."),210,90,67,20);
	pinCabCont.adic(txtNumCont,210,110,67,20);
	pinCabCont.adic(new JLabel("Compl."),280,90,47,20);
	pinCabCont.adic(txtComplCont,280,110,47,20);
	pinCabCont.adic(new JLabel("Bairro."),330,90,157,20);
	pinCabCont.adic(txtBairCont,330,110,157,20);
	pinCabCont.adic(new JLabel("Cidade."),7,130,180,20);
	pinCabCont.adic(txtCidCont,7,150,180,20);
	pinCabCont.adic(new JLabel("UF"),190,130,30,20);
	pinCabCont.adic(txtUfCont,190,150,30,20);
	
	tabCont.adicColuna("Ind.");
	tabCont.adicColuna("Sit.");
	tabCont.adicColuna("Data");
	tabCont.adicColuna("Histórico");
	tabCont.adicColuna("Usuário");
	tabCont.adicColuna("Hora");
	
	tabCont.setTamColuna(40,0);
	tabCont.setTamColuna(30,1);
	tabCont.setTamColuna(90,2);
	tabCont.setTamColuna(160,3);
	tabCont.setTamColuna(130,4);
	tabCont.setTamColuna(70,5);
	
	JPanel pnBotCont = new JPanel(new GridLayout(1,2));
	pnBotCont.setPreferredSize(new Dimension(60,30));
	pnBotCont.add(btNovo);
	pnBotCont.add(btExcluir);
	
	pnRodCont.add(pnBotCont,BorderLayout.WEST);
	
	btSair.setPreferredSize(new Dimension(110,30));        
	pnRodCont.add(btSair,BorderLayout.EAST);
	
	btSair.addActionListener(this);
	
	tabCont.addMouseListener(
	  new MouseAdapter() {
	  	public void mouseClicked(MouseEvent mevt) {
	  	  if (mevt.getClickCount() == 2) {
	  	  	editaHist();
	  	  }
	  	}
	  }
	);
	
	pnCont.add(pnRodCont,BorderLayout.SOUTH);
	
	btNovo.addActionListener(this);
	btExcluir.addActionListener(this);
	lcCont.addCarregaListener(this);
	
  }
  private void carregaTabCont() {
  	String sSQL = "SELECT H.CODHISTTK,H.SITHISTTK,H.DATAHISTTK,H.DESCHISTTK,A.NOMEATEND,H.HORAHISTTK,H.CODATEND" +
  		                  " FROM TKHISTORICO H, ATATENDENTE A WHERE H.CODCTO=? AND H.CODEMPCO=? AND H.CODFILIALCO=?" +
  	                      " AND A.CODATEND=H.CODATEND AND A.CODEMP=H.CODEMPAE AND A.CODFILIAL=H.CODFILIALAE " +
  		                  " ORDER BY H.DATAHISTTK DESC,H.HORAHISTTK DESC";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodCont.getVlrInteger().intValue());
      ps.setInt(2,Aplicativo.iCodEmp);
      ps.setInt(3,lcCont.getCodFilial());
      ResultSet rs = ps.executeQuery();
      tabCont.limpa();
      vCodHists.clear();
      vCodAtends.clear();
      for (int i=0;rs.next();i++) {
      	vCodHists.addElement(rs.getString("CodHistTK")); 
      	vCodAtends.addElement(rs.getString("CodAtend")); 
      	tabCont.adicLinha();
		tabCont.setValor(rs.getString("CodHistTK"),i,0);
		tabCont.setValor(rs.getString("SitHistTK"),i,1);
      	tabCont.setValor(Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK")),i,2);
      	tabCont.setValor(rs.getString("DescHistTK"),i,3);
		tabCont.setValor(rs.getString("NomeAtend"),i,4);
		tabCont.setValor(rs.getString("HoraHistTK"),i,5);
      }
      rs.close();
      ps.close();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar tabela de históricos!\n"+err.getMessage());
    }
  }
  private void editaHist() {
  	int iLin = 0;
  	if ((iLin = tabCont.getLinhaSel()) < 0) {
  		Funcoes.mensagemInforma(this,"Não ha nenhum histórico selecionado!");
  		return;
  	}
  	String sRets[];
  	DLNovoHist dl = new DLNovoHist(txtCodCont.getVlrInteger().intValue(),this);
  	dl.setConexao(con);
  	dl.setValores(
  			new String[] {
					   (String)tabCont.getValor(iLin,3),
					   (String)vCodAtends.elementAt(iLin),
					   (String)tabCont.getValor(iLin,1)
  			}
  	);
  	dl.setVisible(true);
  	if (dl.OK) {
  		sRets = dl.getValores();
  		try {
  			String sSQL = "SELECT IRET FROM TKSETHISTSP(?,?,?,?,?,?,?,?,?,?)";
  			PreparedStatement ps = con.prepareStatement(sSQL);
  			ps.setInt(1,Integer.parseInt((String)tabCont.getValor(iLin,0)));
  			ps.setInt(2,Aplicativo.iCodEmp);
  			ps.setInt(3,lcCont.getCodFilial());
  			ps.setInt(4,txtCodCont.getVlrInteger().intValue());
            ps.setNull(5,Types.INTEGER);  //Filialcli
            ps.setNull(6,Types.INTEGER);  //Codcli
            ps.setString(7,sRets[0]);
  			ps.setInt(8,ListaCampos.getMasterFilial("ATATENDENTE"));
  			ps.setString(9,sRets[1]);
  			ps.setString(10,sRets[2]);
  			ps.executeQuery();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();
  		}
  		catch(SQLException err) {
  			Funcoes.mensagemErro(this,"Erro ao salvar o histórico!\n"+err.getMessage());
  		}
  		carregaTabCont();
  	}
  	dl.dispose();
  }
  private void excluiHist() {
  	if (tabCont.getLinhaSel() == -1) { 
		Funcoes.mensagemInforma(this,"Selecione um item na lista!");
  	  return;
    } 
  	else if (Funcoes.mensagemConfirma(this,"Deseja relamente excluir o histórico '"+vCodHists.elementAt(tabCont.getLinhaSel())+"'?") != JOptionPane.YES_OPTION) {
  		return;
  	}
    try {
	  String sSQL = "DELETE FROM TKHISTORICO WHERE CODHISTTK=? AND CODEMP=? AND CODFILIAL=?";
	  PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setString(1,""+vCodHists.elementAt(tabCont.getLinhaSel()));
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("TKHISTORICO"));
	  ps.execute();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
	}
	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao excluir o histórico!\n"+err.getMessage());
	}
	carregaTabCont();
  }
  private void novoHist() {
  	if (txtCodCont.getVlrInteger().intValue() == 0) {
		Funcoes.mensagemInforma(this,"Não ha nenhum contato selecionado!");
  		txtCodCont.requestFocus();
  		return;
  	}
  	String sRets[];
  	DLNovoHist dl = new DLNovoHist(txtCodCont.getVlrInteger().intValue(),this);
  	dl.setConexao(con);
  	dl.setVisible(true);
  	if (dl.OK) {
  	  sRets = dl.getValores();
  	  int iCodHist = 0;
  	  try {
        String sSQL = "SELECT IRET FROM TKSETHISTSP(0,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.setInt(2,lcCont.getCodFilial());
        ps.setInt(3,txtCodCont.getVlrInteger().intValue());
        ps.setNull(4,Types.INTEGER);  //Filialcli
        ps.setNull(5,Types.INTEGER);  //Codcli
        ps.setString(6,sRets[0]);
        ps.setInt(7,ListaCampos.getMasterFilial("ATATENDENTE"));
	    ps.setString(8,sRets[1]);
	    ps.setString(9,sRets[2]);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	iCodHist = rs.getInt("IRet");
	    }
	    rs.close();
	    ps.close();
	    if (!con.getAutoCommit())
	    	con.commit();
	    if (sRets[3] != null) {
	    	sSQL = "EXECUTE PROCEDURE SGSETAGENDASP(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        ps = con.prepareStatement(sSQL);
	        ps.setInt(1,Aplicativo.iCodEmp);
	        ps.setString(2,"HISTO");
	        ps.setInt(3,ListaCampos.getMasterFilial("TKHISTORICO"));
	        ps.setInt(4,iCodHist);
	        ps.setDate(5,Funcoes.strDateToSqlDate(sRets[3]));
	        ps.setString(6,sRets[4]+":00");
	        ps.setDate(7,Funcoes.strDateToSqlDate(sRets[5]));
	        ps.setString(8,sRets[6]+":00");
	        ps.setString(9,sRets[7]);
	        ps.setString(10,sRets[8]);
	        ps.setString(11,sRets[9]);
	        ps.setInt(12,5);
	        ps.setInt(13,Aplicativo.iCodFilialPad);
	        ps.setString(14,Aplicativo.strUsuario);
	        ps.setString(15,sRets[10]);
	        ps.setString(16,sRets[11]);
	        ps.execute();
	        ps.close();
	        if (!con.getAutoCommit())
	        	con.commit();
	    }
    }
  	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao salvar o histórico!\n"+err.getMessage());
  	  }
	  carregaTabCont();
    }
    dl.dispose();
  }
  public void afterCarrega(CarregaEvent cevt) {  }
  public void beforeCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcCont) {
  		carregaTabCont();
  	}
  }
  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btSair) {
		dispose();
  	}
  	else if (evt.getSource() == btNovo) {
  		novoHist();
  	}
	else if (evt.getSource() == btExcluir) {
		excluiHist();
	}
  }
  public void setConexao(Connection cn) {
  	con = cn;
  	lcCont.setConexao(cn);
  }
}
