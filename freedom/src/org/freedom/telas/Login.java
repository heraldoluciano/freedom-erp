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
import java.sql.Types;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public abstract class Login extends FDialogo implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;	
	protected JTextFieldPad txtUsuario = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	protected JPasswordFieldPad txpSenha = new JPasswordFieldPad(9);
	protected Vector vVals = new Vector();
	protected Vector vLabs = new Vector();
	protected JComboBoxPad cbEmp = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_INTEGER, 8 , 0);
	protected String strBanco = "";
	protected String strDriver = "";
	protected String sUsuAnt = "";
	protected int iFilialMz = 0;
	protected Connection conLogin = null;
	protected JLabelPad lbInstrucoes = new JLabelPad("");
	protected Properties props = new Properties();
	public boolean bAdmin = false;
	protected int iFilialPadrao = 0;
	protected int iCodEst = 0;
	protected int tries = 0;
	
	public abstract void inicializaLogin();
	
	public void execLogin(String sBanco, String sDriver, String sImg, int iCodEst){
		strBanco = sBanco;
		strDriver = sDriver;
		this.iCodEst = iCodEst;

		ImageIcon ic = Icone.novo(sImg); 
		JLabelPad lbImg = new JLabelPad(Icone.novo(sImg));
		int iWidth = ic.getIconWidth();
		int iHeight = ic.getIconHeight();
		setAtribos(iWidth+12,iHeight+170);
		lbImg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		adic(lbImg,0,0,iWidth,iHeight);
		
		adic(new JLabelPad ("Usuario: "),7,iHeight,180,20);
		adic(txtUsuario,7,iHeight+20,180,20);
		adic(new JLabelPad ("Senha: "),190,iHeight,100,20);
		adic(txpSenha,190,iHeight+20,100,20);
		adic(new JLabelPad("Filial:"),7,iHeight+40,200,20);
		adic(cbEmp,7,iHeight+60,283,20);
		adic(lbInstrucoes,7,iHeight+83,300,20);
		setVisible(true);
		
	}
	
	public Login () {
		
		String sUsuarioTst = Aplicativo.getParameter("usuariotst");
		String sSenhaTst = Aplicativo.getParameter("senhatst");
		 	
		setTitulo("Login");
		
		lbInstrucoes.setForeground(Color.BLUE );
				
		txtUsuario.addFocusListener(this);
		txpSenha.addFocusListener(this);
		cbEmp.addFocusListener(this);
		btOK.addFocusListener(this);
		
		if((sUsuarioTst!=null) && (sSenhaTst!=null)){
			if((sUsuarioTst.length()>0) && (sSenhaTst.length()>0)){
				txtUsuario.setVlrString(sUsuarioTst);
				txpSenha.setVlrString(sSenhaTst);
				btOK.doClick();			
			}	
		}					
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
	
	public String getRazFilial() {
		return (cbEmp.getItemAt(cbEmp.getSelectedIndex()).toString());
	} 
	 
	public int getFilialMz() {
		return iFilialMz;
	}
	  
	public int getFilialPad() {
		return iFilialPadrao;
	}
	
	public abstract Connection getConection(); 
	
	protected abstract boolean execConexao(String sUsu, String sConexao);
	
	protected abstract boolean montaCombo(String sUsu);
	  
	protected abstract boolean adicConFilial();
	  
	public void focusLost(FocusEvent fevt) { }
	  
	public void focusGained(FocusEvent fevt) {
		
		if ( fevt.getSource()==txtUsuario)
			lbInstrucoes.setText("Digite sua identificação de usuário!");
		else if ( fevt.getSource()==txpSenha)
			lbInstrucoes.setText("Digite sua senha!");
		else if (fevt.getSource()==cbEmp) {
			if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) )
				btOK.requestFocus();
			else
				lbInstrucoes.setText("Selecione a filial!");
		}
		else if ( fevt.getSource()==btOK) {
			if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) ) {	
				lbInstrucoes.setText("Pressione espaço p/ conectar ao banco de dados!");
				if (tries == 0)  {
					btOK.doClick();
					tries++;
				}			   
			} else
				lbInstrucoes.setText("Pressione espaço p/ entrar no sistema!");	
		}
		
	}
		  
	public void actionPerformed( ActionEvent evt ) {
	  	
		String sUsu = txtUsuario.getText().trim().toLowerCase();
		          
		if ( evt.getSource() == btOK ) {			
			if ( sUsu.trim().equals("") ) {
				Funcoes.mensagemInforma( this, "Usuario em branco!" );
				txtUsuario.requestFocus();
				return;
			} else if ( txpSenha.getVlrString().trim().equals("") ) {
				Funcoes.mensagemInforma( this, "Senha em branco!" );
				txpSenha.requestFocus();
				return;
			}
			
			if (sUsu.equals("sysdba"))
				bAdmin = true;
			
			if ( !sUsuAnt.equals(sUsu) ) {
				if (!execConexao(sUsu, txpSenha.getVlrString().trim()))
					return;
				adicConFilial();      
				montaCombo(sUsu);
				cbEmp.requestFocus();
				if (cbEmp.getItemCount() == 1)
					btOK.doClick();
				return;
			} else if ((cbEmp.getVlrInteger().intValue()==0 ) && (!bAdmin)) {
				if ( sUsuAnt.equals(sUsu) ) 	
					Funcoes.mensagemInforma( this, "Filial não foi selecionada!" );
				cbEmp.requestFocus();
				return;
			}
			if (!adicConFilial())
				return;
		}
		super.actionPerformed(evt);
		
	}
}    
