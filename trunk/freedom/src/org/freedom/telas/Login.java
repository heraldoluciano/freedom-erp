/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class Login extends FDialogo implements ActionListener, FocusListener {
  private JTextFieldPad txtUsuario = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JPasswordFieldPad txpSenha = new JPasswordFieldPad(9);
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JComboBoxPad cbEmp = new JComboBoxPad();
  private String strBanco = "";
  private String strDriver = "";
  private String sUsuAnt = "";
  private int iFilialMz = 0;
  private Connection conLogin = null;
  private JLabel lbInstrucoes = new JLabel("");
  private Properties props = new Properties();
  public boolean bAdmin = false;
  private int iFilialPadrao = 0;
  private int iTerminal = 0;
  private int tries = 0;

  public Login (String sBanco, String sDriver, String sImg, int iTerm) {
    
	strBanco = sBanco;
	strDriver = sDriver;
	
	iTerminal = iTerm;
     	
	setTitulo("Freedom - Login");
	setAtribos(312,320);
  	
	JLabel lbImg = new JLabel(Icone.novo(sImg));
	lbImg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
	lbInstrucoes.setForeground(Color.BLUE );
  	
	adic(lbImg,0,0,348,150); 	
	adic(new JLabel ("Usuario: "),7,150,180,20);
	adic(txtUsuario,7,170,180,20);
	adic(new JLabel ("Senha: "),190,150,100,20);
	adic(txpSenha,190,170,100,20);
	adic(new JLabel("Filial:"),7,190,200,20);
	adic(cbEmp,7,210,283,20);
	adic(lbInstrucoes,7,233,300,20);
    
    
	txtUsuario.addFocusListener(this);
	txpSenha.addFocusListener(this);
	cbEmp.addFocusListener(this);
	btOK.addFocusListener(this);

	setVisible(true);
    
  }
  public String[] getStrVals() {
  	 String[] ret = new String[3];
     ret[0] = txtUsuario.getText().trim().toLowerCase();
     ret[1] = txpSenha.getVlrString();
     return ret;
  }
  
  public int getFilial() {
  	if (cbEmp.getVlrInteger().intValue() == 0)
  	  return 1;
  	return cbEmp.getVlrInteger().intValue();
  }

  public int getFilialMz() {
  	 return iFilialMz;
  }
  
  public int getFilialPad() {
	 return iFilialPadrao;
  }
  
  public void focusLost(FocusEvent fevt) {
  	if ( fevt.getSource()==txpSenha && 
  			!sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase()) &&
			tries == 0)  {
  		btOK.doClick();
  		tries++;
  	}
  }
  
  public void focusGained(FocusEvent fevt) {
  	 if ( fevt.getSource()==txtUsuario) {
        lbInstrucoes.setText("Digite sua identificação de usuário!");
  	 }
  	 else if ( fevt.getSource()==txpSenha) {
        lbInstrucoes.setText("Digite sua senha!");
  	 }
  	 else if (fevt.getSource()==cbEmp) {
  	    if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) ) {
    	  btOK.requestFocus();
  	  	}
  	  	else {
  	      lbInstrucoes.setText("Selecione a filial!");  		
  	  	}
  	 }
  	 else if ( fevt.getSource()==btOK) {
  	    if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) ) {	
           lbInstrucoes.setText("Pressione espaço p/ conectar ao banco de dados!");
           
  	    }
  	    else {
           lbInstrucoes.setText("Pressione espaço p/ entrar no sistema!");  		
  	    }
  	 }
  }
   
  private boolean montaCombo(String sUsu, String sSenha) {
    try {
        Class.forName(strDriver);
    }
    catch (java.lang.ClassNotFoundException e) {
        Funcoes.mensagemErro( this,"Driver nao foi encontrado: "+e.getMessage ());
        return false;
    }

    try {
		props.put("user", sUsu);
		props.put("password", sSenha);
//		props.put("roleName", "testrole");
		conLogin = DriverManager.getConnection(strBanco, props);
        conLogin.setAutoCommit(false);
    }
    catch (java.sql.SQLException e) {
        if (e.getErrorCode() == 335544472)
           Funcoes.mensagemErro( this, "Nome do usuário ou senha inválidos ! ! !");
        else                                                                             
           Funcoes.mensagemErro( this,"Não foi possível estabelecer conexão com o banco de dados.\n"+e.getMessage());
        return false;
    }
    
    try {
      String sSQL = "";
      if (bAdmin) {
      	sSQL =  "SELECT CODFILIAL,NOMEFILIAL,1 FROM SGFILIAL FL WHERE CODEMP=?";
      }
      else {
		sSQL = "SELECT FL.CODFILIAL,FL.NOMEFILIAL,AC.CODFILIAL FROM SGFILIAL FL, SGACESSOEU AC WHERE "+
				   "FL.CODEMP = ? AND LOWER(AC.IDUSU) = '"+sUsu+"' AND FL.CODEMP = AC.CODEMPFL AND FL.CODFILIAL = AC.CODFILIALFL";
      }
      PreparedStatement ps = conLogin.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ResultSet rs = ps.executeQuery();
      vVals.clear();
      vLabs.clear();
      while (rs.next()) {
      	 vVals.addElement(new Integer(rs.getInt(1)));
      	 vLabs.addElement(rs.getString("NOMEFILIAL") != null ? rs.getString("NOMEFILIAL") : "");
      	 if ( rs.getInt(1)==rs.getInt(3) ) 
            iFilialPadrao = rs.getInt(1);  
      }
      if (!conLogin.getAutoCommit())
      	conLogin.commit();
      cbEmp.setItens(vLabs,vVals);
      cbEmp.setVlrInteger(new Integer(iFilialPadrao));
      
      sUsuAnt = sUsu;
      
      // Buscar código da filial matriz
      
      sSQL = "SELECT FL.CODFILIAL FROM SGFILIAL FL WHERE "+
                    "FL.CODEMP = ? AND FL.MZFILIAL=?";
      ps = conLogin.prepareStatement(sSQL);
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setString(2,"S");
      rs = ps.executeQuery();
      if (rs.next() ) {
      	 iFilialMz = rs.getInt("CODFILIAL");
      }
      rs.close();
      ps.close();
      if (!conLogin.getAutoCommit())
      	conLogin.commit();
      
      
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar dados da empresa\n"+err);
   	}
    return true;
  }

  public Connection getConection() {
  	Connection conRet = null;
  	
  	if (conLogin == null)
  	  return null;
  	if (bAdmin)
  	  return conLogin;
	try {
		PreparedStatement ps = conLogin.prepareStatement("SELECT G.IDGRPUSU FROM SGGRPUSU G, SGUSUARIO U "+
                                                         "WHERE G.IDGRPUSU=U.IDGRPUSU AND "+
                                                         "G.CODEMP = U.CODEMPIG AND G.CODFILIAL=U.CODFILIALIG AND "+
                                                         "U.IDUSU=?");
        ps.setString(1,txtUsuario.getVlrString().trim().toLowerCase());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        	System.out.println("IDGRUP = "+rs.getString("IDGRPUSU")); 
			props.put("sql_role_name", rs.getString("IDGRPUSU"));
        }
        rs.close();
        ps.close();
        conLogin.close();
		conRet = DriverManager.getConnection(strBanco, props);
	}
	catch (java.sql.SQLException e) {
      Funcoes.mensagemErro( this,"Não foi possível ajustar o grupo de acesso do usuário.\n"+e.getMessage());
	  return null;
	}
	return conRet;
  }
  
  private boolean adicConFilial() {
  	boolean bRet = false;
  	String sSQL = "EXECUTE PROCEDURE SGINICONSP(?,?,?,?,?)";
  	try {
  		PreparedStatement ps = conLogin.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,iFilialPadrao);
  		ps.setString(3,txtUsuario.getVlrString().trim().toLowerCase());
		ps.setInt(4,cbEmp.getVlrInteger().intValue());
		ps.setInt(5,iTerminal);
		ps.execute();
		ps.close();
		if (!conLogin.getAutoCommit())
			conLogin.commit();
		bRet = true; 
  	}
  	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao gravar filial atual no banco!\n"+err.getMessage());
  	}
  	return bRet;
  }
  
  public void actionPerformed( ActionEvent evt ) {
  	
  	 String sUsu = txtUsuario.getText().trim().toLowerCase();
          
     if ( evt.getSource() == btOK ) {
	  
        if ( sUsu.length()==0 ) {
           Funcoes.mensagemInforma( this, "Usuario em branco!" );
           txtUsuario.requestFocus();
           return;
        }
        else if ( txpSenha.getVlrString().trim().length()==0 ) {
           Funcoes.mensagemInforma( this, "Senha em branco!" );
           txpSenha.requestFocus();
           return;
        }
        
		if (sUsu.equals("sysdba"))
		  bAdmin = true;

        if ( !sUsuAnt.equals(sUsu) ) {
            montaCombo(sUsu , txpSenha.getVlrString().trim());
            cbEmp.requestFocus();
            if (cbEmp.getItemCount() == 2) {
            	btOK.doClick();
            }
            return;
        }
        else if ((cbEmp.getVlrInteger().intValue()==0 ) && (!bAdmin)) {
           if ( sUsuAnt.equals(sUsu) ) 	
              Funcoes.mensagemInforma( this, "Filial não foi selecionada!" );
           cbEmp.requestFocus();
           return;
        }
        if (!adicConFilial()) {
        	return;
        }
     }
     super.actionPerformed(evt);
  }

}    
