/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FUsuario.java <BR>
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
 * Formulário de cadastro de usuários do sistema.
 * 
 */

package org.freedom.modulos.cfg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.bmps.Icone;

public class FUsuario extends FDados implements PostListener, DeleteListener, InsertListener, CarregaListener, ActionListener {
  private JTextFieldPad txtCodUsu = new JTextFieldPad();
  private JTextFieldPad txtNomeUsu = new JTextFieldPad();
  private JTextFieldPad txtPNomeUsu = new JTextFieldPad();
  private JTextFieldPad txtUNomeUsu = new JTextFieldPad();
  private JTextFieldPad txtCodGrup = new JTextFieldPad();
  private JTextFieldFK txtDescGrup = new JTextFieldFK();
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextAreaPad txaComentUsu = new JTextAreaPad();
  private JPasswordFieldPad txpSenha = new JPasswordFieldPad(8);
  private JPasswordFieldPad txpConfirma = new JPasswordFieldPad(8);
  private static Vector vEmp = new Vector();
  private static Vector vDisp =  new Vector();
  private static Vector vCodEmp = new Vector();
  private static Vector vCodDisp =  new Vector();
  private JList lsEmp = new JList();
  private JList lsDisp = new JList();
  private JScrollPane spnObs = new JScrollPane(txaComentUsu);
  private JScrollPane spnEmp = new JScrollPane(lsEmp);
  private JScrollPane spnDisp = new JScrollPane(lsDisp);
  private JButton btAdicEmp = new JButton(Icone.novo("btFlechaDir.gif"));
  private JButton btDelEmp = new JButton(Icone.novo("btFlechaEsq.gif"));
  private Connection con = null;  
  private ListaCampos lcGrup = new ListaCampos(this,"IG");
  private ListaCampos lcCC = new ListaCampos(this,"CC");
  private JCheckBoxPad cbBaixoCusto = new JCheckBoxPad("Permite vendas abaixo do custo","S","N");
  private Connection conIB = null;
  public FUsuario () {
    setTitulo("Cadastro de Usuários");
    setAtribos( 50, 50, 395, 470);

    txpSenha.setListaCampos(lcCampos);
    txpConfirma.setListaCampos(lcCampos);

    lcGrup.add(new GuardaCampo( txtCodGrup, 7, 100, 80, 20, "IDGRPUSU", "ID Grupo", true, false, txtDescGrup, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcGrup.add(new GuardaCampo( txtDescGrup, 7, 100, 180, 20, "NOMEGRPUSU", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcGrup.montaSql(false, "GRPUSU", "SG");    
    lcGrup.setQueryCommit(false);
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);

    lcCC.add(new GuardaCampo( txtCodCC, 7, 100, 80, 20, "CodCC", "Código", true, false, txtDescCC, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcCC.add(new GuardaCampo( txtDescCC, 7, 100, 180, 20, "DescCC", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcCC.montaSql(false, "CC", "FN");    
    lcCC.setQueryCommit(false);
    lcCC.setReadOnly(true);
    txtCodCC.setTabelaExterna(lcCC);
    
    adicCampo(txtCodUsu, 7, 20, 80, 20, "IDUsu", "ID", JTextFieldPad.TP_STRING, 8, 0, true, false, null, true);
    adicCampo(txtNomeUsu, 90, 20, 280, 20, "NomeUsu", "Nome", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
    adicCampo(txtPNomeUsu, 7, 60, 180, 20, "PNomeUsu", "Primeiro Nome", JTextFieldPad.TP_STRING, 20, 0, false, false, null, true);
    adicCampo(txtUNomeUsu, 190, 60, 180, 20, "UNomeUsu", "Ultimo Nome", JTextFieldPad.TP_STRING, 20, 0, false, false, null, true);
    adicCampo(txtCodGrup, 7, 100, 50, 20, "IDGRPUSU", "ID Grupo", JTextFieldPad.TP_STRING, 8, 0, false, true, txtDescGrup,false);
    adicDescFK(txtDescGrup, 60, 100, 197, 20, "NOMEGRPUSU", "e descrição do grupo", JTextFieldPad.TP_STRING, 50, 0);
    adic(new JLabel("Senha"),260,80,57,20);
    adic(txpSenha,260,100,57,20);
    adic(new JLabel("Confirma"),320,80,50,20);
    adic(txpConfirma,320,100,50,20);
    adicCampo(txtCodCC, 7, 140, 110, 20, "CodCC", "Código", JTextFieldPad.TP_STRING, 19, 0, false, true, txtDescCC,false);
    adicDescFK(txtDescCC, 120, 140, 250, 20, "DescCC", "e descrição do centro de custo", JTextFieldPad.TP_STRING, 50, 0);
    
    adicDBLiv(txaComentUsu, "ComentUsu", "Comentário",JTextFieldPad.TP_STRING, false);
    adic(new JLabel("Comentário"),7,160,100,20);
    adic(spnObs,7,180,362,60);
    adic(new JLabel("Filiais disponíveis:"),7,240,120,20);
    adic(spnDisp,7,260,160,100);
    adic(btAdicEmp,175,275,30,30);
    adic(btDelEmp,175,315,30,30);
    adic(new JLabel("Acesso:"),212,240,158,20);
    adic(spnEmp,212,260,158,100);
    adicDB(cbBaixoCusto, 7, 370, 300, 20, "BaixoCustoUsu", "", JTextFieldPad.TP_STRING, false);
    
    setListaCampos( false, "USUARIO", "SG");
    lcCampos.addCarregaListener(this);
    lcCampos.addPostListener(this);
	lcCampos.addInsertListener(this);
    lcCampos.addDeleteListener(this);
    lcCampos.setQueryInsert(false);    
    
    btAdicEmp.addActionListener(this);
    btDelEmp.addActionListener(this);
  }
  private void adicionaEmp() {
  	if (lsDisp.isSelectionEmpty()) 
  	  return;
  	for (int i=lsDisp.getMaxSelectionIndex();i>=0;i--) {
  	  if (lsDisp.isSelectedIndex(i)) {
        vEmp.add(vDisp.elementAt(i));
        vDisp.remove(i);
        vCodEmp.add(vCodDisp.elementAt(i));
        vCodDisp.remove(i);
  	  }
  	}
    lsDisp.setListData(vDisp);
    lsEmp.setListData(vEmp);
    lcCampos.edit();
  }
  private void removeEmp() {
  	if (lsEmp.isSelectionEmpty()) 
  	  return;
  	for (int i=lsEmp.getMaxSelectionIndex();i>=0;i--) {
  	  if (lsEmp.isSelectedIndex(i)) {
        vDisp.add(vEmp.elementAt(i));
        vEmp.remove(i);
        vCodDisp.add(vCodEmp.elementAt(i));
        vCodEmp.remove(i);
  	  }
  	}
    lsDisp.setListData(vDisp);
    lsEmp.setListData(vEmp);
    lcCampos.edit();
  }
  private void carregaDisp() {
  	try {
      PreparedStatement ps;
      ps = con.prepareStatement("SELECT CODFILIAL,NOMEFILIAL FROM SGFILIAL WHERE CODEMP=?");
  	  ps.setInt(1,Aplicativo.iCodEmp);
  	  ResultSet rs = ps.executeQuery();
  	  vDisp.clear();
  	  vCodDisp.clear();
      while (rs.next()) {
        vCodDisp.addElement(rs.getString("CodFilial"));
        vDisp.addElement(rs.getString("NomeFilial") != null ? rs.getString("NomeFilial").trim(): "");
  	  }
  	  rs.close();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
  	  lsDisp.setListData(vDisp);
	}
  	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Não foi carregar as filiais diponíveis.\n"+err);
	}  		
  }
  private void carregaAcesso() {
  	int iPos = 0;
  	try {
      String sSQL = "SELECT FL.CODFILIAL,FL.NOMEFILIAL FROM SGFILIAL FL, SGACESSOEU AC WHERE "+
                    "AC.IDUSU = ? AND FL.CODEMP = AC.CODEMPFL AND FL.CODFILIAL = AC.CODFILIALFL";
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setString(1,txtCodUsu.getVlrString());
      ResultSet rs = ps.executeQuery();
      vEmp.clear();
      vCodEmp.clear();
      while (rs.next()) {
        vCodEmp.addElement(rs.getString("CodFilial"));
        vEmp.addElement(rs.getString("NomeFilial") != null ? rs.getString("NomeFilial").trim(): "");
        
        iPos = vCodDisp.indexOf(rs.getString("CodFilial"));
        
        vCodDisp.remove(iPos);
        vDisp.remove(iPos);
  	  }
  	  rs.close();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
  	  lsEmp.setListData(vEmp);
  	  lsDisp.setListData(vDisp);
	}
  	catch(SQLException err) {
		Funcoes.mensagemInforma(this,"Não foi carregar as filiais que o usuário tem acesso.\n"+err);
	}  		
  }
  private void gravaAcesso() {
  	String sSep = "";
  	String sSqlI = "";
  	String sSqlD = "";
    for (int i=0; i<vCodEmp.size();i++) {
      sSqlI += sSep + vCodEmp.elementAt(i);
      sSep = ",";
    }
    
    try {
      sSqlD = "DELETE FROM SGACESSOEU WHERE IDUSU=? AND CODEMP=?";
      PreparedStatement ps = con.prepareStatement(sSqlD);
      ps.setString(1,txtCodUsu.getVlrString());
      ps.setInt(2,Aplicativo.iCodEmp);
      ps.executeUpdate();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      if (vCodEmp.size() > 0) {
        sSqlI = "INSERT INTO SGACESSOEU (CODEMP,CODFILIAL,IDUSU,CODEMPFL,CODFILIALFL) "+	
                "SELECT CODEMP,"+Aplicativo.iCodFilial+",'"+txtCodUsu.getVlrString()+"',CODEMP,CODFILIAL FROM SGFILIAL "+
                "WHERE CODEMP=? AND CODFILIAL IN ("+sSqlI+")";
        ps = con.prepareStatement(sSqlI);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.executeUpdate();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
    }
    catch (SQLException err) {
		Funcoes.mensagemInforma(this,"Erro ao cadastrar o acesso!\n"+err.getMessage());
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
  public void beforePost(PostEvent pevt) {
  	if (!txpSenha.getVlrString().equals(txpConfirma.getVlrString())) {
		Funcoes.mensagemInforma(this,"Senha diferente da confirmação!");
  		txpSenha.requestFocus();
        pevt.cancela();
  	}
  	else if (txpSenha.getVlrString().trim().equals("")) {
		Funcoes.mensagemInforma(this,"Senha em branco!");
  		txpSenha.requestFocus();
        pevt.cancela();
  	}
  	else if (txpSenha.getVlrString().length() > 8) {
		Funcoes.mensagemInforma(this,"A senha não pode ultrapassar 8 caracteres!");
  		txpSenha.requestFocus();
        pevt.cancela();
  	}
  	else {
  		try {
  			boolean bCheck = false;
			PreparedStatement ps;
		  	if ((lcCampos.getStatus() == ListaCampos.LCS_INSERT) || (lcCampos.getStatus() == ListaCampos.LCS_EDIT)) {
				ps = conIB.prepareStatement("SELECT SRET FROM CHECKUSER(?)");
				ps.setString(1,txtCodUsu.getVlrString());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					if (rs.getString(1).trim().equals("S")) {
						if (lcCampos.getStatus() == ListaCampos.LCS_INSERT)
						  Funcoes.mensagemInforma(this,"Atenção!!\n"+
														   "O usuário não será inserido no banco de dados ISC4, \n"+
														   "pois este já esta cadastrado.");
					    bCheck = true;
					}
				}
				rs.close();
				ps.close();
				if (bCheck) {
				  if (!txpSenha.getVlrString().equals("88888888") && !txtCodUsu.getVlrString().toUpperCase().equals("SYSDBA"))
				    ps = conIB.prepareStatement("EXECUTE PROCEDURE CHANGEPASSWORD(?,?)");
				  else
				    return;
				}
  				else
				  ps = conIB.prepareStatement("EXECUTE PROCEDURE ADDUSER(?,?)");
		  	}
  			else
            	return;
	  		ps.setString(1,txtCodUsu.getVlrString());
  			ps.setString(2,txpSenha.getVlrString());
  			ps.execute();
	  		ps.close();
	  		if (!con.getAutoCommit())
	  			con.commit();
	  	}
  		catch(SQLException err) {
			Funcoes.mensagemInforma(this,"Não foi possível criar usuário no banco de dados.\n"+err);
    	    pevt.cancela();
	  	}  		
  	}
  }
  public void beforeDelete(DeleteEvent devt) {
  	if(txtCodUsu.getVlrString().toUpperCase().equals("SYSDBA"))
  	  return;
  	try {
  		PreparedStatement ps = conIB.prepareStatement("execute procedure deleteuser(?)");
		ps.setString(1,txtCodUsu.getVlrString());
		ps.execute();
		ps.close();
		if (!con.getAutoCommit())
			con.commit();
	}
  	catch(SQLException err) {
		Funcoes.mensagemInforma(this,"Não foi possível excluir usuário no banco de dados.\n"+err);
    	devt.cancela();
	}  		
  }
  public void afterCarrega(CarregaEvent pevt) { 
  	carregaDisp();
  	carregaAcesso();
  	txpSenha.setVlrString("88888888");
  	txpConfirma.setVlrString("88888888");
  }
  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btAdicEmp)
  		adicionaEmp();
  	else if (evt.getSource() == btDelEmp)
  		removeEmp();
  	super.actionPerformed(evt);

  }
  public void beforeCarrega(CarregaEvent pevt) {
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void afterPost(PostEvent pevt) {
    gravaAcesso();
  }
  public void afterInsert(InsertEvent ievt) {
	carregaDisp();
	carregaAcesso();
  }
  public void afterDelete(DeleteEvent devt) { }
  public void setConexao(Connection cn) {
  	conIB = cn;
  }
  public void execShow(Connection cn) {
  	con = cn;
    lcGrup.setConexao(cn);
	lcCC.setConexao(cn);
    lcCC.setWhereAdic("NIVELCC=10 AND ANOCC="+buscaAnoBaseCC());
	if (conIB == null) {
		Funcoes.mensagemInforma(this,"A conexão com o banco de dados de usuário está nula,\n"+
										  "provavelmente o usuário qual você conectou não possui\n"+
										  "permissão para acessar este banco de dados.\n"+
										  "Por favor, contate o administrador do sistema.");
		dispose();
	}
	super.execShow(cn);
  }
}
