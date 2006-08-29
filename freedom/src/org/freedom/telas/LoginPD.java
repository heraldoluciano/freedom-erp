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
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.funcoes.Funcoes;

public class LoginPD extends Login implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
		
	public LoginPD () {
		super();
	} 
		
	public void inicializaLogin() {

	
	} 
	
	public Connection getConection() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		Connection conRet = null;
		
		if (conLogin == null)
			return null;
		if (bAdmin)
			return conLogin;
		try {
			
			sSQL = "SELECT G.IDGRPUSU FROM SGGRPUSU G, SGUSUARIO U "+
				   "WHERE G.IDGRPUSU=U.IDGRPUSU AND G.CODEMP=U.CODEMPIG "+
				   "AND G.CODFILIAL=U.CODFILIALIG AND U.IDUSU=?";
			
			ps = conLogin.prepareStatement(sSQL);
			ps.setString(1,txtUsuario.getVlrString().trim().toLowerCase());
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("IDGRUP = "+rs.getString("IDGRPUSU")); 
				props.put("sql_role_name", rs.getString("IDGRPUSU"));
			}
			rs.close();
			ps.close();
			conLogin.close();
			conRet = DriverManager.getConnection(strBanco, props);
			
		} catch (java.sql.SQLException err) {
			Funcoes.mensagemErro( this,"Não foi possível ajustar o grupo de acesso do usuário.\n"+err.getMessage());
			err.printStackTrace();
			return null;
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return conRet;
	}		  

	protected boolean execConexao(String sUsu, String sSenha) {	
	    strBanco = Aplicativo.getParameter("banco");
	    strDriver = Aplicativo.getParameter("driver");
		try {
			Class.forName(strDriver);
		} catch (java.lang.ClassNotFoundException e) {
			Funcoes.mensagemErro( this,"Driver nao foi encontrado:\n"+strDriver+"\n"+e.getMessage ());
			return false;
		}
		
		try {
			props.put("user", sUsu);
			props.put("password", sSenha);
			conLogin = DriverManager.getConnection(strBanco, props);
			conLogin.setAutoCommit(false);
		} catch (java.sql.SQLException e) {
			if (e.getErrorCode() == 335544472)
				Funcoes.mensagemErro( this, "Nome do usuário ou senha inválidos ! ! !");
			else                                                                             
				Funcoes.mensagemErro( this,"Não foi possível estabelecer conexão com o banco de dados.\n"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		txtUsuario.setAtivo(false);
		txpSenha.setEditable(false);
		return true;
	}

	protected boolean montaCombo(String sUsu) {
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		    
		try {
			
			if (bAdmin)
				sSQL =  "SELECT CODFILIAL,NOMEFILIAL,1 FROM SGFILIAL FL WHERE CODEMP=?"; 
			else {
				sSQL = "SELECT FL.CODFILIAL,FL.NOMEFILIAL,AC.CODFILIAL FROM SGFILIAL FL, SGACESSOEU AC WHERE "+
				"FL.CODEMP = ? AND LOWER(AC.IDUSU) = '"+sUsu+"' AND FL.CODEMP = AC.CODEMPFL AND FL.CODFILIAL = AC.CODFILIALFL";
			}
			ps = conLogin.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			rs = ps.executeQuery();
			vVals.clear();
			vLabs.clear();
			while (rs.next()) {
				vVals.addElement(new Integer(rs.getInt(1)));
				vLabs.addElement(rs.getString("NOMEFILIAL") != null ? rs.getString("NOMEFILIAL") : "");
				if ( rs.getInt(1)==rs.getInt(3) ) 
					iFilialPadrao = rs.getInt(1);  
			}
			  
			cbEmp.setItens(vLabs,vVals);
			cbEmp.setVlrInteger(new Integer(iFilialPadrao));
			  
			sUsuAnt = sUsu;
			  
			// Buscar código da filial matriz
			  
			sSQL = "SELECT FL.CODFILIAL FROM SGFILIAL FL "+
				   "WHERE FL.CODEMP=? AND FL.MZFILIAL=?";
			ps = conLogin.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setString(2,"S");
			rs = ps.executeQuery();
			if (rs.next() )
				iFilialMz = rs.getInt("CODFILIAL");
			rs.close();
			ps.close();
			if (!conLogin.getAutoCommit())
				conLogin.commit();
		
		} catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar dados da empresa\n"+err);
			err.printStackTrace();
		} finally {
			sSQL = null;
			rs = null;
			ps = null;
		}
		return true;
		
	}

	protected boolean adicConFilial() {		
 		boolean bRet = false;
		String sSQL = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			sSQL = "SELECT SRET FROM SGINICONSP(?,?,?,?)";  		
			ps = conLogin.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setString(2,txtUsuario.getVlrString().trim().toLowerCase());
			if (iFilialPadrao==0)
				ps.setNull(3,Types.INTEGER);
			else
				ps.setInt(3,iFilialPadrao);
			ps.setInt(4,iCodEst);
			rs = ps.executeQuery();
			if (rs.next()) 
				bRet = rs.getInt(1)==1; // grava true se tiver efetuado a conexao
			rs.close();
			ps.close();
			if (!conLogin.getAutoCommit())
				conLogin.commit();
		} catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao gravar filial atual no banco!\n"+err.getMessage());
			err.printStackTrace();
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return bRet;
	}

}    



