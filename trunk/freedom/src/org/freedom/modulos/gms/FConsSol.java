/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsOrc.java <BR>
 *                   Este programa é licenciado de acordo com a LPG-PC (Licença
 *                   Pública Geral para Programas de Computador), <BR>
 *                   versão 2.1.0 ou qualquer versão posterior. <BR>
 *                   A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 *                   REPRODUÇÕES deste Programa. <BR>
 *                   Caso uma cópia da LPG-PC não esteja disponível junto com
 *                   este Programa, você pode contatar <BR>
 *                   o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                   Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                   Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                   ALTERAR este Programa é preciso estar <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 *                   <BR>
 *                   Formulário de consulta de orçamento.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.telas.FPrincipal;

public class FConsSol extends FFilho implements ActionListener {

	private JPanelPad pinCab = new JPanelPad(0, 185);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JCheckBoxPad cbPendentes = new JCheckBoxPad("Solicitações pendentes", "S", "N");
	private JCheckBoxPad cbCompletas = new JCheckBoxPad("Solicitações completas", "S", "N");
	private JCheckBoxPad cbCanceladas = new JCheckBoxPad("Solicitações canceladas", "S", "N");
	private JCheckBoxPad cbTomadasDePreco = new JCheckBoxPad("Cotações de preço", "S", "N");
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING, 19, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK();
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private Tabela tab = new Tabela();
	private JButton btBusca = new JButton("Buscar", Icone.novo("btPesquisa.gif"));
	private JButton btPrevimp = new JButton("Imprimir", Icone.novo("btPrevimp.gif"));
			JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcUsuario = new ListaCampos(this, "");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private FPrincipal fPrim;

	public FConsSol() {
		setTitulo("Pesquisa Solicitações de Compra");
		setAtribos(10, 10, 513, 480);

		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);

		txtDescAlmoxarife.setSoLeitura(true);
		txtCodAlmoxarife.setTabelaExterna(lcAlmox);

		txtCodUsu.setNomeCampo("IdUsuItSol");

