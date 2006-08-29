/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)Aplicativo.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários da classe.....
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;

import org.freedom.bmps.Icone;
import org.freedom.bmps.Imagem;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ObjetoEmpresa;
import org.freedom.componentes.TabObjeto;
import org.freedom.funcoes.Funcoes;

public class AplicativoPD extends Aplicativo implements ActionListener, KeyListener {
	
	public AplicativoPD() {
		Locale.setDefault(new Locale("pt", "BR"));
	}
	
	public AplicativoPD(String sIcone, String sSplash, int iCodSis, String sDescSis, 
			int iCodModu, String sDescModu, String sDirImagem,final FPrincipal telaP, Class cLogin) {
	    if (sDirImagem!=null) {
	        Imagem.dirImages = sDirImagem;
	        Icone.dirImages = sDirImagem;
	    } 
		if (System.getProperty("ARQLOG") != null)
			ligaLog(System.getProperty("ARQLOG"));
		strSplash = sSplash; 
		Locale.setDefault(new Locale("pt", "BR"));
		vOpcoes = new Vector();
		vBotoes = new Vector();

		telaPrincipal = telaP;
		this.iCodSis = iCodSis;
		this.iCodModu = iCodModu;
		this.sDescSis = sDescSis;
		this.sDescModu = sDescModu;
		this.cLoginExec = cLogin;
		
		imgIcone = Icone.novo(sIcone);
		telaPrincipal.setIconImage(imgIcone.getImage());
		
		setSplashName(sSplash);
		iniConexao();
		carregaCasasDec();
		getMultiAlmox();
		buscaInfoUsuAtual();
		setaInfoTela();
	}
		
	public void setaSysdba(){
		if (strUsuario.toUpperCase().trim().equals("SYSDBA")) {
			iXPanel = 30;
			pinBotoes.adic(btAtualMenu, 0, 0, 30, 30);
		} else
			iXPanel = 0;
	}
		
	public void setaInfoTela() {
		telaPrincipal.setIdent(sDescSis.trim()+" - "+sDescModu.trim());
		telaPrincipal.setConexao(con); // Variavel de conexão da Classe

		telaPrincipal.statusBar.setUsuario(strUsuario);//Variavel de usuario da
		telaPrincipal.statusBar.setCodFilial(iCodFilial);
		telaPrincipal.statusBar.setRazFilial(sRazFilial);
		telaPrincipal.statusBar.setNumEst(iNumEst);
		telaPrincipal.statusBar.setDescEst(getDescEst());
		
		setaSysdba();
		
		telaPrincipal.adicCompInBar(pinBotoes, BorderLayout.WEST);
		btAtualMenu.addActionListener(this);
		bModoDemo = getModoDemo();		
		
	}
	
	public void iniConexao() {
		String sAutoCommit = getParameter("autocommit");
		try {
			strBanco = getParameter("banco");
			strDriver = getParameter("driver");
			if (sAutoCommit == null)
				sAutoCommit = "N";
			if (sAutoCommit.trim().equals(""))
				sAutoCommit = "N";
			if (sAutoCommit.toUpperCase().equals("S"))
				bAutoCommit = true;

			strTemp = getParameter("temp");
			strOS = getParameter("os").toLowerCase();
			strBrowser = getParameter("browser");
			strTefEnv = getParameter("tef_path_envio");
			strTefRet = getParameter("tef_path_retorno");
			strCharSetRel = getParameter("charSetRel");
			if ((strCharSetRel == null) || ("".equals(strCharSetRel))) {
				strCharSetRel = "ISO8859-1";
			}

			try {
				iCodEmp = Integer.parseInt(getParameter("codemp"));
			} catch (Exception err) {
				Funcoes.mensagemErro(null,
						"Não foi possível carregar o parâmetro 'codemp'\n"
								+ err.getMessage(),true,con,err);
			}
			
			try {
				if(!getParameter("codfilial").equals(""))
					iCodFilialParam = Integer.parseInt(getParameter("codfilial"));
			} catch (Exception err) {
				Funcoes.mensagemErro(null,
						"Não foi possível carregar o parâmetro 'codfilialparam'\n"
								+ err.getMessage(),true,con,err);
			}

			try {
				iNumEst = Integer.parseInt(getParameter("numterm"));
			} catch (Exception err) {
				Funcoes.mensagemErro(null,
						"Não foi possível carregar o parâmetro 'numterm'\n"
								+ err.getMessage(),true,con,err);
			}
			if (strBanco == null) {
				Funcoes.mensagemInforma(null,
						"Parametro banco nao foi preenchido");
				return;
			}
			if (strDriver == null) {
				Funcoes.mensagemInforma(null,
						"Parametro driver nao foi preenchido");
				return;
			}
			con = conexao();
			if (con == null)
				System.exit(1);
			try {
				con.setAutoCommit(bAutoCommit);
				//      	con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			} catch (SQLException err) {
				Funcoes.mensagemErro(null, err.getMessage());
			}
			tbObjetos = new TabObjeto();
			tbObjetos.montaLista(con, iCodEmp, "SGOBJETO", "TB");
		
			empresa = new ObjetoEmpresa(con);
		} finally {
			sAutoCommit = null;
		}
	}

