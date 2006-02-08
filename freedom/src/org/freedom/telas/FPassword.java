/**
 * @version 06/02/2006 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FPassword.java <BR>
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
 * FFDialog para validação de senha.
 */
package org.freedom.telas;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class FPassword extends FFDialogo{

	public static final long serialVersionUID = 1L;
	public static final int BAIXO_CUSTO = 0;
	private JTextFieldPad txtUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JPasswordFieldPad txtPass = new JPasswordFieldPad(8);
	private String sTitulo = null;
	private String[] param = null;
	private int tipo = 0;
	private int[] log = null;
	
	public FPassword(Component arg0,int arg1, String[] arg2, Connection arg3) {
		super(arg0);			
		setParam(arg1,arg2,arg3);
		montaTela();
	}
	
	private void montaTela() {
		setTitulo(sTitulo);
		setAtribos(300, 140);
		adic(new JLabelPad("Usuário: "), 7, 10, 100, 20);
		adic(new JLabelPad("Senha: "), 7, 30, 100, 20);
		adic(txtUsu, 110, 10, 150, 20);
		adic(txtPass, 110, 30, 150, 20);
		adic(new JLabelPad("Senha: "), 7, 30, 100, 20);		
		
		txtUsu.setVlrString(Aplicativo.strUsuario);
		setFirstFocus(txtPass);
	}
	
	public void ok() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Properties props = null;
		String sIDUsu = null;
		String sSQL = null;
		boolean ret = true;
		try {
			do {
				try {
					props = new Properties();
					sIDUsu = txtUsu.getVlrString().toLowerCase().trim();
					props.put("user", sIDUsu);
					props.put("password", txtPass.getVlrString());
					if (sIDUsu.equals("") || txtPass.getVlrString().trim().equals("")) {
						Funcoes.mensagemErro(this, "Campo em branco!");
						return;
					}
					DriverManager.getConnection(Aplicativo.strBanco, props).close();
					
					if(tipo == BAIXO_CUSTO) {
						sSQL = "SELECT BAIXOCUSTOUSU FROM SGUSUARIO "
							+ "WHERE IDUSU = ? AND CODEMP=? AND CODFILIAL=?";
						ps = con.prepareStatement(sSQL);
						ps.setString(1, sIDUsu);
						ps.setInt(2, Aplicativo.iCodEmp);
						ps.setInt(3, Aplicativo.iCodFilial);
						rs = ps.executeQuery();
						if (rs.next()) {
							if ((rs.getString(1) != null ? rs.getString(1) : "").equals("S")) {
								log = Aplicativo.gravaLog(sIDUsu, "PR", "LIB",
										"Liberação de "  + param[0] + " abaixo do custo",
										""+param[0]+" [" + param[1] + "], " + 		//codigo da tabela
										"Item: ["    	 + param[2] + "], " + 		//codigo do item
										"Produto: [" 	 + param[3] + "], " +		//codigo do produto
										"Preço: ["   	 + param[4] + "]"			//preço do produto
										, con);
							} else {
								Funcoes.mensagemErro(this,"Ação não permitida para este usuário.");
								ret = false;
							}
						} else {
							Funcoes.mensagemErro(this,"Ação não permitida para este usuário.");
							ret = false;
						}
					}
					
				} catch(SQLException sqle){
					if (sqle.getErrorCode() == 335544472) {
						Funcoes.mensagemErro(this,"Nome do usuário ou senha inválidos ! ! !");
						continue;
					}
					Funcoes.mensagemErro(this,"Erro ao verificar senha.",true,con,sqle);
					sqle.printStackTrace();
					ret = false;
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					ps = null;
					rs = null;
					props = null;
					sIDUsu = null;
					sSQL = null;
				}
				break;
			} while(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

        OK = ret;
        setVisible(false);
	}
	
	public void execShow(){
		setVisible(true);
		firstFocus();
	}
	
	public void setTitulo(String arg0) {
		if(arg0 != null && arg0.trim().length() > 0) 
			sTitulo = arg0;
		else 
			sTitulo = "Permição";
	}
	
	private void setParam(int arg0, String[] arg1, Connection arg2) {
		tipo = arg0;
		param = arg1;
		setConexao(arg2);
	}
	
	public String[] getLog() {
		return new String[] {Aplicativo.iCodEmp+"",log[0]+"",log[1]+""};
	}

}