		lcUsuario.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, false));
		lcUsuario.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, false));
		lcUsuario.montaSql(false, "USUARIO", "SG");
		lcUsuario.setQueryCommit(false);
		lcUsuario.setReadOnly(true);

		txtCodUsu.setTabelaExterna(lcUsuario);

		lcCC.add(new GuardaCampo(txtCodCC, "codCC", "Cód.c.c.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descriçao", ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);

		txtCodCC.setTabelaExterna(lcCC);

		Container c = getTela();
		c.add(pnRod, BorderLayout.SOUTH);
		c.add(pnCli, BorderLayout.CENTER);
		pnCli.add(pinCab, BorderLayout.NORTH);
		pnCli.add(spnTab, BorderLayout.CENTER);

		btSair.setPreferredSize(new Dimension(100, 30));

		JPanelPad pnBordaSair = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER, 3, 3));
		pnBordaSair.add(btSair);
		pnRod.add(pnBordaSair, BorderLayout.EAST);
		JPanelPad pnBordaConsVenda = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER, 3, 3));
		pnRod.add(pnBordaConsVenda, BorderLayout.WEST);
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbStatus = new JLabelPad(" Filtrar:");
		lbStatus.setOpaque(true);

		pinCab.adic(new JLabelPad("Período:"), 7, 5, 50, 20);
		pinCab.adic(txtDtIni, 7, 25, 95, 20);
		pinCab.adic(new JLabelPad("Até"), 111, 25, 27, 20);
		pinCab.adic(txtDtFim, 139, 25, 95, 20);

		pinCab.adic(new JLabelPad("Cód.c.c."), 237, 5, 70, 20);
		pinCab.adic(txtCodCC, 237, 25, 70, 20);
		pinCab.adic(new JLabelPad("Centro de custo"), 310, 5, 410, 20);
		pinCab.adic(txtDescCC, 310, 25, 180, 20);

		pinCab.adic(new JLabelPad("Cód.usu."), 7, 48, 70, 20);
		pinCab.adic(txtCodUsu, 7, 70, 70, 20);
		pinCab.adic(new JLabelPad("Nome do usuário"), 80, 48, 410, 20);
		pinCab.adic(txtNomeUsu, 80, 70, 153, 20);

		pinCab.adic(new JLabelPad("Cód.almox."), 237, 48, 75, 20);
		pinCab.adic(txtCodAlmoxarife, 237, 70, 70, 20);
		pinCab.adic(new JLabelPad("Nome do almoxarifado"), 310, 48, 410, 20);
		pinCab.adic(txtDescAlmoxarife, 310, 70, 180, 20);

		pinCab.adic(lbStatus, 15, 100, 50, 18);
		pinCab.adic(lbLinha2, 7, 110, 373, 66);
		pinCab.adic(cbPendentes, 15, 122, 170, 20);
		pinCab.adic(cbCompletas, 15, 147, 170, 20);
		pinCab.adic(cbCanceladas, 195, 122, 180, 20);
		pinCab.adic(cbTomadasDePreco, 195, 147, 180, 20);

		pinCab.adic(btBusca, 382, 110, 110, 30);
		pinCab.adic(btPrevimp, 382, 145, 110, 30);

		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("Sit.");
		tab.adicColuna("Sol.");
		tab.adicColuna("Data");
		tab.adicColuna("Motivo");

		tab.setTamColuna(80, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(90, 2);
		tab.setTamColuna(250, 3);

		btBusca.addActionListener(this);
		btPrevimp.addActionListener(this);

		tab.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource() == tab && mevt.getClickCount() == 2)
					abreSol();
			}
		});
		btSair.addActionListener(this);
	}

	/**
	 * Carrega os valores para a tabela de consulta. Este método é executado após
	 * carregar o ListaCampos da tabela.
	 */
	private void carregaTabela() {
		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = (!txtCodUsu.getVlrString().trim().equals(""));
		boolean almoxarifado = (txtCodAlmoxarife.getVlrInteger().intValue() > 0);
		boolean CC = (!txtCodUsu.getVlrString().trim().equals(""));

		if (cbPendentes.getVlrString().equals("S")) {
			usaWhere = true;
			where = " SitSol ='PE'";
		}
		if (cbCompletas.getVlrString().equals("S")) {
			if (where.trim().equals("")) {
				where = " SitSol ='SC'";
			} else {
				where = where + " OR SitSol ='SC'";
				usaOr = true;
			}
			usaWhere = true;
		}
		if (cbCanceladas.getVlrString().equals("S")) {
			if (where.trim().equals("")) {
				where = " SitSol ='CA'";
			} else {
				where = where + " OR SitSol ='CA'";
				usaOr = true;
			}
			usaWhere = true;
		}
		if (cbTomadasDePreco.getVlrString().equals("S")) {
			if (where.trim().equals(""))
				where = " SitSol ='TP'";
			else {
				where = where + " OR SitSol ='TP'";
				usaOr = true;
			}
			usaWhere = true;
		}

		if (usaWhere && usaOr)
			where = " AND (" + where + ")";
		else if (usaWhere)
			where = " AND " + where;
		else
			where = " AND SitSol='PE'";

		if (almoxarifado)
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if (CC)
			where += " AND IT.CODCC=? AND IT.ANOCC=? AND IT.CODEMPCC=? AND IT.CODFILIALCC=? ";

		if (usuario)
			where += " AND (IT.IDUSUITSOL=? OR IT.IDUSUAPROVITSOL=? OR IT.IDUSUCANCITSOL=?) ";

		String sSQL = "SELECT O.SITSOL, O.CODSOL,O.DTEMITSOL, O.MOTIVOSOL "
				+ "FROM  CPSOLICITACAO O, CPITSOLICITACAO IT "
				+ "WHERE O.CODEMP=? "
				+ "AND O.CODFILIAL=? "
				+ "AND IT.CODSOL=O.CODSOL AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL "
				+ "AND ((IT.DTAPROVITSOL BETWEEN ? AND ?) OR  (O.DTEMITSOL BETWEEN ? AND ?)) "
				+ where + " GROUP BY O.CODSol, O.SitSol, O.DTEmitSol, O.MOTIVOSOL ";

		System.out.println("Query completa:" + sSQL);
		System.out.println(sSQL);
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			int param = 1;
			ps.setInt(param++, Aplicativo.iCodEmp);
			ps.setInt(param++, ListaCampos.getMasterFilial("CPSOLICITACAO"));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));

			if (almoxarifado) {
				ps.setInt(param++, txtCodAlmoxarife.getVlrInteger().intValue());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (CC) {
				ps.setString(param++, txtCodCC.getVlrString());
				ps.setInt(param++, txtAnoCC.getVlrInteger().intValue());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (usuario) {
				ps.setInt(param++, txtCodUsu.getVlrInteger().intValue());
				ps.setInt(param++, txtCodUsu.getVlrInteger().intValue());
				ps.setInt(param++, txtCodUsu.getVlrInteger().intValue());
			}

			ResultSet rs = ps.executeQuery();
			int iLin = 0;

			tab.limpa();
			while (rs.next()) {
				tab.adicLinha();
				
				String aprovSolicitacaoCompra = rs.getString(1);
				if (aprovSolicitacaoCompra.equalsIgnoreCase("PE")) {
					tab.setValor("Pendente", iLin, 0);
				} else if (aprovSolicitacaoCompra.equalsIgnoreCase("CA")) {
					tab.setValor("Cancelada", iLin, 0);
				} else if (aprovSolicitacaoCompra.equalsIgnoreCase("SC")) {
					tab.setValor("Concluída", iLin, 0);
				} else if (aprovSolicitacaoCompra.equalsIgnoreCase("TM")) {
					tab.setValor("Cotação", iLin, 0);
				}

				tab.setValor(new Integer(rs.getInt(2)), iLin, 1);
				tab.setValor(rs.getString(3) == null ? "-" : Funcoes
						.sqlDateToStrDate(rs.getDate(3))
						+ "", iLin, 2);
				tab.setValor(rs.getString(4) == null ? "-" : rs.getString(4) + "",
						iLin, 3);

				iLin++;
			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela CPSOLICITACAO!\n"
					+ err.getMessage());
			err.printStackTrace();
		}
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal("0");
		boolean bImpCot = false;

		/*
		 * bImpCot = Funcoes.mensagemConfirma(this, "Deseja imprimir informações de
		 * cotações de preço?") == 0 ? true : false;
		 */

		imp.montaCab();
		imp.setTitulo("Relatório de Solicitações de Compra");

		try {
			imp.limpaPags();
			for (int iLin = 0; iLin < tab.getNumLinhas(); iLin++) {
				if (imp.pRow() == 0) {
					imp.impCab(136);
					//	imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "| N.Sol.");
					imp.say(imp.pRow() + 0, 15, "| Emissão");
					imp.say(imp.pRow() + 0, 29, "| Situação");
					imp.say(imp.pRow() + 0, 45, "| Motivo.");
					imp.say(imp.pRow() + 0, 137, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					if (bImpCot) {
						imp.say(imp.pRow() + 0, 1, "| Nro. Pedido");
						imp.say(imp.pRow() + 0, 15, "| Nro. Nota");
						imp.say(imp.pRow() + 0, 29, "| Data Fat.");
						imp.say(imp.pRow() + 0, 41, "| ");
						imp.say(imp.pRow() + 0, 56, "| ");
						imp.say(imp.pRow() + 0, 87, "| Vlr. Item Fat.");
						imp.say(imp.pRow() + 0, 105, "| ");
						imp.say(imp.pRow() + 0, 124, "| ");
						imp.say(imp.pRow() + 0, 137, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					}

					imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 136));

				}

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 2, "|"
						+ Funcoes.alinhaDir(tab.getValor(iLin, 1) + "", 8));
				imp.say(imp.pRow() + 0, 15, "| "
						+ Funcoes.alinhaDir(tab.getValor(iLin, 2) + "", 8));
				imp.say(imp.pRow() + 0, 29, "| "
						+ Funcoes.alinhaDir(tab.getValor(iLin, 0) + "", 8));
				imp.say(imp.pRow() + 0, 45, "| "
						+ Funcoes.alinhaDir(tab.getValor(iLin, 3) + "", 87));
				imp.say(imp.pRow() + 0, 137, "| ");

				if (bImpCot) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 2, "|"
							+ Funcoes.alinhaDir(tab.getValor(iLin, 2) + "", 8));
					imp.say(imp.pRow() + 0, 15, "|"
							+ Funcoes.alinhaDir(tab.getValor(iLin, 3) + "", 8));
					imp.say(imp.pRow() + 0, 29, "|");
					imp.say(imp.pRow() + 0, 41, "|");
					imp.say(imp.pRow() + 0, 56, "|");
					imp.say(imp.pRow() + 0, 87, "|"
							+ Funcoes.alinhaDir(tab.getValor(iLin, 12) + "", 15));
					imp.say(imp.pRow() + 0, 105, "|");
					imp.say(imp.pRow() + 0, 124, "|");
					imp.say(imp.pRow() + 0, 137, "|");
				}

				imp.say(imp.pRow() + 1, 0, "+ " + Funcoes.replicate("-", 133));
				imp.say(imp.pRow() + 0, 136, "+");

				if (tab.getValor(iLin, 9) != null) {
					bTotalLiq = bTotalLiq.add(new BigDecimal(Funcoes
							.strCurrencyToDouble("" + tab.getValor(iLin, 9))));
				}

				if (imp.pRow() >= linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0, Funcoes.replicate("=", 136));
			imp.eject();

			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela de orçamentos!"
					+ err.getMessage());
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private void abreSol() {
		int iCodOrc = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
		if (fPrim.temTela("Aprova Solicitação de Compra") == false) {
			FAprovaSolicitacaoCompra tela = new FAprovaSolicitacaoCompra();
			fPrim.criatela("Aprova Solicitação de Compra", tela, con);
			tela.exec(iCodOrc);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btSair) {
			dispose();
		}
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data final!");
			else
				carregaTabela();
			if (evt.getSource() == btPrevimp) {
				imprimir(true);
			}
		}
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}

	}

	public void setTelaPrim(FPrincipal fP) {
		fPrim = fP;
	}

	private int buscaAnoBaseCC() {
		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao buscar o ano-base para o centro de custo.\n"
							+ err.getMessage());
		}
		return iRet;
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);

		lcAlmox.setConexao(cn);
		lcUsuario.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaAnoBaseCC());

	}
}