	public static int[] gravaLog(String sClas, String sTipo, String sDesc,
			String sObs, Connection con) {
		return gravaLog(strUsuario, sClas, sTipo, sDesc, sObs, con);
	}
	
	public static int[] gravaLog(String sIDUSU, String sClas, String sTipo,
			String sDesc, String sObs, Connection con) {
		int iRet[] = new int[2];
		String sSQL = "SELECT CODFILIAL,CODLOG FROM SGLOGSP01(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodEmp);
			ps.setString(2, sIDUSU);
			ps.setString(3, sClas);
			ps.setString(4, sTipo);
			ps.setString(5, sDesc);
			ps.setString(6, sObs);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet[0] = rs.getInt("CodFilial");
				iRet[1] = rs.getInt("CodLog");
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "Erro ao gravar LOG!!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return iRet;
	}
	
	public boolean getModoDemo() {
		String sSQL = "SELECT MODODEMOEST FROM SGESTACAO WHERE CODEST="
				+ iNumEst + "AND CODEMP=" + iCodEmp + " AND CODFILIAL="
				+ ListaCampos.getMasterFilial("SGESTACAO");
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bModo = true;
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			if (!rs.next())
				Funcoes.mensagemErro(null,
						"Estação de trabalho não cadastrado!");
			else {
				if (rs.getString("ModoDemoEst").equals("S"))
					bModo = true;
				else
					bModo = false;
			}
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage(),true,con,err);
			return true;
		}
		return bModo;
	}

    protected void buscaInfoUsuAtual() {      	
    	String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU " +
				      "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " +
				      "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				strCodCCUsu = rs.getString("CODCC");
				strAnoCCUsu = rs.getString("ANOCC");
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			killProg(1, "Erro ao carregar informações da tabela de usuários!\n"	+ err.getMessage());
		}
    }

	public boolean verifAcesso(int iCodSisP, int iCodModuP, int iCodMenuP) {
		boolean bRet = false;
		if (strUsuario.toUpperCase().equals("SYSDBA"))
			return true;
		try {
			String sTmp = "";
			String sSQL = "SELECT TPACESSOMU FROM SGACESSOMU WHERE CODEMP = ? "
					+ "AND CODFILIAL = ? " + "AND IDUSU = ? "
					+ "AND CODSIS = ? " + "AND CODMODU = ? "
					+ "AND CODMENU = ?";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setString(3, strUsuario);
			ps.setInt(4, iCodSisP);
			ps.setInt(5, iCodModuP);
			ps.setInt(6, iCodMenuP);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sTmp = rs.getString("TPACESSOMU");
				if (sTmp == null)
					return bRet;
				if (sTmp.toCharArray()[0] > 'A')
					bRet = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			killProg(1, "Erro ao verificar acessos para arvore de menus!\n"+ err.getMessage());
		}
		return bRet;
	}

	public String getDescEst() {
		String sSQL = "SELECT DESCEST FROM SGESTACAO WHERE CODEST=" + iNumEst
				+ " AND CODEMP=" + iCodEmp + " AND CODFILIAL=" + ListaCampos.getMasterFilial("SGESTACAO");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sDesc = "";
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			if (!rs.next())
				sDesc = "ESTAÇÃO DE TRABALHO NÃO CADASTRADA";
			else
				sDesc = rs.getString("DescEst");
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage(),true,con,err);
			return "NÃO FOI POSSÍVEL REGISTRAR A ESTAÇÃO DE TRABALHO! ! !";
		}
		return sDesc;
	}

	public void getMultiAlmox() {
		String sSQL = "SELECT MULTIALMOXEMP FROM SGEMPRESA WHERE CODEMP=?" ;				
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,iCodEmp);
			rs = ps.executeQuery();
			
			if (!rs.next())
				sMultiAlmoxEmp = rs.getString(1)==null?"N":rs.getString(1);
			else
				
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, err.getMessage());			
		}		
	}	

	protected void carregaCasasDec() {
		String sSQL = null;
		String sBusca = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT CASASDEC,CASASDECFIN,BUSCAPRODSIMILAR,BUSCACODPRODGEN FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				casasDec = rs.getInt("CASASDEC");
				casasDecFin = rs.getInt("CASASDECFIN");
				sBusca = (rs.getString("BUSCAPRODSIMILAR") == null ? "N" : rs.getString("BUSCAPRODSIMILAR"));
				bBuscaProdSimilar = sBusca.equals("S") ? true : false;
				sBusca = (rs.getString("BUSCACODPRODGEN") == null ? "N" : rs.getString("BUSCACODPRODGEN"));
				bBuscaCodProdGen = sBusca.equals("S") ? true : false;
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null,
					"Não foi possível obter o número de casas decimais!\n"
							+ err.getMessage(),true,con,err);
		} finally {
			sSQL = null;
			sBusca = null;
			ps = null;
			rs = null;
		}
	}

	public static String carregaFiltro(Connection conF, int iCodEmpF) {
		String sSQL = "SELECT FILTRO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		//	  String sSQL = "INSERT INTO TESTE (TESTE1) VALUES ('2001-3-23')";
		PreparedStatement ps = null;
		ResultSet rs = null;
		sFiltro = "";
		try {
			ps = conF.prepareStatement(sSQL);
			ps.setInt(1, iCodEmpF);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (!rs.next()) {
				sFiltro = "";
				Funcoes.mensagemInforma(null,"Preferências não foram cadastradas!");
			} else
				sFiltro = rs.getString("FILTRO");
			//		rs.close();
			//		ps.close();
			if (!conF.getAutoCommit())
				conF.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "NÃO FOI POSSÍVEL CARREGAR OS FILTROS! "
					+ err.getMessage(),true,conF,err);
			//		return "NÃO FOI POSSÍVEL CARREGAR OS FILTROSL! ! !";
		}
		if (sFiltro == null)
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals(""))
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals("SN"))
			sFiltro = "('S','N')";
		else if (sFiltro.trim().equals("S"))
			sFiltro = "('S')";
		else if (sFiltro.trim().equals("N"))
			sFiltro = "('N')";
		return sFiltro;
	}

	public void setFiltro(char cFiltro) {
		sFiltro = "";
		switch (cFiltro) {
		case '1':
			sFiltro = "S";
			break;
		case '2':
			sFiltro = "N";
			break;
		case '3':
			sFiltro = "SN";
			break;
		}
		try {
			String sSQL = "UPDATE SGPREFERE1 SET FILTRO=? WHERE CODEMP=?";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setString(1, sFiltro);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.execute();
			if (!con.getAutoCommit())
				con.commit();
			Funcoes.mensagemInforma(null, "Filtros atualizados para: "+ sFiltro);
		} catch (SQLException err) {
			Funcoes.mensagemErro(null, "Erro ao atualizar filtro.\n"
					+ err.getMessage(),true,con,err);
		}
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = true;
		if (bCtrl) {
			if (kevt.getKeyCode() == KeyEvent.VK_F10)
				setFiltro('1');
			else if (kevt.getKeyCode() == KeyEvent.VK_F11)
				setFiltro('2');
			else if (kevt.getKeyCode() == KeyEvent.VK_F12)
				setFiltro('3');
		}
	}

}

