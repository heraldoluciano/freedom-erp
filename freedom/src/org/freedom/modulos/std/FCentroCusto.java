/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCentroCusto.java <BR>
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FCentroCusto
	extends FFilho
	implements ActionListener, MouseListener, KeyListener {
	private Connection con = null;
	private Tabela tab = new Tabela();
	private JScrollPane spnTab = new JScrollPane(tab);
	private FlowLayout flCliRod = new FlowLayout(FlowLayout.CENTER, 0, 0);
	private JPanel pnCli = new JPanel(new GridLayout(1, 1));
	private JPanel pnRodape = new JPanel(new BorderLayout());
	private JPanel pnCliRod = new JPanel(flCliRod);
	private JPanel pnBotoes = new JPanel(new GridLayout(1, 4, 2, 0));
	private JPanel pnImp = new JPanel(new GridLayout(1, 2, 0, 0));
	private JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
	private JButton btPrim = new JButton("Nivel 1", Icone.novo("btNovo.gif"));
	private JButton btSint = new JButton("Sintética", Icone.novo("btNovo.gif"));
	private JButton btAnal = new JButton("Analítica", Icone.novo("btNovo.gif"));
	private JButton btImp = new JButton(Icone.novo("btImprime.gif"));
	private JButton btPrevimp = new JButton(Icone.novo("btPrevimp.gif"));
	int iAnoBase = 0;
	public FCentroCusto() {
		setTitulo("Centro de Custo");
		setAtribos(25, 25, 620, 380);

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

		pnBotoes.setPreferredSize(new Dimension(440, 29));
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
		tab.adicColuna("Sigla");
		tab.setTamColuna(140, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(300, 2);
		tab.setTamColuna(95, 3);

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
		con = cn;
		if (buscaAnoCC())
			montaTab();
		else {
			btAnal.setEnabled(false);
			btSint.setEnabled(false);
			btPrim.setEnabled(false);
			btImp.setEnabled(false);
			btPrevimp.setEnabled(false);
		}
	}
	private void montaTab() {
		String sSQL =
			"SELECT CODCC,CODREDCC,DESCCC,SIGLACC FROM FNCC WHERE CODEMP=? AND CODFILIAL =? AND ANOCC=? ORDER BY CODCC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		tab.limpa();
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			rs = ps.executeQuery();
			for (int i = 0; rs.next(); i++) {
				tab.adicLinha();
				tab.setValor(rs.getString("CodCC"), i, 0);
				tab.setValor(
					rs.getString("CodRedCC") != null
						? rs.getString("CodRedCC")
						: "",
					i,
					1);
				tab.setValor(rs.getString("DescCC").trim(), i, 2);
				tab.setValor(rs.getString("SiglaCC").trim(), i, 3);
			}
			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consultar a tabela FNCC! ! !\n" + err.getMessage());
			return;
		}
	}
	private void gravaNovoPrim() {
		String sSQLQuery =
			"SELECT MAX(CODCC) FROM FNCC WHERE NIVELCC=1 AND CODEMP=? AND CODFILIAL=? AND ANOCC=?";
		String sCodPrim = "";
		String sDescPrim = "";
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		try {
			psQuery = con.prepareStatement(sSQLQuery);
			psQuery.setInt(1, Aplicativo.iCodEmp);
			psQuery.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			psQuery.setInt(3, iAnoBase);
			rs = psQuery.executeQuery();
			if (!rs.next()) {
				Funcoes.mensagemInforma(this,
					"Não foi possível consultar a tabela PLAJAMENTO");
				return;
			}
			sCodPrim =
			  rs.getString(1) != null
			  ? "" + (Integer.parseInt(rs.getString(1).trim()) + 1)
			      : "1";
			//      rs.close();
			//      psQuery.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
				"Erro ao consultar a tabela PALNEJAMENTO! ! !\n"
					+ err.getMessage());
			return;
		}
		DLCCPrim dl = new DLCCPrim(this,sCodPrim, null, null);
		dl.setVisible(true);
		if (!dl.OK)
			return;
		String[] sRet = dl.getValores(); 
		sDescPrim = sRet[0];
		String sSigla = sRet[1];
		dl.dispose();
		String sSQL =
			"INSERT INTO FNCC (CODEMP,CODFILIAL,ANOCC,CODCC,DESCCC,NIVELCC,SIGLACC) "
				+ "VALUES(?,?,?,?,?,1,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			ps.setString(4, sCodPrim);
			ps.setString(5, sDescPrim);
			ps.setString(6, sSigla);  //60211090
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(this,
					"Não foi possível inserir registro na tabela FNCC! ! !");
			}
			//		ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
				"Erro ao inserir registro na tabela FNCC! ! !\n"
					+ err.getMessage());
			return;
		}
	}
	private void gravaNovoSint() {
		if (tab.getLinhaSel() < 0) {
			Funcoes.mensagemInforma(
				this,
				"Seleciona a origem na tabela! ! !");
			return;
		}
		String sCodPai = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		if (sCodPai.length() == 19) {
			Funcoes.mensagemInforma(this,
				"Não é possível criar uma Conta Sintética de uma Conta Analítica! ! !");
			return;
		} else if (sCodPai.length() == 17) {
			Funcoes.mensagemInforma(
				this,
				"Não é possível criar mais de oito niveis de Contas Sintéticas! ! !");
			return;
		}
		String sDescPai = ("" + tab.getValor(tab.getLinhaSel(), 2)).trim();
		String sDescFilho = "";
		String sCodFilho = "";
		int iCodFilho = 0;
		int iNivelPai = 0;
		int iNivelFilho = 0;
		String sMax = "";
		String sSQLQuery =
			"SELECT G.NIVELCC,(SELECT MAX(M.CODCC) FROM FNCC M "
				+ "WHERE M.CODSUBCC=G.CODCC AND M.CODEMP=G.CODEMP AND M.CODFILIAL=G.CODFILIAL AND M.ANOCC=G.ANOCC)"
				+ "FROM FNCC G WHERE G.CODCC='"
				+ sCodPai
				+ "' AND G.CODEMP=? AND G.CODFILIAL=? AND G.ANOCC=?";
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		try {
			psQuery = con.prepareStatement(sSQLQuery);
			psQuery.setInt(1, Aplicativo.iCodEmp);
			psQuery.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			psQuery.setInt(3, iAnoBase);
			rs = psQuery.executeQuery();
			if (!rs.next()) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível consultar a tabela FNCC");
				return;
			} 
			iNivelPai = rs.getInt(1);
			sMax =
			  rs.getString(2) != null
			  ? rs.getString(2).trim()
			      : sCodPai + "00";
		    if (sMax.length() == 19) {
			    Funcoes.mensagemInforma(
			        this,
			        "Não é possível criar uma conta sintética desta conta sintética,\n"
			        + "pois esta conta sintética possui contas analíticas.");
			    return;
			}
			iCodFilho =
			  Integer.parseInt(
			      sMax.substring(sMax.length() - 2));
			sCodFilho = sCodPai + Funcoes.strZero("" + (iCodFilho + 1), 2);
			iNivelFilho = iNivelPai + 1;
			//      rs.close();
			//      psQuery.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consulta a tabela FNCC! ! !\n" + err.getMessage());
			return;
		}
		DLCCSin dl = new DLCCSin(this,sCodPai, sDescPai, sCodFilho, null, null);
		dl.setVisible(true);
		if (!dl.OK)
			return;
		String[] sRets = dl.getValores(); 
		sDescFilho = sRets[0]; 
		String sSigla = sRets[1]; 
		dl.dispose();
		String sSQL =
			"INSERT INTO FNCC (CODEMP,CODFILIAL,ANOCC,CODCC,DESCCC,NIVELCC,CODSUBCC,SIGLACC) "
				+ "VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			ps.setString(4, sCodFilho);
			ps.setString(5, sDescFilho);
			ps.setInt(6, iNivelFilho);
			ps.setString(7, sCodPai);
			ps.setString(8, sSigla);
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível inserir registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao inserir registro na tabela FNCC! ! !\n"
					+ err.getMessage());
			return;
		}
	}
	/**
	 * 
	 */
	private void gravaNovoAnal() {
		if (tab.getLinhaSel() < 0) {
			Funcoes.mensagemInforma(
				this,
				"Selecione a conta sintética de origem! ! !");
			return;
		}
		String sCodPai = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		String sDescPai = ("" + tab.getValor(tab.getLinhaSel(), 2)).trim();
		if (sCodPai.length() == 19) {
			Funcoes.mensagemInforma(
				this,
				"Não é possível criar uma conta analítica de outra conta analítica! ! !");
			return;
		} else if (sCodPai.length() == 1) {
			Funcoes.mensagemInforma(
				this,
				"Não é possível criar uma conta analítica apartir do 1º Nivel! ! !");
			return;
		}
		String sSQLQuery =
			"SELECT MAX(C.CODCC) "
				+ "FROM FNCC C "
				+ "WHERE C.CODSUBCC = '"
				+ sCodPai
				+ "' AND C.CODEMP=? AND C.CODFILIAL=? AND C.ANOCC=?";
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		int iCodFilho = 0;
		String sCodFilho = "";
		String sMax = "";
		try {
			psQuery = con.prepareStatement(sSQLQuery);
			psQuery.setInt(1, Aplicativo.iCodEmp);
			psQuery.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			psQuery.setInt(3, iAnoBase);
			rs = psQuery.executeQuery();
			rs.next();
			sMax = rs.getString(1) != null ? rs.getString(1).trim() : "";
			if ((sMax.trim().length() > sCodPai.trim().length())
				&& (sMax.length() < 19)) {
				Funcoes.mensagemInforma(
					this,
					"Não é possível criar uma conta analítica desta conta sintética,\n"
						+ "pois esta conta sintética têm sub-divisões.");
				return;
			}
			if (sMax.length() == 0) {
				sCodFilho =
					sCodPai + Funcoes.replicate("0", 18 - sCodPai.length()) + 1;
			} else {
				if (sMax.length() > 17)
					iCodFilho = Integer.parseInt(sMax.substring(17));
				else
					iCodFilho = 0;
				iCodFilho = iCodFilho + 1;
				sCodFilho =
					sCodPai
						+ Funcoes.strZero(
							"" + iCodFilho,
							(19 - (sCodPai.length())));
			}
			//      psQuery.close();
			//      rs.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consultar a tabela FNCC! ! !\n" + err.getMessage());
			return;
		}
		DLCCAnal dl = new DLCCAnal(this,sCodPai, sDescPai, sCodFilho, null, null);
		dl.setVisible(true);
		if (!dl.OK) {
			dl.dispose();
			return;
		}
		String[] sRets = dl.getValores();
		String sDescFilho = sRets[0];
		String sSiglaFilho = sRets[1];
		dl.dispose();
		String sSQL =
			"INSERT INTO FNCC (CODEMP,CODFILIAL,ANOCC,CODCC,DESCCC,NIVELCC,CODSUBCC,SIGLACC) "
				+ "VALUES (?,?,?,'"
				+ sCodFilho
				+ "','"
				+ sDescFilho
				+ "',10,'"
				+ sCodPai
				+ "',?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			ps.setString(4, sSiglaFilho);
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível inserir registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao inserir registro na tabela FNCC! ! !\n"
					+ err.getMessage());
			return;
		}
	}
	private void editaPrim() {
		String sCodFilho = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		String sDescFilho = ("" + tab.getValor(tab.getLinhaSel(), 2)).trim();
		String sSiglaFilho = ("" + tab.getValor(tab.getLinhaSel(), 3)).trim();
		DLCCPrim dl = new DLCCPrim(this,sCodFilho, sDescFilho, sSiglaFilho);
		dl.setVisible(true);
		if (!dl.OK)
			return;
		String[] sRets = dl.getValores(); 
		sDescFilho = sRets[0]; 
		String sSigla = sRets[1]; 
		dl.dispose();
		String sSQL =
			"UPDATE FNCC SET DESCCC=?,SIGLACC=? WHERE CODCC=? AND ANOCC=? AND CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, sDescFilho);
			ps.setString(2, sSigla);
			ps.setString(3, sCodFilho);
			ps.setInt(4, iAnoBase);
			ps.setInt(5, Aplicativo.iCodEmp);
			ps.setInt(6, ListaCampos.getMasterFilial("FNCC"));
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível editar um registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao editar um registro na tabela FNCC! ! !\n"
					+ err.getMessage());
			return;
		}
	}
	private void editaSin() {
		String sCodFilho = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		String sCodPai = sCodFilho.substring(0, sCodFilho.length() - 2);
		String sDescFilho = ("" + tab.getValor(tab.getLinhaSel(), 2)).trim();
		String sSiglaFilho = ("" + tab.getValor(tab.getLinhaSel(), 3)).trim();
		String sSQLQuery =
			"SELECT DESCCC FROM FNCC WHERE CODCC='"
				+ sCodPai
				+ "' AND CODEMP=? AND CODFILIAL=? AND ANOCC=?";
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		String sDescPai = "";
		try {
			psQuery = con.prepareStatement(sSQLQuery);
			psQuery.setInt(1, Aplicativo.iCodEmp);
			psQuery.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			psQuery.setInt(3, iAnoBase);
			rs = psQuery.executeQuery();
			if (!rs.next()) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível consultar a tabela FNCC! ! !");
				return;
			}
			sDescPai = rs.getString("DescCC").trim();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consultar a tabela FNCC! ! !\n" + err.getMessage());
			return;
		}
		DLCCSin dl = new DLCCSin(this,sCodPai, sDescPai, sCodFilho, sDescFilho, sSiglaFilho);
		dl.setVisible(true);
		if (!dl.OK)
			return;
		String[] sRets = dl.getValores(); 
		sDescFilho = sRets[0];
		String sSigla = sRets[1]; 
		dl.dispose();
		String sSQL =
			"UPDATE FNCC SET DESCCC='"
				+ sDescFilho
				+ "',SIGLACC=? "
				+ "WHERE CODCC='"
				+ sCodFilho
				+ "' AND CODEMP=? AND CODFILIAL=? AND ANOCC=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, sSigla);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(4, iAnoBase);
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível editar um registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao editar um registro na tabela FNCC! ! !\n"
					+ err.getMessage());
		}
	}
	private void editaAnal() {
		String sCodFilho = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		String sDescFilho = ("" + tab.getValor(tab.getLinhaSel(), 2)).trim();
		String sSiglaFilho = ("" + tab.getValor(tab.getLinhaSel(), 3)).trim();
		String sSQLQuery =
			"SELECT P.DESCCC,F.CODSUBCC FROM FNCC P, FNCC F "
				+ "WHERE F.CODCC='"
				+ sCodFilho
				+ "' AND P.CODCC=F.CODSUBCC AND P.CODEMP=F.CODEMP"
				+ " AND P.CODFILIAL=F.CODFILIAL AND P.ANOCC=F.ANOCC AND F.CODEMP=? AND F.CODFILIAL=? AND F.ANOCC=? ";
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		String sDescPai = "";
		String sCodPai = "";
		try {
			psQuery = con.prepareStatement(sSQLQuery);
			psQuery.setInt(1, Aplicativo.iCodEmp);
			psQuery.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			psQuery.setInt(3, iAnoBase);
			rs = psQuery.executeQuery();
			if (!rs.next()) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível consultar a tabela FNCC! ! !");
				return;
			}
			sDescPai = rs.getString("DescCC").trim();
			sCodPai = rs.getString("CodSubCC").trim();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consultar a tabela FNCC! ! !\n" + err.getMessage());
			return;
		}
		DLCCAnal dl = new DLCCAnal(this,sCodPai, sDescPai, sCodFilho, sDescFilho, sSiglaFilho);
		dl.setVisible(true);
		if (!dl.OK)
			return;
		String[] sRets = dl.getValores();
		sDescFilho = sRets[0];
		sSiglaFilho = sRets[1];
		dl.dispose();
		String sSQL =
			"UPDATE FNCC SET DESCCC='"
				+ sDescFilho
				+ "', SIGLACC=?"
				+ "WHERE CODCC='"
				+ sCodFilho
				+ "' AND CODEMP=? AND CODFILIAL=? AND ANOCC=? ";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, sSiglaFilho);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(4, iAnoBase);
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível editar um registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao editar um registro na tabela FNCC! ! !\n"
					+ err.getMessage());
		}
	}
	public void deletar() {
		if (tab.getLinhaSel() < 0) {
			Funcoes.mensagemInforma(
				this,
				"Selecione a conta na tabela! ! !");
			return;
		}
		String sCod = ("" + tab.getValor(tab.getLinhaSel(), 0)).trim();
		String sSQL =
			"DELETE FROM FNCC WHERE CODCC='"
				+ sCod
				+ "' AND CODEMP=? AND CODFILIAL=? AND ANOCC=? ";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			if (ps.executeUpdate() == 0) {
				Funcoes.mensagemErro(
					this,
					"Não foi possível deletar um registro na tabela FNCC! ! !");
				return;
			}
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			if (err.getErrorCode() == 335544466)
				Funcoes.mensagemErro(
					this,
					"O registro possui vínculos, não pode ser deletado! ! !");
			else
				Funcoes.mensagemErro(
					this,
					"Erro ao deletar um registro na tabela FNCC! ! !\n"
						+ err.getMessage());
			return;
		}
	}
	private boolean buscaAnoCC() {
		boolean bRet = false;
		try {
			String sSQL =
				"SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("ANOCENTROCUSTO") == 0) {
					Funcoes.mensagemErro(
						this,
						"Não foi ajustado a data-base para os centros de custos.");
				} else {
					iAnoBase = rs.getInt("ANOCENTROCUSTO");
					bRet = true;
				}
				rs.close();
				ps.close();
			} else {
				Funcoes.mensagemInforma(
					this,
					"Não ha preferências cadastradas.");
			}
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro ao consultar database." + err.getMessage());
		}
		return bRet;
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btSair) {
			dispose();
		} else if (evt.getSource() == btPrim) {
			gravaNovoPrim();
			montaTab();
		} else if (evt.getSource() == btSint) {
			gravaNovoSint();
			montaTab();
		} else if (evt.getSource() == btAnal) {
			gravaNovoAnal();
			montaTab();
		} else if (evt.getSource() == btPrevimp) {
			imprimir(true);
		} else if (evt.getSource() == btImp)
			imprimir(false);
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo("Relatório de Centros de Custo");
		DLRCentroCusto dl = new DLRCentroCusto(this);
		dl.setVisible(true);
		if (dl.OK == false) {
		  dl.dispose();
		  return;
		}
		String sSQL =
			"SELECT CODCC,CODREDCC,DESCCC "
				+ "FROM FNCC WHERE CODEMP=? AND CODFILIAL=? AND ANOCC=? ORDER BY "
				+ dl.getValor();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(3, iAnoBase);
			rs = ps.executeQuery();
			imp.limpaPags();
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.impCab(80);
					imp.say(imp.pRow() + 0, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 0, 2, "Código");
					imp.say(imp.pRow() + 0, 23, "Cód. Red.");
					imp.say(imp.pRow() + 0, 35, "Descrição");
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 80));
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.normal());
				imp.say(imp.pRow() + 0, 0, "");
				imp.say(imp.pRow() + 0, 2, rs.getString("CodCC"));
				if (rs.getString("CodRedCC") != null)
					imp.say(imp.pRow() + 0, 23, rs.getString("CodRedCC"));
				imp.say(
					imp.pRow() + 0,
					35,
					Funcoes.copy(rs.getString("DescCC"), 0, 40));
				if (imp.pRow() >= linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0, "" + imp.normal());
			imp.say(imp.pRow() + 0, 0, Funcoes.replicate("=", 80));
			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(
				this,
				"Erro consulta tabela de Almoxarifados!" + err.getMessage());
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}
	public void mouseClicked(MouseEvent mevt) {
		if ((mevt.getSource() == tab)
			& (mevt.getClickCount() == 2)
			& (tab.getLinhaSel() >= 0)) {
			if (("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
				== 1) {
				editaPrim();
				montaTab();
			} else if (
				(("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length() > 1)
					& (("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
						< 19)) {
				editaSin();
				montaTab();
			} else if (
				("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
					== 19) {
				editaAnal();
				montaTab();
			}
		}
	}
	public void keyPressed(KeyEvent kevt) {
		if ((kevt.getKeyCode() == KeyEvent.VK_DELETE)
			& (kevt.getSource() == tab)) {
			if (Funcoes.mensagemConfirma(
					this,
					"Deseja realmente deletar este registro?") == 0)
				deletar();
			montaTab();
		} else if (
			(kevt.getKeyCode() == KeyEvent.VK_ENTER)
				& (kevt.getSource() == tab)
				& (tab.getLinhaSel() >= 0)) {
			if (("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
				== 1) {
				editaPrim();
				montaTab();
			} else if (
				(("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length() > 1)
					& (("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
						< 19)) {
				editaSin();
				montaTab();
			} else if (
				("" + tab.getValor(tab.getLinhaSel(), 0)).trim().length()
					== 19) {
				editaAnal();
				montaTab();
			}
		}
	}
	public void keyTyped(KeyEvent kevt) {
	}
	public void keyReleased(KeyEvent kevt) {
	}
	public void mouseEntered(MouseEvent mevt) {
	}
	public void mouseExited(MouseEvent mevt) {
	}
	public void mousePressed(MouseEvent mevt) {
	}
	public void mouseReleased(MouseEvent mevt) {
	}
}
